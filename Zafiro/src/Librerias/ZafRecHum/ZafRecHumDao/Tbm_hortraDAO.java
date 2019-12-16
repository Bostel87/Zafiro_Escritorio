/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumBo.Tbm_callabBO;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_hortra;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_callab;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Objeto de Acceso a Datos de la tabla Tbm_cabHorTra, Tbm_detHorTra
 * Usada por el BO
 * @author Roberto Flores
 * Guayaquil 08/09/2011
 */
public class Tbm_hortraDAO implements Tbm_hortraDAOInterface {

    /**
     * Retorna el máximo id de la tabla tbm_cabhortra.
     * @param connection
     * @return Retorna 0 si no hay registros y el máximo id si los hay.
     * @throws Exception
     */
    public int obtenerMaxId(Connection connection) throws SQLException {
        String strSql = null;
        Statement sta = null;
        ResultSet resSet = null;
        int intMaxId = 0;

        try{
            strSql = "select max(co_hor) as maxId from tbm_cabHorTra";
            sta = connection.createStatement();
            resSet = sta.executeQuery(strSql);

            if(resSet.next()){
                intMaxId = resSet.getInt("maxId");
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{sta.close();}catch(Throwable ignore){}
        }

        return intMaxId;
    }

    /**
     * Graba en las tablas tbm_cabhortra & tbm_dethortra.
     * @param connection
     * @param tbm_hortra Objeto POJO que contiene los datos de las tablas tbm_cabhortra & tbm_dethortra.
     * @throws SQLException
     */
    public void grabar(Connection connection, List<Tbm_hortra> lisTbm_hortra , Tbm_hortra tbm_horTra, Date datFecHoy, int intMaxAño, Tbm_callabBO tbm_callabBO ) throws Exception {
        String strSql = null;
        PreparedStatement preSta = null;
        java.sql.Statement stmLoc = null;

         try{
             strSql = "insert into tbm_cabhortra(co_hor, tx_nom, tx_obs1, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod) ";
             strSql += "values(?,?,?,?,?,?,?,?)";

             preSta = connection.prepareStatement(strSql);

             if(tbm_horTra.getIntCo_hor()>0){
                preSta.setInt(1, tbm_horTra.getIntCo_hor());
             }else{
                 preSta.setNull(1, Types.INTEGER);
             }
             if(tbm_horTra.getStrTx_nom()!=null){
                preSta.setString(2, tbm_horTra.getStrTx_nom());
             }else{
                 preSta.setNull(2, Types.VARCHAR);
             }
             if(tbm_horTra.getStrTxa_obs1()!=null){
                preSta.setString(3, tbm_horTra.getStrTxa_obs1());
             }else{
                 preSta.setNull(3, Types.VARCHAR);
             }
             if(tbm_horTra.getStrSt_reg()!=null){
                preSta.setString(4, tbm_horTra.getStrSt_reg());
             }else{
                 preSta.setNull(4, Types.VARCHAR);
             }
             if(tbm_horTra.getDatFe_ing()!=null){
                preSta.setTimestamp(5, new Timestamp(tbm_horTra.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(5, Types.TIMESTAMP);
            }
            if(tbm_horTra.getDatFe_ultMod()!=null){
                preSta.setTimestamp(6, new Timestamp(tbm_horTra.getDatFe_ultMod().getTime()));
            }else{
                preSta.setNull(6, Types.TIMESTAMP);
            }
            if(tbm_horTra.getIntCo_usrIng()>0){
                preSta.setInt(7, tbm_horTra.getIntCo_usrIng());
            }else{
                preSta.setNull(7, Types.INTEGER);
            }
            if(tbm_horTra.getIntCo_usrMod()>0){
                preSta.setInt(8, tbm_horTra.getIntCo_usrMod());
            }else{
                preSta.setNull(8, Types.INTEGER);
            }

            preSta.executeUpdate();

            String[][] strTbl_entsaldet = tbm_horTra.getStrTbl_entsaldet();
            int intDiaSum = 1;

            stmLoc = connection.createStatement();
            
            for(int i=0; i <= strTbl_entsaldet.length-1; i++){

                
                
                String strHoEnt = strTbl_entsaldet[i][0];
                if(strHoEnt==null || strHoEnt.compareTo("")==0){
                    strHoEnt="null";
                }else{
                    strHoEnt="'"+strHoEnt+"'";
                }
                        
                String strHoSal = strTbl_entsaldet[i][1];
                if(strHoSal==null || strHoSal.compareTo("")==0){
                    strHoSal="null";
                }else{
                    strHoSal="'"+strHoSal+"'";
                }
                        
                String strMinGra = strTbl_entsaldet[i][2];
                if(strMinGra==null || strMinGra.compareTo("")==0){
                    strMinGra="null";
                }else{
                    strMinGra="'"+strMinGra+"'";
                }
                
                strSql = "";
                strSql = "insert into tbm_dethortra(co_hor, ne_dia, ho_ent, ho_sal, ho_mingraent) ";
                strSql += "values("+tbm_horTra.getIntCo_hor()+","+ intDiaSum++ +","+strHoEnt+","+
                                strHoSal+","+strMinGra+")";
                

                stmLoc.executeUpdate(strSql);

                /*preSta = connection.prepareStatement(strSql);

                if(tbm_horTra.getIntCo_hor()>0){
                    preSta.setInt(1, tbm_horTra.getIntCo_hor());
                }else{
                 preSta.setNull(1, Types.INTEGER);
                }

                preSta.setInt(2, intDiaSum++);

                DateFormat sdf = new SimpleDateFormat("hh:mm");
                Date date = null;

                if(strTbl_entsaldet[i][0].compareTo("null")==0 || strTbl_entsaldet[i][0].compareTo("")==0){
                    preSta.setTime(3, null);
                }else{
                    date = sdf.parse(strTbl_entsaldet[i][0]);
                    preSta.setTime(3, new Time(date.getTime()));
                    
                }

                if(strTbl_entsaldet[i][1].compareTo("null")==0 || strTbl_entsaldet[i][1].compareTo("")==0){
                    preSta.setTime(4, null);
                }else{
                    date = sdf.parse(strTbl_entsaldet[i][1]);
                    preSta.setTime(4, new Time(date.getTime()));
                }
                
                if(strTbl_entsaldet[i][2].compareTo("null")==0 || strTbl_entsaldet[i][2].compareTo("")==0){
                    preSta.setTime(5, null);
                }else{
                    date = sdf.parse(strTbl_entsaldet[i][2]);
                    preSta.setTime(5, new Time(date.getTime()));
                }
                
                preSta.executeUpdate();*/
                
            }


            if(lisTbm_hortra==null){
                lisTbm_hortra = new ArrayList<Tbm_hortra>();
            }
            lisTbm_hortra.add(tbm_horTra);
            //procesarCalendarioLaboral(connection, datFecHoy, tbm_horTra, intMaxAño, tbm_callabBO);
            procesarCalendarioLaboral(connection, lisTbm_hortra, datFecHoy, intMaxAño, tbm_horTra, 0);

         }catch(Exception e){
             e.printStackTrace();
             throw new Exception(e.getMessage());
         }
         finally {
            try{preSta.close();}catch(Throwable ignore){}
            try{stmLoc.close();}catch(Throwable ignore){}
         }
    }

    
    //opcion 0.... insercion
    //opcion 1 act...
    private void procesarCalendarioLaboral(Connection con, List<Tbm_hortra> lisTbm_hortra , Date datFecHoy , int intMaxAño, Tbm_hortra tbm_HorTra, int opcion) throws Exception {

        boolean res = false;
        String strSql = "";
        PreparedStatement preSta = null;
        ArrayList<Tbm_callab> arrTbm_callab = null;

        String formato="yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        int intAño = Integer.parseInt(dateFormat.format(datFecHoy));

        try {

            //eliminarCalendarioLaboral(con, tbm_callabBO);

            if(opcion==0){
                /*strSql = "delete from tbm_callab where fe_dia >= ?";
                preSta = con.prepareStatement(strSql);
                preSta.setDate(1, new java.sql.Date(datFecHoy.getTime()));
                preSta.executeUpdate();*/

                for(Iterator<Tbm_hortra> it  = lisTbm_hortra.iterator(); it.hasNext();){

                    Tbm_hortra tbmHorTra = it.next();
                    if(!tbmHorTra.equals(tbm_HorTra)){
                        it.remove();
                        /*if(intAño>intMaxAño){
                            intMaxAño = 0;
                        }else{
                            intMaxAño = intAño;
                        }*/
                        if(intMaxAño>intAño){

                        }else{
                            intMaxAño = 0;
                        }
                        
                    }

                }


            }


            arrTbm_callab = new ArrayList<Tbm_callab>();
            Calendar cal = null;
            int intDiasValidosIniciales;
            int intMesesValidosIniciales;
            //List<Tbm_hortra> lisTbm_hortra = null;
            int intCodHor;

            
            int intRecAño = 0;

            if(intMaxAño>0){

                for(int intRecLisTbm = 0; intRecLisTbm < lisTbm_hortra.size(); intRecLisTbm++){

                    Tbm_hortra tbm_hortra = lisTbm_hortra.get(intRecLisTbm);
                    datFecHoy = tbm_hortra.getDatFe_ing();

                    for(intRecAño = intAño; intRecAño <= intMaxAño; intRecAño++){

                        formato="MM";
                        dateFormat = new SimpleDateFormat(formato);
                        intMesesValidosIniciales = Integer.parseInt(dateFormat.format(datFecHoy));

                        formato="dd";
                        dateFormat = new SimpleDateFormat(formato);
                        intDiasValidosIniciales = Integer.parseInt(dateFormat.format(datFecHoy));//Tbm_hortraBO.consultaMesesValidosIniciales();
                        intCodHor = tbm_hortra.getIntCo_hor(); //Tbm_hortraBO.consultaCodHor();
                        //lisTbm_hortra = Tbm_hortraBO.consultarCalLabAct();

                        int[] diaSemana = {1, 2, 3, 4, 5, 6, 7};
                        int[] intMeses = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
                        int total = 0;
                        //int intC = 0;
                        int tamStrTbl = tbm_hortra.getStrTbl_entsaldet().length;

                        //for(int intRecCodHor = 0; intRecCodHor < intCodHor.length; intRecCodHor++){

                            //armo los horarios
                            String[][] horarios = tbm_hortra.getStrTbl_entsaldet(); //new String[7][3];
                            for(int i = 0; i < tamStrTbl; i++){
                                    //if(intCodHor==lisTbm_hortra.get(i).getIntCo_hor()){
                                        horarios[i][0] = tbm_hortra.getStrTbl_entsaldet()[i][0];
                                        horarios[i][1] = tbm_hortra.getStrTbl_entsaldet()[i][1];
                                        horarios[i][2] = tbm_hortra.getStrTbl_entsaldet()[i][2];
                                        //intC++;
                                    //}
                            }
                            //intC = 0;

                            int intMesMin = intMesesValidosIniciales-1;//[intRecCodHor]-1;
                            int intMesMax = intMeses[intMeses.length-1];
                            int intDiaMin = intDiasValidosIniciales;//[intRecCodHor];//cal.getActualMinimum(cal.DATE);


                            for(int intMes = intMesMin; intMes <= intMesMax; intMes++){
                                cal = Calendar.getInstance();
                                //set(int year, int month, int date)
                                int intDiaMax = cal.getActualMaximum(cal.DATE);
                                cal.set(intRecAño, intMes, intDiaMin);


                                    for(int intDia = intDiaMin; intDia <= intDiaMax; intDia++){

                                        Tbm_callab tmp = new Tbm_callab();
                                        cal.set(intRecAño, intMes, intDia);
                                        intDiaMax = cal.getActualMaximum(cal.DATE);
                                        datFecHoy = cal.getTime();
                                        System.out.println(datFecHoy.toString());
                                        int day = datFecHoy.getDay();
                                        if(day==0){day=6;}else{
                                            day--;
                                        }
                                        tmp.setDteFe_Dia(datFecHoy);
                                        tmp.setIntCo_Hor(intCodHor);
                                        //if(horarios[day][0]==null || horarios[day][0].compareTo("")==0){
                                        //if(horarios[day][0]==null){
                                            //tmp.setStrSt_DiaLab("N");
                                        //}else{
                                        tmp.setStrSt_DiaLab("S");
                                        //}
                                        tmp.setStrHo_Ent(horarios[day][0]);
                                        tmp.setStrHo_Sal(horarios[day][1]);
                                        tmp.setStrMin_GraEnt(horarios[day][2]);
                                        arrTbm_callab.add(tmp);
                                        //System.out.println(day);
                                        total++;
                                    }
                                    intDiaMin = 1;
                                    System.out.println(total);
                                    total = 0;
                            }
                            cal.set(intRecAño, intMeses[0], 1);
                            datFecHoy = cal.getTime();

                    }
                }
            }else if(intMaxAño==0){

                //for(intRecAño = intAño; intRecAño <= intMaxAño; intRecAño++){


                intRecAño = Integer.parseInt(dateFormat.format(datFecHoy));

                for(int intRecLisTbm = 0; intRecLisTbm < lisTbm_hortra.size(); intRecLisTbm++){

                    Tbm_hortra tbm_hortra = lisTbm_hortra.get(intRecLisTbm);
                    datFecHoy = tbm_hortra.getDatFe_ing();


                    formato="MM";
                    dateFormat = new SimpleDateFormat(formato);
                    intMesesValidosIniciales = Integer.parseInt(dateFormat.format(datFecHoy));

                    formato="dd";
                    dateFormat = new SimpleDateFormat(formato);
                    intDiasValidosIniciales = Integer.parseInt(dateFormat.format(datFecHoy));//Tbm_hortraBO.consultaMesesValidosIniciales();
                    intCodHor = tbm_hortra.getIntCo_hor(); //Tbm_hortraBO.consultaCodHor();
                    //lisTbm_hortra = Tbm_hortraBO.consultarCalLabAct();

                    int[] diaSemana = {1, 2, 3, 4, 5, 6, 7};
                    int[] intMeses = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
                    int total = 0;
                    //int intC = 0;
                    int tamStrTbl = tbm_hortra.getStrTbl_entsaldet().length;

                        //for(int intRecCodHor = 0; intRecCodHor < intCodHor.length; intRecCodHor++){

                            //armo los horarios
                            String[][] horarios = tbm_hortra.getStrTbl_entsaldet(); //new String[7][3];
                            for(int i = 0; i < tamStrTbl; i++){
                                    //if(intCodHor==lisTbm_hortra.get(i).getIntCo_hor()){
                                        horarios[i][0] = tbm_hortra.getStrTbl_entsaldet()[i][0];
                                        horarios[i][1] = tbm_hortra.getStrTbl_entsaldet()[i][1];
                                        horarios[i][2] = tbm_hortra.getStrTbl_entsaldet()[i][2];
                                        //intC++;
                                    //}
                            }
                            //intC = 0;

                        int intMesMin = intMesesValidosIniciales-1;//[intRecCodHor]-1;
                        int intMesMax = intMeses[intMeses.length-1];
                        int intDiaMin = intDiasValidosIniciales;//[intRecCodHor];//cal.getActualMinimum(cal.DATE);


                        for(int intMes = intMesMin; intMes <= intMesMax; intMes++){
                            cal = Calendar.getInstance();
                            //set(int year, int month, int date)
                            int intDiaMax = cal.getActualMaximum(cal.DATE);
                            cal.set(intRecAño, intMes, intDiaMin);


                                for(int intDia = intDiaMin; intDia <= intDiaMax; intDia++){

                                    Tbm_callab tmp = new Tbm_callab();
                                    cal.set(intRecAño, intMes, intDia);
                                    intDiaMax = cal.getActualMaximum(cal.DATE);
                                    datFecHoy = cal.getTime();
                                    System.out.println(datFecHoy.toString());
                                    int day = datFecHoy.getDay();
                                    if(day==0){day=6;}else{
                                        day--;
                                    }
                                    tmp.setDteFe_Dia(datFecHoy);
                                    tmp.setIntCo_Hor(intCodHor);
                                    //if(horarios[day][0]==null || horarios[day][0].compareTo("")==0){
                                    //if(horarios[day][0]==null){
                                        //tmp.setStrSt_DiaLab("N");
                                    //}else{
                                    tmp.setStrSt_DiaLab("S");
                                    //}
                                    tmp.setStrHo_Ent(horarios[day][0]);
                                    tmp.setStrHo_Sal(horarios[day][1]);
                                    tmp.setStrMin_GraEnt(horarios[day][2]);
                                    arrTbm_callab.add(tmp);
                                    //System.out.println(day);
                                    total++;
                                }
                                intDiaMin = 1;
                                System.out.println(total);
                                total = 0;
                        }
                        cal.set(intRecAño, intMeses[0], 1);
                        datFecHoy = cal.getTime();

                }
            }


        DateFormat sdf = new SimpleDateFormat("hh:mm");
        Date date = null;

        for(Iterator<Tbm_callab> it = arrTbm_callab.iterator(); it.hasNext();){


            Tbm_callab tbm_callab = it.next();

            try{
                strSql = "insert into tbm_callab(fe_dia, co_hor, st_dialab, ho_ent, ho_sal, ho_mingraent, tx_obs1) ";
                        strSql += "values(?,?,?,?,?,?,?)";

                        preSta = con.prepareStatement(strSql);

                        if(tbm_callab.getDteFe_Dia()!=null){
                            date = tbm_callab.getDteFe_Dia();
                            preSta.setDate(1, new java.sql.Date(date.getTime()));
                        }else{
                            preSta.setNull(1, Types.DATE);
                        }
                        if(tbm_callab.getIntCo_Hor()>0){
                            preSta.setInt(2, tbm_callab.getIntCo_Hor());
                        }else{
                            preSta.setNull(2, Types.INTEGER);
                        }
                        if(tbm_callab.getStrSt_DiaLab()!=null){
                            preSta.setString(3, tbm_callab.getStrSt_DiaLab());
                        }else{
                            preSta.setNull(3, Types.VARCHAR);
                        }
                        if(tbm_callab.getStrHo_Ent()==null || tbm_callab.getStrHo_Ent().compareTo("")==0){
                            preSta.setTime(4, null);
                        }else{
                            date = sdf.parse(tbm_callab.getStrHo_Ent());
                            preSta.setTime(4, new Time(date.getTime()));
                        }
                        if(tbm_callab.getStrHo_Sal()==null || tbm_callab.getStrHo_Sal().compareTo("")==0){
                            preSta.setTime(5, null);
                        }else{
                            date = sdf.parse(tbm_callab.getStrHo_Sal());
                            preSta.setTime(5, new Time(date.getTime()));
                        }
                        if(tbm_callab.getStrMin_GraEnt()==null || tbm_callab.getStrMin_GraEnt().compareTo("")==0){
                            preSta.setTime(6, null);
                        }else{
                            date = sdf.parse(tbm_callab.getStrMin_GraEnt());
                            preSta.setTime(6, new Time(date.getTime()));
                        }
                        if(tbm_callab.getStrTx_Obs1()!=null){
                            preSta.setString(7, tbm_callab.getStrTx_Obs1());
                        }else{
                            preSta.setNull(7, Types.VARCHAR);
                        }

                        preSta.executeUpdate();

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    finally {
                        try{preSta.close();}catch(Throwable ignore){}
                    }
                }
                res = true;

        }catch (Exception ex) {
            res = false;
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }



        

    }

    /*private void procesarCalendarioLaboral(Connection con, Date datFecHoy, Tbm_hortra tbm_hortra, int intMaxAño, Tbm_callabBO tbm_callabBO) throws Exception {
       
        boolean res = false;
        
        try {


            ArrayList<Tbm_callab> arrTbm_callab = new ArrayList<Tbm_callab>();
            Calendar cal = null;
            int intDiasValidosIniciales;
            int intMesesValidosIniciales;
            //List<Tbm_hortra> lisTbm_hortra = null;
            int intCodHor;
            

            String formato="yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            int intAño = Integer.parseInt(dateFormat.format(datFecHoy));
            int intRecAño = 0;


            if(intMaxAño>0){
                for(intRecAño = intAño; intRecAño <= intMaxAño; intRecAño++){

                    formato="MM";
                    dateFormat = new SimpleDateFormat(formato);
                    intMesesValidosIniciales = Integer.parseInt(dateFormat.format(datFecHoy));

                    formato="dd";
                    dateFormat = new SimpleDateFormat(formato);
                    intDiasValidosIniciales = Integer.parseInt(dateFormat.format(datFecHoy));//Tbm_hortraBO.consultaMesesValidosIniciales();
                    intCodHor = tbm_hortra.getIntCo_hor(); //Tbm_hortraBO.consultaCodHor();
                    //lisTbm_hortra = Tbm_hortraBO.consultarCalLabAct();

                    int[] diaSemana = {1, 2, 3, 4, 5, 6, 7};
                    int[] intMeses = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
                    int total = 0;
                    int intC = 0;

                    //for(int intRecCodHor = 0; intRecCodHor < intCodHor.length; intRecCodHor++){

                        //armo los horarios
                        String[][] horarios = tbm_hortra.getStrTbl_entsaldet(); //new String[7][3];
                        //for(int i = 0; i < lisTbm_hortra.size(); i++){
                                //if(intCodHor==lisTbm_hortra.get(i).getIntCo_hor()){
                                    horarios[intC][0] = tbm_hortra.getStrTbl_entsaldet()[0][0];
                                    horarios[intC][1] = tbm_hortra.getStrTbl_entsaldet()[0][1];
                                    horarios[intC][2] = tbm_hortra.getStrTbl_entsaldet()[0][2];
                                    intC++;
                                //}
                        //}
                        intC = 0;

                        int intMesMin = intMesesValidosIniciales-1;//[intRecCodHor]-1;
                        int intMesMax = intMeses[intMeses.length-1];
                        int intDiaMin = intDiasValidosIniciales;//[intRecCodHor];//cal.getActualMinimum(cal.DATE);


                        for(int intMes = intMesMin; intMes <= intMesMax; intMes++){
                            cal = Calendar.getInstance();
                            //set(int year, int month, int date)
                            int intDiaMax = cal.getActualMaximum(cal.DATE);
                            cal.set(intRecAño, intMes, intDiaMin);


                                for(int intDia = intDiaMin; intDia <= intDiaMax; intDia++){

                                    Tbm_callab tmp = new Tbm_callab();
                                    cal.set(intRecAño, intMes, intDia);
                                    intDiaMax = cal.getActualMaximum(cal.DATE);
                                    datFecHoy = cal.getTime();
                                    System.out.println(datFecHoy.toString());
                                    int day = datFecHoy.getDay();
                                    if(day==0){day=6;}else{
                                        day--;
                                    }
                                    tmp.setDteFe_Dia(datFecHoy);
                                    tmp.setIntCo_Hor(intCodHor);
                                    //if(horarios[day][0]==null || horarios[day][0].compareTo("")==0){
                                    if(horarios[day][0]==null){
                                        tmp.setStrSt_DiaLab("N");
                                    }else{
                                        tmp.setStrSt_DiaLab("S");
                                    }
                                    tmp.setStrHo_Ent(horarios[day][0]);
                                    tmp.setStrHo_Sal(horarios[day][1]);
                                    tmp.setStrMin_GraEnt(horarios[day][2]);
                                    arrTbm_callab.add(tmp);
                                    //System.out.println(day);
                                    total++;
                                }
                                intDiaMin = 1;
                                System.out.println(total);
                                total = 0;
                        }
                        cal.set(intRecAño, intMeses[0], 1);
                        datFecHoy = cal.getTime();
                        
                }
            }else if(intMaxAño==0){

                //for(intRecAño = intAño; intRecAño <= intMaxAño; intRecAño++){

                    intRecAño = Integer.parseInt(dateFormat.format(datFecHoy));

                    formato="MM";
                    dateFormat = new SimpleDateFormat(formato);
                    intMesesValidosIniciales = Integer.parseInt(dateFormat.format(datFecHoy));

                    formato="dd";
                    dateFormat = new SimpleDateFormat(formato);
                    intDiasValidosIniciales = Integer.parseInt(dateFormat.format(datFecHoy));//Tbm_hortraBO.consultaMesesValidosIniciales();
                    intCodHor = tbm_hortra.getIntCo_hor(); //Tbm_hortraBO.consultaCodHor();
                    //lisTbm_hortra = Tbm_hortraBO.consultarCalLabAct();

                    int[] diaSemana = {1, 2, 3, 4, 5, 6, 7};
                    int[] intMeses = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
                    int total = 0;
                    int intC = 0;

                    //for(int intRecCodHor = 0; intRecCodHor < intCodHor.length; intRecCodHor++){

                        //armo los horarios
                        String[][] horarios = tbm_hortra.getStrTbl_entsaldet(); //new String[7][3];
                        //for(int i = 0; i < lisTbm_hortra.size(); i++){
                                //if(intCodHor==lisTbm_hortra.get(i).getIntCo_hor()){
                                    horarios[intC][0] = tbm_hortra.getStrTbl_entsaldet()[0][0];
                                    horarios[intC][1] = tbm_hortra.getStrTbl_entsaldet()[0][1];
                                    horarios[intC][2] = tbm_hortra.getStrTbl_entsaldet()[0][2];
                                    intC++;
                                //}
                        //}
                        intC = 0;

                        int intMesMin = intMesesValidosIniciales-1;//[intRecCodHor]-1;
                        int intMesMax = intMeses[intMeses.length-1];
                        int intDiaMin = intDiasValidosIniciales;//[intRecCodHor];//cal.getActualMinimum(cal.DATE);


                        for(int intMes = intMesMin; intMes <= intMesMax; intMes++){
                            cal = Calendar.getInstance();
                            //set(int year, int month, int date)
                            int intDiaMax = cal.getActualMaximum(cal.DATE);
                            cal.set(intRecAño, intMes, intDiaMin);


                                for(int intDia = intDiaMin; intDia <= intDiaMax; intDia++){

                                    Tbm_callab tmp = new Tbm_callab();
                                    cal.set(intRecAño, intMes, intDia);
                                    intDiaMax = cal.getActualMaximum(cal.DATE);
                                    datFecHoy = cal.getTime();
                                    System.out.println(datFecHoy.toString());
                                    int day = datFecHoy.getDay();
                                    if(day==0){day=6;}else{
                                        day--;
                                    }
                                    tmp.setDteFe_Dia(datFecHoy);
                                    tmp.setIntCo_Hor(intCodHor);
                                    //if(horarios[day][0]==null || horarios[day][0].compareTo("")==0){
                                    if(horarios[day][0]==null){
                                        tmp.setStrSt_DiaLab("N");
                                    }else{
                                        tmp.setStrSt_DiaLab("S");
                                    }
                                    tmp.setStrHo_Ent(horarios[day][0]);
                                    tmp.setStrHo_Sal(horarios[day][1]);
                                    tmp.setStrMin_GraEnt(horarios[day][2]);
                                    arrTbm_callab.add(tmp);
                                    //System.out.println(day);
                                    total++;
                                }
                                intDiaMin = 1;
                                System.out.println(total);
                                total = 0;
                        }
                        //cal.set(intRecAño, intMeses[0], 1);
                        //datFecHoy = cal.getTime();

                //}
            }
            //guardo lo almacenado en el array
            tbm_callabBO.grabarHorTra(con, arrTbm_callab);
            //setearFecha();
            res = true;

       }catch (Exception ex) {
           res = false;
           //zafUti.mostrarMsgErr_F1(zafRecHum06.this, null);
           ex.printStackTrace();
           throw new Exception(ex.getMessage());
       }
    }*/

    /**
     * Actualiza datos de las tablas tbm_cahortra & tbm_dethortra.
     * @param connection
     * @param tbm_hortra Objeto con los parámetros a actualizar
     * @param opcion entero con el parametro indicando: 1 modificacion, 2 si es eliminacion y anulacion
     * @throws SQLException
     */
    public void actualizar(Connection connection, Tbm_hortra tbm_horTra, int opcion, Tbm_callabBO tbm_callabBO) throws Exception {
        String strSql = null;
        PreparedStatement preSta = null;
        java.sql.Statement stmLoc = null;
        //PreparedStatement preStaActCalLab = null;

        try{

            strSql = "update tbm_cabHorTra ";
            strSql += "set tx_nom = ?, ";
            strSql += "tx_obs1 = ?, ";
            strSql += "st_reg = ?, ";
            strSql += "fe_ing = ?, ";
            strSql += "fe_ultmod = ?, ";
            strSql += "co_usring = ?, ";
            strSql += "co_usrmod = ? ";
            strSql += "where co_hor = ?";

            preSta = connection.prepareStatement(strSql);

            if(tbm_horTra.getStrTx_nom()!=null){
                preSta.setString(1, tbm_horTra.getStrTx_nom());
            }else{
                preSta.setNull(1, Types.VARCHAR);
            }
            if(tbm_horTra.getStrTxa_obs1()!=null){
                preSta.setString(2, tbm_horTra.getStrTxa_obs1());
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            if(tbm_horTra.getStrSt_reg()!=null){
                preSta.setString(3, tbm_horTra.getStrSt_reg());
            }else{
                preSta.setNull(3, Types.VARCHAR);
            }
            if(tbm_horTra.getDatFe_ing()!=null){
                preSta.setTimestamp(4, new Timestamp(tbm_horTra.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(4, Types.TIMESTAMP);
            }
            if(tbm_horTra.getDatFe_ultMod()!=null){
                preSta.setTimestamp(5, new Timestamp(tbm_horTra.getDatFe_ultMod().getTime()));
            }else{
                preSta.setNull(5, Types.TIMESTAMP);
            }
            if(tbm_horTra.getIntCo_usrIng()>0){
                preSta.setInt(6, tbm_horTra.getIntCo_usrIng());
            }else{
                preSta.setNull(6, Types.INTEGER);
            }
            if(tbm_horTra.getIntCo_usrMod()>0){
                preSta.setInt(7, tbm_horTra.getIntCo_usrMod());
            }else{
                preSta.setNull(7, Types.INTEGER);
            }

            preSta.setInt(8, tbm_horTra.getIntCo_hor());

            preSta.executeUpdate();

            
            stmLoc = connection.createStatement();
            
            switch(opcion){
                
                case 1:

                    String[][] strTbl_entsaldet = tbm_horTra.getStrTbl_entsaldet();
                    int intDiaSum = 1;
                    
                    for(int i=0; i <= strTbl_entsaldet.length-1; i++){//

                        
                        String strHoEnt = strTbl_entsaldet[i][0];
                        if(strHoEnt==null || strHoEnt.compareTo("")==0){
                            strHoEnt="null";
                        }else{
                            strHoEnt="'"+strHoEnt+"'";
                        }
                        
                        String strHoSal = strTbl_entsaldet[i][1];
                        if(strHoSal==null || strHoSal.compareTo("")==0){
                            strHoSal="null";
                        }else{
                            strHoSal="'"+strHoSal+"'";
                        }
                        
                        String strMinGra = strTbl_entsaldet[i][2];
                        if(strMinGra==null || strMinGra.compareTo("")==0){
                            strMinGra="null";
                        }else{
                            strMinGra="'"+strMinGra+"'";
                        }
                        
                        
                        strSql = "update tbm_detHorTra ";
                        strSql += "set ho_ent = " + strHoEnt + ", ";
                        strSql += "ho_sal = " + strHoSal + ", ";
                        strSql += "ho_mingraent = " + strMinGra + " ";
                        strSql += "where co_hor = "+ tbm_horTra.getIntCo_hor() + " ";
                        strSql += "and ne_dia = " + intDiaSum;

                        /*preSta = connection.prepareStatement(strSql);

                        DateFormat sdf = new SimpleDateFormat("hh:mm");
                        Date date = null;

                        if(strTbl_entsaldet[i][0].compareTo("null")==0 || strTbl_entsaldet[i][0].compareTo("")==0){
                            preSta.setTime(1, null);
                        }else{
                            date = sdf.parse(strTbl_entsaldet[i][0]);
                            preSta.setTime(1, new Time(date.getTime()));
                        }

                        if(strTbl_entsaldet[i][1].compareTo("null")==0 || strTbl_entsaldet[i][1].compareTo("")==0){
                            preSta.setTime(2, null);
                        }else{
                            date = sdf.parse(strTbl_entsaldet[i][1]);
                            preSta.setTime(2, new Time(date.getTime()));
                        }

                        if(strTbl_entsaldet[i][2].compareTo("null")==0 || strTbl_entsaldet[i][2].compareTo(" ")==0 || strTbl_entsaldet[i][2].compareTo("")==0){
                            preSta.setTime(3, null);
                        }else{
                            date = sdf.parse(strTbl_entsaldet[i][2]);
                            preSta.setTime(3, new Time(date.getTime()));
                        }
                        
                        

                        preSta.setInt(4, tbm_horTra.getIntCo_hor());
                        preSta.setInt(5, intDiaSum);

                        preSta.executeUpdate();*/
                        intDiaSum++;

                        /*strSql =  "update tbm_callab ";
                        strSql += "set ho_ent = ?, ";
                        strSql += "ho_sal = ?, ";
                        strSql += "ho_mingraent = ? ";
                        strSql += "where co_hor = ?";
                        strSql += "and fe_dia > ?";*/
                        
                        stmLoc.executeUpdate(strSql);


                    }
                    break;

                case 2:
                    break;
            }

            if(tbm_horTra.getStrSt_reg().equals("E") || tbm_horTra.getStrSt_reg().equals("I")){
                //setDatFe_ultMod
                eliminarCalendarioLaboral(connection, tbm_horTra.getIntCo_hor(), tbm_horTra.getDatFe_ultMod(), tbm_callabBO);
            }else if(tbm_horTra.getStrSt_reg().equals("A")){
                strSql = "delete from tbm_callab where co_hor = ? and fe_dia >= ? ";
                preSta = connection.prepareStatement(strSql);
                preSta.setInt(1, tbm_horTra.getIntCo_hor());
                preSta.setDate(2, new java.sql.Date( tbm_horTra.getDatFe_ultMod().getTime()));
                preSta.executeUpdate();

                int intMaxAño = tbm_callabBO.obtenerMaxAño();

                List<Tbm_hortra> lisTbm_hortra = new ArrayList<Tbm_hortra>();
                lisTbm_hortra.add(tbm_horTra);
                procesarCalendarioLaboral(connection, lisTbm_hortra, tbm_horTra.getDatFe_ultMod(), intMaxAño, tbm_horTra, 1);
            }

        }catch(Exception e){
             //e.printStackTrace();
             throw new Exception(e.getMessage());
        }
        finally {
            try{preSta.close();}catch(Throwable ignore){}
            try{stmLoc.close();}catch(Throwable ignore){}
        }
    }

    private void eliminarCalendarioLaboral(Connection con, int intCoHor, Date datFe_ultMod, Tbm_callabBO tbm_callabBO) throws Exception {
        tbm_callabBO.eliminar(con, intCoHor, datFe_ultMod);
    }

    /*private void eliminarCalendarioLaboral(Connection con, Tbm_callabBO tbm_callabBO) throws Exception {
        tbm_callabBO.eliminar(con);
    }*/

    /** modificar esta informacion
     * Consulta general de las tablas tbm_cabhortra & tbm_dethortra aplicando pagineo
     * @param connection
     * @param tbm_hortra Objeto con parámetros de consulta
     * @param intRegIni Número inicial de registro del conjunto de registros
     * @param intCanReg Cantidad de registros a consultar a partir del número inicial de registro
     * @return Retorna lista de tipo Tbm_hortra con los registros consultados
     * @throws SQLException
     */
    public List<Tbm_hortra> consultar(Connection connection) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_hortra> lisTbm_hortra = null;

        try{

            //**falta realizar inner join
            strSql = "select * from tbm_cabhortra where st_reg like 'A' order by co_hor asc";
            //strSql += "where (? is null or co_hor = ?) ";
            //strSql += "and (? is null or lower(tx_nom) like lower(?)) ";
            //strSql += "and (? is null or lower(tx_obs1) like lower(?)) ";
            //strSql += "and (? is null or lower(st_reg) = lower(?)) ";
            //strSql += "and (? is null or fe_ing = ?) ";
            //strSql += "and (? is null or fe_ultmod = ?) ";
            //strSql += "and (? is null or co_usring = ?) ";
            //strSql += "and (? is null or co_usrmod = ?) ";
            //strSql += "order by co_hor ";
            //strSql += "limit ? offset ?";

            preSta = connection.prepareStatement(strSql);
            resSet = preSta.executeQuery();

            Tbm_hortra tmp = null;
            if(resSet.next()){
                lisTbm_hortra = new ArrayList<Tbm_hortra>();
                do{
                    tmp = new Tbm_hortra();
                    tmp.setIntCo_hor(resSet.getInt("co_hor"));
                    tmp.setStrTx_nom(resSet.getString("tx_nom"));
                    tmp.setStrTxa_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultMod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usrIng(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrMod(resSet.getInt("co_usrmod"));

                    String[][] strTbl_entsaldet = new String[7][3];
                    //int intDiaSum = 1;

                    //for(int i = 0; i < 7; i++){

                        strSql = "";
                        strSql = "select * from tbm_dethortra ";
                        strSql += "where (co_hor = ?) ";

                        PreparedStatement preSta2 = connection.prepareStatement(strSql);

                        //if(tbm_horTra.getIntCo_hor()>0){
                            preSta2.setInt(1, resSet.getInt("co_hor"));
                        //}else{
                            //preSta2.setNull(1, Types.INTEGER);
                            //preSta2.setNull(2, Types.INTEGER);
                        //}

                        /*if(intDiaSum>0){
                            preSta2.setInt(3, intDiaSum);
                            preSta2.setInt(4, intDiaSum);
                        }else{
                            preSta2.setNull(3, Types.INTEGER);
                            preSta2.setNull(4, Types.INTEGER);
                        }

                        /*if(intCanReg > 0 ){
                            preSta2.setInt(5, intCanReg);
                        }else{
                            preSta2.setNull(5, Types.INTEGER);
                        }*/

                        //offset 12
                        //preSta.setInt(6, intRegIni);
                        ResultSet resSet2 = preSta2.executeQuery();
                        //int i = 0;
                        if(resSet2.next()){
                        do{

                                //System.out.println("codigo horario: " + resSet2.getString("co_hor"));
                                //System.out.println("dia consultado: " + (resSet2.getInt("ne_dia")-1));
                                if(resSet2.getInt("ne_dia")-1!=7){
                                    strTbl_entsaldet[resSet2.getInt("ne_dia")-1][0] = resSet2.getString("ho_ent");
                                    strTbl_entsaldet[resSet2.getInt("ne_dia")-1][1] = resSet2.getString("ho_sal");
                                    strTbl_entsaldet[resSet2.getInt("ne_dia")-1][2] = resSet2.getString("ho_mingraent");
                                }else{
                                    break;
                                }
                        }while(resSet2.next());
                        //}
                        tmp.setStrTbl_entsaldet(strTbl_entsaldet);
                        lisTbm_hortra.add(tmp);
                        //intDiaSum++;
                        //i = 0;
                    }
                }while(resSet.next());
            }

            
        }finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_hortra;
    }

    /**
     * Consulta general de las tablas tbm_cabhortra & tbm_dethortra aplicando pagineo
     * @param connection
     * @param tbm_hortra Objeto con parámetros de consulta
     * @param intRegIni Número inicial de registro del conjunto de registros
     * @param intCanReg Cantidad de registros a consultar a partir del número inicial de registro
     * @return Retorna lista de tipo Tbm_hortra con los registros consultados
     * @throws SQLException
     */
    public List<Tbm_hortra> consultarLisPag(Connection connection, Tbm_hortra tbm_horTra, int intRegIni, int intCanReg) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_hortra> lisTbm_hortra = null;

        try{

            //**falta realizar inner join
            strSql = "select * from tbm_cabhortra ";
            strSql += "where (? is null or co_hor = ?) ";
            strSql += "and (? is null or lower(tx_nom) like lower(?)) ";
            strSql += "and (? is null or lower(tx_obs1) like lower(?)) ";
            strSql += "and (? is null or lower(st_reg) = lower(?)) ";
            strSql += "and (? is null or fe_ing = ?) ";
            strSql += "and (? is null or fe_ultmod = ?) ";
            strSql += "and (? is null or co_usring = ?) ";
            strSql += "and (? is null or co_usrmod = ?) ";
            strSql += "order by co_hor ";
            strSql += "limit ? offset ?";

            preSta = connection.prepareStatement(strSql);

            if(tbm_horTra.getIntCo_hor()>0){
                preSta.setInt(1, tbm_horTra.getIntCo_hor());
                preSta.setInt(2, tbm_horTra.getIntCo_hor());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }

            if(tbm_horTra.getStrTx_nom()!=null){
                preSta.setString(3, tbm_horTra.getStrTx_nom().replace('*', '%'));
                preSta.setString(4, tbm_horTra.getStrTx_nom().replace('*', '%'));
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }

            if(tbm_horTra.getStrTxa_obs1()!=null){
                preSta.setString(5, tbm_horTra.getStrTxa_obs1().replace('*', '%'));
                preSta.setString(6, tbm_horTra.getStrTxa_obs1().replace('*', '%'));
            }else{
                preSta.setNull(5, Types.VARCHAR);
                preSta.setNull(6, Types.VARCHAR);
            }

            if(tbm_horTra.getStrSt_reg()!=null){
                preSta.setString(7, tbm_horTra.getStrSt_reg());
                preSta.setString(8, tbm_horTra.getStrSt_reg());
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }

            if(tbm_horTra.getDatFe_ing()!=null){
                preSta.setTimestamp(9, new Timestamp(tbm_horTra.getDatFe_ing().getTime()));
                preSta.setTimestamp(10, new Timestamp(tbm_horTra.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(9, Types.TIMESTAMP);
                preSta.setNull(10, Types.TIMESTAMP);
            }

            if(tbm_horTra.getDatFe_ultMod()!=null){
                preSta.setTimestamp(11, new Timestamp(tbm_horTra.getDatFe_ultMod().getTime()));
                preSta.setTimestamp(12, new Timestamp(tbm_horTra.getDatFe_ultMod().getTime()));
            }else{
                preSta.setNull(11, Types.TIMESTAMP);
                preSta.setNull(12, Types.TIMESTAMP);
            }

            if(tbm_horTra.getIntCo_usrIng()>0){
                preSta.setInt(13, tbm_horTra.getIntCo_usrIng());
                preSta.setInt(14, tbm_horTra.getIntCo_usrIng());
            }else{
                preSta.setNull(13, Types.INTEGER);
                preSta.setNull(14, Types.INTEGER);
            }

            if(tbm_horTra.getIntCo_usrMod()>0){
                preSta.setInt(15, tbm_horTra.getIntCo_usrMod());
                preSta.setInt(16, tbm_horTra.getIntCo_usrMod());
            }else{
                preSta.setNull(15, Types.INTEGER);
                preSta.setNull(16, Types.INTEGER);
            }

            //limit 11
            if(intCanReg > 0 ){
                preSta.setInt(17, intCanReg);
            }else{
                preSta.setNull(17, Types.INTEGER);
            }

            //offset 12
            preSta.setInt(18, intRegIni);

            resSet = preSta.executeQuery();

            Tbm_hortra tmp = new Tbm_hortra();
            if(resSet.next()){
                lisTbm_hortra = new ArrayList<Tbm_hortra>();
                do{
                    tmp.setIntCo_hor(resSet.getInt("co_hor"));
                    tmp.setStrTx_nom(resSet.getString("tx_nom"));
                    tmp.setStrTxa_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultMod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usrIng(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrMod(resSet.getInt("co_usrmod"));

                    String[][] strTbl_entsaldet = new String[7][3];
                    //int intDiaSum = 1;

                    //for(int i = 0; i < 7; i++){

                        strSql = "";
                        strSql = "select * from tbm_dethortra ";
                        strSql += "where (? is null or co_hor = ?) ";
                        //strSql += "and (? is null or ne_dia = ?) ";
                        //strSql += "where co_hor = ? ";
                        strSql += "order by co_hor ";

                        //strSql += "limit ? offset ?";

                        PreparedStatement preSta2 = connection.prepareStatement(strSql);

                        //if(tbm_horTra.getIntCo_hor()>0){
                            preSta2.setInt(1, resSet.getInt("co_hor"));
                            preSta2.setInt(2, resSet.getInt("co_hor"));
                        //}else{
                            //preSta2.setNull(1, Types.INTEGER);
                            //preSta2.setNull(2, Types.INTEGER);
                        //}

                        /*if(intDiaSum>0){
                            preSta2.setInt(3, intDiaSum);
                            preSta2.setInt(4, intDiaSum);
                        }else{
                            preSta2.setNull(3, Types.INTEGER);
                            preSta2.setNull(4, Types.INTEGER);
                        }

                        /*if(intCanReg > 0 ){
                            preSta2.setInt(5, intCanReg);
                        }else{
                            preSta2.setNull(5, Types.INTEGER);
                        }*/

                        //offset 12
                        //preSta.setInt(6, intRegIni);
                        ResultSet resSet2 = preSta2.executeQuery();
                        //int i = 0;
                        if(resSet2.next()){
                            //while(resSet2.next())
                            do{

                                //System.out.println("codigo horario: " + resSet2.getString("co_hor"));
                                //System.out.println("dia consultado: " + (resSet2.getInt("ne_dia")-1));
                                if(resSet2.getInt("ne_dia")-1!=7){
                                    strTbl_entsaldet[resSet2.getInt("ne_dia")-1][0] = resSet2.getString("ho_ent");
                                    strTbl_entsaldet[resSet2.getInt("ne_dia")-1][1] = resSet2.getString("ho_sal");
                                    strTbl_entsaldet[resSet2.getInt("ne_dia")-1][2] = resSet2.getString("ho_mingraent");
                                }else{
                                    break;
                                }


                                //i++;
                            }while(resSet2.next());
                        }
                        tmp.setStrTbl_entsaldet(strTbl_entsaldet);
                        lisTbm_hortra.add(tmp);
                        //intDiaSum++;
                        //i = 0;
                    //}
                }while(resSet.next());
            }

            /*String[][] strTbl_entsaldet = new String[7][3];
            int intDiaSum = 1;

            for(int i = 0; i < 7; i++){

                strSql = "";
                strSql = "select * from tbm_dethortra ";
                strSql += "where (? is null or co_hor = ?) ";
                strSql += "and (? is null or ne_dia = ?) ";
                strSql += "order by co_hor ";
                strSql += "limit ? offset ?";

                preSta = connection.prepareStatement(strSql);

                if(tbm_horTra.getIntCo_hor()>0){
                    preSta.setInt(1, tbm_horTra.getIntCo_hor());
                    preSta.setInt(2, tbm_horTra.getIntCo_hor());
                }else{
                    preSta.setNull(1, Types.INTEGER);
                    preSta.setNull(2, Types.INTEGER);
                }

                if(intDiaSum>0){
                    preSta.setInt(3, intDiaSum);
                    preSta.setInt(4, intDiaSum);
                }else{
                    preSta.setNull(3, Types.INTEGER);
                    preSta.setNull(4, Types.INTEGER);
                }

                if(intCanReg > 0 ){
                    preSta.setInt(5, intCanReg);
                }else{
                    preSta.setNull(5, Types.INTEGER);
                }

                //offset 12
                preSta.setInt(6, intRegIni);
                resSet = preSta.executeQuery();

                if(resSet.next()){
                    do{
                        strTbl_entsaldet[i][0] = resSet.getString("ho_ent");
                        strTbl_entsaldet[i][1] = resSet.getString("ho_sal");
                        strTbl_entsaldet[i][2] = resSet.getString("ho_mingraent");
                    }while(resSet.next());
                }
                intDiaSum++;
            }
            tmp.setStrTbl_entsaldet(strTbl_entsaldet);
            lisTbm_hortra.add(tmp);*/
        }finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_hortra;
    }

    /**
     * Consulta si el codigo y nombre horario ya se encuentra registrado en la base
     * @param strNomHor Nombre de horario a consultar
     * @param intCodHor Código del registro
     * @return Retorna true si ya existe y false caso contrario
     * @throws Exception
     */
    public boolean consultarNomHorRep(Connection connection, String strNomHor, int intCodHor) throws SQLException {
        boolean blnRep = false;
        String strSql = null;
        int intNumRep = 0;
        PreparedStatement preSta = null;
        ResultSet resSet = null;

        try{
            strSql = "select 1 as flag from tbm_cabhortra where tx_nom = ? and co_hor != ? and st_reg != 'I'";

            preSta = connection.prepareStatement(strSql);
            preSta.setString(1, strNomHor);
            preSta.setInt(2, intCodHor);

            resSet = preSta.executeQuery();

            if(resSet.next()){
                intNumRep = resSet.getInt("flag");
                blnRep = (intNumRep == 1);
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return blnRep;
    }

    /**
     * Consulta los horarios de trabajos que se encuentran activos en la base
     * @return Retorna una lista tipo Tbm_hortra cargada con la información de la base
     */
    public List<Tbm_hortra> consultarCalLabAct(Connection connection) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_hortra> lisTbm_hortra = null;

        try{
            strSql = "select * from tbm_cabhortra a ";
            strSql += "inner join tbm_dethortra b on a.co_hor = b.co_hor ";
            //strSql += "where a.co_hor = 30 ";//a.co_hor = 29 or
            strSql += "where a.st_reg like 'A' ";
            strSql += "order by a.co_hor desc, b.ne_dia asc";// ";

            preSta = connection.prepareStatement(strSql);
            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_hortra = new ArrayList<Tbm_hortra>();
                do{
                    Tbm_hortra tmp = new Tbm_hortra();
                    String[][] strTbl_entsaldet = new String[1][3];
                    tmp.setIntCo_hor(resSet.getInt("co_hor"));
                    tmp.setStrTx_nom(resSet.getString("tx_nom"));
                    tmp.setStrTxa_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultMod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usrIng(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrMod(resSet.getInt("co_usrmod"));
                    tmp.setIntNe_diadet(resSet.getInt("ne_dia"));
                    strTbl_entsaldet[0][0] = resSet.getString("ho_ent");
                    strTbl_entsaldet[0][1] = resSet.getString("ho_sal");
                    strTbl_entsaldet[0][2] = resSet.getString("ho_mingraent");
                    tmp.setStrTbl_entsaldet(strTbl_entsaldet);
                    lisTbm_hortra.add(tmp);
                }while(resSet.next());
                
            }
        }catch(Exception e){
             e.printStackTrace();
        }
        finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }
        return lisTbm_hortra;
    }

    /**
     * Consulta los horarios de trabajos que se encuentran activos en la base
     * @return Retorna arreglo de enteros con los dias de ingreso de los horarios de trabajo existentes en la tabla
     */
    public int[] consultaDiasValidosIniciales(Connection connection) throws SQLException {

        String strSql = null;
        PreparedStatement preSta = null;
        Statement sta = null;
        ResultSet resSet = null;
        int intRec = 0;
        int[] intDiasRes = null;

        try{


            strSql=  "select count(*) from tbm_cabhortra ";
            strSql+= "where st_reg like 'A' ";

            sta = connection.createStatement();
            resSet = sta.executeQuery(strSql);
            if(resSet.next()){
                intDiasRes = new int[resSet.getInt("count")];

                strSql = "select * from tbm_cabhortra ";
                strSql+= "where st_reg like 'A' ";
                strSql+= "order by co_hor desc ";

                preSta = connection.prepareStatement(strSql);
                resSet = preSta.executeQuery();

                while(resSet.next()){
                String formato="dd";
                SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
                intDiasRes[intRec] = Integer.parseInt(dateFormat.format(resSet.getTimestamp("fe_ing")));
                intRec++;
                }

            }
            
        }catch(Exception e){
             e.printStackTrace();
        }
        finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();sta.close();}catch(Throwable ignore){}
        }
        return intDiasRes;
    }

    /**
     * Consulta los horarios de trabajos que se encuentran activos en la base
     * @return Retorna arreglo de enteros con los meses de ingreso de los horarios de trabajo existentes en la tabla
     */
    public int[] consultaMesesValidosIniciales(Connection connection) throws SQLException {

        String strSql = null;
        PreparedStatement preSta = null;
        Statement sta = null;
        ResultSet resSet = null;
        int intRec = 0;
        int[] intMesesRes = null;

        try{

            strSql=  "select count(*) from tbm_cabhortra ";
            strSql+= "where st_reg like 'A' ";

            sta = connection.createStatement();
            resSet = sta.executeQuery(strSql);
            if(resSet.next()){
                intMesesRes = new int[resSet.getInt("count")];

                strSql = "select * from tbm_cabhortra ";
                strSql+= "where st_reg like 'A' ";
                strSql+= "order by co_hor desc ";

                preSta = connection.prepareStatement(strSql);
                resSet = preSta.executeQuery();

                while(resSet.next()){
                String formato="MM";
                SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
                intMesesRes[intRec] = Integer.parseInt(dateFormat.format(resSet.getTimestamp("fe_ing")));
                intRec++;
                }

            }

        }catch(Exception e){
             e.printStackTrace();
        }
        finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();sta.close();}catch(Throwable ignore){}
        }
        return intMesesRes;
    }

    public int[] consultaCodHor(Connection connection) throws SQLException {
        String strSql = null;
        Statement sta = null;
        ResultSet resSet = null;
        int intPos = 0;
        int intRes[] = null;


        try{
            strSql = "select count(a.co_hor) as max from tbm_cabhortra a ";
            //strSql += "where a.co_hor = 30 ";//a.co_hor = 29 or
            strSql += "where a.st_reg like 'A' ";

            sta = connection.createStatement();
            resSet = sta.executeQuery(strSql);
            if(resSet.next()){
                intRes = new int[resSet.getInt("max")];
            }

            strSql = "select distinct a.co_hor from tbm_cabhortra a ";
            strSql += "inner join tbm_dethortra b on a.co_hor = b.co_hor ";
            //strSql += "where a.co_hor = 30 ";//a.co_hor = 29 or
            strSql += "where a.st_reg like 'A' ";
            strSql += "order by a.co_hor desc ";

            sta = connection.createStatement();
            resSet = sta.executeQuery(strSql);

            while(resSet.next()){
                intRes[intPos] = resSet.getInt("co_hor");
                intPos++;
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{sta.close();}catch(Throwable ignore){}
        }

        return intRes;

    }

    public List<Tbm_hortra> consultarLisGen(Connection connection, Tbm_hortra tbm_horTra) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int consultarNomHorRep(Connection connection, String strNomHor) throws SQLException {
        int intRes = -1;
        String strSql = null;
        Statement sta = null;
        ResultSet resSet = null;

        try{

            strSql = "select distinct a.co_hor from tbm_cabhortra a ";
            strSql += "where a.st_reg like 'A' ";
            strSql += "and lower(tx_nom) like lower('"+ strNomHor +"')";


            sta = connection.createStatement();
            resSet = sta.executeQuery(strSql);

            while(resSet.next()){
                intRes = resSet.getInt("co_hor");

            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{sta.close();}catch(Throwable ignore){}
        }

        return intRes;

    }

    /*public List<Tbm_hortra> consultarLisGen(Connection connection, Tbm_hortra tbm_horTra) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void grabar(Connection connection, Tbm_hortra tbm_horTra, Date datFecHoy, int intMaxAño) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }*/

}