/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas.ZafHer25;


import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumnModel;


/**
 *
 * @author Sistemas 5
 */
public class ZafHer25 extends javax.swing.JInternalFrame implements ItemListener{

    /**
     * Creates new form ZafCon64
     */
    
    private static final String PANEL_RESUMEN_SALDOS="Resumen Saldos";
    private static final String PANEL_ANALISIS="Analisis de Egresos e Ingresos";
    private static final String PANEL_GENERA_EGRESOS="Generacion de Egresos";
   
    private javax.swing.JPanel panTabGenResSal;
    private javax.swing.JPanel panTabGenAnlEgrIng;
    private javax.swing.JPanel panResTblEgrIng;   
    private javax.swing.JPanel panTabGenItmEgr;
    //private javax.swing.JPanel panTabGenItmEgr;
    private javax.swing.JPanel panTabGenEgrTrf;
    
    private javax.swing.JScrollPane scrollTbl;
    private javax.swing.JScrollPane scrollTblTot;
    private javax.swing.JScrollPane scrollTblAsientoEgr;
    private javax.swing.JScrollPane scrollTblAsientoIng;
    private javax.swing.JScrollPane scrollTblItmEgr;
    private javax.swing.JScrollPane scrollTblItmIng;
    private javax.swing.JScrollPane scrollTblEgrTrf;
    private JButton btnExeEgr;
    
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDatEgr;
    private javax.swing.JTable tblDatIng;
    

    private JTable tblDatItmEgr;
    private JTable tblDatItmIng;
    
    private JTable tblDatEgrTrf;    
    
    private JTable tblTot;
    private ZafTblTot objTblTot;
    private ZafParSis objZafParSis;
    
    JPanel comboBoxPane = new JPanel(); //use FlowLayout
    String comboBoxItems[] = { PANEL_RESUMEN_SALDOS, PANEL_ANALISIS , PANEL_GENERA_EGRESOS};
    String comboBoxItems2[] = { "AQUI ESTOY", "AQUI NO ESTOY" };
    private JComboBox cmbPrb;
    private JLabel lblEgr;
    JComboBox cboMesCor;
    JComboBox cboAnioCor;
    private JButton butCon;
    private javax.swing.JLabel lblFecDoc;
    private ZafDatePicker txtFecDoc;
    
    
    final int INT_TBL_LINEA=0;
    final int INT_TBL_EMPRESA=1;
    final int INT_TBL_CODCTA=2;
    final int INT_TBL_TXTCTA=3;
    final int INT_TBL_DESCTA=4;
    final int INT_TBL_SALMEN=5;
    final int INT_TBL_SALACU=6;  
    final int INT_TBL_CODEMPRESA=7;
    
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_COD_CTA=1;
    final int INT_TBL_DAT_NUM_CTA=2;
    final int INT_TBL_DAT_NOM_CTA=3;
    final int INT_TBL_DAT_DEB=4;
    final int INT_TBL_DAT_HAB=5;
    final int INT_TBL_DAT_REF=6;             //Referencia.
    final int INT_TBL_DAT_EST_CON=7;        //Estado de conciliación bancaria de la cuenta
    
    final int INT_TBL_ITM_LIN=0;
    final int INT_TBL_ITM_EMP_ORG=1;
    final int INT_TBL_ITM_COD=2;
    final int INT_TBL_ITM_ALT2=3;
    final int INT_TBL_ITM_STK=4;
    final int INT_TBL_ITM_STKDIS=5;
    
    /*final int INT_TBL_ITM_CSTUNI=5;
    final int INT_TBL_ITM_CANT=6;    
    final int INT_TBL_ITM_CSTTOT=7;
    final int INT_TBL_ITM_EMP_DES=8;
    final int INT_TBL_ITM_NOM=9;*/
    
    final int INT_TBL_ITM_CSTUNI=6;
    final int INT_TBL_ITM_CANT=7;    
    
    final int INT_TBL_ITM_FINAL_DIS=8;    
    final int INT_TBL_ITM_FINAL_STK=9;    
    
    /*final int INT_TBL_ITM_CSTTOT=8;
    final int INT_TBL_ITM_EMP_DES=9;
    final int INT_TBL_ITM_NOM=10;*/   
    
    final int INT_TBL_ITM_CSTTOT=10; 
    final int INT_TBL_ITM_EMP_DES=11;
    final int INT_TBL_ITM_NOM=12;   
    
    /*modificado 2017-04-20 para realizar transferencias entre bodegas diferentes de inmaconsa*/
    final int INT_TBL_ITM_BOD=13;
    /*modificado 2017-04-20 para realizar transferencias entre bodegas diferentes de inmaconsa*/
    
    final int INT_TBL_ITM_PROC=14; // PARA VER SI ESTA PROCESADO Y NO VOLVERLO A PROCESAR AGREGADO MAYO 8 DE 2017
    
    
    final int INT_ARL_COD_EMP=0;            // CODIGO DE LA EMPRESA
    final int INT_ARL_COD_LOC=1;            // CODIGO DEL LOCAL
    final int INT_ARL_COD_TIP_DOC=2;        // CODIGO DEL TIPO DE DOCUMENTO
    final int INT_ARL_COD_CLI=3;            // CODIGO DEL CLIENTE/PROVEEDOR
    final int INT_ARL_COD_BOD_ING_EGR=4;    // CODGIO DE LA BODEGA DE INGRESO/EGRESO 
    final int INT_ARL_COD_ITM_GRP=5;        // CODIGO DE ITEM DE GRUPO
    final int INT_ARL_COD_ITM=6;            // CODIGO DEL ITEM POR EMPRESA
    final int INT_ARL_COD_ITM_MAE=7;        // CODIGO DEL ITEM MAESTRO
    final int INT_ARL_COD_ITM_ALT=8;        // CODIGO DEL ITEM ALTERNO 
    final int INT_ARL_NOM_ITM=9;            // NOMBRE DEL ITEM
    final int INT_ARL_UNI_MED_ITM=10;       // UNIDAD DE MEDIDA
    final int INT_ARL_COD_LET_ITM=11;       // CODIGO ALTERNO DEL ITEM 2 (CODIGO DE TRES LETRAS)
    final int INT_ARL_CAN_MOV=12;           // CANTIDAD 
    final int INT_ARL_COS_UNI=13;           // COSTO UNITARIO DEL ITEM
    final int INT_ARL_PRE_UNI=14;           // PRECIO UNITARIO DEL ITEM
    final int INT_ARL_PES_ITM=15;           // PESO DEL ITEM
    final int INT_ARL_IVA_COM_ITM=16;       // ESTADO DEL ITEM IVA
    final int INT_ARL_EST_MER_ING_EGR_FIS_BOD=17;// SI LA MERCADERIA EGRESA FISICAMENTE / SI SE CONFIRMA
    final int INT_ARL_IND_ORG=18;        
    
    final int INT_TBL_EGR_TRF_LINEA=0;
    final int INT_TBL_EGR_TRF_DESTRF=1;
    final int INT_TBL_EGR_TRF_MONTO=2;
    final int INT_TBL_EGR_TRF_EMP_ORG=3;
    final int INT_TBL_EGR_TRF_EMP_DES=4;
    final int INT_TBL_EGR_TRF_BTN_DET=5;
    final int INT_TBL_EGR_TRF_STPROC=6;
    final int INT_TBL_EGR_TRF_ORDEJE=7;
    /*se agrega para poder configurar las bodegas de las que egresa los items*/
    final int INT_TBL_EGR_TRF_CNFBOD=8;
    /*se agrega para poder configurar las bodegas de las que egresa los items*/
    
    
    
    private Vector vecCab = new Vector();    //Almacena las cabeceras  /**/;
    private Vector vecCabAsientoEgr= new Vector();
    private Vector vecCabAsientoIng= new Vector();
    private Vector vecCabItmEgr= new Vector();
    private Vector vecCabItmIngr= new Vector();
    private Vector vecCabEgrTrf=new Vector();
    
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModAsientoEgr;
    private ZafTblMod objTblModAsientoIng;
    private ZafTblMod objTblModItemEgr;
    private ZafTblMod objTblModItemIng;
    private ZafTblMod objTblModEgrTrf;
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private Connection conexion;
    private ZafTblPopMnu objTblPopMnu;                      //PopupMenu: Establecer PeopuMenó en JTable.    
    private ZafTblPopMnu objTblPopMnuItmEgr;     
    private ZafTblPopMnu objTblPopMnuItmIng;   
    
    private Vector vecCta;
    private Vector vecAux;
    private ZafTblFilCab objTblFilCab;
                   //Render: Presentar JLabel en JTable.
    
    private Vector vecSalNegTuval;
    private Vector vecSalPosTuval;
    private Vector vecSalNegCastek;
    private Vector vecSalPosCastek;
    private Vector vecSalNegDimulti;
    private Vector vecSalPosDimulti;
    
    
    //private ArrayList arlConDatPreEmpEgr, arlConDatPreEmpIng;
    private Date datFecAux,dtpFechaDocumento;
    private ZafUtil objUti;
    ZafHer25_MovEgrIng objZafCon64_MovEgrIng;
    
    
    
    BigDecimal bgdSalVal=BigDecimal.ZERO;    
    
    int intRowSelEgrTrf=0;
    List<String> lstProEgrTrf=new ArrayList<String>();
    
    private JTable tblTotItm, tblTotItmIng;
    private ZafTblTot objTblTotItm, objTblTotItmIng;
    private JScrollPane scrollTblTotItm , scrollTblTotItmIng;
    
    private List<ZafBodAgr> lstBodAgr=new ArrayList();

    private String VERSION = " v2.1";
    
