/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
     
package Librerias.ZafInventario;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
      
/**     
 *
 * @author jayapata   Mail: jaya_gar@hotmail.com     
 * 
 * @v v3.3: (Ingrid Lino - 19/Nov/2015)
 *    v3.4: (José Marín - 4/Ene/2016)
 *    v3.4.2: (José Marín - 8/Ene/2016)
 */ 
public class ZafInvItm {
   private ZafParSis objZafParSis;
   private ZafUtil objUti;
   private JInternalFrame jIntfra=null;
   private JDialog jDialo=null;
   int intTipConf= 2;    // variable de configuracion 1=: Trabaja con Grupo  2=: Trabaja con Empresa.
   StringBuffer stbinvemp; //VARIABLE TIPO BUFFER
   StringBuffer stbinvgrp; //VARIABLE TIPO BUFFER
   StringBuffer stbConStkInvEmp; //VARIABLE TIPO BUFFER Control de Stock Inventario Empresa
   StringBuffer stbConStkInvGrp; //VARIABLE TIPO BUFFER Control de Stock Inventario Grupo
   int intIndUni=0;  
   int INTCODEMPGRP=0;
   double dblCanIngFisBod=0;
   double dblCanEgrFisBod=0;
   private String strVersion="ZafInvItm v3.4.6";
 
      /** Creates a new instance of ZafInv */
    public ZafInvItm(javax.swing.JInternalFrame jfrthis, Librerias.ZafParSis.ZafParSis obj){
      try{  
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jIntfra = jfrthis;
            objUti = new Librerias.ZafUtil.ZafUtil();
            INTCODEMPGRP=objZafParSis.getCodigoEmpresaGrupo();
            System.out.println(strVersion);
        }catch (CloneNotSupportedException e){ objUti.mostrarMsgErr_F1(jfrthis, e);  }        
    }
 
    public ZafInvItm(javax.swing.JDialog jDial, Librerias.ZafParSis.ZafParSis obj){
       try{  
           this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jDialo = jDial;
            objUti = new Librerias.ZafUtil.ZafUtil();
            INTCODEMPGRP=objZafParSis.getCodigoEmpresaGrupo();
            System.out.println(strVersion);
        }catch (CloneNotSupportedException e){ objUti.mostrarMsgErr_F1(jDial, e);  }        
    } 
    
    public ZafInvItm( Librerias.ZafParSis.ZafParSis obj){
       try{  
           this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            objUti = new Librerias.ZafUtil.ZafUtil();
            INTCODEMPGRP=objZafParSis.getCodigoEmpresaGrupo();
        }catch (CloneNotSupportedException e){
            System.err.println("ERROR!!!!!!!!! "+e.toString() );
             
         } 
    }
      
    
 /**
  * Función que se encarga de obtener el Query de inventario que se manejara en el programa de ventas.
  * 
  * @return el sql cargado.
  */   
 public String getSqlInvVen(){
     boolean blnIsCosenco=false,blnIsEcuatosa=false,blnIsDetopacio=false;
     //Saber si la empresa que ingreso es COSENCO
     blnIsCosenco = (objZafParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO") > -1)?true:false;
     blnIsEcuatosa = (objZafParSis.getNombreEmpresa().toUpperCase().indexOf("ECUATOSA") > -1)?true:false;
     blnIsDetopacio = (objZafParSis.getNombreEmpresa().toUpperCase().indexOf("DETOPACIO") > -1)?true:false;
//     System.out.println("---------------------------->>>>>>>>>>>>>>>>"  + objZafParSis.getNombreEmpresa());
//     System.out.println("blnIsCosenco" + blnIsCosenco);
//     System.out.println("blnIsEcuatosa" + blnIsEcuatosa);
//     System.out.println("blnIsDetopacio" + blnIsDetopacio);
     String strSQL="";   
       //José Marín 31/Oct/2014 
        String strAux = ",CASE " +
        "WHEN ( (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
        " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
        " AND co_tipdoc IN (1,228) AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='C' " +
        " ))) THEN 'S' " +
        "" +
        " WHEN ( (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
        " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
        " AND co_tipdoc IN (1,228) AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='I' " +
        " ))) THEN 'I' " +
        " ELSE 'N' END  as isterL ";            
        //José Marín 31/Oct/2014
        String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1)) IN ( " +
        " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
        " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
        " THEN 'S' ELSE 'N' END AS proconf  ";
       if(intTipConf==1){ 
          if(objZafParSis.getCodigoEmpresa()==2 || objZafParSis.getCodigoEmpresa()==4){
                strSQL="SELECT a7.tx_codAlt, a7.co_itm, a7.tx_nomItm, a7.nd_stkAct,  a7.nd_preVta1, a7.st_ivaVen,a7.tx_descor,a7.tx_codAlt2,a7.st_ser";
                strSQL+=strAux;
                strSQL+=" , case when (trim(SUBSTR (UPPER(a7.tx_codAlt), length(a7.tx_codAlt) ,1)) = 'S' ) then 0 else   a7.nd_stkActcen  end as nd_stkActcen  , a7.nd_maruti, a7.st_permodnomitmven, a7.nd_pesitmkgr  ";
                strSQL+=strAux2;
                strSQL+=" FROM (";
                strSQL+=" SELECT a2.tx_codAlt, a2.co_itm, a2.tx_nomItm, a1.nd_stkAct, a1.nd_stkAct AS nd_stkTot, a2.nd_preVta1, a2.st_ivaVen,a2.tx_descor,a2.tx_codAlt2,a2.st_ser , a2.nd_maruti, a2.st_permodnomitmven,   d3.nd_stkActcen, a2.nd_pesitmkgr ";
                strSQL+=" FROM (";
                strSQL+=" SELECT b1.co_itmMae, SUM(b2.nd_stkAct) AS nd_stkAct";
                strSQL+=" FROM tbm_equInv AS b1";
                strSQL+=" INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
                strSQL+=" INNER JOIN tbr_bodEmp AS b3 ON (b2.co_emp=b3.co_empPer AND b2.co_bod=b3.co_bodPer)";
                strSQL+=" WHERE b3.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL+=" AND b3.co_loc=" + objZafParSis.getCodigoLocal();
                strSQL+=" GROUP BY b1.co_itmMae";
                strSQL+=" ) AS a1 " +
                "" +
                " LEFT OUTER JOIN ( SELECT c1.co_itmMae, SUM(c2.nd_stkAct) AS nd_stkActcen FROM tbm_equInv AS c1 " +
                " INNER JOIN tbm_invBod AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm) " +
                " INNER JOIN tbr_bodEmp AS c3 ON (c2.co_emp=c3.co_empPer AND c2.co_bod=c3.co_bodPer) " +
                " WHERE c3.co_emp=1 AND c3.co_loc=1 GROUP BY c1.co_itmMae " +
                " ) AS d3 ON (a1.co_itmMae=d3.co_itmMae) " +
                ", (";
                strSQL+=" SELECT c2.co_itmMae, c1.co_itm, c1.tx_codAlt, c1.tx_nomItm, c1.nd_preVta1, c1.st_ivaVen,var.tx_descor,c1.tx_codAlt2,c1.st_ser , c1.nd_maruti, c1.st_permodnomitmven , c1.nd_pesitmkgr  ";
                strSQL+=" FROM tbm_inv AS c1";
                strSQL+=" left outer join tbm_var as var on (c1.co_uni=var.co_reg and var.co_grp=5 )  ";
                strSQL+=" INNER JOIN tbm_equInv AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
                strSQL+=" WHERE c1.st_reg not in ('T','I','E','U') and  c2.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL+=" ) AS a2";
                strSQL+=" WHERE a1.co_itmMae=a2.co_itmMae";
                strSQL+=" ) AS a7  order by a7.tx_codalt";
            }else{
                strSQL="SELECT a7.tx_codAlt, a7.co_itm, a7.tx_nomItm, a7.nd_stkAct,  a7.nd_preVta1, a7.st_ivaVen,a7.tx_descor,a7.tx_codAlt2,a7.st_ser, a7.nd_maruti, a7.st_permodnomitmven, a7.nd_pesitmkgr ";
                strSQL+=strAux;
                strSQL+=", 0 as nd_stkActcen ";
                strSQL+=strAux2;
                strSQL+=" FROM (";
                strSQL+=" SELECT a2.tx_codAlt, a2.co_itm, a2.tx_nomItm, a1.nd_stkAct, a1.nd_stkAct AS nd_stkTot, a2.nd_preVta1, a2.st_ivaVen,a2.tx_descor,a2.tx_codAlt2,a2.st_ser, a2.nd_maruti, a2.st_permodnomitmven, a2.nd_pesitmkgr  ";
                strSQL+=" FROM (";
                strSQL+=" SELECT b1.co_itmMae, SUM(b2.nd_stkAct) AS nd_stkAct";
                strSQL+=" FROM tbm_equInv AS b1";
                strSQL+=" INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
                strSQL+=" INNER JOIN tbr_bodEmp AS b3 ON (b2.co_emp=b3.co_empPer AND b2.co_bod=b3.co_bodPer)";
                strSQL+=" WHERE b3.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL+=" AND b3.co_loc=" + objZafParSis.getCodigoLocal();
                strSQL+=" GROUP BY b1.co_itmMae";
                strSQL+=" ) AS a1 " +
                ", (";
                strSQL+=" SELECT c2.co_itmMae, c1.co_itm, c1.tx_codAlt, c1.tx_nomItm, c1.nd_preVta1, c1.st_ivaVen,var.tx_descor,c1.tx_codAlt2,c1.st_ser , c1.nd_maruti, c1.st_permodnomitmven, c1.nd_pesitmkgr   ";
                strSQL+=" FROM tbm_inv AS c1";
                strSQL+=" left outer join tbm_var as var on (c1.co_uni=var.co_reg and var.co_grp=5 )  ";
                strSQL+=" INNER JOIN tbm_equInv AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
                strSQL+=" WHERE c1.st_reg not in ('T','I','E','U') and  c2.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL+=" ) AS a2";
                strSQL+=" WHERE a1.co_itmMae=a2.co_itmMae";
                strSQL+=" ) AS a7  order by a7.tx_codalt";
            }
          
       }    
       if(intTipConf==2){ 
           strSQL="SELECT a.tx_codalt, a.co_itm, a.tx_nomitm, a5.nd_stkact,ROUND(a.nd_prevta1,2) as nd_preVta1, a.st_ivaven, a1.tx_descor, \n";
           //strSQL+="      a.tx_codalt as tx_codalt2, a.st_ser, a.nd_maruti, a.st_permodnomitmven, a.nd_pesitmkgr \n";
		   strSQL+="      a.tx_codalt as tx_codalt2, a.st_ser, a.nd_maruti, a.st_permodnomitmven, case when a.nd_pesitmkgr is null then 0 else ROUND(a.nd_pesitmkgr,2) end as nd_pesItmKgr  \n";
           strSQL+=strAux;
           strSQL+=", 0 as nd_stkActcen \n";
           strSQL+=strAux2;
           strSQL+="    ,a1.tx_tipunimed  , a.st_blqprevta, a2.nd_caningbod, a.co_ctaegr \n" ;
           if(blnIsCosenco || blnIsEcuatosa ||blnIsDetopacio){  // COSENCO
                
                strSQL+=", '' as nd_stkActInm, '' as nd_canDisInm \n"; 
           }
           else{
                 strSQL+=", ROUND(a3.nd_stkact,2) as nd_stkActInm, ROUND(a3.nd_canDis,2) as nd_canDisInm  \n"; //
           }
//           strSQL+=" ,CASE WHEN a2.st_impOrd='S'  THEN a.tx_codAlt2 ELSE '' END as tx_codAlt3Letras \n";  /* JoséMario 4/Ene/2016 */
           strSQL+=" ,  a.tx_codAlt2 as tx_codAlt3Letras \n"; /* JM 5/Dic/2017 */
           strSQL+=" ,  ROUND(a5.nd_canDis,2) as nd_canDis\n"; /* JoseMario 12/May/2016 */
           strSQL+=" FROM tbm_inv AS a  \n";
           strSQL+=" LEFT JOIN tbm_var AS a1 ON(a1.co_reg=a.co_uni and a1.co_grp=5 ) \n" ;
           strSQL+=" INNER JOIN tbm_invbod AS a2 ON(a2.co_emp = a.co_emp AND a2.co_itm=a.co_itm \n" ;
           strSQL+=" AND a2.co_bod = " ;
           strSQL+=" ( select co_bod from tbr_bodloc where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+"  " ;
           strSQL+=" and st_reg='P'  )  )";
           strSQL+=" INNER JOIN tbm_equInv as a4 ON (a.co_emp=a4.co_emp AND a.co_itm=a4.co_itm)";
           strSQL+=" INNER JOIN (";//JoseMario 6/Agosto/2015
           strSQL+=" 	SELECT DISTINCT a2.co_itmMae, SUM(a1.nd_stkAct) AS nd_stkAct, SUM(a1.nd_canDis) AS nd_canDis ";//JoseMario 6/Agosto/2015
           strSQL+=" 	FROM tbm_invBod AS a1	";//JoseMario 6/Agosto/2015
           strSQL+=" 	INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";//JoseMario 6/Agosto/2015
           strSQL+=" 	INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod) ";//JoseMario 6/Agosto/2015
           strSQL+=" 	INNER JOIN tbr_bodEmpBodGrp as a4 ON (a3.co_empGrp=a4.co_empGrp AND a3.co_bodGrp=a4.co_bodGrp)";//JoseMario 6/Agosto/2015
           strSQL+=" 	INNER JOIN tbr_bodLoc as a5 ON (a4.co_emp=a5.co_emp AND a4.co_bod=a5.co_bod)";//JoseMario 6/Agosto/2015
           strSQL+=" 	WHERE a5.co_emp = "+objZafParSis.getCodigoEmpresa()+" AND ";//JoseMario 6/Agosto/2015
           strSQL+="      a5.co_loc="+objZafParSis.getCodigoLocal()+" and  a5.st_reg = 'P' ";//JoseMario 6/Agosto/2015
           strSQL+="	GROUP BY a2.co_itmMae";//JoseMario 6/Agosto/2015
           strSQL+=") as a5 ON (a4.co_itmMae=a5.co_itmMae)";//JoseMario 6/Agosto/2015
           if(blnIsCosenco || blnIsEcuatosa || blnIsDetopacio){
                System.out.println("-_-");
           }
           else{
               strSQL+=" INNER JOIN (";  //JoseMario 6/Agosto/2015
                strSQL+=" 	SELECT a2.co_itmMae, SUM(a1.nd_stkAct) AS nd_stkAct, SUM(a1.nd_canDis) AS nd_canDis"; 
                strSQL+=" 	FROM tbm_invBod AS a1"; 
                strSQL+=" 	INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)"; 
                strSQL+=" 	INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)"; 
                strSQL+=" 	WHERE a3.co_empGrp=0"; 
                strSQL+=" 	AND a3.co_bodGrp=15"; 
                strSQL+=" 	GROUP BY a2.co_itmMae"; 
                strSQL+=" ) as a3 ON (a4.co_itmMae=a3.co_itmMae)"; 
           }
           strSQL+=" WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" " ;
           strSQL+=" AND  a.st_reg not in ('T','I','E','U')        " ;
           strSQL+=" ORDER BY a.tx_codalt";
       }
       System.out.println("sssss ITEMS " +strSQL);
     return strSQL;
   } 
    
  
 /**
  * Función que se encarga de obtener el Query de inventario que se manejara en el programa de ordenes de compra.
  * 
  * @return el sql cargado.
  */        
 public String getSqlInvCom(){
     String strSQL="";   
     int co_tipdoc=2;
     String strAux = ",CASE WHEN (" +
     " (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
     " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
     " AND co_tipdoc="+co_tipdoc+" AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
     " ))) THEN 'S' ELSE 'N' END  as isterL";
       if(intTipConf==1){ 
            
            strSQL=" SELECT tx_codAlt, co_itm, tx_nomItm,  nd_stkAct, nd_cosUni, st_ivacom, tx_desCor, tx_codAlt2, st_ser \n" +
                    "FROM ( \n" +
                    "SELECT d1.tx_codAlt, d1.co_itm, d1.tx_nomItm,  d3.nd_stkAct, d2.nd_cosUni, d1.st_ivacom, d4.tx_desCor, d1.tx_codAlt2,d1.st_ser \n ";
            strSQL+=strAux;
            strSQL+=" FROM ( SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.co_uni, a1.st_ivacom,a1.st_ser";
            strSQL+=" FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
            strSQL+=" WHERE a1.st_reg not in('T','I','E','U') and  a1.co_emp="+objZafParSis.getCodigoEmpresa()+" ) AS d1";
            strSQL+=" INNER JOIN ( SELECT b2.co_itmMae, b1.nd_cosUni FROM tbm_inv AS b1";
            strSQL+=" INNER JOIN tbm_equInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
            strSQL+=" WHERE b1.co_emp="+objZafParSis.getCodigoEmpresaGrupo()+" ) AS d2 ON (d1.co_itmMae=d2.co_itmMae)";
            strSQL+=" INNER JOIN (SELECT c1.co_itmMae, SUM(c2.nd_stkAct) AS nd_stkAct";
            strSQL+=" FROM tbm_equInv AS c1 INNER JOIN tbm_invBod AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
            strSQL+=" INNER JOIN tbr_bodEmp AS c3 ON (c2.co_emp=c3.co_empPer AND c2.co_bod=c3.co_bodPer)";
            strSQL+=" WHERE c3.co_emp="+objZafParSis.getCodigoEmpresa()+" AND c3.co_loc="+objZafParSis.getCodigoLocal()+" GROUP BY c1.co_itmMae";
            strSQL+=" ) AS d3 ON (d1.co_itmMae=d3.co_itmMae)";
            strSQL+=" LEFT OUTER JOIN tbm_var AS d4 ON (d1.co_uni=d4.co_reg) " +
            " )  AS x WHERE " +
            " CASE WHEN (" +
            " SELECT COUNT(*) FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
            " AND co_tipdoc="+co_tipdoc+" AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
            " )>=1 THEN x.isterl='S' ELSE  x.isterl='N'  END " +
            " ORDER BY tx_codAlt";
       }    
       if(intTipConf==2){ 
           strSQL="SELECT tx_codAlt, co_itm, tx_nomItm,  nd_stkAct, nd_cosUni, st_ivacom, tx_desCor, tx_codAlt2, st_ser" +
           " FROM ( SELECT tx_codAlt, co_itm, tx_nomItm,  nd_stkAct, nd_cosUni, st_ivacom, tx_desCor, tx_codAlt2, st_ser , isterl  " +
           " FROM ( SELECT a.tx_codalt, a.co_itm, a.tx_nomitm, a2.nd_stkact, a.nd_cosuni, a.st_ivacom, a1.tx_descor, a.tx_codalt as tx_codalt2, a.st_ser  "+
           " "+strAux+" FROM tbm_inv AS a " +
           " LEFT JOIN tbm_var AS a1 ON(a1.co_reg=a.co_uni and a1.co_grp=5 ) " +
           " INNER JOIN tbm_invbod AS a2 ON(a2.co_emp = a.co_emp AND a2.co_itm=a.co_itm " +
           " AND a2.co_bod = " +
           " ( select co_bod from tbr_bodloc where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and st_reg='P' )  ) " +
           " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" " +
           " AND  a.st_reg not in ('T','I','E','U')   ) AS x WHERE  "+ // isterl='S'   " +
           " CASE WHEN (" +
            " SELECT COUNT(*) FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
            " AND co_tipdoc="+co_tipdoc+" AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
            " )>=1 THEN x.isterl='S' ELSE  x.isterl='N'  END " +
           " )  AS x  ORDER BY tx_codalt ";
       }
     //System.out.println(""+ strSQL );   
     return strSQL;
   } 
  
    
   
   
