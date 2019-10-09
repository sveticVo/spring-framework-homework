package ru.voskresenskaya.interview;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.voskresenskaya.interview.service.ScannerService;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        ScannerService service = (ScannerService)context.getBean("scannerService");

        service.spendTest();

    }
}
