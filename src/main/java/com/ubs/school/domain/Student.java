package com.ubs.school.domain;

import javax.persistence.*;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date birthDate;


    @ManyToOne
    @JoinColumn(name = "fk_school_class", nullable = false, updatable = false) //unidirectional
    private Class clazz;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student") //bidirectional
    private List<MappingStudentSubjectGrade> grades;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public List<MappingStudentSubjectGrade> getGrades() {
        return grades;
    }

    public void setGrades(List<MappingStudentSubjectGrade> grades) {
        this.grades = grades;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDateAsString(String birthDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = sdf.parse(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.birthDate = date;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Student))
            return false;
        if (obj == this)
            return true;
        return this.getId().equals(((Student) obj).getId());
    }

    @Override
    public int hashCode() {
        int i = Math.toIntExact(getId());
        return i * hashCode();
    }
}
