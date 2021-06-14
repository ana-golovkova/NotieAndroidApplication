package com.anastasia.notie.features.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.R;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    private List<Note> notesList;
    private Listener listener;

    public interface Listener{
        void onNoteClicked(Note note);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;

        public ViewHolder(View view){
            super(view);
            textView = (TextView) view.findViewById(R.id.titleText);
        }

        public void bindView(Note note, Listener listener) {
            textView.setText(note.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onNoteClicked(note);
                }
            });
        }

    }

    public  MainAdapter(List<Note> notesList, Listener listener) {
        this.notesList = notesList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_note, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int index){
        viewHolder.bindView(notesList.get(index), listener);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void setItems(List<Note> notes) {
        this.notesList = notes;
        notifyDataSetChanged();
    }
}
