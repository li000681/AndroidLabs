package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageButton mImageButton;
    private Button chatButton;
    private Button weatherButton;
    private Button toolBarButton;
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        EditText email=findViewById(R.id.email);
        mImageButton= (ImageButton)findViewById(R.id.ibutton);


        mImageButton.setOnClickListener(click -> dispatchTakePictureIntent());

        Intent fromMain = getIntent();
        //fromMain.getStringExtra("EMAIL");
        email.setText( fromMain.getStringExtra("EMAIL"));
        chatButton=findViewById(R.id.chatButton);
        Intent chatPage =new Intent( ProfileActivity.this, ChatRoomActivity.class);

        chatButton.setOnClickListener(click->startActivity(chatPage));
        weatherButton=findViewById(R.id.weatherButton);
        toolBarButton=findViewById(R.id.toolBarButton);
        Intent weatherPage =new Intent( ProfileActivity.this, WeatherForecast.class);

        weatherButton.setOnClickListener(click->startActivity(weatherPage));
        Intent TestToolbar =new Intent( ProfileActivity.this, TestToolbar.class);
        toolBarButton.setOnClickListener(click->startActivityForResult(TestToolbar,));
        Log.e(ACTIVITY_NAME, "In function: onCreate");
    }
        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                mImageButton.setImageBitmap(imageBitmap);
                Log.e(ACTIVITY_NAME, "In function: onActivityResult" );
            }
        }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In function: onResume");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME, "In function: onStart");
    }
    //@Override
   // protected  void onPause(){
    //    super.onPause();

   // }
}