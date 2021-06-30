package com.anastasia.notie.infrastructure.repositories;

import com.anastasia.notie.NotieApplication;
import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.infrastructure.sources.NotieService;
import com.chuckerteam.chucker.api.ChuckerInterceptor;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotesRepository {

    private NotieService notieService;

    @Inject
    public NotesRepository(NotieService notieService) {
        this.notieService = notieService;
    }

    public Observable<Map<Integer,Note>> getNotes() {
        return notieService.listNotes();
    }

    public Observable<Note> editNote(Note note) {
        return notieService.editNote(note);
    }

    public Observable<Note> getNote(int id) {
        return notieService.getNote(id);
    }

    public Completable deleteNode(int id) {
        return notieService.deleteNode(id);
    }

    public Observable<Note> addNote(Note note) {
        return notieService.addNote(note);
    }
}
