package com.anastasia.notie.infrastructure.usecases;

import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.infrastructure.repositories.NotesRepository;

import io.reactivex.Observable;

public class GetNote {
    private final NotesRepository notesRepository;

    public  GetNote() {
        this.notesRepository = new NotesRepository();
    }

    public Observable<Note> execute(int id) {
        return notesRepository.getNote(id);
    }
}
