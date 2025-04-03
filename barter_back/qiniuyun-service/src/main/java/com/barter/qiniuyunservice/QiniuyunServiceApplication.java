package com.barter.qiniuyunservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class QiniuyunServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(QiniuyunServiceApplication.class, args);
    }

}
