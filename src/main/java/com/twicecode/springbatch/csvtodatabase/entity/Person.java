package com.twicecode.springbatch.csvtodatabase.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Setter
@Entity
public class Person {

    @Id
    private Long id;
    private String name;
    private String surname;
    private int age;
    private String email;
}
