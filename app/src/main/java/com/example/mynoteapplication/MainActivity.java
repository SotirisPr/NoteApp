package com.example.mynoteapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnNoteClickListener {

    private static final int REQUEST_CODE_NEW_NOTE = 1;
    private static final int REQUEST_CODE_EDIT_NOTE = 2;

    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(noteList, this);
        recyclerView.setAdapter(noteAdapter);

        loadNotes();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            // Start NoteActivity to create a new note
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            startActivityForResult(intent, REQUEST_CODE_NEW_NOTE);
        });
    }

    @Override
    public void onNoteClick(Note note) {
        // Start NoteActivity to edit the selected note
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        intent.putExtra("note_content", note.getContent());
        intent.putExtra("note_position", noteList.indexOf(note)); // Pass note position
        startActivityForResult(intent, REQUEST_CODE_EDIT_NOTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) { // Check if data is not null
            String content = data.getStringExtra("note_content");
            if (requestCode == REQUEST_CODE_NEW_NOTE) {
                // Add new note to the list
                noteList.add(new Note(content));
            } else if (requestCode == REQUEST_CODE_EDIT_NOTE) {
                // Update existing note in the list
                int position = data.getIntExtra("note_position", -1);
                if (position != -1) {
                    noteList.get(position).setContent(content);
                }
            }
            saveNotes();
            noteAdapter.notifyDataSetChanged();
        }
    }

    private void saveNotes() {

        NotesStorage.saveNotes(this, noteList);
    }

    private void loadNotes() {
        // Check if the loaded notes are null
        List<Note> loadedNotes = NotesStorage.loadNotes(this);
        if (loadedNotes != null) {
            noteList.clear();
            noteList.addAll(loadedNotes);
            noteAdapter.notifyDataSetChanged();
        }
    }

}

