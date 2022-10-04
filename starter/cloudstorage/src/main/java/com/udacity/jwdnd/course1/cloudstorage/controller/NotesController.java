package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteListService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NotesController {

    private UserService userService;
    private NoteListService noteListService;


    public NotesController(UserService userService, NoteListService noteListService) {
        this.noteListService = noteListService;
        this.userService = userService;
    }

    @PostMapping("/note/upload")
    public String addOrUpdateNote(Authentication authentication, Note note)  {
        Integer currentUser = userService.getUser(authentication.getName()).getUserId();
        note.setUserId(currentUser);

        if (note.getNoteId() != null) {
            noteListService.updateNote(note);

        } else {
            noteListService.saveNote(note, currentUser);

        }
        return "redirect:/home";
    }
    @GetMapping("/deleteNote/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId, Authentication authentication, Model model) {
        Integer currentUser = userService.getUser(authentication.getName()).getUserId();

        try{
            noteListService.deleteNote(noteId);
        } catch (Exception e) {
            model.addAttribute("error", true);
            model.addAttribute("message", "There was a problem deleting note" + noteId);
        }
        return "redirect:/home";
    }



}
