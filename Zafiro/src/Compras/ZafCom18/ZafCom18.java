/*
 * ZafCom18.java
 *
 * Created on 05 de enero de 2006, 10:10 PM
 */
package Compras.ZafCom18;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import java.math.BigDecimal;

/**
 *
 * @author  Eddye Lino
 */
public class ZafCom18 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_MAE=1;                     //Código maestro del item.
    static final int INT_TBL_DAT_COD_SIS=2;                     //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_ALT=3;                     //Código del item (Alterno).
    static final int INT_TBL_DAT_COD_ALT_2=4;                   //Código del item (Alterno 2).
    static final int INT_TBL_DAT_NOM_ITM=5;                     //Nombre del item.
    static final int INT_TBL_DAT_CHK_SER=6;                     //Nombre del item.
    static final int INT_TBL_DAT_COD_UNI=7;                     //Código de la unidad de medida (Sistema).
    static final int INT_TBL_DAT_DEC_UNI=8;                     //Descripción corta de la unidad de medida.
    static final int INT_TBL_DAT_BUT_UNI=9;                     //Botón de consulta.
    static final int INT_TBL_DAT_STK_CON=10;                    //Stock consolidado.
    static final int INT_TBL_DAT_PRE_VTA1=11;                   //Precio de venta 1.
    static final int INT_TBL_DAT_MAR_UTI=12;                    //Margen de utilidad.
    static final int INT_TBL_DAT_PES_KGR=13;                    //Peso en Kilogramos.
    static final int INT_TBL_DAT_CAN_MAX_VEN=14;                //Cantidad máxima para la venta.
    static final int INT_TBL_DAT_COS_UNI=15;                    //Costo unitario.
    static final int INT_TBL_DAT_FAC_COS_UNI=16;                //Factor para el costo unitario.
    static final int INT_TBL_DAT_COS_UNI_FAC=17;                //Costo unitario con factor.
    static final int INT_TBL_DAT_MAR_UTI_PRE_VTA=18;            //Margen de utilidad sobre el costo unitario.
    static final int INT_TBL_DAT_BUT_HIS=19;                    //Botón "Histórico".
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafHisTblBasDat objHisTblBasDat;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblDer;                 //Render: Presentar JLabel en JTable (Derecha).
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtPre;                 //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtMarUti;              //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtPesKgr;              //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtCanMaxVen;           //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtFacCosUni;           //Editor: JTextField en celda.
    private ZafTblCelEdiButDlg objTblCelEdiButDlg;              //Editor: JButton en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoUniMed;        //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoUniMed;        //Editor: JTextField de consulta en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafVenCon vcoItm;                                   //Ventana de consulta "Item".
    private ZafVenCon vcoUniMed;                                //Ventana de consulta "Unidad de medida".
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                    //true: Continua la ejecución del hilo.
    private String strCodAlt, strNomItm;                       //Contenido del campo al obtener el foco.
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
    private ZafPerUsr objPerUsr;                               //Objeto que almacena el perfil del usuario.
    private ZafCom18_01 objCom18_01;
   
    /** Crea una nueva instancia de la clase ZafCom18. */
    public ZafCom18(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
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
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAlt = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panNomCli = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        panCodAltItmTer = new javax.swing.JPanel();
        lblCodAltItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        chkMosItmBie = new javax.swing.JCheckBox();
        chkMosItmSer = new javax.swing.JCheckBox();
        chkSolStk = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butPreVen = new javax.swing.JButton();
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
        setTitle("Título de la ventana");
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
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 4, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(4, 24, 400, 20);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Beneficiario");
        panFil.add(lblItm);
        lblItm.setBounds(24, 44, 120, 20);
        panFil.add(txtCodItm);
        txtCodItm.setBounds(88, 44, 56, 20);

        txtCodAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltActionPerformed(evt);
            }
        });
        txtCodAlt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltFocusLost(evt);
            }
        });
        panFil.add(txtCodAlt);
        txtCodAlt.setBounds(144, 44, 92, 20);

        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });
        txtNomItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomItmFocusLost(evt);
            }
        });
        panFil.add(txtNomItm);
        txtNomItm.setBounds(236, 44, 424, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(660, 44, 20, 20);

        panNomCli.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panNomCli.setLayout(null);

        lblCodAltDes.setText("Desde:");
        panNomCli.add(lblCodAltDes);
        lblCodAltDes.setBounds(12, 20, 48, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        panNomCli.add(txtCodAltDes);
        txtCodAltDes.setBounds(60, 20, 100, 20);

        lblCodAltHas.setText("Hasta:");
        panNomCli.add(lblCodAltHas);
        lblCodAltHas.setBounds(168, 20, 48, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        panNomCli.add(txtCodAltHas);
        txtCodAltHas.setBounds(216, 20, 100, 20);

        panFil.add(panNomCli);
        panNomCli.setBounds(24, 64, 328, 52);

        panCodAltItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panCodAltItmTer.setLayout(null);

        lblCodAltItmTer.setText("Terminan con:");
        panCodAltItmTer.add(lblCodAltItmTer);
        lblCodAltItmTer.setBounds(12, 20, 100, 20);

        txtCodAltItmTer.setToolTipText("<HTML>\nSi desea consultar más de un terminal separe cada terminal por medio de una coma.\n<BR><FONT COLOR=\"blue\">Por ejemplo:</FONT> S,L,T\n</HTML>");
        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panCodAltItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(112, 20, 204, 20);

        panFil.add(panCodAltItmTer);
        panCodAltItmTer.setBounds(356, 64, 328, 52);

        chkMosItmBie.setSelected(true);
        chkMosItmBie.setText("Mostrar los bienes");
        chkMosItmBie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosItmBieActionPerformed(evt);
            }
        });
        panFil.add(chkMosItmBie);
        chkMosItmBie.setBounds(24, 116, 324, 20);

        chkMosItmSer.setSelected(true);
        chkMosItmSer.setText("Mostrar los servicios");
        chkMosItmSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosItmSerActionPerformed(evt);
            }
        });
        panFil.add(chkMosItmSer);
        chkMosItmSer.setBounds(24, 136, 324, 20);

        chkSolStk.setText("Mostrar sólo los items con stock");
        chkSolStk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolStkActionPerformed(evt);
            }
        });
        panFil.add(chkSolStk);
        chkSolStk.setBounds(24, 156, 324, 20);

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

        butGua.setText("Guardar");
        butGua.setToolTipText("Guarda los cambios realizados.");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butPreVen.setText("Pre.Vta.1");
        butPreVen.setToolTipText("Asigna el valor de \"Cos.Uni.Fac.\" a \"Pre.Vta.1.\" para las filas seleccionadas.");
        butPreVen.setPreferredSize(new java.awt.Dimension(92, 25));
        butPreVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPreVenActionPerformed(evt);
            }
        });
        panBot.add(butPreVen);

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

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
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

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
    }//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if (txtCodAltDes.getText().length()>0) {
            optFil.setSelected(true);
            if (txtCodAltHas.getText().length()==0)
                txtCodAltHas.setText(txtCodAltDes.getText());
        }
    }//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusGained
        txtCodAltHas.selectAll();
    }//GEN-LAST:event_txtCodAltHasFocusGained

    private void txtCodAltHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusLost
        if (txtCodAltHas.getText().length()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltHasFocusLost

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (objTblMod.isDataModelChanged())
        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
                if (actualizarDet())
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                else
                    mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
            }
        }
        else
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
    }//GEN-LAST:event_butGuaActionPerformed

    private void chkSolStkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSolStkActionPerformed
        if (chkSolStk.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkSolStkActionPerformed

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butItmActionPerformed

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(2);
            }
        }
        else
            txtNomItm.setText(strNomItm);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomItmFocusLost

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAlt.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(1);
            }
        }
        else
            txtCodAlt.setText(strCodAlt);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodAltFocusLost

    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
        strCodAlt=txtCodAlt.getText();
        txtCodAlt.selectAll();
    }//GEN-LAST:event_txtCodAltFocusGained

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtCodAlt.transferFocus();
    }//GEN-LAST:event_txtCodAltActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtNomItm.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
            txtCodAltItmTer.setText("");
            chkMosItmBie.setSelected(true);
            chkMosItmSer.setSelected(true);
            chkSolStk.setSelected(false);
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
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void chkMosItmBieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosItmBieActionPerformed
        if (!chkMosItmBie.isSelected())
            optFil.setSelected(true);
}//GEN-LAST:event_chkMosItmBieActionPerformed

    private void chkMosItmSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosItmSerActionPerformed
        if (!chkMosItmSer.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkMosItmSerActionPerformed

    private void butPreVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPreVenActionPerformed
        int intFilSel[], i;
        if (mostrarMsgCon("<HTML>¿Está seguro que desear asignar <FONT COLOR=\"blue\">Cos.Uni.Fac.</FONT> a <FONT COLOR=\"blue\">Pre.Vta.1.</FONT>?<BR>Sólo se asignarán los valores en las filas seleccionadas.")==0)
        {
            intFilSel=tblDat.getSelectedRows();
            for (i=0; i<intFilSel.length; i++)
            {
                if (objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_COS_UNI_FAC))!=0)
                    objTblMod.setValueAt(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_COS_UNI_FAC), intFilSel[i], INT_TBL_DAT_PRE_VTA1);
            }
            //Seleccionar la fila donde se encontró el valor buscado.
            tblDat.setRowSelectionInterval(intFilSel[0], intFilSel[intFilSel.length-1]);
            //Ubicar el foco en la fila seleccionada.
            tblDat.changeSelection(intFilSel[0], INT_TBL_DAT_PRE_VTA1, true, true);
            tblDat.requestFocus();
        }
    }//GEN-LAST:event_butPreVenActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butItm;
    private javax.swing.JButton butPreVen;
    private javax.swing.JCheckBox chkMosItmBie;
    private javax.swing.JCheckBox chkMosItmSer;
    private javax.swing.JCheckBox chkSolStk;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblCodAltItmTer;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCodAltItmTer;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtNomItm;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            objHisTblBasDat=new ZafHisTblBasDat();
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.26");
            lblTit.setText(strAux);
            //Configurar objetos.
            txtCodItm.setVisible(false);
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(745))
                butCon.setVisible(false);
            if (!objPerUsr.isOpcionEnabled(746))
                butGua.setVisible(false);
            if (!objPerUsr.isOpcionEnabled(3827))
                butPreVen.setVisible(false);
            if (!objPerUsr.isOpcionEnabled(747))
                butCer.setVisible(false);
            //Configurar las ZafVenCon.
            configurarVenConItm();
            configurarVenConUniMed();
            //Configurar los JTables.
            configurarTblDat();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(20);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_MAE,"Cód.Mae.");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALT_2,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre");
            vecCab.add(INT_TBL_DAT_CHK_SER,"Servicio");
            vecCab.add(INT_TBL_DAT_COD_UNI,"Cód.Uni.");
            vecCab.add(INT_TBL_DAT_DEC_UNI,"Unidad");
            vecCab.add(INT_TBL_DAT_BUT_UNI,"");
            vecCab.add(INT_TBL_DAT_STK_CON,"Stock");
            vecCab.add(INT_TBL_DAT_PRE_VTA1,"Pre.Vta.1");
            vecCab.add(INT_TBL_DAT_MAR_UTI,"Mar.Uti.");
            vecCab.add(INT_TBL_DAT_PES_KGR,"Peso(Kg)");
            vecCab.add(INT_TBL_DAT_CAN_MAX_VEN,"Can.Máx.Ven.");
            vecCab.add(INT_TBL_DAT_COS_UNI,"Cos.Uni.");
            vecCab.add(INT_TBL_DAT_FAC_COS_UNI,"Fac.Cos.Uni.");
            vecCab.add(INT_TBL_DAT_COS_UNI_FAC,"Cos.Uni.Fac.");
            vecCab.add(INT_TBL_DAT_MAR_UTI_PRE_VTA,"Mar.Uti.Pre.Ven.");
            vecCab.add(INT_TBL_DAT_BUT_HIS,"");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_PRE_VTA1, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_MAR_UTI, ZafTblMod.INT_COL_DBL, new Integer(0), new Integer(100));
            objTblMod.setColumnDataType(INT_TBL_DAT_PES_KGR, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_MAX_VEN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_FAC_COS_UNI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setCellSelectionEnabled(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnu.isClickBorrarContenido())
                    {
                        int intFilSel[], i;
                        intFilSel=tblDat.getSelectedRows();
                        //Al borrar lo que se encuentra en INT_TBL_DAT_DEC_UNI se debe borrar también lo que se encuentra en INT_TBL_DAT_COD_UNI.
                        if (tblDat.isColumnSelected(INT_TBL_DAT_DEC_UNI))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_COD_UNI);
                            }
                        }
                        else if (tblDat.isColumnSelected(INT_TBL_DAT_FAC_COS_UNI))
                        {
                            calcularCosUniFac();
                        }
                        else if (tblDat.isColumnSelected(INT_TBL_DAT_PRE_VTA1))
                        {
                            calcularMarUtiCos();
                        }
                    }
                    else if (objTblPopMnu.isClickPegar())
                    {
                        //Eddye: Hay que mejorar esto porque no está validando correctamente cuando se pega la unidad de medida.
                        int intFilSel[], i;
                        intFilSel=tblDat.getSelectedRows();
                        //Al borrar lo que se encuentra en INT_TBL_DAT_DEC_UNI se debe borrar también lo que se encuentra en INT_TBL_DAT_COD_UNI.
                        if (tblDat.isColumnSelected(INT_TBL_DAT_DEC_UNI))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_COD_UNI);
                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_DEC_UNI);
                            }
                        }
                        else if (tblDat.isColumnSelected(INT_TBL_DAT_FAC_COS_UNI))
                        {
                            calcularCosUniFac();
                        }
                        else if (tblDat.isColumnSelected(INT_TBL_DAT_PRE_VTA1))
                        {
                            calcularMarUtiCos();
                        }
                    }
                }
            });
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_2).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CHK_SER).setPreferredWidth(47);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setPreferredWidth(47);
            tcmAux.getColumn(INT_TBL_DAT_BUT_UNI).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PRE_VTA1).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_MAR_UTI).setPreferredWidth(48);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MAX_VEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FAC_COS_UNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI_FAC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_MAR_UTI_PRE_VTA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS).setPreferredWidth(20);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BUT_UNI).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SIS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_UNI, tblDat);
            if (objParSis.getCodigoUsuario()!=1)
            {
                //2990: Ficha "Reporte": Tabla->Mostrar "Costo unitario".
                if (!objPerUsr.isOpcionEnabled(2990))
                {
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COS_UNI, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FAC_COS_UNI, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COS_UNI_FAC, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_MAR_UTI_PRE_VTA, tblDat);
                    butPreVen.setVisible(false);
                }
            }
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            if (objParSis.getCodigoUsuario()==1)
            {
                vecAux.add("" + INT_TBL_DAT_NOM_ITM);
                vecAux.add("" + INT_TBL_DAT_DEC_UNI);
                vecAux.add("" + INT_TBL_DAT_BUT_UNI);
                vecAux.add("" + INT_TBL_DAT_PRE_VTA1);
                vecAux.add("" + INT_TBL_DAT_MAR_UTI);
                vecAux.add("" + INT_TBL_DAT_PES_KGR);
                vecAux.add("" + INT_TBL_DAT_CAN_MAX_VEN);
                vecAux.add("" + INT_TBL_DAT_FAC_COS_UNI);
                vecAux.add("" + INT_TBL_DAT_BUT_HIS);
            }
            else
            {
                //2960: Ficha "Reporte": Tabla->Modificar "Nombre del item".
                if (objPerUsr.isOpcionEnabled(2960))
                    vecAux.add("" + INT_TBL_DAT_NOM_ITM);
                //2961: Ficha "Reporte": Tabla->Modificar "Unidad de medida".
                if (objPerUsr.isOpcionEnabled(2961))
                {
                    vecAux.add("" + INT_TBL_DAT_DEC_UNI);
                    vecAux.add("" + INT_TBL_DAT_BUT_UNI);
                }
                //2962: Ficha "Reporte": Tabla->Modificar "Precio de venta 1".
                if (objPerUsr.isOpcionEnabled(2962))
                    vecAux.add("" + INT_TBL_DAT_PRE_VTA1);
                //2963: Ficha "Reporte": Tabla->Modificar "Margen de utilidad".
                if (objPerUsr.isOpcionEnabled(2963))
                    vecAux.add("" + INT_TBL_DAT_MAR_UTI);
                //2964: Ficha "Reporte": Tabla->Modificar "Peso (Kg)".
                if (objPerUsr.isOpcionEnabled(2964))
                    vecAux.add("" + INT_TBL_DAT_PES_KGR);
                //2989: Ficha "Reporte": Tabla->Modificar "Cantidad máxima para la venta".
                if (objPerUsr.isOpcionEnabled(2989))
                    vecAux.add("" + INT_TBL_DAT_CAN_MAX_VEN);
                //2990: Ficha "Reporte": Tabla->Mostrar "Costo unitario".
                if (objPerUsr.isOpcionEnabled(2990))
                    vecAux.add("" + INT_TBL_DAT_FAC_COS_UNI);
            }
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_2).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_MAR_UTI)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_MAR_UTI))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else
                    {
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0)
                        {
                            objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else
                        {
                            if (bgdMarUti.compareTo(bgdMarUtiCos)==1)
                            {
                                objTblCelRenLbl.setBackground(java.awt.Color.RED);
                            }
                            else
                            {
                                objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            objTblCelRenLblDer=new ZafTblCelRenLbl();
            objTblCelRenLblDer.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setCellRenderer(objTblCelRenLblDer);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellRenderer(objTblCelRenLblDer);
            objTblCelRenLblDer.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), INT_TBL_DAT_MAR_UTI)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), INT_TBL_DAT_MAR_UTI))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblDer.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else
                    {
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0)
                        {
                            objTblCelRenLblDer.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else
                        {
                            if (bgdMarUti.compareTo(bgdMarUtiCos)==1)
                            {
                                objTblCelRenLblDer.setBackground(java.awt.Color.RED);
                            }
                            else
                            {
                                objTblCelRenLblDer.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_PRE_VTA1).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_MAR_UTI).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MAX_VEN).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_FAC_COS_UNI).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI_FAC).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_MAR_UTI_PRE_VTA).setCellRenderer(objTblCelRenLblNum);
            objTblCelRenLblNum.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), INT_TBL_DAT_MAR_UTI)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), INT_TBL_DAT_MAR_UTI))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else
                    {
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0)
                        {
                            objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else
                        {
                            if (bgdMarUti.compareTo(bgdMarUtiCos)==1)
                            {
                                objTblCelRenLblNum.setBackground(java.awt.Color.RED);
                            }
                            else
                            {
                                objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_UNI).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_SER).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_MAR_UTI)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_MAR_UTI))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else
                    {
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0)
                        {
                            objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else
                        {
                            if (bgdMarUti.compareTo(bgdMarUtiCos)==1)
                            {
                                objTblCelRenChk.setBackground(java.awt.Color.RED);
                            }
                            else
                            {
                                objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_UNI;
            intColTbl[1]=INT_TBL_DAT_DEC_UNI;
            objTblCelEdiTxtVcoUniMed=new ZafTblCelEdiTxtVco(tblDat, vcoUniMed, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellEditor(objTblCelEdiTxtVcoUniMed);
            
            objTblCelEdiButVcoUniMed=new ZafTblCelEdiButVco(tblDat, vcoUniMed, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_UNI).setCellEditor(objTblCelEdiButVcoUniMed);
            intColVen=null;
            intColTbl=null;

            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt=null;
            
            objTblCelEdiTxtPre=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_PRE_VTA1).setCellEditor(objTblCelEdiTxtPre);
            objTblCelEdiTxtPre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (!objParSis.getNombreEmpresa().equals("INDUSTRIAS COSENCO S.A."))
                    {
                        if (objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ALT).toString().toUpperCase().endsWith("L"))
                        {
                            objTblCelEdiTxtPre.setCancelarEdicion(true);
                        }
                        else
                        {
                            switch (objParSis.getCodigoUsuario())
                            {
                                case 1: //admin
                                case 24: //lsensi
                                case 122: //fruiz
                                    break;
                                case 38: //cbetancourt
                                    if (!(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ALT).toString().toUpperCase().endsWith("S") || objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ALT).toString().toUpperCase().endsWith("P")))
                                    {
                                        objTblCelEdiTxtPre.setCancelarEdicion(true);
                                    }
                                    break;
                            }

                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularMarUtiCos();
                }
            });
            
            objTblCelEdiTxtMarUti=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_MAR_UTI).setCellEditor(objTblCelEdiTxtMarUti);
            objTblCelEdiTxtMarUti=null;
            
            objTblCelEdiTxtPesKgr=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setCellEditor(objTblCelEdiTxtPesKgr);
            objTblCelEdiTxtPesKgr=null;
            
            objTblCelEdiTxtCanMaxVen=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MAX_VEN).setCellEditor(objTblCelEdiTxtCanMaxVen);
            objTblCelEdiTxtCanMaxVen=null;
            
            objTblCelEdiTxtFacCosUni=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_FAC_COS_UNI).setCellEditor(objTblCelEdiTxtFacCosUni);
            objTblCelEdiTxtFacCosUni.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularCosUniFac();
                }
            });
            
            objCom18_01=new ZafCom18_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButDlg=new ZafTblCelEdiButDlg(objCom18_01);
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS).setCellEditor(objTblCelEdiButDlg);
            objTblCelEdiButDlg.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    if (intFilSel!=-1)
                    {
                        objCom18_01.setParDlg(objParSis.getCodigoEmpresa(), Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_SIS).toString()));
                        objCom18_01.cargarReg();
                    }
                }
            });
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer que el JTable sea editable.
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
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
        BigDecimal bgdPreVta, bgdCosUni, bgdFacCosUni;
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
                //Obtener la condición.
                strConSQL="";
                if (txtCodItm.getText().length()>0)
                    strConSQL+=" AND a1.co_itm=" + txtCodItm.getText();
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
                    strConSQL+=" AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                if (txtCodAltItmTer.getText().length()>0)
                {
                    strConSQL+=getConSQLAdiCamTer("a1.tx_codAlt", txtCodAltItmTer.getText());
                }
                if (chkMosItmBie.isSelected())
                {
                    if (chkMosItmSer.isSelected())
                        strConSQL+="";
                    else
                        strConSQL+=" AND a1.st_ser='N'";
                }
                else
                {
                    if (chkMosItmSer.isSelected())
                        strConSQL+=" AND a1.st_ser IN ('S', 'T', 'O')";
                    else
                        strConSQL+=" AND a1.st_ser NOT IN ('N', 'S', 'T', 'O')";
                }
                if (chkSolStk.isSelected())
                    strConSQL+=" AND a3.nd_stkAct>0";
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a4.co_reg, a4.tx_desCor, a1.st_ser, a3.nd_stkAct, a1.nd_preVta1, a1.nd_marUti, a1.nd_pesItmKgr";
                //2990: Ficha "Reporte": Tabla->Mostrar "Costo unitario".
                if (objPerUsr.isOpcionEnabled(2990))
                    strSQL+=", a1.nd_cosUni, a1.nd_canMaxVen, a1.nd_facCosUni";
                else
                    strSQL+=", NULL AS nd_cosUni, NULL AS nd_canMaxVen, NULL AS nd_facCosUni";
                strSQL+=" FROM tbm_inv AS a1";
                strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                strSQL+=" LEFT OUTER JOIN (";
                strSQL+=" SELECT b1.co_itmMae, SUM(b2.nd_stkAct) AS nd_stkAct";
                strSQL+=" FROM tbm_equInv AS b1";
                strSQL+=" INNER JOIN tbm_inv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    strSQL+=" WHERE b2.co_emp<>" + objParSis.getCodigoEmpresaGrupo();
                }
                else
                {
                    strSQL+=" WHERE b2.co_emp=" + objParSis.getCodigoEmpresa();
                }
                strSQL+=" GROUP BY b1.co_itmMae";
                strSQL+=" ) AS a3 ON (a2.co_itmMae=a3.co_itmMae)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a4 ON (a1.co_uni=a4.co_reg)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=strConSQL;
                strSQL+=" ORDER BY a1.tx_codAlt";
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
                        vecReg.add(INT_TBL_DAT_COD_MAE,rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_SIS,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_2,rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_CHK_SER,rst.getString("st_ser").equals("N")?null:Boolean.TRUE);
                        vecReg.add(INT_TBL_DAT_COD_UNI,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_DEC_UNI,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_BUT_UNI,null);
                        vecReg.add(INT_TBL_DAT_STK_CON,rst.getString("nd_stkAct"));
                        bgdPreVta=rst.getBigDecimal("nd_preVta1");
                        vecReg.add(INT_TBL_DAT_PRE_VTA1,bgdPreVta);
                        vecReg.add(INT_TBL_DAT_MAR_UTI,rst.getString("nd_marUti"));
                        vecReg.add(INT_TBL_DAT_PES_KGR,rst.getString("nd_pesItmKgr"));
                        vecReg.add(INT_TBL_DAT_CAN_MAX_VEN,rst.getString("nd_canMaxVen"));
                        bgdCosUni=rst.getBigDecimal("nd_cosUni");
                        bgdFacCosUni=rst.getBigDecimal("nd_facCosUni");
                        vecReg.add(INT_TBL_DAT_COS_UNI,bgdCosUni);
                        vecReg.add(INT_TBL_DAT_FAC_COS_UNI,bgdFacCosUni);
                        if (bgdCosUni==null || bgdFacCosUni==null)
                            vecReg.add(INT_TBL_DAT_COS_UNI_FAC,null);
                        else
                            vecReg.add(INT_TBL_DAT_COS_UNI_FAC,objUti.redondearBigDecimal(bgdCosUni.multiply(bgdFacCosUni), objParSis.getDecimalesMostrar()));
                        if (bgdPreVta==null || bgdPreVta.compareTo(BigDecimal.ZERO)==0)
                        {
                            vecReg.add(INT_TBL_DAT_MAR_UTI_PRE_VTA,null);
                        }
                        else
                        {
                            if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0)
                            {
                                vecReg.add(INT_TBL_DAT_MAR_UTI_PRE_VTA,null);
                            }
                            else
                            {
                                bgdPreVta=bgdPreVta.multiply(BigDecimal.valueOf(0.75));
                                vecReg.add(INT_TBL_DAT_MAR_UTI_PRE_VTA,objUti.redondearBigDecimal(((bgdPreVta.subtract(bgdCosUni)).divide(bgdPreVta, objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP)).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesMostrar()));
                            }
                        }
                        vecReg.add(INT_TBL_DAT_BUT_HIS,null);
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
     * Esta función permite actualizar los registros del detalle.
     * @return true: Si se pudo actualizar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarDet()
    {
        int intNumFil, i;
        String strFecUltMod;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                intNumFil=objTblMod.getRowCountTrue();
                for (i=0; i<intNumFil;i++)
                {
                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M"))
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="UPDATE tbm_inv";
                        strSQL+=" SET tx_nomItm=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM),1);
                        strSQL+=", co_uni=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_UNI),2);
                        strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_PRE_VTA1),2);
                        strSQL+=", nd_marUti=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_MAR_UTI),2);
                        strSQL+=", nd_pesItmKgr=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_PES_KGR),2);
                        strSQL+=", nd_canMaxVen=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_MAX_VEN),2);
                        strSQL+=", nd_facCosUni=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_FAC_COS_UNI),2);
                        strSQL+=", fe_ultMod='" + strFecUltMod + "'";
                        strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                        strSQL+=" FROM (";
                        strSQL+=" SELECT a1.co_emp, a2.co_itm";
                        strSQL+=" FROM tbm_inv AS a1";
                        strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                        strSQL+=" WHERE a2.co_itmMae=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_MAE);
                        strSQL+=" ) AS b1";
                        strSQL+=" WHERE tbm_inv.co_emp=b1.co_emp AND tbm_inv.co_itm=b1.co_itm";
                        stm.executeUpdate(strSQL);
                    }
                }
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());
                stm.close();
                con.close();
                stm=null;
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
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("d1.co_itm");
            arlCam.add("d1.tx_codAlt");
            arlCam.add("d1.tx_nomItm");
            arlCam.add("d4.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Alterno");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("340");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_codAlt";
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
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
     * mostrar la "Unidad de medida".
     */
    private boolean configurarVenConUniMed()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_reg");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Siglas");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("400");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_reg, a1.tx_desCor, a1.tx_desLar";
            strSQL+=" FROM tbm_var AS a1";
            strSQL+=" WHERE a1.co_grp=5 AND a1.st_reg='A'";
            strSQL+=" ORDER BY a1.co_reg";
            vcoUniMed=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de unidades de medida", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoUniMed.setConfiguracionColumna(2, javax.swing.JLabel.CENTER);
            vcoUniMed.setCampoBusqueda(1);
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
    private boolean mostrarVenConItm(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(1);
                    vcoItm.setVisible(true);
                    if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAlt.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
                        }
                        else
                        {
                            txtCodAlt.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
                        }
                        else
                        {
                            txtNomItm.setText(strNomItm);
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
     * Esta función obtiene la condición SQL adicional para los campos que "Terminan con".
     * La cadena recibida es separada para formar la condición que se agregará la sentencia SQL.
     * Por ejemplo: 
     * Si strCam="a2.tx_codAlt" y strCad="I, S, L" el resultado sería "AND (a2.tx_codalt LIKE '%I' OR a2.tx_codalt LIKE '%S' OR a2.tx_codalt LIKE '%L')"
     * @param strCam El campo que se utilizará para la condición.
     * @param strCad La cadena que se separará para formar la condición.
     * @return La cadena que contiene la condición SQL .
     */
    private String getConSQLAdiCamTer(String strCam, String strCad)
    {
        byte i;
        String strRes="";
        try
        {
            if (strCad.length()>0)
            {
                java.util.StringTokenizer stkAux=new java.util.StringTokenizer(strCad, ",", false);
                i=0;
                while (stkAux.hasMoreTokens())
                {
                    if (i==0)
                        strRes+=" AND (LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    else
                        strRes+=" OR LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    i++;
                }
                strRes+=")";
            }
        }
        catch (java.util.NoSuchElementException e)
        {
            strRes="";
        }
        return strRes;
    }
    
    /**
     * Esta función calcula el costo unitario con factor de la fila.
     */
    private void calcularCosUniFac()
    {
        int intFilSel[], i;
        BigDecimal bgdCosUni, bgdFacCosUni;
        intFilSel=tblDat.getSelectedRows();
        for (i=0; i<intFilSel.length; i++)
        {
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_COS_UNI)));
            bgdFacCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_FAC_COS_UNI)));
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdCosUni.multiply(bgdFacCosUni), objParSis.getDecimalesMostrar()), intFilSel[i], INT_TBL_DAT_COS_UNI_FAC);
        }
    }
    
    /**
     * Esta función calcula el margen de utilidad sobre el costo de la fila.
     */
    private void calcularMarUtiCos()
    {
        int intFilSel[], i;
        BigDecimal bgdPreVta, bgdCosUni;
        intFilSel=tblDat.getSelectedRows();
        for (i=0; i<intFilSel.length; i++)
        {
            bgdPreVta=(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_PRE_VTA1)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_PRE_VTA1))));
            bgdCosUni=(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_COS_UNI)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_COS_UNI))));
            if (bgdPreVta==null || bgdPreVta.compareTo(BigDecimal.ZERO)==0)
            {
                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_MAR_UTI_PRE_VTA);
            }
            else
            {
                if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0)
                {
                    objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_MAR_UTI_PRE_VTA);
                }
                else
                {
                    bgdPreVta=bgdPreVta.multiply(BigDecimal.valueOf(0.75));
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreVta.subtract(bgdCosUni)).divide(bgdPreVta, objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP)).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesMostrar()), intFilSel[i], INT_TBL_DAT_MAR_UTI_PRE_VTA);
                }
            }
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
                    strMsg="<html><h3 style='text-align:center;'>Colores utilizados en la tabla</h3><table border='1'><tr><td><b>Fondo</b></td><td><b>Fuente</b></td><td><b>Observación</b></td></tr><tr><td style='background-color: #FF0000'>&nbsp;</td><td>&nbsp;</td><td>Indica que el \"Mar.Uti.Cos.\" es menor al \"Mar.Uti.\".</td></tr></table><br></html>";
                    break;
                case INT_TBL_DAT_COD_MAE:
                    strMsg="Código maestro del item";
                    break;
                case INT_TBL_DAT_COD_SIS:
                    strMsg="Código del item (Sistema)";
                    break;
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_ALT_2:
                    strMsg="Código alterno 2 del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_CHK_SER:
                    strMsg="Servicio";
                    break;
                case INT_TBL_DAT_DEC_UNI:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_BUT_UNI:
                    strMsg="Muestra el \"Listado de unidades de medida\"";
                    break;
                case INT_TBL_DAT_STK_CON:
                    strMsg="Stock";
                    break;
                case INT_TBL_DAT_PRE_VTA1:
                    strMsg="Precio de venta 1";
                    break;
                case INT_TBL_DAT_MAR_UTI:
                    strMsg="Margen de utilidad";
                    break;
                case INT_TBL_DAT_PES_KGR:
                    strMsg="Peso(Kg)";
                    break;
                case INT_TBL_DAT_CAN_MAX_VEN:
                    strMsg="Cantidad máxima para la venta";
                    break;
                case INT_TBL_DAT_COS_UNI:
                    strMsg="Costo unitario";
                    break;
                case INT_TBL_DAT_FAC_COS_UNI:
                    strMsg="Factor para el costo unitario";
                    break;
                case INT_TBL_DAT_COS_UNI_FAC:
                    strMsg="Costo unitario con factor";
                    break;
                case INT_TBL_DAT_MAR_UTI_PRE_VTA:
                    strMsg="<HTML>Margen de utilidad sobre el precio de venta <BR><FONT COLOR=\"blue\">(Pre.Vta.1*0.75-Cos.Uni.)/(Pre.Vta.1*0.75).</FONT></HTML>";
                    break;
                case INT_TBL_DAT_BUT_HIS:
                    strMsg="Muestra el \"Histórico de items\"";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}