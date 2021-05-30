package com.anastasia.notie.infrastructure.sources;

import com.anastasia.notie.infrastructure.models.Note;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface NotieService {
    @GET("notes")
    Observable<List<Note>> listNotes();

}
