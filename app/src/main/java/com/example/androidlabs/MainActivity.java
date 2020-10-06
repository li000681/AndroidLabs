package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {


    SharedPreferences prefs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_main_grid);
        //setContentView(R.layout.activity_main_relative);

        EditText et1= findViewById(R.id.editTtext1);
        Button bt =findViewById(R.id.button);

        @Override
        protected void onPause() {
            super.onPause();
            prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);

            String savedString = prefs.getString("EmailAdress", "getResourse().getString");
            EditText et= findViewById(R.id.editText);
            et.setText(savedString);

            Button saveButton = findViewById(R.id.saveButton);

            saveButton.setOnClickListener(bt -> saveSharedPrefs(typeField.getText().toString()));
        }

        private void saveSharedPrefs(String stringToSave) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("EmailAdress", stringToSave);
            editor.commit();
        }
        }



    }
}