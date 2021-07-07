package com.java.medicall;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Programare extends AppCompatActivity
         {
    Date d;
    String specializare;
             ArrayList<String> toateSpecializarile=new ArrayList<String>();
    Queries o;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programare);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button data;
        data=(Button) findViewById(R.id.Data);


        EditText dataTxt=(EditText)findViewById(R.id.editTextData);


        data.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int zi = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                int luna = mcurrentTime.get(Calendar.MONTH);
                int an=mcurrentTime.get(Calendar.YEAR);
               DatePickerDialog datePicker;
                datePicker = new DatePickerDialog(Programare.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker dp, int anSelectat, int lunaSelectata, int ziSelectata) {
                        if(lunaSelectata<10&&ziSelectata<10)
                            dataTxt.setText( anSelectat+"-0"+(lunaSelectata+1)+"-0"+ziSelectata);
                        else if(lunaSelectata>=10&&ziSelectata<10)
                            dataTxt.setText(anSelectat+"-"+(lunaSelectata+1)+"-0"+ziSelectata);
                        else if (lunaSelectata<10&&ziSelectata>=10)
                            dataTxt.setText( anSelectat+"-0"+(lunaSelectata+1)+"-"+ziSelectata);
                    }
                },an, luna, zi);
                datePicker.setTitle("Select Time");
                datePicker.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePicker.show();

            }
        });

        Spinner spnSpecializari=(Spinner)findViewById(R.id.spnSpecializare);



toateSpecializarile.addAll(getIntent().getStringArrayListExtra("toateSpecializarile"));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, toateSpecializarile);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSpecializari.setAdapter(arrayAdapter);
        spnSpecializari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String specializare = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + specializare,  Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        CardView verifica=(CardView) findViewById(R.id.butonVerifica);
        specializare=spnSpecializari.getSelectedItem().toString();
        int pozitie=0;
        for(int i = 0; i<toateSpecializarile.size(); i++)
            if(toateSpecializarile.get(i).equals(specializare))
                pozitie=i;
        verifica.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                     String date=dataTxt.getText().toString();

                     DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                     try {       o=new Queries();

                         Intent i=getIntent();
                         d = format.parse(date);
                         o.verificaDisponibilitate(d, getIntent().getIntegerArrayListExtra("idSpecializare").get(spnSpecializari.getSelectedItemPosition()));
                        Intent myIntent=new Intent(Programare.this, Disponibilitate.class);
                         myIntent.putExtra("dataOra",date);
                         myIntent.putExtra("data", dataTxt.getText().toString());
                         myIntent.putExtra("specializare",spnSpecializari.getSelectedItem().toString());
                         myIntent.putExtra("idProgram", o.idProgram);
                         myIntent.putExtra("idCont", i.getIntExtra("idCont",0));
                         myIntent.putExtra("mediciDisponibili", o.mediciDisponibili);
                         Programare.this.startActivity(myIntent);

                    System.out.println(d);
                     } catch (ParseException e) {
                         e.printStackTrace();
                     } catch (SQLException e) {
                         e.printStackTrace();
                     } catch (ClassNotFoundException e) {
                         e.printStackTrace();
                     }


            }
        });

    }




    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), Home.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }








}