/**
  * Función que se encarga de obtener el Query de inventario que se manejara en el programa de ingreso y egreso de varios motivos.
  * 
  * @return el sql cargado.
  */          
 public String getSqlInvIngEgr(){
     String strSQL="";   
     int co_tipdoc=1; 
     
        String strAux = ",CASE WHEN (" +
        " (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
        " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
        " AND co_tipdoc="+co_tipdoc+" AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
        " ))) THEN 'S' ELSE 'N' END  as isterL";

         String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1)) IN ( " +
        " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
        " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
        " THEN 'S' ELSE 'N' END AS proconf  ";
             
       if(intTipConf==1){ 
             
            strSQL=" SELECT tx_codAlt, co_itm, tx_nomItm,  nd_stkAct, nd_cosUni, st_ivacom, tx_desCor, tx_codAlt2, st_ser " +
            " "+strAux2+" "+
            " FROM ( " +
            "        SELECT d1.tx_codAlt, d1.co_itm, d1.tx_nomItm,  d3.nd_stkAct, d2.nd_cosUni, d1.st_ivacom, d4.tx_desCor, d1.tx_codAlt2,d1.st_ser ";
            strSQL+=strAux;
            strSQL+=" FROM ( SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.co_uni, a1.st_ivacom,a1.st_ser";
            strSQL+=" FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
            strSQL+=" WHERE a1.st_reg not in('T','I','U') and  a1.co_emp="+objZafParSis.getCodigoEmpresa()+" ) AS d1";
            strSQL+=" INNER JOIN ( SELECT b2.co_itmMae, b1.nd_cosUni FROM tbm_inv AS b1";
            strSQL+=" INNER JOIN tbm_equInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
            strSQL+=" WHERE b1.co_emp="+objZafParSis.getCodigoEmpresaGrupo()+" ) AS d2 ON (d1.co_itmMae=d2.co_itmMae)";
            strSQL+=" INNER JOIN (SELECT c1.co_itmMae, SUM(c2.nd_stkAct) AS nd_stkAct";
            strSQL+=" FROM tbm_equInv AS c1 INNER JOIN tbm_invBod AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
            strSQL+=" INNER JOIN tbr_bodEmp AS c3 ON (c2.co_emp=c3.co_empPer AND c2.co_bod=c3.co_bodPer)";
            strSQL+=" WHERE c3.co_emp="+objZafParSis.getCodigoEmpresa()+" AND c3.co_loc="+objZafParSis.getCodigoLocal()+" GROUP BY c1.co_itmMae";
            strSQL+=" ) AS d3 ON (d1.co_itmMae=d3.co_itmMae)";
            strSQL+=" LEFT OUTER JOIN tbm_var AS d4 ON (d1.co_uni=d4.co_reg) " +
            " )  AS x WHERE " +
            " CASE WHEN (" +
            " SELECT COUNT(*) FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
            " AND co_tipdoc="+co_tipdoc+" AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
            " )>=1 THEN x.isterl='S' ELSE  x.isterl='N'  END " +
            " ORDER BY tx_codAlt";
       }    
       if(intTipConf==2){ 
           strSQL="SELECT a.tx_codalt, a.co_itm, a.tx_nomitm, a2.nd_stkact, a.nd_cosuni, a.st_ivacom, a1.tx_descor, a.tx_codalt as tx_codalt2, a.st_ser  "+
           " "+strAux2+" "+
           " FROM tbm_inv AS a " +
           " LEFT JOIN tbm_var AS a1 ON(a1.co_reg=a.co_uni and a1.co_grp=5 ) " +
           " INNER JOIN tbm_invbod AS a2 ON(a2.co_emp = a.co_emp AND a2.co_itm=a.co_itm " +
           " AND a2.co_bod = " +
           " ( select co_bod from tbr_bodloc where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and st_reg='P'  )  ) " +
           " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" " +
           " AND  a.st_reg not in ('T','I','E','U')        " +
           " ORDER BY a.tx_codalt";
       }
     //System.out.println(""+ strSQL );   
     return strSQL;
   } 
  
      
   
   /**
    *Esta función se encarga de inicializar las variable de tipo buffer para que sean adicionados todos los querys que se ejecutaran.
    */
   public void inicializaObjeto(){
        stbinvemp=new StringBuffer(); 
        stbinvgrp=new StringBuffer(); 
        stbConStkInvEmp=new StringBuffer();
        stbConStkInvGrp=new StringBuffer(); 
        intIndUni=0;
   }
  
   
   
     
   /**
    * Esta función se encarga de armar el Query y lo adiciona en las variables de tipo buffer. 
    * 
    * @param intCodEmp Código de empresa
    * @param intCodItm Código de item
    * @param intCodBod Código de bodega
    * @param dblCan    Cantidad  
    * @param intTipMov Tipo de movimiento sea esta aumenta o disminuye sea el caso
    * @param strEstFisBod Estado de si ingresa o no fisicamente a bodega
    * @param strMerIngEgr Estado si mercaderia ingresa o egresa fisicamente a bodega
    * @param strTipIngEgr Estado de tipo si es ingreso o egreso fisicamente a bodega
    * @param intTipMovFisBod Tipo de movimiento fisico de bodega
    */
   public void generaQueryStock(int intCodEmp, int intCodItm, int intCodBod, double dblCan, int intTipMov, String strEstFisBod, String strMerIngEgr, String strTipIngEgr, int intTipMovFisBod ){
    try{
        dblCanIngFisBod=0;
        dblCanEgrFisBod=0;
        if(strEstFisBod.equals("N")){
            if(strMerIngEgr.equals("S")){   
             if(strTipIngEgr.equals("I")) dblCanIngFisBod=dblCan*intTipMovFisBod;
             if(strTipIngEgr.equals("E")) dblCanEgrFisBod=dblCan*intTipMovFisBod;
         }}
        dblCan=dblCan*intTipMov;
        stbinvemp.append("\n/* JOTA */ UPDATE tbm_inv \n SET st_regrep='M', nd_stkAct=nd_stkAct+"+dblCan+" " +
        " \n WHERE co_emp="+intCodEmp+" AND co_itm="+intCodItm+"; ");
        stbinvemp.append("\n UPDATE tbm_invBod \n SET nd_stkAct=( CASE WHEN nd_stkAct IS NULL THEN 0 ELSE nd_stkAct END ) +"+dblCan+" " +
        ",\n nd_caningbod=( CASE WHEN nd_caningbod IS NULL THEN 0 ELSE nd_caningbod END )+"+dblCanIngFisBod+",\n nd_canegrbod=( CASE WHEN nd_canegrbod IS NULL THEN 0 ELSE nd_canegrbod END )+"+dblCanEgrFisBod+" "+
        "\n WHERE co_emp="+intCodEmp+" AND co_itm="+intCodItm+" AND co_bod="+intCodBod+"; ");

        if(intIndUni>0) stbConStkInvEmp.append("\n UNION ALL ");
        stbConStkInvEmp.append("\n SELECT co_emp, co_itm, nd_stkact \n FROM tbm_invbod AS a \n WHERE a.co_emp="+intCodEmp+" AND a.co_itm="+intCodItm+" AND a.co_bod="+intCodBod+" ");
                
       if(intTipConf==1){  
            stbinvgrp.append("\n UPDATE tbm_inv \n SET st_regrep='M', nd_stkAct=nd_stkAct+"+dblCan+" " +
            "\n WHERE  co_emp="+INTCODEMPGRP+" AND co_itm=" +
            "\n ( Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" And co_itmmae IN " +
            "\n  (Select co_itmmae from tbm_equinv where co_emp="+intCodEmp+" and co_itm="+intCodItm+")); "); 
            stbinvgrp.append("UPDATE tbm_invBod SET nd_stkAct=( CASE WHEN nd_stkAct IS NULL THEN 0 ELSE nd_stkAct END )+"+dblCan+" " +
            ",\n nd_caningbod=( CASE WHEN nd_caningbod IS NULL THEN 0 ELSE nd_caningbod END )+"+dblCanIngFisBod+",\n nd_canegrbod=( CASE WHEN nd_canegrbod IS NULL THEN 0 ELSE nd_canegrbod END )+"+dblCanEgrFisBod+" "+        
            "\n WHERE  co_emp="+INTCODEMPGRP+" AND co_itm=" +
            "\n ( Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" And co_itmmae IN " +
            " \n (Select co_itmmae from tbm_equinv where co_emp="+intCodEmp+" and co_itm="+intCodItm+")) " +
            "\n  AND co_bod=(select co_bodgrp from  tbr_bodempbodgrp where co_emp="+intCodEmp+" and co_bod="+intCodBod+"); "); 

            if(intIndUni>0) stbConStkInvGrp.append("\n UNION ALL ");
            stbConStkInvGrp.append("\n SELECT co_itm, nd_stkact \n FROM tbm_invBod AS a " +
            "\n WHERE co_emp="+INTCODEMPGRP+" AND co_itm=( Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" And co_itmmae IN " +
            "\n (Select co_itmmae from tbm_equinv where co_emp="+intCodEmp+" and co_itm="+intCodItm+" )) " +
            "\n AND co_bod=(select co_bodgrp from  tbr_bodempbodgrp where co_emp="+intCodEmp+" and co_bod="+intCodBod+" ) ");
       }
       System.out.println("\n\n ZafInvItm.generaQueryStock --- arma UPDATES (1) "  + stbinvemp.toString());
       System.out.println("\n\n ZafInvItm.generaQueryStock --- arma UPDATES (2) "  + stbConStkInvEmp.toString());
       System.out.println("\n\n ZafInvItm.generaQueryStock --- arma UPDATES (3) "  + stbinvgrp.toString());
       System.out.println("\n\n ZafInvItm.generaQueryStock --- arma UPDATES (4) "  + stbConStkInvGrp.toString());
     intIndUni=1;  
    }catch(Exception evt){  mostarErrorException(evt); }
   }
   


   /**
    * Esta función se encarga de armar el Query y lo adiciona en las variables de tipo buffer.
    *
    * @param intCodEmp Código de empresa
    * @param intCodItm Código de item
    * @param intCodBod Código de bodega
    * @param dblCan    Cantidad
    * @param intTipMov Tipo de movimiento sea esta aumenta o disminuye sea el caso
    * @param strEstFisBod Estado de si ingresa o no fisicamente a bodega
    * @param strMerIngEgr Estado si mercaderia ingresa o egresa fisicamente a bodega
    * @param strTipIngEgr Estado de tipo si es ingreso o egreso fisicamente a bodega
    * @param intTipMovFisBod Tipo de movimiento fisico de bodega
    * @param ndCanEgreFisBod  cantidad que egresa fisicamente de bodega.
    */
   public void generaQueryStock(int intCodEmp, int intCodItm, int intCodBod, double dblCan, int intTipMov, String strEstFisBod, String strMerIngEgr, String strTipIngEgr, int intTipMovFisBod, double ndCanEgreFisBod ){
    try{
        dblCanIngFisBod=0;
        dblCanEgrFisBod=0;

        if(strEstFisBod.equals("N")){
            if(strMerIngEgr.equals("S")){
                if(strTipIngEgr.equals("E")) {
                    dblCanEgrFisBod=ndCanEgreFisBod*intTipMovFisBod;
                }
            }
        }
        dblCan=dblCan*intTipMov;
        stbinvemp.append("UPDATE tbm_inv SET st_regrep='M', nd_stkAct=nd_stkAct+"+dblCan+" " +
        " WHERE co_emp="+intCodEmp+" AND co_itm="+intCodItm+"; ");
        stbinvemp.append("UPDATE tbm_invBod SET nd_stkAct=( CASE WHEN nd_stkAct IS NULL THEN 0 ELSE nd_stkAct END ) +"+dblCan+" " +
        ", nd_caningbod=( CASE WHEN nd_caningbod IS NULL THEN 0 ELSE nd_caningbod END )+"+dblCanIngFisBod+", nd_canegrbod=( CASE WHEN nd_canegrbod IS NULL THEN 0 ELSE nd_canegrbod END )+"+dblCanEgrFisBod+" "+
        " WHERE co_emp="+intCodEmp+" AND co_itm="+intCodItm+" AND co_bod="+intCodBod+"; ");
        
        System.out.println("ZafInvItm.generaQueryStock:stbinvemp  " + stbinvemp.toString());
        
        if(intIndUni>0) stbConStkInvEmp.append(" UNION ALL ");
        stbConStkInvEmp.append("SELECT co_emp, co_itm, nd_stkact FROM tbm_invbod AS a WHERE a.co_emp="+intCodEmp+" AND a.co_itm="+intCodItm+" AND a.co_bod="+intCodBod+" ");

        System.out.println("ZafInvItm.generaQueryStock:stbConStkInvEmp  " + stbConStkInvEmp.toString());
        
       if(intTipConf==1){
           System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n\n\nJOTA\n\n<<<<<<<<");
            stbinvgrp.append("UPDATE tbm_inv SET st_regrep='M', nd_stkAct=nd_stkAct+"+dblCan+" " +
            " WHERE  co_emp="+INTCODEMPGRP+" AND co_itm=" +
            " ( Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" And co_itmmae IN " +
            "  (Select co_itmmae from tbm_equinv where co_emp="+intCodEmp+" and co_itm="+intCodItm+")); ");
            stbinvgrp.append("UPDATE tbm_invBod SET nd_stkAct=( CASE WHEN nd_stkAct IS NULL THEN 0 ELSE nd_stkAct END )+"+dblCan+" " +
            ", nd_caningbod=( CASE WHEN nd_caningbod IS NULL THEN 0 ELSE nd_caningbod END )+"+dblCanIngFisBod+", nd_canegrbod=( CASE WHEN nd_canegrbod IS NULL THEN 0 ELSE nd_canegrbod END )+"+dblCanEgrFisBod+" "+
            " WHERE  co_emp="+INTCODEMPGRP+" AND co_itm=" +
            " ( Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" And co_itmmae IN " +
            "  (Select co_itmmae from tbm_equinv where co_emp="+intCodEmp+" and co_itm="+intCodItm+")) " +
            "  AND co_bod=(select co_bodgrp from  tbr_bodempbodgrp where co_emp="+intCodEmp+" and co_bod="+intCodBod+"); ");           
            System.out.println("ZafInvItm.generaQueryStock:stbinvgrp  " + stbinvgrp.toString());
            if(intIndUni>0) stbConStkInvGrp.append(" UNION ALL ");
            stbConStkInvGrp.append("SELECT co_itm, nd_stkact FROM tbm_invBod AS a " +
            " WHERE co_emp="+INTCODEMPGRP+" AND co_itm=( Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" And co_itmmae IN " +
            " (Select co_itmmae from tbm_equinv where co_emp="+intCodEmp+" and co_itm="+intCodItm+" )) " +
            " AND co_bod=(select co_bodgrp from  tbr_bodempbodgrp where co_emp="+intCodEmp+" and co_bod="+intCodBod+" ) ");
            System.out.println("ZafInvItm.generaQueryStock:stbConStkInvGrp  " + stbConStkInvGrp.toString());
       }
     intIndUni=1;
    }catch(Exception evt){  
        mostarErrorException(evt); 
    }
   }

   
   

