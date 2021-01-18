package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/login")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    public String getLogin() {
        return "login";
    }

    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public String handleLoginError(Model model) {
        model.addAttribute("loginerror", "true");

        return "login";
    }
}
