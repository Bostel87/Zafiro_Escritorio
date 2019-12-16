/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafGenFacAut;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafCtaCtb_dat;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Sistemas6
 */
public class ZafCalDatFac {
    
    private ZafParSis objParSis;
    private ZafCorEle objCorEle;
    private java.awt.Component jifCnt;
    private ZafUtil objUti;
    
   
    private String strCorEleTo="sistemas6@tuvalsa.com;jota.marin@live.com"; 
    private String strTitCorEle="Sistemas: Calculo de Facturas "; 
    private String strVersion="v. 0.1";
    private String strSql="";
    private ZafCtaCtb_dat objZafCtaCtb_dat;
    
    
    private static final int INT_TBL_LINEA = 0;                       //Numero de linea de la tabla
    private static final int INT_TBL_ITMALT = 1;                       //Codigo del item alterno
    private static final int INT_TBL_BUTITM = 2;                       //Boton para buscar item
    private static final int INT_TBL_BUTSTK = 3;                       //Boton para ver el stock
    private static final int INT_TBL_BUTSOL = 4;                       //Boton para ver solicitud
    private static final int INT_TBL_DESITM = 5;                       //Descripcion del item
    private static final int INT_TBL_UNIDAD = 6;                       //Unidad de medida
    private static final int INT_TBL_CODBOD = 7;                       //Codigo de bodega
    private static final int INT_TBL_BUTBOD = 8;                       //Boton para buscar bodega
    private static final int INT_TBL_TRATOT = 9;                       //Transferencia total
    private static final int INT_TBL_CANMOV = 10;                      //Cantidad del movimiento (venta o compra)
    private static final int INT_TBL_PREUNI = 11;                      //Precio de Venta
    private static final int INT_TBL_PORDES = 12;                      //Porcentaje de descuento
    private static final int INT_TBL_BLNIVA = 13;                       //Boolean Iva
    private static final int INT_TBL_TOTAL = 14;                       //Total de la venta o compra del producto
    private static final int INT_TBL_PESTOT = 15;
    private static final int INT_TBL_CODITM = 16;                       //Codigo del item
    private static final int INT_TBL_ESTADO = 17;
    private static final int INT_TBL_IVATXT = 18;
    private static final int INT_TBL_PRE_COS = 19;                       //Columna que contiene  precio de compra
    private static final int INT_TBL_DESPRECOM = 20;                       //Columna que contiene  el descuento de precio de compra  nd_porDesPreCom
    private static final int INT_TBL_COD_PRO = 21;                       //Columna que contiene el codigo del proveedor
    private static final int INT_TBL_NOM_PRO = 22;                       //Columna que contiene el Nombre del proveedor
    private static final int INT_TBL_BUT_PRO = 23;                       //Columna que contiene para busqueda del proveedor
    private static final int INT_TBL_BLNPRE = 24;                       //Columna que contiene verdadero si es problema de precio
    private static final int INT_TBL_ITMALT2 = 25;                       //Columna que contiene verdadero si es problema de precio
    private static final int INT_TBL_ITMSER = 26;                       //Columna que contiene SI el item es de servicio S o N
    private static final int INT_TBL_ITMTER = 27;                       //Columna que contiene "S" (el item es terminal L ) "N"  no corresponde a la terminal L
    private static final int INT_TBL_CODBODPRV = 28;
    private static final int INT_TBL_NOMBODPRV = 29;
    private static final int INT_TBL_BUTBODPRV = 30;
    private static final int INT_TBL_MARUTI = 31;                       //Margen de utilidad
    private static final int INT_TBL_IEBODFIS = 32;                       //Estado que dice si ingresa/egresa fisicamente en bodega
    private static final int INT_TBL_MODNOMITM = 33;                       //Nombre del item modificado
    private static final int INT_TBL_COLOCREL = 34;
    private static final int INT_TBL_COTIPDOCREL = 35;
    private static final int INT_TBL_CODOCREL = 36;
    private static final int INT_TBL_COREGREL = 37;
    private static final int INT_TBL_COLOCRELSOL = 38;
    private static final int INT_TBL_COTIPDOCRELSOL = 39;
    private static final int INT_TBL_CODOCRELSOL = 40;
    private static final int INT_TBL_COREGRELSOL = 41;
    private static final int INT_TBL_COLOCRELOC = 42;
    private static final int INT_TBL_COTIPDOCRELOC = 43;
    private static final int INT_TBL_CODOCRELOC = 44;
    private static final int INT_TBL_COREGRELOC = 45;
    private static final int INT_TBL_PRELISITM = 46;
    private static final int INT_TBL_PRELISITM2 = 47;
    private static final int INT_TBL_CANORI = 48;                       //Cantidad de ventas origen
    private static final int INT_TBL_PREORI = 49;                       //Precio de venta origen
    private static final int INT_TBL_DESORI = 50;                       //Descuento de venta origen
    private static final int INT_TBL_DESVENMAX = 51;                       //Maximo descuento de ventas
    private static final int INT_TBL_NUMFILCOMPVEN = 52;                       //Sirve para saber que item se realizara compra venta entre compaÃ±ias
    private static final int INT_TBL_MAXDESCOM = 53;                       //Maximo descuento de compras
    private static final int INT_TBL_DATBODCOM = 54;                       //Dato de las bodegas donde se realiza la compra
    private static final int INT_TBL_CODREGCOT = 55;
    private static final int INT_TBL_CANVENRES = 56;
    private static final int INT_TBL_PREVTARES = 57;
    private static final int INT_TBL_PORDESRES = 58;
    private static final int INT_TBL_CODITMRES = 59;
    private static final int INT_TBL_TIPUNIMED = 60;                       //Tipo de Unidad de Medida E=Entero F=Fraccion.
    private static final int INT_TBL_BLOPREVTA = 61;                       //Bloquea que no se pueda cambiar precio de lista y porcentaje.
    private static final int INT_TBL_DESITMORI = 62;                      //Descripcion del item origen
    private static final int INT_TBL_CLIRETBOD = 63;                      //Item se retira en otra empresa
    private static final int INT_TBL_CANCLIRETBOD = 64;                      //Cantidad Item se retira en otra empresa
    private static final int INT_TBL_CODCTAEGR = 65;
    private static final int INT_TBL_PESITM = 66;
     
    
     //Tabla Forma de Pago
    private static final int INT_TBL_PAGLIN = 0;                                  //Linea de pago
    private static final int INT_TBL_PAGCRE = 1;                                  //Dias de credito
    private static final int INT_TBL_PAGFEC = 2;                                  //Fecha de vencimiento
    private static final int INT_TBL_PAGRET = 3;                                  //Porcentaje de retencion
    private static final int INT_TBL_PAGMON = 4;                                  //Monto de pago
    private static final int INT_TBL_PAGGRA = 5;                                  //Dias de gracias    
    private static final int INT_TBL_PAGCOD = 6;                                  //Codigo de retencion
    private static final int INT_TBL_PAGSOP = 7;                                  //Soporte de cheque
    private static final int INT_TBL_COMSOL = 8;                                  //Soporte de cheque
    private ZafTblMod objTblModPag, objTblMod;
    private javax.swing.JTable tblPag;
    //Calculos Cotización
    private double dblPorIva;                                                  //Porcentaje de Iva para la empresa enviado en ZafParSis
    private double dblTotalCot, dblIvaCot;
    private double dblSubtotalCot;
    
    
    
    public ZafCalDatFac(ZafParSis obj, java.awt.Component componente){
         objParSis=obj;
         jifCnt=componente;
         objCorEle = new ZafCorEle(objParSis);
         objUti = new ZafUtil();
         
         objUti = new ZafUtil();
         tblPag = new javax.swing.JTable();
         tblDat = new javax.swing.JTable();
          
         
         txtFecDoc = new ZafDatePicker("d/m/y");
         System.out.println(strVersion);
     } 
    
