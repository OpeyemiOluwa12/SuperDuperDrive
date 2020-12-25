package com.opeyemi.superduperdrive.controller;

import com.opeyemi.superduperdrive.model.Files;
import com.opeyemi.superduperdrive.model.Users;
import com.opeyemi.superduperdrive.services.CommonService;
import com.opeyemi.superduperdrive.services.FileService;
import com.opeyemi.superduperdrive.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final FileService fileService;
    private final UserService userService;
    private final CommonService commonService;

    public HomeController(FileService fileService, UserService userService, CommonService commonService) {
        this.fileService = fileService;
        this.userService = userService;
        this.commonService = commonService;
    }

    @GetMapping
    public String home() {
        return "/home";
    }

    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model) {
        model.addAttribute("file", file);
        int rowsAdded = fileService.uploadFiles(file, commonService.getUserId());
//TODO add conditions
        return "redirect:/home";
    }

    @ModelAttribute("files")
    public List<Files> files() {
        return fileService.getAllFiles(commonService.getUserId());
    }



}
