package com.twicecode.springbatch.csvtodatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.twicecode.springbatch.csvtodatabase.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
