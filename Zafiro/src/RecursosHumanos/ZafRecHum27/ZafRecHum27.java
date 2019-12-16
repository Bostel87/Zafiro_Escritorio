package RecursosHumanos.ZafRecHum27;

import Librerias.ZafRecHum.ZafRecHumVen.ZafVenMotHorSupExt;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Solicitudes de horas suplementarias/extraordinarias
 *
 * @author Roberto Flores Guayaquil 03/04/2012
 */
public class ZafRecHum27 extends javax.swing.JInternalFrame {

    private java.sql.Connection CONN_GLO = null;
    private java.sql.Statement STM_GLO = null;
    private java.sql.ResultSet rstCab = null;

    private Librerias.ZafParSis.ZafParSis objZafParSis;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelEdiChk zafTblCelEdiChkLab;
    private ZafTblCelRenChk zafTblCelRenChkLab;
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    private Librerias.ZafDate.ZafDatePicker txtFecSol;
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private java.util.Date datFecAux;

    private ZafVenMotHorSupExt zafVenMotHorSupExt;              //Ventana de consulta "Motivos de horas suplementarias/extraordinarias".

    private int intCodEmp, intCodLoc;                           //Código de la empresa y local. 
    private String strAux = "";
    private String strDesCorTipDoc = "";
    private String strDesLarTipDoc = "";
    private String strCodTipDoc = "";
    private String strCodDep = "";
    private String strDesDep = "";
    private String strCodMot = "";
    private String strDesMot = "";

    private String strMerIngEgr = "", strTipIngEgr = "";

    private Vector vecCab = new Vector();
    private ZafVenCon objVenConTipdoc;
    private ZafVenCon objVenConMotHorSupExt;

    private ZafUtil objUti;
    private mitoolbar objTooBar;

    private boolean blnHayCam = false;

    private final int INT_TBL_LINEA = 0;
    private final int INT_TBL_CHKTRA = 1;
    private final int INT_TBL_CODEMPPAG = 2;
    private final int INT_TBL_CODTRA = 3;
    private final int INT_TBL_NOMTRA = 4;
    private final int INT_TBL_COREG = 5;
    private final int INT_TBL_HORENT = 6;
    private final int INT_TBL_HORSAL = 7;

    private javax.swing.JTextField txtCodTipDoc = new javax.swing.JTextField();
    private javax.swing.JTextField txtCodTipDocSol = new javax.swing.JTextField();
    private javax.swing.JTextArea txtsql = new javax.swing.JTextArea();

    private ZafThreadGUI objThrGUI;
    private ZafRptSis objRptSis;                                //Reportes del Sistema.

    private ZafVenCon vcoTipDoc;                                //Ventana de consulta "Tipo de documento".

    private int intSig = 1;                                       //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.

    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst;//, rstCab;

    private ZafVenCon vcoDep;                                  //Ventana de consulta "Tipo de documento".

    private Vector vecAux;
    private Vector vecDat = null;

    private String strCodEmpAut = "";
    private String strCodLocAut = "";
    private String strCodTipDocAut = "";
    private String strCodDocAut = "";
    private boolean blnEstCarSolHSE = false;

    private String strVersion = " v1.30";

