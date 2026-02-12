package com.mccrady;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class App {
    public static void main(String[] args) {
   	 log.info("Starting application...");
   	 SpringApplication.run(App.class, args);
    }
}