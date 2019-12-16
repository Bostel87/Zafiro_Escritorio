/*
 * ZafCxC49.java
 *
 * Created on 05 de Noviembre de 2008, 14:26 PM
 * ESTABLECER LAS SOLICITUDES DE CREDITO QUE YA ESTAN ACTUALIZADAS
 * DESARROLLADO POR DARIO CARDENAS EL 09/NOV/2008
 * MODIFICADO EL DIA 10/NOV/2008
 */
package CxC.ZafCxC49;
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
import Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut;

/**
 *
 * @author  DARIO CARDENAS
 */
public class ZafCxC49 extends javax.swing.JInternalFrame 
{
//    //Constantes: Columnas del JTable:
//    final int INT_TBL_DAT_LIN=0;                        //Lï¿½nea.
//    final int INT_TBL_DAT_SEL=1;                        //Seleccion.
//    final int INT_TBL_DAT_COD_EMP=2;                    //Cï¿½digo de la empresa.
//    final int INT_TBL_DAT_COD_SOL=3;                    //Codigo de Solicitud.
//    final int INT_TBL_DAT_FEC_SOL=4;                    //Fecha de Solicitud.
//    final int INT_TBL_DAT_COD_CLI=5;                    //Codigo del cliente.
//    final int INT_TBL_DAT_NOM_CLI=6;                    //Nombre del cliente.
//    final int INT_TBL_DAT_EST_ANA=7;                    //Estado de Analisis de Solicitud de Credito.
//    final int INT_TBL_DAT_BOT_PAN=8;                    //Boton de Panel de Control.
    
    /* Declaracion de CONSTANTES*/ 
    final int INT_TBL_LINEA          =0;            //LINEA DE NUMEROS DE REGISTROS EN LA TABLA///
    final int INT_TBL_SEL            =1;            //CASILLA DE SELECCION
    final int INT_TBL_COD_EMP        =2;            //CODIGO DE LA EMPRESA
    final int INT_TBL_COD_SOL        =3;            //CODIGO DE SOLICITUD
    final int INT_TBL_FEC_SOL        =4;            //FECHA DE SOLICITUD
    final int INT_TBL_COD_CLI        =5;            //CODIGO DEL CLIENTE
    final int INT_TBL_NOM_CLI        =6;            //NOMBRE DEL CLIENTE
    final int INT_TBL_FEC_ULT_ACT_DAT=7;            //FECHA DE ULTIMA ACTUALIZACION DE DATOS
    final int INT_TBL_MES_PRO_ACT_DAT=8;            //NUMERO DE MES PROXIMA ACTUALIZACION DE DATOS
    final int INT_TBL_FEC_PRO_ACT_DAT=9;            //FECHA DE PROXIMA ACTUALIZACION DE DATOS
    final int INT_TBL_BOT_PAN        =10;            //BOTON PARA ACCEDER AL PANEL DE CONTROL
    
    //Variables
    private ZafDatePicker dtpDat;                      //esto es para calcular el numero de dias transcurridos
    private Librerias.ZafDate.ZafDatePicker dtpFecDoc;
    private ZafUtil objAux;
   // private java.util.Date datFecAux;
    private java.util.Date datFecSer, datFecVen, datFecAux;
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
    private String strCodCli, strDesLarCli,strCodEmp, strNomEmp,strCodTipDoc, strDesCorTipDoc, strDesLarTipDoc, strCodEmpTipDoc, strDesLarEmpTipDoc;             //Contenido del campo al obtener el foco.
    private int intCodLocAux, k=0, p=0, CODEMP=0;
    private ZafVenCon vcoCli, vcoEmp, vcoTipDoc;                   //Ventana de consulta.
    private Vector vecAux, vecAuxDat;
    private ZafTblCelRenChk objTblCelRenChkMain;
    private ZafTblCelEdiChk objTblCelEdiChkMain;
    private ZafTblCelEdiButGen objTblCelEdiButGen;      //Editor: JButton en celda.
    private ZafTblCelRenBut objTblCelRenBut;            //Render: Presentar JButton en JTable.
    private ZafTblEdi objTblEdi;
    private ZafTblCelEdiBut objTblCelEdiButCli;
    private ZafTblCelRenBut objTblCelRenButCli;
    
