package ru.voskresenskaya.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.voskresenskaya.interview.service.InterviewService;


@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        InterviewService service = context.getBean(InterviewService.class);
        service.spendTest();
    }
}
