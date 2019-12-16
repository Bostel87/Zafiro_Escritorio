/*
 * ZafInv.java     
 *
 * Created on 2 de diciembre de 2004, 14:34
 */
package Librerias.ZafInventario;
  
/**
 *
 *   @author: jsalazar 02/Dic/2005
 *   ultima actualizacion 06/Ene/2006
 *   v0.1 : se actualizo metodo movInventario para actualizacion de inv. consolidado
 *   v0.2 : se actualizo metodo movInventario para calculo de costeo
 *
 *ver 1.1
 *
 */
public class ZafInventario {
   // java.sql.Connection conRec;  //Coneccion a la base
    java.sql.Statement stmRec;   //Statement para el recosteo
    java.sql.ResultSet rstRec;   //Resultset que tendra los datos del recosteo
    java.sql.ResultSet rstExi;   //Resultset que tendra los datos de la existencia y el valor de la Existencia
    java.sql.Connection conExi;
    String sSQL;
    Librerias.ZafUtil.ZafUtil objUti;
    javax.swing.JInternalFrame jfract;
    javax.swing.JDialog jfract2;
    Librerias.ZafParSis.ZafParSis objZafParSis;
    Librerias.ZafSisCon.ZafSisCon  objSisCon;
    private final String VERSION = " v0.3 beta";
    
    
    /** Creates a new instance of ZafInv */
    public ZafInventario(javax.swing.JInternalFrame jfrthis, Librerias.ZafParSis.ZafParSis obj){

        try{  
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jfract = jfrthis;
            objUti = new Librerias.ZafUtil.ZafUtil();
            objSisCon = new Librerias.ZafSisCon.ZafSisCon(objZafParSis);
        }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(jfrthis, e);
        }        
        
    }

    
    
      public ZafInventario(javax.swing.JDialog jfrthis, Librerias.ZafParSis.ZafParSis obj){

        try{  
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jfract2 = jfrthis;
            objUti = new Librerias.ZafUtil.ZafUtil();
            objSisCon = new Librerias.ZafSisCon.ZafSisCon(objZafParSis);
        }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(jfrthis, e);
        }        
        
    }
    

    public void recosteo(int intCodTipDoc, int intCodDoc, int intCodArt, java.sql.Connection conRec){
                     
    }
    
    public void movInventario(int intCodArt, int intCodBod, int intAcc, double dblArtCan, java.sql.Connection conMovInv, String strEstFisBod, String strMerIngEgr, String strTipIngEgr ){
        try{//odbc,usuario,password
          
            if (dblArtCan<0)
                dblArtCan=dblArtCan*(-1);
                
            dblArtCan = dblArtCan * intAcc;
            
           //**** if(!isItemBodega(intCodArt,intCodBod,conMovInv))
           //*****     creaItemBodega(intCodArt,intCodBod, conMovInv);
             
            
              String strAux="";
                if(strEstFisBod.equals("N")){
                 if(strMerIngEgr.equals("S")){
                   if(strTipIngEgr.equals("I")){ 
                       if(dblArtCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+dblArtCan+") ";
                   }
                   if(strTipIngEgr.equals("E")){
                       if(dblArtCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+dblArtCan+") ";
                   } 
                 }} 
           
           String sql ="Update tbm_invBod set " +
               " nd_stkAct  = nd_stkAct + " + dblArtCan +
               " "+strAux+" "+
               " where " +
               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
               " co_bod = " + intCodBod + " and "+  
               " co_itm = " + intCodArt;  
            java.sql.PreparedStatement pstMovInvBod = conMovInv.prepareStatement(sql);
            
             //System.out.println("1>> " +sql);
            
               pstMovInvBod.executeUpdate();    
             
               
            
             sql=" Update tbm_inv set " +
               " nd_stkAct  = nd_stkAct + " + dblArtCan +
               " where " +
               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +  
               " co_itm = " + intCodArt;
              //  System.out.println("2>> " +sql);
              java.sql.PreparedStatement pstMovInv = conMovInv.prepareStatement(sql);     
               pstMovInv.executeUpdate();      
        }              
        catch(java.sql.SQLException Evt)
            {
                   try{
                        conMovInv.rollback();
                        conMovInv.close();
                   }catch(java.sql.SQLException Evts){
                        objUti.mostrarMsgErr_F1(jfract, Evts);
                   }
                   objUti.mostrarMsgErr_F1(jfract, Evt); 
            }
       catch(Exception Evt)
            {
                objUti.mostrarMsgErr_F1(jfract, Evt);
            }                       
    }
  
   
    
    public boolean movInventario(java.sql.Connection conn, int intCodArt, int intCodBod, int intAcc, double dblArtCan ){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        String sqlSql="";
         try{
            if(conn!=null){
               stmLoc=conn.createStatement();
                
               if(dblArtCan<0)
                 dblArtCan=dblArtCan*(-1);
                
                dblArtCan = dblArtCan * intAcc;
                
                
                sqlSql="Update tbm_invBod set nd_stkAct  = nd_stkAct + " + dblArtCan +
                " where co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
                " co_bod = " + intCodBod + " and co_itm = " + intCodArt;  
                System.out.println("1>> " +sqlSql);
                stmLoc.executeUpdate(sqlSql);
            
                sqlSql="Update tbm_inv set  nd_stkAct  = nd_stkAct + " + dblArtCan +
                " where  co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_itm = " + intCodArt;
                System.out.println("2>> " +sqlSql);
                stmLoc.executeUpdate(sqlSql);
                
                stmLoc.close();
                stmLoc=null;
              blnRes=true;
        }}catch(java.sql.SQLException Evt){  objUti.mostrarMsgErr_F1(jfract, Evt); blnRes=false; }
          catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfract, Evt);   blnRes=false; }
        return blnRes;
     }
  
     
    
    
     public boolean movInventario(java.sql.Connection conn, int intCodArt, int intCodBod, int intAcc, double dblArtCan,  String strEstFisBod, String  strMerIngEgr,  String strTipIngEgr, double nd_dblCan ){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        String sqlSql="";
         try{
            if(conn!=null){
               stmLoc=conn.createStatement();
                
               if(dblArtCan<0)
                 dblArtCan=dblArtCan*(-1);
                
                dblArtCan = dblArtCan * intAcc;
                
                
               String strAux="";
               String strAuxGrp="";
                if(strEstFisBod.equals("N")){
                 if(strMerIngEgr.equals("S")){
                   if(strTipIngEgr.equals("I")){ 
                       if(nd_dblCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+nd_dblCan+") ";
                       if(nd_dblCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+nd_dblCan+") ";
                       if(nd_dblCan > 0) strAuxGrp=" nd_caningbod=nd_caningbod+abs("+nd_dblCan+") ";
                       if(nd_dblCan < 0) strAuxGrp=" nd_caningbod=nd_caningbod-abs("+nd_dblCan+") ";
                   }
                   if(strTipIngEgr.equals("E")){
                       if(nd_dblCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+nd_dblCan+") ";
                       if(nd_dblCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+nd_dblCan+") ";
                       if(nd_dblCan > 0) strAuxGrp=" nd_canegrbod=nd_canegrbod-abs("+nd_dblCan+") ";
                       if(nd_dblCan < 0) strAuxGrp=" nd_canegrbod=nd_canegrbod+abs("+nd_dblCan+") ";
                   } 
                 }} 
               if(!strAuxGrp.equals("")){
                sqlSql = " Update tbm_invbod set "+strAuxGrp+" "+
                         " where co_emp=" + objZafParSis.getCodigoEmpresaGrupo() + " and co_itm =" +
                         " (Select co_itm from tbm_equinv where co_emp=" +  objZafParSis.getCodigoEmpresaGrupo() + " and co_itmmae IN " +
                         " (Select co_itmmae from tbm_equinv where co_emp ="+ objZafParSis.getCodigoEmpresa() +" and co_itm="+ intCodArt +") )" +
                         " and co_bod =" +
                         " (select co_bodgrp from  tbr_bodempbodgrp where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_bod="+intCodBod+") ";
                 stmLoc.executeUpdate(sqlSql);
               }        
               
                
                sqlSql="Update tbm_invBod set nd_stkAct  = nd_stkAct + " + dblArtCan +
                strAux+" where co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
                " co_bod = " + intCodBod + " and co_itm = " + intCodArt;  
                System.out.println("1>> " +sqlSql);
                stmLoc.executeUpdate(sqlSql);
            
                sqlSql="Update tbm_inv set  nd_stkAct  = nd_stkAct + " + dblArtCan +
                " where  co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_itm = " + intCodArt;
                System.out.println("2>> " +sqlSql);
                stmLoc.executeUpdate(sqlSql);
                
                stmLoc.close();
                stmLoc=null;
              blnRes=true;
        }}catch(java.sql.SQLException Evt){  objUti.mostrarMsgErr_F1(jfract, Evt); blnRes=false; }
          catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfract, Evt);   blnRes=false; }
        return blnRes;
     }
  
     
    
    public void movInventario_solo_FAC(java.sql.Connection con_remota, int intCodArt, int intCodBod, int intAcc, double dblArtCan, java.sql.Connection conMovInv, String strEstFisBod, String strMerIngEgr, String strTipIngEgr ){
        try{
            if (dblArtCan<0)
                dblArtCan=dblArtCan*(-1);
                
            dblArtCan = dblArtCan * intAcc;
            
            
              String strAux="";
                if(strEstFisBod.equals("N")){
                 if(strMerIngEgr.equals("S")){
                   if(strTipIngEgr.equals("I")){ 
                       if(dblArtCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+dblArtCan+") ";
                   }
                   if(strTipIngEgr.equals("E")){
                       if(dblArtCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+dblArtCan+") ";
                   } 
                 }} 
              
            String sql ="Update tbm_invBod set " +
               " nd_stkAct  = nd_stkAct + " + dblArtCan +
               " "+strAux+" "+
               " where " +
               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
               " co_bod = " + intCodBod + " and "+  
               " co_itm = " + intCodArt;  
            java.sql.PreparedStatement pstMovInvBod = conMovInv.prepareStatement(sql);
            pstMovInvBod.executeUpdate();
            
            java.sql.PreparedStatement pstMovInvBod_rem;
            if(con_remota!=null){ 
              pstMovInvBod_rem = con_remota.prepareStatement(sql);
              pstMovInvBod_rem.executeUpdate();    
            }
            
             sql=" Update tbm_inv set " +
               " nd_stkAct  = nd_stkAct + " + dblArtCan +
               " ,st_regrep='M'  where " +
               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +  
               " co_itm = " + intCodArt;
               java.sql.PreparedStatement pstMovInv = conMovInv.prepareStatement(sql);     
               pstMovInv.executeUpdate();      
              if(con_remota!=null){ 
                 pstMovInvBod_rem = con_remota.prepareStatement(sql);
                 pstMovInvBod_rem.executeUpdate();    
              }
        }              
        catch(java.sql.SQLException Evt)
            {
                   try{
                        conMovInv.rollback();
                        if(con_remota!=null){ 
                           con_remota.rollback();
                           con_remota.close();
                        }
                        
                        conMovInv.close();
                   }catch(java.sql.SQLException Evts){
                        objUti.mostrarMsgErr_F1(jfract, Evts);
                   }
                   objUti.mostrarMsgErr_F1(jfract, Evt); 
            }
       catch(Exception Evt)
            {
                objUti.mostrarMsgErr_F1(jfract, Evt);
            }                       
    }
  
    
    
 /**
    * Metodo polimorfico para Cargar o Descargar Inv. en bodegas transaccionalmente
    * Actualiza a travez del objeto SisCon el inventario consolidado
    * Se asume que ya existe el item en la bodega. (En el maestro de inventario al crear el item se le asigna a todas las bodegas existentes) 
    * @param: Conexion .- 
    * @param: CodEmpresa .-
    * @param: CodLocal .-
    * @param: CodBodega.-
    * @param: CodItm .-
    * @param: Cantidad .-
    * @param: Accion (1 carga o incrementa y -1 descarga o decrementa)
    * regresa true si fue realizado con exito 
    * regresa false si tuvo algun problema y no se genero movimiento o no actualizo consolidado
    *
    * @autor: jsalazar
    */ 

  
    
    public boolean movInventario(java.sql.Connection conMovInv, int intCodEmp,int intCodLoc, int intCodArt, int intCodBod, double dblArtCan, int intAcc ){
       String strSQL,strMsgError="";
       boolean blnMov=false;
        try{//odbc,usuario,password              
            dblArtCan = dblArtCan * intAcc;   
            
            if(!isItemBodega(intCodArt,intCodBod,conMovInv))
                creaItemBodega(intCodArt,intCodBod, conMovInv);
            if (objSisCon.existeStockItem(intCodEmp,intCodLoc,intCodArt,java.lang.Math.abs(dblArtCan)) || dblArtCan>0){                                                
                strSQL  = "";                        
                strSQL  = " Update tbm_invBod set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
                strSQL +=  " where co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                 

                java.sql.PreparedStatement pstMovInvBod = conMovInv.prepareStatement(strSQL);               
                pstMovInvBod.executeUpdate();                    
                blnMov = objSisCon.actualizarInventario(conMovInv, intCodEmp, intCodLoc, intCodArt, dblArtCan,intCodBod);
                // blnMov = true;  //*****************
            }else                //*********
                  blnMov = true; ///************* aqui era true
        }catch(java.sql.SQLException Evt){
           try{
                conMovInv.rollback();
                conMovInv.close();
           }catch(java.sql.SQLException Evts){
               objUti.mostrarMsgErr_F1(jfract, Evts);
           }
           blnMov = false;
           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
 
 
   /*
    * ESTA FUNCION  REALIZA EL AUMENTO O DISMINUCION DE LAS COMPRAS ENTRE CONPAÑIAS
    */
   public boolean movInventario_nuevo_2(java.sql.Connection conMovInv, int intCodEmp,int intCodLoc, int intCodArt, int intCodBod, double dblArtCan, int intAcc , int intValAtk, String strEstFisBod, String strMerIngEgr, String strTipIngEgr ){
       String strSQL,strMsgError="";
       boolean blnMov=false;
        try{//odbc,usuario,password              
            dblArtCan = dblArtCan * intAcc;   
                
              if (objSisCon.existeStockItem_nueva_2(intCodBod, intCodEmp,intCodLoc,intCodArt , intValAtk ,java.lang.Math.abs(dblArtCan)) || dblArtCan>0  ){                                                
                
                 String strAux="";
                if(strEstFisBod.equals("N")){
                 if(strMerIngEgr.equals("S")){
                   if(strTipIngEgr.equals("I")){ 
                       if(dblArtCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+dblArtCan+") ";
                   }
                   if(strTipIngEgr.equals("E")){
                       if(dblArtCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+dblArtCan+") ";
                   } 
                 }} 
                 
                strSQL  = "";                        
                strSQL  = " Update tbm_invBod set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
                strSQL +=  strAux+" where co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                 

                java.sql.PreparedStatement pstMovInvBod = conMovInv.prepareStatement(strSQL);               
                pstMovInvBod.executeUpdate();                    
                blnMov = true;  //***************** creado nuevo 22/06/2006
            }else                //*********
               blnMov = false; ///************* aqui era true
        }catch(java.sql.SQLException Evt){
           try{
                conMovInv.rollback();
                conMovInv.close();
           }catch(java.sql.SQLException Evts){
               objUti.mostrarMsgErr_F1(jfract, Evts);
           }
           blnMov = false;
           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
 
 
   
       
   public boolean movInventario_nuevo_2_solo_OC(java.sql.Connection conMovInv, java.sql.Connection con_remota, int intCodEmp,int intCodLoc, int intCodArt, int intCodBod, double dblArtCan, int intAcc , int intValAtk, String strEstFisBod, String strMerIngEgr, String strTipIngEgr ){
       String strSQL,strMsgError="";
       boolean blnMov=false;
        try{//odbc,usuario,password              
            dblArtCan = dblArtCan * intAcc;   
                   
              if (objSisCon.existeStockItem_nueva_2_solo_OC(conMovInv,  con_remota, intCodBod, intCodEmp,intCodLoc,intCodArt , intValAtk ,java.lang.Math.abs(dblArtCan)) || dblArtCan>0  ){                                                
                
                  String strAux="";
                if(strEstFisBod.equals("N")){
                 if(strMerIngEgr.equals("S")){
                   if(strTipIngEgr.equals("I")){ 
                       if(dblArtCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+dblArtCan+") ";
                   }
                   if(strTipIngEgr.equals("E")){
                       if(dblArtCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+dblArtCan+") ";
                   } 
                 }} 
                  
                strSQL  = "";                        
                strSQL  = " Update tbm_invBod set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
                strSQL +=  strAux+" where co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                 

                java.sql.PreparedStatement pstMovInvBod = conMovInv.prepareStatement(strSQL);               
                pstMovInvBod.executeUpdate();   
                 if(con_remota!=null){
                   java.sql.PreparedStatement pstMovInvBod_rem = con_remota.prepareStatement(strSQL);               
                   pstMovInvBod_rem.executeUpdate();   
                 }
                 
                blnMov = true;  //***************** creado nuevo 22/06/2006
            }else                //*********
               blnMov = false; ///************* aqui era true
        }catch(java.sql.SQLException Evt){
           try{
                conMovInv.rollback();
                
                if(con_remota!=null){
                  con_remota.rollback();
                  con_remota.close();
                }
                
                conMovInv.close();
                
           }catch(java.sql.SQLException Evts){
               objUti.mostrarMsgErr_F1(jfract, Evts);
           }
           blnMov = false;
           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
 
   
   
   public boolean movInventario_nuevo_grupo(java.sql.Connection conMovInv, int intCodEmp,int intCodLoc, int intCodArt, int intCodBod, double dblArtCan, int intAcc, String strEstFisBod, String strMerIngEgr, String strTipIngEgr ){
       String strSQL,strMsgError="";
       boolean blnMov=false;
        try{//odbc,usuario,password              
            dblArtCan = dblArtCan * intAcc;   
            
       //*****     if(!isItemBodega(intCodArt,intCodBod,conMovInv))
      //******          creaItemBodega(intCodArt,intCodBod, conMovInv);
             if (objSisCon.existeStockItem_nueva(intCodBod, intCodEmp,intCodLoc,intCodArt,java.lang.Math.abs(dblArtCan)) || dblArtCan>0){                                                
 //               strSQL  = "";                        
//                strSQL  = " Update tbm_invBod set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
//                strSQL +=  " where co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                 

                
               //  System.out.println("Elimina reg o anula  ==> "+strSQL);
              
//                java.sql.PreparedStatement pstMovInvBod = conMovInv.prepareStatement(strSQL);               
//                pstMovInvBod.executeUpdate();                    
                 blnMov = objSisCon.actualizarInventario_nuevo(conMovInv, intCodEmp, intCodLoc, intCodArt, dblArtCan, intCodBod,  strEstFisBod,  strMerIngEgr,  strTipIngEgr);
                 blnMov = true;  //*****************
            }else                //*********
                  blnMov = false; ///************* aqui era true
//        }catch(java.sql.SQLException Evt){
//           try{
//                conMovInv.rollback();
//                conMovInv.close();
//           }catch(java.sql.SQLException Evts){
//               objUti.mostrarMsgErr_F1(jfract, Evts);
//           }
//           blnMov = false;
//           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
 
   
 
   
   public boolean movInventario_nuevo_grupo_solo_OC(java.sql.Connection conMovInv,  java.sql.Connection con_remota,  int intCodEmp,int intCodLoc, int intCodArt, int intCodBod, double dblArtCan, int intAcc, String strEstFisBod, String strMerIngEgr, String strTipIngEgr ){
       String strSQL,strMsgError="";
       boolean blnMov=false;
        try{//odbc,usuario,password              
            dblArtCan = dblArtCan * intAcc;   
             if (objSisCon.existeStockItem_nueva_solo_OC(conMovInv, con_remota, intCodBod, intCodEmp,intCodLoc,intCodArt,java.lang.Math.abs(dblArtCan)) || dblArtCan>0){                                                
                  blnMov = objSisCon.actualizarInventario_2_solo_OC(conMovInv, con_remota,  intCodEmp, intCodLoc, intCodArt, dblArtCan, intCodBod, strEstFisBod, strMerIngEgr, strTipIngEgr);
                  blnMov = true;  
            }else               
                  blnMov = false; 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
 
   
 
     
   
  
   
   public boolean movInventario_nuevo(java.sql.Connection conMovInv, int intCodEmp,int intCodLoc, int intCodArt, int intCodBod, double dblArtCan, int intAcc, String strEstFisBod, String strMerIngEgr, String strTipIngEgr ){
       String strSQL,strMsgError="";
       boolean blnMov=false;
        try{//odbc,usuario,password              
            dblArtCan = dblArtCan * intAcc;   
            
            //****if(!isItemBodega(intCodArt,intCodBod,conMovInv))
            //******    creaItemBodega(intCodArt,intCodBod, conMovInv);
             if (objSisCon.existeStockItem_nueva(intCodBod, intCodEmp,intCodLoc,intCodArt,java.lang.Math.abs(dblArtCan)) || dblArtCan>0){                                                
                
                
                 String strAux="";
                if(strEstFisBod.equals("N")){
                 if(strMerIngEgr.equals("S")){
                   if(strTipIngEgr.equals("I")){ 
                       if(dblArtCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+dblArtCan+") ";
                   }
                   if(strTipIngEgr.equals("E")){
                       if(dblArtCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+dblArtCan+") ";
                   } 
                 }} 
                 
                 
                strSQL  = "";                        
                strSQL  = " Update tbm_invBod set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
                strSQL +=  strAux+" where co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                 

                 
                 System.out.println("Elimina reg o anula  ==> "+strSQL);
              
                java.sql.PreparedStatement pstMovInvBod = conMovInv.prepareStatement(strSQL);               
                pstMovInvBod.executeUpdate();                    
                 blnMov = objSisCon.actualizarInventario_nuevo(conMovInv, intCodEmp, intCodLoc, intCodArt, dblArtCan, intCodBod,  strEstFisBod,  strMerIngEgr,  strTipIngEgr);
                 blnMov = true;  //*****************
            }else                //*********
                  blnMov = false; ///************* aqui era true
        }catch(java.sql.SQLException Evt){
           try{
                conMovInv.rollback();
                conMovInv.close();
           }catch(java.sql.SQLException Evts){
               objUti.mostrarMsgErr_F1(jfract, Evts);
           }
           blnMov = false;
           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
 
  
   
   public boolean movInventario_nuevo_solo_OC(java.sql.Connection conMovInv, java.sql.Connection con_remota, int intCodEmp,int intCodLoc, int intCodArt, int intCodBod, double dblArtCan, int intAcc, String strEstFisBod, String strMerIngEgr, String strTipIngEgr  ){
       String strSQL,strMsgError="";
       boolean blnMov=false;
        try{             
            dblArtCan = dblArtCan * intAcc;   
             if (objSisCon.existeStockItem_nueva_solo_OC(conMovInv,  con_remota, intCodBod, intCodEmp,intCodLoc,intCodArt,java.lang.Math.abs(dblArtCan)) || dblArtCan>0){                                                
                
                 
                String strAux="";
                if(strEstFisBod.equals("N")){
                 if(strMerIngEgr.equals("S")){
                   if(strTipIngEgr.equals("I")){ 
                       if(dblArtCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+dblArtCan+") ";
                   }
                   if(strTipIngEgr.equals("E")){
                       if(dblArtCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+dblArtCan+") ";
                   } 
                 }} 
                 
                strSQL  = " Update tbm_invBod set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
                strSQL += strAux+" where co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                 
            
                java.sql.PreparedStatement pstMovInvBod = conMovInv.prepareStatement(strSQL);               
                pstMovInvBod.executeUpdate();                    
                if(con_remota!=null){
                  java.sql.PreparedStatement pstMovInvBod_rem = con_remota.prepareStatement(strSQL);               
                  pstMovInvBod_rem.executeUpdate();                    
                }
                
                 blnMov = objSisCon.actualizarInventario_2_solo_OC(conMovInv, con_remota, intCodEmp, intCodLoc, intCodArt, dblArtCan, intCodBod,  strEstFisBod, strMerIngEgr, strTipIngEgr);
                 blnMov = true;  //*****************
            }else                //*********
                  blnMov = false; ///************* aqui era true
        }catch(java.sql.SQLException Evt){
           try{
                conMovInv.rollback();
                if(con_remota!=null){
                  con_remota.rollback();
                  con_remota.close(); 
                }
                conMovInv.close();
               
           }catch(java.sql.SQLException Evts){
               objUti.mostrarMsgErr_F1(jfract, Evts);
           }
           blnMov = false;
           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
 
  
   
   
   
   
   /**
    * Metodo polimorfico para Cargar o Descargar Inv. en bodegas transaccionalmente
    * Actualiza a travez del objeto SisCon el inventario consolidado
    * Se recibe clave primaria del documento a recibir
    * Cantidad  a actulizar el inventario y la cantidad para costear
    * regresa true si fue realizado con exito 
    * regresa false si tuvo algun problema y no se genero movimiento o no actualizo consolidado
    *
    * @autor: jsalazar
    */ 
 
   public boolean movInventario_2(java.sql.Connection conMovInv, int intCodEmp,int intCodLoc,int intTipDoc, int intNumDoc, int intCodArt, int intCodBod, double dblArtCan,double dblCantReal,double dblValorCosteo, double dblValorDescuento, int intAcc ,boolean blncostear, String strEstFisBod, String strMerIngEgr, String strTipIngEgr){
       String strSQL,strMsgError="";
       boolean blnMov;
        try{//odbc,usuario,password              
            dblArtCan = dblArtCan * intAcc;   
            blnMov = false;
           //***** if(!isItemBodega(intCodArt,intCodBod,conMovInv))
           //*******     creaItemBodega(intCodArt,intCodBod, conMovInv);
             if (objSisCon.existeStockItem_bod(intCodBod, intCodEmp,intCodLoc,intCodArt, java.lang.Math.abs(dblArtCan)) || dblArtCan>0){  
                 
                 
              String strAux="";
                if(strEstFisBod.equals("N")){
                 if(strMerIngEgr.equals("S")){
                   if(strTipIngEgr.equals("I")){ 
                       if(dblArtCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+dblArtCan+") ";
                   }
                   if(strTipIngEgr.equals("E")){
                       if(dblArtCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+dblArtCan+") ";
                   } 
                 }} 
              
              
                strSQL  = "";                        
                strSQL  = " Update tbm_invBod set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
                strSQL +=  strAux+" where co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                 
                
                java.sql.PreparedStatement pstMovInvBod = conMovInv.prepareStatement(strSQL);               
                pstMovInvBod.executeUpdate();                    

                blnMov=true;
                
            }               
        }catch(java.sql.SQLException Evt){
           try{
                conMovInv.rollback();
                conMovInv.close();
           }catch(java.sql.SQLException Evts){
               objUti.mostrarMsgErr_F1(jfract, Evts);
           }
           blnMov = false;
           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
 
 
 
   
   
public boolean movInventario_2_solo_OC_venoc(java.sql.Connection conMovInv, java.sql.Connection con_remota, int intCodEmp,int intCodLoc,int intTipDoc, int intNumDoc, int intCodArt, int intCodBod, double dblArtCan,double dblCantReal,double dblValorCosteo, double dblValorDescuento, int intAcc ,boolean blncostear, String strEstFisBod, String strMerIngEgr, String strTipIngEgr){
       String strSQL,strMsgError="";
       boolean blnMov;
        try{//odbc,usuario,password              
            dblArtCan = dblArtCan * intAcc;   
            blnMov = false;
                 
             String strAux="";
                if(strEstFisBod.equals("N")){
                 if(strMerIngEgr.equals("S")){
                   if(strTipIngEgr.equals("I")){ 
                       if(dblArtCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+dblArtCan+") ";
                   }
                   if(strTipIngEgr.equals("E")){
                       if(dblArtCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+dblArtCan+") ";
                   } 
                 }} 
             
                strSQL  = "";                        
                strSQL  = " Update tbm_invBod set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
                strSQL +=  strAux+" where co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                 
               
               java.sql.Statement stm= conMovInv.createStatement();
               stm.executeUpdate(strSQL);
                        
   
              if(con_remota!=null){
                 java.sql.Statement stm_remota = con_remota.createStatement();
                 stm_remota.executeUpdate(strSQL);  // REMOTAMENTE         
              }   
               
               
                blnMov=true;
                
                    
        }catch(java.sql.SQLException Evt){
           try{
                conMovInv.rollback();
                 if(con_remota!=null) con_remota.rollback();
                conMovInv.close();
           }catch(java.sql.SQLException Evts){
               objUti.mostrarMsgErr_F1(jfract, Evts);
           }
           blnMov = false;
           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
 
 
   
   
 public boolean movInventario_2_solo_OC(java.sql.Connection conMovInv, java.sql.Connection con_remota, int intCodEmp,int intCodLoc,int intTipDoc, int intNumDoc, int intCodArt, int intCodBod, double dblArtCan,double dblCantReal,double dblValorCosteo, double dblValorDescuento, int intAcc ,boolean blncostear, String strEstFisBod, String strMerIngEgr, String strTipIngEgr){
       String strSQL,strMsgError="";
       boolean blnMov;
        try{//odbc,usuario,password              
            dblArtCan = dblArtCan * intAcc;   
            blnMov = false;
             if (objSisCon.existeStockItem_bod_solo_OC(conMovInv, con_remota, intCodBod, intCodEmp,intCodLoc,intCodArt, java.lang.Math.abs(dblArtCan)) || dblArtCan>0){                                                
                  
                 java.sql.Statement stm_remota; // = con_remota.createStatement();
                 java.sql.Statement stm= conMovInv.createStatement();
                 
                 String strAux="";
                if(strEstFisBod.equals("N")){
                 if(strMerIngEgr.equals("S")){
                   if(strTipIngEgr.equals("I")){ 
                       if(dblArtCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+dblArtCan+") ";
                   }
                   if(strTipIngEgr.equals("E")){
                       if(dblArtCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+dblArtCan+") ";
                   } 
                 }} 
                 
                strSQL  = "";                        
                strSQL  = " Update tbm_invBod set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
                strSQL +=  strAux+" where co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                 
                
               stm.executeUpdate(strSQL);
                if(con_remota!= null){
                    stm_remota = con_remota.createStatement();
                    stm_remota.executeUpdate(strSQL);  // REMOTAMENTE                  
                }
               
                blnMov=true;
                
            }               
        }catch(java.sql.SQLException Evt){
           try{
                conMovInv.rollback();
                if(con_remota!= null)
                     con_remota.rollback();
                conMovInv.close();
           }catch(java.sql.SQLException Evts){
               objUti.mostrarMsgErr_F1(jfract, Evts);
           }
           blnMov = false;
           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
 
 
   
   
   
   
 
 public boolean movInventario_sologrupo(java.sql.Connection conMovInv, int intCodEmp,int intCodLoc,int intTipDoc, int intNumDoc, int intCodArt, int intCodBod, double dblArtCan,double dblCantReal,double dblValorCosteo, double dblValorDescuento, int intAcc ,boolean blncostear,  String strEstFisBod, String strMerIngEgr, String strTipIngEgr){
       String strSQL,strMsgError="";
       boolean blnMov;
        try{//odbc,usuario,password              
            dblArtCan = dblArtCan * intAcc;   
            blnMov = false;
         //*****   if(!isItemBodega(intCodArt,intCodBod,conMovInv))
         ///******       creaItemBodega(intCodArt,intCodBod, conMovInv);
            if (objSisCon.existeStockItem(intCodEmp,intCodLoc,intCodArt,java.lang.Math.abs(dblArtCan)) || dblArtCan>0){                                                
//                strSQL  = "";                        
//                strSQL  = " Update tbm_invBod set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
//                strSQL +=  " where co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                 
//                
//                java.sql.PreparedStatement pstMovInvBod = conMovInv.prepareStatement(strSQL);               
//                pstMovInvBod.executeUpdate();                    
//               //*********************************

//              //*********************************
                 
                //*****  if (!blncostear)
               //****     blnMov = objSisCon.actualizarInventario_2(conMovInv, intCodEmp, intCodLoc, intTipDoc, intNumDoc, intCodArt, dblArtCan,dblCantReal,dblValorCosteo,dblValorDescuento,blncostear, intCodBod);
              //*****  else
                    blnMov = objSisCon.actualizarInventario_2(conMovInv, intCodEmp, intCodLoc, intCodArt, dblArtCan, intCodBod, strEstFisBod, strMerIngEgr, strTipIngEgr);
            }               
      // }
//        catch(java.sql.SQLException Evt){
//           try{
//                conMovInv.rollback();
//                conMovInv.close();
//           }catch(java.sql.SQLException Evts){
//               objUti.mostrarMsgErr_F1(jfract, Evts);
//           }
//           blnMov = false;
//           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
   

 
   public boolean movInventario_sologrupo_solo_OC(java.sql.Connection conMovInv,  java.sql.Connection con_remota, int intCodEmp,int intCodLoc,int intTipDoc, int intNumDoc, int intCodArt, int intCodBod, double dblArtCan,double dblCantReal,double dblValorCosteo, double dblValorDescuento, int intAcc ,boolean blncostear, String strEstFisBod, String strMerIngEgr, String strTipIngEgr){
       String strSQL,strMsgError="";
       boolean blnMov;
        try{//odbc,usuario,password              
            dblArtCan = dblArtCan * intAcc;   
            blnMov = false;
            if (objSisCon.existeStockItem_solo_OC(conMovInv, con_remota, intCodEmp,intCodLoc,intCodArt,java.lang.Math.abs(dblArtCan)) || dblArtCan>0){                                                
                blnMov = objSisCon.actualizarInventario_2_solo_OC(conMovInv, con_remota,  intCodEmp, intCodLoc, intCodArt, dblArtCan, intCodBod, strEstFisBod, strMerIngEgr, strTipIngEgr);
            }               
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
   
   
   
     
 
 public boolean movInventario(java.sql.Connection conMovInv, int intCodEmp,int intCodLoc,int intTipDoc, int intNumDoc, int intCodArt, int intCodBod, double dblArtCan,double dblCantReal,double dblValorCosteo, double dblValorDescuento, int intAcc ,boolean blncostear, String strEstFisBod, String strMerIngEgr, String strTipIngEgr){
       String strSQL,strMsgError="";
       boolean blnMov;
        try{//odbc,usuario,password              
            dblArtCan = dblArtCan * intAcc;   
            blnMov = false;
           //**** if(!isItemBodega(intCodArt,intCodBod,conMovInv))
           //****     creaItemBodega(intCodArt,intCodBod, conMovInv);
            if (objSisCon.existeStockItem(intCodEmp,intCodLoc,intCodArt,java.lang.Math.abs(dblArtCan)) || dblArtCan>0){   
                
                
              String strAux="";
                if(strEstFisBod.equals("N")){
                 if(strMerIngEgr.equals("S")){
                   if(strTipIngEgr.equals("I")){ 
                       if(dblArtCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+dblArtCan+") ";
                   }
                   if(strTipIngEgr.equals("E")){
                       if(dblArtCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+dblArtCan+") ";
                   } 
                 }} 
              
                strSQL  = "";                        
                strSQL  = " Update tbm_invBod set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
                strSQL +=  strAux+" where co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                 
                
                java.sql.PreparedStatement pstMovInvBod = conMovInv.prepareStatement(strSQL);               
                pstMovInvBod.executeUpdate();                    
                
               //**** if (!blncostear)
              //****      blnMov = objSisCon.actualizarInventario_2(conMovInv, intCodEmp, intCodLoc, intTipDoc, intNumDoc, intCodArt, dblArtCan,dblCantReal,dblValorCosteo,dblValorDescuento,blncostear, intCodBod);
             //****  else
                    blnMov = objSisCon.actualizarInventario_2(conMovInv, intCodEmp, intCodLoc, intCodArt, dblArtCan, intCodBod, strEstFisBod, strMerIngEgr, strTipIngEgr);
            }               
        }catch(java.sql.SQLException Evt){
           try{   
                conMovInv.rollback();
                conMovInv.close();
           }catch(java.sql.SQLException Evts){
               objUti.mostrarMsgErr_F1(jfract, Evts);
           }
           blnMov = false;
           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
       

 
 
  
 public boolean movInventario_solo_OC_venoc(java.sql.Connection conMovInv, java.sql.Connection con_remota, int intCodEmp,int intCodLoc,int intTipDoc, int intNumDoc, int intCodArt, int intCodBod, double dblArtCan,double dblCantReal,double dblValorCosteo, double dblValorDescuento, int intAcc ,boolean blncostear, String strEstFisBod, String strMerIngEgr, String strTipIngEgr){
       String strSQL,strMsgError="";
       boolean blnMov;
        try{
            dblArtCan = dblArtCan * intAcc;   
            blnMov = false;
            
               String strAux="";
                if(strEstFisBod.equals("N")){
                 if(strMerIngEgr.equals("S")){
                   if(strTipIngEgr.equals("I")){ 
                       if(dblArtCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+dblArtCan+") ";
                   }
                   if(strTipIngEgr.equals("E")){
                       if(dblArtCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+dblArtCan+") ";
                   } 
                 }} 
            
                strSQL  = "";                        
                strSQL  = " Update tbm_invBod set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
                strSQL +=  strAux+" where co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                 
               
                java.sql.Statement stm= conMovInv.createStatement();
                stm.executeUpdate(strSQL);
               
                if(con_remota!=null){
                   java.sql.Statement stm_remota = con_remota.createStatement();
                   stm_remota.executeUpdate(strSQL);  // REMOTAMENTE
                }
                                                
                blnMov = objSisCon.actualizarInventario_2_solo_OC(conMovInv, con_remota,  intCodEmp, intCodLoc, intCodArt, dblArtCan, intCodBod,  strEstFisBod,  strMerIngEgr,  strTipIngEgr);
                 
        }catch(java.sql.SQLException Evt){
           try{   
                conMovInv.rollback();
                if(con_remota!=null) con_remota.rollback();
                conMovInv.close();
           }catch(java.sql.SQLException Evts){
               objUti.mostrarMsgErr_F1(jfract, Evts);
           }
           blnMov = false;
           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
 
 
 
 public boolean movInventario_solo_OC(java.sql.Connection conMovInv, java.sql.Connection con_remota, int intCodEmp,int intCodLoc,int intTipDoc, int intNumDoc, int intCodArt, int intCodBod, double dblArtCan,double dblCantReal,double dblValorCosteo, double dblValorDescuento, int intAcc ,boolean blncostear, String strEstFisBod, String strMerIngEgr, String strTipIngEgr ){
       String strSQL,strMsgError="";
       boolean blnMov;
        try{
            dblArtCan = dblArtCan * intAcc;   
            blnMov = false;   
            if (objSisCon.existeStockItem_solo_OC(conMovInv, con_remota, intCodEmp,intCodLoc,intCodArt,java.lang.Math.abs(dblArtCan)) || dblArtCan>0){                                                
                
                String strAux="";
                if(strEstFisBod.equals("N")){
                 if(strMerIngEgr.equals("S")){
                   if(strTipIngEgr.equals("I")){ 
                       if(dblArtCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+dblArtCan+") ";
                   }
                   if(strTipIngEgr.equals("E")){
                       if(dblArtCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+dblArtCan+") ";
                   } 
                 }} 
                
                strSQL  = "";                        
                strSQL  = " Update tbm_invBod set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
                strSQL +=  strAux+" where co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                 
                
                java.sql.Statement stm_remota; //= con_remota.createStatement();
                java.sql.Statement stm= conMovInv.createStatement();
                stm.executeUpdate(strSQL);
                if(con_remota!=null){
                  stm_remota = con_remota.createStatement();
                  stm_remota.executeUpdate(strSQL);  // REMOTAMENTE
                }                                
                blnMov = objSisCon.actualizarInventario_2_solo_OC(conMovInv, con_remota,  intCodEmp, intCodLoc, intCodArt, dblArtCan, intCodBod, strEstFisBod,  strMerIngEgr,  strTipIngEgr );
            }               
        }catch(java.sql.SQLException Evt){
           try{   
                if(con_remota!=null){
                  conMovInv.rollback();
                  con_remota.rollback();
                }
                conMovInv.close();
           }catch(java.sql.SQLException Evts){
               objUti.mostrarMsgErr_F1(jfract, Evts);
           }
           blnMov = false;
           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
   
 
 
   public boolean movInventario_nuevo_2(java.sql.Connection conMovInv, int intCodEmp,int intCodLoc,int intTipDoc, int intNumDoc, int intCodArt, int intCodBod, double dblArtCan,double dblCantReal,double dblValorCosteo, double dblValorDescuento, int intAcc ,boolean blncostear , int intValAtk ){
       String strSQL,strMsgError="";
       boolean blnMov;
        try{//odbc,usuario,password              
            dblArtCan = dblArtCan * intAcc;   
            
            blnMov = false;
           //**** if(!isItemBodega(intCodArt,intCodBod,conMovInv))
           //*****     creaItemBodega(intCodArt,intCodBod, conMovInv);
            if (objSisCon.existeStockItem_nueva_bod(intCodBod, intCodEmp,intCodLoc,intCodArt, intValAtk, java.lang.Math.abs(dblArtCan)) || dblArtCan>0){                                                
                
                strSQL  = "";                        
                strSQL  = " Update tbm_invBod set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
                strSQL +=  " where co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                 
              
                
                java.sql.PreparedStatement pstMovInvBod = conMovInv.prepareStatement(strSQL);               
                pstMovInvBod.executeUpdate();                    
               
//              //*********************************
                blnMov=true;

            }               
        }catch(java.sql.SQLException Evt){
           try{
                conMovInv.rollback();
                conMovInv.close();
           }catch(java.sql.SQLException Evts){
               objUti.mostrarMsgErr_F1(jfract, Evts);
           }
           blnMov = false;
           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
   
   
   public boolean movInventario_nuevo_2_solo_OC(java.sql.Connection conMovInv, java.sql.Connection con_remota, int intCodEmp,int intCodLoc,int intTipDoc, int intNumDoc, int intCodArt, int intCodBod, double dblArtCan,double dblCantReal,double dblValorCosteo, double dblValorDescuento, int intAcc ,boolean blncostear , int intValAtk ){
       String strSQL,strMsgError="";
       boolean blnMov;
        try{//odbc,usuario,password              
            dblArtCan = dblArtCan * intAcc;   
            blnMov = false;
            if (objSisCon.existeStockItem_nueva_bod_solo_OC(conMovInv, con_remota, intCodBod, intCodEmp,intCodLoc,intCodArt, intValAtk, java.lang.Math.abs(dblArtCan)) || dblArtCan>0){                                                
                    
                strSQL  = "";                        
                strSQL  = " Update tbm_invBod set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
                strSQL +=  " where co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                 
                
                java.sql.PreparedStatement pstMovInvBod = conMovInv.prepareStatement(strSQL);               
                pstMovInvBod.executeUpdate();                    
                if(con_remota!=null){
                  java.sql.PreparedStatement pstMovInvBod_rem = con_remota.prepareStatement(strSQL);               
                  pstMovInvBod_rem.executeUpdate();         
                }
                blnMov=true;
            }               
        }catch(java.sql.SQLException Evt){
           try{
                conMovInv.rollback();
                 if(con_remota!=null){
                    con_remota.rollback();
                    con_remota.close();
                 }
                conMovInv.close();
                
           }catch(java.sql.SQLException Evts){
               objUti.mostrarMsgErr_F1(jfract, Evts);
           }
           blnMov = false;
           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
  
   
   
   
   
   
   
 
   public boolean movInventario_nuevo(java.sql.Connection conMovInv, int intCodEmp,int intCodLoc,int intTipDoc, int intNumDoc, int intCodArt, int intCodBod, double dblArtCan,double dblCantReal,double dblValorCosteo, double dblValorDescuento, int intAcc ,boolean blncostear){
       String strSQL,strMsgError="";
       boolean blnMov;
        try{//odbc,usuario,password              
            dblArtCan = dblArtCan * intAcc;   
           
          //  System.out.println("345==>>> " +dblArtCan +" == "+ intAcc +" == "+ dblArtCan);
            
            blnMov = false;
          //*****  if(!isItemBodega(intCodArt,intCodBod,conMovInv))
          //*******      creaItemBodega(intCodArt,intCodBod, conMovInv);
            if (objSisCon.existeStockItem_nueva(intCodBod, intCodEmp,intCodLoc,intCodArt,java.lang.Math.abs(dblArtCan)) || dblArtCan>0){                                                
              
               //  System.out.println("==>>> " +dblArtCan +" == "+ intAcc +" == "+ dblArtCan);
                
                strSQL  = "";                        
                strSQL  = " Update tbm_invBod set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
                strSQL +=  " where co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                 
              
                System.out.println("Actualiza o inserta ==> "+strSQL);
                         
                
                java.sql.PreparedStatement pstMovInvBod = conMovInv.prepareStatement(strSQL);               
                pstMovInvBod.executeUpdate();                    
//               //*********************************
//                String sql  = "select nd_stkact from tbm_invBod"+ 
//                          " where co_emp="+intCodEmp+" and co_bod ="+intCodBod+"  and co_itm ="+ intCodArt;            
                     
//                  String  sql = "select d.nd_stkAct from tbm_inv  as a "+
//                            " INNER JOIN tbm_equInv as x ON (a.co_emp=x.co_emp and a.co_itm=x.co_itm) "+
//                            " left outer join tbm_var as b on ( a.co_uni = b.co_reg) "+
//                            " inner join tbr_bodempbodgrp as c on (c.co_emp=1 and c.co_bod=(select  co_bodper from tbr_bodemp where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc = "+objZafParSis.getCodigoLocal()+" and co_empper="+objZafParSis.getCodigoEmpresaGrupo()+" and st_reg='P'))"+
//                            " inner join tbm_invbod as d on (d.co_emp=c.co_empgrp and d.co_bod=c.co_bodgrp and  a.co_itm=d.co_itm)"+
//                            " where  a.co_emp ="+objZafParSis.getCodigoEmpresa()+" and a.co_itm="+intCodArt;
                /*
                   String  sql = "SELECT  d3.nd_stkAct,d1.co_itmmae"+
                       " FROM( SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.co_uni, a1.st_ivacom"+
                       " FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)"+
                       " WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+""+
                       " ) AS d1 INNER JOIN( SELECT b2.co_itmMae, b1.nd_cosUni FROM tbm_inv AS b1"+
                       " INNER JOIN tbm_equInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)"+
                       " WHERE b1.co_emp="+objZafParSis.getCodigoEmpresaGrupo()+" ) AS d2 ON (d1.co_itmMae=d2.co_itmMae) "+
                       " INNER JOIN(SELECT c1.co_itmMae, SUM(c2.nd_stkAct) AS nd_stkAct FROM tbm_equInv AS c1"+
                       " INNER JOIN tbm_invBod AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)"+
                       " INNER JOIN tbr_bodEmp AS c3 ON (c2.co_emp=c3.co_empPer AND c2.co_bod=c3.co_bodPer)"+
                       " WHERE c3.co_emp="+objZafParSis.getCodigoEmpresa()+" AND c3.co_loc="+objZafParSis.getCodigoLocal()+" GROUP BY c1.co_itmMae"+
                       " ) AS d3 ON (d1.co_itmMae=d3.co_itmMae) LEFT OUTER JOIN tbm_var AS d4 ON (d1.co_uni=d4.co_reg)"+
                       " where  d1.co_itm="+intCodArt;
                  */
                
              String sql = "SELECT SUM(a2.nd_stkAct) AS nd_stkAct" +
                           " FROM tbm_equInv AS a1 INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)" +
                           " INNER JOIN tbr_bodEmp AS a3 ON (a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer)" +
                           " WHERE a3.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a3.co_loc="+objZafParSis.getCodigoLocal()+" AND a1.co_itmMae=(SELECT co_itmMae FROM tbm_equInv" +
                           " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+intCodArt+")";

                        java.sql.ResultSet rst2;
                        java.sql.Statement stm2= conMovInv.createStatement();   
                        System.out.println("CONSULTA STOCK  ==> "+ sql );
                        rst2 = stm2.executeQuery(sql);
                         if(rst2.next()){
                           double i = rst2.getDouble(1);
                           
                           if(i < 0.00 && intAcc == -1 ) {
                            //  System.out.println("Stock ==> "+ i ); 
                              conMovInv.rollback();
                              return blnMov = false;
                           } }
//              //*********************************
                
               //****** if (!blncostear) {
                   // System.out.println("AQUI ENTRA 1 ");
                //********     blnMov = objSisCon.actualizarInventario_nuevo(conMovInv, intCodEmp, intCodLoc, intTipDoc, intNumDoc, intCodArt, dblArtCan,dblCantReal,dblValorCosteo,dblValorDescuento,blncostear,intCodBod);
                
               //***** }else{ // System.out.println("AQUI ENTRA 2 ");   
                    blnMov = objSisCon.actualizarInventario(conMovInv, intCodEmp, intCodLoc, intCodArt, dblArtCan, intCodBod);
               //**** }
            }               
        }catch(java.sql.SQLException Evt){
           try{
                conMovInv.rollback();
                conMovInv.close();
           }catch(java.sql.SQLException Evts){
               objUti.mostrarMsgErr_F1(jfract, Evts);
           }
           blnMov = false;
           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
   
   
  public boolean movInventario_nuevo_solo_OC(java.sql.Connection conMovInv, java.sql.Connection con_remota, int intCodEmp,int intCodLoc,int intTipDoc, int intNumDoc, int intCodArt, int intCodBod, double dblArtCan,double dblCantReal,double dblValorCosteo, double dblValorDescuento, int intAcc ,boolean blncostear, String strEstFisBod, String strMerIngEgr, String strTipIngEgr){
       String strSQL,strMsgError="";
       boolean blnMov;
        try{           
            dblArtCan = dblArtCan * intAcc;   
            blnMov = false;
            if (objSisCon.existeStockItem_nueva_solo_OC(conMovInv, con_remota, intCodBod, intCodEmp,intCodLoc,intCodArt,java.lang.Math.abs(dblArtCan)) || dblArtCan>0){                                                
             
                String strAux="";
                if(strEstFisBod.equals("N")){
                 if(strMerIngEgr.equals("S")){
                   if(strTipIngEgr.equals("I")){ 
                       if(dblArtCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+dblArtCan+") ";
                   }
                   if(strTipIngEgr.equals("E")){
                       if(dblArtCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+dblArtCan+") ";
                       if(dblArtCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+dblArtCan+") ";
                   } 
                 }} 
                
                strSQL  = " Update tbm_invBod set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
                strSQL +=  strAux+" where co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                 
              
               // System.out.println("Actualiza o inserta ==> "+strSQL);
                
                java.sql.PreparedStatement pstMovInvBod = conMovInv.prepareStatement(strSQL);               
                pstMovInvBod.executeUpdate();       
                
                if(con_remota!=null){
                  java.sql.PreparedStatement pstMovInvBod_rem = con_remota.prepareStatement(strSQL);               
                  pstMovInvBod_rem.executeUpdate();       
                }
                
              String sql = "SELECT SUM(a2.nd_stkAct) AS nd_stkAct" +
                           " FROM tbm_equInv AS a1 INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)" +
                           " INNER JOIN tbr_bodEmp AS a3 ON (a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer)" +
                           " WHERE a3.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a3.co_loc="+objZafParSis.getCodigoLocal()+" AND a1.co_itmMae=(SELECT co_itmMae FROM tbm_equInv" +
                           " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+intCodArt+")";

                        java.sql.ResultSet rst2;
                        
                        java.sql.Statement stm2;
                        
                        if(con_remota!=null){
                          stm2= con_remota.createStatement();   
                          rst2 = stm2.executeQuery(sql);
                        }else{
                           stm2= conMovInv.createStatement();   
                           rst2 = stm2.executeQuery(sql);   
                        }
                        
                        System.out.println("CONSULTA STOCK  ==> "+ sql );
                        
                        if(rst2.next()){
                           double i = rst2.getDouble(1);
                           
                           if(i < 0.00 && intAcc == -1 ) {
                              conMovInv.rollback();
                              return blnMov = false;
                           } }
                    blnMov = objSisCon.actualizarInventario_2_solo_OC(conMovInv, con_remota,  intCodEmp, intCodLoc, intCodArt, dblArtCan, intCodBod,  strEstFisBod,  strMerIngEgr,  strTipIngEgr);
            }               
        }catch(java.sql.SQLException Evt){
           try{
                conMovInv.rollback();
                conMovInv.close();
           }catch(java.sql.SQLException Evts){
               objUti.mostrarMsgErr_F1(jfract, Evts);
           }
           blnMov = false;
           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
   
   
 
   
   
   
    public boolean movInventario_nuevo_impo(java.sql.Connection conMovInv, int intCodEmp,int intCodLoc,int intTipDoc, int intNumDoc, int intCodArt, int intCodBod, double dblArtCan,double dblCantReal,double dblValorCosteo, double dblValorDescuento, int intAcc ,boolean blncostear){
       String strSQL,strMsgError="";
       boolean blnMov;
       try{             
            dblArtCan = dblArtCan * intAcc;   
            blnMov = false;
            if (objSisCon.existeStockItem_nueva(intCodBod, intCodEmp,intCodLoc,intCodArt,java.lang.Math.abs(dblArtCan)) || dblArtCan>0){                                                
              
                
                strSQL  = "";                        
                strSQL  = " Update tbm_invBod set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
                strSQL +=  " where co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                 
                java.sql.PreparedStatement pstMovInvBod = conMovInv.prepareStatement(strSQL);               
                pstMovInvBod.executeUpdate();                    


                strSQL  = "";                        
                strSQL  = " Update tbm_inv set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
                strSQL +=  " where co_emp = " + intCodEmp + " and co_itm = " + intCodArt;                 
                pstMovInvBod = conMovInv.prepareStatement(strSQL);               
                pstMovInvBod.executeUpdate();                    

                
              String sql = "SELECT SUM(a2.nd_stkAct) AS nd_stkAct" +
                           " FROM tbm_equInv AS a1 INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)" +
                           " INNER JOIN tbr_bodEmp AS a3 ON (a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer)" +
                           " WHERE a3.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a3.co_loc="+objZafParSis.getCodigoLocal()+" AND a1.co_itmMae=(SELECT co_itmMae FROM tbm_equInv" +
                           " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+intCodArt+")";

                        java.sql.ResultSet rst2;
                        java.sql.Statement stm2= conMovInv.createStatement();   
                      //  System.out.println("CONSULTA STOCK  ==> "+ sql );
                        rst2 = stm2.executeQuery(sql);
                         if(rst2.next()){
                           double i = rst2.getDouble(1);
                           
                           if(i < 0.00 && intAcc == -1 ) {
                            //  System.out.println("Stock ==> "+ i ); 
                              conMovInv.rollback();
                              return blnMov = false;
                           } }
                    blnMov = objSisCon.actualizarInventario(conMovInv, intCodEmp, intCodLoc, intCodArt, dblArtCan, intCodBod);
            }               
        }catch(java.sql.SQLException Evt){
           try{
                conMovInv.rollback();
                conMovInv.close();
           }catch(java.sql.SQLException Evts){
               objUti.mostrarMsgErr_F1(jfract, Evts);
           }
           blnMov = false;
           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
   
 
   
   
  /**
    * Metodo polimorfico para Cargar o Descargar Inv. transaccionalmente
    * @param: Conexion .- 
    * @param: CodEmpresa .-
    * @param: ArrayList.- Contiene el codigo de sistema de los items a actualizar
    * regresa true si fue realizado con exito 
    * regresa false si tuvo algun problema y no se genero movimiento  
    *
    * @autor: jsalazar
    */ 
   public boolean UpdateInventarioMaestro(java.sql.Connection conMovInv, int intCodEmp, java.util.ArrayList arlAux){
       String strSQL;
       boolean blnMov;
        try{//odbc,usuario,password              
       	    String strAux="";
            for (int i=0;i<arlAux.size();i++){      
                strAux +=arlAux.get(i).toString();
                if (i+1<arlAux.size()) strAux +=",";
            }
            strSQL  = "";                        
            strSQL  = " UPDATE tbm_inv SET nd_stkAct=b1.nd_stkAct " +
		      " FROM ( SELECT a2.co_emp, a2.co_itm, SUM(a2.nd_stkAct) AS nd_stkAct FROM tbm_invBod AS a2 WHERE a2.co_emp=" + intCodEmp +" AND a2.co_itm IN (" + strAux +") GROUP BY a2.co_emp, a2.co_itm ORDER BY a2.co_itm ) AS b1 " +
                      " WHERE tbm_inv.co_emp=b1.co_emp AND tbm_inv.co_itm=b1.co_itm " +
                      " AND tbm_inv.co_emp=" + intCodEmp +
                      " AND  tbm_inv.co_itm IN ("+strAux+")";                 
          
            
          // System.out.println("sSQL:"+strSQL);
            java.sql.PreparedStatement pstMovInvBod = conMovInv.prepareStatement(strSQL);               
            pstMovInvBod.executeUpdate();    
             
            blnMov = true;
        }catch(java.sql.SQLException Evt){
           try{
                conMovInv.rollback();
                conMovInv.close();
           }catch(java.sql.SQLException Evts){
               blnMov = false;
               objUti.mostrarMsgErr_F1(jfract, Evts);
           }
           blnMov = false;
           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
  
   
    
      
     
   public boolean UpdateInventarioMaestro_solo_OC(java.sql.Connection conMovInv, java.sql.Connection con_remota, int intCodEmp, java.util.ArrayList arlAux){
       String strSQL;
       boolean blnMov;
        try{//odbc,usuario,password              
       	    String strAux="";
            for (int i=0;i<arlAux.size();i++){      
                strAux +=arlAux.get(i).toString();
                if (i+1<arlAux.size()) strAux +=",";
            }
            strSQL  = "";                        
            strSQL  = " UPDATE tbm_inv SET nd_stkAct=b1.nd_stkAct ,st_regrep='M' " +
		      " FROM ( SELECT a2.co_emp, a2.co_itm, SUM(a2.nd_stkAct) AS nd_stkAct FROM tbm_invBod AS a2 WHERE a2.co_emp=" + intCodEmp +" AND a2.co_itm IN (" + strAux +") GROUP BY a2.co_emp, a2.co_itm ORDER BY a2.co_itm ) AS b1 " +
                      " WHERE tbm_inv.co_emp=b1.co_emp AND tbm_inv.co_itm=b1.co_itm " +
                      " AND tbm_inv.co_emp=" + intCodEmp +
                      " AND  tbm_inv.co_itm IN ("+strAux+")";                 
          
            java.sql.PreparedStatement pstMovInvBod = conMovInv.prepareStatement(strSQL);               
            pstMovInvBod.executeUpdate();    
            if(con_remota!=null){
              java.sql.PreparedStatement pst_remota = con_remota.prepareStatement(strSQL);               
              pst_remota.executeUpdate();  
            }
               
            blnMov = true;
        }catch(java.sql.SQLException Evt){
           try{
                conMovInv.rollback();
                if(con_remota!=null){
                  con_remota.rollback();
                  con_remota.close();
                }
                conMovInv.close();
                
           }catch(java.sql.SQLException Evts){
               blnMov = false;
               objUti.mostrarMsgErr_F1(jfract, Evts);
           }
           blnMov = false;
           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
  
  
   
   
   private void MensajeInf(String strMsg){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit="Mensaje del sistema Zafiro";
            obj.showMessageDialog(new javax.swing.JInternalFrame(),strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
   /**
    * Metodo que devuelve el sgte. codigo maestro de inventario
    * @param: codigo de la empresa.
    * @return : 0 si tuvo un error
    *           n con el sgte. numero
    * @autor: jsalazar
    */
    public int getCodItemMaestro(int intCodEmp){
        int intRes = 0;
        try{
            java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            try{
                if (con!=null){
                    java.sql.Statement stm = con.createStatement();
                    String strSQL = "select max(co_itmmae) + 1 as co_itmmae from tbm_equinv";
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);
                    if (rst.next()){
                        intRes = rst.getInt("co_itmmae");
                    }else{
                        intRes = 1;
                    }
                }
            }catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
            }
        }catch(Exception e){
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return intRes;
    }
    
    
    
    
    public double getExistencia(int co_art){
        //java.util.Vector vecExi = new java.util.Vector();
        double dblExistencia=0;
        try{//odbc,usuario,password
          conExi=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            stmRec=conExi.createStatement();
            
         sSQL= " Select nd_stkAct " +
               " From tbm_inv " +
               " where " +
               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
               " co_itm = " + co_art;  
          
            rstExi=stmRec.executeQuery(sSQL);
               
               if (rstExi.next())
               {
                    dblExistencia= Double.parseDouble(rstExi.getString("nd_stkAct"));
               }
               else
                    return -1;
            
               stmRec.close();
               conExi.close();
        }              
        catch(java.sql.SQLException Evt)
            {
                objUti.mostrarMsgErr_F1(jfract, Evt);
                return -1;
            }

        catch(Exception Evt)
            {
                objUti.mostrarMsgErr_F1(jfract, Evt);
                return -1;
            }       
        return dblExistencia;
    }
    
    public double getExistencia(int codArt, int codBod){
        //java.util.Vector vecExi = new java.util.Vector();
        double dblExistencia=0;
        try{//odbc,usuario,password
            conExi=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            stmRec=conExi.createStatement();
            
         sSQL= " Select nd_stkAct " +
               " From tbm_invBod " +
               " where " +
               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
               " co_bod = " + codBod + " and "+  
               " co_itm = " + codArt;  
          
            rstExi=stmRec.executeQuery(sSQL);
               
               if (rstExi.next())
               {
                    dblExistencia= Double.parseDouble(rstExi.getString("nd_stkAct")==null?"0":rstExi.getString("nd_stkAct"));
               }
               else
                    return -1;
            
               stmRec.close();
               conExi.close();
        }              
        catch(java.sql.SQLException Evt)
            {
                objUti.mostrarMsgErr_F1(jfract, Evt);
                return -1;
            }

        catch(Exception Evt)
            {
                objUti.mostrarMsgErr_F1(jfract, Evt);
                return -1;
            }       
        return dblExistencia;
    }
    
    
    
    public double getCosto(int intCodArt){
        double dblCosto=0;
        try{//odbc,usuario,password
            conExi=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            stmRec=conExi.createStatement();
            
         sSQL= " Select nd_cosUni " +
               " From tbm_inv " +
               " where " +
               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
               " co_itm = " + intCodArt;
          
               rstExi=stmRec.executeQuery(sSQL);
               
               if (rstExi.next())
               {
			dblCosto=Double.parseDouble(rstExi.getString("nd_cosUni")==null?"0":rstExi.getString("nd_cosUni"));
               }
               else
                   return -1;
               
               stmRec.close();
               conExi.close();
        }              
        catch(java.sql.SQLException Evt)
            {
                objUti.mostrarMsgErr_F1(jfract, Evt);
                return -1;
            }
        catch(Exception Evt)
            {
                objUti.mostrarMsgErr_F1(jfract, Evt);
                return -1;
            }       
        return dblCosto;
    }
    
 
  
   public boolean isItem(int intCodArt){
        java.sql.Statement stmInv;   //Statement para el recosteo
        java.sql.ResultSet rstInv;   //Resultset que tendra los datos de la existencia y el valor de la Existencia
        String sSQL;
        
        
        try{//odbc,usuario,password
            conExi=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            stmInv=conExi.createStatement();
            
         sSQL= " Select * " +
               " From tbm_inv " +
               " where " +
               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
               " co_itm = " + intCodArt;  
               
            rstInv=stmInv.executeQuery(sSQL);
               
               if (!rstExi.next())
               {
                    return false;
               }
  
            rstInv.close();
            stmInv.close();
            conExi.close();
         }              
         catch(java.sql.SQLException Evt)
            {
                objUti.mostrarMsgErr_F1(jfract, Evt);
                return false;
            }

        catch(Exception Evt)
            {
                objUti.mostrarMsgErr_F1(jfract, Evt);
                return false;
            }       
        return true;
   }
   
   public boolean isItemBodega(int intCodArt, int intCodBod){
        java.sql.Statement stmInv;   //Statement para el recosteo
        java.sql.ResultSet rstInv;   //Resultset que tendra los datos de la existencia y el valor de la Existencia
        String sSQL;
        
        
        try{//odbc,usuario,password
            conExi=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            stmInv=conExi.createStatement();
            
         sSQL= " Select * " +
               " From tbm_invbod " +
               " where " +
               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
               " co_bod = " + intCodBod + " and "+  
               " co_itm = " + intCodArt;  
              
            rstInv=stmInv.executeQuery(sSQL);
               
               if (!rstInv.next())
                    return false;
               rstInv.close();
               stmInv.close();
               conExi.close();
        }              
         catch(java.sql.SQLException Evt)
            {
                objUti.mostrarMsgErr_F1(jfract, Evt);
                return false;
            }

        catch(Exception Evt)
            {
                objUti.mostrarMsgErr_F1(jfract, Evt);
                return false;
            }       
        return true;
   }
   public boolean isItemBodega(int intCodArt, int intCodBod, java.sql.Connection conItm){
        java.sql.Statement stmInv;   //Statement para el recosteo
        java.sql.ResultSet rstInv;   //Resultset que tendra los datos de la existencia y el valor de la Existencia
        String sSQL;
        
        
        try{//odbc,usuario,password
            //conItm=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            stmInv=conItm.createStatement();
            
         sSQL= " Select * " +
               " From tbm_invbod " +
               " where " +
               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
               " co_bod = " + intCodBod + " and "+  
               " co_itm = " + intCodArt;  
//             System.out.println(sSQL) ;  
            rstInv=stmInv.executeQuery(sSQL);
               
               if (!rstInv.next())
                    return false;
               //rstInv.close();
               //stmInv.close();
//               conExi.close();
        }              
         catch(java.sql.SQLException Evt)
            {
                objUti.mostrarMsgErr_F1(jfract, Evt);
                return false;
            }

        catch(Exception Evt)
            {
                objUti.mostrarMsgErr_F1(jfract, Evt);
                return false;
            }       
        return true;
   }
   
   
   /**
    * Metodo para verificar si tiene stock en la bodega de la empresa
    * @param: CodEmpresa.-
    * @param: CodBodega .-
    * @param: CodItem .-
    * @autor: jsalazar
    * @return true si el stock + la cantidad a operar es mayor a cero.
    */
   public boolean isStockItemBodega(java.sql.Connection conExi,int intCodEmp, int intCodBod, int intCodArt, double dblArtCan){
        java.sql.Statement stmInv;   //Statement para el recosteo
        java.sql.ResultSet rstInv;   //Resultset que tendra los datos de la existencia y el valor de la Existencia
        String sSQL;
                
        try{//odbc,usuario,password
            stmInv=conExi.createStatement();            
             sSQL= " Select *  From tbm_invbod  where (nd_stkact+"+dblArtCan+")>=0 and co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                
//             System.out.println("sSQL:"+sSQL);
             rstInv=stmInv.executeQuery(sSQL);
               if (!rstInv.next()){                                    
                    return false;
               }
               rstInv.close();
               stmInv.close();
        }catch(java.sql.SQLException Evt) {
                objUti.mostrarMsgErr_F1(jfract, Evt);
                return false;
        }catch(Exception Evt){
                objUti.mostrarMsgErr_F1(jfract, Evt);
                return false;
        }       
        return true;
   }   
   
   /**
    * @jsalazar
    */
   public boolean creaItemBodega(java.sql.Connection con,int intCodEmp,int intCodItm, int intCodBod){
        
        try{                           
            String strSQL = " Insert into tbm_invbod  (co_emp, co_bod, co_itm, nd_stkact, nd_stkmin, nd_stkmax ) " +
                            " values( " + intCodEmp + ", "+  intCodBod + ", "+  intCodItm + ", 0, 0, 0)"; //stock actual, minimo y maximo==0  
            java.sql.PreparedStatement pstInvBod = con.prepareStatement(strSQL);
           // System.out.println(strSQL);
            pstInvBod.executeUpdate();                           
         }catch(java.sql.SQLException Evt){
                try{
                        con.rollback();
                        con.close();
                   }catch(java.sql.SQLException Evts){
                        objUti.mostrarMsgErr_F1(jfract, Evts);
                   }
                   objUti.mostrarMsgErr_F1(jfract, Evt); 
                   return false;
            }catch(Exception Evt){
                objUti.mostrarMsgErr_F1(jfract, Evt);
                return false;
            }       
        return true;
     }    
   public void creaItemBodega(int intCodArt, int intCodBod, java.sql.Connection conCreItmBod){
        
        double dblValExistencia=0;
        
        try{
               
            
            String strSQL = " Insert into tbm_invbod  (co_emp, co_bod, co_itm, nd_stkact, nd_stkmin, nd_stkmax ) " +
               " values( " + objZafParSis.getCodigoEmpresa() + ", "+  intCodBod + ", "+  intCodArt + ", 0, 0, 0)"; //stock actual, minimo y maximo==0  
            java.sql.PreparedStatement pstInvBod = conCreItmBod.prepareStatement(strSQL);
            //System.out.println(strSQL);
            pstInvBod.executeUpdate();                           
         }              
         catch(java.sql.SQLException Evt)
            {
                try{
                        conCreItmBod.rollback();
                        conCreItmBod.close();
                   }catch(java.sql.SQLException Evts){
                        objUti.mostrarMsgErr_F1(jfract, Evts);
                   }
                   objUti.mostrarMsgErr_F1(jfract, Evt); 
            }

        catch(Exception Evt)
            {
                objUti.mostrarMsgErr_F1(jfract, Evt);
            }       
     }
   
   
    public void creaItemBodega(int intCodArt, int intCodBod){
        java.sql.Statement stmInv;   //Statement para el recosteo
        String sSQL;
        
        double dblValExistencia=0;
        
        try{//odbc,usuario,password
            conExi=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            stmInv=conExi.createStatement();
            
         sSQL= " Insert into tbm_invbod " +
               " (co_emp, co_bod, co_itm, nd_stkact, nd_stkmin, nd_stkmax ) " +
               " values( " +
                objZafParSis.getCodigoEmpresa() + ", "+  
                 intCodBod + ", "+  
                 intCodArt + ", "+  
               " 0, 0, 0)"; //stock actual, minimo y maximo==0  
      //         System.out.println(sSQL);
            stmInv.executeUpdate(sSQL);
               
            stmInv.close();
            conExi.close();
         }              
         catch(java.sql.SQLException Evt)
            {
                objUti.mostrarMsgErr_F1(jfract, Evt);
            }

        catch(Exception Evt)
            {
                objUti.mostrarMsgErr_F1(jfract, Evt);
            }       
     }   
   public boolean isItem(int intCodEmp,int intCodItem){
        java.sql.Statement stmInv;   //Statement para el recosteo
        java.sql.ResultSet rstInv;   //Resultset que tendra los datos de la existencia y el valor de la Existencia
        String sSQL;
        
        
        try{//odbc,usuario,password
            conExi=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            stmInv=conExi.createStatement();
            
         sSQL= " Select co_itm " +
               " From tbm_inv " +
               " where " +
               " co_emp = " + intCodEmp + " and "+  
               " co_itm = " + intCodItem;  
               
            rstInv=stmInv.executeQuery(sSQL);
               
               if (!rstInv.next())
               {
                    return false;
               }
  
            rstInv.close();
            stmInv.close();
            conExi.close();
         }catch(java.sql.SQLException Evt){
                objUti.mostrarMsgErr_F1(jfract, Evt);
                return false;
         }catch(Exception Evt){
                objUti.mostrarMsgErr_F1(jfract, Evt);
                return false;
         }       
        return true;
   }
   public int isItem(int intCodEmp,String strCodAlt){
       int intResp = 0;
        java.sql.Statement stmInv;   //Statement para el recosteo
        java.sql.ResultSet rstInv;   //Resultset que tendra los datos de la existencia y el valor de la Existencia
        String sSQL;
        
        
        try{//odbc,usuario,password
            conExi=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            stmInv=conExi.createStatement();
            
         sSQL= " Select co_itm " +
               " From tbm_inv " +
               " where " +
               " co_emp = " + intCodEmp + " and "+  
               " tx_codalt = '" + strCodAlt + "'";  
               
            rstInv=stmInv.executeQuery(sSQL);
               
               if (rstInv.next())
               {
                    intResp = rstExi.getInt("co_itm");
               }
  
            rstInv.close();
            stmInv.close();
            conExi.close();
         }catch(java.sql.SQLException Evt){
                objUti.mostrarMsgErr_F1(jfract, Evt);
                return intResp;
         }catch(Exception Evt){
                objUti.mostrarMsgErr_F1(jfract, Evt);
                return intResp;
         }       
        return intResp;
   }

    /**
     * Metodo para verificar si el item es de servicio
     * Recibe el codigo del sistema 
     *Return true si encontro el item como servicio
     *       false si no encontro el item como de servicio
     *@autor: jsalazar
     */
   public boolean isItemServicio (int intCodItm){
       boolean blnResp =  false;                   
       try{
//           if (!isItem(objZafParSis.getCodigoEmpresa(),intCodItm))
//               return false;
           String strSQL = "";
           strSQL = " Select count(co_itm) from tbm_inv " +
                    " where co_emp = " + objZafParSis.getCodigoEmpresa() +
                    " and co_itm = " + intCodItm + " and st_ser='S'";
           if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(),objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL) ==1)
               blnResp =  true;                   
           else
               blnResp =  false;                                  
       }catch (Exception e){
           objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
           blnResp =  false;
       }
       return blnResp;
   }
    /**
     * Metodo para verificar si el item es de servicio
     * Recibe el codigo de la empresa y codigo del sistema 
     *Return true si encontro el item como servicio
     *       false si no encontro el item como de servicio
     *@autor: jsalazar
     */
   public boolean isItemServicio (int intCodEmp,int intCodItm){
       boolean blnResp =  false;                   
       try{
//           if (!isItem(objZafParSis.getCodigoEmpresa(),intCodItm))
//               return false;
           String strSQL = "";
           strSQL = " Select count(co_itm) from tbm_inv " +
                    " where co_emp = " + intCodEmp +
                    " and co_itm = " + intCodItm + " and st_ser='S'";
           if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(),objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL) ==1)
               blnResp =  true;                   
           else
               blnResp =  false;                                  
       }catch (Exception e){
           objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
           blnResp =  false;
       }
       return blnResp;
   }
    /**
     * Metodo para verificar si el item es de servicio
     * Recibe el codigo de la empresa y codigo alterno 
     *Return true si encontro el item como servicio
     *       false si no encontro el item como de servicio
     *@autor: jsalazar
     */
   public boolean isItemServicio (int intCodEmp,String strCodAlt){
       boolean blnResp =  false;                   
       try{
           int intCodItm = isItem(objZafParSis.getCodigoEmpresa(),strCodAlt);
           if (intCodItm==0)
               return false;
           String strSQL = "";
           strSQL = " Select count(co_itm) from tbm_inv " +
                    " where co_emp = " + intCodEmp +
                    " and co_itm = " + intCodItm + " and st_ser='S'";
           if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(),objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL) ==1)
               blnResp =  true;                   
           else
               blnResp =  false;                                  
       }catch (Exception e){
           objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
           blnResp =  false;
       }
       return blnResp;
   }
  
   
   
   
   
    public boolean movInventario_nuevo2(java.sql.Connection conMovInv, int intCodEmp,int intCodLoc, int intCodArt, int intCodBod, double dblArtCan, int intAcc, String strEstFisBod, String strMerIngEgr, String strTipIngEgr ){
       String strSQL,strMsgError="";
       boolean blnMov=false;
        try{//odbc,usuario,password              
            dblArtCan = dblArtCan * intAcc;   
              
            //****if(!isItemBodega(intCodArt,intCodBod,conMovInv))
            //******    creaItemBodega(intCodArt,intCodBod, conMovInv);
             if (objSisCon.existeStockItem_nueva2(conMovInv, intCodBod, intCodEmp,intCodLoc,intCodArt,java.lang.Math.abs(dblArtCan)) || dblArtCan>0){                                                
                strSQL  = "";                        
                strSQL  = " Update tbm_invBod set  nd_stkAct  = nd_stkAct + " + dblArtCan ;
                strSQL +=  " where co_emp = " + intCodEmp + " and co_bod = " + intCodBod + " and co_itm = " + intCodArt;                 

                
                 System.out.println("Elimina reg o anula  ==> "+strSQL);
              
                java.sql.PreparedStatement pstMovInvBod = conMovInv.prepareStatement(strSQL);               
                pstMovInvBod.executeUpdate();                    
                 blnMov = objSisCon.actualizarInventario_nuevo(conMovInv, intCodEmp, intCodLoc, intCodArt, dblArtCan, intCodBod,  strEstFisBod,  strMerIngEgr,  strTipIngEgr);
                 blnMov = true;  //*****************
            }else                //*********
                  blnMov = false; ///************* aqui era true
        }catch(java.sql.SQLException Evt){
           try{
                conMovInv.rollback();
                conMovInv.close();
           }catch(java.sql.SQLException Evts){
               objUti.mostrarMsgErr_F1(jfract, Evts);
           }
           blnMov = false;
           objUti.mostrarMsgErr_F1(jfract, Evt); 
        }catch(Exception Evt){
            blnMov = false;
            objUti.mostrarMsgErr_F1(jfract, Evt);
        }            
       return blnMov;
    }    
 
   
   
   
   
}
