package com.example.mynoteapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class NoteActivity extends AppCompatActivity {

    private EditText editTextNote;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        editTextNote = findViewById(R.id.editTextNote);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String noteContent = editTextNote.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("note_content", noteContent);
        setResult(RESULT_OK, intent);
        finish();
    }
}

