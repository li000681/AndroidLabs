package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_linear);
        setContentView(R.layout.activity_main_grid);
        //setContentView(R.layout.activity_main_relative);
        TextView tx= findViewById(R.id.TextView);
        Button bt =findViewById(R.id.button);
        bt.setOnClickListener( v -> Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_message) , Toast.LENGTH_LONG).show());


        CheckBox checkBox=findViewById(R.id.checkbox);

        ImageButton ib= findViewById(R.id.imageButton);
        Switch sw = findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener((cb,b)->{if(b)
        {Snackbar.make(sw,getResources().getString(R.string.switch_on),Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.undo), click -> sw.setChecked(!b)).show();}else{
            Snackbar.make(sw,getResources().getString(R.string.switch_off),Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.undo), click -> sw.setChecked(!b)).show();
        }});


    }
}