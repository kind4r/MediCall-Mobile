package com.java.medicall;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class RecyclerAdapterOre extends RecyclerView.Adapter<RecyclerAdapterOre.ViewHolder> {
    ArrayList<Date> arr = new ArrayList<Date>();
    private LayoutInflater inflater;
    public Queries o=new Queries();
    public Logare l=new Logare();
    Context context;
    int idComp;
    String dataP;
    String numeMedic;
    Date dOra;
    int idCont;
    int idProgram;
    public RecyclerAdapterOre(Context c,ArrayList arr, String data, String medic, int idCont,int idProgram) throws SQLException {
        inflater = LayoutInflater.from(c);
        this.context=c;
        dataP=data;
        numeMedic=medic;

        this.idCont=idCont;
        this.idProgram=idProgram;





        this.arr=arr;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public int currentItem;

        public TextView ora;
        public ViewHolder(View itemView)  {
            super(itemView);
            ora=(TextView)itemView.findViewById(R.id.item_ora);


        }




        private void onClick(View v) {

        }
    }
@Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v=inflater.inflate(R.layout.cardview_layout_ora, viewGroup, false);
        RecyclerAdapterOre.ViewHolder viewHolder= null;

        viewHolder = new RecyclerAdapterOre.ViewHolder(v);

        return viewHolder;

    }



    public void onBindViewHolder(RecyclerAdapterOre.ViewHolder viewHolder, int i){

        viewHolder.ora.setText((CharSequence) arr.get(i));
        viewHolder.ora.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder b=new AlertDialog.Builder(context);
                b.setTitle("Programare");
                b.setMessage("Doriti sa efectuati o programare pe data "+dataP.toString()+", la ora "+arr.get(i)+", la medicul "+numeMedic+"?");
                b.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime d1=LocalDateTime.parse(dataP+" "+arr.get(i)+":00", f);
                        try {
                            if(o.existaProgramareLaOra( d1, o.idPacient(idCont))==false) {
                                o.efectuareProgramare(o.idPacient(idCont), idProgram, d1);
                                arr.remove(i);

                                notifyItemRemoved(i);
                                notifyItemRangeChanged(i, arr.size());

                            }
                            else {
                                Toast.makeText(context, "Aveti o programare in aceasta zi, la ora selectata!", Toast.LENGTH_LONG).show();
                            }

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

        return arr.size();
    }
}
