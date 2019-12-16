/* 
 * ZafCxC42.java
 *
 * Created on 18 de Abril de 2008, 13:40 PM
 * Dario Cardenas
 * Programa Listado de Documentos por Cobrar de acuerdo a la Forma de Pago
 * FUE MODIFICADO EL 18/ABRIL/2008
 */
package CxC.ZafCxC42;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.sql.Connection; 
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafDate.ZafDatePicker;//esto es para calcular el numero de dias transcurridos
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafSelFec.ZafSelFec;

import java.io.File;
import javax.swing.*;

////Dario_para llamar a clase de Importar Datos a un Archivo de Excel////
import java.io.*;
import Librerias.ZafUtil.ZafImpDatXml;

///para el boton abrir archivos///
import javax.swing.*;
import javax.swing.filechooser.*; 
//import java.io.File;

/**
 *
 * @author  Eddye Lino
 */
public class ZafCxC42 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                        //LINEA DE NUMERACION
    static final int INT_TBL_DAT_COD_CLI=1;                    //CODIGO DE CLIENTE
    static final int INT_TBL_DAT_IDE_CLI=2;                    //NUMERO DE RUC/CEDULA DEL CLIENTE
    static final int INT_TBL_DAT_NOM_CLI=3;                    //NOMBRE DE CLIENTE
    static final int INT_TBL_DAT_COD_EMP=4;                    //CODIGO DE EMPRESA
    static final int INT_TBL_DAT_COD_LOC=5;                    //CODIGO DE LOCAL
    static final int INT_TBL_DAT_TIP_DOC=6;                    //CODIGO DE TIPO DE DOCUMENTO
    static final int INT_TBL_DAT_NOM_DOC=7;                    //NOMBRE DE TIPO DE DOCUMENTO
    static final int INT_TBL_DAT_COD_DOC=8;                    //CODIGO DE DOCUMENTO
    static final int INT_TBL_DAT_COD_REG=9;                    //CODIGO DE REGISTRO
    static final int INT_TBL_DAT_NUM_DOC=10;                   //NUMERO DE DOCUMENTO
    static final int INT_TBL_DAT_FEC_DOC=11;                   //FECHA DE DOCUMENTO
    static final int INT_TBL_DAT_DIA_CRE=12;                   //DIAS DE CREDITO
    static final int INT_TBL_DAT_FEC_VEN=13;                   //FECHA DE VENCIMIENTO
    static final int INT_TBL_DAT_POR_RET=14;                   //PORCENTAJE DE RETENCION
    static final int INT_TBL_DAT_VAL_DOC=15;                   //VALOR DE DOCUMENTO
    static final int INT_TBL_DAT_VAL_ABO=16;                   //VALOR DE ABONO
    static final int INT_TBL_DAT_VAL_PEN=17;                   //VALOR PENDIENTE
    
    //Variables
    private ZafDatePicker dtpDat;                      //esto es para calcular el numero de dias transcurridos
