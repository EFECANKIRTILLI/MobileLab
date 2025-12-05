
package com.example.projeadi; 

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends Activity implements
        NoteFragment.OnNoteListInteractionListener {

    boolean displayingEditor = false;
    Note editingNote;
    ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main); 
        
        
        notes = retrieveNotes();
        Log.d("onCreate", "Note Count = " + notes.size());

        if (savedInstanceState == null) {
            
            if (!displayingEditor) {
                
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.container, NoteFragment.newInstance(notes));
                ft.commit();
            } else {
                
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, EditNoteFragment.newInstance(readContent(editingNote)));
                ft.addToBackStack(null);
                ft.commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d("onPrepareOptionsMenu new visible",
                menu.findItem(R.id.action_new).isVisible() + "");
        menu.findItem(R.id.action_new).setVisible(!displayingEditor);
        menu.findItem(R.id.action_close).setVisible(displayingEditor);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("onOptionsItemSelected", item.getTitle().toString());
        
        switch (item.getItemId()) {
            case R.id.action_new:
                editingNote = createNote();
                notes.add(editingNote); 
                
                
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                
                ft.replace(R.id.container, EditNoteFragment.newInstance(""), "edit_note"); 
                ft.addToBackStack(null);
                ft.commit();
                
                displayingEditor = true; 
                invalidateOptionsMenu(); 
                return true;
                
            case R.id.action_close:
                
                onBackPressed();
                return true;
                
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    
    public ArrayList<Note> retrieveNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        File dir = getFilesDir(); 
        File[] files = dir.listFiles(); 
        
        if(files == null) return notes; 
        
        for (File file : files) {
            Log.d("Retrieving", "absolute path = " + file.getAbsolutePath());
            Log.d("Retrieving", "name = " + file.getName());
            
            Note note = new Note();
            note.setFilePath(file.getAbsolutePath());
            note.setDate(new Date(file.lastModified())); 
            
            
            String header =
                    getPreferences(Context.MODE_PRIVATE).getString(file.getName(), "No Header!");
            note.setHeader(header);
            notes.add(note);
        }
        return notes;
    }

    @Override
    public void onBackPressed() {
        
        EditNoteFragment editFragment = (EditNoteFragment)
                getFragmentManager().findFragmentByTag("edit_note");
        if (editFragment != null) {
            String content = editFragment.getContent();
            saveContent(editingNote, content); 
        }
        
        
        super.onBackPressed();
        
        
        
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            displayingEditor = false;
            invalidateOptionsMenu();
        }
    }

    @Override
    public void onNoteSelected(Note note) {
        
        editingNote = note;
        
        
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, EditNoteFragment.newInstance(readContent(editingNote)), "edit_note");
        ft.addToBackStack(null);
        ft.commit();
        
        displayingEditor = true; 
        invalidateOptionsMenu(); 
    }

    
    private Note createNote() {
        Note note = new Note();
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        int next = pref.getInt("next", 1); 
        
        File dir = getFilesDir();
        
        String filePath = dir.getAbsolutePath() + "/note_" + next; 
        Log.d("Create Note with path", filePath);
        note.setFilePath(filePath);
        note.setDate(new Date());

        
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("next", next + 1);
        editor.apply(); 
        return note;
    }

    
    private void saveContent(Note note, String content) {
        note.setDate(new Date()); 
        
        
        String header = content.length() < 30 ? content : content.substring(0, 30);
       
        note.setHeader(header.replaceAll("\n", " ")); 

        FileWriter writer = null;
        File file = new File(note.getFilePath());
        try {
            
            writer = new FileWriter(file);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        
        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
        Log.d("Saving tp Pref", "key = " + file.getName() + " value = " + note.getHeader());
        editor.putString(file.getName(), note.getHeader());
        editor.apply();
    }

    
    private String readContent(Note note) {
        Log.d("Reading Note with path", note.getFilePath());
        StringBuilder content = new StringBuilder(); 
        try (BufferedReader reader = new BufferedReader(new FileReader(new
                File(note.getFilePath())))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n"); 
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ""; 
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return content.toString();
    }
}