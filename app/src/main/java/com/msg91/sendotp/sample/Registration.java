package com.msg91.sendotp.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Registration extends AppCompatActivity {
    EditText txt1,txt2,txt3,txt4,txt5,txt6;
    Button btn1;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        txt1=findViewById(R.id.reg_name);
        txt2=findViewById(R.id.reg_email);
        txt3=findViewById(R.id.reg_phno);
        txt4=findViewById(R.id.reg_adrress);
        txt5=findViewById(R.id.reg_pass);
        txt6=findViewById(R.id.reg_cnfrmpass);
        btn1 = findViewById(R.id.register);
        tv1 = findViewById(R.id.login_now);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(txt1.getText().toString().isEmpty()||txt2.getText().toString().isEmpty()||txt3.getText().toString().isEmpty()||txt4.getText().toString().isEmpty()||txt5.getText().toString().isEmpty()||txt6.getText().toString().isEmpty()&&(txt5.getText().toString().equals(txt6.getText().toString()))))
                {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://hastalavistaresto.000webhostapp.com/civilregistry/crregistration.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

//If we are getting success from server
                                    if(response.equals("Success"))
                                    {
                                        Intent ii= new Intent(getApplicationContext(),optionspage.class);
                                        Toast.makeText(Registration.this,"You have Succesfully Registered with Creg",Toast.LENGTH_LONG).show();
                                        startActivity(ii);
                                    }
                                    else
                                    {
                                        Toast.makeText(Registration.this,"Unsuccessfull attempt",Toast.LENGTH_SHORT).show();
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

                            params.put("name",txt1.getText().toString());
                            params.put("email_id",txt2.getText().toString());
                            params.put("phno",txt3.getText().toString());
                            params.put("address",txt4.getText().toString());
                            params.put("password",txt5.getText().toString());

//returning parameter
                            return params;
                        }
                    };

//Adding the string request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(Registration.this);
                    requestQueue.add(stringRequest);

                }
                else
                {
                    Toast.makeText(Registration.this,"enter values correctly", Toast.LENGTH_LONG).show();

                }
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iy=new Intent(getApplicationContext(),Login.class);
                startActivity(iy);
            }
        });
    }
}






