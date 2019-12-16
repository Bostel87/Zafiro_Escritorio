
package Librerias.ZafConRecHum.ZafConRecHumNotAtr;
 
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.awt.Frame;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JProgressBar;
/**
 * Clase nueva para mostrar información relacionada al Módulo de RRHH.
 * @author Roberto Flores
 * Guayaquil 08/01/2013
 */
public class ZafConRecHumNotAtr extends javax.swing.JPanel
{
    private ZafParSis objParSis;
    private JProgressBar pgrConMem;
    private JButton butGarCol;
   
    private ArrayList arlDat, arlReg, arlDatMarInc;
    private ArrayList arlDatFal, arlRegFal , arlRegMarInc;
     private ZafUtil objUti;
     
    java.awt.Frame parent2;
    javax.swing.JDesktopPane dskGen;

    private int intCodUsr=0;
    
    /** Creates a new instance of ZafConMem */
    public ZafConRecHumNotAtr(ZafParSis obj, Frame parent ) {
        try{ 
            objParSis=obj;        
            parent2=parent;
            objUti = new ZafUtil();
        }catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }    
    }
        
    public ZafConRecHumNotAtr(ZafParSis obj , java.awt.Frame parent , javax.swing.JDesktopPane dskGe)
    {
      try{ 
        //Configurar el JPanel.      ZafConRecHum
        objParSis=obj;
        
        parent2=parent;
        dskGen=dskGe;
        this.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        this.setLayout(new java.awt.BorderLayout());
        this.setPreferredSize(new java.awt.Dimension(140, 20));
        
        objUti = new ZafUtil();
        
//        //Configurar el JProgressBar.
//        pgrConMem=new JProgressBar();
//        pgrConMem.setBorderPainted(false);  
//        pgrConMem.setStringPainted(true);
//        pgrConMem.setBackground(new java.awt.Color(255, 255, 255));
//        pgrConMem.setForeground(new java.awt.Color(0, 0, 0));
//        //Configurar el JButton.
//        butGarCol=new JButton("...");
//        butGarCol.setToolTipText("Liberar memoria");
//        butGarCol.setPreferredSize(new java.awt.Dimension(20, 20));
//        butGarCol.setFocusPainted(false);
//        butGarCol.addActionListener(new java.awt.event.ActionListener() {
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent evt) { 
//                butGarColActionPerformed(evt);
//            }
//        });
        
        
        
        intCodUsr=objParSis.getCodigoUsuario();
        arlDat=new ArrayList();
        arlDatFal=new ArrayList();
        arlDatMarInc=new ArrayList();
        
        String strTxUsr = objParSis.getNombreUsuario();
      
        cargarListadoEmpAtr(objParSis.getNombreUsuario().toUpperCase());

      }catch (Exception e){
          objUti.mostrarMsgErr_F1(this, e);
      }    
         
    }
    
    /**
     * Esta función le sugiere al "Garbage Collector" que libere la memoria.
     */
    private void butGarColActionPerformed(java.awt.event.ActionEvent evt){        
       //System.gc();
       Runtime.getRuntime().gc();        
    }

    private void cargarListadoEmpAtr(String strUsr)
    {
     try{  
             ZafThrConFacPenImp objThrConFacPenImp=new ZafThrConFacPenImp(strUsr);
             objThrConFacPenImp.start();
      }
      catch(Exception e){
          
      }
    }

    private class ZafThrConFacPenImp extends Thread
    {
        
        private String strUsr="";
        private int intDiaAtr=-3;
        java.sql.ResultSet rst = null;
        java.sql.Statement stm = null;
        
        public ZafThrConFacPenImp(String strUsr)
        {
            this.strUsr = strUsr;
        }
        
        @Override
        public void run()
        {
            try
            {
                while (true)
                    {  

                int intEst=0;
                String  strSQL = "";
                String  strSQLFAL = "";
                String strSQLMarInc="";
                
//                String   strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
//                String strFec[] = strFecSis.split(" ");
//                String strFecFin[] = strFec[0].split("-");
//                
//                String strMesHas=strFecFin[1];
//               if(strMesHas.length()==1){
//                   strMesHas="0"+strMesHas;
//               }
//               
//               String strDiaHas=strFecFin[2];
//               if(strDiaHas.length()==1){
//                   strDiaHas="0"+strDiaHas;
//               }
//                
//                String strFecHas = strFecFin[0] + "-" + strMesHas + "-" + strDiaHas;
//
//               java.util.Calendar CalVal =  java.util.Calendar.getInstance();
//               CalVal.set(java.util.Calendar.YEAR,Integer.parseInt(strFecFin[0]));
//               CalVal.set(java.util.Calendar.MONTH,Integer.parseInt(strFecFin[1]));
//               CalVal.set(java.util.Calendar.DATE,Integer.parseInt(strFecFin[2]));
//               CalVal.roll(Calendar.DATE, intDiaAtr);
//
//               int intDiaDes=CalVal.get(Calendar.DATE);
//               int intMesDes=CalVal.get(Calendar.MONTH);
//               int intAñoDes=CalVal.get(Calendar.YEAR);
//               
//               String strMesDes=String.valueOf(intMesDes);
//               if(strMesDes.length()==1){
//                   strMesDes="0"+strMesDes;
//               }
//               
//               String strDiaDes=String.valueOf(intDiaDes);
//               if(strDiaDes.length()==1){
//                   strDiaDes="0"+strDiaDes;
//               }
               
//               String strFecDes = intAñoDes + "-" + strMesDes + "-" + strDiaDes;

                java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());          
                stm = conn.createStatement();
                String strSqlFec="";
                String strFecDes="";
                String strFecHas="";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSqlFec="select fe_des from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=1";
                    
                    rst = stm.executeQuery(strSqlFec);
                    while(rst.next()){
                        strFecDes=rst.getString("fe_des");
                    }
                    strSqlFec="select fe_has from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=2";
                    
                    rst = stm.executeQuery(strSqlFec);
                    while(rst.next()){
                        strFecHas=rst.getString("fe_has");
                    }
                }else{
                    strSqlFec="select fe_des from tbm_feccorrolpag where co_emp="+objParSis.getCodigoEmpresa()+" and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=1";
                    
                    rst = stm.executeQuery(strSqlFec);
                    while(rst.next()){
                        strFecDes=rst.getString("fe_des");
                    }
                    strSqlFec="select fe_has from tbm_feccorrolpag where co_emp="+objParSis.getCodigoEmpresa()+" and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=2";
                    
                    rst = stm.executeQuery(strSqlFec);
                    while(rst.next()){
                        strFecHas=rst.getString("fe_has");
                    }
                }
                
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {

                    
                    if(objParSis.getCodigoUsuario()==1){
//                        strFecDes = "2014-05-27";
                        if(objParSis.getDireccionIP().compareTo("172.16.1.62")==0 || objParSis.getDireccionIP().compareTo("172.16.1.63")==0){
                            strSQL+=" select c.co_emp, e.tx_nom as empresa, a.fe_dia, c.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado, a.ho_ent from tbm_cabconasitra a";
                            strSQL+=" inner join tbm_tra b on (b.co_tra=a.co_tra)";
                            strSQL+=" inner join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A'  and c.st_horfij not like 'N')";
                            strSQL+=" inner join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia)";
                            strSQL+=" inner join tbm_emp e on (e.co_emp=c.co_emp)";
                            strSQL+=" where a.fe_dia between " + objUti.codificar(strFecDes) + " and " + objUti.codificar(strFecHas);
                            strSQL+=" and a.ho_ent is not null";
                            strSQL+=" and (a.ho_ent::interval)>(d.ho_ent::interval+d.ho_mingraent::interval)";
                            strSQL+=" and a.st_jusatr is null";
                            strSQL+=" order by a.fe_dia, a.ho_ent";
                            
                            strSQLFAL+="select c.co_emp, e.tx_nom as empresa, count(a.fe_dia) as ne_numFal, c.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado from tbm_cabconasitra a " + "\n";
                            strSQLFAL+="inner join tbm_tra b on (b.co_tra=a.co_tra) " + "\n";
                            strSQLFAL+="inner join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A'  and c.st_horfij not like 'N') " + "\n";
                            strSQLFAL+="inner join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia) " + "\n";
                            strSQLFAL+="inner join tbm_emp e on (e.co_emp=c.co_emp) " + "\n";
                            strSQLFAL+="where a.fe_dia between  (select fe_des from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=1) " + "\n";
                            strSQLFAL+="and (select fe_has from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=2) " + "\n";
                            strSQLFAL+="and (a.ho_ent is null and a.ho_sal is null) " + "\n";
                            strSQLFAL+="and a.st_jusfal is null " + "\n";
                            strSQLFAL+="AND NOT (EXTRACT(DOW FROM a.fe_dia ) in (6,7))" + "\n";
                            strSQLFAL+="GROUP BY c.co_emp, empresa, empleado, c.co_tra " + "\n";
                            strSQLFAL+="order by ne_numFal desc ";
                            
                            strSQLMarInc+="select c.co_emp, e.tx_nom as empresa, count(a.fe_dia) as ne_numMarInc , c.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado from tbm_cabconasitra a " + "\n";
                            strSQLMarInc+="left outer join tbm_tra b on (b.co_tra=a.co_tra) " + "\n";
                            strSQLMarInc+="left outer join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A'  and c.st_horfij not like 'N') " + "\n";
                            strSQLMarInc+="left outer join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia) " + "\n";
                            strSQLMarInc+="left outer join tbm_emp e on (e.co_emp=c.co_emp) " + "\n";
                            strSQLMarInc+="where a.fe_dia between (select fe_des from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=1) " + "\n";
                            strSQLMarInc+="and (select fe_has from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=2) " + "\n";
                            strSQLMarInc+="and  ( (a.ho_ent is not null and a.ho_sal is null) " + "\n";
                            strSQLMarInc+="or (a.ho_ent is null and a.ho_sal is not null))  " + "\n";
                            strSQLMarInc+="and c.st_reg='A' " + "\n";
                            strSQLMarInc+="GROUP BY c.co_emp, empresa, empleado, c.co_tra " + "\n";
                            strSQLMarInc+="order by ne_numMarInc desc ";
                            
                            
                            
                        }
                    }else if ( objParSis.getCodigoUsuario()==172 || objParSis.getCodigoUsuario()==220 || objParSis.getCodigoUsuario()==209
                            || objParSis.getCodigoUsuario()==225 || objParSis.getCodigoUsuario()==239){
//                        strFecDes = "2014-05-27";
                        strSQL+=" select c.co_emp, e.tx_nom as empresa, a.fe_dia, c.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado, a.ho_ent from tbm_cabconasitra a";
                        strSQL+=" inner join tbm_tra b on (b.co_tra=a.co_tra)";
                        strSQL+=" inner join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A'  and c.st_horfij not like 'N')";
                        strSQL+=" inner join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia)";
                        strSQL+=" inner join tbm_emp e on (e.co_emp=c.co_emp)";
                        strSQL+=" where a.fe_dia between " + objUti.codificar(strFecDes) + " and " + objUti.codificar(strFecHas);
                        strSQL+=" and a.ho_ent is not null";
                        strSQL+=" and (a.ho_ent::interval)>(d.ho_ent::interval+d.ho_mingraent::interval)";               
                        strSQL+=" and a.st_jusatr is null";
                        strSQL+=" order by a.fe_dia, a.ho_ent";
                        
                        strSQLFAL+="select c.co_emp, e.tx_nom as empresa, count(a.fe_dia) as ne_numFal, c.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado from tbm_cabconasitra a " + "\n";
                        strSQLFAL+="inner join tbm_tra b on (b.co_tra=a.co_tra) " + "\n";
                        strSQLFAL+="inner join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A'  and c.st_horfij not like 'N') " + "\n";
                        strSQLFAL+="inner join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia) " + "\n";
                        strSQLFAL+="inner join tbm_emp e on (e.co_emp=c.co_emp) " + "\n";
                        strSQLFAL+="where a.fe_dia between (select fe_des from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=1) " + "\n";
                        strSQLFAL+="and (select fe_has from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=2) " + "\n";
                        strSQLFAL+="and (a.ho_ent is null and a.ho_sal is null) " + "\n";
                        strSQLFAL+="and a.st_jusfal is null " + "\n";
                        strSQLFAL+="AND NOT (EXTRACT(DOW FROM a.fe_dia ) in (6,7))" + "\n";
                        strSQLFAL+="GROUP BY c.co_emp, empresa, empleado, c.co_tra " + "\n";
                        strSQLFAL+="order by ne_numFal desc ";
                        
                        strSQLMarInc+="select c.co_emp, e.tx_nom as empresa, count(a.fe_dia) as ne_numMarInc , c.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado from tbm_cabconasitra a " + "\n";
                        strSQLMarInc+="left outer join tbm_tra b on (b.co_tra=a.co_tra) " + "\n";
                        strSQLMarInc+="left outer join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A'  and c.st_horfij not like 'N') " + "\n";
                        strSQLMarInc+="left outer join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia) " + "\n";
                        strSQLMarInc+="left outer join tbm_emp e on (e.co_emp=c.co_emp) " + "\n";
                        strSQLMarInc+="where a.fe_dia between (select fe_des from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=1) " + "\n";
                        strSQLMarInc+="and (select fe_has from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=2) " + "\n";
                        strSQLMarInc+="and  ( (a.ho_ent is not null and a.ho_sal is null) " + "\n";
                        strSQLMarInc+="or (a.ho_ent is null and a.ho_sal is not null))  " + "\n";
                        strSQLMarInc+="and c.st_reg='A' " + "\n";
                        strSQLMarInc+="GROUP BY c.co_emp, empresa, empleado, c.co_tra " + "\n";
                        strSQLMarInc+="order by ne_numMarInc desc ";

                    }
                    else{
                        strSQL+=" select c.co_emp, e.tx_nom as empresa, a.fe_dia, c.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado, a.ho_ent from tbm_cabconasitra a";
                        strSQL+=" inner join tbm_tra b on (b.co_tra=a.co_tra)";
                        strSQL+=" inner join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A'  and c.st_horfij not like 'N')";
                        strSQL+=" inner join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia)";
                        strSQL+=" inner join tbm_emp e on (e.co_emp=c.co_emp)";
                        strSQL+=" where a.fe_dia between " + objUti.codificar(strFecDes) + " and " + objUti.codificar(strFecHas);
                        strSQL+=" and a.ho_ent is not null";
                        strSQL+=" and (a.ho_ent::interval)>(d.ho_ent::interval+d.ho_mingraent::interval)";
                        strSQL+=" and a.st_jusatr is null";
                        strSQL+=" and c.co_jef in (";
                        strSQL+=" select distinct co_tra";
                        strSQL+=" from ";
                        strSQL+=" (select d.co_usr, upper(d.tx_usr) as tx_usr ";
                        strSQL+=" from tbm_usr as d ";
                        strSQL+=" where d.st_reg='A') as c, ";
                        strSQL+=" (select b.co_dep, a.co_tra, trim(substr(a.tx_nom,1,1) || substr(a.tx_ape, 1, case when position(' ' in a.tx_ape) = 0 then length(a.tx_ape) else position(' ' in a.tx_ape) end)) as tx_usr ";
                        strSQL+=" from tbm_tra as a ";
                        strSQL+=" inner join tbm_traemp as b on (b.co_tra=a.co_tra and b.st_reg='A') ";
                        strSQL+=" where a.st_reg='A') as e ";
                        strSQL+=" where c.tx_usr=e.tx_usr";
                        strSQL+=" and e.tx_usr="+objUti.codificar(strUsr.toUpperCase()) +")";
                        strSQL+=" order by a.fe_dia, a.ho_ent";
                        
                        strSQLFAL+="select c.co_emp, e.tx_nom as empresa, count(a.fe_dia) as ne_numFal , c.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado from tbm_cabconasitra a " + "\n";
                        strSQLFAL+="inner join tbm_tra b on (b.co_tra=a.co_tra) " + "\n";
                        strSQLFAL+="inner join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A'  and c.st_horfij not like 'N') " + "\n";
                        strSQLFAL+="inner join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia) " + "\n";
                        strSQLFAL+="inner join tbm_emp e on (e.co_emp=c.co_emp) " + "\n";
                        strSQLFAL+="where a.fe_dia between (select fe_des from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(YEAR from current_date) and ne_mes=EXTRACT(MONTH from current_date) and ne_per=1) " + "\n";
                        strSQLFAL+="and (select fe_has from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(YEAR from current_date) and ne_mes=EXTRACT(MONTH from current_date) and ne_per=2) " + "\n";
                        strSQLFAL+="and (a.ho_ent is null and a.ho_sal is null) " + "\n";
                        strSQLFAL+="and a.st_jusfal is null " + "\n";
                        strSQLFAL+="and c.co_jef in ( " + "\n";
                        strSQLFAL+="select distinct co_tra " + "\n";
                        strSQLFAL+="from  " + "\n";
                        strSQLFAL+="(select d.co_usr, upper(d.tx_usr) as tx_usr  " + "\n";
                        strSQLFAL+="from tbm_usr as d  " + "\n";
                        strSQLFAL+="where d.st_reg='A') as c,  " + "\n";
                        strSQLFAL+="(select b.co_dep, a.co_tra, trim(substr(a.tx_nom,1,1) || substr(a.tx_ape, 1, case when position(' ' in a.tx_ape) = 0 then length(a.tx_ape) else position(' ' in a.tx_ape) end)) as tx_usr  " + "\n";
                        strSQLFAL+="from tbm_tra as a  " + "\n";
                        strSQLFAL+="inner join tbm_traemp as b on (b.co_tra=a.co_tra and b.st_reg='A')  " + "\n";
                        strSQLFAL+="where a.st_reg='A') as e  " + "\n";
                        strSQLFAL+="where c.tx_usr=e.tx_usr " + "\n";
                        strSQLFAL+="and e.tx_usr="+objUti.codificar(strUsr.toUpperCase()) +") " + "\n";
                        strSQLFAL+="AND NOT (EXTRACT(DOW FROM a.fe_dia ) in (6,7))" + "\n";
                        strSQLFAL+="GROUP BY c.co_emp, empresa, empleado, c.co_tra " + "\n";
                        strSQLFAL+="order by ne_numFal desc ";
                        
                        strSQLMarInc+="select c.co_emp, e.tx_nom as empresa, count(a.fe_dia) as ne_numMarInc , c.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado from tbm_cabconasitra a " + "\n";
                        strSQLMarInc+="inner join tbm_tra b on (b.co_tra=a.co_tra) " + "\n";
                        strSQLMarInc+="inner join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A'  and c.st_horfij not like 'N') " + "\n";
                        strSQLMarInc+="inner join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia) " + "\n";
                        strSQLMarInc+="inner join tbm_emp e on (e.co_emp=c.co_emp) " + "\n";
                        strSQLMarInc+="where a.fe_dia between (select fe_des from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(YEAR from current_date) and ne_mes=EXTRACT(MONTH from current_date) and ne_per=1) " + "\n";
                        strSQLMarInc+="and (select fe_has from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(YEAR from current_date) and ne_mes=EXTRACT(MONTH from current_date) and ne_per=2) " + "\n";
                        strSQLMarInc+="and  ( (a.ho_ent is not null and a.ho_sal is null) " + "\n";
                        strSQLMarInc+="or (a.ho_ent is null and a.ho_sal is not null))  " + "\n";
                        strSQLMarInc+="and c.st_reg='A' " + "\n";
                        strSQLMarInc+="and c.co_jef in ( " + "\n";
                        strSQLMarInc+="select distinct co_tra " + "\n";
                        strSQLMarInc+="from  " + "\n";
                        strSQLMarInc+="(select d.co_usr, upper(d.tx_usr) as tx_usr  " + "\n";
                        strSQLMarInc+="from tbm_usr as d  " + "\n";
                        strSQLMarInc+="where d.st_reg='A') as c,  " + "\n";
                        strSQLMarInc+="(select b.co_dep, a.co_tra, trim(substr(a.tx_nom,1,1) || substr(a.tx_ape, 1, case when position(' ' in a.tx_ape) = 0 then length(a.tx_ape) else position(' ' in a.tx_ape) end)) as tx_usr  " + "\n";
                        strSQLMarInc+="from tbm_tra as a  " + "\n";
                        strSQLMarInc+="inner join tbm_traemp as b on (b.co_tra=a.co_tra and b.st_reg='A')  " + "\n";
                        strSQLMarInc+="where a.st_reg='A') as e  " + "\n";
                        strSQLMarInc+="where c.tx_usr=e.tx_usr " + "\n";
                        strSQLMarInc+="and e.tx_usr="+objUti.codificar(strUsr.toUpperCase()) +") " + "\n";
                        strSQLMarInc+="GROUP BY c.co_emp, empresa, empleado, c.co_tra " + "\n";
                        strSQLMarInc+="order by ne_numMarInc desc ";
                        
                    }
                 }else{
                    if(objParSis.getCodigoUsuario()==1){
//                        strFecDes = "2014-05-27";
                        if(objParSis.getDireccionIP().compareTo("172.16.1.62")==0 || objParSis.getDireccionIP().compareTo("172.16.1.63")==0){
                            strSQL+=" select c.co_emp, e.tx_nom as empresa, a.fe_dia, c.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado, a.ho_ent from tbm_cabconasitra a";
                            strSQL+=" inner join tbm_tra b on (b.co_tra=a.co_tra)";
                            strSQL+=" inner join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A'  and c.st_horfij not like 'N')";
                            strSQL+=" inner join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia)";
                            strSQL+=" inner join tbm_emp e on (e.co_emp=c.co_emp)";
                            strSQL+=" where a.fe_dia between " + objUti.codificar(strFecDes) + " and " + objUti.codificar(strFecHas);
                            strSQL+=" and a.ho_ent is not null";
                            strSQL+=" and (a.ho_ent::interval)>(d.ho_ent::interval+d.ho_mingraent::interval)";
                            strSQL+=" and a.st_jusatr is null";
                            strSQL+=" and c.co_emp="+objParSis.getCodigoEmpresa();
                            strSQL+=" order by a.fe_dia, a.ho_ent";
                            
                            strSQLFAL+="select c.co_emp, e.tx_nom as empresa, count(a.fe_dia) as ne_numFal, c.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado from tbm_cabconasitra a " + "\n";
                            strSQLFAL+="inner join tbm_tra b on (b.co_tra=a.co_tra) " + "\n";
                            strSQLFAL+="inner join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A'  and c.st_horfij not like 'N') " + "\n";
                            strSQLFAL+="inner join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia) " + "\n";
                            strSQLFAL+="inner join tbm_emp e on (e.co_emp=c.co_emp) " + "\n";
                            strSQLFAL+="where a.fe_dia between (select fe_des from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(YEAR from current_date) and ne_mes=EXTRACT(MONTH from current_date) and ne_per=1) " + "\n";
                            strSQLFAL+="and (select fe_has from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(YEAR from current_date) and ne_mes=EXTRACT(MONTH from current_date) and ne_per=2) " + "\n";
                            strSQLFAL+="and (a.ho_ent is null and a.ho_sal is null) " + "\n";
                            strSQLFAL+="and a.st_jusfal is null " + "\n";
                            strSQLFAL+="and c.co_emp="+objParSis.getCodigoEmpresa()+ "\n";
                            strSQLFAL+="AND NOT (EXTRACT(DOW FROM a.fe_dia ) in (6,7))" + "\n";
                            strSQLFAL+="GROUP BY c.co_emp, empresa, empleado, c.co_tra " + "\n";
                            strSQLFAL+="order by ne_numFal desc ";
                            
                            strSQLMarInc+="select c.co_emp, e.tx_nom as empresa, count(a.fe_dia) as ne_numMarInc , c.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado from tbm_cabconasitra a " + "\n";
                            strSQLMarInc+="left outer join tbm_tra b on (b.co_tra=a.co_tra) " + "\n";
                            strSQLMarInc+="left outer join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A'  and c.st_horfij not like 'N') " + "\n";
                            strSQLMarInc+="left outer join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia) " + "\n";
                            strSQLMarInc+="left outer join tbm_emp e on (e.co_emp=c.co_emp) " + "\n";
                            strSQLMarInc+="where a.fe_dia between (select fe_des from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=1) " + "\n";
                            strSQLMarInc+="and (select fe_has from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=2) " + "\n";
                            strSQLMarInc+="and  ( (a.ho_ent is not null and a.ho_sal is null) " + "\n";
                            strSQLMarInc+="or (a.ho_ent is null and a.ho_sal is not null))  " + "\n";
                            strSQLMarInc+="and c.co_emp="+objParSis.getCodigoEmpresa();
                            strSQLMarInc+="GROUP BY c.co_emp, empresa, empleado, c.co_tra " + "\n";
                            strSQLMarInc+="order by ne_numMarInc desc ";
                            
                        }
                    }else if ( objParSis.getCodigoUsuario()==172 || objParSis.getCodigoUsuario()==220 || objParSis.getCodigoUsuario()==209
                            || objParSis.getCodigoUsuario()==225 || objParSis.getCodigoUsuario()==239 ){
                        strSQL+=" select c.co_emp, e.tx_nom as empresa, a.fe_dia, c.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado, a.ho_ent from tbm_cabconasitra a";
                        strSQL+=" inner join tbm_tra b on (b.co_tra=a.co_tra)";
                        strSQL+=" inner join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A'  and c.st_horfij not like 'N')";
                        strSQL+=" inner join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia)";
                        strSQL+=" inner join tbm_emp e on (e.co_emp=c.co_emp)";
                        strSQL+=" where a.fe_dia between " + objUti.codificar(strFecDes) + " and " + objUti.codificar(strFecHas);
                        strSQL+=" and a.ho_ent is not null";
                        strSQL+=" and (a.ho_ent::interval)>(d.ho_ent::interval+d.ho_mingraent::interval)";
                        strSQL+=" and a.st_jusatr is null";
                        strSQL+=" and c.co_emp="+objParSis.getCodigoEmpresa();
                        strSQL+=" order by a.fe_dia, a.ho_ent";
                        
                        strSQLFAL+="select c.co_emp, e.tx_nom as empresa, count(a.fe_dia) as ne_numFal , c.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado from tbm_cabconasitra a " + "\n";
                        strSQLFAL+="inner join tbm_tra b on (b.co_tra=a.co_tra) " + "\n";
                        strSQLFAL+="inner join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A'  and c.st_horfij not like 'N') " + "\n";
                        strSQLFAL+="inner join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia) " + "\n";
                        strSQLFAL+="inner join tbm_emp e on (e.co_emp=c.co_emp) " + "\n";
                        strSQLFAL+="where a.fe_dia between (select fe_des from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(YEAR from current_date) and ne_mes=EXTRACT(MONTH from current_date) and ne_per=1) " + "\n";
                        strSQLFAL+="and (select fe_has from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(YEAR from current_date) and ne_mes=EXTRACT(MONTH from current_date) and ne_per=2) " + "\n";
                        strSQLFAL+="and (a.ho_ent is null and a.ho_sal is null) " + "\n";
                        strSQLFAL+="and a.st_jusfal is null " + "\n";
                        strSQLFAL+="and c.co_emp="+objParSis.getCodigoEmpresa()+"\n";
                        strSQLFAL+="AND NOT (EXTRACT(DOW FROM a.fe_dia ) in (6,7))" + "\n";
                        strSQLFAL+="GROUP BY c.co_emp, empresa, empleado, c.co_tra " + "\n";
                        strSQLFAL+="order by ne_numFal desc ";
                        
                        strSQLMarInc+="select c.co_emp, e.tx_nom as empresa, count(a.fe_dia) as ne_numMarInc , c.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado from tbm_cabconasitra a " + "\n";
                        strSQLMarInc+="left outer join tbm_tra b on (b.co_tra=a.co_tra) " + "\n";
                        strSQLMarInc+="left outer join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A'  and c.st_horfij not like 'N') " + "\n";
                        strSQLMarInc+="left outer join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia) " + "\n";
                        strSQLMarInc+="left outer join tbm_emp e on (e.co_emp=c.co_emp) " + "\n";
                        strSQLMarInc+="where a.fe_dia between (select fe_des from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=1) " + "\n";
                        strSQLMarInc+="and (select fe_has from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=2) " + "\n";
                        strSQLMarInc+="and  ( (a.ho_ent is not null and a.ho_sal is null) " + "\n";
                        strSQLMarInc+="or (a.ho_ent is null and a.ho_sal is not null))  " + "\n";
                        strSQLMarInc+="and c.co_emp="+objParSis.getCodigoEmpresa();
                        strSQLMarInc+="GROUP BY c.co_emp, empresa, empleado, c.co_tra " + "\n";
                        strSQLMarInc+="order by ne_numMarInc desc ";
                        
                    }
                    else{
                        strSQL+=" select c.co_emp, e.tx_nom as empresa, a.fe_dia, c.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado, a.ho_ent from tbm_cabconasitra a";
                        strSQL+=" inner join tbm_tra b on (b.co_tra=a.co_tra)";
                        strSQL+=" inner join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A'  and c.st_horfij not like 'N')";
                        strSQL+=" inner join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia)";
                        strSQL+=" inner join tbm_emp e on (e.co_emp=c.co_emp)";
                        strSQL+="where a.fe_dia between (select fe_des from tbm_feccorrolpag where co_emp="+objParSis.getCodigoEmpresa()+" and ne_ani=EXTRACT(YEAR from current_date) and ne_mes=EXTRACT(MONTH from current_date) and ne_per=1) " + "\n";
                        strSQL+="and (select fe_has from tbm_feccorrolpag where co_emp="+objParSis.getCodigoEmpresa()+" and ne_ani=EXTRACT(YEAR from current_date) and ne_mes=EXTRACT(MONTH from current_date) and ne_per=2) " + "\n";
                        strSQL+=" and a.ho_ent is not null";
                        strSQL+=" and (a.ho_ent::interval)>(d.ho_ent::interval+d.ho_mingraent::interval)";
                        strSQL+=" and a.st_jusatr is null";
                        strSQL+=" and c.co_emp="+objParSis.getCodigoEmpresa();
                        strSQL+=" and c.co_jef in (";
                        strSQL+=" select distinct co_tra";
                        strSQL+=" from ";
                        strSQL+=" (select d.co_usr, upper(d.tx_usr) as tx_usr ";
                        strSQL+=" from tbm_usr as d ";
                        strSQL+=" where d.st_reg='A') as c, ";
                        strSQL+=" (select b.co_dep, a.co_tra, trim(substr(a.tx_nom,1,1) || substr(a.tx_ape, 1, case when position(' ' in a.tx_ape) = 0 then length(a.tx_ape) else position(' ' in a.tx_ape) end)) as tx_usr ";
                        strSQL+=" from tbm_tra as a ";
                        strSQL+=" inner join tbm_traemp as b on (b.co_tra=a.co_tra and b.st_reg='A') ";
                        strSQL+=" where a.st_reg='A') as e ";
                        strSQL+=" where c.tx_usr=e.tx_usr";
                        strSQL+=" and e.tx_usr="+objUti.codificar(strUsr.toUpperCase()) +")";
                        strSQL+=" order by a.fe_dia, a.ho_ent";
                        
                        strSQLFAL+="select c.co_emp, e.tx_nom as empresa, count(a.fe_dia) as ne_numFal , c.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado from tbm_cabconasitra a " + "\n";
                        strSQLFAL+="inner join tbm_tra b on (b.co_tra=a.co_tra) " + "\n";
                        strSQLFAL+="inner join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A') " + "\n";
                        strSQLFAL+="inner join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia  and c.st_horfij not like 'N') " + "\n";
                        strSQLFAL+="inner join tbm_emp e on (e.co_emp=c.co_emp) " + "\n";
                        strSQLFAL+="where a.fe_dia between (select fe_des from tbm_feccorrolpag where co_emp="+objParSis.getCodigoEmpresa()+" and ne_ani=EXTRACT(YEAR from current_date) and ne_mes=EXTRACT(MONTH from current_date) and ne_per=1) " + "\n";
                        strSQLFAL+="and (select fe_has from tbm_feccorrolpag where co_emp="+objParSis.getCodigoEmpresa()+" and ne_ani=EXTRACT(YEAR from current_date) and ne_mes=EXTRACT(MONTH from current_date) and ne_per=2) " + "\n";
                        strSQLFAL+="and (a.ho_ent is null and a.ho_sal is null) " + "\n";
                        strSQLFAL+="and a.st_jusfal is null " + "\n";
                        strSQLFAL+="and c.co_emp="+objParSis.getCodigoEmpresa();
                        strSQLFAL+="and c.co_jef in ( " + "\n";
                        strSQLFAL+="select distinct co_tra " + "\n";
                        strSQLFAL+="from  " + "\n";
                        strSQLFAL+="(select d.co_usr, upper(d.tx_usr) as tx_usr  " + "\n";
                        strSQLFAL+="from tbm_usr as d  " + "\n";
                        strSQLFAL+="where d.st_reg='A') as c,  " + "\n";
                        strSQLFAL+="(select b.co_dep, a.co_tra, trim(substr(a.tx_nom,1,1) || substr(a.tx_ape, 1, case when position(' ' in a.tx_ape) = 0 then length(a.tx_ape) else position(' ' in a.tx_ape) end)) as tx_usr  " + "\n";
                        strSQLFAL+="from tbm_tra as a  " + "\n";
                        strSQLFAL+="inner join tbm_traemp as b on (b.co_tra=a.co_tra and b.st_reg='A')  " + "\n";
                        strSQLFAL+="where a.st_reg='A') as e  " + "\n";
                        strSQLFAL+="where c.tx_usr=e.tx_usr " + "\n";
                        strSQLFAL+="and e.tx_usr="+objUti.codificar(strUsr.toUpperCase()) +") " + "\n";
                        strSQLFAL+="AND NOT (EXTRACT(DOW FROM a.fe_dia ) in (6,7))" + "\n";
                        strSQLFAL+="GROUP BY c.co_emp, empresa, empleado, c.co_tra " + "\n";
                        strSQLFAL+="order by ne_numFal desc ";
                        
                        strSQLMarInc+="select c.co_emp, e.tx_nom as empresa, count(a.fe_dia) as ne_numMarInc , c.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado from tbm_cabconasitra a " + "\n";
                        strSQLMarInc+="inner join tbm_tra b on (b.co_tra=a.co_tra) " + "\n";
                        strSQLMarInc+="inner join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A') " + "\n";
                        strSQLMarInc+="inner join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia  and c.st_horfij not like 'N') " + "\n";
                        strSQLMarInc+="inner join tbm_emp e on (e.co_emp=c.co_emp) " + "\n";
                        strSQLMarInc+="where a.fe_dia between (select fe_des from tbm_feccorrolpag where co_emp="+objParSis.getCodigoEmpresa()+" and ne_ani=EXTRACT(YEAR from current_date) and ne_mes=EXTRACT(MONTH from current_date) and ne_per=1) " + "\n";
                        strSQLMarInc+="and (select fe_has from tbm_feccorrolpag where co_emp="+objParSis.getCodigoEmpresa()+" and ne_ani=EXTRACT(YEAR from current_date) and ne_mes=EXTRACT(MONTH from current_date) and ne_per=2) " + "\n";
                        strSQLMarInc+="and  ( (a.ho_ent is not null and a.ho_sal is null) " + "\n";
                        strSQLMarInc+="or (a.ho_ent is null and a.ho_sal is not null))  " + "\n";
                        strSQLMarInc+="and c.co_emp="+objParSis.getCodigoEmpresa();
                        strSQLMarInc+="and c.co_jef in ( " + "\n";
                        strSQLMarInc+="select distinct co_tra " + "\n";
                        strSQLMarInc+="from  " + "\n";
                        strSQLMarInc+="(select d.co_usr, upper(d.tx_usr) as tx_usr  " + "\n";
                        strSQLMarInc+="from tbm_usr as d  " + "\n";
                        strSQLMarInc+="where d.st_reg='A') as c,  " + "\n";
                        strSQLMarInc+="(select b.co_dep, a.co_tra, trim(substr(a.tx_nom,1,1) || substr(a.tx_ape, 1, case when position(' ' in a.tx_ape) = 0 then length(a.tx_ape) else position(' ' in a.tx_ape) end)) as tx_usr  " + "\n";
                        strSQLMarInc+="from tbm_tra as a  " + "\n";
                        strSQLMarInc+="inner join tbm_traemp as b on (b.co_tra=a.co_tra and b.st_reg='A')  " + "\n";
                        strSQLMarInc+="where a.st_reg='A') as e  " + "\n";
                        strSQLMarInc+="where c.tx_usr=e.tx_usr " + "\n";
                        strSQLMarInc+="and e.tx_usr="+objUti.codificar(strUsr.toUpperCase()) +") " + "\n";
                        strSQLMarInc+="AND NOT (EXTRACT(DOW FROM a.fe_dia ) in (6,7))" + "\n";//David
                        strSQLMarInc+="GROUP BY c.co_emp, empresa, empleado, c.co_tra " + "\n";
                        strSQLMarInc+="order by ne_numMarInc desc ";
                    }
                 }
                
                System.out.println("atrasos: " + strSQL);
                System.out.println("faltas: " + strSQLFAL);
                System.out.println("marinc: " + strSQLMarInc);
                rst = stm.executeQuery(strSQL);


                        arlDat.clear();
                        while(rst.next()){  

                             arlReg=new ArrayList();
                                    arlReg.add(0, rst.getString("co_emp"));
                                    arlReg.add(1, rst.getString("empresa"));
                                    arlReg.add(2, rst.getString("fe_dia"));
                                    arlReg.add(3, rst.getString("co_tra"));
                                    arlReg.add(4, rst.getString("empleado"));
                                    arlReg.add(5, rst.getString("ho_ent").substring(0, 5));
                             arlDat.add(arlReg);  
                             intEst=1;

                        }
                      
                        arlDatFal.clear();
                        rst = stm.executeQuery(strSQLFAL);
                        
                        while(rst.next()){

                             arlRegFal=new ArrayList();
                                    arlRegFal.add(0, rst.getString("co_emp"));
                                    arlRegFal.add(1, rst.getString("empresa"));
                                    arlRegFal.add(2, rst.getString("co_tra"));
                                    arlRegFal.add(3, rst.getString("empleado"));
                                    arlRegFal.add(4, rst.getInt("ne_numFal"));
                             arlDatFal.add(arlRegFal);  
                             intEst=1;

                        }
                        
                        arlDatMarInc.clear();
                        rst = stm.executeQuery(strSQLMarInc);
                        
                        while(rst.next()){

                             arlRegMarInc=new ArrayList();
                                    arlRegMarInc.add(0, rst.getString("co_emp"));
                                    arlRegMarInc.add(1, rst.getString("empresa"));
                                    arlRegMarInc.add(2, rst.getString("co_tra"));
                                    arlRegMarInc.add(3, rst.getString("empleado"));
                                    arlRegMarInc.add(4, rst.getInt("ne_numMarInc"));
                             arlDatMarInc.add(arlRegMarInc);  
                             intEst=1;

                        }
                        
                        rst.close();
                        stm.close();
                        conn.close();
                        rst=null;
                        stm=null;
                        conn=null;

                           if(intEst==1){
                            ZafConRecHumNotAtr_01 obj1 = new  ZafConRecHumNotAtr_01( parent2, false, arlDat , arlDatFal , arlDatMarInc , objParSis, dskGen, strFecDes, strFecHas, strFecDes, strFecHas );
                            obj1.show();
                            intEst=0;
                           }
                         sleep( 10000000 );

               }
            }
            catch (InterruptedException e)   //InterruptedException
            {
                System.out.println("Excepción: " + e.toString());
            }
             catch(SQLException  Evt){ Evt.printStackTrace();   }
            catch(Exception  Evt){ Evt.printStackTrace();   }
        }
    }
}