package com.anastasia.notie.infrastructure.usecases;

import com.anastasia.notie.infrastructure.repositories.NotesRepository;

import io.reactivex.Completable;

public class DeleteNote {
    private final NotesRepository notesRepository;

    public DeleteNote() {
        this.notesRepository = new NotesRepository();
    }

    public Completable execute(int id) {
        return notesRepository.deleteNote(id);
    }
}
