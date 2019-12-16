/*
 * CxC.ZafCxC53
 *
 * Created on 17 de Febrero de 2009, 15:10 PM
 * ANALISIS DE ANTIGUEDAD DE SALDOS DE CLIENTES POR GRUPO DE EMPRESAS
 * DARIO CARDENAS  CREADO EL 17/FEBRERO/2009
 */

package CxC.ZafCxC53;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafSelFec.ZafSelFec;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafDate.ZafDatePicker;//esto es para calcular el numero de dias transcurridos
import Librerias.ZafDate.ZafSelectDate;//esto es para calcular el numero de dias transcurridos
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;

/**
 *
 * @author  Dario Cardenas
 */
public class ZafCxC53 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    final int INT_TBL_DAT_FAC_LIN=0;                    //Línea
    final int INT_TBL_DAT_FAC_COD_EMP=1;                //Código de la empresa.    
    final int INT_TBL_DAT_FAC_COD_CLI=2;                //Código del cliente.
    final int INT_TBL_DAT_FAC_RUC_CED=3;                //Código del RUC O CEDULA DEL CLIENTE
    final int INT_TBL_DAT_FAC_NOM_CLI=4;                //Nombre del cliente.
    final int INT_TBL_DAT_FAC_VAL_PEN=5;                //Valor Pendiente.
    final int INT_TBL_DAT_FAC_VAL_VEN=6;                //Valor por vencer.
    final int INT_TBL_DAT_FAC_VAL_30D=7;                //Valor vencido (0-30 días).
    final int INT_TBL_DAT_FAC_VAL_60D=8;                //Valor vencido (31-60 días).
    final int INT_TBL_DAT_FAC_VAL_90D=9;                //Valor vencido (61-90 días).
    final int INT_TBL_DAT_FAC_VAL_MAS=10;               //Valor vencido (Más de 90 días).
    
    
    //Constantes: Columnas del JTable://
    final int INT_TBL_DAT_LIN=0;                        //Línea
    final int INT_TBL_DAT_COD_EMP=1;                    //Código de la empresa.
    final int INT_TBL_DAT_COD_LOC=2;                    //Código del local.
    final int INT_TBL_DAT_COD_CLI=3;                    //Código del cliente.
    final int INT_TBL_DAT_RUC_CED=4;                    //Código del RUC O CEDULA.
    final int INT_TBL_DAT_NOM_CLI=5;                    //Nombre del cliente.
    final int INT_TBL_DAT_COD_TIP_DOC=6;                //Código del Tipo de Documento.
    final int INT_TBL_DAT_DEC_TIP_DOC=7;                //Descripción corta del Tipo de Documento.
    final int INT_TBL_DAT_DEL_TIP_DOC=8;                //Descripción larga del Tipo de Documento.
    final int INT_TBL_DAT_COD_DOC=9;                    //Código del documento.
    final int INT_TBL_DAT_COD_REG=10;                   //Código del registro.
    final int INT_TBL_DAT_NUM_DOC=11;                   //Número de documento.
    ///final int INT_TBL_DAT_FEC_DOC=11;                //Fecha del documento.
    final int INT_TBL_DAT_DIA_CRE=12;                   //Días de crédito.
    final int INT_TBL_DAT_FEC_VEN=13;                   //Fecha de vencimiento.
    final int INT_TBL_DAT_POR_RET=14;                   //Porcentaje de retención.
    final int INT_TBL_DAT_VAL_DOC=15;                   //Valor del documento.
    final int INT_TBL_DAT_VAL_ABO=16;                   //Valor del Abono.
    final int INT_TBL_DAT_VAL_PEN=17;                   //Valor Pendiente.
    final int INT_TBL_DAT_VAL_VEN=18;                   //Valor por vencer.
    final int INT_TBL_DAT_VAL_30D=19;                   //Valor vencido (0-30 días).
    final int INT_TBL_DAT_VAL_60D=20;                   //Valor vencido (31-60 días).
    final int INT_TBL_DAT_VAL_90D=21;                   //Valor vencido (61-90 días).
    final int INT_TBL_DAT_VAL_MAS=22;                   //Valor vencido (Más de 90 días).
    final int INT_TBL_DAT_COD_BAN=23;                   //Código del Banco.
    final int INT_TBL_DAT_NOM_BAN=24;                   //Nombre del Banco.
    final int INT_TBL_DAT_NUM_CTA=25;                   //Número de cuenta.
    final int INT_TBL_DAT_NUM_CHQ=26;                   //Número de cheque.
    final int INT_TBL_DAT_FEC_REC_CHQ=27;               //Fecha de recepción del cheque.
    final int INT_TBL_DAT_FEC_VEN_CHQ=28;               //Fecha de vencimiento del cheque.
    final int INT_TBL_DAT_FEC_DOC=29;                   //Fecha del documento.
    final int INT_TBL_DAT_VAL_CHQ=30;                   //Valor del cheque.
    
    //Variables
    private ZafSelectDate objSelDate;
    private ZafSelFec objSelFec;
    private ZafDatePicker dtpDat;                       //esto es para calcular el numero de dias transcurridos
    private ZafUtil objAux;
   // private java.util.Date datFecAux;
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod, objTblModFac;
    private ZafTblMod objTblModDab;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda, objMouMotAdaAux;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                        //Editor de búsqueda.
    private ZafTblTot objTblTot, objTotDatMovFac;                        //JTable de totales.
    private Connection con, conFac;
    private Statement stm, stmFac;
    private ResultSet rst, rstFac;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatFac, vecCabFac, vecRegFac;
    private boolean blnCon, OPTFEC;                             //true: Continua la ejecución del hilo.
    private String strCodCli, strDesLarCli,strCodEmp, strDesLarEmp,strCodTipDoc, strDesLarTipDoc,strCodEmpTipDoc, strDesLarEmpTipDoc;             //Contenido del campo al obtener el foco.
    private String strCodLoc, strNomLoc, strRucCli, strCodGrpCli, strNomGrpCli;          //Contenido del campo al obtener el foco.
    private int intCodLocAux, intSig=1, INTCODEMP=0, INTCODEMPGRP=0;
    private String strTipDoc;        //Contenido del campo al obtener el foco.
    private String strFecSeldes = "";
    private String strFecSelhas = "";
    private ZafVenCon vcoCli;                                   //Ventana de consulta "Cliente".
    private ZafVenCon vcoGrpCli;                                //Ventana de consulta "Grupo de Cliente".
    private ZafVenCon vcoTipDoc;                                //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoLoc;                                   //Ventana de consulta "Local".
    private ZafVenCon vcoEmp;                                   //Ventana de consulta "Empresa".
    private Librerias.ZafDate.ZafDatePicker dtpFecDoc, dtpDatFacCor;
    private ZafTblOrd objTblOrd, objTblOrdDet;
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCxC53(ZafParSis obj) 
    {
        initComponents();
        
        //Inicializar objetos.
        objParSis=obj;
        
        if (!configurarFrm())
            exitForm();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtRucCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        txtCodEmp = new javax.swing.JTextField();
        txtDesLarEmp = new javax.swing.JTextField();
        lblEmp = new javax.swing.JLabel();
        butEmp = new javax.swing.JButton();
        lblGrpCli = new javax.swing.JLabel();
        txtCodGrpCli = new javax.swing.JTextField();
        txtNomGrpCli = new javax.swing.JTextField();
        butGrpCli = new javax.swing.JButton();
        chkMosRet = new javax.swing.JCheckBox();
        chkMosDocVen = new javax.swing.JCheckBox();
        chkMosDeb = new javax.swing.JCheckBox();
        chkMosCre = new javax.swing.JCheckBox();
        lblLoc = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtDesLarLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        panNomCli = new javax.swing.JPanel();
        lblNomCliDes = new javax.swing.JLabel();
        txtNomCliDes = new javax.swing.JTextField();
        lblNomCliHas = new javax.swing.JLabel();
        txtNomCliHas = new javax.swing.JTextField();
        lblFecCor = new javax.swing.JLabel();
        panPagRea = new javax.swing.JPanel();
        sppPagRea = new javax.swing.JSplitPane();
        panPagReaReg = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panPagReaMovReg = new javax.swing.JPanel();
        chkDatFacMosMovReg = new javax.swing.JCheckBox();
        spnDatMov = new javax.swing.JScrollPane();
        tblDatFacMovReg = new javax.swing.JTable();
        spnTotMovDat = new javax.swing.JScrollPane();
        tblTotMovDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana"); // NOI18N
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana"); // NOI18N
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los clientes"); // NOI18N
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 120, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los clientes que cumplan el criterio seleccionado"); // NOI18N
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(4, 140, 400, 20);

        lblCli.setText("Cliente:"); // NOI18N
        lblCli.setToolTipText("Beneficiario"); // NOI18N
        panFil.add(lblCli);
        lblCli.setBounds(24, 185, 120, 20);

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
        panFil.add(txtCodCli);
        txtCodCli.setBounds(144, 185, 56, 20);

        txtRucCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRucCliActionPerformed(evt);
            }
        });
        txtRucCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtRucCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRucCliFocusLost(evt);
            }
        });
        panFil.add(txtRucCli);
        txtRucCli.setBounds(200, 185, 110, 20);

        txtDesLarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCliActionPerformed(evt);
            }
        });
        txtDesLarCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusLost(evt);
            }
        });
        panFil.add(txtDesLarCli);
        txtDesLarCli.setBounds(310, 185, 350, 20);

        butCli.setText("..."); // NOI18N
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(660, 185, 20, 20);

        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(80, 10, 56, 20);

        txtDesLarEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarEmpActionPerformed(evt);
            }
        });
        txtDesLarEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarEmpFocusLost(evt);
            }
        });
        panFil.add(txtDesLarEmp);
        txtDesLarEmp.setBounds(136, 10, 220, 20);

        lblEmp.setText("Empresa:"); // NOI18N
        lblEmp.setToolTipText("Beneficiario"); // NOI18N
        panFil.add(lblEmp);
        lblEmp.setBounds(15, 10, 60, 20);

        butEmp.setText("..."); // NOI18N
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(357, 10, 20, 20);

        lblGrpCli.setText("Grupo de Cliente:"); // NOI18N
        lblGrpCli.setToolTipText("Beneficiario"); // NOI18N
        panFil.add(lblGrpCli);
        lblGrpCli.setBounds(24, 160, 120, 20);

        txtCodGrpCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodGrpCliActionPerformed(evt);
            }
        });
        txtCodGrpCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodGrpCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodGrpCliFocusLost(evt);
            }
        });
        panFil.add(txtCodGrpCli);
        txtCodGrpCli.setBounds(144, 160, 56, 20);

        txtNomGrpCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomGrpCliActionPerformed(evt);
            }
        });
        txtNomGrpCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomGrpCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomGrpCliFocusLost(evt);
            }
        });
        panFil.add(txtNomGrpCli);
        txtNomGrpCli.setBounds(200, 160, 460, 20);

        butGrpCli.setText("..."); // NOI18N
        butGrpCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGrpCliActionPerformed(evt);
            }
        });
        panFil.add(butGrpCli);
        butGrpCli.setBounds(660, 160, 20, 20);

        chkMosRet.setSelected(true);
        chkMosRet.setText("Mostrar las Retenciones"); // NOI18N
        panFil.add(chkMosRet);
        chkMosRet.setBounds(350, 60, 190, 23);

        chkMosDocVen.setText("Mostrar sólo los documentos vencidos"); // NOI18N
        panFil.add(chkMosDocVen);
        chkMosDocVen.setBounds(350, 85, 270, 23);

        chkMosDeb.setSelected(true);
        chkMosDeb.setText("Mostrar los Débitos"); // NOI18N
        chkMosDeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDebActionPerformed(evt);
            }
        });
        panFil.add(chkMosDeb);
        chkMosDeb.setBounds(30, 85, 190, 23);

        chkMosCre.setSelected(true);
        chkMosCre.setText("Mostrar los Créditos"); // NOI18N
        chkMosCre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosCreActionPerformed(evt);
            }
        });
        panFil.add(chkMosCre);
        chkMosCre.setBounds(30, 60, 190, 23);

        lblLoc.setText("Local:"); // NOI18N
        lblLoc.setToolTipText("Beneficiario"); // NOI18N
        panFil.add(lblLoc);
        lblLoc.setBounds(15, 35, 55, 20);

        txtCodLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLocActionPerformed(evt);
            }
        });
        txtCodLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLocFocusLost(evt);
            }
        });
        panFil.add(txtCodLoc);
        txtCodLoc.setBounds(80, 35, 56, 20);

        txtDesLarLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarLocActionPerformed(evt);
            }
        });
        txtDesLarLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarLocFocusLost(evt);
            }
        });
        panFil.add(txtDesLarLoc);
        txtDesLarLoc.setBounds(136, 35, 220, 20);

        butLoc.setText("..."); // NOI18N
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panFil.add(butLoc);
        butLoc.setBounds(357, 35, 20, 20);

        panNomCli.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre de cliente"));
        panNomCli.setLayout(null);

        lblNomCliDes.setText("Desde:"); // NOI18N
        panNomCli.add(lblNomCliDes);
        lblNomCliDes.setBounds(12, 20, 44, 20);

        txtNomCliDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliDesFocusLost(evt);
            }
        });
        panNomCli.add(txtNomCliDes);
        txtNomCliDes.setBounds(56, 20, 268, 20);

        lblNomCliHas.setText("Hasta:"); // NOI18N
        panNomCli.add(lblNomCliHas);
        lblNomCliHas.setBounds(336, 20, 44, 20);

        txtNomCliHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusLost(evt);
            }
        });
        panNomCli.add(txtNomCliHas);
        txtNomCliHas.setBounds(380, 20, 268, 20);

        panFil.add(panNomCli);
        panNomCli.setBounds(20, 210, 660, 52);

        lblFecCor.setText("Fecha de Corte:"); // NOI18N
        lblFecCor.setToolTipText("Beneficiario"); // NOI18N
        panFil.add(lblFecCor);
        lblFecCor.setBounds(420, 35, 100, 20);

        tabFrm.addTab("Filtro", panFil);

        panPagRea.setLayout(new java.awt.BorderLayout());

        sppPagRea.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppPagRea.setResizeWeight(0.5);
        sppPagRea.setOneTouchExpandable(true);

        panPagReaReg.setLayout(new java.awt.BorderLayout());

        spnDat.setPreferredSize(new java.awt.Dimension(353, 403));

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
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnDat.setViewportView(tblDat);

        panPagReaReg.add(spnDat, java.awt.BorderLayout.CENTER);

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

        panPagReaReg.add(spnTot, java.awt.BorderLayout.SOUTH);

        sppPagRea.setTopComponent(panPagReaReg);

        panPagReaMovReg.setMinimumSize(new java.awt.Dimension(22, 22));
        panPagReaMovReg.setPreferredSize(new java.awt.Dimension(453, 403));
        panPagReaMovReg.setLayout(new java.awt.BorderLayout());

        chkDatFacMosMovReg.setText("Mostrar el movimiento del documento seleccionado"); // NOI18N
        chkDatFacMosMovReg.setPreferredSize(new java.awt.Dimension(269, 20));
        chkDatFacMosMovReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDatFacMosMovRegActionPerformed(evt);
            }
        });
        panPagReaMovReg.add(chkDatFacMosMovReg, java.awt.BorderLayout.NORTH);

        tblDatFacMovReg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnDatMov.setViewportView(tblDatFacMovReg);

        panPagReaMovReg.add(spnDatMov, java.awt.BorderLayout.CENTER);

        spnTotMovDat.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTotMovDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTotMovDat.setViewportView(tblTotMovDat);

        panPagReaMovReg.add(spnTotMovDat, java.awt.BorderLayout.SOUTH);

        sppPagRea.setBottomComponent(panPagReaMovReg);

        panPagRea.add(sppPagRea, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panPagRea);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar"); // NOI18N
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado."); // NOI18N
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butCer.setText("Cerrar"); // NOI18N
        butCer.setToolTipText("Cierra la ventana."); // NOI18N
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo"); // NOI18N
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-703)/2, (screenSize.height-450)/2, 703, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomCliHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusLost
        // TODO add your handling code here:
        if (txtNomCliHas.getText().length()>0)
            optFil.setSelected(true);
        
        if(txtCodGrpCli.getText().length()>0 && txtNomGrpCli.getText().length()>0)
        {
            txtCodGrpCli.setText("");
            txtNomGrpCli.setText("");
        }
        
        if(txtCodCli.getText().length()>0 && txtRucCli.getText().length()>0 && txtDesLarCli.getText().length()>0)
        {
            txtCodCli.setText("");
            txtRucCli.setText("");
            txtDesLarCli.setText("");
        }
    }//GEN-LAST:event_txtNomCliHasFocusLost

    private void txtNomCliHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusGained
        // TODO add your handling code here:
        txtNomCliHas.selectAll();
    }//GEN-LAST:event_txtNomCliHasFocusGained

    private void txtNomCliDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusLost
        // TODO add your handling code here:
        if (txtNomCliDes.getText().length()>0)
        {
            optFil.setSelected(true);
            if (txtNomCliHas.getText().length()==0)
                txtNomCliHas.setText(txtNomCliDes.getText());
        }        
        
        if(txtCodGrpCli.getText().length()>0 && txtNomGrpCli.getText().length()>0)
        {
            txtCodGrpCli.setText("");
            txtNomGrpCli.setText("");
        }
        
        if(txtCodCli.getText().length()>0 && txtRucCli.getText().length()>0 && txtDesLarCli.getText().length()>0)
        {
            txtCodCli.setText("");
            txtRucCli.setText("");
            txtDesLarCli.setText("");
        }
    }//GEN-LAST:event_txtNomCliDesFocusLost

    private void txtNomCliDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusGained
        // TODO add your handling code here:
        txtNomCliDes.selectAll();
    }//GEN-LAST:event_txtNomCliDesFocusGained

    private void chkDatFacMosMovRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDatFacMosMovRegActionPerformed
        // TODO add your handling code here:
        if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
        {
            if (chkDatFacMosMovReg.isSelected())
            {
                System.out.println("---Esta activo el chk de movreg--- POR GRUPO---");
                cargarDetRegGrp();
            }
            else
                objTblMod.removeAllRows();
        }
        else
        {
            if (chkDatFacMosMovReg.isSelected())
            {
                System.out.println("---Esta activo el chk de movreg--- POR EMPRESA---");
                cargarDetReg();
            }
            else
                objTblMod.removeAllRows();
        }
        
    }//GEN-LAST:event_chkDatFacMosMovRegActionPerformed

    private void chkMosCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosCreActionPerformed
        // TODO add your handling code here:
        if (!chkMosDeb.isSelected())
        {
            chkMosCre.setSelected(true);
        }
    }//GEN-LAST:event_chkMosCreActionPerformed

    private void chkMosDebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDebActionPerformed
        // TODO add your handling code here:
        if (!chkMosCre.isSelected())
        {
            chkMosDeb.setSelected(true);
        }
    }//GEN-LAST:event_chkMosDebActionPerformed

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        // TODO add your handling code here:
        mostrarVenConLoc(0);
    }//GEN-LAST:event_butLocActionPerformed

    private void txtDesLarLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarLocFocusLost
        // TODO add your handling code here:
        if (txtDesLarLoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesLarLoc.getText().equalsIgnoreCase(strNomLoc))
            {
                if (txtDesLarLoc.getText().equals(""))
                {
                    txtCodLoc.setText("");
                    txtDesLarLoc.setText("");
                }
                else
                {
                    mostrarVenConLoc(2);
                }
            }
            else
                txtDesLarLoc.setText(strNomLoc);
        }
    }//GEN-LAST:event_txtDesLarLocFocusLost

    private void txtDesLarLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarLocFocusGained
        // TODO add your handling code here:
        strNomLoc=txtDesLarLoc.getText();
        txtDesLarLoc.selectAll();
    }//GEN-LAST:event_txtDesLarLocFocusGained

    private void txtDesLarLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarLocActionPerformed
        // TODO add your handling code here:
        txtDesLarLoc.transferFocus();
    }//GEN-LAST:event_txtDesLarLocActionPerformed

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        // TODO add your handling code here:
        if (txtCodLoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc))
            {
                if (txtCodLoc.getText().equals(""))
                {
                    txtCodLoc.setText("");
                    txtDesLarLoc.setText("");
                }
                else
                {
                    mostrarVenConLoc(1);
                }
            }
            else
                txtCodLoc.setText(strCodLoc);
        }
    }//GEN-LAST:event_txtCodLocFocusLost

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        // TODO add your handling code here:
        strCodLoc=txtCodLoc.getText();
        txtCodLoc.selectAll();
    }//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        // TODO add your handling code here:
        txtCodLoc.transferFocus();
    }//GEN-LAST:event_txtCodLocActionPerformed

    private void txtCodGrpCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpCliFocusLost
        // TODO add your handling code here:
        if (!txtCodGrpCli.getText().equalsIgnoreCase(strCodGrpCli))
        {
            if (txtCodGrpCli.getText().equals(""))
            {
//                txtCodTipDoc.setText("");
                txtNomGrpCli.setText("");
            }
            else
            {
                ///mostrarVenConTipDoc(1);
                mostrarVenConGrpCli(1);
            }
        }
        else
        {
            txtCodGrpCli.setText(strCodGrpCli);
        }
        
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodGrpCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
        
        if(txtCodCli.getText().length()>0 && txtRucCli.getText().length()>0 && txtDesLarCli.getText().length()>0)
        {
            txtCodCli.setText("");
            txtRucCli.setText("");
            txtDesLarCli.setText("");
        }
}//GEN-LAST:event_txtCodGrpCliFocusLost

    private void txtCodGrpCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpCliFocusGained
        // TODO add your handling code here:
        strCodGrpCli=txtCodGrpCli.getText();
        txtCodGrpCli.selectAll();
}//GEN-LAST:event_txtCodGrpCliFocusGained

    private void txtCodGrpCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodGrpCliActionPerformed
        // TODO add your handling code here:
        txtCodGrpCli.transferFocus();
        
        if(txtCodCli.getText().length()>0 && txtRucCli.getText().length()>0 && txtDesLarCli.getText().length()>0)
        {
            txtCodCli.setText("");
            txtRucCli.setText("");
            txtDesLarCli.setText("");
        }
}//GEN-LAST:event_txtCodGrpCliActionPerformed

    private void butGrpCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGrpCliActionPerformed
        // TODO add your handling code here://
            ///mostrarVenConTipDoc(0);
        mostrarVenConGrpCli(0);
}//GEN-LAST:event_butGrpCliActionPerformed

    private void txtNomGrpCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomGrpCliFocusLost
        // TODO add your handling code here:
         //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomGrpCli.getText().equalsIgnoreCase(strNomGrpCli))
        {
            if (txtNomGrpCli.getText().equals(""))
            {
//                txtCodTipDoc.setText("");
                txtNomGrpCli.setText("");
            }
            else
            {
                ///mostrarVenConTipDoc(2);
                mostrarVenConGrpCli(2);
            }
        }
        else
        {
            txtNomGrpCli.setText(strNomGrpCli);
        }
        
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtNomGrpCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtNomGrpCliFocusLost

    private void txtNomGrpCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomGrpCliFocusGained
        // TODO add your handling code here:
        strNomGrpCli=txtNomGrpCli.getText();
        txtNomGrpCli.selectAll();
}//GEN-LAST:event_txtNomGrpCliFocusGained

    private void txtNomGrpCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomGrpCliActionPerformed
        // TODO add your handling code here:
        txtNomGrpCli.transferFocus();
}//GEN-LAST:event_txtNomGrpCliActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
//        if (optFil.isSelected())
//        {
//            txtCodEmp.setText("");
//            txtDesLarEmp.setText("");
//        }
    }//GEN-LAST:event_optFilItemStateChanged

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtDesLarEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarEmpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarEmp.getText().equalsIgnoreCase(strDesLarEmp))
        {
            if (txtDesLarEmp.getText().equals(""))
            {
                txtCodEmp.setText("");
                txtDesLarEmp.setText("");
            }
            else
            {
                mostrarVenConEmp(2);
            }
        }
        else
        {
            txtDesLarEmp.setText(strDesLarEmp);
            optTod.setSelected(true);
        }
    }//GEN-LAST:event_txtDesLarEmpFocusLost

    private void txtDesLarEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarEmpFocusGained
        strDesLarEmp=txtDesLarEmp.getText();
        txtDesLarEmp.selectAll();
    }//GEN-LAST:event_txtDesLarEmpFocusGained

    private void txtDesLarEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarEmpActionPerformed
        txtDesLarEmp.transferFocus();
    }//GEN-LAST:event_txtDesLarEmpActionPerformed

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp))
        {
            if (txtCodEmp.getText().equals(""))
            {
                txtCodEmp.setText("");
                txtDesLarEmp.setText("");
            }
            else
            {
                mostrarVenConEmp(1);
            }
        }
        else
        {
            txtCodEmp.setText(strCodEmp);
        }
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        strCodEmp=txtCodEmp.getText();
        txtCodEmp.selectAll();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:
        if(optTod.isSelected())
        {
            txtCodGrpCli.setText("");
            txtNomGrpCli.setText("");
            txtCodCli.setText("");
            txtRucCli.setText("");
            txtDesLarCli.setText("");
            txtNomCliDes.setText("");
            txtNomCliHas.setText("");
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        configurarVenConCli();
        mostrarVenConCli(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butCliActionPerformed

    private void txtDesLarCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarCli.getText().equalsIgnoreCase(strDesLarCli))
        {
            if (txtDesLarCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtRucCli.setText("");
                txtDesLarCli.setText("");
                optTod.setSelected(true);
            }
            else
            {
                mostrarVenConCli(2);
            }
        }
        else
        {
            txtDesLarCli.setText(strDesLarCli);
        }
        
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
        else
        {
            optTod.setSelected(true);
        }
    }//GEN-LAST:event_txtDesLarCliFocusLost

    private void txtDesLarCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusGained
        strDesLarCli=txtDesLarCli.getText();
        txtDesLarCli.selectAll();
    }//GEN-LAST:event_txtDesLarCliFocusGained

    private void txtDesLarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCliActionPerformed
        txtDesLarCli.transferFocus();
    }//GEN-LAST:event_txtDesLarCliActionPerformed

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCodCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtRucCli.setText("");
                txtDesLarCli.setText("");
                optTod.setSelected(true);
            }
            else
            {
                mostrarVenConCli(1);
            }
        }
        else
        {
            txtCodCli.setText(strCodCli);
        }
        
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
        else
        {
            optTod.setSelected(true);
        }
        
        if(txtCodGrpCli.getText().length()>0 && txtNomGrpCli.getText().length()>0)
        {
            txtCodGrpCli.setText("");
            txtNomGrpCli.setText("");
        }
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
        
        if(txtCodGrpCli.getText().length()>0 && txtNomGrpCli.getText().length()>0)
        {
            txtCodGrpCli.setText("");
            txtNomGrpCli.setText("");
        }
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
//            txtCodCli.setText("");
//            txtDesLarCli.setText("");
//            txtCodEmp.setText("");
//            txtDesLarEmp.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
       if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }            
        }
        else
        {
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtRucCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRucCliActionPerformed
        // TODO add your handling code here:
        txtRucCli.transferFocus();
}//GEN-LAST:event_txtRucCliActionPerformed

    private void txtRucCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRucCliFocusGained
        // TODO add your handling code here:
        strRucCli=txtCodCli.getText();
        txtRucCli.selectAll();
}//GEN-LAST:event_txtRucCliFocusGained

    private void txtRucCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRucCliFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtRucCli.getText().equalsIgnoreCase(strRucCli))
        {
            if (txtRucCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtRucCli.setText("");
                txtDesLarCli.setText("");
                optTod.setSelected(true);
            }
            else
            {
                mostrarVenConCli(2);
            }
        }
        else
        {
            txtRucCli.setText(strRucCli);
        }
        
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtRucCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
        else
        {
            optTod.setSelected(true);
        }
}//GEN-LAST:event_txtRucCliFocusLost

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butGrpCli;
    private javax.swing.JButton butLoc;
    private javax.swing.JCheckBox chkDatFacMosMovReg;
    private javax.swing.JCheckBox chkMosCre;
    private javax.swing.JCheckBox chkMosDeb;
    private javax.swing.JCheckBox chkMosDocVen;
    private javax.swing.JCheckBox chkMosRet;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblFecCor;
    private javax.swing.JLabel lblGrpCli;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNomCliDes;
    private javax.swing.JLabel lblNomCliHas;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JPanel panPagRea;
    private javax.swing.JPanel panPagReaMovReg;
    private javax.swing.JPanel panPagReaReg;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnDatMov;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JScrollPane spnTotMovDat;
    private javax.swing.JSplitPane sppPagRea;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDatFacMovReg;
    private javax.swing.JTable tblTot;
    private javax.swing.JTable tblTotMovDat;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodGrpCli;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtDesLarEmp;
    private javax.swing.JTextField txtDesLarLoc;
    private javax.swing.JTextField txtNomCliDes;
    private javax.swing.JTextField txtNomCliHas;
    private javax.swing.JTextField txtNomGrpCli;
    private javax.swing.JTextField txtRucCli;
    // End of variables declaration//GEN-END:variables
   
    //estos tres metodos me sirven para calcular los dias  que han transcurrido de una fecha a la actual
    /**
     * Esta función permite calcular el numero de dias transcurridos entre 2 fechas.
     *@param intAniIni,intMesIni,intDiaIni,intAniFin,intMesFin,intDiaFin recibe 6 enteros que representan el anio inicial y final, mes inicial y final, dia inicial y final
     *@return intNumDia: Retorna el numero de dias.
     * 
     */
   /*la funcion intCalDiaTra( año inicialFecVenc, mes inicialFecVenc, dia inicialFecVenc, año final FecActualSis, mes final FecActualSis, dia final FecActualSis)*/
   private int intCalDiaTra (int intAniIni, int intMesIni,int intDiaIni,int intAniFin, int intMesFin,int intDiaFin)
   {
        int intNumDia=-1,i;
        int intNumAux=0;
        //CALCULA LOS DIAS DE LOS AÑOS QUE ESTAN ENTRE LAS FECHAS INICIAL Y FINAL
        for(int j=intAniIni+1;j<intAniFin;j++)
        {
            if(blnSiBis(j)){
                intNumAux+=366;//SI ES BISIESTO EL AÑO EN CUESTION
            }else{
                intNumAux+=365;
            }
        }



        //1. Cuando el año ini y fin son igules
        if(intAniIni==intAniFin)
        {
            intNumDia=intDiaTran(intAniFin,intMesFin,intDiaFin) - intDiaTran(intAniIni,intMesIni,intDiaIni);
        }
        else
        {//2.1 si no es bisiesto calculo el numero de dias transcurridos 
              //en el año inicial hasta la fecha ini y eso le resto de los dias de ese año 
              //para calcular los dias que faltan de ese año
              //2.2 sumo el numero de dias de los años entre las fechas ini y fin
              //2.3 finalmente sumo los dias que han transcurrido en el año de la fecha fin hasta esa fecha
            if(intAniIni>intAniFin)
                intNumDia= intDiaTran(intAniIni,intMesIni,intDiaIni) - intDiaTran(intAniFin,intMesFin,intDiaFin); ///funcion provisional///
            else{
                if (blnSiBis(intAniIni))
                    ///System.out.println("---AÑO BISIESTO--- ");
                    intNumDia=(366-intDiaTran(intAniIni,intMesIni,intDiaIni)) + intNumAux + intDiaTran(intAniFin,intMesFin,intDiaFin); ///funcion original///
                else
                    intNumDia=(365-intDiaTran(intAniIni,intMesIni,intDiaIni)) + intNumAux + intDiaTran(intAniFin,intMesFin,intDiaFin);
            }
        }
        return intNumDia;
        
    }
   /**
     * Esta función permite verificar si un anio es bisiesto o no
     *@param intAni recibe 1 entero que representan el anio que queremos verificar 
     *@return true: Booleano que indica si es bisiesto
     * <Br> false: Si no es bisiesto
     */
    private boolean blnSiBis(int intAni){
    int intMod1,intMod2,intMod3;
    intMod1=intAni%4;
    intMod2=intAni%100;
    intMod3=intAni%400;
    if(intMod1==0){
        if(intMod2==0){
            if(intMod3==0){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }else{
        return false;
    }
}
    /**
     * Esta función calcula el numero de dias que han transcurrido en un año hasta la fecha dada
     *@param anio,mes,dia recibe 3 enteros que representan el anio, el mes y el dia  respectivamente 
     *@return numDia: entero con el numero de dias que han transcurrido en ese año hasta ese dia de ese mes
     */
    private int intDiaTran(int intAni,int intMes, int intDia)
    {
        int intNumDia=0,i;
        for(i=1;i<intMes;i++)
        {
            switch (i)
            {
                case 1:
                    intNumDia+=31;
                    break;
                case 2:
                    if(blnSiBis(intAni)){
                        intNumDia+=29;
                    }else{
                        intNumDia+=28;
                    }
                    break;
                case 3:
                    intNumDia+=31;
                    break;
                case 4:
                    intNumDia+=30;
                    break;
                case 5:
                    intNumDia+=31;
                    break;
                case 6:
                    intNumDia+=30;
                    break;
                case 7:
                    intNumDia+=31;
                    break;
                case 8:
                    intNumDia+=31;
                    break;
                case 9:
                    intNumDia+=30;
                    break;
                case 10:
                    intNumDia+=31;
                    break;
                case 11:
                    intNumDia+=30;
                    break;
                case 12:
                    intNumDia+=31;
                    break;
            }
        }
        ///System.out.println("Los dia calculados en el mes " + intMes + " es: " + intNumDia);
        return (intNumDia+=intDia);            
    }
    
    private boolean isCamVal()
    {
        if(dtpFecDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de Corte </FONT> es Obligatorio.<BR>Escriba la fecha de corte y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }                
        return true;
    } 
    
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            lblEmp.setVisible(false);
            txtCodEmp.setVisible(false);
            txtDesLarEmp.setVisible(false);
            butEmp.setVisible(false);
            lblFecCor.setVisible(false);
            
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                lblEmp.setVisible(true);
                txtCodEmp.setVisible(true);
                txtDesLarEmp.setVisible(true);
                butEmp.setVisible(true);
            }
            
            if(objParSis.getCodigoMenu()==645)
            {
                lblFecCor.setVisible(true);
                dtpFecDoc = new Librerias.ZafDate.ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y"); 
                dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
                dtpFecDoc.setText("");
                panFil.add(dtpFecDoc);
                dtpFecDoc.setBounds(530, 35, 120, 20);
            }
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " V0.1");
            lblTit.setText(strAux);
            dtpDat=new ZafDatePicker(((javax.swing.JFrame)this.getParent()), "d/m/y");//inicializa el objeto DatePicker
            dtpDatFacCor=new ZafDatePicker(((javax.swing.JFrame)this.getParent()), "d/m/y");//inicializa el objeto DatePicker
            //Configurar objetos.
//            txtCodTipDoc.setVisible(false);
           
            ///Configurar Ventana de Cliente///
            configurarVenConCli();
            configurarVenConGrpCli();
            configurarVenConLoc();
            configurarVenConEmp();
            ////configurarVenConTipDoc();
            configurarTblDatFac();
            configurarTblMovDatFac();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /** Configurar el formulario. */
    private boolean configurarTblDatFac()
    {
        boolean blnRes=true;
        try
        {   
            //Configurar JTable: Establecer el modelo.
            vecDatFac=new Vector();    //Almacena los datos
            vecCabFac=new Vector(15);  //Almacena las cabeceras
            vecCabFac.clear();
            vecCabFac.add(INT_TBL_DAT_FAC_LIN,"");///0
            vecCabFac.add(INT_TBL_DAT_FAC_COD_EMP,"Cód.Emp.");///1
            vecCabFac.add(INT_TBL_DAT_FAC_COD_CLI,"Cli/Grp.");///2
            vecCabFac.add(INT_TBL_DAT_FAC_RUC_CED,"Ruc/Ced.");///3
            vecCabFac.add(INT_TBL_DAT_FAC_NOM_CLI,"Cliente/Grupo");///4
            vecCabFac.add(INT_TBL_DAT_FAC_VAL_PEN,"Val.Pen.");///5
            vecCabFac.add(INT_TBL_DAT_FAC_VAL_VEN,"Val.Por.Ven.");///6
            vecCabFac.add(INT_TBL_DAT_FAC_VAL_30D,"0-30");///7
            vecCabFac.add(INT_TBL_DAT_FAC_VAL_60D,"31-60");///8
            vecCabFac.add(INT_TBL_DAT_FAC_VAL_90D,"61-90");///9
            vecCabFac.add(INT_TBL_DAT_FAC_VAL_MAS,"+90");///10

            objTblModFac=new ZafTblMod();
            objTblModFac.setHeader(vecCabFac);
            tblDat.setModel(objTblModFac);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_FAC_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_FAC_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_FAC_COD_EMP).setPreferredWidth(10);//antes estaba 30
            tcmAux.getColumn(INT_TBL_DAT_FAC_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FAC_RUC_CED).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_DAT_FAC_NOM_CLI).setPreferredWidth(200);//antes estaba 70
            tcmAux.getColumn(INT_TBL_DAT_FAC_VAL_PEN).setPreferredWidth(95);
            tcmAux.getColumn(INT_TBL_DAT_FAC_VAL_VEN).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_FAC_VAL_30D).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_FAC_VAL_60D).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_FAC_VAL_90D).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_FAC_VAL_MAS).setPreferredWidth(95);//antes estaba 60
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.//
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);///
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_FAC_COD_CLI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            objTblCelRenLbl=new ZafTblCelRenLbl();//inincializo el renderizador
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);//alineacion del contenido de la celda
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);//formato de la celda, este es numero
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_FAC_VAL_PEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FAC_VAL_VEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FAC_VAL_30D).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FAC_VAL_60D).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FAC_VAL_90D).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FAC_VAL_MAS).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_VAL_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_FAC_VAL_PEN, INT_TBL_DAT_FAC_VAL_VEN, INT_TBL_DAT_FAC_VAL_30D, INT_TBL_DAT_FAC_VAL_60D, INT_TBL_DAT_FAC_VAL_90D, INT_TBL_DAT_FAC_VAL_MAS};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
                      
            //Configurar JTable: Establecer el ListSelectionListener.
            javax.swing.ListSelectionModel lsm=tblDat.getSelectionModel();
            lsm.addListSelectionListener(new ZafLisSelLisDatFac());
            
            
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /** Configurar el formulario. */
    private boolean configurarTblMovDatFac()
    {
        boolean blnRes=true;
        try
        {
            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(30);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_RUC_CED,"Ruc/Ced.");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_DIA_CRE,"Día.Cre.");
            vecCab.add(INT_TBL_DAT_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_DAT_POR_RET,"Por.Ret.");
            vecCab.add(INT_TBL_DAT_VAL_DOC,"Val.Doc.");            
            vecCab.add(INT_TBL_DAT_VAL_ABO,"Val.Abo.");
            vecCab.add(INT_TBL_DAT_VAL_PEN,"Val.Pen.");
            vecCab.add(INT_TBL_DAT_VAL_VEN,"Val.Por.Ven.");
            vecCab.add(INT_TBL_DAT_VAL_30D,"0-30");
            vecCab.add(INT_TBL_DAT_VAL_60D,"31-60");
            vecCab.add(INT_TBL_DAT_VAL_90D,"61-90");
            vecCab.add(INT_TBL_DAT_VAL_MAS,"+90");
            vecCab.add(INT_TBL_DAT_COD_BAN,"Cód.Ban.");
            vecCab.add(INT_TBL_DAT_NOM_BAN,"Banco");
            vecCab.add(INT_TBL_DAT_NUM_CTA,"Núm.Cta.");
            vecCab.add(INT_TBL_DAT_NUM_CHQ,"Núm.Chq.");
            vecCab.add(INT_TBL_DAT_FEC_REC_CHQ,"Fec.Rec.Chq.");
            vecCab.add(INT_TBL_DAT_FEC_VEN_CHQ,"Fec.Ven.Chq.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_VAL_CHQ,"Val.Chq.");

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDatFacMovReg.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDatFacMovReg.setRowSelectionAllowed(true);
            tblDatFacMovReg.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDatFacMovReg,INT_TBL_DAT_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatFacMovReg);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatFacMovReg.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatFacMovReg.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(10);//antes estaba 30
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(10);//antes estaba 30
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_RUC_CED).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(200);//antes estaba 70
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DEC_TIP_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);//antes 80            
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FEC_VEN).setPreferredWidth(95);
            tcmAux.getColumn(INT_TBL_DAT_POR_RET).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_VAL_ABO).setPreferredWidth(95);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setPreferredWidth(95);
            tcmAux.getColumn(INT_TBL_DAT_VAL_VEN).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_VAL_30D).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_VAL_60D).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_VAL_90D).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_VAL_MAS).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_COD_BAN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_BAN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CHQ).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_REC_CHQ).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_VEN_CHQ).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(95);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CHQ).setPreferredWidth(95);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatFacMovReg.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_BAN).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_BAN).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_BAN).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_BAN).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_BAN).setResizable(false);

            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC).setResizable(false);

            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setResizable(false);
                        
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblDatFacMovReg.getTableHeader().addMouseMotionListener(new ZafMouMotAdaAux());
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatFacMovReg);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_RUC_CED).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();//inincializo el renderizador
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);//alineacion del contenido de la celda
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);//formato de la celda, este es numero
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_POR_RET).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_ABO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_VEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_30D).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_60D).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_90D).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_MAS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intColTblDatFacMovReg[]={INT_TBL_DAT_VAL_DOC, INT_TBL_DAT_VAL_ABO, INT_TBL_DAT_VAL_PEN, INT_TBL_DAT_VAL_VEN, INT_TBL_DAT_VAL_30D, INT_TBL_DAT_VAL_60D, INT_TBL_DAT_VAL_90D, INT_TBL_DAT_VAL_MAS, INT_TBL_DAT_VAL_CHQ};
            objTotDatMovFac=new ZafTblTot(spnDatMov, spnTotMovDat, tblDatFacMovReg, tblTotMovDat, intColTblDatFacMovReg);
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Clientes/Proveedores".
     */
    private boolean configurarVenConCli()
    {
        boolean blnRes=true;
        int intCodEmp, intCodEmpGrp, intCodMnu;
        
        try
        {
            
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodEmpGrp = objParSis.getCodigoEmpresaGrupo();
            intCodMnu= objParSis.getCodigoMenu();            
            System.out.println("---intCodMnu: " + intCodMnu);
            INTCODEMP = intCodEmp;
            INTCODEMPGRP = intCodEmpGrp;
            System.out.println("---INTCODEMP: " + INTCODEMP);
            System.out.println("---INTCODEMPGRP: " + INTCODEMPGRP);
            
            
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");
            
//            //Armar la sentencia SQL.
//            strSQL="";
//            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
//            strSQL+=" FROM tbm_cli AS a1";
//            //strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
//            {
//                if (txtCodEmp.getText().length()>0)
//                    strSQL+=" WHERE a1.co_emp = " + txtCodEmp.getText();
//                else
//                    strSQL+=" WHERE a1.co_emp IN (1,2,3,4)";
//            }
//            else
//                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//            
//            strSQL+=" AND a1.st_reg='A'";
//            strSQL+=" ORDER BY a1.tx_nom";
//            System.out.println("---QUERY PARA CONCLI: " + strSQL);
            
            /*Query mejorado para consultar Clientes filtrado por local y empresa*/            
            //Armar la sentencia SQL.            
            if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
            {
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                ///strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    if (txtCodEmp.getText().length()>0)
                        strSQL+=" WHERE a1.co_emp = " + txtCodEmp.getText();
                    else
                        strSQL+=" WHERE a1.co_emp IN (1,2,3,4)";
                }
                else
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_nom";
                System.out.println("---QUERY PARA CONCLI---ADMIN--: " + strSQL);
            }
            else
            {
                strSQL="";
                strSQL+="SELECT a1.co_cli, a2.tx_ide, a2.tx_nom, a2.tx_dir";
                strSQL+=" FROM  tbr_cliloc AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.st_reg='A'";
                strSQL+=" ORDER BY a2.tx_nom";
                System.out.println("---QUERY PARA CONCLI POR LOCAL---: " + strSQL);
            }
            
            
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCli.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);            
            vcoCli.setConfiguracionColumna(3, javax.swing.JLabel.LEFT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Grupos de Clientes".
     */
    private boolean configurarVenConGrpCli()
    {
        boolean blnRes=true;
        int intCodEmp, intCodEmpGrp, intCodMnu;
        
        try
        {
            
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodEmpGrp = objParSis.getCodigoEmpresaGrupo();
            intCodMnu= objParSis.getCodigoMenu();
//            System.out.println("---intCodEmp: " + intCodEmp);
//            System.out.println("---intCodEmpGrp: " + intCodEmpGrp);
//            System.out.println("---intCodMnu: " + intCodMnu);
            
            
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_grp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre de Grupo");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("284");
            
            
            /*Query mejorado para consultar Clientes filtrado por local y empresa*/            
            //Armar la sentencia SQL.            
            if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
            {
                strSQL="";
                strSQL+="SELECT a1.co_grp, a1.tx_nom";
                strSQL+=" FROM tbm_grpcli AS a1";
                ///strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    if (txtCodEmp.getText().length()>0)
                        strSQL+=" WHERE a1.co_emp = " + txtCodEmp.getText();
                    else
                        strSQL+=" WHERE a1.co_emp IN (1,2,3,4)";
                }
                else
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_nom";
                System.out.println("---QUERY PARA CONGRP_CLI---ADMIN--: " + strSQL);
            }
            else
            {
                strSQL="";
                strSQL+=" SELECT a1.co_grp, a1.tx_nom";
                strSQL+=" FROM tbm_grpcli AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_grp=a2.co_grp) ";
                strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli) ";
                strSQL+=" WHERE a3.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_nom";
                System.out.println("---QUERY PARA CONGRP_CLI POR LOCAL---: " + strSQL);
            }
            
            
            vcoGrpCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Grupo de clientes", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoGrpCli.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoGrpCli.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        int intCodUsr = objParSis.getCodigoUsuario();
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc ";
            strSQL+=" FROM tbm_cabTipDoc AS a1 ";
            strSQL+=" WHERE ";
            strSQL+=" a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
            strSQL+=" AND a1.ne_mod IN (1,3)" ;
            System.out.println("---Query VenConTipDoc()-- "+strSQL);
                
                
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=5;
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Locales".
     */
    private boolean configurarVenConLoc()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_loc");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("492");
            
//            //Armar la sentencia SQL.
//            strSQL="";
//            strSQL+="SELECT a1.co_loc, a1.tx_nom";
//            strSQL+=" FROM tbm_loc AS a1";
//            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
//                strSQL+=" WHERE a1.co_emp IN (1,2,3,4)";
//            else
//                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//            strSQL+=" AND a1.st_reg IN ('A', 'P')";
//            strSQL+=" ORDER BY a1.co_emp, a1.co_loc";
//            System.out.println("---QUERY PARA CONLOC: " + strSQL);
            
            /*Query mejorado para consultar LOCALES filtrado por local y empresa*/            
            //Armar la sentencia SQL.            
            if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_loc, a1.tx_nom";
                strSQL+=" FROM tbm_loc AS a1";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE a1.co_emp IN (1,2,3,4)";
                else
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg IN ('A', 'P')";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc";
                System.out.println("---QUERY PARA CONLOC--admin--: " + strSQL);
            }
            else
            {
                strSQL="";
                strSQL="";
                strSQL+="SELECT a1.co_loc, a1.tx_nom";
                strSQL+=" FROM tbm_loc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.st_reg IN ('A', 'P')";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc";
                System.out.println("---QUERY PARA CONLOC POR LOCAL---: " + strSQL);
            }
            
            
            vcoLoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de locales", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoLoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Empresas".
     */
    private boolean configurarVenConEmp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("492");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_emp, a1.tx_nom";
            strSQL+=" FROM tbm_emp AS a1";
            strSQL+=" ORDER BY a1.co_emp ";
            System.out.println("---QUERY PARA CONEMP: " + strSQL);
            
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de locales", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDatFac_ORI()
    {
        int intCodEmp, intCodLoc, intNumTotReg, i;
        
        int intNumDia; 
        String strFecSis, strFecIni, strFecSel, strFecVen;
        int intFecIni[];
        int intFecFin[];//para calcular los dias entre fechas
        int intFecSel[];//fecha seleccionada por el usuario//

        
        double dblSub, dblIva;
        java.util.Date datFecSer, datFecVen, datFecAux;
        
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            conFac=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conFac!=null)
            {
                stmFac=conFac.createStatement();
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                datFecAux=datFecSer;
                
                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");

                //Obtener la condición.
                strAux="";
                
                //Condicion para filtro por Codigo de Grupo del cliente
                if (!(txtCodGrpCli.getText().equals("")))
                {
                    strAux+=" AND a4.co_grp= " + txtCodGrpCli.getText();
                }
                
                //Condicion para filtro por cliente//
                if (txtCodCli.getText().length()>0)
                {
                    strAux+=" AND a4.co_cli= " + txtCodCli.getText();
                }
                
                //Condicion para filtro por Ruc del cliente//
                if (txtRucCli.getText().length()>0)
                {
                    strAux+=" AND a4.tx_ide= " + txtRucCli.getText();
                }
                
                ////////Condicion para filtro por cliente en un rango especifico//
                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a4.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a4.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Condicion para filtro por empresa
                    if (txtCodEmp.getText().length()>0)
                        strAux+=" AND a1.co_emp=" + txtCodEmp.getText();
                }
                else
                {
                    strAux+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                    
                    if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                    {
                        //Condicion para filtro por cLocal
                        if (txtCodLoc.getText().length()>0)
                            strAux+=" AND a1.co_loc= " + txtCodLoc.getText();
                        else
                            strAux+=" AND a1.co_loc= " + objParSis.getCodigoLocal();
                    }
                }
                
                ///FILTRO PARA MOSTRAR CREDITOS Y DEBITOS///
                if ( !(chkMosCre.isSelected() && chkMosDeb.isSelected()) )
                {
                    System.out.println("ENTRO POR FILTRO DE CREDITOS Y DEBITOS ");
                    if (chkMosCre.isSelected())
                        strAux+=" AND a3.tx_natDoc='I'";
                    else
                        strAux+=" AND a3.tx_natDoc='E'";
                }
                ///FILTRO PARA DOCUMENTOS VENCIDOS///
                if (chkMosDocVen.isSelected())
                {
                    System.out.println("ENTRO POR FILTRO DE DOCUMENTOS VENCIDOS ");
                    strFecVen=objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
                    strAux+=" AND a2.fe_ven<='" + strFecVen + "'";
                }
                ///FILTRO PARA MOSTRAR RETENCIONES///
                if (!chkMosRet.isSelected())
                {
                    System.out.println("ENTRO POR FILTRO DE MOSTRAR RETENCIONES");
                    strAux+=" AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
                }
                
                //Obtener el número total de registros.
                strSQL="";
                strSQL+="SELECT COUNT (*)";
                strSQL+=" FROM (";
                        strSQL+=" SELECT Z1.CODEMP, Z1.CODCLI, Z1.NUMCED, Z1.NOMCLI, Z1.VALDOC, Z1.VALABO, Z1.VALPEN, Z2.VALXVEN, Z3.VAL30D, Z4.VAL60D,Z5.VAL90D, Z6.VALMAS90 ";
                        strSQL+=" FROM (";
                        
                                strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, '' AS NUMCED, a5.tx_nom AS NOMCLI, ";
                                strSQL+=" SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO, SUM(a2.mo_pag+a2.nd_abo) AS VALPEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom ";   ///, a2.fe_ven
                                strSQL+=" ORDER BY a5.tx_nom )";
                                strSQL+=" UNION ALL ";                                
                                strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, ";
                                strSQL+=" SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO, SUM(a2.mo_pag+a2.nd_abo) AS VALPEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
//                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";   ///, a2.fe_ven   ////AND a4.co_grp is null
                                strSQL+=" ORDER BY a4.tx_nom )";
                        strSQL+=" ) AS Z1";
                        strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, '' AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND (current_date - a2.fe_ven) <= 0";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom )";
                                strSQL+=" UNION ALL ";
                                strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
//                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND (current_date - a2.fe_ven) <= 0";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom )";
                        strSQL+=" ) AS Z2 ON (Z1.CODEMP=Z2.CODEMP AND Z1.NUMCED = Z2.NUMCED)";
                        strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, '' AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL30D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom )";
                                strSQL+=" UNION ALL ";
                                strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL30D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
//                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom )";                                
                       strSQL+=" ) AS Z3 ON (Z1.CODEMP=Z3.CODEMP AND Z1.NUMCED = Z3.NUMCED)";
                       strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, '' AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL60D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom )";
                                strSQL+=" UNION ALL ";
                                strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL60D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
//                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom )";
                       strSQL+=" ) AS Z4 ON (Z1.CODEMP=Z4.CODEMP AND Z1.NUMCED = Z4.NUMCED)";
                       strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, '' AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL90D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom )";
                                strSQL+=" UNION ALL ";
                                strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL90D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
//                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom )";
                       strSQL+=" ) AS Z5 ON (Z1.CODEMP=Z5.CODEMP AND Z1.NUMCED = Z5.NUMCED)";
                       strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, '' AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND (current_date - a2.fe_ven) >=91";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom )";
                                strSQL+=" UNION ALL ";
                                strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
//                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND (current_date - a2.fe_ven) >=91";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom )";
                       strSQL+=" ) AS Z6 ON (Z1.CODEMP=Z5.CODEMP AND Z1.NUMCED = Z6.NUMCED)";
                strSQL+=" ) AS B1";
//                System.out.println("---Query COUNT --cargarDatFac()--: "+ strSQL);
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                
/*
-----super query-----
SELECT Z1.co_emp, Z1.co_cli, Z1.tx_nom, Z1.VALDOC, Z1.VALABO, Z1.VALPEN, Z2.VALXVEN, Z3.VAL30D, Z4.VAL60D,Z5.VAL90D, Z6.VALMAS90
FROM
(
---Query de registros Consultados --cargarDatFac()--:
SELECT a1.co_emp, a1.co_cli, a4.tx_nom, SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO,
SUM(a2.mo_pag+a2.nd_abo) AS VALPEN
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)
INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)
INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3)
---AND a1.co_cli= 3
AND a1.co_emp=1
GROUP BY a1.co_emp, a1.co_cli, a4.tx_nom
ORDER BY a4.tx_nom
) AS Z1
LEFT OUTER JOIN
(
--------para la columna de valor por vencer--------
SELECT a1.co_emp, a1.co_cli, a3.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)
INNER JOIN tbm_grpcli AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_grp=a4.co_grp)
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0
---AND a1.co_cli= 3
AND a1.co_emp=1
---AND a2.fe_ven > current_date
AND (current_date - a2.fe_ven) <= 0
GROUP BY a1.co_emp, a1.co_cli, a3.tx_nom
) AS Z2 ON (Z1.tx_ide = Z2.tx_ide)
LEFT OUTER JOIN
(
-----para columna de valor vencido entre 0 - 30 dias----------
SELECT a1.co_emp, a1.co_cli, a3.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL30D
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) 
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0
---AND a1.co_cli= 3 
AND a1.co_emp=1
AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30)
GROUP BY a1.co_emp, a1.co_cli, a3.tx_nom
) AS Z3 ON (Z1.tx_ide = Z3.tx_ide)
LEFT OUTER JOIN
(
-----para columna de valor vencido entre 31 - 60 dias----------
SELECT a1.co_emp, a1.co_cli, a3.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL60D
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) 
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0
---AND a1.co_cli= 3 
AND a1.co_emp=1
AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60)
GROUP BY a1.co_emp, a1.co_cli, a3.tx_nom
) AS Z4 ON (Z1.tx_ide = Z4.tx_ide)
LEFT OUTER JOIN
(
-----para columna de valor vencido entre 61 - 90 dias----------
SELECT a1.co_emp, a1.co_cli, a3.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL90D
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
AND a1.co_doc=a2.co_doc) 
INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 
---AND a1.co_cli= 3
AND a1.co_emp=1
AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90)
GROUP BY a1.co_emp, a1.co_cli, a3.tx_nom 
)AS Z5 ON (Z1.tx_ide = Z5.tx_ide)
LEFT OUTER JOIN
(
-----para columna de valor vencido mayor a 91 dias----------
SELECT a1.co_emp, a1.co_cli, a3.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
AND a1.co_doc=a2.co_doc) 
INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0
---AND a1.co_cli= 3 
AND a1.co_emp=1
AND (current_date - a2.fe_ven) >=91
GROUP BY a1.co_emp, a1.co_cli, a3.tx_nom
) AS Z6 ON(Z1.tx_ide = Z6.tx_ide)                 
*/                
                
                //Armar la sentencia SQL.
                strSQL="";
                ///strSQL+=" SELECT * ";
                ///strSQL+=" FROM (";
                strSQL+=" SELECT Z1.CODEMP, Z1.CODCLI, Z1.NUMCED, Z1.NOMCLI, Z1.VALDOC, Z1.VALABO, Z1.VALPEN, Z2.VALXVEN, Z3.VAL30D, Z4.VAL60D,Z5.VAL90D, Z6.VALMAS90 ";
                strSQL+=" FROM (";
                        strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, '' AS NUMCED, a5.tx_nom AS NOMCLI, ";
                        strSQL+=" SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO, SUM(a2.mo_pag+a2.nd_abo) AS VALPEN ";
                        strSQL+=" FROM tbm_cabMovInv AS a1";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                        strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                        strSQL+=" AND a2.st_reg IN ('A','C')";
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                        strSQL+=" AND a3.ne_mod in(1,3)";
                        strSQL+=strAux;
                        strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom ";   ///, a2.fe_ven
                        strSQL+=" ORDER BY a5.tx_nom )";
                        strSQL+=" UNION ALL ";
                        strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, ";
                        strSQL+=" SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO, SUM(a2.mo_pag+a2.nd_abo) AS VALPEN ";
                        strSQL+=" FROM tbm_cabMovInv AS a1";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                        strSQL+=" AND a2.st_reg IN ('A','C')";
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                        strSQL+=" AND a3.ne_mod in(1,3)";
                        strSQL+=" AND a4.co_grp IS NULL";
                        strSQL+=strAux;
                        strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";   ///, a2.fe_ven
                        strSQL+=" ORDER BY a4.tx_nom )";
                strSQL+=" ) AS Z1";
                strSQL+=" LEFT OUTER JOIN (";
                        strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, '' AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN ";
                        strSQL+=" FROM tbm_cabMovInv AS a1";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                        strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                        strSQL+=" AND a2.st_reg IN ('A','C')";
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                        strSQL+=" AND a3.ne_mod in(1,3)";
                        strSQL+=" AND (current_date - a2.fe_ven) <= 0";
                        strSQL+=strAux;
                        strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom )";
                        strSQL+=" UNION ALL ";
                        strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN ";
                        strSQL+=" FROM tbm_cabMovInv AS a1";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                        strSQL+=" AND a2.st_reg IN ('A','C')";
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                        strSQL+=" AND a3.ne_mod in(1,3)";
                        strSQL+=" AND a4.co_grp IS NULL";
                        strSQL+=" AND (current_date - a2.fe_ven) <= 0";
                        strSQL+=strAux;
                        strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom )";
                strSQL+=" ) AS Z2 ON (Z1.CODEMP=Z2.CODEMP AND Z1.NUMCED = Z2.NUMCED)";
                strSQL+=" LEFT OUTER JOIN (";
                        strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, '' AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL30D ";
                        strSQL+=" FROM tbm_cabMovInv AS a1";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                        strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                        strSQL+=" AND a2.st_reg IN ('A','C')";
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                        strSQL+=" AND a3.ne_mod in(1,3)";
                        strSQL+=" AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30)";
                        strSQL+=strAux;
                        strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom ";
                        strSQL+=" ORDER BY a5.tx_nom )";
                        strSQL+=" UNION ALL ";
                        strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL30D ";
                        strSQL+=" FROM tbm_cabMovInv AS a1";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                        strSQL+=" AND a2.st_reg IN ('A','C')";
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                        strSQL+=" AND a3.ne_mod in(1,3)";
                        strSQL+=" AND a4.co_grp IS NULL";
                        strSQL+=" AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30)";
                        strSQL+=strAux;
                        strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
                        strSQL+=" ORDER BY a4.tx_nom )";
               strSQL+=" ) AS Z3 ON (Z1.CODEMP=Z3.CODEMP AND Z1.NUMCED=Z3.NUMCED)";
               strSQL+=" LEFT OUTER JOIN (";
                        strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, '' AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL60D ";
                        strSQL+=" FROM tbm_cabMovInv AS a1";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                        strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                        strSQL+=" AND a2.st_reg IN ('A','C')";
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                        strSQL+=" AND a3.ne_mod in(1,3)";
                        strSQL+=" AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60)";
                        strSQL+=strAux;
                        strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom ";
                        strSQL+=" ORDER BY a5.tx_nom )";
                        strSQL+=" UNION ALL ";
                        strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL60D ";
                        strSQL+=" FROM tbm_cabMovInv AS a1";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                        strSQL+=" AND a2.st_reg IN ('A','C')";
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                        strSQL+=" AND a3.ne_mod in(1,3)";
                        strSQL+=" AND a4.co_grp IS NULL";
                        strSQL+=" AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60)";
                        strSQL+=strAux;
                        strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
                        strSQL+=" ORDER BY a4.tx_nom )";
               strSQL+=" ) AS Z4 ON (Z1.CODEMP=Z4.CODEMP AND Z1.NUMCED=Z4.NUMCED)";
               strSQL+=" LEFT OUTER JOIN (";
                        strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, '' AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL90D ";
                        strSQL+=" FROM tbm_cabMovInv AS a1";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                        strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                        strSQL+=" AND a2.st_reg IN ('A','C')";
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                        strSQL+=" AND a3.ne_mod in(1,3)";
                        strSQL+=" AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90)";
                        strSQL+=strAux;
                        strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom ";
                        strSQL+=" ORDER BY a5.tx_nom )";
                        strSQL+=" UNION ALL ";
                        strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL90D ";
                        strSQL+=" FROM tbm_cabMovInv AS a1";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                        strSQL+=" AND a2.st_reg IN ('A','C')";
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                        strSQL+=" AND a3.ne_mod in(1,3)";
                        strSQL+=" AND a4.co_grp IS NULL";
                        strSQL+=" AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90)";
                        strSQL+=strAux;
                        strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
                        strSQL+=" ORDER BY a4.tx_nom )";
               strSQL+=" ) AS Z5 ON (Z1.CODEMP=Z5.CODEMP AND Z1.NUMCED=Z5.NUMCED)";
               strSQL+=" LEFT OUTER JOIN (";
                        strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, '' AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
                        strSQL+=" FROM tbm_cabMovInv AS a1";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                        strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                        strSQL+=" AND a2.st_reg IN ('A','C')";
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                        strSQL+=" AND a3.ne_mod in(1,3)";
                        strSQL+=" AND (current_date - a2.fe_ven) >=91";
                        strSQL+=strAux;
                        strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom ";
                        strSQL+=" ORDER BY a5.tx_nom )";
                        strSQL+=" UNION ALL ";
                        strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
                        strSQL+=" FROM tbm_cabMovInv AS a1";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                        strSQL+=" AND a2.st_reg IN ('A','C')";
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                        strSQL+=" AND a3.ne_mod in(1,3)";
                        strSQL+=" AND a4.co_grp IS NULL";
                        strSQL+=" AND (current_date - a2.fe_ven) >=91";
                        strSQL+=strAux;
                        strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
                        strSQL+=" ORDER BY a4.tx_nom )";
               strSQL+=" ) AS Z6 ON (Z1.CODEMP=Z6.CODEMP AND Z1.NUMCED=Z6.NUMCED)";
               ///strSQL+=" )";
               strSQL+=" ORDER BY Z1.NOMCLI ";
                
                System.out.println("---Query de registros Consultados --cargarDatFac()--Mnu=321--CxC53: "+ strSQL);
                
                rstFac=stmFac.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDatFac.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;                
                
                while (rstFac.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_FAC_LIN,"");///0
                        vecReg.add(INT_TBL_DAT_FAC_COD_EMP,rstFac.getString("CODEMP"));///1
                        vecReg.add(INT_TBL_DAT_FAC_COD_CLI,rstFac.getString("CODCLI"));///2
                        vecReg.add(INT_TBL_DAT_FAC_RUC_CED,rstFac.getString("NUMCED"));///3
                        vecReg.add(INT_TBL_DAT_FAC_NOM_CLI,rstFac.getString("NOMCLI"));///4
                        vecReg.add(INT_TBL_DAT_FAC_VAL_PEN,rstFac.getString("VALPEN"));///5                        
                        vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,rstFac.getString("VALXVEN"));///6
                        vecReg.add(INT_TBL_DAT_FAC_VAL_30D,rstFac.getString("VAL30D"));///7
                        vecReg.add(INT_TBL_DAT_FAC_VAL_60D,rstFac.getString("VAL60D"));///8
                        vecReg.add(INT_TBL_DAT_FAC_VAL_90D,rstFac.getString("VAL90D"));///9
                        vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,rstFac.getString("VALMAS90"));///10
                        ///datFecVen=rst.getDate("fe_ven");
                        
                        vecDatFac.add(vecReg);
                        i++;
                        ///pgrSis.setValue(i);///
                    }
                    else
                    {
                        break;
                    }
                }
                
                //Asignar vectores al modelo.
                objTblModFac.setData(vecDatFac);
                tblDat.setModel(objTblModFac);
                vecDatFac.clear();
                
                //Calcular totales.
                objTblTot.calcularTotales();
                
                if (intNumTotReg==tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    ///lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDatFacMovReg.getRowCount() + ".");
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
                
                pgrSis.setValue(0);
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
                
                rstFac.close();
                stmFac.close();
                conFac.close();
                rstFac=null;
                stmFac=null;
                conFac=null;
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDatFac()
    {
        int intCodEmp, intCodLoc, intNumTotReg, i;
        
        int intNumDia; 
        String strFecSis, strFecIni, strFecSel, strFecVen;
        int intFecIni[];
        int intFecFin[];//para calcular los dias entre fechas
        int intFecSel[];//fecha seleccionada por el usuario//

        
        double dblSub, dblIva;
        java.util.Date datFecSer, datFecVen, datFecAux;
        
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            conFac=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conFac!=null)
            {
                stmFac=conFac.createStatement();
                //Obtener la fecha del servidor.//
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                datFecAux=datFecSer;
                
                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");

                //Obtener la condición.//
                strAux="";
                
                //Condicion para filtro por Codigo de Grupo del cliente
                if (!(txtCodGrpCli.getText().equals("")))
                {
                    strAux+=" AND a4.co_grp= " + txtCodGrpCli.getText();
                }
                
                //Condicion para filtro por cliente//
                if (txtCodCli.getText().length()>0)
                {
                    ///strAux+=" AND a4.co_cli= " + txtCodCli.getText();
                    strAux+=" AND a4.tx_ide= '" + txtRucCli.getText() + "'";
                }
                else
                {
                    //Condicion para filtro por Ruc del cliente//
                    if (txtRucCli.getText().length()>0)
                    {
                        strAux+=" AND a4.tx_ide= '" + txtRucCli.getText() + "'";
                    }
                }
                
                ////////Condicion para filtro por cliente en un rango especifico//
                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a4.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a4.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Condicion para filtro por empresa//
                    if (txtCodEmp.getText().length()>0)
                        strAux+=" AND a1.co_emp=" + txtCodEmp.getText();
                    else
                        strAux+=" AND a1.co_emp IN (1,2,3,4) ";
                }
                else
                {
                    strAux+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                    
                    if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                    {
                        //Condicion para filtro por cLocal
                        if (txtCodLoc.getText().length()>0)
                            strAux+=" AND a1.co_loc= " + txtCodLoc.getText();
//                        else
//                            strAux+=" AND a1.co_loc= " + objParSis.getCodigoLocal();
                    }
                }
                
                ///FILTRO PARA MOSTRAR CREDITOS Y DEBITOS///
                if ( !(chkMosCre.isSelected() && chkMosDeb.isSelected()) )
                {
                    System.out.println("ENTRO POR FILTRO DE CREDITOS Y DEBITOS ");
                    if (chkMosCre.isSelected())
                        strAux+=" AND a3.tx_natDoc='I'";
                    else
                        strAux+=" AND a3.tx_natDoc='E'";
                }
                ///FILTRO PARA DOCUMENTOS VENCIDOS///
                if (chkMosDocVen.isSelected())
                {
                    System.out.println("ENTRO POR FILTRO DE DOCUMENTOS VENCIDOS ");
                    strFecVen=objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
                    strAux+=" AND a2.fe_ven<='" + strFecVen + "'";
                }
                
                ///FILTRO PARA MOSTRAR RETENCIONES///
                if (!chkMosRet.isSelected())
                {
                    System.out.println("ENTRO POR FILTRO DE MOSTRAR RETENCIONES");
                    strAux+=" AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
                }
                
                //Obtener el número total de registros.//
                strSQL="";
                strSQL+="SELECT COUNT (*)";
                strSQL+=" FROM (";
                        strSQL+="( SELECT Z1.CODEMP, Z1.CODCLI, Z1.NUMCED, Z1.NOMCLI, Z1.VALDOC, Z1.VALABO, Z1.VALPEN, Z2.VALXVEN, Z3.VAL30D, Z4.VAL60D,Z5.VAL90D, Z6.VALMAS90 ";
                        strSQL+=" FROM (";                        
                                strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, ";
                                strSQL+=" SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO, SUM(a2.mo_pag+a2.nd_abo) AS VALPEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom ";   ///, a2.fe_ven
                                strSQL+=" ORDER BY a5.tx_nom )";
                        strSQL+=" ) AS Z1";
                        strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND (current_date - a2.fe_ven) <= 0";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                        strSQL+=" ) AS Z2 ON (Z1.CODEMP=Z2.CODEMP AND Z1.CODCLI = Z2.CODCLI)";
                        strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL30D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                       strSQL+=" ) AS Z3 ON (Z1.CODEMP=Z3.CODEMP AND Z1.CODCLI = Z3.CODCLI)";
                       strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL60D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                       strSQL+=" ) AS Z4 ON (Z1.CODEMP=Z4.CODEMP AND Z1.CODCLI = Z4.CODCLI)";
                       strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL90D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                       strSQL+=" ) AS Z5 ON (Z1.CODEMP=Z5.CODEMP AND Z1.CODCLI = Z5.CODCLI)";
                       strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND (current_date - a2.fe_ven) >=91";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                       strSQL+=" ) AS Z6 ON (Z1.CODEMP=Z6.CODEMP AND Z1.CODCLI = Z6.CODCLI)";
                       strSQL+=" ORDER BY Z1.NOMCLI )";
                       
                       strSQL+=" UNION ALL ";
                       
/*                       

( SELECT Z1.CODEMP, Z1.CODCLI, Z1.NUMCED, Z1.NOMCLI, Z1.VALDOC, Z1.VALABO, Z1.VALPEN, Z2.VALXVEN, Z3.VAL30D, Z4.VAL60D, Z5.VAL90D, Z6.VALMAS90  
FROM (
( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI,  SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO, SUM(a2.mo_pag+a2.nd_abo) AS VALPEN  
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) 
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) 
INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) 
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 
AND a3.ne_mod in(1,3) AND a4.co_grp IS NULL 
AND a1.co_emp=1 
---AND a1.co_loc= 4 
---AND a1.co_emp IN (1,2,3,4)
GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom  
ORDER BY a4.tx_nom)
) AS Z1 LEFT OUTER JOIN (
(SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, 
sum(a2.mo_pag+a2.nd_abo) AS VALXVEN  
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) 
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) 
INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) 
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 
AND a3.ne_mod in(1,3) AND a4.co_grp IS NULL AND (current_date - a2.fe_ven) <= 0 
AND a1.co_emp=1 
---AND a1.co_loc= 4 
---AND a1.co_emp IN (1,2,3,4)
GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom)
) AS Z2 ON (Z1.CODEMP=Z2.CODEMP AND Z1.NUMCED = Z2.NUMCED) LEFT OUTER JOIN (
(SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, 
sum(a2.mo_pag+a2.nd_abo) AS VAL30D  
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) 
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) 
INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) 
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 
AND a3.ne_mod in(1,3) AND a4.co_grp IS NULL AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30) 
AND a1.co_emp=1 
---AND a1.co_loc= 4 
---AND a1.co_emp IN (1,2,3,4)
GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom  
ORDER BY a4.tx_nom)
) AS Z3 ON (Z1.CODEMP=Z3.CODEMP AND Z1.NUMCED=Z3.NUMCED) LEFT OUTER JOIN (
(SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, 
sum(a2.mo_pag+a2.nd_abo) AS VAL60D  
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) 
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) 
INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) 
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 
AND a3.ne_mod in(1,3) AND a4.co_grp IS NULL AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60) 
AND a1.co_emp=1 
---AND a1.co_loc= 4 
---AND a1.co_emp IN (1,2,3,4)
GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom  
ORDER BY a4.tx_nom)
) AS Z4 ON (Z1.CODEMP=Z4.CODEMP AND Z1.NUMCED=Z4.NUMCED) LEFT OUTER JOIN (
( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL90D  
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) 
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) 
INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) 
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 
AND a3.ne_mod in(1,3) AND a4.co_grp IS NULL 
AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90) 
AND a1.co_emp=1 
---AND a1.co_loc= 4 
---AND a1.co_emp IN (1,2,3,4)
GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom  
ORDER BY a4.tx_nom )
) AS Z5 ON (Z1.CODEMP=Z5.CODEMP AND Z1.NUMCED=Z5.NUMCED) LEFT OUTER JOIN (
( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, 
sum(a2.mo_pag+a2.nd_abo) AS VALMAS90  
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) 
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) 
INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) 
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 
AND a3.ne_mod in(1,3) 
AND a4.co_grp IS NULL 
AND (current_date - a2.fe_ven) >=91 
AND a1.co_emp=1 
---AND a1.co_loc= 4 
---AND a1.co_emp IN (1,2,3,4)
GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom  
ORDER BY a4.tx_nom )
) AS Z6 ON (Z1.CODEMP=Z6.CODEMP AND Z1.NUMCED=Z6.NUMCED) 
ORDER BY Z1.NOMCLI)                       
*/
                        strSQL+="( SELECT Z1.CODEMP, Z1.CODCLI, Z1.NUMCED, Z1.NOMCLI, Z1.VALDOC, Z1.VALABO, Z1.VALPEN, Z2.VALXVEN, Z3.VAL30D, Z4.VAL60D, Z5.VAL90D, Z6.VALMAS90  ";
                        strSQL+=" FROM ("; 
                                strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, ";
                                strSQL+=" SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO, SUM(a2.mo_pag+a2.nd_abo) AS VALPEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";   ///, a2.fe_ven   ////AND a4.co_grp is null
                                strSQL+=" ORDER BY a4.tx_nom )";
                        strSQL+=" ) AS Z1";
                        strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND (current_date - a2.fe_ven) <= 0";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
                                strSQL+=" ORDER BY a4.tx_nom )";
                        strSQL+=" ) AS Z2 ON (Z1.CODEMP=Z2.CODEMP AND Z1.NUMCED = Z2.NUMCED)";
                        strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL30D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
                                strSQL+=" ORDER BY a4.tx_nom )";
                       strSQL+=" ) AS Z3 ON (Z1.CODEMP=Z3.CODEMP AND Z1.NUMCED = Z3.NUMCED)";
                       strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL60D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
                                strSQL+=" ORDER BY a4.tx_nom )";
                       strSQL+=" ) AS Z4 ON (Z1.CODEMP=Z4.CODEMP AND Z1.NUMCED = Z4.NUMCED)";
                       strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL90D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
                                strSQL+=" ORDER BY a4.tx_nom )";
                       strSQL+=" ) AS Z5 ON (Z1.CODEMP=Z5.CODEMP AND Z1.NUMCED = Z5.NUMCED)";
                       strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND (current_date - a2.fe_ven) >=91";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
                                strSQL+=" ORDER BY a4.tx_nom )";
                       strSQL+=" ) AS Z6 ON (Z1.CODEMP=Z6.CODEMP AND Z1.NUMCED = Z6.NUMCED) ";
                       strSQL+=" ORDER BY Z1.NOMCLI )";
                strSQL+=" ) AS B1";
                
                ///System.out.println("---Query COUNT --cargarDatFac()--: "+ strSQL);
                
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT * ";
                strSQL+=" FROM (";
                strSQL+="( SELECT Z1.CODEMP, Z1.CODCLI, Z1.NUMCED, Z1.NOMCLI, Z1.VALDOC, Z1.VALABO, Z1.VALPEN, Z2.VALXVEN, Z3.VAL30D, Z4.VAL60D,Z5.VAL90D, Z6.VALMAS90 ";
                        strSQL+=" FROM (";                        
                                strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, ";
                                strSQL+=" SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO, SUM(a2.mo_pag+a2.nd_abo) AS VALPEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom ";   ///, a2.fe_ven
                                strSQL+=" ORDER BY a5.tx_nom )";
                        strSQL+=" ) AS Z1";
                        strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND (current_date - a2.fe_ven) <= 0";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                        strSQL+=" ) AS Z2 ON (Z1.CODEMP=Z2.CODEMP AND Z1.CODCLI = Z2.CODCLI)";
                        strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL30D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                       strSQL+=" ) AS Z3 ON (Z1.CODEMP=Z3.CODEMP AND Z1.CODCLI = Z3.CODCLI)";
                       strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL60D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                       strSQL+=" ) AS Z4 ON (Z1.CODEMP=Z4.CODEMP AND Z1.CODCLI = Z4.CODCLI)";
                       strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL90D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                       strSQL+=" ) AS Z5 ON (Z1.CODEMP=Z5.CODEMP AND Z1.CODCLI = Z5.CODCLI)";
                       strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a5.co_emp AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND (current_date - a2.fe_ven) >=91";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_emp, a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                       strSQL+=" ) AS Z6 ON (Z1.CODEMP=Z6.CODEMP AND Z1.CODCLI = Z6.CODCLI)";
                       strSQL+=" ORDER BY Z1.NOMCLI )";
                       
                       strSQL+=" UNION ALL ";
                       
                       strSQL+="( SELECT Z1.CODEMP, Z1.CODCLI, Z1.NUMCED, Z1.NOMCLI, Z1.VALDOC, Z1.VALABO, Z1.VALPEN, Z2.VALXVEN, Z3.VAL30D, Z4.VAL60D,Z5.VAL90D, Z6.VALMAS90 ";
                        strSQL+=" FROM ("; 
                                strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, ";
                                strSQL+=" SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO, SUM(a2.mo_pag+a2.nd_abo) AS VALPEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";   ///, a2.fe_ven   ////AND a4.co_grp is null
                                strSQL+=" ORDER BY a4.tx_nom )";
                        strSQL+=" ) AS Z1";
                        strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND (current_date - a2.fe_ven) <= 0";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
                                strSQL+=" ORDER BY a4.tx_nom )";
                        strSQL+=" ) AS Z2 ON (Z1.CODEMP=Z2.CODEMP AND Z1.NUMCED = Z2.NUMCED)";
                        strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL30D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
                                strSQL+=" ORDER BY a4.tx_nom )";
                       strSQL+=" ) AS Z3 ON (Z1.CODEMP=Z3.CODEMP AND Z1.NUMCED = Z3.NUMCED)";
                       strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL60D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
                                strSQL+=" ORDER BY a4.tx_nom )";
                       strSQL+=" ) AS Z4 ON (Z1.CODEMP=Z4.CODEMP AND Z1.NUMCED = Z4.NUMCED)";
                       strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL90D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
                                strSQL+=" ORDER BY a4.tx_nom )";
                       strSQL+=" ) AS Z5 ON (Z1.CODEMP=Z5.CODEMP AND Z1.NUMCED = Z5.NUMCED)";
                       strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT a1.co_emp AS CODEMP, a1.co_cli AS CODCLI, a4.tx_ide AS NUMCED, a4.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND (current_date - a2.fe_ven) >=91";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
                                strSQL+=" ORDER BY a4.tx_nom )";
                       strSQL+=" ) AS Z6 ON (Z1.CODEMP=Z6.CODEMP AND Z1.NUMCED = Z6.NUMCED) ";
                       strSQL+=" ORDER BY Z1.NOMCLI )";
                strSQL+=" ) AS X";
                strSQL+=" ORDER BY X.NOMCLI";
                
                System.out.println("---EMPRESA---cargarDatFac()--Mnu=321--CxC53: "+ strSQL);
                
                rstFac=stmFac.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDatFac.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;                
                
                while (rstFac.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_FAC_LIN,"");///0
                        vecReg.add(INT_TBL_DAT_FAC_COD_EMP,rstFac.getString("CODEMP"));///1
                        vecReg.add(INT_TBL_DAT_FAC_COD_CLI,rstFac.getString("CODCLI"));///2
                        vecReg.add(INT_TBL_DAT_FAC_RUC_CED,rstFac.getString("NUMCED"));///3
                        vecReg.add(INT_TBL_DAT_FAC_NOM_CLI,rstFac.getString("NOMCLI"));///4
                        vecReg.add(INT_TBL_DAT_FAC_VAL_PEN,rstFac.getString("VALPEN"));///5                        
                        vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,rstFac.getString("VALXVEN"));///6
                        vecReg.add(INT_TBL_DAT_FAC_VAL_30D,rstFac.getString("VAL30D"));///7
                        vecReg.add(INT_TBL_DAT_FAC_VAL_60D,rstFac.getString("VAL60D"));///8
                        vecReg.add(INT_TBL_DAT_FAC_VAL_90D,rstFac.getString("VAL90D"));///9
                        vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,rstFac.getString("VALMAS90"));///10
                        ///datFecVen=rst.getDate("fe_ven");
                        
                        vecDatFac.add(vecReg);
                        i++;
//                        pgrSis.setValue(i);
                    }
                    else
                    {
                        break;
                    }
                }
                
                //Asignar vectores al modelo.//
                objTblModFac.setData(vecDatFac);
                tblDat.setModel(objTblModFac);
                vecDatFac.clear();
                
                //Calcular totales.//
                objTblTot.calcularTotales();
                
                if (intNumTotReg==tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    ///lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDatFacMovReg.getRowCount() + ".");
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
                
                pgrSis.setValue(0);
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
                
                rstFac.close();
                stmFac.close();
                conFac.close();
                rstFac=null;
                stmFac=null;
                conFac=null;
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDatFacGrp()
    {
        int intCodEmp, intCodLoc, intNumTotReg, i;
        
        int intNumDia; 
        String strFecSis, strFecIni, strFecSel, strFecVen;
        int intFecIni[];
        int intFecFin[];//para calcular los dias entre fechas//
        int intFecSel[];//fecha seleccionada por el usuario//
        
        double dblSub, dblIva;
        java.util.Date datFecSer, datFecVen, datFecAux;
        
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            conFac=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conFac!=null)
            {
                stmFac=conFac.createStatement();
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                datFecAux=datFecSer;
                
                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");

                ////Obtener la condición.////
                strAux="";
                
                //Condicion para filtro por Codigo de Grupo del cliente//
                if (!(txtCodGrpCli.getText().equals("")))
                {
                    strAux+=" AND a4.co_grp= " + txtCodGrpCli.getText();
                }
                
                //Condicion para filtro por cliente//
                if (txtCodCli.getText().length()>0)
                {
                    ////strAux+=" AND a4.co_cli= " + txtCodCli.getText();
                    strAux+=" AND a4.tx_ide= '" + txtRucCli.getText() + "'";
                }
                else
                {                
                    //Condicion para filtro por Ruc del cliente//
                    if (txtRucCli.getText().length()>0)
                    {
                        strAux+=" AND a4.tx_ide= '" + txtRucCli.getText() + "'";
                    }
                }
                
                ////////Condicion para filtro por cliente en un rango especifico//
                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a4.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a4.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Condicion para filtro por empresa
                    if (txtCodEmp.getText().length()>0)
                        strAux+=" AND a1.co_emp=" + txtCodEmp.getText();
                    else
                        strAux+=" AND a1.co_emp IN (1,2,3,4) ";
                }
                else
                {
                    strAux+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                    
                    if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                    {
                        //Condicion para filtro por cLocal//
                        if (txtCodLoc.getText().length()>0)
                            strAux+=" AND a1.co_loc= " + txtCodLoc.getText();
//                        else
//                            strAux+=" AND a1.co_loc= " + objParSis.getCodigoLocal();
                    }
                }
                
                ///FILTRO PARA MOSTRAR CREDITOS Y DEBITOS///
                if ( !(chkMosCre.isSelected() && chkMosDeb.isSelected()) )
                {
                    System.out.println("ENTRO POR FILTRO DE CREDITOS Y DEBITOS ");
                    if (chkMosCre.isSelected())
                        strAux+=" AND a3.tx_natDoc='I'";
                    else
                        strAux+=" AND a3.tx_natDoc='E'";
                }
                ///FILTRO PARA DOCUMENTOS VENCIDOS///
                if (chkMosDocVen.isSelected())
                {
                    System.out.println("ENTRO POR FILTRO DE DOCUMENTOS VENCIDOS ");
                    strFecVen=objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
                    strAux+=" AND a2.fe_ven<='" + strFecVen + "'";
                }
                ///FILTRO PARA MOSTRAR RETENCIONES///
                if (!chkMosRet.isSelected())
                {
                    System.out.println("ENTRO POR FILTRO DE MOSTRAR RETENCIONES");
                    strAux+=" AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
                }
                
                //Obtener el número total de registros.//
                strSQL="";
                strSQL+="SELECT COUNT (*)";
                strSQL+=" FROM (";
                        strSQL+="( SELECT Z1.CODEMP, Z1.CODCLI, Z1.NUMCED, Z1.NOMCLI, Z1.VALDOC, Z1.VALABO, Z1.VALPEN, Z2.VALXVEN, Z3.VAL30D, Z4.VAL60D,Z5.VAL90D, Z6.VALMAS90 ";
                        strSQL+=" FROM (";
                                strSQL+="( SELECT MIN(a5.co_emp) AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, ";
                                strSQL+=" SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO, SUM(a2.mo_pag+a2.nd_abo) AS VALPEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_grp, a5.tx_nom ";   ///, a2.fe_ven
                                strSQL+=" ORDER BY a5.tx_nom )";
                        strSQL+=" ) AS Z1";
                        strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT MIN(a5.co_emp) AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND (current_date - a2.fe_ven) <= 0";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                        strSQL+=" ) AS Z2 ON (Z1.CODEMP=Z2.CODEMP AND Z1.CODCLI = Z2.CODCLI)";
                        strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT MIN(a5.co_emp) AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL30D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                       strSQL+=" ) AS Z3 ON (Z1.CODEMP=Z3.CODEMP AND Z1.CODCLI = Z3.CODCLI)";
                       strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT MIN(a5.co_emp) AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL60D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                       strSQL+=" ) AS Z4 ON (Z1.CODEMP=Z4.CODEMP AND Z1.CODCLI = Z4.CODCLI)";
                       strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT MIN(a5.co_emp) AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL90D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                       strSQL+=" ) AS Z5 ON (Z1.CODEMP=Z5.CODEMP AND Z1.CODCLI = Z5.CODCLI)";
                       strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT MIN(a5.co_emp) AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND (current_date - a2.fe_ven) >=91";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                       strSQL+=" ) AS Z6 ON (Z1.CODEMP=Z6.CODEMP AND Z1.CODCLI = Z6.CODCLI)";
                       strSQL+=" ORDER BY Z1.NOMCLI )";
                       
                       strSQL+=" UNION ALL ";
                       
/*
SELECT Z1.CODEMP, Z1.CODCLI, Z1.NUMCED, Z1.NOMCLI, Z1.VALDOC, Z1.VALABO, Z1.VALPEN,
Z2.VALXVEN, Z3.VAL30D, Z4.VAL60D, Z5.VAL90D, Z6.VALMAS90  
FROM (

SELECT X2.CODEMP, X2.CODCLI, X2.NUMCED, X2.NOMCLI, X1.VALDOC, X1.VALABO, X1.VALPEN
FROM (
( SELECT a4.tx_ide AS NUMCED, SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO, SUM(a2.mo_pag+a2.nd_abo) AS VALPEN  
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) 
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) 
INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) 
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 
AND a3.ne_mod in(1,3) AND a4.co_grp IS NULL 
---AND a1.co_emp=1 
---AND a1.co_loc= 4 
--AND a4.tx_ide = '1790004724001'
AND a1.co_emp IN (1,2,3,4)
GROUP BY  a4.tx_ide )
) AS X1 LEFT OUTER JOIN (
SELECT a2.co_emp AS CODEMP, MAX(a2.co_cli) AS CODCLI, a2.tx_ide AS NUMCED, a2.tx_nom AS NOMCLI
FROM ( 
SELECT MIN(co_emp) AS co_emp, tx_ide 
FROM tbm_cli GROUP BY tx_ide 
) AS a1 
INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide) 
--WHERE a2.tx_ide = '1790004724001'
GROUP BY a2.co_emp, a2.tx_ide, a2.tx_nom
ORDER BY a2.tx_nom
) AS X2 ON (X1.NUMCED=X2.NUMCED)
) AS Z1 LEFT OUTER JOIN (

SELECT X2.CODEMP, X2.CODCLI, X2.NUMCED, X2.NOMCLI, X1.VALXVEN
FROM (
(SELECT a4.tx_ide AS NUMCED, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN  
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) 
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) 
INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) 
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 
AND a3.ne_mod in(1,3) AND a4.co_grp IS NULL AND (current_date - a2.fe_ven) <= 0 
---AND a1.co_emp=1 
---AND a1.co_loc= 4 
AND a1.co_emp IN (1,2,3,4)
GROUP BY a4.tx_ide )
) AS X1 LEFT OUTER JOIN (
SELECT a2.co_emp AS CODEMP, MAX(a2.co_cli) AS CODCLI, a2.tx_ide AS NUMCED, a2.tx_nom AS NOMCLI
FROM ( 
SELECT MIN(co_emp) AS co_emp, tx_ide 
FROM tbm_cli GROUP BY tx_ide 
) AS a1 
INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide) 
--WHERE a2.tx_ide = '1790004724001'
GROUP BY a2.co_emp, a2.tx_ide, a2.tx_nom
ORDER BY a2.tx_nom
) AS X2 ON (X1.NUMCED=X2.NUMCED)

) AS Z2 ON (Z1.CODEMP=Z2.CODEMP AND Z1.NUMCED = Z2.NUMCED) LEFT OUTER JOIN (

SELECT X2.CODEMP, X2.CODCLI, X2.NUMCED, X2.NOMCLI, X1.VAL30D
FROM (
(SELECT a4.tx_ide AS NUMCED, sum(a2.mo_pag+a2.nd_abo) AS VAL30D  
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) 
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) 
INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) 
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 
AND a3.ne_mod in(1,3) AND a4.co_grp IS NULL AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30) 
---AND a1.co_emp=1 
---AND a1.co_loc= 4 
AND a1.co_emp IN (1,2,3,4)
GROUP BY a4.tx_ide)
) AS X1 LEFT OUTER JOIN (
SELECT a2.co_emp AS CODEMP, MAX(a2.co_cli) AS CODCLI, a2.tx_ide AS NUMCED, a2.tx_nom AS NOMCLI
FROM ( 
SELECT MIN(co_emp) AS co_emp, tx_ide 
FROM tbm_cli GROUP BY tx_ide 
) AS a1 
INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide) 
--WHERE a2.tx_ide = '1790004724001'
GROUP BY a2.co_emp, a2.tx_ide, a2.tx_nom
ORDER BY a2.tx_nom
) AS X2 ON (X1.NUMCED=X2.NUMCED)

) AS Z3 ON (Z1.CODEMP=Z3.CODEMP AND Z1.NUMCED=Z3.NUMCED) LEFT OUTER JOIN (

SELECT X2.CODEMP, X2.CODCLI, X2.NUMCED, X2.NOMCLI, X1.VAL60D
FROM (
(SELECT a4.tx_ide AS NUMCED, sum(a2.mo_pag+a2.nd_abo) AS VAL60D  
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) 
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) 
INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) 
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 
AND a3.ne_mod in(1,3) AND a4.co_grp IS NULL AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60) 
---AND a1.co_emp=1 
---AND a1.co_loc= 4 
AND a1.co_emp IN (1,2,3,4)
GROUP BY a4.tx_ide)
) AS X1 LEFT OUTER JOIN (
SELECT a2.co_emp AS CODEMP, MAX(a2.co_cli) AS CODCLI, a2.tx_ide AS NUMCED, a2.tx_nom AS NOMCLI
FROM ( 
SELECT MIN(co_emp) AS co_emp, tx_ide 
FROM tbm_cli GROUP BY tx_ide 
) AS a1 
INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide) 
--WHERE a2.tx_ide = '1790004724001'
GROUP BY a2.co_emp, a2.tx_ide, a2.tx_nom
ORDER BY a2.tx_nom
) AS X2 ON (X1.NUMCED=X2.NUMCED)

) AS Z4 ON (Z1.CODEMP=Z4.CODEMP AND Z1.NUMCED=Z4.NUMCED) LEFT OUTER JOIN (

SELECT X2.CODEMP, X2.CODCLI, X2.NUMCED, X2.NOMCLI, X1.VAL90D
FROM (
( SELECT a4.tx_ide AS NUMCED, sum(a2.mo_pag+a2.nd_abo) AS VAL90D  
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) 
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) 
INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) 
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 
AND a3.ne_mod in(1,3) AND a4.co_grp IS NULL 
AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90) 
---AND a1.co_emp=1 
---AND a1.co_loc= 4 
AND a1.co_emp IN (1,2,3,4)
GROUP BY a4.tx_ide)
) AS X1 LEFT OUTER JOIN (
SELECT a2.co_emp AS CODEMP, MAX(a2.co_cli) AS CODCLI, a2.tx_ide AS NUMCED, a2.tx_nom AS NOMCLI
FROM ( 
SELECT MIN(co_emp) AS co_emp, tx_ide 
FROM tbm_cli GROUP BY tx_ide 
) AS a1 
INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide) 
--WHERE a2.tx_ide = '1790004724001'
GROUP BY a2.co_emp, a2.tx_ide, a2.tx_nom
ORDER BY a2.tx_nom
) AS X2 ON (X1.NUMCED=X2.NUMCED)

) AS Z5 ON (Z1.CODEMP=Z5.CODEMP AND Z1.NUMCED=Z5.NUMCED) LEFT OUTER JOIN (

SELECT X2.CODEMP, X2.CODCLI, X2.NUMCED, X2.NOMCLI, X1.VALMAS90
FROM (
( SELECT a4.tx_ide AS NUMCED, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90  
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) 
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) 
INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) 
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 
AND a3.ne_mod in(1,3) 
AND a4.co_grp IS NULL 
AND (current_date - a2.fe_ven) >=91 
---AND a1.co_emp=1 
---AND a1.co_loc= 4 
AND a1.co_emp IN (1,2,3,4)
GROUP BY a4.tx_ide)
) AS X1 LEFT OUTER JOIN (
SELECT a2.co_emp AS CODEMP, MAX(a2.co_cli) AS CODCLI, a2.tx_ide AS NUMCED, a2.tx_nom AS NOMCLI
FROM ( 
SELECT MIN(co_emp) AS co_emp, tx_ide 
FROM tbm_cli GROUP BY tx_ide 
) AS a1 
INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide) 
--WHERE a2.tx_ide = '1790004724001'
GROUP BY a2.co_emp, a2.tx_ide, a2.tx_nom
ORDER BY a2.tx_nom
) AS X2 ON (X1.NUMCED=X2.NUMCED)

) AS Z6 ON (Z1.CODEMP=Z6.CODEMP AND Z1.NUMCED=Z6.NUMCED) 
ORDER BY Z1.NOMCLI
                      
*/
                       
                      
                        strSQL+="( SELECT Z1.CODEMP, Z1.CODCLI, Z1.NUMCED, Z1.NOMCLI, Z1.VALDOC, Z1.VALABO, Z1.VALPEN, Z2.VALXVEN, Z3.VAL30D, Z4.VAL60D, Z5.VAL90D, Z6.VALMAS90  ";
                        strSQL+=" FROM ("; 
                            strSQL+=" SELECT X2.CODEMP, X2.CODCLI, X2.NUMCED, X2.NOMCLI, X1.VALDOC, X1.VALABO, X1.VALPEN ";
                            strSQL+=" FROM ( ";
                                strSQL+="( SELECT a4.tx_ide AS NUMCED, SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO, SUM(a2.mo_pag+a2.nd_abo) AS VALPEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a4.tx_ide )";   ///, a2.fe_ven   ////AND a4.co_grp is null
                            strSQL+=" ) AS X1 LEFT OUTER JOIN ( ";
                                strSQL+=" SELECT a2.co_emp AS CODEMP, MAX(a2.co_cli) AS CODCLI, a2.tx_ide AS NUMCED, a2.tx_nom AS NOMCLI ";
                                strSQL+=" FROM ( ";
                                strSQL+="  SELECT MIN(co_emp) AS co_emp, tx_ide ";
                                strSQL+="  FROM tbm_cli GROUP BY tx_ide ";
                                strSQL+="  ) AS a1";
                                strSQL+="  INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide) ";
                                strSQL+="  GROUP BY a2.co_emp, a2.tx_ide, a2.tx_nom ";
                                strSQL+="  ORDER BY a2.tx_nom ";
                            strSQL+="  ) AS X2 ON (X1.NUMCED=X2.NUMCED) ";                                
                        strSQL+=" ) AS Z1";
                        strSQL+=" LEFT OUTER JOIN (";
                        
                            strSQL+=" SELECT X2.CODEMP, X2.CODCLI, X2.NUMCED, X2.NOMCLI, X1.VALXVEN ";
                            strSQL+=" FROM ( ";
                                strSQL+="( SELECT a4.tx_ide AS NUMCED, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND (current_date - a2.fe_ven) <= 0";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a4.tx_ide )";
                            strSQL+=" ) AS X1 LEFT OUTER JOIN ( ";
                                strSQL+=" SELECT a2.co_emp AS CODEMP, MAX(a2.co_cli) AS CODCLI, a2.tx_ide AS NUMCED, a2.tx_nom AS NOMCLI ";
                                strSQL+=" FROM ( ";
                                strSQL+="  SELECT MIN(co_emp) AS co_emp, tx_ide ";
                                strSQL+="  FROM tbm_cli GROUP BY tx_ide ";
                                strSQL+="  ) AS a1";
                                strSQL+="  INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide) ";
                                strSQL+="  GROUP BY a2.co_emp, a2.tx_ide, a2.tx_nom ";
                                strSQL+="  ORDER BY a2.tx_nom ";
                            strSQL+="  ) AS X2 ON (X1.NUMCED=X2.NUMCED) ";
                        strSQL+=" ) AS Z2 ON (Z1.CODEMP=Z2.CODEMP AND Z1.NUMCED = Z2.NUMCED)";
                        strSQL+=" LEFT OUTER JOIN (";
                        
                            strSQL+=" SELECT X2.CODEMP, X2.CODCLI, X2.NUMCED, X2.NOMCLI, X1.VAL30D ";
                            strSQL+=" FROM ( ";
                                strSQL+="( SELECT a4.tx_ide AS NUMCED, sum(a2.mo_pag+a2.nd_abo) AS VAL30D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a4.tx_ide )";
                            strSQL+=" ) AS X1 LEFT OUTER JOIN ( ";
                                strSQL+=" SELECT a2.co_emp AS CODEMP, MAX(a2.co_cli) AS CODCLI, a2.tx_ide AS NUMCED, a2.tx_nom AS NOMCLI ";
                                strSQL+=" FROM ( ";
                                strSQL+="  SELECT MIN(co_emp) AS co_emp, tx_ide ";
                                strSQL+="  FROM tbm_cli GROUP BY tx_ide ";
                                strSQL+="  ) AS a1";
                                strSQL+="  INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide) ";
                                strSQL+="  GROUP BY a2.co_emp, a2.tx_ide, a2.tx_nom ";
                                strSQL+="  ORDER BY a2.tx_nom ";
                            strSQL+="  ) AS X2 ON (X1.NUMCED=X2.NUMCED) ";
                            
                       strSQL+=" ) AS Z3 ON (Z1.CODEMP=Z3.CODEMP AND Z1.NUMCED = Z3.NUMCED)";
                       strSQL+=" LEFT OUTER JOIN (";
                       
                            strSQL+=" SELECT X2.CODEMP, X2.CODCLI, X2.NUMCED, X2.NOMCLI, X1.VAL60D ";
                            strSQL+=" FROM ( ";
                                strSQL+="( SELECT a4.tx_ide AS NUMCED, sum(a2.mo_pag+a2.nd_abo) AS VAL60D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a4.tx_ide )";
                            strSQL+=" ) AS X1 LEFT OUTER JOIN ( ";
                                strSQL+=" SELECT a2.co_emp AS CODEMP, MAX(a2.co_cli) AS CODCLI, a2.tx_ide AS NUMCED, a2.tx_nom AS NOMCLI ";
                                strSQL+=" FROM ( ";
                                strSQL+="  SELECT MIN(co_emp) AS co_emp, tx_ide ";
                                strSQL+="  FROM tbm_cli GROUP BY tx_ide ";
                                strSQL+="  ) AS a1";
                                strSQL+="  INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide) ";
                                strSQL+="  GROUP BY a2.co_emp, a2.tx_ide, a2.tx_nom ";
                                strSQL+="  ORDER BY a2.tx_nom ";
                            strSQL+="  ) AS X2 ON (X1.NUMCED=X2.NUMCED) ";                                
                                
                       strSQL+=" ) AS Z4 ON (Z1.CODEMP=Z4.CODEMP AND Z1.NUMCED = Z4.NUMCED)";
                       strSQL+=" LEFT OUTER JOIN (";
                       
                            strSQL+=" SELECT X2.CODEMP, X2.CODCLI, X2.NUMCED, X2.NOMCLI, X1.VAL90D ";
                            strSQL+=" FROM ( ";
                                strSQL+="( SELECT a4.tx_ide AS NUMCED, sum(a2.mo_pag+a2.nd_abo) AS VAL90D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a4.tx_ide )";
                            strSQL+=" ) AS X1 LEFT OUTER JOIN ( ";
                                strSQL+=" SELECT a2.co_emp AS CODEMP, MAX(a2.co_cli) AS CODCLI, a2.tx_ide AS NUMCED, a2.tx_nom AS NOMCLI ";
                                strSQL+=" FROM ( ";
                                strSQL+="  SELECT MIN(co_emp) AS co_emp, tx_ide ";
                                strSQL+="  FROM tbm_cli GROUP BY tx_ide ";
                                strSQL+="  ) AS a1";
                                strSQL+="  INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide) ";
                                strSQL+="  GROUP BY a2.co_emp, a2.tx_ide, a2.tx_nom ";
                                strSQL+="  ORDER BY a2.tx_nom ";
                            strSQL+="  ) AS X2 ON (X1.NUMCED=X2.NUMCED) ";
                            
                       strSQL+=" ) AS Z5 ON (Z1.CODEMP=Z5.CODEMP AND Z1.NUMCED = Z5.NUMCED)";
                       strSQL+=" LEFT OUTER JOIN (";
                       
                            strSQL+=" SELECT X2.CODEMP, X2.CODCLI, X2.NUMCED, X2.NOMCLI, X1.VALMAS90 ";
                            strSQL+=" FROM ( ";
                                strSQL+="( SELECT a4.tx_ide AS NUMCED, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND (current_date - a2.fe_ven) >=91";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a4.tx_ide )";
                            strSQL+=" ) AS X1 LEFT OUTER JOIN ( ";
                                strSQL+=" SELECT a2.co_emp AS CODEMP, MAX(a2.co_cli) AS CODCLI, a2.tx_ide AS NUMCED, a2.tx_nom AS NOMCLI ";
                                strSQL+=" FROM ( ";
                                strSQL+="  SELECT MIN(co_emp) AS co_emp, tx_ide ";
                                strSQL+="  FROM tbm_cli GROUP BY tx_ide ";
                                strSQL+="  ) AS a1";
                                strSQL+="  INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide) ";
                                strSQL+="  GROUP BY a2.co_emp, a2.tx_ide, a2.tx_nom ";
                                strSQL+="  ORDER BY a2.tx_nom ";
                            strSQL+="  ) AS X2 ON (X1.NUMCED=X2.NUMCED) ";
                            
                       strSQL+=" ) AS Z6 ON (Z1.CODEMP=Z6.CODEMP AND Z1.NUMCED = Z6.NUMCED) ";
                       strSQL+=" ORDER BY Z1.NOMCLI )";
                strSQL+=" ) AS B1";
                
                ///System.out.println("---Query COUNT --cargarDatFac()--: "+ strSQL);
                
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT * ";
                strSQL+=" FROM (";
                strSQL+="( SELECT Z1.CODEMP, Z1.CODCLI, Z1.NUMCED, Z1.NOMCLI, Z1.VALDOC, Z1.VALABO, Z1.VALPEN, Z2.VALXVEN, Z3.VAL30D, Z4.VAL60D,Z5.VAL90D, Z6.VALMAS90 ";
                        strSQL+=" FROM (";                        
                                strSQL+="( SELECT MIN(a5.co_emp) AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, ";
                                strSQL+=" SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO, SUM(a2.mo_pag+a2.nd_abo) AS VALPEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_grp, a5.tx_nom ";   ///, a2.fe_ven
                                strSQL+=" ORDER BY a5.tx_nom )";
                        strSQL+=" ) AS Z1";
                        strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT MIN(a5.co_emp) AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND (current_date - a2.fe_ven) <= 0";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                        strSQL+=" ) AS Z2 ON (Z1.CODEMP=Z2.CODEMP AND Z1.CODCLI = Z2.CODCLI)";
                        strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT MIN(a5.co_emp) AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL30D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                        strSQL+=" ) AS Z3 ON (Z1.CODEMP=Z3.CODEMP AND Z1.CODCLI = Z3.CODCLI)";
                        strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT MIN(a5.co_emp) AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL60D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                        strSQL+=" ) AS Z4 ON (Z1.CODEMP=Z4.CODEMP AND Z1.CODCLI = Z4.CODCLI)";
                        strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT MIN(a5.co_emp) AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VAL90D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                        strSQL+=" ) AS Z5 ON (Z1.CODEMP=Z5.CODEMP AND Z1.CODCLI = Z5.CODCLI)";
                        strSQL+=" LEFT OUTER JOIN (";
                                strSQL+="( SELECT MIN(a5.co_emp) AS CODEMP, a5.co_grp AS CODCLI, CAST('' AS varchar(13)) AS NUMCED, a5.tx_nom AS NOMCLI, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND (current_date - a2.fe_ven) >=91";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a5.co_grp, a5.tx_nom ";
                                strSQL+=" ORDER BY a5.tx_nom )";
                        strSQL+=" ) AS Z6 ON (Z1.CODEMP=Z6.CODEMP AND Z1.CODCLI = Z6.CODCLI)";
                        strSQL+=" ORDER BY Z1.NOMCLI )";
                       
                        strSQL+=" UNION ALL ";
                       
                        strSQL+="( SELECT Z1.CODEMP, Z1.CODCLI, Z1.NUMCED, Z1.NOMCLI, Z1.VALDOC, Z1.VALABO, Z1.VALPEN, Z2.VALXVEN, Z3.VAL30D, Z4.VAL60D,Z5.VAL90D, Z6.VALMAS90 ";
                        strSQL+=" FROM (";
                        
                            strSQL+=" SELECT X2.CODEMP, X2.CODCLI, X2.NUMCED, X2.NOMCLI, X1.VALDOC, X1.VALABO, X1.VALPEN ";
                            strSQL+=" FROM ( ";
                                strSQL+="( SELECT a4.tx_ide AS NUMCED, SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO, SUM(a2.mo_pag+a2.nd_abo) AS VALPEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a4.tx_ide )";
                            strSQL+=" ) AS X1 LEFT OUTER JOIN ( ";
                                strSQL+=" SELECT a2.co_emp AS CODEMP, MAX(a2.co_cli) AS CODCLI, a2.tx_ide AS NUMCED, a2.tx_nom AS NOMCLI ";
                                strSQL+=" FROM ( ";
                                strSQL+="  SELECT MIN(co_emp) AS co_emp, tx_ide ";
                                strSQL+="  FROM tbm_cli GROUP BY tx_ide ";
                                strSQL+="  ) AS a1";
                                strSQL+="  INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide) ";
                                strSQL+="  GROUP BY a2.co_emp, a2.tx_ide, a2.tx_nom ";
                                strSQL+="  ORDER BY a2.tx_nom ";
                            strSQL+="  ) AS X2 ON (X1.NUMCED=X2.NUMCED) ";
                            
                        strSQL+=" ) AS Z1";
                        strSQL+=" LEFT OUTER JOIN (";
                        
                            strSQL+=" SELECT X2.CODEMP, X2.CODCLI, X2.NUMCED, X2.NOMCLI, X1.VALXVEN ";
                            strSQL+=" FROM ( ";
                                strSQL+="( SELECT a4.tx_ide AS NUMCED, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND (current_date - a2.fe_ven) <= 0";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a4.tx_ide )";
                            strSQL+=" ) AS X1 LEFT OUTER JOIN ( ";
                                strSQL+=" SELECT a2.co_emp AS CODEMP, MAX(a2.co_cli) AS CODCLI, a2.tx_ide AS NUMCED, a2.tx_nom AS NOMCLI ";
                                strSQL+=" FROM ( ";
                                strSQL+="  SELECT MIN(co_emp) AS co_emp, tx_ide ";
                                strSQL+="  FROM tbm_cli GROUP BY tx_ide ";
                                strSQL+="  ) AS a1";
                                strSQL+="  INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide) ";
                                strSQL+="  GROUP BY a2.co_emp, a2.tx_ide, a2.tx_nom ";
                                strSQL+="  ORDER BY a2.tx_nom ";
                            strSQL+="  ) AS X2 ON (X1.NUMCED=X2.NUMCED) ";
                            
                        strSQL+=" ) AS Z2 ON (Z1.CODEMP=Z2.CODEMP AND Z1.NUMCED = Z2.NUMCED)";
                        strSQL+=" LEFT OUTER JOIN (";
                        
                            strSQL+=" SELECT X2.CODEMP, X2.CODCLI, X2.NUMCED, X2.NOMCLI, X1.VAL30D ";
                            strSQL+=" FROM ( ";
                                strSQL+="( SELECT a4.tx_ide AS NUMCED, sum(a2.mo_pag+a2.nd_abo) AS VAL30D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a4.tx_ide )";
                            strSQL+=" ) AS X1 LEFT OUTER JOIN ( ";
                                strSQL+=" SELECT a2.co_emp AS CODEMP, MAX(a2.co_cli) AS CODCLI, a2.tx_ide AS NUMCED, a2.tx_nom AS NOMCLI ";
                                strSQL+=" FROM ( ";
                                strSQL+="  SELECT MIN(co_emp) AS co_emp, tx_ide ";
                                strSQL+="  FROM tbm_cli GROUP BY tx_ide ";
                                strSQL+="  ) AS a1";
                                strSQL+="  INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide) ";
                                strSQL+="  GROUP BY a2.co_emp, a2.tx_ide, a2.tx_nom ";
                                strSQL+="  ORDER BY a2.tx_nom ";
                            strSQL+="  ) AS X2 ON (X1.NUMCED=X2.NUMCED) ";
                            
                        strSQL+=" ) AS Z3 ON (Z1.CODEMP=Z3.CODEMP AND Z1.NUMCED = Z3.NUMCED)";
                        strSQL+=" LEFT OUTER JOIN (";
                        
                            strSQL+=" SELECT X2.CODEMP, X2.CODCLI, X2.NUMCED, X2.NOMCLI, X1.VAL60D ";
                            strSQL+=" FROM ( ";
                                strSQL+="( SELECT a4.tx_ide AS NUMCED, sum(a2.mo_pag+a2.nd_abo) AS VAL60D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a4.tx_ide )";
                            strSQL+=" ) AS X1 LEFT OUTER JOIN ( ";
                                strSQL+=" SELECT a2.co_emp AS CODEMP, MAX(a2.co_cli) AS CODCLI, a2.tx_ide AS NUMCED, a2.tx_nom AS NOMCLI ";
                                strSQL+=" FROM ( ";
                                strSQL+="  SELECT MIN(co_emp) AS co_emp, tx_ide ";
                                strSQL+="  FROM tbm_cli GROUP BY tx_ide ";
                                strSQL+="  ) AS a1";
                                strSQL+="  INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide) ";
                                strSQL+="  GROUP BY a2.co_emp, a2.tx_ide, a2.tx_nom ";
                                strSQL+="  ORDER BY a2.tx_nom ";
                            strSQL+="  ) AS X2 ON (X1.NUMCED=X2.NUMCED) ";
                            
                        strSQL+=" ) AS Z4 ON (Z1.CODEMP=Z4.CODEMP AND Z1.NUMCED = Z4.NUMCED)";
                        strSQL+=" LEFT OUTER JOIN (";
                        
                            strSQL+=" SELECT X2.CODEMP, X2.CODCLI, X2.NUMCED, X2.NOMCLI, X1.VAL90D ";
                            strSQL+=" FROM ( ";
                                strSQL+="( SELECT a4.tx_ide AS NUMCED, sum(a2.mo_pag+a2.nd_abo) AS VAL90D ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90)";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a4.tx_ide )";
                            strSQL+=" ) AS X1 LEFT OUTER JOIN ( ";
                                strSQL+=" SELECT a2.co_emp AS CODEMP, MAX(a2.co_cli) AS CODCLI, a2.tx_ide AS NUMCED, a2.tx_nom AS NOMCLI ";
                                strSQL+=" FROM ( ";
                                strSQL+="  SELECT MIN(co_emp) AS co_emp, tx_ide ";
                                strSQL+="  FROM tbm_cli GROUP BY tx_ide ";
                                strSQL+="  ) AS a1";
                                strSQL+="  INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide) ";
                                strSQL+="  GROUP BY a2.co_emp, a2.tx_ide, a2.tx_nom ";
                                strSQL+="  ORDER BY a2.tx_nom ";
                            strSQL+="  ) AS X2 ON (X1.NUMCED=X2.NUMCED) ";
                            
                        strSQL+=" ) AS Z5 ON (Z1.CODEMP=Z5.CODEMP AND Z1.NUMCED = Z5.NUMCED)";
                        strSQL+=" LEFT OUTER JOIN (";
                        
                            strSQL+=" SELECT X2.CODEMP, X2.CODCLI, X2.NUMCED, X2.NOMCLI, X1.VALMAS90 ";
                            strSQL+=" FROM ( ";
                                strSQL+="( SELECT a4.tx_ide AS NUMCED, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
                                strSQL+=" FROM tbm_cabMovInv AS a1";
                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                                strSQL+=" AND a2.st_reg IN ('A','C')";
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                                strSQL+=" AND a3.ne_mod in(1,3)";
                                strSQL+=" AND a4.co_grp IS NULL";
                                strSQL+=" AND (current_date - a2.fe_ven) >=91";
                                strSQL+=strAux;
                                strSQL+=" GROUP BY a4.tx_ide )";
                            strSQL+=" ) AS X1 LEFT OUTER JOIN ( ";
                                strSQL+=" SELECT a2.co_emp AS CODEMP, MAX(a2.co_cli) AS CODCLI, a2.tx_ide AS NUMCED, a2.tx_nom AS NOMCLI ";
                                strSQL+=" FROM ( ";
                                strSQL+="  SELECT MIN(co_emp) AS co_emp, tx_ide ";
                                strSQL+="  FROM tbm_cli GROUP BY tx_ide ";
                                strSQL+="  ) AS a1";
                                strSQL+="  INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide) ";
                                strSQL+="  GROUP BY a2.co_emp, a2.tx_ide, a2.tx_nom ";
                                strSQL+="  ORDER BY a2.tx_nom ";
                            strSQL+="  ) AS X2 ON (X1.NUMCED=X2.NUMCED) ";
                            
                       strSQL+=" ) AS Z6 ON (Z1.CODEMP=Z6.CODEMP AND Z1.NUMCED = Z6.NUMCED) ";
                       strSQL+=" ORDER BY Z1.NOMCLI )";
                strSQL+=" ) AS X";
                strSQL+=" ORDER BY X.NOMCLI";
                
                System.out.println("---GRUPO---cargarDatFacGrp()--Mnu=321--CxC53: "+ strSQL);
                
                rstFac=stmFac.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDatFac.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;                
                
                while (rstFac.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_FAC_LIN,"");///0
                        vecReg.add(INT_TBL_DAT_FAC_COD_EMP,rstFac.getString("CODEMP"));///1
                        vecReg.add(INT_TBL_DAT_FAC_COD_CLI,rstFac.getString("CODCLI"));///2
                        vecReg.add(INT_TBL_DAT_FAC_RUC_CED,rstFac.getString("NUMCED"));///3
                        vecReg.add(INT_TBL_DAT_FAC_NOM_CLI,rstFac.getString("NOMCLI"));///4
                        vecReg.add(INT_TBL_DAT_FAC_VAL_PEN,rstFac.getString("VALPEN"));///5                        
                        vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,rstFac.getString("VALXVEN"));///6
                        vecReg.add(INT_TBL_DAT_FAC_VAL_30D,rstFac.getString("VAL30D"));///7
                        vecReg.add(INT_TBL_DAT_FAC_VAL_60D,rstFac.getString("VAL60D"));///8
                        vecReg.add(INT_TBL_DAT_FAC_VAL_90D,rstFac.getString("VAL90D"));///9
                        vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,rstFac.getString("VALMAS90"));///10
                        ///datFecVen=rst.getDate("fe_ven");
                        
                        vecDatFac.add(vecReg);
                        i++;
//                        pgrSis.setValue(i);
                    }
                    else
                    {
                        break;
                    }
                }
                
                //Asignar vectores al modelo.//
                objTblModFac.setData(vecDatFac);
                tblDat.setModel(objTblModFac);
                vecDatFac.clear();
                
                
                //Calcular totales.//
                objTblTot.calcularTotales();
                
                if (intNumTotReg==tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    ///lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDatFacMovReg.getRowCount() + ".");
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
                
                pgrSis.setValue(0);
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
                
                rstFac.close();
                stmFac.close();
                conFac.close();
                rstFac=null;
                stmFac=null;
                conFac=null;
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
//     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
//     * @return true: Si se pudo consultar los registros.
//     * <BR>false: En el caso contrario.
//     */
//    private boolean cargarDatFacLoc()
//    {
//        int intCodEmp, intCodLoc, intNumTotReg, i;
//        
//        int intNumDia; 
//        String strFecSis, strFecIni, strFecSel, strFecVen;
//        int intFecIni[];
//        int intFecFin[];//para calcular los dias entre fechas
//        int intFecSel[];//fecha seleccionada por el usuario//
//
//        
//        double dblSub, dblIva;
//        java.util.Date datFecSer, datFecVen, datFecAux;
//        
//        boolean blnRes=true;
//        try
//        {
//            pgrSis.setIndeterminate(true);
//            butCon.setText("Detener");
//            lblMsgSis.setText("Obteniendo datos...");
//            
//            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
//            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
//            conFac=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            if (conFac!=null)
//            {
//                stmFac=conFac.createStatement();
//                //Obtener la fecha del servidor.
//                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//                if (datFecSer==null)
//                    return false;
//                datFecAux=datFecSer;
//                
//                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
//
//                //Obtener la condición.
//                strAux="";
//                
//                //Condicion para filtro por cliente
//                if (txtCodCli.getText().length()>0)
//                {
//                    strAux+=" AND a4.co_cli= " + txtCodCli.getText();
//                }
//                
//                //Condicion para filtro por Codigo de Grupo del cliente
//                if (!(txtCodGrpCli.getText().equals("")))
//                {
//                    strAux+=" AND a4.co_grp= " + txtCodGrpCli.getText();
//                }
//                
//                //Condicion para filtro por Ruc del cliente
//                if (txtRucCli.getText().length()>0)
//                {
//                    strAux+=" AND a4.tx_ide= " + txtRucCli.getText();
//                }
//                
//                ////////Condicion para filtro por cliente en un rango especifico
//                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
//                    strAux+=" AND ((LOWER(a4.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a4.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
//                
//                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
//                {
//                    //Condicion para filtro por empresa
//                    if (txtCodEmp.getText().length()>0)
//                        strAux+=" AND a1.co_emp=" + txtCodEmp.getText();
//                }
//                else
//                {
//                    strAux+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
//                    
//                    //Condicion para filtro por cLocal
//                    if (txtCodLoc.getText().length()>0)
//                        strAux+=" AND a1.co_loc= " + txtCodLoc.getText();
//                    else
//                        strAux+=" AND a1.co_loc= " + objParSis.getCodigoLocal();
//                }
//                
//                ///FILTRO PARA MOSTRAR CREDITOS Y DEBITOS///
//                if ( !(chkMosCre.isSelected() && chkMosDeb.isSelected()) )
//                {
//                    System.out.println("ENTRO POR FILTRO DE CREDITOS Y DEBITOS ");
//                    if (chkMosCre.isSelected())
//                        strAux+=" AND a3.tx_natDoc='I'";
//                    else
//                        strAux+=" AND a3.tx_natDoc='E'";
//                }
//                
//                ///FILTRO PARA DOCUMENTOS VENCIDOS///
//                if (chkMosDocVen.isSelected())
//                {
//                    System.out.println("ENTRO POR FILTRO DE DOCUMENTOS VENCIDOS ");
//                    strFecVen=objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
//                    strAux+=" AND a2.fe_ven<='" + strFecVen + "'";
//                }
//                
//                ///FILTRO PARA MOSTRAR RETENCIONES///
//                if (!chkMosRet.isSelected())
//                {
//                    System.out.println("ENTRO POR FILTRO DE MOSTRAR RETENCIONES");
//                    strAux+=" AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
//                }
//                
//                //Obtener el número total de registros.
//                strSQL="";
//                strSQL+="SELECT COUNT (*)";
//                strSQL+=" FROM (";
//                        strSQL+=" SELECT Z1.co_emp, Z1.co_cli, Z1.tx_ide, Z1.tx_nom, Z1.VALDOC, Z1.VALABO, Z1.VALPEN, Z2.VALXVEN, Z3.VAL30D, Z4.VAL60D,Z5.VAL90D, Z6.VALMAS90 ";
//                        strSQL+=" FROM (";
//                                strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom, ";
//                                strSQL+=" SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO, SUM(a2.mo_pag+a2.nd_abo) AS VALPEN ";
//                                strSQL+=" FROM tbm_cabMovInv AS a1";
//                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
////                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";                                
//                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                                strSQL+=" AND a2.st_reg IN ('A','C')";
//                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                                strSQL+=" AND a3.ne_mod in(1,3)";
//                                strSQL+=strAux;
//                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";   ///, a2.fe_ven
//                                strSQL+=" ORDER BY a4.tx_nom ";
//                        strSQL+=" ) AS Z1";
//                        strSQL+=" LEFT OUTER JOIN (";
//                                strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN ";
//                                strSQL+=" FROM tbm_cabMovInv AS a1";
//                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
////                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";                                
//                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                                strSQL+=" AND a2.st_reg IN ('A','C')";
//                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                                strSQL+=" AND a3.ne_mod in(1,3)";
//                                strSQL+=" AND (current_date - a2.fe_ven) <= 0";
//                                strSQL+=strAux;
//                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
//                        strSQL+=" ) AS Z2 ON (Z1.co_emp=Z2.co_emp AND Z1.tx_ide = Z2.tx_ide)";
//                        strSQL+=" LEFT OUTER JOIN (";
//                                strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL30D ";
//                                strSQL+=" FROM tbm_cabMovInv AS a1";
//                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
////                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";                                
//                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                                strSQL+=" AND a2.st_reg IN ('A','C')";
//                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                                strSQL+=" AND a3.ne_mod in(1,3)";
//                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                                strSQL+=" AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30)";
//                                strSQL+=strAux;
//                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
//                       strSQL+=" ) AS Z3 ON (Z1.co_emp=Z3.co_emp AND Z1.tx_ide = Z3.tx_ide)";
//                       strSQL+=" LEFT OUTER JOIN (";
//                                strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL60D ";
//                                strSQL+=" FROM tbm_cabMovInv AS a1";
//                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
////                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";                                
//                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                                strSQL+=" AND a2.st_reg IN ('A','C')";
//                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                                strSQL+=" AND a3.ne_mod in(1,3)";
//                                strSQL+=" AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60)";
//                                strSQL+=strAux;
//                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
//                       strSQL+=" ) AS Z4 ON (Z1.co_emp=Z4.co_emp AND Z1.tx_ide = Z4.tx_ide)";
//                       strSQL+=" LEFT OUTER JOIN (";
//                                strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL90D ";
//                                strSQL+=" FROM tbm_cabMovInv AS a1";
//                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
////                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";                                
//                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                                strSQL+=" AND a2.st_reg IN ('A','C')";
//                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                                strSQL+=" AND a3.ne_mod in(1,3)";
//                                strSQL+=" AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90)";
//                                strSQL+=strAux;
//                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
//                       strSQL+=" ) AS Z5 ON (Z1.co_emp=Z5.co_emp AND Z1.tx_ide = Z5.tx_ide)";
//                       strSQL+=" LEFT OUTER JOIN (";
//                                strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
//                                strSQL+=" FROM tbm_cabMovInv AS a1";
//                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
////                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";                                
//                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                                strSQL+=" AND a2.st_reg IN ('A','C')";
//                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                                strSQL+=" AND a3.ne_mod in(1,3)";
//                                strSQL+=" AND (current_date - a2.fe_ven) >=91";
//                                strSQL+=strAux;
//                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
//                       strSQL+=" ) AS Z6 ON (Z1.co_emp=Z5.co_emp AND Z1.tx_ide = Z6.tx_ide)";                    
//                strSQL+=" ) AS B1";
//                ///System.out.println("---Query COUNT --cargarDatFac()--: "+ strSQL);
//                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
//                if (intNumTotReg==-1)
//                    return false;
//                
///*
//-----super query-----
//SELECT Z1.co_emp, Z1.co_cli, Z1.tx_nom, Z1.VALDOC, Z1.VALABO, Z1.VALPEN, Z2.VALXVEN, Z3.VAL30D, Z4.VAL60D,Z5.VAL90D, Z6.VALMAS90
//FROM
//(
//---Query de registros Consultados --cargarDatFac()--:
//SELECT a1.co_emp, a1.co_cli, a4.tx_nom, SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO,
//SUM(a2.mo_pag+a2.nd_abo) AS VALPEN
//FROM tbm_cabMovInv AS a1
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
//INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)
//INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3)
//---AND a1.co_cli= 3
//AND a1.co_emp=1
//GROUP BY a1.co_emp, a1.co_cli, a4.tx_nom
//ORDER BY a4.tx_nom
//) AS Z1
//LEFT OUTER JOIN
//(
//--------para la columna de valor por vencer--------
//SELECT a1.co_emp, a1.co_cli, a3.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN
//FROM tbm_cabMovInv AS a1
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
//AND a1.co_doc=a2.co_doc)
//INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0
//---AND a1.co_cli= 3
//AND a1.co_emp=1
//---AND a2.fe_ven > current_date
//AND (current_date - a2.fe_ven) <= 0
//GROUP BY a1.co_emp, a1.co_cli, a3.tx_nom
//) AS Z2 ON (Z1.tx_ide = Z2.tx_ide)
//LEFT OUTER JOIN
//(
//-----para columna de valor vencido entre 0 - 30 dias----------
//SELECT a1.co_emp, a1.co_cli, a3.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL30D
//FROM tbm_cabMovInv AS a1
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
//AND a1.co_doc=a2.co_doc)
//INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) 
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0
//---AND a1.co_cli= 3 
//AND a1.co_emp=1
//AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30)
//GROUP BY a1.co_emp, a1.co_cli, a3.tx_nom
//) AS Z3 ON (Z1.tx_ide = Z3.tx_ide)
//LEFT OUTER JOIN
//(
//-----para columna de valor vencido entre 31 - 60 dias----------
//SELECT a1.co_emp, a1.co_cli, a3.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL60D
//FROM tbm_cabMovInv AS a1 
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
//AND a1.co_doc=a2.co_doc)
//INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) 
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0
//---AND a1.co_cli= 3 
//AND a1.co_emp=1
//AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60)
//GROUP BY a1.co_emp, a1.co_cli, a3.tx_nom
//) AS Z4 ON (Z1.tx_ide = Z4.tx_ide)
//LEFT OUTER JOIN
//(
//-----para columna de valor vencido entre 61 - 90 dias----------
//SELECT a1.co_emp, a1.co_cli, a3.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL90D
//FROM tbm_cabMovInv AS a1
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
//AND a1.co_doc=a2.co_doc) 
//INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 
//---AND a1.co_cli= 3
//AND a1.co_emp=1
//AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90)
//GROUP BY a1.co_emp, a1.co_cli, a3.tx_nom 
//)AS Z5 ON (Z1.tx_ide = Z5.tx_ide)
//LEFT OUTER JOIN
//(
//-----para columna de valor vencido mayor a 91 dias----------
//SELECT a1.co_emp, a1.co_cli, a3.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90
//FROM tbm_cabMovInv AS a1 
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
//AND a1.co_doc=a2.co_doc) 
//INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0
//---AND a1.co_cli= 3 
//AND a1.co_emp=1
//AND (current_date - a2.fe_ven) >=91
//GROUP BY a1.co_emp, a1.co_cli, a3.tx_nom
//) AS Z6 ON(Z1.tx_ide = Z6.tx_ide)                 
//*/                
//                
//                //Armar la sentencia SQL.
//                strSQL="";
//                strSQL+=" SELECT Z1.co_emp, Z1.co_cli, Z1.tx_ide, Z1.tx_nom, Z1.VALDOC, Z1.VALABO, Z1.VALPEN, Z2.VALXVEN, Z3.VAL30D, Z4.VAL60D,Z5.VAL90D, Z6.VALMAS90 ";
//                strSQL+=" FROM (";
//                        strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom, ";
//                        strSQL+=" SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO, SUM(a2.mo_pag+a2.nd_abo) AS VALPEN ";
//                        strSQL+=" FROM tbm_cabMovInv AS a1";
//                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
////                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";                        
//                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                        strSQL+=" AND a2.st_reg IN ('A','C')";
//                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                        strSQL+=" AND a3.ne_mod in(1,3)";
//                        strSQL+=strAux;
//                        strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";   ///, a2.fe_ven
//                        strSQL+=" ORDER BY a4.tx_nom ";
//                strSQL+=" ) AS Z1";
//                strSQL+=" LEFT OUTER JOIN (";
//                        strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN ";
//                        strSQL+=" FROM tbm_cabMovInv AS a1";
//                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
////                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";                        
//                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                        strSQL+=" AND a2.st_reg IN ('A','C')";
//                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                        strSQL+=" AND a3.ne_mod in(1,3)";
//                        strSQL+=" AND (current_date - a2.fe_ven) <= 0";
//                        strSQL+=strAux;
//                        strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
//                strSQL+=" ) AS Z2 ON (Z1.co_emp=Z2.co_emp AND Z1.tx_ide = Z2.tx_ide)";
//                strSQL+=" LEFT OUTER JOIN (";
//                        strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL30D ";
//                        strSQL+=" FROM tbm_cabMovInv AS a1";
//                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
////                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";                        
//                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                        strSQL+=" AND a2.st_reg IN ('A','C')";
//                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                        strSQL+=" AND a3.ne_mod in(1,3)";
//                        strSQL+=" AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30)";
//                        strSQL+=strAux;
//                        strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
//                        strSQL+=" ORDER BY a4.tx_nom ";
//               strSQL+=" ) AS Z3 ON (Z1.co_emp=Z3.co_emp AND Z1.tx_ide = Z3.tx_ide)";
//               strSQL+=" LEFT OUTER JOIN (";
//                        strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL60D ";
//                        strSQL+=" FROM tbm_cabMovInv AS a1";
//                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
////                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";                        
//                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                        strSQL+=" AND a2.st_reg IN ('A','C')";
//                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                        strSQL+=" AND a3.ne_mod in(1,3)";
//                        strSQL+=" AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60)";
//                        strSQL+=strAux;
//                        strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
//                        strSQL+=" ORDER BY a4.tx_nom ";
//               strSQL+=" ) AS Z4 ON (Z1.co_emp=Z4.co_emp AND Z1.tx_ide = Z4.tx_ide)";
//               strSQL+=" LEFT OUTER JOIN (";
//                        strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL90D ";
//                        strSQL+=" FROM tbm_cabMovInv AS a1";
//                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
////                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";                        
//                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                        strSQL+=" AND a2.st_reg IN ('A','C')";
//                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                        strSQL+=" AND a3.ne_mod in(1,3)";
//                        strSQL+=" AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90)";
//                        strSQL+=strAux;
//                        strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
//                        strSQL+=" ORDER BY a4.tx_nom ";
//               strSQL+=" ) AS Z5 ON (Z1.co_emp=Z5.co_emp AND Z1.tx_ide = Z5.tx_ide)";
//               strSQL+=" LEFT OUTER JOIN (";
//                        strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
//                        strSQL+=" FROM tbm_cabMovInv AS a1";
//                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
////                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";                        
//                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                        strSQL+=" AND a2.st_reg IN ('A','C')";
//                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                        strSQL+=" AND a3.ne_mod in(1,3)";
//                        strSQL+=" AND (current_date - a2.fe_ven) >=91";
//                        strSQL+=strAux;
//                        strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_ide, a4.tx_nom ";
//                        strSQL+=" ORDER BY a4.tx_nom ";
//               strSQL+=" ) AS Z6 ON (Z1.co_emp=Z6.co_emp AND Z1.tx_ide = Z6.tx_ide)";
//               strSQL+=" ORDER BY Z1.tx_nom ";
//                
//                System.out.println("---Query de registros Consultados --cargarDatFac()--Mnu=321--CxC16: "+ strSQL);
//                
//                rstFac=stmFac.executeQuery(strSQL);
//                
//                //Limpiar vector de datos.
//                vecDatFac.clear();
//                //Obtener los registros.
//                lblMsgSis.setText("Cargando datos...");
//                pgrSis.setMinimum(0);
//                pgrSis.setMaximum(intNumTotReg);
//                pgrSis.setValue(0);
//                i=0;                
//                
//                while (rstFac.next())
//                {
//                    if (blnCon)
//                    {
//                        vecReg=new Vector();
//                        vecReg.add(INT_TBL_DAT_FAC_LIN,"");///0
//                        vecReg.add(INT_TBL_DAT_FAC_COD_EMP,rstFac.getString("co_emp"));///1
//                        vecReg.add(INT_TBL_DAT_FAC_COD_CLI,rstFac.getString("co_cli"));///2
//                        vecReg.add(INT_TBL_DAT_FAC_RUC_CED,rstFac.getString("tx_ide"));///3
//                        vecReg.add(INT_TBL_DAT_FAC_NOM_CLI,rstFac.getString("tx_nom"));///4
//                        vecReg.add(INT_TBL_DAT_FAC_VAL_PEN,rstFac.getString("VALPEN"));///5                        
//                        vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,rstFac.getString("VALXVEN"));///6
//                        vecReg.add(INT_TBL_DAT_FAC_VAL_30D,rstFac.getString("VAL30D"));///7
//                        vecReg.add(INT_TBL_DAT_FAC_VAL_60D,rstFac.getString("VAL60D"));///8
//                        vecReg.add(INT_TBL_DAT_FAC_VAL_90D,rstFac.getString("VAL90D"));///9
//                        vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,rstFac.getString("VALMAS90"));///10
//                        ///datFecVen=rst.getDate("fe_ven");
////                        try
////                        {
////                            //almaceno la fecha del sistema en un arreglo de enteros
////                            intFecFin=dtpDat.getFecha(strFecSis);
//////                            System.out.println("La FechaSist es: "+ strFecSis);
////                            ///System.out.println("En numeros la FechaSist del Doc --intFecFin-- es: "+ intFecFin);
////
////                            //tomo la fecha de la base y la almaceno en un arreglo como entero
////                            strFecIni=objUti.formatearFecha(datFecVen,"dd/MM/yyyy");
////                            intFecIni=dtpDat.getFecha(strFecIni);//(strFecIni);
//////                            System.out.println("La FechaVenc del Doc es: "+ strFecIni);
////                            ///System.out.println("En numeros la FechaVenc del Doc --intFecIni-- es: "+ intFecIni);
////
////                            //// la funcion intCalDiaTra( año inicialFecVenc, mes inicialFecVenc, dia inicialFecVenc, 
////                            //// año final FecActualSis, mes final FecActualSis, dia final FecActualSis)
////                            ////calcula los dias transcurridos entre 2 fechas
////                            intNumDia=intCalDiaTra(intFecIni[2], intFecIni[1], intFecIni[0], intFecFin[2], intFecFin[1], intFecFin[0]);
//////                            System.out.println("---> Los Dias de Diferencias entre FechaVen " + strFecIni + "y FechaSist " + strFecSis + " es ---> "+ intNumDia);
////                            //ubico el valor vencido en el rango correspondiente
////                            if(intNumDia<=0){
////                                vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,rst.getString("VALPEN"));
////                                vecReg.add(INT_TBL_DAT_FAC_VAL_30D,null);
////                                vecReg.add(INT_TBL_DAT_FAC_VAL_60D,null);
////                                vecReg.add(INT_TBL_DAT_FAC_VAL_90D,null);
////                                vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,null);
////                            }else
////                            {
////                                if(intNumDia>=0 && intNumDia<=30){
////                                //if(intdiaCre>=0 && intdiaCre<=30){
////                                    vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,null);
////                                    vecReg.add(INT_TBL_DAT_FAC_VAL_30D,rst.getString("VALPEN"));
////                                    vecReg.add(INT_TBL_DAT_FAC_VAL_60D,null);
////                                    vecReg.add(INT_TBL_DAT_FAC_VAL_90D,null);
////                                    vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,null);
////                                }else
////                                {
////                                    if(intNumDia>31 && intNumDia<=60){
////                                    //if(intdiaCre>=31 && intdiaCre<=60){
////                                        vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,null);
////                                        vecReg.add(INT_TBL_DAT_FAC_VAL_30D,null);
////                                        vecReg.add(INT_TBL_DAT_FAC_VAL_60D,rst.getString("VALPEN"));
////                                        vecReg.add(INT_TBL_DAT_FAC_VAL_90D,null);
////                                        vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,null);
////                                    }else
////                                    {
////                                        if(intNumDia>61 && intNumDia<=90){
////                                        ///if(intdiaCre>=61 && intdiaCre<=90){
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,null);
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_30D,null);
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_60D,null);
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_90D,rst.getString("VALPEN"));
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,null);
////                                        }else{
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,null);
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_30D,null);
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_60D,null);
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_90D,null);
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,rst.getString("VALPEN"));
////                                        }
////                                    }
////                                }
////                            }
////                        }
////                        catch (Exception e)
////                        {
////                            objUti.mostrarMsgErr_F1(this, e);
////                        }
//                
////                        vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,null);///5
////                        vecReg.add(INT_TBL_DAT_FAC_VAL_30D,null);///6
////                        vecReg.add(INT_TBL_DAT_FAC_VAL_60D,null);///7
////                        vecReg.add(INT_TBL_DAT_FAC_VAL_90D,null);///8
////                        vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,null);///9
//
////                        vecReg.add(INT_TBL_DAT_COD_BAN,rst.getString("co_banChq"));
////                        vecReg.add(INT_TBL_DAT_NOM_BAN,rst.getString("a5_tx_desLar"));
////                        vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_numCtaChq"));
////                        vecReg.add(INT_TBL_DAT_NUM_CHQ,rst.getString("tx_numChq"));
////                        vecReg.add(INT_TBL_DAT_FEC_REC_CHQ,rst.getString("fe_recChq"));
////                        vecReg.add(INT_TBL_DAT_FEC_VEN_CHQ,rst.getString("fe_venChq"));
////                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
////                        vecReg.add(INT_TBL_DAT_VAL_CHQ,rst.getString("nd_monChq"));
//                        
//                        vecDatFac.add(vecReg);
//                        i++;
//                        ///pgrSis.setValue(i);
//                    }
//                    else
//                    {
//                        break;
//                    }
//                }
//                rstFac.close();
//                stmFac.close();
//                conFac.close();
//                rstFac=null;
//                stmFac=null;
//                conFac=null;
//                //Asignar vectores al modelo.
//                objTblModFac.setData(vecDatFac);
//                tblDat.setModel(objTblModFac);
//                vecDatFac.clear();
//                
//                //Calcular totales.
//                objTblTot.calcularTotales();
//                
//                if (intNumTotReg==tblDat.getRowCount())
//                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
//                else
//                    ///lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDatFacMovReg.getRowCount() + ".");
//                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
//                
//                pgrSis.setValue(0);
//                butCon.setText("Consultar");
//                pgrSis.setIndeterminate(false);
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
//        return blnRes;
//    }
    
//    /**
//     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
//     * @return true: Si se pudo consultar los registros.
//     * <BR>false: En el caso contrario.
//     */
//    private boolean cargarDatFacCorFec()
//    {
//        int intCodEmp, intCodLoc, intNumTotReg, i;
//        
//        int intNumDia; 
//        String strFecSis, strFecIni, strFecSel, strFecVen, strFecDoc;
//        int intFecIni[];
//        int intFecFin[];//para calcular los dias entre fechas
//        int intFecSel[];//fecha seleccionada por el usuario//
//
//        
//        double dblSub, dblIva;
//        java.util.Date datFecSer, datFecVen, datFecAux;
//        
//        boolean blnRes=true;
//        try
//        {
//            butCon.setText("Detener");
//            lblMsgSis.setText("Obteniendo datos...");
//            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
//            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
//            conFac=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            if (conFac!=null)
//            {
//                stmFac=conFac.createStatement();
//                //Obtener la fecha del servidor.
//                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//                if (datFecSer==null)
//                    return false;
//                datFecAux=datFecSer;
//                
//                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
//
//                //Obtener la condición.
//                strAux="";                
//
//                //Condicion para filtro por cLocal
//                if (txtCodLoc.getText().length()>0)
//                {
//                    strAux+=" AND a1.co_loc= " + txtCodLoc.getText();                    
//                }
//                
//                //Condicion para filtro por cliente
//                if (txtCodCli.getText().length()>0)
//                {
//                    strAux+=" AND a1.co_cli= " + txtCodCli.getText();
//                }
//                
//                ////////Condicion para filtro por cliente en un rango especifico
//                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
//                    strAux+=" AND ((LOWER(a4.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a4.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
//                
//                if (!(txtDesCorTipDoc.getText().equals("")))
//                {
//                    strAux+=" AND a1.co_tipdoc= " + txtCodTipDoc.getText();
//                }
//
//                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
//                {
//                    //Condicion para filtro por empresa
//                    if (txtCodEmp.getText().length()>0)
//                        strAux+=" AND a1.co_emp=" + txtCodEmp.getText();
//                }
//                else
//                {
//                    strAux+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
//                }
//                
//                ///FILTRO PARA MOSTRAR CREDITOS Y DEBITOS///
//                if ( !(chkMosCre.isSelected() && chkMosDeb.isSelected()) )
//                {
//                    System.out.println("ENTRO POR FILTRO DE CREDITOS Y DEBITOS ");
//                    if (chkMosCre.isSelected())
//                        strAux+=" AND a3.tx_natDoc='I'";
//                    else
//                        strAux+=" AND a3.tx_natDoc='E'";
//                }
//                ///FILTRO PARA DOCUMENTOS VENCIDOS///
//                if (chkMosDocVen.isSelected())
//                {
//                    System.out.println("ENTRO POR FILTRO DE DOCUMENTOS VENCIDOS ");
//                    strFecVen=objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
//                    strAux+=" AND a2.fe_ven<='" + strFecVen + "'";
//                }
//                ///FILTRO PARA MOSTRAR RETENCIONES///
//                if (!chkMosRet.isSelected())
//                {
//                    System.out.println("ENTRO POR FILTRO DE MOSTRAR RETENCIONES");
//                    strAux+=" AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
//                }
//                
//                ///strFecDoc=objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy");
//                strFecDoc=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "yyyy/MM/dd");
//                String strFecDocAux = objUti.formatearFecha(strFecDoc,"yyyy/MM/dd","dd/MM/yyyy");
//                System.out.println("---La Fecha Corte es: "+ strFecDoc);
//                System.out.println("---La Fecha Corte FORMATEADA es: "+ strFecDocAux);
//                
//                //Obtener el número total de registros.
//                strSQL="";
//                strSQL+="SELECT COUNT (*)";
//                strSQL+=" FROM (";
//                        strSQL+=" SELECT Z1.co_emp, Z1.co_cli, Z1.tx_nom, Z1.VALDOC, Z1.VALABO, Z1.VALPEN, Z2.VALXVEN, Z3.VAL30D, Z4.VAL60D,Z5.VAL90D, Z6.VALMAS90 ";
//                        strSQL+=" FROM (";
//                                strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_nom, ";
//                                strSQL+=" SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO, SUM(a2.mo_pag+a2.nd_abo) AS VALPEN ";
//                                strSQL+=" FROM tbm_cabMovInv AS a1";
//                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
//                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                                strSQL+=" AND a2.st_reg IN ('A','C')";
//                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                                strSQL+=" AND a3.ne_mod in(1,3)";
//                                strSQL+=" AND a1.fe_doc <= '" +strFecDoc+ "'";
//                                strSQL+=strAux;
//                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_nom ";   ///, a2.fe_ven
//                                strSQL+=" ORDER BY a4.tx_nom ";
//                        strSQL+=" ) AS Z1";
//                        strSQL+=" LEFT OUTER JOIN (";
//                                strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN ";
//                                strSQL+=" FROM tbm_cabMovInv AS a1";
//                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
//                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                                strSQL+=" AND a2.st_reg IN ('A','C')";
//                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                                strSQL+=" AND a3.ne_mod in(1,3)";
//                                strSQL+=" AND a1.fe_doc <= '" +strFecDoc+ "'";
//                                strSQL+=" AND (current_date - a2.fe_ven) <= 0";
//                                strSQL+=strAux;
//                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_nom ";
//                        strSQL+=" ) AS Z2 ON (Z1.co_cli = Z2.co_cli)";
//                        strSQL+=" LEFT OUTER JOIN (";
//                                strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL30D ";
//                                strSQL+=" FROM tbm_cabMovInv AS a1";
//                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
//                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                                strSQL+=" AND a2.st_reg IN ('A','C')";
//                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                                strSQL+=" AND a3.ne_mod in(1,3)";
//                                strSQL+=" AND a1.fe_doc <= '" +strFecDoc+ "'";
//                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                                strSQL+=" AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30)";
//                                strSQL+=strAux;
//                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_nom ";
//                       strSQL+=" ) AS Z3 ON (Z1.co_cli = Z3.co_cli)";
//                       strSQL+=" LEFT OUTER JOIN (";
//                                strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL60D ";
//                                strSQL+=" FROM tbm_cabMovInv AS a1";
//                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
//                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                                strSQL+=" AND a2.st_reg IN ('A','C')";
//                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                                strSQL+=" AND a3.ne_mod in(1,3)";
//                                strSQL+=" AND a1.fe_doc <= '" +strFecDoc+ "'";
//                                strSQL+=" AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60)";
//                                strSQL+=strAux;
//                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_nom ";
//                       strSQL+=" ) AS Z4 ON (Z1.co_cli = Z4.co_cli)";
//                       strSQL+=" LEFT OUTER JOIN (";
//                                strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL90D ";
//                                strSQL+=" FROM tbm_cabMovInv AS a1";
//                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
//                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                                strSQL+=" AND a2.st_reg IN ('A','C')";
//                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                                strSQL+=" AND a3.ne_mod in(1,3)";
//                                strSQL+=" AND a1.fe_doc <= '" +strFecDoc+ "'";
//                                strSQL+=" AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90)";
//                                strSQL+=strAux;
//                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_nom ";
//                       strSQL+=" ) AS Z5 ON (Z1.co_cli = Z5.co_cli)";
//                       strSQL+=" LEFT OUTER JOIN (";
//                                strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
//                                strSQL+=" FROM tbm_cabMovInv AS a1";
//                                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
//                                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                                strSQL+=" AND a2.st_reg IN ('A','C')";
//                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                                strSQL+=" AND a3.ne_mod in(1,3)";
//                                strSQL+=" AND a1.fe_doc <= '" +strFecDoc+ "'";
//                                strSQL+=" AND (current_date - a2.fe_ven) >=91";
//                                strSQL+=strAux;
//                                strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_nom ";
//                       strSQL+=" ) AS Z6 ON (Z1.co_cli = Z6.co_cli)";                    
//                strSQL+=" ) AS B1";
//                ////System.out.println("---Query COUNT --cargarDatFacCorFec()--: "+ strSQL);
//                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
//                if (intNumTotReg==-1)
//                    return false;
//                
///*
//-----super query-----
//SELECT Z1.co_emp, Z1.co_cli, Z1.tx_nom, Z1.VALDOC, Z1.VALABO, Z1.VALPEN, Z2.VALXVEN, Z3.VAL30D, Z4.VAL60D,Z5.VAL90D, Z6.VALMAS90
//FROM
//(
//---Query de registros Consultados --cargarDatFac()--:
//SELECT a1.co_emp, a1.co_cli, a4.tx_nom, SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO,
//SUM(a2.mo_pag+a2.nd_abo) AS VALPEN
//FROM tbm_cabMovInv AS a1
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
//INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)
//INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3)
//---AND a1.co_cli= 3
//AND a1.co_emp=1
//GROUP BY a1.co_emp, a1.co_cli, a4.tx_nom
//ORDER BY a4.tx_nom
//) AS Z1
//LEFT OUTER JOIN
//(
//--------para la columna de valor por vencer--------
//SELECT a1.co_emp, a1.co_cli, a3.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN
//FROM tbm_cabMovInv AS a1
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
//AND a1.co_doc=a2.co_doc)
//INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0
//---AND a1.co_cli= 3
//AND a1.co_emp=1
//---AND a2.fe_ven > current_date
//AND (current_date - a2.fe_ven) <= 0
//GROUP BY a1.co_emp, a1.co_cli, a3.tx_nom
//) AS Z2 ON (Z1.co_cli = Z2.co_cli)
//LEFT OUTER JOIN
//(
//-----para columna de valor vencido entre 0 - 30 dias----------
//SELECT a1.co_emp, a1.co_cli, a3.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL30D
//FROM tbm_cabMovInv AS a1
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
//AND a1.co_doc=a2.co_doc)
//INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) 
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0
//---AND a1.co_cli= 3 
//AND a1.co_emp=1
//AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30)
//GROUP BY a1.co_emp, a1.co_cli, a3.tx_nom
//) AS Z3 ON (Z1.co_cli = Z3.co_cli)
//LEFT OUTER JOIN
//(
//-----para columna de valor vencido entre 31 - 60 dias----------
//SELECT a1.co_emp, a1.co_cli, a3.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL60D
//FROM tbm_cabMovInv AS a1 
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
//AND a1.co_doc=a2.co_doc)
//INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) 
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0
//---AND a1.co_cli= 3 
//AND a1.co_emp=1
//AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60)
//GROUP BY a1.co_emp, a1.co_cli, a3.tx_nom
//) AS Z4 ON (Z1.co_cli = Z4.co_cli)
//LEFT OUTER JOIN
//(
//-----para columna de valor vencido entre 61 - 90 dias----------
//SELECT a1.co_emp, a1.co_cli, a3.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL90D
//FROM tbm_cabMovInv AS a1
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
//AND a1.co_doc=a2.co_doc) 
//INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 
//---AND a1.co_cli= 3
//AND a1.co_emp=1
//AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90)
//GROUP BY a1.co_emp, a1.co_cli, a3.tx_nom 
//)AS Z5 ON (Z1.co_cli = Z5.co_cli)
//LEFT OUTER JOIN
//(
//-----para columna de valor vencido mayor a 91 dias----------
//SELECT a1.co_emp, a1.co_cli, a3.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90
//FROM tbm_cabMovInv AS a1 
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
//AND a1.co_doc=a2.co_doc) 
//INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0
//---AND a1.co_cli= 3 
//AND a1.co_emp=1
//AND (current_date - a2.fe_ven) >=91
//GROUP BY a1.co_emp, a1.co_cli, a3.tx_nom
//) AS Z6 ON(Z1.co_cli = Z6.co_cli)                 
//*/                
//                
//                //Armar la sentencia SQL.
//                strSQL="";
//                strSQL+=" SELECT Z1.co_emp, Z1.co_cli, Z1.tx_nom, Z1.VALDOC, Z1.VALABO, Z1.VALPEN, Z2.VALXVEN, Z3.VAL30D, Z4.VAL60D,Z5.VAL90D, Z6.VALMAS90 ";
//                strSQL+=" FROM (";
//                        strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_nom, ";
//                        strSQL+=" SUM(a2.mo_pag) AS VALDOC, SUM(a2.nd_abo) AS VALABO, SUM(a2.mo_pag+a2.nd_abo) AS VALPEN ";
//                        strSQL+=" FROM tbm_cabMovInv AS a1";
//                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
//                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                        strSQL+=" AND a2.st_reg IN ('A','C')";
//                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                        strSQL+=" AND a3.ne_mod in(1,3)";
//                        strSQL+=" AND a1.fe_doc <= '" +strFecDoc+ "'";
//                        strSQL+=strAux;
//                        strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_nom ";   ///, a2.fe_ven
//                        strSQL+=" ORDER BY a4.tx_nom ";
//                strSQL+=" ) AS Z1";
//                strSQL+=" LEFT OUTER JOIN (";
//                        strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VALXVEN ";
//                        strSQL+=" FROM tbm_cabMovInv AS a1";
//                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
//                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                        strSQL+=" AND a2.st_reg IN ('A','C')";
//                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                        strSQL+=" AND a3.ne_mod in(1,3)";
//                        strSQL+=" AND a1.fe_doc <= '" +strFecDoc+ "'";
//                        strSQL+=" AND (current_date - a2.fe_ven) <= 0";
//                        strSQL+=strAux;
//                        strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_nom ";
//                strSQL+=" ) AS Z2 ON (Z1.co_cli = Z2.co_cli)";
//                strSQL+=" LEFT OUTER JOIN (";
//                        strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL30D ";
//                        strSQL+=" FROM tbm_cabMovInv AS a1";
//                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
//                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                        strSQL+=" AND a2.st_reg IN ('A','C')";
//                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                        strSQL+=" AND a3.ne_mod in(1,3)";
//                        strSQL+=" AND a1.fe_doc <= '" +strFecDoc+ "'";
//                        strSQL+=" AND ((current_date - a2.fe_ven) >=1 and (current_date - a2.fe_ven) <=30)";
//                        strSQL+=strAux;
//                        strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_nom ";
//               strSQL+=" ) AS Z3 ON (Z1.co_cli = Z3.co_cli)";
//               strSQL+=" LEFT OUTER JOIN (";
//                        strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL60D ";
//                        strSQL+=" FROM tbm_cabMovInv AS a1";
//                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
//                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                        strSQL+=" AND a2.st_reg IN ('A','C')";
//                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                        strSQL+=" AND a3.ne_mod in(1,3)";
//                        strSQL+=" AND a1.fe_doc <= '" +strFecDoc+ "'";
//                        strSQL+=" AND ((current_date - a2.fe_ven) >=31 and (current_date - a2.fe_ven) <=60)";
//                        strSQL+=strAux;
//                        strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_nom ";
//               strSQL+=" ) AS Z4 ON (Z1.co_cli = Z4.co_cli)";
//               strSQL+=" LEFT OUTER JOIN (";
//                        strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VAL90D ";
//                        strSQL+=" FROM tbm_cabMovInv AS a1";
//                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
//                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                        strSQL+=" AND a2.st_reg IN ('A','C')";
//                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                        strSQL+=" AND a3.ne_mod in(1,3)";
//                        strSQL+=" AND a1.fe_doc <= '" +strFecDoc+ "'";
//                        strSQL+=" AND ((current_date - a2.fe_ven) >=61 and (current_date - a2.fe_ven) <=90)";
//                        strSQL+=strAux;
//                        strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_nom ";
//               strSQL+=" ) AS Z5 ON (Z1.co_cli = Z5.co_cli)";
//               strSQL+=" LEFT OUTER JOIN (";
//                        strSQL+=" SELECT a1.co_emp, a1.co_cli, a4.tx_nom, sum(a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
//                        strSQL+=" FROM tbm_cabMovInv AS a1";
//                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
//                        strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                        strSQL+=" AND a2.st_reg IN ('A','C')";
//                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                        strSQL+=" AND a3.ne_mod in(1,3)";
//                        strSQL+=" AND a1.fe_doc <= '" +strFecDoc+ "'";
//                        strSQL+=" AND (current_date - a2.fe_ven) >=91";
//                        strSQL+=strAux;
//                        strSQL+=" GROUP BY a1.co_emp, a1.co_cli, a4.tx_nom ";
//               strSQL+=" ) AS Z6 ON (Z1.co_cli = Z6.co_cli)";   
//                
//                System.out.println("---Query de registros Consultados --cargarDatFacCorFec()--: "+ strSQL);
//                
//                rstFac=stmFac.executeQuery(strSQL);
//                
//                //Limpiar vector de datos.
//                vecDatFac.clear();
//                //Obtener los registros.
//                lblMsgSis.setText("Cargando datos...");
//                pgrSis.setMinimum(0);
//                pgrSis.setMaximum(intNumTotReg);
//                pgrSis.setValue(0);
//                i=0;                
//                
//                while (rstFac.next())
//                {
//                    if (blnCon)
//                    {
//                        vecReg=new Vector();
//                        vecReg.add(INT_TBL_DAT_FAC_LIN,"");///0
//                        vecReg.add(INT_TBL_DAT_FAC_COD_EMP,rstFac.getString("co_emp"));///1
//                        vecReg.add(INT_TBL_DAT_FAC_COD_CLI,rstFac.getString("co_cli"));///2
//                        vecReg.add(INT_TBL_DAT_FAC_NOM_CLI,rstFac.getString("tx_nom"));///3
//                        vecReg.add(INT_TBL_DAT_FAC_VAL_PEN,rstFac.getString("VALPEN"));///4                        
//                        vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,rstFac.getString("VALXVEN"));///5
//                        vecReg.add(INT_TBL_DAT_FAC_VAL_30D,rstFac.getString("VAL30D"));///6
//                        vecReg.add(INT_TBL_DAT_FAC_VAL_60D,rstFac.getString("VAL60D"));///7
//                        vecReg.add(INT_TBL_DAT_FAC_VAL_90D,rstFac.getString("VAL90D"));///8
//                        vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,rstFac.getString("VALMAS90"));///9
//                        ///datFecVen=rst.getDate("fe_ven");
////                        try
////                        {
////                            //almaceno la fecha del sistema en un arreglo de enteros
////                            intFecFin=dtpDat.getFecha(strFecSis);
//////                            System.out.println("La FechaSist es: "+ strFecSis);
////                            ///System.out.println("En numeros la FechaSist del Doc --intFecFin-- es: "+ intFecFin);
////
////                            //tomo la fecha de la base y la almaceno en un arreglo como entero
////                            strFecIni=objUti.formatearFecha(datFecVen,"dd/MM/yyyy");
////                            intFecIni=dtpDat.getFecha(strFecIni);//(strFecIni);
//////                            System.out.println("La FechaVenc del Doc es: "+ strFecIni);
////                            ///System.out.println("En numeros la FechaVenc del Doc --intFecIni-- es: "+ intFecIni);
////
////                            //// la funcion intCalDiaTra( año inicialFecVenc, mes inicialFecVenc, dia inicialFecVenc, 
////                            //// año final FecActualSis, mes final FecActualSis, dia final FecActualSis)
////                            ////calcula los dias transcurridos entre 2 fechas
////                            intNumDia=intCalDiaTra(intFecIni[2], intFecIni[1], intFecIni[0], intFecFin[2], intFecFin[1], intFecFin[0]);
//////                            System.out.println("---> Los Dias de Diferencias entre FechaVen " + strFecIni + "y FechaSist " + strFecSis + " es ---> "+ intNumDia);
////                            //ubico el valor vencido en el rango correspondiente
////                            if(intNumDia<=0){
////                                vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,rst.getString("VALPEN"));
////                                vecReg.add(INT_TBL_DAT_FAC_VAL_30D,null);
////                                vecReg.add(INT_TBL_DAT_FAC_VAL_60D,null);
////                                vecReg.add(INT_TBL_DAT_FAC_VAL_90D,null);
////                                vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,null);
////                            }else
////                            {
////                                if(intNumDia>=0 && intNumDia<=30){
////                                //if(intdiaCre>=0 && intdiaCre<=30){
////                                    vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,null);
////                                    vecReg.add(INT_TBL_DAT_FAC_VAL_30D,rst.getString("VALPEN"));
////                                    vecReg.add(INT_TBL_DAT_FAC_VAL_60D,null);
////                                    vecReg.add(INT_TBL_DAT_FAC_VAL_90D,null);
////                                    vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,null);
////                                }else
////                                {
////                                    if(intNumDia>31 && intNumDia<=60){
////                                    //if(intdiaCre>=31 && intdiaCre<=60){
////                                        vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,null);
////                                        vecReg.add(INT_TBL_DAT_FAC_VAL_30D,null);
////                                        vecReg.add(INT_TBL_DAT_FAC_VAL_60D,rst.getString("VALPEN"));
////                                        vecReg.add(INT_TBL_DAT_FAC_VAL_90D,null);
////                                        vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,null);
////                                    }else
////                                    {
////                                        if(intNumDia>61 && intNumDia<=90){
////                                        ///if(intdiaCre>=61 && intdiaCre<=90){
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,null);
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_30D,null);
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_60D,null);
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_90D,rst.getString("VALPEN"));
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,null);
////                                        }else{
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,null);
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_30D,null);
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_60D,null);
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_90D,null);
////                                            vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,rst.getString("VALPEN"));
////                                        }
////                                    }
////                                }
////                            }
////                        }
////                        catch (Exception e)
////                        {
////                            objUti.mostrarMsgErr_F1(this, e);
////                        }
//                
////                        vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,null);///5
////                        vecReg.add(INT_TBL_DAT_FAC_VAL_30D,null);///6
////                        vecReg.add(INT_TBL_DAT_FAC_VAL_60D,null);///7
////                        vecReg.add(INT_TBL_DAT_FAC_VAL_90D,null);///8
////                        vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,null);///9
//
////                        vecReg.add(INT_TBL_DAT_COD_BAN,rst.getString("co_banChq"));
////                        vecReg.add(INT_TBL_DAT_NOM_BAN,rst.getString("a5_tx_desLar"));
////                        vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_numCtaChq"));
////                        vecReg.add(INT_TBL_DAT_NUM_CHQ,rst.getString("tx_numChq"));
////                        vecReg.add(INT_TBL_DAT_FEC_REC_CHQ,rst.getString("fe_recChq"));
////                        vecReg.add(INT_TBL_DAT_FEC_VEN_CHQ,rst.getString("fe_venChq"));
////                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
////                        vecReg.add(INT_TBL_DAT_VAL_CHQ,rst.getString("nd_monChq"));
//                        
//                        vecDatFac.add(vecReg);
//                        i++;
//                        pgrSis.setValue(i);
//                    }
//                    else
//                    {
//                        break;
//                    }
//                }
//                rstFac.close();
//                stmFac.close();
//                conFac.close();
//                rstFac=null;
//                stmFac=null;
//                conFac=null;
//                //Asignar vectores al modelo.
//                objTblModFac.setData(vecDatFac);
//                tblDat.setModel(objTblModFac);
//                vecDatFac.clear();
//                
//                //Calcular totales.
//                objTblTot.calcularTotales();
//                
//                if (intNumTotReg==tblDat.getRowCount())
//                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
//                else
//                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDatFacMovReg.getRowCount() + ".");
//                pgrSis.setValue(0);
//                butCon.setText("Consultar");
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
//        return blnRes;
//    }
    
    
    private String FilSql() 
    {
        String strFilSql = "";
        String strFecSis, strFecIni, strFecsisFor, strFecVen;
        java.util.Date datFecSer, datFecVen, datFecAux;
        boolean blnRes=true;        
        int intCodEmp, intCodEmpGrp;
                
        datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
        intCodEmpGrp=objParSis.getCodigoEmpresaGrupo();
        
        if (datFecSer==null)
            blnRes=false;
        
        datFecAux=datFecSer;
                
        strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
        strFecsisFor=objUti.formatearFecha(datFecAux, "yyyy/MM/dd");
        //Obtener la condición.
       
        //Condicion para filtro por cliente
        if (txtCodCli.getText().length()>0)
        {
            strFilSql+=" AND x3.co_cli= " + txtCodCli.getText();
        }
        
        //Condicion para filtro por nombres desde y hasta//
        if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
            strFilSql+=" AND ((LOWER(x3.tx_nomcli) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(x3.tx_nomcli) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";

        if (!(txtCodGrpCli.getText().equals("")))
        {
            strFilSql+=" AND x3.co_grp= " + txtCodGrpCli.getText();
        }

        if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
        {
            //Condicion para filtro por empresa
            if (txtCodEmp.getText().length()>0)
                strFilSql+=" AND x3.co_emp=" + txtCodEmp.getText();
        }
        else
        {
            strFilSql+=" AND x3.co_emp=" + objParSis.getCodigoEmpresa();
            
            //Condicion para filtro por cLocal
            if (txtCodLoc.getText().length()>0)
                strFilSql+=" AND x3.co_loc= " + txtCodLoc.getText();
            else
                strFilSql+=" AND x3.co_loc= " + objParSis.getCodigoEmpresa();
        }
        ///FILTRO PARA MOSTRAR CREDITOS Y DEBITOS///
        if ( !(chkMosCre.isSelected() && chkMosDeb.isSelected()) )
        {
            System.out.println("ENTRO POR FILTRO DE CREDITOS Y DEBITOS ");
            if (chkMosCre.isSelected())
                strFilSql+=" AND x4.tx_natDoc='I'";
            else
                strFilSql+=" AND x4.tx_natDoc='E'";
        }
        ///FILTRO PARA DOCUMENTOS VENCIDOS///
        if (chkMosDocVen.isSelected())
        {
            System.out.println("ENTRO POR FILTRO DE DOCUMENTOS VENCIDOS ");
            strFecVen=objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
            strFilSql+=" AND x4.fe_ven<='" + strFecVen + "'";
        }
        ///FILTRO PARA MOSTRAR RETENCIONES///
        if (!chkMosRet.isSelected())
        {
            System.out.println("ENTRO POR FILTRO DE MOSTRAR RETENCIONES");
            strFilSql+=" AND (x4.nd_porRet IS NULL OR x4.nd_porRet=0)";
        }
        
        System.out.println("---FilSql()---strFilSql: " + strFilSql);
        
        return strFilSql;
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    /*
    private boolean cargarDatFacCorFec()
    {
        int intCodEmp, intCodLoc, intNumTotReg, i;
        
        int intNumDia; 
        String strFecSis, strFecIni, strFecSel, strFecVen, strFecDoc;
        int intFecIni[];
        int intFecFin[];//para calcular los dias entre fechas
        int intFecSel[];//fecha seleccionada por el usuario//

        
        double dblSub, dblIva;
        java.util.Date datFecSer, datFecVen, datFecAux;
        
        boolean blnRes=true;
        try
        {
            ///butCon.setText("Detener");
            ///lblMsgSis.setText("Obteniendo datos...");
            
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            conFac=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conFac!=null)
            {
                stmFac=conFac.createStatement();
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                datFecAux=datFecSer;
                
                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");

                //Obtener la condición.
                strAux="";                

                //Condicion para filtro por cLocal
                if (txtCodLoc.getText().length()>0)
                {
                    strAux+=" AND a1.co_loc= " + txtCodLoc.getText();                    
                }
                
                //Condicion para filtro por cliente
                if (txtCodCli.getText().length()>0)
                {
                    strAux+=" AND a1.co_cli= " + txtCodCli.getText();
                }
                
                ////////Condicion para filtro por cliente en un rango especifico
                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a4.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a4.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                
                if (!(txtDesCorTipDoc.getText().equals("")))
                {
                    strAux+=" AND a1.co_tipdoc= " + txtCodTipDoc.getText();
                }

                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Condicion para filtro por empresa
                    if (txtCodEmp.getText().length()>0)
                        strAux+=" AND a1.co_emp=" + txtCodEmp.getText();
                }
                else
                {
                    strAux+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                }
                
                ///FILTRO PARA MOSTRAR CREDITOS Y DEBITOS///
                if ( !(chkMosCre.isSelected() && chkMosDeb.isSelected()) )
                {
                    System.out.println("ENTRO POR FILTRO DE CREDITOS Y DEBITOS ");
                    if (chkMosCre.isSelected())
                        strAux+=" AND a3.tx_natDoc='I'";
                    else
                        strAux+=" AND a3.tx_natDoc='E'";
                }
                ///FILTRO PARA DOCUMENTOS VENCIDOS///
                if (chkMosDocVen.isSelected())
                {
                    System.out.println("ENTRO POR FILTRO DE DOCUMENTOS VENCIDOS ");
                    strFecVen=objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
                    strAux+=" AND a2.fe_ven<='" + strFecVen + "'";
                }
                ///FILTRO PARA MOSTRAR RETENCIONES///
                if (!chkMosRet.isSelected())
                {
                    System.out.println("ENTRO POR FILTRO DE MOSTRAR RETENCIONES");
                    strAux+=" AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
                }
                
                ///strFecDoc=objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy");
                strFecDoc=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "yyyy/MM/dd");
                String strFecDocAux = objUti.formatearFecha(strFecDoc,"yyyy/MM/dd","dd/MM/yyyy");
                System.out.println("---La Fecha Corte es: "+ strFecDoc);
                System.out.println("---La Fecha Corte FORMATEADA es: "+ strFecDocAux);
                
                //Obtener el número total de registros.
                strSQL="";
                strSQL+="SELECT COUNT (*)";
                strSQL+=" FROM (";
                    strSQL+=" SELECT Z1.co_emp, Z1.co_cli, Z1.tx_ide, Z1.tx_nom, Z1.SUMVALDOC, Z1.SUMVALABO, Z1.SUMVALPEN, Z2.SUMVALXVEN, Z3.SUMVAL30D, Z4.SUMVAL60D,Z5.SUMVAL90D, Z6.SUMVALMAS90 ";
                    strSQL+=" FROM (";
                        strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALDOC) AS SUMVALDOC, sum(VALABO) AS SUMVALABO, sum(VALPEN) AS SUMVALPEN ";
                        strSQL+=" FROM ( ";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALPEN ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  INNER JOIN ";
                            strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                            strSQL+="    from tbm_detpag as x1";
                            strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                            strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                            strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                            strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                            strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VALPEN ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VALPEN";
                            strSQL+="  FROM";
                                    strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                    strSQL+=" FROM tbm_detpag as x1";
                                    strSQL+=" INNER JOIN (  ";
                                            strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                            strSQL+=" from tbm_cabpag as a1";
                                            strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                            strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                    strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                    strSQL+=" INNER JOIN tbm_cli AS x7 ON (x3.co_emp=x7.co_emp and x3.co_cli=x7.co_cli)";
                                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                    //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                    //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                    strSQL+=FilSql();
                                    strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli,  x2.fe_doc, x4.mo_pag ";
                                    strSQL+=") as p ";
                            strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag";
                            strSQL+=")";
                        strSQL+=" ) AS Q ";
                        strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";                    
                    strSQL+=" ) AS Z1";
                    strSQL+=" LEFT OUTER JOIN (";
                        strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALXVEN) AS SUMVALXVEN ";
                        strSQL+=" FROM ( ";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALXVEN ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  INNER JOIN ";
                            strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                            strSQL+="    from tbm_detpag as x1";
                            strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                            strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                            strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                            strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                            strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) <= 0 ";   ///---valxven---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VALXVEN ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) <= 0 ";   ///---valxven---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VALXVEN";
                            strSQL+="  FROM";
                                    strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                    strSQL+=" FROM tbm_detpag as x1";
                                    strSQL+=" INNER JOIN (  ";
                                            strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                            strSQL+=" from tbm_cabpag as a1";
                                            strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                            strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                    strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                    strSQL+=" INNER JOIN tbm_cli AS x7 ON (x3.co_emp=x7.co_emp and x3.co_cli=x7.co_cli)";
                                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                    //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                    //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                    strSQL+=FilSql();
                                    strSQL+=" AND ('"+strFecDoc+"' - x4.fe_ven) <= 0 ";   ///---valxven---
                                    strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli,  x2.fe_doc, x4.mo_pag ";
                                    strSQL+=") as p ";
                            strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag";
                            strSQL+=")";
                        strSQL+=" ) AS Q ";
                        strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";                    
                    strSQL+=" ) AS Z2 ON (Z1.tx_ide = Z2.tx_ide)";
                    strSQL+=" LEFT OUTER JOIN (";
                        strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VAL30D) AS SUMVAL30D ";
                        strSQL+=" FROM ( ";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VAL30D ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  INNER JOIN ";
                            strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                            strSQL+="    from tbm_detpag as x1";
                            strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                            strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                            strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                            strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                            strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 1 AND ('"+strFecDoc+"' - a2.fe_ven) <=30 )";   ///---val30d---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VAL30D ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 1 AND ('"+strFecDoc+"' - a2.fe_ven) <=30 )";   ///---val30d---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VAL30D";
                            strSQL+="  FROM";
                                    strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                    strSQL+=" FROM tbm_detpag as x1";
                                    strSQL+=" INNER JOIN (  ";
                                            strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                            strSQL+=" from tbm_cabpag as a1";
                                            strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                            strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                    strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                    strSQL+=" INNER JOIN tbm_var AS x7 ON (x3.co_emp=x7.co_emp and x3.co_cli=x7.co_cli)";
                                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                    //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                    //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                    strSQL+=FilSql();
                                    strSQL+=" AND (('"+strFecDoc+"' - x4.fe_ven) >= 1 AND ('"+strFecDoc+"' - x4.fe_ven) <=30 )";   ///---val30d---
                                    strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli,  x2.fe_doc, x4.mo_pag ";
                                    strSQL+=") as p ";
                            strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag";
                            strSQL+=")";
                        strSQL+=" ) AS Q ";
                        strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";                    
                   strSQL+=" ) AS Z3 ON (Z1.tx_ide = Z3.tx_ide)";
                   strSQL+=" LEFT OUTER JOIN (";
                        strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VAL60D) AS SUMVAL60D ";
                        strSQL+=" FROM ( ";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VAL60D ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  INNER JOIN ";
                            strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                            strSQL+="    from tbm_detpag as x1";
                            strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                            strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                            strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                            strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                            strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 31 AND ('"+strFecDoc+"' - a2.fe_ven) <=60 )";   ///---val60d---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VAL60D ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 31 AND ('"+strFecDoc+"' - a2.fe_ven) <= 60 )";   ///---val60d---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VAL60D";
                            strSQL+="  FROM";
                                    strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                    strSQL+=" FROM tbm_detpag as x1";
                                    strSQL+=" INNER JOIN (  ";
                                            strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                            strSQL+=" from tbm_cabpag as a1";
                                            strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                            strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                    strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                    strSQL+=" INNER JOIN tbm_cli AS x7 ON (x3.co_emp=x7.co_emp and x3.co_cli=x7.co_cli)";
                                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                    //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                    //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                    strSQL+=FilSql();
                                    strSQL+=" AND (('"+strFecDoc+"' - x4.fe_ven) >= 31 AND ('"+strFecDoc+"' - x4.fe_ven) <= 60 )";   ///---val60d---
                                    strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli,  x2.fe_doc, x4.mo_pag ";
                                    strSQL+=") as p ";
                            strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag";
                            strSQL+=")";
                        strSQL+=" ) AS Q ";
                        strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
                   strSQL+=" ) AS Z4 ON (Z1.tx_ide = Z4.tx_ide)";
                   strSQL+=" LEFT OUTER JOIN (";
                        strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VAL90D) AS SUMVAL90D ";
                        strSQL+=" FROM ( ";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VAL90D ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  INNER JOIN ";
                            strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                            strSQL+="    from tbm_detpag as x1";
                            strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                            strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                            strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                            strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                            strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 61 AND ('"+strFecDoc+"' - a2.fe_ven) <=90 )";   ///---val90d---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VAL90D ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 61 AND ('"+strFecDoc+"' - a2.fe_ven) <=90 )";   ///---val90d---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VAL90D";
                            strSQL+="  FROM";
                                    strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                    strSQL+=" FROM tbm_detpag as x1";
                                    strSQL+=" INNER JOIN (  ";
                                            strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                            strSQL+=" from tbm_cabpag as a1";
                                            strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                            strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                    strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                    strSQL+=" INNER JOIN tbm_cli AS x7 ON (x3.co_emp=x7.co_emp and x3.co_cli=x7.co_cli)";
                                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                    //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                    //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                    strSQL+=FilSql();
                                    strSQL+=" AND (('"+strFecDoc+"' - x4.fe_ven) >= 61 AND ('"+strFecDoc+"' - x4.fe_ven) <= 90 )";   ///---val90d---
                                    strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli,  x2.fe_doc, x4.mo_pag ";
                                    strSQL+=") as p ";
                            strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag";
                            strSQL+=")";
                        strSQL+=" ) AS Q ";
                        strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";               
                   strSQL+=" ) AS Z5 ON (Z1.tx_ide = Z5.tx_ide)";               
                   strSQL+=" LEFT OUTER JOIN (";
                        strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALMAS90) AS SUMVALMAS90 ";
                        strSQL+=" FROM ( ";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALMAS90 ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  INNER JOIN ";
                            strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                            strSQL+="    from tbm_detpag as x1";
                            strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                            strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                            strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                            strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                            strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) >=91 ";   ///---valmas90d---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) >=91 ";   ///---valmas90d---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VALMAS90";
                            strSQL+="  FROM";
                                    strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                    strSQL+=" FROM tbm_detpag as x1";
                                    strSQL+=" INNER JOIN (  ";
                                            strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                            strSQL+=" from tbm_cabpag as a1";
                                            strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                            strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                    strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                    strSQL+=" INNER JOIN tbm_cli AS x7 ON (x3.co_emp=x7.co_emp and x3.co_cli=x7.co_cli)";
                                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                    //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                    //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                    strSQL+=FilSql();
                                    strSQL+=" AND ('"+strFecDoc+"' - x4.fe_ven) >= 91 ";   ///---valmas90d---
                                    strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli,  x2.fe_doc, x4.mo_pag ";
                                    strSQL+=") as p ";
                            strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag";
                            strSQL+=")";
                        strSQL+=" ) AS Q ";
                        strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
                   strSQL+=" ) AS Z6 ON (Z1.tx_ide = Z6.tx_ide)";
                strSQL+=" ) AS B1";
                System.out.println("---Query COUNT --cargarDatFacCorFec()--Mnu=645--CxC16: "+ strSQL);
                
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;                

                
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT Z1.co_emp, Z1.co_cli, Z1.tx_ide, Z1.tx_nom, Z1.SUMVALDOC, Z1.SUMVALABO, Z1.SUMVALPEN, Z2.SUMVALXVEN, Z3.SUMVAL30D, Z4.SUMVAL60D, Z5.SUMVAL90D, Z6.SUMVALMAS90 ";
                strSQL+=" FROM (";
                    strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALDOC) AS SUMVALDOC, sum(VALABO) AS SUMVALABO, sum(VALPEN) AS SUMVALPEN ";
                    strSQL+=" FROM ( ";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALPEN ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  INNER JOIN ";
                        strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                        strSQL+="    from tbm_detpag as x1";
                        strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                        strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                        strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                        strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                        strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VALPEN ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VALPEN";
                        strSQL+="  FROM";
                                strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                strSQL+=" FROM tbm_detpag as x1";
                                strSQL+=" INNER JOIN (  ";
                                        strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                        strSQL+=" from tbm_cabpag as a1";
                                        strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                        strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                strSQL+=" INNER JOIN tbm_cli AS x7 ON (x3.co_emp=x7.co_emp and x3.co_cli=x7.co_cli)";
                                strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                strSQL+=FilSql();
                                strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli,  x2.fe_doc, x4.mo_pag ";
                                strSQL+=") as p ";
                        strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag";
                        strSQL+=")";
                    strSQL+=" ) AS Q ";
                    strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";                    
                strSQL+=" ) AS Z1";
                strSQL+=" LEFT OUTER JOIN (";
                    strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALXVEN) AS SUMVALXVEN ";
                    strSQL+=" FROM ( ";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALXVEN ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  INNER JOIN ";
                        strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                        strSQL+="    from tbm_detpag as x1";
                        strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                        strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                        strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                        strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                        strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) <= 0 ";   ///---valxven---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VALXVEN ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) <= 0 ";   ///---valxven---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VALXVEN";
                        strSQL+="  FROM";
                                strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                strSQL+=" FROM tbm_detpag as x1";
                                strSQL+=" INNER JOIN (  ";
                                        strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                        strSQL+=" from tbm_cabpag as a1";
                                        strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                        strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                strSQL+=" INER JOIN tbm_cli AS x7 ON (x3.co_emp=x7.co_emp and x3.co_cli=x7.co_cli)";
                                strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                strSQL+=FilSql();
                                strSQL+=" AND ('"+strFecDoc+"' - x4.fe_ven) <= 0 ";   ///---valxven---
                                strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli,  x2.fe_doc, x4.mo_pag ";
                                strSQL+=") as p ";
                        strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag";
                        strSQL+=")";
                    strSQL+=" ) AS Q ";
                    strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";                    
                strSQL+=" ) AS Z2 ON (Z1.tx_ide = Z2.tx_ide)";
                strSQL+=" LEFT OUTER JOIN (";
                    strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VAL30D) AS SUMVAL30D ";
                    strSQL+=" FROM ( ";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VAL30D ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  INNER JOIN ";
                        strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                        strSQL+="    from tbm_detpag as x1";
                        strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                        strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                        strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                        strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                        strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 1 AND ('"+strFecDoc+"' - a2.fe_ven) <=30 )";   ///---val30d---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VAL30D ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 1 AND ('"+strFecDoc+"' - a2.fe_ven) <=30 )";   ///---val30d---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VAL30D";
                        strSQL+="  FROM";
                                strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                strSQL+=" FROM tbm_detpag as x1";
                                strSQL+=" INNER JOIN (  ";
                                        strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                        strSQL+=" from tbm_cabpag as a1";
                                        strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                        strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                strSQL+=" INNER JOIN tbm_cli AS x7 ON (x3.co_emp=x7.co_emp and x3.co_cli=x7.co_cli)";
                                strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                strSQL+=FilSql();
                                strSQL+=" AND (('"+strFecDoc+"' - x4.fe_ven) >= 1 AND ('"+strFecDoc+"' - x4.fe_ven) <=30 )";   ///---val30d---
                                strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli,  x2.fe_doc, x4.mo_pag ";
                                strSQL+=") as p ";
                        strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag";
                        strSQL+=")";
                    strSQL+=" ) AS Q ";
                    strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";                    
               strSQL+=" ) AS Z3 ON (Z1.tx_ide = Z3.tx_ide)";
               strSQL+=" LEFT OUTER JOIN (";
                    strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VAL60D) AS SUMVAL60D ";
                    strSQL+=" FROM ( ";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VAL60D ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  INNER JOIN ";
                        strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                        strSQL+="    from tbm_detpag as x1";
                        strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                        strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                        strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                        strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                        strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 31 AND ('"+strFecDoc+"' - a2.fe_ven) <=60 )";   ///---val60d---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VAL60D ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 31 AND ('"+strFecDoc+"' - a2.fe_ven) <=60 )";   ///---val60d---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VAL60D";
                        strSQL+="  FROM";
                                strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                strSQL+=" FROM tbm_detpag as x1";
                                strSQL+=" INNER JOIN (  ";
                                        strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                        strSQL+=" from tbm_cabpag as a1";
                                        strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                        strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                strSQL+=" INNER JOIN tbm_var AS x7 ON (x3.co_emp=x7.co_emp and x3.co_cli=x7.co_cli)";
                                strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                strSQL+=FilSql();
                                strSQL+=" AND (('"+strFecDoc+"' - x4.fe_ven) >= 31 AND ('"+strFecDoc+"' - x4.fe_ven) <= 60 )";   ///---val60d---
                                strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli,  x2.fe_doc, x4.mo_pag ";
                                strSQL+=") as p ";
                        strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag";
                        strSQL+=")";
                    strSQL+=" ) AS Q ";
                    strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
               strSQL+=" ) AS Z4 ON (Z1.tx_ide = Z4.tx_ide)";
               strSQL+=" LEFT OUTER JOIN (";
                    strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VAL90D) AS SUMVAL90D ";
                    strSQL+=" FROM ( ";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VAL90D ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  INNER JOIN ";
                        strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                        strSQL+="    from tbm_detpag as x1";
                        strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                        strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                        strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                        strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                        strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 61 AND ('"+strFecDoc+"' - a2.fe_ven) <=90 )";   ///---val90d---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VAL90D ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 61 AND ('"+strFecDoc+"' - a2.fe_ven) <=90 )";   ///---val90d---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VAL90D";
                        strSQL+="  FROM";
                                strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                strSQL+=" FROM tbm_detpag as x1";
                                strSQL+=" INNER JOIN (  ";
                                        strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                        strSQL+=" from tbm_cabpag as a1";
                                        strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                        strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                strSQL+=" INNER JOIN tbm_cli AS x7 ON (x3.co_emp=x7.co_emp and x3.co_cli=x7.co_cli)";
                                strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                strSQL+=FilSql();
                                strSQL+=" AND (('"+strFecDoc+"' - x4.fe_ven) >= 61 AND ('"+strFecDoc+"' - x4.fe_ven) <= 90 )";   ///---val90d---
                                strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli,  x2.fe_doc, x4.mo_pag ";
                                strSQL+=") as p ";
                        strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag";
                        strSQL+=")";
                    strSQL+=" ) AS Q ";
                    strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";               
               strSQL+=" ) AS Z5 ON (Z1.tx_ide = Z5.tx_ide)";               
               strSQL+=" LEFT OUTER JOIN (";
                    strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALMAS90) AS SUMVALMAS90 ";
                    strSQL+=" FROM ( ";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALMAS90 ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  INNER JOIN ";
                        strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                        strSQL+="    from tbm_detpag as x1";
                        strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                        strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                        strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                        strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                        strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) >=91 ";   ///---valmas90d---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) >=91 ";   ///---valmas90d---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VALMAS90";
                        strSQL+="  FROM";
                                strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                strSQL+=" FROM tbm_detpag as x1";
                                strSQL+=" INNER JOIN (  ";
                                        strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                        strSQL+=" from tbm_cabpag as a1";
                                        strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                        strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                strSQL+=" INNER JOIN tbm_cli AS x7 ON (x3.co_emp=x7.co_emp and x3.co_cli=x7.co_cli)";
                                strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                strSQL+=FilSql();
                                strSQL+=" AND ('"+strFecDoc+"' - x4.fe_ven) >= 91 ";   ///---valmas90d---
                                strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x7.tx_ide, x3.tx_nomcli,  x2.fe_doc, x4.mo_pag ";
                                strSQL+=") as p ";
                        strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nomcli, p.mo_pag";
                        strSQL+=")";
                    strSQL+=" ) AS Q ";
                    strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
               strSQL+=" ) AS Z6 ON (Z1.tx_ide = Z6.tx_ide) ";
               strSQL+=" ORDER BY Z1.tx_nom ";
               System.out.println("---cargarDatFacCorFec() Mnu=645--CxC16: "+ strSQL);
                
               rstFac=stmFac.executeQuery(strSQL);
                
               //Limpiar vector de datos.
               vecDatFac.clear();
               //Obtener los registros.
               lblMsgSis.setText("Cargando datos...");
               
//               pgrSis.setMinimum(0);
//               pgrSis.setMaximum(intNumTotReg);
//               pgrSis.setValue(0);
               
               i=0;                
                
               while (rstFac.next())
               {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_FAC_LIN,"");///0
                        vecReg.add(INT_TBL_DAT_FAC_COD_EMP,rstFac.getString("co_emp"));///1
                        vecReg.add(INT_TBL_DAT_FAC_COD_CLI,rstFac.getString("co_cli"));///2
                        vecReg.add(INT_TBL_DAT_FAC_RUC_CED,rstFac.getString("tx_ide"));///3
                        vecReg.add(INT_TBL_DAT_FAC_NOM_CLI,rstFac.getString("tx_nom"));///3
                        vecReg.add(INT_TBL_DAT_FAC_VAL_PEN,rstFac.getString("SUMVALPEN"));///4                        
                        vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,rstFac.getString("SUMVALXVEN"));///5
                        vecReg.add(INT_TBL_DAT_FAC_VAL_30D,rstFac.getString("SUMVAL30D"));///6
                        vecReg.add(INT_TBL_DAT_FAC_VAL_60D,rstFac.getString("SUMVAL60D"));///7
                        vecReg.add(INT_TBL_DAT_FAC_VAL_90D,rstFac.getString("SUMVAL90D"));///8
                        vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,rstFac.getString("SUMVALMAS90"));///9
                        
                        vecDatFac.add(vecReg);
                        i++;
                        pgrSis.setValue(i);
                    }
                    else
                    {
                        break;
                    }
                }
                rstFac.close();
                stmFac.close();
                conFac.close();
                rstFac=null;
                stmFac=null;
                conFac=null;
                //Asignar vectores al modelo.
                objTblModFac.setData(vecDatFac);
                tblDat.setModel(objTblModFac);
                vecDatFac.clear();
                
                //Calcular totales.
                objTblTot.calcularTotales();
                
                if (intNumTotReg==tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDatFacMovReg.getRowCount() + ".");
                
                ///pgrSis.setValue(0);                
                ///butCon.setText("Consultar");
                
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
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
 */
    
    private boolean cargarDatFacCorFec()
    {
        int intCodEmp, intCodLoc, intNumTotReg, i;
        
        int intNumDia; 
        String strFecSis, strFecIni, strFecSel, strFecVen, strFecDoc;
        int intFecIni[];
        int intFecFin[];//para calcular los dias entre fechas
        int intFecSel[];//fecha seleccionada por el usuario//

        
        double dblSub, dblIva;
        java.util.Date datFecSer, datFecVen, datFecAux;
        
        boolean blnRes=true;
        try
        {
            ///butCon.setText("Detener");
            ///lblMsgSis.setText("Obteniendo datos...");
            
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            conFac=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conFac!=null)
            {
                stmFac=conFac.createStatement();
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                datFecAux=datFecSer;
                
                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");

                //Obtener la condición.
                strAux="";
                
                //Condicion para filtro por cliente
                if (txtCodCli.getText().length()>0)
                {
                    strAux+=" AND a1.co_cli= " + txtCodCli.getText();
                }
                
                //Condicion para filtro por Ruc del cliente
                if (txtRucCli.getText().length()>0)
                {
                    strAux+=" AND a4.tx_ide= " + txtRucCli.getText();
                }
                
                ////////Condicion para filtro por cliente en un rango especifico
                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a4.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a4.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                
                //Condicion para filtro por Codigo de Grupo del cliente
                if (!(txtCodGrpCli.getText().equals("")))
                {
                    strAux+=" AND a4.co_grp= " + txtCodGrpCli.getText();
                }

                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Condicion para filtro por empresa
                    if (txtCodEmp.getText().length()>0)
                        strAux+=" AND a1.co_emp=" + txtCodEmp.getText();
                }
                else
                {
                    strAux+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                    
                    //Condicion para filtro por cLocal
                    if (txtCodLoc.getText().length()>0)
                        strAux+=" AND a1.co_loc= " + txtCodLoc.getText();
                    else
                        strAux+=" AND a1.co_loc= " + objParSis.getCodigoLocal();
                }
                
                ///FILTRO PARA MOSTRAR CREDITOS Y DEBITOS///
                if ( !(chkMosCre.isSelected() && chkMosDeb.isSelected()) )
                {
                    System.out.println("ENTRO POR FILTRO DE CREDITOS Y DEBITOS ");
                    if (chkMosCre.isSelected())
                        strAux+=" AND a3.tx_natDoc='I'";
                    else
                        strAux+=" AND a3.tx_natDoc='E'";
                }
                ///FILTRO PARA DOCUMENTOS VENCIDOS///
                if (chkMosDocVen.isSelected())
                {
                    System.out.println("ENTRO POR FILTRO DE DOCUMENTOS VENCIDOS ");
                    strFecVen=objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
                    strAux+=" AND a2.fe_ven<='" + strFecVen + "'";
                }
                ///FILTRO PARA MOSTRAR RETENCIONES///
                if (!chkMosRet.isSelected())
                {
                    System.out.println("ENTRO POR FILTRO DE MOSTRAR RETENCIONES");
                    strAux+=" AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
                }
                
                ///strFecDoc=objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy");
                strFecDoc=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "yyyy/MM/dd");
                String strFecDocAux = objUti.formatearFecha(strFecDoc,"yyyy/MM/dd","dd/MM/yyyy");
                System.out.println("---La Fecha Corte es: "+ strFecDoc);
                System.out.println("---La Fecha Corte FORMATEADA es: "+ strFecDocAux);
                
                //Obtener el número total de registros.
                strSQL="";
                strSQL+="SELECT COUNT (*)";
                strSQL+=" FROM (";
                    strSQL+=" SELECT Z1.co_emp, Z1.co_cli, Z1.tx_ide, Z1.tx_nom, SUM(Z1.SUMVALDOC) AS VALDOC, SUM(Z1.SUMVALABO) AS VALABO, SUM(Z1.SUMVALPEN) AS VALPEN, SUM(Z2.SUMVALXVEN) AS VALXVEN, SUM(Z3.SUMVAL30D) AS VAL30D, SUM(Z4.SUMVAL60D) AS VAL60D, SUM(Z5.SUMVAL90D) AS VAL90D, SUM(Z6.SUMVALMAS90) AS VALMAS90D ";
                    strSQL+=" FROM (";
                        strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALDOC) AS SUMVALDOC, sum(VALABO) AS SUMVALABO, sum(VALPEN) AS SUMVALPEN ";
                        strSQL+=" FROM ( ";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALPEN ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                                strSQL+=" INNER JOIN tbm_grpcli AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_grp=a5.co_grp)";                                                    
                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  INNER JOIN ";
                            strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                            strSQL+="    from tbm_detpag as x1";
                            strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                            strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                            strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                            strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                            strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VALPEN ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VALPEN";
                            strSQL+="  FROM";
                                    strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                    strSQL+=" FROM tbm_detpag as x1";
                                    strSQL+=" INNER JOIN (  ";
                                            strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                            strSQL+=" from tbm_cabpag as a1";
                                            strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                            strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                    ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                    strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
                                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                    //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                    //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                    strSQL+=FilSql();
                                    strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
                                    strSQL+=") as p ";
                            strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
                            strSQL+=")";
                        strSQL+=" ) AS Q ";
                        strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";                    
                        strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
                    strSQL+=" ) AS Z1";
                    strSQL+=" LEFT OUTER JOIN (";
                        strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALXVEN) AS SUMVALXVEN ";
                        strSQL+=" FROM ( ";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALXVEN ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  INNER JOIN ";
                            strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                            strSQL+="    from tbm_detpag as x1";
                            strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                            strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                            strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                            strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                            strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) <= 0 ";   ///---valxven---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VALXVEN ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) <= 0 ";   ///---valxven---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VALXVEN";
                            strSQL+="  FROM";
                                    strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                    strSQL+=" FROM tbm_detpag as x1";
                                    strSQL+=" INNER JOIN (  ";
                                            strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                            strSQL+=" from tbm_cabpag as a1";
                                            strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                            strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                    ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                    strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
                                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                    //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                    //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                    strSQL+=FilSql();
                                    strSQL+=" AND ('"+strFecDoc+"' - x4.fe_ven) <= 0 ";   ///---valxven---
                                    strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
                                    strSQL+=") as p ";
                            strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
                            strSQL+=")";
                        strSQL+=" ) AS Q ";
                        strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
                        strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
                    strSQL+=" ) AS Z2 ON (Z1.co_emp=Z2.co_emp AND Z1.tx_ide = Z2.tx_ide)";
                    strSQL+=" LEFT OUTER JOIN (";
                        strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VAL30D) AS SUMVAL30D ";
                        strSQL+=" FROM ( ";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VAL30D ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  INNER JOIN ";
                            strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                            strSQL+="    from tbm_detpag as x1";
                            strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                            strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                            strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                            strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                            strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 1 AND ('"+strFecDoc+"' - a2.fe_ven) <=30 )";   ///---val30d---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VAL30D ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 1 AND ('"+strFecDoc+"' - a2.fe_ven) <=30 )";   ///---val30d---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VAL30D";
                            strSQL+="  FROM";
                                    strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                    strSQL+=" FROM tbm_detpag as x1";
                                    strSQL+=" INNER JOIN (  ";
                                            strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                            strSQL+=" from tbm_cabpag as a1";
                                            strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                            strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                    ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                    strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
                                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                    //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                    //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                    strSQL+=FilSql();
                                    strSQL+=" AND (('"+strFecDoc+"' - x4.fe_ven) >= 1 AND ('"+strFecDoc+"' - x4.fe_ven) <=30 )";   ///---val30d---
                                    strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
                                    strSQL+=") as p ";
                            strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
                            strSQL+=")";
                        strSQL+=" ) AS Q ";
                        strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
                        strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
                   strSQL+=" ) AS Z3 ON (Z1.co_emp=Z3.co_emp AND Z1.tx_ide = Z3.tx_ide)";
                   strSQL+=" LEFT OUTER JOIN (";
                        strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VAL60D) AS SUMVAL60D ";
                        strSQL+=" FROM ( ";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VAL60D ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  INNER JOIN ";
                            strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                            strSQL+="    from tbm_detpag as x1";
                            strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                            strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                            strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                            strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                            strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 31 AND ('"+strFecDoc+"' - a2.fe_ven) <=60 )";   ///---val60d---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VAL60D ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 31 AND ('"+strFecDoc+"' - a2.fe_ven) <= 60 )";   ///---val60d---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VAL60D";
                            strSQL+="  FROM";
                                    strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                    strSQL+=" FROM tbm_detpag as x1";
                                    strSQL+=" INNER JOIN (  ";
                                            strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                            strSQL+=" from tbm_cabpag as a1";
                                            strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                            strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                    ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                    strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
                                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                    //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                    //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                    strSQL+=FilSql();
                                    strSQL+=" AND (('"+strFecDoc+"' - x4.fe_ven) >= 31 AND ('"+strFecDoc+"' - x4.fe_ven) <= 60 )";   ///---val60d---
                                    strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
                                    strSQL+=") as p ";
                            strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
                            strSQL+=")";
                        strSQL+=" ) AS Q ";
                        strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
                        strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
                   strSQL+=" ) AS Z4 ON (Z1.co_emp=Z4.co_emp AND Z1.tx_ide = Z4.tx_ide)";
                   strSQL+=" LEFT OUTER JOIN (";
                        strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VAL90D) AS SUMVAL90D ";
                        strSQL+=" FROM ( ";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VAL90D ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  INNER JOIN ";
                            strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                            strSQL+="    from tbm_detpag as x1";
                            strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                            strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                            strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                            strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                            strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 61 AND ('"+strFecDoc+"' - a2.fe_ven) <=90 )";   ///---val90d---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VAL90D ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 61 AND ('"+strFecDoc+"' - a2.fe_ven) <=90 )";   ///---val90d---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VAL90D";
                            strSQL+="  FROM";
                                    strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                    strSQL+=" FROM tbm_detpag as x1";
                                    strSQL+=" INNER JOIN (  ";
                                            strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                            strSQL+=" from tbm_cabpag as a1";
                                            strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                            strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                    ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                    strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
                                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                    //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                    //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                    strSQL+=FilSql();
                                    strSQL+=" AND (('"+strFecDoc+"' - x4.fe_ven) >= 61 AND ('"+strFecDoc+"' - x4.fe_ven) <= 90 )";   ///---val90d---
                                    strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x2.fe_doc, x4.mo_pag ";
                                    strSQL+=") as p ";
                            strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
                            strSQL+=")";
                        strSQL+=" ) AS Q ";
                        strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
                        strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
                   strSQL+=" ) AS Z5 ON (Z1.co_emp=Z5.co_emp AND Z1.tx_ide = Z5.tx_ide)";               
                   strSQL+=" LEFT OUTER JOIN (";
                        strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALMAS90) AS SUMVALMAS90 ";
                        strSQL+=" FROM ( ";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALMAS90 ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  INNER JOIN ";
                            strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                            strSQL+="    from tbm_detpag as x1";
                            strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                            strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                            strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                            strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                            strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) >=91 ";   ///---valmas90d---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                            strSQL+=strAux;
                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                            strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) >=91 ";   ///---valmas90d---
                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                            strSQL+=")";
                            strSQL+="UNION ALL";
                            strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VALMAS90";
                            strSQL+="  FROM";
                                    strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                    strSQL+=" FROM tbm_detpag as x1";
                                    strSQL+=" INNER JOIN (  ";
                                            strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                            strSQL+=" from tbm_cabpag as a1";
                                            strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                            strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                    ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                    strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
                                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                    //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                    //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                    strSQL+=FilSql();
                                    strSQL+=" AND ('"+strFecDoc+"' - x4.fe_ven) >= 91 ";   ///---valmas90d---
                                    strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
                                    strSQL+=") as p ";
                            strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
                            strSQL+=")";
                        strSQL+=" ) AS Q ";
                        strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
                        strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
                   strSQL+=" ) AS Z6 ON (Z1.co_emp=Z6.co_emp AND Z1.tx_ide = Z6.tx_ide)";
                   strSQL+=" GROUP BY Z1.co_emp, Z1.co_cli, Z1.tx_ide, Z1.tx_nom";
                   strSQL+=" ORDER BY Z1.tx_nom, Z1.co_emp ";
                strSQL+=" ) AS B1";                
                System.out.println("---Query COUNT --cargarDatFacCorFec()--Mnu=645: "+ strSQL);
                
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;                
/*


 -----query prueba----
SELECT Z1.co_emp, Z1.co_cli, Z1.tx_ide, Z1.tx_nom, SUM(Z1.SUMVALDOC) AS VALDOC, SUM(Z1.SUMVALABO) AS VALABO, SUM(Z1.SUMVALPEN) AS VALPEN, SUM(Z2.SUMVALXVEN) AS VALXVEN , SUM(Z3.SUMVAL30D) AS VAL30D, SUM(Z4.SUMVAL60D) AS VAL60D, SUM(Z5.SUMVAL90D) AS VAL90D, SUM(Z6.SUMVALMAS90D) AS VALMAS90D
FROM 
( 
SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALDOC) AS SUMVALDOC, sum(VALABO) AS SUMVALABO, sum(VALPEN) AS SUMVALPEN  
FROM (
( 
---query donde la suma del abono en tbm_detpag es igual al abono en tbm_pagmovinv por cada documento---
SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, 
(a2.mo_pag+a6.sumabodet) as VALPEN   
FROM tbm_cabMovInv AS a1   
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
INNER JOIN   
( 
select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet     
from tbm_detpag as x1    
inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc 
and x1.co_doc=x2.co_doc)     where x2.st_reg NOT IN ('E','I')     AND x2.fe_doc <= '2006/12/31'    
group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  
) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag 
and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)  
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 
AND a3.ne_mod in (1,3)   AND a1.fe_doc <= '2006/12/31'  
ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
)
UNION ALL
( 
---query donde el abono en tbm_pagmovinv es igual acero (no tiene pago asociado a tbm_detpag)---
SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, 
(a2.mo_pag+a2.nd_abo) AS VALPEN   
FROM tbm_cabMovInv AS a1   
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) 
AND a2.nd_abo=0.00
AND a1.fe_doc <= '2006/12/31'  
ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
)
UNION ALL
( 
SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, 
(p.mo_pag+sum(p.valpagfecmay)) as VALPEN  
FROM
( 
SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, 
case when x2.fe_doc >='2006/12/31' then 0.00 end as valpagfecmay  
FROM tbm_detpag as x1 
INNER JOIN 
(   select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc  
from tbm_cabpag as a1 where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '2006/12/31' 
) as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc)  
INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc)  
INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg)  
INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc)  
---LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg) 
INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)
WHERE x3.fe_doc <= '2006/12/31' 
GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag 
) as p  
GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag
) 
) AS Q  
GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom  
ORDER BY Q.tx_nom, Q.co_emp
) AS Z1 

LEFT OUTER JOIN 

(
SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALDOC) AS SUMVALDOC, sum(VALABO) AS SUMVALABO, 
sum(VALXVEN) AS SUMVALXVEN  
FROM (
( 
---query donde la suma del abono en tbm_detpag es igual al abono en tbm_pagmovinv por cada documento---
SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, 
(a2.mo_pag+a6.sumabodet) as VALXVEN   
FROM tbm_cabMovInv AS a1   
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
INNER JOIN   
( 
select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet     
from tbm_detpag as x1    
inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc 
and x1.co_doc=x2.co_doc)     where x2.st_reg NOT IN ('E','I')     AND x2.fe_doc <= '2006/12/31'    
group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  
) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag 
and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)  
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 
AND a3.ne_mod in (1,3)   AND a1.fe_doc <= '2006/12/31'  AND ('2006/12/31' - a2.fe_ven) <= 0   
ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
)
UNION ALL
( 
---query donde el abono en tbm_pagmovinv es igual acero (no tiene pago asociado a tbm_detpag)---
SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, 
(a2.mo_pag+a2.nd_abo) AS VALPEN   
FROM tbm_cabMovInv AS a1   
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) 
AND a2.nd_abo=0.00 AND a1.fe_doc <= '2006/12/31'  AND ('2006/12/31' - a2.fe_ven) <= 0   
ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
)
UNION ALL
( 
SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, 
(p.mo_pag+sum(p.valpagfecmay)) as VALPEN  
FROM
( 
SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, 
case when x2.fe_doc >='2006/12/31' then 0.00 end as valpagfecmay  
FROM tbm_detpag as x1 
INNER JOIN 
(   select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc  
from tbm_cabpag as a1 where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '2006/12/31' 
) as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc)  
INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc)  
INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg)  
INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc)  
---LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg) 
INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)
WHERE x3.fe_doc <= '2006/12/31' AND ('2006/12/31' - x4.fe_ven) <= 0   
GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag 
) as p  
GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag
) 
) AS Q  
GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom  
ORDER BY Q.tx_nom, Q.co_emp
) AS Z2 ON (Z1.co_emp=Z2.co_emp AND Z1.tx_ide=Z2.tx_ide)

LEFT OUTER JOIN 

(
SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALDOC) AS SUMVALDOC, sum(VALABO) AS SUMVALABO, 
sum(VAL30D) AS SUMVAL30D
FROM (
( 
---query donde la suma del abono en tbm_detpag es igual al abono en tbm_pagmovinv por cada documento---
SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, 
(a2.mo_pag+a6.sumabodet) as VAL30D   
FROM tbm_cabMovInv AS a1   
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
INNER JOIN   
( 
select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet     
from tbm_detpag as x1    
inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc 
and x1.co_doc=x2.co_doc)     where x2.st_reg NOT IN ('E','I')     AND x2.fe_doc <= '2006/12/31'    
group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  
) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag 
and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)  
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 
AND a3.ne_mod in (1,3)   AND a1.fe_doc <= '2006/12/31'  ---AND ('2006/12/31' - a2.fe_ven) <= 0   
AND (('2006/12/31' - a2.fe_ven) >= 1 AND ('2006/12/31' - a2.fe_ven) <=30 )
ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
)
UNION ALL
( 
---query donde el abono en tbm_pagmovinv es igual acero (no tiene pago asociado a tbm_detpag)---
SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, 
(a2.mo_pag+a2.nd_abo) AS VAL30D   
FROM tbm_cabMovInv AS a1   
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) 
AND a2.nd_abo=0.00 AND a1.fe_doc <= '2006/12/31'  ---AND ('2006/12/31' - a2.fe_ven) <= 0  
AND (('2006/12/31' - a2.fe_ven) >= 1 AND ('2006/12/31' - a2.fe_ven) <=30 ) 
ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
)
UNION ALL
( 
SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, 
(p.mo_pag+sum(p.valpagfecmay)) as VAL30D  
FROM
( 
SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, 
case when x2.fe_doc >='2006/12/31' then 0.00 end as valpagfecmay  
FROM tbm_detpag as x1 
INNER JOIN 
(   select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc  
from tbm_cabpag as a1 where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '2006/12/31' 
) as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc)  
INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc)  
INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg)  
INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc)  
---LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg) 
INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)
WHERE x3.fe_doc <= '2006/12/31' ---AND ('2006/12/31' - x4.fe_ven) <= 0   
AND (('2006/12/31' - x4.fe_ven) >= 1 AND ('2006/12/31' - x4.fe_ven) <=30 )
GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag 
) as p  
GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag
) 
) AS Q  
GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom  
ORDER BY Q.tx_nom, Q.co_emp
) AS Z3 ON (Z1.co_emp=Z3.co_emp AND Z1.tx_ide=Z3.tx_ide)

LEFT OUTER JOIN 

(
SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALDOC) AS SUMVALDOC, sum(VALABO) AS SUMVALABO, 
sum(VAL60D) AS SUMVAL60D
FROM (
( 
---query donde la suma del abono en tbm_detpag es igual al abono en tbm_pagmovinv por cada documento---
SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, 
(a2.mo_pag+a6.sumabodet) as VAL60D   
FROM tbm_cabMovInv AS a1   
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
INNER JOIN   
( 
select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet     
from tbm_detpag as x1    
inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc 
and x1.co_doc=x2.co_doc)     where x2.st_reg NOT IN ('E','I')     AND x2.fe_doc <= '2006/12/31'    
group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  
) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag 
and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)  
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 
AND a3.ne_mod in (1,3)   AND a1.fe_doc <= '2006/12/31'  ---AND ('2006/12/31' - a2.fe_ven) <= 0   
AND (('2006/12/31' - a2.fe_ven) >= 31 AND ('2006/12/31' - a2.fe_ven) <=60 )
ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
)
UNION ALL
( 
---query donde el abono en tbm_pagmovinv es igual acero (no tiene pago asociado a tbm_detpag)---
SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, 
(a2.mo_pag+a2.nd_abo) AS VAL60D   
FROM tbm_cabMovInv AS a1   
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) 
AND a2.nd_abo=0.00 AND a1.fe_doc <= '2006/12/31'  ---AND ('2006/12/31' - a2.fe_ven) <= 0  
AND (('2006/12/31' - a2.fe_ven) >= 31 AND ('2006/12/31' - a2.fe_ven) <=60 ) 
ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
)
UNION ALL
( 
SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, 
(p.mo_pag+sum(p.valpagfecmay)) as VAL60D  
FROM
( 
SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, 
case when x2.fe_doc >='2006/12/31' then 0.00 end as valpagfecmay  
FROM tbm_detpag as x1 
INNER JOIN 
(   select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc  
from tbm_cabpag as a1 where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '2006/12/31' 
) as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc)  
INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc)  
INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg)  
INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc)  
---LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg) 
INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)
WHERE x3.fe_doc <= '2006/12/31' ---AND ('2006/12/31' - x4.fe_ven) <= 0   
AND (('2006/12/31' - x4.fe_ven) >= 31 AND ('2006/12/31' - x4.fe_ven) <=60 )
GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag 
) as p  
GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag
) 
) AS Q  
GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom  
ORDER BY Q.tx_nom, Q.co_emp
) AS Z4 ON (Z1.co_emp=Z4.co_emp AND Z1.tx_ide=Z4.tx_ide)

LEFT OUTER JOIN 

(
SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALDOC) AS SUMVALDOC, sum(VALABO) AS SUMVALABO, 
sum(VAL90D) AS SUMVAL90D
FROM (
( 
---query donde la suma del abono en tbm_detpag es igual al abono en tbm_pagmovinv por cada documento---
SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, 
(a2.mo_pag+a6.sumabodet) as VAL90D   
FROM tbm_cabMovInv AS a1   
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
INNER JOIN   
( 
select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet     
from tbm_detpag as x1    
inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc 
and x1.co_doc=x2.co_doc)     where x2.st_reg NOT IN ('E','I')     AND x2.fe_doc <= '2006/12/31'    
group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  
) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag 
and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)  
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 
AND a3.ne_mod in (1,3)   AND a1.fe_doc <= '2006/12/31'  ---AND ('2006/12/31' - a2.fe_ven) <= 0   
AND (('2006/12/31' - a2.fe_ven) >= 61 AND ('2006/12/31' - a2.fe_ven) <=90 )
ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
)
UNION ALL
( 
---query donde el abono en tbm_pagmovinv es igual acero (no tiene pago asociado a tbm_detpag)---
SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, 
(a2.mo_pag+a2.nd_abo) AS VAL90D   
FROM tbm_cabMovInv AS a1   
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) 
AND a2.nd_abo=0.00 AND a1.fe_doc <= '2006/12/31'  ---AND ('2006/12/31' - a2.fe_ven) <= 0  
AND (('2006/12/31' - a2.fe_ven) >= 61 AND ('2006/12/31' - a2.fe_ven) <=90 ) 
ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
)
UNION ALL
( 
SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, 
(p.mo_pag+sum(p.valpagfecmay)) as VAL90D  
FROM
( 
SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, 
case when x2.fe_doc >='2006/12/31' then 0.00 end as valpagfecmay  
FROM tbm_detpag as x1 
INNER JOIN 
(   select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc  
from tbm_cabpag as a1 where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '2006/12/31' 
) as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc)  
INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc)  
INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg)  
INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc)  
---LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg) 
INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)
WHERE x3.fe_doc <= '2006/12/31' ---AND ('2006/12/31' - x4.fe_ven) <= 0   
AND (('2006/12/31' - x4.fe_ven) >= 61 AND ('2006/12/31' - x4.fe_ven) <=90 )
GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag 
) as p  
GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag
) 
) AS Q  
GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom  
ORDER BY Q.tx_nom, Q.co_emp
) AS Z5 ON (Z1.co_emp=Z5.co_emp AND Z1.tx_ide=Z5.tx_ide)

LEFT OUTER JOIN

(
SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALDOC) AS SUMVALDOC, sum(VALABO) AS SUMVALABO, 
sum(VALMAS90D) AS SUMVALMAS90D
FROM (
( 
---query donde la suma del abono en tbm_detpag es igual al abono en tbm_pagmovinv por cada documento---
SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, 
(a2.mo_pag+a6.sumabodet) as VALMAS90D   
FROM tbm_cabMovInv AS a1   
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
INNER JOIN   
( 
select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet     
from tbm_detpag as x1    
inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc 
and x1.co_doc=x2.co_doc)     where x2.st_reg NOT IN ('E','I')     AND x2.fe_doc <= '2006/12/31'    
group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  
) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag 
and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)  
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 
AND a3.ne_mod in (1,3)   AND a1.fe_doc <= '2006/12/31'  
---AND ('2006/12/31' - a2.fe_ven) <= 0   
---AND (('2006/12/31' - a2.fe_ven) >= 61 AND ('2006/12/31' - a2.fe_ven) <=90 )
AND ('2006/12/31' - a2.fe_ven) >= 91
ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
)
UNION ALL
( 
---query donde el abono en tbm_pagmovinv es igual acero (no tiene pago asociado a tbm_detpag)---
SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, 
(a2.mo_pag+a2.nd_abo) AS VALMAS90D   
FROM tbm_cabMovInv AS a1   
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) 
AND a2.nd_abo=0.00 AND a1.fe_doc <= '2006/12/31'  
---AND ('2006/12/31' - a2.fe_ven) <= 0
---AND (('2006/12/31' - a2.fe_ven) >= 61 AND ('2006/12/31' - a2.fe_ven) <=90 ) 
AND ('2006/12/31' - a2.fe_ven) >= 91
ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
)
UNION ALL
( 
SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, 
(p.mo_pag+sum(p.valpagfecmay)) as VALMAS90D  
FROM
( 
SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, 
case when x2.fe_doc >='2006/12/31' then 0.00 end as valpagfecmay  
FROM tbm_detpag as x1 
INNER JOIN 
(   select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc  
from tbm_cabpag as a1 where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '2006/12/31' 
) as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc)  
INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc)  
INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg)  
INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc)  
---LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg) 
INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)
WHERE x3.fe_doc <= '2006/12/31' 
---AND ('2006/12/31' - x4.fe_ven) <= 0   
---AND (('2006/12/31' - x4.fe_ven) >= 61 AND ('2006/12/31' - x4.fe_ven) <=90 )
AND ('2006/12/31' - x4.fe_ven) >= 91
GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag 
) as p  
GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag
) 
) AS Q  
GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom  
ORDER BY Q.tx_nom, Q.co_emp
) AS Z6 ON (Z1.co_emp=Z6.co_emp AND Z1.tx_ide=Z6.tx_ide)

GROUP BY Z1.co_emp, Z1.co_cli, Z1.tx_ide, Z1.tx_nom
ORDER BY Z1.tx_nom, Z1.co_emp                
*/                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT Z1.co_emp, Z1.co_cli, Z1.tx_ide, Z1.tx_nom, SUM(Z1.SUMVALDOC) AS VALDOC, SUM(Z1.SUMVALABO) AS VALABO, SUM(Z1.SUMVALPEN) AS VALPEN, SUM(Z2.SUMVALXVEN) AS VALXVEN, SUM(Z3.SUMVAL30D) AS VAL30D, SUM(Z4.SUMVAL60D) AS VAL60D, SUM(Z5.SUMVAL90D) AS VAL90D, SUM(Z6.SUMVALMAS90) AS VALMAS90D";
                strSQL+=" FROM (";
                    strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALDOC) AS SUMVALDOC, sum(VALABO) AS SUMVALABO, sum(VALPEN) AS SUMVALPEN ";
                    strSQL+=" FROM ( ";
                        ///---query donde la suma del abono en tbm_detpag es igual al abono en tbm_pagmovinv por cada documento---///                        
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALPEN ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  INNER JOIN ";
                        strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                        strSQL+="    from tbm_detpag as x1";
                        strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                        strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                        strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                        strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                        strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VALPEN ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VALPEN";
                        strSQL+="  FROM";
                                strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                strSQL+=" FROM tbm_detpag as x1";
                                strSQL+=" INNER JOIN (  ";
                                        strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                        strSQL+=" from tbm_cabpag as a1";
                                        strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                        strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
                                strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                strSQL+=FilSql();
                                strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
                                strSQL+=") as p ";
                        strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
                        strSQL+=")";
                    strSQL+=" ) AS Q ";
                    strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
                    strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
                strSQL+=" ) AS Z1";
                strSQL+=" LEFT OUTER JOIN (";
                    strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALXVEN) AS SUMVALXVEN ";
                    strSQL+=" FROM ( ";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALXVEN ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  INNER JOIN ";
                        strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                        strSQL+="    from tbm_detpag as x1";
                        strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                        strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                        strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                        strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                        strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) <= 0 ";   ///---valxven---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VALXVEN ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) <= 0 ";   ///---valxven---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VALXVEN";
                        strSQL+="  FROM";
                                strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                strSQL+=" FROM tbm_detpag as x1";
                                strSQL+=" INNER JOIN (  ";
                                        strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                        strSQL+=" from tbm_cabpag as a1";
                                        strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                        strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
                                strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                strSQL+=FilSql();
                                strSQL+=" AND ('"+strFecDoc+"' - x4.fe_ven) <= 0 ";   ///---valxven---
                                strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
                                strSQL+=") as p ";
                        strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
                        strSQL+=")";
                    strSQL+=" ) AS Q ";
                    strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";  
                    strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
                strSQL+=" ) AS Z2 ON (Z1.co_emp=Z2.co_emp AND Z1.tx_ide = Z2.tx_ide)";
                strSQL+=" LEFT OUTER JOIN (";
                    strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VAL30D) AS SUMVAL30D ";
                    strSQL+=" FROM ( ";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VAL30D ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  INNER JOIN ";
                        strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                        strSQL+="    from tbm_detpag as x1";
                        strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                        strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                        strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                        strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                        strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 1 AND ('"+strFecDoc+"' - a2.fe_ven) <=30 )";   ///---val30d---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VAL30D ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 1 AND ('"+strFecDoc+"' - a2.fe_ven) <=30 )";   ///---val30d---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VAL30D";
                        strSQL+="  FROM";
                                strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                strSQL+=" FROM tbm_detpag as x1";
                                strSQL+=" INNER JOIN (  ";
                                        strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                        strSQL+=" from tbm_cabpag as a1";
                                        strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                        strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
                                strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                strSQL+=FilSql();
                                strSQL+=" AND (('"+strFecDoc+"' - x4.fe_ven) >= 1 AND ('"+strFecDoc+"' - x4.fe_ven) <=30 )";   ///---val30d---
                                strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
                                strSQL+=") as p ";
                        strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
                        strSQL+=")";
                    strSQL+=" ) AS Q ";
                    strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";                    
               strSQL+=" ) AS Z3 ON (Z1.co_emp=Z3.co_emp AND Z1.tx_ide = Z3.tx_ide)";
               strSQL+=" LEFT OUTER JOIN (";
                    strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VAL60D) AS SUMVAL60D ";
                    strSQL+=" FROM ( ";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VAL60D ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  INNER JOIN ";
                        strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                        strSQL+="    from tbm_detpag as x1";
                        strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                        strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                        strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                        strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                        strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 31 AND ('"+strFecDoc+"' - a2.fe_ven) <=60 )";   ///---val60d---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VAL60D ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 31 AND ('"+strFecDoc+"' - a2.fe_ven) <=60 )";   ///---val60d---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VAL60D";
                        strSQL+="  FROM";
                                strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                strSQL+=" FROM tbm_detpag as x1";
                                strSQL+=" INNER JOIN (  ";
                                        strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                        strSQL+=" from tbm_cabpag as a1";
                                        strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                        strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
                                strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                strSQL+=FilSql();
                                strSQL+=" AND (('"+strFecDoc+"' - x4.fe_ven) >= 31 AND ('"+strFecDoc+"' - x4.fe_ven) <= 60 )";   ///---val60d---
                                strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
                                strSQL+=") as p ";
                        strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
                        strSQL+=")";
                    strSQL+=" ) AS Q ";
                    strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
                    strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
               strSQL+=" ) AS Z4 ON (Z1.co_emp=Z4.co_emp AND Z1.tx_ide = Z4.tx_ide)";
               strSQL+=" LEFT OUTER JOIN (";
                    strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VAL90D) AS SUMVAL90D ";
                    strSQL+=" FROM ( ";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VAL90D ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  INNER JOIN ";
                        strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                        strSQL+="    from tbm_detpag as x1";
                        strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                        strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                        strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                        strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                        strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 61 AND ('"+strFecDoc+"' - a2.fe_ven) <=90 )";   ///---val90d---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VAL90D ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 61 AND ('"+strFecDoc+"' - a2.fe_ven) <=90 )";   ///---val90d---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VAL90D";
                        strSQL+="  FROM";
                                strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                strSQL+=" FROM tbm_detpag as x1";
                                strSQL+=" INNER JOIN (  ";
                                        strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                        strSQL+=" from tbm_cabpag as a1";
                                        strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                        strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
                                strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                strSQL+=FilSql();
                                strSQL+=" AND (('"+strFecDoc+"' - x4.fe_ven) >= 61 AND ('"+strFecDoc+"' - x4.fe_ven) <= 90 )";   ///---val90d---
                                strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
                                strSQL+=") as p ";
                        strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
                        strSQL+=")";
                    strSQL+=" ) AS Q ";
                    strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
                    strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
               strSQL+=" ) AS Z5 ON (Z1.co_emp=Z2.co_emp AND Z1.tx_ide = Z5.tx_ide)";               
               strSQL+=" LEFT OUTER JOIN (";
                    strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALMAS90) AS SUMVALMAS90 ";
                    strSQL+=" FROM ( ";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALMAS90 ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  INNER JOIN ";
                        strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
                        strSQL+="    from tbm_detpag as x1";
                        strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                        strSQL+="    where x2.st_reg NOT IN ('E','I') ";
                        strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
                        strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
                        strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) >=91 ";   ///---valmas90d---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
                        strSQL+=strAux;
                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
                        strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) >=91 ";   ///---valmas90d---
                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
                        strSQL+=")";
                        strSQL+="UNION ALL";
                        strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VALMAS90";
                        strSQL+="  FROM";
                                strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
                                strSQL+=" FROM tbm_detpag as x1";
                                strSQL+=" INNER JOIN (  ";
                                        strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
                                        strSQL+=" from tbm_cabpag as a1";
                                        strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
                                        strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
                                strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
                                strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
                                strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
                                ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
                                strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
                                strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
                                //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
                                //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
                                strSQL+=FilSql();
                                strSQL+=" AND ('"+strFecDoc+"' - x4.fe_ven) >= 91 ";   ///---valmas90d---
                                strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
                                strSQL+=") as p ";
                        strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
                        strSQL+=")";
                    strSQL+=" ) AS Q ";
                    strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
                    strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
               strSQL+=" ) AS Z6 ON (Z1.co_emp = Z6.co_emp AND Z1.tx_ide = Z6.tx_ide) ";
               strSQL+=" GROUP BY Z1.co_emp, Z1.co_cli, Z1.tx_ide, Z1.tx_nom ";
               strSQL+=" ORDER BY Z1.tx_nom, Z1.co_emp ";
               System.out.println("---DetFacturas cargarDatFacCorFec() Mnu=645--: "+ strSQL);
                
               rstFac=stmFac.executeQuery(strSQL);
                
               //Limpiar vector de datos.
               vecDatFac.clear();
               //Obtener los registros.
               lblMsgSis.setText("Cargando datos...");               
               pgrSis.setMinimum(0);
               pgrSis.setMaximum(intNumTotReg);
               pgrSis.setValue(0);               
               i=0;
                
               while (rstFac.next())
               {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_FAC_LIN,"");///0
                        vecReg.add(INT_TBL_DAT_FAC_COD_EMP,rstFac.getString("co_emp"));///1
                        vecReg.add(INT_TBL_DAT_FAC_COD_CLI,rstFac.getString("co_cli"));///2
                        vecReg.add(INT_TBL_DAT_FAC_RUC_CED,rstFac.getString("tx_ide"));///3
                        vecReg.add(INT_TBL_DAT_FAC_NOM_CLI,rstFac.getString("tx_nom"));///3
                        vecReg.add(INT_TBL_DAT_FAC_VAL_PEN,rstFac.getString("VALPEN"));///4                        
                        vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,rstFac.getString("VALXVEN"));///5
                        vecReg.add(INT_TBL_DAT_FAC_VAL_30D,rstFac.getString("VAL30D"));///6
                        vecReg.add(INT_TBL_DAT_FAC_VAL_60D,rstFac.getString("VAL60D"));///7
                        vecReg.add(INT_TBL_DAT_FAC_VAL_90D,rstFac.getString("VAL90D"));///8
                        vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,rstFac.getString("VALMAS90D"));///9
                        
                        vecDatFac.add(vecReg);
                        i++;
                        ///pgrSis.setValue(i);
                    }
                    else
                    {
                        break;
                    }
                }
                rstFac.close();
                stmFac.close();
                conFac.close();
                rstFac=null;
                stmFac=null;
                conFac=null;
                //Asignar vectores al modelo.
                objTblModFac.setData(vecDatFac);
                tblDat.setModel(objTblModFac);
                vecDatFac.clear();
                
                //Calcular totales.
                objTblTot.calcularTotales();
                
                if (intNumTotReg==tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + objTblModFac.getRowCount() + ".");
                
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);
                butCon.setText("Consultar");
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
    
    
//    private boolean cargarDatFacCorFecLoc()
//    {
//        int intCodEmp, intCodLoc, intNumTotReg, i;
//        
//        int intNumDia; 
//        String strFecSis, strFecIni, strFecSel, strFecVen, strFecDoc;
//        int intFecIni[];
//        int intFecFin[];//para calcular los dias entre fechas
//        int intFecSel[];//fecha seleccionada por el usuario//
//
//        
//        double dblSub, dblIva;
//        java.util.Date datFecSer, datFecVen, datFecAux;
//        
//        boolean blnRes=true;
//        try
//        {
//            ///butCon.setText("Detener");
//            ///lblMsgSis.setText("Obteniendo datos...");
//            
//            pgrSis.setIndeterminate(true);
//            butCon.setText("Detener");
//            lblMsgSis.setText("Obteniendo datos...");
//            
//            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
//            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
//            conFac=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            if (conFac!=null)
//            {
//                stmFac=conFac.createStatement();
//                //Obtener la fecha del servidor.
//                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//                if (datFecSer==null)
//                    return false;
//                datFecAux=datFecSer;
//                
//                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
//
//                //Obtener la condición.
//                strAux="";
//                
//                //Condicion para filtro por cliente
//                if (txtCodCli.getText().length()>0)
//                {
//                    strAux+=" AND a1.co_cli= " + txtCodCli.getText();
//                }
//                
//                ////////Condicion para filtro por cliente en un rango especifico
//                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
//                    strAux+=" AND ((LOWER(a4.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a4.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
//                
//                if (!(txtCodGrpCli.getText().equals("")))
//                {
//                    strAux+=" AND a1.co_tipdoc= " + txtCodTipDoc.getText();
//                }
//
//                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
//                {
//                    //Condicion para filtro por empresa
//                    if (txtCodEmp.getText().length()>0)
//                        strAux+=" AND a1.co_emp=" + txtCodEmp.getText();
//                }
//                else
//                {
//                    strAux+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
//                    
//                    //Condicion para filtro por cLocal
//                    if (txtCodLoc.getText().length()>0)
//                        strAux+=" AND a1.co_loc= " + txtCodLoc.getText();
//                    else
//                        strAux+=" AND a1.co_loc= " + objParSis.getCodigoLocal();
//                }
//                
//                ///FILTRO PARA MOSTRAR CREDITOS Y DEBITOS///
//                if ( !(chkMosCre.isSelected() && chkMosDeb.isSelected()) )
//                {
//                    System.out.println("ENTRO POR FILTRO DE CREDITOS Y DEBITOS ");
//                    if (chkMosCre.isSelected())
//                        strAux+=" AND a3.tx_natDoc='I'";
//                    else
//                        strAux+=" AND a3.tx_natDoc='E'";
//                }
//                ///FILTRO PARA DOCUMENTOS VENCIDOS///
//                if (chkMosDocVen.isSelected())
//                {
//                    System.out.println("ENTRO POR FILTRO DE DOCUMENTOS VENCIDOS ");
//                    strFecVen=objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
//                    strAux+=" AND a2.fe_ven<='" + strFecVen + "'";
//                }
//                ///FILTRO PARA MOSTRAR RETENCIONES///
//                if (!chkMosRet.isSelected())
//                {
//                    System.out.println("ENTRO POR FILTRO DE MOSTRAR RETENCIONES");
//                    strAux+=" AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
//                }
//                
//                ///strFecDoc=objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy");
//                strFecDoc=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "yyyy/MM/dd");
//                String strFecDocAux = objUti.formatearFecha(strFecDoc,"yyyy/MM/dd","dd/MM/yyyy");
//                System.out.println("---La Fecha Corte es: "+ strFecDoc);
//                System.out.println("---La Fecha Corte FORMATEADA es: "+ strFecDocAux);
//                
//                //Obtener el número total de registros.
//                strSQL="";
//                strSQL+="SELECT COUNT (*)";
//                strSQL+=" FROM (";
//                    strSQL+=" SELECT Z1.co_emp, Z1.co_cli, Z1.tx_ide, Z1.tx_nom, SUM(Z1.SUMVALDOC) AS VALDOC, SUM(Z1.SUMVALABO) AS VALABO, SUM(Z1.SUMVALPEN) AS VALPEN, SUM(Z2.SUMVALXVEN) AS VALXVEN, SUM(Z3.SUMVAL30D) AS VAL30D, SUM(Z4.SUMVAL60D) AS VAL60D, SUM(Z5.SUMVAL90D) AS VAL90D, SUM(Z6.SUMVALMAS90) AS VALMAS90D ";
//                    strSQL+=" FROM (";
//                        strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALDOC) AS SUMVALDOC, sum(VALABO) AS SUMVALABO, sum(VALPEN) AS SUMVALPEN ";
//                        strSQL+=" FROM ( ";
//                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALPEN ";
//                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                            strSQL+="  INNER JOIN ";
//                            strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
//                            strSQL+="    from tbm_detpag as x1";
//                            strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                            strSQL+="    where x2.st_reg NOT IN ('E','I') ";
//                            strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
//                            strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
//                            strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
//                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
//                            strSQL+=strAux;
//                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                            strSQL+=")";
//                            strSQL+="UNION ALL";
//                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VALPEN ";
//                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
//                            strSQL+=strAux;
//                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                            strSQL+=")";
//                            strSQL+="UNION ALL";
//                            strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VALPEN";
//                            strSQL+="  FROM";
//                                    strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
//                                    strSQL+=" FROM tbm_detpag as x1";
//                                    strSQL+=" INNER JOIN (  ";
//                                            strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
//                                            strSQL+=" from tbm_cabpag as a1";
//                                            strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
//                                            strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
//                                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
//                                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
//                                    ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
//                                    strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
//                                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
//                                    //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
//                                    //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
//                                    strSQL+=FilSql();
//                                    strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
//                                    strSQL+=") as p ";
//                            strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
//                            strSQL+=")";
//                        strSQL+=" ) AS Q ";
//                        strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";                    
//                        strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
//                    strSQL+=" ) AS Z1";
//                    strSQL+=" LEFT OUTER JOIN (";
//                        strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALXVEN) AS SUMVALXVEN ";
//                        strSQL+=" FROM ( ";
//                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALXVEN ";
//                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                            strSQL+="  INNER JOIN ";
//                            strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
//                            strSQL+="    from tbm_detpag as x1";
//                            strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                            strSQL+="    where x2.st_reg NOT IN ('E','I') ";
//                            strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
//                            strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
//                            strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
//                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
//                            strSQL+=strAux;
//                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                            strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) <= 0 ";   ///---valxven---
//                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                            strSQL+=")";
//                            strSQL+="UNION ALL";
//                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VALXVEN ";
//                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
//                            strSQL+=strAux;
//                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                            strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) <= 0 ";   ///---valxven---
//                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                            strSQL+=")";
//                            strSQL+="UNION ALL";
//                            strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VALXVEN";
//                            strSQL+="  FROM";
//                                    strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
//                                    strSQL+=" FROM tbm_detpag as x1";
//                                    strSQL+=" INNER JOIN (  ";
//                                            strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
//                                            strSQL+=" from tbm_cabpag as a1";
//                                            strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
//                                            strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
//                                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
//                                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
//                                    ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
//                                    strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
//                                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
//                                    //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
//                                    //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
//                                    strSQL+=FilSql();
//                                    strSQL+=" AND ('"+strFecDoc+"' - x4.fe_ven) <= 0 ";   ///---valxven---
//                                    strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
//                                    strSQL+=") as p ";
//                            strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
//                            strSQL+=")";
//                        strSQL+=" ) AS Q ";
//                        strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
//                        strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
//                    strSQL+=" ) AS Z2 ON (Z1.co_emp=Z2.co_emp AND Z1.tx_ide = Z2.tx_ide)";
//                    strSQL+=" LEFT OUTER JOIN (";
//                        strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VAL30D) AS SUMVAL30D ";
//                        strSQL+=" FROM ( ";
//                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VAL30D ";
//                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                            strSQL+="  INNER JOIN ";
//                            strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
//                            strSQL+="    from tbm_detpag as x1";
//                            strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                            strSQL+="    where x2.st_reg NOT IN ('E','I') ";
//                            strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
//                            strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
//                            strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
//                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
//                            strSQL+=strAux;
//                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                            strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 1 AND ('"+strFecDoc+"' - a2.fe_ven) <=30 )";   ///---val30d---
//                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                            strSQL+=")";
//                            strSQL+="UNION ALL";
//                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VAL30D ";
//                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
//                            strSQL+=strAux;
//                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                            strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 1 AND ('"+strFecDoc+"' - a2.fe_ven) <=30 )";   ///---val30d---
//                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                            strSQL+=")";
//                            strSQL+="UNION ALL";
//                            strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VAL30D";
//                            strSQL+="  FROM";
//                                    strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
//                                    strSQL+=" FROM tbm_detpag as x1";
//                                    strSQL+=" INNER JOIN (  ";
//                                            strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
//                                            strSQL+=" from tbm_cabpag as a1";
//                                            strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
//                                            strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
//                                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
//                                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
//                                    ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
//                                    strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
//                                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
//                                    //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
//                                    //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
//                                    strSQL+=FilSql();
//                                    strSQL+=" AND (('"+strFecDoc+"' - x4.fe_ven) >= 1 AND ('"+strFecDoc+"' - x4.fe_ven) <=30 )";   ///---val30d---
//                                    strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
//                                    strSQL+=") as p ";
//                            strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
//                            strSQL+=")";
//                        strSQL+=" ) AS Q ";
//                        strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
//                        strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
//                   strSQL+=" ) AS Z3 ON (Z1.co_emp=Z3.co_emp AND Z1.tx_ide = Z3.tx_ide)";
//                   strSQL+=" LEFT OUTER JOIN (";
//                        strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VAL60D) AS SUMVAL60D ";
//                        strSQL+=" FROM ( ";
//                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VAL60D ";
//                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                            strSQL+="  INNER JOIN ";
//                            strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
//                            strSQL+="    from tbm_detpag as x1";
//                            strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                            strSQL+="    where x2.st_reg NOT IN ('E','I') ";
//                            strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
//                            strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
//                            strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
//                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
//                            strSQL+=strAux;
//                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                            strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 31 AND ('"+strFecDoc+"' - a2.fe_ven) <=60 )";   ///---val60d---
//                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                            strSQL+=")";
//                            strSQL+="UNION ALL";
//                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VAL60D ";
//                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
//                            strSQL+=strAux;
//                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                            strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 31 AND ('"+strFecDoc+"' - a2.fe_ven) <= 60 )";   ///---val60d---
//                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                            strSQL+=")";
//                            strSQL+="UNION ALL";
//                            strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VAL60D";
//                            strSQL+="  FROM";
//                                    strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
//                                    strSQL+=" FROM tbm_detpag as x1";
//                                    strSQL+=" INNER JOIN (  ";
//                                            strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
//                                            strSQL+=" from tbm_cabpag as a1";
//                                            strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
//                                            strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
//                                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
//                                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
//                                    ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
//                                    strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
//                                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
//                                    //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
//                                    //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
//                                    strSQL+=FilSql();
//                                    strSQL+=" AND (('"+strFecDoc+"' - x4.fe_ven) >= 31 AND ('"+strFecDoc+"' - x4.fe_ven) <= 60 )";   ///---val60d---
//                                    strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
//                                    strSQL+=") as p ";
//                            strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
//                            strSQL+=")";
//                        strSQL+=" ) AS Q ";
//                        strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
//                        strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
//                   strSQL+=" ) AS Z4 ON (Z1.co_emp=Z4.co_emp AND Z1.tx_ide = Z4.tx_ide)";
//                   strSQL+=" LEFT OUTER JOIN (";
//                        strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VAL90D) AS SUMVAL90D ";
//                        strSQL+=" FROM ( ";
//                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VAL90D ";
//                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                            strSQL+="  INNER JOIN ";
//                            strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
//                            strSQL+="    from tbm_detpag as x1";
//                            strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                            strSQL+="    where x2.st_reg NOT IN ('E','I') ";
//                            strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
//                            strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
//                            strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
//                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
//                            strSQL+=strAux;
//                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                            strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 61 AND ('"+strFecDoc+"' - a2.fe_ven) <=90 )";   ///---val90d---
//                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                            strSQL+=")";
//                            strSQL+="UNION ALL";
//                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VAL90D ";
//                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
//                            strSQL+=strAux;
//                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                            strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 61 AND ('"+strFecDoc+"' - a2.fe_ven) <=90 )";   ///---val90d---
//                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                            strSQL+=")";
//                            strSQL+="UNION ALL";
//                            strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VAL90D";
//                            strSQL+="  FROM";
//                                    strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
//                                    strSQL+=" FROM tbm_detpag as x1";
//                                    strSQL+=" INNER JOIN (  ";
//                                            strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
//                                            strSQL+=" from tbm_cabpag as a1";
//                                            strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
//                                            strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
//                                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
//                                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
//                                    ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
//                                    strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
//                                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
//                                    //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
//                                    //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
//                                    strSQL+=FilSql();
//                                    strSQL+=" AND (('"+strFecDoc+"' - x4.fe_ven) >= 61 AND ('"+strFecDoc+"' - x4.fe_ven) <= 90 )";   ///---val90d---
//                                    strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x2.fe_doc, x4.mo_pag ";
//                                    strSQL+=") as p ";
//                            strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
//                            strSQL+=")";
//                        strSQL+=" ) AS Q ";
//                        strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
//                        strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
//                   strSQL+=" ) AS Z5 ON (Z1.co_emp=Z5.co_emp AND Z1.tx_ide = Z5.tx_ide)";               
//                   strSQL+=" LEFT OUTER JOIN (";
//                        strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALMAS90) AS SUMVALMAS90 ";
//                        strSQL+=" FROM ( ";
//                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALMAS90 ";
//                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                            strSQL+="  INNER JOIN ";
//                            strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
//                            strSQL+="    from tbm_detpag as x1";
//                            strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                            strSQL+="    where x2.st_reg NOT IN ('E','I') ";
//                            strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
//                            strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
//                            strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
//                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
//                            strSQL+=strAux;
//                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                            strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) >=91 ";   ///---valmas90d---
//                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                            strSQL+=")";
//                            strSQL+="UNION ALL";
//                            strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
//                            strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                            strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                            strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                            strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                            ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                            strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
//                            strSQL+=strAux;
//                            strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                            strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) >=91 ";   ///---valmas90d---
//                            strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                            strSQL+=")";
//                            strSQL+="UNION ALL";
//                            strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VALMAS90";
//                            strSQL+="  FROM";
//                                    strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
//                                    strSQL+=" FROM tbm_detpag as x1";
//                                    strSQL+=" INNER JOIN (  ";
//                                            strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
//                                            strSQL+=" from tbm_cabpag as a1";
//                                            strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
//                                            strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
//                                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
//                                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
//                                    ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
//                                    strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
//                                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
//                                    //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
//                                    //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
//                                    strSQL+=FilSql();
//                                    strSQL+=" AND ('"+strFecDoc+"' - x4.fe_ven) >= 91 ";   ///---valmas90d---
//                                    strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
//                                    strSQL+=") as p ";
//                            strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
//                            strSQL+=")";
//                        strSQL+=" ) AS Q ";
//                        strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
//                        strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
//                   strSQL+=" ) AS Z6 ON (Z1.co_emp=Z6.co_emp AND Z1.tx_ide = Z6.tx_ide)";
//                   strSQL+=" GROUP BY Z1.co_emp, Z1.co_cli, Z1.tx_ide, Z1.tx_nom";
//                   strSQL+=" ORDER BY Z1.tx_nom, Z1.co_emp ";
//                strSQL+=" ) AS B1";                
//                System.out.println("---Query COUNT --cargarDatFacCorFec()--Mnu=645: "+ strSQL);
//                
//                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
//                if (intNumTotReg==-1)
//                    return false;                
///*
//
//
// -----query prueba----
//SELECT Z1.co_emp, Z1.co_cli, Z1.tx_ide, Z1.tx_nom, SUM(Z1.SUMVALDOC) AS VALDOC, SUM(Z1.SUMVALABO) AS VALABO, SUM(Z1.SUMVALPEN) AS VALPEN, SUM(Z2.SUMVALXVEN) AS VALXVEN , SUM(Z3.SUMVAL30D) AS VAL30D, SUM(Z4.SUMVAL60D) AS VAL60D, SUM(Z5.SUMVAL90D) AS VAL90D, SUM(Z6.SUMVALMAS90D) AS VALMAS90D
//FROM 
//( 
//SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALDOC) AS SUMVALDOC, sum(VALABO) AS SUMVALABO, sum(VALPEN) AS SUMVALPEN  
//FROM (
//( 
//---query donde la suma del abono en tbm_detpag es igual al abono en tbm_pagmovinv por cada documento---
//SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, 
//(a2.mo_pag+a6.sumabodet) as VALPEN   
//FROM tbm_cabMovInv AS a1   
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
//AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
//INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
//---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
//INNER JOIN   
//( 
//select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet     
//from tbm_detpag as x1    
//inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc 
//and x1.co_doc=x2.co_doc)     where x2.st_reg NOT IN ('E','I')     AND x2.fe_doc <= '2006/12/31'    
//group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  
//) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag 
//and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)  
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 
//AND a3.ne_mod in (1,3)   AND a1.fe_doc <= '2006/12/31'  
//ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
//)
//UNION ALL
//( 
//---query donde el abono en tbm_pagmovinv es igual acero (no tiene pago asociado a tbm_detpag)---
//SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, 
//(a2.mo_pag+a2.nd_abo) AS VALPEN   
//FROM tbm_cabMovInv AS a1   
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
//AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
//INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
//---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) 
//AND a2.nd_abo=0.00
//AND a1.fe_doc <= '2006/12/31'  
//ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
//)
//UNION ALL
//( 
//SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, 
//(p.mo_pag+sum(p.valpagfecmay)) as VALPEN  
//FROM
//( 
//SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, 
//case when x2.fe_doc >='2006/12/31' then 0.00 end as valpagfecmay  
//FROM tbm_detpag as x1 
//INNER JOIN 
//(   select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc  
//from tbm_cabpag as a1 where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '2006/12/31' 
//) as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc)  
//INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc)  
//INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg)  
//INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc)  
//---LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg) 
//INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)
//WHERE x3.fe_doc <= '2006/12/31' 
//GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag 
//) as p  
//GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag
//) 
//) AS Q  
//GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom  
//ORDER BY Q.tx_nom, Q.co_emp
//) AS Z1 
//
//LEFT OUTER JOIN 
//
//(
//SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALDOC) AS SUMVALDOC, sum(VALABO) AS SUMVALABO, 
//sum(VALXVEN) AS SUMVALXVEN  
//FROM (
//( 
//---query donde la suma del abono en tbm_detpag es igual al abono en tbm_pagmovinv por cada documento---
//SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, 
//(a2.mo_pag+a6.sumabodet) as VALXVEN   
//FROM tbm_cabMovInv AS a1   
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
//AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
//INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
//---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
//INNER JOIN   
//( 
//select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet     
//from tbm_detpag as x1    
//inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc 
//and x1.co_doc=x2.co_doc)     where x2.st_reg NOT IN ('E','I')     AND x2.fe_doc <= '2006/12/31'    
//group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  
//) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag 
//and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)  
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 
//AND a3.ne_mod in (1,3)   AND a1.fe_doc <= '2006/12/31'  AND ('2006/12/31' - a2.fe_ven) <= 0   
//ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
//)
//UNION ALL
//( 
//---query donde el abono en tbm_pagmovinv es igual acero (no tiene pago asociado a tbm_detpag)---
//SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, 
//(a2.mo_pag+a2.nd_abo) AS VALPEN   
//FROM tbm_cabMovInv AS a1   
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
//AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
//INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
//---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) 
//AND a2.nd_abo=0.00 AND a1.fe_doc <= '2006/12/31'  AND ('2006/12/31' - a2.fe_ven) <= 0   
//ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
//)
//UNION ALL
//( 
//SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, 
//(p.mo_pag+sum(p.valpagfecmay)) as VALPEN  
//FROM
//( 
//SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, 
//case when x2.fe_doc >='2006/12/31' then 0.00 end as valpagfecmay  
//FROM tbm_detpag as x1 
//INNER JOIN 
//(   select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc  
//from tbm_cabpag as a1 where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '2006/12/31' 
//) as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc)  
//INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc)  
//INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg)  
//INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc)  
//---LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg) 
//INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)
//WHERE x3.fe_doc <= '2006/12/31' AND ('2006/12/31' - x4.fe_ven) <= 0   
//GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag 
//) as p  
//GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag
//) 
//) AS Q  
//GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom  
//ORDER BY Q.tx_nom, Q.co_emp
//) AS Z2 ON (Z1.co_emp=Z2.co_emp AND Z1.tx_ide=Z2.tx_ide)
//
//LEFT OUTER JOIN 
//
//(
//SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALDOC) AS SUMVALDOC, sum(VALABO) AS SUMVALABO, 
//sum(VAL30D) AS SUMVAL30D
//FROM (
//( 
//---query donde la suma del abono en tbm_detpag es igual al abono en tbm_pagmovinv por cada documento---
//SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, 
//(a2.mo_pag+a6.sumabodet) as VAL30D   
//FROM tbm_cabMovInv AS a1   
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
//AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
//INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
//---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
//INNER JOIN   
//( 
//select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet     
//from tbm_detpag as x1    
//inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc 
//and x1.co_doc=x2.co_doc)     where x2.st_reg NOT IN ('E','I')     AND x2.fe_doc <= '2006/12/31'    
//group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  
//) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag 
//and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)  
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 
//AND a3.ne_mod in (1,3)   AND a1.fe_doc <= '2006/12/31'  ---AND ('2006/12/31' - a2.fe_ven) <= 0   
//AND (('2006/12/31' - a2.fe_ven) >= 1 AND ('2006/12/31' - a2.fe_ven) <=30 )
//ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
//)
//UNION ALL
//( 
//---query donde el abono en tbm_pagmovinv es igual acero (no tiene pago asociado a tbm_detpag)---
//SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, 
//(a2.mo_pag+a2.nd_abo) AS VAL30D   
//FROM tbm_cabMovInv AS a1   
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
//AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
//INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
//---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) 
//AND a2.nd_abo=0.00 AND a1.fe_doc <= '2006/12/31'  ---AND ('2006/12/31' - a2.fe_ven) <= 0  
//AND (('2006/12/31' - a2.fe_ven) >= 1 AND ('2006/12/31' - a2.fe_ven) <=30 ) 
//ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
//)
//UNION ALL
//( 
//SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, 
//(p.mo_pag+sum(p.valpagfecmay)) as VAL30D  
//FROM
//( 
//SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, 
//case when x2.fe_doc >='2006/12/31' then 0.00 end as valpagfecmay  
//FROM tbm_detpag as x1 
//INNER JOIN 
//(   select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc  
//from tbm_cabpag as a1 where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '2006/12/31' 
//) as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc)  
//INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc)  
//INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg)  
//INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc)  
//---LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg) 
//INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)
//WHERE x3.fe_doc <= '2006/12/31' ---AND ('2006/12/31' - x4.fe_ven) <= 0   
//AND (('2006/12/31' - x4.fe_ven) >= 1 AND ('2006/12/31' - x4.fe_ven) <=30 )
//GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag 
//) as p  
//GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag
//) 
//) AS Q  
//GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom  
//ORDER BY Q.tx_nom, Q.co_emp
//) AS Z3 ON (Z1.co_emp=Z3.co_emp AND Z1.tx_ide=Z3.tx_ide)
//
//LEFT OUTER JOIN 
//
//(
//SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALDOC) AS SUMVALDOC, sum(VALABO) AS SUMVALABO, 
//sum(VAL60D) AS SUMVAL60D
//FROM (
//( 
//---query donde la suma del abono en tbm_detpag es igual al abono en tbm_pagmovinv por cada documento---
//SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, 
//(a2.mo_pag+a6.sumabodet) as VAL60D   
//FROM tbm_cabMovInv AS a1   
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
//AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
//INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
//---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
//INNER JOIN   
//( 
//select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet     
//from tbm_detpag as x1    
//inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc 
//and x1.co_doc=x2.co_doc)     where x2.st_reg NOT IN ('E','I')     AND x2.fe_doc <= '2006/12/31'    
//group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  
//) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag 
//and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)  
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 
//AND a3.ne_mod in (1,3)   AND a1.fe_doc <= '2006/12/31'  ---AND ('2006/12/31' - a2.fe_ven) <= 0   
//AND (('2006/12/31' - a2.fe_ven) >= 31 AND ('2006/12/31' - a2.fe_ven) <=60 )
//ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
//)
//UNION ALL
//( 
//---query donde el abono en tbm_pagmovinv es igual acero (no tiene pago asociado a tbm_detpag)---
//SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, 
//(a2.mo_pag+a2.nd_abo) AS VAL60D   
//FROM tbm_cabMovInv AS a1   
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
//AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
//INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
//---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) 
//AND a2.nd_abo=0.00 AND a1.fe_doc <= '2006/12/31'  ---AND ('2006/12/31' - a2.fe_ven) <= 0  
//AND (('2006/12/31' - a2.fe_ven) >= 31 AND ('2006/12/31' - a2.fe_ven) <=60 ) 
//ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
//)
//UNION ALL
//( 
//SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, 
//(p.mo_pag+sum(p.valpagfecmay)) as VAL60D  
//FROM
//( 
//SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, 
//case when x2.fe_doc >='2006/12/31' then 0.00 end as valpagfecmay  
//FROM tbm_detpag as x1 
//INNER JOIN 
//(   select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc  
//from tbm_cabpag as a1 where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '2006/12/31' 
//) as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc)  
//INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc)  
//INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg)  
//INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc)  
//---LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg) 
//INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)
//WHERE x3.fe_doc <= '2006/12/31' ---AND ('2006/12/31' - x4.fe_ven) <= 0   
//AND (('2006/12/31' - x4.fe_ven) >= 31 AND ('2006/12/31' - x4.fe_ven) <=60 )
//GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag 
//) as p  
//GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag
//) 
//) AS Q  
//GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom  
//ORDER BY Q.tx_nom, Q.co_emp
//) AS Z4 ON (Z1.co_emp=Z4.co_emp AND Z1.tx_ide=Z4.tx_ide)
//
//LEFT OUTER JOIN 
//
//(
//SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALDOC) AS SUMVALDOC, sum(VALABO) AS SUMVALABO, 
//sum(VAL90D) AS SUMVAL90D
//FROM (
//( 
//---query donde la suma del abono en tbm_detpag es igual al abono en tbm_pagmovinv por cada documento---
//SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, 
//(a2.mo_pag+a6.sumabodet) as VAL90D   
//FROM tbm_cabMovInv AS a1   
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
//AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
//INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
//---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
//INNER JOIN   
//( 
//select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet     
//from tbm_detpag as x1    
//inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc 
//and x1.co_doc=x2.co_doc)     where x2.st_reg NOT IN ('E','I')     AND x2.fe_doc <= '2006/12/31'    
//group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  
//) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag 
//and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)  
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 
//AND a3.ne_mod in (1,3)   AND a1.fe_doc <= '2006/12/31'  ---AND ('2006/12/31' - a2.fe_ven) <= 0   
//AND (('2006/12/31' - a2.fe_ven) >= 61 AND ('2006/12/31' - a2.fe_ven) <=90 )
//ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
//)
//UNION ALL
//( 
//---query donde el abono en tbm_pagmovinv es igual acero (no tiene pago asociado a tbm_detpag)---
//SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, 
//(a2.mo_pag+a2.nd_abo) AS VAL90D   
//FROM tbm_cabMovInv AS a1   
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
//AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
//INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
//---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) 
//AND a2.nd_abo=0.00 AND a1.fe_doc <= '2006/12/31'  ---AND ('2006/12/31' - a2.fe_ven) <= 0  
//AND (('2006/12/31' - a2.fe_ven) >= 61 AND ('2006/12/31' - a2.fe_ven) <=90 ) 
//ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
//)
//UNION ALL
//( 
//SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, 
//(p.mo_pag+sum(p.valpagfecmay)) as VAL90D  
//FROM
//( 
//SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, 
//case when x2.fe_doc >='2006/12/31' then 0.00 end as valpagfecmay  
//FROM tbm_detpag as x1 
//INNER JOIN 
//(   select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc  
//from tbm_cabpag as a1 where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '2006/12/31' 
//) as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc)  
//INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc)  
//INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg)  
//INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc)  
//---LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg) 
//INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)
//WHERE x3.fe_doc <= '2006/12/31' ---AND ('2006/12/31' - x4.fe_ven) <= 0   
//AND (('2006/12/31' - x4.fe_ven) >= 61 AND ('2006/12/31' - x4.fe_ven) <=90 )
//GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag 
//) as p  
//GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag
//) 
//) AS Q  
//GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom  
//ORDER BY Q.tx_nom, Q.co_emp
//) AS Z5 ON (Z1.co_emp=Z5.co_emp AND Z1.tx_ide=Z5.tx_ide)
//
//LEFT OUTER JOIN
//
//(
//SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALDOC) AS SUMVALDOC, sum(VALABO) AS SUMVALABO, 
//sum(VALMAS90D) AS SUMVALMAS90D
//FROM (
//( 
//---query donde la suma del abono en tbm_detpag es igual al abono en tbm_pagmovinv por cada documento---
//SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, 
//(a2.mo_pag+a6.sumabodet) as VALMAS90D   
//FROM tbm_cabMovInv AS a1   
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
//AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
//INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
//---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
//INNER JOIN   
//( 
//select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet     
//from tbm_detpag as x1    
//inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc 
//and x1.co_doc=x2.co_doc)     where x2.st_reg NOT IN ('E','I')     AND x2.fe_doc <= '2006/12/31'    
//group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  
//) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag 
//and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)  
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 
//AND a3.ne_mod in (1,3)   AND a1.fe_doc <= '2006/12/31'  
//---AND ('2006/12/31' - a2.fe_ven) <= 0   
//---AND (('2006/12/31' - a2.fe_ven) >= 61 AND ('2006/12/31' - a2.fe_ven) <=90 )
//AND ('2006/12/31' - a2.fe_ven) >= 91
//ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
//)
//UNION ALL
//( 
//---query donde el abono en tbm_pagmovinv es igual acero (no tiene pago asociado a tbm_detpag)---
//SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, 
//(a2.mo_pag+a2.nd_abo) AS VALMAS90D   
//FROM tbm_cabMovInv AS a1   
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
//AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
//INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
//---LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) 
//AND a2.nd_abo=0.00 AND a1.fe_doc <= '2006/12/31'  
//---AND ('2006/12/31' - a2.fe_ven) <= 0
//---AND (('2006/12/31' - a2.fe_ven) >= 61 AND ('2006/12/31' - a2.fe_ven) <=90 ) 
//AND ('2006/12/31' - a2.fe_ven) >= 91
//ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
//)
//UNION ALL
//( 
//SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, 
//(p.mo_pag+sum(p.valpagfecmay)) as VALMAS90D  
//FROM
//( 
//SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, 
//case when x2.fe_doc >='2006/12/31' then 0.00 end as valpagfecmay  
//FROM tbm_detpag as x1 
//INNER JOIN 
//(   select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc  
//from tbm_cabpag as a1 where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '2006/12/31' 
//) as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc)  
//INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc)  
//INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg)  
//INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc)  
//---LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg) 
//INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)
//WHERE x3.fe_doc <= '2006/12/31' 
//---AND ('2006/12/31' - x4.fe_ven) <= 0   
//---AND (('2006/12/31' - x4.fe_ven) >= 61 AND ('2006/12/31' - x4.fe_ven) <=90 )
//AND ('2006/12/31' - x4.fe_ven) >= 91
//GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag 
//) as p  
//GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag
//) 
//) AS Q  
//GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom  
//ORDER BY Q.tx_nom, Q.co_emp
//) AS Z6 ON (Z1.co_emp=Z6.co_emp AND Z1.tx_ide=Z6.tx_ide)
//
//GROUP BY Z1.co_emp, Z1.co_cli, Z1.tx_ide, Z1.tx_nom
//ORDER BY Z1.tx_nom, Z1.co_emp                
//*/                
//                //Armar la sentencia SQL.
//                strSQL="";
//                strSQL+=" SELECT Z1.co_emp, Z1.co_cli, Z1.tx_ide, Z1.tx_nom, SUM(Z1.SUMVALDOC) AS VALDOC, SUM(Z1.SUMVALABO) AS VALABO, SUM(Z1.SUMVALPEN) AS VALPEN, SUM(Z2.SUMVALXVEN) AS VALXVEN, SUM(Z3.SUMVAL30D) AS VAL30D, SUM(Z4.SUMVAL60D) AS VAL60D, SUM(Z5.SUMVAL90D) AS VAL90D, SUM(Z6.SUMVALMAS90) AS VALMAS90D";
//                strSQL+=" FROM (";
//                    strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALDOC) AS SUMVALDOC, sum(VALABO) AS SUMVALABO, sum(VALPEN) AS SUMVALPEN ";
//                    strSQL+=" FROM ( ";
//                        ///---query donde la suma del abono en tbm_detpag es igual al abono en tbm_pagmovinv por cada documento---///                        
//                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALPEN ";
//                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                        strSQL+="  INNER JOIN ";
//                        strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
//                        strSQL+="    from tbm_detpag as x1";
//                        strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                        strSQL+="    where x2.st_reg NOT IN ('E','I') ";
//                        strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
//                        strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
//                        strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
//                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
//                        strSQL+=strAux;
//                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                        strSQL+=")";
//                        strSQL+="UNION ALL";
//                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VALPEN ";
//                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
//                        strSQL+=strAux;
//                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                        strSQL+=")";
//                        strSQL+="UNION ALL";
//                        strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VALPEN";
//                        strSQL+="  FROM";
//                                strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
//                                strSQL+=" FROM tbm_detpag as x1";
//                                strSQL+=" INNER JOIN (  ";
//                                        strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
//                                        strSQL+=" from tbm_cabpag as a1";
//                                        strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
//                                        strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                                strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
//                                strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
//                                strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
//                                ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
//                                strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
//                                strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
//                                //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
//                                //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
//                                strSQL+=FilSql();
//                                strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
//                                strSQL+=") as p ";
//                        strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
//                        strSQL+=")";
//                    strSQL+=" ) AS Q ";
//                    strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
//                    strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
//                strSQL+=" ) AS Z1";
//                strSQL+=" LEFT OUTER JOIN (";
//                    strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALXVEN) AS SUMVALXVEN ";
//                    strSQL+=" FROM ( ";
//                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALXVEN ";
//                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                        strSQL+="  INNER JOIN ";
//                        strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
//                        strSQL+="    from tbm_detpag as x1";
//                        strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                        strSQL+="    where x2.st_reg NOT IN ('E','I') ";
//                        strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
//                        strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
//                        strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
//                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
//                        strSQL+=strAux;
//                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                        strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) <= 0 ";   ///---valxven---
//                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                        strSQL+=")";
//                        strSQL+="UNION ALL";
//                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VALXVEN ";
//                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
//                        strSQL+=strAux;
//                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                        strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) <= 0 ";   ///---valxven---
//                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                        strSQL+=")";
//                        strSQL+="UNION ALL";
//                        strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VALXVEN";
//                        strSQL+="  FROM";
//                                strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
//                                strSQL+=" FROM tbm_detpag as x1";
//                                strSQL+=" INNER JOIN (  ";
//                                        strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
//                                        strSQL+=" from tbm_cabpag as a1";
//                                        strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
//                                        strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                                strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
//                                strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
//                                strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
//                                ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
//                                strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
//                                strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
//                                //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
//                                //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
//                                strSQL+=FilSql();
//                                strSQL+=" AND ('"+strFecDoc+"' - x4.fe_ven) <= 0 ";   ///---valxven---
//                                strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
//                                strSQL+=") as p ";
//                        strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
//                        strSQL+=")";
//                    strSQL+=" ) AS Q ";
//                    strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";  
//                    strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
//                strSQL+=" ) AS Z2 ON (Z1.co_emp=Z2.co_emp AND Z1.tx_ide = Z2.tx_ide)";
//                strSQL+=" LEFT OUTER JOIN (";
//                    strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VAL30D) AS SUMVAL30D ";
//                    strSQL+=" FROM ( ";
//                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VAL30D ";
//                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                        strSQL+="  INNER JOIN ";
//                        strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
//                        strSQL+="    from tbm_detpag as x1";
//                        strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                        strSQL+="    where x2.st_reg NOT IN ('E','I') ";
//                        strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
//                        strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
//                        strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
//                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
//                        strSQL+=strAux;
//                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                        strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 1 AND ('"+strFecDoc+"' - a2.fe_ven) <=30 )";   ///---val30d---
//                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                        strSQL+=")";
//                        strSQL+="UNION ALL";
//                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VAL30D ";
//                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
//                        strSQL+=strAux;
//                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                        strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 1 AND ('"+strFecDoc+"' - a2.fe_ven) <=30 )";   ///---val30d---
//                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                        strSQL+=")";
//                        strSQL+="UNION ALL";
//                        strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VAL30D";
//                        strSQL+="  FROM";
//                                strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
//                                strSQL+=" FROM tbm_detpag as x1";
//                                strSQL+=" INNER JOIN (  ";
//                                        strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
//                                        strSQL+=" from tbm_cabpag as a1";
//                                        strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
//                                        strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                                strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
//                                strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
//                                strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
//                                ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
//                                strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
//                                strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
//                                //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
//                                //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
//                                strSQL+=FilSql();
//                                strSQL+=" AND (('"+strFecDoc+"' - x4.fe_ven) >= 1 AND ('"+strFecDoc+"' - x4.fe_ven) <=30 )";   ///---val30d---
//                                strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
//                                strSQL+=") as p ";
//                        strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
//                        strSQL+=")";
//                    strSQL+=" ) AS Q ";
//                    strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";                    
//               strSQL+=" ) AS Z3 ON (Z1.co_emp=Z3.co_emp AND Z1.tx_ide = Z3.tx_ide)";
//               strSQL+=" LEFT OUTER JOIN (";
//                    strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VAL60D) AS SUMVAL60D ";
//                    strSQL+=" FROM ( ";
//                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VAL60D ";
//                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                        strSQL+="  INNER JOIN ";
//                        strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
//                        strSQL+="    from tbm_detpag as x1";
//                        strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                        strSQL+="    where x2.st_reg NOT IN ('E','I') ";
//                        strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
//                        strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
//                        strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
//                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
//                        strSQL+=strAux;
//                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                        strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 31 AND ('"+strFecDoc+"' - a2.fe_ven) <=60 )";   ///---val60d---
//                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                        strSQL+=")";
//                        strSQL+="UNION ALL";
//                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VAL60D ";
//                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
//                        strSQL+=strAux;
//                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                        strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 31 AND ('"+strFecDoc+"' - a2.fe_ven) <=60 )";   ///---val60d---
//                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                        strSQL+=")";
//                        strSQL+="UNION ALL";
//                        strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VAL60D";
//                        strSQL+="  FROM";
//                                strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
//                                strSQL+=" FROM tbm_detpag as x1";
//                                strSQL+=" INNER JOIN (  ";
//                                        strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
//                                        strSQL+=" from tbm_cabpag as a1";
//                                        strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
//                                        strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                                strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
//                                strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
//                                strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
//                                ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
//                                strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
//                                strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
//                                //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
//                                //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
//                                strSQL+=FilSql();
//                                strSQL+=" AND (('"+strFecDoc+"' - x4.fe_ven) >= 31 AND ('"+strFecDoc+"' - x4.fe_ven) <= 60 )";   ///---val60d---
//                                strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
//                                strSQL+=") as p ";
//                        strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
//                        strSQL+=")";
//                    strSQL+=" ) AS Q ";
//                    strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
//                    strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
//               strSQL+=" ) AS Z4 ON (Z1.co_emp=Z4.co_emp AND Z1.tx_ide = Z4.tx_ide)";
//               strSQL+=" LEFT OUTER JOIN (";
//                    strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VAL90D) AS SUMVAL90D ";
//                    strSQL+=" FROM ( ";
//                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VAL90D ";
//                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                        strSQL+="  INNER JOIN ";
//                        strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
//                        strSQL+="    from tbm_detpag as x1";
//                        strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                        strSQL+="    where x2.st_reg NOT IN ('E','I') ";
//                        strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
//                        strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
//                        strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
//                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
//                        strSQL+=strAux;
//                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                        strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 61 AND ('"+strFecDoc+"' - a2.fe_ven) <=90 )";   ///---val90d---
//                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                        strSQL+=")";
//                        strSQL+="UNION ALL";
//                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VAL90D ";
//                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
//                        strSQL+=strAux;
//                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                        strSQL+="  AND (('"+strFecDoc+"' - a2.fe_ven) >= 61 AND ('"+strFecDoc+"' - a2.fe_ven) <=90 )";   ///---val90d---
//                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                        strSQL+=")";
//                        strSQL+="UNION ALL";
//                        strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VAL90D";
//                        strSQL+="  FROM";
//                                strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
//                                strSQL+=" FROM tbm_detpag as x1";
//                                strSQL+=" INNER JOIN (  ";
//                                        strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
//                                        strSQL+=" from tbm_cabpag as a1";
//                                        strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
//                                        strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                                strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
//                                strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
//                                strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
//                                ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
//                                strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
//                                strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
//                                //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
//                                //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
//                                strSQL+=FilSql();
//                                strSQL+=" AND (('"+strFecDoc+"' - x4.fe_ven) >= 61 AND ('"+strFecDoc+"' - x4.fe_ven) <= 90 )";   ///---val90d---
//                                strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
//                                strSQL+=") as p ";
//                        strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
//                        strSQL+=")";
//                    strSQL+=" ) AS Q ";
//                    strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
//                    strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
//               strSQL+=" ) AS Z5 ON (Z1.co_emp=Z2.co_emp AND Z1.tx_ide = Z5.tx_ide)";               
//               strSQL+=" LEFT OUTER JOIN (";
//                    strSQL+=" SELECT Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom, sum(VALMAS90) AS SUMVALMAS90 ";
//                    strSQL+=" FROM ( ";
//                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALMAS90 ";
//                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                        strSQL+="  INNER JOIN ";
//                        strSQL+="  ( select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
//                        strSQL+="    from tbm_detpag as x1";
//                        strSQL+="    inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                        strSQL+="    where x2.st_reg NOT IN ('E','I') ";
//                        strSQL+="    AND x2.fe_doc <= '"+strFecDoc+ "'";
//                        strSQL+="    group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag";
//                        strSQL+="  ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)";
//                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 AND a3.ne_mod in (1,3) ";
//                        strSQL+=strAux;
//                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                        strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) >=91 ";   ///---valmas90d---
//                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                        strSQL+=")";
//                        strSQL+="UNION ALL";
//                        strSQL+="( SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) AS VALMAS90 ";
//                        strSQL+="  FROM tbm_cabMovInv AS a1 ";
//                        strSQL+="  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
//                        strSQL+="  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
//                        strSQL+="  INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) ";
//                        ///strSQL+="  LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg) ";
//                        strSQL+="  WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) AND a2.nd_abo=0.00 ";
//                        strSQL+=strAux;
//                        strSQL+="  AND a1.fe_doc <= '"+strFecDoc+"'";
//                        strSQL+="  AND ('"+strFecDoc+"' - a2.fe_ven) >=91 ";   ///---valmas90d---
//                        strSQL+="  ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc ";
//                        strSQL+=")";
//                        strSQL+="UNION ALL";
//                        strSQL+="( SELECT p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag AS VALDOC, sum(p.valpagfecmay) as VALABO, (p.mo_pag+sum(p.valpagfecmay)) as VALMAS90";
//                        strSQL+="  FROM";
//                                strSQL+="( SELECT x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom, x4.mo_pag, case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay ";
//                                strSQL+=" FROM tbm_detpag as x1";
//                                strSQL+=" INNER JOIN (  ";
//                                        strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc ";
//                                        strSQL+=" from tbm_cabpag as a1";
//                                        strSQL+=" where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '"+strFecDoc+"' ";
//                                        strSQL+=") as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                                strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc) ";
//                                strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg) ";
//                                strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc) ";
//                                ///strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
//                                strSQL+=" INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
//                                strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
//                                //strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
//                                //strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
//                                strSQL+=FilSql();
//                                strSQL+=" AND ('"+strFecDoc+"' - x4.fe_ven) >= 91 ";   ///---valmas90d---
//                                strSQL+=" GROUP BY x2.co_emp, x2.co_loc, x3.co_cli, x6.tx_ide, x6.tx_nom,  x2.fe_doc, x4.mo_pag ";
//                                strSQL+=") as p ";
//                        strSQL+=" GROUP BY p.co_emp, p.co_loc, p.co_cli, p.tx_ide, p.tx_nom, p.mo_pag";
//                        strSQL+=")";
//                    strSQL+=" ) AS Q ";
//                    strSQL+=" GROUP BY Q.co_emp, Q.co_loc, Q.co_cli, Q.tx_ide, Q.tx_nom ";
//                    strSQL+=" ORDER BY Q.tx_nom, Q.co_emp ";
//               strSQL+=" ) AS Z6 ON (Z1.co_emp = Z6.co_emp AND Z1.tx_ide = Z6.tx_ide) ";
//               strSQL+=" GROUP BY Z1.co_emp, Z1.co_cli, Z1.tx_ide, Z1.tx_nom ";
//               strSQL+=" ORDER BY Z1.tx_nom, Z1.co_emp ";
//               System.out.println("---DetFacturas cargarDatFacCorFec() Mnu=645--: "+ strSQL);
//                
//               rstFac=stmFac.executeQuery(strSQL);
//                
//               //Limpiar vector de datos.
//               vecDatFac.clear();
//               //Obtener los registros.
//               lblMsgSis.setText("Cargando datos...");               
//               pgrSis.setMinimum(0);
//               pgrSis.setMaximum(intNumTotReg);
//               pgrSis.setValue(0);               
//               i=0;
//                
//               while (rstFac.next())
//               {
//                    if (blnCon)
//                    {
//                        vecReg=new Vector();
//                        vecReg.add(INT_TBL_DAT_FAC_LIN,"");///0
//                        vecReg.add(INT_TBL_DAT_FAC_COD_EMP,rstFac.getString("co_emp"));///1
//                        vecReg.add(INT_TBL_DAT_FAC_COD_CLI,rstFac.getString("co_cli"));///2
//                        vecReg.add(INT_TBL_DAT_FAC_RUC_CED,rstFac.getString("tx_ide"));///3
//                        vecReg.add(INT_TBL_DAT_FAC_NOM_CLI,rstFac.getString("tx_nom"));///3
//                        vecReg.add(INT_TBL_DAT_FAC_VAL_PEN,rstFac.getString("VALPEN"));///4                        
//                        vecReg.add(INT_TBL_DAT_FAC_VAL_VEN,rstFac.getString("VALXVEN"));///5
//                        vecReg.add(INT_TBL_DAT_FAC_VAL_30D,rstFac.getString("VAL30D"));///6
//                        vecReg.add(INT_TBL_DAT_FAC_VAL_60D,rstFac.getString("VAL60D"));///7
//                        vecReg.add(INT_TBL_DAT_FAC_VAL_90D,rstFac.getString("VAL90D"));///8
//                        vecReg.add(INT_TBL_DAT_FAC_VAL_MAS,rstFac.getString("VALMAS90D"));///9
//                        
//                        vecDatFac.add(vecReg);
//                        i++;
//                        ///pgrSis.setValue(i);
//                    }
//                    else
//                    {
//                        break;
//                    }
//                }
//                rstFac.close();
//                stmFac.close();
//                conFac.close();
//                rstFac=null;
//                stmFac=null;
//                conFac=null;
//                //Asignar vectores al modelo.
//                objTblModFac.setData(vecDatFac);
//                tblDat.setModel(objTblModFac);
//                vecDatFac.clear();
//                
//                //Calcular totales.
//                objTblTot.calcularTotales();
//                
//                if (intNumTotReg==tblDat.getRowCount())
//                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
//                else
//                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + objTblModFac.getRowCount() + ".");
//                
//                pgrSis.setValue(0);
//                pgrSis.setIndeterminate(false);
//                butCon.setText("Consultar");
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
//        return blnRes;
//    }
    
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */ ///cargarDatFacMovReg();
    private boolean cargarDetReg()
    {
        int intCodEmp, intCodLoc, intNumTotReg, i;
        
        int intNumDia; 
        String strFecSis, strFecIni, strFecSel, strFecVen, strRuc="";
        int intFecIni[];
        int intFecFin[];//para calcular los dias entre fechas
        int intFecSel[];//fecha seleccionada por el usuario//

        
        double dblSub, dblIva;
        java.util.Date datFecSer, datFecVen, datFecAux;
        
        boolean blnRes=true;
        try
        {
//            butCon.setText("Detener");
//            lblMsgSis.setText("Obteniendo datos...");
            
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                datFecAux=datFecSer;
                
                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");

                //Obtener la condición.
                strAux="";
                
                //Condicion para filtro por cliente///
                if (txtCodCli.getText().length()>0)
                {
                    strAux+=" AND a1.co_cli= " + txtCodCli.getText();
                }
//                else
//                {
                strRuc = objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_RUC_CED).toString();
                System.out.println("---strRuc: " + strRuc);
                
                ///if(objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_RUC_CED).toString()=="")
                if(strRuc.equals(""))
                ///strAux+=" AND a1.co_cli = " + objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_COD_CLI);
                ///strAux+=" AND a4.tx_ide = '" + objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_RUC_CED) + "'";
                    strAux+=" AND a4.co_grp = '" + objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_COD_CLI) + "'";
                else
                    strAux+=" AND a4.tx_ide = '" + objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_RUC_CED) + "'";

                
                ////////Condicion para filtro por cliente en un rango especifico///////
                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a4.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a4.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                
                if (!(txtCodGrpCli.getText().equals("")))
                {
                    strAux+=" AND a4.co_grp= " + txtCodGrpCli.getText();
                }

                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Condicion para filtro por empresa//
                    if (txtCodEmp.getText().length()>0)
                        strAux+=" AND a1.co_emp=" + txtCodEmp.getText();
                    else
                        strAux+=" AND a1.co_emp=" + objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP);
                }
                else
                {
                    strAux+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                    
                    if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                    {
                        //Condicion para filtro por cLocal
                        if (txtCodLoc.getText().length()>0)
                            strAux+=" AND a1.co_loc= " + txtCodLoc.getText();
//                        else
//                            strAux+=" AND a1.co_loc= " + objParSis.getCodigoLocal();
                    }
//                    else
//                        strAux+=" AND a1.co_loc= " + objParSis.getCodigoLocal();
                }
                
                ///FILTRO PARA MOSTRAR CREDITOS Y DEBITOS///
                if ( !(chkMosCre.isSelected() && chkMosDeb.isSelected()) )
                {
                    System.out.println("ENTRO POR FILTRO DE CREDITOS Y DEBITOS ");
                    if (chkMosCre.isSelected())
                        strAux+=" AND a3.tx_natDoc='I'";
                    else
                        strAux+=" AND a3.tx_natDoc='E'";
                }
                ///FILTRO PARA DOCUMENTOS VENCIDOS///
                if (chkMosDocVen.isSelected())
                {
                    System.out.println("ENTRO POR FILTRO DE DOCUMENTOS VENCIDOS ");
                    strFecVen=objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
                    strAux+=" AND a2.fe_ven<='" + strFecVen + "'";
                }
                ///FILTRO PARA MOSTRAR RETENCIONES///
                if (!chkMosRet.isSelected())
                {
                    System.out.println("ENTRO POR FILTRO DE MOSTRAR RETENCIONES");
                    strAux+=" AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
                }

                //Obtener el número total de registros.//
                strSQL="";
                strSQL+="SELECT COUNT (*)";
                strSQL+=" FROM (";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc";
                strSQL+=", a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, a2.mo_pag, a2.nd_abo, (a2.mo_pag+a2.nd_abo) AS nd_pen, a2.co_banChq, a5.tx_desLar AS a5_tx_desLar";
                strSQL+=", a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a2.nd_monChq";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";
                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                strSQL+=" AND a3.ne_mod in(1,3)";                
                ///strSQL+=" AND a1.co_emp="+ objParSis.getCodigoEmpresa();
                strSQL+=strAux;
                strSQL+=" ) AS b1";
                System.out.println("---Query EMPRESA--COUNT---cargarDetReg()--CxC53: "+ strSQL);

                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc";
                strSQL+=", a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, a2.mo_pag, a2.nd_abo, (a2.mo_pag+a2.nd_abo) AS nd_pen, a2.co_banChq, a5.tx_desLar AS a5_tx_desLar";
                strSQL+=", a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a2.nd_monChq";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";
                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                strSQL+=" AND a3.ne_mod in (1,3)";//modulo, para saber donde utilizo el tipo de doc
                ///strSQL+=" AND a1.co_emp="+ objParSis.getCodigoEmpresa();
                strSQL+=strAux;
                ///strSQL+=" ORDER BY a4.tx_nom, a1.co_tipdoc, a2.fe_ven";
                strSQL+=" ORDER BY a2.co_emp, a4.tx_nom, a2.co_loc, a2.co_tipDoc, a1.ne_numdoc, a2.co_reg";
                
                System.out.println("---Query EMPRESA--cargarDetReg()--CxC53: "+ strSQL);
                
                rst=stm.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
//                lblMsgSis.setText("Cargando datos...");
//                pgrSis.setMinimum(0);
//                pgrSis.setMaximum(intNumTotReg);
//                pgrSis.setValue(0);
                i=0;
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_RUC_CED,rst.getString("tx_ide"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nom"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_DEC_TIP_DOC,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DEL_TIP_DOC,rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_COD_REG,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("ne_numDoc"));
                        ///vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_DIA_CRE,rst.getString("ne_diaCre"));
                        datFecVen=rst.getDate("fe_ven");
                        ///vecReg.add(INT_TBL_DAT_FEC_VEN,datFecVen);
                        vecReg.add(INT_TBL_DAT_FEC_VEN,rst.getString("fe_ven"));
                        vecReg.add(INT_TBL_DAT_POR_RET,rst.getString("nd_porRet"));
                        vecReg.add(INT_TBL_DAT_VAL_DOC,rst.getString("mo_pag"));
                        vecReg.add(INT_TBL_DAT_VAL_ABO,rst.getString("nd_abo"));
                        vecReg.add(INT_TBL_DAT_VAL_PEN,rst.getString("nd_pen"));
                        try
                        {
                            int intdiaCre=Integer.parseInt(vecReg.get(INT_TBL_DAT_DIA_CRE).toString());
                            ///System.out.println("Los Dias de Creditos desde la tabla son ---> "+ intdiaCre);

                            //almaceno la fecha del sistema en un arreglo de enteros
                            intFecFin=dtpDat.getFecha(strFecSis);
//                            System.out.println("La FechaSist es: "+ strFecSis);
                            ///System.out.println("En numeros la FechaSist del Doc --intFecFin-- es: "+ intFecFin);

                            //tomo la fecha de la base y la almaceno en un arreglo como entero
                            strFecIni=objUti.formatearFecha(datFecVen,"dd/MM/yyyy");
                            intFecIni=dtpDat.getFecha(strFecIni);//(strFecIni);
//                            System.out.println("La FechaVenc del Doc es: "+ strFecIni);
                            ///System.out.println("En numeros la FechaVenc del Doc --intFecIni-- es: "+ intFecIni);

                            //// la funcion intCalDiaTra( año inicialFecVenc, mes inicialFecVenc, dia inicialFecVenc, 
                            //// año final FecActualSis, mes final FecActualSis, dia final FecActualSis)
                            ////calcula los dias transcurridos entre 2 fechas
                            intNumDia=intCalDiaTra(intFecIni[2], intFecIni[1], intFecIni[0], intFecFin[2], intFecFin[1], intFecFin[0]);
//                            System.out.println("---> Los Dias de Diferencias entre FechaVen " + strFecIni + "y FechaSist " + strFecSis + " es ---> "+ intNumDia);
                            //ubico el valor vencido en el rango correspondiente
                            if(intNumDia<=0){
                                vecReg.add(INT_TBL_DAT_VAL_VEN,rst.getString("nd_pen"));
                                vecReg.add(INT_TBL_DAT_VAL_30D,null);
                                vecReg.add(INT_TBL_DAT_VAL_60D,null);
                                vecReg.add(INT_TBL_DAT_VAL_90D,null);
                                vecReg.add(INT_TBL_DAT_VAL_MAS,null);
                            }else
                            {
                                if(intNumDia>=0 && intNumDia<=30){
                                //if(intdiaCre>=0 && intdiaCre<=30){
                                    vecReg.add(INT_TBL_DAT_VAL_VEN,null);
                                    vecReg.add(INT_TBL_DAT_VAL_30D,rst.getString("nd_pen"));
                                    vecReg.add(INT_TBL_DAT_VAL_60D,null);
                                    vecReg.add(INT_TBL_DAT_VAL_90D,null);
                                    vecReg.add(INT_TBL_DAT_VAL_MAS,null);
                                }else
                                {
                                    if(intNumDia>=31 && intNumDia<=60){
                                    //if(intdiaCre>=31 && intdiaCre<=60){
                                        vecReg.add(INT_TBL_DAT_VAL_VEN,null);
                                        vecReg.add(INT_TBL_DAT_VAL_30D,null);
                                        vecReg.add(INT_TBL_DAT_VAL_60D,rst.getString("nd_pen"));
                                        vecReg.add(INT_TBL_DAT_VAL_90D,null);
                                        vecReg.add(INT_TBL_DAT_VAL_MAS,null);
                                    }else
                                    {
                                        if(intNumDia>=61 && intNumDia<=90){
                                        ///if(intdiaCre>=61 && intdiaCre<=90){
                                            vecReg.add(INT_TBL_DAT_VAL_VEN,null);
                                            vecReg.add(INT_TBL_DAT_VAL_30D,null);
                                            vecReg.add(INT_TBL_DAT_VAL_60D,null);
                                            vecReg.add(INT_TBL_DAT_VAL_90D,rst.getString("nd_pen"));
                                            vecReg.add(INT_TBL_DAT_VAL_MAS,null);
                                        }else{
                                            vecReg.add(INT_TBL_DAT_VAL_VEN,null);
                                            vecReg.add(INT_TBL_DAT_VAL_30D,null);
                                            vecReg.add(INT_TBL_DAT_VAL_60D,null);
                                            vecReg.add(INT_TBL_DAT_VAL_90D,null);
                                            vecReg.add(INT_TBL_DAT_VAL_MAS,rst.getString("nd_pen"));
                                        }
                                    }
                                }
                            }
                        }
                        catch (Exception e)
                        {
                            objUti.mostrarMsgErr_F1(this, e);
                        }

                        vecReg.add(INT_TBL_DAT_COD_BAN,rst.getString("co_banChq"));
                        vecReg.add(INT_TBL_DAT_NOM_BAN,rst.getString("a5_tx_desLar"));
                        vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_numCtaChq"));
                        vecReg.add(INT_TBL_DAT_NUM_CHQ,rst.getString("tx_numChq"));
                        vecReg.add(INT_TBL_DAT_FEC_REC_CHQ,rst.getString("fe_recChq"));
                        vecReg.add(INT_TBL_DAT_FEC_VEN_CHQ,rst.getString("fe_venChq"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_VAL_CHQ,rst.getString("nd_monChq"));
                        vecDat.add(vecReg);
                        i++;
//                        pgrSis.setValue(i);
                    }
                    else
                    {
                        break;
                    }
                }
                
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDatFacMovReg.setModel(objTblMod);
                vecDat.clear();
                
                //Calcular totales.
                objTotDatMovFac.calcularTotales();
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */ ///cargarDatFacMovReg();
    private boolean cargarDetRegGrp()
    {
        int intCodEmpGrp, intCodEmp, intCodLoc, intNumTotReg, i;
        
        int intNumDia; 
        String strFecSis, strFecIni, strFecSel, strFecVen, strRuc="";
        int intFecIni[];
        int intFecFin[];//para calcular los dias entre fechas
        int intFecSel[];//fecha seleccionada por el usuario//

        
        double dblSub, dblIva;
        java.util.Date datFecSer, datFecVen, datFecAux;
        
        boolean blnRes=true;
        try
        {
//            butCon.setText("Detener");
//            lblMsgSis.setText("Obteniendo datos...");   ///chkDatFacMosMovReg///
            
            intCodEmpGrp=objParSis.getCodigoEmpresaGrupo();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.//
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                datFecAux=datFecSer;
                
                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");

                //Obtener la condición.//
                strAux="";
                
                //Condicion para filtro por cliente///
                if (txtCodCli.getText().length()>0)
                {
                    strAux+=" AND a1.co_cli= " + txtCodCli.getText();
                }
                
                strRuc = objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_RUC_CED).toString();
                System.out.println("---strRuc: " + strRuc);
                                    
                ///if(objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_RUC_CED).toString()=="")
                if(strRuc.equals(""))
                ///strAux+=" AND a1.co_cli = " + objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_COD_CLI);
                ///strAux+=" AND a4.tx_ide = '" + objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_RUC_CED) + "'";
                    strAux+=" AND a4.co_grp = '" + objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_COD_CLI) + "'";
                else
                    strAux+=" AND a4.tx_ide = '" + objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_RUC_CED) + "'";

                
                ////////Condicion para filtro por cliente en un rango especifico///////
                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a4.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a4.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                
                if (!(txtCodGrpCli.getText().equals("")))
                {
                    strAux+=" AND a4.co_grp= " + txtCodGrpCli.getText();
                }

                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Condicion para filtro por empresa//
                    if (txtCodEmp.getText().length()>0)
                        strAux+=" AND a1.co_emp=" + txtCodEmp.getText();
                    else
                        strAux+=" AND a1.co_emp IN (1,2,3,4) ";
                }
                else
                {
                    strAux+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                    
                    if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                    {
                        //Condicion para filtro por cLocal
                        if (txtCodLoc.getText().length()>0)
                            strAux+=" AND a1.co_loc= " + txtCodLoc.getText();
//                        else
//                            strAux+=" AND a1.co_loc= " + objParSis.getCodigoLocal();
                    }
//                    else
//                        strAux+=" AND a1.co_loc= " + objParSis.getCodigoLocal();
                }
                
                ///FILTRO PARA MOSTRAR CREDITOS Y DEBITOS///
                if ( !(chkMosCre.isSelected() && chkMosDeb.isSelected()) )
                {
                    System.out.println("ENTRO POR FILTRO DE CREDITOS Y DEBITOS ");
                    if (chkMosCre.isSelected())
                        strAux+=" AND a3.tx_natDoc='I'";
                    else
                        strAux+=" AND a3.tx_natDoc='E'";
                }
                ///FILTRO PARA DOCUMENTOS VENCIDOS///
                if (chkMosDocVen.isSelected())
                {
                    System.out.println("ENTRO POR FILTRO DE DOCUMENTOS VENCIDOS ");
                    strFecVen=objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
                    strAux+=" AND a2.fe_ven<='" + strFecVen + "'";
                }
                ///FILTRO PARA MOSTRAR RETENCIONES///
                if (!chkMosRet.isSelected())
                {
                    System.out.println("ENTRO POR FILTRO DE MOSTRAR RETENCIONES");
                    strAux+=" AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
                }

                //Obtener el número total de registros.//
                strSQL="";
                strSQL+="SELECT COUNT (*)";
                strSQL+=" FROM (";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc";
                strSQL+=", a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, a2.mo_pag, a2.nd_abo, (a2.mo_pag+a2.nd_abo) AS nd_pen, a2.co_banChq, a5.tx_desLar AS a5_tx_desLar";
                strSQL+=", a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a2.nd_monChq";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";
                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                strSQL+=" AND a3.ne_mod in(1,3)";                
                ///strSQL+=" AND a1.co_emp="+ objParSis.getCodigoEmpresa();
                strSQL+=strAux;
                strSQL+=" ) AS b1";
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_ide, a4.tx_nom, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc";
                strSQL+=", a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, a2.mo_pag, a2.nd_abo, (a2.mo_pag+a2.nd_abo) AS nd_pen, a2.co_banChq, a5.tx_desLar AS a5_tx_desLar";
                strSQL+=", a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a2.nd_monChq";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";
                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                strSQL+=" AND a3.ne_mod in (1,3)";//modulo, para saber donde utilizo el tipo de doc
                ///strSQL+=" AND a1.co_emp="+ objParSis.getCodigoEmpresa();
                strSQL+=strAux;
                ///strSQL+=" ORDER BY a4.tx_nom, a1.co_tipdoc, a2.fe_ven";
                strSQL+=" ORDER BY a2.co_emp, a4.tx_nom, a2.co_loc, a2.co_tipDoc, a1.ne_numdoc, a2.co_reg";
                
                System.out.println("---Query GRUPO--cargarDetRegGrp()--CxC53: "+ strSQL);
                
                rst=stm.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
//                lblMsgSis.setText("Cargando datos...");
//                pgrSis.setMinimum(0);
//                pgrSis.setMaximum(intNumTotReg);
//                pgrSis.setValue(0);
                i=0;
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_RUC_CED,rst.getString("tx_ide"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nom"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_DEC_TIP_DOC,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DEL_TIP_DOC,rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_COD_REG,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("ne_numDoc"));
                        ///vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_DIA_CRE,rst.getString("ne_diaCre"));
                        datFecVen=rst.getDate("fe_ven");
                        ///vecReg.add(INT_TBL_DAT_FEC_VEN,datFecVen);
                        vecReg.add(INT_TBL_DAT_FEC_VEN,rst.getString("fe_ven"));
                        vecReg.add(INT_TBL_DAT_POR_RET,rst.getString("nd_porRet"));
                        vecReg.add(INT_TBL_DAT_VAL_DOC,rst.getString("mo_pag"));
                        vecReg.add(INT_TBL_DAT_VAL_ABO,rst.getString("nd_abo"));
                        vecReg.add(INT_TBL_DAT_VAL_PEN,rst.getString("nd_pen"));
                        try
                        {
                            int intdiaCre=Integer.parseInt(vecReg.get(INT_TBL_DAT_DIA_CRE).toString());
                            ///System.out.println("Los Dias de Creditos desde la tabla son ---> "+ intdiaCre);

                            //almaceno la fecha del sistema en un arreglo de enteros
                            intFecFin=dtpDat.getFecha(strFecSis);
//                            System.out.println("La FechaSist es: "+ strFecSis);
                            ///System.out.println("En numeros la FechaSist del Doc --intFecFin-- es: "+ intFecFin);

                            //tomo la fecha de la base y la almaceno en un arreglo como entero
                            strFecIni=objUti.formatearFecha(datFecVen,"dd/MM/yyyy");
                            intFecIni=dtpDat.getFecha(strFecIni);//(strFecIni);
//                            System.out.println("La FechaVenc del Doc es: "+ strFecIni);
                            ///System.out.println("En numeros la FechaVenc del Doc --intFecIni-- es: "+ intFecIni);

                            //// la funcion intCalDiaTra( año inicialFecVenc, mes inicialFecVenc, dia inicialFecVenc, 
                            //// año final FecActualSis, mes final FecActualSis, dia final FecActualSis)
                            ////calcula los dias transcurridos entre 2 fechas
                            intNumDia=intCalDiaTra(intFecIni[2], intFecIni[1], intFecIni[0], intFecFin[2], intFecFin[1], intFecFin[0]);
//                            System.out.println("---> Los Dias de Diferencias entre FechaVen " + strFecIni + "y FechaSist " + strFecSis + " es ---> "+ intNumDia);
                            //ubico el valor vencido en el rango correspondiente
                            if(intNumDia<=0){
                                vecReg.add(INT_TBL_DAT_VAL_VEN,rst.getString("nd_pen"));
                                vecReg.add(INT_TBL_DAT_VAL_30D,null);
                                vecReg.add(INT_TBL_DAT_VAL_60D,null);
                                vecReg.add(INT_TBL_DAT_VAL_90D,null);
                                vecReg.add(INT_TBL_DAT_VAL_MAS,null);
                            }else
                            {
                                if(intNumDia>=0 && intNumDia<=30){
                                //if(intdiaCre>=0 && intdiaCre<=30){
                                    vecReg.add(INT_TBL_DAT_VAL_VEN,null);
                                    vecReg.add(INT_TBL_DAT_VAL_30D,rst.getString("nd_pen"));
                                    vecReg.add(INT_TBL_DAT_VAL_60D,null);
                                    vecReg.add(INT_TBL_DAT_VAL_90D,null);
                                    vecReg.add(INT_TBL_DAT_VAL_MAS,null);
                                }else
                                {
                                    if(intNumDia>=31 && intNumDia<=60){
                                    //if(intdiaCre>=31 && intdiaCre<=60){
                                        vecReg.add(INT_TBL_DAT_VAL_VEN,null);
                                        vecReg.add(INT_TBL_DAT_VAL_30D,null);
                                        vecReg.add(INT_TBL_DAT_VAL_60D,rst.getString("nd_pen"));
                                        vecReg.add(INT_TBL_DAT_VAL_90D,null);
                                        vecReg.add(INT_TBL_DAT_VAL_MAS,null);
                                    }else
                                    {
                                        if(intNumDia>=61 && intNumDia<=90){
                                        ///if(intdiaCre>=61 && intdiaCre<=90){
                                            vecReg.add(INT_TBL_DAT_VAL_VEN,null);
                                            vecReg.add(INT_TBL_DAT_VAL_30D,null);
                                            vecReg.add(INT_TBL_DAT_VAL_60D,null);
                                            vecReg.add(INT_TBL_DAT_VAL_90D,rst.getString("nd_pen"));
                                            vecReg.add(INT_TBL_DAT_VAL_MAS,null);
                                        }else{
                                            vecReg.add(INT_TBL_DAT_VAL_VEN,null);
                                            vecReg.add(INT_TBL_DAT_VAL_30D,null);
                                            vecReg.add(INT_TBL_DAT_VAL_60D,null);
                                            vecReg.add(INT_TBL_DAT_VAL_90D,null);
                                            vecReg.add(INT_TBL_DAT_VAL_MAS,rst.getString("nd_pen"));
                                        }
                                    }
                                }
                            }
                        }
                        catch (Exception e)
                        {
                            objUti.mostrarMsgErr_F1(this, e);
                        }

                        vecReg.add(INT_TBL_DAT_COD_BAN,rst.getString("co_banChq"));
                        vecReg.add(INT_TBL_DAT_NOM_BAN,rst.getString("a5_tx_desLar"));
                        vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_numCtaChq"));
                        vecReg.add(INT_TBL_DAT_NUM_CHQ,rst.getString("tx_numChq"));
                        vecReg.add(INT_TBL_DAT_FEC_REC_CHQ,rst.getString("fe_recChq"));
                        vecReg.add(INT_TBL_DAT_FEC_VEN_CHQ,rst.getString("fe_venChq"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_VAL_CHQ,rst.getString("nd_monChq"));
                        vecDat.add(vecReg);
                        i++;
//                        pgrSis.setValue(i);
                    }
                    else
                    {
                        break;
                    }
                }
                
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDatFacMovReg.setModel(objTblMod);
                vecDat.clear();
                
                //Calcular totales.
                objTotDatMovFac.calcularTotales();
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
//     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
//     * @return true: Si se pudo consultar los registros.
//     * <BR>false: En el caso contrario.
//     */
//    private boolean cargarDetRegCorFec()
//    {
//        int intCodEmp, intCodLoc, intNumTotReg, i;
//        
//        int intNumDia; 
//        String strFecSis, strFecIni, strFecSel, strFecVen, strFecDoc;
//        int intFecIni[];
//        int intFecFin[];//para calcular los dias entre fechas
//        int intFecSel[];//fecha seleccionada por el usuario//
//
//        
//        double dblSub, dblIva;
//        java.util.Date datFecSer, datFecVen, datFecAux, datFacDoc;
//        
//        boolean blnRes=true;
//        try
//        {
//            ///butCon.setText("Detener");
//            ///lblMsgSis.setText("Obteniendo datos...");
//            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
//            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
//            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            if (con!=null)
//            {
//                stm=con.createStatement();
//                //Obtener la fecha del servidor.
//                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//                if (datFecSer==null)
//                    return false;
//                datFecAux=datFecSer;
//                
//                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
//
//                //Obtener la condición.
//                strAux="";                
//
//                //Condicion para filtro por cLocal
//                if (txtCodLoc.getText().length()>0)
//                {
//                    strAux+=" AND a1.co_loc= " + txtCodLoc.getText();                    
//                }
//                
//                //Condicion para filtro por cliente
//                if (txtCodCli.getText().length()>0)
//                {
//                    strAux+=" AND a1.co_cli= " + txtCodCli.getText();
//                }
//                //Condicion para filtro por cliente
//                if(optTod.isSelected())
//                {
//                    ///strAux+=" AND a1.co_cli=" + objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_COD_CLI);
//                    strAux+=" AND a4.tx_ide = '" + objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_RUC_CED) + "'";
//                }
//                
//                ////////Condicion para filtro por cliente en un rango especifico
//                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
//                    strAux+=" AND ((LOWER(a4.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a4.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
//                
//                if (!(txtDesCorTipDoc.getText().equals("")))
//                {
//                    strAux+=" AND a1.co_tipdoc= " + txtCodTipDoc.getText();
//                }
//
//                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
//                {
//                    //Condicion para filtro por empresa
//                    if (txtCodEmp.getText().length()>0)
//                        strAux+=" AND a1.co_emp=" + txtCodEmp.getText();
//                }
//                else
//                {
//                    strAux+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
//                }
//                
//                ///FILTRO PARA MOSTRAR CREDITOS Y DEBITOS///
//                if ( !(chkMosCre.isSelected() && chkMosDeb.isSelected()) )
//                {
//                    System.out.println("ENTRO POR FILTRO DE CREDITOS Y DEBITOS ");
//                    if (chkMosCre.isSelected())
//                        strAux+=" AND a3.tx_natDoc='I'";
//                    else
//                        strAux+=" AND a3.tx_natDoc='E'";
//                }
//                ///FILTRO PARA DOCUMENTOS VENCIDOS///
//                if (chkMosDocVen.isSelected())
//                {
//                    System.out.println("ENTRO POR FILTRO DE DOCUMENTOS VENCIDOS ");
//                    strFecVen=objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
//                    strAux+=" AND a2.fe_ven<='" + strFecVen + "'";
//                }
//                ///FILTRO PARA MOSTRAR RETENCIONES///
//                if (!chkMosRet.isSelected())
//                {
//                    System.out.println("ENTRO POR FILTRO DE MOSTRAR RETENCIONES");
//                    strAux+=" AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
//                }
//                
//                ///strFecDoc=objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy");
//                strFecDoc=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "yyyy/MM/dd");
//                String strFecDocAux = objUti.formatearFecha(strFecDoc,"yyyy/MM/dd","dd/MM/yyyy");
//                System.out.println("---La Fecha Corte es: "+ strFecDoc);
//                System.out.println("---La Fecha Corte FORMATEADA es: "+ strFecDocAux);
//                ///FILTRO PARA FECHA DE CORTE///
//                if(dtpFecDoc.isFecha())
//                {
//                    System.out.println("ENTRO POR FILTRO DE FECHA DE CORTE");
//                    ///strFecDoc=objUti.formatearFecha(dtpFecDoc.getText(), "yyyy-MM-dd", objParSis.getFormatoFechaBaseDatos());
//                    strAux+=" AND a1.fe_doc<='" + strFecDoc + "'";
//                }
//                
//                //Obtener el número total de registros.
//                strSQL="";
//                strSQL+="SELECT COUNT (*)";
//                strSQL+=" FROM (";
//                strSQL+=" (SELECT a1.co_emp AS EMP, a1.co_loc AS LOC, a1.co_cli AS CLI, a4.tx_ide AS RUCCED, a4.tx_nom AS NOM, a1.co_tipDoc AS TIPDOC, a3.tx_desCor AS DESCOR, a3.tx_desLar AS DESLAR, a1.co_doc AS CODDOC, ";
//                strSQL+=" a2.co_reg AS CODREG, a1.ne_numDoc AS NUMFAC, a1.fe_doc AS FECDOC, a2.ne_diaCre AS DIACRE, a2.fe_ven AS FECVEN, a2.nd_porRet AS PORRET, a2.st_reg AS ESTREG, ";
//                strSQL+=" a2.mo_pag AS VALDOC, a2.nd_abo AS ABONO, (a2.mo_pag+a2.nd_abo) AS PENDIENTE, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALPEN, ";
//                strSQL+=" a2.co_banChq, a5.tx_desLar AS X6_TX_DESLAR, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a2.nd_monChq, a3.tx_natdoc AS NATDOC ";
//                strSQL+=" FROM tbm_cabMovInv AS a1";
//                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
//                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";
//                strSQL+=" INNER JOIN (";
//                strSQL+=" select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
//                strSQL+=" from tbm_detpag as x1";
//                strSQL+=" inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                strSQL+=" where x2.st_reg NOT IN ('E','I') ";
//                strSQL+=" AND x2.fe_doc <= '" +strFecDoc+ "'";
//                strSQL+=" group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag ";
//                strSQL+=" ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag) ";
//                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                strSQL+=" AND a2.st_reg IN ('A','C')";
//                ////strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                strSQL+=" AND (a2.mo_pag+a6.sumabodet)<>0";
//                strSQL+=" AND a3.ne_mod in (1,3)";//modulo, para saber donde utilizo el tipo de doc
//                ///strSQL+=" AND a1.co_emp="+ objParSis.getCodigoEmpresa();
//                strSQL+=strAux;
//                strSQL+=" ORDER BY a3.tx_natdoc, a4.tx_nom, a1.co_tipdoc, a2.fe_ven)";
//                
//                strSQL+=" UNION ALL";
//                
//                strSQL+=" (SELECT a1.co_emp AS EMP, a1.co_loc AS LOC, a1.co_cli AS CLI, a4.tx_ide AS RUCCED, a4.tx_nom AS NOM, a1.co_tipDoc AS TIPDOC, a3.tx_desCor AS DESCOR, a3.tx_desLar AS DESLAR, a1.co_doc AS CODDOC, ";
//                strSQL+=" a2.co_reg AS CODREG, a1.ne_numDoc AS NUMFAC, a1.fe_doc AS FECDOC, a2.ne_diaCre AS DICRE, a2.fe_ven AS FECVEN, a2.nd_porRet AS PORRET, a2.st_reg AS ESTREG, ";
//                strSQL+=" a2.mo_pag AS VALDOC, a2.nd_abo AS ABONO, (a2.mo_pag+a2.nd_abo) AS PENDIENTE, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) as VALPEN, ";
//                strSQL+=" a2.co_banChq, a5.tx_desLar AS X6_TX_DESLAR, a2.tx_numCtaChq, a2.tx_numChq,  a2.fe_recChq, a2.fe_venChq, a2.nd_monChq, a3.tx_natdoc AS NATDOC ";
//                strSQL+=" FROM tbm_cabMovInv AS a1";
//                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
//                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";                
//                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                strSQL+=" AND a2.st_reg IN ('A','C')";
//                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                ///strSQL+=" AND (a2.mo_pag+a6.sumabodet)<>0";
//                strSQL+=" AND a3.ne_mod in (1,3)";//modulo, para saber donde utilizo el tipo de doc
//                strSQL+=" AND a2.nd_abo=0.00 ";
//                ///strSQL+=" AND a1.co_emp="+ objParSis.getCodigoEmpresa();
//                strSQL+=strAux;
//                strSQL+=" ORDER BY a3.tx_natdoc, a4.tx_nom, a1.co_tipdoc, a2.fe_ven)";
//                
//                strSQL+="UNION ALL";
//                
//                strSQL+=" ( SELECT q.co_emp AS EMP, q.co_locpag AS LOC, q.co_cli AS CLI, q.tx_ide AS RUCCED, q.tx_nomcli AS NOM, q.co_tipdocpag AS TIPDOC, q.tx_descor AS DESCOR, q.tx_deslar AS DESLAR, q.co_docpag AS CODDOC, ";
//                strSQL+=" q.CODREG, q.numfac as NUMFAC, q.FECDOC, q.ne_diacre AS DIACRE, q.FECVEN, q.nd_porRet AS PORRET, q.ESTREG, ";
//                strSQL+=" q.mo_pag AS VALDOC, sum(q.valpagfecmay) as ABONO, (q.mo_pag+sum(q.valpagfecmay)) as PENDIENTE, sum(q.valpagfecmay) as VALABO, (q.mo_pag+sum(q.valpagfecmay)) as VALPEN, ";
//                strSQL+=" q.co_banChq, q.X6_TX_DESLAR, q.tx_numCtaChq, q.tx_numChq, q.fe_recChq, q.fe_venChq, q.nd_monChq, q.NATDOC ";
//                strSQL+="FROM (";
//                    strSQL+=" SELECT x2.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag,  x3.co_cli, x7.tx_ide, x3.tx_nomcli, x5.tx_descor, x5.tx_deslar, x4.fe_ven  AS FECVEN, x4.nd_porret, x4.co_reg AS CODREG, x4.ne_diacre, ";
//                    strSQL+=" x4.co_banChq, x6.tx_desLar AS X6_TX_DESLAR, x4.tx_numCtaChq, x4.tx_numChq, x4.fe_recChq, x4.fe_venChq, x4.nd_monChq, x5.tx_natdoc AS NATDOC, x3.st_reg AS ESTREG, ";
//                    strSQL+=" sum(x1.nd_abo) as abodetpag, x3.ne_numdoc as NumFac, x3.fe_doc as FECDOC, x4.mo_pag, x4.nd_abo, (x4.mo_pag+x4.nd_abo)  as saldo, ";
//                    strSQL+=" case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay";
//                    strSQL+=" FROM tbm_detpag as x1";
//                    strSQL+=" INNER JOIN ( ";
//                                    strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc";
//                                    strSQL+=" from tbm_cabpag as a1 ";
//                                    strSQL+=" where a1.st_reg NOT IN ('E', 'I')";
//                                    strSQL+=" and a1.fe_doc >= '"+strFecDoc+"' ";
//                    strSQL+=" ) as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc)";
//                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc)";
//                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg)";
//                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc)";
//                    strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
//                    strSQL+=" INNER JOIN tbm_cli AS x7 ON (x3.co_emp=x7.co_emp and x3.co_cli=x7.co_cli)";
//                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
//                    strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
//                    ///strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
//                    strSQL+=" AND x7.tx_ide = '" + objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_RUC_CED) + "'";
//                    strSQL+=FilSql();
//                    strSQL+=" ";
//                    strSQL+=" GROUP BY x2.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x3.co_cli, x7.tx_ide, x3.tx_nomcli, x5.tx_descor, x2.fe_doc, x4.fe_ven, x4.nd_porret, x4.co_reg, x4.ne_diacre, x3.st_reg, ";
//                    strSQL+=" x4.co_banChq, x6.tx_desLar, x4.tx_numCtaChq, x4.tx_numChq, x4.fe_recChq, x4.fe_venChq, x4.nd_monChq, x5.tx_natdoc, x3.ne_numdoc, x3.fe_doc, x4.mo_pag, x4.nd_abo, x5.tx_deslar ";
//                    strSQL+=" ORDER BY x1.co_tipdocpag, x3.ne_numdoc ";
//                strSQL+=" ) as q ";
//                strSQL+=" GROUP BY q.co_emp, q.co_locpag, q.co_tipdocpag, q.co_docpag, q.numfac, q.mo_pag, q.FECDOC, q.co_cli, q.tx_ide, q.tx_nomcli, q.tx_descor, q.tx_deslar, q.FECVEN, q.nd_porret, q.ne_diacre, ";
//                strSQL+=" q.CODREG, q.ESTREG, q.co_banChq, q.X6_TX_DESLAR, q.tx_numCtaChq, q.tx_numChq, q.fe_recChq, q.fe_venChq, q.nd_monChq, q.NATDOC )";                
//                strSQL+=" ) AS b1";
//                
//                System.out.println("---Query de CANTIDAD registros Consultados --cargarDetRegCorFec()--CxC16: "+ strSQL);
//                
//                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
//                if (intNumTotReg==-1)
//                    return false;                
//          
//                //Armar la sentencia SQL.
//                strSQL="";
//                strSQL+=" SELECT *";
//                strSQL+=" FROM (";
//                ///---query donde la suma del abono en tbm_detpag es igual al abono en tbm_pagmovinv por cada documento---
//                strSQL+=" (SELECT a1.co_emp AS EMP, a1.co_loc AS LOC, a1.co_cli AS CLI, a4.tx_ide AS RUCCED, a4.tx_nom AS NOM, a1.co_tipDoc AS TIPDOC, a3.tx_desCor AS DESCOR, a3.tx_desLar AS DESLAR, a1.co_doc AS CODDOC, ";
//                strSQL+=" a2.co_reg AS CODREG, a1.ne_numDoc AS NUMFAC, a1.fe_doc AS FECDOC, a2.ne_diaCre AS DIACRE, a2.fe_ven AS FECVEN, a2.nd_porRet AS PORRET, a2.st_reg AS ESTREG, ";
//                strSQL+=" a2.mo_pag AS VALDOC, a2.nd_abo AS ABONO, (a2.mo_pag+a2.nd_abo) AS PENDIENTE, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALPEN, ";
//                strSQL+=" a2.co_banChq, a5.tx_desLar AS X6_TX_DESLAR, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a2.nd_monChq, a3.tx_natdoc AS NATDOC ";
//                strSQL+=" FROM tbm_cabMovInv AS a1";
//                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
//                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";
//                strSQL+=" INNER JOIN (";
//                strSQL+=" select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
//                strSQL+=" from tbm_detpag as x1";
//                strSQL+=" inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                strSQL+=" where x2.st_reg NOT IN ('E','I') ";
//                strSQL+=" AND x2.fe_doc <= '" +strFecDoc+ "'";
//                strSQL+=" group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag ";
//                strSQL+=" ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag) ";
//                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                strSQL+=" AND a2.st_reg IN ('A','C')";
//                ////strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                strSQL+=" AND (a2.mo_pag+a6.sumabodet)<>0";
//                strSQL+=" AND a3.ne_mod in (1,3)";//modulo, para saber donde utilizo el tipo de doc
//                ///strSQL+=" AND a1.co_emp="+ objParSis.getCodigoEmpresa();
//                strSQL+=strAux;
//                strSQL+=" ORDER BY a3.tx_natdoc, a4.tx_nom, a1.co_tipdoc, a2.fe_ven)";
//                
//                strSQL+=" UNION ALL";
//                
//                strSQL+=" (SELECT a1.co_emp AS EMP, a1.co_loc AS LOC, a1.co_cli AS CLI, a4.tx_ide AS RUCCED, a4.tx_nom AS NOM, a1.co_tipDoc AS TIPDOC, a3.tx_desCor AS DESCOR, a3.tx_desLar AS DESLAR, a1.co_doc AS CODDOC, ";
//                strSQL+=" a2.co_reg AS CODREG, a1.ne_numDoc AS NUMFAC, a1.fe_doc AS FECDOC, a2.ne_diaCre AS DICRE, a2.fe_ven AS FECVEN, a2.nd_porRet AS PORRET, a2.st_reg AS ESTREG, ";
//                strSQL+=" a2.mo_pag AS VALDOC, a2.nd_abo AS ABONO, (a2.mo_pag+a2.nd_abo) AS PENDIENTE, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) as VALPEN, ";
//                strSQL+=" a2.co_banChq, a5.tx_desLar AS X6_TX_DESLAR, a2.tx_numCtaChq, a2.tx_numChq,  a2.fe_recChq, a2.fe_venChq, a2.nd_monChq, a3.tx_natdoc AS NATDOC ";
//                strSQL+=" FROM tbm_cabMovInv AS a1";
//                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
//                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";                
//                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                strSQL+=" AND a2.st_reg IN ('A','C')";
//                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                ///strSQL+=" AND (a2.mo_pag+a6.sumabodet)<>0";
//                strSQL+=" AND a3.ne_mod in (1,3)";//modulo, para saber donde utilizo el tipo de doc
//                strSQL+=" AND a2.nd_abo=0.00 ";
//                ///strSQL+=" AND a1.co_emp="+ objParSis.getCodigoEmpresa();
//                strSQL+=strAux;
//                strSQL+=" ORDER BY a3.tx_natdoc, a4.tx_nom, a1.co_tipdoc, a2.fe_ven)";
//                
//                strSQL+="UNION ALL";
//             
//                strSQL+=" ( SELECT q.co_emp AS EMP, q.co_locpag AS LOC, q.co_cli AS CLI, q.tx_ide AS RUCCED, q.tx_nomcli AS NOM, q.co_tipdocpag AS TIPDOC, q.tx_descor AS DESCOR, q.tx_deslar AS DESLAR, q.co_docpag AS CODDOC, ";
//                strSQL+=" q.CODREG, q.numfac as NUMFAC, q.FECDOC, q.ne_diacre AS DIACRE, q.FECVEN, q.nd_porRet AS PORRET, q.ESTREG, ";
//                strSQL+=" q.mo_pag AS VALDOC, sum(q.valpagfecmay) as ABONO, (q.mo_pag+sum(q.valpagfecmay)) as PENDIENTE, sum(q.valpagfecmay) as VALABO, (q.mo_pag+sum(q.valpagfecmay)) as VALPEN, ";
//                strSQL+=" q.co_banChq, q.X6_TX_DESLAR, q.tx_numCtaChq, q.tx_numChq, q.fe_recChq, q.fe_venChq, q.nd_monChq, q.NATDOC ";
//                strSQL+="FROM (";
//                    strSQL+=" SELECT x2.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag,  x3.co_cli, x7.tx_ide, x3.tx_nomcli, x5.tx_descor, x5.tx_deslar, x4.fe_ven  AS FECVEN, x4.nd_porret, x4.co_reg AS CODREG, x4.ne_diacre, ";
//                    strSQL+=" x4.co_banChq, x6.tx_desLar AS X6_TX_DESLAR, x4.tx_numCtaChq, x4.tx_numChq, x4.fe_recChq, x4.fe_venChq, x4.nd_monChq, x5.tx_natdoc AS NATDOC, x3.st_reg AS ESTREG, ";
//                    strSQL+=" sum(x1.nd_abo) as abodetpag, x3.ne_numdoc as NumFac, x3.fe_doc as FECDOC, x4.mo_pag, x4.nd_abo, (x4.mo_pag+x4.nd_abo)  as saldo, ";
//                    strSQL+=" case when x2.fe_doc >='"+strFecDoc+"' then 0.00 end as valpagfecmay";
//                    strSQL+=" FROM tbm_detpag as x1";
//                    strSQL+=" INNER JOIN ( ";
//                                    strSQL+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc";
//                                    strSQL+=" from tbm_cabpag as a1 ";
//                                    strSQL+=" where a1.st_reg NOT IN ('E', 'I')";
//                                    strSQL+=" and a1.fe_doc >= '"+strFecDoc+"' ";
//                    strSQL+=" ) as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc)";
//                    strSQL+=" INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc)";
//                    strSQL+=" INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg)";
//                    strSQL+=" INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc)";
//                    strSQL+=" LEFT OUTER JOIN tbm_var AS x6 ON (x4.co_banChq=x6.co_reg)";
//                    strSQL+=" INNER JOIN tbm_cli AS x7 ON (x3.co_emp=x7.co_emp and x3.co_cli=x7.co_cli)";
//                    strSQL+=" WHERE x3.fe_doc <= '"+strFecDoc+"'";
//                    strSQL+=" AND x3.co_emp = " + objParSis.getCodigoEmpresa();
//                    ///strSQL+=" AND x3.co_cli = " + txtCodCli.getText();
//                    strSQL+=" AND x7.tx_ide = '" + objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_RUC_CED) + "'";
//                    strSQL+=FilSql();
//                    strSQL+=" ";
//                    strSQL+=" GROUP BY x2.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x3.co_cli, x7.tx_ide, x3.tx_nomcli, x5.tx_descor, x2.fe_doc, x4.fe_ven, x4.nd_porret, x4.co_reg, x4.ne_diacre, x3.st_reg, ";
//                    strSQL+=" x4.co_banChq, x6.tx_desLar, x4.tx_numCtaChq, x4.tx_numChq, x4.fe_recChq, x4.fe_venChq, x4.nd_monChq, x5.tx_natdoc, x3.ne_numdoc, x3.fe_doc, x4.mo_pag, x4.nd_abo, x5.tx_deslar ";
//                    strSQL+=" ORDER BY x1.co_tipdocpag, x3.ne_numdoc ";
//                strSQL+=" ) as q ";
//                strSQL+=" GROUP BY q.co_emp, q.co_locpag, q.co_tipdocpag, q.co_docpag, q.numfac, q.mo_pag, q.FECDOC, q.co_cli, q.tx_ide, q.tx_nomcli, q.tx_descor, q.tx_deslar, q.FECVEN, q.nd_porret, q.ne_diacre, ";
//                strSQL+=" q.CODREG, q.ESTREG, q.co_banChq, q.X6_TX_DESLAR, q.tx_numCtaChq, q.tx_numChq, q.fe_recChq, q.fe_venChq, q.nd_monChq, q.NATDOC )";
//                
//                strSQL+=" ) AS Q";
//                strSQL+=" ORDER BY Q.NATDOC, Q.NOM, Q.NUMFAC";
//                
//                
//                System.out.println("---Query de registros Consultados --cargarDetRegCorFec()--CxC16: "+ strSQL);
//                
//                rst=stm.executeQuery(strSQL);
//                
//                //Limpiar vector de datos.
//                vecDat.clear();
//                
//                //Obtener los registros.
////                lblMsgSis.setText("Cargando datos...");
////                pgrSis.setMinimum(0);
////                pgrSis.setMaximum(intNumTotReg);
////                pgrSis.setValue(0);
//                
//                i=0;
//                while (rst.next())
//                {
//                    if (blnCon)
//                    {
//                        vecReg=new Vector();
//                        vecReg.add(INT_TBL_DAT_LIN,"");
//                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("EMP"));
//                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("LOC"));
//                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("CLI"));
//                        vecReg.add(INT_TBL_DAT_RUC_CED,rst.getString("RUCCED"));
//                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("NOM"));
//                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("TIPDOC"));
//                        vecReg.add(INT_TBL_DAT_DEC_TIP_DOC,rst.getString("DESCOR"));
//                        vecReg.add(INT_TBL_DAT_DEL_TIP_DOC,rst.getString("DESLAR"));
//                        vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("CODDOC"));
//                        vecReg.add(INT_TBL_DAT_COD_REG,rst.getString("CODREG"));
//                        vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("NUMFAC"));
//                        ///vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
//                        vecReg.add(INT_TBL_DAT_DIA_CRE,rst.getString("DIACRE"));
//                        datFecVen=rst.getDate("FECVEN");
//                        ///vecReg.add(INT_TBL_DAT_FEC_VEN,datFecVen);
//                        vecReg.add(INT_TBL_DAT_FEC_VEN,rst.getString("FECVEN"));
//                        vecReg.add(INT_TBL_DAT_POR_RET,rst.getString("PORRET"));
//                        vecReg.add(INT_TBL_DAT_VAL_DOC,rst.getString("VALDOC"));
//                        vecReg.add(INT_TBL_DAT_VAL_ABO,rst.getString("VALABO"));
//                        vecReg.add(INT_TBL_DAT_VAL_PEN,rst.getString("VALPEN"));
//                        try
//                        {
//                            int intdiaCre=Integer.parseInt(vecReg.get(INT_TBL_DAT_DIA_CRE).toString());
//                            ///System.out.println("Los Dias de Creditos desde la tabla son ---> "+ intdiaCre);
//
////                            //almaceno la fecha del sistema en un arreglo de enteros
////                            intFecFin=dtpDat.getFecha(strFecSis);
////                            System.out.println("La FechaSist es: "+ strFecSis);
////                            ///System.out.println("En numeros la FechaSist del Doc --intFecFin-- es: "+ intFecFin);
//                            
//                            //almaceno la fecha del sistema en un arreglo de enteros                            
//                            intFecFin=dtpDatFacCor.getFecha(strFecDocAux);
////                            System.out.println("---La Fecha Corte es: "+ strFecDoc);
//                            ///System.out.println("En numeros la FechaSist del Doc --intFecFin-- es: "+ intFecFin);
//
//                            //tomo la fecha de la base y la almaceno en un arreglo como entero
//                            strFecIni=objUti.formatearFecha(datFecVen,"dd/MM/yyyy");
//                            intFecIni=dtpDat.getFecha(strFecIni);//(strFecIni);
////                            System.out.println("La FechaVenc del Doc es: "+ strFecIni);
//                            ///System.out.println("En numeros la FechaVenc del Doc --intFecIni-- es: "+ intFecIni);
//
//                            //// la funcion intCalDiaTra( año inicialFecVenc, mes inicialFecVenc, dia inicialFecVenc, 
//                            //// año final FecActualSis, mes final FecActualSis, dia final FecActualSis)
//                            ////calcula los dias transcurridos entre 2 fechas
//                            intNumDia=intCalDiaTra(intFecIni[2], intFecIni[1], intFecIni[0], intFecFin[2], intFecFin[1], intFecFin[0]);
//                            ///System.out.println("---> Los Dias de Diferencias entre FechaVen " + strFecIni + "y FechaSist " + strFecSis + " es ---> "+ intNumDia);
//                            ///System.out.println("---> Los Dias de Diferencias entre FechaVen " + strFecIni + "y FechaDoc " + strFecDocAux + " es ---> "+ intNumDia);
//                            //ubico el valor vencido en el rango correspondiente
//                            if(intNumDia<=0){
//                            ///if(){
//                                vecReg.add(INT_TBL_DAT_VAL_VEN,rst.getString("VALPEN"));
//                                vecReg.add(INT_TBL_DAT_VAL_30D,null);
//                                vecReg.add(INT_TBL_DAT_VAL_60D,null);
//                                vecReg.add(INT_TBL_DAT_VAL_90D,null);
//                                vecReg.add(INT_TBL_DAT_VAL_MAS,null);
//                            }else
//                            {
//                                if(intNumDia>=0 && intNumDia<=30){
//                                //if(intdiaCre>=0 && intdiaCre<=30){
//                                    vecReg.add(INT_TBL_DAT_VAL_VEN,null);
//                                    vecReg.add(INT_TBL_DAT_VAL_30D,rst.getString("VALPEN"));
//                                    vecReg.add(INT_TBL_DAT_VAL_60D,null);
//                                    vecReg.add(INT_TBL_DAT_VAL_90D,null);
//                                    vecReg.add(INT_TBL_DAT_VAL_MAS,null);
//                                }else
//                                {
//                                    if(intNumDia>=31 && intNumDia<=60){
//                                    //if(intdiaCre>=31 && intdiaCre<=60){
//                                        vecReg.add(INT_TBL_DAT_VAL_VEN,null);
//                                        vecReg.add(INT_TBL_DAT_VAL_30D,null);
//                                        vecReg.add(INT_TBL_DAT_VAL_60D,rst.getString("VALPEN"));
//                                        vecReg.add(INT_TBL_DAT_VAL_90D,null);
//                                        vecReg.add(INT_TBL_DAT_VAL_MAS,null);
//                                    }else
//                                    {
//                                        if(intNumDia>=61 && intNumDia<=90){
//                                        ///if(intdiaCre>=61 && intdiaCre<=90){
//                                            vecReg.add(INT_TBL_DAT_VAL_VEN,null);
//                                            vecReg.add(INT_TBL_DAT_VAL_30D,null);
//                                            vecReg.add(INT_TBL_DAT_VAL_60D,null);
//                                            vecReg.add(INT_TBL_DAT_VAL_90D,rst.getString("VALPEN"));
//                                            vecReg.add(INT_TBL_DAT_VAL_MAS,null);
//                                        }else{
//                                            vecReg.add(INT_TBL_DAT_VAL_VEN,null);
//                                            vecReg.add(INT_TBL_DAT_VAL_30D,null);
//                                            vecReg.add(INT_TBL_DAT_VAL_60D,null);
//                                            vecReg.add(INT_TBL_DAT_VAL_90D,null);
//                                            vecReg.add(INT_TBL_DAT_VAL_MAS,rst.getString("VALPEN"));
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                        catch (Exception e)
//                        {
//                            objUti.mostrarMsgErr_F1(this, e);
//                        }
//
//                        vecReg.add(INT_TBL_DAT_COD_BAN,rst.getString("co_banChq"));
//                        vecReg.add(INT_TBL_DAT_NOM_BAN,rst.getString("X6_TX_DESLAR"));
//                        vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_numCtaChq"));
//                        vecReg.add(INT_TBL_DAT_NUM_CHQ,rst.getString("tx_numChq"));
//                        vecReg.add(INT_TBL_DAT_FEC_REC_CHQ,rst.getString("fe_recChq"));
//                        vecReg.add(INT_TBL_DAT_FEC_VEN_CHQ,rst.getString("fe_venChq"));
//                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("FECDOC"));
//                        vecReg.add(INT_TBL_DAT_VAL_CHQ,rst.getString("nd_monChq"));
//                        vecDat.add(vecReg);
//                        i++;
//                        ///pgrSis.setValue(i);
//                    }
//                    else
//                    {
//                        break;
//                    }
//                }
//                rst.close();
//                stm.close();
//                con.close();
//                rst=null;
//                stm=null;
//                con=null;
//                
////                //Asignar vectores al modelo.
////                objTblMod.setData(vecDat);
////                tblDat.setModel(objTblMod);
////                vecDat.clear();
////                //Calcular totales.
////                objTblTot.calcularTotales();
//                
//                //Asignar vectores al modelo.
//                objTblMod.setData(vecDat);
//                tblDatFacMovReg.setModel(objTblMod);
//                vecDat.clear();
//                
//                //Calcular totales.
//                objTotDatMovFac.calcularTotales();
//                
////                if (intNumTotReg==tblDatFacMovReg.getRowCount())
////                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
////                else
////                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDatFacMovReg.getRowCount() + ".");
//                
//                ///pgrSis.setValue(0);
//                ///pgrSis.setIndeterminate(false);
//                butCon.setText("Consultar");
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
//        return blnRes;
//    }
    
//    /**
//     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
//     * @return true: Si se pudo consultar los registros.
//     * <BR>false: En el caso contrario.
//     */
//    private boolean cargarDetRegCorFec()
//    {
//        int intCodEmp, intCodLoc, intNumTotReg, i;
//        
//        int intNumDia; 
//        String strFecSis, strFecIni, strFecSel, strFecVen, strFecDoc;
//        int intFecIni[];
//        int intFecFin[];//para calcular los dias entre fechas
//        int intFecSel[];//fecha seleccionada por el usuario//
//
//        
//        double dblSub, dblIva;
//        java.util.Date datFecSer, datFecVen, datFecAux, datFacDoc;
//        
//        boolean blnRes=true;
//        try
//        {
//            ///butCon.setText("Detener");
//            ///lblMsgSis.setText("Obteniendo datos...");
//            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
//            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
//            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            if (con!=null)
//            {
//                stm=con.createStatement();
//                //Obtener la fecha del servidor.
//                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//                if (datFecSer==null)
//                    return false;
//                datFecAux=datFecSer;
//                
//                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
//
//                //Obtener la condición.
//                strAux="";
//                
//                //Condicion para filtro por cliente
//                if(optTod.isSelected())
//                {
//                    ///strAux+=" AND a1.co_cli=" + objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_COD_CLI);
//                    strAux+=" AND a4.tx_ide = '" + objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_RUC_CED) + "'";
//                }
//                
//                ////////Condicion para filtro por cliente en un rango especifico
//                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
//                    strAux+=" AND ((LOWER(a4.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a4.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
//                
//                if (!(txtCodGrpCli.getText().equals("")))
//                {
//                    strAux+=" AND a1.co_tipdoc= " + txtCodTipDoc.getText();
//                }
//
//                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
//                {
//                    //Condicion para filtro por empresa
//                    if (txtCodEmp.getText().length()>0)
//                        strAux+=" AND a1.co_emp=" + txtCodEmp.getText();
//                    else
//                        strAux+=" AND a1.co_emp=" + objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP);
//                    
//                    //Condicion para filtro por cliente
//                    if (txtCodCli.getText().length()>0)
//                        strAux+=" AND a1.co_cli= " + txtCodCli.getText();
//                    else
//                        strAux+=" AND a1.co_cli=" + objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_COD_CLI);
//                }
//                else
//                {
//                    strAux+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
//                    
//                    //Condicion para filtro por cLocal
//                    if (txtCodLoc.getText().length()>0)
//                        strAux+=" AND a1.co_loc= " + txtCodLoc.getText();
//                    else
//                        strAux+=" AND a1.co_loc= " + objParSis.getCodigoLocal();
//                }
//                
//                ///FILTRO PARA MOSTRAR CREDITOS Y DEBITOS///
//                if ( !(chkMosCre.isSelected() && chkMosDeb.isSelected()) )
//                {
//                    System.out.println("ENTRO POR FILTRO DE CREDITOS Y DEBITOS ");
//                    if (chkMosCre.isSelected())
//                        strAux+=" AND a3.tx_natDoc='I'";
//                    else
//                        strAux+=" AND a3.tx_natDoc='E'";
//                }
//                ///FILTRO PARA DOCUMENTOS VENCIDOS///
//                if (chkMosDocVen.isSelected())
//                {
//                    System.out.println("ENTRO POR FILTRO DE DOCUMENTOS VENCIDOS ");
//                    strFecVen=objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
//                    strAux+=" AND a2.fe_ven<='" + strFecVen + "'";
//                }
//                ///FILTRO PARA MOSTRAR RETENCIONES///
//                if (!chkMosRet.isSelected())
//                {
//                    System.out.println("ENTRO POR FILTRO DE MOSTRAR RETENCIONES");
//                    strAux+=" AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
//                }
//                
//                ///strFecDoc=objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy");
//                strFecDoc=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "yyyy/MM/dd");
//                String strFecDocAux = objUti.formatearFecha(strFecDoc,"yyyy/MM/dd","dd/MM/yyyy");
//                System.out.println("---La Fecha Corte es: "+ strFecDoc);
//                System.out.println("---La Fecha Corte FORMATEADA es: "+ strFecDocAux);
//                ///FILTRO PARA FECHA DE CORTE///
//                if(dtpFecDoc.isFecha())
//                {
//                    System.out.println("ENTRO POR FILTRO DE FECHA DE CORTE");
//                    ///strFecDoc=objUti.formatearFecha(dtpFecDoc.getText(), "yyyy-MM-dd", objParSis.getFormatoFechaBaseDatos());
//                    strAux+=" AND a1.fe_doc<='" + strFecDoc + "'";
//                }
//                
///*                         
//
//SELECT Q.EMP, Q.LOC, Q.CLI, Q.RUCCED, Q.NOM, Q.TIPDOC, Q.DESCOR, Q.DESLAR, Q.CODDOC, Q.CODREG, Q.NUMFAC, Q.FECDOC, Q.DIACRE, Q.FECVEN, Q.PORRET, Q.ESTREG,
//Q.VALDOC, Q.VALABO, Q.VALPEN,Q.co_banchq, Q.X6_TX_DESLAR, Q.tx_numCtaChq, Q.tx_numChq, Q.fe_recChq, Q.fe_venChq, Q.nd_monChq, Q.NATDOC
//FROM (
//( 
//---query donde la suma del abono en tbm_detpag es igual al abono en tbm_pagmovinv por cada documento---
//SELECT a1.co_emp AS EMP, a1.co_loc AS LOC, a1.co_cli AS CLI, a4.tx_ide AS RUCCED, a4.tx_nom AS NOM, 
//a1.co_tipDoc AS TIPDOC, a3.tx_desCor AS DESCOR, a3.tx_desLar AS DESLAR, a1.co_doc AS CODDOC,
//a2.co_reg AS CODREG, a1.ne_numDoc AS NUMFAC, a1.fe_doc AS FECDOC, a2.ne_diaCre AS DIACRE, 
//a2.fe_ven AS FECVEN, a2.nd_porRet AS PORRET, a2.st_reg AS ESTREG,
//a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALPEN,
//a2.co_banChq, a5.tx_desLar AS X6_TX_DESLAR, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, 
//a2.fe_venChq, a2.nd_monChq, a3.tx_natdoc AS NATDOC
//FROM tbm_cabMovInv AS a1   
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
//AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
//INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
//LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
//INNER JOIN   
//( 
//select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet     
//from tbm_detpag as x1    
//inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc 
//and x1.co_doc=x2.co_doc)     where x2.st_reg NOT IN ('E','I')     AND x2.fe_doc <= '2006/12/31'    
//group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  
//) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag 
//and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag)  
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a6.sumabodet) <> 0 
//AND a3.ne_mod in (1,3)   AND a1.fe_doc <= '2006/12/31' 
//AND a4.tx_ide='0992126744001' 
//ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
//)
//UNION ALL
//( 
//---query donde el abono en tbm_pagmovinv es igual acero (no tiene pago asociado a tbm_detpag)---
//SELECT a1.co_emp AS EMP, a1.co_loc AS LOC, a1.co_cli AS CLI, a4.tx_ide AS RUCCED, a4.tx_nom AS NOM, 
//a1.co_tipDoc AS TIPDOC, a3.tx_desCor AS DESCOR, a3.tx_desLar AS DESLAR, a1.co_doc AS CODDOC, 
//a2.co_reg AS CODREG, a1.ne_numDoc AS NUMFAC, a1.fe_doc AS FECDOC, a2.ne_diaCre AS DICRE, 
//a2.fe_ven AS FECVEN, a2.nd_porRet AS PORRET, a2.st_reg AS ESTREG, 
//a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, (a2.mo_pag+a2.nd_abo) as VALPEN,  
//a2.co_banChq, a5.tx_desLar AS X6_TX_DESLAR, a2.tx_numCtaChq, a2.tx_numChq,  
//a2.fe_recChq, a2.fe_venChq, a2.nd_monChq, a3.tx_natdoc AS NATDOC 
//FROM tbm_cabMovInv AS a1   
//INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc 
//AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   
//INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)   INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)   
//LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)   
//WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod in (1,3) 
//AND a2.nd_abo=0.00
//AND a1.fe_doc <= '2006/12/31'  
//AND a4.tx_ide='0992126744001'
//ORDER BY a3.tx_natdoc, a4.tx_nom, a1.ne_numdoc 
//)
//UNION ALL
//(
//SELECT p.EMP, p.LOC, p.CLI, p.RUCCED, p.NOM, p.TIPDOC, p.DESCOR, p.DESLAR, p.CODDOC, p.CODREG, p.NUMFAC, p.FECDOC, p.DICRE, p.FECVEN, p.PORRET, p.ESTREG, 
//p.VALDOC, sum(p.valpagfecmay) as VALABO, (p.VALDOC+sum(p.valpagfecmay)) as VALPEN,p.co_banChq, p.X6_TX_DESLAR, p.tx_numCtaChq, p.tx_numChq,  
//p.fe_recChq, p.fe_venChq, p.nd_monChq, p.NATDOC 
//FROM
//( 
//SELECT x2.co_emp AS EMP, x2.co_loc AS LOC, x3.co_cli AS CLI, x6.tx_ide AS RUCCED, x6.tx_nom AS NOM, x3.co_tipDoc AS TIPDOC, x5.tx_desCor AS DESCOR, x5.tx_desLar AS DESLAR, x3.co_doc AS CODDOC, 
//x4.co_reg AS CODREG, x3.ne_numDoc AS NUMFAC, x3.fe_doc AS FECDOC, x4.ne_diaCre AS DICRE, x4.fe_ven AS FECVEN, x4.nd_porRet AS PORRET, x4.st_reg AS ESTREG, 
//x4.mo_pag AS VALDOC, x4.nd_abo AS VALABO, case when x2.fe_doc >='2006/12/31' then 0.00 end as valpagfecmay,(x4.mo_pag+x4.nd_abo) as VALPEN,  x4.co_banChq, x7.tx_desLar AS X6_TX_DESLAR, x4.tx_numCtaChq, x4.tx_numChq,  
//x4.fe_recChq, x4.fe_venChq, x4.nd_monChq, x5.tx_natdoc AS NATDOC 
//FROM tbm_detpag as x1 
//INNER JOIN 
//(   select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc  
//from tbm_cabpag as a1 where a1.st_reg NOT IN ('E', 'I') and a1.fe_doc >= '2006/12/31' 
//) as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc)  
//INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc)  
//INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg)  
//INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc)  
//INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)
//LEFT OUTER JOIN tbm_var AS x7 ON (x4.co_banChq=x7.co_reg) 
//WHERE x3.fe_doc <= '2006/12/31' 
//AND x6.tx_ide='0992126744001'
//) as p  
//GROUP BY p.EMP, p.LOC, p.CLI, p.RUCCED, p.NOM, p.TIPDOC, p.DESCOR, p.DESLAR, p.CODDOC, 
//p.CODREG, p.NUMFAC, p.FECDOC, p.DICRE, p.FECVEN, p.PORRET, p.ESTREG, p.VALDOC, p.co_banChq, p.X6_TX_DESLAR, 
//p.tx_numCtaChq, p.tx_numChq,  p.fe_recChq, p.fe_venChq, p.nd_monChq, p.NATDOC 
//) 
//) AS Q  
//ORDER BY Q.NOM, Q.EMP
//*/          
//                //Armar la sentencia SQL.
//                strSQL="";
//                strSQL+=" SELECT Q.EMP, Q.LOC, Q.CLI, Q.RUCCED, Q.NOM, Q.TIPDOC, Q.DESCOR, Q.DESLAR, Q.CODDOC, Q.CODREG, Q.NUMFAC, Q.FECDOC, Q.DIACRE, Q.FECVEN, Q.PORRET, Q.ESTREG, ";
//                strSQL+=" Q.VALDOC, Q.VALABO, Q.VALPEN,Q.co_banchq, Q.X6_TX_DESLAR, Q.tx_numCtaChq, Q.tx_numChq, Q.fe_recChq, Q.fe_venChq, Q.nd_monChq, Q.NATDOC ";
//                strSQL+=" FROM (";
//                ///---query donde la suma del abono en tbm_detpag es igual al abono en tbm_pagmovinv por cada documento---
//                strSQL+=" ( SELECT a1.co_emp AS EMP, a1.co_loc AS LOC, a1.co_cli AS CLI, a4.tx_ide AS RUCCED, a4.tx_nom AS NOM, a1.co_tipDoc AS TIPDOC, a3.tx_desCor AS DESCOR, a3.tx_desLar AS DESLAR, a1.co_doc AS CODDOC, ";
//                strSQL+=" a2.co_reg AS CODREG, a1.ne_numDoc AS NUMFAC, a1.fe_doc AS FECDOC, a2.ne_diaCre AS DIACRE, a2.fe_ven AS FECVEN, a2.nd_porRet AS PORRET, a2.st_reg AS ESTREG, ";
//                strSQL+=" a2.mo_pag AS VALDOC, a6.sumabodet AS VALABO, (a2.mo_pag+a6.sumabodet) as VALPEN, ";
//                strSQL+=" a2.co_banChq, a5.tx_desLar AS X6_TX_DESLAR, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a2.nd_monChq, a3.tx_natdoc AS NATDOC ";
//                strSQL+=" FROM tbm_cabMovInv AS a1";
//                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
//                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";
//                strSQL+=" INNER JOIN (";
//                strSQL+=" select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet ";
//                strSQL+=" from tbm_detpag as x1";
//                strSQL+=" inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) ";
//                strSQL+=" where x2.st_reg NOT IN ('E','I') ";
//                strSQL+=" AND x2.fe_doc <= '" +strFecDoc+ "'";
//                strSQL+=" group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag ";
//                strSQL+=" ) AS a6 ON (a2.co_emp=a6.co_emp and a2.co_loc=a6.co_locpag and a2.co_tipdoc=a6.co_tipdocpag and a2.co_doc=a6.co_docpag and a2.co_reg=a6.co_regpag) ";
//                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                strSQL+=" AND a2.st_reg IN ('A','C')";
//                ////strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                strSQL+=" AND (a2.mo_pag+a6.sumabodet)<>0";
//                strSQL+=" AND a3.ne_mod in (1,3)";//modulo, para saber donde utilizo el tipo de doc
//                strSQL+=" AND a1.fe_doc <= '" +strFecDoc+ "'";
//                strSQL+=strAux;
//                strSQL+=" ORDER BY a3.tx_natdoc, a4.tx_nom )";
//                
//                strSQL+=" UNION ALL";
//                
//                ///---query donde el abono en tbm_pagmovinv es igual acero (no tiene pago asociado a tbm_detpag)---///
//                strSQL+=" ( SELECT a1.co_emp AS EMP, a1.co_loc AS LOC, a1.co_cli AS CLI, a4.tx_ide AS RUCCED, a4.tx_nom AS NOM, a1.co_tipDoc AS TIPDOC, a3.tx_desCor AS DESCOR, a3.tx_desLar AS DESLAR, a1.co_doc AS CODDOC, ";
//                strSQL+=" a2.co_reg AS CODREG, a1.ne_numDoc AS NUMFAC, a1.fe_doc AS FECDOC, a2.ne_diaCre AS DICRE, a2.fe_ven AS FECVEN, a2.nd_porRet AS PORRET, a2.st_reg AS ESTREG, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, ";
//                strSQL+=" (a2.mo_pag+a2.nd_abo) as VALPEN,  a2.co_banChq, a5.tx_desLar AS X6_TX_DESLAR, a2.tx_numCtaChq, a2.tx_numChq,  a2.fe_recChq, a2.fe_venChq, a2.nd_monChq, a3.tx_natdoc AS NATDOC ";
//                strSQL+=" FROM tbm_cabMovInv AS a1";
//                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
//                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
//                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";                
//                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
//                strSQL+=" AND a2.st_reg IN ('A','C')";
//                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
//                strSQL+=" AND a3.ne_mod in (1,3)";//modulo, para saber donde utilizo el tipo de doc
//                strSQL+=" AND a2.nd_abo=0.00 ";
//                strSQL+=" AND a1.fe_doc <= '" +strFecDoc+ "'";
//                strSQL+=strAux;
//                strSQL+=" ORDER BY a3.tx_natdoc, a4.tx_nom )";
//                
//                strSQL+="UNION ALL";                
//
//                strSQL+=" ( SELECT p.EMP, p.LOC, p.CLI, p.RUCCED, p.NOM, p.TIPDOC, p.DESCOR, p.DESLAR, p.CODDOC, p.CODREG, p.NUMFAC, p.FECDOC, p.DICRE, p.FECVEN, p.PORRET, p.ESTREG, p.VALDOC, ";
//                strSQL+=" sum(p.valpagfecmay) as VALABO, (p.VALDOC+sum(p.valpagfecmay)) as VALPEN,p.co_banChq, p.X6_TX_DESLAR, p.tx_numCtaChq, p.tx_numChq, p.fe_recChq, p.fe_venChq, p.nd_monChq, p.NATDOC ";
//                strSQL+=" FROM ";
//                strSQL+=" ( ";
//                strSQL+="   SELECT x2.co_emp AS EMP, x2.co_loc AS LOC, x3.co_cli AS CLI, x6.tx_ide AS RUCCED, x6.tx_nom AS NOM, x3.co_tipDoc AS TIPDOC, x5.tx_desCor AS DESCOR, x5.tx_desLar AS DESLAR, x3.co_doc AS CODDOC, ";
//                strSQL+="   x4.co_reg AS CODREG, x3.ne_numDoc AS NUMFAC, x3.fe_doc AS FECDOC, x4.ne_diaCre AS DICRE, x4.fe_ven AS FECVEN, x4.nd_porRet AS PORRET, x4.st_reg AS ESTREG, x4.mo_pag AS VALDOC, x4.nd_abo AS VALABO, ";
//                strSQL+="   case when x2.fe_doc >='2006/12/31' then 0.00 end as valpagfecmay,(x4.mo_pag+x4.nd_abo) as VALPEN,  x4.co_banChq, x7.tx_desLar AS X6_TX_DESLAR, x4.tx_numCtaChq, x4.tx_numChq, ";
//                strSQL+="   x4.fe_recChq, x4.fe_venChq, x4.nd_monChq, x5.tx_natdoc AS NATDOC ";
//                strSQL+="   FROM tbm_detpag as x1 ";
//                strSQL+="   INNER JOIN ";
//                strSQL+="   (   select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.fe_doc  ";
//                strSQL+="       from tbm_cabpag as a1";
//                strSQL+="       where a1.st_reg NOT IN ('E', 'I') ";
//                strSQL+="       AND a1.fe_doc >= '" +strFecDoc+ "'";
//                strSQL+="   ) as x2 on (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc)";
//                strSQL+="   INNER JOIN tbm_cabmovinv as x3 ON (x1.co_emp=x3.co_emp and x1.co_locpag=x3.co_loc and x1.co_tipdocpag=x3.co_tipdoc and x1.co_docpag=x3.co_doc)";
//                strSQL+="   INNER JOIN tbm_pagmovinv as x4 ON (x1.co_emp=x4.co_emp and x1.co_locpag=x4.co_loc and x1.co_tipdocpag=x4.co_tipdoc and x1.co_docpag=x4.co_doc and x1.co_regpag=x4.co_reg)";
//                strSQL+="   INNER JOIN tbm_cabtipdoc as x5 ON (x3.co_emp=x5.co_emp and x3.co_loc=x5.co_loc and x3.co_tipdoc=x5.co_tipdoc)";
//                strSQL+="   INNER JOIN tbm_cli AS x6 ON (x3.co_emp=x6.co_emp AND x3.co_cli=x6.co_cli)";
//                strSQL+="   LEFT OUTER JOIN tbm_var AS x7 ON (x4.co_banChq=x7.co_reg)";
//                strSQL+="   WHERE x3.fe_doc <= '" +strFecDoc+ "'";
//                strSQL+="   AND x6.tx_ide = '" + objTblModFac.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FAC_RUC_CED) + "'";
//                strSQL+=FilSql();
//                strSQL+=" ) as p ";
//                strSQL+=" GROUP BY p.EMP, p.LOC, p.CLI, p.RUCCED, p.NOM, p.TIPDOC, p.DESCOR, p.DESLAR, p.CODDOC, p.CODREG, p.NUMFAC, p.FECDOC, p.DICRE, p.FECVEN, p.PORRET, p.ESTREG, p.VALDOC, p.co_banChq, p.X6_TX_DESLAR, ";
//                strSQL+=" p.tx_numCtaChq, p.tx_numChq,  p.fe_recChq, p.fe_venChq, p.nd_monChq, p.NATDOC )";
//                strSQL+=" ) AS Q";
//                strSQL+=" ORDER BY Q.NOM, Q.EMP, Q.FECDOC";
//                
//                System.out.println("---Query de registros Consultados --cargarDetRegCorFec()--CxC16: "+ strSQL);
//                
//                rst=stm.executeQuery(strSQL);
//                
//                //Limpiar vector de datos.
//                vecDat.clear();
//                
//                //Obtener los registros.
////                lblMsgSis.setText("Cargando datos...");
////                pgrSis.setMinimum(0);
////                pgrSis.setMaximum(intNumTotReg);
////                pgrSis.setValue(0);
//                
//                i=0;
//                while (rst.next())
//                {
//                    if (blnCon)
//                    {
//                        vecReg=new Vector();
//                        vecReg.add(INT_TBL_DAT_LIN,"");
//                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("EMP"));
//                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("LOC"));
//                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("CLI"));
//                        vecReg.add(INT_TBL_DAT_RUC_CED,rst.getString("RUCCED"));
//                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("NOM"));
//                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("TIPDOC"));
//                        vecReg.add(INT_TBL_DAT_DEC_TIP_DOC,rst.getString("DESCOR"));
//                        vecReg.add(INT_TBL_DAT_DEL_TIP_DOC,rst.getString("DESLAR"));
//                        vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("CODDOC"));
//                        vecReg.add(INT_TBL_DAT_COD_REG,rst.getString("CODREG"));
//                        vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("NUMFAC"));
//                        ///vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
//                        vecReg.add(INT_TBL_DAT_DIA_CRE,rst.getString("DIACRE"));
//                        datFecVen=rst.getDate("FECVEN");
//                        ///vecReg.add(INT_TBL_DAT_FEC_VEN,datFecVen);
//                        vecReg.add(INT_TBL_DAT_FEC_VEN,rst.getString("FECVEN"));
//                        vecReg.add(INT_TBL_DAT_POR_RET,rst.getString("PORRET"));
//                        vecReg.add(INT_TBL_DAT_VAL_DOC,rst.getString("VALDOC"));
//                        vecReg.add(INT_TBL_DAT_VAL_ABO,rst.getString("VALABO"));
//                        vecReg.add(INT_TBL_DAT_VAL_PEN,rst.getString("VALPEN"));
//                        try
//                        {
//                            int intdiaCre=Integer.parseInt(vecReg.get(INT_TBL_DAT_DIA_CRE).toString());
//                            ///System.out.println("Los Dias de Creditos desde la tabla son ---> "+ intdiaCre);
//
////                            //almaceno la fecha del sistema en un arreglo de enteros
////                            intFecFin=dtpDat.getFecha(strFecSis);
////                            System.out.println("La FechaSist es: "+ strFecSis);
////                            ///System.out.println("En numeros la FechaSist del Doc --intFecFin-- es: "+ intFecFin);
//                            
//                            //almaceno la fecha del sistema en un arreglo de enteros                            
//                            intFecFin=dtpDatFacCor.getFecha(strFecDocAux);
////                            System.out.println("---La Fecha Corte es: "+ strFecDoc);
//                            ///System.out.println("En numeros la FechaSist del Doc --intFecFin-- es: "+ intFecFin);
//
//                            //tomo la fecha de la base y la almaceno en un arreglo como entero
//                            strFecIni=objUti.formatearFecha(datFecVen,"dd/MM/yyyy");
//                            intFecIni=dtpDat.getFecha(strFecIni);//(strFecIni);
////                            System.out.println("La FechaVenc del Doc es: "+ strFecIni);
//                            ///System.out.println("En numeros la FechaVenc del Doc --intFecIni-- es: "+ intFecIni);
//
//                            //// la funcion intCalDiaTra( año inicialFecVenc, mes inicialFecVenc, dia inicialFecVenc, 
//                            //// año final FecActualSis, mes final FecActualSis, dia final FecActualSis)
//                            ////calcula los dias transcurridos entre 2 fechas
//                            intNumDia=intCalDiaTra(intFecIni[2], intFecIni[1], intFecIni[0], intFecFin[2], intFecFin[1], intFecFin[0]);
//                            ///System.out.println("---> Los Dias de Diferencias entre FechaVen " + strFecIni + "y FechaSist " + strFecSis + " es ---> "+ intNumDia);
//                            ///System.out.println("---> Los Dias de Diferencias entre FechaVen " + strFecIni + "y FechaDoc " + strFecDocAux + " es ---> "+ intNumDia);
//                            //ubico el valor vencido en el rango correspondiente
//                            if(intNumDia<=0){
//                            ///if(){
//                                vecReg.add(INT_TBL_DAT_VAL_VEN,rst.getString("VALPEN"));
//                                vecReg.add(INT_TBL_DAT_VAL_30D,null);
//                                vecReg.add(INT_TBL_DAT_VAL_60D,null);
//                                vecReg.add(INT_TBL_DAT_VAL_90D,null);
//                                vecReg.add(INT_TBL_DAT_VAL_MAS,null);
//                            }else
//                            {
//                                if(intNumDia>=0 && intNumDia<=30){
//                                //if(intdiaCre>=0 && intdiaCre<=30){
//                                    vecReg.add(INT_TBL_DAT_VAL_VEN,null);
//                                    vecReg.add(INT_TBL_DAT_VAL_30D,rst.getString("VALPEN"));
//                                    vecReg.add(INT_TBL_DAT_VAL_60D,null);
//                                    vecReg.add(INT_TBL_DAT_VAL_90D,null);
//                                    vecReg.add(INT_TBL_DAT_VAL_MAS,null);
//                                }else
//                                {
//                                    if(intNumDia>=31 && intNumDia<=60){
//                                    //if(intdiaCre>=31 && intdiaCre<=60){
//                                        vecReg.add(INT_TBL_DAT_VAL_VEN,null);
//                                        vecReg.add(INT_TBL_DAT_VAL_30D,null);
//                                        vecReg.add(INT_TBL_DAT_VAL_60D,rst.getString("VALPEN"));
//                                        vecReg.add(INT_TBL_DAT_VAL_90D,null);
//                                        vecReg.add(INT_TBL_DAT_VAL_MAS,null);
//                                    }else
//                                    {
//                                        if(intNumDia>=61 && intNumDia<=90){
//                                        ///if(intdiaCre>=61 && intdiaCre<=90){
//                                            vecReg.add(INT_TBL_DAT_VAL_VEN,null);
//                                            vecReg.add(INT_TBL_DAT_VAL_30D,null);
//                                            vecReg.add(INT_TBL_DAT_VAL_60D,null);
//                                            vecReg.add(INT_TBL_DAT_VAL_90D,rst.getString("VALPEN"));
//                                            vecReg.add(INT_TBL_DAT_VAL_MAS,null);
//                                        }else{
//                                            vecReg.add(INT_TBL_DAT_VAL_VEN,null);
//                                            vecReg.add(INT_TBL_DAT_VAL_30D,null);
//                                            vecReg.add(INT_TBL_DAT_VAL_60D,null);
//                                            vecReg.add(INT_TBL_DAT_VAL_90D,null);
//                                            vecReg.add(INT_TBL_DAT_VAL_MAS,rst.getString("VALPEN"));
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                        catch (Exception e)
//                        {
//                            objUti.mostrarMsgErr_F1(this, e);
//                        }
//
//                        vecReg.add(INT_TBL_DAT_COD_BAN,rst.getString("co_banChq"));
//                        vecReg.add(INT_TBL_DAT_NOM_BAN,rst.getString("X6_TX_DESLAR"));
//                        vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_numCtaChq"));
//                        vecReg.add(INT_TBL_DAT_NUM_CHQ,rst.getString("tx_numChq"));
//                        vecReg.add(INT_TBL_DAT_FEC_REC_CHQ,rst.getString("fe_recChq"));
//                        vecReg.add(INT_TBL_DAT_FEC_VEN_CHQ,rst.getString("fe_venChq"));
//                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("FECDOC"));
//                        vecReg.add(INT_TBL_DAT_VAL_CHQ,rst.getString("nd_monChq"));
//                        vecDat.add(vecReg);
//                        i++;
//                        ///pgrSis.setValue(i);
//                    }
//                    else
//                    {
//                        break;
//                    }
//                }
//                rst.close();
//                stm.close();
//                con.close();
//                rst=null;
//                stm=null;
//                con=null;
//                
////                //Asignar vectores al modelo.
////                objTblMod.setData(vecDat);
////                tblDat.setModel(objTblMod);
////                vecDat.clear();
////                //Calcular totales.
////                objTblTot.calcularTotales();
//                
//                //Asignar vectores al modelo.
//                objTblMod.setData(vecDat);
//                tblDatFacMovReg.setModel(objTblMod);
//                vecDat.clear();
//                
//                //Calcular totales.
//                objTotDatMovFac.calcularTotales();
//                
////                if (intNumTotReg==tblDatFacMovReg.getRowCount())
////                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
////                else
////                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDatFacMovReg.getRowCount() + ".");
//                
//                ///pgrSis.setValue(0);
//                ///pgrSis.setIndeterminate(false);
//                butCon.setText("Consultar");
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
//        return blnRes;
//    }
    
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
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
                        txtRucCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtRucCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
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
                            txtRucCli.setText(vcoCli.getValueAt(2));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.tx_ide", txtRucCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtRucCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
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
                            txtRucCli.setText(vcoCli.getValueAt(2));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtRucCli.setText(strRucCli);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtRucCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
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
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtDesLarCli.setText(strDesLarCli);
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConGrpCli(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoGrpCli.setCampoBusqueda(1);
                    vcoGrpCli.show();
                    if (vcoGrpCli.getSelectedButton()==vcoGrpCli.INT_BUT_ACE)
                    {
                        txtCodGrpCli.setText(vcoGrpCli.getValueAt(1));
                        txtNomGrpCli.setText(vcoGrpCli.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoGrpCli.buscar("a1.co_grp", txtCodGrpCli.getText()))
                    {
                        txtCodGrpCli.setText(vcoGrpCli.getValueAt(1));
                        txtNomGrpCli.setText(vcoGrpCli.getValueAt(2));
                    }
                    else
                    {
                        vcoGrpCli.setCampoBusqueda(1);
                        vcoGrpCli.setCriterio1(11);
                        vcoGrpCli.cargarDatos();
                        vcoGrpCli.show();
                        if (vcoGrpCli.getSelectedButton()==vcoGrpCli.INT_BUT_ACE)
                        {
                            txtCodGrpCli.setText(vcoGrpCli.getValueAt(1));
                            txtNomGrpCli.setText(vcoGrpCli.getValueAt(2));
                        }
                        else
                        {
                            txtCodGrpCli.setText(strCodGrpCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoGrpCli.buscar("a1.tx_nom", txtNomGrpCli.getText()))
                    {
                        txtCodGrpCli.setText(vcoGrpCli.getValueAt(1));
                        txtNomGrpCli.setText(vcoGrpCli.getValueAt(2));
                    }
                    else
                    {
                        vcoGrpCli.setCampoBusqueda(2);
                        vcoGrpCli.setCriterio1(11);
                        vcoGrpCli.cargarDatos();
                        vcoGrpCli.show();
                        if (vcoGrpCli.getSelectedButton()==vcoGrpCli.INT_BUT_ACE)
                        {
                            txtCodGrpCli.setText(vcoGrpCli.getValueAt(1));
                            txtNomGrpCli.setText(vcoGrpCli.getValueAt(2));
                        }
                        else
                        {
                            txtNomGrpCli.setText(strNomGrpCli);
                        }
                    }
                    break;
                default:
                    txtCodGrpCli.requestFocus();
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
    
    
//////    /**
//////     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
//////     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
//////     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
//////     * exista lo que se está buscando) o presentar la ventana de consulta para que
//////     * el usuario seleccione la opción que desea utilizar.
//////     * @param intTipBus El tipo de búsqueda a realizar.
//////     * @return true: Si no se presentó ningún problema.
//////     * <BR>false: En el caso contrario.
//////     */
//////    private boolean mostrarVenConTipDoc(int intTipBus)
//////    {
//////        boolean blnRes=true;
//////        try
//////        {
//////            switch (intTipBus)
//////            {
//////                case 0: //Mostrar la ventana de consulta.
//////                    vcoTipDoc.setCampoBusqueda(1);
//////                    vcoTipDoc.show();
//////                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
//////                    {
////////                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));///txtCodTipDoc   ///txtCodTipDoc
//////                        txtCodGrpCli.setText(vcoTipDoc.getValueAt(2));
//////                        txtNomGrpCli.setText(vcoTipDoc.getValueAt(3));
//////                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
//////
//////                    }
//////                    break;
//////                case 1: //Búsqueda directa por "Descripción corta".
//////                    if (vcoTipDoc.buscar("a1.tx_descor", txtCodGrpCli.getText()))
//////                    {
////////                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));   ///txtCodTipDoc
//////                        txtCodGrpCli.setText(vcoTipDoc.getValueAt(2));
//////                        txtNomGrpCli.setText(vcoTipDoc.getValueAt(3));
//////                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
//////                    }
//////                    else
//////                    {
//////                        vcoTipDoc.setCampoBusqueda(1);
//////                        vcoTipDoc.setCriterio1(11);
//////                        vcoTipDoc.cargarDatos();
//////                        vcoTipDoc.show();
//////                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
//////                        {
////////                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
//////                            txtCodGrpCli.setText(vcoTipDoc.getValueAt(2));
//////                            txtNomGrpCli.setText(vcoTipDoc.getValueAt(3));
//////                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
//////                        }
//////                        else
//////                        {
//////                            txtCodGrpCli.setText(strTipDoc);
//////                        }
//////                    }
//////                    break;
//////                case 2: //Búsqueda directa por "Descripción larga".
//////                    if (vcoTipDoc.buscar("a1.tx_deslar", txtNomGrpCli.getText()))
//////                    {
////////                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
//////                        txtCodGrpCli.setText(vcoTipDoc.getValueAt(2));
//////                        txtNomGrpCli.setText(vcoTipDoc.getValueAt(3));
//////                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
//////                    }
//////                    else
//////                    {
//////                        vcoTipDoc.setCampoBusqueda(2);
//////                        vcoTipDoc.setCriterio1(11);
//////                        vcoTipDoc.cargarDatos();
//////                        vcoTipDoc.show();
//////                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
//////                        {
//////                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
//////                            txtCodGrpCli.setText(vcoTipDoc.getValueAt(2));
//////                            txtNomGrpCli.setText(vcoTipDoc.getValueAt(3));
//////                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
//////                        }
//////                        else
//////                        {
//////                            txtNomGrpCli.setText(strDesLarTipDoc);
//////                        }
//////                    }
//////                    break;
//////                default:
//////                    txtCodGrpCli.requestFocus();
//////                    break;
//////            }
//////        }
//////        catch (Exception e)
//////        {
//////            blnRes=false;
//////            objUti.mostrarMsgErr_F1(this, e);
//////        }
//////        return blnRes;
//////    }
    
    
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConLoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoLoc.setCampoBusqueda(1);
                    vcoLoc.show();
                    if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE)
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtDesLarLoc.setText(vcoLoc.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoLoc.buscar("a1.co_loc", txtCodLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtDesLarLoc.setText(vcoLoc.getValueAt(2));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(0);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.show();
                        if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtDesLarLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtCodLoc.setText(strCodLoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoLoc.buscar("a1.tx_nom", txtDesLarLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtDesLarLoc.setText(vcoLoc.getValueAt(2));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(1);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.show();
                        if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtDesLarLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtDesLarLoc.setText(strNomLoc);
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConEmp(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(1);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtDesLarEmp.setText(vcoEmp.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtDesLarEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoLoc.getValueAt(1));
                            txtDesLarEmp.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoEmp.buscar("a1.tx_nom", txtDesLarEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtDesLarEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtDesLarEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else
                        {
                            txtDesLarEmp.setText(strDesLarEmp);
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
     * Esta clase implementa la interface "ListSelectionListener" para determinar
     * cambios en la selección. Es decir, cada vez que se selecciona una fila
     * diferente en el JTable se ejecutará el "ListSelectionListener".
     */
    private class ZafLisSelLisDatFac implements javax.swing.event.ListSelectionListener
    {
        public void valueChanged(javax.swing.event.ListSelectionEvent e)
        {
            javax.swing.ListSelectionModel lsm=(javax.swing.ListSelectionModel)e.getSource();
            int intMax=lsm.getMaxSelectionIndex();
            String strAux;
            if (!lsm.isSelectionEmpty())
            {  
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    if (chkDatFacMosMovReg.isSelected())
                    {
                        System.out.println("---Cuando selecciono un registro cargo datos por GRUPO---");
                        cargarDetRegGrp();
                    }
                    else
                        objTblMod.removeAllRows();
                }
                else
                {
                    if (chkDatFacMosMovReg.isSelected())
                    {
                        System.out.println("---Cuando selecciono un registro cargo datos por EMPRESA---");
                        cargarDetReg();
                    }
                    else
                        objTblMod.removeAllRows();
                }
                
            }
            else
                objTblMod.removeAllRows();
        }
    }
    
    /**
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
        public void run()
        {
            objTblMod.removeAllRows();
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                if (!cargarDatFacGrp())
                {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
                }
            }
            else
            {
                if (!cargarDatFac())
                {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
                }
            }
            ////Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
            
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_FAC_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_FAC_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_FAC_COD_CLI:
                    strMsg="Código del cliente o del Grupo";
                    break;
                case INT_TBL_DAT_FAC_RUC_CED:
                    strMsg="No.Ruc o No.Ced";
                    break;
                case INT_TBL_DAT_FAC_NOM_CLI:
                    strMsg="Nombre del cliente o del Grupo";
                    break;
                case INT_TBL_DAT_FAC_VAL_PEN:
                    strMsg="Valor Pendiente";
                    break;                    
                case INT_TBL_DAT_FAC_VAL_VEN:
                    strMsg="Valor por vencer";
                    break;
                case INT_TBL_DAT_FAC_VAL_30D:
                    strMsg="Valor vencido (0-30 días)";
                    break;
                case INT_TBL_DAT_FAC_VAL_60D:
                    strMsg="Valor vencido (31-60 días)";
                    break;
                case INT_TBL_DAT_FAC_VAL_90D:
                    strMsg="Valor vencido (61-90 días)";
                    break;
                case INT_TBL_DAT_FAC_VAL_MAS:
                    strMsg="Valor vencido (Más de 90 días)";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaAux extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_DAT_RUC_CED:
                    strMsg="Ruc o Num.Ced.";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DEC_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DEL_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_COD_REG:
                    strMsg="Código del registro";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;
//                case INT_TBL_DAT_FEC_DOC:
//                    strMsg="Fecha del documento";
//                    break;
                case INT_TBL_DAT_DIA_CRE:
                    strMsg="Días de crédito";
                    break;
                case INT_TBL_DAT_FEC_VEN:
                    strMsg="Fecha de vencimiento";
                    break;
                case INT_TBL_DAT_POR_RET:
                    strMsg="Porcentaje de retención";
                    break;
                case INT_TBL_DAT_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                case INT_TBL_DAT_VAL_ABO:
                    strMsg="Valor del Abono";
                    break;
                case INT_TBL_DAT_VAL_PEN:
                    strMsg="Valor Pendiente";
                    break;                    
                case INT_TBL_DAT_VAL_VEN:
                    strMsg="Valor por vencer";
                    break;
                case INT_TBL_DAT_VAL_30D:
                    strMsg="Valor vencido (0-30 días)";
                    break;
                case INT_TBL_DAT_VAL_60D:
                    strMsg="Valor vencido (31-60 días)";
                    break;
                case INT_TBL_DAT_VAL_90D:
                    strMsg="Valor vencido (61-90 días)";
                    break;
                case INT_TBL_DAT_VAL_MAS:
                    strMsg="Valor vencido (Más de 90 días)";
                    break;
                case INT_TBL_DAT_COD_BAN:
                    strMsg="Código del Banco";
                    break;
                case INT_TBL_DAT_NOM_BAN:
                    strMsg="Nombre del Banco";
                    break;
                case INT_TBL_DAT_NUM_CTA:
                    strMsg="Número de cuenta";
                    break;
                case INT_TBL_DAT_NUM_CHQ:
                    strMsg="Número de cheque";
                    break;
                case INT_TBL_DAT_FEC_REC_CHQ:
                    strMsg="Fecha de recepción del cheque";
                    break;
                case INT_TBL_DAT_FEC_VEN_CHQ:
                    strMsg="Fecha de vencimiento del cheque";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_VAL_CHQ:
                    strMsg="Valor del cheque";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}