/* 
 * ZafCon27.java
 *
 * Created on 01 de enero de 2007, 10:10 PM
 *
 * REPORTE DE DECLARACION DE RETENCIONES PARA EL SRI... CREADO EL 21/MAYO/2007
 * PROGRAMA MODIFICADO EL 18/JUNIO/2007
 */
package Contabilidad.ZafCon27;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author  DARIO CARDENAS
 */
public class ZafCon27 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable: 
    final int INT_TBL_DAT_LIN=0;                //Línea
    final int INT_TBL_DAT_FEC_REG=1;            /// FECHA DE REGISTRO DEL DOCUMENTO ///
    final int INT_TBL_DAT_DES_DOC=2;            /// TIPO DE DOCUMENTO DESCRIPCION CORTA ///
    final int INT_TBL_DAT_NUM_DOC=3;            /// NUMERO DE DOCUMENTO ///
    final int INT_TBL_DAT_SUB_TOT=4;            /// VALOR DEL SUBTOTAL ///
    final int INT_TBL_DAT_IVA_VAL=5;            /// VALOR DEL IVA ///
    final int INT_TBL_DAT_VAL_TOT=6;            /// VALOR TOTAL ///
    final int INT_TBL_DAT_TIP_RUC=7;            /// TIPO R PARA RUC O C PARA CEDULA ///
    final int INT_TBL_DAT_RUC_CED=8;            /// NUMERO DE RUC O CEDULA ///
    final int INT_TBL_DAT_NOM_CLI=9;            /// NOMBRE DEL CLIENTE ///
    ///final int INT_TBL_DAT_FEC_REG=9;            /// FECHA DE REGISTRO DEL DOCUMENTO ///
    final int INT_TBL_DAT_FEC_EMI=10;           /// FECHA EMISION RETENCION ///
    final int INT_TBL_DAT_SER_DOC=11;           /// NUMERO DE SERIE DE FACTURA CLIENTE_PROVEEDOR ///
    final int INT_TBL_DAT_NUM_FAC=12;           /// NUMERO DE FACTURA DEL CLIENTE_PROVEEDOR ///
    final int INT_TBL_DAT_NUM_AUT=13;           /// NUMERO DE AUTORIZACION DE DOCUMENTO DEL CLIENTE_PROVEEDOR ///
    final int INT_TBL_DAT_TIP_COM=14;           /// TIPO DE COMPROBANTE DEL DOCUMENTO
    final int INT_TBL_DAT_BAS_IMP_CON=15;       /// BASE IMPONIBLE DEL DOCUMENTO (SUB_TOTAL FACTURA) CON IVA ///
    final int INT_TBL_DAT_BAS_IMP_SIN=16;       /// BASE IMPONIBLE DEL DOCUMENTO (SUB_TOTAL FACTURA) SIN IVA ///
    final int INT_TBL_DAT_VAL_IVA=17;           /// VALOR DEL IVA DEL DOCUMENTO ///
    final int INT_TBL_DAT_BAS_IMP_I30=18;       /// BASE IMPONIBLE DEL DOCUMENTO DEL VALOR DE IVA 30% ///
    final int INT_TBL_DAT_VAL_BAS_I30=19;       /// VALOR DEL IVA DE LA BASE IMPONIBLE DEL 30% ///
    final int INT_TBL_DAT_BAS_IMP_I70=20;       /// BASE IMPONIBLE DEL DOCUMENTO DEL VALOR DE IVA 70% - 100% ///
    final int INT_TBL_DAT_VAL_BAS_I70=21;       /// VALOR DEL IVA DE LA BASE IMPONIBLE DEL 70% - 100% ///
    final int INT_TBL_DAT_FEC_CAD=22;           /// FECHA DE CADUCIDAD DEL DOCUMENTO DE CLIENTE_PROVEEDOR ///
    final int INT_TBL_DAT_COD_RET=23;           /// CODIGO DEL SRI PARA LA RETENCION ///
    final int INT_TBL_DAT_VAL_RET=24;           /// VALOR CALCULADO DE LA RETENCION ///
    final int INT_TBL_DAT_BAS_IMP_RET=25;       /// BASE IMPONIBLE DE LA RETENCION (SUB_TOTAL FACTURA) ///
    final int INT_TBL_DAT_FEC_RET=26;           /// FECHA DE REGISTRO DE LA RETENCION ///
    final int INT_TBL_DAT_SER_RET=27;            /// NUMERO DE SERIE DE LA RETENCION QUE NOSOTROS EMITIMOS AL CLIENTE_PROVEEDOR///
    final int INT_TBL_DAT_NUM_RET=28;            /// NUMERO DE LA RETENCION QUE NOSOTROS EMITIMOS AL CLIENTE_PROVEEDOR ///
    final int INT_TBL_DAT_AUT_RET=29;            /// NUMERO DE AUTORIZACION DE LA RETENCION QUE NOSOTROS EMITIMOS AL CLIENTE_PROVEEDOR ///
    final int INT_TBL_DAT_TIP_DOC=30;           // NUMERO DEL TIPO DEL DOCUMENTO DE LA FACTURA EN TBM_CABMOVINV
    final int INT_TBL_DAT_COD_DOC=31;           // NUMERO DEL DOCUMENTO DE LA FACTURA EN TBM_CABMOVINV
    final int INT_TBL_DAT_TIPDOC1=32;           // NUMERO DEL TIPO DEL DOCUMENTO DE LA FACTURA EN TBM_DETPAG
    final int INT_TBL_DAT_CODDOC1=33;           // NUMERO DEL DOCUMENTO DE LA FACTURA EN TBM_DETPAG
    final int INT_TBL_DAT_CODREG1=34;           // NUMERO DEL DOCUMENTO DE LA FACTURA EN TBM_DETPAG
    final int INT_TBL_DAT_DESCOR2=35;           // NUMERO DEL DOCUMENTO DE LA FACTURA EN TBM_DETPAG    
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;    //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;
    private ZafVenCon vcoTipDoc;                //Ventana de consulta.
    private ZafVenCon vcoCli;                   //Ventana de consulta.
    private Connection con;
    private Statement stm, stmA, stmB;
    private ResultSet rst, rstA, rstB;
    private String strSQL, strSQLA, strSQLB, strAux, strAuxTmp;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecEstReg, vecAux;
    private boolean blnCon;                     //true: Continua la ejecución del hilo.
    private String strDesCorTipDoc, strDesLarTipDoc;    //Contenido del campo al obtener el foco.
    private String strCodCli, strDesLarCli;             //Contenido del campo al obtener el foco.
    //variables para la fecha//
    private Librerias.ZafDate.ZafDatePicker dtpFecDes;
    private Librerias.ZafDate.ZafDatePicker dtpFecHas;        
    private Librerias.ZafDate.ZafDatePicker dtpFecTmp;
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.
    
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCon27(ZafParSis obj) 
    {
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
        
        objUti=new ZafUtil();
        
            //Configurar fechas//
            dtpFecDes=new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            dtpFecDes.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDes.setText("");
            //panCorRpt.add(dtpFecDes);
            panNomCta.add(dtpFecDes);
            dtpFecDes.setBounds(56, 20, 148, 20);

            dtpFecHas=new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            dtpFecHas.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecHas.setText("");
            //panCorRpt.add(dtpFecHas);
            panNomCta.add(dtpFecHas);
            dtpFecHas.setBounds(310, 20, 148, 20);
                
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        panNomCta = new javax.swing.JPanel();
        lblFecDes = new javax.swing.JLabel();
        lblFecHas = new javax.swing.JLabel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblTipRep = new javax.swing.JLabel();
        cboTipRep = new javax.swing.JComboBox();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("T\u00edtulo de la ventana");
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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("T\u00edtulo de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        optTod.setSelected(true);
        optTod.setText("Todas los documentos");
        bgrFil.add(optTod);
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });

        panFil.add(optTod);
        optTod.setBounds(4, 4, 400, 20);

        optFil.setText("S\u00f3lo los documentos que cumplan el criterio seleccionado");
        bgrFil.add(optFil);
        panFil.add(optFil);
        optFil.setBounds(4, 24, 400, 20);

        panNomCta.setLayout(null);

        panNomCta.setBorder(new javax.swing.border.TitledBorder("Fecha del documento"));
        lblFecDes.setText("Desde:");
        panNomCta.add(lblFecDes);
        lblFecDes.setBounds(12, 20, 44, 20);

        lblFecHas.setText("Hasta:");
        panNomCta.add(lblFecHas);
        lblFecHas.setBounds(270, 20, 44, 20);

        panFil.add(panNomCta);
        panNomCta.setBounds(24, 90, 490, 52);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panFil.add(lblTipDoc);
        lblTipDoc.setBounds(24, 44, 120, 20);

        panFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(112, 44, 32, 20);

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

        panFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(144, 44, 56, 20);

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

        panFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(200, 44, 460, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });

        panFil.add(butTipDoc);
        butTipDoc.setBounds(660, 44, 20, 20);

        lblTipRep.setText("Tipo de Reporte");
        lblTipRep.setToolTipText("Estado del documento");
        panFil.add(lblTipRep);
        lblTipRep.setBounds(24, 150, 120, 20);

        panFil.add(cboTipRep);
        cboTipRep.setBounds(125, 150, 156, 20);

        lblCli.setText("Cliente/Proveedor:");
        lblCli.setToolTipText("Beneficiario");
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

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });

        panFil.add(butCli);
        butCli.setBounds(660, 64, 20, 20);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.BorderLayout());

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
        tblDat.setToolTipText("Doble click o ENTER para abrir la opci\u00f3n seleccionada.");
        tblDat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDatKeyPressed(evt);
            }
        });
        tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatMouseClicked(evt);
            }
        });

        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });

        panBot.add(butCon);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });

        panBot.add(butCer);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setLayout(new java.awt.BorderLayout());

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        jPanel6.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        pgrSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
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
        //Validar el contenido de la celda sólo si ha cambiado.
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

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtDesLarTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(2);
            }
        }
        else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodTipDoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
        {
            if (txtDesCorTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(1);
            }
        }
        else
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodTipDoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodCli.setText("");
            txtDesLarCli.setText("");
            //txtFecDes.setText("");
            //txtFecHas.setText("");
            cboTipRep.setSelectedIndex(0);
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
        //Abrir la opción seleccionada al presionar ENTER.
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
            evt.consume();
            abrirFrm();
        }
    }//GEN-LAST:event_tblDatKeyPressed

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

    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMouseClicked
        //Abrir la opción seleccionada al dar doble click.
        if (evt.getClickCount()==2)
        {
            abrirFrm();
        }
    }//GEN-LAST:event_tblDatMouseClicked

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
    private javax.swing.JButton butTipDoc;
    private javax.swing.JComboBox cboTipRep;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblFecDes;
    private javax.swing.JLabel lblFecHas;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTipRep;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panNomCta;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtDesLarTipDoc;
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
            this.setTitle(strAux + " V0.3"); ///"v0.1_FORMULARIO_RETENCIONES_HECHAS");
            lblTit.setText(strAux);
            //Configurar objetos.
            txtCodTipDoc.setVisible(false);            
            
            //Configurar el combo "Estado de registro".
            vecEstReg=new Vector();
            vecEstReg.add("");
            vecEstReg.add("CR1");
            vecEstReg.add("CR2");
            vecEstReg.add("CR3");
            cboTipRep.addItem("Solo Retenciones");            
            cboTipRep.addItem("Otros Documentos");
            cboTipRep.addItem("CR1 = CodRet307");
            cboTipRep.addItem("CR2 = CodRet30B");
            cboTipRep.addItem("CR3 = SinDatos");
            cboTipRep.setSelectedIndex(0);
            
            //Configurar ZafVenCon: Tipo de documento.
            configurarVenConTipDoc();
            configurarVenConCli();
            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(35);  //Almacena las cabeceras
            vecCab.clear();            
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_FEC_REG, "FEC.REG.");            
            vecCab.add(INT_TBL_DAT_DES_DOC, "DES.DOC");
            vecCab.add(INT_TBL_DAT_NUM_DOC, "NUM.DOC.");
            vecCab.add(INT_TBL_DAT_SUB_TOT, "SUB.TOT.");
            vecCab.add(INT_TBL_DAT_IVA_VAL, "IVA.VAL.");
            vecCab.add(INT_TBL_DAT_VAL_TOT, "VAL.TOT.");
            vecCab.add(INT_TBL_DAT_TIP_RUC, "TIP.RUC.");
            vecCab.add(INT_TBL_DAT_RUC_CED, "RUC.CED.");
            vecCab.add(INT_TBL_DAT_NOM_CLI, "NOM.CLI.");
            ///vecCab.add(INT_TBL_DAT_FEC_REG, "FEC.REG.");
            vecCab.add(INT_TBL_DAT_FEC_EMI, "FEC.EMI.");
            vecCab.add(INT_TBL_DAT_SER_DOC, "SER.DOC.");
            vecCab.add(INT_TBL_DAT_NUM_FAC, "NUM.FAC.");
            vecCab.add(INT_TBL_DAT_NUM_AUT, "NUM.AUT.");
            vecCab.add(INT_TBL_DAT_TIP_COM, "TIP.COM.");
            vecCab.add(INT_TBL_DAT_BAS_IMP_CON, "BAS.IMP1");
            vecCab.add(INT_TBL_DAT_BAS_IMP_SIN, "BAS.IMP2");
            vecCab.add(INT_TBL_DAT_VAL_IVA, "VAL.IVA.");
            vecCab.add(INT_TBL_DAT_BAS_IMP_I30, "BAS.I30.");
            vecCab.add(INT_TBL_DAT_VAL_BAS_I30, "VAL.I30.");
            vecCab.add(INT_TBL_DAT_BAS_IMP_I70, "BAS.I70.");
            vecCab.add(INT_TBL_DAT_VAL_BAS_I70, "VAL.I70.");
            vecCab.add(INT_TBL_DAT_FEC_CAD, "FEC.CAD.");
            vecCab.add(INT_TBL_DAT_COD_RET, "COD.RET.");
            vecCab.add(INT_TBL_DAT_VAL_RET, "VAL.RET.");
            vecCab.add(INT_TBL_DAT_BAS_IMP_RET, "BAS.RET.");
            vecCab.add(INT_TBL_DAT_FEC_RET, "FEC.RET.");
            vecCab.add(INT_TBL_DAT_SER_RET, "SER.RET.");
            vecCab.add(INT_TBL_DAT_NUM_RET, "NUM.RET.");
            vecCab.add(INT_TBL_DAT_AUT_RET, "AUT.RET.");
            vecCab.add(INT_TBL_DAT_TIP_DOC, "TIP.DOC.");
            vecCab.add(INT_TBL_DAT_COD_DOC, "COD.DOC.");
            vecCab.add(INT_TBL_DAT_TIPDOC1, "TIPDOC1.");
            vecCab.add(INT_TBL_DAT_CODDOC1, "CODDOC1.");
            vecCab.add(INT_TBL_DAT_CODREG1, "CODREG1.");
            vecCab.add(INT_TBL_DAT_DESCOR2, "DESCOR2.");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_FEC_REG).setPreferredWidth(92);            
            tcmAux.getColumn(INT_TBL_DAT_DES_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_SUB_TOT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_IVA_VAL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT).setPreferredWidth(60);            
            tcmAux.getColumn(INT_TBL_DAT_TIP_RUC).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_RUC_CED).setPreferredWidth(113);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(140);
            ///tcmAux.getColumn(INT_TBL_DAT_FEC_REG).setPreferredWidth(92);
            tcmAux.getColumn(INT_TBL_DAT_FEC_EMI).setPreferredWidth(92);
            tcmAux.getColumn(INT_TBL_DAT_SER_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NUM_FAC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NUM_AUT).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_TIP_COM).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_BAS_IMP_CON).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BAS_IMP_SIN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VAL_IVA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BAS_IMP_I30).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_VAL_BAS_I30).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BAS_IMP_I70).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_VAL_BAS_I70).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FEC_CAD).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_RET).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_VAL_RET).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BAS_IMP_RET).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_RET).setPreferredWidth(92);
            tcmAux.getColumn(INT_TBL_DAT_SER_RET).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NUM_RET).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_AUT_RET).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_DESCOR2).setPreferredWidth(60);
            ///
            tcmAux.getColumn(INT_TBL_DAT_TIP_DOC).setPreferredWidth(1);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(1);
            tcmAux.getColumn(INT_TBL_DAT_TIPDOC1).setPreferredWidth(1);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC1).setPreferredWidth(1);
            tcmAux.getColumn(INT_TBL_DAT_CODREG1).setPreferredWidth(1);
            
            //Configurar JTable: Ocultar columnas del sistema.
            tcmAux.getColumn(INT_TBL_DAT_TIP_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TIP_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TIP_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TIP_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TIP_DOC).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_TIPDOC1).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TIPDOC1).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TIPDOC1).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TIPDOC1).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TIPDOC1).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_CODDOC1).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC1).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC1).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC1).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC1).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_CODREG1).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_CODREG1).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_CODREG1).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_CODREG1).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_CODREG1).setResizable(false);
         
	    //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
                        
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Establecer columnas editables.
//            vecAux=new Vector();
//            vecAux.add("" + INT_TBL_DAT_SER_DOC);   ///numero de serie de la Factura Provvedor///
//            vecAux.add("" + INT_TBL_DAT_NUM_FAC);   ///numero de Factura Provvedor ///
//            vecAux.add("" + INT_TBL_DAT_NUM_AUT);   ///numero de Autorizacion Factura Provvedor///
//            vecAux.add("" + INT_TBL_DAT_FEC_CAD);   ///Fecha de Caducidad Factura Provvedor///
//            vecAux.add("" + INT_TBL_DAT_COD_RET);   ///Codigo del SRI///
//            objTblMod.setColumnasEditables(vecAux);
//            vecAux=null;
            
            //Configurar JTable: Editor de búsqueda.
            ///objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);            
            objTblCelRenLbl=null;            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);            
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer que el JTable sea editable.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        //int intCodEmp, intCodLoc, intNumTotReg, i;
        int intNumTotReg, i;
        boolean blnRes=true;
        try
        {            
                                
                //comparacion para escoger opcion TODOS en el combo// 
                if (cboTipRep.getSelectedIndex() == 0)
                {   
                    cargarDetRegOp0();
                    rst=null;
                    stm=null;
                    con=null;
                }                   
                
                //comparacion para escoger opcion 1 Otros Documentos en el combo// 
                if (cboTipRep.getSelectedIndex() == 1)
                {   
                    cargarDetRegOp1();
                    rst=null;
                    stm=null;
                    con=null;
                }
                
                //comparacion para escoger opcion 2 CodRet307 en el combo// 
                if (cboTipRep.getSelectedIndex() == 2)
                {   
                    cargarDetRegOp2();
                    rst=null;
                    stm=null;
                    con=null;
                }  
                
                //comparacion para escoger opcion 3 CodRet30B en el combo// 
                if (cboTipRep.getSelectedIndex() == 3)
                {   
                    cargarDetRegOp3();
                    rst=null;
                    stm=null;
                    con=null;
                }
                //comparacion para escoger opcion 4 Facturas sin DAtos del SRI en el combo// 
                if (cboTipRep.getSelectedIndex() == 4)
                {   
                    cargarDetRegOp4();
                    rst=null;
                    stm=null;
                    con=null;
                }
                                           
        }        
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    //final funcion cargar consulta

