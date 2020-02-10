package com.msg91.sendotp.sample;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends AppCompatActivity {
View root;
    List<Cheque> productList;
SwipeRefreshLayout s;
    //the recyclerview
    RecyclerView recyclerView;
SwipeRefreshLayout swipe;
    SharedPreferences sh;
    Chequeadapter adapter;
    EditText search;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         root= inflater.inflate(R.layout.fragment_status, container, false);
        loadProducts();
        recyclerView = root.findViewById(R.id.recylcerViewc);
        recyclerView.setHasFixedSize(true);
        search=root.findViewById(R.id.editText);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        swipe=root.findViewById(R.id.swiperefresh);
        sh=getApplicationContext().getSharedPreferences("Official",MODE_PRIVATE);
        productList = new ArrayList<>();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList.clear();
                loadProducts();
            }
        });
        return root;
    }

    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://hastalavistaresto.000webhostapp.com/civilregistry/cregret.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

swipe.setRefreshing(false);
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = array.length()-1; i >=0; i--) {


                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                //adding the product to product list
                                productList.add(new Cheque(
                                        product.getString("Trackid"),
                                        product.getString("FORM"),
                                        product.getString("Status")
                                ));
                            }


                             adapter = new Chequeadapter(getApplicationContext(), productList);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }


                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request


                //returning parameter
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    private void filter(String text) {
        ArrayList<Cheque> filteredList = new ArrayList<>();

        for (Cheque item : productList) {
            if (item.getTrackid().toLowerCase().contains(text.toLowerCase())||item.getForm().toLowerCase().contains(text.toLowerCase())||item
                    .getStatus().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }
    }

