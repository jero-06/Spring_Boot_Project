package com.rookies6.myspringbootlab.runner;

import com.rookies6.myspringbootlab.property.MyPropProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyPropRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyPropRunner.class);

    @Value("${myprop.username}")
    private String username;

    @Value("${myprop.port}")
    private int port;

    private final MyPropProperties myPropProperties;

       public MyPropRunner(MyPropProperties myPropProperties) {
        this.myPropProperties = myPropProperties;

    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Myprop username = " + username);
        System.out.println("Myprop int = " + port);

        logger.info("username = {}", myPropProperties.getUsername());
        logger.info("port = {}", myPropProperties.getPort());
    }

}
