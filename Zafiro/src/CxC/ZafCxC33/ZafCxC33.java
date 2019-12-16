/*
 * ZafCxC33.java
 *
 * Created on 21 de Noviembre de 2007, 14:26 PM 
 * SOLICITUDES DE CREDITO POR ANALIZAR
 * DESARROLLADO POR DARIO CARDENAS EL 20/FEB/2008
 */
package CxC.ZafCxC33;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
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
import Librerias.ZafDate.ZafDatePicker;//esto es para calcular el numero de dias transcurridos
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;//para visualizar una ventana de programa desde una columna de botones
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;

/**
 *
 * @author  DARIO CARDENAS
 */
public class ZafCxC33 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    final int INT_TBL_DAT_LIN=0;                        //Lï¿½nea.
    final int INT_TBL_DAT_COD_EMP=1;                    //Cï¿½digo de la empresa.
    final int INT_TBL_DAT_COD_SOL=2;                    //Codigo de Solicitud.
    final int INT_TBL_DAT_FEC_SOL=3;                    //Fecha de Solicitud.
    final int INT_TBL_DAT_COD_CLI=4;                    //Codigo del cliente.
    final int INT_TBL_DAT_NOM_CLI=5;                    //Nombre del cliente.
    final int INT_TBL_DAT_EST_ANA=6;                    //Estado de Analisis de Solicitud de Credito.
    final int INT_TBL_DAT_BOT_PAN=7;                    //Boton de Panel de Control.
    
    //Variables
    private ZafDatePicker dtpDat;                      //esto es para calcular el numero de dias transcurridos
    private ZafUtil objAux;
   // private java.util.Date datFecAux;
    javax.swing.JInternalFrame jfrThis; //Hace referencia a this
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModDab;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenï¿½ en JTable.
    private ZafTblBus objTblBus;                        //Editor de bï¿½squeda.
    private ZafTblTot objTblTot;                        //JTable de totales.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                             //true: Continua la ejecuciï¿½n del hilo.
    private String strCodCli, strDesLarCli,strCodEmp, strNomEmp,strCodTipDoc, strDesLarTipDoc,strCodEmpTipDoc, strDesLarEmpTipDoc;             //Contenido del campo al obtener el foco.
    private int intCodLocAux, k=0, p=0, CODEMP=0;
    private ZafVenCon vcoCli, vcoEmp;                   //Ventana de consulta.
    private Vector vecAux, vecAuxDat;
    private ZafTblCelRenChk objTblCelRenChkMain;
    private ZafTblCelEdiChk objTblCelEdiChkMain;
    private ZafTblCelEdiButGen objTblCelEdiButGen;      //Editor: JButton en celda.
    private ZafTblCelRenBut objTblCelRenBut;            //Render: Presentar JButton en JTable.
    private ZafTblEdi objTblEdi;
    
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCxC33(ZafParSis obj) 
    {
//        initComponents();
//        //Inicializar objetos.
//        objParSis=obj;
//        //butTipDoc.setVisible(false);
//        
//        if (!configurarFrm())
//            exitForm();
        
        try
        {  
            initComponents();
            //Inicializar objetos.
            this.objParSis=obj;
            jfrThis = this;
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            
            lblTit.setText("Solicitudes de Credito por Analizar...");

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
    private void initComponents() {//GEN-BEGIN:initComponents
        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lblEmp = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
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
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("T\u00edtulo de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

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

        jPanel1.setLayout(null);

        jPanel1.setPreferredSize(new java.awt.Dimension(10, 30));
        lblEmp.setText("Empresa:");
        jPanel1.add(lblEmp);
        lblEmp.setBounds(10, 10, 70, 15);

        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });

        jPanel1.add(txtCodEmp);
        txtCodEmp.setBounds(70, 8, 30, 20);

        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });

        jPanel1.add(txtNomEmp);
        txtNomEmp.setBounds(100, 8, 200, 20);

        butEmp.setText("...");
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });

        jPanel1.add(butEmp);
        butEmp.setBounds(300, 8, 20, 20);

        panRpt.add(jPanel1, java.awt.BorderLayout.NORTH);

        tabFrm.addTab("General", panRpt);

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

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        // TODO add your handling code here:
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        // TODO add your handling code here:
        if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp))
        {
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
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        // TODO add your handling code here:
        strNomEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
    }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        // TODO add your handling code here:
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        // TODO add your handling code here:
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp))
        {
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
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        // TODO add your handling code here:
        strCodEmp=txtCodEmp.getText();
        txtCodEmp.selectAll();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        // TODO add your handling code here:
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acciï¿½n de acuerdo a la etiqueta del botï¿½n ("Consultar" o "Detener").
        k=0;
        p=0;
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

    /** Cerrar la aplicaciï¿½n. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="ï¿½Estï¿½ seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    /** Cerrar la aplicaciï¿½n. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEmp;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtNomEmp;
    // End of variables declaration//GEN-END:variables
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        int intCodEmp=0, intCodEmpGrp=0;
        try
        {
            //titulo de la ventana
            this.setTitle(objParSis.getNombreMenu()+ " V 0.4");
            ///lblTit.setText(""+objParSis.getNombreMenu());
            
            dtpDat=new ZafDatePicker(((javax.swing.JFrame)this.getParent()), "d/m/y");//inicializa el objeto DatePicker
            
            intCodEmp = objParSis.getCodigoEmpresa();
            intCodEmpGrp = objParSis.getCodigoEmpresaGrupo();
            CODEMP = intCodEmpGrp;
            System.out.println("---intCodEmp--- " + intCodEmp);
            System.out.println("---CODEMPGRP--- " + CODEMP);
            
            lblEmp.setVisible(false);
            txtCodEmp.setVisible(false);
            txtNomEmp.setVisible(false);
            butEmp.setVisible(false);
            
            
            if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
            {
                lblEmp.setVisible(true);
                txtCodEmp.setVisible(true);
                txtNomEmp.setVisible(true);
                butEmp.setVisible(true);
            }
            
            //Configurar Ventana de Consulta de Empresa//
            configurarVenConEmp();
            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(15);  //Almacena las cabeceras
            vecCab.clear();
    
            vecCab.add(INT_TBL_DAT_LIN,"");///0
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cï¿½d.Emp.");///1
            vecCab.add(INT_TBL_DAT_COD_SOL,"Cod.Sol.");///2
            vecCab.add(INT_TBL_DAT_FEC_SOL,"Fecha");///3
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cï¿½d.Cli.");///4
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente");///5
            vecCab.add(INT_TBL_DAT_EST_ANA,"Est.Ana.Sol.");///6
            vecCab.add(INT_TBL_DAT_BOT_PAN,"...");///7
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            objTblEdi=new ZafTblEdi(tblDat);
            setEditable(true);
                        
            //Configurar JTable: Establecer tipo de selecciï¿½n.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            
            //Configurar JTable: Establecer el menï¿½ de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);///0
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(50);///1
            tcmAux.getColumn(INT_TBL_DAT_COD_SOL).setPreferredWidth(50);///2
            tcmAux.getColumn(INT_TBL_DAT_FEC_SOL).setPreferredWidth(95);///3
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(60);///4
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(250);///5
            tcmAux.getColumn(INT_TBL_DAT_EST_ANA).setPreferredWidth(30);///6
            tcmAux.getColumn(INT_TBL_DAT_BOT_PAN).setPreferredWidth(35);///7

            //Configurar JTable: Ocultar columnas del sistema.
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_EST_ANA).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_EST_ANA).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_EST_ANA).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_EST_ANA).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_EST_ANA).setResizable(false);
            
            //para hacer editable las celdas
            vecAux=new Vector();
            vecAuxDat=new Vector();
            vecAux.add("" + INT_TBL_DAT_BOT_PAN);///7
            objTblMod.setColumnasEditables(vecAux);
            vecAuxDat = vecAux;
            vecAux=null;
            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Editor de bï¿½squeda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_SOL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_SOL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Renderizo para boton
            objTblCelRenBut=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BOT_PAN).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BOT_PAN).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    abrirFrm();
                }
            });
            
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
    
    public void setEditable(boolean editable)
    {
        if (editable==true)
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }
        else
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }
    
    /**
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar los "Empresa".
     */
    private boolean configurarVenConEmp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_ruc");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cï¿½digo");
            arlAli.add("Identificaciï¿½n");
            arlAli.add("Nombre");
            arlAli.add("Direcciï¿½n");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_emp, a1.tx_ruc, a1.tx_nom, a1.tx_dir";
            strSQL+=" FROM tbm_emp AS a1";
            ///strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" ORDER BY a1.co_emp";
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoEmp.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    /**
     * Esta funciï¿½n permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intCodEmp, intCodLoc, intNumTotReg, i;
        
        int intNumDia; 
        String strFecSis, strFecIni;
        int intFecIni[];
        int intFecFin[];//para calcular los dias entre fechas
        
        double dblSub, dblIva;
        java.util.Date datFecSer, datFecVen, datFecAux;
        
        String strEstAnaSolCre="";
        
        boolean blnRes=true;
        
        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                datFecAux=datFecSer;
                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");

                //Obtener la condiciï¿½n.
                strAux="";
                
                //Condicion para filtro por cliente//
                if(intCodEmp==0)
                {
                    if (txtCodEmp.getText().length()>0)
                        strAux+=" AND a1.co_emp = " + txtCodEmp.getText();
                }
                else
                {
                    strAux+=" AND a1.co_emp = " + intCodEmp;
                }

                //Obtener el nï¿½mero total de registros.
                strSQL="";
                strSQL+="SELECT COUNT(*)";
                strSQL+=" FROM (";
                strSQL+=" SELECT a1.co_emp, a1.co_sol, a1.fe_sol, a1.co_cli, a2.tx_nom, a1.st_anasol ";
                strSQL+=" FROM tbm_solcre AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                    strSQL+=strAux;
                    strSQL+=" AND a1.st_anasol='P' AND a1.st_reg='A' AND a2.st_reg='A' ";
                    strSQL+=" ORDER BY a2.tx_nom, a1.co_sol ";
                }
                else
                {
                    strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli)";
                    strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                    strSQL+=" AND a3.co_loc = " + intCodLoc + "";
                    strSQL+=strAux;
                    strSQL+=" AND a1.st_anasol='P'  AND a1.st_reg='A' AND a2.st_reg='A' ";
                    strSQL+=" ORDER BY a2.tx_nom, a1.co_sol ";
                }
                
//                ///strSQL+=" WHERE a1.co_emp = " + intCodEmp;
//                strSQL+=" WHERE a1.st_reg IN ('A')";
//                strSQL+=" AND a1.st_anasol IN ('P')";
//                strSQL+=strAux;
//                strSQL+=" ORDER BY a1.co_emp, a1.co_sol ";
                
                strSQL+=" ) AS b1";
                
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;

                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_sol, a1.fe_sol, a1.co_cli, a2.tx_nom, a1.st_anasol ";
                strSQL+=" FROM tbm_solcre AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                    strSQL+=strAux;
                    strSQL+=" AND a1.st_anasol='P' AND a1.st_reg='A' AND a2.st_reg='A' ";
                    strSQL+=" ORDER BY a2.tx_nom, a1.co_sol ";
                }
                else
                {
                    strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli)";
                    strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                    strSQL+=" AND a3.co_loc = " + intCodLoc + "";
                    strSQL+=strAux;
                    strSQL+=" AND a1.st_anasol='P'  AND a1.st_reg='A' AND a2.st_reg='A' ";
                    strSQL+=" ORDER BY a2.tx_nom, a1.co_sol ";
                }
                
//                ///strSQL+=" WHERE a1.co_emp = " + intCodEmp;
//                strSQL+=" WHERE a1.st_reg IN ('A')";
//                strSQL+=" AND a1.st_anasol IN ('P')";
//                strSQL+=strAux;
//                strSQL+=" ORDER BY a1.co_emp, a1.co_sol ";
                
                System.out.println("---Query de cargarDetReg(): "+ strSQL);
                
                rst=stm.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");///0
                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));///1
                        vecReg.add(INT_TBL_DAT_COD_SOL,rst.getString("co_sol"));///2
                        vecReg.add(INT_TBL_DAT_FEC_SOL,rst.getString("fe_sol"));///3
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli"));///4
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nom"));///5
                        vecReg.add(INT_TBL_DAT_EST_ANA,rst.getString("st_anasol"));///6
                        vecReg.add(INT_TBL_DAT_BOT_PAN,"");///7
                        vecDat.add(vecReg);
                        i++;
                        pgrSis.setValue(i);
                    }
                    else
                    {
                        break;
                    }
                    
                }///fin del while///
                
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
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sï¿½lo se procesaron " + tblDat.getRowCount() + ".");
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
    
    private boolean abrirFrm()
    {
        boolean blnRes=true;
        try
        {
            if(tabFrm.getSelectedIndex()==0)
            {
                ///System.out.println("ABRIR_FRM Tab Propietarios - Accionistas...");
                //////////////PARA LLAMAR A LA VENTANA DE CONTACTOS ////////
                if(!(tblDat.getSelectedColumn()==-1))
                {
                    if(!(tblDat.getSelectedRow()==-1))
                    {
                        strAux="CxC.ZafCxC32.ZafCxC32";
                        if (!strAux.equals(""))
                            invocarClase(strAux);
                    }
                }
            }
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
//    private boolean invocarClase(String clase)
//    {
//        boolean blnRes=true;
//        int intcodemp=0;
//        int q=0;
//        try
//        {
//            System.out.println("---FUNCION INVOCAR CLASE DESDE CXC33...");
//            ///System.out.println("El estado MODIFICAR es... " + m);
//            intcodemp = objParSis.getCodigoEmpresa();
//            q++;
//            ////TAB DE REPORTES////
//            if(tabFrm.getSelectedIndex()==0)
//            {
//                ///System.out.println("--1.-INVOCAR_CLASE Tab Propietarios - Accionistas...");
//                //Obtener el constructor de la clase que se va a invocar.
//                Class objVen=Class.forName(clase);
//                Class objCla[]=new Class[6];
//                objCla[0]=objParSis.getClass();         ///objParZis///
//                objCla[1]=new Integer(0).getClass();    ///Int CodCliente///
//                objCla[2]=new String("").getClass();    ///Str NomCliente///
//                objCla[3]=new Integer(0).getClass();    ///Int CodCliente///
//                objCla[4]=new Integer(0).getClass();    ///Int CodSolCre///
//                objCla[5]=new Integer(0).getClass();    ///Int Bandera///
//                
//                java.lang.reflect.Constructor objCon=objVen.getConstructor(objCla);
//                
//                //Inicializar el constructor que se obtuvo.
//                Object objObj[]=new Object[6];
//                
//                ///Para verificar si ultima linea del Jtable esta vacia o en modo de Insercion///
//                if(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_SOL)==null )
//                {
//                    System.out.println("---ENTRO POR LA COLUMNA VACIA DESDE CXC33...");
//                    objObj[0]=objParSis;
//                    objObj[1]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_CLI).toString());
//                    objObj[2]=new String(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_NOM_CLI).toString());
//                    objObj[3]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_EMP).toString());
//                    objObj[4]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_SOL).toString());
//                    objObj[5]=new Integer(q);
//                }
//                else
//                {
//                    System.out.println("---ENTRO POR LA COLUMNA CON DATOS DESDE CXC33...");
//                    objObj[0]=objParSis;
//                    objObj[1]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_CLI).toString());
//                    objObj[2]=new String(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_NOM_CLI).toString());
//                    objObj[3]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_EMP).toString());
//                    objObj[4]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_SOL).toString());
//                    objObj[5]=new Integer(q);
//                }
//                
//                javax.swing.JInternalFrame ifrVen;
//                ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
//                this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
//                ifrVen.show();
//            }
//        }
//        catch (ClassNotFoundException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (NoSuchMethodException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (SecurityException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (InstantiationException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (IllegalAccessException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (IllegalArgumentException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (java.lang.reflect.InvocationTargetException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
    
    
    private boolean invocarClase(String clase)
    {
        boolean blnRes=true;
        int intcodemp=0;
        int q=0;
        try
        {
            System.out.println("---FUNCION INVOCAR CLASE DESDE CXC33...");
            ///System.out.println("El estado MODIFICAR es... " + m);
            intcodemp = objParSis.getCodigoEmpresa();
            q++;
            ////TAB DE REPORTES////
            if(tabFrm.getSelectedIndex()==0)
            {
                ///System.out.println("--1.-INVOCAR_CLASE Tab Propietarios - Accionistas...");
                //Obtener el constructor de la clase que se va a invocar.
                Class objVen=Class.forName(clase);
                Class objCla[]=new Class[5];
                objCla[0]=objParSis.getClass();         ///objParZis///
                objCla[1]=new Integer(0).getClass();    ///Int CodEmpresa///
                objCla[2]=new Integer(0).getClass();    ///Int CodCliente///
                objCla[3]=new Integer(0).getClass();    ///Int CodSolCre///
                objCla[4]=new Integer(0).getClass();    ///Int Bandera///
                
                java.lang.reflect.Constructor objCon=objVen.getConstructor(objCla);
                
                //Inicializar el constructor que se obtuvo.
                Object objObj[]=new Object[5];
                
                ///Para verificar si ultima linea del Jtable esta vacia o en modo de Insercion///
                if(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_SOL)==null )
                {
                    System.out.println("---ENTRO POR LA COLUMNA VACIA DESDE CXC33...");
                    objObj[0]=objParSis;
                    objObj[1]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_EMP).toString());
                    objObj[2]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_CLI).toString());                    
                    objObj[3]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_SOL).toString());
                    objObj[4]=new Integer(q);
                }
                else
                {
                    System.out.println("---ENTRO POR LA COLUMNA CON DATOS DESDE CXC33...");
                    objObj[0]=objParSis;
                    objObj[1]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_EMP).toString());
                    objObj[2]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_CLI).toString());                    
                    objObj[3]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_SOL).toString());
                    objObj[4]=new Integer(q);
                }
                
                javax.swing.JInternalFrame ifrVen;
                ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
                this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
                ifrVen.show();
            }
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
     * Esta funciï¿½n muestra un mensaje informativo al usuario. Se podrï¿½a utilizar
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
     * Esta funciï¿½n muestra un mensaje "showConfirmDialog". Presenta las opciones
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
     * Esta funciï¿½n muestra un mensaje de error al usuario. Se podrï¿½a utilizar
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
     * Esta funciï¿½n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de bï¿½squeda determina si se debe hacer
     * una bï¿½squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se estï¿½ buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opciï¿½n que desea utilizar.
     * @param intTipBus El tipo de bï¿½squeda a realizar.
     * @return true: Si no se presentï¿½ ningï¿½n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConEmp(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(3));
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Cï¿½digo".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(3));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(3));
                        }
                        else
                        {
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(3));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(2);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(3));
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

   
    /**
     * Esta clase crea un hilo que permite manipular la interface grï¿½fica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que estï¿½ ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podrï¿½a presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estarï¿½a informado en todo
     * momento de lo que ocurre. Si se desea hacer ï¿½sto es necesario utilizar ï¿½sta clase
     * ya que si no sï¿½lo se apreciarï¿½a los cambios cuando ha terminado todo el proceso.
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
            //Establecer el foco en el JTable sï¿½lo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(0);
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
     * resulta muy corto para mostrar leyendas que requieren mï¿½s espacio.
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
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Cï¿½digo de la empresa";
                    break;
                case INT_TBL_DAT_COD_SOL:
                    strMsg="Cï¿½digo de la Solicitud de Credito";
                    break;
                case INT_TBL_DAT_FEC_SOL:
                    strMsg="Fecha de la Solicitud de Credito";
                    break;                    
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Cï¿½digo del cliente";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_DAT_BOT_PAN:
                    strMsg="Boton para Visualizar el Panel de Control";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}