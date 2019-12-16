/*
 * ZafVen37.java
 *PENDIENTE NO CARGA!!! 
 * Created on 16 de Agosto de 2017, 12:20 PM
 */
package Ventas.ZafVen37;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author Jose Mario Marin
 */
public class ZafVen37 extends javax.swing.JInternalFrame 
{
 
//co_cli--tx_nom--co_emp--co_loc--co_cot--co_bod--tx_nomBod--co_itm--tx_codAlt--tx_nomItm--nd_canAut--nd_canFac--nd_canCan--nd_canPen

    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_MAE_ITM=1;                     //Código Cliente.
    static final int INT_TBL_DAT_COD_ALT=2;                     //Nombre Cliente.
    static final int INT_TBL_DAT_COD_ALT_DOS=3;                     //Nombre Cliente.
    static final int INT_TBL_DAT_NOM_ITM=4;                     //Código Empresa.
    static final int INT_TBL_DAT_COD_BOD=5;                     //Codigo de la bodega.
    static final int INT_TBL_DAT_NOM_BOD=6;                     //Nombre de la Bodega.
    static final int INT_TBL_DAT_CAN=7;                    //Cantidad Autoizada.
    static final int INT_TBL_DAT_CAN_FAC=8;                    //Cantidad Facturada.
    static final int INT_TBL_DAT_CAN_CAN=9;                    //Cantidad Cancelada.
    static final int INT_TBL_DAT_CAN_PEN=10;                    //Cantidad Pendiente.
    static final int INT_TBL_DAT_BTN_LIS_COT_RES_EMP=11;                    //Cantidad Autoizada.
    static final int INT_TBL_DAT_CAN_RES_VEN=12;                    //Cantidad Reserva en venta.   
    static final int INT_TBL_DAT_CAN_STOCK=13;
    static final int INT_TBL_DAT_CAN_RESERVADO=14;
    static final int INT_TBL_DAT_CAN_DISPONIBLE=15;    
    static final int INT_TBL_DAT_BTN_LIS_COT=16;                    //Cantidad Autoizada.
     
    
    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
   
    private ZafTblMod objTblMod;
 
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd, objTblOrdLoc;                  //JTable de ordenamiento.
    private ZafTblTot objTblTot;                                //JTable de totales.
    private ZafVenCon vcoLoc;                                   //Ventana de consulta "Empresa".
    private ZafVenCon vcoCli;                                   //Ventana de consulta "Clientes".
     
    private ZafVenCon vcoItm;                                   //Ventana de consulta "Item".
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSql, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg, vecAux;
     
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodLoc, strNomLoc;                        //Contenido del campo al obtener el foco.
    private String strCodCli, strDesLarCli;                     //Contenido del campo al obtener el foco.
     
    private String strCodAlt, strCodAlt2, strNomItm;                        //Contenido del campo al obtener el foco.
 
    private ZafPerUsr objPerUsr;

