/*
 * ZafCom80.java
 *
 * Created on 20 de octubre de 2014, 05:11 PM
 */
package Compras.ZafCom80;
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
public class ZafCom80 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_BOD_LIN=0;                         //Línea.
    static final int INT_TBL_BOD_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_BOD_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_BOD_NOM_EMP=3;                     //Nombre de la empresa.
    static final int INT_TBL_BOD_COD_BOD=4;                     //Código de la bodega.
    static final int INT_TBL_BOD_NOM_BOD=5;                     //Nombre de la bodega.
    
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_LOC=1;                     //Código del local.
    static final int INT_TBL_DAT_COD_TIP_DOC=2;                 //Código del tipo de documento.
    static final int INT_TBL_DAT_DEC_TIP_DOC=3;                 //Descripción corta del tipo de documento.
    static final int INT_TBL_DAT_DEL_TIP_DOC=4;                 //Descripción larga del tipo de documento.
    static final int INT_TBL_DAT_COD_DOC=5;                     //Código del documento (Sistema).
    static final int INT_TBL_DAT_DOC_ORI=6;                     //Documento que origina la guía de remisión.
    static final int INT_TBL_DAT_NUM_DOC=7;                     //Número del documento (Preimpreso).
    static final int INT_TBL_DAT_FEC_DOC=8;                     //Fecha del documento.
    static final int INT_TBL_DAT_COD_BOD=9;                     //Código de la bodega.
    static final int INT_TBL_DAT_NOM_BOD=10;                    //Nombre de la bodega.    
    static final int INT_TBL_DAT_COD_CLI=11;                    //Código del cliente.
    static final int INT_TBL_DAT_NOM_CLI=12;                    //Nombre del cliente.
    static final int INT_TBL_DAT_NOM_DES=13;                    //Lugar del destino
    static final int INT_TBL_DAT_PES_KGR=14;                    //Peso en kilogramos.
    static final int INT_TBL_DAT_COD_VEH=15;                    //Código del vehículo.
    static final int INT_TBL_DAT_PLA_VEH=16;                    //Placa del vehículo.
    static final int INT_TBL_DAT_COD_CHO=17;                    //Código del chofer.
    static final int INT_TBL_DAT_IDE_TRA=18;                    //Identificación del transportista.
    static final int INT_TBL_DAT_NOM_TRA=19;                    //Nombre del transportista.
    static final int INT_TBL_DAT_EST_DOC=20;                    //Estado del documento.
    static final int INT_TBL_DAT_FEC_ING=21;                    //Fecha de ingreso.
    static final int INT_TBL_DAT_COD_USR_ING=22;                //Código del usuario de ingreso.
    static final int INT_TBL_DAT_USR_ING=23;                    //Usuario de ingreso.
    static final int INT_TBL_DAT_COM_ING=24;                    //Computadora de ingreso.
    static final int INT_TBL_DAT_FEC_MOD=25;                    //Fecha de última modificación.
    static final int INT_TBL_DAT_COD_USR_MOD=26;                //Código del usuario de última modificación.
    static final int INT_TBL_DAT_USR_MOD=27;                    //Usuario de última modificación.
    static final int INT_TBL_DAT_COM_MOD=28;                    //Computadora de última modificación.
    //Variables
    private ZafSelFec objSelFec;
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
    private ZafTblTot objTblTot;                                //JTable de totales.
    private ZafVenCon vcoLoc;                                   //Ventana de consulta.
    private ZafVenCon vcoTipDoc;                                //Ventana de consulta.
    private ZafVenCon vcoVeh;                                   //Ventana de consulta.
    private ZafVenCon vcoCho;                                   //Ventana de consulta.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private Vector vecEstReg;                                   //Estado del registro.
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodVeh, strDesLarVeh;                     //Contenido del campo al obtener el foco.
    private String strCodCho, strNomCho;                        //Contenido del campo al obtener el foco.
    private ZafPerUsr objPerUsr;                                //Objeto que almacena el perfil del usuario.
    private boolean blnMarTodChkTblBod=true;                    //Marcar todas las casillas de verificación del JTable de bodegas.
   
    /** Crea una nueva instancia de la clase ZafCom10. */
    public ZafCom80(ZafParSis obj) 
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            configurarFrm();
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
        lblVeh = new javax.swing.JLabel();
        txtCodVeh = new javax.swing.JTextField();
        txtDesLarVeh = new javax.swing.JTextField();
        butVeh = new javax.swing.JButton();
        lblCho = new javax.swing.JLabel();
        txtCodCho = new javax.swing.JTextField();
        txtNomCho = new javax.swing.JTextField();
        butCho = new javax.swing.JButton();
        lblEstReg = new javax.swing.JLabel();
        cboEstReg = new javax.swing.JComboBox();
        panBod = new javax.swing.JPanel();
        spnBod = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
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
        optTod.setText("Todos los documentos");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 168, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(4, 188, 400, 20);

        lblVeh.setText("Vehículo:");
        lblVeh.setToolTipText("Vehículo");
        panFil.add(lblVeh);
        lblVeh.setBounds(24, 208, 120, 20);

        txtCodVeh.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVehActionPerformed(evt);
            }
        });
        txtCodVeh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVehFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVehFocusLost(evt);
            }
        });
        panFil.add(txtCodVeh);
        txtCodVeh.setBounds(144, 208, 56, 20);

        txtDesLarVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarVehActionPerformed(evt);
            }
        });
        txtDesLarVeh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarVehFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarVehFocusLost(evt);
            }
        });
        panFil.add(txtDesLarVeh);
        txtDesLarVeh.setBounds(200, 208, 460, 20);

        butVeh.setText("...");
        butVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVehActionPerformed(evt);
            }
        });
        panFil.add(butVeh);
        butVeh.setBounds(660, 208, 20, 20);

        lblCho.setText("Chofer:");
        lblCho.setToolTipText("Chofer");
        panFil.add(lblCho);
        lblCho.setBounds(24, 228, 120, 20);

        txtCodCho.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodCho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodChoActionPerformed(evt);
            }
        });
        txtCodCho.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodChoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodChoFocusLost(evt);
            }
        });
        panFil.add(txtCodCho);
        txtCodCho.setBounds(144, 228, 56, 20);

        txtNomCho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomChoActionPerformed(evt);
            }
        });
        txtNomCho.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomChoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomChoFocusLost(evt);
            }
        });
        panFil.add(txtNomCho);
        txtNomCho.setBounds(200, 228, 460, 20);

        butCho.setText("...");
        butCho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butChoActionPerformed(evt);
            }
        });
        panFil.add(butCho);
        butCho.setBounds(660, 228, 20, 20);

        lblEstReg.setText("Estado del documento:");
        lblEstReg.setToolTipText("Estado del documento");
        panFil.add(lblEstReg);
        lblEstReg.setBounds(24, 248, 120, 20);
        panFil.add(cboEstReg);
        cboEstReg.setBounds(144, 248, 264, 20);

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
        panBod.setBounds(4, 76, 660, 92);

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
        tblDat.setToolTipText("");
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

    private void butVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVehActionPerformed
        mostrarVenConVeh(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVeh.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butVehActionPerformed

    private void txtDesLarVehFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarVehFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarVeh.getText().equalsIgnoreCase(strDesLarVeh))
        {
            if (txtDesLarVeh.getText().equals(""))
            {
                txtCodVeh.setText("");
                txtDesLarVeh.setText("");
            }
            else
            {
                mostrarVenConVeh(2);
            }
        }
        else
            txtDesLarVeh.setText(strDesLarVeh);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVeh.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtDesLarVehFocusLost

    private void txtDesLarVehFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarVehFocusGained
        strDesLarVeh=txtDesLarVeh.getText();
        txtDesLarVeh.selectAll();
    }//GEN-LAST:event_txtDesLarVehFocusGained

    private void txtDesLarVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarVehActionPerformed
        txtDesLarVeh.transferFocus();
    }//GEN-LAST:event_txtDesLarVehActionPerformed

    private void txtCodVehFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVehFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodVeh.getText().equalsIgnoreCase(strCodVeh))
        {
            if (txtCodVeh.getText().equals(""))
            {
                txtCodVeh.setText("");
                txtDesLarVeh.setText("");
            }
            else
            {
                mostrarVenConVeh(1);
            }
        }
        else
            txtCodVeh.setText(strCodVeh);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVeh.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodVehFocusLost

    private void txtCodVehFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVehFocusGained
        strCodVeh=txtCodVeh.getText();
        txtCodVeh.selectAll();
    }//GEN-LAST:event_txtCodVehFocusGained

    private void txtCodVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVehActionPerformed
        txtCodVeh.transferFocus();
    }//GEN-LAST:event_txtCodVehActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodCho.setText("");
            txtNomCho.setText("");
            txtCodVeh.setText("");
            txtDesLarVeh.setText("");
            cboEstReg.setSelectedIndex(0);
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
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodChoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodChoActionPerformed
        txtCodCho.transferFocus();
}//GEN-LAST:event_txtCodChoActionPerformed

    private void txtCodChoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodChoFocusGained
        strCodCho=txtCodCho.getText();
        txtCodCho.selectAll();
}//GEN-LAST:event_txtCodChoFocusGained

    private void txtCodChoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodChoFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodCho.getText().equalsIgnoreCase(strCodCho))
        {
            if (txtCodCho.getText().equals(""))
            {
                txtCodCho.setText("");
                txtNomCho.setText("");
            }
            else
            {
                mostrarVenConCho(1);
            }
        }
        else
            txtCodCho.setText(strCodCho);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCho.getText().length()>0)
        {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtCodChoFocusLost

    private void txtNomChoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomChoActionPerformed
        txtNomCho.transferFocus();
}//GEN-LAST:event_txtNomChoActionPerformed

    private void txtNomChoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomChoFocusGained
        strNomCho=txtNomCho.getText();
        txtNomCho.selectAll();
}//GEN-LAST:event_txtNomChoFocusGained

    private void txtNomChoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomChoFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomCho.getText().equalsIgnoreCase(strNomCho))
        {
            if (txtNomCho.getText().equals(""))
            {
                txtCodCho.setText("");
                txtNomCho.setText("");
            }
            else
            {
                mostrarVenConCho(2);
            }
        }
        else
            txtNomCho.setText(strNomCho);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCho.getText().length()>0)
        {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtNomChoFocusLost

    private void butChoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butChoActionPerformed
        mostrarVenConCho(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCho.getText().length()>0)
        {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_butChoActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCho;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butVeh;
    private javax.swing.JComboBox cboEstReg;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCho;
    private javax.swing.JLabel lblEstReg;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVeh;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBod;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBod;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBod;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodCho;
    private javax.swing.JTextField txtCodVeh;
    private javax.swing.JTextField txtDesLarVeh;
    private javax.swing.JTextField txtNomCho;
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
            objSelFec.setTitulo("Fecha del documento");
            panFil.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.5");
            lblTit.setText(strAux);
            //Configurar objetos.
//            txtCodTipDoc.setVisible(false);
            //Configurar el combo "Estado de registro".
            vecEstReg=new Vector();
            vecEstReg.add("");
            vecEstReg.add("A");
            vecEstReg.add("I");
            cboEstReg.addItem("(Todos)");
            cboEstReg.addItem("A: Activo");
            cboEstReg.addItem("I: Anulado");
            cboEstReg.setSelectedIndex(0);
            //Configurar las ZafVenCon.
            configurarVenConVeh();
            configurarVenConCho();
            //Configurar los JTables.
            configurarTblBod();
            cargarBod();
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
            objTblCelEdiChk=null;
            
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblBod.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModBod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
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
            vecCab=new Vector(29);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Código");
            vecCab.add(INT_TBL_DAT_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_DOC_ORI,"Doc.Ori.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_COD_BOD,"Cód.Bod.");
            vecCab.add(INT_TBL_DAT_NOM_BOD,"Bodega");            
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente/Proveedor");
            vecCab.add(INT_TBL_DAT_NOM_DES,"Destino");
            vecCab.add(INT_TBL_DAT_PES_KGR,"Peso(Kg)");
            vecCab.add(INT_TBL_DAT_COD_VEH,"Cód.Veh.");
            vecCab.add(INT_TBL_DAT_PLA_VEH,"Pla.Veh.");
            vecCab.add(INT_TBL_DAT_COD_CHO,"Cód.Cho.");
            vecCab.add(INT_TBL_DAT_IDE_TRA,"Ide.Tra.");
            vecCab.add(INT_TBL_DAT_NOM_TRA,"Nom.Tra.");
            vecCab.add(INT_TBL_DAT_EST_DOC,"Est.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_ING,"Fec.Ing.");
            vecCab.add(INT_TBL_DAT_COD_USR_ING,"Cód.Usr.Ing.");
            vecCab.add(INT_TBL_DAT_USR_ING,"Usr.Ing.");
            vecCab.add(INT_TBL_DAT_COM_ING,"Com.Ing.");
            vecCab.add(INT_TBL_DAT_FEC_MOD,"Fec.Ult.Mod.");
            vecCab.add(INT_TBL_DAT_COD_USR_MOD,"Cód.Usr.Ult.Mod.");
            vecCab.add(INT_TBL_DAT_USR_MOD,"Usr.Ult.Mod.");
            vecCab.add(INT_TBL_DAT_COM_MOD,"Com.Ult.Mod.");
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
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DEC_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_NOM_DES).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_DAT_IDE_TRA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_NOM_TRA).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_EST_DOC).setPreferredWidth(50);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CLI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_VEH, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CHO, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_USR_ING, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_USR_MOD, tblDat);
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!(objPerUsr.isOpcionEnabled(3976) ))
            {
                //3976: Ficha "Reporte": Tabla->Mostrar "Datos de ingreso y última modificación de documentos".
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_ING, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_USR_ING, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COM_ING, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_MOD, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_USR_MOD, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COM_MOD, tblDat);
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
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_EST_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_PES_KGR};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
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
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    if (objParSis.getCodigoUsuario()==1)
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                        strSQL+=" FROM tbm_emp AS a1";
                        strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
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
                        strSQL+=" INNER JOIN tbr_bodLocPrgUsr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod)";
                        strSQL+=" WHERE a3.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                        strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario();
                        strSQL+=" AND a3.st_reg IN ('A','P')";
                        strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                        rst=stm.executeQuery(strSQL);
                    }
                }
                else
                {
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
                        strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND b1.co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND b1.co_mnu=" + objParSis.getCodigoMenu();
                        strSQL+=" AND b1.co_usr=" + objParSis.getCodigoUsuario();
                        strSQL+=" AND b1.st_reg IN ('A','P')";
                        strSQL+=" GROUP BY b2.co_empGrp, b2.co_bodGrp";
                        strSQL+=" ) AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod)";
                        strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                        rst=stm.executeQuery(strSQL);
                    }
                }
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_BOD_LIN,"");
                    vecReg.add(INT_TBL_BOD_CHK,Boolean.TRUE);
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intNumFilTblBod, i, j;
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
                switch (objSelFec.getTipoSeleccion())
                {
                    case 0: //Búsqueda por rangos
                        strConSQL+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strConSQL+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strConSQL+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
                }
                //Obtener las bodegas a consultar.
                intNumFilTblBod=objTblModBod.getRowCountTrue();
                i=0;
                strAux="";
                for (j=0; j<intNumFilTblBod; j++)
                {
                    if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                    {
                        if (i==0)
                            strAux+=" (a4.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + " AND a4.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + ")";
                        else
                            strAux+=" OR (a4.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + " AND a4.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + ")";
                        i++;
                    }
                }
                strConSQL+=" AND (" + strAux + ")";
                if (txtCodVeh.getText().length()>0)
                    strConSQL+=" AND a1.co_veh=" + txtCodVeh.getText();
                if (txtCodCho.getText().length()>0)
                    strConSQL+=" AND a1.co_cho=" + txtCodCho.getText();
                if (cboEstReg.getSelectedIndex()>0)
                    strConSQL+=" AND a1.st_reg='" + vecEstReg.get(cboEstReg.getSelectedIndex()) + "'";
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a1.tx_datDocOriGuiRem, a1.ne_numDoc, a1.fe_doc, a1.co_cliDes, a1.tx_nomCliDes, a4.co_bodGrp, a2.tx_nom AS tx_nomBod, a1.nd_pesTotKgr";
                strSQL+="      , a1.co_veh, (CASE WHEN a1.co_veh IS NULL THEN a1.tx_plaVehTra ELSE a5.tx_pla END) AS tx_plaVehTra";
                strSQL+="      , a1.co_cho, (CASE WHEN a1.co_cho IS NULL THEN a1.tx_ideTra ELSE a6.tx_ide END) AS tx_ideTra, (CASE WHEN a1.co_cho IS NULL THEN a1.tx_nomTra ELSE a6.tx_nom || ' ' || a6.tx_ape END) AS tx_nomTra";
                strSQL+="      , a1.st_reg, a1.fe_ing, a1.co_usrIng, a7.tx_usr AS tx_usrIng, a1.tx_comIng, a1.fe_ultMod, a1.co_usrMod, a8.tx_usr AS tx_usrMod, a1.tx_comUltMod, a1.tx_dirCliDes";
                strSQL+=" FROM tbm_cabGuiRem AS a1";
                strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_ptoPar=a2.co_bod)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod)";
                strSQL+=" LEFT OUTER JOIN tbm_veh AS a5 ON (a1.co_veh=a5.co_veh)";
                strSQL+=" LEFT OUTER JOIN tbm_tra AS a6 ON (a1.co_cho=a6.co_tra)";
                strSQL+=" LEFT OUTER JOIN tbm_usr AS a7 ON (a1.co_usrIng=a7.co_usr)";
                strSQL+=" LEFT OUTER JOIN tbm_usr AS a8 ON (a1.co_usrMod=a8.co_usr)";
                strSQL+=" WHERE a1.ne_numOrdDes=0";
                strSQL+=strConSQL;
                strSQL+=" ORDER BY a4.co_bodGrp, a1.ne_numDoc";
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
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_DEC_TIP_DOC,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DEL_TIP_DOC,rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_DOC_ORI,rst.getString("tx_datDocOriGuiRem"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_COD_BOD,rst.getString("co_bodGrp"));
                        vecReg.add(INT_TBL_DAT_NOM_BOD,rst.getString("tx_nomBod"));                        
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cliDes"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nomCliDes"));
                        vecReg.add(INT_TBL_DAT_NOM_DES,rst.getString("tx_dirCliDes"));
                        vecReg.add(INT_TBL_DAT_PES_KGR,rst.getString("nd_pesTotKgr"));
                        vecReg.add(INT_TBL_DAT_COD_VEH,rst.getString("co_veh"));
                        vecReg.add(INT_TBL_DAT_PLA_VEH,rst.getString("tx_plaVehTra"));
                        vecReg.add(INT_TBL_DAT_COD_CHO,rst.getString("co_cho"));
                        vecReg.add(INT_TBL_DAT_IDE_TRA,rst.getString("tx_ideTra"));
                        vecReg.add(INT_TBL_DAT_NOM_TRA,rst.getString("tx_nomTra"));
                        vecReg.add(INT_TBL_DAT_EST_DOC,rst.getString("st_reg"));
                        //Habilitar/Inhabilitar las opciones según el perfil del usuario.
                        if (objPerUsr.isOpcionEnabled(3976))
                        {
                            //3976: Ficha "Reporte": Tabla->Mostrar "Datos de ingreso y última modificación de documentos".
                            vecReg.add(INT_TBL_DAT_FEC_ING,rst.getString("fe_ing"));
                            vecReg.add(INT_TBL_DAT_COD_USR_ING,rst.getString("co_usrIng"));
                            vecReg.add(INT_TBL_DAT_USR_ING,rst.getString("tx_usrIng"));
                            vecReg.add(INT_TBL_DAT_COM_ING,rst.getString("tx_comIng"));
                            vecReg.add(INT_TBL_DAT_FEC_MOD,rst.getString("fe_ultMod"));
                            vecReg.add(INT_TBL_DAT_COD_USR_MOD,rst.getString("co_usrMod"));
                            vecReg.add(INT_TBL_DAT_USR_MOD,rst.getString("tx_usrMod"));
                            vecReg.add(INT_TBL_DAT_COM_MOD,rst.getString("tx_comUltMod"));
                        }
                        else
                        {
                            vecReg.add(INT_TBL_DAT_FEC_ING,null);
                            vecReg.add(INT_TBL_DAT_COD_USR_ING,null);
                            vecReg.add(INT_TBL_DAT_USR_ING,null);
                            vecReg.add(INT_TBL_DAT_COM_ING,null);
                            vecReg.add(INT_TBL_DAT_FEC_MOD,null);
                            vecReg.add(INT_TBL_DAT_COD_USR_MOD,null);
                            vecReg.add(INT_TBL_DAT_USR_MOD,null);
                            vecReg.add(INT_TBL_DAT_COM_MOD,null);
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
            ifrVen.setVisible(true);
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
     * mostrar los "Vehículos".
     */
    private boolean configurarVenConVeh()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_veh");
            arlCam.add("a1.tx_pla");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Placa");
            arlAli.add("Vehículo");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("400");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_veh, a1.tx_pla, a1.tx_desLar";
            strSQL+=" FROM tbm_veh AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.co_veh";
            vcoVeh=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de vehículos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoVeh.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
     * mostrar los "Choferes".
     */
    private boolean configurarVenConCho()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("350");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_tra, a1.tx_ide, (a1.tx_nom || ' ' || a1.tx_ape) AS tx_nom";
            strSQL+=" FROM tbm_tra AS a1";
            strSQL+=" INNER JOIN tbm_traEmp AS a2 ON (a1.co_tra=a2.co_tra)";
            strSQL+=" INNER JOIN tbm_carLab AS a3 ON (a2.co_car=a3.co_car)";
            strSQL+=" INNER JOIN tbm_carLabPre AS a4 ON (a3.co_carPre=a4.co_carPre)";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY tx_nom";
            vcoCho=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de choferes", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCho.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCho.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
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
    private boolean mostrarVenConVeh(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoVeh.setCampoBusqueda(2);
                    vcoVeh.setVisible(true);
                    if (vcoVeh.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodVeh.setText(vcoVeh.getValueAt(1));
                        txtDesLarVeh.setText(vcoVeh.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoVeh.buscar("a1.co_veh", txtCodVeh.getText()))
                    {
                        txtCodVeh.setText(vcoVeh.getValueAt(1));
                        txtDesLarVeh.setText(vcoVeh.getValueAt(3));
                    }
                    else
                    {
                        vcoVeh.setCampoBusqueda(0);
                        vcoVeh.setCriterio1(11);
                        vcoVeh.cargarDatos();
                        vcoVeh.setVisible(true);
                        if (vcoVeh.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodVeh.setText(vcoVeh.getValueAt(1));
                            txtDesLarVeh.setText(vcoVeh.getValueAt(3));
                        }
                        else
                        {
                            txtCodVeh.setText(strCodVeh);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoVeh.buscar("a1.tx_desLar", txtDesLarVeh.getText()))
                    {
                        txtCodVeh.setText(vcoVeh.getValueAt(1));
                        txtDesLarVeh.setText(vcoVeh.getValueAt(3));
                    }
                    else
                    {
                        vcoVeh.setCampoBusqueda(2);
                        vcoVeh.setCriterio1(11);
                        vcoVeh.cargarDatos();
                        vcoVeh.setVisible(true);
                        if (vcoVeh.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodVeh.setText(vcoVeh.getValueAt(1));
                            txtDesLarVeh.setText(vcoVeh.getValueAt(3));
                        }
                        else
                        {
                            txtDesLarVeh.setText(strDesLarVeh);
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
    private boolean mostrarVenConCho(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCho.setCampoBusqueda(2);
                    vcoCho.setVisible(true);
                    if (vcoCho.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodCho.setText(vcoCho.getValueAt(1));
                        txtNomCho.setText(vcoCho.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCho.buscar("a1.co_tra", txtCodCho.getText()))
                    {
                        txtCodCho.setText(vcoCho.getValueAt(1));
                        txtNomCho.setText(vcoCho.getValueAt(3));
                    }
                    else
                    {
                        vcoCho.setCampoBusqueda(0);
                        vcoCho.setCriterio1(11);
                        vcoCho.cargarDatos();
                        vcoCho.setVisible(true);
                        if (vcoCho.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCho.setText(vcoCho.getValueAt(1));
                            txtNomCho.setText(vcoCho.getValueAt(3));
                        }
                        else
                        {
                            txtCodCho.setText(strCodCho);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoCho.buscar("a1.tx_nom", txtNomCho.getText()))
                    {
                        txtCodCho.setText(vcoCho.getValueAt(1));
                        txtNomCho.setText(vcoCho.getValueAt(3));
                    }
                    else
                    {
                        vcoCho.setCampoBusqueda(2);
                        vcoCho.setCriterio1(11);
                        vcoCho.cargarDatos();
                        vcoCho.setVisible(true);
                        if (vcoCho.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCho.setText(vcoCho.getValueAt(1));
                            txtNomCho.setText(vcoCho.getValueAt(3));
                        }
                        else
                        {
                            txtNomCho.setText(strNomCho);
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
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblBod.columnAtPoint(evt.getPoint())==INT_TBL_BOD_CHK)
            {
                if (blnMarTodChkTblBod)
                {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBod.setChecked(true, i, INT_TBL_BOD_CHK);
                    }
                    blnMarTodChkTblBod=false;
                }
                else
                {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBod.setChecked(false, i, INT_TBL_BOD_CHK);
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
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DEC_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DEL_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_DOC_ORI:
                    strMsg="Documento que origina la guía de remisión";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número del documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_COD_BOD:
                    strMsg="Código de la bodega";
                    break;
                case INT_TBL_DAT_NOM_BOD:
                    strMsg="Nombre de la bodega";
                    break;                    
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_DAT_NOM_DES:
                    strMsg="Lugar de destino";
                    break;                    
                case INT_TBL_DAT_PES_KGR:
                    strMsg="Peso(Kg)";
                    break;
                case INT_TBL_DAT_COD_VEH:
                    strMsg="Código del vehículo";
                    break;
                case INT_TBL_DAT_PLA_VEH:
                    strMsg="Placa del vehículo";
                    break;
                case INT_TBL_DAT_COD_CHO:
                    strMsg="Código del chofer";
                    break;
                case INT_TBL_DAT_IDE_TRA:
                    strMsg="Identificación del transportista";
                    break;
                case INT_TBL_DAT_NOM_TRA:
                    strMsg="Nombre del transportista";
                    break;
                case INT_TBL_DAT_EST_DOC:
                    strMsg="Estado del documento";
                    break;
                case INT_TBL_DAT_FEC_ING:
                    strMsg="Fecha de ingreso";
                    break;
                case INT_TBL_DAT_COD_USR_ING:
                    strMsg="Código del usuario de ingreso";
                    break;
                case INT_TBL_DAT_USR_ING:
                    strMsg="Usuario de ingreso";
                    break;
                case INT_TBL_DAT_COM_ING:
                    strMsg="Computadora de ingreso";
                    break;
                case INT_TBL_DAT_FEC_MOD:
                    strMsg="Fecha de última modificación";
                    break;
                case INT_TBL_DAT_COD_USR_MOD:
                    strMsg="Código del usuario de última modificación";
                    break;
                case INT_TBL_DAT_USR_MOD:
                    strMsg="Usuario de última modificación";
                    break;
                case INT_TBL_DAT_COM_MOD:
                    strMsg="Computadora de última modificación";
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
    
}