private void mostarErrorException(Exception evt){
    if(jDialo==null)
        objUti.mostrarMsgErr_F1(jIntfra, evt);    
     else objUti.mostrarMsgErr_F1(jDialo, evt);  
}   
   
private void mostarErrorSqlException(java.sql.SQLException evt){
    if(jDialo==null)
        objUti.mostrarMsgErr_F1(jIntfra, evt);    
     else objUti.mostrarMsgErr_F1(jDialo, evt);  
}   
   


/**
 * Esta función se encarga de hacer le ejecución de la actualización de inventario.
 *  
 * @param conn Conexion de la base de datos
 * @param intActGrp si actualiza grupo o no 1=Si  0 = NO
 * @return True :Si la ejecución fue con exito.
 * @return False:Si la ejecución dio un error.
 */  
public boolean ejecutaActStock(java.sql.Connection conn, int intActGrp ){
   boolean blnRes=true;
   java.sql.Statement stmLoc;
   try{
     stmLoc=conn.createStatement();
     
     //System.out.println("sqlEmp: "+ stbinvemp.toString() );
     //System.out.println("sqlGrp: "+ stbinvgrp.toString() );
//     System.out.println("ZafInvItm.ejecutaActStock " + stbinvemp.toString());
     stmLoc.executeUpdate(stbinvemp.toString()); 
        
     if(intTipConf==1){ 
      if(intActGrp==1) stmLoc.executeUpdate(stbinvgrp.toString());
     }
     stmLoc.close();
     stmLoc=null;
   }catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorSqlException(Evt); }     
    catch(Exception evt){  blnRes=false; mostarErrorException(evt); } 
  return  blnRes; 
}

  


/**
 * Esta función se encarga de obtener el query que se ejecutara para disminucion o aumentar el inventario.
 *
 * @return  El Query del inventario.
 */
public String getQueryEjecutaActStock(){
   String strSqlQue="";
   try{
  
     strSqlQue=stbinvemp.toString();
   
   }catch(Exception evt){  mostarErrorException(evt); }
  return  strSqlQue;
}



/**
 * Esta función se encarga de limpiar las variable de tipo buffer tipo querys.
 */
public void limpiarObjeto(){
  stbinvemp=null;
  stbinvgrp=null;
  stbConStkInvEmp=null;
  stbConStkInvGrp=null;
}


/** 
 * Esta función se encarga de saber si el item es de servicio o no.
 * 
 * @param conn  Conexion de la base de datos
 * @param intCodItm Codigo del item 
 * @return True: si el item no es de servicio.
 * @return False: si el item es de servicio.
 */
public boolean isItmServicio(java.sql.Connection conn, int intCodItm){
  boolean blnRes=false;
  String strSql="";
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  try{
      if(conn!=null){
         stmLoc=conn.createStatement(); 
        
         strSql="SELECT st_ser FROM tbm_inv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+intCodItm;
         rstLoc = stmLoc.executeQuery(strSql);
         if(rstLoc.next())
            blnRes=(rstLoc.getString("st_ser").equalsIgnoreCase("N"))?true:false;
         rstLoc.close();
         stmLoc.close();
         rstLoc=null;
         stmLoc=null;
     }}catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorSqlException(Evt); }
       catch(Exception Evt){ blnRes=false; mostarErrorException(Evt); }
    return blnRes;
  }   
    

/**
 * Esta función se encarga de saber si el item es de servicio o no.
 *
 * @param conn  Conexion de la base de datos
 * @param intCodItm Codigo del item
 * @return True: si el item no es de servicio.
 * <br> False: si el item es de servicio.
 */
public boolean getItmServicio(java.sql.Connection conn, int intCodEmp, int intCodItm){
  boolean blnRes=false;
  String strSql="";
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  try{
      if(conn!=null){
         stmLoc=conn.createStatement();

         strSql="SELECT st_ser FROM tbm_inv WHERE co_emp="+intCodEmp+" AND co_itm="+intCodItm+" AND st_ser='N'";
         rstLoc = stmLoc.executeQuery(strSql);
         if(rstLoc.next())
            blnRes=true;
         rstLoc.close();
         stmLoc.close();
         rstLoc=null;
         stmLoc=null;
     }}catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorSqlException(Evt); }
       catch(Exception Evt){ blnRes=false; mostarErrorException(Evt); }
    return blnRes;
  }




/**
 * Funcion que se encarga de validar que no haya negativo en algun item que tenga movimiento.
 * 
 * @param conn Recibe la conexion de la base
 * @param intValGrpEmp si validad stock con grupo  1=Si  0=No
 * @return True: si no hay ningun problema
 * @return False:si hay problema con item en negativo 
 */
public boolean ejecutaVerificacionStock(java.sql.Connection conn, int intValGrpEmp ){
   boolean blnRes=true;
   java.sql.Statement stmLoc;
   java.sql.ResultSet rstLoc;
   String strSql="";
   String strItmProStk=""; // Item con problema de stock
   try{
     if(conn!=null){  
     stmLoc=conn.createStatement();
     
     if(intTipConf==2) // Para que trabaje por Empresa.....
        strSql="SELECT x.co_emp, x.co_itm, inv.tx_codalt, inv.tx_nomitm, sum(x.nd_stkact)  as nd_stkact FROM( "+stbConStkInvEmp.toString()+" ) AS x  LEFT JOIN tbm_inv AS inv ON(inv.co_emp=x.co_emp AND inv.co_itm=x.co_itm) WHERE x.nd_stkact < 0 GROUP BY x.co_emp, x.co_itm, inv.tx_codalt, inv.tx_nomitm "; 
     
     if(intTipConf==1) {
       if(intValGrpEmp==0) strSql="SELECT x.co_emp, x.co_itm, inv.tx_codalt, inv.tx_nomitm, sum(x.nd_stkact)  as nd_stkact FROM( "+stbConStkInvEmp.toString()+" ) AS x LEFT JOIN tbm_inv AS inv ON(inv.co_emp=x.co_emp AND inv.co_itm=x.co_itm) WHERE x.nd_stkact < 0 GROUP BY x.co_emp, x.co_itm, inv.tx_codalt, inv.tx_nomitm "; 
       if(intValGrpEmp==1) strSql="SELECT x.co_itm, inv.tx_codalt, inv.tx_nomitm, sum(x.nd_stkact)  as nd_stkact FROM( "+stbConStkInvGrp.toString()+" ) AS x LEFT JOIN tbm_inv AS inv ON(inv.co_emp="+INTCODEMPGRP+" AND inv.co_itm=x.co_itm) WHERE x.nd_stkact < 0 GROUP BY x.co_itm, inv.tx_codalt, inv.tx_nomitm "; 
     }
     System.out.println("ZafInvItm.ejecutaVerificacionStock >> "+ intValGrpEmp +"><><><    \n" + strSql );
     
     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next())
        strItmProStk=strItmProStk+rstLoc.getString("tx_codalt")+" "+rstLoc.getString("tx_nomitm")+" //  "+rstLoc.getString("nd_stkact")+    "\n";
     rstLoc.close();
     rstLoc=null;
        
     if(!strItmProStk.equals("")){
         strItmProStk="Problemas de Inventario. \n"+strItmProStk;
         MensajeInf(strItmProStk);
         blnRes=false;
     }
     stmLoc.close();
     stmLoc=null;
  }}catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorSqlException(Evt); }     
    catch(Exception evt){  blnRes=false; mostarErrorException(evt); } 
  return  blnRes; 
}





