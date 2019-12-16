/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafGenDocCobAut;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
/**
 *
 * @author Sistemas6
 */
public class ZafGenDocCobAut {
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafAsiDia objAsiDia;
    
    private Vector vecRegDia, vecDatDia;
    final int INT_VEC_DIA_LIN=0;
    final int INT_VEC_DIA_COD_CTA=1;
    final int INT_VEC_DIA_NUM_CTA=2;
    final int INT_VEC_DIA_BUT_CTA=3;
    final int INT_VEC_DIA_NOM_CTA=4;
    final int INT_VEC_DIA_DEB=5;
    final int INT_VEC_DIA_HAB=6;
    final int INT_VEC_DIA_REF=7;
    final int INT_VEC_DIA_EST_CON=8;
    private Date dtpFecDoc;
    
    private java.awt.Component cmpPad;
    private String strVersion="ZafGenDocCobAut: v0.2", strSql;
    private int intNumDoc,intCodDoc,intCodReg,intCodTipDocDesSol=-1;
    private BigDecimal bdeMon;
    
    /**
     * Función que permite obtener el objeto ZafParSis
     * @param obj El objeto ZafParSis
     */
    public ZafGenDocCobAut(ZafParSis obj, java.awt.Component parent){
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            cmpPad=parent;
            objAsiDia = new ZafAsiDia(objParSis);
            vecDatDia=new Vector();
            System.out.println(strVersion);
        }
        catch (CloneNotSupportedException e){
            System.out.println("ZafGenDocCobAut: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
    }   
    
    
    

    /**
     * Clase creada para generar factura
     * 
     * @param conn
     * @param intCodSeg
     * @return 
     */
        

    public boolean generaDocumentoCobroAutomatico(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodTipDoc, int CodDoc, 
                                                    Date dtpFechaDocumento, int CodCfg){
        boolean blnRes=false;
        try{
            if(conExt!=null){
                System.out.println("codEmp "+CodEmp+" CodLoc "+CodLoc+" CodTipDoc "+CodTipDoc+" CodDoc "+ CodDoc);
                dtpFecDoc=dtpFechaDocumento;
                if(iniDia()){
                    if(insertarCabPag(conExt, CodEmp, CodLoc,CodTipDoc, CodDoc, CodCfg)){
                        if(insertarDetalle(conExt,CodEmp,CodLoc,CodTipDoc,CodDoc,CodCfg)){
                            if(genDiaTrs(CodCfg)){
                                if(objAsiDia.insertarDiario(conExt, CodEmp, CodLoc, intCodTipDocDesSol, String.valueOf(intCodDoc), String.valueOf(intNumDoc), dtpFecDoc)){
                                    System.out.println("generaDocumentoCobroAutomatico");
                                    blnRes=true;
                                }
                            }
                        }
                    }
                }
                
            }
        }
        catch(Exception  e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    
    private boolean iniDia(){
        boolean blnRes=true;
        try{
            objAsiDia.inicializar();
            vecDatDia.clear();
        }
        catch(Exception e){
            System.out.println("Error - iniDia: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    /**
     * Función que permite generar el diario de transferencia
     * @param codigoEmpresaDocumento Código de empresa del documento
     * @param codigoEmpresaCliente Código de empresa del cliente
     */
    private boolean genDiaTrs(int codigoConfiguracion){        
        boolean blnRes=true;
        try{
            System.out.println("****codigoConfiguracion (1 FACTURACION 2 DEVOLUCION): " + codigoConfiguracion);
            if(codigoConfiguracion==1){ /* FACTURAS */
                //Cuenta de debe
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "2304");//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "4.01.01.13");
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "Descto 2% Compensación Solidaria");
                vecRegDia.add(INT_VEC_DIA_DEB, "" + bdeMon.abs());
                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
                //Cuenta de haber
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "1035");//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.03");
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "CLIENTES MANTA");
                vecRegDia.add(INT_VEC_DIA_DEB, "0");
                vecRegDia.add(INT_VEC_DIA_HAB, "" + bdeMon.abs());
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
            }else if(codigoConfiguracion==2){  /* DEVOLUCIONES */
                //Cuenta de debe
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "1035");//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.03");
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "CLIENTES MANTA");
                vecRegDia.add(INT_VEC_DIA_DEB, "" + bdeMon.abs());
                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
                //Cuenta de haber
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "2304");//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "4.01.01.13");
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "Descto 2% Compensación Solidaria");
                vecRegDia.add(INT_VEC_DIA_DEB, "0");
                vecRegDia.add(INT_VEC_DIA_HAB, "" + bdeMon.abs());
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
            }
            
