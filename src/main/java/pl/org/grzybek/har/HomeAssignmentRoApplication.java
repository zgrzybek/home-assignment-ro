package pl.org.grzybek.har;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class HomeAssignmentRoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeAssignmentRoApplication.class, args);
    }

}
