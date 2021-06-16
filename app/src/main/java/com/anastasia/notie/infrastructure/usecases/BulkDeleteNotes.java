package com.anastasia.notie.infrastructure.usecases;

import com.anastasia.notie.infrastructure.repositories.NotesRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import io.reactivex.Completable;

public class BulkDeleteNotes {
    private final NotesRepository notesRepository;

    public BulkDeleteNotes() {
        this.notesRepository = new NotesRepository();
    }


    public Completable execute(Queue<Integer> noteIds) {
        List<Completable> completableDeletes = new LinkedList<>();
        while (!noteIds.isEmpty()) {
            int nextNoteToDelete = noteIds.poll();
            completableDeletes.add(notesRepository.deleteNote(nextNoteToDelete));
        }
        return Completable.merge(completableDeletes);
    }
}
