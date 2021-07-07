package com.java.medicall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.sql.SQLException;

public class Medic extends AppCompatActivity
         {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    Queries q;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        q=new Queries();
        setContentView(R.layout.activity_medic);
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        Intent i=getIntent();
        try {

            adapter=new RecyclerAdapter(q,this, i.getIntExtra("idCont",0), i.getStringArrayListExtra("numeMedici"), i.getStringArrayListExtra("specializare"),i.getIntegerArrayListExtra("idComp") );

        } catch (SQLException e) {
            e.printStackTrace();
        }

        recyclerView.setAdapter(adapter);


        TextView itemTitle=(TextView) findViewById(R.id.item_title);
        TextView itemDetail=(TextView) findViewById(R.id.item_detail);



    }







}