public void MensajeInf(String strMsg){
   //JOptionPane obj =new JOptionPane();
   String strTit="Mensaje del sistema Zafiro";
   JOptionPane.showMessageDialog(jIntfra,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
   //obj=null;
}





/**
 * Función que se encarga de devolver el costo de item sea esta del grupo o empresa dependiendo como se configure.
 *
 * @param conn Concexion del motor de la base de datos en que se trabaja
 * @param codigoEmpresa Código de la empresa con la que se trabaje
 * @param codigoItem  Código del item
 * 
 * @return El costo de item
 */
public double getCostoItm(java.sql.Connection conn, int codigoEmpresa, int codigoItem){
  double dblRes=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
    if(conn!=null){
       stmLoc=conn.createStatement();
       
      if(intTipConf==1) 
       strSql="SELECT nd_cosuni FROM tbm_inv WHERE co_emp="+INTCODEMPGRP+" AND co_itm = " +
       " (SELECT co_itm FROM tbm_equinv WHERE co_emp="+INTCODEMPGRP+" AND co_itmmae=(Select co_itmmae from tbm_equinv where co_emp ="+ codigoEmpresa +" and co_itm="+ codigoItem +"))";
       
      if(intTipConf==2) 
        strSql="SELECT nd_cosuni FROM tbm_inv WHERE co_emp="+codigoEmpresa+" AND co_itm ="+codigoItem+"";
       
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next()){
          dblRes=rstLoc.getDouble("nd_cosUni");
        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;
       }
    }}catch(java.sql.SQLException e){ mostarErrorSqlException(e); }
      catch(Exception e){ mostarErrorException(e); }
     return dblRes;
  }
   

    
/**
 * Función que validad si es nulo asignas "" caso contrario devuelve el valor que tiene.
 * @param objTbl Objeto de la celda 
 * @return "" si el nulo ó el valor que tiene
 */
public String getStringDatoValidado( Object objTbl ){
 String strVar="";
 try{
    if(objTbl==null) strVar="";
    else if(objTbl.toString().equals("")) strVar="";
    else strVar=objTbl.toString();
  }catch(Exception e){ mostarErrorException(e); }
 return strVar;   
}

/**
 * Función que validad si es nulo asignas "0" caso contrario devuelve el valor que tiene.
 * @param objTbl Objeto de la celda 
 * @return "0" si el nulo ó  vacion "" el valor que tiene
 */
public String getIntDatoValidado( Object objTbl ){
 String strVar="0";
 try{
    if(objTbl==null) strVar="0";
    else if(objTbl.toString().equals("")) strVar="0";
    else strVar=objTbl.toString();
  }catch(Exception e){ mostarErrorException(e); }
 return strVar;   
}




/**
 * Funcion que se encarga de truncar el valor recibido
 * @param dblval  valor recibido
 * @param intNumDec  numero de decilames
 * @return  retorna el valor truncado
 *  <br> ejemplo <br>
 *   25.52  -> 25.00
 */
public double Truncar(double dblval, int intNumDec){
 double dlbRes=0;
 try{
  if(dblval > 0)
    dblval = Math.floor(dblval * Math.pow(10,intNumDec))/Math.pow(10,intNumDec);
  else
    dblval = Math.ceil(dblval * Math.pow(10,intNumDec))/Math.pow(10,intNumDec);

  dlbRes=dblval;

 }catch(Exception e){ dblval=-1;  mostarErrorException(e); }
  return dlbRes;
}





public void enviarCorreo(String strCorEleTo, String strMsExcp ){
  try
    {

        String server = "mail.tuvalsa.com";
        String userName = "zafiro@tuvalsa.com";
        String password = "3Mjoafdfma!6MK7n";
        String fromAddres = "zafiro@tuvalsa.com";
        String toAddres = strCorEleTo;

        String cc = "";
        String bcc = "";
        boolean htmlFormat = false;
        String subject = "Sistema Zafiro:   ";
        String body = strMsExcp;

        

        sendMailTuv(server, userName, password, fromAddres, toAddres, cc, bcc,
                     htmlFormat, subject, body);

        }catch (Exception e) {  e.printStackTrace();    }
      }

/**
 * Funcion de enviar correo electronico
 * @param strCorEleTo  Correo para
 * @param subject      Asunto del correo
 * @param strMsExcp    Mensaje
 */
public void enviarCorreo(String strCorEleTo, String subject, String strMsExcp ){
  try
    {

        String server = "mail.tuvalsa.com";
        String userName = "zafiro@tuvalsa.com";
        String password = "3Mjoafdfma!6MK7n";
        String fromAddres = "zafiro@tuvalsa.com";
        String toAddres = strCorEleTo;

        String cc = "";
        String bcc = "";
        boolean htmlFormat = false;
        String body = strMsExcp;

        sendMailTuv(server, userName, password, fromAddres, toAddres, cc, bcc,
                     htmlFormat, subject, body);

        }catch (Exception e) {  e.printStackTrace();    }
      }


 public boolean sendMailTuv(String server, String userName, String password, String fromAddress, String toAddress, String cc, String bcc, boolean htmlFormat, String subject, String body)
   {
    boolean blnRes=false;
        try{
           Properties props = System.getProperties();
           //System.out.println("---1---");
           props.put("mail.smtp.starttls.enable", "true");
           props.put("mail.smtp.ssl.trust", server);
           props.put("mail.smtp.host", server);
           props.put("mail.smtp.user", userName);
           props.put("mail.smtp.password", password);
           props.put("mail.smtp.port", "587");
           props.put("mail.smtp.auth", "true");                      

           Authenticator auth = new MyAuthenticator();

            // Get session
            Session session = Session.getDefaultInstance(props, auth);
            session.setDebug(true);

            // Define message
            MimeMessage message = new MimeMessage(session);

            // Set the from address
            message.setFrom(new InternetAddress(fromAddress));

            // Set the to address
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));

            // Set the subject
            message.setSubject(subject);


            MimeBodyPart  cuerpoCorreo = new MimeBodyPart();
            //cuerpoCorreo.setText("Estado de Cuenta ...");
             cuerpoCorreo.setContent(body, "text/html");

            MimeMultipart multipart= new MimeMultipart();
            multipart.addBodyPart(cuerpoCorreo);

            message.setContent(multipart);

            message.saveChanges();


            Transport.send(message);

           blnRes=true;

       }catch(MessagingException e){ blnRes=false;  e.printStackTrace(); }

      return blnRes;
    }

    static class MyAuthenticator extends Authenticator {
        PasswordAuthentication l = new PasswordAuthentication("zafiro@tuvalsa.com", "3Mjoafdfma!6MK7n");
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return l;
        }
    }
  
/**
 * Funcion que permite saber si el ingreso que corresponde al egreso esta cofirmado o no y actuliza la cantidad
 * nunca recividad
 * @param conn
 * @param strCodEmp
 * @param strCodLoc
 * @param strCodTipDoc
 * @param strCodDoc
 * @param strDocReg
 * @param ndCanNunEnv
 * @return
 */
public boolean _getVerificaConfirmacionIng(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strDocReg, double ndCanNunEnv, String strCodBodRel, double ndCanCon  ){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rst;
 String strSql="";
 String strEstConfIng="";
 int intTipDocConf=114;  // confirmacion de ingreso
 int intCodDoc=0;
 int intNumDoc=0;
 java.util.Date datFecAux;
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();
      stmLoc01=conn.createStatement();


      strSql="select * from ( select ( nd_can - "
      + " ( case when nd_cancon is null then 0 else nd_cancon end +  "
      + "   case when nd_cannunrec is null then 0 else nd_cannunrec end +  "
      + "   case when nd_cantotmalest is null then 0 else nd_cantotmalest end  "
      + " ) ) as totconf , co_itm, st_meringegrfisbod  from tbm_detmovinv "
      + " where  co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
      + " and co_doc="+strCodDoc+"  and co_reg="+strDocReg+" "
      + "  ) as x  where  ("+ndCanCon+" + "+ndCanNunEnv+")  <=  totconf    ";
      
      System.out.println("ZafInvItm._getVerificaConfirmacionIng: " + strSql );
      
      rst=stmLoc.executeQuery(strSql);
      if(rst.next()){

           datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
           if(datFecAux==null)
              return false;

           strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabingegrmerbod WHERE " +
           " co_emp="+strCodEmp+" AND co_loc="+strCodLoc+" AND co_tipDoc="+intTipDocConf;
           intCodDoc = _getMaxCodigo(conn, strSql);

           strSql="SELECT CASE WHEN (ne_ultdoc+1) is null THEN 1 ELSE ne_ultdoc+1 END AS co_doc FROM tbm_cabtipdoc WHERE " +
           " co_emp="+strCodEmp+" AND co_loc="+strCodLoc+" AND co_tipDoc="+intTipDocConf;
           intNumDoc = _getMaxCodigo(conn, strSql);


           strSql="INSERT INTO tbm_cabingegrmerbod(" +
                " co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, " +
                " co_locrel, co_tipdocrel, co_docrel, co_mnu, st_imp, tx_obs1, " +
                " tx_obs2, st_reg, fe_ing, co_usring ) "+
            " VALUES( " +
            " "+strCodEmp+", "+strCodLoc+", "+intTipDocConf+", "+intCodDoc+" " +
            " ,'"+datFecAux+"', "+intNumDoc+", "+strCodBodRel+", " +
            " "+strCodLoc+", "+strCodTipDoc+", "+strCodDoc+", "+
            " 286, 'N', '',  'CONFIRMACION REALIZADA POR EGRESO MERCADERIA QUE NO SE ENVIARA.', "+
            " 'A', "+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getCodigoUsuario()+" )";
            //System.out.println("  --> 1  "+strSql );
            stmLoc01.executeUpdate(strSql);

            strSql="UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDoc+" WHERE co_emp="+strCodEmp+" " +
            " AND co_loc="+strCodLoc+" AND co_tipdoc="+intTipDocConf;
            stmLoc01.executeUpdate(strSql);

             strSql="INSERT INTO tbm_detingegrmerbod( " +
             " co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrel, co_tipdocrel, "+
             " co_docrel, co_regrel, co_itm, co_bod,  nd_can, tx_obs1, nd_canMalEst, st_solProReaMerMalEst, nd_cannunrec, st_solProReaMerNunRec ) "+
             " VALUES("+strCodEmp+", "+strCodLoc+", "+intTipDocConf+", "+intCodDoc+" " +
             " , 1 , "+strCodLoc+", "+strCodTipDoc+", "+strCodDoc+", "+
             " "+strDocReg+",  "+
             " "+rst.getInt("co_itm")+", "+strCodBodRel+"  " +
             " ,"+ndCanCon+", " +
             " '' ,0 ,'' ,"+ndCanNunEnv+", 'P' ) ";
             //System.out.println("  --> 2  "+strSql );
             stmLoc01.executeUpdate(strSql);

           if(rst.getString("st_meringegrfisbod").equals("S")){
                strSql=" UPDATE tbm_invbod SET nd_caningbod=nd_caningbod-"+(ndCanCon+ndCanNunEnv)+" WHERE  co_emp="+strCodEmp+" and co_bod="+strCodBodRel+" and co_itm="+rst.getInt("co_itm")+" ; ";
                stmLoc01.executeUpdate(strSql);
           }           
         

           strSql="UPDATE tbm_detmovinv SET "
           + " nd_cancon= case when nd_cancon is null then 0 else nd_cancon  end + "+ndCanCon+" "
           + " ,nd_cannunrec= case when nd_cannunrec is null then 0 else nd_cannunrec  end + "+ndCanNunEnv+" "
           + " WHERE  co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
           + " and co_doc="+strCodDoc+"  and co_reg="+strDocReg+" ";
            stmLoc01.executeUpdate(strSql);


           strEstConfIng= _getEstConfirmacionIng(conn, strCodEmp, strCodLoc, strCodTipDoc,strCodDoc );

           strSql=" ; UPDATE tbm_cabmovinv SET st_coninv='"+strEstConfIng+"' "
           + " WHERE  co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
           + " and co_doc="+strCodDoc+"  ";
           stmLoc01.executeUpdate(strSql);


         blnRes=true;
      }
      rst.close();
      rst=null;

      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;

 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
  return blnRes;
}

