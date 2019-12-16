/*
 *  
 * v. 1.02
 * 
 */ 
package Librerias.ZafConRecHum.ZafConRecHumNotVenCon;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.awt.Frame;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JProgressBar;
/**
 * Clase nueva para la notificación de empleados que se les vence el contrato.
 * @author Roberto Flores
 * Guayaquil 05/03/2013
 */
public class ZafConRecHumNotVenCon extends javax.swing.JPanel
{
    private ZafParSis objParSis;
    private JProgressBar pgrConMem;
    private JButton butGarCol;
   
    private ArrayList arlDat, arlReg;
     private ZafUtil objUti;
     
    java.awt.Frame parent2;
    javax.swing.JDesktopPane dskGen;

    private int intCodUsr=0;
    
    /** Creates a new instance of ZafConMem */
    public ZafConRecHumNotVenCon(ZafParSis obj, Frame parent ) {
        try{ 
            objParSis=obj;        
            parent2=parent;
            objUti = new ZafUtil();
        }catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }    
    }
        
    public ZafConRecHumNotVenCon(ZafParSis obj , java.awt.Frame parent , javax.swing.JDesktopPane dskGe)
    {
      try{ 
            //Configurar el JPanel.      ZafConRecHum
            objParSis=obj;

            parent2=parent;
            dskGen=dskGe;
            this.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
            this.setLayout(new java.awt.BorderLayout());
            this.setPreferredSize(new java.awt.Dimension(120, 60));

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

            String strTxUsr = objParSis.getNombreUsuario();

            cargarListadoEmpNotVenCon(objParSis.getNombreUsuario().toUpperCase());

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

    private void cargarListadoEmpNotVenCon(String strUsr)
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

                    String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                    String strFec[] = strFecSis.split(" ");
                    String strFecFin[] = strFec[0].split("-");
                    String strFecDes = strFecFin[0] + "-" + strFecFin[1] + "-" + strFecFin[2];

                   java.util.Calendar CalValPru =  java.util.Calendar.getInstance();
                   CalValPru.add(Calendar.MONTH, +1);
                   String strFecHasPru = CalValPru.get(Calendar.YEAR) + "-" + CalValPru.get(Calendar.MONTH) + "-" + CalValPru.get(Calendar.DATE);
                   
                   java.util.Calendar CalValAño =  java.util.Calendar.getInstance();
                   CalValAño.add(Calendar.MONTH, +2);
                   String strFecHasAño = CalValAño.get(Calendar.YEAR) + "-" + CalValAño.get(Calendar.MONTH) + "-" + CalValAño.get(Calendar.DATE);

                   if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {


                        if(objParSis.getCodigoUsuario()==1){

                            if(objParSis.getDireccionIP().compareTo("172.16.1.62")==0){
                                strSQL+=" select b.co_emp, c.tx_nom as empresa, b.co_tra, a.tx_ide,(a.tx_ape || ' ' ||a.tx_nom) as empleado, b.fe_inicon, ";
                                strSQL+=" b.fe_finperprucon, b.fe_fincon, b.st_perprucon, b.st_fincon, b.fe_reafincon, ";
                                strSQL+= " case (b.st_perprucon is null) when true then 'Prueba' else '1 año' end as strTipCon";
                                strSQL+=" from tbm_tra a";
                                strSQL+=" inner join tbm_traemp b on (b.co_tra=a.co_tra)";
                                strSQL+=" inner join tbm_emp c on (c.co_emp=b.co_emp)";
                                strSQL+=" where b.st_reg='A'";
                                strSQL+=" and (b.fe_finperprucon between "+objUti.codificar(strFecDes) +" and "+objUti.codificar(strFecHasPru) +" OR b.fe_finperprucon IS NULL)";
                                strSQL+=" and b.co_tra not in (4,30,41,42)";
                                strSQL+=" UNION";
                                strSQL+=" select b.co_emp, c.tx_nom as empresa, b.co_tra, a.tx_ide,(a.tx_ape || ' ' ||a.tx_nom) as empleado, b.fe_inicon, ";
                                strSQL+=" b.fe_finperprucon, b.fe_fincon, b.st_perprucon, b.st_fincon, b.fe_reafincon, ";
                                strSQL+= " case (b.st_perprucon is null) when true then 'Prueba' else '1 año' end as strTipCon";
                                strSQL+=" from tbm_tra a";
                                strSQL+=" inner join tbm_traemp b on (b.co_tra=a.co_tra)";
                                strSQL+=" inner join tbm_emp c on (c.co_emp=b.co_emp)";
                                strSQL+=" where b.st_reg='A'";
                                strSQL+=" and (b.st_perprucon='S')";
                                strSQL+=" and (b.fe_fincon between "+objUti.codificar(strFecDes) +" and "+objUti.codificar(strFecHasAño) +")";
                                strSQL+=" and b.co_tra not in (4,30,41,42)";
                                strSQL+=" order by co_emp, empleado";
                            }
                        }else if (objParSis.getCodigoUsuario()==166 || objParSis.getCodigoUsuario()==172 || objParSis.getCodigoUsuario()==207 || objParSis.getCodigoUsuario()==220
                                || objParSis.getCodigoUsuario()==225){
                                strSQL+=" select b.co_emp, c.tx_nom as empresa, b.co_tra, a.tx_ide,(a.tx_ape || ' ' ||a.tx_nom) as empleado, b.fe_inicon, ";
                                strSQL+=" b.fe_finperprucon, b.fe_fincon, b.st_perprucon, b.st_fincon, b.fe_reafincon, ";
                                strSQL+= " case (b.st_perprucon is null) when true then 'Prueba' else '1 año' end as strTipCon";
                                strSQL+=" from tbm_tra a";
                                strSQL+=" inner join tbm_traemp b on (b.co_tra=a.co_tra)";
                                strSQL+=" inner join tbm_emp c on (c.co_emp=b.co_emp)";
                                strSQL+=" where b.st_reg='A'";
                                strSQL+=" and (b.fe_finperprucon between "+objUti.codificar(strFecDes) +" and "+objUti.codificar(strFecHasPru) +" OR b.fe_finperprucon IS NULL)";
                                strSQL+=" and b.co_tra not in (4,30,41,42)";
                                strSQL+=" UNION";
                                strSQL+=" select b.co_emp, c.tx_nom as empresa, b.co_tra, a.tx_ide,(a.tx_ape || ' ' ||a.tx_nom) as empleado, b.fe_inicon, ";
                                strSQL+=" b.fe_finperprucon, b.fe_fincon, b.st_perprucon, b.st_fincon, b.fe_reafincon, ";
                                strSQL+= " case (b.st_perprucon is null) when true then 'Prueba' else '1 año' end as strTipCon";
                                strSQL+=" from tbm_tra a";
                                strSQL+=" inner join tbm_traemp b on (b.co_tra=a.co_tra)";
                                strSQL+=" inner join tbm_emp c on (c.co_emp=b.co_emp)";
                                strSQL+=" where b.st_reg='A'";
                                strSQL+=" and (b.st_perprucon='S')";
                                strSQL+=" and (b.fe_fincon between "+objUti.codificar(strFecDes) +" and "+objUti.codificar(strFecHasAño) +")";
                                strSQL+=" and b.co_tra not in (4,30,41,42)";
                                strSQL+=" order by co_emp, empleado";

                        }
                        else{
                                strSQL+=" select b.co_emp, c.tx_nom as empresa, b.co_tra, a.tx_ide,(a.tx_ape || ' ' ||a.tx_nom) as empleado, b.fe_inicon, ";
                                strSQL+=" b.fe_finperprucon, b.fe_fincon, b.st_perprucon, b.st_fincon, b.fe_reafincon, ";
                                strSQL+= " case (b.st_perprucon is null) when true then 'Prueba' else '1 año' end as strTipCon";
                                strSQL+=" from tbm_tra a";
                                strSQL+=" inner join tbm_traemp b on (b.co_tra=a.co_tra)";
                                strSQL+=" inner join tbm_emp c on (c.co_emp=b.co_emp)";
                                strSQL+=" where b.st_reg='A'";
                                strSQL+=" and (b.fe_finperprucon between "+objUti.codificar(strFecDes) +" and "+objUti.codificar(strFecHasPru) +" OR b.fe_finperprucon IS NULL)";
                                strSQL+=" and b.co_tra not in (4,30,41,42)";
                                strSQL+=" and b.co_jef in (";
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
                                strSQL+=" UNION";
                                strSQL+=" select b.co_emp, c.tx_nom as empresa, b.co_tra, a.tx_ide,(a.tx_ape || ' ' ||a.tx_nom) as empleado, b.fe_inicon, ";
                                strSQL+=" b.fe_finperprucon, b.fe_fincon, b.st_perprucon, b.st_fincon, b.fe_reafincon, ";
                                strSQL+= " case (b.st_perprucon is null) when true then 'Prueba' else '1 año' end as strTipCon";
                                strSQL+=" from tbm_tra a";
                                strSQL+=" inner join tbm_traemp b on (b.co_tra=a.co_tra)";
                                strSQL+=" inner join tbm_emp c on (c.co_emp=b.co_emp)";
                                strSQL+=" where b.st_reg='A'";
                                strSQL+=" and (b.st_perprucon='S')";
                                strSQL+=" and (b.fe_fincon between "+objUti.codificar(strFecDes) +" and "+objUti.codificar(strFecHasAño) +")";
                                strSQL+=" and b.co_tra not in (4,30,41,42)";
                                strSQL+=" and b.co_jef in (";
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
                                strSQL+=" order by co_emp, empleado";
                        }
                     }else{
                        if(objParSis.getCodigoUsuario()==1){
                            if(objParSis.getDireccionIP().compareTo("172.16.1.62")==0){
                                strSQL+=" select b.co_emp, c.tx_nom as empresa, b.co_tra, a.tx_ide,(a.tx_ape || ' ' ||a.tx_nom) as empleado, b.fe_inicon, ";
                                strSQL+=" b.fe_finperprucon, b.fe_fincon, b.st_perprucon, b.st_fincon, b.fe_reafincon, ";
                                strSQL+= " case (b.st_perprucon is null) when true then 'Prueba' else '1 año' end as strTipCon";
                                strSQL+=" from tbm_tra a";
                                strSQL+=" inner join tbm_traemp b on (b.co_tra=a.co_tra)";
                                strSQL+=" inner join tbm_emp c on (c.co_emp=b.co_emp)";
                                strSQL+=" where b.st_reg='A'";
                                strSQL+=" and (b.fe_finperprucon between "+objUti.codificar(strFecDes) +" and "+objUti.codificar(strFecHasPru) +" OR b.fe_finperprucon IS NULL)";
                                strSQL+=" and b.co_emp="+objParSis.getCodigoEmpresa();
                                strSQL+=" and b.co_tra not in (4,30,41,42)";
                                strSQL+=" UNION";
                                strSQL+=" select b.co_emp, c.tx_nom as empresa, b.co_tra, a.tx_ide,(a.tx_ape || ' ' ||a.tx_nom) as empleado, b.fe_inicon, ";
                                strSQL+=" b.fe_finperprucon, b.fe_fincon, b.st_perprucon, b.st_fincon, b.fe_reafincon, ";
                                strSQL+= " case (b.st_perprucon is null) when true then 'Prueba' else '1 año' end as strTipCon";
                                strSQL+=" from tbm_tra a";
                                strSQL+=" inner join tbm_traemp b on (b.co_tra=a.co_tra)";
                                strSQL+=" inner join tbm_emp c on (c.co_emp=b.co_emp)";
                                strSQL+=" where b.st_reg='A'";
                                strSQL+=" and (b.st_perprucon='S')";
                                strSQL+=" and (b.fe_fincon between "+objUti.codificar(strFecDes) +" and "+objUti.codificar(strFecHasAño) +")";
                                strSQL+=" and b.co_emp="+objParSis.getCodigoEmpresa();
                                strSQL+=" and b.co_tra not in (4,30,41,42)";
                                strSQL+=" order by co_emp, empleado";
                            }
                        }else if (objParSis.getCodigoUsuario()==166 || objParSis.getCodigoUsuario()==172 || objParSis.getCodigoUsuario()==207 || objParSis.getCodigoUsuario()==220
                                || objParSis.getCodigoUsuario()==225){
                                strSQL+=" select b.co_emp, c.tx_nom as empresa, b.co_tra, a.tx_ide,(a.tx_ape || ' ' ||a.tx_nom) as empleado, b.fe_inicon, ";
                                strSQL+=" b.fe_finperprucon, b.fe_fincon, b.st_perprucon, b.st_fincon, b.fe_reafincon, ";
                                strSQL+= " case (b.st_perprucon is null) when true then 'Prueba' else '1 año' end as strTipCon";
                                strSQL+=" from tbm_tra a";
                                strSQL+=" inner join tbm_traemp b on (b.co_tra=a.co_tra)";
                                strSQL+=" inner join tbm_emp c on (c.co_emp=b.co_emp)";
                                strSQL+=" where b.st_reg='A'";
                                strSQL+=" and (b.fe_finperprucon between "+objUti.codificar(strFecDes) +" and "+objUti.codificar(strFecHasPru) +" OR b.fe_finperprucon IS NULL)";
                                strSQL+=" and b.co_emp="+objParSis.getCodigoEmpresa();
                                strSQL+=" and b.co_tra not in (4,30,41,42)";
                                strSQL+=" UNION";
                                strSQL+=" select b.co_emp, c.tx_nom as empresa, b.co_tra, a.tx_ide,(a.tx_ape || ' ' ||a.tx_nom) as empleado, b.fe_inicon, ";
                                strSQL+=" b.fe_finperprucon, b.fe_fincon, b.st_perprucon, b.st_fincon, b.fe_reafincon, ";
                                strSQL+= " case (b.st_perprucon is null) when true then 'Prueba' else '1 año' end as strTipCon";
                                strSQL+=" from tbm_tra a";
                                strSQL+=" inner join tbm_traemp b on (b.co_tra=a.co_tra)";
                                strSQL+=" inner join tbm_emp c on (c.co_emp=b.co_emp)";
                                strSQL+=" where b.st_reg='A'";
                                strSQL+=" and (b.st_perprucon='S')";
                                strSQL+=" and (b.fe_fincon between "+objUti.codificar(strFecDes) +" and "+objUti.codificar(strFecHasAño) +")";
                                strSQL+=" and b.co_emp="+objParSis.getCodigoEmpresa();
                                strSQL+=" and b.co_tra not in (4,30,41,42)";
                                strSQL+=" order by co_emp, empleado";
                        }
                        else{

                            strSQL+=" select b.co_emp, c.tx_nom as empresa, b.co_tra, a.tx_ide,(a.tx_ape || ' ' ||a.tx_nom) as empleado, b.fe_inicon, ";
                            strSQL+=" b.fe_finperprucon, b.fe_fincon, b.st_perprucon, b.st_fincon, b.fe_reafincon, ";
                            strSQL+= " case (b.st_perprucon is null) when true then 'Prueba' else '1 año' end as strTipCon";
                            strSQL+=" from tbm_tra a";
                            strSQL+=" inner join tbm_traemp b on (b.co_tra=a.co_tra)";
                            strSQL+=" inner join tbm_emp c on (c.co_emp=b.co_emp)";
                            strSQL+=" where b.st_reg='A'";
                            strSQL+=" and (b.fe_finperprucon between "+objUti.codificar(strFecDes) +" and "+objUti.codificar(strFecHasPru) +" OR b.fe_finperprucon IS NULL)";
                            strSQL+=" and b.co_emp="+objParSis.getCodigoEmpresa();
                            strSQL+=" and b.co_tra not in (4,30,41,42)";
                            strSQL+=" and b.co_jef in (";
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
                            strSQL+=" UNION";
                            strSQL+=" select b.co_emp, c.tx_nom as empresa, b.co_tra, a.tx_ide,(a.tx_ape || ' ' ||a.tx_nom) as empleado, b.fe_inicon, ";
                            strSQL+=" b.fe_finperprucon, b.fe_fincon, b.st_perprucon, b.st_fincon, b.fe_reafincon, ";
                            strSQL+= " case (b.st_perprucon is null) when true then 'Prueba' else '1 año' end as strTipCon";
                            strSQL+=" from tbm_tra a";
                            strSQL+=" inner join tbm_traemp b on (b.co_tra=a.co_tra)";
                            strSQL+=" inner join tbm_emp c on (c.co_emp=b.co_emp)";
                            strSQL+=" where b.st_reg='A'";
                            strSQL+=" and (b.st_perprucon='S')";
                            strSQL+=" and (b.fe_fincon between "+objUti.codificar(strFecDes) +" and "+objUti.codificar(strFecHasAño) +")";
                            strSQL+=" and b.co_emp="+objParSis.getCodigoEmpresa();
                            strSQL+=" and b.co_tra not in (4,30,41,42)";
                            strSQL+=" and b.co_jef in (";
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
                            strSQL+=" order by co_emp, empleado";

                        }
                     }  

                    java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());          
                    stm = conn.createStatement();
                    rst = stm.executeQuery(strSQL);

                    arlDat.clear();
                    while(rst.next()){  

                        arlReg=new ArrayList();
                        arlReg.add(0, rst.getString("co_emp"));
                        arlReg.add(1, rst.getString("empresa"));
                        arlReg.add(2, rst.getString("co_tra"));
                        arlReg.add(3, rst.getString("empleado"));
                        arlReg.add(4, rst.getString("strTipCon"));
    //                                    arlReg.add(5, rst.getString("fe_finperprucon")== null ? "" : rst.getString("fe_finperprucon").substring(0, 10).toString());
                        String strFeFinPerPruCon=rst.getString("fe_finperprucon");
                        arlReg.add(5, strFeFinPerPruCon);
                        arlReg.add(6, "...");
                        arlDat.add(arlReg);  
                        intEst=1;
                    }

                    rst.close();
                    stm.close();
                    conn.close();
                    rst=null;
                    stm=null;
                    conn=null;

                    if(intEst==1){
                        ZafConRecHumNotVenCon_01 obj1 = new  ZafConRecHumNotVenCon_01( parent2, false, arlDat ,objParSis, dskGen );
                        obj1.show();
                        intEst=0;
                    }
                    sleep( 10000000 );
       }
    }catch(org.postgresql.util.PSQLException ePSQL){}
    catch (InterruptedException e)   //InterruptedException
    {
        System.out.println("Excepción: " + e.toString());
    }
     catch(SQLException  Evt){ Evt.printStackTrace();   }
        }
    }
}