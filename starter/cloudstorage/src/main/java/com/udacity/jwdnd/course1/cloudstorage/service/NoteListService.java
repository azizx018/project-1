package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteListService {
    private final NoteMapper noteMapper;

    public NoteListService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int updateNote(Note note) {
        note.setNoteId(note.getNoteId());
        note.setNoteDescription(note.getNoteDescription());
        note.setNoteTitle(note.getNoteTitle());
        return noteMapper.updateNote(note);
    }

    public int saveNote(Note note, Integer userId) {
        return noteMapper.saveNote(new Note(null, note.getNoteTitle(), note.getNoteDescription(), note.getUserId()));
    }
    public Note getSelectedNote(Integer noteId) {
        return noteMapper.selectNote(noteId);
    }

    public List<Note> getNotesByUser(Integer userId) {
        return noteMapper.getNotesByUser(userId);
    }

    public int deleteNote(Integer noteId) {
        return noteMapper.deleteNoteByNoteId(noteId);
    }
}
