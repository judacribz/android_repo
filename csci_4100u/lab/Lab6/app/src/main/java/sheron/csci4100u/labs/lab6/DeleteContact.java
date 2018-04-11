package sheron.csci4100u.labs.lab6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import java.util.ArrayList;

import static sheron.csci4100u.labs.lab6.ShowContacts.EXTRA_CONTACTS;


public class DeleteContact extends AppCompatActivity implements View.OnClickListener{

    static final String EXTRA_CONTACT_ID =
            "sheron.csci4100u.labs.lab6.EXTRA_CONTACT_ID";

    ArrayList<Contact> contacts;
    Spinner spinner;
    Intent deleteContactIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_contact);

        (findViewById(R.id.btnDeleteContact)).setOnClickListener(this);
        spinner = findViewById(R.id.spnrContacts);


        // Set adapter for spinner
        deleteContactIntent = getIntent();
        contacts = deleteContactIntent
                .getParcelableArrayListExtra(EXTRA_CONTACTS);
        spinner.setAdapter(new ContactAdapter(this, contacts));
    }


    // Delete button handler
    @Override
    public void onClick(View v) {
        if (!contacts.isEmpty()) {
            Intent deleteContactIntent = getIntent();
            int contactId = (spinner.getSelectedItemPosition());

            deleteContactIntent.putExtra(EXTRA_CONTACT_ID, contactId);
            setResult(RESULT_OK, deleteContactIntent);
        }
        finish();
    }
}
