/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.  ne_numguides
 */

package Ventas.ZafVen25;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import ZafReglas.ZafGuiRemDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author jayapata
 */
public class ZafVen25_02 {
 private ZafParSis objZafParSis;
 private ZafUtil objUti;
 private JInternalFrame jIntfra=null;
 private boolean blnIsSegundaDev=false;
 private boolean blnIsCosenco=false,blnIsEcuatosa=false,blnIsDetopacio=false;
            //Saber si la empresa que ingreso es COSENCO
            

  /** Creates a new instance of ZafInv */
    public ZafVen25_02(JInternalFrame jfrthis, ZafParSis obj){
      try{
            this.objZafParSis = (ZafParSis) obj.clone();
            jIntfra = jfrthis;
            objUti = new ZafUtil();
            blnIsSegundaDev=false;         
            blnIsCosenco = (objZafParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO") > -1)?true:false;  // Josémario 3/Feb/2016
            blnIsEcuatosa = (objZafParSis.getNombreEmpresa().toUpperCase().indexOf("ECUATOSA") > -1)?true:false;
            blnIsDetopacio = (objZafParSis.getNombreEmpresa().toUpperCase().indexOf("DETOPACIO") > -1)?true:false;
        }catch (CloneNotSupportedException e){ 
            objUti.mostrarMsgErr_F1(jfrthis, e);  
        }
    }

/**
 * Autoriza Solicitud de devolucion por via cantidad y descuento.
 * @param conn
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @param intCodDoc
 * @param strObs
 * @return  true =  todo bien <br> false = algun problema.
 */
public boolean autorizadoSolCanDes(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strObs ){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
     System.out.println("ZafVen25_02.autorizadoSolCanDes");
   if(conn!=null){
      stmLoc=conn.createStatement();

      strSql="UPDATE tbm_cabsoldevven SET  co_usrAut="+objZafParSis.getCodigoUsuario()+", tx_comAut='"+objZafParSis.getNombreComputadoraConDirIP()+"', "
      + " st_aut='A', tx_obsaut='"+strObs+"', fe_aut="+objZafParSis.getFuncionFechaHoraBaseDatos()+" "
      + " WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc;
      System.out.println("ZafVen25_02.autorizadoSolCanDes: "+ strSql );
      stmLoc.executeUpdate(strSql);

     stmLoc.close();
     stmLoc=null;

  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt); }
 return blnRes;
}

/**
 * Permite saber si la guia esta impresa bajo el esquema anterior
 * @param conn
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @param intCodDoc
 * @return  0 = Error problema en clase <br>  1 = Trabaja con el esquema anterior <br> 2 = trabaja con el esquema actual.
 */
public int _getImpGui(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 int intRes=0;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
     System.out.println("ZafVen25_02._getImpGui");
    if(conn!=null){
         stmLoc=conn.createStatement();
         strSql="SELECT * \nFROM (\n "
         + " SELECT a3.ne_numdoc \n FROM tbm_cabsoldevven as a \n"
         + " \nINNER JOIN tbm_cabmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel )  "
         + " \nINNER JOIN tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc )  "
         + " \nINNER JOIN tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc )  "
         + " \nWHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+" "
         + " \nGROUP BY a3.ne_numdoc "
         + " \n) AS x \nWHERE ne_numdoc >  0   ";  
         System.out.println("ZafVen25_02._getImpGui: " + strSql);
         rstLoc=stmLoc.executeQuery(strSql);
         if(rstLoc.next()){
            intRes=1;
         }else intRes=2;
         rstLoc.close();
         rstLoc=null;
         stmLoc.close();
         stmLoc=null;
   }
 }
 catch(java.sql.SQLException Evt) { 
     intRes=0; 
     objUti.mostrarMsgErr_F1(jIntfra, Evt);  
 }
 catch(Exception Evt) { 
     intRes=0; 
     objUti.mostrarMsgErr_F1(jIntfra, Evt); 
 }
 return intRes;
}

/**
 * Permite verificar si la sol.dev.ven .  hay mercaderi que egreso fis. de bodega, si no hay nada que egresa directamente se marca
 * estado para que bodega no confirme y pase directamente a contabilidad.
 * @param conn
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @param intCodDoc
 * @return  true = todo bien <br>  false = problema.
 * 
 * 2012-01-30 EFLORESA
 * Se corrige condicion, caso en que la totalidad de los items de la solicitud se volveran a facturar; motivo por el cual
 * pasa directamente a contabilidad y bodega no tiene que confirmar nada
 */
public boolean _getVerificaItmEgrFisBod(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 boolean blnRes=false;
Statement stmLoc;
ResultSet rstLoc;
 String strSql="";
 try{
     System.out.println("ZafVen25_02._getVerificaItmEgrFisBod");
   if(conn!=null){
      stmLoc=conn.createStatement();

      /*
      strSql="SELECT a.co_doc  FROM tbm_detsoldevven as a "
      + " INNER JOIN tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel )  "
      + " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+"  "
      + " and a.nd_canDev > 0  and case when a1.st_meringegrfisbod='N' then  a.nd_cannodevprv  > 0 else a1.st_meringegrfisbod='S' end  "
      + " GROUP BY a.co_doc  ";
      */
      
      strSql = " select a.co_doc, a.st_volFacMerSinDev, sum(b.nd_can) as nd_can, sum(b.nd_canDev) as nd_canDev, sum(b.nd_canVolFac) as nd_canVolFac, round((sum(b.nd_canDev) - sum(b.nd_canVolFac)),0) as nd_cannovuefac,  "
             + " round((sum(b.nd_canDev) - sum(b.nd_canVolFac) - sum(b.nd_canDevprv)),0) as nd_canDevprvterl "
             + " from tbm_cabsoldevven as a, tbm_detsoldevven as b, tbm_detmovinv as c "
             + " where a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc "
             + " and b.co_emp=c.co_emp and b.co_locrel= c.co_loc and b.co_tipdocrel= c.co_tipdoc and b.co_docrel= c.co_doc and b.co_regrel= c.co_reg "
             + " and a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+"  "
             + " and b.nd_canDev > 0 "
             + " and case when c.st_meringegrfisbod='N' then b.nd_cannodevprv  > 0 else c.st_meringegrfisbod='S' end "
             + " group by a.co_doc, a.st_volFacMerSinDev ";
      
      System.out.println("ZafVen25_02._getVerificaItmEgrFisBod: " + strSql);
      
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){          
//          if (rstLoc.getString("st_volFacMerSinDev").equals("S") && rstLoc.getInt("nd_cannovuefac") == 0){
//            // TODOS LOS ITEM'S SE VAN A VOLVER A FACTURAR POR TANTO NO CONFIRMA BODEGA ->  CONTABILIDAD DIRECTAMENTE.
//            strSql=" UPDATE tbm_cabsoldevven SET st_impguiremaut='N' WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" ";
//            stmLoc.executeUpdate(strSql);
//            blnRes=true;
//          }else          
//            blnRes=true;  // HAY MERCADERIA QUE BODEGA TIENE QUE CONFIRMAR CASO TER. L  CUANDO SE QUEDA EN STOCK
          
          if(rstLoc.getString("st_volFacMerSinDev").equals("S") ) {
              if ( rstLoc.getInt("nd_cannovuefac") == 0 ) {
                  // TODOS LOS ITEM'S SE VAN A VOLVER A FACTURAR POR TANTO NO CONFIRMA BODEGA ->  CONTABILIDAD DIRECTAMENTE.
                  strSql=" UPDATE tbm_cabsoldevven SET st_impguiremaut='N' WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" ";
                  System.out.println("ZafVen25_02._getVerificaItmEgrFisBod (TODOS LOS ITEM'S SE VAN A VOLVER A FACTURAR POR TANTO NO CONFIRMA BODEGA ->  CONTABILIDAD DIRECTAMENTE.): \n"+ strSql );
                  stmLoc.executeUpdate(strSql);
                  blnRes=true;                  
              } else {
                  if ( rstLoc.getInt("nd_canDevprvterl") == 0 ) {  
                      // NADA REGRESA AL PROVEEDOR st_impGuiRemAut='N' 
                      strSql=" UPDATE tbm_cabsoldevven SET st_impguiremaut='N' WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" ";
                      System.out.println("ZafVen25_02._getVerificaItmEgrFisBod (3) NADA REGRESA AL PROVEEDOR : \n"+ strSql );
                      stmLoc.executeUpdate(strSql);
                      blnRes=true;                      
                  } else {
                      blnRes=true;  // HAY MERCADERIA QUE BODEGA TIENE QUE CONFIRMAR CASO TER. L  CUANDO SE QUEDA EN STOCK
                  }
              }
          }else          
            blnRes=true;  // HAY MERCADERIA QUE BODEGA TIENE QUE CONFIRMAR CASO TER. L  CUANDO SE QUEDA EN STOCK
          
      }
      else{
         // EL ITEM ES TERMINAL L Ó  DE SERVICIO POR TANTO NO CONFIRMA BODEGA ->  CONTABILIDAD DIRECTAMENTE.
         strSql=" UPDATE tbm_cabsoldevven SET st_impguiremaut='N' WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" ";
         System.out.println("ZafVen25_02._getVerificaItmEgrFisBod (EL ITEM ES TERMINAL L Ó  DE SERVICIO POR TANTO NO CONFIRMA BODEGA ->  CONTABILIDAD DIRECTAMENTE.): \n"+ strSql );
         stmLoc.executeUpdate(strSql);
         blnRes=true;
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
  }
 }catch(SQLException Evt) {  
     blnRes=false; 
     objUti.mostrarMsgErr_F1(jIntfra, Evt);  
 }catch(Exception Evt) { 
     blnRes=false; 
     objUti.mostrarMsgErr_F1(jIntfra, Evt); 
 }
 return blnRes;
}

/**
 * Permite saber si la orden de despacho esta impreso( asignado numero )
 * @param conn
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @param intCodDoc
 * @return  0 =  Error <br>  1 = Esta impreso la O.D Asignado Numero <br> 2 = No esta asignado numero. 
 * @return  = No genero O.D por lo tanto debe pasar directamente a contabilidad
 * 
 * MODIFICADO EFLORESA 2012-05-29
 * DEVOLUCION DE LA SEGUNDA FACTURA.
 */