            System.out.println("*detalle diario:  " + objAsiDia.getDetalleDiario());
            objAsiDia.setDetalleDiario(vecDatDia);
            System.out.println("***detalle diario:  " + objAsiDia.getDetalleDiario());
            objAsiDia.setGeneracionDiario((byte)2);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    /**
     * 
     * 
     * 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodTipDoc
     * @param CodDoc
     * @param CodMnu
     * @return 
     */
    
    
    private boolean insertarDetalle(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodTipDoc, int CodDoc,int CodCfg){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+="  INSERT INTO tbm_detpag(co_emp, co_loc, co_tipdoc, co_doc, co_reg, \n";  
                strSql+="                         co_locpag, co_tipdocpag, co_docpag, co_regpag, nd_abo,  st_reg)  \n";  
                strSql+="  VALUES ( "+CodEmp+","+CodLoc+","+intCodTipDocDesSol+","+intCodDoc+",1,"+CodLoc+","+CodTipDoc+",  \n";  
                strSql+="           "+CodDoc+","+intCodReg+","+bdeMon+",'A'); \n";  
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
        }
        catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(cmpPad, Evt);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(cmpPad, Evt);
        }
         return blnRes;
    }
    
    
    
    /**
     * 
     * 
     * 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodTipDoc
     * @param CodDoc
     * @param CodMnu
     * @return 
     */
    
    
    private boolean insertarCabPag(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodTipDoc, int CodDoc,int CodCfg){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                
                /*
                 *  2;4;289;"COCSFA";"Cobro compensación solidaria (Facturas de ventas)"
                 *   2;4;290;"COCSDE";"Cobro compensación solidaria (Devoluciones de ventas)"
                 */
                
                if(CodCfg==1){
                    intCodTipDocDesSol=289;
                }
                else if(CodCfg==2){
                    intCodTipDocDesSol=290;
                }
                
                //código
                strSql="";
                strSql+="SELECT CASE WHEN MAX(a1.co_doc) IS NULL THEN 0 ELSE MAX(a1.co_doc) END AS co_doc FROM tbm_cabPag AS a1";
                strSql+=" WHERE a1.co_emp=" + CodEmp + "";
                strSql+=" AND a1.co_loc=" + CodLoc + "";
                strSql+=" AND a1.co_tipDoc=" + intCodTipDocDesSol + "";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                    intCodDoc=(Integer.parseInt(rstLoc.getObject("co_doc")==null?"":(rstLoc.getString("co_doc").equals("")?"":rstLoc.getString("co_doc")))) + 1;
                else
                    return false;
                //número
                strSql="";
                strSql+="SELECT a1.ne_ultDoc FROM tbm_cabTipDoc AS a1";
                strSql+=" WHERE a1.co_emp=" + CodEmp + "";
                strSql+=" AND a1.co_loc=" + CodLoc + "";
                strSql+=" AND a1.co_tipDoc=" + intCodTipDocDesSol + "";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                    intNumDoc=(Integer.parseInt(rstLoc.getObject("ne_ultDoc")==null?"":(rstLoc.getString("ne_ultDoc").equals("")?"":rstLoc.getString("ne_ultDoc")))) + 1;
                else
                    return false;
                
                //Monto
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_loc,a1.co_tipDoc,a1.co_doc, a1.co_cli,a1.tx_ruc,a1.tx_nomCli, a1.tx_dirCli,a2.co_reg, a2.nd_abo \n";        
                strSql+=" FROM tbm_cabMovInv as a1  \n";
                strSql+=" INNER JOIN tbm_pagMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND \n";
                strSql+="                                    a1.co_doc=a2.co_doc)  \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_tipDoc="+CodTipDoc+" AND \n";
                strSql+="       a1.co_doc="+CodDoc+" AND a2.tx_tipReg='S' /* Compensacion Solidaria */ \n";
                System.out.println("MONTO " +strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    bdeMon=rstLoc.getBigDecimal("nd_abo");
                    intCodReg=rstLoc.getInt("co_reg");
                }
                else
                    return false;
                 

                strSql="";
                strSql+=" INSERT INTO tbm_cabpag(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, fe_ven, ne_numdoc1, \n";  
                strSql+="                        ne_numdoc2, co_cta, co_cli, tx_ruc, tx_nomcli, tx_dircli, nd_mondoc,  \n";
                strSql+="                        tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod)  \n";
                strSql+=" VALUES ("+CodEmp+","+CodLoc+","+intCodTipDocDesSol+","+intCodDoc+",   \n";
                strSql+=" '"+ objUti.formatearFecha(dtpFecDoc, objParSis.getFormatoFechaBaseDatos()) +"', ";  //fe_doc
                strSql+=" '"+ objUti.formatearFecha(dtpFecDoc, objParSis.getFormatoFechaBaseDatos()) +"', ";  //fe_ven 
                strSql+=" "+intNumDoc+","+intNumDoc+",null,null,null,null,null,"+bdeMon+",null,'Generado por Zafiro',  \n";
                strSql+="'A',CURRENT_TIMESTAMP,null,1,null); ";
                System.out.println("ZafGenDocCobAut.insertarCabPag " + strSql);
                stmLoc.executeUpdate(strSql);
                
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
        }
        catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(cmpPad, Evt);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(cmpPad, Evt);
        }
         return blnRes;
    }
    
    /**
     * 
     * 
     * 
     * @param conExt
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodTipDoc
     * @param intCodDoc
     * @return 
     */
    
    private boolean noExisteCobroRelacionado(java.sql.Connection conExt,int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" select a2.*,a3.* \n";
                strSql+=" from tbm_cabPag as a1 \n";
                strSql+=" inner join tbm_detPag as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND  \n";
                strSql+="                                  a1.co_doc=a2.co_doc) \n";
                strSql+=" INNER JOIN tbm_pagMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND  \n";
                strSql+="                                    a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg)  \n";
                strSql+=" WHERE a3.co_emp="+intCodEmp+" AND a3.co_loc="+intCodLoc+" AND a3.co_tipDoc="+intCodTipDoc+" AND a3.co_doc="+intCodDoc+" AND \n";
                strSql+="        a3.tx_tipReg='S' /* Compensacion Solidaria */ ";
                System.out.println("noExisteCobroRelacionado ::: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    blnRes=false;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(cmpPad, Evt);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(cmpPad, Evt);
        }
         return blnRes;
    }

          /**
     * Esta función muestra un mensaje informativo al usuario. Se podróa utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(cmpPad,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
}
