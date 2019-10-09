package ru.voskresenskaya.interview.dao;

import org.apache.commons.lang3.StringUtils;
import ru.voskresenskaya.interview.InterviewException;
import ru.voskresenskaya.interview.domain.Option;

import java.util.*;

import static ru.voskresenskaya.interview.Constants.*;

public class ScannerDaoImpl implements ScannerDao {

    private static final Map<Integer, String> ANSWER_MAP = new HashMap<>();

    static {
        initMap();
    }

    @Override
    public List<Option> interview(Scanner in, List<String> questions) throws InterviewException {
        if (OPTION_MAP.isEmpty()) {
            throw new IllegalArgumentException("Не подготовлены ответы на вопросы.");
        }
        List<Option> resultList = new ArrayList<>();

        for (String question : questions) {
            System.out.println("\n" + question);
            for (Map.Entry<String, Option> entry : OPTION_MAP.entrySet()) {
                String key = entry.getKey();
                Option value = entry.getValue();
                if (value == null || value.isEmpty()) {
                    throw new InterviewException("Варианты ответов на вопросы составлены некорректно.");
                }
                System.out.println(String.format("\n  %s: %s", key, value.getName()));
            }
            System.out.println("\n-Укажите вариант ответа:");

            int count = 1;
            while (true) {
                String answer = in.nextLine();
                Option option = StringUtils.isNotBlank(answer)? OPTION_MAP.get(answer.trim()) : null;
                if (option == null) {
                    if (count == MAX_TRY_COUNT) {
                        throw new InterviewException("- К сожалению, превышено количество попыток для ответа.");
                    }
                    count++;
                    System.out.println("- Попробуйте еще раз:");
                    continue;
                }
                resultList.add(option);
                break;
            }
        }

        return resultList;
    }

    @Override
    public String calculateAnswer(List<Option> options) {
        long goodCount = options.stream().filter(item -> item.isGood()).count();
        if (goodCount >= options.size()/2 + 1) {
            return ANSWER_MAP.get(1);
        } else if (goodCount > options.size()/2 - 1) {
            return ANSWER_MAP.get(2);
        } else {
            return ANSWER_MAP.get(3);
        }
    }

    private static void initMap() {
        OPTION_MAP.put("1", new Option(true, "Да"));
        OPTION_MAP.put("2", new Option(true, "Скорее \"да\", чем \"нет\""));
        OPTION_MAP.put("3", new Option(false, "Скорее \"нет\", чем \"да\""));
        OPTION_MAP.put("4", new Option(false, "Нет"));

        ANSWER_MAP.put(1, ANSWER_1);
        ANSWER_MAP.put(2, ANSWER_2);
        ANSWER_MAP.put(3, ANSWER_3);
    }
}
