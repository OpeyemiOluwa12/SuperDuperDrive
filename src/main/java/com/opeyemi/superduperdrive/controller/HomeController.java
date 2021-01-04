package com.opeyemi.superduperdrive.controller;

import com.opeyemi.superduperdrive.model.Credentials;
import com.opeyemi.superduperdrive.model.Files;
import com.opeyemi.superduperdrive.model.Notes;
import com.opeyemi.superduperdrive.services.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final UserService userService;
    private final CommonService commonService;

    public HomeController(FileService fileService, NoteService noteService, CredentialService credentialService, UserService userService, CommonService commonService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.userService = userService;
        this.commonService = commonService;
    }

    @GetMapping
    public String home() {
        return "home";
    }

    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model) {
        String fileUploadError = null;
        int fileLength = 0;
        try {
            fileLength = file.getBytes().length;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fileLength < 1) {
            fileUploadError = "You cannot upload an empty file";
        }

        if (fileUploadError == null && fileService.isFileNameAvailable(file.getOriginalFilename())) {
            fileUploadError = "You can not upload a file that has been uploaded before";
        }
        if (fileUploadError == null) {
            int rowsAdded = fileService.uploadFiles(file, commonService.getUserId());
            if (rowsAdded < 0) {
                fileUploadError = "File upload error occurred please try again";
            }
        }
        if (fileUploadError == null) {
            model.addAttribute("fileUploadSuccess", file);
        } else {
            model.addAttribute("fileUploadError", fileUploadError);
        }

        return "result";
    }

    @ModelAttribute("files")
    public List<Files> files() {
        return fileService.getAllFiles(commonService.getUserId());
    }

    @GetMapping("/delete-file/{fileId}")
    public String deleteFile(@PathVariable int fileId) {
        int rowsRemoved = fileService.deleteFile(fileId);
        return "redirect:/home";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int fileId) {
        Files files = fileService.getSingleFile(fileId);
        ByteArrayResource resource = new ByteArrayResource(files.getFileData());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(Long.parseLong(files.getFileSize()))
                .contentType(MediaType.valueOf(files.getContentType()))
                .body(resource);
    }

    @ModelAttribute("notes")
    public List<Notes> notes() {
        return noteService.getAllNotes(commonService.getUserId());
    }

    @PostMapping("/note")
    public String saveNote(@ModelAttribute("noteForm") Notes notes, Model model) {
        notes.setUserId(commonService.getUserId());
        if (notes.getNoteId() == null) {
            noteService.addNote(notes);
        } else {
            noteService.updateNotes(notes);
        }
        model.addAttribute("notes", noteService.getAllNotes(commonService.getUserId()));
        return "home";
    }

    @GetMapping("/delete-note/{noteId}")
    public String deleteNote(@PathVariable int noteId) {
        int rowsRemoved = noteService.deleteNote(noteId);
        return "redirect:/home";
    }


    @ModelAttribute("credentials")
    public List<Credentials> credentials() {
        return credentialService.getAllCredentials(commonService.getUserId());
    }

    @PostMapping("/credentials")
    public String saveCredential(@ModelAttribute("credentialForm") Credentials credentials, Model model) {
        credentials.setUserId(commonService.getUserId());
        if (credentials.getCredentialId() == null) {
            credentialService.addCredential(credentials);
        } else {
            credentialService.updateCredential(credentials);
        }
        model.addAttribute("credentials", credentialService.getAllCredentials(commonService.getUserId()));
        return "home";
    }

    @GetMapping("/delete-credential/{credentialId}")
    public String deleteCredential(@PathVariable int credentialId) {
        int rowsRemoved = credentialService.deleteCredential(credentialId);
        return "redirect:/home";
    }
}
