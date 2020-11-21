package com.compgensoft.simplenotes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class DeleteConfirmationActivity extends Activity {
    private final String SHARED_PREFS = "sharedPrefs";
    private final String NOTE_NAME_PREF = "loadNoteName";
    private TextView confirmDeleteText;
    private Button yesBtn;
    private Button noBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_delete_confirmation);

        confirmDeleteText = (TextView) findViewById(R.id.deleteText);
        yesBtn = (Button) findViewById(R.id.yesBtn);
        noBtn = (Button) findViewById(R.id.noBtn);

        init();
    }

    private void init(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        final String fileName = sharedPreferences.getString(NOTE_NAME_PREF, "");

        confirmDeleteText.setText("Are you sure you want to delete " + fileName);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // deleting the file
                String fileExtension = ".simp";
                deleteFile(fileName + fileExtension);

                // loading load intent
                Intent intent = new Intent(getApplicationContext(), LoadActivity.class);
                startActivity(intent);
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoteEditActivity.class);
                startActivity(intent);
            }
        });
    }
}