package RecursosHumanos.ZafRecHum37;

import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafNotCorEle.ZafNotCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumDao.RRHHDao;
import Librerias.ZafRecHum.ZafRecHumPoj.Provisiones;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_traemp;
import Librerias.ZafRecHum.ZafRecHumPar.ZafRecHumPar;
import Librerias.ZafRecHum.ZafVenFun.ZafVenFun;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import RecursosHumanos.ZafRecHum37.ExcelTableExporter.ExcelTableExporter;
import com.tuval.utilities.archivos.ArchivosTuval;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.JTableHeader;

/**
 * Roles de Pago
 *
 * @author Roberto Flores Guayaquil 02/07/2012
 */
public class ZafRecHum37 extends javax.swing.JInternalFrame 
{
    private static final int INT_TBL_LINEA = 0;
    private static final int INT_TBL_CODTRA = 1;
    private static final int INT_TBL_IDTRA = 2;
    private static final int INT_TBL_NOMTRA = 3;
    private static final int INT_TBL_DIAS_LAB = 4;
    private static final int INT_TBL_TOTING = 5;
    private static final int INT_TBL_TOTEGR = 6;
    private static final int INT_TBL_TOTREC = 7;

    private static final int INT_TBL_PROV_LINEA = 0;
    //  private static final int INT_TBL_PROV_COEMP=1;
    //  private static final int INT_TBL_PROV_NOMEMP=2;
    private static final int INT_TBL_PROV_CODTRA = 1;
    private static final int INT_TBL_PROV_NOMTRA = 2;
    private static final int INT_TBL_PROV_DECIMO_TERCER_SUELDO = 3;
    private static final int INT_TBL_PROV_DECIMO_CUARTO_SUELDO = 4;
    private static final int INT_TBL_PROV_FONDO_RESERVA = 5;
    private static final int INT_TBL_PROV_VACACIONES = 6;
    private static final int INT_TBL_PROV_APORTE_PATRONAL = 7;
    private static final int INT_TBL_PROV_TOTAL_TRA = 8;
    
    static final int INT_TBL_DAT_NUM_TOT_CDI = 35;                //Número total de columnas dinámicas.
    
    private java.sql.Connection CONN_GLO = null;
    private java.sql.Statement STM_GLO = null;
    private java.sql.ResultSet rstCab = null;

    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst;
    
    private ZafParSis objZafParSis;
    private ZafNotCorEle objNotCorEle;                          //Libreria para Notificaciones de Correo. 
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModProv;

    private ZafTblTot objTblTot, objTblTotProv;                 //JTable de totales.
    private ZafTblBus objTblBus;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafTblFilCab objTblFilCab, objTblFilCabProv;
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafMouMotAdaProv objMouMotAdaProv;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private java.util.Date datFecAux;
    private ZafAsiDia objAsiDia, objAsiDiaProv;
    private ZafUtil objUti;
    private mitoolbar objTooBar;
    private ZafThreadGUI objThrGUI;
    private ZafRptSis objRptSis;                                //Reportes del Sistema.
    private ZafVenCon vcoTipDoc;                                //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoCta;                                   //Ventana de consulta "Cuenta".
    private ZafVenCon vcoDep;                                   //Ventana de consulta "Tipo de documento".
    javax.swing.JTable jTblAsiDia = null;
    ZafTblMod zafTblModAsiDia = null;

    private Vector vecAsiDia;    
    private Vector vecCab = new Vector();
    private Vector vecDat, vecDatProv;
    private Vector vecAux;
    Vector vecTipDoc, vecDetDiario;                             //Vector que contiene el codigo de tipos de documentos  
    
    private ArrayList<String> arrLst = null;                    //almacena los rubros del rol de pagos
    private ArrayList<Tbm_traemp> arrLstTbmTraEmp = new ArrayList<Tbm_traemp>();//almacena datos de empleados tbm_traemp
    private ArrayList<String> arrLstAntSue = null;
    private ArrayList<String> arrLstAntBon = null;
    private ArrayList<String> arrLstAntMov = null;
    
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
    private String strNeNumDocAut = "";
    private String strCodDocAut = "";
    
    private int intSig = 1;                                     //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private int intCodEmp, intCodLoc;                           //Código de la empresa y local.
    private int intCanIng = 0;
    private int intCanEgr = 0;
    int intCanColTot = 0;
    private int intCodTipDocProv = 0;
    private int intCodDocProv = 0;

    private double dblProvFonResAdm = 0;
    private double dblProvFonResVen = 0;
    private double dblProvDCSAdm = 0;
    private double dblProvDCSVen = 0;

    private boolean blnHayCam = false;
    private boolean blnEstCarSolHSE = false;
    
    private String strVer = " v1.40 ";
    private double dblSBU = 0;
    private double dblPorApoPatIes = 0;
    private double dblPorFonRes = 0;
    
    /**
     * Creates new form 
     */
    public ZafRecHum37(ZafParSis obj) 
    {
        try 
        {
            this.objZafParSis = (ZafParSis) obj.clone();
            initComponents();

            /*INSERTAR LOS AÑOS EXISTENTES EN TBM_INGEGRMENTRA*/
            cargarAños();

            objUti = new ZafUtil();
            objNotCorEle = new ZafNotCorEle(objZafParSis);  
            
            //Obtener el SBU
            ZafRecHumPar objRecHumPar = new ZafRecHumPar();
            dblSBU = objRecHumPar.getSueBas();
            //Obtener el Porcentaje del Aporte Patronal.
            dblPorApoPatIes = objRecHumPar.getPorApoPat();
            //Obtener el Porcentaje de Fondos de Reserva.
            dblPorFonRes = objRecHumPar.getPorFonRes();
            
            //System.out.println("dblSBU:"+dblSBU+" - dblPorApoPatIes:"+dblPorApoPatIes+" - dblPorFonRes:"+dblPorFonRes);
            
            intCodEmp = objZafParSis.getCodigoEmpresa();
            intCodLoc = objZafParSis.getCodigoLocal();

            objTooBar = new mitoolbar(this);
            this.getContentPane().add(objTooBar, "South");
            
            //objTooBar.setVisibleModificar(false);
            //          if(objZafParSis.getCodigoUsuario()!=1){
            //              butArc.setVisible(false);
            //              objTooBar.agregarSeparador();
            //              objTooBar.agregarBoton(butExp);
            //              panTabGenProv.setVisible(false);
            //              panAsiDiaProv.setVisible(false);
            //          }else{
            //              objTooBar.agregarSeparador();
            //              objTooBar.agregarBoton(butArc);
            //              
            //              objTooBar.agregarSeparador();
            //              objTooBar.agregarBoton(butExp);
            //          }
            
            objTooBar.agregarSeparador();
            objTooBar.agregarBoton(butArc);

            objTooBar.agregarSeparador();
            objTooBar.agregarBoton(butExp);

            objRptSis = new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            panGenCab.add(txtFecDoc);
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
            panAsiDiaRolPag.add(objAsiDia, java.awt.BorderLayout.CENTER);

            objAsiDiaProv = new ZafAsiDia(objZafParSis);
            objAsiDiaProv.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals("")) {
                        objAsiDiaProv.setCodigoTipoDocumento(-1);
                    } else {
                        objAsiDiaProv.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                    }
                }
            });
            panAsiDiaProv.add(objAsiDiaProv, java.awt.BorderLayout.CENTER);

            this.setTitle(objZafParSis.getNombreMenu() + " " + strVer);
            lblTit.setText(objZafParSis.getNombreMenu());
        }
        catch (CloneNotSupportedException e) {   objUti.mostrarMsgErr_F1(this, e);         e.printStackTrace();        } 
        catch (Exception e) {      objUti.mostrarMsgErr_F1(this, e);      e.printStackTrace();        }
    }

    public ZafRecHum37(Librerias.ZafParSis.ZafParSis obj, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strNeNumDoc) 
    {
        try
        {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            this.setTitle(objZafParSis.getNombreMenu() + " " + strVer);
            lblTit.setText(objZafParSis.getNombreMenu());

            strCodEmpAut = strCodEmp;
            strCodLocAut = strCodLoc;
            strCodTipDocAut = strCodTipDoc;
            strCodDocAut = strCodDoc;
            strNeNumDocAut = strNeNumDoc;
            blnEstCarSolHSE = true;

            objUti = new ZafUtil();
            objNotCorEle = new ZafNotCorEle(objZafParSis);  

            objTooBar = new ZafRecHum37.mitoolbar(this);
            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleModificar(false);
            objTooBar.setVisibleAnular(false);
            objTooBar.setVisibleConsultar(false);
            objTooBar.setVisibleImprimir(false);
            objTooBar.setVisibleVistaPreliminar(false);
            objTooBar.setVisible(false);

            objTooBar.setEstado('m');

            String strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText(strFecSis);
            panGenCab.add(txtFecDoc);
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
            panAsiDiaRolPag.add(objAsiDia, java.awt.BorderLayout.CENTER);

            objAsiDiaProv = new ZafAsiDia(objZafParSis);
            objAsiDiaProv.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals("")) {
                        objAsiDiaProv.setCodigoTipoDocumento(-1);
                    } else {
                        objAsiDiaProv.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                    }
                }
            });
            panAsiDiaProv.add(objAsiDiaProv, java.awt.BorderLayout.CENTER);

            tabFrm.remove(3);
            tabFrm.remove(2);
            tabFrm.remove(1);
        } 
        catch (CloneNotSupportedException e) {    objUti.mostrarMsgErr_F1(this, e);  } 
        catch (Exception e) {    e.printStackTrace();  objUti.mostrarMsgErr_F1(this, e);       }
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
            if (con != null) 
            {
                stmLoc = con.createStatement();

                strSQL = "select distinct ne_ani from tbm_ingEgrMenTra order by ne_ani desc";
                rstLoc = stmLoc.executeQuery(strSQL);
                while (rstLoc.next()) 
                {
                    cboPerAAAA.addItem(rstLoc.getString("ne_ani"));
                }
            }
        }
        catch (java.sql.SQLException Evt) {    blnRes = false;  objUti.mostrarMsgErr_F1(this, Evt);     } 
        catch (Exception Evt) {  blnRes = false;    objUti.mostrarMsgErr_F1(this, Evt);        } 
        finally 
        {
            try {   rstLoc.close();  } catch (Throwable ignore) {  }
            try {   stmLoc.close();  } catch (Throwable ignore) {  }
            try {   con.close();     } catch (Throwable ignore) {  }
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
                strSQL ="";
                strSQL+=" SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a2.co_mnu=" + objZafParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
            } 
            else 
            {
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a2.co_mnu=" + objZafParSis.getCodigoMenu();
                strSQL+=" AND a2.co_usr=" + objZafParSis.getCodigoUsuario();
                strSQL+=" ORDER BY a1.tx_desCor";
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
        catch (Exception e) {   blnRes = false;   objUti.mostrarMsgErr_F1(this, e);   }
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
            arlAncCol.add("334");
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
        catch (Exception e) {   blnRes = false;  objUti.mostrarMsgErr_F1(this, e);  }
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
            if (con != null)
            {
                stm = con.createStatement();
                
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objZafParSis.getCodigoUsuario() == 1) 
                {
                    //Armar la sentencia SQL.
                    strSQL = "";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+="    SELECT co_tipDoc";
                    strSQL+="    FROM tbr_tipDocPrg";
                    strSQL+="    WHERE co_emp=" + intCodEmp;
                    strSQL+="    AND co_loc=" + intCodLoc;
                    strSQL+="    AND co_mnu=" + objZafParSis.getCodigoMenu();
                    strSQL+="    AND st_reg='S'";
                    strSQL+=" )";
                    rst = stm.executeQuery(strSQL);
                } 
                else 
                {
                    //Armar la sentencia SQL.
                    strSQL = "";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+="    SELECT co_tipDoc";
                    strSQL+="    FROM tbr_tipDocUsr";
                    strSQL+="    WHERE co_emp=" + intCodEmp;
                    strSQL+="    AND co_loc=" + intCodLoc;
                    strSQL+="    AND co_mnu=" + objZafParSis.getCodigoMenu();
                    strSQL+="    AND co_usr=" + objZafParSis.getCodigoUsuario();
                    strSQL+="    AND st_reg='S'";
                    strSQL+=" )";
                    rst = stm.executeQuery(strSQL);
                }
                if (rst.next()) 
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtNumDoc.setText("" + (rst.getInt("ne_ultDoc") + 1));
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
        } 
        catch (java.sql.SQLException e) {   blnRes = false;  objUti.mostrarMsgErr_F1(this, e);        }
        catch (Exception e) {   blnRes = false;   objUti.mostrarMsgErr_F1(this, e);       }
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
            //Validar que sólo se pueda seleccionar la "Cuenta" si se seleccioná el "Tipo de documento".
            if (txtCodTipDoc.getText().equals("")) 
            {
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                txtDesLarCta.setText("");
                strUbiCta = "";
                strAutCta = "";
                mostrarMsgInf("<HTML>Primero debe seleccionar un <FONT COLOR=\"blue\">Tipo de documento</FONT>.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                txtDesCorTipDoc.requestFocus();
            }
            else 
            {
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta, a1.st_aut, a1.ne_ultnumchq";
                strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" AND a2.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objZafParSis.getCodigoLocal();
                strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" ORDER BY a1.tx_codCta";
                //System.out.println("mostrarVenConCta:" + strSQL);
                //System.out.println("Estado.ToolBar: " + objTooBar.getEstado());

                vcoCta.setSentenciaSQL(strSQL);
                switch (intTipBus) 
                {
                    case 0: //Mostrar la ventana de consulta.
                        vcoCta.setCampoBusqueda(1);
                        vcoCta.show();
                        if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE) 
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                            strUbiCta = vcoCta.getValueAt(4);
                            strAutCta = vcoCta.getValueAt(5);
                        }
                        break;
                    case 1: //Básqueda directa por "Número de cuenta".
                        if (vcoCta.buscar("a1.tx_codCta", txtDesCorCta.getText()))
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                            strUbiCta = vcoCta.getValueAt(4);
                            strAutCta = vcoCta.getValueAt(5);
                        } 
                        else 
                        {
                            vcoCta.setCampoBusqueda(0);
                            vcoCta.setCriterio1(11);
                            vcoCta.cargarDatos();
                            vcoCta.show();
                            if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE)
                            {
                                txtCodCta.setText(vcoCta.getValueAt(1));
                                txtDesCorCta.setText(vcoCta.getValueAt(2));
                                txtDesLarCta.setText(vcoCta.getValueAt(3));
                                strUbiCta = vcoCta.getValueAt(4);
                                strAutCta = vcoCta.getValueAt(5);
                            } 
                            else
                            {
                                txtDesCorCta.setText(strDesCorCta);
                            }
                        }
                        break;
                    case 2: //Búsqueda directa por "Descripcián larga".
                        if (vcoCta.buscar("a1.tx_desLar", txtDesLarCta.getText())) 
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                            strUbiCta = vcoCta.getValueAt(4);
                            strAutCta = vcoCta.getValueAt(5);
                        }
                        else
                        {
                            vcoCta.setCampoBusqueda(1);
                            vcoCta.setCriterio1(11);
                            vcoCta.cargarDatos();
                            vcoCta.show();
                            if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE) 
                            {
                                txtCodCta.setText(vcoCta.getValueAt(1));
                                txtDesCorCta.setText(vcoCta.getValueAt(2));
                                txtDesLarCta.setText(vcoCta.getValueAt(3));
                                strUbiCta = vcoCta.getValueAt(4);
                                strAutCta = vcoCta.getValueAt(5);
                            } 
                            else 
                            {
                                txtDesLarCta.setText(strDesLarCta);
                            }
                        }
                        break;
                }
            }
        } 
        catch (Exception e) {   blnRes = false;    objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }

    public void setEditable(boolean editable) 
    {
        if (editable == true) 
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }
        else
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }

    public void abrirCon()
    {
        try 
        {
            CONN_GLO = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
        }
        catch (java.sql.SQLException Evt) {    objUti.mostrarMsgErr_F1(this, Evt);        }
    }

    public void CerrarCon() 
    {
        try 
        {
            CONN_GLO.close();
            CONN_GLO = null;
        }
        catch (java.sql.SQLException Evt) {  objUti.mostrarMsgErr_F1(this, Evt);        }
    }

    /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTabla(int intSel) 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            //vecDat=new Vector();    //Almacena los datos
            vecCab = new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CODTRA, "Código");
            vecCab.add(INT_TBL_IDTRA, "Cédula");
            vecCab.add(INT_TBL_NOMTRA, "Empleado");
            vecCab.add(INT_TBL_DIAS_LAB, "Días. Lab.");
            vecCab.add(INT_TBL_TOTING, "Ingresos");
            vecCab.add(INT_TBL_TOTEGR, "Egresos");
            vecCab.add(INT_TBL_TOTREC, "Total");

            /*RUBROS INGRESOS DE MANERA DINAMICA*/
            Connection conIns = null;
            Statement stmLoc = null;
            ResultSet rstLoc = null;

            intCanIng = 0;
            intCanEgr = 0;

            try 
            {
                conIns = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());

                if (conIns != null)
                {
                    stmLoc = conIns.createStatement();
                    int intAño = 0;
                    int intMes = 0;
                    //System.out.println(objTooBar.getEstado());

                    if (objTooBar.getEstado() == 'n')  //insertar
                    {
                        strSQL =" select case when min(ne_ani) is null then 0 else min(ne_ani) END as ne_anio ";
                        strSQL+=" from tbm_ingegrmentra ";
                        strSQL+=" where st_rolpaggen is null ";
                        strSQL+=" and co_emp = " + objZafParSis.getCodigoEmpresa();
                        //System.out.println("sql ne_ani : " + strSQL);
                        rstLoc = stmLoc.executeQuery(strSQL);

                        if (rstLoc.next()) 
                        {
                            intAño = rstLoc.getInt("ne_anio");
                        }

                        /**/
                        strSQL =" select case when min(ne_mes) is null then 0 else min(ne_mes) END as ne_meso ";
                        strSQL+=" from tbm_ingegrmentra ";
                        strSQL+=" where st_rolpaggen is null";
                        strSQL+=" and ne_per not in (0) and co_emp = " + objZafParSis.getCodigoEmpresa();
                        //System.out.println("sql ne_mes : " + strSQL);
                        rstLoc = stmLoc.executeQuery(strSQL);

                        if (rstLoc.next())
                        {
                            intMes = rstLoc.getInt("ne_meso");
                        }

                    } 
                    else if (objTooBar.getEstado() == 'c' || objTooBar.getEstado() == 'j') 
                    {
                        intAño = Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
                        intMes = cboPerMes.getSelectedIndex();
                    }

                    arrLst = new ArrayList<String>();
                    strSQL = "";

                    switch (intSel)
                    {
                        case 1: //consulta lo actual
                            if (objZafParSis.getCodigoMenu() == 3138) 
                            {
                                strSQL =" select * from tbm_rubrolpag";
                                strSQL+=" where co_grp = 1";
                                strSQL+=" order by tx_tiprub desc,co_rub asc";
                            } 
                            else 
                            {
                                strSQL+="select * from tbm_rubrolpag";
                                if (txtCodTipDoc.getText().compareTo("199") == 0) 
                                {
                                    strSQL+=" where co_grp = 2";
                                } 
                                else if (txtCodTipDoc.getText().compareTo("202") == 0)
                                {
                                    strSQL+=" where co_grp = 3";
                                } 
                                else
                                {
                                    strSQL+=" where co_grp = 2";
                                }
                                strSQL+=" order by tx_tiprub desc,co_rub asc";
                            }

                            break;

                        case 2: //consulta rol de pagos almacenados
                            strSQL+=" select distinct b.co_rub, b.tx_nom, b.tx_tiprub ";
                            strSQL+=" from tbm_detrolpag a ";
                            strSQL+=" inner join tbm_rubrolpag b on (a.co_rub=b.co_rub)";
                            strSQL+=" where a.co_emp=" + objZafParSis.getCodigoEmpresa();
                            strSQL+=" and a.co_loc=" + objZafParSis.getCodigoLocal();
                            strSQL+=" and a.co_tipdoc=" + txtCodTipDoc.getText().toString();
                            strSQL+=" and a.co_doc=" + txtCodDoc.getText().toString();
                            if (blnEstCarSolHSE) 
                            {
                                // System.out.println("And im loving every second minute hour bigger better stronger power");
                                if (txtCodTipDoc.getText().compareTo("192") == 0) 
                                {
                                    strSQL += " and co_grp = 1";
                                }
                                else if (txtCodTipDoc.getText().compareTo("199") == 0) 
                                {
                                    strSQL += " and co_grp = 2";
                                }
                                else if (txtCodTipDoc.getText().compareTo("202") == 0) 
                                {
                                    strSQL += " and co_grp = 3";
                                }
                            }
                            else 
                            {
                                if (objZafParSis.getCodigoMenu() == 3138) 
                                {
                                    strSQL += " and b.co_grp=1";
                                } 
                                else 
                                {
                                    if (txtCodTipDoc.getText().compareTo("199") == 0) 
                                    {
                                        strSQL += " and co_grp = 2";
                                    }
                                    else if (txtCodTipDoc.getText().compareTo("202") == 0)
                                    {
                                        strSQL += " and co_grp = 3";
                                    } 
                                    else 
                                    {
                                        strSQL += " and co_grp = 2";
                                    }
                                }
                            }

                            strSQL += " order by b.tx_tiprub desc,b.co_rub asc";

                            break;
                    }
                    //System.out.println("consulta rol de pagos almacenados:"+strSQL);
                    
                    stmLoc = conIns.createStatement();
                    rstLoc = stmLoc.executeQuery(strSQL);
                    while (rstLoc.next()) 
                    {
                        vecCab.add(vecCab.size(), rstLoc.getString("tx_nom"));

                        intCanColTot++;
                        if (rstLoc.getString("tx_tiprub").equals("I")) 
                        {
                            intCanIng++;
                        } 
                        else 
                        {
                            intCanEgr++;
                        }
                        arrLst.add(rstLoc.getString("co_rub"));
                    }
                }
            }
            catch (SQLException ex) {     objUti.mostrarMsgErr_F1(this, ex);    return false;     }
            finally 
            {
                try {   stmLoc.close();  } catch (Throwable ignore) {   }
                try {   rstLoc.close();  } catch (Throwable ignore) {   }
                try {   conIns.close();  } catch (Throwable ignore) {   }
            }

            vecCab.add(vecCab.size(), "Ingresos");
            vecCab.add(vecCab.size(), "Egresos");
            vecCab.add(vecCab.size(), "Total");

            //System.out.println("Tamaño de columnas vecCab (todos los empleados deben de tener esta cantidad de columnas): " + vecCab.size());

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);

//            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
//            objTblMod.setColumnDataType(INT_TBL_CUOTA, objTblMod.INT_COL_DBL, new Integer(0), null);
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

//            //cambios realizados a la tabla
//            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_CODTRA).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            objTblCelRenLbl = new ZafTblCelRenLbl();
            //objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);

            tcmAux.getColumn(INT_TBL_DIAS_LAB).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_MIN_SEC_SUG).setCellRenderer(objTblCelRenLbl);
            //objTblCelRenLbl=null;

            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODTRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_IDTRA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_NOMTRA).setPreferredWidth(220);
            tcmAux.getColumn(INT_TBL_DIAS_LAB).setPreferredWidth(70);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_LINEA).setResizable(false);
            tcmAux.getColumn(INT_TBL_CODTRA).setResizable(false);
//            tcmAux.getColumn(INT_TBL_IDTRA).setResizable(false);
            tcmAux.getColumn(INT_TBL_NOMTRA).setResizable(false);
            tcmAux.getColumn(INT_TBL_DIAS_LAB).setResizable(false);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(vecCab.size(), tblDat);
            objTblMod.addSystemHiddenColumn(vecCab.size() - 1, tblDat);
            objTblMod.addSystemHiddenColumn(vecCab.size() - 2, tblDat);
            objTblMod.addSystemHiddenColumn(vecCab.size() - 3, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
//            vecAux=new Vector();
//            vecAux.add("" + INT_TBL_CHKTRA);
//            objTblMod.setColumnasEditables(vecAux);
//            vecAux=null;

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

            int intP = INT_TBL_DIAS_LAB + 1;
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            for (int i = 0; i < 1; i++) 
            {
                for (int x = intP; x < tblDat.getColumnCount(); x++) 
                {
                    objTblMod.setColumnDataType(x + i, objTblMod.INT_COL_DBL, new Integer(0), null);
                    tcmAux.getColumn(x + i).setCellEditor(objTblCelEdiTxt);
                    tcmAux.getColumn(x + i).setCellRenderer(objTblCelRenLbl);
                    tcmAux.getColumn(x + i).setCellEditor(objTblCelEdiTxt);

                    objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                        int intFilSel;//=tblDat.getSelectedRow();

                        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        }

                        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        }
                    });
                }
            }

            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intTblCalTotColIni = INT_TBL_DIAS_LAB + 1;
            int intCol[] = new int[objTblMod.getColumnCount() - INT_TBL_DIAS_LAB - 1];
            int intColPos = 0;
            for (int i = intTblCalTotColIni; i < objTblMod.getColumnCount(); i++) {
                intCol[intColPos] = i;
                intColPos++;
            }
            JTableHeader obj = new JTableHeader();//tony se agregó para evitar null
            tblTot.setTableHeader(obj);
			

            objTblTot = new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);

            //Configurar JTable: Editor de búsqueda.
            objTblBus = new ZafTblBus(tblDat);

            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);
        }
        catch (Exception e) 
        {
            blnRes = false;
            e.printStackTrace();
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
    private boolean configuraTblProv() 
    {
        boolean blnRes = false;

        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            vecCab.add(INT_TBL_PROV_LINEA, "");
            vecCab.add(INT_TBL_PROV_CODTRA, "Código");
            vecCab.add(INT_TBL_PROV_NOMTRA, "Empleado");
            vecCab.add(INT_TBL_PROV_DECIMO_TERCER_SUELDO, "Prov. DTS");
            vecCab.add(INT_TBL_PROV_DECIMO_CUARTO_SUELDO, "Prov. DCS");
            vecCab.add(INT_TBL_PROV_FONDO_RESERVA, "Prov. Fon. Res.");
            vecCab.add(INT_TBL_PROV_VACACIONES, "Prov. Vac.");
            vecCab.add(INT_TBL_PROV_APORTE_PATRONAL, "Apor. Pat.");
            vecCab.add(INT_TBL_PROV_TOTAL_TRA, "Total");

            objTblModProv = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblModProv.setHeader(vecCab);

               //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
//            objTblMod.setColumnDataType(INT_TBL_CUOTA, objTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDatProv.setModel(objTblModProv);
            //Configurar JTable: Establecer tipo de selección.
            tblDatProv.setRowSelectionAllowed(true);
            tblDatProv.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDatProv);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatProv.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDatProv.getColumnModel();

//            //cambios realizados a la tabla
//            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Configurar JTable: Renderizar celdas.
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_PROV_CODTRA).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            objTblCelRenLbl = new ZafTblCelRenLbl();
            //objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_PROV_DECIMO_TERCER_SUELDO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PROV_DECIMO_CUARTO_SUELDO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PROV_FONDO_RESERVA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PROV_VACACIONES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PROV_APORTE_PATRONAL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PROV_TOTAL_TRA).setCellRenderer(objTblCelRenLbl);

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_PROV_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_PROV_CODTRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PROV_NOMTRA).setPreferredWidth(280);
            tcmAux.getColumn(INT_TBL_PROV_DECIMO_TERCER_SUELDO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PROV_DECIMO_CUARTO_SUELDO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PROV_FONDO_RESERVA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PROV_VACACIONES).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PROV_APORTE_PATRONAL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PROV_TOTAL_TRA).setPreferredWidth(60);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatProv.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Ocultar columnas del sistema.
//            objTblMod.addSystemHiddenColumn(vecCab.size()-1, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaProv = new ZafMouMotAdaProv();
            tblDatProv.getTableHeader().addMouseMotionListener(objMouMotAdaProv);
            //Configurar JTable: Establecer columnas editables.