public boolean _getVerificaConfirmacionIngRel(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strDocReg, double ndCanNunEnv, String strCodBodRel, double ndCanCon  ){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rst;
 String strSql="";
 String strEstConfIng="";
 int intTipDocConf=114;  // confirmacion de ingreso
 int intCodDoc=0;
 int intNumDoc=0;
 java.util.Date datFecAux;
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();
      stmLoc01=conn.createStatement();


      strSql="select * from ( select ( nd_can - "
      + " ( case when nd_cancon is null then 0 else nd_cancon end +  "
      + "   case when nd_cannunrec is null then 0 else nd_cannunrec end +  "
      + "   case when nd_cantotmalest is null then 0 else nd_cantotmalest end  "
      + " ) ) as totconf , co_itm, st_meringegrfisbod  from tbm_detmovinv "
      + " where  co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
      + " and co_doc="+strCodDoc+"  and co_reg="+strDocReg+" "
      + "  ) as x  where  ("+ndCanCon+" + "+ndCanNunEnv+")  <=  totconf    ";
      
      System.out.println("ZafInvItm._getVerificaConfirmacionIngRel: " + strSql );
      
      rst=stmLoc.executeQuery(strSql);
      if(rst.next()){

           datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
           if(datFecAux==null)
              return false;

           strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabingegrmerbod WHERE " +
           " co_emp="+strCodEmp+" AND co_loc="+strCodLoc+" AND co_tipDoc="+intTipDocConf;
           intCodDoc = _getMaxCodigo(conn, strSql);

           strSql="SELECT CASE WHEN (ne_ultdoc+1) is null THEN 1 ELSE ne_ultdoc+1 END AS co_doc FROM tbm_cabtipdoc WHERE " +
           " co_emp="+strCodEmp+" AND co_loc="+strCodLoc+" AND co_tipDoc="+intTipDocConf;
           intNumDoc = _getMaxCodigo(conn, strSql);


           strSql="INSERT INTO tbm_cabingegrmerbod(" +
           " co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, " +
           " co_locrel, co_tipdocrel, co_docrel, co_mnu, st_imp, tx_obs1, " +
           " tx_obs2, st_reg, fe_ing, co_usring ) "+
           " VALUES( " +
           " "+strCodEmp+", "+strCodLoc+", "+intTipDocConf+", "+intCodDoc+" " +
           " ,'"+datFecAux+"', "+intNumDoc+", "+strCodBodRel+", " +
           " "+strCodLoc+", "+strCodTipDoc+", "+strCodDoc+", "+
           " 286, 'N', '',  'CONFIRMACION AUTOMATICA REALIZADA POR INGRESO DE MERCADERIA.', "+
           " 'A', "+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getCodigoUsuario()+" )";
           //System.out.println("  --> 1  "+strSql );
           stmLoc01.executeUpdate(strSql);

           strSql="UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDoc+" WHERE co_emp="+strCodEmp+" " +
           " AND co_loc="+strCodLoc+" AND co_tipdoc="+intTipDocConf;
           stmLoc01.executeUpdate(strSql);

           strSql="INSERT INTO tbm_detingegrmerbod( " +
           " co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrel, co_tipdocrel, "+
           " co_docrel, co_regrel, co_itm, co_bod,  nd_can, tx_obs1, nd_canMalEst, st_solProReaMerMalEst, nd_cannunrec, st_solProReaMerNunRec ) "+
           " VALUES("+strCodEmp+", "+strCodLoc+", "+intTipDocConf+", "+intCodDoc+" " +
           " , 1 , "+strCodLoc+", "+strCodTipDoc+", "+strCodDoc+", "+
           " "+strDocReg+",  "+
           " "+rst.getInt("co_itm")+", "+strCodBodRel+"  " +
           " ,"+ndCanCon+", " +
           " '', 0, '', "+ndCanNunEnv+", '' ) ";
           //System.out.println("  --> 2  "+strSql );
           stmLoc01.executeUpdate(strSql);



           if(rst.getString("st_meringegrfisbod").equals("S")){
                strSql=" UPDATE tbm_invbod SET nd_caningbod=nd_caningbod-"+(ndCanCon+ndCanNunEnv)+" WHERE  co_emp="+strCodEmp+" and co_bod="+strCodBodRel+" and co_itm="+rst.getInt("co_itm")+" ; ";
                stmLoc01.executeUpdate(strSql);
           }           

           strSql="UPDATE tbm_detmovinv SET "
           + " nd_cancon= case when nd_cancon is null then 0 else nd_cancon  end + "+ndCanCon+" "
           + " ,nd_cannunrec= case when nd_cannunrec is null then 0 else nd_cannunrec  end + "+ndCanNunEnv+" "
           + " WHERE  co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
           + " and co_doc="+strCodDoc+"  and co_reg="+strDocReg+" ";
           stmLoc01.executeUpdate(strSql);


           strEstConfIng= _getEstConfirmacionIng(conn, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);

           strSql=" ; UPDATE tbm_cabmovinv SET st_coninv='"+strEstConfIng+"' "
           + " WHERE  co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
           + " and co_doc="+strCodDoc+"  ";
           stmLoc01.executeUpdate(strSql);


         blnRes=true;
      }
      rst.close();
      rst=null;

      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;

 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
  return blnRes;
}
   
/* José Mario Marín M - 31/Oct/2014 */
public boolean _getVerificaConfirmacionEgr(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strDocReg, double ndCanNunEnv, String strCodBodRel, double ndCanCon  ){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rst;
 String strSql="";
 String strEstConfIng="";
 int intTipDocConf=114;  // confirmacion de egreso
 int intCodDoc=0;
 int intNumDoc=0;
 java.util.Date datFecAux;
 
 double ndCanConfirmada;
 double ndCanPenConfirmar;
 
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();
      stmLoc01=conn.createStatement();

      /*strSql="select * from ( "
      + " select (abs(x2.nd_can) - abs(x2.nd_cancon)) as canpenconf, x2.co_emp, x2.co_loc, x2.co_tipdoc, x2.co_doc, "
      + " x2.co_reg, x2.co_itm          "
      + "  from tbm_detmovinv as x "
      + " inner join tbr_detmovinv as x1 on (x1.co_emprel=x.co_emp and x1.co_locrel=x.co_loc and x1.co_tipdocrel=x.co_tipdoc and x1.co_docrel=x.co_doc and x1.co_regrel=x.co_reg "
      + " and x1.co_emp=x.co_emp and x1.co_tipdoc= 1  ) "
      + " inner join tbm_detguirem as x2 on (x2.co_emprel=x1.co_emp and x2.co_locrel=x1.co_loc and x2.co_tipdocrel=x1.co_tipdoc and x2.co_docrel=x1.co_doc and x2.co_regrel=x1.co_reg ) "
      + " where x.co_emp="+strCodEmp+" and x.co_loc="+strCodLoc+" and x.co_tipdoc="+strCodTipDoc+" and x.co_doc= "+strCodDoc+" and x.co_reg="+strDocReg+" "
      + " ) as x where   canpenconf  >= "+ndCanCon+"  ";*/
      
      strSql="select sum(cantotpenconf) as cantotpenconf, y.*"
           + " from ( "
           + " select  (abs(x2.nd_can) - abs(x2.nd_cancon)) as cantotpenconf"
           + " from tbm_detmovinv as x" 
           + " inner join tbr_detmovinv as x1 on (x1.co_emprel=x.co_emp and x1.co_locrel=x.co_loc and x1.co_tipdocrel=x.co_tipdoc and x1.co_docrel=x.co_doc and x1.co_regrel=x.co_reg  and x1.co_emp=x.co_emp and x1.co_tipdoc IN ( 1,228)  ) "  
           + " inner join tbm_detguirem as x2 on (x2.co_emprel=x1.co_emp and x2.co_locrel=x1.co_loc and x2.co_tipdocrel=x1.co_tipdoc and x2.co_docrel=x1.co_doc and x2.co_regrel=x1.co_reg ) "  
           + " where x.co_emp="+strCodEmp
           + " and x.co_loc="+strCodLoc 
           + " and x.co_tipdoc="+strCodTipDoc 
           + " and x.co_doc="+strCodDoc
           + " and x.co_reg="+strDocReg 
           + " ) as X, (" 
           + " select (abs(x2.nd_can) - abs(x2.nd_cancon)) as canpenconf, x2.co_emp, x2.co_loc, x2.co_tipdoc, x2.co_doc, x2.co_reg, x2.co_itm " 
           + " from tbm_detmovinv as x " 
           + " inner join tbr_detmovinv as x1 on (x1.co_emprel=x.co_emp and x1.co_locrel=x.co_loc and x1.co_tipdocrel=x.co_tipdoc and x1.co_docrel=x.co_doc and x1.co_regrel=x.co_reg  and x1.co_emp=x.co_emp and x1.co_tipdoc IN ( 1,228)  ) "  	
           + " inner join tbm_detguirem as x2 on (x2.co_emprel=x1.co_emp and x2.co_locrel=x1.co_loc and x2.co_tipdocrel=x1.co_tipdoc and x2.co_docrel=x1.co_doc and x2.co_regrel=x1.co_reg ) "  	
           + " where x.co_emp="+strCodEmp
           + " and x.co_loc="+strCodLoc 
           + " and x.co_tipdoc="+strCodTipDoc 
           + " and x.co_doc="+strCodDoc 
           + " and x.co_reg="+strDocReg 
           + " ) as Y" 
           + " group by y.co_emp, y.co_loc, y.co_tipdoc, y.co_doc, y.co_reg, y.co_itm, y.canpenconf" 
           + " having sum(cantotpenconf) >= "+ ndCanCon;

      System.out.println("ZafInvItm._getVerificaConfirmacionEgr: "+strSql );
/* José Mario Marín M - 31/Oct/2014 */
      rst=stmLoc.executeQuery(strSql);
      if(rst.next()){
                    
           datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
           if(datFecAux==null)
              return false;

           strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabingegrmerbod WHERE " +
           " co_emp="+rst.getInt("co_emp")+" AND co_loc="+rst.getInt("co_loc")+" AND co_tipDoc="+intTipDocConf;
           intCodDoc = _getMaxCodigo(conn, strSql);

           strSql="SELECT CASE WHEN (ne_ultdoc+1) is null THEN 1 ELSE ne_ultdoc+1 END AS co_doc FROM tbm_cabtipdoc WHERE " +
           " co_emp="+rst.getInt("co_emp")+" AND co_loc="+rst.getInt("co_loc")+" AND co_tipDoc="+intTipDocConf;
           intNumDoc = _getMaxCodigo(conn, strSql);


           strSql="INSERT INTO tbm_cabingegrmerbod(" +
                " co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, " +
                " co_locrelguirem, co_tipdocrelguirem, co_docrelguirem, co_mnu, st_imp, tx_obs1, " +
                " tx_obs2, st_reg, fe_ing, co_usring ) "+
            " VALUES( " +
            " "+rst.getInt("co_emp")+", "+rst.getInt("co_loc")+", "+intTipDocConf+", "+intCodDoc+" " +
            " ,'"+datFecAux+"', "+intNumDoc+", "+strCodBodRel+", " +
            " "+rst.getInt("co_loc")+", "+rst.getInt("co_tipdoc")+", "+rst.getInt("co_doc")+", "+
            " 2205, 'N', '',  'CONFIRMACION REALIZADA POR EGRESO MERCADERIA AUTOMATICA.', "+
            " 'A', "+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getCodigoUsuario()+" )";
            System.out.println("  --> 1  "+strSql );
            stmLoc01.executeUpdate(strSql);

            strSql="UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDoc+" WHERE co_emp="+rst.getInt("co_emp")+" " +
            " AND co_loc="+rst.getInt("co_loc")+" AND co_tipdoc="+intTipDocConf;
            System.out.println("  --> 1  "+strSql );
            stmLoc01.executeUpdate(strSql);

             strSql="INSERT INTO tbm_detingegrmerbod( " +
             " co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrelguirem, co_tipdocrelguirem, "+
             " co_docrelguirem, co_regrelguirem, co_itm, co_bod,  nd_can, tx_obs1, nd_canMalEst, st_solProReaMerMalEst, nd_cannunrec, st_solProReaMerNunRec ) "+
             " VALUES("+rst.getInt("co_emp")+", "+rst.getInt("co_loc")+", "+intTipDocConf+", "+intCodDoc+" " +
             " , 1 , "+rst.getInt("co_loc")+", "+rst.getInt("co_tipdoc")+", "+rst.getInt("co_doc")+", "+
             " "+rst.getInt("co_reg")+",  "+
             " "+rst.getInt("co_itm")+", "+strCodBodRel+"  " +
             " ,"+ndCanCon+", " +
             " '' ,0 ,'' ,"+ndCanNunEnv+", 'P' ) ";
             System.out.println("  --> 2  "+strSql );
             stmLoc01.executeUpdate(strSql);

           strSql="UPDATE tbm_detguirem SET "
           + " nd_cancon= case when nd_cancon is null then 0 else nd_cancon  end + "+ndCanCon+" "
           + " ,nd_cannunrec= case when nd_cannunrec is null then 0 else nd_cannunrec  end + "+ndCanNunEnv+" "
           + " WHERE  co_emp="+rst.getInt("co_emp")+" and co_loc="+rst.getInt("co_loc")+" and co_tipdoc="+rst.getInt("co_tipdoc")+" "
           + " and co_doc="+rst.getInt("co_doc")+"  and co_reg="+rst.getInt("co_reg")+" ";
           System.out.println("  --> 1  "+strSql );
            stmLoc01.executeUpdate(strSql);

           strEstConfIng= _getEstConfirmacionEgr(conn, rst.getInt("co_emp"), rst.getInt("co_loc"), rst.getInt("co_tipdoc"), rst.getInt("co_doc") );

           strSql=" ; UPDATE tbm_cabguirem SET st_coninv='"+strEstConfIng+"' "
           + " WHERE  co_emp="+rst.getInt("co_emp")+" and co_loc="+rst.getInt("co_loc")+" and co_tipdoc="+rst.getInt("co_tipdoc")+" "
           + " and co_doc="+rst.getInt("co_doc")+"  ";
           System.out.println("  --> 1  "+strSql );
           stmLoc01.executeUpdate(strSql);


         blnRes=true;
      }
      rst.close();
      rst=null;

      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;

 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
  return blnRes;
}

