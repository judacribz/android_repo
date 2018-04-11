package sheron.csci4100u.ass.a2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import sheron.csci4100u.ass.a2.model.ProductDBHelper;


public class AddProductActivity extends AppCompatActivity
        implements View.OnClickListener {

    ProductDBHelper dbHelper;
    EditText etName, etDescription, etPrice;
    String errorStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_activity);

        dbHelper = new ProductDBHelper(this);
        errorStatus = getString(R.string.required);

        findViewById(R.id.btnSave).setOnClickListener(this);
        findViewById(R.id.btnCancel).setOnClickListener(this);

        etName        = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etPrice       = findViewById(R.id.etPriceCAD);
    }


    // Click Handling
    // ========================================================================
    @Override
    public void onClick(View v) {
        switch(v.getId()) {

            // Save button handling
            case R.id.btnSave:
                boolean nameStatus  = validateForm(etName);
                boolean descStatus  = validateForm(etDescription);
                boolean priceStatus = validateForm(etPrice);

                if (nameStatus && descStatus && priceStatus) {
                    dbHelper.createProduct(
                            etName.getText().toString(),
                            etDescription.getText().toString(),
                            Float.valueOf(etPrice.getText().toString())
                    );
                    finishActivity();
                }
                break;

            // Cancel button handling
            case R.id.btnCancel:
                finishActivity();
                break;
        }
    }


    public boolean validateForm(EditText editText) {
        boolean formStatus = false;

        if (!editText.getText().toString().trim().isEmpty()) {
            formStatus = true;
        } else {
            editText.setError(errorStatus);
        }

        return formStatus;
    }

    // Clears EditText fields and finishes activity
    public void finishActivity() {
        etName.setText("");
        etDescription.setText("");
        etPrice.setText("");
        finish();
    }
    // End of Click Handling ==================================================
}
