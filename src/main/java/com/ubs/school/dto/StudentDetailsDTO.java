package com.ubs.school.dto;

import java.util.List;

public class StudentDetailsDTO {

    private Long id;
    private StudentDTO student;
    private SubjectDTO subject;
    private double average;
    private List<Double> grades;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudentDTO(StudentDTO student) {
        this.student = student;
    }

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubjectDTO(SubjectDTO subject) {
        this.subject = subject;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

    public List<Double> getGrades() {
        return grades;
    }

    public void setGrades(List<Double> grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {
        return "StudentDetailsDTO{" +
                "id=" + id +
                ", studentDTO=" + student +
                ", subjectDTO=" + subject +
                '}';
    }
}
