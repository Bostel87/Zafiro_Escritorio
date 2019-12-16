/*
 * ZafAut.java  System.out
 *
 * Created on 9 de noviembre de 2005, 17:58
 */
  
package Librerias.ZafAut;

import Librerias.ZafDate.ZafDatePicker;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.JFrame;

/**
 * 1.0
 *    
 * @author  IDITRIXXX
 */
public class ZafAut
{
    private javax.swing.JInternalFrame jfrThis; 
    private Librerias.ZafParSis.ZafParSis objZafParSis;
    private Librerias.ZafUtil.ZafUtil objUtil;
    private ZafWinAut objWinAut;
    private int intCodAut;
    private String strVersion="v0.11";
    
    /** Creates a new instance of ZafAut */
    public ZafAut(javax.swing.JInternalFrame jfrThis, Librerias.ZafParSis.ZafParSis objZafParSis) {
        this.objZafParSis = objZafParSis;
        this.jfrThis      = jfrThis;
        objUtil = new Librerias.ZafUtil.ZafUtil ();
        objDatPickFecDoc = new ZafDatePicker(((JFrame) jfrThis.getParent()), "d/m/y");
    }
    public ZafAut(){
        System.out.println("Retorno de invocar clase: " + invocarFuncion("getPrueba"));
    }
    
//    /**
//     * Retorna un vetor que contiene vectores con el siguiente formato
//     */
    private java.util.Vector getLstCtls(){
       java.sql.Connection conCon;
       try{
           System.out.println("getLstCtls");
           conCon =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
           java.util.Vector vecLstCtls = new java.util.Vector();
           if (conCon!=null){
//              /**
//               * Extrayendo los nombres de programas que deben ejecutarse
//               * para este programa.
//               */
              java.sql.Statement stmAutDoc = conCon.createStatement(); 

              /* Ejecutando el Consulta */
              java.sql.ResultSet rstAutDoc = stmAutDoc.executeQuery(
                        " SELECT doccab.tx_descor, doccab.tx_nomfun," +
                        "       doccab.tx_obs1, doccab.st_aut  " +
                        " FROM  tbm_regneg " +
                        " where " +
                        " co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                        " co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                        " co_mnu = " + objZafParSis.getCodigoMenu()    + " and st_reg = 'A'"
                );
              while(rstAutDoc.next()){
                java.util.Vector vecDetCtl = new java.util.Vector();
                vecDetCtl.add(rstAutDoc.getString("tx_descor"));
                vecDetCtl.add(rstAutDoc.getString("tx_nomfun"));
                vecDetCtl.add(rstAutDoc.getString("tx_obs1"));
                vecDetCtl.add(rstAutDoc.getString("st_aut"));
                vecLstCtls.add(vecDetCtl);
              }

             conCon.close();
             conCon = null;
           }   
             return vecLstCtls;             
       }              
       catch(java.sql.SQLException Evt)
       {    
            //objUtil.mostrarMsgErr_F1(jfrThis, Evt);
           return null;             
       }
       catch(Exception Evt)
       {    
            //objUtil.mostrarMsgErr_F1(jfrThis, Evt);
           return null;             
       }      
       
    }
    
    
    
    
    /** SOLO PARA LA FACTURA 
     */
    
    public int checkCtls2(){
       java.sql.Connection conCon;
       try{
           System.out.println("checkCtls2");
           conCon =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
           if (conCon!=null){
              /**
               * Extrayendo los nombres de programas que deben ejecutarse
               * para este programa.
               */
              java.sql.Statement stmAutDoc = conCon.createStatement(); 

              /* Ejecutando el Consulta */
              java.sql.ResultSet rstAutDoc = stmAutDoc.executeQuery(
                        " SELECT co_reg, tx_descor, tx_nomfun," +
                        "       tx_obs1, st_aut  " +
                        " FROM  tbm_regneg " +
                        " where " +
                        " co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                        " co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                        " co_mnu = " + objZafParSis.getCodigoMenu()    + " and st_reg ='A' "
                );
              java.util.Vector vecLstCtls = new java.util.Vector();
              boolean blnShowWin = false; // Presenta la ventana donde sale la lista de controle que no paso el programa.
              while(rstAutDoc.next()){
                
                java.util.Vector vecDetCtl = new java.util.Vector();
                String strStAut  = rstAutDoc.getString("st_aut")+"",
                       strNomFun = rstAutDoc.getString("tx_nomfun")+"",
                       strDesCor = rstAutDoc.getString("tx_descor")+"",
                       strObs1   =rstAutDoc.getString("tx_obs1")+"" ,
                       strCoReg  =rstAutDoc.getString("co_reg")+"" ;
                boolean blnCtlOk=true;
                if(strStAut.equalsIgnoreCase("S")){
                     blnCtlOk = invocarFuncion(strNomFun);
                     if(!blnCtlOk)
                        blnShowWin = true;
                }

                //Agregando los datos de esta funcion
                vecDetCtl.add(new Boolean(!blnCtlOk));
                vecDetCtl.add(strDesCor);
                vecDetCtl.add(strObs1);
                vecDetCtl.add(strCoReg);
                
                vecLstCtls.add(vecDetCtl);
              }
             conCon.close();
             conCon = null;
             
             if(blnShowWin){
                // Presentando la ventana de lista motivos por los que no se graba este documentp
              //  objWinAut = new ZafWinAut(javax.swing.JOptionPane.getFrameForComponent(jfrThis), true , vecLstCtls);
              //  objWinAut.show();
              //  int intRes = objWinAut.getResultado();
                return 2; //intRes;
             }
             
           }
           return 1;             
       }              
       catch(java.sql.SQLException Evt)
       {    
           objUtil.mostrarMsgErr_F1(jfrThis, Evt);
           return -1;             
       }
       catch(Exception Evt)
       {    
           objUtil.mostrarMsgErr_F1(jfrThis, Evt);
           return -1;             
       }      
       
    }
    
    
    
    
    
    
    
    
    
     public int checkCtls_2(java.sql.Connection conCon){
        int intvalor=0;
         try{
             System.out.println("checkCtls_2");
            if (conCon!=null){
              /**
               * Extrayendo los nombres de programas que deben ejecutarse
               * para este programa.
               */
              java.sql.Statement stmAutDoc = conCon.createStatement(); 
           
              
                    String sql = " SELECT co_reg, tx_descor, tx_nomfun," +
                        "       tx_obs1, st_aut  " +
                        " FROM  tbm_regneg " +
                        " where " +
                        " co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                        " co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                        " co_mnu = " + objZafParSis.getCodigoMenu()    + " and st_reg ='A' and co_reg<>16 "; /* Jose Marin 11Dic2013*/
                    
              
                
                        java.sql.ResultSet rstAutDoc = stmAutDoc.executeQuery(sql);
              
              java.util.Vector vecLstCtls = new java.util.Vector();
              boolean blnShowWin = false; // Presenta la ventana donde sale la lista de controle que no paso el programa.
              while(rstAutDoc.next()){
                intvalor=1;
                java.util.Vector vecDetCtl = new java.util.Vector();
                String strStAut  = rstAutDoc.getString("st_aut")+"",
                       strNomFun = rstAutDoc.getString("tx_nomfun")+"",
                       strDesCor = rstAutDoc.getString("tx_descor")+"",
                       strObs1   =rstAutDoc.getString("tx_obs1")+"" ,
                       strCoReg  =rstAutDoc.getString("co_reg")+"" ;
                boolean blnCtlOk=true;
                if(strStAut.equalsIgnoreCase("S")){
                     blnCtlOk = invocarFuncion(strNomFun);
                  //   System.out.println("Estado  de Funcion ==> "+ blnCtlOk);
                    // if(!blnCtlOk)
                        blnShowWin = true;
                }

                //Agregando los datos de esta funcion
                vecDetCtl.add("");
                vecDetCtl.add(new Boolean(!blnCtlOk));
                vecDetCtl.add(strDesCor);
                vecDetCtl.add(strObs1);
                vecDetCtl.add(strCoReg);
                
                vecLstCtls.add(vecDetCtl);
              }
             //conCon.close();    
             //conCon = null;
              
             if(blnShowWin){
                // Presentando la ventana de lista motivos por los que no se graba este documentp
                 objWinAut = new ZafWinAut(javax.swing.JOptionPane.getFrameForComponent(jfrThis), true , vecLstCtls);
                 //objWinAut.show();
                  
                 int intRes = 1; // objWinAut.getResultado();
                return intRes;
             }
             
           }
           return intvalor;             
       }              
       catch(java.sql.SQLException Evt) { objUtil.mostrarMsgErr_F1(jfrThis, Evt);  return -1;  }
       catch(Exception Evt) { objUtil.mostrarMsgErr_F1(jfrThis, Evt); return -1;  }      
    }
  
    
    
       
    
    
    
    
    
    
    
    /**
     * Chequea si el programa pasa 
     * todos los controles que tiene 
     * asignado.
     * retorna un entero 
     * -1 ocurrio algun error 
     * 1 Todo Ok .... se puede grabar el documento correctamente
     * 2 Solicita autorizacion
     * 3 Cancela la grabacion.. El usuario no quiere hacer ninguna accion.
     */
    public int checkCtls(java.sql.Connection conCon){
       ///java.sql.Connection conCon;
       try{
           System.out.println("checkCtls java.sql.Connection conCon");
          // conCon =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
           if (conCon!=null){
              /**
               * Extrayendo los nombres de programas que deben ejecutarse
               * para este programa.
               */
              java.sql.Statement stmAutDoc = conCon.createStatement(); 
 
              java.sql.ResultSet rstAutDoc = stmAutDoc.executeQuery(
                        " SELECT co_reg, tx_descor, tx_nomfun," +
                        "       tx_obs1, st_aut  " +
                        " FROM  tbm_regneg " +
                        " where " +
                        " co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                        " co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                        " co_mnu = " + objZafParSis.getCodigoMenu()    + " and st_reg ='A' and co_reg<>16 "
                     + "  ORDER BY tx_descor DESC " /* Jose Marin 11Dic2013*/
                );
              java.util.Vector vecLstCtls = new java.util.Vector();
              boolean blnShowWin = false; // Presenta la ventana donde sale la lista de controle que no paso el programa.
              while(rstAutDoc.next()){
                
                java.util.Vector vecDetCtl = new java.util.Vector();
                String strStAut  = rstAutDoc.getString("st_aut")+"",
                       strNomFun = rstAutDoc.getString("tx_nomfun")+"",
                       strDesCor = rstAutDoc.getString("tx_descor")+"",
                       strObs1   =rstAutDoc.getString("tx_obs1")+"" ,
                       strCoReg  =rstAutDoc.getString("co_reg")+"" ;
                boolean blnCtlOk=true;
                if(strStAut.equalsIgnoreCase("S")){
                     blnCtlOk = invocarFuncion(strNomFun);
                     if(!blnCtlOk)
                        blnShowWin = true;
                }

                //Agregando los datos de esta funcion
                vecDetCtl.add("");
                vecDetCtl.add(new Boolean(!blnCtlOk));
                vecDetCtl.add(strDesCor);
                vecDetCtl.add(strObs1);
                vecDetCtl.add(strCoReg);
                
                vecLstCtls.add(vecDetCtl);
              }
             //conCon.close();  
             //conCon = null;
             
             if(blnShowWin){
                // Presentando la ventana de lista motivos por los que no se graba este documentp
                objWinAut = new ZafWinAut(javax.swing.JOptionPane.getFrameForComponent(jfrThis), true , vecLstCtls);
                objWinAut.show();
                int intRes = objWinAut.getResultado();
                return intRes;
             }
             
           }
           return 1;             
       }              
       catch(java.sql.SQLException Evt) { objUtil.mostrarMsgErr_F1(jfrThis, Evt);  return -1;  }
       catch(Exception Evt) { objUtil.mostrarMsgErr_F1(jfrThis, Evt); return -1;  }      
    }
    
    /**
     * JM
     * Chequea si el programa pasa 
     * todos los controles que tiene 
     * asignado.
     * retorna un entero 
     * -1 ocurrio algun error 
     * 1 Todo Ok .... se puede grabar el documento correctamente
     * 2 Solicita autorizacion
     * 3 Cancela la grabacion.. El usuario no quiere hacer ninguna accion.
     */
    public int checkCtls(java.sql.Connection conCon, int intCodEmp, int intCodLoc,int intCodCot, int intCodMnu){
       ///java.sql.Connection conCon;
       try{
           System.out.println("checkCtls java.sql.Connection conCon");
          // conCon =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
           if (conCon!=null){
              /**
               * Extrayendo los nombres de programas que deben ejecutarse
               * para este programa.
               */
              java.sql.Statement stmAutDoc = conCon.createStatement(); 
              java.sql.ResultSet rstAutDoc = stmAutDoc.executeQuery(
                        " SELECT co_reg, tx_descor, tx_nomfun," +
                        "       tx_obs1, st_aut  " +
                        " FROM  tbm_regneg " +
                        " where " +
                        " co_emp = " + intCodEmp + " and " +
                        " co_loc = " + intCodLoc + " and " +
                        " co_mnu = " + intCodMnu + " and st_reg ='A' and co_reg<>16 ORDER BY tx_descor DESC " /* Jose Marin 11Dic2013*/
                );
              java.util.Vector vecLstCtls = new java.util.Vector();
              boolean blnShowWin = false; // Presenta la ventana donde sale la lista de controle que no paso el programa.
              while(rstAutDoc.next()){
                
                java.util.Vector vecDetCtl = new java.util.Vector();
                String strStAut  = rstAutDoc.getString("st_aut")+"",
                       strNomFun = rstAutDoc.getString("tx_nomfun")+"",
                       strDesCor = rstAutDoc.getString("tx_descor")+"",
                       strObs1   =rstAutDoc.getString("tx_obs1")+"" ,
                       strCoReg  =rstAutDoc.getString("co_reg")+"" ;
                boolean blnCtlOk=true;
                if(strStAut.equalsIgnoreCase("S")){
//                    String funcion, int intCodEmp, int intCodLoc, int intCodCot
                     blnCtlOk = invocarFuncion(conCon, strNomFun, intCodEmp, intCodLoc, intCodCot);
                     if(!blnCtlOk)
                        blnShowWin = true;
                }

                //Agregando los datos de esta funcion
                vecDetCtl.add("");
                vecDetCtl.add(new Boolean(!blnCtlOk));
                vecDetCtl.add(strDesCor);
                vecDetCtl.add(strObs1);
                vecDetCtl.add(strCoReg);
                
                vecLstCtls.add(vecDetCtl);
              }
             //conCon.close();  
             //conCon = null;
             
             if(blnShowWin){
                // Presentando la ventana de lista motivos por los que no se graba este documentp
                objWinAut = new ZafWinAut(javax.swing.JOptionPane.getFrameForComponent(jfrThis), true , vecLstCtls);
                objWinAut.show();
                int intRes = objWinAut.getResultado();
                return intRes;
             }
             
           }
           return 1;             
       }              
       catch(java.sql.SQLException Evt) { 
           objUtil.mostrarMsgErr_F1(jfrThis, Evt);  
           return -1;  
       }
       catch(Exception Evt) { 
           objUtil.mostrarMsgErr_F1(jfrThis, Evt); 
           return -1;  
       }      
    }
    
