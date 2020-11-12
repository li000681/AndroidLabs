package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WeatherForecast extends AppCompatActivity {


    private ImageView iv;
    private TextView ct;
    private TextView mint;
    private TextView maxt;
    private TextView uvr;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        iv=findViewById(R.id.currentWeather);
        ct=findViewById(R.id.currentTemperature);
        mint=findViewById(R.id.minTemperature);
        maxt=findViewById(R.id.maxTemperature);
        uvr=findViewById(R.id.uvRating);

        pb=findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        ForecastQuery req = new ForecastQuery();
        req.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric","http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");

    }





    private  class ForecastQuery extends AsyncTask<String, Integer, String> {
        //string variables for the UV, min, max, and current temperature
        String uv;
        String min;
        String max;
        String current;
        Bitmap image;

        @Override
        public String doInBackground(String ... args)
        {
            try {
                //String a =args[0]+ URLEncoder.encode(args[1], "UTF-8");

                //create a URL object of what server to contact:
                URL url = new URL(args[0]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();



                //From part 3: slide 19
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response  , "UTF-8"); //response is data from the server



                //From part 3, slide 20
                //String parameter = null;

                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                while(eventType != XmlPullParser.END_DOCUMENT)
                {

                    if(eventType == XmlPullParser.START_TAG)
                    {
                        //If you get here, then you are pointing at a start tag
                        if(xpp.getName().equals("temperature"))
                        {
                            //If you get here, then you are pointing to a <Weather> start tag
                            min = xpp.getAttributeValue(null,    "min");

                            publishProgress(25);
                            max = xpp.getAttributeValue(null, "max");

                            publishProgress(50);

                            current = xpp.getAttributeValue(null, "value");
                            publishProgress(75);

                        }
                        else if(xpp.getName().equals("weather")) {

                            String iconName = xpp.getAttributeValue(null, "icon"); //this will run for <Weather outlook="parameter"
                            if (fileExistance(iconName+ ".png")) {
                                FileInputStream fis = null;
                                try {
                                    fis = openFileInput(iconName+ ".png");
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                image = BitmapFactory.decodeStream(fis);

                                Log.i("images("+iconName+")", " is from local.");

                            } else {
                                try {


                                String urlString = "http://openweathermap.org/img/w/" + iconName + ".png";

                                url = new URL(urlString);
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.connect();
                                int responseCode = connection.getResponseCode();
                                if (responseCode == 200) {
                                    image = BitmapFactory.decodeStream(connection.getInputStream());
                                    Log.i(iconName, " is downloaded.");


                                    FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    outputStream.flush();
                                    outputStream.close();




                                }

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                            publishProgress(100);
                        }
                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable
                }



                //String URL1 = URLEncoder.encode(args[3], "UTF-8");
                try {


                    url = new URL(args[1]);

                    //open the connection
                    urlConnection = (HttpURLConnection) url.openConnection();

                    //wait for data:
                    response = urlConnection.getInputStream();

                    //JSON reading:   Look at slide 26
                    //Build the entire string response:
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    String result = sb.toString(); //result is the whole string


                    // convert string to JSON: Look at slide 27:
                    JSONObject uvReport = new JSONObject(result);

                    //get the double associated with "value"
                    float value = (float) uvReport.getDouble("value");

                    uv = Float.toString(value);

                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            catch (Exception e)
            {
                Log.i(String.valueOf(e),"not connected");
            }

            return "done";
        }


        //Type 2
        public void onProgressUpdate(Integer ... value)
        {
            pb.setVisibility(View.VISIBLE);
            pb.setProgress(value[0]);

        }
        //Type3
        public void onPostExecute(String fromDoInBackground)
        {
            iv.setImageBitmap(image);
            ct.setText("Current temperature: " +current);
            mint.setText("Min temperature: "+min);
            maxt.setText("Max temperature: "+max);
            uvr.setText("UV Rate: "+uv);
            pb.setVisibility(View.INVISIBLE);
        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();   }

    }


}