    public ZafRecHum27(Librerias.ZafParSis.ZafParSis obj) {
        try {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            lblHorAutDes.setVisible(false);
            lblHorAutHas.setVisible(false);
            cboHHAutDes.setVisible(false);
            cboMMAutDes.setVisible(false);
            jLabel9.setVisible(false);
            cboHHAutHas.setVisible(false);
            jLabel10.setVisible(false);
            cboMMAutHas.setVisible(false);

            objUti = new ZafUtil();

            intCodEmp = objZafParSis.getCodigoEmpresa();
            intCodLoc = objZafParSis.getCodigoLocal();

            objTooBar = new mitoolbar(this);
            this.getContentPane().add(objTooBar, "South");

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            PanTabGen.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);
            //txtFecDoc.setEnabled(false);
            txtFecDoc.setBackground(objZafParSis.getColorCamposObligatorios());
            txtFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
            txtFecDoc.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtFecDocActionPerformed(evt);
                }
            });

            txtFecSol = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecSol.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecSol.setText("");
            PanTabGen.add(txtFecSol);
            txtFecSol.setBounds(140, 70, 110, 20);
            //txtFecSol.setEnabled(false);
            txtFecSol.setBackground(objZafParSis.getColorCamposObligatorios());
            txtFecSol.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
            txtFecSol.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtFecSolActionPerformed(evt);
                }
            });
            
            this.setTitle(objZafParSis.getNombreMenu() + " " + strVersion);
            lblTit.setText(objZafParSis.getNombreMenu());
            zafVenMotHorSupExt = new ZafVenMotHorSupExt(JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Motivos de horas suplementarias/extraordinarias");
        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
            e.printStackTrace();
        }
    }

    /**
     * Creates new form ZafRecHum27
     */
    public ZafRecHum27(Librerias.ZafParSis.ZafParSis obj, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        try {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            if (objZafParSis.getCodigoMenu() == 2862 || objZafParSis.getCodigoMenu() == 3485) {
                lblHorAutDes.setVisible(true);
                lblHorAutHas.setVisible(true);
                cboHHAutDes.setVisible(true);
                cboMMAutDes.setVisible(true);
                jLabel9.setVisible(true);
                cboHHAutHas.setVisible(true);
                jLabel10.setVisible(true);
                cboMMAutHas.setVisible(true);
            } else {
                lblHorAutDes.setVisible(false);
                lblHorAutHas.setVisible(false);
                cboHHAutDes.setVisible(false);
                cboMMAutDes.setVisible(false);
                jLabel9.setVisible(false);
                cboHHAutHas.setVisible(false);
                jLabel10.setVisible(false);
                cboMMAutHas.setVisible(false);
            }

            this.setTitle(objZafParSis.getNombreMenu() + " " + strVersion);
            lblTit.setText(objZafParSis.getNombreMenu());

            strCodEmpAut = strCodEmp;
            strCodLocAut = strCodLoc;
            strCodTipDocAut = strCodTipDoc;
            strCodDocAut = strCodDoc;
            blnEstCarSolHSE = true;

            objUti = new ZafUtil();
            objTooBar = new mitoolbar(this);

            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleModificar(false);

            if (objZafParSis.getCodigoMenu() == 2216) {
                objTooBar.setVisibleAnular(false);
                objTooBar.setVisibleConsultar(false);
                objTooBar.setVisibleImprimir(false);
                objTooBar.setVisibleVistaPreliminar(false);
            }

            String strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText(strFecSis);
            PanTabGen.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);

            txtFecSol = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecSol.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecSol.setText(strFecSis);
            PanTabGen.add(txtFecSol);
            txtFecSol.setBounds(140, 70, 110, 20);
            objTooBar.setVisible(false);
        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /**
     * Esta función carga el documento especificado.
     *
     * @param codigoEmpresa El código de la empresa a cargar.
     * @param codigoLocal El código del local a cargar.
     * @param codigoTipoDocumento El código del tipo de documento a cargar.
     * @param codigoDocumento El código del documento a cargar.
     */
    public void cargarDocumento(Integer codigoEmpresa, Integer codigoLocal, Integer codigoTipoDocumento, Integer codigoDocumento) {
        intCodEmp = codigoEmpresa.intValue();
        intCodLoc = codigoLocal.intValue();
        txtCodTipDoc.setText(codigoTipoDocumento.toString());
        txtCodDoc.setText(codigoDocumento.toString());
        objTooBar.setVisibleModificar(false);
        objTooBar.setVisibleEliminar(false);
        objTooBar.setVisibleAnular(false);
        objTooBar.setEstado('c');
        objTooBar.consultar();
        objTooBar.setEstado('w');
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc() {
        boolean blnRes = true;
        String strSQL = "";
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            arlCam.add("a1.st_merIngEgrFisBod");
            arlCam.add("a1.ne_numLin");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Est.Mer.Ing.Egr.Fis.Bod.");
            arlAli.add("Núm.Lín.");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("60");
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if (objZafParSis.getCodigoUsuario() == 1) {
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                strSQL += " FROM tbm_cabTipDoc AS a1";
                strSQL += " INNER JOIN tbr_tipDocPrg AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL += " WHERE a1.co_emp=" + intCodEmp;
                strSQL += " AND a1.co_loc=" + intCodLoc;
                strSQL += " AND a2.co_mnu=" + objZafParSis.getCodigoMenu();
                strSQL += " ORDER BY a1.tx_desCor";
            } else {
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                strSQL += " FROM tbm_cabTipDoc AS a1";
                strSQL += " INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL += " WHERE a1.co_emp=" + intCodEmp;
                strSQL += " AND a1.co_loc=" + intCodLoc;
                strSQL += " AND a2.co_mnu=" + objZafParSis.getCodigoMenu();
                strSQL += " AND a2.co_usr=" + objZafParSis.getCodigoUsuario();
                strSQL += " ORDER BY a1.tx_desCor";
            }
            //Ocultar columnas.
            int intColOcu[] = new int[3];
            intColOcu[0] = 5;
            intColOcu[1] = 6;
            intColOcu[2] = 7;
            vcoTipDoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función muestra el tipo de documento predeterminado del programa.
     *
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre() {
        boolean blnRes = true;
        String strSQL = "";
        try {
            con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objZafParSis.getCodigoUsuario() == 1) {
                    //Armar la sentencia SQL.
                    strSQL = "";
                    strSQL += "SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                    strSQL += " FROM tbm_cabTipDoc AS a1";
                    strSQL += " WHERE a1.co_emp=" + intCodEmp;
                    strSQL += " AND a1.co_loc=" + intCodLoc;
                    strSQL += " AND a1.co_tipDoc=";
                    strSQL += " (";
                    strSQL += " SELECT co_tipDoc";
                    strSQL += " FROM tbr_tipDocPrg";
                    strSQL += " WHERE co_emp=" + intCodEmp;
                    strSQL += " AND co_loc=" + intCodLoc;
                    strSQL += " AND co_mnu=" + objZafParSis.getCodigoMenu();
                    strSQL += " AND st_reg='S'";
                    strSQL += " )";
                    rst = stm.executeQuery(strSQL);
                } else {
                    //Armar la sentencia SQL.
                    strSQL = "";
                    strSQL += "SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                    strSQL += " FROM tbm_cabTipDoc AS a1";
                    strSQL += " WHERE a1.co_emp=" + intCodEmp;
                    strSQL += " AND a1.co_loc=" + intCodLoc;
                    strSQL += " AND a1.co_tipDoc=";
                    strSQL += " (";
                    strSQL += " SELECT co_tipDoc";
                    strSQL += " FROM tbr_tipDocUsr";
                    strSQL += " WHERE co_emp=" + intCodEmp;
                    strSQL += " AND co_loc=" + intCodLoc;
                    strSQL += " AND co_mnu=" + objZafParSis.getCodigoMenu();
                    strSQL += " AND co_usr=" + objZafParSis.getCodigoUsuario();
                    strSQL += " AND st_reg='S'";
                    strSQL += " )";
                    rst = stm.executeQuery(strSQL);
                }
                if (rst.next()) {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtNumDoc.setText("" + (rst.getInt("ne_ultDoc") + 1));//THERE
                    intSig = (rst.getString("tx_natDoc").equals("I") ? 1 : -1);
                    //strMerIngEgrFisBodTipDoc=rst.getString("st_merIngEgrFisBod");
                    objTblMod.setMaxRowsAllowed(Integer.valueOf(rst.getInt("ne_numLin")));
                }
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función muestra el departamento predeterminado del programa.
     *
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarDepPre() {
        boolean blnRes = true;
        String strSQL = "";
        try {
            con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objZafParSis.getCodigoUsuario() == 1) {
                    //Armar la sentencia SQL.
//                    strSQL="";
//                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
//                    strSQL+=" FROM tbm_cabTipDoc AS a1";
//                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
//                    strSQL+=" AND a1.co_loc=" + intCodLoc;
//                    strSQL+=" AND a1.co_tipDoc=";
//                    strSQL+=" (";
//                    strSQL+=" SELECT co_tipDoc";
//                    strSQL+=" FROM tbr_tipDocPrg";
//                    strSQL+=" WHERE co_emp=" + intCodEmp;
//                    strSQL+=" AND co_loc=" + intCodLoc;
//                    strSQL+=" AND co_mnu=" + objZafParSis.getCodigoMenu();
//                    strSQL+=" AND st_reg='S'";
//                    strSQL+=" )";

                } else {
                    //Armar la sentencia SQL.
                    strSQL = "select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="
                            + objZafParSis.getCodigoUsuario() + "and co_mnu=" + objZafParSis.getCodigoMenu() + " and st_reg like 'P')";
                    rst = stm.executeQuery(strSQL);
                    if (rst.next()) {

                        txtCodDep.setText(rst.getString("co_dep"));
                        txtDesDep.setText(rst.getString("tx_deslar"));
                        insertarDetalle();
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst = null;
                    stm = null;
                    con = null;
                }
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public void setEditable(boolean editable) {
        if (editable == true) {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        } else {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }

    public void abrirCon() {
        try {
            CONN_GLO = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    public void CerrarCon() {
        try {
            CONN_GLO.close();
            CONN_GLO = null;
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Departamentos".
     */
    private boolean configurarVenConDep() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_dep");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción corta");
            arlAli.add("Descripción larga");
            arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            arlAncCol.add("110");
            arlAncCol.add("40");

            String strSQL = "";
            if (objZafParSis.getCodigoUsuario() == 1) {
                strSQL = "select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where st_reg like 'A' order by co_dep";
            } else {
                strSQL = "select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr=" + objZafParSis.getCodigoUsuario() + " "
                        + "and co_mnu=" + objZafParSis.getCodigoMenu() + " and st_reg like 'A')";
            }

            //Ocultar columnas.
            int intColOcu[] = new int[1];
            //intColOcu[0]=4;
            //intColOcu[1]=6;
            //intColOcu[2]=7;
            vcoDep = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado Departamentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoDep.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoDep.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConMotHorSupExt() {
        boolean blnRes = true;
        try {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_mot");
            arlCam.add("a.tx_deslar");
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción larga");
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //Armar la sentencia SQL.
            String strSQL = "";
            strSQL = "select a.co_mot, a.tx_deslar  from tbm_motHorSupExt as a"
                    + " where a.st_reg not in ('I','E') order by a.tx_deslar";
            objVenConMotHorSupExt = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTabla() {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecCab = new Vector();  //Almacena las cabeceras
            vecCab.clear();

            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CHKTRA, "");
            vecCab.add(INT_TBL_CODEMPPAG, "Cód.Emp.");
            vecCab.add(INT_TBL_CODTRA, "Código");
            vecCab.add(INT_TBL_NOMTRA, "Empleado");
            vecCab.add(INT_TBL_COREG, "Cód.Reg.");
            vecCab.add(INT_TBL_HORENT, "Entrada");
            vecCab.add(INT_TBL_HORSAL, "Salida");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();

            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CHKTRA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODEMPPAG).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODTRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOMTRA).setPreferredWidth(300);
            tcmAux.getColumn(INT_TBL_COREG).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_HORENT).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_HORSAL).setPreferredWidth(50);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_LINEA).setResizable(false);
            tcmAux.getColumn(INT_TBL_CHKTRA).setResizable(false);
            tcmAux.getColumn(INT_TBL_CODEMPPAG).setResizable(false);
            tcmAux.getColumn(INT_TBL_CODTRA).setResizable(false);
            tcmAux.getColumn(INT_TBL_NOMTRA).setResizable(false);
            tcmAux.getColumn(INT_TBL_HORENT).setResizable(false);
            tcmAux.getColumn(INT_TBL_HORSAL).setResizable(false);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_COREG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODEMPPAG, tblDat);
            System.out.println("menu: " + objZafParSis.getCodigoMenu());
            if (objZafParSis.getCodigoMenu() == 2862 || objZafParSis.getCodigoMenu() == 3485) {

            } else {
                objTblMod.addSystemHiddenColumn(INT_TBL_HORENT, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_HORSAL, tblDat);
            }

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_CHKTRA);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            //Configurar JTable: Editor de la tabla.
            objTblEdi = new ZafTblEdi(tblDat);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);

            tcmAux.getColumn(INT_TBL_LINEA).setCellRenderer(objTblFilCab);

            //Configurar JTable: Detectar cambios de valores en las celdas.
            /*objTblModLis=new ZafTblModLis();
             objTblMod.addTableModelListener(objTblModLis);*/
            zafTblCelRenChkLab = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKTRA).setCellRenderer(zafTblCelRenChkLab);
            zafTblCelEdiChkLab = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHKTRA).setCellEditor(zafTblCelEdiChkLab);

            //cambios realizados a la tabla
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public void Configura_ventana_consulta() {
        configurarVenConTipDoc();
        configurarVenConDep();
        configurarVenConMotHorSupExt();
        configurarTabla();

        if (blnEstCarSolHSE) {
            vecDat = null;
            cargarDatos();
        }
    }

    private boolean cargarDatos() {
        boolean blnRes = true;
        try {
            conCab = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conCab != null) {

                if (cargarCabReg()) {
                    if (cargarDetReg()) {
                        blnRes = true;
                    }
                }
                //   objAsiDia.setDiarioModificado(false);
                blnHayCam = false;
            }
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     *
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg() {
        int intPosRel;
        boolean blnRes = true;
        String strSQL = "";
        String strSql = "", strStReg = "";
        Vector vecDataCon = null;

        try {
            con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement();
                strSQL = "";
                strSQL += "select * from (select c.*, f.tx_deslar as deslardep, g.tx_deslar as deslarmot,c.st_reg,h.tx_descor as txdescortipdoc,h.tx_deslar as txdeslartipdoc from ( ";
                strSQL += " select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a.ne_numdoc, a.co_dep, a.co_mot, a.fe_sol, a.ho_soldes, a.ho_solhas, ";
                strSQL += " a.tx_obs1, a.tx_obs2, a.st_reg  from tbm_cabSolHorSupExt a )c ";
                strSQL += " inner join tbm_dep f on (f.co_dep=c.co_dep) ";
                strSQL += " inner join tbm_motHorSupExt g on (g.co_mot=c.co_mot) ";
                strSQL += " inner join tbm_cabtipdoc h on (h.co_emp=c.co_emp AND h.co_loc=c.co_loc  AND  h.co_tipdoc=c.co_tipdoc) ";
                strSQL += " ) e ";

                strSQL += " WHERE co_emp = " + strCodEmpAut;
                strSQL += " AND co_loc = " + strCodLocAut;
                strSQL += " AND co_tipdoc = " + strCodTipDocAut;
                strSQL += " AND co_doc = " + strCodDocAut;

                rst = stm.executeQuery(strSQL);
                if (rst.next()) {

                    strStReg = rst.getString("st_reg");
                    txtDesCorTipDoc.setText(rst.getString("txdescortipdoc"));
                    txtDesLarTipDoc.setText(rst.getString("txdeslartipdoc"));
                    txtCodDep.setText(rst.getString("co_dep"));
                    txtDesDep.setText(rst.getString("deslardep"));
                    txtCodMot.setText(rst.getString("co_mot"));
                    txtDesMot.setText(rst.getString("deslarmot"));
                    txtNumDoc.setText(rst.getString("ne_numdoc"));
                    txtCodDoc.setText(rst.getString("co_doc"));
                    txtHorSolDes.setText(rst.getString("ho_soldes").substring(0, 5));
                    txtHorSolHas.setText(rst.getString("ho_solhas").substring(0, 5));
                    txtObs1.setText(rst.getString("tx_obs1"));
                    txtObs2.setText(rst.getString("tx_obs2"));

                    cboHHDes.setSelectedItem(rst.getString("ho_soldes").substring(0, 2));
                    cboMMDes.setSelectedItem(rst.getString("ho_soldes").substring(3, 5));

                    cboHHHas.setSelectedItem(rst.getString("ho_solhas").substring(0, 2));
                    cboMMHas.setSelectedItem(rst.getString("ho_solhas").substring(3, 5));

                    java.util.Date dateObj = rst.getDate("fe_doc");
                    if (dateObj == null) {
                        txtFecDoc.setText("");
                    } else {
                        java.util.Calendar calObj = java.util.Calendar.getInstance();
                        calObj.setTime(dateObj);
                        txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                calObj.get(java.util.Calendar.MONTH) + 1,
                                calObj.get(java.util.Calendar.YEAR));
                    }

                    dateObj = rst.getDate("fe_sol");
                    if (dateObj == null) {
                        txtFecSol.setText("");
                    } else {
                        java.util.Calendar calObj = java.util.Calendar.getInstance();
                        calObj.setTime(dateObj);
                        txtFecSol.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                calObj.get(java.util.Calendar.MONTH) + 1,
                                calObj.get(java.util.Calendar.YEAR));
                    }

                }

                strSql = "";
                strSql += "select ho_autdes,ho_authas from tbm_autsolhorsupext";
                strSql += " where co_emp=" + strCodEmpAut;
                strSql += " and co_loc=" + strCodLocAut;
                strSql += " and co_tipdoc=" + strCodTipDocAut;
                strSql += " and co_doc=" + strCodDocAut;
                strSql += "and co_reg in (select max(co_reg) from tbm_autsolhorsupext";
                strSql += " where co_emp=" + strCodEmpAut;
                strSql += " and co_loc=" + strCodLocAut;
                strSql += " and co_tipdoc=" + strCodTipDocAut;
                strSql += " and co_doc=" + strCodDocAut + ")";
                System.out.println("autorizad: " + strSql);
                rst = stm.executeQuery(strSql);
                String strHoAutDes = "";
                String strHoAutHas = "";
                if (rst.next()) {
                    strHoAutDes = rst.getString("ho_autdes");
                    strHoAutHas = rst.getString("ho_authas");
                    if (strHoAutDes == null) {
                        cboHHAutDes.setSelectedIndex(0);
                        cboMMAutDes.setSelectedIndex(0);
                    } else {
                        String strArrHoAutDes[] = strHoAutDes.split(":");
                        cboHHAutDes.setSelectedItem(strArrHoAutDes[0]);
                        cboMMAutDes.setSelectedItem(strArrHoAutDes[1]);
                    }
                    if (strHoAutHas == null) {
                        cboHHAutHas.setSelectedIndex(0);
                        cboMMAutHas.setSelectedIndex(0);
                    } else {
                        String strArrHoAutHas[] = strHoAutHas.split(":");
                        cboHHAutHas.setSelectedItem(strArrHoAutHas[0]);
                        cboMMAutHas.setSelectedItem(strArrHoAutHas[1]);
                    }
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst = null;
            stm = null;
            con = null;
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean cargarDetReg() {
        String strSQL;
        boolean blnRes = true;

        try {
            //            objTooBar.setMenSis("Obteniendo datos...");
            con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) {
                objTblMod.removeAllRows();
                vecDat = new Vector();
                stm = con.createStatement();
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL = "select * from (select c.*, (d.tx_ape || ' ' || d.tx_nom) AS nomCom, f.tx_deslar as deslardep, g.tx_deslar as deslarmot,c.st_reg,h.tx_descor as txdescortipdoc,h.tx_deslar as txdeslartipdoc from ( "
                        + "select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a.ne_numdoc, a.co_dep, a.co_mot, a.fe_sol, a.ho_soldes, a.ho_solhas,"
                        + "a.tx_obs1, a.tx_obs2, a.st_reg,b.co_reg, b.co_tra  from tbm_cabSolHorSupExt a inner join tbm_detSolHorSupExt b on "
                        + "(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc) )c "
                        + "inner join tbm_tra d on (c.co_tra=d.co_tra)  "
                        + "inner join tbm_dep f on (f.co_dep=c.co_dep) "
                        + "inner join tbm_motHorSupExt g on (g.co_mot=c.co_mot) "
                        + "inner join tbm_cabtipdoc h on (h.co_emp=c.co_emp AND h.co_loc=c.co_loc  AND  h.co_tipdoc=c.co_tipdoc) "
                        + ") e "
                        + " left outer join tbm_cabconasitra i on (i.fe_dia=e.fe_sol and i.co_tra=e.co_tra)"
                        + " left outer join tbm_calciu j on (j.fe_dia=e.fe_sol and j.co_ciu=1)"
                        + " WHERE e.co_emp=" + strCodEmpAut + " AND e.co_loc=" + strCodLocAut + "  AND "
                        + " e.co_tipdoc=" + strCodTipDocAut + " AND e.co_doc=" + strCodDocAut
                        + " order by nomcom";
                System.out.println("detalle sol: " + strSQL);
                rst = stm.executeQuery(strSQL);

                while (rst.next()) {
                    Vector vecReg = new Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_CHKTRA, Boolean.TRUE);
                    vecReg.add(INT_TBL_CODEMPPAG, rst.getInt("co_emp"));
                    vecReg.add(INT_TBL_CODTRA, rst.getInt("co_tra"));
                    vecReg.add(INT_TBL_NOMTRA, rst.getString("nomCom"));
                    vecReg.add(INT_TBL_COREG, rst.getString("co_reg"));

////                    String strHoSolDes=rst.getString("ho_soldes");
////                    String strHoSolHas=rst.getString("ho_solhas");
////                    
////                    String strArrHoSolDes[]=strHoSolDes.split(":");**
//                    if(rst.getString("ho_ent")!=null){
//                        String strHoEnt=rst.getString("ho_ent").substring(0,5);
//                        String str[] = strHoEnt.split(":");
//                        if(str[0].compareTo("00")==0){
//                            strHoEnt="12:"+str[1];
//                        }
//                        vecReg.add(INT_TBL_HORENT, strHoEnt);
//                    }else{
//                        vecReg.add(INT_TBL_HORENT, "");
//                    }
//                    
//                    if(rst.getString("ho_sal")!=null){
//                        String strHoSal=rst.getString("ho_sal").substring(0,5);
//                        String strSal[] = strHoSal.split(":");
//                        if(strSal[0].compareTo("00")==0){
//                            strHoSal="12:"+strSal[1];
//                        }
//                        vecReg.add(INT_TBL_HORSAL, strHoSal);
//                    }else{
//                        vecReg.add(INT_TBL_HORSAL, "");
//                    }
                    if (objZafParSis.getCodigoMenu() == 2862 || objZafParSis.getCodigoMenu() == 3485) {
                        String strHoSolDes = "";
                        if (rst.getString("ho_soldes") != null) {
                            strHoSolDes = rst.getString("ho_soldes");
                        }

                        //       String strHoSolHas=rst.getString("ho_solhas");
                        String strArrHoSolDes[];
                        //       boolean blnValEntSal=false;
                        int intValEntSal = 0;//0 no se valida, 1 se valida la entrada, 2 se valida la salida, 3 feriados (entrada y salida)

                        String strTipDia = rst.getString("tx_tipdia");

                        if (strTipDia == null) {
                            vecReg.add(INT_TBL_HORENT, "");
                            vecReg.add(INT_TBL_HORSAL, "");
                        } else {

                            if (strTipDia.compareTo("F") == 0) {
                                intValEntSal = 3;
                            } else {
                                if (strHoSolDes != null) {
                                    strArrHoSolDes = strHoSolDes.split(":");
                                    int intHHSolDes = Integer.valueOf(strArrHoSolDes[0]);
                                    if (intHHSolDes >= 12) {
                                        intValEntSal = 2;
                                    } else {
                                        intValEntSal = 1;
                                    }
                                }
                            }

                            if (intValEntSal == 1) {

                                if (rst.getString("ho_ent") == null) {
                                    vecReg.add(INT_TBL_HORENT, "");
                                } else {
                                    String strHoEnt = rst.getString("ho_ent").substring(0, 5);
                                    String str[] = strHoEnt.split(":");
                                    if (str[0].compareTo("00") == 0) {
                                        strHoEnt = "12:" + str[1];
                                    }
                                    vecReg.add(INT_TBL_HORENT, strHoEnt);
                                }
                                vecReg.add(INT_TBL_HORSAL, "");

                            } else if (intValEntSal == 2) {

                                vecReg.add(INT_TBL_HORENT, "");

                                if (rst.getString("ho_sal") == null) {
                                    vecReg.add(INT_TBL_HORSAL, "");

                                } else {
                                    String strHoSal = rst.getString("ho_sal").substring(0, 5);
                                    String str[] = strHoSal.split(":");
                                    if (str[0].compareTo("00") == 0) {
                                        strHoSal = "12:" + str[1];
                                    }
                                    vecReg.add(INT_TBL_HORSAL, strHoSal);
                                }

                            } else if (intValEntSal == 3) {
                                if (rst.getString("ho_ent") == null) {
                                    vecReg.add(INT_TBL_HORENT, "");
                                } else {
                                    String strHoEnt = rst.getString("ho_ent").substring(0, 5);
                                    String str[] = strHoEnt.split(":");
                                    if (str[0].compareTo("00") == 0) {
                                        strHoEnt = "12:" + str[1];
                                    }
                                    vecReg.add(INT_TBL_HORENT, strHoEnt);
                                }
                                if (rst.getString("ho_sal") == null) {
                                    vecReg.add(INT_TBL_HORSAL, "");

                                } else {
                                    String strHoSal = rst.getString("ho_sal").substring(0, 5);
                                    String str[] = strHoSal.split(":");
                                    if (str[0].compareTo("00") == 0) {
                                        strHoSal = "12:" + str[1];
                                    }
                                    vecReg.add(INT_TBL_HORSAL, strHoSal);
                                }
                            }
                        }

                    } else {
                        vecReg.add(INT_TBL_HORENT, "");
                        vecReg.add(INT_TBL_HORSAL, "");
                    }

                    vecDat.add(vecReg);
                }

                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                vecDat = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        buttonGroup1 = new javax.swing.ButtonGroup();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        PanTabGen = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        butBusTipDoc = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        lblNumDoc = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCodDep = new javax.swing.JTextField();
        txtDesDep = new javax.swing.JTextField();
        butDep = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodMot = new javax.swing.JTextField();
        txtDesMot = new javax.swing.JTextField();
        butMot = new javax.swing.JButton();
        lblNumDoc1 = new javax.swing.JLabel();
        lblFecDto1 = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        txtHorSolHas = new javax.swing.JTextField();
        txtCodDoc = new javax.swing.JTextField();
        cboMMHas = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        cboHHHas = new javax.swing.JComboBox();
        txtDesLarTipDoc = new javax.swing.JTextField();
        lblNumDoc2 = new javax.swing.JLabel();
        lblHorAutDes = new javax.swing.JLabel();
        cboHHDes = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        cboMMDes = new javax.swing.JComboBox();
        txtHorSolDes = new javax.swing.JTextField();
        lblNumDoc4 = new javax.swing.JLabel();
        cboHHAutDes = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        cboMMAutDes = new javax.swing.JComboBox();
        lblHorAutHas = new javax.swing.JLabel();
        cboHHAutHas = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        cboMMAutHas = new javax.swing.JComboBox();
        panDetTraDep = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panObs = new javax.swing.JPanel();
        panLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panTxa = new javax.swing.JPanel();
        spnObs3 = new javax.swing.JScrollPane();
        txtObs1 = new javax.swing.JTextArea();
        spnObs4 = new javax.swing.JScrollPane();
        txtObs2 = new javax.swing.JTextArea();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
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

        panCen.setLayout(new java.awt.BorderLayout());

        tabGen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        tabGen.setPreferredSize(new java.awt.Dimension(115, 100));

        panTabGen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panTabGen.setPreferredSize(new java.awt.Dimension(100, 90));
        panTabGen.setLayout(new java.awt.BorderLayout());

        PanTabGen.setPreferredSize(new java.awt.Dimension(100, 110));
        PanTabGen.setLayout(null);

        lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDoc.setText("Tipo de Documento:"); // NOI18N
        PanTabGen.add(lblTipDoc);
        lblTipDoc.setBounds(10, 10, 120, 20);

        butBusTipDoc.setText("jButton1"); // NOI18N
        butBusTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusTipDocActionPerformed(evt);
            }
        });
        PanTabGen.add(butBusTipDoc);
        butBusTipDoc.setBounds(420, 10, 20, 20);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecDoc.setText("Fecha del Documento:"); // NOI18N
        PanTabGen.add(lblFecDoc);
        lblFecDoc.setBounds(450, 10, 130, 20);

        txtDesCorTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        PanTabGen.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(140, 10, 70, 20);

        lblNumDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc.setText("Número de Documento:"); // NOI18N
        PanTabGen.add(lblNumDoc);
        lblNumDoc.setBounds(450, 30, 140, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Departamento:"); // NOI18N
        PanTabGen.add(jLabel5);
        jLabel5.setBounds(10, 30, 110, 20);

        txtCodDep.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodDep.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDepActionPerformed(evt);
            }
        });
        txtCodDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDepFocusLost(evt);
            }
        });
        PanTabGen.add(txtCodDep);
        txtCodDep.setBounds(140, 30, 70, 20);

        txtDesDep.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesDepActionPerformed(evt);
            }
        });
        txtDesDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesDepFocusLost(evt);
            }
        });
        PanTabGen.add(txtDesDep);
        txtDesDep.setBounds(210, 30, 210, 20);

        butDep.setText(".."); // NOI18N
        butDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDepActionPerformed(evt);
            }
        });
        butDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                butDepFocusGained(evt);
            }
        });
        PanTabGen.add(butDep);
        butDep.setBounds(420, 30, 20, 20);

        jLabel6.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel6.setText(":"); // NOI18N
        PanTabGen.add(jLabel6);
        jLabel6.setBounds(522, 70, 10, 20);

        txtCodMot.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodMot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodMot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodMotActionPerformed(evt);
            }
        });
        txtCodMot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodMotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodMotFocusLost(evt);
            }
        });
        PanTabGen.add(txtCodMot);
        txtCodMot.setBounds(140, 50, 70, 20);

        txtDesMot.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesMot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesMotActionPerformed(evt);
            }
        });
        txtDesMot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesMotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesMotFocusLost(evt);
            }
        });
        PanTabGen.add(txtDesMot);
        txtDesMot.setBounds(210, 50, 210, 20);

        butMot.setText(".."); // NOI18N
        butMot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butMotActionPerformed(evt);
            }
        });
        PanTabGen.add(butMot);
        butMot.setBounds(420, 50, 20, 20);

        lblNumDoc1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc1.setText("Hasta:"); // NOI18N
        PanTabGen.add(lblNumDoc1);
        lblNumDoc1.setBounds(430, 70, 40, 20);

        lblFecDto1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecDto1.setText("Fecha solicitada:"); // NOI18N
        PanTabGen.add(lblFecDto1);
        lblFecDto1.setBounds(10, 70, 120, 20);

        txtNumDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDocFocusGained(evt);
            }
        });
        PanTabGen.add(txtNumDoc);
        txtNumDoc.setBounds(590, 30, 80, 20);

        txtHorSolHas.setBackground(objZafParSis.getColorCamposObligatorios());
        txtHorSolHas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHorSolHasActionPerformed(evt);
            }
        });
        txtHorSolHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHorSolHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHorSolHasFocusLost(evt);
            }
        });
        PanTabGen.add(txtHorSolHas);
        txtHorSolHas.setBounds(580, 70, 80, 20);
        txtHorSolHas.setVisible(false);

        txtCodDoc.setBackground(objZafParSis.getColorCamposSistema());
        txtCodDoc.setEditable(false);
        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        PanTabGen.add(txtCodDoc);
        txtCodDoc.setBounds(590, 50, 80, 20);

        cboMMHas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "15", "30", "45" }));
        PanTabGen.add(cboMMHas);
        cboMMHas.setBounds(530, 70, 50, 20);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel7.setText("Motivo:"); // NOI18N
        PanTabGen.add(jLabel7);
        jLabel7.setBounds(10, 50, 110, 20);

        cboHHHas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        PanTabGen.add(cboHHHas);
        cboHHHas.setBounds(470, 70, 50, 20);

        txtDesLarTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        PanTabGen.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(210, 10, 210, 20);

        lblNumDoc2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc2.setText("Código del Documento:"); // NOI18N
        PanTabGen.add(lblNumDoc2);
        lblNumDoc2.setBounds(450, 50, 140, 20);

        lblHorAutDes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblHorAutDes.setText("Hora autorizada desde:"); // NOI18N
        PanTabGen.add(lblHorAutDes);
        lblHorAutDes.setBounds(10, 90, 120, 20);

        cboHHDes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cboHHDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHHDesActionPerformed(evt);
            }
        });
        PanTabGen.add(cboHHDes);
        cboHHDes.setBounds(310, 70, 50, 20);

        jLabel8.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel8.setText(":"); // NOI18N
        PanTabGen.add(jLabel8);
        jLabel8.setBounds(362, 70, 10, 20);

        cboMMDes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "15", "30", "45" }));
        PanTabGen.add(cboMMDes);
        cboMMDes.setBounds(370, 70, 50, 20);

        txtHorSolDes.setBackground(objZafParSis.getColorCamposObligatorios());
        txtHorSolDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHorSolDesActionPerformed(evt);
            }
        });
        txtHorSolDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHorSolDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHorSolDesFocusLost(evt);
            }
        });
        PanTabGen.add(txtHorSolDes);
        txtHorSolDes.setBounds(420, 70, 80, 20);
        txtHorSolDes.setVisible(false);

        lblNumDoc4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc4.setText("Desde:"); // NOI18N
        PanTabGen.add(lblNumDoc4);
        lblNumDoc4.setBounds(270, 70, 40, 20);

        cboHHAutDes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cboHHAutDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHHAutDesActionPerformed(evt);
            }
        });
        PanTabGen.add(cboHHAutDes);
        cboHHAutDes.setBounds(140, 90, 50, 20);
        cboHHDes.setSelectedIndex(18);

        jLabel9.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel9.setText(":"); // NOI18N
        PanTabGen.add(jLabel9);
        jLabel9.setBounds(190, 90, 10, 20);

        cboMMAutDes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "15", "30", "45" }));
        PanTabGen.add(cboMMAutDes);
        cboMMAutDes.setBounds(200, 90, 50, 20);

        lblHorAutHas.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblHorAutHas.setText("Hora autorizada hasta:"); // NOI18N
        PanTabGen.add(lblHorAutHas);
        lblHorAutHas.setBounds(270, 90, 120, 20);

        cboHHAutHas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cboHHAutHas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHHAutHasActionPerformed(evt);
            }
        });
        PanTabGen.add(cboHHAutHas);
        cboHHAutHas.setBounds(400, 90, 50, 20);
        cboHHDes.setSelectedIndex(18);

        jLabel10.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel10.setText(":"); // NOI18N
        PanTabGen.add(jLabel10);
        jLabel10.setBounds(450, 90, 10, 20);

        cboMMAutHas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "15", "30", "45" }));
        PanTabGen.add(cboMMAutHas);
        cboMMAutHas.setBounds(460, 90, 50, 20);

        panTabGen.add(PanTabGen, java.awt.BorderLayout.PAGE_START);

        panDetTraDep.setLayout(new java.awt.BorderLayout());

        scrTbl.setToolTipText("");

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
        tblDat.setColumnSelectionAllowed(true);
        scrTbl.setViewportView(tblDat);
        tblDat.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        panDetTraDep.add(scrTbl, java.awt.BorderLayout.CENTER);

        panTabGen.add(panDetTraDep, java.awt.BorderLayout.CENTER);

        panObs.setPreferredSize(new java.awt.Dimension(100, 80));
        panObs.setLayout(new java.awt.BorderLayout());

        panLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs1.setText("Observación 1:"); // NOI18N
        lblObs1.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs1.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs1.setPreferredSize(new java.awt.Dimension(92, 15));
        panLbl.add(lblObs1);

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblObs2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs2.setText("Observación 2:"); // NOI18N
        lblObs2.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs2.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs2.setPreferredSize(new java.awt.Dimension(92, 15));
        lblObs2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        panLbl.add(lblObs2);

        panObs.add(panLbl, java.awt.BorderLayout.WEST);

        panTxa.setLayout(new java.awt.GridLayout(2, 1, 0, 1));

        txtObs1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtObs1.setLineWrap(true);
        spnObs3.setViewportView(txtObs1);

        panTxa.add(spnObs3);

        txtObs2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtObs2.setLineWrap(true);
        spnObs4.setViewportView(txtObs2);

        panTxa.add(spnObs4);

        panObs.add(panTxa, java.awt.BorderLayout.CENTER);

        panTabGen.add(panObs, java.awt.BorderLayout.SOUTH);

        tabGen.addTab("General", panTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panTit.setPreferredSize(new java.awt.Dimension(100, 30));

        lblTit.setText("titulo"); // NOI18N
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.PAGE_START);

        setSize(new java.awt.Dimension(700, 450));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
    txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
    strDesCorTipDoc = txtDesCorTipDoc.getText();
    txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
    if (txtDesCorTipDoc.isEditable()) {
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(1);
            }
        } else {
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }
}//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtFecDocActionPerformed(java.awt.event.ActionEvent evt) {
        txtFecDoc.transferFocus();
    }

    private void txtFecSolActionPerformed(java.awt.event.ActionEvent evt) {
        txtFecSol.transferFocus();
    }

