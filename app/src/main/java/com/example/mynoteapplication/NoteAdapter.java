package com.example.mynoteapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> noteList;
    private OnNoteClickListener onNoteClickListener;
    private OnNoteDeleteClickListener onNoteDeleteClickListener;

    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }

    public interface OnNoteDeleteClickListener {
        void onNoteDeleteClick(Note note);
    }

    public NoteAdapter(List<Note> noteList, OnNoteClickListener onNoteClickListener, OnNoteDeleteClickListener onNoteDeleteClickListener) {
        this.noteList = noteList;
        this.onNoteClickListener = onNoteClickListener;
        this.onNoteDeleteClickListener = onNoteDeleteClickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.textViewNote.setText(note.getContent());
        holder.buttonEdit.setOnClickListener(v -> onNoteClickListener.onNoteClick(note));
        holder.buttonDelete.setOnClickListener(v -> onNoteDeleteClickListener.onNoteDeleteClick(note));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNote;
        Button buttonEdit;
        Button buttonDelete;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNote = itemView.findViewById(R.id.textViewNote);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
