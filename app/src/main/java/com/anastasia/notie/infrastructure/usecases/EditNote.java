package com.anastasia.notie.infrastructure.usecases;


import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.infrastructure.repositories.NotesRepository;

import io.reactivex.Observable;

public class EditNote {

    private final NotesRepository notesRepository;

    public EditNote() {
        this.notesRepository = new NotesRepository();
    }

    public Observable<Note> execute(Note note) {
        return notesRepository.editNote(note);
    }
}
