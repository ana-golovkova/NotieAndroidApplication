package com.anastasia.notie.features.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.R;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    private List<Note> notesList;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;

        public ViewHolder(View view){
            super(view);
            textView = (TextView) view.findViewById(R.id.titleText);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public  MainAdapter(List<Note> notesList) {
        this.notesList = notesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_note, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int index){
        viewHolder.getTextView().setText(notesList.get(index).getTitle());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}
