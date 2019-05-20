package com.sm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableConfigServer
@EnableEurekaClient
public class ConfigServerApplication implements CommandLineRunner {

//    static Logger logger = Logger.getLogger(ConfigServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);

//        IntStream.range(1, 10).forEach(b -> {
//            logger.info("测试日志" + b);
//        });
    }

    @Override
    public void run(String... strings) throws Exception {


    }

}

