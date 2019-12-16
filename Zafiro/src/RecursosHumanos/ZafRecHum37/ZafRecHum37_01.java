/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package RecursosHumanos.ZafRecHum37;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


/**
 * Mantenimiento de las cuentas contables por empleado y por rubro
 * @author Roberto Flores
 * Guayaquil 24/07/2012
 */

public class ZafRecHum37_01 extends javax.swing.JInternalFrame {

  private Librerias.ZafParSis.ZafParSis objZafParSis;
  private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
  private ZafTblEdi objTblEdi;                                            //Editor: Editor del JTable.
  private ZafTblFilCab objTblFilCab;
  private ZafUtil objUti;
  private ZafTblCelEdiChk zafTblCelEdiChkPla;                             //Editor de Check Box para campo Laborable
  private ZafTblCelRenChk zafTblCelRenChkPla;                             //Renderer de Check Box para campo Laborable
  private ZafTblCelRenLbl objTblCelRenLbl;                                //Render: Presentar JLabel en JTable.
  
  Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLblCol;
  
  private ZafTblCelRenBut objTblCelRenBut;                                //Render: Presentar JButton en JTable.
  private ZafTblCelEdiButVco objTblCelEdiButVcoCta;                       //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoCta;                     //Editor: JTextField de consulta en celda.
  
  private ZafThreadGUI objThrGUI;
  private ZafMouMotAda objMouMotAda;                                      //ToolTipText en TableHeader.
//  private ZafThreadGUIRpt objThrGUIRpt;
  private ZafRptSis objRptSis;
  private ZafTblPopMnu objTblPopMnu;                                     //PopupMenu: Establecer PeopuMenú en JTable.
  private ZafTblTot objTblTot;                        //JTable de totales.
  
  private static final int INT_TBL_DAT_NUM_TOT_CDI=16;                   //Número total de columnas dinámicas.

  private static final int INT_TBL_DAT_LINEA = 0;                        //Índice de columna Linea.
  private static final int INT_TBL_DAT_COD_EMP = 1;                      
  private static final int INT_TBL_DAT_NOM_EMP = 2;
  private static final int INT_TBL_DAT_COD_TRA = 3;
  private static final int INT_TBL_DAT_NOM_APE_TRA = 4;
  private static final int INT_TBL_DAT_COD_RUB = 5;
  private static final int INT_TBL_DAT_NOM_RUB = 6;
  private static final int INT_TBL_DAT_COD_CTA = 7;
  private static final int INT_TBL_DAT_NUM_CTA = 8;
  private static final int INT_TBL_DAT_BUT_CTA = 9;
  private static final int INT_TBL_DAT_NOM_CTA = 10;
  private static final int INT_TBL_DAT_ND_VALRUB = 11;
  private static final int INT_TBL_DAT_EST=12;
  
  private String strVersion=" v1.0 ";
  
  private Vector vecDat, vecCab, vecReg;

  private boolean blnMod;                                                //Indica si han habido cambios en el documento
  private boolean blnConsDat;
     
  private ZafVenCon vcoEmp;                                   //Ventana de consulta.
  private ZafVenCon vcoDep;                                   //Ventana de consulta.
  private ZafVenCon vcoTra;
  private ZafVenCon vcoCta;                               //Ventana de consulta.
  
  private String strMe;
  private String strAux="";
  
  //ESTA VARIABLE SE CREO PARA Q CONTENGA EL CODTIPDOC PARA EL NUEVO ESQUEMA DE CTAS POR USUARIO
  private int intCodTipDocGlb;
    
  final java.awt.Color colFonCol =new java.awt.Color(255,172,165);
  private int intCantRubAct=0;
    
  private ZafPerUsr objPerUsr;
    
