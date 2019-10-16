package ru.voskresenskaya.interview.service;

import ru.voskresenskaya.interview.InterviewException;

import java.util.function.Predicate;

public interface ScannerService {

    String getNotNullInput() throws InterviewException;

    String compareUserInput(final String value) throws InterviewException;

    String compareUserInput(Predicate<String> predicate) throws InterviewException;
}
