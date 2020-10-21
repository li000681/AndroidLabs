package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

public class ChatRoomActivity<sendButtonIsClicked> extends AppCompatActivity {
    private ArrayList<Message> elements = new ArrayList<>();
    private MyListAdapter myAdapter;
    private Button sendButton;
    private Button receiveButton;
    private EditText et;

    private class Message{
        private String msg;
        private Boolean sendButtonIsClicked;
        private Long id;
        public Message(Long id,String msg, Boolean sendButtonIsClicked){
            this.msg=msg;
            this.sendButtonIsClicked=sendButtonIsClicked;
            this.id=id;
        }
        public boolean getSendButtonIsClicked(){
            return sendButtonIsClicked;
        }


        public String getMsg() {
            return msg;
        }


    }
    private class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return elements.size();
        }

        @Override
        public Object getItem(int position) {
            return elements.get(position);
        }

        @Override
        public long getItemId(int position) {
            return (long)id;
        }
        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return position % 2;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View newView;

            if (elements.get(position).getSendButtonIsClicked()) {
                newView= inflater.inflate(R.layout.row_layout_send, parent, false);



            }else {
                newView = inflater.inflate(R.layout.row_layout_receive, parent, false);

            }

            TextView tView = newView.findViewById(R.id.msg);
            tView.setText( elements.get(position).getMsg());

            return newView;

        }





    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        et=(EditText)findViewById(R.id.typeText);
        ListView chatList = (ListView) findViewById(R.id.chatList);

        chatList.setAdapter(  myAdapter = new MyListAdapter() );
        chatList.setOnItemClickListener( (parent, view, pos, id) -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle( getResources().getString(R.string.alertTitle))
                    .setMessage( getResources().getString(R.string.alertMessage1)+ elements.get(pos).getMsg()+ getResources().getString(R.string.alertMessage2)+ pos )
                  //  .setView(newView) //add the 3 edit texts showing the contact information

                    .setPositiveButton( getResources().getString(R.string.alertPB), (click, b) -> {

                        elements.remove(pos);
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton( getResources().getString(R.string.alertNB), (click, b) -> { })
                    .create().show();

        }   );
        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(click->{

            Message message= new Message(et.getText().toString(),true);
            elements.add(message);
            et.setText("");
            myAdapter.notifyDataSetChanged();


        });
        receiveButton = (Button) findViewById(R.id.receiveButton);
        receiveButton.setOnClickListener(click->{

            Message message= new Message(et.getText().toString(),false);
            elements.add(message);
            myAdapter.notifyDataSetChanged();

            et.setText("");

        });

    }
    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onStart() {
        super.onStart();

    }
}