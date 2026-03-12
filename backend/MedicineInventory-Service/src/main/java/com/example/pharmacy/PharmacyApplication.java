package com.example.pharmacy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.cognizant.entities.entity")
public class PharmacyApplication {
    public static void main(String[] args) {
        SpringApplication.run(PharmacyApplication.class, args);
    }
}