//            vecAux=new Vector();
//            vecAux.add("" + INT_TBL_CHKTRA);
//            objTblMod.setColumnasEditables(vecAux);
//            vecAux=null;

            //Configurar JTable: Editor de la tabla.
            objTblEdi = new ZafTblEdi(tblDatProv);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabProv = new ZafTblFilCab(tblDatProv);

            tcmAux.getColumn(INT_TBL_PROV_LINEA).setCellRenderer(objTblFilCabProv);

            //Configurar JTable: Detectar cambios de valores en las celdas.
            /*objTblModLis=new ZafTblModLis();
             objTblMod.addTableModelListener(objTblModLis);*/
            //cambios realizados a la tabla
            objTblModProv.setModoOperacion(ZafTblMod.INT_TBL_EDI);

            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intColProv[] = new int[6];
            intColProv[0] = 3;
            intColProv[1] = 4;
            intColProv[2] = 5;
            intColProv[3] = 6;
            intColProv[4] = 7;
            intColProv[5] = 8;
            JTableHeader obj = new JTableHeader();//tony se agregó para evitar null
            tblTotProv.setTableHeader(obj);            
			
            objTblTotProv = new ZafTblTot(spnDatProv, spnTotProv, tblDatProv, tblTotProv, intColProv);

            //Configurar JTable: Editor de búsqueda.
            objTblBus = new ZafTblBus(tblDatProv);

        } 
        catch (Exception e) {    blnRes = false;  objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }

    public void Configura_ventana_consulta()
    {
        configurarVenConTipDoc();
        configurarVenConCta();

        if (blnEstCarSolHSE) 
        {
            cargarReg();
            configuraTblProv();
            butArc.setVisible(false);
            butExp.setVisible(false);
        }
        else
        {
            configurarTabla(1);
            configuraTblProv();
            agregarColTblDat();
        }
    }

    private boolean agregarColTblDat()
    {
        int i, intNumFil, intNumColTblDat;
        ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16 * 2);
        ZafTblHeaColGrp objTblHeaColGrpEmp = null;
        boolean blnRes = true;
        int intCanColIng = 0;
        int intCanColEgr = 0;

        try 
        {
            intNumFil = objTblMod.getRowCountTrue();
            intNumColTblDat = objTblMod.getColumnCount();

            for (i = 0; i < 1; i++) 
            {
                objTblHeaColGrpEmp = new ZafTblHeaColGrp("");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CODTRA));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_IDTRA));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_NOMTRA));

                objTblHeaColGrpEmp = new ZafTblHeaColGrp("Totales");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DIAS_LAB));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_TOTING));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_TOTEGR));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_TOTREC));

                objTblHeaColGrpEmp = new ZafTblHeaColGrp("Ingresos");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                intCanColIng = INT_TBL_TOTREC + 1 + intCanIng;
                for (int t = INT_TBL_TOTREC + 1; t < intCanColIng; t++)
                {
                    objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(t + i * INT_TBL_DAT_NUM_TOT_CDI));
                }

                intCanColEgr = intCanColIng + intCanEgr;

                objTblHeaColGrpEmp = new ZafTblHeaColGrp("Egresos");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                for (int t = intCanColIng; t < intCanColEgr; t++) 
                {
                    objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(t + i * INT_TBL_DAT_NUM_TOT_CDI));
                }

                objTblHeaColGrpEmp = new ZafTblHeaColGrp("Totales");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                for (int t = intCanColEgr; t < tblDat.getColumnCount(); t++) 
                {
                    objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(t + i * INT_TBL_DAT_NUM_TOT_CDI));
                }
            }
        } 
        catch (Exception e) {  blnRes = false;  objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        buttonGroup1 = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butBusTipDoc = new javax.swing.JButton();
        lblCta = new javax.swing.JLabel();
        txtCodCta = new javax.swing.JTextField();
        txtDesCorCta = new javax.swing.JTextField();
        txtDesLarCta = new javax.swing.JTextField();
        butCta = new javax.swing.JButton();
        lblPer = new javax.swing.JLabel();
        cboPerAAAA = new javax.swing.JComboBox();
        cboPerMes = new javax.swing.JComboBox();
        cboPer = new javax.swing.JComboBox();
        butPer = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblValDoc = new javax.swing.JLabel();
        txtValDoc = new javax.swing.JTextField();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        butArc = new javax.swing.JButton();
        butExp = new javax.swing.JButton();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panGenObs = new javax.swing.JPanel();
        panLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panTxa = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txtObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txtObs2 = new javax.swing.JTextArea();
        panAsiDiaRolPag = new javax.swing.JPanel();
        panProv = new javax.swing.JPanel();
        panDetProv = new javax.swing.JPanel();
        spnDatProv = new javax.swing.JScrollPane();
        tblDatProv = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnTotProv = new javax.swing.JScrollPane();
        tblTotProv = new javax.swing.JTable();
        panAsiDiaProv = new javax.swing.JPanel();

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

        panTit.setPreferredSize(new java.awt.Dimension(100, 30));

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.PAGE_START);

        panCen.setLayout(new java.awt.BorderLayout());

        tabFrm.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        tabFrm.setPreferredSize(new java.awt.Dimension(115, 100));

        panGen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panGen.setPreferredSize(new java.awt.Dimension(100, 90));
        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(100, 100));
        panGenCab.setLayout(null);

        lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDoc.setText("Tipo de Documento:"); // NOI18N
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(10, 10, 120, 20);

        txtCodTipDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(108, 10, 32, 20);
        //txtCodTipDoc.setVisible(false);

        txtDesCorTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesCorTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(140, 10, 70, 20);

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
        panGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(210, 10, 210, 20);

        butBusTipDoc.setText("jButton1"); // NOI18N
        butBusTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butBusTipDoc);
        butBusTipDoc.setBounds(420, 10, 20, 20);

        lblCta.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCta.setText("Cuenta:"); // NOI18N
        panGenCab.add(lblCta);
        lblCta.setBounds(10, 30, 110, 20);

        txtCodCta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtCodCta);
        txtCodCta.setBounds(108, 30, 32, 20);
        txtCodCta.setVisible(true);

        txtDesCorCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorCtaFocusLost(evt);
            }
        });
        txtDesCorCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorCtaActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesCorCta);
        txtDesCorCta.setBounds(140, 30, 70, 20);

        txtDesLarCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCtaFocusLost(evt);
            }
        });
        txtDesLarCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCtaActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesLarCta);
        txtDesLarCta.setBounds(210, 30, 210, 20);

        butCta.setText("...");
        butCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaActionPerformed(evt);
            }
        });
        panGenCab.add(butCta);
        butCta.setBounds(420, 30, 20, 20);

        lblPer.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblPer.setText("Período:"); // NOI18N
        panGenCab.add(lblPer);
        lblPer.setBounds(10, 50, 110, 20);

        cboPerAAAA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Año" }));
        panGenCab.add(cboPerAAAA);
        cboPerAAAA.setBounds(140, 50, 70, 20);

        cboPerMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mes", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        panGenCab.add(cboPerMes);
        cboPerMes.setBounds(210, 50, 100, 20);

        cboPer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Período", "Primera quincena", "Fin de mes" }));
        panGenCab.add(cboPer);
        cboPer.setBounds(310, 50, 110, 20);

        butPer.setText(".."); // NOI18N
        panGenCab.add(butPer);
        butPer.setBounds(420, 50, 20, 20);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecDoc.setText("Fecha del Documento:"); // NOI18N
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(450, 10, 130, 20);

        lblNumDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc.setText("Número de Documento:"); // NOI18N
        panGenCab.add(lblNumDoc);
        lblNumDoc.setBounds(450, 30, 140, 20);

        txtNumDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDocFocusGained(evt);
            }
        });
        panGenCab.add(txtNumDoc);
        txtNumDoc.setBounds(590, 30, 80, 20);

        lblValDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblValDoc.setText("Valor del Documento:"); // NOI18N
        panGenCab.add(lblValDoc);
        lblValDoc.setBounds(450, 50, 140, 20);

        txtValDoc.setBackground(objZafParSis.getColorCamposSistema());
        txtValDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValDoc.setText("0.0");
        panGenCab.add(txtValDoc);
        txtValDoc.setBounds(590, 50, 80, 20);

        lblCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodDoc.setText("Código del Documento:"); // NOI18N
        panGenCab.add(lblCodDoc);
        lblCodDoc.setBounds(450, 70, 140, 20);

        txtCodDoc.setEditable(false);
        txtCodDoc.setBackground(objZafParSis.getColorCamposSistema());
        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(590, 70, 80, 20);

        butArc.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        butArc.setText("Generar Archivo");
        butArc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butArcActionPerformed(evt);
            }
        });
        panGenCab.add(butArc);
        butArc.setBounds(270, 70, 140, 30);
        butArc.setEnabled(false);

        butExp.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        butExp.setText("Exportar");
        butExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExpActionPerformed(evt);
            }
        });
        panGenCab.add(butExp);
        butExp.setBounds(390, 70, 140, 30);
        //butExp.setVisible(false);

        panGen.add(panGenCab, java.awt.BorderLayout.PAGE_START);

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
        spnDat.setViewportView(tblDat);

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

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

        panGenDet.add(spnTot, java.awt.BorderLayout.SOUTH);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        panGenObs.setPreferredSize(new java.awt.Dimension(100, 80));
        panGenObs.setLayout(new java.awt.BorderLayout());

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

        panGenObs.add(panLbl, java.awt.BorderLayout.WEST);

        panTxa.setLayout(new java.awt.GridLayout(2, 1, 0, 1));

        txtObs1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtObs1.setLineWrap(true);
        spnObs1.setViewportView(txtObs1);

        panTxa.add(spnObs1);

        txtObs2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtObs2.setLineWrap(true);
        spnObs2.setViewportView(txtObs2);

        panTxa.add(spnObs2);

        panGenObs.add(panTxa, java.awt.BorderLayout.CENTER);

        panGen.add(panGenObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panAsiDiaRolPag.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de diario", panAsiDiaRolPag);

        panProv.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panProv.setPreferredSize(new java.awt.Dimension(100, 90));
        panProv.setLayout(new java.awt.BorderLayout());

        panDetProv.setLayout(new java.awt.BorderLayout());

        tblDatProv.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDatProv.setViewportView(tblDatProv);

        panDetProv.add(spnDatProv, java.awt.BorderLayout.CENTER);

        spnTotProv.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTotProv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTotProv.setViewportView(tblTotProv);

        panDetProv.add(spnTotProv, java.awt.BorderLayout.SOUTH);

        panProv.add(panDetProv, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Provisiones", panProv);

        panAsiDiaProv.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de Provisiones", panAsiDiaProv);

        panCen.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    //<editor-fold defaultstate="collapsed" desc="/* Eventos */">
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


private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
    Configura_ventana_consulta();
}//GEN-LAST:event_formInternalFrameOpened

private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
    String strMsg = "¿Está Seguro que desea cerrar este programa?";
    javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
    String strTit;
    strTit = "Mensaje del sistema Zafiro";
    if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == 0) 
    {
        if (rstCab != null) 
        {
            rstCab = null;
        }
        if (STM_GLO != null) 
        {
            STM_GLO = null;
        }
        if (con != null) 
        {
            con = null;
        }

        System.gc();
        dispose();
    }
}//GEN-LAST:event_formInternalFrameClosing

    private void txtNumDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusGained
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

    private void butArcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butArcActionPerformed
        try 
        {
            String strArc = directorioArchivo();
            if (strArc == null)
            {
                mostrarMsgErr("NO SE PUDO GENERAR EL ARCHIVO");
            } else {

                if (generarArchivoRolPago(strArc)) {
                    mostrarMsgInf("Archivo generado correctamente");
                } else {
                    mostrarMsgInf("Archivo no fue generado correctamente");
                }
            }
        } 
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(ZafRecHum37.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) 
        {
            Logger.getLogger(ZafRecHum37.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_butArcActionPerformed
  
    private void butExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpActionPerformed
        try 
        {
            ZafVenFun zafVenFun = new ZafVenFun(objZafParSis, objUti, this);
            FileNameExtensionFilter objFilNamExt = new FileNameExtensionFilter("Archivos XLS", "xls");
            String strArc = zafVenFun.directorioArchivo(objFilNamExt);
            String strTitle = "";
            if (txtCodTipDoc.getText().compareTo("192") == 0)
            {
                strTitle = "ROL DE PAGOS " + cboPerMes.getSelectedItem().toString().toUpperCase() + cboPerAAAA.getSelectedItem().toString().toUpperCase();
            } 
            else if (txtCodTipDoc.getText().compareTo("199") == 0) 
            {
                strTitle = "PRESTAMOS FUNCIONARIOS " + cboPerMes.getSelectedItem().toString().toUpperCase() + cboPerAAAA.getSelectedItem().toString().toUpperCase();
            } 
            else if (txtCodTipDoc.getText().compareTo("202") == 0) 
            {
                strTitle = "REEMBOLSO POR GASTOS " + cboPerMes.getSelectedItem().toString().toUpperCase() + cboPerAAAA.getSelectedItem().toString().toUpperCase();
            }
            ExcelTableExporter excelExporter = new ExcelTableExporter(tblDat, new File(strArc), strTitle, arrLst);
            if (excelExporter.export())
            {
                JOptionPane.showMessageDialog(null, "Archivo generado correctamente");
                Process p = Runtime.getRuntime().exec("cmd /c start " + strArc);
            }
        } 
        catch (Exception ex)
        {
            objUti.mostrarMsgErr_F1(this, ex);
            //            Logger.getLogger(ZafRecHum37.class.getName()).log(Level.SEVERE, null, ex);
        }

        //        ZafVenFun zafVenFun = new ZafVenFun(objZafParSis, objUti, this);
        //        FileNameExtensionFilter objFilNamExt=new FileNameExtensionFilter("Archivos XLS", "xls");
        //        String strArc = zafVenFun.directorioArchivo(objFilNamExt);
        //        ExcelTableExporter excelExporter = new ExcelTableExporter 
        //        (tblDat, new File(strArc),"ROL ", arrLst); 
        //        if (excelExporter.export()) { 
        //        JOptionPane.showMessageDialog(null, "Archivo generado correctamente"); 
        //            
        //                Process p = Runtime.getRuntime().exec("cmd /c start "+strArc);
        //            } catch (IOException ex) {
        //                Logger.getLogger(ZafRecHum37.class.getName()).log(Level.SEVERE, null, ex);
        //            }

    }//GEN-LAST:event_butExpActionPerformed
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* Variables declaration - do not modify */">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butArc;
    private javax.swing.JButton butBusTipDoc;
    private javax.swing.JButton butCta;
    private javax.swing.JButton butExp;
    private javax.swing.JButton butPer;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboPer;
    private javax.swing.JComboBox cboPerAAAA;
    private javax.swing.JComboBox cboPerMes;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblCta;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPer;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValDoc;
    private javax.swing.JPanel panAsiDiaProv;
    private javax.swing.JPanel panAsiDiaRolPag;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panDetProv;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenObs;
    private javax.swing.JPanel panLbl;
    private javax.swing.JPanel panProv;
    private javax.swing.JPanel panTit;
    private javax.swing.JPanel panTxa;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnDatProv;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JScrollPane spnTotProv;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDatProv;
    private javax.swing.JTable tblTot;
    private javax.swing.JTable tblTotProv;
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

    //</editor-fold>
    
    public void BuscarDep(String campo, String strBusqueda, int tipo)
    {
        vcoDep.setTitle("Listado de Departamentos");
        if (vcoDep.buscar(campo, strBusqueda)) 
        {
            txtDesLarCta.setText(vcoDep.getValueAt(3));
        } 
        else 
        {
            vcoDep.setCampoBusqueda(tipo);
            vcoDep.cargarDatos();
            vcoDep.show();
            if (vcoDep.getSelectedButton() == vcoDep.INT_BUT_ACE) 
            {
                txtDesLarCta.setText(vcoDep.getValueAt(3));
            }
            else 
            {
                txtDesLarCta.setText(strDesDep);
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
    private boolean mostrarVenConTipDoc(int intTipBus) 
    {
        boolean blnRes = true;
        try
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.setVisible(true);
                    if (vcoTipDoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
                    {
                        if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7)))) 
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado() == 'n')
                            {
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
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText())) 
                    {
                        if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7))))
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado() == 'n') 
                            {
                                strAux = vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux) ? Integer.parseInt(strAux) + 1 : 1));
                            }
                            intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        }
                        else 
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    } 
                    else 
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE)
                        {
                            if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7)))) 
                            {
                                txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                                txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                                txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                                if (objTooBar.getEstado() == 'n')
                                {
                                    strAux = vcoTipDoc.getValueAt(4);
                                    txtNumDoc.setText("" + (objUti.isNumero(strAux) ? Integer.parseInt(strAux) + 1 : 1));
                                }
                                intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                                txtNumDoc.selectAll();
                                txtNumDoc.requestFocus();
                            } 
                            else
                            {
                                txtDesCorTipDoc.setText(strDesCorTipDoc);
                            }
                        }
                        else 
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText())) 
                    {
                        if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7)))) 
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado() == 'n')
                            {
                                strAux = vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux) ? Integer.parseInt(strAux) + 1 : 1));
                            }
                            intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    else 
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
                        {
                            if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7)))) 
                            {
                                txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                                txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                                txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                                if (objTooBar.getEstado() == 'n') 
                                {
                                    strAux = vcoTipDoc.getValueAt(4);
                                    txtNumDoc.setText("" + (objUti.isNumero(strAux) ? Integer.parseInt(strAux) + 1 : 1));
                                }
                                intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                                txtNumDoc.selectAll();
                                txtNumDoc.requestFocus();
                            }
                            else 
                            {
                                txtDesLarTipDoc.setText(strDesLarTipDoc);
                            }
                        }
                        else 
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
            }
        } 
        catch (Exception e) {  blnRes = false;  objUti.mostrarMsgErr_F1(this, e);  }

        if (objZafParSis.getCodigoMenu() != 3138) 
        {
            if (objTooBar.getEstado() == 'n') 
            {
                strCodTipDoc = txtCodTipDoc.getText();
                //System.out.println("codtipdoc: " + strCodTipDoc);
                procesoInsertarRolBonMov(strCodTipDoc);
            }
        }

        return blnRes;
    }

    private boolean procesoInsertarRolBonMov(String strCodTipDoc) 
    {
        boolean blnRes = false;

        try 
        {
            java.awt.Color colBack;
            colBack = txtValDoc.getBackground();
            txtValDoc.setEditable(false);
            txtValDoc.setBackground(colBack);
            txtCodDoc.setEditable(false);
            txtCodDoc.setBackground(colBack);

            datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
            java.util.Date dateObj = datFecAux;
            java.util.Calendar calObj = java.util.Calendar.getInstance();
            calObj.setTime(dateObj);
            txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                    calObj.get(java.util.Calendar.MONTH) + 1,
                    calObj.get(java.util.Calendar.YEAR));

            /*CARGAR REGISTROS ROLES*/
            if (cargarRolPer()) 
            {
                if (generaAsiento()) 
                {
                    //  if(blnProv){
                    if (objAsiDia.isDiarioCuadrado())
                    {
                        boolean blnProv = false;
                        if (cboPer.getSelectedIndex() == 2) 
                        {
                            blnProv = generaAsientoProvisiones();
                            panAsiDiaProv.setVisible(Boolean.FALSE);
                        }
                        
                        //  if (objZafParSis.getCodigoUsuario() == 1) {
                        //      butArc.setEnabled(true);
                        //      butExp.setEnabled(true);
                        //  } else {
                        //      butExp.setEnabled(true);
                        //  }
                        //  butArc.setVisible(true);
                        
                        butArc.setEnabled(true);
                        // butExp.setEnabled(true);

                    }
                    else 
                    {
                        llamarManCtaCon(String.valueOf(objZafParSis.getCodigoEmpresa()),
                                String.valueOf(cboPerAAAA.getSelectedItem()),
                                String.valueOf(cboPerMes.getSelectedIndex()),
                                String.valueOf(cboPer.getSelectedIndex())
                        );
                    }
                 //               }
                }
            }

            if (rstCab != null) 
            {
                rstCab.close();
                rstCab = null;
            }

            txtFecDoc.setEnabled(true);
            objAsiDia.setEditable(true);
            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);
            objAsiDia.setDiarioModificado(false);
            blnHayCam = false;
            objTooBar.setEnabledVistaPreliminar(true);
            blnRes = true;

        }
        catch (Exception e) {    e.printStackTrace();  objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }

    private void llamarManCtaCon(String strCodEmp, String strNeAni, String strNeMes, String strNePer) 
    {
        RecursosHumanos.ZafRecHum37.ZafRecHum37_01 obj1 = new RecursosHumanos.ZafRecHum37.ZafRecHum37_01(objZafParSis, strCodEmp, strNeAni, strNeMes, strNePer);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }

    //<editor-fold defaultstate="collapsed" desc="/* Botón - Generar Archivo */">
    private String directorioArchivo()
    {
        boolean blnRes = true;
        String strArc = null;
        try 
        {
            JFileChooser objFilCho = new JFileChooser();
            objFilCho.setDialogTitle("Guardar");
            objFilCho.setFileSelectionMode(JFileChooser.FILES_ONLY);
            objFilCho.setCurrentDirectory(new File("C:\\"));
            //Filtrado (Opción 1):
            FileNameExtensionFilter objFilNamExt = new FileNameExtensionFilter("Archivos TXT", "txt");
            objFilCho.setFileFilter(objFilNamExt);
            //Filtrado (Opción 2):
            //            objFilCho.addChoosableFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
            //            objFilCho.addChoosableFileFilter(new FileNameExtensionFilter("Archivos java", "java"));
            //            objFilCho.addChoosableFileFilter(new FileNameExtensionFilter("Archivos php", "php", "php4", "phtml"));
            if (objFilCho.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
            {
                strArc = objFilCho.getSelectedFile().getPath();
                //Si no tiene la extensión "txt" agregarsela.
                if (!strArc.toLowerCase().endsWith(".txt")) 
                {
                    strArc += ".txt";
                }
            } 
            else 
            {
                //System.out.println("El usuario canceló");
            }
        } 
        catch (Exception e) 
        {
            System.out.println("Excepción: " + e.toString());
            blnRes = false;
        }
        return strArc;
    }
    
    private boolean generarArchivoRolPago(String strArc) throws FileNotFoundException, IOException 
    {
        boolean blnRes = true;
        File archivoSueldos = null;
        File archivoBonos = null;
        File archivoMovilizacion = null;

        File archivoSueldosSinCtaBan = null;
        File archivoBonosSinCtaBan = null;
        File archivoMovilizacionSinCtaBan = null;

        String strTab = "	";

        String strCodigoOrientacion = "PA";
        String strContraPartida = "";      //1ERA QUINC MAY 2012;

        if (cboPer.getSelectedIndex() == 1) 
        {
            strContraPartida += "1ERA QUINC ";
        } 
        else if (cboPer.getSelectedIndex() == 2)
        {
            strContraPartida += "2DA QUINC ";
        }

        if (cboPerMes.getSelectedIndex() > 0) 
        {
            strContraPartida += cboPerMes.getSelectedItem().toString().substring(0, 3).toUpperCase() + " ";
        }

        if (cboPerAAAA.getSelectedIndex() > 0) 
        {
            strContraPartida += cboPerAAAA.getSelectedItem().toString();
        }

        String strMoneda = "USD";
        String strValor = "";
        String strFormaPago = "CTA";
        String strTipoCuenta = "";
        String strNumeroCuenta = "";
        String strReferencia = strContraPartida;
        String strTipoIDBeneficiario = "C";
        String strNumeroIDClienteBeneficiario = "";   //cedula
        String strNombreBeneficiario = "";
        String strCodigoInstitucionFinanciera = "32";

        try 
        {
            String slash = File.separator;
            String strEscArc = "";
            
            //Prepara Archivo
            SimpleDateFormat fmt = new SimpleDateFormat("dd_MMM_yyyy");
            String strFec = fmt.format(new java.util.Date());

            String strQFDM = "";
            String strNomEmp = objZafParSis.getNombreEmpresa();
            String strMes = cboPerMes.getSelectedItem().toString().toUpperCase();
            String strAni = cboPerAAAA.getSelectedItem().toString();

            if (cboPer.getSelectedItem().toString().equals("Primera quincena"))
            {
                strQFDM = "1ERA QUINC";
            } 
            else if (cboPer.getSelectedItem().toString().equals("Fin de mes")) 
            {
                strQFDM = "2DA QUINC";
            }

            //String strRutArcLogSueldos = "/home/sistemas2/NetBeansProjects/Zafiro_escritorio/Zafiro/src/RecursosHumanos/ZafRecHum37/CASH MANAGEMENT/"+strNomEmp+"/"+"ROLPAG_SUELDOS " + strNomEmp+" - "+strQFDM + " " + strMes + " " + strAni + ".txt";
            //String strRutArcLogBonos = "/home/sistemas2/NetBeansProjects/Zafiro_escritorio/Zafiro/src/RecursosHumanos/ZafRecHum37/CASH MANAGEMENT/"+strNomEmp+"/"+"ROLPAG_BONOS " + strNomEmp+" - "+strQFDM + " " + strMes + " " + strAni + ".txt";
            //String strRutArcLogMovilizacion = "/home/sistemas2/NetBeansProjects/Zafiro_escritorio/Zafiro/src/RecursosHumanos/ZafRecHum37/CASH MANAGEMENT/"+strNomEmp+"/"+"ROLPAG_MOVILIZACION " + strNomEmp+" - "+strQFDM + " " + strMes + " " + strAni + ".txt";
                        
            String strRutArcLogSueldosSinCtaBan = "/home/sistemas2/NetBeansProjects/Zafiro_escritorio/Zafiro/src/RecursosHumanos/ZafRecHum37/CASH MANAGEMENT/" + "ROLPAG_SUELDOS_SIN_CTABAN " + objZafParSis.getNombreEmpresa() + " - " + strFec + ".txt";
            String strRutArcLogBonosSinCtaBan = "/home/sistemas2/NetBeansProjects/Zafiro_escritorio/Zafiro/src/RecursosHumanos/ZafRecHum37/CASH MANAGEMENT/" + "ROLPAG_BONOS_SIN_CTABAN " + objZafParSis.getNombreEmpresa() + " - " + strFec + ".txt";
            String strRutArcLogMovilizacionSinCtaBan = "/home/sistemas2/NetBeansProjects/Zafiro_escritorio/Zafiro/src/RecursosHumanos/ZafRecHum37/CASH MANAGEMENT/" + "ROLPAG_MOVILIZACION_SIN_CTABAN " + objZafParSis.getNombreEmpresa() + " - " + strFec + ".txt";
            
            if (!System.getProperty("os.name").equals("Linux")) 
            {
                // strRutArcLogSueldos = "C:"+slash+"Zafiro"+slash+"Temp";
                // strRutArcLogBonos = "C:"+slash+strRutArcLogBonos;
                // strRutArcLogSueldos = "C:/Zafiro/Temp/RecursosHumanos/ZafRecHum37/CASH MANAGEMENT/"+strNomEmp+"/"+"ROLPAG_SUELDOS " + strNomEmp+" - "+strQFDM + " " + strMes + " " + strAni + ".txt";
                // strRutArcLogBonos = "C:/Zafiro/Temp/RecursosHumanos/ZafRecHum37/CASH MANAGEMENT/"+strNomEmp+"/"+"ROLPAG_BONOS " + strNomEmp+" - "+strQFDM + " " + strMes + " " + strAni + ".txt";
                // strRutArcLogMovilizacion="C:/Zafiro/Temp/RecursosHumanos/ZafRecHum37/CASH MANAGEMENT/"+strNomEmp+"/"+"ROLPAG_MOVILIZACION " + strNomEmp+" - "+strQFDM + " " + strMes + " " + strAni + ".txt";

                strRutArcLogSueldosSinCtaBan = "C:/Zafiro/Temp/RecursosHumanos/ZafRecHum37/CASH MANAGEMENT/" + "ROLPAG_SUELDOS_SIN_CTABAN " + objZafParSis.getNombreEmpresa() + " - " + strFec + ".txt";
                strRutArcLogBonosSinCtaBan = "C:/Zafiro/Temp/RecursosHumanos/ZafRecHum37/CASH MANAGEMENT/" + "ROLPAG_BONOS_SIN_CTABAN " + objZafParSis.getNombreEmpresa() + " - " + strFec + ".txt";
                strRutArcLogMovilizacionSinCtaBan = "C:/Zafiro/Temp/RecursosHumanos/ZafRecHum37/CASH MANAGEMENT/" + "ROLPAG_MOVILIZACION_SIN_CTABAN " + objZafParSis.getNombreEmpresa() + " - " + strFec + ".txt";
            }

            archivoSueldos = new File(strArc);
            //File dirSueldos = new File(strRutArcLogSueldos);

            archivoBonos = new File(strArc);
            //File dirBonos = new File(strRutArcLogBonos);

            archivoSueldosSinCtaBan = new File(strRutArcLogSueldosSinCtaBan);
            archivoBonosSinCtaBan = new File(strRutArcLogBonosSinCtaBan);

            archivoMovilizacion = new File(strArc);
            archivoMovilizacionSinCtaBan = new File(strRutArcLogMovilizacionSinCtaBan);

            //**aumentar coincidencia de codigos de trabajo
            if (objZafParSis.getCodigoMenu() == 3138)
            {
                archivoSueldos.delete();
                archivoSueldosSinCtaBan.delete();
                ArchivosTuval logSueldos = new ArchivosTuval(strArc);
                ArchivosTuval logSueldosSinCtaBan = new ArchivosTuval(strRutArcLogSueldosSinCtaBan);

                System.out.println("-------------------------------------------------------------------ROL DE PAGOS SUELDOS-------------------------------------------------------------------");

                for (Iterator<Tbm_traemp> itLstTraEmp = arrLstTbmTraEmp.iterator(); itLstTraEmp.hasNext();) 
                {
                    //  String strLog="";
                    String strLog = new String("windows-1252");
                    Tbm_traemp tbm_traemp = itLstTraEmp.next();
                    int intCodTra = tbm_traemp.getIntCo_tra();
                    strTipoCuenta = tbm_traemp.getStrTx_TipCtaBan();
                    strNumeroCuenta = tbm_traemp.getStrTx_NumCtaBan();

                    for (int intFilRec = 0; intFilRec < objTblMod.getRowCount(); intFilRec++) 
                    {
                        if (intCodTra == Integer.parseInt(objTblMod.getValueAt(intFilRec, INT_TBL_CODTRA).toString()))
                        {
                            if (validaArchivo(strTipoCuenta, strNumeroCuenta)) 
                            {
                                if (strTipoCuenta.equals("A")) 
                                {
                                    strTipoCuenta = "AHO";
                                } 
                                else
                                {
                                    strTipoCuenta = "CTE";
                                }

                                strNumeroIDClienteBeneficiario = tbm_traemp.getStrTx_Ide();
                                //strValor=objUti.parseString(objTblMod.getValueAt(intFil, objTblMod.getColumnCount()-1).toString());
                                //strValor=objUti.parseString(objTblMod.getValueAt(intFilRec, objTblMod.getColumnCount()-2).toString());
                                strValor = objUti.parseString(objTblMod.getValueAt(intFilRec, objTblMod.getColumnCount() - 1).toString());

                                strValor = retornaValorStr(strValor);

                                strNombreBeneficiario = tbm_traemp.getStrEmpleado();

                                if (Double.parseDouble(strValor) > 0) 
                                {
                                    strLog = strCodigoOrientacion + strTab + strContraPartida + strTab + strMoneda + strTab + strValor + strTab + strFormaPago + strTab + strTipoCuenta + strTab;
                                    strLog += strNumeroCuenta + strTab + strReferencia + strTab + strTipoIDBeneficiario + strTab + strNumeroIDClienteBeneficiario + strTab;
                                    strLog += strNombreBeneficiario + strTab + strCodigoInstitucionFinanciera;
                                    //System.out.println(strLog);
                                    logSueldos.println(strLog);
                                    //System.Text.Encoding.Default
                                }

                            //intFil++;
                            }
                            else 
                            {
                                strTipoCuenta = "XXX";
                                strNumeroCuenta = "XXX";

                                strNumeroIDClienteBeneficiario = tbm_traemp.getStrTx_Ide();
                                //strValor=objUti.parseString(objTblMod.getValueAt(intFil, objTblMod.getColumnCount()-1).toString());
                                strValor = objUti.parseString(objTblMod.getValueAt(intFilRec, objTblMod.getColumnCount() - 2).toString());

                                strValor = retornaValorStr(strValor);

                                strNombreBeneficiario = tbm_traemp.getStrEmpleado();

                                if (Double.parseDouble(strValor) > 0) 
                                {
                                    strLog = strCodigoOrientacion + strTab + strContraPartida + strTab + strMoneda + strTab + strValor + strTab + strFormaPago + strTab + strTipoCuenta + strTab;
                                    strLog += strNumeroCuenta + strTab + strReferencia + strTab + strTipoIDBeneficiario + strTab + strNumeroIDClienteBeneficiario + strTab;
                                    strLog += strNombreBeneficiario + strTab + strCodigoInstitucionFinanciera;
                                    //System.out.println(strLog);
                                    logSueldosSinCtaBan.println(strLog);
                                }

                                intFilRec = objTblMod.getRowCount();

                            }
                        }
                    }
                }
                logSueldos = null;
                logSueldosSinCtaBan = null;
            }
            else 
            {
                //BONOS
                if (txtCodTipDoc.getText().compareTo("199") == 0) 
                {
                    archivoBonos.delete();
                    archivoBonosSinCtaBan.delete();
                    ArchivosTuval logBonos = new ArchivosTuval(strArc);
                    ArchivosTuval logBonosSinCtaBan = new ArchivosTuval(strRutArcLogBonosSinCtaBan);

                    System.out.println("--------------------------------------------------------------------ROL DE BONOS--------------------------------------------------------------------");

                    for (Iterator<Tbm_traemp> itLstTraEmp = arrLstTbmTraEmp.iterator(); itLstTraEmp.hasNext();) 
                    {
                        String strLog = "";
                        Tbm_traemp tbm_traemp = itLstTraEmp.next();
                        int intCodTra = tbm_traemp.getIntCo_tra();
                        strTipoCuenta = tbm_traemp.getStrTx_TipCtaBan();
                        strNumeroCuenta = tbm_traemp.getStrTx_NumCtaBan();

                        for (int intFilRec = 0; intFilRec < objTblMod.getRowCount(); intFilRec++)
                        {
                            if (intCodTra == Integer.parseInt(objTblMod.getValueAt(intFilRec, INT_TBL_CODTRA).toString()))
                            {
                                if (validaArchivo(strTipoCuenta, strNumeroCuenta)) 
                                {
                                    if (strTipoCuenta.equals("A")) 
                                    {
                                        strTipoCuenta = "AHO";
                                    }
                                    else 
                                    {
                                        strTipoCuenta = "CTE";
                                    }

                                    strNumeroIDClienteBeneficiario = tbm_traemp.getStrTx_Ide();
                                    strValor = objUti.parseString(objTblMod.getValueAt(intFilRec, objTblMod.getColumnCount() - 1).toString());

                                    strValor = retornaValorStr(strValor);

                                    strNombreBeneficiario = tbm_traemp.getStrEmpleado();

                                    if (Double.parseDouble(strValor) > 0) 
                                    {
                                        strLog = strCodigoOrientacion + strTab + strContraPartida + strTab + strMoneda + strTab + strValor + strTab + strFormaPago + strTab + strTipoCuenta + strTab;
                                        strLog += strNumeroCuenta + strTab + strReferencia + strTab + strTipoIDBeneficiario + strTab + strNumeroIDClienteBeneficiario + strTab;
                                        strLog += strNombreBeneficiario + strTab + strCodigoInstitucionFinanciera;
                                        //System.out.println(strLog);
                                        logBonos.println(strLog);
                                    }
                                }
                                else
                                {
                                    strTipoCuenta = "XXX";
                                    strNumeroCuenta = "XXX";

                                    strNumeroIDClienteBeneficiario = tbm_traemp.getStrTx_Ide();
                                    strValor = objUti.parseString(objTblMod.getValueAt(intFilRec, objTblMod.getColumnCount() - 1).toString());

                                    strValor = retornaValorStr(strValor);

                                    strNombreBeneficiario = tbm_traemp.getStrEmpleado();

                                    if (Double.parseDouble(strValor) > 0) 
                                    {
                                        strLog = strCodigoOrientacion + strTab + strContraPartida + strTab + strMoneda + strTab + strValor + strTab + strFormaPago + strTab + strTipoCuenta + strTab;
                                        strLog += strNumeroCuenta + strTab + strReferencia + strTab + strTipoIDBeneficiario + strTab + strNumeroIDClienteBeneficiario + strTab;
                                        strLog += strNombreBeneficiario + strTab + strCodigoInstitucionFinanciera;
                                        //System.out.println(strLog);
                                        logBonosSinCtaBan.println(strLog);
                                    }
                                }
                            }
                        }
                    }
                    logBonos = null;
                    logBonosSinCtaBan = null;
                }
                else if (txtCodTipDoc.getText().compareTo("202") == 0) 
                {
                    //System.out.println("archivo mov eliminado");
                    archivoMovilizacion.delete();
                    archivoMovilizacionSinCtaBan.delete();

//                    ArchivosTuval logMovilizacion = null;
//                    ArchivosTuval logMovilizacionSinCtaBan = null;
                    ArchivosTuval logMovilizacion = new ArchivosTuval(strArc);
                    ArchivosTuval logMovilizacionSinCtaBan = new ArchivosTuval(strRutArcLogMovilizacionSinCtaBan);

                    System.out.println("--------------------------------------------------------------------ROL DE MOVILIZACION--------------------------------------------------------------------");

                    for (Iterator<Tbm_traemp> itLstTraEmp = arrLstTbmTraEmp.iterator(); itLstTraEmp.hasNext();) 
                    {
                        String strLog = "";
                        Tbm_traemp tbm_traemp = itLstTraEmp.next();
                        int intCodTra = tbm_traemp.getIntCo_tra();
                        strTipoCuenta = tbm_traemp.getStrTx_TipCtaBan();
                        strNumeroCuenta = tbm_traemp.getStrTx_NumCtaBan();

                        for (int intFilRec = 0; intFilRec < objTblMod.getRowCount(); intFilRec++) 
                        {
                            if (intCodTra == Integer.parseInt(objTblMod.getValueAt(intFilRec, INT_TBL_CODTRA).toString())) 
                            {
                                if (validaArchivo(strTipoCuenta, strNumeroCuenta)) 
                                {
                                    if (strTipoCuenta.equals("A")) 
                                    {
                                        strTipoCuenta = "AHO";
                                    }
                                    else
                                    {
                                        strTipoCuenta = "CTE";
                                    }

                                    strNumeroIDClienteBeneficiario = tbm_traemp.getStrTx_Ide();
                                    strValor = objUti.parseString(objTblMod.getValueAt(intFilRec, objTblMod.getColumnCount() - 1).toString());

                                    strValor = retornaValorStr(strValor);

                                    strNombreBeneficiario = tbm_traemp.getStrEmpleado();

                                    if (Double.parseDouble(strValor) > 0) 
                                    {
                                        strLog = strCodigoOrientacion + strTab + strContraPartida + strTab + strMoneda + strTab + strValor + strTab + strFormaPago + strTab + strTipoCuenta + strTab;
                                        strLog += strNumeroCuenta + strTab + strReferencia + strTab + strTipoIDBeneficiario + strTab + strNumeroIDClienteBeneficiario + strTab;
                                        strLog += strNombreBeneficiario + strTab + strCodigoInstitucionFinanciera;
                                        //System.out.println(strLog);
                                        logMovilizacion.println(strLog);
                                    }
                                } 
                                else 
                                {
                                    strTipoCuenta = "XXX";
                                    strNumeroCuenta = "XXX";

                                    strNumeroIDClienteBeneficiario = tbm_traemp.getStrTx_Ide();
                                    strValor = objUti.parseString(objTblMod.getValueAt(intFilRec, objTblMod.getColumnCount() - 1).toString());

                                    strValor = retornaValorStr(strValor);

                                    strNombreBeneficiario = tbm_traemp.getStrEmpleado();

                                    if (Double.parseDouble(strValor) > 0) 
                                    {
                                        strLog = strCodigoOrientacion + strTab + strContraPartida + strTab + strMoneda + strTab + strValor + strTab + strFormaPago + strTab + strTipoCuenta + strTab;
                                        strLog += strNumeroCuenta + strTab + strReferencia + strTab + strTipoIDBeneficiario + strTab + strNumeroIDClienteBeneficiario + strTab;
                                        strLog += strNombreBeneficiario + strTab + strCodigoInstitucionFinanciera;
                                        //System.out.println(strLog);
                                        logMovilizacionSinCtaBan.println(strLog);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } 
        catch (Exception e) {  blnRes = false; objUti.mostrarMsgErr_F1(this, e);  } 
        finally
        {
            try 
            {
                archivoSueldos = null;
                archivoBonos = null;
                archivoMovilizacion = null;
                archivoSueldosSinCtaBan = null;
                archivoBonosSinCtaBan = null;
                archivoMovilizacionSinCtaBan = null;
            } 
            catch (Throwable ignore) {       }
        }
        //  conversionArchivo();
        return blnRes;
    }

    private void conversionArchivo() throws FileNotFoundException, IOException 
    {
        String FilePath = "C:/Zafiro/Temp/RecursosHumanos/ZafRecHum37/CASH MANAGEMENT/DIMULTI S.A/ROLPAG_SUELDOS DIMULTI S.A. - 2DA QUINC MAYO 2013.txt";
        FileInputStream fis = new FileInputStream(FilePath);
        Charset inputCharset = Charset.forName("ISO-8859-1");
        InputStreamReader isr = new InputStreamReader(fis, inputCharset);

        Reader in = new BufferedReader(isr);
        StringBuffer buffer = new StringBuffer();

        int ch;
        while ((ch = in.read()) > -1)
        {
            buffer.append((char) ch);
        }
        in.close();

        FileOutputStream fos = new FileOutputStream(FilePath + "\\ROLPAG_SUELDOS DIMULTI S.A. - 2DA QUINC MAYO 2013.txt");
        Writer out = new OutputStreamWriter(fos, "UTF8");
        out.write(buffer.toString());
        out.close();
    }

    private String retornaValorStr(String strValor) 
    {
        String cadena = strValor;
        String cadena2 = "";
        int car = cadena.indexOf(".");
        int limite = 0;
        if (car > 0) 
        {
            cadena2 = cadena.substring(car + 1);
        }

        limite = (cadena2.length() > 0) ? cadena2.length() : 2;

        if (limite % 2 != 0)
        {
            for (int i = 0; i < limite; i++)
            {
                cadena += "0";
            }
        }

        cadena = cadena.replace(".", "");
        strValor = cadena;
        return strValor;
    }

    /**
     * Esta función determina si los campos son válidos.
     *
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean validaArchivo(String strTipoCuenta, String strNumeroCuenta) 
    {
        //Validar el "Tipo de documento".
        if (strTipoCuenta == null) 
        {
            return false;
        }
        //Validar el "Departamento".
        if (strNumeroCuenta == null) 
        {
            return false;
        }

        return true;
    }
    //</editor-fold>    
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se
     * grabaron y que debe comunicar de este particular al administrador del
     * sistema.
     */
    private void mostrarMsgErr(String strMsg) 
    {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     *
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgInf(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void MensajeInf(String strMensaje)
    {
        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        obj.showMessageDialog(this, strMensaje, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void bloquea(javax.swing.JTextField txtFiel, boolean blnEst) 
    {
        java.awt.Color colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }

    
    public class mitoolbar extends ZafToolBar 
    {
        public mitoolbar(javax.swing.JInternalFrame jfrThis) 
        {
            super(jfrThis, objZafParSis);
        }

        public boolean anular() 
        {
            strAux = objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) 
            {
                MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;

            }
            if (strAux.equals("Anulado")) 
            {
                MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }

            if (!anularReg()) 
            {
                return false;
            }
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam = false;
            return true;
        }

        private boolean anularReg() 
        {
            boolean blnRes = false;
            try 
            {
                con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                con.setAutoCommit(false);
                if (con != null) 
                {
                    if (anularCab()) 
                    {
                        if (actualizarTbmIngEgrMenTra()) 
                        {
                            if (objAsiDia.anularDiario(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()))) 
                            {
                                con.commit();
                                blnRes = true;
                            }
                            else 
                            {
                                con.rollback();
                            }
                        } 
                        else 
                        {
                            con.rollback();
                        }
                    }
                    else
                    {
                        con.rollback();
                    }
                }
                con.close();
                con = null;
            } 
            catch (java.sql.SQLException e) {   objUti.mostrarMsgErr_F1(this, e);     } 
            catch (Exception e) {    objUti.mostrarMsgErr_F1(this, e);            }
            return blnRes;
        }

        private boolean actualizarTbmIngEgrMenTra()
        {
            boolean blnRes = true;
            int intNePer = -1;
            try
            {
                if (con != null) 
                {
                    stm = con.createStatement();

                    if (cboPer.getSelectedItem().toString().equals("Primera quincena"))
                    {
                        intNePer = 1;
                    } 
                    else if (cboPer.getSelectedItem().toString().equals("Fin de mes")) 
                    {
                        intNePer = 2;
                    }

                    //Armar la sentencia SQL.
                    strSQL ="";
                    strSQL =" update tbm_ingEgrMenTra set st_rolpaggen = null";
                    strSQL+=" where co_emp = " + objZafParSis.getCodigoEmpresa();
                    strSQL+=" and ne_ani = " + cboPerAAAA.getSelectedItem().toString();
                    strSQL+=" and ne_mes = " + cboPerMes.getSelectedIndex();
                    strSQL+=" and ne_per = " + intNePer;
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm = null;
                }
            } 
            catch (java.sql.SQLException e) {   blnRes = false;      objUti.mostrarMsgErr_F1(this, e);       } 
            catch (Exception e) {    blnRes = false;        objUti.mostrarMsgErr_F1(this, e);         }
            return blnRes;
        }

        /**
         * Esta función permite anular la cabecera de un registro.
         *
         * @return true: Si se pudo anular la cabecera del registro.
         * <BR>false: En el caso contrario.
         */
        private boolean anularCab() 
        {
            boolean blnRes = true;
            try 
            {
                if (con != null) 
                {
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
            }
            catch (java.sql.SQLException e) {      blnRes = false;      objUti.mostrarMsgErr_F1(this, e);      } 
            catch (Exception e) {          blnRes = false;         objUti.mostrarMsgErr_F1(this, e);       }
            return blnRes;
        }

        public void clickAceptar() 
        {
            setEstadoBotonMakeFac();
        }

        public void clickAnterior() 
        {
            try 
            {
                if (!rstCab.isFirst())
                {
                    rstCab.previous();
                    cargarReg();
                }
            }
            catch (java.sql.SQLException e) {        objUti.mostrarMsgErr_F1(this, e);        }
            catch (Exception e) {        objUti.mostrarMsgErr_F1(this, e);         }
        }

        public void clickSiguiente() 
        {
            try 
            {
                if (!rstCab.isLast()) 
                {
                    rstCab.next();
                    cargarReg();
                }
            } 
            catch (java.sql.SQLException e) {        objUti.mostrarMsgErr_F1(this, e);        } 
            catch (Exception e) {       objUti.mostrarMsgErr_F1(this, e);           }
        }

        public void clickInicio()
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    rstCab.first();
                    cargarReg();
                }
            }
            catch (java.sql.SQLException e) {      objUti.mostrarMsgErr_F1(this, e);      } 
            catch (Exception e) {      objUti.mostrarMsgErr_F1(this, e);          }
        }

        public void clickFin()
        {
            try 
            {
                if (!rstCab.isLast()) 
                {
                    rstCab.last();
                    cargarReg();
                }
            }
            catch (java.sql.SQLException e) {         objUti.mostrarMsgErr_F1(this, e);         } 
            catch (Exception e) {         objUti.mostrarMsgErr_F1(this, e);            }
        }

        public void clickAnular() 
        {

        }

        public void clickConsultar() 
        {
            clnTextos();
            noEditable(false);
            bloquea(txtNumDoc, false);
            butArc.setEnabled(true);
            butExp.setEnabled(true);
        }

        public void clickEliminar() 
        {
            //          noEditable(false);
        }

        public void clickInsertar() 
        {
            try
            {
                clnTextos();
                //noEditable(false);

                java.awt.Color colBack;
                colBack = txtValDoc.getBackground();
                txtValDoc.setEditable(false);
                txtValDoc.setBackground(colBack);
                txtCodDoc.setEditable(false);
                txtCodDoc.setBackground(colBack);

                if (objZafParSis.getCodigoMenu() != 3138) 
                {
                    txtCodTipDoc.setEditable(true);
                    txtDesCorTipDoc.setEditable(true);
                    txtDesLarTipDoc.setEditable(true);
                    txtDesCorTipDoc.setEnabled(true);
                    txtDesLarTipDoc.setEnabled(true);
                    butBusTipDoc.setEnabled(true);
                    txtCodTipDoc.setEnabled(true);
                    txtDesCorTipDoc.requestFocus();
                }
                else 
                {
                    mostrarTipDocPre();

                    datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                    // java.util.Date dateObj =datFecAux;
                    //  java.util.Calendar calObj = java.util.Calendar.getInstance();
                    // calObj.setTime(dateObj);

                    /*CARGAR REGISTROS ROLES*/
                    String strA = String.valueOf(cboPerAAAA.getSelectedItem());

                    if (cargarRolPer()) 
                    {
                        if (generaAsiento())
                        {
                            boolean blnProv = false;
                            //Aqui no entra si se inserta y el cbo esta en primeraquincena
                            if (cboPer.getSelectedIndex() == 2)
                            {
                                blnProv = generaAsientoProvisiones();
                                panAsiDiaProv.setVisible(Boolean.FALSE);
                            }

                            if (blnProv) 
                            {
                                if (objAsiDia.isDiarioCuadrado()) 
                                {
                                    butArc.setEnabled(true);
                                } 
                                else 
                                {
                                    llamarManCtaCon(String.valueOf(objZafParSis.getCodigoEmpresa()),
                                            String.valueOf(cboPerAAAA.getSelectedItem()),
                                            String.valueOf(cboPerMes.getSelectedIndex()),
                                            String.valueOf(cboPer.getSelectedIndex())
                                    );
                                }
                            }
                            else 
                            {
                                if (objAsiDia.isDiarioCuadrado()) 
                                {
                                    butArc.setEnabled(true);
                                    butExp.setEnabled(true);
                                }
                                else 
                                {
                                    llamarManCtaCon(String.valueOf(objZafParSis.getCodigoEmpresa()),
                                            String.valueOf(cboPerAAAA.getSelectedItem()),
                                            String.valueOf(cboPerMes.getSelectedIndex()),
                                            String.valueOf(cboPer.getSelectedIndex())
                                    );
                                }
                            }
                        }
                    }

                    if (rstCab != null) 
                    {
                        rstCab.close();
                        rstCab = null;
                    }

                    txtFecDoc.setEnabled(true);
                    objAsiDia.setEditable(true);
                    //Inicializar las variables que indican cambios.
                    objTblMod.setDataModelChanged(false);
                    objAsiDia.setDiarioModificado(false);
                    blnHayCam = false;
                    objTooBar.setEnabledVistaPreliminar(true);

                }
            }
            catch (Exception e) {     e.printStackTrace();  objUti.mostrarMsgErr_F1(this, e);            }
        }

        public void setEstadoBotonMakeFac() 
        {
            switch (getEstado()) 
            {
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

        public boolean eliminar() 
        {
            try 
            {
                if (!eliminarReg()) 
                {
                    return false;
                }
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast()) 
                {
                    rstCab.next();
                    cargarReg();
                } 
                else 
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    clnTextos();
                }
                blnHayCam = false;
            } 
            catch (java.sql.SQLException e) {   return true;      }
            return true;
        }

        /**
         * Esta función elimina el registro de la base de datos.
         *
         * @return true: Si se pudo eliminar el registro.
         * <BR>false: En el caso contrario.
         */
        private boolean eliminarReg() 
        {
            boolean blnRes = false;
            try 
            {
                con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                con.setAutoCommit(false);
                if (con != null) 
                {
                    if (eliminarDet()) 
                    {
                        if (eliminarCab()) 
                        {
                            if (actualizarTbmIngEgrMenTra()) 
                            {
                                if (objAsiDia.eliminarDiario(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()))) 
                                {
                                    con.commit();
                                    blnRes = true;
                                }
                                else  {      con.rollback();    }
                            } else {     con.rollback();    }
                        } else  {     con.rollback();   }
                    } else {    con.rollback();    }
                }
                con.close();
                con = null;
            } 
            catch (java.sql.SQLException e) {   objUti.mostrarMsgErr_F1(this, e);       } 
            catch (Exception e) {     objUti.mostrarMsgErr_F1(this, e);     }
            return blnRes;
        }

        /**
         * Esta función permite eliminar la cabecera de un registro.
         *
         * @return true: Si se pudo eliminar la cabecera del registro.
         * <BR>false: En el caso contrario.
         */
        private boolean eliminarCab() 
        {
            boolean blnRes = true;
            int intNePer = -1;

            try 
            {
                if (con != null) 
                {
                    stm = con.createStatement();

                    if (cboPer.getSelectedItem().toString().equals("Primera quincena"))
                    {
                        intNePer = 1;
                    } 
                    else if (cboPer.getSelectedItem().toString().equals("Fin de mes")) 
                    {
                        intNePer = 2;
                    }

                    //Obtener la fecha del servidor.
                    datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux == null) 
                    {
                        return false;
                    }
                    //Armar la sentencia SQL.
                    strSQL ="";
                    strSQL+=" DELETE from tbm_cabRolPag";
                    strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                    strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                    strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                    strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm = null;
                    datFecAux = null;
                }
            } 
            catch (java.sql.SQLException e) {   blnRes = false;     objUti.mostrarMsgErr_F1(this, e);      } 
            catch (Exception e) {       blnRes = false;       objUti.mostrarMsgErr_F1(this, e);      }
            return blnRes;
        }

        /**
         * Esta función permite eliminar la cabecera de un registro.
         *
         * @return true: Si se pudo eliminar la cabecera del registro.
         * <BR>false: En el caso contrario.
         */
        private boolean eliminarDet()
        {
            boolean blnRes = true;

            try 
            {
                if (con != null) 
                {
                    stm = con.createStatement();

                    //Obtener la fecha del servidor.
                    datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux == null)
                    {
                        return false;
                    }
                    //Armar la sentencia SQL.
                    strSQL ="";
                    strSQL+=" DELETE from tbm_detRolPag";
                    strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                    strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                    strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                    strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm = null;
                    datFecAux = null;
                }
            }
            catch (java.sql.SQLException e) { blnRes = false;    objUti.mostrarMsgErr_F1(this, e); } 
            catch (Exception e) {     blnRes = false;     objUti.mostrarMsgErr_F1(this, e);    }
            return blnRes;
        }

        /**
         * Esta función determina si los campos son válidos.
         *
         * @return true: Si los campos son válidos.
         * <BR>false: En el caso contrario.
         */
        private boolean validaCampos()
        {
            //Validar el "Tipo de documento".
            if (txtCodTipDoc.getText().equals(""))
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                txtDesCorTipDoc.requestFocus();
                return false;
            }
            //Validar el "Departamento".
            if (txtCodCta.getText().equals(""))
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cuenta</FONT> es obligatorio.<BR>Escriba o seleccione una cuenta y vuelva a intentarlo.</HTML>");
                txtDesCorCta.requestFocus();
                return false;
            }
            //Validar la "Fecha del Documento".
            if (!txtFecDoc.isFecha()) 
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de Documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha de documento y vuelva a intentarlo.</HTML>");
                txtFecDoc.requestFocus();
                return false;
            }
            //Validar el "Número de Documento".
            String str = txtNumDoc.getText();
            if (txtNumDoc.getText().equals("")) 
            {
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de Documento</FONT> es obligatorio.<BR>Escriba un número de documento y vuelva a intentarlo.</HTML>");
                txtNumDoc.requestFocus();
                return false;
            } 
            else
            {
                if (!objUti.isNumero(txtNumDoc.getText())) 
                {
                    tabFrm.setSelectedIndex(1);
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de Documento</FONT> contiene formato érroneo.<BR>Escriba un número de documento y vuelva a intentarlo.</HTML>");
                    txtNumDoc.requestFocus();
                    return false;
                }
            }

            //Validar el "Año".
            int intPerAAAA = cboPerAAAA.getSelectedIndex();
            if (intPerAAAA <= 0) 
            {
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Año</FONT> es obligatorio.<BR>Seleccione el año y vuelva a intentarlo.</HTML>");
                cboPerAAAA.requestFocus();
                return false;
            }
            //Validar el "Mes".
            int intPerMes = cboPerMes.getSelectedIndex();
            if (intPerMes <= 0) 
            {
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Mes</FONT> es obligatorio.<BR>Seleccione el mes y vuelva a intentarlo.</HTML>");
                cboPerMes.requestFocus();
                return false;
            }
            //Validar el "Período".
            int intPer = cboPer.getSelectedIndex();
            if (intPer <= 0) 
            {
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Periodo</FONT> es obligatorio.<BR>Seleccione el período y vuelva a intentarlo.</HTML>");
                cboPer.requestFocus();
                return false;
            }

            //Validar que el "Diario esté cuadrado".
            if (!objAsiDia.isDiarioCuadrado()) 
            {
                mostrarMsgInf("<HTML>El asiento de diario está <FONT COLOR=\"blue\">descuadrado</FONT>.<BR>Cuadre el asiento de diario y vuelva a intentarlo.</HTML>");
                return false;
            }
            
        // Validar que el "Monto del diario" sea igual al monto del documento.
        //     if (!objAsiDia.isDocumentoCuadrado(objUti.redondear(objUti.parseDouble(objTblTot.getValueAt(0, objTblMod.getColumnCount()-5)), objZafParSis.getDecimalesMostrar())))
        // if (!objAsiDia.isDocumentoCuadrado(txtValDoc.getText()))
        // {
        //     mostrarMsgInf("<HTML>El valor del documento no coincide con el valor del asiento de diario.<BR>Cuadre el valor del documento con el valor del asiento de diario y vuelva a intentarlo.</HTML>");
        //     txtValDoc.selectAll();
        //     txtValDoc.requestFocus();
        //     return false;
        // }

            return true;
        }

        public boolean insertar() 
        {
            boolean blnRes = false;
            try 
            {
                if (validaCampos()) 
                {
                    if (!rolPerExiste()) 
                    {
                        if (!validarValoresTotalesRecibirNegativos()) 
                        {
                            if (revisionRRHH())
                            {
                                if (insertarReg()) 
                                {
                                    blnRes = true;
                                }
                            }
                            else 
                            {
                                mostrarMsgInf("<HTML>Está pendiente la revisión de <FONT COLOR=\"blue\">Recursos Humanos</FONT> para que el Rol pueda ser guardado.<BR></HTML>");
                                return false;
                            }
                        }
                        else 
                        {
                            mostrarMsgInf("<HTML>No se puede guardar un <FONT COLOR=\"blue\">Rol de Pagos</FONT> que contiene valores negativos en los totales a recibir.<BR></HTML>");
                            return false;
                        }
                    }
                    else 
                    {
                        mostrarMsgInf("<HTML><FONT COLOR=\"blue\">Rol de Pagos</FONT> que se desea guardar es de un Período pasado.<BR></HTML>");
                        return false;
                    }
                }
            }
            catch (Exception Evt) 
            {
                Evt.printStackTrace();
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean rolPerExiste() 
        {
            boolean blnRes = false;
            java.sql.Connection con = null;
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;
            String strSQL = "";

            try
            {
                con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (con != null) 
                {
                    con.setAutoCommit(false);
                    stmLoc = con.createStatement();

                    strSQL = "select * from tbm_cabrolpag \n"
                           + "where co_emp = " + objZafParSis.getCodigoEmpresa() + "\n"
                           + "and co_loc = " + objZafParSis.getCodigoLocal() + "\n"
                           + "and co_tipdoc = " + txtCodTipDoc.getText() + "\n"
                           + "and ne_ani = " + cboPerAAAA.getSelectedItem().toString() + "\n"
                           + "and ne_mes = " + cboPerMes.getSelectedIndex() + "\n"
                           + "and ne_per = " + cboPer.getSelectedIndex() + "\n"
                           + "and st_reg = 'A'";
                    rstLoc = stmLoc.executeQuery(strSQL);
                    if (rstLoc.next()) 
                    {
                        blnRes = true;
                    }
                }
            }
            catch (java.sql.SQLException Evt) {     blnRes = false;       objUti.mostrarMsgErr_F1(this, Evt);       } 
            catch (Exception Evt) {    blnRes = false;         objUti.mostrarMsgErr_F1(this, Evt);        }
            finally 
            {
                try {   rstLoc.close();       } catch (Throwable ignore) {     }
                try {   stmLoc.close();       } catch (Throwable ignore) {     }
                try {   con.close();          } catch (Throwable ignore) {     }
            }
            return blnRes;
        }

        public boolean actualizarRelacionRol(Connection con, int intCodDoc)
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;
            String strSql = "";

            try
            {
                if (con != null) 
                {
                    con.setAutoCommit(false);
                    stmLoc = con.createStatement();

                    strSql = "";
                    strSql += "update tbm_feccorrolpag SET " + " \n";

                    if (txtCodTipDoc.getText().compareTo("192") == 0) 
                    {
                        strSql += " co_locRelRolPag = " + objZafParSis.getCodigoLocal() + " , \n";
                        strSql += " co_tipDocRelRolPag = " + txtCodTipDoc.getText() + " , \n";
                        strSql += " co_docRelRolPag = " + intCodDoc + " \n";
                    } 
                    else if (txtCodTipDoc.getText().compareTo("199") == 0) 
                    {
                        strSql += " co_locRelRolPagBon = " + objZafParSis.getCodigoLocal() + " , \n";
                        strSql += " co_tipDocRelRolPagBon = " + txtCodTipDoc.getText() + " , \n";
                        strSql += " co_docRelRolPagBon = " + intCodDoc + " \n";
                    }
                    else if (txtCodTipDoc.getText().compareTo("202") == 0) 
                    {
                        strSql += " co_locRelRolPagMov = " + objZafParSis.getCodigoLocal() + " , \n";
                        strSql += " co_tipDocRelRolPagMov = " + txtCodTipDoc.getText() + " , \n";
                        strSql += " co_docRelRolPagMov = " + intCodDoc + " \n";
                    }

                    strSql += " where co_emp = " + objZafParSis.getCodigoEmpresa() + " \n";
                    strSql += " and ne_ani = " + cboPerAAAA.getSelectedItem().toString() + " \n";
                    strSql += " and ne_mes = " + cboPerMes.getSelectedIndex() + " \n";
                    strSql += " and ne_per = " + cboPer.getSelectedIndex() + " \n";
                    //System.out.println("updateblnPas2: " + strSql);

                    stmLoc.executeUpdate(strSql);
                    blnRes = true;
                }
            }
            catch (java.sql.SQLException Evt) {   blnRes = false;    objUti.mostrarMsgErr_F1(this, Evt);        } 
            catch (Exception Evt) {         blnRes = false;     objUti.mostrarMsgErr_F1(this, Evt);    } 
            finally 
            {
                try
                {
                    rstLoc.close();
                    rstLoc = null;
                } 
                catch (Throwable ignore)   {        }
                try 
                {
                    stmLoc.close();
                    stmLoc = null;
                } catch (Throwable ignore) {        }
            }
            return blnRes;
        }

        public boolean validarValoresTotalesRecibirNegativos() 
        {
            boolean blnRes = false;
            try 
            {
                for (int intFil = 0; intFil < objTblMod.getRowCount(); intFil++) 
                {
                    // double dblValRec = objUti.redondear(objTblMod.getValueAt(intFil, (objTblMod.getColumnCount()-1)).toString(), objZafParSis.getDecimalesMostrar());
                    double dblValRec = objUti.redondear(objTblMod.getValueAt(intFil, INT_TBL_TOTREC).toString(), objZafParSis.getDecimalesMostrar());
                    //System.out.println(dblValRec);
                    if (dblValRec < 0) 
                    {
                        return true;
                    }
                }
            } 
            catch (Exception Evt) {  blnRes = true;      objUti.mostrarMsgErr_F1(this, Evt);    }
            return blnRes;
        }

        public boolean revisionRRHH() 
        {
            boolean blnRes = false;
            java.sql.Connection con = null;
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;
            String strSQL = "";
            try
            {
                con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (con != null) 
                {
                    con.setAutoCommit(false);
                    stmLoc = con.createStatement();

                    strSQL += "SELECT b.co_emp , b.ne_ani , b.ne_mes , b.ne_per , st_revfal , fe_autrevfal  " + "\n";
                    strSQL += "FROM tbm_feccorrolpag  b  " + "\n";
                    strSQL += "LEFT OUTER JOIN tbm_cabrolpag a on (a.co_emp=b.co_emp and a.ne_ani=b.ne_ani and a.ne_mes=b.ne_mes and b.ne_per=a.ne_per)   " + "\n";
                    strSQL += "WHERE b.co_emp = " + objZafParSis.getCodigoEmpresa() + "\n";
                    Calendar cal = Calendar.getInstance();
                    strSQL += " and b.ne_ani = " + cboPerAAAA.getSelectedItem().toString() + "\n";
                    strSQL += " and b.ne_mes =" + cboPerMes.getSelectedIndex() + "\n";
                    strSQL += " and b.ne_per =" + cboPer.getSelectedIndex();
                    //System.out.println("strSqlRevFal: " + strSQL);
                    rstLoc = stmLoc.executeQuery(strSQL);

                    if (rstLoc.next()) 
                    {
                        Object objStRevFal = rstLoc.getString("st_revfal");
                        if (objStRevFal != null) 
                        {
                            blnRes = true;
                        }
                    }
                }
            } 
            catch (java.sql.SQLException Evt) {     blnRes = false;        objUti.mostrarMsgErr_F1(this, Evt);      } 
            catch (Exception Evt) {        blnRes = false;       objUti.mostrarMsgErr_F1(this, Evt);     } 
            finally 
            {
                try {    rstLoc.close();       } catch (Throwable ignore) {        }
                try {    stmLoc.close();       } catch (Throwable ignore) {        }
                try {    con.close();          } catch (Throwable ignore) {        }
            }
            return blnRes;
        }

        public boolean insertarReg() 
        {
            boolean blnRes = false;
            java.sql.Connection con = null;
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;
            String strSQL = "";
            int intCodDocDiaRol = 0;
            int intNumDocDiaRol = 0;
            int intCoDocDiaProv = 0;

            try 
            {
                con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (con != null) 
                {
                    con.setAutoCommit(false);

                    stmLoc = con.createStatement();

                    int intCodTipDocProv = 0;
                    if (txtCodTipDoc.getText().compareTo("192") == 0)
                    {
                        intCodTipDocProv = 208;
                    }
                    else if (txtCodTipDoc.getText().compareTo("199") == 0) 
                    {
                        intCodTipDocProv = 209;
                    } 
                    else if (txtCodTipDoc.getText().compareTo("202") == 0)
                    {
                        intCodTipDocProv = 210;
                    }

                    strSQL = " SELECT case when (Max(co_doc)+1) is null then 1 else Max(co_doc)+1 end as co_doc  "
                            +" FROM tbm_cabrolpag "
                            +" WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + objZafParSis.getCodigoLocal() + " "
                            +" and co_tipDoc = " + txtCodTipDoc.getText();
                    rstLoc = stmLoc.executeQuery(strSQL);
                    if (rstLoc.next()) 
                    {
                        intCodDocDiaRol = rstLoc.getInt("co_doc");
                    }
                    rstLoc.close();
                    rstLoc = null;

                    strSQL =" SELECT CASE WHEN (ne_ultdoc+1) is null THEN 1 ELSE ne_ultdoc+1 END AS numDoc "
                           +" FROM tbm_cabtipdoc "
                           +" WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + objZafParSis.getCodigoLocal() + " and co_tipdoc=" + txtCodTipDoc.getText() + " ";
                    rstLoc = stmLoc.executeQuery(strSQL);
                    if (rstLoc.next()) 
                    {
                        intNumDocDiaRol = rstLoc.getInt("numDoc");
                    }
                    rstLoc.close();
                    rstLoc = null;
                   //intNumDoc=intCodDoc;

                    strSQL = "SELECT CASE WHEN (ne_ultdoc+1) is null THEN 1 ELSE ne_ultdoc+1 END AS numDoc FROM tbm_cabtipdoc "
                            + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + objZafParSis.getCodigoLocal() + " and co_tipdoc=" + intCodTipDocProv + " ";
                    rstLoc = stmLoc.executeQuery(strSQL);
                    if (rstLoc.next()) 
                    {
                        intCoDocDiaProv = rstLoc.getInt("numDoc");
                    }
                    rstLoc.close();
                    rstLoc = null;

                    int Fecha[] = txtFecDoc.getFecha(txtFecDoc.getText());
                    String strFecha = "#" + Fecha[2] + "/" + Fecha[1] + "/" + Fecha[0] + "#";
                    String FecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), "yyyy/MM/dd");

                    //<editor-fold defaultstate="collapsed" desc="/* COMENTADO */">
    //                strSQL="";
    //                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
    //                strSQL+=" FROM tbm_cabTipDoc AS a1";
    //                strSQL+=" INNER JOIN tbr_tipDocPrg AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
    //                strSQL+=" WHERE a1.co_emp="+intCodEmp;
    //                strSQL+=" AND a1.co_loc="+intCodLoc;
    //                strSQL+=" AND a2.co_mnu="+objZafParSis.getCodigoMenu();
    //                strSQL+=" ORDER BY a1.tx_desCor";
    //                rstLoc=stmLoc.executeQuery(strSQL);
    //                int intCodTipDocProv = 0;
    //
    //                if(rstLoc.next())
    //                rstLoc.close();
    //                rstLoc=null;
                    //</editor-fold>
                    
                    //<editor-fold defaultstate="collapsed" desc="/* Quincena */">
                    if (cboPer.getSelectedItem().toString().equals("Primera quincena")) 
                    {
                        if (insertarCabReg(con, intCodDocDiaRol, intNumDocDiaRol, strFecha, intCodLoc, intCodTipDocProv, txtNumDoc.getText())) {
                            if (insertarDetReg(con, intCodDocDiaRol, intNumDocDiaRol, strFecha)) {
                                if (setearAnticipadoFDM(con)) 
                                {
                                    if (actualizarRelacionRol(con, intCodDocDiaRol)) 
                                    {
                                        if (objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), txtNumDoc.getText(), objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy"), "O")) 
                                        {
//                                            if (enviarCorEle()) Bostel
//                                            {
//                                                con.commit();
//                                                blnRes = true;
//                                                txtCodDoc.setText("" + intCodDocDiaRol);
//                                                txtNumDoc.setText("" + intNumDocDiaRol);
//                                            }
//                                            else  {    con.rollback();   }
                                        } else {  con.rollback();    }
                                    } else {   con.rollback();    }
                                } else {   con.rollback();   }
                            } else {   con.rollback();   }
                        } else {  con.rollback();    }
                    } 
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="/* Fin de Mes */">
                    else 
                    {
                        //<editor-fold defaultstate="collapsed" desc="/* COMENTADO */">
//                       if (insertarCabReg(con,intCodDocDiaRol, intCodDocDiaRol, strFecha, intCodLoc, intCodTipDocProv, txtNumDoc.getText())) {
//                           if (insertarDetReg(con,intCodDocDiaRol, intCodDocDiaRol, strFecha)) { 
//                               if(actualizarStRolPagGen(con)){
//                                   if (objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()) , String.valueOf(intCodDocDiaRol), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"))){
//                                       con.commit();
//                                       blnRes = true;
//                                       txtCodDoc.setText(""+intCodDocDiaRol);
//                                       txtNumDoc.setText(""+intCodDocDiaRol);
//                                   }
//                               }
//                           }
//                       }
//                       if (insertarDetalleProvisiones(con)) {
////                           if(objAsiDiaProv.insertarDiario(con, intCodEmp, intCodLoc,  intCodTipDocProv, String.valueOf(intCoDocDiaProv), String.valueOf(intCoDocDiaProv), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"))){
////                               if(actualizarRelacionesRolProv(con, intCodEmp, intCodLoc,  intCodTipDocProv, String.valueOf(txtCodDoc.getText()) ,String.valueOf(intCoDocDiaProv))){
//                                   con.commit();
//                                    blnRes = true;
////                                    txtCodDoc.setText(""+intCodDocDiaRol);
////                                    txtNumDoc.setText(""+intNumDocDiaRol);
////                               }
////                           }
//                       }
//                       if (insertarDetalleProvisiones(con)) {
//                           con.commit();
//                           blnRes = true;
//                           if(objAsiDiaProv.insertarDiario(con, intCodEmp, intCodLoc,  intCodTipDocProv, String.valueOf(intCoDocDiaProv), String.valueOf(intCoDocDiaProv), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"))){
//                               if(actualizarRelacionesRolProv(con, intCodEmp, intCodLoc,  intCodTipDocProv, String.valueOf(txtCodDoc.getText()) ,String.valueOf(intCoDocDiaProv))){
//                                   con.commit();
//                                    blnRes = true;
//                                    txtCodDoc.setText(""+intCodDocDiaRol);
//                                    txtNumDoc.setText(""+intNumDocDiaRol);
//                               }
//                           }
//                       }else{
//                           con.rollback();
//                       }
                        //</editor-fold>
                        if (insertarCabReg(con, intCodDocDiaRol, intCodDocDiaRol, strFecha, intCodLoc, intCodTipDocProv, txtNumDoc.getText())) {
                            if (insertarDetReg(con, intCodDocDiaRol, intCodDocDiaRol, strFecha)) {
                                if (actualizarRelacionRol(con, intCodDocDiaRol)) {
                                    //<editor-fold defaultstate="collapsed" desc="/* COMENTADO */">
//                                       if(actualizarStRolPagGen(con)){
//                                       if (insertarDetProv(con,intCodDocDiaRol, intCodDocDiaRol, strFecha)) { 
                                    //</editor-fold>
                                    //<editor-fold defaultstate="collapsed" desc="/* Meses distintos a Diciembre */">
                                    if (cboPerMes.getSelectedIndex() != 12) {
                                        //<editor-fold defaultstate="collapsed" desc="/* COMENTADO */">
//                                      if(txtCodTipDoc.getText().compareTo("192")==0){
                                        //</editor-fold>
                                        if (insertarDetalleProvisiones(con)) {
                                            if (objAsiDiaProv.insertarDiario(con, intCodEmp, intCodLoc, intCodTipDocProv, String.valueOf(intCoDocDiaProv), String.valueOf(intCoDocDiaProv), objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy"))) {
                                                if (objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), String.valueOf(intCodDocDiaRol), objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy"), "0")) {
                                                    if (actualizarRelacionesRolProv(con, intCodEmp, intCodLoc, intCodTipDocProv, String.valueOf(intCodDocDiaRol), String.valueOf(intCoDocDiaProv))) 
                                                    {
//                                                        if (enviarCorEle()) 
//                                                        {
//                                                            con.commit();
//                                                            blnRes = true;
//                                                            txtCodDoc.setText("" + intCodDocDiaRol);
//                                                            txtNumDoc.setText("" + intNumDocDiaRol);
//                                                        } else {    con.rollback();      }
                                                    } else {    con.rollback();       }
                                                } else {    con.rollback();       }
                                            } else {    con.rollback();      }
                                        } else {     con.rollback();    }
//                                            }                                            
                                    } 
                                    //</editor-fold>
                                    //<editor-fold defaultstate="collapsed" desc="/* Diciembre */">
                                    else 
                                    {
                                        if (txtCodTipDoc.getText().compareTo("192") == 0) 
                                        {
                                            if (insertarDetalleProvisiones(con)) {
                                                if (objAsiDiaProv.insertarDiario(con, intCodEmp, intCodLoc, intCodTipDocProv, String.valueOf(intCoDocDiaProv), String.valueOf(intCoDocDiaProv), objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy"))) {
                                                    if (objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), String.valueOf(intCodDocDiaRol), objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy"), "0")) {
                                                        if (actualizarRelacionesRolProv(con, intCodEmp, intCodLoc, intCodTipDocProv, String.valueOf(intCodDocDiaRol), String.valueOf(intCoDocDiaProv))) 
                                                        {
//                                                            if (enviarCorEle()) bostel
//                                                            {
//                                                                con.commit();
//                                                                blnRes = true;
//                                                                txtCodDoc.setText("" + intCodDocDiaRol);
//                                                                txtNumDoc.setText("" + intNumDocDiaRol);
//                                                            }
//                                                            else {   con.rollback();         }
                                                        } else {   con.rollback();         }
                                                    } else {    con.rollback();         }
                                                } else {     con.rollback();        }
                                            } else {      con.rollback();       }
                                        }
                                        else 
                                        {
                                            /*POR EL MOMENTO PARA CO_TIPDOC=199 Y CO_TIPDOC=202 NO SE REALIZA PROVISIONES, NI ASIENTOS EN ESTE MES, AL MOMENTO SE LO HARIA EN NOVIEMBRE*/
                                            if (objAsiDiaProv.insertarDiario(con, intCodEmp, intCodLoc, intCodTipDocProv, String.valueOf(intCoDocDiaProv), String.valueOf(intCoDocDiaProv), objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy"))) {
                                                if (objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), String.valueOf(intCodDocDiaRol), objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy"), "0")) {
                                                    if (actualizarRelacionesRolProv(con, intCodEmp, intCodLoc, intCodTipDocProv, String.valueOf(intCodDocDiaRol), String.valueOf(intCoDocDiaProv))) 
                                                    {
//                                                        if (enviarCorEle()) bostel
//                                                        {
//                                                            con.commit();
//                                                            blnRes = true;
//                                                            txtCodDoc.setText("" + intCodDocDiaRol);
//                                                            txtNumDoc.setText("" + intNumDocDiaRol);
//                                                        } else {  con.rollback();       }
                                                    } else {   con.rollback();      }
                                                } else {   con.rollback();       }
                                            } else {    con.rollback();     }
                                        }
                                    }
                                    //</editor-fold>
                                    
                                    //<editor-fold defaultstate="collapsed" desc="/* COMENTADO */">
//                                    }else{   con.rollback();}
                                    //</editor-fold>
                                } else {    con.rollback();       }
                            } else {   con.rollback();       }
                        } else {    con.rollback();      }
                    }
                    //</editor-fold>
                }
            } 
            catch (java.sql.SQLException Evt) {   blnRes = false;      objUti.mostrarMsgErr_F1(this, Evt);    }
            catch (Exception Evt) {    blnRes = false;         objUti.mostrarMsgErr_F1(this, Evt);     }
            finally 
            {
                try {    rstLoc.close();       } catch (Throwable ignore) {         }
                try {    stmLoc.close();       } catch (Throwable ignore) {         }
                try {    con.close();          } catch (Throwable ignore) {         }
            }
            return blnRes;
        }
        
        /**
         * Función que se encarga del envío de Correo.
         * @return 
         *///bostel
//        private boolean enviarCorEle() 
//        {
//            boolean blnRes = true;
//            int intCodCfgCorEleAdmin=20;     //Notificación de RRHH - Admin
//            int intCodCfgCorEleRolPag=23;    //Notificación de RRHH: ROLPAG
//            int intCodCfgCorElePreFun=24;    //Notificación de RRHH: PREFUN Y OTROS
//            int intCodLocGrp=1;              //Código de Local de Grupo             
//            try 
//            {
//                //String strCorEleTo = "vicepresidencia@tuvalsa.com;gerenciageneral@tuvalsa.com;geren_admin@tuvalsa.com;contador@tuvalsa.com;";
//                //Subject de Correo
//                String strSbjMsg ="";
//                strSbjMsg+="NOTIFICACIONES RRHH - " + objZafParSis.getNombreMenu() + " - ";
//                if (cboPer.getSelectedIndex() == 1)  {
//                    strSbjMsg+="1ERA QUINCENA " + txtDesCorTipDoc.getText() + "  " + objZafParSis.getNombreEmpresa() + " DEL MES DE " + cboPerMes.getSelectedItem().toString().toUpperCase() + "/" + cboPerAAAA.getSelectedItem().toString().toUpperCase();
//                } 
//                else if (cboPer.getSelectedIndex() == 2) {
//                    strSbjMsg+="2DA QUINCENA " + txtDesCorTipDoc.getText() + "  " + objZafParSis.getNombreEmpresa() + " DEL MES DE " + cboPerMes.getSelectedItem().toString().toUpperCase() + "/" + cboPerAAAA.getSelectedItem().toString().toUpperCase();
//                }        
//                strSbjMsg+=" - Estado: revisado por area contable";                
//                strSbjMsg+=" - "+strVer;   
//                
//                //Mensaje de Correo
//                String strMsg = " <HTML> <body> <BR/><BR/>";
//                strMsg+= "<TR>Estimados usuarios, </TR></BR></BR>";
//                strMsg+= "<TR>El &#225;rea contable ha realizado la supervisi&#243;n y el registro del " + txtDesCorTipDoc.getText() + " el d&#237;a de hoy. </TR></BR></BR>";
//                strMsg+= "<TR>A continuaci&#243;n la Gerencia General deber&#225; realizar la aprobaci&#243;n.</TR></BR></BR></BR>";
//                strMsg+= "<TR>Gracias por su atenci&#243;n.</TR></BR>";
//                
//                ////////////////////////////////////
//                if (txtDesCorTipDoc.getText().trim().compareTo("ROLPAG") == 0) {
//                    if(!objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(objZafParSis.getCodigoEmpresaGrupo(), intCodLocGrp, intCodCfgCorEleRolPag, strMsg, strSbjMsg )){ 
//                        System.out.println("No se envio correo ROLPAG");
//                    }  
//                }   
//                else{
//                    if(!objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(objZafParSis.getCodigoEmpresaGrupo(), intCodLocGrp, intCodCfgCorElePreFun, strMsg, strSbjMsg )){
//                        System.out.println("No se envio correo PREFUN Y OTROS");
//                    }                  
//                }
//                
//                if(!objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(objZafParSis.getCodigoEmpresaGrupo(), intCodLocGrp, intCodCfgCorEleAdmin, strMsg, strSbjMsg )){
//                    System.out.println("No se envio correo al usuario admin");
//                }                   
//                
//            }
//            catch (Exception Evt)
//            {
//                blnRes = false;
//                Evt.printStackTrace();
//                objUti.mostrarMsgErr_F1(this, Evt);
//            }
//            return blnRes;
//        }

        /**
         * Esta función permite cargar el detalle del registro seleccionado.
         *
         * @return true: Si se pudo cargar el detalle del registro.
         * <BR>false: En el caso contrario.
         */
        private boolean cargarDetReg(int intCoEmp, int intCoLoc, int intCoTipDoc, int intCoDoc) 
        {
            String strSQL;
            boolean blnRes = true;
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;

            try 
            {
                con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (con != null)
                {
                    stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmLoc = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    //Armar la sentencia SQL.
                    strSQL = "";
                    if (!blnEstCarSolHSE)
                    {
                        if (cboPer.getSelectedIndex() == 1) 
                        {
                            strSQL+=" select distinct a.co_emp,a.co_loc,a.co_tipdoc,a.co_doc,a.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado ";
                            strSQL+=" from tbm_detrolpag a";
                            strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra and a.co_emp=" + intCoEmp + ")";
                        } 
                        else if (cboPer.getSelectedIndex() == 2) 
                        {
                            strSQL+=" select distinct a.co_emp,a.co_loc,a.co_tipdoc,a.co_doc,a.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado, c.ne_numdialab ";
                            strSQL+=" from tbm_detrolpag a";
                            strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra and a.co_emp=" + intCoEmp + ")";
                            strSQL+=" LEFT OUTER JOIN tbm_datgeningegrmentra c on (c.co_emp=a.co_emp and c.co_tra=a.co_tra and c.ne_ani=" + cboPerAAAA.getSelectedItem() + " and c.ne_mes=" + cboPerMes.getSelectedIndex() + " and c.ne_per=0)";
                        }
                        strSQL += " where a.co_emp = " + intCoEmp;
                        strSQL += " and a.co_loc = " + intCoLoc;
                        strSQL += " and a.co_tipdoc = " + intCoTipDoc;
                        strSQL += " and a.co_doc = " + intCoDoc;
                    }
                    else 
                    {
                        if (cboPer.getSelectedIndex() == 1) 
                        {
                            strSQL += " select distinct a.co_emp,a.co_loc,a.co_tipdoc,a.co_doc,a.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado ";
                            strSQL += " from tbm_detrolpag a";
                            strSQL += " inner join tbm_tra b on (a.co_tra=b.co_tra and a.co_emp=" + intCoEmp + ")";
                        }
                        else if (cboPer.getSelectedIndex() == 2) 
                        {
                            strSQL+=" select distinct a.co_emp,a.co_loc,a.co_tipdoc,a.co_doc,a.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado, c.ne_numdialab ";
                            strSQL+=" from tbm_detrolpag a";
                            strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra and a.co_emp=" + intCoEmp + ")";
                            strSQL+=" LEFT OUTER JOIN tbm_datgeningegrmentra c on (c.co_emp=a.co_emp and c.co_tra=a.co_tra and c.ne_ani=" + cboPerAAAA.getSelectedItem() + " and c.ne_mes=" + cboPerMes.getSelectedIndex() + " and c.ne_per=0)";
                        }
                        strSQL += " where a.co_emp = " + intCoEmp;
                        strSQL += " and a.co_loc = " + intCoLoc;
                        strSQL += " and a.co_tipdoc = " + intCoTipDoc;
                        strSQL += " and a.co_doc = " + intCoDoc;
                    }
                    strSQL += " order by empleado asc";
                    //System.out.println("sql detalle : " + strSQL);
                    rst = stm.executeQuery(strSQL);

                    //Limpiar vector de datos.
                    vecDat = new Vector();
                    //Obtener los registros.
                    int intPosCol = INT_TBL_DIAS_LAB + 1;
                    int intCont = 0;
                    String strCoTra = "";

                    while (rst.next())
                    {
                        double dblTotIng = 0;
                        double dblTotEgr = 0;
                        double dblBono = 0;

                        strSQL =" select a.*,(b.tx_ape || ' ' || b.tx_nom) as nomcom,c.tx_tiprub from tbm_detrolpag a";
                        strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra)";
                        strSQL+=" inner join tbm_rubrolpag c on (c.co_rub=a.co_rub)";
                        strSQL+=" where a.co_emp = " + rst.getString("co_emp");
                        strSQL+=" and a.co_loc = " + rst.getString("co_loc");
                        strSQL+=" and a.co_tipdoc = " + rst.getString("co_tipdoc");
                        strSQL+=" and a.co_doc = " + rst.getString("co_doc");
                        strSQL+=" and a.co_tra = " + rst.getString("co_tra");
                        strSQL+=" order by c.tx_tiprub desc,a.co_rub asc";

                        //System.out.println(" SQL Obtener Rubros RolPag Empleado: " + strSQL);
                        rstLoc = stmLoc.executeQuery(strSQL);

                        if (rstLoc.next()) 
                        {
                            Vector vecReg = new Vector();
                            vecReg.add(INT_TBL_LINEA, "");
                            strCoTra = rstLoc.getString("co_tra");
                            vecReg.add(INT_TBL_CODTRA, rstLoc.getString("co_tra"));
                            vecReg.add(INT_TBL_NOMTRA, rstLoc.getString("nomcom"));
                            if (cboPer.getSelectedIndex() == 1)
                            {
                                vecReg.add(INT_TBL_DIAS_LAB, "");
                            }
                            else if (cboPer.getSelectedIndex() == 2) 
                            {
                                vecReg.add(INT_TBL_DIAS_LAB, rst.getString("ne_numdialab"));
                            }

                            vecReg.add(intPosCol, rstLoc.getDouble("nd_valrub"));
                            dblTotIng = dblTotIng + objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                            intPosCol++;

                            while (rstLoc.next()) 
                            {
                                vecReg.add(intPosCol, rstLoc.getDouble("nd_valrub"));
                                if (rstLoc.getString("tx_tiprub").equals("I"))
                                {
                                    if (rstLoc.getInt("co_rub") == 6)
                                    {
                                        dblBono = rstLoc.getDouble("nd_valrub");
                                    }
                                    dblTotIng += objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                                }
                                else if (rstLoc.getString("tx_tiprub").equals("E")) 
                                {
                                    dblTotEgr += objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                                }

                                intCont++;
                                intPosCol++;
                            }

                            vecReg.add(vecReg.size(), dblTotIng);
                            intPosCol++;
                            vecReg.add(vecReg.size(), dblTotEgr);
                            vecReg.add(vecReg.size(), objUti.redondear(objUti.parseDouble(dblTotIng + dblTotEgr), objZafParSis.getDecimalesMostrar()));

                            vecDat.add(vecReg);
                        }
                        intPosCol = INT_TBL_DIAS_LAB + 1;
                    }

                    //Asignar vectores al modelo.
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                    objTblMod.setData(vecDat);
                    tblDat.setModel(objTblMod);
                    objTblTot.calcularTotales();
                  
                    //if(txtCodTipDoc.getText().compareTo("199")==0 && txtCodTipDoc.getText().compareTo("202")==0){  for(int intFil=0;int)  }     ***
            
                    vecDat.clear();
                }
            }
            catch (java.sql.SQLException e) 
            {
                e.printStackTrace();
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } 
            catch (Exception evt) 
            {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, evt);
            } 
            finally 
            {
                try {   rst.close();      } catch (Throwable ignore) {  }
                try {   stm.close();      } catch (Throwable ignore) {  }
                try {   con.close();      } catch (Throwable ignore) {  }
                try {   rstLoc.close();   } catch (Throwable ignore) {  }
                try {   stmLoc.close();   } catch (Throwable ignore) {  }
            }
            return blnRes;
        }

        public boolean setearAnticipadoFDM(java.sql.Connection con) 
        {
            boolean blnRes = true;
            String strFecSis = "";
            String strSql = "";
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;

            try 
            {
                if (con != null) 
                {
                    stmLoc = con.createStatement();
                    for (int intFil = 0; intFil < objTblMod.getRowCount(); intFil++)
                    {
                        strSql = "";
                        String strAnt = null;
                        //String strAntMov=null;

                        if (txtCodTipDoc.getText().compareTo("192") == 0)
                        {
                            strAnt = objUti.parseString(objTblMod.getValueAt(intFil, objTblMod.getColumnCount() - 1).toString());
                            if (strAnt.compareTo("0.0") == 0) 
                            {
                                strAnt = "NULL";
                            }
                            else
                            {
                                strAnt = "-" + strAnt;
                            }
                            strSql ="";
                            strSql+=" update tbm_ingegrmentra set nd_valrub = " + strAnt;
                            strSql+=" where co_emp = " + objZafParSis.getCodigoEmpresa();
                            strSql+=" and co_tra = " + objTblMod.getValueAt(intFil, INT_TBL_CODTRA).toString();
                            strSql+=" and ne_ani = " + cboPerAAAA.getSelectedItem().toString();
                            strSql+=" and ne_mes = " + cboPerMes.getSelectedIndex();
                            strSql+=" and ne_per in (0, 2) ";
                            strSql+=" and co_rub = 24 ";
                            //System.out.println("SQL Act Anticipo(Sueldos) FDM: " + strSql);
                            stmLoc.executeUpdate(strSql);
                        }
                        else if (txtCodTipDoc.getText().compareTo("199") == 0) 
                        {
                            //if(arrLstAntBon.get(intFil).toString().compareTo("0.0")!=0){
                            strAnt = objUti.parseString(objTblMod.getValueAt(intFil, objTblMod.getColumnCount() - 1).toString());
                            if (strAnt.compareTo("0.0") == 0) 
                            {
                                strAnt = "NULL";
                            } 
                            else 
                            {
                                strAnt = "-" + strAnt;
                            }
                            strSql ="";
                            strSql+=" update tbm_ingegrmentra set nd_valrub = " + strAnt;
                            strSql+=" where co_emp = " + objZafParSis.getCodigoEmpresa();
                            strSql+=" and co_tra = " + objTblMod.getValueAt(intFil, INT_TBL_CODTRA).toString();
                            strSql+=" and ne_ani = " + cboPerAAAA.getSelectedItem().toString();
                            strSql+=" and ne_mes = " + cboPerMes.getSelectedIndex();
                            strSql+=" and ne_per in (0, 2) ";
                            strSql+=" and co_rub = 25 ";
                            //System.out.println("SQL Act Anticipo(Bono) FDM: " + strSql);
                            stmLoc.executeUpdate(strSql);
//                        }
                        } else if (txtCodTipDoc.getText().compareTo("202") == 0) {

//                        if(arrLstAntMov.get(intFil).toString().compareTo("0.0")!=0){
//                            strAntMov=arrLstAntMov.get(intFil).toString();
                            strAnt = objUti.parseString(objTblMod.getValueAt(intFil, objTblMod.getColumnCount() - 1).toString());
                            if (strAnt.compareTo("0.0") == 0) {
                                strAnt = "NULL";
                            } else {
                                strAnt = "-" + strAnt;
                            }
                            strSql = "";
                            strSql += "update tbm_ingegrmentra set nd_valrub = " + strAnt;
                            strSql += " where co_emp = " + objZafParSis.getCodigoEmpresa();
                            strSql += " and co_tra = " + objTblMod.getValueAt(intFil, INT_TBL_CODTRA).toString();
                            strSql += " and ne_ani = " + cboPerAAAA.getSelectedItem().toString();
                            strSql += " and ne_mes = " + cboPerMes.getSelectedIndex();
                            strSql += " and ne_per in (0, 2) ";
                            strSql += " and co_rub = 26 ";
                            //System.out.println("query act anticipo(movilizacion) FDM: " + strSql);
                            stmLoc.executeUpdate(strSql);
//                        }
                        }
                    }
                }

            }
            catch (java.sql.SQLException Evt) 
            {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } 
            catch (Exception Evt)
            {
                blnRes = false;
                Evt.printStackTrace();
                objUti.mostrarMsgErr_F1(this, Evt);
            } 
            finally 
            {
                try {   stmLoc.close();  } catch (Throwable ignore) {  }
                try {   rstLoc.close();  } catch (Throwable ignore) {  }
            }
            return blnRes;
        }

        public boolean insertarCabReg(java.sql.Connection con, int intCodDoc, int intNumDoc, String strFecha, int local, int tipoDocumento, String numeroDiario) 
        {
            boolean blnRes = false;
            String strFecSis = "";
            String strSql = "";
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;

            try 
            {
                if (con != null) 
                {
                    stmLoc = con.createStatement();

                    strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());

                    int intAAAAPer = Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
                    int intMesPer = cboPerMes.getSelectedIndex();
                    int intPer = cboPer.getSelectedIndex();
                    String strStImp = "N";
                    String strStReg = "A";

                    strSql = "INSERT INTO tbm_cabrolpag(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, nd_valdoc, co_cta, ne_ani, ne_mes, ne_per, st_imp, tx_obs1,";
                    strSql += " tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod) VALUES (";
                    strSql += " " + objZafParSis.getCodigoEmpresa() + ",";
                    strSql += " " + objZafParSis.getCodigoLocal() + ",";
                    strSql += " " + txtCodTipDoc.getText() + ",";
                    strSql += " " + intCodDoc + ",";
                    strSql += " '" + strFecha + "',";
                    //strSql+= " " + txtNumDoc.getText() + ",";
                    strSql += " " + intCodDoc + ",";
                    strSql += " " + objUti.redondear(objUti.parseDouble(txtValDoc.getText()), objZafParSis.getDecimalesMostrar()) + ",";
                    strSql += " " + txtCodCta.getText() + ",";
                    strSql += " " + intAAAAPer + ",";
                    strSql += " " + intMesPer + ",";
                    strSql += " " + intPer + ",";
                    strSql += " " + objUti.codificar(strStImp) + ",";
                    strSql += " " + objUti.codificar(txtObs1.getText()) + ",";
                    strSql += " " + objUti.codificar(txtObs2.getText()) + ",";
                    strSql += " " + objUti.codificar(strStReg) + ",";
                    strSql += " " + "current_timestamp" + ",";
                    strSql += " " + "null" + ",";
                    strSql += " " + objZafParSis.getCodigoUsuario() + ",";
                    strSql += " " + "null";
                    strSql += ")";
                    
                    //<editor-fold defaultstate="collapsed" desc="/* Comentado Borrar Posteriormente */">
                    //  if(cboPer.getSelectedItem().toString().equals("Primera quincena")){
                    //        strSql+= " " + "null";
                    //        strSql+= ")";
                    //  }else{
                    //        strSql+= " " + "null" + ",";
                    //        strSql+= " " + local + ",";
                    //        strSql+= " " + tipoDocumento + ",";
                    //        strSql+= " " + numeroDiario ;
                    //        strSql+= ")";
                    //  }
                    //</editor-fold>

                    //System.out.println("SQL insertarCabReg()=> Insert Cabecera Rol Pagos: " + strSql);
                    stmLoc.executeUpdate(strSql);
                    blnRes = true;
                }
            } 
            catch (java.sql.SQLException Evt) {  blnRes = false;  objUti.mostrarMsgErr_F1(this, Evt);    }
            catch (Exception Evt) {  blnRes = false;    objUti.mostrarMsgErr_F1(this, Evt);       } 
            finally
            {
                try {  stmLoc.close();    } catch (Throwable ignore) {      }
                try {  rstLoc.close();    } catch (Throwable ignore) {      }
            }
            return blnRes;
        }

        private boolean insertarDetalleProvisiones(java.sql.Connection con) 
        {
            boolean blnRes = true;
            String strSql = "";
            java.sql.Statement stmLoc = null, stmLocAux = null;
            java.sql.ResultSet rstLoc = null, rstLocAux = null;
            double dblSueldo = 0;
            double dblHE1 = 0;
            double dblHE2 = 0;
            double dblCom = 0; //Rose
            int intNeTipPro = 0;

            try
            {
                if (con != null) 
                {
                    stmLoc = con.createStatement();
                    if (txtCodTipDoc.getText().compareTo("192") == 0) 
                    {
                        intNeTipPro = 1;
                    }
                    else if (txtCodTipDoc.getText().compareTo("199") == 0) 
                    {
                        intNeTipPro = 2;
                    }
                    else if (txtCodTipDoc.getText().compareTo("202") == 0) 
                    {
                        intNeTipPro = 3;
                    }

                    for (int i = 0; i < tblDatProv.getRowCount(); i++) 
                    {
                        dblSueldo = objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_TOTREC + 1));
                        dblHE1 = objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_TOTREC + 2));
                        dblHE2 = objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_TOTREC + 3));
                        
                        if(intNeTipPro == 1) //Rose: 30/Ene/2016
                        { 
                             dblCom = objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_TOTREC + 9));  
                        }
                        
                        double dblNdSue = 0; //dblSueldo + dblHE1 + dblHE2 + dblCom;
                        String strSue = "";

                        if (intNeTipPro == 1) 
                        {
                            dblNdSue = dblSueldo + dblHE1 + dblHE2 + dblCom;//Rose
                        } 
                        else if (intNeTipPro == 2 || intNeTipPro == 3) 
                        {
                            dblNdSue = dblSueldo;
                        }

                        if (dblNdSue > 0) 
                        {
                            strSue = "" + dblNdSue;
                        } 
                        else
                        {
                            strSue = "" + null;
                        }

                        strSql = " INSERT INTO tbm_bensocmentra( " + "\n"
                                + "co_emp, co_tra, ne_ani, ne_mes, ne_tippro, tx_tiptra, nd_sue, " + "\n"
                                + "nd_valdectersue, nd_valpagdectersue, nd_valsbu, nd_valdeccuasue, " + "\n"
                                + "nd_valpagdeccuasue, nd_porfonres, nd_valfonres, nd_valpagfonres, " + "\n"
                                + "nd_valvac, nd_valpagvac, nd_porapopaties, nd_valapopaties, nd_valpagapopaties)" + "\n"
                                + "VALUES (" + "\n"
                                + objZafParSis.getCodigoEmpresa() + "\n"
                                + " , " + objTblModProv.getValueAt(i, INT_TBL_PROV_CODTRA).toString() + "\n"
                                + " , " + cboPerAAAA.getSelectedItem().toString() + "\n"
                                + " , " + cboPerMes.getSelectedIndex() + "\n"
                                + " , " + intNeTipPro + "\n"
                                + " , " + null + "\n"
                                + " , " + strSue + "\n"
                                + " , " + objUti.redondear(objUti.parseDouble(objTblModProv.getValueAt(i, INT_TBL_PROV_DECIMO_TERCER_SUELDO)), 2) + "\n"
                                + " , " + null + "\n"
                                + " , " + dblSBU + "\n"
                                + " , " + objUti.redondear(objUti.parseDouble(objTblModProv.getValueAt(i, INT_TBL_PROV_DECIMO_CUARTO_SUELDO)), 2) + "\n"
                                + " , " + null + "\n"
                                + " , " + dblPorFonRes + "\n"
                                + " , " + objUti.redondear(objUti.parseDouble(objTblModProv.getValueAt(i, INT_TBL_PROV_FONDO_RESERVA)), 2) + "\n"
                                + " , " + null + "\n"
                                + " , " + objUti.redondear(objUti.parseDouble(objTblModProv.getValueAt(i, INT_TBL_PROV_VACACIONES)), 2) + "\n"
                                + " , " + null + "\n"
                                + " , " + dblPorApoPatIes + "\n"
                                + " , " + objUti.redondear(objUti.parseDouble(objTblModProv.getValueAt(i, INT_TBL_PROV_APORTE_PATRONAL)), 2) + "\n"
                                + " , " + null + "\n"
                                + ");";
                        System.out.println("insertarDetalleProvisiones.insertTbmbensoctra: " + strSql);
                        stmLoc.executeUpdate(strSql);
                    }
                }
            } 
            catch (java.sql.SQLException Evt) 
            {
                Evt.printStackTrace();
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            catch (Exception Evt) 
            {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            finally
            {
                try {  stmLoc.close();  } catch (Throwable ignore) {  }
                try {  rstLoc.close();  } catch (Throwable ignore) {  }
            }

            return blnRes;
        }

        public boolean actualizarRelacionesRolProv(java.sql.Connection con, int empresa, int local, int tipoDocumento, String codigoDocumentoRol, String codigoDocumentoProv) 
        {
            boolean blnRes = false;
            String strSql = "";
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;

            try 
            {
                if (con != null) 
                {
                    stmLoc = con.createStatement();

                    strSql = "";
                    strSql += " update tbm_cabrolpag set co_locRelProBenSol= " + local + " , co_tipDocRelProBenSol = " + tipoDocumento + " , ";
                    strSql += " co_docRelProBenSol = " + codigoDocumentoProv;
                    strSql += " WHERE co_emp = " + empresa;
                    strSql += " AND co_loc = " + local;
                    strSql += " AND co_tipdoc = " + txtCodTipDoc.getText();
                    strSql += " AND co_doc = " + codigoDocumentoRol;
                    //System.out.println("act rela: " + strSql);
                    stmLoc.executeUpdate(strSql);
                    blnRes = true;
                }
            } 
            catch (java.sql.SQLException Evt) {   blnRes = false;    objUti.mostrarMsgErr_F1(this, Evt);       } 
            catch (Exception Evt) {    blnRes = false;        objUti.mostrarMsgErr_F1(this, Evt);        } 
            finally 
            {
                try {   stmLoc.close();   } catch (Throwable ignore) {   }
                try {   rstLoc.close();   } catch (Throwable ignore) {   }
            }
            return blnRes;

        }

        public boolean insertarDetReg(java.sql.Connection con, int intCodDoc, int intNumDoc, String strFecha) 
        {
            boolean blnRes = true;
            String strSql = "";
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;

            try 
            {
                if (con != null) 
                {

                    stmLoc = con.createStatement();

                    // ArrayList<String> arrLst=null;
                    // try{
                    //     arrLst = new ArrayList<String>();
                    //                          
                    //     int intAño=Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
                    //     int intMes=cboPerMes.getSelectedIndex();
                    //                
                    //     strSQL="";
                    //     //strSQL="select * from tbm_rubrolpag where co_rub in (select distinct co_rub from tbm_ingEgrMenTra) order by co_rub asc";
                    //     strSQL+="select * from tbm_rubrolpag";
                    //                strSQL+=" where co_rub in (select distinct co_rub from tbm_ingEgrMenTra)";
                    //                strSQL+=" and extract(year from fe_ing)<="+intAño;
                    //                strSQL+=" and extract(month from fe_ing)<="+intMes;
                    //                strSQL+=" order by tx_tiprub desc,co_rub asc";
                    //                rstLoc=stmLoc.executeQuery(strSQL);
                    //                while(rstLoc.next()){
                    //                    arrLst.add(rstLoc.getString("co_rub"));
                    //                }
                    //            }catch(SQLException ex)
                    //            {
                    //                objUti.mostrarMsgErr_F1(this, ex);
                    //                return false;
                    //            }
                    int intCoReg = 0;
                    int intPosCoRub = 0;
                    /*INSERT EN EL DETALLE*/
                    for (int i = 0; i < tblDat.getRowCount(); i++) 
                    {
                        int intPosCol = INT_TBL_TOTREC + 1;
                        for (int x = 0; x < arrLst.size(); x++) 
                        {
                            intCoReg = intCoReg + 1;
                            strSql = "INSERT INTO tbm_detrolpag(";
                            strSql += " co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_tra, co_rub, nd_valrub) VALUES (";
                            strSql += " " + objZafParSis.getCodigoEmpresa() + ",";
                            strSql += " " + objZafParSis.getCodigoLocal() + ",";
                            strSql += " " + txtCodTipDoc.getText() + ",";
                            strSql += " " + intCodDoc + ",";
                            strSql += " " + intCoReg + ",";
                            strSql += " " + objTblMod.getValueAt(i, INT_TBL_CODTRA).toString() + ",";
                            strSql += " " + arrLst.get(intPosCoRub).toString() + ",";
                            String strNdValRub = objTblMod.getValueAt(i, intPosCol).toString();
                            if (strNdValRub.compareTo("0.0") == 0) 
                            {
                                strNdValRub = "NULL";
                            } 
                            else
                            {
                                strNdValRub = objTblMod.getValueAt(i, intPosCol).toString();
                            }
                            strSql += " " + strNdValRub + ");";
                            System.out.println("query insert detalle rol pagos: " + strSql);
                            stmLoc.executeUpdate(strSql);
                            intPosCol++;
                            intPosCoRub++;
                        }
                        intPosCoRub = 0;
                    }
                }
            }
            catch (java.sql.SQLException Evt) {     blnRes = false;       objUti.mostrarMsgErr_F1(this, Evt);       } 
            catch (Exception Evt) {    blnRes = false;          objUti.mostrarMsgErr_F1(this, Evt);             }
            finally 
            {
                try {  stmLoc.close();  } catch (Throwable ignore) {    }
                try {  rstLoc.close();  } catch (Throwable ignore) {    }
            }
            return blnRes;
        }
        
        /*
         public boolean conciliarPeriodo(java.sql.Connection con){
         boolean blnRes=false;
         String strSql="";
         java.sql.Statement stmLoc = null;
         java.sql.ResultSet rstLoc = null;

         try{
         if(con!=null){
            
         stmLoc=con.createStatement();
         String strCoGrp="";
         if(txtCodTipDoc.getText().compareTo("192")==0){
         strCoGrp="1";
         }else if(txtCodTipDoc.getText().compareTo("199")==0){
         strCoGrp="2";
         }else{
         strCoGrp="3";
         }
            
         strSql="update tbm_ingegrmentra set st_rolpaggen='S'";
         strSql+=" where co_emp = " + objZafParSis.getCodigoEmpresa();
         strSql+=" and ne_ani = " + Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
         strSql+=" and ne_mes = " + cboPerMes.getSelectedIndex();
         strSql+=" and ne_per = " + cboPer.getSelectedIndex();
         strSql+=" and co_rub in (select distinct co_rub from tbm_rubrolpag where co_grp=" + strCoGrp+")";
         System.out.println("query actualizacion st_rolpaggen rol pagos: " + strSql);
         stmLoc.executeUpdate(strSql);
         blnRes=true;
         }
         }catch(java.sql.SQLException Evt){ 
         Evt.printStackTrace();
         blnRes=false;  
         objUti.mostrarMsgErr_F1(this, Evt); 
         }catch(Exception Evt){ 
         Evt.printStackTrace();
         blnRes=false;  
         objUti.mostrarMsgErr_F1(this, Evt); 
         }finally {
         try{stmLoc.close();}catch(Throwable ignore){}
         try{rstLoc.close();}catch(Throwable ignore){}
         }
         return blnRes;         
         }*/

        private boolean validarDat() 
        {
            boolean blnRes = true;
            if (txtCodTipDoc.getText().trim().equals(""))
            {
                MensajeInf("Ingrese tipo el documento");
                return false;
            }

            return blnRes;
        }

        public boolean modificar()
        {
            boolean blnRes = false;
            //generaAsiento();
            //boolean blnProv=false;
            if (cboPer.getSelectedIndex() == 2) 
            {
                if (generaDetalleProvisiones()) 
                {
                    //blnProv=generaAsientoProvisiones();
                }
                blnRes = insertarReg();
            }
            //objAsiDia.setEditable(true);
            return blnRes;
        }

        public boolean modificarCab(java.sql.Connection conn, int intCodDoc) 
        {
            boolean blnRes = false;

            return blnRes;
        }

        private void noEditable(boolean blnEst) 
        {
            txtObs1.setEditable(false);
            txtObs2.setEditable(false);
        }

        public boolean cancelar() 
        {
            boolean blnRes = true;
            try
            {
                if (blnHayCam || objTblMod.isDataModelChanged()) 
                {
                    if (objTooBar.getEstado() == 'n' || objTooBar.getEstado() == 'm') 
                    {
                        if (!isRegPro()) 
                        {
                            return false;
                        }
                    }
                }
                if (rstCab != null)
                {
                    rstCab.close();
                    if (STM_GLO != null)
                    {
                        STM_GLO.close();
                        STM_GLO = null;
                    }
                    rstCab = null;

                }
            } 
            catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e);     } 
            catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e);       }
            clnTextos();
            blnHayCam = false;

            return blnRes;
        }

        public boolean aceptar() 
        {
            return true;
        }

        public boolean afterAceptar() 
        {
            return true;
        }

        public boolean afterAnular() 
        {
            return true;
        }

        public boolean afterCancelar() 
        {
            return true;
        }

        public boolean afterConsultar()
        {
 //           objTooBar.setEstado('m');//Bostel
            return true;
        }

        public boolean afterEliminar() 
        {
            return true;
        }

        public boolean afterImprimir() 
        {
            return true;
        }

        public boolean afterInsertar() 
        {
            this.setEstado('w');
            return true;
        }

        public boolean afterModificar() 
        {
            this.setEstado('w');
            return true;
        }

        public boolean afterVistaPreliminar() 
        {
            return true;
        }

        /**
         * Esta función muestra un mensaje "showConfirmDialog". Presenta las
         * opciones Si, No y Cancelar. El usuario es quien determina lo que debe
         * hacer el sistema seleccionando una de las opciones que se presentan.
         */
        private int mostrarMsgCon(String strMsg) 
        {
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
        private boolean isRegPro() 
        {
            boolean blnRes = true;
            String strAux = "¿Desea guardar los cambios efectuados a éste registro?\n";
            strAux += "Si no guarda los cambios perderá toda la información que no haya guardado.";
            switch (mostrarMsgCon(strAux))
            {
                case 0: //YES_OPTION
                    switch (objTooBar.getEstado()) 
                    {
                        case 'n': //Insertar
                            blnRes = objTooBar.insertar();
                            break;
                        case 'm': //Modificar
                            //blnRes=objTooBar.modificar();
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

        /**
         * 
         * @return 
         */
        public boolean consultar() 
        {
            //return _consultar(FilSql());  //Esto se realiza en caso de que el modo de operacion sea Consulta.
            consultarReg();
            return true;
        }

        private void mostrarEstado(String strStReg)
        {
            setEstado('w');//l-c-x-y-z-n-m-e-a-w
            if (strStReg.equals("I"))
            {
                setEstadoRegistro("Anulado");
                setEnabledModificar(false);
                setEnabledEliminar(false);
                setEnabledAnular(false);
            }
            else
            {
                if (strStReg.equals("E"))
                {
                    setEstadoRegistro("Eliminado");
                    setEnabledModificar(false);
                    setEnabledEliminar(false);
                    setEnabledAnular(false);
                }
                else 
                {
                    setEstadoRegistro("");
                }
            }
        }

        public void clickModificar() 
        {
//            boolean blnProv = false;
//            if (cboPer.getSelectedIndex() == 2) 
//            {
//                if (generaAsiento()) 
//                {
//                    if (generaDetalleProvisiones()) Bostel nodebe generar nada solo al dar click en modificar
//                    {
//                        blnProv = generaAsientoProvisiones();
//                    }
//                }
//            }
        }

        public boolean vistaPreliminar() 
        {
            if (objThrGUI == null) 
            {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;
        }

        public boolean imprimir() 
        {
            if (objThrGUI == null) 
            {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.setIndFunEje(0);
                objThrGUI.start();
            }
            return true;
        }

        public void clickImprimir() 
        {
        }

        public void clickVisPreliminar() 
        {
        }

        public void clickCancelar() 
        {
            clnTextos();
        }

        public void cierraConnections()
        {

        }

        public boolean beforeAceptar() 
        {
            return true;
        }

        public boolean beforeAnular() 
        {
            return true;
        }

        public boolean beforeCancelar()
        {
            return true;
        }

        public boolean beforeConsultar() 
        {
            return true;
        }

        public boolean beforeEliminar() 
        {
            return true;
        }

        public boolean beforeImprimir() 
        {
            return true;
        }

        public boolean beforeInsertar() 
        {
            return true;
        }

        public boolean beforeModificar() 
        {
            return true;
        }

        public boolean beforeVistaPreliminar() 
        {
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
    private boolean consultarReg() 
    {
        boolean blnRes = true;
        String strSQL = "";
        try
        {
            conCab = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conCab != null) 
            {
                stmCab = conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que sólo se muestre los documentos asignados al programa.
                //  if (txtDesCorTipDoc.getText().equals(""))
                //  {
                
                if (!blnEstCarSolHSE) 
                {
                    strSQL += " select * from tbm_cabRolPag a1";
                    strSQL += " INNER JOIN tbm_cabTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                    strSQL += " LEFT OUTER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
                    strSQL += " WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                    strSQL += " AND a1.co_loc=" + objZafParSis.getCodigoLocal();
                    strSQL += " AND a5.co_mnu=" + objZafParSis.getCodigoMenu();
                    strSQL += " AND a1.st_reg<>'E'";
                }
                 // }

                strAux = txtCodTipDoc.getText();
                if (!strAux.equals("")) 
                {
                    strSQL += " AND a1.co_tipdoc=" + strAux;
                }

                if (txtFecDoc.isFecha()) 
                {
                    strSQL += " AND a1.fe_doc='" + objUti.formatearFecha(txtFecDoc.getText(), "dd/MM/yyyy", objZafParSis.getFormatoFechaBaseDatos()) + "'";
                }

                strAux = txtCodDoc.getText();
                if (!strAux.equals("")) 
                {
                    strSQL += " AND a1.co_doc=" + strAux;
                }

                if (cboPerAAAA.getSelectedIndex() > 0) 
                {
                    strSQL += " AND a1.ne_ani=" + cboPerAAAA.getSelectedItem();
                }

                if (cboPerMes.getSelectedIndex() > 0) 
                {
                    strSQL += " AND a1.ne_mes=" + cboPerMes.getSelectedIndex();
                }

                if (cboPer.getSelectedIndex() > 0) 
                {
                    strSQL += " AND a1.ne_per=" + cboPer.getSelectedIndex();
                }

                strSQL += " ORDER BY a1.co_emp, a1.co_doc";
                System.out.println("Query consulta.consultarReg(): " + strSQL);
                rstCab = stmCab.executeQuery(strSQL);
                if (rstCab.next()) 
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                }
                else 
                {
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    clnTextos();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
            }
        } 
        catch (java.sql.SQLException e) {   blnRes = false;      objUti.mostrarMsgErr_F1(this, e);  } 
        catch (Exception e) {      blnRes = false;     objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }

    /**
     * Esta función permite cargar el registro seleccionado.
     *
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg() 
    {
        boolean blnRes = true;
        try 
        {
            if (cargarCabReg())
            {
                configurarTabla(2);
                agregarColTblDat();
                if (cboPer.getSelectedIndex() == 1)
                {
                    if (cargarDetReg()) 
                    {
                        objAsiDia.consultarDiario(intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                        // generaAsiento();
                    }
                }
                else 
                {
                    //  if (cargarDetReg() && cargarDetProv())
                    if (cargarDetReg()) 
                    {
                        //System.out.println("co_tipdoc ----> " + Integer.parseInt(txtCodTipDoc.getText()));
                        //System.out.println("co_doc ----> " + Integer.parseInt(txtCodDoc.getText()));
                        if (blnEstCarSolHSE) 
                        {
                            intCodEmp = Integer.parseInt(strCodEmpAut);
                            intCodLoc = Integer.parseInt(strCodLocAut);
                            // generaDetalleProvisiones();
                            // generaAsientoProvisiones();
                            // objAsiDia.consultarDiario(intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                            // objAsiDiaProv.consultarDiario(intCodEmp, intCodLoc, intCodTipDocProv, intCodDocProv);
                        } 
                        else 
                        {
                            objAsiDia.consultarDiario(intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                            objAsiDiaProv.consultarDiario(intCodEmp, intCodLoc, intCodTipDocProv, intCodDocProv);
                        }
                        // generaAsiento();
                    }
                }
            }
            objAsiDia.setDiarioModificado(false);
            blnHayCam = false;
        } 
        catch (Exception e) {  e.printStackTrace();    blnRes = false;      }
        return blnRes;
    }

    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     *
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg() 
    {
        int intPosRel;
        boolean blnRes = true;
        String strSQL = "";
        java.util.Date datFecDoc;
        java.util.Date datPriCuo;

        try 
        {
            con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                strSQL = "";
                if (!blnEstCarSolHSE)
                {
                    strSQL =" SELECT a.*,b.tx_descor as descortipdoc, b.co_tipdoc , b.tx_deslar as deslartipdoc,c.co_cta, c.tx_codCta as codcta,c.tx_deslar as txdeslarcta  from tbm_cabRolPag a";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS b ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipDoc=b.co_tipDoc";
                    strSQL+=" LEFT OUTER JOIN tbm_plaCta AS c ON (a.co_emp=c.co_emp AND a.co_cta=c.co_cta)";
                    strSQL+=" WHERE a.co_emp = " + rstCab.getString("co_emp");
                    strSQL+=" AND a.co_loc = " + rstCab.getString("co_loc");
                    strSQL+=" AND a.co_tipdoc = " + rstCab.getString("co_tipdoc");
                    strSQL+=" AND a.co_doc=" + rstCab.getString("co_doc");
                } 
                else 
                {
                    strSQL =" SELECT a.*,b.tx_descor as descortipdoc, b.co_tipdoc , b.tx_deslar as deslartipdoc,c.co_cta, c.tx_codCta as codcta,c.tx_deslar as txdeslarcta  from tbm_cabRolPag a";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS b ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipDoc=b.co_tipDoc";
                    strSQL+=" LEFT OUTER JOIN tbm_plaCta AS c ON (a.co_emp=c.co_emp AND a.co_cta=c.co_cta)";
                    strSQL+=" WHERE a.co_emp = " + strCodEmpAut;
                    strSQL+=" AND a.co_loc = " + strCodLocAut;
                    strSQL+=" AND a.co_tipdoc = " + strCodTipDocAut;
                    strSQL+=" AND a.ne_numdoc = " + strNeNumDocAut;
                    strSQL+=" AND a.co_doc=" + strCodDocAut;
                }

                rst = stm.executeQuery(strSQL);
                if (rst.next()) 
                {
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

                    txtValDoc.setText("" + objUti.redondear(rst.getDouble("nd_valdoc"), objZafParSis.getDecimalesMostrar()));

                    if (blnEstCarSolHSE) 
                    {
                        cboPerAAAA.addItem(rst.getString("ne_ani"));
                        cboPerAAAA.setSelectedIndex(1);
                    }
                    else 
                    {
                        cboPerAAAA.setSelectedItem(rst.getString("ne_ani"));
                    }

                    cboPerMes.setSelectedIndex(rst.getInt("ne_mes"));
                    cboPer.setSelectedIndex(rst.getInt("ne_per"));

                    strAux = rst.getString("tx_obs1");
                    txtObs1.setText((strAux == null) ? "" : strAux);
                    strAux = rst.getString("tx_obs2");
                    txtObs2.setText((strAux == null) ? "" : strAux);

                    //Mostrar el estado del registro.
                    strAux = rst.getString("st_reg");
                    if (strAux.equals("A"))
                    {
                        strAux = "Activo";
                    } 
                    else if (strAux.equals("I")) 
                    {
                        strAux = "Anulado";
                    } 
                    else
                    {
                        strAux = "Otro";
                    }
                    objTooBar.setEstadoRegistro(strAux);

                    // intCodTipDocProv = rst.getInt("co_tipDocRelProBenSol");
                    // intCodDocProv = rst.getInt("co_docRelProBenSol");
                } 
                else
                {
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
            if (!blnEstCarSolHSE) 
            {
                intPosRel = rstCab.getRow();
                rstCab.last();
                objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
                rstCab.absolute(intPosRel);
            } 
            else
            {
                intPosRel = 1;
                objTooBar.setPosicionRelativa("" + intPosRel + " / " + intPosRel);
            }
        } 
        catch (java.sql.SQLException e) {  blnRes = false;  objUti.mostrarMsgErr_F1(this, e);   } 
        catch (Exception e) {   blnRes = false;   e.printStackTrace();   objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }

    /**
     * Función que se utiliza en modo consulta para visualizar el detalle de Provisiones.
     * Pestaña (Provisiones)
     * @return 
     */
    private boolean cargarDetProv() 
    {
        boolean blnRes = true;
        String strSQL;
        int intTipPro = 0;
        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        try 
        {
            con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stmLoc = con.createStatement();

                strSQL  ="";
                strSQL+=" SELECT a.* , (b.tx_ape || ' ' || b.tx_nom) as empleado FROM tbm_benSocMenTra a";
                strSQL+=" INNER JOIN tbm_tra b on (b.co_tra = a.co_tra)";
                strSQL+=" WHERE a.co_emp = " + objZafParSis.getCodigoEmpresa();
                strSQL+=" AND a.ne_ani = " + cboPerAAAA.getSelectedItem().toString();
                strSQL+=" AND a.ne_mes = " + cboPerMes.getSelectedIndex();
                
                if (txtCodTipDoc.getText().compareTo("192") == 0)       intTipPro = 1;
                else if (txtCodTipDoc.getText().compareTo("199") == 0)  intTipPro = 2;
                else if (txtCodTipDoc.getText().compareTo("202") == 0)  intTipPro = 3;
              
                strSQL+=" AND a.ne_tippro = " + intTipPro;
                strSQL+=" ORDER BY empleado asc";

                //System.out.println("cargarDetProv.SQL Obtiene Provisiones Empleados: " + strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                double dblTotProvTra = 0;

                vecDatProv = new Vector();
                
                //Valida que se presente el detalle de provisiones en Roles de Pago de Fin de Mes.
                if (cboPer.getSelectedIndex() == 2) 
                {
                    while (rstLoc.next()) 
                    {
                        dblTotProvTra = 0;
                        Vector vecReg = new Vector();
                        vecReg.add(INT_TBL_PROV_LINEA, "");
                        vecReg.add(INT_TBL_PROV_CODTRA, rstLoc.getInt("co_tra"));
                        vecReg.add(INT_TBL_PROV_NOMTRA, rstLoc.getString("empleado"));
                        vecReg.add(INT_TBL_PROV_DECIMO_TERCER_SUELDO, rstLoc.getDouble("nd_valDecTerSue"));
                        dblTotProvTra += rstLoc.getDouble("nd_valDecTerSue");
                        vecReg.add(INT_TBL_PROV_DECIMO_CUARTO_SUELDO, rstLoc.getDouble("nd_valDecCuaSue"));
                        dblTotProvTra += rstLoc.getDouble("nd_valDecCuaSue");
                        vecReg.add(INT_TBL_PROV_FONDO_RESERVA, rstLoc.getDouble("nd_valFonRes"));
                        dblTotProvTra += rstLoc.getDouble("nd_valFonRes");
                        vecReg.add(INT_TBL_PROV_VACACIONES, rstLoc.getDouble("nd_valVac"));
                        dblTotProvTra += rstLoc.getDouble("nd_valVac");
                        vecReg.add(INT_TBL_PROV_APORTE_PATRONAL, rstLoc.getDouble("nd_valApoPatIes"));
                        dblTotProvTra += rstLoc.getDouble("nd_valApoPatIes");
                        vecReg.add(INT_TBL_PROV_TOTAL_TRA, dblTotProvTra);
                        vecDatProv.add(vecReg);
                    }
                    //Asignar vectores al modelo.
                    objTblModProv.setModoOperacion(objTblMod.INT_TBL_EDI);
                    objTblModProv.setData(vecDatProv);
                    tblDatProv.setModel(objTblModProv);
                    objTblTotProv.calcularTotales();
                    vecDatProv.clear();
                 }
                 else
                 {
                    vecDatProv.clear();
                    objTblModProv.setData(vecDatProv);
                    tblDatProv.setModel(objTblModProv);
                    objTblTotProv.calcularTotales();
                 }
            }
        }
        catch (java.sql.SQLException e) 
        {
            e.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception evt) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, evt);
        } 
        finally 
        {
            try {   con.close();     con = null;     } catch (Throwable ignore) { }
            try {   rstLoc.close();  rstLoc = null;  } catch (Throwable ignore) { }
            try {   stmLoc.close();  stmLoc = null;  } catch (Throwable ignore) { }
        }
        return blnRes;
    }

    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     *
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        String strSQL;
        boolean blnRes = true;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        try
        {
            con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmLoc = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Armar la sentencia SQL.
                strSQL = "";
                if (!blnEstCarSolHSE) 
                {
                    if (cboPer.getSelectedIndex() == 1) 
                    {
                        strSQL+=" select distinct a.co_emp,a.co_loc,a.co_tipdoc,a.co_doc,a.co_tra, b.tx_ide , (b.tx_ape || ' ' || b.tx_nom) as empleado from tbm_detrolpag a";
                        strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra and a.co_emp=" + rstCab.getString("co_emp") + ")";
                    } 
                    else if (cboPer.getSelectedIndex() == 2) 
                    {
                        strSQL+=" select distinct a.co_emp,a.co_loc,a.co_tipdoc,a.co_doc,a.co_tra, b.tx_ide , (b.tx_ape || ' ' || b.tx_nom) as empleado, c.ne_numdialab from tbm_detrolpag a";
                        strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra and a.co_emp=" + rstCab.getString("co_emp") + ")";
                        strSQL+=" LEFT OUTER JOIN tbm_datgeningegrmentra c on (c.co_emp=a.co_emp and c.co_tra=a.co_tra and c.ne_ani=" + cboPerAAAA.getSelectedItem() + " and c.ne_mes=" + cboPerMes.getSelectedIndex() + " and c.ne_per=0)";
                    }
                    strSQL+=" where a.co_emp = " + rstCab.getString("co_emp");
                    strSQL+=" and a.co_loc = " + rstCab.getString("co_loc");
                    strSQL+=" and a.co_tipdoc = " + rstCab.getString("co_tipdoc");
                    strSQL+=" and a.co_doc = " + rstCab.getString("co_doc");
                }
                else 
                {
                    if (cboPer.getSelectedIndex() == 1) 
                    {
                        strSQL+=" select distinct a.co_emp,a.co_loc,a.co_tipdoc,a.co_doc,a.co_tra, b.tx_ide , (b.tx_ape || ' ' || b.tx_nom) as empleado from tbm_detrolpag a";
                        strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra and a.co_emp=" + strCodEmpAut + ")";
                    }
                    else if (cboPer.getSelectedIndex() == 2) 
                    {
                        strSQL+=" select distinct a.co_emp,a.co_loc,a.co_tipdoc,a.co_doc,a.co_tra, b.tx_ide , (b.tx_ape || ' ' || b.tx_nom) as empleado, c.ne_numdialab from tbm_detrolpag a";
                        strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra and a.co_emp=" + strCodEmpAut + ")";
                        strSQL+=" LEFT OUTER JOIN tbm_datgeningegrmentra c on (c.co_emp=a.co_emp and c.co_tra=a.co_tra and c.ne_ani=" + cboPerAAAA.getSelectedItem() + " and c.ne_mes=" + cboPerMes.getSelectedIndex() + " and c.ne_per=0)";
                    }
                    strSQL+=" where a.co_emp = " + strCodEmpAut;
                    strSQL+=" and a.co_loc = " + strCodLocAut;
                    strSQL+=" and a.co_tipdoc = " + strCodTipDocAut;
                    strSQL+=" and a.co_doc = " + strCodDocAut;
                }

                strSQL+=" order by empleado asc";
                System.out.println("cargarDetReg(): " + strSQL);
                rst = stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDat = new Vector();
                //Obtener los registros.
                int intPosCol = INT_TBL_TOTREC + 1;
                int intCont = 0;
                String strCoTra = "";
  
                while (rst.next()) 
                {
                    double dblTotIng = 0;
                    double dblTotEgr = 0;
                    double dblBono = 0;

                    strSQL =" select a.*,(b.tx_ape || ' ' || b.tx_nom) as nomcom,c.tx_tiprub from tbm_detrolpag a";
                    strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra)";
                    strSQL+=" inner join tbm_rubrolpag c on (c.co_rub=a.co_rub)";
                    strSQL+=" where a.co_emp = " + rst.getString("co_emp");
                    strSQL+=" and a.co_loc = " + rst.getString("co_loc");
                    strSQL+=" and a.co_tipdoc = " + rst.getString("co_tipdoc");
                    strSQL+=" and a.co_doc = " + rst.getString("co_doc");
                    strSQL+=" and a.co_tra = " + rst.getString("co_tra");
                    strSQL+=" order by c.tx_tiprub desc,a.co_rub asc";

                    System.out.println("cargarDetReg. SQL Obtiene Rubros RolPag Empleado: " + strSQL);
                    rstLoc = stmLoc.executeQuery(strSQL);

                    if (rstLoc.next()) 
                    {
                        Vector vecReg = new Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        strCoTra = rstLoc.getString("co_tra");
                        vecReg.add(INT_TBL_CODTRA, rstLoc.getString("co_tra"));
                        vecReg.add(INT_TBL_IDTRA, rst.getString("tx_ide"));
                        vecReg.add(INT_TBL_NOMTRA, rstLoc.getString("nomcom"));
                        if (cboPer.getSelectedIndex() == 1) 
                        {
                            vecReg.add(INT_TBL_DIAS_LAB, "");
                        }
                        else if (cboPer.getSelectedIndex() == 2) 
                        {
                            vecReg.add(INT_TBL_DIAS_LAB, rst.getString("ne_numdialab"));
                        }

                        vecReg.add(INT_TBL_TOTING, "");
                        vecReg.add(INT_TBL_TOTEGR, "");
                        vecReg.add(INT_TBL_TOTREC, "");

                        vecReg.add(intPosCol, rstLoc.getDouble("nd_valrub"));
                        dblTotIng = dblTotIng + objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                        intPosCol++;

                        while (rstLoc.next()) 
                        {
                            vecReg.add(intPosCol, rstLoc.getDouble("nd_valrub"));
                            if (rstLoc.getString("tx_tiprub").equals("I")) 
                            {
                                if (rstLoc.getInt("co_rub") == 6) 
                                {
                                    dblBono = rstLoc.getDouble("nd_valrub");
                                }
                                dblTotIng += objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                            } 
                            else if (rstLoc.getString("tx_tiprub").equals("E")) 
                            {
                                dblTotEgr += objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                            }

                            intCont++;
                            intPosCol++;
                        }

                        vecReg.add(vecReg.size(), dblTotIng);
                        intPosCol++;
                        vecReg.add(vecReg.size(), dblTotEgr);
                        vecReg.add(vecReg.size(), objUti.redondear(objUti.parseDouble(dblTotIng + dblTotEgr), objZafParSis.getDecimalesMostrar()));

                        vecReg.set(INT_TBL_TOTING, dblTotIng);
                        vecReg.set(INT_TBL_TOTEGR, dblTotEgr);
                        vecReg.set(INT_TBL_TOTREC, objUti.redondear(objUti.parseDouble(dblTotIng + dblTotEgr), objZafParSis.getDecimalesMostrar()));

                        vecDat.add(vecReg);
                    }
                    intPosCol = INT_TBL_TOTREC + 1;
                }
                
                //Asignar vectores al modelo.
