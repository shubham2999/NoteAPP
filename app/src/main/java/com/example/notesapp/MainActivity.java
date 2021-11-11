package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    private ListView noteListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        loadFromDBToMemory();
        setNoteAdapter();
        setOnClickListener();
    }


    private void initWidgets()
    {
        noteListView = findViewById(R.id.noteListView);
    }

    private void loadFromDBToMemory()
    {
        SQliteManager sqLiteManager = SQliteManager.instanceOfDatabase(this);
        sqLiteManager.populateNoteListArray();
    }

    private void setNoteAdapter()
    {
        NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(), Note.nonDeletedNotes());
        noteListView.setAdapter(noteAdapter);
    }


    private void setOnClickListener()
    {
        noteListView.setOnItemClickListener((adapterView, view, position, l) -> {
            Note selectedNote = (Note) noteListView.getItemAtPosition(position);
            Intent editNoteIntent = new Intent(getApplicationContext(), NotesDetailActivity.class);
            editNoteIntent.putExtra(Note.NOTE_EDIT_EXTRA, selectedNote.getId());
            startActivity(editNoteIntent);
        });
    }


    public void newNote(View view)
    {
        Intent newNoteIntent = new Intent(this, NotesDetailActivity.class);
        startActivity(newNoteIntent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setNoteAdapter();
    }
}