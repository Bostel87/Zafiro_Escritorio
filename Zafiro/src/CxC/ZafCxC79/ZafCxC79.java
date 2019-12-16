/* >> Archivo: ZafCxC79.java
 * >> Descripcion: Codigo para el modulo de Tarjetas de crédito.
 * >> Autor: Omar Gutierrez.
 * Created on NOV 15, 2012, 08:30:18 AM
 =====================================================================*/
package CxC.ZafCxC79;
import GenOD.ZafGenOdPryTra;
import Librerias.ZafAjuCenAut.ZafAjuCenAut;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafCnfDoc.ZafCnfDoc;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import ZafReglas.ZafMetImp;
import java.sql.Connection;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;
import java.math.BigDecimal;

// Clase Principal
public class ZafCxC79 extends javax.swing.JInternalFrame
{
    //Constantes: Jtable 
    private static final int INT_TBL_LINEA = 0;                                 // NUMERO DE LINEAS
    private static final int INT_TBL_BUTCLI = 1;                                // BUTON PARA BUSCAR DOCUMENTO
    private static final int INT_TBL_CHKSEL = 2;                                // SELECCION  DE FILA
    private static final int INT_TBL_CODCLI = 3;                                // CODIGO CLIENTE
    private static final int INT_TBL_NOMCLI = 4;                                // NOMBRE CLIENTE
    private static final int INT_TBL_CODEMP = 5;                                // CODIGO EMPRESA
    private static final int INT_TBL_CODLOC = 6;                                // CODIGO DEL LOCAL
    private static final int INT_TBL_CODTID = 7;                                // CODIGO TIPO DE DOCUMENTO
    private static final int INT_TBL_CODDOC = 8;                                // CODIGO DOCUMENTAL
    private static final int INT_TBL_CODREG = 9;                                // CODIGO REGISTRO
    private static final int INT_TBL_DCTIPDOC = 10;                             // DESCRIPCION CORTA TIPO DOCUMENTO
    private static final int INT_TBL_DLTIPDOC = 11;                             // DESCRIPCION LARGA TIPO DOCUMENTO
    private static final int INT_TBL_NUMDOC = 12;                               // NUMERO DOCUMENTO
    private static final int INT_TBL_FECDOC = 13;                               // FECHA DOCUMENTO
    private static final int INT_TBL_DIACRE = 14;                               // DIA DE CREDITO
    private static final int INT_TBL_FECVEN = 15;                               // FECHA VENCIMIENTO DOCUMENTO
    private static final int INT_TBL_PORRET = 16;                               // PORCENTAJE DE RETENCION
    private static final int INT_TBL_VALDOC = 17;                               // VALOR DOCUMENTO
    private static final int INT_TBL_VALPEN = 18;                               // VALOR PENDIENTE
    private static final int INT_TBL_NUMTRA = 19;                               // INGRESO NUMERO DE TRANSACCION.
    private static final int INT_TBL_NUMAUT = 20;                               // INGRESO DE NUMERO DE AUTORIZACION.
    private static final int INT_TBL_ABONO = 21;                                // ABONO
    private static final int INT_TBL_CODREGEFE = 22;                            // CODIGO REGISTRO DE PAGO
    private static final int INT_TBL_ABONOORI = 23;                             // ABONO ORIGEN
    private static final int INT_TBL_BUTFAC = 24;                               // MUESTRA EL DOCUMENTO FACTURA.
    private static final int INT_TBL_TIPCRE = 25;                               // MUESTRA EL TIPO DE CREDITO.
    
    //Constantes del ArrayList Elementos Eliminados
    final int INT_ARR_CODREG = 0;
    
    //Constante IVA
    private double dblPorIva=0.00;                                              //Porcentaje del IVA. Ej.:12.00
    private double dblFacIva=0.00;                                              //Factor Iva.
    
    // Declaración Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafDatePicker txtFecDoc;
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoFacRet;
    private ZafTblCelEdiButGen objTblCelEdiButGen;                              //Editor: JButton en celda.
    private ZafAsiDia objAsiDia, objAsiDia2;                                    //Asiento de Diario
    private ZafAjuCenAut objAjuCenAut;
    private ZafTblPopMnu objTblPopMnu;
    private java.util.Date datFecAux;
    private ZafThreadGUI objThrGUI;
    private ZafRptSis objRptSis;
    private ZafCxC79_02 objLiberaGuia;
    private Connection conCab;
    private ZafVenCon vcoTipDoc;
    private ZafVenCon vcoFac;
    java.sql.Connection CONN_GLO = null;
    java.sql.Statement STM_GLO = null;
    java.sql.ResultSet rstCab = null;
    ZafCxC79_03 objZafCxC79_03;
    mitoolbar objTooBar;
    java.util.Vector vecDetDiario;
    Vector vecCab = new Vector();                                              //Almacena las cabeceras
    Vector vecOD=new Vector();
    
    int intPuertoImpGuia = 0;
    int INT_ENV_REC_IMP_GUIA = 0;
    int intCodMnuDocIng = 0;
    int intTipModDoc = 0;
    double dblMinAjuCenAut = 0;
    double dblMaxAjuCenAut = 0;
    javax.swing.JTextField txtCodTipDoc = new javax.swing.JTextField();
   
    boolean blnHayCam = false;
    boolean blnEstCar = false;
        
    StringBuffer strSqlInsDet;
    StringBuffer stbFacSel;
    
    String strCodEmp = "";
    String strCodLoc = "";
    String strCodTip = "";
    String strCodDoc = "";
    String strCodTipDoc = "";
    String strDesCodTipDoc = "";
    String strDesLarTipDoc = "";
    String strCodTipDocCon = "";
    String strDesCorTipDocCon = "";
    String strDesLarTipDocCon = "";
    String strSqlTipDocAux = "";
    String strCodCtaDeb = "";
    String strTxtCodCtaDeb = "";
    String strNomCtaDeb = "";
    String strCodCtaHab = "";
    String strTxtCodCtaHab = "";
    String strNomCtaHab = "";
    String strCodCtaCli = "";
    String strTxtCodCtaCli = "";
    String strNomCtaCli = "";
    String strCodCtaEfe = "";
    String strTxtCodCtaEfe = "";
    String strNomCtaEfe = "";
    String strIpImpGuia = "";

    String strFormatoFecha = "d/m/y";
    String strVersion = " v1.15 ";

    //Constructor
    public ZafCxC79(Librerias.ZafParSis.ZafParSis obj)
    {
        try 
        {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            this.setTitle(objParSis.getNombreMenu() + " " + strVersion);
            lblTit.setText(objParSis.getNombreMenu()); 

            objUti = new ZafUtil();
            objTooBar = new mitoolbar(this);
            this.getContentPane().add(objTooBar, "South");

        
            
            objLiberaGuia = new ZafCxC79_02(this, objParSis);
            objTooBar.agregarSeparador();

            objRptSis = new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objAjuCenAut = new Librerias.ZafAjuCenAut.ZafAjuCenAut(this, objParSis);

            objAsiDia = new Librerias.ZafAsiDia.ZafAsiDia(this.objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals("")) {
                        objAsiDia.setCodigoTipoDocumento(-1);
                    } else {
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                    }
                }
            });

            objAsiDia2 = new Librerias.ZafAsiDia.ZafAsiDia(this.objParSis);
            objAsiDia2.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                    {
                        objAsiDia2.setCodigoTipoDocumento(-1);
                    }
                    else
                    {
                        //Eddye: Parche temporal hasta que se mejore el código.
                        int intCodTipDocCXCTC=0;
                        if (txtDesCodTitpDoc.getText().equals("COBTCM"))
                        {
                            intCodTipDocCXCTC=196;
                        }
                        else if (txtDesCodTitpDoc.getText().equals("COBTCD"))
                        {
                            intCodTipDocCXCTC=217;
                        }
                        objAsiDia2.setCodigoTipoDocumento(intCodTipDocCXCTC);
                    }
                }
            });

            cargarIpPuertoGuiaEmp();

            panAsiDia.add(objAsiDia, java.awt.BorderLayout.CENTER);
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            panGenCab.add(txtFecDoc);
            txtFecDoc.setBounds(564, 4, 100, 20);
            
            //Iva 
            objParSis.cargarConfiguracionImpuestos();
            BigDecimal bdgIva=objParSis.getPorcentajeIvaVentas();
            dblPorIva= bdgIva.doubleValue();
            dblFacIva=objUti.redondear(((dblPorIva/100)+1.00), objParSis.getDecimalesMostrar());  //Ej.:1.12
        } 
        catch (CloneNotSupportedException e) {      objUti.mostrarMsgErr_F1(this, e);     }  
    }

    //Constructor2 - Ventana Consulta Cobro TC desde otros programas.
    public ZafCxC79(Librerias.ZafParSis.ZafParSis obj, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodMnu) 
    {
        try 
        {
            //System.out.println("ZafCxC79.ZafCxC79 (2): ");
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            this.setTitle(objParSis.getNombreMenu() + " " + strVersion);
            lblTit.setText(objParSis.getNombreMenu());

            objParSis.setCodigoMenu(intCodMnu);

            objUti = new ZafUtil();
            objTooBar = new mitoolbar(this);
            //this.getContentPane().add(objTooBar, "South");
            
            objRptSis = new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objAjuCenAut = new Librerias.ZafAjuCenAut.ZafAjuCenAut(this, objParSis);

            strCodEmp = String.valueOf(intCodEmp);
            strCodLoc = String.valueOf(intCodLoc);
            strCodTip = String.valueOf(intCodTipDoc);
            strCodDoc = String.valueOf(intCodDoc);
            blnEstCar = true;

            objAsiDia = new Librerias.ZafAsiDia.ZafAsiDia(this.objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals("")) {
                        objAsiDia.setCodigoTipoDocumento(-1);
                    } else {
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                    }
                }
            });

            objAsiDia2 = new Librerias.ZafAsiDia.ZafAsiDia(this.objParSis);
            objAsiDia2.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                    {
                        objAsiDia2.setCodigoTipoDocumento(-1);
                    }
                    else
                    {
                        //Eddye: Parche temporal hasta que se mejore el código.
                        int intCodTipDocCXCTC=0;
                        if (txtDesCodTitpDoc.getText().equals("COBTCM"))
                        {
                            intCodTipDocCXCTC=196;
                        }
                        else if (txtDesCodTitpDoc.getText().equals("COBTCD"))
                        {
                            intCodTipDocCXCTC=217;
                        }
                        objAsiDia2.setCodigoTipoDocumento(intCodTipDocCXCTC);
                    }
                }
            });

            panAsiDia.add(objAsiDia, java.awt.BorderLayout.CENTER);

            cargarIpPuertoGuiaEmp();

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            panGenCab.add(txtFecDoc);
            txtFecDoc.setBounds(580, 4, 92, 20);

