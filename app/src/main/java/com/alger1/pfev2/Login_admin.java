package com.alger1.pfev2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alger1.pfev2.navigation.Main2Activity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import javax.xml.transform.Templates;

public class Login_admin extends AppCompatActivity {

    TextView textView;
    String url,url2;
    Button connexion;
    Button forgotpassword;
    TextInputLayout textInputUsername;
    TextInputLayout textInputPassword;
    TextInputLayout textInputEmail;
    ProgressDialog prog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        textView=findViewById(R.id.admin);
        Typeface myCostumFont=Typeface.createFromAsset(getAssets(),"fonts/GloriaHallelujah.ttf");
        textView.setTypeface(myCostumFont);
        textInputUsername= findViewById(R.id.username);
        textInputPassword= findViewById(R.id.password);
        connexion=(Button) findViewById(R.id.post);
        forgotpassword=(Button) findViewById(R.id.forgetpassword);
        requestQueue = Volley.newRequestQueue(this);


        //Action Clic bouton Connecter
        connexion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!validationUsername() | !validationPassword()){
                    return;
                }else {
                    url = "http://192.168.1.5:8080/PROJECT/AuthentificationAdminApp?username=" + textInputUsername.getEditText().getText() + "&password=" + textInputPassword.getEditText().getText();
                    //creation d'un progress Dialog
                    prog = new ProgressDialog(view.getContext());
                    prog.setMessage("Please Wait");
                    prog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    prog.setCancelable(false);
                    prog.show();


                    //importer le contenu des EditTexts
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                //si l'emplye existe
                                if ((boolean) response.get("success") == true) {
                                    Toast.makeText(getApplicationContext(), "" + response.get("result") + " : " + response.get("message"), Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(Login_admin.this,Main2Activity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "" + response.get("message"), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            prog.dismiss();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Cant Connect Server", Toast.LENGTH_LONG).show();
                            prog.dismiss();
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                }}
        });



        //Action Clic bouton ForgotPassword
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder mBuilder= new AlertDialog.Builder(Login_admin.this);
                View mView =getLayoutInflater().inflate(R.layout.dialogue_forget_password,null);
                textInputEmail=mView.findViewById(R.id.email);
                //relation entre java et activity XML
                Button forget=(Button)mView.findViewById(R.id.send);
                Button cancel=(Button)mView.findViewById(R.id.cancel);
                //Action d'envoyé le mot de pass a ton email
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                forget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        url2="http://192.168.1.5:8080/PROJECT/Recoverpassword?email="+textInputEmail.getEditText().getText();
                        prog = new ProgressDialog(view.getContext());
                        prog.setMessage("Please Wait");
                        prog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        prog.setCancelable(false);
                        prog.show();
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url2, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    //si l'email existe
                                    if((boolean)response.get("success")==true){

                                        Toast.makeText(getApplicationContext(), ""+response.get("message"), Toast.LENGTH_LONG).show();
                                        dialog.cancel();
                                    }
                                    //sinon
                                    else{
                                        Toast.makeText(getApplicationContext(), "ERROR : "+response.get("message"), Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                prog.dismiss();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Cant Connect Server", Toast.LENGTH_LONG).show();
                                prog.dismiss();
                            }
                        });
                        requestQueue.add(jsonObjectRequest);
                    }
                });
                //annuler la demande
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

            }
        });



    }

    public  boolean validationUsername(){
        String usernameInput= textInputUsername.getEditText().getText().toString().trim();
        if(usernameInput.isEmpty()){
            textInputUsername.setError("Field can't be empty");
            return false;
        }else {
            textInputUsername.setError(null);
            return true;
        }
    }

    public  boolean validationPassword(){
        String passwordInput= textInputPassword.getEditText().getText().toString().trim();
        if(passwordInput.isEmpty()){
            textInputPassword.setError("Field can't be empty");
            return false;
        }else {
            textInputPassword.setError(null);
            return true;
        }
    }

}
