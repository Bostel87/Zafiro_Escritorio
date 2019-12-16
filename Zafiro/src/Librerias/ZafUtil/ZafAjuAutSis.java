/*
 * ZafAjuAutSis.java
 *
 * CLASE PARA GENERAR AJUSTE DE CENTAVOS AUTOMATICOS
 *
 * Created on 26 de diciembre de 2007, 12:15
 * VERSION 1.0
 * DARIO CARDENAS LANDIN
 * MODIFICADO POR DARIO CARDENAS EL 02 DE DICIEMBRE DEL 2009
 */

package Librerias.ZafUtil;
import Librerias.ZafParSis.ZafParSis;
///import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author  dcardenas
 */
public class ZafAjuAutSis 
{
    //Declar<cion de Variables//
    private ZafParSis objZafParSis;
    private ZafUtil objUti;
    private Vector vecDet, vecDetDiario;
    private java.sql.Connection conCab, con, conCnsDet,conAnu, conAux;
    private java.sql.Statement stmCab, stm, stmCnsDet, stmAnu, stmAux1, stmAux2, stmAux3, stmAux4;
    private java.sql.ResultSet rstCab, rst, rstCnsDet, rstAux1, rstAux2, rstAux3, rstAux4;
    private String strSQL="", strAux="", strSQLCon="", strFecAct="";
    private String STRCODTIPDOC="";
    private int INTCODTIPDOCAJU=80;
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.
    private int INTNUMDOC=0, CANTIDAD=0, CODDOCPAG=0, ultCodDoc=0, cantreg=0, CANT=0, INTCODDOC=0, VALCANT=0, INTDOCCOD=0, TIPDOCPAG=80;
    private double VALREG=0.00, VALREGABO=0.00, VALREGELI=0.00, ValReg=0.00, sumValReg=0.00, VALDOC=0.00, VALTOTAJUAUT=0.00, SUMVALABO=0.00;
    private String strCodLoc="", strCodTipDoc="", strCodDoc="", strCodReg="";
    private String CODLOC="", CODTIPDOC="", CODDOC="", CODREG="", FECDOCAUT="", CODDOCINS="", STRFECDOC="", STRFECPAG="", CODDOCINSDIA="";
    
