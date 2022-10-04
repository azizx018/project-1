package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private NoteListService noteListService;
    @Autowired
    private AttachmentListService attachmentListService;
    @Autowired
    private UserService userService;
    @Autowired
    private CredentialListService credentialListService;
    private EncryptionService encryptionService;




    public HomeController(AttachmentListService attachmentListService, NoteListService noteListService,
                          UserService userService, CredentialListService credentialListService,
                          EncryptionService encryptionService) {
        this.attachmentListService = attachmentListService;
        this.userService = userService;
        this.noteListService = noteListService;
        this.credentialListService = credentialListService;
        this.encryptionService = encryptionService;

    }


    @GetMapping()
    public String getHomePage(Authentication authentication, Model model) {
        User user = userService.getUser(authentication.getPrincipal().toString());
        int userId = user.getUserId();
        model.addAttribute("notes", noteListService.getNotesByUser(userId));
        model.addAttribute("attachments", attachmentListService.getAttachmentsByUser(userId));
        model.addAttribute("credentials", credentialListService.getCredByUser(userId));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }



}