//    private ZafUtil objAux;
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModDab;
    private ZafThreadGUI objThrGUI;
    private ZafThreadGUIRpt objThrGUIRpt;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu; 
    private ZafTblEdi objTblEdi;                        //PopupMenu: Establecer PeopuMenï¿½ en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafTblTot objTblTot;                                //JTable de totales.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg , vecAux;
    private ZafVenCon vcoCli, vcoEmp;                       //Ventana de consulta.
    private boolean blnCon;                             //true: Continua la ejecuciï¿½n del hilo.
    private String strCodCli, strIdeCli, strDesLarCli,strCodEmp, strNomEmp, strDesLarEmp,strCodTipDoc, strTipDocNom,strTipDocCor, strDesLarEmpTipDoc;             //Contenido del campo al obtener el foco.    
    private int intCodLocAux;
    private ZafTblCelRenChk objTblCelRenChkMain;
    private ZafTblCelEdiChk objTblCelEdiChkMain;
    private ZafRptSis objRptSis;                        //Reportes del Sistema.
    private ZafSelFec objSelFec;                        //Selector de Fechas///
    private int clickbut=0, clickBotCon=0, intBanClicSel=0, Z=0;
    
    int TIPDOCRET = 1;
    
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblCelEdiTxt objTblCelEdiTxtNumDoc;
    javax.swing.JInternalFrame jfrThis; //Hace referencia a this
    private java.util.Date datFecSer;                   //Fecha del servidor.
    
    
    /*Dario_Objeto del Tipo ZafImpDatXml*/
    private ZafImpDatXml objImpDatXml;
    Object objAux, objTblAux;
    StringBuffer stb=new StringBuffer();
    //String strArrLis[][];
    
    ///variables para el boton de abrir archivos////
    static JFrame frame;
    JFileChooser chooser;
    
    private String strVer=" v0.5";
    
    /** Crea una nueva instancia de la clase ZafCxC42. */
    public ZafCxC42(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            chooser = new JFileChooser();
            objParSis=(ZafParSis)obj.clone();
            jfrThis=this;
            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
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
        spnFil = new javax.swing.JScrollPane();
        panspnFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        panNomCli = new javax.swing.JPanel();
        lblNomCliDes = new javax.swing.JLabel();
        txtNomCliDes = new javax.swing.JTextField();
        lblNomCliHas = new javax.swing.JLabel();
        txtNomCliHas = new javax.swing.JTextField();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        panEstados = new javax.swing.JPanel();
        chkNegociacion = new javax.swing.JCheckBox();
        chkContado = new javax.swing.JCheckBox();
        chkCheque = new javax.swing.JCheckBox();
        chkCredito = new javax.swing.JCheckBox();
        txtIdeCli = new javax.swing.JTextField();
        chkMosDocVen = new javax.swing.JCheckBox();
        chkMosDocSinAbo = new javax.swing.JCheckBox();
        chkMosRet = new javax.swing.JCheckBox();
        lblEmp = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        chkMosDocSinRes = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butVisPre = new javax.swing.JButton();
        butImp = new javax.swing.JButton();
        butExpArc = new javax.swing.JButton();
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

        panFil.setLayout(new java.awt.BorderLayout());

        spnFil.setBorder(null);

        panspnFil.setPreferredSize(new java.awt.Dimension(610, 420));
        panspnFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los clientes"); // NOI18N
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panspnFil.add(optTod);
        optTod.setBounds(4, 150, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los clientes que cumplan el criterio seleccionado"); // NOI18N
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        panspnFil.add(optFil);
        optFil.setBounds(4, 170, 400, 20);

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
        txtNomCliDes.setBounds(56, 20, 258, 20);

        lblNomCliHas.setText("Hasta:"); // NOI18N
        panNomCli.add(lblNomCliHas);
        lblNomCliHas.setBounds(326, 20, 44, 20);

        txtNomCliHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusLost(evt);
            }
        });
        panNomCli.add(txtNomCliHas);
        txtNomCliHas.setBounds(370, 20, 258, 20);

        panspnFil.add(panNomCli);
        panNomCli.setBounds(24, 210, 640, 52);

        lblCli.setText("Cliente:"); // NOI18N
        lblCli.setToolTipText("Cliente"); // NOI18N
        panspnFil.add(lblCli);
        lblCli.setBounds(24, 190, 120, 20);

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
        panspnFil.add(txtCodCli);
        txtCodCli.setBounds(144, 190, 56, 20);

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
        panspnFil.add(txtDesLarCli);
        txtDesLarCli.setBounds(300, 190, 340, 20);

        butCli.setText("..."); // NOI18N
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panspnFil.add(butCli);
        butCli.setBounds(640, 190, 20, 20);

        panEstados.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de forma de pago"));
        panEstados.setPreferredSize(new java.awt.Dimension(460, 52));
        panEstados.setLayout(null);

        chkNegociacion.setSelected(true);
        chkNegociacion.setText("Negociación"); // NOI18N
        chkNegociacion.setToolTipText("Documentos por Recaudar(Abiertos)"); // NOI18N
        panEstados.add(chkNegociacion);
        chkNegociacion.setBounds(20, 16, 100, 20);

        chkContado.setSelected(true);
        chkContado.setText("Contado"); // NOI18N
        chkContado.setToolTipText("Documentos por Recaudar(Abiertos)"); // NOI18N
        panEstados.add(chkContado);
        chkContado.setBounds(150, 16, 90, 20);

        chkCheque.setSelected(true);
        chkCheque.setText("Cheque"); // NOI18N
        chkCheque.setToolTipText("Documentos por Recaudar(Abiertos)"); // NOI18N
        panEstados.add(chkCheque);
        chkCheque.setBounds(270, 16, 90, 20);

        chkCredito.setSelected(true);
        chkCredito.setText("Crédito"); // NOI18N
        chkCredito.setToolTipText("Documentos por Recaudar(Abiertos)"); // NOI18N
        panEstados.add(chkCredito);
        chkCredito.setBounds(390, 16, 100, 20);

        panspnFil.add(panEstados);
        panEstados.setBounds(4, 24, 660, 44);

        txtIdeCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdeCliActionPerformed(evt);
            }
        });
        txtIdeCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdeCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdeCliFocusLost(evt);
            }
        });
        panspnFil.add(txtIdeCli);
        txtIdeCli.setBounds(200, 190, 100, 20);

        chkMosDocVen.setSelected(true);
        chkMosDocVen.setText("Mostrar sólo los documentos vencidos"); // NOI18N
        chkMosDocVen.setToolTipText("Mostrar sólo los documentos vencidos"); // NOI18N
        chkMosDocVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDocVenActionPerformed(evt);
            }
        });
        panspnFil.add(chkMosDocVen);
        chkMosDocVen.setBounds(4, 70, 500, 20);

        chkMosDocSinAbo.setText("Mostrar sólo los documentos que NO tienen ningún abono"); // NOI18N
        chkMosDocSinAbo.setToolTipText("Mostrar sólo los documentos vencidos"); // NOI18N
        panspnFil.add(chkMosDocSinAbo);
        chkMosDocSinAbo.setBounds(4, 90, 500, 20);

        chkMosRet.setText("Mostrar las retenciones"); // NOI18N
        chkMosRet.setToolTipText("Mostrar sólo los documentos vencidos"); // NOI18N
        panspnFil.add(chkMosRet);
        chkMosRet.setBounds(4, 110, 500, 20);

        lblEmp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblEmp.setText("Empresa:"); // NOI18N
        panspnFil.add(lblEmp);
        lblEmp.setBounds(4, 4, 120, 20);

        txtCodEmp.setMaximumSize(null);
        txtCodEmp.setPreferredSize(new java.awt.Dimension(70, 20));
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
        panspnFil.add(txtCodEmp);
        txtCodEmp.setBounds(124, 4, 92, 20);

        txtNomEmp.setPreferredSize(new java.awt.Dimension(100, 20));
        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        panspnFil.add(txtNomEmp);
        txtNomEmp.setBounds(216, 4, 424, 20);

        butEmp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butEmp.setText("..."); // NOI18N
        butEmp.setPreferredSize(new java.awt.Dimension(20, 20));
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panspnFil.add(butEmp);
        butEmp.setBounds(640, 4, 20, 20);

        chkMosDocSinRes.setText("Mostrar solo los documentos sin soporte"); // NOI18N
        chkMosDocSinRes.setToolTipText("Mostrar sólo los documentos vencidos"); // NOI18N
        panspnFil.add(chkMosDocSinRes);
        chkMosDocSinRes.setBounds(4, 130, 500, 20);

        spnFil.setViewportView(panspnFil);

        panFil.add(spnFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.BorderLayout());

        spnDat.setPreferredSize(new java.awt.Dimension(453, 403));

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

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

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

        panRpt.add(spnTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Reporte", panRpt);

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

        butVisPre.setText("Vista preliminar"); // NOI18N
        butVisPre.setToolTipText("Vista preliminar"); // NOI18N
        butVisPre.setPreferredSize(new java.awt.Dimension(92, 25));
        butVisPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVisPreActionPerformed(evt);
            }
        });
        panBot.add(butVisPre);

        butImp.setText("Imprimir"); // NOI18N
        butImp.setToolTipText("Imprimir"); // NOI18N
        butImp.setPreferredSize(new java.awt.Dimension(92, 25));
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panBot.add(butImp);

        butExpArc.setText("Exportar");
        butExpArc.setToolTipText("Exportar");
        butExpArc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExpArcActionPerformed(evt);
            }
        });
        panBot.add(butExpArc);

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
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        // TODO add your handling code here:
        mostrarMsgInf("<HTML> La FunciÃ³n <FONT COLOR=\"blue\"> ---IMPRIMIR--- </FONT> todavÃ­a <FONT COLOR=\"blue\"> NO </FONT> esta implementada en el sistema...  </HTML>");
