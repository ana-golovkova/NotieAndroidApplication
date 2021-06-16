package com.anastasia.notie.features.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.R;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Note> notesList = Collections.emptyList();
    private Listener listener;

    public interface Listener {
        void onNoteClicked(Integer noteId);

        void onNoteChecked(Integer id, boolean checked);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.titleText);
            checkBox = view.findViewById(R.id.checkBox);
        }

        public void bindView(Note note, Listener listener) {
            textView.setText(note.getTitle());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    listener.onNoteChecked(note.getId(), isChecked);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onNoteClicked(note.getId());
                }
            });
        }

    }

    public MainAdapter(Listener listener) {
        this.listener = listener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_note, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder viewHolder, final int index) {
        viewHolder.bindView(notesList.get(index), listener);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void setItems(Map<Integer, Note> notes) {
        this.notesList = new ArrayList<>(notes.values());
        notifyDataSetChanged();
    }
}
