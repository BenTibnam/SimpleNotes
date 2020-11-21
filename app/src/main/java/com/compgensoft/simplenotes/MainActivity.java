package com.compgensoft.simplenotes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

import com.compgensoft.simplenotes.LoadActivity;
import com.compgensoft.simplenotes.R;

public class MainActivity extends Activity {
    private final String SHARED_PREFS = "sharedPrefs";
    private final String NOTE_NAME_PREF = "loadNoteName";
    private final String FONT_NAME = "font";
    private final String FONT_SIZE = "fontSize";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(getApplicationContext(), LoadActivity.class);
        startActivity(intent);

        finish();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        if(sharedPreferences.getInt(FONT_SIZE, -1) == -1){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(FONT_SIZE, 12);
            editor.commit();
        }
    }
}