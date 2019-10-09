package ru.voskresenskaya.interview.dao;

import ru.voskresenskaya.interview.domain.Person;

public class PersonDaoSimple implements PersonDao {

    public Person findByName(String name) {
        return new Person(name, 18);
    }
}
