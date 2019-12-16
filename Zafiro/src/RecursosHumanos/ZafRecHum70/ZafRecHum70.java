package RecursosHumanos.ZafRecHum70;

import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafCtaCtb_dat;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Maestros.ZafMae07.ZafMae07_01;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * Pago del décimo tercer sueldo...
 *
 * @author Roberto Flores Guayaquil 22/11/2013
 */
public class ZafRecHum70 extends javax.swing.JInternalFrame
{
    //Constantes 
    private static final int INT_TBL_LINEA = 0;
    private static final int INT_TBL_CODTRA = 1;
    private static final int INT_TBL_NOMTRA = 2;
    private static final int INT_TBL_FECING = 3;
    private static final int INT_TBL_CODCAR = 4;
    private static final int INT_TBL_CARGO = 5;
    private static final int INT_TBL_CODCARCOMSEC = 6;
    private static final int INT_TBL_NOMCARCOMSEC = 7;
    private static final int INT_TBL_MINSEC = 8;
    
    static final int INT_TBL_DAT_NUM_TOT_CDI = 35;            //Número total de columnas dinámicas.
    
    //Variables
    private java.sql.Connection CONN_GLO = null;
    private java.sql.Statement STM_GLO = null;
    private java.sql.ResultSet rstCab = null;

    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst;
    
    private ZafParSis objZafParSis;
    private ZafTblMod objTblMod;
    private ZafTblTot objTblTot;                               //JTable de totales.
    private ZafTblBus objTblBus;
    private ZafPerUsr objPerUsr;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafCtaCtb_dat objCtaCtb_dat;                        // Para obtener  los codigos y nombres de ctas ctbles
    private ZafAsiDia objAsiDia, objAsiDiaProv;
    private ZafUtil objUti;
    private ZafThreadGUI objThrGUI;
    private ZafRptSis objRptSis;                                //Reportes del Sistema.
    private ZafVenCon vcoTipDoc;                                //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoCta;                                   //Ventana de consulta "Cuenta".
    private ZafVenCon vcoDep;                                   //Ventana de consulta "Departamento".
    private ZafDatePicker txtFecDoc;
    private java.util.Date datFecAux;
    private mitoolbar objTooBar;
    private ZafTblCelRenLbl objTblCelRenLblDL;                                //Render: Presentar JLabel en JTable.
    
    private ArrayList<String> arrLstMeses = null;
    javax.swing.JTable jTblAsiDia = null;
    ZafTblMod zafTblModAsiDia = null;
    
    private boolean blnEstCarSolHSE = false;
    private boolean blnHayCam = false;
    private Vector vecCab = new Vector();
    private Vector vecDat;
    private Vector vecAux;
    private Vector vecAsiDia;
    Vector vecTipDoc, vecDetDiario;                             //Vector que contiene el codigo de tipos de documentos    
    
    private static int intValTotDiaLabPos = 45;                 //constantes
    private static int[] intCabDCS = { 57, 58};
    private static int[] intValDTSProv;
    private static int[] intValDiaLab;
    
    private int intCodEmp, intCodLoc;                           //Código de la empresa y local.
    private int intSig = 1;                                     //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    int intCanColTot = 0;
    private int intCodTipDocProv = 0;
    private int intCodDocProv = 0;
    
    private String strSQL, strAux;
    private String strDesCorCta, strDesLarCta;                  //Contenido del campo al obtener el foco.
    private String strUbiCta, strAutCta;                        //Campos: Ubicacián y Estado de autorizacián de la cuenta.
    private String strDesCorTipDoc = "";
    private String strDesLarTipDoc = "";
    private String strCodTipDoc = "";
    private String strDesDep = "";
    private String strCodEmpAut = "";
    private String strCodLocAut = "";
    private String strCodTipDocAut = "";
    private String strCodDocAut = "";
    private String strNumDoc = "";
    
    private String strVersion = " v1.04 ";

