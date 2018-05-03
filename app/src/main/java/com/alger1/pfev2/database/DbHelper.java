package com.alger1.pfev2.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by ISLEM-PC on 4/30/2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String db_name = "pfe.db";
    private static final String insertEmployeQuery = "INSERT INTO employe VALUES ( ?, ?, ?, ?, ?, ?)";
    private static final String insertPointageQuery = "INSERT INTO pointage VALUES ( NULL, ?, ?, ?, ?, ?, ?)";

    public DbHelper(Context context) {
        super(context, db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS employe(id_employe INTEGER PRIMARY KEY, pid INTEGER, username VARCHAR, password VARCHAR, first_name VARCHAR, last_name VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS pointage(id_pointage INTEGER PRIMARY KEY AUTOINCREMENT, date VARCHAR, time VARCHAR, type VARCHAR, longitude DOUBLE, latitude DOUBLE, username VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertEmploye(int id, int pid, String username, String password, String first, String last){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        SQLiteStatement statement = sqLiteDatabase.compileStatement(insertEmployeQuery);
        statement.clearBindings();
        statement.bindLong(1, id);
        statement.bindLong(2, pid);
        statement.bindString(3, username);
        statement.bindString(4, password);
        statement.bindString(5, first);
        statement.bindString(6, last);

        try {
            statement.executeInsert();
        }catch(Exception e){
            sqLiteDatabase.close();
            return false;
        }
        sqLiteDatabase.close();
        return true;
    }

    public boolean insertPointage(String date, String time, String type, double longitude, double latitude, String username){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        SQLiteStatement statement = sqLiteDatabase.compileStatement(insertPointageQuery);
        statement.clearBindings();
        statement.bindString(1, date);
        statement.bindString(2, time);
        statement.bindString(3, type);
        statement.bindDouble(4, longitude);
        statement.bindDouble(5, latitude);
        statement.bindString(6, username);

        try {
            statement.executeInsert();
        }catch(Exception e){
            sqLiteDatabase.close();
            return false;
        }
        sqLiteDatabase.close();
        return true;
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public void dropTable() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS employe");
        onCreate(database);
    }
}
