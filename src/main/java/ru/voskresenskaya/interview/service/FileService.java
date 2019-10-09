package ru.voskresenskaya.interview.service;

import java.io.IOException;
import java.util.List;

public interface FileService {

    List<String> getQuestions() throws IOException;
}
