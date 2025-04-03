package com.example.barter_good;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@EnableTransactionManagement
@SpringBootApplication
@EntityScan("com.example.barter_good.domain.entity")
@MapperScan("com.example.barter_good.mapper")
@EnableDiscoveryClient
@EnableDistributedTransaction
public class BarterGoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarterGoodApplication.class, args);
    }

}
