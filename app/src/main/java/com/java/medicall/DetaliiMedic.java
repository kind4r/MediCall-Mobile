package com.java.medicall;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class DetaliiMedic extends AppCompatActivity
        {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    Queries o;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_medic);



        o=new Queries();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txtNume=(TextView) findViewById(R.id.txtNumme);
        TextView txtSpec=(TextView) findViewById(R.id.txtSpec);
        TextView txtNota=(TextView) findViewById(R.id.textNota);
        TextInputEditText textReview=(TextInputEditText) findViewById(R.id.textReview);

        Intent intent=getIntent();
        txtNume.setText(intent.getStringExtra("nume"));
        txtSpec.setText(intent.getStringExtra("specializare"));
        TextView review=(TextView) findViewById(R.id.textReview);
        SeekBar seekBarNota=(SeekBar) findViewById(R.id.seekBarNota);
        CardView adauga=(CardView) findViewById(R.id.adaugaReview);
        TextView scrie=(TextView) findViewById(R.id.textView5);
        try {
            boolean b=o.areProgramare(intent.getIntExtra("idCont",0), intent.getIntExtra("idCmp",0));
           // System.out.print();
           // scrie.setText(intent.getIntExtra("idCont", 1)+" "+intent.getIntExtra("idCmp",0)+" "+b);
            if(b==false)
            {   scrie.setText("Nu puteti acorda recenzii");
                seekBarNota.setEnabled(false);
                textReview.setEnabled(false);
                adauga.setEnabled(false);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        seekBarNota.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtNota.setText(String.valueOf(progress));

                System.out.print("\n"+progress+" ");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {



            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Intent intent1=getIntent();
        //Queries o=new Queries();



        recyclerView=(RecyclerView) findViewById(R.id.recycler_view_review);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        try {

            adapter=new RecyclerAdapterRecenzii(this, intent1.getIntExtra("idCmp",1), intent1.getIntExtra("idCont",1));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        recyclerView.setAdapter(adapter);


        adauga.setOnClickListener(new View.OnClickListener()

                                  {
                                      public void onClick(View v)
                                      {
                                          try {
                                              o.adaugaReview(intent1.getIntExtra("idCmp",1),seekBarNota.getProgress(),1,review.getText().toString());
                                              Toast.makeText(DetaliiMedic.this,"Recenzie adaugata!", Toast.LENGTH_LONG).show();
                                              adapter.notifyItemInserted(o.recenziiMedic.size());


                                          } catch (SQLException e) {
                                              e.printStackTrace();
                                          } catch (ClassNotFoundException e) {
                                              e.printStackTrace();
                                          }

                                      }
                                  }

        );


    }

            @Override
            public boolean onSupportNavigateUp() {
                onBackPressed();
                return true;
            }



}
