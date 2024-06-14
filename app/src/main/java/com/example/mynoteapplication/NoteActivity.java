package com.example.mynoteapplication;
import static android.service.controls.ControlsProviderService.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentifier;

import java.util.Locale;

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
        if (existingNoteContent != null) {
            editTextNote.setText(existingNoteContent);
            identifyLanguage(existingNoteContent);
        }
        buttonSave.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String noteContent = editTextNote.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("note_content", noteContent);
        intent.putExtra("note_position", notePosition); // Pass the note position
        identifyLanguage(noteContent); // Identify language on note save
        setResult(RESULT_OK, intent);
        finish();
    }

    private void identifyLanguage(String text) {
        LanguageIdentifier languageIdentifier = LanguageIdentification.getClient();
        languageIdentifier.identifyLanguage(text)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@Nullable String languageCode) {
                                if (languageCode.equals("und")) {
                                    Log.i(TAG, "Can't identify language.");
                                    Toast.makeText( NoteActivity.this, "Can't identify language.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.i(TAG, "Language: " + languageCode);
                                    editTextNote.setImeHintLocales(new LocaleList(new Locale(languageCode)));
                                }}
                            })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be loaded or other internal error.
                                // ...
                            }
                        });

    }
}