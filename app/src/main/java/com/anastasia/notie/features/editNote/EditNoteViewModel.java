package com.anastasia.notie.features.editNote;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.infrastructure.usecases.EditNote;
import com.anastasia.notie.infrastructure.usecases.GetNote;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EditNoteViewModel extends ViewModel {
    private final MutableLiveData<EditNoteState> state;
    private final GetNote getNote;
    private final EditNote editNote;

    public EditNoteViewModel() {
        this.editNote = new EditNote();
        this.getNote = new GetNote();
        this.state = new MutableLiveData<>();
        this.state.setValue(new EditNoteState(true, false, null));
    }

    public LiveData<EditNoteState> getState() {
        return state;
    }

    public void load(int id) {
        state.postValue(this.state.getValue().setLoading());
        getNote.execute(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Note>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Note note) {
                state.postValue(state.getValue().setContent(new EditNoteState.EditNoteContent(note, EditNoteState.DataLoadingState.IDLE)));
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

    public void updateNote(String title, String body) {
        Note currentNote = this.state.getValue().getContent().getNote();
        Note newNote = new Note(currentNote.getId(), title, body);
        state.postValue(this.state.getValue().setContent(new EditNoteState.EditNoteContent(newNote, EditNoteState.DataLoadingState.LOADING)));
        editNote.execute(newNote).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Note>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Note note) {
                state.postValue(state.getValue().setContent(new EditNoteState.EditNoteContent(newNote, EditNoteState.DataLoadingState.SUCCESS)));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                state.postValue(state.getValue().setContent(new EditNoteState.EditNoteContent(newNote, EditNoteState.DataLoadingState.ERROR)));
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void editNoteErrorDismissed() {
        state.postValue(state.getValue().
                setContent(new EditNoteState.EditNoteContent(state.getValue().getContent().getNote(), EditNoteState.DataLoadingState.IDLE)));
    }
}