    private String strVersion=" v 0.1";
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafVen37(ZafParSis obj) 
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
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
        bgrItmPen = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAlt = new javax.swing.JTextField();
        txtCodAlt2 = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panCodAltItmDesHas = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        panCodAltItmTer = new javax.swing.JPanel();
        lblCodAltItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        panBodOri = new javax.swing.JPanel();
        spnBodOri = new javax.swing.JScrollPane();
        tblBodOri = new javax.swing.JTable();
        jCheckBox1 = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable()  {    protected javax.swing.table.JTableHeader createDefaultTableHeader()    {       return new ZafTblHeaGrp(columnModel);    } };
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
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
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setPreferredSize(new java.awt.Dimension(0, 480));
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
        optTod.setBounds(10, 80, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(10, 100, 400, 20);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Item");
        panFil.add(lblItm);
        lblItm.setBounds(20, 120, 90, 20);
        panFil.add(txtCodItm);
        txtCodItm.setBounds(90, 120, 56, 20);

        txtCodAlt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltFocusLost(evt);
            }
        });
        txtCodAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltActionPerformed(evt);
            }
        });
        panFil.add(txtCodAlt);
        txtCodAlt.setBounds(140, 120, 90, 20);

        txtCodAlt2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusLost(evt);
            }
        });
        txtCodAlt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAlt2ActionPerformed(evt);
            }
        });
        panFil.add(txtCodAlt2);
        txtCodAlt2.setBounds(230, 120, 63, 20);

        txtNomItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomItmFocusLost(evt);
            }
        });
        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });
        panFil.add(txtNomItm);
        txtNomItm.setBounds(300, 120, 343, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(640, 120, 20, 20);

        panCodAltItmDesHas.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panCodAltItmDesHas.setLayout(null);

        lblCodAltDes.setText("Desde:");
        panCodAltItmDesHas.add(lblCodAltDes);
        lblCodAltDes.setBounds(12, 20, 48, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        panCodAltItmDesHas.add(txtCodAltDes);
        txtCodAltDes.setBounds(60, 20, 100, 20);

        lblCodAltHas.setText("Hasta:");
        panCodAltItmDesHas.add(lblCodAltHas);
        lblCodAltHas.setBounds(168, 20, 48, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        panCodAltItmDesHas.add(txtCodAltHas);
        txtCodAltHas.setBounds(216, 20, 100, 20);

        panFil.add(panCodAltItmDesHas);
        panCodAltItmDesHas.setBounds(20, 150, 328, 52);

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
        txtCodAltItmTer.setBounds(112, 20, 184, 20);

        panFil.add(panCodAltItmTer);
        panCodAltItmTer.setBounds(360, 150, 308, 52);

        panBodOri.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de bodegas"));
        panBodOri.setLayout(new java.awt.BorderLayout());

        tblBodOri.setModel(new javax.swing.table.DefaultTableModel(
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
        spnBodOri.setViewportView(tblBodOri);

        panBodOri.add(spnBodOri, java.awt.BorderLayout.CENTER);

        panFil.add(panBodOri);
        panBodOri.setBounds(350, 210, 320, 100);
        panBodOri.getAccessibleContext().setAccessibleDescription("");

        jCheckBox1.setText("Mostrar solo items pendientes");
        panFil.add(jCheckBox1);
        jCheckBox1.setBounds(20, 210, 260, 23);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.BorderLayout());

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

        spnDat.setRequestFocusEnabled(false);

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
        ));
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);
        tabFrm.getAccessibleContext().setAccessibleName("Filtro");

        panBar.setPreferredSize(new java.awt.Dimension(320, 54));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 35));
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

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length()>0)
        optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
    }//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void txtCodAltHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusLost
        if (txtCodAltHas.getText().length()>0)
        optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltHasFocusLost

    private void txtCodAltHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusGained
        txtCodAltHas.selectAll();
    }//GEN-LAST:event_txtCodAltHasFocusGained

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if (txtCodAltDes.getText().length()>0)
        {
            optFil.setSelected(true);
            if (txtCodAltHas.getText().length()==0)
            txtCodAltHas.setText(txtCodAltDes.getText());
        }
    }//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

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
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(3);
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

    private void txtCodAlt2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt2.getText().equalsIgnoreCase(strCodAlt2))
        {
            if (txtCodAlt2.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(2);
            }
        }
        else
        txtCodAlt2.setText(strCodAlt2);
    }//GEN-LAST:event_txtCodAlt2FocusLost

    private void txtCodAlt2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusGained
        strCodAlt2=txtCodAlt2.getText();
        txtCodAlt2.selectAll();
    }//GEN-LAST:event_txtCodAlt2FocusGained

    private void txtCodAlt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAlt2ActionPerformed
        txtCodAlt2.transferFocus();
    }//GEN-LAST:event_txtCodAlt2ActionPerformed

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAlt.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
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
            txtCodAlt2.setText("");
            txtNomItm.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
            txtCodAltItmTer.setText("");
            
            
            
        }
    }//GEN-LAST:event_optTodItemStateChanged

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.ButtonGroup bgrItmPen;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butItm;
    private javax.swing.JCheckBox jCheckBox1;
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
    private javax.swing.JPanel panBodOri;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCodAltItmDesHas;
    private javax.swing.JPanel panCodAltItmTer;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBodOri;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBodOri;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAlt2;
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
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            panFil.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            //Configurar objetos.
             
            //Configurar las ZafVenCon.
            configurarVenConItm();
            //Configurar los JTables.
            configurarTblBodegas();
            
            configurarTblDat();
            
            cargarBodegas();
            
             
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
    
    private ZafTblCelRenBut objTblCelRenBut;//Render: Presentar JButton en JTable.
    
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
            vecCab=new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_MAE_ITM,"Cód.Mae.Sis.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cod.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALT_DOS,"Cod.Alt.2");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nom.Itm.");
            vecCab.add(INT_TBL_DAT_COD_BOD,"Cód.Bod.");
            vecCab.add(INT_TBL_DAT_NOM_BOD,"Bodega");
            vecCab.add(INT_TBL_DAT_CAN,"Can.Cot.");
            vecCab.add(INT_TBL_DAT_CAN_FAC,"Can.Fac.Res.");
            vecCab.add(INT_TBL_DAT_CAN_CAN,"Can.Can.Res.");
            vecCab.add(INT_TBL_DAT_CAN_PEN,"Can.Res.");
            vecCab.add(INT_TBL_DAT_BTN_LIS_COT_RES_EMP,"...");
            
            vecCab.add(INT_TBL_DAT_CAN_RES_VEN,"Can.Res.Ven");
            
            vecCab.add(INT_TBL_DAT_CAN_STOCK,"Stk.Act.");
            vecCab.add(INT_TBL_DAT_CAN_RESERVADO,"Can.Res.");
            vecCab.add(INT_TBL_DAT_CAN_DISPONIBLE,"Can.Dis.");
            
            vecCab.add(INT_TBL_DAT_BTN_LIS_COT,"...");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
           
             
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE_ITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setPreferredWidth(80);
            
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_BOD).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_CAN_FAC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CAN).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PEN).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_BTN_LIS_COT_RES_EMP).setPreferredWidth(30);
            
            tcmAux.getColumn(INT_TBL_DAT_CAN_RES_VEN).setPreferredWidth(75);
            
            tcmAux.getColumn(INT_TBL_DAT_CAN_STOCK).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_CAN_RESERVADO).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_CAN_DISPONIBLE).setPreferredWidth(75);
            
            tcmAux.getColumn(INT_TBL_DAT_BTN_LIS_COT).setPreferredWidth(30);
             
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MAE_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN, tblDat);
            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_STOCK, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_RESERVADO, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_DISPONIBLE, tblDat);
             
             
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
             
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BTN_LIS_COT_RES_EMP);
            vecAux.add("" + INT_TBL_DAT_BTN_LIS_COT);
	    objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_RES_VEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_FAC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CAN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PEN).setCellRenderer(objTblCelRenLbl);
            
            tcmAux.getColumn(INT_TBL_DAT_CAN_STOCK).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_RESERVADO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_DISPONIBLE).setCellRenderer(objTblCelRenLbl);
         
            
            objTblCelRenLbl=null;
            
             //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BTN_LIS_COT_RES_EMP).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BTN_LIS_COT).setCellRenderer(objTblCelRenBut);
            
            objTblCelRenBut=null;
            ButLisCotVen butLisCotVen = new ButLisCotVen(tblDat, INT_TBL_DAT_BTN_LIS_COT_RES_EMP); //*****
            ButLisCotVen2 butLisCotVen2 = new ButLisCotVen2(tblDat, INT_TBL_DAT_BTN_LIS_COT); //*****
            
            
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*2);
            
            ZafTblHeaColGrp objTblHeaColGrpCot=new ZafTblHeaColGrp("Reservas por Solicitud en Empresa");
            objTblHeaColGrpCot.setHeight(16);
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_DAT_CAN_FAC)); 
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_DAT_CAN_CAN)); 
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_DAT_CAN_PEN)); 
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_DAT_BTN_LIS_COT_RES_EMP)); 
             
            
            ZafTblHeaColGrp objTblHeaColGrpSol=new ZafTblHeaColGrp("Reservas por Venta directa");
            objTblHeaColGrpSol.setHeight(16);
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_CAN_RES_VEN));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_CAN_STOCK));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_CAN_RESERVADO));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_CAN_DISPONIBLE));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_BTN_LIS_COT));
             
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpCot);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpSol);
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_CAN, INT_TBL_DAT_CAN_FAC,INT_TBL_DAT_CAN_CAN, INT_TBL_DAT_CAN_PEN, INT_TBL_DAT_CAN_RES_VEN,INT_TBL_DAT_CAN_STOCK,INT_TBL_DAT_CAN_RESERVADO,INT_TBL_DAT_CAN_DISPONIBLE };
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
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
      * Listado de facturas de Venta
      * 
      */
     
      private class ButLisCotVen extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButLisCotVen(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Factura de Venta.");
        }
        @Override
        public void butCLick() {
            int intCol = tblDat.getSelectedRow();
            String strCodEmp = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_ALT  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_ALT  ).toString());
            //OBTENER FACTURAS SEGUN LA COTIZACION PUEDE TENER MUCHAS FACTURAS
            llamarCotVen(strCodEmp,1 );
        }
        
    }
      
      
      private class ButLisCotVen2 extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButLisCotVen2(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Factura de Venta.");
        }
        @Override
        public void butCLick() {
            int intCol = tblDat.getSelectedRow();
            String strCodEmp = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_ALT  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_ALT  ).toString());
            //OBTENER FACTURAS SEGUN LA COTIZACION PUEDE TENER MUCHAS FACTURAS
            llamarCotVen(strCodEmp,2 );
        }
        
    }
      
    private void llamarCotVen(  String strCodCot, int CodCfg){
        ZafVen37_01 obj1 = new  ZafVen37_01(objParSis,this,CodCfg, strCodCot);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }
    
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int   intNumDec,   j;
        boolean blnBodOrg=false ; 
        boolean blnRes=true;
        String strBodOrg="";
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            intNumDec=objParSis.getDecimalesBaseDatos();
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strConSQL="";
                switch (objSelFec.getTipoSeleccion())
                {
                    case 0: //Búsqueda por rangos
                        strConSQL+=" AND a1.fe_cot BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strConSQL+=" AND a1.fe_cot<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strConSQL+=" AND a1.fe_cot>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
                }
                
                //<editor-fold defaultstate="collapsed" desc="/* Filtro: Bodega. */">
            strBodOrg="";
            for (  j = 0; j < tblBodOri.getRowCount(); j++) 
            {
                if (tblBodOri.getValueAt(j, INT_TBL_BODORI_CHKBOD) != null) 
                {
                    if (tblBodOri.getValueAt(j, INT_TBL_BODORI_CHKBOD).toString().equals("true")) 
                    {
                        if(strBodOrg.equals("")) strBodOrg +=  tblBodOri.getValueAt(j, INT_TBL_BODORI_CODBOD).toString();
                        else strBodOrg += ","+tblBodOri.getValueAt(j, INT_TBL_BODORI_CODBOD).toString();
                        blnBodOrg=true;
                    } 
                }
            }
            if(blnBodOrg)
            {
               strConSQL+=" AND ( a6.co_bodGrp in ( "+strBodOrg+" ) )";
            }
            //</editor-fold>
               
                 
                if (txtCodItm.getText().length()>0){
                    if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                        strConSQL+=" AND a3.co_itmMae=( SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_itm=" + txtCodItm.getText() + " )";
                }
                 
                //Filtro: Código alterno del item
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0){
                        strConSQL+=" AND ((LOWER(a2.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a2.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                }
                
                if (txtCodAltItmTer.getText().length()>0)
                {
                        strConSQL+=getConSQLAdiCamTer("a2.tx_codAlt", txtCodAltItmTer.getText());
                }
                 
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Obtener los datos del "Grupo".
                    //Armar la sentencia SQL.m
                    strSql="";
                    strSql+=" SELECT a7.tx_nom as tx_nomBod, a9.tx_nomItm,a9.tx_codAlt2,a.* \n";
                    strSql+=" FROM ( \n";
                    strSql+="       SELECT CASE WHEN a1.co_itmMae IS NULL THEN a2.co_itmMAe ELSE a1.co_itmMae END AS co_itmMae,  \n";
                    strSql+="              CASE WHEN a1.tx_codALt IS NULL THEN a2.tx_codAlt ELSE a1.tx_codAlt END AS tx_codAlt,  \n";
                    strSql+="              CASE WHEN a1.co_bodGrp IS NULL THEN a2.co_bodGrp ELSE a1.co_bodGrp END as co_bodGrp,   \n";
                    strSql+="              CASE WHEN a1.nd_can IS NULL THEN 0 ELSE a1.nd_can END + CASE WHEN a2.nd_can IS NULL THEN 0 ELSE a2.nd_can END as nd_can,  \n";
                    strSql+="              CASE WHEN a1.nd_canFac IS NULL THEN 0 ELSE a1.nd_canFac END AS nd_canFac,  \n";
                    strSql+="              CASE WHEN a1.nd_canCan IS NULL THEN 0 ELSE a1.nd_canCan END AS nd_canCan, \n";
                    strSql+="              CASE WHEN a1.nd_canPenFac IS NULL THEN 0 ELSE a1.nd_canPenFac END AS nd_canPenFac,   \n";
                    strSql+="              CASE WHEN a2.nd_canResVen IS NULL THEN 0 ELSE a2.nd_canResVen END as nd_canResVen  \n";
                    strSql+="       FROM ( \n";
                    strSql+="             SELECT a3.co_itmMae,a2.tx_codAlt, a6.co_bodGrp ,   \n";
                    strSql+="                    SUM(CASE WHEN a2.nd_can IS NULL THEN 0 ELSE a2.nd_can END) as nd_can,  \n";
                    strSql+="                    SUM(CASE WHEN a2.nd_canFac IS NULL THEN 0 ELSE a2.nd_canFac END) as nd_canFac,   \n";
                    strSql+="                    SUM(CASE WHEN a2.nd_canCan IS NULL THEN 0 ELSE a2.nd_canCan END) as nd_canCan,   \n";
                    strSql+="                    SUM(CASE WHEN a2.nd_canPenFac IS NULL THEN 0 ELSE a2.nd_canPenFac END) as nd_canPenFac,  \n";
                    strSql+="                    SUM(CASE WHEN a2.nd_can IS NULL THEN 0 ELSE a2.nd_canAutRes END) as nd_canAutRes  \n";
                    strSql+="             FROM tbm_cabCotVen as a1    \n";
                    strSql+="             INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot)   \n";
                    strSql+="             INNER JOIN tbm_equInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)   \n";
                    strSql+="             INNER JOIN tbm_inv as a4 ON (a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm)  \n";
                    strSql+="             INNER JOIN tbm_tipSolResInv as a5 ON (a1.co_tipSolResInv=a5.co_tipSolResInv)  \n";
                    strSql+="             INNER JOIN tbr_bodEmpBodGrp as a6 ON (a2.co_emp=a6.co_emp AND a2.co_bod=a6.co_bod)  \n";
                    strSql+="             WHERE a1.st_autSolResInv='A' AND a4.st_ser='N' AND a5.tx_tipResInv='R'  \n";
                    if(strConSQL.length()>0){
                        strSql+="                   " + strConSQL;
                    }
                    strSql+="             GROUP BY a3.co_itmMae,a2.tx_codAlt, a6.co_bodGrp  \n";
                    strSql+="             ORDER BY a6.co_bodGrp,a2.tx_codAlt     \n";
                    strSql+="       ) as a1  \n";
                    strSql+="       FULL OUTER JOIN (  \n";  // JM 17/Nov/2017
                    strSql+="           SELECT a3.co_itmMae,a6.co_bodGrp,  a2.tx_codAlt,SUM( a2.nd_can) as nd_can, SUM(a2.nd_canLoc) as nd_canResVen  \n";
                    strSql+="           FROM tbm_cabCotVen as a1  \n";
                    strSql+="           INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot)   \n";
                    strSql+="           INNER JOIN tbr_bodEmpBodGrp as a6 ON (a2.co_emp=a6.co_emp AND a2.co_bod=a6.co_bod)  \n";
                    strSql+="           INNER JOIN tbm_equInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)  \n";
                    strSql+="           WHERE a1.st_reg='E' and   ( a2.tx_codAlt like '%I' OR a2.tx_codALt like '%S' )  AND a2.nd_canLoc > 0 \n";
                    if(strConSQL.length()>0){
                        strSql+="                   " + strConSQL;
                    }
                    strSql+="           GROUP BY a3.co_itmMae,a6.co_bodGrp,  a2.tx_codAlt  \n";
                    strSql+="       ) as a2 ON (a1.co_itmMae=a2.co_itmMae AND a1.co_bodGrp=a2.co_bodGrp)  \n";
                    strSql+="       ORDER BY co_bodGrp , tx_codAlt  \n";
                    strSql+=" ) as a  \n";
                    strSql+=" INNER JOIN tbm_bod as a7 ON ("+objParSis.getCodigoEmpresaGrupo()+"=a7.co_emp AND a.co_bodGrp=a7.co_bod)   \n";
                    strSql+=" INNER JOIN tbm_equInv as a8 ON (a.co_itmMae=a8.co_itmMae AND a8.co_emp=0) \n";
                    strSql+=" INNER JOIN tbm_inv as a9 ON (a8.co_emp=a9.co_emp AND a8.co_itm=a9.co_itm)  \n";
                    if(objParSis.getCodigoUsuario()!=1){
                        strSql+=" INNER JOIN tbr_bodlocprgusr as a10 ON (a7.co_emp=a10.co_emp AND a7.co_bod=a10.co_bod)  \n";
                        strSql+=" WHERE a10.co_emp="+objParSis.getCodigoEmpresa()+" AND a10.co_loc="+objParSis.getCodigoLocal()+" AND \n";
                        strSql+="       a10.co_mnu="+objParSis.getCodigoMenu()+" AND a10.co_usr="+objParSis.getCodigoUsuario()+" \n";
                    }
                    
                    strSql+=" ORDER BY  a.co_bodGrp , a9.tx_nomItm  \n";
                    System.out.println("ZafVen37 "  + strSql);
                    rst=stm.executeQuery(strSql);
                }
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_MAE_ITM,rst.getString("co_itmMae"));
                    vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_DOS,rst.getString("tx_codAlt2"));
                    vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                    vecReg.add(INT_TBL_DAT_COD_BOD,rst.getString("co_bodGrp"));
                    vecReg.add(INT_TBL_DAT_NOM_BOD,rst.getString("tx_nomBod"));
                    vecReg.add(INT_TBL_DAT_CAN,rst.getDouble("nd_can"));
                    vecReg.add(INT_TBL_DAT_CAN_FAC,rst.getDouble("nd_canFac"));
                    vecReg.add(INT_TBL_DAT_CAN_CAN,rst.getDouble("nd_canCan"));
                    vecReg.add(INT_TBL_DAT_CAN_PEN,rst.getDouble("nd_canPenFac"));
                    vecReg.add(INT_TBL_DAT_BTN_LIS_COT_RES_EMP,"");
                    vecReg.add(INT_TBL_DAT_CAN_RES_VEN,rst.getDouble("nd_canResVen"));
                    vecReg.add(INT_TBL_DAT_CAN_STOCK,rst.getDouble("nd_canResVen"));
                    vecReg.add(INT_TBL_DAT_CAN_RESERVADO,rst.getDouble("nd_canResVen"));
                    vecReg.add(INT_TBL_DAT_CAN_DISPONIBLE,rst.getDouble("nd_canResVen"));
                    vecReg.add(INT_TBL_DAT_BTN_LIS_COT,"");
                    vecDat.add(vecReg);
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
        boolean blnRes = true;
        String strSql="";
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("d1.co_itm");
            arlCam.add("d1.tx_codAlt");
            arlCam.add("d1.tx_codAlt2");
            arlCam.add("d1.tx_nomItm");
            arlCam.add("d4.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.Itm.");
            arlAli.add("Cód.Alt.2");
            arlAli.add("Nombre");
            arlAli.add("Uni.Med.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("60");
            arlAncCol.add("350");
            arlAncCol.add("60");
            
            //Armar la sentencia SQL.
            strSql+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor";
            strSql+=" FROM tbm_inv AS a1";
            strSql+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSql+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSql+=" AND a1.st_reg='A'";
            strSql+=" ORDER BY a1.tx_codAlt";
            
            System.out.println("configurarVenConItm: "+strSql);
            
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(3, javax.swing.JLabel.CENTER);
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
        }
        catch (Exception e) {     blnRes=false;    objUti.mostrarMsgErr_F1(this, e);  }
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
        boolean blnRes = true;
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
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAlt.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
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
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodAlt.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Codigo alterno 2".
                    if (vcoItm.buscar("a1.tx_codAlt2", txtCodAlt2.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
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
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodAlt2.setText(strCodAlt2);
                        }
                    }
                    break;   
                case 3: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(3);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtNomItm.setText(strNomItm);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)  {    blnRes=false;      objUti.mostrarMsgErr_F1(this, e);    }
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
            switch (intCol){
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_MAE_ITM:
                    strMsg="Código Maestro del cliente";
                    break;
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Codigo alterno del item";
                    break;    
                case INT_TBL_DAT_COD_ALT_DOS:
                    strMsg="Codigo alterno del item";
                    break;    
                    
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del Item";
                    break;
                 
                case INT_TBL_DAT_COD_BOD:
                    strMsg="Código de la Bodega";
                    break;
                case INT_TBL_DAT_NOM_BOD:
                    strMsg="Nombre de la Bodega";
                    break;    
                
                case INT_TBL_DAT_CAN:
                    strMsg="Cantidad Cotizada";
                    break;
                case INT_TBL_DAT_BTN_LIS_COT_RES_EMP:
                    strMsg="Listado de Cotizaciones Reservadas por Empresa";
                    break;
                case INT_TBL_DAT_CAN_FAC:
                    strMsg="Cantidad Facturada por reserva";
                    break;
                case INT_TBL_DAT_CAN_CAN:
                    strMsg="Cantidad Cancelada en Reserva";
                    break;
                case INT_TBL_DAT_CAN_PEN:
                    strMsg="Cantidad Reservada en Empresa";
                    break;
                case INT_TBL_DAT_CAN_RES_VEN:
                    strMsg="Cantidad Reservada por Venta";
                    break;    
                case INT_TBL_DAT_BTN_LIS_COT:
                    strMsg="Listado de Cotizaciones Reservadas por una Venta";
                    break;      
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    //JTable: Tabla de Bodega Origen.
    private static final int INT_TBL_BODORI_LIN = 0;
    private static final int INT_TBL_BODORI_CHKBOD = 1;
    private static final int INT_TBL_BODORI_CODBOD = 2;
    private static final int INT_TBL_BODORI_NOMBOD = 3;
    private ZafTblMod objTblModBodOri;
    
    private boolean cargarBodegas(){
        boolean blnRes = true;
        try
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();

                //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                if (objParSis.getCodigoUsuario() == 1) 
                {
                    //Armar la sentencia SQL.
                    strSql = "";
                    strSql+=" SELECT a2.co_bod, a2.tx_nom, a2.st_reg ";
                    strSql+=" FROM tbm_emp AS a1 ";
                    strSql+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp) ";
                    strSql+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + " AND a2.st_reg='A' ";
                    strSql+=" ORDER BY a1.co_emp, a2.co_bod ";
                } 
                else{
                    strSql = "";
                    strSql += " SELECT a.co_bod, a2.tx_nom ";
                    strSql += " FROM tbr_bodlocprgusr as a  ";
                    strSql += " INNER JOIN tbm_bod as a2 ON (a.co_Emp=a2.co_emp and a.co_bod=a2.co_bod) ";
                    strSql += " WHERE a.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                    strSql += " AND a.co_loc=" + objParSis.getCodigoLocal();
                    strSql += " AND a.co_mnu=" + objParSis.getCodigoMenu();
                    strSql += " AND a.co_usr=" + objParSis.getCodigoUsuario();
                    strSql += " AND a.tx_natBod in ('E', 'A') ";
                    strSql += " ORDER BY a.co_bod ";
                }
                rst = stm.executeQuery(strSql);

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_BODORI_LIN, "");
                    vecReg.add(INT_TBL_BODORI_CHKBOD, false);
                    vecReg.add(INT_TBL_BODORI_CODBOD, rst.getString("co_bod"));
                    vecReg.add(INT_TBL_BODORI_NOMBOD, rst.getString("tx_nom"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
                //Asignar vectores al modelo.
                objTblModBodOri.setData(vecDat);
                tblBodOri.setModel(objTblModBodOri);
                vecDat.clear();
                blnMarTodChkTblBodOri = false;
            }
        }
        catch (java.sql.SQLException e) {     blnRes = false;       objUti.mostrarMsgErr_F1(this, e);      } 
        catch (Exception e) {      blnRes = false;        objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }
