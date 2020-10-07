package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    SharedPreferences prefs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        String savedString = prefs.getString("EmailAdress", "");
        EditText et = findViewById(R.id.editText);
        et.setText(savedString);
        String savedString1 = prefs.getString("PassWord", "");
        EditText et1 = findViewById(R.id.editTtext1);
        et1.setText(savedString1);

        Button logIn = findViewById(R.id.button);
        logIn.setOnClickListener(bt -> {
            saveSharedPrefs(et.getText().toString());
            saveSharedPrefs1(et1.getText().toString());
            Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
            goToProfile.putExtra("EMAIL",et.getText().toString());
            startActivity(goToProfile);

        });

        Log.e(ACTIVITY_NAME, "In function: onCreate");




    }

        @Override
        protected void onPause () {


            super.onPause();
            Log.e(ACTIVITY_NAME, "In function: onPause");
        }

    private void saveSharedPrefs(String stringToSave){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("EmailAdress", stringToSave);
        editor.commit();
    }
    private void saveSharedPrefs1 (String stringToSave){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PassWord", stringToSave);
        editor.commit();
    }

}

