/*
 * ZafMae20.java
 *
 * Created on 14 Octubre , 2013, 8:44 PM
 */

package Maestros.ZafMae20;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;



/**
 *
 * @author  José Marín 
 */
public class ZafMae20 extends javax.swing.JInternalFrame {
    
    private ZafParSis objParSis;
    private ZafVenCon vcoPrg;
    private ZafTblCelEdiTxt objTblCelEdiTxt;//Editor: JTextField en celda.
    private ZafUtil objUti;//Objeto del tipo de la clase ZafUtil, el cual me va a permitir 
    private ZafTblMod objTblMod;
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu;
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelRenBut objTblCelRenBut;//Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButDG1;
    private ZafTblOrd objTblOrd;
    private ZafTblBus objTblBus;
    private ZafVenCon vcoUsr;
    private ZafVenCon vecLoc; 
    

    
    private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButGenDG1;
    private ZafTblFilCab objTblFilCab;
    private boolean blnHayCam;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private boolean blnCon;//true: Continua la ejecución del hilo. // Continuidad del hilo
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenLbl objTblCelRenLblCod;                 //Render: Presentar JLabel en JTable (Números).
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
    private String strMarCod;
    private String strMarNom;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChkPre; 
    private String strComCod;
    private String strComNom;
    
    private ZafVenCon vcoMar;//j
    private ZafVenCon vcoCom;//j
    
    private String strChoCod;
    private String strChoNom;
    private ZafVenCon vcoCho;//j
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.
    
    private String strSQL, strAux;
    private Vector vecDat, vecReg, vecCab;

    private String strTipPrg;
    private String strNomPrg;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private int intSelPrd;
    private int intSelRepSelPrd;
   
    

    
    //Tabla 
    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_COD_REP=1;
    private final int INT_TBL_DAT_DES_LAR_REP=2;
    private final int INT_TBL_DAT_RUT_ABS=3;
    private final int INT_TBL_DAT_RUT_REL=4;
    private final int INT_TBL_DAT_NOM_REP=5;
    
  
    

    
    private Vector vecCodRep, vecDesLarRep, vecRutAbs, vecRutRel, vecNomRep;
    
    private boolean blnPre;
    private ZafPerUsr objPerUsr;                               //Objeto que almacena el perfil del usuario.
    private ZafDocLis objDocLis;
    
    
    
