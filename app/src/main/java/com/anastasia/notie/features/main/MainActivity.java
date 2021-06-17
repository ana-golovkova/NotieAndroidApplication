package com.anastasia.notie.features.main;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anastasia.notie.R;
import com.anastasia.notie.features.editNote.EditNoteActivity;
import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.infrastructure.repositories.AnalyticsRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainAdapter.Listener {

    private MainViewModel viewModel;
    private RecyclerView content;
    private ProgressBar loading;
    private TextView errorText;
    private Button errorButton;
    private MainAdapter adapter;
    private FloatingActionButton addNoteButton;
    private SwipeRefreshLayout swipeRefreshLayout;

    private AnalyticsRepository analyticsRepository;

    ActivityResultLauncher<EditNoteActivity.EditNoteContract.EditNoteContractParameters> editNote = registerForActivityResult(new EditNoteActivity.EditNoteContract(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    viewModel.load();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analyticsRepository = new AnalyticsRepository();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setContentView(R.layout.activity_main);
        initViews();
        viewModel.getState().observe(this, new Observer<MainViewState>() {
            @Override
            public void onChanged(MainViewState mainViewState) {
                if (mainViewState.isLoading()) {
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
                    adapter.setItems(mainViewState.getContent());
                    content.setAdapter(adapter);
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
                analyticsRepository.recordEvent("AddNewNoteClickedEvent");
                editNote.launch(new EditNoteActivity.EditNoteContract.EditNoteContractParameters(-1, true));
            }
        });
        adapter = new MainAdapter(MainActivity.this);
    }

    @Override
    public void onNoteClicked(Integer noteId) {
        analyticsRepository.recordEvent("NoteClickedEvent");
        editNote.launch(new EditNoteActivity.EditNoteContract.EditNoteContractParameters(noteId, false));
    }
}