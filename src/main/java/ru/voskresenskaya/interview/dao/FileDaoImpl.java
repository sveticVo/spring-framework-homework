package ru.voskresenskaya.interview.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import ru.voskresenskaya.interview.service.MessageSourceService;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import static ru.voskresenskaya.interview.Constants.ENCODING;

@Service
public class FileDaoImpl implements FileDao {
    private final Resource questionResource;
    private final Resource answerResource;
    private final Resource totalAnswerResource;
    private final MessageSourceService messageSourceService;

    public FileDaoImpl(ResourceLoader loader, MessageSourceService messageSourceService) {
        this.messageSourceService = messageSourceService;
        this.questionResource = loader.getResource("classpath:" + messageSourceService.getMessage("question.file"));
        this.answerResource = loader.getResource("classpath:" + messageSourceService.getMessage("answer.file"));
        this.totalAnswerResource = loader.getResource("classpath:" + messageSourceService.getMessage("total.answer.file"));
    }

    @Override
    public List<String> readQuestionFile() throws IOException {
        return readFileAsList(questionResource);
    }

    @Override
    public Map<String, String> readAnswerFile() throws IOException {
        return readFileAsMap(answerResource);
    }

    @Override
    public Map<String, String> readTotalAnswerFile() throws IOException {
        return readFileAsMap(totalAnswerResource);
    }

    private List<String>  readFileAsList(Resource resource) throws IOException {
        List<String> resultList = new ArrayList<>();

        try (CSVParser parser = CSVParser.parse(resource.getInputStream(), Charset.forName(ENCODING), CSVFormat.RFC4180)) {
            parser.getRecords().forEach(csvRecord -> {
                Iterator<String> iterator = csvRecord.iterator();
                StringBuilder questionBuilder = new StringBuilder();
                while (iterator.hasNext()) {
                    String item = iterator.next();
                    if (StringUtils.isNotBlank(item)) {
                        item = item.replace(";", "\n");
                        questionBuilder.append(", ").append(item);
                    }
                }
                String question = questionBuilder.toString();
                if (StringUtils.isNotBlank(question)) {
                    resultList.add(question.substring(1).trim());
                }
            });
        }
        return resultList;
    }

    private Map<String, String> readFileAsMap(Resource resource) throws IOException {
        Map<String, String> resultMap = new HashMap<>();

        try (CSVParser parser = CSVParser.parse(resource.getInputStream(), Charset.forName(ENCODING), CSVFormat.RFC4180)) {
            parser.getRecords().forEach(csvRecord -> {
                Iterator<String> iterator = csvRecord.iterator();
                StringBuilder questionBuilder = new StringBuilder();
                int count = 0;
                String key = null;
                while (iterator.hasNext()) {
                    String item = iterator.next();
                    if (StringUtils.isNotBlank(item)) {
                        if (count == 0) {
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
                    resultMap.put(key, question.substring(1).trim());
                }
            });
        }
        return resultMap;
    }
}
