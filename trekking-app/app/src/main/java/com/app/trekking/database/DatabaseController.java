package com.app.trekking.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.app.trekking.SystemConfig;

import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;

/**
 * Class that wraps the most common database operations. This example assumes you want a single table and data entity
 * with two properties: a title and a priority as an integer. Modify in all relevant locations if you need other/more
 * properties for your data and/or additional tables.
 */
public class DatabaseController {
    private SQLiteOpenHelper _openHelper;

    /**
     * Construct a new database helper object
     * @param context The current context for the application or activity
     */
    public DatabaseController(Context context) {
        _openHelper = new SimpleSQLiteOpenHelper(context);
    }

    /**
     * This is an internal class that handles the creation of all database tables
     */
    class SimpleSQLiteOpenHelper extends SQLiteOpenHelper {
        SimpleSQLiteOpenHelper(Context context) {
            super(context, "Test123.db", null, 2);
            Log.d("abc", "abc");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + DatabaseConfig.userTableName +
                    " (_id integer primary key autoincrement, " +
                    " " + DatabaseConfig.nameUserColumn + " text," +
                    " " + DatabaseConfig.emailUserColumn + " text," +
                    " " + DatabaseConfig.passwordUserColumn + " text," +
                    " " + DatabaseConfig.typeUserColumn + " integer)");
//            db.execSQL("create table " + DatabaseConfig.companyTableName +
//                    " (_id integer primary key autoincrement, " +
//                    " " + DatabaseConfig.nameCompanyColumn + " text," +
//                    " " + DatabaseConfig.descriptionCompanyColumn + " text," +
//                    " " + DatabaseConfig.locationCompanyColumn + " integer)");
//            db.execSQL("create table " + DatabaseConfig.locationTableName +
//                    " (_id integer primary key autoincrement, " +
//                    " " + DatabaseConfig.latitudeLocationColumn + " integer," +
//                    " " + DatabaseConfig.longitudeLocationColumn + " integer," +
//                    " " + DatabaseConfig.nameLocationColumn + " text)");
//            db.execSQL("create table " + DatabaseConfig.tourTableName +
//                    " (_id integer primary key autoincrement, " +
//                    " " + DatabaseConfig.idTourLocationInfoColumn + " integer," +
//                    " " + DatabaseConfig.userTourColumn + " integer," +
//                    " " + DatabaseConfig.dateTourColumn + " integer)");
//            db.execSQL("create table " + DatabaseConfig.locationInfoTableName +
//                    " (_id integer primary key autoincrement, " +
//                    " " + DatabaseConfig.idLocationLocationInfoColumn + " integer," +
//                    " " + DatabaseConfig.idTourLocationInfoColumn + " integer");
//            Log.d("create", "database");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    private String hashPassword(String password) {
        int hash = 7;
        for (int i = 0; i < password.length(); i++) {
            hash = hash*31 + password.charAt(i);
        }
        return Integer.toHexString(hash);
    }

    public long addUser(String name, String email, String password) {
        SQLiteDatabase db = _openHelper.getWritableDatabase();
        if (db == null) {
            return 0;
        }
        ContentValues row = new ContentValues();
        row.put(DatabaseConfig.nameUserColumn, name);
        row.put(DatabaseConfig.emailUserColumn, email);
        row.put(DatabaseConfig.passwordUserColumn, hashPassword(password));
        row.put(DatabaseConfig.typeUserColumn, 0);
        long id;
        try {

            id = db.insert(DatabaseConfig.userTableName, null, row);
        }
        catch (Exception e) {
         /* This is a generic Exception handler which means it can handle
          * all the exceptions. This will execute if the exception is not
          * handled by previous catch blocks.
          */
            Log.e("addUser", e.toString());
            id = -1;
        }
        db.close();
        return id;
    }

