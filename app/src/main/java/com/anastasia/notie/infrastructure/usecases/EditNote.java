package com.anastasia.notie.infrastructure.usecases;


import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.infrastructure.repositories.NotesRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class EditNote {

    private final NotesRepository notesRepository;

    @Inject
    public EditNote(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    public Observable<Note> execute(Note note) {
        return notesRepository.editNote(note);
    }
}
