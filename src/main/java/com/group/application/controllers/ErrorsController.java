package com.group.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorsController {

    @RequestMapping(value = "/errors")
    public ModelAndView renderErrorPage(HttpServletRequest req, Exception e) {
        ModelAndView mav = new ModelAndView("errorPage");
        mav.addObject("status", req.getAttribute("javax.servlet.error.status_code"));
        mav.addObject("reason", req.getAttribute("javax.servlet.error.message"));

        mav.addObject("msg",e.getMessage());
        return mav;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }


}
