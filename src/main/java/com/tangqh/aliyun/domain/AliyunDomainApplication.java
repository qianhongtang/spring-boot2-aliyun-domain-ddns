package com.tangqh.aliyun.domain;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AliyunDomainApplication<bean> {

    public static void main(String[] args) {
        SpringApplication.run(AliyunDomainApplication.class, args);
    }
}
