/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafImp;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import Librerias.ZafUtil.ZafUtil;

/**
 *
 * @author Sistemas3
 */
public class ZafPagExt
{
    //Variables generales.
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafAsiDia objAsiDia;
    private Statement stm;
    private ResultSet rst;
    private java.awt.Component cmpPad;
    private ZafImp objZafImp;
    private java.util.Date datFecAux;
    private String STR_NUM_DOC_ORD_PAG_EXT;//Número de documento de OPIMPE
    private ZafSegMovInv objSegMovInv;
    private Object objCodSegDocPagExt;   
    private ZafPagImp objPagImp;    
    private int intTipErr;
    private String strSQL, strAux;

    //Constantes
    //DATOS DE ORDEN DE PAGO AL EXTERIOR
    private int INT_COD_EMP_ORD_PAG_EXT;    //Código de empresa de OPIMPE
    private int INT_COD_LOC_ORD_PAG_EXT;    //Código de local de OPIMPE
    private int INT_COD_TIP_DOC_ORD_PAG_EXT;//Código de tipo de dcoumento de OPIMPE
    private int INT_COD_DOC_ORD_PAG_EXT;    //Código de documento de OPIMPE
    private BigDecimal BGD_VAL_DXP_EXT;     //Valor de documento de OPIMPE
    private int INT_COD_CTA_BCO;
    
    //DATOS DE OTROS MOVIMIENTOS BANCARIOS
    private int INT_COD_EMP_OTR_MOV_BAN;    //Código de empresa de TRANSFERENCIA AL EXTERIOR
    private int INT_COD_LOC_OTR_MOV_BAN;    //Código de local de TRANSFERENCIA AL EXTERIOR
    private int INT_COD_TIP_DOC_OTR_MOV_BAN;//Código de tipo de dcoumento de TRANSFERENCIA AL EXTERIOR
    private int INT_COD_DOC_OTR_MOV_BAN;    //Código de documento de TRANSFERENCIA AL EXTERIOR
    
    //DATOS DE CABECERA DE LA TRANSFERENCIA
    private int INT_COD_EMP_TRS;        //Código de la empresa de la transferencia
    private int INT_COD_LOC_TRS;        //Código del local de la transferencia
    private int INT_COD_TIP_DOC_TRS;    //Código del tipo de documento de la transferencia
    private int INT_COD_DOC_TRS;        //Código del documento de la transferencia       
    private int INT_NUM_DOC_TRS;        //Número del documento de la transferencia
    private int INT_ULT_REG_CAR_TRA_BAN;//Último registro de la carta de transferencia bancaria
    
    private int INT_COD_CTA_BAN_EGR;    //Código de la cuenta bancaria del egreso
    private int INT_COD_CTA_BAN_ING;    //Código de la cuenta bancaria del ingreso
    private Date DAT_FEC_CAR;           //Fecha de la carta
    private BigDecimal BIG_VAL_CAR;     //Valor de la carta
    private BigDecimal BIG_VAL_SUB;     //Valor del SubTotal
    private BigDecimal BIG_VAL_IVA;     //Valor del IVA.
    private BigDecimal BIG_VAL_TOT;     //Valor del Total del documento

    private Vector vecRegDetTrs, vecDatDetTrs;
    private int INT_VEC_TRS_LIN=0;
    private int INT_VEC_TRS_COD_CTA=1;
    private int INT_VEC_TRS_NUM_CTA=2;
    private int INT_VEC_TRS_BUT=3;
    private int INT_VEC_TRS_NOM_CTA=4;
    private int INT_VEC_TRS_DEB=5;
    private int INT_VEC_TRS_HAB=6;
    private int INT_VEC_TRS_REF=7;
    private int INT_VEC_TRS_EST_CON=8;
    
    //DATOS DEL PEDIDO RELACIONADO AL DXP
    private int INT_COD_EMP_PED_REL;//Codigo de empresa del pedido relacionado al DxP del exterior
    private int INT_COD_LOC_PED_REL;//Codigo de local del pedido relacionado al DxP del exterior
    private int INT_COD_TIP_DOC_PED_REL;//Codigo de tipo de dcoumento del pedido relacionado al DxP del exterior
    private int INT_COD_DOC_PED_REL;//Codigo de documento del pedido relacionado al DxP del exterior
    private int INT_COD_EXP_PED_REL;//Codigo de Exportador del pedido relacionado al DxP del exterior
    
    //DATOS DE DETALLE DE LA TRANSFERENCIA
    private ArrayList arlRegDetTrs, arlDatDetTrs;
    private int INT_ARL_DOC_PAG_EXT_COD_EMP=0;
    private int INT_ARL_DOC_PAG_EXT_COD_LOC=1;
    private int INT_ARL_DOC_PAG_EXT_COD_TIP_DOC=2;
    private int INT_ARL_DOC_PAG_EXT_COD_DOC=3;
    private int INT_ARL_DOC_PAG_EXT_COD_REG=4;
    private int INT_ARL_DOC_PAG_EXT_NUM_DOC=5;
    private int INT_ARL_DOC_PAG_EXT_FEC_DOC=6;
    private int INT_ARL_DOC_PAG_EXT_COD_CLI=7;
    private int INT_ARL_DOC_PAG_EXT_NUM_RUC=8;
    private int INT_ARL_DOC_PAG_EXT_NOM_CLI=9;
    private int INT_ARL_DOC_PAG_EXT_VAL_TOT=10;
    private int INT_ARL_DOC_PAG_EXT_VAL_COM=11;
    private int INT_ARL_DOC_PAG_EXT_VAL_INT=12;
    private int INT_ARL_DOC_PAG_EXT_EST_REG=13;
    private int INT_ARL_DOC_PAG_EXT_DET_DSC=14;
    private int INT_ARL_DOC_PAG_EXT_DET_CAN=15;
    private int INT_ARL_DOC_PAG_EXT_DET_PRE_UNI=16; 
    private int INT_ARL_DOC_PAG_EXT_DET_TOT=17; 
    private int INT_ARL_DOC_PAG_EXT_COD_EMP_INS_PED=18;  
    private int INT_ARL_DOC_PAG_EXT_COD_LOC_INS_PED=19;  
    private int INT_ARL_DOC_PAG_EXT_COD_TIP_DOC_INS_PED=20;
    private int INT_ARL_DOC_PAG_EXT_COD_DOC_INS_PED=21;
    private int INT_ARL_DOC_PAG_EXT_COD_REG_CAR_PAG=22;
    private int INT_ARL_DOC_PAG_EXT_NOM_CAR_PAG=23;
    private int INT_ARL_DOC_PAG_EXT_TIP_CAR_PAG=24;
    private int INT_ARL_DOC_PAG_EXT_COD_CTA_ACT_INS_PED=25;
    private int INT_ARL_DOC_PAG_EXT_COD_CTA_PAS_INS_PED=26;
    private int INT_ARL_DOC_PAG_EXT_INS_PED=27;
    private int INT_ARL_DOC_PAG_EXT_COD_CTA_ISD=28;
    
    
    
    
    public final int INT_COD_IMP_ISD=5;
    public final int INT_COD_CAR_PAG_ISD_CRE=23;
    public final int INT_COD_CAR_PAG_ISD_GAS=26;
    
    public final int INT_COD_CAR_PAG_INT_FAC=25;  //Código de cargo a pagar: Interes en Factura.
    
    private BigDecimal bgdValBcoTrsExt;
//    private BigDecimal bgdValISDCreTriTrsExt;
//    private BigDecimal bgdValISDGtoTrsExt;

    private int INT_COD_SEC_DOC_GEN_OTR_MOV_BAN;
    
    private String strVer=" v0.2.2";
    
