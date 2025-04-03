package com.example.barter_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class BarterEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarterEurekaApplication.class, args);
    }

}
