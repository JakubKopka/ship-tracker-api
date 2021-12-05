package dev.kopka.shiptracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShipTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShipTrackerApplication.class, args);
    }

}