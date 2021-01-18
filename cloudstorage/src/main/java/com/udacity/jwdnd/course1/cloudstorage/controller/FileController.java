package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.Message;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.service.*;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.mvc.support.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.*;

@Controller
@RequestMapping("/user/files")
@SessionAttributes(names = { "authuser" })
public class FileController {
    private FileService service;

    public FileController(FileService service) {
        this.service = service;
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public ModelAndView getList(@SessionAttribute(name = "authUser") User authUser, Model model, @RequestParam(name = "view", required = false) boolean view, @RequestParam(name = "id", required = false) Integer id, HttpServletResponse response) throws Exception {
        // Service call - retrieve file list
        List<FileData> fileList = service.getAllFiles(authUser.getId());

        // Prepare Model
        model.addAttribute("fileObject", new FileData());
        model.addAttribute("fileList", fileList);
        addMessageAttributes(model);

        // Return view and model
        return new ModelAndView("files", "listModel", model);
    }

    private void downloadFile(int id, HttpServletResponse response) throws Exception {
        // Service call - Get file data
        FileData fileData = service.getFileData(id);
        InputStream fileStream = service.getDataAsStream(id);

        // Set MIME type, content type, content length, content disposition, copy
        String mimeType = URLConnection.guessContentTypeFromName(fileData.getName());
        if(mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setContentLength(Integer.parseInt(fileData.getSize()));
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + fileData.getName() + "\""));

        OutputStream outStream = response.getOutputStream();
        IOUtils.copy(fileStream, outStream);
        outStream.flush();
        outStream.close();
    }

    private void addMessageAttributes(Model model) {
        Map<String, Object> modelMap = model.asMap();

        if(modelMap.containsKey("success")) {
            model.addAttribute("success", (boolean)modelMap.get("success"));
        }

        if(modelMap.containsKey("message")) {
            model.addAttribute("message", (String)modelMap.get("message"));
        }
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("fileUpload") MultipartFile uploadedFile, @SessionAttribute("authUser") User user, Model model, RedirectAttributes redirectAttributes) throws Exception {
        // Validation - Check file selection
        if(uploadedFile.getOriginalFilename() == null || uploadedFile.getOriginalFilename() == "") {
            redirectAttributes.addFlashAttribute("success", false);
            redirectAttributes.addFlashAttribute("message", Message.fileNotSelected);
            return "redirect:/user/files/list";
        }

        // Validation - Check file name
        if(service.getFileByName(uploadedFile.getOriginalFilename(), user.getId()) != null) {
            redirectAttributes.addFlashAttribute("success", false);
            redirectAttributes.addFlashAttribute("message", Message.filenameAlreadyExists);
            return "redirect:/user/files/list";
        }

        // Service call - upload file
        FileData newFile = new FileData(null, uploadedFile.getOriginalFilename(), uploadedFile.getContentType(), Long.toString(uploadedFile.getSize()), user.getId());
        boolean isCreated = service.upload(newFile, uploadedFile.getInputStream());

        if(isCreated) {
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", Message.fileCreateSuccess);
            return "redirect:/user/files/list";
        }
        else {
            model.addAttribute("success", false);
            model.addAttribute("message", Message.fileCannotBeCreated);
            model.addAttribute("fileObject", uploadedFile);
            return "files";
        }
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public String deleteFile(@RequestParam("id") int id, Model model, RedirectAttributes redirectAttributes) throws Exception {
        // Service call - delete file
        boolean isDeleted = service.delete(id);

        if(isDeleted) {
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", Message.fileDeleteSuccess);
            return "redirect:/user/files/list";
        }
        else {
            model.addAttribute("success", false);
            model.addAttribute("message", Message.fileCannotBeDeleted);
            return "files";
        }
    }

    @RequestMapping(path = "/view", method = RequestMethod.GET)
    public void viewFile(@RequestParam("id") int id, HttpServletResponse response, Model model) throws Exception {
        downloadFile(id, response);

        model.addAttribute("success",true);
        model.addAttribute("message",Message.fileDownloadSuccess);
    }

}
