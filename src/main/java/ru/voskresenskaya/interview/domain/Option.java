package ru.voskresenskaya.interview.domain;

import org.apache.commons.lang3.StringUtils;

public class Option {
    private final boolean good;
    private final String name;

    public Option(boolean good, String name) {
        this.good = good;
        this.name = name;
    }

    public boolean isGood() {
        return good;
    }

    public String getName() {
        return name;
    }

    public boolean isEmpty() {
        return StringUtils.isBlank(name);
    }
}