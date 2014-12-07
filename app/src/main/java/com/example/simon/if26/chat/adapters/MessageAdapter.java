package com.example.simon.if26.chat.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.simon.chat.R;
import com.example.simon.if26.chat.models.Message;

import java.util.ArrayList;

/**
 * Created by simon on 29/11/14.
 */
public class MessageAdapter extends ArrayAdapter<Message> {
    public MessageAdapter(Context context, ArrayList<Message> contacts) {
        super(context, 0, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message, parent, false);
        }

        TextView messageContent = (TextView) convertView.findViewById(R.id.message_content);
        TextView messageDate = (TextView) convertView.findViewById(R.id.message_date);

        if (message.isSent()) {
            messageContent.setTextColor(Color.parseColor("#0099cc"));
            messageContent.setGravity(Gravity.RIGHT);
            messageDate.setGravity(Gravity.RIGHT);
        }

        messageContent.setText(message.getContent());
        messageDate.setText(message.getDate());

        return convertView;
    }
}