    /** Creates new form ZafMae20 */
    public ZafMae20(ZafParSis obj) {
        try{
            initComponents();
            objUti = new ZafUtil();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objTblCelEdiButGenDG1=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            objTblCelRenButDG1=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            if (!configurarFrm())
                exitForm();

        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panRpt = new javax.swing.JPanel();
        panDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodRep = new javax.swing.JTextField();
        txtNomRep = new javax.swing.JTextField();
        butRep = new javax.swing.JButton();
        txtNumCta = new javax.swing.JTextField();
        txtNumPro = new javax.swing.JTextField();
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
        lblTit.setText("Información del registro actual");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setPreferredSize(new java.awt.Dimension(459, 473));

        panRpt.setLayout(new java.awt.BorderLayout());

        panDet.setLayout(new java.awt.BorderLayout());

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
        spnDet.setViewportView(tblDat);

        panDet.add(spnDet, java.awt.BorderLayout.CENTER);

        panCab.setPreferredSize(new java.awt.Dimension(610, 30));
        panCab.setLayout(null);

        lblTipDoc.setText("Reporte:");
        lblTipDoc.setToolTipText("Programa");
        panCab.add(lblTipDoc);
        lblTipDoc.setBounds(2, 4, 70, 20);

        txtCodRep.setMaximumSize(null);
        txtCodRep.setPreferredSize(new java.awt.Dimension(70, 20));
        txtCodRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodRepActionPerformed(evt);
            }
        });
        txtCodRep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodRepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodRepFocusLost(evt);
            }
        });
        panCab.add(txtCodRep);
        txtCodRep.setBounds(72, 4, 80, 20);

        txtNomRep.setMaximumSize(null);
        txtNomRep.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNomRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomRepActionPerformed(evt);
            }
        });
        txtNomRep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomRepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomRepFocusLost(evt);
            }
        });
        panCab.add(txtNomRep);
        txtNomRep.setBounds(152, 4, 500, 20);

        butRep.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butRep.setText("...");
        butRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butRepActionPerformed(evt);
            }
        });
        panCab.add(butRep);
        butRep.setBounds(650, 5, 24, 20);

        txtNumCta.setMaximumSize(null);
        txtNumCta.setPreferredSize(new java.awt.Dimension(70, 20));
        panCab.add(txtNumCta);
        txtNumCta.setBounds(180, 25, 0, 0);

        txtNumPro.setMaximumSize(null);
        txtNumPro.setPreferredSize(new java.awt.Dimension(70, 20));
        panCab.add(txtNumPro);
        txtNumPro.setBounds(180, 46, 0, 0);

        panDet.add(panCab, java.awt.BorderLayout.PAGE_START);

        panRpt.add(panDet, java.awt.BorderLayout.CENTER);

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

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
// TODO add your handling code here:
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                if(con!=null){
                    con.close();
                    con=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e)
        {
            dispose();
        }        
}//GEN-LAST:event_exitForm

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        //objTblMod.removeAllRows();
        if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
             cargarDetReg();
            if (objThrGUI==null)
            {
                objThrGUI=new ZafMae20.ZafThreadGUI();
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

    private void txtCodRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodRepActionPerformed
        // TODO add your handling code here:
        txtNomRep.transferFocus();
    }//GEN-LAST:event_txtCodRepActionPerformed

    private void txtCodRepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodRepFocusGained
        // TODO add your handling code here:
        strTipPrg=txtCodRep.getText();
        txtCodRep.selectAll();
    }//GEN-LAST:event_txtCodRepFocusGained

    private void txtCodRepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodRepFocusLost
        // TODO add your handling code here:
        if (!txtCodRep.getText().equalsIgnoreCase(strTipPrg)){
            if (txtCodRep.getText().equals("")){
                txtCodRep.setText("");
                txtNomRep.setText("");
            }
            else{
                mostrarReportes(1);
            }
        }
        else
        txtCodRep.setText(strTipPrg);
    }//GEN-LAST:event_txtCodRepFocusLost

    private void txtNomRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomRepActionPerformed
        // TODO add your handling code here:
        txtNomRep.transferFocus();
    }//GEN-LAST:event_txtNomRepActionPerformed

    private void txtNomRepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomRepFocusGained
        // TODO add your handling code here:
        strNomPrg=txtNomRep.getText();
        txtNomRep.selectAll();
    }//GEN-LAST:event_txtNomRepFocusGained

    private void txtNomRepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomRepFocusLost
        // TODO add your handling code here:
        if (!txtNomRep.getText().equalsIgnoreCase(strNomPrg)){
            if (txtNomRep.getText().equals("")){
                txtCodRep.setText("");
                txtNomRep.setText("");
            }
            else{
                mostrarReportes(2);
            }
        }
        else
        txtNomRep.setText(strNomPrg);
    }//GEN-LAST:event_txtNomRepFocusLost

    private void butRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRepActionPerformed
        // TODO add your handling code here:
        mostrarReportes(0);
    }//GEN-LAST:event_butRepActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butRep;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodRep;
    private javax.swing.JTextField txtNomRep;
    private javax.swing.JTextField txtNumCta;
    private javax.swing.JTextField txtNumPro;
    // End of variables declaration//GEN-END:variables

//    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }   
    
       
    
//    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            //System.out.println("configurarFrm....");
            objUti=new ZafUtil();
            objPerUsr=new ZafPerUsr(objParSis);
            intSelPrd=0;
            blnPre=false;
            this.setTitle(objParSis.getNombreMenu() + " v0.1");
            lblTit.setText(objParSis.getNombreMenu());
            configurarReportes();
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_REP,"Cód.Rep.");
            vecCab.add(INT_TBL_DAT_DES_LAR_REP,"Reporte");
            vecCab.add(INT_TBL_DAT_RUT_ABS,"Rut.Abs.");
            vecCab.add(INT_TBL_DAT_RUT_REL,"Rut.Rel.");
            vecCab.add(INT_TBL_DAT_NOM_REP,"Nom.Rep.");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setCellSelectionEnabled(true);
            //tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();

            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_REP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_REP).setPreferredWidth(230);
            tcmAux.getColumn(INT_TBL_DAT_RUT_ABS).setPreferredWidth(125);
            tcmAux.getColumn(INT_TBL_DAT_RUT_REL).setPreferredWidth(125);
            tcmAux.getColumn(INT_TBL_DAT_NOM_REP).setPreferredWidth(130);
            
            
            objTblCelRenLblCod=new ZafTblCelRenLbl();
            objTblCelRenLblCod.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_REP).setCellRenderer(objTblCelRenLblCod);
            
            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            objTblFilCab=null;
            
            objTblOrd=new ZafTblOrd(tblDat);
            objTblBus=new ZafTblBus(tblDat);
            objDocLis=new ZafDocLis();
           // System.out.println("Se configuara la tabla!!!!... ");
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);           
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
    