/**
 * Funcion que se encarga de obtener el maxino codigo de docuemnto que se buscara.
 * @param conn
 * @param strSql
 * @return
 */
private int _getMaxCodigo(java.sql.Connection conn, String strSql ){
  int intCodDoc=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  try{
      if(conn!=null){
         stmLoc=conn.createStatement();

         rstLoc = stmLoc.executeQuery(strSql);
         if(rstLoc.next())
               intCodDoc = rstLoc.getInt("co_doc");
          rstLoc.close();
          rstLoc=null;

         stmLoc.close();
         stmLoc=null;

     }}catch(java.sql.SQLException e){ intCodDoc=-1;  }
       catch(Exception e){ intCodDoc=-1;; }
   return intCodDoc;
 }

public String _getEstConfirmacionIng(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc  ){
 String strEstConfIng=null;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rst;
 String strSql="";
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();
      stmLoc01=conn.createStatement();

      strSql="select  can, cantotconf ,  case when cantotconf = can then 'C' else  case when cantotconf > 0 then  'E' else 'P' end end  as estconf "
      + " from ( "
      + " select can, (cancon+cannunrec+canmalest) as cantotconf from ( "
      + " select sum(case when nd_can is null then 0 else nd_can end ) as can, "
      + " sum(case when nd_cancon is null then 0 else nd_cancon end ) as cancon, "
      + " sum(case when nd_cannunrec is null then 0 else nd_cannunrec end ) as cannunrec,  "
      + " sum(case when nd_cantotmalest is null then 0 else nd_cantotmalest end ) as canmalest "
      + " from tbm_detmovinv "
      + " where co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" and co_doc= "+strCodDoc+"  and  st_meringegrfisbod='S' and nd_can > 0 "
      + " ) as x  ) as x  ";
      rst=stmLoc.executeQuery(strSql);
      if(rst.next()){
           strEstConfIng=rst.getString("estconf");
      }
      rst.close();
      rst=null;

      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;

 }}catch(java.sql.SQLException e) { strEstConfIng=null;  mostarErrorException(e);  }
  catch(Exception  e){ strEstConfIng=null;  mostarErrorException(e);  }
  return strEstConfIng;
}

public String _getEstConfirmacionEgr(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc  ){
 String strEstConfIng=null;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rst;
 String strSql="";
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();
      stmLoc01=conn.createStatement();

      strSql="select  can, cantotconf ,  case when cantotconf = can then 'C' else  case when cantotconf > 0 then  'E' else 'P' end end  as estconf "
      + " from ( "
      + " select can, (cancon+cannunrec) as cantotconf from ( "
      + " select sum(case when nd_can is null then 0 else nd_can end ) as can, "
      + " sum(case when nd_cancon is null then 0 else nd_cancon end ) as cancon, "
      + " sum(case when nd_cannunrec is null then 0 else nd_cannunrec end ) as cannunrec  "
      + " from tbm_detguirem "
      + " where co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc= "+intCodDoc+"  and  st_meregrfisbod='S' and nd_can > 0 "
      + " ) as x  ) as x  ";
      rst=stmLoc.executeQuery(strSql);
      if(rst.next()){
           strEstConfIng=rst.getString("estconf");
      }
      rst.close();
      rst=null;

      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;

 }}catch(java.sql.SQLException e) { strEstConfIng=null;  mostarErrorException(e);  }
  catch(Exception  e){ strEstConfIng=null;  mostarErrorException(e);  }
  return strEstConfIng;
}

/**
 * Funcion que permite saber si ya tiene ingresado cantidad nunca recibida que corresponde a una confirmacion de
 * egreso que mercaderia que nunca enviarar .
 * @param conn
 * @param strCodEmp
 * @param strCodLoc
 * @param strCodTipDoc
 * @param strCodDoc
 * @param strDocReg
 * @param ndCanNunRec
 * @return
 */
public boolean _getVerificaCanNunRecIng(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strDocReg, double ndCanNunRec  ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rst;
 String strSql="";
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      strSql="select nd_cannunrec from tbm_detmovinv "
      + " where  co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
      + " and co_doc="+strCodDoc+"  and co_reg="+strDocReg+" "
      + " and   nd_cannunrec <= "+ndCanNunRec+"  ";
      System.out.println(""+strSql );
      rst=stmLoc.executeQuery(strSql);
      if(rst.next()){

         blnRes=true;
      }
      rst.close();
      rst=null;

      stmLoc.close();
      stmLoc=null;

 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
  return blnRes;
}

public boolean getEstItm(java.sql.Connection conn, int intCodEmp, int intCodItm, String strEstItm ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rst;
 String strSql="";
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      strSql="SELECT co_itm FROM tbm_inv WHERE co_emp="+intCodEmp+" and co_itm="+intCodItm+" and st_ser='"+strEstItm+"' ";
      rst=stmLoc.executeQuery(strSql);
      if(rst.next()){
         blnRes=true;
      }
      rst.close();
      rst=null;
      stmLoc.close();
      stmLoc=null;

 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
  return blnRes;
}

/**
 *  Funcion que se encarga de enviar requerimiento de impresion de guía
 * @param conn
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @param intCodDoc
 * @param intEstTbl estado de la tabla a utilizar  ejem: <BR> 1 = tbm_cabmovinv  <BR> 2 = tbm_cabguirem
 * @return
 */
public boolean enviarRequisitoImp(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intEstTbl ){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rst;
 String strSql="";
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();

    if(intEstTbl==1){
      strSql="select a2.co_ser, a2.ne_pueser, tx_dirser, tx_coreleerr from ( "
      + " select a1.co_emp, a1.co_bod  from tbm_cabmovinv as a "
      + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  "
      + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+" "
      + " and a1.nd_can < 0  group by a1.co_emp, a1.co_bod "
      + " ) as a  "
      + " inner join tbr_sercliserbod as a1 on (a1.co_empbod=a.co_emp and a1.co_bod=a.co_bod )  "
      + " inner join tbm_sercliserloc as a2 on (a2.co_emp=a1.co_empser and a2.co_loc=a1.co_locser and a2.co_ser=a1.co_ser ) "
      + " "; // where a1.co_ser=1 ";
    }
    if(intEstTbl==2){
      strSql="select a2.co_ser, a2.ne_pueser, tx_dirser, tx_coreleerr from ( "
      + " select a.co_emp, a.co_ptopar as co_bod  from tbm_cabguirem as a "
      + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+"  and a.co_doc= "+intCodDoc+" "
      + " ) as a "
      + " inner join tbr_sercliserbod as a1 on (a1.co_empbod=a.co_emp and a1.co_bod=a.co_bod ) "
      + " inner join tbm_sercliserloc as a2 on (a2.co_emp=a1.co_empser and a2.co_loc=a1.co_locser and a2.co_ser=a1.co_ser ) "
      + " where a1.co_ser=1 ";
    }
      System.out.println("ZafInvItm.enviarRequisitoImp: " + strSql);
      rst=stmLoc.executeQuery(strSql);
      while(rst.next()){
          System.out.println(""+ rst.getString("ne_pueser") );
          System.out.println(""+ rst.getString("tx_dirser") );
          enviarRequisitoImp( rst.getString("tx_dirser"), rst.getInt("ne_pueser") );
      }
      rst.close();
      rst=null;
      stmLoc.close();
      stmLoc=null;

 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
  return blnRes;
}

/**
 * Funcion que se encarga de enviar requerimiento de impresion de guia
 * @param strIp    Ip del servidor de impresión de guía
 * @param intPuerto  Puerto del servidor de impresión de guía
 */
public void enviarRequisitoImp(String strIp, int intPuerto){
    try{
       ZafThreadEnvReqSerPrinGuia objHilo = new ZafThreadEnvReqSerPrinGuia(strIp, intPuerto );
       objHilo.start();
     }catch (Exception e){  mostarErrorException(e); }
}



private class ZafThreadEnvReqSerPrinGuia extends Thread
{
 String strIpS="";
 int intPuertoS=0;
 public ZafThreadEnvReqSerPrinGuia(String strIp, int intPuerto)
 {
 strIpS=strIp;
 intPuertoS= intPuerto;
 }
 @Override
 public void run()
 {
  try{

       java.net.Socket s1 = new java.net.Socket(strIpS, intPuertoS);
       java.io.DataOutputStream dos = new java.io.DataOutputStream(s1.getOutputStream());
       dos.writeInt(1);
       dos.close();
       s1.close();

     }catch (java.net.ConnectException connExc){   System.err.println("OCURRIO UN ERROR 1 "+connExc ); }
      catch (java.io.IOException e){  System.err.println("OCURRIO UN ERROR 2 "+ e );  }
  }
}




/**
 *  Funcion que se encarga de enviar al servicio de impresion que imprima la guia
 * @param conn
 * @param intCodBodGrp codigo de bodega de grupo al cual enviara
 * @return
 */
public boolean enviarRequisitoImp(java.sql.Connection conn, int intCodBodGrp ){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rst;
 String strSql="";
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      strSql="select a2.ne_pueser, a2.tx_dirser, a2.tx_coreleerr from  tbr_bodempbodgrp as a "
      + " inner join tbr_sercliserbod as a1 on (a1.co_empbod=a.co_emp and a1.co_bod=a.co_bod ) "
      + " inner join tbm_sercliserloc as a2 on (a2.co_emp=a1.co_empser and  a2.co_loc=a1.co_locser and a2.co_ser=a1.co_ser ) "
      + " where a.co_empgrp=0 and a1.co_Ser=1 and a2.st_reg='A' and a.co_bodgrp= "+intCodBodGrp+" "
      + " group by a2.ne_pueser, a2.tx_dirser, a2.tx_coreleerr ";
      rst=stmLoc.executeQuery(strSql);
      while(rst.next()){
           enviarRequisitoImp( rst.getString("tx_dirser"), rst.getInt("ne_pueser") );
      }
      rst.close();
      rst=null;
      stmLoc.close();
      stmLoc=null;

 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
  return blnRes;
}

/**
 *  Funcion que se encarga de enviar requerimiento de impresion de de punto de venta que enviara mensaje de que la ord.des esta anulado
 * @param conn
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @param intCodDoc
 * @param intEstTbl estado de la tabla a utilizar  ejem: <BR> 1 = tbm_cabmovinv  <BR> 2 = tbm_cabguirem
 * @return
 */
public boolean enviarRequisitoImp(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strTipMensFacGui  ){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rst;
 String strSql="";
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      strSql="select a2.co_ser, a2.ne_pueser, tx_dirser, tx_coreleerr from ( "
      + " select a1.co_emp, a1.co_bod  from tbm_cabmovinv as a "
      + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  "
      + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+" "
      + " and a1.nd_can < 0  group by a1.co_emp, a1.co_bod "
      + " ) as a  "
      + " inner join tbr_sercliserbod as a1 on (a1.co_empbod=a.co_emp and a1.co_bod=a.co_bod )  "
      + " inner join tbm_sercliserloc as a2 on (a2.co_emp=a1.co_empser and a2.co_loc=a1.co_locser and a2.co_ser=a1.co_ser ) "
      + " where a1.co_ser=1 ";

      rst=stmLoc.executeQuery(strSql);
      while(rst.next()){
           enviarRequisitoImp( rst.getString("tx_dirser"), rst.getInt("ne_pueser"), intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strTipMensFacGui );
      }
      rst.close();
      rst=null;
      stmLoc.close();
      stmLoc=null;

 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
  return blnRes;
}



/**
 * Funcion que se encarga de enviar requerimiento de impresion de guia
 * @param strIp Ip del servidor de impresión de guía
 * @param intPuerto  Puerto del servidor de impresión de guía
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @param intCodDoc
 * @param strTipMensFacGui
 */
public void enviarRequisitoImp(String strIp, int intPuerto, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strTipMensFacGui ){
    try{
       ZafThreadEnvMesImpBod objHilo = new ZafThreadEnvMesImpBod(strIp, intPuerto, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strTipMensFacGui );
       objHilo.start();
     }catch (Exception e){  mostarErrorException(e); }
}



private class ZafThreadEnvMesImpBod extends Thread
{
 String strIpS="";
 int intPuertoS=0;
 int intCodEmp=0;
 int intCodLoc=0;
 int intCodTipDoc=0;
 int intCodDoc=0;
 String strTipMensFacGui="";
 public ZafThreadEnvMesImpBod(String strIp, int intPuerto, int intCodEmpRec, int intCodLocRec, int intCodTipDocRec, int intCodDocRec, String strTipMensFacGuiRec )
 {
 strIpS=strIp;
 intPuertoS= intPuerto;
 intCodEmp=intCodEmpRec;
 intCodLoc=intCodLocRec;
 intCodTipDoc=intCodTipDocRec;
 intCodDoc=intCodDocRec;
 strTipMensFacGui=strTipMensFacGuiRec;
 }
 @Override
 public void run()
 {
  try{
       java.net.Socket s1 = new java.net.Socket(strIpS, intPuertoS);
       java.io.DataOutputStream dos = new java.io.DataOutputStream(s1.getOutputStream());
       String strMens=""+intCodEmp+","+intCodLoc+","+intCodTipDoc+","+intCodDoc+","+strTipMensFacGui;
       dos.writeUTF(strMens);
       dos.close();
       s1.close();
     }catch (java.net.ConnectException connExc){   System.err.println("OCURRIO UN ERROR 1 "+connExc ); }
      catch (java.io.IOException e){  System.err.println("OCURRIO UN ERROR 2 "+ e );  }
  }
}




  
/**
 * Obtiene el valor de margen para compra y venta relacionada
 * @param intCodEmpVen  codigo de empresa quien vende
 * @param intCodEmpCom  codigo de empresa quien compra
 * @return  valor de margen
 */
