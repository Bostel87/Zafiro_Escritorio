/*
 * ZafCom11.java
 *
 * Created on 29 de diciembre de 2005, 10:10 PM
 */
package Compras.ZafCom11;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenConItm01;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;

/**
 *
 * @author  Eddye Lino
 */
public class ZafCom11 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_MAE=1;                     //Código maestro del item.
    static final int INT_TBL_DAT_COD_ITM=2;                     //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_ALT=3;                     //Código alterno del item.
    static final int INT_TBL_DAT_COD_ALT_2=4;                   //Código en letras del item.
    static final int INT_TBL_DAT_NOM_ITM=5;                     //Nombre del item.
    static final int INT_TBL_DAT_DEC_UNI=6;                     //Descripción corta de la unidad de medida.
    static final int INT_TBL_DAT_STK_CON=7;                     //Stock consolidado.
    static final int INT_TBL_DAT_CAN_DIS=8;                     //Cantidad Disponible.
    static final int INT_TBL_DAT_PRE_VTA1=9;                    //Precio de venta 1.
    
    static final int INT_TBL_DET_LIN=0;                         //Línea.
    static final int INT_TBL_DET_COD_EMP=1;                     //Código de la empresa.
    static final int INT_TBL_DET_NOM_EMP=2;                     //Nombre de la empresa.
    static final int INT_TBL_DET_COD_BOD=3;                     //Código de la bodega.
    static final int INT_TBL_DET_NOM_BOD=4;                     //Nombre de la bodega.
    static final int INT_TBL_DET_COD_ITM=5;                     //Código de item (Sistema).
    static final int INT_TBL_DET_COD_ALT=6;                     //Código alterno del item.
    static final int INT_TBL_DET_COD_ALT_2=7;                   //Código en letras del item.
    static final int INT_TBL_DET_STK_ACT=8;                     //Stock actual.
    static final int INT_TBL_DET_CAN_ING_BOD=9;                 //Cantidad por ingresar a Bodega.
    static final int INT_TBL_DET_CAN_EGR_BOD=10;                //Cantidad por egresar de Bodega.
    static final int INT_TBL_DET_CAN_DES_ENT_BOD=11;            //Cantidad en Despacho que se va a entregar a Bodega.
    static final int INT_TBL_DET_CAN_DES_ENT_CLI=12;            //Cantidad en Despacho que se va a entregar al Cliente.
    static final int INT_TBL_DET_CAN_TRA=13;                    //Cantidad en Tránsito.
    static final int INT_TBL_DET_CAN_REV=14;                    //Cantidad en Revisión.
    static final int INT_TBL_DET_CAN_RES=15;                    //Cantidad en Reserva.
    static final int INT_TBL_DET_CAN_RES_VEN=16;                //Cantidad en Reserva (Vendida).
    static final int INT_TBL_DET_CAN_DIS=17;                    //Cantidad Disponible.
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;                                //Modelo del JTable: 
    private ZafTblMod objTblModDatFilSel;                       //Modelo del JTable: Datos de la fila seleccionada.
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafVenConItm01 objVenConItm01;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodAlt, strNomItm, strCodAlt2;            //Contenido del campo al obtener el foco.
    int intEst=0;
    
    /**
     * Este constructor crea una instancia de la clase ZafCom11.
     * @param objParSis El objeto ZafParSis.
     */
    public ZafCom11(ZafParSis objParSis)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            this.objParSis=(ZafParSis)objParSis.clone();
            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    public ZafCom11(ZafParSis obj, String strItm1, String strItm2, String strItm3)
    {
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
        if (!configurarFrm())
            exitForm();
        intEst=1;
        /*
        //Se lo puso como comentario cuando se comenzó a utilizar la clase "ZafVenConItm01" porque dicha clase por el momento no recibe el filtro a aplicar.
        //Se analizó y se detectó que el único programa que usa éste constructor es el programa "Unificación de inventario..." el cual ya no se está utilizando.
        txtCodItm.setText("" + strItm1);
        txtCodAlt.setText("" + strItm2);
        txtNomItm.setText("" + strItm3);
        */
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
        chkSolStk = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        sppRpt = new javax.swing.JSplitPane();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panRptStkBod = new javax.swing.JPanel();
        chkMosStkBod = new javax.swing.JCheckBox();
        spnStkBod = new javax.swing.JScrollPane();
        tblDatFilSel = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butConStkBod = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
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

        chkSolStk.setText("Sólo items con stock");
        chkSolStk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolStkActionPerformed(evt);
            }
        });
        panFil.add(chkSolStk);
        chkSolStk.setBounds(24, 64, 324, 20);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.BorderLayout());

        sppRpt.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppRpt.setResizeWeight(0.8);
        sppRpt.setOneTouchExpandable(true);

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

        sppRpt.setTopComponent(spnDat);

        panRptStkBod.setLayout(new java.awt.BorderLayout());

        chkMosStkBod.setText("Mostrar datos del item seleccionado");
        chkMosStkBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosStkBodActionPerformed(evt);
            }
        });
        panRptStkBod.add(chkMosStkBod, java.awt.BorderLayout.NORTH);
        chkMosStkBod.getAccessibleContext().setAccessibleName("Mostrar más datos del item seleccionado");

        tblDatFilSel.setModel(new javax.swing.table.DefaultTableModel(
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
        spnStkBod.setViewportView(tblDatFilSel);

        panRptStkBod.add(spnStkBod, java.awt.BorderLayout.CENTER);

        sppRpt.setBottomComponent(panRptStkBod);

        panRpt.add(sppRpt, java.awt.BorderLayout.CENTER);

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

        butConStkBod.setText("Bodegas");
        butConStkBod.setToolTipText("Mostrar el stock existente en cada bodega");
        butConStkBod.setPreferredSize(new java.awt.Dimension(92, 25));
        butConStkBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConStkBodActionPerformed(evt);
            }
        });
        panBot.add(butConStkBod);

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

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    //<editor-fold defaultstate="collapsed" desc="/* Eventos */">
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        if (intEst==1) 
        {
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
        }
    }//GEN-LAST:event_formInternalFrameOpened

    private void chkSolStkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSolStkActionPerformed
        if (chkSolStk.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkSolStkActionPerformed

    private void chkMosStkBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosStkBodActionPerformed
        if (chkMosStkBod.isSelected())
            cargarDatFilSel();
        else
            objTblModDatFilSel.removeAllRows();
    }//GEN-LAST:event_chkMosStkBodActionPerformed

    private void butConStkBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConStkBodActionPerformed
        cargarDatFilSel();
    }//GEN-LAST:event_butConStkBodActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            objVenConItm01.limpiar();
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

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }   
    //</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="/* Variables declaration - do not modify */">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butConStkBod;
    private javax.swing.JCheckBox chkMosStkBod;
    private javax.swing.JCheckBox chkSolStk;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panRptStkBod;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnStkBod;
    private javax.swing.JSplitPane sppRpt;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDatFilSel;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
        
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafVenConItm01:
            objVenConItm01=new ZafVenConItm01(objParSis, "a1.", optFil);
            panFil.add(objVenConItm01);
            objVenConItm01.setBounds(24, 44, 656, 20);
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.12");
            lblTit.setText(strAux);
            //Configurar objetos.
            //txtCodItm.setVisible(false);
            //Configurar las ZafVenCon.
            //configurarVenConItm();
            //Configurar los JTables.
            configurarTblDat();
            configurarTblDet();
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
            vecCab=new Vector(9);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_MAE,"Cód.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALT_2,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre");
            vecCab.add(INT_TBL_DAT_DEC_UNI,"Unidad");
            vecCab.add(INT_TBL_DAT_STK_CON,"Stock");
            vecCab.add(INT_TBL_DAT_CAN_DIS,"Disponible");
            vecCab.add(INT_TBL_DAT_PRE_VTA1,"Pre.Vta.1");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_2).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(180);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_DIS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PRE_VTA1).setPreferredWidth(70);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            //Si no es el usuario Administrador (Código=1) ocultar ciertas columnas.
            if (objParSis.getCodigoUsuario()!=1)
            {
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MAE, tblDat);
            }
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_DIS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PRE_VTA1).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_2).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer el ListSelectionListener.
            javax.swing.ListSelectionModel lsm=tblDat.getSelectionModel();
            lsm.addListSelectionListener(new ZafLisSelLis());
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura el JTable "tblDet".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDet()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(18);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DET_LIN,"");
            vecCab.add(INT_TBL_DET_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DET_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_DET_COD_BOD,"Cód.Bod.");
            vecCab.add(INT_TBL_DET_NOM_BOD,"Bodega");
            vecCab.add(INT_TBL_DET_COD_ITM,"Cód.Itm.");
            vecCab.add(INT_TBL_DET_COD_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DET_COD_ALT_2,"Cód.Alt.2");
            vecCab.add(INT_TBL_DET_STK_ACT,"Stock");
            vecCab.add(INT_TBL_DET_CAN_ING_BOD,"Can.Ing.Bod.");
            vecCab.add(INT_TBL_DET_CAN_EGR_BOD,"Can.Egr.Bod.");
            vecCab.add(INT_TBL_DET_CAN_DES_ENT_BOD,"Can.Des.Ent.Bod.");
            vecCab.add(INT_TBL_DET_CAN_DES_ENT_CLI,"Can.Des.Ent.Cli.");
            vecCab.add(INT_TBL_DET_CAN_TRA,"Can.Tra.");
            vecCab.add(INT_TBL_DET_CAN_REV,"Can.Rev.");
            vecCab.add(INT_TBL_DET_CAN_RES,"Can.Res.");
            vecCab.add(INT_TBL_DET_CAN_RES_VEN,"Can.Res.Ven.");
            vecCab.add(INT_TBL_DET_CAN_DIS,"Can.Dis.");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModDatFilSel=new ZafTblMod();
            objTblModDatFilSel.setHeader(vecCab);
            tblDatFilSel.setModel(objTblModDatFilSel);
            //Configurar JTable: Establecer tipo de selección.
            tblDatFilSel.setRowSelectionAllowed(true);
            tblDatFilSel.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatFilSel);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatFilSel.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatFilSel.getColumnModel();
            tcmAux.getColumn(INT_TBL_DET_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DET_NOM_EMP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DET_COD_BOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DET_NOM_BOD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DET_COD_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DET_COD_ALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DET_COD_ALT_2).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DET_STK_ACT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DET_CAN_ING_BOD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DET_CAN_EGR_BOD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DET_CAN_DES_ENT_BOD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DET_CAN_DES_ENT_CLI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DET_CAN_TRA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DET_CAN_REV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DET_CAN_RES).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DET_CAN_RES_VEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DET_CAN_DIS).setPreferredWidth(70);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