    /**
     * Constructor ZafRecHum70
     */
    public ZafRecHum70(Librerias.ZafParSis.ZafParSis obj) 
    {
        try 
        {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            /*INSERTAR LOS AÑOS EXISTENTES EN TBM_INGEGRMENTRA*/
            cargarAños();

            objUti = new ZafUtil();

            intCodEmp = objZafParSis.getCodigoEmpresa();
            intCodLoc = objZafParSis.getCodigoLocal();

            if (objZafParSis.getCodigoMenu() == 3643 || objZafParSis.getCodigoMenu() == 3653) {
                insertarMesesPerCom(1);
            } else if (objZafParSis.getCodigoMenu() == 3782 || objZafParSis.getCodigoMenu() == 3792) {
                insertarMesesPerCom(2);
            } else if (objZafParSis.getCodigoMenu() == 3800 || objZafParSis.getCodigoMenu() == 3810) {
                insertarMesesPerCom(1);
            }

            objTooBar = new mitoolbar(this);
            this.getContentPane().add(objTooBar, "South");
            //objTooBar.setVisibleModificar(false);

            objRptSis = new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

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

            objAsiDia = new ZafAsiDia(objZafParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals("")) {
                        objAsiDia.setCodigoTipoDocumento(-1);
                    } else {
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                    }
                }
            });
            panAsiDia.add(objAsiDia, java.awt.BorderLayout.CENTER);

            this.setTitle(objZafParSis.getNombreMenu() + " " + strVersion);
            lblTit.setText(objZafParSis.getNombreMenu());
        } 
        catch (CloneNotSupportedException e) {      objUti.mostrarMsgErr_F1(this, e);       e.printStackTrace();        } 
        catch (Exception e) {      objUti.mostrarMsgErr_F1(this, e);        e.printStackTrace();      }
    }

    public ZafRecHum70(Librerias.ZafParSis.ZafParSis obj, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strNumDoc)
    {
        try
        {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            this.setTitle(objZafParSis.getNombreMenu() + " " + strVersion);
            lblTit.setText(objZafParSis.getNombreMenu());

            strCodEmpAut = strCodEmp;
            strCodLocAut = strCodLoc;
            strCodTipDocAut = strCodTipDoc;
            strCodDocAut = strCodDoc;
            this.strNumDoc = strNumDoc;
            blnEstCarSolHSE = true;

            objUti = new ZafUtil();

            if (objZafParSis.getCodigoMenu() == 3643 || objZafParSis.getCodigoMenu() == 3653) {
                insertarMesesPerCom(1);
            } else if (objZafParSis.getCodigoMenu() == 3782 || objZafParSis.getCodigoMenu() == 3792) {
                insertarMesesPerCom(2);
            } else if (objZafParSis.getCodigoMenu() == 3800 || objZafParSis.getCodigoMenu() == 3810) {
                insertarMesesPerCom(1);
            }

            objTooBar = new ZafRecHum70.mitoolbar(this);
            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleModificar(false);
            objTooBar.setVisibleAnular(false);
            objTooBar.setVisibleConsultar(false);
            objTooBar.setVisibleImprimir(false);
            objTooBar.setVisibleVistaPreliminar(false);
            objTooBar.setVisible(false);

            String strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText(strFecSis);
            PanTabGen.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);

            objAsiDia = new ZafAsiDia(objZafParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals("")) {
                        objAsiDia.setCodigoTipoDocumento(-1);
                    } else {
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                    }
                }
            });
            panAsiDia.add(objAsiDia, java.awt.BorderLayout.CENTER);

        }
        catch (CloneNotSupportedException e) {        objUti.mostrarMsgErr_F1(this, e);        }
    }

    private boolean cargarAños() 
    {
        boolean blnRes = true;
        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSQL = "";

        try 
        {
            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) {

                stmLoc = con.createStatement();
                int intLstAni = 0;
                strSQL = "select distinct ne_ani from tbm_ingEgrMenTra order by ne_ani asc";
                rstLoc = stmLoc.executeQuery(strSQL);
                while (rstLoc.next()) {
                    cboPerAAAA.addItem(rstLoc.getString("ne_ani"));
                }
                cboPerAAAA.setSelectedIndex(cboPerAAAA.getItemCount() - 1);
            }
        } 
        catch (java.sql.SQLException Evt) {         blnRes = false;        objUti.mostrarMsgErr_F1(this, Evt);        }
        catch (Exception Evt) {        blnRes = false;         objUti.mostrarMsgErr_F1(this, Evt);       } 
        finally 
        {
            try {     rstLoc.close();        } catch (Throwable ignore) {         }
            try {     stmLoc.close();        } catch (Throwable ignore) {         }
            try {     con.close();           } catch (Throwable ignore) {         }
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc() 
    {
        boolean blnRes = true;
        String strSQL = "";
        try
        {
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
            if (objZafParSis.getCodigoUsuario() == 1) 
            {
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                strSQL += " FROM tbm_cabTipDoc AS a1";
                strSQL += " INNER JOIN tbr_tipDocPrg AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL += " WHERE a1.co_emp=" + intCodEmp;
                strSQL += " AND a1.co_loc=" + intCodLoc;
                strSQL += " AND a2.co_mnu=" + objZafParSis.getCodigoMenu();
                strSQL += " ORDER BY a1.tx_desCor";
            }
            else 
            {
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
        }
        catch (Exception e) {        blnRes = false;         objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }

    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConCta()
    {
        boolean blnRes = true;
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codCta");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a2.tx_ubiCta");
            arlCam.add("a1.st_aut");
            arlCam.add("a1.ne_ultnumchq");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cuenta");
            arlAli.add("Nombre");
            arlAli.add("Ubicación");
            arlAli.add("Autorización");
            arlAli.add("Número de Cheque");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("100");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            //Ocultar columnas.
            int intColOcu[] = new int[2];
            intColOcu[0] = 6;
            vcoCta = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de cuentas", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoCta.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCta.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
        } 
        catch (Exception e) {       blnRes = false;         objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }

    /**
     * Esta función muestra el tipo de documento predeterminado del programa.
     *
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre()
    {
        boolean blnRes = true;
        try 
        {
            con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objZafParSis.getCodigoUsuario() == 1) {
                    //Armar la sentencia SQL.
                    if (!blnEstCarSolHSE) {
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
                        strSQL = "";
                        strSQL += "SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                        strSQL += " FROM tbm_cabTipDoc AS a1";
                        strSQL += " WHERE a1.co_emp=" + strCodEmpAut;
                        strSQL += " AND a1.co_loc=" + strCodLocAut;
                        strSQL += " AND a1.co_tipDoc=";
                        strSQL += " (";
                        strSQL += " SELECT co_tipDoc";
                        strSQL += " FROM tbr_tipDocPrg";
                        strSQL += " WHERE co_emp=" + strCodEmpAut;
                        strSQL += " AND co_loc=" + strCodLocAut;
                        strSQL += " AND co_mnu=" + objZafParSis.getCodigoMenu();
                        strSQL += " AND st_reg='S'";
                        strSQL += " )";
                        rst = stm.executeQuery(strSQL);
                    }
                } else {
                    //Armar la sentencia SQL.
                    strSQL = "";
                    if (!blnEstCarSolHSE) {
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
                    } else {
                        strSQL += "SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                        strSQL += " FROM tbm_cabTipDoc AS a1";
                        strSQL += " WHERE a1.co_emp=" + strCodEmpAut;
                        strSQL += " AND a1.co_loc=" + strCodLocAut;
                        strSQL += " AND a1.co_tipDoc=";
                        strSQL += " (";
                        strSQL += " SELECT co_tipDoc";
                        strSQL += " FROM tbr_tipDocUsr";
                        strSQL += " WHERE co_emp=" + strCodEmpAut;
                        strSQL += " AND co_loc=" + strCodLocAut;
                        strSQL += " AND co_mnu=" + objZafParSis.getCodigoMenu();
                        strSQL += " AND co_usr=" + objZafParSis.getCodigoUsuario();
                        strSQL += " AND st_reg='S'";
                        strSQL += " )";
                    }

                    rst = stm.executeQuery(strSQL);
                }
                if (rst.next()) {
                    System.out.println(rst.getString("co_tipDoc"));
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtNumDoc.setText("" + (rst.getInt("ne_ultDoc") + 1));//THERE
                    intSig = (rst.getString("tx_natDoc").equals("I") ? 1 : -1);
                    //strMerIngEgrFisBodTipDoc=rst.getString("st_merIngEgrFisBod");
//                    objTblMod.setMaxRowsAllowed(Integer.valueOf(rst.getInt("ne_numLin")));
                    objCtaCtb_dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objZafParSis, Integer.parseInt((txtCodTipDoc.getText().equals("")) ? "0" : txtCodTipDoc.getText()));
                }
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
            }
        }
        catch (java.sql.SQLException e) {        blnRes = false;         objUti.mostrarMsgErr_F1(this, e);     } 
        catch (Exception e) {           blnRes = false;          objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }

    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de básqueda determina si se debe
     * hacer una básqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opcián que desea utilizar.
     *
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCta(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            //Validar que sálo se pueda seleccionar la "Cuenta" si se seleccioná el "Tipo de documento".
            if (txtCodTipDoc.getText().equals("")) {
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                txtDesLarCta.setText("");
                strUbiCta = "";
                strAutCta = "";
                mostrarMsgInf("<HTML>Primero debe seleccionar un <FONT COLOR=\"blue\">Tipo de documento</FONT>.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                txtDesCorTipDoc.requestFocus();
            } else {
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta, a1.st_aut, a1.ne_ultnumchq";
                strSQL += " FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                strSQL += " WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL += " AND a2.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL += " AND a2.co_loc=" + objZafParSis.getCodigoLocal();
                strSQL += " AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL += " ORDER BY a1.tx_codCta";
                //System.out.println("mostrarVenConCta:" + strSQL);

                //System.out.println("NEFWNFKWF: " + objTooBar.getEstado());

                vcoCta.setSentenciaSQL(strSQL);
                switch (intTipBus) {
                    case 0: //Mostrar la ventana de consulta.
                        vcoCta.setCampoBusqueda(1);
                        vcoCta.show();
                        if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE) {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                            strUbiCta = vcoCta.getValueAt(4);
                            strAutCta = vcoCta.getValueAt(5);
                        }
                        break;
                    case 1: //Básqueda directa por "Námero de cuenta".
                        if (vcoCta.buscar("a1.tx_codCta", txtDesCorCta.getText())) {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                            strUbiCta = vcoCta.getValueAt(4);
                            strAutCta = vcoCta.getValueAt(5);
                        } else {
                            vcoCta.setCampoBusqueda(0);
                            vcoCta.setCriterio1(11);
                            vcoCta.cargarDatos();
                            vcoCta.show();
                            if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE) {
                                txtCodCta.setText(vcoCta.getValueAt(1));
                                txtDesCorCta.setText(vcoCta.getValueAt(2));
                                txtDesLarCta.setText(vcoCta.getValueAt(3));
                                strUbiCta = vcoCta.getValueAt(4);
                                strAutCta = vcoCta.getValueAt(5);
                            } else {
                                txtDesCorCta.setText(strDesCorCta);
                            }
                        }
                        break;
                    case 2: //Básqueda directa por "Descripcián larga".
                        if (vcoCta.buscar("a1.tx_desLar", txtDesLarCta.getText())) {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                            strUbiCta = vcoCta.getValueAt(4);
                            strAutCta = vcoCta.getValueAt(5);
                        } else {
                            vcoCta.setCampoBusqueda(1);
                            vcoCta.setCriterio1(11);
                            vcoCta.cargarDatos();
                            vcoCta.show();
                            if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE) {
                                txtCodCta.setText(vcoCta.getValueAt(1));
                                txtDesCorCta.setText(vcoCta.getValueAt(2));
                                txtDesLarCta.setText(vcoCta.getValueAt(3));
                                strUbiCta = vcoCta.getValueAt(4);
                                strAutCta = vcoCta.getValueAt(5);
                            } else {
                                txtDesLarCta.setText(strDesLarCta);
                            }
                        }
                        break;
                }
            }
        } 
        catch (Exception e) {        blnRes = false;         objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }

    public void setEditable(boolean editable) 
    {
        if (editable == true) {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        } else {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }

    public void abrirCon()
    {
        try {
            CONN_GLO = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    public void CerrarCon() 
    {
        try {
            CONN_GLO.close();
            CONN_GLO = null;
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    private boolean insertarMesesPerCom(int intOpSel) 
    {
        boolean blnRes = false;
        try 
        {
            switch (intOpSel)
            {
                case 1:
                    arrLstMeses = new ArrayList<String>();
                    arrLstMeses.add("Diciembre");
                    arrLstMeses.add("Enero");
                    arrLstMeses.add("Febrero");
                    arrLstMeses.add("Marzo");
                    arrLstMeses.add("Abril");
                    arrLstMeses.add("Mayo");
                    arrLstMeses.add("Junio");
                    arrLstMeses.add("Julio");
                    arrLstMeses.add("Agosto");
                    arrLstMeses.add("Septiembre");
                    arrLstMeses.add("Octubre");
                    arrLstMeses.add("Noviembre");
                    blnRes = true;
                    break;
                case 2:
                    arrLstMeses = new ArrayList<String>();
                    arrLstMeses.add("Enero");
                    arrLstMeses.add("Febrero");
                    arrLstMeses.add("Marzo");
                    arrLstMeses.add("Abril");
                    arrLstMeses.add("Mayo");
                    arrLstMeses.add("Junio");
                    arrLstMeses.add("Julio");
                    arrLstMeses.add("Agosto");
                    arrLstMeses.add("Septiembre");
                    arrLstMeses.add("Octubre");
                    arrLstMeses.add("Noviembre");
                    arrLstMeses.add("Diciembre");
                    blnRes = true;
//                    intPosArrLstMesesRegTraAnt=5;
            }
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
//    private boolean configurarTabla(int intSel)
    private boolean configurarTabla()
    {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            //vecDat=new Vector();    //Almacena los datos
            vecCab = new Vector();  //Almacena las cabeceras
            vecCab.clear();

            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CODTRA, "Código");
            vecCab.add(INT_TBL_NOMTRA, "Empleado");
            vecCab.add(INT_TBL_FECING, "Fec.Ing.");
            vecCab.add(INT_TBL_CODCAR, "Cód.Car.");
            vecCab.add(INT_TBL_CARGO, "Cargo");
            vecCab.add(INT_TBL_CODCARCOMSEC, "Cód.Car.Com.Sec.");
            vecCab.add(INT_TBL_NOMCARCOMSEC, "Nom.Car.Com.Sec.");
            vecCab.add(INT_TBL_MINSEC, "Mín.Sec.");

            intValDTSProv = new int[(arrLstMeses.size() * 3) + 1];
            intValDiaLab = new int[arrLstMeses.size()];
            int intCont = 0;
            int intContDL = 0;
            for (Iterator<String> it = arrLstMeses.iterator(); it.hasNext(); it.next()) {
                intValDTSProv[intCont++] = vecCab.size();
                vecCab.add(vecCab.size(), "Sueldo");
                intValDTSProv[intCont++] = vecCab.size();
                vecCab.add(vecCab.size(), "Comisión");
                intValDiaLab[intContDL++] = vecCab.size();
                vecCab.add(vecCab.size(), "Día.Lab.");
                intValDTSProv[intCont++] = vecCab.size();
                vecCab.add(vecCab.size(), "Valor");

            }

            vecCab.add(vecCab.size(), "Día.Lab.");
            System.out.println("vecCab.size(): " + vecCab.size());
            intValDTSProv[intCont++] = vecCab.size();
            vecCab.add(vecCab.size(), "Valor");
            vecCab.add(vecCab.size(), "Observación");
            vecCab.add(vecCab.size(), "");//BUTOBS
            System.out.println("vecCab.size = " + vecCab.size());

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

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_CODTRA).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_MINSEC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            objTblCelRenLbl = new ZafTblCelRenLbl();
            //objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);

            tcmAux.getColumn(INT_TBL_CODTRA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CODCAR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODTRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOMTRA).setPreferredWidth(240);
            tcmAux.getColumn(INT_TBL_FECING).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CODCAR).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CARGO).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_CODCARCOMSEC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_NOMCARCOMSEC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_MINSEC).setPreferredWidth(50);

            tcmAux.getColumn(vecCab.size() - 1).setPreferredWidth(25);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_LINEA).setResizable(false);
            tcmAux.getColumn(INT_TBL_CODTRA).setResizable(false);
            tcmAux.getColumn(INT_TBL_FECING).setResizable(false);
            tcmAux.getColumn(INT_TBL_CODCARCOMSEC).setResizable(false);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Ocultar columnas del sistema.
//            objTblMod.addSystemHiddenColumn(vecCab.size()-1, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + (tblDat.getColumnCount() - 1));
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
            //cambios realizados a la tabla
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            //objTblCelRenLbl=null;

            objTblCelEdiTxt = new ZafTblCelEdiTxt(tblDat);

            objTblCelRenLblDL = new ZafTblCelRenLbl();
            objTblCelRenLblDL.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblDL.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLblDL.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            //objTblCelRenLbl=null;

            int intP = INT_TBL_MINSEC + 1;

            int intArrValDbl[] = new int[arrLstMeses.size()];

            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            for (int i = 0; i < 1; i++) {
                for (int x = intP; x < tblDat.getColumnCount(); x++) {

//                    if(((x+i)%3)==0){
//                        objTblMod.setColumnDataType(x+i, objTblMod.INT_COL_DBL, new Integer(0), null);
////                        tcmAux.getColumn(x+i).setCellEditor(objTblCelEdiTxt);
//                        tcmAux.getColumn(x+i).setCellRenderer(objTblCelRenLbl);
////                        tcmAux.getColumn(x+i).setCellEditor(objTblCelEdiTxt);
//                    }else{
//                        objTblMod.setColumnDataType(x+i, objTblMod.INT_COL_INT, new Integer(0), null);
//                        tcmAux.getColumn(x+i).setCellRenderer(objTblCelRenLblDL);
//                        tcmAux.getColumn(x+i).setCellEditor(objTblCelEdiTxt);
//                    }
//                    if(x==intValTotDiaLabPos){
//                        objTblMod.setColumnDataType(x, objTblMod.INT_COL_INT, new Integer(0), null);
//                        tcmAux.getColumn(x).setCellRenderer(objTblCelRenLblDL);
//                        tcmAux.getColumn(x).setCellEditor(objTblCelEdiTxt);
//                    }
                    for (int intPosVal = 0; intPosVal < intValDTSProv.length; intPosVal++) {
//                        if(intValDTSProv[intPosVal]==x){
                        objTblMod.setColumnDataType(intValDTSProv[intPosVal], objTblMod.INT_COL_DBL, new Integer(0), null);
                        tcmAux.getColumn(intValDTSProv[intPosVal]).setCellEditor(objTblCelEdiTxt);
                        tcmAux.getColumn(intValDTSProv[intPosVal]).setCellRenderer(objTblCelRenLbl);
                        System.out.println(intValDTSProv[intPosVal]);

                    }

                    for (int intPosVal = 0; intPosVal < intValDiaLab.length; intPosVal++) {
                        objTblMod.setColumnDataType(intValDiaLab[intPosVal], objTblMod.INT_COL_INT, new Integer(0), null);
                        tcmAux.getColumn(intValDiaLab[intPosVal]).setCellRenderer(objTblCelRenLblDL);
                        tcmAux.getColumn(intValDiaLab[intPosVal]).setCellEditor(objTblCelEdiTxt);
                    }

                    objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                        int intFilSel;//=tblDat.getSelectedRow();

                        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        }

                        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        }
                    });
                }
            }

            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(tblDat.getColumnCount() - 1).setCellRenderer(zafTblDocCelRenBut);
            ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, tblDat.getColumnCount() - 1, "Justificación") {
                public void butCLick() {
                    int intSelFil = tblDat.getSelectedRow();
                    int intSelCol = tblDat.getColumnCount() - 2;
                    String strObs = (tblDat.getValueAt(intSelFil, intSelCol) == null ? "" : tblDat.getValueAt(intSelFil, intSelCol).toString());
                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum70.this), true, strObs);
                    zafMae07_01.show();
                    if (zafMae07_01.getAceptar()) {
                        tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, intSelCol);
                    }
                }
            };

            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            objTblTot = new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intValDTSProv);

            //Configurar JTable: Editor de búsqueda.
            objTblBus = new ZafTblBus(tblDat);

            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public void Configura_ventana_consulta() {
        configurarVenConTipDoc();
        mostrarTipDocPre();
        configurarVenConCta();

        configurarTabla();
//       configuraTblProv();
        agregarColTblDat();

        if (blnEstCarSolHSE) {
            mostrarCtaPre(1);
            vecDat = null;
            consultarReg();
        } else {
            mostrarTipDocPre();
            mostrarCtaPre(1);
        }

    }

    private boolean agregarColTblDat() {

        int i, intNumFil, intNumColTblDat;
        ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16 * 2);
        ZafTblHeaColGrp objTblHeaColGrpEmp = null;
        java.awt.Color colFonCol;
        boolean blnRes = true;

        try {
            intNumFil = objTblMod.getRowCountTrue();
            intNumColTblDat = objTblMod.getColumnCount();

            objTblHeaColGrpEmp = new ZafTblHeaColGrp("");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_LINEA));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

            objTblHeaColGrpEmp = new ZafTblHeaColGrp("");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CODTRA));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_NOMTRA));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_FECING));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CODCAR));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CODCARCOMSEC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CARGO));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_NOMCARCOMSEC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_MINSEC));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

            int intCont = INT_TBL_MINSEC + 1;

            for (Iterator<String> it = arrLstMeses.iterator(); it.hasNext();) {
                String strMes = it.next().toString();
                System.out.println("mes: " + strMes);
                objTblHeaColGrpEmp = new ZafTblHeaColGrp(strMes);
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                for (i = 0; i < 4; i++) {
                    objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(intCont));
                    intCont++;
                }
            }

            objTblHeaColGrpEmp = new ZafTblHeaColGrp("Totales");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
            for (int intPos = 0; intPos < intCabDCS.length; intPos++) {
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(intCabDCS[intPos]));
//                            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(57));
            }

            objTblHeaColGrpEmp = new ZafTblHeaColGrp("");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(tblDat.getColumnCount() - 2));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(tblDat.getColumnCount() - 1));

        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
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
        JLblTipDoc = new javax.swing.JLabel();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butBusTipDoc = new javax.swing.JButton();
        JLblFecDoc = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        JLlblNumDoc = new javax.swing.JLabel();
        jLblCta = new javax.swing.JLabel();
        JLblValDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        txtValDoc = new javax.swing.JTextField();
        JLblCodDoc = new javax.swing.JLabel();
        cboPerAAAA = new javax.swing.JComboBox();
        txtDesCorCta = new javax.swing.JTextField();
        txtCodCta = new javax.swing.JTextField();
        txtDesLarCta = new javax.swing.JTextField();
        butCta = new javax.swing.JButton();
        txtCodTipDoc = new javax.swing.JTextField();
        txtCodDoc = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        panDetTraDep = new javax.swing.JPanel();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panObs = new javax.swing.JPanel();
        panLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panTxa = new javax.swing.JPanel();
        spnObs3 = new javax.swing.JScrollPane();
        txtObs1 = new javax.swing.JTextArea();
        spnObs4 = new javax.swing.JScrollPane();
        txtObs2 = new javax.swing.JTextArea();
        panAsiDia = new javax.swing.JPanel();
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

        PanTabGen.setPreferredSize(new java.awt.Dimension(100, 100));
        PanTabGen.setLayout(null);

        JLblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        JLblTipDoc.setText("Tipo de Documento:"); // NOI18N
        PanTabGen.add(JLblTipDoc);
        JLblTipDoc.setBounds(10, 10, 120, 20);

        txtDesLarTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesLarTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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

        butBusTipDoc.setText("jButton1"); // NOI18N
        butBusTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusTipDocActionPerformed(evt);
            }
        });
        PanTabGen.add(butBusTipDoc);
        butBusTipDoc.setBounds(420, 10, 20, 20);

        JLblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        JLblFecDoc.setText("Fecha del Documento:"); // NOI18N
        PanTabGen.add(JLblFecDoc);
        JLblFecDoc.setBounds(450, 10, 130, 20);

        txtDesCorTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesCorTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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

        JLlblNumDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        JLlblNumDoc.setText("Número de Documento:"); // NOI18N
        PanTabGen.add(JLlblNumDoc);
        JLlblNumDoc.setBounds(450, 30, 140, 20);

        jLblCta.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLblCta.setText("Cuenta:"); // NOI18N
        PanTabGen.add(jLblCta);
        jLblCta.setBounds(10, 30, 110, 20);

        JLblValDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        JLblValDoc.setText("Valor del Documento:"); // NOI18N
        PanTabGen.add(JLblValDoc);
        JLblValDoc.setBounds(450, 70, 140, 20);

        txtNumDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDocFocusGained(evt);
            }
        });
        PanTabGen.add(txtNumDoc);
        txtNumDoc.setBounds(590, 30, 80, 20);

        txtValDoc.setBackground(objZafParSis.getColorCamposSistema());
        txtValDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValDoc.setText("0.0");
        PanTabGen.add(txtValDoc);
        txtValDoc.setBounds(590, 70, 80, 20);

        JLblCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        JLblCodDoc.setText("Código del Documento:"); // NOI18N
        PanTabGen.add(JLblCodDoc);
        JLblCodDoc.setBounds(450, 50, 140, 20);

        cboPerAAAA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Año" }));
        cboPerAAAA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboPerAAAAMouseClicked(evt);
            }
        });
        cboPerAAAA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPerAAAAActionPerformed(evt);
            }
        });
        PanTabGen.add(cboPerAAAA);
        cboPerAAAA.setBounds(110, 50, 100, 20);

        txtDesCorCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorCtaActionPerformed(evt);
            }
        });
        txtDesCorCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorCtaFocusLost(evt);
            }
        });
        PanTabGen.add(txtDesCorCta);
        txtDesCorCta.setBounds(140, 30, 70, 20);

        txtCodCta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        PanTabGen.add(txtCodCta);
        txtCodCta.setBounds(108, 30, 32, 20);
        txtCodCta.setVisible(true);

        txtDesLarCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCtaActionPerformed(evt);
            }
        });
        txtDesLarCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCtaFocusLost(evt);
            }
        });
        PanTabGen.add(txtDesLarCta);
        txtDesLarCta.setBounds(210, 30, 210, 20);

        butCta.setText("...");
        butCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaActionPerformed(evt);
            }
        });
        PanTabGen.add(butCta);
        butCta.setBounds(420, 30, 20, 20);

        txtCodTipDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        PanTabGen.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(108, 10, 32, 20);
        //txtCodTipDoc.setVisible(false);

        txtCodDoc.setEditable(false);
        txtCodDoc.setBackground(objZafParSis.getColorCamposSistema());
        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        PanTabGen.add(txtCodDoc);
        txtCodDoc.setBounds(590, 50, 80, 20);

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel8.setText("Período:"); // NOI18N
        PanTabGen.add(jLabel8);
        jLabel8.setBounds(10, 50, 110, 20);

        panTabGen.add(PanTabGen, java.awt.BorderLayout.PAGE_START);

        panDetTraDep.setLayout(new java.awt.BorderLayout());

        spnTot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTot.setViewportView(tblTot);

        panDetTraDep.add(spnTot, java.awt.BorderLayout.SOUTH);

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
        spnDat.setViewportView(tblDat);

        panDetTraDep.add(spnDat, java.awt.BorderLayout.CENTER);

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

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabGen.addTab("Asiento de diario", panAsiDia);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panTit.setPreferredSize(new java.awt.Dimension(100, 30));

        lblTit.setText("titulo"); // NOI18N
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.PAGE_START);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
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

private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
    txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtFecDocActionPerformed(java.awt.event.ActionEvent evt) {
        txtFecDoc.transferFocus();
    }

private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
    strDesLarTipDoc = txtDesLarTipDoc.getText();
    txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost

    if (txtDesLarTipDoc.isEditable()) {
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(2);
            }
        } else {
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
    }
}//GEN-LAST:event_txtDesLarTipDocFocusLost

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

        if (objZafParSis.getCodigoMenu() != 3138) {
            if (objTooBar.getEstado() == 'n') {
                strCodTipDoc = txtCodTipDoc.getText();
                System.out.println("codtipdoc: " + strCodTipDoc);
//                procesoInsertarRolBonMov(strCodTipDoc);
            }
        }

        return blnRes;
    }

    private void llamarManCtaCon(String strCodEmp, String strNeAni, String strNeMes, String strNePer) {
        RecursosHumanos.ZafRecHum37.ZafRecHum37_01 obj1 = new RecursosHumanos.ZafRecHum37.ZafRecHum37_01(objZafParSis, strCodEmp, strNeAni, strNeMes, strNePer);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }

private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
    Configura_ventana_consulta();
}//GEN-LAST:event_formInternalFrameOpened

private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
    // TODO add your handling code here:
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

        System.gc();
        dispose();
    }
}//GEN-LAST:event_formInternalFrameClosing

    public void BuscarDep(String campo, String strBusqueda, int tipo) {

        vcoDep.setTitle("Listado de Departamentos");
        if (vcoDep.buscar(campo, strBusqueda)) {
            txtDesLarCta.setText(vcoDep.getValueAt(3));
        } else {
            vcoDep.setCampoBusqueda(tipo);
            vcoDep.cargarDatos();
            vcoDep.show();
            if (vcoDep.getSelectedButton() == vcoDep.INT_BUT_ACE) {
                txtDesLarCta.setText(vcoDep.getValueAt(3));
            } else {
                txtDesLarCta.setText(strDesDep);
            }
        }
    }

    private void txtNumDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusGained
        // TODO add your handling code here:
        txtNumDoc.selectAll();
    }//GEN-LAST:event_txtNumDocFocusGained

    private void txtDesCorCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorCtaActionPerformed
        txtDesCorCta.transferFocus();
    }//GEN-LAST:event_txtDesCorCtaActionPerformed

    private void txtDesCorCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaFocusGained
        strDesCorCta = txtDesCorCta.getText();
        txtDesCorCta.selectAll();
    }//GEN-LAST:event_txtDesCorCtaFocusGained

    private void txtDesCorCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesCorCta.getText().equalsIgnoreCase(strDesCorCta)) {
            if (txtDesCorCta.getText().equals("")) {
                txtCodCta.setText("");
                txtDesLarCta.setText("");
                //objTblMod.removeAllRows();
            } else {
                mostrarVenConCta(1);
            }
        } else {
            txtDesCorCta.setText(strDesCorCta);
        }
    }//GEN-LAST:event_txtDesCorCtaFocusLost

    private void txtDesLarCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCtaActionPerformed
        txtDesLarCta.transferFocus();
    }//GEN-LAST:event_txtDesLarCtaActionPerformed

    private void txtDesLarCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaFocusGained
        strDesLarCta = txtDesLarCta.getText();
        txtDesLarCta.selectAll();
    }//GEN-LAST:event_txtDesLarCtaFocusGained

    private void txtDesLarCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarCta.getText().equalsIgnoreCase(strDesLarCta)) {
            if (txtDesLarCta.getText().equals("")) {
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                //objTblMod.removeAllRows();
            } else {
                mostrarVenConCta(2);
            }
        } else {
            txtDesLarCta.setText(strDesLarCta);
        }
    }//GEN-LAST:event_txtDesLarCtaFocusLost

    private void butCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaActionPerformed
        mostrarVenConCta(0);
    }//GEN-LAST:event_butCtaActionPerformed

    private void cboPerAAAAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPerAAAAActionPerformed

    }//GEN-LAST:event_cboPerAAAAActionPerformed

    private void cboPerAAAAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboPerAAAAMouseClicked

    }//GEN-LAST:event_cboPerAAAAMouseClicked

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     *
     * @param strMsg El mensaje que se desea mostrar en el terdro de diálogo.
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLblCodDoc;
    private javax.swing.JLabel JLblFecDoc;
    private javax.swing.JLabel JLblTipDoc;
    private javax.swing.JLabel JLblValDoc;
    private javax.swing.JLabel JLlblNumDoc;
    private javax.swing.JPanel PanTabGen;
    private javax.swing.JButton butBusTipDoc;
    private javax.swing.JButton butCta;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboPerAAAA;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLblCta;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panDetTraDep;
    private javax.swing.JPanel panLbl;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTit;
    private javax.swing.JPanel panTxa;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs3;
    private javax.swing.JScrollPane spnObs4;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodCta;
    private javax.swing.JTextField txtCodDoc;
    public javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorCta;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarCta;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextArea txtObs1;
    private javax.swing.JTextArea txtObs2;
    private javax.swing.JTextField txtValDoc;
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
            strAux = objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) {
                MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;

            }
            if (strAux.equals("Anulado")) {
                MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }

            if (!anularReg()) {
                return false;
            }
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam = false;
            return true;
        }

        private boolean anularReg() {

            boolean blnRes = false;
            try {
                con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                con.setAutoCommit(false);
                if (con != null) {
                    if (anularCab()) {
//                    if(actualizarTbmIngEgrMenTra()){

                        if (objAsiDia.anularDiario(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()))) {
                            con.commit();
                            blnRes = true;
                        } else {
                            con.rollback();
                        }
//                    }else{
//                        con.rollback();
//                    }
                    } else {
                        con.rollback();
                    }
                }
                con.close();
                con = null;
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;

        }

        private boolean actualizarTbmIngEgrMenTra() {
            boolean blnRes = true;
//        int intNePer=-1;
//        try
//        {
//            if (con!=null)
//            {
//                stm=con.createStatement();
//                
//                if(cboPer.getSelectedItem().toString().equals("Primera quincena")){
//                    intNePer=1;
//                }else if(cboPer.getSelectedItem().toString().equals("Fin de mes")){
//                    intNePer=2;
//                }
//                
//                //Armar la sentencia SQL.
//                
//                strSQL="";
//                strSQL="update tbm_ingEgrMenTra set st_rolpaggen = null";
//                strSQL+=" where co_emp = " + objZafParSis.getCodigoEmpresa();
//                strSQL+=" and ne_ani = " + cboPerAAAA.getSelectedItem().toString();
//                strSQL+=" and ne_mes = " + cboPerMes.getSelectedIndex();
//                strSQL+=" and ne_per = " + intNePer;
//                stm.executeUpdate(strSQL);
//                stm.close();
//                stm=null;
//            }
//        }    
//        catch (java.sql.SQLException e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
            return blnRes;
        }

        /**
         * Esta función permite anular la cabecera de un registro.
         *
         * @return true: Si se pudo anular la cabecera del registro.
         * <BR>false: En el caso contrario.
         */
        private boolean anularCab() {
            boolean blnRes = true;
            try {
                if (con != null) {
                    stm = con.createStatement();
                    //Obtener la fecha del servidor.
                    datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux == null) {
                        return false;
                    }
                    //Armar la sentencia SQL.
                    strSQL = "";
                    strSQL += "UPDATE tbm_cabrolpag";
                    strSQL += " SET st_reg='I'";
                    strSQL += ", fe_ultMod='" + objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos()) + "'";
                    strSQL += ", co_usrMod=" + objZafParSis.getCodigoUsuario();
                    strSQL += " WHERE co_emp=" + rstCab.getString("co_emp");
                    strSQL += " AND co_loc=" + rstCab.getString("co_loc");
                    strSQL += " AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                    strSQL += " AND co_doc=" + rstCab.getString("co_doc");
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm = null;
                    datFecAux = null;
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

        public void clickAceptar() {
            setEstadoBotonMakeFac();
        }

        public void clickAnterior() {
            try {
                if (!rstCab.isFirst()) {
                    rstCab.previous();
                    cargarReg();
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickSiguiente() {
            try {
                if (!rstCab.isLast()) {
                    rstCab.next();
                    cargarReg();
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInicio() {
            try {
                if (!rstCab.isFirst()) {
                    rstCab.first();
                    cargarReg();
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickFin() {
            try {
                if (!rstCab.isLast()) {
                    rstCab.last();
                    cargarReg();
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
//        butArc.setEnabled(true);
        }

        public void clickEliminar() {
//          noEditable(false);
        }

        public void clickInsertar() {
            try {

                java.awt.Color colBack;
                colBack = txtValDoc.getBackground();
                txtValDoc.setEditable(false);
                txtValDoc.setBackground(colBack);
                txtCodDoc.setEditable(false);
                txtCodDoc.setBackground(colBack);
//
                datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                java.util.Date dateObj = datFecAux;
                java.util.Calendar calObj = java.util.Calendar.getInstance();
                calObj.setTime(dateObj);
                txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                        calObj.get(java.util.Calendar.MONTH) + 1,
                        calObj.get(java.util.Calendar.YEAR));

                mostrarCtaPre(1);
                cboPerAAAA.setSelectedIndex(cboPerAAAA.getItemCount() - 1);
                if (cboPerAAAA.getSelectedIndex() > 0) {
                    if (cargarRolPer()) {
                        if (!blnEstCarSolHSE) {
                            if (generaAsiento()) {

                            }
                        } else {
                            mostrarTipDocPre();
                            System.out.println("co_tipdoc ----> " + Integer.parseInt(txtCodTipDoc.getText()));
                            System.out.println("co_doc ----> " + Integer.parseInt(txtCodDoc.getText()));
                            objAsiDia.consultarDiario(Integer.parseInt(strCodEmpAut), Integer.parseInt(strCodLocAut), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                        }
                    }
                }

                txtFecDoc.setEnabled(true);

                objAsiDia.setEditable(true);
                //Inicializar las variables que indican cambios.
                objTblMod.setDataModelChanged(false);
                objAsiDia.setDiarioModificado(false);
                blnHayCam = false;
                objTooBar.setEnabledVistaPreliminar(true);
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

            try {
                if (!eliminarReg()) {
                    return false;
                }
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast()) {
                    rstCab.next();
                    cargarReg();
                } else {
                    objTooBar.setEstadoRegistro("Eliminado");
                    clnTextos();
                }
                blnHayCam = false;
            } catch (java.sql.SQLException e) {
                return true;
            }
            return true;
        }

        /**
         * Esta función elimina el registro de la base de datos.
         *
         * @return true: Si se pudo eliminar el registro.
         * <BR>false: En el caso contrario.
         */
        private boolean eliminarReg() {
            boolean blnRes = false;
            try {
                con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                con.setAutoCommit(false);
                if (con != null) {
                    if (eliminarDet()) {
                        if (eliminarCab()) {

                            if (actualizarTbmIngEgrMenTra()) {

                                if (objAsiDia.eliminarDiario(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()))) {
                                    con.commit();
                                    blnRes = true;
                                } else {
                                    con.rollback();
                                }

                            } else {
                                con.rollback();
                            }
                        } else {
                            con.rollback();
                        }
                    } else {
                        con.rollback();
                    }
                }
                con.close();
                con = null;
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
        }

        /**
         * Esta función permite eliminar la cabecera de un registro.
         *
         * @return true: Si se pudo eliminar la cabecera del registro.
         * <BR>false: En el caso contrario.
         */
        private boolean eliminarCab() {
            boolean blnRes = true;
//        int intNePer=-1;
//
//        try
//        {
//            if (con!=null)
//            {
//                stm=con.createStatement();
//                
//                if(cboPer.getSelectedItem().toString().equals("Primera quincena")){
//                    intNePer=1;
//                }else if(cboPer.getSelectedItem().toString().equals("Fin de mes")){
//                    intNePer=2;
//                }
//                
//                //Obtener la fecha del servidor.
//                datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
//                if (datFecAux==null)
//                    return false;
//                //Armar la sentencia SQL.
//                strSQL="";
//                strSQL+="DELETE from tbm_cabRolPag";
//                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
//                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
//                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
//                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
//                stm.executeUpdate(strSQL);
//                stm.close();
//                stm=null;
//                datFecAux=null;
//            }
//        }
//        catch (java.sql.SQLException e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
            return blnRes;
        }

        /**
         * Esta función permite eliminar la cabecera de un registro.
         *
         * @return true: Si se pudo eliminar la cabecera del registro.
         * <BR>false: En el caso contrario.
         */
        private boolean eliminarDet() {
            boolean blnRes = true;

            try {
                if (con != null) {
                    stm = con.createStatement();

                    //Obtener la fecha del servidor.
                    datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux == null) {
                        return false;
                    }
                    //Armar la sentencia SQL.
                    strSQL = "";
                    strSQL += "DELETE from tbm_detRolPag";
                    strSQL += " WHERE co_emp=" + rstCab.getString("co_emp");
                    strSQL += " AND co_loc=" + rstCab.getString("co_loc");
                    strSQL += " AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                    strSQL += " AND co_doc=" + rstCab.getString("co_doc");
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm = null;
                    datFecAux = null;
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
         * Esta función determina si los campos son válidos.
         *
         * @return true: Si los campos son válidos.
         * <BR>false: En el caso contrario.
         */
        private boolean validaCampos() {
            //Validar el "Tipo de documento".
            if (txtCodTipDoc.getText().equals("")) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                txtDesCorTipDoc.requestFocus();
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

            //Validar el "Año".
            int intPerAAAA = cboPerAAAA.getSelectedIndex();
            if (intPerAAAA <= 0) {
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Año</FONT> es obligatorio.<BR>Seleccione el año y vuelva a intentarlo.</HTML>");
                cboPerAAAA.requestFocus();
                return false;
            }

            //Validar sectoriales de los empleados
            for (int intFil = 0; intFil < objTblMod.getRowCount(); intFil++) {
                //INT_TBL_CODCARCOMSEC
                Object objCodCarComSec = objTblMod.getValueAt(intFil, INT_TBL_CODCARCOMSEC);
                if (objCodCarComSec == null) {
                    tabGen.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El empleado <FONT COLOR=\"blue\">" + objTblMod.getValueAt(intFil, INT_TBL_NOMTRA).toString() + "</FONT> no tiene asignada una <FONT COLOR=\"blue\">Comisión Sectorial</FONT>.<BR>Comunicarse con el Departamento de Recursos Humanos.</HTML>");
                    return false;
                } else {
                    String strCodCarComSec = objCodCarComSec.toString();
                    if (strCodCarComSec.compareTo("") == 0) {
                        tabGen.setSelectedIndex(0);
                        mostrarMsgInf("<HTML>El empleado <FONT COLOR=\"blue\">" + objTblMod.getValueAt(intFil, INT_TBL_NOMTRA).toString() + "</FONT> no tiene asignada una <FONT COLOR=\"blue\">Comisión Sectorial</FONT>.<BR>Comunicarse con el Departamento de Recursos Humanos.</HTML>");
                        return false;
                    }
                }
            }

            return true;
        }

        public boolean insertar() {
            boolean blnRes = false;

            try {
                if (validaCampos()) {
//                 if(!rolPerExiste()){
                    if (insertarReg()) {
                        blnRes = true;
                    }
//                 }
//                 else{
//                     mostrarMsgInf("<HTML><FONT COLOR=\"blue\">Décimo Tercer Sueldo</FONT> que se desea guardar es de un Período pasado.<BR></HTML>");
//                     return false;
//                 }
                }
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean rolPerExiste() {
            boolean blnRes = false;
            java.sql.Connection con = null;
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;
            String strSQL = "";

            try {

                con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (con != null) {

                    con.setAutoCommit(false);
                    stmLoc = con.createStatement();
                    String strReg = null;

                    strSQL = "select * from tbm_cabrolpag \n"
                            + "where co_emp = " + objZafParSis.getCodigoEmpresa() + "\n"
                            + "and co_loc = " + objZafParSis.getCodigoLocal() + "\n"
                            + "and co_tipdoc = " + txtCodTipDoc.getText() + "\n"
                            + "and ne_ani = " + cboPerAAAA.getSelectedItem().toString() + "\n";

                    rstLoc = stmLoc.executeQuery(strSQL);
                    if (rstLoc.next()) {
                        blnRes = true;
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
                    con.close();
                } catch (Throwable ignore) {
                }
            }
            return blnRes;

        }

        public boolean insertarReg() {
            boolean blnRes = false;
            java.sql.Connection con = null;
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;
            String strSQL = "";
            int intCodDocDiaRol = 0;
            int intNumDocDiaRol = 0;
//        int intCoDocDiaProv=0;

            try {
                con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (con != null) {
                    con.setAutoCommit(false);

                    stmLoc = con.createStatement();
//
                    strSQL = "SELECT case when (Max(co_doc)+1) is null then 1 else Max(co_doc)+1 end as co_doc  FROM tbm_cabrolpag WHERE "
                            + " co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " and co_tipDoc = " + txtCodTipDoc.getText();
                    rstLoc = stmLoc.executeQuery(strSQL);
                    if (rstLoc.next()) {
                        intCodDocDiaRol = rstLoc.getInt("co_doc");
                    }
                    rstLoc.close();
                    rstLoc = null;
//
                    strSQL = "SELECT CASE WHEN (ne_ultdoc+1) is null THEN 1 ELSE ne_ultdoc+1 END AS numDoc FROM tbm_cabtipdoc "
                            + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + objZafParSis.getCodigoLocal() + " and co_tipdoc=" + txtCodTipDoc.getText() + " ";
                    rstLoc = stmLoc.executeQuery(strSQL);
                    if (rstLoc.next()) {
                        intNumDocDiaRol = rstLoc.getInt("numDoc");
                    }
                    rstLoc.close();
                    rstLoc = null;
                    intCodDocDiaRol = intNumDocDiaRol;

                    int Fecha[] = txtFecDoc.getFecha(txtFecDoc.getText());
                    String strFecha = "#" + Fecha[2] + "/" + Fecha[1] + "/" + Fecha[0] + "#";
                    String FecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), "yyyy/MM/dd");

                    if (insertarCabReg(con, intCodDocDiaRol, intCodDocDiaRol, strFecha, intCodLoc, intCodTipDocProv, txtNumDoc.getText())) {
                        if (insertarDetReg(con, intCodDocDiaRol, intCodDocDiaRol, strFecha)) {
                            if (saldarDCSValPen(con)) {
                                if (objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), String.valueOf(intCodDocDiaRol), objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy"), "0")) {
                                    con.commit();
                                    blnRes = true;
                                    txtCodDoc.setText("" + intCodDocDiaRol);
                                    txtNumDoc.setText("" + intCodDocDiaRol);
                                } else {
                                    con.rollback();
                                }
                            } else {
                                con.rollback();
                            }
                        } else {
                            con.rollback();
                        }
                    } else {
                        con.rollback();
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
                    con.close();
                } catch (Throwable ignore) {
                }
            }
            return blnRes;
        }

        private boolean saldarDCSValPen(Connection con) {
            boolean blnRes = true;
            java.sql.Statement stmLoc = null;
            java.sql.Statement stmLocAux = null;
            java.sql.Statement stmLocUpd = null;
            java.sql.ResultSet rstLoc = null;
            java.sql.ResultSet rstLocAux = null;
            String strSql = "";

            try {

                if (con != null) {

                    con.setAutoCommit(false);
                    stmLoc = con.createStatement();
                    stmLocAux = con.createStatement();
                    stmLocUpd = con.createStatement();
                    int intNeTipPro = 0;
                    if (objZafParSis.getCodigoMenu() == 3643 || objZafParSis.getCodigoMenu() == 3653) {
                        intNeTipPro = 1;
                    } else if (objZafParSis.getCodigoMenu() == 3782 || objZafParSis.getCodigoMenu() == 3792) {
                        intNeTipPro = 2;
                    } else if (objZafParSis.getCodigoMenu() == 3800 || objZafParSis.getCodigoMenu() == 3810) {
                        intNeTipPro = 3;
                    }

                    Calendar cal = Calendar.getInstance();
                    String strFecDes = "";
                    String strFecHas = "";
                    String strFilPer = "";

                    if (intNeTipPro == 1) {
                        cal.set(Calendar.YEAR, (Integer.parseInt(cboPerAAAA.getSelectedItem().toString()) - 1));
                        cal.set(Calendar.MONTH, 11);
                        strFecDes = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.getActualMinimum(Calendar.DATE);

                        cal.set(Calendar.YEAR, (Integer.parseInt(cboPerAAAA.getSelectedItem().toString())));
                        cal.set(Calendar.MONTH, 10);
                        strFecHas = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.getActualMaximum(Calendar.DATE);
                        strFilPer += "AND ((a.ne_ani in (" + (Integer.parseInt(cboPerAAAA.getSelectedItem().toString()) - 1) + ") AND a.ne_mes in (12))" + " \n";
                        strFilPer += "OR (a.ne_ani in (" + (Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) + ") AND a.ne_mes in (1,2,3,4,5,6,7,8,9,10,11)))" + " \n";
                    } else {
                        cal.set(Calendar.YEAR, (Integer.parseInt(cboPerAAAA.getSelectedItem().toString())));
                        cal.set(Calendar.MONTH, 0);
                        strFecDes = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.getActualMinimum(Calendar.DATE);

                        cal.set(Calendar.YEAR, (Integer.parseInt(cboPerAAAA.getSelectedItem().toString())));
                        cal.set(Calendar.MONTH, 11);
                        strFecHas = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.getActualMaximum(Calendar.DATE);
                        strFilPer += "AND  (  ((a.ne_ani in (" + (Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) + ") AND a.ne_mes in (1,2,3,4,5,6,7,8,9,10,11,12))) ) \n ";
                    }

                    String strEmp = "";
                    if (objZafParSis.getCodigoEmpresa() != objZafParSis.getCodigoEmpresaGrupo()) {
                        strEmp = " and a1.co_emp = " + objZafParSis.getCodigoEmpresa();
                    }

                    strSql = "";
                    strSql += " SELECT distinct a.co_emp, a.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado, a.co_emp, c.tx_nom as empresa, a1.co_ofi, \n ";
                    strSql += "d.tx_nom as oficina, a1.fe_inicon, a1.fe_reafincon, a1.st_consub , ((current_date-a1.fe_inicon)/360) as ne_numAnioLab , extract (MONTH from a1.fe_inicon) as ne_mesEnt, \n ";
                    strSql += "extract (YEAR from a1.fe_inicon) as ne_aniEnt, (select count(*) from tbm_traemp  a where a.co_tra=a1.co_tra and a.st_reg='A') as traActEmp , \n";
                    strSql += "(select sum((case(nd_valdectersue is null) when true then 0 else nd_valdectersue end ) -(case(nd_valpagdectersue is null) when true then 0 else nd_valpagdectersue end ) ) as valDecTerSue from tbm_datgeningegrmentra a  \n ";
                    strSql += "left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro=" + intNeTipPro + ") \n ";
                    strSql += "where a.co_emp = a1.co_emp \n ";
                    strSql += "and a.co_tra =a1.co_tra \n ";
//                strSql+="AND  ( ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1)+") AND a.ne_mes in (12))) \n ";
//                strSql+="OR ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString()))+") AND a.ne_mes in (1,2,3,4,5,6,7,8,9,10,11))) )) as nd_valdectersue  \n ";
                    strSql += strFilPer + " ) as nd_valdectersue \n";
                    strSql += "from tbm_bensocmentra a \n";
                    strSql += "LEFT OUTER JOIN tbm_traemp a1 on (a1.co_emp = a.co_emp and a1.co_tra = a.co_tra) \n";
                    strSql += "LEFT OUTER JOIN tbm_tra b on (b.co_tra=a.co_tra) \n";
                    strSql += "LEFT OUTER JOIN tbm_emp c on (c.co_emp=a.co_emp) \n";
                    strSql += "LEFT OUTER JOIN tbm_ofi d on (d.co_ofi=a1.co_ofi) \n";
                    strSql += "LEFT OUTER JOIN tbm_carlab e on (e.co_car=a1.co_car) \n";
                    strSql += "WHERE ((a1.fe_inicon<=" + objUti.codificar(strFecDes) + " and a1.st_reg = 'A') or (a1.fe_inicon is null and a1.st_reg='A') or (a1.fe_inicon between " + objUti.codificar(strFecDes) + " and " + objUti.codificar(strFecHas) + ")) \n";
                    strSql += " " + strEmp + "  \n";
                    strSql += "ORDER BY a.co_emp, empleado";

                    rstLocAux = stmLocAux.executeQuery(strSql);

                    while (rstLocAux.next()) {

                        boolean bln = false;
                        System.out.println("EMPLEADO: " + rstLocAux.getString("empleado"));

                        if (rstLocAux.getDouble("nd_valdectersue") > 0) {

                            String strEmpAux = "";
                            if (rstLocAux.getString("fe_inicon") == null) {
                                strEmpAux = " and a.co_emp = " + rstLocAux.getInt("co_emp") + "\n";
                                bln = true;
                            }

                            if (rstLocAux.getInt("traActEmp") > 1) {
                                strEmpAux = " and a.co_emp = " + rstLocAux.getInt("co_emp") + "\n";
                                bln = true;
                            }

//                        strSql="";
//                        strSql="select *  from ( " + "\n";
//                        strSql+="select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue , b.nd_valdectersue , b.nd_valpagdectersue from tbm_datgeningegrmentra a " + "\n";
//                        strSql+="left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro="+intNeTipPro+")" + "\n";
//                        strSql+="where a.co_tra = "+rstLocAux.getString("co_tra") + "\n";
//                        strSql+=strEmpAux;
//                        strSql+="AND ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1) +") AND a.ne_mes in (12)))"  + " \n"; 
//                        strSql+="UNION"  + " \n"; 
//                        strSql+="select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue , b.nd_valdectersue , b.nd_valpagdectersue from tbm_datgeningegrmentra a " + "\n";
//                        strSql+="left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro="+intNeTipPro+")"  + " \n"; 
//                        strSql+="where a.co_tra = "+rstLocAux.getString("co_tra")  + "\n";
//                        strSql+=strEmpAux;
//                        strSql+="AND ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) +") AND a.ne_mes in (1,2,3,4,5,6,7,8,9,10,11)))"  + " \n"; 
//                        strSql+=") as x"  + "\n";
//                        strSql+="ORDER BY x.ne_ani , x.ne_mes asc"  + "\n";
                            strSql = "";
                            strSql = "select * from ( " + "\n";
                            if (intNeTipPro == 1) {
                                strSql += "select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue ,  a.nd_comvenies, a.nd_comvenbon, " + "\n";
                                strSql += "b.nd_valdectersue as nd_valdectersueIESS,b.nd_valpagdectersue  , " + "\n";
                                strSql += "(b.nd_valdectersue +   case (a.nd_comvenies is null) when true then 0 else round((a.nd_comvenies/12),2) end )  as nd_valdectersue , " + "\n";
                                strSql += "(b.nd_valdectersue +   case (a.nd_comvenbon is null) when true then 0 else round((a.nd_comvenbon/12),2) end )  as nd_valdectersueBon, " + "\n";
                                strSql += "round((a.nd_comvenbon/12),2) as decTerSueComBon " + "\n";
                                strSql += "from tbm_datgeningegrmentra a " + "\n";
                                strSql += "left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro=" + intNeTipPro + ")" + "\n";
                                strSql += "where a.co_tra = " + rstLocAux.getString("co_tra") + "\n";
                                strSql += strEmpAux;
                                strSql += "AND ((a.ne_ani in (" + (Integer.parseInt(cboPerAAAA.getSelectedItem().toString()) - 1) + ") AND a.ne_mes in (12)))" + " \n";
                                strSql += "UNION" + " \n";
                                strSql += "select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue ,  a.nd_comvenies, a.nd_comvenbon, " + "\n";
                                strSql += "b.nd_valdectersue as nd_valdectersueIESS,b.nd_valpagdectersue  , (b.nd_valdectersue +   case (a.nd_comvenies is null) when true then 0 else round((a.nd_comvenies/12),2) end )  as nd_valdectersue , " + "\n";
                                strSql += "(b.nd_valdectersue +   case (a.nd_comvenbon is null) when true then 0 else round((a.nd_comvenbon/12),2) end )  as nd_valdectersueBon, " + "\n";
                                strSql += "round((a.nd_comvenbon/12),2) as decTerSueComBon " + "\n";
                                strSql += "from tbm_datgeningegrmentra a " + "\n";
                                strSql += "left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro=" + intNeTipPro + ")" + "\n";
                                strSql += "where a.co_tra = " + rstLocAux.getString("co_tra") + "\n";
                                strSql += strEmpAux;
                                strSql += "AND ((a.ne_ani in (" + (Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) + ") AND a.ne_mes in (1,2,3,4,5,6,7,8,9,10,11)))" + " \n";
                                strSql += ") as x" + "\n";
                                strSql += "ORDER BY x.ne_ani , x.ne_mes asc" + "\n";
                            } else {
                                strSql += "select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue ,  a.nd_comvenies, a.nd_comvenbon, " + "\n";
                                strSql += "b.nd_valdectersue as nd_valdectersueIESS,b.nd_valpagdectersue  , " + "\n";
                                strSql += "(b.nd_valdectersue +   case (a.nd_comvenies is null) when true then 0 else round((a.nd_comvenies/12),2) end )  as nd_valdectersue , " + "\n";
                                strSql += "(b.nd_valdectersue +   case (a.nd_comvenbon is null) when true then 0 else round((a.nd_comvenbon/12),2) end )  as nd_valdectersueBon, " + "\n";
                                strSql += "round((a.nd_comvenbon/12),2) as decTerSueComBon " + "\n";
                                strSql += "from tbm_datgeningegrmentra a " + "\n";
                                strSql += "left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro=" + intNeTipPro + ")" + "\n";
                                if (intNeTipPro == 6) {
                                    strSql += "left outer join tbm_ingegrmenprgtra c on (c.co_emp=a.co_emp and c.co_tra=a.co_tra and c.ne_ani=a.ne_ani and c.ne_mes=a.ne_mes and c.co_rub=6) " + "\n";
                                } else {
                                    strSql += "left outer join tbm_ingegrmenprgtra c on (c.co_emp=a.co_emp and c.co_tra=a.co_tra and c.ne_ani=a.ne_ani and c.ne_mes=a.ne_mes and c.co_rub=7) " + "\n";
                                }

                                strSql += "where a.co_tra = " + rstLocAux.getString("co_tra") + "\n";
                                strSql += strEmpAux;
                                strSql += "AND ((a.ne_ani in (" + (Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) + ") AND a.ne_mes in (1,2,3,4,5,6,7,8,9,10,11,12)))" + " \n";
                                strSql += ") as x" + "\n";
                                strSql += "ORDER BY x.ne_ani , x.ne_mes asc" + "\n";
                            }

                            System.out.println("q consulTra: " + strSql);

                            rstLoc = stmLoc.executeQuery(strSql);

                            while (rstLoc.next()) {

                                strSql = "";
                                if (intNeTipPro == 1) {
                                    strSql += "UPDATE tbm_bensocmentra set nd_valpagdectersue = " + rstLoc.getDouble("nd_valdectersue") + " \n";
                                } else if (intNeTipPro == 2) {
                                    strSql += "UPDATE tbm_bensocmentra set nd_valpagdectersue = " + rstLoc.getDouble("nd_valdectersueBon") + " \n";
                                } else if (intNeTipPro == 3) {
                                    strSql += "UPDATE tbm_bensocmentra set nd_valpagdectersue = " + rstLoc.getDouble("nd_valdectersueIESS") + " \n";
                                }

                                strSql += "WHERE co_tra = " + rstLoc.getString("co_tra").toString() + " \n";
                                if (bln) {
                                    strSql += "AND co_emp = " + rstLoc.getString("co_emp").toString() + " \n";
                                }
                                strSql += "AND ne_ani = " + rstLoc.getString("ne_ani").toString() + " \n";
                                strSql += "AND ne_mes = " + rstLoc.getString("ne_mes").toString() + " \n";
                                strSql += "AND ne_tippro = " + intNeTipPro + " \n";
                                System.out.print("updateTbmbensocmentra: " + strSql);
                                stmLocUpd.executeUpdate(strSql);
                            }
                        }
                    }
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                Evt.printStackTrace();
                objUti.mostrarMsgErr_F1(this, Evt);
            } finally {
                try {
                    stmLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                    rstLoc.close();
                } catch (Throwable ignore) {
                }
            }
            return blnRes;
        }

        public boolean insertarCabReg(java.sql.Connection con, int intCodDoc, int intNumDoc, String strFecha, int local, int tipoDocumento, String numeroDiario) {
            boolean blnRes = false;
            String strFecSis = "";
            String strSql = "";
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;
//
            try {
                if (con != null) {
                    stmLoc = con.createStatement();
//              
                    strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());

                    int intAAAAPer = Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
                    String strStImp = "N";
                    String strStReg = "O";

                    strSql = "INSERT INTO tbm_cabrolpag(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, nd_valdoc, co_cta, ne_ani, ne_mes, ne_per, tx_reg , st_imp, tx_obs1,";
                    strSql += " tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, co_locRelProBenSol, co_tipDocRelProBenSol, co_docRelProBenSol, ";
                    strSql += " st_autRecHum, fe_autRecHum, co_usrAutRecHum, tx_comAutRecHum, st_autGer, fe_autGer, co_usrAutGer, tx_comAutGer, ";
                    strSql += " fe_genArcMRL, co_usrGenArcMRL, tx_comGenArcMRL, ";
                    strSql += " fe_genArcBan, co_usrGenArcBan, tx_comGenArcBan) VALUES (";
                    strSql += " " + objZafParSis.getCodigoEmpresa() + ",";
                    strSql += " " + objZafParSis.getCodigoLocal() + ",";
                    strSql += " " + txtCodTipDoc.getText() + ",";
                    strSql += " " + intCodDoc + ",";
                    strSql += " '" + strFecha + "',";
                    strSql += " " + intCodDoc + ",";
                    strSql += " " + objUti.redondear(objUti.parseDouble(txtValDoc.getText()), objZafParSis.getDecimalesMostrar()) + ",";
                    strSql += " " + txtCodCta.getText() + ",";
                    strSql += " " + intAAAAPer + ",";
                    strSql += "  NULL , ";
                    strSql += "  NULL , ";
//              strSql+= "  " + strReg +" , ";
                    strSql += "  NULL , ";
                    strSql += " " + objUti.codificar(strStImp) + ",";
                    strSql += " " + objUti.codificar(txtObs1.getText()) + ",";
                    strSql += " " + objUti.codificar(txtObs2.getText()) + ",";
                    strSql += " " + objUti.codificar(strStReg) + ",";
                    strSql += " " + "current_timestamp" + ",";
                    strSql += " " + "null" + ",";
                    strSql += " " + objZafParSis.getCodigoUsuario() + ",";//co_usring
                    strSql += " " + "null" + " , ";//co_usrmod
                    strSql += " " + "null" + " , ";//co_locRelProBenSol
                    strSql += " " + "null" + " , ";//co_tipDocRelProBenSol
                    strSql += " " + "null" + " , ";//co_docRelProBenSol

                    strSql += " " + "null" + " , ";//st_autRecHum
                    strSql += " " + "null" + " , ";//fe_autRecHum
                    strSql += " " + "null" + " , ";//co_usrAutRecHum
                    strSql += " " + "null" + " , ";//tx_comAutRecHum

                    strSql += " " + "null" + " , ";//st_autGer
                    strSql += " " + "null" + " , ";//st_autGer
                    strSql += " " + "null" + " , ";//st_autGer
                    strSql += " " + "null" + " , ";//tx_comAutGer

                    strSql += " " + "null" + " , ";//fe_genArchMRL
                    strSql += " " + "null" + " , ";//co_usrGenArchMRL
                    strSql += " " + "null" + " , ";//tx_comGenArcMRL
                    strSql += " " + "null" + " , ";//fe_genArcBan

                    strSql += " " + "null" + " , ";//co_usrGenArcBan
                    strSql += " " + "null";//tx_comGenArcBan

                    strSql += ")";

                    System.out.println("query insert cabecera rol pagos: " + strSql);
                    stmLoc.executeUpdate(strSql);
                    blnRes = true;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } finally {
                try {
                    stmLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                    rstLoc.close();
                } catch (Throwable ignore) {
                }
            }
            return blnRes;
        }

        public boolean insertarDetReg(java.sql.Connection con, int intCodDoc, int intNumDoc, String strFecha) {

            boolean blnRes = true;
            String strSql = "";
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;

            java.sql.Statement stmLocAux = null;
            java.sql.ResultSet rstLocAux = null;

            try {
                if (con != null) {

                    stmLoc = con.createStatement();
                    stmLocAux = con.createStatement();

                    /*INSERT EN EL DETALLE*/
                    for (int i = 0; i < tblDat.getRowCount(); i++) {

                        strSql = "INSERT INTO tbm_detrolpag(";
                        strSql += " co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_tra, co_rub, nd_valrub, ";
                        strSql += " tx_obs1, co_ofi, co_car, tx_codComSec, tx_nomCarComSec, nd_minSec, ne_numTotDiaLab ) VALUES (";
                        strSql += " " + objZafParSis.getCodigoEmpresa() + ",";
                        strSql += " " + objZafParSis.getCodigoLocal() + ",";
                        strSql += " " + txtCodTipDoc.getText() + ",";
                        strSql += " " + intCodDoc + ",";
                        strSql += " " + i + ",";
                        strSql += " " + objTblMod.getValueAt(i, INT_TBL_CODTRA).toString() + ",";
                        strSql += " NULL ,";

                        Object obj = objTblMod.getValueAt(i, (tblDat.getColumnCount() - 3));
                        String strNdValRub = "";
                        if (obj == null) {
                            strNdValRub = null;
                        } else {
                            strNdValRub = objTblMod.getValueAt(i, (tblDat.getColumnCount() - 3)).toString();
                            if (strNdValRub.compareTo("0.0") == 0) {
                                strNdValRub = "NULL";
                            }
                        }

                        strSql += " " + strNdValRub + " , ";// + ");";
                        strSql += " " + objUti.codificar(objTblMod.getValueAt(i, (tblDat.getColumnCount() - 2))) + " , ";//tx_obs1

                        String strSqlAux = "select distinct co_ofi from tbm_traemp where co_emp = " + objZafParSis.getCodigoEmpresa();
                        strSqlAux += " and co_tra = " + objTblMod.getValueAt(i, INT_TBL_CODTRA).toString();
                        rstLocAux = stmLocAux.executeQuery(strSqlAux);
                        if (rstLocAux.next()) {
                            strSql += " " + rstLocAux.getString("co_ofi") + " , ";//co_ofi
                        } else {
                            strSql += " NULL ,";//co_ofi
                        }

                        strSql += " " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_CODCAR)) + " , ";//co_car
                        String strTxCodCarComSec = objUti.codificar(objTblMod.getValueAt(i, INT_TBL_CODCARCOMSEC));
                        if (strTxCodCarComSec == null) {
                            strSql += " NULL ,";//tx_codComSec
                        } else {
                            strSql += " " + strTxCodCarComSec + " , ";//tx_codComSec
                        }

                        String strTxNomCarComSec = objUti.codificar(objTblMod.getValueAt(i, INT_TBL_NOMCARCOMSEC));
                        if (strTxNomCarComSec == null) {
                            strSql += " NULL ,";//tx_nomCarComSec
                        } else {
                            strSql += " " + strTxNomCarComSec + " , ";;//tx_nomCarComSec
                        }

                        String strNdMinSec = objUti.codificar(objTblMod.getValueAt(i, INT_TBL_MINSEC));
                        if (strNdMinSec == null) {
                            strSql += " NULL ,";//nd_minSec
                        } else {
                            strSql += " " + strNdMinSec + " , ";;//nd_minSec
                        }

                        String strNeNumTotDiaLab = objUti.codificar(objTblMod.getValueAt(i, (tblDat.getColumnCount() - 4)));
                        if (strNeNumTotDiaLab == null) {
                            strSql += " NULL ";//ne_numTotDiaLab
                        } else {
                            strSql += " " + strNeNumTotDiaLab;//ne_numTotDiaLab
                        }

//                String strTxObs1=objUti.codificar(objTblMod.getValueAt(i, (tblDat.getColumnCount()-2)));
//                if(strTxObs1==null){
//                    strSql+= " NULL ";//tx_obs1
//                }else{
//                    strSql+= " " +strTxNomCarComSec;//tx_obs1
//                }
                        strSql += ");";

                        System.out.println("query insert detalle rol pagos: " + strSql);
                        stmLoc.executeUpdate(strSql);
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
                    stmLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                    rstLoc.close();
                } catch (Throwable ignore) {
                }
            }
            return blnRes;
        }

        public boolean actualizarStRolPagGen(java.sql.Connection con) {
            boolean blnRes = false;
//    String strSql="";
//    java.sql.Statement stmLoc = null;
//    java.sql.ResultSet rstLoc = null;
//
//    try{
//        if(con!=null){
//            
//            stmLoc=con.createStatement();
//            String strCoGrp="";
//            if(txtCodTipDoc.getText().compareTo("192")==0){
//                strCoGrp="1";
//            }else if(txtCodTipDoc.getText().compareTo("199")==0){
//                strCoGrp="2";
//            }else{
//                strCoGrp="3";
//            }
//            
//            strSql="update tbm_ingegrmentra set st_rolpaggen='S'";
//            strSql+=" where co_emp = " + objZafParSis.getCodigoEmpresa();
//            strSql+=" and ne_ani = " + Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
//            strSql+=" and ne_mes = " + cboPerMes.getSelectedIndex();
//            strSql+=" and ne_per = " + cboPer.getSelectedIndex();
//            strSql+=" and co_rub in (select distinct co_rub from tbm_rubrolpag where co_grp=" + strCoGrp+")";
//            System.out.println("query actualizacion st_rolpaggen rol pagos: " + strSql);
//            stmLoc.executeUpdate(strSql);
//            blnRes=true;
//        }
//    }catch(java.sql.SQLException Evt){ 
//        Evt.printStackTrace();
//        blnRes=false;  
//        objUti.mostrarMsgErr_F1(this, Evt); 
//    }catch(Exception Evt){ 
//        Evt.printStackTrace();
//        blnRes=false;  
//        objUti.mostrarMsgErr_F1(this, Evt); 
//    }finally {
//        try{stmLoc.close();}catch(Throwable ignore){}
//        try{rstLoc.close();}catch(Throwable ignore){}
//    }
            return blnRes;
        }

        public boolean modificar() {

            boolean blnRes = true;
//        generaAsiento();
//        boolean blnProv=false;
//        if(cboPer.getSelectedIndex()==2){
//            blnProv=generaAsientoProvisiones();
//        }
//        objAsiDia.setEditable(true);
            return blnRes;

        }

        public boolean modificarCab(java.sql.Connection conn, int intCodDoc) {
            boolean blnRes = false;

            return blnRes;
        }

        private void noEditable(boolean blnEst) {
            txtObs1.setEditable(false);
            txtObs2.setEditable(false);

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
            /*
             * Esto Hace en caso de que el modo de operacion sea Consulta
             */
//       return _consultar(FilSql());
            consultarReg();
            return true;
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

        public void clickModificar() {
    //  setEditable(true);
            //
            //  java.awt.Color colBack;
            //  colBack = txtValDoc.getBackground();
            //
            // this.setEnabledConsultar(false);
            //
            // objTblMod.setDataModelChanged(false);
            // blnHayCam=false;
            modificar();
        }

        public boolean vistaPreliminar() {
            if (objThrGUI == null) {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;
        }

        public boolean imprimir() {
            if (objThrGUI == null) {

                objThrGUI = new ZafThreadGUI();
                objThrGUI.setIndFunEje(0);
                objThrGUI.start();
            }
            return true;
        }

        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickCancelar() {
            clnTextos();
//        cboPerAAAA.setSelectedIndex(cboPerAAAA.getItemCount()-1);
//        configurarVenConTipDoc();
//       mostrarTipDocPre();
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

    /**
     * Esta función permite consultar los registros de acuerdo al criterio
     * seleccionado.
     *
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg() {
        boolean blnRes = true;
        String strSQL = "";
        try {
            conCab = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conCab != null) {
                stmCab = conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que sólo se muestre los documentos asignados al programa.
//                if (txtDesCorTipDoc.getText().equals(""))
//                {
                if (!blnEstCarSolHSE) {

                    strSQL += "select * from tbm_cabRolPag a1";
                    strSQL += " INNER JOIN tbm_cabTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                    strSQL += " LEFT OUTER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
                    strSQL += " WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                    strSQL += " AND a1.co_loc=" + objZafParSis.getCodigoLocal();
                    strSQL += " AND a5.co_mnu=" + objZafParSis.getCodigoMenu();
                    strSQL += " AND a1.st_reg<>'E'";
                    //                }

                    strAux = txtCodTipDoc.getText();
                    if (!strAux.equals("")) {
                        strSQL += " AND a1.co_tipdoc=" + strAux;
                    }

                    if (txtFecDoc.isFecha()) {
                        strSQL += " AND a1.fe_doc='" + objUti.formatearFecha(txtFecDoc.getText(), "dd/MM/yyyy", objZafParSis.getFormatoFechaBaseDatos()) + "'";
                    }

                    strAux = txtCodDoc.getText();
                    if (!strAux.equals("")) {
                        strSQL += " AND a1.co_doc=" + strAux;
                    }

                    if (cboPerAAAA.getSelectedIndex() > 0) {
                        strSQL += " AND a1.ne_ani=" + cboPerAAAA.getSelectedItem();
                    }

                } else {
                    strSQL += "select * from tbm_cabRolPag a1";
                    strSQL += " INNER JOIN tbm_cabTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                    strSQL += " LEFT OUTER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
                    strSQL += " WHERE a1.co_emp=" + strCodEmpAut;
                    strSQL += " AND a1.co_loc=" + strCodLocAut;
                    strSQL += " AND a1.co_tipdoc = " + strCodTipDocAut;
                    strSQL += " AND a1.ne_numdoc = " + strNumDoc;
                    strSQL += " AND a1.st_reg<>'E'";
                }

                strSQL += " ORDER BY a1.co_emp, a1.co_doc";
                System.out.println("query consulta: " + strSQL);
                rstCab = stmCab.executeQuery(strSQL);
                if (rstCab.next()) {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                } else {
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    clnTextos();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
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

    /**
     * Esta función permite cargar el registro seleccionado.
     *
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg() {
        boolean blnRes = true;
        try {
            if (cargarCabReg()) {
                if (cargarDetReg()) {
                    System.out.println("co_tipdoc ----> " + Integer.parseInt(txtCodTipDoc.getText()));
                    System.out.println("co_doc ----> " + Integer.parseInt(txtCodDoc.getText()));
                    objAsiDia.consultarDiario(intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                }
            }
            objAsiDia.setDiarioModificado(false);
            blnHayCam = false;
        } catch (Exception e) {
            e.printStackTrace();
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
        java.util.Date datFecDoc;
        java.util.Date datPriCuo;

        try {
            con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement();
                strSQL = "";

                strSQL = "select a.*,b.tx_descor as descortipdoc, b.co_tipdoc , b.tx_deslar as deslartipdoc,c.co_cta, c.tx_codCta as codcta,c.tx_deslar as txdeslarcta  from tbm_cabRolPag a";
                strSQL += " INNER JOIN tbm_cabTipDoc AS b ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipDoc=b.co_tipDoc";
                strSQL += " LEFT OUTER JOIN tbm_plaCta AS c ON (a.co_emp=c.co_emp AND a.co_cta=c.co_cta)";
                strSQL += " where a.co_emp = " + rstCab.getString("co_emp");
                strSQL += " AND a.co_loc = " + rstCab.getString("co_loc");
                strSQL += " AND a.co_tipdoc = " + rstCab.getString("co_tipdoc");
                strSQL += " AND a.co_doc=" + rstCab.getString("co_doc");

                rst = stm.executeQuery(strSQL);
                if (rst.next()) {

                    strAux = rst.getString("co_tipdoc");
                    txtCodTipDoc.setText((strAux == null) ? "" : strAux);

                    strAux = rst.getString("descortipdoc");
                    txtDesCorTipDoc.setText((strAux == null) ? "" : strAux);

                    strAux = rst.getString("deslartipdoc");
                    txtDesLarTipDoc.setText((strAux == null) ? "" : strAux);

                    strAux = rst.getString("co_cta");
                    txtCodCta.setText((strAux == null) ? "" : strAux);

                    strAux = rst.getString("codcta");
                    txtDesCorCta.setText((strAux == null) ? "" : strAux);

                    strAux = rst.getString("txdeslarcta");
                    txtDesLarCta.setText((strAux == null) ? "" : strAux);

                    datFecDoc = rst.getDate("fe_doc");
                    txtFecDoc.setText(objUti.formatearFecha(datFecDoc, "dd/MM/yyyy"));

                    strAux = rst.getString("co_doc");
                    txtCodDoc.setText((strAux == null) ? "" : strAux);

                    strAux = rst.getString("ne_numdoc");
                    txtNumDoc.setText((strAux == null) ? "" : strAux);

                    double dblNdValDoc = rst.getDouble("nd_valdoc");

                    txtValDoc.setText("" + objUti.redondear(dblNdValDoc, objZafParSis.getDecimalesMostrar()));

                    if (blnEstCarSolHSE) {
                        cboPerAAAA.addItem(rst.getString("ne_ani"));
                        cboPerAAAA.setSelectedIndex(1);
                    } else {
                        cboPerAAAA.setSelectedItem(rst.getString("ne_ani"));
                    }

                    strAux = rst.getString("tx_obs1");
                    txtObs1.setText((strAux == null) ? "" : strAux);
                    strAux = rst.getString("tx_obs2");
                    txtObs2.setText((strAux == null) ? "" : strAux);

                    //Mostrar el estado del registro.
                    strAux = rst.getString("st_reg");
                    if (strAux.equals("A")) {
                        strAux = "Activo";
                    } else if (strAux.equals("I")) {
                        strAux = "Anulado";
                    } else {
                        strAux = "Otro";
                    }
                    objTooBar.setEstadoRegistro(strAux);

//                    intCodTipDocProv = rst.getInt("co_tipDocRelProBenSol");
//                    intCodDocProv = rst.getInt("co_docRelProBenSol");
                } else {
                    objTooBar.setEstadoRegistro("Eliminado");
                    clnTextos();
                    blnRes = false;
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst = null;
            stm = null;
            con = null;
            //Mostrar la posición relativa del registro.
            intPosRel = rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);
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
     * Esta función permite cargar el detalle del registro seleccionado.
     *
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg() {

        String strSQL;
        boolean blnRes = true;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        try {

            objTooBar.clickInsertar();

        } //        catch (java.sql.SQLException e)
        //        {
        //            e.printStackTrace();
        //            blnRes=false;
        //            objUti.mostrarMsgErr_F1(this, e);
        //        }
        catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, evt);
        } finally {
            try {
                rst.close();
            } catch (Throwable ignore) {
            }
            try {
                stm.close();
            } catch (Throwable ignore) {
            }
            try {
                con.close();
            } catch (Throwable ignore) {
            }
            try {
                rstLoc.close();
            } catch (Throwable ignore) {
            }
            try {
                stmLoc.close();
            } catch (Throwable ignore) {
            }
        }
        return blnRes;
    }

    /**
     * Esta funcián permite limpiar el formulario.
     *
     * @return true: Si se pudo limpiar la ventana sin ningán problema.
     * <BR>false: En el caso contrario.
     */
    public boolean clnTextos() {

        boolean blnRes = true;
        try {
            strCodTipDoc = "";
            strDesCorTipDoc = "";
            strDesLarTipDoc = "";

            objAsiDia.inicializar();
            if (objAsiDiaProv != null) {
                objAsiDiaProv.inicializar();
            }

            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtFecDoc.setText("");

            txtCodCta.setText("");
            txtDesCorCta.setText("");
            txtDesLarCta.setText("");
            txtNumDoc.setText("");

            txtCodDoc.setText("");
            txtValDoc.setText("");

            txtObs1.setText("");
            txtObs2.setText("");

            cboPerAAAA.setSelectedIndex(0);

            objTblMod.removeAllRows();
            for (int intCol = 0; intCol < tblTot.getColumnCount(); intCol++) {
                objTblTot.setValueAt(null, 0, intCol);
            }

        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
        }
        return blnRes;
    }

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_LINEA:
                    strMsg = "";
                    break;
                case INT_TBL_CODTRA:
                    strMsg = "Código del empleado";
                    break;
                case INT_TBL_NOMTRA:
                    strMsg = "Nombres y apellidos del empleado";
                    break;
                case INT_TBL_FECING:
                    strMsg = "Fecha de ingreso del empleado";
                    break;
                case INT_TBL_CODCAR:
                    strMsg = "Código del cargo laboral";
                    break;
                case INT_TBL_CARGO:
                    strMsg = "Nombre del cargo laboral";
                    break;
                case INT_TBL_CODCARCOMSEC:
                    strMsg = "Código del cargo en la comisión sectorial";
                    break;
                case INT_TBL_NOMCARCOMSEC:
                    strMsg = "Nombre del cargo en la comisión sectorial";
                    break;
                case INT_TBL_MINSEC:
                    strMsg = "Mínimo sectorial";
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
     * ir incrementando un JProgressBar con lo terl el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios terndo ha terminado todo el proceso.
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
     * <LI>1: Impresión directa (Terdro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt) {
//        String strRutRpt="", strNomRpt="", strFecHorSer = "", strCamAudRpt = "";
//        String strNomEmp="", strPago="", strPeriodo="", strImpresión="";
//        int i, intNumTotRpt;
        boolean blnRes = true;
        try {
//            
//            if(cboPer.getSelectedIndex()==1){
//                mostrarMsgInf("<HTML>No se puede realizar impresiones para el Período seleccionado.</HTML>");
//                return false;
//            }else{
//                
//                objRptSis.cargarListadoReportes();
//            objRptSis.setVisible(true);
//            if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
//            {
//
//                intNumTotRpt=objRptSis.getNumeroTotalReportes();
//                for (i=0;i<intNumTotRpt;i++)
//                {
//                    if (objRptSis.isReporteSeleccionado(i))
//                    {
//                        
//                        int intCodRep = Integer.parseInt(objRptSis.getCodigoReporte(i));
//                        
//                        int intAño=Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
//                        int intMes=cboPerMes.getSelectedIndex();
//                        int intPer = cboPerMes.getSelectedIndex();
//                        System.out.println(objRptSis.getCodigoReporte(i));
//                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
//                        {
//                            default:
//
//                            
//                            case 433:
//                                
//                                
//                                strRutRpt=objRptSis.getRutaReporte(i);
//                                strNomRpt=objRptSis.getNombreReporte(i);
//                                strCamAudRpt = "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ";
//                                //Inicializar los parametros que se van a pasar al reporte.
//                                
//                                strNomEmp=objZafParSis.getNombreEmpresa();
//                                strPago=cboPer.getSelectedItem().toString();
//                                strPeriodo=cboPerMes.getSelectedItem().toString() + " " + cboPerAAAA.getSelectedItem().toString();
//                                
//                                //Obtener la fecha y hora del servidor.
//                                datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
//                                if (datFecAux!=null){
//                                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
//                                    datFecAux=null;
//                                }
//                                
////                                strSQL="";
////                                strSQL+="select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_tra, ";
////                                strSQL+=" (b.tx_ape || ' ' || b.tx_nom) as empleado from tbm_detrolpag a";
////                                strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra) ";
////                                strSQL+=" where co_emp="+objZafParSis.getCodigoEmpresa();
////                                strSQL+=" and co_loc="+objZafParSis.getCodigoLocal();
////                                strSQL+=" and co_tipdoc="+txtCodTipDoc.getText();
////                                strSQL+=" and co_doc="+txtCodDoc.getText();
////                                strSQL+=" group by a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc,a.co_tra, empleado";
////                                strSQL+=" order by empleado";
//                                strSQL="";
//                                strSQL+="SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_tra, \n" +
//                                "(b.tx_ape || ' ' || b.tx_nom) as empleado, round(d.nd_valRubSinDes,2) as nd_valIESS, e.ne_numDiaLab, null as strPeriodo from tbm_detrolpag a\n" +
//                                "INNER JOIN tbm_tra b on (a.co_tra=b.co_tra) \n" +
//                                "INNER JOIN tbm_cabrolpag c on (c.co_emp=a.co_emp and c.co_loc=a.co_loc and c.co_tipdoc=a.co_tipdoc and c.co_doc=a.co_doc)\n" +
//                                "INNER JOIN tbm_ingegrmentra d on (d.co_emp=a.co_emp and d.co_tra=a.co_tra and d.ne_ani=c.ne_ani and d.ne_mes=c.ne_mes and d.ne_per=0 and d.co_rub = 1)\n" +
//                                "LEFT OUTER JOIN tbm_datgeningegrmentra e on (e.co_emp=a.co_emp and e.co_tra=a.co_tra and e.ne_ani=c.ne_ani and e.ne_mes=c.ne_mes and e.ne_per=0)\n" +
//                                "WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+"\n" +
//                                "AND a.co_loc="+objZafParSis.getCodigoLocal()+"\n" +
//                                "AND a.co_tipdoc="+txtCodTipDoc.getText()+"\n" +
//                                "AND a.co_doc="+txtCodDoc.getText()+"\n" +
//                                "GROUP BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc,a.co_tra, empleado, d.nd_valRubSinDes, e.ne_numDiaLab\n" +
//                                "ORDER BY empleado";
//                                System.out.println("vista previa SUE: " + strSQL);
//                                break;
//                                
//                            case 434:
//                                
//                                strRutRpt=objRptSis.getRutaReporte(i);
//                                strNomRpt=objRptSis.getNombreReporte(i);
//                                strCamAudRpt = "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ";
//                                //Inicializar los parametros que se van a pasar al reporte.
//                                
//                                strNomEmp=objZafParSis.getNombreEmpresa();
//                                strPago=cboPer.getSelectedItem().toString();
//                                strPeriodo=cboPerMes.getSelectedItem().toString() + " " + cboPerAAAA.getSelectedItem().toString();
//                                
//                                //Obtener la fecha y hora del servidor.
//                                datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
//                                if (datFecAux!=null){
//                                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
//                                    datFecAux=null;
//                                }
//                                
//                                strSQL="";
//                                strSQL+="select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_tra, ";
//                                strSQL+=" (b.tx_ape || ' ' || b.tx_nom) as empleado , null as strPeriodo from tbm_detrolpag a";
//                                strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra) ";
//                                strSQL+=" inner join tbm_rubrolpag c on (c.co_rub=a.co_rub and c.co_grp=2)";
//                                strSQL+=" where a.co_emp="+objZafParSis.getCodigoEmpresa();
//                                strSQL+=" and a.co_loc="+objZafParSis.getCodigoLocal();
//                                strSQL+=" and a.co_tipdoc="+txtCodTipDoc.getText();
//                                strSQL+=" and a.co_doc="+txtCodDoc.getText();
//                                strSQL+=" and a.nd_valrub > 0 and a.nd_valrub is not null";
////                                strSQL+=" and round((select sum(nd_valrub) as nd_valRubIng from tbm_detrolpag x ";
////                                strSQL+=" inner join tbm_rubrolpag y on (y.tx_tiprub='I' and x.co_rub=y.co_rub)";
////                                strSQL+=" where x.co_emp = a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc and x.co_tra=a.co_tra) +  ";
////                                strSQL+=" (select sum(nd_valrub) as nd_valRubIng from tbm_detrolpag x";
////                                strSQL+=" inner join tbm_rubrolpag y on (y.tx_tiprub='E' and x.co_rub=y.co_rub) ";
////                                strSQL+=" where x.co_emp = a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc and x.co_tra=a.co_tra) , 2) >0";
//                                strSQL+=" group by a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc,a.co_tra, empleado";
//                                strSQL+=" order by empleado";
//                                System.out.println("vista previa BON: " + strSQL);
//                                break;
//                            
//                            case 435:
//                                
//                                strRutRpt=objRptSis.getRutaReporte(i);
//                                strNomRpt=objRptSis.getNombreReporte(i);
//                                strCamAudRpt = "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ";
//                                //Inicializar los parametros que se van a pasar al reporte.
//                                strNomEmp=objZafParSis.getNombreEmpresa();
//                                strPago=cboPer.getSelectedItem().toString();
//                                strPeriodo=cboPerMes.getSelectedItem().toString() + " " + cboPerAAAA.getSelectedItem().toString();
//                                
//                                //Obtener la fecha y hora del servidor.
//                                datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
//                                if (datFecAux!=null){
//                                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
//                                    datFecAux=null;
//                                }
//                                
//                                strSQL="";
//                                strSQL+="select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_tra, ";
//                                strSQL+=" (b.tx_ape || ' ' || b.tx_nom) as empleado ,null as strPeriodo from tbm_detrolpag a";
//                                strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra) ";
//                                strSQL+=" inner join tbm_rubrolpag c on (c.co_rub=a.co_rub and c.co_grp=3)";
//                                strSQL+=" where a.co_emp="+objZafParSis.getCodigoEmpresa();
//                                strSQL+=" and a.co_loc="+objZafParSis.getCodigoLocal();
//                                strSQL+=" and a.co_tipdoc="+txtCodTipDoc.getText();
//                                strSQL+=" and a.co_doc="+txtCodDoc.getText();
//                                strSQL+=" and a.nd_valrub > 0 and a.nd_valrub is not null";
////                                strSQL+=" and round((select sum(nd_valrub) as nd_valRubIng from tbm_detrolpag x ";
////                                strSQL+=" inner join tbm_rubrolpag y on (y.tx_tiprub='I' and x.co_rub=y.co_rub)";
////                                strSQL+=" where x.co_emp = a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc and x.co_tra=a.co_tra) +  ";
////                                strSQL+=" (select sum(nd_valrub) as nd_valRubIng from tbm_detrolpag x";
////                                strSQL+=" inner join tbm_rubrolpag y on (y.tx_tiprub='E' and x.co_rub=y.co_rub) ";
////                                strSQL+=" where x.co_emp = a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc and x.co_tra=a.co_tra) , 2) >0";
//                                strSQL+=" group by a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc,a.co_tra, empleado";
//                                strSQL+=" order by empleado";
//                                System.out.println("vista previa MOV: " + strSQL);
//                        }
//                        
//                        
//                        java.util.Map mapPar=new java.util.HashMap();
//                        mapPar.put("strsql", strSQL );
//                        mapPar.put("co_emp", objZafParSis.getCodigoEmpresa() );
//                        mapPar.put("ne_ani", intAño );
//                        mapPar.put("ne_mes", intMes );
//                        mapPar.put("ne_per", intPer );
//                        mapPar.put("empresa", strNomEmp );
//                        mapPar.put("pago", strPago );
//                        mapPar.put("periodo", strPeriodo );
//                        mapPar.put("impresion", strFecHorSer );
//                        mapPar.put("strCamAudRpt", strCamAudRpt);
//                        mapPar.put("SUBREPORT_DIR", strRutRpt);
//
////                      mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
//                        objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
//                        
//                    }
//                }
//            }
//            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    //public boolean cargarRolPer(boolean blnAct){
    public boolean cargarRolPer() {

        boolean blnRes = true;
        java.sql.Connection conn = null;
        java.sql.Statement stmLoc = null;
        java.sql.Statement stmLocAux = null;
        java.sql.ResultSet rstLoc = null;
        java.sql.ResultSet rstLocAux = null;
        String strSql = "";
        Vector vecDataCon;
        try {

            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());

            if (conn != null) {

                stmLoc = conn.createStatement();
                stmLocAux = conn.createStatement();
                vecDataCon = new Vector();

                String strFilEmp = "";
                if (objZafParSis.getCodigoEmpresa() != objZafParSis.getCodigoEmpresaGrupo()) {
                    strFilEmp = " AND a1.co_emp = " + objZafParSis.getCodigoEmpresa();
                }

                String strFecHas = "";
                String strFecDes = "";
                Calendar cal = Calendar.getInstance();

                String strFilPer = "";
//                String strFilBenDes = "";
//                String strFilBenHas = "";
//                String strSqlOfi="";

//                double dblValPenTra=0;
//                double dblValPagTra=0;
                int intNeTipPro = 0;/**/

                if (objZafParSis.getCodigoMenu() == 3643 || objZafParSis.getCodigoMenu() == 3653) {
                    intNeTipPro = 1;
                } else if (objZafParSis.getCodigoMenu() == 3782 || objZafParSis.getCodigoMenu() == 3792) {
                    intNeTipPro = 2;
                } else if (objZafParSis.getCodigoMenu() == 3800 || objZafParSis.getCodigoMenu() == 3810) {
                    intNeTipPro = 3;
                }

                if (intNeTipPro == 1 || intNeTipPro == 3) {
                    cal.set(Calendar.YEAR, (Integer.parseInt(cboPerAAAA.getSelectedItem().toString()) - 1));
                    cal.set(Calendar.MONTH, 11);
                    strFecDes = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.getActualMinimum(Calendar.DATE);

                    cal.set(Calendar.YEAR, (Integer.parseInt(cboPerAAAA.getSelectedItem().toString())));
                    cal.set(Calendar.MONTH, 10);
                    strFecHas = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.getActualMaximum(Calendar.DATE);
                    strFilPer += "AND ((a.ne_ani in (" + (Integer.parseInt(cboPerAAAA.getSelectedItem().toString()) - 1) + ") AND a.ne_mes in (12))" + " \n";
                    strFilPer += "OR (a.ne_ani in (" + (Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) + ") AND a.ne_mes in (1,2,3,4,5,6,7,8,9,10,11)))" + " \n";
                } else {
                    cal.set(Calendar.YEAR, (Integer.parseInt(cboPerAAAA.getSelectedItem().toString())));
                    cal.set(Calendar.MONTH, 0);
                    strFecDes = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.getActualMinimum(Calendar.DATE);

                    cal.set(Calendar.YEAR, (Integer.parseInt(cboPerAAAA.getSelectedItem().toString())));
                    cal.set(Calendar.MONTH, 11);
                    strFecHas = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.getActualMaximum(Calendar.DATE);
                    strFilPer += "AND  (  ((a.ne_ani in (" + (Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) + ") AND a.ne_mes in (1,2,3,4,5,6,7,8,9,10,11,12))) ) \n ";
                }

                strSql = "";
                strSql += " SELECT distinct a.co_emp, a.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado, a.co_emp, c.tx_nom as empresa, a1.co_ofi, \n ";
                strSql += "d.tx_nom as oficina, a1.fe_inicon, e.co_car, e.tx_nomcar, e.co_comsec, e.tx_codcomsec, e.tx_nomcarcomsec, e.nd_minsec, \n";
                strSql += "((current_date-a1.fe_inicon)/360) as ne_numAnioLab , extract (MONTH from a1.fe_inicon) as ne_mesEnt, \n ";
                strSql += "extract (YEAR from a1.fe_inicon) as ne_aniEnt, (select count(*) from tbm_traemp  where co_tra=a1.co_tra and st_reg='A') as traActEmp , \n";
                strSql += "(select sum((case(nd_valdectersue is null) when true then 0 else nd_valdectersue end ) -(case(nd_valpagdectersue is null) when true then 0 else nd_valpagdectersue end ) \n ";

                if (intNeTipPro == 2) {
                    strSql += " + (case (a.nd_comvenbon is null) when true then 0 else round((a.nd_comvenbon/12),2) end )  \n ";
                    strSql += " ) as valDecTerSue from tbm_datgeningegrmentra a  \n ";
                } else {
                    strSql += " ) as valDecTerSue from tbm_datgeningegrmentra a  \n ";
                }

                strSql += "left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro=" + intNeTipPro + ") \n ";
                strSql += "where a.co_emp = a1.co_emp \n ";
                strSql += "and a.co_tra =a1.co_tra  \n";
                strSql += strFilPer + " ) as nd_valdectersue \n";
//                strSql+="AND  ( ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1)+") AND a.ne_mes in (12))) \n ";
//                strSql+="OR ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString()))+") AND a.ne_mes in (1,2,3,4,5,6,7,8,9,10,11))) )) as nd_valdectersue  \n ";

                System.out.println("objTooBar.getEstado() : " + objTooBar.getEstado());
                if (objTooBar.getEstado() == 'c' || objTooBar.getEstado() == 'j') {
                    strSql += ", f.tx_obs1 \n ";
                }

                strSql += "from tbm_bensocmentra a \n";
                strSql += "LEFT OUTER JOIN tbm_traemp a1 on (a1.co_emp = a.co_emp and a1.co_tra = a.co_tra) \n";
                strSql += "LEFT OUTER JOIN tbm_tra b on (b.co_tra=a.co_tra) \n";
                strSql += "LEFT OUTER JOIN tbm_emp c on (c.co_emp=a.co_emp) \n";
                strSql += "LEFT OUTER JOIN tbm_ofi d on (d.co_ofi=a1.co_ofi) \n";
                strSql += "LEFT OUTER JOIN tbm_carlab e on (e.co_car=a1.co_car) \n";

                if (objTooBar.getEstado() == 'c' || objTooBar.getEstado() == 'j') {
                    strSql += "LEFT OUTER JOIN tbm_detrolpag f on (f.co_emp=a1.co_emp and f.co_loc=" + objZafParSis.getCodigoLocal() + " and f.co_tipdoc=" + txtCodTipDoc.getText() + " and f.co_doc=" + txtCodDoc.getText() + " and f.co_tra=a1.co_tra) \n";
                }

                strSql += "WHERE ((a1.fe_inicon<=" + objUti.codificar(strFecDes) + " and a1.st_reg = 'A') or (a1.fe_inicon is null and a1.st_reg='A') or (a1.fe_inicon between " + objUti.codificar(strFecDes) + " and " + objUti.codificar(strFecHas) + ")) \n";
                strSql += " " + strFilEmp + "  \n";
                strSql += "ORDER BY a.co_emp, empleado";
                boolean blnVer = false;

                if (objTooBar.getEstado() == 'c' || objTooBar.getEstado() == 'j' || blnEstCarSolHSE) {
                    strSql = "";
                    strSql += "select * , (select count(*) from tbm_traemp a where a.co_tra=a3.co_tra and a.st_reg='A') as traActEmp ,  (a4.tx_ape || ' ' || a4.tx_nom) as empleado , ((current_date-a3.fe_inicon)/360) as ne_numAnioLab , extract (MONTH from a3.fe_inicon) as ne_mesEnt, a2.tx_obs1 as tx_comentario , \n";
                    strSql += "(select sum((case(nd_valdectersue is null) when true then 0 else nd_valdectersue end ) -(case(nd_valpagdectersue is null) when true then 0 else nd_valpagdectersue end ) ) as valDecTerSue from tbm_datgeningegrmentra a  \n ";
                    strSql += "left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro=1) \n ";
                    strSql += "where a.co_emp = a3.co_emp \n ";
                    strSql += "and a.co_tra =a3.co_tra \n ";
//**
//                    strSql+="AND  ( ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1)+") AND a.ne_mes in (12))) \n ";
//                    strSql+="OR ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString()))+") AND a.ne_mes in (1,2,3,4,5,6,7,8,9,10,11))) )) as nd_valdectersue  \n ";

                    strSql += strFilPer + " ) as nd_valdectersue \n";

                    strSql += "from tbm_cabrolpag a1 \n ";
                    strSql += "inner join tbm_detrolpag a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc) \n";
                    strSql += "inner join tbm_traemp a3 on (a3.co_emp=a1.co_emp and a3.co_tra=a2.co_tra) \n";
                    strSql += "INNER JOIN tbm_tra a4 on (a4.co_tra=a3.co_tra) \n";
                    strSql += "INNER JOIN tbm_emp a5 on (a5.co_emp=a3.co_emp) \n";
                    strSql += "LEFT OUTER JOIN tbm_ofi a6 on (a6.co_ofi=a3.co_ofi) \n";
                    strSql += "LEFT OUTER JOIN tbm_carlab a7 on (a7.co_car=a3.co_car) \n";
                    if (!blnEstCarSolHSE) {
                        strSql += "where a1.co_emp= " + objZafParSis.getCodigoEmpresa() + "\n";
                        strSql += "and a1.co_loc = " + objZafParSis.getCodigoLocal() + "\n";
                        strSql += "and a1.co_tipdoc= " + txtCodTipDoc.getText() + "\n";
                        strSql += "and a1.co_doc= " + txtCodDoc.getText() + "\n";
                    } else {
                        strSql += "where a1.co_emp= " + strCodEmpAut + "\n";
                        strSql += "and a1.co_loc = " + strCodLocAut + "\n";
                        strSql += "and a1.co_tipdoc= " + strCodTipDocAut + "\n";
                        strSql += "and a1.co_doc= " + strCodDocAut + "\n";
                        strSql += "and a1.ne_numdoc= " + strNumDoc + "\n";
                    }

                    strSql += "AND ((a3.fe_inicon<=" + objUti.codificar(strFecDes) + " and a3.st_reg = 'A') or (a3.fe_inicon is null and a3.st_reg='A') or (a3.fe_inicon between " + objUti.codificar(strFecDes) + " and " + objUti.codificar(strFecHas) + ")) \n";
                    blnVer = true;
                }

                System.out.println("q consul: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);

                vecDat = new Vector();
                while (rstLoc.next()) {

                    if (blnVer) {

                        System.out.println(rstLoc.getDouble("nd_valdectersue"));

//              if(rstLoc.getDouble("nd_valdectersue")>0){
                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_CODTRA, rstLoc.getInt("co_tra"));
                        System.out.println("EMPLEADO: " + rstLoc.getString("empleado"));
                        if (rstLoc.getString("co_tra").compareTo("36") == 0) {
                            System.out.println();
                        }
                        vecReg.add(INT_TBL_NOMTRA, rstLoc.getString("empleado"));
                        vecReg.add(INT_TBL_FECING, rstLoc.getString("fe_inicon"));
                        vecReg.add(INT_TBL_CODCAR, rstLoc.getString("co_car"));
                        vecReg.add(INT_TBL_CARGO, rstLoc.getString("tx_nomcar"));
                        vecReg.add(INT_TBL_CODCARCOMSEC, rstLoc.getString("tx_codcomsec"));
                        vecReg.add(INT_TBL_NOMCARCOMSEC, rstLoc.getString("tx_nomcarcomsec"));
                        vecReg.add(INT_TBL_MINSEC, rstLoc.getString("nd_minsec"));

                        double dblValDescTerSueTra = 0;
                        int intDiasLabTra = 0;

                        String strEmpAux = "";

                        if (rstLoc.getString("fe_inicon") == null) {
//                    if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                            strEmpAux = " and a.co_emp = " + rstLoc.getInt("co_emp") + "\n";
//                    }
                        }

                        if (rstLoc.getInt("traActEmp") > 1) {
                            strEmpAux = " and a.co_emp = " + rstLoc.getInt("co_emp") + "\n";
                        }

//                strSql="";
//                strSql="select * from ( " + "\n";
////                strSql+="select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue , b.nd_valdectersue , b.nd_valpagdectersue from tbm_datgeningegrmentra a " + "\n";
////                strSql+="left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro="+intNeTipPro+")" + "\n";
//                strSql+="select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue ,  a.nd_comvenies, a.nd_comvenbon, " + "\n";
//                strSql+="b.nd_valdectersue as nd_valdectersueIESS,b.nd_valpagdectersue  , " + "\n";
//                strSql+="(b.nd_valdectersue +   case (a.nd_comvenies is null) when true then 0 else round((a.nd_comvenies/12),2) end )  as nd_valdectersue , " + "\n";
//                strSql+="(b.nd_valdectersue +   case (a.nd_comvenbon is null) when true then 0 else round((a.nd_comvenbon/12),2) end )  as nd_valdectersueBon , " + "\n";
//                strSql+="round((a.nd_comvenbon/12),2) as decTerSueComBon " + "\n";
//                strSql+="from tbm_datgeningegrmentra a " + "\n";
//                strSql+="left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro="+intNeTipPro+")" + "\n";
//                strSql+="where a.co_tra = "+rstLoc.getString("co_tra")  + "\n";
//                strSql+=strEmpAux;
//                strSql+="AND ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1) +") AND a.ne_mes in (12)))"  + " \n"; 
//                strSql+="UNION"  + " \n"; 
////                strSql+="select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue , b.nd_valdectersue , b.nd_valpagdectersue from tbm_datgeningegrmentra a " + "\n";
////                strSql+="left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro="+intNeTipPro+")"  + " \n"; 
//                strSql+="select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue ,  a.nd_comvenies, a.nd_comvenbon, " + "\n";
//                strSql+="b.nd_valdectersue as nd_valdectersueIESS,b.nd_valpagdectersue  , " + "\n";
//                strSql+="(b.nd_valdectersue +   case (a.nd_comvenies is null) when true then 0 else round((a.nd_comvenies/12),2) end )  as nd_valdectersue , " + "\n";
//                strSql+="(b.nd_valdectersue +   case (a.nd_comvenbon is null) when true then 0 else round((a.nd_comvenbon/12),2) end )  as nd_valdectersueBon , " + "\n";
//                strSql+="round((a.nd_comvenbon/12),2) as decTerSueComBon " + "\n";
//                strSql+="from tbm_datgeningegrmentra a " + "\n";
//                strSql+="left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro="+intNeTipPro+")" + "\n";
//                strSql+="where a.co_tra = "+rstLoc.getString("co_tra")  + "\n";
//                strSql+=strEmpAux;
//                strSql+="AND ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) +") AND a.ne_mes in (1,2,3,4,5,6,7,8,9,10,11)))"  + " \n"; 
//                strSql+=") as x"  + "\n";
//                strSql+="ORDER BY x.ne_ani , x.ne_mes asc"  + "\n";
                        strSql = "";
                        strSql = "select * from ( " + "\n";
                        if (intNeTipPro == 1 || intNeTipPro == 3) {
                            strSql += "select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue ,  a.nd_comvenies, a.nd_comvenbon, " + "\n";
                            strSql += "b.nd_valdectersue as nd_valdectersueIESS,b.nd_valpagdectersue  , " + "\n";
                            strSql += "(b.nd_valdectersue +   case (a.nd_comvenies is null) when true then 0 else round((a.nd_comvenies/12),2) end )  as nd_valdectersue , " + "\n";
                            strSql += "(b.nd_valdectersue +   case (a.nd_comvenbon is null) when true then 0 else round((a.nd_comvenbon/12),2) end )  as nd_valdectersueBon, " + "\n";
                            strSql += "round((a.nd_comvenbon/12),2) as decTerSueComBon " + "\n";
                            strSql += "from tbm_datgeningegrmentra a " + "\n";
                            strSql += "left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro=" + intNeTipPro + ")" + "\n";
                            strSql += "where a.co_tra = " + rstLoc.getString("co_tra") + "\n";
                            strSql += strEmpAux;
                            strSql += "AND ((a.ne_ani in (" + (Integer.parseInt(cboPerAAAA.getSelectedItem().toString()) - 1) + ") AND a.ne_mes in (12)))" + " \n";
                            strSql += "UNION" + " \n";
                            strSql += "select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue ,  a.nd_comvenies, a.nd_comvenbon, " + "\n";
                            strSql += "b.nd_valdectersue as nd_valdectersueIESS,b.nd_valpagdectersue  , (b.nd_valdectersue +   case (a.nd_comvenies is null) when true then 0 else round((a.nd_comvenies/12),2) end )  as nd_valdectersue , " + "\n";
                            strSql += "(b.nd_valdectersue +   case (a.nd_comvenbon is null) when true then 0 else round((a.nd_comvenbon/12),2) end )  as nd_valdectersueBon, " + "\n";
                            strSql += "round((a.nd_comvenbon/12),2) as decTerSueComBon " + "\n";
                            strSql += "from tbm_datgeningegrmentra a " + "\n";
                            strSql += "left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro=" + intNeTipPro + ")" + "\n";
                            strSql += "where a.co_tra = " + rstLoc.getString("co_tra") + "\n";
                            strSql += strEmpAux;
                            strSql += "AND ((a.ne_ani in (" + (Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) + ") AND a.ne_mes in (1,2,3,4,5,6,7,8,9,10,11)))" + " \n";
                            strSql += ") as x" + "\n";
                            strSql += "ORDER BY x.ne_ani , x.ne_mes asc" + "\n";
                        } else {
                            strSql += "select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue ,  a.nd_comvenies, a.nd_comvenbon, " + "\n";
                            strSql += "b.nd_valdectersue as nd_valdectersueIESS,b.nd_valpagdectersue  , " + "\n";
                            strSql += "(b.nd_valdectersue +   case (a.nd_comvenies is null) when true then 0 else round((a.nd_comvenies/12),2) end )  as nd_valdectersue , " + "\n";
                            strSql += "(b.nd_valdectersue +   case (a.nd_comvenbon is null) when true then 0 else round((a.nd_comvenbon/12),2) end )  as nd_valdectersueBon, " + "\n";
                            strSql += "round((a.nd_comvenbon/12),2) as decTerSueComBon " + "\n";
                            strSql += "from tbm_datgeningegrmentra a " + "\n";
                            strSql += "left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro=" + intNeTipPro + ")" + "\n";
                            if (intNeTipPro == 6) {
                                strSql += "left outer join tbm_ingegrmenprgtra c on (c.co_emp=a.co_emp and c.co_tra=a.co_tra and c.ne_ani=a.ne_ani and c.ne_mes=a.ne_mes and c.co_rub=6) " + "\n";
                            } else {
                                strSql += "left outer join tbm_ingegrmenprgtra c on (c.co_emp=a.co_emp and c.co_tra=a.co_tra and c.ne_ani=a.ne_ani and c.ne_mes=a.ne_mes and c.co_rub=7) " + "\n";
                            }

                            strSql += "where a.co_tra = " + rstLoc.getString("co_tra") + "\n";
                            strSql += strEmpAux;
                            strSql += "AND ((a.ne_ani in (" + (Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) + ") AND a.ne_mes in (1,2,3,4,5,6,7,8,9,10,11,12)))" + " \n";
                            strSql += ") as x" + "\n";
                            strSql += "ORDER BY x.ne_ani , x.ne_mes asc" + "\n";
                        }

                        System.out.println("q consulTra: " + strSql);
                        rstLocAux = stmLocAux.executeQuery(strSql);
                        int intNeMesPos = 0;

                        while (rstLocAux.next()) {

                            /*validacion subrogrados
                             int intEmpAct=0;
                             strSql="";
                             strSql+="select count(*) as canReg from tbm_bensocmentra where co_emp="+rstLocAux.getString("co_emp") +"\n";
                             strSql+="and ne_tippro="+intNeTipPro+"\n";
                             strSql+="and co_tra="+rstLocAux.getString("co_tra");
                             strSql+="AND ((ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1) +") AND ne_mes in (12))"  + " \n"; 
                             strSql+="OR ((ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) +") AND ne_mes in (1,2,3,4,5,6,7,8,9,10,11))) ) "  + " \n"; 
                    
                    
                             rstLocTraSub=stmLocTraSub.executeQuery(strSql);
                    
                             if(rstLocTraSub.next()){
                             intEmpAct=rstLocTraSub.getInt("canReg");
                             }
                    
                             int intEmpTot=0;
                             strSql="";
                             strSql+="select count(*) as canReg from tbm_bensocmentra where ne_tippro="+intNeTipPro+"\n";
                             strSql+="and co_tra="+rstLocAux.getString("co_tra");
                             strSql+="AND ((ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1) +") AND ne_mes in (12))"  + " \n"; 
                             strSql+="OR ((ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) +") AND ne_mes in (1,2,3,4,5,6,7,8,9,10,11))) ) "  + " \n"; 
                    
                             rstLocTraSub=stmLocTraSub.executeQuery(strSql);
                    
                             if(rstLocTraSub.next()){
                             intEmpTot=rstLocTraSub.getInt("canReg");
                             }*/
                            boolean blnAct = false;
                            String strNdValDecTerSue = "";
                            if (intNeTipPro == 2) {
                                strNdValDecTerSue = rstLocAux.getString("nd_valdectersueBon");
                                if (strNdValDecTerSue == null) {
                                    strNdValDecTerSue = rstLocAux.getString("nd_comvenbon");
                                    blnAct = true;
                                }
                            } else {
                                strNdValDecTerSue = rstLocAux.getString("nd_valdectersue");
                            }
                            String strFeIniCon = rstLoc.getString("fe_inicon");
                            if (strFeIniCon == null) {

                                do {
                                    if (intNeTipPro == 2) {
                                        strNdValDecTerSue = rstLocAux.getString("nd_valdectersueBon");
                                        if (strNdValDecTerSue == null) {
                                            strNdValDecTerSue = rstLocAux.getString("nd_comvenbon");
                                            blnAct = true;
                                        }
                                    } else {
                                        strNdValDecTerSue = rstLocAux.getString("nd_valdectersue");
                                    }
                                    if (strNdValDecTerSue == null) {
                                        vecReg.add(vecReg.size(), null);
                                        vecReg.add(vecReg.size(), null);
                                        vecReg.add(vecReg.size(), null);
                                        vecReg.add(vecReg.size(), null);
                                        intNeMesPos++;
                                    } else {

                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_sue"));

                                        if (intNeTipPro == 1) {
                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenies"));
                                        } else if (intNeTipPro == 2) {
                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenbon"));
                                        } else if (intNeTipPro == 3) {
                                            vecReg.add(vecReg.size(), null);
                                        }

                                        vecReg.add(vecReg.size(), rstLocAux.getInt("ne_numdialab"));

                                        if (intNeTipPro == 1) {
                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valDecTerSue"));
                                            dblValDescTerSueTra += rstLocAux.getDouble("nd_valDecTerSue");
                                        } else if (intNeTipPro == 2) {
                                            if (blnAct) {
                                                vecReg.add(vecReg.size(), rstLocAux.getDouble("decTerSueComBon"));
                                                dblValDescTerSueTra += rstLocAux.getDouble("decTerSueComBon");
                                            } else {
                                                vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueBon"));
                                                dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueBon");
                                            }
                                        } else if (intNeTipPro == 3) {
                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueIESS"));
                                            dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueIESS");
                                        }
//                                      
//                                      if(intNeTipPro==2){
////                                        vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valdectersueBon"));
//                                        /*vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valdectersueBon"));
//                                        dblValDescTerSueTra+=rstLocAux.getDouble("nd_valdectersueBon");*/
//                                          
//                                         if(blnAct){
//                                            vecReg.add(vecReg.size(),rstLocAux.getDouble("decTerSueComBon"));
//                                            dblValDescTerSueTra+=rstLocAux.getDouble("decTerSueComBon");
//                                         }else{
//                                            vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valdectersueBon"));
//                                            dblValDescTerSueTra+=rstLocAux.getDouble("nd_valdectersueBon");
//                                         }
//                                        
//                                      }else{
//                                        vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valPagDecTerSue"));
//                                        dblValDescTerSueTra+=rstLocAux.getDouble("nd_valPagDecTerSue");
//                                      }

                                        intDiasLabTra += rstLocAux.getInt("ne_numdialab");
                                        intNeMesPos++;
                                    }
                                } while (rstLocAux.next());

                            } else {

                                if (rstLoc.getInt("ne_numAnioLab") == 0) {
                                    if (rstLoc.getInt("ne_mesent") == 1) {

//                              vecReg.add(vecReg.size(), null);
//                              vecReg.add(vecReg.size(), null);
//                              vecReg.add(vecReg.size(), null);
//                              vecReg.add(vecReg.size(), null);
//                              intNeMesPos++;
                                        do {
                                            if (intNeTipPro == 2) {
                                                strNdValDecTerSue = rstLocAux.getString("nd_valdectersueBon");
                                                if (strNdValDecTerSue == null) {
                                                    strNdValDecTerSue = rstLocAux.getString("nd_comvenbon");
                                                    blnAct = true;
                                                }
                                            } else {
                                                strNdValDecTerSue = rstLocAux.getString("nd_valdectersue");
                                            }
                                            if (strNdValDecTerSue == null) {
                                                vecReg.add(vecReg.size(), null);
                                                vecReg.add(vecReg.size(), null);
                                                vecReg.add(vecReg.size(), null);
                                                vecReg.add(vecReg.size(), null);
                                                intNeMesPos++;
                                            } else {
                                                vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_sue"));

                                                if (intNeTipPro == 1) {
                                                    vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenies"));
                                                } else if (intNeTipPro == 2) {
                                                    vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenbon"));
                                                } else if (intNeTipPro == 3) {
                                                    vecReg.add(vecReg.size(), null);
                                                }

                                                vecReg.add(vecReg.size(), rstLocAux.getInt("ne_numdialab"));
                                                if (intNeTipPro == 1) {
                                                    vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valDecTerSue"));
                                                    dblValDescTerSueTra += rstLocAux.getDouble("nd_valDecTerSue");
                                                } else if (intNeTipPro == 2) {
                                                    if (blnAct) {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("decTerSueComBon"));
                                                        dblValDescTerSueTra += rstLocAux.getDouble("decTerSueComBon");
                                                    } else {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueBon"));
                                                        dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueBon");
                                                    }
                                                } else if (intNeTipPro == 3) {
                                                    vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueIESS"));
                                                    dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueIESS");
                                                }
                                                intDiasLabTra += rstLocAux.getInt("ne_numdialab");
                                                intNeMesPos++;
                                            }
                                        } while (rstLocAux.next());

                                    } else if (rstLoc.getInt("ne_mesent") > 1) {

                                        cal = Calendar.getInstance();
                                        int intMesAct = cal.get(Calendar.MONTH);

                                        if (intMesAct < rstLoc.getInt("ne_mesent")) {

                                            do {
                                                if (intNeTipPro == 2) {
                                                    strNdValDecTerSue = rstLocAux.getString("nd_valdectersueBon");
                                                    if (strNdValDecTerSue == null) {
                                                        strNdValDecTerSue = rstLocAux.getString("nd_comvenbon");
                                                        blnAct = true;
                                                    }
                                                } else {
                                                    strNdValDecTerSue = rstLocAux.getString("nd_valdectersue");
                                                }
                                                if (strNdValDecTerSue == null) {
                                                    vecReg.add(vecReg.size(), null);
                                                    vecReg.add(vecReg.size(), null);
                                                    vecReg.add(vecReg.size(), null);
                                                    vecReg.add(vecReg.size(), null);
                                                    intNeMesPos++;
                                                } else {
                                                    vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_sue"));

                                                    if (intNeTipPro == 1) {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenies"));
                                                    } else if (intNeTipPro == 2) {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenbon"));
                                                    } else if (intNeTipPro == 3) {
                                                        vecReg.add(vecReg.size(), null);
                                                    }

                                                    vecReg.add(vecReg.size(), rstLocAux.getInt("ne_numdialab"));
                                                    if (intNeTipPro == 1) {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valDecTerSue"));
                                                        dblValDescTerSueTra += rstLocAux.getDouble("nd_valDecTerSue");
                                                    } else if (intNeTipPro == 2) {
                                                        if (blnAct) {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("decTerSueComBon"));
                                                            dblValDescTerSueTra += rstLocAux.getDouble("decTerSueComBon");
                                                        } else {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueBon"));
                                                            dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueBon");
                                                        }
                                                    } else if (intNeTipPro == 3) {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueIESS"));
                                                        dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueIESS");
                                                    }
                                                    intDiasLabTra += rstLocAux.getInt("ne_numdialab");
                                                    intNeMesPos++;
                                                }
                                            } while (rstLocAux.next());
                                        } else if (intMesAct == rstLoc.getInt("ne_mesent")) {

                                            int intPosValMax = 0;
                                            intPosValMax = rstLoc.getInt("ne_mesent");
                                            for (int intFil = 0; intFil < intPosValMax; intFil++) {

                                                vecReg.add(vecReg.size(), null);
                                                vecReg.add(vecReg.size(), null);
                                                vecReg.add(vecReg.size(), null);
                                                vecReg.add(vecReg.size(), null);
                                                intNeMesPos++;
                                            }
//                                  }
                                            do {
                                                if (intNeTipPro == 2) {
                                                    strNdValDecTerSue = rstLocAux.getString("nd_valdectersueBon");
                                                    if (strNdValDecTerSue == null) {
                                                        strNdValDecTerSue = rstLocAux.getString("nd_comvenbon");
                                                        blnAct = true;
                                                    }
                                                } else {
                                                    strNdValDecTerSue = rstLocAux.getString("nd_valdectersue");
                                                }
                                                if (strNdValDecTerSue == null) {
                                                    vecReg.add(vecReg.size(), null);
                                                    vecReg.add(vecReg.size(), null);
                                                    vecReg.add(vecReg.size(), null);
                                                    vecReg.add(vecReg.size(), null);
                                                    intNeMesPos++;
                                                } else {
                                                    vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_sue"));
                                                    if (intNeTipPro == 1) {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenies"));
                                                    } else if (intNeTipPro == 2) {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenbon"));
                                                    } else if (intNeTipPro == 3) {
                                                        vecReg.add(vecReg.size(), null);
                                                    }

                                                    vecReg.add(vecReg.size(), rstLocAux.getInt("ne_numdialab"));
                                                    if (intNeTipPro == 1) {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valDecTerSue"));
                                                        dblValDescTerSueTra += rstLocAux.getDouble("nd_valDecTerSue");
                                                    } else if (intNeTipPro == 2) {
                                                        if (blnAct) {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("decTerSueComBon"));
                                                            dblValDescTerSueTra += rstLocAux.getDouble("decTerSueComBon");
                                                        } else {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueBon"));
                                                            dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueBon");
                                                        }
                                                    } else if (intNeTipPro == 3) {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueIESS"));
                                                        dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueIESS");
                                                    }
                                                    intDiasLabTra += rstLocAux.getInt("ne_numdialab");
                                                    intNeMesPos++;
                                                }
                                            } while (rstLocAux.next());

                                        } else {

                                            int intPosValMax = 0;

                                            intPosValMax = rstLoc.getInt("ne_mesent");
                                            if (intNeTipPro == 2 || intNeTipPro == 3) {
                                                intPosValMax--;
                                            }
                                            for (int intFil = 0; intFil < intPosValMax; intFil++) {
                                                vecReg.add(vecReg.size(), null);
                                                vecReg.add(vecReg.size(), null);
                                                vecReg.add(vecReg.size(), null);
                                                vecReg.add(vecReg.size(), null);
                                                intNeMesPos++;
                                            }
//                                  }

                                            do {
                                                if (intNeTipPro == 2) {
                                                    strNdValDecTerSue = rstLocAux.getString("nd_valdectersueBon");
                                                    if (strNdValDecTerSue == null) {
                                                        strNdValDecTerSue = rstLocAux.getString("nd_comvenbon");
                                                        blnAct = true;
                                                    }
                                                } else {
                                                    strNdValDecTerSue = rstLocAux.getString("nd_valdectersue");
                                                }
                                                if (strNdValDecTerSue == null) {
                                                    vecReg.add(vecReg.size(), null);
                                                    vecReg.add(vecReg.size(), null);
                                                    vecReg.add(vecReg.size(), null);
                                                    vecReg.add(vecReg.size(), null);
                                                    intNeMesPos++;
                                                } else {
                                                    vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_sue"));
                                                    if (intNeTipPro == 1) {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenies"));
                                                    } else if (intNeTipPro == 2) {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenbon"));
                                                    } else if (intNeTipPro == 3) {
                                                        vecReg.add(vecReg.size(), null);
                                                    }
                                                    vecReg.add(vecReg.size(), rstLocAux.getInt("ne_numdialab"));
                                                    if (intNeTipPro == 1) {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valDecTerSue"));
                                                        dblValDescTerSueTra += rstLocAux.getDouble("nd_valDecTerSue");
                                                    } else if (intNeTipPro == 2) {
                                                        if (blnAct) {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("decTerSueComBon"));
                                                            dblValDescTerSueTra += rstLocAux.getDouble("decTerSueComBon");
                                                        } else {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueBon"));
                                                            dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueBon");
                                                        }
                                                    } else if (intNeTipPro == 3) {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueIESS"));
                                                        dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueIESS");
                                                    }
                                                    intDiasLabTra += rstLocAux.getInt("ne_numdialab");
                                                    intNeMesPos++;
                                                }
                                            } while (rstLocAux.next());
                                        }
                                    }

                                } else {
                                    if (strNdValDecTerSue == null) {
                                        vecReg.add(vecReg.size(), null);
                                        vecReg.add(vecReg.size(), null);
                                        vecReg.add(vecReg.size(), null);
                                        vecReg.add(vecReg.size(), null);
                                        intNeMesPos++;
                                    } else {
                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_sue"));

                                        if (intNeTipPro == 1) {
                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenies"));
                                        } else if (intNeTipPro == 2) {
                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenbon"));
                                        } else if (intNeTipPro == 3) {
                                            vecReg.add(vecReg.size(), null);
                                        }

                                        vecReg.add(vecReg.size(), rstLocAux.getInt("ne_numdialab"));
                                        if (intNeTipPro == 1) {
                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valDecTerSue"));
                                            dblValDescTerSueTra += rstLocAux.getDouble("nd_valDecTerSue");
                                        } else if (intNeTipPro == 2) {
                                            if (blnAct) {
                                                vecReg.add(vecReg.size(), rstLocAux.getDouble("decTerSueComBon"));
                                                dblValDescTerSueTra += rstLocAux.getDouble("decTerSueComBon");
                                            } else {
                                                vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueBon"));
                                                dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueBon");
                                            }
                                        } else if (intNeTipPro == 3) {
                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueIESS"));
                                            dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueIESS");
                                        }
                                        intDiasLabTra += rstLocAux.getInt("ne_numdialab");
                                        intNeMesPos++;
                                    }
                                }
                            }
                        }

                        if (intNeMesPos < 12) {
                            for (int intFil = intNeMesPos; intFil < arrLstMeses.size(); intFil++) {
                                vecReg.add(vecReg.size(), null);
                                vecReg.add(vecReg.size(), null);
                                vecReg.add(vecReg.size(), null);
                                vecReg.add(vecReg.size(), null);
                            }
                        }

                        vecReg.add(vecReg.size(), intDiasLabTra);
                        vecReg.add(vecReg.size(), dblValDescTerSueTra);
                        vecReg.add(vecReg.size(), "");
                        vecReg.add(vecReg.size(), "...");

                        if (vecReg.size() != 61) {
                            System.err.println(rstLoc.getString("empleado") + " ---> " + vecReg.size());
                        }

                        vecDataCon.add(vecReg);

                    } else { //blnVer*

                        System.out.println(rstLoc.getDouble("nd_valdectersue"));

                        if (rstLoc.getDouble("nd_valdectersue") > 0) {

                            java.util.Vector vecReg = new java.util.Vector();
                            vecReg.add(INT_TBL_LINEA, "");
                            vecReg.add(INT_TBL_CODTRA, rstLoc.getInt("co_tra"));
                            System.out.println("EMPLEADO: " + rstLoc.getString("empleado"));
                            vecReg.add(INT_TBL_NOMTRA, rstLoc.getString("empleado"));
                            vecReg.add(INT_TBL_FECING, rstLoc.getString("fe_inicon"));
                            vecReg.add(INT_TBL_CODCAR, rstLoc.getString("co_car"));
                            vecReg.add(INT_TBL_CARGO, rstLoc.getString("tx_nomcar"));
                            vecReg.add(INT_TBL_CODCARCOMSEC, rstLoc.getString("tx_codcomsec"));
                            vecReg.add(INT_TBL_NOMCARCOMSEC, rstLoc.getString("tx_nomcarcomsec"));
                            vecReg.add(INT_TBL_MINSEC, rstLoc.getString("nd_minsec"));

                            double dblValDescTerSueTra = 0;
                            int intDiasLabTra = 0;

                            String strEmpAux = "";

                            if (rstLoc.getString("fe_inicon") == null) {
                                strEmpAux = " and a.co_emp = " + rstLoc.getInt("co_emp") + "\n";
                            }

                            if (rstLoc.getInt("traActEmp") > 1) {
                                strEmpAux = " and a.co_emp = " + rstLoc.getInt("co_emp") + "\n";
                            }

//                strSql="";
//                strSql="select * from ( " + "\n";
////                strSql+="select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue , b.nd_valdectersue , b.nd_valpagdectersue from tbm_datgeningegrmentra a " + "\n";
////                strSql+="left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro="+intNeTipPro+")" + "\n";
//                strSql+="select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue ,  a.nd_comvenies, a.nd_comvenbon, " + "\n";
//                strSql+="b.nd_valdectersue as nd_valdectersueIESS,b.nd_valpagdectersue  , " + "\n";
//                strSql+="(b.nd_valdectersue +   case (a.nd_comvenies is null) when true then 0 else round((a.nd_comvenies/12),2) end )  as nd_valdectersue , " + "\n";
//                strSql+="(b.nd_valdectersue +   case (a.nd_comvenbon is null) when true then 0 else round((a.nd_comvenbon/12),2) end )  as nd_valdectersueBon , " + "\n";
//                strSql+="round((a.nd_comvenbon/12),2) as decTerSueComBon " + "\n";
//                strSql+="from tbm_datgeningegrmentra a " + "\n";
//                strSql+="left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro="+intNeTipPro+")" + "\n";
//                strSql+="where a.co_tra = "+rstLoc.getString("co_tra")  + "\n";
//                
//                strSql+=strEmpAux;
//                strSql+="AND ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1) +") AND a.ne_mes in (12)))"  + " \n"; 
//                strSql+="UNION"  + " \n"; 
////                strSql+="select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue , b.nd_valdectersue , b.nd_valpagdectersue from tbm_datgeningegrmentra a " + "\n";
////                strSql+="left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro="+intNeTipPro+")"  + " \n"; 
//                strSql+="select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue ,  a.nd_comvenies, a.nd_comvenbon, " + "\n";
//                strSql+="b.nd_valdectersue as nd_valdectersueIESS,b.nd_valpagdectersue  , " + "\n";
//                strSql+="(b.nd_valdectersue +   case (a.nd_comvenies is null) when true then 0 else round((a.nd_comvenies/12),2) end )  as nd_valdectersue , " + "\n";
//                strSql+="(b.nd_valdectersue +   case (a.nd_comvenbon is null) when true then 0 else round((a.nd_comvenbon/12),2) end )  as nd_valdectersueBon , " + "\n";
//                strSql+="round((a.nd_comvenbon/12),2) as decTerSueComBon " + "\n";
//                strSql+="from tbm_datgeningegrmentra a " + "\n";
//                strSql+="left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro="+intNeTipPro+")" + "\n";
//                strSql+="where a.co_tra = "+rstLoc.getString("co_tra")  + "\n";
//                strSql+=strEmpAux;
//                strSql+="AND ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) +") AND a.ne_mes in (1,2,3,4,5,6,7,8,9,10,11)))"  + " \n"; 
//                strSql+=") as x"  + "\n";
//                strSql+="ORDER BY x.ne_ani , x.ne_mes asc"  + "\n";
                            strSql = "";
                            strSql = "select * from ( " + "\n";
                            if (intNeTipPro == 1 || intNeTipPro == 3) {
                                strSql += "select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue ,  a.nd_comvenies, a.nd_comvenbon, " + "\n";
                                strSql += "b.nd_valdectersue as nd_valdectersueIESS,b.nd_valpagdectersue  , " + "\n";
                                strSql += "(b.nd_valdectersue +   case (a.nd_comvenies is null) when true then 0 else round((a.nd_comvenies/12),2) end )  as nd_valdectersue , " + "\n";
                                strSql += "(b.nd_valdectersue +   case (a.nd_comvenbon is null) when true then 0 else round((a.nd_comvenbon/12),2) end )  as nd_valdectersueBon, " + "\n";
                                strSql += "round((a.nd_comvenbon/12),2) as decTerSueComBon " + "\n";
                                strSql += "from tbm_datgeningegrmentra a " + "\n";
                                strSql += "left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro=" + intNeTipPro + ")" + "\n";
                                strSql += "where a.co_tra = " + rstLoc.getString("co_tra") + "\n";
                                strSql += strEmpAux;
                                strSql += "AND ((a.ne_ani in (" + (Integer.parseInt(cboPerAAAA.getSelectedItem().toString()) - 1) + ") AND a.ne_mes in (12)))" + " \n";
                                strSql += "UNION" + " \n";
                                strSql += "select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue ,  a.nd_comvenies, a.nd_comvenbon, " + "\n";
                                strSql += "b.nd_valdectersue as nd_valdectersueIESS,b.nd_valpagdectersue  , (b.nd_valdectersue +   case (a.nd_comvenies is null) when true then 0 else round((a.nd_comvenies/12),2) end )  as nd_valdectersue , " + "\n";
                                strSql += "(b.nd_valdectersue +   case (a.nd_comvenbon is null) when true then 0 else round((a.nd_comvenbon/12),2) end )  as nd_valdectersueBon, " + "\n";
                                strSql += "round((a.nd_comvenbon/12),2) as decTerSueComBon " + "\n";
                                strSql += "from tbm_datgeningegrmentra a " + "\n";
                                strSql += "left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro=" + intNeTipPro + ")" + "\n";
                                strSql += "where a.co_tra = " + rstLoc.getString("co_tra") + "\n";
                                strSql += strEmpAux;
                                strSql += "AND ((a.ne_ani in (" + (Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) + ") AND a.ne_mes in (1,2,3,4,5,6,7,8,9,10,11)))" + " \n";
                                strSql += ") as x" + "\n";
                                strSql += "ORDER BY x.ne_ani , x.ne_mes asc" + "\n";
                            } else {
                                strSql += "select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue ,  a.nd_comvenies, a.nd_comvenbon, " + "\n";
                                strSql += "b.nd_valdectersue as nd_valdectersueIESS,b.nd_valpagdectersue  , " + "\n";
                                strSql += "(b.nd_valdectersue +   case (a.nd_comvenies is null) when true then 0 else round((a.nd_comvenies/12),2) end )  as nd_valdectersue , " + "\n";
                                strSql += "(b.nd_valdectersue +   case (a.nd_comvenbon is null) when true then 0 else round((a.nd_comvenbon/12),2) end )  as nd_valdectersueBon, " + "\n";
                                strSql += "round((a.nd_comvenbon/12),2) as decTerSueComBon " + "\n";
                                strSql += "from tbm_datgeningegrmentra a " + "\n";
                                strSql += "left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro=" + intNeTipPro + ")" + "\n";
                                if (intNeTipPro == 2) {
                                    strSql += "left outer join tbm_ingegrmenprgtra c on (c.co_emp=a.co_emp and c.co_tra=a.co_tra and c.ne_ani=a.ne_ani and c.ne_mes=a.ne_mes and c.co_rub=6) " + "\n";
                                } else {
                                    strSql += "left outer join tbm_ingegrmenprgtra c on (c.co_emp=a.co_emp and c.co_tra=a.co_tra and c.ne_ani=a.ne_ani and c.ne_mes=a.ne_mes and c.co_rub=7) " + "\n";
                                }

                                strSql += "where a.co_tra = " + rstLoc.getString("co_tra") + "\n";
                                strSql += strEmpAux;
                                strSql += "AND ((a.ne_ani in (" + (Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) + ") AND a.ne_mes in (1,2,3,4,5,6,7,8,9,10,11,12)))" + " \n";
                                strSql += ") as x" + "\n";
                                strSql += "ORDER BY x.ne_ani , x.ne_mes asc" + "\n";
                            }

                            System.out.println("q consulTra: " + strSql);
                            rstLocAux = stmLocAux.executeQuery(strSql);
                            int intNeMesPos = 0;

                            while (rstLocAux.next()) {

                                /*validacion subrogrados
                                 int intEmpAct=0;
                                 strSql="";
                                 strSql+="select count(*) as canReg from tbm_bensocmentra where co_emp="+rstLocAux.getString("co_emp") +"\n";
                                 strSql+="and ne_tippro="+intNeTipPro+"\n";
                                 strSql+="and co_tra="+rstLocAux.getString("co_tra");
                                 strSql+="AND ((ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1) +") AND ne_mes in (12))"  + " \n"; 
                                 strSql+="OR ((ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) +") AND ne_mes in (1,2,3,4,5,6,7,8,9,10,11))) ) "  + " \n"; 
                    
                    
                                 rstLocTraSub=stmLocTraSub.executeQuery(strSql);
                    
                                 if(rstLocTraSub.next()){
                                 intEmpAct=rstLocTraSub.getInt("canReg");
                                 }
                    
                                 int intEmpTot=0;
                                 strSql="";
                                 strSql+="select count(*) as canReg from tbm_bensocmentra where ne_tippro="+intNeTipPro+"\n";
                                 strSql+="and co_tra="+rstLocAux.getString("co_tra");
                                 strSql+="AND ((ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1) +") AND ne_mes in (12))"  + " \n"; 
                                 strSql+="OR ((ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) +") AND ne_mes in (1,2,3,4,5,6,7,8,9,10,11))) ) "  + " \n"; 
                    
                                 rstLocTraSub=stmLocTraSub.executeQuery(strSql);
                    
                                 if(rstLocTraSub.next()){
                                 intEmpTot=rstLocTraSub.getInt("canReg");
                                 }*/
                                boolean blnAct = false;
                                String strNdValDecTerSue = "";
                                if (intNeTipPro == 2) {
                                    strNdValDecTerSue = rstLocAux.getString("nd_valdectersueBon");
                                    if (strNdValDecTerSue == null) {
                                        strNdValDecTerSue = rstLocAux.getString("nd_comvenbon");
                                        blnAct = true;
                                    }
                                } else {
                                    strNdValDecTerSue = rstLocAux.getString("nd_valdectersue");
                                }
                                String strFeIniCon = rstLoc.getString("fe_inicon");
                                if (strFeIniCon == null) {

//                        vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_sue"));
//                                    vecReg.add(vecReg.size(),rstLocAux.getInt("ne_numdialab"));
//                                    if(intNeTipPro==2){
//                                        vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valdectersueBon")-rstLocAux.getDouble("nd_valdectersueBon"));
//                                        dblValDescTerSueTra+=rstLocAux.getDouble("nd_valdectersueBon")-rstLocAux.getDouble("nd_valdectersueBon");
//                                    }else{
//                                        vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valDecTerSue")-rstLocAux.getDouble("nd_valPagDecTerSue"));
//                                        dblValDescTerSueTra+=rstLocAux.getDouble("nd_valDecTerSue")-rstLocAux.getDouble("nd_valPagDecTerSue");
//                                    }
//                                    
//                                    intDiasLabTra+=rstLocAux.getInt("ne_numdialab");
//                                    intNeMesPos++;
                                    do {
                                        if (intNeTipPro == 2) {
                                            strNdValDecTerSue = rstLocAux.getString("nd_valdectersueBon");
                                            if (strNdValDecTerSue == null) {
                                                strNdValDecTerSue = rstLocAux.getString("nd_comvenbon");
                                                blnAct = true;
                                            }
                                        } else {
                                            strNdValDecTerSue = rstLocAux.getString("nd_valdectersue");
                                        }
                                        if (strNdValDecTerSue == null) {
                                            vecReg.add(vecReg.size(), null);
                                            vecReg.add(vecReg.size(), null);
                                            vecReg.add(vecReg.size(), null);
                                            vecReg.add(vecReg.size(), null);
                                            intNeMesPos++;
                                        } else {
                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_sue"));
                                            if (intNeTipPro == 1) {
                                                vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenies"));
                                            } else if (intNeTipPro == 2) {
                                                vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenbon"));
                                            } else if (intNeTipPro == 3) {
                                                vecReg.add(vecReg.size(), null);
                                            }
                                            vecReg.add(vecReg.size(), rstLocAux.getInt("ne_numdialab"));
                                            if (intNeTipPro == 1) {
                                                vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valDecTerSue"));
                                                dblValDescTerSueTra += rstLocAux.getDouble("nd_valDecTerSue");
                                            } else if (intNeTipPro == 2) {
                                                if (blnAct) {
                                                    vecReg.add(vecReg.size(), rstLocAux.getDouble("decTerSueComBon"));
                                                    dblValDescTerSueTra += rstLocAux.getDouble("decTerSueComBon");
                                                } else {
                                                    vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueBon"));
                                                    dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueBon");
                                                }
                                            } else if (intNeTipPro == 3) {
                                                vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueIESS"));
                                                dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueIESS");
                                            }
                                            intDiasLabTra += rstLocAux.getInt("ne_numdialab");
                                            intNeMesPos++;
                                        }
                                    } while (rstLocAux.next());
                                } else {

                                    if (rstLoc.getInt("ne_numAnioLab") == 0) {
                                        if (rstLoc.getInt("ne_mesent") == 1) {

//                              if(intNeTipPro==2 || intNeTipPro==3){
//                                      intPosValMax--;
//                                  }
//                              vecReg.add(vecReg.size(), null);
//                              vecReg.add(vecReg.size(), null);
//                              vecReg.add(vecReg.size(), null);
//                              vecReg.add(vecReg.size(), null);
//                              intNeMesPos++;
                                            do {
                                                if (intNeTipPro == 2) {
                                                    strNdValDecTerSue = rstLocAux.getString("nd_valdectersueBon");
                                                    if (strNdValDecTerSue == null) {
                                                        strNdValDecTerSue = rstLocAux.getString("nd_comvenbon");
                                                        blnAct = true;
                                                    }
                                                } else {
                                                    strNdValDecTerSue = rstLocAux.getString("nd_valdectersue");
                                                }
                                                if (strNdValDecTerSue == null) {
                                                    vecReg.add(vecReg.size(), null);
                                                    vecReg.add(vecReg.size(), null);
                                                    vecReg.add(vecReg.size(), null);
                                                    vecReg.add(vecReg.size(), null);
                                                    intNeMesPos++;
                                                } else {
                                                    vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_sue"));
                                                    if (intNeTipPro == 1) {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenies"));
                                                    } else if (intNeTipPro == 2) {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenbon"));
                                                    } else if (intNeTipPro == 3) {
                                                        vecReg.add(vecReg.size(), null);
                                                    }
                                                    vecReg.add(vecReg.size(), rstLocAux.getInt("ne_numdialab"));
                                                    if (intNeTipPro == 1) {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valDecTerSue"));
                                                        dblValDescTerSueTra += rstLocAux.getDouble("nd_valDecTerSue");
                                                    } else if (intNeTipPro == 2) {
                                                        if (blnAct) {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("decTerSueComBon"));
                                                            dblValDescTerSueTra += rstLocAux.getDouble("decTerSueComBon");
                                                        } else {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueBon"));
                                                            dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueBon");
                                                        }
                                                    } else if (intNeTipPro == 3) {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueIESS"));
                                                        dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueIESS");
                                                    }
                                                    intDiasLabTra += rstLocAux.getInt("ne_numdialab");
                                                    intNeMesPos++;
                                                }
                                            } while (rstLocAux.next());

                                        } else if (rstLoc.getInt("ne_mesent") > 1) {

                                            cal = Calendar.getInstance();
                                            int intMesAct = cal.get(Calendar.MONTH);

                                            if (intMesAct < rstLoc.getInt("ne_mesent")) {

                                                do {
                                                    if (intNeTipPro == 2) {
                                                        strNdValDecTerSue = rstLocAux.getString("nd_valdectersueBon");
                                                        if (strNdValDecTerSue == null) {
                                                            strNdValDecTerSue = rstLocAux.getString("nd_comvenbon");
                                                            blnAct = true;
                                                        }
                                                    } else {
                                                        strNdValDecTerSue = rstLocAux.getString("nd_valdectersue");
                                                    }
                                                    if (strNdValDecTerSue == null) {
                                                        vecReg.add(vecReg.size(), null);
                                                        vecReg.add(vecReg.size(), null);
                                                        vecReg.add(vecReg.size(), null);
                                                        vecReg.add(vecReg.size(), null);
                                                        intNeMesPos++;
                                                    } else {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_sue"));
                                                        if (intNeTipPro == 1) {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenies"));
                                                        } else if (intNeTipPro == 2) {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenbon"));
                                                        } else if (intNeTipPro == 3) {
                                                            vecReg.add(vecReg.size(), null);
                                                        }
                                                        vecReg.add(vecReg.size(), rstLocAux.getInt("ne_numdialab"));
                                                        if (intNeTipPro == 1) {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valDecTerSue"));
                                                            dblValDescTerSueTra += rstLocAux.getDouble("nd_valDecTerSue");
                                                        } else if (intNeTipPro == 2) {
                                                            if (blnAct) {
                                                                vecReg.add(vecReg.size(), rstLocAux.getDouble("decTerSueComBon"));
                                                                dblValDescTerSueTra += rstLocAux.getDouble("decTerSueComBon");
                                                            } else {
                                                                vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueBon"));
                                                                dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueBon");
                                                            }
                                                        } else if (intNeTipPro == 3) {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueIESS"));
                                                            dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueIESS");
                                                        }
                                                        intDiasLabTra += rstLocAux.getInt("ne_numdialab");
                                                        intNeMesPos++;
                                                    }
                                                } while (rstLocAux.next());
                                            } else if (intMesAct == rstLoc.getInt("ne_mesent")) {

                                                int intPosValMax = 0;
//                                  if(objParSis.getCodigoEmpresa()==1 ||objParSis.getCodigoEmpresa()==4){
//                                      intPosValMax=((rstLoc.getInt("ne_mesent")-1)-2);
                                                intPosValMax = rstLoc.getInt("ne_mesent");
                                                for (int intFil = 0; intFil < intPosValMax; intFil++) {

                                                    vecReg.add(vecReg.size(), null);
                                                    vecReg.add(vecReg.size(), null);
                                                    vecReg.add(vecReg.size(), null);
                                                    vecReg.add(vecReg.size(), null);
                                                    intNeMesPos++;
                                                }
//                                  }
                                                do {
                                                    if (intNeTipPro == 2) {
                                                        strNdValDecTerSue = rstLocAux.getString("nd_valdectersueBon");
                                                        if (strNdValDecTerSue == null) {
                                                            strNdValDecTerSue = rstLocAux.getString("nd_comvenbon");
                                                            blnAct = true;
                                                        }
                                                    } else {
                                                        strNdValDecTerSue = rstLocAux.getString("nd_valdectersue");
                                                    }
                                                    if (strNdValDecTerSue == null) {
                                                        vecReg.add(vecReg.size(), null);
                                                        vecReg.add(vecReg.size(), null);
                                                        vecReg.add(vecReg.size(), null);
                                                        vecReg.add(vecReg.size(), null);
                                                        intNeMesPos++;
                                                    } else {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_sue"));
                                                        if (intNeTipPro == 1) {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenies"));
                                                        } else if (intNeTipPro == 2) {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenbon"));
                                                        } else if (intNeTipPro == 3) {
                                                            vecReg.add(vecReg.size(), null);
                                                        }
                                                        vecReg.add(vecReg.size(), rstLocAux.getInt("ne_numdialab"));
                                                        if (intNeTipPro == 1) {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valDecTerSue"));
                                                            dblValDescTerSueTra += rstLocAux.getDouble("nd_valDecTerSue");
                                                        } else if (intNeTipPro == 2) {
                                                            if (blnAct) {
                                                                vecReg.add(vecReg.size(), rstLocAux.getDouble("decTerSueComBon"));
                                                                dblValDescTerSueTra += rstLocAux.getDouble("decTerSueComBon");
                                                            } else {
                                                                vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueBon"));
                                                                dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueBon");
                                                            }
                                                        } else if (intNeTipPro == 3) {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueIESS"));
                                                            dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueIESS");
                                                        }
                                                        intDiasLabTra += rstLocAux.getInt("ne_numdialab");
                                                        intNeMesPos++;
                                                    }
                                                } while (rstLocAux.next());

                                            } else {

                                                int intPosValMax = 0;
//                                  if(objParSis.getCodigoEmpresa()==1 ||objParSis.getCodigoEmpresa()==4){
//                                      intPosValMax=((rstLoc.getInt("ne_mesent")-1)-2);
                                                intPosValMax = rstLoc.getInt("ne_mesent");
                                                if (intNeTipPro == 2 || intNeTipPro == 3) {
                                                    intPosValMax--;
                                                }
                                                for (int intFil = 0; intFil < intPosValMax; intFil++) {
                                                    vecReg.add(vecReg.size(), null);
                                                    vecReg.add(vecReg.size(), null);
                                                    vecReg.add(vecReg.size(), null);
                                                    vecReg.add(vecReg.size(), null);
                                                    intNeMesPos++;
                                                }
//                                  }

                                                do {
                                                    if (intNeTipPro == 2) {
                                                        strNdValDecTerSue = rstLocAux.getString("nd_valdectersueBon");
                                                        if (strNdValDecTerSue == null) {
                                                            strNdValDecTerSue = rstLocAux.getString("nd_comvenbon");
                                                            blnAct = true;
                                                        }
                                                    } else {
                                                        strNdValDecTerSue = rstLocAux.getString("nd_valdectersue");
                                                    }
                                                    if (strNdValDecTerSue == null) {
                                                        vecReg.add(vecReg.size(), null);
                                                        vecReg.add(vecReg.size(), null);
                                                        vecReg.add(vecReg.size(), null);
                                                        vecReg.add(vecReg.size(), null);
                                                        intNeMesPos++;
                                                    } else {
                                                        vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_sue"));
                                                        if (intNeTipPro == 1) {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenies"));
                                                        } else if (intNeTipPro == 2) {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenbon"));
                                                        } else if (intNeTipPro == 3) {
                                                            vecReg.add(vecReg.size(), null);
                                                        }
                                                        vecReg.add(vecReg.size(), rstLocAux.getInt("ne_numdialab"));
                                                        if (intNeTipPro == 1) {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valDecTerSue"));
                                                            dblValDescTerSueTra += rstLocAux.getDouble("nd_valDecTerSue");
                                                        } else if (intNeTipPro == 2) {
                                                            if (blnAct) {
                                                                vecReg.add(vecReg.size(), rstLocAux.getDouble("decTerSueComBon"));
                                                                dblValDescTerSueTra += rstLocAux.getDouble("decTerSueComBon");
                                                            } else {
                                                                vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueBon"));
                                                                dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueBon");
                                                            }
                                                        } else if (intNeTipPro == 3) {
                                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueIESS"));
                                                            dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueIESS");
                                                        }
                                                        intDiasLabTra += rstLocAux.getInt("ne_numdialab");
                                                        intNeMesPos++;
                                                    }
                                                } while (rstLocAux.next());
                                            }
                                        }

                                    } else {
                                        if (strNdValDecTerSue == null) {
                                            vecReg.add(vecReg.size(), null);
                                            vecReg.add(vecReg.size(), null);
                                            vecReg.add(vecReg.size(), null);
                                            vecReg.add(vecReg.size(), null);
                                            intNeMesPos++;
                                        } else {
                                            vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_sue"));
                                            if (intNeTipPro == 1) {
                                                vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenies"));
                                            } else if (intNeTipPro == 2) {
                                                vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_comvenbon"));
                                            } else if (intNeTipPro == 3) {
                                                vecReg.add(vecReg.size(), null);
                                            }
                                            vecReg.add(vecReg.size(), rstLocAux.getInt("ne_numdialab"));
                                            if (intNeTipPro == 1) {
                                                vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valDecTerSue"));
                                                dblValDescTerSueTra += rstLocAux.getDouble("nd_valDecTerSue");
                                            } else if (intNeTipPro == 2) {
                                                if (blnAct) {
                                                    vecReg.add(vecReg.size(), rstLocAux.getDouble("decTerSueComBon"));
                                                    dblValDescTerSueTra += rstLocAux.getDouble("decTerSueComBon");
                                                } else {
                                                    vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueBon"));
                                                    dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueBon");
                                                }
                                            } else if (intNeTipPro == 3) {
                                                vecReg.add(vecReg.size(), rstLocAux.getDouble("nd_valdectersueIESS"));
                                                dblValDescTerSueTra += rstLocAux.getDouble("nd_valdectersueIESS");
                                            }
                                            intDiasLabTra += rstLocAux.getInt("ne_numdialab");
                                            intNeMesPos++;
                                        }
                                    }
                                }
                            }

                            if (intNeMesPos < 12) {
                                for (int intFil = intNeMesPos; intFil < arrLstMeses.size(); intFil++) {
                                    vecReg.add(vecReg.size(), null);
                                    vecReg.add(vecReg.size(), null);
                                    vecReg.add(vecReg.size(), null);
                                    vecReg.add(vecReg.size(), null);
                                }
                            }

                            vecReg.add(vecReg.size(), intDiasLabTra);
                            vecReg.add(vecReg.size(), dblValDescTerSueTra);
                            vecReg.add(vecReg.size(), "");
                            vecReg.add(vecReg.size(), "...");

                            if (vecReg.size() != 61) {
                                System.err.println(rstLoc.getString("empleado") + " ---> " + vecReg.size());
                            }

                            vecDataCon.add(vecReg);
                        }
                    }

                }

                objTblMod.setData(vecDataCon);
                tblDat.setModel(objTblMod);
                objTblTot.calcularTotales();

//              if(!blnEstCarSolHSE){
                if (!blnVer) {
                    txtValDoc.setText("" + objUti.redondear(tblTot.getValueAt(0, tblTot.getColumnCount() - 3).toString(), objZafParSis.getDecimalesMostrar()));
                }

//              }
//              lblMsgSis.setText("Listo");
//              tabFrm.setSelectedIndex(1);
//              lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros");
//              pgrSis.setIndeterminate(false);
                vecDat.clear();
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(this, Evt);
        } finally {
            try {
                rstLoc.close();
            } catch (Throwable ignore) {
            }
            try {
                rstLocAux.close();
            } catch (Throwable ignore) {
            }
            try {
                stmLoc.close();
            } catch (Throwable ignore) {
            }
            try {
                stmLocAux.close();
            } catch (Throwable ignore) {
            }
            try {
                conn.close();
            } catch (Throwable ignore) {
            }
        }
        System.gc();
        return blnRes;
    }

    private void calcularValorDocumento() {

        double dblTot = objUti.redondear(objUti.parseDouble(objTblTot.getValueAt(0, objTblMod.getColumnCount() - 1)), objZafParSis.getDecimalesMostrar());
        txtValDoc.setText(String.valueOf(dblTot));
    }

    public static java.sql.Time SparseToTime(String hora) throws Exception {
        int h, m, s = 0;
        h = Integer.parseInt(hora.charAt(0) + "" + hora.charAt(1));
        m = Integer.parseInt(hora.charAt(3) + "" + hora.charAt(4));
        return (new java.sql.Time(h, m, s));
    }

    protected void finalize() throws Throwable {
        System.gc();
        super.finalize();
    }

    private boolean generaAsiento() {

        boolean blnRes = true;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        String strTipDocDes = "";

        BigDecimal bgdDebe, bgdHaber;
        int INT_LINEA = 0; //0) Línea: Se debe asignar una cadena vacía o null. 
        int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema). 
        int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso). 
        int INT_VEC_BOTON = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null. 
        int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta. 
        int INT_VEC_DEBE = 5; //5) Debe. 
        int INT_VEC_HABER = 6; //6) Haber. 
        int INT_VEC_REF = 7; //7) Referencia: Se debe asignar una cadena vacía o null
        int INT_VEC_NUEVO = 8;

//        int intEst=0;
//        int intColFij=INT_TBL_DIAS_LAB;
//        int intColDin=INT_TBL_DIAS_LAB;
        int intCodCta = 0;
        String strNumCta = "";

        try {
            objAsiDia.inicializar();
            vecAsiDia = new Vector();

            objAsiDia.setGlosa("PAGO DE DECIMO TERCER SUELDO");

            if (vecDetDiario == null) {
                vecDetDiario = new java.util.Vector();
            } else {
                vecDetDiario.removeAllElements();
            }

            java.util.Vector vecReg = new Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, objCtaCtb_dat.getCtaDeb());
            vecReg.add(INT_VEC_NUMCTA, objCtaCtb_dat.getCtaCodDeb());
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, objCtaCtb_dat.getCtaNomDeb());

            BigDecimal bgdValDoc = new BigDecimal(txtValDoc.getText());
            vecReg.add(INT_VEC_DEBE, bgdValDoc);
            vecReg.add(INT_VEC_HABER, new Double(0));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);

            vecReg = new Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, objCtaCtb_dat.getCtaHab());
            vecReg.add(INT_VEC_NUMCTA, objCtaCtb_dat.getCtaCodHab());
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, objCtaCtb_dat.getCtaNomHab());
            vecReg.add(INT_VEC_DEBE, new Double(0));
            vecReg.add(INT_VEC_HABER, bgdValDoc);
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);

            objAsiDia.setDetalleDiario(vecDetDiario);
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;

        } finally {
            try {
                rst.close();
                rst = null;
            } catch (Throwable ignore) {
            }
            try {
                stm.close();
                stm = null;
            } catch (Throwable ignore) {
            }
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

    private class Plantilla {

        int intCoPla;
        int intCantEmp;

        private Plantilla() {
            intCoPla = 0;
            intCantEmp = 0;
        }

        public void setCoPla(int intCoPla) {
            this.intCoPla = intCoPla;
        }

        public int getCoPla() {
            return this.intCoPla;
        }

        public void setCantEmp(int intCantEmp) {
            this.intCantEmp = intCantEmp;
        }

        public int getCantEmp() {
            return this.intCantEmp;
        }
    }

    private boolean mostrarCtaPre(int intPer) {
        boolean blnRes = true;
        try {
            con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                switch (intPer) {
                    case 1:

                        //Armar la sentencia SQL.
                        strSQL = "";
                        if (blnEstCarSolHSE) {
                            strSQL += "SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                            strSQL += " FROM tbm_plaCta AS a1, tbm_cabTipDoc AS a2";
                            strSQL += " WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_ctahab";
                            strSQL += " AND a2.co_emp=" + strCodEmpAut;
                            strSQL += " AND a2.co_loc=" + strCodLocAut;
                            strSQL += " AND a2.co_tipDoc=" + strCodTipDocAut;
                            strSQL += " AND a2.st_reg='A'";
                        } else {
                            strSQL += "SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                            strSQL += " FROM tbm_plaCta AS a1, tbm_cabTipDoc AS a2";
                            strSQL += " WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_ctahab";
                            strSQL += " AND a2.co_emp=" + objZafParSis.getCodigoEmpresa();
                            strSQL += " AND a2.co_loc=" + objZafParSis.getCodigoLocal();
                            strSQL += " AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                            strSQL += " AND a2.st_reg='A'";
                        }

                        break;

                    case 2:

                        strSQL = "";
                        if (blnEstCarSolHSE) {
                            strSQL += "SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta";
                            strSQL += " FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                            strSQL += " WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                            strSQL += " AND a2.co_emp=" + strCodEmpAut;
                            strSQL += " AND a2.co_loc=" + strCodLocAut;
                            strSQL += " AND a2.co_tipDoc=" + strCodTipDocAut;
                            strSQL += " AND a2.st_reg='S'";
                        } else {
                            strSQL += "SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta";
                            strSQL += " FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                            strSQL += " WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                            strSQL += " AND a2.co_emp=" + objZafParSis.getCodigoEmpresa();
                            strSQL += " AND a2.co_loc=" + objZafParSis.getCodigoLocal();
                            strSQL += " AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                            strSQL += " AND a2.st_reg='S'";
                        }

                }

                rst = stm.executeQuery(strSQL);
                if (rst.next()) {
                    txtCodCta.setText(rst.getString("co_cta"));
                    txtDesCorCta.setText(rst.getString("tx_codCta"));
                    txtDesLarCta.setText(rst.getString("tx_desLar"));
                    switch (intPer) {
                        case 2:
                            strUbiCta = rst.getString("tx_ubiCta");
                            break;
                    }

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
}
