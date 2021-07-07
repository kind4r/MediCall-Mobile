package com.java.medicall;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecycleAdapterDisp extends RecyclerView.Adapter<RecycleAdapterDisp.ViewHolder> {

    private LayoutInflater inflater;
    public Queries o=new Queries();

    public LinearLayoutManager llm;
    private static RecyclerAdapterOre Adapt;
    private ArrayList<Date> adapterList;

    ArrayList<Integer>idProgram=new ArrayList<>();
    ArrayList<Integer>mediciDisponibili=new ArrayList<>();
    private List<Object> items;
    Context context;

    int idCont;

    String spec;
    String dataText="";
    Date dOra;
    public RecycleAdapterDisp(Context c, String specializare, Date data, String d, int idCont, ArrayList<Integer> mediciDisponibili, ArrayList<Integer> idProgram) throws SQLException {
        inflater = LayoutInflater.from(c);
        this.context = c;
        this.idProgram.addAll(idProgram);
        dataText = d;
        dOra = data;
        this.idCont = idCont;

        this.mediciDisponibili.addAll(mediciDisponibili);

        spec=specializare;


        adapterList = new ArrayList<Date>();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public int currentItem;
        public TextView nume, specializare;
        public RecyclerView rv;
        public RecyclerView.LayoutManager layoutManager;
        public RecyclerView.Adapter adapter;
        public ViewHolder(View itemView)  {
            super(itemView);
           nume=(TextView)itemView.findViewById(R.id.item_nume);
           specializare=(TextView)itemView.findViewById(R.id.item_specializare);
           rv=(RecyclerView)itemView.findViewById(R.id.recycler_view);

        }


        private void onClick(View v) {

        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v=inflater.inflate(R.layout.cardview_layout_disp, viewGroup, false);
        ViewHolder viewHolder= null;

        viewHolder = new ViewHolder(v);

        return viewHolder;

    }

    public void onBindViewHolder(ViewHolder viewHolder, int i){

                try {
                    o.oreDisponibile(i);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

        viewHolder.layoutManager=new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false );
        viewHolder.rv.setLayoutManager(viewHolder.layoutManager);

        try {
            viewHolder.adapter=new RecyclerAdapterOre(context, o.oreDisponibile, dataText,o.medicCompetenta(mediciDisponibili.get(i)), idCont,idProgram.get(i));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        viewHolder.rv.setAdapter(viewHolder.adapter);


                try {
                    viewHolder.nume.setText(o.medicCompetenta(o.mediciDisponibili.get(i)));
                    viewHolder.specializare.setText(spec);

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


    }

    public int getItemCount(){

        return o.mediciDisponibili.size();

    }
}

