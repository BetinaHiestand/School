package com.ubs.school.dto;

import com.ubs.school.domain.Subject;

public class ClassDTO {

    private Long id;
    private String className;
    private TeacherDTO teacher;
    private SubjectDTO subject;

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public SubjectDTO getSubjectDTO() {
        return subject;
    }

    public void setSubjectDTO(SubjectDTO subjectDTO) {
        this.subject = subjectDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ClassDTO{" +
                "className='" + className + '\'' +
                '}';
    }
}
