package rest.reservoirapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReservoirApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservoirApiApplication.class, args);
    }

}