//        if (objThrGUIRpt==null)
//        {
//            objThrGUIRpt=new ZafThreadGUIRpt();
//            objThrGUIRpt.setIndFunEje(1);
//            objThrGUIRpt.start();
//            ///intButCon=3;
//        }
    }//GEN-LAST:event_butImpActionPerformed

    private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed
        // TODO add your handling code here:
        mostrarMsgInf("<HTML> La FunciÃ³n <FONT COLOR=\"blue\"> ---VISTA PRELIMINAR--- </FONT> todavÃ­a <FONT COLOR=\"blue\"> NO </FONT> esta implementada en el sistema...  </HTML>");
//        if (objThrGUIRpt==null)
//        {
//            objThrGUIRpt=new ZafThreadGUIRpt();
//            objThrGUIRpt.setIndFunEje(1);
//            objThrGUIRpt.start();
//            ///intButCon=2;
//        }
    }//GEN-LAST:event_butVisPreActionPerformed

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        // TODO add your handling code here:
        mostrarVenConEmp(0);
        
        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
        //Configurar Ventana de Consulta de Cliente//
            configurarVenConCli();
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        // TODO add your handling code here:
        if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp))
        {
            if (txtNomEmp.getText().equals(""))
            {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            }
            else
            {
                mostrarVenConEmp(2);
            }
        }
        else
            txtNomEmp.setText(strNomEmp);
        
        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
        //Configurar Ventana de Consulta de Cliente//
            configurarVenConCli();
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        // TODO add your handling code here:
        strNomEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
    }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        // TODO add your handling code here:
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        // TODO add your handling code here:
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp))
        {
            if (txtCodEmp.getText().equals(""))
            {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            }
            else
            {
                mostrarVenConEmp(1);
            }
        }
        else
            txtCodEmp.setText(strCodEmp);
        
        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
        //Configurar Ventana de Consulta de Cliente//
            configurarVenConCli();
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        // TODO add your handling code here:
        strCodEmp=txtCodEmp.getText();
        txtCodEmp.selectAll();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        // TODO add your handling code here:
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtIdeCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeCliFocusLost
        // TODO add your handling code here:
        if (!txtIdeCli.getText().equalsIgnoreCase(strIdeCli))
        {
            if (txtIdeCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtIdeCli.setText("");
                txtDesLarCli.setText("");
            }
            else
            {
                mostrarVenConCli(3);
            }
        }
        else
            txtIdeCli.setText(strIdeCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtIdeCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtIdeCliFocusLost

    private void txtIdeCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeCliFocusGained
        // TODO add your handling code here:
        strIdeCli=txtIdeCli.getText();
        txtIdeCli.selectAll();
    }//GEN-LAST:event_txtIdeCliFocusGained

    private void txtIdeCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdeCliActionPerformed
        // TODO add your handling code here:
        txtIdeCli.transferFocus();
    }//GEN-LAST:event_txtIdeCliActionPerformed

    private void chkMosDocVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDocVenActionPerformed
        // TODO add your handling code here:        
    }//GEN-LAST:event_chkMosDocVenActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        if (optFil.isSelected())
        {
//            txtCodEmp.setText("");
//            txtDesLarEmp.setText("");
//            txtCodTipDoc.setText("");
//            txtDesLarTipDoc.setText("");
//            txtCodEmpTipDoc.setText("");
//            txtDesLarEmpTipDoc.setText("");
        }

    }//GEN-LAST:event_optFilItemStateChanged

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        mostrarVenConCli(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butCliActionPerformed

    private void txtDesLarCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusLost
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtDesLarCli.getText().equalsIgnoreCase(strDesLarCli))
        {
            if (txtDesLarCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtDesLarCli.setText("");
            }
            else
            {
                mostrarVenConCli(2);
            }
        }
        else
            txtDesLarCli.setText(strDesLarCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
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
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCodCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtDesLarCli.setText("");
            }
            else
            {
                mostrarVenConCli(1);
            }
        }
        else
            txtCodCli.setText(strCodCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void txtNomCliHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusLost
        if (txtNomCliHas.getText().length()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_txtNomCliHasFocusLost

    private void txtNomCliDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusLost
        if (txtNomCliDes.getText().length()>0)
        {
            optFil.setSelected(true);
            if (txtNomCliHas.getText().length()==0)
                txtNomCliHas.setText(txtNomCliDes.getText());
        }
    }//GEN-LAST:event_txtNomCliDesFocusLost

    private void txtNomCliHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusGained
        txtNomCliHas.selectAll();
    }//GEN-LAST:event_txtNomCliHasFocusGained

    private void txtNomCliDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusGained
        txtNomCliDes.selectAll();
    }//GEN-LAST:event_txtNomCliDesFocusGained

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodCli.setText("");
            txtIdeCli.setText("");
            txtDesLarCli.setText("");
            txtNomCliDes.setText("");
            txtNomCliHas.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acciï¿½n de acuerdo a la etiqueta del botï¿½n ("Consultar" o "Detener").
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
        
        clickBotCon++;
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicaciï¿½n. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="ï¿½Estï¿½ seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butExpArcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpArcActionPerformed
        // TODO add your handling code here:
        
        if(clickBotCon>0) {
            int intNumCol, intNumFil, intNumFinCol;
            int intNumColSel[], intNumFilSel[];
            int intFilSel[], intColSel[];
            String Ruta="";
            ArrayList arrNumColEli;
            
            tblDat.selectAll();
            
            //Para saber el total de fila y la columna seleccionada//
            intFilSel=tblDat.getSelectedRows();
            intColSel=tblDat.getSelectedColumns();
            ///Para Saber total de filas y columnas de la tabla//
            intNumCol = objTblMod.getColumnCount();
            intNumFil = objTblMod.getRowCountTrue();
            arrNumColEli = objTblMod.getSystemHiddenColumns();
            intNumFinCol = (intNumCol - arrNumColEli.size());
            String strArrLis[][] = new String[intNumFil][intNumFinCol];
            
            tblDat.clearSelection();
            
            ////boton para abrir cuadro de dialogo de abrir documento////
            int retval = chooser.showSaveDialog(frame);
            if (retval == JFileChooser.APPROVE_OPTION) {
                if (chooser.isMultiSelectionEnabled()) {
                    File [] files = chooser.getSelectedFiles();
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
                                    "You chose this directory: " +
                                    theFile.getPath());
                        } else {
                            //			JOptionPane.showMessageDialog(frame,
                            //						      "La Ruta del Archivo Seleccionado es: " +
                            //						      theFile.getPath());
                            Ruta = theFile.getPath();
                        }
                    }
                }
            } else if (retval == JFileChooser.CANCEL_OPTION) {
                ///JOptionPane.showMessageDialog(frame, "User cancelled operation. No file was chosen.");
            } else if (retval == JFileChooser.ERROR_OPTION) {
                JOptionPane.showMessageDialog(frame, "Ocurrio un Eror al momento de Guardar.");
            } else {
                JOptionPane.showMessageDialog(frame, "La Operacion no es valida.");
            }
            
            //Obtener el detalle que se va a enviar al archivo de Excel//
            for (int i=0; i<intFilSel.length; i++) {
                for (int j=1; j<intNumFinCol; j++) {
                    if(j < intNumFinCol) {
                        objAux=objTblMod.getValueAt(intFilSel[i], intColSel[j]);
                        strArrLis[i][j] = objUti.parseString(""+objAux);
                    }
                    //else
                    //break;
                }
            }
            
            intColSel = new int[intNumFinCol];
            if(!Ruta.equals("")) {
                /*Dario_---Para Generar archivo de Excel---*/
                objImpDatXml.EjecutarFuncion(intFilSel, intColSel, strArrLis, Ruta);
            }
            ////////////////////////////////////////////
        } else
            JOptionPane.showMessageDialog(frame, "Favor Debe Consultar Datos.");
        
        clickbut++;
    }//GEN-LAST:event_butExpArcActionPerformed

    /** Cerrar la aplicaciï¿½n. */
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
    private javax.swing.JButton butExpArc;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butVisPre;
    private javax.swing.JCheckBox chkCheque;
    private javax.swing.JCheckBox chkContado;
    private javax.swing.JCheckBox chkCredito;
    private javax.swing.JCheckBox chkMosDocSinAbo;
    private javax.swing.JCheckBox chkMosDocSinRes;
    private javax.swing.JCheckBox chkMosDocVen;
    private javax.swing.JCheckBox chkMosRet;
    private javax.swing.JCheckBox chkNegociacion;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNomCliDes;
    private javax.swing.JLabel lblNomCliHas;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panEstados;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panspnFil;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtIdeCli;
    private javax.swing.JTextField txtNomCliDes;
    private javax.swing.JTextField txtNomCliHas;
    private javax.swing.JTextField txtNomEmp;
    // End of variables declaration//GEN-END:variables
   
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            dtpDat=new ZafDatePicker(((javax.swing.JFrame)this.getParent()), "d/m/y");//inicializa el objeto DatePicker//
            
            /*Dario_---Para crear Archivos de Excel---*/
            objImpDatXml = new Librerias.ZafUtil.ZafImpDatXml(objParSis);
            
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setTitulo("Fecha de vencimiento");
            objSelFec.setCheckBoxChecked(false);
            panspnFil.add(objSelFec);
            objSelFec.setBounds(24, 262, 472, 72);
            
            
            //Ocultar objetos de empresa para cuando se carga por empresa//
            if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
            {
                lblEmp.setVisible(true);
                txtCodEmp.setVisible(true);
                txtNomEmp.setVisible(true);
                butEmp.setVisible(true);
            }
            else
            {
                lblEmp.setVisible(false);
                txtCodEmp.setVisible(false);
                txtNomEmp.setVisible(false);
                butEmp.setVisible(false);
            }
            
            butImp.setVisible(false);
            butVisPre.setVisible(false);
            //Obtener la fecha del servidor. Luego de un anï¿½lisis se tomï¿½ la decisiï¿½n de obtener la fecha del servidor al cargar el formulario y no cada vez que se efectï¿½a una consulta.
            datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            if (datFecSer==null)
                datFecSer=new java.util.Date();

            //txtCodTipDoc.setVisible(false);
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(25);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");//0
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");//1
            vecCab.add(INT_TBL_DAT_IDE_CLI,"Ruc.Ced.");//2
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Nom.Cli.");//3
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");//4
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");//5
            vecCab.add(INT_TBL_DAT_TIP_DOC,"Tip.Doc.");//6
            vecCab.add(INT_TBL_DAT_NOM_DOC,"Des.Doc.");//7
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");//8
            vecCab.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");//9
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");//10
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");//11
            vecCab.add(INT_TBL_DAT_DIA_CRE,"Día.Cré.");//12
            vecCab.add(INT_TBL_DAT_FEC_VEN,"Fec.Ven.");//13
            vecCab.add(INT_TBL_DAT_POR_RET,"Por.Ret.");//14
            vecCab.add(INT_TBL_DAT_VAL_DOC,"Val.Doc.");//15
            vecCab.add(INT_TBL_DAT_VAL_ABO,"Val.Abo.");//16
            vecCab.add(INT_TBL_DAT_VAL_PEN,"Val.Pen.");//17

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selecciï¿½n.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            
            //Configurar JTable: Establecer el menï¿½ de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            ////Configurar JTable: Establecer el ancho de las columnas.////
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();            
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_IDE_CLI).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(250);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_DOC).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_FEC_VEN).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_POR_RET).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_VAL_ABO).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setPreferredWidth(90);
            
            
            ////Configurar JTable: Establecer el tipo de reordenamiento de columnas.////
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            ////ConfigurarJTable: Ocultar Columnas de Sistemas////
            ocultaCol(INT_TBL_DAT_IDE_CLI);
            ocultaCol(INT_TBL_DAT_TIP_DOC);
            ocultaCol(INT_TBL_DAT_COD_DOC);
            ocultaCol(INT_TBL_DAT_COD_REG);
            
            ////Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.////
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            ////Configurar JTable: Editor de bï¿½squeda.////
            objTblBus=new ZafTblBus(tblDat);
            
            ////Configurar JTable: Renderizar celdas.////
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLbl);

            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();//inincializo el renderizador
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);//alineacion del contenido de la celda
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);//formato de la celda, este es numero
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);            
            tcmAux.getColumn(INT_TBL_DAT_VAL_ABO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setCellRenderer(objTblCelRenLbl);
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer relaciónn entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_VAL_DOC, INT_TBL_DAT_VAL_ABO, INT_TBL_DAT_VAL_PEN};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
            
            //Configurar Ventana de Consulta de Empresa//
            configurarVenConEmp();
            
            //Configurar Ventana de Consulta de Cliente//
            configurarVenConCli();
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            objTblCelRenLbl=null;
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
     * mostrar los "Empresa".
     */
    private boolean configurarVenConEmp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_ruc");
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
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_emp, a1.tx_ruc, a1.tx_nom, a1.tx_dir";
            strSQL+=" FROM tbm_emp AS a1";
            ///strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" ORDER BY a1.co_emp";
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoEmp.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
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
            //Armar la sentencia SQL.
            strSQL="";
            
           
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
               //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + txtCodEmp.getText();
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            else
            {
//                //Armar la sentencia SQL.
//                strSQL="";
//                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
//                strSQL+=" FROM tbm_cli AS a1";
//                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//                strSQL+=" AND a1.st_reg='A'";
//                strSQL+=" ORDER BY a1.tx_nom";
                
                /*Query mejorado para consultar Clientes filtrado por local y empresa*/            
                //Armar la sentencia SQL.            
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.st_reg='A'";
                    strSQL+=" ORDER BY a1.tx_nom";
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
                }
            }
            
