/*  
 *  ZafCom22.java
 */
package Compras.ZafCom22;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import java.util.Vector;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;

/**
 *
 * @author  Javier Ayapata
 */
public class ZafCom22 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    final int INT_TBL_DAT_LIN=0;                        //Línea
    final int INT_TBL_DAT_COD_SIS=1;                    //Código del item (Sistema).
    final int INT_TBL_DAT_COD_ALT=2;                    //Código del item (Alterno).
    final int INT_TBL_DAT_NOM_ITM=3;                    //Nombre del item.
    final int INT_TBL_DAT_UNI_ITM=4;                    //Nombre del item.
    final int INT_TBL_DAT_DEC_UNI=5;                    //Descripción corta de la unidad de medida.
    final int INT_TBL_DAT_COD_ANT=6;                    //Stock consolidado.
    final int INT_TBL_DAT_NOM_ANT=7;                    //Precio de venta 1.
    final int INT_TBL_DAT_UNI_ANT=8;                    //Precio de venta 1.
    final int INT_TBL_DAT_USR_MOD=9;                    //Precio de venta 1.
    final int INT_TBL_DAT_FEC_MOD=10;                   //fecha de  actualizacion.
    //Tabla 2    
    final int INT_TBL_DAT2_LINEA=0;                     //Línea
    final int INT_TBL_DAT2_COD_SIS=1;                   //Código del item (Sistema).
    final int INT_TBL_DAT2_COD_ALT=2;                   //Código del item (Alterno).
    final int INT_TBL_DAT2_NOM_ITM=3;                   //Nombre del item.
    final int INT_TBL_DAT2_UNIDAD =4;                   // Unidad del item
    final int INT_TBL_DAT2_USR_MOD=5;                   //Precio de venta 1.
    final int INT_TBL_DAT2_FEC_MOD=6;                   //fecha de  actualizacion.
    //Tabla 2    
    final int INT_TBL_DAT3_LINEA=0;                     //Línea
    final int INT_TBL_DAT3_COD_SIS=1;                   //Código del item (Sistema).
    final int INT_TBL_DAT3_COD_ALT=2;                   //Código del item (Alterno).
    final int INT_TBL_DAT3_NOM_ITM=3;                   //Nombre del item.
    final int INT_TBL_DAT3_UNIDAD =4;                   // Unidad del item
    final int INT_TBL_DAT3_USR_MOD=5;                   //Precio de venta 1.
    final int INT_TBL_DAT3_FEC_MOD=6;                   //fecha de  actualizacion.
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod,objTblMod2,objTblMod3;
    private ZafTblMod objTblModDab;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;
    private Connection con;
    private Statement stm,stm2, stm3;
    private ResultSet rst,rst2, rst3;
    private String strSQL, strSQL2 , strAux;
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                             //true: Continua la ejecución del hilo.
    private String strCodAlt, strNomItm;                //Contenido del campo al obtener el foco.
    private ZafTblTot objTblTot;                        //JTable de totales.
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    
    ZafVenCon objVenCon;      
       
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCom22(ZafParSis obj) 
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
  
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panNomCli = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        txtCodAlt = new javax.swing.JTextField();
        optUni = new javax.swing.JRadioButton();
        optMod = new javax.swing.JRadioButton();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panRpt1 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        spnDat1 = new javax.swing.JScrollPane();
        tblDat1 = new javax.swing.JTable();
        panRptDocAbi = new javax.swing.JPanel();
        chkMosStkBod = new javax.swing.JCheckBox();
        panDocAbi = new javax.swing.JPanel();
        spnStkBod = new javax.swing.JScrollPane();
        tblDat2 = new javax.swing.JTable();
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

        panNomCli.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panNomCli.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
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
        panNomCli.add(optTod);
        optTod.setBounds(16, 24, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panNomCli.add(optFil);
        optFil.setBounds(16, 44, 400, 20);

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
        panNomCli.add(txtCodAlt);
        txtCodAlt.setBounds(36, 68, 120, 20);

        buttonGroup1.add(optUni);
        optUni.setSelected(true);
        optUni.setText("Items Unificados");
        optUni.setActionCommand("Items Unificados.");
        panNomCli.add(optUni);
        optUni.setBounds(16, 124, 400, 20);

        buttonGroup1.add(optMod);
        optMod.setText("Items Actualizados.");
        panNomCli.add(optMod);
        optMod.setBounds(16, 144, 400, 20);

        panFil.add(panNomCli);
        panNomCli.setBounds(8, 24, 660, 200);

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

        tabFrm.addTab("Unificados", panRpt);

        panRpt1.setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setLayout(new java.awt.GridLayout(2, 0));

        jPanel2.setLayout(new java.awt.BorderLayout());

        tblDat1.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDat1.setViewportView(tblDat1);

        jPanel2.add(spnDat1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2);

        panRptDocAbi.setLayout(new java.awt.BorderLayout());

        chkMosStkBod.setText("Mostrar los cambios del item ");
        chkMosStkBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosStkBodActionPerformed(evt);
            }
        });
        panRptDocAbi.add(chkMosStkBod, java.awt.BorderLayout.NORTH);

        panDocAbi.setBorder(javax.swing.BorderFactory.createTitledBorder("Stock de cada bodega"));
        panDocAbi.setLayout(new java.awt.BorderLayout());

        tblDat2.setModel(new javax.swing.table.DefaultTableModel(
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
        spnStkBod.setViewportView(tblDat2);

        panDocAbi.add(spnStkBod, java.awt.BorderLayout.CENTER);

        panRptDocAbi.add(panDocAbi, java.awt.BorderLayout.CENTER);

        jPanel1.add(panRptDocAbi);

        panRpt1.add(jPanel1);

        tabFrm.addTab("Actualizados", panRpt1);

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
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void chkMosStkBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosStkBodActionPerformed
        if (chkMosStkBod.isSelected())
            cargarDetRegUni2(); 
        else
            objTblMod3.removeAllRows();
    }//GEN-LAST:event_chkMosStkBodActionPerformed

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        txtCodAlt.setText("");
    }//GEN-LAST:event_optTodActionPerformed
    
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        Configura_ventana_consulta();
        configurarFrm();
        configurarTabla2();
        configurarTabla3();
    }//GEN-LAST:event_formInternalFrameOpened
    
    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if ( txtCodAlt.getText().length()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltFocusLost

    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
        strCodAlt=txtCodAlt.getText();
        txtCodAlt.selectAll();
    }//GEN-LAST:event_txtCodAltFocusGained

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed

    }//GEN-LAST:event_txtCodAltActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
      
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

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkMosStkBod;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optMod;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JRadioButton optUni;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDocAbi;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panRpt1;
    private javax.swing.JPanel panRptDocAbi;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnDat1;
    private javax.swing.JScrollPane spnStkBod;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDat1;
    private javax.swing.JTable tblDat2;
    private javax.swing.JTextField txtCodAlt;
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
            this.setTitle(strAux+" v0.2");
            lblTit.setText(strAux);
            //Configurar objetos.
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(8);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre");
            vecCab.add(INT_TBL_DAT_UNI_ITM,"Unidad");
            vecCab.add(INT_TBL_DAT_DEC_UNI,"Cod.Sis.");
            vecCab.add(INT_TBL_DAT_COD_ANT,"Cod.Alt.");
            vecCab.add(INT_TBL_DAT_NOM_ANT,"Nombre.");
            vecCab.add(INT_TBL_DAT_UNI_ANT,"Unidad.");
            vecCab.add(INT_TBL_DAT_USR_MOD,"Usuario que Cambio");
            vecCab.add(INT_TBL_DAT_FEC_MOD,"Fecha de Cambio");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(52);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_DAT_UNI_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ANT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ANT).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_DAT_UNI_ANT).setPreferredWidth(60); 
            tcmAux.getColumn(INT_TBL_DAT_USR_MOD).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_MOD).setPreferredWidth(70);
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLbl=null;
            //Libero los objetos auxiliares.
            objTblOrd=new ZafTblOrd(tblDat);
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
      
    private boolean configurarTabla2()
    {
       boolean blnRes=true;
       try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(5);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT2_LINEA,"");
            vecCab.add(INT_TBL_DAT2_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT2_COD_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT2_NOM_ITM,"Nombre");
            vecCab.add(INT_TBL_DAT2_UNIDAD,"Unidad"); 
            vecCab.add(INT_TBL_DAT2_USR_MOD,"Usuario");                   //Precio de venta 1.
            vecCab.add(INT_TBL_DAT2_FEC_MOD,"Fecha");                   //fecha de  actualizacion.
            
            objTblMod2=new ZafTblMod();
            objTblMod2.setHeader(vecCab);
            tblDat1.setModel(objTblMod2);
               
            tblDat1.setRowSelectionAllowed(true);
            tblDat1.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            objColNum=new ZafColNumerada(tblDat1,INT_TBL_DAT2_LINEA);
            objTblPopMnu=new ZafTblPopMnu(tblDat1);
            tblDat1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat1.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT2_LINEA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT2_COD_SIS).setPreferredWidth(52);
            tcmAux.getColumn(INT_TBL_DAT2_COD_ALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT2_NOM_ITM).setPreferredWidth(240);
            tcmAux.getColumn(INT_TBL_DAT2_UNIDAD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT2_USR_MOD).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_DAT2_FEC_MOD).setPreferredWidth(90);  
            //Configurar JTable: Establecer el ListSelectionListener.
            javax.swing.ListSelectionModel lsm=tblDat1.getSelectionModel();
            lsm.addListSelectionListener(new ZafLisSelLis());
            tblDat1.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat1.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat1);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=null;
            //Libero los objetos auxiliares.
            objTblOrd=new ZafTblOrd(tblDat1);
            tcmAux=null;
        }   
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }  
    
    private boolean configurarTabla3()
    {
       boolean blnRes=true;
       try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(5);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT3_LINEA,"");
            vecCab.add(INT_TBL_DAT3_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT3_COD_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT3_NOM_ITM,"Nombre");
            vecCab.add(INT_TBL_DAT3_UNIDAD,"Unidad");
            vecCab.add(INT_TBL_DAT3_USR_MOD,"Usuario");                   //Precio de venta 1.
            vecCab.add(INT_TBL_DAT3_FEC_MOD,"Fecha");                   //fecha de  actualizacion.
            
            objTblMod3=new ZafTblMod();
            objTblMod3.setHeader(vecCab);
            tblDat2.setModel(objTblMod3);
               
            tblDat2.setRowSelectionAllowed(true);
            tblDat2.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            objColNum=new ZafColNumerada(tblDat2,INT_TBL_DAT3_LINEA);
            objTblPopMnu=new ZafTblPopMnu(tblDat2);
            tblDat2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat2.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT3_LINEA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT3_COD_SIS).setPreferredWidth(52);
            tcmAux.getColumn(INT_TBL_DAT3_COD_ALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT3_NOM_ITM).setPreferredWidth(240);
            tcmAux.getColumn(INT_TBL_DAT3_UNIDAD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT3_USR_MOD).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT3_FEC_MOD).setPreferredWidth(90);
            
            tblDat2.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat2.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat2);
            //Configurar JTable: Renderizar celdas.
            
            objTblCelRenLbl=null;
            //Libero los objetos auxiliares.
            objTblOrd=new ZafTblOrd(tblDat2);
            tcmAux=null;
        }   
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }  
    
    public void Configura_ventana_consulta()
    {
        configurarVenConProducto();
    }
    
    private boolean configurarVenConProducto()
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a7.co_itm");
            arlCam.add("a7.tx_codAlt");
            arlCam.add("a7.tx_nomItm");
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Sis.");
            arlAli.add("Código");
            arlAli.add("Nombre");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("90");
            arlAncCol.add("390");
            String Str_Sql="";
            Str_Sql+="SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm";
            Str_Sql+=" FROM tbm_inv AS a1";
            Str_Sql+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            objVenCon=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
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
        int intCodEmp, intCodLoc, intNumTotReg, i;
        try  
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                if (optTod.isSelected())
                {
                    strSQL="select a.co_itm, a.co_itmuni as cotimant, co_usruni, fe_uni as fe_ultmod, inv.tx_codalt, " +
                    " inv.tx_nomitm ,uni1.tx_descor AS unidadinv, inh.tx_codalt as txcodaltant, inh.tx_nomitm as ModUni,  uni2.tx_descor AS unidadinh, u.tx_nom" +
                    " FROM tbh_uniinv AS a" +
                    " INNER JOIN tbm_inv AS inv ON(a.co_emp=inv.co_emp AND a.co_itm=inv.co_itm)" +
                    " INNER JOIN tbh_inv AS inh ON(a.co_emp=inh.co_emp AND a.co_itmuni=inh.co_itm and a.co_hisUni=inh.co_his)" +
                    "  LEFT JOIN tbm_var AS uni1 on (inv.co_uni=uni1.co_reg)" +
                    "  LEFT JOIN tbm_var AS uni2 on (inh.co_uni=uni2.co_reg) " +
                    "INNER JOIN tbm_usr AS u on(a.co_usruni=u.co_usr) WHERE a.co_emp=" + intCodEmp; 

                    strSQL2="SELECT count(*) FROM ( select a.co_itm,a.co_itmuni as cotimant ,co_usruni,fe_uni as fe_ultmod , inv.tx_codalt ," +
                    " inv.tx_nomitm, inh.tx_codalt as txcodaltant, inh.tx_nomitm as ModUni, u.tx_nom " +
                    " FROM tbh_uniinv as a " +
                    " INNER JOIN tbm_inv as inv ON(a.co_emp=inv.co_emp AND a.co_itm=inv.co_itm)" +
                    " INNER JOIN tbh_inv as inh ON(a.co_emp=inh.co_emp AND a.co_itmuni=inh.co_itm and a.co_hisUni=inh.co_his)" +
                    " INNER JOIN tbm_usr as u on(a.co_usruni=u.co_usr) WHERE a.co_emp=" + intCodEmp + " ) AS x";
                }    
                else
                {
                    strSQL="select a.co_itm, a.co_itmuni as cotimant ,co_usruni, fe_uni as fe_ultmod, inv.tx_codalt, " +
                    " inv.tx_nomitm, uni1.tx_descor as unidadinv, inh.tx_codalt as txcodaltant, inh.tx_nomitm as ModUni ,  uni2.tx_descor as unidadinh, u.tx_nom " +
                    " FROM tbh_uniinv as a " +
                    " INNER JOIN tbm_inv as inv ON(a.co_emp=inv.co_emp AND a.co_itm=inv.co_itm)" +
                    " INNER JOIN tbh_inv as inh ON(a.co_emp=inh.co_emp AND a.co_itmuni=inh.co_itm and a.co_hisUni=inh.co_his)" +
                    "  LEFT JOIN tbm_var as uni1 on (inv.co_uni=uni1.co_reg)" +
                    "  LEFT JOIN tbm_var as uni2 on (inh.co_uni=uni2.co_reg) " +
                    " INNER JOIN tbm_usr as u on(a.co_usruni=u.co_usr) WHERE a.co_emp=" + intCodEmp + ""+
                    " and inh.tx_codalt='" + txtCodAlt.getText().trim().toUpperCase() + "'";

                    strSQL2="SELECT count(*) FROM (  select a.co_itm,a.co_itmuni as cotimant ,co_usruni,fe_uni as fe_ultmod, inv.tx_codalt," +
                    " inv.tx_nomitm, inh.tx_codalt as txcodaltant, inh.tx_nomitm as ModUni, u.tx_nom " +
                    " FROM tbh_uniinv as a " +
                    " INNER JOIN tbm_inv as inv ON(a.co_emp=inv.co_emp AND a.co_itm=inv.co_itm)" +
                    " INNER JOIN tbh_inv as inh ON(a.co_emp=inh.co_emp AND a.co_itmuni=inh.co_itm and a.co_hisUni=inh.co_his)" +
                    " INNER JOIN tbm_usr as u on(a.co_usruni=u.co_usr) WHERE a.co_emp=" + intCodEmp + "" +
                    " and inh.tx_codalt='" + txtCodAlt.getText().trim().toUpperCase() + "' ) AS x";
                }
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL2);
                if (intNumTotReg==-1)
                    return false;
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_SIS,rst.getString("cotimant"));
                    vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("txcodaltant"));
                    vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("ModUni"));
                    vecReg.add(INT_TBL_DAT_UNI_ITM,rst.getString("unidadinv"));
                    vecReg.add(INT_TBL_DAT_DEC_UNI,rst.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_COD_ANT,rst.getString("tx_codalt"));
                    vecReg.add(INT_TBL_DAT_NOM_ANT,rst.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_DAT_UNI_ANT,rst.getString("unidadinh"));
                    vecReg.add(INT_TBL_DAT_USR_MOD,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_DAT_FEC_MOD,rst.getDate("fe_ultmod"));
                    vecDat.add(vecReg);
                    i++;
                    pgrSis.setValue(i);
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
       
    private boolean cargarDetRegUni()
    {
        boolean blnRes=true;
        int intCodEmp, intCodLoc, intNumTotReg, i;
        try  
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                if (optTod.isSelected())
                {
                    strSQL="select * from ( " +
                    "  select * from (" +
                    "  select distinct(a.co_itm) as cotimant " +
                    " FROM tbh_inv as a " +
                    "  WHERE a.co_emp="+intCodEmp+" " +
                    "  group by a.co_emp,a.co_itm,a.tx_codalt,a.tx_nomitm" +
                    " ) as x ,(" +
                    "  select a.co_itm,a.tx_nomitm,tx_codalt, u.tx_nom,a.fe_ultmod , uni.tx_descor  as unidad from tbm_inv as a " +
                    "  LEFT JOIN tbm_var as uni on (a.co_uni=uni.co_reg)  " +
                    "  LEFT JOIN tbm_usr as u on(a.co_usrmod=u.co_usr) " +
                    "  where a.co_emp="+intCodEmp+""+
                    " ) as y where  y.co_itm=x.cotimant" +
                    " ) as x ";
                    strSQL2="select count(*) from ( " +
                    "  select * from (" +
                    "  select distinct(a.co_itm) as cotimant " +
                    " FROM tbh_inv as a " +
                    "  WHERE a.co_emp="+intCodEmp+" " +
                    "  group by a.co_emp,a.co_itm,a.tx_codalt,a.tx_nomitm" +
                    " ) as x ,(" +
                    "  select a.co_itm,a.tx_nomitm,tx_codalt, u.tx_nom,a.fe_ultmod from tbm_inv as a " +
                    "  LEFT JOIN tbm_usr as u on(a.co_usrmod=u.co_usr) " +
                    "  where a.co_emp="+intCodEmp+""+
                    " ) as y where  y.co_itm=x.cotimant" +
                    " ) as x ";
                }
                else
                {
                    strSQL="select * from ( " +
                    "  select * from (" +
                    "  select distinct(a.co_itm) as cotimant " +
                    " FROM tbh_inv as a " +
                    "  WHERE a.co_emp="+intCodEmp+" " +
                    "  group by a.co_emp,a.co_itm,a.tx_codalt,a.tx_nomitm" +
                    " ) as x ,(" +
                    "  select a.co_itm,a.tx_nomitm,tx_codalt, u.tx_nom,a.fe_ultmod  , uni.tx_descor  as unidad from tbm_inv as a " +
                    " LEFT JOIN tbm_var as uni on (a.co_uni=uni.co_reg) " +
                    " LEFT JOIN tbm_usr as u on(a.co_usrmod=u.co_usr) " +
                    "  where a.co_emp="+intCodEmp+""+
                    " ) as y where  y.co_itm=x.cotimant" +
                    " ) as x  WHERE upper(x.tx_codalt)='"+txtCodAlt.getText().trim().toUpperCase()+"'"; 
                    strSQL2="select count(*) from ( " +
                    "  select * from (" +
                    "  select distinct(a.co_itm) as cotimant " +
                    " FROM tbh_inv as a " +
                    "  WHERE a.co_emp="+intCodEmp+" " +
                    "  group by a.co_emp,a.co_itm,a.tx_codalt,a.tx_nomitm" +
                    " ) as x ,(" +
                    "  select a.co_itm,a.tx_nomitm,tx_codalt, u.tx_nom,a.fe_ultmod from tbm_inv as a " +
                    "  LEFT JOIN tbm_usr as u on(a.co_usrmod=u.co_usr) " +
                    "  where a.co_emp="+intCodEmp+""+
                    " ) as y where  y.co_itm=x.cotimant" +
                    " ) as x  WHERE upper(x.tx_codalt)='"+txtCodAlt.getText().trim().toUpperCase()+"'";
                }
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL2);
                if (intNumTotReg==-1)
                    return false;
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                while (rst.next())
                {  
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT2_LINEA,"");
                    vecReg.add(INT_TBL_DAT2_COD_SIS,rst.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT2_COD_ALT,rst.getString("tx_codalt"));
                    vecReg.add(INT_TBL_DAT2_NOM_ITM,rst.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_DAT2_UNIDAD, rst.getString("unidad") );
                    vecReg.add(INT_TBL_DAT2_USR_MOD,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_DAT2_FEC_MOD,rst.getDate("fe_ultmod")); 
                    vecDat.add(vecReg);
                    i++;
                    pgrSis.setValue(i);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblMod2.setData(vecDat);
                tblDat1.setModel(objTblMod2);
                vecDat.clear();
                if (intNumTotReg==tblDat1.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat1.getRowCount() + ".");
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
            if (optUni.isSelected())
            {
                if (!cargarDetReg())
                {
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
            }
            if (optMod.isSelected())
            {
                if (!cargarDetRegUni())
                {
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
                }
                //Establecer el foco en el JTable sólo cuando haya datos.
                if (tblDat1.getRowCount()>0)
                {
                    tabFrm.setSelectedIndex(2);
                    //  tblDat1.setRowSelectionInterval(0, 0);
                    tblDat1.requestFocus();
                }
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
                case INT_TBL_DAT_COD_SIS:
                    strMsg="Código del item (Sistema)";
                    break;
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_DEC_UNI:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_COD_ANT:
                    strMsg="Codigo Anteriro del Item. ";
                    break;
                case INT_TBL_DAT_NOM_ANT:
                    strMsg="Nombre Anteriro del Item.";
                    break;
                case INT_TBL_DAT_FEC_MOD:
                    strMsg="Fecha de Actualizacion o Unificacion."; 
                    break;
                case INT_TBL_DAT_USR_MOD:
                    strMsg="Ususario que Actualizacion o Unificacion."; 
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    private boolean cargarDetRegUni2()
    {
        boolean blnRes=true;
        int intCodEmp, intCodLoc, i;
        try  
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="select a.co_itm, a.tx_codalt , a.tx_nomitm , u.tx_nom,a.fe_ultmod , uni.tx_descor as unidad FROM tbh_inv as a" +
                " LEFT JOIN tbm_var as uni on (a.co_uni=uni.co_reg) " +
                " LEFT JOIN tbm_usr as u on(a.co_usrmod=u.co_usr) " +
                " where a.co_emp="+intCodEmp+" and a.co_itm="+tblDat1.getValueAt(tblDat1.getSelectedRow(),INT_TBL_DAT2_COD_SIS);
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                //Obtener los registros.
                i=0;
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT3_LINEA,"");
                    vecReg.add(INT_TBL_DAT3_COD_SIS,rst.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT3_COD_ALT,rst.getString("tx_codalt"));
                    vecReg.add(INT_TBL_DAT3_NOM_ITM,rst.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_DAT3_UNIDAD,rst.getString("unidad"));
                    vecReg.add(INT_TBL_DAT3_USR_MOD,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_DAT3_FEC_MOD,rst.getDate("fe_ultmod")); 
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblMod3.setData(vecDat);
                tblDat2.setModel(objTblMod3);
                vecDat.clear();
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
    * Esta clase implementa la interface "ListSelectionListener" para determinar
    * cambios en la selección. Es decir, cada vez que se selecciona una fila
    * diferente en el JTable se ejecutará el "ListSelectionListener".
    */
    private class ZafLisSelLis implements javax.swing.event.ListSelectionListener
    {
        public void valueChanged(javax.swing.event.ListSelectionEvent e)
        {
            javax.swing.ListSelectionModel lsm=(javax.swing.ListSelectionModel)e.getSource();
            int intMax=lsm.getMaxSelectionIndex();
            String strAux;
            if (!lsm.isSelectionEmpty())
            {
                if (chkMosStkBod.isSelected())
                    cargarDetRegUni2();
                else
                    objTblMod3.removeAllRows();
            }
        }
    }
    
}