  private String strNeAni="";
  private String strNeMes="";
  private String strNePer="";
  
    
//    public ZafRecHum37_01(Librerias.ZafParSis.ZafParSis obj) {
//
//        try{
//
//            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
//	    initComponents();
//
//            this.setTitle(objZafParSis.getNombreMenu()+"  "+strVersion+" ");
//            lblTit.setText(objZafParSis.getNombreMenu());
//
//	    objUti = new ZafUtil();
//
//            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);
//
//	    this.objZafParSis = (ZafParSis) objZafParSis.clone();
//            objPerUsr=new ZafPerUsr(objZafParSis);
//            
//            //Configurar las ZafVenCon.
//            configurarVenConCta();
//            
//            configuraTbl();
//            tblDat.getModel().addTableModelListener(new MyTableModelListener(tblDat));
//            
//            butCon.setVisible(false);
//            butGua.setVisible(false);
//            butCer.setVisible(false);
//            
//            if(objPerUsr.isOpcionEnabled(3168)){
//                butCon.setVisible(true);
//            }
//            if(objPerUsr.isOpcionEnabled(3169)){
//                butGua.setVisible(true);
//            }
//            if(objPerUsr.isOpcionEnabled(3170)){
//                butCer.setVisible(true);
//            }
//            
//            blnMod = false;
//            blnConsDat = false;
//
//         }catch (Exception e){ e.printStackTrace(); objUti.mostrarMsgErr_F1(this, e); }  /**/
//    }

    public ZafRecHum37_01(Librerias.ZafParSis.ZafParSis obj, String strCodEmp, String strNeAni, String strNeMes, String strNePer) {

        try{

            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();
            
            this.setTitle(objZafParSis.getNombreMenu()+"  "+strVersion+" ");
            lblTit.setText(objZafParSis.getNombreMenu());

	    objUti = new ZafUtil();

            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

	    this.objZafParSis = (ZafParSis) objZafParSis.clone();
            objPerUsr=new ZafPerUsr(objZafParSis);
            
            //Configurar las ZafVenCon.
            configurarVenConCta();

            configuraTbl();
            tblDat.getModel().addTableModelListener(new MyTableModelListener(tblDat));
            
//            butCon.setVisible(false);
//            butGua.setVisible(false);
//            butCer.setVisible(false);
//            
//            if(objPerUsr.isOpcionEnabled(3168)){
//                butCon.setVisible(true);
//            }
//            if(objPerUsr.isOpcionEnabled(3169)){
//                butGua.setVisible(true);
//            }
//            if(objPerUsr.isOpcionEnabled(3170)){
//                butCer.setVisible(true);
//            }
            
            blnMod = false;
            blnConsDat = false;

            this.strNeAni=strNeAni;
            this.strNeMes=strNeMes;
            this.strNePer=strNePer;
            
            if (objThrGUI==null) {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }

         }catch (Exception e){ e.printStackTrace(); objUti.mostrarMsgErr_F1(this, e); }  /**/
    }

    public class MyTableModelListener implements TableModelListener {
    JTable table;

    // It is necessary to keep the table since it is not possible
    // to determine the table from the event's source
    MyTableModelListener(JTable table) {
        this.table = table;
    }

