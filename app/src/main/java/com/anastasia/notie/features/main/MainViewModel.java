package com.anastasia.notie.features.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.anastasia.notie.infrastructure.usecases.GetNotes;
import com.anastasia.notie.infrastructure.models.Note;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private final MutableLiveData<MainViewState> state;
    private final GetNotes getNotes;

    @Inject
    public MainViewModel(GetNotes getNotes) {
        this.getNotes = getNotes;
        this.state = new MutableLiveData<>();
        this.state.setValue(new MainViewState(true, false, null));
    }

    public LiveData<MainViewState> getState() {
        return state;
    }

    public void load() {
        state.postValue(this.state.getValue().setLoading());
        getNotes.execute().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Map<Integer,Note>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Map<Integer,Note> notes) {
                state.postValue(state.getValue().setContent(notes));
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
}
