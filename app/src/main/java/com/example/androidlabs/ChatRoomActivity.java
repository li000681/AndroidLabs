package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    SQLiteDatabase db;
    private void loadDataFromDatabase()
    {
        //get a database connection:
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase(); //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer


        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String [] columns = {MyOpener.COL_ID, MyOpener.COL_MESSAGES, String.valueOf(MyOpener.COL_ISSENT)};
        //query all the results from the database:
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:
        int emailColumnIndex = results.getColumnIndex(MyOpener.COL_MESSAGES);
        int nameColIndex = results.getColumnIndex(String.valueOf(MyOpener.COL_ISSENT));
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String msg = results.getString(nameColIndex);
            Boolean sendButtonIsClicked = results.getInt(emailColumnIndex)>0;
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            elements.add(new Message(id, msg, sendButtonIsClicked));
        }

        //At this point, the contactsList array has loaded every row from the cursor.
    }
    protected void showMessage(int position)
    {
        Message selectedMessage = elements.get(position);

        View contact_view = getLayoutInflater().inflate(R.layout.message_edit, null);
        //get the TextViews
        EditText rowName = contact_view.findViewById(R.id.row_name);
        EditText rowEmail = contact_view.findViewById(R.id.row_email);
        TextView rowId = contact_view.findViewById(R.id.row_id);

        //set the fields for the alert dialog
        rowName.setText(selectedMessage.getMsg());
        rowEmail.setText(selectedMessage.booleanToString(selectedMessage.getSendButtonIsClicked()));
        rowId.setText("id:" + selectedMessage.getId());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You clicked on item #" + position)
                .setMessage("You can update the fields and then click update to save in the database")
                .setView(contact_view) //add the 3 edit texts showing the contact information
                .setPositiveButton("Update", (click, b) -> {
                    selectedMessage.update(rowName.getText().toString(), selectedMessage.stringToBoolean(rowEmail.getText().toString()));
                    updateMessage(selectedMessage);
                    myAdapter.notifyDataSetChanged(); //the email and name have changed so rebuild the list
                })
                .setNegativeButton("Delete", (click, b) -> {
                    deleteMessage(selectedMessage); //remove the contact from database
                    elements.remove(position); //remove the contact from contact list
                    myAdapter.notifyDataSetChanged(); //there is one less item so update the list
                })
                .setNeutralButton("dismiss", (click, b) -> { })
                .create().show();
    }
    protected void updateMessage(Message c)
    {
        //Create a ContentValues object to represent a database row:
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(MyOpener.COL_MESSAGES, c.getMsg());
        updatedValues.put(String.valueOf(MyOpener.COL_ISSENT), c.getSendButtonIsClicked());

        //now call the update function:
        db.update(MyOpener.TABLE_NAME, updatedValues, MyOpener.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
    }

    protected void deleteMessage(Message c)
    {
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
    }
    protected void printCursor( Cursor c, int version){

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
            return elements.get(position).getId();
        }
//        @Override
//        public int getViewTypeCount() {
//            return 2;
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            return position % 2;
//        }
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
        loadDataFromDatabase(); //get any previously saved Contact objects
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
            String msg = et.getText().toString();
            //add to the database and get the new ID
            ContentValues newRowValues = new ContentValues();

            //Now provide a value for every database column defined in MyOpener.java:
            //put string name in the NAME column:
            newRowValues.put(MyOpener.COL_MESSAGES, msg);
            //put string email in the EMAIL column:
            newRowValues.put(String.valueOf(MyOpener.COL_ISSENT), true);

            //Now insert in the database:
            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);
            Message message= new Message(newId, msg,true);
            elements.add(message);
            et.setText("");
            myAdapter.notifyDataSetChanged();


        });
        receiveButton = (Button) findViewById(R.id.receiveButton);
        receiveButton.setOnClickListener(click->{
            String msg = et.getText().toString();
            //add to the database and get the new ID
            ContentValues newRowValues = new ContentValues();

            //Now provide a value for every database column defined in MyOpener.java:
            //put string name in the NAME column:
            newRowValues.put(MyOpener.COL_MESSAGES, msg);
            //put string email in the EMAIL column:
            newRowValues.put(String.valueOf(MyOpener.COL_ISSENT), true);

            //Now insert in the database:
            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);
            Message message= new Message(newId, msg,false);
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