    /**
         * NUEVO JOTA 
         * 
         * @param conExt
         * @param CodEmp
         * @param CodLoc
         * @param CodTipDoc
         * @param CodDoc
         * @return 
         */
        public boolean calcularFactura(java.sql.Connection conExt,int CodEmp, int CodLoc,int CodTipDoc,int CodDoc, int CodCot){
            boolean blnRes=false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            try{
                if(conExt!=null){
                    stmLoc=conExt.createStatement();
                    strSql="";
                    strSql+=" SELECT b1.*  \n";
                    strSql+=" FROM ( \n";
                    strSql+="        SELECT z1.co_emp, z1.co_loc,z1.co_tipDoc,z1.co_doc,a1.fe_doc,a1.nd_porIva, a1.nd_sub,z1.nd_totDet,  \n";
                    strSql+="               CASE WHEN a1.nd_sub <> z1.totDetJota THEN 'NOOOO' ELSE 'OK' END AS mira, z1.totDetJota, a1.nd_valIva, a1.nd_tot , \n";
                    strSql+="               a1.tx_obs2 ,a1.fe_ing, a1.st_AutfacEle ,a1.tx_numAutFacEle \n";
                    strSql+="        FROM ( \n";
                    strSql+="                SELECT x1.co_emp, x1.co_loc,x1.co_tipDoc,x1.co_doc, SUM(x1.nd_tot) as nd_totDet, SUM(x1.totDet) as totDetJota  \n";
                    strSql+="                FROM (  \n";
                    strSql+="                    SELECT a1.co_emp, a1.co_loc,a1.co_tipDoc, a1.co_doc, a2.co_reg,a2.nd_can,a2.nd_preUni,nd_porDes, a2.nd_tot,   \n";
                    strSql+="                           ROUND( (a2.nd_can * a2.nd_preUni) - ((a2.nd_can * a2.nd_preUni) * (nd_porDes/100)),2) as totDet  \n";
                    strSql+="                    FROM tbm_cabMovInv as a1  \n";
                    strSql+="                    INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND \n";
                    strSql+="                                                        a1.co_doc=a2.co_doc)  \n";
                    strSql+="                    INNER JOIN tbm_inv as a3 on (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)  \n";
                    strSql+="                    WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_tipDoc="+CodTipDoc+" AND  \n";
                    strSql+="                            a1.co_doc="+CodDoc+" \n";
                    strSql+="                    GROUP BY a1.co_emp, a1.co_loc,a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.nd_tot , \n";
                    strSql+="                             a2.nd_preUni ,a2.nd_porDes,a2.nd_can ,a3.st_ivaVen,a1.nd_porIva  \n";
                    strSql+="                ) as x1 \n";
                    strSql+="        GROUP BY x1.co_emp, x1.co_loc,x1.co_tipDoc,x1.co_doc \n";
                    strSql+="        ) as z1  \n";
                    strSql+=" INNER JOIN tbm_cabMovInv as a1 ON (z1.co_emp=a1.co_emp AND z1.co_loc=a1.co_loc AND \n";
                    strSql+="                                    z1.co_tipDoc=a1.co_tipDoc AND z1.co_doc=a1.co_doc) \n";
                    strSql+=" ORDER BY mira DESC  \n";
                    strSql+=" ) as b1 \n";
                    strSql+=" WHERE    b1.mira like 'NOOOO' \n";  // SI LE QUITO ESTA LINEA RECALCULA TODO
                    strSql+=" \n";
//                    System.out.println("ZafCalDatFac.calcularFactura:  " + strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    if(rstLoc.next()){
                        /* SI ENTRA ESTA MAL CALCULADA LA FACTURA -- > DEBE REVISARSE */
                        System.out.println("MAL CALCULADA LA FACTURA -- > DEBE REVISARSE  ");
                        cargarTipEmp(conExt,CodEmp,CodLoc);
                        if(modificaDetalle(conExt,CodEmp,CodLoc,CodTipDoc,CodDoc)){
                            if(modificaCabecera(conExt,CodEmp,CodLoc,CodTipDoc,CodDoc)){
                                if(modificaDiario(conExt,CodEmp,CodLoc,CodTipDoc,CodDoc)){
                                    Configurartabla();
                                    if(configurarTablaPago()){
                                        if(obtenerDatosFactura(conExt,CodEmp,CodLoc, CodTipDoc, CodDoc)){
                                            if(FormaRetencion(conExt,CodEmp)){
                                                if(BORRARtbm_PagMovInv(conExt,CodEmp,CodLoc, CodTipDoc, CodDoc)){
                                                    refrescaDatos2(conExt,CodEmp,CodLoc, CodTipDoc, CodDoc, CodCot);
                                                    //cargarDetPag(conExt, CodEmp,CodLoc, CodCot);
                                                    CalculoPago(conExt, CodEmp,CodLoc, CodTipDoc, CodDoc, CodCot); // TOTALES CARGADO????? 
                                                    CalculoPago2(conExt, CodEmp);
                                                    if(insertarPagFac(conExt,CodEmp,CodLoc, CodTipDoc, CodDoc)) {
                                                        blnRes=true;
                                                        System.out.println("RECALCULADO OK!! ");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else{
                        blnRes=true;
                    }
                    rstLoc.close();
                    stmLoc.close();
                    rstLoc = null;
                    stmLoc = null;
		    if(blnRes==false){
                        objCorEle.enviarCorreoMasivo(strCorEleTo, strTitCorEle, "REVISAR: " + CodEmp+" - "+CodLoc+" - "+CodCot);
                    }
                }
            }
            catch(java.sql.SQLException Evt){ 
                blnRes=false; 
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            }
            catch(Exception Evt){ 
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
                blnRes=false;
            }
            return blnRes;
        }
        
        
        
        
    /**
     * Permite cargar datos de la empresa en variables
     */
    private void cargarTipEmp(java.sql.Connection conn, int CodEmp, int CodLoc) {
        Statement stmTipEmp;
        ResultSet rstEmp;
        String sSql;
        try {
            if (conn != null) {
                stmTipEmp = conn.createStatement();
                sSql = "select b.co_tipper , b.tx_descor , round(a.nd_ivaVen,2) as porIva , bod.co_bod, a1.tx_dir, a1.tx_nom as nombod  FROM  tbm_emp as a "
                        + " left join tbm_tipper as b on(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper)"
                        + " left join tbr_bodloc as bod on(bod.co_emp=a.co_emp and bod.co_loc=" + CodLoc + " and bod.st_reg='P')  "
                        + " inner join tbm_bod as a1 on (a1.co_emp=bod.co_emp and a1.co_bod=bod.co_bod ) "
                        + " where a.co_emp=" + CodEmp;
                rstEmp = stmTipEmp.executeQuery(sSql);
                if (rstEmp.next()) {
                     
                    intCodTipPerEmp = rstEmp.getInt("co_tipper");
                     
                }
                rstEmp.close();
                stmTipEmp.close();
                stmTipEmp = null;
                rstEmp = null;
            }
        } 
        catch (SQLException Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            System.err.println("ERROR " + Evt.toString());
        } 
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            System.err.println("ERROR " + Evt.toString());
        }
    }
        
        
        
    private void CalculoPago(java.sql.Connection conPag, int CodEmp, int CodLoc, int CodTipDoc, int CodDoc, int CodCot) {
        try {
            //java.sql.Connection conPag = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conPag != null) {
                String sSQL2 = "SELECT A1.ne_numPag, A2.ne_diaCre, A2.st_sop "
                        + " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                        " Where A1.co_forPag = " + intCodForPag +// Clausulas Where para las tablas maestras
                        "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                        "       and A1.co_emp = " + CodEmp + // Consultando en la empresa en la ke se esta trabajando
                        "       and A2.co_emp = A1.co_emp " + // Consultando en la empresa en la ke se esta trabajando
                        "       order by A2.co_reg ";// Consultando en el local en el ke se esta trabajando

                String sSQL3 = "SELECT count(A2.ne_diaCre) as c "
                        + " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                        " Where A1.co_forPag = " + intCodForPag +// Clausulas Where para las tablas maestras
                        "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                        "       and A1.co_emp = " + CodEmp + // Consultando en la empresa en la ke se esta trabajando
                        "       and A2.co_emp = A1.co_emp ";  // Consultando en la empresa en la ke se esta trabajando

                Statement stmDoc2 = conPag.createStatement();
                System.out.println("CalculoPago.UNO " + sSQL2);
                System.out.println("CalculoPago.DOS " + sSQL3);
                
                ResultSet rstDocCab2 = stmDoc2.executeQuery(sSQL3);
                rstDocCab2.next();
                intCanArr[0] = rstDocCab2.getInt(1);

                stmDoc2 = conPag.createStatement();
                rstDocCab2 = stmDoc2.executeQuery(sSQL2);
                int x = 0;
                while (rstDocCab2.next()) {
                    intarreglodia[x] = rstDocCab2.getInt(2);
                    intarreglonum[x] = rstDocCab2.getInt(1);
                    strarreglosop[x] = rstDocCab2.getString("st_sop");
                    x++;
                }
            }
        } 
        catch (SQLException Evt) {
            System.err.println("ERROR " + Evt.toString());
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        catch (Exception Evt) {
            System.err.println("ERROR " + Evt.toString());
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        calculaPag(conPag,CodEmp,CodLoc,CodTipDoc,CodDoc,CodCot);
    }
    
    
    private void CalculoPago2(java.sql.Connection conPag, int CodEmp) {
        try {
           // java.sql.Connection conPag = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conPag != null) {

                String sSQL2 = "SELECT A1.ne_numPag, A2.ne_diaCre, A2.st_sop "
                        + " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                        " Where A1.co_forPag = " + intCodForPag +// Clausulas Where para las tablas maestras
                        "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                        "       and A1.co_emp = " + CodEmp + // Consultando en la empresa en la ke se esta trabajando
                        "       and A2.co_emp = A1.co_emp " + // Consultando en la empresa en la ke se esta trabajando
                        "       order by A2.co_reg ";// Consultando en el local en el ke se esta trabajando

                String sSQL3 = "SELECT count(A2.ne_diaCre) as c "
                        + " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                        " Where A1.co_forPag = " + intCodForPag +// Clausulas Where para las tablas maestras
                        "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                        "       and A1.co_emp = " + CodEmp + // Consultando en la empresa en la ke se esta trabajando
                        "       and A2.co_emp = A1.co_emp ";  // Consultando en la empresa en la ke se esta trabajando

                Statement stmDoc2 = conPag.createStatement();
                ResultSet rstDocCab2 = stmDoc2.executeQuery(sSQL3);
                rstDocCab2.next();
                intCanArr[0] = rstDocCab2.getInt(1);

                stmDoc2 = conPag.createStatement();
                rstDocCab2 = stmDoc2.executeQuery(sSQL2);
                int x = 0;
                while (rstDocCab2.next()) {
                    intarreglodia[x] = rstDocCab2.getInt(2);
                    intarreglonum[x] = rstDocCab2.getInt(1);
                    strarreglosop[x] = rstDocCab2.getString("st_sop");
                    x++;
                }
                rstDocCab2.close();
                rstDocCab2 = null;
//                conPag.close();
//                conPag = null;
            }
        }
        catch (SQLException Evt) {
            System.err.println("ERROR " + Evt.toString());
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        } 
        catch (Exception Evt) {
            System.err.println("ERROR " + Evt.toString());
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
    }

    
    public void calculaPag(java.sql.Connection conLoc, int intCodEmpFacEle, int intCodLocFacEle, int intCodTipDocFacEle, int intCodDoc, int intCodCot) {
        int intVal = intCanArr[0];
        int intsizearre = intarreglodia.length;
        intVal = intsizearre - (intsizearre - intVal);
        double dblBaseDePagos = 0, dblRetIva = 0, dblRetFue = 0, dblRetFueFle = 0;
        double dblPago = 0.00;
        double dblPagos = 0.00;
        double dblRete = 0;
        int i = 0;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
       
        try {
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            datFecDoc = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            strFecSisBase = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaBaseDatos());
            java.util.Date dateObj = datFecDoc;
            Calendar calObj = Calendar.getInstance();
            calObj.setTime(dateObj);

            objFec.set(java.util.Calendar.DAY_OF_MONTH, calObj.get(Calendar.DAY_OF_MONTH));
            objFec.set(java.util.Calendar.MONTH, calObj.get(Calendar.MONTH) + 1 );
            objFec.set(java.util.Calendar.YEAR, calObj.get(Calendar.YEAR));
                
            //dblBaseDePagos = dblTotalCot;

//            if(dblValorPagar>0){
                dblBaseDePagos = dblValorPagar;
//            }
//            else{
//                dblBaseDePagos = dblTotalCot;
//            }

            
            dblRetFueGlo = 0.0;
            dblRetIvaGlo = 0.00;

            
            if (strCodTipPerCli.equals("")) {
                objCorEle.enviarCorreoMasivo(strCorEleTo,strTitCorEle," Cliente sin tipo de persona..." + intCodEmpFacEle + "-"+
                                                intCodLocFacEle+"-"+intCodCot);
                System.err.println("ERROR: CLIENTE SIN TIPO DE PERSONA");
                objTblModPag.removeAllRows();
            } 
            else {
                if(dblBaseDePagos > 0) {
                    vecDataTblPag = new Vector();
                    Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
                    //*************************************************************************************///
                    //java.sql.Connection conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (conLoc != null) {
                        stmLoc = conLoc.createStatement();
                        strSql="";
                        strSql+=" SELECT a1.*,a2.st_ser, a3.tx_desCor as tx_desUni \n ";
                        strSql+=" FROM tbm_detCotVen as a1 \n";
                        strSql+=" INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                        strSql+=" INNER JOIN tbm_var as a3 ON (a2.co_uni=a3.co_reg) \n";
                        strSql+=" WHERE a1.co_emp="+intCodEmpFacEle+ " AND ";
                        strSql+=" a1.co_loc="+intCodLocFacEle+" AND a1.co_cot="+intCodCot;
                        System.out.println("====>>>  \n" + strSql);
                        rstLoc=stmLoc.executeQuery(strSql);
                        while(rstLoc.next()){
                            /**
                            * ***************************************************************************************************************************
                            */
                           calculaSubtotalServiNoServi(conLoc, intCodEmpFacEle, "T");

                           if (dblSubSerNoSer > 0.00) {
                               cargaForPag(conLoc,intCodEmpFacEle, intCodMotTran);
                           }

                           /**
                            * ***************************************************************************************************************************
                            */
                           calculaSubtotalServiNoServi(conLoc,intCodEmpFacEle, "N");

                           if (dblSubSerNoSer > 0.00) {
                               cargaForPag(conLoc,intCodEmpFacEle, intCodMotBien);
                           }

                           calculaSubtotalServiNoServi(conLoc,intCodEmpFacEle, "S");

                           if (dblSubSerNoSer > 0.00) {
                               cargaForPag(conLoc,intCodEmpFacEle, intCodMotServ);
                            }
                        }
                        
                        if(blnIsComSol && dblPorIva==14.00){
                            cargaForPagComSol();
                        }
 
                    }

                    dblRete = dblRetFueGlo + dblRetIvaGlo;
                    if(dblValorPagar>0){
                        //dblBaseDePagos = dblTotalCot;
                        dblBaseDePagos = objUti.redondear((dblValorPagar  - dblRete), objParSis.getDecimalesMostrar());
                    }
                    else{
                        dblBaseDePagos = objUti.redondear((dblTotalCot - dblRete), objParSis.getDecimalesMostrar());
                    }
//                    dblBaseDePagos = objUti.redondear((dblTotalCot - dblRete), objParSis.getDecimalesMostrar());



                    for (i = 0; i < intVal; i++) {
                        java.util.Calendar objFecPagActual = Calendar.getInstance();
                        objFecPagActual.setTime(objFec.getTime());

                        int diaCre = intarreglodia[i];
                        int numPag = intarreglonum[i];
                        String strSop = ((strarreglosop[i] == null) ? "N" : strarreglosop[i]);

                        if (diaCre != 0) {
                            objFecPagActual.add(java.util.Calendar.DATE, diaCre);
                        }

                        dtePckPag.setAnio(objFecPagActual.get(java.util.Calendar.YEAR));
                        dtePckPag.setMes(objFecPagActual.get(java.util.Calendar.MONTH) + 1);
                        dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));

                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_PAGLIN, "");
                        vecReg.add(INT_TBL_PAGCRE, "" + diaCre);
                        vecReg.add(INT_TBL_PAGFEC, dtePckPag.getText());

                        dblPagos = objUti.redondear((numPag == 0) ? 0 : (dblBaseDePagos / numPag), objParSis.getDecimalesMostrar());
                        dblPago += dblPagos;
                        dblPagos = objUti.redondear(dblPagos, objParSis.getDecimalesMostrar());

                        vecReg.add(INT_TBL_PAGRET, "");

                        if (i == (intVal - 1)) {
                            if(dblValorPagar>0){
                                dblPagos = objUti.redondear(dblPagos + (dblValorPagar  - (dblPago + dblRete)), objParSis.getDecimalesMostrar());
                            }
                            else{
                                dblPagos = objUti.redondear(dblPagos + (dblTotalCot - (dblPago + dblRete)), objParSis.getDecimalesMostrar());
                            }
                        }

                        vecReg.add(INT_TBL_PAGMON, "" + dblPagos);
                        vecReg.add(INT_TBL_PAGGRA, "0");
                        vecReg.add(INT_TBL_PAGCOD, "");
                        vecReg.add(INT_TBL_PAGSOP, strSop);
                        vecReg.add(INT_TBL_COMSOL,"N");
                        vecDataTblPag.add(vecReg);
                    }
                    objTblModPag.setData(vecDataTblPag);
                    tblPag.setModel(objTblModPag);

                    vecDataTblPag = null;

                    /**
                     * ********************************************************************************************
                     */
                    
                    double dblValRet = 0;
                    String strFecCor = "";
                    
                    for (int x = 0; x < tblPag.getRowCount(); x++) {
                        dblValRet = Double.parseDouble(getIntDatoValidado(tblPag.getValueAt(x, INT_TBL_PAGRET)));
                        if (dblValRet == 0.00) {
                            strFecCor = tblPag.getValueAt(x, INT_TBL_PAGFEC).toString();
                            break;
                        }
                    }

                    String strF1 = objUti.formatearFecha(strFecSisBase, "yyyy-MM-dd", "yyyy/MM/dd");
                    java.util.Date fac1 = objUti.parseDate(strF1, "yyyy/MM/dd");
                    int intAnioAct = (fac1.getYear() + 1900);
                    if(strFecCor.length()==0)
                        strFecCor=strFecSisBase;    
                    //  por alfredo.  año nuevo 31 dic año anterior
                    String strF = objUti.formatearFecha(strFecCor, "dd/MM/yyyy", "yyyy/MM/dd");
                    java.util.Date fac = objUti.parseDate(strF, "yyyy/MM/dd");
                    int intAnioCre = (fac.getYear() + 1900);


                    if (intAnioCre > intAnioAct) {
                        strFecCor = "31/12/" + intAnioAct;
                    }

                    for (int x = 0; x < tblPag.getRowCount(); x++) {
                        dblValRet = Double.parseDouble(getIntDatoValidado(tblPag.getValueAt(x, INT_TBL_PAGRET)));
                        if (dblValRet > 0.00) {
                            tblPag.setValueAt(strFecCor, x, INT_TBL_PAGFEC);
                        }
                    }
                }
            }
        } catch (SQLException Evt) {
            System.err.println("ERROR " + Evt.toString());
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        } catch (Exception Evt) {
            System.err.println("ERROR " + Evt.toString());
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
    }
        
        private boolean BORRARtbm_PagMovInv (java.sql.Connection conExt,int CodEmp, int CodLoc,int CodTipDoc,int CodDoc){
            boolean blnRes=false;
            java.sql.Statement stmLoc;
            try{
                if(conExt!=null){
                    stmLoc=conExt.createStatement();
                    strSql="";
                    strSql=" DELETE FROM tbm_pagMovInv WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_tipDoc="+CodTipDoc+" AND co_doc="+CodDoc+"; \n";
                    System.out.println("BORRARtbm_PagMovInv: \n" + strSql);
                    stmLoc.executeUpdate(strSql);
                    blnRes=true;
                    stmLoc.close();
                    stmLoc = null;
                }
            }
            catch(java.sql.SQLException Evt){ 
                blnRes=false; 
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            }
            catch(Exception Evt){ 
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
                blnRes=false;
            }
            return blnRes;
        }
        
        private boolean modificaDetalle (java.sql.Connection conExt,int CodEmp, int CodLoc,int CodTipDoc,int CodDoc){
            boolean blnRes=false;
            java.sql.Statement stmLoc;
            try{
                if(conExt!=null){
                    stmLoc=conExt.createStatement();
                    strSql="";
                    strSql=" UPDATE tbm_detMovInv SET nd_tot=x.totDet FROM( \n";
                    strSql+="       SELECT a1.co_emp, a1.co_loc,a1.co_tipDoc, a1.co_doc, a2.co_reg,a2.nd_can,a2.nd_preUni,nd_porDes, a2.nd_tot, \n";
                    strSql+="              ROUND( (a2.nd_can * a2.nd_preUni) - ((a2.nd_can * a2.nd_preUni) * (nd_porDes/100)),2) as totDet  \n";
                    strSql+="       FROM tbm_cabMovInv as a1  \n";
                    strSql+="       INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND  \n";
                    strSql+="                                          a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  \n";
                    strSql+="       INNER JOIN tbm_inv as a3 on (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)  \n";
                    strSql+="       WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_tipDoc="+CodTipDoc+" AND \n";
                    strSql+="             a1.co_doc="+CodDoc+" \n";
                    strSql+="       GROUP BY a1.co_emp, a1.co_loc,a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.nd_tot , \n";
                    strSql+="                a2.nd_preUni ,a2.nd_porDes,a2.nd_can ,a3.st_ivaVen,a1.nd_porIva  \n";
                    strSql+=" ) AS x \n";
                    strSql+=" WHERE tbm_detMovInv.co_emp=x.co_emp AND tbm_detMovInv.co_loc=x.co_loc AND \n";
                    strSql+="       tbm_detMovInv.co_tipDoc=x.co_tipDoc AND tbm_detMovInv.co_doc=x.co_doc AND  \n";
                    strSql+="       tbm_detMovInv.co_reg=x.co_reg; \n";
                    strSql+=" \n";
//                    System.out.println("modificaDetalle: " + strSql);
                    stmLoc.executeUpdate(strSql);
                    blnRes=true;
                    stmLoc.close();
                    stmLoc = null;
                }
            }
            catch(java.sql.SQLException Evt){ 
                blnRes=false; 
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            }
            catch(Exception Evt){ 
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
                blnRes=false;
            }
            return blnRes;
        }
    
         private boolean modificaCabecera(java.sql.Connection conExt,int CodEmp, int CodLoc,int CodTipDoc,int CodDoc){
            boolean blnRes=false;
            java.sql.Statement stmLoc;
            try{
                if(conExt!=null){
                    stmLoc=conExt.createStatement();
                    strSql="";
                    strSql+=" UPDATE tbm_cabMovInv SET nd_sub=x.nd_SubTotal  \n";
                    strSql+=" FROM( \n";
                    strSql+="        SELECT x1.co_emp, x1.co_loc,x1.co_tipDoc,x1.co_doc, SUM(x1.nd_tot) as nd_SubTotal   \n";
                    strSql+="        FROM (  \n";
                    strSql+="                SELECT a1.co_emp, a1.co_loc,a1.co_tipDoc, a1.co_doc,a2.co_reg, a2.nd_tot , a2.st_ivaCom \n";
                    strSql+="                FROM tbm_cabMovInv as a1  \n";
                    strSql+="                INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND \n";
                    strSql+="                                                    a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  \n";
                    strSql+="                WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_tipDoc="+CodTipDoc+" AND \n";
                    strSql+="                      a1.co_doc="+CodDoc+" \n";
                    strSql+="        ) as x1 \n";
                    strSql+="        GROUP BY x1.co_emp, x1.co_loc,x1.co_tipDoc,x1.co_doc  \n";
                    strSql+=" ) AS x \n";
                    strSql+=" WHERE tbm_cabMovInv.co_emp=x.co_emp AND tbm_cabMovInv.co_loc=x.co_loc AND  \n";
                    strSql+="        tbm_cabMovInv.co_tipDoc=x.co_tipDoc AND tbm_cabMovInv.co_doc=x.co_doc;   \n";
//                    System.out.println("modificaCabecera (Subtotal): " + strSql);
                    stmLoc.executeUpdate(strSql);
                    
                    strSql="";
                    strSql+=" UPDATE tbm_cabMovInv SET nd_valIva = x.IVA, nd_valComSol = x.COMPENSACION \n";
                    strSql+=" FROM( \n";
                    strSql+="        SELECT x.co_emp, x.co_loc,x.co_tipDoc,x.co_doc,a.nd_sub, ROUND(x.nd_subTotalSoloIva * (x.nd_porIva/100),2) as IVA,  \n";
                    strSql+="               ROUND((x.nd_subTotalSoloIva*-1) * (x.nd_porComSol/100),2) as COMPENSACION \n";
                    strSql+="        FROM ( \n";
                    strSql+="            SELECT x1.co_emp, x1.co_loc,x1.co_tipDoc,x1.co_doc, x1.nd_porIva,x1.nd_porComSol,  \n";
                    strSql+="                    SUM(x1.nd_tot) as nd_SubTotalSoloIva \n";
                    strSql+="            FROM ( \n";
                    strSql+="                    SELECT a1.co_emp, a1.co_loc,a1.co_tipDoc, a1.co_doc,a2.co_reg,a1.nd_porIva, \n";
                    strSql+="                            a1.nd_porComSol, a2.nd_tot , a2.st_ivaCom  \n";
                    strSql+="                    FROM tbm_cabMovInv as a1  \n";
                    strSql+="                    INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND \n";
                    strSql+="                                                       a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
                    strSql+="                    WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_tipDoc="+CodTipDoc+" AND \n";
                    strSql+="                          a1.co_doc="+CodDoc+" AND a2.st_ivaCom = 'S' \n";
                    strSql+="            ) as x1 \n";
                    strSql+="            GROUP BY x1.co_emp, x1.co_loc,x1.co_tipDoc,x1.co_doc , x1.nd_porIva,x1.nd_porComSol \n";
                    strSql+="        ) as X \n";
                    strSql+="        INNER JOIN tbm_cabMovInv as a ON (x.co_emp=a.co_emp AND x.co_loc=a.co_loc AND x.co_tipDoc=a.co_tipDoc AND  \n";
                    strSql+="                                            x.co_doc=a.co_doc) \n";
                    strSql+=" ) AS x \n";
		    strSql+=" WHERE tbm_cabMovInv.co_emp=x.co_emp AND tbm_cabMovInv.co_loc=x.co_loc AND  \n";
                    strSql+="       tbm_cabMovInv.co_tipDoc=x.co_tipDoc AND tbm_cabMovInv.co_doc=x.co_doc; \n";
//                    System.out.println("modificaCabecera (IVA COMPENSACION): " + strSql);
                    stmLoc.executeUpdate(strSql);
      
                   strSql="";
                   strSql+=" UPDATE tbm_cabMovInv SET nd_subIvaGra=x.nd_val  \n";
                   strSql+=" FROM( \n";
                   strSql+="        SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,ROUND(SUM( a2.nd_tot),2) as nd_val \n";
                   strSql+="        FROM tbm_cabMovInv as a1 \n";
                   strSql+="        INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc \n";
                   strSql+="                                            AND a1.co_doc=a2.co_doc) \n";
                   strSql+="        INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                   strSql+="        WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_tipDoc="+CodTipDoc+" AND \n";
                   strSql+="               a1.co_doc="+CodDoc+" AND a3.st_ivaVen='S' \n";
                   strSql+="        GROUP BY a1.co_emp, a1.co_loc,a1.co_tipDoc, a1.co_doc \n";
                   strSql+=" ) AS x \n";
                   strSql+=" WHERE tbm_cabMovInv.co_emp=x.co_emp AND tbm_cabMovInv.co_loc=x.co_loc AND tbm_cabMovInv.co_tipDoc=x.co_tipDoc AND  \n";
                   strSql+="        tbm_cabMovInv.co_doc=x.co_doc ;  \n";
//                   System.out.println("modificaCabecera (SUBTOTAL GRAVA IVA): " + strSql);
                   stmLoc.executeUpdate(strSql); 
                    
                   strSql="";        
                   strSql+=" UPDATE tbm_cabMovInv SET nd_subIvaCer=x.nd_val \n";
                   strSql+=" FROM( \n";
                   strSql+="        SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,ROUND(SUM( a2.nd_tot),2) as nd_val \n";
                   strSql+="        FROM tbm_cabMovInv as a1 \n";
                   strSql+="        INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND \n";
                   strSql+="                                           a1.co_doc=a2.co_doc) \n";
                   strSql+="        INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)  \n";
                   strSql+="        WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_tipDoc="+CodTipDoc+" AND \n";
                   strSql+="               a1.co_doc="+CodDoc+" AND a3.st_ivaVen='N' \n";
                   strSql+="        GROUP BY a1.co_emp, a1.co_loc,a1.co_tipDoc, a1.co_doc  \n";
                   strSql+=" ) AS x \n";
                   strSql+=" WHERE tbm_cabMovInv.co_emp=x.co_emp AND tbm_cabMovInv.co_loc=x.co_loc AND tbm_cabMovInv.co_tipDoc=x.co_tipDoc AND  \n";
                   strSql+="        tbm_cabMovInv.co_doc=x.co_doc;  \n";
//                   System.out.println("modificaCabecera (SUBTOTAL SIN IVA): " + strSql);
                   stmLoc.executeUpdate(strSql); 

	            
                    strSql="";
                    strSql+=" UPDATE tbm_cabMovInv SET nd_tot=x.nd_total, tx_obs2=tx_obs2 || ' "+strVersion+"' \n";
                    strSql+=" FROM( \n";
                    strSql+="       SELECT co_emp, co_loc, co_tipDoc, co_doc, nd_sub, nd_valIva, ROUND(nd_sub + nd_valIva,2 ) as nd_total \n";
                    strSql+="       FROM tbm_cabMovInv \n";
                    strSql+="       WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_tipDoc="+CodTipDoc+" AND \n";
                    strSql+="             co_doc="+CodDoc+" \n";
                    strSql+=" ) AS x\n";
                    strSql+=" WHERE tbm_cabMovInv.co_emp=x.co_emp AND tbm_cabMovInv.co_loc=x.co_loc AND \n";
                    strSql+="       tbm_cabMovInv.co_tipDoc=x.co_tipDoc AND tbm_cabMovInv.co_doc=x.co_doc; \n";
                    strSql+=" \n";
                    strSql+=" \n";
//                    System.out.println("modificaCabecera (TOTAL): " + strSql);
                    stmLoc.executeUpdate(strSql);
                    blnRes=true;
                }
            }
            catch(java.sql.SQLException Evt){ 
                blnRes=false; 
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            }
            catch(Exception Evt){ 
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
                blnRes=false;
            }
            return blnRes;
        }
         
         
          
          private boolean modificaDiario(java.sql.Connection conExt,int CodEmp, int CodLoc,int CodTipDoc,int CodDoc){
            boolean blnRes=false;
            java.sql.Statement stmLoc;
            int intCodCta=-1;
            String strCodCta, strNomCta;
            try{
                if(conExt!=null){
                    stmLoc=conExt.createStatement();
                    objZafCtaCtb_dat = new ZafCtaCtb_dat(objParSis,CodEmp, CodLoc, CodTipDoc);
                    dblPorIva =  objZafCtaCtb_dat.getPorIvaVen();
                    dblPorComSol = objZafCtaCtb_dat.getPorComSol();
                    
                    /* IVA CERO */
                    if(CodEmp==1){
                        intCodCta=4680;
                        strCodCta="4.01.01.04";
                        strNomCta="VENTAS MERCADERIAS GQUIL T/. 0%";
                    }else if(CodEmp==2){
                        intCodCta=2303;
                        strCodCta="4.01.01.04";
                        strNomCta="VENTAS MERCADERIAS GQUIL T/. 0%";
                    }else if(CodEmp==4){
                        intCodCta=3210;
                        strCodCta="4.01.01.04";
                        strNomCta="VENTAS MERCADERIAS GQUIL T/. 0%";
                    }
                    
                    strSql="";
                    strSql+=" UPDATE tbm_detDia SET nd_monDeb = ABS(x.nd_Tot) \n";
                    strSql+=" FROM( \n";
                    strSql+="       SELECT a1.co_emp, a1.co_loc,a1.co_tipDoc, a1.co_doc,a2.co_dia,a2.co_reg, a1.nd_tot, a2.nd_monDeb, a2.nd_monHab \n";
                    strSql+="       FROM tbm_cabMovInv as a1  \n";
                    strSql+="       INNER Join tbm_detDia as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND \n";
                    strSql+="                                       a1.co_doc=a2.co_dia) \n";
                    strSql+="       WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_tipDoc="+CodTipDoc+" AND \n";
                    strSql+="             a1.co_doc="+CodDoc+" AND a2.co_cta="+objZafCtaCtb_dat.getCtaDeb()+" \n";
                    strSql+="       ) as x \n";
                    strSql+=" WHERE tbm_detDia.co_emp=x.co_emp AND tbm_detDia.co_loc=x.co_loc AND  \n";
                    strSql+=" tbm_detDia.co_tipDoc=x.co_tipDoc AND tbm_detDia.co_dia=x.co_dia AND tbm_detDia.co_reg=x.co_reg ; \n";
//                    System.out.println("modificaDiario (TOTAL DEBE): " + strSql);
                    stmLoc.executeUpdate(strSql);
                    
                    strSql+=" UPDATE tbm_detDia SET nd_monHab = ABS(x.nd_sub) \n";
                    strSql+=" FROM( \n";
                    strSql+="       SELECT a1.co_emp, a1.co_loc,a1.co_tipDoc, a1.co_doc,a2.co_dia,a2.co_reg, a1.nd_sub, a2.nd_monDeb, a2.nd_monHab \n";
                    strSql+="       FROM tbm_cabMovInv as a1  \n";
                    strSql+="       INNER Join tbm_detDia as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND \n";
                    strSql+="                                       a1.co_doc=a2.co_dia) \n";
                    strSql+="       WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_tipDoc="+CodTipDoc+" AND \n";
                    strSql+="             a1.co_doc="+CodDoc+" AND a2.co_cta="+objZafCtaCtb_dat.getCtaHab()+" \n";
                    strSql+="       ) as x \n";
                    strSql+=" WHERE tbm_detDia.co_emp=x.co_emp AND tbm_detDia.co_loc=x.co_loc AND  \n";
                    strSql+=" tbm_detDia.co_tipDoc=x.co_tipDoc AND tbm_detDia.co_dia=x.co_dia AND tbm_detDia.co_reg=x.co_reg ; \n";
//                    System.out.println("modificaDiario (SUBTOTAL HABER): " + strSql);
                    stmLoc.executeUpdate(strSql);
                    
                    strSql+=" UPDATE tbm_detDia SET nd_monHab = ABS(x.nd_valIva) \n";
                    strSql+=" FROM( \n";
                    strSql+="       SELECT a1.co_emp, a1.co_loc,a1.co_tipDoc, a1.co_doc,a2.co_dia,a2.co_reg, a1.nd_valIva, a2.nd_monDeb, a2.nd_monHab \n";
                    strSql+="       FROM tbm_cabMovInv as a1  \n";
                    strSql+="       INNER Join tbm_detDia as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND \n";
                    strSql+="                                       a1.co_doc=a2.co_dia) \n";
                    strSql+="       WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_tipDoc="+CodTipDoc+" AND \n";
                    strSql+="             a1.co_doc="+CodDoc+" AND a2.co_cta="+objZafCtaCtb_dat.getCtaIvaVentas()+" \n";
                    strSql+="       ) as x \n";
                    strSql+=" WHERE tbm_detDia.co_emp=x.co_emp AND tbm_detDia.co_loc=x.co_loc AND  \n";
                    strSql+=" tbm_detDia.co_tipDoc=x.co_tipDoc AND tbm_detDia.co_dia=x.co_dia AND tbm_detDia.co_reg=x.co_reg ; \n";
//                    System.out.println("modificaDiario (IVA GRAVADO HABER): " + strSql);
                    stmLoc.executeUpdate(strSql);
                    
                    strSql+=" UPDATE tbm_detDia SET nd_monHab = ABS(x.nd_subIvaCer) \n";
                    strSql+=" FROM( \n";
                    strSql+="       SELECT a1.co_emp, a1.co_loc,a1.co_tipDoc, a1.co_doc,a2.co_dia,a2.co_reg, a1.nd_subIvaCer, a2.nd_monDeb, a2.nd_monHab \n";
                    strSql+="       FROM tbm_cabMovInv as a1  \n";
                    strSql+="       INNER Join tbm_detDia as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND \n";
                    strSql+="                                       a1.co_doc=a2.co_dia) \n";
                    strSql+="       WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_tipDoc="+CodTipDoc+" AND \n";
                    strSql+="             a1.co_doc="+CodDoc+" AND a2.co_cta="+intCodCta+" \n";
                    strSql+="       ) as x \n";
                    strSql+=" WHERE tbm_detDia.co_emp=x.co_emp AND tbm_detDia.co_loc=x.co_loc AND  \n";
                    strSql+=" tbm_detDia.co_tipDoc=x.co_tipDoc AND tbm_detDia.co_dia=x.co_dia AND tbm_detDia.co_reg=x.co_reg ; \n";
//                    System.out.println("modificaDiario (IVA CERO HABER): " + strSql);
                    stmLoc.executeUpdate(strSql);
                    
                    blnRes=true;
                    stmLoc.close();
                    stmLoc = null;
                }
            }
            catch(java.sql.SQLException Evt){ 
                blnRes=false; 
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            }
            catch(Exception Evt){ 
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
                blnRes=false;
            }
            return blnRes;
        }
         
          
          
    private boolean Configurartabla() 
    {
        boolean blnRes = false;
        try {
            

            //Configurar JTable: Establecer el modelo.
            //Almacena los datos
            Vector vecDat = new Vector();   
            //Almacena las cabeceras
            Vector vecCab = new Vector();    
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_ITMALT, "Cod. Item");
//            vecCab.add(INT_TBL_ITMALTCOD3LET,"Cód.Let.");
            vecCab.add(INT_TBL_BUTITM, "..");
            vecCab.add(INT_TBL_BUTSTK, "..");
            vecCab.add(INT_TBL_BUTSOL, "..");
            vecCab.add(INT_TBL_DESITM, "Descripcion");
            vecCab.add(INT_TBL_UNIDAD, "Unidad");
            vecCab.add(INT_TBL_CODBOD, "Bodega");
            vecCab.add(INT_TBL_BUTBOD, "..");
            vecCab.add(INT_TBL_TRATOT, "Tranf.Tot.");
            vecCab.add(INT_TBL_CANMOV, "Cantidad");
            vecCab.add(INT_TBL_PREUNI, "Precio");
            vecCab.add(INT_TBL_PORDES, "%Desc");
            vecCab.add(INT_TBL_BLNIVA, "IVA");
            vecCab.add(INT_TBL_TOTAL, "Total");
            vecCab.add(INT_TBL_PESTOT, "Pes.Tot.");   /* José Marín - 20/Oct/2015 */
            vecCab.add(INT_TBL_CODITM, "coditm");
            vecCab.add(INT_TBL_ESTADO, "estado");
            vecCab.add(INT_TBL_IVATXT, "ivatxt");
            vecCab.add(INT_TBL_PRE_COS, "Pre.Com.");                            //Columna que contiene  precio de compra
            vecCab.add(INT_TBL_DESPRECOM, " %Desc.");
            vecCab.add(INT_TBL_COD_PRO, "Cod.prv.");                            //Columna que contiene el codigo del proveedor
            vecCab.add(INT_TBL_NOM_PRO, "Proveedor");                           //Columna que contiene el Nombre del proveedor
            vecCab.add(INT_TBL_BUT_PRO, "..");
            vecCab.add(INT_TBL_BLNPRE, "blnpre");
            vecCab.add(INT_TBL_ITMALT2, "ItmAlt2");
            vecCab.add(INT_TBL_ITMSER, "Itm.SER.");
            vecCab.add(INT_TBL_ITMTER, " isTerL");
            vecCab.add(INT_TBL_CODBODPRV, "Cód.Bod");
            vecCab.add(INT_TBL_NOMBODPRV, "Nom.Bod");
            vecCab.add(INT_TBL_BUTBODPRV, "");
            vecCab.add(INT_TBL_MARUTI, "Mar.Uti.");
            vecCab.add(INT_TBL_IEBODFIS, "IE.Fis.Bod");
            vecCab.add(INT_TBL_MODNOMITM, "Mod.Nom.Itm");
            vecCab.add(INT_TBL_COLOCREL, "Cod.Loc.Rel");
            vecCab.add(INT_TBL_COTIPDOCREL, "Tip.Doc.Rel");
            vecCab.add(INT_TBL_CODOCREL, "Cod.Doc.Rel");
            vecCab.add(INT_TBL_COREGREL, "Cod.Reg.Rel");
            vecCab.add(INT_TBL_COLOCRELSOL, "Cod.Loc.Rel");
            vecCab.add(INT_TBL_COTIPDOCRELSOL, "Tip.Doc.Rel");
            vecCab.add(INT_TBL_CODOCRELSOL, "Cod.Doc.Rel");
            vecCab.add(INT_TBL_COREGRELSOL, "Cod.Reg.Rel");
            vecCab.add(INT_TBL_COLOCRELOC, "Cod.Loc.Rel");
            vecCab.add(INT_TBL_COTIPDOCRELOC, "Tip.Doc.Rel");
            vecCab.add(INT_TBL_CODOCRELOC, "Cod.Doc.Rel");
            vecCab.add(INT_TBL_COREGRELOC, "Cod.Reg.Rel");
            vecCab.add(INT_TBL_PRELISITM, "Pre.Lis.Itm");
            vecCab.add(INT_TBL_PRELISITM2, "Pre.Lis.Itm2");
            vecCab.add(INT_TBL_CANORI, "");
            vecCab.add(INT_TBL_PREORI, "");
            vecCab.add(INT_TBL_DESORI, "");
            vecCab.add(INT_TBL_DESVENMAX, "Por.Des.Ven.MAx");
            vecCab.add(INT_TBL_NUMFILCOMPVEN, "");
            vecCab.add(INT_TBL_MAXDESCOM, "MaxDesCom");
            vecCab.add(INT_TBL_DATBODCOM, "datCom");
            vecCab.add(INT_TBL_CODREGCOT, "CodReg");
            vecCab.add(INT_TBL_CANVENRES, "CANVENRES");
            vecCab.add(INT_TBL_PREVTARES, "PREVTARES");
            vecCab.add(INT_TBL_PORDESRES, "PORDESRES");
            vecCab.add(INT_TBL_CODITMRES, "CODITMRES");
            vecCab.add(INT_TBL_TIPUNIMED, "TIPUNIMED");
            vecCab.add(INT_TBL_BLOPREVTA, "Blo.Pre.Vta");
            vecCab.add(INT_TBL_DESITMORI, "Des.Itm.");
            vecCab.add(INT_TBL_CLIRETBOD, "Item ret otr bod");
            vecCab.add(INT_TBL_CANCLIRETBOD, "can Itm ret bod");
            vecCab.add(INT_TBL_CODCTAEGR, "Cód.Cta.Egr.");
            vecCab.add(INT_TBL_PESITM, "Pes.Itm."); /* José Marín 2015/Oct/21 */
            

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat, INT_TBL_LINEA);

            objTblMod.setColumnDataType(INT_TBL_CANMOV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PREUNI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PORDES, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_TOTAL, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PESTOT, ZafTblMod.INT_COL_DBL, new Integer(0), null);  /* José Marín - 21/Oct/2015 */
            objTblMod.setColumnDataType(INT_TBL_PRE_COS, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DESPRECOM, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PESITM, ZafTblMod.INT_COL_DBL, new Integer(0), null);  /* José Marín - 21-Oct-2015 */
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            ArrayList arlAux = new ArrayList();
            arlAux.add("" + INT_TBL_ITMALT);
            arlAux.add("" + INT_TBL_CODBOD);
            arlAux.add("" + INT_TBL_CANMOV);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux = null;
            
            

            //Configurar JTable: Establecer el menú de contexto.
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux = tblDat.getColumnModel();
            //Configurar JTable: Establecer el ancho de las columnas.

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_ITMALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_BUTITM).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_BUTSTK).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_BUTSOL).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DESITM).setPreferredWidth(220);
            tcmAux.getColumn(INT_TBL_UNIDAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_BUTBOD).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_TRATOT).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CANMOV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PREUNI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PORDES).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_BLNIVA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_TOTAL).setPreferredWidth(55);
            
            tcmAux.getColumn(INT_TBL_PESTOT).setPreferredWidth(55);  /* José Marín - 20/Oct/2015 */
            
            tcmAux.getColumn(INT_TBL_PRE_COS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DESPRECOM).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_NOM_PRO).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_BUT_PRO).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_BUTBODPRV).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_PESITM).setPreferredWidth(10);
            
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tblDat.getTableHeader().setReorderingAllowed(false);
            
           
            
            blnRes = true;
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
            System.err.println("Configurartabla.ERROR " + e.toString());
        }
        return blnRes;
    }

   
        /**
     * Configura la tabla de pago
     *
     * @return true si OK 
     * @return false si hay algun error
     */
    private boolean configurarTablaPago() 
    {
        boolean blnRes = false;
        try 
        {
            //Almacena cabeceras
            Vector vecCabPAg = new Vector();                           
            vecCabPAg.clear();
            vecCabPAg.add(INT_TBL_PAGLIN, "");
            vecCabPAg.add(INT_TBL_PAGCRE, "Días.Crédito");
            vecCabPAg.add(INT_TBL_PAGFEC, "Fec.Vencimiento");
            vecCabPAg.add(INT_TBL_PAGRET, "%Retención");
            vecCabPAg.add(INT_TBL_PAGMON, "Monto");
            vecCabPAg.add(INT_TBL_PAGGRA, "Días.Gracia");
            vecCabPAg.add(INT_TBL_PAGCOD, "Cod.Ret");
            vecCabPAg.add(INT_TBL_PAGSOP, "Tx_Sop");
            vecCabPAg.add(INT_TBL_COMSOL, "Compensacion");
            objTblModPag = new ZafTblMod();
            objTblModPag.setHeader(vecCabPAg);

            tblPag.setModel(objTblModPag);
            tblPag.setRowSelectionAllowed(true);
            tblPag.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblPag, INT_TBL_PAGLIN);

            objTblModPag.setColumnDataType(INT_TBL_PAGCRE, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModPag.setColumnDataType(INT_TBL_PAGRET, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModPag.setColumnDataType(INT_TBL_PAGMON, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModPag.setColumnDataType(INT_TBL_PAGGRA, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            tblPag.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux = tblPag.getColumnModel();
            //Configurar JTable: Establecer el ancho de las columnas.
            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_PAGLIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_PAGCRE).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_PAGFEC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_PAGRET).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_PAGMON).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_PAGGRA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_PAGCOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PAGSOP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_COMSOL).setPreferredWidth(60);

            tblPag.getTableHeader().setReorderingAllowed(false);
            tcmAux = null;
            blnRes=true;

        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
            System.err.println("configurarTablaPago.ERROR " + e.toString());
        }
        return blnRes;
    }  
    
    private final int intCanArr[] = new int[1];
    private final int intarreglodia[] = new int[10];
    private final int intarreglonum[] = new int[10];
    private String strarreglosop[] = new String[10];
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    private String strFecSis, strFecSisBase; 
    private double dblRetFueGlo = 0, dblRetIvaGlo = 0;
    private Vector vecDataTblPag;
    private Date datFecDoc;     
    
    private boolean insertarPagFac(java.sql.Connection conn,int intCodEmpFacEle, int intCodLocFacEle, int intCodTipDocFacEle, int intCodDoc) {
        boolean blnRes = false, blnpagoscompensacion = false;
        java.sql.Statement stmLocIns;
        java.util.Calendar objFec;
        int intVal = intCanArr[0];
        int intsizearre = intarreglodia.length;
        int i = 0;
        double dblBaseDePagos = 0, dblRetIva = 0, dblRetFue = 0, dblRetFueFle = 0;
                    BigDecimal bigBaseDePagos=BigDecimal.ZERO;
        double dblPago = 0.00;
        BigDecimal bigPago=BigDecimal.ZERO;

        double dblPagos = 0.00;
        BigDecimal bigPagos=BigDecimal.ZERO;

        double dblRete = 0;
        BigDecimal bigRete=BigDecimal.ZERO;
        String strSql = "", strSqlIns = "";
        String strFec = "";
        String strFecSem = "";

        try {
            if (conn != null) {
                    intVal = intsizearre - (intsizearre - intVal);
                    strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaBaseDatos());
                    dblRetFueGlo = 0.0;
                    dblRetIvaGlo = 0.00;
                    vecDataTblPag = new Vector();

                    strFec = objUti.formatearFecha(strFecSis, "yyyy-MM-dd", "dd/MM/yyyy");

                    datFecDoc = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                    strFecSisBase = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaBaseDatos());
                    java.util.Date dateObj = datFecDoc;
                    Calendar calObj = Calendar.getInstance();
                    calObj.setTime(dateObj);

                    objFec = java.util.Calendar.getInstance();

                    objFec.set(java.util.Calendar.DAY_OF_MONTH, calObj.get(Calendar.DAY_OF_MONTH));
                    objFec.set(java.util.Calendar.MONTH, calObj.get(Calendar.MONTH));
                    objFec.set(java.util.Calendar.YEAR, calObj.get(Calendar.YEAR));


//                    if(dblValorPagar>0){
                        dblBaseDePagos =dblValorPagar ;
//                    }
//                    else{
//                        dblBaseDePagos =dblTotalCot ;
//                    }

                    if (dblBaseDePagos > 0) {
                        Vector vecData = new Vector();

                        Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
                        /**
                         * **********************************************************************************
                         *///

                        calculaSubtotalServiNoServi(conn, intCodEmpFacEle,"T");

                        if (dblSubSerNoSer > 0.00) {
                            cargaForPag(conn,intCodEmpFacEle, intCodMotTran);
                        }

                        /**
                         * ***************************************************************************************************************************
                         */
                        calculaSubtotalServiNoServi(conn, intCodEmpFacEle, "N");

                        if (dblSubSerNoSer > 0.00) {
                            cargaForPag(conn,intCodEmpFacEle, intCodMotBien);
                        }

                        /**
                         * ***************************************************************************************************************************
                         */
                        calculaSubtotalServiNoServi(conn, intCodEmpFacEle, "S");

                        if (dblSubSerNoSer > 0.00) {
                            cargaForPag(conn,intCodEmpFacEle, intCodMotServ);
                        }


                        if(blnIsComSol && dblPorIva==14.00){
                              cargaForPagComSol();
                        }

                        /**
                         * **********************************************************************************
                         */
                        bigRete= new BigDecimal(dblRetFueGlo+dblRetIvaGlo).setScale(2, RoundingMode.HALF_UP);
//                            bigBaseDePagos =new BigDecimal(dblTotalCot).subtract(bigRete).setScale(2, RoundingMode.HALF_UP);     // JoseMario 31/Ene/2017                      
//                        if(dblValorPagar>0){
                            dblBaseDePagos = objUti.redondear((dblValorPagar  - dblRete), objParSis.getDecimalesMostrar());
//                        }
//                        else{
//                             dblBaseDePagos = objUti.redondear((dblTotalCot - dblRete), objParSis.getDecimalesMostrar());
//                        }
                        for (i = 0; i < intVal; i++) {
                            java.util.Calendar objFecPagActual = Calendar.getInstance();
                            objFecPagActual.setTime(objFec.getTime());

                            int diaCre = intarreglodia[i];
                            int numPag = intarreglonum[i];
                            String strSop = ((strarreglosop[i] == null) ? "N" : strarreglosop[i]);

                            if (diaCre != 0) {
                                objFecPagActual.add(java.util.Calendar.DATE, diaCre);
                            }
                            System.out.println("..." + java.util.Calendar.DAY_OF_MONTH);

                            dtePckPag.setAnio(objFecPagActual.get(java.util.Calendar.YEAR));
                            dtePckPag.setMes(objFecPagActual.get(java.util.Calendar.MONTH) + 1);  //AnteS Tony 2Jul2016
                            //dtePckPag.setMes(objFecPagActual.get(java.util.Calendar.MONTH));    //Tony 2Jul2016
                            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));

                            System.out.println("FECHA VENCIMIENTO " + dtePckPag.getText().toString());

                            java.util.Vector vecReg = new java.util.Vector();
                            vecReg.add(INT_TBL_PAGLIN, "");
                            vecReg.add(INT_TBL_PAGCRE, "" + diaCre);
                            vecReg.add(INT_TBL_PAGFEC, dtePckPag.getText());

                            bigPagos=((numPag==0)?BigDecimal.ZERO:(bigBaseDePagos.divide(new BigDecimal(numPag),2,RoundingMode.HALF_UP))).setScale(2, RoundingMode.HALF_UP);
                            bigPago=bigPago.add(bigPagos);

                            vecReg.add(INT_TBL_PAGRET, "");
                            if (i == (intVal - 1)) {
//                                    bigPagos=  bigPagos.add(new BigDecimal(dblTotalCot).subtract(bigPago.add(bigRete))).setScale(2, RoundingMode.HALF_UP);
//                                if(dblValorPagar > 0){
//                                        dblPagos = objUti.redondear(dblPagos + (dblValorPagar   - (dblPago + dblRete)), objParSis.getDecimalesMostrar());
                                    bigPagos=  bigPagos.add(new BigDecimal(dblValorPagar).subtract(bigPago.add(bigRete))).setScale(2, RoundingMode.HALF_UP);
//                                }
//                                else{
////                                        dblPagos = objUti.redondear(dblPagos + (dblTotalCot - (dblPago + dblRete)), objParSis.getDecimalesMostrar());
//                                    bigPagos=  bigPagos.add(new BigDecimal(dblTotalCot).subtract(bigPago.add(bigRete))).setScale(2, RoundingMode.HALF_UP);
//                                }
                            }

                            vecReg.add(INT_TBL_PAGMON, ""+bigPagos);
                            vecReg.add(INT_TBL_PAGGRA, "0");
                            vecReg.add(INT_TBL_PAGCOD, "");
                            vecReg.add(INT_TBL_PAGSOP, strSop);
                            vecReg.add(INT_TBL_COMSOL,"N"); 
                            vecDataTblPag.add(vecReg);
                        }
                        objTblModPag.setData(vecDataTblPag);
                        tblPag.setModel(objTblModPag);

                        vecDataTblPag = null;

                        double dblValRet = 0;
                        String strFecCor = "";
                        for (int x = 0; x < tblPag.getRowCount(); x++) {
                            dblValRet = Double.parseDouble(getIntDatoValidado(tblPag.getValueAt(x, INT_TBL_PAGRET)));
                            System.out.println("INT_TBL_COMSOL==>  "+tblPag.getValueAt(x, INT_TBL_COMSOL).toString() );
                            if (dblValRet == 0.00  && (tblPag.getValueAt(x, INT_TBL_COMSOL).toString().equals("N"))) {
                                strFecCor = tblPag.getValueAt(x, INT_TBL_PAGFEC).toString();
                                break;
                            }
                        }

                        String strF1 = objUti.formatearFecha(strFecSisBase, "yyyy-MM-dd", "yyyy/MM/dd");
                        java.util.Date fac1 = objUti.parseDate(strF1, "yyyy/MM/dd");
                        int intAnioAct = (fac1.getYear() + 1900);

                        //  por alfredo.  año nuevo 31 dic año anterior
                        String strF = objUti.formatearFecha(strFecCor, "dd/MM/yyyy", "yyyy/MM/dd");
                        java.util.Date fac = objUti.parseDate(strF, "yyyy/MM/dd");
                        int intAnioCre = (fac.getYear() + 1900);

                        if (intAnioCre > intAnioAct) {
                            strFecCor = "31/12/" + intAnioAct;
                        }

                        for (int x = 0; x < tblPag.getRowCount(); x++) {
                            dblValRet = Double.parseDouble(getIntDatoValidado(tblPag.getValueAt(x, INT_TBL_PAGRET)));
                            if ((dblValRet > 0.00) && (tblPag.getValueAt(x, INT_TBL_COMSOL).toString().equals("N"))) {
                                tblPag.setValueAt(strFecCor, x, INT_TBL_PAGFEC);
                            }
                        }

                        /**
                         * ********************************************************************************************
                         */
                        //javax.swing.text.JTextComponent txtFecDoc ;

                        for (int x = 0; x < tblPag.getRowCount(); x++) {
                            i = x;

                            int FecPagDoc[] = txtFecDoc.getFecha(tblPag.getValueAt(i, INT_TBL_PAGFEC).toString());///////JOTA AKIIIII!!!!
                            String strFechaPag = "#" + FecPagDoc[2] + "/" + FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
                            String strSop = ((tblPag.getValueAt(i, INT_TBL_PAGSOP) == null) ? "" : tblPag.getValueAt(i, INT_TBL_PAGSOP).toString());
                            String strCodTipRet = getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGCOD));