//            tcmAux.getColumn(INT_TBL_DAT_BUT_TIP_RET).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatFilSel.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblModDatFilSel.addSystemHiddenColumn(INT_TBL_DET_COD_EMP, tblStkBod);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblDatFilSel.getTableHeader().addMouseMotionListener(new ZafMouMotAdaDet());
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatFilSel);
            tcmAux.getColumn(INT_TBL_DET_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DET_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_COD_BOD).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_COD_ITM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DET_COD_ALT_2).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DET_STK_ACT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_CAN_ING_BOD).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_CAN_EGR_BOD).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_CAN_DES_ENT_BOD).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_CAN_DES_ENT_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_CAN_TRA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_CAN_REV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_CAN_RES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_CAN_RES_VEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_CAN_DIS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Libero los objetos auxiliares.
            tcmAux=null;
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
                //Obtener la condición.
                strConSQL="";
//------------------------------------------------------------------------------
                //Filtrar por "Item".
                if (objVenConItm01.isCondicionAplicada())
                {
                    strConSQL+=objVenConItm01.getCondicionesSQL();
                }
//------------------------------------------------------------------------------
                if (chkSolStk.isSelected())
                    strConSQL+=" AND a3.nd_stkAct>0";
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a5.tx_desCor, a3.nd_stkAct, a4.nd_canDis, a1.nd_preVta1";
                strSQL+=" FROM tbm_inv AS a1";
                strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                strSQL+=" LEFT OUTER JOIN (";
                strSQL+=" SELECT b1.co_itmMae, SUM(b2.nd_stkAct) AS nd_stkAct";
                strSQL+=" FROM tbm_equInv AS b1";
                strSQL+=" INNER JOIN tbm_inv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
                strSQL+=" WHERE b2.co_emp<>" + objParSis.getCodigoEmpresaGrupo();
                strSQL+=" GROUP BY b1.co_itmMae";
                strSQL+=" ) AS a3 ON (a2.co_itmMae=a3.co_itmMae)";
                strSQL+=" LEFT OUTER JOIN (";
                strSQL+=" SELECT b1.co_itmMae, SUM(b2.nd_canDis) AS nd_canDis";
                strSQL+=" FROM tbm_equInv AS b1";
                strSQL+=" INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
                strSQL+=" WHERE b2.co_emp<>" + objParSis.getCodigoEmpresaGrupo();
                strSQL+=" GROUP BY b1.co_itmMae";
                strSQL+=" ) AS a4 ON (a2.co_itmMae=a4.co_itmMae)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a1.co_uni=a5.co_reg)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
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
                        vecReg.add(INT_TBL_DAT_COD_MAE, rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_ITM, rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT, rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_2, rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM, rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_DEC_UNI, rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_STK_CON, rst.getString("nd_stkAct"));
                        vecReg.add(INT_TBL_DAT_CAN_DIS, rst.getString("nd_canDis"));
                        vecReg.add(INT_TBL_DAT_PRE_VTA1, rst.getString("nd_preVta1"));
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
     * Esta función permite cargar los datos de la fila seleccionada.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDatFilSel()
    {
        boolean blnRes=true;
        try
        {
            if (tblDat.getSelectedRow()!=-1)
            {
//                objTooBar.setMenSis("Obteniendo datos...");
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT b2.co_emp, b3.tx_nom, b2.co_bod, b4.tx_nom AS b4_tx_nom, b5.co_itm, b5.tx_codAlt, b5.tx_codAlt2, b2.nd_stkAct, b2.nd_canIngBod, b2.nd_canEgrBod, b2.nd_canDesEntBod, b2.nd_canDesEntCli, b2.nd_canTra, b2.nd_canRev, b2.nd_canRes, b2.nd_canResVen, b2.nd_canDis";
                    strSQL+=" FROM tbm_equInv AS b1";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" SELECT a2.co_itmMae, a3.co_empGrp AS co_emp, a3.co_bodGrp AS co_bod, SUM(a1.nd_stkAct) AS nd_stkAct, SUM(a1.nd_canIngBod) AS nd_canIngBod, SUM(a1.nd_canEgrBod) AS nd_canEgrBod, SUM(a1.nd_canDesEntBod) AS nd_canDesEntBod, SUM(a1.nd_canDesEntCli) AS nd_canDesEntCli, SUM(a1.nd_canTra) AS nd_canTra, SUM(a1.nd_canRev) AS nd_canRev, SUM(a1.nd_canRes) AS nd_canRes, SUM(a1.nd_canResVen) AS nd_canResVen, SUM(a1.nd_canDis) AS nd_canDis";
                    strSQL+=" FROM tbm_invBod AS a1";
                    strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                    strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)";
                    strSQL+=" WHERE a2.co_itmMae=" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_MAE).toString();
                    strSQL+=" AND a3.co_empGrp=" + objParSis.getCodigoEmpresaGrupo();
                    strSQL+=" GROUP BY a2.co_itmMae, a3.co_empGrp, a3.co_bodGrp";
                    strSQL+=" UNION ALL";
                    strSQL+=" SELECT a2.co_itmMae, a1.co_emp, a1.co_bod, a1.nd_stkAct, a1.nd_canIngBod, a1.nd_canEgrBod, a1.nd_canDesEntBod, a1.nd_canDesEntCli, a1.nd_canTra, a1.nd_canRev, a1.nd_canRes, a1.nd_CanResVen, a1.nd_canDis";
                    strSQL+=" FROM tbm_invBod AS a1";
                    strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                    strSQL+=" WHERE a2.co_itmMae=" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_MAE).toString();
                    strSQL+=" AND a2.co_emp<>" + objParSis.getCodigoEmpresaGrupo();
                    strSQL+=" ) AS b2 ON (b1.co_itmMae=b2.co_itmMae AND b1.co_emp=b2.co_emp)";
                    strSQL+=" INNER JOIN tbm_emp AS b3 ON (b2.co_emp=b3.co_emp)";
                    strSQL+=" INNER JOIN tbm_bod AS b4 ON (b2.co_emp=b4.co_emp AND b2.co_bod=b4.co_bod)";
                    strSQL+=" INNER JOIN tbm_inv AS b5 ON (b1.co_emp=b5.co_emp AND b1.co_itm=b5.co_itm)";
                    strSQL+=" ORDER BY b1.co_itmMae, b2.co_emp, b2.co_bod";
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
//                    objTooBar.setMenSis("Cargando datos...");
                    while (rst.next())
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DET_LIN,"");
                        vecReg.add(INT_TBL_DET_COD_EMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DET_NOM_EMP,rst.getString("tx_nom"));
                        vecReg.add(INT_TBL_DET_COD_BOD,rst.getString("co_bod"));
                        vecReg.add(INT_TBL_DET_NOM_BOD,rst.getString("b4_tx_nom"));
                        vecReg.add(INT_TBL_DET_COD_ITM,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DET_COD_ALT,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DET_COD_ALT_2,rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DET_STK_ACT,rst.getString("nd_stkAct"));
                        vecReg.add(INT_TBL_DET_CAN_ING_BOD,rst.getString("nd_canIngBod"));
                        vecReg.add(INT_TBL_DET_CAN_EGR_BOD,rst.getString("nd_canEgrBod"));
                        vecReg.add(INT_TBL_DET_CAN_DES_ENT_BOD,rst.getString("nd_canDesEntBod"));
                        vecReg.add(INT_TBL_DET_CAN_DES_ENT_CLI,rst.getString("nd_canDesEntCli"));
                        vecReg.add(INT_TBL_DET_CAN_TRA,rst.getString("nd_canTra"));
                        vecReg.add(INT_TBL_DET_CAN_REV,rst.getString("nd_canRev"));
                        vecReg.add(INT_TBL_DET_CAN_RES,rst.getString("nd_canRes"));
                        vecReg.add(INT_TBL_DET_CAN_RES_VEN,rst.getString("nd_canResVen"));
                        vecReg.add(INT_TBL_DET_CAN_DIS,rst.getString("nd_canDis"));
                        vecDat.add(vecReg);
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    //Asignar vectores al modelo.
                    objTblModDatFilSel.setData(vecDat);
                    tblDatFilSel.setModel(objTblModDatFilSel);
                    vecDat.clear();
