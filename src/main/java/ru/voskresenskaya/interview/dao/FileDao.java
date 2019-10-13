package ru.voskresenskaya.interview.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileDao {

    List<String> readQuestionFile() throws IOException;

    Map<String, String> readAnswerFile() throws IOException;

    Map<String, String> readTotalAnswerFile() throws IOException;
}
