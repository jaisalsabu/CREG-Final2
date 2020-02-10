package com.msg91.sendotp.sample;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

public class BirthCertficate extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] sex = {"Male", "Female", "Others"};
    EditText txt9, txt10, txt11, txt12;
    Spinner gender;
    String token;
    Button btn3, btn4;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birth_certficate);
        txt9 = findViewById(R.id.bc_name);
        gender = findViewById(R.id.gender);
        txt10 = findViewById(R.id.bc_place);
        txt11 = findViewById(R.id.bc_dob);
        txt12 = findViewById(R.id.bc_fathername);
        btn3 = findViewById(R.id.bc_apply);
        gender.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sex);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(aa);
        btn4 = findViewById(R.id.bc_pdfdwn);
        txt11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(BirthCertficate.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                txt11.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
// datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(txt9.getText().toString().isEmpty() || txt10.getText().toString().isEmpty() || txt11.getText().toString().isEmpty() || txt12.getText().toString().isEmpty())) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://hastalavistaresto.000webhostapp.com/civilregistry/birthcertificate.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

//If we are getting success from server
                                    if (response.equals("Success")) {

                                        Toast.makeText(BirthCertficate.this, "Your application for Birth Certificate has been registered ", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(BirthCertficate.this, "Form not Submitted", Toast.LENGTH_SHORT).show();
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

                            params.put("name", txt9.getText().toString());
                            params.put("place", txt10.getText().toString());
                            params.put("DOB", txt11.getText().toString());
                            params.put("sex", gender.getSelectedItem().toString());
                            params.put("fathersname", txt12.getText().toString());

//returning parameter
                            return params;
                        }
                    };

//Adding the string request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(BirthCertficate.this);
                    requestQueue.add(stringRequest);

                } else {
                    Toast.makeText(BirthCertficate.this, "enter values correctly", Toast.LENGTH_SHORT).show();
                }

            }


        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://hastalavistaresto.000webhostapp.com/civilregistry/retrival1.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//If we are getting success from server
                            //Toast.makeText(BirthCertficate.this, response, Toast.LENGTH_LONG).show();
                            new SweetAlertDialog(BirthCertficate.this, SweetAlertDialog.WARNING_TYPE)
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
                                    token = json_obj.getString("name");
                                    new SweetAlertDialog(BirthCertficate.this)
                                            .setTitleText(token)
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

                    params.put("name",txt9.getText().toString());

//returning parameter
                    return params;
                }
            };

//Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(BirthCertficate.this);
                requestQueue.add(stringRequest);

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        Toast.makeText(BirthCertficate.this,"Sex not selected",Toast.LENGTH_SHORT).show();


    }

}





