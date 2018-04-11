package sheron.csci4100u.ass.a2.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class ProductDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE       = "products";

    private static final String PRODUCT_ID  = "productID";
    private static final String NAME        = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE       = "price";

    private static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE + " (\n" +
                    PRODUCT_ID  + " integer primary key autoincrement,\n" +
                    NAME        + " text not null,\n" +
                    DESCRIPTION + " varchar2(100) not null,\n" +
                    PRICE       + " real not null\n" + 
            ")\n";

    private static final String DROP_STATEMENT = "DROP TABLE " + TABLE;

    
    public ProductDBHelper(Context context) {
        super(context, "products", null, DATABASE_VERSION);
    }

    
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create the database, using CREATE SQL statement
        db.execSQL(CREATE_STATEMENT);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersionNum,
                          int newVersionNum) {
        // delete the old database
        db.execSQL(DROP_STATEMENT);

        // re-create the database
        db.execSQL(CREATE_STATEMENT);
    }


    // CRUD functions
    // CREATE
    public void createProduct(String name, String description, float price) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put(NAME,        name);
        newValues.put(DESCRIPTION, description);
        newValues.put(PRICE,       price);

        db.insert(TABLE, null, newValues);
    }


    // READ
    public ArrayList<Product> getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Product> products = new ArrayList<>();
        Product product;

        String[] columns = new String[] {PRODUCT_ID, NAME, DESCRIPTION, PRICE};
        String where = "";
        String[] whereArgs = new String[] {  };
        Cursor cursor = db.query(TABLE, columns, where, whereArgs, "", "", "");


        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                float price = cursor.getFloat(3);

                product = new Product(name, description, price);
                product.setProductId(id);
                products.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return products;
    }


    // DELETE
    public void deleteProduct(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE, PRODUCT_ID + " = ?", new String[] { "" + id });
    }


    public void deleteAllProducts() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE, "", new String[] { });
    }

}
