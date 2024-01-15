package com.app.server;

import org.apache.catalina.core.ApplicationContext;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ServerApplication {

    static final Logger LOGGER = LogManager.getLogger(ServerApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);

    }

}