    /** 
     * Crea una nueva instancia de la clase ZafPagExt.
     * Permite generar sólo el documento de Transferencia bancaria del Exterior TRBAEX
     * Este método es llamado desde el programa Autorización de Pagos y Bancos ó Autorización de Bancos
     */
    public ZafPagExt(ZafParSis obj, java.awt.Component parent)
    {
        //super(parent, true);
        //
        try{
            objParSis=(ZafParSis)obj.clone();
            cmpPad=parent;
            objUti = new ZafUtil();
            objAsiDia=new ZafAsiDia(objParSis);
            objSegMovInv=new ZafSegMovInv(objParSis, cmpPad);
            objZafImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(cmpPad));
            vecDatDetTrs=new Vector();
            arlDatDetTrs=new ArrayList();
            objZafImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(cmpPad));
            objPagImp = new ZafPagImp(objParSis, cmpPad);  //Pagos de importaciones
            //System.out.println("ZafPagExt version: " + strVer);
        }
        catch (CloneNotSupportedException e){
            System.out.println("ZafPagExt: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
    }
    
    /**
     * Esta función devuelve el local predeterminado de la empresa seleccionada
     * @return entero: si devuelve el local predeterminado
     * <BR>false: En el caso contrario.
     */
    private int getCodigoLocalPredeterminado(Connection conexion, int codigoEmpresa){
        int intCodLocPre=-1;
        Statement stmLocPre;
        ResultSet rstLocPre;
        try{
            if(conexion!=null){
                stmLocPre=conexion.createStatement();
                strSQL="";
                strSQL+=" SELECT co_loc FROM tbm_loc";
                strSQL+=" WHERE co_emp=" + codigoEmpresa + "";
                strSQL+=" AND st_reg='P'";
                rstLocPre=stmLocPre.executeQuery(strSQL);
                if(rstLocPre.next()){
                    intCodLocPre=rstLocPre.getInt("co_loc");
                }
                stmLocPre.close();
                stmLocPre=null;
                rstLocPre.close();
                rstLocPre=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return intCodLocPre;
    }
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podróa utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(cmpPad,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(cmpPad,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podróa utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifóquelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(cmpPad,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }    
    
    /**
     * Función que permite obtener el número de Orden de Pago del Exterior OPIMPE
     * @return true Si se puede realizar la operación
     * <BR> false Caso contrario
     */
    private boolean getDatOrdPagExt(Connection conexion){
        boolean blnRes=true;
        try{
            if(conexion!=null){
                stm=conexion.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.ne_numDoc, a1.nd_valCar, a2.fe_venChqAutPag, a1.nd_sub, a1.nd_valIva, a1.nd_tot, a1.co_ctaBanExp";
                strSQL+=" FROM tbm_cabMovInv AS a1 INNER JOIN tbm_pagMovInv AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" WHERE a1.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + "";
                strSQL+=" AND a1.co_loc=" + INT_COD_LOC_ORD_PAG_EXT + "";
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_ORD_PAG_EXT + "";
                strSQL+=" AND a1.co_doc=" + INT_COD_DOC_ORD_PAG_EXT + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    STR_NUM_DOC_ORD_PAG_EXT=rst.getString("ne_numDoc");
                    BIG_VAL_CAR=rst.getBigDecimal("nd_valCar");
                    DAT_FEC_CAR=rst.getDate("fe_venChqAutPag");
                    BIG_VAL_SUB=rst.getBigDecimal("nd_sub");
                    BIG_VAL_IVA=rst.getBigDecimal("nd_valIva");
                    BIG_VAL_TOT=rst.getBigDecimal("nd_tot");
                    INT_COD_CTA_BAN_ING=rst.getInt("co_ctaBanExp");
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg(Connection conexion){
        boolean blnRes=false;
        try{
            if (conexion!=null){
                if(getDatOrdPagExt(conexion)){
                    if(getPedidoRelacionadoDxP(conexion)){
                        if(getPreDocDxPExt(conexion)){
                            if(generaAsiDiaTRBAEX(conexion)){
                                if (insertarCabOtrMovBan(conexion)){
                                    if (insertarDetOtrMovBan(conexion)){
                                        if(generaTransferBancariaGrupo(conexion)){
                                            if(insertarCabSegMovInv(conexion)){
                                                //No se guarda en tabla de seguimiento porque no existe relacion de campos en tbm_cabSegMovInv entre -tbm_cabMovInv y tbm_cabOtrMovBan -
    //                                            if(objSegMovInv.setSegMovInvCompra(conexion, objCodSegDocPagExt, 5, INT_COD_EMP_ORD_PAG_EXT, INT_COD_LOC_ORD_PAG_EXT, INT_COD_TIP_DOC_ORD_PAG_EXT, INT_COD_DOC_ORD_PAG_EXT, null, 8, INT_COD_EMP_TRS, INT_COD_LOC_TRS, INT_COD_TIP_DOC_TRS, INT_COD_DOC_TRS, null)){
                                                    if(objZafImp.setCampoTabla(conexion, INT_COD_EMP_ORD_PAG_EXT, INT_COD_LOC_ORD_PAG_EXT, INT_COD_TIP_DOC_ORD_PAG_EXT, INT_COD_DOC_ORD_PAG_EXT, "tbm_cabMovInv", "st_reg", "'A'")){
                                                        if(objZafImp.setCampoTabla(conexion, "co_emp", "co_loc", "co_tipDoc", "co_dia", INT_COD_EMP_ORD_PAG_EXT, INT_COD_LOC_ORD_PAG_EXT, INT_COD_TIP_DOC_ORD_PAG_EXT, INT_COD_DOC_ORD_PAG_EXT, "tbm_cabDia", "st_reg", "'A'")){
                                                            if(actualizaTbmPagMovInv(conexion, INT_COD_EMP_ORD_PAG_EXT, INT_COD_LOC_ORD_PAG_EXT, INT_COD_TIP_DOC_ORD_PAG_EXT, INT_COD_DOC_ORD_PAG_EXT)){
                                                                blnRes=true;
                                                            }
                                                            else{
                                                                conexion.rollback();    intTipErr=10;
                                                            }
                                                        }
                                                        else{
                                                            conexion.rollback();    intTipErr=9;
                                                        }
                                                    }
                                                    else{
                                                        conexion.rollback();    intTipErr=8;
                                                    }
    //                                            }
    //                                            else
    //                                                conexion.rollback();
                                            }
                                            else{
                                                conexion.rollback();    intTipErr=7;
                                            }
                                        }
                                        else{
                                            conexion.rollback();    intTipErr=6;
                                        }
                                    }
                                    else{
                                        conexion.rollback();    intTipErr=5;
                                    }
                                }
                                else{
                                    conexion.rollback();     intTipErr=4;
                                }
                            }
                            else {
                                conexion.rollback();     intTipErr=3;                
                            }
                        }
                        else {
                            conexion.rollback();     intTipErr=2;                
                        }
                    }
                    else {
                        conexion.rollback();     intTipErr=1;                
                    }
                } 
                else {
                    conexion.rollback();     intTipErr=0;                
                }                    
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     * @param strMsg
     */
    public void mostrarMsgErr()
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit, strMsg="";
        strTit="Mensaje del sistema Zafiro";
        switch(intTipErr){
            case 0:
                strMsg="Error al obtener datos del documento a pagar.";
                break;      
            case 1:
                strMsg="Error al obtener datos del pedido.";
                break;   
            case 2:   
                strMsg="Error al obtener numeraciones para el proceso.";
                break;                
            case 3:   
                strMsg="Error al generar arreglo con datos para la transferencia al exterior.";
                break;
            case 4:
                strMsg="Error al generar Transferencia Bancaria Grupo: Cabecera.";
                break;
            case 5:   
                strMsg="Error al generar Transferencia Bancaria Grupo: Detalle.";
                break;                
            case 6:
                strMsg="Error al generar asiento de diario TRBAEX.";
                break;  
            case 7:
                strMsg="Error al guardar en seguimiento.";
                break;           
            case 8:
                strMsg="Error al actualizar estado de registro de DxP";
                break;                      
            case 9:
                strMsg="Error al actualizar estado de registro de TRBAEX";
                break;                   
            case 10:
                strMsg="Error al actualizar abono en los pagos del Documento por Pagar.";
                break;                    
        }
        oppMsg.showMessageDialog(cmpPad,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }       
    
    /**
     * Función que permite obtener los datos del documento por pagar al exterior
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean getPreDocDxPExt(Connection conexion){
        boolean blnRes=true;
        try{
            if(conexion!=null){
                stm=conexion.createStatement();
                
                //CODIGO DE DOCUMENTO
                strSQL ="";
                strSQL+=" SELECT MAX(a1.co_dia)+1 AS co_dia";
                strSQL+=" FROM tbm_cabDia AS a1";
                strSQL+=" WHERE a1.co_emp=" + INT_COD_EMP_TRS;
                strSQL+=" AND a1.co_loc=" + INT_COD_LOC_TRS;
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_TRS + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    INT_COD_DOC_TRS=rst.getInt("co_dia");
                if (INT_COD_DOC_TRS==-1)
                    return false;

                //NUMERO DE DOCUMENTO
                strSQL ="";
                strSQL+=" SELECT (a1.ne_ultDoc)+1 AS tx_numDia";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + INT_COD_EMP_TRS;
                strSQL+=" AND a1.co_loc=" + INT_COD_LOC_TRS;
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_TRS + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    INT_NUM_DOC_TRS=rst.getInt("tx_numDia");
                if (INT_NUM_DOC_TRS==-1)
                    return false;

                //CODIGO DE REGISTRO
                strSQL ="";
                strSQL+=" SELECT MAX(a1.co_reg)+1 AS co_reg";
                strSQL+=" FROM tbm_cartrabandia AS a1";
                strSQL+=" WHERE a1.co_emp=" + INT_COD_EMP_TRS;
                strSQL+=" AND a1.co_loc=" + INT_COD_LOC_TRS;
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_TRS + "";
                strSQL+=" AND a1.co_dia=" + INT_COD_DOC_TRS + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    INT_ULT_REG_CAR_TRA_BAN=rst.getInt("co_reg");
                if (INT_ULT_REG_CAR_TRA_BAN==-1)
                    return false;

                //CODIGO DE SECUENCIA
                strSQL ="";
                strSQL+=" SELECT MAX(a1.co_sec)+1 AS co_sec";
                strSQL+=" FROM tbm_docgenotrmovban AS a1";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    INT_COD_SEC_DOC_GEN_OTR_MOV_BAN=rst.getInt("co_sec");
                if (INT_COD_SEC_DOC_GEN_OTR_MOV_BAN==-1)
                    return false;

                //Obtener código de documento
                strSQL ="";
                strSQL+=" SELECT MAX(a1.co_doc)+1 AS co_doc";
                strSQL+=" FROM tbm_docgenotrmovban AS a1";
                strSQL+=" WHERE a1.co_emp=" + INT_COD_EMP_OTR_MOV_BAN + "";
                strSQL+=" AND a1.co_loc=" + INT_COD_LOC_OTR_MOV_BAN + "";
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_OTR_MOV_BAN + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    INT_COD_DOC_OTR_MOV_BAN=rst.getInt("co_doc");
                if (INT_COD_DOC_OTR_MOV_BAN==-1)
                    return false;

//                /*CUENTA DE INGRESO - ES LA CUENTA DEL PROVEEDOR DEL EXTERIOR*/
//                strSQL="";
//                strSQL+=" SELECT a1.co_emp, a1.co_ctaBan, a1.co_ctaExp, a1.co_ban AS co_banVar";
//                strSQL+=" FROM tbm_ctaBan AS a1";
//                strSQL+=" WHERE a1.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + "";
//                strSQL+=" AND a1.co_ctaExp=" + INT_COD_EXP_PED_REL + "";
//                System.out.println("SQL - INT_COD_CTA_BAN_ING: " + strSQL);
//                rst=stm.executeQuery(strSQL);
//                if(rst.next())
//                    INT_COD_CTA_BAN_ING=rst.getInt("co_ctaBan");
                
                //CUENTA DE EGRESO - ES LA CUENTA DEL PROVEEDOR DEL EXTERIOR
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_ctaBan, a1.co_ctaExp, a1.co_ban AS co_banVar";
                strSQL+=" FROM tbm_ctaBan AS a1";
                strSQL+=" WHERE a1.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + "";
                strSQL+=" AND a1.co_cta=" + INT_COD_CTA_BCO + ""; //tbm_plaCta.co_cta
                System.out.println("SQL - INT_COD_CTA_BAN_EGR: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    INT_COD_CTA_BAN_EGR=rst.getInt("co_ctaBan");//cuenta bancaria del banco tbm_ctaBan y no de tbm_plaCta
                
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCabOtrMovBan(Connection conexion)
    {
        boolean blnRes=true;
        int intCodUsr, intUltNumDoc=-1;
        try{
            if (conexion!=null){
                stm=conexion.createStatement();
                intCodUsr=objParSis.getCodigoUsuario();
                //Obtener el código del último registro.
                strSQL ="";
                strSQL+=" SELECT MAX(a1.co_doc)+1 AS co_doc";
                strSQL+=" FROM tbm_cabOtrMovBan AS a1";
                strSQL+=" WHERE a1.co_emp=" + INT_COD_EMP_OTR_MOV_BAN + "";
                strSQL+=" AND a1.co_loc=" + INT_COD_LOC_OTR_MOV_BAN + "";
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_OTR_MOV_BAN + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next()) {
                    INT_COD_DOC_OTR_MOV_BAN=rst.getInt("co_doc");
                }
                rst.close();
                rst=null;                
                
                //Obtener el último número de documento
                strSQL ="";
                strSQL+=" SELECT (a1.ne_ultDoc)+1 AS ne_ultDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + INT_COD_EMP_OTR_MOV_BAN + "";
                strSQL+=" AND a1.co_loc=" + INT_COD_LOC_OTR_MOV_BAN + "";
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_OTR_MOV_BAN + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next()) {
                    intUltNumDoc=rst.getInt("ne_ultDoc");
                }
                rst.close();
                rst=null;                     
                
                //Obtener la fecha del servidor.
                datFecAux=null;
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" INSERT INTO tbm_cabOtrMovBan (";
                strSQL+="     co_emp, co_loc, co_tipDoc, co_doc, fe_doc, ne_numDoc, co_mnu";
                strSQL+="   , st_imp, st_pro, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod";
                strSQL+="   , co_usrIng, co_usrMod, tx_comIng, tx_comUltMod";
                strSQL+=" )";
                strSQL+=" VALUES (";
                strSQL+="  " + INT_COD_EMP_OTR_MOV_BAN + "";     //co_emp
                strSQL+=", " + INT_COD_LOC_OTR_MOV_BAN + "";     //co_loc
                strSQL+=", " + INT_COD_TIP_DOC_OTR_MOV_BAN + ""; //co_tipDoc
                strSQL+=", " + INT_COD_DOC_OTR_MOV_BAN + "";     //co_doc
                strSQL+=", '" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_doc
                strSQL+=", " + objUti.codificar(intUltNumDoc,2); //ne_numDoc  
                strSQL+=", " + objParSis.getCodigoMenu();        //co_mnu
                strSQL+=", 'N'";//st_imp
                strSQL+=", 'C'";//st_pro - Incompleto
                strSQL+=", 'Pago al Exterior Automático : " + intUltNumDoc + " - " + objUti.formatearFecha(datFecAux,"dd/MM/yyyy")  + "'"; //tx_obs1
                strSQL+=", 'Orden de Pago Exterior: " + STR_NUM_DOC_ORD_PAG_EXT + "'";//tx_obs2
                strSQL+=", 'A'";//st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'"; //fe_ing
                strSQL+=", '" + strAux + "'"; //fe_ultMod
                strSQL+=", " + intCodUsr; //co_usrIng
                strSQL+=", " + intCodUsr; //co_usrMod
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_coming
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_comultmod
                strSQL+=");";
                //Incrementa el número de documento en "tbm_cabOtrMovBan"
                strSQL+=" UPDATE tbm_cabTipDoc";
                strSQL+=" SET ne_ultDoc=ne_ultDoc+1";
                strSQL+=" WHERE co_emp=" + INT_COD_EMP_OTR_MOV_BAN + "";
                strSQL+=" AND co_loc=" + INT_COD_LOC_OTR_MOV_BAN + "";
                strSQL+=" AND co_tipDoc=" + INT_COD_TIP_DOC_OTR_MOV_BAN + ";";
                System.out.println("insert insertarCabOtrMovBan: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDetOtrMovBan(Connection conexion){
        int j=1;  
        boolean blnRes=true;
        String strUpd="";
        try{
            if(conexion!=null){
                stm=conexion.createStatement();
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" INSERT INTO tbm_detotrmovban(";
                strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, co_reg, tx_tiptra, co_emptra";
                strSQL+=" , co_ctatra, nd_valtra, fe_car, co_ctaBanEgr, co_ctaBanIng";
                strSQL+=" , st_mosParRes, tx_motTra, nd_adv, nd_fod, nd_valiva";
                strSQL+=" )";
                strSQL+=" VALUES (";
                strSQL+="  " + INT_COD_EMP_OTR_MOV_BAN + "";//co_emp
                strSQL+=", " + INT_COD_LOC_OTR_MOV_BAN + "";//co_loc
                strSQL+=", " + INT_COD_TIP_DOC_OTR_MOV_BAN + "";//co_tipdoc
                strSQL+=", " + INT_COD_DOC_OTR_MOV_BAN + "";//co_doc
                strSQL+=", " + j;//co_reg
                strSQL+=", 'T'"; //tx_tiptra
                strSQL+=", " + INT_COD_EMP_TRS + "";//co_emptra
                strSQL+=", " + INT_COD_CTA_BCO + "";//co_ctatra   Este es el código de la cuenta contable del banco en tbm_plaCta
                strSQL+=", " + BIG_VAL_TOT + "";    //nd_valtra
                strSQL+=", '" + objUti.formatearFecha(DAT_FEC_CAR.toString(),"yyyy-MM-dd",objParSis.getFormatoFechaBaseDatos())  + "'";//fe_car
                strSQL+=", " + INT_COD_CTA_BAN_EGR + "";//co_ctaBanEgr   Este es el código de la cuenta bancaria en tbm_ctaBan
                strSQL+=", " + INT_COD_CTA_BAN_ING + "";//co_ctaBanIng   Este es el código de la cuenta bancaria en tbm_ctaBan
                strSQL+=", Null";//st_mosParRes
                strSQL+=", Null";//tx_motTra
                strSQL+=", Null";//nd_adv
                strSQL+=", Null";//nd_fod
                strSQL+=", Null";//nd_valiva
                strSQL+=");";
                strUpd+=strSQL;
                System.out.println("insertar insertarDetOtrMovBan: " + strUpd);
                stm.executeUpdate(strUpd);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite determinar si existe código de seguimiento asociado al Ingreso de Importación
     * @return true Si existe seguimiento para la instancia anterior del Ajuste
     * <BR> false Caso contrario
     */
    private boolean insertarCabSegMovInv(Connection conexion){
        boolean blnRes=false;
        try{
            if(conexion!=null){
                stm=conexion.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.co_cli, a1.tx_ruc,";
                strSQL+=" a1.tx_nomCli, a1.nd_tot, a1.st_reg, a4.co_seg";
                strSQL+=" FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabSegMovInv AS a4";
                strSQL+=" ON a1.co_emp=a4.co_empRelCabMovInv AND a1.co_loc=a4.co_locRelCabMovInv AND a1.co_tipDoc=a4.co_tipDocRelCabMovInv AND a1.co_doc=a4.co_docRelCabMovInv)";
                strSQL+=" INNER JOIN tbm_detConIntMovInv AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" WHERE a1.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + "";
                strSQL+=" AND a1.co_loc=" + INT_COD_LOC_ORD_PAG_EXT + "";
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_ORD_PAG_EXT + "";
                strSQL+=" AND a1.co_doc=" + INT_COD_DOC_ORD_PAG_EXT + "";
                strSQL+=" AND a1.st_ciepagext IN('P', 'E') AND a1.st_reg='O'";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.co_cli, a1.tx_ruc,";
                strSQL+=" a1.tx_nomCli, a1.nd_tot, a1.st_reg, a4.co_seg";

                //<editor-fold defaultstate="collapsed" desc="/* comentado */">
//                strSQL="";
//                strSQL+="  SELECT d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.co_reg, d1.ne_numDoc, d1.fe_doc, d1.co_cli, d1.tx_ruc";
//                strSQL+=" , d1.tx_nomCli, d1.nd_totDoc, d1.st_reg";
//                strSQL+=" , d1.co_empInsPed, d1.co_locInsPed, d1.co_tipDocInsPed, d1.co_docInsPed, d1.co_regInsPed";
//                strSQL+=" , d1.co_ctaActInsPed, d1.co_ctaPasInsPed, d1.tx_desDetInsPed, d1.nd_canInsPed";
//                strSQL+=" , d1.nd_preUniInsPed, d1.nd_totInsPed, d1.co_impInsPed, d1.ne_insPed, d1.tx_desCorTipDocPed, d1.tx_numDoc2Ped";
//                strSQL+=" , d1.co_seg";
//                strSQL+=" FROM(";
//                strSQL+="  	SELECT  c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.ne_numDoc, c1.fe_doc, c1.co_cli, c1.tx_ruc";
//                strSQL+="  	, c1.tx_nomCli, c1.nd_totDoc, c1.st_reg";
//                strSQL+="  	, CASE WHEN c1.co_empRelNotPed IS NULL THEN (CASE WHEN c1.co_empRelPedEmb IS NULL THEN c1.co_empRelMovInv ELSE c1.co_empRelPedEmb END ) ELSE c1.co_empRelNotPed END AS co_empInsPed";
//                strSQL+="        , CASE WHEN c1.co_locRelNotPed IS NULL THEN (CASE WHEN c1.co_locRelPedEmb IS NULL THEN c1.co_locRelMovInv ELSE c1.co_locRelPedEmb END ) ELSE c1.co_locRelNotPed END AS co_locInsPed";
//                strSQL+="  	, CASE WHEN c1.co_tipDocRelNotPed IS NULL THEN (CASE WHEN c1.co_tipDocRelPedEmb IS NULL THEN c1.co_tipDocRelMovInv ELSE c1.co_tipDocRelPedEmb END ) ELSE c1.co_tipDocRelNotPed END AS co_tipDocInsPed";
//                strSQL+="  	, CASE WHEN c1.co_docRelNotPed IS NULL THEN (CASE WHEN c1.co_docRelPedEmb IS NULL THEN c1.co_docRelMovInv ELSE c1.co_docRelPedEmb END ) ELSE c1.co_docRelNotPed END AS co_docInsPed";
//                strSQL+="  	, CASE WHEN c1.co_regRelNotPed IS NULL THEN (CASE WHEN c1.co_regRelPedEmb IS NULL THEN c1.co_regRelMovInv ELSE c1.co_regRelPedEmb END ) ELSE c1.co_regRelNotPed END AS co_regInsPed";
//                strSQL+="  	, CASE WHEN c1.co_ctaActNotPed IS NULL THEN (CASE WHEN c1.co_ctaActPedEmb IS NULL THEN c1.co_ctaActMovInv ELSE c1.co_ctaActPedEmb END ) ELSE c1.co_ctaActNotPed END AS co_ctaActInsPed";
//                strSQL+="  	, CASE WHEN c1.co_ctaPasNotPed IS NULL THEN (CASE WHEN c1.co_ctaPasPedEmb IS NULL THEN c1.co_ctaPasMovInv ELSE c1.co_ctaPasPedEmb END ) ELSE c1.co_ctaPasNotPed END AS co_ctaPasInsPed";
//                strSQL+="  	, CASE WHEN c1.tx_desDetNotPed IS NULL THEN (CASE WHEN c1.tx_desDetPedEmb IS NULL THEN c1.tx_desDetMovInv ELSE c1.tx_desDetPedEmb END ) ELSE c1.tx_desDetNotPed END AS tx_desDetInsPed";
//                strSQL+="  	, CASE WHEN c1.nd_canNotPed IS NULL THEN (CASE WHEN c1.nd_canPedEmb IS NULL THEN c1.nd_canMovInv ELSE c1.nd_canPedEmb END ) ELSE c1.nd_canNotPed END AS nd_canInsPed";
//                strSQL+="  	, CASE WHEN c1.nd_preUniNotPed IS NULL THEN (CASE WHEN c1.nd_preUniPedEmb IS NULL THEN c1.nd_preUniMovInv ELSE c1.nd_preUniPedEmb END ) ELSE c1.nd_preUniNotPed END AS nd_preUniInsPed";
//                strSQL+="  	, CASE WHEN c1.nd_totNotPed IS NULL THEN (CASE WHEN c1.nd_totPedEmb IS NULL THEN c1.nd_totMovInv ELSE c1.nd_totPedEmb END ) ELSE c1.nd_totNotPed END AS nd_totInsPed";
//                strSQL+="  	, CASE WHEN c1.co_regNotPed IS NULL THEN (CASE WHEN c1.co_regPedEmb IS NULL THEN c1.co_regMovInv ELSE c1.co_regPedEmb END ) ELSE c1.co_regNotPed END AS co_reg";
//                strSQL+="  	, CASE WHEN c1.co_impNotPed IS NULL THEN (CASE WHEN c1.co_impPedEmb IS NULL THEN c1.co_impMovInv ELSE c1.co_impPedEmb END ) ELSE c1.co_impNotPed END AS co_impInsPed";
//                strSQL+="  	, CASE WHEN c1.tx_desCorTipDocNotPed IS NULL THEN (CASE WHEN c1.tx_desCorTipDocPedEmb IS NULL THEN c1.tx_desCorTipDocMovInv ELSE c1.tx_desCorTipDocPedEmb END ) ELSE c1.tx_desCorTipDocNotPed END AS tx_desCorTipDocPed";
//                strSQL+="  	, CASE WHEN c1.ne_insNotPed IS NULL THEN (CASE WHEN c1.ne_insPedEmb IS NULL THEN c1.ne_insMovInv ELSE c1.ne_insPedEmb END ) ELSE c1.ne_insNotPed END AS ne_insPed";
//                strSQL+="  	, CASE WHEN c1.tx_numDoc2NotPed IS NULL THEN (CASE WHEN c1.tx_numDoc2PedEmb IS NULL THEN c1.tx_numDoc2MovInv ELSE c1.tx_numDoc2PedEmb END ) ELSE c1.tx_numDoc2NotPed END AS tx_numDoc2Ped";
//                strSQL+="  	, c1.co_seg";
//                strSQL+="  	FROM(";
//                strSQL+="  		 SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc, b1.fe_doc, b1.co_cli, b1.tx_ruc";
//                strSQL+="  		 , b1.tx_nomCli, b1.nd_tot AS nd_totDoc, b1.st_reg, b2.co_regPedEmb, b3.co_regNotPed, b4.co_regMovInv";
//                strSQL+="  		 , b2.co_empRelPedEmb, b2.co_locRelPedEmb, b2.co_tipDocRelPedEmb, b2.co_docRelPedEmb, b2.co_regRelPedEmb, b2.co_ctaActPedEmb, b2.co_ctaPasPedEmb, b2.tx_desDetPedEmb, b2.nd_canPedEmb, b2.nd_preUniPedEmb, b2.nd_totPedEmb, b2.co_impPedEmb";
//                strSQL+="  		 , b3.co_empRelNotPed, b3.co_locRelNotPed, b3.co_tipDocRelNotPed, b3.co_docRelNotPed, b3.co_regRelNotPed, b3.co_ctaActNotPed, b3.co_ctaPasNotPed, b3.tx_desDetNotPed, b3.nd_canNotPed, b3.nd_preUniNotPed, b3.nd_totNotPed, b3.co_impNotPed";
//                strSQL+="  		 , b4.co_empRelMovInv, b4.co_locRelMovInv, b4.co_tipDocRelMovInv, b4.co_docRelMovInv, b4.co_regRelMovInv, b4.co_ctaActMovInv, b4.co_ctaPasMovInv, b4.tx_desDetMovInv, b4.nd_canMovInv, b4.nd_preUniMovInv, b4.nd_totMovInv, b4.co_impMovInv";
//                strSQL+="  		 , b2.ne_insPedEmb, b3.ne_insNotPed, b4.ne_insMovInv, b2.tx_desCorTipDocPedEmb, b3.tx_desCorTipDocNotPed, b4.tx_desCorTipDocMovInv";
//                strSQL+="  		 , b2.tx_numDoc2PedEmb, b3.tx_numDoc2NotPed, b4.tx_numDoc2MovInv, b1.co_seg";
//                strSQL+="  		 FROM(";
//                strSQL+="  			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a1.co_cli, a1.tx_ruc,";
//                strSQL+="  			a1.tx_nomCli, a1.nd_tot, a1.st_reg, a4.co_seg";
//                strSQL+="  			FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabSegMovInv AS a4";
//                strSQL+="                                   ON a1.co_emp=a4.co_empRelCabMovInv AND a1.co_loc=a4.co_locRelCabMovInv AND a1.co_tipDoc=a4.co_tipDocRelCabMovInv AND a1.co_doc=a4.co_docRelCabMovInv)";
//                strSQL+="  			INNER JOIN tbm_detConIntMovInv AS a2";
//                strSQL+="  			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
//                strSQL+="                       WHERE a1.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + "";
//                strSQL+="                       AND a1.co_loc=" + INT_COD_LOC_ORD_PAG_EXT + "";
//                strSQL+="                       AND a1.co_tipDoc=" + INT_COD_TIP_DOC_ORD_PAG_EXT + "";
//                strSQL+="                       AND a1.co_doc=" + INT_COD_DOC_ORD_PAG_EXT + "";
//                strSQL+="  		        AND a1.st_ciepagext IN('P', 'E') AND a1.st_reg='O'";
//                strSQL+="  		 ) AS b1";
//                strSQL+="  		 LEFT OUTER JOIN(/*Pedido Embarcado*/";
//                strSQL+="                       SELECT a2.co_emp AS co_empDxP, a2.co_loc AS co_locDxP, a2.co_tipDoc AS co_tipDocDxP, a2.co_doc AS co_docDxP, a2.co_reg AS co_regPedEmb";
//                strSQL+="                       , a1.tx_des AS tx_desDetPedEmb, a1.nd_can AS nd_canPedEmb, a1.nd_preUni AS nd_preUniPedEmb, a1.nd_tot AS nd_totPedEmb";
//                strSQL+="                       , a2.co_empRelPedEmb, a2.co_locRelPedEmb, a2.co_tipDocRelPedEmb, a2.co_docRelPedEmb, a2.co_regRelPedEmb, a3.co_ctaAct AS co_ctaActPedEmb";
//                strSQL+="                       , a3.co_ctaPas AS co_ctaPasPedEmb, a3.co_imp AS co_impPedEmb, 1 AS ne_insPedEmb, a4.tx_desCor AS tx_desCorTipDocPedEmb, a3.tx_numDoc2 AS tx_numDoc2PedEmb";
//                strSQL+="                       FROM tbm_detConIntMovInv AS a1";
//                strSQL+="                       /*NUEVO*/ INNER JOIN (tbr_detConIntCarPagInsImp AS a2 INNER JOIN tbm_carPagPedEmbImp AS a5";
//                strSQL+="                       ON a2.co_empRelPedEmb=a5.co_emp AND a2.co_locRelPedEmb=a5.co_loc AND a2.co_tipDocRelPedEmb=a5.co_tipDoc AND a2.co_docRelPedEmb=a5.co_doc AND a2.co_regRelPedEmb=a5.co_reg";
//                strSQL+="                       INNER JOIN tbm_carPagImp AS a6 ON a5.co_carPag=a6.co_carPag) ";
//                strSQL+="                       ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg";
//                strSQL+="                       INNER JOIN (tbm_cabPedEmbImp AS a3 INNER JOIN tbm_cabTipDoc AS a4";
//                strSQL+="                       ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc)";
//                strSQL+="                       ON a2.co_empRelPedEmb=a3.co_emp AND a2.co_locRelPedEmb=a3.co_loc AND a2.co_tipDocRelPedEmb=a3.co_tipDoc AND a2.co_docRelPedEmb=a3.co_doc";
//                strSQL+="                       WHERE a2.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + "";
//                strSQL+="                       AND a2.co_loc=" + INT_COD_LOC_ORD_PAG_EXT + "";
//                strSQL+="                       AND a2.co_tipDoc=" + INT_COD_TIP_DOC_ORD_PAG_EXT + "";
//                strSQL+="                       AND a2.co_doc=" + INT_COD_DOC_ORD_PAG_EXT + "";
//                strSQL+="                       /*NUEVO*/ AND a6.st_imp IN('N')";                
//                strSQL+="  		 ) AS b2";
//                strSQL+="  		 ON b1.co_emp=b2.co_empDxP AND b1.co_loc=b2.co_locDxP AND b1.co_tipDoc=b2.co_tipDocDxP AND b1.co_doc=b2.co_docDxP AND b1.co_reg=b2.co_regPedEmb";
//                strSQL+="  		 LEFT OUTER JOIN(/*Nota de Pedido*/";
//                strSQL+="  			SELECT a2.co_emp AS co_empDxP, a2.co_loc AS co_locDxP, a2.co_tipDoc AS co_tipDocDxP, a2.co_doc AS co_docDxP, a2.co_reg AS co_regNotPed";
//                strSQL+="  			, a1.tx_des AS tx_desDetNotPed, a1.nd_can AS nd_canNotPed, a1.nd_preUni AS nd_preUniNotPed, a1.nd_tot AS nd_totNotPed";
//                strSQL+="  			, a2.co_empRelNotPed, a2.co_locRelNotPed, a2.co_tipDocRelNotPed, a2.co_docRelNotPed, a2.co_regRelNotPed, a3.co_ctaAct AS co_ctaActNotPed";
//                strSQL+="  			, a3.co_ctaPas AS co_ctaPasNotPed, a3.co_imp AS co_impNotPed, 2 AS ne_insNotPed, a4.tx_desCor AS tx_desCorTipDocNotPed, a3.tx_numDoc2 AS tx_numDoc2NotPed";
//                strSQL+="  			FROM tbm_detConIntMovInv AS a1";
//                strSQL+="  			/*NUEVO*/ INNER JOIN (tbr_detConIntCarPagInsImp AS a2";
//                strSQL+="  			INNER JOIN tbm_carPagNotPedImp AS a5";
//                strSQL+="  			ON a2.co_empRelNotPed=a5.co_emp AND a2.co_locRelNotPed=a5.co_loc AND a2.co_tipDocRelNotPed=a5.co_tipDoc AND a2.co_docRelNotPed=a5.co_doc AND a2.co_regRelNotPed=a5.co_reg";
//                strSQL+="  			INNER JOIN tbm_carPagImp AS a6 ON a5.co_carPag=a6.co_carPag)";
//                strSQL+="  			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg";
//                strSQL+="  			INNER JOIN (tbm_cabNotPedImp AS a3 INNER JOIN tbm_cabTipDoc AS a4";
//                strSQL+="  			ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc)";
//                strSQL+="  			ON a2.co_empRelNotPed=a3.co_emp AND a2.co_locRelNotPed=a3.co_loc AND a2.co_tipDocRelNotPed=a3.co_tipDoc AND a2.co_docRelNotPed=a3.co_doc";                
//                strSQL+="                       WHERE a2.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + "";
//                strSQL+="                       AND a2.co_loc=" + INT_COD_LOC_ORD_PAG_EXT + "";
//                strSQL+="                       AND a2.co_tipDoc=" + INT_COD_TIP_DOC_ORD_PAG_EXT + "";
//                strSQL+="                       AND a2.co_doc=" + INT_COD_DOC_ORD_PAG_EXT + "";
//                strSQL+="                       /*NUEVO*/ AND a6.st_imp IN('N')";
//                strSQL+="  		) AS b3";
//                strSQL+="  		ON b1.co_emp=b3.co_empDxP AND b1.co_loc=b3.co_locDxP AND b1.co_tipDoc=b3.co_tipDocDxP AND b1.co_doc=b3.co_docDxP AND b1.co_reg=b3.co_regNotPed";
//                strSQL+="  		LEFT OUTER JOIN(/*Ingreso por Importación*/";
//                strSQL+="  			SELECT a2.co_emp AS co_empDxP, a2.co_loc AS co_locDxP, a2.co_tipDoc AS co_tipDocDxP, a2.co_doc AS co_docDxP, a2.co_reg AS co_regMovInv";
//                strSQL+="  			, a1.tx_des AS tx_desDetMovInv, a1.nd_can AS nd_canMovInv, a1.nd_preUni AS nd_preUniMovInv, a1.nd_tot AS nd_totMovInv";
//                strSQL+="  			, a2.co_empRelMovInv, a2.co_locRelMovInv, a2.co_tipDocRelMovInv, a2.co_docRelMovInv, a2.co_regRelMovInv, a4.co_ctaAct AS co_ctaActMovInv";
//                strSQL+="  			, a4.co_ctaPas AS co_ctaPasMovInv, a3.co_emp AS co_impMovInv, 3 AS ne_insMovInv, a5.tx_desCor AS tx_desCorTipDocMovInv, a3.tx_numDoc2 AS tx_numDoc2MovInv";
//                strSQL+="  			FROM tbm_detConIntMovInv AS a1";
//                strSQL+="  			/*NUEVO*/ INNER JOIN (tbr_detConIntCarPagInsImp AS a2";
//                strSQL+="                       INNER JOIN tbm_carPagMovinv AS a6";
//                strSQL+="                       ON a2.co_empRelPedEmb=a6.co_emp AND a2.co_locRelPedEmb=a6.co_loc AND a2.co_tipDocRelPedEmb=a6.co_tipDoc AND a2.co_docRelPedEmb=a6.co_doc AND a2.co_regRelPedEmb=a6.co_reg";
//                strSQL+="  			INNER JOIN tbm_carPagImp AS a7 ON a6.co_carPag=a7.co_carPag)";
//                strSQL+="  			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg";
//                strSQL+="  			INNER JOIN (tbm_cabMovInv AS a3 INNER JOIN tbm_cabTipDoc AS a5";
//                strSQL+="  			ON a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipDoc=a5.co_tipDoc)";
//                strSQL+="  			ON a2.co_empRelMovInv=a3.co_emp AND a2.co_locRelMovInv=a3.co_loc AND a2.co_tipDocRelMovInv=a3.co_tipDoc AND a2.co_docRelMovInv=a3.co_doc";
//                strSQL+="  			INNER JOIN tbm_cabPedEmbImp AS a4 ON a3.co_empRelPedEmbImp=a4.co_emp AND a3.co_locRelPedEmbImp=a4.co_loc AND a3.co_tipDocRelPedEmbImp=a4.co_tipDoc AND a3.co_docRelPedEmbImp=a4.co_doc";
//                strSQL+="                       WHERE a2.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + "";
//                strSQL+="                       AND a2.co_loc=" + INT_COD_LOC_ORD_PAG_EXT + "";
//                strSQL+="                       AND a2.co_tipDoc=" + INT_COD_TIP_DOC_ORD_PAG_EXT + "";
//                strSQL+="                       AND a2.co_doc=" + INT_COD_DOC_ORD_PAG_EXT + "";
//                strSQL+="  			/*NUEVO*/ AND a7.st_imp IN('N')";
//                strSQL+="  		) AS b4";
//                strSQL+="  		ON b1.co_emp=b4.co_empDxP AND b1.co_loc=b4.co_locDxP AND b1.co_tipDoc=b4.co_tipDocDxP AND b1.co_doc=b4.co_docDxP AND b1.co_reg=b4.co_regMovInv";
//                strSQL+="  	) AS c1";
//                strSQL+=" ) AS d1";
//                strSQL+=" GROUP BY d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.co_reg, d1.ne_numDoc, d1.fe_doc, d1.co_cli, d1.tx_ruc";
//                strSQL+=" , d1.tx_nomCli, d1.nd_totDoc, d1.st_reg";
//                strSQL+=" , d1.co_empInsPed, d1.co_locInsPed, d1.co_tipDocInsPed, d1.co_docInsPed, d1.co_regInsPed";
//                strSQL+=" , d1.co_ctaActInsPed, d1.co_ctaPasInsPed, d1.tx_desDetInsPed, d1.nd_canInsPed";
//                strSQL+=" , d1.nd_preUniInsPed, d1.nd_totInsPed, d1.co_impInsPed, d1.ne_insPed, d1.tx_desCorTipDocPed, d1.tx_numDoc2Ped";
//                strSQL+=" , d1.co_seg";
//                strSQL+=" ORDER BY d1.co_reg";
                //</editor-fold>
                System.out.println("insertarCabSegMovInv: " + strSQL);
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    objCodSegDocPagExt=rst.getObject("co_seg");
                    if(objSegMovInv.setSegMovInvCompra(conexion, objCodSegDocPagExt, 5, INT_COD_EMP_ORD_PAG_EXT, INT_COD_LOC_ORD_PAG_EXT, INT_COD_TIP_DOC_ORD_PAG_EXT, INT_COD_DOC_ORD_PAG_EXT, null
                                                            , 8, INT_COD_EMP_TRS, INT_COD_LOC_TRS, INT_COD_TIP_DOC_TRS, INT_COD_DOC_TRS, null)){
                        blnRes=true;
                    }
                    else{
                        blnRes=false;
                        break;
                    }
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    /**
     * Función que permite conocer el pedido asociado al DxP al Exterior
     * @param codigoEmpresa Código de empresa del DxP al exterior
     * @param codigoLocal   Código de local del DxP al exterior
     * @param codigoTipoDocumento Código de tipo de documento del DxP al exterior
     * @param codigoDocumento Código de documento del DxP al exterior
     * @return 
     */
    private boolean getPedidoRelacionadoDxP(Connection conexion){
        boolean blnRes=true;
        Statement stmPedRelDxP;
        ResultSet rstPedRelDxP;
        try{
            if(conexion!=null){
                stmPedRelDxP=conexion.createStatement();
                strSQL="";
                strSQL+=" SELECT d1.co_empInsPed, d1.co_locInsPed, d1.co_tipDocInsPed, d1.co_docInsPed/*, d1.co_regInsPed*/, d1.co_expInsPed";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.ne_numDoc, c1.fe_doc, c1.co_cli, c1.tx_ruc";
                strSQL+=" 	     , c1.tx_nomCli, c1.nd_totDoc, c1.st_reg,  c1.tx_desDet, c1.nd_can, c1.nd_preUni, c1.nd_totReg";
                strSQL+=" 	     , CASE WHEN c1.co_empRelNotPed IS NULL THEN (CASE WHEN c1.co_empRelPedEmb IS NULL THEN c1.co_empRelMovInv ELSE c1.co_empRelPedEmb END ) ELSE c1.co_empRelNotPed END AS co_empInsPed";
                strSQL+=" 	     , CASE WHEN c1.co_locRelNotPed IS NULL THEN (CASE WHEN c1.co_locRelPedEmb IS NULL THEN c1.co_locRelMovInv ELSE c1.co_locRelPedEmb END ) ELSE c1.co_locRelNotPed END AS co_locInsPed";
                strSQL+=" 	     , CASE WHEN c1.co_tipDocRelNotPed IS NULL THEN (CASE WHEN c1.co_tipDocRelPedEmb IS NULL THEN c1.co_tipDocRelMovInv ELSE c1.co_tipDocRelPedEmb END ) ELSE c1.co_tipDocRelNotPed END AS co_tipDocInsPed";
                strSQL+=" 	     , CASE WHEN c1.co_docRelNotPed IS NULL THEN (CASE WHEN c1.co_docRelPedEmb IS NULL THEN c1.co_docRelMovInv ELSE c1.co_docRelPedEmb END ) ELSE c1.co_docRelNotPed END AS co_docInsPed";
                strSQL+=" 	     , CASE WHEN c1.co_regRelNotPed IS NULL THEN (CASE WHEN c1.co_regRelPedEmb IS NULL THEN c1.co_regRelMovInv ELSE c1.co_regRelPedEmb END ) ELSE c1.co_regRelNotPed END AS co_regInsPed";
                strSQL+=" 	     , CASE WHEN c1.co_ctaActNotPed IS NULL THEN (CASE WHEN c1.co_ctaActPedEmb IS NULL THEN c1.co_ctaActMovInv ELSE c1.co_ctaActPedEmb END ) ELSE c1.co_ctaActNotPed END AS co_ctaActInsPed";
                strSQL+=" 	     , CASE WHEN c1.co_ctaPasNotPed IS NULL THEN (CASE WHEN c1.co_ctaPasPedEmb IS NULL THEN c1.co_ctaPasMovInv ELSE c1.co_ctaPasPedEmb END ) ELSE c1.co_ctaPasNotPed END AS co_ctaPasInsPed";
                strSQL+=" 	     , CASE WHEN c1.co_expNotPed IS NULL THEN (CASE WHEN c1.co_expPedEmb IS NULL THEN c1.co_expMovInv ELSE c1.co_expPedEmb END ) ELSE c1.co_expNotPed END AS co_expInsPed";
                strSQL+=" 	FROM(";
                strSQL+=" 		 SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc, b1.fe_doc, b1.co_cli, b1.tx_ruc";
                strSQL+=" 		      , b1.tx_nomCli, b1.nd_tot AS nd_totDoc, b1.st_reg,  b2.tx_desDet, b2.nd_can, b2.nd_preUni, b2.nd_tot AS nd_totReg";
                strSQL+=" 		      , b2.co_empRelPedEmb, b2.co_locRelPedEmb, b2.co_tipDocRelPedEmb, b2.co_docRelPedEmb, b2.co_regRelPedEmb, b2.co_ctaActPedEmb, b2.co_ctaPasPedEmb, b2.co_expPedEmb";
                strSQL+=" 		      , b3.co_empRelNotPed, b3.co_locRelNotPed, b3.co_tipDocRelNotPed, b3.co_docRelNotPed, b3.co_regRelNotPed, b3.co_ctaActNotPed, b3.co_ctaPasNotPed, b3.co_expNotPed";
                strSQL+=" 		      , b4.co_empRelMovInv, b4.co_locRelMovInv, b4.co_tipDocRelMovInv, b4.co_docRelMovInv, b4.co_regRelMovInv, b4.co_ctaActMovInv, b4.co_ctaPasMovInv, b4.co_expMovInv";
                strSQL+=" 		 FROM( "; /* DxP*/
                strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc";
                strSQL+=" 			     , a1.fe_doc, a1.co_cli, a1.tx_ruc, a1.tx_nomCli, a1.nd_tot, a1.st_reg";
                strSQL+=" 			FROM tbm_cabMovInv AS a1";
                strSQL+="                       WHERE a1.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + " AND a1.co_loc=" + INT_COD_LOC_ORD_PAG_EXT + "";
                strSQL+="                       AND a1.co_tipDoc=" + INT_COD_TIP_DOC_ORD_PAG_EXT + " AND a1.co_doc=" + INT_COD_DOC_ORD_PAG_EXT + "";
                strSQL+="                       AND a1.st_ciepagext IN('P', 'E')";
                strSQL+="                       AND a1.st_reg='O'";
                strSQL+=" 		 ) AS b1";
                strSQL+=" 		 LEFT OUTER JOIN(";/*Pedido Embarcado*/
                strSQL+=" 			SELECT a2.co_emp AS co_empDxP, a2.co_loc AS co_locDxP, a2.co_tipDoc AS co_tipDocDxP, a2.co_doc AS co_docDxP, a2.co_reg AS co_regDxP";
                strSQL+=" 			     , a1.tx_des AS tx_desDet, a1.nd_can, a1.nd_preUni, a1.nd_tot, a3.co_exp AS co_expPedEmb";
                strSQL+=" 			     , a2.co_empRelPedEmb, a2.co_locRelPedEmb, a2.co_tipDocRelPedEmb, a2.co_docRelPedEmb, a2.co_regRelPedEmb, a3.co_ctaAct AS co_ctaActPedEmb, a3.co_ctaPas AS co_ctaPasPedEmb";
                strSQL+=" 			FROM tbm_detConIntMovInv AS a1 INNER JOIN tbr_detConIntCarPagInsImp AS a2";
                strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg";
                strSQL+=" 			INNER JOIN tbm_cabPedEmbImp AS a3 ON a2.co_empRelPedEmb=a3.co_emp AND a2.co_locRelPedEmb=a3.co_loc AND a2.co_tipDocRelPedEmb=a3.co_tipDoc AND a2.co_docRelPedEmb=a3.co_doc";
                strSQL+=" 			WHERE a2.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + " AND a2.co_loc=" + INT_COD_LOC_ORD_PAG_EXT + "";
                strSQL+="                       AND a2.co_tipDoc=" + INT_COD_TIP_DOC_ORD_PAG_EXT + " AND a2.co_doc=" + INT_COD_DOC_ORD_PAG_EXT + "";
                strSQL+="		 ) AS b2";
                strSQL+=" 		 ON b1.co_emp=b2.co_empDxP AND b1.co_loc=b2.co_locDxP AND b1.co_tipDoc=b2.co_tipDocDxP AND b1.co_doc=b2.co_docDxP";
                strSQL+=" 		 LEFT OUTER JOIN(";/*Nota de Pedido*/
                strSQL+=" 			SELECT a2.co_emp AS co_empDxP, a2.co_loc AS co_locDxP, a2.co_tipDoc AS co_tipDocDxP, a2.co_doc AS co_docDxP, a2.co_reg AS co_regDxP";
                strSQL+=" 			     , a1.tx_des AS tx_desDet, a1.nd_can, a1.nd_preUni, a1.nd_tot, a3.co_exp AS co_expNotPed";
                strSQL+=" 			     , a2.co_empRelNotPed, a2.co_locRelNotPed, a2.co_tipDocRelNotPed, a2.co_docRelNotPed, a2.co_regRelNotPed, a3.co_ctaAct AS co_ctaActNotPed, a3.co_ctaPas AS co_ctaPasNotPed";
                strSQL+=" 			FROM tbm_detConIntMovInv AS a1 INNER JOIN tbr_detConIntCarPagInsImp AS a2";
                strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg";
                strSQL+=" 			INNER JOIN tbm_cabNotPedImp AS a3 ON a2.co_empRelNotPed=a3.co_emp AND a2.co_locRelNotPed=a3.co_loc AND a2.co_tipDocRelNotPed=a3.co_tipDoc AND a2.co_docRelNotPed=a3.co_doc";
                strSQL+=" 			WHERE a2.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + " AND a2.co_loc=" + INT_COD_LOC_ORD_PAG_EXT + "";
                strSQL+=" 		        AND a2.co_tipDoc=" + INT_COD_TIP_DOC_ORD_PAG_EXT + " AND a2.co_doc=" + INT_COD_DOC_ORD_PAG_EXT + "";
                strSQL+=" 		) AS b3";
                strSQL+=" 		ON b1.co_emp=b3.co_empDxP AND b1.co_loc=b3.co_locDxP AND b1.co_tipDoc=b3.co_tipDocDxP AND b1.co_doc=b3.co_docDxP";
                strSQL+=" 		LEFT OUTER JOIN(";/*Ingreso por Importación*/
                strSQL+=" 			SELECT a2.co_emp AS co_empDxP, a2.co_loc AS co_locDxP, a2.co_tipDoc AS co_tipDocDxP, a2.co_doc AS co_docDxP, a2.co_reg AS co_regDxP";
                strSQL+=" 			     , a1.tx_des AS tx_desDet, a1.nd_can, a1.nd_preUni, a1.nd_tot, a3.co_exp AS co_expMovInv";
                strSQL+=" 			     , a2.co_empRelMovInv, a2.co_locRelMovInv, a2.co_tipDocRelMovInv, a2.co_docRelMovInv, a2.co_regRelMovInv, a4.co_ctaAct AS co_ctaActMovInv, a4.co_ctaPas AS co_ctaPasMovInv";
                strSQL+=" 			FROM tbm_detConIntMovInv AS a1 INNER JOIN tbr_detConIntCarPagInsImp AS a2";
                strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg";
                strSQL+=" 			INNER JOIN tbm_cabMovInv AS a3 ON a2.co_empRelMovInv=a3.co_emp AND a2.co_locRelMovInv=a3.co_loc AND a2.co_tipDocRelMovInv=a3.co_tipDoc AND a2.co_docRelMovInv=a3.co_doc";
                strSQL+=" 			INNER JOIN tbm_cabPedEmbImp AS a4 ON a3.co_empRelPedEmbImp=a4.co_emp AND a3.co_locRelPedEmbImp=a4.co_loc AND a3.co_tipDocRelPedEmbImp=a4.co_tipDoc AND a3.co_docRelPedEmbImp=a4.co_doc";
                strSQL+=" 			WHERE a2.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + " AND a2.co_loc=" + INT_COD_LOC_ORD_PAG_EXT + "";
                strSQL+=" 		        AND a2.co_tipDoc=" + INT_COD_TIP_DOC_ORD_PAG_EXT + " AND a2.co_doc=" + INT_COD_DOC_ORD_PAG_EXT + "";
                strSQL+=" 		) AS b4";
                strSQL+=" 		ON b1.co_emp=b4.co_empDxP AND b1.co_loc=b4.co_locDxP AND b1.co_tipDoc=b4.co_tipDocDxP AND b1.co_doc=b4.co_docDxP";
                strSQL+=" 	) AS c1";
                strSQL+=" ) AS d1";
                strSQL+=" INNER JOIN tbm_carPagImp AS d2 ON d1.co_regInsPed=d2.co_carPag";
                strSQL+=" GROUP BY d1.co_empInsPed, d1.co_locInsPed, d1.co_tipDocInsPed, d1.co_docInsPed, d1.co_expInsPed";
                strSQL+=" ORDER BY d1.co_docInsPed";
                System.out.println("getPedidoRelacionadoDxP: " + strSQL);
                rstPedRelDxP=stmPedRelDxP.executeQuery(strSQL);
                if(rstPedRelDxP.next()){
                    INT_COD_EMP_PED_REL=rstPedRelDxP.getInt("co_empInsPed");
                    INT_COD_LOC_PED_REL=rstPedRelDxP.getInt("co_locInsPed");
                    INT_COD_TIP_DOC_PED_REL=rstPedRelDxP.getInt("co_tipDocInsPed");
                    INT_COD_DOC_PED_REL=rstPedRelDxP.getInt("co_docInsPed");
                    INT_COD_EXP_PED_REL=rstPedRelDxP.getInt("co_expInsPed");
                }
                stmPedRelDxP.close();
                stmPedRelDxP=null;
                rstPedRelDxP.close();
                rstPedRelDxP=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
            
    /**
     * Función que permite conocer el pedido asociado al DxP al Exterior
     * @param codigoEmpresa Código de empresa del DxP al exterior
     * @param codigoLocal   Código de local del DxP al exterior
     * @param codigoTipoDocumento Código de tipo de documento del DxP al exterior
     * @param codigoDocumento Código de documento del DxP al exterior
     * @return 
     */
    private boolean generaAsiDiaTRBAEX(Connection conexion){
        boolean blnRes=true;
        Statement stmPedRelDxP, stmValCom;
        ResultSet rstPedRelDxP, rstValCom;
        int intCodCarPag=-1;
        BigDecimal bgdTotValPed=BigDecimal.ZERO;
        BigDecimal bgdTotValISDCreTri=BigDecimal.ZERO;
        BigDecimal bgdTotValISDGto=BigDecimal.ZERO;
        BigDecimal bgdTotValCom=BigDecimal.ZERO;
        BigDecimal bgdTotValInt=BigDecimal.ZERO;
        int intCodRegInsPed=-1;
        int intCtaISDCreTriInsPed=-1;
        int intCtaISDGtoInsPed=-1;
        int intCtaValCom=-1;
        int intCtaValInt=-1;
        int intCtaValProInt=-1;
        String strGloDiaTrsExt="";
        int intInsPed=-1;
        try{
//            INT_COD_EMP_ORD_PAG_EXT=1;
//            INT_COD_LOC_ORD_PAG_EXT=4;
//            INT_COD_TIP_DOC_ORD_PAG_EXT=106;
//            INT_COD_DOC_ORD_PAG_EXT=4178;
            
            strGloDiaTrsExt="Pago de Pedido: ";

            if(conexion!=null){
                arlDatDetTrs.clear();
                vecDatDetTrs.clear();
                objAsiDia.inicializar();                
                
//                bgdValISDCreTriTrsExt=BigDecimal.ZERO;
//                bgdValISDGtoTrsExt=BigDecimal.ZERO;
                //Obtener la fecha del servidor.
                datFecAux=null;
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                stmPedRelDxP=conexion.createStatement();
//                strSQL ="";
//                strSQL+=" SELECT d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.co_reg, d1.ne_numDoc, d1.fe_doc, d1.nd_valComExt, d1.nd_valIntExt, d1.co_cli, d1.tx_ruc";
//                strSQL+="      , d1.tx_nomCli, d1.nd_totDoc, d1.st_reg, d1.co_empInsPed, d1.co_locInsPed, d1.co_tipDocInsPed, d1.co_docInsPed, d1.co_regInsPed";
//                strSQL+="      , d2.tx_nom AS tx_nomCarPag, d2.tx_tipCarPag, d1.co_ctaActInsPed, d1.co_ctaPasInsPed, d1.tx_desDetInsPed, d1.nd_canInsPed";
//                strSQL+="      , d1.nd_preUniInsPed, d1.nd_totInsPed, d1.co_impInsPed, d1.ne_insPed, d1.tx_desCorTipDocPed, d1.tx_numDoc2Ped";
//                strSQL+="      , co_carPagInsPed /*Nuevo*/";
//                strSQL+=" FROM(";
//                strSQL+=" 	SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.ne_numDoc, c1.fe_doc, c1.nd_valComExt, c1.nd_valIntExt, c1.co_cli, c1.tx_ruc";
//                strSQL+=" 	     , c1.tx_nomCli, c1.nd_totDoc, c1.st_reg";
//                strSQL+=" 	     , CASE WHEN c1.co_empRelNotPed IS NULL THEN (CASE WHEN c1.co_empRelPedEmb IS NULL THEN c1.co_empRelMovInv ELSE c1.co_empRelPedEmb END ) ELSE c1.co_empRelNotPed END AS co_empInsPed";
//                strSQL+="            , CASE WHEN c1.co_locRelNotPed IS NULL THEN (CASE WHEN c1.co_locRelPedEmb IS NULL THEN c1.co_locRelMovInv ELSE c1.co_locRelPedEmb END ) ELSE c1.co_locRelNotPed END AS co_locInsPed";
//                strSQL+=" 	     , CASE WHEN c1.co_tipDocRelNotPed IS NULL THEN (CASE WHEN c1.co_tipDocRelPedEmb IS NULL THEN c1.co_tipDocRelMovInv ELSE c1.co_tipDocRelPedEmb END ) ELSE c1.co_tipDocRelNotPed END AS co_tipDocInsPed";
//                strSQL+=" 	     , CASE WHEN c1.co_docRelNotPed IS NULL THEN (CASE WHEN c1.co_docRelPedEmb IS NULL THEN c1.co_docRelMovInv ELSE c1.co_docRelPedEmb END ) ELSE c1.co_docRelNotPed END AS co_docInsPed";
//                strSQL+=" 	     , CASE WHEN c1.co_regRelNotPed IS NULL THEN (CASE WHEN c1.co_regRelPedEmb IS NULL THEN c1.co_regRelMovInv ELSE c1.co_regRelPedEmb END ) ELSE c1.co_regRelNotPed END AS co_regInsPed";
//                strSQL+=" 	     , CASE WHEN c1.co_ctaActNotPed IS NULL THEN (CASE WHEN c1.co_ctaActPedEmb IS NULL THEN c1.co_ctaActMovInv ELSE c1.co_ctaActPedEmb END ) ELSE c1.co_ctaActNotPed END AS co_ctaActInsPed";
//                strSQL+=" 	     , CASE WHEN c1.co_ctaPasNotPed IS NULL THEN (CASE WHEN c1.co_ctaPasPedEmb IS NULL THEN c1.co_ctaPasMovInv ELSE c1.co_ctaPasPedEmb END ) ELSE c1.co_ctaPasNotPed END AS co_ctaPasInsPed";
//                strSQL+=" 	     , CASE WHEN c1.tx_desDetNotPed IS NULL THEN (CASE WHEN c1.tx_desDetPedEmb IS NULL THEN c1.tx_desDetMovInv ELSE c1.tx_desDetPedEmb END ) ELSE c1.tx_desDetNotPed END AS tx_desDetInsPed";
//                strSQL+=" 	     , CASE WHEN c1.nd_canNotPed IS NULL THEN (CASE WHEN c1.nd_canPedEmb IS NULL THEN c1.nd_canMovInv ELSE c1.nd_canPedEmb END ) ELSE c1.nd_canNotPed END AS nd_canInsPed";
//                strSQL+=" 	     , CASE WHEN c1.nd_preUniNotPed IS NULL THEN (CASE WHEN c1.nd_preUniPedEmb IS NULL THEN c1.nd_preUniMovInv ELSE c1.nd_preUniPedEmb END ) ELSE c1.nd_preUniNotPed END AS nd_preUniInsPed";
//                strSQL+=" 	     , CASE WHEN c1.nd_totNotPed IS NULL THEN (CASE WHEN c1.nd_totPedEmb IS NULL THEN c1.nd_totMovInv ELSE c1.nd_totPedEmb END ) ELSE c1.nd_totNotPed END AS nd_totInsPed";
//                strSQL+=" 	     , CASE WHEN c1.co_regNotPed IS NULL THEN (CASE WHEN c1.co_regPedEmb IS NULL THEN c1.co_regMovInv ELSE c1.co_regPedEmb END ) ELSE c1.co_regNotPed END AS co_reg";
//                strSQL+=" 	     , CASE WHEN c1.co_impNotPed IS NULL THEN (CASE WHEN c1.co_impPedEmb IS NULL THEN c1.co_impMovInv ELSE c1.co_impPedEmb END ) ELSE c1.co_impNotPed END AS co_impInsPed";
//                strSQL+=" 	     , CASE WHEN c1.tx_desCorTipDocNotPed IS NULL THEN (CASE WHEN c1.tx_desCorTipDocPedEmb IS NULL THEN c1.tx_desCorTipDocMovInv ELSE c1.tx_desCorTipDocPedEmb END ) ELSE c1.tx_desCorTipDocNotPed END AS tx_desCorTipDocPed";
//                strSQL+=" 	     , CASE WHEN c1.ne_insNotPed IS NULL THEN (CASE WHEN c1.ne_insPedEmb IS NULL THEN c1.ne_insMovInv ELSE c1.ne_insPedEmb END ) ELSE c1.ne_insNotPed END AS ne_insPed";
//                strSQL+=" 	     , CASE WHEN c1.tx_numDoc2NotPed IS NULL THEN (CASE WHEN c1.tx_numDoc2PedEmb IS NULL THEN c1.tx_numDoc2MovInv ELSE c1.tx_numDoc2PedEmb END ) ELSE c1.tx_numDoc2NotPed END AS tx_numDoc2Ped";
//                strSQL+=" 	     /*Nuevo*/, CASE WHEN c1.co_carPagNotPed IS NULL THEN (CASE WHEN c1.co_carPagPedEmb IS NULL THEN c1.co_carPagMovInv ELSE c1.co_carPagPedEmb END ) ELSE c1.co_carPagNotPed END AS co_carPagInsPed";
//                strSQL+=" 	FROM(";
//                strSQL+=" 		 SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc, b1.fe_doc, b1.nd_valComExt, b1.nd_valIntExt, b1.co_cli, b1.tx_ruc";
//                strSQL+=" 		      , b1.tx_nomCli, b1.nd_tot AS nd_totDoc, b1.st_reg, b2.co_regPedEmb, b3.co_regNotPed, b4.co_regMovInv";
//                strSQL+=" 		      , b2.co_empRelPedEmb, b2.co_locRelPedEmb, b2.co_tipDocRelPedEmb, b2.co_docRelPedEmb, b2.co_regRelPedEmb, b2.co_ctaActPedEmb, b2.co_ctaPasPedEmb, b2.tx_desDetPedEmb, b2.nd_canPedEmb, b2.nd_preUniPedEmb, b2.nd_totPedEmb, b2.co_impPedEmb";
//                strSQL+=" 		      , b3.co_empRelNotPed, b3.co_locRelNotPed, b3.co_tipDocRelNotPed, b3.co_docRelNotPed, b3.co_regRelNotPed, b3.co_ctaActNotPed, b3.co_ctaPasNotPed, b3.tx_desDetNotPed, b3.nd_canNotPed, b3.nd_preUniNotPed, b3.nd_totNotPed, b3.co_impNotPed";
//                strSQL+=" 		      , b4.co_empRelMovInv, b4.co_locRelMovInv, b4.co_tipDocRelMovInv, b4.co_docRelMovInv, b4.co_regRelMovInv, b4.co_ctaActMovInv, b4.co_ctaPasMovInv, b4.tx_desDetMovInv, b4.nd_canMovInv, b4.nd_preUniMovInv, b4.nd_totMovInv, b4.co_impMovInv";
//                strSQL+=" 		      , b2.ne_insPedEmb, b3.ne_insNotPed, b4.ne_insMovInv, b2.tx_desCorTipDocPedEmb, b3.tx_desCorTipDocNotPed, b4.tx_desCorTipDocMovInv";
//                strSQL+=" 		      , b2.tx_numDoc2PedEmb, b3.tx_numDoc2NotPed, b4.tx_numDoc2MovInv";
//                strSQL+=" 		      /*Nuevo*/, b3.co_carPagNotPed, b2.co_carPagPedEmb, b4.co_carPagMovInv";
//                strSQL+=" 		 FROM( /*DxP*/";
//                strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc";
//                strSQL+=" 			     , (CASE WHEN a1.nd_valComExt IS NULL THEN 0 ELSE a1.nd_valComExt END) AS nd_valComExt";
//                strSQL+=" 			     , (CASE WHEN a1.nd_valIntExt IS NULL THEN 0 ELSE a1.nd_valIntExt END) AS nd_valIntExt";                
//                strSQL+=" 			     , a1.co_cli, a1.tx_ruc, a1.tx_nomCli, a1.nd_tot, a1.st_reg";
//                strSQL+=" 			FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detConIntMovInv AS a2";
//                strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
//                strSQL+="                       WHERE a1.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + " AND a1.co_loc=" + INT_COD_LOC_ORD_PAG_EXT + "";
//                strSQL+="                       AND a1.co_tipDoc=" + INT_COD_TIP_DOC_ORD_PAG_EXT + " AND a1.co_doc=" + INT_COD_DOC_ORD_PAG_EXT + "";
//                strSQL+=" 		        AND a1.st_ciepagext IN('P', 'E') AND a1.st_reg='O'";
//                strSQL+=" 		 ) AS b1";
//                strSQL+=" 		 LEFT OUTER JOIN( /*Pedido Embarcado*/";
//                strSQL+=" 			SELECT a2.co_emp AS co_empDxP, a2.co_loc AS co_locDxP, a2.co_tipDoc AS co_tipDocDxP, a2.co_doc AS co_docDxP, a2.co_reg AS co_regPedEmb";
//                strSQL+=" 			     , a1.tx_des AS tx_desDetPedEmb, a1.nd_can AS nd_canPedEmb, a1.nd_preUni AS nd_preUniPedEmb, a1.nd_tot AS nd_totPedEmb";
//                strSQL+=" 			     , a2.co_empRelPedEmb, a2.co_locRelPedEmb, a2.co_tipDocRelPedEmb, a2.co_docRelPedEmb, a2.co_regRelPedEmb, a3.co_ctaAct AS co_ctaActPedEmb";
//                strSQL+=" 			     , a3.co_ctaPas AS co_ctaPasPedEmb, a3.co_imp AS co_impPedEmb, 1 AS ne_insPedEmb, a4.tx_desCor AS tx_desCorTipDocPedEmb, a3.tx_numDoc2 AS tx_numDoc2PedEmb";
//                strSQL+=" 			     , a6.co_carPag AS co_carPagPedEmb /*Nuevo*/";
//                strSQL+=" 			FROM tbm_detConIntMovInv AS a1 ";
//                strSQL+="                       INNER JOIN(tbr_detConIntCarPagInsImp AS a2 INNER JOIN tbm_carPagPedEmbImp AS a5";
//                strSQL+="                                  ON a2.co_empRelPedEmb=a5.co_emp AND a2.co_locRelPedEmb=a5.co_loc AND a2.co_tipDocRelPedEmb=a5.co_tipDoc AND a2.co_docRelPedEmb=a5.co_doc AND a2.co_regRelPedEmb=a5.co_reg";
//                strSQL+="                                  INNER JOIN tbm_carPagImp AS a6 ON a5.co_carPag=a6.co_carPag)";
//                strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg";
//                strSQL+=" 			INNER JOIN (tbm_cabPedEmbImp AS a3 INNER JOIN tbm_cabTipDoc AS a4";
//                strSQL+=" 			ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc)";
//                strSQL+=" 			ON a2.co_empRelPedEmb=a3.co_emp AND a2.co_locRelPedEmb=a3.co_loc AND a2.co_tipDocRelPedEmb=a3.co_tipDoc AND a2.co_docRelPedEmb=a3.co_doc";
//                strSQL+="                       WHERE a2.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + " AND a2.co_loc=" + INT_COD_LOC_ORD_PAG_EXT + "";
//                strSQL+="                       AND a2.co_tipDoc=" + INT_COD_TIP_DOC_ORD_PAG_EXT + " AND a2.co_doc=" + INT_COD_DOC_ORD_PAG_EXT + "";
//                strSQL+=" 		 ) AS b2";
//                strSQL+=" 		 ON b1.co_emp=b2.co_empDxP AND b1.co_loc=b2.co_locDxP AND b1.co_tipDoc=b2.co_tipDocDxP AND b1.co_doc=b2.co_docDxP AND b1.co_reg=b2.co_regPedEmb";
//                strSQL+=" 		 LEFT OUTER JOIN( /*Nota de Pedido*/";
//                strSQL+=" 			SELECT a2.co_emp AS co_empDxP, a2.co_loc AS co_locDxP, a2.co_tipDoc AS co_tipDocDxP, a2.co_doc AS co_docDxP, a2.co_reg AS co_regNotPed";
//                strSQL+=" 			     , a1.tx_des AS tx_desDetNotPed, a1.nd_can AS nd_canNotPed, a1.nd_preUni AS nd_preUniNotPed, a1.nd_tot AS nd_totNotPed";
//                strSQL+=" 			     , a2.co_empRelNotPed, a2.co_locRelNotPed, a2.co_tipDocRelNotPed, a2.co_docRelNotPed, a2.co_regRelNotPed, a3.co_ctaAct AS co_ctaActNotPed";
//                strSQL+=" 			     , a3.co_ctaPas AS co_ctaPasNotPed, a3.co_imp AS co_impNotPed, 2 AS ne_insNotPed, a4.tx_desCor AS tx_desCorTipDocNotPed, a3.tx_numDoc2 AS tx_numDoc2NotPed";
//                strSQL+=" 			     , a6.co_carPag AS co_carPagNotPed /*Nuevo*/";
//                strSQL+=" 			, a7.co_cta AS co_ctaISDNotPed";                
//                strSQL+=" 			FROM tbm_detConIntMovInv AS a1 ";
//                strSQL+="                       INNER JOIN( tbr_detConIntCarPagInsImp AS a2 INNER JOIN tbm_carPagNotPedImp AS a5";
//                strSQL+="                                   ON a2.co_empRelNotPed=a5.co_emp AND a2.co_locRelNotPed=a5.co_loc AND a2.co_tipDocRelNotPed=a5.co_tipDoc AND a2.co_docRelNotPed=a5.co_doc AND a2.co_regRelNotPed=a5.co_reg";
//                strSQL+=" 			INNER JOIN tbm_carPagImp AS a6 ON a5.co_carPag=a6.co_carPag)";                
//                strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg";
//                strSQL+=" 			INNER JOIN (tbm_cabNotPedImp AS a3 INNER JOIN tbm_cabTipDoc AS a4";
//                strSQL+=" 			ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc)";
//                strSQL+=" 			ON a2.co_empRelNotPed=a3.co_emp AND a2.co_locRelNotPed=a3.co_loc AND a2.co_tipDocRelNotPed=a3.co_tipDoc AND a2.co_docRelNotPed=a3.co_doc";
//                strSQL+="                       WHERE a2.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + " AND a2.co_loc=" + INT_COD_LOC_ORD_PAG_EXT + "";
//                strSQL+="                       AND a2.co_tipDoc=" + INT_COD_TIP_DOC_ORD_PAG_EXT + " AND a2.co_doc=" + INT_COD_DOC_ORD_PAG_EXT + "";
//                strSQL+=" 		) AS b3";
//                strSQL+=" 		ON b1.co_emp=b3.co_empDxP AND b1.co_loc=b3.co_locDxP AND b1.co_tipDoc=b3.co_tipDocDxP AND b1.co_doc=b3.co_docDxP AND b1.co_reg=b3.co_regNotPed";
//                strSQL+=" 		LEFT OUTER JOIN( /*Ingreso por Importación*/";
//                strSQL+=" 			SELECT a2.co_emp AS co_empDxP, a2.co_loc AS co_locDxP, a2.co_tipDoc AS co_tipDocDxP, a2.co_doc AS co_docDxP, a2.co_reg AS co_regMovInv";
//                strSQL+=" 			     , a1.tx_des AS tx_desDetMovInv, a1.nd_can AS nd_canMovInv, a1.nd_preUni AS nd_preUniMovInv, a1.nd_tot AS nd_totMovInv";
//                strSQL+=" 			     , a2.co_empRelMovInv, a2.co_locRelMovInv, a2.co_tipDocRelMovInv, a2.co_docRelMovInv, a2.co_regRelMovInv, a4.co_ctaAct AS co_ctaActMovInv";
//                strSQL+=" 			     , a4.co_ctaPas AS co_ctaPasMovInv, a3.co_emp AS co_impMovInv, 3 AS ne_insMovInv, a5.tx_desCor AS tx_desCorTipDocMovInv, a3.tx_numDoc2 AS tx_numDoc2MovInv";
//                strSQL+=" 			     , a6.co_carPag AS co_carPagMovInv /*Nuevo*/";
//                strSQL+=" 			FROM tbm_detConIntMovInv AS a1 ";
//                strSQL+="                       INNER JOIN (tbr_detConIntCarPagInsImp AS a2 INNER JOIN tbm_carPagMovInv AS a6";
//                strSQL+="                                   ON a2.co_empRelMovInv=a6.co_emp AND a2.co_locRelMovInv=a6.co_loc AND a2.co_tipDocRelMovInv=a6.co_tipDoc AND a2.co_docRelMovInv=a6.co_doc AND a2.co_regRelMovInv=a6.co_reg";
//                strSQL+="                                   INNER JOIN tbm_carPagImp AS a7 ON a6.co_carPag=a7.co_carPag)";
//                strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg";
//                strSQL+=" 			INNER JOIN (tbm_cabMovInv AS a3 INNER JOIN tbm_cabTipDoc AS a5";
//                strSQL+=" 			ON a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipDoc=a5.co_tipDoc)";
//                strSQL+=" 			ON a2.co_empRelMovInv=a3.co_emp AND a2.co_locRelMovInv=a3.co_loc AND a2.co_tipDocRelMovInv=a3.co_tipDoc AND a2.co_docRelMovInv=a3.co_doc";
//                strSQL+=" 			INNER JOIN tbm_cabPedEmbImp AS a4 ON a3.co_empRelPedEmbImp=a4.co_emp AND a3.co_locRelPedEmbImp=a4.co_loc AND a3.co_tipDocRelPedEmbImp=a4.co_tipDoc AND a3.co_docRelPedEmbImp=a4.co_doc";
//                strSQL+="                       WHERE a2.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + " AND a2.co_loc=" + INT_COD_LOC_ORD_PAG_EXT + "";
//                strSQL+="                       AND a2.co_tipDoc=" + INT_COD_TIP_DOC_ORD_PAG_EXT + " AND a2.co_doc=" + INT_COD_DOC_ORD_PAG_EXT + "";
//                strSQL+=" 		) AS b4";
//                strSQL+=" 		ON b1.co_emp=b4.co_empDxP AND b1.co_loc=b4.co_locDxP AND b1.co_tipDoc=b4.co_tipDocDxP AND b1.co_doc=b4.co_docDxP AND b1.co_reg=b4.co_regMovInv";
//                strSQL+=" 	) AS c1";
//                strSQL+=" ) AS d1";
//                strSQL+=" INNER JOIN tbm_carPagImp AS d2 ON d1.co_carPagInsPed=d2.co_carPag";
//                strSQL+=" ORDER BY d1.co_reg";
                
                
                strSQL="";
                strSQL+=" SELECT d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.co_reg, d1.ne_numDoc, d1.fe_doc, d1.nd_valComExt, d1.nd_valIntExt, d1.co_cli, d1.tx_ruc";
                strSQL+=" , d1.tx_nomCli, d1.nd_totDoc, d1.st_reg, d1.co_empInsPed, d1.co_locInsPed, d1.co_tipDocInsPed, d1.co_docInsPed, d1.co_regInsPed";
                strSQL+=" , d2.tx_nom AS tx_nomCarPag, d2.tx_tipCarPag, d1.co_ctaActInsPed, d1.co_ctaPasInsPed, d1.tx_desDetInsPed, d1.nd_canInsPed";
                strSQL+=" , d1.nd_preUniInsPed, d1.nd_totInsPed, d1.co_impInsPed, d1.ne_insPed, d1.tx_desCorTipDocPed, d1.tx_numDoc2Ped";
                strSQL+=" , d1.co_carPagInsPed/*Nuevo*/, d1.co_ctaISDInsPed";
                strSQL+=" FROM(";
                strSQL+="  	  SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.ne_numDoc, c1.fe_doc, c1.nd_valComExt, c1.nd_valIntExt, c1.co_cli, c1.tx_ruc";
                strSQL+="         , c1.tx_nomCli, c1.nd_totDoc, c1.st_reg";
                strSQL+="         , CASE WHEN c1.co_empRelNotPed IS NULL THEN (CASE WHEN c1.co_empRelPedEmb IS NULL THEN c1.co_empRelMovInv ELSE c1.co_empRelPedEmb END ) ELSE c1.co_empRelNotPed END AS co_empInsPed";
                strSQL+="         , CASE WHEN c1.co_locRelNotPed IS NULL THEN (CASE WHEN c1.co_locRelPedEmb IS NULL THEN c1.co_locRelMovInv ELSE c1.co_locRelPedEmb END ) ELSE c1.co_locRelNotPed END AS co_locInsPed";
                strSQL+="         , CASE WHEN c1.co_tipDocRelNotPed IS NULL THEN (CASE WHEN c1.co_tipDocRelPedEmb IS NULL THEN c1.co_tipDocRelMovInv ELSE c1.co_tipDocRelPedEmb END ) ELSE c1.co_tipDocRelNotPed END AS co_tipDocInsPed";
                strSQL+="         , CASE WHEN c1.co_docRelNotPed IS NULL THEN (CASE WHEN c1.co_docRelPedEmb IS NULL THEN c1.co_docRelMovInv ELSE c1.co_docRelPedEmb END ) ELSE c1.co_docRelNotPed END AS co_docInsPed";
                strSQL+="         , CASE WHEN c1.co_regRelNotPed IS NULL THEN (CASE WHEN c1.co_regRelPedEmb IS NULL THEN c1.co_regRelMovInv ELSE c1.co_regRelPedEmb END ) ELSE c1.co_regRelNotPed END AS co_regInsPed";
                strSQL+="         , CASE WHEN c1.co_ctaActNotPed IS NULL THEN (CASE WHEN c1.co_ctaActPedEmb IS NULL THEN c1.co_ctaActMovInv ELSE c1.co_ctaActPedEmb END ) ELSE c1.co_ctaActNotPed END AS co_ctaActInsPed";
                strSQL+="         , CASE WHEN c1.co_ctaPasNotPed IS NULL THEN (CASE WHEN c1.co_ctaPasPedEmb IS NULL THEN c1.co_ctaPasMovInv ELSE c1.co_ctaPasPedEmb END ) ELSE c1.co_ctaPasNotPed END AS co_ctaPasInsPed";
                strSQL+="         , CASE WHEN c1.tx_desDetNotPed IS NULL THEN (CASE WHEN c1.tx_desDetPedEmb IS NULL THEN c1.tx_desDetMovInv ELSE c1.tx_desDetPedEmb END ) ELSE c1.tx_desDetNotPed END AS tx_desDetInsPed";
                strSQL+="         , CASE WHEN c1.nd_canNotPed IS NULL THEN (CASE WHEN c1.nd_canPedEmb IS NULL THEN c1.nd_canMovInv ELSE c1.nd_canPedEmb END ) ELSE c1.nd_canNotPed END AS nd_canInsPed";
                strSQL+="         , CASE WHEN c1.nd_preUniNotPed IS NULL THEN (CASE WHEN c1.nd_preUniPedEmb IS NULL THEN c1.nd_preUniMovInv ELSE c1.nd_preUniPedEmb END ) ELSE c1.nd_preUniNotPed END AS nd_preUniInsPed";
                strSQL+="         , CASE WHEN c1.nd_totNotPed IS NULL THEN (CASE WHEN c1.nd_totPedEmb IS NULL THEN c1.nd_totMovInv ELSE c1.nd_totPedEmb END ) ELSE c1.nd_totNotPed END AS nd_totInsPed";
                strSQL+="         , CASE WHEN c1.co_regNotPed IS NULL THEN (CASE WHEN c1.co_regPedEmb IS NULL THEN c1.co_regMovInv ELSE c1.co_regPedEmb END ) ELSE c1.co_regNotPed END AS co_reg";
                strSQL+="         , CASE WHEN c1.co_impNotPed IS NULL THEN (CASE WHEN c1.co_impPedEmb IS NULL THEN c1.co_impMovInv ELSE c1.co_impPedEmb END ) ELSE c1.co_impNotPed END AS co_impInsPed";
                strSQL+="         , CASE WHEN c1.tx_desCorTipDocNotPed IS NULL THEN (CASE WHEN c1.tx_desCorTipDocPedEmb IS NULL THEN c1.tx_desCorTipDocMovInv ELSE c1.tx_desCorTipDocPedEmb END ) ELSE c1.tx_desCorTipDocNotPed END AS tx_desCorTipDocPed";
                strSQL+="         , CASE WHEN c1.ne_insNotPed IS NULL THEN (CASE WHEN c1.ne_insPedEmb IS NULL THEN c1.ne_insMovInv ELSE c1.ne_insPedEmb END ) ELSE c1.ne_insNotPed END AS ne_insPed";
                strSQL+="         , CASE WHEN c1.tx_numDoc2NotPed IS NULL THEN (CASE WHEN c1.tx_numDoc2PedEmb IS NULL THEN c1.tx_numDoc2MovInv ELSE c1.tx_numDoc2PedEmb END ) ELSE c1.tx_numDoc2NotPed END AS tx_numDoc2Ped";
                strSQL+="         , CASE WHEN c1.co_carPagNotPed IS NULL THEN (CASE WHEN c1.co_carPagPedEmb IS NULL THEN c1.co_carPagMovInv ELSE c1.co_carPagPedEmb END ) ELSE c1.co_carPagNotPed END AS co_carPagInsPed";
                strSQL+="         /*Nuevo*/, CASE WHEN c1.co_ctaISDNotPed IS NULL THEN (CASE WHEN c1.co_ctaISDPedEmb IS NULL THEN c1.co_ctaISDMovInv ELSE c1.co_ctaISDPedEmb END ) ELSE c1.co_ctaISDNotPed END AS co_ctaISDInsPed";
                strSQL+="  	FROM(";
                strSQL+="  		 SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc, b1.fe_doc, b1.nd_valComExt, b1.nd_valIntExt, b1.co_cli, b1.tx_ruc";
                strSQL+="  		 , b1.tx_nomCli, b1.nd_tot AS nd_totDoc, b1.st_reg, b2.co_regPedEmb, b3.co_regNotPed, b4.co_regMovInv";
                strSQL+="  		 , b2.co_empRelPedEmb, b2.co_locRelPedEmb, b2.co_tipDocRelPedEmb, b2.co_docRelPedEmb, b2.co_regRelPedEmb, b2.co_ctaActPedEmb, b2.co_ctaPasPedEmb, b2.tx_desDetPedEmb, b2.nd_canPedEmb, b2.nd_preUniPedEmb, b2.nd_totPedEmb, b2.co_impPedEmb";
                strSQL+="  		 , b3.co_empRelNotPed, b3.co_locRelNotPed, b3.co_tipDocRelNotPed, b3.co_docRelNotPed, b3.co_regRelNotPed, b3.co_ctaActNotPed, b3.co_ctaPasNotPed, b3.tx_desDetNotPed, b3.nd_canNotPed, b3.nd_preUniNotPed, b3.nd_totNotPed, b3.co_impNotPed";
                strSQL+="                , b4.co_empRelMovInv, b4.co_locRelMovInv, b4.co_tipDocRelMovInv, b4.co_docRelMovInv, b4.co_regRelMovInv, b4.co_ctaActMovInv, b4.co_ctaPasMovInv, b4.tx_desDetMovInv, b4.nd_canMovInv, b4.nd_preUniMovInv, b4.nd_totMovInv, b4.co_impMovInv";
                strSQL+="                , b2.ne_insPedEmb, b3.ne_insNotPed, b4.ne_insMovInv, b2.tx_desCorTipDocPedEmb, b3.tx_desCorTipDocNotPed, b4.tx_desCorTipDocMovInv";
                strSQL+="                , b2.tx_numDoc2PedEmb, b3.tx_numDoc2NotPed, b4.tx_numDoc2MovInv";
                strSQL+=" 		 , b3.co_carPagNotPed, b2.co_carPagPedEmb, b4.co_carPagMovInv";
                strSQL+="                /*Nuevo*/, b3.co_ctaISDNotPed, b2.co_ctaISDPedEmb, b4.co_ctaISDMovInv";
                strSQL+="                FROM( /*DxP*/";
                strSQL+="  			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc";
                strSQL+="                       , (CASE WHEN a1.nd_valComExt IS NULL THEN 0 ELSE a1.nd_valComExt END) AS nd_valComExt";
                strSQL+="                       , (CASE WHEN a1.nd_valIntExt IS NULL THEN 0 ELSE a1.nd_valIntExt END) AS nd_valIntExt";
                strSQL+="                       , a1.co_cli, a1.tx_ruc, a1.tx_nomCli, a1.nd_tot, a1.st_reg";
                strSQL+="                       FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detConIntMovInv AS a2";
                strSQL+="                       ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="                       WHERE a1.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + " AND a1.co_loc=" + INT_COD_LOC_ORD_PAG_EXT + "";
                strSQL+="                       AND a1.co_tipDoc=" + INT_COD_TIP_DOC_ORD_PAG_EXT + " AND a1.co_doc=" + INT_COD_DOC_ORD_PAG_EXT + "";
                strSQL+="                       AND a1.st_ciepagext IN('P', 'E') AND a1.st_reg='O'";
                strSQL+="               ) AS b1";
                strSQL+="               LEFT OUTER JOIN( /*Pedido Embarcado*/";
                strSQL+="			SELECT b1.co_empDxP, b1.co_locDxP, b1.co_tipDocDxP, b1.co_docDxP, b1.co_regPedEmb";
                strSQL+="                       , b1.tx_desDetPedEmb, b1.nd_canPedEmb, b1.nd_preUniPedEmb, b1.nd_totPedEmb";
                strSQL+="                       , b1.co_empRelPedEmb, b1.co_locRelPedEmb, b1.co_tipDocRelPedEmb, b1.co_docRelPedEmb, b1.co_regRelPedEmb, b1.co_ctaActPedEmb";
                strSQL+="                       , b1.co_ctaPasPedEmb, b1.co_impPedEmb, b1.ne_insPedEmb, b1.tx_desCorTipDocPedEmb, b1.tx_numDoc2PedEmb";
                strSQL+="                       , b1.co_carPagPedEmb";
                strSQL+="                       /*Nuevo*/, b2.co_cta AS co_ctaISDPedEmb";
                strSQL+="                       FROM(";
                strSQL+="				SELECT a2.co_emp AS co_empDxP, a2.co_loc AS co_locDxP, a2.co_tipDoc AS co_tipDocDxP, a2.co_doc AS co_docDxP, a2.co_reg AS co_regPedEmb";
                strSQL+="				, a1.tx_des AS tx_desDetPedEmb, a1.nd_can AS nd_canPedEmb, a1.nd_preUni AS nd_preUniPedEmb, a1.nd_tot AS nd_totPedEmb";
                strSQL+="                               , a2.co_empRelPedEmb, a2.co_locRelPedEmb, a2.co_tipDocRelPedEmb, a2.co_docRelPedEmb, a2.co_regRelPedEmb, a3.co_ctaAct AS co_ctaActPedEmb";
                strSQL+="                               , a3.co_ctaPas AS co_ctaPasPedEmb, a3.co_imp AS co_impPedEmb, 1 AS ne_insPedEmb, a4.tx_desCor AS tx_desCorTipDocPedEmb, a3.tx_numDoc2 AS tx_numDoc2PedEmb";
                strSQL+="                               , a6.co_carPag AS co_carPagPedEmb";
                strSQL+="                               FROM tbm_detConIntMovInv AS a1";
                strSQL+="                               INNER JOIN(tbr_detConIntCarPagInsImp AS a2 INNER JOIN tbm_carPagPedEmbImp AS a5";
                strSQL+="                               ON a2.co_empRelPedEmb=a5.co_emp AND a2.co_locRelPedEmb=a5.co_loc AND a2.co_tipDocRelPedEmb=a5.co_tipDoc AND a2.co_docRelPedEmb=a5.co_doc AND a2.co_regRelPedEmb=a5.co_reg";
                strSQL+="                               INNER JOIN tbm_carPagImp AS a6 ON a5.co_carPag=a6.co_carPag)";
                strSQL+="                               ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg";
                strSQL+="                               INNER JOIN (tbm_cabPedEmbImp AS a3 INNER JOIN tbm_cabTipDoc AS a4";
                strSQL+="                               ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc)";
                strSQL+="                               ON a2.co_empRelPedEmb=a3.co_emp AND a2.co_locRelPedEmb=a3.co_loc AND a2.co_tipDocRelPedEmb=a3.co_tipDoc AND a2.co_docRelPedEmb=a3.co_doc";
                strSQL+="                               WHERE a2.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + " AND a2.co_loc=" + INT_COD_LOC_ORD_PAG_EXT + "";
                strSQL+="                               AND a2.co_tipDoc=" + INT_COD_TIP_DOC_ORD_PAG_EXT + " AND a2.co_doc=" + INT_COD_DOC_ORD_PAG_EXT + "";
                strSQL+="                      ) AS b1";
                strSQL+="                      LEFT OUTER JOIN(";
                strSQL+="				SELECT * FROM tbr_carPagImpPlaCta AS a1";
                strSQL+="			) AS b2";
                strSQL+="                       ON b1.co_carPagPedEmb=b2.co_carPag AND b1.co_impPedEmb=b2.co_emp";
                strSQL+="               ) AS b2";
                strSQL+="               ON b1.co_emp=b2.co_empDxP AND b1.co_loc=b2.co_locDxP AND b1.co_tipDoc=b2.co_tipDocDxP AND b1.co_doc=b2.co_docDxP AND b1.co_reg=b2.co_regPedEmb";
                strSQL+="               LEFT OUTER JOIN( /*Nota de Pedido*/";
                strSQL+="                       SELECT b1.co_empDxP, b1.co_locDxP, b1.co_tipDocDxP, b1.co_docDxP, b1.co_regNotPed";
                strSQL+="                       , b1.tx_desDetNotPed, b1.nd_canNotPed, b1.nd_preUniNotPed, b1.nd_totNotPed";
                strSQL+="                       , b1.co_empRelNotPed, b1.co_locRelNotPed, b1.co_tipDocRelNotPed, b1.co_docRelNotPed, b1.co_regRelNotPed, b1.co_ctaActNotPed";
                strSQL+="                       , b1.co_ctaPasNotPed, b1.co_impNotPed, b1.ne_insNotPed, b1.tx_desCorTipDocNotPed, b1.tx_numDoc2NotPed";
                strSQL+="                       , b1.co_carPagNotPed";
                strSQL+="                       /*Nuevo*/, b2.co_cta AS co_ctaISDNotPed";
                strSQL+="                       FROM(";
                strSQL+="                               SELECT a2.co_emp AS co_empDxP, a2.co_loc AS co_locDxP, a2.co_tipDoc AS co_tipDocDxP, a2.co_doc AS co_docDxP, a2.co_reg AS co_regNotPed";
 		strSQL+="                               , a1.tx_des AS tx_desDetNotPed, a1.nd_can AS nd_canNotPed, a1.nd_preUni AS nd_preUniNotPed, a1.nd_tot AS nd_totNotPed";
                strSQL+="                               , a2.co_empRelNotPed, a2.co_locRelNotPed, a2.co_tipDocRelNotPed, a2.co_docRelNotPed, a2.co_regRelNotPed, a3.co_ctaAct AS co_ctaActNotPed";
                strSQL+="                               , a3.co_ctaPas AS co_ctaPasNotPed, a3.co_imp AS co_impNotPed, 2 AS ne_insNotPed, a4.tx_desCor AS tx_desCorTipDocNotPed, a3.tx_numDoc2 AS tx_numDoc2NotPed";
                strSQL+="                               , a6.co_carPag AS co_carPagNotPed";
                strSQL+="                               FROM tbm_detConIntMovInv AS a1";
                strSQL+="                               INNER JOIN( tbr_detConIntCarPagInsImp AS a2 INNER JOIN tbm_carPagNotPedImp AS a5";
                strSQL+="                                       ON a2.co_empRelNotPed=a5.co_emp AND a2.co_locRelNotPed=a5.co_loc AND a2.co_tipDocRelNotPed=a5.co_tipDoc AND a2.co_docRelNotPed=a5.co_doc AND a2.co_regRelNotPed=a5.co_reg";
                strSQL+="                                       INNER JOIN tbm_carPagImp AS a6";
                strSQL+="                                       ON a5.co_carPag=a6.co_carPag)";
                strSQL+="                               ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg";
                strSQL+="                               INNER JOIN (tbm_cabNotPedImp AS a3 INNER JOIN tbm_cabTipDoc AS a4";
                strSQL+="                               ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc )";
                strSQL+="                               ON a2.co_empRelNotPed=a3.co_emp AND a2.co_locRelNotPed=a3.co_loc AND a2.co_tipDocRelNotPed=a3.co_tipDoc AND a2.co_docRelNotPed=a3.co_doc";
                strSQL+="                               WHERE a2.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + " AND a2.co_loc=" + INT_COD_LOC_ORD_PAG_EXT + "";
                strSQL+="                               AND a2.co_tipDoc=" + INT_COD_TIP_DOC_ORD_PAG_EXT + " AND a2.co_doc=" + INT_COD_DOC_ORD_PAG_EXT + "";
                strSQL+="                       ) AS b1";
                strSQL+="			LEFT OUTER JOIN(";
                strSQL+="				SELECT * FROM tbr_carPagImpPlaCta AS a1";
                strSQL+="			) AS b2";
                strSQL+="                       ON b1.co_carPagNotPed=b2.co_carPag AND b1.co_impNotPed=b2.co_emp";
 		strSQL+="  		) AS b3";
                strSQL+=" 		ON b1.co_emp=b3.co_empDxP AND b1.co_loc=b3.co_locDxP AND b1.co_tipDoc=b3.co_tipDocDxP AND b1.co_doc=b3.co_docDxP AND b1.co_reg=b3.co_regNotPed";
                strSQL+="               LEFT OUTER JOIN( /*Ingreso por Importación*/";
                strSQL+="			SELECT b1.co_empDxP, b1.co_locDxP, b1.co_tipDocDxP, b1.co_docDxP, b1.co_regMovInv";
                strSQL+="                       , b1.tx_desDetMovInv, b1.nd_canMovInv, b1.nd_preUniMovInv, b1.nd_totMovInv";
                strSQL+="                       , b1.co_empRelMovInv, b1.co_locRelMovInv, b1.co_tipDocRelMovInv, b1.co_docRelMovInv, b1.co_regRelMovInv, b1.co_ctaActMovInv";
                strSQL+="                       , b1.co_ctaPasMovInv, b1.co_impMovInv, b1.ne_insMovInv, b1.tx_desCorTipDocMovInv, b1.tx_numDoc2MovInv";
                strSQL+="                       , b1.co_carPagMovInv";
                strSQL+="                       /*Nuevo*/, b2.co_cta AS co_ctaISDMovInv";
                strSQL+="                       FROM(";
                strSQL+="				SELECT a2.co_emp AS co_empDxP, a2.co_loc AS co_locDxP, a2.co_tipDoc AS co_tipDocDxP, a2.co_doc AS co_docDxP, a2.co_reg AS co_regMovInv";
                strSQL+="				, a1.tx_des AS tx_desDetMovInv, a1.nd_can AS nd_canMovInv, a1.nd_preUni AS nd_preUniMovInv, a1.nd_tot AS nd_totMovInv";
                strSQL+="                               , a2.co_empRelMovInv, a2.co_locRelMovInv, a2.co_tipDocRelMovInv, a2.co_docRelMovInv, a2.co_regRelMovInv, a4.co_ctaAct AS co_ctaActMovInv";
                strSQL+="                               , a4.co_ctaPas AS co_ctaPasMovInv, a3.co_emp AS co_impMovInv, 3 AS ne_insMovInv, a5.tx_desCor AS tx_desCorTipDocMovInv, a3.tx_numDoc2 AS tx_numDoc2MovInv";
                strSQL+="                               , a6.co_carPag AS co_carPagMovInv";
                strSQL+="                               FROM tbm_detConIntMovInv AS a1";
                strSQL+="                               INNER JOIN (tbr_detConIntCarPagInsImp AS a2 INNER JOIN tbm_carPagMovInv AS a6";
                strSQL+="                               	   ON a2.co_empRelMovInv=a6.co_emp AND a2.co_locRelMovInv=a6.co_loc AND a2.co_tipDocRelMovInv=a6.co_tipDoc AND a2.co_docRelMovInv=a6.co_doc AND a2.co_regRelMovInv=a6.co_reg";
                strSQL+="                               	   INNER JOIN tbm_carPagImp AS a7 ON a6.co_carPag=a7.co_carPag)";
                strSQL+="                               ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg";
                strSQL+="                               INNER JOIN (tbm_cabMovInv AS a3 INNER JOIN tbm_cabTipDoc AS a5";
                strSQL+="                               ON a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipDoc=a5.co_tipDoc)";
                strSQL+="                               ON a2.co_empRelMovInv=a3.co_emp AND a2.co_locRelMovInv=a3.co_loc AND a2.co_tipDocRelMovInv=a3.co_tipDoc AND a2.co_docRelMovInv=a3.co_doc";
                strSQL+="                               INNER JOIN tbm_cabPedEmbImp AS a4 ON a3.co_empRelPedEmbImp=a4.co_emp AND a3.co_locRelPedEmbImp=a4.co_loc AND a3.co_tipDocRelPedEmbImp=a4.co_tipDoc AND a3.co_docRelPedEmbImp=a4.co_doc";
                strSQL+="                               WHERE a2.co_emp=" + INT_COD_EMP_ORD_PAG_EXT + " AND a2.co_loc=" + INT_COD_LOC_ORD_PAG_EXT + "";
                strSQL+="                               AND a2.co_tipDoc=" + INT_COD_TIP_DOC_ORD_PAG_EXT + " AND a2.co_doc=" + INT_COD_DOC_ORD_PAG_EXT + "";
                strSQL+="                       ) AS b1";
                strSQL+="                       LEFT OUTER JOIN(";
                strSQL+="                       	SELECT * FROM tbr_carPagImpPlaCta AS a1";
                strSQL+="                       ) AS b2";
                strSQL+="                       ON b1.co_carPagMovInv=b2.co_carPag AND b1.co_impMovInv=b2.co_emp";
                strSQL+="               ) AS b4";
                strSQL+="               ON b1.co_emp=b4.co_empDxP AND b1.co_loc=b4.co_locDxP AND b1.co_tipDoc=b4.co_tipDocDxP AND b1.co_doc=b4.co_docDxP AND b1.co_reg=b4.co_regMovInv";
                strSQL+=" 	) AS c1";
                strSQL+=" ) AS d1";
                strSQL+=" INNER JOIN tbm_carPagImp AS d2 ON d1.co_carPagInsPed=d2.co_carPag";
                strSQL+=" ORDER BY d1.co_reg";
                System.out.println("strSQL - generaAsiDiaTRBAEX: " + strSQL);
                rstPedRelDxP=stmPedRelDxP.executeQuery(strSQL);
                while(rstPedRelDxP.next())
                {
                    bgdValBcoTrsExt=rstPedRelDxP.getBigDecimal("nd_totDoc");
                    
                    arlRegDetTrs=new ArrayList();
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_COD_EMP,     rstPedRelDxP.getInt("co_emp"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_COD_LOC,     rstPedRelDxP.getInt("co_loc"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_COD_TIP_DOC, rstPedRelDxP.getInt("co_tipDoc"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_COD_DOC,     rstPedRelDxP.getInt("co_doc"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_COD_REG,     rstPedRelDxP.getInt("co_reg"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_NUM_DOC,     rstPedRelDxP.getString("ne_numDoc"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_FEC_DOC,     rstPedRelDxP.getString("fe_doc"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_COD_CLI,     rstPedRelDxP.getInt("co_cli"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_NUM_RUC,     rstPedRelDxP.getString("tx_ruc"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_NOM_CLI,     rstPedRelDxP.getString("tx_nomCli"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_VAL_TOT,     rstPedRelDxP.getBigDecimal("nd_totDoc"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_VAL_COM,     rstPedRelDxP.getBigDecimal("nd_valComExt"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_VAL_INT,     rstPedRelDxP.getBigDecimal("nd_valIntExt"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_EST_REG,     rstPedRelDxP.getString("st_reg"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_DET_DSC,             rstPedRelDxP.getString("tx_nomCarPag"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_DET_CAN,             rstPedRelDxP.getBigDecimal("nd_canInsPed"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_DET_PRE_UNI,         rstPedRelDxP.getBigDecimal("nd_preUniInsPed"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_DET_TOT,             rstPedRelDxP.getBigDecimal("nd_totInsPed"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_COD_EMP_INS_PED,     rstPedRelDxP.getInt("co_empInsPed"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_COD_LOC_INS_PED,     rstPedRelDxP.getInt("co_locInsPed"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_COD_TIP_DOC_INS_PED, rstPedRelDxP.getInt("co_tipDocInsPed"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_COD_DOC_INS_PED,     rstPedRelDxP.getInt("co_docInsPed"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_COD_REG_CAR_PAG,     rstPedRelDxP.getInt("co_carPagInsPed"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_NOM_CAR_PAG,         rstPedRelDxP.getString("tx_nomCarPag"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_TIP_CAR_PAG,         rstPedRelDxP.getString("tx_tipCarPag"));
                    intCodCarPag=rstPedRelDxP.getInt("co_carPagInsPed");
                    if(intCodCarPag==INT_COD_CAR_PAG_ISD_CRE){
                        arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_COD_CTA_ACT_INS_PED, (objParSis.getCodCtaConImp(rstPedRelDxP.getInt("co_impInsPed"), getCodigoLocalPredeterminado(conexion, rstPedRelDxP.getInt("co_impInsPed")), INT_COD_IMP_ISD, datFecAux)));
//                        bgdValISDCreTriTrsExt=bgdValISDCreTriTrsExt.add(rstPedRelDxP.getBigDecimal("nd_totInsPed"));
                    }
                    else if(intCodCarPag==INT_COD_CAR_PAG_ISD_GAS){
                        arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_COD_CTA_ACT_INS_PED, (objParSis.getCodCtaConImp(rstPedRelDxP.getInt("co_impInsPed"), getCodigoLocalPredeterminado(conexion, rstPedRelDxP.getInt("co_impInsPed")), INT_COD_IMP_ISD, datFecAux)));
//                        bgdValISDGtoTrsExt=bgdValISDGtoTrsExt.add(rstPedRelDxP.getBigDecimal("nd_totInsPed"));
                    }
                    else{
                        arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_COD_CTA_ACT_INS_PED, rstPedRelDxP.getInt("co_ctaActInsPed"));
                        strGloDiaTrsExt+="" + rstPedRelDxP.getString("tx_desCorTipDocPed")+" "+ rstPedRelDxP.getString("tx_numDoc2Ped")+"/ ";
                    }
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_COD_CTA_PAS_INS_PED, rstPedRelDxP.getInt("co_ctaPasInsPed"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_INS_PED, rstPedRelDxP.getInt("ne_insPed"));
                    arlRegDetTrs.add(INT_ARL_DOC_PAG_EXT_COD_CTA_ISD, rstPedRelDxP.getInt("co_ctaISDInsPed"));
                                        
                    arlDatDetTrs.add(arlRegDetTrs);
                }
                stmPedRelDxP.close();
                stmPedRelDxP=null;
                rstPedRelDxP.close();
                rstPedRelDxP=null;                
                System.out.println("arlDatDetTrs: " + arlDatDetTrs.toString());
                
                //Obtener Cuenta de Comisiones
                stmValCom=conexion.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" FROM tbm_plaCta AS a1 INNER JOIN tbm_detTipDoc AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" WHERE a2.co_emp=" +INT_COD_EMP_TRS;
                strSQL+=" AND a2.co_loc="+INT_COD_LOC_TRS;
                strSQL+=" AND a2.co_tipDoc=" + INT_COD_TIP_DOC_TRS;
                System.out.println("Comisiones: "+strSQL);
                rstValCom=stmValCom.executeQuery(strSQL);
                if(rstValCom.next()){
                    intCtaValCom=rstValCom.getInt("co_cta");
                }
                stmValCom.close();
                stmValCom=null;
                rstValCom.close();
                rstValCom=null;      
                
                //Obtener Cuenta de Provisio Interes
                stmValCom=conexion.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" FROM tbm_plaCta AS a1 INNER JOIN tbr_carPagImpPlaCta AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" WHERE a2.co_emp=" +INT_COD_EMP_TRS;
                strSQL+=" AND a2.co_carPag="+INT_COD_CAR_PAG_INT_FAC;
                System.out.println("Interes: "+strSQL);
                rstValCom=stmValCom.executeQuery(strSQL);
                if(rstValCom.next()){
                    intCtaValProInt=rstValCom.getInt("co_cta");
                }
                stmValCom.close();
                stmValCom=null;
                rstValCom.close();
                rstValCom=null;      
                
                //Obtener Cuenta de Interes
                stmValCom=conexion.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" FROM tbm_plaCta AS a1 INNER JOIN tbr_carPagImpPlaCta AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_ctaPagExt";
                strSQL+=" WHERE a2.co_emp=" +INT_COD_EMP_TRS;
                strSQL+=" AND a2.co_carPag="+INT_COD_CAR_PAG_INT_FAC;
                System.out.println("Interes: "+strSQL);
                rstValCom=stmValCom.executeQuery(strSQL);
                if(rstValCom.next()){
                    intCtaValInt=rstValCom.getInt("co_cta");
                }
                stmValCom.close();
                stmValCom=null;
                rstValCom.close();
                rstValCom=null;                     
                
                //////////////////////////////////////////////////////////////////////////////////////////////////
                //Información para armar el diario de la transferencia bancaria al exterior
                //Armar asiento de diario
                
                //PEDIDO
                for(int i=0; i<arlDatDetTrs.size(); i++) { //solo carga informacion de pedido
                    
                    if(i==0){
                        bgdTotValCom=objUti.getBigDecimalValueAt(arlDatDetTrs, i, INT_ARL_DOC_PAG_EXT_VAL_COM); /*Obtener valor de comisión*/
                        bgdTotValInt=objUti.getBigDecimalValueAt(arlDatDetTrs, i, INT_ARL_DOC_PAG_EXT_VAL_INT); /*Obtener valor de interes*/
                    }
                    
                    intCodRegInsPed=objUti.getIntValueAt(arlDatDetTrs, i, INT_ARL_DOC_PAG_EXT_COD_REG_CAR_PAG);
                    intInsPed=objUti.getIntValueAt(arlDatDetTrs, i, INT_ARL_DOC_PAG_EXT_INS_PED);

                    System.out.println("intCodRegInsPed: " + intCodRegInsPed);
                    
                    if(intCodRegInsPed==INT_COD_CAR_PAG_ISD_CRE){
                        System.out.println("Credito Tributario");
                        //ISD - Credito Tributario
                        bgdTotValISDCreTri=bgdTotValISDCreTri.add(objUti.getBigDecimalValueAt(arlDatDetTrs, i, INT_ARL_DOC_PAG_EXT_DET_TOT));
                        intCtaISDCreTriInsPed=objUti.getIntValueAt(arlDatDetTrs, i, INT_ARL_DOC_PAG_EXT_COD_CTA_ISD);
                        
                        System.out.println("intCtaISDCreTriInsPed: " + intCtaISDCreTriInsPed);
                        System.out.println("bgdTotValISDCreTri: " + bgdTotValISDCreTri);
                        
                        vecRegDetTrs = new java.util.Vector();
                        vecRegDetTrs.add(INT_VEC_TRS_LIN, null);
                        vecRegDetTrs.add(INT_VEC_TRS_COD_CTA, intCtaISDCreTriInsPed);
                        vecRegDetTrs.add(INT_VEC_TRS_NUM_CTA, "");
                        vecRegDetTrs.add(INT_VEC_TRS_BUT, null);
                        vecRegDetTrs.add(INT_VEC_TRS_NOM_CTA, "ISD-CREDITO TRIBUTARIO");///para probar///
                        vecRegDetTrs.add(INT_VEC_TRS_DEB, bgdTotValISDCreTri);
                        vecRegDetTrs.add(INT_VEC_TRS_HAB, BigDecimal.ZERO);
                        vecRegDetTrs.add(INT_VEC_TRS_REF, "");
                        vecRegDetTrs.add(INT_VEC_TRS_EST_CON, "N");
                        vecDatDetTrs.add(vecRegDetTrs);
                        
                        bgdTotValISDCreTri=BigDecimal.ZERO;
                    }
                    else if(intCodRegInsPed==INT_COD_CAR_PAG_ISD_GAS){
                        System.out.println("Gastos");
                        //ISD - Gastos
                        bgdTotValISDGto=bgdTotValISDGto.add(objUti.getBigDecimalValueAt(arlDatDetTrs, i, INT_ARL_DOC_PAG_EXT_DET_TOT));
                        intCtaISDGtoInsPed=objUti.getIntValueAt(arlDatDetTrs, i, INT_ARL_DOC_PAG_EXT_COD_CTA_ISD);
                        
                        System.out.println("intCtaISDGtoInsPed: " + intCtaISDGtoInsPed);
                        System.out.println("bgdTotValISDGto: " + bgdTotValISDGto);
                        
                        vecRegDetTrs = new java.util.Vector();
                        vecRegDetTrs.add(INT_VEC_TRS_LIN, null);
                        vecRegDetTrs.add(INT_VEC_TRS_COD_CTA, intCtaISDGtoInsPed);
                        vecRegDetTrs.add(INT_VEC_TRS_NUM_CTA, "");
                        vecRegDetTrs.add(INT_VEC_TRS_BUT, null);
                        vecRegDetTrs.add(INT_VEC_TRS_NOM_CTA, "ISD-GASTOS");///para probar///
                        vecRegDetTrs.add(INT_VEC_TRS_DEB, bgdTotValISDGto);
                        vecRegDetTrs.add(INT_VEC_TRS_HAB, BigDecimal.ZERO);
                        vecRegDetTrs.add(INT_VEC_TRS_REF, "");
                        vecRegDetTrs.add(INT_VEC_TRS_EST_CON, "N");
                        vecDatDetTrs.add(vecRegDetTrs);
                        
                        bgdTotValISDGto=BigDecimal.ZERO;
                    }
                    else{
                        System.out.println("Valor factura");
                        bgdTotValPed=objUti.getBigDecimalValueAt(arlDatDetTrs, i, INT_ARL_DOC_PAG_EXT_DET_TOT); /*Obtener valor del pedido*/
                        System.out.println("bgdTotValPed:"+bgdTotValPed);
                        //ASIENTO DE DIARIO
                        vecRegDetTrs = new java.util.Vector();
                        vecRegDetTrs.add(INT_VEC_TRS_LIN, null);
                        //20Jun2019: Se realiza cambio en la cuenta contable del pedido en los TRBAEX, para que en vez de utilizar la cuenta de activo, utilice siempre la cuenta de pasivo sea NOTPED, PEDEMB O INIMPO.
                        vecRegDetTrs.add(INT_VEC_TRS_COD_CTA, objUti.getIntValueAt(arlDatDetTrs, i, INT_ARL_DOC_PAG_EXT_COD_CTA_PAS_INS_PED));
                        vecRegDetTrs.add(INT_VEC_TRS_NUM_CTA, "");
                        vecRegDetTrs.add(INT_VEC_TRS_BUT, null);
                        vecRegDetTrs.add(INT_VEC_TRS_NOM_CTA, "Pedido");
                        vecRegDetTrs.add(INT_VEC_TRS_DEB, bgdTotValPed);
                        vecRegDetTrs.add(INT_VEC_TRS_HAB, BigDecimal.ZERO);
                        vecRegDetTrs.add(INT_VEC_TRS_REF, "");
                        vecRegDetTrs.add(INT_VEC_TRS_EST_CON, "N");
                        vecDatDetTrs.add(vecRegDetTrs);
                        
                        bgdTotValPed=BigDecimal.ZERO;
                    }                                        
                }
                
                //Comisiones                
                if(bgdTotValCom.compareTo(new BigDecimal("0")) > 0) 
                {
                    vecRegDetTrs = new java.util.Vector();
                    vecRegDetTrs.add(INT_VEC_TRS_LIN, null);
                    vecRegDetTrs.add(INT_VEC_TRS_COD_CTA, intCtaValCom);
                    vecRegDetTrs.add(INT_VEC_TRS_NUM_CTA, "");
                    vecRegDetTrs.add(INT_VEC_TRS_BUT, null);
                    vecRegDetTrs.add(INT_VEC_TRS_NOM_CTA, "Comision");
                    vecRegDetTrs.add(INT_VEC_TRS_DEB, bgdTotValCom);
                    vecRegDetTrs.add(INT_VEC_TRS_HAB, BigDecimal.ZERO);
                    vecRegDetTrs.add(INT_VEC_TRS_REF, "");
                    vecRegDetTrs.add(INT_VEC_TRS_EST_CON, "N");
                    vecDatDetTrs.add(vecRegDetTrs);      
                }     
                
                //Interes                
                if(bgdTotValInt.compareTo(new BigDecimal("0")) > 0) 
                {
                    vecRegDetTrs = new java.util.Vector();
                    vecRegDetTrs.add(INT_VEC_TRS_LIN, null);
                    vecRegDetTrs.add(INT_VEC_TRS_COD_CTA, intCtaValInt);
                    vecRegDetTrs.add(INT_VEC_TRS_NUM_CTA, "");
                    vecRegDetTrs.add(INT_VEC_TRS_BUT, null);
                    vecRegDetTrs.add(INT_VEC_TRS_NOM_CTA, "Interes");
                    vecRegDetTrs.add(INT_VEC_TRS_DEB, bgdTotValInt);
                    vecRegDetTrs.add(INT_VEC_TRS_HAB, BigDecimal.ZERO);
                    vecRegDetTrs.add(INT_VEC_TRS_REF, "");
                    vecRegDetTrs.add(INT_VEC_TRS_EST_CON, "N");
                    vecDatDetTrs.add(vecRegDetTrs);      
                }                     
                
                //Provision Interes                
                if(bgdTotValInt.compareTo(new BigDecimal("0")) > 0) 
                {
                    vecRegDetTrs = new java.util.Vector();
                    vecRegDetTrs.add(INT_VEC_TRS_LIN, null);
                    vecRegDetTrs.add(INT_VEC_TRS_COD_CTA, intCtaValProInt);
                    vecRegDetTrs.add(INT_VEC_TRS_NUM_CTA, "");
                    vecRegDetTrs.add(INT_VEC_TRS_BUT, null);
                    vecRegDetTrs.add(INT_VEC_TRS_NOM_CTA, "Provision Interes");
                    vecRegDetTrs.add(INT_VEC_TRS_DEB, BigDecimal.ZERO);
                    vecRegDetTrs.add(INT_VEC_TRS_HAB, bgdTotValInt);
                    vecRegDetTrs.add(INT_VEC_TRS_REF, "");
                    vecRegDetTrs.add(INT_VEC_TRS_EST_CON, "N");
                    vecDatDetTrs.add(vecRegDetTrs);      
                }                       

                //BANCO
                vecRegDetTrs = new java.util.Vector();
                vecRegDetTrs.add(INT_VEC_TRS_LIN, null);
                vecRegDetTrs.add(INT_VEC_TRS_COD_CTA, INT_COD_CTA_BCO);
                vecRegDetTrs.add(INT_VEC_TRS_NUM_CTA, "");
                vecRegDetTrs.add(INT_VEC_TRS_BUT, null);
                vecRegDetTrs.add(INT_VEC_TRS_NOM_CTA, "Banco");///para probar///
                vecRegDetTrs.add(INT_VEC_TRS_DEB, BigDecimal.ZERO);
                vecRegDetTrs.add(INT_VEC_TRS_HAB, bgdValBcoTrsExt);
                vecRegDetTrs.add(INT_VEC_TRS_REF, "");
                vecRegDetTrs.add(INT_VEC_TRS_EST_CON, "N");
                vecDatDetTrs.add(vecRegDetTrs);

                objAsiDia.setGlosa("" + strGloDiaTrsExt);
                objAsiDia.setDetalleDiario(vecDatDetTrs);
                objAsiDia.setGeneracionDiario((byte)2);
                objAsiDia.getDetalleDiario();

                System.out.println("vecDatDetTrs: " + vecDatDetTrs.toString());
                System.out.println("getDetalleDiario: " + objAsiDia.getDetalleDiario());
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    /**
     * Función que permite obtener el número de la cuenta contable 
     * @return String del número de cuenta contable
     * <BR> false en caso que no se encuentre la cuenta contable ingresada
     */
    private String getNumeroCuentaContable(Connection conexion, int codigoEmpresa, int codigoCuenta){
        String strNumCtaCtb="";
        String strSQLAux="";
        Statement stmTmp;
        ResultSet rstTmp;
        try{
            if(conexion!=null){
                stmTmp=conexion.createStatement();
                strSQLAux="";
                strSQLAux+=" SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQLAux+=" FROM tbm_plaCta AS a1";
                strSQLAux+=" WHERE a1.co_emp=" + codigoEmpresa + "";
                strSQLAux+=" AND a1.co_cta=" + codigoCuenta + "";
                rstTmp=stmTmp.executeQuery(strSQLAux);
                if(rstTmp.next()){
                    strNumCtaCtb=rstTmp.getString("tx_codCta");
                }               
                stmTmp.close();
                stmTmp=null;
                rstTmp.close();
                rstTmp=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return strNumCtaCtb;
    }
    
    /**
     * Función que permite generar el documento de Transferencia Bancaria de Grupo
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean generaTransferBancariaGrupo(Connection conexion){
        boolean blnRes=false;
        int intSec=-1;
        int j=1;
        String strUpd="";
        Statement stmGenTrsBanGrp;
        try{
            if(conexion!=null){
                
                //Obtener la fecha del servidor.
                datFecAux=null;
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;

                if (objAsiDia.insertarDiario(conexion, INT_COD_EMP_TRS, INT_COD_LOC_TRS, INT_COD_TIP_DOC_TRS, ""+INT_COD_DOC_TRS, ""+INT_NUM_DOC_TRS, objUti.parseDate((objUti.formatearFecha(datFecAux,"dd/MM/yyyy")),"dd/MM/yyyy"), objParSis.getCodigoMenu()))
                {
                    if(objAsiDia.isDiarioCuadrado()){
                        stmGenTrsBanGrp=conexion.createStatement();
                        strSQL ="";
                        strSQL+=" INSERT INTO tbm_docgenotrmovban(";
                        strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_sec, co_emprelcabmovinv";
                        strSQL+=" , co_locrelcabmovinv, co_tipdocrelcabmovinv, co_docrelcabmovinv";
                        strSQL+=" , co_emprelcabpag, co_locrelcabpag, co_tipdocrelcabpag, co_docrelcabpag";
                        strSQL+=" , co_emprelcabdia, co_locrelcabdia, co_tipdocrelcabdia, co_docrelcabdia, st_regrep";
                        strSQL+=" )";
                        strSQL+=" VALUES (";
                        strSQL+="  " + INT_COD_EMP_OTR_MOV_BAN;//co_emp
                        strSQL+=", " + INT_COD_LOC_OTR_MOV_BAN;//co_loc
                        strSQL+=", " + INT_COD_TIP_DOC_OTR_MOV_BAN;//co_tipdoc
                        strSQL+=", " + INT_COD_DOC_OTR_MOV_BAN;//co_doc
                        strSQL+=", " + j;//co_reg
                        strSQL+=", " + intSec;//co_sec
                        strSQL+=", Null";//co_emprelcabmovinv
                        strSQL+=", Null";//co_locrelcabmovinv
                        strSQL+=", Null";//co_tipdocrelcabmovinv
                        strSQL+=", Null";//co_docrelcabmovinv
                        strSQL+=", Null";//co_emprelcabpag
                        strSQL+=", Null";//co_locrelcabpag
                        strSQL+=", Null";//co_tipdocrelcabpag
                        strSQL+=", Null";//co_docrelcabpag
                        strSQL+=", " + INT_COD_EMP_TRS + "";//co_emprelcabdia
                        strSQL+=", " + INT_COD_LOC_TRS + "";//co_locrelcabdia
                        strSQL+=", " + INT_COD_TIP_DOC_TRS + "";//co_tipdocrelcabdia
                        strSQL+=", " + INT_COD_DOC_TRS + "";//co_docrelcabdia
                        strSQL+=", 'I'";//st_regrep
                        strSQL+=");";
                        j++;
                        intSec++;
                        strUpd+=strSQL;
                        /////////////////////////////////////////////////////////////////////

                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+=" INSERT INTO tbm_cartrabandia(";
                        strSQL+="   co_emp, co_loc, co_tipdoc, co_dia, co_reg, fe_car, co_ctabanegr";
                        strSQL+=" , co_ctabaning, nd_valtra, tx_valtrapal, st_mosparresp, tx_mottra";
                        strSQL+=" , tx_ejeasi, st_fircon, tx_firaut1, tx_carfiraut1, tx_firaut2";
                        strSQL+=" , tx_carfiraut2, nd_adv, nd_fod, nd_valiva";
                        strSQL+=")";
                        strSQL+=" (SELECT ";
                        strSQL+="  " + INT_COD_EMP_TRS + " AS co_emp"; //co_emp
                        strSQL+=", " + INT_COD_LOC_TRS + " AS co_loc"; //co_loc
                        strSQL+=", " + INT_COD_TIP_DOC_TRS + " AS co_tipdoc"; //co_tipdoc
                        strSQL+=", " + INT_COD_DOC_TRS + " AS co_dia";        //co_dia
                        strSQL+=", " + INT_ULT_REG_CAR_TRA_BAN + " AS co_reg";   //co_reg
                        strSQL+=", '" + objUti.formatearFecha(DAT_FEC_CAR.toString(),"yyyy-MM-dd",objParSis.getFormatoFechaBaseDatos()) + "' AS fe_car";//fe_car
                        strSQL+=", " + INT_COD_CTA_BAN_EGR + " AS co_ctabanegr"; //co_ctabanegr    tbm_ctaBan.co_ctaBan
                        strSQL+=", " + INT_COD_CTA_BAN_ING + " AS co_ctabaning"; //co_ctabaning    tbm_ctaBan.co_ctaBan
                        strSQL+=", " + BIG_VAL_CAR + " AS nd_valtra";            //nd_valtra  es el valor de la carta y no el del documento
                        //Cantidad en palabras.
                        strSQL+=", " + objUti.codificar(getValorPalabras(BIG_VAL_CAR.toString())) + " AS tx_valtrapal";//tx_valtrapal
                        strSQL+=", Null AS st_mosParRes";//st_mosParRes
                        strSQL+=", Null AS tx_motTra";//tx_motTra
                        strSQL+=", a1.tx_ejeasi ";//tx_ejeasi
                        strSQL+=", a1.st_fircon ";//st_fircon
                        strSQL+=", a1.tx_firaut1";//tx_firaut1
                        strSQL+=", a1.tx_carfiraut1";//tx_carfiraut1
                        strSQL+=", a1.tx_firaut2";//tx_firaut2
                        strSQL+=", a1.tx_carfiraut2";//tx_carfiraut2
                        strSQL+=", Null AS nd_adv";//nd_adv              arancel
                        strSQL+=", Null AS nd_fod";//nd_fod             arancel
                        strSQL+=", Null AS nd_valiva";//nd_valiva           arancel
                        strSQL+=" from tbm_ctaban AS a1";
                        strSQL+=" where a1.co_emp=" + INT_COD_EMP_TRS + "";
                        strSQL+=" and a1.co_ctaban=" + INT_COD_CTA_BAN_EGR + "";  //objTblMod.getValueAt(i, INT_TBL_DAT_COD_CTA_BAN_EGR)
                        strSQL+=");";
                        strUpd+=strSQL;
                        System.out.println("insertarDiario: " +strUpd);
                        stmGenTrsBanGrp.executeUpdate(strUpd);
                        stmGenTrsBanGrp.close();
                        stmGenTrsBanGrp=null;
                        blnRes=true;
                    }
                    else{
                        blnRes=false;
                    }
                }
                else{
                    blnRes=false;
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }

    
    /**
     * Función que convierte el valor en número de la carta a palabras
     * @param valorConvertir : el valor a convertir
     * @return String : El valor en letras
     */
    private String getValorPalabras(String valorConvertir){
        String valPal="";
        try{
            Librerias.ZafUtil.ZafNumLetra numero;
            double cantidad= objUti.redondear(valorConvertir, objParSis.getDecimalesMostrar());
            String decimales=String.valueOf(cantidad).toString();
            decimales=decimales.substring(decimales.indexOf('.') + 1);
            decimales=(decimales.length()<2?decimales + "0":decimales.substring(0,2));
            int deci=Integer.parseInt(decimales);
            int m_pesos=(int)cantidad;
            numero = new Librerias.ZafUtil.ZafNumLetra(m_pesos);
            String res=numero.convertirLetras(m_pesos);
            res=res+" "+decimales+"/100  DOLARES";
            res=res.toUpperCase();
            valPal=res;

            numero=null;
        }
        catch(Exception e){  
            objUti.mostrarMsgErr_F1(cmpPad, e);  
        }
        return valPal;
    }
    
    
    
    /**
     * Función que permite insertar los datos de Otros movimientos bancarios y Pagos al Exterior
     * @return true si se pudo realizar la operación
     * <BR> false caso contrario
     */
    public boolean insertar(Connection conexion, ArrayList arregloDatos){//int intCodEmpOrdPagExt, int intCodLocOrdPagExt, int intCodTipDocOrdPagExt, int intCodDocOrdPagExt, int intCodBcoEgr
        boolean blnRes=false;
        try{
           if(conexion!=null){
               intTipErr=-1; /* Se inicializa la variable de error. */
               System.out.println("arregloDatos: " + arregloDatos.toString());
                for(int i=0; i<arregloDatos.size(); i++){
                    INT_COD_EMP_ORD_PAG_EXT=objUti.getIntValueAt(arregloDatos, i, objPagImp.INT_TBL_ARL_PAG_COD_EMP);
                    INT_COD_LOC_ORD_PAG_EXT=objUti.getIntValueAt(arregloDatos, i, objPagImp.INT_TBL_ARL_PAG_COD_LOC);
                    INT_COD_TIP_DOC_ORD_PAG_EXT=objUti.getIntValueAt(arregloDatos, i, objPagImp.INT_TBL_ARL_PAG_COD_TIPDOC);
                    INT_COD_DOC_ORD_PAG_EXT=objUti.getIntValueAt(arregloDatos, i, objPagImp.INT_TBL_ARL_PAG_COD_DOC);
                    BGD_VAL_DXP_EXT=objUti.getBigDecimalValueAt(arregloDatos, i, objPagImp.INT_TBL_ARL_PAG_VAL_DOC);
                    INT_COD_CTA_BCO=objUti.getIntValueAt(arregloDatos, i, objPagImp.INT_TBL_ARL_PAG_COD_BAN);
            
                    INT_COD_EMP_OTR_MOV_BAN=objParSis.getCodigoEmpresaGrupo();
                    INT_COD_LOC_OTR_MOV_BAN=getCodigoLocalPredeterminado(conexion, INT_COD_EMP_OTR_MOV_BAN);
                    INT_COD_TIP_DOC_OTR_MOV_BAN=objZafImp.INT_COD_TIP_DOC_OTR_MOV_BAN;
            
                    //DATOS DE CABECERA DE LA TRANSFERENCIA
                    INT_COD_EMP_TRS=INT_COD_EMP_ORD_PAG_EXT;
                    INT_COD_LOC_TRS=getCodigoLocalPredeterminado(conexion, INT_COD_EMP_TRS);
                    INT_COD_TIP_DOC_TRS=objZafImp.INT_COD_TIP_DOC_TRA_BAN_EXT;
                   
                    if(insertarReg(conexion)){
                        blnRes=true;
                    }
               }
           }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite actualizar el valor del abono en la tabla de pagos
     * @param conexion Connection a la base de datos
     * @param intCodEmp Código de la empresa
     * @param intCodLoc Código del local
     * @param intCodTipDoc Código de Tipo de documento
     * @param intCodDoc Código de documento
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean actualizaTbmPagMovInv(Connection conexion, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
        boolean blnRes=true;
        try{
            if(conexion!=null){
                stm=conexion.createStatement();
                strSQL="";
                strSQL+=" UPDATE tbm_pagMovInv";
                strSQL+=" SET nd_abo=(nd_abo - " + BGD_VAL_DXP_EXT + ")";
                strSQL+=" WHERE co_emp=" + intCodEmp + "";
                strSQL+=" AND co_loc=" + intCodLoc + "";
                strSQL+=" AND co_tipDoc=" + intCodTipDoc + "";
                strSQL+=" AND co_doc=" + intCodDoc + "";
                strSQL+=" AND co_reg=1";
                strSQL+=" AND st_reg='A'";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
            
            

    
}
