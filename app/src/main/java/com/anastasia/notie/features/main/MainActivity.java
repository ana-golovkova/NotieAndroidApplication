package com.anastasia.notie.features.main;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anastasia.notie.R;
import com.anastasia.notie.features.editNote.EditNoteActivity;
import com.anastasia.notie.infrastructure.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements MainAdapter.Listener {

    private MainViewModel viewModel;
    private RecyclerView content;
    private ProgressBar loading;
    private TextView errorText;
    private Button errorButton;
    private MainAdapter adapter;
    private FloatingActionButton addNoteButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog deleteProgressDialog;

    private LinkedList<Integer> selectedNotesForDeletionQueue = new LinkedList<>();

    ActivityResultLauncher<EditNoteActivity.EditNoteContract.EditNoteContractParameters> editNote = registerForActivityResult(new EditNoteActivity.EditNoteContract(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
                        viewModel.load();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        initViews();
        viewModel.getState().observe(this, new Observer<MainViewState>() {
            @Override
            public void onChanged(MainViewState mainViewState) {
                if (mainViewState.isLoading()) {
                    selectedNotesForDeletionQueue = new LinkedList<>();
                    swipeRefreshLayout.setVisibility(View.GONE);
                    errorText.setVisibility(View.GONE);
                    errorButton.setVisibility(View.GONE);
                    addNoteButton.setVisibility(View.GONE);
                    loading.setVisibility(View.VISIBLE);
                }
                if (mainViewState.isError()) {
                    swipeRefreshLayout.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                    addNoteButton.setVisibility(View.GONE);
                    errorText.setVisibility(View.VISIBLE);
                    errorButton.setVisibility(View.VISIBLE);
                }
                if (mainViewState.getContent() != null) {
                    loading.setVisibility(View.GONE);
                    errorText.setVisibility(View.GONE);
                    errorButton.setVisibility(View.GONE);
                    addNoteButton.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                    adapter.setItems(mainViewState.getContent().getNotes());
                    content.setAdapter(adapter);
                    switch (mainViewState.getContent().getDeleteNotesState()) {
                        case IDLE:
                            if (deleteProgressDialog != null) {
                                deleteProgressDialog.dismiss();
                            }
                            break;
                        case SUCCESS:
                            deleteProgressDialog.dismiss();
                            Snackbar.make(content, "Successfully deleted notes!", Snackbar.LENGTH_SHORT).show();
                            viewModel.bulkDeleteErrorOrSuccessHandled();
                            break;
                        case ERROR:
                            deleteProgressDialog.dismiss();
                            Snackbar.make(content, "There was an error deleting notes", Snackbar.LENGTH_SHORT).show();
                            viewModel.bulkDeleteErrorOrSuccessHandled();
                            break;
                        case LOADING:
                            deleteProgressDialog = ProgressDialog.show(MainActivity.this, "Deleting selected notes", "It should be over soon :)");
                            break;
                    }
                }
            }
        });
        viewModel.load();
    }

    private void initViews() {
        content = findViewById(R.id.content);
        loading = findViewById(R.id.loading);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.load();
            }
        });
        errorText = findViewById(R.id.errorText);
        errorButton = findViewById(R.id.retry);
        errorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.load();
            }
        });
        addNoteButton = findViewById(R.id.addNote);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNote.launch(new EditNoteActivity.EditNoteContract.EditNoteContractParameters(-1, true));
            }
        });
        adapter = new MainAdapter(MainActivity.this);
    }


    @Override
    public void onNoteClicked(Integer noteId) {
        editNote.launch(new EditNoteActivity.EditNoteContract.EditNoteContractParameters(noteId, false));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.delete);
        item.setVisible(!selectedNotesForDeletionQueue.isEmpty());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                viewModel.deleteNotesQueue(selectedNotesForDeletionQueue);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onNoteChecked(Integer id, boolean checked) {
        if (checked) {
            selectedNotesForDeletionQueue.remove(id);
            selectedNotesForDeletionQueue.push(id);
        } else {
            selectedNotesForDeletionQueue.remove(id);
        }
        invalidateOptionsMenu();
    }
}