    public void tableChanged(TableModelEvent e) {
        if(!blnConsDat){
            switch (e.getType()) {
              case TableModelEvent.UPDATE:
                  blnMod = true;
                break;
            }
        }
        blnConsDat = false;
    }
}

    
    private boolean configuraTbl(){
        
        boolean blnRes=false;
        String strSQL="";
        
        try{
            
        //Configurar JTable: Establecer el modelo.
        vecCab=new Vector();
        vecCab.clear();
        vecCab.add(INT_TBL_DAT_LINEA,"");
        vecCab.add(INT_TBL_DAT_COD_EMP,"Cód. Emp.");
        vecCab.add(INT_TBL_DAT_NOM_EMP,"Empresa");
        vecCab.add(INT_TBL_DAT_COD_TRA,"Código");
        vecCab.add(INT_TBL_DAT_NOM_APE_TRA,"Empleado");
        vecCab.add(INT_TBL_DAT_COD_RUB,"Cód. Rub.");
        vecCab.add(INT_TBL_DAT_NOM_RUB,"Rubro");
        vecCab.add(INT_TBL_DAT_COD_CTA,"Cód. Cta.");
        vecCab.add(INT_TBL_DAT_NUM_CTA,"Núm. Cta.");
        vecCab.add(INT_TBL_DAT_BUT_CTA,"");
        vecCab.add(INT_TBL_DAT_NOM_CTA,"Nombre de la cuenta");
        vecCab.add(INT_TBL_DAT_ND_VALRUB,"Valor");
        vecCab.add(INT_TBL_DAT_EST,"ESTADO");
        
        
        objTblMod=new ZafTblMod();
        objTblMod.setHeader(vecCab);
        
        //Configurar JTable: Establecer el modelo de la tabla.
        tblDat.setModel(objTblMod);
        
        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_DAT_LINEA);
        
        //Configurar JTable: Establecer el menú de contexto.
        objTblPopMnu=new ZafTblPopMnu(tblDat);
        
        //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        
        //Configurar JTable: Renderizar celdas.
        objTblCelRenLbl=new ZafTblCelRenLbl();
        //objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn(INT_TBL_DAT_ND_VALRUB).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_MIN_SEC_SUG).setCellRenderer(objTblCelRenLbl);
        //objTblCelRenLbl=null;
        
        //Configurar JTable: Establecer el ancho de las columnas.
        tcmAux.getColumn(INT_TBL_DAT_LINEA).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_DAT_NOM_EMP).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_DAT_COD_TRA).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_DAT_NOM_APE_TRA).setPreferredWidth(250);
        tcmAux.getColumn(INT_TBL_DAT_COD_RUB).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_DAT_NOM_RUB).setPreferredWidth(120);
        tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_DAT_NOM_CTA).setPreferredWidth(150);
        tcmAux.getColumn(INT_TBL_DAT_ND_VALRUB).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DAT_EST).setPreferredWidth(50);
        
        //Configurar JTable: Ocultar columnas del sistema.
        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_RUB, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CTA, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST, tblDat);
        
        
        //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
        tblDat.getTableHeader().setReorderingAllowed(false);

        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        objMouMotAda=new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

        //Configurar JTable: Establecer columnas editables.
        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_DAT_NUM_CTA);
        vecAux.add("" + INT_TBL_DAT_BUT_CTA);
        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;

        //Configurar JTable: Editor de la tabla.
        objTblEdi=new ZafTblEdi(tblDat);
        //Configurar JTable: Establecer la fila de cabecera.
        objTblFilCab=new ZafTblFilCab(tblDat);
        tcmAux.getColumn(INT_TBL_DAT_LINEA).setCellRenderer(objTblFilCab);
            
        //Configurar JTable: Renderizar celdas.
        //objZafDtePckEdi = new ZafDtePckEdi(strFormat);
        //tcmAux.getColumn(INT_TBL_FECHA).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormat));
            
            
        int intColVen[]=new int[3];
        intColVen[0]=1;
        intColVen[1]=2;
        intColVen[2]=3;
        int intColTbl[]=new int[3];
        intColTbl[0]=INT_TBL_DAT_COD_CTA;
        intColTbl[1]=INT_TBL_DAT_NUM_CTA;
        intColTbl[2]=INT_TBL_DAT_NOM_CTA;
            
            
        objTblCelRenLbl=new ZafTblCelRenLbl();
        objTblCelRenLblCol = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        tcmAux.getColumn(INT_TBL_DAT_NOM_EMP).setCellRenderer(objTblCelRenLblCol);
        tcmAux.getColumn(INT_TBL_DAT_COD_TRA).setCellRenderer(objTblCelRenLblCol);
        tcmAux.getColumn(INT_TBL_DAT_NOM_APE_TRA).setCellRenderer(objTblCelRenLblCol);
        tcmAux.getColumn(INT_TBL_DAT_NOM_RUB).setCellRenderer(objTblCelRenLblCol);
            
        objTblCelRenLblCol.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
        public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
            //Mostrar de color gris las columnas impares.

