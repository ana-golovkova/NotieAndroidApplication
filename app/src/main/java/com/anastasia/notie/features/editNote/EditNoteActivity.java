package com.anastasia.notie.features.editNote;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anastasia.notie.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class EditNoteActivity extends AppCompatActivity {
    public static final String ID_PARAMETER = "id_parameter";
    public static final String NEW_NOTE_PARAMETER = "new_note_parameter";
    private EditNoteViewModel viewModel;
    private EditText title;
    private EditText body;
    private Button updateNoteButton;
    private ProgressBar loading;
    private TextView errorText;
    private Button errorButton;
    private ProgressBar noteUpdateProgressBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                viewModel.deleteNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(EditNoteViewModel.class);
        setContentView(R.layout.activity_edit_note);
        initViews();
        viewModel.getState().observe(this, new Observer<EditNoteState>() {
            @Override
            public void onChanged(EditNoteState editNoteState) {
                if (editNoteState.isLoading()) {
                    title.setVisibility(View.GONE);
                    body.setVisibility(View.GONE);
                    updateNoteButton.setVisibility(View.GONE);
                    errorText.setVisibility(View.GONE);
                    errorButton.setVisibility(View.GONE);
                    noteUpdateProgressBar.setVisibility(View.GONE);
                    loading.setVisibility(View.VISIBLE);
                }

                if (editNoteState.isError()) {
                    title.setVisibility(View.GONE);
                    body.setVisibility(View.GONE);
                    updateNoteButton.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                    noteUpdateProgressBar.setVisibility(View.GONE);
                    errorText.setVisibility(View.VISIBLE);
                    errorButton.setVisibility(View.VISIBLE);
                }

                if (editNoteState.getContent() != null) {
                    handleEditNoteActionState(editNoteState.getContent().getEditNoteActionState());
                    if (editNoteState.getContent().getEditNoteActionState().getDataLoadingState() == EditNoteState.DataLoadingState.IDLE) {
                        title.setText(editNoteState.getContent().getNote().getTitle());
                        body.setText(editNoteState.getContent().getNote().getContent());
                    }
                }

            }
        });
        viewModel.load(getIntent().getBooleanExtra(NEW_NOTE_PARAMETER,false),getIntent().getIntExtra(ID_PARAMETER, -1));

    }

    private void initViews() {
        title = findViewById(R.id.titleText);
        body = findViewById(R.id.bodyText);
        updateNoteButton = findViewById(R.id.updateNote);
        updateNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.updateNote(title.getText().toString(), body.getText().toString());
            }
        });
        loading = findViewById(R.id.loading);
        errorText = findViewById(R.id.errorText);
        errorButton = findViewById(R.id.retry);
        noteUpdateProgressBar = findViewById(R.id.noteUpdateProgressBar);
    }

    public static class EditNoteContract extends ActivityResultContract<EditNoteContract.EditNoteContractParameters, Boolean> {

        public static class EditNoteContractParameters {
            private int id;
            private boolean isNewNote;

            public EditNoteContractParameters(int id, boolean isNewNote) {
                this.id = id;
                this.isNewNote = isNewNote;
            }

            public int getId() {
                return id;
            }

            public boolean isNewNote() {
                return isNewNote;
            }
        }

        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, EditNoteContractParameters input) {
            Intent intent = new Intent(context, EditNoteActivity.class);
            intent.putExtra(EditNoteActivity.ID_PARAMETER, input.id);
            intent.putExtra(EditNoteActivity.NEW_NOTE_PARAMETER, input.isNewNote);
            return intent;
        }

        @Override
        public Boolean parseResult(int resultCode, @Nullable Intent intent) {
            return resultCode == Activity.RESULT_OK;
        }
    }

    private void handleEditNoteActionState(EditNoteState.EditNoteActionState editNoteActionState) {
        switch (editNoteActionState.getDataLoadingState()) {
            case IDLE:
                loading.setVisibility(View.GONE);
                errorText.setVisibility(View.GONE);
                errorButton.setVisibility(View.GONE);
                noteUpdateProgressBar.setVisibility(View.GONE);
                title.setVisibility(View.VISIBLE);
                title.setEnabled(true);
                body.setVisibility(View.VISIBLE);
                body.setEnabled(true);
                updateNoteButton.setVisibility(View.VISIBLE);
                updateNoteButton.setEnabled(true);
                break;
            case ERROR:
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(EditNoteActivity.this);
                builder.setTitle(getErrorTextTitle(editNoteActionState.getActionType())).setMessage(getErrorTextMessage(editNoteActionState.getActionType()))
                        .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                switch(editNoteActionState.getActionType()){
                                    case EDIT:
                                        viewModel.updateNote(title.getText().toString(), body.getText().toString());
                                        break;
                                    case DELETE:
                                        viewModel.deleteNote();
                                        break;
                                    case ADD:
                                        //TODO: viewModel.addNote
                                        break;
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                viewModel.errorDismissed();
                            }
                        });
                builder.create().show();
                break;
            case LOADING:
                loading.setVisibility(View.GONE);
                errorText.setVisibility(View.GONE);
                errorButton.setVisibility(View.GONE);
                title.setVisibility(View.VISIBLE);
                title.setEnabled(false);
                body.setVisibility(View.VISIBLE);
                body.setEnabled(false);
                updateNoteButton.setVisibility(View.VISIBLE);
                updateNoteButton.setEnabled(false);
                noteUpdateProgressBar.setVisibility(View.VISIBLE);
                break;
            case SUCCESS:
                Toast.makeText(EditNoteActivity.this, getSuccessText(editNoteActionState.getActionType()), Toast.LENGTH_LONG).show();
                setResult(Activity.RESULT_OK);
                finish();
                break;
        }
    }

    private String getSuccessText(EditNoteState.ActionType actionType) {
        switch(actionType){
            case ADD:
                return "Note added successfully";
            case EDIT:
                return "Note updated successfully";
            case DELETE:
                return "Note deleted successfully";
        }

        return null;
    }

    private String getErrorTextTitle(EditNoteState.ActionType actionType) {
        switch(actionType){
            case ADD:
                return "Error creating note";
            case EDIT:
                return "Error updating note";
            case DELETE:
                return "Error deleting note";
        }

        return null;
    }

    private String getErrorTextMessage(EditNoteState.ActionType actionType) {
        switch(actionType){
            case ADD:
                return "Something went wrong creating the note";
            case EDIT:
                return "Something went wrong updating the note";
            case DELETE:
                return "Something went wrong deleting the note";
        }

        return null;
    }
}
