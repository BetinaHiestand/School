package com.ubs.school.controller;

import com.ubs.school.domain.Login;
import com.ubs.school.domain.User;
import com.ubs.school.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mindrot.jbcrypt.BCrypt.checkpw;

@Controller
@SessionAttributes("login")
public class LoginController {

    @Autowired
    SchoolService schoolService;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mav = new ModelAndView("/WEB-INF/jsp/login.jsp");

        mav.addObject("login", new Login());

        return mav;

    }

    @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
    public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response,
                                     @ModelAttribute("login") Login login) {
        ModelAndView mav = null;

        User user = schoolService.validateUser(login);

        if (user != null && checkpw(login.getPassword(), user.getPassword())) {
            mav = new ModelAndView("/WEB-INF/jsp/index.jsp");

            request.setAttribute("user", user.getUsername());
            mav.addObject("user", user.getUsername());
            mav.addObject("request", request.getSession().getId());
            mav.addObject("login", login);
        } else {
            mav = new ModelAndView("/WEB-INF/jsp/login.jsp");
            mav.addObject("message", "Username or Password is wrong!!");
        }

        return mav;

    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ModelAndView logoutProcess(HttpServletRequest request, HttpServletResponse response) {

        request.getSession().invalidate();


        ModelAndView mav = new ModelAndView("/WEB-INF/jsp/home.jsp");
        mav.addObject("message", "You were successfully disconnected.");
        mav.addObject("login", new Login());

        return mav;
    }


}