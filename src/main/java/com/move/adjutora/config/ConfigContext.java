package com.move.adjutora.config;

import com.move.adjutora.service.TaskService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigContext {
    private final SqlSessionFactory sqlSessionFactory;
    private final TaskService taskService;

    public ConfigContext(String myBatisConfigLocation, Properties props) {
        try {
            InputStream inputStream = Resources.getResourceAsStream(myBatisConfigLocation);

            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            sqlSessionFactory= builder.build(inputStream, props);
            taskService = new TaskService(sqlSessionFactory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TaskService getTaskService() {
        return taskService;
    }
}