//    /**
//     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
//     * @return true: Si se pudo consultar los registros.
//     * <BR>false: En el caso contrario.
//     */
    
    private boolean cargarDetReg(){
        int i,temp=0,j=0;
        String strAux="";
        
        objUti=new ZafUtil();
        boolean blnRes=true;
        strAux="";
        try{
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
            strSQL=" ";
            strSQL+=" SELECT a1.co_rpt, a1.tx_desCor, a1.tx_rutAbsRpt, a1.tx_rutRelRpt, a1.tx_nomRpt";
            strSQL+=" FROM tbm_rptSis as a1 ";
            strSQL+=" WHERE a1.st_reg ='A'";
            if(txtCodRep.getText().length()>0)
            {
                strSQL+=" and a1.co_rpt=" + txtCodRep.getText();
            }
            strSQL+=" ORDER BY a1.co_rpt ";
            System.out.println("zafMae20: "+strSQL);
            rst=stm.executeQuery(strSQL);
            vecDat.clear();
            lblMsgSis.setText("Cargando datos...");
            i=0;	   	
            vecCodRep = new Vector();
            vecDesLarRep = new Vector();
            vecRutAbs = new Vector();
            vecRutRel = new Vector();
            vecNomRep = new Vector();
            while(rst.next()){
                if (blnCon){
                   vecCodRep.add(j,rst.getString("co_rpt"));
                   if(rst.getString("tx_desCor")!=null) {vecDesLarRep.add(j,rst.getString("tx_desCor"));}
                   else {vecDesLarRep.add(j,"");}
                   if(rst.getString("tx_rutAbsRpt")!=null) {vecRutAbs.add(j,rst.getString("tx_rutAbsRpt"));}
                   else {vecRutAbs.add(j,"");}
                   if(rst.getString("tx_rutRelRpt")!=null) {vecRutRel.add(j,rst.getString("tx_rutRelRpt"));}
                   else {vecRutRel.add(j,"");}
                   if(rst.getString("tx_nomRpt")!=null) {vecNomRep.add(j,rst.getString("tx_nomRpt"));}
                   else {vecNomRep.add(j,"");}
                   j++;
                }
              }
            int jota=0;
            while(j>jota){
               vecReg= new Vector(); 
               vecReg.add(INT_TBL_DAT_LIN,"");
               if(vecCodRep.get(jota)!=null){vecReg.add(INT_TBL_DAT_COD_REP,vecCodRep.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_COD_REP," ");}
               if(vecDesLarRep.get(jota)!=null) {vecReg.add(INT_TBL_DAT_DES_LAR_REP,vecDesLarRep.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_DES_LAR_REP," ");}
               if(vecRutAbs.get(jota)!=null) {vecReg.add(INT_TBL_DAT_RUT_ABS,vecRutAbs.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_RUT_ABS," ");}
               if(vecRutRel.get(jota)!=null) {vecReg.add(INT_TBL_DAT_RUT_REL,vecRutRel.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_RUT_REL," ");}
               if(vecNomRep.get(jota)!=null){vecReg.add(INT_TBL_DAT_NOM_REP,vecNomRep.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_NOM_REP," ");}
               vecDat.add(vecReg);
               jota++;
            }
         }     
        objTblMod.setData(vecDat);
        tblDat.setModel(objTblMod);
        vecDat.clear();
        if(blnCon){
                 lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
         }
        else{
             lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
        }
        rst.close();
        stm.close();
        con.close();
        rst=null;
        stm=null;
        con=null;
        butCon.setText("Consultar");
        pgrSis.setIndeterminate(false);
    }       
   catch (java.sql.SQLException e){
       blnRes=false;
       objUti.mostrarMsgErr_F1(this, e);
   }
   catch (Exception e){
       blnRes=false;
       objUti.mostrarMsgErr_F1(this, e);
   }
   return blnRes;
}
    
    
//    /**
//     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
//     * del mouse (mover el mouse; arrastrar y soltar).
//     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
//     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
//     * resulta muy corto para mostrar leyendas que requieren más espacio.
//     */
    
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
      case INT_TBL_DAT_COD_REP: strMsg="Código del Reporte"; break;
      case INT_TBL_DAT_DES_LAR_REP: strMsg="Nombre del Reporte"; break;
      case INT_TBL_DAT_RUT_ABS: strMsg="Ruta Absoluta del Reporte"; break;
      case INT_TBL_DAT_RUT_REL: strMsg="Ruta Relativa del Reporte"; break;
      case INT_TBL_DAT_NOM_REP: strMsg="Nombre del Reporte"; break;
      default: strMsg=""; break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
      
