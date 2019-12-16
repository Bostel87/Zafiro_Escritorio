/*
 * ZafVen23.java
 *
 * Created on 7 de mayo de 2013, 12:35 PM
 */

package Ventas.ZafVen23;

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
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafUtil.ZafLocPrgUsr;
import java.awt.Color;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Jose Mario Marin
 */
public class ZafVen23 extends javax.swing.JInternalFrame 
{
    //Constantes
    //Columnas del JTable: TblDat
    private static final int INT_TBL_DAT_LIN=0;             //Línea.
    private static final int INT_TBL_DAT_NOM_DOC=1;         //Código del vendedor.
    private static final int INT_TBL_DAT_COD_VEN=2;         //Código del vendedor.
    private static final int INT_TBL_DAT_NOM_VEN=3;         //Nombre del vendedor.
    
    //Columnas del JTable: TblEmp
    private static final int INT_TBL_EMP_LIN=0;             //Línea.
    private static final int INT_TBL_EMP_CHK=1;             //Casilla de verificación.
    private static final int INT_TBL_EMP_COD_EMP=2;         //Código de la empresa.
    private static final int INT_TBL_EMP_NOM_EMP=3;         //Nombre de la empresa.

    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafSelFec objSelFec;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblModEmp;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblFil;   
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                      //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                      //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                            //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                            //JTable de ordenamiento.
    private ZafTblTot objTblTot;                            //JTable de totales.
    private ZafVenCon vcoEmp;                               //Ventana de consulta <jota>
    private ZafVenCon vcoLoc; 
    private ZafVenCon vcoVen;                               //Ventana de consulta.
    private ZafVenCon vcoItm;                               //Ventana de consulta.
    private ZafTblCelRenLbl objTblCelRenLblColGre;
    private ZafTblCelRenLbl objTblCelRenLblColOcp;
    private ZafLocPrgUsr objLocPrgUsr;                          //Objeto que almacena los locales por usuario y programa.
    private int intNumDec=2, columnaPorcentual;
    private String strSQL, strConSQL, strAux;
    private Vector vecDatEmp;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecFechasCot;
    private Vector vecSumaFac, vecSumaCot;
    private Vector vecDevCountFac,vecDevSumaFac; 
    private Vector vecCountFac, vecCountCot;
    private Vector vecCodVenCot, vecNomVen;
    private Vector vecEstReg;
    private boolean blnCon;                                 //true: Continua la ejecución del hilo.
    private String strCodEmp, strNomEmp;                    //Contenido del campo al obtener el foco. <jota>
    private String strCodLoc, strNomLoc;                    //Contenido del campo al obtener el foco. 
    private String strCodVen, strNomVen;                    //Contenido del campo al obtener el foco.
    private boolean blnMarTodChkTblEmp=true;                //Marcar todas las casillas de verificación del JTable de empresas.
    private int intFechasCot=0;
    private int intFechasFac=0;                             //Conocer cuantas fechas son.
    private int intColTot[];  
    private final Color colFonCol =new Color(255,221,187);  //otro color de celdas naranja

    /**
     * Crea una nueva instancia de la clase ZafVen23
     * @param obj 
     */
    public ZafVen23(ZafParSis obj) 
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objTblCelRenLblColGre = new ZafTblCelRenLbl();
            objTblCelRenLblColOcp = new ZafTblCelRenLbl();
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
        panFil2 = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblEmp = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        lblVen = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        lblLoc = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
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
        panEst = new javax.swing.JPanel();
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(new java.awt.BorderLayout());

        panFil2.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los vendedores");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil2.add(optTod);
        optTod.setBounds(10, 100, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los vendedores que cumplan el criterio seleccionado");
        panFil2.add(optFil);
        optFil.setBounds(10, 120, 400, 20);

        lblEmp.setText("Empresa");
        lblEmp.setToolTipText("Vendedor/Comprador");
        panFil2.add(lblEmp);
        lblEmp.setBounds(30, 150, 100, 20);

        txtCodVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVenFocusLost(evt);
            }
        });
        txtCodVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVenActionPerformed(evt);
            }
        });
        panFil2.add(txtCodVen);
        txtCodVen.setBounds(130, 190, 56, 20);

        txtNomVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomVenFocusLost(evt);
            }
        });
        txtNomVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomVenActionPerformed(evt);
            }
        });
        panFil2.add(txtNomVen);
        txtNomVen.setBounds(187, 190, 460, 20);

        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panFil2.add(butVen);
        butVen.setBounds(647, 190, 20, 20);

        lblVen.setText("Vendedor");
        lblVen.setToolTipText("Vendedor/Comprador");
        panFil2.add(lblVen);
        lblVen.setBounds(30, 190, 100, 20);

        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        panFil2.add(txtCodEmp);
        txtCodEmp.setBounds(130, 150, 56, 20);

        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        panFil2.add(txtNomEmp);
        txtNomEmp.setBounds(187, 150, 460, 20);

        butEmp.setText("...");
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil2.add(butEmp);
        butEmp.setBounds(647, 150, 20, 20);

        lblLoc.setText("Local");
        lblLoc.setToolTipText("Vendedor/Comprador");
        panFil2.add(lblLoc);
        lblLoc.setBounds(30, 170, 100, 20);

        txtCodLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLocFocusLost(evt);
            }
        });
        txtCodLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLocActionPerformed(evt);
            }
        });
        panFil2.add(txtCodLoc);
        txtCodLoc.setBounds(130, 170, 56, 20);

        txtNomLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomLocFocusLost(evt);
            }
        });
        txtNomLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomLocActionPerformed(evt);
            }
        });
        panFil2.add(txtNomLoc);
        txtNomLoc.setBounds(187, 170, 460, 20);

        butLoc.setText("...");
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panFil2.add(butLoc);
        butLoc.setBounds(647, 170, 20, 20);

        panFil.add(panFil2, java.awt.BorderLayout.CENTER);

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
        tblDat.setToolTipText("Doble click o ENTER para abrir la opción seleccionada.");
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(454, 97));

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

        panEst.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panEst.setMinimumSize(new java.awt.Dimension(24, 26));
        panEst.setPreferredSize(new java.awt.Dimension(200, 15));
        panEst.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panEst.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panEst, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
    }//GEN-LAST:event_formInternalFrameOpened

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodLoc.setText("");
            txtNomLoc.setText("");
            txtCodVen.setText("");
            txtNomVen.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        columnaPorcentual=0; 
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

    /**
     * Cerrar Aplicación
     * 
     */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
    configurarVenConVen();
    txtCodVen.transferFocus();
}//GEN-LAST:event_txtCodVenActionPerformed

private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
    strCodVen = txtCodVen.getText();
    txtCodVen.selectAll();
}//GEN-LAST:event_txtCodVenFocusGained

private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtCodVen.getText().equalsIgnoreCase(strCodVen)) 
    {
        if (txtCodVen.getText().equals("")) {
            txtCodVen.setText("");
            txtNomVen.setText("");
        }
        else
        {
            mostrarVenConVen(1);
        }
    }
    else
        txtCodVen.setText(strCodVen);
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtCodVen.getText().length()>0)
    {
        optFil.setSelected(true);
    }
}//GEN-LAST:event_txtCodVenFocusLost

private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
    configurarVenConVen();
    txtNomVen.transferFocus();
}//GEN-LAST:event_txtNomVenActionPerformed

private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
    strNomVen=txtNomVen.getText();
    txtNomVen.selectAll();
}//GEN-LAST:event_txtNomVenFocusGained

private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtNomVen.getText().equalsIgnoreCase(strNomVen))
    {
        if (txtNomVen.getText().equals(""))
        {
            txtCodVen.setText("");
            txtNomVen.setText("");
        }
        else
        {
            mostrarVenConVen(2);
        }
    }
    else
        txtNomVen.setText(strNomVen);
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtNomVen.getText().length()>0)
    {
        optFil.setSelected(true);
    }
}//GEN-LAST:event_txtNomVenFocusLost

