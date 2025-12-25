package com.alaToo.scrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class StudentComplaintApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentComplaintApplication.class, args);
    }
}
