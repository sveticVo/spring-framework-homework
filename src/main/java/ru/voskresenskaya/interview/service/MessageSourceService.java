package ru.voskresenskaya.interview.service;

import org.springframework.lang.Nullable;
import java.util.Locale;

public interface MessageSourceService {

    Locale getLocale();

    String getMessage(String var1);

    String getMessage(String var1, @Nullable Object[] var2);
}
