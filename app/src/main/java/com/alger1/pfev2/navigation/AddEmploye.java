package com.alger1.pfev2.navigation;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.alger1.pfev2.R;
import com.alger1.pfev2.dao.EmployeDao;
import com.alger1.pfev2.model.Employe;
import com.rscja.deviceapi.Fingerprint;

/**
 * Created by ISLEM-PC on 4/30/2018.
 */

public class AddEmploye extends Fragment {

    private Main2Activity mContext;
    ProgressDialog progressDialog;
    Button button;
    EditText p,u,pass,first,last;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_employe, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AssTask().execute();
            }
        });

    }

    private void init(){
        mContext = (Main2Activity) getActivity();
        button = getView().findViewById(R.id.save);
        p = getView().findViewById(R.id.page_id);
        u = getView().findViewById(R.id.username);
        pass = getView().findViewById(R.id.password);
        first = getView().findViewById(R.id.firstname);
        last = getView().findViewById(R.id.lastname);
    }

    class AssTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... integers) {
            boolean exeSucc = false;

            if (!mContext.mFingerprint.getImage()) {
                return null;
            }

            if (mContext.mFingerprint.genChar(Fingerprint.BufferEnum.B1)) {
                exeSucc = true;
            }

            if (!mContext.mFingerprint.getImage()) {
                return null;
            }

            if (mContext.mFingerprint.genChar(Fingerprint.BufferEnum.B2)) {
                exeSucc = true;
            }

            if (mContext.mFingerprint.regModel()) {
                exeSucc = true;
            }

            if (exeSucc) {
                int pid = Integer.parseInt(p.getText().toString());
                if (mContext.mFingerprint.storChar(Fingerprint.BufferEnum.B1, pid)) {
                    /*
                    if (mContext.mFingerprint.getImage()) {
                        mContext.mFingerprint.upImage(1,mContext.getFilesDir() + "/finger.bmp");
                    }*/
                    Employe employe = new Employe();
                    employe.setId_employe(pid);
                    employe.setUsername(u.getText().toString());
                    employe.setPassword(pass.getText().toString());
                    employe.setFirstname(first.getText().toString());
                    employe.setLastname(last.getText().toString());
                    EmployeDao employeDao = new EmployeDao();
                    employeDao.insertEmploye(employe,mContext);
                    return "success";
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.cancel();
            if(s == null){
                Toast.makeText(mContext,"failed to get images",Toast.LENGTH_SHORT).show();
            }else {
                /*
                Bitmap bitmap = BitmapFactory.decodeFile(mContext.getFilesDir()
                        + "/finger.bmp");
                imageView.setImageBitmap(bitmap);*/
                Toast.makeText(mContext,"Sucsess",Toast.LENGTH_SHORT).show();
            }
        }

    }
}