public int _getImpOrdDes(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 int intRes=0;
 Statement stmLoc;
 ResultSet rstLoc;
 String strSql="";
 blnIsSegundaDev=false;
 try{
   if(conn!=null){
       stmLoc=conn.createStatement();
   
        strSql="SELECT * FROM ( "
        + " SELECT case when a3.ne_numorddes is null then 0 else a3.ne_numorddes  end as numorddes   FROM tbm_cabsoldevven as a "
        + " INNER JOIN tbm_cabmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel )  "
        + " INNER JOIN tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc )  "
        + " INNER JOIN tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc )  "
        + " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+" "
        + " GROUP BY a3.ne_numorddes "
        + " ) AS x WHERE numorddes > 0 "
        
        /*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
        
        + " union \n"
        + " select a3.ne_numorddes \n"
        + " FROM tbm_cabsoldevven as a \n"
        + " INNER JOIN tbm_cabmovinv as a1 \n"
        + " on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel ) \n"
        + " inner join tbm_cabsegmovinv as s \n"
        + " on (s.co_emprelcabmovinv =a1.co_emp and s.co_locrelcabmovinv=a1.co_loc and s.co_tipdocrelcabmovinv=a1.co_tipdoc and s.co_docrelcabmovinv=a1.co_doc) \n"
        + " inner join tbm_cabsegmovinv as s2 \n"
        + " on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271) \n"
        + " INNER JOIN tbm_cabguirem as a3 \n"
        + " on (a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem and a1.ne_numorddes=a3.ne_numorddes) \n"
        + " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" \n"
        + " and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" \n";
        
        
        /*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
        
        
        
        System.out.println("ZafVen25_02._getImpOrdDes:(1) " + strSql);
        rstLoc=stmLoc.executeQuery(strSql);
        if(rstLoc.next()) 
            intRes=1;  // OD IMPRESA tiene numero <JM> 
        else{
		/*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
            strSql=" select case when sum(x.numorddes) is null then sum(x.numorddes)  else sum(x.numorddes) end  as numorddes\n" 
                    +"from("
                    +" SELECT case when a3.ne_numorddes is null then 0 else a3.ne_numorddes end as numorddes "
		/*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
            + " FROM tbm_cabsoldevven as a "
            + " INNER JOIN tbr_cabmovinv as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_locrel and a4.co_tipdoc=a.co_tipdocrel and a4.co_doc=a.co_docrel )  "
            + " INNER JOIN tbr_cabmovinv as a5 on (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_locrel and a5.co_tipdoc=a4.co_tipdocrel and a5.co_doc=a4.co_docrel )  "
            + " INNER JOIN tbm_cabmovinv as a1 on (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_locrel and a1.co_tipdoc=a5.co_tipdocrel and a1.co_doc=a5.co_docrel )  "
            + " LEFT OUTER JOIN tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc )  "
            + " LEFT OUTER JOIN tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc )  "
            + " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+" "
            + " GROUP BY a3.ne_numorddes "
			/*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
            +" union "

            + " SELECT distinct case when a3.ne_numorddes is null then 0 else a3.ne_numorddes end as numorddes \n"
            +" FROM tbm_cabsoldevven as a  \n"
            +" INNER JOIN tbr_cabmovinv as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_locrel and a4.co_tipdoc=a.co_tipdocrel and a4.co_doc=a.co_docrel ) \n"
            +" INNER JOIN tbr_cabmovinv as a5 on (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_locrel and a5.co_tipdoc=a4.co_tipdocrel and a5.co_doc=a4.co_docrel ) \n"
            +" INNER JOIN tbm_cabmovinv as a1 on (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_locrel and a1.co_tipdoc=a5.co_tipdocrel and a1.co_doc=a5.co_docrel ) \n"
            +" INNER JOIN tbm_cabsegmovinv as s \n"
            +" on (s.co_emprelcabmovinv =a1.co_emp and s.co_locrelcabmovinv=a1.co_loc and s.co_tipdocrelcabmovinv=a1.co_tipdoc and s.co_docrelcabmovinv=a1.co_doc) \n"
            +" INNER JOIN tbm_cabsegmovinv as s2 \n"    
            +" on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271) \n"
            +" INNER JOIN tbm_cabguirem as a3   \n"
            +" on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem )   \n"
            +" INNER JOIN tbm_detguirem as detguirem   \n"
            +" on(detguirem.co_emp=a3.co_emp and detguirem.co_loc=a3.co_loc and detguirem.co_tipdoc=a3.co_tipdoc and detguirem.co_doc=a3.co_doc )  \n"
            +" INNER JOIN tbm_detmovinv as detres   \n"
            +" on (detres.co_emp=detguirem.co_emprel  and detres.co_loc=detguirem.co_locrel  and detres.co_tipdoc=detguirem.co_tipdocrel and detres.co_doc=detguirem.co_docrel and detres.co_reg=detguirem.co_regrel and detres.co_tipdoc<>228 and detres.co_tipdoc=294)  \n" 
            +" WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+" )as x  \n"                   
            +" HAVING sum(x.numorddes) IS NOT NULL"
            ; 
            /*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
            System.out.println("ZafVen25_02._getImpOrdDes: (2) " + strSql);
            rstLoc=stmLoc.executeQuery(strSql);
            if(rstLoc.next()) {                
                if (objUti.redondear(rstLoc.getDouble("numorddes"), 0) > 0) 
                    intRes=1;
                else
                    intRes=3;  
                blnIsSegundaDev=true;
            }else 
                intRes=2;  // NO TIENE ORDEN DE DESPACHO
        }
        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;
  }
 }catch(SQLException Evt) { 
     intRes=0; 
     objUti.mostrarMsgErr_F1(jIntfra, Evt);  
 }catch(Exception Evt) { 
     intRes=0; 
     objUti.mostrarMsgErr_F1(jIntfra, Evt); 
 }
 return intRes;
}

/**
 * Anula la orden de despacho ( parcial ó todo ) si es parcial geneta nueva orden de despacho por saldo
 * @param conn
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @param intCodDoc
 * @return  true = todo bien <br> false = problema error.
 * 
 * CUANDO LA O/D NO ESTA IMPRESA Y NO SE HAN HECHO DESPACHOS SE ANULA LA ORDEN, 
 * SE GENERA CONFIRMACION DE CANTIDAD NUNCA RECIBIDA Y SE PASA A CONTABILIDAD.
 */
public boolean _anularOrdDesGenODNueSal(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 boolean blnRes=false;
 Statement stmLoc, stmLoc01, stmLoc02;
 ResultSet rstLoc, rstLoc01;
 String strSql="", strSql01="";
 try{
     System.out.println("ZafVen25_02._anularOrdDesGenODNueSal"); 
   if(conn!=null){
      stmLoc=conn.createStatement();
      stmLoc01=conn.createStatement();
      stmLoc02=conn.createStatement();

      /*strSql="SELECT * FROM ( "
      + " SELECT a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc FROM tbm_cabsoldevven as a  "
      + " INNER JOIN tbm_cabmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel )  "
      + " INNER JOIN tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc )  "
      + " INNER JOIN tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc )   "
      + " WHERE a.co_emp="+intCodEmp+"  and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+" "
      + " GROUP BY a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc "
      + " ) AS x  ";*/
      
      if (blnIsSegundaDev){
        strSql="SELECT * FROM ( "
        + " SELECT a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a1.ne_numdoc, a.st_volfacmersindev as volfac "
        + " FROM tbm_cabsoldevven as a  "
        + " INNER JOIN tbr_cabmovinv as a5 on (a5.co_emp=a.co_emp and a5.co_loc=a.co_locrel and a5.co_tipdoc=a.co_tipdocrel and a5.co_doc=a.co_docrel ) "
        + " INNER JOIN tbr_cabmovinv as a6 on (a6.co_emp=a5.co_emp and a6.co_loc=a5.co_locrel and a6.co_tipdoc=a5.co_tipdocrel and a6.co_doc=a5.co_docrel )  "
        + " INNER JOIN tbm_cabmovinv as a1 on (a1.co_emp=a6.co_emp and a1.co_loc=a6.co_locrel and a1.co_tipdoc=a6.co_tipdocrel and a1.co_doc=a6.co_docrel )  "
        + " LEFT OUTER JOIN tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc )  "
        + " LEFT OUTER JOIN tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc )   "
        + " WHERE a.co_emp="+intCodEmp+"  and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+" "
        + " GROUP BY a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a1.ne_numdoc, a.st_volfacmersindev "
        + " ) AS x  ";
      }else{              
            if(blnIsCosenco || blnIsEcuatosa ||blnIsDetopacio){  // COSENCO JoséMario 
                strSql= " select  volfac, can, canDev, canVolFac, canNoVolFac  \n"; 
                strSql+=" from (   \n"; 
                strSql+="          select   a.st_volfacmersindev as volfac, sum(abs(a4.nd_can)) as can, sum(abs(a4.nd_canDev)) as CANDEV,  \n"; 
                strSql+="                  sum(abs(a4.nd_canVolFac)) as canVolFac, sum(a4.nd_canDev - a4.nd_canVolFac) as CANNOVOLFAC  \n"; 
                strSql+="          from tbm_cabsoldevven as a \n"; 
                strSql+="          inner join tbm_detsoldevven as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoc and a4.co_doc=a.co_doc  )  \n"; 
                strSql+="          where a.co_emp="+intCodEmp+"  and a.co_loc="+intCodLoc+"  and a.co_tipdoc="+intCodTipDoc+"  and a.co_doc="+intCodDoc+"  \n"; 
                strSql+="          group by a.st_volfacmersindev \n" ;
                strSql+=") as x  \n"; 
           }
           else{
                strSql="SELECT * FROM ( "
                + " SELECT a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a1.ne_numdoc, a.st_volfacmersindev as volfac "
                + " FROM tbm_cabsoldevven as a  "
                + " INNER JOIN tbm_cabmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel )  "
                + " INNER JOIN tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc )  "
                + " INNER JOIN tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc )   "
                + " WHERE a.co_emp="+intCodEmp+"  and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+" "
                + " GROUP BY a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a1.ne_numdoc, a.st_volfacmersindev "
				/*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
                + " union "

                + " SELECT a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a1.ne_numdoc, a.st_volfacmersindev as volfac "
                + " from tbm_cabsoldevven as a "
                + " inner join tbm_detsoldevven as b "
                + " on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc) "
                + " inner join tbm_cabmovinv as a1 "
                + " on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel ) "
                + " inner join tbm_cabsegmovinv as s "
                + " on (s.co_emprelcabmovinv=a1.co_emp and s.co_locrelcabmovinv=a1.co_loc and s.co_tipdocrelcabmovinv=a1.co_tipdoc and s.co_docrelcabmovinv=a1.co_doc) "
                + " inner join tbm_cabsegmovinv as s2 "
                + " on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271) "
                + " INNER JOIN tbm_cabguirem as a3  "
                + " on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem and a1.ne_numorddes=a3.ne_numorddes) "
                + " WHERE a.co_emp="+intCodEmp+"  and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+" "                        
                + " GROUP BY a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a1.ne_numdoc, a.st_volfacmersindev "
                /*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/        
                        
                + " ) AS x  ";
            }        
      }
      System.out.println("ZafVen25_02._anularOrdDesGenODNueSal: " + strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
           if(blnIsCosenco || blnIsEcuatosa ||blnIsDetopacio){  // COSENCO JoséMario 
               System.out.println("Cosenco no hay guias OD Naaaa ");
           }
           else{
                Double dblValPen=0.00; //José Marín M. 10/Sep/2014
                ZafGuiRemDAO dao=new ZafGuiRemDAO();
                int intTipOD=dao.obtenerTipDocOD(conn, rstLoc.getInt("co_emp"));
                if(intTipOD!=rstLoc.getInt("co_tipdoc")){
                    strSql01="";
                    strSql01+=" SELECT *  \n";
                    strSql01+=" FROM (  \n";
                    strSql01+="      SELECT a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.nd_can - (a2.nd_canCon+a2.nd_canNunRec) as canPen\n";
                    strSql01+="      FROM tbm_cabsoldevven as a \n";
                    strSql01+="      INNER JOIN tbm_cabmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel )   \n";
                    strSql01+="      INNER JOIN tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc ) \n";
                    strSql01+="      INNER JOIN tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc )\n";
                    strSql01+="      WHERE a.co_emp="+intCodEmp+"  and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+" ";
                    /*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
                    strSql01+=" union \n";
                    
                    strSql01+=" SELECT a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, a4.nd_can - (a4.nd_canCon+a4.nd_canNunRec) as canPen \n";
                    strSql01+=" from tbm_cabsoldevven as a \n";
                    strSql01+=" inner join tbm_detsoldevven as b \n";
                    strSql01+=" on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc) \n";
                    strSql01+=" inner join tbm_cabmovinv as a1 \n";
                    strSql01+=" on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel ) \n";
                    strSql01+=" inner join tbm_cabsegmovinv as s \n";
                    strSql01+=" on (s.co_emprelcabmovinv=a1.co_emp and s.co_locrelcabmovinv=a1.co_loc and s.co_tipdocrelcabmovinv=a1.co_tipdoc and s.co_docrelcabmovinv=a1.co_doc) \n";
                    strSql01+=" inner join tbm_cabsegmovinv as s2 \n";
                    strSql01+=" on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271) \n";
                    strSql01+=" INNER JOIN tbm_cabguirem as a3  \n";
                    strSql01+=" on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem and a1.ne_numorddes=a3.ne_numorddes) \n";
                    strSql01+=" INNER JOIN tbm_detguirem as a4 \n";
                    strSql01+=" on(a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc and /*a4.co_reg=b.co_reg*/a4.co_itm=b.co_itm) \n";
                    strSql01+=" INNER JOIN tbm_detmovinv as detres \n";
                    strSql01+=" on (detres.co_emp=a4.co_emprel  and detres.co_loc=a4.co_locrel  and detres.co_tipdoc=a4.co_tipdocrel and detres.co_doc=a4.co_docrel and detres.co_reg=a4.co_regrel and detres.co_tipdoc<>228) \n";
                    /*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
                    strSql01+=") AS x   \n";
                    strSql01+="  \n";
                    System.out.println("ZafVen25_02.autorizadoSolCanDes (1) \n" + strSql01);
                }else{
                    strSql01="";
                    strSql01+=" SELECT *  \n";
                    strSql01+=" FROM (  \n";
                    strSql01+="      SELECT a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.nd_can - (d.nd_canCon+d.nd_canNunRec) as canPen\n";
                    strSql01+="      FROM tbm_cabsoldevven as a \n";
                    strSql01+="      INNER JOIN tbm_detsoldevven as dv on (a.co_emp=dv.co_emp and a.co_loc=dv.co_loc and a.co_tipdoc=dv.co_tipdoc and a.co_doc=dv.co_doc) \n";
                    strSql01+="      INNER JOIN tbm_detmovinv as d on (dv.co_emp=d.co_emp and dv.co_locrel=d.co_loc and dv.co_tipdocrel=d.co_tipdoc and dv.co_docrel=d.co_doc and dv.co_regrel=d.co_reg)           \n";
                    strSql01+="      INNER JOIN tbm_detguirem as a2 on (a2.co_emprel=d.co_emp and a2.co_locrel=d.co_loc and a2.co_tipdocrel=d.co_tipdoc and a2.co_docrel=d.co_doc and a2.co_regrel=d.co_reg)  \n";
                    strSql01+="      INNER JOIN tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc )\n";
                    strSql01+="      WHERE a.co_emp="+intCodEmp+"  and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+" ";
                    
                    /*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
                    strSql01+=" union \n";
                    
		    strSql01+=" SELECT a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, a4.nd_can - (detres.nd_canCon+detres.nd_canNunRec) as canPen \n";
                    strSql01+=" from tbm_cabsoldevven as a \n";
                    strSql01+=" inner join tbm_detsoldevven as b \n";
                    strSql01+=" on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc) \n";
                    strSql01+=" inner join tbm_cabmovinv as a1 \n";
                    strSql01+=" on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel ) \n";
                    strSql01+=" inner join tbm_cabsegmovinv as s \n";
                    strSql01+=" on (s.co_emprelcabmovinv=a1.co_emp and s.co_locrelcabmovinv=a1.co_loc and s.co_tipdocrelcabmovinv=a1.co_tipdoc and s.co_docrelcabmovinv=a1.co_doc) \n";
                    strSql01+=" inner join tbm_cabsegmovinv as s2 \n";
                    strSql01+=" on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271) \n";
                    strSql01+=" INNER JOIN tbm_cabguirem as a3  \n";
                    strSql01+=" on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem and a1.ne_numorddes=a3.ne_numorddes) \n";
                    strSql01+=" INNER JOIN tbm_detguirem as a4 \n";
                    strSql01+=" on(a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc and /*a4.co_reg=b.co_reg*/a4.co_itm=b.co_itm) \n";
                    strSql01+=" INNER JOIN tbm_detmovinv as detres \n";
                    strSql01+=" on (detres.co_emp=a4.co_emprel  and detres.co_loc=a4.co_locrel  and detres.co_tipdoc=a4.co_tipdocrel and detres.co_doc=a4.co_docrel and detres.co_reg=a4.co_regrel and detres.co_tipdoc<>228) \n";
                    strSql01+=" WHERE a.co_emp="+intCodEmp+"  and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+" ";                    
					/*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
                    strSql01+=") AS x   \n";
                    strSql01+="  \n";
                    System.out.println("ZafVen25_02.autorizadoSolCanDes (1) \n" + strSql01);                
                }
                rstLoc01=stmLoc02.executeQuery(strSql01);
                while(rstLoc01.next()){
                    dblValPen+=Double.parseDouble(rstLoc01.getString("canPen").toString());
                }  // SI NO HAY NADA PENDIENTE SERA 0

                if(dblValPen==0){ //José Marín M. 10/Sep/2014
                    if (rstLoc.getString("volfac").equals("N")) {  
                      strSql=" UPDATE tbm_cabguirem " +
                              " SET st_reg='I', st_regrep='M', fe_ultmod=current_timestamp, " +
                              " tx_obs1='ANULADO POR DEVOLUCION DE LA FACTURA# " + rstLoc.getString("ne_numdoc") + "', " +
                              " co_usrmod= "+ objZafParSis.getCodigoUsuario() + ", " +
                              " tx_comultmod=" + objUti.codificar(objZafParSis.getNombreComputadoraConDirIP()) + " " +
                              " where co_emp="+rstLoc.getString("co_emp") + 
                              " and co_loc="+rstLoc.getString("co_loc") +
                              " and co_tipdoc="+rstLoc.getString("co_tipdoc") +
                              " and co_doc="+rstLoc.getString("co_doc");
                      System.out.println("ZafVen25_02._anularOrdDesGenODNueSal (2): "+ strSql );
                      stmLoc01.executeUpdate(strSql);
                    }
                }
           }
          

          if (_getVerGenConf(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)) 
              blnRes=true; 
          else 
              blnRes=false;
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;
 }
 }catch(SQLException Evt) {  
     blnRes=false; 
     objUti.mostrarMsgErr_F1(jIntfra, Evt);  
 }catch(Exception Evt) { 
     blnRes=false; 
     objUti.mostrarMsgErr_F1(jIntfra, Evt); 
 }
 return blnRes;
}

private boolean _verificarGenNueGuia(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
  try{
    if(conn!=null){
        stmLoc=conn.createStatement();

        strSql="select * from ( "
        + " select a.nd_can, a.nd_canDev, a2.nd_can as cangui, (a2.nd_can-a.nd_canDev) as cansal "
        + " ,a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc "
        + " from tbm_detsoldevven as a "
        + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp  and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
        + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg ) "
        + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+"  and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+" "
        + " ) as x  where  cansal > 0  ";

        System.out.println("ZafVen25_02._verificarGenNueGuia: " + strSql);

        rstLoc=stmLoc.executeQuery(strSql);
        if(rstLoc.next()){

            if(!_generarNueGuiRem( conn, intCodEmp,  intCodLoc,  intCodTipDoc,  intCodDoc, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc")  )){
                blnRes=false;
            }

        }
        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;
    }
  }
  catch(java.sql.SQLException Evt) {  
      blnRes=false; 
      objUti.mostrarMsgErr_F1(jIntfra, Evt);  
  }
  catch(Exception Evt) { 
      blnRes=false; 
      objUti.mostrarMsgErr_F1(jIntfra, Evt); 
  }
 return blnRes;
}

private boolean _generarNueGuiRem(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpG, int intCodLocG, int intCodTipDocG, int intCodDocG ){
 boolean blnRes=false;
 int intCodDocNueGui=0;
 try{
     System.out.println("ZafVen25_02._generaNueGuiRem");
    if(conn!=null){

     intCodDocNueGui=_getCodDocConfEgre( conn, intCodEmpG, intCodLocG, intCodTipDocG, "tbm_cabguirem" );

  if(_insertarCabGuiRem(conn, intCodEmpG, intCodLocG, intCodTipDocG, intCodDocG, intCodDocNueGui  ) ){
        if(_insertarDetGuiRem(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodDocNueGui  ) ){
            blnRes=true;
        }else conn.rollback();
  }else conn.rollback();


 }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt); }
 return blnRes;
}

private boolean _insertarCabGuiRem(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodDocNueGui ){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc;
 String strSql="", strFecSis="";
  try{
      System.out.println("ZafVen25_02._insertarCabGuiRem");
    if(conn!=null){
      stmLoc=conn.createStatement();
      stmLoc01=conn.createStatement();

      strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaBaseDatos());

      strSql="select * from tbm_cabguirem where co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and  co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" ";
      System.out.println("ZafVen25_02.insertarCabGuiRem " + strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){

       strSql=" INSERT INTO tbm_cabguirem (co_emp, co_loc, co_tipdoc, co_doc, fe_doc, fe_initra, fe_tertra, " +
              " ne_numdoc, tx_ptopar, co_clides, tx_rucclides, tx_nomclides, tx_dirclides, tx_telclides,  " +
              " tx_ciuclides, nd_pestotkgr, st_imp, tx_obs1, tx_obs2, st_reg, fe_ing, " +
              " fe_ultmod, co_usring, co_usrmod, tx_coming, tx_comultmod, co_ptopar, st_tipGuiRem, tx_datdocoriguirem " +
              " ,co_forRet,tx_vehRet,tx_choRet, co_ven, tx_nomven, st_aut  ) " +
              " VALUES ("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", " +
              " "+intCodDocNueGui+", '"+strFecSis+"', '"+strFecSis+"', '"+strFecSis+"', " +
              " 0, '"+rstLoc.getString("tx_ptopar")+"', "+rstLoc.getString("co_clides")+", '"+rstLoc.getString("tx_rucclides")+"', '"+rstLoc.getString("tx_nomclides")+"', " +
              " '"+rstLoc.getString("tx_dirclides")+"', '"+rstLoc.getString("tx_telclides")+"', '"+rstLoc.getString("tx_ciuclides")+"', " +
              " "+rstLoc.getString("nd_pestotkgr")+", 'N', 'Genera nueva guia por solicitud de devolucion solo lo pendiente', '"+rstLoc.getString("tx_obs2")+"','A', "+objZafParSis.getFuncionFechaHoraBaseDatos()+", " +
              " "+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getCodigoUsuario()+", "+objZafParSis.getCodigoUsuario()+",  " +
              " '"+objZafParSis.getNombreComputadoraConDirIP()+"', '"+objZafParSis.getNombreComputadoraConDirIP()+"', "+rstLoc.getString("co_ptopar")+", 'P', '"+rstLoc.getString("tx_datdocoriguirem")+"' " +
              " ,"+rstLoc.getString("co_forRet")+", '"+rstLoc.getString("tx_vehRet")+"', '"+rstLoc.getString("tx_choRet")+"', "+rstLoc.getString("co_ven")+" ,'"+rstLoc.getString("tx_nomven")+"', 'P'  )";

       strSql+=" ; INSERT INTO tbr_cabguirem( co_emp, co_loc, co_tipdoc, co_doc, co_locrel, co_tipdocrel, co_docrel, st_regrep) "
              +" VALUES ("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDocNueGui+", "
              + " "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+",'P' ) ";
        System.out.println("ZafVen25_02._insertarCabGuiRem: (1)" + strSql);
       stmLoc01.executeUpdate(strSql);
       blnRes=true;
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;
     
 }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt); }
 return blnRes;
}

private boolean _insertarDetGuiRem(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodDocNueGui ) {
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc;
 String strSql="";
 int intNumReg=0;
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();
      stmLoc01=conn.createStatement();

      strSql="select * from ( "
            + " select a.nd_can, a.nd_canDev, a2.nd_can as cangui, (a2.nd_can-a.nd_canDev) as cansal "
            + " ,a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a1.co_reg  "
            + " ,a2.co_itm, a2.tx_codalt, a2.tx_nomitm, a2.tx_unimed, a2.nd_pestotkgr, a2.st_meregrfisbod, a2.co_emprel, a2.co_locrel, a2.co_tipdocrel, a2.co_docrel, a2.co_regrel        "
            + " from tbm_detsoldevven as a "
            + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp  and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel )  "
            + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg )  "
            + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+"  and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+"   "
            + " ) as x  where  cansal > 0  ORDER BY co_reg ";
      System.out.println("ZafVen25_02._insertarDetGuiRem: " + strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){

         intNumReg++;

         strSql=" INSERT INTO tbm_detguirem(co_emp, co_loc, co_tipdoc, co_doc, co_reg,  " +
         "  co_itm, tx_codalt, tx_nomitm, tx_unimed, nd_can, st_regrep, nd_pestotkgr, tx_obs1, st_meregrfisbod " +
         " ,co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel ) " +
         " VALUES ("+rstLoc.getInt("co_emp")+", "+rstLoc.getInt("co_loc")+", "+rstLoc.getInt("co_tipdoc")+", " +
         " "+intCodDocNueGui+", "+intNumReg+", "+
         " "+rstLoc.getString("co_itm")+", '"+rstLoc.getString("tx_codalt")+"', " +
         " '"+rstLoc.getString("tx_nomitm")+"' ,'"+rstLoc.getString("tx_unimed")+"', " +
         " "+rstLoc.getString("cansal")+" ,'I' , "+rstLoc.getString("nd_pestotkgr")+",'' " +
         " ,'"+rstLoc.getString("st_meregrfisbod")+"', "+rstLoc.getInt("co_emprel")+", "+rstLoc.getInt("co_locrel")+", "+rstLoc.getInt("co_tipdocrel")+"" +
         " , "+rstLoc.getInt("co_docrel")+", "+rstLoc.getInt("co_regrel")+"   )";

         strSql+=" ; UPDATE  tbm_detguirem set co_emprel=null, co_locrel=null, co_tipdocrel=null, co_docrel=null, co_regrel=null  "
         +" WHERE co_emp="+rstLoc.getInt("co_emp")+" and co_loc="+rstLoc.getInt("co_loc")+" and co_tipdoc="+rstLoc.getInt("co_tipdoc")+" "
         + " and co_doc="+rstLoc.getInt("co_doc")+" ";
        System.out.println("ZafVen25_02._insertarDetGuiRem: (2) " + strSql);
         stmLoc01.executeUpdate(strSql);
         blnRes=true;
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;    

 }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt); }
 return blnRes;
}

private int _getCodDocConfEgre(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, String strNomTbl ){
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 int intCodDoc=-1;
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

        strSql="SELECT case when (Max(co_doc)+1) is null then 1 else Max(co_doc)+1 end as co_doc  FROM "+strNomTbl+" WHERE " +
        " co_emp="+intCodEmp+" and co_loc="+intCodLoc+" "+
        " and co_tipDoc = "+intCodTipDoc;
        System.out.println("ZafVen25_02._getCodDocConfEgre:  " + strSql);
        rstLoc = stmLoc.executeQuery(strSql);
        if(rstLoc.next())
            intCodDoc = rstLoc.getInt("co_doc");
        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;

 }}catch(java.sql.SQLException Evt) {  intCodDoc=-1; objUti.mostrarMsgErr_F1(jIntfra, Evt);  }
    catch(Exception Evt) { intCodDoc=-1; objUti.mostrarMsgErr_F1(jIntfra, Evt); }
 return intCodDoc;
}

/**
 * Permite saber si genera confirmacion por lo que no se enviara ó verificar si bodega confirma ingreso.
 * @param conn
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @param intCodDoc
 * @return  true = Todo bien <br>  false = Error problema
 * 
 * Se corrige condicion, caso en que la totalidad de los items de la solicitud se volveran a facturar; motivo por el cual
 * pasa directamente a contabilidad y bodega no tiene que confirmar nada
 * 
 */
public boolean _getVerGenConf(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 boolean blnRes=false;
 Statement stmLoc;
 ResultSet rstLoc;
 String strSql="";
 
 try{
   if(conn!=null){
       stmLoc=conn.createStatement();
        if (blnIsSegundaDev){
            strSql= " select sum(a.nd_canDev) as canDev, sum(a.nd_canVolFac) as canVolFac, " +
                    " case when a6.ne_numdoc is null or a6.ne_numdoc = 0 then 0 else a6.ne_numdoc end as guirem, " +
                    " case when a6.ne_numorddes is null or a6.ne_numorddes = 0 then 0 else a6.ne_numorddes end as orddes, " +
                    " sum(a2.nd_can) as can,"+ 
                    //sum(a2.nd_cancon + a2.nd_cannunrec) as cantotguisec,  " +
                    " case when a2.co_tipdoc=102 then "+
                    "      sum(a2.nd_cancon + a2.nd_cannunrec)  "+
                    " else "+
                    "      abs(sum(a1.nd_cancon + a1.nd_cannunrec))"+
                    " end as cantotguisec, "+
                    
                    " case when  a2.co_tipdoc=102 then "+
                    "   (sum(a2.nd_can) - sum(a2.nd_cancon + a2.nd_cannunrec)) " +
                    "  else "+
                    "      (sum(a1.nd_can) - sum(a1.nd_cancon  +a1.nd_cannunrec) ) "+   
                    "  end as canpendes,"  +                  
                    " a3.st_volfacmersindev " +
                    " from tbm_detsoldevven as a " +
                    " inner join tbm_detmovinv as a7 on (a7.co_emp=a.co_emp and a7.co_loc=a.co_locrel and a7.co_tipdoc=a.co_tipdocrel and a7.co_doc=a.co_docrel and a7.co_reg=a.co_regrel  ) " +
                    " inner join tbr_cabmovinv as a4 on (a4.co_emp=a7.co_emp and a4.co_loc=a7.co_loc and a4.co_tipdoc=a7.co_tipdoc and a4.co_doc=a7.co_doc ) " +
                    " inner join tbr_cabmovinv as a5 on (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_locrel and a5.co_tipdoc=a4.co_tipdocrel and a5.co_doc=a4.co_docrel ) " +
                    " inner join tbm_detmovinv as a1 on (a1.co_emp=a5.co_emp  and a1.co_loc=a5.co_locrel and a1.co_tipdoc=a5.co_tipdocrel and a1.co_doc=a5.co_docrel and a1.co_itm=a7.co_itm ) " +
                    " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg ) " +
                    " inner join tbm_cabsoldevven as a3 on (a3.co_emp=a.co_emp  and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) " +
                    " inner join tbm_cabguirem as a6 on (a6.co_emp=a2.co_emp and a6.co_loc=a2.co_loc and a6.co_tipdoc=a2.co_tipdoc and a6.co_doc=a2.co_doc ) " +
                    " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+"  and a.nd_canDev > 0 and a2.st_meregrfisbod = 'S' "  + 
                    " group by a3.st_volfacmersindev, a6.ne_numdoc, a6.ne_numorddes, a2.co_tipdoc " +
                    
					/*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
                    " union \n"+


                    " select sum(a.nd_canDev) as canDev, sum(a.nd_canVolFac) as canVolFac,  \n"+
                    "    case when a3.ne_numdoc is null or a3.ne_numdoc = 0 then  \n"+
                    "            0 \n"+
                    "    else \n"+
                    "            a3.ne_numdoc \n"+
                    "    end as guirem,  \n"+
                    "    case when a3.ne_numorddes is null or a3.ne_numorddes = 0 then \n"+ 
                    "            0 \n"+
                    "    else \n"+
                    "            a3.ne_numorddes \n"+
                    "    end as orddes,  \n"+
                    "    sum(a4.nd_can) as can, \n"+
                    "    case when a4.co_tipdoc=102 then       \n"+
                    "            sum(a4.nd_cancon + a4.nd_cannunrec)   \n"+
                    "    else       \n"+
                    "            abs(sum(a1.nd_cancon + a1.nd_cannunrec)) \n"+
                    "    end as cantotguisec,  \n"+
                    "    case when  a4.co_tipdoc=102 then    \n"+
                    "            (sum(a4.nd_can) - sum(a4.nd_cancon + a4.nd_cannunrec))   \n"+
                    "    else       \n"+
                    "            (sum(detres.nd_can) - sum(detres.nd_cancon  +detres.nd_cannunrec) )   \n"+
                    "    end as canpendes, b.st_volfacmersindev \n"+
                    " from tbm_detsoldevven as a  \n"+
                    " inner join tbm_cabsoldevven as b \n"+
                    " on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc) \n"+
                    " inner join tbm_detmovinv as a7 \n"+
                    " on (a7.co_emp=a.co_emp and a7.co_loc=a.co_locrel and a7.co_tipdoc=a.co_tipdocrel and a7.co_doc=a.co_docrel and a7.co_reg=a.co_regrel  )  \n"+
                    " inner join tbr_cabmovinv as rel \n"+
                    " on (rel.co_emp=a7.co_emp and rel.co_loc=a7.co_loc and rel.co_tipdoc=a7.co_tipdoc and rel.co_doc=a7.co_doc )  \n"+
                    " inner join tbr_cabmovinv as a5 \n"+
                    " on (a5.co_emp=rel.co_emp and a5.co_loc=rel.co_locrel and a5.co_tipdoc=rel.co_tipdocrel and a5.co_doc=rel.co_docrel )  \n"+
                    " inner join tbm_detmovinv as a1 \n"+
                    " on (a1.co_emp=a5.co_emp  and a1.co_loc=a5.co_locrel and a1.co_tipdoc=a5.co_tipdocrel and a1.co_doc=a5.co_docrel and a1.co_itm=a7.co_itm )  \n"+
                    " inner join tbm_cabsegmovinv as s \n"+
                    " on (s.co_emprelcabmovinv =a1.co_emp and s.co_locrelcabmovinv=a1.co_loc and s.co_tipdocrelcabmovinv=a1.co_tipdoc and s.co_docrelcabmovinv=a1.co_doc)  \n"+
                    " inner join tbm_cabsegmovinv as s2   \n"+
                    " on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271)   \n"+
                    " INNER JOIN tbm_cabguirem as a3  \n"+
                    " on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem ) \n"+  
                    " INNER JOIN tbm_detguirem as a4   \n"+
                    " on(a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc and a4.co_reg=a.co_reg) \n"+
                    " INNER JOIN tbm_detmovinv as detres   \n"+
                    " on (detres.co_emp=a4.co_emprel  and detres.co_loc=a4.co_locrel  and detres.co_tipdoc=a4.co_tipdocrel and detres.co_doc=a4.co_docrel and detres.co_reg=a4.co_regrel and detres.co_tipdoc<>228 and detres.co_tipdoc=294) \n"+
                

                    " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" \n"+
                    " and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+"    \n"+            
                
                    " group by b.st_volfacmersindev, a3.ne_numdoc, a3.ne_numorddes, a4.co_tipdoc \n";
					/*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
        }else{
//                strSql= " select sum(a.nd_canDev) as canDev, "
//                + " sum(a.nd_canVolFac) as canVolFac, "
//                + " case when a4.ne_numdoc is null or a4.ne_numdoc = 0 then 0 else a4.ne_numdoc end as guirem, "
//                + " case when a4.ne_numorddes is null or a4.ne_numorddes = 0 then 0 else a4.ne_numorddes end as orddes, "
//                        
//                + " sum(a2.nd_can) as can,  sum(a2.nd_cancon + a2.nd_cannunrec) as cantotguisec, "
//                + " (sum(a2.nd_can) - sum(a2.nd_cancon + a2.nd_cannunrec) ) as canpendes, "
//                + " a3.st_volfacmersindev "
//                + " from tbm_detsoldevven as a  "
//                + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp  and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
//                + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg ) "
//                + " inner join tbm_cabsoldevven as a3 on (a3.co_emp=a.co_emp  and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
//                + " inner join tbm_cabguirem as a4 on (a4.co_emp=a2.co_emp and a4.co_loc=a2.co_loc and a4.co_tipdoc=a2.co_tipdoc and a4.co_doc=a2.co_doc ) "        
//                + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+"  and a.nd_canDev > 0 and a2.st_meregrfisbod = 'S' " 
//                + " group by a3.st_volfacmersindev, a4.ne_numdoc, a4.ne_numorddes " 
//                ;    
            
			strSql= " select sum(a.nd_canDev) as canDev, "
                + " sum(a.nd_canVolFac) as canVolFac, "
                + " case when a4.ne_numdoc is null or a4.ne_numdoc = 0 then 0 else a4.ne_numdoc end as guirem, "
                + " case when a4.ne_numorddes is null or a4.ne_numorddes = 0 then 0 else a4.ne_numorddes end as orddes, "
                        
                /* SE AGREGA PARA EL NUEVO PROYECTO DE TRANSFERENCIAS*/
                + " sum(a2.nd_can) as can,"
                + "   case when a2.co_tipdoc=102 then"
                + "       sum(a2.nd_cancon + a2.nd_cannunrec)  "
                + "  else "
                + "       abs(sum(a1.nd_cancon + a1.nd_cannunrec)) "                 
                + "  end as cantotguisec, "
                + "  case when  a2.co_tipdoc=102 then "
                + "      (sum(a2.nd_can) - sum(a2.nd_cancon  +a2.nd_cannunrec) ) "
                + "  else "
                + "      (sum(a1.nd_can) - sum(a1.nd_cancon  +a1.nd_cannunrec) ) "
                + "  end as canpendes,"
                /* SE AGREGA PARA EL NUEVO PROYECTO DE TRANSFERENCIAS*/
                + " a3.st_volfacmersindev "
                + " from tbm_detsoldevven as a  "
                + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp  and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
                + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg ) "
                + " inner join tbm_cabsoldevven as a3 on (a3.co_emp=a.co_emp  and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
                + " inner join tbm_cabguirem as a4 on (a4.co_emp=a2.co_emp and a4.co_loc=a2.co_loc and a4.co_tipdoc=a2.co_tipdoc and a4.co_doc=a2.co_doc ) "        
                + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+"  and a.nd_canDev > 0 and a2.st_meregrfisbod = 'S' " 
                + " group by a3.st_volfacmersindev, a4.ne_numdoc, a4.ne_numorddes,a2.co_tipdoc " 
                                
                /*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/            
                + "    union \n"

                + "  select sum(a.nd_canDev) as canDev,  sum(a.nd_canVolFac) as canVolFac, \n"  
                + "          case when a3.ne_numdoc is null or a3.ne_numdoc = 0 then 0 else a3.ne_numdoc end as guirem, \n"  
                + "            case when a3.ne_numorddes is null or a3.ne_numorddes = 0 then 0 else a3.ne_numorddes end as orddes,  \n"
                + "            sum(a4.nd_can) as can, \n"
                + "            case when a4.co_tipdoc=102 then \n"      
                + "                    sum(a4.nd_cancon + a4.nd_cannunrec) \n"    
                + "            else  \n"
                + "                    abs(sum(detres.nd_cancon + detres.nd_cannunrec)) \n"    
                + "            end as cantotguisec,   \n"  
                + "            case when  a4.co_tipdoc=102 then     \n"    
                + "                    (sum(a4.nd_can) - sum(a4.nd_cancon  +a4.nd_cannunrec) )  \n"   
                + "            else    \n"     
                + "                    (sum(detres.nd_can) - sum(detres.nd_cancon  +detres.nd_cannunrec) ) \n"    
                + "           end as canpendes, a5.st_volfacmersindev \n"  
                + "    FROM tbm_detsoldevven as a  \n"  
                + "    inner join tbm_cabsoldevven as a5 \n"  
                + "    on (a5.co_emp=a.co_emp  and a5.co_loc=a.co_loc and a5.co_tipdoc=a.co_tipdoc and a5.co_doc=a.co_doc )  \n"  
                + "    INNER JOIN tbm_cabmovinv as a1 \n"  
                + "   on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel ) \n"    
                + "    inner join tbm_cabsegmovinv as s \n"  
                + "    on (s.co_emprelcabmovinv =a1.co_emp and s.co_locrelcabmovinv=a1.co_loc and s.co_tipdocrelcabmovinv=a1.co_tipdoc and s.co_docrelcabmovinv=a1.co_doc) \n"  
                + "    inner join tbm_cabsegmovinv as s2 \n"  
                + "    on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271) \n"  
                + "    INNER JOIN tbm_cabguirem as a3  \n"  
                + "    on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem and a1.ne_numorddes=a3.ne_numorddes) \n"  
                + "    INNER JOIN tbm_detguirem as a4 \n"  
                + "    on(a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc and a4.co_reg=a.co_reg) \n"  
                + "    INNER JOIN tbm_detmovinv as detres \n"  
                + "   on (detres.co_emp=a4.co_emprel  and detres.co_loc=a4.co_locrel  and detres.co_tipdoc=a4.co_tipdocrel and detres.co_doc=a4.co_docrel and detres.co_reg=a4.co_regrel and detres.co_tipdoc<>228 and detres.co_tipdoc=294) \n"  
                + "    WHERE a.co_emp="+intCodEmp +" and a.co_loc="+intCodLoc+"  \n"  
                + "    and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" \n"  
                + "    group by a5.st_volfacmersindev, a3.ne_numdoc, a3.ne_numorddes,a4.co_tipdoc \n" ; 
                                
               /*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/      
                                
                                
                                
                             
        }

        System.out.println("ZafVen25_02._getVerGenConf: " + strSql);
        rstLoc=stmLoc.executeQuery(strSql);
        
        if(rstLoc.next()){ 
            
            String strVolFac=rstLoc.getString("st_volfacmersindev");
            int intOrdDes=rstLoc.getInt("orddes");
            double dblCan=rstLoc.getDouble("can");
            double dblCanTotGuiSec=rstLoc.getDouble("cantotguisec");
            double dblCanDev=rstLoc.getDouble("canDev");
            double dblCanVolFac=rstLoc.getDouble("canVolFac");
            double dblCanPenDes=rstLoc.getDouble("canpendes");
            
            if (strVolFac.equals("S")) {
                if (dblCanTotGuiSec > 0 &&  dblCanPenDes == 0) 
                    blnRes=_getVerificaItmEgrFisBod(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc); // NO HAY PENDIENTE DE ENTREGAR NADA
                else 
                    blnRes=_GenConfEgr(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);  // HAY COSAS PENDIENTE DE ENTREGAR Y SE CONFIRMA QUE NO SE ENVIARA
            }else if (strVolFac.equals("N")) {
                if (dblCanTotGuiSec > 0 && dblCanPenDes == 0) 
                    blnRes=_getVerificaItmEgrFisBod(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc); // NO HAY PENDIENTE DE ENTREGAR NADA
                else 
                    blnRes=_GenConfEgr(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);  // HAY COSAS PENDIENTE DE ENTREGAR Y SE CONFIRMA QUE NO SE ENVIARA                
            }
        }else 
            blnRes=_getVerificaItmEgrFisBod(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc); // NO HAY PENDIENTE DE ENTREGAR NADA 
        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;
  }
 }catch(SQLException Evt) { 
     blnRes=false; 
     objUti.mostrarMsgErr_F1(jIntfra, Evt);  
 }catch(Exception Evt) { 
     blnRes=false; 
     objUti.mostrarMsgErr_F1(jIntfra, Evt); 
 }
 return blnRes;
}

/*public boolean _getVerGenConf(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 boolean blnRes=false;
 Statement stmLoc;
 ResultSet rstLoc;
 String strSql="";
 
 try{
   if(conn!=null){
       stmLoc=conn.createStatement();

        strSql="SELECT * FROM ( "
        //+ " select sum(abs(a2.nd_can) - abs(a2.nd_cantotguisec) ) as saldo "
        //+ " from tbm_detsoldevven as a  "
        //+ " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp  and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
        //+ " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg ) "
        //+ " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+"  and a.nd_canDev > 0 "
        //+ " ) as x  where  saldo >  0";

        //strSql= " select case when ((sum(abs(a.nd_canDev)) - sum(abs(a.nd_canVolFac))) = 0) and a3.st_volfacmersindev = 'S' then 'S' else case when ((sum(abs(a.nd_canDev)) - sum(abs(a.nd_canVolFac))) > 0) and a3.st_volfacmersindev = 'S' then 'N' else 'N' end end as volfac "
        //strSql= " select a3.st_volfacmersindev as volfac, sum(abs(a2.nd_can) - abs(a2.nd_cantotguisec) ) as saldo "
        //+ " from tbm_detsoldevven as a  "
        //+ " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp  and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
        //+ " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg ) "
        //+ " inner join tbm_cabsoldevven as a3 on (a3.co_emp=a.co_emp  and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
        //+ " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+"  and a.nd_canDev > 0 " 
        //+ " and a1.st_meringegrfisbod = 'S' "        
        //+ " group by a.nd_canDev, a.nd_canVolFac, a3.st_volfacmersindev "
        //+ " having sum(abs(a2.nd_can) - abs(a2.nd_cantotguisec) ) > 0 ";

        if (blnIsSegundaDev){
            strSql= " select case when (dif = 0) and st_volfacmersindev = 'S' then 'S' else case when (dif > 0) and st_volfacmersindev = 'S' then 'N' else 'N' end end as volfac, " +
                    " case when guirem > 0 or orddes > 0 then 'S' else 'N' end as orddes, " + 
                    " case when (canpendes>0) then 'S' else 'N' end as canpendesp " +
                    " from ( " +
                    " select sum(a.nd_canDev) as canDev, sum(a.nd_canVolFac) as canVolFac, (sum(a.nd_canDev) - sum(a.nd_canVolFac)) as dif, a3.st_volfacmersindev, " +
                    " case when a6.ne_numdoc is null or a6.ne_numdoc = 0 then 0 else a6.ne_numdoc end as guirem, " +
                    " case when a6.ne_numorddes is null or a6.ne_numorddes = 0 then 0 else a6.ne_numorddes end as orddes, " +
                    //" (sum(a2.nd_can) - sum(a2.nd_cantotguisec)) as canpendes " +
                    " (sum(a2.nd_can) - sum(a2.nd_cancon + a2.nd_cannunrec)) as canpendes " +
                    " from tbm_detsoldevven as a " +
                    " inner join tbr_cabmovinv as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_locrel and a4.co_tipdoc=a.co_tipdocrel and a4.co_doc=a.co_docrel ) " +
                    " inner join tbr_cabmovinv as a5 on (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_locrel and a5.co_tipdoc=a4.co_tipdocrel and a5.co_doc=a4.co_docrel ) " +
                    " inner join tbm_detmovinv as a1 on (a1.co_emp=a5.co_emp  and a1.co_loc=a5.co_locrel and a1.co_tipdoc=a5.co_tipdocrel and a1.co_doc=a5.co_docrel and a1.co_reg=a.co_regrel ) " +
                    " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg ) " +
                    " inner join tbm_cabsoldevven as a3 on (a3.co_emp=a.co_emp  and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) " +
                    " inner join tbm_cabguirem as a6 on (a6.co_emp=a2.co_emp and a6.co_loc=a2.co_loc and a6.co_tipdoc=a2.co_tipdoc and a6.co_doc=a2.co_doc ) " +
                    " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+"  and a.nd_canDev > 0 "  + 
                    " group by a3.st_volfacmersindev, a6.ne_numdoc, a6.ne_numorddes " +
                    " ) as x " ;
        }else{
                strSql= " select case when (dif = 0) and st_volfacmersindev = 'S' then 'S' else case when (dif > 0) and st_volfacmersindev = 'S' then 'N' else 'N' end end as volfac, "
                + " case when guirem > 0 or orddes > 0 then 'S' else 'N' end as orddes, "
                + " case when (canpendes>0) then 'S' else 'N' end as canpendesp "        
                + " from ( "
                + " select sum(a.nd_canDev) as canDev, sum(a.nd_canVolFac) as canVolFac, (sum(a.nd_canDev) - sum(a.nd_canVolFac)) as dif, a3.st_volfacmersindev, "
                + " case when a4.ne_numdoc is null or a4.ne_numdoc = 0 then 0 else a4.ne_numdoc end as guirem, "
                + " case when a4.ne_numorddes is null or a4.ne_numorddes = 0 then 0 else a4.ne_numorddes end as orddes, "
                //+ " (sum(a2.nd_can) - sum(a2.nd_cantotguisec)) as canpendes "
                + " (sum(a2.nd_can) - sum(a2.nd_cancon + a2.nd_cannunrec)) as canpendes "
                + " from tbm_detsoldevven as a  "
                + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp  and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
                + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg ) "
                + " inner join tbm_cabsoldevven as a3 on (a3.co_emp=a.co_emp  and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
                + " inner join tbm_cabguirem as a4 on (a4.co_emp=a2.co_emp and a4.co_loc=a2.co_loc and a4.co_tipdoc=a2.co_tipdoc and a4.co_doc=a2.co_doc ) "        
                + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+"  and a.nd_canDev > 0 " 
                + " group by a3.st_volfacmersindev, a4.ne_numdoc, a4.ne_numorddes " 
                + " ) as x " ;       
        }

        System.out.println("ZafVen25_02._getVerGenConf: " + strSql);
        rstLoc=stmLoc.executeQuery(strSql);

        //if(rstLoc.next()){
           //blnRes=_GenConfEgr(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);  // HAY COSAS PENDIENTE DE ENTREGAR Y SE CONFIRMA QUE NO SE ENVIARA
        //}else blnRes=_getVerificaItmEgrFisBod(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc); // NO HAY PENDIENTE DE ENTREGAR NADA 
        
        if(rstLoc.next()){ 
            if (rstLoc.getString("volfac").equals("N") && rstLoc.getString("orddes").equals("N") && rstLoc.getString("canpendesp").equals("S"))
                blnRes=_GenConfEgr(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);  // HAY COSAS PENDIENTE DE ENTREGAR Y SE CONFIRMA QUE NO SE ENVIARA
            else if (rstLoc.getString("volfac").equals("N") && rstLoc.getString("orddes").equals("S") && rstLoc.getString("canpendesp").equals("S") )
                blnRes=_GenConfEgr(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
            else
                blnRes=_getVerificaItmEgrFisBod(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc); // NO HAY PENDIENTE DE ENTREGAR NADA
        }else 
            blnRes=_getVerificaItmEgrFisBod(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc); // NO HAY PENDIENTE DE ENTREGAR NADA 
        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;
  }
 }catch(SQLException Evt) { 
     blnRes=false; 
     objUti.mostrarMsgErr_F1(jIntfra, Evt);  
 }catch(Exception Evt) { 
     blnRes=false; 
     objUti.mostrarMsgErr_F1(jIntfra, Evt); 
 }
 return blnRes;
}*/

private boolean _GenConfEgr(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 boolean blnRes=false;
 Statement stmLoc;
 ResultSet rstLoc;
 String strSql="";
 try{
   if(conn!=null){
       stmLoc=conn.createStatement();
       
       if (blnIsSegundaDev){
            strSql=" \nSELECT * \nFROM ( " +
                   " \nSELECT a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a3.co_ptopar FROM tbm_cabsoldevven as a " +
                   " \nINNER JOIN tbr_cabmovinv as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_locrel and a4.co_tipdoc=a.co_tipdocrel and a4.co_doc=a.co_docrel ) " +
                   " \nINNER JOIN tbr_cabmovinv as a5 on (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_locrel and a5.co_tipdoc=a4.co_tipdocrel and a5.co_doc=a4.co_docrel ) " +
                   " \nINNER JOIN tbm_cabmovinv as a1 on (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_locrel and a1.co_tipdoc=a5.co_tipdocrel and a1.co_doc=a5.co_docrel ) " +
                   " \nINNER JOIN tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc ) " +
                   " \nINNER JOIN tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc ) " +
                   " \nWHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+" " +
                   " \nGROUP BY a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a3.co_ptopar " +
                   
				   /*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
                    " union \n" +
                    
                   " SELECT a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a3.co_ptopar --FROM tbm_cabsoldevven as a  \n"+
                   " from tbm_detsoldevven as a  \n"+
                   " inner join tbm_cabsoldevven as b \n"+ 
                   " on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc) \n"+
                   " inner join tbm_detmovinv as a7 \n"+
                   " on (a7.co_emp=a.co_emp and a7.co_loc=a.co_locrel and a7.co_tipdoc=a.co_tipdocrel and a7.co_doc=a.co_docrel and a7.co_reg=a.co_regrel  )  \n"+
                   " inner join tbr_cabmovinv as rel \n"+
                   " on (rel.co_emp=a7.co_emp and rel.co_loc=a7.co_loc and rel.co_tipdoc=a7.co_tipdoc and rel.co_doc=a7.co_doc )  \n"+
                   " inner join tbr_cabmovinv as a5 \n"+
                   " on (a5.co_emp=rel.co_emp and a5.co_loc=rel.co_locrel and a5.co_tipdoc=rel.co_tipdocrel and a5.co_doc=rel.co_docrel )  \n"+
                   " inner join tbm_detmovinv as a1 \n"+
                   " on (a1.co_emp=a5.co_emp  and a1.co_loc=a5.co_locrel and a1.co_tipdoc=a5.co_tipdocrel and a1.co_doc=a5.co_docrel and a1.co_itm=a7.co_itm )  \n"+
                   "   inner join tbm_cabsegmovinv as s \n"+
                   "   on (s.co_emprelcabmovinv =a1.co_emp and s.co_locrelcabmovinv=a1.co_loc and s.co_tipdocrelcabmovinv=a1.co_tipdoc and s.co_docrelcabmovinv=a1.co_doc)  \n"+
                   "   inner join tbm_cabsegmovinv as s2   \n"+
                   "   on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271)   \n"+
                   "   INNER JOIN tbm_cabguirem as a3  \n"+
                   "   on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem ) \n"+
                   "   INNER JOIN tbm_detguirem as a4   \n"+
                   "   on(a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc and a4.co_reg=a.co_reg) \n"+
                   "   INNER JOIN tbm_detmovinv as detres   \n"+
                   "   on (detres.co_emp=a4.co_emprel  and detres.co_loc=a4.co_locrel  and detres.co_tipdoc=a4.co_tipdocrel and detres.co_doc=a4.co_docrel and detres.co_reg=a4.co_regrel and detres.co_tipdoc<>228 and detres.co_tipdoc=294) \n"+
                   "   where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" \n"+
                   "   and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+"  \n"+
                   "  GROUP BY a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a3.co_ptopar \n"+
				   /*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
                    
                   " \n) \nAS x  ";           
       }else{
            strSql="\nSELECT * \nFROM (  "
            + " \nSELECT a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a3.co_ptopar FROM tbm_cabsoldevven as a  "
            + " \nINNER JOIN tbm_cabmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel )  "
            + " \nINNER JOIN tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc )  "
            + " \nINNER JOIN tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc ) "
            + " \nWHERE a.co_emp="+intCodEmp+"  and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+" "
            + " \nGROUP BY a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a3.co_ptopar  "
            //+ "  \n) \nAS x  ";
            
            /*MODIFICADO POR RESERVAS 04 - OCT -2017*/
            
            + " union \n"
            + " select a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a3.co_ptopar \n"
            + " from tbm_cabsoldevven as a \n"
            + " inner join tbm_cabmovinv as a1 \n"
            + " on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel ) \n"
            + " inner join tbm_cabsegmovinv as s \n"
            + " on (s.co_emprelcabmovinv=a1.co_emp and s.co_locrelcabmovinv=a1.co_loc and s.co_tipdocrelcabmovinv=a1.co_tipdoc and s.co_docrelcabmovinv=a1.co_doc) \n"
            + " inner join tbm_cabsegmovinv as s2 \n"
            + " on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271) \n"
            + " INNER JOIN tbm_cabguirem as a3  \n"
            + " on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem and a1.ne_numorddes=a3.ne_numorddes) \n"
            + " INNER JOIN tbm_detguirem as a4 \n"
            + " on(a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc ) \n"
            + " INNER JOIN tbm_detmovinv as detres \n"
            + " on (detres.co_emp=a4.co_emprel  and detres.co_loc=a4.co_locrel  and detres.co_tipdoc=a4.co_tipdocrel and detres.co_doc=a4.co_docrel and detres.co_reg=a4.co_regrel and detres.co_tipdoc<>228) \n"
            + " WHERE a.co_emp="+intCodEmp+"  and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" \n" 
            + " GROUP BY a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a3.co_ptopar \n"  
            + "  \n) \nAS x  ";
            /*MODIFICADO POR RESERVAS 04 - OCT -2017*/
            
       } 
       
       System.out.println("ZafVen25_02._GenConfEgr: " + strSql);
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next()){ 
           if(_insertarConfEgr(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), rstLoc.getInt("co_ptopar")  )){ 
               blnRes=true; 
           }
       }
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;
  }}catch(SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt); }
 return blnRes;
}

/*
 * MODIFICADO EFLORESA 2012-04-27
 */
private boolean _insertarConfEgr(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpG, int intCodLocG, int intCodTipDocG, int intCodDocG, int intCodBodG ){
    boolean blnRes=false;
    int intTipDocConf=114;
    int intCodDocConf=0;
    int intNumDocConf=0;
    try{
        System.out.println("ZafVen25_02.insertarConfEgr");
        intTipDocConf=obtenerTipDocCnfAutDev(conn, intCodEmpG);
        intCodDocConf=_getCodDocConfEgre( conn, intCodEmpG, intCodLocG, intTipDocConf, "tbm_cabingegrmerbod" ); // MAX(co_doc) FROM tbm_cabingegrmerbod
        intNumDocConf=_getNumDocConfEgre( conn, intCodEmpG, intCodLocG, intTipDocConf );  // ne_ultDoc + 1 FROM tbm_cabTipDoc

        //if(_insertarCabConfEgre(conn, intCodEmpG, intCodLocG, intTipDocConf, intCodDocConf, intNumDocConf, intCodTipDocG, intCodDocG, intCodBodG   ) ){
        if(_insertarCabConfEgre(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmpG, intCodLocG, intTipDocConf, intCodDocConf, intNumDocConf, intCodTipDocG, intCodDocG, intCodBodG   ) ) {
            if(_insertarDetConfEgre(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmpG, intCodLocG, intCodTipDocG, intCodDocG, intTipDocConf, intCodDocConf  ) ) {
                blnRes=true;
            }
            else {
                blnRes=false;
            }
        }
                //conn.rollback();
        else {
            blnRes=false;
        }
            //conn.rollback();
    } catch(Exception Evt) { 
        blnRes=false; 
        objUti.mostrarMsgErr_F1(jIntfra, Evt); 
    }
    return blnRes;
}

/**
 */
private boolean _insertarCabConfEgre(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpG, int intCodLocG, int intTipDocConf, int intCodDocConf, int intNumDoc, int intCodTipDocG, int intCodDocG, int intCodBodG ){
    boolean blnRes=true;
    ResultSet rstLoc;
    Statement stmLoc;
    String strSql="", strFecSis="";
    try{
        System.out.println("ZafVen25_02.insertarCabConfEgr");
        if(conn!=null){
            stmLoc=conn.createStatement();

            strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaBaseDatos());

            /*strSql="select * from (  "
            + " select *, case when volfac = 'S' then case when nd_canDev >= nd_cantotguisec then case when (nd_cantotguisec = nd_canVolFac) then (nd_canDev - nd_canVolFac) else case when (nd_cantotguisec > nd_canVolFac) then (nd_cantotguisec - nd_canVolFac) else case when (nd_cantotguisec < nd_canVolFac) then (nd_canVolFac - nd_cantotguisec) end end end else (nd_canDev - nd_canVolFac) end else case when saldo > nd_canDev then nd_canDev else saldo end end as canconf "
            + " from (  "
            + " select a.co_reg as coregsol, a.nd_canDev, a2.nd_can as cangui, a2.nd_cantotguisec , "
            + " (abs(a2.nd_can) - abs(a2.nd_cantotguisec) ) as saldo, "
            + " abs(a.nd_canVolFac) as nd_canVolFac, "        
            + " a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg , a2.co_itm, a1.co_bod  ,a2.st_meregrfisbod, a3.st_volfacmersindev as volfac "
            + " from tbm_detsoldevven as a  "
            + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp  and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
            + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg )  "
            + " inner join tbm_cabsoldevven as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc )  "
            + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a.nd_canDev > 0   "
            + " ) as x  where saldo > 0 "
            + " ) as x "; */
            
            /*if (blnIsSegundaDev){
                strSql="select * from ( "
                + " select *, case when volfac = 'S' then case when nd_canDev >= nd_cantotguisec and nd_cantotguisec > 0 then case when (nd_cantotguisec = nd_canVolFac) then (nd_canDev - nd_canVolFac) else case when (nd_cantotguisec > nd_canVolFac) then (nd_cantotguisec - nd_canVolFac) else case when (nd_cantotguisec < nd_canVolFac) then (nd_canVolFac - nd_cantotguisec) end end end else (nd_canDev - nd_canVolFac) end else case when saldo > nd_canDev then nd_canDev else saldo end end as canconf "
                + " from ( "
                //+ " select a.co_reg as coregsol, a.nd_canDev, a2.nd_can as cangui, a2.nd_cantotguisec, "
                //+ " (abs(a2.nd_can) - abs(a2.nd_cantotguisec) ) as saldo, "
                + " select a.co_reg as coregsol, a.nd_canDev, a2.nd_can as cangui, (a2.nd_cancon + a2.nd_cannunrec) as nd_cantotguisec, "
                + " (abs(a2.nd_can) - abs(a2.nd_cancon + a2.nd_cannunrec) ) as saldo, "
                + " abs(a.nd_canVolFac) as nd_canVolFac, "        
                + " a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg , a2.co_itm, a1.co_bod  ,a2.st_meregrfisbod, a3.st_volfacmersindev as volfac "
                + " from tbm_detsoldevven as a "
                + " INNER JOIN tbr_cabmovinv as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_locrel and a4.co_tipdoc=a.co_tipdocrel and a4.co_doc=a.co_docrel ) "
                + " INNER JOIN tbr_cabmovinv as a5 on (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_locrel and a5.co_tipdoc=a4.co_tipdocrel and a5.co_doc=a4.co_docrel ) "
                + " INNER JOIN tbm_detmovinv as a1 on (a1.co_emp=a5.co_emp  and a1.co_loc=a5.co_locrel and a1.co_tipdoc=a5.co_tipdocrel and a1.co_doc=a5.co_docrel and a1.co_reg=a.co_regrel ) "
                + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg ) "
                + " inner join tbm_cabsoldevven as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
                + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a.nd_canDev > 0 "
                + " ) as x  where saldo > 0 "
                + " ) as x ";            
            }else{            
                strSql="select * from (  "
                + " select *, case when volfac = 'S' then case when nd_canDev >= nd_cantotguisec and nd_cantotguisec > 0 then case when (nd_cantotguisec = nd_canVolFac) then (nd_canDev - nd_canVolFac) else case when (nd_cantotguisec > nd_canVolFac) then (nd_cantotguisec - nd_canVolFac) else case when (nd_cantotguisec < nd_canVolFac) then (nd_canVolFac - nd_cantotguisec) end end end else (nd_canDev - nd_canVolFac) end else case when saldo > nd_canDev then nd_canDev else saldo end end as canconf "
                + " from (  "
                //+ " select a.co_reg as coregsol, a.nd_canDev, a2.nd_can as cangui, a2.nd_cantotguisec , "
                //+ " (abs(a2.nd_can) - abs(a2.nd_cantotguisec) ) as saldo, "
                + " select a.co_reg as coregsol, a.nd_canDev, a2.nd_can as cangui, (a2.nd_cancon + a2.nd_cannunrec) as nd_cantotguisec , "
                + " (abs(a2.nd_can) - abs(a2.nd_cancon + a2.nd_cannunrec) ) as saldo, "
                + " abs(a.nd_canVolFac) as nd_canVolFac, "        
                + " a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg , a2.co_itm, a1.co_bod  ,a2.st_meregrfisbod, a3.st_volfacmersindev as volfac "
                + " from tbm_detsoldevven as a  "
                + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp  and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
                + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg )  "
                + " inner join tbm_cabsoldevven as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc )  "
                + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a.nd_canDev > 0   "
                + " ) as x  where saldo > 0 "
                + " ) as x ";            
            }*/
            
            if (blnIsSegundaDev){
              strSql="select * from ( "
              //+ " select *, case when volfac = 'S' then case when nd_canDev >= nd_cantotguisec and nd_cantotguisec > 0 and ordendespacho > 0 then case when (nd_cantotguisec = nd_canVolFac) then (nd_canDev - nd_canVolFac) else case when (nd_cantotguisec > nd_canVolFac) then (nd_cantotguisec - nd_canVolFac) else case when (nd_cantotguisec < nd_canVolFac) then (nd_canVolFac - nd_cantotguisec) end end end else (nd_canDev - nd_canVolFac) end else case when saldo > nd_canDev then nd_canDev else saldo end end as canconf " 
              + " select *, "
              + " case when nd_cantotguisec > 0 then case when nd_canDev > 0 then case when nd_canVolFac > 0 then case when nd_canDev = nd_canVolFac then (nd_canDev - nd_canVolFac) else case when cangui = nd_cantotguisec then saldo else case when nd_cantotguisec > nd_canVolFac then (nd_canDev - nd_cantotguisec) else (nd_canDev - nd_canVolFac) end end end else saldo end end else case when nd_canDev > 0 then case when nd_canVolFac > 0 then case when nd_canDev = nd_canVolFac then (nd_canDev - nd_canVolFac) else (nd_canDev - nd_canVolFac) end else (nd_canDev - nd_canVolFac) end end end as canconf " 
              + " from ( "
              //+ " select a.co_reg as coregsol, a.nd_canDev, a2.nd_can as cangui, a2.nd_cantotguisec, "
              //+ " (abs(a2.nd_can) - abs(a2.nd_cantotguisec) ) as saldo, "
              + " select a.co_reg as coregsol, a.nd_canDev, a2.nd_can as cangui, "
              //+ " (a2.nd_cancon + a2.nd_cannunrec) as nd_cantotguisec, "
              //+ " (abs(a2.nd_can) - abs(a2.nd_cancon + a2.nd_cannunrec) ) as saldo, "
                      
              + " case when a2.co_tipdoc=102 then "
              + "      (a2.nd_cancon + a2.nd_cannunrec) "
              + " else "
              + "       abs(a1.nd_cancon + a1.nd_cannunrec) "
              + " end as nd_cantotguisec, "
              +" case when a2.co_tipdoc=102 then "
              +"    (abs(a2.nd_can) - abs(a2.nd_cancon + a2.nd_cannunrec) )"
              + " else "
              + "   (abs(a1.nd_can) - abs(a1.nd_cancon + a1.nd_cannunrec) ) "
              + " end as saldo, "                 
                      
              + " abs(a.nd_canVolFac) as nd_canVolFac, "        
              + " a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg, a2.co_itm, a1.co_bod, a2.st_meregrfisbod, a3.st_volfacmersindev as volfac, "
              + " case when a4.ne_numdoc is null or a4.ne_numdoc = 0 then 0 else a4.ne_numdoc end as guiaremision, " 
              + " case when a4.ne_numorddes is null then 0 else a4.ne_numorddes end as ordendespacho "
              //+ " ,a.co_emp as co_emprel, a.co_locrel as co_locrel, a.co_tipdocrel as co_tipdocrel, a.co_docrel as co_docrel, a.co_regrel as co_regrel" 
              + " from tbm_detsoldevven as a  "
              + " INNER JOIN tbr_cabmovinv as a5 on (a5.co_emp=a.co_emp and a5.co_loc=a.co_locrel and a5.co_tipdoc=a.co_tipdocrel and a5.co_doc=a.co_docrel ) "
              + " INNER JOIN tbr_cabmovinv as a6 on (a6.co_emp=a5.co_emp and a6.co_loc=a5.co_locrel and a6.co_tipdoc=a5.co_tipdocrel and a6.co_doc=a5.co_docrel ) "
              + " INNER JOIN tbm_detmovinv as a7 on (a7.co_emp=a5.co_emp and a7.co_loc=a5.co_loc and a7.co_tipdoc=a5.co_tipdoc and a7.co_doc=a5.co_doc and a7.co_reg=a.co_regrel ) "
              + " INNER JOIN tbm_detmovinv as a1 on (a1.co_emp=a6.co_emp and a1.co_loc=a6.co_locrel and a1.co_tipdoc=a6.co_tipdocrel and a1.co_doc=a6.co_docrel and a1.co_itm=a.co_itm ) "
              + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg )  "
              + " inner join tbm_cabsoldevven as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
              + " inner join tbm_cabguirem as a4 on (a4.co_emp=a2.co_emp and a4.co_loc=a2.co_loc and a4.co_tipdoc=a2.co_tipdoc and a4.co_doc=a2.co_doc) "
              + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a.nd_canDev > 0 "
               /*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/       
              + "  union  \n"

              + "	  select a.co_reg as coregsol, a.nd_canDev, a4.nd_can as cangui,   \n"
              + "		case when a4.co_tipdoc=102 then     \n"   
              + "			(a4.nd_cancon + a4.nd_cannunrec)  \n" 
              + "		else         \n"
              + "		abs(detres.nd_cancon + detres.nd_cannunrec)   \n"
              + "	end as nd_cantotguisec,   \n"
              + "	case when a4.co_tipdoc=102 then      \n"
              + "		(abs(a4.nd_can) - abs(a4.nd_cancon + a4.nd_cannunrec) )  \n"
              + "	else     \n"
              + "		(abs(detres.nd_can) - abs(detres.nd_cancon + detres.nd_cannunrec) )   \n"
              + "	end as saldo,   \n"
              + " abs(a.nd_canVolFac) as nd_canVolFac,  a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc,  \n"
              + "	a4.co_reg, a4.co_itm, a1.co_bod, a4.st_meregrfisbod, b.st_volfacmersindev as volfac,   \n"
              + " case when a3.ne_numdoc is null or a3.ne_numdoc = 0 then  \n"
              + "	0  \n"
              + " else  \n"
              + "	a3.ne_numdoc  \n"
              + " end as guiaremision,   \n"
              + " case when a3.ne_numorddes is null then  \n"
              + " 	0  \n"
              + " else  \n"
              + " 	a3.ne_numorddes  \n"
              + " end as ordendespacho \n"
              + " from tbm_detsoldevven as a   \n"
              + " inner join tbm_cabsoldevven as b  \n"
              + " on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc)  \n"
              + " inner join tbm_detmovinv as a7  \n"
              + " on (a7.co_emp=a.co_emp and a7.co_loc=a.co_locrel and a7.co_tipdoc=a.co_tipdocrel and a7.co_doc=a.co_docrel and a7.co_reg=a.co_regrel  )   \n"
              + " inner join tbr_cabmovinv as rel  \n"
              + " on (rel.co_emp=a7.co_emp and rel.co_loc=a7.co_loc and rel.co_tipdoc=a7.co_tipdoc and rel.co_doc=a7.co_doc )   \n"
              + " inner join tbr_cabmovinv as a5  \n"
              + " on (a5.co_emp=rel.co_emp and a5.co_loc=rel.co_locrel and a5.co_tipdoc=rel.co_tipdocrel and a5.co_doc=rel.co_docrel )   \n"
              + " inner join tbm_detmovinv as a1  \n"
              + " on (a1.co_emp=a5.co_emp  and a1.co_loc=a5.co_locrel and a1.co_tipdoc=a5.co_tipdocrel and a1.co_doc=a5.co_docrel and a1.co_itm=a7.co_itm )   \n"
              + "  inner join tbm_cabsegmovinv as s  \n"
              + " on (s.co_emprelcabmovinv =a1.co_emp and s.co_locrelcabmovinv=a1.co_loc and s.co_tipdocrelcabmovinv=a1.co_tipdoc and s.co_docrelcabmovinv=a1.co_doc)   \n"
              + " inner join tbm_cabsegmovinv as s2    \n"
              + " on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271)    \n"
              + " INNER JOIN tbm_cabguirem as a3  \n"
              + " on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem )  \n"
              + " INNER JOIN tbm_detguirem as a4    \n"
              + " on(a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc and a4.co_reg=a.co_reg)  \n"
              + " INNER JOIN tbm_detmovinv as detres    \n"
              + " on (detres.co_emp=a4.co_emprel  and detres.co_loc=a4.co_locrel  and detres.co_tipdoc=a4.co_tipdocrel and detres.co_doc=a4.co_docrel and detres.co_reg=a4.co_regrel and detres.co_tipdoc<>228 and detres.co_tipdoc=294)  \n"
              + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+"  \n"
              + " and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a.nd_canDev > 0  \n"
                /*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/      
                      
              + " ) as x "
              //+ " where saldo > 0 "
              + " ) as x where canconf > 0 order by coregsol ";             
            }else{
              strSql="select * from ( "
              //+ " select *, case when volfac = 'S' then case when nd_canDev >= nd_cantotguisec and nd_cantotguisec > 0 and ordendespacho > 0 then case when (nd_cantotguisec = nd_canVolFac) then (nd_canDev - nd_canVolFac) else case when (nd_cantotguisec > nd_canVolFac) then (nd_cantotguisec - nd_canVolFac) else case when (nd_cantotguisec < nd_canVolFac) then (nd_canVolFac - nd_cantotguisec) end end end else (nd_canDev - nd_canVolFac) end else case when saldo > nd_canDev then nd_canDev else saldo end end as canconf " 
              + " select *, "
              //+ " case when volfac = 'S' then case when nd_canDev >= nd_cantotguisec and nd_cantotguisec > 0 and ordendespacho > 0 then case when (nd_cantotguisec = nd_canVolFac) then (nd_canDev - nd_canVolFac) else case when (nd_cantotguisec > nd_canVolFac) then saldo else case when (nd_cantotguisec < nd_canVolFac) then (nd_canVolFac - nd_cantotguisec) end end end else (nd_canDev - nd_canVolFac) end else case when saldo > nd_canDev then nd_canDev else saldo end end as canconf " 
              + " case when nd_cantotguisec > 0 then case when nd_canDev > 0 then case when nd_canVolFac > 0 then case when nd_canDev = nd_canVolFac then (nd_canDev - nd_canVolFac) else case when cangui = nd_cantotguisec then saldo else case when nd_cantotguisec > nd_canVolFac then (nd_canDev - nd_cantotguisec) else (nd_canDev - nd_canVolFac) end end end else saldo end end else case when nd_canDev > 0 then case when nd_canVolFac > 0 then case when nd_canDev = nd_canVolFac then (nd_canDev - nd_canVolFac) else (nd_canDev - nd_canVolFac) end else (nd_canDev - nd_canVolFac) end end end as canconf " 
              + " from ( "
              //+ " select a.co_reg as coregsol, a.nd_canDev, a2.nd_can as cangui, a2.nd_cantotguisec, "
              //+ " (abs(a2.nd_can) - abs(a2.nd_cantotguisec) ) as saldo, "
              + " select a.co_reg as coregsol, a.nd_canDev, a2.nd_can as cangui, "
               
              /*AGREGADO POR EL NUEVO PROYECTO DE TRANSFERENCIAS*/       
              //+ "(a2.nd_cancon + a2.nd_cannunrec) as nd_cantotguisec, " // SE COMENTAN PARA QUE VALIDE EL TIPO DE OD QUE VA A SER SIEMPRE UNA 102
              //+ " (abs(a2.nd_can) - abs(a2.nd_cancon + a2.nd_cannunrec) ) as saldo, "
              + "case when a2.co_tipdoc=102 then "
	      + "		(a2.nd_cancon + a2.nd_cannunrec)  " 
	      + "		else "
	      + "		abs(a1.nd_cancon + a1.nd_cannunrec) "
	      + "		end as nd_cantotguisec, "
	      + "	case when a2.co_tipdoc=102 then "
	      + "		(abs(a2.nd_can) - abs(a2.nd_cancon + a2.nd_cannunrec) ) "  
	      + "	else "
	      + "			(abs(a2.nd_can) - abs(a1.nd_cancon + a1.nd_cannunrec) ) "
	      + "       end as saldo,"                      
              /*AGREGADO POR EL NUEVO PROYECTO DE TRANSFERENCIAS*/   
                      
              + " abs(a.nd_canVolFac) as nd_canVolFac, "        
              + " a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg , a2.co_itm, a1.co_bod, a2.st_meregrfisbod, a3.st_volfacmersindev as volfac, "
              + " case when a4.ne_numdoc is null or a4.ne_numdoc = 0 then 0 else a4.ne_numdoc end as guiaremision, " 
              + " case when a4.ne_numorddes is null then 0 else a4.ne_numorddes end as ordendespacho "
              //+ " ,a1.co_emp as co_emprel, a1.co_loc as co_locrel, a1.co_tipdoc as co_tipdocrel, a1.co_doc as co_docrel, a1.co_reg as co_regrel " 
              + " from tbm_detsoldevven as a  "
              + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp  and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
              + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg )  "
              + " inner join tbm_cabsoldevven as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
              + " inner join tbm_cabguirem as a4 on (a4.co_emp=a2.co_emp and a4.co_loc=a2.co_loc and a4.co_tipdoc=a2.co_tipdoc and a4.co_doc=a2.co_doc) "
              + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a.nd_canDev > 0 "
              
              //+ " where saldo > 0 "

             //MODIFICADO POR RESERVAS 4 DE OCT DE 2017

	      + " union "

              + " select b.co_reg as coregsol, b.nd_canDev, a4.nd_can as cangui, "
              + "	case when a4.co_tipdoc=102 then 	"	
	      + "			(a4.nd_cancon + a4.nd_cannunrec)  "		
	      + "		else 		"
	      + "			abs(detres.nd_cancon + detres.nd_cannunrec) "
	      + "		end as nd_cantotguisec, "
	      + "		case when a4.co_tipdoc=102 then 		"
	      + "			(abs(a4.nd_can) - abs(a4.nd_cancon + a4.nd_cannunrec) ) "
              + "		else 			"
              + "			(abs(a4.nd_can) - abs(detres.nd_cancon + detres.nd_cannunrec) )      "
              + "		end as saldo, "
              + "		abs(b.nd_canVolFac) as nd_canVolFac,  a4.co_emp, a4.co_loc, "
              + "		a4.co_tipdoc, a4.co_doc, a4.co_reg , a4.co_itm, "
              + "	detres.co_bod, a4.st_meregrfisbod, a.st_volfacmersindev as volfac,  "
              + "	case when a3.ne_numdoc is null or a3.ne_numdoc = 0 then "
              + "		0 "
              + "	else "
              + "		a3.ne_numdoc "
              + " end as guiaremision,  "
              + "	case when a3.ne_numorddes is null then "
              + "			0 "
	      + "	else "
              + "			a3.ne_numorddes "
              + "		end as ordendespacho "

              + " from tbm_cabsoldevven as a "
              + " inner join tbm_detsoldevven as b "
	      + " on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc) "
              + " inner join tbm_cabmovinv as a1 "
              + " on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel ) "
              + " inner join tbm_cabsegmovinv as s "
              + " on (s.co_emprelcabmovinv=a1.co_emp and s.co_locrelcabmovinv=a1.co_loc and s.co_tipdocrelcabmovinv=a1.co_tipdoc and s.co_docrelcabmovinv=a1.co_doc) "
              + " inner join tbm_cabsegmovinv as s2 "
              + " on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271) "
              + " INNER JOIN tbm_cabguirem as a3  "
              + " on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem and a1.ne_numorddes=a3.ne_numorddes) "
              + " INNER JOIN tbm_detguirem as a4 "
              + " on(a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc and /*a4.co_reg=b.co_reg*/a4.co_itm=b.co_itm) "
              + " INNER JOIN tbm_detmovinv as detres "
              + " on (detres.co_emp=a4.co_emprel  and detres.co_loc=a4.co_locrel  and detres.co_tipdoc=a4.co_tipdocrel and detres.co_doc=a4.co_docrel and detres.co_reg=a4.co_regrel and detres.co_tipdoc<>228) "
              + " WHERE a.co_emp="+intCodEmp
              + " and a.co_loc="+intCodLoc
              + " and a.co_tipdoc="+intCodTipDoc
              + " and a.co_doc="+intCodDoc
              + " and b.nd_canDev > 0 "                  
             
            //MODIFICADO POR RESERVAS 4 DE OCT DE 2017          
                     + " ) as x " 
                      + " ) as x where canconf > 0 order by coregsol "; 
            }

            
            System.out.println("ZafVen25_02._insertarCabConfEgre: " + strSql);
            rstLoc = stmLoc.executeQuery(strSql);
            if(rstLoc.next()){
                strSql="INSERT INTO tbm_cabingegrmerbod(" +
                " co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, " +
                " co_locrelguirem, co_tipdocrelguirem, co_docrelguirem, co_mnu, st_imp, tx_obs1, " +
                " tx_obs2, st_reg, fe_ing, co_usring ) "+
                " VALUES( " +
                " "+intCodEmpG+", "+intCodLocG+", "+intTipDocConf+", "+intCodDocConf+" " +
                " ,'"+strFecSis+"', "+intNumDoc+", "+intCodBodG+", " +
                " "+intCodLocG+", "+intCodTipDocG+", "+intCodDocG+", "+
                " 2205, 'N', '', " + objUti.codificar("SE GENERA DE MANERA AUTOMATICA POR SISTEMAS.") + " , "+
                " 'A', "+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getCodigoUsuario()+" ) ; ";

                strSql +=" UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDoc+" WHERE co_emp="+intCodEmpG+" " +
                " AND co_loc="+intCodLocG+" AND co_tipdoc="+intTipDocConf+" ; ";
                System.out.println("ZafVen25_02._insertarCabConfEgre: " + strSql);
                stmLoc.executeUpdate(strSql);
            }
            stmLoc.close();
            stmLoc=null;
            blnRes=true;
        }
    }catch(SQLException Evt) {  
        blnRes=false; 
        objUti.mostrarMsgErr_F1(jIntfra, Evt);  
    }catch(Exception Evt) { 
        blnRes=false; 
        objUti.mostrarMsgErr_F1(jIntfra, Evt); 
    }
    return blnRes;
}

private boolean _insertarCabConfEgre(Connection conn, int intCodEmpG, int intCodLocG, int intCodTipDoc, int intCodDoc, int intNumDoc, int intCodTipDocG, int intCodDocG, int intCodBodG ){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 String strSql="", strFecSis="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaBaseDatos());

      strSql="INSERT INTO tbm_cabingegrmerbod(" +
      " co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, " +
      " co_locrelguirem, co_tipdocrelguirem, co_docrelguirem, co_mnu, st_imp, tx_obs1, " +
      " tx_obs2, st_reg, fe_ing, co_usring ) "+
      " VALUES( " +
      " "+intCodEmpG+", "+intCodLocG+", "+intCodTipDoc+", "+intCodDoc+" " +
      " ,'"+strFecSis+"', "+intNumDoc+", "+intCodBodG+", " +
      " "+intCodLocG+", "+intCodTipDocG+", "+intCodDocG+", "+
      " 2205, 'N', '', 'SE GENERA DE MANERA AUTOMATICA POR SISTEMAS.', "+
      " 'A', "+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getCodigoUsuario()+" ) ; ";

      strSql +=" UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDoc+" WHERE co_emp="+intCodEmpG+" " +
      " AND co_loc="+intCodLocG+" AND co_tipdoc="+intCodTipDoc+" ; ";

      stmLoc.executeUpdate(strSql);
 
      stmLoc.close();
      stmLoc=null;
      blnRes=true;
 }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt); }
 return blnRes;
}

