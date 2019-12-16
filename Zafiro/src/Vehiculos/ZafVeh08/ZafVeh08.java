/*
 * ZafMae20.java
 *
 * Created on 19 Agosto , 2013, 8:44 PM
 */

package Vehiculos.ZafVeh08;
import Vehiculos.ZafVeh07.ZafVeh07;
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
public class ZafVeh08 extends javax.swing.JInternalFrame {
    
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
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg;//Editor: JButton en celda (Bodega origen).
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg2;//Editor: JButton en celda (Bodega origen).
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg3;
    
    private ZafTblCelEdiButDlg objTblCelEdiButDlg;              //Editor: JButton en celda.

    
    private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButGenDG1;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenCbo objTblCelRenCmbBox;
    private ZafTblCelEdiCbo objTblCelEdiCmbBox;
    private ZafTblCelRenCbo objTblCelRenCmbBox2;
    private ZafTblCelEdiCbo objTblCelEdiCmbBox2;
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
    private String strVersion=" v0.3";
    //José Marín M. 25/Feb/2014
    
    private ZafVenCon vcoMar;//j
    private ZafVenCon vcoCom;//j
    
    private String strChoCod;
    private String strChoNom;
    private ZafVenCon vcoCho;//j
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;           //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm2;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm3;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm4;
    
    private String strSQL, strAux;
    private Vector vecDat, vecReg, vecCab, vecAux;
    private Vector vecVeh, vecCom;
    private String strTipPrg;
    private String strNomPrg;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private int intSelPrd;
    private int intSelRepSelPrd;
   
    
    private Vehiculos.ZafVeh07.ZafVeh07 objVeh07;
    private ZafTblCelRenBut objTblCelRenButConGre, objTblCelRenButConOcp;  

    
    //Tabla 
    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_COD_LOC=1;
    private final int INT_TBL_DAT_COD_TIP_DOC=2;
    private final int INT_TBL_DAT_DES_COR_TIP_DOC=3;
    private final int INT_TBL_DAT_DES_LAR_TIP_DOC=4;
    private final int INT_TBL_DAT_COD_DOC=5;
    private final int INT_TBL_DAT_NUM_DOC=6;
    private final int INT_TBL_DAT_FEC_DOC=7;
    private final int INT_TBL_DAT_NOM_VEH=8;
    private final int INT_TBL_DAT_VAL_CAL=9;//aki
    private final int INT_TBL_DAT_VAL_MAN=10;
    private final int INT_TBL_DAT_VAL_EFE=11;
    private final int INT_TBL_DAT_VAL_DOC=12;
    private final int INT_TBL_DAT_CON_TIK_COM=13;
    private final int INT_TBL_DAT_CHK_AUT=14;
    private final int INT_TBL_DAT_CHK_DEN=15;
    private final int INT_TBL_DAT_OBS=16;
    private final int INT_TBL_DAT_CON_OBS=17;
    
    private String strCodMar, strNomMar;                //Contenido del campo al obtener el foco.
    private String strCodCom, strNomCom;                //Contenido del campo al obtener el foco.
    
    private Vector vecCodLoc, vecCodTipDoc, vecDesCorTipDoc, vecDesLarTipDoc;
    private Vector vecCodDoc, vecNomDoc, vecFecDoc, vecNumDoc;
    private Vector vecNomVeh, vecValCal, vecValMan, vecValEfe; 
    private Vector vecValDoc, vecStAut, vecObs;
    private boolean blnPre;
    private ZafPerUsr objPerUsr;                               //Objeto que almacena el perfil del usuario.
    private ZafDocLis objDocLis;
    
