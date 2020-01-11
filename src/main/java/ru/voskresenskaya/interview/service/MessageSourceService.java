package ru.voskresenskaya.interview.service;

import java.util.Locale;

public interface MessageSourceService {

    Locale getLocale();

    String getMessage(String var1);

    String getMessage(String var1, Object[] var2);
}
