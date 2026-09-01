package com.rookies6.myspringbootlab.runner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyPropRunner implements CommandLineRunner {
    @Value("${myprop.username}")
    private String username;

    @Value("${myprop.port}")
    private int port;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Myprop username = " + username);
        System.out.println("Myprop int = " + port);
    }

}
