package com.java.medicall;

import android.widget.SeekBar;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Queries {
    static ArrayList<String> nume=new ArrayList<String>();
    static ArrayList<String> specializare=new ArrayList<String>();
    static ArrayList<Integer> idMedic=new ArrayList<Integer>();

    static ArrayList<Integer> idComp=new ArrayList<Integer>();
    static ArrayList<String> toateSpecializarile=new ArrayList<String>();
    static ArrayList<Integer> idSpecializare=new ArrayList<Integer>();
    static ArrayList<String> mediciSpecializare=new ArrayList<String>();
    static ArrayList<Integer> mediciDisponibili=new ArrayList<Integer>();
    static ArrayList<Integer> idProgram=new ArrayList<Integer>();
    static ArrayList<Date> dataOraInceput=new ArrayList<Date>();
    static ArrayList<Date> dataOraSfarsit=new ArrayList<Date>();
    static ArrayList<String> oreDisponibile=new ArrayList<String>();
    static ArrayList<Date> oreIndisponibile=new ArrayList<Date>();
    static ArrayList<String> recenziiMedic=new ArrayList<>();
    static ArrayList<Integer> noteMedic=new ArrayList<>();
    static ArrayList<String>dateProgramari=new ArrayList<>();
    static ArrayList<Integer>orarProgramari=new ArrayList<>();
    static ArrayList<Integer>idProgramare=new ArrayList<>();
    static ArrayList<Integer> stareProgramare=new ArrayList<>();
    static ArrayList<Integer> idContRec=new ArrayList<>();
    static int idCont=0;

    static Connection conect;
    int ore=0;




public Queries(){
    try {
        conect = Conectare.conect();
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
}



    String ConnectionResult = "";

   static boolean login(String Username, String parola) throws SQLException, ClassNotFoundException {

        String ConnectionResult = "";

        boolean b = false;

            Statement stmt = conect.createStatement();
            String email = "";
            String pass = "";

            String q = "Select email, parola, id_cont from conturi";
            ResultSet rs = stmt.executeQuery(q);//Conectare.selectare(q);//
            while (rs.next()) {
                email = rs.getString(1);
                pass = rs.getString(2);
                idCont=rs.getInt(3);
                System.out.print(email + ", " + pass + "\n");
                if (email.equals(Username) && pass.equals(parola)) {
                    b = true;
                    break;
                }

        }
        return b;
    }
    static void programarileMele(int idPacient) throws SQLException, ClassNotFoundException {
        String s="select idProgramare, dataOra, idProgram from programari where idPacient='"+idPacient+"'";
        Statement st=conect.createStatement();
        dateProgramari.clear();
        orarProgramari.clear();
        idProgramare.clear();
        stareProgramare.clear();
        ResultSet rs=st.executeQuery(s);
        Date d;
        SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy HH:mm", new Locale("ro"));

        int idProgram;
        while(rs.next()) {
            d = rs.getTimestamp(2);
            idProgram = rs.getInt(3);
            dateProgramari.add(df.format(d));
            orarProgramari.add(idProgram);
            idProgramare.add(rs.getInt(1));
            if(d.before(new Date()))
                stareProgramare.add(0);
            else stareProgramare.add(1);

        }
    }

    static void anuleazaProgramare(int idProgramare) throws SQLException, ClassNotFoundException {
        String s="delete from programari where idProgramare='"+idProgramare+"'";
        Conectare.inserare(s);
    }

   static void numeMedici() throws SQLException, ClassNotFoundException {
idComp.clear();
nume.clear();
            Statement st=conect.createStatement();

            int i=0;
            String n="";
            int id=0, idC=0, stare=0;
            String q="select m.numeMedic, m.idMedic,cm.idComp, cm.idSpecializare, cm.stare from medici m, competente_medici cm where m.idMedic=cm.idMedic order by m.numeMedic";
            ResultSet rs = st.executeQuery(q);//Conectare.selectare(q);
            while (rs.next()) {
                n=rs.getString(1);
                id=rs.getInt(2);
                idC=rs.getInt(3);
                stare=rs.getInt(4);
                if(stare!=1)
                {idComp.add(rs.getInt(3));
                nume.add(n)  ;

                idMedic.add(rs.getInt(2));
                System.out.print("Nume: "+n+", "+id+", "+idC);
                // System.out.print(nume.get(i)+"nume ");
                 i++;}
            }


    }

    static void adaugaCont(String email, String parola) throws SQLException, ClassNotFoundException {

        String s="insert into conturi(email,parola) values(?,?)";
        PreparedStatement pst=Conectare.conect().prepareStatement(s);
        pst.setString (1, email);
        pst.setString (2, parola);
        pst.execute();
    }

    static int ultimulCont() throws SQLException, ClassNotFoundException {
        int idCont=0;
        Statement st=conect.createStatement();

        String s="select max(id_cont) from conturi";
        ResultSet rs=st.executeQuery(s);
        while(rs.next())
            idCont=rs.getInt(1);

        return idCont;
    }

    static void adaugaPacient(String nume, String numarTelefon, String sex, Date dataNasterii, int idCont) throws SQLException, ClassNotFoundException {

        String s="insert into pacienti(numePacient, numarTelefon, sex, dataNasterii, id_cont) values(?,?,?,?,?)";
        PreparedStatement pst=Conectare.conect().prepareStatement(s);
        pst.setString (1, nume);
        pst.setString (2, numarTelefon);
        pst.setString (3, sex);
        pst.setObject (4,  dataNasterii);
        pst.setInt (5, idCont);
        pst.execute();
    }

    static void adaugaReview(int idComp, int nota, int idCont, String text) throws SQLException, ClassNotFoundException {

        String s = "Insert into review(idComp, nota, idCont, textReview) values('"+idComp+"', '"+nota+"', '"+idCont+"', '"+text+"')";

        Conectare.inserare(s);
    }

    static float medie(int idComp) throws SQLException, ClassNotFoundException {
        float med=0;

        Statement st=conect.createStatement();

        String s = "select avg(nota) from review where idComp='"+idComp+"'";
        ResultSet rs=st.executeQuery(s);

        while (rs.next())
        {
            med=rs.getFloat(1);
        }




        return med;
    }

    static void denumireSpecializare() throws SQLException, ClassNotFoundException {
            Statement st=conect.createStatement();
            specializare.clear();
            int j=1;
            for(int i=1;i<idMedic.size();i++) {
                String s = "SELECT s.denumireSpecializare, cm.idSpecializare, cm.idMedic from specializari s, competente_medici cm \n" +
                        "where s.idSpecializare=cm.idSpecializare and cm.idMedic='"+idMedic.get(i)+"'";
                ResultSet rs = st.executeQuery(s);
                while (rs.next()) {

                    specializare.add(rs.getString(1));
                    System.out.print("\n"+"Specializare: "+rs.getString(1));
                    j++;
                }
            }


    }

     void acordareNota(SeekBar sb, int idCont, int idComp, TextView n) throws SQLException {

         int nota = sb.getProgress();
         int notaExistenta = 0;


         Statement stmt = conect.createStatement();
         boolean b = false;


         String q = "Insert into review( idComp, idCont, nota ) values('" + idComp + "','" + idCont + "','" + nota + "')";
         stmt.executeUpdate(q);
         //  n.setText(sb.getProgress(}

     }



    boolean existaNota(int idCont, int idComp) throws SQLException {
        boolean b = false;




        if (conect == null) {
            ConnectionResult = "Check Your Internet Access!";
        } else {
            Statement stmt = conect.createStatement();

            String s = "SELECT  nota from review where idCont='" + idCont + "' and idComp='" + idComp+"'";
            ResultSet rs = stmt.executeQuery(s);
            while (rs.next()) {


                b = true;
            }

        }

        return b;

    }


    static int CompidProgram(int idProgram) throws SQLException, ClassNotFoundException {
        int idComp=0;
        Statement st=conect.createStatement();

        String s="Select idComp from orar where idProgram='"+idProgram+"'";
        ResultSet rs=st.executeQuery(s);

        while(rs.next()) {

            idComp = rs.getInt(1);

        }

        return idComp;
    }

    static String specializareComp(int idComp) throws SQLException, ClassNotFoundException {
        String specializare="";
        Statement st=conect.createStatement();

        String s="select denumireSpecializare from specializari s, competente_medici cm where s.idSpecializare=cm.idSpecializare and cm.idComp='"+idComp+"'";
        ResultSet rs=st.executeQuery(s);

        while(rs.next())
        {
            specializare=rs.getString(1);
        }

        return specializare;
    }


    static String medicComp(int idComp) throws SQLException, ClassNotFoundException {
        String numeMedic="";
        Statement st=conect.createStatement();

        String s="select numeMedic from medici m, competente_medici cm where m.idMedic=cm.idMedic and cm.idComp='"+idComp+"'";
        ResultSet rs=st.executeQuery(s);

        while(rs.next())
        {
            numeMedic=rs.getString(1);
        }

        return numeMedic;
    }


    static void toateSpecializarile() throws SQLException, ClassNotFoundException {



        Statement st=conect.createStatement();
toateSpecializarile.clear();
idSpecializare.clear();

                String s = "SELECT denumireSpecializare, idSpecializare from specializari";
                ResultSet rs = st.executeQuery(s);
                while (rs.next()) {

                    toateSpecializarile.add(rs.getString(1));
                    idSpecializare.add(rs.getInt(2));
                    System.out.print(rs.getString(1));

                }



    }

   static void mediciSpecializare(String denumireSpecializare) throws SQLException, ClassNotFoundException {

        int idSpecializare=0;

       Statement st=conect.createStatement();

            String s = "SELECT idSpecializare from specializari where denumireSpecializare='"+denumireSpecializare+"'";
            ResultSet rs = st.executeQuery(s);
            while (rs.next()) {


                idSpecializare=rs.getInt(1);
                mediciSpecializare.clear();

            }


            String q = "select m.numeMedic, cm.idMedic from medici m, competente_medici cm where cm.idSpecializare='"+idSpecializare+"' and m.idMedic=cm.idMedic";
       Statement stmt=conect.createStatement();
            rs = stmt.executeQuery(q);

            while (rs.next()) {

                mediciSpecializare.add(rs.getString(1));

            }

    }


   static void verificaDisponibilitate(Date data,  int ids) throws SQLException, ClassNotFoundException {

       idProgram.clear();
       mediciDisponibili.clear();
       boolean b=false;



       int idm=0,  idc=0, idp=0;
       String q;
       ResultSet rs ;



       Statement stmt2=conect.createStatement();
       Statement stmt3=conect.createStatement();

       q="select idComp from competente_medici where idSpecializare='"+ids+"'";

            ResultSet res;
            res = stmt2.executeQuery(q);
            while (res.next()) {

                idc=res.getInt(1);



            String s = "select idProgram, DataOraInceput, DataOraSfarsit from orar where idComp='"+idc+"'";
                ResultSet resultSet;
            resultSet=stmt3.executeQuery(s);


            while (resultSet.next()) {
                idp = resultSet.getInt(1);
                System.out.println(idp);

                String dataInceput = resultSet.getString(2);
                String dataSfarsit = resultSet.getString(3);

                               DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                try {
                    Date d1 = format.parse(dataInceput);

                    Date d2 = format.parse(dataSfarsit);

                    Date d3 = format2.parse(dataInceput);


                    if (data.equals(d3)) {


                        dataOraInceput.add(d1);
                        dataOraSfarsit.add(d2);
                        idProgram.add(idp);


                        mediciDisponibili.add(idc);




                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }


        }



    }


    static void oreDisponibile(int i) throws SQLException, ClassNotFoundException {
        Statement stmt=conect.createStatement();
            oreDisponibile.clear();
            oreIndisponibile.clear();
            String s = "select dataOra from programari where idProgram='" + idProgram.get(i) + "'";
            ResultSet rs =stmt.executeQuery(s);
            boolean b=false;
          while (rs.next())
          {
              oreIndisponibile.add(new Date(rs.getTimestamp(1).getTime()));

          }
           
          


            long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
            SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
            oreDisponibile.add(localDateFormat.format(dataOraInceput.get(i)));
            long t = dataOraInceput.get(i).getTime();
            long f = dataOraSfarsit.get(i).getTime();
            long dif = f - t;
            long diferenta = dif / ONE_MINUTE_IN_MILLIS / 30;
            Date urmatoareaData = new Date(t + (30 * ONE_MINUTE_IN_MILLIS));
            String urmatoareaOra = localDateFormat.format(urmatoareaData);
           // output.setText( dataOraInceput.get(i) + " " + dataOraSfarsit.get(i) + " "  +diferenta + " " + urmatoareaData + " " );
            oreDisponibile.add(urmatoareaOra);
            for(int k = 1; k < diferenta-1; k++) {
                urmatoareaData = new Date(urmatoareaData.getTime() + (30 * ONE_MINUTE_IN_MILLIS));


                urmatoareaOra = localDateFormat.format(urmatoareaData);

               // output.setText(output.getText() + " " + urmatoareaOra + " ");
                oreDisponibile.add(urmatoareaOra);
            }



            for (int j = 0; j < oreDisponibile.size(); j++) {
                //output.setText(output.getText() + " " + oreDisponibile.get(j));
                for (int k = 0; k < oreIndisponibile.size(); k++) {
                   //output.setText(output.getText() + " a " + localDateFormat.format(oreIndisponibile.get(k)) );
                    if (localDateFormat.format(oreIndisponibile.get(k)).equals(oreDisponibile.get(j))) {


                        oreDisponibile.remove(j);
                     //   output.setText(output.getText()+"sters"+oreDisponibile.get(j));
                    }

                }
            }
           // for (int j = 0; j < oreDisponibile.size(); j++)
             //   output.setText(output.getText() + " " + oreDisponibile.get(j));

       // }
    }



    static String medicCompetenta(int idComp) throws SQLException, ClassNotFoundException {
        String nume="";
        Statement st=conect.createStatement();
        ResultSet rs;
        String q="Select numeMedic from medici m, competente_medici cm where cm.idMedic=m.idMedic and idComp='"+idComp+"'";
        rs = st.executeQuery(q);
        while (rs.next())
        {
            nume=rs.getString(1);
        }
        return nume;
    }

    static int idCompetenta(String nume, String specializare) throws SQLException, ClassNotFoundException {
        int id=0;
        String s="select idComp from competente_medici where idMedic=(select idMedic from medici where upper(numeMedic)='"+nume+"') and idSpecializare=" +
                "(select idSpecializare from specializari where denumireSpecializare='"+specializare+"')";

        Statement stmt=conect.createStatement();
        ResultSet rs=stmt.executeQuery(s);

        while (rs.next())
        {
            id=rs.getInt(1);
        }

        return id;
    }



   static int idPacient(int idCont) throws SQLException, ClassNotFoundException {
        int id=0;

        String s="select idPacient from pacienti where id_cont='"+idCont+"'";

        Statement st=conect.createStatement();
        ResultSet rs=st.executeQuery(s);

        while(rs.next())
           id=rs.getInt(1);

        return id;

    }

   static String numePacient(int idCont) throws SQLException, ClassNotFoundException {
        String nume="";

        String s="Select numePacient from pacienti where id_cont='"+idCont+"'";
        Statement st=conect.createStatement();
        ResultSet rs=st.executeQuery(s);

        while(rs.next())
            nume=rs.getString(1);

        return nume;

    }

    static void recenziiMedic(int idComp) throws SQLException, ClassNotFoundException {
       recenziiMedic.clear();
       noteMedic.clear();
       idContRec.clear();
        String s="select textReview, nota, idCont from review where idComp='"+idComp+"'";
        Statement st= conect.createStatement();
        ResultSet rs=st.executeQuery(s);
        while(rs.next())
        {
            recenziiMedic.add(rs.getString(1));
            noteMedic.add(rs.getInt(2));
            idContRec.add(rs.getInt(3));
        }

    }

    static void efectuareProgramare(int idPacient, int idComp, LocalDateTime dataOra) throws SQLException, ClassNotFoundException {
        String s="insert into programari(idPacient, idProgram, dataOra) values('"+idPacient+"', '"+idComp+"', '"+dataOra+"')";
        Statement st=conect.createStatement();
        st.executeUpdate(s);
       // Conectare.inserare(s);

    }

    static boolean areProgramare(int idCont, int idComp) throws SQLException, ClassNotFoundException {
        boolean b=false;

        String s="select distinct idPacient from programari where idProgram in (select idProgram from orar where idComp='"+idComp+"') and dataOra <= CURDATE()";

        Statement st=conect.createStatement();
        ResultSet rs=st.executeQuery(s);


        while(rs.next())
            if(idPacient(idCont)==rs.getInt(1))
                b=true;

        return b;

    }

    static boolean emailUnic(String email) throws SQLException, ClassNotFoundException {
       boolean b=true;
        Statement st=conect.createStatement();
       String s="Select email from conturi where email='"+email+"'";
       ResultSet rs=st.executeQuery(s);
       while(rs.next())
           b=false;

       return b;
    }

    static boolean nrTelefonUnic(String nrTelefon) throws SQLException, ClassNotFoundException {
        boolean b=true;
        Statement st=conect.createStatement();

        String s="Select numarTelefon from pacienti where numarTelefon='"+nrTelefon+"'";
        ResultSet rs=st.executeQuery(s);
        while(rs.next())
            b=false;

        return b;
    }

    boolean existaProgramareLaOra(LocalDateTime dataOra, int idPacient) throws SQLException {
     boolean b=false;

     String s="select dataOra from programari where idPacient='"+idPacient+"' and dataOra='"+dataOra+"'";
     Statement st=conect.createStatement();
     ResultSet rs=st.executeQuery(s);
     while (rs.next())
         b=true;

     return b;
    }


}