/**
 * Cuando la cantidad a devolver es igual a la cantidad q se va a volver a facturar no se genera 
 * confirmacion de cantidad nunca recibida, caso contrario se genera una confirmacion por la diferencia 
 * 
 */
private boolean _insertarDetConfEgre(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpG, int intCodLocG, int intCodTipDocG, int intCodDocG, int intTipDocConf, int intCodDocConf ){
 boolean blnRes=false;
 boolean blnActDetRelacionados=false;
 Statement stmLoc, stmLoc01;
 ResultSet rstLoc;
 String strSql="";
 String st_volfac=""; 
 String st_orddes="";
 String st_devtoddes=""; //se devuelve todo lo despachado? 
 double nd_cantotguisec=0, nd_canVolFac=0;
 int intNumReg=0;
 try{
     System.out.println("ZafVen25_02._insertarDetConfEgre");
    if(conn!=null){
      stmLoc=conn.createStatement();
      stmLoc01=conn.createStatement();

      /*strSql="select * from (  "
      + " select *, case when saldo > nd_canDev then nd_canDev else saldo end as canconf "
      + " from (  "
      + " select a.co_reg as coregsol, a.nd_canDev, a2.nd_can as cangui, a2.nd_cantotguisec ,(abs(a2.nd_can) - abs(a2.nd_cantotguisec) ) as saldo "
      + " ,a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg , a2.co_itm, a1.co_bod "
      + " ,a2.st_meregrfisbod "
      + " from tbm_detsoldevven as a  "
      + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp  and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
      + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg )  "
      + "  where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+"  and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+"  and a.nd_canDev > 0   "
      + "  ) as x  where saldo > 0 "
      + " ) as x ";*/
      
      /*strSql="select * from (  "
      + " select *, case when volfac = 'S' then case when nd_canDev >= nd_cantotguisec then case when (nd_cantotguisec = nd_canVolFac) then (nd_canDev - nd_canVolFac) else case when (nd_cantotguisec > nd_canVolFac) then (nd_cantotguisec - nd_canVolFac) else case when (nd_cantotguisec < nd_canVolFac) then (nd_canVolFac - nd_cantotguisec) end end end else (nd_canDev - nd_canVolFac) end else case when saldo > nd_canDev then nd_canDev else saldo end end as canconf "
      + " from (  "
      + " select a.co_reg as coregsol, a.nd_canDev, a2.nd_can as cangui, a2.nd_cantotguisec , "
      + " (abs(a2.nd_can) - abs(a2.nd_cantotguisec) ) as saldo, "
      + " abs(a.nd_canVolFac) as nd_canVolFac, "        
      + " a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg , a2.co_itm, a1.co_bod  ,a2.st_meregrfisbod, a3.st_volfacmersindev as volfac "
      + " from tbm_detsoldevven as a  "
      + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp  and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
      + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg )  "
      + " inner join tbm_cabsoldevven as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc )  "
      + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a.nd_canDev > 0   "
      + " ) as x  where saldo > 0 "
      + " ) as x "; */      
      
      if (blnIsSegundaDev){
        strSql=" select * from ( "
        //+ " select *, case when volfac = 'S' then case when nd_canDev >= nd_cantotguisec and nd_cantotguisec > 0 and ordendespacho > 0 then case when (nd_cantotguisec = nd_canVolFac) then (nd_canDev - nd_canVolFac) else case when (nd_cantotguisec > nd_canVolFac) then (nd_cantotguisec - nd_canVolFac) else case when (nd_cantotguisec < nd_canVolFac) then (nd_canVolFac - nd_cantotguisec) end end end else (nd_canDev - nd_canVolFac) end else case when saldo > nd_canDev then nd_canDev else saldo end end as canconf " 
        + " select *, "
        + " case when nd_cantotguisec > 0 then case when nd_canDev > 0 then case when nd_canVolFac > 0 then case when nd_canDev = nd_canVolFac then (nd_canDev - nd_canVolFac) else case when cangui = nd_cantotguisec then saldo else case when nd_cantotguisec > nd_canVolFac then (nd_canDev - nd_cantotguisec) else (nd_canDev - nd_canVolFac) end end end else saldo end end else case when nd_canDev > 0 then case when nd_canVolFac > 0 then case when nd_canDev = nd_canVolFac then (nd_canDev - nd_canVolFac) else (nd_canDev - nd_canVolFac) end else (nd_canDev - nd_canVolFac) end end end as canconf " 
        + " from ( "
        //+ " select a.co_reg as coregsol, a.nd_canDev, a2.nd_can as cangui, a2.nd_cantotguisec, "
        //+ " (abs(a2.nd_can) - abs(a2.nd_cantotguisec) ) as saldo, "
        + " select a.co_reg as coregsol, a.nd_canDev, a2.nd_can as cangui, "
        //+ "(a2.nd_cancon + a2.nd_cannunrec) as nd_cantotguisec, "
        //+ " (abs(a2.nd_can) - abs(a2.nd_cancon + a2.nd_cannunrec) ) as saldo, "
                
        + " case when a2.co_tipdoc=102 then "
        + "	(a2.nd_cancon + a2.nd_cannunrec)  "
        + " else "
        + "	abs(a1.nd_cancon + a1.nd_cannunrec) "
        + " end as nd_cantotguisec, "
        + " case when a2.co_tipdoc=102 then "
        + "	(abs(a2.nd_can) - abs(a2.nd_cancon + a2.nd_cannunrec) ) "
        + " else "
        + " 	(abs(a2.nd_can) - abs(a1.nd_cancon + a1.nd_cannunrec) ) "
        + " end as saldo,"
                
        + " abs(a.nd_canVolFac) as nd_canVolFac, "        
        //+ " a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg, a2.co_itm, a1.co_bod, a2.st_meregrfisbod, a3.st_volfacmersindev as volfac, "
        + " a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg, a2.co_itm, a1.co_bod, a2.st_meregrfisbod, a3.st_volfacmersindev as volfac, a1.nd_canegrbod, a1.co_emp as empfac, a1.co_loc as locfac,a1.co_tipdoc as tipdocfac,a1.co_doc as docfac ,a1.co_reg as regfac,"//tony agregue el campo de a1.nd_canegrbod
        + " case when a4.ne_numdoc is null or a4.ne_numdoc = 0 then 0 else a4.ne_numdoc end as guiaremision, " 
        + " case when a4.ne_numorddes is null then 0 else a4.ne_numorddes end as ordendespacho "
        //+ " ,a.co_emp as co_emprel, a.co_locrel as co_locrel, a.co_tipdocrel as co_tipdocrel, a.co_docrel as co_docrel, a.co_regrel as co_regrel" 
        + " from tbm_detsoldevven as a  "
        + " INNER JOIN tbr_cabmovinv as a5 on (a5.co_emp=a.co_emp and a5.co_loc=a.co_locrel and a5.co_tipdoc=a.co_tipdocrel and a5.co_doc=a.co_docrel ) "
        + " INNER JOIN tbr_cabmovinv as a6 on (a6.co_emp=a5.co_emp and a6.co_loc=a5.co_locrel and a6.co_tipdoc=a5.co_tipdocrel and a6.co_doc=a5.co_docrel ) "
        + " INNER JOIN tbm_detmovinv as a7 on (a7.co_emp=a5.co_emp and a7.co_loc=a5.co_loc and a7.co_tipdoc=a5.co_tipdoc and a7.co_doc=a5.co_doc and a7.co_reg=a.co_regrel ) "
        + " INNER JOIN tbm_detmovinv as a1 on (a1.co_emp=a6.co_emp and a1.co_loc=a6.co_locrel and a1.co_tipdoc=a6.co_tipdocrel and a1.co_doc=a6.co_docrel and a1.co_itm=a.co_itm ) "
        + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg )  "
        + " inner join tbm_cabsoldevven as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
        + " inner join tbm_cabguirem as a4 on (a4.co_emp=a2.co_emp and a4.co_loc=a2.co_loc and a4.co_tipdoc=a2.co_tipdoc and a4.co_doc=a2.co_doc) "
        + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a.nd_canDev > 0 "
         /*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/       
        + " union \n" 
                
        + " select a.co_reg as coregsol, a.nd_canDev, a4.nd_can as cangui,  \n" 
	+ "		case when a4.co_tipdoc=102 then 	\n" 
	+ "			(a4.nd_cancon + a4.nd_cannunrec)   \n" 
	+ "		else 	\n" 
	+ "			abs(detres.nd_cancon + detres.nd_cannunrec)  \n" 
	+ "		end as nd_cantotguisec,  \n" 
	+ "		case when a4.co_tipdoc=102 then 	\n" 
	+ "			(abs(a4.nd_can) - abs(a4.nd_cancon + a4.nd_cannunrec) )  \n" 
	+ "		else  	\n" 
	+ "			(abs(a4.nd_can) - abs(detres.nd_cancon + detres.nd_cannunrec) )  \n" 
	+ "		end as saldo, abs(a.nd_canVolFac) as nd_canVolFac,  a4.co_emp, a4.co_loc, \n" 
	+ "		a4.co_tipdoc, a4.co_doc, a4.co_reg, a4.co_itm, \n" 
	+ "		detres.co_bod, a4.st_meregrfisbod, b.st_volfacmersindev as volfac, \n" 
	+ "		detres.nd_canegrbod, detres.co_emp as empfac, detres.co_loc as locfac,detres.co_tipdoc as tipdocfac, \n" 
	+ "		detres.co_doc as docfac ,detres.co_reg as regfac, \n" 
	+ "		case when a3.ne_numdoc is null or a3.ne_numdoc = 0 then  \n" 
	+ "			0  \n" 
	+ "		else  \n" 
	+ "			a3.ne_numdoc \n" 
	+ "		end as guiaremision,  \n" 
	+ "		case when a3.ne_numorddes is null then \n" 
	+ "			0 \n" 
	+ "		else \n" 
	+ "			a3.ne_numorddes \n" 
	+ "		end as ordendespacho  \n" 

	+ "	from tbm_detsoldevven as a  \n" 
	+ "	 inner join tbm_cabsoldevven as b  \n"
	+ "	 on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc)  \n"
	+ "	 inner join tbm_detmovinv as a7  \n"
	+ "	 on (a7.co_emp=a.co_emp and a7.co_loc=a.co_locrel and a7.co_tipdoc=a.co_tipdocrel and a7.co_doc=a.co_docrel and a7.co_reg=a.co_regrel  )   \n"
	+ "	 inner join tbr_cabmovinv as rel  \n"
	+ "	 on (rel.co_emp=a7.co_emp and rel.co_loc=a7.co_loc and rel.co_tipdoc=a7.co_tipdoc and rel.co_doc=a7.co_doc )   \n"
	+ "	 inner join tbr_cabmovinv as a5  \n"
	+ "	 on (a5.co_emp=rel.co_emp and a5.co_loc=rel.co_locrel and a5.co_tipdoc=rel.co_tipdocrel and a5.co_doc=rel.co_docrel )   \n"
	+ "	 inner join tbm_detmovinv as a1  \n"
	+ "	 on (a1.co_emp=a5.co_emp  and a1.co_loc=a5.co_locrel and a1.co_tipdoc=a5.co_tipdocrel and a1.co_doc=a5.co_docrel and a1.co_itm=a7.co_itm )   \n"
	+ "	   inner join tbm_cabsegmovinv as s  \n"
	+ "	   on (s.co_emprelcabmovinv =a1.co_emp and s.co_locrelcabmovinv=a1.co_loc and s.co_tipdocrelcabmovinv=a1.co_tipdoc and s.co_docrelcabmovinv=a1.co_doc)   \n"
	+ "	   inner join tbm_cabsegmovinv as s2    \n"
	+ "	   on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271)    \n"
	+ "	   INNER JOIN tbm_cabguirem as a3   \n"
	+ "	   on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem )  \n"
	+ "	   INNER JOIN tbm_detguirem as a4    \n"
	+ "	   on(a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc and a4.co_reg=a.co_reg)  \n"
	+ "	   INNER JOIN tbm_detmovinv as detres    \n"
	+ "	   on (detres.co_emp=a4.co_emprel  and detres.co_loc=a4.co_locrel  and detres.co_tipdoc=a4.co_tipdocrel and detres.co_doc=a4.co_docrel and detres.co_reg=a4.co_regrel and detres.co_tipdoc<>228 and detres.co_tipdoc=294)  \n"
	+ "	   where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+"  \n"
	+ "	   and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a.nd_canDev > 0  \n"
         /*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/       
                
        + " ) as x "
        //+ " where saldo > 0 "
        + " ) as x where canconf > 0 order by coregsol ";             
      }else{
        strSql=" select * from ( "
        //+ " select *, case when volfac = 'S' then case when nd_canDev >= nd_cantotguisec and nd_cantotguisec > 0 and ordendespacho > 0 then case when (nd_cantotguisec = nd_canVolFac) then (nd_canDev - nd_canVolFac) else case when (nd_cantotguisec > nd_canVolFac) then (nd_cantotguisec - nd_canVolFac) else case when (nd_cantotguisec < nd_canVolFac) then (nd_canVolFac - nd_cantotguisec) end end end else (nd_canDev - nd_canVolFac) end else case when saldo > nd_canDev then nd_canDev else saldo end end as canconf " 
        + " select *, "
        //+ " case when volfac = 'S' then case when nd_canDev >= nd_cantotguisec and nd_cantotguisec > 0 and ordendespacho > 0 then case when (nd_cantotguisec = nd_canVolFac) then (nd_canDev - nd_canVolFac) else case when (nd_cantotguisec > nd_canVolFac) then saldo else case when (nd_cantotguisec < nd_canVolFac) then (nd_canVolFac - nd_cantotguisec) end end end else (nd_canDev - nd_canVolFac) end else case when saldo > nd_canDev then nd_canDev else saldo end end as canconf " 
        /* VERSION ANTERIOR INMEDIATA */
        //+ " case when nd_cantotguisec > 0 then case when nd_canDev > 0 then case when nd_canVolFac > 0 then case when nd_canDev = nd_canVolFac then (nd_canDev - nd_canVolFac) else case when cangui = nd_cantotguisec then saldo else case when nd_cantotguisec > nd_canVolFac then (nd_canDev - nd_cantotguisec) else (nd_canDev - nd_canVolFac) end end end else saldo end end else case when nd_canDev > 0 then case when nd_canVolFac > 0 then case when nd_canDev = nd_canVolFac then (nd_canDev - nd_canVolFac) else (nd_canDev - nd_canVolFac) end else (nd_canDev - nd_canVolFac) end end end as canconf " 
        /* VERSION ANTERIOR INMEDIATA */                
        + " case when nd_cantotguisec > 0 then case when nd_canDev > 0 then case when nd_canVolFac > 0 then case when nd_canDev = nd_canVolFac then (nd_canDev - nd_canVolFac) else case when cangui = nd_cantotguisec then saldo else case when nd_cantotguisec > nd_canVolFac then (nd_canDev - nd_cantotguisec) else (nd_canDev - nd_canVolFac) end end end else case when nd_candev <=saldo then nd_candev else saldo end end end else case when nd_canDev > 0 then case when nd_canVolFac > 0 then case when nd_canDev = nd_canVolFac then (nd_canDev - nd_canVolFac) else (nd_canDev - nd_canVolFac) end else (nd_canDev - nd_canVolFac) end end end as canconf "                 
        + " from ( "
        //+ " select a.co_reg as coregsol, a.nd_canDev, a2.nd_can as cangui, a2.nd_cantotguisec, "
        //+ " (abs(a2.nd_can) - abs(a2.nd_cantotguisec) ) as saldo, "
        + " select a.co_reg as coregsol, a.nd_canDev, a2.nd_can as cangui, "
        /*AGREGADO POR EL NUEVO PROYECTO DE TRANSFERENCIAS*/       
              //+ "(a2.nd_cancon + a2.nd_cannunrec) as nd_cantotguisec, " // SE COMENTAN PARA QUE VALIDE EL TIPO DE OD QUE VA A SER SIEMPRE UNA 102
              //+ " (abs(a2.nd_can) - abs(a2.nd_cancon + a2.nd_cannunrec) ) as saldo, "
              //+ " case when a2.co_tipdoc=102 then "
	      //+ "	(a2.nd_cancon + a2.nd_cannunrec)  " 
	      //+ " else "
	      + "	abs(a1.nd_cancon + a1.nd_cannunrec) "
	      + " /*end */ as nd_cantotguisec, "
	      //+ " case when a2.co_tipdoc=102 then "
	      //+ "	(abs(a2.nd_can) - abs(a2.nd_cancon + a2.nd_cannunrec) ) "  
	      //+ " else "
	      + "        (abs(a2.nd_can) - abs(a1.nd_cancon + a1.nd_cannunrec) ) "
	      + "/* end */ as saldo,"                      
        /*AGREGADO POR EL NUEVO PROYECTO DE TRANSFERENCIAS*/                
                
        + " abs(a.nd_canVolFac) as nd_canVolFac, "        
        + " a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg , a2.co_itm, a1.co_bod, a2.st_meregrfisbod, a3.st_volfacmersindev as volfac, "
        + " case when a4.ne_numdoc is null or a4.ne_numdoc = 0 then 0 else a4.ne_numdoc end as guiaremision, " 
        /*AGREGADO PARA EL PROYECTO DE TRANSFERENCIAS CMATEO*/                
        + " a1.co_emp as empfac, a1.co_loc as locfac, a1.co_tipdoc as tipdocfac, a1.co_doc as docfac ,a1.co_reg as regfac,"
        /*AGREGADO PARA EL PROYECTO DE TRANSFERENCIAS CMATEO*/
        + " case when a4.ne_numorddes is null then 0 else a4.ne_numorddes end as ordendespacho , detcot.nd_canloc, detcot.nd_canrem, a1.nd_canegrbod , a1.nd_candesentcli "
        //+ " ,a1.co_emp as co_emprel, a1.co_loc as co_locrel, a1.co_tipdoc as co_tipdocrel, a1.co_doc as co_docrel, a1.co_reg as co_regrel " 
        + " from tbm_detsoldevven as a  "
        + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp  and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
        + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg )  "
        + " inner join tbm_cabsoldevven as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
        + " inner join tbm_cabguirem as a4 on (a4.co_emp=a2.co_emp and a4.co_loc=a2.co_loc and a4.co_tipdoc=a2.co_tipdoc and a4.co_doc=a2.co_doc) "
        
        /*AGREGADO POR PROYECTO DE TRANSFERENCIAS*/
                
        + " inner join tbm_cabmovinv as fac "
        + " on (fac.co_emp=a1.co_emp and fac.co_loc=a1.co_loc and fac.co_tipdoc=a1.co_tipdoc and fac.co_doc=a1.co_doc) "
	+ " inner join tbm_cabcotven as cot "
	+ " on (cot.co_emp=fac.co_emp and cot.co_loc=fac.co_loc and cot.co_cot=fac.ne_numcot) " 
	+ " inner join tbm_detcotven as detcot "
	+ " on (cot.co_emp=detcot.co_emp and cot.co_loc=detcot.co_loc and cot.co_cot=detcot.co_cot and detcot.co_reg=a1.co_reg) "                
                
        /*AGREGADO POR PROYECTO DE TRANSFERENCIAS*/                
                
        + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a.nd_canDev > 0 "
                
                /*MODIFICADO POR DEVOLUCIONES EN RESERVAS */
                
        + " union \n" 

        + "    select b.co_reg as coregsol, b.nd_canDev, a4.nd_can as cangui, \n"
        + "             abs(detres.nd_cancon + detres.nd_cannunrec) as nd_cantotguisec, \n"
        + "             (abs(a4.nd_can) - abs(detres.nd_cancon + detres.nd_cannunrec) ) as saldo, \n"
        + "             abs(b.nd_canVolFac) as nd_canVolFac,  \n"
        + "             a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, \n"
        + "             a4.co_reg , a4.co_itm, detres.co_bod, a4.st_meregrfisbod, \n"
        + "             a.st_volfacmersindev as volfac,  \n"
        + "             case when a3.ne_numdoc is null or a3.ne_numdoc = 0 then \n"
        + "                     0 \n"
        + "             else \n"
        + "                     a3.ne_numdoc \n"
        + "             end as guiaremision,  detres.co_emp as empfac, detres.co_loc as locfac, \n"
        + "             detres.co_tipdoc as tipdocfac, detres.co_doc as docfac ,detres.co_reg as regfac, \n"
        + "             case when a3.ne_numorddes is null then \n"
        + "                     0 \n"
        + "             else \n"
        + "                     a3.ne_numorddes \n"
        + "             end as ordendespacho , 0, 0, \n"
        + "             detres.nd_canegrbod , detres.nd_candesentcli \n"


        + "      from tbm_cabsoldevven as a \n"
        + "      inner join tbm_detsoldevven as b \n"
        + "      on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc) \n"
        + "      inner join tbm_cabmovinv as a1 \n"
        + "      on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel ) \n"
        + "      inner join tbm_cabsegmovinv as s \n"
        + "      on (s.co_emprelcabmovinv=a1.co_emp and s.co_locrelcabmovinv=a1.co_loc and s.co_tipdocrelcabmovinv=a1.co_tipdoc and s.co_docrelcabmovinv=a1.co_doc) \n"
        + "      inner join tbm_cabsegmovinv as s2 \n"
        + "      on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271) \n"
        + "      INNER JOIN tbm_cabguirem as a3  \n"
        + "      on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem and a1.ne_numorddes=a3.ne_numorddes) \n"
        + "      INNER JOIN tbm_detguirem as a4 \n"
        + "      on(a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc and /*a4.co_reg=b.co_reg*/a4.co_itm=b.co_itm) \n"
        + "      INNER JOIN tbm_detmovinv as detres \n"
        + "      on (detres.co_emp=a4.co_emprel  and detres.co_loc=a4.co_locrel  and detres.co_tipdoc=a4.co_tipdocrel and detres.co_doc=a4.co_docrel and detres.co_reg=a4.co_regrel and detres.co_tipdoc<>228) \n"
        + "      WHERE a.co_emp="+intCodEmp+"\n"
        + "      and a.co_loc="+intCodLoc+"\n"
        + "      and a.co_tipdoc="+intCodTipDoc+"\n"
        + "      and a.co_doc="+intCodDoc+"\n"
        + "      and b.nd_canDev > 0 "       
                
                /*MODIFICADO POR DEVOLUCIONES EN RESERVAS */
                
        + " ) as x "
        //+ " where saldo > 0 "
        + " ) as x where canconf > 0 order by coregsol "; 
      }
      
      System.out.println("ZafVen25_02._insertarDetConfEgre: ANTES DE " + strSql);
      rstLoc=stmLoc.executeQuery(strSql);  // Si Aqui no retorna algo... 
      ZafGuiRemDAO dao=new ZafGuiRemDAO();     
      
      while(rstLoc.next()){
            
            int intTipDocODTra=dao.obtenerTipDocOD(conn, rstLoc.getInt("co_emp"));
            
            intNumReg++;
            strSql="INSERT INTO tbm_detingegrmerbod( " +
            " co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrelguirem, co_tipdocrelguirem, co_docrelguirem, co_regrelguirem, co_itm, co_bod,  nd_cannunrec, tx_obs1 ) "+
            " VALUES( " +
            " " + intCodEmpG+", "+intCodLocG+", "+intTipDocConf+", "+intCodDocConf+" " +
            " ,"+intNumReg+", "+rstLoc.getInt("co_loc")+", "+rstLoc.getInt("co_tipdoc")+", "+rstLoc.getInt("co_doc")+", "+
            " "+rstLoc.getInt("co_reg")+",  "+
            " "+rstLoc.getInt("co_itm")+", "+rstLoc.getInt("co_bod")+"  " + 
            " , "+rstLoc.getDouble("canconf")*-1+" , " +
            " '' )  ";

            if(rstLoc.getString("st_meregrfisbod").equals("S")){

                /*ACTUALIZACION POR EL PROYECTO DE TRANSFERENCIAS*/
                 if(rstLoc.getInt("co_tipdoc")==intTipDocODTra){
                    if(rstLoc.getDouble("nd_canegrbod")<0){                                
                        if(rstLoc.getDouble("canconf")<=Math.abs(rstLoc.getDouble("nd_canegrbod"))){
                            strSql+=" ; UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod+"+rstLoc.getDouble("canconf")+"  WHERE  co_emp="+rstLoc.getInt("co_emp")+" and co_bod="+rstLoc.getInt("co_bod")+" and co_itm="+rstLoc.getInt("co_itm");
                            strSql+=" ;UPDATE tbm_detmovinv SET nd_canegrbod=nd_canegrbod+"+rstLoc.getDouble("canconf")+", nd_cannunrec=case when nd_cannunrec is null then 0 else nd_cannunrec end + "+rstLoc.getDouble("canconf")*(-1)
                                  + "  WHERE  co_emp="+rstLoc.getInt("empfac")+" and co_loc="+rstLoc.getInt("locfac")+" and co_tipdoc="+rstLoc.getInt("tipdocfac")+" and co_doc="+rstLoc.getInt("docfac")+" and co_reg= "+rstLoc.getInt("regfac");                
                            strSql+=" ;UPDATE tbm_detmovinv SET nd_canpen=nd_can-nd_cancon-nd_cannunrec WHERE  co_emp="+rstLoc.getInt("empfac")+" and co_loc="+rstLoc.getInt("locfac")+" and co_tipdoc="+rstLoc.getInt("tipdocfac")+" and co_doc="+rstLoc.getInt("docfac")+" and co_reg= "+rstLoc.getInt("regfac");                
                        }else{                                
                            strSql+=" ; UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod+"+rstLoc.getDouble("nd_canegrbod")*(-1)+"  WHERE  co_emp="+rstLoc.getInt("co_emp")+" and co_bod="+rstLoc.getInt("co_bod")+" and co_itm="+rstLoc.getInt("co_itm");
                            strSql+=" ;UPDATE tbm_detmovinv SET nd_canegrbod=nd_canegrbod+"+rstLoc.getDouble("nd_canegrbod")*(-1)+", nd_cannunrec=case when nd_cannunrec is null then 0 else nd_cannunrec end + "+rstLoc.getDouble("canconf")*(-1)
                                  + "  WHERE  co_emp="+rstLoc.getInt("empfac")+" and co_loc="+rstLoc.getInt("locfac")+" and co_tipdoc="+rstLoc.getInt("tipdocfac")+" and co_doc="+rstLoc.getInt("docfac")+" and co_reg= "+rstLoc.getInt("regfac");                
                            strSql+=" ;UPDATE tbm_detmovinv SET nd_canpen=nd_can-nd_cancon-nd_cannunrec WHERE  co_emp="+rstLoc.getInt("empfac")+" and co_loc="+rstLoc.getInt("locfac")+" and co_tipdoc="+rstLoc.getInt("tipdocfac")+" and co_doc="+rstLoc.getInt("docfac")+" and co_reg= "+rstLoc.getInt("regfac");

                            /*ELIMINAR LA CANTIDAD EN DESPACHO ENTREGAR AL CLIENTE EN EL CASO DE DESPACHO PARCIALES*/
                            if(rstLoc.getDouble("nd_candesentcli")<0){
                                double dblVal=rstLoc.getDouble("canconf")+rstLoc.getDouble("nd_canegrbod");
                                if(dblVal<=Math.abs(rstLoc.getDouble("nd_candesentcli"))){
                                    strSql+=" ;UPDATE tbm_detmovinv SET nd_candesentcli=nd_candesentcli+"+dblVal
                                          + "  WHERE  co_emp="+rstLoc.getInt("empfac")+" and co_loc="+rstLoc.getInt("locfac")+" and co_tipdoc="+rstLoc.getInt("tipdocfac")+" and co_doc="+rstLoc.getInt("docfac")+" and co_reg= "+rstLoc.getInt("regfac");
                                }                                    
                            }
                            /*ELIMINAR LA CANTIDAD EN DESPACHO ENTREGAR AL CLIENTE EN EL CASO DE DESPACHO PARCIALES*/
                        }
                    }else{
                        if(rstLoc.getDouble("nd_candesentcli")<0){
                            if(rstLoc.getDouble("canconf")<=Math.abs(rstLoc.getDouble("nd_candesentcli"))){                                        
                                strSql+=" ; UPDATE tbm_invbod SET nd_candesentcli=nd_candesentcli+"+rstLoc.getDouble("canconf")+"  WHERE  co_emp="+rstLoc.getInt("co_emp")+" and co_bod="+rstLoc.getInt("co_bod")+" and co_itm="+rstLoc.getInt("co_itm");                                        
                                strSql+=" ;UPDATE tbm_detmovinv SET nd_candesentcli=nd_candesentcli+"+rstLoc.getDouble("canconf")+", nd_cannunrec=case when nd_cannunrec is null then 0 else nd_cannunrec end + "+rstLoc.getDouble("canconf")*(-1)
                                      + "  WHERE  co_emp="+rstLoc.getInt("empfac")+" and co_loc="+rstLoc.getInt("locfac")+" and co_tipdoc="+rstLoc.getInt("tipdocfac")+" and co_doc="+rstLoc.getInt("docfac")+" and co_reg= "+rstLoc.getInt("regfac");                
                                strSql+=" ;UPDATE tbm_detmovinv SET nd_canpen=nd_can-nd_cancon-nd_cannunrec WHERE  co_emp="+rstLoc.getInt("empfac")+" and co_loc="+rstLoc.getInt("locfac")+" and co_tipdoc="+rstLoc.getInt("tipdocfac")+" and co_doc="+rstLoc.getInt("docfac")+" and co_reg= "+rstLoc.getInt("regfac");                
                            }
                         }
                    }                     
                     
                }else{/*NUEVO 2-SEP-2016 DOCUMENTOS DE ORDEN DE DESPACHO 102 (ANTERIORES AL PROYECTO)*/
                        if(rstLoc.getDouble("nd_canegrbod")<0){//SI TODAVIA HAY ALGO QUE EGRESAR DE BODEGA
                            if(rstLoc.getDouble("canconf")<=Math.abs(rstLoc.getDouble("nd_canegrbod"))){
                                strSql+=" ; UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod+"+rstLoc.getDouble("canconf")+"  WHERE  co_emp="+rstLoc.getInt("co_emp")+" and co_bod="+rstLoc.getInt("co_bod")+" and co_itm="+rstLoc.getInt("co_itm");
                                strSql+=" ;UPDATE tbm_detmovinv SET nd_canegrbod=nd_canegrbod+"+rstLoc.getDouble("canconf")+", nd_cannunrec=case when nd_cannunrec is null then 0 else nd_cannunrec end + "+rstLoc.getDouble("canconf")*(-1)
                                      + "  WHERE  co_emp="+rstLoc.getInt("empfac")+" and co_loc="+rstLoc.getInt("locfac")+" and co_tipdoc="+rstLoc.getInt("tipdocfac")+" and co_doc="+rstLoc.getInt("docfac")+" and co_reg= "+rstLoc.getInt("regfac");                
                                strSql+=" ;UPDATE tbm_detmovinv SET nd_canpen=nd_can-nd_cancon-nd_cannunrec WHERE  co_emp="+rstLoc.getInt("empfac")+" and co_loc="+rstLoc.getInt("locfac")+" and co_tipdoc="+rstLoc.getInt("tipdocfac")+" and co_doc="+rstLoc.getInt("docfac")+" and co_reg= "+rstLoc.getInt("regfac");                
                                
                            }else{
                                strSql+=" ; UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod+"+rstLoc.getDouble("nd_canegrbod")*(-1)+"  WHERE  co_emp="+rstLoc.getInt("co_emp")+" and co_bod="+rstLoc.getInt("co_bod")+" and co_itm="+rstLoc.getInt("co_itm");
                                strSql+=" ;UPDATE tbm_detmovinv SET nd_canegrbod=nd_canegrbod+"+rstLoc.getDouble("nd_canegrbod")*(-1)+", nd_cannunrec=case when nd_cannunrec is null then 0 else nd_cannunrec end + "+rstLoc.getDouble("canconf")*(-1)
                                      + "  WHERE  co_emp="+rstLoc.getInt("empfac")+" and co_loc="+rstLoc.getInt("locfac")+" and co_tipdoc="+rstLoc.getInt("tipdocfac")+" and co_doc="+rstLoc.getInt("docfac")+" and co_reg= "+rstLoc.getInt("regfac");                
                                strSql+=" ;UPDATE tbm_detmovinv SET nd_canpen=nd_can-nd_cancon-nd_cannunrec WHERE  co_emp="+rstLoc.getInt("empfac")+" and co_loc="+rstLoc.getInt("locfac")+" and co_tipdoc="+rstLoc.getInt("tipdocfac")+" and co_doc="+rstLoc.getInt("docfac")+" and co_reg= "+rstLoc.getInt("regfac");
                                
                                /*ELIMINAR LA CANTIDAD EN DESPACHO ENTREGAR AL CLIENTE EN EL CASO DE DESPACHO PARCIALES*/
                                if(rstLoc.getDouble("nd_candesentcli")<0){
                                    double dblVal=rstLoc.getDouble("canconf")+rstLoc.getDouble("nd_canegrbod");
                                    if(dblVal<=Math.abs(rstLoc.getDouble("nd_candesentcli"))){
                                        strSql+=" ;UPDATE tbm_detmovinv SET nd_candesentcli=nd_candesentcli+"+dblVal
                                              + "  WHERE  co_emp="+rstLoc.getInt("empfac")+" and co_loc="+rstLoc.getInt("locfac")+" and co_tipdoc="+rstLoc.getInt("tipdocfac")+" and co_doc="+rstLoc.getInt("docfac")+" and co_reg= "+rstLoc.getInt("regfac");
                                    }                                    
                                }
                                /*ELIMINAR LA CANTIDAD EN DESPACHO ENTREGAR AL CLIENTE EN EL CASO DE DESPACHO PARCIALES*/
                            }
                         }else{
                            if(rstLoc.getDouble("nd_candesentcli")<0){
                                if(rstLoc.getDouble("canconf")<=Math.abs(rstLoc.getDouble("nd_candesentcli"))){                                        
                                    strSql+=" ; UPDATE tbm_invbod SET nd_candesentcli=nd_candesentcli+"+rstLoc.getDouble("canconf")+"  WHERE  co_emp="+rstLoc.getInt("co_emp")+" and co_bod="+rstLoc.getInt("co_bod")+" and co_itm="+rstLoc.getInt("co_itm");                                        
                                    strSql+=" ;UPDATE tbm_detmovinv SET nd_candesentcli=nd_candesentcli+"+rstLoc.getDouble("canconf")+", nd_cannunrec=case when nd_cannunrec is null then 0 else nd_cannunrec end + "+rstLoc.getDouble("canconf")*(-1)
                                          + "  WHERE  co_emp="+rstLoc.getInt("empfac")+" and co_loc="+rstLoc.getInt("locfac")+" and co_tipdoc="+rstLoc.getInt("tipdocfac")+" and co_doc="+rstLoc.getInt("docfac")+" and co_reg= "+rstLoc.getInt("regfac");                
                                    strSql+=" ;UPDATE tbm_detmovinv SET nd_canpen=nd_can-nd_cancon-nd_cannunrec WHERE  co_emp="+rstLoc.getInt("empfac")+" and co_loc="+rstLoc.getInt("locfac")+" and co_tipdoc="+rstLoc.getInt("tipdocfac")+" and co_doc="+rstLoc.getInt("docfac")+" and co_reg= "+rstLoc.getInt("regfac");                
                                }
                             }
                        }
                     /*NUEVO 2-SEP-2016*/
                 }
                /*ACTUALIZACION POR EL PROYECTO DE TRANSFERENCIAS*/                
            }else{//CASO TERMINALES L
                    strSql+=" ;UPDATE tbm_detmovinv SET nd_cannunrec=case when nd_cannunrec is null then 0 else nd_cannunrec end + "+rstLoc.getDouble("canconf")*(-1)
                          + " WHERE  co_emp="+rstLoc.getInt("empfac")+" and co_loc="+rstLoc.getInt("locfac")+" and co_tipdoc="+rstLoc.getInt("tipdocfac")+" and co_doc="+rstLoc.getInt("docfac")+" and co_reg= "+rstLoc.getInt("regfac");                
                    strSql+=" ;UPDATE tbm_detmovinv SET nd_canpen=nd_can-nd_cancon-nd_cannunrec WHERE  co_emp="+rstLoc.getInt("empfac")+" and co_loc="+rstLoc.getInt("locfac")+" and co_tipdoc="+rstLoc.getInt("tipdocfac")+" and co_doc="+rstLoc.getInt("docfac")+" and co_reg= "+rstLoc.getInt("regfac");                
            }
            
            

            strSql += " ; UPDATE tbm_detsoldevven SET  co_locRelCon="+intCodLocG+", co_tipDocRelCon="+intTipDocConf+", co_docRelCon="+intCodDocConf+", co_regRelCon="+intNumReg+"  "
            + " WHERE co_emp= "+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_Doc="+intCodDoc+" and co_reg="+rstLoc.getInt("coregsol");
            if(rstLoc.getInt("co_tipdoc")!=intTipDocODTra){
                strSql += " ; UPDATE tbm_detguirem SET nd_cannunrec=case when nd_cannunrec is null then 0 else nd_cannunrec end + "+rstLoc.getDouble("canconf")+" "
                + ", nd_cantotguisec=case when nd_cantotguisec is null then 0 else nd_cantotguisec end + "+rstLoc.getDouble("canconf")+" " 
                + "  WHERE co_emp="+rstLoc.getInt("co_emp")+" " +
                " and co_loc="+rstLoc.getInt("co_loc")+" and co_tipdoc="+rstLoc.getInt("co_tipdoc")+" and co_doc="+rstLoc.getInt("co_doc")+" " +
                " and co_reg="+rstLoc.getInt("co_reg")+"  ";
                System.out.println("ZafVen25_02._insertarDetConfEgre: (A) " + strSql);
            }
            stmLoc01.executeUpdate(strSql);

            st_volfac = rstLoc.getString("volfac");
            st_orddes = !rstLoc.getString("guiaremision").equals("0")?"S":!rstLoc.getString("ordendespacho").equals("0")?"S":"N"; 
            nd_cantotguisec = nd_cantotguisec + objUti.redondear(rstLoc.getDouble("nd_cantotguisec"), 0);
            nd_canVolFac = nd_canVolFac + objUti.redondear(rstLoc.getDouble("nd_canVolFac"), 0);

            //blnActDetRelacionados=actualizaDetDocRelacionados(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, rstLoc.getInt("coregsol") );

//            System.out.println("ZafVen25_02._insertarDetConfEgre: actualizaDetDocRelCliRet: " + intCodEmp + "," + intCodLoc + "," + "," + intCodTipDoc + "," + intCodDoc + "," + rstLoc.getInt("coregsol"));
            
           // José Marín M. 25/Nov/2014 CLIENTE RETIRA 
           // blnActDetRelacionados = actualizaDetDocRelCliRet(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, rstLoc.getInt("coregsol") ); 
           // José Marín M. 25/Nov/2014 CLIENTE RETIRA 
         
      }
      rstLoc.close();
      rstLoc=null;      
      
      st_devtoddes = (nd_cantotguisec > 0)?((nd_canVolFac > 0)?((nd_canVolFac==nd_cantotguisec)?"S":"N"):"" ):"";
      
      if (st_devtoddes.equals("")) {
//        strSql=" select (a.nd_canDev) as canDev, (a.nd_canVolFac) as canVolFac, (a2.nd_can) as can, "
//                + " case when a2.co_tipdoc=102 then"
//                + "(a2.nd_cancon + a2.nd_cannunrec) "
//                + " else "
//                + "(a1.nd_cancon + a1.nd_cannunrec) "
//                + " end as cantotguisec"
//                + " (a2.nd_cancon) as cancon, (a2.nd_cannunrec) as cannunrec, "
//                + " ((a2.nd_can) - (a2.nd_cancon + a2.nd_cannunrec) ) as canpendes, "
//                + " case when (a2.nd_cancon) > 0 then case when (a2.nd_cancon<=a.nd_canVolFac) then 'N' else 'S' end else 'N' end as tiecancon, "
//                + " case when (a2.nd_cancon) > 0 then 'S' else 'N' end as tieguirem, "
//                + " a3.st_volfacmersindev "
//                + " from tbm_detsoldevven as a "
//                + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp  and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
//                + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg ) "
//                + " inner join tbm_cabsoldevven as a3 on (a3.co_emp=a.co_emp  and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
//                + " inner join tbm_cabguirem as a4 on (a4.co_emp=a2.co_emp and a4.co_loc=a2.co_loc and a4.co_tipdoc=a2.co_tipdoc and a4.co_doc=a2.co_doc ) "
//                + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" "
//                + " and a.nd_canDev > 0 "
//                + " and a2.st_meregrfisbod = 'S' ";
          
          
strSql=" select (a.nd_canDev) as canDev, (a.nd_canVolFac) as canVolFac, (a2.nd_can) as can," 
               + " case when a2.co_tipdoc=102 then"
	       + "	(a2.nd_cancon + a2.nd_cannunrec) "
               + " else "
	       + "	(a1.nd_cancon + a1.nd_cannunrec) "
               + "  end as cantotguisec,"
               + "  case when a2.co_tipdoc=102 then "
	       + "	(a2.nd_cancon)  "
	       + " else"
               + "     (a1.nd_cancon) "
	       + " end as cancon,"
	       + " case when a2.co_tipdoc=102 then "
	       + "	(a2.nd_cannunrec) "
	       + "	else "
	       + "	(a1.nd_cannunrec) "
	       + " end as cannunrec,"
	       + " case when a2.co_tipdoc=102 then"		
               + "	((a2.nd_can) - (a2.nd_cancon + a2.nd_cannunrec) )  "
               + " else"
               + "((a1.nd_can) - (a1.nd_cancon + a1.nd_cannunrec) ) "
               + " end as canpendes,"
               + " case when a2.co_tipdoc=102 then"
               + "	case when (a2.nd_cancon) > 0 then "
               + "             case when (a2.nd_cancon<=a.nd_canVolFac) then "
               + "			'N' "
	       + "		else "
	       + "			'S' "
               + "		end "
               + "      else "
	       + "		'N' "
               + "	end  "
	       + " else "
	       + "      case when (a1.nd_cancon) > 0 then "
               + "		case when (a1.nd_cancon<=a.nd_canVolFac) then "
               + "			'N' "
               + "		else "
               + "			'S' "
               + "			end "
               + "		else "
               + "			'N' "
               + "	end "
               + " end as tiecancon,"
               + " case when a2.co_tipdoc=102 then "
               + "	case when (a2.nd_cancon) > 0 then "
               + "          'S' "
               + "	else "
               + "          'N' "
               + "      end "
               + " else"
               + "  	case when (a1.nd_cancon) > 0 then "
               + "          'S' "
               + "	else "
               + "          'N' "
               + "      end "		
               + " end as tieguirem ,"
               + " a3.st_volfacmersindev "
               + " from tbm_detsoldevven as a "
               + " inner join tbm_detmovinv as a1 "
               + " on (a1.co_emp=a.co_emp  and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel )"
               + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp " 
               + " and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg ) "
               + " inner join tbm_cabsoldevven as a3 on (a3.co_emp=a.co_emp  and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
               + " inner join tbm_cabguirem as a4 on (a4.co_emp=a2.co_emp and a4.co_loc=a2.co_loc and a4.co_tipdoc=a2.co_tipdoc and a4.co_doc=a2.co_doc ) "
               + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc 
               + " and a.nd_canDev > 0 "
               + " and a2.st_meregrfisbod = 'S'"

               /*MODIFICACION POR RESERVAS 05-OCT-2017 */

                + " UNION "


                + " select (b.nd_canDev) as canDev, (b.nd_canVolFac) as canVolFac, (a4.nd_can) as can, "
                + "        case when a4.co_tipdoc=102 then "
                + "                (a4.nd_cancon + a4.nd_cannunrec)  "
                + "        else "
                + "                (detres.nd_cancon + detres.nd_cannunrec)  "
                + "        end as cantotguisec, "
                + "       case when a4.co_tipdoc=102 then "
                + "                (a4.nd_cancon)  "
                + "        else  "
                + "                (detres.nd_cancon)  "
                + "        end as cancon, "
                + "        case when a4.co_tipdoc=102 then "
                + "                (a4.nd_cannunrec) "
                + "        else "
                + "                (detres.nd_cannunrec)  "
                + "        end as cannunrec, "
                + "        case when a4.co_tipdoc=102 then "
                + "                ((a4.nd_can) - (a4.nd_cancon + a4.nd_cannunrec) )   "
                + "        else	"
                + "                ((detres.nd_can) - (detres.nd_cancon + detres.nd_cannunrec) )  "
                + "        end as canpendes, "
                + "        case when a4.co_tipdoc=102 then "
                + "                case when (a4.nd_cancon) > 0 then  "
                + "                        case when (a4.nd_cancon<=b.nd_canVolFac) then "
                + "                                'N' 	"
                + "                        else "		
                + "                                'S' 	"
                + "                        end  \n"
                + "                else \n"	
                + "                        'N' 	\n"
                + "                end   \n"
                + "        else       \n"
                + "                case when (detres.nd_cancon) > 0 then \n"
                + "                        case when (detres.nd_cancon<=b.nd_canVolFac) then \n"
                + "                                'N' 	\n"
                + "                        else \n"		
                + "                                'S' 	\n"
                + "                        end 	\n"
                + "                else \n"		
                + "                        'N' 	\n"
                + "                end  \n"
                + "        end as tiecancon, \n"
                + "        case when a4.co_tipdoc=102 then \n"
                + "                case when (a4.nd_cancon) > 0 then  \n"
                + "                       'S'  \n"
                + "                else  \n"
                + "                        'N'  \n"
                + "                end  \n"
                + "        else  \n"
                + "                case when (detres.nd_cancon) > 0 then  \n"
                + "                        'S' 	\n"
                + "                else  \n"
                + "                        'N'   \n"
                + "                end  \n"
                + "        end as tieguirem , a.st_volfacmersindev \n"

                + " from tbm_cabsoldevven as a \n"
                + " inner join tbm_detsoldevven as b \n"
                + " on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc) \n"
                + " inner join tbm_cabmovinv as a1 \n"
                + " on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel ) \n"
                + " inner join tbm_cabsegmovinv as s \n"
                + " on (s.co_emprelcabmovinv=a1.co_emp and s.co_locrelcabmovinv=a1.co_loc and s.co_tipdocrelcabmovinv=a1.co_tipdoc and s.co_docrelcabmovinv=a1.co_doc) \n"
                + " inner join tbm_cabsegmovinv as s2 \n"
                + " on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271) \n"
                + " INNER JOIN tbm_cabguirem as a3  \n"
                + " on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem ) \n"
                + " INNER JOIN tbm_detguirem as a4 \n"
                + " on(a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc and /*a4.co_reg=b.co_reg*/a4.co_itm=b.co_itm) \n"
                + " INNER JOIN tbm_detmovinv as detres \n"
                + " on (detres.co_emp=a4.co_emprel  and detres.co_loc=a4.co_locrel  \n"
                + " and detres.co_tipdoc=a4.co_tipdocrel and detres.co_doc=a4.co_docrel \n"
                + " and detres.co_reg=a4.co_regrel and detres.co_tipdoc<>228) \n"
                + " WHERE a.co_emp="+intCodEmp+" \n"
                + " and a.co_loc="+intCodLoc+" \n"
                + " and a.co_tipdoc="+intCodTipDoc+" \n"
                + " and a.co_doc="+intCodDoc+" \n"
                + " and b.nd_canDev > 0 \n"
                + " and a4.st_meregrfisbod = 'S' \n";

          /*MODIFICACION POR RESERVAS 05-OCT-2017 */
          

        System.out.println("ZafVen25_02._insertarDetConfEgre: (B) " + strSql);
        rstLoc=stmLoc.executeQuery(strSql);
        while (rstLoc.next()){
            if (st_volfac.equals("")) {
                st_volfac=rstLoc.getString("st_volfacmersindev");
            }
            if (st_orddes.equals("")) {
                st_orddes=rstLoc.getString("tieguirem");
            }
            if (rstLoc.getString("tiecancon").equals("S")){
                st_devtoddes="N";
                break;
            }
        }
        rstLoc.close();
        rstLoc=null;          
      }      
      
      strSql=" select * from ( "
      + " select case when st_meringegrfisbod='N' then nd_cannodevprv > 0 else cannunenv < nd_canDev end as estConfBod "
      + " from ( "
      + " select a.co_doc, a.nd_canDev, "
      + " abs(case when a2.nd_cannunrec is null then 0 else a2.nd_cannunrec end ) as cannunenv, "
      + " a1.st_meringegrfisbod, a.nd_cannodevprv "
      + " from tbm_detsoldevven as a "
      + " INNER JOIN tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
      + " LEFT JOIN  tbm_detingegrmerbod AS a2 on (a2.co_emp=a.co_emp and a2.co_loc=a.co_locrelcon and a2.co_tipdoc=a.co_tipdocrelcon and a2.co_doc=a.co_docrelcon and a2.co_reg=a.co_regrelcon ) "
      + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" "
      + " and a.nd_canDev > 0 " 
      //+ " and abs(a2.nd_cannunrec) > 0 " 
      + " ) as x "
      + " ) as x "
      + " where estConfBod=true "
      + " group by estConfBod ";
      
      System.out.println("ZafVen25_02._insertarDetConfEgre: (C)" + strSql);
      
      rstLoc=stmLoc.executeQuery(strSql);
      
      /*if(rstLoc.next()){
         //  SI PASA POR BODEGA
      }else {
          strSql=" UPDATE tbm_cabsoldevven SET st_impguiremaut='N' WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" ";
          stmLoc01.executeUpdate(strSql);
      }*/
      
      if(rstLoc.next()){
         //  SI PASA POR BODEGA          
          if (st_volfac.equals("S") && st_orddes.equals("N")){
            strSql=" UPDATE tbm_cabsoldevven SET st_impguiremaut='N' WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" ";
            System.out.println("ZafVen25_02._insertarDetConfEgre: (1)" + strSql);
            stmLoc01.executeUpdate(strSql);
          }
          if (st_volfac.equals("S") && st_orddes.equals("S") && (st_devtoddes.equals("S") || st_devtoddes.equals("") )){
            strSql=" UPDATE tbm_cabsoldevven SET st_impguiremaut='N' WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" ";
            System.out.println("ZafVen25_02._insertarDetConfEgre: (2)" + strSql);
            stmLoc01.executeUpdate(strSql);
          }
      }else {
          strSql=" UPDATE tbm_cabsoldevven SET st_impguiremaut='N' WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" ";
          System.out.println("ZafVen25_02._insertarDetConfEgre: (3)" + strSql);
          stmLoc01.executeUpdate(strSql);
      }
      rstLoc.close();
      rstLoc=null;
      
      //_getVerificaItmEgrFisBod(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );

        strSql="SELECT case when st_coninv = 'F' then 'F' else  case when can=cantotgs then 'C' else 'E' end end as  estconf "
        + " ,x.co_emp, x.co_loc, x.co_tipdoc, x.co_doc "                
        + " from ( "
        //+ " select a.st_coninv, sum(a1.nd_can) as can, sum(a1.nd_cantotguisec) as cantotgs "
        + " select a.st_coninv, sum(d.nd_can) as can, sum(d.nd_cancon + d.nd_cannunrec) as cantotgs, "
        + "	       d.co_emp, d.co_loc, d.co_tipdoc, d.co_doc "
        + " FROM tbm_cabguirem as a "
        + " INNER JOIN tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  "
        + " INNER JOIN tbm_detmovinv as d on  (a1.co_emprel= d.co_emp and a1.co_locrel=d.co_loc and a1.co_tipdocrel=d.co_tipdoc and a1.co_docrel = d.co_doc and a1.co_regrel=d.co_reg )  "
        + " where a.co_emp="+intCodEmpG+" and a.co_loc="+intCodLocG+" and a.co_tipdoc="+intCodTipDocG+" and a.co_doc = "+intCodDocG+" "
        + " group by a.st_coninv, d.co_emp,d.co_loc, d.co_tipdoc, d.co_doc ) as x ";          
      
      //}
      System.out.println("ZafVen25_02._insertarDetConfEgre: (4)" + strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
          strSql=" update tbm_cabmovinv SET st_coninv='"+rstLoc.getString("estconf")+"' WHERE co_emp="+rstLoc.getInt("co_emp")+" and co_loc="+rstLoc.getInt("co_loc")+" and co_tipdoc="+rstLoc.getInt("co_tipdoc")+" and co_doc="+rstLoc.getInt("co_doc")+" ";
          System.out.println("ZafVen25_02._insertarDetConfEgre: (5)" + strSql);          
          stmLoc01.executeUpdate(strSql);
      }
      rstLoc.close();
      rstLoc=null;

      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;
      
      blnRes=true;
      // José Marín M. 25/Nov/2014 CLIENTE RETIRA 
      // blnRes = blnActDetRelacionados;  // José Marín M. 25/Nov/2014 CLIENTE RETIRA 
      // José Marín M. 25/Nov/2014 CLIENTE RETIRA 
      
      //this.actualizaDetOrdDesRel(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);      
 }
 }catch(SQLException Evt) {  
     blnRes=false; 
     objUti.mostrarMsgErr_F1(jIntfra, Evt);  
 }catch(Exception Evt) { 
     blnRes=false; 
     
     objUti.mostrarMsgErr_F1(jIntfra, Evt); 
 }
 return blnRes;
}

