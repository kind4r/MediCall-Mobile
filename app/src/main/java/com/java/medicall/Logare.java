package com.java.medicall;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class Logare extends AppCompatActivity
    {
 int idCont=0;
    boolean b;
    TextView user;
    TextView parola;
    Queries q;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logare);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




         user=(TextView) findViewById(R.id.txtEmail);
         parola=(TextView) findViewById(R.id.txtParola);
        TextView crearecont=(TextView) findViewById(R.id.crearecont);
        crearecont.setOnClickListener(new View.OnClickListener()

                                      {
                                          public void onClick(View v)
                                          {
                                                  Intent myIntent = new Intent(Logare.this, CreareCont.class);

                                                  Logare.this.startActivity(myIntent);


                                          }
                                      }

        );

        CheckBox keep=(CheckBox)findViewById(R.id.checkBox);


        TextView log=(TextView) findViewById(R.id.txtLogin);

        log.setOnClickListener(
                new View.OnClickListener()

                {
                    public void onClick(View v)
                    {

                        try {
                            q=new Queries();
                            b= q.login(user.getText().toString(),parola.getText().toString());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                        idCont=q.idCont;
                        if (b == true) {

                            Intent myIntent = new Intent (Logare.this, Home.class);
                            myIntent.putExtra(user.getText().toString(),parola.getText().toString());
                            myIntent.putExtra("idCont", idCont);

                            // myIntent.putExtra("conectare", Conectare.conect());
                            Logare.this.startActivity(myIntent);

                        } else
                            Toast.makeText(getApplicationContext(), "Cont inexistent!", Toast.LENGTH_LONG).show();



                        SaveSharedPreferences ssp;
                        if(keep.isChecked()) {
                            ssp = new SaveSharedPreferences();
                            ssp.setUserName(Logare.this, user.getText().toString());
                        }


                    }
                }
        );



        if(SaveSharedPreferences.getUserName(Logare.this).length() == 0)
        {

        }
        else
        {
            Intent myIntent = new Intent(Logare.this, Home.class);

            Logare.this.startActivity(myIntent);

        }



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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
