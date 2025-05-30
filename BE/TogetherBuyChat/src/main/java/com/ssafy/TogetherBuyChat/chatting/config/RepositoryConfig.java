package com.ssafy.TogetherBuyChat.chatting.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.ssafy.TogetherBuyChat.chatting.repository.jpa")
@EnableMongoRepositories(basePackages = "com.ssafy.TogetherBuyChat.chatting.repository.mongo")
public class RepositoryConfig {
    // JPA와 MongoDB 레포지토리 구분을 위해 각각의 패키지를 지정
}
