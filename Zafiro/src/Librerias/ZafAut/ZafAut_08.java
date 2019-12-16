/*
 * ZafAut_08.java
 *
 * Created on 05 de julio de 2007, 12:13 PM
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
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author  Eddye Lino
 */
public class ZafAut_08 extends javax.swing.JDialog
{
    /*Simbología:
     *  VCC: Ventas de contado por cliente
     *  VCL: Ventas de contado por local
     **/
    //Constantes: Columnas del JTable:
    static final int INT_TBL_VCC_LIN=0;                        //Línea
    static final int INT_TBL_VCC_COD_EMP=1;                    //Código de la empresa.
    static final int INT_TBL_VCC_COD_LOC=2;                    //Código del local.
    static final int INT_TBL_VCC_COD_TIP_DOC=3;                //Código del tipo de documento.
    static final int INT_TBL_VCC_DEC_TIP_DOC=4;                //Descripción corta del tipo de documento.
    static final int INT_TBL_VCC_DEL_TIP_DOC=5;                //Descripción larga del tipo de documento.
    static final int INT_TBL_VCC_COD_DOC=6;                    //Código del documento (Sistema).
    static final int INT_TBL_VCC_COD_REG=7;                    //Código del registro (Sistema).
    static final int INT_TBL_VCC_NUM_DOC=8;                    //Número del documento.
    static final int INT_TBL_VCC_FEC_DOC=9;                    //Fecha del documento.
    static final int INT_TBL_VCC_VAL_DOC=10;                   //Valor del documento.
    static final int INT_TBL_VCC_VAL_ABO=11;                   //Valor abonado.
    static final int INT_TBL_VCC_VAL_PEN=12;                   //Valor pendiente.
    static final int INT_TBL_VCC_FEC_VEN_CHQ=13;               //Fecha de vencimiento del cheque.
    static final int INT_TBL_VCC_VAL_CHQ=14;                   //Valor del cheque.
    
    static final int INT_TBL_VCL_LIN=0;                        //Línea
    static final int INT_TBL_VCL_COD_EMP=1;                    //Código de la empresa.
    static final int INT_TBL_VCL_COD_LOC=2;                    //Código del local.
    static final int INT_TBL_VCL_COD_TIP_DOC=3;                //Código del tipo de documento.
    static final int INT_TBL_VCL_DEC_TIP_DOC=4;                //Descripción corta del tipo de documento.
    static final int INT_TBL_VCL_DEL_TIP_DOC=5;                //Descripción larga del tipo de documento.
    static final int INT_TBL_VCL_COD_DOC=6;                    //Código del documento (Sistema).
    static final int INT_TBL_VCL_COD_REG=7;                    //Código del registro (Sistema).
    static final int INT_TBL_VCL_NUM_DOC=8;                    //Número del documento.
    static final int INT_TBL_VCL_FEC_DOC=9;                    //Fecha del documento.
    static final int INT_TBL_VCL_COD_CLI=10;                   //Código del cliente.
    static final int INT_TBL_VCL_NOM_CLI=11;                   //Nombre del cliente.
    static final int INT_TBL_VCL_VAL_DOC=12;                   //Valor del documento.
    static final int INT_TBL_VCL_VAL_ABO=13;                   //Valor abonado.
    static final int INT_TBL_VCL_VAL_PEN=14;                   //Valor pendiente.
    static final int INT_TBL_VCL_FEC_VEN_CHQ=15;               //Fecha de vencimiento del cheque.
    static final int INT_TBL_VCL_VAL_CHQ=16;                   //Valor del cheque.
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblModVenConCli;
    private ZafTblMod objTblModVenConLoc;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                   //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                         //ToolTipText en TableHeader.
    private ZafMouMotAdaVenConLoc objMouMotAdaVenConLoc;       //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                         //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                               //Editor de búsqueda.
    private ZafTblTot objTblTot;                               //JTable de totales.
    private ZafTblTot objTblTotVenConLoc;                      //JTable de totales.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                             //true: Continua la ejecución del hilo.
    //Variables de la clase.
    private int intCodEmp;                              //Código de la empresa.
    private int intCodLoc;                              //Código del local.
    private int intCodTipDoc;                           //Código del tipo de documento.
    private int intCodDoc;                              //Código del docuemnto.
    
