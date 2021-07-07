package com.java.medicall;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.SQLException;



public class RecyclerAdapterRecenzii extends RecyclerView.Adapter<RecyclerAdapterRecenzii.ViewHolder> {

    private LayoutInflater inflater;
    public Queries o=new Queries();
    public Logare l=new Logare();
    Context context;
    int idContPacient=0;
    int idComp;
    public RecyclerAdapterRecenzii(Context c, int idComp, int idCont) throws SQLException {
        inflater = LayoutInflater.from(c);
        this.context=c;


                try {

                    o.recenziiMedic(idComp);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public int currentItem;
        public TextView itemRecenzie;
        public TextView itemNota;
        public TextView itemNumePacient;
        public ViewHolder(View itemView)  {
            super(itemView);
            itemRecenzie=(TextView) itemView.findViewById(R.id.item_review);
            itemNota=(TextView) itemView.findViewById(R.id.item_nota);
            itemNumePacient=(TextView) itemView.findViewById(R.id.nume_pacient);
            itemView.setOnClickListener((v)->{
                int position=getAdapterPosition();
                Snackbar.make(v,"Click detected on item"+ position,Snackbar.LENGTH_LONG).setAction("Action", null).show();
            });


        }

        private void onClick(View v) {

        }

    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v=inflater.inflate(R.layout.cardview_layout_review, viewGroup, false);
        ViewHolder viewHolder= null;

        viewHolder = new ViewHolder(v);

        return viewHolder;

    }

    public void onBindViewHolder(ViewHolder viewHolder, int i){


      viewHolder.itemRecenzie.setText(o.recenziiMedic.get(i));
      viewHolder.itemNota.setText("Nota:"+o.noteMedic.get(i).toString());
        try {
            viewHolder.itemNumePacient.setText(o.numePacient(o.idContRec.get(i)));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public int getItemCount(){

        return o.recenziiMedic.size();

    }
}
