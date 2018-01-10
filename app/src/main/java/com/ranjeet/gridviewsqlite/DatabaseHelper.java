package com.ranjeet.gridviewsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by admin on 12/12/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private String TAG = this.getClass().getSimpleName();

    private static final String DATABASE_NAME = "emp_db";
    private static final int DATABASE_VERSION = 1;

    // TABLE NAMES
    private static final String TABLE_EMP = "employee";

    /* Keys for Table Employee */
    private static final String KEY_CODE = "code";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ADDRESS = "address";

    String CREATE_TABLE_CALL = "CREATE TABLE " + TABLE_EMP + "(" + KEY_CODE + " INTEGER," + KEY_NAME + " TEXT," + KEY_EMAIL + " TEXT," + KEY_ADDRESS
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        Log.v(TAG, "CREATE TABLE CALL: " + CREATE_TABLE_CALL);
        db.execSQL(CREATE_TABLE_CALL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMP);
        onCreate(db);
    }

    /* Method to create a Employee */
    public long createEmployee(Employee emp) {
        long c;

        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CODE, emp.getCode());
        values.put(KEY_NAME, emp.getName());
        values.put(KEY_EMAIL, emp.getEmail());
        values.put(KEY_ADDRESS, emp.getAddress());

        c = database.insert(TABLE_EMP, null, values);
        database.close();
        return c;

    }

    /* Method for fetching record from Database */
    public ArrayList<Employee> getAllEmployee() {
        String query = "SELECT * FROM " + TABLE_EMP;
        ArrayList<Employee> employees = new ArrayList<Employee>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                int code = c.getInt(c.getColumnIndex(KEY_CODE));
                String name = c.getString(c.getColumnIndex(KEY_NAME));
                String email = c.getString(c.getColumnIndex(KEY_EMAIL));
                String address = c.getString(c.getColumnIndex(KEY_ADDRESS));

                Employee emp = new Employee();
                emp.setCode(code);
                emp.setName(name);
                emp.setEmail(email);
                emp.setAddress(address);

                Log.v("DBHelper: ", "Name: " + name);
                Log.v("DBHelper: ", "Code: " + code);
                Log.v("DBHelper: ", "Email: " + email);
                Log.v("DBHelper: ", "Address: " + address);

                employees.add(emp);
            }
        }

        return employees;

    }
/* This method is used to get a single record from Database.
   I have given an example, you have to do something like this. */

    public Employee getEmployeeByCode(int code) {
        String query = "SELECT * FROM " + TABLE_EMP + " WHERE " + KEY_CODE + " = " + code;
        Employee emp = new Employee();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);

        if (c.getCount() > 0) {

            c.moveToFirst();
            int code = c.getInt(c.getColumnIndex(KEY_CODE));
            String name = c.getString(c.getColumnIndex(KEY_NAME));
            String email = c.getString(c.getColumnIndex(KEY_EMAIL));
            String address = c.getString(c.getColumnIndex(KEY_ADDRESS));

            emp.setCode(code);
            emp.setName(name);
            emp.setEmail(email);
            emp.setAddress(address);

            Log.v("DBHelper: ", "Name: " + name);
            Log.v("DBHelper: ", "Code: " + code);
            Log.v("DBHelper: ", "Email: " + email);
            Log.v("DBHelper: ", "Address: " + address);


        }
        return emp;
    }
}
