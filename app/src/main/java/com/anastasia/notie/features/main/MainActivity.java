package com.anastasia.notie.features.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anastasia.notie.R;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private RecyclerView content;
    private ProgressBar loading;
    private TextView errorText;
    private Button errorButton;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setContentView(R.layout.activity_main);
        initViews();
        viewModel.getState().observe(this, new Observer<MainViewState>() {
            @Override
            public void onChanged(MainViewState mainViewState) {
                if (mainViewState.isLoading()) {
                    content.setVisibility(View.GONE);
                    errorText.setVisibility(View.GONE);
                    errorButton.setVisibility(View.GONE);
                    loading.setVisibility(View.VISIBLE);
                }
                if (mainViewState.isError()) {
                    content.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                    errorText.setVisibility(View.VISIBLE);
                    errorButton.setVisibility(View.VISIBLE);
                }
                if (mainViewState.getContent() != null) {
                    loading.setVisibility(View.GONE);
                    errorText.setVisibility(View.GONE);
                    errorButton.setVisibility(View.GONE);
                    content.setVisibility(View.VISIBLE);
                    adapter = new MainAdapter(mainViewState.getContent());
                    content.setAdapter(adapter);
                }
            }
        });
        viewModel.load();
    }

    private void initViews(){
        content = findViewById(R.id.content);
        loading = findViewById(R.id.loading);
        errorText = findViewById(R.id.errorText);
        errorButton = findViewById(R.id.retry);
        errorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.load();
            }
        });
    }
}