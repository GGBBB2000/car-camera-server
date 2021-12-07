package com.example.cameraserverspringgradle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class CameraServerSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(CameraServerSpringApplication.class, args);
    }
}
