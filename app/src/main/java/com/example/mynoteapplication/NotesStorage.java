package com.example.mynoteapplication;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NotesStorage {

    private static final String PREFS_NAME = "notes_prefs";
    private static final String NOTES_KEY = "notes_key";

    public static void saveNotes(Context context, List<Note> noteList) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> notesSet = new HashSet<>();
        for (Note note : noteList) {
            if (note != null && note.getContent() != null) { // Check for null values
                notesSet.add(note.getContent());
            }
        }
        editor.putStringSet(NOTES_KEY, notesSet);
        editor.apply();
    }

    public static List<Note> loadNotes(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> notesSet = prefs.getStringSet(NOTES_KEY, new HashSet<>());
        List<Note> noteList = new ArrayList<>();
        for (String content : notesSet) {
            if (content != null) { // Check for null values
                noteList.add(new Note(content));
            }
        }
        return noteList;
    }
}



