package com.java.medicall;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecyclerAdapterProgramari extends RecyclerView.Adapter<RecyclerAdapterProgramari.ViewHolder> {

    private LayoutInflater inflater;

ArrayList<String> orarProgramari=new ArrayList<>();
    private ArrayList<Date> adapterList;
    private List<Object> items;
    Context context;
    int idCont;

Queries o=new Queries();
    public RecyclerAdapterProgramari(Context c, int idCont, ArrayList<String>orarProgramari) throws SQLException {
        inflater = LayoutInflater.from(c);
        this.context=c;
        this.idCont=idCont;

      // this.orarProgramari.addAll(orarProgramari);
        try {
              o.programarileMele(o.idPacient(idCont));


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public int currentItem;
        public TextView nume, specializare, dataOra;
        Button anulare;


        public ViewHolder(View itemView)  {
            super(itemView);
            nume=(TextView)itemView.findViewById(R.id.item_nume);
            specializare=(TextView)itemView.findViewById(R.id.item_specializare);
            dataOra=(TextView)itemView.findViewById(R.id.item_data);
            anulare=(Button) itemView.findViewById(R.id.buttonAnulare);

        }




        private void onClick(View v) {

        }
    }

    public RecyclerAdapterProgramari.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v=inflater.inflate(R.layout.cardview_layout_programari, viewGroup, false);
        RecyclerAdapterProgramari.ViewHolder viewHolder= null;

        viewHolder = new RecyclerAdapterProgramari.ViewHolder(v);

        return viewHolder;

    }



    @SuppressLint("ResourceAsColor")
    public void onBindViewHolder(RecyclerAdapterProgramari.ViewHolder viewHolder, int i){

        if(o.stareProgramare.get(i)==0) {
            viewHolder.anulare.setEnabled(false);

        }

        viewHolder.dataOra.setText(o.dateProgramari.get(i)+"");
        try {
            int idComp=o.CompidProgram(o.orarProgramari.get(i));

            viewHolder.nume.setText(o.medicComp(idComp));

            viewHolder.specializare.setText(o.specializareComp(idComp));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        viewHolder.anulare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b=new AlertDialog.Builder(context);
                b.setTitle("Programare");
                b.setMessage("Doriti sa anulati aceasta programare?");
                b.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try{
                            o.anuleazaProgramare(o.idProgramare.get(i));
                            o.orarProgramari.remove(i);
                            o.idProgramare.remove(i);
                            o.dateProgramari.remove(i);
                            notifyItemRemoved(i);
                            notifyItemRangeChanged(i, o.orarProgramari.size());

                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                });
                b.setNegativeButton("Nu", null);


                AlertDialog dialog=b.create();
                dialog.show();






            }
        });

    }

    public int getItemCount(){

        return o.orarProgramari.size();

    }
}


