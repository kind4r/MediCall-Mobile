package com.java.medicall;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private LayoutInflater inflater;

    Context context;
    Queries q;
    int idCont;
    ArrayList<String> medici=new ArrayList<>();
    ArrayList<String> specializare=new ArrayList<>();
    ArrayList<Integer> idComp=new ArrayList<>();
    public RecyclerAdapter(Queries q, Context c, int idCont, ArrayList<String>medici, ArrayList<String> specializare, ArrayList<Integer> idComp) throws SQLException {
        inflater = LayoutInflater.from(c);
        this.context=c;
        this.idCont=idCont;
this.q=q;
          this.medici.addAll(medici);
          this.specializare.addAll(specializare);
        this.idComp.addAll(idComp);


    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public int currentItem;

        public TextView itemTitle;
        public TextView itemDetails;
        public TextView nota;
        public TextView itemMedie;
        public ViewHolder(View itemView)  {
            super(itemView);
            itemMedie=(TextView) itemView.findViewById(R.id.item_medie);

            itemTitle=(TextView) itemView.findViewById(R.id.item_title);
            itemDetails=(TextView) itemView.findViewById(R.id.item_detail);


            itemTitle.setOnClickListener(this::onClick);
            itemView.setOnClickListener((v)->{
                int position=getAdapterPosition();
               // Snackbar.make(v,"Click detected on item"+ position,Snackbar.LENGTH_LONG).setAction("Action", null).show();



            });


        }



        private void onClick(View v) {

        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v=inflater.inflate(R.layout.cardview_layout, viewGroup, false);
        ViewHolder viewHolder= null;

            viewHolder = new ViewHolder(v);

        return viewHolder;

    }

    public void onBindViewHolder(ViewHolder viewHolder, int i){

        DecimalFormat nf= new DecimalFormat("#.0");
        DecimalFormat nf2= new DecimalFormat("#");

        viewHolder.itemTitle.setText(medici.get(i));

                viewHolder.itemDetails.setText(specializare.get(i));
                try {
                    float medie=q.medie(q.idComp.get(i));


                    if(medie>0)
                       if(medie==10)
                           viewHolder.itemMedie.setText(String.valueOf(nf2.format( medie) )+"/10");

                       else
                      viewHolder.itemMedie.setText(String.valueOf( nf.format(medie))+"/10");
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                  }


        viewHolder.itemTitle.setOnClickListener((v)->{

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent myIntent = new Intent(context, DetaliiMedic.class);
                    myIntent.putExtra("nume", viewHolder.itemTitle.getText());
                    myIntent.putExtra("specializare", viewHolder.itemDetails.getText());
                    myIntent.putExtra("idCmp",idComp.get(i));
                    myIntent.putExtra("idCont",idCont);
                    context.startActivity(myIntent);

                }

            }).start();




        });


    }

    public int getItemCount(){

        return medici.size();
    }
}
