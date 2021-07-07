package com.java.medicall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Disponibilitate extends AppCompatActivity
       {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<Integer> idPr=new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disponibilitate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_disponibilitate);
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view2);
        layoutManager=new LinearLayoutManager(this );
        recyclerView.setLayoutManager(layoutManager);
        Intent i=getIntent();
        Date d=new Date();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);



        try {
            d = format.parse(i.getStringExtra("dataOra"));

            idPr=i.getIntegerArrayListExtra("idProgram");

                adapter = new RecycleAdapterDisp(this, i.getStringExtra("specializare"), d, i.getStringExtra("data").toString(),i.getIntExtra("idCont",0), i.getIntegerArrayListExtra("mediciDisponibili"), idPr);
                recyclerView.setAdapter(adapter);

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ParseException e) {
            e.printStackTrace();
        }

            TextView ora= (TextView)findViewById(R.id.item_ora);


    }

           @Override
           public boolean onSupportNavigateUp() {
               onBackPressed();
               return true;
           }





}
