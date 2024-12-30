package com.move.adjutora.utils;

import com.move.adjutora.config.ConfigContext;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.jline.utils.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class AdjutoraUtils {
    private static ConfigContext configContext;

    public static ConfigContext getConfigContext() {
        return configContext;
    }

    public static void setConfigContext(ConfigContext configContext) {
        AdjutoraUtils.configContext = configContext;
    }

    public static void initializeDatabase(String databaseUrl, String schema) {
        try (Connection connection = DriverManager.getConnection(databaseUrl + ";create=true");
             BufferedReader script = new BufferedReader(
                     new InputStreamReader(
                             Objects.requireNonNull(
                                     AdjutoraUtils.class.getResourceAsStream("/com/move/adjutora/database/initDatabase.sql"))
                             , StandardCharsets.UTF_8)
             );
        ) {

            DatabaseMetaData dmd = connection.getMetaData();
            ResultSet rs = dmd.getSchemas();
            List<String> schemas = new LinkedList<>();
            while (rs.next()) {
                schemas.add(rs.getString("TABLE_SCHEM"));
            }

            if (schemas.contains(schema)) {
                return;
            }

            ScriptRunner runner = new ScriptRunner(connection);
            runner.setStopOnError(true);
            runner.setLogWriter(null);
            runner.runScript(script);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeDatabase(String databaseUrl) {
        try (Connection connection = DriverManager.getConnection(databaseUrl + ";shutdown=true")) {
        } catch (SQLException e) {
            Log.info("Success shutting down database");
        }
    }
}
