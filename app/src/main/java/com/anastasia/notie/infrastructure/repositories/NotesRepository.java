package com.anastasia.notie.infrastructure.repositories;

import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.infrastructure.sources.NotieService;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotesRepository {

    private NotieService notieService;

    public NotesRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://notie-fifnnkcglq-ts.a.run.app/")
                .build();

        notieService = retrofit.create(NotieService.class);
    }

    public Observable<List<Note>> getNotes() {
        return notieService.listNotes();
    }
}
