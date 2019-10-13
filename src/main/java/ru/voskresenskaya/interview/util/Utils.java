package ru.voskresenskaya.interview.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voskresenskaya.interview.InterviewException;
import ru.voskresenskaya.interview.service.MessageSourceService;

import java.util.Scanner;
import java.util.function.Predicate;
import static ru.voskresenskaya.interview.Constants.*;

@Service
public class Utils {
    private static final String DEFAULT_SEPARATOR = ";";
    private static final String DEFAULT_REPLACE_SEPARATOR = "\n";

    private MessageSourceService messageSourceService;

    @Autowired
    public Utils(MessageSourceService messageSourceService) {
        this.messageSourceService = messageSourceService;
    }

    public static String replaceSeparator(String str) {
        return transform(str, DEFAULT_REPLACE_SEPARATOR, "");
    }

    public static String addNewLine(String str) {
        return transform(str, DEFAULT_REPLACE_SEPARATOR, "\n");
    }

    private static String transform(String str, String separator, String addingStr) {
        if (StringUtils.isBlank(str) || addingStr == null) {
            return null;
        }
        return addingStr + str.replace(DEFAULT_SEPARATOR, separator);
    }

    public String getNotNullInput(Scanner in) throws InterviewException {
        return compareUserInput(in, StringUtils::isNotBlank);
    }

    public String compareUserInput(Scanner in, final String value) throws InterviewException {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return compareUserInput(in, value::equalsIgnoreCase);
    }

    public static String compareFewUserInputDefault(Scanner in, String tryCountDefaultMessage,
                                                    String tryAgainDefaultMessage, String[] vars) throws InterviewException {
        return compareUserInput(in, tryCountDefaultMessage, tryAgainDefaultMessage, compareFewUserInputPredicate(vars));
    }

    public String compareUserInput(Scanner in, Predicate<String> predicate) throws InterviewException {
        return compareUserInput(in, messageSourceService.getMessage("try.count.error"),
                messageSourceService.getMessage("try.again"), predicate);
    }

    private static Predicate<String> compareFewUserInputPredicate(String[] vars) {
        return item -> {
            if (vars == null || item == null) {
                return false;
            }
            for (String var : vars) {
                if (StringUtils.isNotBlank(var) && var.equalsIgnoreCase(item.trim())) {
                    return true;
                }
            }
            return false;
        };
    }

    private static String compareUserInput(Scanner in, String tryCountMessage, String tryAgainMessage,
                                    Predicate<String> predicate) throws InterviewException {
        String answer;
        int count = 1;
        while (true) {
            answer = in.nextLine().trim();
            if (predicate.test(answer)) {
                break;
            }
            if (count == MAX_TRY_COUNT) {
                throw new InterviewException(replaceSeparator(tryCountMessage));
            }
            count++;
            System.out.println(replaceSeparator(tryAgainMessage));
        }
        return answer;
    }
}
