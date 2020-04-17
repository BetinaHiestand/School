package com.ubs.school.service;

import com.ubs.school.dao.BaseDAO;
import com.ubs.school.domain.Class;
import com.ubs.school.domain.*;
import com.ubs.school.dto.*;
import org.mindrot.jbcrypt.BCrypt;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * This services fills the database from a csv-import and gets all the Students, Teachers, Schoolclasses, Subjects, Grades from the database.
 */
@Service
public class SchoolService {

    @Autowired
    private BaseDAO dao;


    @Transactional(readOnly = true)
    public Double getStudentsAverageBySubject(String subject, Long studentId) {
        Double average = dao.getStudentsAvgGrades(subject, studentId);
        return average;
    }

    /**
     * Gets student details from the database and creates a StudentDTO out of it
     *
     * @param id
     * @return a Student DTO
     */

    @Transactional(readOnly = true)
    public StudentDTO getStudentDetailsById(Long id) {
        Student student = dao.getStudentById(id);
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setPhoneNumber(student.getPhoneNumber());
        ClassDTO classDTO = new ClassDTO();
        classDTO.setClassName(student.getClazz().getClassName());
        studentDTO.setClassDTO(classDTO);

        return studentDTO;
    }

    /**
     * Gets user-password from Database
     */

    @Transactional(readOnly = true)
    public User validateUser(Login login) {

        User user = dao.getUserByUsername(login.getUsername());

        return user;

    }

    /**
     * Checks if user already exists and creates user if possible
     */
    @Transactional
    public User register(User user) {
        User existingUser = dao.getUserByUsername(user.getUsername());
        if (existingUser == null){
            String encryptedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            if(BCrypt.checkpw(user.getPassword(), encryptedPassword)){
                user.setPassword(user.getPassword());
                user.setUsername(Encode.forJavaScript(user.getUsername()));
                dao.persistUser(user);
                return user;
            }
                return null;
        }
            return null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean deleteCurrentUser(String username){
        try{
            User user = dao.getUserByUsername(username);
            dao.deleteUser(user);
            return true;

        }
        catch (Exception e){
            return false;
        }
    }



    /**
     * Get's teachers from Database
     *
     * @return a List of TeacherDTO's
     */
    @Transactional(readOnly = true)
    public List<TeacherDTO> getTeachers() {
        List<Teacher> teachers = dao.getTeachers();
        List<TeacherDTO> teacherDTOS = new ArrayList<>();

        for (Teacher teacher : teachers) {
            TeacherDTO teacherDTO = convertTeacherToDTO(teacher);
            teacherDTOS.add(teacherDTO);
        }
        return teacherDTOS;
    }

    @Transactional(readOnly = true)
    public List<ClassDTO> getClasses() {
        List<com.ubs.school.domain.Class> schoolClasses = dao.getClasses();

        List<ClassDTO> teacherClasses = new ArrayList<>();

        for (com.ubs.school.domain.Class schoolClass : schoolClasses) {
            ClassDTO clazz = new ClassDTO();
            clazz.setClassName(schoolClass.getClassName());

            teacherClasses.add(clazz);
        }
        return teacherClasses;
    }

    /**
     * Get's all Schoolclasses form the database and adds the Teacher
     *
     * @return A list of schoolclasses(ClassDTO) with the main teacher
     */
    @Transactional(readOnly = true)
    public List<ClassDTO> getClassesWithTeacher() {
        List<com.ubs.school.domain.Class> schoolClasses = dao.getClasses();

        List<ClassDTO> teacherClasses = new ArrayList<>();

        for (com.ubs.school.domain.Class schoolClass : schoolClasses) {
            ClassDTO clazz = new ClassDTO();
            clazz.setClassName(schoolClass.getClassName());
            TeacherDTO teacher = convertTeacherToDTO(schoolClass.getTeacher());
            clazz.setTeacher(teacher);
            teacherClasses.add(clazz);
        }
        return teacherClasses;
    }

    /**
     * Gets all classes which are assigned to a teacher
     *
     * @param teacherId
     * @return list of schoolclasses
     */
    @Transactional(readOnly = true)
    public List<ClassDTO> getClassesofTeacher(Long teacherId) {
        List<com.ubs.school.domain.Class> schoolClasses = dao.getClassesOfTeacher(teacherId);
        List<ClassDTO> teacherClasses = new ArrayList<>();

        for (com.ubs.school.domain.Class schoolClass : schoolClasses) {
            ClassDTO classDTO = new ClassDTO();
            classDTO.setClassName(schoolClass.getClassName());
            TeacherDTO teacher = convertTeacherToDTO(schoolClass.getTeacher());
            classDTO.setTeacher(teacher);
            teacherClasses.add(classDTO);
        }
        return teacherClasses;
    }

    /**
     * Gets all the students of a specific class from the database
     *
     * @param className
     * @return list of StudentDTO's
     */
    @Transactional(readOnly = true)
    public List<StudentDTO> getStudentsOfAClass(String className) {
        List<Student> studentsOfClass = dao.getStudentsOfAClass(className);

        List<StudentDTO> students = new ArrayList<>();

        for (Student student1 : studentsOfClass) {
            StudentDTO student = convertStudentToDTO(student1);
            ClassDTO clazz = new ClassDTO();
            clazz.setClassName(student1.getClazz().getClassName());
            student.setClassDTO(clazz);
            students.add(student);
        }
        return students;
    }

    @Transactional
    public void persistClass(Class clazz) {
        dao.persistClass(clazz);
    }

    @Transactional
    public void persistStudent(Student student) {
        dao.persistStudent(student);
    }

    @Transactional
    public void persistStudentDTOWithClass(StudentDTO studentDTO, Class clazz) {

        Student student = new Student();
        student.setFirstName(Encode.forJavaScript(studentDTO.getFirstName()));
        student.setLastName(Encode.forJavaScript(studentDTO.getLastName()));
        student.setPhoneNumber(Encode.forJavaScript(studentDTO.getPhoneNumber()));
        student.setBirthDateAsString(studentDTO.getBirthDate());

        Class clazz1 = getClassByName(clazz.getClassName());
        student.setClazz(clazz1);
        persistStudent(student);

    }

    @Transactional
    public void persistClassDTOWithTeacher(ClassDTO clazz, Teacher teacher) {

        Class schoolClass = new Class();
        schoolClass.setClassName(Encode.forJavaScript(clazz.getClassName()));
        schoolClass.setTeacher(teacher);

        persistClass(schoolClass);
    }

    @Transactional(readOnly = true)
    public Subject getSubjectById(Long id) {
        Subject subject = dao.getSubjectById(id);
        return subject;
    }

    @Transactional(readOnly = true)
    public Teacher getTeacherByID(Long id) {
        Teacher teacher = dao.getTeacherById(id);
        return teacher;
    }

    @Transactional(readOnly = true)
    public Class getClassById(Long id) {
        Class clazz = dao.getClassById(id);
        return clazz;
    }

    @Transactional(readOnly = true)
    public Class getClassByName(String className) {
        Class clazz = dao.getClassByName(className);
        return clazz;
    }

    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        Student student = dao.getStudentById(id);
        return student;
    }

