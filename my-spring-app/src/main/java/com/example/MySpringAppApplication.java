package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"com.example.modules.entertainment_ecosystem"})
@EnableJpaRepositories(basePackages = "com.example.modules.entertainment_ecosystem.repository")
@EntityScan(basePackages = "com.example.modules.entertainment_ecosystem.model")
public class MySpringAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySpringAppApplication.class, args);
    }

}