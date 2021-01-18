package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    @RequestMapping(method = RequestMethod.POST)
    public String logout(SessionStatus sessionStatus, Model model) {
        sessionStatus.setComplete();

        model.addAttribute("logout", true);

        return "redirect:/login";
    }
}