/**
 * Permite obtener el numero de confirmacion de egreso
 * @param conn
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @return
 */
private int _getNumDocConfEgre(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc ){
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 int intNumDoc=-1;
  try{
      System.out.println("ZafVen25_02._getNumDocConfEgre");
    if(conn!=null){
      stmLoc=conn.createStatement();
        strSql="SELECT CASE WHEN (ne_ultdoc+1) is null THEN 1 ELSE ne_ultdoc+1 END AS numDoc FROM tbm_cabtipdoc " +
        " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc;
        System.out.println("ZafVen25_02.getNumDocConfEgre " + strSql);
        rstLoc = stmLoc.executeQuery(strSql);
        if(rstLoc.next())
            intNumDoc = rstLoc.getInt("numDoc");
        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;

 }}catch(java.sql.SQLException Evt) {  intNumDoc=-1; objUti.mostrarMsgErr_F1(jIntfra, Evt);  }
    catch(Exception Evt) { intNumDoc=-1; objUti.mostrarMsgErr_F1(jIntfra, Evt); }
 return intNumDoc;
}

/**
 *
 * @param conn
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @param intCodDoc
 * @return 0= no Hay guia para esta factura <br> 1= esquema anterior <br> 2= esquema actual <br> 3 = no esta impreso od <br> -1 = error 
 */
