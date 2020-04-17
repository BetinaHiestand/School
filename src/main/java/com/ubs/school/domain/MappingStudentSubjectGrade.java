package com.ubs.school.domain;

import javax.persistence.*;

@Entity
@Table(name = "STUDENT_SUBJECT_GRADE")
public class MappingStudentSubjectGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double grade;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_SUBJECT", nullable = false, updatable = false) //unidirectional
    private Subject subject;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_STUDENT", nullable = false, updatable = false) //unidirectional
    private Student student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
