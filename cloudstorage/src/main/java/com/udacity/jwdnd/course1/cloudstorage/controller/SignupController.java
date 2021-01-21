package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.Message;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
import static com.udacity.jwdnd.course1.cloudstorage.common.Message.*;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/signup")
public class SignupController {
    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getSignupPage() {
        ModelMap model = new ModelMap();
        model.addAttribute("userform", new User());

        return new ModelAndView("signup", model);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView signupUser(@ModelAttribute("userform") User newUser, Model model, BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()) {
            return new ModelAndView("signup", "userModel", model);
        }

        // Validation - Check user
        boolean userExists = userService.isUserExisting(newUser.getFirstname(), newUser.getLastname());
        if(userExists) {
            model.addAttribute("success", false);
            model.addAttribute("signupError", Message.userAlreadyExists);
            return new ModelAndView("signup", "userModel", model);
        }

        // Validation - Check username
        boolean usernameExists = userService.isUsernameExisting(newUser.getUsername());
        if(usernameExists) {
            model.addAttribute("success", false);
            model.addAttribute("signupError", usernameAlreadyExists);
            return new ModelAndView("signup", "userModel", model);
        }

        // Service call - add user
        boolean isInserted = userService.add(newUser);

        if(isInserted) {
            model.addAttribute("success", true);
            //return new ModelAndView("signup", "userModel", model);
            return new ModelAndView("login", "loginModel", model);
        }
        else {
            model.addAttribute("success", false);
            model.addAttribute("signupError", Message.databaseError);
            return new ModelAndView("signup", "userModel", model);
        }
    }

}
