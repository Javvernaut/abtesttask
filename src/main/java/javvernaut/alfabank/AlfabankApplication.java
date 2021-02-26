package javvernaut.alfabank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AlfabankApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlfabankApplication.class, args);

    }
}