public int _getVerEstSolDevVen(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 int intEstSol=0;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
     System.out.println("ZafVen25_02._getVerEstSolDevVen");
    if(conn!=null){
      stmLoc=conn.createStatement();

        strSql="select * from ( "
        + " select a3.ne_numdoc, case when a3.ne_numorddes is null then 0 else a3.ne_numorddes end as num_od from tbm_cabsoldevven as a "
        + " inner join tbm_cabmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel ) "
        + " Inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc )  "
        + " inner join tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc )  "
        + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+"  "
        + " ) as x group by ne_numdoc, num_od ";
        System.out.println("ZafVen25_02.getVerEstSolDevVen " + strSql);
        rstLoc = stmLoc.executeQuery(strSql);
        if(rstLoc.next()){
           if(rstLoc.getInt("ne_numdoc") > 0 ) intEstSol = 1; // ESQUEMA DE ANTERIOR DE GUIAS
            else if(rstLoc.getInt("num_od") > 0 ) intEstSol = 2; // ESQUEMA DE ACTUAL DE GUIAS
            else   intEstSol = 3; // NO ESTA IMPRESO 
        }
        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;

 }}catch(java.sql.SQLException Evt) {  intEstSol=-1; objUti.mostrarMsgErr_F1(jIntfra, Evt);  }
    catch(Exception Evt) { intEstSol=-1; objUti.mostrarMsgErr_F1(jIntfra, Evt); }
 return intEstSol;
}