private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
    configurarVenConVen();
    mostrarVenConVen(0);
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtCodVen.getText().length()>0)
    {
        optFil.setSelected(true);
        optTod.setSelected(false);
    }
}//GEN-LAST:event_butVenActionPerformed

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
         txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
         strCodEmp=txtCodEmp.getText();
         txtCodEmp.selectAll();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp)) 
        {
            txtCodLoc.setText("");
            txtNomLoc.setText("");
            txtCodVen.setText("");
            txtNomVen.setText("");
            if (txtCodEmp.getText().equals("")) 
            {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            } 
            else 
            {
                mostrarVenConEmp(1);
            }
        }
        else
            txtCodEmp.setText(strCodEmp);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodEmp.getText().length() > 0) 
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        strNomEmp = txtNomEmp.getText();
        txtNomEmp.selectAll();
    }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp)) 
        {
            txtCodLoc.setText("");
            txtNomLoc.setText("");
            txtCodVen.setText("");
            txtNomVen.setText("");
            if (txtNomEmp.getText().equals("")) 
            {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            }
            else 
            {
                mostrarVenConEmp(2);
            }
        }
        else
            txtNomEmp.setText(strNomEmp);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodEmp.getText().length() > 0) 
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        //configurarVenConEmp(); 
        mostrarVenConEmp(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodEmp.getText().length() > 0) 
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        configurarVenConLoc(); 
        txtCodLoc.transferFocus();
    }//GEN-LAST:event_txtCodLocActionPerformed

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        strCodLoc = txtCodLoc.getText();
        txtCodLoc.selectAll();
    }//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc)) 
        {
            txtCodVen.setText("");
            txtNomVen.setText("");
            if (txtCodLoc.getText().equals("")) 
            {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            }
            else 
            {
                mostrarVenConLoc(1);
            }
        }
        else
            txtCodLoc.setText(strCodLoc);
        
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length() > 0) 
        {
            optFil.setSelected(true);
        } 
    }//GEN-LAST:event_txtCodLocFocusLost

    private void txtNomLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLocActionPerformed
        configurarVenConLoc(); 
        txtNomLoc.transferFocus();
    }//GEN-LAST:event_txtNomLocActionPerformed

    private void txtNomLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusGained
        strNomLoc = txtNomLoc.getText();
        txtNomLoc.selectAll();
    }//GEN-LAST:event_txtNomLocFocusGained

    private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
        if (!txtNomLoc.getText().equalsIgnoreCase(strNomLoc)) 
        {
            txtCodVen.setText("");
            txtNomVen.setText("");
            if (txtNomLoc.getText().equals("")) 
            {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            } 
            else
            {
                mostrarVenConLoc(2);
            }
        }
        else
            txtNomLoc.setText(strNomLoc);
        
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0)
        {
           optFil.setSelected(true);
        }           
    }//GEN-LAST:event_txtNomLocFocusLost

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        configurarVenConLoc();
        mostrarVenConLoc(0);
    }//GEN-LAST:event_butLocActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butVen;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panEst;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFil2;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomLoc;
    private javax.swing.JTextField txtNomVen;
    // End of variables declaration//GEN-END:variables

    
    /**
     * Configurar el formulario.
     */
    private boolean configurarFrm() 
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            panFil2.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Obtener los locales por Usuario y Programa.
            objLocPrgUsr=new ZafLocPrgUsr(objParSis);
            //Título de la ventana.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + "v0.9");
            lblTit.setText(strAux);

            //Configurar las ZafVenCon.
            configurarVenConEmp();
            configurarVenConLoc();
            configurarVenConVen();
            
            //Configurar los JTables.
            configurarTblDat();
            
            if(objParSis.getCodigoEmpresa()!= objParSis.getCodigoEmpresaGrupo())
            {
                lblEmp.setVisible(false);
                txtCodEmp.setVisible(false);
                txtNomEmp.setVisible(false);
                butEmp.setVisible(false);
            }

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_NOM_DOC,"Nom.Doc.");
            vecCab.add(INT_TBL_DAT_COD_VEN,"Cód.Ven.");
            vecCab.add(INT_TBL_DAT_NOM_VEN,"Vendedor");
        
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //objTblFil.setFilasEditables(vecFec);
            objTblMod.setFilasEditables(vecDat);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(130);
            tcmAux.getColumn(INT_TBL_DAT_NOM_DOC).setPreferredWidth(130); 
            tcmAux.getColumn(INT_TBL_DAT_COD_VEN).setPreferredWidth(100); 
            tcmAux.getColumn(INT_TBL_DAT_NOM_VEN).setPreferredWidth(100);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            objTblCelRenLbl=null;
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
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
    * Configura Tabla de Datos, volviendose a generar la tabla según los datos lo requieran.
    * @return 
    */
   private boolean configurarTblDatMos()
    {
        int intCol[] = new int[300];
        int b=0,k=0,i;
        String fecha="";
        //intColTot=intFechasCot;
        boolean blnRes=true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();//Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, ""); //0
            vecCab.add(INT_TBL_DAT_NOM_DOC, "Nom.Doc.");//1
            vecCab.add(INT_TBL_DAT_COD_VEN, "Cód.Ven.");//2
            vecCab.add(INT_TBL_DAT_NOM_VEN,"Vendedor");//3
            for(i=4;i<=intFechasCot+3;i++)
            {
               fecha=vecFechasCot.get(k).toString();
               vecCab.add(i,fecha);
               k++;
            }
            vecCab.add(i, "TOTAL");
            vecCab.add(i + 1, "%");
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //objTblMod.setFilasEditables(vecDat);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30); //jota
            tcmAux.getColumn(INT_TBL_DAT_NOM_DOC).setPreferredWidth(130); //jota
            //tcmAux.getColumn(INT_TBL_DAT_COD_VEN).setPreferredWidth(50); //jota
            tcmAux.getColumn(INT_TBL_DAT_NOM_VEN).setPreferredWidth(130);
            //columna oculta
            ArrayList arlColHid = new ArrayList();
            arlColHid.add("" + INT_TBL_DAT_COD_VEN);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid = null;
            for (i = 4; i <= intFechasCot + 3; i++) 
            {
                tcmAux.getColumn(i).setPreferredWidth(100);
                tcmAux.getColumn(i).setCellRenderer(objTblCelRenLblColOcp);
                columnaPorcentual++;
            }
            //totales
            tcmAux.getColumn(i).setPreferredWidth(100);
            tcmAux.getColumn(i).setCellRenderer(objTblCelRenLblColOcp);
            //porcentajes
            tcmAux.getColumn(i + 1).setPreferredWidth(100);
            tcmAux.getColumn(i + 1).setCellRenderer(objTblCelRenLblColOcp);
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            tcmAux.getColumn(INT_TBL_DAT_NOM_DOC).setCellRenderer(objTblCelRenLblColGre);
            tcmAux.getColumn(INT_TBL_DAT_NOM_VEN).setCellRenderer(objTblCelRenLblColGre);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Jota: Aqui se envia las posiciones que quieres que le aparezca a la cabecera 
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus = new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            //tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            objTblCelRenLbl = null;
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            objTblCelRenLbl = null;
             //Configurar JTable: Establecer la clase que controla el ordenamiento.
            //objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            for (i = 4; i <= intFechasCot + 3; i++) 
            {
                intCol[b] = i;
                b++;
            }
            intColTot = intCol;
            //add    
            intCol[b] = i;
            intCol[b + 1] = i + 1;
            // objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
            objTblTot = new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
            objTblTot.insertMoreRows(5);
            objTblCelRenLblColOcp.addTblCelRenListener(new ZafTblCelRenAdapter() {
            @Override
            public void beforeRender(ZafTblCelRenEvent evt) {
                        //pintar las filas 
            int intCell=objTblCelRenLblColOcp.getRowRender();
                if ((intCell >= 0 && intCell < 6) || (intCell >= 12 && intCell < 18) || (intCell >= 24 && intCell < 30)
                        || (intCell >= 36 && intCell < 42) || (intCell >= 48 && intCell < 54) || (intCell >= 60 && intCell < 66)
                        || (intCell >= 72 && intCell < 78) || (intCell >= 84 && intCell < 90) || (intCell >= 96 && intCell < 102)
                        || (intCell >= 108 && intCell < 114) || (intCell >= 120 && intCell < 126) || (intCell >= 132 && intCell < 138)
                        || (intCell >= 144 && intCell < 150) || (intCell >= 156 && intCell < 162) || (intCell >= 168 && intCell < 174)
                        || (intCell >= 180 && intCell < 186) || (intCell >= 192 && intCell < 198) || (intCell >= 204 && intCell < 210)
                        || (intCell >= 216 && intCell < 222) || (intCell >= 234 && intCell < 240) || (intCell >= 246 && intCell < 252)
                        || (intCell >= 258 && intCell < 264) || (intCell >= 270 && intCell < 276) || (intCell >= 282 && intCell < 288)
                        || (intCell >= 294 && intCell < 300) || (intCell >= 306 && intCell < 312) || (intCell >= 318 && intCell < 324)
                        || (intCell >= 330 && intCell < 336) || (intCell >= 342 && intCell < 348) || (intCell >= 354 && intCell < 360)
                        || (intCell >= 366 && intCell < 372) || (intCell >= 378 && intCell < 384) || (intCell >= 390 && intCell < 396)
                        || (intCell >= 402 && intCell < 408) || (intCell >= 414 && intCell < 420) || (intCell >= 426 && intCell < 432)
                        || (intCell >= 438 && intCell < 444) || (intCell >= 450 && intCell < 456) || (intCell >= 462 && intCell < 468)
                        || (intCell >= 474 && intCell < 480) || (intCell >= 486 && intCell < 492)) 
                    {
                        objTblCelRenLblColOcp.setBackground(Color.white);
                        objTblCelRenLblColOcp.setForeground(Color.BLACK);
                        objTblCelRenLblColOcp.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                        objTblCelRenLblColOcp.setTipoFormato(1);
                        objTblCelRenLblColOcp.setFormatoNumerico("###,###.##", false, true);
                    } else {
                        objTblCelRenLblColOcp.setBackground(colFonCol);
                        objTblCelRenLblColOcp.setForeground(Color.BLACK);
                        objTblCelRenLblColOcp.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                        objTblCelRenLblColOcp.setTipoFormato(1);
                        objTblCelRenLblColOcp.setFormatoNumerico("###,###.##", false, true);
                    }
                }
            });
            //  objTblCelRenLblColGre();
            objTblCelRenLblColGre.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) {
                    //pintar las filas 
                    int intCell = objTblCelRenLblColGre.getRowRender();
                    if ((intCell >= 0 && intCell < 6) || (intCell >= 12 && intCell < 18) || (intCell >= 24 && intCell < 30)
                            || (intCell >= 36 && intCell < 42) || (intCell >= 48 && intCell < 54) || (intCell >= 60 && intCell < 66)
                            || (intCell >= 72 && intCell < 78) || (intCell >= 84 && intCell < 90) || (intCell >= 96 && intCell < 102)
                            || (intCell >= 108 && intCell < 114) || (intCell >= 120 && intCell < 126) || (intCell >= 132 && intCell < 138)
                            || (intCell >= 144 && intCell < 150) || (intCell >= 156 && intCell < 162) || (intCell >= 168 && intCell < 174)
                            || (intCell >= 180 && intCell < 186) || (intCell >= 192 && intCell < 198) || (intCell >= 204 && intCell < 210)
                            || (intCell >= 216 && intCell < 222) || (intCell >= 234 && intCell < 240) || (intCell >= 246 && intCell < 252)
                            || (intCell >= 258 && intCell < 264) || (intCell >= 270 && intCell < 276) || (intCell >= 282 && intCell < 288)
                            || (intCell >= 294 && intCell < 300) || (intCell >= 306 && intCell < 312) || (intCell >= 318 && intCell < 324)
                            || (intCell >= 330 && intCell < 336) || (intCell >= 342 && intCell < 348) || (intCell >= 354 && intCell < 360)
                            || (intCell >= 366 && intCell < 372) || (intCell >= 378 && intCell < 384) || (intCell >= 390 && intCell < 396)
                            || (intCell >= 402 && intCell < 408) || (intCell >= 414 && intCell < 420) || (intCell >= 426 && intCell < 432)
                            || (intCell >= 438 && intCell < 444) || (intCell >= 450 && intCell < 456) || (intCell >= 462 && intCell < 468)
                            || (intCell >= 474 && intCell < 480) || (intCell >= 486 && intCell < 492)) {
                        objTblCelRenLblColGre.setBackground(Color.white);
                        objTblCelRenLblColGre.setForeground(Color.BLACK);
                        objTblCelRenLblColGre.setHorizontalAlignment(javax.swing.JLabel.LEFT);
                        objTblCelRenLblColGre.setTipoFormato(1);
                        objTblCelRenLblColGre.setFormatoNumerico("###,###.##", false, true);
                    }
                    else 
                    {
                        objTblCelRenLblColGre.setBackground(colFonCol);
                        objTblCelRenLblColGre.setForeground(Color.BLACK);
                        objTblCelRenLblColGre.setHorizontalAlignment(javax.swing.JLabel.LEFT);
                        objTblCelRenLblColGre.setTipoFormato(1);
                        objTblCelRenLblColGre.setFormatoNumerico("###,###.##", false, true);
                    }
                }
            });
            //Libero los objetos auxiliares.
            tcmAux = null;
        }
        catch (Exception e) {   blnRes = false;   objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
 
    
    private boolean configurarVenConEmp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("374");
            
            //Armar la sentencia SQL.
            if (objParSis.getCodigoUsuario()==1)
            {
                strSQL="";
                strSQL+=" SELECT a1.co_emp as co_emp,a1.tx_nom as tx_nom";
                strSQL+=" FROM tbm_Emp AS a1";
                strSQL+=" WHERE a1.st_reg like 'A' and a1.co_emp<>"+objParSis.getCodigoEmpresaGrupo();
                strSQL+=" ORDER BY a1.co_emp";
            }
            else
            {
                strSQL="";
                strSQL+=" SELECT DISTINCT a4.co_emp as co_emp, a4.tx_nom as tx_nom ";
                strSQL+=" FROM tbr_locPrgUsr as a2";
                strSQL+=" INNER JOIN tbm_loc as a3 ON(a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc)";
                strSQL+=" INNER JOIN tbm_emp as a4 ON(a2.co_emprel=a4.co_emp)";
                strSQL+=" WHERE a2.st_reg !='I'";
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a4.co_emp";
            }
            
            //System.out.println("configurarVenConEmp: " + strSQL);
       
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empresas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
        int intTipLoc=2; //Tipo de Consulta para generar query de locales. 1=Código Local; 2=Todos los datos del local.
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_loc");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.co_emp");
            arlCam.add("a1.Emp");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Loc.");
            arlAli.add("Local");
            arlAli.add("Cód.Emp.");
            arlAli.add("Empresa");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("374");
            arlAncCol.add("50");
            arlAncCol.add("374");
            
            //Armar la sentencia SQL.
            strSQL = "";
            if (objParSis.getCodigoUsuario() == 1) 
            {
                if (txtCodEmp.getText().length() > 0) 
                {
                    strSQL = objLocPrgUsr.cargarLocGrp(intTipLoc, Integer.parseInt(txtCodEmp.getText()));
                }
                else
                {
                    if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) //Grupo
                    {
                        strSQL = objLocPrgUsr.cargarLocGrp(intTipLoc, objParSis.getCodigoEmpresaGrupo());
                    }
                    else 
                    {
                        strSQL = objLocPrgUsr.cargarLoc(intTipLoc);
                    }
                }
            }
            else
            {
                if (txtCodEmp.getText().length() > 0) 
                {
                    strSQL = objLocPrgUsr.cargarLocUsrEmpRel(intTipLoc, Integer.parseInt(txtCodEmp.getText()));
                } 
                else 
                {
                    strSQL = objLocPrgUsr.cargarLocUsr(intTipLoc);
                }
            }
            System.out.println("ConfigurarVenConLoc: "+strSQL);
            
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=3;  //Código Empresa
            intColOcu[0]=4;  //Nombre Empresa
            vcoLoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Locales", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoLoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Vendedores".
     */
    private boolean configurarVenConVen()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_usr");
            arlCam.add("a1.tx_usr");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Usuario");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("120");
            arlAncCol.add("374");
            //Armar la sentencia SQL.
            if (objParSis.getCodigoUsuario() == 1) 
            {
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) 
                {
                    strSQL = " ";
                    strSQL += " SELECT DISTINCT a2.co_usr, a2.tx_usr, a2.tx_nom ";
                    strSQL += " FROM tbr_locPrgUsr as a1 ";
                    strSQL += " INNER JOIN tbr_locusr as a4 ON(a1.co_emprel=a4.co_emp AND a1.co_locrel=a4.co_loc)";
                    strSQL += " INNER JOIN tbm_usr as a2 ON (a4.co_usr=a2.co_usr)";
                    strSQL += " INNER JOIN tbr_usremp as a3 ON (a2.co_usr=a3.co_usr AND a3.co_emp=a4.co_emp)";
                    strSQL += " WHERE a3.st_ven='S' and a2.st_reg='A' and a3.co_emp>0";
                    if (txtCodEmp.getText().length() > 0) 
                    {
                        strSQL += " and a4.co_emp=" + txtCodEmp.getText();
                    }
                    if (txtCodLoc.getText().length() > 0) 
                    {
                        strSQL += " and a4.co_loc=" + txtCodLoc.getText();
                    }
                    strSQL += " GROUP BY a2.co_usr,a2.tx_usr,a2.tx_nom ";
                    strSQL += " ORDER BY a2.tx_nom";
                }
                else 
                {
                    strSQL = " ";
                    strSQL += " SELECT DISTINCT a2.co_usr, a2.tx_usr, a2.tx_nom ";
                    strSQL += " FROM tbr_locPrgUsr as a1 ";
                    strSQL += " INNER JOIN tbr_locusr as a4 ON(a1.co_emprel=a4.co_emp AND a1.co_locrel=a4.co_loc)";
                    strSQL += " INNER JOIN tbm_usr as a2 ON (a4.co_usr=a2.co_usr)";
                    strSQL += " INNER JOIN tbr_usremp as a3 ON (a2.co_usr=a3.co_usr AND a3.co_emp=a4.co_emp)";
                    strSQL += " WHERE a3.st_ven='S' and a2.st_reg='A' and a3.co_emp>0";
                    strSQL += " and a4.co_emp=" + objParSis.getCodigoEmpresa();
                    if (txtCodEmp.getText().length() > 0)
                    {
                        strSQL += " and a4.co_emp=" + txtCodEmp.getText();
                    }
                    if (txtCodLoc.getText().length() > 0) 
                    {
                        strSQL += " and a4.co_loc=" + txtCodLoc.getText();
                    }
                    strSQL += " GROUP BY a2.co_usr,a2.tx_usr,a2.tx_nom ";
                    strSQL += " ORDER BY a2.tx_nom";
                }
            } 
            else 
            {
                strSQL = " ";
                strSQL += " SELECT DISTINCT a2.co_usr, a2.tx_usr, a2.tx_nom ";
                strSQL += " FROM tbr_locPrgUsr as a1 ";
                strSQL += " INNER JOIN tbr_locusr as a4 ON(a1.co_emprel=a4.co_emp AND a1.co_locrel=a4.co_loc)";
                strSQL += " INNER JOIN tbm_usr as a2 ON (a4.co_usr=a2.co_usr)";
                strSQL += " INNER JOIN tbr_usremp as a3 ON (a2.co_usr=a3.co_usr AND a3.co_emp=a4.co_emp)";
                strSQL += " WHERE a3.st_ven='S' and a2.st_reg='A' and a3.co_emp>0";
                strSQL += " ";
                strSQL += " and a1.co_usr=" + objParSis.getCodigoUsuario();
                strSQL += " and a1.co_mnu=" + objParSis.getCodigoMenu();
                strSQL += " and a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " and a1.co_loc=" + objParSis.getCodigoLocal();
                if (txtCodEmp.getText().length() > 0) 
                {
                    strSQL += " and a4.co_emp=" + txtCodEmp.getText();
                }
                if (txtCodLoc.getText().length() > 0) 
                {
                    strSQL += " and a4.co_loc=" + txtCodLoc.getText();
                }
                strSQL += " GROUP BY a2.co_usr,a2.tx_usr,a2.tx_nom ";
                strSQL += " ORDER BY a2.tx_nom";
            }
            //System.out.println("configurarVenConVen: " + strSQL);
            vcoVen = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de vendedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoVen.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } 
        catch (Exception e) {
            blnRes = false;
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
    private boolean mostrarVenConEmp(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: 
                    vcoEmp.setCampoBusqueda(1);
                    vcoEmp.setVisible(true);
                    if (vcoEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                        txtCodLoc.setText("");
                        txtNomLoc.setText("");
                        txtCodVen.setText("");
                        txtNomVen.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                        txtCodLoc.setText("");
                        txtNomLoc.setText("");
                        txtCodVen.setText("");
                        txtNomVen.setText("");
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.setVisible(true);
                        if (vcoEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                            txtCodLoc.setText("");
                            txtNomLoc.setText("");
                            txtCodVen.setText("");
                            txtNomVen.setText("");
                        }
                        else
                        {
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                        txtCodLoc.setText("");
                        txtNomLoc.setText("");
                        txtCodVen.setText("");
                        txtNomVen.setText("");
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.setVisible(true);
                        if (vcoEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                            txtCodLoc.setText("");
                            txtNomLoc.setText("");
                            txtCodVen.setText("");
                            txtNomVen.setText("");
                        }
                        else
                        {
                            txtNomEmp.setText(strNomEmp);
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
    
    private boolean mostrarVenConLoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: 
                    vcoLoc.setCampoBusqueda(1);
                    vcoLoc.setVisible(true);
                    if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                        txtCodEmp.setText(vcoLoc.getValueAt(3));
                        txtNomEmp.setText(vcoLoc.getValueAt(4));
                        txtCodVen.setText("");
                        txtNomVen.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoLoc.buscar("a1.co_loc", txtCodLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                        txtCodEmp.setText(vcoLoc.getValueAt(3));
                        txtNomEmp.setText(vcoLoc.getValueAt(4));
                        txtCodVen.setText("");
                        txtNomVen.setText("");
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(0);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                            txtCodEmp.setText(vcoLoc.getValueAt(3));
                            txtNomEmp.setText(vcoLoc.getValueAt(4));
                            txtCodVen.setText("");
                            txtNomVen.setText("");
                        }
                        else
                        {
                            txtCodLoc.setText(strCodLoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoLoc.buscar("a1.tx_nom", txtNomLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                        txtCodEmp.setText(vcoLoc.getValueAt(3));
                        txtNomEmp.setText(vcoLoc.getValueAt(4));
                        txtCodVen.setText("");
                        txtNomVen.setText("");
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(1);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                            txtCodEmp.setText(vcoLoc.getValueAt(3));
                            txtNomEmp.setText(vcoLoc.getValueAt(4));
                            txtCodVen.setText("");
                            txtNomVen.setText("");
                        }
                        else
                        {
                            txtNomLoc.setText(strNomLoc);
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
    private boolean mostrarVenConVen(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoVen.setCampoBusqueda(2);
                    vcoVen.setVisible(true);
                    if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoVen.buscar("a1.co_usr", txtCodVen.getText()))
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    else
                    {
                        vcoVen.setCampoBusqueda(0);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.setVisible(true);
                        if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtNomVen.setText(vcoVen.getValueAt(3));
                        }
                        else
                        {
                            txtCodVen.setText(strCodVen);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoVen.buscar("a1.tx_nom", txtNomVen.getText()))
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    else
                    {
                        vcoVen.setCampoBusqueda(2);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.setVisible(true);
                        if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtNomVen.setText(vcoVen.getValueAt(3));
                        }
                        else
                        {
                            txtNomVen.setText(strNomVen);
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     *
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg() 
    {
        int i, pos = 4, temp = 0, b = 0, d = 0, contCotCount = 0, contFacCount = 0, contCotSum = 0, contFacSum = 0, cont = 0, dato = 0, ban = 0, j = 0, acum = 0;
        int contDevCount = 0, contDevSum = 0;
        double valCotizacion = 0, valFacturas = 0, valDevolucion = 0, valor = 0;
        String strConSQLFec = "", vendedor = "", strSQLFecFac = "", strSQL2 = "";
        double acumD = 0;
        boolean blnRes = true;
        objUti=new ZafUtil();
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm = con.createStatement();
                //Obtener la condición.
                strConSQL = "";
                strConSQLFec = "";
                switch (objSelFec.getTipoSeleccion())
                {
                    case 0: //Búsqueda por rangos
                        strConSQLFec += " AND c.fe_cot BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        strSQLFecFac += " AND c.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strConSQLFec += " AND  c.fe_cot<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        strSQLFecFac += " AND  c.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strConSQLFec += " AND c.fe_cot>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        strSQLFecFac += " AND c.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo....
                        break;
                }
                if(objParSis.getCodigoUsuario()==1)
                {
                    if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) 
                    {
                        if (txtCodVen.getText().length() > 0) 
                        {
                            strConSQL += " and c.co_ven=" + txtCodVen.getText();
                            strSQL2 += " and c.co_com=" + txtCodVen.getText();
                        }
                        if (txtCodEmp.getText().length() > 0) 
                        {
                            strConSQL += " and c.co_emp=" + txtCodEmp.getText();
                            strSQL2 += " and c.co_emp=" + txtCodEmp.getText();
                        }
                        if (txtCodLoc.getText().length() > 0) 
                        {
                            strConSQL += " and c.co_loc=" + txtCodLoc.getText();
                            strSQL2 += " and c.co_loc=" + txtCodLoc.getText();
                        }
                    } 
                    else 
                    {
                        if (txtCodVen.getText().length() > 0) 
                        {
                            strConSQL += " and c.co_ven=" + txtCodVen.getText();
                            strSQL2 += " and c.co_com=" + txtCodVen.getText();
                        }
                        if (txtCodEmp.getText().length() > 0) 
                        {
                            strConSQL += " and c.co_emp=" + txtCodEmp.getText();
                            strSQL2 += " and c.co_emp=" + txtCodEmp.getText();
                        }
                        if (txtCodLoc.getText().length() > 0) 
                        {
                            strConSQL += " and c.co_loc=" + txtCodLoc.getText();
                            strSQL2 += " and c.co_loc=" + txtCodLoc.getText();
                        }
                        strConSQL += " and c.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL2 += " and c.co_emp=" + objParSis.getCodigoEmpresa();
                    }
                } 
                else 
                { //SI NO ES ADMINISTRADOR
                    if (txtCodVen.getText().length() > 0) 
                    {
                        strConSQL += " and c.co_ven=" + txtCodVen.getText();
                        strSQL2 += " and c.co_com=" + txtCodVen.getText();
                    }
                    if (txtCodEmp.getText().length() > 0)
                    {
                        strConSQL += " and c.co_emp=" + txtCodEmp.getText();
                        strSQL2 += " and c.co_emp=" + txtCodEmp.getText();
                    }
                    else 
                    {
                        strConSQL += " and c.co_emp in (SELECT DISTINCT  a3.co_emp FROM tbr_locPrgUsr as a2 ";
                        strConSQL += " INNER JOIN tbm_loc as a3 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc) WHERE 1=1";
                        strConSQL += " and a2.co_mnu=" + objParSis.getCodigoMenu();
                        strConSQL += " and a2.co_emp=" + objParSis.getCodigoEmpresa();
                        strConSQL += " and a2.co_loc=" + objParSis.getCodigoLocal();
                        strConSQL += " and a2.st_reg in('A','P') and a2.co_usr=" + objParSis.getCodigoUsuario() + ")";
                        strSQL2 += " and c.co_emp in (SELECT DISTINCT  a3.co_emp FROM tbr_locPrgUsr as a2 ";
                        strSQL2 += " INNER JOIN tbm_loc as a3 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc) WHERE 1=1";
                        strSQL2 += " and a2.co_mnu=" + objParSis.getCodigoMenu();
                        strSQL2 += " and a2.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL2 += " and a2.co_loc=" + objParSis.getCodigoLocal();
                        strSQL2 += " and a2.st_reg in('A','P') and a2.co_usr=" + objParSis.getCodigoUsuario() + ")";
                    }
                    if (txtCodLoc.getText().length() > 0) 
                    {
                        strConSQL += " and c.co_loc=" + txtCodLoc.getText();
                        strSQL2 += " and c.co_loc=" + txtCodLoc.getText();
                    }
                    else 
                    {
                        strConSQL += " and c.co_loc in (SELECT DISTINCT  a3.co_loc FROM tbr_locPrgUsr as a2 ";
                        strConSQL += " INNER JOIN tbm_loc as a3 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc) WHERE 1=1";
                        strConSQL += " and a2.co_mnu=" + objParSis.getCodigoMenu();
                        strConSQL += " and a2.co_emp=" + objParSis.getCodigoEmpresa();
                        strConSQL += " and a2.co_loc=" + objParSis.getCodigoLocal();
                        strConSQL += " and a2.st_reg in('A','P') and a2.co_usr=" + objParSis.getCodigoUsuario() + ")";
                        strSQL2 += " and c.co_loc in (SELECT DISTINCT  a3.co_loc FROM tbr_locPrgUsr as a2 ";
                        strSQL2 += " INNER JOIN tbm_loc as a3 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc) WHERE 1=1";
                        strSQL2 += " and a2.co_mnu=" + objParSis.getCodigoMenu();
                        strSQL2 += " and a2.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL2 += " and a2.co_loc=" + objParSis.getCodigoLocal();
                        strSQL2 += " and a2.st_reg in('A','P') and a2.co_usr=" + objParSis.getCodigoUsuario() + ")";
                    }
                }
                //System.out.println("strSQL2: " + strSQL2 + " ************************");
                strConSQL+=strConSQLFec;
                strSQL2+=strSQLFecFac;
                strSQL = "";
                strSQL += " ";
                strSQL += " select a.fe_cot, a.co_usr ,a.tx_nom, b.conteo, b.suma AS suma, c.conteoFac, ROUND(c.sumaFac,2) as sumaFac, d.conteoDev as conteoDev, d.devFac as sumaDev from (";
                strSQL += " select co_usr, fe_cot, tx_nom from (";
                strSQL += " select distinct c.fe_doc as fe_cot";
                strSQL += " from tbm_cabmovinv as c  ";
                strSQL += " INNER JOIN tbm_cli as a7 ON (c.co_emp=a7.co_emp and c.co_cli=a7.co_cli) where 1=1";
                strSQL += " and c.co_tipdoc in ( select co_tipdoc from tbr_tipdocprg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu=" + objParSis.getCodigoMenu() + ")";//rose Consulta los tipDoc del Programa                       
                strSQL += " and a7.co_empGrp IS NULL AND c.st_reg IN ('A','R','C','F') ";
                strSQL += strSQL2;
                strSQL += " union";
                strSQL += " select distinct c.fe_cot as fe_cot";
                strSQL += " from tbm_cabcotven c";
                strSQL += " where 1=1 ";
                strSQL += strConSQL;
                strSQL += " ) as x inner join (";
                strSQL += " select distinct u.co_usr , u.tx_nom";
                strSQL += " from tbm_cabcotven as c";
                strSQL += " INNER JOIN tbm_usr as u ON(u.co_usr=c.co_ven)";
                strSQL += " where 1=1 ";
                strSQL += strConSQL;
                strSQL += " union";
                strSQL += " select distinct u.co_usr , u.tx_nom";
                strSQL += " from tbm_cabcotven as c ";
                strSQL += " INNER JOIN tbm_usr as u ON (u.co_usr = c.co_ven)";
                strSQL += " INNER JOIN tbr_usremp as a3 ON (c.co_emp=a3.co_emp and u.co_usr=a3.co_usr)";
                strSQL += " INNER JOIN tbr_locusr as a4 ON (c.co_loc=a4.co_loc and c.co_emp=a4.co_emp)";
                strSQL += " where a3.st_ven='S' ";
                strSQL += " ";
                strSQL += strConSQL;
                strSQL += " ) as y on (x.fe_cot != '1900-01-01')";
                strSQL += " ) AS a";
                //mod
                strSQL += " FULL outer join ( ";
                strSQL += " select u.co_usr, c.fe_cot, count(c.co_cot) as conteo, sum(c.nd_sub) as suma";
                strSQL += " from tbm_cabcotven as c ";
                strSQL += " INNER JOIN tbm_usr as u ON (c.co_ven = u.co_usr)";
                strSQL += " where 1=1 ";
                strSQL += strConSQL;
                strSQL += " group by u.co_usr, c.fe_cot";
                strSQL += " ) AS b ";
                strSQL += " on (a.co_usr = b.co_usr and a.fe_cot = b.fe_cot) ";
                strSQL += " FULL outer join ( ";
                strSQL += " SELECT c.co_com as co_usr ,c.fe_doc as fe_cot, count(c.co_doc) as conteoFac, round(-sum(c.nd_sub),2) as sumaFac ";
                strSQL += " FROM tbm_cabmovinv as c ";
                strSQL += " INNER JOIN tbm_cabTipDoc as a4 ON (c.co_emp=a4.co_emp AND c.co_loc=a4.co_loc AND c.co_tipDoc=a4.co_tipDoc)";
                strSQL += " INNER JOIN tbm_cli as a7 ON (c.co_emp=a7.co_emp and c.co_cli=a7.co_cli)";
                strSQL += " WHERE 1=1 AND c.st_reg IN ('A','R','C','F') ";

                /*Realiza la consulta en base al Tipos Documento que tiene el Programa en la empresa y Local Respectivo y Tipos Documento que existen en Factura Comercial (co_mnu=14) --rose*/
                strSQL += " and c.co_tipdoc IN (select DISTINCT t.co_tipdoc from tbr_tipdocprg as t";
                /*Cuando es GRUPO consulta los tipos de Documentos en alguna empresa, si los tiene presenta el reporte*/
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) {
                    strSQL += " INNER JOIN tbm_cabmovinv as f ON (t.co_tipdoc=f.co_tipdoc)";
                } else {
                    strSQL += " INNER JOIN tbm_cabmovinv as f ON (t.co_tipdoc=f.co_tipdoc and t.co_emp=f.co_emp and t.co_loc=f.co_loc)";
                }
                strSQL += " WHERE t.co_emp=" + objParSis.getCodigoEmpresa() + " and t.co_loc=" + objParSis.getCodigoLocal() + " and t.co_mnu=" + objParSis.getCodigoMenu() + " and f.co_mnu=14)";

                strSQL += " and a7.co_empGrp IS NULL ";
                strSQL += strSQL2;
                strSQL += " group by c.co_com, c.fe_doc";
                strSQL += " order by 1,2";
                strSQL += " )as c ";
                strSQL += " on (a.co_usr=c.co_usr and a.fe_cot=c.fe_cot) ";
                strSQL += " left outer join ( ";
                strSQL += " SELECT c.co_com as co_usr,c.fe_doc as fe_cot ,count(c.co_doc) as conteoDev ,round(-sum(c.nd_sub),2) as devFac ";
                strSQL += " FROM tbm_cabmovinv as c";
                strSQL += " INNER JOIN tbm_cabTipDoc as a4 ON (c.co_emp=a4.co_emp AND c.co_loc=a4.co_loc AND c.co_tipDoc=a4.co_tipDoc) ";
                strSQL += " INNER JOIN tbm_cli as a7 ON (c.co_emp=a7.co_emp and c.co_cli=a7.co_cli)";
                strSQL += " WHERE 1=1 AND c.st_reg IN ('A','R','C','F')";

                /*Realiza la consulta en base al Tipos Documento que tiene el Programa en la empresa y Local Respectivo y Tipos Documento que existen en Devoluciones Ventas (co_mnu=1918) --rose*/
                strSQL += " and c.co_tipdoc IN (select DISTINCT t.co_tipdoc from tbr_tipdocprg as t";
                /*Cuando es GRUPO consulta los tipos de Documentos en alguna empresa, si los tiene presenta el reporte*/
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) {
                    strSQL += " INNER JOIN tbm_cabmovinv as f ON (t.co_tipdoc=f.co_tipdoc)";
                } else {
                    strSQL += " INNER JOIN tbm_cabmovinv as f ON (t.co_tipdoc=f.co_tipdoc and t.co_emp=f.co_emp and t.co_loc=f.co_loc)";
                }
                strSQL += " WHERE t.co_emp=" + objParSis.getCodigoEmpresa() + " and t.co_loc=" + objParSis.getCodigoLocal() + " and t.co_mnu=" + objParSis.getCodigoMenu() + " and f.co_mnu=1918) ";

                strSQL += " and a7.co_empGrp IS NULL ";
                strSQL += strSQL2;
                strSQL += " group by c.co_com, c.fe_doc)as d ";
                strSQL += " on (a.co_usr = d.co_usr and a.fe_cot = d.fe_cot) ";
                strSQL += " order by  a.co_usr ,a.fe_cot";
                //System.out.println("zafVen23 " + strSQL);
                rst = stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                i = 0;
                vecFechasCot = new Vector();
                vecSumaCot = new Vector();
                vecCountCot = new Vector();
                vecCodVenCot = new Vector();
                vecNomVen = new Vector();
                vecSumaFac = new Vector();
                vecCountFac = new Vector();
                vecDevCountFac = new Vector();
                vecDevSumaFac=new Vector();
            
                while (rst.next()) {
                    if (blnCon) {
                        vecCodVenCot.add(j, rst.getString("co_usr"));
                        vecNomVen.add(j, rst.getString("tx_nom"));
                        vecFechasCot.add(j, rst.getString("fe_cot"));
                        vecCountCot.add(j, rst.getString("conteo"));
                        vecSumaCot.add(j, rst.getString("suma"));
                        vecCountFac.add(j, rst.getString("conteoFac"));
                        vecSumaFac.add(j, rst.getString("sumaFac"));
                        vecDevCountFac.add(j, rst.getString("conteoDev"));
                        vecDevSumaFac.add(j, rst.getString("sumaDev"));
                        temp++;
                        j++;
                    } else {
                        break;
                    }
                }
                j = 0;
                i = 1;
                b = 0;
                if (txtCodVen.getText().length() > 0) {
                    if (!vecCodVenCot.isEmpty()) {
                        vendedor = vecNomVen.get(b).toString();
                        intFechasCot = 0;
                        while (dato + 2 <= temp) {
                            if (vecNomVen.get(dato).equals(vendedor)) {
                                cont++;
                                dato++;
                                intFechasCot++;
                                intFechasFac++;
                            } else {
                                ban = 1;
                            }
                        }
                        if (ban == 0) {
                            cont++;
                            dato++;
                            intFechasCot++;
                        }
                        if (cont == 0) {
                            cont = 1;
                            intFechasCot = 1;
                        }
                 //temp = 60 registros totales encontrados
                        //cont = registros por vendedor
                        d = ((temp / cont) * 6);
                        configurarTblDatMos();
                        while (d > 0) {
                            vecReg = new Vector();
                            pos = 4;
                            vecReg.add(INT_TBL_DAT_LIN, "");
                            if (i == 1) {
                                vecReg.add(INT_TBL_DAT_NOM_DOC, "COTIZACION (#)");
                                if (vecCodVenCot.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, vecCodVenCot.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, " ");
                                }
                                if (vecNomVen.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, vecNomVen.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, " ");
                                }
                                b = 0;
                                acum = 0;
                                while (cont > b) {
                                    if (vecCountCot.get(contCotCount) != null) {
                                        vecReg.add(pos, vecCountCot.get(contCotCount).toString());
                                        acum = acum + Integer.parseInt(vecCountCot.get(contCotCount).toString());
                                    } else {
                                        vecReg.add(pos, " ");
                                        acum = acum + 0;
                                    }
                                    pos++;
                                    b++;
                                    contCotCount++;
                                }
                                vecReg.add(pos, acum);
                                valCotizacion = acum;
                                vecReg.add(pos + 1, "");
                            }
                            pos = 4;
                            b = 0;
                            if (i == 2) {
                                vecReg.add(INT_TBL_DAT_NOM_DOC, "COTIZACION ($)");
                                if (vecCodVenCot.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, vecCodVenCot.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, "");
                                }
                                if (vecNomVen.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, vecNomVen.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, "");
                                }
                                acumD = 0;
                                while (cont > b) {
                                    if (vecSumaCot.get(contCotSum) != null) {
                                        vecReg.add(pos, vecSumaCot.get(contCotSum).toString());
                                        acumD = acumD + Double.parseDouble(vecSumaCot.get(contCotSum).toString());
                                    } else {
                                        acumD = acumD + 0;
                                        vecReg.add(pos, " ");
                                    }
                                    pos++;
                                    b++;
                                    contCotSum++;
                                }
                                acumD = objUti.redondear(acumD, 2);
                                vecReg.add(pos, acumD);//valor cotizaciones $$$
                                vecReg.add(pos + 1, valCotizacion);
                            }
                            pos = 4;
                            b = 0;
                            if (i == 3) {
                                vecReg.add(INT_TBL_DAT_NOM_DOC, "FACTURAS (#)");
                                if (vecCodVenCot.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, vecCodVenCot.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, "");
                                }
                                if (vecNomVen.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, vecNomVen.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, "");
                                }
                                acum = 0;
                                while (cont > b) {
                                    if (vecCountFac.get(contFacCount) != null) {
                                        vecReg.add(pos, vecCountFac.get(contFacCount).toString());
                                        acum = acum + Integer.parseInt(vecCountFac.get(contFacCount).toString());
                                    } else {
                                        acum = acum + 0;
                                        vecReg.add(pos, " ");
                                    }
                                    pos++;
                                    b++;
                                    contFacCount++;
                                }
                                vecReg.add(pos, acum);
                                vecReg.add(pos + 1, "");
                            }
                            pos = 4;
                            b = 0;
                            if (i == 4) {
                                vecReg.add(INT_TBL_DAT_NOM_DOC, "FACTURAS ($)");
                                if (vecCodVenCot.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, vecCodVenCot.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, "");
                                }
                                if (vecNomVen.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, vecNomVen.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, "");
                                }
                                acumD = 0;
                                while (cont > b) {
                                    if (vecSumaFac.get(contFacSum) != null) {
                                        vecReg.add(pos, vecSumaFac.get(contFacSum).toString());
                                        acumD = acumD + Double.parseDouble(vecSumaFac.get(contFacSum).toString());
                                    } else {
                                        vecReg.add(pos, " ");
                                        acumD = acumD + 0;
                                    }
                                    pos++;
                                    b++;
                                    contFacSum++;
                                }
                                acumD = objUti.redondear(acumD, 2);
                                vecReg.add(pos, acumD);
                                valFacturas = acumD;
                                valor = (valFacturas / valCotizacion) * 100;
                                valor = objUti.redondear(valor, 2);
                                vecReg.add(pos + 1, valor);
                                valor = 0;
                            }
                            pos = 4;
                            b = 0;
                            if (i == 5) {
                                vecReg.add(INT_TBL_DAT_NOM_DOC, "DEVOLUCIONES (#)");
                                if (vecCodVenCot.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, vecCodVenCot.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, "");
                                }
                                if (vecNomVen.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, vecNomVen.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, "");
                                }
                                acum = 0;
                                while (cont > b) {
                                    if (vecDevCountFac.get(contDevCount) != null) {
                                        vecReg.add(pos, vecDevCountFac.get(contDevCount).toString());
                                        acum = acum + Integer.parseInt(vecDevCountFac.get(contDevCount).toString());
                                    } else {
                                        vecReg.add(pos, " ");
                                        acum = acum + 0;
                                    }
                                    pos++;
                                    b++;
                                    contDevCount++;
                                }
                                vecReg.add(pos, acum);
                                vecReg.add(pos + 1, "");
                            }
                            pos = 4;
                            b = 0;
                            if (i == 6) {
                                vecReg.add(INT_TBL_DAT_NOM_DOC, "DEVOLUCIONES ($)");
                                if (vecCodVenCot.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, vecCodVenCot.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, "");
                                }
                                if (vecNomVen.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, vecNomVen.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, "");
                                }
                                acumD = 0;
                                while (cont > b) {
                                    if (vecDevSumaFac.get(contDevSum) != null) {
                                        vecReg.add(pos, vecDevSumaFac.get(contDevSum).toString());
                                        acumD = acumD + Double.parseDouble(vecDevSumaFac.get(contDevSum).toString());
                                    } else {
                                        vecReg.add(pos, " ");
                                        acumD = acumD + 0;
                                    }
                                    pos++;
                                    b++;
                                    contDevSum++;
                                }
                                acumD = objUti.redondear(acumD, 2);
                                vecReg.add(pos, acumD);
                                valDevolucion = acumD;
                                valor = (valDevolucion / valCotizacion) * 100;
                                valor = objUti.redondear(valor, 2);
                                vecReg.add(pos + 1, valor);
                                valFacturas = 0;
                                valDevolucion = 0;
                                valCotizacion = 0;
                                valor = 0;
                                i = 0;
                                j = j + dato;
                            }
                            pos = 4;
                            b = 0;
                            b = 0;
                            vecDat.add(vecReg);
                            i++;
                            d--;
                        }
                        //Asignar vectores al modelo.
                        objTblMod.setData(vecDat);
                        tblDat.setModel(objTblMod);
                        vecDat.clear();
                        //Calcular totales.
                        calcularTotales2();
                        if (blnCon) {
                            lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() / 6 + " registros.");
                        } else {
                            lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                        }
                    } else {
                        lblMsgSis.setText("No Se encontraron registros.");
                        configurarTblDat();
                    }
                } else {
                    if (!vecCodVenCot.isEmpty()) {
                        vendedor = vecNomVen.get(b).toString();
                        intFechasCot = 0;
                        while (vecNomVen.get(dato).equals(vendedor) && dato + 2 <= temp) {
                            cont++;
                            dato++;
                            intFechasCot++;
                            intFechasFac++;
                        }
                        //temp = 60 registros
                        //cont = registros por vendedor
                        if (cont == 0) {
                            cont = 1;
                            intFechasCot = 1;
                        }
                        d = ((temp / cont) * 6);//son 6
                        configurarTblDatMos();
                        while (d > 0) {
                            vecReg = new Vector();
                            vecReg.add(INT_TBL_DAT_LIN, "");
                            pos = 4;
                            if (i == 1) {
                                vecReg.add(INT_TBL_DAT_NOM_DOC, "COTIZACION (#)");
                                if (vecCodVenCot.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, vecCodVenCot.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, " ");
                                }
                                if (vecNomVen.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, vecNomVen.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, " ");
                                }
                                b = 0;
                                acum = 0;
                                while (cont > b) {
                                    if (vecCountCot.get(contCotCount) != null) {
                                        vecReg.add(pos, vecCountCot.get(contCotCount).toString());
                                        acum = acum + Integer.parseInt(vecCountCot.get(contCotCount).toString());
                                    } else {
                                        vecReg.add(pos, " ");
                                        acum = acum + 0;
                                    }
                                    pos++;
                                    b++;
                                    contCotCount++;
                                }
                                vecReg.add(pos, acum);
                                valCotizacion = acum;
                                vecReg.add(pos + 1, "");
                            }
                            pos = 4;
                            b = 0;
                            if (i == 2) {
                                vecReg.add(INT_TBL_DAT_NOM_DOC, "COTIZACION ($)");
                                if (vecCodVenCot.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, vecCodVenCot.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, "");
                                }
                                if (vecNomVen.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, vecNomVen.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, "");
                                }
                                acumD = 0;
                                while (cont > b) {
                                    if (vecSumaCot.get(contCotSum) != null) {
                                        vecReg.add(pos, vecSumaCot.get(contCotSum).toString());
                                        acumD = acumD + Double.parseDouble(vecSumaCot.get(contCotSum).toString());
                                    } else {
                                        acumD = acumD + 0;
                                        vecReg.add(pos, " ");
                                    }
                                    pos++;
                                    b++;
                                    contCotSum++;
                                }
                                acumD = objUti.redondear(acumD, 2);
                                vecReg.add(pos, acumD);
                                vecReg.add(pos + 1, valCotizacion);
                            }
                            pos = 4;
                            b = 0;
                            if (i == 3) {
                                vecReg.add(INT_TBL_DAT_NOM_DOC, "FACTURA (#)");
                                if (vecCodVenCot.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, vecCodVenCot.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, "");
                                }
                                if (vecNomVen.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, vecNomVen.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, "");
                                }
                                acum = 0;
                                while (cont > b) {
                                    if (vecCountFac.get(contFacCount) != null) {
                                        vecReg.add(pos, vecCountFac.get(contFacCount).toString());
                                        acum = acum + Integer.parseInt(vecCountFac.get(contFacCount).toString());
                                    } else {
                                        acum = acum + 0;
                                        vecReg.add(pos, " ");
                                    }
                                    pos++;
                                    b++;
                                    contFacCount++;
                                }
                                vecReg.add(pos, acum);
                                vecReg.add(pos + 1, "");
                            }
                            pos = 4;
                            b = 0;
                            if (i == 4) {
                                vecReg.add(INT_TBL_DAT_NOM_DOC, "FACTURA ($)");
                                if (vecCodVenCot.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, vecCodVenCot.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, "");
                                }
                                if (vecNomVen.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, vecNomVen.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, "");
                                }
                                acumD = 0;
                                while (cont > b) {
                                    if (vecSumaFac.get(contFacSum) != null) {
                                        vecReg.add(pos, vecSumaFac.get(contFacSum).toString());
                                        acumD = acumD + Double.parseDouble(vecSumaFac.get(contFacSum).toString());
                                    } else {
                                        vecReg.add(pos, " ");
                                        acumD = acumD + 0;
                                    }
                                    pos++;
                                    b++;
                                    contFacSum++;
                                }
                                acumD = objUti.redondear(acumD, 2);
                                vecReg.add(pos, acumD);
                                valFacturas = acumD;
                                valor = (valFacturas / valCotizacion) * 100;
                                valor = objUti.redondear(valor, 2);
                                vecReg.add(pos + 1, valor);
                                valor = 0;

                            }

                            pos = 4;
                            b = 0;
                            if (i == 5) {
                                vecReg.add(INT_TBL_DAT_NOM_DOC, "DEVOLUCIONES (#)");
                                if (vecCodVenCot.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, vecCodVenCot.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, "");
                                }
                                if (vecNomVen.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, vecNomVen.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, "");
                                }
                                acum = 0;
                                while (cont > b) {
                                    if (vecDevCountFac.get(contDevCount) != null) {
                                        vecReg.add(pos, vecDevCountFac.get(contDevCount).toString());
                                        acum = acum + Integer.parseInt(vecDevCountFac.get(contDevCount).toString());
                                    } else {
                                        vecReg.add(pos, " ");
                                        acum = acum + 0;
                                    }
                                    pos++;
                                    b++;
                                    contDevCount++;
                                }
                                vecReg.add(pos, acum);
                                vecReg.add(pos + 1, "");
                            }
                            pos = 4;
                            b = 0;
                            if (i == 6) {
                                vecReg.add(INT_TBL_DAT_NOM_DOC, "DEVOLUCIONES ($)");
                                if (vecCodVenCot.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, vecCodVenCot.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_COD_VEN, "");
                                }
                                if (vecNomVen.get(j) != null) {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, vecNomVen.get(j).toString());
                                } else {
                                    vecReg.add(INT_TBL_DAT_NOM_VEN, "");
                                }
                                acumD = 0;
                                while (cont > b) {
                                    if (vecDevSumaFac.get(contDevSum) != null) {
                                        vecReg.add(pos, vecDevSumaFac.get(contDevSum).toString());
                                        acumD = acumD + Double.parseDouble(vecDevSumaFac.get(contDevSum).toString());
                                    } else {
                                        vecReg.add(pos, " ");
                                        acumD = acumD + 0;
                                    }
                                    pos++;
                                    b++;
                                    contDevSum++;
                                }
                                acumD = objUti.redondear(acumD, 2);
                                vecReg.add(pos, acumD);
                                valDevolucion = acumD;
                                valor = (valDevolucion / valCotizacion) * 100;
                                valor = objUti.redondear(valor, 2);
                                vecReg.add(pos + 1, valor);
                                valFacturas = 0;
                                valDevolucion = 0;
                                valCotizacion = 0;
                                valor = 0;

                                i = 0;
                                j = j + dato;
                            }
                            pos = 4;
                            b = 0;
                            b = 0;
                            vecDat.add(vecReg);
                            i++;
                            d--;
                        }
                        //Asignar vectores al modelo.
                        objTblMod.setData(vecDat);
                        tblDat.setModel(objTblMod);
                        vecDat.clear();
                        //Calcular totales.
                        calcularTotales2();
                        if (blnCon) {
                            lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() / 6 + " registros.");
                        } else {
                            lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                        }
                    } else {
                        lblMsgSis.setText("No Se encontraron registros.");
                        configurarTblDat();
                    }
                }
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;

                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) 
    {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
            // configurarTblDatMos();   // APARECE NUEVA TABLA LISTA PARA SER LLENADA XD 
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
            int b=intCol;
            String strMsg="";
            if (intCol>INT_TBL_DAT_NOM_VEN) 
            {
                intCol=(intCol - 4) % columnaPorcentual + 4;
            }
            switch (intCol){
                case INT_TBL_DAT_NOM_DOC:   //1
                    strMsg="Nombre del Documento";
                    break;
                case INT_TBL_DAT_COD_VEN:  //2
                    strMsg="Codigo del vendedor";
                    break;
                case INT_TBL_DAT_NOM_VEN: //3
                    strMsg="Nombre del vendedor";
                    break;
                case 5:
                    if(b!=5)
                    strMsg="<html> % Facturas ($)=(Facturas ($)/Cotizaciones(#))*100 \n<br>  % Devoluciones ($)=(Devoluciones ($)/Cotizaciones(#))*100</html>";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }    
    }
    
     public boolean calcularTotales2()
     {
        int intNumFil, intNumCol, i, j, temp1 = 0, temp2 = 0;
        boolean blnRes = true;
        try 
        {
            intNumFil = tblDat.getRowCount();
            intNumCol = intColTot.length;
            double fact = 0, coti = 0, dev = 0, totFact = 0, totDev = 0;
            double dblTot[]=new double[intNumCol];
            double dblTot1[]=new double[intNumCol];
            double dblTot2[]=new double[intNumCol];
            double dblTot3[]=new double[intNumCol];
            double dblTot4[]=new double[intNumCol];
            double dblTot5[]=new double[intNumCol];
            for(i=0; i<intNumFil; i=i+6)
            {
                for(j=0; j<intNumCol; j++)
                {    dblTot[j]+=objUti.parseDouble(tblDat.getValueAt(i, intColTot[j]));
                    if(dblTot[j]>0) {
                        coti=dblTot[j];
                    }
                }
            }
            for(i=1; i<intNumFil; i=i+6){
                for (j=0; j<intNumCol; j++){  
                    dblTot1[j]+=objUti.parseDouble(tblDat.getValueAt(i, intColTot[j]));
                }
            }
            for(i=2; i<intNumFil; i=i+6){
                for (j=0; j<intNumCol; j++)
                { dblTot2[j]+=objUti.parseDouble(tblDat.getValueAt(i, intColTot[j]));}
            }
            for(i=3; i<intNumFil; i=i+6){
                for (j=0; j<intNumCol; j++){  
                    dblTot3[j]+=objUti.parseDouble(tblDat.getValueAt(i, intColTot[j]));
                    if(dblTot3[j]>0) {
                        temp1=j-1;
                        fact=dblTot3[j];
                    }
                }
            }
            for(i=4; i<intNumFil; i=i+6){
                for (j=0; j<intNumCol; j++)
                {  dblTot4[j]+=objUti.parseDouble(tblDat.getValueAt(i, intColTot[j]));}
            }
            for(i=5; i<intNumFil; i=i+6){
                for (j=0; j<intNumCol; j++){  
                    dblTot5[j]+=objUti.parseDouble(tblDat.getValueAt(i, intColTot[j]));
                    if(dblTot5[j]<0) {
                        temp2=j-1;
                        dev=dblTot3[j];
                    }
                }
            }
            fact=dblTot3[temp1];
            dev=dblTot5[temp2];
            totFact=(fact/coti)*100;
            totDev=(dev/coti)*100;
            for(j=0; j<intNumCol; j++){ 
                tblTot.setValueAt("" + objUti.redondear(dblTot[j], intNumDec), 0, intColTot[j]);
                tblTot.setValueAt("" + objUti.redondear(dblTot1[j], intNumDec), 1, intColTot[j]);
                tblTot.setValueAt("" + objUti.redondear(dblTot2[j], intNumDec), 2, intColTot[j]);
                tblTot.setValueAt("" + objUti.redondear(dblTot3[j], intNumDec), 3, intColTot[j]); 
                tblTot.setValueAt("" + objUti.redondear(dblTot4[j], intNumDec), 4, intColTot[j]); 
                tblTot.setValueAt("" + objUti.redondear(dblTot5[j], intNumDec), 5, intColTot[j]); 
            }
                tblTot.setValueAt("" + objUti.redondear(totFact, intNumDec), 3, temp1+5); 
                tblTot.setValueAt("" + objUti.redondear(totDev, intNumDec), 5, temp2+5);
                //Cabeceras de Columnas
                tblTot.setValueAt("COTIZACION (#)          ",0,1);
                tblTot.setValueAt("COTIZACION ($)            ",1,1);
                tblTot.setValueAt("FACTURA (#)              ",2,1);
                tblTot.setValueAt("FACTURA ($)              ",3,1);
                tblTot.setValueAt("DEVOLUCIONES (#)              ",4,1);
                tblTot.setValueAt("DEVOLUCIONES ($)              ",5,1);
                tblTot.setValueAt(" ",0,0);
                tblTot.setValueAt(" ",1,0);
                tblTot.setValueAt(" ",2,0);
                tblTot.setValueAt(" ",3,0);
                tblTot.setValueAt(" ",4,0);
                tblTot.setValueAt(" ",5,0);
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    
}