//            txtCodTipDoc.setText(strCodTip);
//            txtCodDoc.setText(strCodDoc);
//
//            ConfigurarTabla();
//            objTooBar.consultar();

            //Iva
            objParSis.cargarConfiguracionImpuestos();
            BigDecimal bdgIva=objParSis.getPorcentajeIvaVentas();
            dblPorIva= bdgIva.doubleValue();
            dblFacIva=objUti.redondear(((dblPorIva/100)+1.00), objParSis.getDecimalesMostrar());  //Ej.:1.12

        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }  /**/
    }

    /** Permite obtener la Ip de impresion de guia. **/
    public void cargarIpPuertoGuiaEmp()
    {
        //System.out.println("ZafCxC79.cargarIpPuertoGuiaEmp: ");
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try {
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null) {
                stmLoc = conn.createStatement();

                strSql = "SELECT a1.co_emp, a1.co_loc, a1.tx_dirser, a1.ne_pueser FROM tbm_serCliSer AS a "
                        + " INNER JOIN tbm_serCliSerLoc AS a1 ON( a1.co_ser=a.co_ser ) "
                        + " WHERE a.co_ser=1  AND a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objParSis.getCodigoLocal() + " ";
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    strIpImpGuia = rstLoc.getString("tx_dirser");
                    intPuertoImpGuia = rstLoc.getInt("ne_pueser");
                }
                rstLoc.close();
                stmLoc.close();
                stmLoc = null;
                rstLoc = null;
                conn.close();
                conn = null;
            }
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception Evt) {

            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    /**
     * Esta funcion permite extraer los rangos de ajuste de centavos automativos
     *
     * @return true si no hay problema false por algun error.
     */
    private boolean cargarRangoAjuCenAut() 
    {
        boolean blnRes = true;
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try {
            //System.out.println("ZafCxC79.cargarRangoAjuCenAut: ");
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null) {
                stmLoc = conn.createStatement();

                strSql = "SELECT nd_valminajucenaut, nd_valmaxajucenaut  FROM tbm_emp WHERE co_emp=" + objParSis.getCodigoEmpresa() + " ";
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    dblMinAjuCenAut = objUti.redondear(rstLoc.getDouble("nd_valminajucenaut"), 2);
                    dblMaxAjuCenAut = objUti.redondear(rstLoc.getDouble("nd_valmaxajucenaut"), 2);
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    /*
     * Configuracion de las venta del sistemas.
     */
    public void Configura_ventana_consulta()
    {
        //System.out.println("ZafCxC79.Configura_venta_consulta: ");
        configurarVenConTipDoc();
        configurarVenConDocAbi();
        cargarRangoAjuCenAut();
        configurarVenConFacturas();
        ConfigurarTabla();

        if (blnEstCar) 
        {
            cargarDatos(strCodEmp, strCodLoc, strCodTip, strCodDoc);
        }
    }

    private boolean cargarDatos(String intCodEmp, String intCodLoc, String intCodTipDoc, String strCodDoc) {
        boolean blnRes = true;
        java.sql.Connection conn;
        try {
            //System.out.println("ZafCxC79.cargarDatos: ");
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null) {

                cargarCabReg(conn, intCodEmp, intCodLoc, intCodTipDoc, strCodDoc);
                cargarDetReg(conn, intCodEmp, intCodLoc, intCodTipDoc, strCodDoc);

                conn.close();
                conn = null;
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    private boolean cargarCabReg(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        boolean blnRes = false;
        java.sql.Statement stmLoc, stmLoc01;
        java.sql.ResultSet rstLoc01, rstLoc02;
        String strSql = "", strAux = "";
        try {
            //System.out.println("ZafCxC79.cargarCabReg: ");
            if (conn != null) {
                stmLoc = conn.createStatement();
                stmLoc01 = conn.createStatement();

                strSql = "SELECT a.st_reg, a.co_tipdoc, a1.tx_descor, a1.tx_deslar, a.co_doc, a.fe_doc, a.ne_numdoc1, a.ne_numdoc2, a.nd_mondoc, "
                        + " a.tx_obs1, a.tx_obs2  from tbm_cabpag as a "
                        + " INNER JOIN tbm_cabtipdoc as a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc )  "
                        + " WHERE a.co_emp=" + strCodEmp + " and a.co_loc=" + strCodLoc + " "
                        + " and a.co_tipdoc=" + strCodTipDoc + "  and a.co_Doc=" + strCodDoc + " ";
                //.out.println("ZafCxC79.cargarCabReg: "+strSql);
                rstLoc02 = stmLoc.executeQuery(strSql);
                if (rstLoc02.next()) {

                    txtCodTipDoc.setText(rstLoc02.getString("co_tipdoc"));
                    txtDesCodTitpDoc.setText(rstLoc02.getString("tx_descor"));
                    txtDesLarTipDoc.setText(rstLoc02.getString("tx_deslar"));
                    txtCodDoc.setText(rstLoc02.getString("co_doc"));
                    txtAlt1.setText(rstLoc02.getString("ne_numdoc1"));

                    valDoc.setText("" + objUti.redondear(rstLoc02.getString("nd_mondoc"), 2));
                    txaObs1.setText(rstLoc02.getString("tx_obs1"));
                    txaObs2.setText(rstLoc02.getString("tx_obs2"));

                    strAux = rstLoc02.getString("st_reg");

                    java.util.Date dateObj = rstLoc02.getDate("fe_doc");
                    if (dateObj == null) {
                        txtFecDoc.setText("");
                    } else {
                        java.util.Calendar calObj = java.util.Calendar.getInstance();
                        calObj.setTime(dateObj);
                        txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                calObj.get(java.util.Calendar.MONTH) + 1,
                                calObj.get(java.util.Calendar.YEAR));
                    }
                }
                rstLoc02.close();
                rstLoc02 = null;

                /**
                 * ************************************************
                 */
                objAsiDia.consultarDiario(Integer.parseInt(strCodEmp), Integer.parseInt(strCodLoc), Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc));
                //Eddye: Parche temporal hasta que se mejore el código.
                int intCodTipDocCXCTC=0;
                if (txtDesCodTitpDoc.getText().equals("COBTCM"))
                {
                    intCodTipDocCXCTC=196;
                }
                else if (txtDesCodTitpDoc.getText().equals("COBTCD"))
                {
                    intCodTipDocCXCTC=217;
                }
                objAsiDia2.consultarDiario(Integer.parseInt(strCodEmp), Integer.parseInt(strCodLoc), intCodTipDocCXCTC, Integer.parseInt(strCodDoc));

                if (strAux.equals("A")) {
                    strAux = "Activo";
                } else if (strAux.equals("I")) {
                    strAux = "Anulado";
                } else if (strAux.equals("E")) {
                    strAux = "Eliminado";
                } else {
                    strAux = "Otro";
                }
                objTooBar.setEstadoRegistro(strAux);

                stmLoc.close();
                stmLoc = null;
                stmLoc01.close();
                stmLoc01 = null;

            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }

        return blnRes;
    }

    private boolean cargarDetReg(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        boolean blnRes = false;
        java.sql.Statement stmLoc, stmLoc01;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        Vector vecData;
        try {
            //System.out.println("ZafCxC79.cargarDetReg: ");
            if (conn != null) {
                stmLoc = conn.createStatement();
                stmLoc01 = conn.createStatement();

                vecData = new Vector();

                String sqlAuxDif = "";
                if (objParSis.getCodigoMenu() == 1648) {
                    sqlAuxDif = " , ( a1.mo_pag + a1.nd_abo  ) as dif "; //retencion
                }
                if (objParSis.getCodigoMenu() == 256) {
                    sqlAuxDif = " , ( a1.mo_pag + a1.nd_abo  ) as dif ";  // (abs(a1.nd_abo)-abs(a1.mo_pag) ) as dif ";
                }
                if (objParSis.getCodigoMenu() == 488) {
                    sqlAuxDif = " , ( a1.mo_pag + a1.nd_abo  ) as dif ";
                }
                if (objParSis.getCodigoMenu() == 3366) {
                    sqlAuxDif = " , ( a1.mo_pag + a1.nd_abo  ) as dif ";
                }

                strSql = "SELECT a.tx_numtra as tx_numtra, a.tx_numaut as tx_numaut, a2.tx_numautsri, a2.tx_secdoc, a2.tx_feccad, a2.tx_codsri, "
                        + " a1.tx_numctachq, a1.nd_monchq, a1.co_banchq, ban.tx_deslar as dlbanco,  a1.fe_venchq, a1.tx_numchq, a.co_reg as coregpag, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg " + sqlAuxDif + "  "
                        + " ,a2.co_cli, a2.tx_nomcli, a3.tx_descor, a3.tx_deslar, a2.ne_numdoc, a2.fe_doc, a1.ne_diacre, a1.nd_porret, a1.fe_ven, a1.mo_pag, a.nd_abo "
                        + " ,a.co_tipdoccon, a4.tx_descor as txdctipdoc, a4.tx_deslar as txdltipdoc "
                        + " ,a4.co_ctadeb, a5.tx_codcta AS txctadeb, a5.tx_deslar as nomctadeb,  a4.co_ctahab, a6.tx_codcta as txctahab, a6.tx_deslar as nomctahab       "
                        + " "
                        + " FROM tbm_detpag as a "
                        + " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locpag and a1.co_tipdoc=a.co_tipdocpag and a1.co_doc=a.co_docpag and a1.co_reg=a.co_regpag ) "
                        + " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) "
                        + " inner join tbm_cabtipdoc as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc  ) "
                        + " "
//                        + "  inner join tbm_cabtipdoc as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoccon  ) "
//                        + "  inner join tbm_placta as a5 on (a5.co_emp=a4.co_emp and a5.co_cta=a4.co_ctadeb ) "
//                        + "  inner join tbm_placta as a6 on (a6.co_emp=a4.co_emp and a6.co_cta=a4.co_ctahab ) "
                        + "  left join tbm_cabtipdoc as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoccon  ) "
                        + "  left join tbm_placta as a5 on (a5.co_emp=a4.co_emp and a5.co_cta=a4.co_ctadeb ) "
                        + "  left join tbm_placta as a6 on (a6.co_emp=a4.co_emp and a6.co_cta=a4.co_ctahab ) "
                        + "  LEFT  JOIN tbm_var as ban ON (ban.co_reg=a.co_banchq ) "
                        + " WHERE a.co_emp=" + strCodEmp + " and a.co_loc=" + strCodLoc + " "
                        + " and a.co_tipdoc=" + strCodTipDoc + "  and a.co_Doc=" + strCodDoc + " and a.st_reg='A' "
                        + " ORDER BY a.co_reg ";
                //System.out.println("ZafCxC79.cargarDetReg: "+strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while (rstLoc.next()) {

                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_BUTCLI, "..");
                    vecReg.add(INT_TBL_CHKSEL, new Boolean(true));
                    vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli"));
                    vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nomcli"));
                    vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp"));
                    vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc"));
                    vecReg.add(INT_TBL_CODTID, rstLoc.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc"));
                    vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                    vecReg.add(INT_TBL_DCTIPDOC, rstLoc.getString("tx_descor"));
                    vecReg.add(INT_TBL_DLTIPDOC, rstLoc.getString("tx_deslar"));
                    vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc"));
                    vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc"));
                    vecReg.add(INT_TBL_DIACRE, rstLoc.getString("ne_diacre"));
                    vecReg.add(INT_TBL_FECVEN, rstLoc.getString("fe_ven"));
                    vecReg.add(INT_TBL_PORRET, rstLoc.getString("nd_porret"));
                    vecReg.add(INT_TBL_VALDOC, rstLoc.getString("mo_pag"));
                    vecReg.add(INT_TBL_VALPEN, rstLoc.getString("dif"));
                    vecReg.add(INT_TBL_NUMTRA, rstLoc.getString("tx_numtra"));
                    vecReg.add(INT_TBL_NUMAUT, rstLoc.getString("tx_numaut"));
                    vecReg.add(INT_TBL_ABONO, rstLoc.getString("nd_abo"));
                    vecReg.add(INT_TBL_CODREGEFE, rstLoc.getString("coregpag"));
                    vecReg.add(INT_TBL_ABONOORI, rstLoc.getString("nd_abo"));
                    vecReg.add(INT_TBL_BUTFAC, "..");
                    vecReg.add(INT_TBL_TIPCRE, "Tip.Cre.");
                    vecData.add(vecReg);
                }
                rstLoc.close();
                rstLoc = null;

                objTblMod.setData(vecData);
                tblDat.setModel(objTblMod);


                stmLoc.close();
                stmLoc = null;
                stmLoc01.close();
                stmLoc01 = null;

            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }

        return blnRes;
    }

    private boolean configurarVenConFacturas() {
        boolean blnRes = true;
        try {
            //System.out.println("ZafCxC79.configurarVenConFacturas: ");
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_nomcli");
            arlCam.add("a.co_emp");
            arlCam.add("a.co_loc");
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_desCor");
            arlCam.add("a.tx_desLar");
            arlCam.add("a.co_doc");
            arlCam.add("a.co_reg");
            arlCam.add("a.ne_numDoc");
            arlCam.add("a.fe_doc");
            arlCam.add("a.ne_diaCre");
            arlCam.add("a.fe_ven");
            arlCam.add("a.nd_porRet");
            arlCam.add("a.mo_pag");
            arlCam.add("a.nd_abo");
            arlCam.add("a.nd_pen");
            arlCam.add("a.st_sop");
            arlCam.add("a.st_entSop");
            arlCam.add("a.st_pos");
            arlCam.add("a.co_banChq");
            arlCam.add("a.a4_tx_desLar");
            arlCam.add("a.tx_numCtaChq");
            arlCam.add("a.tx_numChq");
            arlCam.add("a.fe_recChq");
            arlCam.add("a.fe_venChq");
            arlCam.add("a.nd_monChq");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Cód.Cli");
            arlAli.add("Nom.Cli");
            arlAli.add("Cód.Emp");
            arlAli.add("Cód.Loc");
            arlAli.add("Cód.TipDoc");
            arlAli.add("DesCor");
            arlAli.add("tx_desLar");
            arlAli.add("co_doc");
            arlAli.add("co_reg");
            arlAli.add("ne_numDoc");
            arlAli.add("fe_doc");
            arlAli.add("ne_diaCre");
            arlAli.add("fe_ven");
            arlAli.add("nd_porRet");
            arlAli.add("mo_pag");
            arlAli.add("nd_abo");
            arlAli.add("nd_pen");
            arlAli.add("st_sop");
            arlAli.add("st_entSop");
            arlAli.add("st_pos");
            arlAli.add("co_banChq");
            arlAli.add("a4_tx_desLar");
            arlAli.add("tx_numCtaChq");
            arlAli.add("tx_numChq");
            arlAli.add("fe_recChq");
            arlAli.add("fe_venChq");
            arlAli.add("nd_monChq");


            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("55");
            arlAncCol.add("150");
            arlAncCol.add("60");
            arlAncCol.add("50");
            arlAncCol.add("10");
            arlAncCol.add("70");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("75");
            arlAncCol.add("80");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("60");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");

            int intColOcu[] = new int[20];
            intColOcu[0] = 3;
            intColOcu[1] = 5;
            intColOcu[2] = 7;
            intColOcu[3] = 8;
            intColOcu[4] = 9;
            intColOcu[5] = 13;
            intColOcu[6] = 15;
            intColOcu[7] = 16;
            intColOcu[8] = 17;
            intColOcu[9] = 18;
            intColOcu[10] = 19;
            intColOcu[11] = 20;
            intColOcu[12] = 21;
            intColOcu[13] = 22;
            intColOcu[14] = 23;
            intColOcu[15] = 24;
            intColOcu[16] = 25;
            intColOcu[17] = 26;
            intColOcu[18] = 27;
            intColOcu[19] = 12;


            //Armar la sentencia SQL.
            String strSQL;

            String strAux = "";
            if (!(objParSis.getCodigoUsuario() == 1)) {

                if (objParSis.getCodigoUsuario() == 89) {
                    if (objParSis.getCodigoEmpresa() == 1) {
                        strAux = " and a1.co_loc in (" + objParSis.getCodigoLocal() + ",5) ";
                    }
                    if (objParSis.getCodigoEmpresa() == 2) {
                        strAux = " and a1.co_loc in (" + objParSis.getCodigoLocal() + ",5) ";
                    }
                    if (objParSis.getCodigoEmpresa() == 4) {
                        strAux = " and a1.co_loc in (" + objParSis.getCodigoLocal() + ",2) ";
                    }
                } else {
                    strAux = " and a1.co_loc=" + objParSis.getCodigoLocal() + " ";
                }


            }

            String strAuxFil = "";
            strAuxFil = " AND a3.ne_mod IN (1, 3)  AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0) ";

            strSQL = "SELECT * FROM ( SELECT  a1.co_cli, a1.tx_nomcli, a1.co_emp, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, a2.mo_pag, a2.nd_abo "
                    + " , (a2.mo_pag+a2.nd_abo) AS nd_pen, a2.st_sop, a2.st_entSop, a2.st_pos, a2.co_banChq, a4.tx_desLar AS a4_tx_desLar, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq "
                    + " , a2.nd_monChq "
                    + " FROM  tbm_cabMovInv AS a1  "
                    + " INNER JOIN tbm_cli AS b1 ON (a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli) "
                    + " INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) "
                    + " INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)  "
                    + " LEFT OUTER JOIN tbm_var AS a4 ON (a2.co_banChq=a4.co_reg) "
                    + " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "   " + strAux + " " + "AND a1.co_forpag = 96 AND a3.co_tipdoc = 1"
                    + " AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C')  " + strAuxFil + " "
                    + " AND ( a2.tx_numchq IS NULL OR a2.tx_numchq='')   AND (a2.mo_pag+a2.nd_abo)<>0 "
                    + " ) AS a ORDER BY co_emp, co_loc, co_tipDoc, co_doc, co_reg ";

            vcoFac = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

            vcoFac.setConfiguracionColumna(14, javax.swing.JLabel.RIGHT, vcoFac.INT_FOR_NUM, objParSis.getFormatoNumero(), false, true);


        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConDocAbi() {
        boolean blnRes = true;
        int tipDoc;
        try {
            //System.out.println("ZafCxC79.configurarVenConDocAbi: ");
            objZafCxC79_03 = new ZafCxC79_03(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de documentos abiertos TC", true,this);

            objZafCxC79_03.setTipoConsulta(1);
            objZafCxC79_03.setCheckedMostrarSoloDocumentosContado(true);
            objZafCxC79_03.setCheckedMostrarRetenciones(false);

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
public int getTipCre() {
    int tipDoc;
    if(txtDesCodTitpDoc.getText().equals("COBTCM"))
        tipDoc=1;
    else if (txtDesCodTitpDoc.getText().equals("COBTCD"))
        tipDoc=2;
    else
        tipDoc=1;
    //System.out.println("TIPO DE RED " + tipDoc );
    return tipDoc;
}




    /**
     * verifica si ya tiene aplicado algun valor al registro antes de eliminar
     * de la fila
     *
     * @return true si se puede eliminar false no se puede eliminar
     */
    private boolean verificaSelEli() {
        boolean blnRes = true;
        String strEstApl = "";
        try {
            //System.out.println("ZafCxC79.verificaSelEli: ");
            int intFilSel[];
            int intFil = 0;
            intFilSel = tblDat.getSelectedRows();

            for (int i = 0; i < intFilSel.length; i++) {
                intFil = intFilSel[i] - i;
                strEstApl = (tblDat.getValueAt(intFil, INT_TBL_CODREGEFE) == null ? "N" : (tblDat.getValueAt(intFil, INT_TBL_CODREGEFE).equals("") ? "N" : tblDat.getValueAt(intFil, INT_TBL_CODREGEFE).toString()));
                if (!(strEstApl.equals("N"))) {
                    blnRes = false;
                    break;
                }
            }
            intFilSel = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Configuracion de la tabla para el detalle de los datos del cobro con
     * targeta de credito.
     */
    private boolean ConfigurarTabla() {
        boolean blnRes = false;
        try {
            //System.out.println("ZafCxC79.ConfigurarTabla: ");
            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_BUTCLI, "");
            vecCab.add(INT_TBL_CHKSEL, "");
            vecCab.add(INT_TBL_CODCLI, "Cód.Cli.");
            vecCab.add(INT_TBL_NOMCLI, "Cliente");
            vecCab.add(INT_TBL_CODEMP, "Cód.Emp");
            vecCab.add(INT_TBL_CODLOC, "Cód.Loc.");
            vecCab.add(INT_TBL_CODTID, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_CODDOC, "Cód.Doc.");
            vecCab.add(INT_TBL_CODREG, "Cód.Reg.");
            vecCab.add(INT_TBL_DCTIPDOC, "Tip.Doc.");
            vecCab.add(INT_TBL_DLTIPDOC, "Tipo de documento");
            vecCab.add(INT_TBL_NUMDOC, "Núm.Doc.");
            vecCab.add(INT_TBL_FECDOC, "Fec.Doc.");
            vecCab.add(INT_TBL_DIACRE, "Dia.Cre");
            vecCab.add(INT_TBL_FECVEN, "Fec.ven");
            vecCab.add(INT_TBL_PORRET, "Por.Ret.");
            vecCab.add(INT_TBL_VALDOC, "Val.Doc.");
            vecCab.add(INT_TBL_VALPEN, "Val.Pen.");
            vecCab.add(INT_TBL_NUMTRA, "Núm.Tra."); // INGRESO NUMERO DE TRANSACCION.
            vecCab.add(INT_TBL_NUMAUT, "Núm.Aut."); // INGRESO DE NUMERO DE AUTORIZACION.
            vecCab.add(INT_TBL_ABONO, "Val.Abo.");
            vecCab.add(INT_TBL_CODREGEFE, "");
            vecCab.add(INT_TBL_ABONOORI, "");
            vecCab.add(INT_TBL_BUTFAC, "..");
            vecCab.add(INT_TBL_TIPCRE, "Tip.Cre.");

            objTblMod = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();  /**/

            objTblMod.setColumnDataType(INT_TBL_DIACRE, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PORRET, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_VALDOC, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_VALPEN, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_ABONO, objTblMod.INT_COL_DBL, new Integer(0), null);


            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_DIACRE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PORRET).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VALDOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VALPEN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_ABONO).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;


            // Setiamos los colores de los campos Num.Tra, Num.Aut
            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            tcmAux.getColumn(INT_TBL_NUMTRA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NUMAUT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            ZafMouMotAda objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_BUTCLI).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CHKSEL).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DCTIPDOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DLTIPDOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DIACRE).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_FECVEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_PORRET).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_VALDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_VALPEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NUMTRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NUMAUT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_ABONO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BUTFAC).setPreferredWidth(20);

            /* Aqui se agrega las columnas que van
             ha hacer ocultas
             * */
            ArrayList arlColHid = new ArrayList();
            arlColHid.add("" + INT_TBL_CODEMP);
            arlColHid.add("" + INT_TBL_CODTID);
            arlColHid.add("" + INT_TBL_CODDOC);
            arlColHid.add("" + INT_TBL_CODREG);
            arlColHid.add("" + INT_TBL_CODREGEFE);
            arlColHid.add("" + INT_TBL_ABONOORI);
            arlColHid.add("" + INT_TBL_DIACRE);
            arlColHid.add("" + INT_TBL_FECVEN);
            arlColHid.add("" + INT_TBL_PORRET);
            arlColHid.add("" + INT_TBL_TIPCRE); // TC  José Marín M.  12/Marzo/2014
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid = null;


            //Configurar ZafTblMod: Establecer las columnas ELIMINADAS
            java.util.ArrayList arlAux = new java.util.ArrayList();
            arlAux.add("" + INT_TBL_CODREGEFE);
            objTblMod.setColsSaveBeforeRemoveRow(arlAux);
            arlAux = null;


            //Vector que contiene los campos editable de la tabla.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_CHKSEL);
            vecAux.add("" + INT_TBL_BUTCLI);

            ZafPerUsr objZafPerUsr=new ZafPerUsr(objParSis);
            if(objZafPerUsr.isOpcionEnabled(3979)){
                vecAux.add("" + INT_TBL_ABONO);//-> Despues se inplementará
            }

            vecAux.add("" + INT_TBL_NUMDOC);
            vecAux.add("" + INT_TBL_BUTFAC);
            vecAux.add("" + INT_TBL_NUMTRA); // Campo editable para el numero de Transaccion
            vecAux.add("" + INT_TBL_NUMAUT);//Campo editable para el en campo de la autorizacion.
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);



            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKSEL).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;


            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHKSEL).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();

                    String strCodCli = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI) == null ? "" : (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).equals("") ? "" : tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));

                    if ((strCodCli.equals(""))) {
                        objTblCelEdiChk.setCancelarEdicion(true);
                    }

                    if (!(tblDat.getValueAt(intNumFil, INT_TBL_CODREGEFE) == null)) {
                        objTblCelEdiChk.setCancelarEdicion(true);
                    }

                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNunFil = tblDat.getSelectedRow();
                    if (tblDat.getValueAt(intNunFil, INT_TBL_CHKSEL).toString().equals("true")) {

                        if (tblDat.getValueAt(intNunFil, INT_TBL_CODREGEFE) == null) {
                            double dblValPen = objUti.redondear((tblDat.getValueAt(intNunFil, INT_TBL_VALPEN) == null ? "0" : (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("") ? "0" : tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString())), 2);

                            if((((String)tblDat.getValueAt(intNunFil, INT_TBL_ABONO)==null) || ((String)tblDat.getValueAt(intNunFil, INT_TBL_ABONO)).length() == 0) || (((String)tblDat.getValueAt(intNunFil, INT_TBL_ABONO)!=null) && ((String)tblDat.getValueAt(intNunFil, INT_TBL_ABONO)).length()> 0)&& Double.parseDouble((String)tblDat.getValueAt(intNunFil, INT_TBL_ABONO))==0){
                                dblValPen = Math.abs(dblValPen);
                                tblDat.setValueAt("" + dblValPen, intNunFil, INT_TBL_ABONO);
                            }

                        } else {
                            tblDat.setValueAt(tblDat.getValueAt(intNunFil, INT_TBL_ABONOORI), intNunFil, INT_TBL_ABONO);
                        }
                    } else {
                        tblDat.setValueAt("0", intNunFil, INT_TBL_ABONO);

                    }
                    calculaTotMonAbo();
                }
            });


            //Codigo para el campo editable del Bono
            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
             tcmAux.getColumn(INT_TBL_ABONO).setCellEditor(objTblCelEdiTxt);

             objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                 Double bgValAboBef=0.00;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();

                    String strCodCli = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI) == null ? "" : (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).equals("") ? "" : tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
                    if ((strCodCli.equals(""))) {
                        objTblCelEdiTxt.setCancelarEdicion(true);
                    }

                    //System.out.println("ll "+tblDat.getValueAt(intNumFil, INT_TBL_CODREGEFE));
                    if (!(tblDat.getValueAt(intNumFil, INT_TBL_CODREGEFE) == null)) {
                        objTblCelEdiTxt.setCancelarEdicion(true);
                    }

                    bgValAboBef=new Double(((String)tblDat.getValueAt(intNumFil, INT_TBL_ABONO)).equals("")?"0.0":(String)tblDat.getValueAt(intNumFil, INT_TBL_ABONO));

                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNunFil = tblDat.getSelectedRow();

                    double dblValApl = objUti.redondear((tblDat.getValueAt(intNunFil, INT_TBL_ABONO) == null ? "0" : (tblDat.getValueAt(intNunFil, INT_TBL_ABONO).equals("") ? "0" : tblDat.getValueAt(intNunFil, INT_TBL_ABONO).toString())), 4);
                    double dblValPen = objUti.redondear((tblDat.getValueAt(intNunFil, INT_TBL_VALPEN) == null ? "0" : (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("") ? "0" : tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString())), 4);

                    dblValPen = Math.abs(dblValPen);


                    if (dblValApl != 0) {
                        tblDat.setValueAt(new Boolean(true), intNunFil, INT_TBL_CHKSEL);
                    } else {
                        tblDat.setValueAt(new Boolean(false), intNunFil, INT_TBL_CHKSEL);
                    }

                    if (!(tblDat.getValueAt(intNunFil, INT_TBL_CODREGEFE) == null)) {
                        tblDat.setValueAt(bgValAboBef.toString(), intNunFil, INT_TBL_ABONO);
                    }


                    calculaTotMonAbo();
                }
             });


            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_NUMTRA).setCellEditor(objTblCelEdiTxt);


            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();

                    String strCodCli = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI) == null ? "" : (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).equals("") ? "" : tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
                    if ((strCodCli.equals(""))) {
                        objTblCelEdiTxt.setCancelarEdicion(true);
                    }

                    if (!(tblDat.getValueAt(intNumFil, INT_TBL_CODREGEFE) == null)) {
                        objTblCelEdiTxt.setCancelarEdicion(true);
                    }

                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNunFil = tblDat.getSelectedRow();

                    Integer dblValNumTra = Integer.parseInt(tblDat.getValueAt(intNunFil, INT_TBL_NUMTRA) == null ? "0" : (tblDat.getValueAt(intNunFil, INT_TBL_NUMTRA).equals("") ? "0" : tblDat.getValueAt(intNunFil, INT_TBL_NUMTRA).toString()));
                    if (dblValNumTra >= 0) {
                        tblDat.setValueAt(dblValNumTra, intNunFil, INT_TBL_NUMTRA);
                    } else {
                        tblDat.setValueAt(new Boolean(false), intNunFil, INT_TBL_NUMTRA);
                    }

                }
            });

            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_NUMAUT).setCellEditor(objTblCelEdiTxt);


            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();

                    String strCodCli = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI) == null ? "" : (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).equals("") ? "" : tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
                    if ((strCodCli.equals(""))) {
                        objTblCelEdiTxt.setCancelarEdicion(true);
                    }

                    if (!(tblDat.getValueAt(intNumFil, INT_TBL_CODREGEFE) == null)) {
                        objTblCelEdiTxt.setCancelarEdicion(true);
                    }

                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNunFil = tblDat.getSelectedRow();

                    Integer dblValNumAut = Integer.parseInt(tblDat.getValueAt(intNunFil, INT_TBL_NUMAUT) == null ? "0" : (tblDat.getValueAt(intNunFil, INT_TBL_NUMAUT).equals("") ? "0" : tblDat.getValueAt(intNunFil, INT_TBL_NUMAUT).toString()));
                    if (dblValNumAut >= 0) {
                        tblDat.setValueAt(dblValNumAut, intNunFil, INT_TBL_NUMAUT);
                    } else {
                        tblDat.setValueAt(new Boolean(false), intNunFil, INT_TBL_NUMAUT);
                    }
                }
            });

            int intColFac[] = new int[19];
            intColFac[0] = 1;
            intColFac[1] = 2;
            intColFac[2] = 3;
            intColFac[3] = 4;
            intColFac[4] = 5;
            intColFac[5] = 8;
            intColFac[6] = 9;
            intColFac[7] = 11;
            intColFac[8] = 10;
            intColFac[9] = 6;
            intColFac[10] = 7;
            intColFac[11] = 12;
            intColFac[12] = 14;
            intColFac[13] = 13;
            intColFac[14] = 15;
            intColFac[15] = 17;

            // intColFac[16]=16;


            int intColTblFac[] = new int[19];
            intColTblFac[0] = INT_TBL_CODCLI;
            intColTblFac[1] = INT_TBL_NOMCLI;
            intColTblFac[2] = INT_TBL_CODEMP;
            intColTblFac[3] = INT_TBL_CODLOC;
            intColTblFac[4] = INT_TBL_CODTID;
            intColTblFac[5] = INT_TBL_CODDOC;
            intColTblFac[6] = INT_TBL_CODREG;
            intColTblFac[7] = INT_TBL_FECDOC;
            intColTblFac[8] = INT_TBL_NUMDOC;
            intColTblFac[9] = INT_TBL_DCTIPDOC;
            intColTblFac[10] = INT_TBL_DLTIPDOC;
            intColTblFac[11] = INT_TBL_DIACRE;
            intColTblFac[12] = INT_TBL_PORRET;
            intColTblFac[13] = INT_TBL_FECVEN;
            intColTblFac[14] = INT_TBL_VALDOC;
            intColTblFac[15] = INT_TBL_VALPEN;


            objTblCelEdiTxtVcoFacRet = new ZafTblCelEdiTxtVco(tblDat, vcoFac, intColFac, intColTblFac);
            tcmAux.getColumn(INT_TBL_NUMDOC).setCellEditor(objTblCelEdiTxtVcoFacRet);
            objTblCelEdiTxtVcoFacRet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();

                    if (!(tblDat.getValueAt(intNumFil, INT_TBL_CODREGEFE) == null)) {
                        objTblCelEdiTxtVcoFacRet.setCancelarEdicion(true);
                    }
                }

                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    vcoFac.setCampoBusqueda(9);
                    vcoFac.setCriterio1(11);
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();

                    if (tblDat.getValueAt(intNumFil, INT_TBL_CODLOC) == null) {
                        objTblCelEdiTxtVcoFacRet.setCancelarEdicion(true);
                    } else {

                        if (!_getVerificaExistenciaVenCon(intNumFil, tblDat.getValueAt(intNumFil, INT_TBL_CODLOC).toString(),
                                tblDat.getValueAt(intNumFil, INT_TBL_CODTID).toString(),
                                tblDat.getValueAt(intNumFil, INT_TBL_CODDOC).toString(),
                                tblDat.getValueAt(intNumFil, INT_TBL_CODREG).toString())) {
                            objTblMod.removeRow(intNumFil);
                        }
                    }

                    /*double dblValAbo = objUti.redondear((tblDat.getValueAt(intNumFil, INT_TBL_ABONO) == null ? "0" : (tblDat.getValueAt(intNumFil, INT_TBL_ABONO).equals("") ? "0" : tblDat.getValueAt(intNumFil, INT_TBL_ABONO).toString())), 4);
                     if (dblValAbo != 0) {
                     calculaTotMonAbo();
                     }*/

                }
            });



            objTblCelRenBut = new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTCLI).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut = null;


            //------------------------------------------------------------------
            //Eddye_ventana de documentos pendientes//
            objTblCelEdiButGen = new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_BUTCLI).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    if ((tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODREGEFE) == null)) {

                        int intFilSel, intFilSelVenCon[], i, j;
                        String strCodCli, strNomCli, strValDoc;
                        double dblValDoc = 0;
                        if (tblDat.getSelectedColumn() == INT_TBL_BUTCLI) {
                            //System.out.println("Se muestra la ventana");
                            objZafCxC79_03.setVisible(true);
                        }
                        if (objZafCxC79_03.getSelectedButton() == objZafCxC79_03.INT_BUT_ACE) {
                            intFilSel = tblDat.getSelectedRow();
                            intFilSelVenCon = objZafCxC79_03.getFilasSeleccionadas();
                            strCodCli = objZafCxC79_03.getCodigoCliente();
                            strNomCli = objZafCxC79_03.getNombreCliente();
                            j = intFilSel;
                            for (i = 0; i < intFilSelVenCon.length; i++) {
                                if (objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_LIN) != "P") {


                                    strValDoc = objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_VAL_CHQ);
                                    strValDoc = (strValDoc == null ? "0" : (strValDoc.equals("") ? "0" : strValDoc));
                                    dblValDoc = objUti.redondear(strValDoc, 4);
                                    if (!_getVerificaExistencia(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_COD_LOC).toString(),
                                            objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_COD_TIP_DOC).toString(),
                                            objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_COD_DOC).toString(),
                                            objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_COD_REG).toString()))
                                    {

                                    }
                                    else
                                    {
                                        objTblMod.insertRow(j);
                                        objTblMod.setValueAt(strCodCli, j, INT_TBL_CODCLI);
                                        objTblMod.setValueAt(strNomCli, j, INT_TBL_NOMCLI);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_COD_LOC), j, INT_TBL_CODLOC);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_COD_TIP_DOC), j, INT_TBL_CODTID);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_DEC_TIP_DOC), j, INT_TBL_DCTIPDOC);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_DEL_TIP_DOC), j, INT_TBL_DLTIPDOC);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_COD_DOC), j, INT_TBL_CODDOC);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_COD_REG), j, INT_TBL_CODREG);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_NUM_DOC), j, INT_TBL_NUMDOC);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_FEC_DOC), j, INT_TBL_FECDOC);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_DIA_CRE), j, INT_TBL_DIACRE);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_FEC_VEN), j, INT_TBL_FECVEN);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_POR_RET), j, INT_TBL_PORRET);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_VAL_DOC), j, INT_TBL_VALDOC);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_VAL_PEN), j, INT_TBL_VALPEN);
                                        objTblMod.setValueAt("" + objParSis.getCodigoEmpresa(), j, INT_TBL_CODEMP);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_VAL_CHQ), j, INT_TBL_ABONO);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_TIP_CRE), j, INT_TBL_TIPCRE); //TC José Marín 12/Marzo/2014
                                        objZafCxC79_03.setFilaProcesada(intFilSelVenCon[i]);
                                    }
                                    j++;
                                }
                            }
                            tblDat.requestFocus();
//                calculaTotMonAbo();
                        }
                    }
                }
            });
            //------------------------------------------------------------------


            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButCotEmi = new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTFAC).setCellRenderer(objTblCelRenButCotEmi);
            objTblCelRenButCotEmi = null;
            new ButFac(tblDat, INT_TBL_BUTFAC);   //*****



            setEditable(false);