///////////
    /*
Al reporte normal de retenciones hay que agregar los siguientes campos
 Numero de orden de compra --- Numero de Documento --- Subtotal del Doc --- Valor Iva del Doc --- Total del Doc   
 los tipo de orden de compras es FACCOM - FACVAR - EGRESO - DEVCOM - LIQCOM
    
    */
//////////
    
    //FUNCION PARA ESCOGER LA OPCION 0 --MUESTRA TODOS LOS REGISTROS--
    private boolean cargarDetRegOp0()
    {
        int intCodEmp, intCodLoc, intNumTotReg, intNumTotRegSRI, i=0, coreg=0, nudocdes=0, nudochas=0;
        boolean blnRes=true;
        int ndoc = 0;
        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                /////////////////////////DATOS PARA EL SRI////////////////////////
                stmA=con.createStatement();
                
                strSQLA="";
                strSQLA+=" SELECT count(*)";
                strSQLA+=" FROM  tbm_cabtipdoc as a1";
                strSQLA+=" INNER JOIN tbm_datautsri as a2 on(a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQLA+=" WHERE a2.co_tipdoc in (2, 33) AND a1.co_emp=" + intCodEmp;
                strSQLA+=" AND a1.co_loc=" + intCodLoc;
                
                intNumTotRegSRI=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQLA);
                if (intNumTotRegSRI==-1)
                    return false;
                
                System.out.println("El total de registros para el SRI es: " +intNumTotRegSRI);
                                

                ///Obtener datos de tabla tbm_datautsri para declaracion de Retenciones///                                                                        
                strSQLA="";
                strSQLA+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a2.co_reg, a1.tx_descor, a2.ne_numdocdes, a2.ne_numdochas, REPLACE(a2.tx_numserfac,'-','') as NumSerFac, a2.tx_numautsri, a2.tx_feccadfac";
                strSQLA+=" FROM  tbm_cabtipdoc as a1";
                strSQLA+=" INNER JOIN tbm_datautsri as a2 on(a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQLA+=" WHERE a2.co_tipdoc in (2, 33) AND a1.co_emp=" + intCodEmp;
                strSQLA+=" AND a1.co_loc=" + intCodLoc;                        
                strSQLA+=" ORDER BY a2.co_reg ";                                                               
                System.out.println("El Query de los reg del SRI es: " +strSQLA);

                //Sentencia que ejecuta el SQL
                rstA=stmA.executeQuery(strSQLA);
                ////////////////////////////////////////////////////////////////
                
                stm=con.createStatement();
                //Obtener la condición.
                strAuxTmp="";
                
                //hace referencia a la tabla tbm_cabtipdoc //
                if (txtCodTipDoc.getText().length()>0)
                    strAuxTmp+=" AND a3.co_tipDoc=" + txtCodTipDoc.getText();
                
                //hace referencia a la tabla tbm_cli//
                if (txtCodCli.getText().length()>0)
                    strAuxTmp+=" AND a4.co_cli=" + txtCodCli.getText();
                
                //Hace referencia a la fechas//                
                if (dtpFecDes.isFecha() && dtpFecHas.isFecha()){
                    strAuxTmp+=" AND (a2.fe_doc>='" + objUti.formatearFecha(dtpFecDes.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    strAuxTmp+=" AND a2.fe_doc<='" + objUti.formatearFecha(dtpFecHas.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') ";                    
                }
                                       
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT COUNT (*) ";
                strSQL+=" FROM (";
                strSQL+=" (";
                strSQL+=" SELECT a6.tx_descor as DES_DOC, a4.tx_tipide as TIP_RUC, a4.tx_ide AS RUC_CED, a4.tx_nom as NOM_CLI, a2.fe_doc as FEC_REG, a2.fe_doc as FEC_EMI, ";
                strSQL+=" CASE a4.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI, REPLACE(a5.tx_secdoc,'-','') as SER_DOC_CLI, ";
                strSQL+=" LPAD((REPLACE(a5.tx_numped,'-','')),7,'0') as NUM_FAC_CLI, a5.tx_numAutSRI as NUM_AUT_CLI, '01' as TIP_COM, ";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub),2) when '0.00' then '0' end as BAS_IMP1, '2' as Tip1, ";
                strSQL+=" CASE a5.nd_poriva when '0.00' then ''||round(abs(a5.nd_sub),2) when '12.00' then '0' end as BAS_IMP2,";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, '0' as Ice1, '0' as Ice2, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12,2) when '70' then '0' when '100' then '0' end AS BAS_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '30' then '1' end AS COD_TIP30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12*0.30,2) when '70' then '0' when '100' then '0' end AS VAL_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12,2) when '100' then round(abs(a5.nd_sub)*0.12,2) end AS BAS_IVA70_100, ";
                strSQL+=" CASE a3.nd_porret when '70' then '2' when '100' then '3' end AS COD_TIP70_100, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12*0.70,2) when '100' then round(abs(a5.nd_sub)*0.12*1,2) end AS VAL_IVA70_100, ";
                strSQL+=" a5.tx_feccad as FEC_CAD_CLI, a1.tx_codsri as COD_SRI, ''||round(abs(a1.nd_abo),2) as VAL_RET, round(abs(a5.nd_sub),2) as BAS_IMP_RET, a2.fe_doc as FEC_REG, LPAD(a2.ne_numdoc1,7,'0') as NUM_RET, '' as NUM_SER_RET, '' as NUM_AUT_RET ";
                strSQL+=" , a5.co_emp, a5.co_loc, a5.co_tipdoc as TIP_DOC, a5.co_doc as COD_DOC, a5.ne_numdoc as NUM_DOC, round(abs(a5.nd_sub),2) as SUB_TOT, round(a5.nd_valiva,2) as IVA, round(a5.nd_tot,2) as TOTAL, a5.tx_secdoc as NUM_SEC, a1.co_tipdoc as TIPDOC1, a7.tx_descor as DESCOR2, a1.co_doc as CODDOC1, a1.co_reg as CODREG1 ";
                strSQL+=" FROM  tbm_detpag as a1";
                strSQL+=" INNER JOIN tbm_cabpag as a2 on (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a3 on (a1.co_emp=a3.co_emp AND a1.co_locpag=a3.co_loc AND a1.co_tipdocpag=a3.co_tipdoc AND a1.co_docpag=a3.co_doc AND a1.co_regpag=a3.co_reg)";
                strSQL+=" INNER JOIN tbm_cli as a4 on (a2.co_emp=a4.co_emp AND a2.co_cli=a4.co_cli)";
                strSQL+=" INNER JOIN tbm_cabmovinv as a5 on (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc AND a3.co_doc=a5.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a6 on (a5.co_emp=a6.co_emp and a5.co_loc=a6.co_loc and a5.co_tipdoc=a6.co_tipdoc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a7 on (a2.co_emp=a7.co_emp and a2.co_loc=a7.co_loc and a2.co_tipdoc=a7.co_tipdoc)";
                strSQL+=" WHERE a5.co_tipdoc in(2, 4, 32, 38, 57) and a1.co_tipdoc IN (33)  and a2.st_reg NOT IN ('I')";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=strAuxTmp;
                strSQL+=" ORDER BY a2.fe_doc, a2.ne_numdoc1";
                strSQL+=" ) ";
                
                strSQL+=" UNION ALL ";
                
                strSQL+=" (";
                strSQL+=" SELECT a5.tx_descor as DES_DOC, a3.tx_tipide as TIP_RUC,  a3.tx_ide as RUC_CED, a3.tx_nom as NOM_CLI, a1.fe_doc as FEC_REG, ";
                strSQL+=" a1.fe_doc as FEC_EMI, CASE a3.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI,";
                strSQL+=" a1.tx_secdoc as SER_DOC_CLI, a1.tx_numped as NUM_FAC_CLI, a1.tx_numautsri as NUM_AUT_CLI, '01' as TIP_COM,";
                strSQL+=" CASE a1.nd_poriva when null then '0' end as BAS_IMP1, '0' as Tip1, CASE a4.nd_porret when '0.00' then ''||(ROUND(a1.nd_sub,2)) end as BAS_IMP2,";
                strSQL+=" CASE a1.nd_poriva when '12.00' then ''||round(abs(a1.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, ";
                strSQL+=" '0' as Ice1, '0' as Ice2, null as BAS_IVA30, null AS COD_TIP30, null as VAL_IVA30, null as BAS_IVA70_100, null AS COD_TIP70_100, ";
                strSQL+=" null as VAL_IVA70_100, a1.tx_feccad as FEC_CAD_CLI, null as COD_SRI, null as VAL_RET, null as BAS_IMP_RET, a1.fe_doc as FEC_REG, ";
                strSQL+=" null as NUM_RET, null AS NUM_SER_RET, null as NUM_AUT_RET, a1.co_emp, a1.co_loc, a1.co_tipdoc as TIP_DOC,  a1.co_doc as COD_DOC, ";
                strSQL+=" a1.ne_numdoc as NUM_DOC, round(a1.nd_sub,2) as SUB_TOT, round(a1.nd_valiva,2) as IVA, round(a1.nd_tot,2) as TOTAL, null as NUM_SEC, null as TIPDOC1, a5.tx_descor as DESCOR2, null as CODDOC1, null as CODREG1 ";
                strSQL+=" FROM  tbm_cabmovinv AS a1 ";
                strSQL+=" LEFT OUTER JOIN tbm_motdoc as a2 on (a1.co_emp = a2.co_emp and a1.co_motdoc = a2.co_mot)";
                strSQL+=" INNER JOIN tbm_cli as a3 on (a1.co_emp=a3.co_emp and a1.co_cli=a3.co_cli)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a4 on (a1.co_emp=a4.co_emp and a1.co_loc=a4.co_loc and a1.co_tipdoc=a4.co_tipdoc and a1.co_doc=a4.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a5 on (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoc=a5.co_tipdoc)";
                strSQL+=" WHERE a1.co_tipdoc in (2, 4, 32, 38, 57) and a1.co_motdoc=5 and a2.st_reg NOT IN ('E','I') and a1.st_reg NOT IN ('E','I')";                
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                if (dtpFecDes.isFecha() && dtpFecHas.isFecha())
                {
                    strSQL+=" AND (a1.fe_doc>='" + objUti.formatearFecha(dtpFecDes.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    strSQL+=" AND a1.fe_doc<='" + objUti.formatearFecha(dtpFecHas.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') ";
                }
                
                if (txtCodTipDoc.getText().length()>0)
                    strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                
                if (txtCodCli.getText().length()>0)
                    strSQL+=" AND a3.co_cli=" + txtCodCli.getText();
                
                strSQL+=" )"; 
                strSQL+=") AS b1";
                
                System.out.println("El Query del count(*) opc0: " +strSQL);
                
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                
                System.out.println("El total de registros es opc0: " +intNumTotReg);
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT * ";
                strSQL+=" FROM (";
                strSQL+=" (";
                strSQL+=" SELECT a6.tx_descor as DES_DOC, a4.tx_tipide as TIP_RUC, a4.tx_ide AS RUC_CED, a4.tx_nom as NOM_CLI, a2.fe_doc as FEC_REG, a2.fe_doc as FEC_EMI, ";
                strSQL+=" CASE a4.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI, REPLACE(a5.tx_secdoc,'-','') as SER_DOC_CLI, ";
                strSQL+=" LPAD((REPLACE(a5.tx_numped,'-','')),7,'0') as NUM_FAC_CLI, a5.tx_numAutSRI as NUM_AUT_CLI, '01' as TIP_COM, ";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub),2) when '0.00' then '0' end as BAS_IMP1, '2' as Tip1, ";
                strSQL+=" CASE a5.nd_poriva when '0.00' then ''||round(abs(a5.nd_sub),2) when '12.00' then '0' end as BAS_IMP2,";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, '0' as Ice1, '0' as Ice2, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12,2) when '70' then '0' when '100' then '0' end AS BAS_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '30' then '1' end AS COD_TIP30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12*0.30,2) when '70' then '0' when '100' then '0' end AS VAL_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12,2) when '100' then round(abs(a5.nd_sub)*0.12,2) end AS BAS_IVA70_100, ";
                strSQL+=" CASE a3.nd_porret when '70' then '2' when '100' then '3' end AS COD_TIP70_100, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12*0.70,2) when '100' then round(abs(a5.nd_sub)*0.12*1,2) end AS VAL_IVA70_100, ";
                strSQL+=" a5.tx_feccad as FEC_CAD_CLI, a1.tx_codsri as COD_SRI, ''||round(abs(a1.nd_abo),2) as VAL_RET, round(abs(a5.nd_sub),2) as BAS_IMP_RET, a2.fe_doc as FEC_REG, LPAD(a2.ne_numdoc1,7,'0') as NUM_RET, '' as NUM_SER_RET, '' as NUM_AUT_RET ";
                strSQL+=" , a5.co_emp, a5.co_loc, a5.co_tipdoc as TIP_DOC, a5.co_doc as COD_DOC, a5.ne_numdoc as NUM_DOC, round(abs(a5.nd_sub),2) as SUB_TOT, round(a5.nd_valiva,2) as IVA, round(a5.nd_tot,2) as TOTAL, a5.tx_secdoc as NUM_SEC, a1.co_tipdoc as TIPDOC1, a7.tx_descor as DESCOR2, a1.co_doc as CODDOC1, a1.co_reg as CODREG1 ";
                strSQL+=" FROM  tbm_detpag as a1";
                strSQL+=" INNER JOIN tbm_cabpag as a2 on (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a3 on (a1.co_emp=a3.co_emp AND a1.co_locpag=a3.co_loc AND a1.co_tipdocpag=a3.co_tipdoc AND a1.co_docpag=a3.co_doc AND a1.co_regpag=a3.co_reg)";
                strSQL+=" INNER JOIN tbm_cli as a4 on (a2.co_emp=a4.co_emp AND a2.co_cli=a4.co_cli)";
                strSQL+=" INNER JOIN tbm_cabmovinv as a5 on (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc AND a3.co_doc=a5.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a6 on (a5.co_emp=a6.co_emp and a5.co_loc=a6.co_loc and a5.co_tipdoc=a6.co_tipdoc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a7 on (a2.co_emp=a7.co_emp and a2.co_loc=a7.co_loc and a2.co_tipdoc=a7.co_tipdoc)";
                strSQL+=" WHERE a5.co_tipdoc in(2, 4, 32, 38, 57) and a1.co_tipdoc IN (33)  and a2.st_reg NOT IN ('I')";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=strAuxTmp;
                strSQL+=" ORDER BY a2.fe_doc, a2.ne_numdoc1";
                strSQL+=" ) ";
                
                strSQL+=" UNION ALL ";
                
                strSQL+=" (";
                strSQL+=" SELECT a5.tx_descor as DES_DOC, a3.tx_tipide as TIP_RUC,  a3.tx_ide as RUC_CED, a3.tx_nom as NOM_CLI, a1.fe_doc as FEC_REG, ";
                strSQL+=" a1.fe_doc as FEC_EMI, CASE a3.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI,";
                strSQL+=" a1.tx_secdoc as SER_DOC_CLI, a1.tx_numped as NUM_FAC_CLI, a1.tx_numautsri as NUM_AUT_CLI, '01' as TIP_COM,";
                strSQL+=" CASE a1.nd_poriva when null then '0' end as BAS_IMP1, '0' as Tip1, CASE a4.nd_porret when '0.00' then ''||(ROUND(a1.nd_sub,2)) end as BAS_IMP2,";
                strSQL+=" CASE a1.nd_poriva when '12.00' then ''||round(abs(a1.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, ";
                strSQL+=" '0' as Ice1, '0' as Ice2, null as BAS_IVA30, null AS COD_TIP30, null as VAL_IVA30, null as BAS_IVA70_100, null AS COD_TIP70_100, ";
                strSQL+=" null as VAL_IVA70_100, a1.tx_feccad as FEC_CAD_CLI, null as COD_SRI, null as VAL_RET, null as BAS_IMP_RET, a1.fe_doc as FEC_REG, ";
                strSQL+=" null as NUM_RET, null AS NUM_SER_RET, null as NUM_AUT_RET, a1.co_emp, a1.co_loc, a1.co_tipdoc as TIP_DOC,  a1.co_doc as COD_DOC, ";
                strSQL+=" a1.ne_numdoc as NUM_DOC, round(a1.nd_sub,2) as SUB_TOT, round(a1.nd_valiva,2) as IVA, round(a1.nd_tot,2) as TOTAL, null as NUM_SEC, null as TIPDOC1, a5.tx_descor as DESCOR2, null as CODDOC1, null as CODREG1 ";
                strSQL+=" FROM  tbm_cabmovinv AS a1 ";
                strSQL+=" LEFT OUTER JOIN tbm_motdoc as a2 on (a1.co_emp = a2.co_emp and a1.co_motdoc = a2.co_mot)";
                strSQL+=" INNER JOIN tbm_cli as a3 on (a1.co_emp=a3.co_emp and a1.co_cli=a3.co_cli)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a4 on (a1.co_emp=a4.co_emp and a1.co_loc=a4.co_loc and a1.co_tipdoc=a4.co_tipdoc and a1.co_doc=a4.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a5 on (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoc=a5.co_tipdoc)";
                strSQL+=" WHERE a1.co_tipdoc in (2, 4, 32, 38, 57) and a1.co_motdoc=5 and a2.st_reg NOT IN ('E','I') and a1.st_reg NOT IN ('E','I')";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                if (dtpFecDes.isFecha() && dtpFecHas.isFecha())
                {
                    strSQL+=" AND (a1.fe_doc>='" + objUti.formatearFecha(dtpFecDes.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    strSQL+=" AND a1.fe_doc<='" + objUti.formatearFecha(dtpFecHas.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') ";
                }
                
                if (txtCodTipDoc.getText().length()>0)
                    strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                
                if (txtCodCli.getText().length()>0)
                    strSQL+=" AND a3.co_cli=" + txtCodCli.getText();
                
                strSQL+=" )"; 
                strSQL+=") AS b1";
                
                System.out.println("Query de Retenciones Hechas opc.0 es: " +strSQL);
                
                //Sentencia que ejecuta el SQL
                rst=stm.executeQuery(strSQL);
                ///rstB=stm.executeQuery(strSQLB);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                
       /////////////////////////////////////////////////////////////////////////////////////////
                ///variables para esta seccion///
                String numfacdes = "";
                String numfachas = "";
                int varfacdes = 0;
                int varfachas = 0;
                
                stmB=con.createStatement();
                strSQLB="";
                strSQLB+=" SELECT MIN(a2.ne_numdoc1) as FacDes, MAX(a2.ne_numdoc1) as FacHas";
                strSQLB+=" FROM  tbm_detpag as a1";
                strSQLB+=" INNER JOIN tbm_cabpag as a2 on (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)";
                strSQLB+=" INNER JOIN tbm_pagmovinv as a3 on (a1.co_emp=a3.co_emp AND a1.co_locpag=a3.co_loc AND a1.co_tipdocpag=a3.co_tipdoc AND a1.co_docpag=a3.co_doc AND a1.co_regpag=a3.co_reg)";
                strSQLB+=" INNER JOIN tbm_cli as a4 on (a2.co_emp=a4.co_emp AND a2.co_cli=a4.co_cli)";
                strSQLB+=" INNER JOIN tbm_cabmovinv as a5 on (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc AND a3.co_doc=a5.co_doc)";
                strSQLB+=" WHERE a3.co_tipdoc in(2,38,57) and a1.co_tipdoc=33  and a2.st_reg NOT IN ('I')";
                strSQLB+=" AND a1.co_emp=" + intCodEmp;
                strSQLB+=" AND a1.co_loc=" + intCodLoc;
                strSQLB+=strAuxTmp;                
                System.out.println("Query para verificar Retenciones Hechas DESDE y HASTA: " +strSQLB);
                rstB=stmB.executeQuery(strSQLB);
                
                if(rstB.next())
                {
                    numfacdes = rstB.getString("FacDes");
                    varfacdes = Integer.parseInt(numfacdes);
                    System.out.println("El Inicio NumFacDes (varfacdes)es: " + varfacdes);
                    numfachas = rstB.getString("FacHas");
                    varfachas = Integer.parseInt(numfachas);
                    System.out.println("El Fin NumFacHas (varfachas)es: " + varfachas);
                }
       /////////////////////////////////////////////////////////////////////////////////////////

                for (int x=1; rstA.next(); x++)                
                ///while(rstA.next())
                {
                        for (int y=0; rst.next(); y++)
                        ///while(rst.next())
                        {
                                    vecReg=new Vector();                                                                        
                                    vecReg.add(INT_TBL_DAT_LIN,"");
                                    vecReg.add(INT_TBL_DAT_FEC_REG,rst.getString("FEC_REG"));
                                    vecReg.add(INT_TBL_DAT_DES_DOC,rst.getString("DES_DOC"));
                                    vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("NUM_DOC"));
                                    vecReg.add(INT_TBL_DAT_SUB_TOT,rst.getString("SUB_TOT"));
                                    vecReg.add(INT_TBL_DAT_IVA_VAL,rst.getString("IVA"));
                                    vecReg.add(INT_TBL_DAT_VAL_TOT,rst.getString("TOTAL"));
                                    vecReg.add(INT_TBL_DAT_TIP_RUC,rst.getString("TIP_RUC"));
                                    vecReg.add(INT_TBL_DAT_RUC_CED,rst.getString("RUC_CED"));
                                    vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("NOM_CLI"));
                                    ///vecReg.add(INT_TBL_DAT_FEC_REG,rst.getString("FEC_REG"));
                                    vecReg.add(INT_TBL_DAT_FEC_EMI,rst.getString("FEC_EMI"));
                                    vecReg.add(INT_TBL_DAT_SER_DOC,rst.getString("SER_DOC_CLI"));
                                    vecReg.add(INT_TBL_DAT_NUM_FAC,rst.getString("NUM_FAC_CLI"));
                                    vecReg.add(INT_TBL_DAT_NUM_AUT,rst.getString("NUM_AUT_CLI"));
                                    vecReg.add(INT_TBL_DAT_TIP_COM,rst.getString("TIP_COM"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_CON,rst.getString("BAS_IMP1"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_SIN,rst.getString("BAS_IMP2"));
                                    vecReg.add(INT_TBL_DAT_VAL_IVA,rst.getString("VAL_IVA"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_I30,rst.getString("BAS_IVA30"));
                                    vecReg.add(INT_TBL_DAT_VAL_BAS_I30,rst.getString("VAL_IVA30"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_I70,rst.getString("BAS_IVA70_100"));
                                    vecReg.add(INT_TBL_DAT_VAL_BAS_I70,rst.getString("VAL_IVA70_100"));
                                    vecReg.add(INT_TBL_DAT_FEC_CAD,rst.getString("FEC_CAD_CLI"));
                                    vecReg.add(INT_TBL_DAT_COD_RET,rst.getString("COD_SRI"));
                                    vecReg.add(INT_TBL_DAT_VAL_RET,rst.getString("VAL_RET"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_RET,rst.getString("BAS_IMP_RET"));
                                    vecReg.add(INT_TBL_DAT_FEC_RET,rst.getString("FEC_REG"));
                                    vecReg.add(INT_TBL_DAT_SER_RET,rstA.getString("NumSerFac"));
                                    vecReg.add(INT_TBL_DAT_NUM_RET,rst.getString("NUM_RET"));
                                    vecReg.add(INT_TBL_DAT_AUT_RET,rstA.getString("tx_numautsri"));
                                    vecReg.add(INT_TBL_DAT_TIP_DOC,rst.getString("TIP_DOC"));
                                    vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("COD_DOC"));
                                    vecReg.add(INT_TBL_DAT_TIPDOC1,rst.getString("TIPDOC1"));
                                    vecReg.add(INT_TBL_DAT_CODDOC1,rst.getString("CODDOC1"));
                                    vecReg.add(INT_TBL_DAT_CODREG1,rst.getString("CODREG1"));
                                    vecReg.add(INT_TBL_DAT_DESCOR2,rst.getString("DESCOR2"));
                                    vecDat.add(vecReg);
                                    i++;
                                    pgrSis.setValue(i);                              
                        }///fin del if rst///

                }///fin del if rstA
              
                rst.close();
                stm.close();
                rstA.close();
                stmA.close();
                con.close();
                rst=null;
                stm=null;
                rstA=null;
                stmA=null;
                con=null;
                rstB=null;
                stmB=null;
                con=null;

                
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                if (intNumTotReg==tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
                pgrSis.setValue(0);
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
    //final opcion 0 todos los registros //
 

    //FUNCION PARA ESCOGER LA OPCION 1 --MUESTRA TODOS LOS REGISTROS--
    private boolean cargarDetRegOp1()
    {
        int intCodEmp, intCodLoc, intNumTotReg, intNumTotRegSRI, i=0, coreg=0, nudocdes=0, nudochas=0;
        boolean blnRes=true;
        int ndoc = 0;
        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                /////////////////////////DATOS PARA EL SRI////////////////////////
                stmA=con.createStatement();
                
                strSQLA="";
                strSQLA+=" SELECT count(*)";
                strSQLA+=" FROM  tbm_cabtipdoc as a1";
                strSQLA+=" INNER JOIN tbm_datautsri as a2 on(a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQLA+=" WHERE a2.co_tipdoc in (2, 33) AND a1.co_emp=" + intCodEmp;
                strSQLA+=" AND a1.co_loc=" + intCodLoc;
                
                intNumTotRegSRI=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQLA);
                if (intNumTotRegSRI==-1)
                    return false;
                
                System.out.println("El total de registros para el SRI es: " +intNumTotRegSRI);
                                

                ///Obtener datos de tabla tbm_datautsri para declaracion de Retenciones///                                                                        
                strSQLA="";
                strSQLA+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a2.co_reg, a1.tx_descor, a2.ne_numdocdes, a2.ne_numdochas, REPLACE(a2.tx_numserfac,'-','') as NumSerFac, a2.tx_numautsri, a2.tx_feccadfac";
                strSQLA+=" FROM  tbm_cabtipdoc as a1";
                strSQLA+=" INNER JOIN tbm_datautsri as a2 on(a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQLA+=" WHERE a2.co_tipdoc in (2, 33) AND a1.co_emp=" + intCodEmp;
                strSQLA+=" AND a1.co_loc=" + intCodLoc;                        
                strSQLA+=" ORDER BY a2.co_reg ";                                                               
                System.out.println("El Query de los reg del SRI es: " +strSQLA);

                //Sentencia que ejecuta el SQL
                rstA=stmA.executeQuery(strSQLA);
                ////////////////////////////////////////////////////////////////
                
                stm=con.createStatement();
                //Obtener la condición.
                strAuxTmp="";
                
                //hace referencia a la tabla tbm_cabtipdoc //
                if (txtCodTipDoc.getText().length()>0)
                    strAuxTmp+=" AND a3.co_tipDoc=" + txtCodTipDoc.getText();
                
                //hace referencia a la tabla tbm_cli//
                if (txtCodCli.getText().length()>0)
                    strAuxTmp+=" AND a4.co_cli=" + txtCodCli.getText();
                
                //Hace referencia a la fechas//                
                if (dtpFecDes.isFecha() && dtpFecHas.isFecha()){
                    strAuxTmp+=" AND (a2.fe_doc>='" + objUti.formatearFecha(dtpFecDes.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    strAuxTmp+=" AND a2.fe_doc<='" + objUti.formatearFecha(dtpFecHas.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') ";                    
                }
                                       
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT COUNT (*) ";
                strSQL+=" FROM (";
                strSQL+=" (";
                strSQL+=" SELECT a6.tx_descor as DES_DOC, a4.tx_tipide as TIP_RUC, a4.tx_ide AS RUC_CED, a4.tx_nom as NOM_CLI, a2.fe_doc as FEC_REG, a2.fe_doc as FEC_EMI, ";
                strSQL+=" CASE a4.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI, REPLACE(a5.tx_secdoc,'-','') as SER_DOC_CLI, ";
                strSQL+=" LPAD((REPLACE(a5.tx_numped,'-','')),7,'0') as NUM_FAC_CLI, a5.tx_numAutSRI as NUM_AUT_CLI, '01' as TIP_COM, ";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub),2) when '0.00' then '0' end as BAS_IMP1, '2' as Tip1, ";
                strSQL+=" CASE a5.nd_poriva when '0.00' then ''||round(abs(a5.nd_sub),2) when '12.00' then '0' end as BAS_IMP2,";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, '0' as Ice1, '0' as Ice2, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12,2) when '70' then '0' when '100' then '0' end AS BAS_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '30' then '1' end AS COD_TIP30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12*0.30,2) when '70' then '0' when '100' then '0' end AS VAL_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12,2) when '100' then round(abs(a5.nd_sub)*0.12,2) end AS BAS_IVA70_100, ";
                strSQL+=" CASE a3.nd_porret when '70' then '2' when '100' then '3' end AS COD_TIP70_100, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12*0.70,2) when '100' then round(abs(a5.nd_sub)*0.12*1,2) end AS VAL_IVA70_100, ";
                strSQL+=" a5.tx_feccad as FEC_CAD_CLI, a1.tx_codsri as COD_SRI, a3.nd_porret, CASE  a1.co_tipdoc when '32' then ''||null when '33' then ''||round(abs(a1.nd_abo),2) end AS VAL_RET, round(abs(a5.nd_sub),2) as BAS_IMP_RET, a2.fe_doc as FEC_REG, LPAD(a2.ne_numdoc1,7,'0') as NUM_RET, '' as NUM_SER_RET, '' as NUM_AUT_RET ";
                strSQL+=" , a5.co_emp, a5.co_loc, a5.co_tipdoc as TIP_DOC, a5.co_doc as COD_DOC, a5.ne_numdoc as NUM_DOC, round(abs(a5.nd_sub),2) as SUB_TOT, round(a5.nd_valiva,2) as IVA, round(a5.nd_tot,2) as TOTAL, a5.tx_secdoc as NUM_SEC, a1.co_tipdoc as TIPDOC1, a7.tx_descor as DESCOR2, a1.co_doc as CODDOC1, a1.co_reg as CODREG1 ";
                strSQL+=" FROM  tbm_detpag as a1";
                strSQL+=" INNER JOIN tbm_cabpag as a2 on (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a3 on (a1.co_emp=a3.co_emp AND a1.co_locpag=a3.co_loc AND a1.co_tipdocpag=a3.co_tipdoc AND a1.co_docpag=a3.co_doc AND a1.co_regpag=a3.co_reg)";
                strSQL+=" INNER JOIN tbm_cli as a4 on (a2.co_emp=a4.co_emp AND a2.co_cli=a4.co_cli)";
                strSQL+=" INNER JOIN tbm_cabmovinv as a5 on (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc AND a3.co_doc=a5.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a6 on (a5.co_emp=a6.co_emp and a5.co_loc=a6.co_loc and a5.co_tipdoc=a6.co_tipdoc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a7 on (a2.co_emp=a7.co_emp and a2.co_loc=a7.co_loc and a2.co_tipdoc=a7.co_tipdoc)";
                strSQL+=" WHERE a5.co_tipdoc in(2, 4, 32, 38, 57) and a1.co_tipdoc IN (32,33)  and a2.st_reg NOT IN ('I')";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=strAuxTmp;
                strSQL+=" ORDER BY a2.fe_doc, a2.ne_numdoc1";
                strSQL+=" ) ";
                
                strSQL+=" UNION ALL ";
                
                strSQL+=" (";
                strSQL+=" SELECT a5.tx_descor as DES_DOC, a3.tx_tipide as TIP_RUC,  a3.tx_ide as RUC_CED, a3.tx_nom as NOM_CLI, a1.fe_doc as FEC_REG, ";
                strSQL+=" a1.fe_doc as FEC_EMI, CASE a3.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI,";
                strSQL+=" a1.tx_secdoc as SER_DOC_CLI, a1.tx_numped as NUM_FAC_CLI, a1.tx_numautsri as NUM_AUT_CLI, '01' as TIP_COM,";
                strSQL+=" CASE a1.nd_poriva when null then '0' end as BAS_IMP1, '0' as Tip1, CASE a4.nd_porret when '0.00' then ''||(ROUND(a1.nd_sub,2)) end as BAS_IMP2,";
                strSQL+=" CASE a1.nd_poriva when '12.00' then ''||round(abs(a1.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, ";
                strSQL+=" '0' as Ice1, '0' as Ice2, null as BAS_IVA30, null AS COD_TIP30, null as VAL_IVA30, null as BAS_IVA70_100, null AS COD_TIP70_100, ";
                strSQL+=" null as VAL_IVA70_100, a1.tx_feccad as FEC_CAD_CLI, null as COD_SRI, a4.nd_porret, null as VAL_RET, null as BAS_IMP_RET, a1.fe_doc as FEC_REG, ";
                strSQL+=" null as NUM_RET, null AS NUM_SER_RET, null as NUM_AUT_RET, a1.co_emp, a1.co_loc, a1.co_tipdoc as TIP_DOC,  a1.co_doc as COD_DOC, ";
                strSQL+=" a1.ne_numdoc as NUM_DOC, round(a1.nd_sub,2) as SUB_TOT, round(a1.nd_valiva,2) as IVA, round(a1.nd_tot,2) as TOTAL, null as NUM_SEC, null as TIPDOC1, a5.tx_descor as DESCOR2, null as CODDOC1, null as CODREG1 ";
                strSQL+=" FROM  tbm_cabmovinv AS a1 ";
                strSQL+=" LEFT OUTER JOIN tbm_motdoc as a2 on (a1.co_emp = a2.co_emp and a1.co_motdoc = a2.co_mot)";
                strSQL+=" INNER JOIN tbm_cli as a3 on (a1.co_emp=a3.co_emp and a1.co_cli=a3.co_cli)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a4 on (a1.co_emp=a4.co_emp and a1.co_loc=a4.co_loc and a1.co_tipdoc=a4.co_tipdoc and a1.co_doc=a4.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a5 on (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoc=a5.co_tipdoc)";
                strSQL+=" WHERE a1.co_tipdoc in (2, 4, 32, 38, 57) and a1.co_motdoc=5 and a2.st_reg NOT IN ('E','I') and a1.st_reg NOT IN ('E','I')";                
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                if (dtpFecDes.isFecha() && dtpFecHas.isFecha())
                {
                    strSQL+=" AND (a1.fe_doc>='" + objUti.formatearFecha(dtpFecDes.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    strSQL+=" AND a1.fe_doc<='" + objUti.formatearFecha(dtpFecHas.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') ";
                }
                
                if (txtCodTipDoc.getText().length()>0)
                    strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                
                if (txtCodCli.getText().length()>0)
                    strSQL+=" AND a3.co_cli=" + txtCodCli.getText();
                
                strSQL+=" )"; 
                strSQL+=") AS b1";
                
                System.out.println("El Query del count(*) opc1: " +strSQL);
                
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                
                System.out.println("El total de registros opc1 es: " +intNumTotReg);
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT * ";
                strSQL+=" FROM (";
                strSQL+=" (";
                strSQL+=" SELECT a6.tx_descor as DES_DOC, a4.tx_tipide as TIP_RUC, a4.tx_ide AS RUC_CED, a4.tx_nom as NOM_CLI, a2.fe_doc as FEC_REG, a2.fe_doc as FEC_EMI, ";
                strSQL+=" CASE a4.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI, REPLACE(a5.tx_secdoc,'-','') as SER_DOC_CLI, ";
                strSQL+=" LPAD((REPLACE(a5.tx_numped,'-','')),7,'0') as NUM_FAC_CLI, a5.tx_numAutSRI as NUM_AUT_CLI, '01' as TIP_COM, ";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub),2) when '0.00' then '0' end as BAS_IMP1, '2' as Tip1, ";
                strSQL+=" CASE a5.nd_poriva when '0.00' then ''||round(abs(a5.nd_sub),2) when '12.00' then '0' end as BAS_IMP2,";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, '0' as Ice1, '0' as Ice2, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12,2) when '70' then '0' when '100' then '0' end AS BAS_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '30' then '1' end AS COD_TIP30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12*0.30,2) when '70' then '0' when '100' then '0' end AS VAL_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12,2) when '100' then round(abs(a5.nd_sub)*0.12,2) end AS BAS_IVA70_100, ";
                strSQL+=" CASE a3.nd_porret when '70' then '2' when '100' then '3' end AS COD_TIP70_100, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12*0.70,2) when '100' then round(abs(a5.nd_sub)*0.12*1,2) end AS VAL_IVA70_100, ";
                strSQL+=" a5.tx_feccad as FEC_CAD_CLI, a1.tx_codsri as COD_SRI, a3.nd_porret, CASE  a1.co_tipdoc when '32' then ''||null when '33' then ''||round(abs(a1.nd_abo),2) end AS VAL_RET, round(abs(a5.nd_sub),2) as BAS_IMP_RET, a2.fe_doc as FEC_REG, LPAD(a2.ne_numdoc1,7,'0') as NUM_RET, '' as NUM_SER_RET, '' as NUM_AUT_RET ";
                strSQL+=" , a5.co_emp, a5.co_loc, a5.co_tipdoc as TIP_DOC, a5.co_doc as COD_DOC, a5.ne_numdoc as NUM_DOC, round(abs(a5.nd_sub),2) as SUB_TOT, round(a5.nd_valiva,2) as IVA, round(a5.nd_tot,2) as TOTAL, a5.tx_secdoc as NUM_SEC, a1.co_tipdoc as TIPDOC1, a7.tx_descor as DESCOR2, a1.co_doc as CODDOC1, a1.co_reg as CODREG1 ";
                strSQL+=" FROM  tbm_detpag as a1";
                strSQL+=" INNER JOIN tbm_cabpag as a2 on (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a3 on (a1.co_emp=a3.co_emp AND a1.co_locpag=a3.co_loc AND a1.co_tipdocpag=a3.co_tipdoc AND a1.co_docpag=a3.co_doc AND a1.co_regpag=a3.co_reg)";
                strSQL+=" INNER JOIN tbm_cli as a4 on (a2.co_emp=a4.co_emp AND a2.co_cli=a4.co_cli)";
                strSQL+=" INNER JOIN tbm_cabmovinv as a5 on (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc AND a3.co_doc=a5.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a6 on (a5.co_emp=a6.co_emp and a5.co_loc=a6.co_loc and a5.co_tipdoc=a6.co_tipdoc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a7 on (a2.co_emp=a7.co_emp and a2.co_loc=a7.co_loc and a2.co_tipdoc=a7.co_tipdoc)";
                strSQL+=" WHERE a5.co_tipdoc in(2, 4, 32, 38, 57) and a1.co_tipdoc IN (32,33)  and a2.st_reg NOT IN ('I')";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=strAuxTmp;
                strSQL+=" ORDER BY a2.fe_doc, a2.ne_numdoc1";
                strSQL+=" ) ";
                
                strSQL+=" UNION ALL ";
                
                strSQL+=" (";
                strSQL+=" SELECT a5.tx_descor as DES_DOC, a3.tx_tipide as TIP_RUC,  a3.tx_ide as RUC_CED, a3.tx_nom as NOM_CLI, a1.fe_doc as FEC_REG, ";
                strSQL+=" a1.fe_doc as FEC_EMI, CASE a3.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI,";
                strSQL+=" a1.tx_secdoc as SER_DOC_CLI, a1.tx_numped as NUM_FAC_CLI, a1.tx_numautsri as NUM_AUT_CLI, '01' as TIP_COM,";
                strSQL+=" CASE a1.nd_poriva when null then '0' end as BAS_IMP1, '0' as Tip1, CASE a4.nd_porret when '0.00' then ''||(ROUND(a1.nd_sub,2)) end as BAS_IMP2,";
                strSQL+=" CASE a1.nd_poriva when '12.00' then ''||round(abs(a1.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, ";
                strSQL+=" '0' as Ice1, '0' as Ice2, null as BAS_IVA30, null AS COD_TIP30, null as VAL_IVA30, null as BAS_IVA70_100, null AS COD_TIP70_100, ";
                strSQL+=" null as VAL_IVA70_100, a1.tx_feccad as FEC_CAD_CLI, null as COD_SRI, a4.nd_porret, null as VAL_RET, null as BAS_IMP_RET, a1.fe_doc as FEC_REG, ";
                strSQL+=" null as NUM_RET, null AS NUM_SER_RET, null as NUM_AUT_RET, a1.co_emp, a1.co_loc, a1.co_tipdoc as TIP_DOC,  a1.co_doc as COD_DOC, ";
                strSQL+=" a1.ne_numdoc as NUM_DOC, round(a1.nd_sub,2) as SUB_TOT, round(a1.nd_valiva,2) as IVA, round(a1.nd_tot,2) as TOTAL, null as NUM_SEC, null as TIPDOC1, a5.tx_descor as DESCOR2, null as CODDOC1, null as CODREG1 ";
                strSQL+=" FROM  tbm_cabmovinv AS a1 ";
                strSQL+=" LEFT OUTER JOIN tbm_motdoc as a2 on (a1.co_emp = a2.co_emp and a1.co_motdoc = a2.co_mot)";
                strSQL+=" INNER JOIN tbm_cli as a3 on (a1.co_emp=a3.co_emp and a1.co_cli=a3.co_cli)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a4 on (a1.co_emp=a4.co_emp and a1.co_loc=a4.co_loc and a1.co_tipdoc=a4.co_tipdoc and a1.co_doc=a4.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a5 on (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoc=a5.co_tipdoc)";
                strSQL+=" WHERE a1.co_tipdoc in (2, 4, 32, 38, 57) and a1.co_motdoc=5 and a2.st_reg NOT IN ('E','I') and a1.st_reg NOT IN ('E','I')";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                if (dtpFecDes.isFecha() && dtpFecHas.isFecha())
                {
                    strSQL+=" AND (a1.fe_doc>='" + objUti.formatearFecha(dtpFecDes.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    strSQL+=" AND a1.fe_doc<='" + objUti.formatearFecha(dtpFecHas.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') ";
                }
                
                if (txtCodTipDoc.getText().length()>0)
                    strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                
                if (txtCodCli.getText().length()>0)
                    strSQL+=" AND a3.co_cli=" + txtCodCli.getText();
                
                strSQL+=" )"; 
                strSQL+=") AS b1";
                
                System.out.println("Query de Retenciones Hechas opc.1 es: " +strSQL);
                
                //Sentencia que ejecuta el SQL
                rst=stm.executeQuery(strSQL);
                ///rstB=stm.executeQuery(strSQLB);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                
       /////////////////////////////////////////////////////////////////////////////////////////
                ///variables para esta seccion///
                String numfacdes = "";
                String numfachas = "";
                int varfacdes = 0;
                int varfachas = 0;
                
                stmB=con.createStatement();
                strSQLB="";
                strSQLB+=" SELECT MIN(a2.ne_numdoc1) as FacDes, MAX(a2.ne_numdoc1) as FacHas";
                strSQLB+=" FROM  tbm_detpag as a1";
                strSQLB+=" INNER JOIN tbm_cabpag as a2 on (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)";
                strSQLB+=" INNER JOIN tbm_pagmovinv as a3 on (a1.co_emp=a3.co_emp AND a1.co_locpag=a3.co_loc AND a1.co_tipdocpag=a3.co_tipdoc AND a1.co_docpag=a3.co_doc AND a1.co_regpag=a3.co_reg)";
                strSQLB+=" INNER JOIN tbm_cli as a4 on (a2.co_emp=a4.co_emp AND a2.co_cli=a4.co_cli)";
                strSQLB+=" INNER JOIN tbm_cabmovinv as a5 on (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc AND a3.co_doc=a5.co_doc)";
                strSQLB+=" WHERE a3.co_tipdoc in(2,38,57) and a1.co_tipdoc=33  and a2.st_reg NOT IN ('I')";
                strSQLB+=" AND a1.co_emp=" + intCodEmp;
                strSQLB+=" AND a1.co_loc=" + intCodLoc;
                strSQLB+=strAuxTmp;                
                System.out.println("Query para verificar Retenciones Hechas DESDE y HASTA: " +strSQLB);
                rstB=stmB.executeQuery(strSQLB);
                
                if(rstB.next())
                {
                    numfacdes = rstB.getString("FacDes");
                    varfacdes = Integer.parseInt(numfacdes);
                    System.out.println("El Inicio NumFacDes (varfacdes)es: " + varfacdes);
                    numfachas = rstB.getString("FacHas");
                    varfachas = Integer.parseInt(numfachas);
                    System.out.println("El Fin NumFacHas (varfachas)es: " + varfachas);
                }
       /////////////////////////////////////////////////////////////////////////////////////////

                for (int x=1; rstA.next(); x++)                
                ///while(rstA.next())
                {
                        for (int y=0; rst.next(); y++)
                        ///while(rst.next())
                        {
                                    vecReg=new Vector();                                                                        
                                    vecReg.add(INT_TBL_DAT_LIN,"");
                                    vecReg.add(INT_TBL_DAT_FEC_REG,rst.getString("FEC_REG"));
                                    vecReg.add(INT_TBL_DAT_DES_DOC,rst.getString("DES_DOC"));
                                    vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("NUM_DOC"));
                                    vecReg.add(INT_TBL_DAT_SUB_TOT,rst.getString("SUB_TOT"));
                                    vecReg.add(INT_TBL_DAT_IVA_VAL,rst.getString("IVA"));
                                    vecReg.add(INT_TBL_DAT_VAL_TOT,rst.getString("TOTAL"));
                                    vecReg.add(INT_TBL_DAT_TIP_RUC,rst.getString("TIP_RUC"));
                                    vecReg.add(INT_TBL_DAT_RUC_CED,rst.getString("RUC_CED"));
                                    vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("NOM_CLI"));
                                    ///vecReg.add(INT_TBL_DAT_FEC_REG,rst.getString("FEC_REG"));
                                    vecReg.add(INT_TBL_DAT_FEC_EMI,rst.getString("FEC_EMI"));
                                    vecReg.add(INT_TBL_DAT_SER_DOC,rst.getString("SER_DOC_CLI"));
                                    vecReg.add(INT_TBL_DAT_NUM_FAC,rst.getString("NUM_FAC_CLI"));
                                    vecReg.add(INT_TBL_DAT_NUM_AUT,rst.getString("NUM_AUT_CLI"));
                                    vecReg.add(INT_TBL_DAT_TIP_COM,rst.getString("TIP_COM"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_CON,rst.getString("BAS_IMP1"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_SIN,rst.getString("BAS_IMP2"));
                                    vecReg.add(INT_TBL_DAT_VAL_IVA,rst.getString("VAL_IVA"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_I30,rst.getString("BAS_IVA30"));
                                    vecReg.add(INT_TBL_DAT_VAL_BAS_I30,rst.getString("VAL_IVA30"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_I70,rst.getString("BAS_IVA70_100"));
                                    vecReg.add(INT_TBL_DAT_VAL_BAS_I70,rst.getString("VAL_IVA70_100"));
                                    vecReg.add(INT_TBL_DAT_FEC_CAD,rst.getString("FEC_CAD_CLI"));
                                    vecReg.add(INT_TBL_DAT_COD_RET,rst.getString("COD_SRI"));
                                    vecReg.add(INT_TBL_DAT_VAL_RET,rst.getString("VAL_RET"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_RET,rst.getString("BAS_IMP_RET"));
                                    vecReg.add(INT_TBL_DAT_FEC_RET,rst.getString("FEC_REG"));
                                    vecReg.add(INT_TBL_DAT_SER_RET,rstA.getString("NumSerFac"));
                                    vecReg.add(INT_TBL_DAT_NUM_RET,rst.getString("NUM_RET"));
                                    vecReg.add(INT_TBL_DAT_AUT_RET,rstA.getString("tx_numautsri"));
                                    vecReg.add(INT_TBL_DAT_TIP_DOC,rst.getString("TIP_DOC"));
                                    vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("COD_DOC"));
                                    vecReg.add(INT_TBL_DAT_TIPDOC1,rst.getString("TIPDOC1"));
                                    vecReg.add(INT_TBL_DAT_CODDOC1,rst.getString("CODDOC1"));
                                    vecReg.add(INT_TBL_DAT_CODREG1,rst.getString("CODREG1"));
                                    vecReg.add(INT_TBL_DAT_DESCOR2,rst.getString("DESCOR2"));
                                    vecDat.add(vecReg);
                                    i++;
                                    pgrSis.setValue(i);                              
                        }///fin del if rst///

                }///fin del if rstA
              
                rst.close();
                stm.close();
                rstA.close();
                stmA.close();
                con.close();
                rst=null;
                stm=null;
                rstA=null;
                stmA=null;
                con=null;
                rstB=null;
                stmB=null;
                con=null;

                
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                if (intNumTotReg==tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
                pgrSis.setValue(0);
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
    //final opcion 1 todos los registros //
    
    
     //FUNCION PARA ESCOGER LA OPCION 2 --MUESTRA LOS REGIDTROS CON CODRET307--
    private boolean cargarDetRegOp2()
    {
        int intCodEmp, intCodLoc, intNumTotReg, intNumTotRegSRI, i=0, coreg=0, nudocdes=0, nudochas=0;
        boolean blnRes=true;
        int ndoc = 0;
        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {                                
                
                /////////////////////////DATOS PARA EL SRI////////////////////////
                stmA=con.createStatement();
                
                strSQLA="";
                strSQLA+=" SELECT count(*)";
                strSQLA+=" FROM  tbm_cabtipdoc as a1";
                strSQLA+=" INNER JOIN tbm_datautsri as a2 on(a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQLA+=" WHERE a2.co_tipdoc in (2, 33) AND a1.co_emp=" + intCodEmp;
                strSQLA+=" AND a1.co_loc=" + intCodLoc;
                
                intNumTotRegSRI=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQLA);
                if (intNumTotRegSRI==-1)
                    return false;
                
                System.out.println("El total de registros para el SRI es: " +intNumTotRegSRI);
                                

                ///Obtener datos de tabla tbm_datautsri para declaracion de Retenciones///                                                                        
                strSQLA="";
                strSQLA+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a2.co_reg, a1.tx_descor, a2.ne_numdocdes, a2.ne_numdochas, REPLACE(a2.tx_numserfac,'-','') as NumSerFac, a2.tx_numautsri, a2.tx_feccadfac";
                strSQLA+=" FROM  tbm_cabtipdoc as a1";
                strSQLA+=" INNER JOIN tbm_datautsri as a2 on(a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQLA+=" WHERE a2.co_tipdoc in (2, 33) AND a1.co_emp=" + intCodEmp;
                strSQLA+=" AND a1.co_loc=" + intCodLoc;                        
                strSQLA+=" ORDER BY a2.co_reg ";                                                               
                System.out.println("El Query para Datos del SRI es: " +strSQLA);

                //Sentencia que ejecuta el SQL
                rstA=stmA.executeQuery(strSQLA);
                ////////////////////////////////////////////////////////////////
                
                //Obtener la condición.
                strAuxTmp="";
                
                //hace referencia a la tabla tbm_cabtipdoc //
                if (txtCodTipDoc.getText().length()>0)
                    strAuxTmp+=" AND a3.co_tipDoc=" + txtCodTipDoc.getText(); 
                
                //hace referencia a la tabla tbm_cli//
                if (txtCodCli.getText().length()>0)
                    strAuxTmp+=" AND a4.co_cli=" + txtCodCli.getText();
                
                //Hace referencia a la fechas//                
                if (dtpFecDes.isFecha() && dtpFecHas.isFecha()){
                    strAuxTmp+=" AND (a2.fe_doc>='" + objUti.formatearFecha(dtpFecDes.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    strAuxTmp+=" AND a2.fe_doc<='" + objUti.formatearFecha(dtpFecHas.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') ";                    
                }

                stm=con.createStatement();
                //Obtener el número total de registros.
                strSQL="";
                strSQL+="SELECT COUNT (*) ";
                strSQL+=" FROM (";
                strSQL+=" (";
                strSQL+=" SELECT a6.tx_descor as DES_DOC, a4.tx_tipide as TIP_RUC, a4.tx_ide AS RUC_CED, a4.tx_nom as NOM_CLI, a2.fe_doc as FEC_REG, a2.fe_doc as FEC_EMI, ";
                strSQL+=" CASE a4.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI, REPLACE(a5.tx_secdoc,'-','') as SER_DOC_CLI, ";
                strSQL+=" LPAD((REPLACE(a5.tx_numped,'-','')),7,'0') as NUM_FAC_CLI, a5.tx_numAutSRI as NUM_AUT_CLI, '01' as TIP_COM, ";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub),2) when '0.00' then '0' end as BAS_IMP1, '2' as Tip1, ";
                strSQL+=" CASE a5.nd_poriva when '0.00' then ''||round(abs(a5.nd_sub),2) when '12.00' then '0' end as BAS_IMP2,";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, '0' as Ice1, '0' as Ice2, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12,2) when '70' then '0' when '100' then '0' end AS BAS_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '30' then '1' end AS COD_TIP30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12*0.30,2) when '70' then '0' when '100' then '0' end AS VAL_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12,2) when '100' then round(abs(a5.nd_sub)*0.12,2) end AS BAS_IVA70_100, ";
                strSQL+=" CASE a3.nd_porret when '70' then '2' when '100' then '3' end AS COD_TIP70_100, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12*0.70,2) when '100' then round(abs(a5.nd_sub)*0.12*1,2) end AS VAL_IVA70_100, ";
                strSQL+=" a5.tx_feccad as FEC_CAD_CLI, a1.tx_codsri as COD_SRI, ''||round(abs(a1.nd_abo),2) as VAL_RET, round(abs(a5.nd_sub),2) as BAS_IMP_RET, a2.fe_doc as FEC_REG, LPAD(a2.ne_numdoc1,7,'0') as NUM_RET, '' as NUM_SER_RET, '' as NUM_AUT_RET ";
                strSQL+=" , a5.co_emp, a5.co_loc, a5.co_tipdoc as TIP_DOC, a5.co_doc as COD_DOC, a5.ne_numdoc as NUM_DOC, round(abs(a5.nd_sub),2) as SUB_TOT, round(a5.nd_valiva,2) as IVA, round(a5.nd_tot,2) as TOTAL, a5.tx_secdoc as NUM_SEC, a1.co_tipdoc as TIPDOC1, a7.tx_descor as DESCOR2, a1.co_doc as CODDOC1, a1.co_reg as CODREG1 ";
                strSQL+=" FROM  tbm_detpag as a1";
                strSQL+=" INNER JOIN tbm_cabpag as a2 on (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a3 on (a1.co_emp=a3.co_emp AND a1.co_locpag=a3.co_loc AND a1.co_tipdocpag=a3.co_tipdoc AND a1.co_docpag=a3.co_doc AND a1.co_regpag=a3.co_reg)";
                strSQL+=" INNER JOIN tbm_cli as a4 on (a2.co_emp=a4.co_emp AND a2.co_cli=a4.co_cli)";
                strSQL+=" INNER JOIN tbm_cabmovinv as a5 on (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc AND a3.co_doc=a5.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a6 on (a5.co_emp=a6.co_emp and a5.co_loc=a6.co_loc and a5.co_tipdoc=a6.co_tipdoc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a7 on (a2.co_emp=a7.co_emp and a2.co_loc=a7.co_loc and a2.co_tipdoc=a7.co_tipdoc)";
                strSQL+=" WHERE a5.co_tipdoc in(2, 4, 32, 38, 57) and a1.co_tipdoc IN (33) and a2.st_reg NOT IN ('I') AND a3.nd_porret IN ('1.00','5.00','8.00')";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=strAuxTmp;
                strSQL+=" ORDER BY a2.fe_doc, a2.ne_numdoc1";
                strSQL+=" ) ";
                
                strSQL+=" UNION ALL ";
                
                strSQL+=" (";
                strSQL+=" SELECT a5.tx_descor as DES_DOC, a3.tx_tipide as TIP_RUC,  a3.tx_ide as RUC_CED, a3.tx_nom as NOM_CLI, a1.fe_doc as FEC_REG, ";
                strSQL+=" a1.fe_doc as FEC_EMI, CASE a3.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI,";
                strSQL+=" a1.tx_secdoc as SER_DOC_CLI, a1.tx_numped as NUM_FAC_CLI, a1.tx_numautsri as NUM_AUT_CLI, '01' as TIP_COM,";
                strSQL+=" CASE a1.nd_poriva when null then '0' end as BAS_IMP1, '0' as Tip1, CASE a4.nd_porret when '0.00' then ''||(ROUND(a1.nd_sub,2)) end as BAS_IMP2,";
                strSQL+=" CASE a1.nd_poriva when '12.00' then ''||round(abs(a1.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, ";
                strSQL+=" '0' as Ice1, '0' as Ice2, null as BAS_IVA30, null AS COD_TIP30, null as VAL_IVA30, null as BAS_IVA70_100, null AS COD_TIP70_100, ";
                strSQL+=" null as VAL_IVA70_100, a1.tx_feccad as FEC_CAD_CLI, null as COD_SRI, null as VAL_RET, null as BAS_IMP_RET, a1.fe_doc as FEC_REG, ";
                strSQL+=" null as NUM_RET, null AS NUM_SER_RET, null as NUM_AUT_RET, a1.co_emp, a1.co_loc, a1.co_tipdoc as TIP_DOC,  a1.co_doc as COD_DOC, ";
                strSQL+=" a1.ne_numdoc as NUM_DOC, round(a1.nd_sub,2) as SUB_TOT, round(a1.nd_valiva,2) as IVA, round(a1.nd_tot,2) as TOTAL, null as NUM_SEC, null as TIPDOC1, a5.tx_descor as DESCOR2, null as CODDOC1, null as CODREG1 ";
                strSQL+=" FROM  tbm_cabmovinv AS a1 ";
                strSQL+=" LEFT OUTER JOIN tbm_motdoc as a2 on (a1.co_emp = a2.co_emp and a1.co_motdoc = a2.co_mot)";
                strSQL+=" INNER JOIN tbm_cli as a3 on (a1.co_emp=a3.co_emp and a1.co_cli=a3.co_cli)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a4 on (a1.co_emp=a4.co_emp and a1.co_loc=a4.co_loc and a1.co_tipdoc=a4.co_tipdoc and a1.co_doc=a4.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a5 on (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoc=a5.co_tipdoc)";
                strSQL+=" a1 .co_tipdoc in(2, 4, 32, 38, 57) and a1.co_motdoc=5 and a2.st_reg NOT IN ('E','I') and a1.st_reg NOT IN ('E','I')";                
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                if (dtpFecDes.isFecha() && dtpFecHas.isFecha())
                {
                    strSQL+=" AND (a1.fe_doc>='" + objUti.formatearFecha(dtpFecDes.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    strSQL+=" AND a1.fe_doc<='" + objUti.formatearFecha(dtpFecHas.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') ";
                }
                
                if (txtCodTipDoc.getText().length()>0)
                    strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                
                if (txtCodCli.getText().length()>0)
                    strSQL+=" AND a3.co_cli=" + txtCodCli.getText();
                
                strSQL+=" )"; 
                strSQL+=") AS b1";
                
                System.out.println("El Query del count(*) opc2: " +strSQL);

                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT * ";
                strSQL+=" FROM (";
                strSQL+=" (";
                strSQL+=" SELECT a6.tx_descor as DES_DOC, a4.tx_tipide as TIP_RUC, a4.tx_ide AS RUC_CED, a4.tx_nom as NOM_CLI, a2.fe_doc as FEC_REG, a2.fe_doc as FEC_EMI, ";
                strSQL+=" CASE a4.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI, REPLACE(a5.tx_secdoc,'-','') as SER_DOC_CLI, ";
                strSQL+=" LPAD((REPLACE(a5.tx_numped,'-','')),7,'0') as NUM_FAC_CLI, a5.tx_numAutSRI as NUM_AUT_CLI, '01' as TIP_COM, ";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub),2) when '0.00' then '0' end as BAS_IMP1, '2' as Tip1, ";
                strSQL+=" CASE a5.nd_poriva when '0.00' then ''||round(abs(a5.nd_sub),2) when '12.00' then '0' end as BAS_IMP2,";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, '0' as Ice1, '0' as Ice2, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12,2) when '70' then '0' when '100' then '0' end AS BAS_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '30' then '1' end AS COD_TIP30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12*0.30,2) when '70' then '0' when '100' then '0' end AS VAL_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12,2) when '100' then round(abs(a5.nd_sub)*0.12,2) end AS BAS_IVA70_100, ";
                strSQL+=" CASE a3.nd_porret when '70' then '2' when '100' then '3' end AS COD_TIP70_100, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12*0.70,2) when '100' then round(abs(a5.nd_sub)*0.12*1,2) end AS VAL_IVA70_100, ";
                strSQL+=" a5.tx_feccad as FEC_CAD_CLI, a1.tx_codsri as COD_SRI, ''||round(abs(a1.nd_abo),2) as VAL_RET, round(abs(a5.nd_sub),2) as BAS_IMP_RET, a2.fe_doc as FEC_REG, LPAD(a2.ne_numdoc1,7,'0') as NUM_RET, '' as NUM_SER_RET, '' as NUM_AUT_RET ";
                strSQL+=" , a5.co_emp, a5.co_loc, a5.co_tipdoc as TIP_DOC, a5.co_doc as COD_DOC, a5.ne_numdoc as NUM_DOC, round(abs(a5.nd_sub),2) as SUB_TOT, round(a5.nd_valiva,2) as IVA, round(a5.nd_tot,2) as TOTAL, a5.tx_secdoc as NUM_SEC, a1.co_tipdoc as TIPDOC1, a7.tx_descor as DESCOR2, a1.co_doc as CODDOC1, a1.co_reg as CODREG1 ";
                strSQL+=" FROM  tbm_detpag as a1";
                strSQL+=" INNER JOIN tbm_cabpag as a2 on (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a3 on (a1.co_emp=a3.co_emp AND a1.co_locpag=a3.co_loc AND a1.co_tipdocpag=a3.co_tipdoc AND a1.co_docpag=a3.co_doc AND a1.co_regpag=a3.co_reg)";
                strSQL+=" INNER JOIN tbm_cli as a4 on (a2.co_emp=a4.co_emp AND a2.co_cli=a4.co_cli)";
                strSQL+=" INNER JOIN tbm_cabmovinv as a5 on (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc AND a3.co_doc=a5.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a6 on (a5.co_emp=a6.co_emp and a5.co_loc=a6.co_loc and a5.co_tipdoc=a6.co_tipdoc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a7 on (a2.co_emp=a7.co_emp and a2.co_loc=a7.co_loc and a2.co_tipdoc=a7.co_tipdoc)";
                strSQL+=" WHERE a5.co_tipdoc in(2, 4, 32, 38, 57) and a1.co_tipdoc IN (33) and a2.st_reg NOT IN ('I') and a5.st_reg NOT IN ('E','I') AND a3.nd_porret IN ('1.00','5.00','8.00')";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=strAuxTmp;
                strSQL+=" ORDER BY a2.fe_doc, a2.ne_numdoc1";
                strSQL+=" ) ";
                
                strSQL+=" UNION ALL ";
                
                strSQL+=" (";
                strSQL+=" SELECT a5.tx_descor as DES_DOC, a3.tx_tipide as TIP_RUC,  a3.tx_ide as RUC_CED, a3.tx_nom as NOM_CLI, a1.fe_doc as FEC_REG, ";
                strSQL+=" a1.fe_doc as FEC_EMI, CASE a3.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI,";
                strSQL+=" a1.tx_secdoc as SER_DOC_CLI, a1.tx_numped as NUM_FAC_CLI, a1.tx_numautsri as NUM_AUT_CLI, '01' as TIP_COM,";
                strSQL+=" CASE a1.nd_poriva when null then '0' end as BAS_IMP1, '0' as Tip1, CASE a4.nd_porret when '0.00' then ''||(ROUND(a1.nd_sub,2)) end as BAS_IMP2,";
                strSQL+=" CASE a1.nd_poriva when '12.00' then ''||round(abs(a1.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, ";
                strSQL+=" '0' as Ice1, '0' as Ice2, null as BAS_IVA30, null AS COD_TIP30, null as VAL_IVA30, null as BAS_IVA70_100, null AS COD_TIP70_100, ";
                strSQL+=" null as VAL_IVA70_100, a1.tx_feccad as FEC_CAD_CLI, null as COD_SRI, null as VAL_RET, null as BAS_IMP_RET, a1.fe_doc as FEC_REG, ";
                strSQL+=" null as NUM_RET, null AS NUM_SER_RET, null as NUM_AUT_RET, a1.co_emp, a1.co_loc, a1.co_tipdoc as TIP_DOC,  a1.co_doc as COD_DOC, ";
                strSQL+=" a1.ne_numdoc as NUM_DOC, round(a1.nd_sub,2) as SUB_TOT, round(a1.nd_valiva,2) as IVA, round(a1.nd_tot,2) as TOTAL, null as NUM_SEC, null as TIPDOC1, a5.tx_descor as DESCOR2, null as CODDOC1, null as CODREG1 ";
                strSQL+=" FROM  tbm_cabmovinv AS a1 ";
                strSQL+=" LEFT OUTER JOIN tbm_motdoc as a2 on (a1.co_emp = a2.co_emp and a1.co_motdoc = a2.co_mot)";
                strSQL+=" INNER JOIN tbm_cli as a3 on (a1.co_emp=a3.co_emp and a1.co_cli=a3.co_cli)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a4 on (a1.co_emp=a4.co_emp and a1.co_loc=a4.co_loc and a1.co_tipdoc=a4.co_tipdoc and a1.co_doc=a4.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a5 on (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoc=a5.co_tipdoc)";
                strSQL+=" WHERE a1.co_tipdoc in (2, 4, 32, 38, 57) and a1.co_motdoc=5 and a2.st_reg NOT IN ('E','I') and a1.st_reg NOT IN ('E','I')";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                if (dtpFecDes.isFecha() && dtpFecHas.isFecha())
                {
                    strSQL+=" AND (a1.fe_doc>='" + objUti.formatearFecha(dtpFecDes.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    strSQL+=" AND a1.fe_doc<='" + objUti.formatearFecha(dtpFecHas.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') ";
                }
                
                if (txtCodTipDoc.getText().length()>0)
                    strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                
                if (txtCodCli.getText().length()>0)
                    strSQL+=" AND a3.co_cli=" + txtCodCli.getText();
                
                strSQL+=" )"; 
                strSQL+=") AS b1";
                
                System.out.println("Query de Retenciones Hechas opc.2 es: " +strSQL);   //AND a4.tx_tipper IN('J','N')
                
                
                
       /////////////////////////////////////////////////////////////////////////////////////////
                ///variables para esta seccion///
                String numfacdes = "";
                String numfachas = "";
                int varfacdes = 0;
                int varfachas = 0;
                
                stmB=con.createStatement();
                strSQLB="";
                strSQLB+=" SELECT MIN(a2.ne_numdoc1) as FacDes, MAX(a2.ne_numdoc1) as FacHas";
                strSQLB+=" FROM  tbm_detpag as a1";
                strSQLB+=" INNER JOIN tbm_cabpag as a2 on (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)";
                strSQLB+=" INNER JOIN tbm_pagmovinv as a3 on (a1.co_emp=a3.co_emp AND a1.co_locpag=a3.co_loc AND a1.co_tipdocpag=a3.co_tipdoc AND a1.co_docpag=a3.co_doc AND a1.co_regpag=a3.co_reg)";
                strSQLB+=" INNER JOIN tbm_cli as a4 on (a2.co_emp=a4.co_emp AND a2.co_cli=a4.co_cli)";
                strSQLB+=" INNER JOIN tbm_cabmovinv as a5 on (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc AND a3.co_doc=a5.co_doc)";
                strSQLB+=" WHERE a3.co_tipdoc in(2,38,57) and a1.co_tipdoc=33  and a2.st_reg NOT IN ('I') AND a3.nd_porret IN ('1','5','8')";
                strSQLB+=" AND a1.co_emp=" + intCodEmp;
                strSQLB+=" AND a1.co_loc=" + intCodLoc;
                strSQLB+=strAuxTmp;                
                System.out.println("Query para verificar Retenciones Hechas DESDE y HASTA: " +strSQLB);
                rstB=stmB.executeQuery(strSQLB);
                
                if(rstB.next())
                {
                    numfacdes = rstB.getString("FacDes");
                    varfacdes = Integer.parseInt(numfacdes);
                    System.out.println("El Inicio NumFacDes (varfacdes)es: " + varfacdes);
                    numfachas = rstB.getString("FacHas");
                    varfachas = Integer.parseInt(numfachas);
                    System.out.println("El Fin NumFacHas (varfachas)es: " + varfachas);
                }
       /////////////////////////////////////////////////////////////////////////////////////////
                
                //Sentencia que ejecuta el SQL
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                
                for (int x=0; rstA.next(); x++)                
                ///while(rstA.next())
                {                  

                        for (int y=0; rst.next(); y++)
                        ///while(rst.next())
                        {                               
                                    vecReg=new Vector();                                                                        
                                    vecReg.add(INT_TBL_DAT_LIN,"");
                                    vecReg.add(INT_TBL_DAT_FEC_REG,rst.getString("FEC_REG"));                                    
                                    vecReg.add(INT_TBL_DAT_DES_DOC,rst.getString("DES_DOC"));
                                    vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("NUM_DOC"));
                                    vecReg.add(INT_TBL_DAT_SUB_TOT,rst.getString("SUB_TOT"));
                                    vecReg.add(INT_TBL_DAT_IVA_VAL,rst.getString("IVA"));
                                    vecReg.add(INT_TBL_DAT_VAL_TOT,rst.getString("TOTAL"));
                                    vecReg.add(INT_TBL_DAT_TIP_RUC,rst.getString("TIP_RUC"));
                                    vecReg.add(INT_TBL_DAT_RUC_CED,rst.getString("RUC_CED"));
                                    vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("NOM_CLI"));
                                    ///vecReg.add(INT_TBL_DAT_FEC_REG,rst.getString("FEC_REG"));
                                    vecReg.add(INT_TBL_DAT_FEC_EMI,rst.getString("FEC_EMI"));
                                    vecReg.add(INT_TBL_DAT_SER_DOC,rst.getString("SER_DOC_CLI"));
                                    vecReg.add(INT_TBL_DAT_NUM_FAC,rst.getString("NUM_FAC_CLI"));
                                    vecReg.add(INT_TBL_DAT_NUM_AUT,rst.getString("NUM_AUT_CLI"));
                                    vecReg.add(INT_TBL_DAT_TIP_COM,rst.getString("TIP_COM"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_CON,rst.getString("BAS_IMP1"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_SIN,rst.getString("BAS_IMP2"));
                                    vecReg.add(INT_TBL_DAT_VAL_IVA,rst.getString("VAL_IVA"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_I30,rst.getString("BAS_IVA30"));
                                    vecReg.add(INT_TBL_DAT_VAL_BAS_I30,rst.getString("VAL_IVA30"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_I70,rst.getString("BAS_IVA70_100"));
                                    vecReg.add(INT_TBL_DAT_VAL_BAS_I70,rst.getString("VAL_IVA70_100"));
                                    vecReg.add(INT_TBL_DAT_FEC_CAD,rst.getString("FEC_CAD_CLI"));
                                    vecReg.add(INT_TBL_DAT_COD_RET,rst.getString("COD_SRI"));
                                    vecReg.add(INT_TBL_DAT_VAL_RET,rst.getString("VAL_RET"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_RET,rst.getString("BAS_IMP_RET"));
                                    vecReg.add(INT_TBL_DAT_FEC_RET,rst.getString("FEC_REG"));
                                    vecReg.add(INT_TBL_DAT_SER_RET,rstA.getString("NumSerFac"));
                                    vecReg.add(INT_TBL_DAT_NUM_RET,rst.getString("NUM_RET"));
                                    vecReg.add(INT_TBL_DAT_AUT_RET,rstA.getString("tx_numautsri"));
                                    vecReg.add(INT_TBL_DAT_TIP_DOC,rst.getString("TIP_DOC"));
                                    vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("COD_DOC"));
                                    vecReg.add(INT_TBL_DAT_TIPDOC1,rst.getString("TIPDOC1"));
                                    vecReg.add(INT_TBL_DAT_CODDOC1,rst.getString("CODDOC1"));
                                    vecReg.add(INT_TBL_DAT_CODREG1,rst.getString("CODREG1"));
                                    vecReg.add(INT_TBL_DAT_DESCOR2,rst.getString("DESCOR2"));
                                    vecDat.add(vecReg);
                                    i++;
                                    pgrSis.setValue(i);
                        }///fin del if rst///
                }///fin del if rstA
              
                rst.close();
                stm.close();
                rstA.close();
                stmA.close();
                con.close();
                rst=null;
                stm=null;
                rstA=null;
                stmA=null;
                con=null;

                
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                if (intNumTotReg==tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
                pgrSis.setValue(0);
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
    //final opcion 2 los registros con CodRet307//
    
    //FUNCION PARA ESCOGER LA OPCION 3 --MUESTRA LOS REGIDTROS CON CODRET30B--
    private boolean cargarDetRegOp3()
    {
        int intCodEmp, intCodLoc, intNumTotReg, intNumTotRegSRI, i=0, coreg=0, nudocdes=0, nudochas=0;
        boolean blnRes=true;
        int ndoc = 0;
        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                
                /////////////////////////DATOS PARA EL SRI////////////////////////
                stmA=con.createStatement();
                
                strSQLA="";
                strSQLA+=" SELECT count(*)";
                strSQLA+=" FROM  tbm_cabtipdoc as a1";
                strSQLA+=" INNER JOIN tbm_datautsri as a2 on(a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQLA+=" WHERE a2.co_tipdoc in (2, 33) AND a1.co_emp=" + intCodEmp;
                strSQLA+=" AND a1.co_loc=" + intCodLoc;
                
                intNumTotRegSRI=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQLA);
                if (intNumTotRegSRI==-1)
                    return false;
                
                System.out.println("El total de registros para el SRI es: " +intNumTotRegSRI);
                                

                ///Obtener datos de tabla tbm_datautsri para declaracion de Retenciones///                                                                        
                strSQLA="";
                strSQLA+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a2.co_reg, a1.tx_descor, a2.ne_numdocdes, a2.ne_numdochas, REPLACE(a2.tx_numserfac,'-','') as NumSerFac, a2.tx_numautsri, a2.tx_feccadfac";
                strSQLA+=" FROM  tbm_cabtipdoc as a1";
                strSQLA+=" INNER JOIN tbm_datautsri as a2 on(a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQLA+=" WHERE a2.co_tipdoc in (2, 33) AND a1.co_emp=" + intCodEmp;
                strSQLA+=" AND a1.co_loc=" + intCodLoc;                        
                strSQLA+=" ORDER BY a2.co_reg ";                                                               
                System.out.println("El Query para Datos del SRI es: " +strSQLA); 

                //Sentencia que ejecuta el SQL
                rstA=stmA.executeQuery(strSQLA);
                ////////////////////////////////////////////////////////////////                        
                
                stm=con.createStatement();
                //Obtener la condición.
                strAuxTmp="";
                
                //hace referencia a la tabla tbm_cabtipdoc //
                if (txtCodTipDoc.getText().length()>0)
                    strAuxTmp+=" AND a3.co_tipDoc=" + txtCodTipDoc.getText(); 
                
                //hace referencia a la tabla tbm_cli//
                if (txtCodCli.getText().length()>0)
                    strAuxTmp+=" AND a4.co_cli=" + txtCodCli.getText();
                
                //Hace referencia a la fechas//                
                if (dtpFecDes.isFecha() && dtpFecHas.isFecha()){
                    strAuxTmp+=" AND (a2.fe_doc>='" + objUti.formatearFecha(dtpFecDes.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    strAuxTmp+=" AND a2.fe_doc<='" + objUti.formatearFecha(dtpFecHas.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') ";                    
                }
                                       
                //Obtener el número total de registros.
                strSQL="";
                strSQL+="SELECT COUNT (*) ";
                strSQL+=" FROM (";
                strSQL+=" (";
                strSQL+=" SELECT a6.tx_descor as DES_DOC, a4.tx_tipide as TIP_RUC, a4.tx_ide AS RUC_CED, a4.tx_nom as NOM_CLI, a2.fe_doc as FEC_REG, a2.fe_doc as FEC_EMI, ";
                strSQL+=" CASE a4.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI, REPLACE(a5.tx_secdoc,'-','') as SER_DOC_CLI, ";
                strSQL+=" LPAD((REPLACE(a5.tx_numped,'-','')),7,'0') as NUM_FAC_CLI, a5.tx_numAutSRI as NUM_AUT_CLI, '01' as TIP_COM, ";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub),2) when '0.00' then '0' end as BAS_IMP1, '2' as Tip1, ";
                strSQL+=" CASE a5.nd_poriva when '0.00' then ''||round(abs(a5.nd_sub),2) when '12.00' then '0' end as BAS_IMP2,";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, '0' as Ice1, '0' as Ice2, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12,2) when '70' then '0' when '100' then '0' end AS BAS_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '30' then '1' end AS COD_TIP30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12*0.30,2) when '70' then '0' when '100' then '0' end AS VAL_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12,2) when '100' then round(abs(a5.nd_sub)*0.12,2) end AS BAS_IVA70_100, ";
                strSQL+=" CASE a3.nd_porret when '70' then '2' when '100' then '3' end AS COD_TIP70_100, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12*0.70,2) when '100' then round(abs(a5.nd_sub)*0.12*1,2) end AS VAL_IVA70_100, ";
                strSQL+=" a5.tx_feccad as FEC_CAD_CLI, a1.tx_codsri as COD_SRI, ''||round(abs(a1.nd_abo),2) as VAL_RET, round(abs(a5.nd_sub),2) as BAS_IMP_RET, a2.fe_doc as FEC_REG, LPAD(a2.ne_numdoc1,7,'0') as NUM_RET, '' as NUM_SER_RET, '' as NUM_AUT_RET ";
                strSQL+=" , a5.co_emp, a5.co_loc, a5.co_tipdoc as TIP_DOC, a5.co_doc as COD_DOC, a5.ne_numdoc as NUM_DOC, round(abs(a5.nd_sub),2) as SUB_TOT, round(a5.nd_valiva,2) as IVA, round(a5.nd_tot,2) as TOTAL, a5.tx_secdoc as NUM_SEC, a1.co_tipdoc as TIPDOC1, a7.tx_descor as DESCOR2, a1.co_doc as CODDOC1, a1.co_reg as CODREG1 ";
                strSQL+=" FROM  tbm_detpag as a1";
                strSQL+=" INNER JOIN tbm_cabpag as a2 on (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a3 on (a1.co_emp=a3.co_emp AND a1.co_locpag=a3.co_loc AND a1.co_tipdocpag=a3.co_tipdoc AND a1.co_docpag=a3.co_doc AND a1.co_regpag=a3.co_reg)";
                strSQL+=" INNER JOIN tbm_cli as a4 on (a2.co_emp=a4.co_emp AND a2.co_cli=a4.co_cli)";
                strSQL+=" INNER JOIN tbm_cabmovinv as a5 on (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc AND a3.co_doc=a5.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a6 on (a5.co_emp=a6.co_emp and a5.co_loc=a6.co_loc and a5.co_tipdoc=a6.co_tipdoc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a7 on (a2.co_emp=a7.co_emp and a2.co_loc=a7.co_loc and a2.co_tipdoc=a7.co_tipdoc)";
                strSQL+=" WHERE a5.co_tipdoc in(2, 4, 32, 38, 57) and a1.co_tipdoc IN (33)  and a2.st_reg NOT IN ('I') AND a3.nd_porret IN ('0.1','30.00','70.00','100.00')";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=strAuxTmp;
                strSQL+=" ORDER BY a2.fe_doc, a2.ne_numdoc1";
                strSQL+=" ) ";
                
                strSQL+=" UNION ALL ";
                
                strSQL+=" (";
                strSQL+=" SELECT a5.tx_descor as DES_DOC, a3.tx_tipide as TIP_RUC,  a3.tx_ide as RUC_CED, a3.tx_nom as NOM_CLI, a1.fe_doc as FEC_REG, ";
                strSQL+=" a1.fe_doc as FEC_EMI, CASE a3.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI,";
                strSQL+=" a1.tx_secdoc as SER_DOC_CLI, a1.tx_numped as NUM_FAC_CLI, a1.tx_numautsri as NUM_AUT_CLI, '01' as TIP_COM,";
                strSQL+=" CASE a1.nd_poriva when null then '0' end as BAS_IMP1, '0' as Tip1, CASE a4.nd_porret when '0.00' then ''||(ROUND(a1.nd_sub,2)) end as BAS_IMP2,";
                strSQL+=" CASE a1.nd_poriva when '12.00' then ''||round(abs(a1.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, ";
                strSQL+=" '0' as Ice1, '0' as Ice2, null as BAS_IVA30, null AS COD_TIP30, null as VAL_IVA30, null as BAS_IVA70_100, null AS COD_TIP70_100, ";
                strSQL+=" null as VAL_IVA70_100, a1.tx_feccad as FEC_CAD_CLI, null as COD_SRI, null as VAL_RET, null as BAS_IMP_RET, a1.fe_doc as FEC_REG, ";
                strSQL+=" null as NUM_RET, null AS NUM_SER_RET, null as NUM_AUT_RET, a1.co_emp, a1.co_loc, a1.co_tipdoc as TIP_DOC,  a1.co_doc as COD_DOC, ";
                strSQL+=" a1.ne_numdoc as NUM_DOC, round(a1.nd_sub,2) as SUB_TOT, round(a1.nd_valiva,2) as IVA, round(a1.nd_tot,2) as TOTAL, null as NUM_SEC, null as TIPDOC1, a5.tx_descor as DESCOR2, null as CODDOC1, null as CODREG1 ";
                strSQL+=" FROM  tbm_cabmovinv AS a1 ";
                strSQL+=" LEFT OUTER JOIN tbm_motdoc as a2 on (a1.co_emp = a2.co_emp and a1.co_motdoc = a2.co_mot)";
                strSQL+=" INNER JOIN tbm_cli as a3 on (a1.co_emp=a3.co_emp and a1.co_cli=a3.co_cli)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a4 on (a1.co_emp=a4.co_emp and a1.co_loc=a4.co_loc and a1.co_tipdoc=a4.co_tipdoc and a1.co_doc=a4.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a5 on (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoc=a5.co_tipdoc)";
                strSQL+=" WHERE a1.co_tipdoc in (2, 4, 32, 38, 57) and a1.co_motdoc=5 and a2.st_reg NOT IN ('E','I') and a1.st_reg NOT IN ('E','I')";                
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                if (dtpFecDes.isFecha() && dtpFecHas.isFecha())
                {
                    strSQL+=" AND (a1.fe_doc>='" + objUti.formatearFecha(dtpFecDes.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    strSQL+=" AND a1.fe_doc<='" + objUti.formatearFecha(dtpFecHas.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') ";
                }
                
                if (txtCodTipDoc.getText().length()>0)
                    strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                
                if (txtCodCli.getText().length()>0)
                    strSQL+=" AND a3.co_cli=" + txtCodCli.getText();
                
                strSQL+=" )"; 
                strSQL+=") AS b1";
                
                System.out.println("El Query del count(*) opc3: " +strSQL);
                
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT * ";
                strSQL+=" FROM (";
                strSQL+=" (";
                strSQL+=" SELECT a6.tx_descor as DES_DOC, a4.tx_tipide as TIP_RUC, a4.tx_ide AS RUC_CED, a4.tx_nom as NOM_CLI, a2.fe_doc as FEC_REG, a2.fe_doc as FEC_EMI, ";
                strSQL+=" CASE a4.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI, REPLACE(a5.tx_secdoc,'-','') as SER_DOC_CLI, ";
                strSQL+=" LPAD((REPLACE(a5.tx_numped,'-','')),7,'0') as NUM_FAC_CLI, a5.tx_numAutSRI as NUM_AUT_CLI, '01' as TIP_COM, ";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub),2) when '0.00' then '0' end as BAS_IMP1, '2' as Tip1, ";
                strSQL+=" CASE a5.nd_poriva when '0.00' then ''||round(abs(a5.nd_sub),2) when '12.00' then '0' end as BAS_IMP2,";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, '0' as Ice1, '0' as Ice2, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12,2) when '70' then '0' when '100' then '0' end AS BAS_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '30' then '1' end AS COD_TIP30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12*0.30,2) when '70' then '0' when '100' then '0' end AS VAL_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12,2) when '100' then round(abs(a5.nd_sub)*0.12,2) end AS BAS_IVA70_100, ";
                strSQL+=" CASE a3.nd_porret when '70' then '2' when '100' then '3' end AS COD_TIP70_100, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12*0.70,2) when '100' then round(abs(a5.nd_sub)*0.12*1,2) end AS VAL_IVA70_100, ";
                strSQL+=" a5.tx_feccad as FEC_CAD_CLI, a1.tx_codsri as COD_SRI, ''||round(abs(a1.nd_abo),2) as VAL_RET, round(abs(a5.nd_sub),2) as BAS_IMP_RET, a2.fe_doc as FEC_REG, LPAD(a2.ne_numdoc1,7,'0') as NUM_RET, '' as NUM_SER_RET, '' as NUM_AUT_RET ";
                strSQL+=" , a5.co_emp, a5.co_loc, a5.co_tipdoc as TIP_DOC, a5.co_doc as COD_DOC, a5.ne_numdoc as NUM_DOC, round(abs(a5.nd_sub),2) as SUB_TOT, round(a5.nd_valiva,2) as IVA, round(a5.nd_tot,2) as TOTAL, a5.tx_secdoc as NUM_SEC, a1.co_tipdoc as TIPDOC1, a7.tx_descor as DESCOR2, a1.co_doc as CODDOC1, a1.co_reg as CODREG1 ";
                strSQL+=" FROM  tbm_detpag as a1";
                strSQL+=" INNER JOIN tbm_cabpag as a2 on (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a3 on (a1.co_emp=a3.co_emp AND a1.co_locpag=a3.co_loc AND a1.co_tipdocpag=a3.co_tipdoc AND a1.co_docpag=a3.co_doc AND a1.co_regpag=a3.co_reg)";
                strSQL+=" INNER JOIN tbm_cli as a4 on (a2.co_emp=a4.co_emp AND a2.co_cli=a4.co_cli)";
                strSQL+=" INNER JOIN tbm_cabmovinv as a5 on (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc AND a3.co_doc=a5.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a6 on (a5.co_emp=a6.co_emp and a5.co_loc=a6.co_loc and a5.co_tipdoc=a6.co_tipdoc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a7 on (a2.co_emp=a7.co_emp and a2.co_loc=a7.co_loc and a2.co_tipdoc=a7.co_tipdoc)";
                strSQL+=" WHERE a5.co_tipdoc in(2, 4, 32, 38, 57) and a1.co_tipdoc IN (33) and a2.st_reg NOT IN ('I') and a5.st_reg NOT IN ('E','I') AND a3.nd_porret IN ('0.1','30.00','70.00','100.00')";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=strAuxTmp;
                strSQL+=" ORDER BY a2.fe_doc, a2.ne_numdoc1";
                strSQL+=" ) ";
                
                strSQL+=" UNION ALL ";
                
                strSQL+=" (";
                strSQL+=" SELECT a5.tx_descor as DES_DOC, a3.tx_tipide as TIP_RUC,  a3.tx_ide as RUC_CED, a3.tx_nom as NOM_CLI, a1.fe_doc as FEC_REG, ";
                strSQL+=" a1.fe_doc as FEC_EMI, CASE a3.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI,";
                strSQL+=" a1.tx_secdoc as SER_DOC_CLI, a1.tx_numped as NUM_FAC_CLI, a1.tx_numautsri as NUM_AUT_CLI, '01' as TIP_COM,";
                strSQL+=" CASE a1.nd_poriva when null then '0' end as BAS_IMP1, '0' as Tip1, CASE a4.nd_porret when '0.00' then ''||(ROUND(a1.nd_sub,2)) end as BAS_IMP2,";
                strSQL+=" CASE a1.nd_poriva when '12.00' then ''||round(abs(a1.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, ";
                strSQL+=" '0' as Ice1, '0' as Ice2, null as BAS_IVA30, null AS COD_TIP30, null as VAL_IVA30, null as BAS_IVA70_100, null AS COD_TIP70_100, ";
                strSQL+=" null as VAL_IVA70_100, a1.tx_feccad as FEC_CAD_CLI, null as COD_SRI, null as VAL_RET, null as BAS_IMP_RET, a1.fe_doc as FEC_REG, ";
                strSQL+=" null as NUM_RET, null AS NUM_SER_RET, null as NUM_AUT_RET, a1.co_emp, a1.co_loc, a1.co_tipdoc as TIP_DOC,  a1.co_doc as COD_DOC, ";
                strSQL+=" a1.ne_numdoc as NUM_DOC, round(a1.nd_sub,2) as SUB_TOT, round(a1.nd_valiva,2) as IVA, round(a1.nd_tot,2) as TOTAL, null as NUM_SEC, null as TIPDOC1, a5.tx_descor as DESCOR2, null as CODDOC1, null as CODREG1 ";
                strSQL+=" FROM  tbm_cabmovinv AS a1 ";
                strSQL+=" LEFT OUTER JOIN tbm_motdoc as a2 on (a1.co_emp = a2.co_emp and a1.co_motdoc = a2.co_mot)";
                strSQL+=" INNER JOIN tbm_cli as a3 on (a1.co_emp=a3.co_emp and a1.co_cli=a3.co_cli)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a4 on (a1.co_emp=a4.co_emp and a1.co_loc=a4.co_loc and a1.co_tipdoc=a4.co_tipdoc and a1.co_doc=a4.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a5 on (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoc=a5.co_tipdoc)";
                strSQL+=" WHERE a1.co_tipdoc in (2, 4, 32, 38, 57) and a1.co_motdoc=5 and a2.st_reg NOT IN ('E','I') and a1.st_reg NOT IN ('E','I')";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                if (dtpFecDes.isFecha() && dtpFecHas.isFecha())
                {
                    strSQL+=" AND (a1.fe_doc>='" + objUti.formatearFecha(dtpFecDes.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    strSQL+=" AND a1.fe_doc<='" + objUti.formatearFecha(dtpFecHas.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') ";
                }
                
                if (txtCodTipDoc.getText().length()>0)
                    strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                
                if (txtCodCli.getText().length()>0)
                    strSQL+=" AND a3.co_cli=" + txtCodCli.getText();
                
                strSQL+=" )"; 
                strSQL+=") AS b1";
                
                System.out.println("Query de Retenciones Hechas opc.3 es: " +strSQL);
                
                
       /////////////////////////////////////////////////////////////////////////////////////////
                ///variables para esta seccion///
                String numfacdes = "";
                String numfachas = "";
                int varfacdes = 0;
                int varfachas = 0;
                
                stmB=con.createStatement();
                strSQLB="";
                strSQLB+=" SELECT MIN(a2.ne_numdoc1) as FacDes, MAX(a2.ne_numdoc1) as FacHas";
                strSQLB+=" FROM  tbm_detpag as a1";
                strSQLB+=" INNER JOIN tbm_cabpag as a2 on (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)";
                strSQLB+=" INNER JOIN tbm_pagmovinv as a3 on (a1.co_emp=a3.co_emp AND a1.co_locpag=a3.co_loc AND a1.co_tipdocpag=a3.co_tipdoc AND a1.co_docpag=a3.co_doc AND a1.co_regpag=a3.co_reg)";
                strSQLB+=" INNER JOIN tbm_cli as a4 on (a2.co_emp=a4.co_emp AND a2.co_cli=a4.co_cli)";
                strSQLB+=" INNER JOIN tbm_cabmovinv as a5 on (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc AND a3.co_doc=a5.co_doc)";
                strSQLB+=" WHERE a3.co_tipdoc in(2,38,57) and a1.co_tipdoc=33  and a2.st_reg NOT IN ('I') AND a3.nd_porret IN ('0.1','30','70','100')";
                strSQLB+=" AND a1.co_emp=" + intCodEmp;
                strSQLB+=" AND a1.co_loc=" + intCodLoc;
                strSQLB+=strAuxTmp;                
                System.out.println("Query para verificar Retenciones Hechas DESDE y HASTA: " +strSQLB);
                rstB=stmB.executeQuery(strSQLB);
                
                if(rstB.next())
                {
                    numfacdes = rstB.getString("FacDes");
                    varfacdes = Integer.parseInt(numfacdes);
                    System.out.println("El Inicio NumFacDes (varfacdes)es: " + varfacdes);
                    numfachas = rstB.getString("FacHas");
                    varfachas = Integer.parseInt(numfachas);
                    System.out.println("El Fin NumFacHas (varfachas)es: " + varfachas);
                }
       /////////////////////////////////////////////////////////////////////////////////////////
                
                
                //Sentencia que ejecuta el SQL
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                
                for (int x=0; rstA.next(); x++)                
                ///while(rstA.next())
                {           

                        for (int y=0; rst.next(); y++)
                        ///while(rst.next())
                        {               
                                    vecReg=new Vector();                                                                        
                                    vecReg.add(INT_TBL_DAT_LIN,"");
                                    vecReg.add(INT_TBL_DAT_FEC_REG,rst.getString("FEC_REG"));                                    
                                    vecReg.add(INT_TBL_DAT_DES_DOC,rst.getString("DES_DOC"));
                                    vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("NUM_DOC"));
                                    vecReg.add(INT_TBL_DAT_SUB_TOT,rst.getString("SUB_TOT"));
                                    vecReg.add(INT_TBL_DAT_IVA_VAL,rst.getString("IVA"));
                                    vecReg.add(INT_TBL_DAT_VAL_TOT,rst.getString("TOTAL"));                                    
                                    vecReg.add(INT_TBL_DAT_TIP_RUC,rst.getString("TIP_RUC"));
                                    vecReg.add(INT_TBL_DAT_RUC_CED,rst.getString("RUC_CED"));
                                    vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("NOM_CLI"));
                                    ///vecReg.add(INT_TBL_DAT_FEC_REG,rst.getString("FEC_REG"));
                                    vecReg.add(INT_TBL_DAT_FEC_EMI,rst.getString("FEC_EMI"));
                                    vecReg.add(INT_TBL_DAT_SER_DOC,rst.getString("SER_DOC_CLI"));
                                    vecReg.add(INT_TBL_DAT_NUM_FAC,rst.getString("NUM_FAC_CLI"));
                                    vecReg.add(INT_TBL_DAT_NUM_AUT,rst.getString("NUM_AUT_CLI"));
                                    vecReg.add(INT_TBL_DAT_TIP_COM,rst.getString("TIP_COM"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_CON,rst.getString("BAS_IMP1"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_SIN,rst.getString("BAS_IMP2"));
                                    vecReg.add(INT_TBL_DAT_VAL_IVA,rst.getString("VAL_IVA"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_I30,rst.getString("BAS_IVA30"));
                                    vecReg.add(INT_TBL_DAT_VAL_BAS_I30,rst.getString("VAL_IVA30"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_I70,rst.getString("BAS_IVA70_100"));
                                    vecReg.add(INT_TBL_DAT_VAL_BAS_I70,rst.getString("VAL_IVA70_100"));
                                    vecReg.add(INT_TBL_DAT_FEC_CAD,rst.getString("FEC_CAD_CLI"));
                                    vecReg.add(INT_TBL_DAT_COD_RET,rst.getString("COD_SRI"));
                                    vecReg.add(INT_TBL_DAT_VAL_RET,rst.getString("VAL_RET"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_RET,rst.getString("BAS_IMP_RET"));
                                    vecReg.add(INT_TBL_DAT_FEC_RET,rst.getString("FEC_REG"));
                                    vecReg.add(INT_TBL_DAT_SER_RET,rstA.getString("NumSerFac"));
                                    vecReg.add(INT_TBL_DAT_NUM_RET,rst.getString("NUM_RET"));
                                    vecReg.add(INT_TBL_DAT_AUT_RET,rstA.getString("tx_numautsri"));
                                    vecReg.add(INT_TBL_DAT_TIP_DOC,rst.getString("TIP_DOC"));
                                    vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("COD_DOC"));
                                    vecReg.add(INT_TBL_DAT_TIPDOC1,rst.getString("TIPDOC1"));
                                    vecReg.add(INT_TBL_DAT_CODDOC1,rst.getString("CODDOC1"));
                                    vecReg.add(INT_TBL_DAT_CODREG1,rst.getString("CODREG1"));
                                    vecReg.add(INT_TBL_DAT_DESCOR2,rst.getString("DESCOR2"));
                                    vecDat.add(vecReg);
                                    i++;
                                    pgrSis.setValue(i);
                        }///fin del if rst///
                }///fin del if rstA
              
                rst.close();
                stm.close();
                rstA.close();
                stmA.close();
                con.close();
                rst=null;
                stm=null;
                rstA=null;
                stmA=null;
                con=null;                
                
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                if (intNumTotReg==tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
                pgrSis.setValue(0);
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
    //final opcion 3 los registros con CodRet30B//
    
    //FUNCION PARA ESCOGER LA OPCION 4 --MUESTRA TODOS LOS REGISTROS de Facturas sin Datos del SRI--
    private boolean cargarDetRegOp4()
    {
        int intCodEmp, intCodLoc, intNumTotReg, intNumTotRegSRI, i=0, coreg=0, nudocdes=0, nudochas=0;
        boolean blnRes=true;
        int ndoc = 0;
        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                /////////////////////////DATOS PARA EL SRI////////////////////////
                stmA=con.createStatement();
                
                strSQLA="";
                strSQLA+=" SELECT count(*)";
                strSQLA+=" FROM  tbm_cabtipdoc as a1";
                strSQLA+=" INNER JOIN tbm_datautsri as a2 on(a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQLA+=" WHERE a2.co_tipdoc in (2, 33) AND a1.co_emp=" + intCodEmp;
                strSQLA+=" AND a1.co_loc=" + intCodLoc;
                
                intNumTotRegSRI=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQLA);
                if (intNumTotRegSRI==-1)
                    return false;
                
                System.out.println("El total de registros para el SRI es: " +intNumTotRegSRI);
                                

                ///Obtener datos de tabla tbm_datautsri para declaracion de Retenciones///                                                                        
                strSQLA="";
                strSQLA+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a2.co_reg, a1.tx_descor, a2.ne_numdocdes, a2.ne_numdochas, REPLACE(a2.tx_numserfac,'-','') as NumSerFac, a2.tx_numautsri, a2.tx_feccadfac";
                strSQLA+=" FROM  tbm_cabtipdoc as a1";
                strSQLA+=" INNER JOIN tbm_datautsri as a2 on(a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQLA+=" WHERE a2.co_tipdoc in (2, 33) AND a1.co_emp=" + intCodEmp;
                strSQLA+=" AND a1.co_loc=" + intCodLoc;                        
                strSQLA+=" ORDER BY a2.co_reg ";                                                               
                System.out.println("El Query para Datos del SRI es: " +strSQLA);

                //Sentencia que ejecuta el SQL
                rstA=stmA.executeQuery(strSQLA);
                ////////////////////////////////////////////////////////////////
                
                stm=con.createStatement();
                //Obtener la condición.
                strAuxTmp="";
                
                //hace referencia a la tabla tbm_cabtipdoc //
                if (txtCodTipDoc.getText().length()>0)
                    strAuxTmp+=" AND a3.co_tipDoc=" + txtCodTipDoc.getText(); 
                
                //hace referencia a la tabla tbm_cli//
                if (txtCodCli.getText().length()>0)
                    strAuxTmp+=" AND a4.co_cli=" + txtCodCli.getText();
                
                //Hace referencia a la fechas//                
                if (dtpFecDes.isFecha() && dtpFecHas.isFecha()){
                    strAuxTmp+=" AND (a2.fe_doc>='" + objUti.formatearFecha(dtpFecDes.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    strAuxTmp+=" AND a2.fe_doc<='" + objUti.formatearFecha(dtpFecHas.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') ";                    
                }
                                       
                //Obtener el número total de registros.
                strSQL="";
                strSQL+="SELECT COUNT (*) ";
                strSQL+=" FROM (";
                strSQL+=" (";
                strSQL+=" SELECT a6.tx_descor as DES_DOC, a4.tx_tipide as TIP_RUC, a4.tx_ide AS RUC_CED, a4.tx_nom as NOM_CLI, a2.fe_doc as FEC_REG, a2.fe_doc as FEC_EMI, ";
                strSQL+=" CASE a4.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI, REPLACE(a5.tx_secdoc,'-','') as SER_DOC_CLI, ";
                strSQL+=" LPAD((REPLACE(a5.tx_numped,'-','')),7,'0') as NUM_FAC_CLI, a5.tx_numAutSRI as NUM_AUT_CLI, '01' as TIP_COM, ";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub),2) when '0.00' then '0' end as BAS_IMP1, '2' as Tip1, ";
                strSQL+=" CASE a5.nd_poriva when '0.00' then ''||round(abs(a5.nd_sub),2) when '12.00' then '0' end as BAS_IMP2,";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, '0' as Ice1, '0' as Ice2, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12,2) when '70' then '0' when '100' then '0' end AS BAS_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '30' then '1' end AS COD_TIP30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12*0.30,2) when '70' then '0' when '100' then '0' end AS VAL_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12,2) when '100' then round(abs(a5.nd_sub)*0.12,2) end AS BAS_IVA70_100, ";
                strSQL+=" CASE a3.nd_porret when '70' then '2' when '100' then '3' end AS COD_TIP70_100, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12*0.70,2) when '100' then round(abs(a5.nd_sub)*0.12*1,2) end AS VAL_IVA70_100, ";
                strSQL+=" a5.tx_feccad as FEC_CAD_CLI, a1.tx_codsri as COD_SRI, ''||round(abs(a1.nd_abo),2) as VAL_RET, round(abs(a5.nd_sub),2) as BAS_IMP_RET, a2.fe_doc as FEC_REG, LPAD(a2.ne_numdoc1,7,'0') as NUM_RET, '' as NUM_SER_RET, '' as NUM_AUT_RET ";
                strSQL+=" , a5.co_emp, a5.co_loc, a5.co_tipdoc as TIP_DOC, a5.co_doc as COD_DOC, a5.ne_numdoc as NUM_DOC, round(abs(a5.nd_sub),2) as SUB_TOT, round(a5.nd_valiva,2) as IVA, round(a5.nd_tot,2) as TOTAL, a5.tx_secdoc as NUM_SEC, a1.co_tipdoc as TIPDOC1, a7.tx_descor as DESCOR2, a1.co_doc as CODDOC1, a1.co_reg as CODREG1 ";
                strSQL+=" FROM  tbm_detpag as a1";
                strSQL+=" INNER JOIN tbm_cabpag as a2 on (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a3 on (a1.co_emp=a3.co_emp AND a1.co_locpag=a3.co_loc AND a1.co_tipdocpag=a3.co_tipdoc AND a1.co_docpag=a3.co_doc AND a1.co_regpag=a3.co_reg)";
                strSQL+=" INNER JOIN tbm_cli as a4 on (a2.co_emp=a4.co_emp AND a2.co_cli=a4.co_cli)";
                strSQL+=" INNER JOIN tbm_cabmovinv as a5 on (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc AND a3.co_doc=a5.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a6 on (a5.co_emp=a6.co_emp and a5.co_loc=a6.co_loc and a5.co_tipdoc=a6.co_tipdoc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a7 on (a2.co_emp=a7.co_emp and a2.co_loc=a7.co_loc and a2.co_tipdoc=a7.co_tipdoc)";
                strSQL+=" WHERE a5.co_tipdoc in(2, 4, 32, 38, 57) and a1.co_tipdoc IN (33)  and a2.st_reg NOT IN ('I') AND (a5.tx_secdoc not like '0%' OR a5.tx_secdoc is null)";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=strAuxTmp;
                strSQL+=" ORDER BY a2.fe_doc, a2.ne_numdoc1";
                strSQL+=" ) ";
                
                strSQL+=" UNION ALL ";
                
                strSQL+=" (";
                strSQL+=" SELECT a5.tx_descor as DES_DOC, a3.tx_tipide as TIP_RUC,  a3.tx_ide as RUC_CED, a3.tx_nom as NOM_CLI, a1.fe_doc as FEC_REG, ";
                strSQL+=" a1.fe_doc as FEC_EMI, CASE a3.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI,";
                strSQL+=" a1.tx_secdoc as SER_DOC_CLI, a1.tx_numped as NUM_FAC_CLI, a1.tx_numautsri as NUM_AUT_CLI, '01' as TIP_COM,";
                strSQL+=" CASE a1.nd_poriva when null then '0' end as BAS_IMP1, '0' as Tip1, CASE a4.nd_porret when '0.00' then ''||(ROUND(a1.nd_sub,2)) end as BAS_IMP2,";
                strSQL+=" CASE a1.nd_poriva when '12.00' then ''||round(abs(a1.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, ";
                strSQL+=" '0' as Ice1, '0' as Ice2, null as BAS_IVA30, null AS COD_TIP30, null as VAL_IVA30, null as BAS_IVA70_100, null AS COD_TIP70_100, ";
                strSQL+=" null as VAL_IVA70_100, a1.tx_feccad as FEC_CAD_CLI, null as COD_SRI, null as VAL_RET, null as BAS_IMP_RET, a1.fe_doc as FEC_REG, ";
                strSQL+=" null as NUM_RET, null AS NUM_SER_RET, null as NUM_AUT_RET, a1.co_emp, a1.co_loc, a1.co_tipdoc as TIP_DOC,  a1.co_doc as COD_DOC, ";
                strSQL+=" a1.ne_numdoc as NUM_DOC, round(a1.nd_sub,2) as SUB_TOT, round(a1.nd_valiva,2) as IVA, round(a1.nd_tot,2) as TOTAL, null as NUM_SEC, null as TIPDOC1, a5.tx_descor as DESCOR2, null as CODDOC1, null as CODREG1 ";
                strSQL+=" FROM  tbm_cabmovinv AS a1 ";
                strSQL+=" LEFT OUTER JOIN tbm_motdoc as a2 on (a1.co_emp = a2.co_emp and a1.co_motdoc = a2.co_mot)";
                strSQL+=" INNER JOIN tbm_cli as a3 on (a1.co_emp=a3.co_emp and a1.co_cli=a3.co_cli)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a4 on (a1.co_emp=a4.co_emp and a1.co_loc=a4.co_loc and a1.co_tipdoc=a4.co_tipdoc and a1.co_doc=a4.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a5 on (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoc=a5.co_tipdoc)";
                strSQL+=" WHERE a1.co_tipdoc in (2, 4, 32, 38, 57) and a1.co_motdoc=5 and a2.st_reg NOT IN ('E','I') and a1.st_reg NOT IN ('E','I')";                
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                if (dtpFecDes.isFecha() && dtpFecHas.isFecha())
                {
                    strSQL+=" AND (a1.fe_doc>='" + objUti.formatearFecha(dtpFecDes.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    strSQL+=" AND a1.fe_doc<='" + objUti.formatearFecha(dtpFecHas.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') ";
                }
                
                if (txtCodTipDoc.getText().length()>0)
                    strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                
                if (txtCodCli.getText().length()>0)
                    strSQL+=" AND a3.co_cli=" + txtCodCli.getText();
                
                strSQL+=" )"; 
                strSQL+=") AS b1";
                
                System.out.println("El Query del count(*) opc4: " +strSQL);
                
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                
                System.out.println("El total de registros es opc4: " +intNumTotReg);
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT * ";
                strSQL+=" FROM (";
                strSQL+=" (";
                strSQL+=" SELECT a6.tx_descor as DES_DOC, a4.tx_tipide as TIP_RUC, a4.tx_ide AS RUC_CED, a4.tx_nom as NOM_CLI, a2.fe_doc as FEC_REG, a2.fe_doc as FEC_EMI, ";
                strSQL+=" CASE a4.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI, REPLACE(a5.tx_secdoc,'-','') as SER_DOC_CLI, ";
                strSQL+=" LPAD((REPLACE(a5.tx_numped,'-','')),7,'0') as NUM_FAC_CLI, a5.tx_numAutSRI as NUM_AUT_CLI, '01' as TIP_COM, ";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub),2) when '0.00' then '0' end as BAS_IMP1, '2' as Tip1, ";
                strSQL+=" CASE a5.nd_poriva when '0.00' then ''||round(abs(a5.nd_sub),2) when '12.00' then '0' end as BAS_IMP2,";
                strSQL+=" CASE a5.nd_poriva when '12.00' then ''||round(abs(a5.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, '0' as Ice1, '0' as Ice2, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12,2) when '70' then '0' when '100' then '0' end AS BAS_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '30' then '1' end AS COD_TIP30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12*0.30,2) when '70' then '0' when '100' then '0' end AS VAL_IVA30, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12,2) when '100' then round(abs(a5.nd_sub)*0.12,2) end AS BAS_IVA70_100, ";
                strSQL+=" CASE a3.nd_porret when '70' then '2' when '100' then '3' end AS COD_TIP70_100, ";
                strSQL+=" CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '2' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12*0.70,2) when '100' then round(abs(a5.nd_sub)*0.12*1,2) end AS VAL_IVA70_100, ";
                strSQL+=" a5.tx_feccad as FEC_CAD_CLI, a1.tx_codsri as COD_SRI, ''||round(abs(a1.nd_abo),2) as VAL_RET, round(abs(a5.nd_sub),2) as BAS_IMP_RET, a2.fe_doc as FEC_REG, LPAD(a2.ne_numdoc1,7,'0') as NUM_RET, '' as NUM_SER_RET, '' as NUM_AUT_RET ";
                strSQL+=" , a5.co_emp, a5.co_loc, a5.co_tipdoc as TIP_DOC, a5.co_doc as COD_DOC, a5.ne_numdoc as NUM_DOC, round(abs(a5.nd_sub),2) as SUB_TOT, round(a5.nd_valiva,2) as IVA, round(a5.nd_tot,2) as TOTAL, a5.tx_secdoc as NUM_SEC, a1.co_tipdoc as TIPDOC1, a7.tx_descor as DESCOR2, a1.co_doc as CODDOC1, a1.co_reg as CODREG1 ";
                strSQL+=" FROM  tbm_detpag as a1";
                strSQL+=" INNER JOIN tbm_cabpag as a2 on (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a3 on (a1.co_emp=a3.co_emp AND a1.co_locpag=a3.co_loc AND a1.co_tipdocpag=a3.co_tipdoc AND a1.co_docpag=a3.co_doc AND a1.co_regpag=a3.co_reg)";
                strSQL+=" INNER JOIN tbm_cli as a4 on (a2.co_emp=a4.co_emp AND a2.co_cli=a4.co_cli)";
                strSQL+=" INNER JOIN tbm_cabmovinv as a5 on (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc AND a3.co_doc=a5.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a6 on (a5.co_emp=a6.co_emp and a5.co_loc=a6.co_loc and a5.co_tipdoc=a6.co_tipdoc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a7 on (a2.co_emp=a7.co_emp and a2.co_loc=a7.co_loc and a2.co_tipdoc=a7.co_tipdoc)";
                strSQL+=" WHERE a5.co_tipdoc in(2, 4, 32, 38, 57) and a1.co_tipdoc IN (33) and a2.st_reg NOT IN ('I') and a5.st_reg NOT IN ('E','I') AND (a5.tx_secdoc not like '0%' OR a5.tx_secdoc is null)";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=strAuxTmp;
                strSQL+=" ORDER BY a2.fe_doc, a2.ne_numdoc1";
                strSQL+=" ) ";
                
                strSQL+=" UNION ALL ";
                
                strSQL+=" (";
                strSQL+=" SELECT a5.tx_descor as DES_DOC, a3.tx_tipide as TIP_RUC,  a3.tx_ide as RUC_CED, a3.tx_nom as NOM_CLI, a1.fe_doc as FEC_REG, ";
                strSQL+=" a1.fe_doc as FEC_EMI, CASE a3.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI,";
                strSQL+=" a1.tx_secdoc as SER_DOC_CLI, a1.tx_numped as NUM_FAC_CLI, a1.tx_numautsri as NUM_AUT_CLI, '01' as TIP_COM,";
                strSQL+=" CASE a1.nd_poriva when null then '0' end as BAS_IMP1, '0' as Tip1, CASE a4.nd_porret when '0.00' then ''||(ROUND(a1.nd_sub,2)) end as BAS_IMP2,";
                strSQL+=" CASE a1.nd_poriva when '12.00' then ''||round(abs(a1.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, ";
                strSQL+=" '0' as Ice1, '0' as Ice2, null as BAS_IVA30, null AS COD_TIP30, null as VAL_IVA30, null as BAS_IVA70_100, null AS COD_TIP70_100, ";
                strSQL+=" null as VAL_IVA70_100, a1.tx_feccad as FEC_CAD_CLI, null as COD_SRI, null as VAL_RET, null as BAS_IMP_RET, a1.fe_doc as FEC_REG, ";
                strSQL+=" null as NUM_RET, null AS NUM_SER_RET, null as NUM_AUT_RET, a1.co_emp, a1.co_loc, a1.co_tipdoc as TIP_DOC,  a1.co_doc as COD_DOC, ";
                strSQL+=" a1.ne_numdoc as NUM_DOC, round(a1.nd_sub,2) as SUB_TOT, round(a1.nd_valiva,2) as IVA, round(a1.nd_tot,2) as TOTAL, null as NUM_SEC, null as TIPDOC1, a5.tx_descor as DESCOR2, null as CODDOC1, null as CODREG1 ";
                strSQL+=" FROM  tbm_cabmovinv AS a1 ";
                strSQL+=" LEFT OUTER JOIN tbm_motdoc as a2 on (a1.co_emp = a2.co_emp and a1.co_motdoc = a2.co_mot)";
                strSQL+=" INNER JOIN tbm_cli as a3 on (a1.co_emp=a3.co_emp and a1.co_cli=a3.co_cli)";
                strSQL+=" INNER JOIN tbm_pagmovinv as a4 on (a1.co_emp=a4.co_emp and a1.co_loc=a4.co_loc and a1.co_tipdoc=a4.co_tipdoc and a1.co_doc=a4.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a5 on (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoc=a5.co_tipdoc)";
                strSQL+=" WHERE a1.co_tipdoc in (2, 4, 32, 38, 57) and a1.co_motdoc=5 and a2.st_reg NOT IN ('E','I') and a1.st_reg NOT IN ('E','I')";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                if (dtpFecDes.isFecha() && dtpFecHas.isFecha())
                {
                    strSQL+=" AND (a1.fe_doc>='" + objUti.formatearFecha(dtpFecDes.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    strSQL+=" AND a1.fe_doc<='" + objUti.formatearFecha(dtpFecHas.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') ";
                }
                
                if (txtCodTipDoc.getText().length()>0)
                    strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                
                if (txtCodCli.getText().length()>0)
                    strSQL+=" AND a3.co_cli=" + txtCodCli.getText();
                
                strSQL+=" )"; 
                strSQL+=") AS b1";
                
                System.out.println("Query de Retenciones Hechas opc.4 es: " +strSQL);
                
       /////////////////////////////////////////////////////////////////////////////////////////  
                ///variables para esta seccion///
                String numfacdes = "";
                String numfachas = "";
                int varfacdes = 0;
                int varfachas = 0;
                
                stmB=con.createStatement();
                strSQLB="";
                strSQLB+=" SELECT MIN(a2.ne_numdoc1) as FacDes, MAX(a2.ne_numdoc1) as FacHas";
                strSQLB+=" FROM  tbm_detpag as a1";
                strSQLB+=" INNER JOIN tbm_cabpag as a2 on (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)";
                strSQLB+=" INNER JOIN tbm_pagmovinv as a3 on (a1.co_emp=a3.co_emp AND a1.co_locpag=a3.co_loc AND a1.co_tipdocpag=a3.co_tipdoc AND a1.co_docpag=a3.co_doc AND a1.co_regpag=a3.co_reg)";
                strSQLB+=" INNER JOIN tbm_cli as a4 on (a2.co_emp=a4.co_emp AND a2.co_cli=a4.co_cli)";
                strSQLB+=" INNER JOIN tbm_cabmovinv as a5 on (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc AND a3.co_doc=a5.co_doc)";
                strSQLB+=" WHERE a3.co_tipdoc in(2,38,57) and a1.co_tipdoc=33  and a2.st_reg NOT IN ('I')";
                strSQLB+=" AND a1.co_emp=" + intCodEmp;
                strSQLB+=" AND a1.co_loc=" + intCodLoc;
                strSQLB+=strAuxTmp;                
                System.out.println("Query para verificar Retenciones Hechas DESDE y HASTA: " +strSQLB);
                rstB=stmB.executeQuery(strSQLB);
                
                if(rstB.next())
                {
                    numfacdes = rstB.getString("FacDes");
                    varfacdes = Integer.parseInt(numfacdes);
                    System.out.println("El Inicio NumFacDes (varfacdes)es: " + varfacdes);
                    numfachas = rstB.getString("FacHas");
                    varfachas = Integer.parseInt(numfachas);
                    System.out.println("El Fin NumFacHas (varfachas)es: " + varfachas);
                }
       /////////////////////////////////////////////////////////////////////////////////////////

/////////////// NUEVA FORMA PARA EL REPORTE DE RETENCIONES HECHAS//////////////////////////
//SELECT a4.tx_tipide as Tip_Ide, a4.tx_ide AS RUC_CED, a2.fe_doc as FEC_REG, a2.fe_doc as FEC_EMI, 
//CASE a4.tx_tipide when 'R' then '01' when 'C' then '03'  end as  TIP_CLI, a5.tx_secdoc as SEC_DOC_CLI, 
//LPAD(a5.tx_numped,7,'0') as NUM_FAC_CLI, a5.tx_numAutSRI as NUM_AUT_CLI, 
//CASE a5.nd_poriva when '12.00' then round(abs(a5.nd_sub),2) when '0.00' then '0' end as BAS_IMP1, '2', 
//CASE a5.nd_poriva when '0.00' then round(abs(a5.nd_sub),2) when '12.00' then '0' end as BAS_IMP2,
//CASE a5.nd_poriva when '12.00' then round(abs(a5.nd_sub)*0.12,2) when '0' then '0' end as VAL_IVA, '0' as Ice1, '0' as Ice2,
//CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12,2) when '70' then '0' when '100' then '0' end AS BAS_IVA30,
//CASE a3.nd_porret when '30' then '1' end AS COD_TIP30,
//CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '5' then '0' when '8' then '0' when '30' then round(abs(a5.nd_sub)*0.12*0.30,2) when '70' then '0' when '100' then '0' end AS VAL_IVA30,
//CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12,2) when '100' then round(abs(a5.nd_sub)*0.12,2) end AS BAS_IVA70_100,
//CASE a3.nd_porret when '70' then '2' when '100' then '3' end AS COD_TIP70_100,
//CASE a3.nd_porret when '0.1'  then '0' when '1' then '0' when '5' then '0' when '8' then '0' when '30' then '0' when '70' then round(abs(a5.nd_sub)*0.12*0.70,2) when '100' then round(abs(a5.nd_sub)*0.12*1,2) end AS VAL_IVA70_100, 
// a5.tx_feccad as FecCad_Cli, a5.tx_codsri, round(abs(a1.nd_abo),2) as ValRet, round(abs(a5.nd_sub),2) as BasImpRet, a2.fe_doc as FecReg,
//'001-001' as sertuval, LPAD(a2.ne_numdoc1,7,'0') as numRetTuval, '0123456789' as AutTuval
//, a5.co_emp, a5.co_loc, a5.co_tipdoc as TIP_DOC, a5.co_doc as COD_DOC, a5.ne_numdoc as nenumdoc, a5.tx_secdoc as NUM_SEC, a1.co_tipdoc as TIPDOC1, a1.co_doc as CODDOC1, a1.co_reg as CODREG1                 
//FROM  tbm_detpag as a1 INNER JOIN tbm_cabpag as a2 on (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc) INNER JOIN tbm_pagmovinv as a3 on (a1.co_emp=a3.co_emp AND a1.co_locpag=a3.co_loc AND a1.co_tipdocpag=a3.co_tipdoc AND a1.co_docpag=a3.co_doc AND a1.co_regpag=a3.co_reg) INNER JOIN tbm_cli as a4 on (a2.co_emp=a4.co_emp AND a2.co_cli=a4.co_cli) INNER JOIN tbm_cabmovinv as a5 on (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc AND a3.co_doc=a5.co_doc) 
//WHERE a3.co_tipdoc in(2,38) and a1.co_tipdoc=33  and a2.st_reg NOT IN ('I') AND a1.co_emp=1 AND a1.co_loc=1 
//ORDER BY a2.fe_doc, a2.ne_numdoc1                
//////////////////////////////
                
                //Sentencia que ejecuta el SQL
                rst=stm.executeQuery(strSQL);
                ///rstB=stm.executeQuery(strSQLB);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);

                for (int x=1; rstA.next(); x++)                
                ///while(rstA.next())
                {                  
                     
                        for (int y=0; rst.next(); y++)
                        ///while(rst.next())
                        {
                                    vecReg=new Vector();                                                                        
                                    vecReg.add(INT_TBL_DAT_LIN,"");
                                    vecReg.add(INT_TBL_DAT_FEC_REG,rst.getString("FEC_REG"));                                    
                                    vecReg.add(INT_TBL_DAT_DES_DOC,rst.getString("DES_DOC"));
                                    vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("NUM_DOC"));
                                    vecReg.add(INT_TBL_DAT_SUB_TOT,rst.getString("SUB_TOT"));
                                    vecReg.add(INT_TBL_DAT_IVA_VAL,rst.getString("IVA"));
                                    vecReg.add(INT_TBL_DAT_VAL_TOT,rst.getString("TOTAL"));                                    
                                    vecReg.add(INT_TBL_DAT_TIP_RUC,rst.getString("TIP_RUC"));
                                    vecReg.add(INT_TBL_DAT_RUC_CED,rst.getString("RUC_CED"));
                                    vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("NOM_CLI"));
                                    ///vecReg.add(INT_TBL_DAT_FEC_REG,rst.getString("FEC_REG"));
                                    vecReg.add(INT_TBL_DAT_FEC_EMI,rst.getString("FEC_EMI"));
                                    vecReg.add(INT_TBL_DAT_SER_DOC,rst.getString("SER_DOC_CLI"));
                                    vecReg.add(INT_TBL_DAT_NUM_FAC,rst.getString("NUM_FAC_CLI"));
                                    vecReg.add(INT_TBL_DAT_NUM_AUT,rst.getString("NUM_AUT_CLI"));
                                    vecReg.add(INT_TBL_DAT_TIP_COM,rst.getString("TIP_COM"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_CON,rst.getString("BAS_IMP1"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_SIN,rst.getString("BAS_IMP2"));
                                    vecReg.add(INT_TBL_DAT_VAL_IVA,rst.getString("VAL_IVA"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_I30,rst.getString("BAS_IVA30"));
                                    vecReg.add(INT_TBL_DAT_VAL_BAS_I30,rst.getString("VAL_IVA30"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_I70,rst.getString("BAS_IVA70_100"));
                                    vecReg.add(INT_TBL_DAT_VAL_BAS_I70,rst.getString("VAL_IVA70_100"));
                                    vecReg.add(INT_TBL_DAT_FEC_CAD,rst.getString("FEC_CAD_CLI"));
                                    vecReg.add(INT_TBL_DAT_COD_RET,rst.getString("COD_SRI"));
                                    vecReg.add(INT_TBL_DAT_VAL_RET,rst.getString("VAL_RET"));
                                    vecReg.add(INT_TBL_DAT_BAS_IMP_RET,rst.getString("BAS_IMP_RET"));
                                    vecReg.add(INT_TBL_DAT_FEC_RET,rst.getString("FEC_REG"));
                                    vecReg.add(INT_TBL_DAT_SER_RET,rstA.getString("NumSerFac"));
                                    vecReg.add(INT_TBL_DAT_NUM_RET,rst.getString("NUM_RET"));
                                    vecReg.add(INT_TBL_DAT_AUT_RET,rstA.getString("tx_numautsri"));
                                    vecReg.add(INT_TBL_DAT_TIP_DOC,rst.getString("TIP_DOC"));
                                    vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("COD_DOC"));
                                    vecReg.add(INT_TBL_DAT_TIPDOC1,rst.getString("TIPDOC1"));
                                    vecReg.add(INT_TBL_DAT_CODDOC1,rst.getString("CODDOC1"));
                                    vecReg.add(INT_TBL_DAT_CODREG1,rst.getString("CODREG1"));
                                    vecReg.add(INT_TBL_DAT_DESCOR2,rst.getString("DESCOR2"));
                                    vecDat.add(vecReg);
                                    i++;
                                    pgrSis.setValue(i);                      
                        }///fin del if rst///
                }///fin del if rstA
              
                rst.close();
                stm.close();
                rstA.close();
                stmA.close();
                con.close();
                rst=null;
                stm=null;
                rstA=null;
                stmA=null;
                con=null;
                rstB=null;
                stmB=null;
                con=null;

                
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                if (intNumTotReg==tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
                pgrSis.setValue(0);
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
    //final opcion 4 todos los registros //
   
   /**
     * Esta función permite actualizar los registros del detalle.
     * @return true: Si se pudo actualizar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarDet()
    {
        int intCodEmp, intCodLoc, intNumFil, i;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                stmA=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                intNumFil=objTblMod.getRowCountTrue();
                for (i=0; i<intNumFil;i++)
                {
                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M"))
                    {
                        ///Armar la sentencia SQL.para la Tabla tbm_cabmovinv///
                        strSQL="";
                        strSQL+="UPDATE tbm_CabMovInv ";
                        strSQL+=" SET tx_secdoc=" + "'"+(objTblMod.getValueAt(i,INT_TBL_DAT_SER_DOC)+"'");
                        strSQL+=", tx_numped=" + "'"+(objTblMod.getValueAt(i,INT_TBL_DAT_NUM_FAC)+"'");
                        strSQL+=", tx_numautsri=" + "'"+(objTblMod.getValueAt(i,INT_TBL_DAT_NUM_AUT)+"'");
                        strSQL+=", tx_feccad=" +    "'"+(objTblMod.getValueAt(i,INT_TBL_DAT_FEC_CAD)+"'");
                        ///strSQL+=", tx_codsri= " +    (objTblMod.getValueAt(i,INT_TBL_DAT_COD_RET)==null?objTblMod.getValueAt(i,INT_TBL_DAT_COD_RET):"'"+objTblMod.getValueAt(i,INT_TBL_DAT_COD_RET)+"'");
                        strSQL+=" WHERE co_emp=" + intCodEmp;
                        strSQL+=" AND co_loc=" + intCodLoc;
                        strSQL+=" AND co_tipDoc=" + (objTblMod.getValueAt(i,INT_TBL_DAT_TIP_DOC));
                        strSQL+=" AND co_doc=" + (objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC));
                        System.out.println("Query de UPDATE tbm_cabmovinv para el S.R.I. es: " +strSQL);
                        stm.executeUpdate(strSQL);
                        
                        ///Armar la sentencia SQL.para la Tabla tbm_detpag///
                        strSQLA="";
                        strSQLA+="UPDATE tbm_DetPag ";
                        strSQLA+=" SET tx_codsri=" + ((objTblMod.getValueAt(i,INT_TBL_DAT_COD_RET)==null)?null:objTblMod.getValueAt(i,INT_TBL_DAT_COD_RET).toString());
                        strSQLA+=" WHERE co_emp=" + intCodEmp;
                        strSQLA+=" AND co_loc=" + intCodLoc;
                        strSQLA+=" AND co_tipDoc=" + (objTblMod.getValueAt(i,INT_TBL_DAT_TIPDOC1));
                        strSQLA+=" AND co_doc=" + (objTblMod.getValueAt(i,INT_TBL_DAT_CODDOC1));
                        strSQLA+=" AND co_reg=" + (objTblMod.getValueAt(i,INT_TBL_DAT_CODREG1));
                        System.out.println("Query de UPDATE tbm_detpag para el S.R.I. es: " +strSQLA);
                        stmA.executeUpdate(strSQLA);
                    }
                }
                stm.close();
                stmA.close();
                con.close();
                stm=null;
                stmA=null;
                con=null;
                datFecAux=null;
                //Inicializo el estado de las filas.
                objTblMod.initRowsState();
                objTblMod.setDataModelChanged(false);
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
    
    
    private boolean abrirFrm()
    {
        boolean blnRes=true;
        try
        {
            if (!((tblDat.getSelectedColumn()==-1) || (tblDat.getSelectedRow()==-1)))
            {
                strAux="Contabilidad.ZafCon01.ZafCon01";
                objParSis.setNombreMenu("Plan de cuentas...");
                if (!strAux.equals(""))
                    invocarClase(strAux);
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
            //Obtener el constructor de la clase que se va a invocar.
            Class objVen=Class.forName(clase);
            Class objCla[]=new Class[2];
            objCla[0]=objParSis.getClass();
            objCla[1]=new Integer(0).getClass();
            java.lang.reflect.Constructor objCon=objVen.getConstructor(objCla);
            //Inicializar el constructor que se obtuvo.
            Object objObj[]=new Object[2];
            objObj[0]=objParSis;
//            objObj[1]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_SIS).toString());
            javax.swing.JInternalFrame ifrVen;
            ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
            this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
            ifrVen.show();
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
            strSQL+=" FROM tbm_cabTipDoc AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
            strSQL+=" AND a1.co_tipdoc IN (2,38,57)";
            strSQL+=" ORDER BY a1.co_tipdoc";
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
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
            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
            strSQL+=" FROM tbm_cli AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" ORDER BY a1.tx_nom";
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
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
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
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
                case 1: //Búsqueda directa por "Código".
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
                case 2: //Búsqueda directa por "Descripción larga".
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
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
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
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_DES_DOC:
                    strMsg="Nombre del Documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Numero del Documento";
                    break;
                case INT_TBL_DAT_SUB_TOT:
                    strMsg="Valor del SubTotal del Doxumento";
                    break;
                case INT_TBL_DAT_IVA_VAL:
                    strMsg="Valor del Iva del Documento";
                    break;
                case INT_TBL_DAT_VAL_TOT:
                    strMsg="Valor Total del Documento";
                    break;                    
                case INT_TBL_DAT_TIP_RUC:
                    strMsg="Tipo de Documento Ruc / Cedula";
                    break;
                case INT_TBL_DAT_RUC_CED:
                    strMsg="Numero de Ruc / Cedula";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del Cliente / Proveedor";
                    break;
                case INT_TBL_DAT_FEC_REG:
                    strMsg="Fecha de Registro del Documento";
                    break;
                case INT_TBL_DAT_FEC_EMI:
                    strMsg="Fecha de Emision del Documento";
                    break;
                case INT_TBL_DAT_SER_DOC:
                    strMsg="Numero de Serie del Doc. Cliente/Proveedor";
                    break;
                case INT_TBL_DAT_NUM_FAC:
                    strMsg="Numero de Secuencia del Doc. Cliente/Proveedor";
                    break;
                case INT_TBL_DAT_NUM_AUT:
                    strMsg="Numero de Autorizacion SRI del Doc. Cliente/Proveedor";
                    break;
                case INT_TBL_DAT_TIP_COM:
                    strMsg="Tipo de Comprobante";
                    break;
                case INT_TBL_DAT_BAS_IMP_CON:
                    strMsg="Base Imponible que se carga con IVA";
                    break;
                case INT_TBL_DAT_BAS_IMP_SIN:
                    strMsg="Base Imponible que no lleva IVA";
                    break;
                case INT_TBL_DAT_VAL_IVA:
                    strMsg="Valor neto del IVA";
                    break;
                case INT_TBL_DAT_BAS_IMP_I30:
                    strMsg="Base Imponible cuando existe IVA del 30%";
                    break;
                case INT_TBL_DAT_VAL_BAS_I30:
                    strMsg="Valor Neto del Iva cuando se carga el 30%";
                    break;
                case INT_TBL_DAT_BAS_IMP_I70:
                    strMsg="Base Imponible cuando existe IVA del 70% o 100%";
                    break;
                case INT_TBL_DAT_VAL_BAS_I70:
                    strMsg="Valor neto del IVA cuando se carga el 70% o 100%";
                    break;
                case INT_TBL_DAT_FEC_CAD:
                    strMsg="Fecha de Caducidad del Doc. Cliente/Proveedor";
                    break;
                case INT_TBL_DAT_COD_RET:
                    strMsg="Codigo de Retencion para el SRI";
                    break;
                case INT_TBL_DAT_VAL_RET:
                    strMsg="Valor neto de la Retencion";
                    break;
                case INT_TBL_DAT_BAS_IMP_RET:
                    strMsg="Base Imponible Neto del Doc.";
                    break;
                case INT_TBL_DAT_FEC_RET:
                    strMsg="Fecha de la Retencion";
                    break;
                case INT_TBL_DAT_SER_RET:
                    strMsg="Numero de Serie de la Retencion Hecha";
                    break;
                case INT_TBL_DAT_NUM_RET:
                    strMsg="Numero de Secuencia de la Retencion Hecha";
                    break;
                case INT_TBL_DAT_AUT_RET:
                    strMsg="Numero de Autorizacion SRI de la Retencion Hecha";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}


////PARA PRUEBA CON RETENCIONES HECHAS///
//        Contabilidad.ZafCon20_A.ZafCon20_A obj = new Contabilidad.ZafCon20_A.ZafCon20_A(objParSis);        
//        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
//        obj.show();