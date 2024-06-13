package com.example.mynoteapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class NoteActivity extends AppCompatActivity {

    private EditText editTextNote;
    private Button buttonSave;
    private int notePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        editTextNote = findViewById(R.id.editTextNote);
        buttonSave = findViewById(R.id.buttonSave);

        // Get the existing note content and position from the intent
        String existingNoteContent = getIntent().getStringExtra("note_content");
        notePosition = getIntent().getIntExtra("note_position", -1);

        if (existingNoteContent!= null) {
            editTextNote.setText(existingNoteContent);
        }

        buttonSave.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String noteContent = editTextNote.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("note_content", noteContent);
        intent.putExtra("note_position", notePosition); // Pass the note position
        setResult(RESULT_OK, intent);
        finish();
    }
}