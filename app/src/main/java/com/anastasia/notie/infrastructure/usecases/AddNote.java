package com.anastasia.notie.infrastructure.usecases;

import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.infrastructure.repositories.NotesRepository;

import io.reactivex.Observable;

public class AddNote {
    private final NotesRepository notesRepository;

    public AddNote() {
        this.notesRepository = new NotesRepository();
    }

    public Observable<Note> execute(Note note){
        return notesRepository.addNote(note);
    }
}
