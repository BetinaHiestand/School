package com.ubs.school.controller;

import com.ubs.school.domain.Login;
import com.ubs.school.domain.User;
import com.ubs.school.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller

public class RegistrationController {

    @Autowired

    public SchoolService schoolService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)

    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mav = new ModelAndView("/WEB-INF/jsp/register.jsp");

        mav.addObject("user", new User());

        return mav;

    }

    @RequestMapping(value = "/registerProcess", method = RequestMethod.POST)

    public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response,

                                @ModelAttribute("user") User user) {

        User createdUser = schoolService.register(user);
        ModelAndView mav = null;

        if(createdUser != null){
            mav = new ModelAndView("/WEB-INF/jsp/home.jsp");
            mav.addObject("message", "Your login has successfully been created.");
        }
        else{
            mav = new ModelAndView("/WEB-INF/jsp/register.jsp");

            mav.addObject("message", "This username already exists!! Please choose another one.");
        }

        return mav;

    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteProcess(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("login") Login login) {

        Login userLogin = (Login) request.getSession().getAttribute("login");
        boolean success = schoolService.deleteCurrentUser(userLogin.getUsername());
        request.getSession().invalidate();
        ModelAndView mav = new ModelAndView("/WEB-INF/jsp/home.jsp");
        if (success){
            mav.addObject("message", "Your User has been deleted.");
        }
        else {
            mav.addObject("message", "Your User could not be deleted.");
        }

        return mav;
    }

}