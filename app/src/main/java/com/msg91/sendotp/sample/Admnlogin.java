package com.msg91.sendotp.sample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Admnlogin extends AppCompatActivity {
EditText admnname,admnpass;
Button btbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admnlogin);
        admnname=findViewById(R.id.admnlogin_email);
        admnpass=findViewById(R.id.admnlogin_pass);
        btbtn=findViewById(R.id.login_proceedo);
        btbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(admnname.getText().toString().isEmpty()||admnpass.getText().toString().isEmpty()))
                {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://hastalavistaresto.000webhostapp.com/civilregistry/admnlog.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//If we are getting success from server
                                    if (response.equals("Success")) {
                                        Toast.makeText(Admnlogin.this,"Logged in Succesfully", Toast.LENGTH_LONG).show();
                                        Intent ii = new Intent(getApplicationContext(),adminview.class);
                                        startActivity(ii);
                                    }
                                    else
                                    {
                                        Toast.makeText(Admnlogin.this,"Unsuccessfull attempt",Toast.LENGTH_SHORT).show();
                                    }
                                    try {
                                        JSONArray jsonArray=new JSONArray(response);
                                        for(int i=0;i<jsonArray.length();i++){
                                            JSONObject json_obj = jsonArray.getJSONObject(i);

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
//You can handle error here if you want
                                }

                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<>();
//Adding parameters to request

                            params.put("username",admnname.getText().toString());
                            params.put("password",admnpass.getText().toString());

//returning parameter
                            return params;
                        }
                    };

//Adding the string request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(Admnlogin.this);
                    requestQueue.add(stringRequest);

                }
                else
                {
                    Toast.makeText(Admnlogin.this,"enter values correctly", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
