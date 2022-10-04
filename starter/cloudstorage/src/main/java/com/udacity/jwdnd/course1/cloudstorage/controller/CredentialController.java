package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialListService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialController {
    private UserService userService;

    private CredentialListService credentialListService;

    public CredentialController(UserService userService, CredentialListService credentialListService) {
        this.userService = userService;
        this.credentialListService = credentialListService;
    }

    @PostMapping("/cred/upload")
    public String addOrUpdateCredential(Authentication authentication, Credential credential) {
        Integer currentUser = userService.getUser(authentication.getName()).getUserId();

        if (credential.getCredentialId() != null) {
            credentialListService.updateCred(credential);
        } else {
            credentialListService.saveCred(credential, currentUser);
        }
        return "redirect:/home";
    }
    @GetMapping("/deleteCredential/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") Integer credentialId, Authentication authentication,
                                    Model model) {
        Integer currentUser = userService.getUser(authentication.getName()).getUserId();
        try{
            credentialListService.deleteCred(credentialId);
        } catch (Exception e) {
            model.addAttribute("error", true);
            model.addAttribute("message", "There was an error deleting credential record " + credentialId);
        }
        return "redirect:/home";
    }
}
