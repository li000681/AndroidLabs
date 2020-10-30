package com.example.androidlabs;

public class Message{
    protected  String msg;
    protected Boolean sendButtonIsClicked;
    protected Long id;
    public Message(Long id,String msg, Boolean sendButtonIsClicked){
        this.msg=msg;
        this.sendButtonIsClicked=sendButtonIsClicked;
        this.id=id;
    }
    public void update(String n, Boolean e)
    {
        msg = n;
        sendButtonIsClicked = e;
    }

    /**Chaining constructor: */
    public Message(String n, Boolean e) { this( 0L, n, e);}



    public long getId() {
        return id;
    }

    public Boolean getSendButtonIsClicked(){

        return sendButtonIsClicked;
    }
    public String booleanToString(Boolean s){
        if(s){
            return "yes";
        }else{
            return "no";
        }
    }
    public boolean stringToBoolean(String s){
        if(s=="yes"){
            return true;
        }else{
            return false;
        }
    }

    public String getMsg() {
        return msg;
    }


}