private boolean blnMarTodChkTblBodOri = true;                               //Marcar todas las casillas de verificación del JTable de bodegas.
     

    
    /**
     * Esta función configura el JTable "tblBodOri".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblBodegas() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(4);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_BODORI_LIN, "");
            vecCab.add(INT_TBL_BODORI_CHKBOD, "");
            vecCab.add(INT_TBL_BODORI_CODBOD, "Cód.Bod.");
            vecCab.add(INT_TBL_BODORI_NOMBOD, "Bodega");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModBodOri = new ZafTblMod();
            objTblModBodOri.setHeader(vecCab);
            tblBodOri.setModel(objTblModBodOri);
            //Configurar JTable: Establecer tipo de selección.
            tblBodOri.setRowSelectionAllowed(true);
            tblBodOri.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblBodOri);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblBodOri.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblBodOri.getColumnModel();
            tcmAux.getColumn(INT_TBL_BODORI_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BODORI_CHKBOD).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BODORI_CODBOD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_BODORI_NOMBOD).setPreferredWidth(190);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BODORI_CHKBOD).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblBodOri.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblBodOri.getTableHeader().addMouseMotionListener(new ZafVen37.ZafMouMotAdaBodOri());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblBodOri.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblBodOriMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_BODORI_CHKBOD);
            objTblModBodOri.setColumnasEditables(vecAux);
            vecAux = null;
            //Configurar JTable: Editor de la tabla.
            //objTblEdi=new ZafTblEdi(tblBodOri);
            //Configurar JTable: Editor de búsqueda.
            //objTblBus=new ZafTblBus(tblBodOri);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblBodOri);
            tcmAux.getColumn(INT_TBL_BODORI_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BODORI_CHKBOD).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_BODORI_CODBOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk = new ZafTblCelEdiChk(tblBodOri);
            tcmAux.getColumn(INT_TBL_BODORI_CHKBOD).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //Mostrar las columnas "Cód.Bod." y "Bodega" sólo si hay seleccionada más de una bodega.
//                    if (objTblModBodOri.getRowCountChecked(INT_TBL_BOD_CHK)>1)
//                    {
//                        //Mostrar columnas.
//                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
//                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
//                    }
//                    else
//                    {
//                        //Ocultar columnas.
//                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
//                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
//                    }
                }
            });
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblBodOri+.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModBodOri.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux = null;
        } 
        catch (Exception e) {      blnRes = false;       objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }
    
    
     /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaBodOri extends java.awt.event.MouseMotionAdapter 
    {
        @Override
        public void mouseMoved(java.awt.event.MouseEvent evt) 
        {
            int intCol = tblBodOri.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) 
            {
                case INT_TBL_BODORI_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_BODORI_CHKBOD:
                    strMsg = "";
                    break;
                case INT_TBL_BODORI_CODBOD:
                    strMsg = "Código de la bodega";
                    break;
                case INT_TBL_BODORI_NOMBOD:
                    strMsg = "Nombre de la bodega";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblBodOri.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera
     * del JTable. Se utiliza ésta función especificamente para marcar todas las
     * casillas de verificación de la columna que indica la bodega seleccionada
     * en el el JTable de bodegas.
     */
    private void tblBodOriMouseClicked(java.awt.event.MouseEvent evt) 
    {
        int i, intNumFil;
        try
        {
            intNumFil = objTblModBodOri.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1 && tblBodOri.columnAtPoint(evt.getPoint()) == INT_TBL_BODORI_CHKBOD) 
            {
                if (blnMarTodChkTblBodOri)
                {
                    //Mostrar todas las columnas.
                    for (i = 0; i < intNumFil; i++)
                    {
                        objTblModBodOri.setChecked(true, i, INT_TBL_BODORI_CHKBOD);
                    }
                    blnMarTodChkTblBodOri = false;
                } 
                else 
                {
                    //Ocultar todas las columnas.
                    for (i = 0; i < intNumFil; i++) 
                    {
                        objTblModBodOri.setChecked(false, i, INT_TBL_BODORI_CHKBOD);
                    }
                    blnMarTodChkTblBodOri = true;
                }
//                //Mostrar las columnas "Cód.Bod." y "Bodega" sólo si hay seleccionada más de una bodega.
//                if (objTblModBod.getRowCountChecked(INT_TBL_BOD_CHK)>1)
//                {
//                    //Mostrar columnas.
//                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
//                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
//                }
//                else
//                {
//                    //Ocultar columnas.
//                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
//                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
//                }
            }
        }
        catch (Exception e) {     objUti.mostrarMsgErr_F1(this, e);       }
    }

}