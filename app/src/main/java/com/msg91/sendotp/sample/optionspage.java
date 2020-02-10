package com.msg91.sendotp.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class optionspage extends AppCompatActivity {
    Button op1,op2,op3,op4;
    Toolbar toolt;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optionspage);
        op1 = findViewById(R.id.birthcertificate);
        op2 = findViewById(R.id.deathcertificate);
        op3 = findViewById(R.id.Votersid);
        toolt=findViewById(R.id.toolbar3);
        op4 = findViewById(R.id.Incometax);
        toolt.inflateMenu(R.menu.menu_bar);
        toolt.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.aa:
                        startActivity(new Intent(optionspage.this, Trackstat.class));
                    case R.id.ab:
                        startActivity(new Intent(optionspage.this,profileact.class));
                }
                return false;
            }
        });

        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ione = new Intent(getApplicationContext(), BirthCertficate.class);
                startActivity(ione);
            }
        });
        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itwo = new Intent(getApplicationContext(), DeathCertificate.class);
                startActivity(itwo);
            }
        });
        op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ithree = new Intent(getApplicationContext(), Votersid.class);
                startActivity(ithree);
            }
        });
        op4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ifour = new Intent(getApplicationContext(), IncomeTax.class);
                startActivity(ifour);
            }
        });
    }

}
