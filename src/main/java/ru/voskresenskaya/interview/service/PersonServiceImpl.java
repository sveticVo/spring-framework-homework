package ru.voskresenskaya.interview.service;

import ru.voskresenskaya.interview.dao.PersonDao;
import ru.voskresenskaya.interview.domain.Person;

public class PersonServiceImpl implements PersonService {

    private PersonDao dao;

    public PersonServiceImpl(PersonDao dao) {
        this.dao = dao;
    }

    public Person getByName(String name) {
        return dao.findByName(name);
    }
}