    private Vehiculos.ZafVeh07.ZafVeh07 objReg;
    
    
    /** Creates new form ZafMae20 */
    public ZafVeh08(ZafParSis obj) {
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
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
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

        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

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
        objTblMod.removeAllRows();
        if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
             cargarDetReg();
            if (objThrGUI==null)
            {
                objThrGUI=new ZafVeh08.ZafThreadGUI();
                objThrGUI.start();    
            }            
        }
        else
        {
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
      if (objTblMod.isDataModelChanged())
        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
                if (actualizarDet())
                    if(cargarDetReg())
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                else
                    System.out.println("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
            }
        }
        else
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");

    }//GEN-LAST:event_butGuaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
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
            System.out.println("configurarFrm....");
            objUti=new ZafUtil();
            objPerUsr=new ZafPerUsr(objParSis);
            intSelPrd=0;
            blnPre=false;
            this.setTitle(objParSis.getNombreMenu() + strVersion);
            lblTit.setText(objParSis.getNombreMenu());
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_NOM_VEH,"Vehículo");
            vecCab.add(INT_TBL_DAT_VAL_CAL,"Val.Cal.");
            vecCab.add(INT_TBL_DAT_VAL_MAN,"Val.Man.");
            vecCab.add(INT_TBL_DAT_VAL_EFE,"Val.Efe.");
            vecCab.add(INT_TBL_DAT_VAL_DOC,"Val.Doc.");
            vecCab.add(INT_TBL_DAT_CON_TIK_COM,"");
            vecCab.add(INT_TBL_DAT_CHK_AUT,"Autorizar");
            vecCab.add(INT_TBL_DAT_CHK_DEN,"Denegar");
            vecCab.add(INT_TBL_DAT_OBS,"Observación");
            vecCab.add(INT_TBL_DAT_CON_OBS,"");
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
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_NOM_VEH).setPreferredWidth(300);            
            tcmAux.getColumn(INT_TBL_DAT_VAL_CAL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VAL_MAN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VAL_EFE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CON_TIK_COM).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_OBS).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_CON_OBS).setPreferredWidth(20);
            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CON_TIK_COM).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CON_OBS).setResizable(false);
            
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            
            tcmAux.getColumn(INT_TBL_DAT_VAL_CAL).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_VAL_MAN).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_VAL_EFE).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLblNum);
            
            
            objTblCelRenLblCod=new ZafTblCelRenLbl();
            objTblCelRenLblCod.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setCellRenderer(objTblCelRenLblCod);
            
            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CON_TIK_COM);
            vecAux.add("" + INT_TBL_DAT_CHK_AUT);
            vecAux.add("" + INT_TBL_DAT_CHK_DEN);
            vecAux.add("" + INT_TBL_DAT_OBS);
            vecAux.add("" + INT_TBL_DAT_CON_OBS);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            objTblFilCab=null;
            
            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_CON_TIK_COM).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_CON_OBS).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;

             objTblCelRenButConOcp=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_CON_TIK_COM).setCellRenderer(objTblCelRenButConOcp);
            objTblCelRenButConOcp = null;
            
            new ButConOcp(tblDat, INT_TBL_DAT_CON_TIK_COM);
            
            
            
            ButCot butCot = new ButCot(tblDat, INT_TBL_DAT_CON_OBS); //*****
            
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setCellRenderer(objTblCelRenChk);
            //ZafMae07  para las autorizaciones  
            objTblCelEdiChkPre = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setCellEditor(objTblCelEdiChkPre);
            objTblCelEdiChkPre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                int intNumFil = tblDat.getSelectedRow();
                tblDat.setValueAt(false, intNumFil, INT_TBL_DAT_CHK_DEN);
                tblDat.setValueAt(true, intNumFil, INT_TBL_DAT_CHK_AUT);
            }
            });
            objTblCelEdiChkPre = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setCellEditor(objTblCelEdiChkPre);
            objTblCelEdiChkPre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                int intNumFil = tblDat.getSelectedRow();
                tblDat.setValueAt(true, intNumFil, INT_TBL_DAT_CHK_DEN);
                tblDat.setValueAt(false, intNumFil, INT_TBL_DAT_CHK_AUT);
            }
            });
            
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblOrd=new ZafTblOrd(tblDat);
            objTblBus=new ZafTblBus(tblDat);
            objDocLis=new ZafDocLis();
            System.out.println("Se configuara la tabla!!!!... ");
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
            strSQL+=" select distinct a1.co_loc,a1.co_tipDoc,a4.tx_desCor as tx_desCorTipDoc,";
            strSQL+=" a4.tx_desLar as tx_deslarTipDoc, ";
            strSQL+=" a1.co_doc, a1.ne_numDoc,a1.fe_doc,a3.tx_desLar as tx_desLarVeh,";
            strSQL+=" a1.nd_valCal,a1.nd_valMan,a1.nd_valEfe,a1.nd_valDoc,a1.tx_obsAut as tx_obs,a1.st_aut ";
            strSQL+=" FROM tbm_cabTicCom as a1 ";
            strSQL+=" INNER JOIN tbr_tipDocPrg as a2 ON (a1.co_emp=a2.co_emp and";
            strSQL+=" a1.co_tipDoc=a2.co_tipDoc and a1.co_loc=a2.co_loc)";
            strSQL+=" INNER JOIN tbm_veh as a3 ON (a1.co_veh=a3.co_veh)";
            strSQL+=" INNER JOIN tbm_cabTipDoc as a4 ON (a1.co_emp=a4.co_emp and";
            strSQL+=" a1.co_tipDoc=a4.co_tipDoc and a1.co_loc=a4.co_loc) ";
            strSQL+=" WHERE a1.st_aut='P' AND a1.st_reg='A'";
            strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" ORDER BY a1.co_loc,a1.fe_doc";
            System.out.println("zabVeh08: "+strSQL);
            rst=stm.executeQuery(strSQL);
            vecDat.clear();
            lblMsgSis.setText("Cargando datos...");
            i=0;	   	
            vecCodLoc = new Vector();
            vecCodTipDoc = new Vector();
            vecDesCorTipDoc = new Vector();
            vecDesLarTipDoc = new Vector();
            vecCodDoc = new Vector();
            vecNumDoc = new Vector();
            vecFecDoc = new Vector();
            vecNomVeh = new Vector();
            vecValCal = new Vector();
            vecValMan = new Vector();
            vecValEfe = new Vector();
            vecValDoc = new Vector();
            vecStAut = new Vector();
            vecObs = new Vector();
            while(rst.next()){
                if (blnCon){
                   vecCodLoc.add(j,rst.getString("co_loc"));
                   if(rst.getString("co_TipDoc")!=null) {vecCodTipDoc.add(j,rst.getString("co_tipDoc"));}
                   else {vecCodTipDoc.add(j,"");}
                   if(rst.getString("tx_desCorTipDoc")!=null) {vecDesCorTipDoc.add(j,rst.getString("tx_desCorTipDoc"));}
                   else {vecDesCorTipDoc.add(j,"");}
                   if(rst.getString("tx_desLarTipDoc")!=null) {vecDesLarTipDoc.add(j,rst.getString("tx_desLarTipDoc"));}
                   else {vecDesLarTipDoc.add(j,"");}
                   if(rst.getString("co_doc")!=null) {vecCodDoc.add(j,rst.getString("co_doc"));}
                   else {vecCodDoc.add(j,"");}
                   if(rst.getString("ne_numDoc")!=null) {vecNumDoc.add(j,rst.getString("ne_numDoc"));}
                   else {vecNumDoc.add(j,"");}
                   if(rst.getString("fe_doc")!=null) {vecFecDoc.add(j,rst.getString("fe_doc"));}
                   else {vecFecDoc.add(j,"");}
                   if(rst.getString("tx_desLarVeh")!=null) {vecNomVeh.add(j,rst.getString("tx_desLarVeh"));}
                   else {vecNomVeh.add(j,"");}
                   if(rst.getString("nd_valCal")!=null) {vecValCal.add(j,rst.getString("nd_valCal"));}
                   else {vecValCal.add(j,"");}  
                   if(rst.getString("nd_valMan")!=null) {vecValMan.add(j,rst.getString("nd_valMan"));}
                   else {vecValMan.add(j,"");}
                   if(rst.getString("nd_valEfe")!=null) {vecValEfe.add(j,rst.getString("nd_valEfe"));}
                   else {vecValEfe.add(j,"");}
                   if(rst.getString("nd_valDoc")!=null) {vecValDoc.add(j,rst.getString("nd_valDoc"));}
                   else {vecValDoc.add(j,"");}
                   if(rst.getString("st_aut")!=null) {vecStAut.add(j,rst.getString("st_aut"));}
                   else {vecStAut.add(j,"");}
                   if(rst.getString("tx_obs")!=null) {vecObs.add(j,rst.getString("tx_obs"));}
                   else {vecObs.add(j,"");}
                   j++;
                }
                else{break;}
              }
            int jota=0;
            while(j>jota){
               vecReg= new Vector(); 
               vecReg.add(INT_TBL_DAT_LIN,"");
               if(vecCodLoc.get(jota)!=null){vecReg.add(INT_TBL_DAT_COD_LOC,vecCodLoc.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_COD_LOC," ");}
               if(vecCodTipDoc.get(jota)!=null) {vecReg.add(INT_TBL_DAT_COD_TIP_DOC,vecCodTipDoc.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_COD_TIP_DOC," ");}
               if(vecDesCorTipDoc.get(jota)!=null) {vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC,vecDesCorTipDoc.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC," ");}
               if(vecDesLarTipDoc.get(jota)!=null) {vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC,vecDesLarTipDoc.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC," ");}
               if(vecCodDoc.get(jota)!=null){vecReg.add(INT_TBL_DAT_COD_DOC,vecCodDoc.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_COD_DOC," ");}
               if(vecNumDoc.get(jota)!=null){vecReg.add(INT_TBL_DAT_NUM_DOC,vecNumDoc.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_NUM_DOC," ");}
               if(vecFecDoc.get(jota)!=null){vecReg.add(INT_TBL_DAT_FEC_DOC,vecFecDoc.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_FEC_DOC," ");}
               if(vecNomVeh.get(jota)!=null){vecReg.add(INT_TBL_DAT_NOM_VEH,vecNomVeh.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_NOM_VEH," ");}        
               if(vecValCal.get(jota)!=null){vecReg.add(INT_TBL_DAT_VAL_CAL,vecValCal.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_VAL_CAL," ");}
               if(vecValMan.get(jota)!=null){vecReg.add(INT_TBL_DAT_VAL_MAN,vecValMan.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_VAL_MAN," ");}               
               if(vecValEfe.get(jota)!=null){vecReg.add(INT_TBL_DAT_VAL_EFE,vecValEfe.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_VAL_EFE," ");}
               if(vecValDoc.get(jota)!=null){vecReg.add(INT_TBL_DAT_VAL_DOC,vecValDoc.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_VAL_DOC," ");}
               vecReg.add(INT_TBL_DAT_CON_TIK_COM,"Boton");
               if(vecStAut.get(jota)!=null)
               {
                   if(vecStAut.get(jota).toString().equals("A"))
                   {
                       vecReg.add(INT_TBL_DAT_CHK_AUT,true);
                       vecReg.add(INT_TBL_DAT_CHK_DEN,false);
                   }
//                   if(vecStAut.get(jota).toString().equals("D"))
//                   {
//                       vecReg.add(INT_TBL_DAT_CHK_AUT,false);
//                       vecReg.add(INT_TBL_DAT_CHK_DEN,true);
//                   }
                   if(vecStAut.get(jota).toString().equals("P"))
                   {
                       vecReg.add(INT_TBL_DAT_CHK_AUT,false);
                       vecReg.add(INT_TBL_DAT_CHK_DEN,false);
                   }
               }
               if(vecObs.get(jota)!=null){vecReg.add(INT_TBL_DAT_OBS,vecObs.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_OBS," ");}
               vecReg.add(INT_TBL_DAT_CON_OBS,"Boton");
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
//Línea/Cód.Loc./Cód.Tip.Doc./Tip.Doc./Tipo de documento/Cód.Doc./
//Núm.Doc./Fec.Doc./Vehículo/Val.Cal./Val.Man./Val.Efe./Val.Doc./CON/Autorizar/Denegar/
//Observación/CON
      case INT_TBL_DAT_COD_LOC: strMsg="Código del Local"; break;
      case INT_TBL_DAT_COD_TIP_DOC: strMsg="Código del tipo de Documento"; break;
      case INT_TBL_DAT_DES_COR_TIP_DOC: strMsg="Descripción corta del tipo de Documento"; break;
      case INT_TBL_DAT_DES_LAR_TIP_DOC: strMsg="Descripción larga del tipo de Documento"; break;
      case INT_TBL_DAT_COD_DOC: strMsg="Código del documento"; break;
      case INT_TBL_DAT_NUM_DOC: strMsg="Número del documento"; break;
      case INT_TBL_DAT_FEC_DOC: strMsg="Fecha del documento"; break;
      case INT_TBL_DAT_NOM_VEH: strMsg="Descripción larga del vehículo"; break;
      case INT_TBL_DAT_VAL_CAL: strMsg="Valor calculado"; break;
      case INT_TBL_DAT_VAL_MAN: strMsg="Valor manual"; break;
      case INT_TBL_DAT_VAL_EFE: strMsg="Valor en efectivo"; break;
      case INT_TBL_DAT_VAL_DOC: strMsg="Valor del documento"; break;
      case INT_TBL_DAT_CON_TIK_COM: strMsg="Muestra el 'Ticket de combustible'"; break;
      case INT_TBL_DAT_CHK_AUT: strMsg="Autorizar"; break;
      case INT_TBL_DAT_CHK_DEN: strMsg="Denegar"; break;
      case INT_TBL_DAT_OBS: strMsg="Observación de la autorización"; break;
      case INT_TBL_DAT_CON_OBS: strMsg="Muestra la Observación de la autorización'"; break;
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
   
    

  

    
 
    
     
