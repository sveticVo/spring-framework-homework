package ru.voskresenskaya.interview.dao;

import ru.voskresenskaya.interview.InterviewException;
import ru.voskresenskaya.interview.domain.Option;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public interface ScannerDao {
    Map<String, Option> OPTION_MAP = new HashMap<>();

    List<Option> interview(Scanner in, List<String> questions) throws InterviewException;

    String calculateAnswer(List<Option> options);
}