    /**
     * Gets students from the database based on the first and/or lastName
     *
     * @param firstName
     * @param lastName
     * @return list of StudentDTO's
     */

    @Transactional(readOnly = true)
    public List<StudentDTO> getStudentsByName(String firstName, String lastName) {
        List<Student> students = new ArrayList<>();

        if (firstName != null && lastName != null) {
            students = dao.getStudentsByNames(firstName, lastName);
        } else if (lastName != null) {
            students = dao.getStudentsByLastName(lastName);
        } else if (firstName != null) {
            students = dao.getStudentsByFirstName(firstName);
        } else {
            students = dao.getStudents();
        }

        List<StudentDTO> studentsDTO = new ArrayList<>();

        for (Student student1 : students) {

            StudentDTO student = convertStudentToDTO(student1);

            ClassDTO clazz = new ClassDTO();
            clazz.setClassName(student1.getClazz().getClassName());
            student.setClassDTO(clazz);

            studentsDTO.add(student);
        }
        return studentsDTO;
    }


    /**
     * Gets all the grades of a specific student from the database
     *
     * @param studentId
     * @return a List of StudentDetailsDTO containing all the grades, subjects and studentdetails
     */
    @Transactional(readOnly = true)
    public List<StudentDetailsDTO> getStudentDetails(Long studentId) {

        Student student = getStudentById(studentId);
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setLastName(student.getLastName());
        studentDTO.setFirstName(student.getFirstName());
        ClassDTO clazz = new ClassDTO();
        clazz.setClassName(student.getClazz().getClassName());
        studentDTO.setClassDTO(clazz);

        List<StudentDetailsDTO> details = new ArrayList<>();
        List<Subject> subjects = dao.getSubjectsOfStudent(studentId);

        if (subjects.size() > 0) {
            for (Subject subject : subjects) {
                StudentDetailsDTO studentDetails = new StudentDetailsDTO();
                studentDetails.setStudent(studentDTO);
                List<Double> gradesPerSubject = dao.getStudentsGradesBySubject(student.getId(), subject.getId());
                studentDetails.setGrades(gradesPerSubject);
                SubjectDTO subjectDTO = new SubjectDTO();
                subjectDTO.setId(subject.getId());
                subjectDTO.setSubjectName(subject.getSubjectName());
                studentDetails.setSubjectDTO(subjectDTO);
                studentDetails.setAverage(dao.getStudentsAvgGrades(subject.getSubjectName(), studentId));
                details.add(studentDetails);
            }
        } else {
            StudentDetailsDTO studentDetail = new StudentDetailsDTO();
            studentDetail.setStudent(studentDTO);
            details.add(studentDetail);
        }
        return details;
    }

    private TeacherDTO convertTeacherToDTO(Teacher teacher) {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setLastName(teacher.getLastName());
        teacherDTO.setFirstName(teacher.getFirstName());
        teacherDTO.setId(teacher.getId());
        return teacherDTO;
    }

    private StudentDTO convertStudentToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setId(student.getId());
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setPhoneNumber(student.getPhoneNumber());
        studentDTO.setBirthDate(student.getBirthDate());
        return studentDTO;
    }
}
