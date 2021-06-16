package com.anastasia.notie.features.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.R;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Map<Integer, Note> notesMap = Collections.emptyMap();
    private Listener listener;

    public interface Listener {
        void onNoteClicked(Integer noteId);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.titleText);
        }

        public void bindView(Integer id, Note note, Listener listener) {
            textView.setText(note.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onNoteClicked(id);
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
        if (notesMap.containsKey(index)) {
            viewHolder.bindView(index, notesMap.get(index), listener);
        }
    }

    @Override
    public int getItemCount() {
        return notesMap.size();
    }

    public void setItems(Map<Integer, Note> notes) {
        this.notesMap = notes;
        notifyDataSetChanged();
    }
}
