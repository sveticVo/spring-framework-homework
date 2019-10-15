package ru.voskresenskaya.interview;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.voskresenskaya.interview.service.InterviewService;
import ru.voskresenskaya.interview.service.ScannerKeeper;

@ComponentScan
@Configuration
public class Main {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms
                = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/bundle");
        ms.setDefaultEncoding(Constants.ENCODING);
        return ms;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        InterviewService service = context.getBean(InterviewService.class);
        service.spendTest();

        ScannerKeeper scanner = context.getBean(ScannerKeeper.class);
        scanner.close();
    }
}
