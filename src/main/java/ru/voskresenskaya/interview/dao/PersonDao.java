package ru.voskresenskaya.interview.dao;

import ru.voskresenskaya.interview.domain.Person;

public interface PersonDao {

    Person findByName(String name);
}
