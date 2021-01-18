package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.Message;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
import com.udacity.jwdnd.course1.cloudstorage.model.*;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping(path = "/user/credentials")
@SessionAttributes(names = { "authUser" })
public class CredentialController extends HomeController {
    private CredentialService service;

    public CredentialController(CredentialService service, UserService userService) {
        super(userService);
        this.service = service;
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public ModelAndView getList(@SessionAttribute(name = "authUser") User authUser, Model model) throws Exception {
        // Service call - Service call
        List<Credential> credentialList = service.getAllCredentials(authUser.getId());

        // Prepare model
        model.addAttribute("credObject", new Credential());
        model.addAttribute("credentialList", credentialList);

        Map<String, Object> modelMap = model.asMap();

        if(modelMap.containsKey("success")) {
            model.addAttribute("success", (boolean)modelMap.get("success"));
        }

        if(modelMap.containsKey("message")) {
            model.addAttribute("message", (String)modelMap.get("message"));
        }

        // Return
        return new ModelAndView("credentials", "listModel", model);
    }

    @RequestMapping(path = "/addoredit", method = RequestMethod.POST)
    public String addoreditCredential(@ModelAttribute(name = "credObject") Credential credential, @SessionAttribute(name = "authUser") User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if(bindingResult.hasErrors()) {
            return "credentials";
        }

        if(credential.getId() == null) {
            return add(credential, user, model, redirectAttributes);
        }
        else {
            return edit(credential, user, model, redirectAttributes);
        }
    }

    private String add(Credential newCredential, User user, Model model, RedirectAttributes redirectAttributes) throws Exception {
        // Validation - Check url
        boolean urlExists = service.isUrlExisting(newCredential.getUrl(), user.getId());
        if(urlExists) {
            model.addAttribute("credSuccess", false);
            model.addAttribute("errormessage", Message.urlExists);
            model.addAttribute("credObject", newCredential);
            model.addAttribute("addoredit", "add");
            return "credentials";
        }

        // Add credential
        newCredential.setUserid(user.getId());
        boolean isCreated = service.add(newCredential);

        if(isCreated) {
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", Message.credentialCreateSuccess);
            return "redirect:/user/credentials/list";
        }
        else {
            model.addAttribute("credSuccess", false);
            model.addAttribute("errormessage", Message.credentialCannotBeCreated);
            model.addAttribute("credObject", newCredential);
            model.addAttribute("addoredit", "add");
            return "credentials";
        }
    }

    private String edit(Credential credential, User user, Model model, RedirectAttributes redirectAttributes) throws Exception {
        // Service call - Update
        credential.setUserid(user.getId());
        boolean updated = service.update(credential);

        if(updated) {
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", Message.credentialUpdateSuccess);
            return "redirect:/user/credentials/list";
        }
        else {
            model.addAttribute("credSuccess", false);
            model.addAttribute("errormessage", Message.credentialCannotBeUpdated);
            model.addAttribute("credObject", credential);
            model.addAttribute("addoredit", "edit");
            return "credentials";
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/delete")
    public String deleteCredential(@RequestParam("id") int id, Model model, RedirectAttributes redirectAttributes) throws Exception {
        // Service call - delete credential
        boolean isDeleted = service.delete(id);

        if(isDeleted) {
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", Message.credentialDeleteSuccess);
            return "redirect:/user/credentials/list";
        }
        else {
            model.addAttribute("credSuccess", false);
            model.addAttribute("errormessage", Message.credentialCannotBeDeleted);
            return "credentials";
        }
    }
}