//            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
//            strSQL+=" FROM tbm_cli AS a1";            
//            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
//                strSQL+=" WHERE a1.co_emp=" + txtCodEmp.getText();
//            else
//                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//            
//            if (objParSis.getCodigoMenu()==319)
//                strSQL+=" AND a1.st_cli='S'";
//            else
//                strSQL+=" AND a1.st_prv='S'";
//            strSQL+=" ORDER BY a1.tx_nom";
            
            
            
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

    
    private void ocultaCol(int intCol)
    {
        tblDat.getColumnModel().getColumn(intCol).setWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);         
    
    }
    
    private void mostrarCol(int intCol, int tamCol)
    {
        tblDat.getColumnModel().getColumn(intCol).setWidth(tamCol);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(tamCol);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(tamCol);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(tamCol);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);
    
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
    private boolean mostrarVenConEmp(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(3));
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Cï¿½digo".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(3));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(3));
                        }
                        else
                        {
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(3));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(2);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(3));
                        }
                        else
                        {
                            txtNomEmp.setText(strNomEmp);
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
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Cï¿½digo".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
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
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
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
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtDesLarCli.setText(strDesLarCli);
                        }
                    }
                    break;
                case 3: //Bï¿½squeda directa por "Numero de Cedula".
                    if (vcoCli.buscar("a1.tx_ide", txtIdeCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(1);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtIdeCli.setText(strIdeCli);
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_cli as CODCLI, a4.tx_ide AS IDECLI, a4.tx_nom AS NOMCLI, a1.co_emp AS CODEMP, a1.co_loc AS CODLOC, a1.co_tipdoc AS TIPDOC, ";
                strSQL+=" a3.tx_descor AS NOMDOC, a1.co_doc AS CODDOC, a2.co_reg AS CODREG, a1.ne_numdoc AS NUMDOC, a1.fe_doc AS FECDOC,a2.ne_diacre AS DIACRE, ";
                strSQL+=" a2.fe_ven AS FECVEN, round(a2.nd_porret,2) AS PORRET, a2.mo_pag AS VALDOC, a2.nd_abo AS VALABO, round(a2.mo_pag+a2.nd_abo,4) AS VALPEN ";                
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";
                strSQL+=" INNER JOIN tbm_cabForPag AS a6 ON (a1.co_emp=a6.co_emp AND a1.co_forpag=a6.co_forpag)";
                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') ";                
                strSQL+=" AND (a2.mo_pag+a2.nd_abo) <> 0 ";
                strSQL+=" AND a3.ne_mod in (1, 3)";
                strSQL+=FilSql();
                strSQL+=" ORDER BY a4.tx_nom, a1.co_loc, a1.co_tipdoc, a2.co_reg";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {                        
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("CODCLI"));
                        vecReg.add(INT_TBL_DAT_IDE_CLI,rst.getString("IDECLI"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("NOMCLI"));
                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("CODEMP"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("CODLOC"));
                        vecReg.add(INT_TBL_DAT_TIP_DOC,rst.getString("TIPDOC"));
                        vecReg.add(INT_TBL_DAT_NOM_DOC,rst.getString("NOMDOC"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("CODDOC"));
                        vecReg.add(INT_TBL_DAT_COD_REG,rst.getString("CODREG"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("NUMDOC"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("FECDOC"));
                        vecReg.add(INT_TBL_DAT_DIA_CRE,rst.getString("DIACRE"));
                        vecReg.add(INT_TBL_DAT_FEC_VEN,rst.getString("FECVEN"));
                        vecReg.add(INT_TBL_DAT_POR_RET,rst.getString("PORRET"));
                        vecReg.add(INT_TBL_DAT_VAL_DOC,rst.getString("VALDOC"));
                        vecReg.add(INT_TBL_DAT_VAL_ABO,rst.getString("VALABO"));
                        vecReg.add(INT_TBL_DAT_VAL_PEN,rst.getString("VALPEN"));
                        vecDat.add(vecReg);
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
                tblDat.setModel(objTblMod);
                vecDat.clear();
                //Calcular totales.
                objTblTot.calcularTotales();
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
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
    
    
    private String FilSql() 
    {
        String strAux = "";
        String strFecSis, strFecIni, strFecsisFor, strFecVen, strFecAux;
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
        
        //Obtener la condiciï¿½n.
        strAux="";
        //Condicion para filtro por cliente
        if (txtCodCli.getText().length()>0)
            strAux+=" AND a1.co_cli=" + txtCodCli.getText();

        ////////Condicion para filtro por cliente en un rango especifico
        if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
            strAux+=" AND ((LOWER(a4.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a4.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";

        //CondiciÃ³n para filtro para documentos vencidos//
        if (chkMosDocVen.isSelected())
        {
            strFecAux=objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaHoraBaseDatos());
            strAux+=" AND a2.fe_ven <= '" + strFecAux + "'";
        }

        //CondiciÃ³n para filtro por Retenciones//
        if (!chkMosRet.isSelected())
        {
            //strAux+=" AND (a2.nd_porRet <> 0)";
            strAux+=" AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
        }

        //CondiciÃ³n para filtro por Documento sin Abonos//
        if (chkMosDocSinAbo.isSelected())
        {
            strAux+=" AND (a2.nd_abo = 0)";
        }

        
        //CondiciÃ³n para filtro por Tipo de Forma de Pago//
//        if (chkNegociacion.isSelected())
//        {
//            ///strAux+=" OR (a6.ne_tipforpag = 0)";
//            strAux+=" AND (a1.co_forpag = 0)";
//        }
//        else if (chkContado.isSelected())
//        {
//            ///strAux+=" OR (a6.ne_tipforpag = 1)";
//            strAux+=" AND (a1.co_forpag = 1)";
//        }
//        else if (chkCheque.isSelected())
//        {
//            ///strAux+=" OR (a6.ne_tipforpag = 2)";
//            strAux+=" AND (a1.co_forpag = 2)";
//        }
//        else if (chkCredito.isSelected())
//        {
//            ///strAux+=" OR (a6.ne_tipforpag = 3)";
//            strAux+=" AND (a1.co_forpag = 3)";
//        }
        
        
        if (chkNegociacion.isSelected() && (!chkContado.isSelected()) && (!chkCheque.isSelected()) && (!chkCredito.isSelected()))
            strAux+=" AND ( (a6.ne_tipforpag = 0) )";
        else if (chkContado.isSelected() && (!chkNegociacion.isSelected()) && (!chkCheque.isSelected()) && (!chkCredito.isSelected()))
            strAux+=" AND ( (a6.ne_tipforpag = 1) )";
        else if (chkCheque.isSelected() && !(chkNegociacion.isSelected()) && !(chkContado.isSelected()) && !(chkCredito.isSelected()))
            strAux+=" AND ( (a6.ne_tipforpag = 2) )";
        else if (chkCredito.isSelected() && !(chkNegociacion.isSelected()) && !(chkContado.isSelected()) && !(chkCheque.isSelected()))
            strAux+=" AND ( (a6.ne_tipforpag = 3) )";
                
        else if (chkNegociacion.isSelected() && chkContado.isSelected() && (!chkCheque.isSelected()) && (!chkCredito.isSelected()))
            strAux+=" AND ( (a6.ne_tipforpag = 0) OR (a6.ne_tipforpag = 1) )";
        else if (chkNegociacion.isSelected() && chkCheque.isSelected() && (!chkContado.isSelected()) && (!chkCredito.isSelected()))
            strAux+=" AND ( (a6.ne_tipforpag = 0) OR (a6.ne_tipforpag = 2) )";
        else if (chkNegociacion.isSelected() && chkCredito.isSelected() && (!chkContado.isSelected()) && (!chkCheque.isSelected()))
            strAux+=" AND ( (a6.ne_tipforpag = 0) OR (a6.ne_tipforpag = 3) )";
        else if (chkNegociacion.isSelected() && chkContado.isSelected() && chkCheque.isSelected() && (!chkCredito.isSelected()))
            strAux+=" AND ( (a6.ne_tipforpag = 0) OR (a6.ne_tipforpag = 1) OR (a6.ne_tipforpag = 2) )";
        else if (chkNegociacion.isSelected() && chkContado.isSelected() && chkCredito.isSelected() && (!chkCheque.isSelected()))
            strAux+=" AND ( (a6.ne_tipforpag = 0) OR (a6.ne_tipforpag = 1) OR (a6.ne_tipforpag = 3) )";
        else if (chkContado.isSelected() && chkCheque.isSelected() && (!chkNegociacion.isSelected()) && (!chkCredito.isSelected()))
            strAux+=" AND ( (a6.ne_tipforpag = 1) OR (a6.ne_tipforpag = 2) )";
        else if (chkContado.isSelected() && chkCredito.isSelected() && (!chkNegociacion.isSelected()) && (!chkCheque.isSelected()))
            strAux+=" AND ( (a6.ne_tipforpag = 1) OR (a6.ne_tipforpag = 3) )";
        else if (chkContado.isSelected() && chkCheque.isSelected() && chkCredito.isSelected() && (!chkNegociacion.isSelected()))
            strAux+=" AND ( (a6.ne_tipforpag = 1) OR (a6.ne_tipforpag = 2) OR (a6.ne_tipforpag = 3) )";
        else if (chkCheque.isSelected() && chkCredito.isSelected() && (!chkNegociacion.isSelected()) && (!chkContado.isSelected()))
            strAux+=" AND ( (a6.ne_tipforpag = 2) OR (a6.ne_tipforpag = 3) )";
        else if (chkNegociacion.isSelected() && chkContado.isSelected() && chkCheque.isSelected() && chkCredito.isSelected())
            strAux+=" AND ( (a6.ne_tipforpag = 0) OR (a6.ne_tipforpag = 1) OR (a6.ne_tipforpag = 2) OR (a6.ne_tipforpag = 3) )";
        
        
        
        
        //Condicion para filtro por tipo de documento
        if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            strAux+=" AND a1.co_emp=" + txtCodEmp.getText();
        else
            strAux+=" AND a1.co_emp=" + intCodEmp;
        
        //Obtener la condiciï¿½n.

//        if(intCodEmpGrp==intCodEmp)
//        {
//                ////////Condicion para filtro por cliente
//                if (txtCodCli.getText().length()>0)
//                    strAux+=" AND a4.tx_ide = '" + txtIde.getText() + "'";
//        }
//        else
//        {
//                ////////Condicion para filtro por cliente
//                if (txtCodCli.getText().length()>0)
//                    strAux+=" AND a1.co_cli=" + txtCodCli.getText();                
//        }
//        
//        ////////Condicion para filtro por tipo de documento
//        if (txtCodTipDoc.getText().length()>0)
//            strAux+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText();
//        
        ///Filtro para fechas///
        if (objSelFec.isCheckBoxChecked())
        {
            switch (objSelFec.getTipoSeleccion())
            {
                case 0: //Bï¿½squeda por rangos
                    strAux+=" AND a2.fe_ven BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    strAux+=" AND a2.fe_ven<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    strAux+=" AND a2.fe_ven>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 3: //Todo.
                    break;
            }
        }
                
//        ///FILTRO PARA MOSTRAR CREDITOS Y DEBITOS///
//        if ( !(chkMosCre.isSelected() && chkMosDeb.isSelected()) )
//        {
//            if (chkMosCre.isSelected())
//                strAux+=" AND a3.tx_natDoc='I'";
//            else
//                strAux+=" AND a3.tx_natDoc='E'";
//        }
//        
//        ///FILTRO PARA DOCUMENTOS VENCIDOS///
//        if (chkMosDocVen.isSelected())
//        {
//            strFecVen=objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
//            strAux+=" AND a2.fe_ven<='" + strFecVen + "'";
//        }
//        ///FILTRO PARA MOSTRAR RETENCIONES///
//        if (!chkMosRet.isSelected())
//        {
//            strAux+=" AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
//        }

        ////////Condicion para Tipos de Documentos////////
        if((chkMosDocSinRes.isSelected()) == true)   ///este es el caso de que el chk CON RESPALDO este activo///
        {                        
            ///strAux+= " AND a2.nd_monchq > 0 ";
            strAux+= " AND a2.nd_monchq IS NULL ";
        }
        else
        ///if((chkMosDocSinRes.isSelected()) == true)   /// este es el caso de que el chk SIN RESPALDO este activo///
        {
            ///strAux+= " AND a2.nd_monchq IS NULL ";
            strAux+= " AND a2.nd_monchq > 0 ";
        }
        
        return strAux;
    }
    
    /**
     * Esta funciï¿½n muestra un mensaje informativo al usuario. Se podrï¿½a utilizar
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
     * Esta funciï¿½n muestra un mensaje "showConfirmDialog". Presenta las opciones
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
     * Esta funciï¿½n muestra un mensaje de error al usuario. Se podrï¿½a utilizar
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
     * Esta clase crea un hilo que permite manipular la interface grï¿½fica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que estï¿½ ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podrï¿½a presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estarï¿½a informado en todo
     * momento de lo que ocurre. Si se desea hacer ï¿½sto es necesario utilizar ï¿½sta clase
     * ya que si no sï¿½lo se apreciarï¿½a los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {            
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sï¿½lo cuando haya datos.
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
     * Esta clase crea un hilo que permite manipular la interface grï¿½fica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que estï¿½ ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podrï¿½a presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estarï¿½a informado en todo
     * momento de lo que ocurre. Si se desea hacer ï¿½sto es necesario utilizar ï¿½sta clase
     * ya que si no sï¿½lo se apreciarï¿½a los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUIRpt extends Thread
    {
        private int intIndFun;
        
        public ZafThreadGUIRpt()
        {
            intIndFun=0;
        }
        
        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botï¿½n "Imprimir".
                    ///objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                    ///objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botï¿½n "Vista Preliminar".
                    ///objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    ///objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUI=null;
        }
        
        /**
         * Esta funciï¿½n establece el indice de la funciï¿½n a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta funciï¿½n sirve para determinar
         * la funciï¿½n que debe ejecutar el Thread.
         * @param indice El indice de la funciï¿½n a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }
    
    
    
    /**
     * Esta funciï¿½n permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresiï¿½n directa.
     * <LI>1: Impresiï¿½n directa (Cuadro de dialogo de impresiï¿½n).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt)
    {
        String strRutRpt, strNomRpt, strFecHorSer, strBan="";
        int i, intNumTotRpt;
        boolean blnRes=true;
        
        int intCodEmp=objParSis.getCodigoEmpresa();
        int intCodLoc=objParSis.getCodigoLocal();
        String CodCli = txtCodCli.getText();
        String NomCli = txtDesLarCli.getText();
        String NomUsr = objParSis.getNombreUsuario();
        String strCodEmp = String.valueOf(intCodEmp);
        
        
//        STRBAN="";
//        
//        STRBAN=FilSql();       
        
        
        try
        {
            objRptSis.cargarListadoReportes();
            objRptSis.show();
            
            //Inicializar los parametros que se van a pasar al reporte.
            java.util.Map mapPar=new java.util.HashMap();
            
            if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
            {
                //Obtener la fecha y hora del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecHorSer=objUti.formatearFecha(datFecAux, "yyyy/MMM/dd HH:mm:ss");
                datFecAux=null;
                
                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            case 92:
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                mapPar.put("CodEmp", ""+strCodEmp);
                                mapPar.put("CodCli", ""+CodCli);
                                mapPar.put("NomCli", ""+NomCli);
                                mapPar.put("FecImp", ""+strFecHorSer);
                                mapPar.put("DirRpt", ""+strRutRpt);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                            case 261:
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                mapPar.put("CodEmp", ""+strCodEmp);
                                mapPar.put("CodCli", ""+CodCli);
                                mapPar.put("NomCli", ""+NomCli);
                                mapPar.put("FecImp", ""+strFecHorSer);
                                mapPar.put("DirRpt", ""+strRutRpt);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    private void calcularAboTot()
    {
        double dblVal=0, dblTot=0;
        int intFilPro, i;
        String strConCel;
        try
        {
            intFilPro=objTblMod.getRowCountTrue();
            for (i=0; i<intFilPro; i++)
            {
                strConCel=(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_ABO)==null)?"":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_ABO).toString();
                dblVal=(objUti.isNumero(strConCel))?Double.parseDouble(strConCel):0;
                dblTot+=dblVal;
            }
            //Calcular la diferencia.
            //txtMonDoc.setText("" + objUti.redondeo(dblTot,objParSis.getDecimalesMostrar()));
        }
        catch (NumberFormatException e)
        {
            //txtMonDoc.setText("[ERROR]");
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
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_DAT_IDE_CLI:
                    strMsg="Identificación del cliente";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_NOM_DOC:
                    strMsg="Nombre del Tipo de Documento";
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
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
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
                    strMsg="Valor abonado";
                    break;
                case INT_TBL_DAT_VAL_PEN:
                    strMsg="Valor pendiente";
                    break;                
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}