    /**
     * Este constructor crea una instancia de la clase ZafAut_08.
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
    public ZafAut_08(java.util.ArrayList parametros)
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
    private void initComponents() {//GEN-BEGIN:initComponents
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        sppGen = new javax.swing.JSplitPane();
        panGenVenConCli = new javax.swing.JPanel();
        panGenCabVenConCli = new javax.swing.JPanel();
        txtNumMaxVenConCli = new javax.swing.JTextField();
        lblNumMaxVenConCli = new javax.swing.JLabel();
        panGenDetVenConCli = new javax.swing.JPanel();
        spnDatVenConCli = new javax.swing.JScrollPane();
        tblDatVenConCli = new javax.swing.JTable();
        spnTotVenConCli = new javax.swing.JScrollPane();
        tblTotVenConCli = new javax.swing.JTable();
        panGenVenConLoc = new javax.swing.JPanel();
        panGenCabVenConLoc = new javax.swing.JPanel();
        lblNumMaxVenConLoc = new javax.swing.JLabel();
        txtNumMaxVenConLoc = new javax.swing.JTextField();
        lblMonMaxVenConLoc = new javax.swing.JLabel();
        txtMonMaxVenConLoc = new javax.swing.JTextField();
        panGenDetVenConLoc = new javax.swing.JPanel();
        spnDatVenConLoc = new javax.swing.JScrollPane();
        tblDatVenConLoc = new javax.swing.JTable();
        spnTotVenConLoc = new javax.swing.JScrollPane();
        tblTotVenConLoc = new javax.swing.JTable();
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
        lblTit.setText("T\u00edtulo de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        sppGen.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppGen.setResizeWeight(0.4);
        sppGen.setOneTouchExpandable(true);
        panGenVenConCli.setLayout(new java.awt.BorderLayout());

        panGenVenConCli.setBorder(new javax.swing.border.TitledBorder("Ventas de contado por cliente"));
        panGenCabVenConCli.setLayout(null);

        panGenCabVenConCli.setPreferredSize(new java.awt.Dimension(10, 24));
        txtNumMaxVenConCli.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCabVenConCli.add(txtNumMaxVenConCli);
        txtNumMaxVenConCli.setBounds(360, 4, 100, 20);

        lblNumMaxVenConCli.setText("N\u00famero m\u00e1ximo de ventas de contado permitido por cliente:");
        lblNumMaxVenConCli.setToolTipText("N\u00famero m\u00e1ximo de ventas de contado permitido");
        panGenCabVenConCli.add(lblNumMaxVenConCli);
        lblNumMaxVenConCli.setBounds(0, 4, 360, 20);

        panGenVenConCli.add(panGenCabVenConCli, java.awt.BorderLayout.NORTH);

        panGenDetVenConCli.setLayout(new java.awt.BorderLayout(0, 1));

        tblDatVenConCli.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDatVenConCli.setViewportView(tblDatVenConCli);

        panGenDetVenConCli.add(spnDatVenConCli, java.awt.BorderLayout.CENTER);

        spnTotVenConCli.setPreferredSize(new java.awt.Dimension(454, 18));
        tblTotVenConCli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTotVenConCli.setViewportView(tblTotVenConCli);

        panGenDetVenConCli.add(spnTotVenConCli, java.awt.BorderLayout.SOUTH);

        panGenVenConCli.add(panGenDetVenConCli, java.awt.BorderLayout.CENTER);

        sppGen.setLeftComponent(panGenVenConCli);

        panGenVenConLoc.setLayout(new java.awt.BorderLayout());

        panGenVenConLoc.setBorder(new javax.swing.border.TitledBorder("Ventas de contado por local"));
        panGenCabVenConLoc.setLayout(null);

        panGenCabVenConLoc.setPreferredSize(new java.awt.Dimension(10, 44));
        lblNumMaxVenConLoc.setText("N\u00famero m\u00e1ximo de ventas de contado permitido por local:");
        lblNumMaxVenConLoc.setToolTipText("N\u00famero m\u00e1ximo de ventas de contado permitido");
        panGenCabVenConLoc.add(lblNumMaxVenConLoc);
        lblNumMaxVenConLoc.setBounds(0, 4, 360, 20);

        txtNumMaxVenConLoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCabVenConLoc.add(txtNumMaxVenConLoc);
        txtNumMaxVenConLoc.setBounds(360, 4, 100, 20);

        lblMonMaxVenConLoc.setText("Monto m\u00e1ximo de ventas de contado permitido por local:");
        lblMonMaxVenConLoc.setToolTipText("N\u00famero m\u00e1ximo de ventas de contado permitido");
        panGenCabVenConLoc.add(lblMonMaxVenConLoc);
        lblMonMaxVenConLoc.setBounds(0, 24, 360, 20);

        txtMonMaxVenConLoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCabVenConLoc.add(txtMonMaxVenConLoc);
        txtMonMaxVenConLoc.setBounds(360, 24, 100, 20);

        panGenVenConLoc.add(panGenCabVenConLoc, java.awt.BorderLayout.NORTH);

        panGenDetVenConLoc.setLayout(new java.awt.BorderLayout(0, 1));

        tblDatVenConLoc.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDatVenConLoc.setViewportView(tblDatVenConLoc);

        panGenDetVenConLoc.add(spnDatVenConLoc, java.awt.BorderLayout.CENTER);

        spnTotVenConLoc.setPreferredSize(new java.awt.Dimension(454, 18));
        tblTotVenConLoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTotVenConLoc.setViewportView(tblTotVenConLoc);

        panGenDetVenConLoc.add(spnTotVenConLoc, java.awt.BorderLayout.SOUTH);

        panGenVenConLoc.add(panGenDetVenConLoc, java.awt.BorderLayout.CENTER);

        sppGen.setRightComponent(panGenVenConLoc);

        tabFrm.addTab("General", sppGen);

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

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

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
    private javax.swing.JLabel lblMonMaxVenConLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNumMaxVenConCli;
    private javax.swing.JLabel lblNumMaxVenConLoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenCabVenConCli;
    private javax.swing.JPanel panGenCabVenConLoc;
    private javax.swing.JPanel panGenDetVenConCli;
    private javax.swing.JPanel panGenDetVenConLoc;
    private javax.swing.JPanel panGenVenConCli;
    private javax.swing.JPanel panGenVenConLoc;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDatVenConCli;
    private javax.swing.JScrollPane spnDatVenConLoc;
    private javax.swing.JScrollPane spnTotVenConCli;
    private javax.swing.JScrollPane spnTotVenConLoc;
    private javax.swing.JSplitPane sppGen;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDatVenConCli;
    private javax.swing.JTable tblDatVenConLoc;
    private javax.swing.JTable tblTotVenConCli;
    private javax.swing.JTable tblTotVenConLoc;
    private javax.swing.JTextField txtMonMaxVenConLoc;
    private javax.swing.JTextField txtNumMaxVenConCli;
    private javax.swing.JTextField txtNumMaxVenConLoc;
    // End of variables declaration//GEN-END:variables
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux="Listado de ventas de contado";
            this.setTitle(strAux + " v0.6");
            lblTit.setText(strAux);
            //Configurar objetos.
            txtNumMaxVenConCli.setEditable(false);
            txtNumMaxVenConLoc.setEditable(false);
            txtMonMaxVenConLoc.setEditable(false);
            //Configurar los JTables.
            configurarTblDatVenConCli();
            configurarTblDatVenConLoc();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura el JTable "tblDatVenConCli".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatVenConCli()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(15);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_VCC_LIN,"");
            vecCab.add(INT_TBL_VCC_COD_EMP,"Cód.Cli.");
            vecCab.add(INT_TBL_VCC_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_VCC_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_VCC_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_VCC_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_VCC_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_VCC_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_VCC_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_VCC_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_VCC_VAL_DOC,"Val.Doc.");
            vecCab.add(INT_TBL_VCC_VAL_ABO,"Val.Abo.");
            vecCab.add(INT_TBL_VCC_VAL_PEN,"Val.Pen.");
            vecCab.add(INT_TBL_VCC_FEC_VEN_CHQ,"Fec.Ven.Chq.");
            vecCab.add(INT_TBL_VCC_VAL_CHQ,"Val.Chq.");
            objTblModVenConCli=new ZafTblMod();
            objTblModVenConCli.setHeader(vecCab);
            tblDatVenConCli.setModel(objTblModVenConCli);
            //Configurar JTable: Establecer tipo de selección.
            tblDatVenConCli.setRowSelectionAllowed(true);
            tblDatVenConCli.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatVenConCli);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatVenConCli.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatVenConCli.getColumnModel();
            tcmAux.getColumn(INT_TBL_VCC_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_VCC_DEC_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_VCC_DEL_TIP_DOC).setPreferredWidth(22);
            tcmAux.getColumn(INT_TBL_VCC_NUM_DOC).setPreferredWidth(59);
            tcmAux.getColumn(INT_TBL_VCC_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_VCC_VAL_DOC).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_VCC_VAL_ABO).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_VCC_VAL_PEN).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_VCC_VAL_CHQ).setPreferredWidth(65);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_VCC_BUT_MOT).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatVenConCli.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModVenConCli.addSystemHiddenColumn(INT_TBL_VCC_COD_EMP, tblDatVenConCli);
            objTblModVenConCli.addSystemHiddenColumn(INT_TBL_VCC_COD_LOC, tblDatVenConCli);
            objTblModVenConCli.addSystemHiddenColumn(INT_TBL_VCC_COD_TIP_DOC, tblDatVenConCli);
            objTblModVenConCli.addSystemHiddenColumn(INT_TBL_VCC_DEL_TIP_DOC, tblDatVenConCli);
            objTblModVenConCli.addSystemHiddenColumn(INT_TBL_VCC_COD_DOC, tblDatVenConCli);
            objTblModVenConCli.addSystemHiddenColumn(INT_TBL_VCC_COD_REG, tblDatVenConCli);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDatVenConCli.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDatVenConCli);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatVenConCli);
            tcmAux.getColumn(INT_TBL_VCC_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatVenConCli);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_VCC_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VCC_VAL_ABO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VCC_VAL_PEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VCC_VAL_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_VCC_VAL_DOC, INT_TBL_VCC_VAL_ABO, INT_TBL_VCC_VAL_PEN};
            objTblTot=new ZafTblTot(spnDatVenConCli, spnTotVenConCli, tblDatVenConCli, tblTotVenConCli, intCol);
            //Configurar JTable: Modo de operación del JTable.
            objTblModVenConCli.setModoOperacion(objTblModVenConCli.INT_TBL_EDI);
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
     * Esta función configura el JTable "tblDatVenConLoc".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatVenConLoc()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(17);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_VCL_LIN,"");
            vecCab.add(INT_TBL_VCL_COD_EMP,"Cód.Cli.");
            vecCab.add(INT_TBL_VCL_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_VCL_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_VCL_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_VCL_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_VCL_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_VCL_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_VCL_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_VCL_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_VCL_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_VCL_NOM_CLI,"Nom.Cli.");
            vecCab.add(INT_TBL_VCL_VAL_DOC,"Val.Doc.");
            vecCab.add(INT_TBL_VCL_VAL_ABO,"Val.Abo.");
            vecCab.add(INT_TBL_VCL_VAL_PEN,"Val.Pen.");
            vecCab.add(INT_TBL_VCL_FEC_VEN_CHQ,"Fec.Ven.Chq.");
            vecCab.add(INT_TBL_VCL_VAL_CHQ,"Val.Chq.");
            objTblModVenConLoc=new ZafTblMod();
            objTblModVenConLoc.setHeader(vecCab);
            tblDatVenConLoc.setModel(objTblModVenConLoc);
            //Configurar JTable: Establecer tipo de selección.
            tblDatVenConLoc.setRowSelectionAllowed(true);
            tblDatVenConLoc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatVenConLoc);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatVenConLoc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatVenConLoc.getColumnModel();
            tcmAux.getColumn(INT_TBL_VCL_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_VCL_DEC_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_VCL_DEL_TIP_DOC).setPreferredWidth(22);
            tcmAux.getColumn(INT_TBL_VCL_NUM_DOC).setPreferredWidth(59);
            tcmAux.getColumn(INT_TBL_VCL_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_VCL_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_VCL_NOM_CLI).setPreferredWidth(58);
            tcmAux.getColumn(INT_TBL_VCL_VAL_DOC).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_VCL_VAL_ABO).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_VCL_VAL_PEN).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_VCL_VAL_CHQ).setPreferredWidth(65);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_VCL_BUT_MOT).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatVenConLoc.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModVenConLoc.addSystemHiddenColumn(INT_TBL_VCL_COD_EMP, tblDatVenConLoc);
            objTblModVenConLoc.addSystemHiddenColumn(INT_TBL_VCL_COD_LOC, tblDatVenConLoc);
            objTblModVenConLoc.addSystemHiddenColumn(INT_TBL_VCL_COD_TIP_DOC, tblDatVenConLoc);
            objTblModVenConLoc.addSystemHiddenColumn(INT_TBL_VCL_DEL_TIP_DOC, tblDatVenConLoc);
            objTblModVenConLoc.addSystemHiddenColumn(INT_TBL_VCL_COD_DOC, tblDatVenConLoc);
            objTblModVenConLoc.addSystemHiddenColumn(INT_TBL_VCL_COD_REG, tblDatVenConLoc);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaVenConLoc=new ZafMouMotAdaVenConLoc();
            tblDatVenConLoc.getTableHeader().addMouseMotionListener(objMouMotAdaVenConLoc);
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDatVenConLoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatVenConLoc);
            tcmAux.getColumn(INT_TBL_VCL_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatVenConLoc);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_VCL_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VCL_VAL_ABO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VCL_VAL_PEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VCL_VAL_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_VCL_VAL_DOC, INT_TBL_VCL_VAL_ABO, INT_TBL_VCL_VAL_PEN};
            objTblTotVenConLoc=new ZafTblTot(spnDatVenConLoc, spnTotVenConLoc, tblDatVenConLoc, tblTotVenConLoc, intCol);
            //Configurar JTable: Modo de operación del JTable.
            objTblModVenConLoc.setModoOperacion(objTblModVenConLoc.INT_TBL_EDI);
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
    private boolean cargarVenConCli()
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
                //Obtener los datos necesarios del cliente.
                strSQL="";
                strSQL+="SELECT a1.ne_numMaxVenCon";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_cli=(";
                strSQL+=" SELECT co_cli";
                strSQL+=" FROM tbm_cabCotVen";
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_cot=" + intCodDoc;
                strSQL+=" )";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtNumMaxVenConCli.setText("" + rst.getInt("ne_numMaxVenCon"));
                }
                else
                {
                    txtNumMaxVenConCli.setText("0");
                }
                rst.close();
                rst=null;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, ABS(a2.mo_pag) AS mo_pag, ABS(a2.nd_abo) AS nd_abo";
                strSQL+=", -(a2.mo_pag+a2.nd_abo+(CASE WHEN a2.nd_monChq IS NULL THEN 0 ELSE (CASE WHEN " + objParSis.getFuncionFechaHoraBaseDatos() + "<a2.fe_venChq THEN 0 ELSE a2.nd_monChq END) END)) AS nd_pen, a2.fe_venChq, a2.nd_monChq";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_cli=";
                strSQL+=" (";
                strSQL+=" SELECT co_cli";
                strSQL+=" FROM tbm_cabCotVen";
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_cot=" + intCodDoc;
                strSQL+=" )";
                strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<0";
                strSQL+=" AND a2.ne_diaCre=0 AND a2.nd_porRet=0";
                strSQL+=" AND a4.ne_diaGra=0";
                strSQL+=" AND a1.st_imp='S'";
                strSQL+=" AND (a1.st_excDocConVenCon IS NULL OR a1.st_excDocConVenCon='N')";
                strSQL+=" AND (a2.fe_venChq IS NULL OR a2.nd_monChq IS NULL OR " + objParSis.getFuncionFechaHoraBaseDatos() + "<a2.fe_venChq OR (a2.mo_pag+a2.nd_monChq)<0)";
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
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
                        vecReg.add(INT_TBL_VCC_LIN,"");
                        vecReg.add(INT_TBL_VCC_COD_EMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_VCC_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_VCC_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_VCC_DEC_TIP_DOC,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_VCC_DEL_TIP_DOC,rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_VCC_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_VCC_COD_REG,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_VCC_NUM_DOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_VCC_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_VCC_VAL_DOC,rst.getString("mo_pag"));
                        vecReg.add(INT_TBL_VCC_VAL_ABO,rst.getString("nd_abo"));
                        vecReg.add(INT_TBL_VCC_VAL_PEN,rst.getString("nd_pen"));
                        vecReg.add(INT_TBL_VCC_FEC_VEN_CHQ,rst.getString("fe_venChq"));
                        vecReg.add(INT_TBL_VCC_VAL_CHQ,rst.getString("nd_monChq"));
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
                objTblModVenConCli.setData(vecDat);
                tblDatVenConCli.setModel(objTblModVenConCli);
                vecDat.clear();
                //Calcular totales.
                objTblTot.calcularTotales();
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDatVenConCli.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDatVenConCli.getRowCount() + " registros.");
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarVenConLoc()
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
                //Obtener los datos necesarios del local.
                strSQL="";
                strSQL+="SELECT a1.ne_numMaxVenCon, a1.nd_monMaxVenCon";
                strSQL+=" FROM tbm_loc AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtNumMaxVenConLoc.setText("" + rst.getInt("ne_numMaxVenCon"));
                    txtMonMaxVenConLoc.setText("" + rst.getDouble("nd_monMaxVenCon"));
                }
                else
                {
                    txtNumMaxVenConLoc.setText("0");
                    txtMonMaxVenConLoc.setText("0");
                }
                rst.close();
                rst=null;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a1.co_cli, a1.tx_nomCli, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, ABS(a2.mo_pag) AS mo_pag, ABS(a2.nd_abo) AS nd_abo";
                strSQL+=", -(a2.mo_pag+a2.nd_abo+(CASE WHEN a2.nd_monChq IS NULL THEN 0 ELSE (CASE WHEN " + objParSis.getFuncionFechaHoraBaseDatos() + "<a2.fe_venChq THEN 0 ELSE a2.nd_monChq END) END)) AS nd_pen, a2.fe_venChq, a2.nd_monChq";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<0";
                strSQL+=" AND a2.ne_diaCre=0 AND a2.nd_porRet=0";
                strSQL+=" AND a3.ne_mod=1";
                strSQL+=" AND a4.ne_diaGra=0";
                strSQL+=" AND a1.st_imp='S'";
                strSQL+=" AND (a1.st_excDocConVenCon IS NULL OR a1.st_excDocConVenCon='N')";
                strSQL+=" AND (a2.fe_venChq IS NULL OR a2.nd_monChq IS NULL OR " + objParSis.getFuncionFechaHoraBaseDatos() + "<a2.fe_venChq OR (a2.mo_pag+a2.nd_monChq)<0)";
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
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
                        vecReg.add(INT_TBL_VCL_LIN,"");
                        vecReg.add(INT_TBL_VCL_COD_EMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_VCL_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_VCL_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_VCL_DEC_TIP_DOC,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_VCL_DEL_TIP_DOC,rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_VCL_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_VCL_COD_REG,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_VCL_NUM_DOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_VCL_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_VCL_COD_CLI,rst.getString("co_cli"));
                        vecReg.add(INT_TBL_VCL_NOM_CLI,rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_VCL_VAL_DOC,rst.getString("mo_pag"));
                        vecReg.add(INT_TBL_VCL_VAL_ABO,rst.getString("nd_abo"));
                        vecReg.add(INT_TBL_VCL_VAL_PEN,rst.getString("nd_pen"));
                        vecReg.add(INT_TBL_VCL_FEC_VEN_CHQ,rst.getString("fe_venChq"));
                        vecReg.add(INT_TBL_VCL_VAL_CHQ,rst.getString("nd_monChq"));
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
                objTblModVenConLoc.setData(vecDat);
                tblDatVenConLoc.setModel(objTblModVenConLoc);
                vecDat.clear();
                //Calcular totales.
                objTblTotVenConLoc.calcularTotales();
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDatVenConLoc.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDatVenConLoc.getRowCount() + " registros.");
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
            if (!cargarVenConCli())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            if (!cargarVenConLoc())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDatVenConCli.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(0);
                tblDatVenConCli.setRowSelectionInterval(0, 0);
                tblDatVenConCli.requestFocus();
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
            int intCol=tblDatVenConCli.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_VCC_LIN:
                    strMsg="";
                    break;
                case INT_TBL_VCC_DEC_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_VCC_DEL_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_VCC_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_VCC_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_VCC_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                case INT_TBL_VCC_VAL_ABO:
                    strMsg="Valor abonado";
                    break;
                case INT_TBL_VCC_VAL_PEN:
                    strMsg="Valor pendiente";
                    break;
                case INT_TBL_VCC_FEC_VEN_CHQ:
                    strMsg="Fecha de vencimiento del cheque";
                    break;
                case INT_TBL_VCC_VAL_CHQ:
                    strMsg="Valor del cheque";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDatVenConCli.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaVenConLoc extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDatVenConLoc.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_VCL_LIN:
                    strMsg="";
                    break;
                case INT_TBL_VCL_DEC_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_VCL_DEL_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_VCL_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_VCL_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_VCL_COD_CLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_VCL_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_VCL_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                case INT_TBL_VCL_VAL_ABO:
                    strMsg="Valor abonado";
                    break;
                case INT_TBL_VCL_VAL_PEN:
                    strMsg="Valor pendiente";
                    break;
                case INT_TBL_VCL_FEC_VEN_CHQ:
                    strMsg="Fecha de vencimiento del cheque";
                    break;
                case INT_TBL_VCL_VAL_CHQ:
                    strMsg="Valor del cheque";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDatVenConLoc.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}
