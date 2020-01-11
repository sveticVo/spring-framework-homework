package ru.voskresenskaya.interview.service;

import org.springframework.stereotype.Service;
import ru.voskresenskaya.interview.dao.FileDao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {
    private final FileDao dao;

    public FileServiceImpl (FileDao dao) {
        this.dao = dao;
    }

    public List<String> getQuestions() throws IOException {
        return dao.readQuestionFile();
    }

    public Map<String, String> getAnswers() throws IOException {
        return dao.readAnswerFile();
    }
}