public double _getMargenComVenRel(int intCodEmpVen , int intCodEmpCom){
//  double dblMar=1.05;
  double dblMar=1.00;
//  try{
//
//      if(intCodEmpVen==1){ // TUVAL VENDE A:
//          
//          if(intCodEmpCom==4) dblMar=1.02;         //  DIMULI COMPRA A TUVAL
//           else if(intCodEmpCom==2)  dblMar=1.02; //  CASTEK COMPRA A TUVAL
//
//      }else if(intCodEmpVen==2){ // CASTEK VENDE A:
//
//          if(intCodEmpCom==1) dblMar=1.05;         //  TUVAK COMPRA A CASTEK
//           else if(intCodEmpCom==4)  dblMar=1.05; //  DIMULTI COMPRA A CASTEK
//
//      }else if(intCodEmpVen==4){ // DIMULTI VENDE A:
//
//          if(intCodEmpCom==1) dblMar=1.03;         //  TUVAK COMPRA A DIMULTI
//           else if(intCodEmpCom==2)  dblMar=1.02; //  CASTEK COMPRA A DIMULTI
//
//      }
//
//
//  }catch (Exception e){  System.err.println("OCURRIO UN ERROR 2 _getMargenComVenRel "+ e.toString() );  }
  return dblMar;
}

/**
 * Funcion que permite marcar con estado el documento si tiene items de servicio.
 * @param conn
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @param intCodDoc
 * @return  true = todo bien  <br> false = error.
 */