/**
 *
 * @param conn
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @param intCodDoc
 * @return  0 = no existe genereado nueva od <br> 1 = hay generado nueva od <br> -1 = Error
 */
public int _getVerOD(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 int intEstOD=0;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();

        strSql="select * from ( "
        + " select  abs(a1.nd_can) as canven, abs(a2.nd_can) as canod, case when  abs(a1.nd_can) > abs(a2.nd_can) then true else false end as est  from tbm_detsoldevven as a "
        + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
        + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg ) "
        + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+"  "
        + "  ) as x where  est = true ";
        System.out.println("ZafVen25_02._getVerOD: " + strSql);
        rstLoc = stmLoc.executeQuery(strSql);
        if(rstLoc.next()){
           intEstOD=1;
        }
        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;

 }}catch(java.sql.SQLException Evt) {  intEstOD=-1; objUti.mostrarMsgErr_F1(jIntfra, Evt);  }
    catch(Exception Evt) { intEstOD=-1; objUti.mostrarMsgErr_F1(jIntfra, Evt); }
 return intEstOD;
}

/**
 * Genera nueva Orden de despacho
 * @param conn
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @param intCodDoc
 * @return
 */
public boolean _generaNuvaODCanDenSol(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc, rstLoc01;
 String strSql="";
 try{
     System.out.println("ZafVen25_02._generaNuvaODCanDenSol");
   if(conn!=null){
       stmLoc=conn.createStatement();
       stmLoc01=conn.createStatement();

         strSql="SELECT * from ( "
         + " select a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a3.st_reg, case when a3.ne_numorddes is null then 0 else a3.ne_numorddes end as orddes, a3.ne_numdoc  "
         + " ,a.co_emp as coempfac, a.co_locrel colocfac, a.co_tipdocrel as cotipdocfac, a.co_docrel as codocfac  "
         + " from tbm_cabsoldevven as a  "
         + " inner join tbm_cabmovinv as a1 on (a1.co_emp=a.co_emp  and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel )  "
         + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp  and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc  )  "
         + " inner join tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp  and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc  )  "
         + " where a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+" AND a.co_tipdoc="+intCodTipDoc+" AND a.co_doc="+intCodDoc+"  "
         + " group by a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a3.st_reg, a3.ne_numorddes, a3.ne_numdoc, a.co_emp, a.co_locrel, a.co_tipdocrel, a.co_docrel  "
         + " ) as x  where  orddes=0 and ne_numdoc=0  ";
         System.out.println("ZafVen25_02.generaNuevaODCanDenSol 1 " + strSql);
         rstLoc=stmLoc.executeQuery(strSql);
         if(rstLoc.next()){

                strSql="SELECT * from ( "
                + " select (abs(canfac)-abs(case when cantotdev is null then 0 else cantotdev end)) as salPengui, * from ( "
                + " select  "
                + " ( "
                + " select sum(x.nd_canDev) from tbm_detsoldevven as x "
                + " inner join tbm_cabsoldevven as x1 on (x1.co_emp=x.co_emp  and x1.co_loc=x.co_loc and x1.co_tipdoc=x.co_tipdoc and x1.co_doc=x.co_doc ) "
                + " where x.co_emp=a.co_emp and x.co_locrel=a.co_loc and x.co_tipdocrel=a.co_tipdoc and x.co_docrel=a.co_doc  and x.co_regrel=a.co_reg and x1.st_reg='A' "
                + " and x1.st_aut='A' "
                + " ) as cantotdev "
                + "  ,a.nd_can as canfac "
                + " from tbm_detmovinv as a "
                + " where a.co_emp="+rstLoc.getInt("coempfac")+" AND a.co_loc="+rstLoc.getInt("colocfac")+" AND a.co_tipdoc="+rstLoc.getInt("cotipdocfac")+" AND a.co_doc="+rstLoc.getInt("codocfac")+"  "
                + " ) as x "
                + " ) as x  where  salPengui > 0 ";
                System.out.println("ZafVen25_02.generaNuevaODCanDenSol 2 " + strSql);
                rstLoc01=stmLoc01.executeQuery(strSql);
                if(rstLoc01.next()){

                    if(_generarNueGuiRemDenCan(conn, rstLoc.getInt("coempfac"), rstLoc.getInt("colocfac"), rstLoc.getInt("cotipdocfac"), rstLoc.getInt("codocfac"), rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc") )){
                       blnRes=true;
                    }
                }
                rstLoc01.close();
                rstLoc01=null;
         }
         rstLoc.close();
         rstLoc=null;
         stmLoc.close();
         stmLoc=null;

  }}catch(java.sql.SQLException Evt) {   blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt); }
 return blnRes;
}

