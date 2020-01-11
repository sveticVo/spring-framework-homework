package ru.voskresenskaya.interview.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.voskresenskaya.interview.InterviewException;
import ru.voskresenskaya.interview.service.MessageSourceService;
import ru.voskresenskaya.interview.service.ScannerService;
import ru.voskresenskaya.interview.util.Utils;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

@Service
public class InterviewDaoImpl implements InterviewDao {

    private final FileDao fileDao;
    private final MessageSourceService messageSourceService;
    private final ScannerService scannerService;

    public InterviewDaoImpl(FileDao fileDao, MessageSourceService messageSourceService, ScannerService scannerService) {
        this.fileDao = fileDao;
        this.messageSourceService = messageSourceService;
        this.scannerService = scannerService;
    }

    @Override
    public List<String> interview() throws InterviewException, IOException {
        Map<String, String> answerMap = fileDao.readAnswerFile();

        if (answerMap == null || answerMap.isEmpty()) {
            throw new IllegalArgumentException(Utils.replaceSeparator(messageSourceService.getMessage("answers.unprepared")));
        }

        List<String> questions = fileDao.readQuestionFile();
        if (CollectionUtils.isEmpty(questions)) {
            throw new InterviewException(Utils.replaceSeparator(messageSourceService.getMessage("questions.empty")));
        }

        List<String> resultList = new ArrayList<>();

        for (String question : questions) {
            System.out.println("\n" + question);
            for (Map.Entry<String, String> entry : answerMap.entrySet()) {
                String key = entry.getKey();
                String answer = entry.getValue();
                if (StringUtils.isBlank(answer)) {
                    throw new InterviewException(Utils.replaceSeparator(messageSourceService.getMessage("answers.not.correct")));
                }
                System.out.println(String.format("\n  %s: %s", key, answer));
            }
            System.out.println(Utils.addNewLine(messageSourceService.getMessage("answer.choose")));

            Predicate<String> predicate = s -> {
                String choice = StringUtils.isNotBlank(s)? answerMap.get(s.trim()) : null;
                return StringUtils.isNotBlank(choice);
            };
            String answer = scannerService.compareUserInput(predicate);
            resultList.add(answer);
        }

        return resultList;
    }

    public String calculateAnswer(List<String> choices) throws IOException, InterviewException {
        Set<String> goodChoices = new HashSet<>();
        goodChoices.add("1");
        goodChoices.add("2");

        long goodAnswerCount = choices.stream().filter(choice -> goodChoices.contains(choice)).count();

        Map<String, String> totalAnswerMap = fileDao.readTotalAnswerFile();
        if (totalAnswerMap == null || totalAnswerMap.isEmpty()) {
            throw new InterviewException(Utils.replaceSeparator(messageSourceService.getMessage("test.not.correct")));
        }
        String totalAnswer;
        if (goodAnswerCount >= choices.size()/2 + 1) {
            totalAnswer = totalAnswerMap.get("1");
        } else if (goodAnswerCount > choices.size()/2 - 1) {
            totalAnswer = totalAnswerMap.get("2");
        } else {
            totalAnswer = totalAnswerMap.get("3");
        }

        if (StringUtils.isBlank(totalAnswer)) {
            throw new InterviewException(Utils.replaceSeparator(messageSourceService.getMessage("answers.empty")));
        }
        return totalAnswer;
    }
}
