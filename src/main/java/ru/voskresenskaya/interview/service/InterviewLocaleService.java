package ru.voskresenskaya.interview.service;

import java.util.Locale;

public interface InterviewLocaleService {

    Locale getLocale();

    boolean addLocale(String name, Locale locale);
}
