package com.anastasia.notie.features.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.anastasia.notie.infrastructure.usecases.BulkDeleteNotes;
import com.anastasia.notie.infrastructure.usecases.GetNotes;
import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.utils.DataLoadingState;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<MainViewState> state;
    private final GetNotes getNotes;
    private final BulkDeleteNotes bulkDeleteNotes;

    public MainViewModel() {
        this.getNotes = new GetNotes();
        this.state = new MutableLiveData<>();
        this.state.setValue(new MainViewState(true, false, null));
        bulkDeleteNotes = new BulkDeleteNotes();
    }

    public LiveData<MainViewState> getState() {
        return state;
    }

    public void load() {
        state.postValue(this.state.getValue().setLoading());
        getNotes.execute().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Map<Integer, Note>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Map<Integer, Note> notes) {
                state.postValue(state.getValue().setContent(new MainViewState.MainViewContent(notes, DataLoadingState.IDLE)));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                state.postValue(state.getValue().setError());
            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void deleteNotesQueue(LinkedList<Integer> notesQueue) {
        state.postValue(state.getValue().setContent(new MainViewState.MainViewContent(state.getValue().getContent().getNotes(), DataLoadingState.LOADING)));
        bulkDeleteNotes.execute(notesQueue).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onComplete() {
                state.postValue(state.getValue().setContent(new MainViewState.MainViewContent(state.getValue().getContent().getNotes(), DataLoadingState.SUCCESS)));
            }

            @Override
            public void onError(@NotNull Throwable e) {
                state.postValue(state.getValue().setContent(new MainViewState.MainViewContent(state.getValue().getContent().getNotes(), DataLoadingState.ERROR)));

            }
        });
    }

    public void bulkDeleteErrorOrSuccessHandled() {
        load();
    }

}
