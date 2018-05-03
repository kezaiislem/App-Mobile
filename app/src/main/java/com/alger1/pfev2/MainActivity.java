package com.alger1.pfev2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.alger1.pfev2.dao.EmployeDao;
import com.alger1.pfev2.dao.PointageDao;
import com.alger1.pfev2.model.Employe;
import com.alger1.pfev2.model.Pointage;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<Employe> list;
    List<Pointage> list1;
    Employe employe;
    Pointage pointage;
    EmployeDao employeDao;
    PointageDao pointageDao;
    //TextView tttstt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        employeDao = new EmployeDao();
        pointageDao = new PointageDao();
        //tttstt = findViewById(R.id.tst);

        employeDao.insertEmploye(new Employe(1,"hamada","1997","hda",
                "ddf","h@yahoo.fr",22,"ds","male","12/140/1998",""),getApplicationContext());

        employeDao.insertEmploye(new Employe(2,"kezai","1998","hda",
                "ddf","h@yahoo.fr",222,"ds","male","12/140/1998",""),getApplicationContext());

        list = employeDao.getEmployeList(getApplicationContext());

        for(int i=0;i<list.size();i++){

            employe = list.get(i);

            //tttstt.setText(tttstt.getText()+""+employe.getId_employe()+";"+employe.getId_employe()+";"+employe.getFirstname()+";"+employe.getLastname()+";"+employe.getUsername()+";"+employe.getPassword()+"\n");

        }



        pointageDao.insertPointage(new Pointage(5.210152,8.5465,"15:25","15/4/2017","exit","kezai"),getApplicationContext());

        list1 = pointageDao.getPointageList(getApplicationContext());

        for(int i=0;i<list1.size();i++){

            pointage = list1.get(i);

            //tttstt.setText(tttstt.getText()+""+pointage.getId_pointage()+";"+pointage.getDate()+";"+pointage.getTime()+";"+pointage.getType()+";"+pointage.getLongitude()+";"+pointage.getLatitude()+";"+pointage.getUsername()+"\n");

        }
    }
}
