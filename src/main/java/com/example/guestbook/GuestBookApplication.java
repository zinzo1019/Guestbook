package com.example.guestbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GuestBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuestBookApplication.class, args);
    }

}
