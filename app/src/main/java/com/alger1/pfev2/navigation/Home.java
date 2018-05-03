package com.alger1.pfev2.navigation;

import android.app.ProgressDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.alger1.pfev2.R;
import com.alger1.pfev2.dao.EmployeDao;
import com.alger1.pfev2.model.Pointage;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.Inflater;

/**
 * Created by ISLEM-PC on 4/30/2018.
 */

public class Home extends Fragment {
    TextView textView,loc,loc2;
    private Main2Activity mContext;
    int res,score;
    Button identification;
    LocationListener locationListener;
    LocationManager locationManager;
    ProgressDialog progressDialog;
    SimpleDateFormat date;
    SimpleDateFormat time;
    Date date1;
    RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        getLocation();

        identification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date1=new Date();
                Log.e("date",""+date.format(date1));
                Log.e("time",""+time.format(date1));
                new AssTask().execute();
            }
        });

    }

    private void init(){
        textView=(TextView)getView().findViewById(R.id.pageid);
        mContext = (Main2Activity) getActivity();
        identification = getView().findViewById(R.id.identification);
        loc=(TextView)getView().findViewById(R.id.location);
        loc2=(TextView)getView().findViewById(R.id.location2);
        date=new SimpleDateFormat("yyyy-MM-dd");
        time=new SimpleDateFormat("HH:mm:ss");

    }

    class AssTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... integers) {
            boolean exeSucc = false;

            if (!mContext.mFingerprint.getImage()) {
                return null;
            }

            if (mContext.mFingerprint.genChar(Fingerprint.BufferEnum.B1)) {
                int[] result = null;
                int exeCount = 0;

                do {
                    exeCount++;
                    result = mContext.mFingerprint.search(Fingerprint.BufferEnum.B1, 0, 1000);

                } while (result == null && exeCount < 3);

                if (result != null) {
                    res = result[0];
                    score = result[1];

                    return "ok";
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
            if(s == null){
                Toast.makeText(mContext,"failed to get fingerprint",Toast.LENGTH_SHORT).show();
            }else {

                requestQueue = Volley.newRequestQueue(mContext);
                textView.setText(" = "+res+" Score = "+score);//pageID
                Toast.makeText(mContext,"success",Toast.LENGTH_SHORT).show();

                String url = "http://192.168.1.110:8080/PROJECT/Pointer?longitude="
                        +Double.parseDouble(loc.getText().toString())+
                        "&latitude="+Double.parseDouble(loc2.getText().toString())+
                        "&time="+time.format(date1).toString()+
                        "&date="+date.format(date1).toString()+
                        "&type=entry"+
                        "&username="+new EmployeDao().getEmployeUsernameById(res,mContext);

                //importer le contenu des EditTexts
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Si il y a aucun erreur
                            if ((boolean) response.get("success") == true) {
                                Toast.makeText(mContext,"success",Toast.LENGTH_LONG).show();
                            } else {//En cas d'erreur
                                Toast.makeText(mContext,"Failed",Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext,"No Connection",Toast.LENGTH_LONG).show();
                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
            progressDialog.cancel();
        }

    }

    private void getLocation(){

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                loc.setText("" + location.getLongitude());
                loc2.setText("" + location.getLatitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(mContext, "Please enable GPS", Toast.LENGTH_SHORT).show();
            }
        };

        try {
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        } catch (SecurityException E) {
            Toast.makeText(mContext, "Need Permissions", Toast.LENGTH_SHORT).show();
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 3, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 3, locationListener);

    }

}