    java.util.Calendar objFec = java.util.Calendar.getInstance();
    Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCxC49(ZafParSis obj) 
    {
//        initComponents();
//        //Inicializar objetos.
//        objParSis=obj;
//        //butTipDoc.setVisible(false);
        try
        {  
            initComponents();
            //Inicializar objetos.
            this.objParSis=obj;
            jfrThis = this;
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();

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
        lblEmp = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        panNomCli = new javax.swing.JPanel();
        lblNomCliDes = new javax.swing.JLabel();
        txtNomCliDes = new javax.swing.JTextField();
        lblNomCliHas = new javax.swing.JLabel();
        txtNomCliHas = new javax.swing.JTextField();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGuardar = new javax.swing.JButton();
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
        optTod.setText("Todos las Solicitudes de Credito");
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
        panFil.add(optTod);
        optTod.setBounds(4, 34, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo las Solicitudes que cumplan el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(4, 55, 400, 20);

        lblEmp.setText("Empresa:");
        panFil.add(lblEmp);
        lblEmp.setBounds(10, 10, 70, 14);

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
        panFil.add(txtCodEmp);
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
        panFil.add(txtNomEmp);
        txtNomEmp.setBounds(100, 8, 200, 20);

        butEmp.setText("...");
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(300, 8, 20, 20);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCli.setText("Cliente:");
        lblCli.setPreferredSize(new java.awt.Dimension(110, 15));
        panFil.add(lblCli);
        lblCli.setBounds(24, 80, 70, 15);

        txtCodCli.setMaximumSize(null);
        txtCodCli.setPreferredSize(new java.awt.Dimension(70, 20));
        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        panFil.add(txtCodCli);
        txtCodCli.setBounds(80, 80, 66, 20);

        txtNomCli.setMaximumSize(null);
        txtNomCli.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });
        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        panFil.add(txtNomCli);
        txtNomCli.setBounds(148, 80, 460, 20);

        butCli.setFont(new java.awt.Font("SansSerif", 1, 12));
        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(610, 80, 24, 20);

        panNomCli.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre de cliente"));
        panNomCli.setLayout(null);

        lblNomCliDes.setText("Desde:"); // NOI18N
        panNomCli.add(lblNomCliDes);
        lblNomCliDes.setBounds(12, 20, 44, 20);

        txtNomCliDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliDesFocusLost(evt);
            }
        });
        panNomCli.add(txtNomCliDes);
        txtNomCliDes.setBounds(56, 20, 268, 20);

        lblNomCliHas.setText("Hasta:"); // NOI18N
        panNomCli.add(lblNomCliHas);
        lblNomCliHas.setBounds(336, 20, 44, 20);

        txtNomCliHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusLost(evt);
            }
        });
        panNomCli.add(txtNomCliHas);
        txtNomCliHas.setBounds(380, 20, 268, 20);

        panFil.add(panNomCli);
        panNomCli.setBounds(16, 105, 660, 52);

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

        butGuardar.setText("Guardar");
        butGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuardarActionPerformed(evt);
            }
        });
        panBot.add(butGuardar);

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

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        // TODO add your handling code here:
        txtCodCli.requestFocus();
    }//GEN-LAST:event_optFilActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        if (optFil.isSelected())
        {
//            txtCodEmp.setText("");
//            txtDesLarEmp.setText("");
//            txtCodTipDoc.setText("");
//            txtDesLarTipDoc.setText("");
//            txtCodEmpTipDoc.setText("");
//            txtDesLarEmpTipDoc.setText("");
        }

    }//GEN-LAST:event_optFilItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_optTodActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodCli.setText("");
            txtNomCli.setText("");
            txtNomCliDes.setText("");
            txtNomCliHas.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged

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

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        // TODO add your handling code here:
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        // TODO add your handling code here:
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCodCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }
            else
            {
                mostrarVenConCli(1);
            }
        }
        else
            txtCodCli.setText(strCodCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
        
//        //Validar el contenido de la celda sï¿½lo si ha cambiado.
//        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)) {
//            if (txtCodCli.getText().equals("")) {
//                txtCodCli.setText("");
//                txtNomCli.setText("");
//                objTblModAcc.removeAllRows();
//                txtCodCli.requestFocus();
//            } else {
//                mostrarVenConCli(1);
//                if(objTooBar.getEstado()=='n') {
//                    if(!txtCodCli.getText().equals("")) {
//                        mostrarDatCli();
//                        isDesCam();
//                    }
//                }
//                if (txtCodCli.getText().equals("")) {
//                    txtCodCli.requestFocus();
//                }
//            }
//        } else
//            txtCodCli.setText(strCodCli);
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        // TODO add your handling code here:
        txtNomCli.transferFocus();
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        // TODO add your handling code here:
        strDesLarCli=txtNomCli.getText();
        txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        // TODO add your handling code here:
        if (!txtNomCli.getText().equalsIgnoreCase(strDesLarCli)) {
            if (txtNomCli.getText().equals("")) {
                txtCodCli.setText("");
                txtNomCli.setText("");
//                objTblModAcc.removeAllRows();
            } else {
                mostrarVenConCli(2);
//                if (txtCodCli.getText().equals("")) {
//                    objTblModAcc.removeAllRows();
//                }
//                mostrarDatCli();
            }
        } else
            txtNomCli.setText(strDesLarCli);
    }//GEN-LAST:event_txtNomCliFocusLost

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        // TODO add your handling code here:
        
        mostrarVenConCli(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
        
//        mostrarVenConCli(0);
//        if (txtCodCli.getText().equals(""))
//            txtCodCli.requestFocus();
//        else
//            txtDir.requestFocus();
//        mostrarDatCli();
}//GEN-LAST:event_butCliActionPerformed

    private void txtNomCliDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusGained
        txtNomCliDes.selectAll();
    }//GEN-LAST:event_txtNomCliDesFocusGained

    private void txtNomCliDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusLost
        if (txtNomCliDes.getText().length()>0) 
        {
            optFil.setSelected(true);
            if (txtNomCliHas.getText().length()==0)
                txtNomCliHas.setText(txtNomCliDes.getText());
            
            txtCodCli.setText("");
            txtNomCli.setText("");
        }
    }//GEN-LAST:event_txtNomCliDesFocusLost

    private void txtNomCliHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusGained
        txtNomCliHas.selectAll();
    }//GEN-LAST:event_txtNomCliHasFocusGained

    private void txtNomCliHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusLost
        if (txtNomCliHas.getText().length()>0)
        {
            optFil.setSelected(true);
            
            if (txtNomCliDes.getText().length()==0)
                txtNomCliDes.setText(txtNomCliHas.getText());
            
            txtCodCli.setText("");
            txtNomCli.setText("");
        }
        
    }//GEN-LAST:event_txtNomCliHasFocusLost

    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed
        // TODO add your handling code here:
        