//                    objTooBar.setMenSis("Listo");
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
                case INT_TBL_DAT_COD_MAE:
                    strMsg="Código maestro del item";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código del item";
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
                case INT_TBL_DAT_DEC_UNI:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_STK_CON:
                    strMsg="Stock consolidado";
                    break;
                case INT_TBL_DAT_PRE_VTA1:
                    strMsg="Precio de venta 1";
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
    private class ZafMouMotAdaDet extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDatFilSel.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DET_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DET_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DET_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_DET_COD_BOD:
                    strMsg="Código de la bodega";
                    break;
                case INT_TBL_DET_NOM_BOD:
                    strMsg="Nombre de la bodega";
                    break;
                    
                case INT_TBL_DET_COD_ITM:
                    strMsg="Código del item";
                    break;
                case INT_TBL_DET_COD_ALT:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DET_COD_ALT_2:
                    strMsg="Código alterno 2 del item";
                    break;
                case INT_TBL_DET_STK_ACT:
                    strMsg="Stock actual";
                    break;
                case INT_TBL_DET_CAN_ING_BOD:
                    strMsg="Cantidad por ingresar a Bodega";
                    break;
                case INT_TBL_DET_CAN_EGR_BOD:
                    strMsg="Cantidad por egresar de Bodega";
                    break;
                case INT_TBL_DET_CAN_DES_ENT_BOD:
                    strMsg="Cantidad en Despacho que se va a entregar a Bodega";
                    break;
                case INT_TBL_DET_CAN_DES_ENT_CLI:
                    strMsg="Cantidad en Despacho que se va a entregar al Cliente";
                    break;
                case INT_TBL_DET_CAN_TRA:
                    strMsg="Cantidad en Tránsito";
                    break;
                case INT_TBL_DET_CAN_REV:
                    strMsg="Cantidad en Revisión";
                    break;
                case INT_TBL_DET_CAN_RES:
                    strMsg="Cantidad en Reserva";
                    break;
                case INT_TBL_DET_CAN_RES_VEN:
                    strMsg="Cantidad en Reserva (En Ventas)";
                    break;
                case INT_TBL_DET_CAN_DIS:
                    strMsg="Cantidad Disponible";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDatFilSel.getTableHeader().setToolTipText(strMsg);
        }
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
            if (!lsm.isSelectionEmpty())
            {
                if (chkMosStkBod.isSelected())
                    cargarDatFilSel();
                else
                    objTblModDatFilSel.removeAllRows();
            }
        }
    }
    
}