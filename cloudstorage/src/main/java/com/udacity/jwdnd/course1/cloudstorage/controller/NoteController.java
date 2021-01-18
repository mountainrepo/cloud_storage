package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.Message;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.common.Message.*;

import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping(path = "/user/notes")
@SessionAttributes(names = { "authUser" })
public class NoteController extends HomeController {
    private NoteService service;

    public NoteController(NoteService service, UserService userService) {
        super(userService);
        this.service = service;
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public ModelAndView getList(Model model, @SessionAttribute(name = "authUser") User authUser) throws Exception {
        // Service call - Get all notes
        List<Note> noteList = service.getAllNotes(authUser.getId());

        // Prepare model
        model.addAttribute("noteObject", new Note());
        model.addAttribute("noteList", noteList);

        Map<String, Object> modelMap = model.asMap();
        if(modelMap.containsKey("success")) {
            model.addAttribute("success", (boolean)modelMap.get("success"));
        }

        if(modelMap.containsKey("message")) {
            model.addAttribute("message", (String)modelMap.get("message"));
        }        

        return new ModelAndView("notes", "noteListModel", model);
    }

    @RequestMapping(path = "/addoredit", method = RequestMethod.POST)
    public String addoreditNote(@ModelAttribute(name = "noteObject") Note note, @SessionAttribute(name = "authUser") User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if(bindingResult.hasErrors()) {
            return "notes";
        }

        // Add or Edit
        if(note.getId() == null) {
            return add(note, user, model, redirectAttributes);
        }
        else {
            return edit(note, user, model, redirectAttributes);
        }
    }

    private String add(Note newNote, User user, Model model, RedirectAttributes redirectAttributes) throws Exception {
        // Validation - Check url
        boolean titleExists = service.isTitleExisting(newNote.getTitle(), user.getId());
        if(titleExists) {
            model.addAttribute("noteSuccess", false);
            model.addAttribute("errormessage", Message.titleExists);
            model.addAttribute("noteObject", newNote);
            model.addAttribute("addoredit", "add");
            return "notes";
        }

        // Service call - add note
        newNote.setUserid(user.getId());
        boolean isCreated = service.add(newNote);

        if(isCreated) {
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", Message.noteCreateSuccess);
            return "redirect:/user/notes/list";
        }
        else {
            model.addAttribute("noteSuccess", false);
            model.addAttribute("errormessage", Message.noteCannotBeCreated);
            model.addAttribute("noteObject", newNote);
            model.addAttribute("addoredit", "add");
            return "notes";
        }
    }

    private String edit(Note note, User user, Model model, RedirectAttributes redirectAttributes) throws Exception {
        // Service call - update note
        note.setUserid(user.getId());
        boolean updated = service.update(note);

        if(updated) {
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", Message.noteUpdateSuccess);
            return "redirect:/user/notes/list";
        }
        else {
            model.addAttribute("noteSuccess", false);
            model.addAttribute("errormessage", Message.noteCannotBeUpdated);
            model.addAttribute("noteObject", note);
            model.addAttribute("addoredit", "edit");
            return "notes";
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/delete")
    public String deleteNote(@RequestParam("id") int id, Model model, RedirectAttributes redirectAttributes) throws Exception {
        // Service call - delete note
        boolean isDeleted = service.delete(id);

        if(isDeleted) {
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", Message.noteDeleteSuccess);
            return "redirect:/user/notes/list";
        }
        else {
            model.addAttribute("noteSuccess", false);
            model.addAttribute("errormessage", Message.noteCannotBeDeleted);
            return "notes";
        }
    }
}