//                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                objTblTot.calcularTotales();
                vecDat.clear();
                
                //<editor-fold defaultstate="collapsed" desc="/* Solicitado por Juan que se visualice el detalle de provisiones. */">
                if(objZafParSis.getCodigoMenu()==3138)
                {
                   cargarDetProv();
                }
                //</editor-fold> 
                
            }
        } 
        catch (java.sql.SQLException e) 
        {
            e.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception evt) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, evt);
        } 
        finally 
        {
            try {  rst.close();    } catch (Throwable ignore) {     }
            try {  stm.close();    } catch (Throwable ignore) {     }
            try {  con.close();    } catch (Throwable ignore) {     }
            try {  rstLoc.close(); } catch (Throwable ignore) {     }
            try {  stmLoc.close(); } catch (Throwable ignore) {     }
        }
        return blnRes;
    }

    /**
     * Esta función permite limpiar el formulario.
     *
     * @return true: Si se pudo limpiar la ventana sin ningán problema.
     * <BR>false: En el caso contrario.
     */
    public boolean clnTextos()
    {

        boolean blnRes = true;
        try 
        {
            strCodTipDoc = "";
            strDesCorTipDoc = "";
            strDesLarTipDoc = "";

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
            cboPerMes.setSelectedIndex(0);
            cboPer.setSelectedIndex(0);

            if (objTblMod != null)
            {
                objAsiDia.inicializar();
                objAsiDiaProv.inicializar();
                objTblMod.removeAllRows();
                objTblModProv.removeAllRows();

                for (int intCol = 0; intCol < tblTot.getColumnCount(); intCol++) 
                {
                    objTblTot.setValueAt(null, 0, intCol);
                }

                for (int intCol = 0; intCol < tblDatProv.getColumnCount(); intCol++) 
                {
                    objTblTotProv.setValueAt(null, 0, intCol);
                }
            }
        } 
        catch (Exception e) {    blnRes = false;    e.printStackTrace();        }
        return blnRes;
    }

    private class ZafMouMotAdaProv extends java.awt.event.MouseMotionAdapter 
    {
        public void mouseMoved(java.awt.event.MouseEvent evt) 
        {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol)
            {
                case INT_TBL_PROV_LINEA:
                    strMsg = "";
                    break;
                //case INT_TBL_PROV_COEMP:
                   //strMsg="Código de la empresa";
                   //break;    
                //case INT_TBL_PROV_NOMEMP:
                    //strMsg="Empresa";
                    //break;    
                case INT_TBL_PROV_CODTRA:
                    strMsg = "Código del empleado";
                    break;
                case INT_TBL_PROV_NOMTRA:
                    strMsg = "Nombres y apellidos del empleado";
                    break;
                case INT_TBL_PROV_DECIMO_TERCER_SUELDO:
                    strMsg = "Décimo Tercer Sueldo";
                    break;
                case INT_TBL_PROV_DECIMO_CUARTO_SUELDO:
                    strMsg = "Décimo Cuarto Sueldo";
                    break;
                case INT_TBL_PROV_FONDO_RESERVA:
                    strMsg = "Fondo de Reserva";
                    break;
                case INT_TBL_PROV_VACACIONES:
                    strMsg = "Vacaciones";
                    break;
                case INT_TBL_PROV_APORTE_PATRONAL:
                    strMsg = "Vacaciones";
                    break;
                case INT_TBL_PROV_TOTAL_TRA:
                    strMsg = "Total";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {
        public void mouseMoved(java.awt.event.MouseEvent evt) 
        {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) 
            {
                case INT_TBL_LINEA:
                    strMsg = "";
                    break;
                case INT_TBL_CODTRA:
                    strMsg = "Código del empleado";
                    break;
                case INT_TBL_IDTRA:
                    strMsg = "Cédula del empleado";
                    break;
                case INT_TBL_NOMTRA:
                    strMsg = "Nombres y apellidos del empleado";
                    break;
                case INT_TBL_DIAS_LAB:
                    strMsg = "Días Laborados";
                    break;
                case INT_TBL_TOTING:
                    strMsg = "Ingresos";
                    break;
                case INT_TBL_TOTEGR:
                    strMsg = "Egresos";
                    break;
                case INT_TBL_TOTREC:
                    strMsg = "Total a Recibir";
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
    private class ZafThreadGUI extends Thread 
    {
        private int intIndFun;

        public ZafThreadGUI() 
        {
            intIndFun = 0;
        }

        public void run() 
        {
            switch (intIndFun)
            {
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
        public void setIndFunEje(int indice) 
        {
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
    private boolean generarRpt(int intTipRpt)
    {
        String strRutRpt = "", strNomRpt = "", strFecHorSer = "", strCamAudRpt = "";
        String strNomEmp = "", strPago = "", strPeriodo = "", strImpresión = "";
        int i, intNumTotRpt;
        boolean blnRes = true;
        try 
        {
            // Reporte habilitado para Roles de Pago de Fin Mes.
            if (cboPer.getSelectedIndex() == 1) 
            {
                mostrarMsgInf("<HTML>No se puede realizar impresiones para el Período seleccionado.</HTML>");
                return false;
            } 
            else 
            {
                objRptSis.cargarListadoReportes();
                objRptSis.setVisible(true);
                if (objRptSis.getOpcionSeleccionada() == objRptSis.INT_OPC_ACE) {

                    intNumTotRpt = objRptSis.getNumeroTotalReportes();
                    for (i = 0; i < intNumTotRpt; i++) 
                    {
                        if (objRptSis.isReporteSeleccionado(i)) 
                        {
                            int intAño = Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
                            int intMes = cboPerMes.getSelectedIndex();
                            int intPer = cboPerMes.getSelectedIndex();
                            //System.out.println(objRptSis.getCodigoReporte(i));
                            switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                            {
                                default:

                                case 433: //Roles de Pago
                                    strRutRpt = objRptSis.getRutaReporte(i);
                                    strNomRpt = objRptSis.getNombreReporte(i);
                                    strCamAudRpt = "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " + strNomRpt + " v0.1    ";
                                    //Inicializar los parametros que se van a pasar al reporte.
                                    strNomEmp = objZafParSis.getNombreEmpresa();
                                    strPago = cboPer.getSelectedItem().toString();
                                    strPeriodo = cboPerMes.getSelectedItem().toString() + " " + cboPerAAAA.getSelectedItem().toString();

                                    //Obtener la fecha y hora del servidor.
                                    datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                                    if (datFecAux != null) 
                                    {
                                        strFecHorSer = objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                                        datFecAux = null;
                                    }

                                    // strSQL="";
                                    // strSQL+="select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_tra, ";
                                    // strSQL+=" (b.tx_ape || ' ' || b.tx_nom) as empleado from tbm_detrolpag a";
                                    // strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra) ";
                                    // strSQL+=" where co_emp="+objZafParSis.getCodigoEmpresa();
                                    // strSQL+=" and co_loc="+objZafParSis.getCodigoLocal();
                                    // strSQL+=" and co_tipdoc="+txtCodTipDoc.getText();
                                    // strSQL+=" and co_doc="+txtCodDoc.getText();
                                    // strSQL+=" group by a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc,a.co_tra, empleado";
                                    // strSQL+=" order by empleado";
                                    
                                    strSQL = "";
                                    strSQL += " SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_tra, \n"
                                            + "       (b.tx_ape || ' ' || b.tx_nom) as empleado, round(d.nd_valRubSinDes,2) as nd_valIESS, e.ne_numDiaLab, null as strPeriodo \n"
                                            + " FROM tbm_detrolpag a\n"
                                            + " INNER JOIN tbm_tra b on (a.co_tra=b.co_tra) \n"
                                            + " INNER JOIN tbm_cabrolpag c on (c.co_emp=a.co_emp and c.co_loc=a.co_loc and c.co_tipdoc=a.co_tipdoc and c.co_doc=a.co_doc)\n"
                                            + " INNER JOIN tbm_ingegrmentra d on (d.co_emp=a.co_emp and d.co_tra=a.co_tra and d.ne_ani=c.ne_ani and d.ne_mes=c.ne_mes and d.ne_per=0 and d.co_rub = 1)\n"
                                            + " LEFT OUTER JOIN tbm_datgeningegrmentra e on (e.co_emp=a.co_emp and e.co_tra=a.co_tra and e.ne_ani=c.ne_ani and e.ne_mes=c.ne_mes and e.ne_per=0)\n"
                                            + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + "\n"
                                            + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + "\n"
                                            + " AND a.co_tipdoc=" + txtCodTipDoc.getText() + "\n"
                                            + " AND a.co_doc=" + txtCodDoc.getText() + "\n"
                                            + " GROUP BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc,a.co_tra, empleado, d.nd_valRubSinDes, e.ne_numDiaLab \n"
                                            + " ORDER BY empleado";
                                    //System.out.println("vista previa SUELDOS: " + strSQL);
                                    break;

                                case 434: //Bono
                                    strRutRpt = objRptSis.getRutaReporte(i);
                                    strNomRpt = objRptSis.getNombreReporte(i);
                                    strCamAudRpt = "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " + strNomRpt + " v0.1    ";
                                    //Inicializar los parametros que se van a pasar al reporte.
                                    strNomEmp = objZafParSis.getNombreEmpresa();
                                    strPago = cboPer.getSelectedItem().toString();
                                    strPeriodo = cboPerMes.getSelectedItem().toString() + " " + cboPerAAAA.getSelectedItem().toString();

                                    //Obtener la fecha y hora del servidor.
                                    datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                                    if (datFecAux != null) 
                                    {
                                        strFecHorSer = objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                                        datFecAux = null;
                                    }

                                    strSQL ="";
                                    strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_tra, \n";
                                    strSQL+="        (b.tx_ape || ' ' || b.tx_nom) as empleado , null as strPeriodo \n";
                                    strSQL+=" FROM tbm_detrolpag a \n";
                                    strSQL+=" INNER JOIN tbm_tra b on (a.co_tra=b.co_tra) \n";
                                    strSQL+=" INNER JOIN tbm_rubrolpag c on (c.co_rub=a.co_rub and c.co_grp=2) \n";
                                    strSQL+=" WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa()+ "\n";
                                    strSQL+=" AND a.co_loc=" + objZafParSis.getCodigoLocal()+ "\n";
                                    strSQL+=" AND a.co_tipdoc=" + txtCodTipDoc.getText()+ "\n";
                                    strSQL+=" AND a.co_doc=" + txtCodDoc.getText()+ "\n";
                                    strSQL+=" AND a.nd_valrub > 0 AND a.nd_valrub is not null \n";
                                    
                                    // strSQL+=" and round((select sum(nd_valrub) as nd_valRubIng from tbm_detrolpag x ";
                                    // strSQL+=" inner join tbm_rubrolpag y on (y.tx_tiprub='I' and x.co_rub=y.co_rub)";
                                    // strSQL+=" where x.co_emp = a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc and x.co_tra=a.co_tra) +  ";
                                    // strSQL+=" (select sum(nd_valrub) as nd_valRubIng from tbm_detrolpag x";
                                    // strSQL+=" inner join tbm_rubrolpag y on (y.tx_tiprub='E' and x.co_rub=y.co_rub) ";
                                    // strSQL+=" where x.co_emp = a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc and x.co_tra=a.co_tra) , 2) >0";
                                    
                                    strSQL += " GROUP BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc,a.co_tra, empleado \n";
                                    strSQL += " ORDER BY empleado";
                                    //System.out.println("vista previa BONO: " + strSQL);
                                    break;

                                case 435: //Movilización
                                    strRutRpt = objRptSis.getRutaReporte(i);
                                    strNomRpt = objRptSis.getNombreReporte(i);
                                    strCamAudRpt = "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " + strNomRpt + " v0.1    ";
                                    //Inicializar los parametros que se van a pasar al reporte.
                                    strNomEmp = objZafParSis.getNombreEmpresa();
                                    strPago = cboPer.getSelectedItem().toString();
                                    strPeriodo = cboPerMes.getSelectedItem().toString() + " " + cboPerAAAA.getSelectedItem().toString();

                                    //Obtener la fecha y hora del servidor.
                                    datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                                    if (datFecAux != null) 
                                    {
                                        strFecHorSer = objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                                        datFecAux = null;
                                    }

                                    strSQL ="";
                                    strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_tra, \n";
                                    strSQL+="        (b.tx_ape || ' ' || b.tx_nom) as empleado ,null as strPeriodo \n";
                                    strSQL+=" FROM tbm_detrolpag a \n";
                                    strSQL+=" INNER JOIN tbm_tra b on (a.co_tra=b.co_tra) \n";
                                    strSQL+=" INNER JOIN tbm_rubrolpag c on (c.co_rub=a.co_rub and c.co_grp=3) \n";
                                    strSQL+=" WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa();
                                    strSQL+=" AND a.co_loc=" + objZafParSis.getCodigoLocal();
                                    strSQL+=" AND a.co_tipdoc=" + txtCodTipDoc.getText();
                                    strSQL+=" AND a.co_doc=" + txtCodDoc.getText();
                                    strSQL+=" AND a.nd_valrub > 0 AND a.nd_valrub is not null \n";
                                    
                                    // strSQL+=" and round((select sum(nd_valrub) as nd_valRubIng from tbm_detrolpag x ";
                                    // strSQL+=" inner join tbm_rubrolpag y on (y.tx_tiprub='I' and x.co_rub=y.co_rub)";
                                    // strSQL+=" where x.co_emp = a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc and x.co_tra=a.co_tra) +  ";
                                    // strSQL+=" (select sum(nd_valrub) as nd_valRubIng from tbm_detrolpag x";
                                    // strSQL+=" inner join tbm_rubrolpag y on (y.tx_tiprub='E' and x.co_rub=y.co_rub) ";
                                    // strSQL+=" where x.co_emp = a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc and x.co_tra=a.co_tra) , 2) >0";
                                    
                                    strSQL += " GROUP BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc,a.co_tra, empleado \n";
                                    strSQL += " ORDER BY empleado";
                                    //System.out.println("vista previa MOVILIZACIÓN: " + strSQL);
                            }

                            java.util.Map mapPar = new java.util.HashMap();
                            mapPar.put("strsql", strSQL);
                            mapPar.put("co_emp", objZafParSis.getCodigoEmpresa());
                            mapPar.put("ne_ani", intAño);
                            mapPar.put("ne_mes", intMes);
                            mapPar.put("ne_per", intPer);
                            mapPar.put("empresa", strNomEmp);
                            mapPar.put("pago", strPago);
                            mapPar.put("periodo", strPeriodo);
                            mapPar.put("impresion", strFecHorSer);
                            mapPar.put("strCamAudRpt", strCamAudRpt);
                            mapPar.put("SUBREPORT_DIR", strRutRpt);

                            // mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                            objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                        }
                    }
                }
            }
        } 
        catch (Exception e) {       blnRes = false; objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }

    private boolean setearFechaDocumento()
    {
        boolean blnRes = false;
        try 
        {
            java.util.Date dateObj = datFecAux;
            java.util.Calendar calObj = java.util.Calendar.getInstance();
            calObj.setTime(dateObj);
            if (cboPer.getSelectedIndex() == 2) 
            {
                //David
                Calendar calFec = Calendar.getInstance();
                calFec.set(Calendar.DAY_OF_MONTH, 1);
                calFec.set(Calendar.YEAR, Integer.valueOf(cboPerAAAA.getSelectedItem().toString()));
                calFec.set(Calendar.MONTH, cboPerMes.getSelectedIndex());

                if (txtCodTipDoc.getText().trim().compareTo("192") == 0) 
                {
                    txtFecDoc.setText(calFec.getActualMaximum(Calendar.DAY_OF_MONTH), calFec.get(Calendar.MONTH), calFec.get(Calendar.YEAR));
                    blnRes = true;
                } 
                else 
                {
                    txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH), calObj.get(java.util.Calendar.MONTH) + 1, calObj.get(java.util.Calendar.YEAR));
                    blnRes = true;
                }
            }
            else
            {
                txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),  calObj.get(java.util.Calendar.MONTH) + 1,   calObj.get(java.util.Calendar.YEAR));
                blnRes = true;
            }
        } 
        catch (Exception e) {  blnRes = false;   objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }

    /**
     * Rose: Función que carga todos los datos para realizar los roles de pago del personal.
     * En esta función se realizan los cáculos de los beneficios sociales como: decimos, vacaciones, etc.
     * Pestaña (General)
     * @return true: Si se pudo realizar la consulta.
     * <BR> false: En el caso contrario.
     */
    public boolean cargarRolPer() 
    {
        boolean blnRes = true;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSQL = "";
        try 
        {
            con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) 
            {
                //int intPer=0;
                arrLstAntSue = new ArrayList<String>();
                arrLstAntBon = new ArrayList<String>();
                arrLstAntMov = new ArrayList<String>();
                //  Calendar calendario = new GregorianCalendar();
                stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmLoc = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                
                //David -> Consultar fecha de la base de Datos.
                RRHHDao dao = new RRHHDao(objUti, objZafParSis);
                String AniMesPer = dao.conAnioUltimoRolGenerado(con, objZafParSis.getCodigoEmpresa(), false);
                int intAni = Integer.valueOf(AniMesPer.split(";")[0]);
                int intMes = Integer.valueOf(AniMesPer.split(";")[1]);
                int intNePer = Integer.valueOf(AniMesPer.split(";")[2]);
                
                cboPerAAAA.setSelectedItem(String.valueOf(intAni)); //Retorna un string del año del ultimo rol generado.
                cboPerMes.setSelectedIndex(intMes);
                cboPer.setSelectedIndex(intNePer);
                setearFechaDocumento();

                strSQL =" SELECT DISTINCT a.co_tra, b.tx_ide, (b.tx_ape || ' ' || b.tx_nom) as empleado ,c.tx_numCtaBan, c.tx_tipCtaBan, c.st_recfonres, c.tx_forrecfonres , d.co_emp, d.co_tra, d.ne_ani, d.ne_mes, d.ne_per, d.ne_numdialab, c.tx_forPagDecTerSue, c.tx_forPagDecCuaSue   \n ";
                strSQL+=" FROM tbm_ingEgrMenTra a \n";
                strSQL+=" INNER JOIN tbm_tra b ON (a.co_tra=b.co_tra) \n";
                strSQL+=" INNER JOIN tbm_traemp c ON (b.co_tra=c.co_tra  and a.co_emp=c.co_emp) \n";
                strSQL+=" LEFT OUTER JOIN tbm_datgeningegrmentra d on (d.co_emp = a.co_emp and d.co_tra = a.co_tra and d.ne_ani = " + intAni + " and d.ne_mes = " + intMes + " and d.ne_per = 0 ) \n";
                strSQL+=" WHERE a.co_emp =" + objZafParSis.getCodigoEmpresa()+"\n";
                strSQL+=" AND a.ne_ani =" + intAni +"\n";
                strSQL+=" AND a.ne_mes =" + intMes +"\n";
                strSQL+=" AND a.ne_per =" + intNePer +"\n";
                strSQL+=" AND c.st_reg='A' \n";
                strSQL+=" AND d.ne_numdialab >0";
                strSQL+=" ORDER BY empleado asc";

                //System.out.println("SQL Cargar Rol Per: " + strSQL);
                rst = stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat = new Vector();
                vecDatProv = new Vector();

                //Obtener los registros.
                // objTooBar.setMenSis("Cargando datos...");
                int intPosCol = INT_TBL_TOTREC + 1;
                int intCont = 0;

                configurarTabla(1);
                agregarColTblDat();

                while (rst.next()) 
                {
                    Tbm_traemp tbm_traemp = new Tbm_traemp();
                    tbm_traemp.setIntCo_tra(rst.getInt("co_tra"));
                    tbm_traemp.setStrTx_Ide(rst.getString("tx_ide"));
                    tbm_traemp.setStrEmpleado(rst.getString("empleado"));
                    tbm_traemp.setStrTx_TipCtaBan(rst.getString("tx_tipCtaBan"));
                    tbm_traemp.setStrTx_NumCtaBan(rst.getString("tx_numCtaBan"));
                    arrLstTbmTraEmp.add(tbm_traemp);

                    strSQL =" select a.*,(b.tx_ape || ' ' || b.tx_nom) as nomcom,c.tx_tiprub,c.co_grp ";
                    strSQL+=" from tbm_ingEgrMenTra a";
                    strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra)";
                    strSQL+=" inner join tbm_rubrolpag c on (c.co_rub=a.co_rub)";
                    strSQL+=" where co_emp = " + objZafParSis.getCodigoEmpresa();
                    strSQL+=" and a.co_tra = " + rst.getString("co_tra");
                    strSQL+=" and ne_ani = " + intAni;
                    strSQL+=" and ne_mes = " + intMes;
                    strSQL+=" and ne_per = " + intNePer;
                    if (objZafParSis.getCodigoMenu() == 3138)
                    {
                        strSQL+=" and c.co_grp = 1";
                    }
                    else 
                    {
                        if (strCodTipDoc.compareTo("199") == 0)       strSQL+=" and c.co_grp = 2";
                        else if (strCodTipDoc.compareTo("202") == 0)  strSQL+=" and c.co_grp = 3";
                        else  strSQL+=" and c.co_grp = 2";
                    }
                    strSQL+=" order by c.tx_tiprub desc,a.co_rub";
                    
                    //System.out.println(" SQL Obtener Rubros RolPag: " + strSQL);
                    rstLoc = stmLoc.executeQuery(strSQL);

                    double dblTotIng = 0;
                    double dblTotEgr = 0;
                    double dblTotEgrSue = 0;
                    double dblTotEgrBon = 0;
                    double dblTotEgrMov = 0;
                    double dblGI1 = 0;
                    double dblGI2 = 0;
                    double dblBon = 0;
                    double dblMov = 0;

                    double dblBaseSueldo = 0;
                    int intCoGrp = 0;
                    if (rstLoc.next()) 
                    {
                        intCoGrp = rstLoc.getInt("co_grp");
                        dblBaseSueldo = 0;

                        Vector vecReg = new Vector();
                        Vector vecRegProv = new Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecRegProv.add(INT_TBL_PROV_LINEA, "");
                        vecReg.add(INT_TBL_CODTRA, rstLoc.getString("co_tra"));
                        vecRegProv.add(INT_TBL_PROV_CODTRA, rstLoc.getString("co_tra"));
                        vecReg.add(INT_TBL_IDTRA, rst.getString("tx_ide"));
                        vecReg.add(INT_TBL_NOMTRA, rstLoc.getString("nomcom"));
                        vecRegProv.add(INT_TBL_PROV_NOMTRA, rstLoc.getString("nomcom"));
                        vecReg.add(INT_TBL_DIAS_LAB, rst.getInt("ne_numdialab"));

                        vecReg.add(INT_TBL_TOTING, "");
                        vecReg.add(INT_TBL_TOTEGR, "");
                        vecReg.add(INT_TBL_TOTREC, "");

                        vecReg.add(intPosCol, rstLoc.getDouble("nd_valrub"));
                        boolean blnMos = false;
                        if (rstLoc.getDouble("nd_valrub") == 0)
                        {
                            blnMos = true;
                        }
                        dblTotIng += objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                        dblBaseSueldo += objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                        dblGI1 += objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                        intPosCol++;

                        while (rstLoc.next()) 
                        {
                            //<editor-fold defaultstate="collapsed" desc="/* Cálculo REMUNERACIÓN BASE. */">
                            if (rstLoc.getInt("co_grp") == 1) //Rol de Pago.
                            {
                                //Se Agrega valor de comisión. REMUNERACION=SUELDO+HE1+HE2+COMISIONES.
                                if (rstLoc.getInt("co_rub") == 1 || rstLoc.getInt("co_rub") == 2 || rstLoc.getInt("co_rub") == 3 || rstLoc.getInt("co_rub") == 33) 
                                {
                                    dblBaseSueldo += rstLoc.getDouble("nd_valrub");
                                }
                            }
                            else //Bono y Movilización.
                            {
                                if (rstLoc.getInt("co_rub") == 6) //Bono.
                                {
                                    dblBaseSueldo += rstLoc.getDouble("nd_valrub");
                                } 
                                else if (rstLoc.getInt("co_rub") == 7) //Movilización.
                                {
                                    dblBaseSueldo += rstLoc.getDouble("nd_valrub");
                                }
                            }
                            //</editor-fold>

                            //<editor-fold defaultstate="collapsed" desc="/* Cálculo INGRESOS - EGRESOS - BONOS - MOVILIZACIÓN */">
                            vecReg.add(intPosCol, rstLoc.getDouble("nd_valrub"));
                            if (rstLoc.getString("tx_tiprub").equals("I")) 
                            {
                                if (rstLoc.getInt("co_rub") == 6) 
                                {
                                    dblBon += rstLoc.getDouble("nd_valrub");
                                    arrLstAntBon.add(String.valueOf(objUti.redondear(objUti.parseDouble(dblBon), objZafParSis.getDecimalesMostrar())));
                                }
                                if (rstLoc.getInt("co_rub") == 7) 
                                {
                                    dblMov += rstLoc.getDouble("nd_valrub");
                                    arrLstAntMov.add(String.valueOf(objUti.redondear(objUti.parseDouble(dblMov), objZafParSis.getDecimalesMostrar())));
                                }

                                if (rstLoc.getInt("co_grp") == 1)
                                {
                                    dblGI1 += rstLoc.getDouble("nd_valrub");
                                } 
                                else if (rstLoc.getInt("co_grp") == 2) 
                                {
                                    dblGI2 += rstLoc.getDouble("nd_valrub");
                                }

                                dblTotIng += objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                            } 
                            else if (rstLoc.getString("tx_tiprub").equals("E")) 
                            {
                                //Acumula el total de egresos para obtener la diferencia que sería el valor a recibir
                                dblTotEgr += objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                                //System.out.println(rstLoc.getString("co_rub"));

                                if (rstLoc.getString("co_rub").compareTo("25") == 0) 
                                {
                                    dblTotEgrBon += objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                                    dblTotEgr -= dblTotEgrBon;
                                }
                                if (rstLoc.getString("co_rub").compareTo("26") == 0) 
                                {
                                    dblTotEgrMov += objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                                    dblTotEgr -= dblTotEgrMov;
                                }
                            }
                            intCont++;
                            intPosCol++;
                            //</editor-fold>
                        }

                        vecReg.add(vecReg.size(), dblTotIng);
                        intPosCol++;
                        vecReg.add(vecReg.size(), dblTotEgr);
                        vecReg.add(vecReg.size(), objUti.redondear(objUti.parseDouble(dblTotIng + dblTotEgr + dblTotEgrBon + dblTotEgrMov), objZafParSis.getDecimalesMostrar()));

                        vecReg.set(INT_TBL_TOTING, dblTotIng);
                        vecReg.set(INT_TBL_TOTEGR, dblTotEgr);
                        vecReg.set(INT_TBL_TOTREC, objUti.redondear(objUti.parseDouble(dblTotIng + dblTotEgr + dblTotEgrBon + dblTotEgrMov), objZafParSis.getDecimalesMostrar()));
               
                 
                        //<editor-fold defaultstate="collapsed" desc="/* PROVISIONES: Carga el detalle de Provisiones al momento de insertar el Rol de Pagos. */">
                        double dblDecTerSueTra = 0;
                        double dblDecCuaSueTra = 0;
                        double dblFonResTra = 0;
                        double dblVacTra = 0;
                        double dblApoPatTra = 0;

                        if (intCoGrp == 1) //Rol de Pago
                        {
                            //<editor-fold defaultstate="collapsed" desc="/* Decimo Tercer Sueldo. */">
                            dblDecTerSueTra = objUti.redondear((dblBaseSueldo / 12), objZafParSis.getDecimalesMostrar());
                            vecRegProv.add(INT_TBL_PROV_DECIMO_TERCER_SUELDO, dblDecTerSueTra);
                            //</editor-fold>
                            
                            //<editor-fold defaultstate="collapsed" desc="/* Decimo Cuarto Sueldo. */">
                            dblDecCuaSueTra = objUti.redondear(((dblSBU / 12 / 30) * (rst.getInt("ne_numdialab"))), objZafParSis.getDecimalesMostrar());
                            vecRegProv.add(INT_TBL_PROV_DECIMO_CUARTO_SUELDO, dblDecCuaSueTra);
                            //</editor-fold>
                            
                            //<editor-fold defaultstate="collapsed" desc="/* Fondo de Reserva. */">
                            if (rst.getString("st_recfonres") != null) 
                            {
                                if (rst.getString("st_recfonres").compareTo("S") == 0)
                                {
                                    if (rst.getString("tx_forrecfonres").compareTo("I") == 0 || rst.getString("tx_forrecfonres").compareTo("R") == 0)
                                    {
                                        dblFonResTra = objUti.redondear((dblBaseSueldo / 12), objZafParSis.getDecimalesMostrar());
                                        vecRegProv.add(INT_TBL_PROV_FONDO_RESERVA, dblFonResTra);
                                    }
                                }
                            } 
                            else 
                            {
                                vecRegProv.add(INT_TBL_PROV_FONDO_RESERVA, null);
                                dblFonResTra = 0;
                            }      
                            //</editor-fold>
                            
                            //<editor-fold defaultstate="collapsed" desc="/* Vacaciones. */">
                            dblVacTra = objUti.redondear((dblBaseSueldo / 24), objZafParSis.getDecimalesMostrar());
                            vecRegProv.add(INT_TBL_PROV_VACACIONES, dblVacTra);
                            //</editor-fold>
                            
                            //<editor-fold defaultstate="collapsed" desc="/* Aporte Patronal. */">
                            dblApoPatTra = objUti.redondear((dblBaseSueldo * (dblPorApoPatIes / 100)), objZafParSis.getDecimalesMostrar());
                            vecRegProv.add(INT_TBL_PROV_APORTE_PATRONAL, dblApoPatTra);
                            //</editor-fold>
                        } 
                        else //Bono/Movilización.
                        {
                            vecRegProv.add(INT_TBL_PROV_DECIMO_TERCER_SUELDO, null);
                            vecRegProv.add(INT_TBL_PROV_DECIMO_CUARTO_SUELDO, null);
                            vecRegProv.add(INT_TBL_PROV_FONDO_RESERVA, null);
                            vecRegProv.add(INT_TBL_PROV_VACACIONES, null);
                            vecRegProv.add(INT_TBL_PROV_APORTE_PATRONAL, null);
                            dblDecTerSueTra = 0;
                            dblDecCuaSueTra = 0;
                            dblFonResTra = 0;
                            dblVacTra = 0;
                            dblApoPatTra = 0;
                        }
                        
                        //<editor-fold defaultstate="collapsed" desc="/* Total Provisiones. */">
                        vecRegProv.add(INT_TBL_PROV_TOTAL_TRA, objUti.redondear((dblDecTerSueTra + dblDecCuaSueTra + dblFonResTra + dblVacTra + dblApoPatTra), objZafParSis.getDecimalesMostrar()));
                        //</editor-fold>
                        
                        //</editor-fold>
                        
                        if (!blnMos) 
                        {
                            vecDat.add(vecReg);
                            vecDatProv.add(vecRegProv);
                        }
                        arrLstAntSue.add(String.valueOf(objUti.redondear(objUti.parseDouble(dblGI1) + dblTotEgr, objZafParSis.getDecimalesMostrar())));
                    }
                    intPosCol = INT_TBL_TOTREC + 1;
                }

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                objTblTot.calcularTotales();
                calcularValorDocumento();
                vecDat.clear();
                //Asignar vectores al modelo de provisiones.
                objTblModProv.setData(vecDatProv);
                tblDatProv.setModel(objTblModProv);
                objTblTotProv.calcularTotales();
                // calcularValorDocumento();
                vecDatProv.clear();

            }
        } 
        catch (java.lang.ArrayIndexOutOfBoundsException Evt) 
        {
            objUti.mostrarMsgErr_F1(this, Evt);
            Evt.printStackTrace();
            blnRes = false;
        }
        catch (java.sql.SQLException Evt) 
        {
            objUti.mostrarMsgErr_F1(this, Evt);
            Evt.printStackTrace();
            blnRes = false;
        } 
        catch (Exception Evt) 
        {
            objUti.mostrarMsgErr_F1(this, Evt);
            Evt.printStackTrace();
            blnRes = false;
        }
        finally 
        {
            try {  rst.close();    } catch (Throwable ignore) {   }
            try {  rstLoc.close(); } catch (Throwable ignore) {   }
            try {  stm.close();    } catch (Throwable ignore) {   }
            try {  stmLoc.close(); } catch (Throwable ignore) {   }
            try {  con.close();    } catch (Throwable ignore) {   }
        }
        return blnRes;
    }

    private void calcularValorDocumento() 
    {
        double dblTot = objUti.redondear(objUti.parseDouble(objTblTot.getValueAt(0, objTblMod.getColumnCount() - 1)), objZafParSis.getDecimalesMostrar());
        txtValDoc.setText(String.valueOf(dblTot));
    }

    public static java.sql.Time SparseToTime(String hora) throws Exception 
    {
        int h, m, s = 0;
        h = Integer.parseInt(hora.charAt(0) + "" + hora.charAt(1));
        m = Integer.parseInt(hora.charAt(3) + "" + hora.charAt(4));
        return (new java.sql.Time(h, m, s));
    }

    protected void finalize() throws Throwable 
    {
        System.gc();
        super.finalize();
    }

    private boolean generaAsiento() 
    {
        boolean blnRes = true;

        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        String strTipDocDes = "";
        BigDecimal bgdDebe;
        int INT_LINEA = 0;         //0) Línea: Se debe asignar una cadena vacía o null. 
        int INT_VEC_CODCTA = 1;    //1) Código de la cuenta (Sistema). 
        int INT_VEC_NUMCTA = 2;    //2) Número de la cuenta (Preimpreso). 
        int INT_VEC_BOTON = 3;     //3) Botón de consulta: Se debe asignar una cadena vacía o null. 
        int INT_VEC_NOMCTA = 4;    //4) Nombre de la cuenta. 
        int INT_VEC_DEBE = 5;      //5) Debe. 
        int INT_VEC_HABER = 6;     //6) Haber. 
        int INT_VEC_REF = 7;       //7) Referencia: Se debe asignar una cadena vacía o null
        int INT_VEC_NUEVO = 8;
        int intColDin = INT_TBL_TOTREC;

        try 
        {
            objAsiDia.inicializar();
            vecAsiDia = new Vector();

            if (vecDetDiario == null)
                vecDetDiario = new java.util.Vector();
            else
                vecDetDiario.removeAllElements();

            java.util.Vector vecReg = new Vector();

            String strMes = cboPerMes.getSelectedItem().toString().toUpperCase();
            String strAño = cboPerAAAA.getSelectedItem().toString().toUpperCase();

            if (txtCodTipDoc.getText().compareTo("192") == 0)        strTipDocDes = "PAGOS";
            else if (txtCodTipDoc.getText().compareTo("199") == 0)   strTipDocDes = "PRESTAMOS FUNCIONARIOS";
            else if (txtCodTipDoc.getText().compareTo("202") == 0)   strTipDocDes = "REEMBOLSO POR GASTOS";

            if (cboPer.getSelectedItem().toString().equals("Primera quincena")) 
            {
                if (txtCodTipDoc.getText().compareTo("192") == 0) 
                    objAsiDia.setGlosa("ROL DE " + strTipDocDes + " - 1ERA QUINCENA " + strMes + "/" + strAño + "");
                else 
                    objAsiDia.setGlosa(strTipDocDes + " - 1ERA QUINCENA " + strMes + "/" + strAño + "");
                mostrarCtaPre(1);
            }
            else if (cboPer.getSelectedItem().toString().equals("Fin de mes"))
            {
                if (txtCodTipDoc.getText().compareTo("192") == 0) 
                    objAsiDia.setGlosa("ROL DE " + strTipDocDes + " - 2DA QUINCENA " + strMes + "/" + strAño + "");
                else 
                    objAsiDia.setGlosa(strTipDocDes + " - 2DA QUINCENA " + strMes + "/" + strAño + "");
                mostrarCtaPre(2);
            }

            con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                stmLoc = con.createStatement();

                if (cboPer.getSelectedItem().toString().equals("Primera quincena")) // QUINCENA
                {
                    /*Segmentar personas que se realizan anticipos con cheque..*/
                    String strCoCtaDeb = "";
                    String strNumCtaDeb = "";
                    String strDesLarCtaDeb = "";

                    strSQL ="";
                    strSQL+=" SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                    strSQL+=" FROM tbm_plaCta AS a1, tbm_cabTipDoc AS a2";
                    strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_ctadeb";
                    strSQL+=" AND a2.co_emp=" + objZafParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_loc=" + objZafParSis.getCodigoLocal();
                    strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                    strSQL+=" AND a2.st_reg='A'";
                    
                    //System.out.println("Query: Segmentar personas que se realizan anticipos con cheque:"+strSQL);
                    rst = stm.executeQuery(strSQL);

                    if (rst.next())
                    {
                        strCoCtaDeb = rst.getString("co_cta");
                        strNumCtaDeb = rst.getString("tx_codcta");
                        strDesLarCtaDeb = rst.getString("tx_deslar");
                    }

                    String strSql = "";
                    BigDecimal bgdTotNInc = BigDecimal.ZERO; // Controlar valores que no incluyen en bancos
                    BigDecimal bgddblTot  = BigDecimal.ZERO;

                    for (int intFil = 0; intFil < tblDat.getRowCount(); intFil++) 
                    {
                        bgdTotNInc = BigDecimal.ZERO;
                        strSql ="";
                        strSql+=" select tx_numctaban ";
                        strSql+=" from tbm_traemp";
                        strSql+=" where co_tra=" + tblDat.getValueAt(intFil, INT_TBL_CODTRA).toString();
                        strSql+=" and co_emp=" + objZafParSis.getCodigoEmpresa();
                        strSql+=" and tx_numctaban is null";
                        
                        //System.out.println("tx_numctaban: " + strSql);
                        rstLoc = stmLoc.executeQuery(strSql);

                        if (rstLoc.next()) 
                        {
                            if (objZafParSis.getCodigoMenu() == 3138) 
                            {
                                bgdTotNInc = bgdTotNInc.add(objUti.redondearBigDecimal(tblDat.getValueAt(intFil, objTblMod.getColumnCount() - 1).toString(), objZafParSis.getDecimalesMostrar()));
                            }
                            else 
                            {
                                bgdTotNInc = bgdTotNInc.add(objUti.redondearBigDecimal(tblDat.getValueAt(intFil, objTblMod.getColumnCount() - 1).toString(), objZafParSis.getDecimalesMostrar()));
                            }

                            bgddblTot = bgddblTot.add(bgdTotNInc);

                            //<editor-fold defaultstate="collapsed" desc="/* Comentado */">
//                          //  vecReg = new Vector();
//                          //  vecReg.add(INT_LINEA, null);
//                          //  vecReg.add(INT_VEC_CODCTA, strCoCtaDeb);
//                          //  vecReg.add(INT_VEC_NUMCTA, strNumCtaDeb);
//                          //  vecReg.add(INT_VEC_BOTON, null);
//                          //  vecReg.add(INT_VEC_NOMCTA,  strDesLarCtaDeb);
//                          //  vecReg.add(INT_VEC_DEBE, bgdTotNInc);
//                          //  vecReg.add(INT_VEC_HABER, new BigDecimal(0));
//                          //  System.err.println(tblDat.getValueAt(intFil, INT_TBL_NOMTRA).toString());
//                          //  vecReg.add(INT_VEC_REF, "Se realiza el pago a: " + tblDat.getValueAt(intFil, INT_TBL_NOMTRA).toString() + " con cheque porque no tiene cuenta bancaria asignada" );
//                          //  vecReg.add(INT_VEC_NUEVO, null);
//                          //  vecDetDiario.add(vecReg);
                            //</editor-fold>
                            
                        }
                    }

                    //Cuenta debe.. Anticipos.. Diferencia de las personas que si tienen cuenta bancaria.
                    BigDecimal bgdValDoc = new BigDecimal(txtValDoc.getText());
                    bgdValDoc = bgdValDoc.subtract(bgddblTot);

                    vecReg = new Vector();
                    vecReg.add(INT_LINEA, null);
                    vecReg.add(INT_VEC_CODCTA, strCoCtaDeb);
                    vecReg.add(INT_VEC_NUMCTA, strNumCtaDeb);
                    vecReg.add(INT_VEC_BOTON, null);
                    vecReg.add(INT_VEC_NOMCTA, strDesLarCtaDeb);

                    //  vecReg.add(INT_VEC_DEBE, bgdValDoc);
                    vecReg.add(INT_VEC_DEBE, txtValDoc.getText());
                    vecReg.add(INT_VEC_HABER, new BigDecimal(0));
                    vecReg.add(INT_VEC_REF, null);
                    vecReg.add(INT_VEC_NUEVO, null);
                    vecDetDiario.add(vecReg);

                    for (int intFil = 0; intFil < tblDat.getRowCount(); intFil++)
                    {
                        bgdTotNInc = BigDecimal.ZERO;
                        strSql ="";
                        strSql+=" select tx_numctaban ";
                        strSql+=" from tbm_traemp";
                        strSql+=" where co_tra=" + tblDat.getValueAt(intFil, INT_TBL_CODTRA).toString();
                        strSql+=" and co_emp=" + objZafParSis.getCodigoEmpresa();
                        strSql+=" and tx_numctaban is null";
                        //System.out.println("tx_numctaban: " + strSql);
                        rstLoc = stmLoc.executeQuery(strSql);

                        if (rstLoc.next()) 
                        {
                            if (objZafParSis.getCodigoMenu() == 3138)
                            {
                                bgdTotNInc = bgdTotNInc.add(objUti.redondearBigDecimal(tblDat.getValueAt(intFil, objTblMod.getColumnCount() - 1).toString(), objZafParSis.getDecimalesMostrar()));
                            } 
                            else 
                            {
                                bgdTotNInc = bgdTotNInc.add(objUti.redondearBigDecimal(tblDat.getValueAt(intFil, objTblMod.getColumnCount() - 1).toString(), objZafParSis.getDecimalesMostrar()));
                            }

                            bgddblTot = bgddblTot.add(bgdTotNInc);

                            vecReg = new Vector();
                            vecReg.add(INT_LINEA, null);
                            vecReg.add(INT_VEC_CODCTA, strCoCtaDeb);
                            vecReg.add(INT_VEC_NUMCTA, strNumCtaDeb);
                            vecReg.add(INT_VEC_BOTON, null);
                            vecReg.add(INT_VEC_NOMCTA, strDesLarCtaDeb);

                            vecReg.add(INT_VEC_DEBE, new BigDecimal(0));
                            vecReg.add(INT_VEC_HABER, bgdTotNInc);
                            System.err.println(tblDat.getValueAt(intFil, INT_TBL_NOMTRA).toString());
                            vecReg.add(INT_VEC_REF, "Se realiza el pago a: " + tblDat.getValueAt(intFil, INT_TBL_NOMTRA).toString() + " con cheque porque no tiene cuenta bancaria asignada");
                            vecReg.add(INT_VEC_NUEVO, null);
                            vecDetDiario.add(vecReg);

                        }
                    }

                    //Cuenta Bancos...haber
                    vecReg = new Vector();
                    vecReg.add(INT_LINEA, null);
                    vecReg.add(INT_VEC_CODCTA, txtCodCta.getText());
                    vecReg.add(INT_VEC_NUMCTA, txtDesCorCta.getText());
                    vecReg.add(INT_VEC_BOTON, null);
                    vecReg.add(INT_VEC_NOMCTA, txtDesLarCta.getText());

                    vecReg.add(INT_VEC_DEBE, new Double(0));
                    vecReg.add(INT_VEC_HABER, bgdValDoc);
                    vecReg.add(INT_VEC_REF, null);
                    vecReg.add(INT_VEC_NUEVO, null);
                    vecDetDiario.add(vecReg);

                    objAsiDia.setDetalleDiario(vecDetDiario);
                } 
                else if (cboPer.getSelectedItem().toString().equals("Fin de mes")) //FIN DE MES
                {
                    int intContAdm = 0, intContVen = 0;
                    for (Iterator<String> itLstRub = arrLst.iterator(); itLstRub.hasNext();) 
                    {
                        String strCodRub = itLstRub.next();
                        intColDin = intColDin + 1;

                        String strSql = "";
                        strSql+=" select distinct a.co_cta,b.tx_deslar,b.tx_codcta,b.co_emp,c.tx_tiprub ";
                        strSql+=" from tbm_suetra a ";
                        strSql+=" inner join tbm_placta b on (a.co_emp=b.co_emp and a.co_cta=b.co_cta)";
                        strSql+=" inner join tbm_rubrolpag c on (c.co_rub=" + strCodRub + ")";
                        strSql+=" where a.co_emp = " + objZafParSis.getCodigoEmpresa();
                        strSql+=" and a.co_rub = " + strCodRub;
                        strSql+=" and a.co_cta is not null";
                        strSql+=" order by tx_codcta asc";
                        
                        //System.out.println("Asiento diario ctas rub " + strCodRub + "   ---->  " + strSql);
                        rst = stm.executeQuery(strSql);

                        while (rst.next()) 
                        {
                            bgdDebe = BigDecimal.ZERO;
                            vecReg = new Vector();
                            vecReg.add(INT_LINEA, null);
                            vecReg.add(INT_VEC_CODCTA, rst.getString("co_cta"));
                            vecReg.add(INT_VEC_NUMCTA, rst.getString("tx_codcta"));
                            vecReg.add(INT_VEC_BOTON, null);
                            vecReg.add(INT_VEC_NOMCTA, rst.getString("tx_deslar"));

                            int intFil = 0;
                            for (intFil = 0; intFil < tblDat.getRowCount(); intFil++) 
                            {
                                strSql =" select * from tbm_suetra a ";
                                strSql+=" inner join tbm_traemp b on (b.co_emp = a.co_emp and b.co_tra = a.co_tra)";
                                strSql+=" where a.co_emp = " + rst.getString("co_emp");
                                strSql+=" and a.co_rub = " + strCodRub;
                                strSql+=" and a.co_cta = " + rst.getString("co_cta");
                                strSql+=" and a.co_tra = " + tblDat.getValueAt(intFil, INT_TBL_CODTRA).toString();
                                // System.out.println("comprob si cod_tra: " + tblDat.getValueAt(intFil, INT_TBL_CODTRA).toString() + " pertenece a la cta : " + rst.getString("co_cta") + "   --------->  "+strSql);
                                rstLoc = stmLoc.executeQuery(strSql);

                                if (rstLoc.next()) 
                                {
                                    bgdDebe = bgdDebe.add(objUti.redondearBigDecimal(tblDat.getValueAt(intFil, intColDin).toString(), objZafParSis.getDecimalesMostrar()));
                                    
                                    if (rstLoc.getInt("co_rub") == 5) 
                                    {
                                        int intDiasLabTra = tblDat.getValueAt(intFil, INT_TBL_DIAS_LAB) == null ? 0 : Integer.parseInt(tblDat.getValueAt(intFil, INT_TBL_DIAS_LAB).toString());
                                        if (rstLoc.getInt("co_pla") == 1)
                                        {
                                            intContAdm++;
                                 
                                            if (rstLoc.getString("st_recfonres") != null) 
                                            {
                                                if (rstLoc.getString("st_recfonres").compareTo("S") == 0) 
                                                {
                                                    if (rstLoc.getString("tx_forrecfonres").compareTo("I") == 0 || rstLoc.getString("tx_forrecfonres").compareTo("R") == 0) 
                                                    {
                                                        //David Error al coger las columnas
                                                        double dblSue = objUti.redondear(tblDat.getValueAt(intFil, INT_TBL_TOTREC + 1 ).toString(), objZafParSis.getDecimalesMostrar());
                                                        double dblHE1 = objUti.redondear(tblDat.getValueAt(intFil, INT_TBL_TOTREC + 2).toString(), objZafParSis.getDecimalesMostrar());
                                                        double dblHE2 = objUti.redondear(tblDat.getValueAt(intFil, INT_TBL_TOTREC + 3).toString(), objZafParSis.getDecimalesMostrar());
                                                        double dblCom = objUti.redondear(tblDat.getValueAt(intFil, INT_TBL_TOTREC + 9).toString(), objZafParSis.getDecimalesMostrar()); 
                                                        
                                                        dblProvFonResAdm += (dblSue + dblHE1 + dblHE2 + dblCom) / 12; 
                                                    }
                                                }
                                            }
                                            dblProvDCSAdm += (((dblSBU / 30) * intDiasLabTra) / 12);
                                        }
                                        else if (rstLoc.getInt("co_pla") == 2)
                                        {
                                            intContVen++;
                                            
                                            if (rstLoc.getString("st_recfonres") != null)
                                            {
                                                if (rstLoc.getString("st_recfonres").compareTo("S") == 0) 
                                                {
                                                    if (rstLoc.getString("tx_forrecfonres").compareTo("I") == 0 || rstLoc.getString("tx_forrecfonres").compareTo("R") == 0) 
                                                    {
                                                        //David Error al coger las columnas
                                                        double dblSue = objUti.redondear(tblDat.getValueAt(intFil, INT_TBL_TOTREC+1).toString(), objZafParSis.getDecimalesMostrar());
                                                        double dblHE1 = objUti.redondear(tblDat.getValueAt(intFil, INT_TBL_TOTREC+2).toString(), objZafParSis.getDecimalesMostrar());
                                                        double dblHE2 = objUti.redondear(tblDat.getValueAt(intFil, INT_TBL_TOTREC+3).toString(), objZafParSis.getDecimalesMostrar());
                                                        double dblCom = objUti.redondear(tblDat.getValueAt(intFil, INT_TBL_TOTREC+9).toString(), objZafParSis.getDecimalesMostrar()); 
                                                        dblProvFonResVen += (dblSue + dblHE1 + dblHE2 + dblCom) / 12; 
                                                    }
                                                }
                                            }
                                            dblProvDCSVen += (((dblSBU / 30) * intDiasLabTra) / 12);
                                        }
                                    }
                                }
                            }

                            if (bgdDebe.signum() == 1) 
                            {
                                vecReg.add(INT_VEC_DEBE, bgdDebe);
                                vecReg.add(INT_VEC_HABER, new Double(0));
                                vecReg.add(INT_VEC_REF, null);
                                vecReg.add(INT_VEC_NUEVO, null);
                                vecDetDiario.add(vecReg);
                            }
                            else if (bgdDebe.signum() == -1) 
                            {
                                vecReg.add(INT_VEC_DEBE, new Double(0));
                                vecReg.add(INT_VEC_HABER, bgdDebe.multiply(BigDecimal.valueOf(-1)));
                                vecReg.add(INT_VEC_REF, null);
                                vecReg.add(INT_VEC_NUEVO, null);
                                vecDetDiario.add(vecReg);
                            }
                        }
                    }

                    String strSql = "";
                    BigDecimal bgdTotNInc = BigDecimal.ZERO;//controlar valores que no incluyen en bancos
                    BigDecimal bgddblTot = BigDecimal.ZERO;

                    for (int intFil = 0; intFil < tblDat.getRowCount(); intFil++) 
                    {
                        bgdTotNInc = BigDecimal.ZERO;
                        strSql = "";
                        strSql += " select tx_numctaban from tbm_traemp";
                        strSql += " where co_tra=" + tblDat.getValueAt(intFil, INT_TBL_CODTRA).toString();
                        strSql += " and co_emp=" + objZafParSis.getCodigoEmpresa();
                        strSql += " and tx_numctaban is null";
                        //System.out.println("comp: " + strSql);
                        rstLoc = stmLoc.executeQuery(strSql);

                        if (rstLoc.next()) 
                        {
                            bgdTotNInc = bgdTotNInc.add(objUti.redondearBigDecimal(tblDat.getValueAt(intFil, tblDat.getColumnCount() - 1).toString(), objZafParSis.getDecimalesMostrar()));
                            bgddblTot = bgddblTot.add(bgdTotNInc);

                            vecReg = new Vector();
                            vecReg.add(INT_LINEA, null);
                            vecReg.add(INT_VEC_CODCTA, txtCodCta.getText());
                            vecReg.add(INT_VEC_NUMCTA, txtDesCorCta.getText());
                            vecReg.add(INT_VEC_BOTON, null);
                            vecReg.add(INT_VEC_NOMCTA, txtDesLarCta.getText());

                            vecReg.add(INT_VEC_DEBE, new Double(0));
                            vecReg.add(INT_VEC_HABER, bgdTotNInc);
                            vecReg.add(INT_VEC_REF, "Se realiza el pago a: " + tblDat.getValueAt(intFil, INT_TBL_NOMTRA).toString() + " con cheque porque no tiene cuenta bancaria asignada");
                            vecReg.add(INT_VEC_NUEVO, null);
                            vecDetDiario.add(vecReg);
                        }
                    }

                    //BigDecimal bgdValDoc= new  BigDecimal(txtValDoc.getText());
                    //bgdValDoc=bgdValDoc.subtract(bgddblTot);
                    vecReg = new Vector();
                    vecReg.add(INT_LINEA, null);
                    vecReg.add(INT_VEC_CODCTA, txtCodCta.getText());
                    vecReg.add(INT_VEC_NUMCTA, txtDesCorCta.getText());
                    vecReg.add(INT_VEC_BOTON, null);
                    vecReg.add(INT_VEC_NOMCTA, txtDesLarCta.getText());

                    vecReg.add(INT_VEC_DEBE, new Double(0));
                    // vecReg.add(INT_VEC_HABER, objUti.redondear(objUti.parseDouble(objTblTot.getValueAt(0, objTblMod.getColumnCount())), objZafParSis.getDecimalesMostrar()));
                    BigDecimal bgdValDoc = new BigDecimal(txtValDoc.getText());
                    bgdValDoc = bgdValDoc.subtract(bgddblTot);
                    vecReg.add(INT_VEC_HABER, bgdValDoc);
                    vecReg.add(INT_VEC_REF, null);
                    vecReg.add(INT_VEC_NUEVO, null);
                    vecDetDiario.add(vecReg);
                    // objTblMod=new ZafTblMod();
                    // objTblMod.setHeader(vecCab);
                    objAsiDia.setDetalleDiario(vecDetDiario);
                    vecAsiDia = (Vector) vecDetDiario.clone();
                    zafTblModAsiDia = new ZafTblMod();
                    Vector vecCab = new Vector(9);
                    vecCab.add(0, "");
                    vecCab.add(1, "Cód.Cta.");
                    vecCab.add(2, "Núm.Cta.");
                    vecCab.add(3, "");
                    vecCab.add(4, "Nombre de la cuenta");
                    vecCab.add(5, "Debe");
                    vecCab.add(6, "Haber");
                    vecCab.add(7, "Referencia");
                    vecCab.add(8, "Estado de Conciliación");
                    zafTblModAsiDia.setHeader(vecCab);
                    jTblAsiDia = new JTable();
                    jTblAsiDia.setModel(objTblMod);
                    zafTblModAsiDia.setData(vecAsiDia);
                    jTblAsiDia.setModel(zafTblModAsiDia);
                    //System.out.println("Fondos admin : " + intContAdm);
                    //System.out.println("Fondos ventas : " + intContVen);
                    //  vecCab.add(INT_TBL_DAT_LIN,"");
                    //  vecCab.add(INT_TBL_DAT_COD_CTA,"Cód.Cta.");
                    //  vecCab.add(INT_TBL_DAT_NUM_CTA,"Núm.Cta.");
                    //  vecCab.add(INT_TBL_DAT_BUT_CTA,"");
                    //  vecCab.add(INT_TBL_DAT_NOM_CTA,"Nombre de la cuenta");
                    //  vecCab.add(INT_TBL_DAT_DEB,"Debe");
                    //  vecCab.add(INT_TBL_DAT_HAB,"Haber");
                    //  vecCab.add(INT_TBL_DAT_REF,"Referencia");
                    //  vecCab.add(INT_TBL_DAT_EST_CON,"Estado de Conciliación");

                }
            }
        }
        catch (java.lang.ArrayIndexOutOfBoundsException e) {       objUti.mostrarMsgErr_F1(this, e);   blnRes = false;   } 
        catch (Exception e) {      objUti.mostrarMsgErr_F1(this, e);          blnRes = false;    }
        finally 
        {
            try {  rst.close();    rst = null;      } catch (Throwable ignore) {    }
            try {  stm.close();    stm = null;      } catch (Throwable ignore) {    }
            try {  rstLoc.close(); rstLoc = null;   } catch (Throwable ignore) {    }
            try {  stmLoc.close(); stmLoc = null;   } catch (Throwable ignore) {    }
            try {  con.close();    con = null;      } catch (Throwable ignore) {    }
        }
        return blnRes;
    }

    private class Plantilla 
    {
        int intCoPla;
        int intCantEmp;

        private Plantilla() 
        {
            intCoPla = 0;
            intCantEmp = 0;
        }

        public void setCoPla(int intCoPla)
        {
            this.intCoPla = intCoPla;
        }

        public int getCoPla() 
        {
            return this.intCoPla;
        }

        public void setCantEmp(int intCantEmp) 
        {
            this.intCantEmp = intCantEmp;
        }

        public int getCantEmp() 
        {
            return this.intCantEmp;
        }
    }

    /**
     * Función que carga el detalle de Provisiones al momento de modificar el Rol de Pagos.
     * Pestaña (Provisiones)
     * @return 
     */
    private boolean generaDetalleProvisiones() 
    {
        boolean blnRes = true;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSQL = "";
        int intCodGrp = 0;
        try 
        {
            if (txtCodTipDoc.getText().compareTo("192") == 0)       intCodGrp = 1;
            else if (txtCodTipDoc.getText().compareTo("199") == 0)  intCodGrp = 2;
            else  intCodGrp = 3;

            con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null)
            {
                stmLoc = con.createStatement();
                vecDatProv = new Vector();

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, Integer.parseInt(cboPerAAAA.getSelectedItem().toString()));
                cal.set(Calendar.MONTH, (cboPerMes.getSelectedIndex() - 1));
                // cal.set(Calendar.DATE, cal.getMaximum(java.util.Calendar.DAY_OF_MONTH));
                String strFecFonRes = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.getActualMaximum(Calendar.DAY_OF_MONTH);

                //<editor-fold defaultstate="collapsed" desc="/* PROVISIONES: Beneficios Sociales. */">
                for (int i = 0; i < tblDat.getRowCount(); i++) 
                {
                    double dblDecTerSueTra = 0;
                    double dblDecCuaSueTra = 0;
                    double dblFonResTra = 0;
                    double dblVacTra = 0;
                    double dblApoPatTra = 0;

                    double dblSueldo = 0;
                    double dblHE1 = 0;
                    double dblHE2 = 0;
                    double dblCom = 0;
                    double dblBaseSueldo =0; //Sueldo Base para calculos provisiones (Sumatoria de Sueldo+HE1+HE2+Comisiones).

                    int intPosRef = INT_TBL_DIAS_LAB + 1;
                    Vector vecReg = new Vector();
                    vecReg.add(INT_TBL_PROV_LINEA, "");
                    vecReg.add(INT_TBL_PROV_CODTRA, tblDat.getValueAt(i, INT_TBL_CODTRA));
                    vecReg.add(INT_TBL_PROV_NOMTRA, tblDat.getValueAt(i, INT_TBL_NOMTRA));

                    if (intCodGrp == 1) 
                    {
                        dblSueldo = Double.parseDouble(tblDat.getValueAt(i, intPosRef).toString());
                        dblHE1 = Double.parseDouble(tblDat.getValueAt(i, intPosRef + 1).toString());
                        dblHE2 = Double.parseDouble(tblDat.getValueAt(i, intPosRef + 2).toString());
                        dblCom = Double.parseDouble(tblDat.getValueAt(i, intPosRef + 8).toString()); 
                        dblBaseSueldo = dblSueldo + dblHE1 + dblHE2 + dblCom; 
                    }
                    else 
                    {
                        dblSueldo = Double.parseDouble(tblDat.getValueAt(i, intPosRef).toString());
                    }
                    //<editor-fold defaultstate="collapsed" desc="/* Fondos Reserva - DTS - DCS  */">
                    if (intCodGrp == 1) 
                    {
                        strSQL = "";
                        strSQL += "SELECT * , \n"
                                + "case ((((" + objUti.codificar(strFecFonRes) + "-fe_inicon)/12))>=13) when true then 'S' else null end as st_recfonres \n"
                                + "FROM tbm_traemp \n"
                                + "WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + "\n"
                                + "AND co_tra = " + tblDat.getValueAt(i, INT_TBL_CODTRA).toString();
                        
                        System.out.println("generaDetalleProvisiones.ConsultaEstado-FonRes-DTS-DCS: "+strSQL);
                        rstLoc = stmLoc.executeQuery(strSQL);

                        if (rstLoc.next()) 
                        {
                            //<editor-fold defaultstate="collapsed" desc="/* Fondos Reserva. */">
                            if (rstLoc.getString("st_recfonres") != null) 
                            {
                                //Se provisionará para todos los empleados que reciban fondos de reserva, ya sea por rol o por iess.
                                if (rstLoc.getString("st_recfonres").compareTo("S") == 0) 
                                {
                                    dblFonResTra = ((dblBaseSueldo) / 12);
                  /*AQUI Cuendo es click modificar*/             vecReg.add(INT_TBL_PROV_FONDO_RESERVA, objUti.redondear(dblFonResTra, objZafParSis.getDecimalesMostrar()));
                                }
                            }
                            else 
                            {
                                vecReg.add(INT_TBL_PROV_FONDO_RESERVA, null);
                            }
                            //</editor-fold>
                            
                            //<editor-fold defaultstate="collapsed" desc="/* Decimo Tercer Sueldo. */">
                            dblDecTerSueTra = ((dblBaseSueldo) / 12);
                            vecReg.add(INT_TBL_PROV_DECIMO_TERCER_SUELDO, objUti.redondear(dblDecTerSueTra, objZafParSis.getDecimalesMostrar()));
                            //</editor-fold>
                            
                            //<editor-fold defaultstate="collapsed" desc="/* Decimo Cuarto Sueldo. */">
                            int intDiasLabTra = tblDat.getValueAt(i, INT_TBL_DIAS_LAB) == null ? 0 : Integer.parseInt(tblDat.getValueAt(i, INT_TBL_DIAS_LAB).toString());
                            dblDecCuaSueTra = (((dblSBU / 30) * intDiasLabTra) / 12);
                            vecReg.add(INT_TBL_PROV_DECIMO_CUARTO_SUELDO, objUti.redondear(dblDecCuaSueTra, objZafParSis.getDecimalesMostrar()));
                            //</editor-fold>
                        } 
                        else
                        {
                            vecReg.add(INT_TBL_PROV_DECIMO_TERCER_SUELDO, null); 
                            vecReg.add(INT_TBL_PROV_DECIMO_CUARTO_SUELDO, null); 
                            vecReg.add(INT_TBL_PROV_FONDO_RESERVA, null);
                        }
                    }
                    else 
                    {
                        vecReg.add(INT_TBL_PROV_DECIMO_TERCER_SUELDO, null); 
                        vecReg.add(INT_TBL_PROV_DECIMO_CUARTO_SUELDO, null); 
                        vecReg.add(INT_TBL_PROV_FONDO_RESERVA, null);
                    }
                    //</editor-fold>
                    
                   
                    if (intCodGrp == 1) 
                    {
                        //<editor-fold defaultstate="collapsed" desc="/* Aporte Patronal. */">
                        dblApoPatTra = ((dblBaseSueldo) * (dblPorApoPatIes / 100)); 
                        vecReg.add(INT_TBL_PROV_APORTE_PATRONAL, objUti.redondear(dblApoPatTra, objZafParSis.getDecimalesMostrar()));
                        //</editor-fold>
                        
                        //<editor-fold defaultstate="collapsed" desc="/* Vacaciones. */">
                        dblVacTra = ((dblBaseSueldo) / 24); 
                        vecReg.add(INT_TBL_PROV_VACACIONES, objUti.redondear(dblVacTra, objZafParSis.getDecimalesMostrar()) );
                        //</editor-fold>
                    }
                    else 
                    {
                        vecReg.add(INT_TBL_PROV_VACACIONES, null);
                        vecReg.add(INT_TBL_PROV_APORTE_PATRONAL, null);
                        
                    }
                    
                    
                    vecReg.add(INT_TBL_PROV_TOTAL_TRA, objUti.redondear((dblDecTerSueTra + dblDecCuaSueTra + dblFonResTra + dblVacTra + dblApoPatTra), objZafParSis.getDecimalesMostrar()));
                    vecDatProv.add(vecReg);
                }
                //</editor-fold>

                //Asignar vectores al modelo de provisiones.
                objTblModProv.setData(vecDatProv);
                tblDatProv.setModel(objTblModProv);
                objTblTotProv.calcularTotales();
                //calcularValorDocumento();
                vecDatProv.clear();

            }
        } 
        catch (java.lang.ArrayIndexOutOfBoundsException Evt) 
        {
            objUti.mostrarMsgErr_F1(this, Evt);
            Evt.printStackTrace();
            blnRes = false;
        }
        catch (java.sql.SQLException Evt)
        {
            objUti.mostrarMsgErr_F1(this, Evt);
            Evt.printStackTrace();
            blnRes = false;
        }
        catch (Exception Evt)
        {
            objUti.mostrarMsgErr_F1(this, Evt);
            Evt.printStackTrace();
            blnRes = false;
        } 
        finally 
        {
            try {  rst.close();     } catch (Throwable ignore) {     }
            try {  rstLoc.close();  } catch (Throwable ignore) {     }
            try {  stm.close();     } catch (Throwable ignore) {     }
            try {  stmLoc.close();  } catch (Throwable ignore) {     }
            try {  con.close();     } catch (Throwable ignore) {     }
        }
        return blnRes;
    }

    /**
     * Función que genera Asiento de Diario de Provisiones.
     * Pestaña (Asiento de Provisiones)
     * @return 
     */
    private boolean generaAsientoProvisiones() 
    {
        boolean blnRes = true;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        java.sql.Connection con = null;
        String strSql = "";
        String strTipDocDes = "";
        double dblTot = 0, dblRec = 0, dblRecHab = 0;
        double dblVacAdm = 0, dblVacVen = 0;
        ArrayList<Plantilla> arrPla = null;
        Plantilla tmpPla = null;
        BigDecimal bgdDebe;
        int INT_LINEA = 0;           //0) Línea: Se debe asignar una cadena vacía o null. 
        int INT_VEC_CODCTA = 1;      //1) Código de la cuenta (Sistema). 
        int INT_VEC_NUMCTA = 2;      //2) Número de la cuenta (Preimpreso). 
        int INT_VEC_BOTON = 3;       //3) Botón de consulta: Se debe asignar una cadena vacía o null. 
        int INT_VEC_NOMCTA = 4;      //4) Nombre de la cuenta. 
        int INT_VEC_DEBE = 5;        //5) Debe. 
        int INT_VEC_HABER = 6;       //6) Haber. 
        int INT_VEC_REF = 7;         //7) Referencia: Se debe asignar una cadena vacía o null
        int INT_VEC_NUEVO = 8;
        int intCoGrp = 0;
        boolean blnIsCosenco=false;   
        
        //Saber si la empresa que ingreso es COSENCO
        blnIsCosenco = (objZafParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO") > -1)?true:false;
        
        try
        {
            objAsiDiaProv.inicializar();

            if (vecDetDiario == null) 
            {
                vecDetDiario = new java.util.Vector();
            }
            else
            {
                vecDetDiario.removeAllElements();
            }

            java.util.Vector vecReg = new Vector();

            String strMes = cboPerMes.getSelectedItem().toString().toUpperCase();
            String strAño = cboPerAAAA.getSelectedItem().toString().toUpperCase();

            if (txtCodTipDoc.getText().compareTo("192") == 0) 
            {
                strTipDocDes = "PAGOS";
                intCoGrp = 1;
            }
            else if (txtCodTipDoc.getText().compareTo("199") == 0) 
            {
                strTipDocDes = "PRESTAMOS FUNCIONARIOS";
                intCoGrp = 2;
            } 
            else if (txtCodTipDoc.getText().compareTo("202") == 0) 
            {
                strTipDocDes = "REEMBOLSO POR GASTOS";
                intCoGrp = 2;
            }

            if (cboPer.getSelectedItem().toString().equals("Primera quincena")) 
            {
                if (txtCodTipDoc.getText().compareTo("192") == 0)
                {
                    objAsiDiaProv.setGlosa("PROVISION ROL DE " + strTipDocDes + " - 1ERA QUINCENA " + strMes + "/" + strAño + "");
                } 
                else
                {
                    objAsiDiaProv.setGlosa("PROVISION " + strTipDocDes + " - 1ERA QUINCENA " + strMes + "/" + strAño + "");
                }
                //mostrarCtaPre(1);
            }
            else if (cboPer.getSelectedItem().toString().equals("Fin de mes")) 
            {
                if (txtCodTipDoc.getText().compareTo("192") == 0)
                {
                    objAsiDiaProv.setGlosa("PROVISION ROL DE " + strTipDocDes + " - " + strMes + "/" + strAño + "");
                }
                else 
                {
                    objAsiDiaProv.setGlosa("PROVISION " + strTipDocDes + " - " + strMes + "/" + strAño + "");
                }
                //mostrarCtaPre(2);
            }

            Librerias.ZafRecHum.ZafVenFun.ZafVenFun zafVenFun = new Librerias.ZafRecHum.ZafVenFun.ZafVenFun(objZafParSis, objUti);
            ArrayList<Provisiones> arrProv = zafVenFun.cuentasProvisiones(objZafParSis.getCodigoEmpresa(), intCoGrp);
            
            //System.out.println("arrProv: "+arrProv.size());
            //System.out.println("arrProv2: "+arrProv);
            
            if (arrProv.size() > 0) 
            {
                //<editor-fold defaultstate="collapsed" desc="/* ROL PAG */">
                if (intCoGrp == 1) 
                {
                    con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                    if (con != null) 
                    {
                        stmLoc = con.createStatement();

                        strSql = "";
                        strSql = "SELECT co_pla,count(co_pla) as cant FROM tbm_traemp \n"
                                + "WHERE co_emp =" + objZafParSis.getCodigoEmpresa() + "\n"
                                + "AND st_reg = 'A'\n"
                                + "AND co_pla IS NOT NULL\n"
                                + "GROUP BY co_pla\n"
                                + "ORDER BY co_pla";
                        rstLoc = stmLoc.executeQuery(strSql);
                        arrPla = new ArrayList<Plantilla>();

                        while (rstLoc.next()) 
                        {
                            tmpPla = new Plantilla();
                            tmpPla.setCoPla(rstLoc.getInt("co_pla"));
                            tmpPla.setCantEmp(rstLoc.getInt("cant"));
                            arrPla.add(tmpPla);
                        }
                    }

                    for (int intCont = 0; intCont < arrProv.size(); intCont++) 
                    {
                        Provisiones tmpProv = arrProv.get(intCont);
                        vecReg = new Vector();
                        vecReg.add(INT_LINEA, null);
                        vecReg.add(INT_VEC_CODCTA, tmpProv.getIntCoCta());
                        vecReg.add(INT_VEC_NUMCTA, tmpProv.getStrNumCta());
                        vecReg.add(INT_VEC_BOTON, null);
                        vecReg.add(INT_VEC_NOMCTA, tmpProv.getNombre());
                        dblRec = 0;

                        for (int intFil = 0; intFil < (jTblAsiDia.getColumnCount() - 1); intFil++) 
                        {
                            if(blnIsCosenco) //Cosenco
                            {
                                //<editor-fold defaultstate="collapsed" desc="/*DECIMO TERCERA REMUNERACION*/">
                                if (tmpProv.getIntCoCta() == 1455) 
                                {
                                    if ((Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 1453)
                                            || (Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 2001)) 
                                    {
                                        //Rose
                                        dblRec += objUti.redondear(jTblAsiDia.getValueAt(intFil, INT_VEC_DEBE).toString(), objZafParSis.getDecimalesMostrar());
                                        dblRec = objUti.redondear((dblRec / 12), objZafParSis.getDecimalesMostrar());
                                        dblTot += dblRec;
                                        dblVacAdm = dblTot;   
                                        dblRecHab += dblRec;  
                                        dblRec = 0;
                                    }
                                }
                                else if (tmpProv.getIntCoCta() == 1577)  //aplicado solo a los no funcionarios
                                {
                                    if ((Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 1574)
                                            || (Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 2002))
                                    {
                                        dblRec += objUti.redondear(jTblAsiDia.getValueAt(intFil, INT_VEC_DEBE).toString(), objZafParSis.getDecimalesMostrar());
                                        dblRec = objUti.redondear((dblRec / 12), objZafParSis.getDecimalesMostrar());
                                        dblTot += dblRec;
                                        dblVacVen = dblTot;   
                                        dblRecHab += dblRec;  
                                        dblRec = 0;
                                    }
                                }
                                else if (tmpProv.getIntCoCta() == 1152 ) 
                                {
                                    dblTot = dblRecHab;
                                    //dblTot = objUti.redondear(dblTot, objZafParSis.getDecimalesMostrar());
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="/*DECIMO CUARTA REMUNERACION*/">
                                if (tmpProv.getIntCoCta() == 1456)
                                {
                                    dblRec = objUti.redondear(dblProvDCSAdm, objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    dblProvDCSAdm = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                } 
                                else if (tmpProv.getIntCoCta() == 1578) 
                                {
                                    dblRec = objUti.redondear(dblProvDCSVen, objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    dblProvDCSVen = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                else if (tmpProv.getIntCoCta() == 1228) 
                                {
                                    dblTot = dblRecHab;
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="/*FONDOS DE RESERVA*/">
                                if (tmpProv.getIntCoCta() == 1458) 
                                {
                                    dblRec = objUti.redondear(dblProvFonResAdm, objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    dblProvFonResAdm = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                } 
                                else if (tmpProv.getIntCoCta() == 1580) 
                                {
                                    dblRec = objUti.redondear(dblProvFonResVen, objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    dblProvFonResVen = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                else if (tmpProv.getIntCoCta() == 1337)
                                {
                                    dblTot = dblRecHab;
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="/*VACACIONES*/">
                                if (tmpProv.getIntCoCta() == 1459)
                                {
                                    dblRec += dblVacAdm; 
                                    dblRec = objUti.redondear((dblRec / 2), objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                } 
                                else if (tmpProv.getIntCoCta() == 1581) 
                                {
                                    dblRec += dblVacVen;
                                    dblRec = objUti.redondear((dblRec / 2), objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                else if (tmpProv.getIntCoCta() == 1304) 
                                {
                                    dblTot = dblRecHab;
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="/*APORTE PATRONAL*/">
                                if (tmpProv.getIntCoCta() == 1460)
                                {
                                    if ((Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 1453)
                                            || (Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 2001)) 
                                    {
                                        dblRec += objUti.redondear(jTblAsiDia.getValueAt(intFil, INT_VEC_DEBE).toString(), objZafParSis.getDecimalesMostrar());
                                        dblRec = objUti.redondear((dblRec * (dblPorApoPatIes/100)), objZafParSis.getDecimalesMostrar());
                                        dblTot += dblRec;
                                        dblRecHab += dblRec;
                                        dblRec = 0;
                                    }
                                }
                                else if (tmpProv.getIntCoCta() == 1582) 
                                {
                                    if ((Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 1574)
                                            || (Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 2002))
                                    {
                                        dblRec += objUti.redondear(jTblAsiDia.getValueAt(intFil, INT_VEC_DEBE).toString(), objZafParSis.getDecimalesMostrar());
                                        dblRec = objUti.redondear((dblRec * (dblPorApoPatIes/100)), objZafParSis.getDecimalesMostrar());
                                        dblTot += dblRec;
                                        dblRecHab += dblRec;
                                        dblRec = 0;
                                    }
                                } 
                                else if (tmpProv.getIntCoCta() == 1068) 
                                {
                                    dblTot = dblRecHab;
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>
                            }                            
                            else if (objZafParSis.getCodigoEmpresa() == 1 ) //Tuval
                            {
                                //<editor-fold defaultstate="collapsed" desc="/*DECIMO TERCERA REMUNERACION*/">
                                if (tmpProv.getIntCoCta() == 1455) 
                                {
                                    if ((Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 1453)
                                            || (Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 2615)) 
                                    {
                                        dblRec += objUti.redondear(jTblAsiDia.getValueAt(intFil, INT_VEC_DEBE).toString(), objZafParSis.getDecimalesMostrar());
                                        dblRec = objUti.redondear((dblRec / 12), objZafParSis.getDecimalesMostrar());
                                        dblTot += dblRec;  
                                        dblVacAdm = dblTot;   
                                        dblRecHab += dblRec;     
                                        dblRec = 0;   
                                    }
                                }
                                else if (tmpProv.getIntCoCta() == 1577)  //aplicado solo a los no funcionarios
                                {
                                    if ((Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 1574)
                                            || (Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 2613))
                                    {
                                        dblRec += objUti.redondear(jTblAsiDia.getValueAt(intFil, INT_VEC_DEBE).toString(), objZafParSis.getDecimalesMostrar());
                                        dblRec = objUti.redondear((dblRec / 12), objZafParSis.getDecimalesMostrar());
                                        dblTot += dblRec;
                                        dblVacVen = dblTot;
                                        dblRecHab += dblRec;
                                        dblRec = 0;
                                    }
                                }
                                else if (tmpProv.getIntCoCta() == 1152 || tmpProv.getIntCoCta() == 277) 
                                {
                                    dblTot = dblRecHab;
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="/*DECIMO CUARTA REMUNERACION*/">
                                if (tmpProv.getIntCoCta() == 1456)
                                {
                                    dblRec = objUti.redondear(dblProvDCSAdm, objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    dblProvDCSAdm = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                } 
                                else if (tmpProv.getIntCoCta() == 1578) 
                                {
                                    dblRec = objUti.redondear(dblProvDCSVen, objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    dblProvDCSVen = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                else if (tmpProv.getIntCoCta() == 1228) 
                                {
                                    dblTot = dblRecHab;
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="/*FONDOS DE RESERVA*/">
                                if (tmpProv.getIntCoCta() == 1458) 
                                {
                                    dblRec = objUti.redondear(dblProvFonResAdm, objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    dblProvFonResAdm = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                } 
                                else if (tmpProv.getIntCoCta() == 1580) 
                                {
                                    dblRec = objUti.redondear(dblProvFonResVen, objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    dblProvFonResVen = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                else if (tmpProv.getIntCoCta() == 1337)
                                {
                                    dblTot = dblRecHab;
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="/*VACACIONES*/">
                                if (tmpProv.getIntCoCta() == 1459)
                                {
                                    dblRec += dblVacAdm;
                                    dblRec = objUti.redondear((dblRec / 2), objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                } 
                                else if (tmpProv.getIntCoCta() == 1581) 
                                {
                                    dblRec += dblVacVen;
                                    dblRec = objUti.redondear((dblRec / 2), objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                else if (tmpProv.getIntCoCta() == 1304) 
                                {
                                    dblTot = dblRecHab;
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="/*APORTE PATRONAL*/">
                                if (tmpProv.getIntCoCta() == 1460)
                                {
                                    if ((Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 1453)
                                            || (Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 2615)) 
                                    {
                                        dblRec += objUti.redondear(jTblAsiDia.getValueAt(intFil, INT_VEC_DEBE).toString(), objZafParSis.getDecimalesMostrar());
                                        dblRec = objUti.redondear((dblRec * (dblPorApoPatIes/100)), objZafParSis.getDecimalesMostrar());
                                        dblTot += dblRec;
                                        dblRecHab += dblRec;
                                        dblRec = 0;
                                    }
                                }
                                else if (tmpProv.getIntCoCta() == 1582) 
                                {
                                    if ((Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 1574)
                                            || (Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 2613))
                                    {
                                        dblRec += objUti.redondear(jTblAsiDia.getValueAt(intFil, INT_VEC_DEBE).toString(), objZafParSis.getDecimalesMostrar());
                                        dblRec = objUti.redondear((dblRec * (dblPorApoPatIes/100)), objZafParSis.getDecimalesMostrar());
                                        dblTot += dblRec;
                                        dblRecHab += dblRec;
                                        dblRec = 0;
                                    }
                                } 
                                else if (tmpProv.getIntCoCta() == 1068) 
                                {
                                    dblTot = dblRecHab;
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>
                            } 
                            else if (objZafParSis.getCodigoEmpresa() == 2) //Castek
                            {
                                //<editor-fold defaultstate="collapsed" desc="/*DECIMO TERCERA REMUNERACION*/">
                                if (tmpProv.getIntCoCta() == 566)
                                {
                                    if ((Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 564)
                                            || (Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 1149))
                                    {
                                        dblRec += objUti.redondear(jTblAsiDia.getValueAt(intFil, INT_VEC_DEBE).toString(), objZafParSis.getDecimalesMostrar());
                                        dblRec = objUti.redondear((dblRec / 12), objZafParSis.getDecimalesMostrar());
                                        dblTot += dblRec;
                                        dblVacAdm = dblTot;
                                        dblRecHab += dblRec;
                                        dblRec = 0;
                                    }
                                }
                                else if (tmpProv.getIntCoCta() == 678)  //aplicado solo a los no funcionarios
                                {
                                    if ((Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 675)
                                            || (Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 1150)) 
                                    {
                                        dblRec += objUti.redondear(jTblAsiDia.getValueAt(intFil, INT_VEC_DEBE).toString(), objZafParSis.getDecimalesMostrar());
                                        dblRec = objUti.redondear((dblRec / 12), objZafParSis.getDecimalesMostrar());
                                        dblTot += dblRec;
                                        dblVacVen = dblTot;
                                        dblRecHab += dblRec;
                                        dblRec = 0;
                                    }
                                } 
                                else if (tmpProv.getIntCoCta() == 277)
                                {
                                    dblTot = dblRecHab;
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="/*DECIMO CUARTA REMUNERACION*/">
                                if (tmpProv.getIntCoCta() == 567) 
                                {
                                    dblRec = objUti.redondear(dblProvDCSAdm, objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    dblProvDCSAdm = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                } 
                                else if (tmpProv.getIntCoCta() == 679) 
                                {
                                    dblRec = objUti.redondear(dblProvDCSVen, objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    dblProvDCSVen = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                } 
                                else if (tmpProv.getIntCoCta() == 352)
                                {
                                    dblTot = dblRecHab;
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="/*FONDOS DE RESERVA*/">
                                if (tmpProv.getIntCoCta() == 569) 
                                {
                                    dblRec = objUti.redondear(dblProvFonResAdm, objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    dblProvFonResAdm = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                } 
                                else if (tmpProv.getIntCoCta() == 681) 
                                {
                                    dblRec = objUti.redondear(dblProvFonResVen, objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    dblProvFonResVen = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                } 
                                else if (tmpProv.getIntCoCta() == 459) 
                                {
                                    dblTot = dblRecHab;
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="/*VACACIONES*/">
                                if (tmpProv.getIntCoCta() == 570) 
                                {
                                    dblRec += dblVacAdm;
                                    dblRec = objUti.redondear((dblRec / 2), objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                } 
                                else if (tmpProv.getIntCoCta() == 682) 
                                {
                                    dblRec += dblVacVen;
                                    dblRec = objUti.redondear((dblRec / 2), objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                else if (tmpProv.getIntCoCta() == 427) 
                                {
                                    dblTot = dblRecHab;
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="/*APORTE PATRONAL*/">
                                if (tmpProv.getIntCoCta() == 571) 
                                {
                                    if ((Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 564)
                                            || (Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 1149))
                                    {
                                        dblRec += objUti.redondear(jTblAsiDia.getValueAt(intFil, INT_VEC_DEBE).toString(), objZafParSis.getDecimalesMostrar());
                                        dblRec = objUti.redondear((dblRec * (dblPorApoPatIes/100)), objZafParSis.getDecimalesMostrar());
                                        dblTot += dblRec;
                                        dblRecHab += dblRec;
                                        dblRec = 0;
                                    }
                                } 
                                else if (tmpProv.getIntCoCta() == 683) 
                                {
                                    if ((Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 675)
                                            || (Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 1150)) 
                                    {
                                        dblRec += objUti.redondear(jTblAsiDia.getValueAt(intFil, INT_VEC_DEBE).toString(), objZafParSis.getDecimalesMostrar());
                                        dblRec = objUti.redondear((dblRec * (dblPorApoPatIes/100)), objZafParSis.getDecimalesMostrar());
                                        dblTot += dblRec;
                                        dblRecHab += dblRec;
                                        dblRec = 0;
                                    }
                                }
                                else if (tmpProv.getIntCoCta() == 251) 
                                {
                                    dblTot = dblRecHab;
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>
                            } 
                            else if (objZafParSis.getCodigoEmpresa() == 4) 
                            {
                                //<editor-fold defaultstate="collapsed" desc="/*DECIMO TERCERA REMUNERACION*/">
                                if (tmpProv.getIntCoCta() == 1394)
                                {
                                    if ((Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 1392)
                                            || (Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 2084)) 
                                    {
                                        dblRec += objUti.redondear(jTblAsiDia.getValueAt(intFil, INT_VEC_DEBE).toString(), objZafParSis.getDecimalesMostrar());
                                        dblRec = objUti.redondear((dblRec / 12), objZafParSis.getDecimalesMostrar());
                                        dblTot += dblRec;
                                        dblVacAdm = dblTot;
                                        dblRecHab += dblRec;
                                        dblRec = 0;
                                    }
                                }
                                else if (tmpProv.getIntCoCta() == 1511)  //aplicado solo a los no funcionarios
                                {
                                    if ((Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 1508)
                                            || (Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 2081)) 
                                    {
                                        dblRec += objUti.redondear(jTblAsiDia.getValueAt(intFil, INT_VEC_DEBE).toString(), objZafParSis.getDecimalesMostrar());
                                        dblRec = objUti.redondear((dblRec / 12), objZafParSis.getDecimalesMostrar());
                                        dblTot += dblRec;
                                        dblVacVen = dblTot;
                                        dblRecHab += dblRec;
                                        dblRec = 0;
                                    }
                                } 
                                else if (tmpProv.getIntCoCta() == 1098)
                                {
                                    dblTot = dblRecHab;
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="/*DECIMO CUARTO SUELDO*/">
                                if (tmpProv.getIntCoCta() == 1395) 
                                {
                                    dblRec = objUti.redondear(dblProvDCSAdm, objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    dblProvDCSAdm = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                } 
                                else if (tmpProv.getIntCoCta() == 1512)
                                {
                                    dblRec = objUti.redondear(dblProvDCSVen, objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    dblProvDCSVen = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                } 
                                else if (tmpProv.getIntCoCta() == 1174) 
                                {
                                    dblTot = dblRecHab;
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="/*FONDOS DE RESERVA*/">
                                if (tmpProv.getIntCoCta() == 1397) 
                                {
                                    dblRec = objUti.redondear(dblProvFonResAdm, objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    dblProvFonResAdm = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                } 
                                else if (tmpProv.getIntCoCta() == 1514) 
                                {
                                    dblRec = objUti.redondear(dblProvFonResVen, objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    dblProvFonResVen = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                else if (tmpProv.getIntCoCta() == 1283) 
                                {
                                    dblTot = dblRecHab;
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="/*VACACIONES*/">
                                if (tmpProv.getIntCoCta() == 1398) 
                                {
                                    dblRec += dblVacAdm;
                                    dblRec = objUti.redondear((dblRec / 2), objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                } 
                                else if (tmpProv.getIntCoCta() == 1515) 
                                {
                                    dblRec += dblVacVen;
                                    dblRec = objUti.redondear((dblRec / 2), objZafParSis.getDecimalesMostrar());
                                    dblTot += dblRec;
                                    dblRecHab += dblRec;
                                    dblRec = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                else if (tmpProv.getIntCoCta() == 1250)
                                {
                                    dblTot = dblRecHab;
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="/*APORTE PATRONAL*/">
                                if (tmpProv.getIntCoCta() == 1399)
                                {
                                    if ((Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 1392)
                                            || (Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 2084)) 
                                    {
                                        dblRec += objUti.redondear(jTblAsiDia.getValueAt(intFil, INT_VEC_DEBE).toString(), objZafParSis.getDecimalesMostrar());
                                        dblRec = objUti.redondear((dblRec * (dblPorApoPatIes/100)), objZafParSis.getDecimalesMostrar());
                                        dblTot += dblRec;
                                        dblRecHab += dblRec;
                                        dblRec = 0;
                                    }
                                }
                                else if (tmpProv.getIntCoCta() == 1516)
                                {
                                    if ((Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 1508)
                                            || (Integer.parseInt(jTblAsiDia.getValueAt(intFil, INT_VEC_CODCTA).toString()) == 2081)) 
                                    {
                                        dblRec += objUti.redondear(jTblAsiDia.getValueAt(intFil, INT_VEC_DEBE).toString(), objZafParSis.getDecimalesMostrar());
                                        dblRec = objUti.redondear((dblRec * (dblPorApoPatIes/100)), objZafParSis.getDecimalesMostrar());
                                        dblTot += dblRec;
                                        dblRecHab += dblRec;
                                        dblRec = 0;
                                    }
                                } 
                                else if (tmpProv.getIntCoCta() == 1014)
                                {
                                    dblTot = dblRecHab;
                                    dblRecHab = 0;
                                    intFil = jTblAsiDia.getColumnCount();
                                }
                                //</editor-fold>
                            }
                        }

                        if (tmpProv.getIntTipCta() == 1) //DEBE
                        {
                            vecReg.add(INT_VEC_DEBE, objUti.redondear(dblTot, objZafParSis.getDecimalesMostrar()));
                            vecReg.add(INT_VEC_HABER, new BigDecimal(0));
                            dblTot = 0;
                        }
                        else //HABER
                        {
                            vecReg.add(INT_VEC_DEBE, new BigDecimal(0));
                            vecReg.add(INT_VEC_HABER, objUti.redondear(dblTot, objZafParSis.getDecimalesMostrar()));
                            dblTot = 0;
                        }
                        // vecReg.add(INT_VEC_HABER, new BigDecimal(0));
                        vecReg.add(INT_VEC_REF, null);
                        vecReg.add(INT_VEC_NUEVO, null);

                        vecDetDiario.add(vecReg);
                    }
                    objAsiDiaProv.setDetalleDiario(vecDetDiario);
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="/* PREFUN Y REEMOV */">
                else 
                {
                    boolean blnComp = false;
                    vecDetDiario = new Vector();
                    for (int intCont = 0; intCont < arrProv.size(); intCont++) 
                    {
                        Provisiones tmpProv = arrProv.get(intCont);
                        vecReg = new Vector();
                        vecReg.add(INT_LINEA, null);
                        vecReg.add(INT_VEC_CODCTA, tmpProv.getIntCoCta());
                        vecReg.add(INT_VEC_NUMCTA, tmpProv.getStrNumCta());
                        vecReg.add(INT_VEC_BOTON, null);
                        vecReg.add(INT_VEC_NOMCTA, tmpProv.getNombre());

                        if (!blnComp) 
                        {
                            vecReg.add(INT_VEC_DEBE, objUti.redondear(tblTotProv.getValueAt(0, tblTotProv.getColumnCount() - 1).toString(), objZafParSis.getDecimalesMostrar()));
                            vecReg.add(INT_VEC_HABER, null);
                            vecReg.add(INT_VEC_REF, null);
                            vecReg.add(INT_VEC_NUEVO, null);
                            vecDetDiario.add(vecReg);
                            blnComp = true;
                        }
                        else
                        {
                            vecReg.add(INT_VEC_DEBE, null);
                            vecReg.add(INT_VEC_HABER, objUti.redondear(tblTotProv.getValueAt(0, tblTotProv.getColumnCount() - 1).toString(), objZafParSis.getDecimalesMostrar()));
                            vecReg.add(INT_VEC_REF, null);
                            vecReg.add(INT_VEC_NUEVO, null);
                            vecDetDiario.add(vecReg);
                        }
                    }
                    objAsiDiaProv.setDetalleDiario(vecDetDiario);
                }
                //</editor-fold>
            }
        }
        catch (java.lang.ArrayIndexOutOfBoundsException e) 
        {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        } 
        catch (Exception e)
        {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;

        } 
        finally 
        {
            try {  rstLoc.close();   rstLoc = null;      } catch (Throwable ignore) {    }
            try {  stmLoc.close();   stmLoc = null;      } catch (Throwable ignore) {    }
            try {  con.close();      con = null;         } catch (Throwable ignore) {    }
        }
        objAsiDiaProv.setEditable(true);
        return blnRes;
    }

    private boolean mostrarCtaPre(int intPer) 
    {
        boolean blnRes = true;
        try 
        {
            con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                switch (intPer) 
                {
                    case 1:

                        //Armar la sentencia SQL.
                        strSQL = "";
                        strSQL += "SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                        strSQL += " FROM tbm_plaCta AS a1, tbm_cabTipDoc AS a2";
                        strSQL += " WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_ctahab";
                        strSQL += " AND a2.co_emp=" + objZafParSis.getCodigoEmpresa();
                        strSQL += " AND a2.co_loc=" + objZafParSis.getCodigoLocal();
                        strSQL += " AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                        strSQL += " AND a2.st_reg='A'";
                        break;

                    case 2:

                        strSQL = "";
                        strSQL += " SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta";
                        strSQL += " FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                        strSQL += " WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                        strSQL += " AND a2.co_emp=" + objZafParSis.getCodigoEmpresa();
                        strSQL += " AND a2.co_loc=" + objZafParSis.getCodigoLocal();
                        strSQL += " AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                        strSQL += " AND a2.st_reg='S'";
                }

                rst = stm.executeQuery(strSQL);
                if (rst.next()) 
                {
                    txtCodCta.setText(rst.getString("co_cta"));
                    txtDesCorCta.setText(rst.getString("tx_codCta"));
                    txtDesLarCta.setText(rst.getString("tx_desLar"));
                    switch (intPer) 
                    {
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
        } 
        catch (java.sql.SQLException e) {   blnRes = false;       objUti.mostrarMsgErr_F1(this, e);    } 
        catch (Exception e) {  blnRes = false;       objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }

}
