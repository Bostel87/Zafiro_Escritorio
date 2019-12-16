/*
 * ZafCxC29.java
 *
 * Created on 12 de junio de 2007, Dario Cardenas Landin.
 * REFERENCIAS BANCARIAS
 * PASADO A PRODUCCION EL 12/SEP/2007
 */
package CxC.ZafCxC29;

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
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.*; //Para el boton abrir archivos.
import java.io.File;

/**
 *
 * @author Dario Cardenas
 */
public class ZafCxC29 extends javax.swing.JInternalFrame 
{
    //Variables para el boton de abrir archivos
    static JFrame frame;
    JFileChooser chooser;

    boolean blnCambios = false;
    private Librerias.ZafDate.ZafDatePicker dtpFecDoc, dtpFecDocApe;
    javax.swing.JInternalFrame jfrThis;                             //Hace referencia a this
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private String strSQL, strAux, strSQLCon, strFecApe;
    private Vector vecDatAcc, vecCabAcc;
    private Vector vecDatPnd, vecCabPnd;
    private boolean blnHayCam;                                     //Determina si hay cambios en el formulario.
    private ZafColNumerada objColNumExc, objColNumPnd;
    private ZafPopupMenu objPopMnu;
    private Vector vecTipCta, vecNatCta, vecEstReg, vecAux;
    private boolean blnCon;
    private ZafToolBar objToolBar;                                 //true: Continua la ejecución del hilo.
    private String strTit, sSQL, strFiltro;
    private String strCodCli, strDesLarCli, strIdeCli, strDirCli, strDesCorCta, strDesLarCta, strSolCre, strCiu, strCodCiu;

    private java.util.Vector vecRegAcc, vecRegPnd;
    private mitoolbar objTooBar;
    private String sSQLCon;
    private java.sql.Connection conCab, conDet, con, conCnsDet, conAnu;

    private java.sql.Statement stmCab, stmDet, stm, stmCnsDet, stmAnu;
    private java.sql.ResultSet rstCab, rstDet, rst, rstCnsDet;
    private tblHilo objHilo;
    private ZafTblMod objTblModAcc;

    //creacion de check
    private ZafTblCelRenChk objTblCelRenChkExc;
    private ZafTblCelRenChk objTblCelRenChkPnd;
    private ZafTblCelEdiChk objTblCelEdiChkExc;
    private ZafTblCelEdiChk objTblCelEdiChkPnd;

    //creacion de label
    private ZafTblCelRenLbl objTblCelRenLblExc;
    private ZafTblCelRenLbl objTblCelRenLblPnd;

    //creacion de txt
    private ZafTblCelEdiTxt objTblCelEdiTxtExc;
    private ZafTblCelEdiTxt objTblCelEdiTxtPnd;

    private int j = 1, CODEMP = 0;

    private ZafTblEdi objTblEdi;
    private ZafVenCon vcoSolCre;                        //Ventana de consulta Solicitudes de Crédito.
    private ZafVenCon vcoCiu;                           //Ventana de consulta "Ciudad".
    
    private double dblMonDoc = 0.00;
    private double dblValAboBef = 0.00;
    private double varTmp = 0.00;

    private int intSig = 1;                             //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private String strTipDoc, strDesLarTipDoc;          //Contenido del campo al obtener el foco.
    private String strUbiCta;                           //Campos: Ubicación de la cuenta.        
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.    

