package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    private ArrayList<String> elements = new ArrayList<>();
    private MyListAdapter myAdapter;
    private Button sendButton;
    private Button receiveButton;
    private EditText et;
    public boolean sendButtonIsClicked(){
        return true;
    }
    public boolean receiveButtonIsClicked(){
        return true;
    }
    private class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return elements.size();
        }

        @Override
        public Object getItem(int position) {
            return "This is row " + position;
        }

        @Override
        public long getItemId(int position) {
            return (long)position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();


            if (sendButtonIsClicked()) {
                View newView = inflater.inflate(R.layout.row_layout_send, parent, false);
                TextView tView = newView.findViewById(R.id.sendText);
                tView.setText(et.getText().toString());
                et.setText("");
                return newView;

            }

            if (receiveButtonIsClicked()) {
                View newView = inflater.inflate(R.layout.row_layout_receive, parent, false);
                TextView tView = newView.findViewById(R.id.receiveText);
                tView.setText(et.getText().toString());
                et.setText("");
                return newView;
            }
            return null;

        }





    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ListView chatList = (ListView) findViewById(R.id.chatList);

        chatList.setAdapter(  myAdapter = new MyListAdapter() );
        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(click->{
            elements.add(et.getText().toString());
            myAdapter.notifyDataSetChanged();
            sendButtonIsClicked();

        });
        receiveButton = (Button) findViewById(R.id.receiveButton);
        receiveButton.setOnClickListener(click->{
            elements.add(et.getText().toString());
            myAdapter.notifyDataSetChanged();
            receiveButtonIsClicked();

        });
        et=(EditText)findViewById(R.id.typeText);
    }
}