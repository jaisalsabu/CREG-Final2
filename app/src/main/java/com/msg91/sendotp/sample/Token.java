package com.msg91.sendotp.sample;


import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Token extends AppCompatActivity {
        Button btnCreate,btnload;
        EditText txttox;
        String name,token;
        SharedPreferences sharedPreferencess;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_token);
            txttox = findViewById(R.id.editText);
            btnload=findViewById(R.id.button);
            btnCreate = findViewById(R.id.button2);
            btnload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                            name = sharedPreferencess.getString("name", "");
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://hastalavistaresto.000webhostapp.com/civilregistry/retrival1.php",
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
//If we are getting success from server
                                            Toast.makeText(Token.this, response, Toast.LENGTH_LONG).show();
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

                                    params.put("find",name);

//returning parameter
                                    return params;
                                }
                            };

//Adding the string request to the queue
                            RequestQueue requestQueue = Volley.newRequestQueue(Token.this);
                            requestQueue.add(stringRequest);


                        }
                    });

            btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createPdf(txttox.getText().toString());
                }
            });
        }

        private void createPdf(String sometext) {
            // create a new document
            PdfDocument document = new PdfDocument();
            // crate a page description
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
            // start a page
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            canvas.drawText(sometext, 80, 50, paint);
            //canvas.drawt
            // finish the page
            document.finishPage(page);
// draw text on the graphics object of the page
            // Create Page 2
            pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 2).create();
            page = document.startPage(pageInfo);
            canvas = page.getCanvas();
            paint = new Paint();
            paint.setColor(Color.BLUE);
            canvas.drawCircle(100, 100, 100, paint);
            document.finishPage(page);
            // write the document content
            String directory_path = Environment.getExternalStorageDirectory().getPath() + "/mypdf/";
            File file = new File(directory_path);
            if (!file.exists()) {
                file.mkdirs();
            }
            String targetPdf = directory_path + "application.pdf";
            File filePath = new File(targetPdf);
            try {
                document.writeTo(new FileOutputStream(filePath));
                Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Log.e("main", "error " + e.toString());
                Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
            }
            // close the document
            document.close();
        }
}