    /**
     * JM
     * Metodo que verifica si todo esta correcto o el documento 
     * ya a sido autorizado, y se puede facturar.
     * Generalmente debe ser llamado cuando se hace alguna modificacion.
     */
    public int checkCtls(String strTbm_CabAut, String strTbm_DetAut, int intCodTipDoc , int intCodDoc ){
       try{
           System.out.println("checkCtls String strTbm_CabAut, String strTbm_DetAut, int intCodTipDoc , int intCodDoc");
           //System.out.println("Revisando solo los controles que no se habian autorizados anteriormente");
            java.sql.Connection conCon =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conCon!=null){
              /**
               * Extrayendo los nombres de programas que deben ejecutarse
               * para este programa.
               */
              java.sql.Statement stmAutDoc = conCon.createStatement(); 
              java.sql.Statement stmAutReg = conCon.createStatement(); 

               
              java.sql.ResultSet rstAutDoc = stmAutDoc.executeQuery(
                            "select cab.st_reg, cab.co_aut from " + strTbm_CabAut + "  as cab where " +
                            "      cab.co_emp    =  " + objZafParSis.getCodigoEmpresa() + " and " +
                            "      cab.co_loc    =  " + objZafParSis.getCodigoLocal()   + " and " +
                            "      cab.co_tipdoc =  " + intCodTipDoc + " and " +
                            "      cab.co_doc =     " + intCodDoc    + " and " +
                            "      cab.co_aut  = ( " +
                            "            select max(co_aut) from " + strTbm_CabAut + " as max " +
                            "               where " +
                            "                   cab.co_emp =max.co_emp and cab.co_loc = max.co_loc and "  +
                            "                   cab.co_doc = max.co_doc and  " +
                            "                   cab.co_tipdoc = max.co_tipdoc) " 
              );
                
              if(rstAutDoc.next()){
                String strStAut  = rstAutDoc.getString("st_reg")+"";
                if(strStAut.equalsIgnoreCase("A")){
                    /*
                     *  Significa que ha sido autorizado y solo hay que verificar los
                     *  controles que no fueron autorizados en ese caso ver cuales son los autorizados
                     *  y los que no comprobarlos
                     */

                    /**
                     * Contiene todos los controles que 
                     * fueron autorizadfos la ultima vez
                     */
                    java.util.Vector vecIsAutorizado = new java.util.Vector();

                    /*
                     * Utilizar el vector para usar el metodo contains 
                     * para saber cuales son los metodos que existen 
                     * y no ejecutar el metodo invocarFuncion con ese control 
                     º  * que ya fue autorizado.
                     */
                     
                     
                     java.sql.ResultSet rstCtlsAutorizados = stmAutDoc.executeQuery(
                            "	select det.co_doc, det.co_reg, reg.tx_nomfun, det.st_cum  from " + strTbm_DetAut +" as det " +
                            "	inner join tbm_regneg as reg " +
                            "	On (reg.co_loc = det.co_loc and reg.co_emp = det.co_emp and det.co_regneg = reg.co_reg )" +
                            "	where " +
                            "           det.co_emp   = " + objZafParSis.getCodigoEmpresa() + " and " +
                            "		det.co_loc   = " + objZafParSis.getCodigoLocal()   + " and " +
                            "		det.co_tipdoc = " + intCodTipDoc + " and " +
                            "		det.co_doc  = " + intCodDoc      + " and " +
                            "           det.co_aut  = (   " +
                            "                     select max(co_aut) from  " + strTbm_DetAut + "   as max  " +
                            "                          where  "+
                            "                          det.co_emp =max.co_emp and det.co_loc = max.co_loc and   " +
                            "                          det.co_doc = max.co_doc and   " +
                            "                          det.co_tipdoc = max.co_tipdoc)"
                      );
                      while(rstCtlsAutorizados.next()){
                        String strStCum  = rstCtlsAutorizados.getString("st_cum")+"" ,
                               strNomFun = rstCtlsAutorizados.getString("tx_nomfun")+"" ;
                        if(strStCum.equalsIgnoreCase("N")){
                           // System.out.println("Funcion que no cumple pero que ya habia sido autorizada: " + strNomFun);
                            vecIsAutorizado.add(strNomFun);
                        }
                      }
                     
                     
                      java.sql.ResultSet rstReg = stmAutReg.executeQuery(
                                " SELECT co_reg, tx_descor, tx_nomfun," +
                                "       tx_obs1, st_aut  " +
                                " FROM  tbm_regneg " +
                                " where " +
                                " co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                                " co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                                " co_mnu = " + objZafParSis.getCodigoMenu()    + " and st_reg = 'A' and co_reg<>16 ORDER BY tx_descor DESC " /* Jose Marin 11Dic2013*/ 
                        );
                      
                      java.util.Vector vecLstCtls = new java.util.Vector(); 
                      boolean blnShowWin = false; // Presenta la ventana donde sale la lista de controle que no paso el programa.
                      while(rstReg.next()){
                      //  System.out.println("Buscando las reglas del negocion");
                        java.util.Vector vecDetCtl = new java.util.Vector();
                        String strStAutDoc= rstReg.getString("st_aut")+"",
                               strNomFun  = rstReg.getString("tx_nomfun")+"",
                               strDesCor  = rstReg.getString("tx_descor")+"",
                               strObs1    = rstReg.getString("tx_obs1")+"" ,
                               strCoReg   = rstReg.getString("co_reg")+"" ;
                        boolean blnCtlOk=false;
                        
                        if(strStAutDoc.equalsIgnoreCase("S")){
                            // System.out.println("Comparando la funcion actual a ver si existe : " + strNomFun);
                             if(!vecIsAutorizado.contains(strNomFun)){
                              //   System.out.println("Verificando Las que no fueron autorizadas anteriormente");
                                 blnCtlOk = invocarFuncion(strNomFun);
                                 if(!blnCtlOk)
                                    blnShowWin = true;
                             }
                             
                        }

                        //Agregando los datos de esta funcion
                        vecDetCtl.add("");
                        vecDetCtl.add(new Boolean(!blnCtlOk));
                        vecDetCtl.add(strDesCor);
                        vecDetCtl.add(strObs1);
                        vecDetCtl.add(strCoReg);

                        vecLstCtls.add(vecDetCtl);
                      }
                     conCon.close();
                     conCon = null;

                     if(blnShowWin){
                        // Presentando la ventana de lista motivos por los que no se graba este documentp
                        objWinAut = new ZafWinAut(javax.swing.JOptionPane.getFrameForComponent(jfrThis), true , vecLstCtls);
                        objWinAut.show();
                        int intRes = objWinAut.getResultado();
                        return intRes;
                     }
                    
                }else{
                    return checkCtls(conCon);
                }
                
              }
            }
           
            
            
           return 1;       
        }
       catch(java.sql.SQLException Evt)
       {    
           objUtil.mostrarMsgErr_F1(jfrThis, Evt);
           return -1;             
       }
       catch(Exception Evt)
       {    
           objUtil.mostrarMsgErr_F1(jfrThis, Evt);
           return -1;             
       }      
        
    }
    

    
    /* FUNCION PARA SOLO FACTURACION AQUI NO PRESENTA LA VENTANA DE AUTORZACION
    */
      public int checkCtlsCot2(String strTbm_CabAut, String strTbm_DetAut, int intCodCot ){
       try{
           System.out.println("checkCtlsCot2 ");
            java.sql.Connection conCon =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conCon!=null){
              /**
               * Extrayendo los nombres de programas que deben ejecutarse
               * para este programa.
               */
              java.sql.Statement stmAutDoc = conCon.createStatement(); 
              java.sql.Statement stmAutReg = conCon.createStatement(); 

              /* Ejecutando el Consulta */
              java.sql.ResultSet rstAutDoc = stmAutDoc.executeQuery(
                            "select cab.st_reg, cab.co_aut from " + strTbm_CabAut + "  as cab where " +
                            "      cab.co_emp    =  " + objZafParSis.getCodigoEmpresa() + " and " +
                            "      cab.co_loc    =  " + objZafParSis.getCodigoLocal()   + " and " +
                            "      cab.co_cot =     " + intCodCot    + " and " +
                            "      cab.co_aut  = ( " +
                            "            select max(co_aut) from " + strTbm_CabAut + " as max " +
                            "               where " +
                            "                   cab.co_emp =max.co_emp and cab.co_loc = max.co_loc and "  +
                            "                   cab.co_cot = max.co_cot  ) " 
              );
              
              
              if(rstAutDoc.next()){
                String strStAut  = rstAutDoc.getString("st_reg")+"";
                if(strStAut.equalsIgnoreCase("A")){
                    /*
                     *  Significa que ha sido autorizado y solo hay que verificar los
                     *  controles que no fueron autorizados en ese caso ver cuales son los autorizados
                     *  y los que no comprobarlos
                     */

                    /**
                     * Contiene todos los controles que 
                     * fueron autorizadfos la ultima vez
                     */
                    java.util.Vector vecIsAutorizado = new java.util.Vector();

                    /*
                     * Utilizar el vector para usar el metodo contains 
                     * para saber cuales son los metodos que existen 
                     * y no ejecutar el metodo invocarFuncion con ese control 
                     º  * que ya fue autorizado.
                     */
                     
                    /* Ejecutando el Consulta */
                     java.sql.ResultSet rstCtlsAutorizados = stmAutDoc.executeQuery(
                            "	select det.co_cot, det.co_reg, reg.tx_nomfun, det.st_cum  from " + strTbm_DetAut +" as det " +
                            "	inner join tbm_regneg as reg " +
                            "	On (reg.co_loc = det.co_loc and reg.co_emp = det.co_emp and det.co_regneg = reg.co_reg )" +
                            "	where " +
                            "           det.co_emp   = " + objZafParSis.getCodigoEmpresa() + " and " +
                            "		det.co_loc   = " + objZafParSis.getCodigoLocal()   + " and " +
                            "		det.co_cot  = " + intCodCot      + " and " +
                            "           det.co_aut  = (   " +
                            "                     select max(co_aut) from  " + strTbm_DetAut + "   as max  " +
                            "                          where  "+
                            "                          det.co_emp =max.co_emp and det.co_loc = max.co_loc and   " +
                            "                          det.co_cot = max.co_cot )"
                      );
                      while(rstCtlsAutorizados.next()){

                        String strStCum  = rstCtlsAutorizados.getString("st_cum")+"" ,
                               strNomFun = rstCtlsAutorizados.getString("tx_nomfun")+"" ;
                        if(strStCum.equalsIgnoreCase("N")){
                        //    System.out.println("Funcion que no cumple pero que ya habia sido autorizada: " + strNomFun);
                            vecIsAutorizado.add(strNomFun);
                        }
                      }
                     
                      /* Ejecutando el Consulta */
                     
                      java.sql.ResultSet rstReg = stmAutReg.executeQuery(
                                " SELECT co_reg, tx_descor, tx_nomfun," +
                                "       tx_obs1, st_aut  " +
                                " FROM  tbm_regneg " +
                                " where " +
                                " co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                                " co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                                " co_mnu = " + objZafParSis.getCodigoMenu()    + " and st_reg = 'A' and co_reg<>16 ORDER BY tx_descor DESC "/* Jose Marin 11Dic2013*/
                        );
                      java.util.Vector vecLstCtls = new java.util.Vector();  
                      boolean blnShowWin = false; // Presenta la ventana donde sale la lista de controle que no paso el programa.
                      while(rstReg.next()){
                      //  System.out.println("Buscando las reglas del negocion");
                        java.util.Vector vecDetCtl = new java.util.Vector();
                        String strStAutDoc= rstReg.getString("st_aut")+"",
                               strNomFun  = rstReg.getString("tx_nomfun")+"",
                               strDesCor  = rstReg.getString("tx_descor")+"",
                               strObs1    = rstReg.getString("tx_obs1")+"" ,
                               strCoReg   = rstReg.getString("co_reg")+"" ;
                        boolean blnCtlOk=false;
                        
                        if(strStAutDoc.equalsIgnoreCase("S")){
                          //   System.out.println("Comparando la funcion actual a ver si existe : " + strNomFun);
                             if(!vecIsAutorizado.contains(strNomFun)){
                            //     System.out.println("Verificando Las que no fueron autorizadas anteriormente");
                                 blnCtlOk = invocarFuncion(strNomFun);
                                 if(!blnCtlOk)
                                    blnShowWin = true;
                             }
                             
                        }

                        //Agregando los datos de esta funcion
                        vecDetCtl.add("");
                        vecDetCtl.add(new Boolean(!blnCtlOk));
                        vecDetCtl.add(strDesCor);
                        vecDetCtl.add(strObs1);
                        vecDetCtl.add(strCoReg);

                        vecLstCtls.add(vecDetCtl);
                      }
                     conCon.close();
                     conCon = null;

                     if(blnShowWin){
                        // Presentando la ventana de lista motivos por los que no se graba este documentp
                       // objWinAut = new ZafWinAut(javax.swing.JOptionPane.getFrameForComponent(jfrThis), true , vecLstCtls);
                       // objWinAut.show();
                       // int intRes = objWinAut.getResultado();
                        return 2; //intRes;
                     }
                    
                }else{
                    return checkCtls2();
                }
                
              }else{
                    return checkCtls2();
                }
            }
           
            
            
           return 1;       
        }
       catch(java.sql.SQLException Evt)
       {    
           objUtil.mostrarMsgErr_F1(jfrThis, Evt);
           return -1;             
       }
       catch(Exception Evt)
       {    
           objUtil.mostrarMsgErr_F1(jfrThis, Evt);
           return -1;             
       }      
        
    }
      
      
      
    
    
    
    
    
    
    
    
    /**
     * Metodo que verifica si todo esta correcto o la cotizacion 
     * ya a sido autorizado, y se puede facturar.
     * Generalmente debe ser llamado cuando se hace alguna modificacion.
     */
    public int checkCtlsCot(String strTbm_CabAut, String strTbm_DetAut, int intCodCot,java.sql.Connection conCon ){
       try{
           System.out.println("checkCtlsCot ");
           // System.out.println("Revisando solo los controles que no se habian autorizados anteriormente..................");
            //java.sql.Connection conCon =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conCon!=null){
              /**
               * Extrayendo los nombres de programas que deben ejecutarse
               * para este programa.
               */
              java.sql.Statement stmAutDoc = conCon.createStatement(); 
              java.sql.Statement stmAutReg = conCon.createStatement(); 

              /* Ejecutando el Consulta */
//              java.sql.ResultSet rstAutDoc = stmAutDoc.executeQuery(
//                            "select cab.st_reg, cab.co_aut from " + strTbm_CabAut + "  as cab where " +
//                            "      cab.co_emp    =  " + objZafParSis.getCodigoEmpresa() + " and " +
//                            "      cab.co_loc    =  " + objZafParSis.getCodigoLocal()   + " and " +
//                            "      cab.co_cot =     " + intCodCot    + " and " +
//                            "      cab.co_aut  = ( " +
//                            "            select max(co_aut) from " + strTbm_CabAut + " as max " +
//                            "               where " +
//                            "                   cab.co_emp =max.co_emp and cab.co_loc = max.co_loc and "  +
//                            "                   cab.co_cot = max.co_cot  ) " 
//              );
              
                
              java.sql.ResultSet rstAutDoc = stmAutDoc.executeQuery(
                     "select count(*) from tbm_detautcotven   where " +
                     "  co_emp    =  " + objZafParSis.getCodigoEmpresa() + " and " +
                     "  co_loc    =  " + objZafParSis.getCodigoLocal()   + " and " +
                     "  co_cot =     " + intCodCot    + " and st_reg<>'A' and st_cum='N'");
               
              if(rstAutDoc.next()){
               // String strStAut  = rstAutDoc.getString("st_reg")+"";
                int strStAut  = rstAutDoc.getInt(1); 
              
                if(strStAut==0){
                    /*
                     *  Significa que ha sido autorizado y solo hay que verificar los
                     *  controles que no fueron autorizados en ese caso ver cuales son los autorizados
                     *  y los que no comprobarlos
                     */

                    /**
                     * Contiene todos los controles que 
                     * fueron autorizadfos la ultima vez
                     */
                    java.util.Vector vecIsAutorizado = new java.util.Vector();

                    /*
                     * Utilizar el vector para usar el metodo contains 
                     * para saber cuales son los metodos que existen 
                     * y no ejecutar el metodo invocarFuncion con ese control 
                     º  * que ya fue autorizado.
                     */
                     
                     
                     java.sql.ResultSet rstCtlsAutorizados = stmAutDoc.executeQuery(
                            "	select det.co_cot, det.co_reg, reg.tx_nomfun, det.st_cum  from " + strTbm_DetAut +" as det " +
                            "	inner join tbm_regneg as reg " +
                            "	On (reg.co_loc = det.co_loc and reg.co_emp = det.co_emp and det.co_regneg = reg.co_reg )" +
                            "	where " +
                            "           det.co_emp   = " + objZafParSis.getCodigoEmpresa() + " and " +
                            "		det.co_loc   = " + objZafParSis.getCodigoLocal()   + " and " +
                            "		det.co_cot  = " + intCodCot      + " and " +
                            "           det.co_aut  = (   " +
                            "                     select max(co_aut) from  " + strTbm_DetAut + "   as max  " +
                            "                          where  "+
                            "                          det.co_emp =max.co_emp and det.co_loc = max.co_loc and   " +
                            "                          det.co_cot = max.co_cot )"
                      );
                      while(rstCtlsAutorizados.next()){
                            String strStCum  = rstCtlsAutorizados.getString("st_cum")+"" ,
                                   strNomFun = rstCtlsAutorizados.getString("tx_nomfun")+"" ;
                            if(strStCum.equalsIgnoreCase("N")){
                             //   System.out.println("Funcion que no cumple pero que ya habia sido autorizada: " + strNomFun);
                                vecIsAutorizado.add(strNomFun);
                            }
                      }
                     
                      
                      java.sql.ResultSet rstReg = stmAutReg.executeQuery(
                                " SELECT co_reg, tx_descor, tx_nomfun," +
                                "       tx_obs1, st_aut  " +
                                " FROM  tbm_regneg " +
                                " where " +
                                " co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                                " co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                                " co_mnu = " + objZafParSis.getCodigoMenu()    + " and st_reg = 'A' and co_reg<>16  ORDER BY tx_descor DESC "
                        );
                      java.util.Vector vecLstCtls = new java.util.Vector(); 
                      boolean blnShowWin = false; // Presenta la ventana donde sale la lista de controle que no paso el programa.
                      while(rstReg.next()){
                      //  System.out.println("Buscando las reglas del negocion");
                        java.util.Vector vecDetCtl = new java.util.Vector();
                        String strStAutDoc= rstReg.getString("st_aut")+"",
                               strNomFun  = rstReg.getString("tx_nomfun")+"",
                               strDesCor  = rstReg.getString("tx_descor")+"",
                               strObs1    = rstReg.getString("tx_obs1")+"" ,
                               strCoReg   = rstReg.getString("co_reg")+"" ;
                        boolean blnCtlOk=false;
                        
                        if(strStAutDoc.equalsIgnoreCase("S")){
                           //  System.out.println("Comparando la funcion actual a ver si existe : " + strNomFun);
                             if(!vecIsAutorizado.contains(strNomFun)){
                            //     System.out.println("Verificando Las que no fueron autorizadas anteriormente");
                                 blnCtlOk = invocarFuncion(strNomFun);
                                 if(!blnCtlOk)
                                    blnShowWin = true;
                             }
                             
                        }

                        //Agregando los datos de esta funcion
                        vecDetCtl.add("");
                        vecDetCtl.add(new Boolean(!blnCtlOk));
                        vecDetCtl.add(strDesCor);
                        vecDetCtl.add(strObs1);
                        vecDetCtl.add(strCoReg);

                        vecLstCtls.add(vecDetCtl);
                      }
                     //conCon.close();
                     //conCon = null;

                     if(blnShowWin){
                        // Presentando la ventana de lista motivos por los que no se graba este documentp
                        objWinAut = new ZafWinAut(javax.swing.JOptionPane.getFrameForComponent(jfrThis), true , vecLstCtls);
                        objWinAut.show();
                        int intRes = objWinAut.getResultado();
//                        objWinAut.dispose();
//                        objWinAut=null;
                        return intRes;
                        
                     }
                    
                }else{
                    return checkCtls(conCon);
                }
                
              }else{
                    return checkCtls(conCon);
                }
            }
           
            
            
           return 1;       
        }
       catch(java.sql.SQLException Evt)
       {    
           objUtil.mostrarMsgErr_F1(jfrThis, Evt);
           return -1;             
       }
       catch(Exception Evt)
       {    
           objUtil.mostrarMsgErr_F1(jfrThis, Evt);
           return -1;             
       }      
        
    }
    String strSQL;
    int intCodMnuCotVen=3965;
    /**
     * Metodo que verifica si todo esta correcto o la cotizacion 
     * ya a sido autorizado, y se puede facturar.
     * Generalmente debe ser llamado cuando se hace alguna modificacion.
     */
    public int checkCtlsCot(String strTbm_CabAut, String strTbm_DetAut, int intCodEmp, int intCodLoc, int intCodCot,java.sql.Connection conCon ){
       try{
            System.out.println("checkCtlsCot Javier!!! ");
            //java.sql.Connection conCon =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conCon!=null){
              /**
               * Extrayendo los nombres de programas que deben ejecutarse
               * para este programa.
               */
              java.sql.Statement stmAutDoc = conCon.createStatement(); 
              java.sql.Statement stmAutReg = conCon.createStatement(); 

              /* Ejecutando el Consulta */
              strSQL="";
              strSQL+=" select count(*) from tbm_detautcotven   where " ;
              strSQL+="  co_emp    =  " + intCodEmp + " and ";
              strSQL+="  co_loc    =  " + intCodLoc   + " and ";
              strSQL+="  co_cot =     " + intCodCot    + " and st_reg<>'A' and st_cum='N'";
               
              java.sql.ResultSet rstAutDoc = stmAutDoc.executeQuery(strSQL);
              System.out.println(".... " + strSQL);
              if(rstAutDoc.next()){
               // String strStAut  = rstAutDoc.getString("st_reg")+"";
                int strStAut  = rstAutDoc.getInt(1); 
              
                if(strStAut==0){
                    /*
                     *  Significa que ha sido autorizado y solo hay que verificar los
                     *  controles que no fueron autorizados en ese caso ver cuales son los autorizados
                     *  y los que no comprobarlos
                     */
                    
                    /**
                     * Contiene todos los controles que 
                     * fueron autorizadfos la ultima vez
                     */
                    java.util.Vector vecIsAutorizado = new java.util.Vector();

                    /*
                     * Utilizar el vector para usar el metodo contains 
                     * para saber cuales son los metodos que existen 
                     * y no ejecutar el metodo invocarFuncion con ese control 
                     º  * que ya fue autorizado.
                     */
                     
                    /* Ejecutando el Consulta */
                    strSQL=(
                            "	select det.co_cot, det.co_reg, reg.tx_nomfun, det.st_cum  from " + strTbm_DetAut +" as det " +
                            "	inner join tbm_regneg as reg " +
                            "	On (reg.co_loc = det.co_loc and reg.co_emp = det.co_emp and det.co_regneg = reg.co_reg )" +
                            "	where " +
                            "           det.co_emp   = " + intCodEmp + " and " +
                            "		det.co_loc   = " + intCodLoc + " and " +
                            "		det.co_cot  = " + intCodCot + " and " +
                            "           det.co_aut  = (   " +
                            "                     select max(co_aut) from  " + strTbm_DetAut + "   as max  " +
                            "                          where  "+
                            "                          det.co_emp =max.co_emp and det.co_loc = max.co_loc and   " +
                            "                          det.co_cot = max.co_cot ) AND reg.co_reg <> 16 "
                      );
                    System.out.println("ReglasDelNegocio2 " + strSQL);
                     java.sql.ResultSet rstCtlsAutorizados = stmAutDoc.executeQuery(strSQL);
                      while(rstCtlsAutorizados.next()){

                        String strStCum  = rstCtlsAutorizados.getString("st_cum")+"" ,
                               strNomFun = rstCtlsAutorizados.getString("tx_nomfun")+"" ;
                        if(strStCum.equalsIgnoreCase("N")){
                         //   System.out.println("Funcion que no cumple pero que ya habia sido autorizada: " + strNomFun);
                            vecIsAutorizado.add(strNomFun);
                        }
                      }
                     
                      /* Ejecutando el Consulta */
                     
                      strSQL=(
                                " SELECT co_reg, tx_descor, tx_nomfun," +
                                "       tx_obs1, st_aut  " +
                                " FROM  tbm_regneg " +
                                " where " +
                                " co_emp = " + intCodEmp+ " and " +
                                " co_loc = " + intCodLoc   + " and " +
                                " co_mnu = "+intCodMnuCotVen+" and st_reg = 'A' AND co_reg<>16 "   // 3965   "Cotizaciones de venta (Facturación electrónica)..."
                        );
                      System.out.println("ReglasDelNegocio:" + strSQL);
                      java.sql.ResultSet rstReg = stmAutReg.executeQuery(strSQL);
                      java.util.Vector vecLstCtls = new java.util.Vector(); 
                      boolean blnShowWin = false; // Presenta la ventana donde sale la lista de controle que no paso el programa.
                      while(rstReg.next()){
                      //  System.out.println("Buscando las reglas del negocion");
                        java.util.Vector vecDetCtl = new java.util.Vector();
                        String strStAutDoc= rstReg.getString("st_aut")+"",
                               strNomFun  = rstReg.getString("tx_nomfun")+"",
                               strDesCor  = rstReg.getString("tx_descor")+"",
                               strObs1    = rstReg.getString("tx_obs1")+"" ,
                               strCoReg   = rstReg.getString("co_reg")+"" ;
                        boolean blnCtlOk=false;
                        
                        if(strStAutDoc.equalsIgnoreCase("S")){
                           //  System.out.println("Comparando la funcion actual a ver si existe : " + strNomFun);
                             if(!vecIsAutorizado.contains(strNomFun)){
                            //     System.out.println("Verificando Las que no fueron autorizadas anteriormente");
                                 blnCtlOk = invocarFuncion(strNomFun);
                                 if(!blnCtlOk)
                                    blnShowWin = true;
                             }
                             
                        }

                        //Agregando los datos de esta funcion
                        vecDetCtl.add("");
                        vecDetCtl.add(new Boolean(!blnCtlOk));
                        vecDetCtl.add(strDesCor);
                        vecDetCtl.add(strObs1);
                        vecDetCtl.add(strCoReg);

                        vecLstCtls.add(vecDetCtl);
                      }

                     if(blnShowWin){
                        // Presentando la ventana de lista motivos por los que no se graba este documentp
                        objWinAut = new ZafWinAut(javax.swing.JOptionPane.getFrameForComponent(jfrThis), true , vecLstCtls);
                        objWinAut.show();
                        int intRes = objWinAut.getResultado();
                        return intRes;
                        
                     }
                    
                }else{
                    return checkCtls(conCon,intCodEmp,intCodLoc,intCodCot,intCodMnuCotVen);
                }
                
              }else{
                    return checkCtls(conCon,intCodEmp,intCodLoc,intCodCot,intCodMnuCotVen);
                }
            }
           
            
            
           return 1;       
        }
       catch(java.sql.SQLException Evt)
       {    
           objUtil.mostrarMsgErr_F1(jfrThis, Evt);
           return -1;             
       }
       catch(Exception Evt)
       {    
           objUtil.mostrarMsgErr_F1(jfrThis, Evt);
           return -1;             
       }      
        
    }
    
    /**
     * MODIFICADO PARA RESERVAS JM 
     * Metodo que verifica si todo esta correcto o la cotizacion 
     * ya a sido autorizado, y se puede facturar.
     * Generalmente debe ser llamado cuando se hace alguna modificacion.
     */
    public int checkCtlsCot(String strTbm_CabAut, String strTbm_DetAut, int intCodEmp, int intCodLoc, int intCodCot,java.sql.Connection conCon, boolean Reservas ){
       try{
            System.out.println("checkCtlsCot JoTA!!! ");
            //java.sql.Connection conCon =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conCon!=null){
              /**
               * Extrayendo los nombres de programas que deben ejecutarse
               * para este programa.
               */
              java.sql.Statement stmAutDoc = conCon.createStatement(); 
              java.sql.Statement stmAutReg = conCon.createStatement(); 

              /* Ejecutando el Consulta */
              strSQL="";
              strSQL+=" select count(*) from tbm_detautcotven   where " ;
              strSQL+="  co_emp    =  " + intCodEmp + " and ";
              strSQL+="  co_loc    =  " + intCodLoc   + " and ";
              strSQL+="  co_cot =     " + intCodCot    + " and st_reg<>'A' and st_cum='N'";
               
              java.sql.ResultSet rstAutDoc = stmAutDoc.executeQuery(strSQL);
              System.out.println(".... " + strSQL);
              if(rstAutDoc.next()){
               // String strStAut  = rstAutDoc.getString("st_reg")+"";
                int strStAut  = rstAutDoc.getInt(1); 
              
                if(strStAut==0){
                    /*
                     *  Significa que ha sido autorizado y solo hay que verificar los
                     *  controles que no fueron autorizados en ese caso ver cuales son los autorizados
                     *  y los que no comprobarlos
                     */
                    
                    /**
                     * Contiene todos los controles que 
                     * fueron autorizadfos la ultima vez
                     */
                    java.util.Vector vecIsAutorizado = new java.util.Vector();

                    /*
                     * Utilizar el vector para usar el metodo contains 
                     * para saber cuales son los metodos que existen 
                     * y no ejecutar el metodo invocarFuncion con ese control 
                     º  * que ya fue autorizado.
                     */
                     
                    /* Ejecutando el Consulta */
                    strSQL=(
                            "	select det.co_cot, det.co_reg, reg.tx_nomfun, det.st_cum  from " + strTbm_DetAut +" as det " +
                            "	inner join tbm_regneg as reg " +
                            "	On (reg.co_loc = det.co_loc and reg.co_emp = det.co_emp and det.co_regneg = reg.co_reg )" +
                            "	where " +
                            "           det.co_emp   = " + intCodEmp + " and " +
                            "		det.co_loc   = " + intCodLoc + " and " +
                            "		det.co_cot  = " + intCodCot + " and " +
                            "           det.co_aut  = (   " +
                            "                     select max(co_aut) from  " + strTbm_DetAut + "   as max  " +
                            "                          where  "+
                            "                          det.co_emp =max.co_emp and det.co_loc = max.co_loc and   " +
                            "                          det.co_cot = max.co_cot ) AND reg.co_reg <> 16 "
                      );
                    System.out.println("ReglasDelNegocio2 " + strSQL);
                     java.sql.ResultSet rstCtlsAutorizados = stmAutDoc.executeQuery(strSQL);
                      while(rstCtlsAutorizados.next()){

                        String strStCum  = rstCtlsAutorizados.getString("st_cum")+"" ,
                               strNomFun = rstCtlsAutorizados.getString("tx_nomfun")+"" ;
                        if(strStCum.equalsIgnoreCase("N")){
                         //   System.out.println("Funcion que no cumple pero que ya habia sido autorizada: " + strNomFun);
                            vecIsAutorizado.add(strNomFun);
                        }
                      }
                     
                      /* Ejecutando el Consulta */
                     
                      strSQL=(
                                " SELECT co_reg, tx_descor, tx_nomfun," +
                                "       tx_obs1, st_aut  " +
                                " FROM  tbm_regneg " +
                                " where " +
                                " co_emp = " + intCodEmp+ " and " +
                                " co_loc = " + intCodLoc   + " and " +
                                " co_mnu = "+intCodMnuCotVen+" and st_reg = 'A' AND co_reg<>16 "   // 3965   "Cotizaciones de venta (Facturación electrónica)..."
                        );
                      System.out.println("ReglasDelNegocio:" + strSQL);
                      java.sql.ResultSet rstReg = stmAutReg.executeQuery(strSQL);
                      java.util.Vector vecLstCtls = new java.util.Vector(); 
                      boolean blnShowWin = false; // Presenta la ventana donde sale la lista de controle que no paso el programa.
                      while(rstReg.next()){
                      //  System.out.println("Buscando las reglas del negocion");
                        java.util.Vector vecDetCtl = new java.util.Vector();
                        String strStAutDoc= rstReg.getString("st_aut")+"",
                               strNomFun  = rstReg.getString("tx_nomfun")+"",
                               strDesCor  = rstReg.getString("tx_descor")+"",
                               strObs1    = rstReg.getString("tx_obs1")+"" ,
                               strCoReg   = rstReg.getString("co_reg")+"" ;
                        boolean blnCtlOk=false;
                        
                        if(strStAutDoc.equalsIgnoreCase("S")){
                           //  System.out.println("Comparando la funcion actual a ver si existe : " + strNomFun);
                             if(!vecIsAutorizado.contains(strNomFun)){
                            //     System.out.println("Verificando Las que no fueron autorizadas anteriormente");
                                 blnCtlOk = invocarFuncion(conCon, strNomFun, intCodEmp, intCodLoc, intCodCot);
                                 
                                 if(!blnCtlOk)
                                    blnShowWin = true;
                             }
                             
                        }

                        //Agregando los datos de esta funcion
                        vecDetCtl.add("");
                        vecDetCtl.add(new Boolean(!blnCtlOk));
                        vecDetCtl.add(strDesCor);
                        vecDetCtl.add(strObs1);
                        vecDetCtl.add(strCoReg);

                        vecLstCtls.add(vecDetCtl);
                      }

                     if(blnShowWin){
                        // Presentando la ventana de lista motivos por los que no se graba este documentp
                        objWinAut = new ZafWinAut(javax.swing.JOptionPane.getFrameForComponent(jfrThis), true , vecLstCtls);
                        objWinAut.show();
                        int intRes = objWinAut.getResultado();
                        return intRes;
                        
                     }
                    
                }else{
                    return checkCtls(conCon,intCodEmp,intCodLoc,intCodCot,intCodMnuCotVen);
                }
                
              }else{
                    return checkCtls(conCon,intCodEmp,intCodLoc,intCodCot,intCodMnuCotVen);
                }
            }
           
            
            
           return 1;       
        }
       catch(java.sql.SQLException Evt)
       {    
           objUtil.mostrarMsgErr_F1(jfrThis, Evt);
           return -1;             
       }
       catch(Exception Evt)
       {    
           objUtil.mostrarMsgErr_F1(jfrThis, Evt);
           return -1;             
       }      
        
    }
    
    
    
    /**
     * Obtiene el valor minimo y maximo establecido 
     * de esa regla para el usuario.
     * Estos valore pueden ser establecidos en el programa de
     * permisos para autorizaciones en el menu de herramientas
     * @return double[2] donde el double[0] = minimo y double[1]=maximo
     */
    public double[] getMinMaxForReg_X_Usr( String strNomFun ){
       java.sql.Connection conCon;
       double dblMinMax[] = {0,0};
       try{
           System.out.println("getMinMaxForReg_X_Usr ");
           conCon =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
           if (conCon!=null){
              /**
               * Extrayendo los valores minimos y maximos 
               * para este programa y usuario.
               */
              java.sql.Statement stmAutDoc = conCon.createStatement(); 

              /* Ejecutando el Consulta */
              java.sql.ResultSet rstAutDoc = stmAutDoc.executeQuery(
                        " SELECT nd_par1, nd_par2 " +
                        " FROM  tbm_regneg as reg INNER JOIN tbm_autdoc as aut " +
                        "       ON (reg.co_emp = aut.co_emp and reg.co_loc = aut.co_loc ) " +
                        " where " +
                        " reg.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                        " reg.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                        " reg.co_mnu = " + objZafParSis.getCodigoMenu()    + " and " +
                        " reg.tx_nomfun = '"+ strNomFun                    + "' and " +
                        " aut.co_usr = " + objZafParSis.getCodigoUsuario()
                );
              
              if(rstAutDoc.next()){
               //   System.out.println("Si encontro el maximo y minimo");
                dblMinMax[0] = rstAutDoc.getDouble("nd_par1");
                dblMinMax[1] = rstAutDoc.getDouble("nd_par2");
              }
             conCon.close();
             conCon = null;
           }
           return dblMinMax;             
       }              
       catch(java.sql.SQLException Evt)
       {
           objUtil.mostrarMsgErr_F1(jfrThis, Evt);
           return dblMinMax;             
       }
       catch(Exception Evt)
       {    
           objUtil.mostrarMsgErr_F1(jfrThis, Evt);
           return dblMinMax;             
       }
    }    
    
    
    private boolean invocarFuncion(String funcion)
    {
        boolean blnRes=true;
        try
        {
            System.out.println("invocarFuncion: " + funcion);
            java.lang.reflect.Method objMet=this.getClass().getMethod(funcion, null);
            Boolean bl = new Boolean(objMet.invoke(this, null)+"") ;
            return bl.booleanValue();
        }
        catch (NoSuchMethodException e){
            blnRes=false;
            System.out.println(" "+  e.toString());
        }
        catch (SecurityException e){
            blnRes=false;
            System.out.println(" "+  e);
        }
        catch (IllegalAccessException e){
            blnRes=false;
              System.out.println(" "+  e);
        }
        catch (IllegalArgumentException e) 
        {
            blnRes=false;
             System.out.println(" "+  e);
        }
        catch (java.lang.reflect.InvocationTargetException e) 
        {
            blnRes=false;
             System.out.println(" "+  e);
        }
        return blnRes;
    }    
    
        
    
    /**
     *
     *          DE AQUI EN ADELANTE ESTARAN TODO LOS METODOS 
     *          QUE VALIDARAN SI UN DOCUMENTO CUMPLE O NO
     *          CON EL CONTROL
     *          SI EL DOCUMENTO CUMPLE EL METODO RETORNARA TRUE
     *          SI NO CUMPLE RETORNARA FALSE
     *          DEBEN SER DECLARADOS DE LA SIGUIENTE MANERA
     *
     *          public boolean nombreMetodo()
     *
     *          NO DEBEN RECIBIR PARAMETROS, EL JINTERNALFRAME 
     *          QUE CONTIENE TODOS LOS OBJETOS LO ENVIAMOS EN EL CONTRUCTOR
     *          Y ESTA DECLARADO COMO VARIABLE GLOBAL javax.swing.JInternalFrame jfrThis.
     */
    /**
     * Ejemplo de funcion 
     * que realizaria alguna validacion 
     * de controles, en este caso devuelve verdadero
     */
    public boolean getPrueba(){
        return false;
    }

    /**
     * Esta función inserta la cabecera de los documentos de la tabla "tbm_cabCotCom"
     * para los cuales se ha solicitado autorización.
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodLoc El código del local de la tabla "tbm_cabCotCom".
     * @param intCodCot El código de la cotización de la tabla "tbm_cabCotCom".
     * @return true: Si se pudo insertar la cabecera de la autorización.
     * <BR>false: En el caso contrario.
     */
    public boolean insertarCabAutCotCom(java.sql.Connection con, int intCodLoc, int intCodCot)
    {
        java.sql.Statement stm;
        String strSQL;
        boolean blnRes=true;
        try
        {
            System.out.println("insertarCabAutCotCom ");
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabAutCotCom (co_emp, co_loc, co_cot, tx_obs1, co_usrAut, fe_aut, tx_comAut, st_reg)";
                strSQL+=" VALUES (";
                strSQL+="" + objZafParSis.getCodigoEmpresa();
                strSQL+=", " + intCodLoc;
                strSQL+=", " + intCodCot;
                strSQL+=", Null"; //Pendiente
                strSQL+=", Null";
                strSQL+=", Null";
                strSQL+=", Null";
		strSQL+=", 'P'";
                strSQL+=")";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUtil.mostrarMsgErr_F1(jfrThis, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUtil.mostrarMsgErr_F1(jfrThis, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función inserta el detalle de los documentos de la tabla "tbm_cabCotCom"
     * para los cuales se ha solicitado autorización.
     * @param con El objeto que contiene la conexión a la base de datos.
     * @return true: Si se pudo insertar el detalle de la autorización.
     * <BR>false: En el caso contrario.
     */
    public boolean insertarDetAutCotCom(java.sql.Connection con, int intCodLoc, int intCodCot)
    {
        java.sql.Statement stm;
        String strSQL;
        int i;
        boolean blnRes=true;
        try
        {
            System.out.println("insertarDetAutCotCom java.sql.Connection con, int intCodLoc, int intCodCot");
            if (con!=null)
            {
                stm=con.createStatement();
                for (i=0;i<1;i++) //Pendiente
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detAutCotCom (co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_regNeg, st_cum)";
                    strSQL+=" VALUES (";
                    strSQL+="" + objZafParSis.getCodigoEmpresa();
                    strSQL+=", " + intCodLoc;
                    strSQL+=", " + intCodCot;
                    strSQL+=", " + (i+1);
                    strSQL+=", 1"; //Pendiente
                    strSQL+=", 'N'"; //Pendiente
                    strSQL+=")";
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUtil.mostrarMsgErr_F1(jfrThis, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUtil.mostrarMsgErr_F1(jfrThis, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función inserta la cabecera de los documentos de la tabla "tbm_cabCotVen"
     * para los cuales se ha solicitado autorización.
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodLoc El código del local de la tabla "tbm_cabCotVen".
     * @param intCodCot El código de la cotización de la tabla "tbm_cabCotVen".
     * @return true: Si se pudo insertar la cabecera de la autorización.
     * <BR>false: En el caso contrario.
     */
    public boolean insertarCabAutCotVen(java.sql.Connection con, int intCodLoc, int intCodCot)
    {
        java.sql.Statement stm;
        String strSQL;
        boolean blnRes=true;
        try
        {
            System.out.println("insertarCabAutCotVen");
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabAutCotVen (co_emp, co_loc, co_cot, tx_obs1, co_usrAut, fe_aut, tx_comAut, st_reg)";
                strSQL+=" VALUES (";
                strSQL+="" + objZafParSis.getCodigoEmpresa();
                strSQL+=", " + intCodLoc;
                strSQL+=", " + intCodCot;
                strSQL+=", Null"; //Pendiente
                strSQL+=", Null";
                strSQL+=", Null";
                strSQL+=", Null";
		strSQL+=", 'P'";
                strSQL+=")";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUtil.mostrarMsgErr_F1(jfrThis, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUtil.mostrarMsgErr_F1(jfrThis, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función inserta el detalle de los documentos de la tabla "tbm_cabCotVen"
     * para los cuales se ha solicitado autorización.
     * @param con El objeto que contiene la conexión a la base de datos.
     * @return true: Si se pudo insertar el detalle de la autorización.
     * <BR>false: En el caso contrario.
     */
    public boolean insertarDetAutCotVen(java.sql.Connection con, int intCodLoc, int intCodCot)
    {
        java.sql.Statement stm;
        String strSQL;
        int i;
        boolean blnRes=true;
        try
        {
            System.out.println("insertarDetAutCotVen java.sql.Connection con, int intCodLoc, int intCodCot");
            if (con!=null)
            {
                stm=con.createStatement();
                for (i=0;i<1;i++) //Pendiente
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detAutCotVen (co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_regNeg, st_cum)";
                    strSQL+=" VALUES (";
                    strSQL+="" + objZafParSis.getCodigoEmpresa();
                    strSQL+=", " + intCodLoc;
                    strSQL+=", " + intCodCot;
                    strSQL+=", " + (i+1);
                    strSQL+=", 1"; //Pendiente
                    strSQL+=", 'N'"; //Pendiente
                    strSQL+=")";
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUtil.mostrarMsgErr_F1(jfrThis, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUtil.mostrarMsgErr_F1(jfrThis, e);
        }
        return blnRes;
    }
    
    
    
     
    public boolean insertarCabDetAut_3(java.sql.Connection con, int intCodPk[], int intTipIns, int inrvalor){
        String strTbCab = "", strTbDet = "";
        String strSQLMax="", strSQLIns="";
        java.util.Vector vecAux = null;        
        java.util.Vector vecAuxReg ;
        Boolean blnCum ;
        
        java.sql.Statement stm;  
        String strSQL;
        boolean blnRes=true;  
        
        try
        {
            System.out.println("insertarCabDetAut_3 ");
            if (con!=null)
            { 
                stm=con.createStatement();
               
              if(inrvalor != 0 ){  
                
                switch(intTipIns){
                  
                    case 3://tbm_cabAutCotVen
                            strTbCab = "tbm_cabAutCotVen";
                            strTbDet = "tbm_detAutCotVen";   
                           // System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>"+intTipIns);
                
                            
                           
                           
                            vecAux = objWinAut.getVecCtls();
                            String streg="P";
                            StringBuffer stb=new StringBuffer(); //VARIABLE TIPO BUFFER
                            for (int i=0;i<vecAux.size();i++) //Pendiente
                            {   
                                 
                                vecAuxReg = (java.util.Vector)vecAux.elementAt(i);
                                blnCum = new Boolean(vecAuxReg.elementAt(1) + "") ;
                              
                                 if(vecAuxReg.elementAt(3).toString().trim().equalsIgnoreCase("7")) streg="A";
                                   else streg="P";
                                
                                //Armar la sentencia SQL. 
                               //***************************************************
                                
                                     if (i>0)
                                         stb.append(" UNION ALL ");
                                         stb.append("SELECT "+objZafParSis.getCodigoEmpresa()+" AS coemp,"+intCodPk[0]+" AS coloc,"+intCodPk[1]+" AS cocot,"+vecAuxReg.elementAt(4)+" AS coregneg,'"+((!blnCum.booleanValue())?'S':'N')+"' as stcum,'"+streg+"' as streg");
                                       
                                
                               //  System.out.println("2>>>>>>>>>>>>>>>>>>>>>>>>>>"+ strSQLIns );
                                
                                  
                            
                            }
                                                        
                             if(intTipIns == 3) {    
                                   strSQLIns="UPDATE "+strTbDet+" SET st_cum=a.stcum,st_reg=a.streg" +
                                    " FROM( "+stb.toString()+" ) AS a " +
                                    " WHERE co_emp=a.coemp AND co_loc=a.coloc AND co_cot=a.cocot " +
                                    " AND co_regneg=a.coregneg";   
                                    stm.executeUpdate(strSQLIns);    
                                    
                              }
                             stb=null;
                        break;
                }
              }   
                stm.close();
                stm=null; 
            }  
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUtil.mostrarMsgErr_F1(jfrThis, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUtil.mostrarMsgErr_F1(jfrThis, e);
        }
        return blnRes;
        
        
    }

   
    
    
    
    
    
    public boolean insertarCabDetAut_2(java.sql.Connection con, int intCodPk[], int intTipIns, int intvalor){
        String strTbCab = "", strTbDet = "";
        String strSQLMax="", strSQLIns="";
        java.util.Vector vecAux = null;        
        java.util.Vector vecAuxReg ;
        Boolean blnCum ;
        
        java.sql.Statement stm;  
        String strSQL;
        boolean blnRes=true;
          
                  
         
        try  
        {
            System.out.println("insertarCabDetAut_2 ");
            if (con!=null)
            { 
                stm=con.createStatement();
                
              if(intvalor != 0 ){    
                 
                switch(intTipIns){
                  
                    case 3://tbm_cabAutCotVen
                            strTbCab = "tbm_cabAutCotVen";
                            strTbDet = "tbm_detAutCotVen";   
                          //  System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>"+intTipIns);
                  
                            
                            //Obtener el código del último registro.
                            strSQLMax=""; strSQLIns="";
                            strSQLMax+=" SELECT MAX(a1.co_aut)";
                            strSQLMax+="  FROM " + strTbCab +" AS a1";
                            strSQLMax+="  WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                            strSQLMax+="  AND a1.co_loc=" + intCodPk[0];
                            strSQLMax+="  AND a1.co_cot=" + intCodPk[1];

                            intCodAut=objUtil.getNumeroRegistro(javax.swing.JOptionPane.getFrameForComponent(jfrThis), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQLMax);
                            if (intCodAut==-1)
                                return false;
                            
                            intCodAut++;                            
                          
                            //  System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>"+intTipIns);
                               
                            
                            //Armar la sentencia SQL.
                            strSQLIns="";
                            strSQLIns+="INSERT INTO " + strTbCab + " (co_emp, co_loc,  co_cot, co_aut, tx_obs1, tx_obs2, co_usrAut, fe_aut, tx_comAut, st_reg)";
                            strSQLIns+=" VALUES (";
                            strSQLIns+="" + objZafParSis.getCodigoEmpresa();
                            strSQLIns+=", " + intCodPk[0];
                            strSQLIns+=", " + intCodPk[1];
                            strSQLIns+=", " + intCodAut;
                            strSQLIns+=",' '";  // + objUtil.codificar(objWinAut.getObservacion1());
                            strSQLIns+=", Null";
                            strSQLIns+=", Null";
                            strSQLIns+=", Null";
                            strSQLIns+=", Null";
                            strSQLIns+=", 'P'";
                            strSQLIns+=")";
                            
                          ///  System.out.println("INS ==> "+ strSQLIns );
                            
                              
                            ///************************************************
                             int NumReg = 0; 
                             java.sql.ResultSet rst2;
                             
                              
                               
                             java.sql.Statement stm2= con.createStatement();   
                              
                           
                            
                           // System.out.println("INS ==> OK.... ");
                             
                             String sql = "select co_aut from tbm_cabAutCotVen  where co_emp="+objZafParSis.getCodigoEmpresa()+" " +
                              " and co_loc="+ intCodPk[0]+" and  co_cot="+ intCodPk[1];
                            //  System.out.println("INS ==> "+ sql );
                             rst2 = stm2.executeQuery(sql);
                             int intCodAUT=0;
                             if(rst2.next()){
                                  intCodAUT = rst2.getInt(1);
                                  // if(i > 0) {
                                     NumReg = 1;
                                 //  }
                              }
                          
                             
                              
                             
                            if(intTipIns == 3 && NumReg == 0)
                              stm.executeUpdate(strSQLIns);
                               
                              
                             
                           
                           
                            vecAux = objWinAut.getVecCtls();
                            
                            
                            String streg="P";
                            StringBuffer stb=new StringBuffer(); //VARIABLE TIPO BUFFER
                            for (int i=0;i<vecAux.size();i++) //Pendiente
                            {    
                                
                                 
                                 vecAuxReg = (java.util.Vector)vecAux.elementAt(i);
                               
                                blnCum = new Boolean(vecAuxReg.elementAt(1) + "") ;
                               
                              
                               
                                 if(vecAuxReg.elementAt(4).toString().trim().equalsIgnoreCase("7")) streg="A";
                                   else streg="P";
                                //Armar la sentencia SQL. 
                                
                                 if(intTipIns == 3 && NumReg == 0){    
                                         if (i>0)
                                         stb.append(" UNION ALL ");
                                         stb.append("SELECT "+objZafParSis.getCodigoEmpresa()+" AS coemp,"+intCodPk[0]+" AS coloc,"+intCodPk[1]+" AS cocot,"+intCodAut+" AS coaut,"+(i+1)+" AS coreg,"+vecAuxReg.elementAt(4)+" AS coregneg,'"+((!blnCum.booleanValue())?'S':'N')+"' as stcum,'"+streg+"' as streg");
                                 }
                                
                                 
                            
                            }
                                                        
                             if(intTipIns == 3 && NumReg == 0){    
                                   strSQLIns="INSERT INTO "+strTbDet+"(co_emp, co_loc, co_cot, co_aut, co_reg, co_regNeg, st_cum,st_reg)"+stb.toString();
                                  stm.executeUpdate(strSQLIns);
                              }
                             stb=null;
                        break;
                }
               }   
                stm.close();
                stm=null; 
            }  
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUtil.mostrarMsgErr_F1(jfrThis, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUtil.mostrarMsgErr_F1(jfrThis, e);
        }
        return blnRes;
        
        
    }

    
    
    
    
    
    
    
    
    
    
    
    
      public boolean insertarCabDetAut_aut(java.sql.Connection con, int intCodPk[], int intTipIns){
        String strTbCab = "", strTbDet = "";
        String strSQLMax="", strSQLIns="";
        java.util.Vector vecAux = null;        
        java.util.Vector vecAuxReg ;
        Boolean blnCum ;
        
        java.sql.Statement stm;
        String strSQL;
        boolean blnRes=true;
        try
        {
            System.out.println("insertarCabDetAut_aut ");
            if (con!=null)
            {
                stm=con.createStatement();
                switch(intTipIns){
                    case 1://tbm_cabAutMovInv
                            strTbCab = "tbm_cabAutMovInv";
                            strTbDet = "tbm_detAutMovInv";                            
                    case 2://tbm_cabAutPag
                            if(intTipIns==2){
                                strTbCab = "tbm_cabAutPag";
                                strTbDet = "tbm_detAutPag";
                            }
                            //Obtener el código del último registro.
                            strSQLMax=""; strSQLIns="";
                            
                            strSQLMax+=" SELECT MAX(a1.co_aut)";
                            strSQLMax+="  FROM " + strTbCab +" AS a1";
                            strSQLMax+="  WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                            strSQLMax+="  AND a1.co_loc=" + intCodPk[0];
                            strSQLMax+="  AND a1.co_tipDoc=" + intCodPk[1];
                            strSQLMax+="  AND a1.co_doc=" + intCodPk[2];

                            intCodAut=objUtil.getNumeroRegistro(javax.swing.JOptionPane.getFrameForComponent(jfrThis), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQLMax);
                            if (intCodAut==-1)
                                return false;
                            
                            intCodAut++;                            
                            
                            //Armar la sentencia SQL.
                            strSQLIns="";
                            strSQLIns+="INSERT INTO " + strTbCab + " (co_emp, co_loc, co_tipDoc, co_doc, co_aut, tx_obs1, tx_obs2, co_usrAut, fe_aut, tx_comAut, st_reg)";
                            strSQLIns+=" VALUES (";
                            strSQLIns+="" + objZafParSis.getCodigoEmpresa();
                            strSQLIns+=", " + intCodPk[0];
                            strSQLIns+=", " + intCodPk[1];
                            strSQLIns+=", " + intCodPk[2];
                            strSQLIns+=", " + intCodAut;
                            strSQLIns+=", " + objUtil.codificar(objWinAut.getObservacion1());
                            strSQLIns+=", Null";
                            strSQLIns+=", Null";
                            strSQLIns+=", Null";
                            strSQLIns+=", Null";
                            strSQLIns+=", 'P'";
                            strSQLIns+=")";
                            
                            stm.executeUpdate(strSQLIns);
                            //stm=con.createStatement();
                            vecAux = objWinAut.getVecCtls();
                            
                           // bl.booleanValue();
                            for (int i=0;i<vecAux.size();i++) //Pendiente
                            {  // System.out.println("CONTROL #: " + i);
                                vecAuxReg = (java.util.Vector)vecAux.elementAt(i);
                                blnCum = new Boolean(vecAuxReg.elementAt(0) +"") ;
                                //Armar la sentencia SQL.
                                strSQLIns="";
                                strSQLIns+="INSERT INTO " + strTbDet + " (co_emp, co_loc, co_tipDoc, co_doc, co_aut, co_reg, co_regNeg, st_cum)";
                                strSQLIns+=" VALUES (";
                                strSQLIns+="" + objZafParSis.getCodigoEmpresa();
                                strSQLIns+=", " + intCodPk[0];
                                strSQLIns+=", " + intCodPk[1];
                                strSQLIns+=", " + intCodPk[2];
                                strSQLIns+=", " + intCodAut;
                                strSQLIns+=", " + (i+1);
                                strSQLIns+=", "+ vecAuxReg.elementAt(3) ; //Pendiente
                                strSQLIns+=", '"+ ((!blnCum.booleanValue())?'S':'N') + "'"; //Pendiente
                                strSQLIns+=")";
                                stm.executeUpdate(strSQLIns);
                            }
                            
                        break;
                    case 3://tbm_cabAutCotVen
                            strTbCab = "tbm_cabAutCotVen";
                            strTbDet = "tbm_detAutCotVen";   
                            
                    case 4://tbm_cabAutPag
                            if(intTipIns==4){
                                strTbCab = "tbm_cabAutCotCom";
                                strTbDet = "tbm_detAutCotCom";
                            }                            
                            //Obtener el código del último registro.
                            strSQLMax=""; strSQLIns="";
                            strSQLMax+=" SELECT MAX(a1.co_aut)";
                            strSQLMax+="  FROM " + strTbCab +" AS a1";
                            strSQLMax+="  WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                            strSQLMax+="  AND a1.co_loc=" + intCodPk[0];
                            strSQLMax+="  AND a1.co_cot=" + intCodPk[1];

                            intCodAut=objUtil.getNumeroRegistro(javax.swing.JOptionPane.getFrameForComponent(jfrThis), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQLMax);
                            if (intCodAut==-1)
                                return false;
                            
                            intCodAut++;                            
                          
                            
                            
                            //Armar la sentencia SQL.
                            strSQLIns="";
                            strSQLIns+="INSERT INTO " + strTbCab + " (co_emp, co_loc,  co_cot, co_aut, tx_obs1, tx_obs2, co_usrAut, fe_aut, tx_comAut, st_reg)";
                            strSQLIns+=" VALUES (";
                            strSQLIns+="" + objZafParSis.getCodigoEmpresa();
                            strSQLIns+=", " + intCodPk[0];
                            strSQLIns+=", " + intCodPk[1];
                            strSQLIns+=", " + intCodAut;
                            strSQLIns+=", " + objUtil.codificar(objWinAut.getObservacion1());
                            strSQLIns+=", Null";
                            strSQLIns+=", Null";
                            strSQLIns+=", Null";
                            strSQLIns+=", Null";
                            strSQLIns+=", 'P'";
                            strSQLIns+=")";
                            
                          //  System.out.println("==> "+ strSQLIns );
                            
                              
                            ///************************************************
                             int NumReg = 0; 
                             java.sql.ResultSet rst2;
                             java.sql.Statement stm2= con.createStatement();   
                             String sql = "select co_aut from tbm_cabAutCotVen  where co_emp="+objZafParSis.getCodigoEmpresa()+" " +
                              " and co_loc="+ intCodPk[0]+" and  co_cot="+ intCodPk[1];
                             rst2 = stm2.executeQuery(sql);
                             int intCodAUT=0;
                             if(rst2.next()){
                                  intCodAUT = rst2.getInt(1);
                                  // if(i > 0) {
                                     NumReg = 1;
                                 //  }
                              }
                          
                            if(intTipIns == 3 && NumReg == 0)
                              stm.executeUpdate(strSQLIns);
                               
                             if(intTipIns == 4)
                                  stm.executeUpdate(strSQLIns);
                           
                             
                           ///************************************************
                          //****  stm.executeUpdate(strSQLIns);
                            
                           
                            vecAux = objWinAut.getVecCtls();
                            
                           // bl.booleanValue();
                            for (int i=0;i<vecAux.size();i++) //Pendiente
                            {   
                                vecAuxReg = (java.util.Vector)vecAux.elementAt(i);
                                blnCum = new Boolean(vecAuxReg.elementAt(1) + "") ;
                                //Armar la sentencia SQL.
                                strSQLIns="";
                                strSQLIns+="INSERT INTO " + strTbDet + " (co_emp, co_loc,  co_cot, co_aut, co_reg, co_regNeg, st_cum)";
                                strSQLIns+=" VALUES (";
                                strSQLIns+="" + objZafParSis.getCodigoEmpresa();
                                strSQLIns+=", " + intCodPk[0];
                                strSQLIns+=", " + intCodPk[1];
                                strSQLIns+=", " + intCodAut;
                                strSQLIns+=", " + (i+1);
                                strSQLIns+=", "+ vecAuxReg.elementAt(4) ; //Pendiente
                                strSQLIns+=", '"+ ((!blnCum.booleanValue())?'S':'N') + "'"; //Pendiente
                                strSQLIns+=")";
                                //***************************************************
                                  if(intTipIns == 3 && NumReg == 0)    //***************
                                        stm.executeUpdate(strSQLIns);  //****************
                                
                                   if(intTipIns == 3 && NumReg == 1) {   //***************
                                       
                                       
                                        strSQLIns=" UPDATE " + strTbCab + " SET  tx_obs1="+objUtil.codificar(objWinAut.getObservacion1()) +"  where " +
                                        " co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+ intCodPk[0]+" and  co_cot="+ intCodPk[1];
                                        stm.executeUpdate(strSQLIns);
                                       
                                       strSQLIns="UPDATE " + strTbDet + "  set  st_cum='"+ ((!blnCum.booleanValue())?'S':'N')+"'";
                                         strSQLIns+=" where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+intCodPk[0]+" and co_cot="+intCodPk[1];
                                         strSQLIns+=" and co_aut ="+intCodAUT +" and co_regNeg="+vecAuxReg.elementAt(4);
                                       //  System.out.println("OKIII   "+strSQLIns);
                                         stm.executeUpdate(strSQLIns);
                                   }
                                
                                //***************************************************
                                     if(intTipIns == 4 )                     //**************
                                          stm.executeUpdate(strSQLIns);      //****************
                                //***** stm.executeUpdate(strSQLIns); 
                                
                            }
                                                        
                            
                        break;
                }
                
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUtil.mostrarMsgErr_F1(jfrThis, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUtil.mostrarMsgErr_F1(jfrThis, e);
        }
        return blnRes;
        
        
    }
    
    
    
    
    
    
    

    /**
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodPk Los codigos de la clave primaria
     * @param intCodTipDoc El código del tipo de documento de la tabla, en caso de no llevar poner 0.
     * @param intCodDoc El código del documento de la tabla "tbm_cabPag".
     * @param intTipIns Un entero que especifica si se inserta en 
     * <BR><BR>
     * <CENTER>
     * <TABLE BORDER=1>
     * <TR><TD><I>Entero</I></TD><TD><I>Tablas que afecta</I></TD></TR>
     * <TR><TD>1</TD><TD>tbm_cabAutMovInv, tbm_detAutMovInv</TD></TR>
     * <TR><TD>2</TD><TD>tbm_cabAutPag, tbm_detAutPag</TD></TR>
     * <TR><TD>3</TD><TD>tbm_cabAutCotVen, tbm_detAutCotVen</TD></TR>
     * <TR><TD>4</TD><TD>tbm_cabAutCotCom, tbm_detAutCotCom</TD></TR>
     * </TABLE>
     * </CENTER>
  
     * @return true: Si se pudo insertar la cabecera de la autorización.
     */
    public boolean insertarCabDetAut(java.sql.Connection con, int intCodPk[], int intTipIns){
        String strTbCab = "", strTbDet = "";
        String strSQLMax="", strSQLIns="";
        java.util.Vector vecAux = null;        
        java.util.Vector vecAuxReg ;
        Boolean blnCum ;
        
        java.sql.Statement stm;
        String strSQL;
        boolean blnRes=true;
        try
        {
             System.out.println("insertarCabDetAut ");
            if (con!=null)
            {
                stm=con.createStatement();
        
                  // System.out.println("DENTRO DE LA FUNCION SOLICITA AUTORIZACION ..............");
                    
                switch(intTipIns){
                    case 1://tbm_cabAutMovInv
                            strTbCab = "tbm_cabAutMovInv";
                            strTbDet = "tbm_detAutMovInv";                            
                    case 2://tbm_cabAutPag
                            if(intTipIns==2){
                                strTbCab = "tbm_cabAutPag";
                                strTbDet = "tbm_detAutPag";
                            }
                            //Obtener el código del último registro.
                            strSQLMax=""; strSQLIns="";
                            
                            strSQLMax+=" SELECT MAX(a1.co_aut)";
                            strSQLMax+="  FROM " + strTbCab +" AS a1";
                            strSQLMax+="  WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                            strSQLMax+="  AND a1.co_loc=" + intCodPk[0];
                            strSQLMax+="  AND a1.co_tipDoc=" + intCodPk[1];
                            strSQLMax+="  AND a1.co_doc=" + intCodPk[2];

                            intCodAut=objUtil.getNumeroRegistro(javax.swing.JOptionPane.getFrameForComponent(jfrThis), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQLMax);
                            if (intCodAut==-1)
                                return false;
                            
                            intCodAut++;                            
                            
                            //Armar la sentencia SQL.
                            strSQLIns="";
                            strSQLIns+="INSERT INTO " + strTbCab + " (co_emp, co_loc, co_tipDoc, co_doc, co_aut, tx_obs1, tx_obs2, co_usrAut, fe_aut, tx_comAut, st_reg)";
                            strSQLIns+=" VALUES (";
                            strSQLIns+="" + objZafParSis.getCodigoEmpresa();
                            strSQLIns+=", " + intCodPk[0];
                            strSQLIns+=", " + intCodPk[1];
                            strSQLIns+=", " + intCodPk[2];
                            strSQLIns+=", " + intCodAut;
                            strSQLIns+=", " + objUtil.codificar(objWinAut.getObservacion1());
                            strSQLIns+=", Null";
                            strSQLIns+=", Null";
                            strSQLIns+=", Null";
                            strSQLIns+=", Null";
                            strSQLIns+=", 'P'";
                            strSQLIns+=")";
                            
                            stm.executeUpdate(strSQLIns);
                            //stm=con.createStatement();
                            vecAux = objWinAut.getVecCtls();
                            
                           // bl.booleanValue();
                            for (int i=0;i<vecAux.size();i++) //Pendiente
                            { //  System.out.println("CONTROL #: " + i);
                                vecAuxReg = (java.util.Vector)vecAux.elementAt(i);
                                blnCum = new Boolean(vecAuxReg.elementAt(1) +"") ;
                                //Armar la sentencia SQL.
                                strSQLIns="";
                                strSQLIns+="INSERT INTO " + strTbDet + " (co_emp, co_loc, co_tipDoc, co_doc, co_aut, co_reg, co_regNeg, st_cum)";
                                strSQLIns+=" VALUES (";
                                strSQLIns+="" + objZafParSis.getCodigoEmpresa();
                                strSQLIns+=", " + intCodPk[0];
                                strSQLIns+=", " + intCodPk[1];
                                strSQLIns+=", " + intCodPk[2];
                                strSQLIns+=", " + intCodAut;
                                strSQLIns+=", " + (i+1);
                                strSQLIns+=", "+ vecAuxReg.elementAt(4) ; //Pendiente
                                strSQLIns+=", '"+ ((!blnCum.booleanValue())?'S':'N') + "'"; //Pendiente
                                strSQLIns+=")";
                                stm.executeUpdate(strSQLIns);
                            }
                            
                        break;
                    case 3://tbm_cabAutCotVen
                            strTbCab = "tbm_cabAutCotVen";
                            strTbDet = "tbm_detAutCotVen";   
                            
                    case 4://tbm_cabAutPag
                            if(intTipIns==4){
                                strTbCab = "tbm_cabAutCotCom";
                                strTbDet = "tbm_detAutCotCom";
                            }                            
                            //Obtener el código del último registro.
                            strSQLMax=""; strSQLIns="";
                            strSQLMax+=" SELECT MAX(a1.co_aut)";
                            strSQLMax+="  FROM " + strTbCab +" AS a1";
                            strSQLMax+="  WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                            strSQLMax+="  AND a1.co_loc=" + intCodPk[0];
                            strSQLMax+="  AND a1.co_cot=" + intCodPk[1];

                            //System.out.println("DENTRO DE LA FUNCION SOLICITA AUTORIZACION ..............2");
                             
                            intCodAut=objUtil.getNumeroRegistro(javax.swing.JOptionPane.getFrameForComponent(jfrThis), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQLMax);
                            if (intCodAut==-1)
                                return false;
                            
                            intCodAut++;                            
                          
                           // System.out.println("DENTRO DE LA FUNCION SOLICITA AUTORIZACION ..............3");
                            
                            //Armar la sentencia SQL.
                            strSQLIns="";
                            strSQLIns+="INSERT INTO " + strTbCab + " (co_emp, co_loc,  co_cot, co_aut, tx_obs1, tx_obs2, co_usrAut, fe_aut, tx_comAut, st_reg)";
                            strSQLIns+=" VALUES (";
                            strSQLIns+="" + objZafParSis.getCodigoEmpresa();
                            strSQLIns+=", " + intCodPk[0];
                            strSQLIns+=", " + intCodPk[1];
                            strSQLIns+=", " + intCodAut;
                            
                           //  System.out.println("DENTRO DE LA FUNCION SOLICITA AUTORIZACION ..............3.1-- "+ objWinAut.getObservacion1()  );
                              
                             
                            strSQLIns+=", " + objUtil.codificar(objWinAut.getObservacion1());
                           
                          //   System.out.println("DENTRO DE LA FUNCION SOLICITA AUTORIZACION ..............3.2");
                            
                             
                            strSQLIns+=", Null";
                            strSQLIns+=", Null";
                            strSQLIns+=", Null";
                            strSQLIns+=", Null";
                            strSQLIns+=", 'P'";
                            strSQLIns+=")";
                            
                          
                            ///*************************************************************
                             int NumReg = 0; 
                             java.sql.ResultSet rst2;
                             java.sql.Statement stm2= con.createStatement();   
                           //  System.out.println("DENTRO DE LA FUNCION SOLICITA AUTORIZACION ..............5");
                             String sql = "select co_aut from tbm_cabAutCotVen  where co_emp="+objZafParSis.getCodigoEmpresa()+" " +
                              " and co_loc="+ intCodPk[0]+" and  co_cot="+ intCodPk[1];
                           //  System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ sql);
                             rst2 = stm2.executeQuery(sql);
                             int intCodAUT=0;
                             if(rst2.next()){
                                  intCodAUT = rst2.getInt(1);
                                  // if(i > 0) {
                                     NumReg = 1;
                                 //  }  
                              }  
                          
                             
                              
                            if(intTipIns == 3 && NumReg == 0)
                              stm.executeUpdate(strSQLIns);
                               
                             if(intTipIns == 4)
                                  stm.executeUpdate(strSQLIns);
                           
                             
                           ///************************************************
                          //****  stm.executeUpdate(strSQLIns);
                            
                           
                            vecAux = objWinAut.getVecCtls();
                            String streg="P";
                         
                            StringBuffer stb=new StringBuffer(); //VARIABLE TIPO BUFFER
                            
                            for (int i=0;i<vecAux.size();i++) //Pendiente
                            {   
                                vecAuxReg = (java.util.Vector)vecAux.elementAt(i);
                                blnCum = new Boolean(vecAuxReg.elementAt(1) + "") ;
                                
                                 if(blnCum.booleanValue()) streg="P";
                                   else streg="A";
                                
                                //Armar la sentencia SQL.
                                //***************************************************
                               
                                 if(intTipIns == 3 && NumReg == 0){    
                                         if (i>0)
                                         stb.append(" UNION ALL ");
                                         stb.append("SELECT "+objZafParSis.getCodigoEmpresa()+" AS coemp,"+intCodPk[0]+" AS coloc,"+intCodPk[1]+" AS cocot,"+intCodAut+" AS coaut,"+(i+1)+" AS coreg,"+vecAuxReg.elementAt(4)+" AS coregneg,'"+((!blnCum.booleanValue())?'S':'N')+"' as stcum,'"+streg+"' as streg");
                                 }
                                         
                                
                                   if(intTipIns == 3 && NumReg == 1) {   //***************
                                       
                                       
                                        if (i>0)
                                         stb.append(" UNION ALL ");
                                         stb.append("SELECT "+objZafParSis.getCodigoEmpresa()+" AS coemp,"+intCodPk[0]+" AS coloc,"+intCodPk[1]+" AS cocot,"+intCodAUT+" AS coaut,"+vecAuxReg.elementAt(4)+" AS coregneg,'"+((!blnCum.booleanValue())?'S':'N')+"' as stcum,'"+streg+"' as streg");
                                       
                                       
//                                         strSQLIns="UPDATE " + strTbDet + "  set  st_cum='"+ ((!blnCum.booleanValue())?'S':'N')+"'";
//                                         strSQLIns+=",st_reg='"+streg+"'  where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+intCodPk[0]+" and co_cot="+intCodPk[1];
//                                         strSQLIns+=" and co_aut ="+intCodAUT +" and co_regNeg="+vecAuxReg.elementAt(3);
//                                         System.out.println("OKIII   "+strSQLIns);
//                                         stm.executeUpdate(strSQLIns);
                                   }
                                
                                //***************************************************
                                     if(intTipIns == 4 )                     //**************
                                          stm.executeUpdate(strSQLIns);      //****************
                                //***** stm.executeUpdate(strSQLIns); 
                                   
                            }  
                              
                               if(intTipIns == 3 && NumReg == 0){    
                                   strSQLIns="INSERT INTO "+strTbDet+"(co_emp, co_loc, co_cot, co_aut, co_reg, co_regNeg, st_cum,st_reg)"+stb.toString();
                                   stm.executeUpdate(strSQLIns);
                              }
                            
                              if(intTipIns == 3 && NumReg == 1) {
                                    strSQLIns="UPDATE "+strTbDet+" SET st_cum=a.stcum,st_reg=a.streg" +
                                    " FROM( "+stb.toString()+" ) AS a " +
                                    " WHERE co_emp=a.coemp AND co_loc=a.coloc AND co_cot=a.cocot AND co_aut=a.coaut " +
                                    " AND co_regneg=a.coregneg";   
                                    stm.executeUpdate(strSQLIns);    
                                  //  System.out.println("OKIII   "+ strSQLIns );
                                    if(!objUtil.codificar(objWinAut.getObservacion1()).equals("Null")){
                                       strSQLIns=" UPDATE " + strTbCab + " SET  tx_obs1="+objUtil.codificar(objWinAut.getObservacion1()) +"  where " +
                                       " co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+ intCodPk[0]+" and  co_cot="+ intCodPk[1];
                                       stm.executeUpdate(strSQLIns);
                                    //   System.out.println("OKIII   "+strSQLIns);
                                    }
                                }  
                              stb=null;
                        break;
                }
                 
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUtil.mostrarMsgErr_F1(jfrThis, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUtil.mostrarMsgErr_F1(jfrThis, e);
        }
        return blnRes;
        
        
    }

    
    /*DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDd*/
    
    
    
    
    

    /**
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodPk Los codigos de la clave primaria
     * @param intCodTipDoc El código del tipo de documento de la tabla, en caso de no llevar poner 0.
     * @param intCodDoc El código del documento de la tabla "tbm_cabPag".
     * @param intTipIns Un entero que especifica si se inserta en 
     * <BR><BR>
     * <CENTER>
     * <TABLE BORDER=1>
     * <TR><TD><I>Entero</I></TD><TD><I>Tablas que afecta</I></TD></TR>
     * <TR><TD>1</TD><TD>tbm_cabAutMovInv, tbm_detAutMovInv</TD></TR>
     * <TR><TD>2</TD><TD>tbm_cabAutPag, tbm_detAutPag</TD></TR>
     * <TR><TD>3</TD><TD>tbm_cabAutCotVen, tbm_detAutCotVen</TD></TR>
     * <TR><TD>4</TD><TD>tbm_cabAutCotCom, tbm_detAutCotCom</TD></TR>
     * </TABLE>
     * </CENTER>
  
     * @return true: Si se pudo insertar la cabecera de la autorización.
     */
    public boolean insertarCabDetAut(java.sql.Connection con,int intCodEmp, int intCodLoc, int intCodCot, int intTipIns){
        String strTbCab = "", strTbDet = "";
        String strSQLMax="", strSQLIns="";
        java.util.Vector vecAux = null;        
        java.util.Vector vecAuxReg ;
        Boolean blnCum ;
        
        java.sql.Statement stm;
        String strSQL;
        boolean blnRes=true;
        try
        {
             System.out.println("insertarCabDetAut ");
            if (con!=null)
            {
                stm=con.createStatement();
            
                strTbCab = "tbm_cabAutCotVen";
                strTbDet = "tbm_detAutCotVen";   


                //Obtener el código del último registro.
                strSQLMax=""; strSQLIns="";
                strSQLMax+=" SELECT MAX(a1.co_aut)";
                strSQLMax+="  FROM " + strTbCab +" AS a1";
                strSQLMax+="  WHERE a1.co_emp=" + intCodEmp;
                strSQLMax+="  AND a1.co_loc=" + intCodLoc;
                strSQLMax+="  AND a1.co_cot=" + intCodCot;

                //System.out.println("DENTRO DE LA FUNCION SOLICITA AUTORIZACION ..............2");

                intCodAut=objUtil.getNumeroRegistro(javax.swing.JOptionPane.getFrameForComponent(jfrThis), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQLMax);
                if (intCodAut==-1)
                    return false;

                intCodAut++;                            

               // System.out.println("DENTRO DE LA FUNCION SOLICITA AUTORIZACION ..............3");

                //Armar la sentencia SQL.
                strSQLIns="";
                strSQLIns+="INSERT INTO " + strTbCab + " (co_emp, co_loc,  co_cot, co_aut, tx_obs1, tx_obs2, co_usrAut, fe_aut, tx_comAut, st_reg)";
                strSQLIns+=" VALUES (";
                strSQLIns+="" + intCodEmp;
                strSQLIns+=", " + intCodLoc;
                strSQLIns+=", " + intCodCot;
                strSQLIns+=", " + intCodAut;

        
                strSQLIns+=", " + objUtil.codificar(objWinAut.getObservacion1());

        
                strSQLIns+=", Null";
                strSQLIns+=", Null";
                strSQLIns+=", Null";
                strSQLIns+=", Null";
                strSQLIns+=", 'P'";
                strSQLIns+=")";


                ///*************************************************************
                 int NumReg = 0; 
                 java.sql.ResultSet rst2;
                 java.sql.Statement stm2= con.createStatement();   
               //  System.out.println("DENTRO DE LA FUNCION SOLICITA AUTORIZACION ..............5");
                 String sql = "select co_aut from tbm_cabAutCotVen  where co_emp="+intCodEmp+" " +
                  " and co_loc="+ intCodLoc+" and  co_cot="+ intCodCot;

                 rst2 = stm2.executeQuery(sql);
                 int intCodAUT=0;
                 if(rst2.next()){
                      intCodAUT = rst2.getInt(1);
                      // if(i > 0) {
                         NumReg = 1;
                     //  }  
                  }  



                if(intTipIns == 3 && NumReg == 0)
                  stm.executeUpdate(strSQLIns);

                 if(intTipIns == 4)
                      stm.executeUpdate(strSQLIns);


               ///************************************************
              //****  stm.executeUpdate(strSQLIns);


                vecAux = objWinAut.getVecCtls();
                String streg="P";

                StringBuffer stb=new StringBuffer(); //VARIABLE TIPO BUFFER

                for (int i=0;i<vecAux.size();i++) //Pendiente
                {   
                    vecAuxReg = (java.util.Vector)vecAux.elementAt(i);
                    blnCum = new Boolean(vecAuxReg.elementAt(1) + "") ;

                     if(blnCum.booleanValue()) streg="P";
                       else streg="A";

                    //Armar la sentencia SQL.
                    //***************************************************

                     if(intTipIns == 3 && NumReg == 0){    
                             if (i>0)
                             stb.append(" UNION ALL ");
                             stb.append("SELECT "+intCodEmp+" AS coemp,"+intCodLoc+" AS coloc,"+intCodCot+" AS cocot,"+intCodAut+" AS coaut,"+(i+1)+" AS coreg,"+vecAuxReg.elementAt(4)+" AS coregneg,'"+((!blnCum.booleanValue())?'S':'N')+"' as stcum,'"+streg+"' as streg");
                     }


                       if(intTipIns == 3 && NumReg == 1) {   //***************


                            if (i>0)
                             stb.append(" UNION ALL ");
                             stb.append("SELECT "+intCodEmp+" AS coemp,"+intCodLoc+" AS coloc,"+intCodCot+" AS cocot,"+intCodAUT+" AS coaut,"+vecAuxReg.elementAt(4)+" AS coregneg,'"+((!blnCum.booleanValue())?'S':'N')+"' as stcum,'"+streg+"' as streg");



                       }

                    //***************************************************
                         if(intTipIns == 4 )                     //**************
                              stm.executeUpdate(strSQLIns);      //****************
                    //***** stm.executeUpdate(strSQLIns); 

                }  

                   if(intTipIns == 3 && NumReg == 0){    
                       strSQLIns="INSERT INTO "+strTbDet+"(co_emp, co_loc, co_cot, co_aut, co_reg, co_regNeg, st_cum,st_reg)"+stb.toString();
                       stm.executeUpdate(strSQLIns);
                  }

                  if(intTipIns == 3 && NumReg == 1) {
                        strSQLIns="UPDATE "+strTbDet+" SET st_cum=a.stcum,st_reg=a.streg" +
                        " FROM( "+stb.toString()+" ) AS a " +
                        " WHERE co_emp=a.coemp AND co_loc=a.coloc AND co_cot=a.cocot AND co_aut=a.coaut " +
                        " AND co_regneg=a.coregneg";   
                        stm.executeUpdate(strSQLIns);    
                        if(!objUtil.codificar(objWinAut.getObservacion1()).equals("Null")){
                           strSQLIns=" UPDATE " + strTbCab + " SET  tx_obs1="+objUtil.codificar(objWinAut.getObservacion1()) +"  where " +
                           " co_emp="+intCodEmp+" and co_loc="+ intCodLoc+" and  co_cot="+ intCodCot;
                           stm.executeUpdate(strSQLIns);
                        }
                    }  
                  stb=null;
                       
                 
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUtil.mostrarMsgErr_F1(jfrThis, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUtil.mostrarMsgErr_F1(jfrThis, e);
        }
        return blnRes;
        
        
    }

    
    
    /* JoseMario: Modificacion para reservas */
    
    /**
     * Invocar Funciones Solo para reservas
     * @param conExt
     * @param funcion
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodCot
     * @return 
     */
    
    private boolean invocarFuncion(java.sql.Connection conExt, String funcion, int intCodEmp, int intCodLoc, int intCodCot)
    {
        boolean blnRes=true;
        try
        {
            if(conExt!=null){
                 System.out.println("invocarFuncion: " + funcion);
                if(funcion.equals("isNomItmMod")){
                   return isNomItmMod(conExt, intCodEmp, intCodLoc, intCodCot);
                }
                if(funcion.equals("isCreCerRet")){
                   return isCreCerRet(conExt, intCodEmp, intCodLoc, intCodCot);
                }
                if(funcion.equals("isItmCanMaxVen")){
                   return isItmCanMaxVen(conExt, intCodEmp, intCodLoc, intCodCot);
                }
                if(funcion.equals("isCotDiaVal")){
                   return isCotDiaVal(conExt, intCodEmp, intCodLoc, intCodCot);
                }
                if(funcion.equals("isPreUniVtaValForPagAut")){
                   return isPreUniVtaValForPagAut(conExt, intCodEmp, intCodLoc, intCodCot);
                }
                if(funcion.equals("isVtaConMon")){
                   return isVtaConMon(conExt, intCodEmp, intCodLoc, intCodCot);
                }
                if(funcion.equals("isCreCli")){
                   return isCreCli(conExt, intCodEmp, intCodLoc, intCodCot);
                }
                if(funcion.equals("isDocVen")){
                   return isDocVen(conExt, intCodEmp, intCodLoc, intCodCot);
                }
                if(funcion.equals("isDocSinSop")){
                   return isDocSinSop(conExt, intCodEmp, intCodLoc, intCodCot);
                }
            }
           
 
        }

        catch (SecurityException e) 
        {
            blnRes=false;
            System.out.println(" "+  e);
        }
        catch (IllegalArgumentException e) 
        {
            blnRes=false;
             System.out.println(" "+  e);
        }
        catch (Exception Evt) {
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                blnRes=false;
            }
        return blnRes;
    }    
    
     public boolean isNomItmMod(java.sql.Connection conExt, int intCodEmp, int intCodLoc, int intCodCot) {
            boolean blnisNomItmMod = true;
            String strNomItmAct = "";
            String strNomItmOri = "";
             
            java.sql.ResultSet rstLoc;
            java.sql.Statement stmLoc;
            try {
                if(conExt!=null){
                    stmLoc = conExt.createStatement();
                    strSQL="";
                    strSQL+=" SELECT a1.tx_nomItm, a2.tx_nomItm as tx_nomItmOri \n ";
                    strSQL+=" FROM tbm_detCotVen as a1 \n";
                    strSQL+=" INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                    strSQL+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" \n";
                    System.out.println("isNomItmMod " + strSQL); 
                    rstLoc = stmLoc.executeQuery(strSQL);
                    while(rstLoc.next()){
                        strNomItmAct = rstLoc.getString("tx_nomItm");
                        strNomItmOri = rstLoc.getString("tx_nomItmOri");
                         if (!(strNomItmOri.equals(strNomItmAct))) {
                            blnisNomItmMod = false;
                            break;
                         }
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                }
            }
            catch (java.sql.SQLException e){
                blnisNomItmMod=false;
                objUtil.mostrarMsgErr_F1(jfrThis, e);
            }
            catch (Exception Evt) {
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                blnisNomItmMod=false;
            }
            return blnisNomItmMod;
        }
     
    /*
     *  control; controla si el cliente tiene cerrado el credito por retencion
     */
        public boolean isCreCerRet(java.sql.Connection conExt, int intCodEmp, int intCodLoc, int intCodCot) {
            boolean blnisCieCreRet = true;
            int intCodCli=0,intTipForPag2=0;
            java.sql.ResultSet rstLoc;
            try {
                if (conExt != null) {
                    java.sql.Statement stmMonCre = conExt.createStatement();
                    strSQL="";
                    strSQL+=" SELECT a1.co_cli, a1.co_forPag \n ";
                    strSQL+=" FROM tbm_cabCotVen as a1 \n";
                    strSQL+=" INNER JOIN tbm_cli as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli) \n";
                    strSQL+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" \n";
                    System.out.println("isCreCerRet " + strSQL); 
                    rstLoc = stmMonCre.executeQuery(strSQL);
                    if(rstLoc.next()){
                        intCodCli = rstLoc.getInt("co_cli");
                        intTipForPag2 = rstLoc.getInt("co_forPag");
                         String sSQL = "SELECT co_cli  FROM tbm_cli AS a1 WHERE a1.co_emp=" + intCodEmp + ""
                            + " AND a1.co_cli=" + intCodCli + " and st_cieretpen='S'";
                            java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                        if (rstMonCre.next()) {
                            blnisCieCreRet = false;
                        }
                        rstMonCre.close();
                        rstMonCre = null;
                        
                        if (intTipForPag2 == 4) {
                            blnisCieCreRet = true;
                        }
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmMonCre.close();
                    stmMonCre = null;
                }     
            } 
            catch (java.sql.SQLException e) {
                objUtil.mostrarMsgErr_F1(jfrThis, e);
                blnisCieCreRet = false;
            } 
            catch (Exception Evt) {
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                blnisCieCreRet = false;
            }
            return blnisCieCreRet;
        }
     
        
        /**
         * @author EFLORESA Funcion que determina si se excedio la cantidad
         * maxima del para la venta del item seleccionado. Terminales "P" false:
         * Excedio la cantidad maxima, se debe solicitar autorizacion true: No
         * hay problema.
         */
        public boolean isItmCanMaxVen(java.sql.Connection conExt, int intCodEmp, int intCodLoc, int intCodCot) {
            boolean blnRes = true;
            String strSql, strSer, strCodAlt;
            String result;
            String cant = "";
            String items[][] = null;
            java.sql.Statement stm = null, stm2 = null, stmLoc;
            java.sql.ResultSet rs = null, rs2 = null, rstLoc;
            double canmaxven = 0, dblCan=0.00;
            double cantven = 0;
            double subtot = 0;
            boolean isTerP = false;
            int intCodItm=0;
            try {
                if(conExt!=null){
                    stmLoc = conExt.createStatement();
                    
                    strSQL="";
                    strSQL+=" SELECT a1.co_itm,a1.tx_codAlt, a1.tx_nomItm,a1.nd_can, a2.st_ser \n ";
                    strSQL+=" FROM tbm_detCotVen as a1 \n";
                    strSQL+=" INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                    strSQL+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" \n";
                    System.out.println("ITEM: isItmCanMaxVen : ");
                    rstLoc = stmLoc.executeQuery(strSQL);
                    while(rstLoc.next()){
                        strSer = rstLoc.getString("st_ser");
                        intCodItm = rstLoc.getInt("co_itm");
                        strCodAlt = rstLoc.getString("tx_codAlt");
                        subtot = rstLoc.getDouble("nd_can");
                        if(strSer.equals("S")){
                            strSql = "select case when upper(trim(substr(" + objUtil.codificar(strCodAlt) + ", length(" + objUtil.codificar(strCodAlt) + ") ,1))) = 'P' then 'S' else 'N' end as TERMINAL_P";
                            System.out.println("ITEM: isItmCanMaxVen : ");
                            stm = conExt.createStatement();
                            rs = stm.executeQuery(strSql);
                            if (rs.next()) {
                                result = rs.getString("TERMINAL_P");
                                if (result.equals("S")) {
                                    isTerP = true;
                                    strSql = "select case when nd_canmaxven is null then 0 else round(nd_canmaxven) end as nd_canmaxven "
                                            + "from tbm_inv "
                                            + "where co_emp = " + intCodEmp + " "
                                            + "and co_itm = " + intCodItm;
                                    stm2 = conExt.createStatement();
                                    rs2 = stm2.executeQuery(strSql);
                                    if (rs2.next()) {
                                        canmaxven = rs2.getDouble("nd_canmaxven");
                                        cantven = objUtil.redondear(subtot, 0);
                                        if (isTerP) {
                                            if (cantven > canmaxven) {
                                                blnRes = false;
                                            } else {
                                                blnRes = true;
                                            }
                                        }
                                    }
                                    rs2.close();
                                    rs2=null;
                                    stm2.close();
                                    stm2=null;
                                }
                            }
                            rs.close();
                            rs=null;
                            stm.close();
                            stm=null;
                        }
                    } // FIN WHILE 
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                    
                } 
            } 
            catch (SQLException e) {
                blnRes = true;
                objUtil.mostrarMsgErr_F1(jfrThis, e);
            } 
            catch (Exception e) {
                blnRes = true;
                objUtil.mostrarMsgErr_F1(jfrThis, e);
            } 
            return blnRes;
    }

        
    
        public boolean isCotDiaVal(java.sql.Connection conExt, int intCodEmp, int intCodLoc, int intCodCot) {
            boolean blnRes = true;
            boolean blnValCon = true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try {
                if (blnValCon) {
                    if (conExt != null) {
                        stmLoc = conExt.createStatement();
                        strSql = "SELECT st_reg, current_date, fe_val ,case when  current_date > fe_val then 'S' else 'N' end as estVal "
                                + " FROM tbm_cabcotven WHERE co_emp=" + intCodEmp + " AND co_loc=" + intCodLoc + " "
                                + " AND co_cot= " + intCodCot;
                        rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) {
                            if (!rstLoc.getString("st_reg").equals("U")) {  // dias de validez de cotizacion, no volver a comprobar si la cotizacion esta autorizada.
                                if (rstLoc.getString("estVal").equals("S")) {
                                    blnRes = false;
                                }
                            }
                        }
                        rstLoc.close();
                        rstLoc = null;
                        stmLoc.close();
                        stmLoc = null;

                    }
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUtil.mostrarMsgErr_F1(jfrThis, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
            }
            return blnRes;
        }    
        private String strNomFuncValPre = "isPreUniVtaValForPagAut";
        private Librerias.ZafDate.ZafDatePicker objDatPickFecDoc;
        private String dateFecCot;                                                  //Variable que contiene la fecha de cotizacion.
        private int intCodTipDocFacEle=228;
        /*
         * FUNCION QUE PERMITE CONTROLAR EL PRECIO DEL ITEM
         * Y TAMBIEN LA FORMA DE PAGO
         *
         */
        public boolean isPreUniVtaValForPagAut(java.sql.Connection conExt, int intCodEmp, int intCodLoc, int intCodCot) {
            boolean blnRes = true;
            boolean blnValCon = true;
            int INT_TBL_COITM = 17;
            int INT_TBL_PREVEN = 11;
            boolean blnValPre = true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            double dblPreUsr, dblPreUniItm, dblDescDigitado, dblDescAsignado = 0.00,dblPorDes=0.00, dblPreCom=0.00,
                dblMinimo, dblValorVenta, dblCostoItm = 0, dblPorDesCom = 0, dblPorGanancia = 0.00, dblPorDesPreCom=0.00;
            int  intNumDiaVal=0;
            double dblMarUtiItm = 0;
            try {
                System.out.println("isPreUniVtaValForPagAut");
                if (blnValCon) {  // true
                    if(conExt!=null){
                        stmLoc = conExt.createStatement();
                        strSQL="";
                        strSQL+=" SELECT a1.fe_cot,a1.ne_val , a2.nd_maxdes, a2.nd_maruti \n";
                        strSQL+=" FROM tbm_cabCotVen as a1 \n";
                        strSQL+=" INNER JOIN tbm_cli as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli) \n";
                        strSQL+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" \n";    
                        System.out.println("isPreUniVtaValForPagAut " + strSQL);
                        rstLoc = stmLoc.executeQuery(strSQL);
                        if(rstLoc.next()){
                            dblDescAsignado = rstLoc.getDouble("nd_maxdes");
                            dblPorGanancia = rstLoc.getDouble("nd_maruti");
                            intNumDiaVal = rstLoc.getInt("ne_val");
                            dateFecCot = objUtil.formatearFecha(rstLoc.getDate("fe_cot"), "dd/MM/yyyy");
                        }
                        rstLoc.close();
                        rstLoc=null;
                      //**********************

                        
                         
                        //objDatPickFecDoc.setText(objUtil.formatearFecha(objUtil.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos()), "dd/MM/yyyy"));
                        
                        String FecAux = objUtil.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), "yyyy/MM/dd");
                        Librerias.ZafDate.ZafDatePicker objDate = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
                        Calendar objFec = Calendar.getInstance();
                        Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
                        int fecDoc[] = objDatPickFecDoc.getFecha(dateFecCot);
                        if (fecDoc != null) {
                            objFec.set(Calendar.DAY_OF_MONTH, fecDoc[0]);
                            objFec.set(Calendar.MONTH, fecDoc[1] - 1);
                            objFec.set(Calendar.YEAR, fecDoc[2]);
                        }
                        Calendar objFecPagActual = Calendar.getInstance();
                        objFecPagActual.setTime(objFec.getTime());
                        objFecPagActual.add(Calendar.DATE, intNumDiaVal);
                        dtePckPag.setAnio(objFecPagActual.get(Calendar.YEAR));
                        dtePckPag.setMes(objFecPagActual.get(Calendar.MONTH) + 1);
                        dtePckPag.setDia(objFecPagActual.get(Calendar.DAY_OF_MONTH));
                        String fecha = objUtil.formatearFecha(dtePckPag.getText(), "dd/MM/yyyy", "yyyy/MM/dd");

                        java.util.Date fe1 = objUtil.parseDate(fecha, "yyyy/MM/dd");// fecha de validez 
                        java.util.Date fe2 = objUtil.parseDate(FecAux, "yyyy/MM/dd");

                        if (fe1.equals(fe2)) {
                            blnValPre = false;
                        } else if (fe1.after(fe2)) {
                            blnValPre = false;
                        } else {
                            System.out.println(" "); //NO HAY DATOS DE FECHA...");
                        }


                        //**********************


                        boolean Estado = false; 
                        if (String.valueOf(intCodCot).trim().equals("")) {
                            Estado = false;
                            blnValPre = true;
                        } else {
                            blnRes = VerificaPermisodeImpresion(conExt, intCodEmp, intCodLoc, intCodCot, strNomFuncValPre);
                        }

                        blnRes = true;
                        int intCodItm;
                        String strCodAlt, strTer;
                        String strAux = ",CASE WHEN ("
                        + " (trim(SUBSTR (UPPER(a2.tx_codalt), length(a2.tx_codalt) ,1))  IN ("
                        + " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp=" + intCodEmp + " AND co_loc=" + intCodLoc + " "
                        + " AND co_tipdoc=" + intCodTipDocFacEle + " AND co_usr=" + objZafParSis.getCodigoUsuario() + " AND st_reg='A' "
                        + " ))) THEN 'S' ELSE 'N' END  as isterL";
                        
                        strSQL="";
                        strSQL+=" SELECT a1.co_itm,a1.tx_codAlt, a1.tx_nomItm,a1.nd_can, a2.st_ser,a1.nd_porDes, a1.nd_preUni, \n ";
                        strSQL+="        a2.nd_marUti,a1.nd_precom,a1.nd_pordesprecom  ";
                        strSQL+= strAux;
                        strSQL+=" FROM tbm_detCotVen as a1 \n";
                        strSQL+=" INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                        strSQL+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" \n";
                        
                        System.out.println("isPreUniVtaValForPagAut 02 + " + strSQL);
                        rstLoc = stmLoc.executeQuery(strSQL);
                        while(rstLoc.next()){
                             intCodItm = rstLoc.getInt("co_itm");
                             dblPreUsr = rstLoc.getDouble("nd_preUni");
                             dblMarUtiItm = rstLoc.getDouble("nd_marUti");
                             strCodAlt = rstLoc.getString("tx_codAlt");
                             dblPorDes = rstLoc.getDouble("nd_porDes");
                             dblPreCom = rstLoc.getDouble("nd_preCom");
                             dblPorDesPreCom = rstLoc.getDouble("nd_pordesprecom");
                             strTer = (rstLoc.getString("isterl") == null ? "" : rstLoc.getString("isterl"));
                            /*PRECIO QUE PONE EL VENDEDOR*/
                            dblPreUsr = objUtil.redondear(dblPreUsr, 6);
                            dblMarUtiItm = objUtil.redondear(dblMarUtiItm, 2);

                            int intTipPre = 0;
                            
                            
                            //**********************
                            if (strTer != null) {
                                if (strTer.trim().equalsIgnoreCase("S")) {
                                    dblPreUniItm = 0;
                                } else {
                                    dblPreUniItm = getPreUni(conExt, intCodEmp,intCodItm );
                                    intTipPre = 1;
                                }
                            } else {
                                dblPreUniItm = getPreUni(conExt, intCodEmp,intCodItm);
                                intTipPre = 1;
                            }

                            //**********************

                            dblDescDigitado = objUtil.redondear(dblPorDes, 2);
                            dblValorVenta = (dblDescDigitado != 0) ? (dblPreUsr - (dblPreUsr * dblDescDigitado / 100)) : dblPreUsr;
    //REVISA AKI JOTA
                            if (dblPreUniItm != 0) {
                                dblMinimo = (dblDescAsignado != 0) ? (dblPreUniItm - (dblPreUniItm * dblDescAsignado / 100)) : dblPreUniItm;

                            } else {

                                double dblStkAct = 0;
                                //*************************
                                if (strTer != null) {
                                    if (strTer.trim().equalsIgnoreCase("S")) {
                                        dblStkAct = 0;
                                    } else {
                                        dblStkAct = getStkAct(conExt, intCodEmp,intCodLoc,intCodItm );
                                    }
                                } else {
                                    dblStkAct = getStkAct(conExt, intCodEmp,intCodLoc,intCodItm);
                                }
                                //**************************

                                if (!(strTer.equalsIgnoreCase("S"))) //if(dblStkAct != 0)
                                {
                                    dblCostoItm = getCosUni(conExt, intCodEmp,intCodLoc,intCodItm);
                                } else {
                                    dblCostoItm = dblPreCom;
                                    dblPorDesCom = dblPorDesPreCom;
                                    dblCostoItm = objUtil.redondear((dblCostoItm - (dblCostoItm * (dblPorDesCom / 100))), objZafParSis.getDecimalesBaseDatos());
                                }
                                dblMinimo = dblCostoItm * dblPorGanancia;
                                dblMinimo = dblCostoItm + ((dblMinimo == 0) ? 0 : (dblMinimo / 100));
                            }
                            dblValorVenta = objUtil.redondear(dblValorVenta, 3);
                            dblMinimo = objUtil.redondear(dblMinimo, 3);


                            double dblPor = (1 - (dblCostoItm / dblValorVenta)) * 100;
                            dblPor = objUtil.redondear(dblPor, 2);

                            /*JoséMario  11/Agos/2015 */
                            double dblMas;
                            dblMas=dblPreUniItm*0.05; /* 16/Sep/2015:.. JoséMario  */
                            dblMas=dblPreUniItm+dblMas; 
                            double dblMenos;
                            dblMenos=dblPreUniItm*0.05; /* 16/Sep/2015:.. JoséMario  */
                            dblMenos=dblPreUniItm-dblMenos; 
                            if (intTipPre == 1) {
                                if (dblPreUniItm > 0.00) {  // PRECIO DEL ITEM 
                                    if (dblValorVenta < dblMinimo) {  //  PRECIO DEL ITEM PUESTO POR USUARIO - DESCUENTO < VALOR MINIMO DE VENTA CON DESCUENTO
                                        blnRes = false;
                                    }
                                    if (dblPreUsr > dblMas) {  // PRECIO NO MAYOR DEL 5% 
                                        blnRes = false;
                                    }
                                    if (dblPreUsr < dblMenos) {  // PRECIO NO MENOR DEL 5% 
                                        blnRes = false;
                                    }
                                } else {
                                    blnRes = false;
                                }
                            } else {

                                if (dblPor < dblMarUtiItm) {
                                    blnRes = false;
                                     
                                }
                            }

                        }
                        rstLoc.close();
                        rstLoc=null;
                        stmLoc.close();
                        stmLoc=null;
                        
                        if (blnRes) {
                            blnRes = isUsrForPag(conExt, intCodEmp,intCodLoc,intCodCot);
                        }
                    }
                    
                    
                }
            } catch (Exception Evt) {
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
            }

            return blnRes;
        }
        
          /**
         * CONTROL: para las ventas de contado.
         */
        public boolean isVtaConMon(java.sql.Connection conExt,int intCodEmp,int intCodLoc,int intCodCot) {
            boolean blnisDocVen = true;
            
            java.sql.ResultSet rstLoc;
            double dlbSumMon = 0, dblDiaGraCli=0;
            int intTipForPag2=0;
            try {
                blnisDocVen = true;
                if (conExt != null) {
                    java.sql.Statement stmMonCre = conExt.createStatement();
                    
                    strSQL="";
                    strSQL+=" SELECT SUM(a2.mo_pag) as mo_pag, a3.ne_diagra, a1.co_forPag \n";
                    strSQL+=" FROM tbm_cabCotVen as a1 \n";
                    strSQL+=" INNER JOIN tbm_pagCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                    strSQL+=" INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
                    strSQL+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" AND a2.ne_diaCre=0 and a2.nd_porRet=0  ";
                    strSQL+=" GROUP BY a3.ne_diagra, a1.co_forPag ";
                    System.out.println("isVtaConMon 01 " + strSQL);
                    rstLoc = stmMonCre.executeQuery(strSQL);
                    if(rstLoc.next()){
                        dlbSumMon = rstLoc.getDouble("mo_pag");
                        dblDiaGraCli = rstLoc.getDouble("ne_diagra");
                        intTipForPag2 = rstLoc.getInt("co_forPag");
                    }
                    rstLoc.close();
                    rstLoc = null;

                    strSQL ="SELECT numVenCon, abs(valor) ,  ne_nummaxvencon, nd_monmaxvencon  ";
                    strSQL+="  ,CASE WHEN  numVenCon>=ne_nummaxvencon THEN 1 else 0  END  AS NumVenConCli  ";
                    strSQL+="  ,CASE WHEN (abs(valor)+" + dlbSumMon + ")>=nd_monmaxvencon THEN 1 else 0  END AS MonCanVen ";;
                    strSQL+=",( ";
                    strSQL+=" SELECT count(distinct(a1.co_doc)) as numVenCon FROM tbm_cabMovInv AS a1 ";;
                    strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   ";
                    strSQL+=" WHERE a1.co_emp=" +intCodEmp+ " AND a1.co_loc=" +intCodLoc+ " AND a1.co_tipdoc= " + intCodTipDocFacEle;
                    strSQL+=" AND a1.co_cli=";
                    strSQL+=" ( SELECT a1.co_cli FROM tbm_cabCotVen as a1 WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" )";
                    strSQL+=" AND a1.st_reg NOT IN ('I','E') AND a1.ST_IMP IN ('S') AND a2.st_reg IN ('A','C')  ";
                    strSQL+=" AND ( a1.st_excDocConVenCon='N' OR a1.st_excDocConVenCon is null ) ";;
                    strSQL+=" AND (a2.nd_porret=0 or a2.nd_porret is null ) and (a2.ne_diacre=0 or a2.ne_diacre is null)      AND (a2.nd_abo+a2.mo_pag) < 0 ";
                    strSQL+=" ) as maxnumvenconCli ";;
                    strSQL+=", ";;
                    strSQL+="(  select ne_nummaxvencon from tbm_cli where co_emp="+intCodEmp+" and co_cli=";
                    strSQL+="( SELECT a1.co_cli FROM tbm_cabCotVen as a1 WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" )"; 
                    strSQL+="  ) as nummaxvenCondecli";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT * FROM (";
                    strSQL+=" 	SELECT count(distinct(a1.co_doc)) as numVenCon FROM tbm_cabforpag as a ";
                    strSQL+="   inner join tbm_cabMovInv AS a1 on (a1.co_emp=a.co_emp and a1.co_forpag=a.co_forpag)  ";
                    strSQL+="	INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                    strSQL+=" inner join tbm_cli as cli on (cli.co_emp=a1.co_Emp and cli.co_cli=a1.co_cli and ( cli.ne_diagra<=0  or cli.ne_diagra = null )) ";
                    strSQL+="	WHERE a1.co_emp=" +intCodEmp+ " AND a1.co_loc=" +intCodLoc+ " AND a1.co_tipdoc=" + intCodTipDocFacEle;
                    strSQL+="   AND a1.st_reg NOT IN ('I','E') AND a1.ST_IMP IN ('S')  AND a2.st_reg IN ('A','C') ";
                    strSQL+=" AND ( a1.st_excDocConVenCon='N' OR a1.st_excDocConVenCon is null ) ";
                    strSQL+="	AND (a2.nd_porret=0 or a2.nd_porret is null ) and (a2.ne_diacre=0 or a2.ne_diacre is null)";
                    strSQL+="	AND (a2.nd_abo+a2.mo_pag) < 0  AND a.ne_tipforpag=1 ";
                    strSQL+="  AND (a2.fe_venChq IS NULL OR a2.nd_monChq IS NULL OR current_date < a2.fe_venChq OR (a2.mo_pag+a2.nd_monChq)<0) ";
                    strSQL+=" ) AS x,(";;
                    strSQL+="	SELECT sum( -(a2.mo_pag+a2.nd_abo+(CASE WHEN a2.nd_monChq IS NULL THEN 0 ELSE (CASE WHEN current_date < a2.fe_venChq THEN 0 ELSE a2.nd_monChq END) END)) ) AS  valor  ";
                    strSQL+="   FROM tbm_cabforpag as a ";
                    strSQL+="   inner join tbm_cabMovInv AS a1 on (a1.co_emp=a.co_emp and a1.co_forpag=a.co_forpag)  ";
                    strSQL+="	INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                    strSQL+=" inner join tbm_cli as cli on (cli.co_emp=a1.co_Emp and cli.co_cli=a1.co_cli and ( cli.ne_diagra<=0  or cli.ne_diagra = null )) ";
                    strSQL+="	WHERE a1.co_emp=" +intCodEmp+ " AND a1.co_loc=" +intCodLoc+ " AND a1.co_tipdoc=" + intCodTipDocFacEle;
                    strSQL+="   AND a1.st_reg NOT IN ('I','E') AND a1.ST_IMP IN ('S')  AND a2.st_reg IN ('A','C') ";
                    strSQL+=" AND ( a1.st_excDocConVenCon='N' OR a1.st_excDocConVenCon is null ) ";
                    strSQL+="	AND (a2.nd_porret=0 or a2.nd_porret is null ) and (a2.ne_diacre=0 or a2.ne_diacre is null)";
                    strSQL+="	AND (a2.nd_abo+a2.mo_pag) < 0  AND  a.ne_tipforpag=1   ";
                    strSQL+="   AND (a2.fe_venChq IS NULL OR a2.nd_monChq IS NULL OR current_date <a2.fe_venChq OR (a2.mo_pag+a2.nd_monChq)<0)  ";
                    strSQL+=") AS y";
                    strSQL+=",(";
                    strSQL+="    SELECT  ne_nummaxvencon, nd_monmaxvencon FROM tbm_loc where co_emp=" +intCodEmp+ " and co_loc=" +intCodLoc+ " ";
                    strSQL+=" ) AS z ";
                    strSQL+=") AS x";
                    System.out.println("isVtaConMon 0.2: " + strSQL);
                    if (dblDiaGraCli == 0 && intTipForPag2 == 1) {
                        java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(strSQL);
                        while (rstMonCre.next()) {
                            if (rstMonCre.getInt("NumVenConCli") == 1) {
                                blnisDocVen = false;
                                break;
                            } else if (rstMonCre.getInt("MonCanVen") == 1) {
                                blnisDocVen = false;
                                break;
                            } else if (rstMonCre.getInt("maxnumvenconCli") > rstMonCre.getInt("nummaxvenCondecli")) {
                                blnisDocVen = false;
                                break;
                            }
                        }
                        rstMonCre.close();
                        rstMonCre = null;
                    }
                    stmMonCre.close();
                    stmMonCre = null;
                }

                if (intTipForPag2 == 4) {
                    blnisDocVen = true;
                }

            } catch (SQLException Evt) {
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                blnisDocVen = false;
            } catch (Exception Evt) {
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                blnisDocVen = false;
            }

            return blnisDocVen;
        }

        
        /**
         * CONTROL: Que el cliente no sobrepase su cupo de credito establecido
         */
        public boolean isCreCli(java.sql.Connection conExt,int intCodEmp, int intCodLoc, int intCodCot) {
            boolean blnRes = true;
            boolean blnValCon = true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            try{
                if (blnValCon) {
                    if(conExt!=null){
                        stmLoc = conExt.createStatement();
                        double dblDiaGraCli=0,dblTotalCot=0;
                        int intTipForPag2=0, intCodCli=0;

                        strSQL="";
                        strSQL+=" SELECT a1.nd_tot,  a3.ne_diagra, a1.co_forPag, a1.co_cli \n";
                        strSQL+=" FROM tbm_cabCotVen as a1 \n";
                        strSQL+=" INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
                        strSQL+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" ";
                        System.out.println("isVtaConMon 01 " + strSQL);
                        rstLoc = stmLoc.executeQuery(strSQL);
                        if(rstLoc.next()){
                            dblTotalCot = rstLoc.getDouble("nd_tot");
                            dblDiaGraCli = rstLoc.getDouble("ne_diagra");
                            intTipForPag2 = rstLoc.getInt("co_forPag");
                            intCodCli = rstLoc.getInt("co_cli");
                        }
                        rstLoc.close();
                        rstLoc = null;
                        stmLoc.close();
                        stmLoc = null;

                        if (!(dblDiaGraCli == 0 && intTipForPag2 == 1)) {
                            //Obteniendo el cupo de credito para este cliente
                            double dblMonCre = getMonCre(conExt, intCodEmp, intCodCli);
                            //Obteniendo el las cuentas por cobrar a este cliente.
                            double dblCxC = getCxC(conExt, intCodEmp, intCodCli);

                             System.out.println("(dblTotalCot) ---> "+dblTotalCot );
                            // System.out.println("(dblCxC) ---> "+dblCxC );

                            // System.out.println("(dblTotalCot+dblCxC) ---> "+(dblTotalCot+dblCxC));
                            System.out.println("dblMonCre---> " + dblMonCre);

                            if (Math.abs(dblTotalCot + dblCxC) > dblMonCre) {
                                blnRes = false;
                            }
                        }
                        if (intTipForPag2 == 4) {
                            blnRes = true;
                        }
                    }

                }
            } 
            catch (SQLException Evt) {
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                blnRes = true;
            } catch (Exception Evt) {
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                blnRes = true;
            }

           
            return blnRes;
        }
        
        
        /**
         * CONTROL: Que el client no tenga documentos vencidos.
         */
        public boolean isDocVen(java.sql.Connection conExt,int intCodEmp, int intCodLoc, int intCodCot) {
            boolean blnisDocVen = true;
            boolean blnValCon = true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            int intTipForPag2=0, intCodCli=0;
            double dblDiaGraCli=0;
            try {
                if (blnValCon) {
                    blnisDocVen = true;
                    if (conExt != null) {
                        java.sql.Statement stmMonCre = conExt.createStatement();
                        stmLoc = conExt.createStatement();
                        strSQL="";
                        strSQL+=" SELECT a1.nd_tot,  a3.ne_diagra, a1.co_forPag, a1.co_cli \n";
                        strSQL+=" FROM tbm_cabCotVen as a1 \n";
                        strSQL+=" INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
                        strSQL+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" ";
                        System.out.println("isVtaConMon 01 " + strSQL);
                        rstLoc = stmLoc.executeQuery(strSQL);
                        if(rstLoc.next()){
                            dblDiaGraCli = rstLoc.getDouble("ne_diagra");
                            intTipForPag2 = rstLoc.getInt("co_forPag");
                            intCodCli = rstLoc.getInt("co_cli");
                        }
                        rstLoc.close();
                        rstLoc = null;
                        stmLoc.close();
                        stmLoc = null;

                        String sSQL = "SELECT a2.nd_abo  FROM tbm_cabMovInv AS a1 "
                                + " INNER join tbm_cli as cli ON (cli.co_emp=a1.co_Emp and cli.co_cli=a1.co_cli) "
                                + " INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc "
                                + " AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) "
                                + " INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND "
                                + " a1.co_tipDoc=a3.co_tipDoc) "
                                + " WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND "
                                + " a1.co_cli=" + intCodCli + " AND "
                                + " a1.st_reg IN ('A','R','C','F') AND a3.ne_mod In (1,3) AND a2.st_reg IN ('A','C') AND "
                                + " (a2.mo_pag+a2.nd_abo)<0  " + // and ( a2.nd_porret = 0 or a2.nd_porret is null )   " +
                                " AND a2.fe_ven+cli.ne_diagra<=" + objZafParSis.getFuncionFechaHoraBaseDatos() + " "
                                + " ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";

                        if (!(dblDiaGraCli == 0 && intTipForPag2 == 1)) {
                            java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                            if (rstMonCre.next()) {
                                blnisDocVen = false;
                            }
                            rstMonCre.close();
                            rstMonCre = null;
                        }
                        stmMonCre.close();
                        stmMonCre = null;
                    }
                }
                if (intTipForPag2 == 4) {
                    blnisDocVen = true;
                }

            } catch (SQLException Evt) {
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                blnisDocVen = false;
            } catch (Exception Evt) {
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                blnisDocVen = false;
            }
            return blnisDocVen;
        }
        
        
        /**
         * CONTROL: De Soporte de cheque.
         */
        public boolean isDocSinSop(java.sql.Connection conExt,int intCodEmp, int intCodLoc, int intCodCot) {
            boolean blnisDocRet = true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            int intTipForPag2=0, intCodCli=0;
            double dblDiaGraCli=0;
            try {
                blnisDocRet = true;
                if (conExt != null) {
                    java.sql.Statement stmMonCre = conExt.createStatement();
                    
                    stmLoc = conExt.createStatement();
                    strSQL="";
                    strSQL+=" SELECT a1.nd_tot,  a3.ne_diagra, a1.co_forPag, a1.co_cli \n";
                    strSQL+=" FROM tbm_cabCotVen as a1 \n";
                    strSQL+=" INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
                    strSQL+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" ";
                    System.out.println("isDocSinSop 01 " + strSQL);
                    rstLoc = stmLoc.executeQuery(strSQL);
                    if(rstLoc.next()){
                        dblDiaGraCli = rstLoc.getDouble("ne_diagra");
                        intTipForPag2 = rstLoc.getInt("co_forPag");
                        intCodCli = rstLoc.getInt("co_cli");
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                    
                    String sSQL = "SELECT a1.co_tipdoc, a2.nd_abo, a2.st_sop, a2.st_entsop  FROM tbm_cabMovInv AS a1"
                            + " INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) "
                            + " INNER JOIN tbm_cli AS cli ON (cli.co_emp=a1.co_emp and cli.co_cli=a1.co_cli) "
                            + " WHERE a1.co_emp=" +intCodEmp + " " +  
                            "  AND (a2.mo_pag+a2.nd_abo)<>0   AND a1.co_cli=" +intCodCli + " AND a1.st_reg NOT IN ('I','E') AND a2.st_reg IN ('A','C') "
                            + " AND a2.st_sop='S'   "
                            + " AND CASE WHEN (a2.mo_pag+a2.nd_abo) < 0 THEN  a2.st_entsop='N' END "
                            + " AND a1.fe_doc+cli.ne_diagrachqfec <= " + objZafParSis.getFuncionFechaHoraBaseDatos() + " ";
                     System.out.println("isDocSinSop 2 "+sSQL); 
                    java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                    if (rstMonCre.next()) {
                        blnisDocRet = false;
                    }
                    rstMonCre.close();
                    rstMonCre = null;
                    stmMonCre.close();
                    stmMonCre = null;
                }

                if (intTipForPag2 == 4) {
                    blnisDocRet = true;
                }

            } catch (SQLException Evt) {
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                blnisDocRet = false;
            } catch (Exception Evt) {
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                blnisDocRet = false;
            }

            return blnisDocRet;
        }

        
        /**
         * Metdo que devuelve el total de las cuentas por cobrar de un cliente
         */
        private double getCxC(java.sql.Connection conExt,int intCodEmp,int intCodCli) {
            double dblCxC = 0;
            try {
                System.out.println("getCxC");
                if (conExt != null) {
                    java.sql.Statement stmMonCre = conExt.createStatement();
                    String sSQL=""; 
                    sSQL+=" SELECT SUM(a.nd_deuda) as nd_deuda   \n";
                    sSQL+=" FROM(   \n";
                    sSQL+="     SELECT  sum((abs(pag.mo_pag) - abs(pag.nd_abo))) as nd_deuda \n";
                    sSQL+="     FROM tbm_pagmovinv AS pag \n";
                    sSQL+="     INNER JOIN tbm_cabmovinv AS cab ON (cab.co_emp=pag.co_emp and cab.co_loc=pag.co_loc and  \n";
                    sSQL+="                                         cab.co_tipdoc=pag.co_tipdoc and cab.co_doc=pag.co_doc )  \n";
                    sSQL+="     INNER JOIN tbm_cabtipdoc AS tipdoc ON ( tipdoc.co_emp=cab.co_emp and tipdoc.co_loc=cab.co_loc and \n";
                    sSQL+="                                             tipdoc.co_tipdoc=cab.co_tipdoc )   \n";
                    sSQL+="     WHERE  pag.co_emp = "+intCodEmp+" AND  tipdoc.ne_mod in (1,3) AND  \n";
                    sSQL+="          (pag.nd_porret = 0 or pag.nd_porret IS NULL ) AND \n";
                    sSQL+="          (abs(pag.mo_pag) - abs(pag.nd_abo)) > 0  AND  cab.st_reg in ('C','A','R','F') AND  \n";
                    sSQL+="             pag.st_reg in ('C','A') AND  cab.co_cli = "+intCodCli+" \n";
                    sSQL+=" UNION   \n";
                    sSQL+="     SELECT  CASE WHEN SUM(a2.nd_tot) IS NULL THEN 0 ELSE SUM(a2.nd_tot) END as nd_deuda   \n";
                    sSQL+="     FROM tbm_cabSegMovInv as a1 \n";
                    sSQL+="     INNER JOIN tbm_cabCotVen as a2 ON (a1.co_empRelCabCotVen=a2.co_emp AND a1.co_locRelCabCotVen=a2.co_loc AND \n";
                    sSQL+="                                         a1.co_cotRelCabCotVen=a2.co_cot) \n";
                    sSQL+="     INNER JOIN tbm_pagCotVen as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_cot=a3.co_cot) \n";
                    sSQL+="     WHERE a3.nd_porRet=0 and a2.co_cli="+intCodCli+" and a2.tx_momGenFac='F' AND  a2.st_reg IN ('E','L') AND \n";
                    sSQL+="           a2.co_emp="+intCodEmp+" \n";
                    sSQL+=" ) as a \n";/* JoseMario 8/Jul/2016 */
		    System.out.println("Monto de credito usado por un cliente: " + sSQL);
                    java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                    if (rstMonCre.next()) {
                        dblCxC = rstMonCre.getDouble("nd_deuda");
                    }
                    rstMonCre.close();
                    stmMonCre.close();
                    rstMonCre = null;
                    stmMonCre = null;
                }
            } catch (java.sql.SQLException Evt) {
                return dblCxC;
            } catch (Exception Evt) {
                return dblCxC;
            }
            return dblCxC;
        }

        
        
        /**
         * Metdo que devuelve el cupo de credito de un cliente
         */
        private double getMonCre(java.sql.Connection conExt,int intCodEmp,int intCodCli) {
            double dblMonCre = 0;
            try {
                if (conExt != null) {
                    java.sql.Statement stmMonCre = conExt.createStatement();
                    String sSQL = "SELECT nd_monCre from tbm_cli as cli "
                            + " where cli.co_emp = " + intCodEmp + " and "
                            + "       cli.co_cli = " + intCodCli;
                    java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                    if (rstMonCre.next()) {
                        dblMonCre = rstMonCre.getDouble("nd_monCre");
                    }
                    rstMonCre.close();
                    stmMonCre.close();
                    rstMonCre = null;
                    stmMonCre = null;

                }
            } catch (java.sql.SQLException Evt) {
                return dblMonCre;
            } catch (Exception Evt) {
                return dblMonCre;
            }
            return dblMonCre;
        }
        
        /*
         *control; controla si el cliente tiene la forma de pago asignado que se ha selecciono
         */
        public boolean isUsrForPag(java.sql.Connection conExt, int intCodEmp, int intCodLoc, int intCodCot) {   // ESQUEMA NUEVO DE VALIDACION DE FORMA DE PAGO
            boolean blnisCieCreRet = false;
            try {
                if (conExt != null) { // JoséMario 22/Marzo/2016 
                    java.sql.Statement stmMonCre = conExt.createStatement();
                    strSQL="";
                    strSQL+=" SELECT b.co_forpag, b.tx_des  ";
                    strSQL+=" FROM tbm_forpagcli as a  ";
                    strSQL+=" INNER JOIN tbm_cabForPag as b on (b.co_emp=a.co_emp and b.co_forpag=a.co_forpag) ";
                    strSQL+=" INNER JOIN tbm_cabCotVen as a1 ON (b.co_emp=a1.co_emp AND b.co_forPag=a1.co_forPag)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + " and a1.co_loc=" +intCodLoc+" AND a1.co_cot="+intCodCot;
                    System.out.println("isUsrForPag " + strSQL);
                    java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(strSQL);
                    if (rstMonCre.next()) {
                        blnisCieCreRet = true;
                    }
                    rstMonCre.close();
                    rstMonCre = null;
                    stmMonCre.close();
                    stmMonCre = null;
                }
            } catch (java.sql.SQLException e) {
                objUtil.mostrarMsgErr_F1(jfrThis, e);
            } catch (Exception Evt) {
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
            }
            return blnisCieCreRet;
        }
        
        
        private double getCosUni(java.sql.Connection conExt, int intCodEmp, int intCodLoc, int intCodItm) {
            double dblPreUni = 0;
            try {
                 if (conExt != null) {
                    java.sql.Statement stmUni = conExt.createStatement();
                    String sSQL = "  SELECT a1.nd_cosUni FROM tbm_inv AS a1"
                            + " INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)"
                            + " WHERE a2.co_emp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a2.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" +intCodEmp + " AND co_itm=" + intCodItm + ")";
                    java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                    if (rstUni.next()) {
                        dblPreUni = rstUni.getDouble("nd_cosuni");
                    }
                    rstUni.close();
                    stmUni.close();
                   
                    rstUni = null;
                    stmUni = null;
                    
                }
            } catch (java.sql.SQLException Evt) {
                return dblPreUni;
            } catch (Exception Evt) {
                return dblPreUni;
            }
            return dblPreUni;
        }
        
    
    private double getStkAct(java.sql.Connection conExt, int intCodEmp, int intCodLoc, int intCodItm) {
        double dblStkAct = 0;
        try {
            if (conExt != null) {
                java.sql.Statement stmUni = conExt.createStatement();
                String sSQL = "SELECT SUM(a2.nd_canDis) AS nd_canDis"
                        + " FROM tbm_equInv AS a1 INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)"
                        + " INNER JOIN tbr_bodEmp AS a3 ON (a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer)"
                        + " WHERE a3.co_emp=" + intCodEmp + " AND a3.co_loc=" + intCodLoc + " AND a1.co_itmMae=(SELECT co_itmMae FROM tbm_equInv"
                        + " WHERE co_emp=" + intCodEmp + " AND co_itm=" + intCodItm + ")";
                java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                if (rstUni.next()) {
                    dblStkAct = rstUni.getDouble("nd_canDis");
                }
                rstUni.close();
                stmUni.close();
                rstUni = null;
                stmUni = null;
            }
        } catch (java.sql.SQLException Evt) {
            objUtil.mostrarMsgErr_F1(jfrThis, Evt);
            return dblStkAct;
        } catch (Exception Evt) {
            objUtil.mostrarMsgErr_F1(jfrThis, Evt);
            return dblStkAct;
        }
        return dblStkAct;
    }    
        
        
        
    private double getPreUni(java.sql.Connection conExt, int intCodEmp, int intCodItm) {
        double dblPreUni = 0;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try {
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSQL="";
                strSQL+=" SELECT CASE WHEN nd_preVta1 IS NULL THEN 0 ELSE nd_preVta1 END AS nd_preUni ";
                strSQL+=" FROM tbm_inv where co_emp="+intCodEmp+" AND co_itm="+intCodItm;
                System.out.println("ZafAut.getPreUni: " + strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    dblPreUni = rstLoc.getDouble("nd_preUni");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                dblPreUni = objUtil.redondear(dblPreUni, 4);
            }
        }
        catch (Exception Evt) {
            dblPreUni = 0;
            objUtil.mostrarMsgErr_F1(jfrThis, Evt);
        }
        return dblPreUni;
    }
        
        public boolean VerificaPermisodeImpresion(java.sql.Connection conExt, int intCodEmp, int intCodLoc, int intCodCot, String funcion) {
            boolean lbnEst = false;
            int IntCodEmp = intCodEmp;
            int IntCodLoc = intCodLoc;
            String StrCodCot = String.valueOf(intCodCot);
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
           
            String strSql = "SELECT b.st_reg,b.st_cum FROM tbm_cabautcotven as a";
                   strSql+=" INNER JOIN tbm_detautcotven AS b ON (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_cot=b.co_cot and a.co_aut=b.co_aut)";
                   strSql+=" INNER JOIN tbm_regneg as c ON(b.co_emp=c.co_emp and b.co_loc=c.co_loc and b.co_regneg=c.co_reg and c.co_mnu=" + intCodMnuCotVen + " and c.tx_nomfun='" + funcion + "')";
                   strSql+=" WHERE a.co_cot=" + StrCodCot + " and a.co_emp=" + IntCodEmp ;
                   strSql+="        and a.co_loc=" + IntCodLoc + "";
                   strSql+=" AND b.co_aut=( SELECT max(co_aut) ";
                   strSql+="                FROM  tbm_cabautcotven WHERE co_emp=" + IntCodEmp ;
                   strSql+="" +                 " AND co_loc=" + IntCodLoc + " AND  co_cot=" + StrCodCot + " )";
            System.out.println("VerificaPermisodeImpresion verificacionDePermisos: " + strSql);
            try {
                if (conExt != null) {
                    stmLoc = conExt.createStatement();
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        if (rstLoc.getString("st_reg") != null) {//
                            if (rstLoc.getString("st_cum").equals("S")) {
                                lbnEst = true;
                            } else if (rstLoc.getString("st_reg").equals("A")) {
                                lbnEst = true;
                            } else {
                                lbnEst = false;  //  TIENE PROBLEMA DE PRECIO Y NO ESTA AUTORIZADA
                            }
                        }
                    } else {
                        lbnEst = true;
                    }
                     
                    rstLoc.close();
                    stmLoc.close();
                     
                    rstLoc=null;
                    stmLoc=null;
                }
            } catch (SQLException Evt) {
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                return lbnEst;
            }
            catch (Exception Evt) {
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                return lbnEst;
            }
            return lbnEst;
        }
        
         
        
        
/* JoseMario: Modificacion para reservas */
    
    
}
