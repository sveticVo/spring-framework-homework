package ru.voskresenskaya.interview.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static ru.voskresenskaya.interview.Constants.ENCODING;

public class FileDaoImpl implements FileDao {
    private Resource resource;

    public FileDaoImpl(ResourceLoader loader, String fileName) {
        this.resource = loader.getResource("classpath:" + fileName);
    }

    @Override
    public List<String> readFile() throws IOException {
        List<String> resultList = new ArrayList<>();
        CSVParser parser = CSVParser.parse(resource.getInputStream(), Charset.forName(ENCODING), CSVFormat.RFC4180);
        for (CSVRecord csvRecord : parser.getRecords()) {
            Iterator<String> iterator = csvRecord.iterator();
            StringBuilder questionBuilder = new StringBuilder();
            while (iterator.hasNext()) {
                String item = iterator.next();
                if (StringUtils.isNotBlank(item)) {
                    questionBuilder.append(",").append(item);
                }
            }
            String question = questionBuilder.toString();
            if (StringUtils.isNotBlank(question)) {
                resultList.add(question.substring(1));
            }
        }
        return resultList;
    }
}
