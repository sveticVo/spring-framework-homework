package ru.voskresenskaya.interview.dao;

import java.io.IOException;
import java.util.List;

public interface FileDao {

    List<String> readFile() throws IOException;
}
