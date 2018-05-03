package com.alger1.pfev2.dao;

import android.content.Context;
import android.database.Cursor;

import com.alger1.pfev2.database.DbHelper;
import com.alger1.pfev2.model.Pointage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ISLEM-PC on 4/30/2018.
 */

public class PointageDao {

    public boolean insertPointage(Pointage pointage, Context context){
        boolean result = false;

        DbHelper dbHelper = new DbHelper(context);
        result = dbHelper.insertPointage(pointage.getDate(),pointage.getTime(),pointage.getType(),pointage.getLongitude(),pointage.getLatitude(),pointage.getUsername());
        dbHelper.close();

        return result;
    }

    public List<Pointage> getPointageList(Context context){
        Cursor cursor;
        Pointage pointage = null;
        List<Pointage> list = null;

        DbHelper dbHelper = new DbHelper(context);
        cursor = dbHelper.getData("SELECT * FROM pointage");

        list = new ArrayList<>();

        while(cursor.moveToNext()){
            pointage = new Pointage();
            pointage.setId_pointage(cursor.getInt(0));
            pointage.setDate(cursor.getString(1));
            pointage.setTime(cursor.getString(2));
            pointage.setType(cursor.getString(3));
            pointage.setLongitude(cursor.getDouble(4));
            pointage.setLatitude(cursor.getDouble(5));
            pointage.setUsername(cursor.getString(6));
            list.add(pointage);
        }

        dbHelper.close();

        return list;
    }

}
