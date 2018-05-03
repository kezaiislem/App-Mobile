package com.alger1.pfev2.navigation;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alger1.pfev2.Adapters.EmployeAdapter;
import com.alger1.pfev2.R;
import com.alger1.pfev2.dao.EmployeDao;
import com.alger1.pfev2.model.Employe;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rscja.deviceapi.Fingerprint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ISLEM-PC on 4/30/2018.
 */

public class EmployeList extends Fragment {

    ArrayList list_Emp;
    ListView listView;
    EmployeAdapter employeAdapter;
    RequestQueue requestQueue;
    private Main2Activity mContext;
    ProgressDialog progressDialog;
    boolean wait;
    String message;
    Employe employe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.employe_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        new getDataTask().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.e("position",""+position);
                employe = (Employe) list_Emp.get(position);
                new registerEmployeTask().execute();

            }
        });


    }

    private void init(){

        mContext = (Main2Activity) getActivity();
        listView = (ListView) getView().findViewById(R.id.emp_list);

    }

    class getDataTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... integers) {

            wait = true;
            list_Emp = new ArrayList();
            requestQueue = Volley.newRequestQueue(mContext);
            String url = "http://192.168.1.110:8080/PROJECT/GetemployesApp";
            //importer le contenu des EditTexts
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //Si il y a aucun erreur
                        if ((boolean) response.get("success") == true) {

                            JSONArray jsonArray = new JSONArray(response.get("employes").toString());
                            for(int i = 0;i<jsonArray.length();i++){
                                JSONObject obj = (JSONObject) jsonArray.get(i);
                                list_Emp.add(new Employe(
                                        (int)obj.get("id_employe"),
                                        obj.get("username").toString(),
                                        obj.get("password").toString(),
                                        obj.get("firstname").toString(),
                                        obj.get("lastname").toString(),
                                        obj.get("email").toString(),
                                        (int)obj.get("mobile_nubmer"),
                                        obj.get("service").toString(),
                                        obj.get("gender").toString(),
                                        obj.get("birth_date").toString(),
                                        obj.get("start_date").toString())
                                );
                            }

                            message = "Success";

                        } else {//En cas d'erreur
                            message = "Internal Error Please Try Later";
                        }

                        wait = false;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    message = "Cant Connect Server";
                    wait = false;
                }
            });
            requestQueue.add(jsonObjectRequest);

            while(wait){

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

            Toast.makeText(mContext, ""+message, Toast.LENGTH_LONG).show();

            employeAdapter = new EmployeAdapter(mContext, R.layout.employe_row, list_Emp);
            listView.setAdapter(employeAdapter);

        }

    }

    class registerEmployeTask extends AsyncTask<Integer, Integer, String> {

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

                if (mContext.mFingerprint.storChar(Fingerprint.BufferEnum.B1, employe.getId_employe())) {
                    /*
                    if (mContext.mFingerprint.getImage()) {
                        mContext.mFingerprint.upImage(1,mContext.getFilesDir() + "/finger.bmp");
                    }*/

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
