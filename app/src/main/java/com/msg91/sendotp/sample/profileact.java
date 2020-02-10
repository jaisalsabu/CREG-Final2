package com.msg91.sendotp.sample;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;


public class profileact extends AppCompatActivity {
    TextView idpr,namepr,emailpr,phonepr,addresspr;
    SharedPreferences sharedPreferences;
    String pid,pname,pemail,pphone,paddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileact);
        idpr=findViewById(R.id.textView4);
        namepr=findViewById(R.id.textView7);
        emailpr=findViewById(R.id.textView8);
        phonepr=findViewById(R.id.textView9);
        addresspr=findViewById(R.id.textView10);
        sharedPreferences=getSharedPreferences("asd",MODE_PRIVATE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://hastalavistaresto.000webhostapp.com/civilregistry/profler.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//If we are getting success from server
                        Toast.makeText(profileact.this, response, Toast.LENGTH_LONG).show();
                        Toast.makeText(profileact.this,sharedPreferences.getString("email","*****"),Toast.LENGTH_LONG).show();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json_obj = jsonArray.getJSONObject(i);
                                pid = json_obj.getString("Id");
                                pname=json_obj.getString("Name");
                                pemail=json_obj.getString("Email");
                                pphone=json_obj.getString("Phone");
                                paddress=json_obj.getString("Address");
                                idpr.setText("   "+pid);
                                namepr.setText("   "+pname);
                                emailpr.setText("   "+pemail);
                                phonepr.setText("   "+pphone);
                                addresspr.setText("   "+paddress);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//error handling
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//Adding parameters to request

                params.put("email",sharedPreferences.getString("email","*****"));

//returning parameter
                return params;
            }
        };

//Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(profileact.this);
        requestQueue.add(stringRequest);

    }

}

