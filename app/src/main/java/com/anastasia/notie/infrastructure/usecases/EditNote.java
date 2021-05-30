package com.anastasia.notie.infrastructure.usecases;

import com.anastasia.notie.infrastructure.repositories.NotesRepository;

public class EditNote {

    private final NotesRepository notesRepository;

    public EditNote() {
        this.notesRepository = new NotesRepository();
    }
}
