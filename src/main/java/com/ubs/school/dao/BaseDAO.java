package com.ubs.school.dao;

import com.ubs.school.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@Repository
public class BaseDAO extends Teacher {

    @PersistenceContext
    private EntityManager em;

    public com.ubs.school.domain.Class getClassById(Long id) {
        TypedQuery<com.ubs.school.domain.Class> clazz = em.createQuery("SELECT cl from Class cl where cl.id = ?1 ", com.ubs.school.domain.Class.class);
        clazz.setParameter(1, id);
        List<com.ubs.school.domain.Class> results = clazz.getResultList();
        if (results.size() == 0) {
            return null;
        } else {
            return results.get(0);
        }
    }

    public com.ubs.school.domain.Class getClassByName(String className) {
        TypedQuery<com.ubs.school.domain.Class> clazz = em.createQuery("SELECT cl from Class cl where cl.className like ?1 ", com.ubs.school.domain.Class.class);
        clazz.setParameter(1, className);
        List<com.ubs.school.domain.Class> results = clazz.getResultList();
        if (results.size() == 0) {
            return null;
        } else {
            return results.get(0);
        }
    }

    public Subject getSubjectById(Long id) {
        TypedQuery<Subject> subject = em.createQuery("SELECT sub from Subject sub where sub.id=?1", Subject.class);
        subject.setParameter(1, id);
        List<Subject> results = subject.getResultList();
        if (results.size() == 0) {
            return null;
        } else {
            return results.get(0);
        }
    }

    public Teacher getTeacherById(Long id) {
        TypedQuery<Teacher> subject = em.createQuery("SELECT teacher from Teacher teacher where teacher.id=?1", Teacher.class);
        subject.setParameter(1, id);
        List<Teacher> results = subject.getResultList();
        if (results.size() == 0) {
            return null;
        } else {
            return results.get(0);
        }
    }

    public List<Student> getStudentsOfAClass(String classname) {
        TypedQuery<Student> student = em.createQuery("SELECT student from Student student inner join Class cl on cl.id = student.clazz.id where cl.className=?1", Student.class);
        student.setParameter(1, classname);
        return student.getResultList();
    }

    public Double getStudentsAvgGrades(String subject, Long studentId) {
        TypedQuery<Double> grades = em.createQuery("SELECT avg(grade.grade) from MappingStudentSubjectGrade grade inner join Student student on student.id = grade.student.id inner join Subject sub on sub.id = grade.subject.id where sub.subjectName=?1 and student.id =?2", Double.class);
        grades.setParameter(1, subject);
        grades.setParameter(2, studentId);
        return grades.getSingleResult();
    }

    public List<com.ubs.school.domain.Class> getClassesOfTeacher(Long teacherId) {
        TypedQuery<com.ubs.school.domain.Class> classnames = em.createQuery("SELECT cl from Class cl inner join Teacher te on te.id = cl.teacher.id where te.id=?1", com.ubs.school.domain.Class.class);
        classnames.setParameter(1, teacherId);
        return classnames.getResultList();
    }

    public List<com.ubs.school.domain.Class> getClasses() {
        TypedQuery<com.ubs.school.domain.Class> classnames = em.createQuery("SELECT cl from Class cl inner join Teacher te on te.id = cl.teacher.id", com.ubs.school.domain.Class.class);
        return classnames.getResultList();
    }

    public Student getStudentById(Long studentId) {
        TypedQuery<Student> student = em.createQuery("SELECT student from Student student where student.id=?1", Student.class);
        student.setParameter(1, studentId);
        return student.getSingleResult();
    }

    public List<Teacher> getTeachers() {
        TypedQuery<Teacher> teacher = em.createQuery("SELECT teacher from Teacher teacher ", Teacher.class);
        return teacher.getResultList();
    }

    //QueryBuilder Abfrage
    public List<Teacher> getTeacher(String teacherName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Teacher> teacher = cb.createQuery(Teacher.class);
        Root<Teacher> root = teacher.from(Teacher.class);
        teacher.select(root).where(cb.like(root.get("firstName"), teacherName));

        TypedQuery<Teacher> query = em.createQuery(teacher);
        List<Teacher> searchedTeacher = query.getResultList();
        return searchedTeacher;
    }

    public List<Student> getStudentsByFirstName(String firstName) {
        TypedQuery<Student> student = em.createQuery("SELECT student from Student student where student.firstName LIKE CONCAT('%',?1,'%')", Student.class);
        student.setParameter(1, firstName);
        return student.getResultList();
    }

    public List<Student> getStudentsByLastName(String lastName) {
        TypedQuery<Student> student = em.createQuery("SELECT student from Student student where student.lastName LIKE CONCAT('%',?1,'%')", Student.class);
        student.setParameter(1, lastName);
        return student.getResultList();
    }

    public List<Student> getStudentsByNames(String firstName, String lastName) {
        TypedQuery<Student> student = em.createQuery("SELECT student from Student student where student.lastName LIKE CONCAT(?1,'%') and student.firstName LIKE CONCAT(?2,'%') ", Student.class);
        student.setParameter(1, lastName);
        student.setParameter(2, firstName);
        return student.getResultList();
    }

    public List<Student> getStudents() {
        TypedQuery<Student> student = em.createQuery("SELECT student from Student student  ", Student.class);
        return student.getResultList();
    }

    public List<MappingStudentSubjectGrade> getStudentsGrades(Long studentId) {
        TypedQuery<MappingStudentSubjectGrade> grades = em.createQuery("SELECT grade from MappingStudentSubjectGrade grade inner join Student student on student.id = grade.student.id where student.id =?1 order by grade.subject.id", MappingStudentSubjectGrade.class);
        grades.setParameter(1, studentId);
        return grades.getResultList();
    }

    public List<Double> getStudentsGradesBySubject(Long studentId, Long subjectId) {
        TypedQuery<Double> grades = em.createQuery("SELECT grade.grade from MappingStudentSubjectGrade grade inner join Student student on student.id = grade.student.id where student.id =?1 and grade.subject.id = ?2 order by grade.subject.id", Double.class);
        grades.setParameter(1, studentId);
        grades.setParameter(2, subjectId);
        return grades.getResultList();
    }

    public List<Subject> getSubjectsOfStudent(Long studentId) {
        TypedQuery<Subject> grades = em.createQuery("SELECT distinct subject from Subject subject inner join MappingStudentSubjectGrade grade on grade.subject.id = subject.id inner join Student student on student.id = grade.student.id where student.id =?1", Subject.class);
        grades.setParameter(1, studentId);
        return grades.getResultList();
    }

    public User getUserByUsername(String username) {
        TypedQuery<User> user = em.createQuery("SELECT user from User user where user.username =?1 ", User.class);
        user.setParameter(1, username);
        if (user.getResultList().size() > 0) {
            return user.getResultList().get(0);
        } else {
            return null;
        }
    }

    public void persistStudent(Student student) {
        em.persist(student);
    }

    public void persistClass(com.ubs.school.domain.Class clazz) {
        em.persist(clazz);
    }

    public void persistUser(User user) {
        em.persist(user);
    }

    public void deleteUser(User user) {
        em.remove(user);
    }



}
