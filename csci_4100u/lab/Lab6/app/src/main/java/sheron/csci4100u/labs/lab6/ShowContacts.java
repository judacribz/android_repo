package sheron.csci4100u.labs.lab6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

import static sheron.csci4100u.labs.lab6.AddContact.EXTRA_CONTACT;
import static sheron.csci4100u.labs.lab6.DeleteContact.EXTRA_CONTACT_ID;
import static sheron.csci4100u.labs.lab6.Utilities.*;


public class ShowContacts extends AppCompatActivity implements View.OnClickListener {

    // Constants
    private static final int REQ_ADD_CONTACT = 101;
    private static final int REQ_DELETE_CONTACT = 102;
    static final String EXTRA_CONTACTS =
            "sheron.csci4100u.labs.lab6.EXTRA_CONTACTS";
    static final String EXTRA_NEW_ID =
            "sheron.csci4100u.labs.lab6.EXTRA_NEW_ID";

    boolean returnFromActivity = false;
    ArrayList<Contact> contacts;
    String file;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contacts);

        (findViewById(R.id.btnAdd)).setOnClickListener(this);
        (findViewById(R.id.btnDelete)).setOnClickListener(this);
        listView = findViewById(R.id.lvContacts);

        contacts = new ArrayList<>();
        file     = getResources().getString(R.string.contacts_file);
    }


    // Btn click handler
    @Override
    public void onClick(View v) {
        Intent intent = null;
        int requestCode = -1;

        switch (v.getId()) {
            // Start AddContact activity
            case R.id.btnAdd:
                intent = new Intent(this, AddContact.class);
                intent.putExtra(EXTRA_NEW_ID, contacts.size());
                requestCode = REQ_ADD_CONTACT;
                break;

            // Start DeleteContact activity
            case R.id.btnDelete:
                intent = new Intent(this, DeleteContact.class);
                intent.putParcelableArrayListExtra(EXTRA_CONTACTS, contacts);
                requestCode = REQ_DELETE_CONTACT;
                break;
        }

        if (requestCode != -1) {
            startActivityForResult(intent, requestCode);
        }
    }


    // Read from file in onStart if contacts list is empty and set the list
    // adapter
    @Override
    protected void onStart() {
        super.onStart();

        if (!returnFromActivity) {
            contacts = readFromFile(file, ShowContacts.this);
            Toast.makeText(this, "Reading", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not Reading", Toast.LENGTH_SHORT).show();
        }

        listView.setAdapter(new ContactAdapter(this, contacts));
    }


    // Write to file in onStop
    @Override
    protected void onStop() {
        Toast.makeText(this, "Writing", Toast.LENGTH_SHORT).show();
        writeToFile(file, contacts, ShowContacts.this);

        super.onStop();
    }


    // Return from sub-activities handler
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent contactInfo) {
        if (resultCode == RESULT_OK) {
            returnFromActivity = true;
            Contact contact;

            switch(requestCode) {

                // Return from AddContact Activity
                case REQ_ADD_CONTACT:
                    contact = contactInfo.getParcelableExtra(EXTRA_CONTACT);

                    contacts.add(contact);

                    toast(this, contact.getFirstName() +
                                " " +
                                contact.getLastName() +
                                " added");

                    break;

                // Return from DeleteContact Activity
                case REQ_DELETE_CONTACT:
                    int index = contactInfo.getIntExtra(EXTRA_CONTACT_ID, -1);
                    contact = contacts.remove(index);

                    toast(this, contact.getName()+ " added");
                    toast(this, contact.getName()+ " added");

                    break;
            }
        } else {
            toast(this, "No contacts added or deleted");
            returnFromActivity = false;
        }
    }
}
