package com.anastasia.notie;

import java.util.List;

public class GetNotes {
    private final NotesRepository notesRepository;

    public GetNotes() {
        this.notesRepository = new NotesRepository();
    }

    public List<Note> execute() {
        return notesRepository.getNotes();
    }
}
