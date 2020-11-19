package com.example.androidlabs;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.androidlabs.ChatRoomActivity;
import com.example.androidlabs.R;


public class DetailsFragment extends Fragment {

    private Bundle dataFromActivity;
    private long id;
    private AppCompatActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(ChatRoomActivity.ITEM_ID );

        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.fragment_details, container, false);

        //show the message
        TextView message = (TextView)result.findViewById(R.id.message);
        message.setText(dataFromActivity.getString(ChatRoomActivity.ITEM_SELECTED));

        //show the id:
        TextView idView = (TextView)result.findViewById(R.id.idText);
        idView.setText("ID=" + id);

        //show the message is sent or not
        CheckBox cb = result.findViewById(R.id.CheckBox);
        cb.setChecked(dataFromActivity.getBoolean(ChatRoomActivity.ITEM_ISSENT));

        // get the delete button, and add a click listener:
        Button finishButton = (Button)result.findViewById(R.id.finishButton);
        finishButton.setOnClickListener( clk -> {

            //Tell the parent activity to remove
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();

            if (parentActivity instanceof EmptyActivity){
                parentActivity.finish();
            }
        });

        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity)context;
    }
}