    private final int intCodigoMenu = 452;
    //***************
      ///private ZafAsiDia objDiario;
      Librerias.ZafAsiDia.ZafAsiDia objDiario;
      Librerias.ZafUtil.ZafCtaCtb_Aju_Cen objCtaCtb, objCtaCtbDeb, objCtaCtbHab;   //  PARA OBTENER  LAS CUENTAS
      ZafVenCon objVenConTipdoc; //*****************  
    //***************
      
    
    /** Creates a new instance of ZafAjuAutSis */
    public ZafAjuAutSis(ZafParSis obj) 
    {
        
        objZafParSis = obj;
        this.objZafParSis = obj;
        
        objUti = new ZafUtil();
        vecDet = new Vector();

        objDiario=new ZafAsiDia(objZafParSis);

        ///////asiento mejorado////
        objDiario.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() 
        {
            public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) 
            {
                 if (STRCODTIPDOC.equals(""))
                    objDiario.setCodigoTipoDocumento(-1);
                 else
                    objDiario.setCodigoTipoDocumento(INTCODTIPDOCAJU);
            }
        });
    }
    
    
    ///public int mostrarTipDocPre()//
    public boolean mostrarTipDocPre()
    {
        boolean blnRes=true;
        String strCodTipDoc="";
        int intNumDoc=0;
        try
        {
            conAux=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            conAux.setAutoCommit(false);
            
            if (conAux!=null)
            {
                stm=conAux.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objZafParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc = " + INTCODTIPDOCAJU;
                rst=stm.executeQuery(strSQL);
//                System.out.println("---ZafAjuAutSis---Query para el tipdoc es: " + strSQL);
                if (rst.next())
                {
                    strCodTipDoc = (rst.getString("co_tipDoc"));
                    intNumDoc = (rst.getInt("ne_ultDoc"));
                }
                STRCODTIPDOC = strCodTipDoc;
                INTNUMDOC = intNumDoc;
                INTCODDOC = intNumDoc;                
//                System.out.println("---ZafAjuAutSis---strCodTipDoc--STRCODTIPDOC: " + strCodTipDoc);
//                System.out.println("---ZafAjuAutSis---ULTIMO NUMERO DE DOCUMENTO---intNumDoc---INTNUMDOC: " + intNumDoc);
                rst.close();
                stm.close();
                conAux.close();
                rst=null;
                stm=null;
                conAux=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch (Exception e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;
//        return intNumDoc;
    }
    
    
    //Funcion de Prueba_2 para ser llamada desde otro programa///
    public String retNomEmp(int codEmp)
    {
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";
        try{
            conTipDoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(conTipDoc!=null)
            {
                stmTipDoc=conTipDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                
                que="";
                que+=" select tx_nom from tbm_emp";
                que+=" where co_emp=" + codEmp + "";
//                System.out.println("---query nombre de la empresa:"+que);
                
                rstTipDoc=stmTipDoc.executeQuery(que);
                
                if (rstTipDoc.next())
                {
                    auxTipDoc=rstTipDoc.getString("tx_nom");
                }
                
                stmTipDoc.close();
                stmTipDoc=null;
                rstTipDoc.close();
                rstTipDoc=null;
                conTipDoc.close();
                conTipDoc=null;
            }
            
        }        
        catch(java.sql.SQLException e) 
        {
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch(Exception e) 
        {
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }        
        return auxTipDoc;
    }
    //Fin de la funcion prueba///
    
    
    //////////////////funcion prueba de mostrarReg///////////////////
    public boolean consultarReg()
    {
        int intCodEmp, intCodLoc, intCodMnu, intCodTipDoc, x=4, val=0, Valcant=0, codDoc=0;
        double dblValPnd=0.00, valDes=0, valHas=0, valreg=0.00, sumValAbo=0.00;
        boolean blnRes=true;
        String strTit="", strMsg="", strFecDoc="";
        //double dblValPnd=0.00;
        try
        {
            System.out.println(" ");
            System.out.println(" ---FUNCION consultarReg()---");

            conAux=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());

            if (conAux!=null)
            {
                stm=conAux.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmAux1=conAux.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmAux2=conAux.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                intCodEmp=objZafParSis.getCodigoEmpresa();
                intCodLoc=objZafParSis.getCodigoLocal();
                intCodMnu=objZafParSis.getCodigoMenu();
                ///intCodTipDoc=objParSis.getCodigoMenu();

                //Para los demï¿½s modos se muestra: sï¿½lo los documentos pagados.
                strSQL="";
                strSQL+=" SELECT COUNT(*) AS CANTIDAD";
                strSQL+=" FROM ( ";
                strSQL+=" SELECT a1.co_emp as CodEmp, a1.co_loc as CodLoc, a1.co_tipDoc, a1.co_doc, a2.co_reg, ";
                strSQL+=" a1.ne_numDoc1 as NumDoc, a1.fe_doc as FecDoc, a2.nd_abo AS Abono";
                strSQL+=" FROM tbm_cabPag AS a1 ";
                strSQL+=" INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                strSQL+=" WHERE a1.st_reg IN ('A','C') ";
                strSQL+=" AND a1.co_emp = " + intCodEmp;
                strSQL+=" AND a1.co_loc = " + intCodLoc;
                strSQL+=" AND a1.co_tipdoc = " + INTCODTIPDOCAJU;
                ///strSQL+=" AND a2.co_doc = " + INTCODDOC;
                strSQL+=" AND a1.ne_numdoc1 = " + INTCODDOC;
                strSQL+=" ORDER BY a1.co_emp, a2.co_reg ";
                strSQL+=" ) AS P";
                rst=stm.executeQuery(strSQL);
//                System.out.println("---COUNT(*)---en mostrarReg: " + strSQL);

                if(rst.next())
                {
                    Valcant = rst.getInt("CANTIDAD");
                }

                VALCANT = Valcant;

//                System.out.println("---en funcion consultarReg VALCANT DEL AJUSTE--: " + VALCANT);
                
                
                strSQL="";
                strSQL+=" SELECT MAX(CodDoc) as MaxCodDoc";
                strSQL+=" FROM (";
                strSQL+=" SELECT a1.co_emp as CodEmp, a1.co_loc as CodLoc, a1.co_tipDoc, a1.co_doc AS CodDoc, a2.co_reg AS CodReg, ";
                strSQL+=" a1.ne_numDoc1 as NumDoc, a1.fe_doc as FecDoc, a2.nd_abo AS Abono";
                strSQL+=" FROM tbm_cabPag AS a1 ";
                strSQL+=" INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                strSQL+=" WHERE a1.st_reg IN ('A','C') ";
                strSQL+=" AND a1.co_emp = " + intCodEmp;
                strSQL+=" AND a1.co_tipdoc = " + INTCODTIPDOCAJU;
                ///strSQL+=" AND a2.co_doc = " + INTCODDOC;
                strSQL+=" AND a1.ne_numdoc1 = " + INTCODDOC;
                strSQL+=" AND a1.st_reg IN ('A','C')";
                strSQL+=" ORDER BY a1.co_emp, a2.co_reg ";
                strSQL+=" ) as Z";
//                System.out.println("---funcion consultarReg MAXCODDOC es--: " + strSQL);

                rstAux1=stmAux1.executeQuery(strSQL);
                
                if(rstAux1.next())
                {
                    //valreg = rstAux1.getDouble("ValPndDetPag");
                    codDoc = rstAux1.getInt("MaxCodDoc");
                }
                INTDOCCOD = codDoc;

//                System.out.println("---en funcion consultarReg INTDOCCOD DEL AJUSTE--: " + INTDOCCOD);
                
                
                
                strSQL="";
                strSQL+=" SELECT sum(a1.nd_abo) AS Abono, a2.fe_doc AS FecDoc";
                strSQL+=" FROM tbm_detPag AS a1 ";
                strSQL+=" INNER JOIN tbm_cabpag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                strSQL+=" WHERE a2.st_reg IN ('A','C') ";
                strSQL+=" AND a2.co_emp = " + intCodEmp;
                strSQL+=" AND a2.co_loc = " + intCodLoc;
                strSQL+=" AND a2.co_tipdoc = " + INTCODTIPDOCAJU;
                ///strSQL+=" AND a2.co_doc = " + INTCODDOC;
                strSQL+=" AND a2.co_doc = " + INTDOCCOD;
                strSQL+=" GROUP BY a2.fe_doc ";
//                System.out.println("---funcion consultarReg de AJUSTES sumAbo es--: " + strSQL);
                rstAux2=stmAux2.executeQuery(strSQL);
                
                if(rstAux2.next())
                {
                    //valreg = rstAux1.getDouble("ValPndDetPag");
                    sumValAbo = rstAux2.getDouble("Abono");
                    strFecDoc = rstAux2.getString("FecDoc");
                }
                SUMVALABO = sumValAbo;
                STRFECDOC = strFecDoc;
//                System.out.println("---en funcion consultarReg SUMVALABO--: " + SUMVALABO + " ---STRFECDOC: " + STRFECDOC);
                          
                stm.close();
                stm=null;
                
                stmAux1.close();
                stmAux1=null;

                stmAux2.close();
                stmAux2=null;
                
                rst.close();
                rst=null;
                
                rstAux1.close();
                rstAux1=null;
                
                rstAux2.close();
                rstAux2=null;
                
                conAux.close();
                conAux=null;
            }
            
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch (Exception e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;
    }
    /////////////////final de funcion mostrarReg///////////////////
    
    
    //////////////////funcion prueba de mostrarReg///////////////////
    public int mostrarReg(int codloc, int tipdoc, int coddoc)
    {
        int intCodEmp, intCodLoc, intCodMnu, intCodTipDoc, x=4, val=0, cant=0;
        double dblValPnd=0.00, valDes=0, valHas=0, valreg=0.00;
        boolean blnRes=true;
        String strTit, strMsg;
        //double dblValPnd=0.00;
        try
        {
            System.out.println(" ");
            System.out.println(" ---FUNCION mostrarReg()---");

            conAux=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conAux!=null)
            {
                stmAux1=conAux.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stm=conAux.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                intCodEmp=objZafParSis.getCodigoEmpresa();
                intCodLoc=objZafParSis.getCodigoLocal();
                intCodMnu=objZafParSis.getCodigoMenu();
                ///intCodTipDoc=objParSis.getCodigoMenu();

                //Para los demï¿½s modos se muestra: sï¿½lo los documentos pagados.
                strSQL="";
                strSQL+=" SELECT COUNT(*) AS CANTIDAD";
                strSQL+=" FROM ( ";                    
                strSQL+="SELECT a1.co_emp as CodEmp, a1.co_loc as CodLoc, a1.co_cli as CodCli, a4.st_cli, a4.st_prv, a4.tx_nom, a1.co_tipDoc, a3.tx_desCor as DesCorDoc,";
                strSQL+=" a3.tx_desLar as DesLarDoc, a1.co_doc, a2.co_reg, a1.ne_numDoc as NumDoc, a1.fe_doc as FecDoc, a3.tx_natdoc, ";
                strSQL+=" round(a2.nd_porret,2) as PorRet, a2.mo_pag as MonDoc, a2.nd_abo AS Abono, round((a2.mo_pag+a2.nd_abo),7) AS ValPnd, ";
                strSQL+=" (-1*round((a2.mo_pag+a2.nd_abo),7)) AS ValPndDetPag ";
                strSQL+=" FROM tbm_cabMovInv AS a1 ";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                //strSQL+=" INNER JOIN tbr_tipdocprg AS a5 ON (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc) ";
                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 ";
                strSQL+=" AND (a2.mo_pag+a2.nd_abo) between -0.03 and 0.03 ";
                strSQL+=" AND a1.co_emp= " + intCodEmp;
                strSQL+=" AND a1.co_loc= " + codloc; 
                strSQL+=" AND a1.co_tipdoc= " + tipdoc;
                strSQL+=" AND a1.co_doc= " + coddoc;
                strSQL+=" ORDER BY a1.co_emp, a1.co_cli, a1.co_tipdoc, a1.ne_numdoc, a2.co_reg ";
                strSQL+=" ) AS P";
                rst=stm.executeQuery(strSQL);
                ///System.out.println("---COUNT(*)---en mostrarReg: " + strSQL);

                if(rst.next())
                {
                    cant = rst.getInt("CANTIDAD");
                }

                CANTIDAD = cant;

//                System.out.println("---en funcion mostrarReg CANTIDAD--: " + CANTIDAD);
                
                
                //Para los demï¿½s modos se muestra: sï¿½lo los documentos pagados.
                strSQL="";
                strSQL+="SELECT a1.co_emp as CodEmp, a1.co_loc as CodLoc, a1.co_cli as CodCli, a4.st_cli, a4.st_prv, a4.tx_nom, a1.co_tipDoc, a3.tx_desCor as DesCorDoc,";
                strSQL+=" a3.tx_desLar as DesLarDoc, a1.co_doc, a2.co_reg, a1.ne_numDoc as NumDoc, a1.fe_doc as FecDoc, a3.tx_natdoc, ";
                strSQL+=" round(a2.nd_porret,2) as PorRet, a2.mo_pag as MonDoc, a2.nd_abo AS Abono, round((a2.mo_pag+a2.nd_abo),7) AS ValPnd, ";
                strSQL+=" (-1*round((a2.mo_pag+a2.nd_abo),7)) AS ValPndDetPag ";
                strSQL+=" FROM tbm_cabMovInv AS a1 ";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                //strSQL+=" INNER JOIN tbr_tipdocprg AS a5 ON (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc) ";
                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 ";
                strSQL+=" AND (a2.mo_pag+a2.nd_abo) between -0.03 and 0.03 ";
                strSQL+=" AND a1.co_emp= " + intCodEmp;
                strSQL+=" AND a1.co_loc= " + codloc; 
                strSQL+=" AND a1.co_tipdoc= " + tipdoc;
                strSQL+=" AND a1.co_doc= " + coddoc;
                ///strSQL+=strAux;
                strSQL+=" ORDER BY a1.co_emp, a1.co_cli, a1.co_tipdoc, a1.ne_numdoc, a2.co_reg ";
//                System.out.println("---funcion mostrarReg en--: " + strSQL);

                rstAux1=stmAux1.executeQuery(strSQL);
                if(rstAux1.next())
                {
                    valreg = rstAux1.getDouble("ValPndDetPag");
                }
                VALREG = valreg;

//                System.out.println("---en funcion mostrarReg VALREG--: " + VALREG);
                
                stm.close();
                stm=null;
                
                stmAux1.close();
                stmAux1=null;
                
                rst.close();
                rst=null;
                
                rstAux1.close();
                rstAux1=null;
                
                conAux.close();
                conAux=null;
            }
            
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch (Exception e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return CANTIDAD;
    }
    /////////////////final de funcion mostrarReg///////////////////
    
  
    public boolean mostrarDatIns()
    {
        int intCodEmp, intCodLoc, intCodMnu, intCodTipDoc, x=4, val=0, cant=0;
        double dblValPnd=0.00, valDes=0, valHas=0, valreg=0.00, valdoc=0.00;
        boolean blnRes=true;
        String strTit, strMsg, strcoddoc="", strnumdoc="", strFecDoc="", coddoc="";
        //double dblValPnd=0.00;
        try
        {
            System.out.println(" ");
            System.out.println(" ---FUNCION mostrarDatIns()---");

                conAux=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());

                if (conAux!=null)
                {
                    stmAux1=conAux.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stm=conAux.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    intCodEmp=objZafParSis.getCodigoEmpresa();
                    intCodLoc=objZafParSis.getCodigoLocal();
                    intCodMnu=objZafParSis.getCodigoMenu();

                    System.out.println(" ");

                    //Para los demï¿½s modos se muestra: sï¿½lo los documentos pagados.
                    strSQL="";
                    strSQL+="SELECT max(a1.co_doc) as CodDoc";
                    strSQL+=" FROM tbm_cabPag AS a1 ";
                    strSQL+=" WHERE a1.st_reg IN ('A','C','R','F') ";
                    strSQL+=" AND a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc; ////se quito filtro por empresa////
                    strSQL+=" AND a1.co_tipdoc = " + TIPDOCPAG;
//                    System.out.println("---objAjuAutSis()--funcion mostrarDatIns() max tbm_cabPag.CodDoc en--: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                    {
                        coddoc = rst.getString("CodDoc");
                    }

                    System.out.println(" ");

                    //Para los demï¿½s modos se muestra: sï¿½lo los documentos pagados.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp as CodEmp, a1.co_loc as CodLoc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc, a1.nd_mondoc ";
                    strSQL+=" FROM tbm_cabPag AS a1 ";
                    strSQL+=" WHERE a1.st_reg IN ('A','C','R','F') ";
                    //strSQL+=" AND a4.st_prv " + txtCanDes.getText();
                    strSQL+=" AND a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc; ////se quito filtro por empresa////
                    ///strSQL+=" AND a1.co_tipdoc in (1,3,7,9,18,19,20,21,22,23,24,25,26,27,28,29,31,34,39,40,43,45,51)";
                    strSQL+=" AND a1.co_tipdoc = " + TIPDOCPAG;
                    strSQL+=" AND a1.co_doc = " + coddoc;
                    ///strSQL+=strAux;
                    strSQL+=" ORDER BY a1.co_emp, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1 ";
//                    System.out.println("---objAjuAutSis()--funcion mostrarDatIns en--: " + strSQL);

                    rstAux1=stmAux1.executeQuery(strSQL);

                    ///if(rstAux1.next())
                    while(rstAux1.next())
                    ///for(int i=0;rstAux1.next();i++)
                    {
                        strcoddoc = rstAux1.getString("co_doc");
                        strnumdoc = rstAux1.getString("ne_numdoc1");
                        strFecDoc = objUti.formatearFecha(rstAux1.getDate("fe_doc"),"yyyy-MM-dd"); ///"dd/MM/yyyy"
                        valdoc = rstAux1.getDouble("nd_mondoc");
                    }

//                    System.out.println("---strcoddoc = " + strcoddoc + "  ---strnumdoc = " + strnumdoc + " ---strFecDoc = " + strFecDoc + " ---valdoc = " + valdoc);
                    CODDOCINS = strcoddoc;
                    FECDOCAUT = strFecDoc;
                    VALDOC = valdoc;

//                    System.out.println("---CODDOCINS = " + CODDOCINS + " ---FECDOCAUT = " + FECDOCAUT + " ---VALDOC = " + VALDOC);

                    if(strcoddoc.equals("") && strnumdoc.equals("") && strFecDoc.equals(""))
                    {
                        cantreg=0;
//////                        System.out.println("---IF cantreg: " + cantreg);
                    }
                    else
                    {
                        cantreg++;
//////                        System.out.println("---ELSE cantreg: " + cantreg);
                    }

                    System.out.println(" ");

                    //Para los demï¿½s modos se muestra: sï¿½lo los documentos pagados.//
                    strSQL="";
                    strSQL+="SELECT max(a1.co_dia) as CodDia ";
                    strSQL+=" FROM tbm_cabDia AS a1 ";
                    strSQL+=" WHERE a1.st_reg IN ('A','C','R','F') ";
                    strSQL+=" AND a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc; ////se quito filtro por empresa////
                    strSQL+=" AND a1.co_tipdoc = " + TIPDOCPAG;
//                    System.out.println("---objAjuAutSis()--funcion mostrarDatIns() tbm_cabDia en--: " + strSQL);

                    rstAux1=stmAux1.executeQuery(strSQL);

                    if(rstAux1.next())
                    {
                        strcoddoc = rstAux1.getString("CodDia");
                    }

                    CODDOCINSDIA = strcoddoc;
//                    System.out.println("---CODDOCINSDIA = " + CODDOCINSDIA);


                    stm.close();
                    stm=null;

                    stmAux1.close();
                    stmAux1=null;

                    rst.close();
                    rst=null;

                    rstAux1.close();
                    rstAux1=null;

                    conAux.close();
                    conAux=null;

                }


        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch (Exception e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;
        ///return CANTIDAD;
    }
   /////////////////final de funcion mostrarReg///////////////////
    
    
    //////////////////funcion prueba de mostrarReg///////////////////
    ///private boolean mostrarReg()
    public double rtnValReg(int codloc, int tipdoc, int coddoc)
    {
        int intCodEmp, intCodLoc, intCodMnu, intCodTipDoc, x=4, val=0, cant=0;
        double dblValPnd=0.00, valDes=0, valHas=0, valreg=0.00;
        boolean blnRes=true;
        String strTit, strMsg;
        //double dblValPnd=0.00;
        try
        {

            System.out.println(" ");
            System.out.println(" ---FUNCION rtnValReg()---");

                conAux=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conAux!=null)
                {
                    stmAux1=conAux.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stm=conAux.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    
                    intCodEmp=objZafParSis.getCodigoEmpresa();
                    intCodLoc=objZafParSis.getCodigoLocal();
                    intCodMnu=objZafParSis.getCodigoMenu();
                    ///intCodTipDoc=objParSis.getCodigoMenu();

                    //Para los demï¿½s modos se muestra: sï¿½lo los documentos pagados.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp as CodEmp, a1.co_loc as CodLoc, a1.co_cli as CodCli, a4.st_cli, a4.st_prv, a4.tx_nom, a1.co_tipDoc as CodTipDoc, a3.tx_desCor as DesCorDoc,";
                    strSQL+=" a3.tx_desLar as DesLarDoc, a1.co_doc as CodDoc, a2.co_reg as CodReg, a1.ne_numDoc as NumDoc, a1.fe_doc as FecDoc, a3.tx_natdoc, ";
                    strSQL+=" round(a2.nd_porret,2) as PorRet, a2.mo_pag as MonDoc, a2.nd_abo AS Abono, round((a2.mo_pag+a2.nd_abo),7) AS ValPnd, ";
                    strSQL+=" (-1*round((a2.mo_pag+a2.nd_abo),7)) AS ValPndDetPag ";
                    strSQL+=" FROM tbm_cabMovInv AS a1 ";
                    strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                    strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                    //strSQL+=" INNER JOIN tbr_tipdocprg AS a5 ON (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc) ";
                    strSQL+=" WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 ";
                    strSQL+=" AND (a2.mo_pag+a2.nd_abo) between -0.03 and 0.03 ";
                    strSQL+=" AND a1.co_emp= " + intCodEmp;
                    strSQL+=" AND a1.co_loc= " + codloc; 
                    strSQL+=" AND a1.co_tipdoc= " + tipdoc;
                    strSQL+=" AND a1.co_doc= " + coddoc;
                    ///strSQL+=strAux;
                    strSQL+=" ORDER BY a1.co_emp, a1.co_cli, a1.co_tipdoc, a1.ne_numdoc, a2.co_reg ";
//                    System.out.println("---rtnValReg en--: " + strSQL);

                    rstAux1=stmAux1.executeQuery(strSQL);
                    if(rstAux1.next())
                    {
                        valreg = rstAux1.getDouble("ValPndDetPag");                        
                        strCodLoc = rstAux1.getString("CodLoc");
                        strCodTipDoc = rstAux1.getString("CodTipDoc");
                        strCodDoc = rstAux1.getString("CodDoc");
                        strCodReg = rstAux1.getString("CodReg");
                    }
                    VALREG = valreg;
                    CODLOC = strCodLoc;
                    CODTIPDOC = strCodTipDoc;
                    CODDOC = strCodDoc;
                    CODREG = strCodReg;
                    
//                    System.out.println("---rtnValReg VALREG---: " + VALREG);
//                    System.out.println("---rtnValReg CODLOC---: " + CODLOC);
//                    System.out.println("---rtnValReg CODTIPDOC---: " + CODTIPDOC);
//                    System.out.println("---rtnValReg CODDOC---: " + CODDOC);
//                    System.out.println("---rtnValReg CODREG---: " + CODREG);
                    
                    stm.close();
                    stm=null;
                    
                    stmAux1.close();
                    stmAux1=null;

                    rstAux1.close();
                    rstAux1=null;
                    
                    conAux.close();
                    conAux=null;                    
                }
                

        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch (Exception e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return VALREG;
    }
    /////////////////final de funcion mostrarReg///////////////////
    
    
    public boolean insertarRegAut(int valor, String Fecha)//funcion original//
    {
        boolean blnRes = true;
        int cantidad=0;
        String strFecSer="";
        try
        {

            System.out.println(" ");
            System.out.println(" ---FUNCION insertarRegAut()---");

            //Obtener la fecha del servidor.
            datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
            if (datFecAux==null)
                return false;

            strFecSer = objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
            strFecAct = objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
            STRFECPAG = Fecha;
            
            ///strFecSer=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
//            System.out.println("---Funcion InsertarRegAut--strFecAct: " + strFecAct);
//            System.out.println("---Funcion InsertarRegAut--strFecSer: " + strFecSer);
//            System.out.println("---Funcion InsertarRegAut--FECDOCAUT: " + FECDOCAUT);
//            System.out.println("---Funcion InsertarRegAut--STRFECDOC: " + STRFECDOC);
//            System.out.println("---Funcion InsertarRegAut--STRFECPAG: " + STRFECPAG);
//
//            System.out.println("---Funcion InsertarRegAut--cantreg: " + cantreg);
//            System.out.println("---Funcion InsertarRegAut--VALCANT: " + VALCANT);
//
//            System.out.println("---Funcion InsertarRegAut--CODDOCPAG: " + CODDOCPAG);
//            System.out.println("---Funcion InsertarRegAut--INTDOCCOD: " + INTDOCCOD);
//            System.out.println("---Funcion InsertarRegAut--CODDOCINS: " + CODDOCINS);
//            System.out.println("---Funcion InsertarRegAut--CODDOCDIA: " + CODDOCINSDIA);
            
            cantidad = valor;
//            System.out.println("---Funcion InsertarRegAut--cantidad valor que recibe la funcion: " + cantidad);
            
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            
            if (con!=null)
            {
                if(cantreg==0)
                {
                    if(cantidad == 1)
                    {
                        if(VALCANT == 0)
                        {
                            System.out.println("---BLOQUE 0--- FUNCION InsertarRegAut");

                            if(insertarCab())
                            {
                                if(insertarDet())
                                {
                                    if(actualizarCabAju(0))
                                    {
                                        if(actualizarPagMovInvAju(0))
                                        {
                                            if (objDiario.insertarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, (""+CODDOCPAG), objUti.parseDate(strFecSer,"dd/MM/yyyy"), intCodigoMenu))
                                            ///if (objDiario.insertarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, CODDOCPAG))
                                            {
//                                                System.out.println("---Pilas ya paso por insertarCab() - insertarDet() - actualizarCab() desde ZafAjuAut 1  ---");
                                                con.commit();
                                                blnRes = true;
                                            }
                                            else
                                                con.rollback();
                                        }
                                        else
                                            con.rollback();
                                    }
                                    else
                                        con.rollback();
                                }
                                else
                                    con.rollback();
                            }
                            else
                                con.rollback();
                        }
                        else
                        {
                            System.out.println("---BLOQUE 1--- FUNCION InsertarRegAut");

                            if(insertarDet())
                            {
                                if(actualizarCabAju(0))
                                {
                                    if(actualizarPagMovInvAju(0))
                                    {
                                        ///generaAsiento();
                                        ///if (objDiario.insertarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, (""+CODDOCPAG), objUti.parseDate(strFecSer,"dd/MM/yyyy")))
                                        ///////if (objDiario.actualizarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, INTDOCCOD, (""+INTDOCCOD), objUti.parseDate(strFecSer,"dd/MM/yyyy"), "A", intCodigoMenu))
                                        ///if (objDiario.insertarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, CODDOCPAG))
                                        if (objDiario.actualizarDiarioAju(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, Integer.parseInt(CODDOCINSDIA), (""+INTDOCCOD), objUti.parseDate(strFecSer,"dd/MM/yyyy"), "A", intCodigoMenu))
                                        {
//                                            System.out.println("---Pilas ya paso por insertarDet() desde ZafAjuAut 2---");
                                            con.commit();
                                            blnRes = true;
                                        }
                                        else
                                            con.rollback();
                                    }
                                    else
                                        con.rollback();
                                }
                                else
                                    con.rollback();
                            }
                            else
                                con.rollback();
                        }
                    }
                    else
                    {
                        System.out.println("---BLOQUE 2--- FUNCION InsertarRegAut");
                        
                        if(insertarDet())
                        {
                            if(actualizarCabAju(0))
                            {
                                if(actualizarPagMovInvAju(0))
                                {
                                    ///generaAsiento();
                                    ///if (objDiario.actualizarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, CODDOCPAG, (""+CODDOCPAG), objUti.parseDate(strFecSer,"dd/MM/yyyy"), "A")) ///original///
                                    ////////if (objDiario.actualizarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, INTDOCCOD, (""+INTDOCCOD), objUti.parseDate(strFecSer,"dd/MM/yyyy"), "A", intCodigoMenu))
                                    if (objDiario.actualizarDiarioAju(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, Integer.parseInt(CODDOCINSDIA), (""+INTDOCCOD), objUti.parseDate(strFecSer,"dd/MM/yyyy"), "A", intCodigoMenu))
                                    {
//                                        System.out.println("---Pilas ya paso por insertarDet() desde ZafAjuAut 2---");
                                        con.commit();
                                        blnRes = true;
                                    }
                                    else
                                        con.rollback();
                                }
                                else
                                    con.rollback();
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                    }
                }
                else
                {
                    System.out.println("---BLOQUE 3--- FUNCION InsertarRegAut");
//                    System.out.println("---BLOQUE 3--- strFecAct " + strFecAct);
//                    System.out.println("---BLOQUE 3--- FECDOCAUT " + FECDOCAUT);
                    
                    ///if(strFecAct.equals(FECDOCAUT))
                    if(FECDOCAUT.equals(STRFECPAG))
                    {
//                        System.out.println("---FECHAS IGUALES strFecSer=FECDOCAUT YA EXISTE UN DOC DE AJUSTE AUTOMATICO.---");
                        if(insertarDet())
                        {
                            if(actualizarCabAju(0))
                            {
                                if(actualizarPagMovInvAju(0))
                                {
//                                    System.out.println(" ");
//                                    System.out.println("---actualizarDiario---3 PARAMETROS---con: " + con);
//                                    System.out.println("---actualizarDiario---3 PARAMETROS---objZafParSis.getCodigoEmpresa(): " + objZafParSis.getCodigoEmpresa());
//                                    System.out.println("---actualizarDiario---3 PARAMETROS---objZafParSis.getCodigoLocal(): " + objZafParSis.getCodigoLocal());
//                                    System.out.println("---actualizarDiario---3 PARAMETROS---CODDOCINS: " + CODDOCINS);
//                                    System.out.println("---actualizarDiario---3 PARAMETROS---INTDOCCOD: " + INTDOCCOD);
//                                    System.out.println("---actualizarDiario---3 PARAMETROS---CODDOCINSDIA: " + CODDOCINSDIA);

                                    ////generaAsiento();
                                    ///if (objDiario.insertarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, (""+CODDOCPAG), objUti.parseDate(strFecSer,"dd/MM/yyyy")))
                                    /////////if (objDiario.actualizarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, Integer.parseInt(CODDOCINS), (""+CODDOCINS), objUti.parseDate(strFecSer,"dd/MM/yyyy"), "A", intCodigoMenu))
                                    ///if (objDiario.insertarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, CODDOCPAG))
                                    if (objDiario.actualizarDiarioAju(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, Integer.parseInt(CODDOCINSDIA), (""+INTDOCCOD), objUti.parseDate(strFecSer,"dd/MM/yyyy"), "A", intCodigoMenu))
                                    {
//                                        System.out.println("---Pilas ya paso por insertarDet() desde ZafAjuAut---");
                                        con.commit();
                                        blnRes = true;
                                    }
                                    else
                                        con.rollback();
                                }
                                else
                                    con.rollback();
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                    }
                    else
                    {
//                        System.out.println("---FECHAS DIFERENTES strFecSer <> FECDOCAUT NO EXISTE UN DOC DE AJUSTE AUTOMATICO.---");
                        if(insertarCab())
                        {
                            if(insertarDet())
                            {
                                if(actualizarCabAju(0))
                                {
                                    if(actualizarPagMovInvAju(0))
                                    {
                                        if (objDiario.insertarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, (""+CODDOCPAG), objUti.parseDate(strFecSer,"dd/MM/yyyy"), intCodigoMenu))
                                        ///if (objDiario.insertarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, CODDOCPAG))
                                        {
//                                            System.out.println("---Pilas ya paso por insertarCab() - insertarDet() - actualizarCab() desde ZafAjuAut 1  ---");
                                            con.commit();
                                            blnRes = true;
                                        }
                                        else
                                            con.rollback();
                                    }
                                    else
                                        con.rollback();
                                }
                                else
                                    con.rollback();
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                    }
                }                
            }
            else
            {
//                System.out.println("---NO ENTRO LA CONEXION ESTA CERRADA---");
            }
            con.close();
            con=null;            
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch (Exception e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        
        return blnRes;
    }
    
    
    ///////////////////FUNCION PARA INSERTAR DATOS EN LA TABLA tbm_cabpag/////////////////
    private boolean insertarCab()
    {
        int intCodUsr, intUltReg=0;
        boolean blnRes=true;
        String strTblAbo="";
        try
        {

            System.out.println(" ");
            System.out.println(" ---FUNCION insertarCab()---");

            ///con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());

            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                intCodUsr=objZafParSis.getCodigoUsuario();
                double dblTblAbo=0.00;
                
                
                
                ///////Query para Obtener el cï¿½digo del ï¿½ltimo registro ingresado.///////////
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc) as MAXCODDOC";
                strSQL+=" FROM tbm_cabPag AS a1";
                strSQL+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objZafParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc = " + INTCODTIPDOCAJU;
                ///strSQL+=" AND a1.st_reg NOT IN ('E','I')";
                
//                System.out.println("---query para el ultimo coddoc en intUltReg-- insertarCab(): " + strSQL);
                
                rst=stm.executeQuery(strSQL);
                
                if (rst.next())
                    intUltReg = (rst.getInt("MAXCODDOC"));
                
                ultCodDoc = intUltReg;
                
//                System.out.println("---el ultimo coddoc en intUltReg-- insertarCab(): " + intUltReg);
                
                intUltReg++;
                CODDOCPAG = intUltReg;
                
//                System.out.println("---EN insertarCab()--EL NUEVO CODDOCPAG ES: " + CODDOCPAG);
                
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                strFecAct = objUti.formatearFecha(datFecAux, "yyyy/MM/dd");
//                System.out.println("---strFecAct: " + strFecAct);
                
                ////////Query para Armar la sentencia SQL de Insertar./////////////
                strSQL="";                                                
                strSQL+=" INSERT INTO tbm_cabPag (co_emp, co_loc, co_tipDoc, co_doc, fe_doc, ne_numDoc1, "; //co_cli, fe_doc, ne_numDoc1, ";
                strSQL+=" nd_monDoc, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod)";
                strSQL+=" VALUES (" ;
                strSQL+= objZafParSis.getCodigoEmpresa();/////////////variable para ver el tipo de empresaque esta activo///////////
                strSQL+=", " + objZafParSis.getCodigoLocal();/////////////variable para ver el tipo de local que esta activo//////
                strSQL+=", " + STRCODTIPDOC; ////variable para ver el co_tipdoc/////////
                strSQL+=", " + intUltReg; /////variable para visualizar el ï¿½ltimo codigo de documento
                ///strSQL+=", " + txtCodCli.getText(); /////variable para visualizar el codigo de cliente
                ///strSQL+=", '" + objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";   ///////para insertar la fecha del documento//////
                ///strSQL+=", '" + objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos()) + "'";    ///////para insertar la fecha del documento//////
                strSQL+=", '" + strFecAct + "'";   ///////para insertar la fecha del documento//////
                
                ///strSQL+=", " + (INTNUMDOC+1);    /////para el codigo alterno1////////
                strSQL+=", " + (intUltReg);         /////para el codigo alterno1////////
                ///strSQL+=", " + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (intSig*Double.parseDouble(txtMonDoc.getText())):"0"),3);/////////monto del documento////////
                ///strSQL+=", " + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (Double.parseDouble(txtMonDoc.getText())):"0"),3);/////////monto del documento////////sin la variable intSig///
                strSQL+=", 0.00" ;/////////monto del documento////////sin la variable intSig///
                strSQL+=", null" ;  ////////para observacion 1//////////////
                strSQL+=", null" ;  ////////para observacion 2//////////////
                strSQL+=", 'A'";     /////////para estado de registro///////////
                strAux=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos()); /////////formateando la fecha de ingreso///////
                strSQL+=", '" + strAux + "'";  //////////para fecha de ingreso////////////////
                strSQL+=", '" + strAux + "'";  //////////para fecha de ultima modificacion////////////
                strSQL+=", " + intCodUsr;      //////////para codigo de usuario que ingreso al programa////////////
                strSQL+=", " + intCodUsr;      //////////para codigo de usuario que ingreso al programa y realizao algun cambio////////////
                strSQL+=")";   ///////fin del query INSERT/////////
                System.out.println("---FUNCION INSERTAR_CAB--- insertarCab(): " + strSQL);
                stm.executeUpdate(strSQL);
                
                stm.close();
                stm=null;

                rst.close();
                rst=null;
//                con.close();
//                con=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch (Exception e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;
    }
    
    
    ////////////funcion original para insertar//////////
    private boolean insertarDet()
    {
        int intCodEmp, intCodLoc, i, j, intUltReg=0;
        String strCodTipDoc, strCodDoc, strNatDoc;
        boolean blnRes=true;
        double dblAbo1, dblAbo2, dblValDoc, dblAjus, dblValPen;
        int intcodDoc=0;
        try
        {

            System.out.println(" ");
            System.out.println(" ---FUNCION insertarDet()---");

            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                intCodEmp=objZafParSis.getCodigoEmpresa();
                intCodLoc=objZafParSis.getCodigoLocal();
                
//                System.out.println("---En Insertar_Detalle cantreg >>>>: " + cantreg);
//                System.out.println("---En Insertar_Detalle ultCodDoc >>>>: " + ultCodDoc);
                
                
                if(cantreg==1 && ultCodDoc==0)
                    ultCodDoc++;
                else
                {
                    if(VALCANT != 0)
                        ultCodDoc++;
                    if(ultCodDoc == 0)
                        ultCodDoc++;
                }
                
//                System.out.println("---En Insertar_Detalle ultCodDoc >>>>: " + ultCodDoc);
//                System.out.println("---En Insertar_Detalle CODDOCPAG >>>>: " + CODDOCPAG);
//                System.out.println("---En Insertar_Detalle CODDOCINS >>>>: " + CODDOCINS);
//                System.out.println("---En Insertar_Detalle INTDOCCOD >>>>: " + INTDOCCOD);
                
                
                ///////Query para Obtener el cï¿½digo del ï¿½ltimo registro ingresado.///////////
                strSQL="";
                strSQL+="SELECT MAX(a1.co_reg) as MAXCODREG";
                strSQL+=" FROM tbm_detPag AS a1";
                strSQL+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objZafParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc = " + INTCODTIPDOCAJU;
                ///strSQL+=" AND a1.co_Doc = " + CODDOCPAG;
                
                
                if(INTDOCCOD==0)
                {
                    if(CODDOCPAG > 0)
                        strSQL+=" AND a1.co_Doc = " + CODDOCPAG;
                    if(CODDOCPAG == 0)
                        strSQL+=" AND a1.co_Doc = " + CODDOCINS;
                }
                else
                {
                    if(CODDOCPAG > 0)
                        strSQL+=" AND a1.co_Doc = " + CODDOCPAG;
                    if(CODDOCPAG == 0)
                        strSQL+=" AND a1.co_Doc = " + CODDOCINS;
                }
                
//                System.out.println("---En Insertar_Detalle MAXCODREG >>>>: " + strSQL);
                rst=stm.executeQuery(strSQL);
                
                if (rst.next())
                    intUltReg = (rst.getInt("MAXCODREG"));
                
                intUltReg++;
                
                strSQL="";
                strSQL+=" INSERT INTO tbm_detPag (co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_locPag, co_tipDocPag, co_docPag, co_regPag, nd_abo)"; 
                strSQL+=" VALUES (";
                strSQL+=" " + intCodEmp;    ///codigo local de pago en tbm_pagMovInv
                strSQL+=", " + intCodLoc;    ////codigo de local de tbm_cabpag
                strSQL+=", " + STRCODTIPDOC; ////codigo de tipo doc de tbm_cabpag
                ////strSQL+=", " + CODDOCPAG;    ////codigo de documento de tbm_cabpag
                
                if(INTDOCCOD==0)
                {
                    if(CODDOCPAG > 0)
                        strSQL+=", " + CODDOCPAG;
                    if(CODDOCPAG == 0)
                        strSQL+=", " + CODDOCINS;
                }
                else
                {
                    if(CODDOCPAG > 0)
                        strSQL+=", " + CODDOCPAG;
                    if(CODDOCPAG == 0)
                        strSQL+=", " + CODDOCINS;
                }
                

                strSQL+=", " + intUltReg;    ////codigo de registro de tbm_cabpag
                strSQL+=", " + CODLOC;    ///codigo local de pago en tbm_pagMovInv
                strSQL+=", " + CODTIPDOC; ///codigo tipo de docpag en tbm_pagMovInv
                strSQL+=", " + CODDOC;    ///codigo de docpag en tbm_pagMovInv
                strSQL+=", " + CODREG;    ///codigo de regpag en tbm_pagMovInv
                ///strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_AJUSTE), 3);   ///valor del abono en tbm_detpag
                ///strSQL+=", " + intSigIn * Double.parseDouble(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_AJUSTE), 3));  ///valor del abono en tbm_detpag
                strSQL+=", " + (VALREG);  ///valor del abono en tbm_detpag
                strSQL+=")";

                ///ValReg = (-1*VALREG);
                ValReg = (VALREG);
                sumValReg = sumValReg + ValReg;

                System.out.println("---Insertar_Detalle --- insertarDet() >>>>: " + strSQL);
                stm.executeUpdate(strSQL);

//                System.out.println("---Funcion Insertar_Detalle el valor sumValReg>>>>: " + sumValReg);
                
                stm.close();
                stm=null;

                rst.close();
                rst=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch (Exception e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;
    }
    
    private boolean actualizarCabAju(int intTipOpe)
    {
        boolean blnRes=true;
        double sumTotValReg=0.00, dblValTotAju=0.0;
        int intValOpe=0, intUltRegPag=0;
        
        try
        {

            System.out.println(" ");
            System.out.println(" ---FUNCION actualizarCabAju()---");

            ///mostrarDatIns();
            
//            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());

            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                
                int intCodEmp=objZafParSis.getCodigoEmpresa();
                int intCodLoc=objZafParSis.getCodigoLocal();
                
//                System.out.println("---actualizarCabAju()---LA VARIABLE intTipOpe ES--- " + intTipOpe);
                intValOpe = intTipOpe;
                
                
                ///////Query para Obtener el cï¿½digo del ï¿½ltimo registro ingresado.///////////
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc) as MAXCODDOC";
                strSQL+=" FROM tbm_cabPag AS a1";
                strSQL+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objZafParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc = " + INTCODTIPDOCAJU;
                ///strSQL+=" AND a1.st_reg NOT IN ('E','I')";
                
                rst=stm.executeQuery(strSQL);
                
                if (rst.next())
                    intUltRegPag = (rst.getInt("MAXCODDOC"));
                                
                CODDOCPAG = intUltRegPag;
                
//                System.out.println("---EN actualizarCabAju()--CODDOCPAG: " + CODDOCPAG);
                
                
                if(intValOpe == 0)
                {
//                    System.out.println("---ENTRO POR EL CASO CERO 000 EN actualizarCabAju()--- ");
//                    System.out.println("---caso 0.-FUNCION ACTUALIZAR CABPAG-- variable--ValReg--: " + ValReg);
//                    System.out.println("---caso 0.-FUNCION ACTUALIZAR CABPAG-- variable--sumValReg--: " + sumValReg);
//                    System.out.println("---caso 0.-FUNCION ACTUALIZAR CABPAG-- variable--VALDOC--: " + VALDOC);
                    
                    strSQL="";                                                
                    strSQL+=" SELECT sum(nd_abo) as CantDoc ";
                    strSQL+=" FROM tbm_detPag "; //co_cli, fe_doc, ne_numDoc1, ";
                    strSQL+=" WHERE ";
                    strSQL+=" co_emp = " + objZafParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc = " + objZafParSis.getCodigoLocal();
                    strSQL+=" AND co_tipdoc = " + STRCODTIPDOC;
                    strSQL+=" AND co_doc = " + CODDOCPAG;
                                    
                    rst=stm.executeQuery(strSQL);
                    if (rst.next())
                    {
                        dblValTotAju=rst.getDouble("CantDoc");
                    }
//                    System.out.println("---AjusteAutomatico.- actualizarCabAju()--dblValTotAju: " + dblValTotAju);
                    
                    ///sumTotValReg = objUti.redondear((sumValReg + VALDOC),2);
                    if(FECDOCAUT.equals(STRFECPAG))   
                    {
//                        System.out.println("---FECHAS SON IGUALES--- FECDOCAUT: " + FECDOCAUT + " ---STRFECPAG: " + STRFECPAG);
                        sumTotValReg = objUti.redondear((dblValTotAju),2);
                    }
                    else
                    {
//                        System.out.println("---FECHAS SON DIFERENTES--- FECDOCAUT: " + FECDOCAUT + " ---STRFECPAG: " + STRFECPAG);
                        VALDOC=0.00;
                        sumTotValReg = objUti.redondear((dblValTotAju),2);
                    }
                    ///sumTotValReg = objUti.redondear((sumTotValReg + ValReg),2);
                    
                    ///sumTotValReg = sumValReg;
                    
                    VALTOTAJUAUT = (sumTotValReg);
                    ///dblValTotDoc=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    ///System.out.println("---2A.- actualizarPagMovInv()--intCanRegDetRecChq: " + intCanRegDetRec);
                                    
//                    System.out.println("---3A.-FUNCION ACTUALIZAR CABPAG-- variable--SUMVALABO--: " + SUMVALABO);
//                    System.out.println("---3A.-FUNCION ACTUALIZAR CABPAG-- variable--sumTotValReg--: " + sumTotValReg);
//                    System.out.println("---3A.-FUNCION ACTUALIZAR CABPAG-- variable--VALTOTAJUAUT--: " + VALTOTAJUAUT);
//
//                    System.out.println("---4A.-FUNCION ACTUALIZAR CABPAG-- variable--ultCodDoc--: " + ultCodDoc);

                    ////////Query para Armar la sentencia SQL de Insertar./////////////
                    strSQL="";                                                
                    strSQL+=" UPDATE tbm_cabPag SET "; //co_cli, fe_doc, ne_numDoc1, ";
                    ///strSQL+=" nd_mondoc = " + Math.abs(sumValReg); ///antes///
                    strSQL+=" nd_mondoc = " + VALTOTAJUAUT;                    
                    strSQL+=" WHERE ";
                    strSQL+=" co_emp = " + objZafParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc = " + objZafParSis.getCodigoLocal();
                    strSQL+=" AND co_tipdoc = " + STRCODTIPDOC;
                    strSQL+=" AND co_doc = " + CODDOCPAG;                   

                    System.out.println("---intValOpe=0---FUNCION ACTUALIZAR CABPAG: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                
                stm.close();
                stm=null;

                rst.close();
                rst=null;
                
                
//                System.out.println("------ANTES DE GENERAR EL DIARIO EL VALOR --VALTOTAJUAUT--ES: " + VALTOTAJUAUT);
                ///System.out.println("------ANTES DE GENERAR EL DIARIO EL VALOR ES: " + strSQL);
                objDiario.generarDiario(INTCODTIPDOCAJU, Math.abs(VALTOTAJUAUT), Math.abs(VALTOTAJUAUT));
                
            }
//            con.close();
//            con=null;
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;
    }
    
    
    ///////////////////FUNCION PARA INSERTAR DATOS EN LA TABLA tbm_cabpag/////////////////
    private boolean actualizarPagMovInvAju(int intTipOpe)
    {
        int intCodUsr, intUltReg=0, intSig=1, intValOpe=0;
        boolean blnRes=true;
        String strTblAbo="";
        double sumTotValReg=0.00;
        
        try
        {

            System.out.println(" ");
            System.out.println(" ---FUNCION actualizarPagMovInvAju()---");

            ///con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());

            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                intCodUsr=objZafParSis.getCodigoUsuario();
                
                intValOpe = intTipOpe;
                
                if(intValOpe==0)
                {
//                    System.out.println("---ENTRO POR EL CASO CASO UNO 000 EN actualizarPagMovInvAju--- ");
//                    
//                    System.out.println("---1X.- FUNCION ACTUALIZAR PAGMOVINV---VALREG--: " + VALREG);
//                    
//                    System.out.println("---2X.- FUNCION ACTUALIZAR PAGMOVINV---VALDOC--: " + VALDOC);
//                    
//                    System.out.println("---3X.- FUNCION ACTUALIZAR PAGMOVINV---intTipOpe--: " + intTipOpe);
                    if(VALREG > 0)
                        intSig=1;
                    else
                        ///intSig=-1;
                        intSig=1;
                    
                    ////////Query para Armar la sentencia SQL de Insertar./////////////
                    strSQL="";
                    strSQL+=" UPDATE tbm_PagMovInv SET "; //co_cli, fe_doc, ne_numDoc1, ";
                    strSQL+=" nd_abo = nd_abo + " + (intSig * VALREG); ///VALREG  ///VALDOC
                    strSQL+=" WHERE ";
                    strSQL+=" co_emp = " + objZafParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc = " + CODLOC;
                    strSQL+=" AND co_tipdoc = " + CODTIPDOC;
                    strSQL+=" AND co_doc = " + CODDOC;
                    strSQL+=" AND co_reg = " + CODREG;

                    System.out.println("---4X.- FUNCION ACTUALIZAR PAGMOVINV---: " + strSQL);

                    stm.executeUpdate(strSQL);
                    
                }
                
                if(intValOpe==1 || intValOpe==2)
                {
//                    System.out.println("---ENTRO POR EL CASO CASO UNO 111 EN actualizarPagMovInvAju--- ");
//                    
//                    System.out.println("---1Y.- FUNCION ACTUALIZAR PAGMOVINV---VALREG--: " + VALREG);
//                    
//                    System.out.println("---2Y.- FUNCION ACTUALIZAR PAGMOVINV---VALREGELI--: " + VALREGELI);
//                
//                    System.out.println("---3Y.- FUNCION ACTUALIZAR PAGMOVINV---intTipOpe--: " + intTipOpe);
                    
                    if(VALREGELI > 0)
                        intSig=-1;
                    else
                        ///intSig=1;
                        intSig=-1;
                    
                    ////////Query para Armar la sentencia SQL de Insertar./////////////
                    strSQL="";
                    strSQL+=" UPDATE tbm_PagMovInv SET "; //co_cli, fe_doc, ne_numDoc1, ";
                    strSQL+=" nd_abo = nd_abo + " + (intSig * VALREGELI); ///VALREG  ///VALDOC
                    strSQL+=" WHERE ";
                    strSQL+=" co_emp = " + objZafParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc = " + CODLOC;
                    strSQL+=" AND co_tipdoc = " + CODTIPDOC;
                    strSQL+=" AND co_doc = " + CODDOC;
                    strSQL+=" AND co_reg = " + CODREG;

                    System.out.println("---4Y.- FUNCION ACTUALIZAR PAGMOVINV---: " + strSQL);

                    stm.executeUpdate(strSQL);
                    
                }
                
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch (Exception e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;
    }
    
    //Funcion de Prueba_1 para ser llamada desde otro programa///
    public boolean eliminarRegAut(int valor)
    {
        boolean blnRes = true;
        int cantidad=0;
        String strFecSer="";
        try
        {
            System.out.println(" ");
            System.out.println(" ---FUNCION eliminarRegAut()---");

            //Obtener la fecha del servidor.
            datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
            if (datFecAux==null)
                return false;

            strFecSer = objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
            strFecAct = objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
            ///strFecSer=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
//            System.out.println("---Funcion EliminarRegAut--strFecAct: " + strFecAct);
//            System.out.println("---Funcion EliminarRegAut--strFecSer: " + strFecSer);
//            System.out.println("---Funcion EliminarRegAut--FECDOCAUT: " + FECDOCAUT);
//            System.out.println("---Funcion EliminarRegAut--cantreg: " + cantreg);
            
            cantidad = valor;
//            System.out.println("---Funcion EliminarRegAut--cantidad valor que recibe la funcion: " + cantidad);
            
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            
            if (con!=null)
            {
//                System.out.println("---BLOQUE 3--- FUNCION EliminarRegAut");
                
                if(cantidad == 100)
                {
//                    System.out.println("---ENTRO POR EL IF DONDE CANTIDAD = 100");
                    CANT = cantidad;
                    if(eliminarDet())
                    {
                        if(actualizarCabAju(3))
                        {
                            if(actualizarPagMovInvAju(1))
                            {
                                if(strFecAct.equals(FECDOCAUT))
                                {
//                                    System.out.println("---FECHAS IGUALES strFecSer=FECDOCAUT YA EXISTE UN DOC.---");
                                    
                                    if (objDiario.actualizarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, Integer.parseInt(CODDOCINS), (""+CODDOCINS), objUti.parseDate(strFecSer,"dd/MM/yyyy"), "A"))
                                    ///if (objDiario.eliminarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, Integer.parseInt(CODDOCINS)))
                                    {
//                                        System.out.println("---Pilas ya paso por eliminarDet() desde ZafAjuAut---");
                                        con.commit();
                                        blnRes = true;
                                    }
                                    else
                                        con.rollback();
                                }
                                else
                                {
//                                    System.out.println("---FECHAS NO SON IGUALES strFecSer!=FECDOCAUT YA EXISTE UN DOC.---");
                                    if (objDiario.actualizarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, Integer.parseInt(CODDOCINS), (""+CODDOCINS), objUti.parseDate(strFecSer,"dd/MM/yyyy"), "A"))
                                    //if (objDiario.eliminarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, CODDOCPAG))
                                    {
//                                        System.out.println("---Pilas ya paso por eliminarDet() desde ZafAjuAut---");
                                        con.commit();
                                        blnRes = true;
                                    }
                                    else
                                        con.rollback();
                                }
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                {
                    if(eliminarDet())
                    {
                        if(actualizarCabAju(1))
                        {
                            if(actualizarPagMovInvAju(1))
                            {
                                if(strFecAct.equals(FECDOCAUT))
                                {
//                                    System.out.println("---FECHAS IGUALES strFecSer=FECDOCAUT YA EXISTE UN DOC.---");
                                    if(cantidad==100)
                                    {
                                        //if (objDiario.actualizarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, Integer.parseInt(CODDOCINS), (""+CODDOCINS), objUti.parseDate(strFecSer,"dd/MM/yyyy"), "A"))
                                        if (objDiario.eliminarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, Integer.parseInt(CODDOCINS)))
                                        {
//                                            System.out.println("---Pilas ya paso por eliminarDet() desde ZafAjuAut---");
                                            con.commit();
                                            blnRes = true;
                                        }
                                        else
                                            con.rollback();
                                    }
                                    else
                                    {
                                        //if (objDiario.actualizarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, Integer.parseInt(CODDOCINS), (""+CODDOCINS), objUti.parseDate(strFecSer,"dd/MM/yyyy"), "A"))
                                        if (objDiario.eliminarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, CODDOCPAG))
                                        {
//                                            System.out.println("---Pilas ya paso por eliminarDet() desde ZafAjuAut---");
                                            con.commit();
                                            blnRes = true;
                                        }
                                        else
                                            con.rollback();
                                    }
                                }
                                else
                                {
//                                    System.out.println("---FECHAS NO SON IGUALES strFecSer!=FECDOCAUT YA EXISTE UN DOC.---");
                                    //if (objDiario.actualizarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, Integer.parseInt(CODDOCINS), (""+CODDOCINS), objUti.parseDate(strFecSer,"dd/MM/yyyy"), "A"))
                                    if (objDiario.eliminarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, CODDOCPAG))
                                    {
//                                        System.out.println("---Pilas ya paso por eliminarDet() desde ZafAjuAut---");
                                        con.commit();
                                        blnRes = true;
                                    }
                                    else
                                        con.rollback();
                                }
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
            }
            else
            {
//                System.out.println("---NO ENTRO LA CONEXION ESTA CERRADA en eliminarRegAut()---");
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch (Exception e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        
        return blnRes;
    }
    
    ////////////funcion original para insertar//////////
    private boolean eliminarDet()
    {
        int intCodEmp, intCodLoc, i, j, intUltReg=0;
        String strCodTipDoc, strCodDoc, strNatDoc;
        //String strFecDoc=objUti.formatearFecha(txtFecDoc.getText().toString(), "dd/MM/yyy", "yyyy/MM/dd");
        boolean blnRes=true;
        double dblAbo1, dblAbo2, dblValDoc, dblAjus, dblValPen;
        int intcodDoc=0;
        try
        {
            System.out.println(" ");
            System.out.println(" ---FUNCION eliminarDet()---");

            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                intCodEmp=objZafParSis.getCodigoEmpresa();
                intCodLoc=objZafParSis.getCodigoLocal();
                
                if(cantreg==1 && ultCodDoc==0)
                    ultCodDoc++;
                
//                System.out.println("---En Eliminar_Detalle ultCodDoc >>>>: " + ultCodDoc);
                CODDOCPAG = ultCodDoc;
                
                ///////Query para Obtener el cï¿½digo del ï¿½ltimo registro ingresado.///////////
                strSQL="";
                strSQL+="SELECT MAX(a1.co_reg) as MAXCODREG";
                strSQL+=" FROM tbm_detPag AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc = " + INTCODTIPDOCAJU;
                if(ultCodDoc==0)
                    strSQL+=" AND a1.co_Doc = " + CODDOCPAG;
                else
                    strSQL+=" AND a1.co_Doc = " + CODDOCINS;
                
//                System.out.println("---En Eliminar_Detalle MAXCODREG >>>>: " + strSQL);
                rst=stm.executeQuery(strSQL);
                
                if (rst.next())
                    intUltReg = (rst.getInt("MAXCODREG"));
                
                intUltReg++;
                
                if(CANT==100)
                {
                    strSQL="";
                    strSQL+=" DELETE FROM tbm_detPag "; ////(co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_locPag, co_tipDocPag, co_docPag, co_regPag, nd_abo)"; 
                    strSQL+=" WHERE ";
                    strSQL+=" co_emp = " + intCodEmp;    ///codigo local de pago en tbm_pagMovInv
                    strSQL+=" AND co_loc = " + intCodLoc;    ////codigo de local de tbm_cabpag
                    strSQL+=" AND co_tipdoc = " + STRCODTIPDOC; ////codigo de tipo doc de tbm_cabpag
                    strSQL+=" AND co_doc = " + CODDOCINS;    ////codigo de documento de tbm_cabpag
                    strSQL+=" AND co_docpag = " + CODDOC;    ////codigo de registro de tbm_cabpag

                    System.out.println("---Eliminar_Detalle >>>>: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch (Exception e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;
    }
    
    
    //Funcion de Prueba_1 para ser llamada desde otro programa///
    public boolean anularRegAut(int valor)
    {
        boolean blnRes = true;
        int cantidad=0;
        String strFecSer="";
        try
        {
            System.out.println(" ");
            System.out.println(" ---FUNCION anularRegAut()---");

            //Obtener la fecha del servidor.
            datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
            if (datFecAux==null)
                return false;

            strFecSer = objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
            strFecAct = objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
            ///strFecSer=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
//            System.out.println("---Funcion AnularRegAut--strFecAct: " + strFecAct);
//            System.out.println("---Funcion AnularRegAut--strFecSer: " + strFecSer);
//            System.out.println("---Funcion AnularRegAut--FECDOCAUT: " + FECDOCAUT);
//            System.out.println("---Funcion AnularRegAut--cantreg: " + cantreg);
            
            cantidad = valor;
//            System.out.println("---Funcion AnularRegAut--cantidad valor que recibe la funcion: " + cantidad);
            
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            
            if (con!=null)
            {
//                System.out.println("---BLOQUE 3--- FUNCION AnularRegAut");
                
                if(anularDet())
                {
                    if(actualizarCabAju(2))
                    {
                        if(actualizarPagMovInvAju(2))
                        {
                            if(strFecAct.equals(FECDOCAUT))
                            {
//                                System.out.println("---FECHAS IGUALES strFecSer=FECDOCAUT YA EXISTE UN DOC.---");
                                ///if (objDiario.eliminarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, CODDOCPAG))
                                if (objDiario.anularDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, CODDOCPAG))
                                {
//                                    System.out.println("---Pilas ya paso por AnularDet() desde ZafAjuAut---");
                                    con.commit();
                                    blnRes = true;
                                }
                                else
                                    con.rollback();
                            }
                            else
                            {
//                                System.out.println("---FECHAS NO SON IGUALES strFecSer!=FECDOCAUT YA EXISTE UN DOC.---");
                                //if (objDiario.eliminarDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, CODDOCPAG))
                                if (objDiario.anularDiario(con, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), INTCODTIPDOCAJU, CODDOCPAG))
                                {
//                                    System.out.println("---Pilas ya paso por eliminarDet() desde ZafAjuAut---");
                                    con.commit();
                                    blnRes = true;
                                }
                                else
                                    con.rollback();
                            }
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            else
            {
//                System.out.println("---NO ENTRO LA CONEXION ESTA CERRADA en eliminarRegAut()---");
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch (Exception e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        
        return blnRes;
    }
    
    ////////////funcion original para insertar//////////
    private boolean anularDet()
    {
        int intCodEmp, intCodLoc, i, j, intUltReg=0;
        String strCodTipDoc, strCodDoc, strNatDoc;
        //String strFecDoc=objUti.formatearFecha(txtFecDoc.getText().toString(), "dd/MM/yyy", "yyyy/MM/dd");
        boolean blnRes=true;
        double dblAbo1, dblAbo2, dblValDoc, dblAjus, dblValPen;
        int intcodDoc=0;
        try
        {
            System.out.println(" ");
            System.out.println(" ---FUNCION anularDet()---");

            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                intCodEmp=objZafParSis.getCodigoEmpresa();
                intCodLoc=objZafParSis.getCodigoLocal();
                
                if(cantreg==1 && ultCodDoc==0)
                    ultCodDoc++;
                
//                System.out.println("---En Eliminar_Detalle ultCodDoc >>>>: " + ultCodDoc);
                CODDOCPAG = ultCodDoc;
                
                ///////Query para Obtener el cï¿½digo del ï¿½ltimo registro ingresado.///////////
                strSQL="";
                strSQL+="SELECT MAX(a1.co_reg) as MAXCODREG";
                strSQL+=" FROM tbm_detPag AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc = " + INTCODTIPDOCAJU;
                if(ultCodDoc==0)
                    strSQL+=" AND a1.co_Doc = " + CODDOCPAG;
                else
                    strSQL+=" AND a1.co_Doc = " + CODDOCINS;
                
//                System.out.println("---En Eliminar_Detalle MAXCODREG >>>>: " + strSQL);
                rst=stm.executeQuery(strSQL);
                
                if (rst.next())
                    intUltReg = (rst.getInt("MAXCODREG"));
                
                intUltReg++;
                
//                strSQL="";
//                strSQL+=" DELETE FROM tbm_detPag "; ////(co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_locPag, co_tipDocPag, co_docPag, co_regPag, nd_abo)"; 
//                strSQL+=" WHERE ";
//                strSQL+=" co_emp = " + intCodEmp;    ///codigo local de pago en tbm_pagMovInv
//                strSQL+=" AND co_loc = " + intCodLoc;    ////codigo de local de tbm_cabpag
//                strSQL+=" AND co_tipdoc = " + STRCODTIPDOC; ////codigo de tipo doc de tbm_cabpag
//                strSQL+=" AND co_doc = " + CODDOCINS;    ////codigo de documento de tbm_cabpag
//                strSQL+=" AND co_docpag = " + CODDOC;    ////codigo de registro de tbm_cabpag
//                
//                System.out.println("---Eliminar_Detalle >>>>: " + strSQL);
//                stm.executeUpdate(strSQL);
                
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch (Exception e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;
    }
    
}