public boolean _getExiItmSer(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 boolean blnRes=true;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
     System.out.println("ZafInvItm._getExiItmSer");
    if(conn!=null){
       stmLoc=conn.createStatement();
       stmLoc01=conn.createStatement();

       strSql="select a2.st_ser from  tbm_cabMovInv as a "
       + " inner join tbm_detMovInv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
       + " inner join tbm_Inv as a2 on (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm ) "
       + "  where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a2.st_ser='S' ";
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next()){

           strSql="UPDATE tbm_cabMovInv SET st_itmSerPro='S' WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+"  ";
           stmLoc01.executeUpdate(strSql);
           blnRes=true;

       }
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;

 }}catch(java.sql.SQLException e){ blnRes=false;  mostarErrorException(e);  }
   catch(Exception e) {  blnRes=false;  mostarErrorException(e);  }
 return blnRes;
}

    /**
    * Funcion que permite saber si se puede cancelar una transferencia.
    * @param conn
    * @param intCodEmp
    * @param intCodLoc
    * @param intCodTipDoc
    * @param intCodDoc
    * @return  true = procede  <br> false = no procede.
    */

    public boolean _getVerificaCancelaTransf(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
        boolean blnRes = false;
        
        try {
            if (conn != null) {
                if (isDocumentoActivo(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)) {
                    if (isNoConfirmado(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)) {
                        if (actualizaStock(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)) {
                            if (actualizaInvMov(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)) {
                                if (eliminaRegRel(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)) {
                                    if (actualizaRegRel(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)) {
                                        blnRes = true;                                        
                                    }
                                }                                                            
                            }                            
                        }                        
                    }                    
                }
            }
        }        
        catch (Exception e) {
            blnRes = false;
            mostarErrorException(e);
        }        
        
        return blnRes;
    }

    public boolean isDocumentoActivo(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
        boolean blnRes = false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();
                strSql = "SELECT co_doc FROM tbm_cabMovInv "
                + " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc
                + " and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc
                + " and st_reg = 'A' " ;
                
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    blnRes = true;
                }
                
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;                
            }            
        }        
        catch (java.sql.SQLException e) { 
            blnRes = false;
            mostarErrorException(e);
        }        
        catch (Exception e) {
            blnRes = false;
            mostarErrorException(e);
        }        
        
        return blnRes;
    }

    public boolean isNoConfirmado(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
        boolean blnRes = true;
        java.sql.Statement stmLoc, stmLoc01;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();
                stmLoc01 = conn.createStatement();
                strSql = "SELECT DISTINCT a1.st_coninv, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc FROM tbm_detGuiRem AS a "
                + " INNER JOIN tbm_cabGuiRem AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) "
                + " WHERE a.co_emprel="+intCodEmp+" and a.co_locrel="+intCodLoc
                + " and a.co_tipdocrel="+intCodTipDoc+" and a.co_docrel="+intCodDoc;
                
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    if( (rstLoc.getString("st_coninv").equals("E")) || (rstLoc.getString("st_coninv").equals("C")) ){
                        blnRes = false;
                    } else {
                        strSql="UPDATE tbm_cabGuiRem SET st_reg='I' "
                        + " WHERE co_emp="+rstLoc.getInt("co_emp")+" AND co_loc="+rstLoc.getInt("co_loc")
                        + " AND co_tipdoc="+rstLoc.getInt("co_tipdoc")+" AND co_doc="+rstLoc.getInt("co_doc");
                        stmLoc01.executeUpdate(strSql);
                    }                   
                }
                
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;                
                stmLoc01.close();
                stmLoc01=null;                
            }            
        }        
        catch (java.sql.SQLException e) { 
            blnRes = false;
            mostarErrorException(e);
        }        
        catch (Exception e) {
            blnRes = false;
            mostarErrorException(e);
        }        
        
        return blnRes;
    }

    public boolean actualizaStock(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
        boolean blnRes = false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        double dblCan = 0;
        int intTipMov = 0, intActStkRea = 0;
        
        try {
            if (conn != null) {
                inicializaObjeto();
                stmLoc = conn.createStatement();
                strSql = "\n SELECT a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a3.co_reg, a3.co_itm, a3.co_bod, a3.nd_can, a4.tx_natdoc \n FROM tbm_cabtipdoc AS a4 "
                + "\n INNER JOIN tbm_detmovinv AS a3 ON (a3.co_emp=a4.co_emp and a3.co_loc=a4.co_loc and a3.co_tipdoc=a4.co_tipdoc) "
                + "\n INNER JOIN tbr_detmovinv AS a2 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc and a2.co_tipdocrel=a3.co_tipdoc and a2.co_docrel=a3.co_doc and a2.co_regrel=a3.co_reg and a2.st_reg='A' and (a2.co_emp!=a3.co_emp or a2.co_loc!=a3.co_loc or a2.co_tipdoc!=a3.co_tipdoc or a2.co_doc!=a3.co_doc) ) "
                + "\n INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc and a1.co_reg=a2.co_reg) "
                + "\n INNER JOIN tbm_cabmovinv AS a on (a.co_emp=a1.co_emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc and a.co_doc=a1.co_doc) "                        
                + "\n WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc
                + "\n and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc;
                System.out.println("ZafInvItm.actualizaStock JOTA" + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while (rstLoc.next()) {
                    dblCan = rstLoc.getDouble("nd_can");
                    
                    generaQueryStock(rstLoc.getInt("co_emp"), rstLoc.getInt("co_itm"), rstLoc.getInt("co_bod"), dblCan, -1, "N", "N", rstLoc.getString("tx_natdoc"), -1);
                    intActStkRea = 1;
                }
                
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                System.out.println("intActStkRea: " + intActStkRea);
                if (intActStkRea == 1) {
                    if (ejecutaActStock(conn, 3))
                        blnRes = true;
                    else
                        blnRes = false;
                    
                    if (ejecutaVerificacionStock(conn, 3)) ///????
                        blnRes = true;
                    else
                        blnRes = false;
                } else 
                    blnRes = true;
                
            }
            
        }        
        catch (java.sql.SQLException e) { 
            blnRes = false;
            mostarErrorException(e);
        }        
        catch (Exception e) {
            blnRes = false;
            mostarErrorException(e);
        }        
        
        return blnRes;
    }
    
    public boolean actualizaInvMov(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
        boolean blnRes = false;
        java.sql.Statement stmLoc, stmLoc01;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        double dblCan = 0;
        
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();
                stmLoc01 = conn.createStatement();
                strSql = "SELECT a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a3.co_reg, a3.co_itm, a3.co_bod, a3.nd_can FROM tbm_detmovinv AS a3 "
                + " INNER JOIN tbr_detmovinv AS a2 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc and a2.co_tipdocrel=a3.co_tipdoc and a2.co_docrel=a3.co_doc and a2.co_regrel=a3.co_reg and a2.st_reg='A' and (a2.co_emp!=a3.co_emp or a2.co_loc!=a3.co_loc or a2.co_tipdoc!=a3.co_tipdoc or a2.co_doc!=a3.co_doc) ) "
                + " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc and a1.co_reg=a2.co_reg) "
                + " INNER JOIN tbm_cabmovinv AS a on (a.co_emp=a1.co_emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc and a.co_doc=a1.co_doc) "                        
                + " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc
                + " and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc;
                
                rstLoc = stmLoc.executeQuery(strSql);
                while (rstLoc.next()) {
                    dblCan = rstLoc.getDouble("nd_can");
                    
                    if ( dblCan > 0 ) {
                        strSql = "UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salcom=nd_salcom-"+dblCan+" WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND " +
                        " co_itm="+rstLoc.getInt("co_itm")+" AND co_emprel="+intCodEmp;
                    } else {
                        strSql = "UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salven=nd_salven-"+dblCan+" WHERE co_emp="+rstLoc.getInt("co_emp")+" AND " +
                        " co_itm="+rstLoc.getInt("co_itm")+" AND co_emprel="+objZafParSis.getCodigoEmpresa();
                    }                    
                    stmLoc01.executeUpdate(strSql);
                }
                
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;               
                stmLoc01.close();
                stmLoc01=null;               
                
                blnRes = true;
            }
            
        }        
        catch (java.sql.SQLException e) { 
            blnRes = false;
            mostarErrorException(e);
        }        
        catch (Exception e) {
            blnRes = false;
            mostarErrorException(e);
        }        
        
        return blnRes;
    }
    
    public boolean eliminaRegRel(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
        boolean blnRes = false;
        java.sql.Statement stmLoc, stmLoc01;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();
                stmLoc01 = conn.createStatement();
                strSql = "SELECT DISTINCT a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc FROM tbm_detmovinv AS a3 "
                + " INNER JOIN tbr_detmovinv AS a2 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc and a2.co_tipdocrel=a3.co_tipdoc and a2.co_docrel=a3.co_doc and a2.co_regrel=a3.co_reg and a2.st_reg='A' and (a2.co_emp!=a3.co_emp or a2.co_loc!=a3.co_loc or a2.co_tipdoc!=a3.co_tipdoc or a2.co_doc!=a3.co_doc) ) "
                + " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc and a1.co_reg=a2.co_reg) "
                + " INNER JOIN tbm_cabmovinv AS a on (a.co_emp=a1.co_emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc and a.co_doc=a1.co_doc) "                        
                + " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc
                + " and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc
                + " and a3.co_tipdoc not in (205,206) ";
                
                rstLoc = stmLoc.executeQuery(strSql);
                while (rstLoc.next()) {
                    strSql = "DELETE FROM tbr_detMovInv"
                    + " WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc
                    + " AND co_emprel="+rstLoc.getInt("co_emp")+" AND co_locrel="+rstLoc.getInt("co_loc")+" AND co_tipdocrel="+rstLoc.getInt("co_tipdoc")+" AND co_docrel="+rstLoc.getInt("co_doc");
                    stmLoc01.executeUpdate(strSql);

                    strSql = "DELETE FROM tbm_detMovInv"
                    + " WHERE co_emp="+rstLoc.getInt("co_emp")+" AND co_loc="+rstLoc.getInt("co_loc")+" AND co_tipdoc="+rstLoc.getInt("co_tipdoc")+" AND co_doc="+rstLoc.getInt("co_doc");
                    stmLoc01.executeUpdate(strSql);

                    strSql = "DELETE FROM tbm_cabMovInv"
                    + " WHERE co_emp="+rstLoc.getInt("co_emp")+" AND co_loc="+rstLoc.getInt("co_loc")+" AND co_tipdoc="+rstLoc.getInt("co_tipdoc")+" AND co_doc="+rstLoc.getInt("co_doc");
                    stmLoc01.executeUpdate(strSql); 
                }
                
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;               
                stmLoc01.close();
                stmLoc01=null;               
                
                blnRes = true;
            }
            
        }        
        catch (java.sql.SQLException e) { 
            blnRes = false;
            mostarErrorException(e);
        }        
        catch (Exception e) {
            blnRes = false;
            mostarErrorException(e);
        }        
        
        return blnRes;
    }
    
    public boolean actualizaRegRel(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
        boolean blnRes = false;
        java.sql.Statement stmLoc, stmLoc01;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        double dblTot = 0;
        
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();
                stmLoc01 = conn.createStatement();
                strSql = "SELECT a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a3.co_reg, a3.nd_tot FROM tbm_detmovinv AS a3 "
                + " INNER JOIN tbr_detmovinv AS a2 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc and a2.co_tipdocrel=a3.co_tipdoc and a2.co_docrel=a3.co_doc and a2.co_regrel=a3.co_reg and a2.st_reg='A' and (a2.co_emp!=a3.co_emp or a2.co_loc!=a3.co_loc or a2.co_tipdoc!=a3.co_tipdoc or a2.co_doc!=a3.co_doc) ) "
                + " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc and a1.co_reg=a2.co_reg) "
                + " INNER JOIN tbm_cabmovinv AS a on (a.co_emp=a1.co_emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc and a.co_doc=a1.co_doc) "                        
                + " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc
                + " and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc
                + " and a3.co_tipdoc in (205,206) ";
                
                rstLoc = stmLoc.executeQuery(strSql);
                while (rstLoc.next()) {
                    strSql = "DELETE FROM tbr_detMovInv"
                    + " WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc
                    + " AND co_emprel="+rstLoc.getInt("co_emp")+" AND co_locrel="+rstLoc.getInt("co_loc")
                    + " AND co_tipdocrel="+rstLoc.getInt("co_tipdoc")+" AND co_docrel="+rstLoc.getInt("co_doc")
                    + " AND co_regrel="+rstLoc.getInt("co_reg");
                    stmLoc01.executeUpdate(strSql);

                    strSql = "DELETE FROM tbm_detMovInv"
                    + " WHERE co_emp="+rstLoc.getInt("co_emp")+" AND co_loc="+rstLoc.getInt("co_loc")
                    + " AND co_tipdoc="+rstLoc.getInt("co_tipdoc")+" AND co_doc="+rstLoc.getInt("co_doc")
                    + " AND co_reg="+rstLoc.getInt("co_reg");
                    stmLoc01.executeUpdate(strSql);
                    
                    dblTot = rstLoc.getDouble("nd_tot");
                    
                    strSql = "UPDATE tbm_cabMovInv SET nd_sub = nd_sub - "+dblTot+", nd_tot = nd_tot - "+dblTot
                    + " WHERE co_emp="+rstLoc.getInt("co_emp")+" AND co_loc="+rstLoc.getInt("co_loc")
                    + " AND co_tipdoc="+rstLoc.getInt("co_tipdoc")+" AND co_doc="+rstLoc.getInt("co_doc");
                    stmLoc01.executeUpdate(strSql);

                    strSql = "UPDATE tbm_pagMovInv SET mo_pag = mo_pag - "+dblTot
                    + " WHERE co_emp="+rstLoc.getInt("co_emp")+" AND co_loc="+rstLoc.getInt("co_loc")
                    + " AND co_tipdoc="+rstLoc.getInt("co_tipdoc")+" AND co_doc="+rstLoc.getInt("co_doc")
                    + " AND co_reg=1";
                    stmLoc01.executeUpdate(strSql);

                    strSql = "UPDATE tbm_detDia SET nd_mondeb = nd_mondeb - abs("+dblTot+")"
                    + " WHERE co_emp="+rstLoc.getInt("co_emp")+" AND co_loc="+rstLoc.getInt("co_loc")
                    + " AND co_tipdoc="+rstLoc.getInt("co_tipdoc")+" AND co_dia="+rstLoc.getInt("co_doc")
                    + " AND co_reg=1";
                    stmLoc01.executeUpdate(strSql);

                    strSql = "UPDATE tbm_detDia SET nd_monhab = nd_monhab - abs("+dblTot+")"
                    + " WHERE co_emp="+rstLoc.getInt("co_emp")+" AND co_loc="+rstLoc.getInt("co_loc")
                    + " AND co_tipdoc="+rstLoc.getInt("co_tipdoc")+" AND co_dia="+rstLoc.getInt("co_doc")
                    + " AND co_reg=2";                    
                    stmLoc01.executeUpdate(strSql);
                }
                
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;               
                stmLoc01.close();
                stmLoc01=null;               
                
                blnRes = true;
            }
            
        }        
        catch (java.sql.SQLException e) { 
            blnRes = false;
            mostarErrorException(e);
        }        
        catch (Exception e) {
            blnRes = false;
            mostarErrorException(e);
        }        
        
        return blnRes;
    }
    
    
    /**
    * EFLORESA - ENVIAR CORREOS A VARIAS DIRECCIONES AL MISMO TIEMPO.
    * @param strCorEleTo - Cuentas de correo a las que se enviara el email; estas deben estar separadas por ";". Ej: cuentaFicticia@tuvalsa.com; cuentaFicticia2@tuvalsa.com
    * @param subject - Asunto
    * @param strMsj - Cuerpo del mensaje de correo. 
    */
    public void enviarCorreoMasivo(String strCorEleTo, String subject, String strMsj ){
        try {

            String server = "mail.tuvalsa.com";
            String userName = "zafiro@tuvalsa.com";
            String password = "3Mjoafdfma!6MK7n";
            String fromAddres = "zafiro@tuvalsa.com";
            String toAddres = strCorEleTo;

            String cc = "";
            String bcc = "";
            boolean htmlFormat = false;
            String body = strMsj;

            sendMailTuvMas(server, userName, password, fromAddres, toAddres, cc, bcc, htmlFormat, subject, body);

        }catch (Exception e) {  
            e.printStackTrace();    
        } 
    }

    /**
    * EFLORESA - ENVIAR CORREOS A VARIAS DIRECCIONES AL MISMO TIEMPO.
    * @param strCorEleTo - Cuentas de correo a las que se enviara el email; estas deben estar separadas por ";". Ej: correoFicticio@tuvalsa.com; correoFicticio2@tuvalsa.com
    * @param strCorEleCC - Cuentas de correo a las que se enviara copia del email; estas deben estar separadas por ";". Ej: correoFicticio3@tuvalsa.com; correoFicticio4@tuvalsa.com
    * @param strCorEleCCO - Cuentas de correo a las que se enviara copia oculta del email; estas deben estar separadas por ";". Ej: correoOculto@tuvalsa.com; correoOculto2@tuvalsa.com
    * @param subject - Asunto
    * @param strMsj - Cuerpo del mensaje de correo. 
    */
    public void enviarCorreoMasivo(String strCorEleTo, String strCorEleCC, String strCorEleCCO, String subject, String strMsj ){
        try {

            String server = "mail.tuvalsa.com";
            String userName = "zafiro@tuvalsa.com";
            String password = "3Mjoafdfma!6MK7n";
            String fromAddres = "zafiro@tuvalsa.com";
            String toAddres = strCorEleTo;

            String cc = (strCorEleCC==null?"":(strCorEleCC.equals("")?"":strCorEleCC));
            String bcc = (strCorEleCCO==null?"":(strCorEleCCO.equals("")?"":strCorEleCCO));
            boolean htmlFormat = false;
            String body = strMsj;

            sendMailTuvMas(server, userName, password, fromAddres, toAddres, cc, bcc, htmlFormat, subject, body);

        }catch (Exception e) {  
            e.printStackTrace();    
        } 
    }

    public boolean sendMailTuvMas(String server, String userName, String password, String fromAddress, String toAddress, String ccAddress, String bccAddress, boolean htmlFormat, String subject, String body) {
        boolean blnRes=false;
        try{

           Properties props = System.getProperties();
//           props.put("mail.smtp.auth", "true");
//           props.put("mail.smtp.ssl.trust", server);
//           props.put("mail.smtp.port", "587");
//           props.put("mail.smtp.starttls.enable", "true");
           //System.out.println("---2---");
           props.put("mail.smtp.starttls.enable", "true");
           props.put("mail.smtp.ssl.trust", server);
           props.put("mail.smtp.host", server);
           props.put("mail.smtp.user", userName);
           props.put("mail.smtp.password", password);
           props.put("mail.smtp.port", "587");
           props.put("mail.smtp.auth", "true");  

            Authenticator auth = new MyAuthenticator();

            // Get session
            Session session = Session.getDefaultInstance(props, auth);
            session.setDebug(true);

            // Define message
            MimeMessage message = new MimeMessage(session);

            // Set the from address
            message.setFrom(new InternetAddress(fromAddress));

            // Set the to addresses
            StringTokenizer tokens = new StringTokenizer(toAddress, ";");
            while (tokens.hasMoreTokens())
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(tokens.nextToken()));
            tokens=null;
            
            if (! ccAddress.equals("")){
                tokens = new StringTokenizer(ccAddress, ";");
                while (tokens.hasMoreTokens())
                    message.addRecipient(Message.RecipientType.CC, new InternetAddress(tokens.nextToken()));
                tokens=null;
            }
            
            if (! bccAddress.equals("")){
                tokens = new StringTokenizer(bccAddress, ";");
                while (tokens.hasMoreTokens())
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(tokens.nextToken()));
                tokens=null;
            }

            // Set the subject
            message.setSubject(subject);

            MimeBodyPart  cuerpoCorreo = new MimeBodyPart();
            cuerpoCorreo.setContent(body, "text/html");

            MimeMultipart multipart= new MimeMultipart();
            multipart.addBodyPart(cuerpoCorreo);

            message.setContent(multipart);

            message.saveChanges();

            Transport.send(message);

            blnRes=true;

        }catch(MessagingException e){ 
            blnRes=false;  
            e.printStackTrace(); 
        }catch(Exception e){ 
            blnRes=false;  
            e.printStackTrace(); 
        }
        return blnRes;
    }
    
    public boolean enviarReqAlertaFaltantes(Connection conn, int intCodEmp, int intCodLoc ){
        boolean blnRes=true;
        PreparedStatement pstLoc=null;
        ResultSet rstLoc;
        String strSql="";
        int intPuerto=9001; 
        String strCodUsr="0";
        //{codEmp, codLoc, codUsr, strUsr, strGrp}
        String strUsrLoc[][] = { 
                                            //TUVAL
                                            {"1", "4", "7", "PSOLORZANO", "V"} ,
                                            {"1", "4", "8", "ACATTAN", "V"} ,
                                            {"1", "4", "123", "DRODRIGUEZ", "V"} ,
                                            {"1", "4", "144", "MDONOSO", "V"} ,
                                            {"1", "4", "148", "MPEÑAFIEL", "I"} ,
                                            //DIMULTI
                                            {"4", "3", "88", "EACOSTA", "V"} ,
                                            {"4", "3", "33", "GMOSQUERA", "V"} ,
                                            {"4", "3", "17", "PPEZO", "V"} ,
                                            {"4", "3", "92", "TMARTINEZ", "V"} ,
                                            {"4", "3", "15", "WMIRANDA", "V"} ,
                                            {"4", "3", "148", "MPEÑAFIEL", "I"} ,
                                            //CASTEK QUITO
                                            {"2", "1", "27", "MVILLALTA", "V"} ,
                                            {"2", "1", "41", "PPEREZ", "V"} ,
                                            {"2", "1", "149", "OTOMSICH", "V"} ,
                                            {"2", "1", "148", "MPEÑAFIEL", "I"} ,
                                            //CASTEK MANTA
                                            {"2", "4", "85", "PARCENTALES", "V"} ,
                                            {"2", "4", "58", "AZULETA", "V"} ,
                                            {"2", "4", "148", "MPEÑAFIEL", "I"} ,
                                            //CASTEK MANTA
                                            {"2", "6", "127", "JRAMIREZ", "V"} ,
                                            {"2", "6", "15", "CGRANDA", "V"} ,
                                            {"2", "6", "148", "MPEÑAFIEL", "I"} 
                                        };

        try{
            if(conn!=null){
                for (int i = 0; i < strUsrLoc.length; i++){
                    if (strUsrLoc[i][0].equals(String.valueOf(intCodEmp))) 
                        if (strUsrLoc[i][1].equals(String.valueOf(intCodLoc)) ) 
                            strCodUsr+=","+strUsrLoc[i][2];                     
                }        

                strSql= " select a.co_usr, a.tx_usr, c.tx_dirip "
                    + " from tbm_usr as a, tbr_locusr as b "
                    + " left outer join tbm_equusringsis as c on (c.co_usr=b.co_usr and c.tx_tipsis='E') "
                    + " where a.co_usr=b.co_usr"
                    + " and b.st_reg='P' "
                    + " and a.st_reg='A' "
                    + " and b.co_emp="+ intCodEmp +" "
                    + " and b.co_loc="+ intCodLoc +" "
                    + " and a.co_usr in ("+ strCodUsr + ")";
                
                pstLoc=conn.prepareStatement(strSql);
                rstLoc=pstLoc.executeQuery();
                while(rstLoc.next()){
                    enviarRequisitoAlerta( rstLoc.getString("tx_dirip"), intPuerto,  rstLoc.getInt("co_usr"));
                }

                if (rstLoc!=null) 
                    rstLoc.close();
                rstLoc=null;
                if (pstLoc!=null) 
                    pstLoc.close();
                pstLoc=null;
            }
        }catch(java.sql.SQLException e) { 
            blnRes=false;  
            mostarErrorException(e);  
        }catch(Exception  e) { 
            blnRes=false;  
            mostarErrorException(e);  
        }
        return blnRes;
    }

    public void enviarRequisitoAlerta(String strIp, int intPuerto, int intCodUsr){
        try{
            ZafThreadEnvAlertaFaltantes objHilo = new ZafThreadEnvAlertaFaltantes(strIp, intPuerto, intCodUsr);
            objHilo.start();
            objHilo=null;
        }catch (Exception e){  
            mostarErrorException(e); 
        }
    }

    private class ZafThreadEnvAlertaFaltantes extends Thread{
        String strIpS="";
        int intPuertoS=0;
        int intCodUsr=0;
        String strTipMensFacGui="";
        
        public ZafThreadEnvAlertaFaltantes(String strIp, int intPuerto) {
            strIpS=strIp;
            intPuertoS= intPuerto;
        }
        
        public ZafThreadEnvAlertaFaltantes(String strIp, int intPuerto, int intCodUsr) {
            strIpS=strIp;
            intPuertoS= intPuerto;
            this.intCodUsr=intCodUsr;
        }
        
        @Override
        public void run() {
            try{
                Socket s1 = new Socket(strIpS, intPuertoS);
                DataOutputStream dos = new DataOutputStream(s1.getOutputStream());
                String strMens=String.valueOf(this.intCodUsr);
                dos.writeUTF(strMens);
                dos.close();
                s1.close();
            }catch (ConnectException e){   
                System.err.println("OCURRIO UN ERROR 1 "+ e ); 
            }catch (IOException e){  
                System.err.println("OCURRIO UN ERROR 2 "+ e );  
            }catch (Exception e){  
                System.err.println("OCURRIO UN ERROR 3 "+ e );  
            }
        }
    }

}
