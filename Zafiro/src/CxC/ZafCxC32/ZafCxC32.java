/*
 * ZafCxC32.java
 *
 * Created on 14 de junio de 2007
 * PANEL DE CONTROL DE SOLICITUD DE CREDITO
 * CREADO POR DARIO CARDENAS LANDIN 14/06/2007
 * PASADO A PRODUCCION EL 12/SEP/2007
 * MODIFICADO EL 05/NOVIEMBRE/2008
 */
package CxC.ZafCxC32;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafToolBar.ZafToolBar;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafPopupMenu.ZafPopupMenu;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafColNumerada.ZafColNumerada;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg; //para la ventana de dialogo
import Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut;
///import Librerias.ZafTblUti.ZafTblCelEdiBut_a.ZafTblCelEdiBut_a;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;//para visualizar una ventana de programa desde una columna de botones
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;

///para el boton abrir archivos///
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.File;

import Librerias.ZafPerUsr.ZafPerUsr;


/**
 *
 * @author  DCARDENAS
 */
public class ZafCxC32 extends javax.swing.JInternalFrame 
{
    ///Constantes para JTable de Accionistas///
    static final int INT_TBL_DAT_LIN=0;            //LINEA
    static final int INT_TBL_DAT_NOM_ACC=1;        //NOMBRE DE ACCIONISTA
    static final int INT_TBL_DAT_COD_CLI=2;        //CODIGO DEL CLIENTE
    static final int INT_TBL_DAT_BUT_ACC=3;        //BOTON PARA ABRIR LA VENTANA DE ACCIONISTAS (ZafCxC26)
    
    ///Constantes para JTable de Contactos///
    static final int INT_TBL_CON_LIN=0;            //LINEA
    static final int INT_TBL_CON_CLI=1;            //CODIGO DE CLIENTE
    static final int INT_TBL_CON_REG=2;            //CODIGO DE REGISTRO
    static final int INT_TBL_CON_NOM=3;            //NOMBRE DE CONTACTO
    static final int INT_TBL_CON_BUT=4;            //BOTON PARA VIZUALIZAR LA VENTANA DE CONTACTO (ZafCxC27)
    
    ///Constantes para JTable de Contactos///
    static final int INT_TBL_OBS_LIN=0;            //LINEA
    static final int INT_TBL_OBS_FEC=1;            //FECHA DE LA OBSERVACION
    static final int INT_TBL_OBS_DES=2;            //DESCRIPCION DE LA OBSERVACION
    static final int INT_TBL_OBS_BUT=3;            //BOTON PARA VIZUALIZAR LA VENTANA DE DIALOGO DE LA OBSERVACION
    
    ///Constantes para JTable de Referencia Comercial///
    static final int INT_TBL_REF_COM_LIN=0;        //LINEA
    static final int INT_TBL_REF_COM_SOL=1;        //CODIGO DE SOLICITUD
    static final int INT_TBL_REF_COM_CLI=2;        //CODIGO DE CLIENTE
    static final int INT_TBL_REF_COM_REG=3;        //CODIGO DE REGISTRO
    static final int INT_TBL_REF_COM_NOM=4;        //NOMBRE DE REFERENCIA COMERCIAL
    static final int INT_TBL_REF_COM_BUT=5;        //BOTON PARA VIZUALIZAR LA VENTANA DE REFERENCIA COMERCIAL (ZafCxC28)
    
    ///Constantes para JTable de Referencia Bancaria///
    static final int INT_TBL_REF_BAN_LIN=0;        //LINEA
    static final int INT_TBL_REF_BAN_SOL=1;        //CODIGO DE SOLICITUD
    static final int INT_TBL_REF_BAN_CLI=2;        //CODIGO DE CLIENTE
    static final int INT_TBL_REF_BAN_REG=3;        //CODIGO DE REGISTRO
    static final int INT_TBL_REF_BAN_NOM=4;        //NOMBRE DE REFERENCIA BANCARIA
    static final int INT_TBL_REF_BAN_BUT=5;        //BOTON PARA VIZUALIZAR LA VENTANA DE REFERENCIA BANCARIA (ZafCxC29)
    
    ///Constantes para JTable de Documentos Digitalizados///
    static final int INT_TBL_DOC_DIG_LIN=0;        //LINEA
    static final int INT_TBL_DOC_DIG_CLI=1;        //CODIGO DE CLIENTE
    static final int INT_TBL_DOC_DIG_REG=2;        //CODIGO DE REGISTRO
    static final int INT_TBL_DOC_DIG_NOM=3;        //NOMBRE DE REFERENCIA COMERCIAL
    static final int INT_TBL_DOC_DIG_BUT=4;        //BOTON PARA VIZUALIZAR LA VENTANA DE REFERENCIA COMERCIAL (ZafCxC30)
    
    ///variables para el boton de abrir archivos////
    static JFrame frame;
    JFileChooser chooser;
    
    boolean blnCambios = false; 
    private Librerias.ZafDate.ZafDatePicker dtpFecDoc, dtpFecDocApe, dtpFecConEmp, dtpFecUltAct, dtpFecProAct;
    javax.swing.JInternalFrame jfrThis; //Hace referencia a this
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private String strSQL, strAux, strSQLCon, strFecApe;
    private Vector vecDatAcc, vecCabAcc;
    private Vector vecDatPnd, vecCabPnd;
    private Vector vecDatCon, vecCabCon;
    private Vector vecDatObs, vecCabObs;
    private Vector vecDatRefCom, vecCabRefCom;
    private Vector vecDatRefBan, vecCabRefBan;
    private Vector vecDatDocDig, vecCabDocDig;
    private Vector vecDat, vecCab, vecReg;
    private boolean blnHayCam;          //Determina si hay cambios en el formulario.
    private ZafColNumerada objColNumExc, objColNumPnd, objColNumCon;
    private ZafPopupMenu objPopMnu;
    private ZafTblPopMnu objPopMnuCon;
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private Vector vecTipCta, vecNatCta, vecEstReg, vecAux, vecAuxCon, vecAuxRefCom, vecAuxRefBan, vecAuxDocDig, vecAuxObs;
    private boolean blnCon;
    private ZafToolBar objToolBar;//true: Continua la ejecuciï¿½n del hilo.
    private String strTit, sSQL, strFiltro;
    private String strCodCli, strDesLarCli,strIdeCli, strDirCli, strDesTipPer, strNomTipPer, strCodTipPer, strCodForPagCreCon, strForPagCreCon;
    private String strDesCorCta, strDesLarCta, strSolCre, strCiu, strZon, strVen, strNomVen, strCodForPag, strForPag;
    private java.util.Vector vecRegAcc, vecRegPnd;
    private mitoolbar objTooBar;
    private String sSQLCon;
    private java.sql.Connection conCab, conCabHis, conDet, conDetHis, con, conHis, conCnsDet, conAnu;
    private java.sql.Connection conCon, conConHis, conObs, conObsHis, conRefCom, conRefComHis, conRefBan, conRefBanHis, conDocDig, conDocDigHis, conDatCli, conDatCliHis, conDetCli, conDetCliHis;

    private java.sql.Statement stmCab, stmCabHis, stmDet, stmDetHis, stm, stmHis, stmCnsDet, stmAnu;
    private java.sql.ResultSet rstCab, rstCabHis, rstDet, rstDetHis, rst, rstHis, rstCnsDet;
    private java.sql.Statement stmCon, stmConHis, stmObs, stmObsHis, stmRefCom, stmRefComHis, stmRefBan, stmRefBanHis, stmDocDig, stmDocDigHis, stmDatCli, stmDatCliHis, stmDetCli, stmDetCliHis;
    private java.sql.ResultSet rstCon, rstConHis, rstObs, rstObsHis, rstRefCom, rstRefComHis, rstRefBan, rstRefBanHis, rstDocDig, rstDocDigHis, rstDatCli, rstDatCliHis, rstDetCli, rstDetCliHis;
    private tblHilo objHilo;
    private ZafTblMod objTblModAcc, objTblModCon, objTblModObs, objTblModRefCom, objTblModRefBan, objTblModDocDig;
    
    
    //creacion de check
    private ZafTblCelRenChk objTblCelRenChkExc;
    private ZafTblCelRenChk objTblCelRenChkPnd;
    private ZafTblCelEdiChk objTblCelEdiChkExc;
    private ZafTblCelEdiChk objTblCelEdiChkPnd;
    
    //creacion de label
    private ZafTblCelRenLbl objTblCelRenLblExc;
    private ZafTblCelRenLbl objTblCelRenLblPnd;
    
    //creacion de txt
    private ZafTblCelEdiTxt objTblCelEdiTxt;            //Editor: JTextField en celda.
//    private ZafTblModLis objTblModLis;                  //Detectar cambios de valores en las celdas.
    private ZafTblCelEdiTxt objTblCelEdiTxtExc;
    private ZafTblCelEdiTxt objTblCelEdiTxtPnd;
    
    ///creacion Boton///
    private ZafTblCelRenBut objTblCelRenButCon;
    private ZafTblCelRenBut objTblCelRenBut;            //Render: Presentar JButton en JTable.
    private ZafTblCelEdiButDlg objTblCelEdiButDlg;      //Editor: JButton en celda.
    private ZafTblCelEdiBut objTblCelEdiBut;            //Editor: JButton en celda.
    ///private ZafTblCelEdiBut_a objTblCelEdiBut_a;     //Editor: JButton en celda.
    private ZafTblCelEdiButGen objTblCelEdiButGen;      //Editor: JButton en celda.
    
    private ZafTblBus objTblBus;
    
    private ZafVenCon vcoCli, vcoEmp, vcoForPag, vcoForPagCreCon;                   //Ventana de consulta.
    
    private int j=1;    
    
    private ZafTblEdi objTblEdi, objTblEdiCon;
    
    
    private double dblMonDoc=0.00;
    private double dblValAboBef=0.00;
    private double varTmp=0.00;

    private int c=0, m=0, e=0, a=0, INTCODMNU=0;      //Bandera para cada click///
    private int intSig=1, CODEMP=0, BAN=0, CODCLI=0, CODSOL=0;                        //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private String strTipDoc, strDesLarTipDoc;        //Contenido del campo al obtener el foco.
    private String strUbiCta;                           //Campos: Ubicaciï¿½n de la cuenta.        
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.    
    java.util.Calendar objFec = java.util.Calendar.getInstance();
    Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
    
    ZafPerUsr objPerUsr;
    private boolean blnPerUsrCon;///Boton Consultar
    private boolean blnPerUsrMod;///Boton Modificar
    private boolean blnPerUsrAce;///Boton Aceptar
    private boolean blnPerUsrCan;///Boton Cancelar
    
