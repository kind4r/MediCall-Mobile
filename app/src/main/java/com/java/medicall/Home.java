package com.java.medicall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.CardView;
import android.view.MenuInflater;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.SQLException;

public class Home extends AppCompatActivity
        //implements NavigationView.OnNavigationItemSelectedListener
{Queries q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


       // q=new Queries();

        CardView medici=(CardView) findViewById(R.id.cardView7);
        CardView programare=(CardView) findViewById(R.id.cardView6);
        CardView programarilemele=(CardView) findViewById(R.id.cardView5);
        MenuView.ItemView logout=(MenuView.ItemView) findViewById(R.id.action_settings);
        CardView desprenoi=(CardView) findViewById(R.id.cardView8);

        desprenoi.setOnClickListener(new View.OnClickListener()

                                  {
                                      public void onClick(View v)
                                      {

                                          Intent myIntent=new Intent(Home.this, DespreNoi.class);
                                          startActivity(myIntent);
                                      }
                                  }

        );


        medici.setOnClickListener(new View.OnClickListener()

                                  {


                                      public void onClick(View v)
                                      {



                                                  Intent i=getIntent();
                                                  Intent myIntent = new Intent(Home.this, Medic.class);
                                                  try { q=new Queries();
                                                      q.numeMedici();
                                                      q.denumireSpecializare();
                                                      myIntent.putExtra("idCont",i.getIntExtra("idCont",0));
                                                      myIntent.putExtra("numeMedici",q.nume);
                                                      myIntent.putExtra("specializare",q.specializare);
                                                      myIntent.putExtra("idComp",q.idComp );
                                                  } catch (SQLException e) {
                                                      e.printStackTrace();
                                                  } catch (ClassNotFoundException e) {
                                                      e.printStackTrace();
                                                  }

                                                  Home.this.startActivity(myIntent);



                                                                               }
                                  }

        );


        programarilemele.setOnClickListener(new View.OnClickListener()

                                  {


                                      public void onClick(View v)
                                      {


                                                  Intent i=getIntent();
                                                  Intent myIntent = new Intent(Home.this, ProgramarileMele.class);

                                                  myIntent.putExtra("idCont",i.getIntExtra("idCont",0));
                                                  myIntent.putExtra("numeMedici",q.nume);
                                                  myIntent.putExtra("orarProgramari",q.orarProgramari);
                                                  Home.this.startActivity(myIntent);


                                      }
                                  }

        );



        programare.setOnClickListener(new View.OnClickListener()

                                  {


                                      public void onClick(View v)
                                      {
                                          try {
                                              q=new Queries();

                                              q.toateSpecializarile();
                                          } catch (SQLException e) {
                                              e.printStackTrace();
                                          } catch (ClassNotFoundException e) {
                                              e.printStackTrace();
                                          }

                                                  Intent i=getIntent();
                                                  Intent myIntent = new Intent(Home.this, Programare.class);
                                                  myIntent.putExtra("idCont", i.getIntExtra("idCont",0));
                                                  myIntent.putExtra("toateSpecializarile", q.toateSpecializarile);
                                                  myIntent.putExtra("idSpecializare",q.idSpecializare);
                                                  Home.this.startActivity(myIntent);




                                      }
                                  }

 );





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {


            SaveSharedPreferences ssp = new SaveSharedPreferences();
            ssp.clearUserName(Home.this);
            Intent myIntent = new Intent(Home.this, Logare.class);
            Home.this.startActivity(myIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



}