private boolean _generarNueGuiRemDenCan(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpG, int intCodLocG, int intCodTipDocG, int intCodDocG ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 int intCodDocNueGui=0;
 try{
     System.out.println("ZafVen25_02._generarNueGuiRemDenCan");
    if(conn!=null){
       stmLoc=conn.createStatement();

     intCodDocNueGui=_getCodDocConfEgre( conn, intCodEmpG, intCodLocG, intCodTipDocG, "tbm_cabguirem" );

     strSql=" UPDATE  tbm_cabguirem set st_reg='I' WHERE co_emp="+intCodEmpG+" and co_loc="+intCodLocG+" and co_tipdoc="+intCodTipDocG+" and co_doc="+intCodDocG+" ";
     System.out.println("ZafVen25_02._generarNueGuiRemDenCan "  + strSql);
     stmLoc.executeUpdate(strSql);

  if(_insertarCabGuiRem(conn, intCodEmpG, intCodLocG, intCodTipDocG, intCodDocG, intCodDocNueGui  ) ){
   if(_insertarDetGuiRem(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmpG, intCodLocG, intCodTipDocG, intCodDocG,  intCodDocNueGui  ) ){
        blnRes=true;
    }else conn.rollback();
  }else conn.rollback();

  stmLoc.close();
  stmLoc=null;

 }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt); }
 return blnRes;
}

private boolean _insertarDetGuiRem(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpG, int intCodLocG, int intCodTipDocG, int intCodDocG, int intCodDocNueGui ) {
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc;
 String strSql="";
 int intNumReg=0;
 try{
     System.out.println("ZafVen25_02._insertarDetGuiRem");
    if(conn!=null){
      stmLoc=conn.createStatement();
      stmLoc01=conn.createStatement();


      strSql="SELECT * from ( "
      + " select (abs(canfac)-abs(case when cantotdev is null then 0 else cantotdev end)) as salPengui, * from ( "
      + " select  "
      + " ( "
      + " select sum(x.nd_canDev) from tbm_detsoldevven as x "
      + " inner join tbm_cabsoldevven as x1 on (x1.co_emp=x.co_emp  and x1.co_loc=x.co_loc and x1.co_tipdoc=x.co_tipdoc and x1.co_doc=x.co_doc ) "
      + " where x.co_emp=a.co_emp and x.co_locrel=a.co_loc and x.co_tipdocrel=a.co_tipdoc and x.co_docrel=a.co_doc  and x.co_regrel=a.co_reg and x1.st_reg='A' "
      + " and x1.st_aut='A' "
      + " ) as cantotdev "
      + "  ,a.nd_can as canfac  , a.co_itm, a.tx_codalt, a.tx_nomitm, a.tx_unimed  , a.st_meringegrfisbod as st_meregrfisbod, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg  "
      + " from tbm_detmovinv as a "
      + " where a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+" AND a.co_tipdoc="+intCodTipDoc+" AND a.co_doc="+intCodDoc+"  "
      + " ) as x "
      + " ) as x  where  salPengui > 0 ";
      System.out.println("ZafVen25_02._insertarDetGuiRem 1 " + strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){

         intNumReg++;

         strSql=" INSERT INTO tbm_detguirem(co_emp, co_loc, co_tipdoc, co_doc, co_reg,  " +
         "  co_itm, tx_codalt, tx_nomitm, tx_unimed, nd_can, st_regrep, nd_pestotkgr, tx_obs1, st_meregrfisbod " +
         " ,co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel ) " +
         " VALUES ("+intCodEmpG+", "+intCodLocG+", "+intCodTipDocG+", " +
         " "+intCodDocNueGui+", "+intNumReg+", "+
         " "+rstLoc.getString("co_itm")+", '"+rstLoc.getString("tx_codalt")+"', " +
         " '"+rstLoc.getString("tx_nomitm")+"' ,'"+rstLoc.getString("tx_unimed")+"', " +
         " "+rstLoc.getString("salPengui")+" ,'I' , 0,'' " +
         " ,'"+rstLoc.getString("st_meregrfisbod")+"', "+rstLoc.getInt("co_emp")+", "+rstLoc.getInt("co_loc")+", "+rstLoc.getInt("co_tipdoc")+"" +
         " , "+rstLoc.getInt("co_doc")+", "+rstLoc.getInt("co_reg")+"   )";

         strSql+=" ; UPDATE  tbm_detguirem set co_emprel=null, co_locrel=null, co_tipdocrel=null, co_docrel=null, co_regrel=null  "
         +" WHERE co_emp="+intCodEmpG+" and co_loc="+intCodLocG+" and co_tipdoc="+intCodTipDocG+" and co_doc="+intCodDocG+" ";
        System.out.println("ZafVen25_02._insertarDetGuiRem 2 " + strSql);
         stmLoc01.executeUpdate(strSql);
         blnRes=true;
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;

 }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt); }
 return blnRes;
}

public boolean _activarODSinImp(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 try{
     System.out.println("ZafVen25_02._activarODSinImp ");
  if(conn!=null){
    stmLoc=conn.createStatement();

    strSql="update tbm_cabguirem set st_reg='A' from ( "
    + " select  a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc  from tbm_cabsoldevven as a "
    + " inner join tbm_cabmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel )  "
    + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc ) "
    + " inner join tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc )  "
    + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+"  and  a3.st_reg='I'  "
    + " group by  a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc   "
    + " ) as x  WHERE tbm_cabguirem.co_emp=x.co_emp and tbm_cabguirem.co_loc=x.co_loc and tbm_cabguirem.co_tipdoc=x.co_tipdoc and tbm_cabguirem.co_doc=x.co_doc ";
    System.out.println("ZafVen25_02._activarODSinImp 1  " + strSql);
    stmLoc.executeUpdate(strSql);
    stmLoc.close();
    stmLoc=null;
    blnRes=true;

 }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt); }
 return blnRes;
}

/**
 * Permite saber so la solicitud tiene confirmacion de egreso por mercaderia que no se despachara.
 * @param conn
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @param intCodDoc
 * @return  true = tiene confirmacion <br>  false =  no tiene o error 
 */
public boolean _getVerConfEgrOD(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
  if(conn!=null){
    stmLoc=conn.createStatement();
    System.out.println("ZafVen25_02._getVerConfEgrOD ");
    
    strSql="select a.co_emp, a.co_locrelcon, a.co_tipdocrelcon, a.co_docrelcon from tbm_detsoldevven as a "
    + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+"  "
    + " and a.co_docrelcon is not null "
    + " ORDER BY a.co_emp, a.co_locrelcon, a.co_tipdocrelcon, a.co_docrelcon ";
    System.out.println("ZafVen25_02._getVerConfEgrOD: 1 " + strSql);
    rstLoc = stmLoc.executeQuery(strSql);
    if(rstLoc.next()){
        blnRes=_anularConfEgr(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_locrelcon"), rstLoc.getInt("co_tipdocrelcon"), rstLoc.getInt("co_docrelcon") );
    }else blnRes=true;
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
   
 }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt); }
 return blnRes;
}

 private boolean _anularConfEgr(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
  boolean blnRes=false;
  try{
      System.out.println("ZafVen25_02._anularConfEgr ");
    if(conn!=null){

        if(_anularConfEgrCab(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc )){
         if(_anularConfEgrDet(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc )){
            blnRes=true;
        }}
  }}catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);  }
  return blnRes;
}

private boolean _anularConfEgrCab(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  try{
      System.out.println("ZafVen25_02._anularConfEgrCab ");
     if(conn!=null){
       stmLoc=conn.createStatement();

       strSql="UPDATE tbm_cabingegrmerbod SET st_reg='I', co_usrMod="+objZafParSis.getCodigoUsuario()+", fe_ultmod="+objZafParSis.getFuncionFechaHoraBaseDatos()+" "
       + ", tx_obs2='Anulado desde autorizaion de solicitud de ventas. ' "
       + " WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" " +
       " AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc;
       System.out.println("ZafVen25_02._anularConfEgrCab: " + strSql);
       stmLoc.executeUpdate(strSql);
       stmLoc.close();
       stmLoc=null;
       blnRes=true;

  }}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(jIntfra, e);  }
    catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);  }
  return blnRes;
}

