package sheron.csci4100u.labs.lab6;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ContactAdapter extends ArrayAdapter<Contact> {
    private ArrayList<Contact> contacts;
    private Context context;


    ContactAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, R.layout.contact_list_item, contacts);
        this.context = context;
        this.contacts = contacts;
    }


    @Override
    @NonNull
    public View getView(int position,
                        View reusableView,
                        @NonNull ViewGroup parent) {

        Contact contact = contacts.get(position);

        if (reusableView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            reusableView = inflater.inflate(R.layout.contact_list_item,
                                            parent,
                                            false);
        }

        TextView id = reusableView.findViewById(R.id.tvId);
        id.setText(String.valueOf(position));

        TextView name = reusableView.findViewById(R.id.tvName);
        name.setText(contact.getName());
        TextView phoneNum = reusableView.findViewById(R.id.tvPhoneNum);
        phoneNum.setText(contact.getPhoneNum());

        return reusableView;
    }


    @Override
    public View getDropDownView(int position,
                                View reusableView,
                                @NonNull ViewGroup parent) {

        Contact contact = contacts.get(position);

        if (reusableView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            reusableView = inflater.inflate(
                    R.layout.support_simple_spinner_dropdown_item,
                    parent,
                    false);
        }

        TextView name = reusableView.findViewById(android.R.id.text1);
        name.setBackgroundColor(ResourcesCompat.getColor(
                reusableView.getResources(),
                R.color.black, null
        ));
        name.setText(contact.getName());

        return reusableView;
    }
}
