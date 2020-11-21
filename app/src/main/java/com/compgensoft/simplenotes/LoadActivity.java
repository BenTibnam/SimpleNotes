package com.compgensoft.simplenotes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoadActivity extends Activity {
    private final String SHARED_PREFS = "sharedPrefs";
    private final String NOTE_NAME_PREF = "loadNoteName";
    private LinearLayout noteScrollView;
    private String buttonClicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_load);
        noteScrollView = (LinearLayout) findViewById(R.id.scrollFile);
        loadNotes();
    }

    public void loadNotes() {
        final String files[] = fileList();
        for(int i = 0; i < files.length; i++){
            String fileName = files[i].substring(0, files[i].indexOf(".simp"));
            Button fileBtn = new Button(this);
            fileBtn.setText(fileName);
            final int currentIndex = i;
            fileBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(NOTE_NAME_PREF, files[currentIndex].substring(0, files[currentIndex].indexOf(".simp")));
                    editor.commit();
                    Toast.makeText(getApplicationContext(), files[currentIndex].substring(0, files[currentIndex].indexOf(".simp")), Toast.LENGTH_SHORT);

                    Intent intent = new Intent(getApplicationContext(), NoteEditActivity.class);
                    startActivity(intent);
                }
            });
            noteScrollView.addView(fileBtn);
        }
    }

    public void onAddBtnClicked(View view){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(NOTE_NAME_PREF, "\\newSimp\\");
        editor.commit();

        Intent intent = new Intent(getApplicationContext(), NoteEditActivity.class);
        startActivity(intent);
    }

    public void onOptionsClicked(View view){
        Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
        startActivity(intent);
    }
}