package com.msg91.sendotp.sample;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class IncomeTax extends AppCompatActivity  {
    EditText txt21, txt22, txt23, txt24;
    Button btn9, btn10;
    String tokenfour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_tax);
        txt21 = findViewById(R.id.bc_name);
        txt22 = findViewById(R.id.bc_place);
        txt23 = findViewById(R.id.bc_dob);
        txt24 = findViewById(R.id.bc_fathername);
        btn9 = findViewById(R.id.bc_apply);
        btn10 = findViewById(R.id.bc_pdfdwn);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(txt21.getText().toString().isEmpty() || txt22.getText().toString().isEmpty() || txt23.getText().toString().isEmpty() || txt24.getText().toString().isEmpty())) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://hastalavistaresto.000webhostapp.com/civilregistry/incometax.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

//If we are getting success from server
                                    if (response.equals("Success")) {

                                        Toast.makeText(IncomeTax.this, "Your application for Income Tax Certificate has been registered ", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(IncomeTax.this, "Form not Submitted", Toast.LENGTH_SHORT).show();
                                    }
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        for (int i = 0; i < jsonArray.length(); i++) {
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

                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
//Adding parameters to request

                            params.put("name", txt21.getText().toString());
                            params.put("fname", txt22.getText().toString());
                            params.put("income", txt23.getText().toString());
                            params.put("address", txt24.getText().toString());

//returning parameter
                            return params;
                        }
                    };

//Adding the string request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(IncomeTax.this);
                    requestQueue.add(stringRequest);

                } else {
                    Toast.makeText(IncomeTax.this, "enter values correctly", Toast.LENGTH_SHORT).show();
                }

            }


        });
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://hastalavistaresto.000webhostapp.com/civilregistry/retrival4.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//If we are getting success from server
                                //Toast.makeText(IncomeTax.this, response, Toast.LENGTH_LONG).show();
                                new SweetAlertDialog(IncomeTax.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("use this id for tracking application status")
                                        .setContentText("your id is")
                                        .setConfirmText(response)
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                            }
                                        })
                                        .show();
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json_obj = jsonArray.getJSONObject(i);
                                        tokenfour = json_obj.getString("name");
                                        new SweetAlertDialog(IncomeTax.this)
                                                .setTitleText(tokenfour)
                                                .show();

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

                        params.put("name", txt21.getText().toString());

//returning parameter
                        return params;
                    }
                };

//Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(IncomeTax.this);
                requestQueue.add(stringRequest);

            }
        });


    }
}






