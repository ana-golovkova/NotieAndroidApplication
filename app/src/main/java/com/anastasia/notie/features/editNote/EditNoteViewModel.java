package com.anastasia.notie.features.editNote;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anastasia.notie.features.editNote.EditNoteState.ActionType;
import com.anastasia.notie.features.editNote.EditNoteState.EditNoteActionState;
import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.infrastructure.usecases.AddNote;
import com.anastasia.notie.infrastructure.usecases.DeleteNote;
import com.anastasia.notie.infrastructure.usecases.EditNote;
import com.anastasia.notie.infrastructure.usecases.GetNote;
import com.anastasia.notie.utils.DataLoadingState;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EditNoteViewModel extends ViewModel {
    private static final String TAG = "EditNoteViewModel";

    private final MutableLiveData<EditNoteState> state;
    private final GetNote getNote;
    private final EditNote editNote;
    private final DeleteNote deleteNote;
    private final AddNote addNote;

    public EditNoteViewModel() {
        this.editNote = new EditNote();
        this.getNote = new GetNote();
        this.deleteNote = new DeleteNote();
        this.addNote = new AddNote();
        this.state = new MutableLiveData<>();
        this.state.setValue(new EditNoteState(true, false, null));
    }

    public LiveData<EditNoteState> getState() {
        return state;
    }

    public void load(boolean newNote, int id) {
        if (!newNote) {
            state.postValue(this.state.getValue().setLoading());
            getNote.execute(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Note>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull Note note) {
                    state.postValue(state.getValue().setContent(new EditNoteState.EditNoteContent(note, new EditNoteActionState(DataLoadingState.IDLE, ActionType.EDIT))));
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    state.postValue(state.getValue().setError());
                }

                @Override
                public void onComplete() {

                }
            });
        } else {
            this.state.setValue(new EditNoteState(false, false, new EditNoteState.EditNoteContent(new Note(null, "", ""), new EditNoteActionState(DataLoadingState.IDLE, ActionType.ADD))));
        }
    }

    public void updateNote(String title, String body) {
        Note currentNote = this.state.getValue().getContent().getNote();
        Note newNote = new Note(currentNote.getId(), title, body);
        state.postValue(this.state.getValue().setContent(new EditNoteState.EditNoteContent(newNote, new EditNoteActionState(DataLoadingState.LOADING, ActionType.EDIT))));
        editNote.execute(newNote).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Note>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Note note) {
                state.postValue(state.getValue().setContent(new EditNoteState.EditNoteContent(newNote, new EditNoteActionState(DataLoadingState.SUCCESS, ActionType.EDIT))));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                state.postValue(state.getValue().setContent(new EditNoteState.EditNoteContent(newNote, new EditNoteActionState(DataLoadingState.ERROR, ActionType.EDIT))));
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void deleteNote() {
        Note currentNote = this.state.getValue().getContent().getNote();
        state.postValue(this.state.getValue().setContent(new EditNoteState.EditNoteContent(currentNote, new EditNoteActionState(DataLoadingState.LOADING, ActionType.DELETE))));

        deleteNote.execute(state.getValue().getContent().getNote().getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError: error deleting", e);
                state.postValue(state.getValue().setContent(new EditNoteState.EditNoteContent(currentNote, new EditNoteActionState(DataLoadingState.ERROR, ActionType.DELETE))));
            }

            @Override
            public void onComplete() {
                state.postValue(state.getValue().setContent(new EditNoteState.EditNoteContent(currentNote, new EditNoteActionState(DataLoadingState.SUCCESS, ActionType.DELETE))));
            }
        });
    }

    public void addNote(String title, String body) {
        Note newNote = new Note(null, title, body);
        state.postValue(this.state.getValue().setContent(new EditNoteState.EditNoteContent(newNote, new EditNoteActionState(DataLoadingState.LOADING, ActionType.ADD))));
        addNote.execute(newNote).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Note>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Note note) {
                state.postValue(state.getValue().setContent(new EditNoteState.EditNoteContent(newNote, new EditNoteActionState(DataLoadingState.SUCCESS, ActionType.ADD))));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                state.postValue(state.getValue().setContent(new EditNoteState.EditNoteContent(newNote, new EditNoteActionState(DataLoadingState.ERROR, ActionType.ADD))));
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void errorDismissed() {
        state.postValue(state.getValue().
                setContent(new EditNoteState.EditNoteContent(state.getValue().getContent().getNote(), new EditNoteActionState(DataLoadingState.IDLE, ActionType.EDIT))));
    }

}
