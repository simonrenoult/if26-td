package com.example.simon.if26.chat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.simon.chat.R;
import com.example.simon.if26.chat.models.Contact;

import java.util.ArrayList;

/**
 * Created by simon on 29/11/14.
 */
public class ContactAdapter extends ArrayAdapter<Contact> {
    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_contact, parent, false);
        }

        TextView fullNameView = (TextView) convertView.findViewById(R.id.fullname);
        TextView lastMessageView = (TextView) convertView.findViewById(R.id.last_message);

        fullNameView.setText(contact.getFullname());
        String lastMessage = "\"" + contact.getLastmessage().getContent() + "\"";

        lastMessageView.setText(lastMessage);

        convertView.setTag(contact.getId());

        return convertView;
    }
}
