package com.msg91.sendotp.sample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class forgotpassword extends AppCompatActivity {
SharedPreferences sharedPreferencesce;
EditText pass,repass;
String mail;
Button frgt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        pass=findViewById(R.id.frgtpass_pass);
        repass=findViewById(R.id.frgtpass_confmpass);
        sharedPreferencesce=getSharedPreferences("asd",MODE_PRIVATE);
        frgt=findViewById(R.id.smsbtn);
        frgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail = sharedPreferencesce.getString("email", "*****");
                if (!(pass.getText().toString().isEmpty() || repass.getText().toString().isEmpty()) && (pass.getText().toString().equals(repass.getText().toString()))) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://hastalavistaresto.000webhostapp.com/civilregistry/forgetpass.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//If we are getting success from server
                                    Toast.makeText(forgotpassword.this, response, Toast.LENGTH_LONG).show();
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
//error handling
                                }

                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
//Adding parameters to request

                            params.put("mail", mail);
                            params.put("newpass",pass.getText().toString());

//returning parameter
                            return params;
                        }
                    };

//Adding the string request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(forgotpassword.this);
                    requestQueue.add(stringRequest);

                }
                else
                {
                   Toast.makeText(forgotpassword.this,"Enter values Correctly",Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}
