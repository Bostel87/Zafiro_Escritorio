/*
 * ZafVen15.java
 *
 * Created on 18 de mayo de 2009, 16:15 PM
 */
package Ventas.ZafVen15;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author  Eddye Lino
 */
public class ZafVen15 extends javax.swing.JInternalFrame
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_BOD_LIN=0;                         //Línea.
    static final int INT_TBL_BOD_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_BOD_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_BOD_NOM_EMP=3;                     //Nombre de la empresa.
    static final int INT_TBL_BOD_COD_BOD=4;                     //Código de la bodega.
    static final int INT_TBL_BOD_NOM_BOD=5;                     //Nombre de la bodega.
    
    static final int INT_TBL_DAT_NUM_TOT_CES=7;                 //Número total de columnas estáticas.
    static final int INT_TBL_DAT_NUM_TOT_CDI=3;                 //Número total de columnas dinámicas.

    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_MAE=1;                     //Código maestro del item.
    static final int INT_TBL_DAT_COD_SIS=2;                     //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_ALT=3;                     //Código del item (Alterno).
    static final int INT_TBL_DAT_NOM_ITM=4;                     //Nombre del item.
    static final int INT_TBL_DAT_DEC_UNI=5;                     //Descripción corta de la unidad de medida.
    static final int INT_TBL_DAT_PRE_UNI_VT1=6;                 //Precio unitario de venta 1.
    static final int INT_TBL_DAT_CDI_DIS=7;                     //Columna dinámica: Cantidad Disponible
    static final int INT_TBL_DAT_CDI_CAN_ING=8;                 //Columna dinámica: Cantidad por ingresar a Bodega.
    static final int INT_TBL_DAT_CDI_CAN_EGR=9;                 //Columna dinámica: Cantidad por egresar de Bodega.
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModBod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafVenCon vcoItm;                                   //Ventana de consulta "Item".
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodAlt, strNomItm;                        //Contenido del campo al obtener el foco.
    private boolean blnMarTodChkTblBod=true;                    //Marcar todas las casillas de verificación del JTable de bodegas.
    
    
    
    
    
    
    private int intNumColAddPedEmb, intNumColIniPedEmb, intNumColFinPedEmb;//numero de columnas que se adicionan pedidos de importaciones
    //contiene la informacion de los nombres de los pedidos embarcados
    private ArrayList arlRegPedEmb, arlDatPedEmb;
    private final int INT_ARL_PED_EMB_COD_EMP=0;
    private final int INT_ARL_PED_EMB_COD_LOC=1;
    private final int INT_ARL_PED_EMB_COD_TIP_DOC=2;
    private final int INT_ARL_PED_EMB_COD_DOC=3;
    private final int INT_ARL_PED_EMB_NUM_PED=4;
    private final int INT_ARL_PED_EMB_NUM_COL=5;
    private final int INT_ARL_PED_EMB_EST_COL=6;
    private final int INT_ARL_PED_EMB_FEC_EST_PED_EMB=7;
    
    //contiene la informacion de los valores ingresados para cada item en los pedidos embarcados
    private ArrayList arlRegCanPedEmb, arlDatCanPedEmb;
    private final int INT_ARL_CAN_PED_EMB_COD_EMP=0;
    private final int INT_ARL_CAN_PED_EMB_COD_LOC=1;
    private final int INT_ARL_CAN_PED_EMB_COD_TIP_DOC=2;
    private final int INT_ARL_CAN_PED_EMB_COD_DOC=3;
    private final int INT_ARL_CAN_PED_EMB_COD_ITM=4;
    private final int INT_ARL_CAN_PED_EMB_CAN_PED_EMB=5;
    private final int INT_ARL_CAN_PED_EMB_COD_ALT_ITM=6;
    private final int INT_ARL_CAN_PED_EMB_EST=7;
    private final int INT_ARL_CAN_PED_EMB_NUM_COL=8;
    private final int INT_ARL_CAN_PED_EMB_COD_ITM_MAE=9;
    
   
    /** Crea una nueva instancia de la clase ZafVen15. */
    public ZafVen15(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            arlDatPedEmb=new ArrayList();
            arlDatCanPedEmb=new ArrayList();
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
        panRngCodAltItm = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        lblItm = new javax.swing.JLabel();
        txtCodAlt = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        txtCodItm = new javax.swing.JTextField();
        panBod = new javax.swing.JPanel();
        spnBod = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        chkMosItmPre = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
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

        panRngCodAltItm.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panRngCodAltItm.setLayout(null);

        lblCodAltDes.setText("Desde:");
        panRngCodAltItm.add(lblCodAltDes);
        lblCodAltDes.setBounds(12, 20, 44, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        panRngCodAltItm.add(txtCodAltDes);
        txtCodAltDes.setBounds(56, 20, 268, 20);

        lblCodAltHas.setText("Hasta:");
        panRngCodAltItm.add(lblCodAltHas);
        lblCodAltHas.setBounds(336, 20, 44, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        panRngCodAltItm.add(txtCodAltHas);
        txtCodAltHas.setBounds(380, 20, 268, 20);

        panFil.add(panRngCodAltItm);
        panRngCodAltItm.setBounds(24, 64, 660, 52);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Beneficiario");
        panFil.add(lblItm);
        lblItm.setBounds(24, 44, 120, 20);

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
        panFil.add(txtCodItm);
        txtCodItm.setBounds(88, 44, 56, 20);

        panBod.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de bodegas"));
        panBod.setLayout(new java.awt.BorderLayout());

        tblBod.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnBod.setViewportView(tblBod);

        panBod.add(spnBod, java.awt.BorderLayout.CENTER);

        panFil.add(panBod);
        panBod.setBounds(24, 116, 660, 92);

        chkMosItmPre.setText("Mostrar sólo los items que tengan precio");
        panFil.add(chkMosItmPre);
        chkMosItmPre.setBounds(30, 220, 470, 23);

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

    private void txtCodAltHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusLost
        if (txtCodAltHas.getText().length()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltHasFocusLost

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if (txtCodAltDes.getText().length()>0)
        {
            optFil.setSelected(true);
            if (txtCodAltHas.getText().length()==0)
                txtCodAltHas.setText(txtCodAltDes.getText());
        }
    }//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusGained
        txtCodAltHas.selectAll();
    }//GEN-LAST:event_txtCodAltHasFocusGained

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtNomItm.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
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

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butItm;
    private javax.swing.JCheckBox chkMosItmPre;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBod;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRngCodAltItm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBod;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBod;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
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
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.6.1");
            lblTit.setText(strAux);
            //Configurar objetos.
            txtCodItm.setVisible(false);
            //Configurar las ZafVenCon.
            configurarVenConItm();
            //Configurar los JTables.
            configurarTblBod();
            configurarTblDat();
            cargarBod();
            if(objParSis.getCodigoMenu()==846)
                cargarPedidosEmbarcadosConfigurados();
            agregarColTblDat();
            if(objParSis.getCodigoMenu()==846)
                agregarColumnasPedidosEmbarcados();
            
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);            
            
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblBod".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblBod()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_BOD_LIN,"");
            vecCab.add(INT_TBL_BOD_CHK,"");
            vecCab.add(INT_TBL_BOD_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_BOD_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_BOD_COD_BOD,"Cód.Bod.");
            vecCab.add(INT_TBL_BOD_NOM_BOD,"Bodega");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModBod=new ZafTblMod();
            objTblModBod.setHeader(vecCab);
            tblBod.setModel(objTblModBod);
            //Configurar JTable: Establecer tipo de selección.
            tblBod.setRowSelectionAllowed(true);
            tblBod.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblBod);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblBod.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblBod.getColumnModel();
            tcmAux.getColumn(INT_TBL_BOD_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BOD_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BOD_NOM_EMP).setPreferredWidth(231);
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BOD_NOM_BOD).setPreferredWidth(231);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BOD_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblBod.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModBod.addSystemHiddenColumn(INT_TBL_BOD_COD_EMP, tblBod);
            objTblModBod.addSystemHiddenColumn(INT_TBL_BOD_NOM_EMP, tblBod);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblBod.getTableHeader().addMouseMotionListener(new ZafMouMotAdaBod());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblBod.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblBodMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_BOD_CHK);
            objTblModBod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblBod);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_BOD_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int i;
                    i=tblBod.getSelectedRow();
                    if (objTblCelEdiChk.isChecked())
                    {
                        //Mostrar columnas.
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_DIS+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        switch (objParSis.getCodigoMenu())
                        {
                            case 846: //Lista de precios (Stock físico por bodegas)...
                                break;
                            case 910: //Listado de stock físico de inventario por bodegas...
                            default:
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_ING+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_EGR+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                                break;
                        }
                    }
                    else
                    {
                        //Ocultar columnas.
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DIS+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        switch (objParSis.getCodigoMenu())
                        {
                            case 846: //Lista de precios (Stock físico por bodegas)...
                                break;
                            case 910: //Listado de stock físico de inventario por bodegas...
                            default:
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_ING+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_EGR+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                                break;
                        }
                    }
                }
            });
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblBod.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModBod.setModoOperacion(objTblModBod.INT_TBL_EDI);
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
            vecCab=new Vector(INT_TBL_DAT_NUM_TOT_CES);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_MAE,"Cód.Mae.");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre");
            vecCab.add(INT_TBL_DAT_DEC_UNI,"Unidad");
            vecCab.add(INT_TBL_DAT_PRE_UNI_VT1,"Pre.Uni.Vta.1");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            //Si es el usuario Administrador (Código=1) habilito el ZafTblPopMnu.
            if (objParSis.getCodigoUsuario()==1)
            {
                objTblPopMnu=new ZafTblPopMnu(tblDat);
            }
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(257);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_PRE_UNI_VT1).setPreferredWidth(60);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SIS, tblDat);
            switch (objParSis.getCodigoMenu())
            {
                case 846: //Lista de precios (Stock físico por bodegas)...
                    break;
                case 910: //Listado de stock físico de inventario por bodegas...
                default:
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_PRE_UNI_VT1, tblDat);
                    break;
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
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_PRE_UNI_VT1).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
//            //Configurar JTable: Establecer la clase que controla el ordenamiento.
//            objTblOrd=new ZafTblOrd(tblDat);
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
     * Esta función permite consultar las bodegas de acuerdo al siguiente criterio:
     * El listado de bodegas se presenta en función de la empresa a la que se ingresa (Empresa Grupo u otra empresa)
     * , el usuario que ingresa (Administrador u otro usuario) y el menú desde el cual es llamado  (237, 886 o 907).
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarBod()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                    strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" SELECT b2.co_empGrp AS co_emp, b2.co_bodGrp AS co_bod";
                    strSQL+=" FROM tbr_bodLocPrgUsr AS b1";
                    strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_bod=b2.co_bod)";
                    strSQL+=" WHERE b1.co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND b1.co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" AND b1.st_reg IN ('A','P')";
                    strSQL+=" GROUP BY b2.co_empGrp, b2.co_bodGrp";
                    strSQL+=" ) AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod)";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                    rst=stm.executeQuery(strSQL);
                }
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_BOD_LIN,"");
                    vecReg.add(INT_TBL_BOD_CHK,new Boolean(true));
                    vecReg.add(INT_TBL_BOD_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_BOD_NOM_EMP,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_BOD_COD_BOD,rst.getString("co_bod"));
                    vecReg.add(INT_TBL_BOD_NOM_BOD,rst.getString("a2_tx_nom"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModBod.setData(vecDat);
                tblBod.setModel(objTblModBod);
                vecDat.clear();
                blnMarTodChkTblBod=false;
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
     * Esta función permite agregar columnas al "tblDat" de acuerdo al "tblEmp".
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColTblDat()
    {
        int i, intNumFil, intNumColTblDat;
        javax.swing.table.TableColumn tbc;
        String strCodBod="";
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColGrpBod=null;
        java.awt.Color colFonCol;
        boolean blnRes=true;
        try
        {
            intNumFil=objTblModBod.getRowCountTrue();
            intNumColTblDat=objTblMod.getColumnCount();
            for (i=0; i<intNumFil; i++)
            {
                //Establecer el color de fondo de las columnas.
                if ((i%2)==0)
                    colFonCol=new java.awt.Color(228,228,203);
                else
                    colFonCol=new java.awt.Color(255,221,187);

                tbc=new javax.swing.table.TableColumn(INT_TBL_DAT_CDI_DIS+i*INT_TBL_DAT_NUM_TOT_CDI);
                tbc.setHeaderValue("Can.Dis.");
                //Configurar JTable: Establecer el ancho de la columna.
                switch (objParSis.getCodigoMenu())
                {
                    case 846: //Lista de precios (Stock físico por bodegas)...
                        tbc.setPreferredWidth(80);
                        break;
                    case 910: //Listado de stock físico de inventario por bodegas...
                    default:
                        tbc.setPreferredWidth(60);
                        break;
                }
                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                objTblCelRenLbl.setBackground(colFonCol);
                tbc.setCellRenderer(objTblCelRenLbl);
                objTblCelRenLbl=null;
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                tbc=new javax.swing.table.TableColumn(INT_TBL_DAT_CDI_CAN_ING+i*INT_TBL_DAT_NUM_TOT_CDI);
                tbc.setHeaderValue("Can.Ing.");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                objTblCelRenLbl.setBackground(colFonCol);
                tbc.setCellRenderer(objTblCelRenLbl);
                objTblCelRenLbl=null;
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                tbc=new javax.swing.table.TableColumn(INT_TBL_DAT_CDI_CAN_EGR+i*INT_TBL_DAT_NUM_TOT_CDI);
                tbc.setHeaderValue("Can.Egr.");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                objTblCelRenLbl.setBackground(colFonCol);
                tbc.setCellRenderer(objTblCelRenLbl);
                objTblCelRenLbl=null;
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                //Agrupar las columnas.
                if (!strCodBod.equals(objTblModBod.getValueAt(i, INT_TBL_BOD_COD_BOD).toString()))
                {
                    objTblHeaColGrpBod=new ZafTblHeaColGrp(objTblModBod.getValueAt(i, INT_TBL_BOD_NOM_BOD).toString());
                    objTblHeaColGrpBod.setHeight(16);
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpBod);
                    strCodBod=objTblModBod.getValueAt(i, INT_TBL_BOD_COD_BOD).toString();
                }
                objTblHeaColGrpBod.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CDI_DIS+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpBod.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CDI_CAN_ING+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpBod.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CDI_CAN_EGR+i*INT_TBL_DAT_NUM_TOT_CDI));
                //Ocultar las columnas si la empresa no está seleccionada.
                if (!objTblModBod.isChecked(i, INT_TBL_BOD_CHK))
                {
                    //Ocultar columnas.
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DIS+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_ING+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_EGR+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                }
                else
                {
                    switch (objParSis.getCodigoMenu())
                    {
                        case 846: //Lista de precios (Stock físico por bodegas)...
                            //Ocultar columnas.
                            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_ING+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_EGR+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                            break;
                        case 910: //Listado de stock físico de inventario por bodegas...
                            break;
                        default:
                            break;
                    }
                }
            }
            objTblCelRenLbl=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    

    private boolean agregarColumnasPedidosEmbarcados(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColGrpFecEstEmb=null;
//        ZafTblHeaColGrp objTblHeaColGrpFecEstEmb=null;

        intNumColAddPedEmb=arlDatPedEmb.size();//
        intNumColIniPedEmb=objTblMod.getColumnCount();//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomPed="";
        String strFecEstPedEmb="";//fecha estimada de pedidoEmbarcado
        try{
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
           
            for (int i=0; i<intNumColAddPedEmb; i++){
                strNomPed="" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_NUM_PED);
                strFecEstPedEmb="" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_FEC_EST_PED_EMB);
                    
                objUti.setStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_NUM_COL, "" + (intNumColIniPedEmb+i));                
                tbc=new javax.swing.table.TableColumn(intNumColIniPedEmb+i);
                tbc.setHeaderValue(strNomPed);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
               
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                objTblHeaColGrpFecEstEmb=new ZafTblHeaColGrp(strFecEstPedEmb);
                objTblHeaColGrpFecEstEmb.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpFecEstEmb);
                objTblHeaColGrpFecEstEmb.add(tbc);

            }
            
            //objTblCelRenLbl=null;
            intNumColFinPedEmb=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }


    private boolean cargarPedidosEmbarcadosConfigurados(){
        boolean blnRes=true;
        arlDatPedEmb.clear();
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.fe_doc, d1.tx_mesNotPed";
                strSQL+=" , d1.tx_numDoc2";
                strSQL+=" FROM(";
                strSQL+="	SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.fe_doc, c1.tx_mesNotPed, c1.tx_numDoc2";
                strSQL+="		, c1.nd_canNotPed, c1.nd_canPedEmb, c1.nd_canNotPedFal";
                strSQL+="	FROM(";
                strSQL+=" 		SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.tx_mesNotPed, b1.tx_numDoc2";
                strSQL+=" 		, b1.nd_canNotPed, CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END AS nd_canPedEmb";
                strSQL+=" 		, (b1.nd_canNotPed - ";
                strSQL+=" 			CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END";
                strSQL+=" 		  ) AS nd_canNotPedFal";
                strSQL+="		FROM(";
                strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a5.tx_deslar AS tx_mesNotPed, a1.tx_numDoc2";
                strSQL+=" 			, a2.co_emp AS co_empPedEmb, a2.co_loc AS co_locPedEmb, a2.co_tipDoc AS co_tipDocPedEmb, a2.co_doc AS co_docPedEmb, a2.co_reg AS co_regPedEmb";
                strSQL+=" 			, a4.nd_can AS nd_canNotPed, a2.nd_can AS nd_canPedEmb";
                //strSQL+="			FROM (tbm_cabNotPedImp AS a1 INNER JOIN tbm_mesEmbImp AS a5 ON a1.co_mesemb=a5.co_mesemb)";
                strSQL+="			FROM (tbm_cabNotPedImp AS a1 INNER JOIN tbm_mesEmbImp AS a5 ON a1.co_mesArr=a5.co_mesemb)";//la fecha estiamada se presenta en base a ARRIBO
                strSQL+="			INNER JOIN tbm_detNotPedImp AS a4";
                strSQL+=" 			ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc AND a1.co_doc=a4.co_doc";
                strSQL+="			LEFT OUTER JOIN ";
                strSQL+="				(tbm_detPedEmbImp AS a2 INNER JOIN tbm_cabPedEmbImp AS a3";
                strSQL+="				 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+="				 AND a3.st_reg NOT IN('I','E')";
                strSQL+="				)";
                strSQL+="			ON a4.co_emp=a2.co_emp AND a4.co_loc=a2.co_locRel AND a4.co_tipDoc=a2.co_tipdocRel ";
                strSQL+="			AND a4.co_doc=a2.co_docRel AND a4.co_reg=a2.co_regRel";
                strSQL+="			WHERE a1.st_reg NOT IN('E','I')  AND a1.st_cie='N'       ";
                strSQL+="			GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a5.tx_deslar, a1.tx_numDoc2";
                strSQL+="			, a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a4.nd_can, a2.nd_can";
                strSQL+="			ORDER BY a1.co_doc";
                strSQL+="		) AS b1";
                strSQL+="		GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.tx_mesNotPed, b1.tx_numDoc2, b1.nd_canNotPed";
                strSQL+="	) AS c1";
                strSQL+=" 	WHERE c1.nd_canNotPedFal > 0";
                strSQL+=" ) AS d1";
                strSQL+=" WHERE d1.nd_canNotPedFal > 0";
                strSQL+=" GROUP BY d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.fe_doc, d1.tx_mesNotPed, d1.tx_numDoc2";                
                
                strSQL+=" UNION";
                
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc,";
                //strSQL+=" ''||a1.fe_emb AS fe_emb, a1.tx_numDoc2";
                strSQL+=" ''||a1.fe_arr AS fe_emb, a1.tx_numDoc2";//la fecha estiamada se presenta en base a ARRIBO
                strSQL+=" FROM tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_cabMovInv AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emprelpedembimp AND a1.co_loc=a2.co_locrelpedembimp";
                strSQL+=" AND a1.co_tipDoc=a2.co_tipdocrelpedembimp AND a1.co_doc=a2.co_docrelpedembimp AND a2.st_reg NOT IN('E','I')";
                strSQL+=" WHERE a1.st_reg NOT IN('E','I') ";
                strSQL+=" AND a2.co_emp IS NULL";
                strSQL+=" ORDER BY co_emp, co_loc, co_tipDoc, co_doc";

                System.out.println("cargarPedidosEmbarcadosConfigurados:  "+ strSQL);
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegPedEmb=new ArrayList();
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_EMP,"" + rst.getString("co_emp"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_LOC,"" + rst.getString("co_loc"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_TIP_DOC,"" + rst.getString("co_tipDoc"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_DOC,"" + rst.getString("co_doc"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_NUM_PED,"" + rst.getString("tx_numDoc2"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_NUM_COL,"");
                    arlRegPedEmb.add(INT_ARL_PED_EMB_EST_COL,"");
                    arlRegPedEmb.add(INT_ARL_PED_EMB_FEC_EST_PED_EMB,"" + rst.getString("tx_mesNotPed"));
                    
                    arlDatPedEmb.add(arlRegPedEmb);
                }
//                //para agregar la columna de totales
//                arlRegPedEmb=new ArrayList();
//                arlRegPedEmb.add(INT_ARL_PED_EMB_COD_EMP,"");
//                arlRegPedEmb.add(INT_ARL_PED_EMB_COD_LOC,"");
//                arlRegPedEmb.add(INT_ARL_PED_EMB_COD_TIP_DOC,"");
//                arlRegPedEmb.add(INT_ARL_PED_EMB_COD_DOC,"");
//                arlRegPedEmb.add(INT_ARL_PED_EMB_NUM_PED,"Total");
//                arlRegPedEmb.add(INT_ARL_PED_EMB_NUM_COL,"");
//                arlRegPedEmb.add(INT_ARL_PED_EMB_EST_COL,"");
//                arlRegPedEmb.add(INT_ARL_PED_EMB_FEC_EST_PED_EMB,"");                
//                arlDatPedEmb.add(arlRegPedEmb);
                System.out.println("arlDatPedEmb: " + arlDatPedEmb.toString());

                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
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
        int intCodEmp, intNumFilTblBod, j;
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intNumFilTblBod=objTblModBod.getRowCountTrue();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strAux="";
                if (txtCodItm.getText().length()>0)
                    strAux+=" AND a1.co_itm=" + txtCodItm.getText();
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT b1.co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm, b1.tx_desCor, b1.nd_preVta1";
                for (j=0; j<intNumFilTblBod; j++)
                {
                    if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                    {
                        strSQL+=", b" + (j+2) + ".nd_canDis AS b" + (j+2) + "_nd_canDis";
                        strSQL+=", b" + (j+2) + ".nd_canIngBod AS b" + (j+2) + "_nd_canIngBod";
                        strSQL+=", b" + (j+2) + ".nd_canEgrBod AS b" + (j+2) + "_nd_canEgrBod";
                    }
                    else
                    {
                        strSQL+=", Null AS b" + (j+2) + "_nd_canDis";
                        strSQL+=", Null AS b" + (j+2) + "_nd_canIngBod";
                        strSQL+=", Null AS b" + (j+2) + "_nd_canEgrBod";
                    }
                }
                strSQL+=" FROM (";
                strSQL+=" SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a3.tx_desCor, a1.nd_preVta1";
                strSQL+=" FROM tbm_inv AS a1";
                strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=strAux;
                strSQL+=" ) AS b1";
                //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                if (objParSis.getCodigoUsuario()==1)
                {
                    for (j=0; j<intNumFilTblBod; j++)
                    {
                        if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                        {
                            strSQL+=" LEFT OUTER JOIN (";
                            strSQL+=" SELECT a2.co_itmMae, SUM(a1.nd_canDis) AS nd_canDis, SUM(a1.nd_canIngBod) AS nd_canIngBod, SUM(a1.nd_canEgrBod) AS nd_canEgrBod";
                            strSQL+=" FROM tbm_invBod AS a1";
                            strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                            strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)";
                            strSQL+=" WHERE a3.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP);
                            strSQL+=" AND a3.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD);
                            strSQL+=" GROUP BY a2.co_itmMae";
                            strSQL+=" ) AS b" + (j+2) + " ON (b1.co_itmMae=b" + (j+2) + ".co_itmMae)";
                        }
                    }
                }
                else
                {
                    for (j=0; j<intNumFilTblBod; j++)
                    {
                        if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                        {
                            strSQL+=" LEFT OUTER JOIN (";
                            strSQL+=" SELECT a2.co_itmMae, SUM(a1.nd_canDis) AS nd_canDis, SUM(a1.nd_canIngBod) AS nd_canIngBod, SUM(a1.nd_canEgrBod) AS nd_canEgrBod";
                            strSQL+=" FROM tbm_invBod AS a1";
                            strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                            strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)";
                            strSQL+=" INNER JOIN tbr_bodLocPrgUsr AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_bod=a4.co_bod)";
                            strSQL+=" WHERE a3.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP);
                            strSQL+=" AND a3.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD);
                            strSQL+=" AND a4.co_mnu=" + objParSis.getCodigoMenu();
                            strSQL+=" AND a4.co_usr=" + objParSis.getCodigoUsuario();
                            strSQL+=" AND a4.st_reg IN ('A','P')";
                            strSQL+=" GROUP BY a2.co_itmMae";
                            strSQL+=" ) AS b" + (j+2) + " ON (b1.co_itmMae=b" + (j+2) + ".co_itmMae)";
                        }
                    }
                }
                if(objParSis.getCodigoMenu()==846){
                    if(chkMosItmPre.isSelected())
                        strSQL+=" WHERE (b1.nd_preVta1 IS NOT NULL AND b1.nd_preVta1>0)";
                }
                strSQL+=" ORDER BY b1.tx_codAlt";
                System.out.println("falla: " + strSQL);
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
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_DEC_UNI,rst.getString("tx_desCor"));
                        switch (objParSis.getCodigoMenu())
                        {
                            case 846: //Lista de precios (Stock físico por bodegas)...
                                vecReg.add(INT_TBL_DAT_PRE_UNI_VT1,rst.getString("nd_preVta1"));
                                for (j=0; j<intNumFilTblBod; j++)
                                {
                                    vecReg.add(INT_TBL_DAT_CDI_DIS+j*INT_TBL_DAT_NUM_TOT_CDI,rst.getString("b" + (j+2) + "_nd_canDis"));
                                    vecReg.add(INT_TBL_DAT_CDI_CAN_ING+j*INT_TBL_DAT_NUM_TOT_CDI,null);
                                    vecReg.add(INT_TBL_DAT_CDI_CAN_EGR+j*INT_TBL_DAT_NUM_TOT_CDI,null);
                                }
                                
                                for(int d=0; d<arlDatPedEmb.size(); d++){
                                    vecReg.add((intNumColIniPedEmb+d),     null);
                                }                                
                                break;
                            case 910: //Listado de stock físico de inventario por bodegas...
                            default:
                                vecReg.add(INT_TBL_DAT_PRE_UNI_VT1,null);
                                for (j=0; j<intNumFilTblBod; j++)
                                {
                                    vecReg.add(INT_TBL_DAT_CDI_DIS+j*INT_TBL_DAT_NUM_TOT_CDI,rst.getString("b" + (j+2) + "_nd_canDis"));
                                    vecReg.add(INT_TBL_DAT_CDI_CAN_ING+j*INT_TBL_DAT_NUM_TOT_CDI,rst.getString("b" + (j+2) + "_nd_canIngBod"));
                                    vecReg.add(INT_TBL_DAT_CDI_CAN_EGR+j*INT_TBL_DAT_NUM_TOT_CDI,rst.getString("b" + (j+2) + "_nd_canEgrBod"));
                                }
                                break;
                        }
                        
                        
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
                
                
                if(objParSis.getCodigoMenu()==846){
                    //se cargan los datos de valores del pedido embarcado
                    int intCodItmMaeTbl=0;
                    int intCodItmMaeArl=0;

                    String strEstPedEmb="";
                    int intColPedEmb=0;
                    BigDecimal bdeValPedCur=new BigDecimal(BigInteger.ZERO);

                    for(int d=0; d<objTblMod.getRowCountTrue(); d++){
                        intCodItmMaeTbl=Integer.parseInt(objTblMod.getValueAt(d, INT_TBL_DAT_COD_MAE)==null?"0":(objTblMod.getValueAt(d, INT_TBL_DAT_COD_MAE).toString().equals("")?"0":objTblMod.getValueAt(d, INT_TBL_DAT_COD_MAE).toString()));
                        for(int e=0; e<(arlDatCanPedEmb.size()); e++){
                            intCodItmMaeArl=objUti.getIntValueAt(arlDatCanPedEmb,    e, INT_ARL_CAN_PED_EMB_COD_ITM_MAE);
                            strEstPedEmb=objUti.getStringValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_EST);
                            intColPedEmb=objUti.getIntValueAt(arlDatCanPedEmb,    e, INT_ARL_CAN_PED_EMB_NUM_COL);

                            if(strEstPedEmb.equals("")){
                                if(intCodItmMaeTbl==intCodItmMaeArl){
                                    objUti.setStringValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_EST, "S");
                                    bdeValPedCur=objUti.getBigDecimalValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_CAN_PED_EMB);

                                    objTblMod.setValueAt(bdeValPedCur, d, intColPedEmb);
                                }
                            }

                        }

                    }
                }
                
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
    
    
    
    
    private boolean consultaDetalleNotayPedidosEmbarcados(){
        boolean blnRes=true;
        Statement stmCanPedEmb;
        ResultSet rstCanPedEmb;
        try{
            if(con!=null){
                stmCanPedEmb=con.createStatement();
                //crear un arreglo y colocar ahi la informacion para luego ser leida antes de terminar de hacer la consulta.
                strSQL="";
                strSQL+="SELECT g1.co_emp, g1.co_loc, g1.co_tipDoc, g1.co_doc, g1.co_itm, g1.nd_can, a1.tx_codAlt, k1.co_itmMae";
                strSQL+="  FROM (tbm_inv AS a1 ";
                strSQL+="                       INNER JOIN tbm_equInv AS k1";
                strSQL+="                       ON a1.co_emp=k1.co_emp AND a1.co_itm=k1.co_itm";
                strSQL+="  )";
                strSQL+="  INNER JOIN(";
                strSQL+="  SELECT f2.co_emp, f2.co_loc, f2.co_tipDoc, f2.co_doc, f2.co_itm, SUM(f2.nd_can) AS nd_can";
                strSQL+="     FROM (tbm_cabPedEmbImp AS f1 ";
                strSQL+="  		LEFT OUTER JOIN tbm_cabMovInv AS a2";
                strSQL+="  		ON f1.co_emp=a2.co_emprelpedembimp AND f1.co_loc=a2.co_locrelpedembimp ";
                strSQL+="  		AND f1.co_tipDoc=a2.co_tipdocrelpedembimp AND f1.co_doc=a2.co_docrelpedembimp AND a2.st_reg NOT IN('E','I')";
                strSQL+="  	)";
                strSQL+="         INNER JOIN tbm_detPedEmbImp AS f2";
                strSQL+="         ON f1.co_emp=f2.co_emp AND f1.co_loc=f2.co_loc AND f1.co_tipDoc=f2.co_tipDoc AND f1.co_doc=f2.co_doc ";
                strSQL+="         WHERE f1.st_reg NOT IN('E','I') AND a2.co_emp IS NULL";
                strSQL+="         GROUP BY f2.co_emp, f2.co_loc, f2.co_tipDoc, f2.co_doc, f2.co_itm";
                strSQL+="       ) AS g1";
                strSQL+="       ON a1.co_emp=g1.co_emp AND a1.co_itm=g1.co_itm";
                strSQL+=" UNION ALL";
                strSQL+=" SELECT g2.co_emp, g2.co_loc, g2.co_tipDoc, g2.co_doc, g2.co_itm, g2.nd_canNotPedFal, a1.tx_codAlt, k1.co_itmMae";
                strSQL+="  FROM (tbm_inv AS a1 ";
                strSQL+="                       INNER JOIN tbm_equInv AS k1";
                strSQL+="                       ON a1.co_emp=k1.co_emp AND a1.co_itm=k1.co_itm";
                strSQL+="  )";
                strSQL+=" INNER JOIN(";
                strSQL+=" 	SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.co_itm";
                strSQL+=" 		/*, c1.nd_canNotPed, c1.nd_canPedEmb*/, c1.nd_canNotPedFal";
                strSQL+=" 	FROM(";
                strSQL+=" 		SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_itm";
                strSQL+="		, b1.nd_canNotPed, CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END AS nd_canPedEmb";
                strSQL+="		, (b1.nd_canNotPed - ";
                strSQL+="			CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END";
                strSQL+="		  ) AS nd_canNotPedFal";
                strSQL+="		FROM(	";
                strSQL+="			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a5.tx_deslar AS tx_mesNotPed, a1.tx_numDoc2";
                strSQL+="			, a2.co_emp AS co_empPedEmb, a2.co_loc AS co_locPedEmb, a2.co_tipDoc AS co_tipDocPedEmb, a2.co_doc AS co_docPedEmb, a2.co_reg AS co_regPedEmb";
                strSQL+="			, CAST(a4.nd_can AS NUMERIC(18,6) ) AS nd_canNotPed, CAST(a2.nd_can AS NUMERIC(18,6) ) AS nd_canPedEmb";
                strSQL+=" 			, a4.co_itm";
                strSQL+=" 			FROM (tbm_cabNotPedImp AS a1 INNER JOIN tbm_mesEmbImp AS a5 ON a1.co_mesemb=a5.co_mesemb)";
                strSQL+="			INNER JOIN tbm_detNotPedImp AS a4";
                strSQL+="			ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc AND a1.co_doc=a4.co_doc";
                strSQL+="			LEFT OUTER JOIN ";
                strSQL+="				(tbm_detPedEmbImp AS a2 INNER JOIN tbm_cabPedEmbImp AS a3";
                strSQL+="				 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+="				 AND a3.st_reg NOT IN('I','E')";
                strSQL+="				)";
                strSQL+=" 			ON a4.co_emp=a2.co_emp AND a4.co_loc=a2.co_locRel AND a4.co_tipDoc=a2.co_tipdocRel ";
                strSQL+=" 			AND a4.co_doc=a2.co_docRel AND a4.co_reg=a2.co_regRel";
                strSQL+=" 			WHERE a1.st_reg NOT IN('E','I')	  AND a1.st_cie='N'	";
                strSQL+="			GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a5.tx_deslar, a1.tx_numDoc2";
                strSQL+=" 			, a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a4.nd_can, a2.nd_can, a4.co_itm";
                strSQL+=" 			ORDER BY a1.co_doc";
                strSQL+=" 		) AS b1";
                strSQL+=" 		GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.tx_mesNotPed, b1.tx_numDoc2, b1.nd_canNotPed, b1.co_itm";
                strSQL+=" 	) AS c1";
                strSQL+=" 	WHERE c1.nd_canNotPedFal > 0";
                strSQL+=") AS g2";
                strSQL+=" ON a1.co_emp=g2.co_emp AND a1.co_itm=g2.co_itm";
                strSQL+=" ORDER BY co_emp, co_loc, co_tipDoc, co_doc";
                
                System.out.println("consultaDetalleNotayPedidosEmbarcados:  "+ strSQL);
                rstCanPedEmb=stmCanPedEmb.executeQuery(strSQL);
                arlDatCanPedEmb.clear();
                while(rstCanPedEmb.next()){
                    arlRegCanPedEmb=new ArrayList();
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_COD_EMP, rstCanPedEmb.getString("co_emp"));
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_COD_LOC, rstCanPedEmb.getString("co_loc"));
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_COD_TIP_DOC, rstCanPedEmb.getString("co_tipDoc"));
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_COD_DOC, rstCanPedEmb.getString("co_doc"));
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_COD_ITM, rstCanPedEmb.getString("co_itm"));
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_CAN_PED_EMB, rstCanPedEmb.getString("nd_can"));
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_COD_ALT_ITM, rstCanPedEmb.getString("tx_codAlt"));
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_EST, "");
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_NUM_COL, "0");
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_COD_ITM_MAE, rstCanPedEmb.getString("co_itmMae"));                    
                    arlDatCanPedEmb.add(arlRegCanPedEmb);                    
                }
                stmCanPedEmb.close();
                stmCanPedEmb=null;
                rstCanPedEmb.close();
                rstCanPedEmb=null;
                
                //se cargan los datos de valores del pedido embarcado
                int intCodEmpNomPedEmb=-1;
                int intCodLocNomPedEmb=-1;
                int intCodTipDocNomPedEmb=-1;
                int intCodDocNomPedEmb=-1;
                int intNumColNomPedEmb=-1;                
                int intCodEmpCanPedEmb=-1;
                int intCodLocCanPedEmb=-1;
                int intCodTipDocCanPedEmb=-1;
                int intCodDocCanPedEmb=-1;                
                String strEstPedEmb="";
                
                for(int d=0; d<(arlDatPedEmb.size()); d++){
                    intCodEmpNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_COD_EMP);
                    intCodLocNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_COD_LOC);
                    intCodTipDocNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_COD_TIP_DOC);
                    intCodDocNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_COD_DOC);
                    intNumColNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_NUM_COL);
                    
                    for(int e=0; e<(arlDatCanPedEmb.size()); e++){
                        intCodEmpCanPedEmb=objUti.getIntValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_COD_EMP);
                        intCodLocCanPedEmb=objUti.getIntValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_COD_LOC);
                        intCodTipDocCanPedEmb=objUti.getIntValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_COD_TIP_DOC);
                        intCodDocCanPedEmb=objUti.getIntValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_COD_DOC);
                        
                        strEstPedEmb=objUti.getStringValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_EST);
                        if(strEstPedEmb.equals("")){
                            if( (intCodEmpNomPedEmb==intCodEmpCanPedEmb) && (intCodLocNomPedEmb==intCodLocCanPedEmb) && (intCodTipDocNomPedEmb==intCodTipDocCanPedEmb) && (intCodDocNomPedEmb==intCodDocCanPedEmb) ){
                                objUti.setStringValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_EST, "S");
                                objUti.setStringValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_NUM_COL, ""+intNumColNomPedEmb);
                            }
                        }
                    }                    
                }                
                
                //SE BLANQUEAN LOS ESTADOS DE LOS REGISTROS PARA PODER PROCESARLOS AL MOMENTO DE CARGAR LOS DATOS
                for(int e=0; e<(arlDatCanPedEmb.size()); e++){
                    objUti.setStringValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_EST, "");
                }
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
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
            arlAncCol.add("350");
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
                    if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE)
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
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE)
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
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE)
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
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblBodMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModBod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblBod.columnAtPoint(evt.getPoint())==INT_TBL_BOD_CHK)
            {
                if (blnMarTodChkTblBod)
                {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBod.setChecked(true, i, INT_TBL_BOD_CHK);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_DIS+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        switch (objParSis.getCodigoMenu())
                        {
                            case 846: //Lista de precios (Stock físico por bodegas)...
                                break;
                            case 910: //Listado de stock físico de inventario por bodegas...
                            default:
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_ING+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_EGR+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                                break;
                        }
                    }
                    blnMarTodChkTblBod=false;
                }
                else
                {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBod.setChecked(false, i, INT_TBL_BOD_CHK);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DIS+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        switch (objParSis.getCodigoMenu())
                        {
                            case 846: //Lista de precios (Stock físico por bodegas)...
                                break;
                            case 910: //Listado de stock físico de inventario por bodegas...
                            default:
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_ING+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_EGR+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                                break;
                        }
                    }
                    blnMarTodChkTblBod=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
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
            if (!cargarReg())
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
    
    
    
   private boolean cargarReg(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                if(objParSis.getCodigoMenu()==846){

                 
                    
                    
                    if(consultaDetalleNotayPedidosEmbarcados()){
                        if(cargarDetReg()){
                            escondeColumnasPedidoSinValor();
                        }
                    }
                }
                else{
                    if(cargarDetReg()){
                    }
                }

                objTblMod.initRowsState();
                con.close();
                con=null;
            }
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
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
            if (intCol>INT_TBL_DAT_NUM_TOT_CES)
                intCol=(intCol-INT_TBL_DAT_NUM_TOT_CES)%INT_TBL_DAT_NUM_TOT_CDI+INT_TBL_DAT_NUM_TOT_CES;
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
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
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_DEC_UNI:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_PRE_UNI_VT1:
                    strMsg="Precio unitario de venta 1";
                    break;
                case INT_TBL_DAT_CDI_DIS:
                    strMsg="Cantidad disponible";
                    break;
                case INT_TBL_DAT_CDI_CAN_ING:
                    strMsg="Cantidad por ingresar a Bodega";
                    break;
                case INT_TBL_DAT_CDI_CAN_EGR:
                    strMsg="Cantidad por egresar de Bodega";
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
    private class ZafMouMotAdaBod extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblBod.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_BOD_LIN:
                    strMsg="";
                    break;
                case INT_TBL_BOD_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_BOD_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_BOD_COD_BOD:
                    strMsg="Código de la bodega";
                    break;
                case INT_TBL_BOD_NOM_BOD:
                    strMsg="Nombre de la bodega";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblBod.getTableHeader().setToolTipText(strMsg);
        }
    }

    
    boolean escondeColumnasPedidoSinValor(){
        boolean blnRes=true;
        BigDecimal bdeValPed=new BigDecimal(BigInteger.ZERO);
        int intNumCol=-1;
        String strEstArl="";
        int intNumColEli=0;
        try{
            
            for(int j=(intNumColFinPedEmb-1); j>=intNumColIniPedEmb; j--){
                objTblMod.removeSystemHiddenColumn(j, tblDat);
            }
            
            
            
            for(int j=(intNumColFinPedEmb-1); j>=intNumColIniPedEmb; j--){//es menos 1 porque la ultima columna es en realidad de TOTAL
                for(int i=0;i<objTblMod.getRowCountTrue(); i++){
                    bdeValPed=bdeValPed.add(new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString())));
                }               
                if( (bdeValPed.compareTo(new BigDecimal("0"))>0) ||  (bdeValPed.compareTo(new BigDecimal("0"))<0) ){
                }
                else{//se elimina la columna y se coloca como estado 'E' en el arraylist
                    for(int k=0;k<arlDatPedEmb.size(); k++){

                        intNumCol=objUti.getIntValueAt(arlDatPedEmb, k, INT_ARL_PED_EMB_NUM_COL);
                        strEstArl=objUti.getStringValueAt(arlDatPedEmb, k, INT_ARL_PED_EMB_EST_COL);

                        if(! strEstArl.equals("E")){
                            if(j==intNumCol){
                                objUti.setStringValueAt(arlDatPedEmb, k, INT_ARL_PED_EMB_EST_COL, "E");
                                objTblMod.addSystemHiddenColumn(intNumCol, tblDat);
                                intNumColEli++;
                                break;
                            }
                        }
                    }
                }
                bdeValPed=new BigDecimal(BigInteger.ZERO);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
}