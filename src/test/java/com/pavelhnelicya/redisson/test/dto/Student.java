package com.pavelhnelicya.redisson.test.dto;

import java.io.Serializable;
import java.util.List;

public class Student {

    private String name;
    private int age;
    private String city;
    private List<Integer> marks;

    public List<Integer> getMarks() {
        return marks;
    }

    public void setMarks(List<Integer> marks) {
        this.marks = marks;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Student(String name, int age, String city, List<Integer> marks) {
        this.name = name;
        this.age = age;
        this.city = city;
        this.marks = marks;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                ", marks=" + marks +
                '}';
    }
}
