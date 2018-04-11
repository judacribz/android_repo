package sheron.csci4100u.ass.a2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sheron.csci4100u.ass.a2.async_task.BitcoinPriceObserver;
import sheron.csci4100u.ass.a2.async_task.GetBitcoinPriceTask;
import sheron.csci4100u.ass.a2.model.*;


public class BrowseProductsActivity extends AppCompatActivity
        implements BitcoinPriceObserver,
                   View.OnClickListener{

    ProductDBHelper dbHelper;
    ArrayList<Product> products;
    String url;
    GetBitcoinPriceTask task;

    int index, indexLimit;
    boolean btnNextStatus, btnPrevStatus, btnDelStatus;

    ProgressBar progressBar;
    TextView tvCounter;
    EditText etName, etDescription, etPriceCAD, etPriceBit;
    Button btnNext, btnPrev, btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_products_activity);

        index      = 0;
        indexLimit = -1;

        dbHelper = new ProductDBHelper(this);

        progressBar = findViewById(R.id.pbBitcoinLoading);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.BLACK,
                                                              PorterDuff.Mode.MULTIPLY);

        tvCounter     = findViewById(R.id.tvCounter);
        etName        = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etPriceCAD    = findViewById(R.id.etPriceCAD);
        etPriceBit    = findViewById(R.id.etPriceBit);

        btnPrev   = findViewById(R.id.btnPrevious);
        btnDelete = findViewById(R.id.btnDelete);
        btnNext   = findViewById(R.id.btnNext);

        btnPrev.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnNext.setOnClickListener(this);


        url = getString(R.string.cad_to_bitcoin_url);
    }


    @Override
    protected void onStart() {
        super.onStart();
        setProducts();
    }


    // Sets all the buttons to either enabled or disabled and calls showProduct
    private void setProducts() {
        products     = dbHelper.getAllProducts();
        indexLimit   = products.size() - 1;
        btnDelStatus = true;

        // if there are no products or only 1 in the database
        if (indexLimit <= 0) {
            btnNextStatus = false;
            btnPrevStatus = false;

            if (indexLimit == -1) {
                btnDelStatus = false;

                etName.setText("");
                etDescription.setText("");
                etPriceCAD.setText("");
                etPriceBit.setText("");
            }

        // if there is more than 1 product in the database
        } else {
            if (index == 0) {
                btnPrevStatus = false;
                btnNextStatus = true;
            } else if (index == indexLimit) {
                btnPrevStatus = true;
                btnNextStatus = false;
            } else {
                btnPrevStatus = true;
                btnNextStatus = true;
            }
        }

        btnNext.setEnabled(btnNextStatus);
        btnPrev.setEnabled(btnPrevStatus);
        btnDelete.setEnabled(btnDelStatus);

        // sets the color of the delete button
        if (btnDelStatus) {
            btnDelete.setBackgroundColor(getColor(R.color.colorPrimary));
            btnDelete.setTextColor(getColor(R.color.white));
        } else {
            btnDelete.setBackgroundColor(getColor(R.color.colorPrimaryDark));
            btnDelete.setTextColor(getColor(R.color.colorDisabled));
        }

        // Show a product if any products were retrieved from the database
        if (indexLimit >= 0) {
            showProduct(products.get(index));
        } else {
            tvCounter.setText(String.valueOf(index));
        }
    }


    // Displays the product info passed through the arguments
    private void showProduct(Product product) {
        tvCounter.setText(String.valueOf(index + 1));
        etName.setText(product.getName());
        etDescription.setText(product.getDescription());

        float price = product.getPrice();
        etPriceCAD.setText(String.valueOf(price));
        convertToBitCoin(price);
    }


    // Calls async task to convert CAD price to bitcoin
    private void convertToBitCoin(float cad) {
        progressBar.setVisibility(View.VISIBLE);

        task = new GetBitcoinPriceTask();
        task.setBitcoinPriceObserver(this);
        task.execute(url + cad);
    }


    // Interface method that receives converted bitcoin price as a string to
    // prevent crashes if a huge number was sent (float is too small)
    @Override
    public void bitcoinValueReceived(String bitcoinValue) {
        if (!bitcoinValue.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            etPriceBit.setText(bitcoinValue);
        } else {
            Toast.makeText(this,
                           R.string.internet_error,
                           Toast.LENGTH_SHORT).show();
        }
    }


    // Click Handling
    // ========================================================================
    public void handleClick(int id) {
        switch(id) {

            // Previous Button
            case R.id.btnPrevious:
                index--;
                break;

            // Next Button
            case R.id.btnNext:
                index++;
                break;

            // Delete Button
            case R.id.btnDelete:
                dbHelper.deleteProduct(products.get(index).getProductId());
                products = dbHelper.getAllProducts();

                if (index != 0) {
                    index--;
                }
                break;

            // Add New Production Menu Item
            case R.id.actAddNewProduct:
                Intent intent = new Intent(this, AddProductActivity.class);
                startActivity(intent);
                break;

            case R.id.actDelAllProducts:
                dbHelper.deleteAllProducts();
                index = 0;
                break;
        }

        // Clear the EditText fields and set them if the next, previous, or
        // delete button were clicked
        etName.setText("");
        etDescription.setText("");
        etPriceCAD.setText("");
        etPriceBit.setText("");


        if (id != R.id.actAddNewProduct) {
            setProducts();
        }
    }

    @Override
    public void onClick(View v) {
        handleClick(v.getId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_browse_products, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        handleClick(item.getItemId());
        return super.onOptionsItemSelected(item);
    }
    // End of Click Handling ==================================================
}
