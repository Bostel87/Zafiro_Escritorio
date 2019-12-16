/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafStkInv;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


/**
 *
 * @author sistemas3
 */
public class ZafStkInv {
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    
    private Statement stm;
    private ResultSet rst;
    
    private String strSQL;
    private int intCodEmp;
    private String strVersion="v0.1.5";
    private String strCodAltItmSinStk;
    
    private ArrayList arlDatItm;
    public static final int INT_ARL_COD_ITM_GRP=0;
    public static final int INT_ARL_COD_ITM_EMP=1;
    public static final int INT_ARL_COD_ITM_MAE=2;    
    public static final int INT_ARL_COD_LET_ITM=3;     
    public static final int INT_ARL_CAN_ITM=4;
    public static final int INT_ARL_COD_BOD_EMP=5;
    
    private int intTipMov;
    
    public ZafStkInv(ZafParSis obj){
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            intTipMov=0;
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(null, e);
        }
    }
    
    /**
     * Función que permite llamar a los métodos que realizarán la actualización de inventario
     * @param conexion: Parámetro recibido de la conexión para generar los SQL's
     * @param codigoEmpresa: Código de empresa donde se guardará el documento
     * @param codigoBodega: Código de bodega donde se ingresará/egresará la mercadería
     * @param indiceCampo: Parámetro que permitirá conocer cual es el nombre del campo que se debe actualizar
     * @param signoCampo:Parámetro que permite conocer el signo que debe aplicarse al campo (+ ó -)
     * @param intTipMov: Indica si el método debe ser o no ejecutado
     *                  <HTML>    
     *                      <BR> 0= El método no se debe ejecutar
     *                      <BR> 1= El método si se debe ejecutar
     *                  </HTML>
     * @param arlDatosItem:             <HTML>Información del item:
     *                                      <BR>Código del item de grupo
     *                                      <BR>Código del item de empresa
     *                                      <BR>Código del item maestro
     *                                      <BR>Código del item en letras
     *                                      <BR>Cantidad del item. <BR>  Si es por entrada de mercadería la cantidad se envía en positivo.
     *                                  </HTML>
     * @return true: Si se pudo efectuar la operación
     * <BR> false: Caso contrario
     */
    public boolean actualizaInventario(Connection conexion 
            , int codigoEmpresa, int indiceCampo, String signoCampo, int intTipoMovimiento, ArrayList arlDatosItem){
        boolean blnRes=false;
        intCodEmp=codigoEmpresa;
        intTipMov=intTipoMovimiento;        
        arlDatItm=arlDatosItem;
        
        if(conexion!=null){
            if(validaStkInv(conexion, intTipoMovimiento, signoCampo)){//por confirmaciones no actualizar nada en tbm_inv
                if(actualizaStkInv(conexion, intTipoMovimiento, signoCampo)){//por confirmaciones no actualizar nada en tbm_inv
                    if(actualizaStkInvBod(conexion, indiceCampo, signoCampo)){//cuando es por confirmaciones si actualiza : Depende del tipo de confirmación para actualiza el campo correcto
                        blnRes=true;
                    }
                    else
                        blnRes=false;

                }
                else
                    blnRes=false;
            }
            else
                blnRes=false;
        }

        

        return blnRes;
    }
    
    
    
    /**
     * Función que permite llamar a los métodos que realizarán la actualización del disponible de  inventario por bodega
     * @param conexion: Parámetro recibido de la conexión para generar los SQL's
     * @param codigoEmpresa: Código de empresa donde se guardará el documento
     * @param codigoBodega: Código de bodega donde se ingresará/egresará la mercadería
     * @param indiceCampo: Parámetro que permitirá conocer cual es el nombre del campo que se debe actualizar
     * @param signoCampo:Parámetro que permite conocer el signo que debe aplicarse al campo (+ ó -)
     * @param intTipMov: Indica si el método debe ser o no ejecutado
     *                  <HTML>    
     *                      <BR> 0= El método no se debe ejecutar
     *                      <BR> 1= El método si se debe ejecutar
     *                  </HTML>
     * @param arlDatosItem:             <HTML>Información del item:
     *                                      <BR>Código del item de grupo
     *                                      <BR>Código del item de empresa
     *                                      <BR>Código del item maestro
     *                                      <BR>Código del item en letras
     *                                      <BR>Cantidad del item. <BR>  Si es por entrada de mercadería la cantidad se envía en positivo.
     *                                  </HTML>
     * @return true: Si se pudo efectuar la operación
     * <BR> false: Caso contrario
     */
    public boolean actualizaDisponible(Connection conexion 
            , int codigoEmpresa, int indiceCampo, String signoCampo, int intTipoMovimiento, ArrayList arlDatosItem){
        boolean blnRes=false;
        intCodEmp=codigoEmpresa;
        intTipMov=intTipoMovimiento;        
        arlDatItm=arlDatosItem;
        
        if(actualizaDisInvBod(conexion, intTipMov, indiceCampo, signoCampo)){//cuando es por confirmaciones si actualiza : Depende del tipo de confirmación para actualiza el campo correcto
            blnRes=true;
        }
        else
            blnRes=false;
        

        return blnRes;
    }
    
    
    
    /**
     * Función que permite actualizar el stock en la tabla de inventarios
     * @param con: conexión
     * @param tipoMovimiento: indica si se debe ejecutar el proceso
     * @param signoCampo: indica el signo que se debe aplicar para realizar la operación
     * @return true: 
     * <BR> false: Caso contrario
     */
    private boolean actualizaStkInv(java.sql.Connection con, int tipoMovimiento, String signoCampo){
        boolean blnRes=true;
        
        String strSQLUpd="";
        try{
            if(tipoMovimiento==1){
                if(con!=null){
                    stm=con.createStatement();
                    for(int i=0; i<arlDatItm.size(); i++){
                        strSQL="";
                        strSQL+="UPDATE tbm_inv";
                        strSQL+=" SET nd_stkact=nd_stkact" + signoCampo + "" + (objUti.getStringValueAt(arlDatItm, i, INT_ARL_CAN_ITM));
                        strSQL+=" WHERE co_emp=" + intCodEmp + "";
                        strSQL+=" AND co_itm=" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_EMP) + "";
                        strSQL+=";";
                        strSQLUpd+=strSQL;
                    }
                    System.out.println("actualizaStkInv: " + strSQLUpd);
                    stm.executeUpdate(strSQLUpd);
                    stm.close();
                    stm=null;

                }
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
      
    /**
     * Función que permite obtener el nombre del campo que se desea actualizar
     * @param indiceNombreCampo 
     *          <HTML>
     *              <BR>  0: Actualiza en campo "nd_stkAct"
     *              <BR>  1: Actualiza en campo ""
     *              <BR>  2: Actualiza en campo ""
     *              <BR>  3: Actualiza en campo "nd_canBodIng"
     *              <BR>  4: Actualiza en campo "nd_canBodEgr"
     *              <BR>  5: Actualiza en campo "nd_canDesEntBod"
     *              <BR>  6: Actualiza en campo "nd_canDesEntCli"
     *              <BR>  7: Actualiza en campo "nd_canTra"
     *              <BR>  8: Actualiza en campo "nd_canRev"
     *              <BR>  9: Actualiza en campo "nd_canRes"
     *              <BR> 10: Actualiza en campo "nd_canDis"
     *              <BR> 11: Actualiza en campo "nd_canResVen"
     *          </HTML>
     * @return true: Si se pudo obtener el nombre del campo
     * <BR> false: Caso contrario
     */
    private String getNomCamInvBod(int indiceNombreCampo){
        String strNomCamAct="";
        switch(indiceNombreCampo){
            case 0:
                strNomCamAct="nd_stkAct";
                break;
            case 1:
                strNomCamAct="";
                break;
            case 2:
                strNomCamAct="";
                break;
            case 3:
                strNomCamAct="nd_canIngBod";
                break;
            case 4:
                strNomCamAct="nd_canEgrBod";
                break;
            case 5:
                strNomCamAct="nd_canDesEntBod";
                break;
            case 6:
                strNomCamAct="nd_canDesEntCli";
                break;
            case 7:
                strNomCamAct="nd_canTra";
                break;
            case 8:
                strNomCamAct="nd_canRev";
                break;
            case 9:
                strNomCamAct="nd_canRes";
                break;
            case 10:
                strNomCamAct="nd_canDis";
                break;
            case 11:
                strNomCamAct="nd_canResVen";
                break;
            default:
                strNomCamAct="";
                break;
        }
        return strNomCamAct;
    }
    
    
    /**
     * Función que permite actualizar el stock en la tabla de inventarios por bodega
     * @param indiceCampo : Indica el campo que se debe actualizar en la tabla
     * @param signoCampo  : Indica el signo que se debe aplicar para realizar la operación
     * @return true: Si se pudo realizar la operación
     * <BR> false: Caso contrario
     */
    private boolean actualizaStkInvBod(java.sql.Connection con,int indiceCampo, String signoCampo){
        boolean blnRes=true;
        String strNomCam="";
        String strSQLUpd="";
        try{
            if(con!=null){
                stm=con.createStatement();
                strNomCam=getNomCamInvBod(indiceCampo);
                if(!strNomCam.equals("")){
                    for(int i=0; i<arlDatItm.size(); i++){
                        strSQL="";
                        strSQL+="UPDATE tbm_invBod";
                        strSQL+=" SET " + strNomCam + "=(CASE WHEN " + strNomCam + " IS NULL THEN 0 ELSE " + strNomCam + " END)" + signoCampo + "" + (objUti.getStringValueAt(arlDatItm, i, INT_ARL_CAN_ITM)) + "";
                        strSQL+="  WHERE co_emp=" + intCodEmp + "";
                        strSQL+="  AND co_bod=" +  objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_BOD_EMP) + "";
                        strSQL+="  AND co_itm=" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_EMP) + "";
                        strSQL+=";";
                        strSQLUpd+=strSQL;
                    }
                    System.out.println("actualizaStkInvBod: " + strSQLUpd);
                    stm.executeUpdate(strSQLUpd);
                }
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Función que permite validar si el stock en la tabla de inventarios abastece el stock que se desea mover
     * @param con: conexión
     * @param tipoMovimiento: indica si se debe ejecutar el proceso - Cuando se decrementa
     * @param signoCampo: indica el signo que se debe aplicar para realizar la operación
     * @return true: 
     * <BR> false: Caso contrario
     */
    private boolean validaStkInv(java.sql.Connection con, int tipoMovimiento, String signoCampo){
        boolean blnRes=true;
        strCodAltItmSinStk="";
        try{
            if(tipoMovimiento==1){//si es para incrementar/decrementar se aplica, caso contrario no 
                if(con!=null){
                    stm=con.createStatement();
                    for(int i=0; i<arlDatItm.size(); i++){
                        strSQL="";
                        strSQL+="SELECT *FROM tbm_inv AS a1";
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                        strSQL+=" AND a1.co_itm=" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_EMP) + "";
                        strSQL+=" AND (a1.nd_stkact" + signoCampo + "" + (objUti.getStringValueAt(arlDatItm, i, INT_ARL_CAN_ITM)) + ")<0";//(a1.nd_stkAct-4)>=0
                        strSQL+=";";
                        
                        rst=stm.executeQuery(strSQL);
                        System.out.println("validaStkInv: " + strSQL);
                        if(rst.next()){//si es negativo
                            strCodAltItmSinStk=rst.getString("tx_codAlt");
                            return false;
                        }//no se cuenta con stock suficiente                        
                    }
                    
                    stm.close();
                    stm=null;
                }
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite validar si el stock en la tabla tbm_invBod abastece el stock que se desea mover
     * @param con: conexión
     * @param tipoMovimiento: indica si se debe ejecutar el proceso - Cuando se decrementa
     * @param signoCampo: indica el signo que se debe aplicar para realizar la operación
     * @return true: 
     * <BR> false: Caso contrario
     */
    public boolean validaStk_Dis_InvBod(java.sql.Connection con, int codigoEmpresa, ArrayList arreglo, int tipoMovimiento, String signoCampo, int indiceNombreCampo){
        boolean blnRes=true;
        String strNomCam="";
        try{
            if(tipoMovimiento==1){//si es para incrementar/decrementar se aplica, caso contrario no 
                if(con!=null){
                    stm=con.createStatement();
                    strNomCam=getNomCamInvBod(indiceNombreCampo);
                    if(!strNomCam.equals("")){
                        for(int i=0; i<arreglo.size(); i++){
                            strSQL="";
                            strSQL+="SELECT *FROM tbm_invBod AS a1";
                            strSQL+=" WHERE a1.co_emp=" + codigoEmpresa + "";
                            strSQL+=" AND a1.co_itm=" + objUti.getStringValueAt(arreglo, i, INT_ARL_COD_ITM_EMP) + "";
                            strSQL+=" AND a1.co_bod=" + objUti.getStringValueAt(arreglo, i, INT_ARL_COD_BOD_EMP) + "";
                            //strSQL+=" AND (a1.nd_stkact" + signoCampo + "(" + (objUti.getStringValueAt(arreglo, i, INT_ARL_CAN_ITM)) + "))<0";//(a1.nd_stkAct-4)>=0
                            strSQL+=" AND (a1." + strNomCam + "" + signoCampo + "(" + (objUti.getStringValueAt(arreglo, i, INT_ARL_CAN_ITM)) + "))<0";//(a1.nd_stkAct-4)>=0
                            strSQL+=";";
                            rst=stm.executeQuery(strSQL);
                            System.out.println("validaStkInvBod: " + strSQL);
                            if(rst.next()){//si es negativo

                                System.out.println("co_emp: " + codigoEmpresa);
                                System.out.println("co_itm: " + objUti.getStringValueAt(arreglo, i, INT_ARL_COD_ITM_EMP));
                                System.out.println("co_bod: " + objUti.getStringValueAt(arreglo, i, INT_ARL_COD_BOD_EMP));
                                System.out.println("signoCampo: " + signoCampo);
                                System.out.println("nd_stkact: " + rst.getString("nd_stkact"));
                                System.out.println("nd_stkact: " + (objUti.getStringValueAt(arreglo, i, INT_ARL_CAN_ITM)));
                                System.out.println("es negativo");
                                return false;
                            }//no se cuenta con stock suficiente                        
                        }
                    }
                    
                    stm.close();
                    stm=null;
                }
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
  
    /**
     * Función que permite actualizar el stock en la tabla de inventarios
     * @param con: conexión
     * @param tipoMovimiento: indica si se debe ejecutar el proceso
     * @param signoCampo: indica el signo que se debe aplicar para realizar la operación
     * @return true: 
     * <BR> false: Caso contrario
     */
    private boolean actualizaDisInvBod(java.sql.Connection con, int tipoMovimiento, int indiceCampo, String signoCampo){//
        boolean blnRes=true;        
        String strSQLUpd="";
        String strNomCam="";
        try{
            if(tipoMovimiento==1){
                if(con!=null){
                    stm=con.createStatement();
                    strNomCam=getNomCamInvBod(indiceCampo);
                    if(!strNomCam.equals("")){
                        for(int i=0; i<arlDatItm.size(); i++){
                            strSQL="";
                            strSQL+="UPDATE tbm_invBod";
                            strSQL+=" SET " + strNomCam + "=(CASE WHEN " + strNomCam + " IS NULL THEN 0 ELSE " + strNomCam + " END)" + signoCampo + "" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_CAN_ITM) + "";
                            strSQL+="  WHERE co_emp=" + intCodEmp + "";
                            strSQL+="  AND co_bod=" +  objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_BOD_EMP) + "";
                            strSQL+="  AND co_itm=" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_EMP) + "";
                            strSQL+=";";
                            strSQLUpd+=strSQL;
                        }
                        System.out.println("actualizaDisInvBod: " + strSQLUpd);
                        stm.executeUpdate(strSQLUpd);
                    }
                    stm.close();
                    stm=null;

                }
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Metodo para obtener el codigo del Item en la empresa indicada.
     * @param CodigoEmpresa
     * @param CodigoItemMaestro
     * @return 
     */
    public int getCodItmEmp(int CodigoEmpresa, int CodigoItemMaestro){
        int intCodItmGrp=0;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT co_itm \n";
                strSQL+=" FROM tbm_equInv as x1 \n";
                strSQL+=" WHERE x1.co_emp="+CodigoEmpresa;
                strSQL+=" AND x1.co_itmMae =" +CodigoItemMaestro;
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCodItmGrp=rstLoc.getInt("co_itm");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            intCodItmGrp=-1;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            intCodItmGrp=-1;
        }
        return intCodItmGrp;
    }

    /**
     * Metodo para obtener el disponible de un item por empresa y bodega grupo.
     * @param con
     * @param CodigoItemMaestro Código Maestro del ítem.
     * @param CodigoBodegaGrupo Código de Bodega en el Grupo, bodega de la que se desea obtener el disponible.
     * @param CodigoEmpresa Código de Empresa del ítem, empresa de la que se desea obtener el disponible.
     * @return 
     */
     public double getDisItmEmp(java.sql.Connection con, int CodigoItemMaestro, int CodigoBodegaGrupo,  int CodigoEmpresa){
        double dblRes=0.00;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(con!=null){
                stmLoc=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a.co_bodGrp, a.co_itmMae,SUM(a.nd_canDis) as nd_canDis,a.tx_codAlt \n";
                strSQL+=" FROM( \n";
                strSQL+="       SELECT a3.co_itmMae, a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_canDis, a4.co_empGrp, a4.co_bodGrp, a2.tx_codAlt \n";
                strSQL+="       FROM tbm_invBod AS a1   \n";
                strSQL+="       INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSQL+="       INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                strSQL+="       INNER JOIN tbr_bodEmpBodGrp as a4 ON (a1.co_emp=a4.co_emp AND a1.co_bod=a4.co_bod) \n";
                strSQL+="       WHERE a3.co_itmMae IN (  "+CodigoItemMaestro+"  ) AND a3.co_emp IN ("+CodigoEmpresa+") \n";
                strSQL+=" ) as a \n";
                strSQL+=" WHERE a.co_bodGrp = ( "+CodigoBodegaGrupo+" ) \n";
                strSQL+=" GROUP BY a.co_bodGrp, a.co_itmMae,a.tx_codAlt   \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    dblRes=rstLoc.getDouble("nd_canDis");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            dblRes=-1;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            dblRes=-1;
        }
        return dblRes;
     }

     /**
      * Metodo para obtener el stock de un item por grupo y bodega de grupo.
      * El stock retornado es la sumatoria del stock de todas las empresas existentes.
      * @param con
      * @param CodigoItemMaestro Código Maestro del ítem.
      * @param CodigoBodegaGrupo Código de Bodega en el Grupo, bodega de la que se desea obtener el stock.
      * @return 
      */
     public double getStkItmGrp(java.sql.Connection con, int CodigoItemMaestro, int CodigoBodegaGrupo){
        double dblRes=0.00;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(con!=null){
                stmLoc=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a.co_bodGrp, a.co_itmMae,SUM(a.nd_stkAct) as nd_canStk,a.tx_codAlt \n";
                strSQL+=" FROM( \n";
                strSQL+="       SELECT a3.co_itmMae, a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_stkAct, a4.co_empGrp, a4.co_bodGrp,a2.tx_codAlt \n";
                strSQL+="       FROM tbm_invBod AS a1   \n";
                strSQL+="       INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSQL+="       INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                strSQL+="       INNER JOIN tbr_bodEmpBodGrp as a4 ON (a1.co_emp=a4.co_emp AND a1.co_bod=a4.co_bod) \n";
                strSQL+="       WHERE a3.co_itmMae IN (  "+CodigoItemMaestro+"  )  \n";
                strSQL+=" ) as a \n";
                strSQL+=" WHERE a.co_bodGrp = ( "+CodigoBodegaGrupo+" ) \n";
                strSQL+=" GROUP BY a.co_bodGrp, a.co_itmMae,a.tx_codAlt   \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    dblRes=rstLoc.getDouble("nd_canStk");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){            
            objUti.mostrarMsgErr_F1(null, e);
            dblRes=-1;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            dblRes=-1;
        }
        return dblRes;
     }

     /**
      * Metodo para obtener el stock de un item por empresa y bodega de grupo.
      * @param con
      * @param CodigoItemMaestro Código Maestro del ítem.
      * @param CodigoBodegaGrupo Código de Bodega en el Grupo, bodega de la que se desea obtener el stock.
      * @param CodigoEmpresa Código de Empresa del ítem, empresa de la que se desea obtener el stock.
      * @return 
      */
     public double getStkItmEmp(java.sql.Connection con, int CodigoItemMaestro, int CodigoBodegaGrupo,  int CodigoEmpresa){
        double dblRes=0.00;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(con!=null){
                stmLoc=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a.co_bodGrp, a.co_itmMae,SUM(a.nd_stkAct) as nd_canStk,a.tx_codAlt \n";
                strSQL+=" FROM( \n";
                strSQL+="       SELECT a3.co_itmMae, a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_stkAct, a4.co_empGrp, a4.co_bodGrp,a2.tx_codAlt \n";
                strSQL+="       FROM tbm_invBod AS a1   \n";
                strSQL+="       INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSQL+="       INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                strSQL+="       INNER JOIN tbr_bodEmpBodGrp as a4 ON (a1.co_emp=a4.co_emp AND a1.co_bod=a4.co_bod) \n";
                strSQL+="       WHERE a3.co_itmMae IN (  "+CodigoItemMaestro+"  ) AND a3.co_emp IN ("+CodigoEmpresa+") \n";
                strSQL+=" ) as a \n";
                strSQL+=" WHERE a.co_bodGrp = ( "+CodigoBodegaGrupo+" ) \n";
                strSQL+=" GROUP BY a.co_bodGrp, a.co_itmMae,a.tx_codAlt   \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    dblRes=rstLoc.getDouble("nd_canStk");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            dblRes=-1;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            dblRes=-1;
        }
        return dblRes;
     }

     /**
      * Metodo para obtener el codigo del Item en Grupo
      * @param CodigoEmpresa
      * @param CodigoItem
      * @return 
      */
    public int getCodItmGrp(int CodigoEmpresa, int CodigoItem){
        int intCodItmGrp=0;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT co_itm \n";
                strSQL+=" FROM tbm_equInv as x1 \n";
                strSQL+=" WHERE x1.co_itmMae = ( \n";
                strSQL+="                        select co_itmMae  \n";
                strSQL+="                        from tbm_Equinv as a1 \n";
                strSQL+="                        where co_emp="+CodigoEmpresa+" and co_itm="+CodigoItem+")  \n";
                strSQL+=" and x1.co_emp="+objParSis.getCodigoEmpresaGrupo()+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCodItmGrp=rstLoc.getInt("co_itm");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            intCodItmGrp=-1;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            intCodItmGrp=-1;
        }
        return intCodItmGrp;
    }

    /**
     * Metodo para obtener el codigo maestro del item
     * @param CodigoEmpresa
     * @param CodigoItem
     * @return 
     */
    public int getCodMaeItm(int CodigoEmpresa, int CodigoItem){
        int intCodItmMae=0;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT x1.co_itmMae \n";
                strSQL+=" FROM tbm_equInv as x1 \n";
                strSQL+=" WHERE x1.co_emp="+CodigoEmpresa+" and x1.co_itm="+CodigoItem+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCodItmMae=rstLoc.getInt("co_itmMae");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            intCodItmMae=-1;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            intCodItmMae=-1;
        }
        return intCodItmMae;
    }
         
    /**
     * Metodo para obtener el codigo de 3 letras del item XLD
     * @param CodigoEmpresa
     * @param CodigoItem
     * @return 
     */
    public String getCodLetItm(int CodigoEmpresa, int CodigoItem){
        String strCodLetItm="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT CASE WHEN tx_codAlt2 IS NULL THEN tx_codAlt ELSE tx_codAlt2 END AS tx_codAlt2 \n";
                strSQL+=" FROM tbm_inv as x1 \n";
                strSQL+=" WHERE x1.co_emp="+CodigoEmpresa+" AND x1.co_itm="+CodigoItem+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    strCodLetItm=rstLoc.getString("tx_codAlt2");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            strCodLetItm="ERROR";
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            strCodLetItm="ERROR";
        }
        return strCodLetItm;
    }
     
     
    /**
     * Función que permite obtener el código alterno del item sin stock suficiente
     * @return String del código alterno del item
     */
    public String getStrCodAltItmSinStk() {
        return strCodAltItmSinStk;
    }
     
     
     
     
    
    
}
