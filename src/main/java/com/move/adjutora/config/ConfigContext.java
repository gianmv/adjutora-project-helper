package com.move.adjutora.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.move.adjutora.service.TaskService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigContext {
    private final SqlSessionFactory sqlSessionFactory;
    private final TaskService taskService;
    private final ObjectMapper mapper;

    public ConfigContext(String myBatisConfigLocation, Properties props) {
        try {
            InputStream inputStream = Resources.getResourceAsStream(myBatisConfigLocation);

            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            this.sqlSessionFactory= builder.build(inputStream, props);
            this.taskService = new TaskService(sqlSessionFactory);

            this.mapper = JsonMapper
                    .builder()
                    .addModule(new JavaTimeModule()).build();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