//                int intCell=objTblCelRenLblCol.getRowRender();
//
////                intCantRubAct
//                String str=tblDat.getValueAt(intCell, INT_TBL_DAT_EST).toString();
////
//                if(str.equals("S")){
//                    objTblCelRenLblCol.setBackground(colFonCol);
//                    objTblCelRenLblCol.setForeground(java.awt.Color.BLACK);
//                }else {
//                    objTblCelRenLblCol.setBackground(java.awt.Color.WHITE);
//                    objTblCelRenLblCol.setForeground(java.awt.Color.BLACK);
//                }
        }
        public void afterRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
            //Mostrar de color gris las columnas impares.

//                int intCell=objTblCelRenLbl.getRowRender();
//
////                intCantRubAct
//                String str=tblDat.getValueAt(intCell, INT_TBL_DAT_EST).toString();
////
//                if(str.equals("S")){
//                    objTblCelRenLbl.setBackground(colFonCol);
//                    objTblCelRenLbl.setForeground(java.awt.Color.BLACK);
//                }else {
//                    objTblCelRenLbl.setBackground(java.awt.Color.WHITE);
//                    objTblCelRenLbl.setForeground(java.awt.Color.BLACK);
//                }
        }
    });
        
        //tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setCellRenderer(objTblCelRenLblCol);
        objTblCelEdiTxtVcoCta=new ZafTblCelEdiTxtVco(tblDat, vcoCta, intColVen, intColTbl);
        tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setCellEditor(objTblCelEdiTxtVcoCta);
        objTblCelEdiTxtVcoCta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            String strEstCncDia="";//estado de conciliacion bancaria de la cuenta

            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

            }
            public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                vcoCta.setCampoBusqueda(1);
                vcoCta.setCriterio1(7);
                if(objZafParSis.getCodigoUsuario()!=1)
                {
//                    strMe="";
//                    strMe+=" AND a2.co_tipdoc=" + intCodTipDocGlb + "";
//                    strMe+=" AND a2.co_usr=" + objZafParSis.getCodigoUsuario() + "";
//                    vcoCta.setCondicionesSQL(strMe);
                }
                setPuntosCta();
            }

            public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

            }
        });

        objTblCelRenBut=new ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setCellRenderer(objTblCelRenBut);



        objTblCelEdiButVcoCta=new ZafTblCelEdiButVco(tblDat, vcoCta, intColVen, intColTbl);
        tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setCellEditor(objTblCelEdiButVcoCta);
         objTblCelEdiButVcoCta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
             public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

             }
             public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

             }
              public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

              }
              public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

              }
         });
        intColVen=null;
        intColTbl=null;

        objTblCelRenLbl=new ZafTblCelRenLbl();
        tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setCellRenderer(objTblCelRenLbl);

        //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
        int intCol[]={11};
        objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
        
        objTblCelRenLbl=null;

        //Configurar JTable: Modo de operación del JTable.
        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        tcmAux=null;
        blnRes=true;

        }catch(Exception e) {  e.printStackTrace(); blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }

        return blnRes;       
    }

    public void setEditable(boolean editable) {
        if (editable==true){
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }else{  objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI); }
    }
  
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
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
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
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

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("titulo"); // NOI18N
        panNor.add(lblTit, java.awt.BorderLayout.CENTER);

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.BorderLayout());

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
        tblDat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblDatFocusGained(evt);
            }
        });
        spnDat.setViewportView(tblDat);

        jPanel1.add(spnDat, java.awt.BorderLayout.CENTER);

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

        jPanel1.add(spnTot, java.awt.BorderLayout.SOUTH);

        tabGen.addTab("Reporte", jPanel1);

        getContentPane().add(tabGen, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        butCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(90, 23));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        jPanel5.add(butCon);

        butGua.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(90, 23));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        jPanel5.add(butGua);

        butCer.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCer.setText("Cerrar");
        butCer.setPreferredSize(new java.awt.Dimension(90, 23));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        jPanel5.add(butCer);

        jPanel3.add(jPanel5, java.awt.BorderLayout.EAST);

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

        jPanel3.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents


    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
    }//GEN-LAST:event_formInternalFrameOpened

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        // TODO add your handling code here:
        java.sql.Connection conn = null;
        
        try{
            conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if(conn!=null){
                
                conn.setAutoCommit(false);
                if(guardarDat(conn)){
                    conn.commit();
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito");
                    butConActionPerformed(null);
                }else{
                    conn.rollback();
                }
            }
        }catch(java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }catch(Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }finally {
            try{conn.close();}catch(Throwable ignore){}
        }
    }//GEN-LAST:event_butGuaActionPerformed

    private boolean guardarDat(java.sql.Connection con){
        
        boolean blnRes=true;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSQL="";
        
        try{
            
            if(con!=null){

                stmLoc=con.createStatement();

                for(int i=0; i<tblDat.getRowCount();i++){
                    if(tblDat.getValueAt(i, INT_TBL_DAT_LINEA).toString().compareTo("M")==0){

                        Object obj = tblDat.getValueAt(i, INT_TBL_DAT_COD_CTA);
                        String strCo_Cta=null;
                        if(obj!=null){
                            if(obj.toString().compareTo("")!=0){
                                strCo_Cta = tblDat.getValueAt(i, INT_TBL_DAT_COD_CTA).toString();
                            }
                        }
                        
                        strSQL="";
                        strSQL+="UPDATE tbm_suetra SET co_cta = " + strCo_Cta;
                        strSQL+=" WHERE co_emp = " + tblDat.getValueAt(i, INT_TBL_DAT_COD_EMP).toString();
                        strSQL+=" AND co_rub = " + tblDat.getValueAt(i, INT_TBL_DAT_COD_RUB).toString();
                        strSQL+=" AND co_tra = " + tblDat.getValueAt(i, INT_TBL_DAT_COD_TRA).toString();
                        System.out.println("act_co_cta_rub_tra: " + strSQL);
                        stmLoc.executeUpdate(strSQL);

                    }
                }
            }
        }catch(java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
        }catch(Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
        }
        finally {
            try{stmLoc.close();}catch(Throwable ignore){}
            try{rstLoc.close();}catch(Throwable ignore){}
        }
        
        return blnRes;
    }
    
    
    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
         exitForm();
}//GEN-LAST:event_butCerActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if (objThrGUI==null) {
            objThrGUI=new ZafThreadGUI();
            objThrGUI.start();
        }
    }//GEN-LAST:event_butConActionPerformed

    private void tblDatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblDatFocusGained

    }//GEN-LAST:event_tblDatFocusGained

    private class ZafThreadGUI extends Thread
    {
        public void run(){

            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);

            cargarDat();

            objThrGUI=null;
        }
    }

    /**
     * Se encarga de cargar la informacion en la tabla
     * @return  true si esta correcto y false  si hay algun error
     */
    private boolean cargarDat(){

        blnConsDat = true;
        blnMod = false;
        boolean blnRes=false;

        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="",sqlFil="";
        Vector vecDataCon;
        

        try{

            conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

            if(conn!=null){

                stmLoc=conn.createStatement();
                vecDataCon = new Vector();
                
                strSql+="select c.co_emp, f.tx_nom as empresa,c.co_tra, (d.tx_ape || ' ' || d.tx_nom) as empleado , ";
                strSql+=" a.ne_ani, a.ne_mes, a.ne_per, b.co_rub, e.tx_nom , a.nd_valrub, b.co_cta, ";
                strSql+=" e.tx_nom as tx_nomrub,g.tx_codcta,g.tx_deslar";
                strSql+=" from tbm_ingegrmentra a";
                strSql+=" inner join tbm_suetra b on (b.co_emp=a.co_emp and a.co_rub=b.co_rub and a.co_tra=b.co_tra)";
                strSql+=" inner join tbm_traemp c on (c.co_tra=b.co_tra and c.co_emp=a.co_emp and c.st_reg='A')";
                strSql+=" inner join tbm_tra d on (d.co_tra=c.co_tra)";
                strSql+=" inner join tbm_rubrolpag e on (b.co_rub=b.co_rub and a.co_rub=e.co_rub)";
                strSql+=" inner join tbm_emp f on (f.co_emp=a.co_emp)";
                strSql+=" left outer join tbm_placta g on (g.co_emp=a.co_emp and g.co_cta=b.co_cta)";
                strSql+=" where a.co_emp="+objZafParSis.getCodigoEmpresa();
                strSql+=" and a.ne_ani="+strNeAni;
                strSql+=" and a.ne_mes="+strNeMes;
                strSql+=" and a.ne_per="+strNePer;
                strSql+=" and a.nd_valrub is not null and (a.nd_valrub>0 or a.nd_valrub<0)";
                strSql+=" and b.co_cta is null";
                
                System.out.println("q consul: " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);

                
                int intCont=1;
                int intEsPI=1;
                while(rstLoc.next()){

                    java.util.Vector vecReg = new java.util.Vector();

                    vecReg.add(INT_TBL_DAT_LINEA,"");
                    vecReg.add(INT_TBL_DAT_COD_EMP,rstLoc.getString("co_emp"));
                    
                    vecReg.add(INT_TBL_DAT_NOM_EMP,rstLoc.getString("empresa"));
                    vecReg.add(INT_TBL_DAT_COD_TRA,rstLoc.getString("co_tra"));
                    vecReg.add(INT_TBL_DAT_NOM_APE_TRA,rstLoc.getString("empleado"));
                    
                    vecReg.add(INT_TBL_DAT_COD_RUB,rstLoc.getString("co_rub"));
                    vecReg.add(INT_TBL_DAT_NOM_RUB,rstLoc.getString("tx_nomrub"));
                    
                    vecReg.add(INT_TBL_DAT_COD_CTA,rstLoc.getString("co_cta"));
                    vecReg.add(INT_TBL_DAT_NUM_CTA,rstLoc.getString("tx_codcta"));
                    
                    vecReg.add(INT_TBL_DAT_BUT_CTA,"...");
                    vecReg.add(INT_TBL_DAT_NOM_CTA,rstLoc.getString("tx_deslar"));
                    vecReg.add(INT_TBL_DAT_ND_VALRUB,rstLoc.getString("nd_valrub"));
                    
                    if(intEsPI%2==1){
                        vecReg.add(INT_TBL_DAT_EST,"S");
                    }else{
                        vecReg.add(INT_TBL_DAT_EST,"V");
                    }
                    
                    
                    if(intCont==intCantRubAct){
                       intEsPI++; 
                       intCont=0;
                    }
                    
                    intCont++;
                    vecDataCon.add(vecReg);
                }

                objTblMod.setData(vecDataCon);
                objTblTot.calcularTotales();
                tblDat .setModel(objTblMod);
                
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conn.close();
                conn=null;
                
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);
                blnRes=true;
           }}catch(java.sql.SQLException Evt) {  Evt.printStackTrace();blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
        catch(Exception Evt) { Evt.printStackTrace();blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;
    }
    
    /**
     * Para salir de la pantalla en donde estamos y pide confirmacion de salidad.
     */
    private void exitForm(){

        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabGen;
    public javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    // End of variables declaration//GEN-END:variables

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){

            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";

            switch (intCol){

                case INT_TBL_DAT_LINEA:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Cód. Emp.";
                    break;
                case INT_TBL_DAT_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_DAT_COD_TRA:
                    strMsg="Código del empleado";
                    break;
                case INT_TBL_DAT_NOM_APE_TRA:
                    strMsg="Nombres y Apellidos del empleado";
                    break;
                case INT_TBL_DAT_COD_RUB:
                    strMsg="Código del rubro";
                    break;
                case INT_TBL_DAT_NOM_RUB:
                    strMsg="Nombre del rubro";
                    break;
                case INT_TBL_DAT_COD_CTA:
                    strMsg="Código de la cuenta";
                    break;
                case INT_TBL_DAT_NUM_CTA:
                    strMsg="Número de la cuenta";
                    break;
                case INT_TBL_DAT_ND_VALRUB:
                    strMsg="Valor";
                    break;
                case INT_TBL_DAT_NOM_CTA:
                    strMsg="Nombre de la cuenta";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }
    
    /**
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConCta()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codCta");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cï¿½digo");
            arlAli.add("Cuenta");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("80");
            arlAncCol.add("400");
            //Armar la sentencia SQL.
            String strSQL="";
            if(objZafParSis.getCodigoEmpresaGrupo()==objZafParSis.getCodigoEmpresa()){
                
                if(objZafParSis.getCodigoUsuario()==1)
                {
                    strSQL+="SELECT distinct a1.co_emp,a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" ORDER BY a1.tx_codCta";
                }
                else
                {
                    strSQL+="SELECT distinct a1.co_emp,a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" ORDER BY a1.tx_codCta";
                }
            }else{
                
                if(objZafParSis.getCodigoUsuario()==1)
                {
                    strSQL+="SELECT distinct a1.co_emp,a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                    strSQL+=" ORDER BY a1.tx_codCta";
                }
                else
                {
                    strSQL+="SELECT distinct a1.co_emp,a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                    strSQL+=" ORDER BY a1.tx_codCta";
                }
            }
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=1;
            vcoCta=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de cuentas contables", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoCta.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCta.setCampoBusqueda(1);
            vcoCta.setCriterio1(7);
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
     * mostrar los "Departamentos".
     */
    private boolean configurarVenConDep()
    {
        boolean blnRes=true;
        String strSQL="";
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_dep");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción corta");
            arlAli.add("Descripción larga");
            arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            arlAncCol.add("110");
            arlAncCol.add("40");

            if(objZafParSis.getCodigoUsuario()==1){
                strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where st_reg like 'A' order by co_dep";
            }else{
                strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objZafParSis.getCodigoUsuario()+" "+
                        "and co_mnu="+objZafParSis.getCodigoMenu()+")";
            }
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            vcoDep=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado Departamentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoDep.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoDep.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
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
     * mostrar los "Empleados".
     */
    private boolean configurarVenConTra()
    {
        boolean blnRes=true;
        String strSQL="";
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ape");
            arlCam.add("a1.tx_nom");
            
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("150");
            
            if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where a.st_reg like 'A' "+
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }else{
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where a.st_reg like 'A' and co_emp = "+ objZafParSis.getCodigoEmpresa() + " " +
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            vcoTra=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private void setPuntosCta(){
        strAux=objTblCelEdiTxtVcoCta.getText();
        String strCodCtaOri=strAux;
        String strCodCtaDes="";
        char chrCtaOri;
        //obtengo la longitud de mi cadena
        int intLonCodCta=strCodCtaOri.length();
        int intLonCodCtaMen=intLonCodCta-1;
        //PARA CUANDO LOS TRES ULTIMOS DIGITOS SE LOS DEBE TOMAR COMO UN NIVEL DIFERENTE
        int intLonCodCtaMenTreDig=intLonCodCta-2;
        if (strCodCtaOri.length()<=1)
            return;
        else{
//            if(  (strCodCtaOri.length() % 2) != 0 ){
                chrCtaOri=strCodCtaOri.charAt(1);
                if(chrCtaOri!='.'){
                    for (int i=0; i < strCodCtaOri.length(); i++){
                        if(i==0){
                            strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                            strCodCtaDes=strCodCtaDes+".";
                        }
                        else{
                            if(  (strCodCtaOri.length() % 2) == 0 ){
                                if(((i % 2)==0)&&(i<intLonCodCtaMenTreDig)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                    strCodCtaDes=strCodCtaDes+".";
                                }
                                if(((i % 2)==0)&&(i==intLonCodCtaMenTreDig)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                                else{
                                    if((i % 2)!= 0)
                                        strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                            }
                            else{
                                if(((i % 2)==0)&&(i!=intLonCodCtaMen)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                    strCodCtaDes=strCodCtaDes+".";
                                }
                                if(((i % 2)==0)&&(i==intLonCodCtaMen)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                                else{
                                    if((i % 2)!= 0)
                                        strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                            }
                        }
                    }
                    objTblCelEdiTxtVcoCta.setText(strCodCtaDes);
                }
//            }
        }
    }
}