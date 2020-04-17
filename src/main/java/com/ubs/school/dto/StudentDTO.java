package com.ubs.school.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class StudentDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date birthDate;

    private List<StudentDetailsDTO> grades;

    private ClassDTO clazz;

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

    public ClassDTO getClazz() {
        return clazz;
    }

    public void setClassDTO(ClassDTO clazz) {
        this.clazz = clazz;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<StudentDetailsDTO> getGrades() {
        return grades;
    }

    public void setGrades(List<StudentDetailsDTO> grades) {
        this.grades = grades;
    }

    public String getBirthDate() {
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(birthDate);

        return date;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", classDTO=" + clazz +
                '}';
    }
}