private void butBusTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusTipDocActionPerformed
    mostrarVenConTipDoc(0);
}//GEN-LAST:event_butBusTipDocActionPerformed

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.setVisible(true);
                    if (vcoTipDoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                        if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7)))) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado() == 'n') {
                                strAux = vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux) ? Integer.parseInt(strAux) + 1 : 1));
                            }
                            intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        }
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText())) {
                        if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7)))) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado() == 'n') {
                                strAux = vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux) ? Integer.parseInt(strAux) + 1 : 1));
                            }
                            intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        } else {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    } else {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7)))) {
                                txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                                txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                                txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                                if (objTooBar.getEstado() == 'n') {
                                    strAux = vcoTipDoc.getValueAt(4);
                                    txtNumDoc.setText("" + (objUti.isNumero(strAux) ? Integer.parseInt(strAux) + 1 : 1));
                                }
                                intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                                txtNumDoc.selectAll();
                                txtNumDoc.requestFocus();
                            } else {
                                txtDesCorTipDoc.setText(strDesCorTipDoc);
                            }
                        } else {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText())) {
                        if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7)))) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado() == 'n') {
                                strAux = vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux) ? Integer.parseInt(strAux) + 1 : 1));
                            }
                            intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        } else {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    } else {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7)))) {
                                txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                                txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                                txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                                if (objTooBar.getEstado() == 'n') {
                                    strAux = vcoTipDoc.getValueAt(4);
                                    txtNumDoc.setText("" + (objUti.isNumero(strAux) ? Integer.parseInt(strAux) + 1 : 1));
                                }
                                intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                                txtNumDoc.selectAll();
                                txtNumDoc.requestFocus();
                            } else {
                                txtDesLarTipDoc.setText(strDesLarTipDoc);
                            }
                        } else {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
    Configura_ventana_consulta();
}//GEN-LAST:event_formInternalFrameOpened