//                            System.out.println("1 " + tblPag.getValueAt(i, INT_TBL_PAGFEC).toString() );
//                            System.out.println("2 " + txtFecDoc.getFecha(tblPag.getValueAt(i, INT_TBL_PAGFEC).toString()) );
//                            System.out.println("3 " + strFechaPag );

                            strSqlIns += " ; INSERT INTO  tbm_pagMovInv(co_emp, co_loc, co_tipDoc, co_doc, co_reg, " ; //CAMPOS PrimayKey
                            strSqlIns+=" ne_diaCre, fe_ven, mo_pag, ne_diaGra, nd_porRet ,st_regrep , st_sop" ;//<==
                            strSqlIns+=" ,co_tipret,tx_tipReg ) VALUES (";
                            strSqlIns+=intCodEmpFacEle + ", " + intCodLocFacEle + ", " + intCodTipDocFacEle + ", " + intCodDoc + ", " + (x + 1) + ", ";
                            strSqlIns+=getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGCRE)) + ", '" + strFechaPag + "',";
                            if(tblPag.getValueAt(i, INT_TBL_COMSOL).equals("S")){
                                    strSqlIns += (new BigDecimal( getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGMON))).multiply(new BigDecimal(-1)))+", ";
                                    blnpagoscompensacion=true;
                            }
                            else{
                                strSqlIns += (new BigDecimal( getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGMON))).multiply(new BigDecimal(-1)))+", ";
                            }
                            strSqlIns+= getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGGRA)) + ", ";
                            strSqlIns+=getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGRET)) + ", 'I', '" + strSop + "', ";
                            strSqlIns+=(strCodTipRet.equals("0") ? null : strCodTipRet) + ", ";
                            if(blnpagoscompensacion){
                                strSqlIns += "'S')";
                                blnpagoscompensacion=false;
                            }
                            else{
                                strSqlIns += " null)";
                            }
                        }
                        blnRes = true;
                    }
                System.out.println("PAGOS!!  " + strSqlIns);
                stmLocIns = conn.createStatement();
                stmLocIns.executeUpdate(strSqlIns);
                stmLocIns.close();
                stmLocIns = null;

            }
        } catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            System.err.println("ERROR " + Evt.toString());
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            System.err.println("ERROR " + Evt.toString());
        }
        return blnRes;
    }
    
    
    private boolean blnIsComSol=false;
    private javax.swing.JTable tblDat;
    private double dblSubSerNoSer = 0, dblIvaSerNoSer = 0;
    private String stIvaVen = "S";
    
    private void calculaSubtotalServiNoServi(java.sql.Connection conn,int CodEmp, String strServi) {
        double dblCan, dblDes, dblTotal = 0.00, dblPre = 0.00, dblValDes = 0.00;
        double dblSub = 0, dblIva = 0, dblTmp = 0;
        int intFilSel = 0;
        dblSubSerNoSer = 0;
        dblIvaSerNoSer = 0;
        try {
           if (conn != null) {
               System.out.println(">>> calculaSubtotalServiNoServi.. tblDat cuenta " + tblDat.getRowCount());
                for (intFilSel = 0; intFilSel < tblDat.getRowCount(); intFilSel++) {
                    if (tblDat.getValueAt(intFilSel, INT_TBL_CODITM) != null) {
                        if (getEstItm(conn, CodEmp, Integer.parseInt(tblDat.getValueAt(intFilSel, INT_TBL_CODITM).toString()), strServi)) {
                            dblCan = Double.parseDouble(getIntDatoValidado(tblDat.getValueAt(intFilSel, INT_TBL_CANMOV)));
                            dblPre = Double.parseDouble(getIntDatoValidado(tblDat.getValueAt(intFilSel, INT_TBL_PREUNI)));
                            dblDes = objUti.redondear(Double.parseDouble(getIntDatoValidado(tblDat.getValueAt(intFilSel, INT_TBL_PORDES))), 2);
                            dblValDes = ((dblCan * dblPre) == 0) ? 0 : ((dblCan * dblPre) * (dblDes / 100));
                            dblTotal = (dblCan * dblPre) - dblValDes;
                            dblTotal = objUti.redondear(dblTotal, 2);
                            dblSub += dblTotal;
                            if (getStringDatoValidado(tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA)).equals("true")) {
                                dblTmp = dblTotal;
                                dblIva = dblIva + (((dblTmp * dblPorIva) == 0) ? 0 : (dblTmp * dblPorIva) / 100);
                            } else {
                                dblIva = dblIva + 0;
                            }
                        }
                    }
                }

                if (stIvaVen.equals("N")) {
                    dblIvaSerNoSer = 0.00;
                } else {
                    dblIvaSerNoSer = objUti.redondear(dblIva, objParSis.getDecimalesMostrar());
                }

                dblSubSerNoSer = objUti.redondear(dblSub, objParSis.getDecimalesMostrar());
            }
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(jifCnt, e);
            System.err.println("ERROR " + e.toString());
        }
    }
    
    //Códigos de Motivos del documento para las retenciones
    private int intCodMotBien = 0;                                                //Bien
    private int intCodMotServ = 0;                                                //Servicio
    private int intCodMotTran = 0;                                                //Transporte
    
    private boolean FormaRetencion(java.sql.Connection conn, int intCodEmp) {
        boolean blnRes = false;
        java.sql.Statement stmTmp;
        java.sql.ResultSet rstTmp;
        try {
            if (conn != null) {
                stmTmp = conn.createStatement();
                String sql = "SELECT tx_tipmot, co_mot FROM tbm_motdoc WHERE co_emp=" + intCodEmp+ " AND tx_tipmot in ('B','S','T') ";
                rstTmp = stmTmp.executeQuery(sql);
                while (rstTmp.next()) {
                    if (rstTmp.getString("tx_tipmot").equals("B")) {
                        intCodMotBien = rstTmp.getInt("co_mot");
                    } else if (rstTmp.getString("tx_tipmot").equals("S")) {
                        intCodMotServ = rstTmp.getInt("co_mot");
                    } else if (rstTmp.getString("tx_tipmot").equals("T")) {
                        intCodMotTran = rstTmp.getInt("co_mot");
                    }
                    blnRes = true;
                }
                rstTmp.close();
                stmTmp.close();
                rstTmp = null;
                stmTmp = null;
            }
        } catch (SQLException Evt) {
            System.err.println("ERROR " + Evt.toString());
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        } catch (Exception Evt) {
            System.err.println("ERROR " + Evt.toString());
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        return blnRes;
    }
    
    private String strNomForPag;
        private int intCodTarCre;
        private int intCodTipPerEmp=-1,intTipForPag2=-1, intCodForPag = -1;
        private String strCodTipPerCli;
        private double dblPorComSol=0.00,dblBaseIva=0.00, dblBaseCero=0.00, dblComSol=0.00, dblValorPagar=0.00;
        
        private boolean obtenerDatosFactura(java.sql.Connection conn,int intCodEmp,int intCodLoc,int intCodTipDoc, int intCodCot ){
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strCadena = "";
            boolean blnRes=false;
            try{
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    strCadena= " SELECT CotCab.co_emp,CotCab.co_loc,cli.co_cli, cli.tx_nom as nomcli, cli.tx_dir as dircli, \n"; //<== Campos con los datos del CLiente para la cabecera
                    strCadena+="        CotCab.co_doc, CotCab.fe_doc, CotCab.tx_ate,  \n"; //<==Campos que aparecen en la parte superior del 1er Tab
                    strCadena+="        CotCab.co_forPag,CotCab.co_tipCre, \n"; //<==Campos que aparecen en la parte superior del 2do Tab // TC
                    strCadena+="        CotCab.tx_obs1, CotCab.tx_obs2, CotCab.nd_sub,CotCab.nd_tot,CotCab.nd_valIva, CotCab.nd_porIva, \n"; //<==Campos que aparecen en la parte Inferior del 1er Tab
                    strCadena+="        CotCab.nd_valComSol,CotCab.nd_subIvaGra ,CotCab.st_reg , forpag.tx_des \n"; // NOMBRE FORMA DE PAGO
                    strCadena+="        , CotCab.st_regrep  ,Cli.tx_tipper, Cli.nd_maxdes, Cli.nd_maruti, Cli.tx_tel, Cli.tx_ide , ciu.tx_desLar  \n";
                    strCadena+="        ,Cli.co_tipper , CotCab.tx_numped ,forpag.ne_tipforpag , forpag.nd_pes , forpagCli.ne_tipforpag as ne_tipforpagcli , forpagCli.nd_pes as nd_pescli   \n";
                    strCadena+="        ,Cli.ne_Diagra, Cli.st_ivaven, CotCab.tx_nomcli, cli.st_peringnomclicotven  \n";
                    strCadena+=" FROM tbm_cabMovInv as CotCab   \n";
                    strCadena+=" LEFT OUTER JOIN  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_cli = cli.co_cli) \n";
                    strCadena+=" LEFT OUTER JOIN tbm_cabForPag as forpagCli on (forpagCli.co_emp=Cli.co_emp and forpagCli.co_forpag = Cli.co_forPag )  \n";
                    strCadena+=" LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=Cli.co_ciu)  \n";
                    strCadena+=" left outer join tbm_cabForPag as forpag on (forpag.co_emp=CotCab.co_emp and forpag.co_forpag = CotCab.co_forPag )  \n";
                    strCadena+=" Where CotCab.co_emp = " +intCodEmp+" \n";
                    strCadena+="       and CotCab.co_loc = "+intCodLoc+"  and  \n";
                    strCadena+="    CotCab.co_doc= " +intCodCot+ "  \n AND CotCab.co_tipDoc="+intCodTipDoc+"  ORDER BY CotCab.co_doc";
//                    System.out.println("obtenerDatosFactura... \n\n " + strCadena);
                    rstLoc = stmLoc.executeQuery(strCadena);
                    if (rstLoc.next()) {
                        
                        strCodTipPerCli = rstLoc.getString("co_tipper");
                        intTipForPag2 = Integer.parseInt(rstLoc.getString("ne_tipforpag"));
                        strNomForPag=rstLoc.getString("tx_des");
                        intCodForPag = rstLoc.getInt("co_forPag");
                        dblTotalCot = rstLoc.getDouble("nd_tot");
                        dblIvaCot = rstLoc.getDouble("nd_valIva");
                        dblSubtotalCot = rstLoc.getDouble("nd_sub");
                        dblComSol = rstLoc.getDouble("nd_valComSol");
                        dblBaseIva = rstLoc.getDouble("nd_subIvaGra");
                         
                        
                        dblValorPagar  = (dblTotalCot*-1) - dblComSol;
                        
                        if(intCodEmp==2 && intCodLoc==4){
                            blnIsComSol = true;//MANTA!!!!
                        }
                        
                        
                    }
                    if(intTipForPag2==4){
                        intCodTarCre=rstLoc.getInt("co_tipCre");
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            }
            catch (java.sql.SQLException ex) {
                objUti.mostrarMsgErr_F1(jifCnt, ex);
                System.err.println("ERROR " + ex.toString());
                blnRes=false;
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(jifCnt, e);
                System.err.println("ERROR " + e.toString());
                blnRes=false;
            }
            return blnRes;
        }
        
    private void cargaForPag(java.sql.Connection conn,int CodEmp, int intCodMot) {
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL = "";
        double dblRetFue = 0, dblRetIva = 0;
        try {
            if (intTipForPag2 == 4) {
                return;
            }
            if (conn != null) {
               stm = conn.createStatement();
                strSQL = " select tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta \n";
                strSQL += " from tbm_polret as polret \n "
                        + "left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) \n  "
                        + "left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret) \n";
                strSQL += " where polret.co_emp = " + CodEmp + " and co_mot = " + intCodMot + " and "
                        + "co_sujret = " + intCodTipPerEmp + " and co_ageret  = " + strCodTipPerCli + " "
                        + " AND polret.st_reg='A' AND  \n "
                        + "'" + strFecSisBase + "'  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' "
                        + " else polret.fe_vighas end ";
//                System.out.println("cargaForPag " + strSQL );
                rst = stm.executeQuery(strSQL);
                while (rst.next()) {

                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_PAGLIN, "");
                    vecReg.add(INT_TBL_PAGCRE, "");
                    System.out.println("cargaForPag 1820 " + strFecSisBase);
                    vecReg.add(INT_TBL_PAGFEC, strFecSisBase);
                    if (rst.getString("tx_aplret").equals("S")) {

                        dblRetFue = objUti.redondeo((dblSubSerNoSer * (rst.getDouble("nd_porret") / 100)), 2);
                        dblRetFueGlo += dblRetFue;
                        vecReg.add(INT_TBL_PAGRET, rst.getString("nd_porret"));
                        vecReg.add(INT_TBL_PAGMON, "" + dblRetFue);
                        vecReg.add(INT_TBL_PAGGRA, "");
                    }
                    else if (rst.getString("tx_aplret").equals("I")) {
                        if (dblIvaSerNoSer > 0) {
//                            dblRetIva = objUti.redondeo((dblIvaSerNoSer * (rst.getDouble("nd_porret") / 100)), 2);
                            if(blnIsComSol){
                                dblRetIva = objUti.redondeo(((dblIvaSerNoSer - dblComSol) * (rst.getDouble("nd_porret") / 100)), 2); 
                            }else{
                                dblRetIva = objUti.redondeo((dblIvaSerNoSer * (rst.getDouble("nd_porret") / 100)), 2);
                            }
                            dblRetIvaGlo += dblRetIva;
                            vecReg.add(INT_TBL_PAGRET, rst.getString("nd_porret"));
                            vecReg.add(INT_TBL_PAGMON, "" + dblRetIva);
                            vecReg.add(INT_TBL_PAGGRA, "");
                        }
                    }
                    

                    vecReg.add(INT_TBL_PAGCOD, "" + rst.getString("co_tipret"));
                    vecReg.add(INT_TBL_PAGSOP, "N");
                    vecReg.add(INT_TBL_COMSOL,"N");

                    vecDataTblPag.add(vecReg);

                }
                stm.close();
                stm=null;
                rst.close();
                rst = null;

            }
        } catch (SQLException Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            System.err.println("ERROR " + Evt.toString());
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            System.err.println("ERROR " + Evt.toString());
        }
    }
        
    
    
    
    private void cargaForPagComSol() {
        double dblRetFue = 0;
        try {
            Date datFecDocComSol;
            
            datFecDocComSol = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
             
            java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL_PAGLIN, "");
            vecReg.add(INT_TBL_PAGCRE, "");
            vecReg.add(INT_TBL_PAGFEC, objUti.formatearFecha(datFecDocComSol, "dd/MM/yyyy"));
            dblRetFue = objUti.redondear(((dblBaseIva*-1) * (dblPorComSol) / 100), 2);  // SOLO AKI ES NEGATIVO JOTA 16/Feb/2017
            vecReg.add(INT_TBL_PAGRET, "");
            vecReg.add(INT_TBL_PAGMON, "" + dblRetFue);
            vecReg.add(INT_TBL_PAGGRA, "");
            vecReg.add(INT_TBL_PAGCOD, "");
            vecReg.add(INT_TBL_PAGSOP, "N");
            vecReg.add(INT_TBL_COMSOL, "S");  // <<< === SI ES COMPENSACION
            vecDataTblPag.add(vecReg);
        }  
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
    }
     
    
    
    
    /**
     * Función que validad si es nulo asignas "0" caso contrario devuelve el valor que tiene.
     * @param objTbl Objeto de la celda 
     * @return "0" si el nulo ó  vacion "" el valor que tiene
     */
    private String getIntDatoValidado( Object objTbl ){
        String strVar="0";
        try{
            if(objTbl==null) strVar="0";
            else if(objTbl.toString().equals("")) strVar="0";
            else strVar=objTbl.toString();
         }catch(Exception e){ 
             objUti.mostrarMsgErr_F1(jifCnt, e); 
         }
        return strVar;   
    }
    

    private boolean getEstItm(java.sql.Connection conn, int intCodEmp, int intCodItm, String strEstItm ){
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

     }}catch(java.sql.SQLException e) { blnRes=false;  objUti.mostrarMsgErr_F1(jifCnt, e);  }
      catch(Exception  e){ blnRes=false; objUti.mostrarMsgErr_F1(jifCnt, e);  }
      return blnRes;
    }


    /**
     * Función que validad si es nulo asignas "" caso contrario devuelve el valor que tiene.
     * @param objTbl Objeto de la celda 
     * @return "" si el nulo ó el valor que tiene
     */
    private String getStringDatoValidado( Object objTbl ){
     String strVar="";
     try{
        if(objTbl==null) strVar="";
        else if(objTbl.toString().equals("")) strVar="";
        else strVar=objTbl.toString();
      }catch(Exception e){ objUti.mostrarMsgErr_F1(jifCnt, e);}
     return strVar;   
    }
    
    
    /* ==============================  */
        
        
    
    
    private boolean cargarDetPag(java.sql.Connection conn, int  CodEmp, int  CodLoc, int  CodCot) {
        boolean blnRes = false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try {

            stmLoc = conn.createStatement();
            Vector vecData = new Vector();
            strSql = "SELECT ne_diacre, fe_ven, nd_porret, mo_pag, ne_diagra , co_tipret  FROM tbm_pagCotven "
                    + " WHERE co_emp=" + CodEmp + " AND co_loc=" + CodLoc + " AND co_cot=" + CodCot + " ORDER BY co_reg";
 
            rstLoc = stmLoc.executeQuery(strSql);

            while (rstLoc.next()) {
                java.util.Vector vecReg = new java.util.Vector();
                vecReg.add(INT_TBL_PAGLIN, "");
                vecReg.add(INT_TBL_PAGCRE, rstLoc.getString("ne_diacre"));
                vecReg.add(INT_TBL_PAGFEC, rstLoc.getString("fe_ven"));
                vecReg.add(INT_TBL_PAGRET, objUti.redondear(rstLoc.getString("nd_porret"), 2));
                vecReg.add(INT_TBL_PAGMON, objUti.redondear(rstLoc.getString("mo_pag"), 2));
                vecReg.add(INT_TBL_PAGGRA, rstLoc.getString("ne_diagra"));
                vecReg.add(INT_TBL_PAGCOD, rstLoc.getString("co_tipret"));
                vecReg.add(INT_TBL_PAGSOP, "");
                vecReg.add(INT_TBL_COMSOL,"N");
                vecData.add(vecReg);
            }
            objTblModPag.setData(vecData);
            tblPag.setModel(objTblModPag);
            vecData = null;
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
            blnRes = true;
        } catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
                System.err.println("ERROR " + Evt.toString());
        } catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, evt);
            System.err.println("ERROR " + evt.toString());
        }
        return blnRes;
    }

    
    
    
    boolean blnEstBufTbl = false;
    
     
        /* ==============================  */
        
        
    public void refrescaDatos2(java.sql.Connection conn, int CodEmp, int CodLoc, int CodTipDoc, int CodDoc, int CodCot) {
        try {
          String sSQL;

            int intNumCot = 0;
            if (conn != null) {
                 
                String strAux = ",CASE WHEN ("
                        + " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN ("
                        + " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp=" + CodEmp+" AND co_loc="+CodLoc+" "
                        + " AND co_tipdoc=" + CodTipDoc + " AND st_reg='A' "
                        + " ))) THEN 'S' ELSE 'N' END  as isterL";

                String strAux2 = " , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( "
                        + " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a "
                        + " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) "
                        + " WHERE a.co_emp="+CodEmp+ " and a.co_loc="+CodLoc+" and a1.st_reg='A' and  a.st_reg='P' ))) "
                        + " THEN 'S' ELSE 'N' END AS proconf  ";

                sSQL = "SELECT var.tx_tipunimed, detcot.tx_nomitm, detcot.co_bod ,detcot.nd_can ,detcot.nd_preuni ,detcot.nd_pordes ,detcot.st_ivaven "
                        + ",detcot.nd_can ,detcot.nd_preuni ,detcot.nd_pordes ,detcot.co_itm ,detcot.nd_precom ,detcot.co_prv ,detcot.tx_codalt2 "
                        + ",detcot.tx_codalt , detcot.nd_pordesprecom, detcot.st_traauttot,   var.tx_descor , inv.st_permodnomitmven, inv.st_ser , cli.tx_nom";

                sSQL += strAux;
                sSQL += strAux2;
                sSQL += " , ROUND(detCot.nd_can*inv.nd_pesItmKgr,2) as nd_pesTot, inv.nd_pesItmKgr, CASE WHEN inv.tx_codAlt2 IS NULL THEN '' ELSE inv.tx_codAlt2 END as tx_codLet ";  /* JoséMario 5/Ene/2016 */
                sSQL += " FROM tbm_detcotven as detcot "
                        + " LEFT outer join tbm_inv as inv on (detcot.co_emp = inv.co_emp and detcot.co_itm = inv.co_itm) "
                        + " LEFT outer join tbm_var as var on (inv.co_uni = var.co_reg)"
                        + " LEFT JOIN tbm_cli as cli on(cli.co_emp=detcot.co_emp and cli.co_cli=detcot.co_prv)"
                        + " WHERE detcot.co_emp="+CodEmp+" and detcot.co_loc="+CodLoc+" "
                        + " and detcot.co_cot="+CodCot+" order by detcot.co_reg";

                System.out.println("refrescaDatos2 \n" + sSQL);
                java.sql.Statement stmCab = conn.createStatement();
                java.sql.ResultSet rst = stmCab.executeQuery(sSQL);
                double dblCan = 0, dblPre = 0, dblPorDes = 0, dblValDes = 0, dblTotal = 0;

                java.sql.Statement stmAux;
                java.sql.ResultSet rstAux;
                stmAux = conn.createStatement();
                String strUnidad = "", strCodAlt = "", strSer = "", strTer = "";
                Vector vecData = new Vector();
                int inttratot = 0;
                for (int i = 0; rst.next(); i++) {
                    java.util.Vector vecReg = new java.util.Vector();
                    strCodAlt = (rst.getString("tx_codalt") == null ? "" : rst.getString("tx_codalt"));
                    strUnidad = (rst.getString("tx_descor") == null ? "" : rst.getString("tx_descor"));
                    strSer = (rst.getString("st_ser") == null ? "" : rst.getString("st_ser"));
                    strTer = (rst.getString("isterl") == null ? "" : rst.getString("isterl"));
                    if (inttratot == 0) {
                        if (rst.getString("st_traauttot").equals("S")) {
                            inttratot = 1;
                        }
                    }
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_ITMALT, strCodAlt);
                    vecReg.add(INT_TBL_BUTITM, "");
                    vecReg.add(INT_TBL_BUTSTK, "");
                    vecReg.add(INT_TBL_BUTSOL, "");
                    vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_UNIDAD, strUnidad);
                    vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_BUTBOD, "");
                    vecReg.add(INT_TBL_TRATOT, (rst.getString("st_traauttot").equals("S")) ? true : false);
                    vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_can")));
                    vecReg.add(INT_TBL_PREUNI, new Double(rst.getDouble("nd_preuni")));
                    vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                    String strIva = rst.getString("st_ivaven");
                    Boolean blnIva;
                    if (stIvaVen.equals("N")) {
                        blnIva = false;
                    } else {
                        blnIva = (strIva.equals("S")) ? true : false;
                    }
                    vecReg.add(INT_TBL_BLNIVA, blnIva);
                    dblCan = rst.getDouble("nd_can");
                    dblPre = rst.getDouble("nd_preuni");
                    dblPorDes = rst.getDouble("nd_pordes");
                    dblValDes = ((dblCan * dblPre) == 0) ? 0 : ((dblCan * dblPre) * (dblPorDes / 100));
                    dblTotal = (dblCan * dblPre) - dblValDes;
                    //dblTotal = objUti.redondear(dblTotal, 3);
                    dblTotal = objUti.redondear(dblTotal, 2);
                    vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
                    vecReg.add(INT_TBL_PESTOT, rst.getString("nd_pesTot"));/* José Marín - 20/Oct/2015 */
                    vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_ESTADO, "E");
                    vecReg.add(INT_TBL_IVATXT, strIva);
                    vecReg.add(INT_TBL_PRE_COS, new Double(rst.getDouble("nd_precom")));            //Columna que contiene  precio de compra
                    vecReg.add(INT_TBL_DESPRECOM, new Double(rst.getDouble("nd_pordesprecom")));
                    vecReg.add(INT_TBL_COD_PRO, rst.getString("co_prv"));            //Columna que contiene el codigo del proveedor

                    strCodAlt = (rst.getString("tx_nom") == null ? "" : rst.getString("tx_nom"));

                    vecReg.add(INT_TBL_NOM_PRO, strCodAlt);            //Columna que contiene el Nombre del proveedor
                    vecReg.add(INT_TBL_BUT_PRO, "");               //Columna que contiene para busqueda del proveedor
                    vecReg.add(INT_TBL_BLNPRE, "");
                    vecReg.add(INT_TBL_ITMALT2, rst.getString("tx_codalt2"));
                    vecReg.add(INT_TBL_ITMSER, strSer);
                    vecReg.add(INT_TBL_ITMTER, strTer);

                    vecReg.add(INT_TBL_CODBODPRV, "");
                    vecReg.add(INT_TBL_NOMBODPRV, "");
                    vecReg.add(INT_TBL_BUTBODPRV, "");

                    vecReg.add(INT_TBL_MARUTI, "");
                    vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf"));
                    vecReg.add(INT_TBL_MODNOMITM, rst.getString("st_permodnomitmven"));
                    vecReg.add(INT_TBL_COLOCREL, "");
                    vecReg.add(INT_TBL_COTIPDOCREL, "");
                    vecReg.add(INT_TBL_CODOCREL, "");
                    vecReg.add(INT_TBL_COREGREL, "");
                    vecReg.add(INT_TBL_COLOCRELSOL, "");
                    vecReg.add(INT_TBL_COTIPDOCRELSOL, "");
                    vecReg.add(INT_TBL_CODOCRELSOL, "");
                    vecReg.add(INT_TBL_COREGRELSOL, "");
                    vecReg.add(INT_TBL_COLOCRELOC, null);
                    vecReg.add(INT_TBL_COTIPDOCRELOC, null);
                    vecReg.add(INT_TBL_CODOCRELOC, null);
                    vecReg.add(INT_TBL_COREGRELOC, null);
                    vecReg.add(INT_TBL_PRELISITM, null);

                    vecReg.add(INT_TBL_PRELISITM2, null);
                    vecReg.add(INT_TBL_CANORI, null);
                    vecReg.add(INT_TBL_PREORI, null);
                    vecReg.add(INT_TBL_DESORI, null);
                    vecReg.add(INT_TBL_DESVENMAX, null);
                    vecReg.add(INT_TBL_NUMFILCOMPVEN, null);
                    vecReg.add(INT_TBL_MAXDESCOM, null);
                    vecReg.add(INT_TBL_DATBODCOM, null);
                    vecReg.add(INT_TBL_CODREGCOT, null);

                    vecReg.add(INT_TBL_CANVENRES, new Double(rst.getDouble("nd_can")));
                    vecReg.add(INT_TBL_PREVTARES, new Double(rst.getDouble("nd_preuni")));
                    vecReg.add(INT_TBL_PORDESRES, new Double(rst.getDouble("nd_pordes")));
                    vecReg.add(INT_TBL_CODITMRES, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_TIPUNIMED, rst.getString("tx_tipunimed"));

                    vecReg.add(INT_TBL_BLOPREVTA, null);
                    vecReg.add(INT_TBL_DESITMORI, null);

                    vecReg.add(INT_TBL_CLIRETBOD, null);
                    vecReg.add(INT_TBL_CANCLIRETBOD, null);

                    vecReg.add(INT_TBL_CODCTAEGR, null);
                    vecReg.add(INT_TBL_PESITM, "nd_pesItmKgr");
                    vecData.add(vecReg);
                }
 
                objTblMod.setData(vecData);
                tblDat.setModel(objTblMod);
                
                rst.close();
                
                //calculaTot();
                
                /*
                 * CARGANDO DATOS DEL TAB FORMA DE PAGO
                 */

                

                cargarDetPag(conn, CodEmp, CodLoc, CodCot);

  
                stmAux.close();
                stmCab.close();

            }
            objTblMod.setDataModelChanged(false);

        } catch (SQLException Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            System.err.println("ERROR " + Evt.toString());
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            System.err.println("ERROR " + Evt.toString());
        }

    }
    
}
