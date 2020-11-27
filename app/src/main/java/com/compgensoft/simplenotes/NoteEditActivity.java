package com.compgensoft.simplenotes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class NoteEditActivity extends Activity {
    private String noteName;
    private final String SHARED_PREFS = "sharedPrefs";
    private final String NOTE_NAME_PREF = "loadNoteName";
    private final String FONT_NAME = "font";
    private final String FONT_SIZE = "fontSize";
    private String noteTitle;

    private Button deleteButton;
    private Button saveButton;
    private Button backButton;
    private EditText titleText;
    private EditText contentText;

    private void save(){
        String title = titleText.getText().toString();
        String programExtension = ".simp";
        String content = contentText.getText().toString();
        FileOutputStream fos = null;

        try{
            fos = openFileOutput(title + programExtension, MODE_PRIVATE);
            fos.write(content.getBytes());

            // changing loaded file title preference to title passed in
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(NOTE_NAME_PREF, title);
            editor.commit();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos != null){
                try{
                    fos.close();
                } catch (IOException e) {
                    Toast.makeText(this, "Error occured trying to close the file output stream. File possible not saved.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
    }

    private void load() {
        String title = titleText.getText().toString();
        String programExtension = ".simp";

        // loading the content from the simp file
        FileInputStream fis = null;
        try {
            fis = openFileInput(title + programExtension);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while((text = br.readLine()) != null){
                sb.append(text).append('\n');
            }

            contentText.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis == null){
                try {
                    fis.close();
                    Toast.makeText(this, "Failed reading content", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_note_edit);

        //defining widgets
        deleteButton = (Button) findViewById(R.id.deleteBtn);
        saveButton = (Button) findViewById(R.id.saveBtn);
        backButton = (Button) findViewById(R.id.backBtn);
        titleText = (EditText) findViewById(R.id.titleText);
        contentText = (EditText) findViewById(R.id.editBox);


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        noteName = sharedPreferences.getString(NOTE_NAME_PREF, "");

        contentText.setTypeface(getTypeFace(sharedPreferences.getString(FONT_NAME, "sans")));
        contentText.setTextSize(sharedPreferences.getInt(FONT_SIZE, 12));

        contentText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                saveButton.setBackground(getDrawable(R.drawable.floppydisk));
                return NoteEditActivity.super.onKeyDown(i, keyEvent);
            }
        });



        if(noteName.equals("\\newSimp\\")){
            titleText.setText("");
            titleText.setHint("Untitled");
        }else{
            String title = noteName;
            titleText.setText(title);
            load();
        }


    }

    private Typeface getTypeFace(String string) {
        switch (string){
            case "sans":
                return Typeface.SANS_SERIF;
            case "casual":
                return Typeface.create("casual", Typeface.NORMAL);
            case "monospace":
                return Typeface.MONOSPACE;
            default:
                return Typeface.DEFAULT;
        }
    }

    public void backButtonPressed(View view){
        Intent intent = new Intent(getApplicationContext(), LoadActivity.class);
        startActivity(intent);
    }

    public void saveButtonPressed(View view){
        save();
        Toast.makeText(this, "Saved Note", Toast.LENGTH_SHORT).show();
        saveButton.setBackground(getDrawable(R.drawable.floppydisk));
    }

    public void deleteButtonPressed(View view){
        Intent intent = new Intent(getApplicationContext(), DeleteConfirmationActivity.class);
        startActivity(intent);
    }
}