    /** Crea una nueva instancia de la clase ZafCxC32. */
    public ZafCxC32(ZafParSis obj) 
    {
        try
        {
            ////boton abrir archivos///
            chooser = new JFileChooser();
            
            initComponents();
            //Inicializar objetos.
            this.objParSis=obj;
            jfrThis = this;
            objParSis=(ZafParSis)obj.clone();
            
            ////fecha de referencia de solicitud///
            dtpFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
            ////fecha de constitucion de la empresa///
            dtpFecConEmp = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
            ////fecha de Ultima Actualizacion de Datos///
            dtpFecUltAct = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
            ////fecha de Ultima Actualizacion de Datos///
            dtpFecProAct = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
            
            if (!configurarFrm())
                exitForm();
            
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
        
    }
    
    public ZafCxC32(ZafParSis objeto, Integer codigoEmpresa, Integer codigoCliente)
    {
        this(objeto);
        
        a=0;
        int codcli=0;
        int codemp=0;
        int codsol=0;
        String nomcli="";
        
        codemp = Integer.parseInt(codigoEmpresa.toString());
        codcli = Integer.parseInt(codigoCliente.toString());
        CODEMP = codemp;
        
        lblTit.setText("Panel de Control de Solicitud de Credito...");
                
        txtCodCli.setEditable(false);
        txtCodCli.setText(""+codcli);
        consultarReg();
        objTooBar.setEstado('w');
        a++;
        
        /* Tablas Editables para poder Consultar */
        tblCon.setEnabled(true);
        tblRefCom.setEnabled(true);
        tblRefBan.setEnabled(true);
    }
    
    public ZafCxC32(ZafParSis objeto, Integer codigoEmpresa, Integer codigoCliente, Integer codigoSolicitud)
    {
        this(objeto);
        
        a=0;
        int codcli=0;
        int codemp=0;
        int codsol=0;
        String nomcli="";
        
        codemp = Integer.parseInt(codigoEmpresa.toString());
        codcli = Integer.parseInt(codigoCliente.toString());        
        codsol = Integer.parseInt(codigoSolicitud.toString());
        
        CODEMP = codemp;
        CODCLI = codcli;
        CODSOL = codsol;
        
        lblTit.setText("Panel de Control de Solicitud de Credito...");
                
        txtCodCli.setEditable(false);
        txtCodCli.setText(""+codcli);
        txtSolCre.setText(""+codsol);
        consultarReg();
        objTooBar.setEstado('w');
        a++;
        
        /* Tablas Editables para poder Consultar */
        tblCon.setEnabled(true);
        tblRefCom.setEnabled(true);
        tblRefBan.setEnabled(true);
    }
    
    public ZafCxC32(ZafParSis objeto, String codigoEmpresa, String codigoCliente, String codigoSolicitud)
    {
        this(objeto);
        
        a=0;
        String codcli="";
        String codemp="";
        String codsol="";
        String nomcli="";
        
        codemp = codigoEmpresa;
        codcli = codigoCliente;        
        codsol = codigoSolicitud;
        
        lblTit.setText("Panel de Control de Solicitud de Credito...");
                
        txtCodCli.setEditable(false);
        txtCodCli.setText(""+codcli);
        txtSolCre.setText(""+codsol);
        consultarReg();
        objTooBar.setEstado('w');
        a++;
        
        //Establecer estados a los botones///
            objTooBar.setVisibleAnular(false);
            objTooBar.setVisibleModificar(false);
            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleInsertar(false);
        
        /* Tablas Editables para poder Consultar */
        tblCon.setEnabled(true);
        tblRefCom.setEnabled(true);
        tblRefBan.setEnabled(true);
    }
    
    
    public ZafCxC32(ZafParSis objeto, Integer codigoEmpresa, Integer codigoCliente, Integer codigoSolicitud, Integer bandera)
    {
        this(objeto);
        
        int a=0;
        int codcli=0;
        int codemp=0;
        int ban=0;
        int codsol=0;
        String nomcli="";
        
        codemp = Integer.parseInt(codigoEmpresa.toString());
        codcli = Integer.parseInt(codigoCliente.toString());        
        codsol = Integer.parseInt(codigoSolicitud.toString());
        ban = Integer.parseInt(bandera.toString());

        CODEMP = codemp;
        CODCLI = codcli;
        CODSOL = codsol;
        BAN = ban;
        
        lblTit.setText("Panel de Control de Solicitud de Credito...");
                
        txtCodCli.setEditable(false);
        txtCodCli.setText(""+codcli);
        txtSolCre.setText(""+codsol);
        consultarReg();        
        
        if(ban > 0)
        {
            tabFrm.setSelectedIndex(6);
            
//            objTooBar.setVisibleAnular(false);
//            objTooBar.setVisibleConsultar(false);
//            objTooBar.setVisibleEliminar(false);
//            objTooBar.setVisibleInsertar(false);
            
            objTooBar.setEstado('m');
            
            isInaCam();
            
            tabFrm.setSelectedIndex(7);
            txtMonApr.requestFocus();
        }
        a++;
        
        /* Tablas Editables para poder Consultar */
        tblCon.setEnabled(true);
        tblRefCom.setEnabled(true);
        tblRefBan.setEnabled(true);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrTipCta = new javax.swing.ButtonGroup();
        bgrNatCta = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panDat = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panGenSolCre = new javax.swing.JPanel();
        lblCodDoc = new javax.swing.JLabel();
        txtDesTipPer = new javax.swing.JTextField();
        lblTipIde = new javax.swing.JLabel();
        lblCli = new javax.swing.JLabel();
        txtNomCli = new javax.swing.JTextField();
        txtCodCli = new javax.swing.JTextField();
        lblAge = new javax.swing.JLabel();
        txtIde = new javax.swing.JTextField();
        txtCiu = new javax.swing.JTextField();
        lblObs1 = new javax.swing.JLabel();
        lblRefUbi = new javax.swing.JLabel();
        txtTelf = new javax.swing.JTextField();
        lblFax = new javax.swing.JLabel();
        txtNomVen = new javax.swing.JTextField();
        txtFax = new javax.swing.JTextField();
        lblTelf = new javax.swing.JLabel();
        txtSolCre = new javax.swing.JTextField();
        lblSolCre = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblInf = new javax.swing.JLabel();
        txtCasilla = new javax.swing.JTextField();
        lblFecRef = new javax.swing.JLabel();
        butCiu = new javax.swing.JButton();
        lblCasilla = new javax.swing.JLabel();
        txtCodCiu = new javax.swing.JTextField();
        lblCiudad = new javax.swing.JLabel();
        lblMonCreDir = new javax.swing.JLabel();
        txtWeb = new javax.swing.JTextField();
        lblDir = new javax.swing.JLabel();
        txtDir = new javax.swing.JTextField();
        butPro = new javax.swing.JButton();
        jChkCli = new javax.swing.JCheckBox();
        jChkPrv = new javax.swing.JCheckBox();
        txtNomTipPer = new javax.swing.JTextField();
        butTipPer = new javax.swing.JButton();
        jCbxTipIde = new javax.swing.JComboBox();
        txtRefUbi = new javax.swing.JTextField();
        lblTelCon1 = new javax.swing.JLabel();
        txtTelCon1 = new javax.swing.JTextField();
        lblTelCon2 = new javax.swing.JLabel();
        txtTelCon2 = new javax.swing.JTextField();
        lblTelCon3 = new javax.swing.JLabel();
        txtTelCon3 = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        lblZona = new javax.swing.JLabel();
        txtCodZon = new javax.swing.JTextField();
        txtNomZon = new javax.swing.JTextField();
        butZona = new javax.swing.JButton();
        txtCodVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        txtDesVen = new javax.swing.JTextField();
        txtCodTipPer = new javax.swing.JTextField();
        lblObs3 = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObsCxC = new javax.swing.JTextArea();
        spnObs3 = new javax.swing.JScrollPane();
        txaObsInfBurCre = new javax.swing.JTextArea();
        lblCodHisSolCre = new javax.swing.JLabel();
        txtCodHisSolCre = new javax.swing.JTextField();
        panProEmp = new javax.swing.JPanel();
        panGenProEmp = new javax.swing.JPanel();
        panCabDat = new javax.swing.JPanel();
        lblCedPasPro = new javax.swing.JLabel();
        txtCedPasPro = new javax.swing.JTextField();
        lblNomPro = new javax.swing.JLabel();
        txtNomPro = new javax.swing.JTextField();
        lblFecConEmp = new javax.swing.JLabel();
        txtNumCta = new javax.swing.JTextField();
        txtNumPro = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDomPro = new javax.swing.JTextField();
        lblDomPro = new javax.swing.JLabel();
        lblTelPro = new javax.swing.JLabel();
        txtTelPro = new javax.swing.JTextField();
        lblNacPro = new javax.swing.JLabel();
        txtNacPro = new javax.swing.JTextField();
        lblTipActEmp = new javax.swing.JLabel();
        txtTipActEmp = new javax.swing.JTextField();
        lblObs2 = new javax.swing.JLabel();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        panAcc = new javax.swing.JPanel();
        spnAcc = new javax.swing.JScrollPane();
        tblAcc = new javax.swing.JTable();
        panCon = new javax.swing.JPanel();
        spnCon = new javax.swing.JScrollPane();
        tblCon = new javax.swing.JTable();
        panObs = new javax.swing.JPanel();
        spnObs = new javax.swing.JScrollPane();
        tblObs = new javax.swing.JTable();
        panRefCom = new javax.swing.JPanel();
        spnRefCom = new javax.swing.JScrollPane();
        tblRefCom = new javax.swing.JTable();
        panRefBan = new javax.swing.JPanel();
        spnRefBan = new javax.swing.JScrollPane();
        tblRefBan = new javax.swing.JTable();
        panDocDig = new javax.swing.JPanel();
        spnDocDig = new javax.swing.JScrollPane();
        tblDocDig = new javax.swing.JTable();
        panDatcre = new javax.swing.JPanel();
        spnDatcre = new javax.swing.JScrollPane();
        panDatGen = new javax.swing.JPanel();
        panCreSol = new javax.swing.JPanel();
        lblMonSol = new javax.swing.JLabel();
        txtMonSol = new javax.swing.JTextField();
        lblForPag = new javax.swing.JLabel();
        txtForPag = new javax.swing.JTextField();
        butForPag = new javax.swing.JButton();
        txtCodForPag = new javax.swing.JTextField();
        panCreCon = new javax.swing.JPanel();
        lblMonApr = new javax.swing.JLabel();
        lblForPagCreCon = new javax.swing.JLabel();
        txtForPagCreCon = new javax.swing.JTextField();
        txtMonApr = new javax.swing.JTextField();
        butForPagCreCon = new javax.swing.JButton();
        txtCodForPagCreCon = new javax.swing.JTextField();
        panActDat = new javax.swing.JPanel();
        lblFecUltAct = new javax.swing.JLabel();
        lblProAct = new javax.swing.JLabel();
        txtProAct = new javax.swing.JTextField();
        lblFecProAct = new javax.swing.JLabel();
        lblObsSolCre1 = new javax.swing.JLabel();
        spnObsSolCre1 = new javax.swing.JScrollPane();
        txaObsSolCre1 = new javax.swing.JTextArea();
        lblObsSolCre2 = new javax.swing.JLabel();
        spnObsSolCre2 = new javax.swing.JScrollPane();
        txaObsSolCre2 = new javax.swing.JTextArea();
        panBar = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
        setPreferredSize(new java.awt.Dimension(116, 210));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                exitForm(evt);
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
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Información del registro actual");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setPreferredSize(new java.awt.Dimension(605, 440));

        panDat.setPreferredSize(new java.awt.Dimension(453, 403));
        panDat.setLayout(new java.awt.BorderLayout());

        panGenSolCre.setPreferredSize(new java.awt.Dimension(610, 420));
        panGenSolCre.setLayout(null);

        lblCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodDoc.setText("Tipo Persona:");
        lblCodDoc.setPreferredSize(new java.awt.Dimension(100, 15));
        panGenSolCre.add(lblCodDoc);
        lblCodDoc.setBounds(10, 57, 105, 15);

        txtDesTipPer.setPreferredSize(new java.awt.Dimension(100, 20));
        txtDesTipPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesTipPerActionPerformed(evt);
            }
        });
        txtDesTipPer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesTipPerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesTipPerFocusLost(evt);
            }
        });
        panGenSolCre.add(txtDesTipPer);
        txtDesTipPer.setBounds(118, 55, 66, 20);

        lblTipIde.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTipIde.setText("Tip. Identificacion:");
        lblTipIde.setPreferredSize(new java.awt.Dimension(100, 15));
        panGenSolCre.add(lblTipIde);
        lblTipIde.setBounds(10, 82, 105, 15);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCli.setText("Cliente:");
        lblCli.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenSolCre.add(lblCli);
        lblCli.setBounds(10, 32, 70, 15);

        txtNomCli.setMaximumSize(null);
        txtNomCli.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });
        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        panGenSolCre.add(txtNomCli);
        txtNomCli.setBounds(185, 30, 409, 20);

        txtCodCli.setMaximumSize(null);
        txtCodCli.setPreferredSize(new java.awt.Dimension(70, 20));
        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        panGenSolCre.add(txtCodCli);
        txtCodCli.setBounds(118, 30, 66, 20);

        lblAge.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblAge.setText("Identificacion:");
        lblAge.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenSolCre.add(lblAge);
        lblAge.setBounds(420, 82, 80, 15);

        txtIde.setMaximumSize(null);
        txtIde.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenSolCre.add(txtIde);
        txtIde.setBounds(500, 80, 120, 20);

        txtCiu.setMaximumSize(null);
        txtCiu.setPreferredSize(new java.awt.Dimension(70, 20));
        txtCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiuActionPerformed(evt);
            }
        });
        txtCiu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCiuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCiuFocusLost(evt);
            }
        });
        panGenSolCre.add(txtCiu);
        txtCiu.setBounds(118, 255, 189, 20);

        lblObs1.setFont(new java.awt.Font("Dialog", 0, 12));
        lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs1.setText("Obs. CxC:");
        lblObs1.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs1.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs1.setPreferredSize(new java.awt.Dimension(92, 8));
        panGenSolCre.add(lblObs1);
        lblObs1.setBounds(10, 305, 100, 23);

        lblRefUbi.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblRefUbi.setText("Ref. Direccion:");
        lblRefUbi.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenSolCre.add(lblRefUbi);
        lblRefUbi.setBounds(10, 132, 105, 15);

        txtTelf.setMaximumSize(null);
        txtTelf.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenSolCre.add(txtTelf);
        txtTelf.setBounds(118, 180, 288, 20);

        lblFax.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFax.setText("Fax:");
        lblFax.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenSolCre.add(lblFax);
        lblFax.setBounds(10, 207, 105, 15);

        txtNomVen.setMaximumSize(null);
        txtNomVen.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNomVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomVenActionPerformed(evt);
            }
        });
        txtNomVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomVenFocusLost(evt);
            }
        });
        panGenSolCre.add(txtNomVen);
        txtNomVen.setBounds(185, 280, 156, 20);

        txtFax.setMaximumSize(null);
        txtFax.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenSolCre.add(txtFax);
        txtFax.setBounds(118, 205, 215, 20);

        lblTelf.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTelf.setText("Telefonos:");
        lblTelf.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenSolCre.add(lblTelf);
        lblTelf.setBounds(10, 182, 105, 15);

        txtSolCre.setMaximumSize(null);
        txtSolCre.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenSolCre.add(txtSolCre);
        txtSolCre.setBounds(118, 5, 66, 20);

        lblSolCre.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblSolCre.setText("Solicitud Credito:");
        lblSolCre.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenSolCre.add(lblSolCre);
        lblSolCre.setBounds(10, 7, 100, 15);

        lblEmail.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblEmail.setText("E-mail:");
        lblEmail.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenSolCre.add(lblEmail);
        lblEmail.setBounds(10, 232, 105, 15);

        lblInf.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblInf.setText("Vendedor:");
        lblInf.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenSolCre.add(lblInf);
        lblInf.setBounds(10, 282, 75, 15);

        txtCasilla.setMaximumSize(null);
        txtCasilla.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenSolCre.add(txtCasilla);
        txtCasilla.setBounds(405, 205, 215, 20);

        lblFecRef.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecRef.setText("Fecha Referencia:");
        lblFecRef.setPreferredSize(new java.awt.Dimension(100, 15));
        panGenSolCre.add(lblFecRef);
        lblFecRef.setBounds(410, 7, 105, 15);

        butCiu.setFont(new java.awt.Font("SansSerif", 1, 12));
        butCiu.setText("...");
        butCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCiuActionPerformed(evt);
            }
        });
        panGenSolCre.add(butCiu);
        butCiu.setBounds(309, 255, 24, 20);

        lblCasilla.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCasilla.setText("Casilla:");
        lblCasilla.setPreferredSize(new java.awt.Dimension(100, 15));
        panGenSolCre.add(lblCasilla);
        lblCasilla.setBounds(355, 207, 50, 15);

        txtCodCiu.setMaximumSize(null);
        txtCodCiu.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenSolCre.add(txtCodCiu);
        txtCodCiu.setBounds(92, 255, 25, 20);

        lblCiudad.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCiudad.setText("Ciudad:");
        lblCiudad.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenSolCre.add(lblCiudad);
        lblCiudad.setBounds(10, 257, 50, 15);

        lblMonCreDir.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblMonCreDir.setText("Web:");
        lblMonCreDir.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenSolCre.add(lblMonCreDir);
        lblMonCreDir.setBounds(355, 232, 50, 15);

        txtWeb.setMaximumSize(null);
        txtWeb.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenSolCre.add(txtWeb);
        txtWeb.setBounds(405, 230, 215, 20);

        lblDir.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDir.setText("Direccion:");
        lblDir.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenSolCre.add(lblDir);
        lblDir.setBounds(10, 107, 105, 15);

        txtDir.setMaximumSize(null);
        txtDir.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenSolCre.add(txtDir);
        txtDir.setBounds(118, 105, 502, 20);

        butPro.setFont(new java.awt.Font("SansSerif", 1, 12));
        butPro.setText("...");
        butPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butProActionPerformed(evt);
            }
        });
        panGenSolCre.add(butPro);
        butPro.setBounds(596, 30, 24, 20);

        jChkCli.setSelected(true);
        jChkCli.setText("Es Cliente");
        jChkCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChkCliActionPerformed(evt);
            }
        });
        panGenSolCre.add(jChkCli);
        jChkCli.setBounds(420, 55, 77, 22);

        jChkPrv.setText("Es Proveedor");
        jChkPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChkPrvActionPerformed(evt);
            }
        });
        panGenSolCre.add(jChkPrv);
        jChkPrv.setBounds(520, 55, 120, 22);

        txtNomTipPer.setPreferredSize(new java.awt.Dimension(100, 20));
        txtNomTipPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTipPerActionPerformed(evt);
            }
        });
        txtNomTipPer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTipPerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTipPerFocusLost(evt);
            }
        });
        panGenSolCre.add(txtNomTipPer);
        txtNomTipPer.setBounds(185, 55, 156, 20);

        butTipPer.setFont(new java.awt.Font("SansSerif", 1, 12));
        butTipPer.setText("...");
        butTipPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipPerActionPerformed(evt);
            }
        });
        panGenSolCre.add(butTipPer);
        butTipPer.setBounds(343, 55, 24, 20);

        jCbxTipIde.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Numero Cedula", "Numero Ruc" }));
        panGenSolCre.add(jCbxTipIde);
        jCbxTipIde.setBounds(118, 80, 130, 20);

        txtRefUbi.setMaximumSize(null);
        txtRefUbi.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenSolCre.add(txtRefUbi);
        txtRefUbi.setBounds(118, 130, 502, 20);

        lblTelCon1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTelCon1.setText("Tlf. Contacto1:");
        lblTelCon1.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenSolCre.add(lblTelCon1);
        lblTelCon1.setBounds(10, 157, 105, 15);

        txtTelCon1.setMaximumSize(null);
        txtTelCon1.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenSolCre.add(txtTelCon1);
        txtTelCon1.setBounds(118, 155, 85, 20);

        lblTelCon2.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTelCon2.setText("Tlf. Contacto2:");
        lblTelCon2.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenSolCre.add(lblTelCon2);
        lblTelCon2.setBounds(230, 157, 85, 15);

        txtTelCon2.setMaximumSize(null);
        txtTelCon2.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenSolCre.add(txtTelCon2);
        txtTelCon2.setBounds(320, 155, 85, 20);

        lblTelCon3.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTelCon3.setText("Tlf. Contacto3:");
        lblTelCon3.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenSolCre.add(lblTelCon3);
        lblTelCon3.setBounds(445, 157, 85, 15);

        txtTelCon3.setMaximumSize(null);
        txtTelCon3.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenSolCre.add(txtTelCon3);
        txtTelCon3.setBounds(535, 155, 85, 20);

        txtEmail.setMaximumSize(null);
        txtEmail.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenSolCre.add(txtEmail);
        txtEmail.setBounds(118, 230, 215, 20);

        lblZona.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblZona.setText("Zona:");
        lblZona.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenSolCre.add(lblZona);
        lblZona.setBounds(355, 257, 35, 15);

        txtCodZon.setMaximumSize(null);
        txtCodZon.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenSolCre.add(txtCodZon);
        txtCodZon.setBounds(378, 255, 25, 20);

        txtNomZon.setMaximumSize(null);
        txtNomZon.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNomZon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomZonActionPerformed(evt);
            }
        });
        txtNomZon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomZonFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomZonFocusLost(evt);
            }
        });
        panGenSolCre.add(txtNomZon);
        txtNomZon.setBounds(405, 255, 189, 20);

        butZona.setFont(new java.awt.Font("SansSerif", 1, 12));
        butZona.setText("...");
        butZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butZonaActionPerformed(evt);
            }
        });
        panGenSolCre.add(butZona);
        butZona.setBounds(596, 255, 24, 20);

        txtCodVen.setMaximumSize(null);
        txtCodVen.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenSolCre.add(txtCodVen);
        txtCodVen.setBounds(92, 280, 25, 20);

        butVen.setFont(new java.awt.Font("SansSerif", 1, 12));
        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panGenSolCre.add(butVen);
        butVen.setBounds(343, 280, 24, 20);

        txtDesVen.setPreferredSize(new java.awt.Dimension(100, 20));
        txtDesVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesVenActionPerformed(evt);
            }
        });
        txtDesVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesVenFocusLost(evt);
            }
        });
        panGenSolCre.add(txtDesVen);
        txtDesVen.setBounds(118, 280, 66, 20);

        txtCodTipPer.setMaximumSize(null);
        txtCodTipPer.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenSolCre.add(txtCodTipPer);
        txtCodTipPer.setBounds(92, 55, 25, 20);

        lblObs3.setFont(new java.awt.Font("Dialog", 0, 12));
        lblObs3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs3.setText("Obs. de Buro:");
        lblObs3.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs3.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs3.setPreferredSize(new java.awt.Dimension(92, 8));
        panGenSolCre.add(lblObs3);
        lblObs3.setBounds(10, 360, 100, 23);

        spnObs1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        txaObsCxC.setLineWrap(true);
        spnObs1.setViewportView(txaObsCxC);

        panGenSolCre.add(spnObs1);
        spnObs1.setBounds(118, 305, 545, 50);

        spnObs3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        txaObsInfBurCre.setLineWrap(true);
        txaObsInfBurCre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txaObsInfBurCreFocusLost(evt);
            }
        });
        spnObs3.setViewportView(txaObsInfBurCre);

        panGenSolCre.add(spnObs3);
        spnObs3.setBounds(118, 362, 545, 50);

        lblCodHisSolCre.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodHisSolCre.setText("Cod.His:");
        lblCodHisSolCre.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenSolCre.add(lblCodHisSolCre);
        lblCodHisSolCre.setBounds(220, 7, 50, 15);

        txtCodHisSolCre.setMaximumSize(null);
        txtCodHisSolCre.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenSolCre.add(txtCodHisSolCre);
        txtCodHisSolCre.setBounds(270, 5, 66, 20);

        jScrollPane1.setViewportView(panGenSolCre);

        panDat.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panDat);

        panProEmp.setPreferredSize(new java.awt.Dimension(453, 403));
        panProEmp.setLayout(new java.awt.BorderLayout());

        panGenProEmp.setPreferredSize(new java.awt.Dimension(10, 230));
        panGenProEmp.setLayout(new java.awt.BorderLayout());

        panCabDat.setPreferredSize(new java.awt.Dimension(400, 230));
        panCabDat.setLayout(null);

        lblCedPasPro.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCedPasPro.setText("Ced/Pasaporte:");
        lblCedPasPro.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDat.add(lblCedPasPro);
        lblCedPasPro.setBounds(10, 6, 105, 15);

        txtCedPasPro.setPreferredSize(new java.awt.Dimension(100, 20));
        panCabDat.add(txtCedPasPro);
        txtCedPasPro.setBounds(140, 6, 100, 20);

        lblNomPro.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNomPro.setText("Nombre Propietario:");
        lblNomPro.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDat.add(lblNomPro);
        lblNomPro.setBounds(10, 30, 120, 15);

        txtNomPro.setPreferredSize(new java.awt.Dimension(6, 20));
        panCabDat.add(txtNomPro);
        txtNomPro.setBounds(140, 30, 320, 20);

        lblFecConEmp.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecConEmp.setText("Fecha de Constitucion de la Empresa");
        lblFecConEmp.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDat.add(lblFecConEmp);
        lblFecConEmp.setBounds(10, 126, 210, 15);

        txtNumCta.setMaximumSize(null);
        txtNumCta.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDat.add(txtNumCta);
        txtNumCta.setBounds(180, 25, 0, 0);

        txtNumPro.setMaximumSize(null);
        txtNumPro.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDat.add(txtNumPro);
        txtNumPro.setBounds(180, 46, 0, 0);
        panCabDat.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(106, 4, 0, 0);

        txtDomPro.setMaximumSize(null);
        txtDomPro.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDat.add(txtDomPro);
        txtDomPro.setBounds(140, 54, 320, 20);

        lblDomPro.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDomPro.setText("Domicilio Propietario");
        lblDomPro.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDat.add(lblDomPro);
        lblDomPro.setBounds(10, 54, 120, 15);

        lblTelPro.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTelPro.setText("Telefonos Propietario");
        lblTelPro.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDat.add(lblTelPro);
        lblTelPro.setBounds(10, 78, 120, 15);

        txtTelPro.setMaximumSize(null);
        txtTelPro.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDat.add(txtTelPro);
        txtTelPro.setBounds(140, 78, 320, 20);

        lblNacPro.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNacPro.setText("Nacionalidad Propietario");
        lblNacPro.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDat.add(lblNacPro);
        lblNacPro.setBounds(10, 102, 120, 15);

        txtNacPro.setMaximumSize(null);
        txtNacPro.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDat.add(txtNacPro);
        txtNacPro.setBounds(140, 102, 100, 20);

        lblTipActEmp.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTipActEmp.setText("Tipo de Actividad de la Empresa");
        lblTipActEmp.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDat.add(lblTipActEmp);
        lblTipActEmp.setBounds(10, 150, 180, 15);

        txtTipActEmp.setMaximumSize(null);
        txtTipActEmp.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDat.add(txtTipActEmp);
        txtTipActEmp.setBounds(185, 150, 500, 20);

        lblObs2.setFont(new java.awt.Font("Dialog", 0, 12));
        lblObs2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs2.setText("Observación:");
        lblObs2.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs2.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs2.setPreferredSize(new java.awt.Dimension(92, 8));
        panCabDat.add(lblObs2);
        lblObs2.setBounds(10, 174, 92, 23);

        spnObs2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        txaObs1.setLineWrap(true);
        spnObs2.setViewportView(txaObs1);

        panCabDat.add(spnObs2);
        spnObs2.setBounds(90, 174, 595, 45);

        panGenProEmp.add(panCabDat, java.awt.BorderLayout.NORTH);

        panProEmp.add(panGenProEmp, java.awt.BorderLayout.NORTH);

        panAcc.setPreferredSize(new java.awt.Dimension(453, 100));
        panAcc.setLayout(new java.awt.BorderLayout());

        tblAcc.setModel(new javax.swing.table.DefaultTableModel(
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
        spnAcc.setViewportView(tblAcc);

        panAcc.add(spnAcc, java.awt.BorderLayout.CENTER);

        panProEmp.add(panAcc, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Prop.Empresa", panProEmp);

        panCon.setLayout(new java.awt.BorderLayout());

        tblCon.setModel(new javax.swing.table.DefaultTableModel(
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
        tblCon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblConKeyPressed(evt);
            }
        });
        tblCon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblConMouseClicked(evt);
            }
        });
        spnCon.setViewportView(tblCon);

        panCon.add(spnCon, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Contactos", panCon);

        panObs.setPreferredSize(new java.awt.Dimension(453, 403));
        panObs.setLayout(new java.awt.BorderLayout());

        tblObs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnObs.setViewportView(tblObs);

        panObs.add(spnObs, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Observacion", panObs);

        panRefCom.setLayout(new java.awt.BorderLayout());

        tblRefCom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRefCom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblRefComKeyPressed(evt);
            }
        });
        tblRefCom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRefComMouseClicked(evt);
            }
        });
        spnRefCom.setViewportView(tblRefCom);

        panRefCom.add(spnRefCom, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Ref.Comercial.", panRefCom);

        panRefBan.setLayout(new java.awt.BorderLayout());

        tblRefBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRefBan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblRefBanKeyPressed(evt);
            }
        });
        tblRefBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRefBanMouseClicked(evt);
            }
        });
        spnRefBan.setViewportView(tblRefBan);

        panRefBan.add(spnRefBan, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Ref.Bancaria", panRefBan);

        panDocDig.setLayout(new java.awt.BorderLayout());

        tblDocDig.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnDocDig.setViewportView(tblDocDig);

        panDocDig.add(spnDocDig, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Doc.Digital.", panDocDig);

        panDatcre.setPreferredSize(new java.awt.Dimension(453, 403));
        panDatcre.setLayout(new java.awt.BorderLayout());

        spnDatcre.setPreferredSize(new java.awt.Dimension(453, 403));

        panDatGen.setPreferredSize(new java.awt.Dimension(100, 180));
        panDatGen.setLayout(null);

        panCreSol.setBorder(javax.swing.BorderFactory.createTitledBorder("Credito Solicitado: "));
        panCreSol.setPreferredSize(new java.awt.Dimension(10, 100));
        panCreSol.setLayout(null);

        lblMonSol.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblMonSol.setText("Monto Solicitado:  $");
        lblMonSol.setPreferredSize(new java.awt.Dimension(100, 15));
        panCreSol.add(lblMonSol);
        lblMonSol.setBounds(10, 32, 105, 15);

        txtMonSol.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtMonSol.setPreferredSize(new java.awt.Dimension(6, 20));
        txtMonSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMonSolActionPerformed(evt);
            }
        });
        txtMonSol.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMonSolFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMonSolFocusLost(evt);
            }
        });
        panCreSol.add(txtMonSol);
        txtMonSol.setBounds(118, 30, 110, 20);

        lblForPag.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblForPag.setText("Forma de Pago:");
        lblForPag.setPreferredSize(new java.awt.Dimension(110, 15));
        panCreSol.add(lblForPag);
        lblForPag.setBounds(10, 56, 90, 15);

        txtForPag.setMaximumSize(null);
        txtForPag.setPreferredSize(new java.awt.Dimension(70, 20));
        txtForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtForPagActionPerformed(evt);
            }
        });
        txtForPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtForPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtForPagFocusLost(evt);
            }
        });
        panCreSol.add(txtForPag);
        txtForPag.setBounds(118, 54, 180, 20);

        butForPag.setFont(new java.awt.Font("SansSerif", 1, 12));
        butForPag.setText("...");
        butForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butForPagActionPerformed(evt);
            }
        });
        panCreSol.add(butForPag);
        butForPag.setBounds(300, 54, 24, 20);

        txtCodForPag.setMaximumSize(null);
        txtCodForPag.setPreferredSize(new java.awt.Dimension(70, 20));
        panCreSol.add(txtCodForPag);
        txtCodForPag.setBounds(92, 54, 25, 20);

        panDatGen.add(panCreSol);
        panCreSol.setBounds(10, 10, 330, 100);

        panCreCon.setBorder(javax.swing.BorderFactory.createTitledBorder("Credito Concedido: "));
        panCreCon.setPreferredSize(new java.awt.Dimension(10, 100));
        panCreCon.setLayout(null);

        lblMonApr.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblMonApr.setText("Monto Aprobado: $");
        lblMonApr.setPreferredSize(new java.awt.Dimension(100, 15));
        panCreCon.add(lblMonApr);
        lblMonApr.setBounds(10, 30, 105, 15);

        lblForPagCreCon.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblForPagCreCon.setText("Forma de Pago:");
        lblForPagCreCon.setPreferredSize(new java.awt.Dimension(110, 15));
        panCreCon.add(lblForPagCreCon);
        lblForPagCreCon.setBounds(10, 54, 105, 15);

        txtForPagCreCon.setMaximumSize(null);
        txtForPagCreCon.setPreferredSize(new java.awt.Dimension(70, 20));
        txtForPagCreCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtForPagCreConActionPerformed(evt);
            }
        });
        txtForPagCreCon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtForPagCreConFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtForPagCreConFocusLost(evt);
            }
        });
        panCreCon.add(txtForPagCreCon);
        txtForPagCreCon.setBounds(118, 54, 180, 20);

        txtMonApr.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtMonApr.setPreferredSize(new java.awt.Dimension(6, 20));
        txtMonApr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMonAprActionPerformed(evt);
            }
        });
        txtMonApr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMonAprFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMonAprFocusLost(evt);
            }
        });
        panCreCon.add(txtMonApr);
        txtMonApr.setBounds(118, 30, 110, 20);

        butForPagCreCon.setFont(new java.awt.Font("SansSerif", 1, 12));
        butForPagCreCon.setText("...");
        butForPagCreCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butForPagCreConActionPerformed(evt);
            }
        });
        panCreCon.add(butForPagCreCon);
        butForPagCreCon.setBounds(300, 54, 24, 20);

        txtCodForPagCreCon.setMaximumSize(null);
        txtCodForPagCreCon.setPreferredSize(new java.awt.Dimension(70, 20));
        panCreCon.add(txtCodForPagCreCon);
        txtCodForPagCreCon.setBounds(92, 54, 25, 20);

        panDatGen.add(panCreCon);
        panCreCon.setBounds(348, 10, 330, 100);

        panActDat.setBorder(javax.swing.BorderFactory.createTitledBorder("Actualizacion de Datos: "));
        panActDat.setPreferredSize(new java.awt.Dimension(10, 100));
        panActDat.setLayout(null);

        lblFecUltAct.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecUltAct.setText("Fecha de Ultima Actualizacion:");
        lblFecUltAct.setPreferredSize(new java.awt.Dimension(100, 15));
        panActDat.add(lblFecUltAct);
        lblFecUltAct.setBounds(10, 25, 170, 15);

        lblProAct.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblProAct.setText("Proxima Actualizacion: (Meses)");
        lblProAct.setPreferredSize(new java.awt.Dimension(110, 15));
        panActDat.add(lblProAct);
        lblProAct.setBounds(10, 50, 180, 15);

        txtProAct.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtProAct.setMaximumSize(null);
        txtProAct.setPreferredSize(new java.awt.Dimension(70, 20));
        txtProAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProActActionPerformed(evt);
            }
        });
        panActDat.add(txtProAct);
        txtProAct.setBounds(190, 50, 100, 20);

        lblFecProAct.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecProAct.setText("Fecha de Proxima Actualizacion:");
        lblFecProAct.setPreferredSize(new java.awt.Dimension(100, 15));
        panActDat.add(lblFecProAct);
        lblFecProAct.setBounds(10, 75, 180, 15);

        panDatGen.add(panActDat);
        panActDat.setBounds(150, 130, 400, 100);

        lblObsSolCre1.setFont(new java.awt.Font("Dialog", 0, 12));
        lblObsSolCre1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObsSolCre1.setText("Observación 1:");
        lblObsSolCre1.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObsSolCre1.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObsSolCre1.setPreferredSize(new java.awt.Dimension(92, 8));
        panDatGen.add(lblObsSolCre1);
        lblObsSolCre1.setBounds(10, 260, 92, 23);

        spnObsSolCre1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        txaObsSolCre1.setLineWrap(true);
        spnObsSolCre1.setViewportView(txaObsSolCre1);

        panDatGen.add(spnObsSolCre1);
        spnObsSolCre1.setBounds(118, 250, 560, 40);

        lblObsSolCre2.setFont(new java.awt.Font("Dialog", 0, 12));
        lblObsSolCre2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObsSolCre2.setText("Observación 2:");
        lblObsSolCre2.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObsSolCre2.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObsSolCre2.setPreferredSize(new java.awt.Dimension(92, 8));
        panDatGen.add(lblObsSolCre2);
        lblObsSolCre2.setBounds(10, 305, 92, 23);

        spnObsSolCre2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        txaObsSolCre2.setLineWrap(true);
        spnObsSolCre2.setViewportView(txaObsSolCre2);

        panDatGen.add(spnObsSolCre2);
        spnObsSolCre2.setBounds(118, 305, 560, 40);

        spnDatcre.setViewportView(panDatGen);

        panDatcre.add(spnDatcre, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Dat.Credito", panDatcre);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void tblConMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblConMouseClicked
        //Abrir la opciï¿½n seleccionada al dar doble click.
        if (evt.getClickCount()==2)
        {
            abrirFrm();
        }
    }//GEN-LAST:event_tblConMouseClicked

    private void tblConKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblConKeyPressed
        //Abrir la opciï¿½n seleccionada al dar presionar ENTER.
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
            evt.consume();
            abrirFrm();
        }
    }//GEN-LAST:event_tblConKeyPressed

    private void txaObsInfBurCreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txaObsInfBurCreFocusLost
        tabFrm.setSelectedIndex(1);
    }//GEN-LAST:event_txaObsInfBurCreFocusLost

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened

    }//GEN-LAST:event_formInternalFrameOpened

    private void txtProActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProActActionPerformed
        int intNumMes=0;
        int intDiaCre=0;
        String strCon = txtProAct.getText();        
        java.util.Calendar objFecDivPag = objFec.getInstance();
        
        //////////////////////////////////////////////////////////////////////////
        java.util.Calendar objFec = java.util.Calendar.getInstance();
                int fecDoc [] = dtpFecUltAct.getFecha(dtpFecUltAct.getText());

                if(fecDoc!=null){
                    objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                        objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1); 
                        objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
                }
        ///////////////////////////////////////////////////////////////////////// 
                
        if (IsNumeric(strCon))
        {
            intNumMes = Integer.parseInt(strCon.toString());
            /////dias de credito/////
            intDiaCre = (intNumMes * 30);

              /////fecha de vencimiento/////
              objFecDivPag.setTime(objFec.getTime()); 

              if (intDiaCre!=0)
                  objFecDivPag.add(java.util.Calendar.DATE, intDiaCre);

              dtePckPag.setAnio( objFecDivPag.get(java.util.Calendar.YEAR));
              dtePckPag.setMes( objFecDivPag.get(java.util.Calendar.MONTH)+1);
              dtePckPag.setDia(objFecDivPag.get(java.util.Calendar.DAY_OF_MONTH));

              dtpFecProAct.setText(dtePckPag.getText());
              
              dtpFecProAct.getFocusListeners();
              
        }
        else
        {
            mostrarMsgInf("¡¡¡ POR FAVOR INGRESE DATOS NUMERICOS !!!");
            txtProAct.setText("");
            txtProAct.requestFocus();
        }
    }//GEN-LAST:event_txtProActActionPerformed

    private void txtMonAprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMonAprActionPerformed
        // TODO add your handling code here:
        String strCon = txtMonApr.getText();
        
        if (IsNumeric(strCon))
        {
            txtForPagCreCon.requestFocus();
        }
        else
        {
            mostrarMsgInf("¡¡¡ POR FAVOR INGRESE DATOS NUMERICOS !!!");
            txtMonApr.setText("");
            txtMonApr.requestFocus();
        }
    }//GEN-LAST:event_txtMonAprActionPerformed

    private void txtMonSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMonSolActionPerformed
        String strCon = txtMonSol.getText();
        if (IsNumeric(strCon))
        {
            txtForPag.requestFocus();
        }
        else
        {
            mostrarMsgInf("¡¡¡ POR FAVOR INGRESE DATOS NUMERICOS !!!");
            txtMonSol.setText("");
            txtMonSol.requestFocus();
        }
    }//GEN-LAST:event_txtMonSolActionPerformed

    private void txtForPagCreConFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtForPagCreConFocusLost
        if (!txtForPagCreCon.getText().equalsIgnoreCase(strForPagCreCon))
        {
            if (txtForPagCreCon.getText().equals(""))
            {
                txtForPagCreCon.setText("");
                txtCodForPagCreCon.setText("");
                objTblModAcc.removeAllRows();
            }
            else
            {
                mostrarVenConForPagCreCon(1);
                if (txtForPagCreCon.getText().equals(""))
                {
                    objTblModAcc.removeAllRows();
                }
            }
        }
        else            
            txtForPagCreCon.setText(strForPagCreCon);
    }//GEN-LAST:event_txtForPagCreConFocusLost

    private void txtForPagCreConFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtForPagCreConFocusGained
        strForPagCreCon=txtForPagCreCon.getText();
        txtForPagCreCon.selectAll();
    }//GEN-LAST:event_txtForPagCreConFocusGained

    private void txtForPagCreConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtForPagCreConActionPerformed
        txtForPagCreCon.transferFocus();
    }//GEN-LAST:event_txtForPagCreConActionPerformed

    private void butForPagCreConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butForPagCreConActionPerformed
        mostrarVenConForPagCreCon(0);
        if (txtForPagCreCon.getText().equals(""))
            txtForPagCreCon.requestFocus();
        else
            txtProAct.requestFocus();
    }//GEN-LAST:event_butForPagCreConActionPerformed

    private void txtMonAprFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMonAprFocusGained
        txtMonApr.selectAll();
    }//GEN-LAST:event_txtMonAprFocusGained

    private void txtMonSolFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMonSolFocusGained
        txtMonSol.selectAll();
    }//GEN-LAST:event_txtMonSolFocusGained

    private void txtMonAprFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMonAprFocusLost
        String strCon = txtMonApr.getText();
        if (IsNumeric(strCon))
        {
            
        }
        else
        {
            mostrarMsgInf("Â¡Â¡Â¡ POR FAVOR INGRESE DATOS NUMERICOS !!!");
            txtMonApr.setText("");
            txtMonApr.requestFocus();
        }
    }//GEN-LAST:event_txtMonAprFocusLost

    private void txtMonSolFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMonSolFocusLost
        String strCon = txtMonSol.getText();
        if(objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo())
        {            
            if (IsNumeric(strCon))
            {
                
            }
            else
            {
                mostrarMsgInf("Â¡Â¡Â¡ POR FAVOR INGRESE DATOS NUMERICOS !!!");
                txtMonSol.setText("");
                txtMonSol.requestFocus();
            }
        }
    }//GEN-LAST:event_txtMonSolFocusLost

    private void butForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butForPagActionPerformed
        mostrarVenConForPag(0);
    }//GEN-LAST:event_butForPagActionPerformed

    private void txtForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtForPagFocusLost
        if (!txtForPag.getText().equalsIgnoreCase(strForPag))
        {
            if (txtForPag.getText().equals(""))
            {
                txtForPag.setText("");
                objTblModAcc.removeAllRows();
            }
            else
            {
                mostrarVenConForPag(1);
                if (txtForPag.getText().equals(""))
                {
                    objTblModAcc.removeAllRows();
                }
            }
        }
        else            
            txtForPag.setText(strForPag);
    }//GEN-LAST:event_txtForPagFocusLost

    private void txtForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtForPagFocusGained
        strForPag=txtForPag.getText();
        txtForPag.selectAll();
    }//GEN-LAST:event_txtForPagFocusGained

    private void txtForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtForPagActionPerformed
        txtForPag.transferFocus();
    }//GEN-LAST:event_txtForPagActionPerformed

    private void tblRefBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRefBanMouseClicked
        //Abrir la opciï¿½n seleccionada al dar doble click.
        if (evt.getClickCount()==2)
        {
            abrirFrm();
        }
        
    }//GEN-LAST:event_tblRefBanMouseClicked

    private void tblRefBanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblRefBanKeyPressed
        //Abrir la opciï¿½n seleccionada al dar presionar ENTER.
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
            evt.consume();
            abrirFrm();
        }
        
    }//GEN-LAST:event_tblRefBanKeyPressed

    private void tblRefComMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRefComMouseClicked
        //Abrir la opciï¿½n seleccionada al dar doble click.
        if (evt.getClickCount()==2)
        {
            abrirFrm();
        }
    }//GEN-LAST:event_tblRefComMouseClicked

    private void tblRefComKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblRefComKeyPressed
        //Abrir la opciï¿½n seleccionada al dar presionar ENTER.
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
            evt.consume();
            abrirFrm();
        }
        
    }//GEN-LAST:event_tblRefComKeyPressed

    private void jChkPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChkPrvActionPerformed
        jChkCli.setSelected(false);
        jChkPrv.setSelected(true);
    }//GEN-LAST:event_jChkPrvActionPerformed

    private void jChkCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChkCliActionPerformed
        jChkCli.setSelected(true);
        jChkPrv.setSelected(false);
        
    }//GEN-LAST:event_jChkCliActionPerformed

    private void txtNomTipPerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTipPerFocusLost
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtNomTipPer.getText().equalsIgnoreCase(strNomTipPer))
        {
            if (txtNomTipPer.getText().equals(""))
            {
                txtCodTipPer.setText("");
                txtDesTipPer.setText("");
            }
            else
            {
            mostrarVenConTipPer(2);
            }
        }
        else
            txtNomTipPer.setText(strNomTipPer);
    }//GEN-LAST:event_txtNomTipPerFocusLost

    private void txtNomTipPerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTipPerFocusGained
        strNomTipPer=txtNomTipPer.getText();
        txtNomTipPer.selectAll();
    }//GEN-LAST:event_txtNomTipPerFocusGained

    private void txtNomTipPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTipPerActionPerformed
        txtNomTipPer.transferFocus();
    }//GEN-LAST:event_txtNomTipPerActionPerformed

    private void txtDesTipPerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesTipPerFocusLost
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtDesTipPer.getText().equalsIgnoreCase(strDesTipPer))
        {
            if (txtDesTipPer.getText().equals(""))
            {
                txtCodTipPer.setText("");
                txtNomTipPer.setText("");

            }
            else
            {
                mostrarVenConTipPer(1);
            }
        }
        else
            txtDesTipPer.setText(strDesTipPer);
    }//GEN-LAST:event_txtDesTipPerFocusLost

    private void txtDesTipPerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesTipPerFocusGained
        strDesTipPer=txtDesTipPer.getText();
        txtDesTipPer.selectAll();                     
    }//GEN-LAST:event_txtDesTipPerFocusGained

    private void txtDesTipPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesTipPerActionPerformed
        txtDesTipPer.transferFocus();
    }//GEN-LAST:event_txtDesTipPerActionPerformed

    private void butTipPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipPerActionPerformed
        mostrarVenConTipPer(0);
    }//GEN-LAST:event_butTipPerActionPerformed

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        mostrarVenConVen(0);
    }//GEN-LAST:event_butVenActionPerformed

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtNomVen.getText().equalsIgnoreCase(strNomVen))
        {
            if (txtNomVen.getText().equals(""))
            {
                txtCodVen.setText("");
                txtDesVen.setText("");
            }
            else
            {
                mostrarVenConVen(2);
            }
        }
        else
            txtNomVen.setText(strNomVen);
    }//GEN-LAST:event_txtNomVenFocusLost

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        strNomVen=txtNomVen.getText();
        txtNomVen.selectAll();
    }//GEN-LAST:event_txtNomVenFocusGained

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        txtNomVen.transferFocus();
    }//GEN-LAST:event_txtNomVenActionPerformed

    private void txtDesVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesVenFocusLost
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtDesVen.getText().equalsIgnoreCase(strVen))
        {
            if (txtDesVen.getText().equals(""))
            {
                txtCodVen.setText("");
                txtNomVen.setText("");

            }
            else
            {
                mostrarVenConVen(1);
            }
        }
        else
            txtDesVen.setText(strVen);
    }//GEN-LAST:event_txtDesVenFocusLost

    private void txtDesVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesVenFocusGained
        strVen=txtDesVen.getText();
        txtDesVen.selectAll();             
    }//GEN-LAST:event_txtDesVenFocusGained

    private void txtDesVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesVenActionPerformed
        txtDesVen.transferFocus();
    }//GEN-LAST:event_txtDesVenActionPerformed

    private void txtNomZonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomZonFocusLost
        if (!txtNomZon.getText().equalsIgnoreCase(strZon))
        {
            if (txtNomZon.getText().equals(""))
            {
                txtNomZon.setText("");
            }
            else
            {
                mostrarVenConZon(1);
                if (txtNomZon.getText().equals(""))
                {
                    objTblModAcc.removeAllRows();
                }
            }
        }
        else            
            txtNomZon.setText(strZon);
    }//GEN-LAST:event_txtNomZonFocusLost

    private void txtNomZonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomZonFocusGained
        strZon=txtCiu.getText();
        txtNomZon.selectAll();
    }//GEN-LAST:event_txtNomZonFocusGained

    private void txtNomZonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomZonActionPerformed
        txtNomZon.transferFocus();
    }//GEN-LAST:event_txtNomZonActionPerformed

    private void butZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butZonaActionPerformed
        mostrarVenConZon(0);
    }//GEN-LAST:event_butZonaActionPerformed

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        if (!txtNomCli.getText().equalsIgnoreCase(strDesLarCli))
        {
            if (txtNomCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtNomCli.setText("");
                objTblModAcc.removeAllRows();
            }
            else
            {
                mostrarVenConCli(2);
                if (txtCodCli.getText().equals(""))
                {
                    objTblModAcc.removeAllRows();
                }
                if(INTCODMNU != 1014)
                    mostrarDatCli();
            }
        }
        else
            txtNomCli.setText(strDesLarCli);
    }//GEN-LAST:event_txtNomCliFocusLost

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        strDesLarCli=txtNomCli.getText();
        txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        txtNomCli.transferFocus();
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCodCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtNomCli.setText("");
                objTblModAcc.removeAllRows();
                txtCodCli.requestFocus();
            }
            else
            {
                mostrarVenConCli(1);
                if(objTooBar.getEstado()=='n')
                {
                    if(!txtCodCli.getText().equals(""))
                    {
                        if(INTCODMNU != 1014)
                            mostrarDatCli();
                        
                        isDesCam();
                    }
                }
                if (txtCodCli.getText().equals(""))
                {
                    txtCodCli.requestFocus();
                }
            }
        }
        else
            txtCodCli.setText(strCodCli);
        
        
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void butProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butProActionPerformed
        mostrarVenConCli(0);
        if (txtCodCli.getText().equals(""))
            txtCodCli.requestFocus();
        else
            txtDir.requestFocus();
        
        if(INTCODMNU != 1014)
            mostrarDatCli();
    }//GEN-LAST:event_butProActionPerformed

    private void txtCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCiuFocusLost
        if (!txtCiu.getText().equalsIgnoreCase(strCiu))
        {
            if (txtCiu.getText().equals(""))
            {
                txtCiu.setText("");
                objTblModAcc.removeAllRows();
            }
            else
            {
                mostrarVenConCiu(1);
                if (txtCiu.getText().equals(""))
                {
                    objTblModAcc.removeAllRows();
                }
            }
        }
        else            
            txtCiu.setText(strCiu);
    }//GEN-LAST:event_txtCiuFocusLost

    private void txtCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCiuFocusGained
        strCiu=txtCiu.getText();
        txtCiu.selectAll();
    }//GEN-LAST:event_txtCiuFocusGained

    private void txtCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiuActionPerformed
        txtCiu.transferFocus();
    }//GEN-LAST:event_txtCiuActionPerformed

    private void butCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCiuActionPerformed
        mostrarVenConCiu(0);
    }//GEN-LAST:event_butCiuActionPerformed

    //Funcion para verificar si campo escrito es Numerico//
    public static boolean IsNumeric(String s)
    {
        for(int i=0; i<s.length(); i++)
        {
            if(s.charAt(i) < '0' || s.charAt(i) > '9')
            {
                return false;
            }
        }
        return true;
    }
    
   /**
     * Esta funciï¿½n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de bï¿½squeda determina si se debe hacer
     * una bï¿½squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se estï¿½ buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opciï¿½n que desea utilizar.
     * @param intTipBus El tipo de bï¿½squeda a realizar.
     * @return true: Si no se presentï¿½ ningï¿½n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCli(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.show();
                    if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Cï¿½digo".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
                    if (vcoCli.buscar("a1.tx_nom", txtNomCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtNomCli.setText(strDesLarCli);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    ///private boolean mostrarVenConCli(int intTipBus)
    private boolean mostrarVenConCiu(int intTipBus)
    {
        String strAli, strCam, strTipCliAux;
        Librerias.ZafConsulta.ZafConsulta objVenCon;
        boolean blnRes=true;
        try
        {
            strAli="Cï¿½digo Ciudad, Desc. Corta, Desc. Larga ";
            strCam="a1.co_ciu, a1.tx_descor, a1.tx_deslar ";
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_ciu, a1.tx_descor, a1.tx_deslar ";
            strSQL+=" FROM tbm_ciu as a1";
            strSQL+=" WHERE a1.st_reg <> 'E'";            
            
            objVenCon=new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this), strAli, strCam, strSQL, "", objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            objVenCon.setTitle("Listado de Ciudades ");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    objVenCon.show();
                    if (objVenCon.acepto())
                    {
                        txtCodCiu.setText(objVenCon.GetCamSel(1));
                        strCiu=objVenCon.GetCamSel(3);
                        txtCiu.setText(objVenCon.GetCamSel(3));

                    }
                    break;
                case 1: //Bï¿½squeda directa por "Cï¿½digo".
                    if (objVenCon.buscar("LOWER(a1.tx_deslar) LIKE '" + txtCiu.getText() + "'"))
                    {
                        txtCodCiu.setText(objVenCon.GetCamSel(1));
                        strCiu=objVenCon.GetCamSel(2);
                        txtCiu.setText(objVenCon.GetCamSel(3));
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtCiu.getText());
                        objVenCon.setSelectedTipBus(3);
                        objVenCon.setSelectedCamBus(0);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodCiu.setText(objVenCon.GetCamSel(1));
                            strCiu=objVenCon.GetCamSel(2);
                            txtCiu.setText(objVenCon.GetCamSel(3));
                        }
                        else
                        {
                            txtCiu.setText(strCiu);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean mostrarVenConZon(int intTipBus)
    {
        String strAli, strCam, strTipCliAux;
        Librerias.ZafConsulta.ZafConsulta objVenCon;
        boolean blnRes=true;
        try
        {
            strAli="Cï¿½digo Zona, Desc. Corta, Desc. Larga ";
            strCam="a1.co_reg, a1.tx_descor, a1.tx_deslar ";
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_reg, a1.tx_descor, a1.tx_deslar ";
            strSQL+=" FROM tbm_var as a1";
            strSQL+=" WHERE a1.st_reg <> 'E'";
            
            objVenCon=new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this), strAli, strCam, strSQL, "", objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            objVenCon.setTitle("Listado de Zonas ");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    objVenCon.show();
                    if (objVenCon.acepto())
                    {
                        txtCodZon.setText(objVenCon.GetCamSel(1));
                        strZon=objVenCon.GetCamSel(3);
                        txtNomZon.setText(objVenCon.GetCamSel(3));

                    }
                    break;
                case 1: //Bï¿½squeda directa por "Cï¿½digo".
                    if (objVenCon.buscar("LOWER(a1.tx_deslar) LIKE '" + txtCiu.getText() + "'"))
                    {
                        txtCodZon.setText(objVenCon.GetCamSel(1));
                        strZon=objVenCon.GetCamSel(2);
                        txtNomZon.setText(objVenCon.GetCamSel(3));
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtCiu.getText());
                        objVenCon.setSelectedTipBus(3);
                        objVenCon.setSelectedCamBus(0);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodZon.setText(objVenCon.GetCamSel(1));
                            strZon=objVenCon.GetCamSel(2);
                            txtNomZon.setText(objVenCon.GetCamSel(3));
                        }
                        else
                        {
                            txtNomZon.setText(strCiu);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean mostrarVenConVen(int intTipBus)
    {
        Librerias.ZafConsulta.ZafConsulta objVenCon;
        String strAli, strCam;
        boolean blnRes=true;
        int intCodUsr = objParSis.getCodigoUsuario();
        
        try
        {
            strAli="Cï¿½digo, Descripciï¿½n corta, Descripciï¿½n larga ";
            strCam="a1.co_usr, a1.tx_usr, a1.tx_nom";
            
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom ";
                strSQL+=" FROM tbm_usr AS a1";
            
            objVenCon=new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this), strAli, strCam, strSQL, "", objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            objVenCon.setTitle("Listado de Usuarios");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    objVenCon.show();
                    if (objVenCon.acepto())
                    {
                        txtCodVen.setText(objVenCon.GetCamSel(1));
                        txtDesVen.setText(objVenCon.GetCamSel(2));
                        txtNomVen.setText(objVenCon.GetCamSel(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=objVenCon.GetCamSel(4);
                        }
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Descripciï¿½n corta".
                    if (objVenCon.buscar("LOWER(a1.tx_usr) LIKE '" + txtDesVen.getText().toLowerCase() + "'"))
                    {
                        txtCodVen.setText(objVenCon.GetCamSel(1));
                        txtDesVen.setText(objVenCon.GetCamSel(2));
                        txtNomVen.setText(objVenCon.GetCamSel(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=objVenCon.GetCamSel(4);
                        }
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtDesVen.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(1);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodVen.setText(objVenCon.GetCamSel(1));
                            txtDesVen.setText(objVenCon.GetCamSel(2));
                            txtNomVen.setText(objVenCon.GetCamSel(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=objVenCon.GetCamSel(4);
                            }
                        }
                        else
                        {
                            txtDesVen.setText(strVen);
                        }
                    }
                    break;
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
                    if (objVenCon.buscar("LOWER(a1.tx_nom) LIKE '" + txtNomVen.getText().toLowerCase() + "'"))
                    {
                        txtCodVen.setText(objVenCon.GetCamSel(1));
                        txtDesVen.setText(objVenCon.GetCamSel(2));
                        txtNomVen.setText(objVenCon.GetCamSel(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=objVenCon.GetCamSel(4);
                        }
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtNomVen.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(2);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodVen.setText(objVenCon.GetCamSel(1));
                            txtDesVen.setText(objVenCon.GetCamSel(2));
                            txtNomVen.setText(objVenCon.GetCamSel(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=objVenCon.GetCamSel(4);
                            }
                        }
                        else
                        {
                            txtNomVen.setText(strNomVen);
                        }
                    }
                    break;
            }           
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean mostrarVenConTipPer(int intTipBus)
    {
        Librerias.ZafConsulta.ZafConsulta objVenCon;
        String strAli, strCam;
        boolean blnRes=true;
        int intCodUsr = objParSis.getCodigoUsuario();
        
        try
        {
            strAli="Cï¿½digo, Descripciï¿½n corta, Descripciï¿½n larga ";
            strCam="a1.co_tipper, a2.tx_tipper, a1.tx_deslar";
            
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipper, a1.tx_descor, a1.tx_deslar";
                strSQL+=" FROM tbm_tipper AS a1";                
                ///strSQL+=" WHERE a1.co_emp = " + objParSis.getCodigoEmpresa();
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE a1.co_emp=" + CODEMP;
                else
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                
            objVenCon=new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this), strAli, strCam, strSQL, "", objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            objVenCon.setTitle("Listado de Tipo de Persona");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    objVenCon.show();
                    if (objVenCon.acepto())
                    {
                        txtCodTipPer.setText(objVenCon.GetCamSel(1));
                        txtDesTipPer.setText(objVenCon.GetCamSel(2));
                        txtNomTipPer.setText(objVenCon.GetCamSel(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=objVenCon.GetCamSel(4);
                        }
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Descripciï¿½n corta".
                    if (objVenCon.buscar("LOWER(a1.tx_tipper) LIKE '" + txtDesTipPer.getText().toLowerCase() + "'"))
                    {
                        txtCodTipPer.setText(objVenCon.GetCamSel(1));
                        txtDesTipPer.setText(objVenCon.GetCamSel(2));
                        txtNomTipPer.setText(objVenCon.GetCamSel(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=objVenCon.GetCamSel(4);
                        }
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtDesVen.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(1);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodTipPer.setText(objVenCon.GetCamSel(1));
                            txtDesTipPer.setText(objVenCon.GetCamSel(2));
                            txtNomTipPer.setText(objVenCon.GetCamSel(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=objVenCon.GetCamSel(4);
                            }
                        }
                        else
                        {
                            txtDesTipPer.setText(strVen);
                        }
                    }
                    break;
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
                    if (objVenCon.buscar("LOWER(a1.tx_deslar) LIKE '" + txtNomTipPer.getText().toLowerCase() + "'"))
                    {
                        txtCodTipPer.setText(objVenCon.GetCamSel(1));
                        txtDesTipPer.setText(objVenCon.GetCamSel(2));
                        txtNomTipPer.setText(objVenCon.GetCamSel(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=objVenCon.GetCamSel(4);
                        }
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtNomVen.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(2);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodTipPer.setText(objVenCon.GetCamSel(1));
                            txtDesTipPer.setText(objVenCon.GetCamSel(2));
                            txtNomTipPer.setText(objVenCon.GetCamSel(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=objVenCon.GetCamSel(4);
                            }
                        }
                        else
                        {
                            txtNomTipPer.setText(strNomVen);
                        }
                    }
                    break;
            }           
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de bï¿½squeda determina si se debe hacer
     * una bï¿½squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se estï¿½ buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opciï¿½n que desea utilizar.
     * @param intTipBus El tipo de bï¿½squeda a realizar.
     * @return true: Si no se presentï¿½ ningï¿½n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConForPag(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoForPag.setCampoBusqueda(2);
                    vcoForPag.show();
                    if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE)
                    {
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtForPag.setText(vcoForPag.getValueAt(2));
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Cï¿½digo".
                    if (vcoForPag.buscar("a1.co_forpag", txtCodForPag.getText()))
                    {
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtForPag.setText(vcoForPag.getValueAt(2));
                    }
                    else
                    {
                        vcoForPag.setCampoBusqueda(0);
                        vcoForPag.setCriterio1(11);
                        vcoForPag.cargarDatos();
                        vcoForPag.show();
                        if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE)
                        {
                            txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtForPag.setText(vcoForPag.getValueAt(2));
                        }
                        else
                        {
                            txtCodForPag.setText(strCodForPag);
                        }
                    }
                    break;
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
                    if (vcoForPag.buscar("a1.tx_des", txtForPag.getText()))
                    {
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtForPag.setText(vcoForPag.getValueAt(2));
                    }
                    else
                    {
                        vcoForPag.setCampoBusqueda(2);
                        vcoForPag.setCriterio1(11);
                        vcoForPag.cargarDatos();
                        vcoForPag.show();
                        if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE)
                        {
                            txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtForPag.setText(vcoForPag.getValueAt(2));
                        }
                        else
                        {
                            txtForPag.setText(strForPag);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de bï¿½squeda determina si se debe hacer
     * una bï¿½squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se estï¿½ buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opciï¿½n que desea utilizar.
     * @param intTipBus El tipo de bï¿½squeda a realizar.
     * @return true: Si no se presentï¿½ ningï¿½n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConForPagCreCon(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoForPagCreCon.setCampoBusqueda(2);
                    vcoForPagCreCon.show();
                    if (vcoForPagCreCon.getSelectedButton()==vcoForPagCreCon.INT_BUT_ACE)
                    {
                        txtCodForPagCreCon.setText(vcoForPagCreCon.getValueAt(1));
                        txtForPagCreCon.setText(vcoForPagCreCon.getValueAt(2));
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Cï¿½digo".
                    if (vcoForPagCreCon.buscar("a1.co_forpag", txtCodForPagCreCon.getText()))
                    {
                        txtCodForPagCreCon.setText(vcoForPagCreCon.getValueAt(1));
                        txtForPagCreCon.setText(vcoForPagCreCon.getValueAt(2));
                    }
                    else
                    {
                        vcoForPagCreCon.setCampoBusqueda(0);
                        vcoForPagCreCon.setCriterio1(11);
                        vcoForPagCreCon.cargarDatos();
                        vcoForPagCreCon.show();
                        if (vcoForPagCreCon.getSelectedButton()==vcoForPagCreCon.INT_BUT_ACE)
                        {
                            txtCodForPagCreCon.setText(vcoForPagCreCon.getValueAt(1));
                            txtForPagCreCon.setText(vcoForPagCreCon.getValueAt(2));
                        }
                        else
                        {
                            txtCodForPagCreCon.setText(strCodForPagCreCon);
                        }
                    }
                    break;
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
                    if (vcoForPagCreCon.buscar("a1.tx_des", txtNomCli.getText()))
                    {
                        txtCodForPagCreCon.setText(vcoForPagCreCon.getValueAt(1));
                        txtForPagCreCon.setText(vcoForPagCreCon.getValueAt(2));
                    }
                    else
                    {
                        vcoForPagCreCon.setCampoBusqueda(2);
                        vcoForPagCreCon.setCriterio1(11);
                        vcoForPagCreCon.cargarDatos();
                        vcoForPagCreCon.show();
                        if (vcoForPagCreCon.getSelectedButton()==vcoForPagCreCon.INT_BUT_ACE)
                        {
                            txtCodForPagCreCon.setText(vcoForPagCreCon.getValueAt(1));
                            txtForPagCreCon.setText(vcoForPagCreCon.getValueAt(2));
                        }
                        else
                        {
                            txtForPagCreCon.setText(strForPagCreCon);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarDatCli()
    {
        int intPosRel, intCodEmp;
        boolean blnRes=true;
        String datcli = "";
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            conDatCli=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conDatCli!=null)
            {
                stmDatCli=conDatCli.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_cli, a1.tx_nom as NomCli, a2.co_tipper, a1.tx_tipper, a2.tx_deslar,  a1.st_cli, a1.st_prv, a1.tx_tipide, ";
                strSQL+=" a1.tx_ide, a1.tx_dir, a1.tx_refubi, a1.tx_tel1, a1.tx_tel2, a1.tx_tel3, a1.tx_tel,  a1.tx_fax, a1.tx_cas, a1.tx_corele, a1.tx_dirweb, ";
                strSQL+=" a1.co_ciu, a3.tx_deslar as NomCiu, a1.co_zon, a4.tx_deslar as NomZon, a1.co_ven, a5.tx_usr,  a5.tx_nom as NomVen, ";
                strSQL+=" a1.tx_obscxc, a1.tx_idepro, a1.tx_nompro, a1.tx_dirpro, a1.tx_telpro, a1.tx_nacpro, a1.fe_conemp, a1.tx_tipactemp, ";
                strSQL+=" a1.tx_obspro,  a1.tx_percon, a1.tx_telcon, a1.tx_corelecon, a1.nd_moncre, a1.ne_diagra, a1.nd_maxdes, a1.tx_obs1, a1.tx_obs2,";
                strSQL+=" a1.tx_obsinfburcre, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod, a1.co_tipper,  a1.nd_maruti, a1.st_regrep, a1.st_cieretpen ";
                strSQL+=" FROM tbm_cli AS a1 ";
                strSQL+=" INNER JOIN tbm_tipper AS a2 ON (a1.co_emp=a2.co_emp and a1.co_tipper=a2.co_tipper)";
                strSQL+=" LEFT OUTER JOIN tbm_ciu AS a3 ON (a1.co_ciu=a3.co_ciu)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a4 ON (a1.co_zon=a4.co_reg)";
                strSQL+=" LEFT OUTER JOIN tbm_usr AS a5 ON (a1.co_ven=a5.co_usr)";
                ///strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE a1.co_emp=" + CODEMP;
                else
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                
                datcli = txtCodCli.getText();
                if(datcli.equals(""))
                    txtCodCli.requestFocus();
                else
                    strSQL+=" AND a1.co_cli = " + datcli + "";
                
                strSQL+=" ORDER BY a1.co_cli ";

                rstDatCli=stmDatCli.executeQuery(strSQL);
                
                if (rstDatCli.next())
                {
                    txtCodTipPer.setText(((rstDatCli.getString("co_tipper")==null)?"":rstDatCli.getString("co_tipper")));
                    txtDesTipPer.setText(((rstDatCli.getString("tx_tipper")==null)?"":rstDatCli.getString("tx_tipper")));
                    txtNomTipPer.setText(((rstDatCli.getString("tx_deslar")==null)?"":rstDatCli.getString("tx_deslar")));
                    
                    /////Para saber el estado del cliente////
                    String strCli= rstDatCli.getString("st_cli");
                    if(strCli.equals("S"))
                    {
                        jChkCli.setSelected(true);
                    }
                    else
                    {
                        jChkCli.setSelected(false);
                    }
                    
                    /////Para saber el estado del cliente////
                    String strPrv= rstDatCli.getString("st_prv");
                    if(strPrv.equals("S"))
                    {
                        jChkPrv.setSelected(true);
                    }
                    else
                    {
                        jChkPrv.setSelected(false);
                    }
                    
                    ////Para saber el tipo de Identificacion de la persona///
                    String strIde= rstDatCli.getString("tx_tipide");
                    if(strIde.equals("C"))
                    {
                            jCbxTipIde.setSelectedIndex(0);
                    }
                    if(strIde.equals("R"))
                    {
                            jCbxTipIde.setSelectedIndex(1);
                    }

                    txtIde.setText(((rstDatCli.getString("tx_ide")==null)?"":rstDatCli.getString("tx_ide")));
                    txtDir.setText(((rstDatCli.getString("tx_dir")==null)?"":rstDatCli.getString("tx_dir")));
                    txtRefUbi.setText(((rstDatCli.getString("tx_refubi")==null)?"":rstDatCli.getString("tx_refubi")));
                    txtTelCon1.setText(((rstDatCli.getString("tx_tel1")==null)?"":rstDatCli.getString("tx_tel1")));
                    txtTelCon2.setText(((rstDatCli.getString("tx_tel2")==null)?"":rstDatCli.getString("tx_tel2")));
                    txtTelCon3.setText(((rstDatCli.getString("tx_tel3")==null)?"":rstDatCli.getString("tx_tel3")));
                    txtTelf.setText(((rstDatCli.getString("tx_tel")==null)?"":rstDatCli.getString("tx_tel")));
                    txtFax.setText(((rstDatCli.getString("tx_fax")==null)?"":rstDatCli.getString("tx_fax")));
                    txtCasilla.setText(((rstDatCli.getString("tx_cas")==null)?"":rstDatCli.getString("tx_cas")));
                    txtEmail.setText(((rstDatCli.getString("tx_corele")==null)?"":rstDatCli.getString("tx_corele")));
                    txtWeb.setText(((rstDatCli.getString("tx_dirweb")==null)?"":rstDatCli.getString("tx_dirweb")));
                    txtCodCiu.setText(((rstDatCli.getString("co_ciu")==null)?"":rstDatCli.getString("co_ciu")));
                    txtCiu.setText(((rstDatCli.getString("NomCiu")==null)?"":rstDatCli.getString("NomCiu")));
                    txtCodZon.setText(((rstDatCli.getString("co_zon")==null)?"":rstDatCli.getString("co_zon")));
                    txtNomZon.setText(((rstDatCli.getString("NomZon")==null)?"":rstDatCli.getString("NomZon")));
                    txtCodVen.setText(((rstDatCli.getString("co_ven")==null)?"":rstDatCli.getString("co_ven")));
                    txtDesVen.setText(((rstDatCli.getString("tx_usr")==null)?"":rstDatCli.getString("tx_usr")));
                    txtNomVen.setText(((rstDatCli.getString("NomVen")==null)?"":rstDatCli.getString("NomVen")));
                    txaObsCxC.setText(((rstDatCli.getString("tx_obscxc")==null)?"":rstDatCli.getString("tx_obscxc")));
                    txaObsInfBurCre.setText(((rstDatCli.getString("tx_obsinfburcre")==null)?"":rstDatCli.getString("tx_obsinfburcre")));
                    
                    txtCedPasPro.setText(((rstDatCli.getString("tx_idepro")==null)?"":rstDatCli.getString("tx_idepro")));
                    txtNomPro.setText(((rstDatCli.getString("tx_nompro")==null)?"":rstDatCli.getString("tx_nompro")));
                    txtDomPro.setText(((rstDatCli.getString("tx_dirpro")==null)?"":rstDatCli.getString("tx_dirpro")));
                    txtTelPro.setText(((rstDatCli.getString("tx_telpro")==null)?"":rstDatCli.getString("tx_telpro")));
                    txtNacPro.setText(((rstDatCli.getString("tx_nacpro")==null)?"":rstDatCli.getString("tx_nacpro")));
                    dtpFecConEmp.setText(objUti.formatearFecha(rstDatCli.getDate("fe_conEmp"),"dd/MM/yyyy"));
                    txtTipActEmp.setText(((rstDatCli.getString("tx_tipactemp")==null)?"":rstDatCli.getString("tx_tipactemp")));
                    txaObs1.setText(((rstDatCli.getString("tx_obspro")==null)?"":rstDatCli.getString("tx_obspro")));
                    
                    ///cargarDetProEmp();
                    
                    try
                    {
                        conDetCli=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                        if (conDetCli!=null)
                        {
                            stmDetCli=conDetCli.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            strSQL="";
                            strSQL+="SELECT a1.co_emp, a1.co_cli, a1.tx_nom ";
                            strSQL+=" FROM tbm_accEmpCli AS a1";
                            strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                            ///strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                            if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                                strSQL+=" WHERE a1.co_emp=" + CODEMP;
                            else
                                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                            
                            ///strSQL+=" AND a1.co_cli = " + txtCodCli.getText() + "";
                            
                            if(datcli.equals(""))
                                txtCodCli.requestFocus();
                            else
                                strSQL+=" AND a1.co_cli = " + datcli + "";
                            
                            strSQL+=" AND a2.st_reg <> 'E'";
                            rstDetCli=stmDetCli.executeQuery(strSQL);
                            while (rstDetCli.next())
                            {
                                vecCabAcc=new Vector();
                                vecCabAcc.add(INT_TBL_DAT_LIN, "");
                                vecCabAcc.add(INT_TBL_DAT_NOM_ACC, rstDetCli.getString("tx_nom"));
                                vecCabAcc.add(INT_TBL_DAT_COD_CLI, rstDetCli.getString("co_cli"));
                                vecDatAcc.add(vecCabAcc);
                            }
                        }
                        ///limpiar y cerrar el resulset///
                        rstDetCli.close();
                        stmDetCli.close();
                        conDetCli.close();
                        rstDetCli=null;
                        stmDetCli=null;
                        conDetCli=null;

                        //Asignar vectores al modelo.
                        objTblModAcc.setData(vecDatAcc);
                        tblAcc.setModel(objTblModAcc);
                        ///vecDatAcc.clear();
                    }
                    catch (java.sql.SQLException e)
                    {
                        blnRes=false;
                        objUti.mostrarMsgErr_F1(this, e);
                    }
                    catch (Exception e)
                    {
                        blnRes=false;
                        objUti.mostrarMsgErr_F1(this, e);
                    }
                }
                else
                {
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rstDatCli.close();
            stmDatCli.close();
            conDatCli.close();
            rstDatCli=null;
            stmDatCli=null;
            conDatCli=null;       
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    
    
    /** Cerrar la aplicaciï¿½n. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
       String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="ï¿½Estï¿½ seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexiï¿½n si estï¿½ abierta.
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e)
        {
            dispose();
        }                        
    }//GEN-LAST:event_exitForm
    
    /** Cerrar la aplicaciï¿½n. */
    private void exitForm() {
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrNatCta;
    private javax.swing.ButtonGroup bgrTipCta;
    private javax.swing.JButton butCiu;
    private javax.swing.JButton butForPag;
    private javax.swing.JButton butForPagCreCon;
    private javax.swing.JButton butPro;
    private javax.swing.JButton butTipPer;
    private javax.swing.JButton butVen;
    private javax.swing.JButton butZona;
    private javax.swing.JComboBox jCbxTipIde;
    private javax.swing.JCheckBox jChkCli;
    private javax.swing.JCheckBox jChkPrv;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAge;
    private javax.swing.JLabel lblCasilla;
    private javax.swing.JLabel lblCedPasPro;
    private javax.swing.JLabel lblCiudad;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblCodHisSolCre;
    private javax.swing.JLabel lblDir;
    private javax.swing.JLabel lblDomPro;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFax;
    private javax.swing.JLabel lblFecConEmp;
    private javax.swing.JLabel lblFecProAct;
    private javax.swing.JLabel lblFecRef;
    private javax.swing.JLabel lblFecUltAct;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblForPagCreCon;
    private javax.swing.JLabel lblInf;
    private javax.swing.JLabel lblMonApr;
    private javax.swing.JLabel lblMonCreDir;
    private javax.swing.JLabel lblMonSol;
    private javax.swing.JLabel lblNacPro;
    private javax.swing.JLabel lblNomPro;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblObs3;
    private javax.swing.JLabel lblObsSolCre1;
    private javax.swing.JLabel lblObsSolCre2;
    private javax.swing.JLabel lblProAct;
    private javax.swing.JLabel lblRefUbi;
    private javax.swing.JLabel lblSolCre;
    private javax.swing.JLabel lblTelCon1;
    private javax.swing.JLabel lblTelCon2;
    private javax.swing.JLabel lblTelCon3;
    private javax.swing.JLabel lblTelPro;
    private javax.swing.JLabel lblTelf;
    private javax.swing.JLabel lblTipActEmp;
    private javax.swing.JLabel lblTipIde;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblZona;
    private javax.swing.JPanel panAcc;
    private javax.swing.JPanel panActDat;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCabDat;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panCreCon;
    private javax.swing.JPanel panCreSol;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panDatGen;
    private javax.swing.JPanel panDatcre;
    private javax.swing.JPanel panDocDig;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenProEmp;
    private javax.swing.JPanel panGenSolCre;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panProEmp;
    private javax.swing.JPanel panRefBan;
    private javax.swing.JPanel panRefCom;
    private javax.swing.JScrollPane spnAcc;
    private javax.swing.JScrollPane spnCon;
    private javax.swing.JScrollPane spnDatcre;
    private javax.swing.JScrollPane spnDocDig;
    private javax.swing.JScrollPane spnObs;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JScrollPane spnObs3;
    private javax.swing.JScrollPane spnObsSolCre1;
    private javax.swing.JScrollPane spnObsSolCre2;
    private javax.swing.JScrollPane spnRefBan;
    private javax.swing.JScrollPane spnRefCom;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblAcc;
    private javax.swing.JTable tblCon;
    private javax.swing.JTable tblDocDig;
    private javax.swing.JTable tblObs;
    private javax.swing.JTable tblRefBan;
    private javax.swing.JTable tblRefCom;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObsCxC;
    private javax.swing.JTextArea txaObsInfBurCre;
    private javax.swing.JTextArea txaObsSolCre1;
    private javax.swing.JTextArea txaObsSolCre2;
    private javax.swing.JTextField txtCasilla;
    private javax.swing.JTextField txtCedPasPro;
    private javax.swing.JTextField txtCiu;
    private javax.swing.JTextField txtCodCiu;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodForPag;
    private javax.swing.JTextField txtCodForPagCreCon;
    private javax.swing.JTextField txtCodHisSolCre;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtCodTipPer;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtCodZon;
    private javax.swing.JTextField txtDesTipPer;
    private javax.swing.JTextField txtDesVen;
    private javax.swing.JTextField txtDir;
    private javax.swing.JTextField txtDomPro;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFax;
    private javax.swing.JTextField txtForPag;
    private javax.swing.JTextField txtForPagCreCon;
    private javax.swing.JTextField txtIde;
    private javax.swing.JTextField txtMonApr;
    private javax.swing.JTextField txtMonSol;
    private javax.swing.JTextField txtNacPro;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomPro;
    private javax.swing.JTextField txtNomTipPer;
    private javax.swing.JTextField txtNomVen;
    private javax.swing.JTextField txtNomZon;
    private javax.swing.JTextField txtNumCta;
    private javax.swing.JTextField txtNumPro;
    private javax.swing.JTextField txtProAct;
    private javax.swing.JTextField txtRefUbi;
    private javax.swing.JTextField txtSolCre;
    private javax.swing.JTextField txtTelCon1;
    private javax.swing.JTextField txtTelCon2;
    private javax.swing.JTextField txtTelCon3;
    private javax.swing.JTextField txtTelPro;
    private javax.swing.JTextField txtTelf;
    private javax.swing.JTextField txtTipActEmp;
    private javax.swing.JTextField txtWeb;
    // End of variables declaration//GEN-END:variables

    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifï¿½quelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }   
    
    private boolean consultarReg()
    {
        boolean blnRes=true;        
        
        try
        {
            if(INTCODMNU == 1014)
            {
                if (consultarCabRegHis())
                {
                  
                }
            }
            else
            {
                if (consultarCabReg())
                {
                  
                }
            }
            blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    

    /**
     * Esta funciï¿½n permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarCabReg()
    {
        int intCodEmp, intCodLoc, intultdoc;
        boolean blnRes=true;
        String codmodprg="";
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            intultdoc = ultCodDoc();

            
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_sol, a1.fe_sol, a1.co_cli, a1.st_reg as EstSol, a2.tx_nom as NomCli, a3.co_tipper, a2.tx_tipper, a3.tx_deslar, ";
                strSQL+=" a2.st_cli, a2.st_prv, a2.tx_tipide, a2.tx_ide, a2.tx_dir, a2.tx_refubi, a2.tx_tel1, a2.tx_tel2, a2.tx_tel3, a2.tx_tel, ";
                strSQL+=" a2.tx_fax, a2.tx_cas, a2.tx_corele, a2.tx_dirweb, a2.co_ciu, a4.tx_deslar, a2.co_zon, a5.tx_deslar, a2.co_ven, a6.tx_usr, ";
                strSQL+=" a6.tx_nom, a2.tx_obscxc, a2.tx_obsinfburcre, a2.tx_idepro, a2.tx_nompro, a2.tx_dirpro, a2.tx_telpro, a2.tx_nacpro, a2.fe_conemp, a2.tx_tipactemp, a2.tx_obspro, ";
                strSQL+=" a2.tx_percon, a2.tx_telcon, a2.tx_corelecon, a2.nd_moncre, a2.ne_diagra, a2.nd_maxdes, a2.tx_obs1, a2.tx_obs2, a2.st_reg, a2.fe_ing, a2.fe_ultmod, a2.co_usring,";
                strSQL+=" a2.co_usrmod, a2.co_tipper,  a2.nd_maruti, a2.st_regrep, a2.st_cieretpen, a2.fe_ultactdat, a2.fe_proactdat, ";
                strSQL+=" a1.co_forpag as CodForPag, a1.nd_moncre as Moncre, a1.tx_obs1, a1.tx_obs2 ";
                strSQL+=" FROM tbm_solcre AS a1 ";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                strSQL+=" LEFT OUTER JOIN tbm_tipper AS a3 ON (a2.co_emp=a3.co_emp and a2.co_tipper=a3.co_tipper)";
                strSQL+=" LEFT OUTER JOIN tbm_ciu AS a4 ON (a2.co_ciu=a4.co_ciu)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_zon=a5.co_reg)";
                strSQL+=" LEFT OUTER JOIN tbm_usr AS a6 ON (a2.co_ven=a6.co_usr)";
                strSQL+=" LEFT  JOIN tbm_obscli AS a7 ON (a2.co_emp=a7.co_emp AND a2.co_cli=a7.co_cli)";
                
//                if(CODEMP==0)
//                    strSQL+=" WHERE a2.co_emp = " + intCodEmp + "";
//                else
//                    strSQL+=" WHERE a2.co_emp = " + CODEMP + "";

                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" WHERE a2.co_emp=" + CODEMP;
                    else
                        strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                }
                else
                {
                    if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" WHERE a2.co_emp=" + CODEMP;
                    else
                    {
                        
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a8.co_loc, a1.co_sol, a1.fe_sol, a1.co_cli, a1.st_reg as EstSol, a2.tx_nom as NomCli, a3.co_tipper, a2.tx_tipper, a3.tx_deslar, ";
                        strSQL+=" a2.st_cli, a2.st_prv, a2.tx_tipide, a2.tx_ide, a2.tx_dir, a2.tx_refubi, a2.tx_tel1, a2.tx_tel2, a2.tx_tel3, a2.tx_tel, ";
                        strSQL+=" a2.tx_fax, a2.tx_cas, a2.tx_corele, a2.tx_dirweb, a2.co_ciu, a4.tx_deslar, a2.co_zon, a5.tx_deslar, a2.co_ven, a6.tx_usr, ";
                        strSQL+=" a6.tx_nom, a2.tx_obscxc, a2.tx_obsinfburcre, a2.tx_idepro, a2.tx_nompro, a2.tx_dirpro, a2.tx_telpro, a2.tx_nacpro, a2.fe_conemp, a2.tx_tipactemp, a2.tx_obspro, ";
                        strSQL+=" a2.tx_percon, a2.tx_telcon, a2.tx_corelecon, a2.nd_moncre, a2.ne_diagra, a2.nd_maxdes, a2.tx_obs1, a2.tx_obs2, a2.st_reg, a2.fe_ing, a2.fe_ultmod, a2.co_usring,";
                        strSQL+=" a2.co_usrmod, a2.co_tipper,  a2.nd_maruti, a2.st_regrep, a2.st_cieretpen, a2.fe_ultactdat, a2.fe_proactdat, ";
                        strSQL+=" a1.co_forpag as CodForPag, a1.nd_moncre as Moncre, a1.tx_obs1, a1.tx_obs2 ";
                        strSQL+=" FROM tbm_solcre AS a1 ";
                        strSQL+=" LEFT OUTER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                        strSQL+=" LEFT OUTER JOIN tbm_tipper AS a3 ON (a2.co_emp=a3.co_emp and a2.co_tipper=a3.co_tipper)";
                        strSQL+=" LEFT OUTER JOIN tbm_ciu AS a4 ON (a2.co_ciu=a4.co_ciu)";
                        strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_zon=a5.co_reg)";
                        strSQL+=" LEFT OUTER JOIN tbm_usr AS a6 ON (a2.co_ven=a6.co_usr)";
                        strSQL+=" LEFT OUTER JOIN tbm_obscli AS a7 ON (a2.co_emp=a7.co_emp AND a2.co_cli=a7.co_cli)";
                        strSQL+=" LEFT OUTER JOIN tbr_cliloc AS a8 ON (a2.co_emp=a8.co_emp and a2.co_cli=a8.co_cli)";
                        strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a8.co_loc=" + objParSis.getCodigoLocal();
                    }
                }


                strAux=txtCodCli.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_cli LIKE '" + strAux.replaceAll("'", "''") + "'";

                strAux=txtSolCre.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_sol LIKE '" + strAux.replaceAll("'", "''") + "'";

                strSQL+=" AND a1.st_reg <> 'E'";
                strSQL+=" ORDER BY a1. co_sol";
                rstCab=stmCab.executeQuery(strSQL);
                
                if (rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                    strSQLCon=strSQL;
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ningï¿½n registro que cumpla el criterio de bï¿½squeda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
            }
            
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    

    /**
     * Esta funciï¿½n permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            if (cargarCabReg())
            {
                if(cargarDatProEmp())
                {
                    if(cargarDetProEmp())
                    {
                        if(cargarDetCon())
                        {
                            if(cargarDatObs())
                            {
                                if(cargarDetRefCom())
                                {
                                    if(cargarDetRefBan())
                                    {
                                        if(cargarDetDocDig())
                                        {
                                            
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
                            else
                                blnRes=false;
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
            else
                blnRes=false;
            
            blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg()
    {
        int intPosRel, intCodEmp;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_sol, a1.fe_sol, a1.co_cli, a1.st_reg as EstSol, a2.tx_nom as NomCli, a3.co_tipper, a2.tx_tipper, a3.tx_deslar, ";
                strSQL+=" a2.st_cli, a2.st_prv, a2.tx_tipide, a2.tx_ide, a2.tx_dir, a2.tx_refubi, a2.tx_tel1, a2.tx_tel2, a2.tx_tel3, a2.tx_tel, a2.co_forpag as CodForPagCon, ";
                strSQL+=" a2.tx_fax, a2.tx_cas, a2.tx_corele, a2.tx_dirweb, a2.co_ciu, a4.tx_deslar as NomCiu, a2.co_zon, a5.tx_deslar as NomZon, a2.co_ven, a6.tx_usr, ";
                strSQL+=" a6.tx_nom as NomVen, a2.tx_obscxc, a2.tx_obsinfburcre, a2.tx_idepro, a2.tx_nompro, a2.tx_dirpro, a2.tx_telpro, a2.tx_nacpro, a2.fe_conemp, a2.tx_tipactemp, a2.tx_obspro, ";
                strSQL+=" a2.tx_percon, a2.tx_telcon, a2.tx_corelecon, round(a2.nd_moncre,0) as MonCreCon, a2.ne_diagra, a2.nd_maxdes, a2.tx_obs1, a2.tx_obs2, a2.st_reg, a2.fe_ing, a2.fe_ultmod, a2.co_usring, ";
                strSQL+=" a2.co_usrmod, a2.co_tipper,  a2.nd_maruti, a2.st_regrep, a2.st_cieretpen, a1.co_forpag as CodForPagSol, round(a1.nd_moncre,0) as MonCreSol, a7.tx_des as DesForPagSol, a8.tx_des as DesForPagCon, ";  /// , a7.tx_des as DesForPag
                strSQL+=" a2.fe_ultactdat, a2.fe_proactdat, a1.tx_obs1 as ObsSolCre1, a1.tx_obs2 as ObsSolCre2";
                strSQL+=" FROM tbm_solcre AS a1 ";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                strSQL+=" LEFT OUTER JOIN tbm_tipper AS a3 ON (a2.co_emp=a3.co_emp and a2.co_tipper=a3.co_tipper)";
                strSQL+=" LEFT OUTER JOIN tbm_ciu AS a4 ON (a2.co_ciu=a4.co_ciu)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_zon=a5.co_reg)";
                strSQL+=" LEFT OUTER JOIN tbm_usr AS a6 ON (a2.co_ven=a6.co_usr)";
                strSQL+=" LEFT OUTER JOIN tbm_cabforpag AS a7 ON (a1.co_emp=a7.co_emp and a1.co_forpag=a7.co_forpag)";
                strSQL+=" LEFT OUTER JOIN tbm_cabforpag AS a8 ON (a2.co_emp=a8.co_emp and a2.co_forpag=a8.co_forpag)";
                                
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL+=" WHERE a1.co_emp = " + rstCab.getString("co_emp") + "";
                    strSQL+=" AND a1.co_sol = " + rstCab.getString("co_sol") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_sol ";
                }
                else
                {
                    strSQL+=" INNER JOIN tbr_cliloc AS a9 ON (a2.co_emp=a9.co_emp and a2.co_cli=a9.co_cli)";
                    strSQL+=" WHERE a1.co_emp = " + rstCab.getString("co_emp") + "";
                    strSQL+=" AND a9.co_loc = " + rstCab.getString("co_loc") + "";
                    strSQL+=" AND a1.co_sol = " + rstCab.getString("co_sol") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_sol ";
                }
                
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {                   
                    txtSolCre.setText(((rst.getString("co_sol")==null)?"":rst.getString("co_sol")));
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_sol"),"dd/MM/yyyy"));
                    txtCodCli.setText(((rst.getString("co_cli")==null)?"":rst.getString("co_cli")));
                    txtNomCli.setText(((rst.getString("NomCli")==null)?"":rst.getString("NomCli")));
                    txtCodTipPer.setText(((rst.getString("co_tipper")==null)?"":rst.getString("co_tipper")));
                    txtDesTipPer.setText(((rst.getString("tx_tipper")==null)?"":rst.getString("tx_tipper")));
                    txtNomTipPer.setText(((rst.getString("tx_deslar")==null)?"":rst.getString("tx_deslar")));
                    
                    /////Para saber el estado del cliente////
                    String strCli= rst.getString("st_cli");
                    if(strCli.equals("S"))
                    {
                        jChkCli.setSelected(true);
                    }
                    else
                    {
                        jChkCli.setSelected(false);
                    }
                    
                    /////Para saber el estado del cliente////
                    String strPrv= rst.getString("st_prv");
                    if(strPrv.equals("S"))
                    {
                        jChkPrv.setSelected(true);
                    }
                    else
                    {
                        jChkPrv.setSelected(false);
                    }
                    
                    ////Para saber el tipo de Identificacion de la persona///
                    String strIde= rst.getString("tx_tipide");
                    if(strIde.equals("C"))
                    {
                            jCbxTipIde.setSelectedIndex(0);
                    }
                    if(strIde.equals("R"))
                    {
                            jCbxTipIde.setSelectedIndex(1);
                    }

                    txtIde.setText(((rst.getString("tx_ide")==null)?"":rst.getString("tx_ide")));
                    txtDir.setText(((rst.getString("tx_dir")==null)?"":rst.getString("tx_dir")));
                    txtRefUbi.setText(((rst.getString("tx_refubi")==null)?"":rst.getString("tx_refubi")));
                    txtTelCon1.setText(((rst.getString("tx_tel1")==null)?"":rst.getString("tx_tel1")));
                    txtTelCon2.setText(((rst.getString("tx_tel2")==null)?"":rst.getString("tx_tel2")));
                    txtTelCon3.setText(((rst.getString("tx_tel3")==null)?"":rst.getString("tx_tel3")));
                    txtTelf.setText(((rst.getString("tx_tel")==null)?"":rst.getString("tx_tel")));
                    txtFax.setText(((rst.getString("tx_fax")==null)?"":rst.getString("tx_fax")));
                    txtCasilla.setText(((rst.getString("tx_cas")==null)?"":rst.getString("tx_cas")));
                    txtEmail.setText(((rst.getString("tx_corele")==null)?"":rst.getString("tx_corele")));
                    txtWeb.setText(((rst.getString("tx_dirweb")==null)?"":rst.getString("tx_dirweb")));
                    txtCodCiu.setText(((rst.getString("co_ciu")==null)?"":rst.getString("co_ciu")));
                    txtCiu.setText(((rst.getString("NomCiu")==null)?"":rst.getString("NomCiu")));
                    txtCodZon.setText(((rst.getString("co_zon")==null)?"":rst.getString("co_zon")));
                    txtNomZon.setText(((rst.getString("NomZon")==null)?"":rst.getString("NomZon")));
                    txtCodVen.setText(((rst.getString("co_ven")==null)?"":rst.getString("co_ven")));
                    txtDesVen.setText(((rst.getString("tx_usr")==null)?"":rst.getString("tx_usr")));
                    txtNomVen.setText(((rst.getString("NomVen")==null)?"":rst.getString("NomVen")));
                    txaObsCxC.setText(((rst.getString("tx_obscxc")==null)?"":rst.getString("tx_obscxc")));
                    txaObsInfBurCre.setText(((rst.getString("tx_obsinfburcre")==null)?"":rst.getString("tx_obsinfburcre")));
                    
                    /////cargar datos de credito Solicitado/////
                    txtMonSol.setText(((rst.getString("MonCreSol")==null)?"":rst.getString("MonCreSol")));
                    txtCodForPag.setText(((rst.getString("CodForPagSol")==null)?"":rst.getString("CodForPagSol")));
                    txtForPag.setText(((rst.getString("DesForPagSol")==null)?"":rst.getString("DesForPagSol")));
                    dtpFecUltAct.setText(objUti.formatearFecha(rst.getDate("fe_ultactdat"),"dd/MM/yyyy"));
                    
                    /////cargar datos de credito Concedido/////
                    txtMonApr.setText(((rst.getString("MonCreCon")==null)?"":rst.getString("MonCreCon")));
                    txtCodForPagCreCon.setText(((rst.getString("CodForPagCon")==null)?"":rst.getString("CodForPagCon")));
                    txtForPagCreCon.setText(((rst.getString("DesForPagCon")==null)?"":rst.getString("DesForPagCon")));
                    dtpFecProAct.setText(objUti.formatearFecha(rst.getDate("fe_proactdat"),"dd/MM/yyyy"));
                    
                    ////cargar datos de Observacion 1 y 2 desde El panel de Control de Solicitud de Credito////
                    txaObsSolCre1.setText(((rst.getString("ObsSolCre1")==null)?"":rst.getString("ObsSolCre1")));
                    txaObsSolCre2.setText(((rst.getString("ObsSolCre2")==null)?"":rst.getString("ObsSolCre2")));
                    
                    //Mostrar el estado del registro.
                    strAux=rstCab.getString("EstSol");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I") )
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                    
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            //Mostrar la posiciï¿½n relativa del registro.
            intPosRel=rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);            
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    //////// CARGA LOS DATOS DEL PROPIETARIO DE LA EMPRESA//////////////
    private boolean cargarDatProEmp()
    {
        int intPosRel, intCodEmp;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_sol, a1.fe_sol, a1.co_cli, a1.st_reg as EstSol, a2.tx_nom as NomCli, a3.co_tipper, a2.tx_tipper, a3.tx_deslar, ";
                strSQL+=" a2.st_cli, a2.st_prv, a2.tx_tipide, a2.tx_ide, a2.tx_dir, a2.tx_refubi, a2.tx_tel1, a2.tx_tel2, a2.tx_tel3, a2.tx_tel, ";
                strSQL+=" a2.tx_fax, a2.tx_cas, a2.tx_corele, a2.tx_dirweb, a2.co_ciu, a4.tx_deslar as NomCiu, a2.co_zon, a5.tx_deslar as NomZon, a2.co_ven, a6.tx_usr, ";
                strSQL+=" a6.tx_nom as NomVen, a2.tx_obscxc, a2.tx_obsinfburcre, a2.tx_idepro, a2.tx_nompro, a2.tx_dirpro, a2.tx_telpro, a2.tx_nacpro, a2.fe_conemp, a2.tx_tipactemp, a2.tx_obspro, ";
                strSQL+=" a2.tx_percon, a2.tx_telcon, a2.tx_corelecon, a2.nd_moncre, a2.ne_diagra, a2.nd_maxdes, a2.tx_obs1, a2.tx_obs2, a2.st_reg, a2.fe_ing, a2.fe_ultmod, a2.co_usring,";
                strSQL+=" a2.co_usrmod, a2.co_tipper,  a2.nd_maruti, a2.st_regrep, a2.st_cieretpen, a1.tx_obs1, a1.tx_obs2";
                strSQL+=" FROM tbm_solcre AS a1 ";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                strSQL+=" LEFT OUTER JOIN tbm_tipper AS a3 ON (a2.co_emp=a3.co_emp and a2.co_tipper=a3.co_tipper)";
                strSQL+=" LEFT OUTER JOIN tbm_ciu AS a4 ON (a2.co_ciu=a4.co_ciu)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_zon=a5.co_reg)";
                strSQL+=" LEFT OUTER JOIN tbm_usr AS a6 ON (a2.co_ven=a6.co_usr)";
                                
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL+=" WHERE a1.co_emp = " + rstCab.getString("co_emp") + "";
                    strSQL+=" AND a1.co_sol = " + rstCab.getString("co_sol") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_sol ";
                }
                else
                {
                    strSQL+=" INNER JOIN tbr_cliloc AS a7 ON (a2.co_emp=a7.co_emp and a2.co_cli=a7.co_cli)";
                    strSQL+=" WHERE a1.co_emp = " + rstCab.getString("co_emp") + "";
                    strSQL+=" AND a7.co_loc = " + rstCab.getString("co_loc") + "";
                    strSQL+=" AND a1.co_sol = " + rstCab.getString("co_sol") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_sol ";
                }
                rst=stm.executeQuery(strSQL);
                
                if (rst.next())
                {
                    txtCedPasPro.setText(((rst.getString("tx_idePro")==null)?"":rst.getString("tx_idePro")));
                    txtNomPro.setText(((rst.getString("tx_nomPro")==null)?"":rst.getString("tx_nomPro")));
                    txtDomPro.setText(((rst.getString("tx_dirPro")==null)?"":rst.getString("tx_dirPro")));
                    txtTelPro.setText(((rst.getString("tx_telPro")==null)?"":rst.getString("tx_telPro")));
                    txtNacPro.setText(((rst.getString("tx_nacPro")==null)?"":rst.getString("tx_nacPro")));
                    dtpFecConEmp.setText(objUti.formatearFecha(rst.getDate("fe_conEmp"),"dd/MM/yyyy"));
                    txtTipActEmp.setText(((rst.getString("tx_tipActEmp")==null)?"":rst.getString("tx_tipActEmp")));
                    txaObs1.setText(((rst.getString("tx_obsPro")==null)?"":rst.getString("tx_obsPro")));                   
                    
                    /////prueba para fechas en blaco o nulas////////
                    String strFecConEmp = objUti.formatearFecha(rst.getDate("fe_conEmp"),"dd/MM/yyyy");
                    if(dtpFecConEmp.isFecha())
                        strFecConEmp=null;
                    
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            //Mostrar la posiciï¿½n relativa del registro.
            intPosRel=rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);            
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    ////////MUESTRA LOS DATOS DE LA TABLA ACCIONISTAS DE LA EMPRESA////
    private boolean cargarDetProEmp()
    {
        int intPosRel, intCodEmp;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            intCodEmp=objParSis.getCodigoEmpresa();
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_cli, a1.tx_nom ";
                strSQL+=" FROM tbm_accEmpCli AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                                
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL+=" WHERE a1.co_emp = " + rstCab.getString("co_emp") + "";
                    strSQL+=" AND a1.co_cli = " + rstCab.getString("co_cli") + "";
                    strSQL+=" AND a2.st_reg <> 'E'";
                }
                else
                {
                    strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli)";
                    strSQL+=" WHERE a1.co_emp = " + rstCab.getString("co_emp") + "";
                    strSQL+=" AND a3.co_loc = " + rstCab.getString("co_loc") + "";
                    strSQL+=" AND a1.co_cli = " + rstCab.getString("co_cli") + "";
                    strSQL+=" AND a2.st_reg <> 'E'";
                }
                    
                rst=stm.executeQuery(strSQL);
                while (rst.next())
                {
                    vecCabAcc=new Vector();
                    vecCabAcc.add(INT_TBL_DAT_LIN, "");
                    vecCabAcc.add(INT_TBL_DAT_NOM_ACC, rst.getString("tx_nom"));
                    vecCabAcc.add(INT_TBL_DAT_COD_CLI, rst.getString("co_cli"));
                    vecCabAcc.add(INT_TBL_DAT_BUT_ACC, null);
                    vecDatAcc.add(vecCabAcc);
                }
            }
            ///limpiar y cerrar el resulset///
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            
            //Asignar vectores al modelo.
            objTblModAcc.setData(vecDatAcc);
            tblAcc.setModel(objTblModAcc);
            ///vecDatAcc.clear();
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetCon()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {
            conCon=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCon!=null)
            {
                stmCon=conCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);              
                strSQL="";
                strSQL+="SELECT a2.co_cli as co_cli, a1.co_reg as co_reg, a1.tx_nom as tx_nomcon ";
                strSQL+=" FROM tbm_concli AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";                
                
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL+=" WHERE a2.co_emp = " + rstCab.getString("co_emp") + "";
                    strSQL+=" AND a2.co_cli = " + rstCab.getString("co_cli") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_reg ";
                }
                else
                {
                    strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli)";
                    strSQL+=" WHERE a2.co_emp = " + rstCab.getString("co_emp") + "";
                    strSQL+=" AND a3.co_loc = " + rstCab.getString("co_loc") + "";
                    strSQL+=" AND a2.co_cli = " + rstCab.getString("co_cli") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_reg ";
                }
                
                rstCon = stmCon.executeQuery(strSQL);
                while (rstCon.next())
                {                    
                    vecCabCon=new Vector();
                    vecCabCon.add(INT_TBL_CON_LIN, "");
                    vecCabCon.add(INT_TBL_CON_CLI, rstCon.getString("co_cli"));
                    vecCabCon.add(INT_TBL_CON_REG, rstCon.getString("co_reg"));
                    vecCabCon.add(INT_TBL_CON_NOM, rstCon.getString("tx_nomcon"));
                    vecCabCon.add(INT_TBL_CON_BUT, null);
                    vecDatCon.add(vecCabCon);
                }
            }
            ///limpiar y cerrar el resulset///
            rstCon.close();
            stmCon.close();
            conCon.close();
            rstCon=null;
            stmCon=null;
            conCon=null;
            
            //Asignar vectores al modelo.
            objTblModCon.setData(vecDatCon);
            tblCon.setModel(objTblModCon);
            //vecDatCon.clear();
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDatObs()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {
            conObs=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conObs!=null)
            {
                stmObs=conObs.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);     
                strSQL="";
                strSQL+="SELECT a1.co_emp as CodEmp, a2.co_cli as CodCli, a1.ne_mod as NumMod, a1.co_reg as CodReg, a1.fe_ing as FecIng, a1.tx_obs1 as NomObs ";
                strSQL+=" FROM tbm_obscli AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                                
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL+=" WHERE a1.co_emp = " + rstCab.getString("co_emp") + "";
                    strSQL+=" AND a2.co_cli = " + rstCab.getString("co_cli") + "";
                    strSQL+=" ORDER BY a1.co_reg ";
                }
                else
                {
                    strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli)";
                    strSQL+=" WHERE a1.co_emp = " + rstCab.getString("co_emp") + "";
                    strSQL+=" AND a3.co_loc = " + rstCab.getString("co_loc") + "";
                    strSQL+=" AND a2.co_cli = " + rstCab.getString("co_cli") + "";
                    strSQL+=" ORDER BY a1.co_reg ";
                }
                
                rstObs = stmObs.executeQuery(strSQL);
                while (rstObs.next())
                {
                    vecCabObs=new Vector();
                    vecCabObs.add(INT_TBL_OBS_LIN, "");
                    vecCabObs.add(INT_TBL_OBS_FEC, rstObs.getString("FecIng"));
                    vecCabObs.add(INT_TBL_OBS_DES, rstObs.getString("NomObs"));
                    vecCabObs.add(INT_TBL_OBS_BUT, null);
                    vecDatObs.add(vecCabObs);
                }
            }
            ///limpiar y cerrar el resulset///
            rstObs.close();
            stmObs.close();
            conObs.close();
            rstObs=null;
            stmObs=null;
            conObs=null;
            
            //Asignar vectores al modelo.
            objTblModObs.setData(vecDatObs);
            tblObs.setModel(objTblModObs);
            //vecDatCon.clear();
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetRefCom()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {
            conRefCom=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conRefCom!=null)
            {
                stmRefCom=conRefCom.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);           
                strSQL="";                
                strSQL+="SELECT a1.co_emp, a1.co_sol, a3.co_cli, a3.tx_nom as tx_nomcli, a1.co_ref, a1.fe_ref, a1.tx_nom as tx_nomref, a1.tx_matproref, a1.tx_tel, a1.tx_tie, a1.tx_cupcre, ";
                strSQL+=" a1.tx_placre, a1.tx_forpag, a1.st_pro, a1.tx_cal, a1.tx_inf, a1.tx_carinf, a1.tx_obs1, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod ";
                strSQL+=" FROM tbm_refcomsolcre as a1 ";
                strSQL+=" INNER JOIN tbm_solcre as a2 ON (a1.co_emp=a2.co_emp and a1.co_sol=a2.co_sol) ";
                strSQL+=" INNER JOIN tbm_cli    as a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli) ";
                                
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL+=" WHERE a2.co_emp = " + rstCab.getString("co_emp") + "";
                    strSQL+=" AND a3.co_cli = " + rstCab.getString("co_cli") + "";
                    strSQL+=" AND a2.co_sol = " + rstCab.getString("co_sol") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_ref ";
                }
                else
                {
                    strSQL+=" INNER JOIN tbr_cliloc AS a4 ON (a3.co_emp=a4.co_emp and a3.co_cli=a4.co_cli)";
                    strSQL+=" WHERE a2.co_emp = " + rstCab.getString("co_emp") + "";
                    strSQL+=" AND a4.co_loc = " + rstCab.getString("co_loc") + "";
                    strSQL+=" AND a3.co_cli = " + rstCab.getString("co_cli") + "";
                    strSQL+=" AND a2.co_sol = " + rstCab.getString("co_sol") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_ref ";
                }
                
                rstRefCom=stmRefCom.executeQuery(strSQL);
                while (rstRefCom.next())
                {                    
                    vecCabRefCom=new Vector();
                    vecCabRefCom.add(INT_TBL_REF_COM_LIN, "");
                    vecCabRefCom.add(INT_TBL_REF_COM_SOL, rstRefCom.getString("co_sol"));
                    vecCabRefCom.add(INT_TBL_REF_COM_CLI, rstRefCom.getString("co_cli"));
                    vecCabRefCom.add(INT_TBL_REF_COM_REG, rstRefCom.getString("co_ref"));
                    vecCabRefCom.add(INT_TBL_REF_COM_NOM, rstRefCom.getString("tx_nomref"));
                    vecCabRefCom.add(INT_TBL_REF_COM_BUT, null);
                    vecDatRefCom.add(vecCabRefCom);
                }
            }
            ///limpiar y cerrar el resulset///
            rstRefCom.close();
            stmRefCom.close();
            conRefCom.close();
            rstRefCom=null;
            stmRefCom=null;
            conRefCom=null;
            
            //Asignar vectores al modelo.
            objTblModRefCom.setData(vecDatRefCom);
            tblRefCom.setModel(objTblModRefCom);
            //vecDatRefCom.clear();
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetRefBan()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {
            conRefBan=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conRefBan!=null)
            {
                stmRefBan=conRefBan.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);          
                strSQL="";                
                strSQL+="SELECT a1.co_emp, a1.co_sol, a3.co_cli, a3.tx_nom as tx_nomcli, a1.co_ref, a1.fe_ref, a1.tx_nom as tx_nombco, a1.tx_age, a1.tx_oficta, a1.tx_numcta, a1.tx_duecta, ";
                strSQL+=" a1.co_ciuapecta, a4.tx_deslar, a1.fe_apecta, a1.tx_salprocta, a1.st_pro, a1.st_credir, a1.st_creind, a1.tx_moncredir, a1.tx_moncreind, a1.tx_inf, a1.tx_carinf, ";
                strSQL+=" a1.tx_docdig, a1.tx_obs1, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod ";
                strSQL+=" FROM tbm_refbansolcre as a1 ";
                strSQL+=" INNER JOIN tbm_solcre as a2 ON (a1.co_emp=a2.co_emp and a1.co_sol=a2.co_sol) ";
                strSQL+=" INNER JOIN tbm_cli    as a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli) ";
                strSQL+=" INNER JOIN tbm_ciu    as a4 ON (a1.co_ciuapecta=a4.co_ciu) ";
                
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL+=" WHERE a2.co_emp = " + rstCab.getString("co_emp") + "";
                    strSQL+=" AND a3.co_cli = " + rstCab.getString("co_cli") + "";
                    strSQL+=" AND a2.co_sol = " + rstCab.getString("co_sol") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_ref ";
                }
                else
                {
                    strSQL+=" INNER JOIN tbr_cliloc AS a5 ON (a3.co_emp=a5.co_emp and a3.co_cli=a5.co_cli)";
                    strSQL+=" WHERE a2.co_emp = " + rstCab.getString("co_emp") + "";
                    strSQL+=" AND a5.co_loc = " + rstCab.getString("co_loc") + "";
                    strSQL+=" AND a3.co_cli = " + rstCab.getString("co_cli") + "";
                    strSQL+=" AND a2.co_sol = " + rstCab.getString("co_sol") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_ref ";
                }
                
                rstRefBan=stmRefBan.executeQuery(strSQL);
                while (rstRefBan.next())
                {                    
                    vecCabRefBan=new Vector();
                    vecCabRefBan.add(INT_TBL_REF_BAN_LIN, "");
                    vecCabRefBan.add(INT_TBL_REF_BAN_SOL, rstRefBan.getString("co_sol"));
                    vecCabRefBan.add(INT_TBL_REF_BAN_CLI, rstRefBan.getString("co_cli"));
                    vecCabRefBan.add(INT_TBL_REF_BAN_REG, rstRefBan.getString("co_ref"));
                    vecCabRefBan.add(INT_TBL_REF_BAN_NOM, rstRefBan.getString("tx_nombco"));
                    vecCabRefBan.add(INT_TBL_REF_BAN_BUT, null);
                    vecDatRefBan.add(vecCabRefBan);
                }
            }
            ///limpiar y cerrar el resulset///
            rstRefBan.close();
            stmRefBan.close();
            conRefBan.close();
            rstRefBan=null;
            stmRefBan=null;
            conRefBan=null;
            
            //Asignar vectores al modelo.
            objTblModRefBan.setData(vecDatRefBan);
            tblRefBan.setModel(objTblModRefBan);
            //vecDatRefBan.clear();
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetDocDig()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {
            conDocDig=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conDocDig!=null)
            {
                stmDocDig=conDocDig.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);            
                strSQL="";
                
                strSQL+="SELECT a1.co_emp, a1.co_sol, a3.co_cli, a3.tx_nom as tx_nomcli, a1.co_ref, a1.fe_ref, a1.tx_nom as tx_nomref, a1.tx_matproref, a1.tx_tel, a1.tx_tie, a1.tx_cupcre, ";
                strSQL+=" a1.tx_placre, a1.tx_forpag, a1.st_pro, a1.tx_cal, a1.tx_inf, a1.tx_carinf, a1.tx_obs1, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod ";
                strSQL+=" FROM tbm_refcomsolcre as a1 ";
                strSQL+=" INNER JOIN tbm_solcre as a2 ON (a1.co_emp=a2.co_emp and a1.co_sol=a2.co_sol) ";
                strSQL+=" INNER JOIN tbm_cli    as a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli) ";
                strSQL+=" WHERE a2.co_emp = " + rstCab.getString("co_emp") + "";
                strSQL+=" AND a3.co_cli = " + rstCab.getString("co_cli") + "";
                strSQL+=" AND a2.co_sol = " + txtSolCre.getText() + "";
            rstDocDig=stmDocDig.executeQuery(strSQL);
                while (rstDocDig.next())
                {                    
//                    vecCabCon=new Vector();
//                    vecCabCon.add(INT_TBL_CON_LIN, "");
//                    vecCabCon.add(INT_TBL_CON_CLI, rst.getString("co_cli"));
//                    vecCabCon.add(INT_TBL_CON_REG, rst.getString("co_reg"));
//                    vecCabCon.add(INT_TBL_CON_NOM, rst.getString("tx_nomcon"));
//                    vecCabCon.add(INT_TBL_CON_BUT, null);
//                    vecDatCon.add(vecCabCon);
                }
            }
            ///limpiar y cerrar el resulset///
            rstDocDig.close();
            stmDocDig.close();
            conDocDig.close();
            rstDocDig=null;
            stmDocDig=null;
            conDocDig=null;
            
            //Asignar vectores al modelo.
            objTblModCon.setData(vecDatCon);
            tblCon.setModel(objTblModCon);
            ///vecDatAcc.clear();
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcion permite consultar los registros historicos de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarCabRegHis()
    {
        int intCodEmp, intCodLoc, intultdoc;
        boolean blnRes=true;
        String codmodprg="";
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            conCabHis=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            intultdoc = ultCodDoc();

            
            if (conCabHis!=null)
            {
                stmCabHis=conCabHis.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_sol, a1.co_his, a1.fe_sol, a1.co_cli, a1.st_reg as EstSol, a2.tx_nom as NomCli, a3.co_tipper, a2.tx_tipper, a3.tx_deslar, ";
                strSQL+=" a2.st_cli, a2.st_prv, a2.tx_tipide, a2.tx_ide, a2.tx_dir, a2.tx_refubi, a2.tx_tel1, a2.tx_tel2, a2.tx_tel3, a2.tx_tel, ";
                strSQL+=" a2.tx_fax, a2.tx_cas, a2.tx_corele, a2.tx_dirweb, a2.co_ciu, a4.tx_deslar, a2.co_zon, a5.tx_deslar, a2.co_ven, a6.tx_usr, ";
                strSQL+=" a6.tx_nom, a2.tx_obscxc, a2.tx_obsinfburcre, a2.tx_idepro, a2.tx_nompro, a2.tx_dirpro, a2.tx_telpro, a2.tx_nacpro, a2.fe_conemp, a2.tx_tipactemp, a2.tx_obspro, ";
                strSQL+=" a2.tx_percon, a2.tx_telcon, a2.tx_corelecon, a2.nd_moncre, a2.ne_diagra, a2.nd_maxdes, a2.tx_obs1, a2.tx_obs2, a2.st_reg, a2.fe_ing, a2.fe_ultmod, a2.co_usring,";
                strSQL+=" a2.co_usrmod, a2.co_tipper,  a2.nd_maruti, a2.st_regrep, a2.st_cieretpen, a2.fe_ultactdat, a2.fe_proactdat, ";
                strSQL+=" a1.co_forpag as CodForPag, a1.nd_moncre as Moncre, a1.tx_obs1, a1.tx_obs2 ";
                strSQL+=" FROM tbh_solcre AS a1 ";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                strSQL+=" INNER JOIN tbm_tipper AS a3 ON (a2.co_emp=a3.co_emp and a2.co_tipper=a3.co_tipper)";
                strSQL+=" INNER JOIN tbm_ciu AS a4 ON (a2.co_ciu=a4.co_ciu)";
                strSQL+=" INNER JOIN tbm_var AS a5 ON (a2.co_zon=a5.co_reg)";
                strSQL+=" INNER JOIN tbm_usr AS a6 ON (a2.co_ven=a6.co_usr)";
                strSQL+=" LEFT  JOIN tbm_obscli AS a7 ON (a2.co_emp=a7.co_emp AND a2.co_cli=a7.co_cli)";
                
//                if(CODEMP==0)
//                    strSQL+=" WHERE a2.co_emp = " + intCodEmp + "";
//                else
//                    strSQL+=" WHERE a2.co_emp = " + CODEMP + "";

                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" WHERE a2.co_emp=" + CODEMP;
                    else
                        strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                }
                else
                {
                    if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" WHERE a2.co_emp=" + CODEMP;
                    else
                    {
                        
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a8.co_loc, a1.co_his, a1.co_sol, a1.fe_sol, a1.co_cli, a1.st_reg as EstSol, a2.tx_nom as NomCli, a3.co_tipper, a2.tx_tipper, a3.tx_deslar, ";
                        strSQL+=" a2.st_cli, a2.st_prv, a2.tx_tipide, a2.tx_ide, a2.tx_dir, a2.tx_refubi, a2.tx_tel1, a2.tx_tel2, a2.tx_tel3, a2.tx_tel, ";
                        strSQL+=" a2.tx_fax, a2.tx_cas, a2.tx_corele, a2.tx_dirweb, a2.co_ciu, a4.tx_deslar, a2.co_zon, a5.tx_deslar, a2.co_ven, a6.tx_usr, ";
                        strSQL+=" a6.tx_nom, a2.tx_obscxc, a2.tx_obsinfburcre, a2.tx_idepro, a2.tx_nompro, a2.tx_dirpro, a2.tx_telpro, a2.tx_nacpro, a2.fe_conemp, a2.tx_tipactemp, a2.tx_obspro, ";
                        strSQL+=" a2.tx_percon, a2.tx_telcon, a2.tx_corelecon, a2.nd_moncre, a2.ne_diagra, a2.nd_maxdes, a2.tx_obs1, a2.tx_obs2, a2.st_reg, a2.fe_ing, a2.fe_ultmod, a2.co_usring,";
                        strSQL+=" a2.co_usrmod, a2.co_tipper,  a2.nd_maruti, a2.st_regrep, a2.st_cieretpen, a2.fe_ultactdat, a2.fe_proactdat, ";
                        strSQL+=" a1.co_forpag as CodForPag, a1.nd_moncre as Moncre, a1.tx_obs1, a1.tx_obs2 ";
                        strSQL+=" FROM tbh_solcre AS a1 ";
                        strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                        strSQL+=" INNER JOIN tbm_tipper AS a3 ON (a2.co_emp=a3.co_emp and a2.co_tipper=a3.co_tipper)";
                        strSQL+=" INNER JOIN tbm_ciu AS a4 ON (a2.co_ciu=a4.co_ciu)";
                        strSQL+=" INNER JOIN tbm_var AS a5 ON (a2.co_zon=a5.co_reg)";
                        strSQL+=" INNER JOIN tbm_usr AS a6 ON (a2.co_ven=a6.co_usr)";
                        strSQL+=" LEFT  JOIN tbm_obscli AS a7 ON (a2.co_emp=a7.co_emp AND a2.co_cli=a7.co_cli)";
                        strSQL+=" INNER JOIN tbr_cliloc AS a8 ON (a2.co_emp=a8.co_emp and a2.co_cli=a8.co_cli)";
                        strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a8.co_loc=" + objParSis.getCodigoLocal();
                    }
                }


                strAux=txtCodCli.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_cli LIKE '" + strAux.replaceAll("'", "''") + "'";

                strAux=txtSolCre.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_sol LIKE '" + strAux.replaceAll("'", "''") + "'";
                
                strAux=txtCodHisSolCre.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_his LIKE '" + strAux.replaceAll("'", "''") + "'";

                strSQL+=" AND a1.st_reg <> 'E'";
                strSQL+=" ORDER BY a1. co_sol, a1.co_his ";
                
                rstCabHis=stmCabHis.executeQuery(strSQL);
                
                if (rstCabHis.next())
                {
                    rstCabHis.last();
                    objTooBar.setMenSis("Se encontraron " + rstCabHis.getRow() + " registros");
                    rstCabHis.first();
                    cargarRegHis();
                    strSQLCon=strSQL;
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ningï¿½n registro que cumpla el criterio de bï¿½squeda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
            }
            
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarRegHis()
    {
        boolean blnRes=true;
        try
        {
            if (cargarCabRegHis())
            {
                if(cargarDatProEmpHis())
                {
                    if(cargarDetProEmpHis())
                    {
                        if(cargarDetConHis())
                        {
                            if(cargarDatObsHis())
                            {
                                if(cargarDetRefComHis())
                                {
                                    if(cargarDetRefBanHis())
                                    {
                                        if(cargarDetDocDigHis())
                                        {
                                            
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
                            else
                                blnRes=false;
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
            else
                blnRes=false;
            
            blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /////////////////////////// H I S T O R I C O ///////////////////////////////////
    /**
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabRegHis()
    {
        int intPosRel, intCodEmp;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            conHis=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conHis!=null)
            {
                stmHis=conHis.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_sol, a1.co_his, a1.fe_sol, a1.co_cli, a1.st_reg as EstSol, a2.tx_nom as NomCli, a3.co_tipper, a2.tx_tipper, a3.tx_deslar, ";
                strSQL+=" a2.st_cli, a2.st_prv, a2.tx_tipide, a2.tx_ide, a2.tx_dir, a2.tx_refubi, a2.tx_tel1, a2.tx_tel2, a2.tx_tel3, a2.tx_tel, a2.co_forpag as CodForPagCon, ";
                strSQL+=" a2.tx_fax, a2.tx_cas, a2.tx_corele, a2.tx_dirweb, a2.co_ciu, a4.tx_deslar as NomCiu, a2.co_zon, a5.tx_deslar as NomZon, a2.co_ven, a6.tx_usr, ";
                strSQL+=" a6.tx_nom as NomVen, a2.tx_obscxc, a2.tx_obsinfburcre, a2.tx_idepro, a2.tx_nompro, a2.tx_dirpro, a2.tx_telpro, a2.tx_nacpro, a2.fe_conemp, a2.tx_tipactemp, a2.tx_obspro, ";
                strSQL+=" a2.tx_percon, a2.tx_telcon, a2.tx_corelecon, round(a2.nd_moncre,0) as MonCreCon, a2.ne_diagra, a2.nd_maxdes, a2.tx_obs1, a2.tx_obs2, a2.st_reg, a2.fe_ing, a2.fe_ultmod, a2.co_usring, ";
                strSQL+=" a2.co_usrmod, a2.co_tipper,  a2.nd_maruti, a2.st_regrep, a2.st_cieretpen, a1.co_forpag as CodForPagSol, round(a1.nd_moncre,0) as MonCreSol, a7.tx_des as DesForPagSol, a8.tx_des as DesForPagCon, ";  /// , a7.tx_des as DesForPag
                strSQL+=" a2.fe_ultactdat, a2.fe_proactdat, a1.tx_obs1 as ObsSolCre1, a1.tx_obs2 as ObsSolCre2";
                strSQL+=" FROM tbh_solcre AS a1 ";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                strSQL+=" INNER JOIN tbm_tipper AS a3 ON (a2.co_emp=a3.co_emp and a2.co_tipper=a3.co_tipper)";
                strSQL+=" INNER JOIN tbm_ciu AS a4 ON (a2.co_ciu=a4.co_ciu)";
                strSQL+=" INNER JOIN tbm_var AS a5 ON (a2.co_zon=a5.co_reg)";
                strSQL+=" INNER JOIN tbm_usr AS a6 ON (a2.co_ven=a6.co_usr)";
                strSQL+=" LEFT OUTER JOIN tbm_cabforpag AS a7 ON (a1.co_emp=a7.co_emp and a1.co_forpag=a7.co_forpag)";
                strSQL+=" LEFT OUTER JOIN tbm_cabforpag AS a8 ON (a2.co_emp=a8.co_emp and a2.co_forpag=a8.co_forpag)";
                                
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL+=" WHERE a1.co_emp = " + rstCabHis.getString("co_emp") + "";
                    strSQL+=" AND a1.co_sol = " + rstCabHis.getString("co_sol") + "";
                    strSQL+=" AND a1.co_his = " + rstCabHis.getString("co_his") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_sol, a1.co_his ";
                }
                else
                {
                    strSQL+=" INNER JOIN tbr_cliloc AS a9 ON (a2.co_emp=a9.co_emp and a2.co_cli=a9.co_cli)";
                    strSQL+=" WHERE a1.co_emp = " + rstCabHis.getString("co_emp") + "";
                    strSQL+=" AND a9.co_loc = " + rstCabHis.getString("co_loc") + "";
                    strSQL+=" AND a1.co_sol = " + rstCabHis.getString("co_sol") + "";
                    strSQL+=" AND a1.co_his = " + rstCabHis.getString("co_his") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_sol, a1.co_his ";
                }
                                
                rstHis=stmHis.executeQuery(strSQL);
                
                if (rstHis.next())
                ///for(int i=0;rst.next();i++)
                {                   
                    txtSolCre.setText(((rstHis.getString("co_sol")==null)?"":rstHis.getString("co_sol")));
                    txtCodHisSolCre.setText(((rstHis.getString("co_his")==null)?"":rstHis.getString("co_his")));
                    dtpFecDoc.setText(objUti.formatearFecha(rstHis.getDate("fe_sol"),"dd/MM/yyyy"));
                    txtCodCli.setText(((rstHis.getString("co_cli")==null)?"":rstHis.getString("co_cli")));
                    txtNomCli.setText(((rstHis.getString("NomCli")==null)?"":rstHis.getString("NomCli")));
                    txtCodTipPer.setText(((rstHis.getString("co_tipper")==null)?"":rstHis.getString("co_tipper")));
                    txtDesTipPer.setText(((rstHis.getString("tx_tipper")==null)?"":rstHis.getString("tx_tipper")));
                    txtNomTipPer.setText(((rstHis.getString("tx_deslar")==null)?"":rstHis.getString("tx_deslar")));
                    
                    /////Para saber el estado del cliente////
                    String strCli= rstHis.getString("st_cli");
                    if(strCli.equals("S"))
                    {
                        jChkCli.setSelected(true);
                    }
                    else
                    {
                        jChkCli.setSelected(false);
                    }
                    
                    /////Para saber el estado del cliente////
                    String strPrv= rstHis.getString("st_prv");
                    if(strPrv.equals("S"))
                    {
                        jChkPrv.setSelected(true);
                    }
                    else
                    {
                        jChkPrv.setSelected(false);
                    }
                    
                    ////Para saber el tipo de Identificacion de la persona///
                    String strIde= rstHis.getString("tx_tipide");
                    if(strIde.equals("C"))
                    {
                            jCbxTipIde.setSelectedIndex(0);
                    }
                    if(strIde.equals("R"))
                    {
                            jCbxTipIde.setSelectedIndex(1);
                    }

                    txtIde.setText(((rstHis.getString("tx_ide")==null)?"":rstHis.getString("tx_ide")));
                    txtDir.setText(((rstHis.getString("tx_dir")==null)?"":rstHis.getString("tx_dir")));
                    txtRefUbi.setText(((rstHis.getString("tx_refubi")==null)?"":rstHis.getString("tx_refubi")));
                    txtTelCon1.setText(((rstHis.getString("tx_tel1")==null)?"":rstHis.getString("tx_tel1")));
                    txtTelCon2.setText(((rstHis.getString("tx_tel2")==null)?"":rstHis.getString("tx_tel2")));
                    txtTelCon3.setText(((rstHis.getString("tx_tel3")==null)?"":rstHis.getString("tx_tel3")));
                    txtTelf.setText(((rstHis.getString("tx_tel")==null)?"":rstHis.getString("tx_tel")));
                    txtFax.setText(((rstHis.getString("tx_fax")==null)?"":rstHis.getString("tx_fax")));
                    txtCasilla.setText(((rstHis.getString("tx_cas")==null)?"":rstHis.getString("tx_cas")));
                    txtEmail.setText(((rstHis.getString("tx_corele")==null)?"":rstHis.getString("tx_corele")));
                    txtWeb.setText(((rstHis.getString("tx_dirweb")==null)?"":rstHis.getString("tx_dirweb")));
                    txtCodCiu.setText(((rstHis.getString("co_ciu")==null)?"":rstHis.getString("co_ciu")));
                    txtCiu.setText(((rstHis.getString("NomCiu")==null)?"":rstHis.getString("NomCiu")));
                    txtCodZon.setText(((rstHis.getString("co_zon")==null)?"":rstHis.getString("co_zon")));
                    txtNomZon.setText(((rstHis.getString("NomZon")==null)?"":rstHis.getString("NomZon")));
                    txtCodVen.setText(((rstHis.getString("co_ven")==null)?"":rstHis.getString("co_ven")));
                    txtDesVen.setText(((rstHis.getString("tx_usr")==null)?"":rstHis.getString("tx_usr")));
                    txtNomVen.setText(((rstHis.getString("NomVen")==null)?"":rstHis.getString("NomVen")));
                    txaObsCxC.setText(((rstHis.getString("tx_obscxc")==null)?"":rstHis.getString("tx_obscxc")));
                    txaObsInfBurCre.setText(((rstHis.getString("tx_obsinfburcre")==null)?"":rstHis.getString("tx_obsinfburcre")));
                    
                    /////cargar datos de credito Solicitado/////
                    txtMonSol.setText(((rstHis.getString("MonCreSol")==null)?"":rstHis.getString("MonCreSol")));
                    txtCodForPag.setText(((rstHis.getString("CodForPagSol")==null)?"":rstHis.getString("CodForPagSol")));
                    txtForPag.setText(((rstHis.getString("DesForPagSol")==null)?"":rstHis.getString("DesForPagSol")));
                    dtpFecUltAct.setText(objUti.formatearFecha(rstHis.getDate("fe_ultactdat"),"dd/MM/yyyy"));
                    
                    /////cargar datos de credito Concedido/////
                    txtMonApr.setText(((rstHis.getString("MonCreCon")==null)?"":rstHis.getString("MonCreCon")));
                    txtCodForPagCreCon.setText(((rstHis.getString("CodForPagCon")==null)?"":rstHis.getString("CodForPagCon")));
                    txtForPagCreCon.setText(((rstHis.getString("DesForPagCon")==null)?"":rstHis.getString("DesForPagCon")));
                    dtpFecProAct.setText(objUti.formatearFecha(rstHis.getDate("fe_proactdat"),"dd/MM/yyyy"));
                    
                    ////cargar datos de Observacion 1 y 2 desde El panel de Control de Solicitud de Credito////
                    txaObsSolCre1.setText(((rstHis.getString("ObsSolCre1")==null)?"":rstHis.getString("ObsSolCre1")));
                    txaObsSolCre2.setText(((rstHis.getString("ObsSolCre2")==null)?"":rstHis.getString("ObsSolCre2")));
                    
                    //Mostrar el estado del registro.
                    strAux=rstCabHis.getString("EstSol");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I") )
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                    
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rstHis.close();
            stmHis.close();
            conHis.close();
            rstHis=null;
            stmHis=null;
            conHis=null;
            //Mostrar la posiciï¿½n relativa del registro.
            intPosRel=rstCabHis.getRow();
            rstCabHis.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCabHis.getRow());
            rstCabHis.absolute(intPosRel);            
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    //////// CARGA LOS DATOS DEL PROPIETARIO DE LA EMPRESA//////////////
    private boolean cargarDatProEmpHis()
    {
        int intPosRel, intCodEmp;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            conHis=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conHis!=null)
            {
                stmHis=conHis.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_sol, a1.fe_sol, a1.co_cli, a1.st_reg as EstSol, a2.tx_nom as NomCli, a3.co_tipper, a2.tx_tipper, a3.tx_deslar, ";
                strSQL+=" a2.st_cli, a2.st_prv, a2.tx_tipide, a2.tx_ide, a2.tx_dir, a2.tx_refubi, a2.tx_tel1, a2.tx_tel2, a2.tx_tel3, a2.tx_tel, ";
                strSQL+=" a2.tx_fax, a2.tx_cas, a2.tx_corele, a2.tx_dirweb, a2.co_ciu, a4.tx_deslar as NomCiu, a2.co_zon, a5.tx_deslar as NomZon, a2.co_ven, a6.tx_usr, ";
                strSQL+=" a6.tx_nom as NomVen, a2.tx_obscxc, a2.tx_obsinfburcre, a2.tx_idepro, a2.tx_nompro, a2.tx_dirpro, a2.tx_telpro, a2.tx_nacpro, a2.fe_conemp, a2.tx_tipactemp, a2.tx_obspro, ";
                strSQL+=" a2.tx_percon, a2.tx_telcon, a2.tx_corelecon, a2.nd_moncre, a2.ne_diagra, a2.nd_maxdes, a2.tx_obs1, a2.tx_obs2, a2.st_reg, a2.fe_ing, a2.fe_ultmod, a2.co_usring,";
                strSQL+=" a2.co_usrmod, a2.co_tipper,  a2.nd_maruti, a2.st_regrep, a2.st_cieretpen, a1.tx_obs1, a1.tx_obs2";
                strSQL+=" FROM tbh_solcre AS a1 ";
                ///strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                strSQL+=" INNER JOIN tbh_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli and a1.co_his=a2.co_hissol) ";
                strSQL+=" INNER JOIN tbm_tipper AS a3 ON (a2.co_emp=a3.co_emp and a2.co_tipper=a3.co_tipper)";
                strSQL+=" INNER JOIN tbm_ciu AS a4 ON (a2.co_ciu=a4.co_ciu)";
                strSQL+=" INNER JOIN tbm_var AS a5 ON (a2.co_zon=a5.co_reg)";
                strSQL+=" INNER JOIN tbm_usr AS a6 ON (a2.co_ven=a6.co_usr)";
                                
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL+=" WHERE a1.co_emp = " + rstCabHis.getString("co_emp") + "";
                    strSQL+=" AND a1.co_sol = " + rstCabHis.getString("co_sol") + "";
                    ///strSQL+=" AND a1.co_his = " + rstCabHis.getString("co_his") + "";
                    strSQL+=" AND a2.co_hissol = " + rstCabHis.getString("co_his") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_sol, a1.co_his ";
                }
                else
                {
                    strSQL+=" INNER JOIN tbr_cliloc AS a7 ON (a2.co_emp=a7.co_emp and a2.co_cli=a7.co_cli)";
                    strSQL+=" WHERE a1.co_emp = " + rstCabHis.getString("co_emp") + "";
                    strSQL+=" AND a7.co_loc = " + rstCabHis.getString("co_loc") + "";
                    strSQL+=" AND a1.co_sol = " + rstCabHis.getString("co_sol") + "";
                    ///strSQL+=" AND a1.co_his = " + rstCabHis.getString("co_his") + "";
                    strSQL+=" AND a2.co_hissol = " + rstCabHis.getString("co_his") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_sol, a1.co_his ";
                }
                rstHis=stmHis.executeQuery(strSQL);
                
                if (rstHis.next())
                {
                    txtCedPasPro.setText(((rstHis.getString("tx_idePro")==null)?"":rstHis.getString("tx_idePro")));
                    txtNomPro.setText(((rstHis.getString("tx_nomPro")==null)?"":rstHis.getString("tx_nomPro")));
                    txtDomPro.setText(((rstHis.getString("tx_dirPro")==null)?"":rstHis.getString("tx_dirPro")));
                    txtTelPro.setText(((rstHis.getString("tx_telPro")==null)?"":rstHis.getString("tx_telPro")));
                    txtNacPro.setText(((rstHis.getString("tx_nacPro")==null)?"":rstHis.getString("tx_nacPro")));
                    dtpFecConEmp.setText(objUti.formatearFecha(rstHis.getDate("fe_conEmp"),"dd/MM/yyyy"));
                    txtTipActEmp.setText(((rstHis.getString("tx_tipActEmp")==null)?"":rstHis.getString("tx_tipActEmp")));
                    txaObs1.setText(((rstHis.getString("tx_obsPro")==null)?"":rstHis.getString("tx_obsPro")));                   
                    
                    /////prueba para fechas en blaco o nulas////////
                    String strFecConEmp = objUti.formatearFecha(rstHis.getDate("fe_conEmp"),"dd/MM/yyyy");
                    if(dtpFecConEmp.isFecha())
                        strFecConEmp=null;
                    
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rstHis.close();
            stmHis.close();
            conHis.close();
            rstHis=null;
            stmHis=null;
            conHis=null;
            //Mostrar la posiciï¿½n relativa del registro.
            intPosRel=rstCabHis.getRow();
            rstCabHis.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCabHis.getRow());
            rstCabHis.absolute(intPosRel);            
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    ////////MUESTRA LOS DATOS DE LA TABLA ACCIONISTAS DE LA EMPRESA////
    private boolean cargarDetProEmpHis()
    {
        int intPosRel, intCodEmp;
        boolean blnRes=true;
        try
        {
            conHis=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            intCodEmp=objParSis.getCodigoEmpresa();
            if (conHis!=null)
            {
                stmHis=conHis.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_cli, a1.tx_nom ";
                strSQL+=" FROM tbh_accEmpCli AS a1";
                ///strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                strSQL+=" INNER JOIN tbh_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli and a1.co_his=a2.co_hissol) ";
                                
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL+=" WHERE a1.co_emp = " + rstCabHis.getString("co_emp") + "";
                    strSQL+=" AND a1.co_cli = " + rstCabHis.getString("co_cli") + "";
                    strSQL+=" AND a1.co_sol = " + rstCabHis.getString("co_sol") + "";
                    strSQL+=" AND a1.co_hissol = " + rstCabHis.getString("co_his") + "";
                    strSQL+=" AND a2.st_reg <> 'E'";
                }
                else
                {
                    strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli)";
                    strSQL+=" WHERE a1.co_emp = " + rstCabHis.getString("co_emp") + "";
                    strSQL+=" AND a3.co_loc = " + rstCabHis.getString("co_loc") + "";
                    strSQL+=" AND a1.co_cli = " + rstCabHis.getString("co_cli") + "";
                    strSQL+=" AND a1.co_sol = " + rstCabHis.getString("co_sol") + "";
                    strSQL+=" AND a1.co_hissol = " + rstCabHis.getString("co_his") + "";
                    strSQL+=" AND a2.st_reg <> 'E'";
                }
                    
                rstHis=stmHis.executeQuery(strSQL);
                while (rstHis.next())
                {
                    vecCabAcc=new Vector();
                    vecCabAcc.add(INT_TBL_DAT_LIN, "");
                    vecCabAcc.add(INT_TBL_DAT_NOM_ACC, rstHis.getString("tx_nom"));
                    vecCabAcc.add(INT_TBL_DAT_COD_CLI, rstHis.getString("co_cli"));
                    vecCabAcc.add(INT_TBL_DAT_BUT_ACC, null);
                    vecDatAcc.add(vecCabAcc);
                }
            }
            ///limpiar y cerrar el resulset///
            rstHis.close();
            stmHis.close();
            conHis.close();
            rstHis=null;
            stmHis=null;
            conHis=null;
            
            //Asignar vectores al modelo.
            objTblModAcc.setData(vecDatAcc);
            tblAcc.setModel(objTblModAcc);
            ///vecDatAcc.clear();
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetConHis()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {
            conConHis=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conConHis!=null)
            {
                stmConHis=conConHis.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);         
                strSQL="";
                strSQL+="SELECT a2.co_cli as co_cli, a1.co_reg as co_reg, a1.tx_nom as tx_nomcon ";
                strSQL+=" FROM tbh_concli AS a1";
                ///strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";                
                strSQL+=" INNER JOIN tbh_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli and a1.co_his=a2.co_hissol) ";
                
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL+=" WHERE a2.co_emp = " + rstCabHis.getString("co_emp") + "";
                    strSQL+=" AND a2.co_cli = " + rstCabHis.getString("co_cli") + "";
                    strSQL+=" AND a1.co_sol = " + rstCabHis.getString("co_sol") + "";
                    strSQL+=" AND a1.co_hissol = " + rstCabHis.getString("co_his") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_reg ";
                }
                else
                {
                    strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli)";
                    strSQL+=" WHERE a2.co_emp = " + rstCabHis.getString("co_emp") + "";
                    strSQL+=" AND a3.co_loc = " + rstCabHis.getString("co_loc") + "";
                    strSQL+=" AND a2.co_cli = " + rstCabHis.getString("co_cli") + "";
                    strSQL+=" AND a1.co_sol = " + rstCabHis.getString("co_sol") + "";
                    strSQL+=" AND a1.co_hissol = " + rstCabHis.getString("co_his") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_reg ";
                }
                
                rstConHis = stmConHis.executeQuery(strSQL);
                while (rstConHis.next())
                {                    
                    vecCabCon=new Vector();
                    vecCabCon.add(INT_TBL_CON_LIN, "");
                    vecCabCon.add(INT_TBL_CON_CLI, rstConHis.getString("co_cli"));
                    vecCabCon.add(INT_TBL_CON_REG, rstConHis.getString("co_reg"));
                    vecCabCon.add(INT_TBL_CON_NOM, rstConHis.getString("tx_nomcon"));
                    vecCabCon.add(INT_TBL_CON_BUT, null);
                    vecDatCon.add(vecCabCon);
                }
            }
            ///limpiar y cerrar el resulset///
            rstConHis.close();
            stmConHis.close();
            conConHis.close();
            rstConHis=null;
            stmConHis=null;
            conConHis=null;
            
            //Asignar vectores al modelo.
            objTblModCon.setData(vecDatCon);
            tblCon.setModel(objTblModCon);
            //vecDatCon.clear();
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDatObsHis()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {
            conObsHis=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conObsHis!=null)
            {
                stmObsHis=conObsHis.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);          
                strSQL="";
                strSQL+="SELECT a1.co_emp as CodEmp, a2.co_cli as CodCli, a1.ne_mod as NumMod, a1.co_reg as CodReg, a1.fe_ing as FecIng, a1.tx_obs1 as NomObs ";
                strSQL+=" FROM tbh_obscli AS a1";
                ///strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                strSQL+=" INNER JOIN tbh_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli and a1.co_his=a2.co_hissol) ";
                                
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL+=" WHERE a1.co_emp = " + rstCabHis.getString("co_emp") + "";
                    strSQL+=" AND a2.co_cli = " + rstCabHis.getString("co_cli") + "";
                    strSQL+=" AND a1.co_sol = " + rstCabHis.getString("co_sol") + "";
                    strSQL+=" AND a1.co_hissol = " + rstCabHis.getString("co_his") + "";
                    strSQL+=" ORDER BY a1.co_reg ";
                }
                else
                {
                    strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli)";
                    strSQL+=" WHERE a1.co_emp = " + rstCabHis.getString("co_emp") + "";
                    strSQL+=" AND a3.co_loc = " + rstCabHis.getString("co_loc") + "";
                    strSQL+=" AND a2.co_cli = " + rstCabHis.getString("co_cli") + "";
                    strSQL+=" AND a1.co_sol = " + rstCabHis.getString("co_sol") + "";
                    strSQL+=" AND a1.co_hissol = " + rstCabHis.getString("co_his") + "";
                    strSQL+=" ORDER BY a1.co_reg ";
                }
                
                rstObsHis = stmObsHis.executeQuery(strSQL);
                while (rstObsHis.next())
                {                    
                    vecCabObs=new Vector();
                    vecCabObs.add(INT_TBL_CON_LIN, "");
                    vecCabObs.add(INT_TBL_OBS_FEC, rstObsHis.getString("FecIng"));
                    vecCabObs.add(INT_TBL_OBS_DES, rstObsHis.getString("NomObs"));
                    vecCabObs.add(INT_TBL_CON_BUT, null);
                    vecDatObs.add(vecCabObs);
                }
            }
            ///limpiar y cerrar el resulset///
            rstObsHis.close();
            stmObsHis.close();
            conObsHis.close();
            rstObsHis=null;
            stmObsHis=null;
            conObsHis=null;
            
            //Asignar vectores al modelo.
            objTblModObs.setData(vecDatObs);
            tblObs.setModel(objTblModObs);
            //vecDatCon.clear();
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetRefComHis()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {
            conRefComHis=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conRefComHis!=null)
            {
                stmRefComHis=conRefComHis.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);          
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_sol, a2.co_his, a3.co_cli, a3.tx_nom as tx_nomcli, a1.co_ref, a1.fe_ref, a1.tx_nom as tx_nomref, a1.tx_matproref, a1.tx_tel, a1.tx_tie, a1.tx_cupcre, ";
                strSQL+=" a1.tx_placre, a1.tx_forpag, a1.st_pro, a1.tx_cal, a1.tx_inf, a1.tx_carinf, a1.tx_obs1, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod ";
                strSQL+=" FROM tbh_refcomsolcre as a1 ";
                ///strSQL+=" INNER JOIN tbh_solcre as a2 ON (a1.co_emp=a2.co_emp and a1.co_sol=a2.co_sol) ";
                strSQL+=" INNER JOIN tbh_solcre as a2 ON (a1.co_emp=a2.co_emp and a1.co_sol=a2.co_sol and a1.co_his=a2.co_his) ";
                strSQL+=" INNER JOIN tbm_cli    as a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli) ";
                                
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL+=" WHERE a2.co_emp = " + rstCabHis.getString("co_emp") + "";
                    strSQL+=" AND a3.co_cli = " + rstCabHis.getString("co_cli") + "";
                    strSQL+=" AND a2.co_sol = " + rstCabHis.getString("co_sol") + "";
                    strSQL+=" AND a2.co_his = " + rstCabHis.getString("co_his") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_ref ";
                }
                else
                {
                    strSQL+=" INNER JOIN tbr_cliloc AS a4 ON (a3.co_emp=a4.co_emp and a3.co_cli=a4.co_cli)";
                    strSQL+=" WHERE a2.co_emp = " + rstCabHis.getString("co_emp") + "";
                    strSQL+=" AND a4.co_loc = " + rstCabHis.getString("co_loc") + "";
                    strSQL+=" AND a3.co_cli = " + rstCabHis.getString("co_cli") + "";
                    strSQL+=" AND a2.co_sol = " + rstCabHis.getString("co_sol") + "";
                    strSQL+=" AND a2.co_his = " + rstCabHis.getString("co_his") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_ref ";
                }
                
                rstRefComHis=stmRefComHis.executeQuery(strSQL);
                while (rstRefComHis.next())
                {                    
                    vecCabRefCom=new Vector();
                    vecCabRefCom.add(INT_TBL_REF_COM_LIN, "");
                    vecCabRefCom.add(INT_TBL_REF_COM_SOL, rstRefComHis.getString("co_sol"));
                    vecCabRefCom.add(INT_TBL_REF_COM_CLI, rstRefComHis.getString("co_cli"));
                    vecCabRefCom.add(INT_TBL_REF_COM_REG, rstRefComHis.getString("co_ref"));
                    vecCabRefCom.add(INT_TBL_REF_COM_NOM, rstRefComHis.getString("tx_nomref"));
                    vecCabRefCom.add(INT_TBL_REF_COM_BUT, null);
                    vecDatRefCom.add(vecCabRefCom);
                }
            }
            ///limpiar y cerrar el resulset///
            rstRefComHis.close();
            stmRefComHis.close();
            conRefComHis.close();
            rstRefComHis=null;
            stmRefComHis=null;
            conRefComHis=null;
            
            //Asignar vectores al modelo.
            objTblModRefCom.setData(vecDatRefCom);
            tblRefCom.setModel(objTblModRefCom);
            //vecDatRefCom.clear();
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetRefBanHis()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {
            conRefBanHis=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conRefBanHis!=null)
            {
                stmRefBanHis=conRefBanHis.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);      
                strSQL="";                
                strSQL+="SELECT a1.co_emp, a1.co_sol, a2.co_his, a3.co_cli, a3.tx_nom as tx_nomcli, a1.co_ref, a1.fe_ref, a1.tx_nom as tx_nombco, a1.tx_age, a1.tx_oficta, a1.tx_numcta, a1.tx_duecta, ";
                strSQL+=" a1.co_ciuapecta, a4.tx_deslar, a1.fe_apecta, a1.tx_salprocta, a1.st_pro, a1.st_credir, a1.st_creind, a1.tx_moncredir, a1.tx_moncreind, a1.tx_inf, a1.tx_carinf, ";
                strSQL+=" a1.tx_docdig, a1.tx_obs1, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod ";
                strSQL+=" FROM tbh_refbansolcre as a1 ";
                ///strSQL+=" INNER JOIN tbh_solcre as a2 ON (a1.co_emp=a2.co_emp and a1.co_sol=a2.co_sol) ";
                strSQL+=" INNER JOIN tbh_solcre as a2 ON (a1.co_emp=a2.co_emp and a1.co_sol=a2.co_sol and a1.co_his=a2.co_his) ";
                strSQL+=" INNER JOIN tbm_cli    as a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli) ";
                strSQL+=" INNER JOIN tbm_ciu    as a4 ON (a1.co_ciuapecta=a4.co_ciu) ";
                
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL+=" WHERE a2.co_emp = " + rstCabHis.getString("co_emp") + "";
                    strSQL+=" AND a3.co_cli = " + rstCabHis.getString("co_cli") + "";
                    strSQL+=" AND a2.co_sol = " + rstCabHis.getString("co_sol") + "";
                    strSQL+=" AND a2.co_his = " + rstCabHis.getString("co_his") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_ref ";
                }
                else
                {
                    strSQL+=" INNER JOIN tbr_cliloc AS a5 ON (a3.co_emp=a5.co_emp and a3.co_cli=a5.co_cli)";
                    strSQL+=" WHERE a2.co_emp = " + rstCabHis.getString("co_emp") + "";
                    strSQL+=" AND a5.co_loc = " + rstCabHis.getString("co_loc") + "";
                    strSQL+=" AND a3.co_cli = " + rstCabHis.getString("co_cli") + "";
                    strSQL+=" AND a2.co_sol = " + rstCabHis.getString("co_sol") + "";
                    strSQL+=" AND a2.co_his = " + rstCabHis.getString("co_his") + "";
                    strSQL+=" AND a1.st_reg <> 'E'";
                    strSQL+=" ORDER BY a1.co_ref ";
                }
                
                rstRefBanHis=stmRefBanHis.executeQuery(strSQL);
                while (rstRefBanHis.next())
                {                    
                    vecCabRefBan=new Vector();
                    vecCabRefBan.add(INT_TBL_REF_BAN_LIN, "");
                    vecCabRefBan.add(INT_TBL_REF_BAN_SOL, rstRefBanHis.getString("co_sol"));
                    vecCabRefBan.add(INT_TBL_REF_BAN_CLI, rstRefBanHis.getString("co_cli"));
                    vecCabRefBan.add(INT_TBL_REF_BAN_REG, rstRefBanHis.getString("co_ref"));
                    vecCabRefBan.add(INT_TBL_REF_BAN_NOM, rstRefBanHis.getString("tx_nombco"));
                    vecCabRefBan.add(INT_TBL_REF_BAN_BUT, null);
                    vecDatRefBan.add(vecCabRefBan);
                }
            }
            ///limpiar y cerrar el resulset///
            rstRefBanHis.close();
            stmRefBanHis.close();
            conRefBanHis.close();
            rstRefBanHis=null;
            stmRefBanHis=null;
            conRefBanHis=null;
            
            //Asignar vectores al modelo.
            objTblModRefBan.setData(vecDatRefBan);
            tblRefBan.setModel(objTblModRefBan);
            //vecDatRefBan.clear();
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetDocDigHis()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {
            conDocDigHis=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conDocDigHis!=null)
            {
                stmDocDigHis=conDocDigHis.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);               
                strSQL="";
                
                strSQL+="SELECT a1.co_emp, a1.co_sol, a2.co_his, a3.co_cli, a3.tx_nom as tx_nomcli, a1.co_ref, a1.fe_ref, a1.tx_nom as tx_nomref, a1.tx_matproref, a1.tx_tel, a1.tx_tie, a1.tx_cupcre, ";
                strSQL+=" a1.tx_placre, a1.tx_forpag, a1.st_pro, a1.tx_cal, a1.tx_inf, a1.tx_carinf, a1.tx_obs1, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod ";
                strSQL+=" FROM tbh_refcomsolcre as a1 ";
                ///strSQL+=" INNER JOIN tbh_solcre as a2 ON (a1.co_emp=a2.co_emp and a1.co_sol=a2.co_sol) ";
                strSQL+=" INNER JOIN tbh_solcre as a2 ON (a1.co_emp=a2.co_emp and a1.co_sol=a2.co_sol and a1.co_his=a2.co_his) ";
                strSQL+=" INNER JOIN tbm_cli    as a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli) ";
                strSQL+=" WHERE a2.co_emp = " + rstCabHis.getString("co_emp") + "";
                strSQL+=" AND a3.co_cli = " + rstCabHis.getString("co_cli") + "";
                strSQL+=" AND a2.co_sol = " + txtSolCre.getText() + "";
                strSQL+=" AND a2.co_his = " + txtCodHisSolCre.getText() + "";
                
                rstDocDigHis=stmDocDigHis.executeQuery(strSQL);
                while (rstDocDigHis.next())
                {                    
//                    vecCabCon=new Vector();
//                    vecCabCon.add(INT_TBL_CON_LIN, "");
//                    vecCabCon.add(INT_TBL_CON_CLI, rst.getString("co_cli"));
//                    vecCabCon.add(INT_TBL_CON_REG, rst.getString("co_reg"));
//                    vecCabCon.add(INT_TBL_CON_NOM, rst.getString("tx_nomcon"));
//                    vecCabCon.add(INT_TBL_CON_BUT, null);
//                    vecDatCon.add(vecCabCon);
                }
            }
            ///limpiar y cerrar el resulset///
            rstDocDigHis.close();
            stmDocDigHis.close();
            conDocDigHis.close();
            rstDocDigHis=null;
            stmDocDigHis=null;
            conDocDigHis=null;
            
            //Asignar vectores al modelo.
            objTblModCon.setData(vecDatCon);
            tblCon.setModel(objTblModCon);
            ///vecDatAcc.clear();
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    /////////////////////////////////////////////////////////////////////////////////
        
    /**
     * Esta funciï¿½n permite insertar un registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (insertarSolCre())
                {
                    if(actualizarDatCli())
                    {
                        if(actualizarDatAcc())
                        {
                            con.commit();
                            blnRes=true;
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
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;        
    }
    

    private boolean insertarSolCre()
    {
        boolean blnRes=true;
        int ultcoddoc;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                ///obtener el ultimo registro ingresado///
                ultcoddoc = ultCodDoc();
                ultcoddoc++;                
                txtSolCre.setText(String.valueOf(ultcoddoc));
                
                ///Fechas///
                strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                if(strAux == null)
                    strAux="";
                
                String codforpag=txtCodForPag.getText();
                if(codforpag.equals(""))
                    codforpag=null;                
                
                String monsol=txtMonSol.getText();
                if(monsol.equals(""))
                    monsol=null;
/*
////----campos de la tabla tbm_solcre-----------//////
co_emp, co_sol, co_cli, fe_sol, co_forpag, nd_moncre, st_anasol, fe_anasol,
co_usranasol, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod
*/
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_solcre";
                strSQL+=" (co_emp, co_sol, co_cli, fe_sol, co_forpag, nd_moncre, st_anasol, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod )";
                strSQL+=" VALUES (";
                strSQL+=" "  + objParSis.getCodigoEmpresa();    ///co_emp
                strSQL+=", " + ultcoddoc;                       ///co_sol
                strSQL+=", " + txtCodCli.getText();             ///co_cli
                strSQL+=", '" + strAux + "'";                 ///fe_sol
                ///strSQL+="," + txtCodForPag.getText()==null?"":txtCodForPag.getText()+" ";       ///co_forpag
                ///strSQL+="," + txtMonSol.getText()==null?"":txtMonSol.getText()+" ";              ///nd_moncre
                strSQL+=", " + codforpag;///co_forpag
                strSQL+=", " + monsol;///nd_moncre
                strSQL+=", 'N' " ;                              ///st_anasol
                strSQL+=", '" + txaObsSolCre1.getText()+ "'";   ///tx_obs1
                strSQL+=", '" + txaObsSolCre2.getText()+ "'";   ///tx_obs2                
                strSQL+=", 'A'";                                ///st_reg
                strSQL+=", '" + strFecSis + "'";                ///fe_ing
                strSQL+=", null ";                              ///fe_ultmod
                strSQL+=", " + objParSis.getCodigoUsuario();    ///co_usring
                strSQL+=", null ";                              ///co_usrmod
                strSQL+=")";
                stm.executeUpdate(strSQL);                
                datFecAux=null;
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
//    /**
//     * Esta funciï¿½n permite insertar un registro seleccionado.
//     * @return true: Si se pudo cargar el registro.
//     * <BR>false: En el caso contrario.
//     */
//    private boolean insertarRegHis()
//    {
//        boolean blnRes=false;
//        try
//        {
//            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            con.setAutoCommit(false);
//            if (con!=null)
//            {
////                if (insertarSolCre())
////                {
////                    if(actualizarDatCli())
////                    {
////                        if(actualizarDatAcc())
////                        {
////                            con.commit();
////                            blnRes=true;
////                        }
////                        else
////                            con.rollback();
////                    }
////                    else
////                        con.rollback();
////                }
////                else
////                    con.rollback();
//            }
//            con.close();
//            con=null;
//        }
//        catch (java.sql.SQLException e)
//        {
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;        
//    }
    
    
    /**
     * Esta funciï¿½n permite eliminar un registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (eliminarCabReg())
                {
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean eliminarCabReg()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_solcre";
                strSQL+=" SET ";
                strSQL+=" st_reg = 'E'";
                strSQL+=", fe_ultmod = '" + strFecSis + "'";
                strSQL+=", co_usrmod = '" + objParSis.getCodigoUsuario() + "'";
//                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
//                strSQL+=" AND co_sol=" + rstCab.getString("co_sol");
                strSQL+=" WHERE co_emp =" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_sol =" + txtSolCre.getText();
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n permite anular un registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (anularCabReg())
                {
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean anularCabReg()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_solcre";
                strSQL+=" SET ";
                strSQL+=" st_reg = 'I'";
                strSQL+=", fe_ultmod = '" + strFecSis + "'";
                strSQL+=", co_usrmod = " + objParSis.getCodigoUsuario() + "";
//                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
//                strSQL+=" AND co_sol=" + rstCab.getString("co_sol");
                ///strSQL+=" WHERE co_emp =" + objParSis.getCodigoEmpresa();
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE co_emp=" + CODEMP;
                else
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                
                strSQL+=" AND co_sol =" + txtSolCre.getText();
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    
    /**
     * Esta funciï¿½n actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (actualizarSolCre())
                {
                    if (actualizarDatCli())
                    {
                        if(actualizarDatAcc())
                        {
//                            if (actualizarDatCre())
//                            {
//                                con.commit();
//                                blnRes=true;
//                            }
//                            else
//                                con.rollback();
                            
                            if (eliminarDetAcc())
                            {
                                if (insertarDetAcc())
                                {
                                    con.commit();
                                    blnRes=true;
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
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean actualizarSolCre()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
/*
////----campos de la tabla tbm_solcre-----------//////
co_emp, co_sol, co_cli, fe_sol, co_forpag, nd_moncre, st_anasol, fe_anasol,
co_usranasol, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod
*/                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_solcre";
                strSQL+=" SET ";
                
                if(txtCodForPag.getText().equals(""))
                    strSQL+=" co_forpag = null";
                else
                    strSQL+=" co_forpag = " + txtCodForPag.getText() + "";
                
                if(txtMonSol.getText().equals(""))
                    strSQL+=", nd_moncre = null";
                else
                    strSQL+=", nd_moncre = " + txtMonSol.getText() + "";
                
                strSQL+=", tx_obs1 = '" + txaObsSolCre1.getText() + "'";
                strSQL+=", tx_obs2 = '" + txaObsSolCre2.getText() + "'";
                strSQL+=", fe_ultmod = '" + strFecSis + "'";
                strSQL+=", co_usrmod = " + objParSis.getCodigoUsuario() + "";
                ///strSQL+=" WHERE co_emp = " + objParSis.getCodigoEmpresa();
                
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE co_emp=" + CODEMP;
                else
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                
                strSQL+=" AND co_sol = " + txtSolCre.getText();
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean actualizarDatCli()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cli";
                strSQL+=" SET ";
                strSQL+=" tx_dir = '" + txtDir.getText() + "'";
                strSQL+=", tx_refubi = '" + txtRefUbi.getText() + "'";
                strSQL+=", tx_tel1 = '" + txtTelCon1.getText() + "'";
                strSQL+=", tx_tel2 = '" + txtTelCon2.getText() + "'";
                strSQL+=", tx_tel3 = '" + txtTelCon3.getText() + "'";
                strSQL+=", tx_tel = '" + txtTelf.getText() + "'";
                strSQL+=", tx_fax = '" + txtFax.getText() + "'";
                strSQL+=", tx_cas = '" + txtCasilla.getText() + "'";
                strSQL+=", tx_corele = '" + txtEmail.getText() + "'";
                strSQL+=", tx_dirweb = '" + txtWeb.getText() + "'";
                strSQL+=", co_ciu = " + objUti.codificar(txtCodCiu.getText()) + "";
                strSQL+=", co_zon = " + objUti.codificar(txtCodZon.getText()) + "";
                strSQL+=", co_ven = " + objUti.codificar(txtCodVen.getText()) + "";
                strSQL+=", tx_obscxc = '" + txaObsCxC.getText() + "'";
                strSQL+=", tx_obsinfburcre = '" + txaObsInfBurCre.getText() + "'";
                strSQL+=", fe_ultmod = '" + strFecSis + "'";
                strSQL+=", co_usrmod = " + objParSis.getCodigoUsuario() + "";
                ///strSQL+=" WHERE co_emp = " + objParSis.getCodigoEmpresa();
                
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE co_emp=" + CODEMP;
                else
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                
                strSQL+=" AND co_cli = " + txtCodCli.getText();
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean actualizarDatAcc()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cli";
                strSQL+=" SET ";
                strSQL+=" tx_idePro = '" + txtCedPasPro.getText() + "'";
                strSQL+=", tx_nomPro = '" + txtNomPro.getText() + "'";
                strSQL+=", tx_dirPro = '" + txtDomPro.getText() + "'";
                strSQL+=", tx_telPro = '" + txtTelPro.getText() + "'";
                strSQL+=", tx_nacPro = '" + txtNacPro.getText() + "'";
                boolean fec = dtpFecConEmp.isFecha();
                if(fec == false)
                    strAux=null;
                else
                    strAux=objUti.formatearFecha(dtpFecConEmp.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                
                if(strAux == null || strAux == "")
                {
                    strSQL+=", fe_conEmp = " + strAux;
                }
                else
                {
                    strSQL+=", fe_conEmp = '" + strAux + "'";
                }
                strSQL+=", tx_tipActEmp = '" + txtTipActEmp.getText() + "'";
                strSQL+=", tx_obsPro = '" + txaObs1.getText() + "'";                
//                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
//                strSQL+=" AND co_cli=" + rstCab.getString("co_cli");
                ///strSQL+=" WHERE co_emp = " + objParSis.getCodigoEmpresa();
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE co_emp=" + CODEMP;
                else
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                
                strSQL+=" AND co_cli = " + txtCodCli.getText();
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     *Esta funcion elimina la cabecera del documento en la base de datos en la tabla tbm_cabpag
     *@return true: si elimina
     *<BR>false: caso contarrio
     */
    private boolean eliminarDetAcc()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE FROM tbm_accEmpCli";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_cli=" + rstCab.getString("co_cli");
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n permite Insertar el  detalle de Accionistas por cada cliente.
     * @return true: Si se pudo Insertar el detalle del cliente.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDetAcc()
    {
        int intCodEmp, intCodLoc, i, j;
        String strCodTipDoc, strCodDoc, strNatDoc="";
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();

                for (i=0;i<objTblModAcc.getRowCountTrue();i++)
                {                    
                    strSQL="";
                    strSQL+="INSERT INTO tbm_accEmpCli (co_emp, co_cli, co_reg, tx_nom ) ";
                    strSQL+=" VALUES (";
                    strSQL+="" + rstCab.getString("co_emp");
                    strSQL+=", " + rstCab.getString("co_cli");
                    ///strSQL+=", " + (i+1);
                    strSQL+=", " + (i+1);
                    strSQL+=", '" + objTblModAcc.getValueAt(i,INT_TBL_DAT_NOM_ACC);
                    strSQL+=" ')";
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean actualizarDatCre()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                //Armar la sentencia SQL.
                strSQL="";
//                strSQL+="UPDATE tbm_solcre";
//                strSQL+=" SET ";
//                strSQL+=" fe_ultmod = '" + strFecSis + "'";
//                strSQL+=", co_usrmod = '" + objParSis.getCodigoUsuario() + "'";
//                strSQL+=" WHERE co_emp =" + rstCab.getString("co_emp");
//                strSQL+=" AND co_sol =" + rstCab.getString("co_sol");
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarRegCreCon()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (actualizarCliCreCon())
                {
                    if (actualizarSolCreCon())
                    {
                        con.commit();
                        blnRes=true;
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean actualizarCliCreCon()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
/*
////----campos de la tabla tbm_solcre-----------//////
co_emp, co_sol, co_cli, fe_sol, co_forpag, nd_moncre, st_anasol, fe_anasol,
co_usranasol, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod
*/
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cli";
                strSQL+=" SET ";
                
                if(txtForPagCreCon.getText().equals(""))
                    strSQL+=" co_forpag = null";
                else
                    strSQL+=" co_forpag = " + txtCodForPagCreCon.getText() + "";
                
                if(txtMonApr.getText().equals(""))
                    strSQL+=", nd_moncre = null";
                else
                    strSQL+=", nd_moncre = " + txtMonApr.getText() + "";
                
                
//                strSQL+=" co_forpag = " + txtCodForPagCreCon.getText() + "";
//                strSQL+=", nd_moncre = " + txtMonApr.getText() + "";
                
                strSQL+=", fe_ultmod = '" + strFecSis + "'";
                strSQL+=", co_usrmod = " + objParSis.getCodigoUsuario() + "";
                ///strSQL+=", fe_proactdat = '" + dtePckPag.getText() + "'";
                strSQL+=", fe_proactdat = '" + objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";                
                ///strSQL+=" WHERE co_emp = " + objParSis.getCodigoEmpresa();
                
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE co_emp=" + CODEMP;
                else
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                
                strSQL+=" AND co_cli = " + txtCodCli.getText();
                stm.executeUpdate(strSQL);
                
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_forpagcli";
                strSQL+=" SET ";
                
                if(txtForPagCreCon.getText().equals(""))
                    strSQL+=" co_forpag = null";
                else
                    strSQL+=" co_forpag = " + txtCodForPagCreCon.getText() + "";
                
                strSQL+=", st_reg = 'S' " ;
                
                
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE co_emp=" + CODEMP;
                else
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                
                strSQL+=" AND co_cli = " + txtCodCli.getText();
                
                stm.executeUpdate(strSQL);
                
                
                stm.close();
                stm=null;
                datFecAux=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean actualizarSolCreCon()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
/*
////----campos de la tabla tbm_solcre-----------//////
co_emp, co_sol, co_cli, fe_sol, co_forpag, nd_moncre, st_anasol, fe_anasol,
co_usranasol, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod
*/                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_solcre";
                strSQL+=" SET ";
                strSQL+=" st_anasol = 'A' ";                
                strSQL+=", fe_anasol = '" + strFecSis + "'";
                strSQL+=", co_usranasol = " + objParSis.getCodigoUsuario() + "";
                ///strSQL+=" WHERE co_emp = " + objParSis.getCodigoEmpresa();
                
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE co_emp=" + CODEMP;
                else
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                
                strSQL+=" AND co_sol = " + txtSolCre.getText();
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        int intCodEmpGrp=0, intCodEmp=0;
        int intCodMnu=0;
        
        try 
        {   
            objUti=new ZafUtil();
            
            objTooBar=new mitoolbar(this);
            panBar.add(objTooBar);//llama a la barra de botones
            
            //titulo de la ventana
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.2");
            lblTit.setText(strAux);
            
            lblCodHisSolCre.setVisible(false);
            txtCodHisSolCre.setVisible(false);
            
            //////////////////////////////////////////////////////
            intCodEmpGrp = objParSis.getCodigoEmpresaGrupo();
            intCodEmp = objParSis.getCodigoEmpresa();
            intCodMnu = objParSis.getCodigoMenu();
            INTCODMNU=intCodMnu;
            
            objTooBar.setVisibleAceptar(true);
            objTooBar.setVisibleCancelar(true);
            objTooBar.setVisibleModificar(true);
            objTooBar.setVisibleConsultar(true);

            if(intCodEmpGrp == intCodEmp)
                objTooBar.setVisibleInsertar(false);
            else
                objTooBar.setVisibleInsertar(true);
            
            
            if(INTCODMNU == 1014)
            {
                objTooBar.setVisibleAceptar(true);
                objTooBar.setVisibleCancelar(true);
                objTooBar.setVisibleConsultar(true);
                objTooBar.setVisibleInsertar(false);
                objTooBar.setVisibleModificar(false);
                lblCodHisSolCre.setVisible(true);
                txtCodHisSolCre.setVisible(true);
                txtCodHisSolCre.setBackground(objParSis.getColorCamposSistema());
            }
            /////////////////////////////////////////////////////
        
            //Configurar Ventana de Consulta de Cliente//
            configurarVenConCli();
            
            //Configurar Ventana de Consulta de Forma de Pago//
            configurarVenConForPag();
            configurarVenConForPagCreCon();
            
            ////fecha de referencia de solicitud///
//            dtpFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
            dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecDoc.setText("");
            panGenSolCre.add(dtpFecDoc);
            dtpFecDoc.setBounds(520, 5, 100, 20);
            
            ////fecha de constitucion de la empresa///
//            dtpFecConEmp = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
            dtpFecConEmp.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecConEmp.setText("");
            panCabDat.add(dtpFecConEmp);
            dtpFecConEmp.setBounds(215, 126, 100, 20);
            
            ////fecha de Ultima Actualizacion de Datos///
//            dtpFecUltAct = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
            dtpFecUltAct.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecUltAct.setText("");
            panActDat.add(dtpFecUltAct);
            dtpFecUltAct.setBounds(190, 25, 100, 20);
            
            
            ////fecha de Ultima Actualizacion de Datos///
//            dtpFecProAct = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
            dtpFecProAct.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecProAct.setText("");
            panActDat.add(dtpFecProAct);
            dtpFecProAct.setBounds(190, 74, 100, 20);

            //asigno el color de fondo de los campos
            txtSolCre.setBackground(objParSis.getColorCamposSistema());
            txtCodCli.setBackground(objParSis.getColorCamposObligatorios());
            txtNomCli.setBackground(objParSis.getColorCamposObligatorios());
            txtDesTipPer.setBackground(objParSis.getColorCamposObligatorios());
            txtNomTipPer.setBackground(objParSis.getColorCamposObligatorios());
            txtIde.setBackground(objParSis.getColorCamposObligatorios());
            txtCodTipPer.setVisible(false);
            txtCodCiu.setVisible(false);
            txtCodVen.setVisible(false);
            txtCodZon.setVisible(false);
            txtCodForPag.setVisible(false);
            txtCodForPagCreCon.setVisible(false);
            
            ///////TAB DOC.DIGITALIZADOS///////////
            tblDocDig.setVisible(false);
            
            
            ///////TAB DATOS DE CREDITOS//////////
            if(a==0)
            {
                txtMonApr.setEnabled(false);
                txtForPagCreCon.setEnabled(false);
                butForPagCreCon.setEnabled(false);
                txtProAct.setEnabled(false);
                dtpFecUltAct.setEnabled(false);
                dtpFecProAct.setEnabled(false);
            }
            
            if(BAN>0)
            {
                txtMonApr.requestFocus();
            }
            
            
            //////////////////////////// DATOS PARA EL JTABLE DE ACCIONISTAS /////////////////////////
            
            //Configurar JTable: Establecer el modelo para Tabla de Credito            
            vecDatAcc=new Vector();
            vecCabAcc=new Vector(6);
            vecCabAcc.clear();
            vecCabAcc.add(INT_TBL_DAT_LIN,"");
            vecCabAcc.add(INT_TBL_DAT_NOM_ACC,"Nom. Acc.");
            vecCabAcc.add(INT_TBL_DAT_COD_CLI,"Cod. Cli.");
            vecCabAcc.add(INT_TBL_DAT_BUT_ACC,"...");

            objTblModAcc=new ZafTblMod();
            objTblModAcc.setHeader(vecCabAcc);

            tblAcc.setModel(objTblModAcc);
            
            //Configurar JTable: Establecer tipo de selecciï¿½n.
            tblAcc.setRowSelectionAllowed(true);
            tblAcc.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumPnd=new ZafColNumerada(tblAcc,INT_TBL_DAT_LIN);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblAcc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblAcc.getColumnModel();
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_LIN).setPreferredWidth(35);
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_NOM_ACC).setPreferredWidth(600);
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_BUT_ACC).setPreferredWidth(20);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblAcc.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_COD_CLI).setWidth(0);
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_COD_CLI).setMaxWidth(0);
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_COD_CLI).setMinWidth(0);
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(0);
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_COD_CLI).setResizable(false);
            
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_BUT_ACC).setWidth(0);
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_BUT_ACC).setMaxWidth(0);
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_BUT_ACC).setMinWidth(0);
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_BUT_ACC).setPreferredWidth(0);
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_BUT_ACC).setResizable(false);
            
            //para hacer editable las celdas
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_NOM_ACC);
            objTblModAcc.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblAcc);            
            setEditable(true);
            
            ////////////////////////////////
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblAcc.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Editor de bï¿½squeda.
            objTblBus=new ZafTblBus(tblAcc);
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ACC).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    abrirFrm();
                }
            });
            
            //Configurar JTable: Modo de operaciï¿½n del JTable.
            objTblModAcc.setModoOperacion(objTblModAcc.INT_TBL_EDI);           
            

            ///////////////////////////////
            
            //////////////////////////// DATOS PARA EL JTABLE DE CONTACTOS /////////////////////////
            configurarTblCon();
            configurarTblObs();
            configurarTblRefCom();
            configurarTblRefBan();
            ///configurarTblDocDig();
            
        }
        catch(Exception e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
        //////////////////////////
    }
    
        
    /** Configurar JTable de Contactos. */
    private boolean configurarTblCon()
    {
        boolean blnRes=true;
        
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            
            //Configurar objetos.
            
            //Configurar JTable: Establecer el modelo.
            vecDatCon=new Vector();    //Almacena los datos
            vecCabCon=new Vector(8);  //Almacena las cabeceras
            vecCabCon.clear();
            vecCabCon.add(INT_TBL_CON_LIN,"");
            vecCabCon.add(INT_TBL_CON_CLI,"Cod.Cli.");
            vecCabCon.add(INT_TBL_CON_REG,"Cod.Reg.");
            vecCabCon.add(INT_TBL_CON_NOM,"Nombre de Contacto");
            vecCabCon.add(INT_TBL_CON_BUT,"...");
            
            objTblModCon=new ZafTblMod();
            objTblModCon.setHeader(vecCabCon);
            tblCon.setModel(objTblModCon);

            //Configurar JTable: Establecer tipo de selecciï¿½n.
            tblCon.setRowSelectionAllowed(true);
            tblCon.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer la fila de cabecera.
            objColNumCon=new ZafColNumerada(tblCon,INT_TBL_CON_LIN);

            //Configurar JTable: Establecer el menï¿½ de contexto.
            objPopMnuCon=new ZafTblPopMnu(tblCon);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblCon.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblCon.getColumnModel();
            tblCon.getColumnModel().getColumn(INT_TBL_CON_LIN).setPreferredWidth(35);
            tblCon.getColumnModel().getColumn(INT_TBL_CON_CLI).setPreferredWidth(10);
            tblCon.getColumnModel().getColumn(INT_TBL_CON_REG).setPreferredWidth(10);
            tblCon.getColumnModel().getColumn(INT_TBL_CON_NOM).setPreferredWidth(600);
            tblCon.getColumnModel().getColumn(INT_TBL_CON_BUT).setPreferredWidth(20);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tblCon.getColumnModel().getColumn(INT_TBL_CON_BUT).setResizable(false);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblCon.getTableHeader().setReorderingAllowed(false);
            
                  
            //Configurar JTable: Ocultar columnas del sistema.            
            tblCon.getColumnModel().getColumn(INT_TBL_CON_CLI).setWidth(0);
            tblCon.getColumnModel().getColumn(INT_TBL_CON_CLI).setMaxWidth(0);
            tblCon.getColumnModel().getColumn(INT_TBL_CON_CLI).setMinWidth(0);
            tblCon.getColumnModel().getColumn(INT_TBL_CON_CLI).setPreferredWidth(0);
            tblCon.getColumnModel().getColumn(INT_TBL_CON_CLI).setResizable(false);
            
            tblCon.getColumnModel().getColumn(INT_TBL_CON_REG).setWidth(0);
            tblCon.getColumnModel().getColumn(INT_TBL_CON_REG).setMaxWidth(0);
            tblCon.getColumnModel().getColumn(INT_TBL_CON_REG).setMinWidth(0);
            tblCon.getColumnModel().getColumn(INT_TBL_CON_REG).setPreferredWidth(0);
            tblCon.getColumnModel().getColumn(INT_TBL_CON_REG).setResizable(false);
            

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblCon.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_CON_BUT);
            objTblModCon.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Editor de bï¿½squeda.
            objTblBus=new ZafTblBus(tblCon);
            
            //Renderizo para boton
            objTblCelRenBut=new ZafTblCelRenBut();
            tblCon.getColumnModel().getColumn(INT_TBL_CON_BUT).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_CON_BUT).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    abrirFrm();
                }
            });
            
            //Configurar JTable: Modo de operaciï¿½n del JTable.
            objTblModCon.setModoOperacion(objTblModCon.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
   //////////-------------///////////////---------------////////////////-----------------////////////////--------------////////////         

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /** Configurar JTable de Referencias Comerciales. */
    private boolean configurarTblObs()
    {
        boolean blnRes=true;
        
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            
            //Configurar objetos.
            
            //Configurar JTable: Establecer el modelo.
            vecDatObs=new Vector();    //Almacena los datos
            vecCabObs=new Vector(8);  //Almacena las cabeceras
            vecCabObs.clear();
            vecCabObs.add(INT_TBL_OBS_LIN,"");
            vecCabObs.add(INT_TBL_OBS_FEC,"Fec.Obs.");
            vecCabObs.add(INT_TBL_OBS_DES,"Descripcion");
            vecCabObs.add(INT_TBL_OBS_BUT,"...");
            
            objTblModObs=new ZafTblMod();
            objTblModObs.setHeader(vecCabObs);
            tblObs.setModel(objTblModObs);

            //Configurar JTable: Establecer tipo de selecciï¿½n.
            tblObs.setRowSelectionAllowed(true);
            tblObs.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer la fila de cabecera.
            objColNumCon=new ZafColNumerada(tblObs,INT_TBL_OBS_LIN);

            //Configurar JTable: Establecer el menï¿½ de contexto.
            objPopMnuCon=new ZafTblPopMnu(tblObs);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblObs.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblObs.getColumnModel();
            tblObs.getColumnModel().getColumn(INT_TBL_OBS_LIN).setPreferredWidth(35);
            tblObs.getColumnModel().getColumn(INT_TBL_OBS_FEC).setPreferredWidth(90);
            tblObs.getColumnModel().getColumn(INT_TBL_OBS_DES).setPreferredWidth(600);
            tblObs.getColumnModel().getColumn(INT_TBL_OBS_BUT).setPreferredWidth(20);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tblObs.getColumnModel().getColumn(INT_TBL_OBS_BUT).setResizable(false);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblObs.getTableHeader().setReorderingAllowed(false);            

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblObs.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_OBS_BUT);
            objTblModObs.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Editor de bï¿½squeda.
            objTblBus=new ZafTblBus(tblObs);
            
            //Renderizo para boton
            objTblCelRenBut=new ZafTblCelRenBut();
            tblObs.getColumnModel().getColumn(INT_TBL_OBS_BUT).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_OBS_BUT).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    ///abrirFrm();
                }
            });
            
            //Configurar JTable: Modo de operaciï¿½n del JTable.
            objTblModObs.setModoOperacion(objTblModObs.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
   //////////-------------///////////////---------------////////////////-----------------////////////////--------------////////////         

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /** Configurar JTable de Referencias Comerciales. */
    private boolean configurarTblRefCom()
    {
        boolean blnRes=true;
        
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            
            //Configurar objetos.
            
            //Configurar JTable: Establecer el modelo.
            vecDatRefCom=new Vector();    //Almacena los datos
            vecCabRefCom=new Vector(8);  //Almacena las cabeceras
            vecCabRefCom.clear();
            vecCabRefCom.add(INT_TBL_REF_COM_LIN,"");
            vecCabRefCom.add(INT_TBL_REF_COM_SOL,"Cod.Sol.");
            vecCabRefCom.add(INT_TBL_REF_COM_CLI,"Cod.Cli.");
            vecCabRefCom.add(INT_TBL_REF_COM_REG,"Cod.Reg.");
            vecCabRefCom.add(INT_TBL_REF_COM_NOM,"Referencia Comercial");
            vecCabRefCom.add(INT_TBL_REF_COM_BUT,"...");
            
            objTblModRefCom=new ZafTblMod();
            objTblModRefCom.setHeader(vecCabRefCom);
            tblRefCom.setModel(objTblModRefCom);

            //Configurar JTable: Establecer tipo de selecciï¿½n.
            tblRefCom.setRowSelectionAllowed(true);
            tblRefCom.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer la fila de cabecera.
            objColNumCon=new ZafColNumerada(tblRefCom,INT_TBL_REF_COM_LIN);

            //Configurar JTable: Establecer el menï¿½ de contexto.
            objPopMnuCon=new ZafTblPopMnu(tblRefCom);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblRefCom.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblRefCom.getColumnModel();
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_LIN).setPreferredWidth(35);
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_SOL).setPreferredWidth(10);
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_CLI).setPreferredWidth(10);
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_REG).setPreferredWidth(10);
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_NOM).setPreferredWidth(600);
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_BUT).setPreferredWidth(20);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_BUT).setResizable(false);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblRefCom.getTableHeader().setReorderingAllowed(false);
            
                  
            //Configurar JTable: Ocultar columnas del sistema.
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_SOL).setWidth(0);
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_SOL).setMaxWidth(0);
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_SOL).setMinWidth(0);
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_SOL).setPreferredWidth(0);
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_SOL).setResizable(false);
            
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_CLI).setWidth(0);
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_CLI).setMaxWidth(0);
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_CLI).setMinWidth(0);
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_CLI).setPreferredWidth(0);
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_CLI).setResizable(false);
            
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_REG).setWidth(0);
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_REG).setMaxWidth(0);
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_REG).setMinWidth(0);
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_REG).setPreferredWidth(0);
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_REG).setResizable(false);
            

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblRefCom.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_REF_COM_BUT);
            objTblModRefCom.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Editor de bï¿½squeda.
            objTblBus=new ZafTblBus(tblRefCom);
            
            //Renderizo para boton
            objTblCelRenBut=new ZafTblCelRenBut();
            tblRefCom.getColumnModel().getColumn(INT_TBL_REF_COM_BUT).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_REF_COM_BUT).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    abrirFrm();
                }
            });
            
            //Configurar JTable: Modo de operaciï¿½n del JTable.
            objTblModRefCom.setModoOperacion(objTblModRefCom.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
   //////////-------------///////////////---------------////////////////-----------------////////////////--------------////////////         

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /** Configurar JTable de Referencias Bancarias. */
    private boolean configurarTblRefBan()
    {
        boolean blnRes=true;
        
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            
            //Configurar objetos.
            
            //Configurar JTable: Establecer el modelo.
            vecDatRefBan=new Vector();    //Almacena los datos
            vecCabRefBan=new Vector(8);  //Almacena las cabeceras
            vecCabRefBan.clear();
            vecCabRefBan.add(INT_TBL_REF_BAN_LIN,"");
            vecCabRefBan.add(INT_TBL_REF_BAN_SOL,"Cod.Sol.");
            vecCabRefBan.add(INT_TBL_REF_BAN_CLI,"Cod.Cli.");
            vecCabRefBan.add(INT_TBL_REF_BAN_REG,"Cod.Reg.");
            vecCabRefBan.add(INT_TBL_REF_BAN_NOM,"Referencia Bancaria");
            vecCabRefBan.add(INT_TBL_REF_BAN_BUT,"...");
            
            objTblModRefBan=new ZafTblMod();
            objTblModRefBan.setHeader(vecCabRefBan);
            tblRefBan.setModel(objTblModRefBan);

            //Configurar JTable: Establecer tipo de selecciï¿½n.
            tblRefBan.setRowSelectionAllowed(true);
            tblRefBan.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer la fila de cabecera.
            objColNumCon=new ZafColNumerada(tblRefBan,INT_TBL_CON_LIN);

            //Configurar JTable: Establecer el menï¿½ de contexto.
            objPopMnuCon=new ZafTblPopMnu(tblRefBan);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblRefBan.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblRefBan.getColumnModel();
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_LIN).setPreferredWidth(35);
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_SOL).setPreferredWidth(10);
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_CLI).setPreferredWidth(10);
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_REG).setPreferredWidth(10);
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_NOM).setPreferredWidth(600);
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_BUT).setPreferredWidth(20);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_BUT).setResizable(false);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblRefBan.getTableHeader().setReorderingAllowed(false);
            
                  
            //Configurar JTable: Ocultar columnas del sistema.
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_SOL).setWidth(0);
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_SOL).setMaxWidth(0);
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_SOL).setMinWidth(0);
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_SOL).setPreferredWidth(0);
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_SOL).setResizable(false);
            
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_CLI).setWidth(0);
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_CLI).setMaxWidth(0);
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_CLI).setMinWidth(0);
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_CLI).setPreferredWidth(0);
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_CLI).setResizable(false);
            
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_REG).setWidth(0);
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_REG).setMaxWidth(0);
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_REG).setMinWidth(0);
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_REG).setPreferredWidth(0);
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_REG).setResizable(false);
            

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblRefBan.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_REF_BAN_BUT);
            objTblModRefBan.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Editor de bï¿½squeda.
            objTblBus=new ZafTblBus(tblRefBan);
            
            //Renderizo para boton
            objTblCelRenBut=new ZafTblCelRenBut();
            tblRefBan.getColumnModel().getColumn(INT_TBL_REF_BAN_BUT).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_REF_BAN_BUT).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    abrirFrm();
                }
            });
            
            //Configurar JTable: Modo de operaciï¿½n del JTable.
            objTblModRefBan.setModoOperacion(objTblModRefBan.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
   //////////-------------///////////////---------------////////////////-----------------////////////////--------------////////////         

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar los "Clientes/Proveedores".
     */
    private boolean configurarVenConCli()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cï¿½digo");
            arlAli.add("Identificaciï¿½n");
            arlAli.add("Nombre");
            arlAli.add("Direcciï¿½n");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");
            
            if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
            {            
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" WHERE a1.st_cli='S' ";

                    if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" AND a1.co_emp=" + CODEMP;
                    else
                        strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();

                    strSQL+=" ORDER BY a1.co_emp, a1.tx_nom";
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" WHERE a1.st_cli='S' ";
                    ///strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();

                    if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" AND a1.co_emp=" + CODEMP;
                    else
                        strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();

                    strSQL+=" ORDER BY a1.co_emp, a1.tx_nom";
                }
            }
            else
            {
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" WHERE a1.st_cli='S' ";
                    ///strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();

                    if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" AND a1.co_emp=" + CODEMP;
                    else
                        strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();

                    strSQL+=" ORDER BY a1.co_emp, a1.tx_nom";
                }
                else
                {
                    
                    strSQL="";
                    strSQL+="SELECT a1.co_cli, a2.tx_ide, a2.tx_nom, a2.tx_dir";
                    strSQL+=" FROM  tbr_cliloc AS a1";
                    strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                    strSQL+=" WHERE a2.st_cli='S' ";
                    if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" AND a1.co_emp=" + CODEMP;
                    else
                    {
                        strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    }
                    strSQL+=" AND a2.st_reg='A'";
                    strSQL+=" ORDER BY a1.co_emp, a2.tx_nom";
                }
            }
            
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCli.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar las "Formas de Pago".
     */
    private boolean configurarVenConForPag()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_forpag");
            arlCam.add("a1.tx_des");
            arlCam.add("a1.ne_numpag");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cï¿½digo For. Pag.");
            arlAli.add("Des. Corta");
            arlAli.add("Num. Pago");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("350");
            arlAncCol.add("50");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_forpag, a1.tx_des, a1.ne_numpag ";
            strSQL+=" FROM tbm_cabforpag as a1 ";
            //strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            
            if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
            {
                ///strSQL+=" WHERE a1.co_emp=" + CODEMP;
                strSQL+=" WHERE ";
                strSQL+=" a1.st_reg <> 'E' ";
            }
            else
            {
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg <> 'E' ";
            }
            
            ///strSQL+=" AND a1.st_reg <> 'E' ";           
            
            strSQL+=" ORDER BY a1.co_forpag ";
            vcoForPag=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Formas de Pago", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoForPag.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
            vcoForPag.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar las "Formas de Pago".
     */
    private boolean configurarVenConForPagCreCon()
    {
        boolean blnRes=true;
        try
        {
            
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_forpag");
            arlCam.add("a1.tx_des");
            arlCam.add("a1.ne_numpag");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cï¿½digo For. Pag.");
            arlAli.add("Des. Corta");
            arlAli.add("Num. Pago");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("350");
            arlAncCol.add("50");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_forpag, a1.tx_des, a1.ne_numpag ";
            strSQL+=" FROM tbm_cabforpag as a1 ";
            ///strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            
            if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
            {
                ///strSQL+=" WHERE a1.co_emp=" + CODEMP;
                strSQL+=" WHERE ";
                strSQL+=" a1.st_reg <> 'E' ";
            }
            else
            {
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg <> 'E' ";
            }
            
            ///strSQL+=" AND a1.st_reg <> 'E' ";
            
            strSQL+=" ORDER BY a1.co_forpag ";
            vcoForPagCreCon=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Formas de Pago", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoForPagCreCon.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
            vcoForPagCreCon.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean abrirFrm()
    {
        boolean blnRes=true;
        try
        {
            if(tabFrm.getSelectedIndex()==1)
            {
                //////////////PARA LLAMAR A LA VENTANA DE PROPIETARIOS DE LA EMPRESA ////////
                if(!(tblAcc.getSelectedColumn()==-1))
                {
                    if(!(tblAcc.getSelectedRow()==-1))
                    {
                        strAux="CxC.ZafCxC26.ZafCxC26";
                        if (!strAux.equals(""))
                            invocarClase(strAux);
                    }
                }
            }
            
            
            if(tabFrm.getSelectedIndex()==2)
            {
                //////////////PARA LLAMAR A LA VENTANA DE CONTACTOS ////////
                if(!(tblCon.getSelectedColumn()==-1))
                {
                    if(!(tblCon.getSelectedRow()==-1))
                    {
                        strAux="CxC.ZafCxC27.ZafCxC27";
                        if (!strAux.equals(""))
                            invocarClase(strAux);
                    }
                }
            }
            
            
            if(tabFrm.getSelectedIndex()==4)
            {
                //////////////PARA LLAMAR A LA VENTANA DE REFERENCIA COMERCIAL ////////
                if(!(tblRefCom.getSelectedColumn()==-1))
                {
                    if(!(tblRefCom.getSelectedRow()==-1))
                    {
                        strAux="CxC.ZafCxC28.ZafCxC28";
                        if (!strAux.equals(""))
                            invocarClase(strAux);
                    }
                }
            }
            
            
            if(tabFrm.getSelectedIndex()==5)
            {
                //////////////PARA LLAMAR A LA VENTANA DE REFERENCIA BANCARIAS ////////
                if(!(tblRefBan.getSelectedColumn()==-1))
                {
                    if(!(tblRefBan.getSelectedRow()==-1))
                    {
                        strAux="CxC.ZafCxC29.ZafCxC29";
                        if (!strAux.equals(""))
                            invocarClase(strAux);
                    }
                }
            }
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean invocarClase(String clase)
    {
        boolean blnRes=true;
        try
        {
            ////TAB DE PROPIETARIOS - ACCIONISTAS////
            if(tabFrm.getSelectedIndex()==1)
            {
                //Obtener el constructor de la clase que se va a invocar.
                Class objVen=Class.forName(clase);
                Class objCla[]=new Class[5];
                objCla[0]=objParSis.getClass();
                objCla[1]=new Integer(0).getClass();
                objCla[2]=new String("").getClass();
                ///objCla[3]=new Integer(0).getClass();
                if(c>=1)
                {
                    objCla[3]=new Integer(0).getClass();
                }
                else
                    objCla[3]=new Integer(0).getClass();
                
                if(m>=1)
                {
                    objCla[4]=new Integer(0).getClass();
                }
                else
                    objCla[4]=new Integer(0).getClass();
                
                java.lang.reflect.Constructor objCon=objVen.getConstructor(objCla);
                
                //Inicializar el constructor que se obtuvo.
                Object objObj[]=new Object[5];
                
                ///Para verificar si ultima linea del Jtable esta vacia o en modo de Insercion///
                if(tblAcc.getValueAt(tblAcc.getSelectedRow(),INT_TBL_DAT_NOM_ACC)==null )
                {
                    objObj[0]=objParSis;
                    objObj[1]=new Integer(Integer.parseInt(txtCodCli.getText()));
                    objObj[2]=new String(txtNomCli.getText());
                    ///objObj[3]=null;
                }
                else
                {
                    objObj[0]=objParSis;
                    objObj[1]=new Integer(tblAcc.getValueAt(tblAcc.getSelectedRow(),INT_TBL_DAT_COD_CLI).toString());
                    objObj[2]=null;
                    ///objObj[3]=new Integer(tblAcc.getValueAt(tblAcc.getSelectedRow(),INT_TBL_CON_REG).toString());
                }
                
                if(c>=1)
                {
                    objObj[3]=new Integer(c);
                }
                else
                    objObj[3]=new Integer(c);

                if(m>=1)
                {
                    objObj[4]=new Integer(m);
                }
                else
                    objObj[4]=new Integer(m);
                
                javax.swing.JInternalFrame ifrVen;
                ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
                this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
                ifrVen.show();
            }
            
            
            
            ////TAB DE CONTACTOS////
            if(tabFrm.getSelectedIndex()==2)
            {
                //Obtener el constructor de la clase que se va a invocar.
                Class objVen=Class.forName(clase);
                Class objCla[]=new Class[7];
                objCla[0]=objParSis.getClass();
                objCla[1]=new Integer(0).getClass();
                objCla[2]=new Integer(0).getClass();
                objCla[3]=new String("").getClass();
                objCla[4]=new Integer(0).getClass();
                if(c>=1)
                {
                    objCla[5]=new Integer(0).getClass();
                }
                else
                    objCla[5]=new Integer(0).getClass();
                
                if(m>=1)
                {
                    objCla[6]=new Integer(0).getClass();
                }
                else
                    objCla[6]=new Integer(0).getClass();
                
                java.lang.reflect.Constructor objCon=objVen.getConstructor(objCla);
                
                //Inicializar el constructor que se obtuvo.
                Object objObj[]=new Object[7];
                
                ///Para verificar si ultima linea del Jtable esta vacia o en modo de Insercion///
                if(tblCon.getValueAt(tblCon.getSelectedRow(),INT_TBL_CON_CLI)==null || tblCon.getValueAt(tblCon.getSelectedRow(),INT_TBL_CON_REG)==null)
                {
                    objObj[0]=objParSis;
                    objObj[1]=new Integer(CODEMP);
                    objObj[2]=new Integer(Integer.parseInt(txtCodCli.getText()));
                    objObj[3]=new String(txtNomCli.getText());
                    objObj[4]=null;
                }
                else
                {
                    objObj[0]=objParSis;
                    objObj[1]=new Integer(CODEMP);
                    objObj[2]=new Integer(tblCon.getValueAt(tblCon.getSelectedRow(),INT_TBL_CON_CLI).toString());
                    objObj[3]=null;
                    objObj[4]=new Integer(tblCon.getValueAt(tblCon.getSelectedRow(),INT_TBL_CON_REG).toString());
                }
                
                if(c>=1)
                {
                    objObj[5]=new Integer(c);
                }
                else
                    objObj[5]=new Integer(c);

                if(m>=1)
                {
                    objObj[6]=new Integer(m);
                }
                else
                    objObj[6]=new Integer(m);
                
                javax.swing.JInternalFrame ifrVen;
                ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
                this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
                ifrVen.show();
            }
            
            
            ////TAB DE REFERENCIAS COMERCIALES////
            if(tabFrm.getSelectedIndex()==4)
            {
                //Obtener el constructor de la clase que se va a invocar.
                Class objVen=Class.forName(clase);
                Class objCla[]=new Class[8];
                objCla[0]=objParSis.getClass();
                objCla[1]=new Integer(0).getClass();
                objCla[2]=new Integer(0).getClass();
                objCla[3]=new Integer(0).getClass();
                objCla[4]=new String ("").getClass();
                objCla[5]=new Integer(0).getClass();
                if(c>=1)
                {
                    objCla[6]=new Integer(0).getClass();
                }
                else
                    objCla[6]=new Integer(0).getClass();
                
                if(m>=1)
                {
                    objCla[7]=new Integer(0).getClass();
                }
                else
                    objCla[7]=new Integer(0).getClass();
                
                java.lang.reflect.Constructor objCon=objVen.getConstructor(objCla);
                
                //Inicializar el constructor que se obtuvo.
                Object objObj[]=new Object[8];
                
                ///Para verificar si ultima linea del Jtable esta vacia o en modo de Insercion///
                if(tblRefCom.getValueAt(tblRefCom.getSelectedRow(),INT_TBL_REF_COM_SOL)==null || tblRefCom.getValueAt(tblRefCom.getSelectedRow(),INT_TBL_REF_COM_REG)==null)
                {
                    objObj[0]=objParSis;
                    objObj[1]=new Integer(CODEMP);
                    objObj[2]=new Integer(txtSolCre.getText());
                    objObj[3]=new Integer(txtCodCli.getText());
                    objObj[4]=new String(txtNomCli.getText());
                    objObj[5]=null;
                }
                else
                {
                    objObj[0]=objParSis;
                    objObj[1]=new Integer(CODEMP);
                    objObj[2]=new Integer(tblRefCom.getValueAt(tblRefCom.getSelectedRow(),INT_TBL_REF_COM_SOL).toString());
                    objObj[3]=new Integer(tblRefCom.getValueAt(tblRefCom.getSelectedRow(),INT_TBL_REF_COM_CLI).toString());
                    objObj[4]=null;
                    objObj[5]=new Integer(tblRefCom.getValueAt(tblRefCom.getSelectedRow(),INT_TBL_REF_COM_REG).toString());
                }

                if(c>=1)
                {
                    objObj[6]=new Integer(c);
                }
                else
                    objObj[6]=new Integer(c);
                
                if(m>=1)
                {
                    objObj[7]=new Integer(m);
                }
                else
                    objObj[7]=new Integer(m);
                
                javax.swing.JInternalFrame ifrVen;
                ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
                this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
                ifrVen.show();
            }
            
            
            ////TAB DE REFERENCIAS BANCARIAS////
            if(tabFrm.getSelectedIndex()==5)
            {
                Class objVen=Class.forName(clase);
                Class objCla[]=new Class[8];
                objCla[0]=objParSis.getClass();
                objCla[1]=new Integer(0).getClass();
                objCla[2]=new Integer(0).getClass();
                objCla[3]=new Integer(0).getClass();
                objCla[4]=new String ("").getClass();
                objCla[5]=new Integer(0).getClass();
                if(c>=1)
                {
                    objCla[6]=new Integer(0).getClass();
                }
                else
                    objCla[6]=new Integer(0).getClass();
                
                if(m>=1)
                {
                    objCla[7]=new Integer(0).getClass();
                }
                else
                    objCla[7]=new Integer(0).getClass();
                
                java.lang.reflect.Constructor objCon=objVen.getConstructor(objCla);
                //Inicializar el constructor que se obtuvo.
                Object objObj[]=new Object[8];
                
                ///Para verificar si ultima linea del Jtable esta vacia o en modo de Insercion///
                if(tblRefBan.getValueAt(tblRefBan.getSelectedRow(),INT_TBL_REF_BAN_SOL)==null || tblRefBan.getValueAt(tblRefBan.getSelectedRow(),INT_TBL_REF_BAN_REG)==null)
                {
                    objObj[0]=objParSis;
                    objObj[1]=new Integer(CODEMP);
                    objObj[2]=new Integer(txtSolCre.getText());
                    objObj[3]=new Integer(txtCodCli.getText());
                    objObj[4]=new String(txtNomCli.getText());
                    objObj[5]=null;
                }
                else
                {
                    objObj[0]=objParSis;
                    objObj[1]=new Integer(CODEMP);
                    objObj[2]=new Integer(tblRefBan.getValueAt(tblRefBan.getSelectedRow(),INT_TBL_REF_BAN_SOL).toString());
                    objObj[3]=new Integer(tblRefBan.getValueAt(tblRefBan.getSelectedRow(),INT_TBL_REF_BAN_CLI).toString());
                    objObj[4]=null;
                    objObj[5]=new Integer(tblRefBan.getValueAt(tblRefBan.getSelectedRow(),INT_TBL_REF_BAN_REG).toString());
                }
                
                if(c>=1)
                {
                    objObj[6]=new Integer(c);
                }
                else
                    objObj[6]=new Integer(c);
                
                if(m>=1)
                {
                    objObj[7]=new Integer(m);
                }
                else
                    objObj[7]=new Integer(m);
                
                javax.swing.JInternalFrame ifrVen;
                ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
                this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
                ifrVen.show();
            }
        }
        catch (ClassNotFoundException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (NoSuchMethodException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (SecurityException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (InstantiationException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (IllegalAccessException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (IllegalArgumentException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (java.lang.reflect.InvocationTargetException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
    public void setEditable(boolean editable)
    {
        if (editable==true)
        {
            if(tabFrm.getSelectedIndex()==1)
                objTblModAcc.setModoOperacion(objTblModAcc.INT_TBL_EDI);
            if(tabFrm.getSelectedIndex()==2)
                objTblModCon.setModoOperacion(objTblModCon.INT_TBL_EDI);
            if(tabFrm.getSelectedIndex()==3)
                objTblModObs.setModoOperacion(objTblModObs.INT_TBL_EDI);
            if(tabFrm.getSelectedIndex()==4)
                objTblModRefCom.setModoOperacion(objTblModRefCom.INT_TBL_EDI);
            if(tabFrm.getSelectedIndex()==5)
                objTblModRefBan.setModoOperacion(objTblModRefBan.INT_TBL_EDI);
        }
        else
        {
            if(tabFrm.getSelectedIndex()==1)
                objTblModAcc.setModoOperacion(objTblModAcc.INT_TBL_NO_EDI);
            if(tabFrm.getSelectedIndex()==2)
                objTblModCon.setModoOperacion(objTblModCon.INT_TBL_NO_EDI);
            if(tabFrm.getSelectedIndex()==3)
                objTblModObs.setModoOperacion(objTblModObs.INT_TBL_NO_EDI);
            if(tabFrm.getSelectedIndex()==4)
                objTblModRefCom.setModoOperacion(objTblModRefCom.INT_TBL_NO_EDI);
            if(tabFrm.getSelectedIndex()==5)
                objTblModRefBan.setModoOperacion(objTblModRefBan.INT_TBL_NO_EDI);
        }
    }   
   
    
 //PARA LA BARRA DE HERRAMIENTAS
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            dtpFecDoc.setText("");            
            txtSolCre.setText("");
            txtCodHisSolCre.setText("");
            txtCodCli.setText("");
            txtNomCli.setText("");
            txtCodTipPer.setText("");
            txtDesTipPer.setText("");
            txtNomTipPer.setText("");
            jChkCli.setSelected(false);
            jChkPrv.setSelected(false);
            jCbxTipIde.setSelectedIndex(-1);
            txtIde.setText("");
            txtDir.setText("");
            txtRefUbi.setText("");
            txtTelCon1.setText("");
            txtTelCon2.setText("");
            txtTelCon3.setText("");
            txtTelf.setText("");
            txtFax.setText("");
            txtCasilla.setText("");
            txtEmail.setText("");
            txtWeb.setText("");
            txtCodCiu.setText("");
            txtCiu.setText("");
            txtCodZon.setText("");
            txtNomZon.setText("");
            txtDesVen.setText("");
            txtNomVen.setText("");
            txaObsCxC.setText("");
            txaObsInfBurCre.setText("");
            
            //////////////////////
            
            dtpFecConEmp.setText("");
            txtCedPasPro.setText("");
            txtNomPro.setText("");            
            txtDomPro.setText("");           
            txtTelPro.setText("");
            txtNacPro.setText("");
            txtTipActEmp.setText("");
            txaObs1.setText("");
            
            objTblModAcc.removeAllRows();
//
//            ////////////////////
//
            objTblModCon.removeAllRows();
//            
//            ////////////////////
            
            objTblModObs.removeAllRows();
//            
//            ////////////////////
//                        
            objTblModRefCom.removeAllRows();
//            
//            ////////////////////
//            
            objTblModRefBan.removeAllRows();
            
            /////////////////////
            
            txtMonSol.setText("");
            txtCodForPag.setText("");
            txtForPag.setText("");
            txtMonApr.setText("");
            txtForPagCreCon.setText("");
            
            txaObsSolCre1.setText("");
            txaObsSolCre2.setText("");
            
            objTooBar.setEstadoRegistro("");
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
  
     private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }   
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    private void consultarCamUsr()
    {
        strAux="ï¿½Desea guardar los cambios efectuados a ï¿½ste registro?\n";
        strAux+="Si no guarda los cambios perderï¿½ toda la informaciï¿½n que no haya guardado.";
        
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'm': //Modificar
                        break;
                    case 'e': //Eliminar
                        break;
                    case 'n': //insertar
                        break;
                }
                break;
            case 1: //NO_OPTION
                break;
            case 2: //CANCEL_OPTION
                break;
        }
    //nuevos metodos del toolbar
                    
    }   

    private boolean isRegPro()
    {
        boolean blnRes=true;
        strAux="ï¿½Desea guardar los cambios efectuados a ï¿½ste registro?\n";
        strAux+="Si no guarda los cambios perderï¿½ toda la informaciï¿½n que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnHayCam=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }   


    /**
     *Esta funcion valida si los campos obligatorios han sido completados correctamente 
     *@return True si los campos obligatorios se llenaron correctamente.
     */
    private boolean isCamVal()
    {
//        if(txtSolCre.getText().equals(""))
//        {
//            tabFrm.setSelectedIndex(0);
//            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Solicitud de Credito</FONT> es obligatorio.<BR>Escriba un cï¿½digo de solicitud de credito y vuelva a intentarlo.</HTML>");
//            txtSolCre.requestFocus();
//            return false;
//        }

        if(txtCodCli.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Codigo Cliente </FONT> es obligatorio.<BR>Escriba un Codigo de Cliente y vuelva a intentarlo.</HTML>");
            txtCodCli.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     *Esta funcion Inactiva los campos obligatorios a la hora de modificar el registro de solicitud del cliente
     *@return True si los campos obligatorios se modificaron correctamente.
     */
    private boolean isDesCam()
    {
        txtSolCre.setEditable(false);
        ///dtpFecDoc.setEnabled(false);
        txtCodCli.setEditable(false);
        txtNomCli.setEditable(false);
        txtDesTipPer.setEditable(false);
        txtNomTipPer.setEditable(false);
        butTipPer.setEnabled(false);
        jChkCli.setEnabled(false);
        jChkPrv.setEnabled(false);
        jCbxTipIde.setEnabled(false);
        txtIde.setEditable(false);
        
        ///inactivar datos que don luiggi tiene que modificar///        
        if(BAN==0)
        {
            txtMonApr.setEditable(false);
            txtForPagCreCon.setEditable(false);
            butForPagCreCon.setEnabled(false);
            txtProAct.setEditable(false);
            dtpFecUltAct.setEnabled(false);
            dtpFecProAct.setEnabled(false);
        }
        return true;
    }
    
    
    /**
     *Esta funcion Inactiva los campos obligatorios a la hora de modificar el registro de solicitud del cliente
     *@return True si los campos obligatorios se modificaron correctamente.
     */
    private boolean isInaCam()
    {
        txtSolCre.setEditable(false);
        dtpFecDoc.setEnabled(false);        
        txtCodCli.setEditable(false);
        txtNomCli.setEditable(false);
        txtDesTipPer.setEditable(false);
        txtNomTipPer.setEditable(false);
        butTipPer.setEnabled(false);
        jChkCli.setEnabled(false);
        jChkPrv.setEnabled(false);
        jCbxTipIde.setEnabled(false);
        txtIde.setEditable(false);
        txtDir.setEditable(false);
        txtRefUbi.setEditable(false);
        txtTelCon1.setEditable(false);
        txtTelCon2.setEditable(false);
        txtTelCon3.setEditable(false);
        txtTelf.setEditable(false);
        txtFax.setEditable(false);
        txtCasilla.setEditable(false);
        txtEmail.setEditable(false);
        txtWeb.setEditable(false);
        txtCiu.setEditable(false);
        txtNomZon.setEditable(false);
        txtDesVen.setEditable(false);
        txtNomVen.setEditable(false);
        txaObsCxC.setEditable(false);
        txaObsInfBurCre.setEditable(false);
        
        txtCedPasPro.setEditable(false);
        txtNomPro.setEditable(false);
        txtDomPro.setEditable(false);
        txtTelPro.setEditable(false);
        txtNacPro.setEditable(false);
        dtpFecConEmp.setEnabled(false);
        txtTipActEmp.setEditable(false);
        txaObs1.setEditable(false);
        
        panCreSol.setEnabled(false);
        panCreCon.setEnabled(true);
        panActDat.setEnabled(true);
        
        txtMonSol.setEditable(false);
        txtCodForPag.setEditable(false);
        txtForPag.setEditable(false);
        butForPag.setEnabled(false);
        txtMonApr.setEnabled(true);
        txtForPagCreCon.setEditable(true);
        txtForPagCreCon.setEnabled(true);
        butForPagCreCon.setEnabled(true);
        txtProAct.setEnabled(true);
        dtpFecUltAct.setEnabled(true);
        txtMonApr.requestFocus();
        dtpFecProAct.setEnabled(true);
        
        /* Tablas*/
        tblAcc.setEnabled(true);
        tblCon.setEnabled(true);
        tblRefCom.setEnabled(true);
        tblRefBan.setEnabled(true);
        
        return true;
    }
    
    
    protected int ultCodDoc()
    {
        int intUltDoc=0;
        boolean blnRes=true;
        java.sql.Connection conNumDoc;
        java.sql.Statement stmNumDoc;
        java.sql.ResultSet rstNumDoc;
        try{            
            conNumDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conNumDoc!=null)
            {            
                stmNumDoc=conNumDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String sqlDoc="";
                sqlDoc+="select max(b1.co_sol) as ultdoc ";
                sqlDoc+=" from tbm_solcre as b1 ";
                ///sqlDoc+=" where b1.co_emp=" + objParSis.getCodigoEmpresa() + ""; 
                
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                    sqlDoc+=" WHERE b1.co_emp=" + CODEMP;
                else
                    sqlDoc+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                intUltDoc=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), sqlDoc);
                ///intUltDoc++;
                rstNumDoc=stmNumDoc.executeQuery(sqlDoc);      
                if(rstNumDoc.next())
                    System.out.println("---el ultimo codigo del documento en SOLCRE es---: "+ intUltDoc);
            }
            conNumDoc.close();
            conNumDoc=null;
            stmNumDoc=null;
            rstNumDoc=null;
            
        }
         catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intUltDoc;
    }

    public class mitoolbar extends ZafToolBar
    {
            public mitoolbar(javax.swing.JInternalFrame jfrThis){
                super(jfrThis, objParSis);
            }

        public void clickInsertar()
        {
            try
            {
                vecDatAcc.clear();
                vecDatCon.clear();
                vecDatRefCom.clear();
                vecDatRefBan.clear();
                ///vecDatDocDig.clear();
                
//                objTblModAcc.setModoOperacion(objTblModAcc.INT_TBL_NO_EDI);
//                objTblModCon.setModoOperacion(objTblModCon.INT_TBL_NO_EDI);
//                objTblModObs.setModoOperacion(objTblModObs.INT_TBL_NO_EDI);
//                objTblModRefCom.setModoOperacion(objTblModRefCom.INT_TBL_NO_EDI);
//                objTblModRefBan.setModoOperacion(objTblModRefBan.INT_TBL_NO_EDI);
                
                if (blnHayCam)
                {
                    isRegPro();
                }
                
                //////PARA PANEL DE CONTROL DE LOS HISTORICOS///
                if(INTCODMNU == 1014)
                {
                    if (rstCabHis!=null)
                    {
                        rstCabHis.close();
                        stmCabHis.close();
                        conCabHis.close();
                        rstCabHis=null;
                        stmCabHis=null;
                        conCabHis=null;
                    }
                }
                else
                {
                    if (rstCab!=null)
                    {
                        rstCab.close();
                        stmCab.close();
                        conCab.close();
                        rstCab=null;
                        stmCab=null;
                        conCab=null;
                    }
                }
                limpiarFrm();
                txtCodCli.requestFocus();               
                txtSolCre.setEditable(false);
                txtDesTipPer.setEditable(false);
                txtNomTipPer.setEditable(false);
                butTipPer.setEnabled(false);
                jChkCli.setEnabled(false);
                jChkPrv.setEnabled(false);
                jCbxTipIde.setEnabled(false);
                txtIde.setEditable(false);

                ////establecer fecha para la referencia///
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                ///dtpFecConEmp.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                
                blnHayCam=false;
                
                tblAcc.setEnabled(true);
                tblCon.setEnabled(true);
                tblRefCom.setEnabled(true);
                tblRefBan.setEnabled(true);
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public boolean insertar()
        {
            if (!insertarReg())
                return false;
        
            return true;
        }
        
        public void clickEliminar()
        {
            vecDatAcc.clear();
            vecDatCon.clear();
            vecDatRefCom.clear();
            vecDatRefBan.clear();
            
            if(INTCODMNU != 1014)
                consultarCabReg();
            
            ///vecDatDocDig.clear();
            
            e++;
        }
        
        public boolean eliminar()
        {
            try
            {
                String strAux=objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado"))
                {
                    mostrarMsgInf("El documento ya estï¿½ ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                    return false;
                }
                else
                    if (!eliminarReg())
                        return false;
                //Desplazarse al siguiente registro si es posible.
                                
                //////PARA PANEL DE CONTROL DE LOS HISTORICOS///
                if(INTCODMNU == 1014)
                {
                    if (!rstCabHis.isLast())
                    {
                        rstCabHis.next();
//                        if(INTCODMNU != 1014)
//                            cargarReg();
                    }
                    else
                    {
                        objTooBar.setEstadoRegistro("Eliminado");
                        limpiarFrm();
                    }
                }
                else
                {
                    if (!rstCab.isLast())
                    {
                        rstCab.next();
                        cargarReg();
                    }
                    else
                    {
                        objTooBar.setEstadoRegistro("Eliminado");
                        limpiarFrm();
                    }
                }
                    
                blnHayCam=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }
        
        public void clickAnular()
        {
            vecDatAcc.clear();
            vecDatCon.clear();
            vecDatRefCom.clear();
            vecDatRefBan.clear();
            
            if(INTCODMNU != 1014)
                consultarCabReg();
            
            ///vecDatDocDig.clear();
            
            a++;
        }
        
        public boolean anular()
        {
            try
            {
                
                String strAux=objTooBar.getEstadoRegistro();
                if (strAux.equals("Anulado"))
                {
                    mostrarMsgInf("El documento ya estï¿½ ANULADO.\n No es posible ANULAR un documento anulado.");
                    return false;
                }
                else
                    if (!anularReg())
                        return false;
                
                //Desplazarse al siguiente registro si es posible.
                
                //////PARA PANEL DE CONTROL DE LOS HISTORICOS///
                if(INTCODMNU == 1014)
                {
                    if (!rstCabHis.isLast())
                    {
                        rstCabHis.next();
//                        if(INTCODMNU != 1014)
//                            cargarReg();
                    }
                    else
                    {
                        objTooBar.setEstadoRegistro("Anulado");
                        ///limpiarFrm();
                    }
                }
                else
                {
                    if (!rstCab.isLast())
                    {
                        rstCab.next();
                        cargarReg();
                    }
                    else
                    {
                        objTooBar.setEstadoRegistro("Anulado");
                        ///limpiarFrm();
                    }
                }
                blnHayCam=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }


        public void clickConsultar()
        {       
            //Configurar JTable: Modo de operaciï¿½n del JTable.            
            objTblModAcc.setModoOperacion(objTblModAcc.INT_TBL_EDI);
            objTblModCon.setModoOperacion(objTblModCon.INT_TBL_EDI);
            objTblModObs.setModoOperacion(objTblModObs.INT_TBL_EDI);
            objTblModRefCom.setModoOperacion(objTblModRefCom.INT_TBL_EDI);
            objTblModRefBan.setModoOperacion(objTblModRefBan.INT_TBL_EDI);
            
            vecDatAcc.clear();
            vecDatCon.clear();
            vecDatObs.clear();
            vecDatRefCom.clear();
            vecDatRefBan.clear();
            ///vecDatDocDig.clear();
                
            txtCodCli.requestFocus();
            c++;
        }
            
            
       public boolean consultar() 
       {
           consultarReg();
           return true;
       }
            
                                    
       public void clickModificar()
       {
           
           txtDir.requestFocus();
           m++;
           c=0;
           isDesCam();
       }
       
       
       public boolean modificar()
       {    
           if(BAN==0)
           {
                /* Aqui se llevara el proceso de insercion de registros para las tablas historicas */
                ///insertarRegHis()
               ///if (!insertarRegHis())
               if(INTCODMNU != 1014)
               {
                if (!actualizarReg())
                   return false;
               }
           }
           else
           {
               if (!actualizarRegCreCon())
                   return false;
           }
           return true;
       }           

     public void clickInicio() 
     {
            try
            {
                vecDatAcc.clear();
                vecDatCon.clear();
                vecDatRefCom.clear();
                vecDatRefBan.clear();
                ///vecDatDocDig.clear();
                
                //////PARA PANEL DE CONTROL DE LOS HISTORICOS///
                if(INTCODMNU == 1014)
                {
                    if (!rstCabHis.isFirst())
                    {
                        if (blnHayCam)
                        {
                            if (isRegPro())
                            {
                                limpiarFrm();
                                rstCabHis.first();
                                cargarRegHis();
                            }
                        }
                        else
                        {
                            limpiarFrm();
                            rstCabHis.first();
                            cargarRegHis();
                        }
                    }
                }
                else
                {
                    if (!rstCab.isFirst())
                    {
                        if (blnHayCam)
                        {
                            if (isRegPro())
                            {
                                limpiarFrm();
                                rstCab.first();
                                cargarReg();
                            }
                        }
                        else
                        {
                            limpiarFrm();
                            rstCab.first();
                            cargarReg();
                        }
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }                
     }
     
     public void clickAnterior() 
     {
            try
            {
                vecDatAcc.clear();
                vecDatCon.clear();
                vecDatRefCom.clear();
                vecDatRefBan.clear();
                ///vecDatDocDig.clear();
                
                //////PARA PANEL DE CONTROL DE LOS HISTORICOS///
                if(INTCODMNU == 1014)
                {
                    if (!rstCabHis.isFirst())
                    {
                        if (blnHayCam)
                        {
                            if(isRegPro())
                            {
                                limpiarFrm();
                                rstCabHis.previous();
                                cargarRegHis();
                            }
                        }
                        else
                        {
                            limpiarFrm();
                            rstCabHis.previous();
                            cargarRegHis();
                        }
                    }
                }
                else
                {
                    if (!rstCab.isFirst())
                    {
                        if (blnHayCam)
                        {
                            if(isRegPro())
                            {
                                limpiarFrm();
                                rstCab.previous();
                                cargarReg();
                            }
                        }
                        else
                        {
                            limpiarFrm();
                            rstCab.previous();
                            cargarReg();
                        }
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            
            
     }
     
     
     public void clickSiguiente() 
     {
        try
        {
            vecDatAcc.clear();
            vecDatCon.clear();
            vecDatRefCom.clear();
            vecDatRefBan.clear();
            ///vecDatDocDig.clear();

            //////PARA PANEL DE CONTROL DE LOS HISTORICOS///
            if(INTCODMNU == 1014)
            {
                if (!rstCabHis.isLast())
                {
                    if (blnHayCam)
                    {
                        if(isRegPro())
                        {
                            limpiarFrm();
                            rstCabHis.next();
                            cargarRegHis();
                        }

                    }
                    else
                    {
                        limpiarFrm();
                        rstCabHis.next();
                        cargarRegHis();
                    }
                }
            }
            else
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam)
                    {
                        if(isRegPro())
                        {
                            limpiarFrm();
                            rstCab.next();
                            cargarReg();
                        }
                    }
                    else
                    {
                        limpiarFrm();
                        rstCab.next();
                        cargarReg();
                    }
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }                  
     }
     
     public void clickFin() 
     {
        try
        {
            vecDatAcc.clear();
            vecDatCon.clear();
            vecDatRefCom.clear();
            vecDatRefBan.clear();
            ///vecDatDocDig.clear();
             
            //////PARA PANEL DE CONTROL DE LOS HISTORICOS///
            if(INTCODMNU == 1014)
            {
                if (!rstCabHis.isLast())
                {
                    if (blnHayCam)
                    {
                        if (isRegPro())
                        {

                            limpiarFrm();
                            rstCabHis.last();
                            cargarRegHis();
                        }
                    }
                    else
                    {
                        limpiarFrm();
                        rstCabHis.last();
                        cargarRegHis();
                    }
                }
            }
            else
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam)
                    {
                        if (isRegPro())
                        {

                            limpiarFrm();
                            rstCab.last();
                            cargarReg();
                        }
                    }
                    else
                    {
                        limpiarFrm();
                        rstCab.last();
                        cargarReg();
                    }
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
     }
            
     public void clickImprimir(){
     }
     
     public void clickVisPreliminar(){
     }
     
     public void clickAceptar()
     {
        vecDatAcc.clear();
        vecDatCon.clear();
        vecDatRefCom.clear();
        vecDatRefBan.clear();
        ///vecDatDocDig.clear();
        
        limpiarFrm();
     }

     public void clickCancelar()
     {
          limpiarFrm();
          vecDatAcc.clear();
          vecDatCon.clear();
          vecDatRefCom.clear();
          vecDatRefBan.clear();
          ///vecDatDocDig.clear();
          
          objTblModAcc.removeAllRows();
          objTblModCon.removeAllRows();
          objTblModRefCom.removeAllRows();
          objTblModRefBan.removeAllRows();
          tabFrm.setSelectedIndex(0);
     }

     private int Mensaje()
     {
                String strTit, strMsg;
                strTit="Mensaje del sistema Zafiro";
                strMsg="ï¿½Desea guardar los cambios efectuados a ï¿½ste registro?\n";
                strMsg+="Si no guarda los cambios perderï¿½ toda la informaciï¿½n que no haya guardado.";

                javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                
                return obj.showConfirmDialog(jfrThis ,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);                
                
     }
     private void MensajeValidaCampo(String strNomCampo){
                    javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
                    obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
     }
     
        public boolean vistaPreliminar() {
            return true;
        }        
        
        public boolean afterVistaPreliminar() {
            return true;
        }        
        
        public boolean imprimir() {
            return true;
        }        
        
        public boolean afterAceptar() {
            return true;
        }        
        
        public boolean afterInsertar() {
            blnHayCam=false;
            ///objTooBar.setEstado('n');
            this.setEstado('w');
            blnHayCam=false;
            ///limpiarFrm();
            consultarReg();
            return true;
        }
        
        public boolean afterModificar() {
            return true;
        }
        
        public boolean afterImprimir() {
            return true;
        }
        
        public boolean afterEliminar() {
            return true;
        }
        
        public boolean afterCancelar() {
            return true;
        }
        
        public boolean afterAnular() {
            return true;
        }
        
        public boolean aceptar() {                        
            return true;
        }
        
        public boolean cancelar() 
        {
            boolean blnRes=true;
            try
            {
                if (blnHayCam)
                {
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                    {
                        if (!isRegPro())
                            return false;
                    }
                }
                //////PARA PANEL DE CONTROL DE LOS HISTORICOS///
                if(INTCODMNU == 1014)
                {
                    if (rstCabHis!=null)
                    {
                        ///Datos de la Cabecera///
                        ///////////////////
                        rstCabHis.close();
                        stmCabHis.close();
                        conCabHis.close();
                        rstCabHis=null;
                        stmCabHis=null;
                        conCabHis=null;
                    }

                    if(rstConHis!=null)
                    {
                        ///Datos de los demas tabs///
                        //////////////////
                        rstConHis.close();
                        stmConHis.close();
                        conConHis.close();
                        rstConHis=null;
                        stmConHis=null;
                        conConHis=null;
                    }

                    if(rstRefComHis!=null)
                    {
                        ///Datos de los demas tabs///
                        //////////////////
                        rstRefComHis.close();
                        stmRefComHis.close();
                        conRefComHis.close();
                        rstRefComHis=null;
                        stmRefComHis=null;
                        conRefComHis=null;
                    }

                    if(rstRefBanHis!=null)
                    {
                        ///Datos de los demas tabs///
                        //////////////////
                        rstRefBanHis.close();
                        stmRefBanHis.close();
                        conRefBanHis.close();
                        rstRefBanHis=null;
                        stmRefBanHis=null;
                        conRefBanHis=null;
                    }
                }
                else
                {
                    if (rstCab!=null)
                    {
                        ///Datos de la Cabecera///
                        ///////////////////
                        rstCab.close();
                        stmCab.close();
                        conCab.close();
                        rstCab=null;
                        stmCab=null;
                        conCab=null;
                    }

                    if(rstCon!=null)
                    {
                        ///Datos de los demas tabs///
                        //////////////////
                        rstCon.close();
                        stmCon.close();
                        conCon.close();
                        rstCon=null;
                        stmCon=null;
                        conCon=null;
                    }

                    if(rstRefCom!=null)
                    {
                        ///Datos de los demas tabs///
                        //////////////////
                        rstRefCom.close();
                        stmRefCom.close();
                        conRefCom.close();
                        rstRefCom=null;
                        stmRefCom=null;
                        conRefCom=null;
                    }

                    if(rstRefBan!=null)
                    {
                        ///Datos de los demas tabs///
                        //////////////////
                        rstRefBan.close();
                        stmRefBan.close();
                        conRefBan.close();
                        rstRefBan=null;
                        stmRefBan=null;
                        conRefBan=null;
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }
        
        public boolean afterConsultar() 
        {
            tblCon.setEnabled(true);
            tblRefCom.setEnabled(true);
            tblRefBan.setEnabled(true);
            return true;
        }
     
    
      public boolean beforeInsertar()
        {
            if (!isCamVal())
                return false;
            return true;
        }

        public boolean beforeConsultar()
        {
             return true;
        }

        public boolean beforeModificar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento estï¿½ ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento estï¿½ ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            if (!isCamVal())
                return false;

            return true;
        }

        public boolean beforeEliminar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento ya estï¿½ ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }

        public boolean beforeAnular()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento estï¿½ ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento ya estï¿½ ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            return true;
        }

        public boolean beforeImprimir()
        {
            return true;
        }

        public boolean beforeVistaPreliminar()
        {
            return true;
        }

        public boolean beforeAceptar()
        {
            return true;
        }

        public boolean beforeCancelar()
        {
            return true;
        }     
   }      

    class tblHilo extends Thread{
            public void run()
            {
                
            }
     }

    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }
    }
    
     /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren mï¿½s espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblCon.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_CON_LIN:
                    strMsg="";
                    break;
                case INT_TBL_CON_NOM:
                    strMsg="Nombre de Contacto";
                    break;
                case INT_TBL_CON_BUT:
                    strMsg="Boton para Vizualizar Ventana de Contacto";
                    break;
                default:
                    strMsg="";
                    break;
            } 
            tblCon.getTableHeader().setToolTipText(strMsg);
        }
    }
    
//    private class ZafTblModLis implements javax.swing.event.TableModelListener
//    {
//        public void tableChanged(javax.swing.event.TableModelEvent e)
//        {
//            switch (e.getType())
//            {
//                case javax.swing.event.TableModelEvent.INSERT:
//                    break;
//                case javax.swing.event.TableModelEvent.DELETE:
//                    break;
//                case javax.swing.event.TableModelEvent.UPDATE:
//                    break;
//            }
//        }
//    }
}