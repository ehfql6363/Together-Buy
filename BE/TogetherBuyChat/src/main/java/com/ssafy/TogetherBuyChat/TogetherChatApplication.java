package com.ssafy.TogetherBuyChat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication(scanBasePackages = "com.ssafy.TogetherBuyChat")
@EnableJpaRepositories(basePackages = {
        "com.ssafy.TogetherBuyChat.member.repository",
        "com.ssafy.TogetherBuyChat.community.repository"
})
//@EnableMongoRepositories
public class TogetherChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(TogetherChatApplication.class, args);
    }
}