//        if (objTblMod.isDataModelChanged()) {
//            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0) {
////                if (actualizarDet())
////                    mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
////                else
////                    mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
////                
////                cargarDetReg();
//            }
//        } else
//            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
        
        
        
        System.out.println("---k: " + k);
        
        if( k!=0)
        {
            if(actualizarTbmCli())
            {
                mostrarMsgInf("La operacion GUARDAR se realizo con Exito.");
            }
            else
                mostrarMsgErr("Ocurrio un error al realizar la operacion GUARDAR.\nIntente realizar la operacion nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
        }
        else
        {
            mostrarMsgInf("No existen Registros Seleccionados \n Favor Seleccione un Registro...");
        }
        
        System.out.println("---TERMINO DE GUARDAR, luego se consultara los registros--- ");
        cargarDatReg();
    }//GEN-LAST:event_butGuardarActionPerformed

    /** Cerrar la aplicaciï¿½n. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butGuardar;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNomCliDes;
    private javax.swing.JLabel lblNomCliHas;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomCliDes;
    private javax.swing.JTextField txtNomCliHas;
    private javax.swing.JTextField txtNomEmp;
    // End of variables declaration//GEN-END:variables
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        int intCodEmp=0, intCodEmpGrp=0;
        try
        {
            //Inicializar objetos.
//            objUti=new ZafUtil();
//            strAux=objParSis.getNombreMenu();
//            this.setTitle(strAux + " V 0.3 ");
//            lblTit.setText(strAux);
            
            //titulo de la ventana
            this.setTitle(objParSis.getNombreMenu()+ " V 0.2");
            lblTit.setText(""+objParSis.getNombreMenu());
            
            
            dtpDat=new ZafDatePicker(((javax.swing.JFrame)this.getParent()), "d/m/y");//inicializa el objeto DatePicker
            
//            //Obtener la fecha y hora del servidor.
//            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//            if (datFecAux==null)
//                return false;
//            String strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
//            System.out.println("---FEC_DIA_HOY ES strFecHorSer: " + strFecHorSer);
//            ///datFecAux=null;
            
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
            
            //Configurar Ventana de Consulta de Cliente//
            configurarVenConCli();
            
            //Configurar Ventana de Consulta de Empresa//
            configurarVenConEmp();
            
                        
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(15);  //Almacena las cabeceras
            vecCab.clear();

            
            /*Configurar Vector de Cabecera para los datos de las Columnas*/
            vecCab.add(INT_TBL_LINEA,"");//0
            vecCab.add(INT_TBL_SEL,"");//1
            vecCab.add(INT_TBL_COD_EMP,"Cod.Emp.");//2
            vecCab.add(INT_TBL_COD_SOL,"Cod.Sol.");//3
            vecCab.add(INT_TBL_FEC_SOL,"Fec.Sol.");//4
            vecCab.add(INT_TBL_COD_CLI,"Cod.Cli.");//5
            vecCab.add(INT_TBL_NOM_CLI,"Nom.Cli.");//6
            vecCab.add(INT_TBL_FEC_ULT_ACT_DAT,"Fec.Ult.Act.Dat.");///7
            vecCab.add(INT_TBL_MES_PRO_ACT_DAT,"Mes.Pro.Act.Dat.");///8
            vecCab.add(INT_TBL_FEC_PRO_ACT_DAT,"Fec.Pro.Act.Dat.");///9
            vecCab.add(INT_TBL_BOT_PAN,"...");//10
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            objTblEdi=new ZafTblEdi(tblDat);
            setEditable(true);
                        
            //Configurar JTable: Establecer tipo de selecciï¿½n.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_LINEA);
            
            //Configurar JTable: Establecer el menï¿½ de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tblDat.getColumnModel().getColumn(INT_TBL_LINEA).setPreferredWidth(40);//0
            tblDat.getColumnModel().getColumn(INT_TBL_SEL).setPreferredWidth(25);//1
            tblDat.getColumnModel().getColumn(INT_TBL_COD_EMP).setPreferredWidth(55);//2
            tblDat.getColumnModel().getColumn(INT_TBL_COD_SOL).setPreferredWidth(55);//3
            tblDat.getColumnModel().getColumn(INT_TBL_FEC_SOL).setPreferredWidth(100);//4
            tblDat.getColumnModel().getColumn(INT_TBL_COD_CLI).setPreferredWidth(55);//5
            tblDat.getColumnModel().getColumn(INT_TBL_NOM_CLI).setPreferredWidth(300);//6
            tblDat.getColumnModel().getColumn(INT_TBL_FEC_ULT_ACT_DAT).setPreferredWidth(100);//7
            tblDat.getColumnModel().getColumn(INT_TBL_MES_PRO_ACT_DAT).setPreferredWidth(40);//8
            tblDat.getColumnModel().getColumn(INT_TBL_FEC_PRO_ACT_DAT).setPreferredWidth(100);//9
            tblDat.getColumnModel().getColumn(INT_TBL_BOT_PAN).setPreferredWidth(25);//10

            //Configurar JTable: Establecer Ocultas las columnas.
            ocultaCol(INT_TBL_FEC_ULT_ACT_DAT);//7
            ocultaCol(INT_TBL_MES_PRO_ACT_DAT);//8
            ocultaCol(INT_TBL_FEC_PRO_ACT_DAT);//9
            
            //para hacer editable las celdas
            vecAux=new Vector();
            vecAuxDat=new Vector();
            vecAux.add("" + INT_TBL_SEL);///1
            vecAux.add("" + INT_TBL_BOT_PAN);///8
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
            
            //Metodo Renderizador para click en el boton del cliente
            objTblCelRenButCli=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_BOT_PAN).setCellRenderer(objTblCelRenButCli);
            objTblCelRenButCli=null;
            
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_BOT_PAN).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    abrirFrm();
                }
            });
            
            //metodo de renderizador de la columna de seleccion//
            objTblCelRenChkMain=new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_SEL).setCellRenderer(objTblCelRenChkMain);
            objTblCelRenChkMain=null;
            
            objTblCelEdiChkMain=new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_SEL).setCellEditor(objTblCelEdiChkMain);            
            objTblCelEdiChkMain.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    System.out.println("afterEdit - DESPUES -- SE SELECCIONO CELDA");
                    if(objTblCelEdiChkMain.isChecked())
                    {
                        k++;
                        System.out.println("---k reg.sel.--- " + k);
                        
                        String strvalProAct = objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_MES_PRO_ACT_DAT)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_MES_PRO_ACT_DAT).toString();
                        System.out.println("---strvalProAct: " + strvalProAct);
                        
                        String strfecUltAct = objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_FEC_ULT_ACT_DAT)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_FEC_ULT_ACT_DAT).toString();
                        System.out.println("---strfecUltAct: " + strfecUltAct);
                            
                    }
                    else
                    {
                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_SEL);
                        k--;
                        System.out.println("---se desactivado k registros--- " + k);
                    }
                }
            });
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tblDat.getColumnModel().getColumn(INT_TBL_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_COD_SOL).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_FEC_SOL).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_COD_CLI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tblDat.getColumnModel().getColumn(INT_TBL_NOM_CLI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
                        
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
     * mostrar los "Clientes/Proveedores".
     */
    private boolean configurarVenConCli()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Codigo");
            arlAli.add("Identificacion");
            arlAli.add("Nombre");
            arlAli.add("Direccion");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");

            /*Query mejorado para consultar Clientes filtrado por local y empresa*/            
            //Armar la sentencia SQL.            
            if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
            {
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_cli='S' AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_nom";
                System.out.println("---QUERY PARA CONCLI---: " + strSQL);
            }
            else
            {
                strSQL="";
                strSQL+="SELECT a1.co_cli, a2.tx_ide, a2.tx_nom, a2.tx_dir";
                strSQL+=" FROM  tbr_cliloc AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.st_cli='S' AND a2.st_reg='A'";
                strSQL+=" ORDER BY a2.tx_nom";
                System.out.println("---QUERY PARA CONCLI POR LOCAL---: " + strSQL);
            }
            
/*Ultimo query para consultar Clientes*/            
//            //Armar la sentencia SQL.
//            strSQL="";
//            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
//            strSQL+=" FROM tbm_cli AS a1";
//            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//            if (objParSis.getCodigoMenu()==319)
//                strSQL+=" AND a1.st_cli='S'";
//            else
//                strSQL+=" AND a1.st_prv='S'";
//            strSQL+=" ORDER BY a1.tx_nom";
            
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCli.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
            arlAli.add("Codigo");
            arlAli.add("Identificacion");
            arlAli.add("Nombre");
            arlAli.add("Direccion");
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
    
    private boolean abrirFrm()
    {
        boolean blnRes=true;
        try
        {
            if(tabFrm.getSelectedIndex()==1)
            {
                ///System.out.println("ABRIR_FRM PANEL DE CONTROL...");
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
    
    private boolean invocarClase(String clase)
    {
        boolean blnRes=true;
        int intcodemp=0;
        try
        {
            System.out.println("---FUNCION INVOCAR CLASE DESDE CXC49...");
            ///System.out.println("El estado CONSULTAR es... " + c);
            ///System.out.println("El estado MODIFICAR es... " + m);
            intcodemp = objParSis.getCodigoEmpresa();
            
            ////TAB DE REPORTES////
            if(tabFrm.getSelectedIndex()==1)
            {
                ///System.out.println("--1.-INVOCAR_CLASE Tab Propietarios - Accionistas...");
                //Obtener el constructor de la clase que se va a invocar.
                Class objVen=Class.forName(clase);
                Class objCla[]=new Class[4];
                objCla[0]=objParSis.getClass();         ///OBJ ZAFPARSIS///
                ///objCla[3]=new String("").getClass();
                objCla[1]=new String("").getClass();    ///INT CODEMP///
                objCla[2]=new String("").getClass();    ///INT CODCLI///                
                objCla[3]=new String("").getClass();    ///INT CODSOLCRE///
                
                java.lang.reflect.Constructor objCon=objVen.getConstructor(objCla);
                
                //Inicializar el constructor que se obtuvo.
                Object objObj[]=new Object[4];
                
                ///Para verificar si ultima linea del Jtable esta vacia o en modo de Insercion///
                if(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_COD_SOL)==null )
                {
                    System.out.println("---ENTRO POR LA FILA VACIA DESDE CXC49...");
                    objObj[0]=objParSis;
                    objObj[1]=new String(""+intcodemp);
                    objObj[2]=new String(txtCodCli.getText());
                    objObj[3]=new String(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_COD_SOL).toString());
                }
                else
                {
                    System.out.println("---ENTRO POR LA FILA CON DATOS DESDE CXC49...");
                    objObj[0]=objParSis;
                    objObj[1]=new String(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_COD_EMP).toString());
                    objObj[2]=new String(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_COD_CLI).toString());                    
                    objObj[3]=new String(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_COD_SOL).toString());
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
     * Esta funciï¿½n permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDatReg()
    {
        int intCodEmp, intCodLoc, intCodTipDoc, intCanReg=0, intCodMnu, intCodUsr;
        boolean blnRes=true;
        //double dblValPnd=0.00;
        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                intCodMnu=objParSis.getCodigoMenu();
                intCodUsr=objParSis.getCodigoUsuario();
                
                if (con!=null)
                {
                    stm=con.createStatement();
                    
                        //Obtener la condicion Auxiliar.
                        strAux="";
                        
                        ////////Condicion para filtro por cliente
                        if (txtCodCli.getText().length()>0)
                            strAux+=" AND a1.co_cli=" + txtCodCli.getText();

                        ////////Condicion para filtro por cliente en un rango especifico
                        if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
                            strAux+=" AND ((LOWER(a2.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a2.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";

                    
/* select a1.co_emp, a1.co_sol, a1.fe_sol, a1.co_cli, a2.tx_nom
from tbm_solcre as a1
inner join tbm_cli as a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli)
where a1.co_emp=1 
order by a2.tx_nom, a1.co_sol */                    

                        //Para los demï¿½s modos se muestra: sï¿½lo los documentos pagados.
                        strSQL="";
                        strSQL+=" SELECT COUNT(*) ";
                        strSQL+=" FROM (";
                        strSQL+="  SELECT a1.co_emp, a1.co_sol, a1.fe_sol, a1.co_cli, a2.tx_nom, a2.fe_ultactdat, a2.ne_nummesproactdat, a2.fe_proactdat ";
                        strSQL+="  FROM tbm_solcre as a1 ";
                        strSQL+="  INNER JOIN tbm_cli as a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                        
                        if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                        {
                            strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                            strSQL+=strAux;
                            strSQL+=" AND a1.st_anasol='A' AND a1.st_reg='A' AND a2.st_reg='A' ";
                            strSQL+=" ORDER BY a2.tx_nom, a1.co_sol ";
                        }
                        else
                        {
                            strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli)";
                            strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                            strSQL+=" AND a3.co_loc = " + intCodLoc + "";
                            strSQL+=strAux;
                            strSQL+=" AND a1.st_anasol='A'  AND a1.st_reg='A' AND a2.st_reg='A' ";
                            strSQL+=" ORDER BY a2.tx_nom, a1.co_sol ";
                        }
                        
                        strSQL+=" ) AS A";
                        
//                        System.out.println("---COUNT---Estoy en la Funcion cargarDatReg--: " + strSQL);
                        
                        intCanReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                        if (intCanReg==-1)
                            return false;
                        
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        
                        strSQL="";
                        strSQL+="  SELECT a1.co_emp, a1.co_sol, a1.fe_sol, a1.co_cli, a2.tx_nom, a2.fe_ultactdat, a2.ne_nummesproactdat, a2.fe_proactdat ";
                        strSQL+="  FROM tbm_solcre as a1 ";
                        strSQL+="  INNER JOIN tbm_cli as a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                        
                        if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                        {
                            strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                            strSQL+=strAux;
                            strSQL+=" AND a1.st_anasol='A' AND a1.st_reg='A' AND a2.st_reg='A' ";
                            strSQL+=" ORDER BY a2.tx_nom, a1.co_sol ";
                        }
                        else
                        {
                            strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli)";
                            strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                            strSQL+=" AND a3.co_loc = " + intCodLoc + "";
                            strSQL+=strAux;
                            strSQL+=" AND a1.st_anasol='A' AND a1.st_reg='A' AND a2.st_reg='A' ";
                            strSQL+=" ORDER BY a2.tx_nom, a1.co_sol ";
                        }
                        
                        
                        ///System.out.println("---Estoy en la Funcion cargarDetRegOpc2--Para las que NO SON RETENCIONES: " + strSQL);   
                        System.out.println("---Estoy en la Funcion cargarDatReg--: " + strSQL);   
                        
                        rst=stm.executeQuery(strSQL);  
                        
                        
                        //Obtener los registros.
                        lblMsgSis.setText("Cargando datos...");
                        pgrSis.setMinimum(0);
                        pgrSis.setMaximum(intCanReg);
                        pgrSis.setValue(0);

            
                        for(int i=0;rst.next();i++)
                        {
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_LINEA, "");////0
                            vecReg.add(INT_TBL_SEL, "");////1
                            vecReg.add(INT_TBL_COD_EMP, rst.getString("co_emp"));////2
                            vecReg.add(INT_TBL_COD_SOL, rst.getString("co_sol"));////3
                            vecReg.add(INT_TBL_FEC_SOL, rst.getString("fe_sol"));////4
                            vecReg.add(INT_TBL_COD_CLI, rst.getString("co_cli"));////5
                            vecReg.add(INT_TBL_NOM_CLI, rst.getString("tx_nom"));////6
                            vecReg.add(INT_TBL_FEC_ULT_ACT_DAT, rst.getString("fe_ultactdat"));////7
                            vecReg.add(INT_TBL_MES_PRO_ACT_DAT, rst.getString("ne_nummesproactdat"));////8
                            vecReg.add(INT_TBL_FEC_PRO_ACT_DAT, rst.getString("fe_proactdat"));////9
                            vecReg.add(INT_TBL_BOT_PAN, "");////10
                            
                            vecDat.add(vecReg);
                            
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
                        
                        if (intCanReg==tblDat.getRowCount())
                            lblMsgSis.setText("Se encontraron " + intCanReg + " registros.");
                        else
                            lblMsgSis.setText("Se encontraron " + intCanReg + " registros pero sï¿½lo se procesaron " + tblDat.getRowCount() + ".");
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
    
    
    private boolean actualizarTbmCli()
    {
        boolean blnRes=true;
        int i=0, x=0;
        String strEstAnaSol="";
        java.util.Calendar objFecDivPag = objFec.getInstance();
        
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                stm=con.createStatement();
                String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                System.out.println("---fecha Sistemas--- strFecSis--- " + strFecSis);
                
                int intvalProAct = Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_MES_PRO_ACT_DAT)==null?"0":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_MES_PRO_ACT_DAT).toString());
                System.out.println("---intvalProAct: " + intvalProAct);
                
                
                String strfecUltAct = objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_FEC_ULT_ACT_DAT)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_FEC_ULT_ACT_DAT).toString();
                System.out.println("---strfecUltAct: " + strfecUltAct);
                        
                        
                //Obtener la fecha y hora del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                String strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
                System.out.println("---FEC_DIA_HOY ES strFecHorSer: " + strFecHorSer);
                ///datFecAux=null;
                
                for (i=0; i<objTblMod.getRowCountTrue(); i++)
                {
                    if (objTblMod.isChecked(i, INT_TBL_SEL)) 
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="UPDATE tbm_cli";
                        strSQL+=" SET ";
                        strSQL+=" fe_ultActDat = '" + strFecSis + "'";
                        
                        if(intvalProAct > 0)
                        {
                            System.out.println("---IF--intvalProAct > 0: " + intvalProAct);
                            
                            int intDiaCre = (intvalProAct * 30);
                            
                            System.out.println("---EL NUMERO DIAS intDiaCre es: " + intDiaCre);
                        
                            /////fecha de vencimiento/////
                            objFecDivPag.setTime(objFec.getTime()); 

                            if (intDiaCre!=0)
                                objFecDivPag.add(java.util.Calendar.DATE, intDiaCre);

                            dtePckPag.setAnio( objFecDivPag.get(java.util.Calendar.YEAR));
                            dtePckPag.setMes( objFecDivPag.get(java.util.Calendar.MONTH)+1);
                            dtePckPag.setDia(objFecDivPag.get(java.util.Calendar.DAY_OF_MONTH));

                            System.out.println("---LA NUEVA FECHA dtePckPag es: " + dtePckPag.getText());
                          
                            objTblMod.setValueAt(dtePckPag.getText(),tblDat.getSelectedRow(),INT_TBL_FEC_PRO_ACT_DAT);
                            String strFec = objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_FEC_PRO_ACT_DAT)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_FEC_PRO_ACT_DAT).toString();
                            String strFecProAct = objUti.formatearFecha(strFec, "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos());
                            strSQL+=", fe_proActDat = '" + strFecProAct + "'";
                        }
                        
                        ///strSQL+=" fe_ultActDat = '" + objUti.formatearFecha(datFecAux,"yyyy/MM/dd") + "'";
                        strSQL+=" WHERE co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_COD_EMP));;
                        strSQL+=" AND co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_COD_CLI));
                        System.out.println("---SQL del update tbm_cli---: " + strSQL);
                        stm.executeUpdate(strSQL);
                    }                    
                }
                con.commit();
                blnRes=true;

                stm.close();
                stm=null;
            }
            else
            {
                System.out.println("---esta falso en con!=null---");
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
    
    private void ocultaCol(int intCol)
    {
        tblDat.getColumnModel().getColumn(intCol).setWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);         
    
    }
    
    private void mostrarCol(int intCol, int tamCol)
    {
        tblDat.getColumnModel().getColumn(intCol).setWidth(tamCol);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(tamCol);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(tamCol);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(tamCol);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);         
    
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
    private boolean mostrarVenConCli(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.show();
                    if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Cï¿½digo".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
                    if (vcoCli.buscar("a1.tx_nom", txtNomCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtNomCli.setText(strDesLarCli);
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
            if (!cargarDatReg())
            ///if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sï¿½lo cuando haya datos.
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
                case INT_TBL_LINEA:
                    strMsg="";
                    break;
                case INT_TBL_SEL:
                    strMsg="";
                    break;
                case INT_TBL_COD_EMP:
                    strMsg="Codigo de la empresa";
                    break;
                case INT_TBL_COD_SOL:
                    strMsg="Codigo de la Solicitud de Credito";
                    break;
                case INT_TBL_FEC_SOL:
                    strMsg="Fecha de la Solicitud de Credito";
                    break;                    
                case INT_TBL_COD_CLI:
                    strMsg="Codigo del cliente";
                    break;
                case INT_TBL_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;                    
                case INT_TBL_FEC_ULT_ACT_DAT:
                    strMsg="Fecha de Ultima Actualizacion de Datos";
                    break;
                case INT_TBL_MES_PRO_ACT_DAT:
                    strMsg="Mes de Proxima Actualizacion de Datos";
                    break;
                case INT_TBL_FEC_PRO_ACT_DAT:
                    strMsg="Fecha de Proxima Actualizacion de Datos";
                    break;
                case INT_TBL_BOT_PAN:
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