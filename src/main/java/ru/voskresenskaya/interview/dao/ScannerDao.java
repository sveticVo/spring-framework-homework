package ru.voskresenskaya.interview.dao;

import ru.voskresenskaya.interview.InterviewException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public interface ScannerDao {

    List<String> interview(Scanner in) throws InterviewException, IOException;

    String calculateAnswer(List<String> choices) throws IOException, InterviewException;
}
