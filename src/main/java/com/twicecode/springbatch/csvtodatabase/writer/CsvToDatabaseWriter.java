package com.twicecode.springbatch.csvtodatabase.writer;

import com.twicecode.springbatch.csvtodatabase.entity.Person;
import com.twicecode.springbatch.csvtodatabase.repository.PersonRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CsvToDatabaseWriter implements ItemWriter<Person> {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void write(List<? extends Person> items) {
        personRepository.saveAll(items);
    }
}
