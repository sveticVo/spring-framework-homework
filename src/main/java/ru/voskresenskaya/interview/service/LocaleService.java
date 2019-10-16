package ru.voskresenskaya.interview.service;

import java.util.Locale;

public interface LocaleService {

    Locale getLocale();

    boolean addLocale(String name, Locale locale);
}
