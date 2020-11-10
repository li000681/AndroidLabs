package com.example.androidlabs;

import android.graphics.Bitmap;

public class Weather {
    String uv;
    String min;
    String max;
    String current;
    Bitmap image;
    public Weather(String uv,String min,String max,String current,Bitmap image){
        this.uv=uv;
        this.min=min;
        this.max=max;
        this.current=current;
        this.image=image;
    }
    public Weather(){
        this(null,null,null,null,null);
    }
    public void setUv(String s)
    {
        uv=s;
    }
    public void setMin(String s){
        min=s;
    }
    public void setCurrent(String s){
        current=s;
    }
    public void setMax(String s){
        max=s;
    }
    public void setImage(Bitmap i){
        image=i;
    }
    public String getMax(){
        return max;
    }
    public String getMin(){
        return min;
    }
    public String getCurrent(){
        return current;
    }
    public String getUv(){
        return uv;
    }
    public Bitmap getImage(){
        return image;
    }

//    /**Chaining constructor: */
//    public Message(String n, Boolean e) { this( 0L, n, e);}
//
//
//
//    public long getId() {
//        return id;
//    }
//
//    public Boolean getSendButtonIsClicked(){
//
//        return sendButtonIsClicked;
//    }
}
