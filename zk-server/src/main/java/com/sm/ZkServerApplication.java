package com.sm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.io.IOException;

@SpringBootApplication
@EnableDiscoveryClient
public class ZkServerApplication {

    static Object o = new Object();

    public static void main(String[] args) throws IOException {

        SpringApplication.run(ZkServerApplication.class, args);
    }

}

