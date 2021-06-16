package com.anastasia.notie.infrastructure.sources;

import com.anastasia.notie.infrastructure.models.Note;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface NotieService {
    @GET("notes")
    Observable<Map<Integer,Note>> listNotes();

    @PUT("note")
    Observable<Note> editNote(@Body Note note);

    @GET("note")
    Observable<Note> getNote(@Query("id") int id);

    @DELETE("note")
    Completable deleteNode(@Query("id") int id);

    @POST("note")
    Observable<Note> addNote(@Body Note note);

}
