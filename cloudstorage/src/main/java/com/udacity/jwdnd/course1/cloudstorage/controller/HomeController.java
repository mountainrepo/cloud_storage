package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.service.*;
import com.udacity.jwdnd.course1.cloudstorage.model.*;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/user")
@SessionAttributes(names = { "authUser" })
public class HomeController {
    private UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path="/home", method = RequestMethod.GET)
    public String getHome(Authentication auth, Model model) throws Exception {
        // Service call - get user
        String username = auth.getName();
        User authenticatedUser = userService.getUserByName(username);

        // Prepare model
        model.addAttribute("authUser", authenticatedUser);

        return "redirect:/user/files/list";
    }
}
