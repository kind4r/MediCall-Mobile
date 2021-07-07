package com.java.medicall;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CreareCont extends AppCompatActivity
        {
Queries q;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creare_cont);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        TextView creareCont=(TextView) findViewById(R.id.creare_cont);
        EditText nume=(EditText) findViewById(R.id.numePacient);
        EditText nrTelefom=(EditText) findViewById(R.id.telefonPacient);
        EditText email=(EditText) findViewById(R.id.emailPacient);
        EditText dataNasterii=(EditText) findViewById(R.id.dataNasterii);
        EditText parola=(EditText) findViewById(R.id.parola);
        EditText repetaParola=(EditText) findViewById(R.id.repetaparola);
        Spinner spnBF=(Spinner) findViewById(R.id.spinnerBF);
        q=new Queries();
        ArrayList<String> MF=new ArrayList<>();
        MF.add("M");
        MF.add("F");
        MF.add("?");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, MF);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBF.setAdapter(arrayAdapter);
        spnBF.setSelection(1);
        dataNasterii.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int zi = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                int luna = mcurrentTime.get(Calendar.MONTH);
                int an=mcurrentTime.get(Calendar.YEAR);
                DatePickerDialog datePicker;
                datePicker = new DatePickerDialog(CreareCont.this,android.R.style.Widget_Holo_Light_PopupWindow, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker dp, int anSelectat, int lunaSelectata, int ziSelectata) {

                        if(lunaSelectata<10&&ziSelectata<10)
                            dataNasterii.setText( anSelectat+"-0"+(lunaSelectata+1)+"-0"+ziSelectata);
                        else if(lunaSelectata>=10&&ziSelectata<10)
                            dataNasterii.setText(anSelectat+"-"+(lunaSelectata+1)+"-0"+ziSelectata);
                        else if (lunaSelectata<10&&ziSelectata>=10)
                            dataNasterii.setText( anSelectat+"-0"+(lunaSelectata+1)+"-"+ziSelectata);

                    }
                },an, luna, zi);
                datePicker.setTitle("Select Time");
                datePicker.show();

            }
        });



        creareCont.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
                if(!nume.getText().toString().matches("") && !email.getText().toString().matches("") && !dataNasterii.getText().toString().matches("") && !parola.getText().toString().matches("") && !repetaParola.getText().toString().matches("") && !nrTelefom.getText().toString().matches("")) {

                        if (parola.getText().toString().matches("(?=.*?[0-9])(?=.*?[A-Za-z]).+"))

                            if (parola.getText().toString().equals(repetaParola.getText().toString()))

                                if (email.getText().toString().matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")) {
                                    try {
                                        if (q.emailUnic(email.getText().toString()) == true) {
                                            if (nrTelefom.getText().toString().matches("^\\d{10}$")) {
                                                if (q.nrTelefonUnic(nrTelefom.getText().toString()) == true) {
                                                    try {

                                                        q.adaugaCont(email.getText().toString(), parola.getText().toString());

                                                        q.adaugaPacient(nume.getText().toString(), nrTelefom.getText().toString(), spnBF.getSelectedItem().toString(), f.parse(dataNasterii.getText().toString()), q.ultimulCont() );

                                                        Toast.makeText(CreareCont.this, "Contul dvs a fost creat!", Toast.LENGTH_LONG).show();


                                                    } catch (SQLException e) {
                                                        e.printStackTrace();
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    } catch (ClassNotFoundException e) {
                                                        e.printStackTrace();
                                                    }
                                                } else {
                                                    Toast.makeText(CreareCont.this, "Acest numar de telefon este deja inregistrat!", Toast.LENGTH_LONG).show();

                                                }
                                            } else {
                                                Toast.makeText(CreareCont.this, "Numar de telefon incorect!", Toast.LENGTH_LONG).show();
                                            }
                                        } else {


                                            Toast.makeText(CreareCont.this, "Exista deja un cont cu acest email!", Toast.LENGTH_LONG).show();

                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(CreareCont.this, "Email incorect!", Toast.LENGTH_LONG).show();
                                }

                            else {
                                Toast.makeText(CreareCont.this, "Parolele nu corespund!", Toast.LENGTH_LONG).show();

                            }

                        else {
                            Toast.makeText(CreareCont.this, "Parola trebuie sa contina litere si cifre!", Toast.LENGTH_LONG).show();


                        }


                }

                else {
                    Toast.makeText(CreareCont.this, "Exista campuri necompletate!",Toast.LENGTH_LONG);
                }


            }
        });



    }

            @Override
            public boolean onSupportNavigateUp() {
                onBackPressed();
                return true;
            }


}