//     new ZafTblOrd(tblDat);

            objTblPopMnu = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
            // objTblPopMnu.setEliminarFilaVisible(false);
            objTblPopMnu.setInsertarFilasVisible(false);
            objTblPopMnu.setInsertarFilaVisible(false);

            objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnu.isClickEliminarFila()) {
                        if (!(objParSis.getCodigoUsuario() == 1)) {
                            if (!verificaSelEli()) {
                                MensajeInf("NO SE PUEDE ELIMINAR. SOLO SE PUEDE ELIMINAR DATOS NUEVOS.  ");
                                objTblPopMnu.cancelarClick();
                            }
                        }
                    }
                }

                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnu.isClickInsertarFila())
                    {
                        //Escriba aquí el código que se debe realizar luego de insertar la fila.
                    }
                    else if (objTblPopMnu.isClickEliminarFila())
                    {
                        //javax.swing.JOptionPane.showMessageDialog(null, "Las filas se eliminaron con éxito.");
                        calculaTotMonAbo();
                    }
                }
            });
            tcmAux = null;


            blnRes = true;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public void setEditable(boolean editable) {
        //System.out.println("ZafCxC79.setEditable: ");
        if (editable == true) {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);

        } else {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }

    private class ButFac extends Librerias.ZafTableColBut.ZafTableColBut_uni {

        public ButFac(javax.swing.JTable tbl, int intIdx) {
            super(tbl, intIdx, "Factura Comercial.");
        }

        public void butCLick() {
            //System.out.println("ZafCxC79.ButFac.butClick: ");
            int intCol = tblDat.getSelectedRow();
            if (tblDat.getValueAt(intCol, INT_TBL_CODCLI) != null) {
                String strCodEmp = tblDat.getValueAt(intCol, INT_TBL_CODEMP).toString();
                String strCodLoc = tblDat.getValueAt(intCol, INT_TBL_CODLOC).toString();
                String strCodTipDoc = tblDat.getValueAt(intCol, INT_TBL_CODTID).toString();
                String strCodDoc = tblDat.getValueAt(intCol, INT_TBL_CODDOC).toString();

                llamarVenFac(strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
            }
        }
    }

    private void llamarVenFac(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        //System.out.println("ZafCxC79.llamarVenFac: ");
        Ventas.ZafVen02.ZafVen02 obj1 = new Ventas.ZafVen02.ZafVen02(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, 14);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();

    }

    private boolean _getVerificaExistencia(String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodReg) {
        boolean blnRes = true;
        try {
            //System.out.println("ZafCxC79._getVerificaExistencia: ");
            for (int i = 0; i < tblDat.getRowCount(); i++) {
                if (tblDat.getValueAt(i, INT_TBL_CODCLI) != null) {
                    if (tblDat.getValueAt(i, INT_TBL_CODLOC).toString().equals(strCodLoc)) {
                        if (tblDat.getValueAt(i, INT_TBL_CODTID).toString().equals(strCodTipDoc)) {
                            if (tblDat.getValueAt(i, INT_TBL_CODDOC).toString().equals(strCodDoc)) {
                                if (tblDat.getValueAt(i, INT_TBL_CODREG).toString().equals(strCodReg)) {
                                    blnRes = false;
                                }
                            }
                        }
                    }
                }
            }

        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean _getVerificaExistenciaVenCon(int intFilSel, String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodReg) {
        boolean blnRes = true;
        try {
            //System.out.println("ZafCxC79._getVerificaExistenciaVenCon: ");
            for (int i = 0; i < tblDat.getRowCount(); i++) {
                if (intFilSel != i) {
                    if (tblDat.getValueAt(i, INT_TBL_CODCLI) != null) {
                        if (tblDat.getValueAt(i, INT_TBL_CODLOC).toString().equals(strCodLoc)) {
                            if (tblDat.getValueAt(i, INT_TBL_CODTID).toString().equals(strCodTipDoc)) {
                                if (tblDat.getValueAt(i, INT_TBL_CODDOC).toString().equals(strCodDoc)) {
                                    if (tblDat.getValueAt(i, INT_TBL_CODREG).toString().equals(strCodReg)) {
                                        blnRes = false;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Calcula el monto total de los cheques ingresados
     */
    public void calculaTotMonAbo() {
        double dblMonCre = 0, dblValDoc = 0;
        String strValDoc = "";
        int intTipMov = 0;
        try {
            //System.out.println("ZafCxC79.calculaTotMonAbo: ");
            for (int i = 0; i < tblDat.getRowCount(); i++) {
                if (tblDat.getValueAt(i, INT_TBL_CODCLI) != null) {
                    strValDoc = (tblDat.getValueAt(i, INT_TBL_VALDOC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_VALDOC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_VALDOC).toString()));

                    dblValDoc = objUti.redondear(strValDoc, 4);
                    if (dblValDoc > 0) {
                        intTipMov = -1;
                    } else {
                        intTipMov = 1;
                    }
                    dblMonCre += Math.abs(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_ABONO) == null) ? "0" : (tblDat.getValueAt(i, INT_TBL_ABONO).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_ABONO).toString())))) * intTipMov;
                }
            }
            dblMonCre = objUti.redondear(dblMonCre, 2);
            valDoc.setText("" + dblMonCre);

            if (!(strCodCtaCli.equals(""))) {
                generaAsiento(dblMonCre);
                generaAsientoCXCTCM(dblMonCre);
            } else {
                generaAsiento();
                generaAsientoCXCTCM();
            }

        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes = true;
        try
        {
            //System.out.println("ZafCxC79.configurarVenConTipDoc: ");
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");
            arlCam.add("a.ne_tipresmoddoc");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Tip.Mod.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("80");
            arlAncCol.add("360");
            arlAncCol.add("20");
            //Armar la sentencia SQL.
            String Str_Sql = "";
            if (objParSis.getCodigoUsuario() == 1)
            {
                Str_Sql = "SELECT * FROM ( Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar, 3 as ne_tipresmoddoc  from tbr_tipdocprg as b "
                        + " left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)"
                        + " where   b.co_emp = " + objParSis.getCodigoEmpresa() + " and "
                        + " b.co_loc = " + objParSis.getCodigoLocal() + " and "
                        + " b.co_mnu = " + objParSis.getCodigoMenu() + " ) AS a ";
            }
            else
            {
                Str_Sql = "SELECT * FROM ( SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar, a1.ne_tipresmoddoc  "
                        + " FROM tbr_tipDocUsr AS a1 inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"
                        + " WHERE "
                        + "  a1.co_emp=" + objParSis.getCodigoEmpresa() + ""
                        + " AND a1.co_loc=" + objParSis.getCodigoLocal() + ""
                        + " AND a1.co_mnu=" + objParSis.getCodigoMenu() + ""
                        + " AND a1.co_usr=" + objParSis.getCodigoUsuario() + " ) AS a  ";
            }
            strSqlTipDocAux = " SELECT co_tipdoc FROM (" + Str_Sql + " ) AS x ";
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=4;
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", Str_Sql, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public void abrirCon() {
        try {
            //System.out.println("ZafCxC79.abrirCon: ");
            CONN_GLO = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    public void CerrarCon() {
        try {
            //System.out.println("ZafCxC79.CerrarCon: ");
            CONN_GLO.close();
            CONN_GLO = null;
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    private void generaAsiento()
    {
        try {
            //System.out.println("ZafCxC79.generaAsiento: ");
            objAsiDia.inicializar();
            int INT_LINEA = 0; //0) Línea: Se debe asignar una cadena vacía o null.
            int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema).
            int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso).
            int INT_VEC_BOTON = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null.
            int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta.
            int INT_VEC_DEBE = 5; //5) Debe.
            int INT_VEC_HABER = 6; //6) Haber.
            int INT_VEC_REF = 7; //7) Referencia: Se debe asignar una cadena vacía o null
            int INT_VEC_NUEVO = 8; //7) Referencia: Se debe asignar una cadena vacía o null


            if (vecDetDiario == null) {
                vecDetDiario = new java.util.Vector();
            } else {
                vecDetDiario.removeAllElements();
            }


            java.util.Vector vecReg;

            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, new Integer(strCodCtaCli));
            vecReg.add(INT_VEC_NUMCTA, strTxtCodCtaCli);
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, strNomCtaCli);
            vecReg.add(INT_VEC_DEBE, new Double(0));
            vecReg.add(INT_VEC_HABER, new Double(0));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);

            vecDetDiario.add(vecReg);
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, new Integer(strCodCtaEfe));
            vecReg.add(INT_VEC_NUMCTA, strTxtCodCtaEfe);
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, strNomCtaEfe);
            vecReg.add(INT_VEC_DEBE, new Double(0));
            vecReg.add(INT_VEC_HABER, new Double(0));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);

            objAsiDia.setDetalleDiario(vecDetDiario);
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private void generaAsiento(double dblValDoc)
    {
        try {
            //System.out.println("ZafCxC79.generaAsiento: ");
            objAsiDia.inicializar();
            int INT_LINEA = 0; //0) Línea: Se debe asignar una cadena vacía o null.
            int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema).
            int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso).
            int INT_VEC_BOTON = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null.
            int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta.
            int INT_VEC_DEBE = 5; //5) Debe.
            int INT_VEC_HABER = 6; //6) Haber.
            int INT_VEC_REF = 7; //7) Referencia: Se debe asignar una cadena vacía o null
            int INT_VEC_NUEVO = 8; //7) Referencia: Se debe asignar una cadena vacía o null

            double dblValDebCli = 0;
            double dblValHabCli = 0;
            double dblValDebEfe = 0;
            double dblValHabEfe = 0;

            if (vecDetDiario == null) {
                vecDetDiario = new java.util.Vector();
            } else {
                vecDetDiario.removeAllElements();
            }

            if (dblValDoc < 0) {
                dblValDebCli = Math.abs(dblValDoc);
                dblValHabCli = 0;
                dblValDebEfe = 0;
                dblValHabEfe = Math.abs(dblValDoc);;
            } else {
                dblValDebCli = 0;
                dblValHabCli = Math.abs(dblValDoc);;
                dblValDebEfe = Math.abs(dblValDoc);;
                dblValHabEfe = 0;
            }
            java.util.Vector vecReg;

            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, new Integer(strCodCtaCli));
            vecReg.add(INT_VEC_NUMCTA, strTxtCodCtaCli);
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, strNomCtaCli);
            vecReg.add(INT_VEC_DEBE, new Double(dblValDebCli));
            vecReg.add(INT_VEC_HABER, new Double(dblValHabCli));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);

            vecDetDiario.add(vecReg);
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, new Integer(strCodCtaEfe));
            vecReg.add(INT_VEC_NUMCTA, strTxtCodCtaEfe);
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, strNomCtaEfe);
            vecReg.add(INT_VEC_DEBE, new Double(dblValDebEfe));
            vecReg.add(INT_VEC_HABER, new Double(dblValHabEfe));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);

            objAsiDia.setDetalleDiario(vecDetDiario);
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private void generaAsientoCXCTCM()
    {
        try
        {
            //System.out.println("ZafCxC79.generaAsientoCXCTCM: ");
            objAsiDia2.inicializar();
            int INT_LINEA = 0; //0) Línea: Se debe asignar una cadena vacía o null.
            int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema).
            int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso).
            int INT_VEC_BOTON = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null.
            int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta.
            int INT_VEC_DEBE = 5; //5) Debe.
            int INT_VEC_HABER = 6; //6) Haber.
            int INT_VEC_REF = 7; //7) Referencia: Se debe asignar una cadena vacía o null
            int INT_VEC_NUEVO = 8; //7) Referencia: Se debe asignar una cadena vacía o null

            int intCodCta1 = 0;
            int intCodCta2 = 0;

            if (vecDetDiario==null)
            {
                vecDetDiario=new java.util.Vector();
            }
            else
            {
                vecDetDiario.removeAllElements();
            }
            java.util.Vector vecReg;
            if (txtDesCodTitpDoc.getText().equals("COBTCM"))
            {
                switch (objParSis.getCodigoEmpresa())
                {
                    case 1: //TUVAL
                        intCodCta1=3527;
                        intCodCta2=3528;
                        break;
                    case 2: //CASTEK
                        intCodCta1=1520;
                        intCodCta2=1521;
                        break;
                    case 4: //DIMULTI
                        intCodCta1=2534;
                        intCodCta2=2535;
                        break;
                }
            }
            else if (txtDesCodTitpDoc.getText().equals("COBTCD"))
            {
                switch (objParSis.getCodigoEmpresa())
                {
                    case 1: //TUVAL
                        intCodCta1=3809;
                        intCodCta2=3808;
                        break;
                    case 2: //CASTEK
                        intCodCta1=1664;
                        intCodCta2=1663;
                        break;
                    case 4: //DIMULTI
                        intCodCta1=2679;
                        intCodCta2=2678;
                        break;
                }
            }
            //OJO: No se cambió los números de las cuentas y nombres de las cuentas de acuerdo al tipo de documento porque
            //     éste documento se guarda en segundo plano y no se ve hasta que se lo vuelva a consultar.
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, intCodCta1);
            vecReg.add(INT_VEC_NUMCTA, "1.01.03.01.17");
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, "TRANSITORIA TC (BANCO INTERNACIONAL)");
            vecReg.add(INT_VEC_DEBE, new Double(0));
            vecReg.add(INT_VEC_HABER, new Double(0));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);

            vecDetDiario.add(vecReg);
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, intCodCta2);
            vecReg.add(INT_VEC_NUMCTA, "1.01.03.01.18");
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, "CXC TC (BANCO INTERNACIONAL)");
            vecReg.add(INT_VEC_DEBE, new Double(0));
            vecReg.add(INT_VEC_HABER, new Double(0));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);

            objAsiDia2.setDetalleDiario(vecDetDiario);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private void generaAsientoCXCTCM(double dblValDoc)
    {
        try
        {
            //System.out.println("ZafCxC79.generaAsientoCXCTCM: ");
            objAsiDia2.inicializar();
            int INT_LINEA = 0; //0) Línea: Se debe asignar una cadena vacía o null.
            int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema).
            int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso).
            int INT_VEC_BOTON = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null.
            int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta.
            int INT_VEC_DEBE = 5; //5) Debe.
            int INT_VEC_HABER = 6; //6) Haber.
            int INT_VEC_REF = 7; //7) Referencia: Se debe asignar una cadena vacía o null
            int INT_VEC_NUEVO = 8; //7) Referencia: Se debe asignar una cadena vacía o null

            int intCodCta1 = 0;
            int intCodCta2 = 0;

            double dblValDebCli = 0;
            double dblValHabCli = 0;
            double dblValDebEfe = 0;
            double dblValHabEfe = 0;

            if (vecDetDiario==null)
            {
                vecDetDiario=new java.util.Vector();
            }
            else
            {
                vecDetDiario.removeAllElements();
            }
            if (dblValDoc<0)
            {
                dblValDebCli=Math.abs(dblValDoc);
                dblValHabCli=0;
                dblValDebEfe=0;
                dblValHabEfe=Math.abs(dblValDoc);;
            }
            else
            {
                dblValDebCli=0;
                dblValHabCli=Math.abs(dblValDoc);;
                dblValDebEfe=Math.abs(dblValDoc);;
                dblValHabEfe=0;
            }
            java.util.Vector vecReg;
            if (txtDesCodTitpDoc.getText().equals("COBTCM"))
            {
                switch (objParSis.getCodigoEmpresa())
                {
                    case 1: //TUVAL
                        intCodCta1=3527;
                        intCodCta2=3528;
                        break;
                    case 2: //CASTEK
                        intCodCta1=1520;
                        intCodCta2=1521;
                        break;
                    case 4: //DIMULTI
                        intCodCta1=2534;
                        intCodCta2=2535;
                        break;
                }
            }
            else if (txtDesCodTitpDoc.getText().equals("COBTCD"))
            {
                switch (objParSis.getCodigoEmpresa())
                {
                    case 1: //TUVAL
                        intCodCta1=3809;
                        intCodCta2=3808;
                        break;
                    case 2: //CASTEK
                        intCodCta1=1664;
                        intCodCta2=1663;
                        break;
                    case 4: //DIMULTI
                        intCodCta1=2679;
                        intCodCta2=2678;
                        break;
                }
            }
            //OJO: No se cambió los números de las cuentas y nombres de las cuentas de acuerdo al tipo de documento porque
            //     éste documento se guarda en segundo plano y no se ve hasta que se lo vuelva a consultar.
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, intCodCta1);
            vecReg.add(INT_VEC_NUMCTA, "1.01.03.01.17");
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, "TRANSITORIA TC (BANCO INTERNACIONAL)");
            vecReg.add(INT_VEC_DEBE, new Double(dblValDebCli));
            vecReg.add(INT_VEC_HABER, new Double(dblValHabCli));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);

            vecDetDiario.add(vecReg);
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, intCodCta2);
            vecReg.add(INT_VEC_NUMCTA, "1.01.03.01.18");
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, "CXC TC (BANCO INTERNACIONAL)");
            vecReg.add(INT_VEC_DEBE, new Double(dblValDebEfe));
            vecReg.add(INT_VEC_HABER, new Double(dblValHabEfe));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);

            objAsiDia2.setDetalleDiario(vecDetDiario);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        lblTit = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtDesCodTitpDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        txtCodDoc = new javax.swing.JTextField();
        lblNumDoc = new javax.swing.JLabel();
        txtAlt1 = new javax.swing.JTextField();
        valDoc = new javax.swing.JTextField();
        lblCodDoc1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblCodDoc2 = new javax.swing.JLabel();
        panGenDet = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenTot = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs3 = new javax.swing.JLabel();
        lblObs4 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panAsiDia = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(100, 64));
        panGenCab.setLayout(null);

        jLabel3.setText("Tipo de documento:"); // NOI18N
        jLabel3.setToolTipText("Tipo de documento");
        panGenCab.add(jLabel3);
        jLabel3.setBounds(0, 4, 100, 20);

        txtDesCodTitpDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesCodTitpDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCodTitpDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCodTitpDocFocusLost(evt);
            }
        });
        txtDesCodTitpDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCodTitpDocActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesCodTitpDoc);
        txtDesCodTitpDoc.setBounds(100, 4, 56, 20);

        txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(156, 4, 284, 20);

        butTipDoc.setText(".."); // NOI18N
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(440, 4, 20, 20);

        txtCodDoc.setBackground(objParSis.getColorCamposSistema());
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(100, 24, 100, 20);

        lblNumDoc.setText("Número de documento:"); // NOI18N
        lblNumDoc.setToolTipText("Número de documento");
        panGenCab.add(lblNumDoc);
        lblNumDoc.setBounds(464, 24, 100, 20);

        txtAlt1.setBackground(objParSis.getColorCamposObligatorios());
        txtAlt1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtAlt1);
        txtAlt1.setBounds(564, 24, 100, 20);

        valDoc.setBackground(objParSis.getColorCamposSistema());
        valDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        valDoc.setText("0.00");
        panGenCab.add(valDoc);
        valDoc.setBounds(564, 44, 100, 20);

        lblCodDoc1.setText("Valor del documento:"); // NOI18N
        lblCodDoc1.setToolTipText("Valor del documento");
        panGenCab.add(lblCodDoc1);
        lblCodDoc1.setBounds(464, 44, 100, 20);

        jLabel6.setText("Fecha del documento:"); // NOI18N
        jLabel6.setToolTipText("Fecha del documento");
        panGenCab.add(jLabel6);
        jLabel6.setBounds(464, 4, 100, 20);

        lblCodDoc2.setText("Código del documento:"); // NOI18N
        lblCodDoc2.setToolTipText("Código del documento");
        panGenCab.add(lblCodDoc2);
        lblCodDoc2.setBounds(0, 24, 100, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        panGenDet.setLayout(new java.awt.BorderLayout());

        tblDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblDat);

        panGenDet.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        panGenTot.setPreferredSize(new java.awt.Dimension(206, 70));
        panGenTot.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs3.setText("Observación 1:"); // NOI18N
        lblObs3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs3);

        lblObs4.setText("Observación 2:"); // NOI18N
        lblObs4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs4);

        panGenTot.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panGenTot.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panGen.add(panGenTot, java.awt.BorderLayout.SOUTH);

        tabGen.addTab("General", panGen);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabGen.addTab("Asiento de diario", panAsiDia);

        getContentPane().add(tabGen, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDesCodTitpDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocActionPerformed
        txtDesCodTitpDoc.transferFocus();
}//GEN-LAST:event_txtDesCodTitpDocActionPerformed

    private void txtDesCodTitpDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocFocusGained
        strDesCodTipDoc = txtDesCodTitpDoc.getText();
        txtDesCodTitpDoc.selectAll();
}//GEN-LAST:event_txtDesCodTitpDocFocusGained

    private void txtDesCodTitpDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocFocusLost
        if (!txtDesCodTitpDoc.getText().equalsIgnoreCase(strDesCodTipDoc))
        {
            if (txtDesCodTitpDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCodTitpDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else
            {
                BuscarTipDoc("a.tx_descor", txtDesCodTitpDoc.getText(), 1);
            }
            //Eddye: Parche temporal hasta que se mejore el código.
            if (objTooBar.getEstado()=='n')
            {
                cargarTipoDocSel(1);
            }
            else
            {
                cargarTipoDocSel(2);
            }
            calculaTotMonAbo();
        }
        else
        {
            txtDesCodTitpDoc.setText(strDesCodTipDoc);
        }
}//GEN-LAST:event_txtDesCodTitpDocFocusLost

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc = txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtDesLarTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCodTitpDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else
            {
                BuscarTipDoc("a.tx_deslar", txtDesLarTipDoc.getText(), 2);
            }
            //Eddye: Parche temporal hasta que se mejore el código.
            if (objTooBar.getEstado()=='n')
            {
                cargarTipoDocSel(1);
            }
            else
            {
                cargarTipoDocSel(2);
            }
            calculaTotMonAbo();
        }
        else
        {
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
}//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        vcoTipDoc.setTitle("Listado de Tipo de Documentos");
        vcoTipDoc.setCampoBusqueda(1);
        vcoTipDoc.show();
        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
            txtDesCodTitpDoc.setText(vcoTipDoc.getValueAt(2));
            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
            intTipModDoc = Integer.parseInt(vcoTipDoc.getValueAt(4).toString());
            strCodTipDoc = vcoTipDoc.getValueAt(1);
        }
        //Eddye: Parche temporal hasta que se mejore el código.
        if (objTooBar.getEstado()=='n')
        {
            cargarTipoDocSel(1);
        }
        else
        {
            cargarTipoDocSel(2);
        }
        calculaTotMonAbo();
}//GEN-LAST:event_butTipDocActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing
    boolean blnExiDifCli = false;

    private double _getObtenerPagoTot() {
        //System.out.println("ZafCxC79._getObtenerPagoTot: ");
        double dblAboTot = 0;
        double dblAbo = 0;
        String strCodCli = "";
        int intFilRec = 0;

        try {
            int intFilSel[];
            int intFil = 0;
            intFilSel = tblDat.getSelectedRows();
            blnExiDifCli = false;

            for (int i = 0; i < intFilSel.length; i++) {
                intFil = intFilSel[i]; //-i;

                if (!(tblDat.getValueAt(intFil, INT_TBL_CODCLI) == null)) {

                    // Math.abs(
                    dblAbo = Double.parseDouble(((tblDat.getValueAt(intFil, INT_TBL_ABONO) == null) ? "0" : (tblDat.getValueAt(intFil, INT_TBL_ABONO).toString().equals("") ? "0" : tblDat.getValueAt(intFil, INT_TBL_ABONO).toString())));

                    if (intFilRec == 0) {
                        intFilRec = 1;
                        strCodCli = (tblDat.getValueAt(intFil, INT_TBL_CODCLI) == null ? "" : tblDat.getValueAt(intFil, INT_TBL_CODCLI).toString());
                    } else {

                        if (!(tblDat.getValueAt(intFil, INT_TBL_CODCLI).toString().equals(strCodCli))) {
                            blnExiDifCli = true;
                        }
                    }
                    dblAboTot += dblAbo;
                }
            }
            intFilSel = null;



        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return dblAboTot;
    }

    /**
     * Para salir de la pantalla en donde estamos y pide confirmacion de
     * salidad.
     */
    private void exitForm() {

        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
        }

    }

    private void MostrarCol(int intCol, int intAch) {
        //System.out.println("ZafCxC79.MostarCol: ");
        tblDat.getColumnModel().getColumn(intCol).setWidth(intAch);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(intAch);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(intAch);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(intAch);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);
    }

    private void ocultaCol(int intCol) {
        //System.out.println("ZafCxC79.ocultarCol: ");
        tblDat.getColumnModel().getColumn(intCol).setWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);
    }

    public void BuscarTipDoc(String campo, String strBusqueda, int tipo) {
        //System.out.println("ZafCxC79.BuscarTipDoc: ");
        vcoTipDoc.setTitle("Listado de Tipo de Documentos");
        if (vcoTipDoc.buscar(campo, strBusqueda)) {
            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
            txtDesCodTitpDoc.setText(vcoTipDoc.getValueAt(2));
            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
            intTipModDoc = Integer.parseInt(vcoTipDoc.getValueAt(4).toString());
            strCodTipDoc = vcoTipDoc.getValueAt(1);

        } else {
            vcoTipDoc.setCampoBusqueda(tipo);
            vcoTipDoc.cargarDatos();
            vcoTipDoc.show();
            if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                txtDesCodTitpDoc.setText(vcoTipDoc.getValueAt(2));
                txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                intTipModDoc = Integer.parseInt(vcoTipDoc.getValueAt(4).toString());
                strCodTipDoc = vcoTipDoc.getValueAt(1);
            } else {
                txtCodTipDoc.setText(strCodTipDoc);
                txtDesCodTitpDoc.setText(strDesCodTipDoc);
                txtDesLarTipDoc.setText(strDesLarTipDoc);
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butTipDoc;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodDoc1;
    private javax.swing.JLabel lblCodDoc2;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs3;
    private javax.swing.JLabel lblObs4;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenTot;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtAlt1;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtDesCodTitpDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField valDoc;
    // End of variables declaration//GEN-END:variables

    private void MensajeInf(String strMensaje) {
        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        obj.showMessageDialog(this, strMensaje, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    public class mitoolbar extends ZafToolBar {

        public mitoolbar(javax.swing.JInternalFrame jfrThis) {
            super(jfrThis, objParSis);
        }

        public boolean anular() {

            boolean blnRes = false;
            java.sql.Connection conn;
            String strAux = "";
            int intCodDoc = 0;
            int intCodTipDoc = 0;
            try {

                strAux = objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado")) {
                    MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                    return false;
                }
                if (strAux.equals("Anulado")) {
                    MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                    return false;
                }

                conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conn != null) {
                    conn.setAutoCommit(false);


                    intCodDoc = Integer.parseInt(txtCodDoc.getText());
                    intCodTipDoc = Integer.parseInt(txtCodTipDoc.getText());


                    if (obtenerEstAnu(conn, intCodTipDoc, intCodDoc)) {
                        if (_getEstCer(conn, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodTipDoc, intCodDoc)) {
                            if (anularReg(conn, intCodTipDoc, intCodDoc)) {
                                if (objAsiDia.anularDiario(conn, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodTipDoc, intCodDoc))
                                {
                                    //Eddye: Parche temporal hasta que se mejore el código.
                                    int intCodTipDocCXCTC=0;
                                    if (txtDesCodTitpDoc.getText().equals("COBTCM"))
                                    {
                                        intCodTipDocCXCTC=196;
                                    }
                                    else if (txtDesCodTitpDoc.getText().equals("COBTCD"))
                                    {
                                        intCodTipDocCXCTC=217;
                                    }
                                    if (objAsiDia2.anularDiario(conn, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodTipDocCXCTC, intCodDoc))
                                    {
                                        conn.commit();
                                        blnRes = true;
                                        objTooBar.setEstadoRegistro("Anulado");
                                        blnHayCam = false;
                                    }
                                    else
                                    {
                                        conn.rollback();
                                    }
                                    blnHayCam = false;
                                }
                                else
                                {
                                    conn.rollback();
                                }
                            } else {
                                conn.rollback();
                            }
                        }
                    } else {
                        blnRes = false;
                    }

                    conn.close();
                    conn = null;
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        /**
         * Anula el registro
         *
         * @param conn coneccion de la base
         * @param intCodTipDoc codigo del tipo de documento
         * @param intCodDoc codigo del documento
         * @return true si se pudo anular false no se puedo anular
         */
        private boolean anularReg(java.sql.Connection conn, int intCodTipDoc, int intCodDoc) {
            boolean blnRes = true;
            java.sql.Statement stmLoc;
            String strSql = "";
            try {
                //System.out.println("ZafCxC79.anularReg: ");
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "UPDATE tbm_cabpag SET st_reg='I', st_regrep='M', "
                            + " fe_ultmod=" + objParSis.getFuncionFechaHoraBaseDatos() + ", "
                            + " co_usrmod=" + objParSis.getCodigoUsuario() + ", "
                            + " tx_commod='" + objParSis.getNombreComputadoraConDirIP() + "' "
                            + " WHERE co_emp=" + objParSis.getCodigoEmpresa() + " "
                            + " AND co_loc=" + objParSis.getCodigoLocal() + " AND co_tipdoc=" + intCodTipDoc + " AND co_doc=" + intCodDoc + " ; "
                            + " "
                            + " UPDATE tbm_pagmovinv SET nd_abo=nd_abo - x.ndabo, st_regrep='M' "
                            + "  FROM ( "
                            + "   select co_emp, co_locpag, co_tipdocpag, co_docpag, co_regpag, nd_abo as ndabo  from tbm_detpag as a "
                            + "   where a.co_emp=" + objParSis.getCodigoEmpresa() + " and a.co_loc=" + objParSis.getCodigoLocal() + " and a.co_tipdoc=" + intCodTipDoc + " "
                            + "   and a.co_doc=" + intCodDoc + " and a.st_reg='A' "
                            + " ) AS x  WHERE "
                            + " tbm_pagmovinv.co_emp=x.co_emp and tbm_pagmovinv.co_loc=x.co_locpag and tbm_pagmovinv.co_tipdoc=x.co_tipdocpag and tbm_pagmovinv.co_doc=x.co_docpag "
                            + " and tbm_pagmovinv.co_reg=x.co_regpag ; "
                            + " ";
                    stmLoc.executeUpdate(strSql);

                    //Eddye: Parche temporal hasta que se mejore el código.
                    int intCodTipDocCXCTC=0;
                    if (txtDesCodTitpDoc.getText().equals("COBTCM"))
                    {
                        intCodTipDocCXCTC=196;
                    }
                    else if (txtDesCodTitpDoc.getText().equals("COBTCD"))
                    {
                        intCodTipDocCXCTC=217;
                    }
                    //Anulamos el documento que corresponde a cuentas por pagar CXCTCM
                    strSql = "UPDATE tbm_cabMovInv SET st_reg = 'I' "
                            + " WHERE co_emp =" + objParSis.getCodigoEmpresa() + " and co_loc = " + objParSis.getCodigoLocal() + " and co_tipdoc = " + intCodTipDocCXCTC + " and co_doc =" + intCodDoc;
                    stmLoc.executeUpdate(strSql);

                    strSql = "UPDATE tbm_pagMovInv SET st_reg = 'I' "
                            + " WHERE co_emp =" + objParSis.getCodigoEmpresa() + " and co_loc = " + objParSis.getCodigoLocal() + " and co_tipdoc = " + intCodTipDocCXCTC + " and co_doc =" + intCodDoc;
                    stmLoc.executeUpdate(strSql);

                    stmLoc.close();
                    stmLoc = null;

                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        public void clickAceptar() {
            setEstadoBotonMakeFac();
        }

        public void clickAnterior() {
            try {
                if (rstCab != null) {
                    abrirCon();
                    if (!rstCab.isFirst()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.previous();
                                refrescaDatos(CONN_GLO, rstCab);
                            }
                        } else {
                            rstCab.previous();
                            refrescaDatos(CONN_GLO, rstCab);
                        }
                    }
                    CerrarCon();

                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickAnular() {
        }

        public void clickConsultar() {
            clnTextos();
            cargarTipoDoc(2);
        }

        public void clickEliminar() {
        }

        public void clickFin() {
            try {
                if (rstCab != null) {
                    abrirCon();
                    if (!rstCab.isLast()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.last();
                                refrescaDatos(CONN_GLO, rstCab);
                            }
                        } else {
                            rstCab.last();
                            refrescaDatos(CONN_GLO, rstCab);
                        }
                    }
                    CerrarCon();
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInicio() {
            try {
                if (rstCab != null) {
                    abrirCon();
                    if (!rstCab.isFirst()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.first();
                                refrescaDatos(CONN_GLO, rstCab);
                            }
                        } else {
                            rstCab.first();
                            refrescaDatos(CONN_GLO, rstCab);
                        }
                    }
                    CerrarCon();
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInsertar() {
            try {
                clnTextos();

                java.awt.Color colBack;
                colBack = txtCodDoc.getBackground();
                txtCodDoc.setEditable(false);
                txtCodDoc.setBackground(colBack);


                valDoc.setEditable(false);
                valDoc.setBackground(colBack);

                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                java.util.Date dateObj = datFecAux;
                java.util.Calendar calObj = java.util.Calendar.getInstance();
                calObj.setTime(dateObj);
                txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                        calObj.get(java.util.Calendar.MONTH) + 1,
                        calObj.get(java.util.Calendar.YEAR));

                setEditable(true);
                objTblMod.setDataModelChanged(false);

                cargarTipoDoc(1);


                if (rstCab != null) {
                    rstCab.close();
                    rstCab = null;
                }


                if (!_getEstImpDoc()) {
                    this.setEstado('w');
                } else {
                    _getMostrarSaldoCta();
                }


            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        /**
         * Esta función muestra la cuenta contable predeterminado del programa
         * de acuerdo al tipo de documento predeterminado.
         *
         * @return true: Si se pudo mostrar la cuenta contable predeterminado.
         * <BR>false: En el caso contrario.
         */
        private void _getMostrarSaldoCta() {
            java.sql.Connection conn;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try {
                //System.out.println("ZafCxC79._getMostrarSaldoCta: ");
                if (txtCodTipDoc.getText().length() > 0) {

                    conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (conn != null) {
                        stmLoc = conn.createStatement();

                        strSql = "SELECT sum(round((a2.nd_mondeb - a2.nd_monhab),2)) as SALDO "
                                + " FROM tbm_cabdia AS a1 "
                                + " INNER JOIN tbm_detdia AS a2 on (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_dia=a2.co_dia) "
                                + " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " "
                                + " AND a2.co_cta = ( SELECT a1.co_ctadeb  FROM tbm_cabTipDoc AS a1 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND  "
                                + " a1.co_loc=" + objParSis.getCodigoLocal() + " AND a1.co_tipDoc= "
                                + " ( SELECT co_tipDoc  FROM tbr_tipDocPrg  WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + " "
                                + " AND co_mnu=" + objParSis.getCodigoMenu() + " AND st_reg='S' )  ) "
                                + " AND a1.st_reg='A' ";
                        //System.out.println("_getMostrarSaldoCta: " + strSql);
                        rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) {
//              txtSalCta.setText(rstLoc.getString("SALDO"));
                        }
                        rstLoc.close();
                        rstLoc = null;
                        stmLoc.close();
                        stmLoc = null;
                        conn.close();
                        conn = null;
                    }
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
        }

        /**
         * carga el tipo de documento cuando se da en click insertar y consultar
         *
         * @param intVal valor si tiene que cargar numero de documento o no 1 =
         * si cargar
         */
        public void cargarTipoDoc(int intVal) {
            java.sql.Connection conn;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try {
                //System.out.println("ZafCxC79.cargarTipoDoc: ");
                conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    if (objParSis.getCodigoUsuario() == 1) {
                        strSql = "SELECT  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor, "
                                + " case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc "
                                + " ,doc.tx_natdoc, doc.st_meringegrfisbod ,a1.co_cta, a1.tx_codcta, a1.tx_deslar AS txdeslarctaefe , a2.co_cta as cocta, a2.tx_codcta as txcodcta, a2.tx_deslar as deslarcta "
                                + " ,2 as ne_tipresmoddoc "
                                + " FROM tbr_tipdocprg as menu "
                                + " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) "
                                + " inner join tbm_placta as a1 on (a1.co_emp=doc.co_emp and a1.co_cta=doc.co_ctadeb ) "
                                + " inner join tbm_placta as a2 on (a2.co_emp=doc.co_emp and a2.co_cta=doc.co_ctahab ) "
                                + " WHERE   menu.co_emp = " + objParSis.getCodigoEmpresa() + " and "
                                + " menu.co_loc = " + objParSis.getCodigoLocal() + " AND  "
                                + " menu.co_mnu = " + objParSis.getCodigoMenu() + " AND  menu.st_reg = 'S' ";
                    } else {
                        strSql = "SELECT  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor, "
                                + " case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc "
                                + " ,doc.tx_natdoc, doc.st_meringegrfisbod ,a1.co_cta, a1.tx_codcta, a1.tx_deslar AS txdeslarctaefe , a2.co_cta as cocta, a2.tx_codcta as txcodcta, a2.tx_deslar as deslarcta "
                                + "  ,menu.ne_tipresmoddoc "
                                + " FROM tbr_tipDocUsr as menu "
                                + " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) "
                                + " inner join tbm_placta as a1 on (a1.co_emp=doc.co_emp and a1.co_cta=doc.co_ctadeb ) "
                                + " inner join tbm_placta as a2 on (a2.co_emp=doc.co_emp and a2.co_cta=doc.co_ctahab ) "
                                + " WHERE   menu.co_emp = " + objParSis.getCodigoEmpresa() + " and "
                                + " menu.co_loc = " + objParSis.getCodigoLocal() + " AND  "
                                + " menu.co_mnu = " + objParSis.getCodigoMenu() + " AND  "
                                + " menu.co_usr=" + objParSis.getCodigoUsuario() + " AND menu.st_reg = 'S' ";

                    }

                    rstLoc = stmLoc.executeQuery(strSql);

                    if (rstLoc.next()) {
                        txtCodTipDoc.setText(((rstLoc.getString("co_tipdoc") == null) ? "" : rstLoc.getString("co_tipdoc")));
                        txtDesCodTitpDoc.setText(((rstLoc.getString("tx_descor") == null) ? "" : rstLoc.getString("tx_descor")));
                        txtDesLarTipDoc.setText(((rstLoc.getString("tx_deslar") == null) ? "" : rstLoc.getString("tx_deslar")));
                        intTipModDoc = rstLoc.getInt("ne_tipresmoddoc");
                        strCodTipDoc = txtCodTipDoc.getText();
                        if (intVal == 1) {
                            txtAlt1.setText(((rstLoc.getString("numDoc") == null) ? "" : rstLoc.getString("numDoc")));
                        }

                        strCodCtaCli = rstLoc.getString("cocta");
                        strTxtCodCtaCli = rstLoc.getString("txcodcta");
                        strNomCtaCli = rstLoc.getString("deslarcta");
                        strCodCtaEfe = rstLoc.getString("co_cta");
                        strTxtCodCtaEfe = rstLoc.getString("tx_codcta");
                        strNomCtaEfe = rstLoc.getString("txdeslarctaefe");

                    }
                    rstLoc.close();
                    stmLoc.close();
                    stmLoc = null;
                    rstLoc = null;
                    conn.close();
                    conn = null;
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
        }

        public void setEstadoBotonMakeFac() {
            switch (getEstado()) {
                case 'l'://Estado 0 => Listo
                    break;
                case 'x'://Estado click modificar
                    break;
                case 'c'://Estado Consultar
                    break;
                case 'y':
                    break;
                case 'z':
                    break;
                default:
                    break;
            }
        }

        public void clickSiguiente() {
            try {
                if (rstCab != null) {
                    abrirCon();
                    if (!rstCab.isLast()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.next();
                                refrescaDatos(CONN_GLO, rstCab);
                            }
                        } else {
                            rstCab.next();
                            refrescaDatos(CONN_GLO, rstCab);
                        }
                    }
                    CerrarCon();
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public boolean eliminar() {
            boolean blnRes = false;

            return blnRes;
        }

        /**
         * validad campos requeridos antes de insertar o modificar
         *
         * @return true si esta todo bien false falta algun dato
         */
        private boolean validaCampos() {
            int intExiDatTbl = 0;
            int intExiDatTbl2 = 0;
            String strMens = "RETENCIONES";
            //System.out.println("ZafCxC79.validaCampos: ");

            if ((objParSis.getCodigoMenu() == 256)) {
                strMens = "CHEQUE";
            }

            if (txtCodTipDoc.getText().trim().equals("")) {
                tabGen.setSelectedIndex(0);
                MensajeInf("El campo << Tipo de documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                txtCodTipDoc.requestFocus();
                return false;
            }

            if (txtAlt1.getText().equals("")) {
                tabGen.setSelectedIndex(0);
                MensajeInf("El campo << Número alterno 1 >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                txtAlt1.requestFocus();
                return false;
            }

//            for (int i = 0; i < tblDat.getRowCount(); i++) {
//                if (!((tblDat.getValueAt(i, INT_TBL_CODCLI) == null ? "" : (tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("") ? "" : tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) && !((tblDat.getValueAt(i, INT_TBL_NUMTRA) == null ? "" : (tblDat.getValueAt(i, INT_TBL_NUMTRA).toString().equals("") ? "" : tblDat.getValueAt(i, INT_TBL_NUMTRA).toString())).equals("")) && !((tblDat.getValueAt(i, INT_TBL_NUMAUT) == null ? "" : (tblDat.getValueAt(i, INT_TBL_NUMAUT).toString().equals("") ? "" : tblDat.getValueAt(i, INT_TBL_NUMAUT).toString())).equals(""))) {
//                    intExiDatTbl = 1;
//                }
//            }

            for (int i = 0; i < tblDat.getRowCount(); i++) {
                if (((tblDat.getValueAt(i, INT_TBL_CHKSEL) == null ? "false" : (tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("") ? "false" : tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true"))) {
                    intExiDatTbl = 1;
                    if (!(!((tblDat.getValueAt(i, INT_TBL_CODCLI) == null ? "" : (tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("") ? "" : tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) && !((tblDat.getValueAt(i, INT_TBL_NUMTRA) == null ? "" : (tblDat.getValueAt(i, INT_TBL_NUMTRA).toString().equals("") ? "" : tblDat.getValueAt(i, INT_TBL_NUMTRA).toString())).equals("")) && !((tblDat.getValueAt(i, INT_TBL_NUMAUT) == null ? "" : (tblDat.getValueAt(i, INT_TBL_NUMAUT).toString().equals("") ? "" : tblDat.getValueAt(i, INT_TBL_NUMAUT).toString())).equals("")))) {
                        intExiDatTbl2 = 1;
                    }
                }
            }


//            if (intExiDatTbl == 0) {
//                MensajeInf("NO HAY DATOS EN DETALLE<<No. AUTORIZACION/No. TRANSACCION>> INGRESE DATOS.... ");
//                return false;
//            }

            if (intExiDatTbl == 0) {
                MensajeInf("NO EXISTEN DOCUMENTOS SELECCIONADOS");
                return false;
            }

            if (intExiDatTbl2 == 1) {
                MensajeInf("DATOS INCOMPLETOS EN DETALLE<<No. AUTORIZACION/No. TRANSACCION>> INGRESE DATOS.... ");
                return false;
            }

            return true;
        }

        public boolean insertar()
        {
            boolean blnRes = false;
            java.sql.Connection conn;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            int intCodDoc = 0;

            ZafGenOdPryTra objGenOD= new ZafGenOdPryTra();
            ZafCnfDoc objValCnf=new ZafCnfDoc(objParSis,this);

            try {
                //System.out.println("ZafCxC79.insertar: ");
                if (validaCampos()) {
                    conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (conn != null) {
                        conn.setAutoCommit(false);
                        stmLoc = conn.createStatement();

                        strSql = "SELECT case when (Max(co_doc)+1) is null then 1 else Max(co_doc)+1 end as codoc  FROM tbm_cabpag WHERE "
                                + " co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + " AND co_tipdoc=" + txtCodTipDoc.getText();
                        rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) {
                            intCodDoc = rstLoc.getInt("codoc");
                        }
                        rstLoc.close();
                        rstLoc = null;

                        strSqlInsDet = new StringBuffer();

                        INT_ENV_REC_IMP_GUIA = 0;

                        if (insertarCab(conn, intCodDoc)) {
                            if (insertarDet(conn, intCodDoc)) {
                                if (objAsiDia.insertarDiario(conn, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), String.valueOf(intCodDoc), txtAlt1.getText(), objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy")))
                                {
                                    //Eddye: Parche temporal hasta que se mejore el código.
                                    int intCodTipDocCXCTC=0;
                                    if (txtDesCodTitpDoc.getText().equals("COBTCM"))
                                    {
                                        intCodTipDocCXCTC=196;
                                    }
                                    else if (txtDesCodTitpDoc.getText().equals("COBTCD"))
                                    {
                                        intCodTipDocCXCTC=217;
                                    }
                                    if (objAsiDia2.insertarDiario(conn, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodTipDocCXCTC, String.valueOf(intCodDoc), txtAlt1.getText(), objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy"))) {
                                        if (objAjuCenAut.realizaAjuCenAut(conn, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), intCodDoc, 80, objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy"))) {
                                            conn.commit();
                                            txtCodDoc.setText("" + intCodDoc);
                                            blnRes = true;
                                            cargarDetIns(conn, intCodDoc);

                                            if (INT_ENV_REC_IMP_GUIA == 1) {
                                                

                                                Iterator itVecOD=vecOD.iterator();
                                                while(itVecOD.hasNext()){
                                                   String strDat=(String)itVecOD.next();
                                                   String[] strArrDat=strDat.split("-");

                                                   int intCodEmp=Integer.parseInt((String)strArrDat[0]);
                                                   int intCodLoc=Integer.parseInt((String)strArrDat[1]);
                                                   int intCodTipDoc2=Integer.parseInt((String)strArrDat[2]);
                                                   int intCodDocu=Integer.parseInt((String)strArrDat[3]);
                                                   //int intNumDoc=Integer.parseInt((String)strArrDat[4]);
                                                  
                                                        if(!objValCnf.isDocIngPenCnfxFac(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, "I")){
                                                            if(!(objGenOD.validarODExs(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu))){
                                                                if(objGenOD.generarNumOD(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, false)){
                                                                    conn.commit();
                                                                    String strIp=objGenOD.obtenerIpSerImp(conn);
                                                                    objGenOD.imprimirOdLocal(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, strIp);                                                
                                                                    objGenOD.generarTermL(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu);
                                                                }else{
                                                                    conn.rollback();
                                                                }
                                                            }
                                                        }
                                                   // }
                                                }
                                                vecOD.clear();
                                            }

                                        } else {
                                            conn.rollback();
                                        }
                                    } else {
                                        conn.rollback();
                                    }
                                }
                                else
                                {
                                    conn.rollback();
                                }
                            } else {
                                conn.rollback();
                            }
                        } else {
                            conn.rollback();
                        }

                        strSqlInsDet = null;

                        stmLoc.close();
                        stmLoc = null;
                        conn.close();
                        conn = null;
                    }

                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private void enviarRequisitoImp(String strIp, int intPuerto) {
            try {

                java.net.Socket s1 = new java.net.Socket(strIp, intPuerto);
                java.io.DataOutputStream dos = new java.io.DataOutputStream(s1.getOutputStream());
                dos.writeInt(1);

                dos.close();
                s1.close();

            } catch (java.net.ConnectException connExc) {
                System.err.println("OCURRIO UN ERROR 1 " + connExc);
            } catch (IOException e) {
                System.err.println("OCURRIO UN ERROR 2 " + e);
            }
        }

        /**
         * Se encarga de hacer la reestructuracion de un pago de la factura
         *
         * @param conn coneccion de la base
         * @return true si se reestructuro bien false si no puedo realizar dicho
         * proceso
         */
        private boolean reestructurarFacturas(java.sql.Connection conn) {
            boolean blnRes = true;
            java.sql.Statement stmLoc, stmLoc01, stmLoc02;
            java.sql.ResultSet rstLoc, rstLoc01;
            String strSql = "";
            String strValDoc = "", strValPen = "", strValApl = "";
            String strCodEmp = "", strCodLoc = "", strCodTipDocRec = "", strCodDoc = "", strCodReg = "";
            int intCodReg = 0;
            int intEst = 0;
            int intTipMov = 1;
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    stmLoc01 = conn.createStatement();
                    stmLoc02 = conn.createStatement();


                    stbFacSel = new StringBuffer();
                    for (int i = 0; i < tblDat.getRowCount(); i++) {
                        if (!((tblDat.getValueAt(i, INT_TBL_CODCLI) == null ? "" : (tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("") ? "" : tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals(""))) {
                            if (((tblDat.getValueAt(i, INT_TBL_CODREGEFE) == null ? "" : (tblDat.getValueAt(i, INT_TBL_CODREGEFE).toString().equals("") ? "" : tblDat.getValueAt(i, INT_TBL_CODREGEFE).toString())).equals(""))) {

                                strValDoc = (tblDat.getValueAt(i, INT_TBL_VALDOC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_VALDOC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_VALDOC).toString()));
                                strValPen = (tblDat.getValueAt(i, INT_TBL_VALPEN) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_VALPEN).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_VALPEN).toString()));
                                strValApl = (tblDat.getValueAt(i, INT_TBL_ABONO) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_ABONO).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_ABONO).toString()));

                                strCodEmp = tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
                                strCodLoc = tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
                                strCodTipDocRec = tblDat.getValueAt(i, INT_TBL_CODTID).toString();
                                strCodDoc = tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
                                strCodReg = tblDat.getValueAt(i, INT_TBL_CODREG).toString();

                                strSql = "SELECT " + strCodEmp + " as coemp, " + strCodLoc + " AS coloc, " + strCodTipDocRec + " as cotipdoc, " + strCodDoc + " as codoc "
                                        + " ," + strCodReg + " as coreg, " + strValDoc + " as valdoc, " + strValPen + " as valpen, " + strValApl + " as valapl  ";

                                //if(strEstFac.equals("S")){

                                if (intEst == 1) {
                                    stbFacSel.append(" UNION ALL ");
                                }
                                stbFacSel.append(strSql);
                                intEst = 1;
                                //}
                            }
                        }
                    }

                    if (intEst == 1) {

                        strSql = "SELECT x.* FROM ( "
                                + " SELECT *, ( abs(valpen)- abs(valapl) ) as val2 FROM ( " + stbFacSel.toString() + " ) AS x ) AS x "
                                + " INNER JOIN tbm_emp AS a1 ON ( a1.co_emp = x.coemp )   "
                                + " WHERE val2 > 0 and   ( abs(val2)  between  a1.nd_valminajucenaut and  a1.nd_valmaxajucenaut ) = false ";

                        rstLoc = stmLoc.executeQuery(strSql);
                        while (rstLoc.next()) {

                            if (rstLoc.getDouble("valdoc") > 0) {
                                intTipMov = 1;
                            } else {
                                intTipMov = -1;
                            }

                            intCodReg = _getObtenerMaxCodRegPag(conn, rstLoc.getInt("coemp"), rstLoc.getInt("coloc"), rstLoc.getInt("cotipdoc"), rstLoc.getInt("codoc"));

                            strSql = "INSERT INTO tbm_pagmovinv( co_emp, co_loc, co_tipdoc, co_doc, co_reg , ne_diacre, fe_ven, "
                                    + " co_tipret, nd_porret, tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, "
                                    + " tx_numchq, fe_recchq, fe_venchq, nd_monchq, co_prochq, st_reg, st_regrep, fe_ree, co_usrree,  "
                                    + " tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp ) "
                                    + " SELECT co_emp, co_loc, co_tipdoc, co_doc, " + intCodReg + " , ne_diacre, fe_ven, "
                                    + " co_tipret, nd_porret, tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop,  "
                                    + " 'N', 'N', null, null, null, null,  "
                                    + " null, 0, co_prochq, CASE WHEN st_reg IN ('A') THEN 'F' ELSE CASE WHEN st_reg IN ('C') THEN 'I'  END END AS estreg , "
                                    + " 'I', fe_ree, co_usrree,  "
                                    + " tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp "
                                    + " FROM tbm_pagmovinv where co_emp=" + rstLoc.getInt("coemp") + " and co_loc=" + rstLoc.getInt("coloc") + " and "
                                    + " co_tipdoc=" + rstLoc.getInt("cotipdoc") + "  and co_doc=" + rstLoc.getInt("codoc") + "  and co_reg=" + rstLoc.getInt("coreg") + "   ";

                            stmLoc01.executeUpdate(strSql);

                            intCodReg = _getObtenerMaxCodRegPag(conn, rstLoc.getInt("coemp"), rstLoc.getInt("coloc"), rstLoc.getInt("cotipdoc"), rstLoc.getInt("codoc"));

                            strSql = "INSERT INTO tbm_pagmovinv( co_emp, co_loc, co_tipdoc, co_doc, co_reg , ne_diacre, fe_ven, "
                                    + " co_tipret, nd_porret, tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, "
                                    + " tx_numchq, fe_recchq, fe_venchq, nd_monchq, co_prochq, st_reg, st_regrep, fe_ree, co_usrree,  "
                                    + " tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp ) "
                                    + " SELECT co_emp, co_loc, co_tipdoc, co_doc, " + intCodReg + " , ne_diacre, fe_ven, "
                                    + " 0, 0, tx_aplret, abs(" + rstLoc.getDouble("val2") + ")*" + intTipMov + ", ne_diagra, 0 as nd_abo, st_sop,  "
                                    + " 'N', 'N', null, null, null, null,  "
                                    + " null, 0, co_prochq, 'C', 'M', fe_ree, co_usrree,  "
                                    + " tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp "
                                    + " FROM tbm_pagmovinv where co_emp=" + rstLoc.getInt("coemp") + " and co_loc=" + rstLoc.getInt("coloc") + " and "
                                    + " co_tipdoc=" + rstLoc.getInt("cotipdoc") + "  and co_doc=" + rstLoc.getInt("codoc") + "  and co_reg=" + rstLoc.getInt("coreg") + "   ";
                            stmLoc01.executeUpdate(strSql);


                            /**
                             * ************* SI HAY PAGO REALIZADO GENERE
                             * REGISTRO DE ESE PAGO Y CAMBIA DETPAG AL REGISTRO
                             * NUEVO *********************
                             */
                            strSql = "SELECT nd_abo FROM tbm_pagmovinv WHERE co_emp=" + rstLoc.getInt("coemp") + " and co_loc=" + rstLoc.getInt("coloc") + " and "
                                    + " co_tipdoc=" + rstLoc.getInt("cotipdoc") + "  and co_doc=" + rstLoc.getInt("codoc") + "  and co_reg=" + rstLoc.getInt("coreg") + "  "
                                    + " AND nd_abo > 0 ";
                            rstLoc01 = stmLoc02.executeQuery(strSql);
                            if (rstLoc01.next()) {

                                intCodReg = _getObtenerMaxCodRegPag(conn, rstLoc.getInt("coemp"), rstLoc.getInt("coloc"), rstLoc.getInt("cotipdoc"), rstLoc.getInt("codoc"));

                                strSql = "INSERT INTO tbm_pagmovinv( co_emp, co_loc, co_tipdoc, co_doc, co_reg , ne_diacre, fe_ven, "
                                        + " co_tipret, nd_porret, tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, "
                                        + " tx_numchq, fe_recchq, fe_venchq, nd_monchq, co_prochq, st_reg, st_regrep, fe_ree, co_usrree,  "
                                        + " tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp ) "
                                        + " SELECT co_emp, co_loc, co_tipdoc, co_doc, " + intCodReg + " , ne_diacre, fe_ven, "
                                        + " co_tipret, nd_porret, tx_aplret, abs(nd_abo)*" + intTipMov + ", ne_diagra, nd_abo, st_sop,  "
                                        + " st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq,  "
                                        + " fe_venchq, nd_monchq, co_prochq, 'C', 'M', fe_ree, co_usrree,  "
                                        + " tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp "
                                        + " FROM tbm_pagmovinv where co_emp=" + rstLoc.getInt("coemp") + " and co_loc=" + rstLoc.getInt("coloc") + " and "
                                        + " co_tipdoc=" + rstLoc.getInt("cotipdoc") + "  and co_doc=" + rstLoc.getInt("codoc") + "  and co_reg=" + rstLoc.getInt("coreg") + "   ";
                                stmLoc01.executeUpdate(strSql);

                                strSql = "UPDATE tbm_detpag SET co_regpag=" + intCodReg + ", st_regrep='M' WHERE co_emp=" + rstLoc.getInt("coemp") + " and co_locpag=" + rstLoc.getInt("coloc") + " and "
                                        + " co_tipdocpag=" + rstLoc.getInt("cotipdoc") + "  and co_docpag=" + rstLoc.getInt("codoc") + "  and co_regpag=" + rstLoc.getInt("coreg");
                                stmLoc01.executeUpdate(strSql);

                            }
                            rstLoc01.close();
                            rstLoc01 = null;

                            /**
                             * ************************************************************************************************************************
                             */
                            strSql = "UPDATE tbm_pagmovinv SET nd_abo=0,  mo_pag=abs(" + rstLoc.getDouble("valapl") + ")*" + intTipMov + ", st_reg='C', st_regrep='M' WHERE "
                                    + " co_emp=" + rstLoc.getInt("coemp") + " and co_loc=" + rstLoc.getInt("coloc") + " and "
                                    + " co_tipdoc=" + rstLoc.getInt("cotipdoc") + "  and co_doc=" + rstLoc.getInt("codoc") + "  and co_reg=" + rstLoc.getInt("coreg") + "   ";
                            stmLoc01.executeUpdate(strSql);

                        }
                        rstLoc.close();
                        rstLoc = null;

                    }
                    stmLoc.close();
                    stmLoc = null;
                    stmLoc01.close();
                    stmLoc01 = null;
                    stmLoc02.close();
                    stmLoc02 = null;
                    stbFacSel = null;

                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        /**
         * Obtiene el maximo registro de la tabla tbm_pagmovinv + 1
         *
         * @param conn coneccion de la base
         * @param intCodEmp codigo de la empresa
         * @param intCodLoc codigo del local
         * @param intCodTipDoc codigo del tipo documento
         * @param intCodDoc codigo del documento
         * @return -1 si no se hay algun error caso contrario retorna el valor
         * correcto
         */
        public int _getObtenerMaxCodRegPag(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc) {
            int intCodReg = -1;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try {
                //System.out.println("ZafCxC79._getObtenerMaxCodRegPag: ");
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "SELECT CASE WHEN max(co_reg) IS NULL THEN 1 ELSE max(co_reg)+1 END AS coreg FROM tbm_pagmovinv "
                            + " WHERE co_emp=" + intCodEmp + " AND co_loc=" + intCodLoc + " AND co_tipdoc=" + intCodTipDoc + " AND co_doc= " + intCodDoc + " ";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        intCodReg = rstLoc.getInt("coreg");
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return intCodReg;
        }

        private boolean insertarCab(java.sql.Connection conn, int intCodDoc) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            String strFecDoc = "";
            try {
                //System.out.println("ZafCxC79.insertarCab: ");
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    //************  PERMITE SABER SI EL NUMERO DE RECEPCION ESTA DUPLICADO  *****************/
                    strSql = "select count(ne_numdoc1) as num from tbm_cabpag WHERE "
                            + " co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " "
                            + "and co_tipdoc=" + txtCodTipDoc.getText() + " and ne_numdoc1=" + txtAlt1.getText();
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        if (rstLoc.getInt("num") >= 1) {
                            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
                            String strTit, strMsg;
                            strTit = "Mensaje del sistema Zafiro";
                            strMsg = " No. de Cobro ya existe... ?";
                            oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE, null);
                            blnRes = true;
                        }
                    }
                    rstLoc.close();
                    rstLoc = null;
                    if (blnRes) {
                        return false;
                    }
                    blnRes = false;
                    //***********************************************************************************************/

                    strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";

                    strSql = "INSERT INTO tbm_cabpag(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc1, ne_numdoc2, tx_obs1, tx_obs2 "
                            + " ,nd_mondoc, st_reg, fe_ing, co_usring, tx_coming, co_mnu, st_ciecobtarcre ) "
                            + " VALUES(" + objParSis.getCodigoEmpresa() + ", " + objParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                            + " ,'" + strFecDoc + "', " + (txtAlt1.getText().trim().equals("") ? null : txtAlt1.getText()) + ", " + (txtAlt1.getText().trim().equals("") ? null : txtAlt1.getText()) + ", " + objUti.codificar(txaObs1.getText()) + ", " + objUti.codificar(txaObs2.getText()) + " "
                            + " ," + (valDoc.getText().equals("") ? "0" : valDoc.getText()) + ", 'A', " + objParSis.getFuncionFechaHoraBaseDatos() + "," + objParSis.getCodigoUsuario() + " "
                            + " ,'" + objParSis.getNombreComputadoraConDirIP() + "', " + objParSis.getCodigoMenu() + ", '' ) ; ";
                    stmLoc.executeUpdate(strSql);
                    //Eddye: Parche temporal hasta que se mejore el código.
                    if (txtDesCodTitpDoc.getText().equals("COBTCM"))
                    {
                        int intCodTipDocCXCTC=196;
                        String strIdeCli="1790098354001";
                        String strCodCli="";
                        String strNomCli="";
                        strSql = "select co_cli, tx_nom from tbm_cli where co_emp = " + objParSis.getCodigoEmpresa() + " AND tx_ide = '" + strIdeCli + "'";
                        rstCab = stmLoc.executeQuery(strSql);
                        while (rstCab.next()) {
                            strCodCli = rstCab.getString("co_cli");
                            strNomCli = rstCab.getString("tx_nom");
                        }
                        strSql = "INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipdoc, co_doc, ne_numdoc, fe_doc, co_cli, tx_ruc, tx_nomcli, co_forpag, nd_tot ,tx_obs1, tx_obs2, st_reg, fe_ing, co_usring, co_mnu)"
                                + "VALUES(" + objParSis.getCodigoEmpresa() + "," + objParSis.getCodigoLocal() + ", " + intCodTipDocCXCTC + ", " + intCodDoc + " "
                                + "," + (txtAlt1.getText().trim().equals("") ? null : txtAlt1.getText()) + ", '" + strFecDoc + "', " + strCodCli + ", '" + strIdeCli + "', '" + strNomCli + "', 97, " + (valDoc.getText().equals("") ? "0" : valDoc.getText()) + " * -1 "
                                + "," + objUti.codificar(txaObs1.getText()) + "," + objUti.codificar(txaObs2.getText()) + ",'A'," + objParSis.getFuncionFechaHoraBaseDatos() + ", " + objParSis.getCodigoUsuario() + " "
                                + "," + objParSis.getCodigoMenu() + ")";
                        //System.out.println("ZafCxC79.insertarCab: " + strSql);
                        stmLoc.executeUpdate(strSql);
                    }
                    else if (txtDesCodTitpDoc.getText().equals("COBTCD"))
                    {
                        int intCodTipDocCXCTC=217;
                        String strIdeCli="1790010937001";
                        String strCodCli="";
                        String strNomCli="";
                        strSql = "select co_cli, tx_nom from tbm_cli where co_emp = " + objParSis.getCodigoEmpresa() + " AND tx_ide = '" + strIdeCli + "'";
                        rstCab = stmLoc.executeQuery(strSql);
                        while (rstCab.next()) {
                            strCodCli = rstCab.getString("co_cli");
                            strNomCli = rstCab.getString("tx_nom");
                        }
                        strSql = "INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipdoc, co_doc, ne_numdoc, fe_doc, co_cli, tx_ruc, tx_nomcli, co_forpag, nd_tot ,tx_obs1, tx_obs2, st_reg, fe_ing, co_usring, co_mnu)"
                                + "VALUES(" + objParSis.getCodigoEmpresa() + "," + objParSis.getCodigoLocal() + ", " + intCodTipDocCXCTC + ", " + intCodDoc + " "
                                + "," + (txtAlt1.getText().trim().equals("") ? null : txtAlt1.getText()) + ", '" + strFecDoc + "', " + strCodCli + ", '" + strIdeCli + "', '" + strNomCli + "', 97, " + (valDoc.getText().equals("") ? "0" : valDoc.getText()) + " * -1 "
                                + "," + objUti.codificar(txaObs1.getText()) + "," + objUti.codificar(txaObs2.getText()) + ",'A'," + objParSis.getFuncionFechaHoraBaseDatos() + ", " + objParSis.getCodigoUsuario() + " "
                                + "," + objParSis.getCodigoMenu() + ")";
                        //System.out.println("ZafCxC79.insertarCab: " + strSql);
                        stmLoc.executeUpdate(strSql);
                    }
                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        /* Función que permite Insertar el Detalle del Cobro de Tarjeta de Crédito */ 
        private boolean insertarDet(java.sql.Connection conn, int intCodDoc)
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstCab = null;
            String strSql = "";
            String strCodEmp = "", strCodLoc = "", strCodTipDocRec = "", strCodDoc = "", strCodReg = "",strNumDoc="";
            String strAbono="0", strValPen="", strNumTra="0", strNumAut="0",  strValDoc="0", strFecVen = "", strMetRed="";
            double dblSubTot=0,	dblIva=0, dblPorCom=0, dblValPen=0, dblValAbo=0, dblPorRetFte=2.000, dblPorRetIva=20.000 ;
            double dblBasComNet=0,  dblBasRetFte=0, dblBasRetIva=0,  dblRetFte=0.02,  dblRetIva=0.20, dblValCom=0.00, dblComFij=0.00;
            double dblTotValCob=0, dblTotValPag=0, dblTotCom=0,  dblTotRetFte=0, dblTotRetIva=0 ;
            int intTipMov = 1;  //Artificio para saber si es valor abonado se guardará con negativo o positivo.
            int intCodRegDet=0; //Código de registro.
            vecOD.clear();

            try
            {
                //System.out.println("ZafCxC79.insertarDet: ");
                if (conn != null)
                {
                    stmLoc = conn.createStatement();

                    for (int i = 0; i < tblDat.getRowCount(); i++)
                    {
                        if (!((tblDat.getValueAt(i, INT_TBL_CODCLI) == null ? "" : (tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("") ? "" : tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")))
                        {
                            if (((tblDat.getValueAt(i, INT_TBL_CHKSEL) == null ? "false" : (tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("") ? "false" : tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")))
                            {
                                strCodEmp = tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
                                strCodLoc = tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
                                strCodTipDocRec = tblDat.getValueAt(i, INT_TBL_CODTID).toString();
                                strCodDoc = tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
                                strCodReg = tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                                strFecVen = tblDat.getValueAt(i, INT_TBL_FECVEN).toString();
                                strValDoc = tblDat.getValueAt(i, INT_TBL_VALDOC).toString();
                                strNumTra = tblDat.getValueAt(i, INT_TBL_NUMTRA).toString();
                                strNumAut = tblDat.getValueAt(i, INT_TBL_NUMAUT).toString();
                                strAbono = (tblDat.getValueAt(i, INT_TBL_ABONO) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_ABONO).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_ABONO).toString()));
                                strValPen = (tblDat.getValueAt(i, INT_TBL_VALPEN) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_VALPEN).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_VALPEN).toString()));

                                /*MODIFICADO PARA NUEVO SERVICIO 1*/
                                strNumDoc = tblDat.getValueAt(i, INT_TBL_NUMDOC).toString();
                                /*MODIFICADO PARA NUEVO SERVICIO 1*/
                                dblValPen = objUti.redondear(strValPen, 2);
                                if (dblValPen > 0)
                                {     intTipMov = -1;          }
                                else
                                {     intTipMov = 1;           }

                                /* Almacena los pagos realizados en el Local que se realiza el Cobro con TC para la factura correspondiente.
                                 * Adicional enlaza el Local de donde se realizo la factura de venta.*/
                                intCodRegDet++;
                                strSql = " INSERT INTO tbm_detpag( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag,"
                                        + " co_docpag, co_regpag, nd_abo,  st_reg, st_regrep, tx_numtra, tx_numaut )"
                                        + " VALUES( " + objParSis.getCodigoEmpresa() + ", " + objParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                        + " ," + intCodRegDet + ", " + strCodLoc + ", " + strCodTipDocRec + ", " + strCodDoc + ", " + strCodReg + ", " + strAbono + " * " + intTipMov + " , 'A','I', " + objUti.codificar(strNumTra) + "," + objUti.codificar(strNumAut) + " ) ; ";
                                //System.out.println("ZafCxC79.insertarDet: " + strSql);
                                stmLoc.executeUpdate(strSql);

                                /* Actualiza el Valor de Abono en la Factura en el Local donde se realizo la factura de venta*/
                                strSql = " UPDATE tbm_pagmovinv set nd_abo=nd_abo + " + strAbono + " * " + intTipMov + ", "
                                        + " st_regrep='M' WHERE "
                                        + " co_emp=" + strCodEmp + " and co_loc=" + strCodLoc + " and co_tipdoc=" + strCodTipDocRec + " and co_doc=" + strCodDoc + " "
                                        + " and co_reg=" + strCodReg + " ; ";
                                //System.out.println("ZafCxC79.insertarDet: " + strSql);
                                stmLoc.executeUpdate(strSql);

                                /* Consulta Datos de la Factura de Ventas para utilizar valores en calculos de retenciones, comisión, etc. */
                                strSql=" SELECT a1.nd_sub, a1.nd_tot, a2.nd_porCom, a2.tx_tipCre, a1.co_tipCre,";
                                strSql+=" CASE WHEN a2.nd_valComFij IS NULL THEN 0 ELSE a2.nd_valComFij END AS nd_valComFij, a2.ne_metRed ";
                                strSql+=" FROM tbm_cabMovInv AS a1 ";
                                strSql+=" INNER JOIN tbm_tipCreRedTarCre AS a2 ON (a1.co_emp = a2.co_emp AND a1.co_tipcre=a2.co_tipcre) ";
                                strSql+=" WHERE a1.co_emp = " + strCodEmp + " AND a1.co_loc = " + strCodLoc + " ";
                                strSql+=" AND a1.co_tipdoc = " + strCodTipDocRec + " and a1.co_doc = " + strCodDoc + "";
                                //System.out.println("ZafCxC79.insertarDet: " + strSql);
                                rstCab = stmLoc.executeQuery(strSql);
                                while (rstCab.next())
                                {
                                    dblSubTot= objUti.redondear(Math.abs(Double.parseDouble(strAbono) / dblFacIva), 2); //Obtener Subtotal
                                    dblIva= objUti.redondear(Math.abs(Double.parseDouble(strAbono) - dblSubTot), 2); //Obtener IVA
                                    dblValAbo = Math.abs(Double.parseDouble(strAbono));
                                    dblPorCom = Math.abs(Double.parseDouble(rstCab.getString("nd_porCom") == null ? "0" : rstCab.getString("nd_porCom").equals("") ? "0" : rstCab.getString("nd_porCom")));
                                    dblComFij= 0.00;
                                    
                                    //<editor-fold defaultstate="collapsed"  desc="/* Habilitar en caso de que se vuelva a manejar comisión fija por parte de algún emisor de tarjeta de crédito (Ej.: Banco Guayaquil) */">
                                    //dblComFij= Math.abs(Double.parseDouble(rstCab.getString("nd_valComFij") == null ? "0" : rstCab.getString("nd_valComFij").equals("") ? "0" : rstCab.getString("nd_valComFij")));
                                    //</editor-fold>
                                    
                                    dblValCom = (dblValAbo * dblPorCom) / 100;
                                    dblBasRetFte = dblBasRetFte + dblSubTot;//Obtener Base Imponible I/R 2% (Subtotal)
                                    dblBasRetIva = dblBasRetIva + dblIva;//Obtener Base Imponible IVA 20%(IVA)
                                    strMetRed=rstCab.getString("ne_metRed"); //Metodo de redondeo 1:Truncar 2:Redondear JoséMarín M

                                    if (rstCab.getString("tx_tipcre").equals("C")) //Corriente
                                   {
                                        if(strMetRed.equals("1")) // Metodo de truncamiento
                                        {
                                            dblValCom = dblValAbo * dblPorCom / 100; //Comisión + IvaComisión
                                            dblBasComNet = Truncar((dblBasComNet + (dblValCom / dblFacIva)), 2); //Comisión sin Iva
                                        }
                                        else if(strMetRed.equals("2")) //Metodo de redondeo
                                        {
                                            dblValCom = dblValAbo * dblPorCom / 100;
                                            dblBasComNet = objUti.redondear((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                        }
                                    }
                                    if (rstCab.getString("tx_tipcre").equals("D"))//Diferido
                                    {
                                        if(strMetRed.equals("1")) // Metodo de truncamiento
                                        {
                                            dblValCom = dblValAbo * dblPorCom / 100;
                                            dblBasComNet = Truncar((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                        }
                                        else if(strMetRed.equals("2")) //Metodo de redondeo
                                        {
                                            dblValCom = dblValAbo * dblPorCom / 100;
                                            dblBasComNet = objUti.redondear((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                        }
                                    }
                                     if(strMetRed.equals("1")) // Metodo de truncamiento
                                     {
                                        dblTotValPag = Truncar((dblTotValPag + dblValAbo + dblComFij), 2);
                                        dblTotCom = Truncar((dblTotCom + dblValCom + dblComFij), 2);
                                     }
                                     else if(strMetRed.equals("2")) //Metodo de redondeo
                                     {
                                        dblTotValPag = objUti.redondear((dblTotValPag + dblValAbo + dblComFij), 2);
                                        dblTotCom = objUti.redondear((dblTotCom + dblValCom + dblComFij), 2);
                                     }
                                }
                                //Pulso Genera OD
                                if( objLiberaGuia._getVerificaPagTotFac(conn, strCodEmp, strCodLoc, strCodTipDocRec, strCodDoc  ))
                                {
                                    strSql = " UPDATE tbm_cabguirem SET " +
                                    "  st_aut='A' "+
                                    " ,fe_aut="+objParSis.getFuncionFechaHoraBaseDatos()+" "+
                                    " ,tx_comAut='"+objParSis.getNombreComputadoraConDirIP()+"' "+
                                    " ,co_usrAut="+objParSis.getCodigoUsuario()+" "+
                                    " FROM  ( " +
                                    "  select co_emp, co_loc, co_tipdoc, co_doc from tbm_detguirem where " +
                                    "  co_emprel="+strCodEmp+" and co_locrel="+strCodLoc+"  and  co_tipdocrel="+strCodTipDocRec+" and co_docrel="+strCodDoc+" " +
                                    "  group by co_emp, co_loc, co_tipdoc, co_doc " +
                                    " ) AS x " +
                                    " WHERE x.co_emp= tbm_cabguirem.co_emp and x.co_loc=tbm_cabguirem.co_loc and x.co_tipdoc=tbm_cabguirem.co_tipdoc " +
                                    " and x.co_doc=tbm_cabguirem.co_doc  and  tbm_cabguirem.ne_numdoc=0 and tbm_cabguirem.st_aut='P'  ;   ";
                                    //System.out.println("ZafCxC79.insertarDet._getVerificaPagTotFac (SALE GUIA): " + strSql);
                                    stmLoc.executeUpdate( strSql );

                                    INT_ENV_REC_IMP_GUIA=1;

                                    //generaOD(conn);

                                    vecOD.add(strCodEmp+"-"+strCodLoc+"-"+strCodTipDocRec+"-"+strCodDoc+"-"+strNumDoc);

                                 }
                            }
                        }
                    }
                    //Eddye: Parche temporal hasta que se mejore el código.
                    int intCodTipDocCXCTC=0;
                    if (txtDesCodTitpDoc.getText().equals("COBTCM"))
                    {             intCodTipDocCXCTC=196;                  }
                    else if (txtDesCodTitpDoc.getText().equals("COBTCD"))
                    {             intCodTipDocCXCTC=217;                  }
                    intTipMov = -1;

                    if(strMetRed.equals("1")) // Metodo de truncamiento
                    {
                        dblTotRetFte = Truncar(dblBasRetFte * dblRetFte, 2); //Total Retención I/R
                        dblTotRetIva = Truncar(dblBasRetIva * dblRetIva, 2); //Total Retención IVA
                        //<editor-fold defaultstate="collapsed" desc="/*comentado por que presentaba el valor final incorrecto, se quito el truncamiento*/">
                        //dblTotValCob = Truncar((dblTotValPag - dblTotRetFte - dblTotRetIva - dblTotCom), 2); //Total Valor a Cobrar  
                        //</editor-fold>
                        dblTotValCob = (dblTotValPag - dblTotRetFte - dblTotRetIva - dblTotCom); //Total Valor a Cobrar
                    }
                    else // Metodo de redondeo
                    {
                         dblTotRetFte = objUti.redondear(dblBasRetFte * dblRetFte, 2);//Total Retención I/R
                         dblTotRetIva = objUti.redondear(dblBasRetIva * dblRetIva, 2); //Total Retención IVA
                         dblTotValCob = objUti.redondear((dblTotValPag - dblTotRetFte - dblTotRetIva - dblTotCom), 2);//Total Valor a Cobrar
                    }

                    /* Insertar Detalle para % Retención Fuente I/R (2%) */
                    intCodRegDet++;
                    strSql = "INSERT INTO tbm_pagMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, tx_tipreg, fe_ven, mo_pag, st_reg, nd_porret, nd_basimp)"
                           + "VALUES(" + objParSis.getCodigoEmpresa() + ", " + objParSis.getCodigoLocal() + ", " + intCodTipDocCXCTC + ", " + intCodDoc + " "
                           + ", " + intCodRegDet + ", 'R', '" + strFecVen + "', " + dblTotRetFte + " * " + intTipMov + ", 'A', " + dblPorRetFte+ ", " + dblBasRetFte + " * " + intTipMov + ")";
                    System.out.println("ZafCxC79.insertarDet: " + strSql);
                    stmLoc.executeUpdate(strSql);

                    /* Insertar Detalle para % Retención IVA (20%) */
                    intCodRegDet++;
                    strSql = "INSERT INTO tbm_pagMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, tx_tipreg, fe_ven, mo_pag, st_reg, nd_porret, nd_basimp)"
                           + "VALUES(" + objParSis.getCodigoEmpresa() + ", " + objParSis.getCodigoLocal() + ", " + intCodTipDocCXCTC + ", " + intCodDoc + " "
                           + ", " + intCodRegDet + ", 'R', '" + strFecVen + "', " + dblTotRetIva + " * " + intTipMov + ", 'A', " + dblPorRetIva+ ", " + dblBasRetIva + " * " + intTipMov + ")";
                    //System.out.println("ZafCxC79.insertarDet: " + strSql);
                    stmLoc.executeUpdate(strSql);

                    /* Insertar Detalle para Comisión */
                    intCodRegDet++;
                    strSql = "INSERT INTO tbm_pagMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, tx_tipreg, fe_ven, mo_pag, st_reg, tx_aplRet, nd_basimp)"
                           + "VALUES(" + objParSis.getCodigoEmpresa() + ", " + objParSis.getCodigoLocal() + ", " + intCodTipDocCXCTC + ", " + intCodDoc + " "
                           + ", " + intCodRegDet + ", 'C', '" + strFecVen + "', " + dblTotCom + " * " + intTipMov + ", 'A', 'O', " + dblBasComNet + ")";
                    //System.out.println("ZafCxC79.insertarDet: " + strSql);
                    stmLoc.executeUpdate(strSql);

                    /* Insertar Detalle para Valor A Cobrar */
                    intCodRegDet++;
                    strSql = "INSERT INTO tbm_pagMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, tx_tipreg, fe_ven, mo_pag, st_reg)"
                           + "VALUES(" + objParSis.getCodigoEmpresa() + ", " + objParSis.getCodigoLocal() + ", " + intCodTipDocCXCTC + ", " + intCodDoc + " "
                           + ", " + intCodRegDet + ", 'V', '" + strFecVen + "', " + dblTotValCob + " * " + intTipMov + ", 'A')";
                    //System.out.println("ZafCxC79.insertarDet: " + strSql);
                    stmLoc.executeUpdate(strSql);

                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            }
            catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
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
 }
 catch(Exception e){
        objUti.mostrarMsgErr_F1(this, e);
 }
    return dlbRes;
}


        /**
         * Permite recargar los datos de la tabla despues de insertar o
         * modificar con objetivo de tener ejem: codigo del registro que eso da
         * cuando se insertar
         *
         * @param conn coneccion de la base
         * @param intCodDoc codigo del documento
         * @return true si se consulto bien false si hay algun error.
         */
        private boolean cargarDetIns(java.sql.Connection conn, int intCodDoc) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            Vector vecData;
            try {
                //System.out.println("ZafCxC79.cargarDetIns: ");
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    vecData = new Vector();

                    strSql = "select a2.co_cli, a2.tx_nomcli, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a3.tx_descor, a3.tx_deslar,a2.co_tipCre, "
                            + " a2.ne_numdoc, a2.fe_doc ,a1.ne_diacre, a1.fe_ven, a1.nd_porret, a1.mo_pag, (a1.mo_pag + a1.nd_abo)  as valpen, a.nd_abo, a.co_reg as coregpag, a.tx_numtra, a.tx_numaut"
                            + " from tbm_detpag as a  "
                            + " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locpag and a1.co_tipdoc=a.co_tipdocpag and a1.co_doc=a.co_docpag and a1.co_reg=a.co_regpag )  "
                            + " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc )  "
                            + " inner join tbm_cabtipdoc as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc  )    "
                            + " WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + " and a.co_loc=" + objParSis.getCodigoLocal() + "  and "
                            + " a.co_tipdoc=" + txtCodTipDoc.getText() + "  and a.co_Doc=" + intCodDoc + "  and a.st_reg='A'  ORDER BY a.co_reg  ";
                    //System.out.println("ZafCxC79.cargarDetIns: " + strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    while (rstLoc.next()) {

                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_BUTCLI, "..");
                        vecReg.add(INT_TBL_CHKSEL, new Boolean(true));
                        vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli"));
                        vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nomcli"));
                        vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp"));
                        vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc"));
                        vecReg.add(INT_TBL_CODTID, rstLoc.getString("co_tipdoc"));
                        vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc"));
                        vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                        vecReg.add(INT_TBL_DCTIPDOC, rstLoc.getString("tx_descor"));
                        vecReg.add(INT_TBL_DLTIPDOC, rstLoc.getString("tx_deslar"));
                        vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc"));
                        vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc"));
                        vecReg.add(INT_TBL_DIACRE, rstLoc.getString("ne_diacre"));
                        vecReg.add(INT_TBL_FECVEN, rstLoc.getString("fe_ven"));
                        vecReg.add(INT_TBL_PORRET, rstLoc.getString("nd_porret"));
                        vecReg.add(INT_TBL_VALDOC, rstLoc.getString("mo_pag"));
                        vecReg.add(INT_TBL_VALPEN, rstLoc.getString("valpen"));
                        vecReg.add(INT_TBL_NUMTRA, rstLoc.getString("tx_numtra"));
                        vecReg.add(INT_TBL_NUMAUT, rstLoc.getString("tx_numaut"));
                        vecReg.add(INT_TBL_ABONO, rstLoc.getString("nd_abo"));
                        vecReg.add(INT_TBL_CODREGEFE, rstLoc.getString("coregpag"));
                        vecReg.add(INT_TBL_ABONOORI, rstLoc.getString("nd_abo"));
                        vecReg.add(INT_TBL_BUTFAC, "..");
                        vecReg.add(INT_TBL_TIPCRE, "co_tipCre");

                        vecData.add(vecReg);
                    }
                    rstLoc.close();
                    rstLoc = null;

                    objTblMod.setData(vecData);
                    tblDat.setModel(objTblMod);

                    /**
                     * ************************************************
                     */
                    stmLoc.close();
                    stmLoc = null;

                    blnRes = true;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        /**
         * Funcion que permite saber si el documento esta Impreso
         *
         * @param conn Coneccion de la base
         * @param intCodEmp Codigo de la empresa
         * @param intCodLoc Codigo del local
         * @param intCodTipDoc Codigo del tipo del documento
         * @param intCodDoc Codigo documento
         * @return true no esta impreso false si esta impreso
         */
        private boolean _getEstImp(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc) {
            boolean blnRes = true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try {
                //System.out.println("ZafCxC79._getEstImp: ");
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    if (intTipModDoc == 1) {
                        blnRes = false;
                        MensajeInf("NO TIENE ACCESO A MODIFICAR EL DOCUMENTO.. ");
                    } else if (intTipModDoc == 2) {

                        strSql = "SELECT st_imp FROM tbm_cabpag WHERE  co_emp=" + intCodEmp + " AND co_loc=" + intCodLoc + " AND co_tipdoc=" + intCodTipDoc + ""
                                + " AND co_doc=" + intCodDoc + "  and st_imp='S'";
                        rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) {
                            blnRes = false;
                            MensajeInf("EL DOCUMENTO YA ESTA IMPRESO Y NO SE PUEDE MODIFICAR ..");
                        }
                        rstLoc.close();
                        rstLoc = null;

                    } else if (intTipModDoc == 3) {
                        // SI PERMITE MODIFCAR
                    }


                    stmLoc.close();
                    stmLoc = null;

                }
            } catch (java.sql.SQLException ex) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, ex);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean _getEstCer(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc) {
            boolean blnRes = true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try {
                //System.out.println("ZafCxC79._getEstCer: ");
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    if (intTipModDoc == 1) {
                        blnRes = false;
                        MensajeInf("NO TIENE ACCESO A MODIFICAR EL DOCUMENTO.. ");
                    } else if (intTipModDoc == 2) {

                        strSql = "SELECT st_ciecobtarcre FROM tbm_cabpag WHERE  co_emp=" + intCodEmp + " AND co_loc=" + intCodLoc + " AND co_tipdoc=" + intCodTipDoc + ""
                                + " AND co_doc=" + intCodDoc + "  and st_ciecobtarcre ='S'";
                        rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) {
                            blnRes = false;
                            MensajeInf("EL DOCUMENTO ESTA CERRADO, NO SE PUEDE MODIFICAR/ANULAR/ELIMINAR ..");
                        }
                        rstLoc.close();
                        rstLoc = null;

                    } else if (intTipModDoc == 3) {
                        // SI PERMITE MODIFCAR
                    }


                    stmLoc.close();
                    stmLoc = null;

                }
            } catch (java.sql.SQLException ex) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, ex);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        public boolean modificar() {
            boolean blnRes = false;
            java.sql.Connection conn;
            int intCodDoc = 0;
            int intCodTipDoc = 0;
            String strAux = "";
            ZafGenOdPryTra genODTra=new ZafGenOdPryTra();
            ZafCnfDoc objValCnf=new ZafCnfDoc(objParSis,this);
            
            try {
                strAux = objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado")) {
                    MensajeInf("El documento está ELIMINADO.\nNo es posible modifcar un documento eliminado.");
                    return false;
                }
                if (strAux.equals("Anulado")) {
                    MensajeInf("El documento ya está ANULADO.\nNo es posible modifcar un documento anulado.");
                    return false;
                }


                INT_ENV_REC_IMP_GUIA = 0;


                if (validaCampos()) {

                    conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (conn != null) {
                        conn.setAutoCommit(false);
                        intCodDoc = Integer.parseInt(txtCodDoc.getText());
                        intCodTipDoc = Integer.parseInt(txtCodTipDoc.getText());
                        if (obtenerEstAnu(conn, intCodTipDoc, intCodDoc)) {
                            if (_getEstImp(conn, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodTipDoc, intCodDoc)) {
                                if (_getEstCer(conn, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodTipDoc, intCodDoc)) {
                                    strSqlInsDet = new StringBuffer();
                                    if (modificarCab(conn, intCodTipDoc, intCodDoc)) {
                                        if (EliminadosRegCobEfe(conn, intCodTipDoc, intCodDoc)) {

                                            if (modificarDet(conn, intCodTipDoc, intCodDoc)) {
                                                if (objAsiDia.actualizarDiario(conn, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodTipDoc, intCodDoc, txtCodDoc.getText(), objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy"), "A"))
                                                {
                                                    //Eddye: Parche temporal hasta que se mejore el código.
                                                    int intCodTipDocCXCTC=0;
                                                    if (txtDesCodTitpDoc.getText().equals("COBTCM"))
                                                    {
                                                        intCodTipDocCXCTC=196;
                                                    }
                                                    else if (txtDesCodTitpDoc.getText().equals("COBTCD"))
                                                    {
                                                        intCodTipDocCXCTC=217;
                                                    }
                                                    if (objAsiDia2.actualizarDiario(conn, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodTipDocCXCTC, intCodDoc, txtCodDoc.getText(), objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy"), "A"))
                                                    {
                                                        if (objAjuCenAut.realizaAjuCenAut(conn, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), intCodDoc, 80, objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy")))
                                                        {
                                                            conn.commit();
                                                            blnRes = true;
                                                            cargarDetIns(conn, intCodDoc);

                                                            if (INT_ENV_REC_IMP_GUIA == 1)
                                                            {
                                                                //Luis Parrales, aqui se invoca el hilo
                                                                boolean resp=false;
                                                                ZafMetImp om;

                                                                Iterator itVecOD=vecOD.iterator();
                                                                //for(int i=0; i<vecOD.size();i++){
                                                                while(itVecOD.hasNext()){
                                                                   //String strDat=(String)vecOD.get(i);
                                                                   String strDat=(String)itVecOD.next();
                                                                   String[] strArrDat=strDat.split("-");

                                                                   int intCodEmp=Integer.parseInt((String)strArrDat[0]);
                                                                   int intCodLoc=Integer.parseInt((String)strArrDat[1]);
                                                                   int intCodTipDoc2=Integer.parseInt((String)strArrDat[2]);
                                                                   int intCodDocu=Integer.parseInt((String)strArrDat[3]);
                                                                   /*int intNumDoc=Integer.parseInt((String)strArrDat[4]);

                                                                   System.out.println("factura "+intCodEmp+"-"+intCodLoc+"-"+intCodTipDoc2+"-"+intCodDocu);

                                                                   ZafImp oz=new ZafImp();

                                                                    oz.setEmp(intCodEmp);
                                                                    oz.setLoc(intCodLoc);
                                                                    oz.setNumdoc(intNumDoc);
                                                                    oz.setTipdoc(intCodTipDoc2);
                                                                    oz.setCoDoc(intCodDocu);
                                                                    om=new ZafMetImp(oz);
                                                                    resp=om.validarOD(conn);
                                                                    if (resp==false) {
                                                                        om.impresionNormal2(conn);
                                                                    }
                                                                    boolean booRetTer=objZafGuiRem.generarProTermL(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, intNumDoc);*/
                                                                   if(!objValCnf.isDocIngPenCnfxFac(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, "I")){
                                                                        if(!(genODTra.validarODExs(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu))){
                                                                            if(genODTra.generarNumOD(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, false)){
                                                                                conn.commit();
                                                                                String strIp=genODTra.obtenerIpSerImp(conn);
                                                                                genODTra.imprimirOdLocal(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, strIp);                                                
                                                                                genODTra.generarTermL(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu);
                                                                            }else{
                                                                                conn.rollback();
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                vecOD.clear();
                                                            }
                                                        }
                                                        else
                                                        {
                                                            conn.rollback();
                                                        }
                                                    }
                                                    else
                                                    {
                                                        conn.rollback();
                                                    }
                                                } else {
                                                    conn.rollback();
                                                }
                                            } else {
                                                conn.rollback();
                                            }
                                        } else {
                                            conn.rollback();
                                        }
                                    } else {
                                        conn.rollback();
                                    }
                                    strSqlInsDet = null;
                                }

                            }
                        }

                        conn.close();
                        conn = null;
                    }

                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        /* Función que permite Eliminar el Detalle del Cobro de Tarjeta de Crédito */ 
        public boolean EliminadosRegCobEfe(java.sql.Connection conn, int intCodTipDoc, int intCodDoc)
        {
            boolean blnRes = true;
            java.sql.Statement stmLoc, stmLoc01;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            String strCodEmp = "", strCodLoc = "", strCodTipDocRec = "", strCodDoc = "", strCodReg = "";
            String strAbono="0", strValPen="",  strMetRed="";
            double dblSubTot=0,	dblIva=0, dblPorCom=0, dblValPen=0, dblValAbo=0, dblPorRetFte=2.000, dblPorRetIva=20.000 ;
            double dblBasComNet=0,  dblBasRetFte=0, dblBasRetIva=0,  dblRetFte=0.02,  dblRetIva=0.20, dblValCom=0.00, dblComFij=0.00;
            double dblTotValCob=0, dblTotValPag=0, dblTotCom=0,  dblTotRetFte=0, dblTotRetIva=0 ;
            int intTipMov = 1; //Artificio para saber si es valor abonado se guardará con negativo o positivo.

            try
            {
                //System.out.println("ZafCxC79.EliminadosRegCobEfe: ");
                if (conn != null)
                {
                    stmLoc = conn.createStatement();
                    stmLoc01 = conn.createStatement();
                    java.util.ArrayList arlAux = objTblMod.getDataSavedBeforeRemoveRow();
                    if (arlAux != null)
                    {
                        for (int i = 0; i < arlAux.size(); i++)
                        {
                            int intCodReg = objUti.getIntValueAt(arlAux, i, INT_ARR_CODREG);
                            strSql = "SELECT co_emp, co_locpag, co_tipdocpag, co_docpag, co_regpag , nd_abo FROM tbm_detpag "
                                    + " WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + " "
                                    + " AND co_tipdoc=" + intCodTipDoc + " AND co_doc= " + intCodDoc + " AND co_reg=" + intCodReg + " ";
                            //System.out.println("ZafCxC79.EliminadosRegCobEfe: " + strSql);
                            rstLoc = stmLoc.executeQuery(strSql);
                            if (rstLoc.next())
                            {
                                strCodEmp = rstLoc.getString("co_emp");
                                strCodLoc = rstLoc.getString("co_locpag");
                                strCodTipDocRec = rstLoc.getString("co_tipdocpag");
                                strCodDoc = rstLoc.getString("co_docpag");
                                strCodReg = rstLoc.getString("co_regpag");
                                strSql  = "UPDATE tbm_detpag SET st_reg='E' WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND"
                                        + " co_loc=" + objParSis.getCodigoLocal() + " AND co_tipdoc=" + intCodTipDoc + " AND"
                                        + "  =" + intCodDoc + " and co_reg=" + intCodReg + " ; ";
                                strSql += " UPDATE tbm_pagmovinv SET  nd_abo = nd_abo - " + rstLoc.getString("nd_abo") + " "
                                        + " WHERE co_emp=" + strCodEmp + " AND co_loc=" + strCodLoc + " AND co_tipdoc=" + strCodTipDocRec + " "
                                        + " AND co_doc=" + strCodDoc + " AND co_reg=" + strCodReg + " ";
                                //System.out.println("ZafCxC79.EliminadosRegCobEfe: " + strSql);
                                stmLoc.executeUpdate(strSql);

                                strSql=" SELECT a1.nd_sub, a1.nd_tot, a2.nd_porCom, a2.tx_tipCre, a1.co_tipCre,";
                                strSql+=" CASE WHEN a2.nd_valComFij IS NULL THEN 0 ELSE a2.nd_valComFij END AS nd_valComFij, a2.ne_metRed ";
                                strSql+=" FROM tbm_cabMovInv AS a1 ";
                                strSql+=" INNER JOIN tbm_tipCreRedTarCre AS a2 ON (a1.co_emp = a2.co_emp AND a1.co_tipcre=a2.co_tipcre) ";
                                strSql+=" WHERE a1.co_emp = " + strCodEmp + " AND a1.co_loc = " + strCodLoc + " ";
                                strSql+=" AND a1.co_tipdoc = " + strCodTipDocRec + " and a1.co_doc = " + strCodDoc + "";
                                //System.out.println("ZafCxC79.EliminadosRegCobEfe: " + strSql);
                                rstCab = stmLoc.executeQuery(strSql);

                                strValPen = (tblDat.getValueAt(i, INT_TBL_VALPEN) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_VALPEN).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_VALPEN).toString()));
                                dblValPen = objUti.redondear(strValPen, 2);
                                if (dblValPen > 0)
                                {   intTipMov = -1;     }
                                else
                                {   intTipMov =  1;     }

                                while (rstCab.next())
                                {
                                    dblSubTot= objUti.redondear(Math.abs(Double.parseDouble(strAbono) / dblFacIva), 2); //Obtener Subtotal
                                    dblIva= objUti.redondear(Math.abs(Double.parseDouble(strAbono) - dblSubTot), 2); //Obtener IVA
                                    dblValAbo = Math.abs(Double.parseDouble(strAbono));
                                    dblPorCom = Math.abs(Double.parseDouble(rstCab.getString("nd_porCom") == null ? "0" : rstCab.getString("nd_porCom").equals("") ? "0" : rstCab.getString("nd_porCom")));
                                    dblValCom = (dblValAbo * dblPorCom) / 100;
                                    dblComFij= 0.00;
                                    
                                    //<editor-fold defaultstate="collapsed"  desc="/* Habilitar en caso de que se vuelva a manejar comisión fija por parte de algún emisor de tarjeta de crédito (Ej.: Banco Guayaquil) */">
                                    //dblComFij= Math.abs(Double.parseDouble(rstCab.getString("nd_valComFij") == null ? "0" : rstCab.getString("nd_valComFij").equals("") ? "0" : rstCab.getString("nd_valComFij")));
                                    //</editor-fold>
                                    
                                    strMetRed=rstCab.getString("ne_metRed"); //Metodo de redondeo 1:Truncar 2:Redondear JoséMarín M
                                    dblBasRetFte = dblBasRetFte + dblSubTot;//Obtener Base Imponible I/R 2% (Subtotal)
                                    dblBasRetIva = dblBasRetIva + dblIva;//Obtener Base Imponible IVA 20%(IVA) 

                                    if (rstCab.getString("tx_tipcre").equals("C"))
                                    {
                                        if(strMetRed.equals("1")) // Metodo de truncamiento
                                        {
                                            dblValCom = dblValAbo * dblPorCom / 100 ;
                                            dblBasComNet = Truncar((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                        }
                                        else if(strMetRed.equals("2")) //Metodo de redondeo
                                        {
                                            dblValCom = dblValAbo * dblPorCom /  100 ;
                                            dblBasComNet = objUti.redondear((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                        }
                                    }
                                    if (rstCab.getString("tx_tipcre").equals("D"))
                                    {
                                        if(strMetRed.equals("1")) // Metodo de truncamiento
                                        {
                                            dblValCom = dblValAbo * dblPorCom  / 100 ;
                                            dblBasComNet = Truncar((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                        }
                                        else if(strMetRed.equals("2")) //Metodo de redondeo
                                        {
                                            dblValCom = dblValAbo * dblPorCom / 100;
                                            dblBasComNet = objUti.redondear((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                        }
                                    }
                                     if(strMetRed.equals("1")) // Metodo de truncamiento
                                     {
                                        dblTotValPag = Truncar((dblTotValPag + dblValAbo + dblComFij), 2);
                                        dblTotCom = Truncar((dblTotCom + dblValCom + dblComFij), 2);
                                     }
                                     else if(strMetRed.equals("2")) //Metodo de redondeo
                                     {
                                        dblTotValPag = objUti.redondear((dblTotValPag + dblValAbo + dblComFij), 2);
                                        dblTotCom = objUti.redondear((dblTotCom + dblValCom + dblComFij), 2);
                                     }
                                }
                            }
                            else
                            {
                                MensajeInf(" NO ELIMINADO... > ");
                            }
                            rstLoc.close();
                            rstLoc = null;
                        }
                        //Eddye: Parche temporal hasta que se mejore el código.
                        int intCodTipDocCXCTC=0;
                        if (txtDesCodTitpDoc.getText().equals("COBTCM"))
                        {              intCodTipDocCXCTC=196;               }
                        else if (txtDesCodTitpDoc.getText().equals("COBTCD"))
                        {              intCodTipDocCXCTC=217;               }
                        intTipMov = -1;

                        if(strMetRed.equals("1")) // Metodo de truncamiento
                        {
                              dblTotRetFte= Truncar(dblBasRetFte * (dblRetFte), 2); //Total Retención I/R
                              dblTotRetIva= Truncar(dblBasRetIva * (dblRetIva), 2); //Total Retención IVA
                              dblTotValCob = Truncar((dblTotValPag - dblTotRetFte - dblTotRetIva - dblTotCom), 2); //Total Valor a Cobrar
                        }
                        else // Metodo de redondeo
                        {
                              dblTotRetFte= objUti.redondear(dblBasRetFte * (dblRetFte), 2); //Total Retención I/R
                              dblTotRetIva= objUti.redondear(dblBasRetIva * (dblRetIva), 2); //Total Retención IVA
                              dblTotValCob = objUti.redondear((dblTotValPag - dblTotRetFte - dblTotRetIva - dblTotCom), 2); //Total Valor a Cobrar
                        }

                        /* Actualiza Detalle para % Retención Fuente I/R (2%) */
                        strSql = " UPDATE tbm_pagmovinv set mo_pag = mo_pag - " + dblTotRetFte + " * " + intTipMov + ", "
                               + " nd_basimp = nd_basimp - " + dblBasRetFte + " * " + intTipMov + ", "
                               + " st_regrep = 'M' WHERE "
                               + " co_emp = " + objParSis.getCodigoEmpresa() + " and co_loc = " + objParSis.getCodigoLocal() + " and co_tipdoc = " + intCodTipDocCXCTC + " and co_doc =" + intCodDoc + " "
                                + " AND tx_tipreg = 'R' AND nd_porret="+dblPorRetFte +" ; ";
                        stmLoc.executeUpdate(strSql);

                        /* Actualiza Detalle para % Retención IVA (20%) */
                          strSql = " UPDATE tbm_pagmovinv set mo_pag = mo_pag - " + dblTotRetIva + " * " + intTipMov + ", "
                               + " nd_basimp = nd_basimp - " + dblBasRetIva + " * " + intTipMov + ", "
                               + " st_regrep = 'M' WHERE "
                               + " co_emp = " + objParSis.getCodigoEmpresa() + " and co_loc = " + objParSis.getCodigoLocal() + " and co_tipdoc = " + intCodTipDocCXCTC + " and co_doc =" + intCodDoc + " "
                               + " AND tx_tipreg = 'R' AND nd_porret="+dblPorRetIva +" ; ";
                        stmLoc.executeUpdate(strSql);

                        /* Actualiza Detalle para Comisión */
                        strSql = " UPDATE tbm_pagmovinv set mo_pag = mo_pag - " + dblTotCom + " * " + intTipMov + ", "
                               + " nd_basimp = nd_basimp - " + dblBasComNet + ", "
                               + " st_regrep = 'M' WHERE "
                               + " co_emp = " + objParSis.getCodigoEmpresa() + " and co_loc = " + objParSis.getCodigoLocal() + " and co_tipdoc = " + intCodTipDocCXCTC + " and co_doc = " + intCodDoc + " "
                               + " AND tx_tipreg = 'C' ; ";
                        stmLoc.executeUpdate(strSql);

                        /* Actualiza Detalle para Valor A Cobrar */
                        strSql = " UPDATE tbm_pagmovinv set mo_pag = mo_pag - " + dblTotValCob + " * " + intTipMov + ", "
                               + " st_regrep = 'M' WHERE "
                               + " co_emp = " + objParSis.getCodigoEmpresa() + " and co_loc = " + objParSis.getCodigoLocal() + " and co_tipdoc = " + intCodTipDocCXCTC + " and co_doc = " + intCodDoc + " "
                               + " AND tx_tipreg = 'V' ; ";
                        stmLoc.executeUpdate(strSql);
                    }
                    stmLoc.close();
                    stmLoc = null;
                    stmLoc01.close();
                    stmLoc01 = null;
                }
            }
            catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        /* Función que permite Modificar el Detalle del Cobro de Tarjeta de Crédito */ 
        private boolean modificarDet(java.sql.Connection conn, int intCodTipDoc, int intCodDoc)
        {
            boolean blnRes = true;
            java.sql.Statement stmLoc;
            String strSql = "";
            String strCodEmp = "", strCodLoc = "", strCodTipDocRec = "", strCodDoc = "", strCodReg = "",strNumDoc= "";
            String strAbono="0", strValPen="", strNumTra="", strNumAut="", strTipCre="", strMetRed="";
            double dblSubTot=0,	dblIva=0, dblPorCom=0, dblValPen=0, dblValAbo=0, dblPorRetFte=2.00, dblPorRetIva=20.00 ;;
            double dblBasComNet=0,  dblBasRetFte=0, dblBasRetIva=0,  dblRetFte=0.02,  dblRetIva=0.20, dblValCom=0.00, dblComFij=0.00;
            double dblTotValCob=0, dblTotValPag=0, dblTotCom=0,  dblTotRetFte=0, dblTotRetIva=0;
            int intTipMov = 1; //Artificio para saber si es valor abonado se guardará con negativo o positivo.
            int intCodRegDet = 0; //Código de registro.
            int intEstMod = 0;
            vecOD.clear();

            try
            {
                //System.out.println("ZafCxC79.modificarDet: ");
                if (conn != null)
                {
                    stmLoc = conn.createStatement();
                    intCodRegDet = _getObtenerMaxCodRegDet(conn, intCodTipDoc, intCodDoc);

                    for (int i = 0; i < tblDat.getRowCount(); i++)
                    {
                        if (((tblDat.getValueAt(i, INT_TBL_CODREGEFE) == null ? "" : tblDat.getValueAt(i, INT_TBL_CODREGEFE).toString()).equals("")))
                        {
                            if (!((tblDat.getValueAt(i, INT_TBL_CODCLI) == null ? "" : (tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("") ? "" : tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")))
                            {
                                if (((tblDat.getValueAt(i, INT_TBL_CHKSEL) == null ? "false" : (tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("") ? "false" : tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")))
                                {
                                    strCodEmp = tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
                                    strCodLoc = tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
                                    strCodTipDocRec = tblDat.getValueAt(i, INT_TBL_CODTID).toString();
                                    strCodDoc = tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
                                    /*MODIFICADO PARA NUEVO SERVICIO 1*/
                                    strNumDoc = tblDat.getValueAt(i, INT_TBL_NUMDOC).toString();
                                    /*MODIFICADO PARA NUEVO SERVICIO 1*/

                                    strCodReg = tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                                    strNumTra = (tblDat.getValueAt(i, INT_TBL_NUMTRA) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_NUMTRA).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_NUMTRA).toString()));
                                    strNumAut = (tblDat.getValueAt(i, INT_TBL_NUMAUT) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_NUMAUT).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_NUMAUT).toString()));
                                    strAbono = (tblDat.getValueAt(i, INT_TBL_ABONO) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_ABONO).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_ABONO).toString()));
                                    strTipCre = tblDat.getValueAt(i, INT_TBL_TIPCRE).toString();
                                    intEstMod = 1;

                                    strValPen = (tblDat.getValueAt(i, INT_TBL_VALPEN) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_VALPEN).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_VALPEN).toString()));
                                    dblValPen = objUti.redondear(strValPen, 2);
                                    if (dblValPen > 0)
                                    {       intTipMov = -1;          }
                                    else
                                    {       intTipMov =  1;          }

                                    /* Almacena los pagos realizados en el Local que se realiza el Cobro con TC para la factura correspondiente.
                                     * Adicional enlaza el Local de donde se realizo la factura de venta.*/
                                    strSql = " INSERT INTO tbm_detpag( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag,"
                                            + " co_docpag, co_regpag, nd_abo,  st_reg, st_regrep, tx_numtra, tx_numaut )"
                                            + " VALUES( " + objParSis.getCodigoEmpresa() + ", " + objParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                            + " ," + intCodRegDet + ", " + strCodLoc + ", " + strCodTipDocRec + ", " + strCodDoc + ", " + strCodReg + ", " + strAbono + "  * " + intTipMov + " , 'A','I'," + objUti.codificar(strNumTra) + "," + objUti.codificar(strNumAut) + " ) ; ";  //);
                                    //System.out.println("ZafCxC79.modificarDet: " + strSql);
                                    stmLoc.executeUpdate(strSql);

                                    /* Actualiza el Valor de Abono en la Factura en el Local donde se realizo la factura de venta*/
                                    intCodRegDet++;
                                    strSql = " UPDATE tbm_pagmovinv set nd_abo=nd_abo + " + strAbono + " * " + intTipMov + ", "
                                            + " st_regrep='M' WHERE "
                                            + " co_emp=" + strCodEmp + " and co_loc=" + strCodLoc + " and co_tipdoc=" + strCodTipDocRec + " and co_doc=" + strCodDoc + " "
                                            + " and co_reg=" + strCodReg + " ; ";
                                    //System.out.println("ZafCxC79.modificarDet: " + strSql);
                                    stmLoc.executeUpdate(strSql);

                                    /* Consulta Datos de la Factura de Ventas para utilizar valores en calculos de retenciones, comisión, etc. */
                                    strSql=" SELECT a1.nd_sub, a1.nd_tot, a2.nd_porCom, a2.tx_tipCre, a1.co_tipCre,";
                                    strSql+=" CASE WHEN a2.nd_valComFij IS NULL THEN 0 ELSE a2.nd_valComFij END AS nd_valComFij, a2.ne_metRed ";
                                    strSql+=" FROM tbm_cabMovInv AS a1 ";
                                    strSql+=" INNER JOIN tbm_tipCreRedTarCre AS a2 ON (a1.co_emp = a2.co_emp AND a1.co_tipcre=a2.co_tipcre) ";
                                    strSql+=" WHERE a1.co_emp = " + strCodEmp + " AND a1.co_loc = " + strCodLoc + " ";
                                    strSql+=" AND a1.co_tipdoc = " + strCodTipDocRec + " and a1.co_doc = " + strCodDoc + "";
                                    //System.out.println("ZafCxC79.modificarDet: " + strSql);
                                    rstCab = stmLoc.executeQuery(strSql);

                                    while (rstCab.next())
                                    {
                                        dblSubTot= objUti.redondear(Math.abs(Double.parseDouble(strAbono) / dblFacIva), 2); //Obtener Subtotal
                                        dblIva= objUti.redondear(Math.abs(Double.parseDouble(strAbono) - dblSubTot), 2); //Obtener IVA
                                        dblValAbo = Math.abs(Double.parseDouble(strAbono));
                                        dblPorCom = Math.abs(Double.parseDouble(rstCab.getString("nd_porCom") == null ? "0" : rstCab.getString("nd_porCom").equals("") ? "0" : rstCab.getString("nd_porCom")));
                                        dblValCom = (dblValAbo * dblPorCom) / 100;
                                        dblComFij= 0.00;
                                    
                                        //<editor-fold defaultstate="collapsed"  desc="/* Habilitar en caso de que se vuelva a manejar comisión fija por parte de algún emisor de tarjeta de crédito (Ej.: Banco Guayaquil) */">
                                        //dblComFij= Math.abs(Double.parseDouble(rstCab.getString("nd_valComFij") == null ? "0" : rstCab.getString("nd_valComFij").equals("") ? "0" : rstCab.getString("nd_valComFij")));
                                        //</editor-fold>
                                        
                                        dblBasRetFte = dblBasRetFte + dblSubTot;//Obtener Base Imponible I/R 2% (Subtotal)
                                        dblBasRetIva = dblBasRetIva + dblIva;//Obtener Base Imponible IVA 20%(IVA)   
                                        strMetRed=rstCab.getString("ne_metRed"); //Metodo de redondeo 1:Truncar 2:Redondear JoséMarín M

                                        if (rstCab.getString("tx_tipcre").equals("C"))
                                        {
                                        if(strMetRed.equals("1")) // Metodo de truncamiento
                                        {
                                            dblValCom = dblValAbo * dblPorCom / 100 ;
                                            dblBasComNet = Truncar((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                        }
                                        else if(strMetRed.equals("2")) //Metodo de redondeo
                                        {
                                            dblValCom = dblValAbo * dblPorCom / 100 ;
                                            dblBasComNet = objUti.redondear((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                        }
                                    }
                                    if (rstCab.getString("tx_tipcre").equals("D"))
                                    {
                                        if(strMetRed.equals("1")) // Metodo de truncamiento
                                        {
                                            dblValCom = dblValAbo * dblPorCom / 100 ;
                                            dblBasComNet = Truncar((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                        }
                                        else if(strMetRed.equals("2")) //Metodo de redondeo
                                        {
                                            dblValCom = dblValAbo * dblPorCom / 100 ;
                                            dblBasComNet = objUti.redondear((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                        }
                                    }
                                     if(strMetRed.equals("1")) // Metodo de truncamiento
                                     {
                                        dblTotValPag = Truncar((dblTotValPag + dblValAbo + dblComFij), 2);
                                        dblTotCom = Truncar((dblTotCom + dblValCom + dblComFij), 2);
                                     }
                                     else if(strMetRed.equals("2")) //Metodo de redondeo
                                     {
                                        dblTotValPag = objUti.redondear((dblTotValPag + dblValAbo + dblComFij), 2);
                                        dblTotCom = objUti.redondear((dblTotCom + dblValCom + dblComFij), 2);
                                     }
                                    }
                                    //Pulso Genera OD
                                    if (objLiberaGuia._getVerificaPagTotFac(conn, strCodEmp, strCodLoc, strCodTipDocRec, strCodDoc))
                                    {
                                        strSql = " UPDATE tbm_cabguirem SET "
                                                + "  st_aut='A' "
                                                + " ,tx_obsAut=' AUTOMATICO '  "
                                                + " ,fe_aut=" + objParSis.getFuncionFechaHoraBaseDatos() + " "
                                                + " ,tx_comAut='" + objParSis.getNombreComputadoraConDirIP() + "' "
                                                + " ,co_usrAut=" + objParSis.getCodigoUsuario() + " "
                                                + " FROM  ( "
                                                + "  select co_emp, co_loc, co_tipdoc, co_doc from tbm_detguirem where "
                                                + "  co_emprel=" + strCodEmp + " and co_locrel=" + strCodLoc + "  and  co_tipdocrel=" + strCodTipDocRec + " and co_docrel=" + strCodDoc + " "
                                                + "  group by co_emp, co_loc, co_tipdoc, co_doc "
                                                + " ) AS x "
                                                + " WHERE x.co_emp= tbm_cabguirem.co_emp and x.co_loc=tbm_cabguirem.co_loc and x.co_tipdoc=tbm_cabguirem.co_tipdoc "
                                                + " and x.co_doc=tbm_cabguirem.co_doc and  tbm_cabguirem.ne_numdoc=0 and tbm_cabguirem.st_aut='P' ;  ";
                                        stmLoc.executeUpdate(strSql);

                                        INT_ENV_REC_IMP_GUIA = 1;

                                        vecOD.add(strCodEmp+"-"+strCodLoc+"-"+strCodTipDocRec+"-"+strCodDoc+"-"+strNumDoc);


                                    }

                                }
                            }
                        }
                    }
                    //Eddye: Parche temporal hasta que se mejore el código.
                    int intCodTipDocCXCTC=0;
                    if (txtDesCodTitpDoc.getText().equals("COBTCM"))
                    {        intCodTipDocCXCTC=196;                     }
                    else if (txtDesCodTitpDoc.getText().equals("COBTCD"))
                    {        intCodTipDocCXCTC=217;                     }
                    intTipMov = -1;

                    if(strMetRed.equals("1")) // Metodo de truncamiento
                    {
                         dblTotRetFte = Truncar(dblBasRetFte * (dblRetFte), 2); //Total Retención I/R
                         dblTotRetIva = Truncar(dblBasRetIva * (dblRetIva), 2); //Total Retención IVA
                         dblTotValCob = Truncar((dblTotValPag - dblTotRetFte - dblTotRetIva - dblTotCom), 2); //Valor A Cobrar
                    }
                    else // Metodo de redondeo
                    {
                         dblTotRetFte= objUti.redondear(dblBasRetFte * (dblRetFte), 2); //Total Retención I/R
                         dblTotRetIva= objUti.redondear(dblBasRetIva * (dblRetIva), 2); //Total Retención IVA
                         dblTotValCob = objUti.redondear((dblTotValPag - dblTotRetFte - dblTotRetIva - dblTotCom), 2); //Total Valor a Cobrar
                    }

                    /* Actualiza Detalle para % Retención Fuente I/R (2%) */
                    strSql = " UPDATE tbm_pagmovinv set mo_pag = mo_pag + " + dblTotRetFte + " * " + intTipMov + ", "
                           + " nd_basimp = nd_basimp + " + dblBasRetFte + " * " + intTipMov + ", "
                           + " st_regrep = 'M' WHERE "
                           + " co_emp = " + objParSis.getCodigoEmpresa() + " and co_loc = " + objParSis.getCodigoLocal() + " and co_tipdoc = " + intCodTipDocCXCTC + " and co_doc = " + intCodDoc + " "
                           + " AND tx_tipreg = 'R' AND nd_porret="+dblPorRetFte +" ; ";
                    stmLoc.executeUpdate(strSql);

                    /* Actualiza Detalle para % Retención IVA (20%) */
                    strSql = " UPDATE tbm_pagmovinv set mo_pag = mo_pag + " + dblTotRetIva + " * " + intTipMov + ", "
                           + " nd_basimp = nd_basimp + " + dblBasRetIva + " * " + intTipMov + ", "
                           + " st_regrep = 'M' WHERE "
                           + " co_emp = " + objParSis.getCodigoEmpresa() + " and co_loc = " + objParSis.getCodigoLocal() + " and co_tipdoc = " + intCodTipDocCXCTC + " and co_doc = " + intCodDoc + " "
                           + " AND tx_tipreg = 'R' AND nd_porret="+dblPorRetIva +" ; ";
                    stmLoc.executeUpdate(strSql);

                    /* Actualiza Detalle para Comisión */
                    strSql = " UPDATE tbm_pagmovinv set mo_pag = mo_pag + " + dblTotCom + " * " + intTipMov + ", "
                           + " nd_basimp = nd_basimp + " + dblBasComNet + " * " + intTipMov + ", "
                           + " st_regrep = 'M' WHERE "
                           + " co_emp = " + objParSis.getCodigoEmpresa() + " and co_loc = " + objParSis.getCodigoLocal() + " and co_tipdoc = " + intCodTipDocCXCTC + " and co_doc = " + intCodDoc + " "
                           + " AND tx_tipreg = 'C' ; ";
                    stmLoc.executeUpdate(strSql);

                    /* Actualiza Detalle para Valor A Cobrar */
                    strSql = " UPDATE tbm_pagmovinv set mo_pag = mo_pag + " + dblTotValCob + " * " + intTipMov + ", "
                           + " st_regrep = 'M' WHERE "
                           + " co_emp = " + objParSis.getCodigoEmpresa() + " and co_loc = " + objParSis.getCodigoLocal() + " and co_tipdoc = " + intCodTipDocCXCTC + " and co_doc = " + intCodDoc + " "
                           + " AND tx_tipreg = 'V' ; ";
                    stmLoc.executeUpdate(strSql);

                    stmLoc.close();
                    stmLoc = null;
                }
            }
            catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        public int _getObtenerMaxCodRegDet(java.sql.Connection conn, int intCodTipDoc, int intCodDoc) {
            int intCodReg = -1;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try {
                //System.out.println("ZafCxC79._getObtenerMaxCodRegDet: ");
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "SELECT CASE WHEN max(co_reg) IS NULL THEN 1 ELSE max(co_reg)+1 END AS coreg FROM tbm_detpag "
                            + " WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + " "
                            + " AND co_tipdoc=" + intCodTipDoc + " AND co_doc= " + intCodDoc + " ";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        intCodReg = rstLoc.getInt("coreg");
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return intCodReg;
        }

        /**
         * Permite saber si el documento esta anulado
         *
         * @param conn coneccion de la base
         * @param intCodTipDoc codigo del tipo de documento
         * @param intCodDoc codigo de documento
         * @return true no esta anulado false esta anulado el registro
         */
        private boolean obtenerEstAnu(java.sql.Connection conn, int intCodTipDoc, int intCodDoc) {
            boolean blnRes = true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "", strEst = "";
            try {
                //System.out.println("ZafCxC79.obtenerEstAnu: ");
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "SELECT st_reg FROM tbm_cabpag "
                            + " where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " "
                            + " and co_tipdoc=" + intCodTipDoc + " and co_doc=" + intCodDoc + " and st_reg='I' ";

                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        if (rstLoc.getString("st_reg").equals("I")) {
                            strEst = "ANULADO";
                        }
                        MensajeInf("El documento ya está " + strEst + ".\nNo es posible modifcar un documento " + strEst + ".");
                        blnRes = false;
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        /**
         * Se encarga de modificar la cabezera del registro
         *
         * @param conn coneccion de la base
         * @param intCodTipDoc codigo del tipo de documento
         * @param intCodDoc codigo de documento
         * @return
         */
        private boolean modificarCab(java.sql.Connection conn, int intCodTipDoc, int intCodDoc) {
            boolean blnRes = true;
            java.sql.Statement stmLoc;
            String strSql = "";
            String strFecDoc = "";
            try {
                //System.out.println("ZafCxC79.modificarCab: ");
                if (conn != null) {

                    stmLoc = conn.createStatement();

                    strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";

                    strSql = "UPDATE tbm_cabpag SET  fe_doc='" + strFecDoc + "', ne_numdoc1=" + (txtAlt1.getText().trim().equals("") ? null : txtAlt1.getText()) + ","
                            + " ne_numdoc2=" + (txtAlt1.getText().trim().equals("") ? null : txtAlt1.getText()) + ", "
                            + " tx_obs1=" + objUti.codificar(txaObs1.getText()) + ", tx_obs2=" + objUti.codificar(txaObs2.getText()) + ", "
                            + " nd_mondoc=" + (valDoc.getText().equals("") ? "0" : valDoc.getText()) + ", "
                            + " fe_ultmod=" + objParSis.getFuncionFechaHoraBaseDatos() + ", "
                            + " co_usrmod=" + objParSis.getCodigoUsuario() + ", "
                            + " tx_commod='" + objParSis.getNombreComputadoraConDirIP() + "', st_regrep='M' "
                            + " WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + " AND co_tipdoc=" + intCodTipDoc + " "
                            + " AND co_doc=" + intCodDoc;
                    stmLoc.executeUpdate(strSql);

                    //Eddye: Parche temporal hasta que se mejore el código.
                    int intCodTipDocCXCTC=0;
                    if (txtDesCodTitpDoc.getText().equals("COBTCM"))
                    {
                        intCodTipDocCXCTC=196;
                    }
                    else if (txtDesCodTitpDoc.getText().equals("COBTCD"))
                    {
                        intCodTipDocCXCTC=217;
                    }
                    strSql = " UPDATE tbm_cabmovinv SET  fe_doc='" + strFecDoc + "', ne_numdoc=" + (txtAlt1.getText().trim().equals("") ? null : txtAlt1.getText()) + ","
                            + " tx_obs1=" + objUti.codificar(txaObs1.getText()) + ", tx_obs2=" + objUti.codificar(txaObs2.getText()) + ", "
                            + " nd_tot=" + (valDoc.getText().equals("") ? "0" : valDoc.getText()) + " * -1, "
                            + " fe_ultmod=" + objParSis.getFuncionFechaHoraBaseDatos() + ", "
                            + " co_usrmod=" + objParSis.getCodigoUsuario() + ", "
                            + " tx_commod='" + objParSis.getNombreComputadoraConDirIP() + "', st_regrep='M' "
                            + " WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + " AND co_tipdoc=" + intCodTipDocCXCTC
                            + " AND co_doc=" + intCodDoc;
                    stmLoc.executeUpdate(strSql);

                    stmLoc.close();
                    stmLoc = null;

                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private void bloquea(javax.swing.JTextField txtFiel, java.awt.Color colBack, boolean blnEst) {
            //System.out.println("ZafCxC79.bloquea: ");
            colBack = txtFiel.getBackground();
            txtFiel.setEditable(blnEst);
            txtFiel.setBackground(colBack);
        }

        public void clnTextos() {
            //System.out.println("ZafCxC79.clnTextos: ");
            strCodTipDoc = "";
            strDesCodTipDoc = "";
            strDesLarTipDoc = "";
            strCodTipDocCon = "";
            strDesCorTipDocCon = "";
            strDesLarTipDocCon = "";
            strCodCtaDeb = "";
            strCodCtaHab = "";

            txtFecDoc.setText("");

            txtCodTipDoc.setText("");
            txtDesCodTitpDoc.setText("");
            txtDesLarTipDoc.setText("");


            valDoc.setText("0.00");
            txtFecDoc.setText("");
            txtAlt1.setText("");

            txtCodDoc.setText("");
            txaObs1.setText("");
            txaObs2.setText("");

            objTblMod.removeAllRows();

            objAsiDia.inicializar();
            objAsiDia.setEditable(true);


            objAsiDia2.inicializar();
            objAsiDia2.setEditable(true);

        }

        public boolean cancelar() {
            boolean blnRes = true;

            try {
                if (blnHayCam) {
                    if (objTooBar.getEstado() == 'n' || objTooBar.getEstado() == 'm') {
                        if (!isRegPro()) {
                            return false;
                        }
                    }
                }
                if (rstCab != null) {
                    rstCab.close();
                    if (STM_GLO != null) {
                        STM_GLO.close();
                        STM_GLO = null;
                    }
                    rstCab = null;

                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            clnTextos();
            blnHayCam = false;

            return blnRes;
        }

        public boolean aceptar() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterAnular() {
            return true;
        }

        public boolean afterCancelar() {

            return true;
        }

        public boolean afterConsultar() {

            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterInsertar() {
            this.setEstado('w');

            _getMostrarSaldoCta();

            return true;
        }

        public boolean afterModificar() {


            _getMostrarSaldoCta();

            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }

        /**
         * Esta función se encarga de agregar el listener "DocumentListener" a
         * los objTooBars de tipo texto para poder determinar si su contenido a
         * cambiado o no.
         */
        private boolean isRegPro() {
            boolean blnRes = true;
//        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
//        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
//        switch (mostrarMsgCon(strAux)) {
//            case 0: //YES_OPTION
//                switch (objTooBar.getEstado()) {
//                    case 'n': //Insertar
//                        blnRes=objTooBar.insertar();
//                        break;
//                    case 'm': //Modificar
////                        blnRes=objTooBar.modificar();
//                        break;
//                }
//                break;
//            case 1: //NO_OPTION
//                objTblMod.setDataModelChanged(false);
//                blnHayCam=false;
//                blnRes=true;
//                break;
//            case 2: //CANCEL_OPTION
//                blnRes=false;
//                break;
//        }
            return blnRes;
        }

        public boolean consultar() {
            /*
             * Esto Hace en caso de que el modo de operacion sea Consulta
             */
            return _consultar(FilSql());
        }

        private boolean _consultar(String strFil) {
            boolean blnRes = false;
            String strSql = "";
            try {
                abrirCon();
                if (CONN_GLO != null) {
                    STM_GLO = CONN_GLO.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY);

                    strSql = "Select co_emp, co_loc, co_tipdoc, co_doc  from tbm_cabpag "
                            + " where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " "
                            + " and st_reg not in ('E')  " + strFil + " ORDER BY  fe_doc, ne_numdoc1 ";
                    rstCab = STM_GLO.executeQuery(strSql);
                    if (rstCab.next()) {
                        rstCab.last();
                        setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                        refrescaDatos(CONN_GLO, rstCab);
                        blnRes = true;
                    } else {
                        setMenSis("0 Registros encontrados");
                        clnTextos();
                    }

                    CerrarCon();
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }

            System.gc();
            return blnRes;
        }

        /**
         * Carga los datos de la reccion previo a la consulta
         *
         * @param conn coneccion de la base
         * @param rstDatRec resulset de los datos consultados
         * @return true si se cargo con exito false si no cargo
         */
        private boolean refrescaDatos(java.sql.Connection conn, java.sql.ResultSet rstDatRec) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc, rstLoc02;
            String strSql = "";
            String strAux = "";
            Vector vecData;
            try {
                //System.out.println("ZafCxC79.refrescaDatos: ");
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    /**
                     * ********CARGAR DATOS DE CABEZERA **************
                     */
                    strSql = "SELECT a.st_reg, a.co_tipdoc, a1.tx_descor, a1.tx_deslar, a.co_doc, a.fe_doc, a.ne_numdoc1, a.ne_numdoc2, a.nd_mondoc, "
                            + " a.tx_obs1, a.tx_obs2, a.co_mnu  from tbm_cabpag as a "
                            + " INNER JOIN tbm_cabtipdoc as a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc )  "
                            + " WHERE a.co_emp=" + rstDatRec.getInt("co_emp") + " and a.co_loc=" + rstDatRec.getInt("co_loc") + " "
                            + " and a.co_tipdoc=" + rstDatRec.getInt("co_tipdoc") + "  and a.co_Doc=" + rstDatRec.getInt("co_doc") + " ";
                    //System.out.println("ZafCxC79.Refresca Datos:" + strSql);
                    rstLoc02 = stmLoc.executeQuery(strSql);
                    if (rstLoc02.next()) {

                        txtCodTipDoc.setText(rstLoc02.getString("co_tipdoc"));
                        txtDesCodTitpDoc.setText(rstLoc02.getString("tx_descor"));
                        txtDesLarTipDoc.setText(rstLoc02.getString("tx_deslar"));
                        txtCodDoc.setText(rstLoc02.getString("co_doc"));
                        txtAlt1.setText(rstLoc02.getString("ne_numdoc1"));

                        valDoc.setText("" + objUti.redondear(rstLoc02.getString("nd_mondoc"), 2));
                        txaObs1.setText(rstLoc02.getString("tx_obs1"));
                        txaObs2.setText(rstLoc02.getString("tx_obs2"));

                        strAux = rstLoc02.getString("st_reg");

                        intCodMnuDocIng = rstLoc02.getInt("co_mnu");

                        java.util.Date dateObj = rstLoc02.getDate("fe_doc");
                        if (dateObj == null) {
                            txtFecDoc.setText("");
                        } else {
                            java.util.Calendar calObj = java.util.Calendar.getInstance();
                            calObj.setTime(dateObj);
                            txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                    calObj.get(java.util.Calendar.MONTH) + 1,
                                    calObj.get(java.util.Calendar.YEAR));
                        }
                    }
                    rstLoc02.close();
                    rstLoc02 = null;

                    /**
                     * ************************************************
                     */
                    objAsiDia.consultarDiario(rstDatRec.getInt("co_emp"), rstDatRec.getInt("co_loc"), rstDatRec.getInt("co_tipdoc"), rstDatRec.getInt("co_doc"));


//       cargarTiPDocCon( conn, rstDatRec.getInt("co_emp"),  rstDatRec.getInt("co_loc"),  rstDatRec.getInt("co_tipdoc"),  rstDatRec.getInt("co_doc") );


                    /**
                     * ********CARGAR DATOS DE DETALLE **************
                     */
                    vecData = new Vector();


                    strSql = "select a2.co_cli, a2.tx_nomcli, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a3.tx_descor, a3.tx_deslar, "
                            + " a2.ne_numdoc, a2.fe_doc ,a1.ne_diacre, a1.fe_ven, a1.nd_porret, a1.mo_pag, ( a1.mo_pag + a1.nd_abo ) as valpen, a.nd_abo, a.co_reg as coregpag, a.tx_numtra as tx_numtra, a.tx_numaut as tx_numaut,a2.co_tipCre "
                            + " from tbm_detpag as a  "
                            + " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locpag and a1.co_tipdoc=a.co_tipdocpag and a1.co_doc=a.co_docpag and a1.co_reg=a.co_regpag )  "
                            + " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc )  "
                            + " inner join tbm_cabtipdoc as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc  )    "
                            + " WHERE a.co_emp=" + rstDatRec.getInt("co_emp") + " and a.co_loc=" + rstDatRec.getInt("co_loc") + "  and "
                            + " a.co_tipdoc=" + rstDatRec.getInt("co_tipdoc") + "  and a.co_Doc=" + rstDatRec.getInt("co_doc") + "  and a.st_reg='A'  ORDER BY a.co_reg  ";
                    //System.out.println("refrescaDatos detalle: " + strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    while (rstLoc.next()) {

                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_BUTCLI, "..");
                        vecReg.add(INT_TBL_CHKSEL, new Boolean(true));
                        vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli"));
                        vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nomcli"));
                        vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp"));
                        vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc"));
                        vecReg.add(INT_TBL_CODTID, rstLoc.getString("co_tipdoc"));
                        vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc"));
                        vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                        vecReg.add(INT_TBL_DCTIPDOC, rstLoc.getString("tx_descor"));
                        vecReg.add(INT_TBL_DLTIPDOC, rstLoc.getString("tx_deslar"));
                        vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc"));
                        vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc"));
                        vecReg.add(INT_TBL_DIACRE, rstLoc.getString("ne_diacre"));
                        vecReg.add(INT_TBL_FECVEN, rstLoc.getString("fe_ven"));
                        vecReg.add(INT_TBL_PORRET, rstLoc.getString("nd_porret"));
                        vecReg.add(INT_TBL_VALDOC, rstLoc.getString("mo_pag"));
                        vecReg.add(INT_TBL_VALPEN, rstLoc.getString("valpen"));
                        vecReg.add(INT_TBL_NUMTRA, rstLoc.getString("tx_numtra"));
                        vecReg.add(INT_TBL_NUMAUT, rstLoc.getString("tx_numaut"));
                        vecReg.add(INT_TBL_ABONO, rstLoc.getString("nd_abo"));
                        vecReg.add(INT_TBL_CODREGEFE, rstLoc.getString("coregpag"));
                        vecReg.add(INT_TBL_ABONOORI, rstLoc.getString("nd_abo"));
                        vecReg.add(INT_TBL_BUTFAC, "..");
                        vecReg.add(INT_TBL_TIPCRE, "co_tipCre");
                        vecData.add(vecReg);
                    }
                    rstLoc.close();
                    rstLoc = null;


                    objTblMod.setData(vecData);
                    tblDat.setModel(objTblMod);

                    /**
                     * ************************************************
                     */
                    stmLoc.close();
                    stmLoc = null;

                    if (strAux.equals("A")) {
                        strAux = "Activo";
                    } else if (strAux.equals("I")) {
                        strAux = "Anulado";
                    } else if (strAux.equals("E")) {
                        strAux = "Eliminado";
                    } else {
                        strAux = "Otro";
                    }
                    objTooBar.setEstadoRegistro(strAux);

                    _getMostrarSaldoCta();

                    int intPosRel = rstDatRec.getRow();
                    rstDatRec.last();
                    objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstDatRec.getRow());
                    rstDatRec.absolute(intPosRel);

                    blnRes = true;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private String FilSql() {
            String sqlFiltro = "";
            try {
                //System.out.println("ZafCxC79.FilSql: ");
                //Agregando filtro por Numero de Cotizacion


                if (!(txtCodTipDoc.getText().equals(""))) {
                    sqlFiltro += " and  co_tipdoc=" + txtCodTipDoc.getText();
                } else {
                    sqlFiltro += " and co_tipdoc in (" + strSqlTipDocAux + ") ";
                }

                if (!txtCodDoc.getText().equals("")) {
                    sqlFiltro += " and co_doc=" + txtCodDoc.getText();
                }


                if (!txtAlt1.getText().equals("")) {
                    sqlFiltro += " and ne_numdoc1=" + txtAlt1.getText();
                }

                if (txtFecDoc.isFecha()) {
                    int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
                    String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" + FecSql[0] + "#";
                    sqlFiltro += " and fe_doc = '" + strFecSql + "'";
                }

            } catch (Exception ev) {
                objUti.mostrarMsgErr_F1(this, ev);
            }
            return sqlFiltro;
        }

        public void clickModificar() {
            try {

                java.awt.Color colBack;
                colBack = txtCodDoc.getBackground();


                bloquea(txtCodDoc, colBack, false);
                bloquea(txtDesCodTitpDoc, colBack, false);
                bloquea(txtDesLarTipDoc, colBack, false);

                bloquea(valDoc, colBack, false);

                butTipDoc.setEnabled(false);

                setEditable(true);
                objTblMod.setDataModelChanged(false);

                blnHayCam = false;

                if (intTipModDoc == 1) {
                    MensajeInf("NO TIENE ACCESO A MODIFICAR EL DOCUMENTO.. ");
                    this.setEstado('w');
                } else if (intTipModDoc == 2) {
                    if (!_estadoImpDoc() || !_estadoCerDoc()) {
                        this.setEstado('w');
                    }
                } else if (intTipModDoc == 3) {
                    // SI PERMITE MODIFCAR
                }
            } catch (Exception evt) {
                objUti.mostrarMsgErr_F1(this, evt);
            }
        }

        /**
         * verificar el estado de impresion realizando la coneccion a la base
         *
         * @return
         */
        private boolean _estadoImpDoc() {
            boolean blnRes = true;
            java.sql.Connection conn;
            int intCodTipDoc = 0;
            int intCodDoc = 0;
            try {
                conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conn != null) {

                    intCodDoc = Integer.parseInt(txtCodDoc.getText());
                    intCodTipDoc = Integer.parseInt(txtCodTipDoc.getText());

                    if (!_getEstImp(conn, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodTipDoc, intCodDoc)) {
                        blnRes = false;
                    }
                    conn.close();
                    conn = null;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean _estadoCerDoc() {
            boolean blnRes = true;
            java.sql.Connection conn;
            int intCodTipDoc = 0;
            int intCodDoc = 0;
            try {
                //System.out.println("ZafCxC79._estadoCerDoc: ");
                conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conn != null) {

                    intCodDoc = Integer.parseInt(txtCodDoc.getText());
                    intCodTipDoc = Integer.parseInt(txtCodTipDoc.getText());

                    if (!_getEstCer(conn, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodTipDoc, intCodDoc)) {
                        blnRes = false;
                    }
                    conn.close();
                    conn = null;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        /**
         * verificar el estado de impresion antes de insertar un nuevo documento
         *
         * @return
         */
        private boolean _getEstImpDoc() {
            boolean blnRes = true;
            java.sql.Connection conn;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            int intCodTipDoc = 0;
            try {
                //System.out.println("ZafCxC79._getEstimpDoc: ");
                conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conn != null) {

                    stmLoc = conn.createStatement();

                    if ((txtCodTipDoc.getText()).equals("")) {
                        intCodTipDoc = 0; // Motivo: Se generaba una excepcion al momento de realiza el cambio a int.
                    } else {
                        intCodTipDoc = Integer.parseInt(txtCodTipDoc.getText());
                    }

                    strSql = "select  st_imp  from tbm_cabpag where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " "
                            + " and co_tipdoc=" + intCodTipDoc + " and ne_numdoc1 = " + (txtAlt1.getText().equals("") ? "0" : txtAlt1.getText()) + "-1 and st_imp='N'";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        MensajeInf("NO ESTA IMPRESO EL DOCUMENTO ANTERIOR  \nTIENE QUE REALIZAR LA IMPRESIÓN PARA PODER INSERTAR UN NUEVO DOCUMENTO..");
                        blnRes = false;
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                    conn.close();
                    conn = null;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        //******************************************************************************************************
        public boolean vistaPreliminar() {
            cargarRepote(1);
            return true;
        }

        private void cargarRepote(int intTipo) {
            if (objThrGUI == null) {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.setIndFunEje(intTipo);
                objThrGUI.start();
            }
        }

        public boolean imprimir() {
            cargarRepote(0);
            return true;
        }

        //******************************************************************************************************
        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickCancelar() {
        }

        public void cierraConnections() {
        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeAnular() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeEliminar() {
            return true;
        }

        public boolean beforeImprimir() {

            return true;
        }

        public boolean beforeInsertar() {
            return true;
        }

        public boolean beforeModificar() {
            return true;
        }

        public boolean beforeVistaPreliminar() {

            return true;
        }
    }

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_LINEA:
                    strMsg = "";
                    break;
                case INT_TBL_CODCLI:
                    strMsg = "Código de cliente";
                    break;
                case INT_TBL_NOMCLI:
                    strMsg = "Nombre del cliente";
                    break;
                case INT_TBL_CODLOC:
                    strMsg = "Código del local";
                    break;
                case INT_TBL_NUMDOC:
                    strMsg = "Número de documento";
                    break;
                case INT_TBL_FECDOC:
                    strMsg = "Fecha del documento";
                    break;
                case INT_TBL_FECVEN:
                    strMsg = "Fecha de vencimiento del crédito";
                    break;
                case INT_TBL_DIACRE:
                    strMsg = "Día de crédito";
                    break;
                case INT_TBL_PORRET:
                    strMsg = "Porcentaje de retención";
                    break;
                case INT_TBL_DCTIPDOC:
                    strMsg = "Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DLTIPDOC:
                    strMsg = "Descripción larga del tipo de documento";
                    break;
                case INT_TBL_VALDOC:
                    strMsg = "Valor del documento";
                    break;
                case INT_TBL_VALPEN:
                    strMsg = "Valor pendiente";
                    break;
                case INT_TBL_NUMTRA:
                    strMsg = "Número de transacción";
                    break;
                case INT_TBL_NUMAUT:
                    strMsg = "Número de autorización";
                    break;
                case INT_TBL_ABONO:
                    strMsg = "Valor abonado";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que está ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podría presentar mensajes informativos en un JLabel e ir incrementando un
     * JProgressBar con lo cual el usuario estaría informado en todo momento de
     * lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase ya
     * que si no sólo se apreciaría los cambios cuando ha terminado todo el
     * proceso.
     */
    private class ZafThreadGUI extends Thread {

        private int intIndFun;

        public ZafThreadGUI() {
            intIndFun = 0;
        }

        public void run() {
            switch (intIndFun) {
                case 0: //Botón "Imprimir".
                    objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                    objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                    objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUI = null;
        }

        /**
         * Esta función establece el indice de la función a ejecutar. En la
         * clase Thread se pueden ejecutar diferentes funciones. Esta función
         * sirve para determinar la función que debe ejecutar el Thread.
         *
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice) {
            intIndFun = indice;
        }
    }

    /**
     * Esta función permite generar el reporte de acuerdo al criterio
     * seleccionado.
     *
     * @param intTipRpt El tipo de reporte a generar. <BR>Puede tomar uno de los
     * siguientes valores: <UL> <LI>0: Impresión directa. <LI>1: Impresión
     * directa (Cuadro de dialogo de impresión). <LI>2: Vista preliminar. </UL>
     * @return true: Si se pudo generar el reporte. <BR>false: En el caso
     * contrario.
     */
    private boolean generarRpt(int intTipRpt) {
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes = true;
        try {
            objRptSis.cargarListadoReportes(conCab);
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada() == objRptSis.INT_OPC_ACE) {
                //Obtener la fecha y hora del servidor.
              /* datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                 if (datFecAux==null)
                 return false;
                 strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                 datFecAux=null;*/
                intNumTotRpt = objRptSis.getNumeroTotalReportes();
                for (i = 0; i < intNumTotRpt; i++) {
                    if (objRptSis.isReporteSeleccionado(i)) {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i))) {
                            case 19:
                            default:
                                strRutRpt = objRptSis.getRutaReporte(i);
                                strNomRpt = objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.
                                java.util.Map mapPar = new java.util.HashMap();
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                mapPar.put("co_emp", objParSis.getCodigoEmpresa());
                                mapPar.put("co_loc", objParSis.getCodigoLocal());
                                mapPar.put("co_tipdoc", Integer.parseInt(txtCodTipDoc.getText()));
                                mapPar.put("co_doc", Integer.parseInt(txtCodDoc.getText()));
                                //   mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                cambiarEstImp(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private void cambiarEstImp(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc) {
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        String strSql = "";
        try {
            //System.out.println("ZafCxC79.cambiarEstImp: ");
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null) {
                stmLoc = conn.createStatement();
                strSql = "UPDATE tbm_cabpag SET st_imp='S' WHERE  co_emp=" + intCodEmp + " AND co_loc=" + intCodLoc + " AND co_tipdoc=" + intCodTipDoc + ""
                        + " AND co_doc=" + intCodDoc + " ";
                stmLoc.executeUpdate(strSql);

                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;
            }
        } catch (java.sql.SQLException ex) {
            objUti.mostrarMsgErr_F1(this, ex);
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    protected void finalize() throws Throwable {
        System.gc();
        super.finalize();
    }

        /**
         * carga el tipo de documento cuando se da en click insertar y consultar
         *
         * @param intVal valor si tiene que cargar numero de documento o no 1 =
         * si cargar
         */
        public void cargarTipoDocSel(int intVal) {
            java.sql.Connection conn;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try {
                //System.out.println("ZafCxC79.cargarTipoDocSel: ");
                conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    if (objParSis.getCodigoUsuario() == 1) {
                        strSql = "SELECT  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor, "
                                + " case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc "
                                + " ,doc.tx_natdoc, doc.st_meringegrfisbod ,a1.co_cta, a1.tx_codcta, a1.tx_deslar AS txdeslarctaefe , a2.co_cta as cocta, a2.tx_codcta as txcodcta, a2.tx_deslar as deslarcta "
                                + " ,2 as ne_tipresmoddoc "
                                + " FROM tbr_tipdocprg as menu "
                                + " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) "
                                + " inner join tbm_placta as a1 on (a1.co_emp=doc.co_emp and a1.co_cta=doc.co_ctadeb ) "
                                + " inner join tbm_placta as a2 on (a2.co_emp=doc.co_emp and a2.co_cta=doc.co_ctahab ) "
                                + " WHERE   menu.co_emp = " + objParSis.getCodigoEmpresa() + " and "
                                + " menu.co_loc = " + objParSis.getCodigoLocal() + " AND  "
                                + " menu.co_mnu = " + objParSis.getCodigoMenu() //+ " AND  menu.st_reg = 'S' "
                                + " AND menu.co_tipDoc=" + txtCodTipDoc.getText();
                    } else {
                        strSql = "SELECT  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor, "
                                + " case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc "
                                + " ,doc.tx_natdoc, doc.st_meringegrfisbod ,a1.co_cta, a1.tx_codcta, a1.tx_deslar AS txdeslarctaefe , a2.co_cta as cocta, a2.tx_codcta as txcodcta, a2.tx_deslar as deslarcta "
                                + "  ,menu.ne_tipresmoddoc "
                                + " FROM tbr_tipDocUsr as menu "
                                + " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) "
                                + " inner join tbm_placta as a1 on (a1.co_emp=doc.co_emp and a1.co_cta=doc.co_ctadeb ) "
                                + " inner join tbm_placta as a2 on (a2.co_emp=doc.co_emp and a2.co_cta=doc.co_ctahab ) "
                                + " WHERE   menu.co_emp = " + objParSis.getCodigoEmpresa() + " and "
                                + " menu.co_loc = " + objParSis.getCodigoLocal() + " AND  "
                                + " menu.co_mnu = " + objParSis.getCodigoMenu() + " AND  "
                                + " menu.co_usr=" + objParSis.getCodigoUsuario() //+ " AND menu.st_reg = 'S' ";
                                + " AND menu.co_tipDoc=" + txtCodTipDoc.getText();

                    }

                    rstLoc = stmLoc.executeQuery(strSql);

                    if (rstLoc.next()) {
                        txtCodTipDoc.setText(((rstLoc.getString("co_tipdoc") == null) ? "" : rstLoc.getString("co_tipdoc")));
                        txtDesCodTitpDoc.setText(((rstLoc.getString("tx_descor") == null) ? "" : rstLoc.getString("tx_descor")));
                        txtDesLarTipDoc.setText(((rstLoc.getString("tx_deslar") == null) ? "" : rstLoc.getString("tx_deslar")));
                        intTipModDoc = rstLoc.getInt("ne_tipresmoddoc");
                        strCodTipDoc = txtCodTipDoc.getText();
                        if (intVal == 1) {
                            txtAlt1.setText(((rstLoc.getString("numDoc") == null) ? "" : rstLoc.getString("numDoc")));
                        }

                        strCodCtaCli = rstLoc.getString("cocta");
                        strTxtCodCtaCli = rstLoc.getString("txcodcta");
                        strNomCtaCli = rstLoc.getString("deslarcta");
                        strCodCtaEfe = rstLoc.getString("co_cta");
                        strTxtCodCtaEfe = rstLoc.getString("tx_codcta");
                        strNomCtaEfe = rstLoc.getString("txdeslarctaefe");

                    }
                    rstLoc.close();
                    stmLoc.close();
                    stmLoc = null;
                    rstLoc = null;
                    conn.close();
                    conn = null;
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
        }

}
