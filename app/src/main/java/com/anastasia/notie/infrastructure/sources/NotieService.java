package com.anastasia.notie.infrastructure.sources;

import com.anastasia.notie.infrastructure.models.Note;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface NotieService {
    @GET("notes")
    Observable<List<Note>> listNotes();

    @PUT("note")
    Observable<Note> editNote(@Body Note note);

    @GET("note")
    Observable<Note> getNote(@Query("id") int id);
}
