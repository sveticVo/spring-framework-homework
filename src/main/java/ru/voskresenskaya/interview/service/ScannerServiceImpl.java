package ru.voskresenskaya.interview.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import ru.voskresenskaya.interview.InterviewException;
import ru.voskresenskaya.interview.dao.ScannerDao;
import ru.voskresenskaya.interview.domain.Option;

import java.util.*;
import java.util.function.Predicate;

import static ru.voskresenskaya.interview.Constants.*;

public class ScannerServiceImpl implements ScannerService {
    private FileService fileService;
    private ScannerDao dao;

    private static final String READY_WORD = "+";
    private static final String SUFFIX_ERROR_MESSAGE = "\nПопробуйте пройти тест в другой раз.";


    public ScannerServiceImpl(FileService fileService, ScannerDao dao) {
        this.fileService = fileService;
        this.dao = dao;
}

    public void spendTest() {
        try (Scanner in = new Scanner(System.in)) {
            System.out.print(TITLE);
            Thread.sleep(2000);
            System.out.print("\n\n- Давайте познакомися :) Как вас зовут?\n");
            String name = getNotNullInput(in);
            System.out.println("- Привет, " + name + "!");

            Thread.sleep(1200);
            System.out.println("Ну что, начнем тест? Поставьте \"" + READY_WORD + "\", если готовы:");

            compareUserInput(in, READY_WORD);
            System.out.print("- Поехали!\n");

            List<String> questions = fileService.getQuestions();
            if (CollectionUtils.isEmpty(questions)) {
                throw new InterviewException("Уупс, к сожалению мы не успели обновить нашу базу с вопросами");
            }

            List<Option> options = dao.interview(in, questions);
            if (CollectionUtils.isEmpty(options)) {
                throw new InterviewException("Ответы не были корректно обработаны.");
            }

            System.out.println(dao.calculateAnswer(options));

        } catch(Exception ex) {
            System.out.println(String.format("Произошла техническая ошибка: %s. %s", ex.getMessage(), SUFFIX_ERROR_MESSAGE));
            System.out.println(GOODBYE_MSG);
            ex.getStackTrace();
        }
    }

    private String getNotNullInput(Scanner in) throws InterviewException {
        return compareUserInput(in, StringUtils::isNotBlank);
    }

    private String compareUserInput(Scanner in, final String value) throws InterviewException {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return compareUserInput(in, value::equalsIgnoreCase);
    }

    private String compareUserInput(Scanner in, Predicate<String> predicate) throws InterviewException {
        String answer;
        int count = 1;
        while (true) {
            answer = in.nextLine().trim();
            if (predicate.test(answer)) {
                break;
            }
            if (count == MAX_TRY_COUNT) {
                throw new InterviewException("- К сожалению, превышено количество попыток для ответа.");
            }
            count++;
            System.out.println("- Попробуйте еще раз:");
        }
        return answer;
    }
}
