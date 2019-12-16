/*
 * ZafAut_06.java
 *
 * Created on 03 de julio de 2007, 02:07 PM
 */

package Librerias.ZafAut;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.math.BigDecimal;

/**
 *
 * @author  Eddye Lino
 */
public class ZafAut_06 extends javax.swing.JDialog
{
    /*Simbología:
     *  DAT: Datos
     **/
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_ITM=1;                     //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_ALT=2;                     //Código alterno.
    static final int INT_TBL_DAT_NOM_ITM=3;                     //Nombre del item.
    static final int INT_TBL_DAT_CAN=4;                         //Cantidad.
    static final int INT_TBL_DAT_STK_ACT=5;                     //Stock actual.
    static final int INT_TBL_DAT_PRE_UNI=6;                     //Precio unitario.
    static final int INT_TBL_DAT_POR_DES=7;                     //Porcentaje de descuento.
    static final int INT_TBL_DAT_PRE_VEN=8;                     //Precio de venta.
    static final int INT_TBL_DAT_TOT_FIL=9;                     //Total de la fila
    static final int INT_TBL_DAT_COS_UNI=10;                     //Costo unitario.
    static final int INT_TBL_DAT_PRE_LIS=11;                    //Precio de lista.
    static final int INT_TBL_DAT_PRE_LIS_ACT=12;                //Precio de lista actual.
    static final int INT_TBL_DAT_PRE_COM=13;                    //Precio de lista.
    static final int INT_TBL_DAT_PRE_MIN=14;                    //Mínimo precio unitario de venta.
    static final int INT_TBL_DAT_DEF_VAL=15;                    //Deficit en valores.
    static final int INT_TBL_DAT_DEF_POR=16;                    //Deficit en porcentajes.
    static final int INT_TBL_DAT_GAN_UNI_VAL=17;                //Ganancia unitaria en valores.
    static final int INT_TBL_DAT_GAN_TOT_VAL=18;                //Ganancia total en valores.
    static final int INT_TBL_DAT_MAR_UTI_ITM=19;                //Margen de utilidad del item.
    static final int INT_TBL_DAT_MAR_UTI_VEN=20;                //Margen de utilidad obtenido en la venta.
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblModAnaPre;
    private ZafTblMod objTblModForPagAut;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblTot objTblTot;                                //JTable de totales.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDatAnaPre, vecCabAnaPre, vecReg;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    //Variables de la clase.
    private int intCodEmp;                                      //Código de la empresa.
    private int intCodLoc;                                      //Código del local.
    private int intCodTipDoc;                                   //Código del tipo de documento.
    private int intCodDoc;                                      //Código del docuemnto.
    