    public ZafHer25(){
    }
    
    
    public ZafHer25(ZafParSis obj) {
        try{
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone(); 
            this.setTitle(objZafParSis.getNombreMenu() + VERSION);
            objUti = new ZafUtil(); /**/
            initComponents();
            abrirCon();
            crearPanelResumen();
            crearPanelAnalisis();
            crearPanelItmsEgr();
            crearPanelEgrTrf();
            inicializarCardLayout();
        }catch(CloneNotSupportedException ex){
            ex.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panNor = new javax.swing.JPanel();
        panCard = new javax.swing.JPanel();
        panBotones = new javax.swing.JPanel();
        btnAnt = new javax.swing.JButton();
        btnsgte = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setMinimumSize(new java.awt.Dimension(121, 34));
        setNormalBounds(new java.awt.Rectangle(0, 0, 121, 0));
        setPreferredSize(new java.awt.Dimension(473, 560));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
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

        panNor.setLayout(new java.awt.BorderLayout());
        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        panCard.setLayout(new java.awt.CardLayout());
        getContentPane().add(panCard, java.awt.BorderLayout.CENTER);

        panBotones.setPreferredSize(new java.awt.Dimension(100, 50));

        btnAnt.setText("<<Ant");
        btnAnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAntActionPerformed(evt);
            }
        });
        panBotones.add(btnAnt);

        btnsgte.setText("Sigte >>");
        btnsgte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsgteActionPerformed(evt);
            }
        });
        panBotones.add(btnsgte);

        getContentPane().add(panBotones, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 835, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        configurartabla();
        configurarTblDatAsiEgr();
        configurarTblDatAsiIng();
        configurartablaItemsEgreso();
        configurartablaItemsIngreso();
        configurartablaEgresoTrf();
        
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnsgteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsgteActionPerformed
        JPanel card=null;
        for (Component comp : panCard.getComponents()) {
            if (comp.isVisible() == true) {
                card = (JPanel) comp;
                break;
            }
        }  
        if(card.getName().equals(PANEL_RESUMEN_SALDOS)){
            CardLayout cl = (CardLayout)(panCard.getLayout());
            cl.show(panCard, PANEL_ANALISIS);
            cargarAsientoEgreso();
            cargarAsientoIngreso();                    
        }else if(card.getName().equals(PANEL_ANALISIS)){
            CardLayout cl = (CardLayout)(panCard.getLayout());
            cl.show(panCard, PANEL_GENERA_EGRESOS);   
            cargarEgrTrf();
        }

    }//GEN-LAST:event_btnsgteActionPerformed

    private void btnAntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAntActionPerformed
        JPanel card=null;
        for (Component comp : panCard.getComponents()) {
            if (comp.isVisible() == true) {
                card = (JPanel) comp;
                break;
            }
        }
        if(card.getName().equals(PANEL_GENERA_EGRESOS)){
            CardLayout cl = (CardLayout)(panCard.getLayout());
            cl.show(panCard, PANEL_ANALISIS);
            cargarAsientoEgreso();
            cargarAsientoIngreso();
        }else if(card.getName().equals(PANEL_ANALISIS)){
            CardLayout cl = (CardLayout)(panCard.getLayout());
            cl.show(panCard, PANEL_RESUMEN_SALDOS);
            cargarRegistrosCuentasTransferencias();
        }
    }//GEN-LAST:event_btnAntActionPerformed


    private void inicializarCardLayout(){
        panCard.add(panTabGenResSal, PANEL_RESUMEN_SALDOS);
        panCard.add(panTabGenAnlEgrIng, PANEL_ANALISIS);
        //panCard.add(panTabGenItmEgr, PANEL_GENERA_EGRESOS);
        panCard.add(panTabGenEgrTrf, PANEL_GENERA_EGRESOS);
    }
    
    private void crearPanelResumen(){
        JPanel panBot=new JPanel();
        JPanel panExtBot=new JPanel();
        JPanel panTbl=new JPanel();
        panTbl.setLayout(new BorderLayout());
        JPanel panSup=new JPanel();
        panSup.setLayout(new FlowLayout());
        
        JLabel lblAnio=new JLabel();
        lblAnio.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblAnio.setText("Año:");
        panSup.add(lblAnio);
        lblAnio.setBounds(20, 50, 80, 20);
        cboAnioCor=new JComboBox();
        cboAnioCor.addItem("2016");
        cboAnioCor.addItem("2017");
        cboAnioCor.addItem("2018");
        cboAnioCor.addItem("2019");
        cboAnioCor.setSelectedItem(((Integer)((Calendar.getInstance().get(Calendar.YEAR)))).toString());
        
        panSup.add(cboAnioCor);
                
                
        JLabel lblMes=new JLabel();
        lblMes.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblMes.setText("Mes:");
        panSup.add(lblMes);
        lblMes.setBounds(20, 110, 80, 20);
        
        
        cboMesCor=new JComboBox();
        cboMesCor.addItem("Enero");
        cboMesCor.addItem("Febrero");
        cboMesCor.addItem("Marzo");
        cboMesCor.addItem("Abril");
        cboMesCor.addItem("Mayo");
        cboMesCor.addItem("Junio");
        cboMesCor.addItem("Julio");
        cboMesCor.addItem("Agosto");
        cboMesCor.addItem("Septiembre");
        cboMesCor.addItem("Octubre");
        cboMesCor.addItem("Noviembre");
        cboMesCor.addItem("Diciembre");
        
        cboMesCor.setSelectedIndex(((Integer)((Calendar.getInstance().get(Calendar.MONTH)))));
        
        panSup.add(cboMesCor);
        
        lblFecDoc=new JLabel();
        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecDoc.setText("Fecha del Documento:"); // NOI18N
        panSup.add(lblFecDoc);
        lblFecDoc.setBounds(470, 8, 110, 20);
        
        txtFecDoc = new ZafDatePicker(((JFrame)this.getParent()),"d/m/y");
        txtFecDoc.setPreferredSize(new Dimension(80, 20));
        txtFecDoc.setText("");
        panSup.add(txtFecDoc);
        txtFecDoc.setBounds(580, 8, 92, 25);
        
            Calendar calObj = Calendar.getInstance();
            //calObj.setTime(dateObj);
            txtFecDoc.setText(calObj.get(Calendar.DAY_OF_MONTH), calObj.get(Calendar.MONTH)+1, calObj.get(Calendar.YEAR));

        
        
        panExtBot.setLayout(new BorderLayout());
        
        butCon=new JButton();
        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //butConActionPerformed(evt);
                consultarResumen(evt);
            }
        });
        panBot.add(butCon);
        panExtBot.add(panBot,BorderLayout.EAST);
        
        panTabGenResSal = new JPanel();
        panTabGenResSal.setName(PANEL_RESUMEN_SALDOS);
        scrollTbl = new JScrollPane();
        scrollTblTot= new JScrollPane();
        
        tblDat = new JTable();
        tblTot= new JTable();

        panTabGenResSal.setBorder(new TitledBorder("RESUMEN DE SALDOS"));
        panTabGenResSal.setLayout(new java.awt.BorderLayout());

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
        scrollTbl.setViewportView(tblDat);
        tblDat.setSize(454, 250);
        
        scrollTbl.setPreferredSize(new java.awt.Dimension(454, 180));
        scrollTbl.setSize(454, 200);

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
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
        
        scrollTblTot.setPreferredSize(new java.awt.Dimension(454, 18));
        scrollTblTot.setViewportView(tblTot);
        panTbl.add(scrollTbl, BorderLayout.CENTER);
        panTbl.add(scrollTblTot, BorderLayout.SOUTH);
        
        
        //panTabGenCen.add(cboMesCor, BorderLayout.NORTH);
        panTabGenResSal.add(panSup, BorderLayout.NORTH);
        //panTabGenCen.add(scrollTbl, BorderLayout.CENTER);  
        panTabGenResSal.add(panTbl, BorderLayout.CENTER);  
        panTabGenResSal.add(panExtBot,BorderLayout.SOUTH );
    }
    
    private void consultarResumen(java.awt.event.ActionEvent evt) { 
        ThreadConsultarResumen threadRes=new ThreadConsultarResumen();
        threadRes.start();
    }              
    
    
    private void ejecutarEgresos(java.awt.event.ActionEvent evt) { 
        String strMsg2 = "¿Está Seguro que desea guardar esta transaccion?";
        if (JOptionPane.showConfirmDialog(this, strMsg2, "Mensaje del sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
            System.out.println("guardarInmaconsa ");
            insertarReg();
        }
        
        
    }    
    
    private class ThreadConsultarResumen extends Thread
    {
        @Override
        public void run(){
            cargarRegistrosCuentasTransferencias();        
        }
    }
    
    public void abrirCon() {
        try {
            conexion = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            conexion.setAutoCommit(false);
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }    
    //AQUI
    private void cargarRegistrosCuentasTransferencias(){
        String strSQL="";
        Calendar calAct=Calendar.getInstance();
        String strAnio="";
        try{
               //conexion=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
               String mes=((String.valueOf((cboMesCor.getSelectedIndex()+1)).length())>1)?(String.valueOf((cboMesCor.getSelectedIndex()+1))):("0"+String.valueOf((cboMesCor.getSelectedIndex()+1)));
               strAnio=String.valueOf(cboAnioCor.getSelectedItem());
               if (conexion!=null)
               {
                    strSQL=" select 'TUVAL' as nomEmp, null as co_emp, null as co_cta, null as ne_niv,null as ne_pad, null as tx_codCta,null as tx_desLar, null as tx_tipCta,null as nd_salMen, null as nd_salAcu"+
                    
                    " UNION ALL\n"+

                   " SELECT null,a1.co_emp,a1.co_cta, a1.ne_niv, a1.ne_pad, \n"+ 
                   " a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, \n"+
                   " a2.nd_salCta AS nd_salMen, a3.nd_salCta AS nd_salAcu \n"+
                   " FROM tbm_plaCta AS a1 \n"+
                   " INNER JOIN tbm_salCta as a2 \n"+
                   " ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta) \n"+
                   " INNER JOIN ( "+
                            " SELECT b1.co_emp, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta \n"+
                            " FROM tbm_salCta AS b1 \n"+
                            " WHERE b1.co_emp=1 \n"+
                            //" AND b1.co_per<="+calAct.get(Calendar.YEAR) + mes+" \n"+
                            " AND b1.co_per<="+strAnio+ mes+" \n"+
                            " GROUP BY b1.co_emp, b1.co_cta \n"+
                    " ) AS a3 \n"+
                    " ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta) \n"+
                    " WHERE a1.co_emp=1 \n"+
                    //" AND a2.co_per="+calAct.get(Calendar.YEAR) + mes+
                    " AND a2.co_per="+strAnio+ mes+
                    " and a1.tx_codcta in ('1.01.03.01.88', '1.01.03.01.89')"+
                    " AND a1.ne_niv<=7"+
                    " AND a1.tx_niv1 IN ('1', '2', '3')"+
                    " AND a1.st_reg='A'"+
                    " AND (a2.nd_salCta<>0 OR a3.nd_salCta<>0)"+
                    //" ORDER BY a1.tx_codCta"+
                            
                    " UNION ALL \n"+

                    " select 'CASTEK' as nomEmp,null as co_emp, null as co_cta, null as ne_niv,null as ne_pad, null as tx_codCta,null as tx_desLar, null as tx_tipCta,null as nd_salMen, null as nd_salAcu"+
                    
                    " UNION ALL \n"+
                    
                    " SELECT null,a1.co_emp,a1.co_cta, a1.ne_niv, a1.ne_pad, \n"+ 
                    " a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, \n"+
                    " a2.nd_salCta AS nd_salMen, a3.nd_salCta AS nd_salAcu \n"+
                    " FROM tbm_plaCta AS a1 \n"+
                    " INNER JOIN tbm_salCta as a2 \n"+
                    " ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta) \n"+
                    " INNER JOIN ( "+
                            " SELECT b1.co_emp, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta \n"+
                            " FROM tbm_salCta AS b1 \n"+
                            " WHERE b1.co_emp=2 \n"+
                            //" AND b1.co_per<="+calAct.get(Calendar.YEAR) + mes+" \n"+
                            " AND b1.co_per<="+strAnio+ mes+" \n"+
                            " GROUP BY b1.co_emp, b1.co_cta \n"+
                    " ) AS a3 \n"+
                    " ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta) \n"+
                    " WHERE a1.co_emp=2 \n"+
                    //" AND a2.co_per="+calAct.get(Calendar.YEAR) + mes+
                    " AND a2.co_per="+strAnio+ mes+
                    " and a1.tx_codcta in ('1.01.03.01.88', '1.01.03.01.98')"+
                    " AND a1.ne_niv<=7"+
                    " AND a1.tx_niv1 IN ('1', '2', '3')"+
                    " AND a1.st_reg='A'"+
                    " AND (a2.nd_salCta<>0 OR a3.nd_salCta<>0)"+
                    //" ORDER BY a1.tx_codCta"+  
                            
                    " UNION ALL\n"+
                            
                    " select 'DIMULTI' as nomEmp, null as co_emp, null as co_cta, null as ne_niv,null as ne_pad, null as tx_codCta,null as tx_desLar, null as tx_tipCta,null as nd_salMen, null as nd_salAcu"+
                    
                    " UNION ALL \n"+                            
                    
                    " SELECT null as nomEmp,a1.co_emp, a1.co_cta, a1.ne_niv, a1.ne_pad, \n"+ 
                    " a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, \n"+
                    " a2.nd_salCta AS nd_salMen, a3.nd_salCta AS nd_salAcu \n"+
                    " FROM tbm_plaCta AS a1 \n"+
                    " INNER JOIN tbm_salCta as a2 \n"+
                    " ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta) \n"+
                    " INNER JOIN ( "+
                            " SELECT b1.co_emp, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta \n"+
                            " FROM tbm_salCta AS b1 \n"+
                            " WHERE b1.co_emp=4 \n"+
                            //" AND b1.co_per<="+calAct.get(Calendar.YEAR) + mes+" \n"+
                            " AND b1.co_per<="+strAnio+ mes+" \n"+
                            " GROUP BY b1.co_emp, b1.co_cta \n"+
                    " ) AS a3 \n"+
                    " ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta) \n"+
                    " WHERE a1.co_emp=4 \n"+
                    //" AND a2.co_per="+calAct.get(Calendar.YEAR) + mes+
                    " AND a2.co_per="+strAnio+ mes+
                    " and a1.tx_codcta in ('1.01.03.01.89', '1.01.03.01.98')"+
                    " AND a1.ne_niv<=7"+
                    " AND a1.tx_niv1 IN ('1', '2', '3')"+
                    " AND a1.st_reg='A'"+
                    " AND (a2.nd_salCta<>0 OR a3.nd_salCta<>0)";
                    //" ORDER BY a1.tx_codCta";                             
                            
//                    System.out.println("String "+strSQL);
//                    System.out.println("Anio "+calAct.get(Calendar.YEAR));
//                    System.out.println("Mes "+String.valueOf((cboMesCor.getSelectedIndex()+1)));
                    java.sql.Statement stm = conexion.createStatement();
//                    System.out.println("permisos: " + strSQL);
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);
                    Vector vecData = new Vector();
                    while(rst.next()){
                       Vector vecReg = new Vector();
                       vecReg.add(INT_TBL_LINEA, "");
                       vecReg.add(INT_TBL_EMPRESA,"<html><b>"+(rst.getString("nomEmp")!=null?rst.getString("nomEmp"):"") +"<b></html>");
                       vecReg.add(INT_TBL_CODCTA, (rst.getInt("co_cta")==0?"":rst.getInt("co_cta")));
                       vecReg.add(INT_TBL_TXTCTA, rst.getString("tx_codCta"));
                       vecReg.add(INT_TBL_DESCTA, rst.getString("tx_desLar"));
                       vecReg.add(INT_TBL_SALMEN, rst.getBigDecimal("nd_salMen"));
                       vecReg.add(INT_TBL_SALACU, rst.getBigDecimal("nd_salAcu"));
                       vecReg.add(INT_TBL_CODEMPRESA, rst.getInt("co_emp"));
                       vecData.add(vecReg);
                    }
                    objTblMod.setData(vecData);
                    tblDat.setModel(objTblMod);
                    objTblTot.calcularTotales();
               }
        }catch(SQLException ex){
               ex.printStackTrace();                
        }
    }
    
    private void crearPanelAnalisis(){
        panTabGenAnlEgrIng = new javax.swing.JPanel();
        panTabGenAnlEgrIng.setName(PANEL_ANALISIS);
        panTabGenAnlEgrIng.setLayout(new java.awt.BorderLayout());
        panTabGenAnlEgrIng.setBorder(new TitledBorder("ANALISIS DE EGRESOS E INGRESOS"));
        panResTblEgrIng= new JPanel();
        panResTblEgrIng.setLayout(new GridLayout(1, 2));
        scrollTblAsientoEgr = new javax.swing.JScrollPane();
        tblDatEgr = new javax.swing.JTable(){
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        
        cmbPrb=new JComboBox(comboBoxItems2);
        tblDatEgr.setModel(new javax.swing.table.DefaultTableModel(
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
        scrollTblAsientoEgr.setViewportView(tblDatEgr);
        scrollTblAsientoEgr.setPreferredSize(new java.awt.Dimension(454, 180));
        scrollTblAsientoEgr.setSize(454, 200);
        panResTblEgrIng.add(scrollTblAsientoEgr);
        //panTabGenRes.add(cmbPrb, java.awt.BorderLayout.CENTER);    
        //panTabGenRes.add(scrollTblAsientoEgr, java.awt.BorderLayout.CENTER);  
        
        
        scrollTblAsientoIng = new javax.swing.JScrollPane();
        tblDatIng = new javax.swing.JTable(){
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        
        tblDatIng.setModel(new javax.swing.table.DefaultTableModel(
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
        scrollTblAsientoIng.setViewportView(tblDatIng);
        scrollTblAsientoIng.setPreferredSize(new java.awt.Dimension(454, 180));
        scrollTblAsientoIng.setSize(454, 200);
        panResTblEgrIng.add(scrollTblAsientoIng);
        //panTabGenRes.add(cmbPrb, java.awt.BorderLayout.CENTER);    
        panTabGenAnlEgrIng.add(panResTblEgrIng, java.awt.BorderLayout.CENTER);         
        
    } 
    
    private void crearPanelItmsEgr(){
        JPanel panCen=new JPanel();
        JPanel panCenBot=new JPanel();
        JPanel panSouBoton=new JPanel();
        panCen.setLayout(new FlowLayout());
        panCenBot.setLayout(new FlowLayout());
        panSouBoton.setLayout(new FlowLayout());
        panTabGenItmEgr = new javax.swing.JPanel();
        panTabGenItmEgr.setLayout(new java.awt.BorderLayout());
        JPanel panBot=new JPanel();
        panBot.setLayout(new BorderLayout());
        
        //panResTbl= new JPanel();
        //panResTbl.setLayout(new GridLayout(1, 2));
        scrollTblItmEgr = new javax.swing.JScrollPane();
        scrollTblItmEgr.setBorder(new TitledBorder("ITEMS DE EGRESAR"));
        tblDatItmEgr = new JTable();
        tblDatItmEgr.setModel(new javax.swing.table.DefaultTableModel(
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
        scrollTblItmEgr.setViewportView(tblDatItmEgr);
        scrollTblItmEgr.setPreferredSize(new java.awt.Dimension(400, 180));
        scrollTblItmEgr.setSize(400, 180);
        btnExeEgr=new JButton("Ejecutar");
        btnExeEgr.setPreferredSize(new java.awt.Dimension(90, 20));
        btnExeEgr.setSize(90, 20);
        btnExeEgr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ejecutarEgresos(evt);
            }
        });


        tblDatItmIng = new JTable();
        tblDatItmIng.setModel(new javax.swing.table.DefaultTableModel(
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
        
       scrollTblItmIng=new JScrollPane();
       scrollTblItmIng.setBorder(new TitledBorder("ITEMS DE INGRESAR"));
       scrollTblItmIng.setViewportView(tblDatItmIng);
       scrollTblItmIng.setPreferredSize(new java.awt.Dimension(400, 180));
       scrollTblItmIng.setSize(400,180);
       

        tblTotItm= new JTable();  
        tblTotItm.setModel(new javax.swing.table.DefaultTableModel(
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
        scrollTblTotItm=new JScrollPane();
        scrollTblTotItm.setViewportView(tblTotItm);
        //scrollTblTotItm.setPreferredSize(new java.awt.Dimension(454, 200));
        scrollTblTotItm.setPreferredSize(new java.awt.Dimension(400, 30));
        scrollTblTotItm.setSize(400, 30);
        
        tblTotItmIng= new JTable();  
        tblTotItmIng.setModel(new javax.swing.table.DefaultTableModel(
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
        scrollTblTotItmIng=new JScrollPane();
        scrollTblTotItmIng.setViewportView(tblTotItmIng);
        //scrollTblTotItm.setPreferredSize(new java.awt.Dimension(454, 200));
        scrollTblTotItmIng.setPreferredSize(new java.awt.Dimension(400, 30));
        scrollTblTotItmIng.setSize(400, 30);        
        
        panCenBot.add(scrollTblTotItm);
        panCenBot.add(scrollTblTotItmIng);
                
        panCen.add(scrollTblItmEgr);
        panCen.add(scrollTblItmIng);
        
        //panTabGenItmEgr.add(scrollTblItmEgr);
        panTabGenItmEgr.add(panCen,BorderLayout.CENTER);
        
        panBot.add(panCenBot, BorderLayout.CENTER);
        panSouBoton.add(btnExeEgr);
        panBot.add(panSouBoton, BorderLayout.SOUTH);
        //panTabGenItmEgr.add(btnExeEgr, BorderLayout.SOUTH);
        panTabGenItmEgr.add(panBot, BorderLayout.SOUTH);

        //panTabGenRes.add(cmbPrb, java.awt.BorderLayout.CENTER);    
        //panTabGenAnlEgrIng.add(panResTbl, java.awt.BorderLayout.CENTER);         
        
    }     
    

    private void crearPanelEgrTrf(){
        
        panTabGenEgrTrf = new javax.swing.JPanel();
        panTabGenEgrTrf.setName(PANEL_GENERA_EGRESOS);
        panTabGenEgrTrf.setLayout(new java.awt.BorderLayout());
        panTabGenEgrTrf.setBorder(new TitledBorder("GENERACION DE EGRESOS E INGRESOS"));
        //panResTbl= new JPanel();
        //panResTbl.setLayout(new GridLayout(1, 2));
        scrollTblEgrTrf = new javax.swing.JScrollPane();
        //scrollTblEgrTrf.setBorder(new TitledBorder("ITEMS DE EGRESAR"));
        tblDatEgrTrf = new JTable();
        tblDatEgrTrf.setModel(new javax.swing.table.DefaultTableModel(
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
        scrollTblEgrTrf.setViewportView(tblDatEgrTrf);
        scrollTblEgrTrf.setPreferredSize(new java.awt.Dimension(454, 95));
        scrollTblEgrTrf.setSize(454, 95);
        panTabGenEgrTrf.add(scrollTblEgrTrf, BorderLayout.NORTH);
        panTabGenEgrTrf.add(panTabGenItmEgr, BorderLayout.CENTER);
    }    
    
    
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(panCard.getLayout());
        if(((String)evt.getItem()).equals(PANEL_ANALISIS)){
            cargarAsientoEgreso();
            cargarAsientoIngreso();
        }else if(((String)evt.getItem()).equals(PANEL_GENERA_EGRESOS)){
            cargarEgrTrf();
        }
        cl.show(panCard, (String)evt.getItem());
    }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnt;
    private javax.swing.JButton btnsgte;
    private javax.swing.JPanel panBotones;
    private javax.swing.JPanel panCard;
    private javax.swing.JPanel panNor;
    // End of variables declaration//GEN-END:variables

    
    private boolean configurartabla() {
        boolean blnRes = false;
        try {
            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_EMPRESA,"Empresa");
            vecCab.add(INT_TBL_CODCTA, "Cod.Cuenta.");
            vecCab.add(INT_TBL_TXTCTA, "Cuenta Contable.");
            vecCab.add(INT_TBL_DESCTA, "Nom. Cuenta.");
            vecCab.add(INT_TBL_SALMEN, "Sal. Mensual.");
            vecCab.add(INT_TBL_SALACU, "Sal. Acumulado.");
            vecCab.add(INT_TBL_CODEMPRESA,"Cod. empresa");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat, INT_TBL_LINEA);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  /**/
            TableColumnModel tcmAux = tblDat.getColumnModel();  /**/
            objTblMod.setColumnDataType(INT_TBL_EMPRESA, ZafTblMod.INT_COL_STR, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CODCTA, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_TXTCTA, ZafTblMod.INT_COL_STR, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DESCTA, ZafTblMod.INT_COL_STR, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_SALMEN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_SALACU, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CODEMPRESA, ZafTblMod.INT_COL_INT,  new Integer(0), null);

            ZafTblCelRenLbl objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_SALMEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_SALACU).setCellRenderer(objTblCelRenLbl);


            objTblCelRenLbl = null;


            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_EMPRESA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CODCTA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_TXTCTA).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DESCTA).setPreferredWidth(250);
            tcmAux.getColumn(INT_TBL_SALMEN).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_SALACU).setPreferredWidth(100);
            
            tcmAux.getColumn(INT_TBL_CODEMPRESA).setWidth(0);
            tcmAux.getColumn(INT_TBL_CODEMPRESA).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_CODEMPRESA).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_CODEMPRESA).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_CODEMPRESA).setResizable(false);

            tblDat.getTableHeader().setReorderingAllowed(false);
            
            objTblOrd=new ZafTblOrd(tblDat);
            objTblBus=new ZafTblBus(tblDat);
            int intCol[] = {INT_TBL_SALMEN, INT_TBL_SALACU};
            objTblTot = new ZafTblTot(scrollTbl, scrollTblTot, tblDat, tblTot, intCol);        
            
            
        }catch(Exception ex ){
           ex.printStackTrace();
        }
        blnRes = true;
        return blnRes;
    }
    
    private boolean configurartablaItemsEgreso() {
        boolean blnRes = false;
        try {
            //Configurar JTable: Establecer el modelo.
            vecCabItmEgr.clear();
            vecCabItmEgr.add(INT_TBL_ITM_LIN, "");
            vecCabItmEgr.add(INT_TBL_ITM_EMP_ORG, "Empresa Origen");
            vecCabItmEgr.add(INT_TBL_ITM_COD, "Codigo Item");
            vecCabItmEgr.add(INT_TBL_ITM_ALT2,"Codigo Alterno");
            vecCabItmEgr.add(INT_TBL_ITM_STK, "Stock.");
            vecCabItmEgr.add(INT_TBL_ITM_STKDIS, "Disponible.");
            vecCabItmEgr.add(INT_TBL_ITM_CSTUNI, "Costo Unitario.");
            vecCabItmEgr.add(INT_TBL_ITM_CANT, "Cantidad.");
            vecCabItmEgr.add(INT_TBL_ITM_FINAL_DIS,"Cant.Dsp.Final");
            vecCabItmEgr.add(INT_TBL_ITM_FINAL_STK,"Cant.Stk.Final");
            vecCabItmEgr.add(INT_TBL_ITM_CSTTOT, "Costo Total.");
            vecCabItmEgr.add(INT_TBL_ITM_EMP_DES, "Empresa Destino.");
            vecCabItmEgr.add(INT_TBL_ITM_NOM, "Nombre Item.");
            
            /*modificado 2017-04-20 para realizar transferencias diferentes bodegas de inmaconsa*/
            vecCabItmEgr.add(INT_TBL_ITM_BOD,"Cod. Bodega");
            /*modificado 2017-04-20 para realizar transferencias diferentes bodegas de inmaconsa*/
            
            /*Agregado para evitar procesar los mismos registros 2017-may-08*/
            vecCabItmEgr.add(INT_TBL_ITM_PROC,"REGISTRO PROCESADO");
            /*Agregado para evitar procesar los mismos registros 2017-may-08*/

            objTblModItemEgr = new ZafTblMod();
            objTblModItemEgr.setHeader(vecCabItmEgr);
            tblDatItmEgr.setModel(objTblModItemEgr);

            //Configurar JTable: Establecer tipo de selección.
            //tblDatItmEgr.setRowSelectionAllowed(true);
            //tblDatItmEgr.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            tblDatItmEgr.setCellSelectionEnabled(true);
            tblDatItmEgr.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); 
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDatItmEgr, INT_TBL_LINEA);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDatItmEgr.getTableHeader().addMouseMotionListener(objMouMotAda);


            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblModItemEgr.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatItmEgr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  /**/
            TableColumnModel tcmAux = tblDatItmEgr.getColumnModel();  /**/
            
            objTblModItemEgr.setColumnDataType(INT_TBL_ITM_EMP_ORG, ZafTblMod.INT_COL_INT, new Integer(0), null);
            objTblModItemEgr.setColumnDataType(INT_TBL_ITM_COD, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModItemEgr.setColumnDataType(INT_TBL_ITM_ALT2, ZafTblMod.INT_COL_STR, new Integer(0), null);
            objTblModItemEgr.setColumnDataType(INT_TBL_ITM_STK, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModItemEgr.setColumnDataType(INT_TBL_ITM_STKDIS, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModItemEgr.setColumnDataType(INT_TBL_ITM_CSTUNI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModItemEgr.setColumnDataType(INT_TBL_ITM_CANT, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModItemEgr.setColumnDataType(INT_TBL_ITM_CSTTOT, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModItemEgr.setColumnDataType(INT_TBL_ITM_EMP_DES, ZafTblMod.INT_COL_INT, new Integer(0), null);
            objTblModItemEgr.setColumnDataType(INT_TBL_ITM_NOM, ZafTblMod.INT_COL_STR, new Integer(0), null);
            objTblModItemEgr.setColumnDataType(INT_TBL_ITM_FINAL_DIS, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModItemEgr.setColumnDataType(INT_TBL_ITM_FINAL_STK, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            /*modificado 2017-04-20 para realizar transferencias diferentes bodegas de inmaconsa*/            
            objTblModItemEgr.setColumnDataType(INT_TBL_ITM_BOD, ZafTblMod.INT_COL_INT, new Integer(0), null);
            /*modificado 2017-04-20 para realizar transferencias diferentes bodegas de inmaconsa*/     
            
            /*modificado 2017-May-08 para controlar si un registro ha sido procesado*/
            objTblModItemEgr.setColumnDataType(INT_TBL_ITM_PROC, ZafTblMod.INT_COL_STR, new Integer(0), null);
            /*modificado 2017-May-08 para controlar si un registro ha sido procesado*/
            
            
            ZafTblCelRenLbl objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            
            tcmAux.getColumn(INT_TBL_ITM_CSTTOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_ITM_STK).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_ITM_STKDIS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_ITM_CANT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_ITM_FINAL_DIS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_ITM_FINAL_STK).setCellRenderer(objTblCelRenLbl);
            
            objTblCelRenLbl = null;
            
            ZafTblCelRenLbl objTblCelRenLbl2 = new ZafTblCelRenLbl();
            objTblCelRenLbl2.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl2.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl2.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl2.setFormatoNumerico("###,###,##0.0000000",false,true);
            tcmAux.getColumn(INT_TBL_ITM_CSTUNI).setCellRenderer(objTblCelRenLbl2);
            
            
            objTblCelRenLbl2=null;


            //Tamaño de las celdas
            
            tcmAux.getColumn(INT_TBL_ITM_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_ITM_ALT2).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_ITM_STK).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_ITM_STKDIS).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_ITM_CSTUNI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_ITM_CANT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_ITM_CSTTOT).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_ITM_NOM).setPreferredWidth(200);            
            tcmAux.getColumn(INT_TBL_ITM_FINAL_DIS).setPreferredWidth(80);  
            tcmAux.getColumn(INT_TBL_ITM_FINAL_STK).setPreferredWidth(80);  

           
            tcmAux.getColumn(INT_TBL_ITM_EMP_ORG).setWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_EMP_ORG).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_EMP_ORG).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_EMP_ORG).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_EMP_ORG).setResizable(false);
            
            
            tcmAux.getColumn(INT_TBL_ITM_PROC).setWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_PROC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_PROC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_PROC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_PROC).setResizable(false);


            tcmAux.getColumn(INT_TBL_ITM_EMP_DES).setWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_EMP_DES).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_EMP_DES).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_EMP_DES).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_EMP_DES).setResizable(false);
            
            /*modificado 2017-04-20 para realizar transferencias diferentes bodegas de inmaconsa*/            
            tcmAux.getColumn(INT_TBL_ITM_BOD).setWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_BOD).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_BOD).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_BOD).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_BOD).setResizable(false);
            /*modificado 2017-04-20 para realizar transferencias diferentes bodegas de inmaconsa*/            

            
            tblDatItmEgr.getTableHeader().setReorderingAllowed(false);
            objTblPopMnuItmEgr=new ZafTblPopMnu(tblDatItmEgr);
            
            //objTblOrd=new ZafTblOrd(tblDatItmEgr);
            //objTblBus=new ZafTblBus(tblDatItmEgr);
            //int intCol[] = {INT_TBL_SALMEN, INT_TBL_SALACU};
            //objTblTot = new ZafTblTot(scrollTbl, scrollTblTot, tblDat, tblTot, intCol);    
            
            int intCol[] = {INT_TBL_ITM_CSTTOT};
            objTblTotItm = new ZafTblTot(scrollTblItmEgr, scrollTblTotItm, tblDatItmEgr, tblTotItm, intCol); 
            


            
            
        }catch(Exception ex ){
           ex.printStackTrace();
        }
        blnRes = true;
        return blnRes;
    }    
    
    private boolean configurartablaItemsIngreso() {
        boolean blnRes = false;
        try {
            //Configurar JTable: Establecer el modelo.
            vecCabItmIngr.clear();
            vecCabItmIngr.add(INT_TBL_ITM_LIN, "");
            vecCabItmIngr.add(INT_TBL_ITM_EMP_ORG, "Empresa Origen");
            vecCabItmIngr.add(INT_TBL_ITM_COD, "Codigo Item");
            vecCabItmIngr.add(INT_TBL_ITM_ALT2,"Codigo Alterno");
            vecCabItmIngr.add(INT_TBL_ITM_STK, "Stock.");
            vecCabItmIngr.add(INT_TBL_ITM_STKDIS, "Disponible.");
            vecCabItmIngr.add(INT_TBL_ITM_CSTUNI, "Costo Unitario.");
            vecCabItmIngr.add(INT_TBL_ITM_CANT, "Cantidad.");
            vecCabItmIngr.add(INT_TBL_ITM_FINAL_DIS,"Cant. Dsp. Final");
            vecCabItmIngr.add(INT_TBL_ITM_FINAL_STK,"Cant. Stk. Final");            
            vecCabItmIngr.add(INT_TBL_ITM_CSTTOT, "Costo Total.");
            vecCabItmIngr.add(INT_TBL_ITM_EMP_DES, "Empresa Destino.");
            vecCabItmIngr.add(INT_TBL_ITM_NOM, "Nombre Item.");
            
            /*modificado 2017-04-20 para realizar transferencias diferentes bodegas de inmaconsa*/            
            vecCabItmIngr.add(INT_TBL_ITM_BOD, "Bodega.");
            /*modificado 2017-04-20 para realizar transferencias diferentes bodegas de inmaconsa*/            
            
            objTblModItemIng = new ZafTblMod();
            objTblModItemIng.setHeader(vecCabItmIngr);
            tblDatItmIng.setModel(objTblModItemIng);

            //Configurar JTable: Establecer tipo de selección.
            //tblDatItmIng.setRowSelectionAllowed(true);
            //tblDatItmIng.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            tblDatItmIng.setCellSelectionEnabled(true);
            tblDatItmIng.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);             
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDatItmIng, INT_TBL_LINEA);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDatItmIng.getTableHeader().addMouseMotionListener(objMouMotAda);


            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblModItemIng.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatItmIng.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  /**/
            TableColumnModel tcmAux = tblDatItmIng.getColumnModel();  /**/
            
            objTblModItemIng.setColumnDataType(INT_TBL_ITM_EMP_ORG, ZafTblMod.INT_COL_INT, new Integer(0), null);
            objTblModItemIng.setColumnDataType(INT_TBL_ITM_COD, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModItemIng.setColumnDataType(INT_TBL_ITM_ALT2, ZafTblMod.INT_COL_STR, new Integer(0), null);
            objTblModItemIng.setColumnDataType(INT_TBL_ITM_STK, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModItemIng.setColumnDataType(INT_TBL_ITM_STKDIS, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModItemIng.setColumnDataType(INT_TBL_ITM_CSTUNI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModItemIng.setColumnDataType(INT_TBL_ITM_CANT, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModItemIng.setColumnDataType(INT_TBL_ITM_CSTTOT, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModItemIng.setColumnDataType(INT_TBL_ITM_EMP_DES, ZafTblMod.INT_COL_INT, new Integer(0), null);
            objTblModItemIng.setColumnDataType(INT_TBL_ITM_NOM, ZafTblMod.INT_COL_STR, new Integer(0), null);
            objTblModItemIng.setColumnDataType(INT_TBL_ITM_FINAL_DIS, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModItemIng.setColumnDataType(INT_TBL_ITM_FINAL_STK, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            
            /*modificado 2017-04-20 para realizar transferencias diferentes bodegas de inmaconsa*/ 
            objTblModItemIng.setColumnDataType(INT_TBL_ITM_BOD, ZafTblMod.INT_COL_INT, new Integer(0), null);
            /*modificado 2017-04-20 para realizar transferencias diferentes bodegas de inmaconsa*/            

            ZafTblCelRenLbl objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            
            tcmAux.getColumn(INT_TBL_ITM_CSTTOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_ITM_STK).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_ITM_STKDIS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_ITM_CANT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_ITM_FINAL_DIS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_ITM_FINAL_STK).setCellRenderer(objTblCelRenLbl);
            
            objTblCelRenLbl = null;
            
            ZafTblCelRenLbl objTblCelRenLbl2 = new ZafTblCelRenLbl();
            objTblCelRenLbl2.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl2.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl2.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl2.setFormatoNumerico("###,###,##0.0000000",false,true);
            tcmAux.getColumn(INT_TBL_ITM_CSTUNI).setCellRenderer(objTblCelRenLbl2);
            
            
            objTblCelRenLbl2=null;


            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_ITM_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_ITM_ALT2).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_ITM_STK).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_ITM_STKDIS).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_ITM_CSTUNI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_ITM_CANT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_ITM_CSTTOT).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_ITM_NOM).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_ITM_FINAL_DIS).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_ITM_FINAL_STK).setPreferredWidth(80);

            
            /*tcmAux.getColumn(INT_TBL_ITM_LIN).setWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_LIN).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_LIN).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_LIN).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_LIN).setResizable(false);*/
            
            tcmAux.getColumn(INT_TBL_ITM_EMP_ORG).setWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_EMP_ORG).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_EMP_ORG).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_EMP_ORG).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_EMP_ORG).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_ITM_EMP_DES).setWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_EMP_DES).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_EMP_DES).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_EMP_DES).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_EMP_DES).setResizable(false);
            
            /*modificado 2017-04-20 para realizar transferencias diferentes bodegas de inmaconsa*/ 
            
            tcmAux.getColumn(INT_TBL_ITM_BOD).setWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_BOD).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_BOD).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_BOD).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_ITM_BOD).setResizable(false);
            
            /*modificado 2017-04-20 para realizar transferencias diferentes bodegas de inmaconsa*/ 

            
            tblDatItmIng.getTableHeader().setReorderingAllowed(false);
            
            
            objTblPopMnuItmIng=new ZafTblPopMnu(tblDatItmIng);
            
            //objTblOrd=new ZafTblOrd(tblDatItmEgr);
            //objTblBus=new ZafTblBus(tblDatItmEgr);
            //int intCol[] = {INT_TBL_SALMEN, INT_TBL_SALACU};
            //objTblTot = new ZafTblTot(scrollTbl, scrollTblTot, tblDat, tblTot, intCol);     
            
            int intCol2[] = {INT_TBL_ITM_CSTTOT};
            objTblTotItmIng = new ZafTblTot(scrollTblItmIng, scrollTblTotItmIng, tblDatItmIng, tblTotItmIng, intCol2); 
            
            
            
        }catch(Exception ex ){
           ex.printStackTrace();
        }
        blnRes = true;
        return blnRes;
    }    
    

    private boolean configurartablaEgresoTrf() {
        boolean blnRes = false;
        try {
            //Configurar JTable: Establecer el modelo.
            
             
            vecCabEgrTrf.clear();
            vecCabEgrTrf.add(INT_TBL_EGR_TRF_LINEA, "");
            vecCabEgrTrf.add(INT_TBL_EGR_TRF_DESTRF, "Desc. Transferencia");
            vecCabEgrTrf.add(INT_TBL_EGR_TRF_MONTO, "Monto");
            vecCabEgrTrf.add(INT_TBL_EGR_TRF_EMP_ORG,"Empresa Origen");
            vecCabEgrTrf.add(INT_TBL_EGR_TRF_EMP_DES, "Empresa Destino");
            vecCabEgrTrf.add(INT_TBL_EGR_TRF_BTN_DET,"...");
            vecCabEgrTrf.add(INT_TBL_EGR_TRF_STPROC,"Procesado");
            vecCabEgrTrf.add(INT_TBL_EGR_TRF_ORDEJE,"Orden Ejec.");
            
            vecCabEgrTrf.add(INT_TBL_EGR_TRF_CNFBOD, "Conf. Bodega");
                    
            //vecCabEgrTrf.add(INT_TBL_EGR_TRF_BTN_DET, "Costo Unitario.");

            objTblModEgrTrf = new ZafTblMod();
            objTblModEgrTrf.setHeader(vecCabEgrTrf);
            tblDatEgrTrf.setModel(objTblModEgrTrf);

            //Configurar JTable: Establecer tipo de selección.
            tblDatEgrTrf.setRowSelectionAllowed(true);
            tblDatEgrTrf.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDatEgrTrf, INT_TBL_LINEA);
            
            tblDatEgrTrf.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  /**/
            TableColumnModel tcmAux = tblDatEgrTrf.getColumnModel();  /**/


            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDatEgrTrf.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_EGR_TRF_BTN_DET);
            vecAux.add("" + INT_TBL_EGR_TRF_DESTRF);
            vecAux.add("" + INT_TBL_EGR_TRF_CNFBOD);

            objTblModEgrTrf.setColumnasEditables(vecAux);
            vecAux=null;
           
            objTblModEgrTrf.setColumnDataType(INT_TBL_EGR_TRF_DESTRF, ZafTblMod.INT_COL_STR, new Integer(0), null);
            objTblModEgrTrf.setColumnDataType(INT_TBL_EGR_TRF_MONTO, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModEgrTrf.setColumnDataType(INT_TBL_EGR_TRF_EMP_ORG, ZafTblMod.INT_COL_INT, new Integer(0), null);
            objTblModEgrTrf.setColumnDataType(INT_TBL_EGR_TRF_EMP_DES, ZafTblMod.INT_COL_INT, new Integer(0), null);
            objTblModEgrTrf.setColumnDataType(INT_TBL_EGR_TRF_STPROC, ZafTblMod.INT_COL_STR, new Integer(0), null);
            objTblModEgrTrf.setColumnDataType(INT_TBL_EGR_TRF_ORDEJE, ZafTblMod.INT_COL_INT, new Integer(0), null);
            
            ZafTblCelRenBut objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_EGR_TRF_BTN_DET).setCellRenderer(objTblCelRenBut);
            
            ZafTblCelRenBut objTblCelRenBut2=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_EGR_TRF_CNFBOD).setCellRenderer(objTblCelRenBut2);
            
            
            ZafTblCelRenLbl objTblCelRenLbl = new ZafTblCelRenLbl();
            //objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_EGR_TRF_MONTO).setCellRenderer(objTblCelRenLbl);


            ZafTblCelEdiButGen objTblCelEdiButGenConfBod=new ZafTblCelEdiButGen();
            tblDatEgrTrf.getColumnModel().getColumn(INT_TBL_EGR_TRF_CNFBOD).setCellEditor(objTblCelEdiButGenConfBod);
            objTblCelEdiButGenConfBod.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    tblDatEgrTrf.getValueAt(tblDatEgrTrf.getSelectedRow(),INT_TBL_EGR_TRF_MONTO);
                    int intEmpOri=(Integer)tblDatEgrTrf.getValueAt(tblDatEgrTrf.getSelectedRow(),INT_TBL_EGR_TRF_EMP_ORG);
                    int intEmpDes=(Integer)tblDatEgrTrf.getValueAt(tblDatEgrTrf.getSelectedRow(), INT_TBL_EGR_TRF_EMP_DES);
                    intRowSelEgrTrf=tblDatEgrTrf.getSelectedRow();                    
                    BigDecimal bgdValTra= (BigDecimal)tblDatEgrTrf.getValueAt(tblDatEgrTrf.getSelectedRow(), INT_TBL_EGR_TRF_MONTO);
                    mostrarVentanBodega(intEmpOri, bgdValTra, intEmpDes);
                }
            });
            
            
            ZafTblCelEdiButGen objTblCelEdiButGenCxP01=new ZafTblCelEdiButGen();
            tblDatEgrTrf.getColumnModel().getColumn(INT_TBL_EGR_TRF_BTN_DET).setCellEditor(objTblCelEdiButGenCxP01);
            objTblCelEdiButGenCxP01.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                        int intEmpOrg=(Integer)tblDatEgrTrf.getValueAt(tblDatEgrTrf.getSelectedRow(), INT_TBL_EGR_TRF_EMP_ORG);
                        int intEmpDes=(Integer)tblDatEgrTrf.getValueAt(tblDatEgrTrf.getSelectedRow(), INT_TBL_EGR_TRF_EMP_DES);
                        BigDecimal bgdVal= (BigDecimal)tblDatEgrTrf.getValueAt(tblDatEgrTrf.getSelectedRow(), INT_TBL_EGR_TRF_MONTO);
                        
                        intRowSelEgrTrf=tblDatEgrTrf.getSelectedRow();
                        
                        BigDecimal bgdValD=BigDecimal.ZERO;
                        
                        if(intEmpOrg==1){
                            if(intEmpDes==2){
                                scrollTblItmEgr.setBorder(new TitledBorder("EGRESO TUVAL-CASTEK"));
                                scrollTblItmIng.setBorder(new TitledBorder("INGRESO CASTEK-TUVAL"));
                                for(int i=0; i<vecSalPosCastek.size();i++){
                                    Vector vecReg=(Vector)vecSalPosCastek.get(i);
                                    int intDes=(Integer)vecReg.get(1);
                                    if(intDes==intEmpOrg){
                                        bgdValD=(BigDecimal)vecReg.get(0);
                                        if(bgdValD.compareTo(bgdVal)==0){
                                            if(((String)tblDatEgrTrf.getValueAt(tblDatEgrTrf.getSelectedRow(), INT_TBL_EGR_TRF_STPROC)).equalsIgnoreCase("S")){
                                                MensajeInf("El registro ya fue procesado");
                                            }else {
                                                cargarItemsEgreso(bgdVal, intEmpOrg, intEmpDes);                        
                                            }
                                        }else{
                                            MensajeInf("Los montos de Egreso ("+bgdValD+") e Ingreso ("+bgdVal+") no son iguales ");
                                        }
                                    }
                                }
                            }else if(intEmpDes==4){
                                scrollTblItmEgr.setBorder(new TitledBorder("EGRESO TUVAL-DIMULTI"));
                                scrollTblItmIng.setBorder(new TitledBorder("INGRESO DIMULTI-TUVAL"));                                
                                for(int i=0; i<vecSalPosDimulti.size();i++){
                                    Vector vecReg=(Vector)vecSalPosDimulti.get(i);
                                    int intDes=(Integer)vecReg.get(1);
                                    if(intDes==intEmpOrg){
                                        bgdValD=(BigDecimal)vecReg.get(0);
                                        if(bgdValD.compareTo(bgdVal)==0){
                                            if(((String)tblDatEgrTrf.getValueAt(tblDatEgrTrf.getSelectedRow(), INT_TBL_EGR_TRF_STPROC)).equalsIgnoreCase("S")){
                                                MensajeInf("El registro ya fue procesado");
                                            }else {
                                                cargarItemsEgreso(bgdVal, intEmpOrg, intEmpDes);                        
                                            }
                                        }else{
                                            //MensajeInf("Los Valores de Egreso e Ingreso no son iguales");
                                            MensajeInf("Los montos de Egreso ("+bgdValD+") e Ingreso ("+bgdVal+") no son iguales ");
                                        }
                                    }
                                }
                            }
                        }else if(intEmpOrg==2){
                            if(intEmpDes==1){
                                scrollTblItmEgr.setBorder(new TitledBorder("EGRESO CASTEK-TUVAL"));
                                scrollTblItmIng.setBorder(new TitledBorder("INGRESO TUVAL-CASTEK"));
                                for(int i=0; i<vecSalPosTuval.size();i++){
                                    Vector vecReg=(Vector)vecSalPosTuval.get(i);
                                    int intDes=(Integer)vecReg.get(1);
                                    if(intDes==intEmpOrg){
                                        bgdValD=(BigDecimal)vecReg.get(0);
                                        if(bgdValD.compareTo(bgdVal)==0){
                                            if(((String)tblDatEgrTrf.getValueAt(tblDatEgrTrf.getSelectedRow(), INT_TBL_EGR_TRF_STPROC)).equalsIgnoreCase("S")){
                                                MensajeInf("El registro ya fue procesado");
                                            }else {
                                                cargarItemsEgreso(bgdVal, intEmpOrg, intEmpDes);                        
                                            }
                                        }else{
                                            //MensajeInf("Los Valores de Egreso e Ingreso no son iguales");
                                            MensajeInf("Los montos de Egreso ("+bgdValD+") e Ingreso ("+bgdVal+") no son iguales ");
                                        }
                                    }
                                }
                            }else if(intEmpDes==4){
                                scrollTblItmEgr.setBorder(new TitledBorder("EGRESO CASTEK-DIMULTI"));
                                scrollTblItmIng.setBorder(new TitledBorder("INGRESO DIMULTI-CASTEK"));                                
                                for(int i=0; i<vecSalPosDimulti.size();i++){
                                    Vector vecReg=(Vector)vecSalPosDimulti.get(i);
                                    int intDes=(Integer)vecReg.get(1);
                                    if(intDes==intEmpOrg){
                                        bgdValD=(BigDecimal)vecReg.get(0);
                                        if(bgdValD.compareTo(bgdVal)==0){
                                            if(((String)tblDatEgrTrf.getValueAt(tblDatEgrTrf.getSelectedRow(), INT_TBL_EGR_TRF_STPROC)).equalsIgnoreCase("S")){
                                                MensajeInf("El registro ya fue procesado");
                                            }else {
                                                cargarItemsEgreso(bgdVal, intEmpOrg, intEmpDes);                        
                                            }
                                        }else{
                                            //MensajeInf("Los Valores de Egreso e Ingreso no son iguales");
                                            MensajeInf("Los montos de Egreso ("+bgdValD+") e Ingreso ("+bgdVal+") no son iguales ");
                                        }
                                    }
                                }
                            }
                        
                        }else if(intEmpOrg==4){
                            if(intEmpDes==1){
                                scrollTblItmEgr.setBorder(new TitledBorder("EGRESO DIMULTI-TUVAL"));
                                scrollTblItmIng.setBorder(new TitledBorder("INGRESO TUVAL-DIMULTI"));
                                for(int i=0; i<vecSalPosTuval.size();i++){
                                    Vector vecReg=(Vector)vecSalPosTuval.get(i);
                                    int intDes=(Integer)vecReg.get(1);
                                    if(intDes==intEmpOrg){
                                        bgdValD=(BigDecimal)vecReg.get(0);
                                        if(bgdValD.compareTo(bgdVal)==0){
                                            if(((String)tblDatEgrTrf.getValueAt(tblDatEgrTrf.getSelectedRow(), INT_TBL_EGR_TRF_STPROC)).equalsIgnoreCase("S")){
                                                MensajeInf("El registro ya fue procesado");
                                            }else {
                                                cargarItemsEgreso(bgdVal, intEmpOrg, intEmpDes);                        
                                            }
                                        }else{
                                            //MensajeInf("Los Valores de Egreso e Ingreso no son iguales");
                                            MensajeInf("Los montos de Egreso ("+bgdValD+") e Ingreso ("+bgdVal+") no son iguales ");
                                        }
                                    }
                                }
                            }else if(intEmpDes==2){
                                scrollTblItmEgr.setBorder(new TitledBorder("EGRESO DIMULTI-CASTEK"));
                                scrollTblItmIng.setBorder(new TitledBorder("INGRESO CASTEK-DIMULTI"));
                                for(int i=0; i<vecSalPosCastek.size();i++){
                                    Vector vecReg=(Vector)vecSalPosCastek.get(i);
                                    int intDes=(Integer)vecReg.get(1);
                                    if(intDes==intEmpOrg){
                                        bgdValD=(BigDecimal)vecReg.get(0);
                                        if(bgdValD.compareTo(bgdVal)==0){
                                            if(((String)tblDatEgrTrf.getValueAt(tblDatEgrTrf.getSelectedRow(), INT_TBL_EGR_TRF_STPROC)).equalsIgnoreCase("S")){
                                                MensajeInf("El registro ya fue procesado");
                                            }else {
                                                cargarItemsEgreso(bgdVal, intEmpOrg, intEmpDes);                        
                                            }
                                        }else{
                                            //MensajeInf("Los Valores de Egreso e Ingreso no son iguales");
                                            MensajeInf("Los montos de Egreso ("+bgdValD+") e Ingreso ("+bgdVal+") no son iguales ");
                                        }
                                    }
                                }
                            }
                        }
                        
                }
            });
            
            
            
            tcmAux.getColumn(INT_TBL_EGR_TRF_DESTRF).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_EGR_TRF_MONTO).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_EGR_TRF_BTN_DET).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_EGR_TRF_STPROC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_EGR_TRF_ORDEJE).setPreferredWidth(80);

            
            tcmAux.getColumn(INT_TBL_EGR_TRF_LINEA).setWidth(0);
            tcmAux.getColumn(INT_TBL_EGR_TRF_LINEA).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_EGR_TRF_LINEA).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_EGR_TRF_LINEA).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_EGR_TRF_LINEA).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_EGR_TRF_EMP_ORG).setWidth(0);
            tcmAux.getColumn(INT_TBL_EGR_TRF_EMP_ORG).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_EGR_TRF_EMP_ORG).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_EGR_TRF_EMP_ORG).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_EGR_TRF_EMP_ORG).setResizable(false);
            

            tcmAux.getColumn(INT_TBL_EGR_TRF_EMP_DES).setWidth(0);
            tcmAux.getColumn(INT_TBL_EGR_TRF_EMP_DES).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_EGR_TRF_EMP_DES).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_EGR_TRF_EMP_DES).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_EGR_TRF_EMP_DES).setResizable(false);

            
            tblDatEgrTrf.getTableHeader().setReorderingAllowed(false);
            objTblModEgrTrf.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            
            //objTblOrd=new ZafTblOrd(tblDatItmEgr);
            //objTblBus=new ZafTblBus(tblDatItmEgr);
            //int intCol[] = {INT_TBL_SALMEN, INT_TBL_SALACU};
            //objTblTot = new ZafTblTot(scrollTbl, scrollTblTot, tblDat, tblTot, intCol);        
            
            
        }catch(Exception ex ){
           ex.printStackTrace();
        }
        blnRes = true;
        return blnRes;
    }      
    
    private void mostrarVentanBodega(int intCodEmp, BigDecimal bgdValTra, int intCodEmpDes){
        ZafHer25_CnfBod obj1 = new ZafHer25_CnfBod(objZafParSis,intCodEmp, bgdValTra, intCodEmpDes,this );
        this.getParent().add(obj1, JLayeredPane.DEFAULT_LAYER );
        obj1.show();
    
    }

    private class ZafMouMotAda extends MouseMotionAdapter {
        @Override
        public void mouseMoved(MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) { 
                case INT_TBL_LINEA:
                    strMsg = "";
                    break;
                case INT_TBL_CODCTA:
                    strMsg = "Código de la Cuenta.";
                    break;
                case INT_TBL_TXTCTA:
                    strMsg = "Identificación del cliente.";
                    break;
                case INT_TBL_DESCTA:
                    strMsg = "Nombre de la Cuenta.";
                    break;
                case INT_TBL_SALMEN:
                    strMsg = "Saldo Mensual.";
                    break;
                case INT_TBL_SALACU:
                    strMsg = "Saldo Acumulado.";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }  
    
    private boolean configurarTblDatAsiEgr()
    {
        boolean blnRes=true;
        try
        {

            //Configurar JTable: Establecer el modelo.
            //vecDat=new Vector();    //Almacena los datos
            vecCabAsientoEgr=new Vector(8);   //Almacena las cabeceras
            vecCabAsientoEgr.clear();
            vecCabAsientoEgr.add(INT_TBL_DAT_LIN,"");
            vecCabAsientoEgr.add(INT_TBL_DAT_COD_CTA,"Cód.Cta.");
            vecCabAsientoEgr.add(INT_TBL_DAT_NUM_CTA,"Núm.Cta.");
            vecCabAsientoEgr.add(INT_TBL_DAT_NOM_CTA,"Nombre de la cuenta");
            vecCabAsientoEgr.add(INT_TBL_DAT_DEB,"Debe");
            vecCabAsientoEgr.add(INT_TBL_DAT_HAB,"Haber");
            vecCabAsientoEgr.add(INT_TBL_DAT_REF,"Referencia");
            vecCabAsientoEgr.add(INT_TBL_DAT_EST_CON,"Estado de Conciliación");


            //vecCta=new Vector();    //Almacena las cuentas.
            objTblModAsientoEgr=new ZafTblMod();
            objTblModAsientoEgr.setHeader(vecCabAsientoEgr);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModAsientoEgr.setColumnDataType(INT_TBL_DAT_DEB, objTblModAsientoEgr.INT_COL_DBL, new Integer(0), null);
            objTblModAsientoEgr.setColumnDataType(INT_TBL_DAT_HAB, objTblModAsientoEgr.INT_COL_DBL, new Integer(0), null);
            objTblModAsientoEgr.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDatEgr.setModel(objTblModAsientoEgr);
            //Configurar JTable: Establecer tipo de selección.
            tblDatEgr.setRowSelectionAllowed(true);
            tblDatEgr.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menó de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatEgr);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatEgr.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatEgr.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CTA).setPreferredWidth(190);
            tcmAux.getColumn(INT_TBL_DAT_DEB).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_HAB).setPreferredWidth(60);

            tblDatEgr.getTableHeader().setReorderingAllowed(false);

            tcmAux.getColumn(INT_TBL_DAT_LIN).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setResizable(false);            
            
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setResizable(false);

            tcmAux.getColumn(INT_TBL_DAT_EST_CON).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_CON).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_CON).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_CON).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_CON).setResizable(false);

            tcmAux.getColumn(INT_TBL_DAT_REF).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_REF).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_REF).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_REF).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_REF).setResizable(false);


            objTblFilCab=new ZafTblFilCab(tblDatEgr);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            ZafTblCelRenLbl objTblCelRenLbl; 
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_DEB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_HAB).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            
            ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDatEgr.getTableHeader();
            objTblHeaGrp.setHeight(16 * 2);

            ZafTblHeaColGrp objTblHeaColGrpCotVen = new ZafTblHeaColGrp("EGRESOS");
            objTblHeaColGrpCotVen.setHeight(16);
            

            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_LIN));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_NUM_CTA));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_NOM_CTA));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_DEB));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_HAB));

            objTblHeaGrp.addColumnGroup(objTblHeaColGrpCotVen);
            objTblHeaColGrpCotVen = null;
            
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            //objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    


    private boolean configurarTblDatAsiIng()
    {
        boolean blnRes=true;
        try
        {

            //Configurar JTable: Establecer el modelo.
            vecCabAsientoIng=new Vector(8);   //Almacena las cabeceras
            vecCabAsientoIng.clear();
            vecCabAsientoIng.add(INT_TBL_DAT_LIN,"");
            vecCabAsientoIng.add(INT_TBL_DAT_COD_CTA,"Cód.Cta.");
            vecCabAsientoIng.add(INT_TBL_DAT_NUM_CTA,"Núm.Cta.");

            vecCabAsientoIng.add(INT_TBL_DAT_NOM_CTA,"Nombre de la cuenta");
            vecCabAsientoIng.add(INT_TBL_DAT_DEB,"Debe");
            vecCabAsientoIng.add(INT_TBL_DAT_HAB,"Haber");
            vecCabAsientoIng.add(INT_TBL_DAT_REF,"Referencia");
            vecCabAsientoIng.add(INT_TBL_DAT_EST_CON,"Estado de Conciliación");


            //vecCta=new Vector();    //Almacena las cuentas.
            objTblModAsientoIng=new ZafTblMod();
            objTblModAsientoIng.setHeader(vecCabAsientoIng);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModAsientoIng.setColumnDataType(INT_TBL_DAT_DEB, objTblModAsientoIng.INT_COL_DBL, new Integer(0), null);
            objTblModAsientoIng.setColumnDataType(INT_TBL_DAT_HAB, objTblModAsientoIng.INT_COL_DBL, new Integer(0), null);
            objTblModAsientoIng.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDatIng.setModel(objTblModAsientoIng);
            //Configurar JTable: Establecer tipo de selección.
            tblDatIng.setRowSelectionAllowed(true);
            tblDatIng.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menó de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatIng);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatIng.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatIng.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CTA).setPreferredWidth(190);
            tcmAux.getColumn(INT_TBL_DAT_DEB).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_HAB).setPreferredWidth(60);

            tblDatIng.getTableHeader().setReorderingAllowed(false);

            tcmAux.getColumn(INT_TBL_DAT_LIN).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setResizable(false);            
            
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setResizable(false);

            tcmAux.getColumn(INT_TBL_DAT_EST_CON).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_CON).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_CON).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_CON).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_CON).setResizable(false);

            tcmAux.getColumn(INT_TBL_DAT_REF).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_REF).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_REF).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_REF).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_REF).setResizable(false);




            objTblFilCab=new ZafTblFilCab(tblDatIng);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            ZafTblCelRenLbl objTblCelRenLbl; 
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_DEB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_HAB).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            
            ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDatIng.getTableHeader();
            objTblHeaGrp.setHeight(16 * 2);

            ZafTblHeaColGrp objTblHeaColGrpCotVen = new ZafTblHeaColGrp("INGRESOS");
            objTblHeaColGrpCotVen.setHeight(16);
            

            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_LIN));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_NUM_CTA));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_NOM_CTA));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_DEB));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_HAB));

            objTblHeaGrp.addColumnGroup(objTblHeaColGrpCotVen);
            objTblHeaColGrpCotVen = null;
            
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            //objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 


    private void cargarAsientoEgreso(){
        Vector vecData=new Vector();
        vecSalNegTuval=new Vector();
        vecSalNegCastek=new Vector();
        vecSalNegDimulti=new Vector();
        
        try{
        for(int i=0;i<tblDat.getRowCount();i++){
            
            //BigDecimal dblSalMen=(BigDecimal)tblDat.getValueAt(i, INT_TBL_SALMEN );
            BigDecimal dblSalAcu=(BigDecimal)tblDat.getValueAt(i, INT_TBL_SALACU );
            //if(dblSalMen!=null && dblSalMen.compareTo(BigDecimal.ZERO)<0){
            if(dblSalAcu!=null && dblSalAcu.compareTo(BigDecimal.ZERO)<0){
                
                if(((Integer)tblDat.getValueAt(i, INT_TBL_CODEMPRESA))==1){
                    Vector vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CTA,"");
                    vecReg.add(INT_TBL_DAT_NUM_CTA,"<html><b>TUVAL<b></html>");
                    vecReg.add(INT_TBL_DAT_NOM_CTA, "");
                    vecReg.add(INT_TBL_DAT_DEB, 0);
                    vecReg.add(INT_TBL_DAT_HAB, 0);
                    vecReg.add(INT_TBL_DAT_REF,0);
                    vecReg.add(INT_TBL_DAT_EST_CON,0);
                    vecData.add(vecReg);
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CTA,"");
                    vecReg.add(INT_TBL_DAT_NUM_CTA,tblDat.getValueAt(i, INT_TBL_TXTCTA));
                    vecReg.add(INT_TBL_DAT_NOM_CTA, tblDat.getValueAt(i, INT_TBL_DESCTA));
                    //vecReg.add(INT_TBL_DAT_DEB, dblSalMen.abs());
                    vecReg.add(INT_TBL_DAT_DEB, dblSalAcu.abs());
                    vecReg.add(INT_TBL_DAT_HAB, 0);
                    vecReg.add(INT_TBL_DAT_REF,0);
                    vecReg.add(INT_TBL_DAT_EST_CON,0);
                    vecData.add(vecReg);
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CTA,"");
                    vecReg.add(INT_TBL_DAT_NUM_CTA,"1.01.06.01.15");                    
                    vecReg.add(INT_TBL_DAT_NOM_CTA, "BODEGA GENERAL");
                    vecReg.add(INT_TBL_DAT_DEB, 0);
                    //vecReg.add(INT_TBL_DAT_HAB, dblSalMen.abs());
                    vecReg.add(INT_TBL_DAT_HAB, dblSalAcu.abs());
                    vecReg.add(INT_TBL_DAT_REF,0);
                    vecReg.add(INT_TBL_DAT_EST_CON,0);                    
                    vecData.add(vecReg); 
                    Vector vecNeg;
                    vecNeg=new Vector();
                    //vecNeg.add(dblSalMen);
                    vecNeg.add(dblSalAcu);
                    if(tblDat.getValueAt(i, INT_TBL_TXTCTA).equals("1.01.03.01.88")){
                        vecNeg.add(2);
                    }else if(tblDat.getValueAt(i, INT_TBL_TXTCTA).equals("1.01.03.01.89")){
                       vecNeg.add(4);
                    }
                    vecSalNegTuval.add(vecNeg);
                }

                if(((Integer)tblDat.getValueAt(i, INT_TBL_CODEMPRESA))==2){
                    Vector vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CTA,"");
                    vecReg.add(INT_TBL_DAT_NUM_CTA,"<html><b>CASTEK</b></html>");
                    vecReg.add(INT_TBL_DAT_NOM_CTA, "");
                    vecReg.add(INT_TBL_DAT_DEB, 0);
                    vecReg.add(INT_TBL_DAT_HAB, 0);
                    vecReg.add(INT_TBL_DAT_REF,0);
                    vecReg.add(INT_TBL_DAT_EST_CON,0);
                    vecData.add(vecReg);                    
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CTA,"");
                    vecReg.add(INT_TBL_DAT_NUM_CTA,tblDat.getValueAt(i, INT_TBL_TXTCTA));
                    vecReg.add(INT_TBL_DAT_NOM_CTA, tblDat.getValueAt(i, INT_TBL_DESCTA));
                    //vecReg.add(INT_TBL_DAT_DEB, dblSalMen.abs());
                    vecReg.add(INT_TBL_DAT_DEB, dblSalAcu.abs());
                    vecReg.add(INT_TBL_DAT_HAB, 0);
                    vecReg.add(INT_TBL_DAT_REF,0);
                    vecReg.add(INT_TBL_DAT_EST_CON,0);
                    vecData.add(vecReg);
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CTA,"");
                    vecReg.add(INT_TBL_DAT_NUM_CTA,"1.01.06.01.15");                    
                    vecReg.add(INT_TBL_DAT_NOM_CTA, "BODEGA GENERAL");
                    vecReg.add(INT_TBL_DAT_DEB, 0);
                    //vecReg.add(INT_TBL_DAT_HAB, dblSalMen.abs());
                    vecReg.add(INT_TBL_DAT_HAB, dblSalAcu.abs());
                    vecReg.add(INT_TBL_DAT_REF,0);
                    vecReg.add(INT_TBL_DAT_EST_CON,0);                    
                    vecData.add(vecReg);
                    Vector vecNeg;
                    vecNeg=new Vector();
                    //vecNeg.add(dblSalMen);
                    vecNeg.add(dblSalAcu);
                    if(tblDat.getValueAt(i, INT_TBL_TXTCTA).equals("1.01.03.01.88")){
                        vecNeg.add(1);
                    }else if(tblDat.getValueAt(i, INT_TBL_TXTCTA).equals("1.01.03.01.98")){
                       vecNeg.add(4);
                    }
                    vecSalNegCastek.add(vecNeg);
                    
                }                
                
                if(((Integer)tblDat.getValueAt(i, INT_TBL_CODEMPRESA))==4){
                    Vector vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CTA,"");
                    vecReg.add(INT_TBL_DAT_NUM_CTA,"<html><b>DIMULTI</b></html>");
                    vecReg.add(INT_TBL_DAT_NOM_CTA, "");
                    vecReg.add(INT_TBL_DAT_DEB, 0);
                    vecReg.add(INT_TBL_DAT_HAB, 0);
                    vecReg.add(INT_TBL_DAT_REF,0);
                    vecReg.add(INT_TBL_DAT_EST_CON,0);
                    vecData.add(vecReg);                    
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CTA,"");
                    vecReg.add(INT_TBL_DAT_NUM_CTA,tblDat.getValueAt(i, INT_TBL_TXTCTA));
                    vecReg.add(INT_TBL_DAT_NOM_CTA, tblDat.getValueAt(i, INT_TBL_DESCTA));
                    //vecReg.add(INT_TBL_DAT_DEB, dblSalMen.abs());
                    vecReg.add(INT_TBL_DAT_DEB, dblSalAcu.abs());
                    vecReg.add(INT_TBL_DAT_HAB, 0);
                    vecReg.add(INT_TBL_DAT_REF,0);
                    vecReg.add(INT_TBL_DAT_EST_CON,0);
                    vecData.add(vecReg);
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CTA,"");
                    vecReg.add(INT_TBL_DAT_NUM_CTA,"1.01.06.01.15");                    
                    vecReg.add(INT_TBL_DAT_NOM_CTA, "BODEGA GENERAL");
                    vecReg.add(INT_TBL_DAT_DEB, 0);
                    //vecReg.add(INT_TBL_DAT_HAB, dblSalMen.abs());
                    vecReg.add(INT_TBL_DAT_HAB, dblSalAcu.abs());
                    vecReg.add(INT_TBL_DAT_REF,0);
                    vecReg.add(INT_TBL_DAT_EST_CON,0);                    
                    vecData.add(vecReg);  
                    /*OBTENGO LOS SALDOS NEGATIVOS QUE IMPLICAN QUE SON EGRESOS*/
                    Vector vecNeg;
                    vecNeg=new Vector();
                    //vecNeg.add(dblSalMen);
                    vecNeg.add(dblSalAcu);
                    if(tblDat.getValueAt(i, INT_TBL_TXTCTA).equals("1.01.03.01.89")){
                        vecNeg.add(1);
                    }else if(tblDat.getValueAt(i, INT_TBL_TXTCTA).equals("1.01.03.01.98")){
                       vecNeg.add(2);
                    }
                    vecSalNegDimulti.add(vecNeg);                    
                }
            }
        
        }
        objTblModAsientoEgr.setData(vecData);
        tblDatEgr.setModel(objTblModAsientoEgr);
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
    
    private void cargarAsientoIngreso()
    {
        Vector vecData=new Vector();
        vecSalPosTuval=new Vector();
        vecSalPosCastek=new Vector();
        vecSalPosDimulti=new Vector();        
        try{
        for(int i=0;i<tblDat.getRowCount();i++){
            
            //BigDecimal dblSalMen=(BigDecimal)tblDat.getValueAt(i, INT_TBL_SALMEN );
            BigDecimal dblSalAcu=(BigDecimal)tblDat.getValueAt(i, INT_TBL_SALACU );
            //if(dblSalMen!=null && dblSalMen.compareTo(BigDecimal.ZERO)>0){
            if(dblSalAcu!=null && dblSalAcu.compareTo(BigDecimal.ZERO)>0){
                
                if(((Integer)tblDat.getValueAt(i, INT_TBL_CODEMPRESA))==1){
                    Vector vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CTA,"");
                    vecReg.add(INT_TBL_DAT_NUM_CTA,"<html><b>TUVAL<b></html>");
                    vecReg.add(INT_TBL_DAT_NOM_CTA, "");
                    vecReg.add(INT_TBL_DAT_DEB, 0);
                    vecReg.add(INT_TBL_DAT_HAB, 0);
                    vecReg.add(INT_TBL_DAT_REF,0);
                    vecReg.add(INT_TBL_DAT_EST_CON,0);
                    vecData.add(vecReg);
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CTA,"");
                    vecReg.add(INT_TBL_DAT_NUM_CTA,tblDat.getValueAt(i, INT_TBL_TXTCTA));
                    vecReg.add(INT_TBL_DAT_NOM_CTA, tblDat.getValueAt(i, INT_TBL_DESCTA));
                    vecReg.add(INT_TBL_DAT_DEB, 0);
                    //vecReg.add(INT_TBL_DAT_HAB, dblSalMen.abs());
                    vecReg.add(INT_TBL_DAT_HAB, dblSalAcu.abs());
                    vecReg.add(INT_TBL_DAT_REF,0);
                    vecReg.add(INT_TBL_DAT_EST_CON,0);
                    vecData.add(vecReg);
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CTA,"");
                    vecReg.add(INT_TBL_DAT_NUM_CTA,"1.01.06.01.15");                    
                    vecReg.add(INT_TBL_DAT_NOM_CTA, "BODEGA GENERAL");
                    //vecReg.add(INT_TBL_DAT_DEB, dblSalMen.abs());
                    vecReg.add(INT_TBL_DAT_DEB, dblSalAcu.abs());
                    vecReg.add(INT_TBL_DAT_HAB, 0);
                    vecReg.add(INT_TBL_DAT_REF,0);
                    vecReg.add(INT_TBL_DAT_EST_CON,0);                    
                    vecData.add(vecReg);  
                    Vector vecPos;
                    vecPos=new Vector();
                    //vecPos.add(dblSalMen);
                    vecPos.add(dblSalAcu);
                    if(tblDat.getValueAt(i, INT_TBL_TXTCTA).equals("1.01.03.01.88")){
                        vecPos.add(2);
                    }else if(tblDat.getValueAt(i, INT_TBL_TXTCTA).equals("1.01.03.01.89")){
                       vecPos.add(4);
                    }
                    vecSalPosTuval.add(vecPos);                     
                }

                if(((Integer)tblDat.getValueAt(i, INT_TBL_CODEMPRESA))==2){
                    Vector vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CTA,"");
                    vecReg.add(INT_TBL_DAT_NUM_CTA,"<html><b>CASTEK</b></html>");
                    vecReg.add(INT_TBL_DAT_NOM_CTA, "");
                    vecReg.add(INT_TBL_DAT_DEB, 0);
                    vecReg.add(INT_TBL_DAT_HAB, 0);
                    vecReg.add(INT_TBL_DAT_REF,0);
                    vecReg.add(INT_TBL_DAT_EST_CON,0);
                    vecData.add(vecReg);                    
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CTA,"");
                    vecReg.add(INT_TBL_DAT_NUM_CTA,tblDat.getValueAt(i, INT_TBL_TXTCTA));
                    vecReg.add(INT_TBL_DAT_NOM_CTA, tblDat.getValueAt(i, INT_TBL_DESCTA));
                    vecReg.add(INT_TBL_DAT_DEB, 0);
                    //vecReg.add(INT_TBL_DAT_HAB, dblSalMen.abs());
                    vecReg.add(INT_TBL_DAT_HAB, dblSalAcu.abs());
                    vecReg.add(INT_TBL_DAT_REF,0);
                    vecReg.add(INT_TBL_DAT_EST_CON,0);
                    vecData.add(vecReg);
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CTA,"");
                    vecReg.add(INT_TBL_DAT_NUM_CTA,"1.01.06.01.15");                    
                    vecReg.add(INT_TBL_DAT_NOM_CTA, "BODEGA GENERAL");
                    //vecReg.add(INT_TBL_DAT_DEB, dblSalMen.abs());
                    vecReg.add(INT_TBL_DAT_DEB, dblSalAcu.abs());
                    vecReg.add(INT_TBL_DAT_HAB, 0);
                    vecReg.add(INT_TBL_DAT_REF,0);
                    vecReg.add(INT_TBL_DAT_EST_CON,0);                    
                    vecData.add(vecReg);   
                    Vector vecPos;
                    vecPos=new Vector();
                    //vecPos.add(dblSalMen);
                    vecPos.add(dblSalAcu);
                    if(tblDat.getValueAt(i, INT_TBL_TXTCTA).equals("1.01.03.01.88")){
                        vecPos.add(1);
                    }else if(tblDat.getValueAt(i, INT_TBL_TXTCTA).equals("1.01.03.01.98")){
                       vecPos.add(4);
                    }
                    vecSalPosCastek.add(vecPos);                    
                }                
                
                if(((Integer)tblDat.getValueAt(i, INT_TBL_CODEMPRESA))==4){
                    Vector vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CTA,"");
                    vecReg.add(INT_TBL_DAT_NUM_CTA,"<html><b>DIMULTI</b></html>");
                    vecReg.add(INT_TBL_DAT_NOM_CTA, "");
                    vecReg.add(INT_TBL_DAT_DEB, 0);
                    vecReg.add(INT_TBL_DAT_HAB, 0);
                    vecReg.add(INT_TBL_DAT_REF,0);
                    vecReg.add(INT_TBL_DAT_EST_CON,0);
                    vecData.add(vecReg);                    
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CTA,"");
                    vecReg.add(INT_TBL_DAT_NUM_CTA,tblDat.getValueAt(i, INT_TBL_TXTCTA));
                    vecReg.add(INT_TBL_DAT_NOM_CTA, tblDat.getValueAt(i, INT_TBL_DESCTA));
                    vecReg.add(INT_TBL_DAT_DEB, 0);
                    //vecReg.add(INT_TBL_DAT_HAB, dblSalMen.abs());
                    vecReg.add(INT_TBL_DAT_HAB, dblSalAcu.abs());
                    vecReg.add(INT_TBL_DAT_REF,0);
                    vecReg.add(INT_TBL_DAT_EST_CON,0);
                    vecData.add(vecReg);
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CTA,"");
                    vecReg.add(INT_TBL_DAT_NUM_CTA,"1.01.06.01.15");                    
                    vecReg.add(INT_TBL_DAT_NOM_CTA, "BODEGA GENERAL");
                    //vecReg.add(INT_TBL_DAT_DEB, dblSalMen.abs());
                    vecReg.add(INT_TBL_DAT_DEB, dblSalAcu.abs());
                    vecReg.add(INT_TBL_DAT_HAB, 0);
                    vecReg.add(INT_TBL_DAT_REF,0);
                    vecReg.add(INT_TBL_DAT_EST_CON,0);                    
                    vecData.add(vecReg);  
                    Vector vecPos;
                    vecPos=new Vector();
                    //vecPos.add(dblSalMen);
                    vecPos.add(dblSalAcu);
                    if(tblDat.getValueAt(i, INT_TBL_TXTCTA).equals("1.01.03.01.89")){
                        vecPos.add(1);
                    }else if(tblDat.getValueAt(i, INT_TBL_TXTCTA).equals("1.01.03.01.98")){
                       vecPos.add(2);
                    }
                    vecSalPosDimulti.add(vecPos);                    
                }
            }
        
        }
        objTblModAsientoIng.setData(vecData);
        tblDatIng.setModel(objTblModAsientoIng);
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
    
    private boolean verificarProcesado(String strCod){
        boolean booProc=false;
        for(String strDat:lstProEgrTrf){
            if(strDat.equals(strCod)){
                booProc=true;
                break;
            }
        }
        return booProc;
    }
    
    private void establecerOrdenEjecucion(){
        int intOrdEje=0;
        BigDecimal bgdTotEgrTuval=BigDecimal.ZERO, bgdTotEgrCastek=BigDecimal.ZERO, bgdTotEgrDimulti=BigDecimal.ZERO;
        

        
        for(int i=0; i<vecSalNegTuval.size();i++){
             Vector vc=(Vector)vecSalNegTuval.get(i);
             bgdTotEgrTuval=bgdTotEgrTuval.add(((BigDecimal)vc.get(0)).abs());
        }
        
        for(int i=0; i<vecSalNegCastek.size();i++){
             Vector vc=(Vector)vecSalNegCastek.get(i);
             bgdTotEgrCastek=bgdTotEgrCastek.add(((BigDecimal)vc.get(0)).abs());
        }

        for(int i=0; i<vecSalNegDimulti.size();i++){
             Vector vc=(Vector)vecSalNegDimulti.get(i);
             bgdTotEgrDimulti=bgdTotEgrDimulti.add(((BigDecimal)vc.get(0)).abs());
        }
        
        SortedMap map = new TreeMap(java.util.Collections.reverseOrder());
        if(bgdTotEgrTuval.compareTo(BigDecimal.ZERO)>0){
            map.put(1,bgdTotEgrTuval.setScale(2, RoundingMode.HALF_UP));
        }
        
        if(bgdTotEgrCastek.compareTo(BigDecimal.ZERO)>0){
            map.put(2,bgdTotEgrCastek.setScale(2, RoundingMode.HALF_UP));
        }
        
        if(bgdTotEgrDimulti.compareTo(BigDecimal.ZERO)>0){
        map.put(4,bgdTotEgrDimulti.setScale(2, RoundingMode.HALF_UP));
        }


        
        Iterator iterator = map.keySet().iterator();
        int intOrden=0;
        //Map<Integer,String> mapDat=new HashMap<Integer,String>();
        
        while (iterator.hasNext()) {
            Object key = iterator.next();
            Integer intKey=(Integer)key;            

            if(((Integer)key)==1){ // REPRESENTA EMPRESA TUVAL
                BigDecimal bgdTotCast=BigDecimal.ZERO;
                BigDecimal bgdTotDim=BigDecimal.ZERO;
                int intIndCastek=0;
                int intIndDimulti=0;
                for(int i=0; i<vecSalNegCastek.size();i++){
                    Vector vc=(Vector)vecSalNegCastek.get(i);
                    if(intKey==(Integer)vc.get(1)){
                        bgdTotCast=(BigDecimal)vc.get(0);
                        intIndCastek=i;
                    }
                }
                
                for(int j=0; j<vecSalNegDimulti.size();j++){
                    Vector vcDimulti=(Vector)vecSalNegDimulti.get(j);                    
                    if(intKey==(Integer)vcDimulti.get(1)){                        
                        bgdTotDim=(BigDecimal)vcDimulti.get(0);
                        intIndDimulti=j;
                    }
                }
                
                Vector vcCastek=null;
                if(vecSalNegCastek!=null && vecSalNegCastek.size()>0)
                    vcCastek=(Vector)vecSalNegCastek.get(intIndCastek);
                
                Vector vcDimulti=null;
                if(vecSalNegDimulti!=null && vecSalNegDimulti.size()>0)
                    vcDimulti=(Vector)vecSalNegDimulti.get(intIndDimulti);
                if(vcCastek!=null && vcCastek.size()==2){
                    if(bgdTotCast.compareTo(bgdTotDim)<0){                        
                        if(vcDimulti!=null && vcDimulti.size()>2){
                            intOrden++; 
                            vcCastek.add(intOrden);
                            for(int k=0; k<vecSalNegTuval.size();k++){
                                Vector vcTuv=(Vector)vecSalNegTuval.get(k);
                                if(vcTuv.size()==2){
                                    intOrden++;
                                    vcTuv.add(intOrden);
                                }
                            }
                        }                        
                    }
                }

                if(vcDimulti!=null && vcDimulti.size()==2){
                    if(bgdTotDim.compareTo(bgdTotCast)>0){
                        intOrden++;                    
                        vcDimulti.add(intOrden);

                        for(int k=0; k<vecSalNegCastek.size();k++){
                            Vector vcCas=(Vector)vecSalNegCastek.get(k);
                            if(vcCas.size()==2){
                                intOrden++;
                                vcCas.add(intOrden);
                            }
                        }
                    }                    
                }                
            
            }
            
            if(((Integer)key)==2){ // REPRESENTA EMPRESA CASTEK
                BigDecimal bgdTotTuv=BigDecimal.ZERO;
                BigDecimal bgdTotDim=BigDecimal.ZERO;
                int intIndTuval=0;
                int intIndDimulti=0;                
                
                for(int i=0; i<vecSalNegTuval.size();i++){
                    Vector vc=(Vector)vecSalNegTuval.get(i);
                    if(intKey==(Integer)vc.get(1)){
                        bgdTotTuv=(BigDecimal)vc.get(0);
                        intIndTuval=i;
                    }
                }
                
                for(int j=0; j<vecSalNegDimulti.size();j++){
                    Vector vcDimulti=(Vector)vecSalNegDimulti.get(j);                    
                    if(intKey==(Integer)vcDimulti.get(1)){                        
                        bgdTotDim=(BigDecimal)vcDimulti.get(0);
                        intIndDimulti=j;
                    }
                }
                Vector vcTuval=null;
                if(vecSalNegTuval!=null && vecSalNegTuval.size()>0)
                    vcTuval=(Vector)vecSalNegTuval.get(intIndTuval);
                
                Vector vcDimulti=null;
                if(vecSalNegDimulti!=null && vecSalNegDimulti.size()>0)
                    vcDimulti=(Vector)vecSalNegDimulti.get(intIndDimulti);
                
                if(vcTuval!=null && vcTuval.size()==2){
                    if(bgdTotTuv.compareTo(bgdTotDim)<0){                        
                        if(vcDimulti!=null && vcDimulti.size()>2){
                            intOrden++; 
                            vcTuval.add(intOrden);
                            for(int k=0; k<vecSalNegCastek.size();k++){
                                Vector vcCas=(Vector)vecSalNegCastek.get(k);
                                if(vcCas!=null && vcCas.size()==2){
                                    intOrden++;
                                    vcCas.add(intOrden);
                                }
                            }
                        }                        
                    }
                }

                if(vcDimulti!=null && vcDimulti.size()==2){
                    if(bgdTotDim.compareTo(bgdTotTuv)>0){
                        intOrden++;                    
                        vcDimulti.add(intOrden);
                        for(int k=0; k<vecSalNegCastek.size();k++){
                            Vector vcCas=(Vector)vecSalNegCastek.get(k);
                            if(vcCas!=null && vcCas.size()==2){
                                intOrden++;
                                vcCas.add(intOrden);
                            }
                        }
                    }                    
                }            
            }       
            
            if((Integer)key==4){  //Representa DIMULTI 
                BigDecimal bgdTotTuv=BigDecimal.ZERO;
                BigDecimal bgdTotCas=BigDecimal.ZERO;
                int intIndTuval=0;
                int intIndCastek=0;
                
                for(int i=0; i<vecSalNegTuval.size();i++){
                    Vector vc=(Vector)vecSalNegTuval.get(i);
                    if(intKey==(Integer)vc.get(1)){
                        bgdTotTuv=(BigDecimal)vc.get(0);
                        intIndTuval=i;
                    }
                }
                
                for(int j=0; j<vecSalNegCastek.size();j++){
                    Vector vcCastek=(Vector)vecSalNegCastek.get(j);                    
                    if(intKey==(Integer)vcCastek.get(1)){                        
                        bgdTotCas=(BigDecimal)vcCastek.get(0);
                        intIndCastek=j;
                    }
                }
                
                Vector vcTuval=null;
                if(vecSalNegTuval!=null && vecSalNegTuval.size()>0)
                    vcTuval=(Vector)vecSalNegTuval.get(intIndTuval);
                
                Vector vcCastek=null;
                if(vecSalNegCastek!=null && vecSalNegCastek.size()>0)
                    vcCastek=(Vector)vecSalNegCastek.get(intIndCastek);
                
                if(vcTuval!=null && vcTuval.size()==2){
                    if(bgdTotTuv.compareTo(bgdTotCas)<0){                        
                        if(vcCastek!=null && vcCastek.size()>2){
                            intOrden++; 
                            vcTuval.add(intOrden);
                            for(int k=0; k<vecSalNegDimulti.size();k++){
                                Vector vcDim=(Vector)vecSalNegDimulti.get(k);
                                if(vcDim!=null && vcDim.size()==2){
                                    intOrden++;
                                    vcDim.add(intOrden);
                                }
                            }
                        }                        
                    }
                }

                if(vcCastek!=null && vcCastek.size()==2){
                    if(bgdTotCas.compareTo(bgdTotTuv)>0){
                        intOrden++;                    
                        vcCastek.add(intOrden);
                        for(int k=0; k<vecSalNegDimulti.size();k++){
                            Vector vcDim=(Vector)vecSalNegDimulti.get(k);
                            if(vcDim!=null && vcDim.size()==2){
                                intOrden++;
                                vcDim.add(intOrden);
                            }
                        }
                    }                    
                }                
            }
        }
        /*SE LES PROPORCIONA UN ORDEN A LOS REGISTROS QUE NO TENGAN*/
        for(int i=0; i<vecSalNegTuval.size();i++){
            Vector vecTuv=(Vector)vecSalNegTuval.get(i);
            if(vecTuv!=null && vecTuv.size()==2){
                intOrden++;
                vecTuv.add(intOrden);
            }
        }
        
        for(int i=0; i<vecSalNegCastek.size();i++){
            Vector vecCas=(Vector)vecSalNegCastek.get(i);
            if(vecCas!=null && vecCas.size()==2){
                intOrden++;
                vecCas.add(intOrden);
            }
        }

        for(int i=0; i<vecSalNegDimulti.size();i++){
            Vector vecDim=(Vector)vecSalNegDimulti.get(i);
            if(vecDim!=null && vecDim.size()==2){
                intOrden++;
                vecDim.add(intOrden);
            }
        }
        /*SE LES PROPORCIONA UN ORDEN A LOS REGISTROS QUE NO TENGAN*/
        
        //printMap(mapDat);
    }
    
    public void cargarEgrTrf(){
        Vector vecData=new Vector();
        establecerOrdenEjecucion();
        for(int i=0; i<vecSalNegTuval.size();i++){
            Vector vecReg=new Vector();
            Vector vc=(Vector)vecSalNegTuval.get(i);
            BigDecimal bgdSal=((BigDecimal)vc.get(0)).abs();
            int intEmpDes=(Integer)vc.get(1);
            String strDesEmp="";
            if(intEmpDes==2){
                strDesEmp="CASTEK";
            }else if(intEmpDes==4){
                strDesEmp="DIMULTI";
            }
            vecReg.add(INT_TBL_EGR_TRF_LINEA,"" );
            vecReg.add(INT_TBL_EGR_TRF_DESTRF, "TUVAL - "+strDesEmp);
            vecReg.add(INT_TBL_EGR_TRF_MONTO, bgdSal);
            vecReg.add(INT_TBL_EGR_TRF_EMP_ORG, 1);
            vecReg.add(INT_TBL_EGR_TRF_EMP_DES, intEmpDes);
            vecReg.add(INT_TBL_EGR_TRF_BTN_DET,"..");
            //vecReg.add(INT_TBL_EGR_TRF_STPROC,"");
            vecReg.add(INT_TBL_EGR_TRF_STPROC,(verificarProcesado("1-"+intEmpDes)==true?"S":""));
            vecReg.add(INT_TBL_EGR_TRF_ORDEJE,(Integer)vc.get(2));
            vecReg.add(INT_TBL_EGR_TRF_CNFBOD,"..");
            
            vecData.add(vecReg);
        }
        
        for(int i=0; i<vecSalNegCastek.size();i++){
            Vector vecReg=new Vector();
            Vector vc=(Vector)vecSalNegCastek.get(i);
            BigDecimal bgdSal=((BigDecimal)vc.get(0)).abs();
            int intEmpDes=(Integer)vc.get(1);
            String strDesEmp="";
            if(intEmpDes==1){
                strDesEmp="TUVAL";
            }else if(intEmpDes==4){
                strDesEmp="DIMULTI";
            }
            vecReg.add(INT_TBL_EGR_TRF_LINEA,"" );
            vecReg.add(INT_TBL_EGR_TRF_DESTRF, "CASTEK - "+strDesEmp);
            vecReg.add(INT_TBL_EGR_TRF_MONTO, bgdSal);
            vecReg.add(INT_TBL_EGR_TRF_EMP_ORG, 2);
            vecReg.add(INT_TBL_EGR_TRF_EMP_DES, intEmpDes);
            vecReg.add(INT_TBL_EGR_TRF_BTN_DET,"..");   
            //vecReg.add(INT_TBL_EGR_TRF_STPROC,"");
            vecReg.add(INT_TBL_EGR_TRF_STPROC,(verificarProcesado("2-"+intEmpDes)==true?"S":""));
            vecReg.add(INT_TBL_EGR_TRF_ORDEJE,(Integer)vc.get(2));
            vecReg.add(INT_TBL_EGR_TRF_CNFBOD,"..");
            vecData.add(vecReg);
        }

        for(int i=0; i<vecSalNegDimulti.size();i++){
            Vector vecReg=new Vector();
            Vector vc=(Vector)vecSalNegDimulti.get(i);
            BigDecimal bgdSal=((BigDecimal)vc.get(0)).abs();
            int intEmpDes=(Integer)vc.get(1);
            String strDesEmp="";
            if(intEmpDes==1){
                strDesEmp="TUVAL";
            }else if(intEmpDes==2){
                strDesEmp="CASTEK";
            }
            vecReg.add(INT_TBL_EGR_TRF_LINEA,"" );
            vecReg.add(INT_TBL_EGR_TRF_DESTRF, "DIMULTI - "+strDesEmp);
            vecReg.add(INT_TBL_EGR_TRF_MONTO, bgdSal);
            vecReg.add(INT_TBL_EGR_TRF_EMP_ORG, 4);
            vecReg.add(INT_TBL_EGR_TRF_EMP_DES, intEmpDes);
            vecReg.add(INT_TBL_EGR_TRF_BTN_DET,"..");
            //vecReg.add(INT_TBL_EGR_TRF_STPROC,"");
            vecReg.add(INT_TBL_EGR_TRF_STPROC,(verificarProcesado("4-"+intEmpDes)==true?"S":""));  
            vecReg.add(INT_TBL_EGR_TRF_ORDEJE,(Integer)vc.get(2));
            vecReg.add(INT_TBL_EGR_TRF_CNFBOD,"..");
            vecData.add(vecReg);
        }
        objTblModEgrTrf.setData(vecData);
        tblDatEgrTrf.setModel(objTblModEgrTrf); 
        
    }
    
    
    public void cargarItemsEgreso(BigDecimal bgdVal, int intEmpOrg, int intEmpDesTrf){
        ZafHer25_Aju objZafCon64_Aju=new ZafHer25_Aju();
        Vector vecDataEgr=new Vector();
        Vector vecDataIng=new Vector();

        if(intEmpOrg==1){
            if(vecSalNegTuval.size() > 0){
                List lstResItms=null;
                for(int i=0; i<vecSalNegTuval.size();i++){
                    Vector vc=(Vector)vecSalNegTuval.get(i);
                    BigDecimal bgdSal=((BigDecimal)vc.get(0)).abs();
                    int intEmpDes=(Integer)vc.get(1);
                    int intEmp=1;
                    if(intEmpDesTrf==intEmpDes){
                        bgdSalVal=bgdSal;
                        boolean booRes=objZafCon64_Aju.calcularAjuste(conexion,bgdSal,intEmp);
                        if(booRes){
                            lstResItms=objZafCon64_Aju.getListResul();
                            Iterator itLstItms= lstResItms.iterator();

                            Vector vecRegEgr=new Vector();
                            vecRegEgr.add(INT_TBL_ITM_LIN,"");
                            vecRegEgr.add(INT_TBL_ITM_EMP_ORG,-1);
                            vecRegEgr.add(INT_TBL_ITM_COD, "");     
                            String strEtiqueta="";
                            if(intEmpDes==2){
                                strEtiqueta="TUVAL - CASTEK";
                            }else if(intEmpDes==4){
                                strEtiqueta="TUVAL - DIMULTI";
                            }
                            vecRegEgr.add(INT_TBL_ITM_ALT2,"<html><b>"+strEtiqueta+"</b></html>");
                            vecRegEgr.add(INT_TBL_ITM_STK,"");
                            vecRegEgr.add(INT_TBL_ITM_STKDIS,"");
                            vecRegEgr.add(INT_TBL_ITM_CSTUNI, "");
                            vecRegEgr.add(INT_TBL_ITM_CANT, 0);
                            vecRegEgr.add(INT_TBL_ITM_FINAL_DIS, "");
                            vecRegEgr.add(INT_TBL_ITM_FINAL_STK, "");                            
                            vecRegEgr.add(INT_TBL_ITM_CSTTOT, 0);
                            vecRegEgr.add(INT_TBL_ITM_EMP_DES,0 );
                            vecRegEgr.add(INT_TBL_ITM_NOM, "");
                            
                            vecRegEgr.add(INT_TBL_ITM_BOD, 0);
                            vecRegEgr.add(INT_TBL_ITM_PROC,"");
                            //vecDataEgr.add(vecRegEgr);  

                            Vector vecRegIng=new Vector();
                            vecRegIng.add(INT_TBL_ITM_LIN,"");
                            vecRegIng.add(INT_TBL_ITM_EMP_ORG,-1);
                            vecRegIng.add(INT_TBL_ITM_COD, "");     
                            String strEtiquetaIng="";
                            if(intEmpDes==2){
                                strEtiquetaIng="CASTEK - TUVAL";
                            }else if(intEmpDes==4){
                                strEtiquetaIng="DIMULTI - TUVAL";
                            }
                            vecRegIng.add(INT_TBL_ITM_ALT2,"<html><b>"+strEtiqueta+"</b></html>");
                            vecRegIng.add(INT_TBL_ITM_STK,"");
                            vecRegIng.add(INT_TBL_ITM_STKDIS,"");
                            vecRegIng.add(INT_TBL_ITM_CSTUNI, "");
                            vecRegIng.add(INT_TBL_ITM_CANT, 0);
                            vecRegIng.add(INT_TBL_ITM_FINAL_DIS, "");
                            vecRegIng.add(INT_TBL_ITM_FINAL_STK, "");                            
                            vecRegIng.add(INT_TBL_ITM_CSTTOT, 0);
                            vecRegIng.add(INT_TBL_ITM_EMP_DES,0 );
                            vecRegIng.add(INT_TBL_ITM_NOM, "");  
                            //vecDataIng.add(vecRegIng);
                            while(itLstItms.hasNext()){
                                vecRegEgr=new Vector();
                                List lstReg=(List)itLstItms.next();
                                vecRegEgr.add(INT_TBL_ITM_LIN,"");
                                vecRegEgr.add(INT_TBL_ITM_EMP_ORG,1);
                                vecRegEgr.add(INT_TBL_ITM_COD, lstReg.get(0));
                                vecRegEgr.add(INT_TBL_ITM_ALT2, lstReg.get(1)); 
                                vecRegEgr.add(INT_TBL_ITM_STK, lstReg.get(5));
                                vecRegEgr.add(INT_TBL_ITM_STKDIS, lstReg.get(9));
                                vecRegEgr.add(INT_TBL_ITM_CSTUNI, lstReg.get(6));
                                vecRegEgr.add(INT_TBL_ITM_CANT, lstReg.get(8));
                                /*agregado el 3 de abril 2017*/
                                vecRegEgr.add(INT_TBL_ITM_FINAL_DIS, ((BigDecimal)lstReg.get(9)).subtract(((BigDecimal)lstReg.get(8))));
                                vecRegEgr.add(INT_TBL_ITM_FINAL_STK, ((BigDecimal)lstReg.get(5)).subtract(((BigDecimal)lstReg.get(8))));
                                /*agregado el 3 de abril 2017*/
                                
                                vecRegEgr.add(INT_TBL_ITM_CSTTOT, lstReg.get(7));
                                vecRegEgr.add(INT_TBL_ITM_EMP_DES,intEmpDes);
                                vecRegEgr.add(INT_TBL_ITM_NOM, lstReg.get(2));
                                
                                vecRegEgr.add(INT_TBL_ITM_BOD, 15);
                                
                                vecRegEgr.add(INT_TBL_ITM_PROC,"");
                                
                                vecDataEgr.add(vecRegEgr);  

                                vecRegIng=new Vector();
                                vecRegIng.add(INT_TBL_ITM_LIN,"");
                                vecRegIng.add(INT_TBL_ITM_EMP_ORG,intEmpDes);
                                //vecRegIng.add(INT_TBL_ITM_COD, lstReg.get(0));
                                int intItm=getCodigoItem(1, intEmpDes, ((Integer)lstReg.get(0)).toString(), conexion);
                                vecRegIng.add(INT_TBL_ITM_COD,  getCodigoItem(1, intEmpDes, ((Integer)lstReg.get(0)).toString(), conexion));                                
                                vecRegIng.add(INT_TBL_ITM_ALT2, lstReg.get(1)); 
                                double dblStkAct=getStkItem(intEmpDes, intItm, 15, conexion);
                                //vecRegIng.add(INT_TBL_ITM_STK, /*lstReg.get(5)*/getStkItem(intEmpDes, intItm, 15, conexion));
                                vecRegIng.add(INT_TBL_ITM_STK,dblStkAct);
                                double dblStkDis=getStkDisItem(intEmpDes, intItm, 15, conexion);
                                vecRegIng.add(INT_TBL_ITM_STKDIS, /*lstReg.get(9)*/ dblStkDis);
                                vecRegIng.add(INT_TBL_ITM_CSTUNI, lstReg.get(6));
                                vecRegIng.add(INT_TBL_ITM_CANT, lstReg.get(8));
                                //vecRegIng.add(INT_TBL_ITM_FINAL_DIS, ((BigDecimal)lstReg.get(9)).add(((BigDecimal)lstReg.get(8))));
                                vecRegIng.add(INT_TBL_ITM_FINAL_DIS, new BigDecimal(dblStkDis).add(((BigDecimal)lstReg.get(8))));
                                vecRegIng.add(INT_TBL_ITM_FINAL_STK, new BigDecimal(dblStkAct).add(((BigDecimal)lstReg.get(8))));
                                
                                vecRegIng.add(INT_TBL_ITM_CSTTOT, lstReg.get(7));
                                vecRegIng.add(INT_TBL_ITM_EMP_DES,1);
                                vecRegIng.add(INT_TBL_ITM_NOM, lstReg.get(2));
                                
                                vecRegIng.add(INT_TBL_ITM_BOD, 15);
                                
                                vecDataIng.add(vecRegIng);                         

                            }
                            //objTblModItemEgr.setData(vecData);
                            //tblDatItmEgr.setModel(objTblModItemEgr);                    
                        }else{
                            MensajeInf("NO SE PUDO TOTALIZAR EL MONTO DESEADO");
                        }
                    }
                }         
            }
        }     
        
        if(intEmpOrg==2){
            if(vecSalNegCastek.size() > 0){
                List lstResItms=null;
                for(int i=0; i<vecSalNegCastek.size();i++){
                    Vector vc=(Vector)vecSalNegCastek.get(i);
                    BigDecimal bgdSal=((BigDecimal)vc.get(0)).abs();
                    int intEmpDes=(Integer)vc.get(1);
                    int intEmp=2;
                    if(intEmpDesTrf==intEmpDes){
                        bgdSalVal=bgdSal;
                        boolean booRes=objZafCon64_Aju.calcularAjuste(conexion,bgdSal,intEmp);
                        if(booRes){
                            lstResItms=objZafCon64_Aju.getListResul();
                            Iterator itLstItms= lstResItms.iterator();

                            Vector vecRegEgr=new Vector();
                            vecRegEgr.add(INT_TBL_ITM_LIN,"");
                            vecRegEgr.add(INT_TBL_ITM_EMP_ORG,-1);
                            vecRegEgr.add(INT_TBL_ITM_COD, "");     
                            String strEtiqueta="";
                            if(intEmpDes==1){
                                strEtiqueta="CASTEK - TUVAL";
                            }else if(intEmpDes==4){
                                strEtiqueta="CASTEK - DIMULTI";
                            }
                            vecRegEgr.add(INT_TBL_ITM_ALT2,"<html><b>"+strEtiqueta+"</b></html>");
                            vecRegEgr.add(INT_TBL_ITM_STK,"");
                            vecRegEgr.add(INT_TBL_ITM_STKDIS,"");
                            vecRegEgr.add(INT_TBL_ITM_CSTUNI, "");
                            vecRegEgr.add(INT_TBL_ITM_CANT, 0);
                            vecRegEgr.add(INT_TBL_ITM_FINAL_DIS, "");
                            vecRegEgr.add(INT_TBL_ITM_FINAL_STK, "");                            
                            vecRegEgr.add(INT_TBL_ITM_CSTTOT, 0);
                            vecRegEgr.add(INT_TBL_ITM_EMP_DES,0 );
                            vecRegEgr.add(INT_TBL_ITM_NOM, "");
                            
                            /*AGREGADO EL 2017-MAY-08 PARA EVITAR QUE SE VUELVAN A PROCESAR ITEMS DE EGRESO*/
                            vecRegEgr.add(INT_TBL_ITM_BOD, 0);
                            vecRegEgr.add(INT_TBL_ITM_PROC,"");
                            /*AGREGADO EL 2017-MAY-08 PARA EVITAR QUE SE VUELVAN A PROCESAR ITEMS DE EGRESO*/
                            //vecDataEgr.add(vecRegEgr);  

                            Vector vecRegIng=new Vector();
                            vecRegIng.add(INT_TBL_ITM_LIN,"");
                            vecRegIng.add(INT_TBL_ITM_EMP_ORG,-1);
                            vecRegIng.add(INT_TBL_ITM_COD, "");     
                            String strEtiquetaIng="";
                            if(intEmpDes==1){
                                strEtiquetaIng="TUVAL - CASTEK";
                            }else if(intEmpDes==4){
                                strEtiquetaIng="DIMULTI - CASTEK";
                            }
                            vecRegIng.add(INT_TBL_ITM_ALT2,"<html><b>"+strEtiqueta+"</b></html>");
                            vecRegIng.add(INT_TBL_ITM_STK,"");
                            vecRegIng.add(INT_TBL_ITM_STKDIS,"");
                            vecRegIng.add(INT_TBL_ITM_CSTUNI, "");
                            vecRegIng.add(INT_TBL_ITM_CANT, 0);
                            vecRegIng.add(INT_TBL_ITM_FINAL_DIS, "");
                            vecRegIng.add(INT_TBL_ITM_FINAL_STK, "");                            
                            vecRegIng.add(INT_TBL_ITM_CSTTOT, 0);
                            vecRegIng.add(INT_TBL_ITM_EMP_DES,0 );
                            vecRegIng.add(INT_TBL_ITM_NOM, "");   
                            //vecDataIng.add(vecRegIng);
                            while(itLstItms.hasNext()){
                                vecRegEgr=new Vector();
                                List lstReg=(List)itLstItms.next();
                                vecRegEgr.add(INT_TBL_ITM_LIN,"");
                                vecRegEgr.add(INT_TBL_ITM_EMP_ORG,2);
                                vecRegEgr.add(INT_TBL_ITM_COD, lstReg.get(0));
                                vecRegEgr.add(INT_TBL_ITM_ALT2, lstReg.get(1)); 
                                vecRegEgr.add(INT_TBL_ITM_STK, lstReg.get(5));
                                vecRegEgr.add(INT_TBL_ITM_STKDIS, lstReg.get(9));
                                vecRegEgr.add(INT_TBL_ITM_CSTUNI, lstReg.get(6));
                                vecRegEgr.add(INT_TBL_ITM_CANT, lstReg.get(8));
                                vecRegEgr.add(INT_TBL_ITM_FINAL_DIS, ((BigDecimal)lstReg.get(9)).subtract(((BigDecimal)lstReg.get(8))));
                                vecRegEgr.add(INT_TBL_ITM_FINAL_STK, ((BigDecimal)lstReg.get(5)).subtract(((BigDecimal)lstReg.get(8))));                                
                                vecRegEgr.add(INT_TBL_ITM_CSTTOT, lstReg.get(7));
                                vecRegEgr.add(INT_TBL_ITM_EMP_DES,intEmpDes);
                                vecRegEgr.add(INT_TBL_ITM_NOM, lstReg.get(2));
                                vecRegEgr.add(INT_TBL_ITM_BOD, 15);     
                                /*AGREGADO EL 2017-MAY-08 PARA EVITAR QUE SE VUELVAN A PROCESAR ITEMS DE EGRESO*/
                                vecRegEgr.add(INT_TBL_ITM_PROC,"");
                                /*AGREGADO EL 2017-MAY-08 PARA EVITAR QUE SE VUELVAN A PROCESAR ITEMS DE EGRESO*/
                                vecDataEgr.add(vecRegEgr);  

                                vecRegIng=new Vector();
                                vecRegIng.add(INT_TBL_ITM_LIN,"");
                                vecRegIng.add(INT_TBL_ITM_EMP_ORG,intEmpDes);
                                int intItm=getCodigoItem(2, intEmpDes, ((Integer)lstReg.get(0)).toString(), conexion);
                                vecRegIng.add(INT_TBL_ITM_COD, /*getCodigoItem(2, intEmpDes, ((Integer)lstReg.get(0)).toString(), conexion)*/intItm);
                                vecRegIng.add(INT_TBL_ITM_ALT2, lstReg.get(1)); 
                                double dblStkAct=getStkItem(intEmpDes, intItm, 15, conexion);
                                //vecRegIng.add(INT_TBL_ITM_STK, /*lstReg.get(5)*/getStkItem(intEmpDes, intItm, 15, conexion));
                                vecRegIng.add(INT_TBL_ITM_STK, dblStkAct);
                                double dblStkDis=getStkDisItem(intEmpDes, intItm, 15, conexion);
                                //vecRegIng.add(INT_TBL_ITM_STKDIS, /*lstReg.get(9)*/getStkDisItem(intEmpDes, intItm, 15, conexion));
                                vecRegIng.add(INT_TBL_ITM_STKDIS, dblStkDis);
                                vecRegIng.add(INT_TBL_ITM_CSTUNI, lstReg.get(6));
                                vecRegIng.add(INT_TBL_ITM_CANT, lstReg.get(8));
                                vecRegIng.add(INT_TBL_ITM_FINAL_DIS, new BigDecimal(dblStkDis).add(((BigDecimal)lstReg.get(8))));
                                vecRegIng.add(INT_TBL_ITM_FINAL_STK, new BigDecimal(dblStkAct).add(((BigDecimal)lstReg.get(8))));                                
                                vecRegIng.add(INT_TBL_ITM_CSTTOT, lstReg.get(7));
                                vecRegIng.add(INT_TBL_ITM_EMP_DES,2);
                                vecRegIng.add(INT_TBL_ITM_NOM, lstReg.get(2));
                                vecRegIng.add(INT_TBL_ITM_BOD, 15);
                                vecDataIng.add(vecRegIng);                         

                            }
                            //objTblModItemEgr.setData(vecData);
                            //tblDatItmEgr.setModel(objTblModItemEgr);                    
                        }else{
                            MensajeInf("NO SE PUDO TOTALIZAR EL MONTO DESEADO");
                        }
                    }
                }         
            }
        }
        
        if(intEmpOrg==4){
            if(vecSalNegDimulti.size() > 0){
                List lstResItms=null;
                for(int i=0; i<vecSalNegDimulti.size();i++){
                    Vector vc=(Vector)vecSalNegDimulti.get(i);
                    BigDecimal bgdSal=((BigDecimal)vc.get(0)).abs();
                    int intEmpDes=(Integer)vc.get(1);
                    int intEmp=4;
                    //boolean booRes=objZafCon64_Aju.calcularAjuste(conexion,bgdSal,intEmp,lstResItms);
                    if(intEmpDesTrf==intEmpDes){
                        bgdSalVal=bgdSal;
                        boolean booRes=objZafCon64_Aju.calcularAjuste(conexion,bgdSal,intEmp);
                        if(booRes){
                            lstResItms=objZafCon64_Aju.getListResul();
                            Iterator itLstItms= lstResItms.iterator();

                            //Vector vecData=new Vector();

                            Vector vecRegEgr=new Vector();
                            vecRegEgr.add(INT_TBL_ITM_LIN,"");
                            vecRegEgr.add(INT_TBL_ITM_EMP_ORG,-1);
                            vecRegEgr.add(INT_TBL_ITM_COD,"");
                            String strEtiqueta="";
                            if(intEmpDes==1){
                                strEtiqueta="DIMULTI - TUVAL";
                            }else if(intEmpDes==2){
                                strEtiqueta="DIMULTI - CASTEK";
                            }

                            vecRegEgr.add(INT_TBL_ITM_ALT2,"<html><b>"+strEtiqueta+"</b></html>");
                            vecRegEgr.add(INT_TBL_ITM_STK,"");
                            vecRegEgr.add(INT_TBL_ITM_STKDIS,"");
                            vecRegEgr.add(INT_TBL_ITM_CSTUNI, "");
                            vecRegEgr.add(INT_TBL_ITM_CANT, 0);
                            vecRegEgr.add(INT_TBL_ITM_FINAL_DIS, "");
                            vecRegEgr.add(INT_TBL_ITM_FINAL_STK, "");                            
                            vecRegEgr.add(INT_TBL_ITM_CSTTOT, 0);
                            vecRegEgr.add(INT_TBL_ITM_EMP_DES,0 );
                            vecRegEgr.add(INT_TBL_ITM_NOM, "");
                            
                            /*AGREGADO EL 2017-MAY-08 PARA EVITAR QUE SE VUELVAN A PROCESAR ITEMS DE EGRESO*/
                            vecRegEgr.add(INT_TBL_ITM_BOD, 0);
                            vecRegEgr.add(INT_TBL_ITM_PROC,"");
                            /*AGREGADO EL 2017-MAY-08 PARA EVITAR QUE SE VUELVAN A PROCESAR ITEMS DE EGRESO*/
                            
                            
                            //vecDataEgr.add(vecRegEgr);

                            Vector vecRegIng=new Vector();
                            vecRegIng.add(INT_TBL_ITM_LIN,"");
                            vecRegIng.add(INT_TBL_ITM_EMP_ORG,-1);
                            vecRegIng.add(INT_TBL_ITM_COD, "");     
                            String strEtiquetaIng="";
                            if(intEmpDes==1){
                                strEtiquetaIng="TUVAL - DIMULTI";
                            }else if(intEmpDes==2){
                                strEtiquetaIng="CASTEK - DIMULTI";
                            }
                            vecRegIng.add(INT_TBL_ITM_ALT2,"<html><b>"+strEtiqueta+"</b></html>");
                            vecRegIng.add(INT_TBL_ITM_STK,"");
                            vecRegIng.add(INT_TBL_ITM_STKDIS,"");
                            vecRegIng.add(INT_TBL_ITM_CSTUNI, "");
                            vecRegIng.add(INT_TBL_ITM_CANT, 0);
                            vecRegIng.add(INT_TBL_ITM_FINAL_DIS, "");
                            vecRegIng.add(INT_TBL_ITM_FINAL_STK, "");                            
                            vecRegIng.add(INT_TBL_ITM_CSTTOT, 0);
                            vecRegIng.add(INT_TBL_ITM_EMP_DES,0 );
                            vecRegIng.add(INT_TBL_ITM_NOM, "");   
                            //vecDataIng.add(vecRegIng);                    

                            while(itLstItms.hasNext()){
                                vecRegEgr=new Vector();
                                List lstReg=(List)itLstItms.next();
                                vecRegEgr.add(INT_TBL_ITM_LIN,"");
                                vecRegEgr.add(INT_TBL_ITM_EMP_ORG,4);
                                vecRegEgr.add(INT_TBL_ITM_COD,lstReg.get(0));                        
                                vecRegEgr.add(INT_TBL_ITM_ALT2, lstReg.get(1)); 
                                vecRegEgr.add(INT_TBL_ITM_STK, lstReg.get(5));
                                vecRegEgr.add(INT_TBL_ITM_STKDIS, lstReg.get(9));
                                vecRegEgr.add(INT_TBL_ITM_CSTUNI, lstReg.get(6));
                                vecRegEgr.add(INT_TBL_ITM_CANT, lstReg.get(8));
                                vecRegEgr.add(INT_TBL_ITM_FINAL_DIS, ((BigDecimal)lstReg.get(9)).subtract(((BigDecimal)lstReg.get(8))));
                                vecRegEgr.add(INT_TBL_ITM_FINAL_STK, ((BigDecimal)lstReg.get(5)).subtract(((BigDecimal)lstReg.get(8))));                                
                                vecRegEgr.add(INT_TBL_ITM_CSTTOT, lstReg.get(7));
                                vecRegEgr.add(INT_TBL_ITM_EMP_DES,intEmpDes);
                                vecRegEgr.add(INT_TBL_ITM_NOM, lstReg.get(2));
                                vecRegEgr.add(INT_TBL_ITM_BOD, 15);
                                /*AGREGADO EL 2017-MAY-08 PARA EVITAR QUE SE VUELVAN A PROCESAR ITEMS DE EGRESO*/
                                vecRegEgr.add(INT_TBL_ITM_PROC,"");
                                /*AGREGADO EL 2017-MAY-08 PARA EVITAR QUE SE VUELVAN A PROCESAR ITEMS DE EGRESO*/
                                vecDataEgr.add(vecRegEgr);  

                                vecRegIng=new Vector();
                                vecRegIng.add(INT_TBL_ITM_LIN,"");
                                vecRegIng.add(INT_TBL_ITM_EMP_ORG,intEmpDes);
                                int intItm=getCodigoItem(4, intEmpDes, ((Integer)lstReg.get(0)).toString(), conexion);
                                vecRegIng.add(INT_TBL_ITM_COD,  /*getCodigoItem(4, intEmpDes, ((Integer)lstReg.get(0)).toString(), conexion)*/intItm);
                                vecRegIng.add(INT_TBL_ITM_ALT2, lstReg.get(1)); 
                                double dblStkAct=getStkItem(intEmpDes, intItm, 15, conexion);
                                //vecRegIng.add(INT_TBL_ITM_STK, /*lstReg.get(5)*/getStkItem(intEmpDes, intItm, 15, conexion));
                                vecRegIng.add(INT_TBL_ITM_STK, dblStkAct);
                                double dblStkDis=getStkDisItem(intEmpDes, intItm, 15, conexion);
                                vecRegIng.add(INT_TBL_ITM_STKDIS, /*lstReg.get(9)*/dblStkDis);
                                vecRegIng.add(INT_TBL_ITM_CSTUNI, lstReg.get(6));
                                vecRegIng.add(INT_TBL_ITM_CANT, lstReg.get(8));
                                vecRegIng.add(INT_TBL_ITM_FINAL_DIS, new BigDecimal(dblStkDis).add(((BigDecimal)lstReg.get(8))));
                                vecRegIng.add(INT_TBL_ITM_FINAL_STK, new BigDecimal(dblStkAct).add(((BigDecimal)lstReg.get(8))));                                
                                vecRegIng.add(INT_TBL_ITM_CSTTOT, lstReg.get(7));
                                vecRegIng.add(INT_TBL_ITM_EMP_DES,4);
                                vecRegIng.add(INT_TBL_ITM_NOM, lstReg.get(2));
                                vecRegIng.add(INT_TBL_ITM_BOD, 15);
                                vecDataIng.add(vecRegIng);                         
                            }
                        }else{
                            MensajeInf("NO SE PUDO TOTALIZAR EL MONTO DESEADO");
                        }
                    }
                }
            } 
        }
        
        objTblModItemEgr.setData(vecDataEgr);
        tblDatItmEgr.setModel(objTblModItemEgr);                    
        
        objTblModItemIng.setData(vecDataIng);
        tblDatItmIng.setModel(objTblModItemIng);   
        
        objTblTotItm.calcularTotales();
        objTblTotItmIng.calcularTotales();
        
    }
    
    public boolean cargarItemsEgresoxBod(BigDecimal bgdVal, int intEmpOrg, int intEmpDesTrf, int intCodBodOrigen){
    //public void cargarItemsEgresoxBod(BigDecimal bgdVal, int intEmpOrg, int intEmpDesTrf, int intCodBodOrigen){
        ZafHer25_Aju objZafCon64_Aju=new ZafHer25_Aju();
        Vector vecDataEgr=new Vector();
        Vector vecDataIng=new Vector();
        boolean booResOri=false;

        if(intEmpOrg==1){
            if(vecSalNegTuval.size() > 0){
                List lstResItms=null;
                for(int i=0; i<vecSalNegTuval.size();i++){
                    Vector vc=(Vector)vecSalNegTuval.get(i);
                    //BigDecimal bgdSal=((BigDecimal)vc.get(0)).abs();
                    int intEmpDes=(Integer)vc.get(1);
                    int intEmp=1;
                    if(intEmpDesTrf==intEmpDes){
                        //bgdSalVal=bgdSal;
                        bgdSalVal=bgdVal;
                        //boolean booRes=objZafCon64_Aju.calcularAjuste(conexion,bgdSal,intEmp);
                        boolean booRes=objZafCon64_Aju.calcularAjuste(conexion,bgdVal,intEmp, intCodBodOrigen);
                        booResOri=booRes;
                        if(booRes){
                            lstResItms=objZafCon64_Aju.getListResul();
                            Iterator itLstItms= lstResItms.iterator();

                            Vector vecRegEgr=new Vector();
                            Vector vecRegIng=new Vector();
                            

                            while(itLstItms.hasNext()){
                                vecRegEgr=new Vector();
                                List lstReg=(List)itLstItms.next();
                                vecRegEgr.add(INT_TBL_ITM_LIN,"");
                                vecRegEgr.add(INT_TBL_ITM_EMP_ORG,1);
                                vecRegEgr.add(INT_TBL_ITM_COD, lstReg.get(0));
                                vecRegEgr.add(INT_TBL_ITM_ALT2, lstReg.get(1)); 
                                vecRegEgr.add(INT_TBL_ITM_STK, lstReg.get(5));
                                vecRegEgr.add(INT_TBL_ITM_STKDIS, lstReg.get(9));
                                vecRegEgr.add(INT_TBL_ITM_CSTUNI, lstReg.get(6));
                                vecRegEgr.add(INT_TBL_ITM_CANT, lstReg.get(8));
                                /*agregado el 3 de abril 2017*/
                                vecRegEgr.add(INT_TBL_ITM_FINAL_DIS, ((BigDecimal)lstReg.get(9)).subtract(((BigDecimal)lstReg.get(8))));
                                vecRegEgr.add(INT_TBL_ITM_FINAL_STK, ((BigDecimal)lstReg.get(5)).subtract(((BigDecimal)lstReg.get(8))));
                                /*agregado el 3 de abril 2017*/
                                
                                vecRegEgr.add(INT_TBL_ITM_CSTTOT, lstReg.get(7));
                                vecRegEgr.add(INT_TBL_ITM_EMP_DES,intEmpDes);
                                vecRegEgr.add(INT_TBL_ITM_NOM, lstReg.get(2));
                                
                                /*modificado 2017-04-17 para realizar transferencias entre diferentes bodegas de inmaconsa*/
                                vecRegEgr.add(INT_TBL_ITM_BOD, intCodBodOrigen);
                                vecRegEgr.add(INT_TBL_ITM_PROC,"");
                                /*modificado 2017-04-17 para realizar transferencias entre diferentes bodegas de inmaconsa*/
                                
                                
                                vecDataEgr.add(vecRegEgr);  

                                vecRegIng=new Vector();
                                vecRegIng.add(INT_TBL_ITM_LIN,"");
                                vecRegIng.add(INT_TBL_ITM_EMP_ORG,intEmpDes);
                                //vecRegIng.add(INT_TBL_ITM_COD, lstReg.get(0));
                                int intItm=getCodigoItem(1, intEmpDes, ((Integer)lstReg.get(0)).toString(), conexion);
                                vecRegIng.add(INT_TBL_ITM_COD,  getCodigoItem(1, intEmpDes, ((Integer)lstReg.get(0)).toString(), conexion));                                
                                vecRegIng.add(INT_TBL_ITM_ALT2, lstReg.get(1)); 
                                int intBodDestino=getBodDestino(intEmpOrg, intCodBodOrigen, intEmpDesTrf, conexion);
                                double dblStkAct=getStkItem(intEmpDes, intItm,intBodDestino , conexion);
                                //vecRegIng.add(INT_TBL_ITM_STK, /*lstReg.get(5)*/getStkItem(intEmpDes, intItm, 15, conexion));
                                vecRegIng.add(INT_TBL_ITM_STK,dblStkAct);
                                double dblStkDis=getStkDisItem(intEmpDes, intItm, intBodDestino, conexion);
                                vecRegIng.add(INT_TBL_ITM_STKDIS, /*lstReg.get(9)*/ dblStkDis);
                                vecRegIng.add(INT_TBL_ITM_CSTUNI, lstReg.get(6));
                                vecRegIng.add(INT_TBL_ITM_CANT, lstReg.get(8));
                                //vecRegIng.add(INT_TBL_ITM_FINAL_DIS, ((BigDecimal)lstReg.get(9)).add(((BigDecimal)lstReg.get(8))));
                                vecRegIng.add(INT_TBL_ITM_FINAL_DIS, new BigDecimal(dblStkDis).add(((BigDecimal)lstReg.get(8))));
                                vecRegIng.add(INT_TBL_ITM_FINAL_STK, new BigDecimal(dblStkAct).add(((BigDecimal)lstReg.get(8))));
                                
                                vecRegIng.add(INT_TBL_ITM_CSTTOT, lstReg.get(7));
                                vecRegIng.add(INT_TBL_ITM_EMP_DES,1);
                                vecRegIng.add(INT_TBL_ITM_NOM, lstReg.get(2));
                                
                                /*modificado 2017-04-17 para realizar transferencias entre diferentes bodegas de inmaconsa*/
                                vecRegIng.add(INT_TBL_ITM_BOD, intBodDestino);
                                
                                /*modificado 2017-04-17 para realizar transferencias entre diferentes bodegas de inmaconsa*/
                                
                                
                                vecDataIng.add(vecRegIng);                         

                            }
                            //objTblModItemEgr.setData(vecData);
                            //tblDatItmEgr.setModel(objTblModItemEgr);                    
                        }else{
                            MensajeInf("NO SE PUDO TOTALIZAR EL MONTO DESEADO");
                        }
                    }
                }         
            }
        }     
        
        if(intEmpOrg==2){
            if(vecSalNegCastek.size() > 0){
                List lstResItms=null;
                for(int i=0; i<vecSalNegCastek.size();i++){
                    Vector vc=(Vector)vecSalNegCastek.get(i);
                    //BigDecimal bgdSal=((BigDecimal)vc.get(0)).abs();
                    int intEmpDes=(Integer)vc.get(1);
                    int intEmp=2;
                    if(intEmpDesTrf==intEmpDes){
                        //bgdSalVal=bgdSal;
                        bgdSalVal=bgdVal;
                        //boolean booRes=objZafCon64_Aju.calcularAjuste(conexion,bgdSal,intEmp);
                        boolean booRes=objZafCon64_Aju.calcularAjuste(conexion,bgdVal,intEmp, intCodBodOrigen);
                        booResOri=booRes;
                        if(booRes){
                            lstResItms=objZafCon64_Aju.getListResul();
                            Iterator itLstItms= lstResItms.iterator();

                            Vector vecRegEgr=new Vector();
                            Vector vecRegIng=new Vector();

                            while(itLstItms.hasNext()){
                                vecRegEgr=new Vector();
                                List lstReg=(List)itLstItms.next();
                                vecRegEgr.add(INT_TBL_ITM_LIN,"");
                                vecRegEgr.add(INT_TBL_ITM_EMP_ORG,2);
                                vecRegEgr.add(INT_TBL_ITM_COD, lstReg.get(0));
                                vecRegEgr.add(INT_TBL_ITM_ALT2, lstReg.get(1)); 
                                vecRegEgr.add(INT_TBL_ITM_STK, lstReg.get(5));
                                vecRegEgr.add(INT_TBL_ITM_STKDIS, lstReg.get(9));
                                vecRegEgr.add(INT_TBL_ITM_CSTUNI, lstReg.get(6));
                                vecRegEgr.add(INT_TBL_ITM_CANT, lstReg.get(8));
                                vecRegEgr.add(INT_TBL_ITM_FINAL_DIS, ((BigDecimal)lstReg.get(9)).subtract(((BigDecimal)lstReg.get(8))));
                                vecRegEgr.add(INT_TBL_ITM_FINAL_STK, ((BigDecimal)lstReg.get(5)).subtract(((BigDecimal)lstReg.get(8))));                                
                                vecRegEgr.add(INT_TBL_ITM_CSTTOT, lstReg.get(7));
                                vecRegEgr.add(INT_TBL_ITM_EMP_DES,intEmpDes);
                                vecRegEgr.add(INT_TBL_ITM_NOM, lstReg.get(2));
                                
                                /*modificado 2017-04-17 para realizar transferencias entre diferentes bodegas de inmaconsa*/
                                vecRegEgr.add(INT_TBL_ITM_BOD, intCodBodOrigen);
                                vecRegEgr.add(INT_TBL_ITM_PROC,"");
                                /*modificado 2017-04-17 para realizar transferencias entre diferentes bodegas de inmaconsa*/

                                
                                vecDataEgr.add(vecRegEgr);  

                                vecRegIng=new Vector();
                                vecRegIng.add(INT_TBL_ITM_LIN,"");
                                vecRegIng.add(INT_TBL_ITM_EMP_ORG,intEmpDes);
                                int intItm=getCodigoItem(2, intEmpDes, ((Integer)lstReg.get(0)).toString(), conexion);
                                vecRegIng.add(INT_TBL_ITM_COD, /*getCodigoItem(2, intEmpDes, ((Integer)lstReg.get(0)).toString(), conexion)*/intItm);
                                vecRegIng.add(INT_TBL_ITM_ALT2, lstReg.get(1)); 
                                
                                int intBodDestino=getBodDestino(intEmpOrg, intCodBodOrigen, intEmpDesTrf, conexion);
                                double dblStkAct=getStkItem(intEmpDes, intItm,intBodDestino , conexion);
                                //vecRegIng.add(INT_TBL_ITM_STK, /*lstReg.get(5)*/getStkItem(intEmpDes, intItm, 15, conexion));
                                vecRegIng.add(INT_TBL_ITM_STK, dblStkAct);
                                double dblStkDis=getStkDisItem(intEmpDes, intItm, intBodDestino, conexion);
                                //vecRegIng.add(INT_TBL_ITM_STKDIS, /*lstReg.get(9)*/getStkDisItem(intEmpDes, intItm, 15, conexion));
                                vecRegIng.add(INT_TBL_ITM_STKDIS, dblStkDis);
                                vecRegIng.add(INT_TBL_ITM_CSTUNI, lstReg.get(6));
                                vecRegIng.add(INT_TBL_ITM_CANT, lstReg.get(8));
                                vecRegIng.add(INT_TBL_ITM_FINAL_DIS, new BigDecimal(dblStkDis).add(((BigDecimal)lstReg.get(8))));
                                vecRegIng.add(INT_TBL_ITM_FINAL_STK, new BigDecimal(dblStkAct).add(((BigDecimal)lstReg.get(8))));                                
                                vecRegIng.add(INT_TBL_ITM_CSTTOT, lstReg.get(7));
                                vecRegIng.add(INT_TBL_ITM_EMP_DES,2);
                                vecRegIng.add(INT_TBL_ITM_NOM, lstReg.get(2));
                                
                                /*modificado 2017-04-17 para realizar transferencias entre diferentes bodegas de inmaconsa*/
                                vecRegIng.add(INT_TBL_ITM_BOD, intBodDestino);
                                /*modificado 2017-04-17 para realizar transferencias entre diferentes bodegas de inmaconsa*/

                                
                                vecDataIng.add(vecRegIng);                         

                            }
                            //objTblModItemEgr.setData(vecData);
                            //tblDatItmEgr.setModel(objTblModItemEgr);                    
                        }else{
                            MensajeInf("NO SE PUDO TOTALIZAR EL MONTO DESEADO");
                        }
                    }
                }         
            }
        }
        
        if(intEmpOrg==4){
            if(vecSalNegDimulti.size() > 0){
                List lstResItms=null;
                for(int i=0; i<vecSalNegDimulti.size();i++){
                    Vector vc=(Vector)vecSalNegDimulti.get(i);
                    //BigDecimal bgdSal=((BigDecimal)vc.get(0)).abs();
                    int intEmpDes=(Integer)vc.get(1);
                    int intEmp=4;
                    //boolean booRes=objZafCon64_Aju.calcularAjuste(conexion,bgdSal,intEmp,lstResItms);
                    if(intEmpDesTrf==intEmpDes){
                        //bgdSalVal=bgdSal;
                        bgdSalVal=bgdVal;
                        //boolean booRes=objZafCon64_Aju.calcularAjuste(conexion,bgdSal,intEmp);
                        //boolean booRes=objZafCon64_Aju.calcularAjuste(conexion,bgdVal,intEmp);
                        boolean booRes=objZafCon64_Aju.calcularAjuste(conexion,bgdVal,intEmp, intCodBodOrigen);
                        booResOri=booRes;
                        if(booRes){
                            lstResItms=objZafCon64_Aju.getListResul();
                            Iterator itLstItms= lstResItms.iterator();

                            //Vector vecData=new Vector();

                            Vector vecRegEgr=new Vector();
                            Vector vecRegIng=new Vector();

                            while(itLstItms.hasNext()){
                                vecRegEgr=new Vector();
                                List lstReg=(List)itLstItms.next();
                                vecRegEgr.add(INT_TBL_ITM_LIN,"");
                                vecRegEgr.add(INT_TBL_ITM_EMP_ORG,4);
                                vecRegEgr.add(INT_TBL_ITM_COD,lstReg.get(0));                        
                                vecRegEgr.add(INT_TBL_ITM_ALT2, lstReg.get(1)); 
                                vecRegEgr.add(INT_TBL_ITM_STK, lstReg.get(5));
                                vecRegEgr.add(INT_TBL_ITM_STKDIS, lstReg.get(9));
                                vecRegEgr.add(INT_TBL_ITM_CSTUNI, lstReg.get(6));
                                vecRegEgr.add(INT_TBL_ITM_CANT, lstReg.get(8));
                                vecRegEgr.add(INT_TBL_ITM_FINAL_DIS, ((BigDecimal)lstReg.get(9)).subtract(((BigDecimal)lstReg.get(8))));
                                vecRegEgr.add(INT_TBL_ITM_FINAL_STK, ((BigDecimal)lstReg.get(5)).subtract(((BigDecimal)lstReg.get(8))));                                
                                vecRegEgr.add(INT_TBL_ITM_CSTTOT, lstReg.get(7));
                                vecRegEgr.add(INT_TBL_ITM_EMP_DES,intEmpDes);
                                vecRegEgr.add(INT_TBL_ITM_NOM, lstReg.get(2));
                                
                                /*modificado 2017-04-17 para realizar transferencias entre diferentes bodegas de inmaconsa*/
                                vecRegEgr.add(INT_TBL_ITM_BOD, intCodBodOrigen);
                                vecRegEgr.add(INT_TBL_ITM_PROC,"");
                                /*modificado 2017-04-17 para realizar transferencias entre diferentes bodegas de inmaconsa*/                                
                                
                                vecDataEgr.add(vecRegEgr);  

                                vecRegIng=new Vector();
                                vecRegIng.add(INT_TBL_ITM_LIN,"");
                                vecRegIng.add(INT_TBL_ITM_EMP_ORG,intEmpDes);
                                int intItm=getCodigoItem(4, intEmpDes, ((Integer)lstReg.get(0)).toString(), conexion);
                                vecRegIng.add(INT_TBL_ITM_COD,  /*getCodigoItem(4, intEmpDes, ((Integer)lstReg.get(0)).toString(), conexion)*/intItm);
                                vecRegIng.add(INT_TBL_ITM_ALT2, lstReg.get(1)); 
                                
                                int intBodDestino=getBodDestino(intEmpOrg, intCodBodOrigen, intEmpDesTrf, conexion);
                                double dblStkAct=getStkItem(intEmpDes, intItm,intBodDestino , conexion);                                

                                //vecRegIng.add(INT_TBL_ITM_STK, /*lstReg.get(5)*/getStkItem(intEmpDes, intItm, 15, conexion));
                                vecRegIng.add(INT_TBL_ITM_STK, dblStkAct);
                                double dblStkDis=getStkDisItem(intEmpDes, intItm, intBodDestino, conexion);
                                vecRegIng.add(INT_TBL_ITM_STKDIS, /*lstReg.get(9)*/dblStkDis);
                                vecRegIng.add(INT_TBL_ITM_CSTUNI, lstReg.get(6));
                                vecRegIng.add(INT_TBL_ITM_CANT, lstReg.get(8));
                                vecRegIng.add(INT_TBL_ITM_FINAL_DIS, new BigDecimal(dblStkDis).add(((BigDecimal)lstReg.get(8))));
                                vecRegIng.add(INT_TBL_ITM_FINAL_STK, new BigDecimal(dblStkAct).add(((BigDecimal)lstReg.get(8))));                                
                                vecRegIng.add(INT_TBL_ITM_CSTTOT, lstReg.get(7));
                                vecRegIng.add(INT_TBL_ITM_EMP_DES,4);
                                vecRegIng.add(INT_TBL_ITM_NOM, lstReg.get(2));
                                
                                /*modificado 2017-04-17 para realizar transferencias entre diferentes bodegas de inmaconsa*/
                                vecRegIng.add(INT_TBL_ITM_BOD, intBodDestino);
                                /*modificado 2017-04-17 para realizar transferencias entre diferentes bodegas de inmaconsa*/
                                
                                
                                vecDataIng.add(vecRegIng);                         
                            }
                        }else{
                            MensajeInf("NO SE PUDO TOTALIZAR EL MONTO DESEADO");
                        }
                    }
                }
            } 
        }
        
        objTblModItemEgr.setData(vecDataEgr);
        tblDatItmEgr.setModel(objTblModItemEgr);                    
        
        objTblModItemIng.setData(vecDataIng);
        tblDatItmIng.setModel(objTblModItemIng);   
        
        objTblTotItm.calcularTotales();
        objTblTotItmIng.calcularTotales();
        
        return booResOri;
    }    
    
    
    
    private int getBodDestino(int intEmpOrg, int intBodOrg, int intEmpDes, Connection cnx){
        int intBodDes=0;
        java.sql.Statement stmLoc; 
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;        
        String strSQL="";
        try{
            //conLoc = conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (cnx!=null)
            {
                stmLoc=cnx.createStatement();
                strSQL="";
                strSQL+=" select co_bod\n" +
                        " from tbr_bodempbodgrp\n" +
                        " where co_empgrp=0\n" +
                        " and co_bodgrp=(select co_bodgrp\n" +
                        "	       from tbr_bodempbodgrp \n" +
                        "	       where co_emp="+intEmpOrg+ "\n" +
                        "	       and co_bod="+intBodOrg+" )\n" +
                        " and co_emp="+intEmpDes;
//                System.out.println("getCodigoTipoDocumentoSolicitudesTransferenciaAutomatica " +strSQL );
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intBodDes=rstLoc.getInt("co_bod");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                
            }
        }
        catch (java.sql.SQLException e){         
            objUti.mostrarMsgErr_F1(this, e);      
        }
        catch (Exception e){         
            objUti.mostrarMsgErr_F1(this, e);       
        }
        return intBodDes;    
    
    }
    
    
    private int getCodigoItem(int intCodEmpOrg, int intCodEmpDes, String strCodItm, Connection cnx){
        java.sql.Statement stmLoc; 
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        int intCodItm=0;
        String strSQL="";
        try{
            //conLoc = conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (cnx!=null)
            {
                stmLoc=cnx.createStatement();
                strSQL="";
                strSQL+=" SELECT co_itm \n";
                strSQL+=" FROM tbm_equInv as x1 \n";
                strSQL+=" WHERE x1.co_itmMae = ( \n";
                strSQL+="                        select co_itmMae  \n";
                strSQL+="                        from tbm_Equinv as a1 \n";
                strSQL+="                        where co_emp="+intCodEmpOrg+" and co_itm="+strCodItm+")  \n";
                strSQL+=" and x1.co_emp="+intCodEmpDes+" \n";
//                System.out.println("getCodigoTipoDocumentoSolicitudesTransferenciaAutomatica " +strSQL );
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCodItm=rstLoc.getInt("co_itm");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                
            }
        }
        catch (java.sql.SQLException e){         
            objUti.mostrarMsgErr_F1(this, e);      
        }
        catch (Exception e){         
            objUti.mostrarMsgErr_F1(this, e);       
        }
        return intCodItm;
    }
    
    
    private double getStkItem(int intCodEmp, int intCodItm, int intCodBod,Connection cnx){
        java.sql.Statement stmStk; 
        java.sql.ResultSet rstStk;
        java.sql.Connection conLoc;
        double dblStk=0.0;
        String strSQL="";
        try{
            //conLoc = conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (cnx!=null)
            {
                stmStk=cnx.createStatement();
                strSQL="";
                strSQL+=" SELECT x1.nd_stkact \n";
                strSQL+=" FROM tbm_invbod as x1 \n";
                strSQL+=" WHERE x1.co_itm ="+intCodItm+" \n";
                strSQL+=" AND x1.co_emp="+intCodEmp+" and co_bod="+intCodBod+" \n";
//                System.out.println("getCodigoTipoDocumentoSolicitudesTransferenciaAutomatica " +strSQL );
                rstStk=stmStk.executeQuery(strSQL);
                if(rstStk.next()){
                    dblStk=rstStk.getDouble("nd_stkact");
                }
                rstStk.close();
                rstStk=null;
                stmStk.close();
                stmStk=null;
                
            }
        }
        catch (java.sql.SQLException e){         
            objUti.mostrarMsgErr_F1(this, e);      
        }
        catch (Exception e){         
            objUti.mostrarMsgErr_F1(this, e);       
        }
        System.out.println("stock "+dblStk);
        return dblStk;
    }    
    
    
    private double getStkDisItem(int intCodEmp, int intCodItm, int intCodBod,Connection cnx){
        java.sql.Statement stmStk; 
        java.sql.ResultSet rstStk;
        java.sql.Connection conLoc;
        double dblStk=0.0;
        String strSQL="";
        try{
            //conLoc = conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (cnx!=null)
            {
                stmStk=cnx.createStatement();
                strSQL="";
                strSQL+=" SELECT x1.nd_candis \n";
                strSQL+=" FROM tbm_invbod as x1 \n";
                strSQL+=" WHERE x1.co_itm ="+intCodItm+" \n";
                strSQL+=" AND x1.co_emp="+intCodEmp+" and co_bod="+intCodBod+" \n";
//                System.out.println("getCodigoTipoDocumentoSolicitudesTransferenciaAutomatica " +strSQL );
                rstStk=stmStk.executeQuery(strSQL);
                if(rstStk.next()){
                    dblStk=rstStk.getDouble("nd_candis");
                }
                rstStk.close();
                rstStk=null;
                stmStk.close();
                stmStk=null;
                
            }
        }
        catch (java.sql.SQLException e){         
            objUti.mostrarMsgErr_F1(this, e);      
        }
        catch (Exception e){         
            objUti.mostrarMsgErr_F1(this, e);       
        }
        return dblStk;
    }    

    private void setearRegistrosProcesados(){
    
        for(int i=0; i<objTblModItemEgr.getRowCountTrue(); i++){
            if(((Integer)objTblModItemEgr.getValueAt(i,INT_TBL_ITM_EMP_ORG))>0){
                objTblModItemEgr.setValueAt("S", i, INT_TBL_ITM_PROC);
            }
        }
    }
    
    
    private boolean insertarReg()
    {
        boolean blnRes=false;
        String strDatProc="";
        try
        {
            //conexion.setAutoCommit(false);
            if (conexion!=null && objTblModItemEgr.getRowCount()>0){
                datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                dtpFechaDocumento =  datFecAux;
                /*SE MODIFICA PARA QUE ESCOJA LA FECHA DESDE UN SELECTOR*/                
                dtpFechaDocumento=objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy");
                /*SE MODIFICA PARA QUE ESCOJA LA FECHA DESDE UN SELECTOR*/
                objZafCon64_MovEgrIng = new ZafHer25_MovEgrIng(objZafParSis,this);
                if(objZafCon64_MovEgrIng.realizaMovimientoEntreEmpresas(conexion,objTblModItemEgr,objTblModItemIng)){
//                     objCfgBod = new ZafCfgBod(objParSis,conLoc,objParSis.getCodigoEmpresaGrupo(),intCodBodGrpEgr,intCodBodGrpIng,this);
                     if(objZafCon64_MovEgrIng.getDatoEgresoIngreso(conexion,dtpFechaDocumento,'N',objZafCon64_MovEgrIng.getArlConDatPreEmpEgr(), objZafCon64_MovEgrIng.getArlConDatPreEmpIng(),
                                                                            "N",bgdSalVal)){
                          
                           blnRes=true;
                           setearRegistrosProcesados();
                           conexion.commit();
                           objZafCon64_MovEgrIng = null;
                           
                           int intEmpOrg=(Integer)tblDatEgrTrf.getValueAt(intRowSelEgrTrf, INT_TBL_EGR_TRF_EMP_ORG);
                           int intEmpDes=(Integer)tblDatEgrTrf.getValueAt(intRowSelEgrTrf, INT_TBL_EGR_TRF_EMP_DES);
                           ZafBodAgr objBodAgr=new ZafBodAgr();
                           objBodAgr.setIntEmpOrg(intEmpOrg);
                           objBodAgr.setIntEmpDes(intEmpDes);
                           objBodAgr.setBgdMonto(bgdSalVal);
                           lstBodAgr.add(objBodAgr);
                           
                           BigDecimal bgdTotalProcesado=calculaTotalProcesado(intEmpOrg, intEmpDes);
                           BigDecimal bgdMonto=(BigDecimal)tblDatEgrTrf.getValueAt(intRowSelEgrTrf,INT_TBL_EGR_TRF_MONTO);
                           
                           if(bgdTotalProcesado.compareTo(bgdMonto)==0){
                                tblDatEgrTrf.setValueAt("S", intRowSelEgrTrf, INT_TBL_EGR_TRF_STPROC);
                                strDatProc=(Integer)tblDatEgrTrf.getValueAt(intRowSelEgrTrf, INT_TBL_EGR_TRF_EMP_ORG)+"-"+(Integer)tblDatEgrTrf.getValueAt(intRowSelEgrTrf, INT_TBL_EGR_TRF_EMP_DES);
                                lstProEgrTrf.add(strDatProc);                           
                           }
                           
                           MensajeInf("La Transaccion se realizo con exito");
                      }else{
                         blnRes=false;
                         conexion.rollback();
                         //MensajeInf("La Transaccion tuvo problemas por favor contacte Sistemas");
                         MensajeInf(objZafCon64_MovEgrIng.getStrErrEspc());
                     }
                 }else{
                       blnRes=false;
                       conexion.rollback();
                       //MensajeInf("La Transaccion tuvo problemas por favor contacte Sistemas");
                       MensajeInf(objZafCon64_MovEgrIng.getStrErrEspc());
                 }
                 
            }else{
                MensajeInf("No se han cargado registros para procesar transferencia");
            }
        }
        catch (java.sql.SQLException e){        
            objUti.mostrarMsgErr_F1(this, e);       
        }
        catch (Exception e){         
            objUti.mostrarMsgErr_F1(this, e);       
        }
        finally{
            if(objZafCon64_MovEgrIng!=null){
                objZafCon64_MovEgrIng=null;
            }
        }
        return blnRes;
    }    

    public BigDecimal calculaTotalProcesado(int intEmpOrg, int intEmpDes){
        BigDecimal bgdTotProc=BigDecimal.ZERO;
        for(ZafBodAgr objBodAgr:lstBodAgr){
            if(objBodAgr.getIntEmpOrg()==intEmpOrg && objBodAgr.getIntEmpDes()==intEmpDes){
                bgdTotProc=bgdTotProc.add(objBodAgr.getBgdMonto());
            }
        }
        return bgdTotProc.setScale(2, RoundingMode.HALF_UP);
    }
    
    private void MensajeInf(String strMensaje) {
        //JOptionPane obj =new JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this, strMensaje, strTit, JOptionPane.INFORMATION_MESSAGE);
    }


}
