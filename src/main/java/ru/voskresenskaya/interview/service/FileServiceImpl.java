package ru.voskresenskaya.interview.service;

import ru.voskresenskaya.interview.dao.FileDao;

import java.io.IOException;
import java.util.List;

public class FileServiceImpl implements FileService {
    private FileDao dao;

    public FileServiceImpl (FileDao dao) {
        this.dao = dao;
    }

    public List<String> getQuestions() throws IOException {
        return dao.readFile();
    }
}