private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
    String strMsg = "¿Está Seguro que desea cerrar este programa?";
    javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
    String strTit;
    strTit = "Mensaje del sistema Zafiro";
    if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == 0) {
        if (rstCab != null) {
            rstCab = null;
        }
        if (STM_GLO != null) {
            STM_GLO = null;
        }

        strCodEmpAut = "";
        strCodLocAut = "";
        strCodTipDocAut = "";
        strCodDocAut = "";
        vecDat = null;
        System.gc();
        dispose();
    }
}//GEN-LAST:event_formInternalFrameClosing

    private void txtCodDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDepActionPerformed
        txtCodDep.transferFocus();
        if (!txtCodDep.equals("")) {
            insertarDetalle();
        } else {
            objTblMod.removeAllRows();
        }

    }//GEN-LAST:event_txtCodDepActionPerformed

    private void txtCodDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusGained
        strCodDep = txtCodDep.getText();
        txtCodDep.selectAll();
    }//GEN-LAST:event_txtCodDepFocusGained

    private void txtCodDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusLost
        if (!txtCodDep.getText().equalsIgnoreCase(strCodDep)) {
            if (txtCodDep.getText().equals("")) {
                txtCodDep.setText("");
                txtDesDep.setText("");
            } else {
                BuscarDep("a1.co_dep", txtCodDep.getText(), 0);
            }
        } else {
            txtCodDep.setText(strCodDep);
        }
    }//GEN-LAST:event_txtCodDepFocusLost

    public void BuscarDep(String campo, String strBusqueda, int tipo) {
        vcoDep.setTitle("Listado de Departamentos");
        if (vcoDep.buscar(campo, strBusqueda)) {
            txtCodDep.setText(vcoDep.getValueAt(1));
            txtDesDep.setText(vcoDep.getValueAt(3));
        } else {
            vcoDep.setCampoBusqueda(tipo);
            vcoDep.cargarDatos();
            vcoDep.show();
            if (vcoDep.getSelectedButton() == vcoDep.INT_BUT_ACE) {
                txtCodDep.setText(vcoDep.getValueAt(1));
                txtDesDep.setText(vcoDep.getValueAt(3));
            } else {
                txtCodDep.setText(strCodDep);
                txtDesDep.setText(strDesDep);
            }
        }
    }

    public void BuscarMotHorSupExt(String campo, String strBusqueda, int tipo) {
        objVenConMotHorSupExt.setTitle("Listado de Motivos de Horas Suplementarias/Extraordinarias");
        if (objVenConMotHorSupExt.buscar(campo, strBusqueda)) {
            txtCodMot.setText(objVenConMotHorSupExt.getValueAt(1));
            txtDesMot.setText(objVenConMotHorSupExt.getValueAt(2));
        } else {
            objVenConMotHorSupExt.setCampoBusqueda(tipo);
            objVenConMotHorSupExt.cargarDatos();
            objVenConMotHorSupExt.show();
            if (objVenConMotHorSupExt.getSelectedButton() == objVenConMotHorSupExt.INT_BUT_ACE) {
                txtCodMot.setText(objVenConMotHorSupExt.getValueAt(1));
                txtDesMot.setText(objVenConMotHorSupExt.getValueAt(2));
            } else {
                txtCodMot.setText(strCodMot);
                txtDesMot.setText(strDesMot);
            }
        }
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConDep(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoDep.setCampoBusqueda(2);
                    vcoDep.setVisible(true);
                    if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtDesDep.setText(vcoDep.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.co_dep", txtCodDep.getText())) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtDesDep.setText(vcoDep.getValueAt(3));
                    } else {
                        vcoDep.setCampoBusqueda(1);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {

                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtDesDep.setText(vcoDep.getValueAt(3));
                        } else {
                            txtDesDep.setText(strDesDep);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoDep.setCampoBusqueda(2);
                    //vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.tx_desLar", txtDesDep.getText())) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtDesDep.setText(vcoDep.getValueAt(3));
                    } else {
                        vcoDep.setCampoBusqueda(2);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtDesDep.setText(vcoDep.getValueAt(3));
                        } else {
                            txtDesDep.setText(strDesDep);
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private void txtDesDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesDepActionPerformed
        txtDesDep.transferFocus();
    }//GEN-LAST:event_txtDesDepActionPerformed

    private void txtDesDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesDepFocusGained
        strDesDep = txtDesDep.getText();
        txtDesDep.selectAll();
    }//GEN-LAST:event_txtDesDepFocusGained

    private boolean insertarDetalle() {
        boolean blnRes = false;
        java.sql.Connection conn;
        java.sql.PreparedStatement preSta = null;
        java.sql.ResultSet resSet = null;
        Vector vecDataCon = null;

        objTblMod.removeAllRows();

        try {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null) {

                if (txtCodDep.getText() != null && !txtCodDep.getText().equals("")) {

                    String strSql = "select b.co_emp,a.co_tra, (a.tx_ape || ' ' || a.tx_nom) AS nomCom from tbm_tra a ";
                    strSql += " inner join tbm_traemp  b on (a.co_tra=b.co_tra and b.st_reg like 'A') ";
                    strSql += " where b.co_dep = ? and a.st_reg like 'A'";
                    strSql += " order by (a.tx_ape || ' ' || a.tx_nom)";

                    preSta = conn.prepareStatement(strSql);

                    if (Integer.parseInt(txtCodDep.getText()) > 0) {
                        preSta.setInt(1, Integer.parseInt(txtCodDep.getText().toString()));
                    } else {
                        preSta.setNull(1, Types.INTEGER);
                    }

                    resSet = preSta.executeQuery();

                    int i = 1;
                    if (resSet.next()) {
                        vecDataCon = new Vector();
                        do {
                            Vector vecReg = new Vector();
                            vecReg.add(INT_TBL_LINEA, "");
                            vecReg.add(INT_TBL_CHKTRA, false);
                            vecReg.add(INT_TBL_CODEMPPAG, resSet.getInt("co_emp"));
                            vecReg.add(INT_TBL_CODTRA, resSet.getInt("co_tra"));
                            vecReg.add(INT_TBL_NOMTRA, resSet.getString("nomCom"));
                            vecReg.add(INT_TBL_COREG, i++);
                            vecReg.add(INT_TBL_HORENT, "");
                            vecReg.add(INT_TBL_HORSAL, "");
                            vecDataCon.add(vecReg);
                        } while (resSet.next());
                    } else {
                        return false;
                    }

                    if (vecDataCon != null) {
                        if (vecDataCon.size() > 0) {
                            objTblMod.setData(vecDataCon);
                            blnRes = true;
                        }
                    } else {
                        vecDataCon = new Vector();
                        objTblMod.setData(vecDataCon);
                        blnRes = true;
                    }
                }
            }
        } catch (Exception ex) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, ex);
            ex.printStackTrace();
        } finally {
            try {
                resSet.close();
            } catch (Throwable ignore) {
            }
            try {
                preSta.close();
            } catch (Throwable ignore) {
            }
        }
        return blnRes;
    }

    private void txtDesDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesDepFocusLost
        if (txtDesDep.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesDep.getText().equalsIgnoreCase(strDesDep)) {
                if (txtDesDep.getText().equals("")) {
                    txtCodDep.setText("");
                    txtDesDep.setText("");
                } else {
                    mostrarVenConDep(2);
                    if (!txtCodDep.equals("")) {
                        insertarDetalle();
                    } else {
                        objTblMod.removeAllRows();
                    }
                }
            } else {
                txtDesDep.setText(strDesDep);
            }
        }

    }//GEN-LAST:event_txtDesDepFocusLost

    private void butDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDepActionPerformed
        mostrarVenConDep(0);
    }//GEN-LAST:event_butDepActionPerformed

    private void txtCodMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodMotActionPerformed
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";

        try {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());

            if (conn != null && (txtCodMot.getText().compareTo("") != 0)) {

                stmLoc = conn.createStatement();
                strSql = "select co_mot, tx_deslar from tbm_motHorSupExt where st_reg like 'A' and co_mot =  " + txtCodMot.getText();
                rstLoc = stmLoc.executeQuery(strSql);

                if (rstLoc.next()) {

                    txtDesMot.setText(rstLoc.getString("tx_deslar"));
                    txtDesMot.setHorizontalAlignment(2);
                    //txtNomDep.setEnabled(false);
                } else {
                    mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                    txtDesMot.setText("");
                    txtCodMot.setText("");
                }

                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;

            }
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }//GEN-LAST:event_txtCodMotActionPerformed

    private void txtCodMotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMotFocusGained
        txtCodMot.selectAll();
    }//GEN-LAST:event_txtCodMotFocusGained

    private void txtCodMotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMotFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodMotFocusLost

    private void txtDesMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesMotActionPerformed
        txtDesMot.transferFocus();
    }//GEN-LAST:event_txtDesMotActionPerformed

    private void txtDesMotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesMotFocusGained
        strDesMot = txtDesMot.getText();
        txtDesMot.selectAll();
    }//GEN-LAST:event_txtDesMotFocusGained

    private void txtDesMotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesMotFocusLost
        if (!txtDesMot.getText().equalsIgnoreCase(strDesMot)) {
            if (txtDesMot.getText().equals("")) {
                txtCodMot.setText("");
                txtDesMot.setText("");
            } else {
                BuscarMotHorSupExt("a.tx_deslar", txtDesMot.getText(), 1);
            }
        } else {
            txtDesMot.setText(strDesMot);
        }
    }//GEN-LAST:event_txtDesMotFocusLost

    private void butMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butMotActionPerformed
        try {
            zafVenMotHorSupExt.limpiar();
            zafVenMotHorSupExt.setCampoBusqueda(0);
            zafVenMotHorSupExt.setVisible(true);

            if (zafVenMotHorSupExt.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                txtCodMot.setText(zafVenMotHorSupExt.getValueAt(1));
                txtDesMot.setText(zafVenMotHorSupExt.getValueAt(3));
            }
        } catch (Exception ex) {
            objUti.mostrarMsgErr_F1(this, ex);
        }
    }//GEN-LAST:event_butMotActionPerformed

    private void txtHorSolHasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHorSolHasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHorSolHasActionPerformed

    private void txtHorSolHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorSolHasFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHorSolHasFocusGained

    private void txtHorSolHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorSolHasFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHorSolHasFocusLost

    private void butDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_butDepFocusGained
        if (!txtDesDep.equals("")) {
            insertarDetalle();
        } else {
            objTblMod.removeAllRows();
        }
    }//GEN-LAST:event_butDepFocusGained

    private void txtNumDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusGained
        txtNumDoc.selectAll();
    }//GEN-LAST:event_txtNumDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtHorSolDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHorSolDesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHorSolDesActionPerformed

    private void txtHorSolDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorSolDesFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHorSolDesFocusGained

    private void txtHorSolDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorSolDesFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHorSolDesFocusLost

    private void cboHHDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHHDesActionPerformed
        if (Integer.valueOf(cboHHDes.getSelectedItem().toString()) == 17) {
            //cboHHDes.setSelectedItem("18");
        }
    }//GEN-LAST:event_cboHHDesActionPerformed

    private void cboHHAutDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHHAutDesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboHHAutDesActionPerformed

    private void cboHHAutHasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHHAutHasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboHHAutHasActionPerformed

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     *
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void bloquea(javax.swing.JTextField txtFiel, boolean blnEst) {
        java.awt.Color colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }

    public void BuscarTipDoc(String campo, String strBusqueda, int tipo) {
        objVenConTipdoc.setTitle("Listado de Tipo de Documentos");
        if (objVenConTipdoc.buscar(campo, strBusqueda)) {
            txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
            txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
            txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
            strCodTipDoc = objVenConTipdoc.getValueAt(1);
            strMerIngEgr = objVenConTipdoc.getValueAt(4);
            strTipIngEgr = objVenConTipdoc.getValueAt(5);
        } else {
            objVenConTipdoc.setCampoBusqueda(tipo);
            objVenConTipdoc.cargarDatos();
            objVenConTipdoc.show();
            if (objVenConTipdoc.getSelectedButton() == objVenConTipdoc.INT_BUT_ACE) {
                txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
                txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
                txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
                strCodTipDoc = objVenConTipdoc.getValueAt(1);
            } else {
                txtCodTipDoc.setText(strCodTipDoc);
                txtDesCorTipDoc.setText(strDesCorTipDoc);
                txtDesLarTipDoc.setText(strDesLarTipDoc);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanTabGen;
    private javax.swing.JButton butBusTipDoc;
    private javax.swing.JButton butDep;
    private javax.swing.JButton butMot;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboHHAutDes;
    private javax.swing.JComboBox cboHHAutHas;
    private javax.swing.JComboBox cboHHDes;
    private javax.swing.JComboBox cboHHHas;
    private javax.swing.JComboBox cboMMAutDes;
    private javax.swing.JComboBox cboMMAutHas;
    private javax.swing.JComboBox cboMMDes;
    private javax.swing.JComboBox cboMMHas;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblFecDto1;
    private javax.swing.JLabel lblHorAutDes;
    private javax.swing.JLabel lblHorAutHas;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblNumDoc1;
    private javax.swing.JLabel lblNumDoc2;
    private javax.swing.JLabel lblNumDoc4;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panDetTraDep;
    private javax.swing.JPanel panLbl;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTit;
    private javax.swing.JPanel panTxa;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JScrollPane spnObs3;
    private javax.swing.JScrollPane spnObs4;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodDep;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodMot;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesDep;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDesMot;
    private javax.swing.JTextField txtHorSolDes;
    private javax.swing.JTextField txtHorSolHas;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextArea txtObs1;
    private javax.swing.JTextArea txtObs2;
    // End of variables declaration//GEN-END:variables

    private void MensajeInf(String strMensaje) {
        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        obj.showMessageDialog(this, strMensaje, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void setLocationRelativeTo(Object object) {
        //        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public class mitoolbar extends ZafToolBar {

        public mitoolbar(javax.swing.JInternalFrame jfrThis) {
            super(jfrThis, objZafParSis);
        }

        public boolean anular() {
            boolean blnRes = false;
            java.sql.Connection conn;
            try {
                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) {
                    conn.setAutoCommit(false);

                    strAux = objTooBar.getEstadoRegistro();
                    if (strAux.equals("Eliminado")) {
                        MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                        blnRes = true;
                    }
                    if (strAux.equals("Anulado")) {
                        MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                        blnRes = true;
                    }
                    if (!blnRes) {

                        if (anularReg(conn)) {

                            conn.commit();
                            blnRes = true;
                            objTooBar.setEstadoRegistro("Anulado");
                            blnHayCam = false;
                        } else {
                            conn.rollback();
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

        private boolean anularReg(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            String strSql = "";

            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "UPDATE tbm_cabSolHorSupExt SET st_reg='I', fe_ultMod=" + objZafParSis.getFuncionFechaHoraBaseDatos() + ", co_usrMod=" + objZafParSis.getCodigoUsuario()
                            + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + "AND  co_tipdoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText();
                    stmLoc.executeUpdate(strSql);

                    stmLoc.close();
                    stmLoc = null;

                    blnRes = true;
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

        public void clickSiguiente() {
            try {
                if (rstCab != null) {
                    abrirCon();
                    if (!rstCab.isLast()) {
                        //if(blnHayCam || objTblMod.isDataModelChanged()) {
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

        public void clickAnular() {

        }

        public void clickConsultar() {
            clnTextos();
            noEditable(false);
            bloquea(txtNumDoc, false);
            cargarTipoDoc(2);

        }

        public void clickEliminar() {
            //          noEditable(false);
        }

        public void clickInsertar() {
            try {
                clnTextos();
                //noEditable(false);
                java.awt.Color colBack;
                colBack = txtCodDoc.getBackground();
                txtCodDoc.setEditable(false);
                txtCodDoc.setBackground(colBack);

                datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                java.util.Date dateObj = datFecAux;
                java.util.Calendar calObj = java.util.Calendar.getInstance();
                calObj.setTime(dateObj);
                txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                        calObj.get(java.util.Calendar.MONTH) + 1,
                        calObj.get(java.util.Calendar.YEAR));

                txtFecSol.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                        calObj.get(java.util.Calendar.MONTH) + 1,
                        calObj.get(java.util.Calendar.YEAR));

                mostrarTipDocPre();
                mostrarDepPre();

                if (rstCab != null) {
                    rstCab.close();
                    rstCab = null;
                }

                txtFecDoc.setEnabled(true);
                txtFecSol.setEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
                objUti.mostrarMsgErr_F1(this, e);
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

        public boolean eliminar() {
            boolean blnRes = false;
            java.sql.Connection conn;
            try {
                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) {
                    conn.setAutoCommit(false);

                    strAux = objTooBar.getEstadoRegistro();
                    if (strAux.equals("Eliminado")) {
                        MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                        blnRes = true;
                    }

                    if (!blnRes) {
                        if (eliminarReg(conn)) {
                            conn.commit();
                            blnRes = true;
                            objTooBar.setEstadoRegistro("Eliminado");
                            blnHayCam = false;
                        } else {
                            conn.rollback();
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

        private boolean eliminarReg(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLoc = null;
            String strSql = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    strSql = "UPDATE tbm_cabSolHorSupExt SET st_reg='E' , fe_ultMod=" + objZafParSis.getFuncionFechaHoraBaseDatos() + ", co_usrMod=" + objZafParSis.getCodigoUsuario()
                            + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + "AND  co_tipdoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText();
                    stmLoc.executeUpdate(strSql);

                }

                stmLoc.close();
                stmLoc = null;
                blnRes = true;
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
         * Esta función determina si los campos son válidos.
         *
         * @return true: Si los campos son válidos.
         * <BR>false: En el caso contrario.
         */
        private boolean validaCampos() throws SQLException {
            //Validar el "Tipo de documento".
            if (txtCodTipDoc.getText().equals("")) {
                //tabFrm.setSelectedIndex(0);
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                txtDesCorTipDoc.requestFocus();
                return false;
            }
            //Validar el "Departamento".
            if (txtCodDep.getText().equals("")) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Departamento</FONT> es obligatorio.<BR>Escriba o seleccione un departamento y vuelva a intentarlo.</HTML>");
                txtCodDep.requestFocus();
                return false;
            }
            //Validar el "Motivo de horas s/e".
            if (txtCodMot.getText().equals("")) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Motivo de Horas S/E</FONT> es obligatorio.<BR>Escriba o seleccione un motivo de horas s/e y vuelva a intentarlo.</HTML>");
                txtCodMot.requestFocus();
                return false;
            }
            //Validar la "Fecha del Documento".
            if (!txtFecDoc.isFecha()) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de Documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha de documento y vuelva a intentarlo.</HTML>");
                txtFecDoc.requestFocus();
                return false;
            }
            //Validar el "Número de Documento".
            String str = txtNumDoc.getText();
            if (txtNumDoc.getText().equals("")) {
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de Documento</FONT> es obligatorio.<BR>Escriba un número de documento y vuelva a intentarlo.</HTML>");
                txtNumDoc.requestFocus();
                return false;
            } else {
                if (!objUti.isNumero(txtNumDoc.getText())) {
                    tabGen.setSelectedIndex(1);
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de Documento</FONT> contiene formato érroneo.<BR>Escriba un número de documento y vuelva a intentarlo.</HTML>");
                    txtNumDoc.requestFocus();
                    return false;
                }
            }
            //Validar la "Fecha Solicitada".
            if (!txtFecSol.isFecha()) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha Solicitada</FONT> es obligatorio.<BR>Escriba o seleccione una fecha solicitada y vuelva a intentarlo.</HTML>");
                txtFecSol.requestFocus();
                return false;
            } else {
                datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());

                String patron = "dd/MM/yyyy";
                SimpleDateFormat formato = new SimpleDateFormat(patron);
                System.out.println(formato.format(datFecAux));
                String strSplit[] = formato.format(datFecAux).split("/");
                Calendar calObjAux = java.util.Calendar.getInstance();
                calObjAux.set(Integer.valueOf(strSplit[2]), Integer.valueOf(strSplit[1]) - 1, Integer.valueOf(strSplit[0]));
                java.util.Date datFecAuxSis = calObjAux.getTime();

                java.util.Calendar calObj = java.util.Calendar.getInstance();
                int Fecha[] = txtFecSol.getFecha(txtFecSol.getText());
                //String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                calObj.set(Fecha[2], Fecha[1] - 1, Fecha[0]);
                java.util.Date datFecSolIng = calObj.getTime();

                long dif = datFecAuxSis.getTime() - datFecSolIng.getTime();
//            if(dif>0){
//                tabGen.setSelectedIndex(0);
//                System.out.println("Dias entre fechas: " + dif / 86400000L); // 3600 * 24 * 1000 
//                mostrarMsgInf("<HTML>No se permite que la <FONT COLOR=\"blue\">Fecha Solicitada</FONT> sea menor que la fecha actual.<BR>Escriba una fecha solicitada y vuelva a intentarlo.</HTML>");
//                txtFecSol.requestFocus();
//                return false;
//            }
            }

            //Validar la "Hora Solicitada Desde".
            txtHorSolDes.setText(cboHHDes.getSelectedItem() + ":" + cboMMDes.getSelectedItem());
            if (txtHorSolDes.getText().equals("")) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Hora Solicitada Desde</FONT> es obligatorio.<BR>Escriba una hora solicitada desde y vuelva a intentarlo.</HTML>");
                txtCodMot.requestFocus();
                return false;
            }
            try {

                if (txtHorSolDes.getText().length() == 5) {
                    int intHH = Integer.parseInt(txtHorSolDes.getText().replace(":", "").substring(0, 2));
                    int intMM = Integer.parseInt(txtHorSolDes.getText().replace(":", "").substring(2, 4));
                    if ((intHH >= 0 && intHH <= 24)) {
                        if ((intMM >= 0 && intMM <= 59)) {
                            java.sql.Time t = SparseToTime(txtHorSolDes.getText());
                        } else {
                            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Hora Solicitada Desde</FONT> contiene formato érroneo.<BR>Escriba una hora solicitada desde y vuelva a intentarlo.</HTML>");
                        }
                    } else {
                        mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Hora Solicitada Desde</FONT> contiene formato érroneo.<BR>Escriba una hora solicitada desde y vuelva a intentarlo.</HTML>");
                    }
                } else {
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Hora Solicitada Desde</FONT> contiene formato érroneo.<BR>Escriba una hora solicitada desde y vuelva a intentarlo.</HTML>");
                }

            } catch (Exception e) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Hora Solicitada Desde</FONT> contiene formato érroneo.<BR>Escriba una hora solicitada desde y vuelva a intentarlo.</HTML>");
                txtHorSolDes.requestFocus();
                return false;
            }

            //Validar la "Hora Solicitada Hasta".
            txtHorSolHas.setText(cboHHHas.getSelectedItem() + ":" + cboMMHas.getSelectedItem());
            if (txtHorSolHas.getText().equals("")) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Hora Solicitada Hasta</FONT> es obligatorio.<BR>Escriba una hora solicitada hasta y vuelva a intentarlo.</HTML>");
                txtCodMot.requestFocus();
                return false;
            }
            try {

                if (txtHorSolHas.getText().length() == 5) {
                    int intHH = Integer.parseInt(txtHorSolHas.getText().replace(":", "").substring(0, 2));
                    int intMM = Integer.parseInt(txtHorSolHas.getText().replace(":", "").substring(2, 4));
                    if ((intHH >= 0 && intHH <= 24)) {
                        if ((intMM >= 0 && intMM <= 59)) {
                            java.sql.Time t = SparseToTime(txtHorSolHas.getText());
                        } else {
                            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Hora Solicitada Hasta</FONT> contiene formato érroneo.<BR>Escriba una hora solicitada hasta y vuelva a intentarlo.</HTML>");
                        }
                    } else {
                        mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Hora Solicitada Hasta</FONT> contiene formato érroneo.<BR>Escriba una hora solicitada hasta y vuelva a intentarlo.</HTML>");
                    }
                } else {
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Hora Solicitada Hasta</FONT> contiene formato érroneo.<BR>Escriba una hora solicitada hasta y vuelva a intentarlo.</HTML>");
                }

            } catch (Exception e) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Hora Solicitada Hasta</FONT> contiene formato érroneo.<BR>Escriba una hora solicitada hasta y vuelva a intentarlo.</HTML>");
                txtHorSolHas.requestFocus();
                return false;
            }

            if (Integer.valueOf(cboHHHas.getSelectedItem().toString()) > Integer.valueOf(cboHHDes.getSelectedItem().toString())) {
                //if(Integer.valueOf(cboMMHas.getSelectedItem().toString())==Integer.valueOf(cboMMDes.getSelectedItem().toString())){
                //mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Hora Solicitada Hasta</FONT> no puede ser igual que la <FONT COLOR=\"blue\">Hora Solicitada Desde</FONT>.<BR>Escriba una hora solicitada hasta y vuelva a intentarlo.</HTML>");
                //return false;
//            if(Integer.valueOf(cboMMHas.getSelectedItem().toString())<Integer.valueOf(cboMMDes.getSelectedItem().toString())){
//                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Hora Solicitada Hasta</FONT> no puede ser menor que la <FONT COLOR=\"blue\">Hora Solicitada Desde</FONT>.<BR>Escriba una hora solicitada hasta y vuelva a intentarlo.</HTML>");
//                return false;
//            }
                //else if(Integer.valueOf(cboMMHas.getSelectedItem().toString())==Integer.valueOf(cboMMDes.getSelectedItem().toString())){
                //  mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Hora Solicitada Desde</FONT> no puede ser igual que la <FONT COLOR=\"blue\">Hora Solicitada Hasta</FONT>.<BR>Escriba una hora solicitada hasta y vuelva a intentarlo.</HTML>");
                //  return false;
                //}
            } else if (Integer.valueOf(cboHHHas.getSelectedItem().toString()) == Integer.valueOf(cboHHDes.getSelectedItem().toString())) {
                if (Integer.valueOf(cboMMHas.getSelectedItem().toString()) == Integer.valueOf(cboMMDes.getSelectedItem().toString())) {
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Hora Solicitada Desde</FONT> no puede ser igual que la <FONT COLOR=\"blue\">Hora Solicitada Hasta</FONT>.<BR>Escriba una hora solicitada hasta y vuelva a intentarlo.</HTML>");
                    return false;
                }
            } else {
                if (Integer.valueOf(cboHHHas.getSelectedItem().toString()) != 0) {
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Hora Solicitada Hasta</FONT> no puede ser menor que la <FONT COLOR=\"blue\">Hora Solicitada Desde</FONT>.<BR>Escriba una hora solicitada hasta y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
            /*boolean booRet=verificarHorSupCor(Integer.valueOf(cboHHDes.getSelectedItem().toString()), Integer.valueOf(cboHHHas.getSelectedItem().toString()), Integer.valueOf(cboMMDes.getSelectedItem().toString()), Integer.valueOf(cboMMHas.getSelectedItem().toString()));
             if(!booRet){
             mostrarMsgInf("<HTML> <FONT COLOR=\"blue\">Las horas no pueden estar dentro de horas laborales</FONT>.</HTML>");
             return false;
             }
        
             boolean booRan=verificarRanValMaxHor(Integer.valueOf(cboHHDes.getSelectedItem().toString()), Integer.valueOf(cboMMDes.getSelectedItem().toString()), Integer.valueOf(cboHHHas.getSelectedItem().toString()), Integer.valueOf(cboMMHas.getSelectedItem().toString()));
             if(!booRan){
             mostrarMsgInf("<HTML> <FONT COLOR=\"blue\">Las horas pueden estar dentro de: 05:00 AM - 08:00 AM y 17:15 PM - 00:00 AM</FONT>.</HTML>");
             return false;
             }*/

            Calendar calFecAct = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date dfa = df.parse(txtFecSol.getText());
                calFecAct.setTime(dfa);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            int dia = calFecAct.get(Calendar.DAY_OF_WEEK);
            System.out.println("Dia " + dia);

            boolean booRan = esHoraValida(Integer.valueOf(cboHHDes.getSelectedItem().toString()), Integer.valueOf(cboMMDes.getSelectedItem().toString()), Integer.valueOf(cboHHHas.getSelectedItem().toString()), Integer.valueOf(cboMMHas.getSelectedItem().toString()), dia);
            boolean booVer = esHoraTraValidaCruce(Integer.valueOf(cboHHDes.getSelectedItem().toString()), Integer.valueOf(cboMMDes.getSelectedItem().toString()), Integer.valueOf(cboHHHas.getSelectedItem().toString()), Integer.valueOf(cboMMHas.getSelectedItem().toString()), dia - 1);
            if (!booVer) {
                mostrarMsgInf("<HTML> <FONT COLOR=\"blue\">Algunos de los trabajadores seleccionados no se les puede asignar una solicitud de horas extras porque estan dentro de su horario laboral.</FONT>.</HTML>");
                return false;
            }
            if (!booRan) {
                mostrarMsgInf("<HTML> <FONT COLOR=\"blue\">Las horas pueden estar dentro de: 04:00 AM - 10:00 AM y 17:15 PM - 00:00 AM, los SABADOS DE 02:30 AM - 08:00 AM Y 12:30 A 23:00 y los DOMINGOS DE 08:00 AM a 23:00 PM. DEPENDIENDO DEL HORARIO DEL TRABAJADOR.</FONT>.</HTML>");
                return false;
            }
            int intDif = Integer.valueOf(cboHHHas.getSelectedItem().toString()) - Integer.valueOf(cboHHDes.getSelectedItem().toString());
//        if(intDif>=6){
//            String strMsj = "No es permitido crear solicitudes mayores a 5 horas. \n"+
//                  //" Se bebe solicitar Autorización a gerencía y dicha autorización acudir al Departamento de Sistemas. \n ";
//                    "Debe acudir al Departamento de Sistemas. \n ";
//            mostrarMsgInf(strMsj);
//            return false;
//        }

            return true;
        }

        public boolean insertar() {
            boolean blnRes = false;

            try {
                if (validaCampos()) {
                    if (validaFechaSolicitada()) {
                        if (validaFechaRangoEstablecido()) {//Establecido en reunion solo se podrá agregar solicitudes de maximo hasta 15 dias despues de la fecha actual y minimo 15 dias antes
                            if (insertarReg()) {
                            blnRes = true;
                        }
                        }else{
                            mostrarMsgInf("No se puede ingresar la solicitud debido a que la fecha solo puede ser 15 dias antes o despues de la fecha actual.");
                        }
                        
                    }

                }

            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean validaFechaSolicitada() {
            boolean blnRes = true;
            java.sql.Connection con = null;
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;
            String strSQL = "";

            try {
                con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (con != null) {
                    if (objZafParSis.getCodigoUsuario() == 1) {
                        return true;
                    } else {

                        stmLoc = con.createStatement();
                        strSQL += "select * from tbr_tipdocusr where co_emp =  " + objZafParSis.getCodigoEmpresa();
                        strSQL += " and co_loc = " + objZafParSis.getCodigoLocal();
                        strSQL += " and co_tipdoc = " + txtCodTipDoc.getText();
                        strSQL += " and co_mnu = " + objZafParSis.getCodigoMenu();
                        strSQL += " and co_usr = " + objZafParSis.getCodigoUsuario();
                        rstLoc = stmLoc.executeQuery(strSQL);
                        int ne_tipResModFecDoc = 0;

                        //fecha solicitada es igual o ya pasada pero no futura
                        datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());

                        String patron = "dd/MM/yyyy";
                        SimpleDateFormat formato = new SimpleDateFormat(patron);
                        System.out.println(formato.format(datFecAux));
                        String strSplit[] = formato.format(datFecAux).split("/");
                        Calendar calObjAux = java.util.Calendar.getInstance();
                        calObjAux.set(Integer.valueOf(strSplit[2]), Integer.valueOf(strSplit[1]) - 1, Integer.valueOf(strSplit[0]));
                        java.util.Date datFecAuxSis = calObjAux.getTime();

                        java.util.Calendar calObj = java.util.Calendar.getInstance();
                        int Fecha[] = txtFecSol.getFecha(txtFecSol.getText());
                        //String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                        calObj.set(Fecha[2], Fecha[1] - 1, Fecha[0]);
                        java.util.Date datFecSolIng = calObj.getTime();
                        long dif = datFecAuxSis.getTime() - datFecSolIng.getTime();
                        //            if(dif>0){
                        //                tabGen.setSelectedIndex(0);
                        //                System.out.println("Dias entre fechas: " + dif / 86400000L); // 3600 * 24 * 1000 
                        //                mostrarMsgInf("<HTML>No se permite que la <FONT COLOR=\"blue\">Fecha Solicitada</FONT> sea menor que la fecha actual.<BR>Escriba una fecha solicitada y vuelva a intentarlo.</HTML>");
                        //                txtFecSol.requestFocus();
                        //                return false;
                        //            }
                        if (rstLoc.next()) {
                            ne_tipResModFecDoc = rstLoc.getInt("ne_tipResModFecDoc");
                        }
                        if (ne_tipResModFecDoc == 1) {
                            if (txtFecSol.getText().compareTo(txtFecDoc.getText()) == 0) {
                                blnRes = true;
                            } else {
                                if (dif > 0) {
                                    mostrarMsgInf("<HTML>No se permite que la <FONT COLOR=\"blue\">Fecha Solicitada</FONT> sea menor que la fecha actual.<BR>Escriba una fecha solicitada y vuelva a intentarlo.</HTML>");
                                    txtFecSol.requestFocus();
                                    return false;
                                } else {
                                    mostrarMsgInf("<HTML>No se permite que la <FONT COLOR=\"blue\">Fecha Solicitada</FONT> sea mayor que la fecha actual.<BR>Escriba una fecha solicitada y vuelva a intentarlo.</HTML>");
                                    txtFecSol.requestFocus();
                                    return false;
                                }
                            }
                        }

                        if (ne_tipResModFecDoc == 2) {
                            if (txtFecSol.getText().compareTo(txtFecDoc.getText()) == 0) {
                                blnRes = true;
                            } else {
                                if (dif > 0) {
                                    blnRes = true;
                                } else {
                                    mostrarMsgInf("<HTML>No se permite que la <FONT COLOR=\"blue\">Fecha Solicitada</FONT> sea mayor que la fecha actual.<BR>Escriba una fecha solicitada y vuelva a intentarlo.</HTML>");
                                    txtFecSol.requestFocus();
                                    return false;
                                }
                            }

                        }

                    }
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } finally {
                try {
                    rstLoc.close();
                    rstLoc = null;
                } catch (Throwable ignore) {
                }
                try {
                    stmLoc.close();
                    stmLoc = null;
                } catch (Throwable ignore) {
                }
                try {
                    con.close();
                    con = null;
                } catch (Throwable ignore) {
                }
            }
            return blnRes;
        }
private boolean validaFechaRangoEstablecido() {
            boolean blnRes = true;
            Date objDateFechaDocumento = new Date(txtFecDoc.getFecha("/", "m/d/y"));
            Date objDateFechaSolicitada = new Date(txtFecSol.getFecha("/", "m/d/y"));
            GregorianCalendar objDiaAct = new GregorianCalendar();
            GregorianCalendar objCalMax = new GregorianCalendar();
            GregorianCalendar objCalMin = new GregorianCalendar();
            objCalMax.add(Calendar.DAY_OF_YEAR, 15);
            objCalMin.add(Calendar.DAY_OF_YEAR, -15);
            objDiaAct.setTime(objDateFechaDocumento);
            if (objDiaAct.compareTo(objCalMax)!=1 && objDiaAct.compareTo(objCalMin)!=-1) {
                blnRes=true;
            }else{
                return false;
            }
            objDiaAct.setTime(objDateFechaSolicitada);
             if (objDiaAct.compareTo(objCalMax)!=1 && objDiaAct.compareTo(objCalMin)!=-1) {
                blnRes=true;
            }else{
                return false;
            }
            return blnRes;
        }
        public boolean insertarReg() {
            boolean blnRes = false;
            java.sql.Connection con = null;
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;
            String strSQL = "";
            int intCodDoc = 0;
            int intNumDoc = 0;
            //int intGenCruAut=0;
            try {
                con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (con != null) {
                    con.setAutoCommit(false);

                    stmLoc = con.createStatement();

                    txtsql.setText("");

                    /*if(Integer.valueOf(cboHHDes.getSelectedItem().toString())==17){
                     cboHHDes.setSelectedItem("18");
                     }*/
                    strSQL = "SELECT case when (Max(co_doc)+1) is null then 1 else Max(co_doc)+1 end as co_doc  FROM tbm_cabsolhorsupext WHERE "
                            + " co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " and co_tipDoc = " + txtCodTipDoc.getText();
                    rstLoc = stmLoc.executeQuery(strSQL);
                    if (rstLoc.next()) {
                        intCodDoc = rstLoc.getInt("co_doc");
                    }
                    rstLoc.close();
                    rstLoc = null;

                    strSQL = "SELECT CASE WHEN (ne_ultdoc+1) is null THEN 1 ELSE ne_ultdoc+1 END AS numDoc FROM tbm_cabtipdoc "
                            + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + objZafParSis.getCodigoLocal() + " and co_tipdoc=" + txtCodTipDoc.getText();
                    rstLoc = stmLoc.executeQuery(strSQL);
                    if (rstLoc.next()) {
                        intNumDoc = rstLoc.getInt("numDoc");
                    }
                    rstLoc.close();
                    rstLoc = null;

                    int Fecha[] = txtFecDoc.getFecha(txtFecDoc.getText());
                    String strFecha = "#" + Fecha[2] + "/" + Fecha[1] + "/" + Fecha[0] + "#";
                    String FecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), "yyyy/MM/dd");

                    java.util.Date fe1 = objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy");
                    java.util.Date fe2 = objUti.parseDate(FecAux, "yyyy/MM/dd");
                    if (fe1.equals(fe2)) {
                        System.out.println("Igual ");

                    }

                    int intCom = 0;
                    for (int i = 0; i < tblDat.getRowCount(); i++) {

                        if (tblDat.getValueAt(i, INT_TBL_CHKTRA).equals(true)) {
                            intCom++;
                        }
                    }

                    if (intCom >= 1) {//if (objTblMod.getRowCountChecked(INT_TBL_DAT_CHK)<=0) {

                        /*VALIDAR QUE LOS EMPLEADOS ESCOGIDOS NO SE ENCUENTREN EN OTRA SOLICITUD*/
                        int FechaSol[] = txtFecSol.getFecha(txtFecSol.getText());
                        String strFechaSol = "#" + FechaSol[2] + "/" + FechaSol[1] + "/" + FechaSol[0] + "#";

                        /* strSQL="SELECT co_tra from tbm_detSolHorSupExt a "+
                         "WHERE co_doc in (select distinct co_doc FROM tbm_cabSolHorSupExt"
                         + " WHERE co_emp = "+objZafParSis.getCodigoEmpresa()+
                         " AND co_loc = "+objZafParSis.getCodigoLocal()+
                         " AND fe_sol='"+strFechaSol+"')";*/
                        strSQL = "SELECT co_tra , b.ho_soldes, b.ho_solhas"
                                + " from tbm_detSolHorSupExt a , tbm_cabSolHorSupExt as b"
                                + " where a.co_doc = b.co_doc "
                                + " and a.co_emp=b.co_emp"
                                + " and a.co_loc=b.co_loc"
                                + " and a.co_tipdoc=b.co_tipdoc"
                                + " and  a.co_emp = " + objZafParSis.getCodigoEmpresa()
                                + " and a.co_loc = " + objZafParSis.getCodigoLocal()
                                + " and b.fe_sol='" + strFechaSol + "'";

                        rstLoc = stmLoc.executeQuery(strSQL);

                        boolean blnExtTraSel = false;
                        while (rstLoc.next()) {
                            String strCoTraSel = rstLoc.getString("co_tra");
                            for (int i = 0; i < tblDat.getRowCount(); i++) {
                                //if(tblDat.getValueAt(i, INT_TBL_LINEA).toString().compareTo("M")==0){
                                if (tblDat.getValueAt(i, INT_TBL_CHKTRA).equals(true)) {
                                    if (tblDat.getValueAt(i, INT_TBL_CODTRA).toString().equals(strCoTraSel)) {
                                        boolean booTieneCruzeHoras = validarHoraNoAdmiteCruze(rstLoc.getString("ho_soldes"), rstLoc.getString("ho_solhas"), Integer.parseInt(cboHHDes.getSelectedItem().toString()), Integer.parseInt(cboMMDes.getSelectedItem().toString()), Integer.parseInt(cboHHHas.getSelectedItem().toString()), Integer.parseInt(cboMMHas.getSelectedItem().toString()));
                                        if (booTieneCruzeHoras) {
                                            blnExtTraSel = true;
                                            i = tblDat.getRowCount();
                                            break;
                                        }
                                    }
                                }
                            }
                            if (blnExtTraSel) {
                                break;
                            }
                        }

                        if (!blnExtTraSel) {
                            if (insertarRegSol(con, intCodDoc, intNumDoc, strFecha)) {

                                if (actualizarCabTipDoc(con, intCodDoc, intNumDoc)) {
                                    con.commit();
                                    blnRes = true;
                                    txtCodDoc.setText("" + intCodDoc);
                                    txtNumDoc.setText("" + intNumDoc);
                                } else {
                                    con.rollback();
                                }
                            } else {
                                con.rollback();
                            }
                        } else {
                            con.rollback();
                            mostrarMsgInf("HAY EMPLEADOS SELECCIONADOS PARA OTRA SOLICITUD, CON HORAS INTERCALADAS");
                            blnRes = false;
                        }
                    } else {
                        con.rollback();
                        mostrarMsgInf("NO HA SELECCIONADO EMPLEADOS QUE APLIQUEN A LA SOLICITUD");
                        blnRes = false;
                    }
                    con.close();
                    con = null;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } finally {
                try {
                    rstLoc.close();
                    rstLoc = null;
                } catch (Throwable ignore) {
                }
                try {
                    stmLoc.close();
                    stmLoc = null;
                } catch (Throwable ignore) {
                }
                try {
                    con.close();
                    con = null;
                } catch (Throwable ignore) {
                }
            }
            return blnRes;
        }

        private boolean actualizarCabTipDoc(java.sql.Connection conn, int intCodDoc, int intNumDoc) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            String strSql = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql += " ; UPDATE tbm_cabtipdoc SET ne_ultdoc=" + intNumDoc + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                            + " AND co_loc=" + objZafParSis.getCodigoLocal() + " AND co_tipdoc=" + txtCodTipDoc.getText();
                    stmLoc.executeUpdate(strSql);

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

        public boolean insertarRegSol(java.sql.Connection con, int intCodDoc, int intNumDoc, String strFecha) {
            boolean blnRes = false;
            String strFecSis = "";
            String strSql = "";
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;
            java.sql.Statement stmLocAut = null;
            java.sql.ResultSet rstLocAut = null;
            try {
                if (con != null) {
                    stmLoc = con.createStatement();

//            //************  PERMITE SABER SI EL NUMERO  ESTA DUPLICADO  *****************/
//            //if( ! ( (objZafParSis.getCodigoMenu()==2205) || (objZafParSis.getCodigoMenu()==2915) || (objZafParSis.getCodigoMenu()==286) || (objZafParSis.getCodigoMenu()==1974) ) ){
//                strSql ="select count(ne_numdoc) as num from tbm_cabingegrmerbod WHERE " +
//                        " co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" " +
//                        "and co_tipdoc="+txtCodTipDoc.getText()+" and ne_numdoc="+txtNumDoc.getText();
//                rstLoc = stmLoc.executeQuery(strSql);
//                if(rstLoc.next()){
//                    if(rstLoc.getInt("num") >= 1 ){
//                            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
//                            String strTit, strMsg;
//                            strTit="Mensaje del sistema Zafiro";
//                            strMsg=" No. de Confitmación ya existe... ?";
//                            oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE,null);
//                            blnRes=true;
//                }} 
//                rstLoc.close();
//                rstLoc=null;
//                if(blnRes){
//                    stmLoc.close();
//                    stmLoc=null;
//                    return false;
//                }
//                blnRes=false;
//            //}
//        //***********************************************************************************************/
                    strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());

                    String strSt_Imp = "N";
                    String strSt_Aut = "P";
                    String strSt_Reg = "A";
                    int intCodTipDoc = Integer.parseInt(txtCodTipDoc.getText());
                    int intNum_Doc = Integer.parseInt(txtNumDoc.getText());
                    int intCo_Dep = Integer.parseInt(txtCodDep.getText());
                    int intCo_Mot = Integer.parseInt(txtCodMot.getText());
                    String strHorSolDes = txtHorSolDes.getText();
                    String strHorSolHas = txtHorSolHas.getText();

                    int FechaSol[] = txtFecSol.getFecha(txtFecSol.getText());
                    String strFechaSol = "#" + FechaSol[2] + "/" + FechaSol[1] + "/" + FechaSol[0] + "#";

                    /*INSERT EN LA CABECERA*/
                    strSql = "INSERT INTO tbm_cabsolhorsupext(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_dep, co_mot, fe_sol, ho_soldes , ho_solhas, st_imp, st_aut, ho_authas, "
                            + "tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod) "
                            + "VALUES (" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + intCodTipDoc + ", " + intCodDoc
                            + ", '" + strFecha + "'," + intNum_Doc + "," + intCo_Dep + "," + intCo_Mot + ",'" + strFechaSol + "','" + strHorSolDes + "','" + strHorSolHas + "','" + strSt_Imp + "','" + strSt_Aut + "',null, "
                            + objUti.codificar(txtObs1.getText()) + "," + objUti.codificar(txtObs2.getText()) + ",'" + strSt_Reg + "',current_timestamp,null, " + objZafParSis.getCodigoUsuario() + ",null"
                            + ")";
                    stmLoc.executeUpdate(strSql);
                    /*FIN DE INSERT EN LA CABECERA*/

                    /*INSERT EN EL DETALLE*/
                    //int intCom=0;
                    for (int i = 0; i < tblDat.getRowCount(); i++) {

                        if (tblDat.getValueAt(i, INT_TBL_LINEA).toString().compareTo("M") == 0) {

                            strSql = "select 1 as flag from tbm_detsolhorsupext where co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc =" + objZafParSis.getCodigoLocal() + " "
                                    + "and co_tipdoc= " + txtCodTipDoc.getText() + " and co_doc=" + intCodDoc + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_COREG).toString() + " and co_tra=" + tblDat.getValueAt(i, INT_TBL_CODTRA).toString();
                            rstLoc = stmLoc.executeQuery(strSql);
                            int intFlag = 0;
                            if (rstLoc.next()) {
                                intFlag = rstLoc.getInt("flag");//1 si ya existe
                            }

                            if (intFlag == 0) {
                                if (tblDat.getValueAt(i, INT_TBL_CHKTRA).equals(true)) {
                                    strSql = "INSERT INTO tbm_detsolhorsupext(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_tra, co_emppag) "
                                            + "VALUES (" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + "," + txtCodTipDoc.getText() + "," + intCodDoc + "," + tblDat.getValueAt(i, INT_TBL_COREG).toString() + ", "
                                            + tblDat.getValueAt(i, INT_TBL_CODTRA).toString() + " , " + tblDat.getValueAt(i, INT_TBL_CODEMPPAG).toString() + ")";
                                    stmLoc.executeUpdate(strSql);
                                }
                            } else {
                                if (tblDat.getValueAt(i, INT_TBL_CHKTRA).equals(false)) {
                                    strSql = "delete from tbm_detsolhorsupext where co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc =" + objZafParSis.getCodigoLocal() + " "
                                            + "and co_tipdoc= " + txtCodTipDoc.getText() + " and co_doc=" + intCodDoc + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_COREG).toString() + " and co_tra=" + tblDat.getValueAt(i, INT_TBL_CODTRA).toString();
                                    stmLoc.executeUpdate(strSql);
                                }
                            }
                        }
                    }

                    /*INSERT PARA LA AUTORIZACION*/
                    int intDiaSem = obtenerDiaSemana(txtFecSol.getText().toString());
                    String strTx_tiphoraut = determinarHoraSuplementariaExtraordinarias(intDiaSem);
                    strSql = "select * from tbm_usrAutHorSupExtDep a "
                            + "where co_dep in(" + txtCodDep.getText() + ") ";
                    //+"where co_dep in("+ txtCodDep.getText() +") and tx_tiphoraut like '"+strTx_tiphoraut+"'";
                    System.out.println("q2: " + strSql);

                    stmLocAut = con.createStatement();
                    rstLocAut = stmLocAut.executeQuery(strSql);

                    if (rstLocAut.next()) {
                        do {

                            String st_Aut = "P";
                            int str = rstLocAut.getInt("co_reg");
                            strSql = "INSERT INTO tbm_autSolHorSupExt(co_emp, co_loc, co_tipdoc, co_doc, co_reg, st_aut) "
                                    + "VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + "," + txtCodTipDoc.getText() + "," + intCodDoc + "," + str + ", '"
                                    + st_Aut + "')";
                            stmLoc.executeUpdate(strSql);
                        } while (rstLocAut.next());
                    } else {
                        mostrarMsgInf("NO EXISTEN USUARIOS ASOCIADOS PARA AUTORIZAR HORAS SUPLEMENTARIAS/EXTRAORDINARIAS");
                        return false;
                    }

                    /*ACTUALIZAR EL NUMERO DE DOCUMENTO*/
                    strSql = "update tbm_cabtipdoc set ne_ultdoc=" + Integer.parseInt(txtNumDoc.getText().toString()) + " "
                            + "where co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + objZafParSis.getCodigoLocal() + " and co_tipdoc=" + txtCodTipDoc.getText();
                    stmLoc.executeUpdate(strSql);

                    blnRes = true;
                }
            } catch (java.sql.SQLException Evt) {
                Evt.printStackTrace();
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                Evt.printStackTrace();
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } finally {
                try {
                    stmLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                    stmLocAut.close();
                } catch (Throwable ignore) {
                }
                try {
                    rstLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                    rstLocAut.close();
                } catch (Throwable ignore) {
                }
            }
            return blnRes;
        }

        public int obtenerDiaSemana(String strHorSol) {

            int intDiaSemAux = 0;

            strHorSol = strHorSol.replace("/", "");
            int intDD = Integer.valueOf(strHorSol.substring(0, 2));
            int intMM = Integer.valueOf(strHorSol.substring(2, 4));
            int intYYYY = Integer.valueOf(strHorSol.substring(4, 8));

            Calendar cal = Calendar.getInstance();

            cal.set(Calendar.YEAR, intYYYY);
            cal.set(Calendar.DATE, intDD);
            cal.set(Calendar.MONTH, intMM + 1);

            intDiaSemAux = cal.get(Calendar.DAY_OF_WEEK) + 1;
            if (intDiaSemAux == 8) {
                intDiaSemAux = 1;
            }

            return intDiaSemAux;
        }

        public String determinarHoraSuplementariaExtraordinarias(int intDiaSemAux) {
            String strHorSupExtAux = "";
            if (intDiaSemAux >= 1 && intDiaSemAux <= 5) {
                strHorSupExtAux = "S";
            } else {
                strHorSupExtAux = "E";
            }
            return strHorSupExtAux;

        }

        private boolean validarDat() {
            boolean blnRes = true;
            if (txtCodTipDoc.getText().trim().equals("")) {
                MensajeInf("Ingrese tipo el documento");
                return false;
            }

            return blnRes;
        }

        public boolean modificar() {
            boolean blnRes = false;
            java.sql.Connection conn = null;
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;
            String strSQL = "";

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

                if (validaCampos()) {
                    conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                    if (conn != null) {
                        conn.setAutoCommit(false);
                        stmLoc = conn.createStatement();

                        if (objTblMod.getRowCountChecked(INT_TBL_CHKTRA) >= 1) {

                            /*VALIDAR QUE LOS EMPLEADOS ESCOGIDOS NO SE ENCUENTREN EN OTRA SOLICITUD*/
                            int FechaSol[] = txtFecSol.getFecha(txtFecSol.getText());
                            String strFechaSol = "#" + FechaSol[2] + "/" + FechaSol[1] + "/" + FechaSol[0] + "#";

                            strSQL = "select co_tra from tbm_detSolHorSupExt a "
                                    + "where co_doc in (select distinct co_doc from tbm_cabSolHorSupExt where fe_sol='" + strFechaSol + "')";
                            rstLoc = stmLoc.executeQuery(strSQL);

                            boolean blnExtTraSel = false;
                            while (rstLoc.next()) {
                                String strCoTraSel = rstLoc.getString("co_tra");
                                for (int i = 0; i < tblDat.getRowCount(); i++) {
                                    if (tblDat.getValueAt(i, INT_TBL_LINEA).toString().compareTo("M") == 0) {

                                        if (tblDat.getValueAt(i, INT_TBL_CODTRA).toString().equals(strCoTraSel)) {
                                            blnExtTraSel = true;
                                            i = tblDat.getRowCount();
                                        }

                                    }
                                }
                            }

                            if (!blnExtTraSel) {
                                if (modificarCab(conn, Integer.parseInt(txtCodDoc.getText()))) {
                                    if (modificarDet(conn, Integer.parseInt(txtCodDoc.getText()))) {
                                        conn.commit();
                                        blnRes = true;
                                    } else {
                                        conn.rollback();
                                    }
                                } else {
                                    conn.rollback();
                                }
                            } else {
                                conn.rollback();
                                mostrarMsgInf("HAY EMPLEADOS YA SELECCIONADOS PARA OTRA SOLICITUD");
                                return false;
                            }

                        } else {
                            con.rollback();
                            mostrarMsgInf("NO HA SELECCIONADO EMPLEADOS QUE APLIQUEN A LA SOLICITUD");
                            return false;
                        }
                    }
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } finally {
                try {
                    rstLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                    stmLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                    conn.close();
                } catch (Throwable ignore) {
                }
            }

            return blnRes;
        }

        public boolean modificarCab(java.sql.Connection conn, int intCodDoc) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            String strSQL = "";
            String strFechaDoc = "";
            String strFechaSol = "";
            String strFecSis = "";
            try {
                if (conn != null) {

                    stmLoc = conn.createStatement();

                    int FechaDoc[] = txtFecDoc.getFecha(txtFecDoc.getText());
                    strFechaDoc = "#" + FechaDoc[2] + "/" + FechaDoc[1] + "/" + FechaDoc[0] + "#";

                    int FechaSol[] = txtFecSol.getFecha(txtFecSol.getText());
                    strFechaSol = "#" + FechaSol[2] + "/" + FechaSol[1] + "/" + FechaSol[0] + "#";

                    strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());

                    strSQL = "UPDATE tbm_cabSolHorSupExt SET  fe_doc='" + strFechaDoc + "', co_dep=" + txtCodDep.getText() + ", co_mot=" + txtCodMot.getText()
                            + ", fe_sol='" + strFechaSol + "' ,ho_sol='" + txtHorSolHas.getText() + "' , tx_obs1=" + objUti.codificar(txtObs1.getText()) + ", tx_obs2=" + objUti.codificar(txtObs2.getText())
                            + ", fe_ultmod='" + strFecSis + "', co_usrmod=" + objZafParSis.getCodigoUsuario() + " "
                            + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal()
                            + " AND co_tipdoc=" + txtCodTipDoc.getText() + "  AND co_doc=" + intCodDoc;
                    stmLoc.executeUpdate(strSQL);

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

        public boolean modificarDet(java.sql.Connection conn, int intCodDoc) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSQL = "";

            try {
                if (conn != null) {

                    stmLoc = conn.createStatement();

                    int intCom = 0;
                    for (int i = 0; i < tblDat.getRowCount(); i++) {

                        if (tblDat.getValueAt(i, INT_TBL_LINEA).toString().compareTo("M") == 0) {

                            intCom++;

                            strSQL = "select 1 as flag from tbm_detsolhorsupext where co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc =" + objZafParSis.getCodigoLocal() + " "
                                    + "and co_tipdoc= " + txtCodTipDoc.getText() + " and co_doc=" + intCodDoc + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_COREG).toString() + " and co_tra=" + tblDat.getValueAt(i, INT_TBL_CODTRA).toString();
                            rstLoc = stmLoc.executeQuery(strSQL);
                            int intFlag = 0;
                            if (rstLoc.next()) {
                                intFlag = rstLoc.getInt("flag");//1 si ya existe
                            }

                            if (intFlag == 0) {
                                strSQL = "INSERT INTO tbm_detsolhorsupext(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_tra) "
                                        + "VALUES (" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + "," + txtCodTipDoc.getText() + "," + intCodDoc + "," + tblDat.getValueAt(i, INT_TBL_COREG).toString() + ", "
                                        + tblDat.getValueAt(i, INT_TBL_CODTRA).toString() + ")";
                                stmLoc.executeUpdate(strSQL);

                                String st_Aut = "P";
                                strSQL = "INSERT INTO tbm_autSolHorSupExt(co_emp, co_loc, co_tipdoc, co_doc, co_reg, st_aut) "
                                        + "VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + "," + txtCodTipDoc.getText() + "," + intCodDoc + "," + tblDat.getValueAt(i, INT_TBL_COREG).toString() + ", '"
                                        + st_Aut + "')";
                                stmLoc.executeUpdate(strSQL);

                            } else {
                                if (tblDat.getValueAt(i, INT_TBL_CHKTRA).equals(false)) {
                                    strSQL = "delete from tbm_detsolhorsupext where co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc =" + objZafParSis.getCodigoLocal() + " "
                                            + "and co_tipdoc= " + txtCodTipDoc.getText() + " and co_doc=" + intCodDoc + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_COREG).toString() + " and co_tra=" + tblDat.getValueAt(i, INT_TBL_CODTRA).toString();
                                    stmLoc.executeUpdate(strSQL);

                                    strSQL = "delete from tbm_autSolHorSupExt where co_emp=" + objZafParSis.getCodigoEmpresa() + "and co_loc =" + objZafParSis.getCodigoLocal()
                                            + "and co_tipdoc= " + txtCodTipDoc.getText() + " and co_doc=" + intCodDoc + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_COREG).toString();
                                    stmLoc.executeUpdate(strSQL);
                                }
                            }
                        }
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

        private void noEditable(boolean blnEst) {
            txtObs1.setEditable(false);
            txtObs2.setEditable(false);
        }

        public void clnTextos() {
            strCodTipDoc = "";
            strDesCorTipDoc = "";
            strDesLarTipDoc = "";

            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtFecDoc.setText("");

            txtCodDep.setText("");
            txtDesDep.setText("");
            txtNumDoc.setText("");

            txtCodMot.setText("");
            txtDesMot.setText("");
            txtCodDoc.setText("");

            txtObs1.setText("");
            txtObs2.setText("");

            txtCodTipDocSol.setText("");
            txtCodDoc.setText("");

            txtFecSol.setText("");
            txtHorSolHas.setText("");

            cboHHHas.setSelectedIndex(0);
            cboMMHas.setSelectedIndex(0);

            cboHHDes.setSelectedIndex(18);
            cboMMDes.setSelectedIndex(0);

            objTblMod.removeAllRows();
        }

        public boolean cancelar() {
            boolean blnRes = true;

            try {
                if (blnHayCam || objTblMod.isDataModelChanged()) {
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

            return true;
        }

        public boolean afterModificar() {

            this.setEstado('w');

            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }

        /**
         * Esta función muestra un mensaje "showConfirmDialog". Presenta las
         * opciones Si, No y Cancelar. El usuario es quien determina lo que debe
         * hacer el sistema seleccionando una de las opciones que se presentan.
         */
        private int mostrarMsgCon(String strMsg) {
            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
            String strTit;
            strTit = "Mensaje del sistema Zafiro";
            return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
        }

        /**
         * Esta función se encarga de agregar el listener "DocumentListener" a
         * los objTooBars de tipo texto para poder determinar si su contenido a
         * cambiado o no.
         */
        private boolean isRegPro() {
            boolean blnRes = true;
            String strAux = "¿Desea guardar los cambios efectuados a éste registro?\n";
            strAux += "Si no guarda los cambios perderá toda la información que no haya guardado.";
            switch (mostrarMsgCon(strAux)) {
                case 0: //YES_OPTION
                    switch (objTooBar.getEstado()) {
                        case 'n': //Insertar
                            blnRes = objTooBar.insertar();
                            break;
                        case 'm': //Modificar
//                        blnRes=objTooBar.modificar();
                            break;
                    }
                    break;
                case 1: //NO_OPTION
                    objTblMod.setDataModelChanged(false);
                    blnHayCam = false;
                    blnRes = true;
                    break;
                case 2: //CANCEL_OPTION
                    blnRes = false;
                    break;
            }
            return blnRes;
        }

        public boolean consultar() {
            return _consultar(FilSql()); //Solo Modo Consulta
        }

        private boolean _consultar(String strFil) {
            boolean blnRes = false;
            String strSql = "";
            try {
                if (!validarDat()) {
                    return false;
                }

                abrirCon();
                if (CONN_GLO != null) {
                    STM_GLO = CONN_GLO.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY);
                    String strCoUsrIng = "";
                    if (objZafParSis.getCodigoUsuario() != 1) {
                        strCoUsrIng = " and a.co_usring= " + objZafParSis.getCodigoUsuario();
                    }
                    strSql = "SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc "
                            + " FROM tbm_cabSolHorSupExt AS a  "
                            + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " and a.co_loc=" + objZafParSis.getCodigoLocal() + strCoUsrIng
                            + " AND a.st_reg NOT IN('E') " + strFil + " ORDER BY a.ne_numdoc ";

                    rstCab = STM_GLO.executeQuery(strSql);
                    if (rstCab.next()) {
                        rstCab.last();
                        setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                        refrescaDatos(CONN_GLO, rstCab);
                        blnRes = true;
                    } else {
                        mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                        setEstado('l');
                        setMenSis("Listo");
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

        private boolean refrescaDatos(java.sql.Connection conn, java.sql.ResultSet rstDatSolCon) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstDatSol;
            String strSql = "", strStReg = "";

            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "";
                    strSql += "select * from (select c.*, (d.tx_ape || ' ' || d.tx_nom) AS nomCom, f.tx_deslar as deslardep, g.tx_deslar as deslarmot,c.st_reg,h.tx_descor as txdescortipdoc from ( ";
                    strSql += " select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a.ne_numdoc, a.co_dep, a.co_mot, a.fe_sol, a.ho_soldes , a.ho_solhas, ";
                    strSql += " a.tx_obs1, a.tx_obs2, a.st_reg,b.co_reg, b.co_tra  from tbm_cabSolHorSupExt a inner join tbm_detSolHorSupExt b on ";
                    strSql += " (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc) )c ";
                    strSql += " inner join tbm_tra d on (c.co_tra=d.co_tra)  ";
                    strSql += " inner join tbm_dep f on (f.co_dep=c.co_dep) ";
                    strSql += " inner join tbm_motHorSupExt g on (g.co_mot=c.co_mot) ";
                    strSql += " inner join tbm_cabtipdoc h on (h.co_emp=c.co_emp AND h.co_loc=c.co_loc  AND  h.co_tipdoc=c.co_tipdoc) ";
                    strSql += " ) e ";
                    strSql += " left outer join tbm_cabconasitra i on (i.fe_dia=e.fe_sol and i.co_tra=e.co_tra)";
                    strSql += " left outer join tbm_calciu j on (j.fe_dia=e.fe_sol and j.co_ciu=1)";
                    strSql += " WHERE e.co_emp=" + rstDatSolCon.getString("co_emp") + " AND e.co_loc=" + rstDatSolCon.getString("co_loc") + "  AND ";
                    strSql += " e.co_tipdoc=" + rstDatSolCon.getString("co_tipdoc") + " AND e.co_doc=" + rstDatSolCon.getString("co_doc");
                    strSql += " order by nomcom";
                    System.out.println("pilas:" + strSql);
                    rstDatSol = stmLoc.executeQuery(strSql);
                    boolean blnPriIng = false;
                    int intValEntSal = 0;//0 no se valida, 1 se valida la entrada, 2 se valida la salida, 3 feriados (entrada y salida)

                    while (rstDatSol.next()) {

                        if (!blnPriIng) {

                            vecDat = new Vector();
                            strStReg = rstDatSol.getString("st_reg");
                            txtDesCorTipDoc.setText(rstDatSol.getString("txdescortipdoc"));
                            txtCodDep.setText(rstDatSol.getString("co_dep"));
                            insertarDetalle();
                            txtDesDep.setText(rstDatSol.getString("deslardep"));
                            txtCodMot.setText(rstDatSol.getString("co_mot"));
                            txtDesMot.setText(rstDatSol.getString("deslarmot"));
                            txtNumDoc.setText(rstDatSol.getString("ne_numdoc"));
                            txtCodDoc.setText(rstDatSol.getString("co_doc"));
                            txtHorSolDes.setText(rstDatSol.getString("ho_soldes").substring(0, 5));
                            txtHorSolHas.setText(rstDatSol.getString("ho_solhas").substring(0, 5));

                            cboHHDes.setSelectedItem(rstDatSol.getString("ho_soldes").substring(0, 2));
                            cboMMDes.setSelectedItem(rstDatSol.getString("ho_soldes").substring(3, 5));

                            cboHHHas.setSelectedItem(rstDatSol.getString("ho_solhas").substring(0, 2));
                            cboMMHas.setSelectedItem(rstDatSol.getString("ho_solhas").substring(3, 5));

                            txtObs1.setText(rstDatSol.getString("tx_obs1"));
                            txtObs2.setText(rstDatSol.getString("tx_obs2"));

                            java.util.Date dateObj = rstDatSol.getDate("fe_doc");
                            if (dateObj == null) {
                                txtFecDoc.setText("");
                            } else {
                                java.util.Calendar calObj = java.util.Calendar.getInstance();
                                calObj.setTime(dateObj);
                                txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                        calObj.get(java.util.Calendar.MONTH) + 1,
                                        calObj.get(java.util.Calendar.YEAR));
                            }

                            dateObj = rstDatSol.getDate("fe_sol");
                            if (dateObj == null) {
                                txtFecSol.setText("");
                            } else {
                                java.util.Calendar calObj = java.util.Calendar.getInstance();
                                calObj.setTime(dateObj);
                                txtFecSol.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                        calObj.get(java.util.Calendar.MONTH) + 1,
                                        calObj.get(java.util.Calendar.YEAR));
                            }

                            String strHoSolDes = "";
                            if (rstDatSol.getString("ho_soldes") != null) {
                                strHoSolDes = rstDatSol.getString("ho_soldes");
                            }

                            String strTipDia = rstDatSol.getString("tx_tipdia");
                            String strArrHoSolDes[];

                            if (strTipDia != null) {
                                if (strTipDia.compareTo("F") == 0) {
                                    intValEntSal = 3;
                                } else {
                                    if (strHoSolDes != null) {
                                        strArrHoSolDes = strHoSolDes.split(":");
                                        int intHHSolDes = Integer.valueOf(strArrHoSolDes[0]);
                                        if (intHHSolDes >= 12) {
                                            intValEntSal = 2;
                                        } else {
                                            intValEntSal = 1;
                                        }
                                    }
                                }
                            }

                            blnPriIng = true;
                        }

//       
//        private final int INT_TBL_LINEA=0;
//  private final int INT_TBL_CHKTRA=1;
//  private final int INT_TBL_CODEMPPAG=2;
//  private final int INT_TBL_CODTRA=3;
//  private final int INT_TBL_NOMTRA=4;
//  private final int INT_TBL_COREG=5;
//  private final int INT_TBL_HORENT=6;
//  private final int INT_TBL_HORSAL=7;
                        Vector vecReg = new Vector();

                        vecReg.add(INT_TBL_LINEA, null);
                        vecReg.add(INT_TBL_CHKTRA, true);
                        vecReg.add(INT_TBL_CODEMPPAG, rstDatSol.getString("co_emp"));
                        vecReg.add(INT_TBL_CODTRA, rstDatSol.getString("co_tra"));
                        vecReg.add(INT_TBL_NOMTRA, rstDatSol.getString("nomCom"));
                        vecReg.add(INT_TBL_COREG, rstDatSol.getString("co_reg"));

//           vecReg.add(INT_TBL_HORENT, null);
//           vecReg.add(INT_TBL_HORSAL, null);
                        if (objZafParSis.getCodigoMenu() == 2862 && objZafParSis.getCodigoMenu() == 3485) {

                            if (intValEntSal == 1) {

                                if (rstDatSol.getString("ho_ent") == null) {
                                    vecReg.add(INT_TBL_HORENT, null);
                                } else {
                                    String strHoEnt = rstDatSol.getString("ho_ent").substring(0, 5);
                                    String str[] = strHoEnt.split(":");
                                    if (str[0].compareTo("00") == 0) {
                                        strHoEnt = "12:" + str[1];
                                    }
                                    vecReg.add(INT_TBL_HORENT, strHoEnt);
                                }
                                vecReg.add(INT_TBL_HORSAL, null);
                            } else if (intValEntSal == 2) {

                                vecReg.add(INT_TBL_HORENT, null);

                                if (rstDatSol.getString("ho_sal") == null) {
                                    vecReg.add(INT_TBL_HORSAL, null);
                                } else {
                                    String strHoSal = rstDatSol.getString("ho_sal").substring(0, 5);
                                    String str[] = strHoSal.split(":");
                                    if (str[0].compareTo("00") == 0) {
                                        strHoSal = "12:" + str[1];
                                    }
                                    vecReg.add(INT_TBL_HORSAL, strHoSal);
                                }
                            } else if (intValEntSal == 3) {

                                if (rstDatSol.getString("ho_ent") == null) {
                                    vecReg.add(INT_TBL_HORENT, null);
                                } else {
                                    String strHoEnt = rstDatSol.getString("ho_ent").substring(0, 5);
                                    String str[] = strHoEnt.split(":");
                                    if (str[0].compareTo("00") == 0) {
                                        strHoEnt = "12:" + str[1];
                                    }
                                    vecReg.add(INT_TBL_HORENT, strHoEnt);
                                }
                                if (rstDatSol.getString("ho_sal") == null) {
                                    vecReg.add(INT_TBL_HORSAL, null);
                                } else {
                                    String strHoSal = rstDatSol.getString("ho_sal").substring(0, 5);
                                    String str[] = strHoSal.split(":");
                                    if (str[0].compareTo("00") == 0) {
                                        strHoSal = "12:" + str[1];
                                    }
                                    vecReg.add(INT_TBL_HORSAL, strHoSal);
                                }
                            }
                        } else {
                            if (objZafParSis.getCodigoMenu() == 3054) {
//                    if(Integer.valueOf(tblDat.getValueAt(i, INT_TBL_CODTRA).toString())==rstDatSol.getInt("co_tra")){
                                vecReg.add(INT_TBL_HORENT, null);
                                vecReg.add(INT_TBL_HORSAL, null);
//                    }        
                            }
                        }
                        vecDat.add(vecReg);
                    }

                    //Asignar vectores al modelo.
                    objTblMod.setData(vecDat);
                    tblDat.setModel(objTblMod);
                    vecDat.clear();
                    vecDat = null;

                    strSql = "";
                    strSql += "select ho_autdes,ho_authas from tbm_autsolhorsupext";
                    strSql += " where co_emp=" + rstDatSolCon.getString("co_emp");
                    strSql += " and co_loc=" + rstDatSolCon.getString("co_loc");
                    strSql += " and co_tipdoc=" + rstDatSolCon.getString("co_tipdoc");
                    strSql += " and co_doc=" + rstDatSolCon.getString("co_doc");
                    strSql += "and co_reg in (select max(co_reg) from tbm_autsolhorsupext";
                    strSql += " where co_emp=" + rstDatSolCon.getString("co_emp");
                    strSql += " and co_loc=" + rstDatSolCon.getString("co_loc");
                    strSql += " and co_tipdoc=" + rstDatSolCon.getString("co_tipdoc");
                    strSql += " and co_doc=" + rstDatSolCon.getString("co_doc") + ")";
                    System.out.println("autorizad: " + strSql);
                    rstDatSol = stmLoc.executeQuery(strSql);
                    String strHoAutDes = "";
                    String strHoAutHas = "";
                    if (rstDatSol.next()) {
                        strHoAutDes = rstDatSol.getString("ho_autdes");
                        strHoAutHas = rstDatSol.getString("ho_authas");
                        if (strHoAutDes == null) {
                            cboHHAutDes.setSelectedIndex(0);
                            cboMMAutDes.setSelectedIndex(0);
                        } else {
                            String strArrHoAutDes[] = strHoAutDes.split(":");
                            cboHHAutDes.setSelectedItem(strArrHoAutDes[0]);
                            cboMMAutDes.setSelectedItem(strArrHoAutDes[1]);
                        }
                        if (strHoAutHas == null) {
                            cboHHAutHas.setSelectedIndex(0);
                            cboMMAutHas.setSelectedIndex(0);
                        } else {
                            String strArrHoAutHas[] = strHoAutHas.split(":");
                            cboHHAutHas.setSelectedItem(strArrHoAutHas[0]);
                            cboMMAutHas.setSelectedItem(strArrHoAutHas[1]);
                        }
                    }

                    rstDatSol.close();
                    rstDatSol = null;

                    int intPosRel = rstDatSolCon.getRow();
                    rstDatSolCon.last();
                    objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstDatSolCon.getRow());
                    mostrarEstado(strStReg);
                    rstDatSolCon.absolute(intPosRel);
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

        private void mostrarEstado(String strStReg) {
            setEstado('w');//l-c-x-y-z-n-m-e-a-w
            if (strStReg.equals("I")) {
                setEstadoRegistro("Anulado");
                setEnabledModificar(false);
                setEnabledEliminar(false);
                setEnabledAnular(false);
            } else {
                if (strStReg.equals("E")) {
                    setEstadoRegistro("Eliminado");
                    setEnabledModificar(false);
                    setEnabledEliminar(false);
                    setEnabledAnular(false);
                } else {
                    setEstadoRegistro("");
                }
            }
        }

        private String FilSql() {
            String sqlFiltro = "";
//Agregando filtro por Numero de Cotizacion
            if (!txtCodTipDoc.getText().equals("")) {
                sqlFiltro = sqlFiltro + " and a.co_tipdoc=" + txtCodTipDoc.getText();
            }

            if (!txtCodDoc.getText().equals("")) {
                sqlFiltro = sqlFiltro + " and a.co_doc=" + txtCodDoc.getText();
            }

            if (!txtNumDoc.getText().equals("")) {
                sqlFiltro = sqlFiltro + " and a.ne_numdoc=" + txtNumDoc.getText();
            }

            //Agregando filtro por Fecha
            if (txtFecDoc.isFecha()) {
                int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
                String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" + FecSql[0] + "#";
                sqlFiltro = sqlFiltro + " and a.fe_doc = '" + strFecSql + "'";
            }

            return sqlFiltro;
        }

        public void clickModificar() {
            setEditable(true);

            java.awt.Color colBack;
            colBack = txtCodDoc.getBackground();

            this.setEnabledConsultar(false);

            objTblMod.setDataModelChanged(false);
            blnHayCam = false;
        }

        public boolean vistaPreliminar() {
            return true;
        }

        public boolean imprimir() {
            return true;
        }

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
                case INT_TBL_CHKTRA:
                    strMsg = "";
                    break;
                case INT_TBL_CODEMPPAG:
                    strMsg = "Código de la empresa que paga el sobretiempo";
                    break;
                case INT_TBL_CODTRA:
                    strMsg = "Código del empleado";
                    break;
                case INT_TBL_NOMTRA:
                    strMsg = "Nombres y apellidos del empleado";
                    break;
                case INT_TBL_HORENT:
                    strMsg = "Hora de entrada";
                    break;
                case INT_TBL_HORSAL:
                    strMsg = "Hora de salida";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /*
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
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

        /*
         * Esta función establece el indice de la función a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta función sirve para determinar
         * la función que debe ejecutar el Thread.
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice) {
            intIndFun = indice;
        }
    }

    /*
     * Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt) {
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes = true;
        try {
            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada() == objRptSis.INT_OPC_ACE) {

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
                                mapPar.put("codEmp", new Integer(objZafParSis.getCodigoEmpresa()));
                                mapPar.put("codLoc", new Integer(objZafParSis.getCodigoLocal()));
                                mapPar.put("codTipDoc", new Integer(Integer.parseInt(txtCodTipDoc.getText())));
                                mapPar.put("codDoc", new Integer(Integer.parseInt(txtCodDoc.getText())));
                                mapPar.put("SUBREPORT_DIR", strRutRpt);

//                                mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
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

    public static java.sql.Time SparseToTime(String hora) throws Exception {
        int h, m, s = 0;
        h = Integer.parseInt(hora.charAt(0) + "" + hora.charAt(1));
        m = Integer.parseInt(hora.charAt(3) + "" + hora.charAt(4));
        //s = Integer.parseInt(hora.charAt(6)+""+hora.charAt(7)) ;
        return (new java.sql.Time(h, m, s));
    }

    protected void finalize() throws Throwable {
        System.gc();
        super.finalize();
    }

    /**
     * Verifica si una hora tiene cruze o se encuentra en el rango de otra hora.
     *
     * @param strHorDes hora desde Registro de la base.
     * @param strHorHas hora hasta Registro de la base.
     * @param intHorDes hora desde pantalla.
     * @param intMinDes minuto desde pantalla.
     * @param intHorHas hora hasta pantalla.
     * @param intMinHas minuto hasta pantalla.
     * @return boolean indica si la hora tiene un cruce con otra
     */
    public boolean validarHoraNoAdmiteCruze(String strHorDes, String strHorHas, int intHorDes, int intMinDes, int intHorHas, int intMinHas) {
        Boolean booRet = false;
        //boolean booIgualDesde=false;
        //boolean booIgualHasta=false;
        boolean booCru = false;
        String[] strArrDes, strArrHas;
        strArrDes = strHorDes.split(":");
        strArrHas = strHorHas.split(":");
        int intHorDesDb = Integer.parseInt(strArrDes[0]);
        int intHorHasDb = Integer.parseInt(strArrHas[0]);

        System.out.println("Fecha solicitada " + txtFecSol.getText());

        if (intHorHasDb == 0) {
            intHorHasDb = 24;
        }
        if (intHorHas == 0) {
            intHorHas = 24;
        }
        List lstHorRan = new ArrayList();
        List lstMinRan = new ArrayList();

        if (intHorDesDb == intHorHasDb) {//Caso en el que las hora desde y hora hasta es la misma es decir HD=17(8):15 - HH=17(8):30
            if (intHorDes == intHorDesDb) {
                if (intHorHas == intHorHasDb) {
                    if (intMinDes > Integer.parseInt(strArrDes[1])) {
                        if (intMinDes < Integer.parseInt(strArrHas[1])) {
                            booRet = true;
                        }
                    } else if (intMinDes < Integer.parseInt(strArrDes[1])) {
                        if (intMinHas > Integer.parseInt(strArrDes[1])) {
                            booRet = true;
                        }
                    } else if (intMinDes == Integer.parseInt(strArrDes[1])) {
                        booRet = true;
                    }
                } else if (intHorHas > intHorHasDb) {
                    if (intMinDes < Integer.parseInt(strArrHas[1])) {
                        booRet = true;
                    }
                }
            } else if (intHorDes < intHorDesDb) {
                if (intHorHas > intHorHasDb) {
                    booRet = true;
                } else if (intHorHas == intHorHasDb) {
                    if (intMinHas > Integer.parseInt(strArrDes[1])) {
                        booRet = true;
                    }
                }
            }
        } else {
            for (int i = intHorDesDb; i <= intHorHasDb; i++) {
                lstHorRan.add(i);
            }
            if (intHorHas == 0) {
                intHorHas = 24;
            }
            for (int j = intHorDes; j <= intHorHas; j++) {

                if (intHorDes == intHorHas) {
                    if (intHorDes == intHorDesDb) {
                        if (intMinDes < Integer.parseInt(strArrDes[1])) {
                            if (intMinHas > Integer.parseInt(strArrDes[1])) {
                                booRet = true;
                                break;
                            }
                        } else if (intMinDes > Integer.parseInt(strArrDes[1])) {
                            if (intHorHasDb > intHorHas) {
                                booRet = true;
                                break;
                            } else if (intHorHas == intHorHasDb) {
                                if (intMinHas <= Integer.parseInt(strArrHas[1])) {
                                    booRet = true;
                                    break;
                                }
                            }
                            /*if(intMinDes < Integer.parseInt(strArrHas[1])){
                             booRet=true;
                             break;
                             }*/
                        }
                    }/*else if(intHorDes<intHorDesDb){
                     if(intHorHas >intHorHasDb ){
                     booRet=true;
                     break;
                     }else if(intHorHas == intHorHasDb){
                     if(intMinHas>Integer.parseInt(strArrDes[1])){
                     booRet=true;
                     break;
                     }
                     }                
                     }*/ else if (intHorDes > intHorDesDb) {
                        if (intHorHas == intHorHasDb) {
                            if (intMinHas <= Integer.parseInt(strArrHas[1])) {
                                booRet = true;
                                break;
                            }
                        } else if (intHorHas < intHorHasDb) {
                            booRet = true;
                            break;
                        }
                    }
                } else if (lstHorRan.contains(j)) {
                    int indice = lstHorRan.indexOf(j);
                    if (j != intHorDesDb && j != intHorHasDb) {/*Entra cuando la hora esta dentro del rango y no en los limites del rango HD o  HH*/

                        booRet = true;
                        break;
                    } else {
                        if (j == intHorDes) {//Coincidencia encontrada en Hora Desde
                            //if(intMinDes>Integer.parseInt(strArrDes[1])){

                            if (indice == 0) {//Hora Desde usuario es igual a Hora Desde base 
                                if (intMinDes > Integer.parseInt(strArrDes[1])) {
                                    booRet = true;
                                    break;
                                } else if (intMinDes == Integer.parseInt(strArrDes[1])) {
                                    booCru = true;
                                } else if (intMinDes < Integer.parseInt(strArrDes[1])) {
                                    booCru = true;
                                }
                            } else if (indice > 0) {// Hora desde usuario es igual a Hora Hasta base
                                if (intMinDes < Integer.parseInt(strArrHas[1])) {
                                    booRet = true;
                                    break;
                                }
                            }

                            //}
                        } else if (j == intHorHas) {//Coincidencia encontrada en Hora Hasta
                            if (indice == 0) {
                                if (intMinHas > Integer.parseInt(strArrDes[1])) {
                                    booRet = true;
                                    break;
                                }
                            } else if (indice > 0) {
                                if (intMinHas < Integer.parseInt(strArrHas[1])) {
                                    booRet = true;
                                    break;
                                } else if (intMinHas == Integer.parseInt(strArrHas[1])) {
                                    if (booCru) {
                                        booRet = true;
                                        break;
                                    }
                                } else if (intMinHas > Integer.parseInt(strArrHas[1])) {
                                    if (booCru) {
                                        booRet = true;
                                        break;
                                    }
                                }
                            }

                        } else {//En el caso en que no sean hora desde ni hora hasta de la pantalla pero se cruze con un rango 
                            booRet = true;
                            break;
                        }
                    }
                }
            }
        }
        return booRet;
    }

    public boolean esHoraValida(int intHorDes, int intMinDes, int intHorHas, int intMinHas, int dia) {
        boolean booRetorno = true;

        if (dia != 7 && dia != 1) {// lunes a viernes
            if (intHorDes >= 4 && intHorDes <= 10) {
                if (intHorHas <= 10) {//Se agrega validacion para que pueda crear una solicitud hasta las 10am
                    if (intHorDes > intHorHas) {
                        booRetorno = false;
                    } else if (intHorHas < 10) {
                        if (intHorDes == intHorHas) {
                            if (intMinHas <= intMinDes) {
                                booRetorno = false;
                            }
                        }
                    } else if (intHorHas == 10) {
                        if (intMinHas > 0) {
                            booRetorno = false;
                        }
                    }
                } else {
                    booRetorno = false;
                }
            } else if (intHorDes >= 17 && intHorDes <= 23) {
                //if (intHorDes == 17) {
                //if (intMinDes < 0) {
                //  booRetorno = false;
                //}
                //}
                if (intHorHas <= 23 && intHorHas != 0) {
                    if (intHorDes > intHorHas) {
                        booRetorno = false;
                    } else if (intHorDes == intHorHas) {
                        if (intMinDes >= intMinHas) {
                            booRetorno = false;
                        }
                    }
                } else if (intHorHas == 0) {
                    if (intMinHas != 0) {
                        booRetorno = false;
                    }
                } else {
                    booRetorno = false;
                }

            } else {
                booRetorno = false;
            }
        } else if (dia == 7) {//Dias sabados 
            //Se agregó validación para que se pueda laborar como horas extra hasta las 8pm. tony
            //Se agrega validación para trabajar desde las 2y 30 hasta las 8
            if (intHorDes >= 2 && intHorDes <= 8) {
                if (intHorDes == 2) {
                    if (intMinDes < 30) {
                        booRetorno = false;
                    }
                }
                if (intHorHas <= 8) {
                    if (intHorDes > intHorHas) {
                        booRetorno = false;
                    } else if (intHorHas < 8) {
                        if (intHorDes == intHorHas) {
                            if (intMinHas <= intMinDes) {
                                booRetorno = false;
                            }
                        }
                    } else if (intHorHas == 8) {
                        if (intHorDes == intHorHas) {
                            booRetorno = false;
                        }
                        if (intMinHas > 30) { //tony validacion para que se pueda asignar hasta as 8y30
                            booRetorno = false;
                        }
                    }
                } else {
                    booRetorno = false;
                }
            } else if (intHorDes >= 12 && intHorDes <= 23) {
                if (intHorDes == 12) {
                    if (intMinDes < 30) {
                        booRetorno = false;
                    }
                }
                if (intHorHas <= 23 /*&& intHorHas!=0*/) {
                    if (intHorHas < 23) {
                        if (intHorDes > intHorHas) {
                            booRetorno = false;
                        } else if (intHorDes == intHorHas) {
                            if (intMinDes >= intMinHas) {
                                booRetorno = false;
                            }
                        }

                    } else if (intHorHas == 23) {
                        if (intHorDes == intHorHas) {
                            booRetorno = false;
                        }
                        if (intMinHas != 0) {
                            booRetorno = false;
                        }
                    }
                } else {
                    booRetorno = false;
                }

            } else {
                booRetorno = false;
            }
        } else if (dia == 1) {// Domingo
            //Se agregó validación para que los días domingos se puedan asignar para laborar como horas extras. tony
            if (intHorDes >= 8 && intHorDes <= 23) {
                if (intHorHas <= 23 && intHorHas >= 8/*&& intHorHas!=0*/) {
                    if (intHorHas < 23) {
                        if (intHorDes > intHorHas) {
                            booRetorno = false;
                        } else if (intHorDes == intHorHas) {
                            if (intMinDes >= intMinHas) {
                                booRetorno = false;
                            }
                        }

                    } else if (intHorHas == 23) {
                        if (intHorDes == intHorHas) {
                            booRetorno = false;
                        }
                        if (intMinHas != 0) {
                            booRetorno = false;
                        }
                    }
                } else {
                    booRetorno = false;
                }
            } else {
                booRetorno = false;
            }
        }
//        else{ //Domingo
//            booRetorno =false;
//        }
        return booRetorno;
    }

    /* public boolean verificarHorSupCor( int intHorDes,  int intHorHas, int intMinDes, int intMinHas){
     boolean booRetorno=true;
     List lstHorarioTrabajo=new ArrayList();
     for(int j=8; j<=17;j++){
     lstHorarioTrabajo.add(j);
     }
     for(int i=intHorDes;i<=intHorHas;i++){
     if(lstHorarioTrabajo.contains(i)){
     if(i== 8){
     if(intHorHas==i){
     if(intMinHas>0){
     booRetorno=false;
     break;
     }
     }else if(intHorDes==i){
     booRetorno=false;
     break;
     }
     }else if(i==17){
                    
     if(intHorHas==i){
     booRetorno=false;
     break;
     }else if(intHorDes==i){
     if(intMinDes<15){
     booRetorno=false;
     break;
     }
     }
     }
     else{
     booRetorno=false;
     break;
     }
     }
     }
     return booRetorno;
     }*/
    /**
     * Metodo verifica que la hora se encuentre en rangos de 5:00 am - 8:00 am y
     * 17:15 pm - 12:00 pm.
     *
     * @param intHorDes valor hora desde.
     * @param intMinDes valor minuto desde.
     * @param intHorHas valor hora hasta.
     * @param intMinHas valor minuto hasta.
     * @return
     */
    /*public boolean verificarRanValMaxHor(int intHorDes,int intMinDes, int intHorHas,int intMinHas){
     boolean booRetorno=true;
     if(intHorDes >= 17){ //Caso Hora Desde mayores igual a 17
     if(intHorDes==17){
     if(intMinDes>=15){
     if(intHorHas > 0 && intHorHas < 17){
     booRetorno=false;
     }else if(intHorHas==0){
     if(intMinHas!=0){
     booRetorno=false;
     }
     }
     }else{
     booRetorno=false; // hora desde 17 y minutos menor a 15
     }
     } else if((intHorHas>0 && intHorHas < 17)){// mayores que 17
     booRetorno=false;
     }else if(intHorHas==0){
     if(intMinHas!=0){
     booRetorno=false;
     }
     }
     }else if(intHorDes > 0){// Caso hora desde mayores a 0
     if(intHorDes>=5){
     if(intHorHas>=8 ){
     if(intMinHas!=0){
     booRetorno=false;
     }
     }
     }else{
     booRetorno=false;
     }
     }else{
     booRetorno=false;
     }
     return booRetorno;
     }*/
    public void cargarTipoDoc(int intVal) {
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null) {
                stmLoc = conn.createStatement();

                if (objZafParSis.getCodigoUsuario() == 1) {
                    strSql = "SELECT  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor, "
                            + " case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc "
                            + " ,doc.tx_natdoc, doc.st_meringegrfisbod "
                            + " FROM tbr_tipdocprg as menu "
                            + " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) "
                            + " WHERE   menu.co_emp = " + objZafParSis.getCodigoEmpresa() + " and "
                            + " menu.co_loc = " + objZafParSis.getCodigoLocal() + " AND  "
                            + " menu.co_mnu = " + objZafParSis.getCodigoMenu() + " AND  menu.st_reg = 'S' ";
                } else {

                    strSql = "SELECT  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor, "
                            + " case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc "
                            + " ,doc.tx_natdoc, doc.st_meringegrfisbod "
                            + " FROM tbr_tipDocUsr as menu "
                            + " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) "
                            + " WHERE   menu.co_emp = " + objZafParSis.getCodigoEmpresa() + " and "
                            + " menu.co_loc = " + objZafParSis.getCodigoLocal() + " AND  "
                            + " menu.co_mnu = " + objZafParSis.getCodigoMenu() + " AND  "
                            + " menu.co_usr=" + objZafParSis.getCodigoUsuario() + " AND menu.st_reg = 'S' ";
                }

                System.out.println("ZafVen28.cargarTipoDoc: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    txtCodTipDoc.setText(((rstLoc.getString("co_tipdoc") == null) ? "" : rstLoc.getString("co_tipdoc")));
                    txtDesCorTipDoc.setText(((rstLoc.getString("tx_descor") == null) ? "" : rstLoc.getString("tx_descor")));
                    txtDesLarTipDoc.setText(((rstLoc.getString("tx_deslar") == null) ? "" : rstLoc.getString("tx_deslar")));
                    strCodTipDoc = txtCodTipDoc.getText();
                    /*strMerIngEgr=((rstLoc.getString("st_meringegrfisbod")==null)?"":rstLoc.getString("st_meringegrfisbod"));
                     strTipIngEgr=((rstLoc.getString("tx_natdoc")==null)?"":rstLoc.getString("tx_natdoc"));*/

                    if (intVal == 1) {
                        txtNumDoc.setText(((rstLoc.getString("numDoc") == null) ? "" : rstLoc.getString("numDoc")));
                    }
                    //objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objZafParSis,  Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ));            
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

    /*public static void main(String args[]){
     ZafRecHum27 objRec=new ZafRecHum27();
     objRec.validarHoraNoAdmiteCruze("18:30", "19:45", 17, 20);
     }*/
    /**
     * Métod para verificar si la solicitud no esta entre el horario de un
     * trabajador
     *
     * @author TonySanginez
     * @param intHorDes
     * @param intMinDes
     * @param intHorHas
     * @param intMinHas
     * @param dia
     * @return
     * @throws SQLException
     */
    public boolean esHoraTraValidaCruce(int intHorDes, int intMinDes, int intHorHas, int intMinHas, int dia) throws SQLException {
        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSQL = "";
        boolean blnCanSol = false;
        boolean blnRetorno = true;
        try {
            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) {
                con.setAutoCommit(false);
                stmLoc = con.createStatement();
                for (int i = 0; i < tblDat.getRowCount(); i++) {
                    if (tblDat.getValueAt(i, INT_TBL_CHKTRA).equals(true)) {
                        strSQL = " select distinct a2.ho_ent, a2.ho_sal "
                                + " from tbm_traemp a1 "
                                + " inner join tbm_dethortra a2 on (a1.co_hor=a2.co_hor) "
                                + " where a1.co_tra= " + tblDat.getValueAt(i, INT_TBL_CODTRA).toString()
                                + " and   a2.ne_dia= " + dia
                                + " and   a1.st_Reg='A' ";
                        rstLoc = stmLoc.executeQuery(strSQL);
                        while (rstLoc.next()) {
                            boolean booTieneCruzeHoras = validarHoraNoAdmiteCruze(rstLoc.getString("ho_ent"), rstLoc.getString("ho_sal"), Integer.parseInt(cboHHDes.getSelectedItem().toString()), Integer.parseInt(cboMMDes.getSelectedItem().toString()), Integer.parseInt(cboHHHas.getSelectedItem().toString()), Integer.parseInt(cboMMHas.getSelectedItem().toString()));
                            if (booTieneCruzeHoras) {
                                blnCanSol = true;
                                blnRetorno = false;
                                i = tblDat.getRowCount();
                                break;
                            }
                            if (blnCanSol) {
                                blnRetorno = false;
                                break;
                            }
                        }
                    }
                }
            }
            con.close();
            con = null;

        } catch (java.sql.SQLException Evt) {
            blnRetorno = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRetorno = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } finally {
            try {
                rstLoc.close();
                rstLoc = null;
            } catch (Throwable ignore) {
            }
            try {
                stmLoc.close();
                stmLoc = null;
            } catch (Throwable ignore) {
            }
            try {
                con.close();
                con = null;
            } catch (Throwable ignore) {
            }
        }
        return blnRetorno;
    }
}
