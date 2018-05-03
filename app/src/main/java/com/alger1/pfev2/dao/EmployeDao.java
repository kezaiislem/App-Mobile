package com.alger1.pfev2.dao;

import android.content.Context;
import android.database.Cursor;

import com.alger1.pfev2.database.DbHelper;
import com.alger1.pfev2.model.Employe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ISLEM-PC on 4/30/2018.
 */

public class EmployeDao {

    public boolean insertEmploye(Employe employe, Context context){
        boolean result = false;

        DbHelper dbHelper = new DbHelper(context);
        result = dbHelper.insertEmploye(employe.getId_employe(),employe.getId_employe(),employe.getUsername(),employe.getPassword(), employe.getFirstname(),employe.getLastname());
        dbHelper.close();

        return result;
    }

    public List<Employe> getEmployeList(Context context){
        Cursor cursor;
        Employe employe = null;
        List<Employe> list = null;

        DbHelper dbHelper = new DbHelper(context);
        cursor = dbHelper.getData("SELECT * FROM employe");

        list = new ArrayList<>();

        while(cursor.moveToNext()){
            employe = new Employe();
            employe.setId_employe(cursor.getInt(0));
            employe.setUsername(cursor.getString(2));
            employe.setPassword(cursor.getString(3));
            employe.setFirstname(cursor.getString(4));
            employe.setLastname(cursor.getString(5));
            list.add(employe);
        }

        dbHelper.close();

        return list;
    }

    public String getEmployeUsernameById(int id,Context context){

        Cursor cursor;
        String result=null;
        DbHelper dbHelper = new DbHelper(context);
        cursor = dbHelper.getData("SELECT * FROM employe WHERE id_employe = "+id);

        if(cursor.moveToNext()){

            result=cursor.getString(2);

        }
        dbHelper.close();

        return result;
    }

}