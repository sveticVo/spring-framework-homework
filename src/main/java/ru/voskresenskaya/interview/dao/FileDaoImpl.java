package ru.voskresenskaya.interview.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import ru.voskresenskaya.interview.service.MessageSourceService;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import static ru.voskresenskaya.interview.Constants.ENCODING;

@Service("fileDao")
public class FileDaoImpl implements FileDao {
    private final Resource questionResource;
    private final Resource answerResource;
    private final Resource totalAnswerResource;
    private final MessageSourceService messageSourceService;

    enum ReturnAnswer {
        MAP, LIST
    }

    @Autowired
    public FileDaoImpl(ResourceLoader loader, MessageSourceService messageSourceService) {
        this.messageSourceService = messageSourceService;
        this.questionResource = loader.getResource("classpath:" + messageSourceService.getMessage("question.file"));
        this.answerResource = loader.getResource("classpath:" + messageSourceService.getMessage("answer.file"));
        this.totalAnswerResource = loader.getResource("classpath:" + messageSourceService.getMessage("total.answer.file"));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> readQuestionFile() throws IOException {
        return (List<String>) readFile(questionResource, ReturnAnswer.LIST);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> readAnswerFile() throws IOException {
        return (Map<String, String>) readFile(answerResource, ReturnAnswer.MAP);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> readTotalAnswerFile() throws IOException {
        return (Map<String, String>) readFile(totalAnswerResource, ReturnAnswer.MAP);
    }

    private Object readFile(Resource resource, ReturnAnswer returnAnswer) throws IOException {
        Map<String, String> resultMap = new HashMap<>();
        List<String> resultList = new ArrayList<>();

        try (CSVParser parser = CSVParser.parse(resource.getInputStream(), Charset.forName(ENCODING), CSVFormat.RFC4180)) {
            for (CSVRecord csvRecord : parser.getRecords()) {
                Iterator<String> iterator = csvRecord.iterator();
                StringBuilder questionBuilder = new StringBuilder();
                int count = 0;
                String key = null;
                while (iterator.hasNext()) {
                    String item = iterator.next();
                    if (StringUtils.isNotBlank(item)) {
                        if (ReturnAnswer.MAP.equals(returnAnswer) && count == 0) {
                            key = item.trim();
                        } else {
                            item = item.replace(";", "\n");
                            questionBuilder.append(", ").append(item);
                        }
                    }
                    count++;
                }
                String question = questionBuilder.toString();
                if (StringUtils.isNotBlank(question)) {
                    if (ReturnAnswer.MAP.equals(returnAnswer)) {
                        resultMap.put(key, question.substring(1).trim());
                    } else {
                        resultList.add(question.substring(1).trim());
                    }
                }
            }
        }
        return ReturnAnswer.MAP.equals(returnAnswer)? resultMap : resultList;
    }
}
