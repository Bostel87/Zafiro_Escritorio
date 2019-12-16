/* 
 * ZafCxC18.java
 *
 * Created on 17 de octubre de 2006, 17:10 PM
 * Dario Cardenas
 * Programa Listado de Retenciones
 * FUE MODIFICADO EL 28/NOVIEMBRE/2007
 */
package CxC.ZafCxC18;
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

/**
 *
 * @author  Dario Xavier Cardenas Landin
 */
public class ZafCxC18 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                        //Lï¿½nea
    static final int INT_TBL_DAT_SEL=1;                        //Linea para seleccionar
    static final int INT_TBL_DAT_COD_EMP=2;                    //Cï¿½digo de la empresa.
    static final int INT_TBL_DAT_COD_LOC=3;                    //Cï¿½digo del local.
    static final int INT_TBL_DAT_COD_CLI=4;                    //Cï¿½digo del cliente.
    static final int INT_TBL_DAT_NOM_CLI=5;                    //Nombre del cliente.
    static final int INT_TBL_DAT_POR_RET=6;                    //Porcentaje de Retencion
    static final int INT_TBL_DAT_COD_DOC=7;                    //Cï¿½digo del documento.
    static final int INT_TBL_DAT_COD_REG=8;                    //Cï¿½digo del registro.
    static final int INT_TBL_DAT_NUM_DOC=9;                    //Nï¿½mero de documento.
    static final int INT_TBL_DAT_FEC_DOC=10;                   //Fecha del documento.
    static final int INT_TBL_DAT_VAL_DOC=11;                   //Valor del documento.
    static final int INT_TBL_DAT_VAL_VEN=12;                   //Valor por vencer.
    static final int INT_TBL_DAT_VAL_ABO=13;                   //Valor por Abonar
    static final int INT_TBL_DAT_NUM_RET=14;                   //Valor por Abonar
    static final int INT_TBL_DAT_FEC_VEN=15;                   //Valor por Abonar
    static final int INT_TBL_DAT_FEC_REC=16;                   //Valor por Abonar
    static final int INT_TBL_DAT_VAL_RET=17;                   //Valor por Abonar
    
    //Variables
    private ZafDatePicker dtpDat;                      //esto es para calcular el numero de dias transcurridos
    private ZafUtil objAux;
   // private java.util.Date datFecAux;
    
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
    private ZafTblBus objTblBus;                        //Editor de bï¿½squeda.
    private ZafTblTot objTblTot;                        //JTable de totales.
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private String strSQL="", strAux="", STRBAN="";
    private Vector vecDat, vecCab, vecReg , vecAux;
    private boolean blnCon;                             //true: Continua la ejecuciï¿½n del hilo.
    private String strCodCli, strDesLarCli,strCodEmp, strDesLarEmp,strCodTipDoc, strTipDocNom,strTipDocCor, strDesLarEmpTipDoc;             //Contenido del campo al obtener el foco.
    private int intCodLocAux, intClickCon=0;
    private ZafTblCelRenChk objTblCelRenChkMain;
    private ZafTblCelEdiChk objTblCelEdiChkMain;
    private ZafVenCon vcoCli,vcoEmp, vcoLoc;
    private ZafRptSis objRptSis;                        //Reportes del Sistema.
    private ZafSelFec objSelFec;
    int TIPDOCRET = 1;
    private String strCodLoc, strNomLoc;
    
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblCelEdiTxt objTblCelEdiTxtNumDoc;    
    private Librerias.ZafDate.ZafDatePicker txtFecDoc, txtFecDocDes, txtFecDocHas;
    javax.swing.JInternalFrame jfrThis; //Hace referencia a this

    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCxC18(ZafParSis obj) 
    {
        try
        {
            initComponents();
            //Inicializar objetos.//
            objParSis=(ZafParSis)obj.clone();
            jfrThis=this;
            txtFecDoc=new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
            txtFecDocDes=new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
            txtFecDocHas=new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
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
        chkMosDocAbi = new javax.swing.JCheckBox();
        chkMosDocCer = new javax.swing.JCheckBox();
        lblLoc = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtDesLarLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
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
        panFil.add(optTod);
        optTod.setBounds(4, 24, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los clientes que cumplan el criterio seleccionado"); // NOI18N
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(4, 44, 400, 20);

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
        panNomCli.setBounds(24, 84, 660, 52);

        lblCli.setText("Cliente:"); // NOI18N
        lblCli.setToolTipText("Beneficiario"); // NOI18N
        panFil.add(lblCli);
        lblCli.setBounds(24, 64, 120, 20);

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
        txtCodCli.setBounds(144, 64, 56, 20);

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
        txtDesLarCli.setBounds(200, 64, 460, 20);

        butCli.setText("..."); // NOI18N
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(660, 64, 20, 20);

        panEstados.setBorder(javax.swing.BorderFactory.createTitledBorder("Estados del Documento"));
        panEstados.setPreferredSize(new java.awt.Dimension(460, 52));
        panEstados.setLayout(null);

        chkMosDocAbi.setSelected(true);
        chkMosDocAbi.setText("Mostrar los documentos abiertos"); // NOI18N
        chkMosDocAbi.setToolTipText("Documentos por Recaudar(Abiertos)"); // NOI18N
        chkMosDocAbi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDocAbiActionPerformed(evt);
            }
        });
        panEstados.add(chkMosDocAbi);
        chkMosDocAbi.setBounds(20, 18, 280, 20);

        chkMosDocCer.setText("Mostrar los documentos cerrados"); // NOI18N
        chkMosDocCer.setToolTipText("Documentos Recaudados (cerrados)"); // NOI18N
        chkMosDocCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDocCerActionPerformed(evt);
            }
        });
        panEstados.add(chkMosDocCer);
        chkMosDocCer.setBounds(350, 18, 280, 20);

        panFil.add(panEstados);
        panEstados.setBounds(24, 136, 660, 52);

        lblLoc.setText("Local:"); // NOI18N
        lblLoc.setToolTipText("Local"); // NOI18N
        panFil.add(lblLoc);
        lblLoc.setBounds(4, 4, 120, 20);

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
        txtCodLoc.setBounds(124, 4, 92, 20);

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
        txtDesLarLoc.setBounds(216, 4, 444, 20);

        butLoc.setText("..."); // NOI18N
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panFil.add(butLoc);
        butLoc.setBounds(660, 4, 20, 20);

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

        butVisPre.setText("Vista Preliminar"); // NOI18N
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
        if(txtCodCli.getText().equals(""))
        {
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Cliente/Proveedor </FONT> debe ser <BR><FONT COLOR=\"blue\">Ingresado</FONT> antes de Realizar una Impresion...</HTML>");
            txtCodCli.requestFocus();
        }
        else
        {
            if (objThrGUIRpt==null)
            {
                objThrGUIRpt=new ZafThreadGUIRpt();
                objThrGUIRpt.setIndFunEje(1);
                objThrGUIRpt.start();
            }
        }
    }//GEN-LAST:event_butImpActionPerformed

    private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed
        if(txtCodCli.getText().equals(""))
        {
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Cliente/Proveedor </FONT> debe ser <BR><FONT COLOR=\"blue\">Ingresado</FONT> antes de Realizar una Vista Preliminar...</HTML>");
            txtCodCli.requestFocus();
        }
        else
        {
            if (objThrGUIRpt==null)
            {
                objThrGUIRpt=new ZafThreadGUIRpt();
                objThrGUIRpt.setIndFunEje(1);
                objThrGUIRpt.start();
            }
        }
    }//GEN-LAST:event_butVisPreActionPerformed

    private void chkMosDocCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDocCerActionPerformed
        if (!chkMosDocAbi.isSelected())
        {
            chkMosDocCer.setSelected(true);
        }

    }//GEN-LAST:event_chkMosDocCerActionPerformed

    private void chkMosDocAbiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDocAbiActionPerformed
         if (!chkMosDocCer.isSelected())
        {
            chkMosDocAbi.setSelected(true);
        }        
    }//GEN-LAST:event_chkMosDocAbiActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        if (optFil.isSelected())
        {

        }
    }//GEN-LAST:event_optFilItemStateChanged

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        mostrarVenConCli(0);
        //Seleccionar el JRadioButton de filtro si es necesario.//
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
            txtDesLarCli.setText("");
            txtNomCliDes.setText("");
            txtNomCliHas.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acciï¿½n de acuerdo a la etiqueta del botï¿½n ("Consultar" o "Detener").
        intClickCon++;
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

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        txtCodLoc.transferFocus();
}//GEN-LAST:event_txtCodLocActionPerformed

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        strCodLoc=txtCodLoc.getText();
        txtCodLoc.selectAll();
}//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        // TODO add your handling code here:
        if (txtCodLoc.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc)) {
                if (txtCodLoc.getText().equals("")) {
                    txtCodLoc.setText("");
                    txtDesLarLoc.setText("");
                } else {
                    mostrarVenConLoc(1);
                }
            } else
                txtCodLoc.setText(strCodLoc);
        }
}//GEN-LAST:event_txtCodLocFocusLost

    private void txtDesLarLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarLocActionPerformed
        // TODO add your handling code here:
        txtDesLarLoc.transferFocus();
}//GEN-LAST:event_txtDesLarLocActionPerformed

    private void txtDesLarLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarLocFocusGained
        // TODO add your handling code here:
        strNomLoc=txtDesLarLoc.getText();
        txtDesLarLoc.selectAll();
}//GEN-LAST:event_txtDesLarLocFocusGained

    private void txtDesLarLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarLocFocusLost
        // TODO add your handling code here:
        if (txtDesLarLoc.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesLarLoc.getText().equalsIgnoreCase(strNomLoc)) {
                if (txtDesLarLoc.getText().equals("")) {
                    txtCodLoc.setText("");
                    txtDesLarLoc.setText("");
                } else {
                    mostrarVenConLoc(2);
                }
            } else
                txtDesLarLoc.setText(strNomLoc);
        }
}//GEN-LAST:event_txtDesLarLocFocusLost

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        mostrarVenConLoc(0);
}//GEN-LAST:event_butLocActionPerformed

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
    private javax.swing.JButton butImp;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butVisPre;
    private javax.swing.JCheckBox chkMosDocAbi;
    private javax.swing.JCheckBox chkMosDocCer;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
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
    private javax.swing.JPanel panEstados;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtDesLarLoc;
    private javax.swing.JTextField txtNomCliDes;
    private javax.swing.JTextField txtNomCliHas;
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
            this.setTitle(strAux + " v0.14");
            lblTit.setText(strAux);
            dtpDat=new ZafDatePicker(((javax.swing.JFrame)this.getParent()), "d/m/y");//inicializa el objeto DatePicker            
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxChecked(false);
            panFil.add(objSelFec);
            objSelFec.setBounds(24, 188, 472, 72);
            /*Configurar Objeto para llamar al Reporte*/
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            //Configurar las ZafVenCon.//
            configurarVenConCli();
            configurarVenConLoc();
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(30);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_SEL,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente");
            vecCab.add(INT_TBL_DAT_POR_RET,"% Ret.");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_VAL_DOC,"Val.Doc.");
            vecCab.add(INT_TBL_DAT_VAL_VEN,"Val.Pen.");
            vecCab.add(INT_TBL_DAT_VAL_ABO,"Abono");
            vecCab.add(INT_TBL_DAT_NUM_RET,"Num.Ret.");
            vecCab.add(INT_TBL_DAT_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_DAT_FEC_REC,"Fec.Rec.");
            vecCab.add(INT_TBL_DAT_VAL_RET,"Val.Ret.");
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
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_SEL).setPreferredWidth(15);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(50);//antes estaba 30
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(50);//antes estaba 30
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(100);//antes estaba 70
            tcmAux.getColumn(INT_TBL_DAT_POR_RET).setPreferredWidth(40);//antes estaba 70
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);//antes 80
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VAL_VEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VAL_ABO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_RET).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_VEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_REC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_RET).setPreferredWidth(60);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            ocultaCol(INT_TBL_DAT_COD_REG);
            ocultaCol(INT_TBL_DAT_SEL);
            ocultaCol(INT_TBL_DAT_COD_DOC);
            ocultaCol(INT_TBL_DAT_FEC_VEN);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Editor de bï¿½squeda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setCellRenderer(objTblCelRenLbl);

            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();//inincializo el renderizador
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);//alineacion del contenido de la celda
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);//formato de la celda, este es numero
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_VEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_ABO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_RET).setCellRenderer(objTblCelRenLbl);

            
            //para hacer editable las celdas
            vecAux=new Vector();                       
            vecAux.add("" + INT_TBL_DAT_SEL);
            objTblMod.setColumnasEditables(vecAux);
            
            //vecAux=null;
            
            ////setEditable(true);////
            objTblEdi=new ZafTblEdi(tblDat);
            
            
            ////Configurar JTable: Establecer relaciï¿½n entre el JTable de datos y JTable de totales.////
            int intCol[]={INT_TBL_DAT_VAL_DOC, INT_TBL_DAT_VAL_VEN, INT_TBL_DAT_VAL_ABO};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
                        
            /////para activar el campo de selccion/////
            objTblCelRenChkMain=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_SEL).setCellRenderer(objTblCelRenChkMain);
            objTblCelEdiChkMain=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_SEL).setCellEditor(objTblCelEdiChkMain);
            objTblCelEdiChkMain.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblCelEdiChkMain.isChecked())
                    {                        
                            objTblMod.setValueAt(objTblMod.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_VAL_VEN),tblDat.getSelectedRow(),INT_TBL_DAT_VAL_ABO);
                    }
                    else
                    {
                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_VAL_ABO);
                    }
                    calcularAboTot();
                }
                });
            
         objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO).setCellEditor(objTblCelEdiTxt);                    
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxt.getText().equals(""))
                    {
                        objTblMod.setValueAt( new Boolean(false), tblDat.getSelectedRow(), INT_TBL_DAT_SEL);
                    }
                    else
                    {
                        objTblMod.setValueAt(new Boolean(true), tblDat.getSelectedRow(), INT_TBL_DAT_SEL);
                    }
                    calcularAboTot();

                }

                });   
            
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
     * Esta funciï¿½n permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intCodEmp, intCodLoc;
        String strFecSis;
        java.util.Date datFecSer, datFecAux;
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
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
                ////////Condicion para filtro por local//////
                if (txtCodLoc.getText().length()>0)
                    strAux+=" AND a1.co_loc=" + txtCodLoc.getText();
                ////////Condicion para filtro por cliente///////
                if (txtCodCli.getText().length()>0)
                    strAux+=" AND a1.co_cli=" + txtCodCli.getText();
                ////////Condicion para filtro por cliente en un rango especifico///////
                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a4.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a4.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                ///////////para filtrar por fechas Desde y Hasta//////////////
                if (objSelFec.isCheckBoxChecked())
                {
                    switch (objSelFec.getTipoSeleccion())
                    {
                        case 0: //BÃ³squeda por rangos
                            strAux+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAux+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAux+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
                //////////para filtrar por doucmentos Abiertos o Cerrados////////////
                if ( !(chkMosDocAbi.isSelected() && chkMosDocCer.isSelected()) )
                {
                        ///chk para documentos abiertos///
                        if (chkMosDocAbi.isSelected())
                        //strAux+=" AND (a2.mo_pag+a2.nd_abo)=0";
                        strAux+=" AND (a2.mo_pag+a2.nd_abo)<>0"; /// AND (a2.nd_abo=0)";
                }                
                //Condicion para filtro por tipo de documento
                if (objParSis.getCodigoEmpresa()==4)
                {
                    strAux+=" AND a1.co_emp=4";
                }
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_nom, round(a2.nd_porret,0) as PorRet, a2.tx_numchq, a2.fe_venchq, a2.fe_recchq, a2.nd_monchq, ";
                strSQL+=" a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc,a2.mo_pag, round(a2.mo_pag+a2.nd_abo,4) AS val_pen, round(a2.nd_abo,4) AS val_abo ";                
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";
                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') ";                
                strSQL+=" AND a3.ne_mod in (1, 3) AND a2.nd_porret<>0";
                //strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";                
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                if(!(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())))
                {
                    strSQL+=" AND a1.co_loc=" + intCodLoc;  ///POR AHORA NO SE PROCESA POR LOCAL///
                }
                strSQL+=strAux;
                strSQL+=" ORDER BY a4.tx_nom, a1.fe_doc, a1.ne_numdoc, a2.co_reg ";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.//
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_SEL, "");
                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nom"));
			vecReg.add(INT_TBL_DAT_POR_RET,rst.getString("PorRet"));                        
                        vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_COD_REG,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_VAL_DOC,rst.getString("mo_pag"));
                        vecReg.add(INT_TBL_DAT_VAL_VEN,rst.getString("val_pen"));
                        vecReg.add(INT_TBL_DAT_VAL_ABO,rst.getString("val_abo"));
                        vecReg.add(INT_TBL_DAT_NUM_RET,rst.getString("tx_numchq"));
                        vecReg.add(INT_TBL_DAT_FEC_VEN,rst.getString("fe_venchq"));
                        vecReg.add(INT_TBL_DAT_FEC_REC,rst.getString("fe_recchq"));
                        vecReg.add(INT_TBL_DAT_VAL_RET,rst.getString("nd_monchq"));
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
                //Asignar vectores al modelo.//
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
    
    
    /**
     * Esta funciï¿½n permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */   
    private boolean cargarDetRegCer()
    {
        int intCodEmp, intCodLoc;
        String strFecSis;
        java.util.Date datFecSer, datFecAux;
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
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
                //Obtener la condiciï¿½n.
                strAux="";
                ////////Condicion para filtro por local//////
                if (txtCodLoc.getText().length()>0)
                    strAux+=" AND a1.co_loc=" + txtCodLoc.getText();
                ////////Condicion para filtro por cliente
                if (txtCodCli.getText().length()>0)
                    strAux+=" AND a1.co_cli=" + txtCodCli.getText();
                ////////Condicion para filtro por cliente en un rango especifico
                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a4.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a4.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                ///////////para filtrar por fechas Desde y Hasta//////////////
                if (objSelFec.isCheckBoxChecked())
                {
                    switch (objSelFec.getTipoSeleccion())
                    {
                        case 0: //BÃ³squeda por rangos
                            strAux+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAux+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAux+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
                //////////para filtrar por doucmentos Abiertos o Cerrados////////////
                if ( !(chkMosDocAbi.isSelected() && chkMosDocCer.isSelected()) )
                {
                    if (chkMosDocCer.isSelected())
                        //strAux+=" AND (a2.mo_pag+a2.nd_abo)<>0 AND (a2.nd_abo=0)";
                        strAux+=" AND (a2.mo_pag+a2.nd_abo)=0";
                }
                //Condicion para filtro por tipo de documento//
                if (objParSis.getCodigoEmpresa()==4)
                {
                    strAux+=" AND a1.co_emp=4";
                }
                //Armar la sentencia SQL.//
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_nom, round(a2.nd_porret,0) as PorRet, a2.tx_numchq, a2.fe_venchq, a2.fe_recchq, a2.nd_monchq,";
                strSQL+=" a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc,a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS val_pen, a2.nd_abo AS val_abo ";                
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";
                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') ";                
                strSQL+=" AND a3.ne_mod in (1, 3) AND a2.nd_porret<>0";
                //strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                if(!(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())))
                {
                    strSQL+=" AND a1.co_loc=" + intCodLoc;  ///POR AHORA NO SE PROCESA POR LOCAL///
                }
                strSQL+=strAux;
                strSQL+=" ORDER BY a4.tx_nom, a1.fe_doc, a1.ne_numdoc, a2.co_reg ";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.//
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_SEL, "");
                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nom"));
			vecReg.add(INT_TBL_DAT_POR_RET,rst.getString("PorRet"));                        
                        vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_COD_REG,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_VAL_DOC,rst.getString("mo_pag"));
                        vecReg.add(INT_TBL_DAT_VAL_VEN,rst.getString("val_pen"));
                        vecReg.add(INT_TBL_DAT_VAL_ABO,rst.getString("val_abo"));
                        vecReg.add(INT_TBL_DAT_NUM_RET,rst.getString("tx_numchq"));
                        vecReg.add(INT_TBL_DAT_FEC_VEN,rst.getString("fe_venchq"));
                        vecReg.add(INT_TBL_DAT_FEC_REC,rst.getString("fe_recchq"));
                        vecReg.add(INT_TBL_DAT_VAL_RET,rst.getString("nd_monchq"));
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
    ////final de funcion prueba///
    
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
            
/*Ultimo query para consultar Clientes*/            
//            //Armar la sentencia SQL.
//            strSQL="";
//            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
//            strSQL+=" FROM tbm_cli AS a1";
//            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
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
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Cï¿½digo".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
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

            /*Query mejorado para consultar LOCALES filtrado por local y empresa*/
            //Armar la sentencia SQL.
            if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
            {
                //Armar la sentencia SQL. camposObligatorios
                strSQL="";
                strSQL+="SELECT a1.co_loc, a1.tx_nom";
                strSQL+=" FROM tbm_loc AS a1";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE a1.co_emp IN (1,2,3,4)";
                else
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg IN ('A', 'P')";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc";
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

    
//    /**
//     * Esta funciï¿½n permite utilizar la "Ventana de Consulta" para seleccionar un
//     * registro de la base de datos. El tipo de bï¿½squeda determina si se debe hacer
//     * una bï¿½squeda directa (No se muestra la ventana de consulta a menos que no 
//     * exista lo que se estï¿½ buscando) o presentar la ventana de consulta para que
//     * el usuario seleccione la opciï¿½n que desea utilizar.
//     * @param intTipBus El tipo de bï¿½squeda a realizar.
//     * @return true: Si no se presentï¿½ ningï¿½n problema.
//     * <BR>false: En el caso contrario.
//     */
//    private boolean mostrarVenConCli(int intTipBus)///antigua funcion///
//    {
//        String strAli, strCam;
//        Librerias.ZafConsulta.ZafConsulta objVenCon;
//        boolean blnRes=true;
//        try
//        {
//            strAli="Cï¿½digo, Identificaciï¿½n, Nombre, Direcciï¿½n";
//            strCam="a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
//            //Armar la sentencia SQL.
//            strSQL="";
//            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
//            strSQL+=" FROM tbm_cli AS a1";
//            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//            strSQL+=" AND a1.st_cli='S'";
//            objVenCon=new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this), strAli, strCam, strSQL, "", objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            objVenCon.setTitle("Listado de clientes");
//            switch (intTipBus)
//            {
//                case 0: //Mostrar la ventana de consulta.
//                    objVenCon.show();
//                    if (objVenCon.acepto())
//                    {
//                        txtCodCli.setText(objVenCon.GetCamSel(1));
//                        txtDesLarCli.setText(objVenCon.GetCamSel(3));
//                    }
//                    break;
//                case 1: //Bï¿½squeda directa por "Cï¿½digo".
//                    if (objVenCon.buscar("LOWER(a1.co_cli) LIKE '" + txtCodCli.getText() + "'"))
//                    {
//                        txtCodCli.setText(objVenCon.GetCamSel(1));
//                        txtDesLarCli.setText(objVenCon.GetCamSel(3));
//                    }
//                    else
//                    {
//                        objVenCon.setFiltroConsulta(txtCodCli.getText());
//                        objVenCon.setSelectedTipBus(2);
//                        objVenCon.setSelectedCamBus(0);
//                        objVenCon.show();
//                        if (objVenCon.acepto())
//                        {
//                            txtCodCli.setText(objVenCon.GetCamSel(1));
//                            txtDesLarCli.setText(objVenCon.GetCamSel(3));
//                        }
//                        else
//                        {
//                            txtCodCli.setText(strCodCli);
//                        }
//                    }
//                    break;
//                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
//                    if (objVenCon.buscar("LOWER(a1.tx_nom) LIKE '" + txtDesLarCli.getText().toLowerCase() + "'"))
//                    {
//                        txtCodCli.setText(objVenCon.GetCamSel(1));
//                        txtDesLarCli.setText(objVenCon.GetCamSel(3));
//                    }
//                    else
//                    {
//                        objVenCon.setFiltroConsulta(txtDesLarCli.getText());
//                        objVenCon.setSelectedTipBus(2);
//                        objVenCon.setSelectedCamBus(2);
//                        objVenCon.show();
//                        if (objVenCon.acepto())
//                        {
//                            txtCodCli.setText(objVenCon.GetCamSel(1));
//                            txtDesLarCli.setText(objVenCon.GetCamSel(3));
//                        }
//                        else
//                        {
//                            txtDesLarCli.setText(strDesLarCli);
//                        }
//                    }
//                    break;
//            }
//        }
//        catch (Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }


    
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
        private int intIndFun;
        
        public ZafThreadGUI()
        {
            intIndFun=0;
        }
        
        public void run()
        {
                ///para cuando esta activo el chk de doc abiertos///
                if(chkMosDocAbi.isSelected())
                {                                        
                    TIPDOCRET = 1;
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

                ///*
                ///para cuando esta activo el chk de doc cerrados///
                if(chkMosDocCer.isSelected())
                {
                    TIPDOCRET = 2;
                    if (!cargarDetRegCer())
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
            objThrGUIRpt=null;
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
        String strCodEmp = String.valueOf(intCodEmp);
        String strCodLoc = String.valueOf(intCodLoc);
        STRBAN="";
        STRBAN=FilSql();
        //Inicializar los parametros que se van a pasar al reporte.
        java.util.Map mapPar=new java.util.HashMap();
        
        Connection conIns;
        try
        {
            conIns =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            try
            {
                if(conIns!=null)
                {
                    objRptSis.cargarListadoReportes();
                    objRptSis.show();

                    

                    if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
                    {
        //                //Obtener la fecha y hora del servidor.
        //                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        //                if (datFecAux==null)
        //                    return false;
        //                strFecHorSer=objUti.formatearFecha(datFecAux, "yyyy/MMM/dd HH:mm:ss");
        //                datFecAux=null;

                        intNumTotRpt=objRptSis.getNumeroTotalReportes();
                        for (i=0;i<intNumTotRpt;i++)
                        {
                            if (objRptSis.isReporteSeleccionado(i))
                            {
                                switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                                {
                                    case 90:
                                            strRutRpt=objRptSis.getRutaReporte(i);
                                            strNomRpt=objRptSis.getNombreReporte(i);
                                            mapPar.put("codEmp", ""+strCodEmp);
                                            mapPar.put("codLoc", ""+strCodLoc);
                                            mapPar.put("codCli", ""+CodCli);
                                            mapPar.put("Ban", ""+STRBAN);
                                            objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                    break;
                                }
                            }
                        }
                    }
                }
                conIns.close();
                conIns=null;
            }
            catch (Exception e)
            {
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        catch(SQLException ex)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, ex);
        }
        
        return blnRes;
    }
    
    
    private String FilSql() 
    {
        String strAux = "";
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
        //Obtener la condiciï¿½n.

        
        //Obtener la condiciï¿½n.
        strAux="";

        ////////Condicion para filtro por local//////
        if (txtCodLoc.getText().length()>0)
            strAux+=" AND a1.co_loc=" + txtCodLoc.getText();

        ////////Condicion para filtro por cliente
        if (txtCodCli.getText().length()>0)
            strAux+=" AND a1.co_cli=" + txtCodCli.getText();

        ////////Condicion para filtro por cliente en un rango especifico
        if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
            strAux+=" AND ((LOWER(a4.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a4.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";

        ///////////para filtrar por fechas Desde y Hasta//////////////
        if (objSelFec.isCheckBoxChecked())
        {
            switch (objSelFec.getTipoSeleccion())
            {
                case 0: //BÃ³squeda por rangos
                    strAux+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    strAux+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    strAux+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 3: //Todo.
                    break;
            }
        }

        //////////para filtrar por doucmentos Abiertos o Cerrados////////////
        if ( !(chkMosDocAbi.isSelected() && chkMosDocCer.isSelected()) )
        {
            if (chkMosDocCer.isSelected())
                strAux+=" AND (a2.mo_pag+a2.nd_abo)=0";
            
            if (chkMosDocAbi.isSelected())
                strAux+=" AND (a2.mo_pag+a2.nd_abo)<>0"; /// AND (a2.nd_abo=0)";
        }
        return strAux;
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
            //Calcular la diferencia.//
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
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_DAT_POR_RET:
                    strMsg="Porcentaje de retención";
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
                     case INT_TBL_DAT_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                case INT_TBL_DAT_VAL_VEN:
                    strMsg="Valor pendiente";
                    break;
                case INT_TBL_DAT_VAL_ABO:
                    strMsg="Valor abonado";
                    break;
                case INT_TBL_DAT_NUM_RET:
                    strMsg="Número de retención";
                    break;
                case INT_TBL_DAT_FEC_VEN:
                    strMsg="Fecha de vencimiento de la retención";
                    break;
                case INT_TBL_DAT_FEC_REC:
                    strMsg="Fecha de recepcion de la retencion";
                    break;
                case INT_TBL_DAT_VAL_RET:
                    strMsg="Valor de la retención";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
}