private boolean _anularConfEgrDet(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
  boolean blnRes=false;
  java.sql.Statement stmLoc, stmLoc01;
  java.sql.ResultSet rstLoc;
  String strSql="";
  double dlbCanConfnunrec=0;
  int intCodEmpG=0, intCodLocG=0, intCodTipDocG=0, intCodDocG=0;
  try{
      System.out.println("ZafVen25_02._anularConfEgrDet ");
     if(conn!=null){
        stmLoc=conn.createStatement();
        stmLoc01=conn.createStatement();
        
        strSql=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a.co_bod, abs(a1.nd_cannunrec) as canconfnunrec "
        + " ,a1.st_meregrfisbod, a.co_itm  "
        + " from tbm_detingegrmerbod as a  "
        + "  inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelguirem and a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem and a1.co_reg=a.co_regrelguirem )  "
        + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc= "+intCodDoc+"  ";
        System.out.println("ZafVen25_02._anularConfEgrDet: " + strSql);
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

             intCodEmpG=rstLoc.getInt("co_emp");
             intCodLocG=rstLoc.getInt("co_loc");
             intCodTipDocG=rstLoc.getInt("co_tipdoc");
             intCodDocG=rstLoc.getInt("co_doc");

             dlbCanConfnunrec = rstLoc.getDouble("canconfnunrec");

             if(rstLoc.getString("st_meregrfisbod").equals("S")){
                 strSql=" UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod+"+dlbCanConfnunrec+"  WHERE  co_emp="+rstLoc.getInt("co_emp")+" and co_bod="+rstLoc.getInt("co_bod")+" and co_itm="+rstLoc.getInt("co_itm");
                 System.out.println("ZafVen25_02._anularConfEgrDet (2): " + strSql);
                 stmLoc01.executeUpdate(strSql);
             }

             strSql=" UPDATE tbm_detguirem SET  nd_cannunrec=nd_cannunrec-"+dlbCanConfnunrec+", nd_cantotguisec=nd_cantotguisec-"+dlbCanConfnunrec+" "+
             " WHERE  co_emp="+rstLoc.getInt("co_emp")+" and co_loc="+rstLoc.getInt("co_loc")+" " +
             " and co_tipdoc="+rstLoc.getInt("co_tipdoc")+" and co_doc="+rstLoc.getInt("co_doc")+" "+
             " and co_reg="+rstLoc.getInt("co_reg");
             System.out.println("ZafVen25_02._anularConfEgrDet (3): " + strSql);
             stmLoc01.executeUpdate(strSql);
             blnRes=true;
                     
        }
        rstLoc.close();
        rstLoc=null;


          if(blnRes){
             strSql=" select case when cantotguisec > 0 then 'S' else 'N' end as sttieguisec "
             + " ,case when cantotconf <= 0 then 'P' else case when cantotconf < can then 'E' else case when cantotconf = can then 'C' end end end as  estconf "
             + " from ( "
             + " select sum(nd_Can) as can, sum(nd_cantotguisec) as cantotguisec,  sum(nd_cancon + nd_cannunrec ) as cantotconf "
             + " from  tbm_detguirem where co_emp="+intCodEmpG+" and co_loc="+intCodLocG+" "
             + " and  co_tipdoc="+intCodTipDocG+"  and co_doc="+intCodDocG+" "
             + " ) as x ";
             System.out.println("ZafVen25_02._anularConfEgrDet (4): " + strSql);
             rstLoc=stmLoc.executeQuery(strSql);
             if(rstLoc.next()){
                strSql=" UPDATE tbm_cabguirem SET st_coninv='"+rstLoc.getString("estconf")+"' "
                + " where co_emp="+intCodEmpG+" and co_loc="+intCodLocG+" and  co_tipdoc="+intCodTipDocG+" and co_doc= "+intCodDocG+" ";
                System.out.println("ZafVen25_02._anularConfEgrDet (5): " + strSql);
                stmLoc01.executeUpdate(strSql);
                blnRes=true;
             }
             rstLoc.close();
             rstLoc=null;
           }
        
        stmLoc.close();
        stmLoc=null;
        stmLoc01.close();
        stmLoc01=null;
  
  }}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(jIntfra, e);  }
    catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);  }
  return blnRes;
}

    public boolean isOrdenNoImpresa(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
        boolean blnRes=false;
        PreparedStatement pst=null;
        ResultSet rst=null;
        String strSql="";
        double can, canDev, canVolFac, canNoVolFac;
        String volfac;
        System.out.println("ZafVen25_02.isOrdenNoImpresa ");
        if (blnIsSegundaDev){
            strSql= " select co_emp, co_loc, co_tipdoc, co_doc, ne_numdoc, volfac, ordendespacho, can, canDev, canVolFac, canNoVolFac " + 
                    " from ( " +   
                    " select a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a1.ne_numdoc, a.st_volfacmersindev as volfac, " +
                    " case when a3.ne_numorddes is null then 0 else a3.ne_numorddes end as ordendespacho, " +
                    " sum(abs(a4.nd_can)) as can, sum(abs(a4.nd_canDev)) as canDev, sum(abs(a4.nd_canVolFac)) as canVolFac, sum(a4.nd_canDev - a4.nd_canVolFac) as canNoVolFac " + 
                    " from tbm_cabsoldevven as a " +    
                    " INNER JOIN tbr_cabmovinv as a5 on (a5.co_emp=a.co_emp and a5.co_loc=a.co_locrel and a5.co_tipdoc=a.co_tipdocrel and a5.co_doc=a.co_docrel ) " +    
                    " INNER JOIN tbr_cabmovinv as a6 on (a6.co_emp=a5.co_emp and a6.co_loc=a5.co_locrel and a6.co_tipdoc=a5.co_tipdocrel and a6.co_doc=a5.co_docrel ) " +    
                    " INNER JOIN tbm_cabmovinv as a1 on (a1.co_emp=a6.co_emp and a1.co_loc=a6.co_locrel and a1.co_tipdoc=a6.co_tipdocrel and a1.co_doc=a6.co_docrel ) " +    
                    " INNER JOIN tbm_detmovinv as a7 on (a7.co_emp=a1.co_emp and a7.co_loc=a1.co_loc and a7.co_tipdoc=a1.co_tipdoc and a7.co_doc=a1.co_doc ) " +    
                    " LEFT OUTER JOIN tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc ) " +    
                    " LEFT OUTER JOIN tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc ) " +     
                    " inner join tbm_detsoldevven as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoc and a4.co_doc=a.co_doc and a4.co_reg = a7.co_reg ) " +
                    " where a.co_emp= ? "+  
                    " and a.co_loc= ? "+
                    " and a.co_tipdoc= ? "+  
                    " and a.co_doc= ? "+   
                    " group by a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a1.ne_numdoc, a.st_volfacmersindev, a3.ne_numorddes " +   
                    /*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
                    " UNION "+
                    
                    " select a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a1.ne_numdoc, a.st_volfacmersindev as volfac, \n"+
                    " case when a3.ne_numorddes is null then 0 else a3.ne_numorddes end as ordendespacho, \n"+
                    " sum(abs(b.nd_can)) as can, sum(abs(b.nd_canDev)) as canDev, sum(abs(b.nd_canVolFac)) as canVolFac, sum(b.nd_canDev - b.nd_canVolFac) as canNoVolFac \n"+
                    " from tbm_cabsoldevven as a     \n"+
                    " inner join tbm_detsoldevven as b \n"+
                    " on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc)  \n"+
                    " INNER JOIN tbr_cabmovinv as a5 on (a5.co_emp=a.co_emp and a5.co_loc=a.co_locrel and a5.co_tipdoc=a.co_tipdocrel and a5.co_doc=a.co_docrel )  \n"+
                    " INNER JOIN tbr_cabmovinv as a6 on (a6.co_emp=a5.co_emp and a6.co_loc=a5.co_locrel and a6.co_tipdoc=a5.co_tipdocrel and a6.co_doc=a5.co_docrel ) \n"+
                    " INNER JOIN tbm_cabmovinv as a1 on (a1.co_emp=a6.co_emp and a1.co_loc=a6.co_locrel and a1.co_tipdoc=a6.co_tipdocrel and a1.co_doc=a6.co_docrel ) \n"+
                    " inner join tbm_cabsegmovinv as s \n"+
                    " on (s.co_emprelcabmovinv =a1.co_emp and s.co_locrelcabmovinv=a1.co_loc and s.co_tipdocrelcabmovinv=a1.co_tipdoc and s.co_docrelcabmovinv=a1.co_doc) \n"+
                    " inner join tbm_cabsegmovinv as s2   \n"+
                    " on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271)   \n"+
                    " inner JOIN tbm_cabguirem as a3    \n"+
                    " on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem) \n"+
                    " inner JOIN tbm_detguirem as a4  \n"+
                    " on(a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc and a4.co_reg=b.co_reg) \n"+
                    " INNER JOIN tbm_detmovinv as detres \n"+
                    " on (detres.co_emp=a4.co_emprel  and detres.co_loc=a4.co_locrel  and detres.co_tipdoc=a4.co_tipdocrel and detres.co_doc=a4.co_docrel and detres.co_reg=a4.co_regrel and detres.co_tipdoc<>228 and detres.co_tipdoc=294) \n"+                   
                    " where a.co_emp= ? "+  
                    " and a.co_loc= ? "+
                    " and a.co_tipdoc= ? "+  
                    " and a.co_doc= ? "+   
                    " group by a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a1.ne_numdoc, a.st_volfacmersindev, a3.ne_numorddes "+
                    " having not a3.ne_numorddes>0" +   
                    /*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
                    " ) as x ";
        }else{
            
            if(blnIsCosenco || blnIsEcuatosa ||blnIsDetopacio){  // COSENCO JoséMario 
                strSql= " select  volfac, can, canDev, canVolFac, canNoVolFac  \n"; 
                strSql+=" from (   \n"; 
                strSql+="          select   a.st_volfacmersindev as volfac, sum(abs(a4.nd_can)) as can, sum(abs(a4.nd_canDev)) as CANDEV,  \n"; 
                strSql+="                  sum(abs(a4.nd_canVolFac)) as canVolFac, sum(a4.nd_canDev - a4.nd_canVolFac) as CANNOVOLFAC  \n"; 
                strSql+="          from tbm_cabsoldevven as a \n"; 
                strSql+="          inner join tbm_detsoldevven as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoc and a4.co_doc=a.co_doc  )  \n"; 
                strSql+="          where a.co_emp=?  and a.co_loc=?  and a.co_tipdoc=?  and a.co_doc=?  \n"; 
                strSql+="          group by a.st_volfacmersindev \n" ;
                strSql+=") as x  \n"; 
           }
           else{ 
                 strSql= " select co_emp, co_loc, co_tipdoc, co_doc, ne_numdoc, volfac, ordendespacho, can, canDev, canVolFac, canNoVolFac " + 
                    " from ( " +   
                    " select a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a1.ne_numdoc, a.st_volfacmersindev as volfac, " +
                    " case when a3.ne_numorddes is null then 0 else a3.ne_numorddes end as ordendespacho, " +
                    " sum(abs(a4.nd_can)) as can, sum(abs(a4.nd_canDev)) as canDev, sum(abs(a4.nd_canVolFac)) as canVolFac, sum(a4.nd_canDev - a4.nd_canVolFac) as canNoVolFac " + 
                    " from tbm_cabsoldevven as a " +    
                    " inner join tbm_cabmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel ) " +    
                    " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc ) " +    
                    " inner join tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc ) " +     
                    " inner join tbm_detsoldevven as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoc and a4.co_doc=a.co_doc and a4.co_reg = a2.co_reg ) " +
                    " where a.co_emp= ? "+  
                    " and a.co_loc= ? "+
                    " and a.co_tipdoc= ? "+  
                    " and a.co_doc= ? "+   
                    " group by a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a1.ne_numdoc, a.st_volfacmersindev, a3.ne_numorddes " +   
					/*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
                    " union \n"

                    +" select a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a1.ne_numdoc, a5.st_volfacmersindev as volfac, " 
                    +" case when a3.ne_numorddes is null then 0 else a3.ne_numorddes end as ordendespacho, " 
                    +" sum(abs(a.nd_can)) as can, sum(abs(a.nd_canDev)) as canDev, sum(abs(a.nd_canVolFac)) as canVolFac, sum(a.nd_canDev - a.nd_canVolFac) as canNoVolFac " 
                    +"    FROM tbm_detsoldevven as a  \n"  
                    +"    inner join tbm_cabsoldevven as a5 \n"  
                    +"    on (a5.co_emp=a.co_emp  and a5.co_loc=a.co_loc and a5.co_tipdoc=a.co_tipdoc and a5.co_doc=a.co_doc )  \n"  
                    +"    INNER JOIN tbm_cabmovinv as a1 \n"  
                    +"   on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel ) \n"    
                    +"    inner join tbm_cabsegmovinv as s \n"  
                    +"    on (s.co_emprelcabmovinv =a1.co_emp and s.co_locrelcabmovinv=a1.co_loc and s.co_tipdocrelcabmovinv=a1.co_tipdoc and s.co_docrelcabmovinv=a1.co_doc) \n"  
                    +"    inner join tbm_cabsegmovinv as s2 \n"  
                    +"    on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271) \n"  
                    +"    INNER JOIN tbm_cabguirem as a3  \n"  
                    +"    on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem and a1.ne_numorddes=a3.ne_numorddes) \n"  
                    +"    INNER JOIN tbm_detguirem as a4 \n"  
                    +"    on(a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc and a4.co_reg=a.co_reg) \n"  
                    +"    INNER JOIN tbm_detmovinv as detres \n"  
                    +"   on (detres.co_emp=a4.co_emprel  and detres.co_loc=a4.co_locrel  and detres.co_tipdoc=a4.co_tipdocrel and detres.co_doc=a4.co_docrel and detres.co_reg=a4.co_regrel and detres.co_tipdoc<>228 and detres.co_tipdoc=294)"                         
                    +"   where a5.co_emp= ? "
                    +"   and a5.co_loc= ? "
                    +"   and a5.co_tipdoc= ? "
                    +"   and a5.co_doc= ? "
                    +"   group by a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a1.ne_numdoc, a5.st_volfacmersindev, a3.ne_numorddes " 

                    +" ) as x ";
					/*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
           }
        }
        
        try{
            pst = conn.prepareStatement(strSql);
            pst.setInt(1, intCodEmp);
            pst.setInt(2, intCodLoc);
            pst.setInt(3, intCodTipDoc);
            pst.setInt(4, intCodDoc);
			/*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
            pst.setInt(5, intCodEmp);
            pst.setInt(6, intCodLoc);
            pst.setInt(7, intCodTipDoc);
            pst.setInt(8, intCodDoc);
			/*modificado CMATEO 3 DE OCTUBRE 2017 POR RESERVAS*/
            System.out.println("ZafVen25_02.isOrdenNoImpresa: " + strSql);
            rst=pst.executeQuery();
            
            if (rst.next()){
                volfac = rst.getString("volfac");
                can = objUti.redondear(rst.getDouble("can"), 0); 
                canDev = objUti.redondear(rst.getDouble("canDev"), 0); 
                canVolFac = objUti.redondear(rst.getDouble("canVolFac"), 0);  
                canNoVolFac = objUti.redondear(rst.getDouble("canNoVolFac"), 0); 
                if (volfac.equals("N")) {
                    if ( canDev == canNoVolFac )  //CanDev == canNOvolFac (cantidad NO volver a facturar) Se devuelve todo <JoséMario> 
                        blnRes=_anularOrdDesGenODNueSal(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
                    //AKi DEBERIA IR EL GENERAR OD POR LA // José marín M 29/Sep/2014
                } 
                if (volfac.equals("S")) {
                    if ( canDev == canVolFac ) //CanDev == CanVolFac 
                        blnRes=_getVerificaItmEgrFisBod(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
                    if ( canDev > canVolFac ) 
                        blnRes=_getVerGenConf(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
                }                
            }
            
        }catch(SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jIntfra, e);
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jIntfra, e);
        }finally{
            try{
                if (rst != null)
                    rst.close();
                rst=null;
                
                if (pst != null)
                    pst.close();
                pst=null;
            }catch(Throwable e){
                e.printStackTrace();
            }
        }
        return blnRes;
    }
    
    private boolean actualizaDetDocRelacionados(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCoReg){
        boolean blnRes=false;
        boolean blnResLoc=false;
        PreparedStatement pstLoc=null, pstLoc1=null, pstLoc2=null, pstLoc3=null;
        ResultSet rstLoc=null;
        String strSql="", strSql1="", strSql2="", strSql3=""; 
        int intCanMov=0, intCanDev=0, intCan=0, intCanNunRec=0, intCanTotGuiSec=0;
        String strTipCon="";
        strSql=" select a4.co_emp as co_empgui, a4.co_loc as co_locgui, a4.co_tipdoc as co_tipdocgui, a4.co_doc as co_docgui, a4.co_reg as co_reggui, "
                + " a4.nd_can, (a4.nd_cancon + a4.nd_cannunrec) as nd_cantotguisec, "
                + " a6.ne_numorddes, a6.st_coninv, a.nd_can as nd_canmov, a.nd_canDev, a4.st_meregrfisbod, a3.co_emp as co_empitm, a4.co_itm, a3.co_bod, "
                + " a2.co_emprel, a2.co_locrel, a2.co_tipdocrel, a2.co_docrel, a2.co_regrel "
                + " from tbm_detsoldevven as a "
                + " inner join tbm_detmovinv as a5 on (a5.co_emp=a.co_emp and a5.co_loc=a.co_locrel and a5.co_tipdoc=a.co_tipdocrel and a5.co_doc=a.co_docrel and a5.co_reg=a.co_regrel ) "
                + " inner join tbr_detmovinv as a1 on (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoc=a5.co_tipdoc and a1.co_doc=a5.co_doc and a1.co_reg=a5.co_reg ) "
                + " inner join tbr_detmovinv as a2 on (a2.co_emprel=a1.co_emprel and a2.co_locrel=a1.co_locrel and a2.co_tipdocrel=a1.co_tipdocrel and a2.co_docrel=a1.co_docrel and a2.co_regrel=a1.co_regrel and (a2.co_emp != a5.co_emp or a2.co_loc != a5.co_loc or a2.co_tipdoc != a5.co_tipdoc or a2.co_doc != a5.co_doc or a2.co_reg != a5.co_reg) ) "
                + " inner join tbm_detmovinv as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc and a3.co_reg=a2.co_reg) "
                + " inner join tbm_detguirem as a4 on (a4.co_emprel=a3.co_emp and a4.co_locrel=a3.co_loc and a4.co_tipdocrel=a3.co_tipdoc and a4.co_docrel=a3.co_doc and a4.co_regrel=a3.co_reg) "
                + " inner join tbm_cabguirem as a6 on (a6.co_emp=a4.co_emp and a6.co_loc=a4.co_loc and a6.co_tipdoc=a4.co_tipdoc and a6.co_doc=a4.co_doc ) "
                + " where a.co_emp=? and a.co_loc=? and a.co_tipdoc=? and a.co_doc=? and a.co_reg=? ";
        
        strSql1=" update tbm_invbod set nd_canegrbod=nd_canegrbod-? where co_emp=? and co_bod=? and co_itm=? ";        
        strSql2=" update tbm_detguirem set nd_cannunrec = nd_cannunrec + ?, nd_cantotguisec=nd_cantotguisec+? where co_emp=? and co_loc=? and co_tipdoc=? and co_doc=? and co_reg=? ";
        strSql3=" update tbm_detmovinv set nd_cannunrec = nd_cannunrec + ? where co_emp=? and co_loc=? and co_tipdoc=? and co_doc=? and co_reg=? ";
        try{
            if (conn != null){
                pstLoc=conn.prepareStatement(strSql);
                pstLoc.setInt(1, intCodEmp);
                pstLoc.setInt(2, intCodLoc);
                pstLoc.setInt(3, intCodTipDoc);
                pstLoc.setInt(4, intCodDoc);
                pstLoc.setInt(5, intCoReg);
                
                rstLoc=pstLoc.executeQuery();
                while(rstLoc.next()){                       
                    strTipCon=rstLoc.getString("st_coninv");                    
                    intCanMov=rstLoc.getInt("nd_canmov");
                    intCanDev=rstLoc.getInt("nd_canDev");
                    intCan=rstLoc.getInt("nd_can");
                    intCanTotGuiSec= rstLoc.getInt("nd_cantotguisec");
                    
                    if (strTipCon.equals("C")){
                        blnRes=true;
                        blnResLoc=false;
                        //break;
                    }else{
                        if (strTipCon.equals("P")){
                            if (intCanDev==intCanMov){
                                if (intCanDev >=intCan) 
                                    intCanNunRec=intCan;
                                if (intCanDev < intCan) 
                                    intCanNunRec=intCanDev;
                                
                                blnRes=true;
                                blnResLoc=true;
                            }
                            if (intCanDev < intCanMov){
                                if (intCanDev >=intCan) 
                                    intCanNunRec=intCan;
                                if (intCanDev < intCan) 
                                    intCanNunRec=intCanDev;
                                blnRes=true;
                                blnResLoc=true;
                            }
                        }else{
                            if (strTipCon.equals("E")){
                                blnRes=true;                                
                                if (intCanDev==intCanMov){
                                    if (intCanDev >=intCan) 
                                        intCanNunRec=intCan;
                                    if (intCanDev < intCan) 
                                        intCanNunRec=intCanDev;                                    
                                    
                                    blnRes=true;
                                    blnResLoc=true;
                                }
                                if (intCanDev < intCanMov){
                                    if (intCanDev >=intCan) 
                                        intCanNunRec=intCan;
                                    if (intCanDev < intCan) 
                                        intCanNunRec=intCanDev;
                                    blnRes=true;
                                    blnResLoc=true;
                                }
                            }
                        }
                    }
                    
                    if (intCanTotGuiSec > 0){
                        if ((intCanTotGuiSec - intCanNunRec) == 0) 
                            blnResLoc=false;
                        else
                            if ((intCanTotGuiSec - intCanNunRec) < 0){
                                intCanNunRec=intCan-intCanTotGuiSec; 
                                blnResLoc=true; 
                            }                         
                    }
                    
                    if (blnResLoc){
                        pstLoc2=conn.prepareStatement(strSql2);
                        pstLoc2.setInt(1, intCanNunRec );
                        pstLoc2.setInt(2, intCanNunRec );
                        pstLoc2.setInt(3, rstLoc.getInt("co_empgui") );
                        pstLoc2.setInt(4, rstLoc.getInt("co_locgui") );
                        pstLoc2.setInt(5, rstLoc.getInt("co_tipdocgui") );
                        pstLoc2.setInt(6, rstLoc.getInt("co_docgui") );
                        pstLoc2.setInt(7, rstLoc.getInt("co_reggui") ); 
                        pstLoc2.executeUpdate();                    
                        
                        pstLoc3=conn.prepareStatement(strSql3);
                        pstLoc3.setInt(1, intCanNunRec );
                        pstLoc3.setInt(2, rstLoc.getInt("co_emprel") );
                        pstLoc3.setInt(3, rstLoc.getInt("co_locrel") );
                        pstLoc3.setInt(4, rstLoc.getInt("co_tipdocrel") );
                        pstLoc3.setInt(5, rstLoc.getInt("co_docrel") );
                        pstLoc3.setInt(6, rstLoc.getInt("co_regrel") ); 
                        pstLoc3.executeUpdate();                    
                        
                        if(rstLoc.getString("st_meregrfisbod").equals("S")){
                            pstLoc1=conn.prepareStatement(strSql1);
                            pstLoc1.setInt(1, intCanNunRec );
                            pstLoc1.setInt(2, rstLoc.getInt("co_empitm") );
                            pstLoc1.setInt(3, rstLoc.getInt("co_bod") );
                            pstLoc1.setInt(4, rstLoc.getInt("co_itm") );
                            pstLoc1.executeUpdate();
                        }
                    }                      
                }                                
            }
        }catch(SQLException e){
            blnRes=false;
            e.printStackTrace();
        }catch(Exception e){
            blnRes=false;
            e.printStackTrace();
        }finally{
            try{
                if (rstLoc != null)
                    rstLoc.close();
                rstLoc=null;
                
                if (pstLoc1 != null)
                    pstLoc1.close();
                pstLoc1=null;                
                
                if (pstLoc2 != null)
                    pstLoc2.close();
                pstLoc2=null;                                
                
                if (pstLoc3 != null)
                    pstLoc3.close();
                pstLoc3=null;                
                
                if (pstLoc != null)
                    pstLoc.close();
                pstLoc=null;                 
            }catch(Throwable e){
                e.printStackTrace();
            }
        }
        
        return blnRes;        
    }       

    private boolean actualizaCabDocRelacionados(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
        boolean blnRes=false;
        PreparedStatement pstLoc=null, pstLoc1=null, pstLoc2=null, pstLoc3=null, pstLoc4=null;
        ResultSet rstLoc=null, rstLoc1=null, rstLoc2=null;
        String strSql="", strSql1="", strSql2="", strSql3="", strSql4=""; 
        String strObservacion="";
        strSql=" select a4.co_emp as co_empgui, a4.co_loc as co_locgui, a4.co_tipdoc as co_tipdocgui, a4.co_doc as co_docgui, "
                + " a2.co_emprel, a2.co_locrel, a2.co_tipdocrel, a2.co_docrel "
                + " from tbm_detsoldevven as a "
                + " inner join tbm_detmovinv as a5 on (a5.co_emp=a.co_emp and a5.co_loc=a.co_locrel and a5.co_tipdoc=a.co_tipdocrel and a5.co_doc=a.co_docrel and a5.co_reg=a.co_regrel ) "
                + " inner join tbr_detmovinv as a1 on (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoc=a5.co_tipdoc and a1.co_doc=a5.co_doc and a1.co_reg=a5.co_reg ) "
                + " inner join tbr_detmovinv as a2 on (a2.co_emprel=a1.co_emprel and a2.co_locrel=a1.co_locrel and a2.co_tipdocrel=a1.co_tipdocrel and a2.co_docrel=a1.co_docrel and a2.co_regrel=a1.co_regrel and (a2.co_emp != a5.co_emp or a2.co_loc != a5.co_loc or a2.co_tipdoc != a5.co_tipdoc or a2.co_doc != a5.co_doc or a2.co_reg != a5.co_reg) ) "
                + " inner join tbm_detmovinv as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc and a3.co_reg=a2.co_reg) "
                + " inner join tbm_detguirem as a4 on (a4.co_emprel=a3.co_emp and a4.co_locrel=a3.co_loc and a4.co_tipdocrel=a3.co_tipdoc and a4.co_docrel=a3.co_doc and a4.co_regrel=a3.co_reg) "
                + " where a.co_emp=? and a.co_loc=? and a.co_tipdoc=? and a.co_doc=?"
                + " group by a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_emprel, a2.co_locrel, a2.co_tipdocrel, a2.co_docrel, a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc ";
        
        strSql1=" select case when st_coninv = 'F' then 'F' else  case when can=cantotgs then 'C' else 'E' end end as  estconf from ( "
                + " select a.st_coninv, sum(a1.nd_can) as can, sum(a1.nd_cancon + a1.nd_cannunrec) as cantotgs "
                + " from tbm_cabguirem as a "
                + " inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  "
                + " where a.co_emp=? and a.co_loc=? and a.co_tipdoc=? and a.co_doc=? "
                + " group by a.st_coninv  ) as x ";
        
        strSql2=" update tbm_cabguirem SET st_coninv=? where co_emp=? and co_loc=? and co_tipdoc=? and co_doc=? ";
        
        strSql3=" select case when st_coninv = 'F' then 'F' else  case when can=cantotgs then 'C' else 'E' end end as  estconf from ( "
                + " select a.st_coninv, sum(a1.nd_can) as can, sum(a1.nd_cancon + a1.nd_cannunrec) as cantotgs "
                + " from tbm_cabmovinv as a "
                + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  "
                + " where a.co_emp=? and a.co_loc=? and a.co_tipdoc=? and a.co_doc=? "
                + " group by a.st_coninv  ) as x ";
        
        strSql4=" update tbm_cabmovinv SET st_coninv=? where co_emp=? and co_loc=? and co_tipdoc=? and co_doc=? ";
        
        try{
            if (conn != null){
                pstLoc=conn.prepareStatement(strSql);
                pstLoc.setInt(1, intCodEmp);
                pstLoc.setInt(2, intCodLoc);
                pstLoc.setInt(3, intCodTipDoc);
                pstLoc.setInt(4, intCodDoc);
                
                rstLoc=pstLoc.executeQuery();
                while(rstLoc.next()){
                    /** ORDDES RELACIONADA */
                    pstLoc1=conn.prepareStatement(strSql1);
                    pstLoc1.setInt(1, rstLoc.getInt("co_empgui") );
                    pstLoc1.setInt(2, rstLoc.getInt("co_locgui") );
                    pstLoc1.setInt(3, rstLoc.getInt("co_tipdocgui") );
                    pstLoc1.setInt(4, rstLoc.getInt("co_docgui") );
                    
                    rstLoc1=pstLoc1.executeQuery();
                    
                    while (rstLoc1.next()){
                        pstLoc2=conn.prepareStatement(strSql2);
                        pstLoc2.setString(1, rstLoc1.getString("estconf") );
                        pstLoc2.setInt(2, rstLoc.getInt("co_empgui") );
                        pstLoc2.setInt(3, rstLoc.getInt("co_locgui") );
                        pstLoc2.setInt(4, rstLoc.getInt("co_tipdocgui") );
                        pstLoc2.setInt(5, rstLoc.getInt("co_docgui") );
                        
                        pstLoc2.executeUpdate(); 
                    }
                    
                    /** DOCUMENTO RELACIONADO */
                    pstLoc3=conn.prepareStatement(strSql3);
                    pstLoc3.setInt(1, rstLoc.getInt("co_emprel") );
                    pstLoc3.setInt(2, rstLoc.getInt("co_locrel") );
                    pstLoc3.setInt(3, rstLoc.getInt("co_tipdocrel") );
                    pstLoc3.setInt(4, rstLoc.getInt("co_docrel") );
                    
                    rstLoc2=pstLoc3.executeQuery();
                    
                    while (rstLoc2.next()){
                        pstLoc4=conn.prepareStatement(strSql4);
                        pstLoc4.setString(1, rstLoc2.getString("estconf") );
                        pstLoc4.setInt(2, rstLoc.getInt("co_emprel") );
                        pstLoc4.setInt(3, rstLoc.getInt("co_locrel") );
                        pstLoc4.setInt(4, rstLoc.getInt("co_tipdocrel") );
                        pstLoc4.setInt(5, rstLoc.getInt("co_docrel") );
                        
                        pstLoc4.executeUpdate(); 
                    }
                                                            
                }                    
                blnRes=true;
            }
        }catch(SQLException e){
            blnRes=false;
            e.printStackTrace();
        }catch(Exception e){
            blnRes=false;
            e.printStackTrace();
        }finally{
            try{
                if (rstLoc1 != null)
                    rstLoc1.close();
                rstLoc1=null;                
                
                if (rstLoc2 != null)
                    rstLoc2.close();
                rstLoc2=null;                
                
                if (rstLoc != null)
                    rstLoc.close();
                rstLoc=null;                
                
                if (pstLoc4 != null)
                    pstLoc4.close();
                pstLoc4=null;                
                
                if (pstLoc3 != null)
                    pstLoc3.close();
                pstLoc3=null;                
                
                if (pstLoc2 != null)
                    pstLoc2.close();
                pstLoc2=null;                
                
                if (pstLoc1 != null)
                    pstLoc1.close();
                pstLoc1=null;                
                
                if (pstLoc != null)
                    pstLoc.close();
                pstLoc=null;                 
            }catch(Throwable e){
                e.printStackTrace();
            }
        }         
        return blnRes;        
    }       
    
    private boolean actualizaDetOrdDesRel(Connection conn, int intCodEmpDev, int intCodLocDev, int intCodTipDocDev, int intCodDocDev){
        boolean blnRes=false;
        PreparedStatement pstLoc=null, pstLoc2=null, pstLoc3=null;
        ResultSet rstLoc=null;
        String strSql="", strSql1=""; 
        String strTipCon="";
        strSql=" select a2.co_emprel as co_emp, a2.co_locrel as co_loc, a2.co_tipdocrel as co_tipdoc, a2.co_docrel as co_doc, a2.co_regrel as co_reg, "
                + " a3.co_emp as co_emprel, a3.co_loc as co_locrel, a3.co_tipdoc as co_tipdocrel, a3.co_doc as co_docrel, a3.co_reg as co_regrel, "
                + " a6.st_coninv "
                + " from tbm_detsoldevven as a "
                + " inner join tbm_detmovinv as a5 on (a5.co_emp=a.co_emp and a5.co_loc=a.co_locrel and a5.co_tipdoc=a.co_tipdocrel and a5.co_doc=a.co_docrel and a5.co_reg=a.co_regrel ) "
                + " inner join tbr_detmovinv as a1 on (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoc=a5.co_tipdoc and a1.co_doc=a5.co_doc and a1.co_reg=a5.co_reg ) "
                + " inner join tbr_detmovinv as a2 on (a2.co_emprel=a1.co_emprel and a2.co_locrel=a1.co_locrel and a2.co_tipdocrel=a1.co_tipdocrel and a2.co_docrel=a1.co_docrel and a2.co_regrel=a1.co_regrel and (a2.co_emp != a5.co_emp or a2.co_loc != a5.co_loc or a2.co_tipdoc != a5.co_tipdoc or a2.co_doc != a5.co_doc or a2.co_reg != a5.co_reg) ) "
                + " inner join tbm_detmovinv as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc and a3.co_reg=a2.co_reg) "
                + " inner join tbm_detguirem as a4 on (a4.co_emprel=a3.co_emp and a4.co_locrel=a3.co_loc and a4.co_tipdocrel=a3.co_tipdoc and a4.co_docrel=a3.co_doc and a4.co_regrel=a3.co_reg) "
                + " inner join tbm_cabguirem as a6 on (a6.co_emp=a4.co_emp and a6.co_loc=a4.co_loc and a6.co_tipdoc=a4.co_tipdoc and a6.co_doc=a4.co_doc ) "
                + " where a.co_emp=? and a.co_loc=? and a.co_tipdoc=? and a.co_doc=? and a6.st_reg not in ('I', 'E') ";
        
        strSql1=" update tbm_detmovinv  set st_cliretemprel=null, tx_perretemprel=null, tx_vehretemprel=null, tx_plavehretemprel=null, tx_obscliretemprel=null, fe_retemprel=null "
                + " where co_emp=? and co_loc=? and co_tipdoc=? and co_doc=? and co_reg=? ";        
        try{
            if (conn != null){
                pstLoc=conn.prepareStatement(strSql);
                pstLoc.setInt(1, intCodEmpDev );
                pstLoc.setInt(2, intCodLocDev );
                pstLoc.setInt(3, intCodTipDocDev );
                pstLoc.setInt(4, intCodDocDev );
                
                System.out.println("ZafVen25_02.actualizaDetOrdDesRel: " + strSql);
                
                rstLoc=pstLoc.executeQuery();
                while(rstLoc.next()){                       
                    strTipCon=rstLoc.getString("st_coninv");                    
                    
                    if (strTipCon.equals("C") || strTipCon.equals("E") ){
                        blnRes=true;
                    }else{
                        if (strTipCon.equals("P") ){
                            pstLoc2=conn.prepareStatement(strSql1);
                            pstLoc2.setInt(1, rstLoc.getInt("co_emp") );
                            pstLoc2.setInt(2, rstLoc.getInt("co_loc") );
                            pstLoc2.setInt(3, rstLoc.getInt("co_tipdoc") );
                            pstLoc2.setInt(4, rstLoc.getInt("co_doc") );
                            pstLoc2.setInt(5, rstLoc.getInt("co_reg") ); 
                            pstLoc2.executeUpdate();                    

                            pstLoc3=conn.prepareStatement(strSql1);
                            pstLoc3.setInt(1, rstLoc.getInt("co_emprel") );
                            pstLoc3.setInt(2, rstLoc.getInt("co_locrel") );
                            pstLoc3.setInt(3, rstLoc.getInt("co_tipdocrel") );
                            pstLoc3.setInt(4, rstLoc.getInt("co_docrel") );
                            pstLoc3.setInt(5, rstLoc.getInt("co_regrel") ); 
                            pstLoc3.executeUpdate();                              
                        } 
                        blnRes=true;
                    }  
                }                                
            }
        }catch(SQLException e){
            blnRes=false;
            e.printStackTrace();
        }catch(Exception e){
            blnRes=false;
            e.printStackTrace();
        }finally{ 
            try{ 
                if (rstLoc != null) 
                    rstLoc.close(); 
                rstLoc=null; 
                
                if (pstLoc2 != null) 
                    pstLoc2.close(); 
                pstLoc2=null;                                 
                
                if (pstLoc3 != null) 
                    pstLoc3.close(); 
                pstLoc3=null;                
                
                if (pstLoc != null) 
                    pstLoc.close(); 
                pstLoc=null;                 
            }catch(Throwable e){ 
                e.printStackTrace(); 
            } 
        }        
        return blnRes;
    }

    private boolean actualizaDetDocRelCliRet(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodReg) {
        boolean blnRes = false;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        String strSql = "", strSqlUpD="";
        String strTipCon = "";
        
        try {
            System.out.println("*********************************************************************************");
            System.out.println("********** ZafVen25_02.actualizaDetDocRelCliRet: ********************************");
            System.out.println("*********************************************************************************");
            if (conn != null) {
                stmLoc = conn.createStatement();
                
                strSql = "select x5.co_emp, x5.co_loc, x5.co_tipdoc, x5.co_doc, x5.co_reg, x5.nd_can, x5.tx_nomitm, x5.co_itm, x5.co_emprel, x5.co_locrel, x5.co_tipdocrel, x5.co_docrel, x5.co_regrel, x6.st_coninv "
                + " from tbm_detsoldevven as x "
                + " inner join tbr_detmovinv as x1 on (x1.co_emp=x.co_emp and x1.co_loc=x.co_locrel and x1.co_tipdoc=x.co_tipdocrel and x1.co_doc=x.co_docrel and x1.co_reg=x.co_regrel ) "
                + " inner join tbr_detmovinv as x2 on (x2.co_emprel=x1.co_emprel and x2.co_locrel=x1.co_locrel and x2.co_tipdocrel=x1.co_tipdocrel and x2.co_docrel=x1.co_docrel and x2.co_regrel=x1.co_regrel and ( x2.co_emp!=x1.co_emp or x2.co_loc!=x1.co_loc or x2.co_tipdoc!=x1.co_tipdoc or x2.co_doc!=x1.co_doc ) ) "
                + " inner join tbr_detmovinv as x3 on (x3.co_emp=x2.co_emp and x3.co_loc=x2.co_loc and x3.co_tipdoc=x2.co_tipdoc and x3.co_doc=x2.co_doc and x3.co_reg=x2.co_reg ) "
                + " inner join tbm_detmovinv as x4 on (x4.co_emp=x3.co_emp and x4.co_loc=x3.co_loc and x4.co_tipdoc=x3.co_tipdoc and x4.co_doc=x3.co_doc and x4.co_reg=x3.co_reg ) "
                + " inner join tbm_detguirem as x5 on (x5.co_emprel=x5.co_emp and x5.co_locrel=x4.co_loc and x5.co_tipdocrel=x4.co_tipdoc and x5.co_docrel=x4.co_doc and x5.co_regrel=x4.co_reg ) "
                + " inner join tbm_cabguirem as x6 on (x6.co_emp=x5.co_emp and x6.co_loc=x5.co_loc and x6.co_tipdoc=x5.co_tipdoc and x6.co_doc=x5.co_doc ) "
                + " where x.co_emp = "+intCodEmp+" and x.co_loc = "+intCodLoc+" and x.co_tipdoc = "+intCodTipDoc+" and x.co_doc = "+intCodDoc+" and x.co_reg = "+intCodReg;

                System.out.println("ZafVen25_02.actualizaDetDocRelCliRet: "+ strSql );

                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    strTipCon = rstLoc.getString("st_coninv");  
                    
                    if (strTipCon.equals("C")) {
                        //blnRes = true;
                    } else {
                        strSqlUpD+="; UPDATE tbm_detmovinv set st_cliretemprel = null "
                        + " WHERE co_emp = "+rstLoc.getInt("co_emprel")+" and co_loc = "+rstLoc.getInt("co_locrel")+" and co_tipdoc = "+rstLoc.getInt("co_tipdocrel")+" "
                        + " and co_doc = "+rstLoc.getInt("co_docrel")+" and co_reg = "+rstLoc.getInt("co_regrel")+"";
                        System.out.println("ZafVen25_02.actualizaDetDocRelCliRet: José Marín M. 25/Nov/2014 CLIENTE RETIRA  "+ strSqlUpD );
                        //blnRes = true;            
                    }                    
                }
                
                stmLoc.executeUpdate(strSqlUpD);
                stmLoc.close();
                stmLoc = null;
                
                rstLoc.close();
                rstLoc = null;

                blnRes = true;    
        
        }}catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt); }
         catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(jIntfra, Evt); }
        System.out.println("ZafVen25_02.actualizaDetDocRelCliRet: " + blnRes );
         return blnRes;
    }
    
    
    private int obtenerTipDocCnfAutDev(java.sql.Connection conn, int intCodEmpCnfEgr){
         int intTipDoc=0;
         java.sql.Statement stmTipDoc;
         java.sql.ResultSet rstTipDoc;
         String strSql="";
         int intCoCfg=0;
          try{
                if(conn != null ){
                    stmTipDoc=conn.createStatement();

                    if(intCodEmpCnfEgr==1){
                        intCoCfg=20;
                    }else if(intCodEmpCnfEgr==2){
                        intCoCfg=20;
                    }else if(intCodEmpCnfEgr==4){
                        intCoCfg=20;
                    }
                    /*SE TRAE EL TIPO DE DOCUMENTO DE GUIA DE REMISION PARA LA EMPRESA DONDE SE GENERA LA CONFIRMACION DE EGRESO*/
                    strSql= "select co_tipdoc from tbm_cfgTipDocUtiProAut where co_emp="+intCodEmpCnfEgr+" and co_cfg="+intCoCfg;
                    /*SE TRAE EL TIPO DE DOCUMENTO DE GUIA DE REMISION PARA LA EMPRESA DONDE SE GENERA LA CONFIRMACION DE EGRESO*/

                    rstTipDoc=stmTipDoc.executeQuery(strSql);
                    if(rstTipDoc.next())
                      intTipDoc = rstTipDoc.getInt("co_tipdoc");
                    rstTipDoc.close();
                    rstTipDoc=null;
                    stmTipDoc.close();
                    stmTipDoc=null;

                }
         }catch(java.sql.SQLException Evt){  
             Evt.printStackTrace();
          }catch(Exception Evt){  
              Evt.printStackTrace();
          }
          return  intTipDoc;
    }    
    

    
    
}


