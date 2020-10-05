package com.seegrid.entrypoint;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.seegrid.controller.MainController;
import com.seegrid.customexception.ServerStartupException;
import com.seegrid.loader.ServerStartupLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;



@SpringBootApplication
@EnableAsync
@EnableMapRepositories("com.seegrid.repository")
@EnableMongoRepositories("com.seegrid.repository")
@ComponentScan({"com.seegrid.controller","com.seegrid.Services","com.seegrid.algorithms.shuffle",
"com.seegrid.configuration","com.seegrid.loader"})

@EnableConfigurationProperties(AppConfig.class)
public class Application {

    static Logger mainLogger = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args)
    {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        try {
            ServerStartupLoader serverStartupLoader = context.getBean(ServerStartupLoader.class);
            serverStartupLoader.loadDocumentsToMemory();
        }
        catch(ServerStartupException se) {
            mainLogger.error("Server startup failed to load data from database");
        }
        catch(Exception e) {
            //This error needs to be caught and alerted
            mainLogger.error("Server Failed to start and");
        }
    }
}
