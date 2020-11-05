package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
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
        req.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");
    }
    private  class ForecastQuery extends AsyncTask<String, Integer, String> {

        @Override
        public String doInBackground(String ... args)
        {
            try {
                String URL = URLEncoder.encode(args[0], "UTF-8");

                //create a URL object of what server to contact:
                URL url = new URL(URL);

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
                String parameter = null;

                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                while(eventType != XmlPullParser.END_DOCUMENT)
                {

                    if(eventType == XmlPullParser.START_TAG)
                    {
                        //If you get here, then you are pointing at a start tag
                        if(xpp.getName().equals("Weather"))
                        {
                            //If you get here, then you are pointing to a <Weather> start tag
                            String outlook = xpp.getAttributeValue(null,    "outlook");
                            String windy = xpp.getAttributeValue(null, "windy");
                        }

                        else if(xpp.getName().equals("AMessage"))
                        {
                            parameter = xpp.getAttributeValue(null, "message"); // this will run for <AMessage message="parameter" >
                        }
                        else if(xpp.getName().equals("Weather"))
                        {
                            parameter = xpp.getAttributeValue(null, "outlook"); //this will run for <Weather outlook="parameter"
                            parameter = xpp.getAttributeValue(null, "windy"); //this will run for <Weather windy="paramter"  >
                        }
                        else if(xpp.getName().equals("Temperature"))
                        {
                            xpp.next(); //move the pointer from the opening tag to the TEXT event
                            parameter = xpp.getText(); // this will return  20
                        }
                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable
                }


            }
            catch (Exception e)
            {

            }

            return "Done";
        }

        //Type 2
        public void onProgressUpdate(Integer ... args)
        {

        }
        //Type3
        public void onPostExecute(String fromDoInBackground)
        {
            Log.i("HTTP", fromDoInBackground);
        }
    }
}