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
    private EditNoteViewModel viewModel;
    private EditText title;
    private EditText body;
    private Button updateNoteButton;
    private ProgressBar loading;
    private TextView errorText;
    private Button errorButton;
    private ProgressBar noteUpdateProgressBar;

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
                    switch (editNoteState.getContent().getNoteUpdateState()) {
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
                            title.setText(editNoteState.getContent().getNote().getTitle());
                            body.setText(editNoteState.getContent().getNote().getContent());
                            break;
                        case ERROR:
                            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(EditNoteActivity.this);
                            builder.setTitle("Error updating note").setMessage("Something went wrong updating the note")
                                    .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int id) {
                                            viewModel.updateNote(title.getText().toString(), body.getText().toString());
                                        }
                                    })
                                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            viewModel.editNoteErrorDismissed();
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
                            Toast.makeText(EditNoteActivity.this, "Note updated successfully", Toast.LENGTH_LONG).show();
                            setResult(Activity.RESULT_OK);
                            finish();
                            break;


                    }
                }

            }
        });
        viewModel.load(getIntent().getIntExtra(ID_PARAMETER, -1));

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

    public static class EditNoteContract extends ActivityResultContract<Integer, Boolean> {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Integer input) {
            Intent intent = new Intent(context, EditNoteActivity.class);
            intent.putExtra(EditNoteActivity.ID_PARAMETER, input);
            return intent;
        }

        @Override
        public Boolean parseResult(int resultCode, @Nullable Intent intent) {
            return resultCode == Activity.RESULT_OK;
        }
    }
}
