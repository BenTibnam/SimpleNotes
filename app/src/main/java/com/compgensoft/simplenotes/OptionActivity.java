package com.compgensoft.simplenotes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class OptionActivity extends Activity {
    private final String SHARED_PREFS = "sharedPrefs";
    private final String STRING_POSITION = "string_position";
    private final String FONT_NAME = "font";
    private final String FONT_SIZE = "fontSize";
    private Spinner fontSpinner;
    private EditText editFontSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_option);
        setSpinner();

        editFontSize = (EditText) findViewById(R.id.editFontSize);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editFontSize.setText(Integer.toString(sharedPreferences.getInt(FONT_SIZE, 12)));
    }

    public void backButtonClicked(View view){
        Intent intent = new Intent(getApplicationContext(), LoadActivity.class);
        startActivity(intent);
    }

    public void setSpinner(){
        fontSpinner = (Spinner) findViewById(R.id.fontSpinner);
        ArrayAdapter<String> fontArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.font_options));
        fontSpinner.setAdapter(fontArrayAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        fontSpinner.setSelection(sharedPreferences.getInt(STRING_POSITION, 0));
    }

    public void saveButtonClicked(View view){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(FONT_NAME, fontSpinner.getSelectedItem().toString());
        editor.putInt(STRING_POSITION, fontSpinner.getSelectedItemPosition());
        editor.putInt(FONT_SIZE, Integer.parseInt(editFontSize.getText().toString()));
        editor.commit();

        Toast.makeText(getApplicationContext(), "Settings Saved", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), LoadActivity.class);
        startActivity(intent);
    }
}