    /**
     * Este constructor crea una instancia de la clase ZafAut_06.
     * @param parametros El ArrayList que contiene los parámetros que necesita el JDialog.
     * Los parámetros que contiene el ArrayList son los siguientes:
     * <UL>
     * <LI>0: El objeto Frame padre.
     * <LI>1: El objeto ZafParSis.
     * <LI>2: Un <I>int</I> que contiene el código de la empresa.
     * <LI>3: Un <I>int</I> que contiene el código del local.
     * <LI>4: Un <I>int</I> que contiene el código del tipo de documento.
     * <LI>5: Un <I>int</I> que contiene el código del documento.
     * </UL>
     */
    public ZafAut_06(java.util.ArrayList parametros)
    {
        super((java.awt.Frame)parametros.get(0), true);
        initComponents();
        //Inicializar objetos.
        objParSis=(ZafParSis)parametros.get(1);
        intCodEmp=Integer.parseInt(parametros.get(2).toString());
        intCodLoc=Integer.parseInt(parametros.get(3).toString());
        if (parametros.get(4)!=null)
            intCodTipDoc=Integer.parseInt(parametros.get(4).toString());
        intCodDoc=Integer.parseInt(parametros.get(5).toString());
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

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenForPag = new javax.swing.JPanel();
        lblForPagAsi = new javax.swing.JLabel();
        txtCodForPagAsi = new javax.swing.JTextField();
        txtForPagAsi = new javax.swing.JTextField();
        lblForPagSol = new javax.swing.JLabel();
        txtForPagSol = new javax.swing.JTextField();
        txtCodForPagSol = new javax.swing.JTextField();
        lblMaxDes = new javax.swing.JLabel();
        txtMaxDes = new javax.swing.JTextField();
        panGenAnaPre = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        spnDatAnaPre = new javax.swing.JScrollPane();
        tblDatAnaPre = new javax.swing.JTable();
        spnTotAnaPre = new javax.swing.JScrollPane();
        tblTotAnaPre = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenForPag.setPreferredSize(new java.awt.Dimension(10, 64));
        panGenForPag.setLayout(null);

        lblForPagAsi.setText("Forma de pago asignada:");
        lblForPagAsi.setToolTipText("Forma de pago asignada");
        panGenForPag.add(lblForPagAsi);
        lblForPagAsi.setBounds(4, 4, 160, 20);

        txtCodForPagAsi.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenForPag.add(txtCodForPagAsi);
        txtCodForPagAsi.setBounds(164, 4, 56, 20);
        panGenForPag.add(txtForPagAsi);
        txtForPagAsi.setBounds(220, 4, 400, 20);

        lblForPagSol.setText("Forma de pago solicitada:");
        lblForPagSol.setToolTipText("Forma de pago solicitada");
        panGenForPag.add(lblForPagSol);
        lblForPagSol.setBounds(4, 24, 160, 20);
        panGenForPag.add(txtForPagSol);
        txtForPagSol.setBounds(220, 24, 400, 20);

        txtCodForPagSol.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenForPag.add(txtCodForPagSol);
        txtCodForPagSol.setBounds(164, 24, 56, 20);

        lblMaxDes.setText("Máximo descuento:");
        lblMaxDes.setToolTipText("Máximo porcentaje de descuento");
        panGenForPag.add(lblMaxDes);
        lblMaxDes.setBounds(4, 44, 160, 20);

        txtMaxDes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenForPag.add(txtMaxDes);
        txtMaxDes.setBounds(164, 44, 56, 20);

        panGen.add(panGenForPag, java.awt.BorderLayout.NORTH);

        panGenAnaPre.setLayout(new java.awt.BorderLayout());

        panGenDet.setLayout(new java.awt.BorderLayout(0, 1));

        tblDatAnaPre.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDatAnaPre.setViewportView(tblDatAnaPre);

        panGenDet.add(spnDatAnaPre, java.awt.BorderLayout.CENTER);

        spnTotAnaPre.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTotAnaPre.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTotAnaPre.setViewportView(tblTotAnaPre);

        panGenDet.add(spnTotAnaPre, java.awt.BorderLayout.SOUTH);

        panGenAnaPre.add(panGenDet, java.awt.BorderLayout.CENTER);

        panGen.add(panGenAnaPre, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Cierra el cuadro de dialogo.");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

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

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1020)/2, (screenSize.height-450)/2, 1020, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        butConActionPerformed(null);
    }//GEN-LAST:event_formWindowOpened

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        exitForm();
    }//GEN-LAST:event_butCanActionPerformed

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
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.JButton butCon;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblForPagAsi;
    private javax.swing.JLabel lblForPagSol;
    private javax.swing.JLabel lblMaxDes;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenAnaPre;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenForPag;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDatAnaPre;
    private javax.swing.JScrollPane spnTotAnaPre;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDatAnaPre;
    private javax.swing.JTable tblTotAnaPre;
    private javax.swing.JTextField txtCodForPagAsi;
    private javax.swing.JTextField txtCodForPagSol;
    private javax.swing.JTextField txtForPagAsi;
    private javax.swing.JTextField txtForPagSol;
    private javax.swing.JTextField txtMaxDes;
    // End of variables declaration//GEN-END:variables
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux="Análisis de precios y forma de pago de la cotización";
            this.setTitle(strAux + " v0.4");
            lblTit.setText(strAux);
            //Configurar objetos.
            txtCodForPagAsi.setEditable(false);
            txtForPagAsi.setEditable(false);
            txtCodForPagSol.setEditable(false);
            txtForPagSol.setEditable(false);
            txtMaxDes.setEditable(false);
            txtCodForPagAsi.setBackground(objParSis.getColorCamposSistema());
            txtForPagAsi.setBackground(objParSis.getColorCamposSistema());
            txtCodForPagSol.setBackground(objParSis.getColorCamposSistema());
            txtForPagSol.setBackground(objParSis.getColorCamposSistema());
            txtMaxDes.setBackground(objParSis.getColorCamposSistema());
            //Configurar los JTables.
            configurarTblDatAnaPre();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura el JTable "tblDatAnaPre".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatAnaPre()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatAnaPre=new Vector();    //Almacena los datos
            vecCabAnaPre=new Vector(21);  //Almacena las cabeceras
            vecCabAnaPre.clear();
            vecCabAnaPre.add(INT_TBL_DAT_LIN,"");
            vecCabAnaPre.add(INT_TBL_DAT_COD_ITM,"Cód.Itm.");
            vecCabAnaPre.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.");
            vecCabAnaPre.add(INT_TBL_DAT_NOM_ITM,"Nombre");
            vecCabAnaPre.add(INT_TBL_DAT_CAN,"Cantidad");
            vecCabAnaPre.add(INT_TBL_DAT_STK_ACT,"Stock");
            vecCabAnaPre.add(INT_TBL_DAT_PRE_UNI,"Pre.Uni.");
            vecCabAnaPre.add(INT_TBL_DAT_POR_DES,"% Des.");
            vecCabAnaPre.add(INT_TBL_DAT_PRE_VEN,"Pre.Vta.");
            vecCabAnaPre.add(INT_TBL_DAT_TOT_FIL,"Total");
            vecCabAnaPre.add(INT_TBL_DAT_COS_UNI,"Cos.Uni.");
            vecCabAnaPre.add(INT_TBL_DAT_PRE_LIS,"Pre.Lis.");
            vecCabAnaPre.add(INT_TBL_DAT_PRE_LIS_ACT,"Pre.Lis.Act.");
            vecCabAnaPre.add(INT_TBL_DAT_PRE_COM,"Pre.Com.");
            vecCabAnaPre.add(INT_TBL_DAT_PRE_MIN,"Min.Pre.");
            vecCabAnaPre.add(INT_TBL_DAT_DEF_VAL,"Res.");
            vecCabAnaPre.add(INT_TBL_DAT_DEF_POR,"Res.(%)");
            vecCabAnaPre.add(INT_TBL_DAT_GAN_UNI_VAL,"Gan.Uni.Val");
            vecCabAnaPre.add(INT_TBL_DAT_GAN_TOT_VAL,"Gan.Tot.Val.");
            vecCabAnaPre.add(INT_TBL_DAT_MAR_UTI_ITM,"Mar.Itm.");
            vecCabAnaPre.add(INT_TBL_DAT_MAR_UTI_VEN,"Mar.Ven.");
            objTblModAnaPre=new ZafTblMod();
            objTblModAnaPre.setHeader(vecCabAnaPre);
            tblDatAnaPre.setModel(objTblModAnaPre);
            //Configurar JTable: Establecer tipo de selección.
            tblDatAnaPre.setRowSelectionAllowed(true);
            tblDatAnaPre.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatAnaPre);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatAnaPre.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatAnaPre.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PRE_UNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_POR_DES).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_PRE_VEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TOT_FIL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PRE_LIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PRE_LIS_ACT).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_PRE_COM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PRE_MIN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DEF_VAL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DEF_POR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_GAN_UNI_VAL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_GAN_TOT_VAL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_MAR_UTI_ITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MAR_UTI_VEN).setPreferredWidth(60);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_MOT).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatAnaPre.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModAnaPre.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDatAnaPre);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDatAnaPre.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDatAnaPre);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatAnaPre);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatAnaPre);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PRE_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_POR_DES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TOT_FIL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PRE_LIS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PRE_LIS_ACT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PRE_COM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DEF_VAL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DEF_POR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_GAN_UNI_VAL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_GAN_TOT_VAL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MAR_UTI_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MAR_UTI_VEN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLbl.setBackground(new java.awt.Color(255,207,159));
            tcmAux.getColumn(INT_TBL_DAT_PRE_VEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PRE_MIN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_TOT_FIL, INT_TBL_DAT_GAN_UNI_VAL, INT_TBL_DAT_GAN_TOT_VAL};
            objTblTot=new ZafTblTot(spnDatAnaPre, spnTotAnaPre, tblDatAnaPre, tblTotAnaPre, intCol);
            //Configurar JTable: Modo de operación del JTable.
            objTblModAnaPre.setModoOperacion(objTblModAnaPre.INT_TBL_EDI);
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
    private boolean cargarAnaPre()
    {
        BigDecimal bgdMaxPorDes, bgdMarUti, bgdCan, bgdPreUni, bgdPorDes, bgdPreUniVta, bgdCosUni, bgdPreLis, bgdPreCom, bgdMinPrePer;
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
                //Obtener los datos necesarios del cliente.
                strSQL="";
                strSQL+="SELECT a4.co_forPag AS co_forPagAsi, a4.tx_des AS tx_forPagAsi, a3.co_forPag AS co_forPagSol, a3.tx_des AS tx_forPagSol, a2.nd_maxDes";
                strSQL+=" FROM tbm_cabCotVen AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                strSQL+=" LEFT OUTER JOIN tbm_cabForPag AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_forPag=a3.co_forPag)";
                strSQL+=" LEFT OUTER JOIN tbm_cabForPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_forPag=a4.co_forPag)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_cot=" + intCodDoc;
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodForPagAsi.setText(rst.getString("co_forPagAsi"));
                    txtForPagAsi.setText(rst.getString("tx_forPagAsi"));
                    txtCodForPagSol.setText(rst.getString("co_forPagSol"));
                    txtForPagSol.setText(rst.getString("tx_forPagSol"));
                    bgdMaxPorDes=rst.getBigDecimal("nd_maxDes");
                    txtMaxDes.setText("" + bgdMaxPorDes);
                }
                else
                {
                    bgdMaxPorDes=BigDecimal.ZERO;
                }
                rst.close();
                rst=null;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.nd_can, a5.nd_stkAct, a1.nd_preUni, a1.nd_porDes, a3.nd_cosUni, a1.nd_preUniVenLis AS nd_preVta1, a4.nd_preVta1 AS nd_preVtaAct, a1.nd_preCom*(1-a1.nd_porDesPreCom/100) AS nd_preCom, a4.nd_marUti";
                strSQL+=" FROM tbm_detCotVen AS a1";
                strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                strSQL+=" INNER JOIN (";
                strSQL+=" SELECT b2.co_itmMae, b1.nd_cosUni";
                strSQL+=" FROM tbm_inv AS b1";
                strSQL+=" INNER JOIN tbm_equInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
                strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                strSQL+=" ) AS a3 ON (a2.co_itmMae=a3.co_itmMae)";
                strSQL+=" INNER JOIN tbm_inv AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_itm=a4.co_itm)";
                strSQL+=" INNER JOIN (";
                strSQL+=" SELECT c2.co_itmMae, SUM(c4.nd_stkAct) AS nd_stkAct";
                strSQL+=" FROM tbm_detCotVen AS c1";
                strSQL+=" INNER JOIN tbm_equInv AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
                strSQL+=" INNER JOIN tbm_equInv AS c3 ON (c2.co_itmMae=c3.co_itmMae)";
                strSQL+=" INNER JOIN tbm_inv AS c4 ON (c3.co_emp=c4.co_emp AND c3.co_itm=c4.co_itm)";
                strSQL+=" WHERE c1.co_emp=" + intCodEmp;
                strSQL+=" AND c1.co_loc=" + intCodLoc;
                strSQL+=" AND c1.co_cot=" + intCodDoc;
                strSQL+=" AND c3.co_emp<>" + objParSis.getCodigoEmpresaGrupo();;
                strSQL+=" GROUP BY c2.co_itmMae";
                strSQL+=" ) AS a5 ON (a2.co_itmMae=a5.co_itmMae)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_cot=" + intCodDoc;
                strSQL+=" ORDER BY a1.co_reg";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDatAnaPre.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        bgdCan=rst.getBigDecimal("nd_can");
                        vecReg.add(INT_TBL_DAT_CAN,"" + bgdCan);
                        vecReg.add(INT_TBL_DAT_STK_ACT,rst.getString("nd_stkAct"));
                        bgdPreUni=rst.getBigDecimal("nd_preUni");
                        vecReg.add(INT_TBL_DAT_PRE_UNI,"" + bgdPreUni);
                        bgdPorDes=rst.getBigDecimal("nd_porDes");
                        vecReg.add(INT_TBL_DAT_POR_DES,"" + bgdPorDes);
                        bgdPreUniVta=bgdPreUni.multiply(BigDecimal.ONE.subtract(bgdPorDes.divide(new BigDecimal("100"), objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP)));
                        vecReg.add(INT_TBL_DAT_PRE_VEN,"" + bgdPreUniVta);
                        vecReg.add(INT_TBL_DAT_TOT_FIL,"" + bgdCan.multiply(bgdPreUniVta));
                        bgdCosUni=rst.getBigDecimal("nd_cosUni");
                        vecReg.add(INT_TBL_DAT_COS_UNI,"" + bgdCosUni);
                        bgdPreLis=rst.getBigDecimal("nd_preVta1");
                        vecReg.add(INT_TBL_DAT_PRE_LIS,"" + bgdPreLis);
                        vecReg.add(INT_TBL_DAT_PRE_LIS_ACT,"" + rst.getString("nd_preVtaAct"));
                        bgdPreCom=rst.getBigDecimal("nd_preCom");
                        vecReg.add(INT_TBL_DAT_PRE_COM,"" + bgdPreCom);
                        bgdMarUti=rst.getBigDecimal("nd_marUti");
                        if (bgdPreCom.compareTo(BigDecimal.ZERO)>0)
                        {
                            //Si el precio de compra es mayor a cero utilizar el "Precio de compra".
                            bgdCosUni=bgdPreCom;
                            bgdMinPrePer=bgdCosUni.multiply(BigDecimal.ONE.add(bgdMarUti.divide(new BigDecimal("100"), objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP)));
                        }
                        else if (bgdPreLis.compareTo(BigDecimal.ZERO)>0)
                        {
                            //Si no hay precio de compra pero si hay precio de lista utilizar el "Precio de lista".
                            bgdMinPrePer=bgdPreLis.multiply(BigDecimal.ONE.subtract(bgdMaxPorDes.divide(new BigDecimal("100"), objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP)));
                        }
                        else
                        {
                            //Si no hay precio de compra y tampoco hay precio de lista utilizar el "Costo promedio".
                            bgdMinPrePer=bgdCosUni.multiply(BigDecimal.ONE.add(bgdMarUti.divide(new BigDecimal("100"), objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP)));
                        }
                        vecReg.add(INT_TBL_DAT_PRE_MIN,"" + bgdMinPrePer);
                        vecReg.add(INT_TBL_DAT_DEF_VAL,"" + bgdPreUniVta.subtract(bgdMinPrePer));
                        vecReg.add(INT_TBL_DAT_DEF_POR,"" + ((bgdPreUniVta.subtract(bgdMinPrePer)).divide(bgdPreUniVta, objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("100")));
                        vecReg.add(INT_TBL_DAT_GAN_UNI_VAL,"" + bgdPreUniVta.subtract(bgdCosUni));
                        vecReg.add(INT_TBL_DAT_GAN_TOT_VAL,"" + bgdCan.multiply(bgdPreUniVta.subtract(bgdCosUni)));
                        vecReg.add(INT_TBL_DAT_MAR_UTI_ITM,bgdMarUti);
                        vecReg.add(INT_TBL_DAT_MAR_UTI_VEN,"" + ((bgdPreUniVta.subtract(bgdCosUni)).divide(bgdPreUniVta, objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("100")));
                        vecDatAnaPre.add(vecReg);
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
                objTblModAnaPre.setData(vecDatAnaPre);
                tblDatAnaPre.setModel(objTblModAnaPre);
                vecDatAnaPre.clear();
                //Calcular totales.
                objTblTot.calcularTotales();
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDatAnaPre.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDatAnaPre.getRowCount() + " registros.");
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
            if (!cargarAnaPre())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDatAnaPre.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(0);
                tblDatAnaPre.setRowSelectionInterval(0, 0);
                tblDatAnaPre.requestFocus();
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
            int intCol=tblDatAnaPre.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_CAN:
                    strMsg="Cantidad";
                    break;
                case INT_TBL_DAT_STK_ACT:
                    strMsg="Stock actual (Grupo)";
                    break;
                case INT_TBL_DAT_PRE_UNI:
                    strMsg="Precio unitario";
                    break;
                case INT_TBL_DAT_POR_DES:
                    strMsg="Porcentaje de descuento";
                    break;
                case INT_TBL_DAT_PRE_VEN:
                    strMsg="Precio unitario de venta";
                    break;
                case INT_TBL_DAT_TOT_FIL:
                    strMsg="Total";
                    break;
                case INT_TBL_DAT_COS_UNI:
                    strMsg="Costo unitario";
                    break;
                case INT_TBL_DAT_PRE_LIS:
                    strMsg="Precio de lista que estaba vigente al momento de guardar la cotización";
                    break;
                case INT_TBL_DAT_PRE_LIS_ACT:
                    strMsg="Precio de lista actual";
                    break;
                case INT_TBL_DAT_PRE_COM:
                    strMsg="Precio de compra";
                    break;
                case INT_TBL_DAT_PRE_MIN:
                    strMsg="Mínimo precio de venta permitido";
                    break;
                case INT_TBL_DAT_DEF_VAL:
                    strMsg="Resultado=Pre.Vta.-Min.Pre.";
                    break;
                case INT_TBL_DAT_DEF_POR:
                    strMsg="Resultado (%)";
                    break;
                case INT_TBL_DAT_GAN_UNI_VAL:
                    strMsg="Ganancia unitaria";
                    break;
                case INT_TBL_DAT_GAN_TOT_VAL:
                    strMsg="Ganancia total";
                    break;
                case INT_TBL_DAT_MAR_UTI_ITM:
                    strMsg="Margen de utilidad del item";
                    break;
                case INT_TBL_DAT_MAR_UTI_VEN:
                    strMsg="Margen de utilidad obtenido en la venta";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDatAnaPre.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}
