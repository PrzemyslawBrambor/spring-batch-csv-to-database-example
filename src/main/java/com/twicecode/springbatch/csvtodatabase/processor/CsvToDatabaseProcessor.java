package com.twicecode.springbatch.csvtodatabase.processor;

import org.springframework.batch.item.ItemProcessor;
import com.twicecode.springbatch.csvtodatabase.entity.Person;

public class CsvToDatabaseProcessor implements ItemProcessor<Person, Person> {

    @Override
    public Person process(Person person) {
        person.setName(person.getName().toUpperCase());
        person.setSurname(person.getSurname().toUpperCase());
        return person;
    }
}