    /**
     * Crea una nueva instancia de la clase ZafCon01.
     */
    public ZafCxC29(ZafParSis obj) 
    {
        try 
        {
            ////boton abrir archivos///
            chooser = new JFileChooser();

            initComponents();
            //Inicializar objetos.
            this.objParSis = obj;
            jfrThis = this;
            objParSis = (ZafParSis) obj.clone();
            objUti = new ZafUtil();

            ////fecha de referencia///
            dtpFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) jfrThis.getParent()), "d/m/y");
            dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecDoc.setText("");
            panCabDoc.add(dtpFecDoc);
            dtpFecDoc.setBounds(440, 55, 100, 20);

            ////fecha de apertura de la agencia///
            dtpFecDocApe = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) jfrThis.getParent()), "d/m/y");
            dtpFecDocApe.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecDocApe.setText("");
            panCabDoc.add(dtpFecDocApe);
            dtpFecDocApe.setBounds(440, 155, 100, 20);

            objTooBar = new mitoolbar(this);
            panBar.add(objTooBar);//llama a la barra de botones

            if (!configurarFrm()) {
                exitForm();
            }

        } catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR]");
        }

    }

    public ZafCxC29(ZafParSis obj, Integer codigoEmpresa, Integer codigoSolicitud, Integer codigoCliente, String nombreCliente, Integer codigoRegistro, Integer consulta, Integer modifica)
    {
        this(obj);

        int c = 0, m = 0, intCodEmp = 0;
        int intCodEmpGrp = 0, intCodEmpNew = 0;

        c = Integer.parseInt(consulta.toString());
        m = Integer.parseInt(modifica.toString());
        intCodEmp = Integer.parseInt(codigoEmpresa.toString());
        CODEMP = intCodEmp;

        intCodEmpGrp = objParSis.getCodigoEmpresaGrupo();
        intCodEmpNew = objParSis.getCodigoEmpresa();

        objTooBar.setVisibleAceptar(true);
        objTooBar.setVisibleCancelar(true);
        objTooBar.setVisibleModificar(true);
        objTooBar.setVisibleConsultar(true);

        if (intCodEmpGrp == intCodEmpNew) {
            objTooBar.setVisibleInsertar(false);
        } else {
            objTooBar.setVisibleInsertar(true);
        }

        if (codigoCliente == null || codigoRegistro == null)
        {
            if (m >= 1) 
            {
                objTooBar.setEstado('n');
                txtCodDoc.setEditable(false);
                txtSolCre.setText(codigoSolicitud.toString());
                txtCodCli.setText(codigoCliente.toString());
                txtNomCli.setText(nombreCliente.toString());
                txtNomBco.requestFocus();
            }
        }
        else 
        {
            txtSolCre.setText(codigoSolicitud.toString());
            txtCodCli.setText(codigoCliente.toString());
            txtCodDoc.setText(codigoRegistro.toString());

            if (c >= 1) {
                objTooBar.setEstado('c');
                objTooBar.consultar();
                objTooBar.setEstado('w');
            }

            if (m >= 1) {
                objTooBar.setEstado('x');
                objTooBar.consultar();
                objTooBar.setEstado('m');
            } else {
                objTooBar.setEstado('x');
                objTooBar.consultar();
                objTooBar.setEstado('m');
            }
        }
        //Inicializar objetos
        strAux = objParSis.getNombreMenu();
        lblTit.setText("Maestro " + strAux);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
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
        panCabDoc = new javax.swing.JPanel();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblNomBco = new javax.swing.JLabel();
        txtNomBco = new javax.swing.JTextField();
        lblCli = new javax.swing.JLabel();
        txtNomCli = new javax.swing.JTextField();
        txtCodCli = new javax.swing.JTextField();
        butSolCre = new javax.swing.JButton();
        txtCarInf = new javax.swing.JTextField();
        lblCarInf = new javax.swing.JLabel();
        lblAge = new javax.swing.JLabel();
        txtAge = new javax.swing.JTextField();
        lblCiu = new javax.swing.JLabel();
        txtCiu = new javax.swing.JTextField();
        lblObs1 = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        lblNumCta = new javax.swing.JLabel();
        txtNumCta = new javax.swing.JTextField();
        lblPro = new javax.swing.JLabel();
        txtInf = new javax.swing.JTextField();
        txtSal = new javax.swing.JTextField();
        lblSal = new javax.swing.JLabel();
        txtSolCre = new javax.swing.JTextField();
        lblSolCre = new javax.swing.JLabel();
        jrbProSi = new javax.swing.JRadioButton();
        jrbProNo = new javax.swing.JRadioButton();
        lblCreDir = new javax.swing.JLabel();
        lblInf = new javax.swing.JLabel();
        lblDueCta = new javax.swing.JLabel();
        txtDueCta = new javax.swing.JTextField();
        lblFecRef = new javax.swing.JLabel();
        jrbCreIndSi = new javax.swing.JRadioButton();
        jrbCreIndNo = new javax.swing.JRadioButton();
        txtDocDig = new javax.swing.JTextField();
        lblDocDig = new javax.swing.JLabel();
        butSelArc = new javax.swing.JButton();
        butVisPre = new javax.swing.JButton();
        butCiu = new javax.swing.JButton();
        lblFecApe = new javax.swing.JLabel();
        txtCodCiu = new javax.swing.JTextField();
        lblCreInd = new javax.swing.JLabel();
        jrbCreDirNo = new javax.swing.JRadioButton();
        jrbCreDirSi = new javax.swing.JRadioButton();
        lblMonCreDir = new javax.swing.JLabel();
        lblMonCreInd = new javax.swing.JLabel();
        txtMonCreDir = new javax.swing.JTextField();
        txtMonCreInd = new javax.swing.JTextField();
        lblOfiCta = new javax.swing.JLabel();
        txtOfiCta = new javax.swing.JTextField();
        lblMumPro = new javax.swing.JLabel();
        txtNumPro = new javax.swing.JTextField();
        lblObsPro = new javax.swing.JLabel();
        txtObsPro = new javax.swing.JTextField();
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
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);
        lblTit.getAccessibleContext().setAccessibleName("Título");

        panDat.setPreferredSize(new java.awt.Dimension(600, 80));
        panDat.setLayout(new java.awt.BorderLayout());

        panCabDoc.setPreferredSize(new java.awt.Dimension(610, 420));
        panCabDoc.setLayout(null);

        lblCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodDoc.setText("Codigo:");
        lblCodDoc.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDoc.add(lblCodDoc);
        lblCodDoc.setBounds(10, 57, 105, 15);

        txtCodDoc.setPreferredSize(new java.awt.Dimension(100, 20));
        panCabDoc.add(txtCodDoc);
        txtCodDoc.setBounds(118, 55, 66, 20);

        lblNomBco.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNomBco.setText("Nom. Banco:");
        lblNomBco.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDoc.add(lblNomBco);
        lblNomBco.setBounds(10, 82, 105, 15);
        panCabDoc.add(txtNomBco);
        txtNomBco.setBounds(118, 80, 150, 20);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCli.setText("Cliente:");
        lblCli.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblCli);
        lblCli.setBounds(10, 32, 70, 15);

        txtNomCli.setMaximumSize(null);
        txtNomCli.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtNomCli);
        txtNomCli.setBounds(185, 30, 355, 20);

        txtCodCli.setMaximumSize(null);
        txtCodCli.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtCodCli);
        txtCodCli.setBounds(118, 30, 66, 20);

        butSolCre.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butSolCre.setText("...");
        butSolCre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSolCreActionPerformed(evt);
            }
        });
        panCabDoc.add(butSolCre);
        butSolCre.setBounds(185, 5, 24, 20);

        txtCarInf.setMaximumSize(null);
        txtCarInf.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtCarInf);
        txtCarInf.setBounds(450, 280, 150, 20);

        lblCarInf.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCarInf.setText("Cargo Informante:");
        lblCarInf.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblCarInf);
        lblCarInf.setBounds(350, 282, 100, 15);

        lblAge.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblAge.setText("Agencia:");
        lblAge.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblAge);
        lblAge.setBounds(330, 82, 60, 15);

        txtAge.setMaximumSize(null);
        txtAge.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtAge);
        txtAge.setBounds(390, 80, 150, 20);

        lblCiu.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCiu.setText("Ciudad:");
        lblCiu.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblCiu);
        lblCiu.setBounds(10, 157, 80, 15);

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
        panCabDoc.add(txtCiu);
        txtCiu.setBounds(118, 155, 150, 20);

        lblObs1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs1.setText("Observación:");
        lblObs1.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs1.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs1.setPreferredSize(new java.awt.Dimension(92, 8));
        panCabDoc.add(lblObs1);
        lblObs1.setBounds(10, 330, 92, 23);

        txaObs1.setPreferredSize(new java.awt.Dimension(0, 12));
        spnObs1.setViewportView(txaObs1);

        panCabDoc.add(spnObs1);
        spnObs1.setBounds(118, 330, 545, 40);

        lblNumCta.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumCta.setText("Num.Cuenta:");
        lblNumCta.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblNumCta);
        lblNumCta.setBounds(10, 132, 105, 15);

        txtNumCta.setMaximumSize(null);
        txtNumCta.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtNumCta);
        txtNumCta.setBounds(118, 130, 150, 20);

        lblPro.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblPro.setText("Protestos:");
        lblPro.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblPro);
        lblPro.setBounds(10, 207, 105, 15);

        txtInf.setMaximumSize(null);
        txtInf.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtInf);
        txtInf.setBounds(118, 280, 222, 20);

        txtSal.setMaximumSize(null);
        txtSal.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtSal);
        txtSal.setBounds(118, 180, 150, 20);

        lblSal.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblSal.setText("Saldos:");
        lblSal.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblSal);
        lblSal.setBounds(10, 182, 105, 15);

        txtSolCre.setMaximumSize(null);
        txtSolCre.setPreferredSize(new java.awt.Dimension(70, 20));
        txtSolCre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSolCreActionPerformed(evt);
            }
        });
        txtSolCre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSolCreFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSolCreFocusLost(evt);
            }
        });
        panCabDoc.add(txtSolCre);
        txtSolCre.setBounds(118, 5, 66, 20);

        lblSolCre.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblSolCre.setText("Solicitud Credito:");
        lblSolCre.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblSolCre);
        lblSolCre.setBounds(4, 6, 110, 15);

        jrbProSi.setText("Si");
        jrbProSi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbProSiActionPerformed(evt);
            }
        });
        panCabDoc.add(jrbProSi);
        jrbProSi.setBounds(118, 205, 45, 23);

        jrbProNo.setSelected(true);
        jrbProNo.setText("No");
        jrbProNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbProNoActionPerformed(evt);
            }
        });
        panCabDoc.add(jrbProNo);
        jrbProNo.setBounds(160, 205, 45, 23);

        lblCreDir.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCreDir.setText("Cred. Directos:");
        lblCreDir.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblCreDir);
        lblCreDir.setBounds(10, 232, 105, 15);

        lblInf.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblInf.setText("Informante:");
        lblInf.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblInf);
        lblInf.setBounds(10, 282, 105, 15);

        lblDueCta.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDueCta.setText("Dueño Cuenta:");
        lblDueCta.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblDueCta);
        lblDueCta.setBounds(300, 132, 90, 15);

        txtDueCta.setMaximumSize(null);
        txtDueCta.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtDueCta);
        txtDueCta.setBounds(390, 130, 150, 20);

        lblFecRef.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecRef.setText("Fecha Referencia:");
        lblFecRef.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDoc.add(lblFecRef);
        lblFecRef.setBounds(330, 57, 105, 15);

        jrbCreIndSi.setText("Si");
        jrbCreIndSi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbCreIndSiActionPerformed(evt);
            }
        });
        panCabDoc.add(jrbCreIndSi);
        jrbCreIndSi.setBounds(118, 255, 45, 23);

        jrbCreIndNo.setSelected(true);
        jrbCreIndNo.setText("No");
        jrbCreIndNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbCreIndNoActionPerformed(evt);
            }
        });
        panCabDoc.add(jrbCreIndNo);
        jrbCreIndNo.setBounds(160, 255, 45, 23);

        txtDocDig.setMaximumSize(null);
        txtDocDig.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtDocDig);
        txtDocDig.setBounds(118, 305, 495, 20);

        lblDocDig.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDocDig.setText("Doc. Digitalizado:");
        lblDocDig.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblDocDig);
        lblDocDig.setBounds(10, 307, 105, 15);

        butSelArc.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butSelArc.setText("...");
        butSelArc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSelArcActionPerformed(evt);
            }
        });
        panCabDoc.add(butSelArc);
        butSelArc.setBounds(615, 305, 24, 20);

        butVisPre.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butVisPre.setText("...");
        panCabDoc.add(butVisPre);
        butVisPre.setBounds(640, 305, 24, 20);

        butCiu.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butCiu.setText("...");
        butCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCiuActionPerformed(evt);
            }
        });
        panCabDoc.add(butCiu);
        butCiu.setBounds(270, 155, 24, 20);

        lblFecApe.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecApe.setText("Fecha Apert.Cta:");
        lblFecApe.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDoc.add(lblFecApe);
        lblFecApe.setBounds(330, 157, 90, 15);

        txtCodCiu.setMaximumSize(null);
        txtCodCiu.setPreferredSize(new java.awt.Dimension(70, 20));
        txtCodCiu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCiuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCiuFocusLost(evt);
            }
        });
        txtCodCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCiuActionPerformed(evt);
            }
        });
        panCabDoc.add(txtCodCiu);
        txtCodCiu.setBounds(90, 155, 25, 20);

        lblCreInd.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCreInd.setText("Cred. Indirectos:");
        lblCreInd.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblCreInd);
        lblCreInd.setBounds(10, 257, 105, 15);

        jrbCreDirNo.setSelected(true);
        jrbCreDirNo.setText("No");
        jrbCreDirNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbCreDirNoActionPerformed(evt);
            }
        });
        panCabDoc.add(jrbCreDirNo);
        jrbCreDirNo.setBounds(160, 230, 45, 23);

        jrbCreDirSi.setText("Si");
        jrbCreDirSi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbCreDirSiActionPerformed(evt);
            }
        });
        panCabDoc.add(jrbCreDirSi);
        jrbCreDirSi.setBounds(118, 230, 45, 23);

        lblMonCreDir.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMonCreDir.setText("Monto Cred. Dir. :");
        lblMonCreDir.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblMonCreDir);
        lblMonCreDir.setBounds(350, 232, 100, 15);

        lblMonCreInd.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMonCreInd.setText("Monto Cred. Ind. :");
        lblMonCreInd.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblMonCreInd);
        lblMonCreInd.setBounds(350, 257, 100, 15);

        txtMonCreDir.setMaximumSize(null);
        txtMonCreDir.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtMonCreDir);
        txtMonCreDir.setBounds(450, 230, 150, 20);

        txtMonCreInd.setMaximumSize(null);
        txtMonCreInd.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtMonCreInd);
        txtMonCreInd.setBounds(450, 255, 150, 20);

        lblOfiCta.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblOfiCta.setText("Oficial de Cuenta :");
        lblOfiCta.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblOfiCta);
        lblOfiCta.setBounds(10, 107, 105, 15);

        txtOfiCta.setMaximumSize(null);
        txtOfiCta.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtOfiCta);
        txtOfiCta.setBounds(118, 105, 422, 20);

        lblMumPro.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMumPro.setText("Numero");
        lblMumPro.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblMumPro);
        lblMumPro.setBounds(250, 207, 50, 15);

        txtNumPro.setMaximumSize(null);
        txtNumPro.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtNumPro);
        txtNumPro.setBounds(300, 205, 40, 20);

        lblObsPro.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblObsPro.setText("Observacion: ");
        lblObsPro.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblObsPro);
        lblObsPro.setBounds(350, 207, 80, 15);

        txtObsPro.setMaximumSize(null);
        txtObsPro.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtObsPro);
        txtObsPro.setBounds(450, 205, 215, 20);

        jScrollPane1.setViewportView(panCabDoc);

        panDat.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panDat);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butSelArcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSelArcActionPerformed
        //Botón para abrir cuadro de dialogo de abrir documento.
        int retval = chooser.showDialog(frame, null);
        if (retval == JFileChooser.APPROVE_OPTION) {
            if (chooser.isMultiSelectionEnabled()) {
                File[] files = chooser.getSelectedFiles();
                if (files != null && files.length > 0) {
                    String filenames = "";
                    for (int i = 0; i < files.length; i++) {
                        filenames = filenames + "\n" + files[i].getPath();
                    }
                    JOptionPane.showMessageDialog(frame,
                            "You chose these files: \n" + filenames);
                }
            } else {
                File theFile = chooser.getSelectedFile();
                if (theFile != null) {
                    if (theFile.isDirectory()) {
                        JOptionPane.showMessageDialog(frame,
                                "You chose this directory: "
                                + theFile.getPath());
                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "La Ruta del Archivo Seleccionado es: "
                                + theFile.getPath());
                        txtDocDig.setText("" + theFile.getPath());
                    }
                }
            }
        } else if (retval == JFileChooser.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(frame, "User cancelled operation. No file was chosen.");
        } else if (retval == JFileChooser.ERROR_OPTION) {
            JOptionPane.showMessageDialog(frame, "An error occured. No file was chosen.");
        } else {
            JOptionPane.showMessageDialog(frame, "Unknown operation occured.");
        }

    }//GEN-LAST:event_butSelArcActionPerformed

    private void jrbCreDirNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbCreDirNoActionPerformed
        jrbCreDirSi.setSelected(false);
        jrbCreDirNo.setSelected(true);
    }//GEN-LAST:event_jrbCreDirNoActionPerformed

    private void jrbCreDirSiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbCreDirSiActionPerformed
        jrbCreDirSi.setSelected(true);
        jrbCreDirNo.setSelected(false);
        if (jrbCreDirSi.isSelected())
        {
            txtMonCreDir.requestFocus();
        }
    }//GEN-LAST:event_jrbCreDirSiActionPerformed

    private void txtCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCiuFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCiu.getText().equalsIgnoreCase(strCiu))
        {
            if (txtCiu.getText().equals("")) 
            {
                txtCodCiu.setText("");
                txtCiu.setText("");
            }
            else 
            {
                mostrarVenConCiu(2);
            }
        }
        else 
        {
            txtCiu.setText(strCiu);
        }
    }//GEN-LAST:event_txtCiuFocusLost

    private void txtCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCiuFocusGained
        strCiu = txtCiu.getText();
        txtCiu.selectAll();
    }//GEN-LAST:event_txtCiuFocusGained

    private void txtCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiuActionPerformed
        txtCiu.transferFocus();
    }//GEN-LAST:event_txtCiuActionPerformed

    private void butCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCiuActionPerformed
        mostrarVenConCiu(0);
    }//GEN-LAST:event_butCiuActionPerformed

    private void jrbCreIndNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbCreIndNoActionPerformed
        jrbCreIndSi.setSelected(false);
        jrbCreIndNo.setSelected(true);
        if (jrbCreIndNo.isSelected())
        {
            txtInf.requestFocus();
        }
    }//GEN-LAST:event_jrbCreIndNoActionPerformed

    private void jrbCreIndSiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbCreIndSiActionPerformed
        jrbCreIndSi.setSelected(true);
        jrbCreIndNo.setSelected(false);
        if (jrbCreIndSi.isSelected()) 
        {
            txtMonCreInd.requestFocus();
        }
    }//GEN-LAST:event_jrbCreIndSiActionPerformed

    private void jrbProNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbProNoActionPerformed
        jrbProSi.setSelected(false);
        jrbProNo.setSelected(true);
    }//GEN-LAST:event_jrbProNoActionPerformed

    private void jrbProSiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbProSiActionPerformed
        jrbProSi.setSelected(true);
        jrbProNo.setSelected(false);
    }//GEN-LAST:event_jrbProSiActionPerformed

    private void txtSolCreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolCreFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtSolCre.getText().equalsIgnoreCase(strSolCre)) {
            if (txtSolCre.getText().equals("")) {
                txtSolCre.setText("");
                objTblModAcc.removeAllRows();
            } else {
                mostrarVenConSolCre(1);
                if (txtSolCre.getText().equals("")) {
                    txtSolCre.requestFocus();
                } else {
                    txtNomBco.requestFocus();
                }
                cargarDatCli();
            }
        } else {
            txtSolCre.setText(strSolCre);
        }
    }//GEN-LAST:event_txtSolCreFocusLost

    private void txtSolCreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolCreFocusGained
        strSolCre = txtSolCre.getText();
        txtSolCre.selectAll();
    }//GEN-LAST:event_txtSolCreFocusGained

    private void txtSolCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSolCreActionPerformed
        txtSolCre.transferFocus();
    }//GEN-LAST:event_txtSolCreActionPerformed

    /**
     * Esta función configura la "Ventana de consulta" que sería utilizada
     * para mostrar los "Clientes/Proveedores".
     */
    private boolean configurarVenConSolCre() 
    {
        boolean blnRes = true;
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_sol");
            arlCam.add("a1.fe_sol");
            arlCam.add("a1.co_cli");
            arlCam.add("a2.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Cod. Sol.");
            arlAli.add("Fec. Sol.");
            arlAli.add("Cod. Cli.");
            arlAli.add("Nom. Cli.");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("100");
            arlAncCol.add("60");
            arlAncCol.add("300");

            /*Query mejorado para consultar Clientes filtrado por local y empresa*/
            //Armar la sentencia SQL.            
            if (objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())) {
                strSQL = "";
                strSQL += "SELECT a1.co_sol, a1.fe_sol, a1.co_cli, a2.tx_nom";
                strSQL += " FROM tbm_solcre as a1";
                strSQL += " INNER JOIN tbm_cli as a2 on (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli)";
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                {
                    strSQL += " WHERE a1.co_emp=" + CODEMP;
                } else {
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                }

                if (intSig == 1) {
                    ///strTipCliAux="Clientes";
                    strSQL += " AND a2.st_cli='S' and a1.st_reg='A'";
                } else {
                    ///strTipCliAux="Proveedores";
                    strSQL += " AND a2.st_prv='S' and a1.st_reg='A'";
                }

                strSQL += " ORDER BY a2.tx_nom";

            } 
            else 
            {
                strSQL = "";
                strSQL += "SELECT a1.co_sol, a1.fe_sol, a1.co_cli, a2.tx_nom";
                strSQL += " FROM tbm_solcre as a1";
                strSQL += " INNER JOIN tbm_cli as a2 on (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli)";
                strSQL += " INNER JOIN tbr_cliloc as a3 on (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli)";
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) {
                    strSQL += " WHERE a1.co_emp=" + CODEMP;
                } else {
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL += " AND a3.co_loc=" + objParSis.getCodigoLocal();
                }

                if (intSig == 1) {
                    ///strTipCliAux="Clientes";
                    strSQL += " AND a2.st_cli='S' and a1.st_reg='A'";
                } else {
                    ///strTipCliAux="Proveedores";
                    strSQL += " AND a2.st_prv='S' and a1.st_reg='A'";
                }

                strSQL += " ORDER BY a2.tx_nom";

            }

            vcoSolCre = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoSolCre.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
            ///vcoSolCre.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de bósqueda determina si se
     * debe hacer una bósqueda directa (No se muestra la ventana de consulta a
     * menos que no exista lo que se estó buscando) o presentar la ventana de
     * consulta para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de bósqueda a realizar.
     * @return true: Si no se presentó ningón problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConSolCre(int intTipBus) 
    {
        boolean blnRes = true;
        try
        {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoSolCre.setCampoBusqueda(2);
                    vcoSolCre.show();
                    if (vcoSolCre.getSelectedButton() == vcoSolCre.INT_BUT_ACE) {
                        txtSolCre.setText(vcoSolCre.getValueAt(1));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo".
                    if (vcoSolCre.buscar("a1.co_sol", txtSolCre.getText())) {
                        txtSolCre.setText(vcoSolCre.getValueAt(1));
                    } else {
                        vcoSolCre.setCampoBusqueda(0);
                        vcoSolCre.setCriterio1(11);
                        vcoSolCre.cargarDatos();
                        vcoSolCre.show();
                        if (vcoSolCre.getSelectedButton() == vcoSolCre.INT_BUT_ACE) {
                            txtSolCre.setText(vcoSolCre.getValueAt(1));
                        } else {
                            txtSolCre.setText(strSolCre);
                        }
                    }
                    break;
                default:
                    txtSolCre.requestFocus();
                    break;
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
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
    private boolean mostrarVenConCiu(int intTipBus) 
    {
        boolean blnRes = true;
        try
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCiu.setCampoBusqueda(2);
                    vcoCiu.show();
                    if (vcoCiu.getSelectedButton() == vcoCiu.INT_BUT_ACE) 
                    {
                        txtCodCiu.setText(vcoCiu.getValueAt(1));
                        txtCiu.setText(vcoCiu.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCiu.buscar("a1.co_ciu", txtCodCiu.getText())) 
                    {
                        txtCodCiu.setText(vcoCiu.getValueAt(1));
                        txtCiu.setText(vcoCiu.getValueAt(3));
                    }
                    else 
                    {
                        vcoCiu.setCampoBusqueda(0);
                        vcoCiu.setCriterio1(11);
                        vcoCiu.cargarDatos();
                        vcoCiu.show();
                        if (vcoCiu.getSelectedButton() == vcoCiu.INT_BUT_ACE) 
                        {
                            txtCodCiu.setText(vcoCiu.getValueAt(1));
                            txtCiu.setText(vcoCiu.getValueAt(3));
                        }
                        else
                        {
                            txtCodCiu.setText(strCodCiu);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción Larga".
                    if (vcoCiu.buscar("a1.tx_desLar", txtCiu.getText())) 
                    {
                        txtCodCiu.setText(vcoCiu.getValueAt(1));
                        txtCiu.setText(vcoCiu.getValueAt(3));
                    } 
                    else
                    {
                        vcoCiu.setCampoBusqueda(2);
                        vcoCiu.setCriterio1(11);
                        vcoCiu.cargarDatos();
                        vcoCiu.show();
                        if (vcoCiu.getSelectedButton() == vcoCiu.INT_BUT_ACE) 
                        {
                            txtCodCiu.setText(vcoCiu.getValueAt(1));
                            txtCiu.setText(vcoCiu.getValueAt(3));
                        } 
                        else 
                        {
                            txtCiu.setText(strCiu);
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
    
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConCiu() 
    {
        boolean blnRes = true;
        try
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_ciu");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Ciudad");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            //Armar la sentencia SQL.
            String strSQL = "";
            strSQL += " SELECT a1.co_ciu, a1.tx_desCor, a1.tx_desLar ";
            strSQL += " FROM tbm_ciu as a1 ";
            strSQL += " WHERE a1.st_Reg<>'E'"; 
            strSQL += " ORDER BY a1.tx_desLar "; 
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=2;
            
            vcoCiu = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Ciudades", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu=null;
            
            //Configurar columnas.
            vcoCiu.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCiu.setCampoBusqueda(1);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    private void butSolCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSolCreActionPerformed
        mostrarVenConSolCre(0);
        if (txtSolCre.getText().equals("")) {
            txtSolCre.requestFocus();
        } else {
            txtNomBco.requestFocus();
        }
    }//GEN-LAST:event_butSolCreActionPerformed

    /**
     * Cerrar la aplicación.
     */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try {
            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
            strTit = "Mensaje del sistema Zafiro";
            strMsg = "¿Estó seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
                //Cerrar la conexión si estó abierta.
                if (rstCab != null) {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab = null;
                    stmCab = null;
                    conCab = null;
                }
                dispose();
            }
        } catch (java.sql.SQLException e) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCiuActionPerformed
        txtCodCiu.transferFocus();
    }//GEN-LAST:event_txtCodCiuActionPerformed

    private void txtCodCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCiuFocusGained
        strCodCiu = txtCodCiu.getText();
        txtCodCiu.selectAll();
    }//GEN-LAST:event_txtCodCiuFocusGained

    private void txtCodCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCiuFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtCodCiu.getText().equalsIgnoreCase(strCodCiu))
        {
            if (txtCodCiu.getText().equals("")) {

                txtCodCiu.setText("");
                txtCiu.setText("");
            } else {
                mostrarVenConCiu(1);
            }
        }
        else 
        {
            txtCodCiu.setText(strCodCiu);
        }
    }//GEN-LAST:event_txtCodCiuFocusLost

    /**
     * Cerrar la aplicación.
     */
    private void exitForm() {
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrNatCta;
    private javax.swing.ButtonGroup bgrTipCta;
    private javax.swing.JButton butCiu;
    private javax.swing.JButton butSelArc;
    private javax.swing.JButton butSolCre;
    private javax.swing.JButton butVisPre;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton jrbCreDirNo;
    private javax.swing.JRadioButton jrbCreDirSi;
    private javax.swing.JRadioButton jrbCreIndNo;
    private javax.swing.JRadioButton jrbCreIndSi;
    private javax.swing.JRadioButton jrbProNo;
    private javax.swing.JRadioButton jrbProSi;
    private javax.swing.JLabel lblAge;
    private javax.swing.JLabel lblCarInf;
    private javax.swing.JLabel lblCiu;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblCreDir;
    private javax.swing.JLabel lblCreInd;
    private javax.swing.JLabel lblDocDig;
    private javax.swing.JLabel lblDueCta;
    private javax.swing.JLabel lblFecApe;
    private javax.swing.JLabel lblFecRef;
    private javax.swing.JLabel lblInf;
    private javax.swing.JLabel lblMonCreDir;
    private javax.swing.JLabel lblMonCreInd;
    private javax.swing.JLabel lblMumPro;
    private javax.swing.JLabel lblNomBco;
    private javax.swing.JLabel lblNumCta;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObsPro;
    private javax.swing.JLabel lblOfiCta;
    private javax.swing.JLabel lblPro;
    private javax.swing.JLabel lblSal;
    private javax.swing.JLabel lblSolCre;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCabDoc;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panFrm;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextField txtAge;
    private javax.swing.JTextField txtCarInf;
    private javax.swing.JTextField txtCiu;
    private javax.swing.JTextField txtCodCiu;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtDocDig;
    private javax.swing.JTextField txtDueCta;
    private javax.swing.JTextField txtInf;
    private javax.swing.JTextField txtMonCreDir;
    private javax.swing.JTextField txtMonCreInd;
    private javax.swing.JTextField txtNomBco;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNumCta;
    private javax.swing.JTextField txtNumPro;
    private javax.swing.JTextField txtObsPro;
    private javax.swing.JTextField txtOfiCta;
    private javax.swing.JTextField txtSal;
    private javax.swing.JTextField txtSolCre;
    // End of variables declaration//GEN-END:variables

    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (strMsg.equals("")) {
            strMsg = "<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        }
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.WARNING_MESSAGE);
    }

    private boolean consultarReg()
    {
        boolean blnRes = true;
        try {
            if (consultarCabReg()) 
            {
                ///System.out.println("SE CONSULTO CAB DE UN REGISTRO CONSULTADO");
            }
            blnHayCam = false;
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

    /**
     * Esta función permite consultar los registros de acuerdo al criterio
     * seleccionado.
     *
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarCabReg() 
    {
        int intCodEmp, intCodLoc, intultdoc;
        boolean blnRes = true;
        String codmodprg = "";
        try {
            intCodEmp = objParSis.getCodigoEmpresa();
            conCab = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            intultdoc = ultCodDoc();

            ///codmodprg = rtnMod();
            ///System.out.println("---el modulo activo de este programa es: " + codmodprg );
            if (conCab != null) {
                stmCab = conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL = "";
                strSQL += "SELECT a1.co_emp, a1.co_sol, a3.co_cli, a3.tx_nom as tx_nomcli, a1.co_ref, a1.fe_ref, a1.tx_nom as tx_nombco, a1.tx_age, a1.tx_oficta, a1.tx_numcta, a1.tx_duecta, ";
                strSQL += " a1.co_ciuapecta, a1.fe_apecta, a1.tx_salprocta, a1.st_pro, a1.st_credir, a1.st_creind, a1.ne_numpro, a1.tx_obspro, a1.tx_moncredir, a1.tx_moncreind, a1.tx_inf, a1.tx_carinf, ";
                strSQL += " a1.tx_docdig, a1.tx_obs1, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod, a4.tx_deslar "; ///, a4.tx_deslar
                strSQL += " FROM tbm_refbansolcre as a1 ";
                strSQL += " INNER JOIN tbm_solcre as a2 ON (a1.co_emp=a2.co_emp and a1.co_sol=a2.co_sol) ";
                strSQL += " INNER JOIN tbm_cli    as a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli) ";
                strSQL += " INNER JOIN tbm_ciu    as a4 ON (a1.co_ciuapecta=a4.co_ciu) ";
                ///strSQL+=" WHERE a2.co_emp = " + intCodEmp + "";
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) {
                    strSQL += " WHERE a2.co_emp=" + CODEMP;
                } else {
                    strSQL += " WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                }

                strAux = txtSolCre.getText();
                if (!strAux.equals("")) {
                    strSQL += " AND a2.co_sol = " + strAux + "";
                }
                ///strSQL+=" AND a2.co_sol = " + txtSolCre.getText() + "";

                strAux = txtCodDoc.getText();
                if (!strAux.equals("")) {
                    strSQL += " AND a1.co_ref = '" + strAux + "'";
                }

                strSQL += " AND a1.st_reg <> 'E'";
                strSQL += " ORDER BY a2. co_sol";
                //System.out.println("---ConsultarCabReg: " + strSQL);
                rstCab = stmCab.executeQuery(strSQL);
                if (rstCab.next()) {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                    strSQLCon = strSQL;
                } else {
                    mostrarMsgInf("No se ha encontrado ningón registro que cumpla el criterio de bósqueda especificado.");
                    limpiarFrm();
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
    private boolean cargarReg() 
    {
        boolean blnRes = true;
        try {
            if (cargarCabReg()) {
                ///System.out.println("SE CARGO CABECERA Y DETALLE DE UN REGISTRO CONSULTADO");
            }
            blnHayCam = false;
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
    private boolean cargarCabReg() 
    {
        int intPosRel, intCodEmp;
        boolean blnRes = true;
        try
        {
            intCodEmp = objParSis.getCodigoEmpresa();
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                strSQL = "";
                strSQL += "SELECT a1.co_emp, a1.co_sol, a3.co_cli, a3.tx_nom as tx_nomcli, a1.co_ref, a1.fe_ref, a1.tx_nom as tx_nombco, a1.tx_age, a1.tx_oficta, a1.tx_numcta, a1.tx_duecta, a1.co_ciuapecta, ";
                strSQL += " a4.tx_deslar as tx_nomciu, a1.fe_apecta, a1.tx_salprocta, a1.st_pro, a1.st_credir, a1.st_creind, a1.ne_numpro, a1.tx_obspro, a1.tx_moncredir, a1.tx_moncreind, a1.tx_inf, a1.tx_carinf, ";
                strSQL += " a1.tx_docdig, a1.tx_obs1, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod ";
                strSQL += " FROM tbm_refbansolcre as a1 ";
                strSQL += " INNER JOIN tbm_solcre as a2 ON (a1.co_emp=a2.co_emp and a1.co_sol=a2.co_sol) ";
                strSQL += " INNER JOIN tbm_cli    as a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli) ";
                strSQL += " INNER JOIN tbm_ciu    as a4 ON (a1.co_ciuapecta=a4.co_ciu) ";
                strSQL += " WHERE a1.co_emp = " + rstCab.getString("co_emp") + "";
                strSQL += " AND a2.co_sol = " + rstCab.getString("co_sol") + "";
                strSQL += " AND a1.co_ref = " + rstCab.getString("co_ref") + "";
                strSQL += " AND a1.st_reg <> 'E'";
                strSQL += " ORDER BY a1.co_ref ";

                //System.out.println("---CargarCabReg: " + strSQL);
                rst = stm.executeQuery(strSQL);
                if (rst.next()) 
                {
                    txtSolCre.setText(((rst.getString("co_sol") == null) ? "" : rst.getString("co_sol")));
                    txtCodCli.setText(((rst.getString("co_cli") == null) ? "" : rst.getString("co_cli")));
                    txtNomCli.setText(((rst.getString("tx_nomcli") == null) ? "" : rst.getString("tx_nomcli")));
                    txtCodDoc.setText(((rst.getString("co_ref") == null) ? "" : rst.getString("co_ref")));
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_ref"), "dd/MM/yyyy"));
                    txtNomBco.setText(((rst.getString("tx_nombco") == null) ? "" : rst.getString("tx_nombco")));
                    txtAge.setText(((rst.getString("tx_age") == null) ? "" : rst.getString("tx_age")));
                    txtOfiCta.setText(((rst.getString("tx_oficta") == null) ? "" : rst.getString("tx_oficta")));
                    txtNumCta.setText(((rst.getString("tx_numcta") == null) ? "" : rst.getString("tx_numcta")));
                    txtDueCta.setText(((rst.getString("tx_duecta") == null) ? "" : rst.getString("tx_duecta")));
                    txtCodCiu.setText(((rst.getString("co_ciuapecta") == null) ? "" : rst.getString("co_ciuapecta")));
                    txtCiu.setText(((rst.getString("tx_nomciu") == null) ? "" : rst.getString("tx_nomciu")));
                    dtpFecDocApe.setText(objUti.formatearFecha(rst.getDate("fe_apecta"), "dd/MM/yyyy"));
                    txtSal.setText(((rst.getString("tx_salprocta") == null) ? "" : rst.getString("tx_salprocta")));

                    /////Para saber el estado del protesto////
                    String strPro = rst.getString("st_pro");
                    if (strPro.equals("S")) {
                        jrbProSi.setSelected(true);
                        jrbProNo.setSelected(false);
                    } else {
                        jrbProNo.setSelected(true);
                        jrbProSi.setSelected(false);
                    }

                    /////Para saber el estado del credito directo////
                    String strCreDir = rst.getString("st_credir");
                    if (strCreDir.equals("S")) {
                        jrbCreDirSi.setSelected(true);
                        jrbCreDirNo.setSelected(false);
                    } else {
                        jrbCreDirNo.setSelected(true);
                        jrbCreDirSi.setSelected(false);
                    }

                    /////Para saber el estado del credito indirecto////
                    String strCreInd = rst.getString("st_creind");
                    if (strCreInd.equals("S")) {
                        jrbCreIndSi.setSelected(true);
                        jrbCreIndNo.setSelected(false);
                    } else {
                        jrbCreIndNo.setSelected(true);
                        jrbCreIndSi.setSelected(false);
                    }

                    txtNumPro.setText(((rst.getString("ne_numpro") == null) ? "" : rst.getString("ne_numpro")));
                    txtObsPro.setText(((rst.getString("tx_obspro") == null) ? "" : rst.getString("tx_obspro")));
                    txtMonCreDir.setText(((rst.getString("tx_moncredir") == null) ? "" : rst.getString("tx_moncredir")));
                    txtMonCreInd.setText(((rst.getString("tx_moncreind") == null) ? "" : rst.getString("tx_moncreind")));
                    txtInf.setText(((rst.getString("tx_inf") == null) ? "" : rst.getString("tx_inf")));
                    txtCarInf.setText(((rst.getString("tx_carinf") == null) ? "" : rst.getString("tx_carinf")));
                    txtDocDig.setText(((rst.getString("tx_docdig") == null) ? "" : rst.getString("tx_docdig")));
                    txaObs1.setText(((rst.getString("tx_obs1") == null) ? "" : rst.getString("tx_obs1")));

                    //Mostrar el estado del registro.
                    strAux = rstCab.getString("st_reg");
                    if (strAux.equals("A")) {
                        strAux = "Activo";
                    } else if (strAux.equals("I")) {
                        strAux = "Anulado";
                    } else {
                        strAux = "Otro";
                    }
                    objTooBar.setEstadoRegistro(strAux);

                } else {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
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
     * Esta función permite cargar los Datos del Cliente Seleccionado.
     *
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDatCli() 
    {
        int intPosRel, intCodEmp;
        boolean blnRes = true;
        try 
        {
            intCodEmp = objParSis.getCodigoEmpresa();
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                strSQL = "";
                strSQL += "SELECT a1.co_emp, a1.co_sol, a2.co_cli, a2.tx_nom as tx_nomcli ";
                strSQL += " FROM tbm_solcre as a1 ";
                strSQL += " INNER JOIN tbm_cli as a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                strSQL += " WHERE a1.co_emp = " + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND a1.co_sol = " + txtSolCre.getText() + "";
                strSQL += " AND a1.st_reg <> 'E'";
                strSQL += " ORDER BY a1.co_sol ";

                //System.out.println("---cargarDatCli: " + strSQL);
                rst = stm.executeQuery(strSQL);
                if (rst.next()) {
                    ///txtSolCre.setText(((rst.getString("co_sol")==null)?"":rst.getString("co_sol")));
                    txtCodCli.setText(((rst.getString("co_cli") == null) ? "" : rst.getString("co_cli")));
                    txtNomCli.setText(((rst.getString("tx_nomcli") == null) ? "" : rst.getString("tx_nomcli")));
                } else {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes = false;
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

    /**
     * Esta función permite insertar un registro seleccionado.
     *
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg() 
    {
        boolean blnRes = false;
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con != null) {
                if (insertarCabReg()) {
                    con.commit();
                    blnRes = true;
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

    private boolean insertarCabReg() 
    {
        boolean blnRes = true;
        int ultcoddoc;
        try 
        {
            if (con != null) {
                stm = con.createStatement();
                String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());

                //Obtener la fecha del servidor.
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }

                ///obtener el ultimo registro ingresado///
                ultcoddoc = ultCodDoc();
                ultcoddoc++;
                txtCodDoc.setText(String.valueOf(ultcoddoc));

                ///Fechas///
                strAux = objUti.formatearFecha(dtpFecDoc.getText(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos());

                ////strAux=objUti.formatearFecha(strFecSis,"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                boolean fec = dtpFecDocApe.isFecha();
                if (fec == false) {
                    strFecApe = null;
                } else {
                    strFecApe = objUti.formatearFecha(dtpFecDocApe.getText(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos());
                }

                String cociu = "";
                cociu = txtCodCiu.getText();
                
                if (cociu.equals("")) {
                    cociu = null;
                }

                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "INSERT INTO tbm_refbansolcre";
                strSQL += " (co_emp, co_sol, co_ref, fe_ref, tx_nom, tx_age, tx_oficta, tx_numcta, tx_duecta, co_ciuapecta, fe_apecta, ";
                strSQL += " tx_salprocta, st_pro, st_credir, st_creind,  ne_numpro, tx_obspro, tx_moncredir, tx_moncreind, tx_inf, tx_carinf, tx_docdig, ";
                strSQL += " tx_obs1, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod ) ";
                strSQL += " VALUES (";
                strSQL += " " + objParSis.getCodigoEmpresa();            ///co_emp
                strSQL += ", " + txtSolCre.getText();                     ///co_sol
                strSQL += ", " + ultcoddoc;                               ///co_ref

                if (strAux == null || strAux == "") {
                    strSQL += ", " + strAux;                             ///fe_ref
                } else {
                    strSQL += ", '" + strAux + "'";                       ///fe_ref
                }
                strSQL += ", '" + txtNomBco.getText() + "'";              ///tx_nom
                strSQL += ", '" + txtAge.getText() + "'";                 ///tx_age
                strSQL += ", '" + txtOfiCta.getText() + "'";              ///tx_oficta
                strSQL += ", '" + txtNumCta.getText() + "'";              ///tx_numcta
                strSQL += ", '" + txtDueCta.getText() + "'";              ///tx_duecta

                if (cociu == null || cociu == "") {
                    cociu = "0";
                    strSQL += ", " + cociu + "";                          ///co_ciuapecta
                } else {
                    strSQL += ", '" + cociu + "'";                        ///co_ciuapecta
                }
                if (strFecApe == null || strFecApe == "") {
                    strSQL += ", " + strFecApe;                          ///fe_apecta
                } else {
                    strSQL += ", '" + strFecApe + "'";                    ///fe_apecta
                }
                strSQL += ", '" + txtSal.getText() + "'";                 ///tx_salprocta

                //////Para protestos///////
                if (jrbProSi.isSelected()) ///st_pro
                {
                    strSQL += ", 'S'";
                } else {
                    if (jrbProNo.isSelected()) {
                        strSQL += ", 'N'";
                    } else {
                        strSQL += ", 'N'";
                    }
                }

                //////Para creditos Directos///////
                if (jrbCreDirSi.isSelected()) ///st_credir
                {
                    strSQL += ", 'S'";
                } else {
                    if (jrbCreDirNo.isSelected()) {
                        strSQL += ", 'N'";
                    } else {
                        strSQL += ", 'N'";
                    }
                }

                //////Para creditos Indirectos///////
                if (jrbCreIndSi.isSelected()) ///st_creind
                {
                    strSQL += ", 'S'";
                } else {
                    if (jrbCreIndNo.isSelected()) {
                        strSQL += ", 'N'";
                    } else {
                        strSQL += ", 'N'";
                    }
                }

                if (txtNumPro.getText().equals("")) {
                    strSQL += ", null";                                  ///ne_numpro
                } else {
                    strSQL += ", " + txtNumPro.getText() + "";               ///ne_numpro
                }
                strSQL += ", '" + txtObsPro.getText() + "'";             ///tx_obspro
                strSQL += ", '" + txtMonCreDir.getText() + "'";          ///tx_nomcredir
                strSQL += ", '" + txtMonCreInd.getText() + "'";          ///tx_nomcreind
                strSQL += ", '" + txtInf.getText() + "'";                ///tx_inf
                strSQL += ", '" + txtCarInf.getText() + "'";             ///tx_carinf
                strSQL += ", '" + txtDocDig.getText() + "'";             ///tx_docdig
                strSQL += ", '" + txaObs1.getText() + "'";             ///tx_obs1
                strSQL += ", 'A'";                                        ///st_reg
                strSQL += ", '" + strFecSis + "'";                        ///fe_ing
                strSQL += ", null ";                                      ///fe_ultmod
                strSQL += ", " + objParSis.getCodigoUsuario();            ///co_usring
                strSQL += ", null ";                                      ///co_usrmod
                strSQL += ")";
                //System.out.println("---SQL del Insert tbm_refBansolcre: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
                datFecAux = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            //System.out.println("---Error1-insertarcabReg()--- ");
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            //System.out.println("---Error2-insertarcabReg()--- ");
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite eliminar un registro seleccionado.
     *
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg() 
    {
        boolean blnRes = false;
        try {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con != null) {
                if (eliminarCabReg()) {
                    con.commit();
                    blnRes = true;
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

    private boolean eliminarCabReg()
    {
        boolean blnRes = true;
        try {
            if (con != null) {
                stm = con.createStatement();
                String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
                //Obtener la fecha del servidor.
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }

                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "UPDATE tbm_refbansolcre";
                strSQL += " SET ";
                strSQL += " st_reg = 'E'";
                strSQL += ", fe_ultmod = '" + strFecSis + "'";
                strSQL += ", co_usrmod = '" + objParSis.getCodigoUsuario() + "'";
                strSQL += " WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL += " AND co_sol=" + rstCab.getString("co_sol");
                strSQL += " AND co_ref=" + txtCodDoc.getText();
                //System.out.println("---SQL del EliminarReg tbm_RefBanSolCre: " + strSQL);
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
     * Esta función permite anular un registro seleccionado.
     *
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg() 
    {
        boolean blnRes = false;
        try
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con != null) {
                if (anularCabReg()) {
                    con.commit();
                    blnRes = true;
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

    private boolean anularCabReg() 
    {
        boolean blnRes = true;
        try {
            if (con != null) {
                stm = con.createStatement();
                String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
                //Obtener la fecha del servidor.
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }

                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "UPDATE tbm_refbansolcre";
                strSQL += " SET ";
                strSQL += " st_reg = 'I'";
                strSQL += ", fe_ultmod = '" + strFecSis + "'";
                strSQL += ", co_usrmod = '" + objParSis.getCodigoUsuario() + "'";
                strSQL += " WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL += " AND co_sol=" + rstCab.getString("co_sol");
                strSQL += " AND co_ref=" + txtCodDoc.getText();
                //System.out.println("---SQL del AnularReg tbm_RefBanSolCre: " + strSQL);
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
     * Esta función actualiza el registro en la base de datos.
     *
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg()
    {
        boolean blnRes = false;
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con != null) {
                if (actualizarCabReg()) {
                    con.commit();
                    blnRes = true;
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

    private boolean actualizarCabReg()
    {
        boolean blnRes = true;
        try {
            if (con != null) {
                stm = con.createStatement();
                String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
                //Obtener la fecha del servidor.
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }

                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "UPDATE tbm_refbansolcre";
                strSQL += " SET ";
                strSQL += " tx_nom = '" + txtNomBco.getText() + "'";
                strAux = objUti.formatearFecha(dtpFecDoc.getText(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos());
                strSQL += ", fe_ref = '" + strAux + "'";
                strSQL += ", tx_age = '" + txtAge.getText() + "'";
                strSQL += ", tx_oficta = '" + txtOfiCta.getText() + "'";
                strSQL += ", tx_numcta = '" + txtNumCta.getText() + "'";
                strSQL += ", tx_duecta = '" + txtDueCta.getText() + "'";
                strSQL += ", co_ciuapecta = " + txtCodCiu.getText() + "";
                strFecApe = objUti.formatearFecha(dtpFecDocApe.getText(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos());
                strSQL += ", fe_apecta = '" + strFecApe + "'";
                strSQL += ", tx_salprocta = '" + txtSal.getText() + "'";

                /// estados protestados ///
                if (jrbProSi.isSelected()) {
                    strSQL += ", st_pro = 'S'";
                } else {
                    if (jrbProNo.isSelected()) {
                        strSQL += ", st_pro = 'N'";
                    } else {
                        strSQL += ", 'N'";
                    }
                }

                /// estados creditos Directos///
                if (jrbCreDirSi.isSelected()) {
                    strSQL += ", st_credir = 'S'";
                } else {
                    if (jrbCreDirNo.isSelected()) {
                        strSQL += ", st_credir = 'N'";
                    } else {
                        strSQL += ", 'N'";
                    }
                }

                /// estados creditos Indirectos///
                if (jrbCreIndSi.isSelected()) {
                    strSQL += ", st_creind = 'S'";
                } else {
                    if (jrbCreIndNo.isSelected()) {
                        strSQL += ", st_creind = 'N'";
                    } else {
                        strSQL += ", 'N'";
                    }
                }

                ///strSQL+=", ne_numpro = '" + txtNumPro.getText() + "'";
                if (txtNumPro.getText().equals("")) {
                    strSQL += ", ne_numpro = null";
                } else {
                    strSQL += ", ne_numpro = " + txtNumPro.getText() + "";
                }

                strSQL += ", tx_obspro = '" + txtObsPro.getText() + "'";
                strSQL += ", tx_moncredir = '" + txtMonCreDir.getText() + "'";
                strSQL += ", tx_moncreind = '" + txtMonCreInd.getText() + "'";
                strSQL += ", tx_inf = '" + txtInf.getText() + "'";
                strSQL += ", tx_carinf = '" + txtCarInf.getText() + "'";
                strSQL += ", tx_docdig = '" + txtDocDig.getText() + "'";
                strSQL += ", tx_obs1 = '" + txaObs1.getText() + "'";
                strSQL += ", fe_ultmod = '" + strFecSis + "'";
                strSQL += ", co_usrmod = '" + objParSis.getCodigoUsuario() + "'";
                strSQL += " WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL += " AND co_sol=" + rstCab.getString("co_sol");
                strSQL += " AND co_ref=" + txtCodDoc.getText();
                //System.out.println("---SQL del update tbm_refbansolcre: " + strSQL);
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
     * Configurar el formulario.
     */
    private boolean configurarFrm()
    {
        boolean blnRes = true;
        try 
        {
            //titulo de la ventana
            this.setTitle(objParSis.getNombreMenu() + "v0.1.8");
            lblTit.setText("" + objParSis.getNombreMenu());

            configurarVenConSolCre(); //Configuracion de la Ventana de Consulta de Solicitud de Credito.
            configurarVenConCiu();    //Configuracion de la Ventana de Consulta de Ciudades.

            //asigno el color de fondo de los campos 
            txtSolCre.setBackground(objParSis.getColorCamposObligatorios());
            txtCodCli.setBackground(objParSis.getColorCamposObligatorios());
            txtNomCli.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtNomBco.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDocApe.setBackground(objParSis.getColorCamposObligatorios());
            txtCodCiu.setVisible(false);

            ///botones de seleccion///
            jrbProNo.setSelected(true);
            jrbCreDirNo.setSelected(true);
            jrbCreIndNo.setSelected(true);

            butSelArc.setEnabled(false);
            butVisPre.setEnabled(false);
            txtDocDig.setEditable(false);

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;

    }

    public void setEditable(boolean editable)
    {
        if (editable == true) {
            objTblModAcc.setModoOperacion(objTblModAcc.INT_TBL_EDI);
        } else {
            objTblModAcc.setModoOperacion(objTblModAcc.INT_TBL_NO_EDI);
        }
    }

    //PARA LA BARRA DE HERRAMIENTAS
    private boolean limpiarFrm()
    {
        boolean blnRes = true;
        try 
        {
            dtpFecDoc.setText("");
            dtpFecDocApe.setText("");
            txtSolCre.setText("");
            txtCodCli.setText("");
            txtNomCli.setText("");
            txtCodDoc.setText("");
            txtNomBco.setText("");
            txtAge.setText("");
            txtOfiCta.setText("");
            txtNumCta.setText("");
            txtDueCta.setText("");
            txtCodCiu.setText("");
            txtCiu.setText("");
            txtSal.setText("");
            txtInf.setText("");
            txtCarInf.setText("");
            txtDocDig.setText("");
            txaObs1.setText("");
            jrbProSi.setSelected(false);
            jrbProNo.setSelected(false);
            jrbCreDirSi.setSelected(false);
            jrbCreDirNo.setSelected(false);
            jrbCreIndSi.setSelected(false);
            jrbCreIndNo.setSelected(false);
            txtNumPro.setText("");
            txtObsPro.setText("");
            txtMonCreDir.setText("");
            txtMonCreInd.setText("");
            ///jCbxCla.setSelectedIndex(-1);
            objTooBar.setEstadoRegistro("");
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private int mostrarMsgCon(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    private void consultarCamUsr() 
    {
        strAux = "Desea guardar los cambios efectuados a éste registro?\n";
        strAux += "Si no guarda los cambios perderá toda la información que no haya guardado.";

        switch (mostrarMsgCon(strAux)) {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado()) {
                    case 'm': //Modificar
                        //System.out.println("Modo Modificar");
                        break;
                    case 'e': //Eliminar
                        //System.out.println("Modo Eliminar");
                        break;
                    case 'n': //insertar
                        //System.out.println("Modo Ninguno");
//                        objTooBar.insertar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                break;
            case 2: //CANCEL_OPTION
                break;
        }

    }

    private boolean isRegPro() 
    {
        boolean blnRes = true;
        strAux = "Desea guardar los cambios efectuados a éste registro?\n";
        strAux += "Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado()) {
                    case 'n': //Insertar
                        blnRes = objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes = objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
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
     * Esta funcion valida si los campos obligatorios han sido completados
     * correctamente
     *
     * @return True si los campos obligatorios se llenaron correctamente.
     */
    private boolean isCamVal()
    {
        if (txtSolCre.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Solicitud de Credito</FONT> es obligatorio.<BR>Escriba un código de solicitud de credito y vuelva a intentarlo.</HTML>");
            txtSolCre.requestFocus();
            return false;
        }

        if (txtNomBco.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre del Banco </FONT> es obligatorio.<BR>Escriba un Nombre de Banco y vuelva a intentarlo.</HTML>");
            txtNomBco.requestFocus();
            return false;
        }

        if (dtpFecDocApe.isFecha() == false) {
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de Apertura de Cuenta </FONT> es obligatorio.<BR>Escriba una Fecha y vuelva a intentarlo.</HTML>");
            return false;
        }
        return true;
    }

    protected int ultCodDoc() 
    {
        int intUltDoc = 0;
        boolean blnRes = true;
        java.sql.Connection conNumDoc;
        java.sql.Statement stmNumDoc;
        java.sql.ResultSet rstNumDoc;
        try 
        {
            conNumDoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conNumDoc != null) {
                stmNumDoc = conNumDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String sqlDoc = "";
                sqlDoc += "select max(b1.co_ref) as ultdoc ";
                sqlDoc += " from tbm_refbansolcre as b1 ";
                sqlDoc += " inner join tbm_solcre as b2 ON ";
                sqlDoc += " (b1.co_emp=b2.co_emp AND b1.co_sol=b2.co_sol) ";
                ///sqlDoc+=" where b1.co_emp=" + objParSis.getCodigoEmpresa() + "";

                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) {
                    sqlDoc += " WHERE b1.co_emp=" + CODEMP;
                } else {
                    sqlDoc += " WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                }

                strAux = txtSolCre.getText();
                if (!strAux.equals("")) {
                    sqlDoc += " AND b2.co_sol = " + strAux + "";
                }
                ///sqlDoc+=" and b2.co_sol=" + txtSolCre.getText() + "";

                //System.out.println(" ---query ultcoddoc: " + sqlDoc);
                intUltDoc = objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), sqlDoc);
                ///intUltDoc++;
                rstNumDoc = stmNumDoc.executeQuery(sqlDoc);
                if (rstNumDoc.next()) {
                    //System.out.println("--el ultimo codigo del documento es:" + intUltDoc);
                }
            }
            conNumDoc.close();
            conNumDoc = null;
            stmNumDoc = null;
            rstNumDoc = null;

        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intUltDoc;
    }

    //////funcion para un futuro///////
    protected String rtnMod() 
    {
        String modprg = "";
        int intUltDoc = 0;
        boolean blnRes = true;
        java.sql.Connection conModDoc;
        java.sql.Statement stmModDoc;
        java.sql.ResultSet rstModDoc;
        try 
        {
            conModDoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conModDoc != null) {
                stmModDoc = conModDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String sqlDoc = "";
                sqlDoc += "select max(a2.ne_mod) as modulo ";
                sqlDoc += " from tbr_tipdocprg as a1 ";
                sqlDoc += " inner join tbm_cabtipdoc as a2 ON ";
                sqlDoc += " (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc) ";
                ///sqlDoc+=" where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) {
                    sqlDoc += " WHERE a1.co_emp=" + CODEMP;
                } else {
                    sqlDoc += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                }

                sqlDoc += " and a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                //System.out.println(" ---query modulo del programa: " + sqlDoc);
                rstModDoc = stmModDoc.executeQuery(sqlDoc);
                if (rstModDoc.next()) ///txtCodDoc.setText(String.valueOf(intUltDoc));
                {
                    modprg = rstModDoc.getString("modulo");
                }
                //System.out.println("el modulo del menu es:" + modprg);
            }
            conModDoc.close();
            conModDoc = null;
            stmModDoc = null;
            rstModDoc = null;

        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return modprg;
    }

    public class mitoolbar extends ZafToolBar 
    {

        public mitoolbar(javax.swing.JInternalFrame jfrThis) {
            super(jfrThis, objParSis);
        }

        public void clickInsertar() {
            try {
                if (blnHayCam) {
                    isRegPro();
                }
                if (rstCab != null) {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab = null;
                    stmCab = null;
                    conCab = null;
                }
                limpiarFrm();
                txtSolCre.requestFocus();
                txtCodCli.setEditable(false);
                txtNomCli.setEditable(false);
                txtCodDoc.setEditable(false);

                ///botones de seleccion///
                jrbProNo.setSelected(true);
                jrbCreDirNo.setSelected(true);
                jrbCreIndNo.setSelected(true);

                ////establecer fecha para la referencia///
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux, "dd/MM/yyyy"));
                datFecAux = null;

                blnHayCam = false;
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public boolean insertar() {
            if (!insertarReg()) {
                return false;
            }

            return true;
        }

        public void clickEliminar() {
            consultarCabReg();
        }

        public boolean eliminar() {
            try {
                String strAux = objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado")) {
                    mostrarMsgInf("El documento ya estó ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                    return false;
                }
                if (!eliminarReg()) {
                    return false;
                }
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast()) {
                    rstCab.next();
                    cargarReg();
                } else {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }
                blnHayCam = false;
            } catch (java.sql.SQLException e) {
                return true;
            }
            return true;
        }

        public void clickAnular() {
            consultarCabReg();
        }

        public boolean anular() {
            try {
                String strAux = objTooBar.getEstadoRegistro();
                if (strAux.equals("Anulado")) {
                    mostrarMsgInf("El documento ya estó ANULADO.\n No es posible ANULAR un documento anulado.");
                    return false;
                }
                if (!anularReg()) {
                    return false;
                }

                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast()) {
                    rstCab.next();
                    cargarReg();
                } else {
                    objTooBar.setEstadoRegistro("Anulado");
                    ///limpiarFrm();
                }
                blnHayCam = false;
            } catch (java.sql.SQLException e) {
                return true;
            }
            return true;
        }

        public void clickConsultar() {
            txtSolCre.requestFocus();
            txtCodCli.setEditable(false);
            txtNomCli.setEditable(false);
        }

        public boolean consultar() {
            consultarReg();
            return true;
        }

        public void clickModificar() {
            txtSolCre.setEditable(false);
            txtCodCli.setEditable(false);
            txtNomCli.setEditable(false);
            txtCodDoc.setEditable(false);
            txtNomBco.requestFocus();
            butSolCre.setEnabled(false);
            consultarCabReg();
        }

        public boolean modificar() {
            if (!actualizarReg()) {
                return false;
            }
            return true;

        }

        public void clickInicio() {
            try {
                if (!rstCab.isFirst()) {
                    if (blnHayCam) {
                        if (isRegPro()) {
                            limpiarFrm();
                            rstCab.first();
                            cargarReg();
                        }
                    } else {
                        limpiarFrm();
                        rstCab.first();
                        cargarReg();
                    }
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickAnterior() {
            try {
                if (!rstCab.isFirst()) {
                    if (blnHayCam) {
                        if (isRegPro()) {
                            limpiarFrm();
                            rstCab.previous();
                            cargarReg();
                        }
                    } else {
                        limpiarFrm();
                        rstCab.previous();
                        cargarReg();
                    }
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
                    if (blnHayCam) {
                        if (isRegPro()) {
                            limpiarFrm();
                            rstCab.next();
                            cargarReg();
                        }

                    } else {
                        limpiarFrm();
                        rstCab.next();
                        cargarReg();
                    }
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
                    if (blnHayCam) {
                        if (isRegPro()) {

                            limpiarFrm();
                            rstCab.last();
                            cargarReg();
                        }
                    } else {
                        limpiarFrm();
                        rstCab.last();
                        cargarReg();
                    }
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickAceptar() {
            limpiarFrm();
        }

        public void clickCancelar() {
            limpiarFrm();
        }

        private int Mensaje()
        {
            String strTit, strMsg;
            strTit = "Mensaje del sistema Zafiro";
            strMsg = "Desea guardar los cambios efectuados a óéste registro?\n";
            strMsg += "Si no guarda los cambios perderá toda la información que no haya guardado.";

            javax.swing.JOptionPane obj = new javax.swing.JOptionPane();

            return obj.showConfirmDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);

        }

        private void MensajeValidaCampo(String strNomCampo) 
        {
            javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
            String strTit, strMsg;
            strTit = "Mensaje del sistema Zafiro";
            strMsg = "El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
            obj.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
            blnHayCam = false;
            //objTooBar.setEstado('n');
            this.setEstado('w');
            blnHayCam = false;
            ///limpiarFrm();
            consultarCabReg();
            return true;
        }

        public boolean afterModificar() {
            blnHayCam = false;
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
                    stmCab.close();
                    conCab.close();
                    rstCab = null;
                    stmCab = null;
                    conCab = null;
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam = false;
            return blnRes;
        }

        public boolean afterConsultar() {
            return true;
        }

        public boolean beforeInsertar() {
            if (!isCamVal()) {
                return false;
            }
            return true;
        }

        public boolean beforeConsultar()
        {
            return true;
        }

        public boolean beforeModificar() {
            strAux = objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")) {
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            if (!isCamVal()) {
                return false;
            }

            return true;
        }

        public boolean beforeEliminar() {
            strAux = objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) {
                mostrarMsgInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }

        public boolean beforeAnular() {
            strAux = objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")) {
                mostrarMsgInf("El documento ya estó ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            return true;
        }

        public boolean beforeImprimir() {
            return true;
        }

        public boolean beforeVistaPreliminar() {
            return true;
        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }
    }

    class tblHilo extends Thread {

        public void run() {
            //System.out.println("antes de llamar a monDoc");
            //ó("despues de llamar a monDoc");
        }
    }

    class ZafDocLis implements javax.swing.event.DocumentListener {

        public void changedUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }
    }
}