    public Cursor getUser(Integer _id, String _name, String _email, Integer _type) {
        SQLiteDatabase db = _openHelper.getReadableDatabase();
        if (db == null) {
            return null;
        }
        Boolean mark = false;
        String query = "select * " +
                "from " + DatabaseConfig.userTableName;
        if (_id != -1) {
            if (mark) {
                query += " AND " + DatabaseConfig.idColumn + "= \"" + _id.toString() + "\"";
            } else {
                query += " WHERE " + DatabaseConfig.idColumn + "= \"" + _id.toString() + "\"";
                mark = true;
            }
        }
        if (_name != "") {
            if (mark) {
                query += " AND " + DatabaseConfig.nameUserColumn + "= \"" + _name + "\"";
            } else {
                query += " WHERE " + DatabaseConfig.nameUserColumn + "= \"" + _name + "\"";
                mark = true;
            }
        }
        if (_email != "") {
            if (mark) {
                query += " AND " + DatabaseConfig.emailUserColumn + "= \"" + _email + "\"";
            } else {
                query += " WHERE " + DatabaseConfig.emailUserColumn + "= \"" + _email + "\"";
                mark = true;
            }
        }
        if (_type != -1) {
            if (mark) {
                query += " AND " + DatabaseConfig.typeUserColumn + "= \"" + _type.toString() + "\"";
            } else {
                query += " WHERE " + DatabaseConfig.typeUserColumn + "= \"" + _type.toString() + "\"";
                mark = true;
            }
        }
        Cursor cur = db.rawQuery(query, null);
        return cur;
    }

    public boolean validateUser(String email, String password, int _type) {
        Cursor cur = getUser(-1, "", email, _type);
        password = hashPassword(password);
        if (cur.getCount() == 0) {
            return false;
        }
        cur.moveToFirst();
        if (password.compareTo(cur.getString(3)) == 0) {
            return true;
        }
        return false;
    }

    public boolean isUserInDatabase(String email, int _type) {
        Cursor cur = getUser(-1, "", email, _type);
        if (cur.getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Return a cursor object with all rows in the table.
     * @return A cursor suitable for use in a SimpleCursorAdapter
     */
    public Cursor getAll() {
        SQLiteDatabase db = _openHelper.getReadableDatabase();
        if (db == null) {
            return null;
        }
        return db.rawQuery("select * from todos order by priority, title", null);
    }

    /**
     * Return values for a single row with the specified id
     * @param id The unique id for the row o fetch
     * @return All column values are stored as properties in the ContentValues object
     */
    public ContentValues get(long id) {
        SQLiteDatabase db = _openHelper.getReadableDatabase();
        if (db == null) {
            return null;
        }
        ContentValues row = new ContentValues();
        Cursor cur = db.rawQuery("select title, priority from todos where _id = ?", new String[] { String.valueOf(id) });
        if (cur.moveToNext()) {
            row.put("title", cur.getString(0));
            row.put("priority", cur.getInt(1));
        }
        cur.close();
        db.close();
        return row;
    }

    /**
     * Add a new row to the database table
     * @param title The title value for the new row
     * @param priority The priority value for the new row
     * @return The unique id of the newly added row
     */
    public long add(String title, int priority) {
        SQLiteDatabase db = _openHelper.getWritableDatabase();
        if (db == null) {
            return 0;
        }
        //db.execSQL("create table todos (_id integer primary key autoincrement, title text, priority integer)");
        Log.d("add", title);
        ContentValues row = new ContentValues();
        row.put("title", title);
        row.put("priority", priority);
        long id = db.insert("todos", null, row);
        db.close();
        return id;
    }

    /**
     * Delete the specified row from the database table. For simplicity reasons, nothing happens if
     * this operation fails.
     * @param id The unique id for the row to delete
     */
    public void delete(long id) {
        SQLiteDatabase db = _openHelper.getWritableDatabase();
        if (db == null) {
            return;
        }
        db.delete("todos", "_id = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    /**
     * Updates a row in the database table with new column values, without changing the unique id of the row.
     * For simplicity reasons, nothing happens if this operation fails.
     * @param id The unique id of the row to update
     * @param title The new title value
     * @param priority The new priority value
     */
    public void update(long id, String title, int priority) {
        SQLiteDatabase db = _openHelper.getWritableDatabase();
        if (db == null) {
            return;
        }
        ContentValues row = new ContentValues();
        row.put("title", title);
        row.put("priority", priority);
        db.update("todos", row, "_id = ?", new String[] { String.valueOf(id) } );
        db.close();
    }
}