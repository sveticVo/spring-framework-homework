package ru.voskresenskaya.interview.service;

import ru.voskresenskaya.interview.domain.Person;

public interface PersonService {

    Person getByName(String name);
}
