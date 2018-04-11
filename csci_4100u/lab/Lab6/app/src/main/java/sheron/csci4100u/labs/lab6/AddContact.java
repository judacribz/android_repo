package sheron.csci4100u.labs.lab6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static sheron.csci4100u.labs.lab6.ShowContacts.EXTRA_NEW_ID;


public class AddContact extends AppCompatActivity implements View.OnClickListener {

    static final String EXTRA_CONTACT =
            "sheron.csci4100u.labs.lab6.EXTRA_CONTACT";

    EditText etFirstName, etLastName, etPhoneNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName  = findViewById(R.id.etLastName);
        etPhoneNum  = findViewById(R.id.etPhoneNum);

        (findViewById(R.id.btnAddContact)).setOnClickListener(this);
    }


    // Add button handler
    @Override
    public void onClick(View v) {

        // Get text field data
        String firstName = getFieldText(etFirstName);
        String lastName = getFieldText(etLastName);
        String phoneNum = getFieldText(etPhoneNum);

        if (   !firstName.isEmpty()
            && !lastName.isEmpty()
            && !phoneNum.isEmpty()) {

            Intent newContactIntent = getIntent();
            int id = newContactIntent.getIntExtra(EXTRA_NEW_ID, -1);
            newContactIntent.putExtra(EXTRA_CONTACT, new Contact(id,
                                                                 firstName,
                                                                 lastName,
                                                                 phoneNum));

            setResult(RESULT_OK, newContactIntent);
            finish();
        }
    }

    // Helper function to validate text field and return text contents
    private String getFieldText(EditText field) {
        String fieldText = field.getText().toString();

        if (fieldText.isEmpty()) {
            field.setError(getString(R.string.required));
        }

        return fieldText;
    }
}
