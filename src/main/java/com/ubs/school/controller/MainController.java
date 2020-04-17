package com.ubs.school.controller;

import com.ubs.school.domain.Class;
import com.ubs.school.domain.Subject;
import com.ubs.school.domain.Teacher;
import com.ubs.school.dto.ClassDTO;
import com.ubs.school.dto.StudentDTO;
import com.ubs.school.dto.StudentDetailsDTO;
import com.ubs.school.dto.TeacherDTO;
import com.ubs.school.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private SchoolService schoolService;

    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("/WEB-INF/jsp/index.jsp");
    }

    @RequestMapping("/")
    public ModelAndView welcome() {
        return new ModelAndView("/WEB-INF/jsp/home.jsp");
    }

    @RequestMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("/WEB-INF/jsp/home.jsp");
    }

    @RequestMapping(value = "/classes", method = RequestMethod.GET)
    public ModelAndView classes(@RequestParam(required = false) Long teacherId) {
        List<ClassDTO> classList;

        if (teacherId == null) {
            classList = schoolService.getClassesWithTeacher();
        } else {
            classList = schoolService.getClassesofTeacher(teacherId);
        }

        return new ModelAndView("/WEB-INF/jsp/classes.jsp", "classList", classList);
    }

    @RequestMapping("/students")
    public ModelAndView students() {
        return new ModelAndView("/WEB-INF/jsp/students.jsp");
    }

    @RequestMapping(value = "/teachers", method = RequestMethod.GET)
    public ModelAndView teacher() {
        List<TeacherDTO> teachers = schoolService.getTeachers();

        return new ModelAndView("/WEB-INF/jsp/teachers.jsp", "teachers", teachers);
    }


    @RequestMapping(value = "/studentdetails", method = RequestMethod.GET)
    public ModelAndView studentDetails(@RequestParam(required = false)Long studentId) {
        List<StudentDetailsDTO> studentDetails = schoolService.getStudentDetails(studentId);
        return new ModelAndView("/WEB-INF/jsp/studentdetails.jsp", "studentDetails", studentDetails);
    }

    @RequestMapping(value = "/studentform", method = RequestMethod.GET)
    public ModelAndView studentform() {

        List<ClassDTO> schoolClasses = schoolService.getClasses();
        return new ModelAndView("/WEB-INF/jsp/studentform.jsp", "schoolClasses", schoolClasses);
    }

    @RequestMapping(value = "/saveStudent", method = RequestMethod.POST)
    public String saveStudent(@ModelAttribute StudentDTO studentDTO, Class clazz, Model model) throws ParseException {

        if (studentDTO != null && studentDTO.getFirstName().length() > 0 && studentDTO.getLastName().length() > 0
                && studentDTO.getPhoneNumber().length() > 0 && clazz.getClassName().length() > 0 && clazz.getClassName() != null) {


            schoolService.persistStudentDTOWithClass(studentDTO,clazz);
            model.addAttribute("error", "0");
        } else {
            model.addAttribute("error", "1");
        }
        return "redirect:students";
    }

    @RequestMapping(value = "/studentsOfaClass", method = RequestMethod.GET)
    public ModelAndView studentsOfAClass(@RequestParam(required = false) String className) {
        List<StudentDTO> students = schoolService.getStudentsOfAClass(className);
        return new ModelAndView("/WEB-INF/jsp/studentsOfaClass.jsp", "students", students);
    }

    @RequestMapping("/classform")
    public ModelAndView classForm() {
        return new ModelAndView("/WEB-INF/jsp/classform.jsp");
    }


    @RequestMapping(value = "/saveClass", method = RequestMethod.POST)
    public String saveClass(@ModelAttribute ClassDTO clazz, Teacher teacher, Subject subject, Model model) {

        if (clazz != null && clazz.getClassName().length() > 0 && subject.getSubjectName().length() > 0
                && teacher.getFirstName().length() > 0 && teacher.getLastName().length() > 0) {

            schoolService.persistClassDTOWithTeacher(clazz,teacher);
            model.addAttribute("error", "0");
        } else {
            model.addAttribute("error", "1");
        }
        return "redirect:classes";
    }

    @RequestMapping(value = "/searchStudent", method = RequestMethod.POST)
    public ModelAndView searchStudent(@ModelAttribute StudentDTO student) {

        String firstName;
        String lastName;

        if (student.getFirstName().length() > 0) {
            firstName = student.getFirstName();
        } else {
            firstName = null;
        }
        if (student.getLastName().length() > 0) {
            lastName = student.getLastName();
        } else {
            lastName = null;
        }
        List<StudentDTO> students = schoolService.getStudentsByName(firstName, lastName);

        return new ModelAndView("/WEB-INF/jsp/students.jsp", "students", students);
    }
}