//    /**
//     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a utilizar
//     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
//     * debe llenar o corregir.
//     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    

//    /**
//     * Esta clase implementa la interface DocumentListener que observa los cambios que
//     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
//     * Se la usa en el sistema para determinar si existe alg�n cambio que se deba grabar
//     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
//     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
//     * presentar� un mensaje advirtiendo que si no guarda los cambios los perder�.
//     */
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }
    }
    
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
            }
            objThrGUI=null;
        }
    }
    
    
//    /**
//     * Esta funci�n muestra un mensaje "showConfirmDialog". Presenta las opciones
//     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
//     * seleccionando una de las opciones que se presentan.
//     */
    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
   
    
    
//     /**
//     * Esta clase hereda de la interface TableModelListener que permite determinar
//     * cambios en las celdas del JTable.
//     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }

    
    /*
     * llenar la consulta de marcas  
     */
    
    private boolean configurarReportes()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_rpt");
            arlCam.add("a1.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("500");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_rpt, a1.tx_desCor";
            strSQL+=" FROM tbm_rptSis a1 WHERE a1.st_reg ='A'";
            strSQL+=" ORDER BY a1.co_rpt";
            System.out.println("configurarReportes:.." + strSQL);
            vcoPrg=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Reportes", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoPrg.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    private boolean mostrarReportes(int intTipBus){
        boolean blnRes=true;
        try{
          //  System.out.println("mostrarReportes....");
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoPrg.setCampoBusqueda(1);
                    vcoPrg.show();
                    if (vcoPrg.getSelectedButton()==vcoPrg.INT_BUT_ACE){
                        txtCodRep.setText(vcoPrg.getValueAt(1));
                        txtNomRep.setText(vcoPrg.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoPrg.buscar("a1.co_rpt", txtCodRep.getText())){
                        txtCodRep.setText(vcoPrg.getValueAt(1));
                        txtNomRep.setText(vcoPrg.getValueAt(2));
                    }
                    else{
                        vcoPrg.setCampoBusqueda(0);
                        vcoPrg.setCriterio1(11);
                        vcoPrg.cargarDatos();
                        vcoPrg.show();
                        if (vcoPrg.getSelectedButton()==vcoPrg.INT_BUT_ACE)
                        {
                            txtCodRep.setText(vcoPrg.getValueAt(1));
                            txtNomRep.setText(vcoPrg.getValueAt(2));
                        }
                        else
                        {
                            txtCodRep.setText(strTipPrg);
                        }
                    }
                    
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoPrg.buscar("a1.tx_desCor", txtNomRep.getText())){
                        txtCodRep.setText(vcoPrg.getValueAt(1));
                        txtNomRep.setText(vcoPrg.getValueAt(2));
                    }
                    else{
                        vcoPrg.setCampoBusqueda(1);
                        vcoPrg.setCriterio1(11);
                        vcoPrg.cargarDatos();
                        vcoPrg.show();
                        if (vcoPrg.getSelectedButton()==vcoPrg.INT_BUT_ACE)
                        {
                            txtCodRep.setText(vcoPrg.getValueAt(1));
                            txtNomRep.setText(vcoPrg.getValueAt(2));
                        }
                        else
                        {
                            txtNomRep.setText(strNomPrg);
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
}