//    /**
//     * Esta función permite actualizar los registros del detalle.
//     * @return true: Si se pudo actualizar los registros.
//     * <BR>false: En el caso contrario.
//     */
    
    
    private boolean actualizarDet()
    {
        int intNumFil, i;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                intNumFil=objTblMod.getRowCountTrue();
                for (i=0; i<intNumFil;i++){
                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="UPDATE tbm_cabTicCom";
                        strSQL+=" SET tx_obsAut='" + objTblMod.getValueAt(i,INT_TBL_DAT_OBS)+"'";
                        strSQL+=",fe_aut='" + objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos()) +"'";
                        strSQL+=",co_usrAut=" + objParSis.getCodigoUsuario() +"";  
                        strSQL+=",tx_comAut='" + objParSis.getNombreComputadoraConDirIP() +"'";
                        strSQL+=",co_usrmod="+ objParSis.getCodigoUsuario() + "";
                        if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_AUT))
                            strSQL+=",st_aut='A'";
                        else if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_DEN))
                            strSQL+=",st_aut='D'";
                        else 
                            strSQL+=",st_aut='P'";
                        strSQL+=" WHERE co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC) + " AND co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                        System.out.println(strSQL);
                        stm.executeUpdate(strSQL);
                    }
                }
                stm.close();
                con.close();
                stm=null;
                con=null;
                datFecAux=null;
                //Inicializo el estado de las filas.
                objTblMod.initRowsState();
                objTblMod.setDataModelChanged(false);
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
    
    private boolean configurarCombustible()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_com");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("200");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_com, a1.tx_deslar";
            strSQL+=" FROM tbm_comVeh a1 WHERE a1.st_reg ='A'";
            strSQL+=" ORDER BY a1.co_com";
            System.out.println("configurarCombustible:.." + strSQL);
            vcoCom=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Combustibles", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCom.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    
    private class ButConOcp extends Librerias.ZafTableColBut.ZafTableColBut_uni {
        public ButConOcp(javax.swing.JTable tbl, int intIdx) {
            super(tbl, intIdx, "Documento.");
        }
        public void butCLick() {
            int intCol = tblDat.getSelectedRow();
            llamarVentana( tblDat.getValueAt(intCol, INT_TBL_DAT_COD_DOC).toString());
        }    
    }
    
    
    
 private void llamarVentana(String strCodDoc){
     System.out.println("llamarVentana::.." + strCodDoc);
           if (objReg != null){
               objReg.dispose();
           }
           ZafVeh08 objReg08 = new  Vehiculos.ZafVeh08.ZafVeh08(objParSis);
          ZafVeh07 objReg = new  Vehiculos.ZafVeh07.ZafVeh07(objParSis,objReg08, strCodDoc);
          
  //ORIGINAL         this.getParent().add(objReg, javax.swing.JLayeredPane.DEFAULT_LAYER);
           this.getParent().add(objReg, javax.swing.JOptionPane.getFrameForComponent(this));
           objReg.show();     
    }    
 
 
 private class ButCot extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButCot(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Observación.");
           
        }
        @Override
        public void butCLick() {
           int intCol = tblDat.getSelectedRow();
           String strObs = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_OBS  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_DAT_OBS  ).toString());
          llamarVenObs(strObs, intCol);
        }
    }
 private void llamarVenObs(String strObs, int intCol){
            ZafVeh08_01 obj1 = new  ZafVeh08_01(javax.swing.JOptionPane.getFrameForComponent(this), true , strObs );
            obj1.show();
            if(obj1.getAceptar())
              tblDat.setValueAt( obj1.getObser(), intCol, INT_TBL_DAT_OBS );
           obj1=null; 
     }
}
