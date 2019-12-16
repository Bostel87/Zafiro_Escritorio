/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RecursosHumanos.ZafRecHum31;

import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import RecursosHumanos.ZafRecHum31.ZafRecHum31_01;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragSource;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel; 


/**
 *
 * @author Gaby@hotmail.com
 */
public class ZafRecHum31 extends javax.swing.JInternalFrame {
    //// constantes 
    //Constantes: Columnas del JTable:
    /// Datos de las Tareas
    public static final int INT_TBL_NO_EDI=0;                   /**Un valor para setModoOperacion: Indica "Tabla no editable".*/
    public static final int INT_TBL_EDI=1;                      /**Un valor para setModoOperacion: Indica "Tabla editable".*/
    public static final int INT_TBL_INS=2;                      /**Un valor para setModoOperacion: Indica "Tabla editable e insertable".*/
                             //Reportes del Sistema.
    
    static final int INT_TBL_DAT_LIN=0;                        //Línea
    static final int INT_TBL_DAT_Cod_Tar=1;                    //Código de la tarea.
    static final int INT_TBL_DAT_Ubi_TAR=2;                    //codtargen de tarea.
    static final int INT_TBL_DAT_TIP_TAR=3;                    //tipo de tarea.
    static final int INT_TBL_DAT_TAR_PAD=4;                    //tarea padre .
    static final int INT_TBL_DAT_NIV_TAR=5;                    //nivel tarea
    static final int INT_TBL_DAT_FECHA=6;                      //fecha
    static final int INT_TBL_DAT_DES_COR=7;                    // descriccion corta de la tarea 
    static final int INT_TBL_DAT_BUT_DES_COR=8;                // boton descripcion corta .
    static final int INT_TBL_DAT_DES_LAR=9;                    // Descripcion LARGA DE LA TAREA
    static final int INT_TBL_DAT_BUT_DES_LAR=10;               // Bton DES LAR
    static final int INT_TBL_DAT_COD_SOL=11;                   //Código del solicitante.
    static final int INT_TBL_DAT_BUT_SOL=12;
    static final int INT_TBL_DAT_SOLICITANTE=13;              //  Solicitante.
    
    /// Planificacion 
  
    static final int INT_TBL_DAT_PRIORIDAD=14;                //Combox Prioridad 
    static final int INT_TBL_DAT_COD_RES=15;                  // CODIGO DE RESPONSABLE
    static final int INT_TBL_DAT_But_RES=16;
    static final int INT_TBL_DAT_RESPONSABLE=17;              /// RESPONSABLE 
    
    static final int INT_TBL_DAT_FEC_INI_PLA=18;              /// FECHA INICIO PLANIFIFICADA 
    static final int INT_TBL_DAT_FEC_FIN_PLA=19;  
    ////Desarrollo 
    
    static final int INT_TBL_DAT_TAR_PEND=20;
    static final int INT_TBL_DAT_Tar_Anular=21;
    static final int INT_TBL_DAT_TAR_PROCESO= 22;
    static final int INT_TBL_DAT_TAR_DETENIDA=23;
    static final int INT_TBL_DAT_TAR_TERMINADA=24;
    static final int INT_TBL_DAT_POR_TRA_REA=25;
    static final int INT_TBL_DAT_FEC_INI_REA=26;
    static final int INT_TBL_DAT_FEC_ULT_TRA=27;
    
    static final int INT_CFE_COD_tar=0;                        //Columnas de las filas eliminadas: Código del tarea.
    static final int INT_CFE_COD_tipar=1;                     //Columnas de las filas eliminadas: tipo de tarea.
    static final int INT_CFE_COD_pad=2;                        //Columnas de las filas eliminadas: Código del tarea.
    static final int INT_CFE_COD_nivel=3;                     //Columnas de las filas eliminadas: tipo de tarea.
    
    
    int intCo_RegMax=0;
    int ubi_nummax =0;
    int intCo_RegMaxubi=0; 
    int maxtabl=0;
    int ubi=0;
    
    private Vector vecCab, vecDat, vecCabAux , vecDatAux;
    private Vector vecReg, vecRegAux;
    
    
      private ZafTblCelRenChk ZafTblCelRenChkDat;
      private ZafParSis objParSis;                                 ///objeto de conexion
      private ZafUtil objUti;
      private ZafTblMod objTblMod, objTblModAux;
      private ZafTblHeaColGrp objTblHeaColGrpAmeSur;
      private ZafTblHeaGrp objTblHeaGrp;
      private ZafTblBus objTblBus;
      private ZafTblFilCab objTblFilCab, objTblFilCabAux;
      private ZafTblCelRenBut objTblCelRenBut;
      private ZafTblCelEdiButDlg objTblCelEdiButDlgCor, objTblCelEdiButDlgLar;
      private ZafTblCelEdiButGen objTblCelEdiButGen;
      private ZafTblCelEdiButVco objTblCelEdiButvco;
      private ZafTblOrd objTblOrd;
      private ZafMouMotAda objMouMotAda;
      private ZafThreadGUI objThrGUI;
      private ZafTblPopMnu objTblPopMnu;
      private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoCli;
      private ZafTblCelEdiTxt objportrarea;
      
      ZafVenCon objVenConsolisitante;
      private ZafTblCelEdiTxtVco objTblCelEdiTxtVcores ;
      private ZafTblEdi objTblEdi;  
      
      /// chak tareas 
      private ZafTblCelEdiChk objTblCelEdiChkPen;
      private ZafTblCelEdiChk objTblCelEdiChkAnul;
      private ZafTblCelEdiChk objTblCelEdiChkPro;
      private ZafTblCelEdiChk objTblCeldeteni;
      private ZafTblCelEdiChk objTblCelEdiTermi;
        
      /// renderizadores 
      private ZafTblCelRenCbo objTblCelRenCmbBoxFec;
      private ZafTblCelRenChk zafTblCelRenChkLab;
      // edicion 
      private ZafTblCelEdiCbo objTblCelEdiCmbBoxFec;
      // renderizadores de Jlabel 
      private ZafTblCelRenLbl objTblCelRenLbl;
      private ZafTblCelRenLbl objTblCelRenLblCol2;
      
      private ZafTblModLis objTblModLis;
      
      ZafDatePicker txtFecdes;                                     /// fecha  desde 
      ZafDatePicker txtFechas;                                     /// fecha hasta 
      
      private ZafVenCon vcoDep;                                    /// obejto consulta departamento 
      private ZafVenCon vcores;
      private ZafVenCon vcosol;
      
      private String strVersion=" V 1.1 "; 
      private String strSql;
      private String strCodLoc, strNomLoc,strNom,strCodres;
      private String strTit="Mensaje del sistema Zafiro";
      private String strMsg="";
      String strFormatoFecha="d/m/y";
      
      private int intFilSel, intColSel;
      
      private Connection conn;
      private Statement stm;
      private ResultSet rst;
      
      private boolean blnHayCam;                                 /// determina si hay cambios en el formulario 
      private  int intCodtardes=0;                               // variable utilizada para generar el codigo de la tarea
      private  int intcodubi=0;
      private  boolean cambia=false;
      private boolean elihijos = false ;                                 /// determina                         
      private final int INT_MAX_NIV_MNU=3;
      // fecha 
      private ZafSelFec objSelFec;
       
      private ZafRecHum31_01 objMae67_01_1, objMae67_01_2;
       
      private int intMaxCodTar, intMaxUbi;
     ArrayList arlelihijos = new java.util.ArrayList();
     ArrayList arlAux=new java.util.ArrayList(); 
      
      
     
     
     
    /**
     * Creates new form ZafRecHum31
     */
    public ZafRecHum31(ZafParSis objZafParsis) {
        
         try {
             
         ZafTblCelRenChkDat = new ZafTblCelRenChk();
          this.objParSis=(ZafParSis) objZafParsis.clone(); 
            //Inicializar objetos.
            objUti=new ZafUtil();
            initComponents();
        
            this.setTitle(objParSis.getNombreMenu() + strVersion );
            lblTit.setText(objParSis.getNombreMenu());
            this.setBounds(10, 10, 710, 480); 
            
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setTitulo("Fecha de la Tarea");
            objSelFec.setCheckBoxVisible(false);
            jPanel5.add(objSelFec);
            objSelFec.setBounds(30, 45, 472, 72);
            
             //*****************************************************************************
            Librerias.ZafDate.ZafDatePicker txtFecDoc;
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setHoy();
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
            int fecDoc [] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if(fecDoc!=null){
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            }
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            objFecPagActual.add(java.util.Calendar.DATE, -30 );

            dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");

            objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );

            //*******************************************************************************
           // DefaultMutableTreeNode padre = new DefaultMutableTreeNode("padre");
            
            /// setear los campos obligatorios 
            
            txtCodDep.setBackground(objZafParsis.getColorCamposObligatorios());
            txtDesLarDep.setBackground(objZafParsis.getColorCamposObligatorios());
           
           
            tab_frm.setEnabledAt(2, false);
            
          
     
            
        }catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR] ");
        }     
    }
    
    
    /**
     * Mostrar la ventana de Departamento 
     */
    
    
    
    private boolean mostrarVenConLoc(int intTipBus) {
        boolean blnRes=true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoDep.setCampoBusqueda(1);
                    //vcoLoc.show();
                    vcoDep.setVisible(true);
                    if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtDesLarDep.setText(vcoDep.getValueAt(2));
                    }
                break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoDep.buscar("a1.co_dep", txtCodDep.getText())) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtDesLarDep.setText(vcoDep.getValueAt(2));
                    }else {
                        vcoDep.setCampoBusqueda(0);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        //vcoLoc.show();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtDesLarDep.setText(vcoDep.getValueAt(2));
                        }else
                            txtCodDep.setText(strCodLoc);                        
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoDep.buscar("a1.tx_deslar", txtDesLarDep.getText())){
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtDesLarDep.setText(vcoDep.getValueAt(2));
                    }else{
                        vcoDep.setCampoBusqueda(1);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        //vcoLoc.show();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtDesLarDep.setText(vcoDep.getValueAt(2));
                        }else 
                            txtDesLarDep.setText(strNomLoc);                        
                    }
                    break;
            }
        }catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    /// Esta Funcion permite con figurar la ventana de consulta  para mostrar 
     /// departamento a tipo de usuario .
    
    
    private boolean configurarVenConDep() {       boolean blnRes=true;
        try {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_dep");
            arlCam.add("a1.tx_deslar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Departemento");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("492");

            //Cargar el listado de departamentos de acuerdo al usuario.
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los departamentos.
            if (objParSis.getCodigoUsuario()==1) {
                strSql= " SELECT a1.co_dep, a1.tx_deslar " +
                        " FROM tbm_dep AS a1 " +
                    
                        " ORDER BY 1";
            }else {
                strSql= " select a.co_dep, a.tx_deslar " +
                        " from tbm_dep a, tbr_depprgusr b " +
                        " where a.co_dep=b.co_dep " +

                        " and b.co_usr=" + objParSis.getCodigoUsuario() + " " +
                        " and b.co_mnu=" + objParSis.getCodigoMenu() + " " +
                        
                        " order by 1 ";
            }                

            System.out.println("ZafRecHum31.configurarVenConDep" + strSql);
            
            vcoDep=new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, "Listado de Departamentos", strSql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoDep.setConfiguracionColumna(1, JLabel.RIGHT);
        }catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }  
    
    
    
      /**
        Esta fucion permite configurar  la ventana de consulta del reponsable de tarea 
     */
    
     
     private boolean configurarVenConRes() {
        boolean blnRes=true;
        try {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("traba");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("300");

            //Cargar el listado de departamentos de acuerdo al usuario.
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los departamentos.
               if (objParSis.getCodigoUsuario()==1) {
                strSql= " SELECT a.co_tra, a.tx_nom ||' '||a.tx_ape as traba  " +
                        " FROM tbm_tra AS a " +
                        " ORDER BY tx_nom";
            }else {
                strSql= "SELECT a.co_tra,a.tx_nom ||' '||a.tx_ape as traba FROM TBM_TRA a" +
                        "INNER JOIN TBM_TRAemp b on (a.co_tra=b.co_tra)" +
                        "inner join tbm_dep c on (b.co_dep=c.co_dep) " ;

                    //    "where c.co_dep=" + '9'+ " " +
                     //   " order by a.co_tra ";
            }                

            System.out.println("ZafRecHum31.configurarVenCotra" + strSql);
            
            vcores=new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empleados", strSql, arlCam, arlAli, arlAncCol);
            vcosol=new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empleados", strSql, arlCam, arlAli, arlAncCol);
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            //vcoDep.setConfiguracionColumna(1, JLabel.RIGHT);
            vcosol.setConfiguracionColumna(1, JLabel.RIGHT);
            
            
        }catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
    
    
     
     
      private boolean mostrarVenConRes(int intTipBus) {
        boolean blnRes=true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcores.setCampoBusqueda(1);
                    //vcoLoc.show();
                    vcores.setVisible(true);
                    if (vcores.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                        txtCodres.setText(vcores.getValueAt(1));
                        txtDesLarRes.setText(vcores.getValueAt(2));
                        radFilCli.setSelected(true);
                    }
                break;
                case 1: //Búsqueda directa por "Código".
                    if (vcores.buscar("a.co_tra", txtCodres.getText())) {
                        txtCodres.setText(vcores.getValueAt(1));
                        txtDesLarRes.setText(vcores.getValueAt(2));
                        radFilCli.setSelected(true);
                    }else {
                        vcores.setCampoBusqueda(0);
                        vcores.setCriterio1(11);
                        vcores.cargarDatos();
                        //vcoLoc.show();
                        vcores.setVisible(true);
                        if (vcores.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodres.setText(vcoDep.getValueAt(1));
                            txtDesLarRes.setText(vcoDep.getValueAt(2));
                            radFilCli.setSelected(true);
                        }else
                            txtCodres.setText(strCodres);                        
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcores.buscar("traba", txtDesLarRes.getText())){
                        txtCodres.setText(vcores.getValueAt(1));
                        txtDesLarRes.setText(vcores.getValueAt(2));
                        radFilCli.setSelected(true);
                    }else{
                        vcores.setCampoBusqueda(2);
                        vcores.setCriterio1(11);
                        vcores.cargarDatos();
                        //vcoLoc.show();
                        vcores.setVisible(true);
                        if (vcores.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {

                            txtCodres.setText(vcores.getValueAt(1));
                            txtDesLarRes.setText(vcores.getValueAt(2));
                            radFilCli.setSelected(true);
                        }else 
                            txtDesLarRes.setText(strNom);                        
                    }
                    break;
            }
        }catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
      
     
      
      private void mostrarBotdire ()
      {
          butder.setVisible(true);
          butizq.setVisible(true);
          butdwn.setVisible(true);
          butup.setVisible(true);
          
      }
      
      //llamar la ventana trabajador
      
      
      
      
      
      
     
      
      public void setEditable(boolean editable) 
        {
        if (editable==true)
          {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);

        }
       else{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }
      
     
     /// eventos para asignar valor a las celdas de la consulta
      private void eventoVenConCli(int intNumFil){
  try{

              int i =  intNumFil-1;
              for(int intNumFilDat=i; intNumFilDat >=  0;   intNumFilDat-- ){
               if( intNumFilDat >= 0 ){

                   String strCodCli1 =  (objTblMod.getValueAt(intNumFil, INT_TBL_DAT_COD_SOL)==null?"":objTblMod.getValueAt(intNumFil,INT_TBL_DAT_SOLICITANTE).toString());
                   String strCodCli2 =  (objTblMod.getValueAt(intNumFilDat, INT_TBL_DAT_COD_SOL)==null?"":objTblMod.getValueAt(intNumFilDat,INT_TBL_DAT_COD_SOL).toString());
                 
               }
            }

  }catch(Exception e) { objUti.mostrarMsgErr_F1(this,e);  }
}
      
       private void eventoVenConres(int intNumFil){
  try{

              int i =  intNumFil-1;
              for(int intNumFilDat=i; intNumFilDat >=  0;   intNumFilDat-- ){
               if( intNumFilDat >= 0 ){

                   String strCodres1 =  (objTblMod.getValueAt(intNumFil, INT_TBL_DAT_COD_RES)==null?"":objTblMod.getValueAt(intNumFil,INT_TBL_DAT_RESPONSABLE).toString());
                   String strCodres2 =  (objTblMod.getValueAt(intNumFilDat, INT_TBL_DAT_COD_RES)==null?"":objTblMod.getValueAt(intNumFilDat,INT_TBL_DAT_COD_RES).toString());
                 
               }
            }

  }catch(Exception e) { objUti.mostrarMsgErr_F1(this,e);  }
}
      
       private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
      
      private boolean validaCampos(){

         int intExiDatTbl=0;
          String strMens="Lista Tareas";

  



     for(int i=0; i<objTblMod.getRowCountTrue(); i++){
         intExiDatTbl=1;
      if( ((objTblMod.getValueAt(i, INT_TBL_DAT_FECHA)==null?"":(objTblMod.getValueAt(i, INT_TBL_DAT_FECHA).toString().equals("")?"":objTblMod.getValueAt(i, INT_TBL_DAT_FECHA).toString())).equals("")) ){
            MensajeInf("Digite la Fecha "+strMens+". ");
                tblDat.repaint();
                tblDat.requestFocus();
                tblDat.editCellAt(i, INT_TBL_DAT_FECHA);
                return false ;
          
      }
        
           if( ((objTblMod.getValueAt(i, INT_TBL_DAT_DES_COR)==null?"":(objTblMod.getValueAt(i, INT_TBL_DAT_DES_COR).toString().equals("")?"":objTblMod.getValueAt(i, INT_TBL_DAT_DES_COR).toString())).equals("")) ){
                MensajeInf("Descripciòn corta es obliagtoria  "+strMens+". ");
                tblDat.repaint();
                tblDat.requestFocus();
                tblDat.editCellAt(i, INT_TBL_DAT_DES_COR);
                return false;
          }
           
           
          objTblMod.removeEmptyRows();
           
          
           
      
     }


    if(intExiDatTbl==0){
        MensajeInf("NO HAY DATOS EN DETALLE INGRESE DATOS.... ");
        return false;
    }

   return true;
 }
      
         /// configurar el formulario 
       private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_Cod_Tar,"Cod.Tar.");
            vecCab.add(INT_TBL_DAT_Ubi_TAR,"Ubi.Tar.");
            vecCab.add(INT_TBL_DAT_TIP_TAR,"Tip.Tar.");
            vecCab.add(INT_TBL_DAT_TAR_PAD,"Tar.Pad.");
            vecCab.add(INT_TBL_DAT_NIV_TAR,"Niv.Tar.");
            vecCab.add(INT_TBL_DAT_FECHA,"Fecha");
            vecCab.add(INT_TBL_DAT_DES_COR,"Des.Cor.Tar");
            vecCab.add(INT_TBL_DAT_BUT_DES_COR,"..");
            vecCab.add(INT_TBL_DAT_DES_LAR,"Des.Lar.Tar");
            vecCab.add(INT_TBL_DAT_BUT_DES_LAR,"..");
            vecCab.add(INT_TBL_DAT_COD_SOL,"Cod.Sol");
            vecCab.add(INT_TBL_DAT_BUT_SOL,"..");
            vecCab.add(INT_TBL_DAT_SOLICITANTE,"Solicitante");
            
            /// PLANIFICACION 
            
            vecCab.add(INT_TBL_DAT_PRIORIDAD,"Prioridad");
            vecCab.add(INT_TBL_DAT_COD_RES,"Cod.Res.");
            vecCab.add(INT_TBL_DAT_But_RES,"..");
            vecCab.add(INT_TBL_DAT_RESPONSABLE,"Responsable");
            vecCab.add(INT_TBL_DAT_FEC_INI_PLA,"Fec.Ini.Pla");
            vecCab.add(INT_TBL_DAT_FEC_FIN_PLA,"Fec.Fin.Pla");
            
            // desarrollo
            vecCab.add(INT_TBL_DAT_TAR_PEND,"Pendiente");
            vecCab.add(INT_TBL_DAT_Tar_Anular,"Anular");
            vecCab.add(INT_TBL_DAT_TAR_PROCESO,"Proceso");
            vecCab.add(INT_TBL_DAT_TAR_DETENIDA,"Detenida");
            vecCab.add(INT_TBL_DAT_TAR_TERMINADA,"terminada");
            vecCab.add(INT_TBL_DAT_POR_TRA_REA,"%Tra.Rea");
            vecCab.add(INT_TBL_DAT_FEC_INI_REA,"Fe.Ini.Rea");
            vecCab.add(INT_TBL_DAT_FEC_ULT_TRA,"Fec.Ult.Tra");
            
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer el modelo de la tabla.
            vecDat=new Vector();    //Almacena los datos
            //vecDat.clear();            
            
            /// EL TIPO DE DATO QUE ALMACENA LA TABLA 
            objTblMod.setColumnDataType(INT_TBL_DAT_COD_SOL, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_COD_RES, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_POR_TRA_REA, objTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar ZafTblMod: Establecer las columnas que el modelo debe almacenar antes de eliminar una fila.
            ArrayList arlAux = new java.util.ArrayList();
            
            arlAux.add( INT_CFE_COD_tar,"" +INT_TBL_DAT_Cod_Tar);
            arlAux.add( INT_CFE_COD_tipar,"" +INT_TBL_DAT_TIP_TAR);
            arlAux.add( INT_CFE_COD_pad,"" +INT_TBL_DAT_TAR_PAD);
            //arlAux.add( INT_CFE_COD_nivel,"" +INT_TBL_DAT_NIV_TAR);
            
            objTblMod.setColsSaveBeforeRemoveRow(arlAux);
            arlAux=null;
                       
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
           // ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            
            //Configurar JTable: Establecer el menú de contexto.
            ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            TableColumnModel tcmAux=tblDat.getColumnModel();

            //objTblMod.setColumnDataType(INT_TBL_VALDOC, objTblMod.INT_COL_DBL, new Integer(0), null);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_Cod_Tar).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_Ubi_TAR).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_TIP_TAR).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_TAR_PAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NIV_TAR).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FECHA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_DAT_BUT_DES_COR).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BUT_DES_LAR).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BUT_DES_LAR).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_SOL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BUT_SOL).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_SOLICITANTE).setPreferredWidth(50);
            
            /// colunnana planificacion 
            tcmAux.getColumn(INT_TBL_DAT_PRIORIDAD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_RES).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_But_RES).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_RESPONSABLE).setPreferredWidth(50);
           
            tcmAux.getColumn(INT_TBL_DAT_FEC_INI_PLA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_FEC_FIN_PLA).setPreferredWidth(80);
            /// desarrollo 
            tcmAux.getColumn(INT_TBL_DAT_TAR_PEND).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_Tar_Anular).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_TAR_PROCESO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_TAR_DETENIDA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_TAR_TERMINADA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_POR_TRA_REA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FEC_INI_REA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_FEC_ULT_TRA).setPreferredWidth(80);
            
            /// editor de fecha 
            tcmAux.getColumn(INT_TBL_DAT_FECHA).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));
            tcmAux.getColumn(INT_TBL_DAT_FEC_INI_PLA).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));
            tcmAux.getColumn(INT_TBL_DAT_FEC_FIN_PLA).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));
   
            tcmAux.getColumn(INT_TBL_DAT_FEC_INI_REA).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));
            tcmAux.getColumn(INT_TBL_DAT_FEC_ULT_TRA).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_DAT_FECHA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR).setCellRenderer(objTblCelRenLbl);
            
            //// renderizar filas que  ckeck  Estados de la tarea 
            
             zafTblCelRenChkLab = new ZafTblCelRenChk();
             tcmAux.getColumn(INT_TBL_DAT_TAR_PEND).setCellRenderer(zafTblCelRenChkLab);
             
              
             zafTblCelRenChkLab = new ZafTblCelRenChk();
             tcmAux.getColumn(INT_TBL_DAT_Tar_Anular).setCellRenderer(zafTblCelRenChkLab);
             
             zafTblCelRenChkLab = new ZafTblCelRenChk();
             tcmAux.getColumn(INT_TBL_DAT_TAR_PROCESO).setCellRenderer(zafTblCelRenChkLab);
             
             zafTblCelRenChkLab = new ZafTblCelRenChk();
             tcmAux.getColumn(INT_TBL_DAT_TAR_DETENIDA).setCellRenderer(zafTblCelRenChkLab);
             
             zafTblCelRenChkLab = new ZafTblCelRenChk();
             tcmAux.getColumn(INT_TBL_DAT_TAR_TERMINADA).setCellRenderer(zafTblCelRenChkLab);

              objTblCelRenLblCol2=new ZafTblCelRenLbl();
              tcmAux.getColumn(INT_TBL_DAT_Cod_Tar).setCellRenderer(objTblCelRenLblCol2);
              //tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(objTblCelRenLblCol1);
             ////renderizar celda de color segun la tarea
             
             objTblCelRenLblCol2.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColBckWht;
                java.awt.Color colFonColBckGrn;
                String strLin="";

                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColBckWht=new java.awt.Color(255,255,255);
                    colFonColBckGrn=new java.awt.Color(228,228,203);

                    strLin=objTblMod.getValueAt(objTblCelRenLblCol2.getRowRender(), INT_TBL_DAT_TIP_TAR)==null?"":objTblMod.getValueAt(objTblCelRenLblCol2.getRowRender(), INT_TBL_DAT_TIP_TAR).toString();

                    if(strLin.equals("C")){
                        objTblCelRenLblCol2.setBackground(colFonColBckGrn);
                    }
                    else{
                        objTblCelRenLblCol2.setBackground(colFonColBckWht);
                    }
                }
            });
             
             
             
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BUT_DES_COR).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_DES_LAR).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_SOL).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_But_RES).setResizable(false);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
             
            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
                        
             vecAux.add("" + INT_TBL_DAT_FECHA);
             vecAux.add("" + INT_TBL_DAT_COD_SOL);
             vecAux.add("" + INT_TBL_DAT_COD_RES);
             vecAux.add("" + INT_TBL_DAT_FEC_INI_PLA);
             vecAux.add("" + INT_TBL_DAT_FEC_FIN_PLA);
             vecAux.add("" + INT_TBL_DAT_Tar_Anular);
             vecAux.add("" + INT_TBL_DAT_TAR_DETENIDA);
             vecAux.add("" + INT_TBL_DAT_TAR_TERMINADA);
             vecAux.add("" + INT_TBL_DAT_POR_TRA_REA);
             vecAux.add("" + INT_TBL_DAT_FEC_INI_REA);
             vecAux.add("" + INT_TBL_DAT_FEC_ULT_TRA);
            
            ///  botones 
            vecAux.add("" + INT_TBL_DAT_BUT_DES_COR);
            vecAux.add("" + INT_TBL_DAT_BUT_DES_LAR);
            vecAux.add("" + INT_TBL_DAT_BUT_SOL);
            vecAux.add("" + INT_TBL_DAT_But_RES);
            
            /// check 
            vecAux.add("" + INT_TBL_DAT_TAR_PEND);
            vecAux.add("" + INT_TBL_DAT_Tar_Anular);
            vecAux.add("" + INT_TBL_DAT_TAR_PROCESO);
            vecAux.add("" + INT_TBL_DAT_TAR_DETENIDA);
            vecAux.add("" + INT_TBL_DAT_TAR_TERMINADA);
            
            // combobox editable
            
             vecAux.add("" + INT_TBL_DAT_PRIORIDAD);
            
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            //*** configurar las filas ocultas ****/////
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_Ubi_TAR, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TIP_TAR, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TAR_PAD, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NIV_TAR, tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Editor de búsqueda.
            //objTblBus=new ZafTblBus(tblDat);
            
           
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);  
            
            // renderizar celdas labels
             objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_BUT_SOL).setCellRenderer(objTblCelRenLbl);
            
            
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_But_RES).setCellRenderer(objTblCelRenLbl);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_DES_COR).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_DES_LAR).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_SOL).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
             objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_But_RES).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            //Configurar JTable: Editor de celdas.
            
            // EDICION DE LA CELDA descripcion corta 
                       
            //objCxP03_01=new ZafCxP03_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objMae67_01_1=new ZafRecHum31_01 (javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButDlgCor= new ZafTblCelEdiButDlg(objMae67_01_1);
            tcmAux.getColumn(INT_TBL_DAT_BUT_DES_COR).setCellEditor(objTblCelEdiButDlgCor);
            objTblCelEdiButDlgCor.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intSelFil=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intSelFil = tblDat.getSelectedRow();
                    objMae67_01_1.setContenido(""+ (objTblMod.getValueAt(intSelFil, INT_TBL_DAT_DES_COR)==null?"":objTblMod.getValueAt(intSelFil, INT_TBL_DAT_DES_COR).toString())   );
                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblMod.setValueAt(objMae67_01_1.getContenido(), intSelFil, INT_TBL_DAT_DES_COR);                  
                    llamarVenDesCor(intSelFil);
                    confTblAuxiliar(intSelFil);
                    
                    
                    
                }
            });
            
            
            objMae67_01_2=new ZafRecHum31_01 (javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButDlgLar= new ZafTblCelEdiButDlg(objMae67_01_2);
            tcmAux.getColumn(INT_TBL_DAT_BUT_DES_LAR).setCellEditor(objTblCelEdiButDlgLar);
            objTblCelEdiButDlgLar.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intSelFil=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intSelFil = tblDat.getSelectedRow();
                    objMae67_01_2.setContenido(""+ (objTblMod.getValueAt(intSelFil, INT_TBL_DAT_DES_LAR)==null?"":objTblMod.getValueAt(intSelFil, INT_TBL_DAT_DES_LAR).toString())   );
                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblMod.setValueAt(objMae67_01_2.getContenido(), intSelFil, INT_TBL_DAT_DES_LAR);                  
                }
            });
 
            
            
            
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_But_RES).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void actionPerformed(ZafTableEvent evt) {
                    mostrarVenConRes(0);
                }
            });
            
            
            
            /// combo 
            objTblCelRenCmbBoxFec = new ZafTblCelRenCbo();
            tcmAux.getColumn(INT_TBL_DAT_PRIORIDAD).setCellRenderer(objTblCelRenCmbBoxFec);
            objTblCelRenCmbBoxFec = null;

            objTblCelEdiCmbBoxFec = new ZafTblCelEdiCbo(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_PRIORIDAD).setCellEditor(objTblCelEdiCmbBoxFec);
            objTblCelEdiCmbBoxFec.addItem("");
            objTblCelEdiCmbBoxFec.addItem("Alta");
            objTblCelEdiCmbBoxFec.addItem("Media");
            objTblCelEdiCmbBoxFec.addItem("Baja");
           
         
               /// EDITOR DE LA TAREA 
       
             objTblCelEdiCmbBoxFec.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                     int intSelFil = tblDat.getSelectedRow();
                  
                }
                
                public void afterEdit(ZafTableEvent evt){
                    
                  
                }
                
            });
            
            
            /// celda solicitante 
            int intColsol[]=new int[2];
            intColsol[0]=1;
            intColsol[1]=2;

            int intColTblCli[]=new int[2];
            intColTblCli[0]=INT_TBL_DAT_COD_SOL;
            intColTblCli[1]=INT_TBL_DAT_SOLICITANTE;

            objTblCelEdiTxtVcoCli=new ZafTblCelEdiTxtVco(tblDat,   vcosol, intColsol, intColTblCli );
            tcmAux.getColumn(INT_TBL_DAT_COD_SOL).setCellEditor(objTblCelEdiTxtVcoCli);
            tcmAux.getColumn(INT_TBL_DAT_SOLICITANTE).setCellEditor(objTblCelEdiTxtVcoCli);
            objTblCelEdiTxtVcoCli.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                int intSelFil = tblDat.getSelectedRow();



            }
            public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

               if(tblDat.getSelectedColumn()==INT_TBL_DAT_COD_SOL)  
                   vcosol.setCampoBusqueda(0);
               else   
                   vcosol.setCampoBusqueda(0);

                  vcosol.setCriterio1(11);
            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                 int intNumFil = tblDat.getSelectedRow();
                  
                 if (objTblCelEdiTxtVcoCli.isConsultaAceptada()) {
                      eventoVenConCli(intNumFil);
                 }
               

            }
        });
           /// boton jdialogo de  solicitante 
            
             objTblCelEdiButvco=new ZafTblCelEdiButVco(tblDat, vcosol, intColsol, intColTblCli);
            tcmAux.getColumn(INT_TBL_DAT_BUT_SOL).setCellEditor(objTblCelEdiButvco);
            objTblCelEdiButvco.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    if (objTblCelEdiButvco.isConsultaAceptada())
                    {
                          eventoVenConCli(intNumFil);
                      
                    }
                }
            });
          
            /// edicion de la celda del solicitante 
            
            int intColres[]=new int[2];
            intColres[0]=1;
            intColres[1]=2;
            
            int intColTblres[]=new int[2];
            intColTblres[0]=INT_TBL_DAT_COD_RES;
            intColTblres[1]=INT_TBL_DAT_RESPONSABLE;
            
            objTblCelEdiTxtVcores=new ZafTblCelEdiTxtVco(tblDat,   vcosol, intColres, intColTblres );
            tcmAux.getColumn(INT_TBL_DAT_COD_RES).setCellEditor(objTblCelEdiTxtVcores);
            tcmAux.getColumn(INT_TBL_DAT_RESPONSABLE).setCellEditor(objTblCelEdiTxtVcores);
            objTblCelEdiTxtVcores.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
              
                
                 
            }
            public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

               if(tblDat.getSelectedColumn()==INT_TBL_DAT_COD_RES)  
                   vcosol.setCampoBusqueda(0);
               else   
                   vcosol.setCampoBusqueda(0);

                vcosol.setCriterio1(11);
            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                 int intNumFil1 = tblDat.getSelectedRow();
                  
                 if (objTblCelEdiTxtVcores.isConsultaAceptada()) {
                      eventoVenConres(intNumFil1);
                 }
               

            }
        });
            
             objTblCelEdiButvco=new ZafTblCelEdiButVco(tblDat, vcores, intColres, intColTblres);
            tcmAux.getColumn(INT_TBL_DAT_But_RES).setCellEditor(objTblCelEdiButvco);
            objTblCelEdiButvco.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    if (objTblCelEdiButvco.isConsultaAceptada())
                    {
                          eventoVenConres(intNumFil);
                      
                    }
                }
            });
         
            
            //// validar  estado de la tarea
            
            objTblCelEdiChkPen=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_TAR_PEND).setCellEditor(objTblCelEdiChkPen);
              objTblCelEdiChkPen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                      
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                     int intCol = tblDat.getSelectedRow();
                    objTblMod.setValueAt( false, intCol, INT_TBL_DAT_Tar_Anular);
                    objTblMod.setValueAt( false, intCol, INT_TBL_DAT_TAR_PROCESO);
                    objTblMod.setValueAt( false, intCol, INT_TBL_DAT_TAR_DETENIDA);
                    objTblMod.setValueAt( false, intCol, INT_TBL_DAT_TAR_TERMINADA);
                    
                }
            });
            
            
            objTblCelEdiChkAnul=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_Tar_Anular).setCellEditor(objTblCelEdiChkAnul);
            objTblCelEdiChkAnul.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                  
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCol = tblDat.getSelectedRow();
                    objTblMod.setValueAt( false, intCol, INT_TBL_DAT_TAR_PEND);
                    objTblMod.setValueAt( false, intCol, INT_TBL_DAT_TAR_PROCESO);
                     objTblMod.setValueAt( false, intCol, INT_TBL_DAT_TAR_DETENIDA);
                    objTblMod.setValueAt( false, intCol, INT_TBL_DAT_TAR_TERMINADA);
                    
                }
            });
            
             objTblCelEdiChkPro=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_TAR_PROCESO).setCellEditor(objTblCelEdiChkPro);
            objTblCelEdiChkPro.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                  
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCol = tblDat.getSelectedRow();
                    objTblMod.setValueAt( false, intCol, INT_TBL_DAT_TAR_PEND);
                    objTblMod.setValueAt( false, intCol, INT_TBL_DAT_Tar_Anular);
                    objTblMod.setValueAt( false, intCol, INT_TBL_DAT_TAR_DETENIDA);
                    objTblMod.setValueAt( false, intCol, INT_TBL_DAT_TAR_TERMINADA);
                    
                }
            });
            
            
             objTblCelEdiTermi=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_TAR_TERMINADA).setCellEditor(objTblCelEdiTermi);
            objTblCelEdiTermi.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                  
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCol = tblDat.getSelectedRow();
                    objTblMod.setValueAt( new Boolean(false), intCol, INT_TBL_DAT_TAR_PEND);
                    objTblMod.setValueAt( new Boolean(false), intCol, INT_TBL_DAT_TAR_PROCESO);
                    objTblMod.setValueAt( new Boolean(false), intCol, INT_TBL_DAT_TAR_DETENIDA);
                    objTblMod.setValueAt( new Boolean(false), intCol, INT_TBL_DAT_Tar_Anular);
                    
                }
            });
            
            
             objTblCeldeteni=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_TAR_DETENIDA).setCellEditor(objTblCeldeteni);
            objTblCeldeteni.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                  
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCol = tblDat.getSelectedRow();
                     objTblMod.setValueAt( new Boolean(false), intCol, INT_TBL_DAT_TAR_PEND);
                     objTblMod.setValueAt( new Boolean(false), intCol, INT_TBL_DAT_TAR_PROCESO);
                     objTblMod.setValueAt( new Boolean(false), intCol, INT_TBL_DAT_TAR_TERMINADA);
                     objTblMod.setValueAt( new Boolean(false), intCol, INT_TBL_DAT_Tar_Anular);
                    
                }
            });
            
              objportrarea=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_POR_TRA_REA).setCellEditor(objportrarea);
         
           
            
            objportrarea.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int filsel = tblDat.getSelectedRow();
                    double  porcentaje =objUti.parseDouble(objTblMod.getValueAt(filsel,INT_TBL_DAT_POR_TRA_REA )) ;
                   
                    if (porcentaje==0){
                          objTblMod.setValueAt(  (true), filsel, INT_TBL_DAT_TAR_PEND);
                          objTblMod.setValueAt(  (false), filsel, INT_TBL_DAT_TAR_PROCESO);
                          objTblMod.setValueAt(  (false), filsel, INT_TBL_DAT_TAR_DETENIDA);
                          objTblMod.setValueAt(  (false), filsel, INT_TBL_DAT_TAR_TERMINADA);
                          objTblMod.setValueAt(  (false), filsel, INT_TBL_DAT_Tar_Anular);}
                    else if (porcentaje==100)
                    {
                          objTblMod.setValueAt(  (false), filsel, INT_TBL_DAT_TAR_PEND);
                          objTblMod.setValueAt(  (false), filsel, INT_TBL_DAT_TAR_PROCESO);
                          objTblMod.setValueAt(  (false), filsel, INT_TBL_DAT_TAR_DETENIDA);
                          objTblMod.setValueAt(  (true), filsel,  INT_TBL_DAT_TAR_TERMINADA);
                          objTblMod.setValueAt(  (false), filsel, INT_TBL_DAT_Tar_Anular);
                         
                                             
                    }
                    
                   else 
                    {
                          objTblMod.setValueAt(  (false), filsel, INT_TBL_DAT_TAR_PEND);
                          objTblMod.setValueAt(  (true), filsel, INT_TBL_DAT_TAR_PROCESO);
                          objTblMod.setValueAt(  (false), filsel, INT_TBL_DAT_TAR_DETENIDA);
                          objTblMod.setValueAt(  (false), filsel,  INT_TBL_DAT_TAR_TERMINADA);
                          objTblMod.setValueAt(  (false), filsel, INT_TBL_DAT_Tar_Anular);
                    }
                    
                }
                
               
                
            });
  
            
            
                              
            
            //AGRUPAR COLUMNAS
            objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*2);

            objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" DATOS DE LAS TAREAS " );
            objTblHeaColGrpAmeSur.setHeight(16);            
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_LIN));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_Cod_Tar));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_Ubi_TAR));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TIP_TAR));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TAR_PAD));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_NIV_TAR));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_FECHA));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_DES_COR));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_BUT_DES_COR));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_DES_LAR));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_BUT_DES_LAR));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_COD_SOL));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_BUT_SOL));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_SOLICITANTE));
            
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;

            objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" PLANIFICACION " );
            objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_PRIORIDAD));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_COD_RES));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_But_RES));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_RESPONSABLE));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_FEC_INI_PLA));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_FEC_FIN_PLA));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;

            objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" DESARROLLO " );
            objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TAR_PEND));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_Tar_Anular));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TAR_PROCESO));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TAR_DETENIDA));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TAR_TERMINADA));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_POR_TRA_REA));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_FEC_INI_REA));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_FEC_ULT_TRA));           
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;

            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            //objTblOrd=new ZafTblOrd(tblDat);
            
            tcmAux=null;  
            this.setTitle( objParSis.getNombreMenu() +" "+ strVersion);    
            lblTit.setText(objParSis.getNombreMenu());
           
            radTodCli.setSelected(true);
           // radCliTod.setSelected(true);
            
            objTblPopMnu = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
           
           objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
               
               public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                 if (objTblPopMnu.isClickInsertarFila())
                  {
                     
                        if (tblDat.getSelectedRow()==0)
                       
                           objTblPopMnu.cancelarClick();
                           
                       
                 }
                    else if (objTblPopMnu.isClickEliminarFila())
                  {
                        int intFilSel[] = tblDat.getSelectedRows();
                        boolean codpad=false ;
                        int codtarea =0;
                        int codpadre=0;
                        int codtardes=0;
                        int codsubhija=0;
                                
                       
                        for (int i=0 ; i<intFilSel.length;i++)
                        {
                            if (objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_TIP_TAR).equals("C"))
                            {
                              
                                
                                
                                codpad=true;
                                codtarea=Integer.parseInt(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_Cod_Tar)==null?"0":(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_Cod_Tar).toString().equals("")?"0":objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_Cod_Tar).toString()));
                               
                                
                                
                         }
                        
                         if (codpad==true)
                         {
                         if (mostrarMsgCon("Las tareas que  usted desea eliminar contiene subtareas\"¿Deseea eliminarlas?")==0)
                         {                              
                             codpad=false;
                             elihijos=true;
                             hijoseliminados(codtarea, i);    
                         }
                         
                         else 
                         {
                             objTblPopMnu.cancelarClick();
                         }
                         }
                      
//        
                 }
               }
            }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                   if (objTblPopMnu.isClickInsertarFila())
                 {
                     //Escriba aquí el código que se debe realizar luego de insertar la fila.
                      System.out.println("afterClick: isClickInsertarFila");
                 }
                 else if (objTblPopMnu.isClickEliminarFila())
                 {
                  
                   if (objTblMod.getRowCount()==1)
                   {
                      elimihijos();
                   }
                                   
                 }
              }
          });
             
             
             
            objTblFilCab=null;
     
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);  
        }
        return blnRes;
    }
  
      
       
       
       
       /// funcion que guarda los hijos para eliminarlos 
       
       private void hijoseliminados (int codtar,int fil) 
       {
            int codtarea =0;
            int codpadre=0;
            int codtardes=0;
            int codsubhija=0;
            int modtrabaja=0;
          
           if (objTblModAux.getRowCountTrue()>objTblMod.getRowCountTrue())
                modtrabaja=objTblModAux.getRowCountTrue();
           else 
               modtrabaja=objTblMod.getRowCountTrue();
           
           for (int hijos=fil;hijos<modtrabaja;hijos++)
            {
              
                 if (objTblModAux.getRowCountTrue()>objTblMod.getRowCountTrue())
                 {
                            codpadre=Integer.parseInt(objTblModAux.getValueAt(hijos, INT_TBL_DAT_TAR_PAD)==null?"0":(objTblModAux.getValueAt(hijos, INT_TBL_DAT_TAR_PAD).toString().equals("")?"0":objTblModAux.getValueAt(hijos, INT_TBL_DAT_TAR_PAD).toString())); 
                            codtardes=Integer.parseInt(objTblModAux.getValueAt(hijos, INT_TBL_DAT_Cod_Tar)==null?"0":(objTblModAux.getValueAt(hijos, INT_TBL_DAT_Cod_Tar).toString().equals("")?"0":objTblModAux.getValueAt(hijos, INT_TBL_DAT_Cod_Tar).toString()));
                            codsubhija=Integer.parseInt(objTblModAux.getValueAt(hijos, INT_TBL_DAT_TAR_PAD)==null?"0":(objTblModAux.getValueAt(hijos, INT_TBL_DAT_TAR_PAD).toString().equals("")?"0":objTblModAux.getValueAt(hijos, INT_TBL_DAT_TAR_PAD).toString()));
                            if (codtar==codpadre)
                        {

                            arlelihijos.add(INT_CFE_COD_tar,objTblModAux.getValueAt(hijos,INT_TBL_DAT_Cod_Tar));
                            arlelihijos.add(INT_CFE_COD_tipar,objTblModAux.getValueAt(hijos,INT_TBL_DAT_TIP_TAR));
                            arlelihijos.add(INT_CFE_COD_pad,objTblModAux.getValueAt(hijos,INT_TBL_DAT_TAR_PAD));
                          
                            objTblMod.addRowDataSavedBeforeRemoveRow(arlelihijos);

                        }
                         for(int i=hijos; i<modtrabaja;i++)
                         {
                           codsubhija=Integer.parseInt(objTblModAux.getValueAt(i, INT_TBL_DAT_TAR_PAD)==null?"0":(objTblModAux.getValueAt(i, INT_TBL_DAT_TAR_PAD).toString().equals("")?"0":objTblModAux.getValueAt(i, INT_TBL_DAT_TAR_PAD).toString()));
                        if (codtardes==codsubhija)
                        {
                            arlelihijos.add(INT_CFE_COD_tar,objTblModAux.getValueAt(i,INT_TBL_DAT_Cod_Tar));
                            arlelihijos.add(INT_CFE_COD_tipar,objTblModAux.getValueAt(i,INT_TBL_DAT_TIP_TAR));
                            arlelihijos.add(INT_CFE_COD_pad,objTblModAux.getValueAt(i,INT_TBL_DAT_TAR_PAD));
                           
                            objTblMod.addRowDataSavedBeforeRemoveRow(arlelihijos);
                        }
                        }
                 }
                 else 
                 {
                            codpadre=Integer.parseInt(objTblMod.getValueAt(hijos, INT_TBL_DAT_TAR_PAD)==null?"0":(objTblMod.getValueAt(hijos, INT_TBL_DAT_TAR_PAD).toString().equals("")?"0":objTblMod.getValueAt(hijos, INT_TBL_DAT_TAR_PAD).toString())); 
                            codtardes=Integer.parseInt(objTblMod.getValueAt(hijos, INT_TBL_DAT_Cod_Tar)==null?"0":(objTblMod.getValueAt(hijos, INT_TBL_DAT_Cod_Tar).toString().equals("")?"0":objTblMod.getValueAt(hijos, INT_TBL_DAT_Cod_Tar).toString()));
                            codsubhija=Integer.parseInt(objTblMod.getValueAt(hijos, INT_TBL_DAT_TAR_PAD)==null?"0":(objTblMod.getValueAt(hijos, INT_TBL_DAT_TAR_PAD).toString().equals("")?"0":objTblMod.getValueAt(hijos, INT_TBL_DAT_TAR_PAD).toString()));
                               
                            
                            if (codtar==codpadre)
                            {

                                arlelihijos.add(INT_CFE_COD_tar,objTblMod.getValueAt(hijos,INT_TBL_DAT_Cod_Tar));
                                arlelihijos.add(INT_CFE_COD_tipar,objTblMod.getValueAt(hijos,INT_TBL_DAT_TIP_TAR));
                                arlelihijos.add(INT_CFE_COD_pad,objTblMod.getValueAt(hijos,INT_TBL_DAT_TAR_PAD));
                                
                                objTblMod.addRowDataSavedBeforeRemoveRow(arlelihijos);

                            }
                             for(int i=hijos; i<modtrabaja;i++)
                             {
                             codsubhija=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_TAR_PAD)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_TAR_PAD).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_TAR_PAD).toString()));

                            if (codtardes==codsubhija)
                            {
                                arlelihijos.add(INT_CFE_COD_tar,objTblMod.getValueAt(i,INT_TBL_DAT_Cod_Tar));
                                arlelihijos.add(INT_CFE_COD_tipar,objTblMod.getValueAt(i,INT_TBL_DAT_TIP_TAR));
                                arlelihijos.add(INT_CFE_COD_pad,objTblMod.getValueAt(i,INT_TBL_DAT_TAR_PAD));
                               
                                objTblMod.addRowDataSavedBeforeRemoveRow(arlelihijos);
                            }
                             }
                 }
            
                
               

    }
                                 
        
       }
       
      /// llama las validacionesque se deben hacer para ingresar una descripcion
     
       
       
       
       
       private void llamarVenDesCor(int intFil){           
             int tarpadre=0;
             int intCodmod=0;
             int tarpadan=0;
            if(objMae67_01_1.isAceptar())
            {
               intCodmod = Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_Cod_Tar)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_Cod_Tar).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_Cod_Tar).toString()));
               tarpadan=Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_Cod_Tar)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_Cod_Tar).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_Cod_Tar).toString()));
                if (  objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN).toString().equals("I")&& intCodmod==0)
                {
                        objTblMod.setValueAt(objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), "dd/MM/yyyy") , intFil, INT_TBL_DAT_FECHA );
                        objTblMod.setValueAt(  (true), intFil, INT_TBL_DAT_TAR_PEND);
                        //codtargen(intFil);

                        intMaxCodTar++;
                        intMaxUbi++;

                        objTblMod.setValueAt(intMaxCodTar,intFil, INT_TBL_DAT_Cod_Tar); 
                        objTblMod.setValueAt(intMaxUbi,intFil, INT_TBL_DAT_Ubi_TAR); 


                        objTblMod.setValueAt("C", intFil, INT_TBL_DAT_TIP_TAR);
                        int nivel=0;
                        int fil=intFil;
                        int codpadr=0;
                        
                    //// validar si la tarea  que se ingrese es por primera vez
                    if (intFil==0)
                    {
                        tblDat.setValueAt(0, intFil, INT_TBL_DAT_TAR_PAD);
                        objTblMod.setValueAt(1,intFil , INT_TBL_DAT_NIV_TAR);
                    }
                    else 
                    {
                      /// que vaida el padre de nivel 1  
                        for (int niv=fil;niv>=0; niv--)
                        {
                            nivel = Integer.parseInt(objTblMod.getValueAt(niv, INT_TBL_DAT_NIV_TAR)==null?"0":(objTblMod.getValueAt(niv, INT_TBL_DAT_NIV_TAR).toString().equals("")?"0":objTblMod.getValueAt(niv, INT_TBL_DAT_NIV_TAR).toString()));
                            codpadr = Integer.parseInt(objTblMod.getValueAt(niv, INT_TBL_DAT_Cod_Tar)==null?"0":(objTblMod.getValueAt(niv, INT_TBL_DAT_Cod_Tar).toString().equals("")?"0":objTblMod.getValueAt(niv, INT_TBL_DAT_Cod_Tar).toString()));
                            if (nivel==1)
                            objTblMod.setValueAt(codpadr,intFil , INT_TBL_DAT_TAR_PAD);
                            objTblMod.setValueAt(1,intFil , INT_TBL_DAT_NIV_TAR);
                            
                            
                         }
                     }      

                }
                
                {  
                     String strAux="";
                     int intNiv =Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_NIV_TAR)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_NIV_TAR).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_NIV_TAR).toString()));
                            for (int j=1; j<intNiv; j++)
                                   strAux+="        ";
                      objTblMod.setValueAt(strAux+objTblMod.getValueAt(intFil , INT_TBL_DAT_DES_COR).toString().trim() ,intFil , INT_TBL_DAT_DES_COR);
                           
                }
            }
            
            
             
     }
      
          
       /// fitro de la consulta 
       
      private String filtro(){
        String strAux=" ", strcodres= "", strPrioridad="";
         strcodres="and co_trares ="+ txtCodres.getText().trim();
         if (cbo_prio.getSelectedIndex()==0) strPrioridad=" and ne_pri in (0,1,2,3)" ;
         if (cbo_prio.getSelectedIndex()==1) strPrioridad=" and ne_pri=1 ";
         if (cbo_prio.getSelectedIndex()==2) strPrioridad=" and ne_pri=2 ";
         if (cbo_prio.getSelectedIndex()==3) strPrioridad=" and ne_pri=3 ";
         
       
         
          if(radTodCli.isSelected()&&cbo_fec_tar.getSelectedIndex()==-1 ) 
              strAux = strAux + " ";
          
          else strAux = strAux + fechaconsultatarea();
        
          
            
            if (radFilCli.isSelected())
             
                
            
                
                if(!txtCodres.getText().trim().equals(""))
                { 
                    strAux = strAux + fechaconsultatarea()+strcodres;
                        
                        
               }
                
               
                  if(cbo_prio.getSelectedIndex()==0)
                      
                  strAux = strAux+strPrioridad;
                 // else strAux = strAux + fechaconsultatarea()+strPrioridad;
                  
                  if(cbo_prio.getSelectedIndex()==1)
                      
                  strAux = strAux +strPrioridad;
                 // else strAux =  fechaconsultatarea()+strPrioridad;
                  
                  if(cbo_prio.getSelectedIndex()==2)
                      
                  strAux = strAux +strPrioridad;
                  //else strAux =  fechaconsultatarea()+strPrioridad;
                  
                  if(cbo_prio.getSelectedIndex()==3)
                      
                  strAux = strAux +strPrioridad;
                  //else strAux =  fechaconsultatarea()+strPrioridad;
                 
         
            
        
        return strAux;
    }
      
      /// cargar detalle de registro 
       private String fechaconsultatarea () {
          String strconfectar="";
           
          if( cbo_fec_tar.getSelectedIndex()==1) {
             //strconfectar = strconfectar  + " and fe_tar  BETWEEN CAST ('"+fecdesde+"' AS DATE) AND CAST ('"+fechasta+"' AS DATE) ";
              switch (objSelFec.getTipoSeleccion())
         {
                    case 0: //Búsqueda por rangos
                        strconfectar=strconfectar+" AND fe_tar BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strconfectar=strconfectar+" AND fe_tar<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strconfectar=strconfectar+" AND fe_tar>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
        }
          }
        
         if( cbo_fec_tar.getSelectedIndex()==2) {
          // strconfectar = strconfectar + " and  fe_inipla BETWEEN CAST ('"+fecdesde+"' AS DATE) AND CAST ('"+fechasta+"' AS DATE) ";
               switch (objSelFec.getTipoSeleccion())
               {
                    case 0: //Búsqueda por rangos
                        strconfectar=strconfectar+" AND fe_inipla BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strconfectar=strconfectar+" AND fe_inipla<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strconfectar=strconfectar+" AND fe_inipla>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
                }      
          }
             
          if( cbo_fec_tar.getSelectedIndex()==3) {
           //  strconfectar = strconfectar + " and fe_finpla BETWEEN CAST ('"+fecdesde+"' AS DATE) AND CAST ('"+fechasta+"' AS DATE) ";
                switch (objSelFec.getTipoSeleccion())
         {
                    case 0: //Búsqueda por rangos
                        strconfectar=strconfectar+" AND fe_finpla BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strconfectar=strconfectar+" AND fe_finpla<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strconfectar=strconfectar+" AND fe_finpla>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
        }      
        
         }
           if( cbo_fec_tar.getSelectedIndex()==4) {
           // strconfectar= strconfectar  + " and fe_inirea BETWEEN CAST ('"+fecdesde+"' AS DATE) AND CAST ('"+fechasta+"' AS DATE) ";
            switch (objSelFec.getTipoSeleccion())
         {
                    case 0: //Búsqueda por rangos
                        strconfectar=strconfectar+" AND fe_inirea BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strconfectar=strconfectar+" AND fe_inirea<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strconfectar=strconfectar+" AND fe_inirea>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
        }      
          
         }
         
            if( cbo_fec_tar.getSelectedIndex()==5) {
           //  strconfectar = strconfectar  + " and fe_ulttrarea  BETWEEN CAST ('"+fecdesde+"' AS DATE) AND CAST ('"+fechasta+"' AS DATE) ";
             switch (objSelFec.getTipoSeleccion())
         {
                    case 0: //Búsqueda por rangos
                        strconfectar=strconfectar+" AND fe_ulttrarea BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strconfectar=strconfectar+" AND fe_ulttrarea<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strconfectar=strconfectar+" AND fe_ulttrarea>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
        }      
         }
          
          return strconfectar;
        }        
       
       
      private boolean cargarDetReg(String strAux){
        int intNiv, j;     
        boolean blnRes = false;
        String str_nepad="", strEstConf="",  strSqlAux="";
        butCon.setText("Detener");
        lblMsgSis.setText("Obteniendo datos...");
          if(chkTarPen.isSelected())     { if(strEstConf.equals("")) strEstConf+="'P'"; else strEstConf+=",'P'"; }
          if(chkTarAnulada.isSelected()) { if(strEstConf.equals("")) strEstConf+="'A'"; else strEstConf+=",'A'"; }
          if(chkTarPro.isSelected())     { if(strEstConf.equals("")) strEstConf+="'O'"; else strEstConf+=",'O'"; }
          if(chkTarDet.isSelected())    { if(strEstConf.equals("")) strEstConf+="'D'"; else strEstConf+=",'D'"; }
          if(chkTarter.isSelected())   { if(strEstConf.equals("")) strEstConf+="'T'"; else strEstConf+=",'T'"; }
          if(!strEstConf.equals(""))
            strSqlAux = " and a.st_reg IN ("+strEstConf+") ";  
      
        if(radTodCli.isSelected())
        {
            strSql= " select a.co_tar , a.ne_ubi , a.tx_tip, a.ne_pad ,a.ne_niv , "
                    + "a.fe_tar ,a.tx_descor,a.tx_deslar , a.co_trasol,b.tx_nom||' '||b.tx_ape as solicitante, a.ne_pri ,a.co_trares ,CASE WHEN a.ne_pri=1 THEN 'Alta'  WHEN a.ne_pri=2 THEN 'Media' WHEN a.ne_pri=3 THEN 'Baja'ELSE '' END  ,c.tx_nom||' '||c.tx_ape as responsable ,"
                    + " a.fe_inipla,a.fe_finpla, a.st_reg, a.nd_portrarea, a.fe_inirea,a.fe_ulttrarea from tbm_tartra  a "
                    + " left outer  JOIN TBM_TRA b on ( a.co_trasol=b.co_tra) "
                    + "left outer  join tbm_tra c on (a.co_trares=c.co_tra) "
                    + "where a.co_dep = "+txtCodDep.getText()+""+strSqlAux+""+strAux+"order by a.ne_ubi";
        }
        
        
         if(radFilCli.isSelected())
        {
            strSql= " select a.co_tar , a.ne_ubi , a.tx_tip, a.ne_pad ,a.ne_niv ,"
                    + " a.fe_tar ,a.tx_descor,a.tx_deslar , a.co_trasol,b.tx_nom||' '||b.tx_ape as solicitante, "
                    + "a.ne_pri ,a.co_trares ,CASE WHEN a.ne_pri=1 THEN 'Alta'  WHEN a.ne_pri=2 THEN 'Media' WHEN a.ne_pri=3 THEN 'Baja' ELSE '' END"
                    + "  ,c.tx_nom||' '||c.tx_ape as responsable , a.fe_inipla,a.fe_finpla, a.st_reg, a.nd_portrarea, "
                    + "a.fe_inirea,a.fe_ulttrarea from tbm_tartra  a "
                    + " left outer  JOIN TBM_TRA b on ( a.co_trasol=b.co_tra) "
                    + "left outer  join tbm_tra c on (a.co_trares=c.co_tra) "
                    + "where a.co_dep = "+txtCodDep.getText()+""+strSqlAux+""+strAux +"order by a.ne_ubi";
        }
         
        
        
       
       
        
        System.out.println("ZafRechum.cargarDetReg: " + strSql);
        
        try{             
            conn=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stm=conn.createStatement();
            rst=stm.executeQuery(strSql);

            vecDat.clear();
            
            lblMsgSis.setText("Cargando datos...");
            
            while(rst.next()){
                vecReg=new Vector();
                vecReg.add(INT_TBL_DAT_LIN,"");
                vecReg.add(INT_TBL_DAT_Cod_Tar,rst.getString("co_tar"));
                vecReg.add(INT_TBL_DAT_Ubi_TAR,rst.getString("ne_ubi"));
                vecReg.add(INT_TBL_DAT_TIP_TAR,rst.getString("tx_tip"));
                str_nepad=rst.getString("ne_pad"); 
                vecReg.add(INT_TBL_DAT_TAR_PAD,str_nepad);
                vecReg.add(INT_TBL_DAT_NIV_TAR,rst.getString("ne_niv"));
                 intNiv=rst.getInt("ne_niv");
               // vecReg.add(INT_TBL_DAT_FECHA,rst.getString("fe_tar"));
                vecReg.add(INT_TBL_DAT_FECHA, objUti.formatearFecha(rst.getDate("fe_tar"), "dd/MM/yyyy") );
                /// validacion de los niveles de las tareas 
                strAux="";
                        for (j=1; j<intNiv; j++)
                            strAux+="        ";
                               
                vecReg.add(INT_TBL_DAT_DES_COR,strAux + rst.getString("tx_descor"));
                vecReg.add(INT_TBL_DAT_BUT_DES_COR,(""));
                
                vecReg.add(INT_TBL_DAT_DES_LAR,rst.getString("tx_deslar"));
                vecReg.add(INT_TBL_DAT_BUT_DES_LAR,("tx_descor"));
                
                vecReg.add(INT_TBL_DAT_COD_SOL,rst.getString("co_trasol"));
                vecReg.add(INT_TBL_DAT_BUT_SOL,(""));
                vecReg.add(INT_TBL_DAT_SOLICITANTE,rst.getString("solicitante"));
                vecReg.add(INT_TBL_DAT_PRIORIDAD,rst.getString("case"));
                String strPerFec ;
                
                 strPerFec=rst.getString("ne_pri");
                 
                 
                //objTblCelEdiCmbBoxFec.setSelectedItem(INT_TBL_DAT_PRIORIDAD);   
                vecReg.add(INT_TBL_DAT_COD_RES,rst.getString("co_trares"));
                vecReg.add(INT_TBL_DAT_But_RES,(""));
                vecReg.add(INT_TBL_DAT_RESPONSABLE,rst.getString("responsable"));
                vecReg.add(INT_TBL_DAT_FEC_INI_PLA,(objUti.formatearFecha(rst.getString("fe_inipla"), "yyyy-MM-dd", "dd/MM/yyyy" )).equals("[ERROR]")?"":objUti.formatearFecha(rst.getString("fe_inipla"), "yyyy-MM-dd", "dd/MM/yyyy" ));
                vecReg.add(INT_TBL_DAT_FEC_FIN_PLA,(objUti.formatearFecha(rst.getString("fe_finpla"), "yyyy-MM-dd", "dd/MM/yyyy" )).equals("[ERROR]")?"":objUti.formatearFecha(rst.getString("fe_finpla"), "yyyy-MM-dd", "dd/MM/yyyy" ));
                    // validar check de las tareas
                
                       boolean tarpen = false, tar_anu =false,tar_pro= false ,tar_dete= false , tar_ter=false  ;
                        if(rst.getString("st_reg")!=null){
                            if(rst.getString("st_reg").equals("P")){
                                tarpen  = true;}
                           }else{
                            tarpen  = false;}
                        
                        if(rst.getString("st_reg")!=null){
                            if(rst.getString("st_reg").equals("A")){
                                tar_anu  = true;
                            }
                            
                        }else{
                            tar_anu  = false;
                        }         
                
                     if(rst.getString("st_reg")!=null){
                            if(rst.getString("st_reg").equals("O")){
                                tar_pro  = true;
                            }
                            
                        }else{
                            tar_pro  = false;
                        }
                
                     if(rst.getString("st_reg")!=null){
                            if(rst.getString("st_reg").equals("D")){
                                tar_dete  = true;
                            }
                            
                        }else{
                            tar_dete  = false;
                        }   
                 
                     if(rst.getString("st_reg")!=null){
                            if(rst.getString("st_reg").equals("T")){
                                tar_ter  = true;
                            }
                            
                        }else{
                            tar_ter  = false;
                        }         
                
                
                
               
                vecReg.add(INT_TBL_DAT_TAR_PEND,tarpen );
                vecReg.add(INT_TBL_DAT_Tar_Anular,tar_anu);
                vecReg.add(INT_TBL_DAT_TAR_PROCESO,tar_pro);
                vecReg.add(INT_TBL_DAT_TAR_DETENIDA,tar_dete);
                vecReg.add(INT_TBL_DAT_TAR_TERMINADA,tar_ter);
                vecReg.add(INT_TBL_DAT_POR_TRA_REA,rst.getString("nd_portrarea"));
                vecReg.add(INT_TBL_DAT_FEC_INI_REA,(objUti.formatearFecha(rst.getString("fe_inirea"), "yyyy-MM-dd", "dd/MM/yyyy" )).equals("[ERROR]")?"":objUti.formatearFecha(rst.getString("fe_inirea"), "yyyy-MM-dd", "dd/MM/yyyy" ));
                vecReg.add(INT_TBL_DAT_FEC_ULT_TRA,(objUti.formatearFecha(rst.getString("fe_ulttrarea"), "yyyy-MM-dd", "dd/MM/yyyy" )).equals("[ERROR]")?"":objUti.formatearFecha(rst.getString("fe_ulttrarea"), "yyyy-MM-dd", "dd/MM/yyyy" ));
               
                
          
                
                vecDat.add(vecReg);
                blnRes=true;
                                
            }
            
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);
           
           objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);
           objTblMod.setDataModelChanged(true);

           
           getCodTar_ubi(conn);
           
           
           
            rst.close();
            stm.close();
            conn.close();
            rst=null;
            stm=null;
            conn=null; 
            
            lblMsgSis.setText("se encontraron " + objTblMod.getRowCountTrue() + " registros."); 
            pgrSis.setValue(0); 
            butCon.setText("Consultar");
            
            
            cargarTablaAuxiliar();
            
          
             
        }catch(SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);            
        }        
        return blnRes;
    }
    
         
        /// funciones de mensajes de dialogo 
         
    
         
      private void mostrarMsgInf(String strMsg){
        JOptionPane.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
    }
         
         
         
      private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
 
         
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        Pan_frm = new javax.swing.JPanel();
        tab_frm = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        txtCodDep = new javax.swing.JTextField();
        txtDesLarDep = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cbo_fec_tar = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        radTodCli = new javax.swing.JRadioButton();
        radFilCli = new javax.swing.JRadioButton();
        lblLoc = new javax.swing.JLabel();
        txtCodres = new javax.swing.JTextField();
        txtDesLarRes = new javax.swing.JTextField();
        butLoc1 = new javax.swing.JButton();
        lblCli = new javax.swing.JLabel();
        cbo_prio = new javax.swing.JComboBox();
        chkTarter = new javax.swing.JCheckBox();
        chkTarPen = new javax.swing.JCheckBox();
        chkTarAnulada = new javax.swing.JCheckBox();
        chkTarPro = new javax.swing.JCheckBox();
        chkTarDet = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = tblDat = new javax.swing.JTable(){
            protected javax.swing.table.JTableHeader createDefaultTableHeader(){
                return new ZafTblHeaGrp(columnModel);
            }
        };
        ;
        panelInvisible = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAux = new javax.swing.JTable();
        pan_bar = new javax.swing.JPanel();
        Pan_bot = new javax.swing.JPanel();
        jPanfle = new javax.swing.JPanel();
        butdwn = new javax.swing.JButton();
        butder = new javax.swing.JButton();
        butup = new javax.swing.JButton();
        butizq = new javax.swing.JButton();
        butCon = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        pan_barest = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();
        lblMsgSis = new javax.swing.JLabel();
        lblTit = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                ExitForm(evt);
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

        Pan_frm.setLayout(new java.awt.BorderLayout());

        tab_frm.setFocusable(false);

        jPanel5.setLayout(null);

        txtCodDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDepActionPerformed(evt);
            }
        });
        txtCodDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDepFocusLost(evt);
            }
        });
        txtCodDep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodDepKeyPressed(evt);
            }
        });
        jPanel5.add(txtCodDep);
        txtCodDep.setBounds(180, 0, 50, 20);

        txtDesLarDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarDepActionPerformed(evt);
            }
        });
        txtDesLarDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarDepFocusLost(evt);
            }
        });
        jPanel5.add(txtDesLarDep);
        txtDesLarDep.setBounds(230, 0, 330, 20);

        butLoc.setText("..."); // NOI18N
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        jPanel5.add(butLoc);
        butLoc.setBounds(560, 0, 20, 20);

        jLabel2.setText("Departamento");
        jPanel5.add(jLabel2);
        jLabel2.setBounds(40, 0, 110, 15);

        cbo_fec_tar.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"(Todas)", "Fecha de la Tarea", "Fecha de inicio Planificada", "Fecha de Finalizacion Planificada ", "Fecha de Inicio Real", "Fecha de Ultimo Trabajo Realizado " }));
        cbo_fec_tar.setSelectedItem(null);
        cbo_fec_tar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbo_fec_tarItemStateChanged(evt);
            }
        });
        jPanel5.add(cbo_fec_tar);
        cbo_fec_tar.setBounds(180, 20, 400, 24);

        jLabel3.setText("Fecha de Consulta");
        jPanel5.add(jLabel3);
        jLabel3.setBounds(40, 20, 140, 20);

        buttonGroup1.add(radTodCli);
        radTodCli.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        radTodCli.setSelected(true);
        radTodCli.setText("Todos las Tareas"); // NOI18N
        radTodCli.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radTodCliItemStateChanged(evt);
            }
        });
        radTodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radTodCliActionPerformed(evt);
            }
        });
        jPanel5.add(radTodCli);
        radTodCli.setBounds(30, 120, 400, 20);

        buttonGroup1.add(radFilCli);
        radFilCli.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        radFilCli.setText("Sólo las tareas que cumplan el criterio seleccionado"); // NOI18N
        radFilCli.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radFilCliItemStateChanged(evt);
            }
        });
        radFilCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radFilCliActionPerformed(evt);
            }
        });
        jPanel5.add(radFilCli);
        radFilCli.setBounds(30, 140, 400, 30);

        lblLoc.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblLoc.setText("Responsable"); // NOI18N
        lblLoc.setToolTipText(""); // NOI18N
        jPanel5.add(lblLoc);
        lblLoc.setBounds(30, 170, 110, 20);

        txtCodres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodresActionPerformed(evt);
            }
        });
        txtCodres.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodresFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodresFocusLost(evt);
            }
        });
        jPanel5.add(txtCodres);
        txtCodres.setBounds(120, 170, 50, 20);

        txtDesLarRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarResActionPerformed(evt);
            }
        });
        txtDesLarRes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarResFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarResFocusLost(evt);
            }
        });
        jPanel5.add(txtDesLarRes);
        txtDesLarRes.setBounds(170, 170, 350, 20);

        butLoc1.setText("..."); // NOI18N
        butLoc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLoc1ActionPerformed(evt);
            }
        });
        jPanel5.add(butLoc1);
        butLoc1.setBounds(520, 170, 20, 20);

        lblCli.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblCli.setText("Prioridad "); // NOI18N
        lblCli.setToolTipText(""); // NOI18N
        jPanel5.add(lblCli);
        lblCli.setBounds(30, 190, 80, 20);

        cbo_prio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(Todas)", "Alta", "Media", "Baja" }));
        cbo_prio.setSelectedItem(null);
        cbo_prio.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbo_prioItemStateChanged(evt);
            }
        });
        cbo_prio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_prioActionPerformed(evt);
            }
        });
        jPanel5.add(cbo_prio);
        cbo_prio.setBounds(120, 190, 400, 24);

        chkTarter.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        chkTarter.setSelected(true);
        chkTarter.setText("Mostrar las tareas que estan terminadas");
        chkTarter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTarterActionPerformed(evt);
            }
        });
        jPanel5.add(chkTarter);
        chkTarter.setBounds(30, 290, 460, 20);

        chkTarPen.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        chkTarPen.setSelected(true);
        chkTarPen.setText("Mostrar las tareas que estan pendientes");
        chkTarPen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTarPenActionPerformed(evt);
            }
        });
        jPanel5.add(chkTarPen);
        chkTarPen.setBounds(30, 220, 450, 10);

        chkTarAnulada.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        chkTarAnulada.setSelected(true);
        chkTarAnulada.setText("Mostrar las tareas que estan anuladas");
        chkTarAnulada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTarAnuladaActionPerformed(evt);
            }
        });
        jPanel5.add(chkTarAnulada);
        chkTarAnulada.setBounds(30, 230, 460, 20);

        chkTarPro.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        chkTarPro.setSelected(true);
        chkTarPro.setText("Mostrar las tareas que estan en proceso");
        chkTarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTarProActionPerformed(evt);
            }
        });
        jPanel5.add(chkTarPro);
        chkTarPro.setBounds(30, 250, 460, 20);

        chkTarDet.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        chkTarDet.setSelected(true);
        chkTarDet.setText("Mostrar las tareas que estan detenidas");
        chkTarDet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTarDetActionPerformed(evt);
            }
        });
        jPanel5.add(chkTarDet);
        chkTarDet.setBounds(30, 270, 460, 20);

        tab_frm.addTab("General", jPanel5);

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
        jScrollPane1.setViewportView(tblDat);

        tab_frm.addTab("Reporte", jScrollPane1);

        jScrollPane2.setFocusable(false);

        tblAux.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblAux);

        javax.swing.GroupLayout panelInvisibleLayout = new javax.swing.GroupLayout(panelInvisible);
        panelInvisible.setLayout(panelInvisibleLayout);
        panelInvisibleLayout.setHorizontalGroup(
            panelInvisibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 685, Short.MAX_VALUE)
            .addGroup(panelInvisibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelInvisibleLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panelInvisibleLayout.setVerticalGroup(
            panelInvisibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
            .addGroup(panelInvisibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelInvisibleLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        tab_frm.addTab("", panelInvisible);

        Pan_frm.add(tab_frm, java.awt.BorderLayout.CENTER);
        tab_frm.getAccessibleContext().setAccessibleName("General");

        pan_bar.setLayout(new java.awt.BorderLayout());

        Pan_bot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butdwn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Imagenes/FleAba_7_16x16.gif"))); // NOI18N
        butdwn.setVisible(false);
        butdwn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butdwnActionPerformed(evt);
            }
        });

        butder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Imagenes/FleDer_7_16x16.gif"))); // NOI18N
        butder.setVisible(false);
        butder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butderActionPerformed(evt);
            }
        });

        butup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Imagenes/FleArr_7_16x16.gif"))); // NOI18N
        butup.setVisible(false);
        butup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butupActionPerformed(evt);
            }
        });

        butizq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Imagenes/FleIzq_7_16x16.gif"))); // NOI18N
        butizq.setVisible(false);
        butizq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butizqActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanfleLayout = new javax.swing.GroupLayout(jPanfle);
        jPanfle.setLayout(jPanfleLayout);
        jPanfleLayout.setHorizontalGroup(
            jPanfleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanfleLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(butizq, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(jPanfleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(butup)
                    .addComponent(butdwn))
                .addGap(6, 6, 6)
                .addComponent(butder, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanfleLayout.setVerticalGroup(
            jPanfleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanfleLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(butizq))
            .addGroup(jPanfleLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(butup)
                .addGap(6, 6, 6)
                .addComponent(butdwn))
            .addGroup(jPanfleLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(butder))
        );

        Pan_bot.add(jPanfle);

        butCon.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        Pan_bot.add(butCon);

        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        Pan_bot.add(jButton1);
        jButton1.getAccessibleContext().setAccessibleName("btn_guardar");

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        Pan_bot.add(butCer);

        pan_bar.add(Pan_bot, java.awt.BorderLayout.CENTER);
        Pan_bot.getAccessibleContext().setAccessibleParent(pan_bar);

        pan_barest.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(200, 16));
        jPanel2.setLayout(new java.awt.BorderLayout());

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 15));
        jPanel2.add(pgrSis, java.awt.BorderLayout.CENTER);

        pan_barest.add(jPanel2, java.awt.BorderLayout.EAST);

        lblMsgSis.setText("Listo"); // NOI18N
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pan_barest.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        pan_bar.add(pan_barest, java.awt.BorderLayout.SOUTH);

        Pan_frm.add(pan_bar, java.awt.BorderLayout.SOUTH);

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana"); // NOI18N
        Pan_frm.add(lblTit, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(Pan_frm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
      if (butCon.getText().equals("Consultar"))
      {
         if (radTodCli.isSelected()||radFilCli.isSelected() ){
             if (!txtCodDep.getText().equals("")) {
                         
                   if (objThrGUI==null){
                            objThrGUI=new ZafThreadGUI();
                            objThrGUI.start();
                            
                             mostrarBotdire();
                             if (cambia==true)
                            {
                              intCodtardes=0;                               
                               intcodubi=0;
                 
                             }
                            
                            
                            }
                         
                         } else mostrarMsgInf("Elija el departamento es un datos obligatorio ");
                 
                       
                       }else
                    mostrarMsgInf("SELECIONE UN CRITERIO DE BUSQUEDA ");
         }else
           mostrarMsgInf("No ha seleccionado ningun criterio de busqueda. ");

       /// tab_frm.setSelectedIndex(1);
        if (tblDat.getRowCount()>0){
            tblDat.setRowSelectionInterval(0, 0);
            tblDat.requestFocus();      
        }


    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        ExitForm(null);            
    }//GEN-LAST:event_butCerActionPerformed

    private void chkTarDetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTarDetActionPerformed
      if(!chkTarDet.isSelected())
            radFilCli.setSelected(true);
    }//GEN-LAST:event_chkTarDetActionPerformed

    private void chkTarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTarProActionPerformed
       if(!chkTarPro.isSelected())
            radFilCli.setSelected(true);
    }//GEN-LAST:event_chkTarProActionPerformed

    private void chkTarAnuladaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTarAnuladaActionPerformed
      if(!chkTarAnulada.isSelected())
            radFilCli.setSelected(true);
    }//GEN-LAST:event_chkTarAnuladaActionPerformed

    private void chkTarPenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTarPenActionPerformed
       if(!chkTarPen.isSelected())
            radFilCli.setSelected(true);
    }//GEN-LAST:event_chkTarPenActionPerformed

    private void chkTarterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTarterActionPerformed
       if(!chkTarter.isSelected())
            radFilCli.setSelected(true);


  }//GEN-LAST:event_chkTarterActionPerformed

    private void butLoc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLoc1ActionPerformed
     mostrarVenConRes(0);
   }//GEN-LAST:event_butLoc1ActionPerformed

    private void txtDesLarResFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarResFocusLost
      if (txtDesLarRes.isEditable()){
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesLarRes.getText().equalsIgnoreCase(strNom)) {
                if (txtDesLarRes.getText().equals("")) {
                    txtCodres.setText("");
                    txtDesLarRes.setText("");
                }else 
                    if(!mostrarVenConRes(2)){
                        txtCodres.setText("");
                        txtDesLarRes.setText("");
                    }                
            }else
                txtDesLarRes.setText(strNom);
        }


   }//GEN-LAST:event_txtDesLarResFocusLost

    private void txtDesLarResFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarResFocusGained
        strNom=txtDesLarRes.getText();
        txtDesLarRes.selectAll();

   }//GEN-LAST:event_txtDesLarResFocusGained

    private void txtDesLarResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarResActionPerformed
         txtDesLarRes.transferFocus();

   }//GEN-LAST:event_txtDesLarResActionPerformed

    private void txtCodresFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodresFocusLost
         if (txtCodres.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtCodres.getText().equalsIgnoreCase(strCodres)) {
                if (txtCodres.getText().equals("")) {
                    txtCodres.setText("");
                    txtDesLarRes.setText("");
                }else
                   if(!mostrarVenConRes(1)){
                        txtCodres.setText("");
                        txtDesLarRes.setText("");
                    }                
            }else
                txtCodres.setText(strCodres);
        }             


  }//GEN-LAST:event_txtCodresFocusLost

    private void txtCodresFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodresFocusGained
      strCodres=txtCodres.getText();
        txtCodres.selectAll();

   }//GEN-LAST:event_txtCodresFocusGained

    private void txtCodresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodresActionPerformed
       txtCodres.transferFocus();

   }//GEN-LAST:event_txtCodresActionPerformed

    private void radFilCliItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radFilCliItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_radFilCliItemStateChanged

    private void radTodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radTodCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radTodCliActionPerformed

    private void radTodCliItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radTodCliItemStateChanged
         if (radTodCli.isSelected())
         {
             txtCodres.setText("");
             txtDesLarRes.setText("");
             chkTarPen.setSelected(true);
             chkTarAnulada.setSelected(true);
             chkTarPro.setSelected(true);
             chkTarDet.setSelected(true);
             chkTarter.setSelected(true);
             cbo_prio.setSelectedIndex(0);
             
             
         }
   }//GEN-LAST:event_radTodCliItemStateChanged

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
       mostrarVenConLoc(0);
   }//GEN-LAST:event_butLocActionPerformed

    private void txtDesLarDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarDepFocusLost
     if (txtDesLarDep.isEditable()){
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesLarDep.getText().equalsIgnoreCase(strNomLoc)) {
                if (txtDesLarDep.getText().equals("")) {
                    txtCodDep.setText("");
                    txtDesLarDep.setText("");
                }else 
                    if(!mostrarVenConLoc(2)){
                        txtCodDep.setText("");
                        txtDesLarDep.setText("");
                    }                
            }else
                txtDesLarDep.setText(strNomLoc);
        }

   }//GEN-LAST:event_txtDesLarDepFocusLost

    private void txtDesLarDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarDepFocusGained
         // TODO add your handling code here:
        strNomLoc=txtDesLarDep.getText();
        txtDesLarDep.selectAll();

   }//GEN-LAST:event_txtDesLarDepFocusGained

    private void txtDesLarDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarDepActionPerformed
    txtDesLarDep.transferFocus();
       

   }//GEN-LAST:event_txtDesLarDepActionPerformed

    private void txtCodDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusLost
      if (txtCodDep.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtCodDep.getText().equalsIgnoreCase(strCodLoc)) {
                if (txtCodDep.getText().equals("")) {
                    txtCodDep.setText("");
                    txtDesLarDep.setText("");
                }else
                   if(!mostrarVenConLoc(1)){
                        txtCodDep.setText("");
                        txtDesLarDep.setText("");
                    }                
            }else
                txtCodDep.setText(strCodLoc);
        }            

   }//GEN-LAST:event_txtCodDepFocusLost

    private void txtCodDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusGained
      strCodLoc=txtCodDep.getText();
        txtCodDep.selectAll();
   }//GEN-LAST:event_txtCodDepFocusGained

    private void txtCodDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDepActionPerformed
        txtCodDep.transferFocus();
       

   }//GEN-LAST:event_txtCodDepActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
           configurarVenConDep();
           configurarVenConRes();
           configurarFrm();
           configurarFrmAux();
    }//GEN-LAST:event_formInternalFrameOpened

    private void ExitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_ExitForm
       strMsg="¿Esta seguro que desea cerrar este programa?";
        if (JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
            Runtime.getRuntime().gc();
            dispose();
        }
    }//GEN-LAST:event_ExitForm

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     
        boolean blnRes=false;
       if (objTblMod.getRowCount()!=-1)
       {
        
        if (objTblMod.isDataModelChanged())
        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
               if (modificar()) {
                    mostrarMsgInf(" Los datos han sido guardado con exito ");
                    cargarDetReg(filtro());
               }                   
            }
        else
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
        } 
       }
       else  mostrarMsgInf("No existen Registro para guardar");
          
    }//GEN-LAST:event_jButton1ActionPerformed

    private void butupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butupActionPerformed

         int intFilSel[] = tblDat.getSelectedRows();
         int ubi =0;
         int ubi2=0;
         
         
         for (int j = 0; j< objTblModAux.getRowCountTrue() ;j++)
                  { 
                   
                      if(j==0)
                      {
                       ubi= Integer.parseInt(objTblModAux.getValueAt(intFilSel[j],INT_TBL_DAT_Ubi_TAR).toString());
                       break;
                      }
                  }
         
        for(int i=0; i<intFilSel.length; i++){
            
              
                if (intFilSel[i]==0)
                {
                  ubi= Integer.parseInt(objTblMod.getValueAt(intFilSel[i],INT_TBL_DAT_Ubi_TAR).toString());
                }
              
                  objTblMod.moveRow((intFilSel[i]), (intFilSel[i]-1));
                  objTblModAux.moveRow((intFilSel[i]), (intFilSel[i]-1));
                    
            
                
                
        }
        
       for (int j = 0; j< objTblModAux.getRowCountTrue() ;j++)
                             
            {      
                objTblModAux.setValueAt(ubi, j, INT_TBL_DAT_Ubi_TAR);
                 ubi++;                 
            }       
        
         movtarpad();  
       

    }//GEN-LAST:event_butupActionPerformed

    private void butderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butderActionPerformed
         String strDesTar="";
        int intFilSel=-1;
        int intfilselant=-1;
        int intNivIni=0, intNivFin=0;
        int maxnivel=0;
        int codpad = 0;
        int nivarr =0;
        int codtar=0;
        String strAux="";
         
           
                       
            
            intFilSel=tblDat.getSelectedRow();
            intfilselant=intFilSel-1;
            if (intfilselant==-1)
            {
                mostrarMsgInf("Debe existir por lo menos una tarea de nivel superior");
            }  
            
            else 
            try{  
            intNivIni=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NIV_TAR)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NIV_TAR).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NIV_TAR).toString()));
            /// capturar los valores anteriores del modelo 
            
            codtar=Integer.parseInt(objTblMod.getValueAt(intfilselant, INT_TBL_DAT_Cod_Tar)==null?"0":(objTblMod.getValueAt(intfilselant, INT_TBL_DAT_Cod_Tar).toString().equals("")?"0":objTblMod.getValueAt(intfilselant, INT_TBL_DAT_Cod_Tar).toString()));
            codpad = Integer.parseInt(objTblMod.getValueAt(intfilselant, INT_TBL_DAT_TAR_PAD)==null?"0":(objTblMod.getValueAt(intfilselant, INT_TBL_DAT_TAR_PAD).toString().equals("")?"0":objTblMod.getValueAt(intfilselant, INT_TBL_DAT_TAR_PAD).toString()));
            maxnivel=Integer.parseInt(objTblMod.getValueAt(intfilselant, INT_TBL_DAT_NIV_TAR)==null?"0":(objTblMod.getValueAt(intfilselant, INT_TBL_DAT_NIV_TAR).toString().equals("")?"0":objTblMod.getValueAt(intfilselant, INT_TBL_DAT_NIV_TAR).toString()));
            nivarr=maxnivel;
            strDesTar=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_DES_COR).toString().trim();  
            maxnivel+=1;           
            if(intNivIni<maxnivel){
                intNivFin=intNivIni+1;
                objTblMod.setValueAt(intNivFin, intFilSel, INT_TBL_DAT_NIV_TAR);
                // validar los  padres de la tarea
               if (intNivFin==nivarr)
               objTblMod.setValueAt(codpad, intFilSel, INT_TBL_DAT_TAR_PAD);
               else if (intNivFin<nivarr)
               objTblMod.setValueAt(codpad-1, intFilSel, INT_TBL_DAT_TAR_PAD);
               else if (intNivFin>nivarr)
               objTblMod.setValueAt(codtar, intFilSel, INT_TBL_DAT_TAR_PAD);
               
                for(int j=1; j<intNivFin; j++)
                {
                    strAux+="        ";
                
                objTblMod.setValueAt((strAux + strDesTar), intFilSel, INT_TBL_DAT_DES_COR);
                objTblMod.setValueAt("D", intFilSel, INT_TBL_DAT_TIP_TAR);
               // objTblMod.setValueAt(codtar, intFilSel, INT_TBL_DAT_TAR_PAD);
               }
            }
            }
            catch (Exception e) { 
            objUti.mostrarMsgErr_F1(this, e);
            
       
            
           setCodigoPadreTbl(intFilSel);
        
        }
       

       
    }//GEN-LAST:event_butderActionPerformed

    private void butdwnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butdwnActionPerformed
        
       int intFilSel[] = tblDat.getSelectedRows();
       ArrayList arrehijos = new java.util.ArrayList();
        //arlelihijos=tblDat.getSelectedRows();
         int ubi =0;
         int ubi2 =0;
         int origen=0;
         int inthijos = 0;
         int intpadre = 0;
         String nivelante= "";
         int possicion = 0;
         String tarpadultima="";
         origen = intFilSel.length-1;
         int nivsegu=Integer.parseInt(objTblMod.getValueAt(1, INT_TBL_DAT_NIV_TAR)==null?"0":(objTblMod.getValueAt(1, INT_TBL_DAT_NIV_TAR).toString().equals("")?"0":objTblMod.getValueAt(1, INT_TBL_DAT_NIV_TAR).toString()));
         String titar=objTblMod.getValueAt(1, INT_TBL_DAT_TIP_TAR)==null?"0":(objTblMod.getValueAt(1, INT_TBL_DAT_TIP_TAR).toString().equals("")?"0":objTblMod.getValueAt(1, INT_TBL_DAT_TIP_TAR).toString());
          // captura los valores para poder mover las tareas
         for (int j = 0; j< objTblModAux.getRowCountTrue() ;j++)
                  { 
                   
                      if(j==0)
                      {
                       ubi= Integer.parseInt(objTblModAux.getValueAt(intFilSel[j],INT_TBL_DAT_Ubi_TAR).toString());
                       break;
                      }
                  }
      
        try {
              
          
                    for (int i=origen; i>=0;i-- )
                    {
                    nivelante= objTblMod.getValueAt(intFilSel[i],INT_TBL_DAT_TIP_TAR).toString();
                    if (nivelante.equals("C"))
                        break;
                    }

                    if (nivelante.equals("C")&&objTblMod.getRowCountTrue()<objTblModAux.getRowCountTrue())
                    mostrarMsgInf("Usted no puede mover esta tarea padre consulte todas las tareas del departamento para poder moverla ");
                    else 
                    {
                        for (int i=origen; i>=0;i-- )
                            {
                            objTblMod.moveRow((intFilSel[i]+1), (intFilSel[i]));
                            objTblModAux.moveRow((intFilSel[i]+1), (intFilSel[i]));
                            }
                    }

            
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
            
        for (int j = 0; j< objTblModAux.getRowCountTrue() ;j++)
                             
            {      
                objTblModAux.setValueAt(ubi, j, INT_TBL_DAT_Ubi_TAR);
                 ubi++;                 
            }       
           
   
         boolean tarpad=false;
         int taraux=0;
         
        
         
        
         
         for (int j = 0; j< objTblMod.getRowCountTrue() ;j++)
                             
            {    
                
                 
         
           int intNivFilSel=-1;
           int intNivFilDes=-1;
           int intCodTarFilDes=-1;
           int intcodpadre=-1;
           
          
           try{
               intNivFilSel=Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_NIV_TAR)==null?"0":(objTblMod.getValueAt(j, INT_TBL_DAT_NIV_TAR).toString().equals("")?"0":objTblMod.getValueAt(j, INT_TBL_DAT_NIV_TAR).toString()));
               System.out.println("intNivFilSel: " + intNivFilSel);
                   
                    
               intNivFilSel=intNivFilSel-1;

               
                if (intNivFilSel!=-1)
               {  
                   if (j==0)
                   {
                    objTblMod.setValueAt(1, 0, INT_TBL_DAT_NIV_TAR);
                    objTblMod.setValueAt(0, 0, INT_TBL_DAT_TAR_PAD);
                    objTblMod.setValueAt(objTblMod.getValueAt(0,INT_TBL_DAT_DES_COR).toString().trim(), 0, INT_TBL_DAT_DES_COR);
                    objTblMod.setValueAt("C", 0, INT_TBL_DAT_TIP_TAR);
                   }
                   else if (j==1)
                   {
                       int tarpa=Integer.parseInt(objTblMod.getValueAt(0, INT_TBL_DAT_Cod_Tar)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_Cod_Tar).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_Cod_Tar).toString()));
                       objTblMod.setValueAt(tarpa, j, INT_TBL_DAT_TAR_PAD);
                       objTblMod.setValueAt(nivsegu, j, INT_TBL_DAT_NIV_TAR);
                       if (nivsegu==2)
                       {
                        objTblMod.setValueAt("        "+objTblMod.getValueAt(j,INT_TBL_DAT_DES_COR).toString().trim(), j, INT_TBL_DAT_DES_COR);
                       }
                       objTblMod.setValueAt(titar, j, INT_TBL_DAT_TIP_TAR);
                      
                   }
                  
                   else{
                            int redei=j;
                            
                             if (intNivFilSel==0)
                                {
                                   intNivFilSel=1;
                                   redei--;
                                   taraux++;
                                   if(taraux>1)
                                   tarpad=true;
                                } 
                           for(int i=redei; i>=0; i--){
                            intNivFilDes=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_NIV_TAR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_NIV_TAR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_NIV_TAR).toString()));
                            System.out.println("intNivFilDes: " + intNivFilDes);
                            
                            if(intNivFilSel==intNivFilDes){
                                
                                intCodTarFilDes=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_Cod_Tar)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_Cod_Tar).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_Cod_Tar).toString()));
                                intcodpadre=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_TAR_PAD)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_TAR_PAD).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_TAR_PAD).toString()));
                                String tiptar = objTblMod.getValueAt(i, INT_TBL_DAT_TIP_TAR).toString();
                                objTblMod.setValueAt(intCodTarFilDes, j, INT_TBL_DAT_TAR_PAD);
                                
                                if (tarpad==true&& tiptar.equals("C"))
                                {
                                 objTblMod.setValueAt(intcodpadre, j, INT_TBL_DAT_TAR_PAD);
                                 tarpad=false;
                                }
                                break;}
                            }
                        }
               }
//               else 
//                   
//               {
//                  mostrarMsgInf("Selecciono una tarea que no puede ser movida");
//               }
               
           }
           catch(Exception e){
           
            objUti.mostrarMsgErr_F1(this, e);            
           }
          
           
            }      


         
    }//GEN-LAST:event_butdwnActionPerformed
   /// se setearean los valores para que el usuario cambie el nivel de la tarea
    private void butizqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butizqActionPerformed
         String strDesTar="";
        int intFilSel=-1;
        int intNivIni=0, intNivFin=0;
        int codpad = 0 ;
       
        String strAux="";
       
        try{ 
            
            intFilSel=tblDat.getSelectedRow();
            
            
            intNivIni=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NIV_TAR)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NIV_TAR).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NIV_TAR).toString()));
            //codpad=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_TAR_PAD)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_TAR_PAD).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_TAR_PAD).toString()));
            strDesTar=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_DES_COR).toString().trim();                     
            if(intNivIni>1){
                intNivFin=intNivIni-1;
                objTblMod.setValueAt(intNivFin, intFilSel, INT_TBL_DAT_NIV_TAR);
                
                
                for(int j=1; j<intNivFin; j++)
                    strAux+="        ";
                objTblMod.setValueAt((strAux + strDesTar), intFilSel, INT_TBL_DAT_DES_COR);
                //objTblMod.setValueAt(codpad-1, intFilSel, INT_TBL_DAT_TAR_PAD); 
                
                if ( objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NIV_TAR).toString().equals("1"))
                     objTblMod.setValueAt("C", intFilSel, INT_TBL_DAT_TIP_TAR);
                    }                   
            
            setCodigoPadreTbl(intFilSel);
        }   
        catch (Exception e) { 
            objUti.mostrarMsgErr_F1(this, e);
        }
    }//GEN-LAST:event_butizqActionPerformed

    private void cbo_prioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbo_prioItemStateChanged
      radFilCli.setSelected(true);
    }//GEN-LAST:event_cbo_prioItemStateChanged

    private void radFilCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radFilCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radFilCliActionPerformed

    private void cbo_prioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_prioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_prioActionPerformed

    private void cbo_fec_tarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbo_fec_tarItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_fec_tarItemStateChanged

    private void txtCodDepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodDepKeyPressed
        cambia= true ;
    }//GEN-LAST:event_txtCodDepKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Pan_bot;
    private javax.swing.JPanel Pan_frm;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butLoc1;
    private javax.swing.JButton butder;
    private javax.swing.JButton butdwn;
    private javax.swing.JButton butizq;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton butup;
    private javax.swing.JComboBox cbo_fec_tar;
    private javax.swing.JComboBox cbo_prio;
    private javax.swing.JCheckBox chkTarAnulada;
    private javax.swing.JCheckBox chkTarDet;
    private javax.swing.JCheckBox chkTarPen;
    private javax.swing.JCheckBox chkTarPro;
    private javax.swing.JCheckBox chkTarter;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanfle;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel pan_bar;
    private javax.swing.JPanel pan_barest;
    private javax.swing.JPanel panelInvisible;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JRadioButton radFilCli;
    private javax.swing.JRadioButton radTodCli;
    private javax.swing.JTabbedPane tab_frm;
    private javax.swing.JTable tblAux;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodDep;
    private javax.swing.JTextField txtCodres;
    private javax.swing.JTextField txtDesLarDep;
    private javax.swing.JTextField txtDesLarRes;
    // End of variables declaration//GEN-END:variables

 
    
    
    public boolean modificar() {
            boolean blnRes=false;
            java.sql.Connection conn;
            try{
                if(validaCampos()){

                    conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    if(conn!=null){
                        conn.setAutoCommit(false);
                        
                        if(modificarCab(conn)){
                           if(regenerarUbicacionDb(conn)){
                                conn.commit();
                                blnRes=true;
                            }
                            else
                                conn.rollback();
                           
                        }
                        else 
                            conn.rollback();
                        
                        conn.close();
                        conn=null;
                   }
                }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
            catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
            return blnRes;
        }  
    
   
    
 
    public void elimiubi()
    {
      
       java.sql.Connection conn; 
       java.sql.PreparedStatement preSta;
       java.sql.ResultSet resSet ;
       String strubi= "";
       java.sql.Statement stmLoc;
       String  estcel=""; 
       String strSql1="";
    
        try {
           
      
            conn=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
        
            if(conn!=null){
                    
                     int val = 0;
                    int valtab= 0;
                    stmLoc=conn.createStatement();
                    strSql1="select count(ne_ubi) ubimax  from tbm_tartra  where co_dep = " + txtCodDep.getText();
                    preSta = conn.prepareStatement(strSql1);
                    resSet = preSta.executeQuery();
                   
                     if(resSet.next())
                   {
                        valtab = resSet.getInt("ubimax");
                   }
                     valtab= valtab-ubi;
                     
                       for(int intFil=0; intFil<valtab; intFil++)
                     {
                         
                    
                      int valubi = Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_Cod_Tar)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_Cod_Tar).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_Cod_Tar).toString()));
                      objTblMod.setValueAt(val+1,intFil, INT_TBL_DAT_Ubi_TAR);
                      val++;
                      
                     }
       
                   
       }     
         
            
        } catch (Exception  Evt) {
        }
      
    }
    
    
    
    
     private void elimihijos()
       { 
        java.sql.Connection conn; 
        java.sql.PreparedStatement preSta;
      
       
       java.sql.Statement stmLoc;
      
       String strSql1="";
    
        try {
           
      
            conn=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
        
            if(conn!=null){
               stmLoc=conn.createStatement();
               java.util.ArrayList arlAux=objTblMod.getDataSavedBeforeRemoveRow();
                            for (int i=0;i<arlAux.size();i++)
                            {
                               
                                  //Armar la sentencia SQL.
                                    strSql="";
                                    
                                    String tiptar;
                                    tiptar=""+ objUti.getStringValueAt(arlAux,i, INT_CFE_COD_tipar);
                                
                                 if (tiptar.trim().equals("C"))
                                {
                                     strSql="";
                                     strSql+="delete from  tbm_tartra";
                                     strSql+=" WHERE co_dep=" + txtCodDep.getText();
                                     strSql+=" AND ne_pad=" + objUti.getObjectValueAt(arlAux,i, INT_CFE_COD_tar);
                                     stmLoc.executeUpdate(strSql);
                                     
                                     strSql="";
                                     strSql+="delete from  tbm_tartra";
                                     strSql+=" WHERE co_dep=" + txtCodDep.getText();
                                     strSql+=" AND co_tar=" + objUti.getObjectValueAt(arlAux,i, INT_CFE_COD_tar);
                                }
                                
                                     else if (tiptar.trim().equals("D"))
                                {
                                    
                                    strSql="";
                                    strSql+="delete from  tbm_tartra"; 
                                    strSql+=" WHERE co_dep=" + txtCodDep.getText();
                                    strSql+=" AND co_tar=" + objUti.getObjectValueAt(arlAux,i, INT_CFE_COD_tar);
                                    
                                 }
                                
                                
                               
                                stmLoc.executeUpdate(strSql);
                                
                               
                            }
                   

                    stmLoc.close();
                    stmLoc=null;
                   
                    objTblMod.initRowsState();
                    objTblMod.setDataModelChanged(false);
                    objTblMod.clearDataSavedBeforeRemoveRow();
                    arlAux.clear();
                    arlelihijos.clear();
                    elihijos= false;
                    
                   
       
                   
       }     
         
            
        } catch (Exception  Evt) {
        }
      
           
       }
    
    
     private boolean modificarCab(java.sql.Connection conn){
            
            boolean blnRes=false;
            java.sql.Statement stmLoc;
            String strSql="";
            String strLin="";
            String strAux1="" ;
            String strubi= "";
            String fecha ="";
             int i;
            
            try{
                
                if(conn!=null){
                    
                    
                    java.sql.PreparedStatement preSta;
                    java.sql.ResultSet resSet ;
                    
                    stmLoc=conn.createStatement();
                    
                    
                            
                    
                   
                    for(int intFil=0; intFil<objTblMod.getRowCountTrue(); intFil++){
                        
                        String strSt_oblSegJerAut="P";
                        String ne_pri ="";
                        String strJerUsr = null;
                        boolean bln = false;
                        int pri=0 ;
                        String fechaini = "";
                        int intCodtar = Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_Cod_Tar)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_Cod_Tar).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_Cod_Tar).toString()));
                      
                         ne_pri = objTblMod.getValueAt(intFil, INT_TBL_DAT_PRIORIDAD)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_PRIORIDAD).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_PRIORIDAD).toString());
                         
                         
                        
                         if (ne_pri.equals("Alta"))
                         
                          pri= 1 ; 
                         if (ne_pri.equals("Media"))
                             pri=2;
                         if (ne_pri.equals("Baja"))
                             pri=3;
                       
           
                        if(objTblMod.getValueAt(intFil, INT_TBL_DAT_TAR_PEND)!=null && objTblMod.getValueAt(intFil, INT_TBL_DAT_TAR_PEND).toString().compareTo("")!=0){
                            bln = (Boolean) objTblMod.getValueAt(intFil, INT_TBL_DAT_TAR_PEND);
                            if(bln)strSt_oblSegJerAut="P";}
                            
                        if(objTblMod.getValueAt(intFil, INT_TBL_DAT_Tar_Anular)!=null && objTblMod.getValueAt(intFil, INT_TBL_DAT_Tar_Anular).toString().compareTo("")!=0){
                            bln = (Boolean) objTblMod.getValueAt(intFil, INT_TBL_DAT_Tar_Anular);
                            if(bln) strSt_oblSegJerAut="A";}
                        
                        if(objTblMod.getValueAt(intFil, INT_TBL_DAT_TAR_PROCESO)!=null && objTblMod.getValueAt(intFil, INT_TBL_DAT_TAR_PROCESO).toString().compareTo("")!=0){
                            bln = (Boolean) objTblMod.getValueAt(intFil, INT_TBL_DAT_TAR_PROCESO);
                            if(bln) strSt_oblSegJerAut="O"; }
                         
                         if(objTblMod.getValueAt(intFil, INT_TBL_DAT_TAR_DETENIDA)!=null && objTblMod.getValueAt(intFil, INT_TBL_DAT_TAR_DETENIDA).toString().compareTo("")!=0){
                            bln = (Boolean) objTblMod.getValueAt(intFil, INT_TBL_DAT_TAR_DETENIDA);
                            if(bln)strSt_oblSegJerAut="D"; }
                             
                          if(objTblMod.getValueAt(intFil, INT_TBL_DAT_TAR_TERMINADA)!=null && objTblMod.getValueAt(intFil, INT_TBL_DAT_TAR_TERMINADA).toString().compareTo("")!=0){
                            bln = (Boolean) objTblMod.getValueAt(intFil, INT_TBL_DAT_TAR_TERMINADA);
                            if(bln) strSt_oblSegJerAut="T";}
                            
                            strAux1=objUti.parseString(objTblMod.getValueAt(intFil,INT_TBL_DAT_LIN));
                         
                            if (strAux1.equals("M")){
                                
                            strSql="";
                            strSql="UPDATE tbm_tartra SET "+
                                   
                                     " ne_niv='"+(objTblMod.getValueAt(intFil,INT_TBL_DAT_NIV_TAR).toString())+"', " +
                                     " tx_tip='"+(objTblMod.getValueAt(intFil,INT_TBL_DAT_TIP_TAR).toString())+"', " +
                                     " ne_ubi='"+(objTblMod.getValueAt(intFil,INT_TBL_DAT_Ubi_TAR).toString())+"', " +
                                     " ne_pad='"+(objTblMod.getValueAt(intFil,INT_TBL_DAT_TAR_PAD).toString())+"', " +
                                     " fe_tar='"+objUti.formatearFecha((objTblMod.getValueAt(intFil, INT_TBL_DAT_FECHA).toString()),"dd/MM/yyyy","yyyy-MM-dd")+"', " +
                                     " tx_descor="+objUti.codificar((objTblMod.getValueAt(intFil, INT_TBL_DAT_DES_COR).toString().trim()))+", " +
                                     " tx_deslar="+objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_DES_LAR))+", " +
                                     " co_trasol="+objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_SOL),2)+", " +
                                     " ne_pri="+objUti.codificar(pri,2 ) +" ," +
                                     " co_trares="+objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_RES),2)+", " +
                                     " fe_inipla="+(objTblMod.getValueAt(intFil, INT_TBL_DAT_FEC_INI_PLA).toString().equals("") ?null:"'"+objUti.formatearFecha((objTblMod.getValueAt(intFil, INT_TBL_DAT_FEC_INI_PLA).toString()),"dd/MM/yyyy","yyyy-MM-dd")+"'")+", " +
                                     " fe_finpla="+(objTblMod.getValueAt(intFil, INT_TBL_DAT_FEC_FIN_PLA).toString().equals("") ?null:"'"+objUti.formatearFecha((objTblMod.getValueAt(intFil, INT_TBL_DAT_FEC_FIN_PLA).toString()),"dd/MM/yyyy","yyyy-MM-dd")+"'")+", " +
                                     " nd_portrarea="+(objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_POR_TRA_REA),3))+", " +
                                     " fe_inirea="+(objTblMod.getValueAt(intFil, INT_TBL_DAT_FEC_INI_REA).toString().equals("") ?null:"'"+objUti.formatearFecha((objTblMod.getValueAt(intFil, INT_TBL_DAT_FEC_INI_REA).toString()),"dd/MM/yyyy","yyyy-MM-dd")+"'")+", " +
                                     " fe_ulttrarea="+(objTblMod.getValueAt(intFil, INT_TBL_DAT_FEC_ULT_TRA).toString().equals("") ?null:"'"+objUti.formatearFecha((objTblMod.getValueAt(intFil, INT_TBL_DAT_FEC_ULT_TRA).toString()),"dd/MM/yyyy","yyyy-MM-dd")+"'")+", " +
                                     " st_reg='"+ (strSt_oblSegJerAut)+"' ," +
                                     " fe_ultmod='"+(objParSis.getFechaHoraServidorIngresarSistema())+"', " +
                                     " co_usrmod="+(objParSis.getCodigoUsuario())+"" +
                                     " WHERE " +
                                     " co_dep="+txtCodDep.getText()+" AND co_tar="+intCodtar ;
                                    
                                     System.out.println("querie modificacion: " + strSql);
                                     stmLoc.executeUpdate(strSql);
                                     
                                 
                                  }else if (strAux1.equals("I")){
                                     
                             

                             
                            
                                    strSql="";
                                    strSql+="INSERT INTO tbm_tartra(co_dep,co_tar ,ne_ubi , tx_tip, ne_pad, ne_niv,fe_tar, tx_descor, tx_deslar";
                                    strSql+=",co_trasol, ne_pri, co_trares, fe_inipla,  fe_finpla, nd_portrarea,fe_inirea,fe_ulttrarea,tx_obs1 , st_reg,fe_ultmod,co_usring, co_usrmod)  ";
                                    strSql+=" VALUES (";
                                    strSql+="" + txtCodDep.getText();
                                    strSql+="," + objUti.codificar( objTblMod.getValueAt(intFil, INT_TBL_DAT_Cod_Tar));
                                    strSql+=", " +objUti.codificar( objTblMod.getValueAt(intFil, INT_TBL_DAT_Ubi_TAR));
                                    strSql+=", " +objUti.codificar( objTblMod.getValueAt(intFil, INT_TBL_DAT_TIP_TAR));
                                    strSql+=", " +objTblMod.getValueAt(intFil, INT_TBL_DAT_TAR_PAD).toString();
                                    strSql+=", " + objTblMod.getValueAt(intFil, INT_TBL_DAT_NIV_TAR).toString();
                                    strSql+=",'"+ (objTblMod.getValueAt(intFil, INT_TBL_DAT_FECHA)==null? objParSis.getFechaHoraServidorIngresarSistema():objUti.formatearFecha((objTblMod.getValueAt(intFil, INT_TBL_DAT_FECHA).toString()),"dd/MM/yyyy","yyyy-MM-dd"));
                                    strSql+="', " +objUti.codificar( objTblMod.getValueAt(intFil, INT_TBL_DAT_DES_COR).toString().trim(),1);
                                    strSql+=", " + objUti.codificar( objTblMod.getValueAt(intFil, INT_TBL_DAT_DES_LAR),1);
                                    strSql+=","+objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_SOL),2);
                                    strSql+="," +objUti.codificar(pri,2) ;
                                    strSql+=","+objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_RES),2);
                                    strSql+=","+(objTblMod.getValueAt(intFil, INT_TBL_DAT_FEC_INI_PLA)==null?null:"'"+(objUti.formatearFecha((objTblMod.getValueAt(intFil, INT_TBL_DAT_FEC_INI_PLA).toString()),"dd/MM/yyyy","yyyy-MM-dd"))+"'");
                                    strSql+=","+(objTblMod.getValueAt(intFil, INT_TBL_DAT_FEC_FIN_PLA)==null?null:"'"+(objUti.formatearFecha((objTblMod.getValueAt(intFil, INT_TBL_DAT_FEC_FIN_PLA).toString()),"dd/MM/yyyy","yyyy-MM-dd"))+"'");
                                    strSql+=","+objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_POR_TRA_REA),3);
                                    strSql+=","+(objTblMod.getValueAt(intFil, INT_TBL_DAT_FEC_INI_REA)==null?null:"'"+(objUti.formatearFecha((objTblMod.getValueAt(intFil, INT_TBL_DAT_FEC_INI_REA).toString()),"dd/MM/yyyy","yyyy-MM-dd"))+"'");
                                    strSql+=","+(objTblMod.getValueAt(intFil, INT_TBL_DAT_FEC_ULT_TRA)==null?null:"'"+(objUti.formatearFecha((objTblMod.getValueAt(intFil, INT_TBL_DAT_FEC_ULT_TRA).toString()),"dd/MM/yyyy","yyyy-MM-dd"))+"'");
                                    strSql+=","+objUti.codificar( objTblMod.getValueAt(intFil, INT_TBL_DAT_DES_COR).toString().trim(),1);
                                    strSql+="," +objUti.codificar( strSt_oblSegJerAut);
                                    strSql+=",'"+(objParSis.getFechaHoraServidorIngresarSistema());                            

                                    strSql+="','"+(objParSis.getCodigoUsuario());
                                    strSql+="',"+(objParSis.getCodigoUsuario());
                                    strSql+=")";

                                    System.out.print(strSql);
                                    stmLoc.executeUpdate(strSql);
                                   
                                     }
                                    

                      blnRes=true;
                    }
                    
                    
                    java.util.ArrayList arlAux=objTblMod.getDataSavedBeforeRemoveRow();
                            for (i=0;i<arlAux.size();i++)
                            {
                               
                                  //Armar la sentencia SQL.
                                    strSql="";
                                    //strSql+="delete from  tbm_tartra";
                                    String tiptar;
                                    tiptar=""+ objUti.getStringValueAt(arlAux,i, INT_CFE_COD_tipar);
                                
                                 if (tiptar.trim().equals("C"))
                                {
                                     strSql="";
                                     strSql+="delete from  tbm_tartra";
                                     strSql+=" WHERE co_dep=" + txtCodDep.getText();
                                     strSql+=" AND ne_pad=" + objUti.getObjectValueAt(arlAux,i, INT_CFE_COD_tar);
                                     stmLoc.executeUpdate(strSql);
                                     
                                     strSql="";
                                     strSql+="delete from  tbm_tartra";
                                     strSql+=" WHERE co_dep=" + txtCodDep.getText();
                                     strSql+=" AND co_tar=" + objUti.getObjectValueAt(arlAux,i, INT_CFE_COD_tar);
                                }
                                
                                     else if (tiptar.trim().equals("D"))
                                {
                                    
                                    strSql="";
                                    strSql+="delete from  tbm_tartra"; 
                                    strSql+=" WHERE co_dep=" + txtCodDep.getText();
                                    strSql+=" AND co_tar=" + objUti.getObjectValueAt(arlAux,i, INT_CFE_COD_tar);
                                    
                                 }
                                
                                
                               
                                stmLoc.executeUpdate(strSql);
                                
                               
                            }
                   

                    stmLoc.close();
                    stmLoc=null;
                   
                    objTblMod.initRowsState();
                    objTblMod.setDataModelChanged(false);
                    objTblMod.clearDataSavedBeforeRemoveRow();
                    arlAux.clear();
                    arlelihijos.clear();
                    elihijos= false;
                    
                    
                }
            }catch(java.sql.SQLException Evt) { Evt.printStackTrace(); blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
            catch(Exception  Evt){ Evt.printStackTrace();blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
            return blnRes;
     }

   

  
 

     
   
     
     
    
private class ZafMouMotAda extends MouseMotionAdapter {

        public ZafMouMotAda() { 
        }
        
        @Override
        public void mouseMoved(MouseEvent evt){
            String msg;
            int col = tblDat.columnAtPoint(evt.getPoint());
            
            switch(col){
                case INT_TBL_DAT_LIN: 
                    msg=""; 
                    break;
                case INT_TBL_DAT_Cod_Tar: 
                    msg="Código de la Tarea."; 
                    break;
                case INT_TBL_DAT_Ubi_TAR: 
                    msg="codtargen de la tarea."; 
                    break;
                case INT_TBL_DAT_TIP_TAR: 
                    msg="Tipo de la Tarea."; 
                    break;
                case INT_TBL_DAT_TAR_PAD: 
                    msg="Tarea Padre."; 
                    break;
                case INT_TBL_DAT_NIV_TAR: 
                    msg="Nivel de Tarea"; 
                    break;
                case  INT_TBL_DAT_FECHA:
                    msg="Fecha de la Tarea"; 
                    break;
                case INT_TBL_DAT_DES_COR: 
                    msg="Descripcion corta de la tarea"; 
                    break;
                case INT_TBL_DAT_BUT_DES_COR: 
                    msg="Muestra Descripcion mas detallada tarea"; 
                    break;
                case INT_TBL_DAT_DES_LAR: 
                    msg="Muestra la larga de la tarea"; 
                    break;
                case INT_TBL_DAT_COD_SOL: 
                    msg="Codigo del Empleado Solicitante"; 
                    break;
                case INT_TBL_DAT_SOLICITANTE: 
                    msg="Nombre del Empleado Solicitante"; 
                    break;
                    ///tool tip planifificacion
                  case INT_TBL_DAT_PRIORIDAD: 
                    msg="Prioridad de la Tarea"; 
                    break; 
                    
                case INT_TBL_DAT_COD_RES: 
                    msg="Codigo del Empleado Responsable "; 
                    break;
                    
                    case INT_TBL_DAT_RESPONSABLE: 
                    msg=" Empleado Responsable "; 
                    break;
                    case INT_TBL_DAT_FEC_INI_PLA: 
                    msg="Fecha de inicio Planifificada "; 
                    break;
                    case INT_TBL_DAT_FEC_FIN_PLA: 
                    msg="Fecha de Finalizacion planificada"; 
                    break;
                        /// desarrollo
                        
                    case INT_TBL_DAT_TAR_PEND: 
                    msg="Tarea  Pendiente "; 
                    break;
                        
                    case INT_TBL_DAT_Tar_Anular: 
                    msg="Tarea anulada "; 
                    break;
                        
                   case INT_TBL_DAT_TAR_PROCESO: 
                    msg="Tarea  Proceso "; 
                    break;
                       
                    case INT_TBL_DAT_TAR_DETENIDA: 
                    msg="Tarea  Detenida "; 
                    break;
                        
                    case INT_TBL_DAT_TAR_TERMINADA: 
                    msg="Tarea  Terminada "; 
                    break;
                        
                    case INT_TBL_DAT_POR_TRA_REA: 
                    msg="Porcentaje de Trabajo Realizado"; 
                    break;
                    case INT_TBL_DAT_FEC_INI_REA: 
                    msg="Fecha de Inicio Real   "; 
                    break;
                    case INT_TBL_DAT_FEC_ULT_TRA: 
                    msg="Fecha del Ultimo Trabajo Realizado "; 
                    break;
                default:
                    msg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(msg);            
        }
    }

 private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                  System.out.print("HOLA MUNDO");
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }
private class ZafThreadGUI extends Thread {

        public ZafThreadGUI() { 
        }
        
        @Override
        public void run(){
            pgrSis.setIndeterminate(true);
            
            if(!cargarDetReg(filtro())){
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            
            pgrSis.setIndeterminate(false);
            if (objTblMod.getRowCountTrue()>0) {
                tab_frm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;            
        }
    }

    private boolean regenerarUbicacionDb(Connection conUbi){
        boolean blnRes=true;
        Statement stmUbi;
        ResultSet rstUbi;
        String strSQLUpd="";
        try{ 
            if(conUbi!=null){
                stmUbi=conUbi.createStatement();
                for(int i=0; i<objTblModAux.getRowCountTrue(); i++){                
                    strSql="";
                    strSql+="UPDATE tbm_tartra";
                    strSql+=" SET ne_ubi=" + objTblModAux.getValueAt(i, INT_TBL_DAT_Ubi_TAR) + "";
                    strSql+=" WHERE co_dep=" + txtCodDep.getText() + "";
                    strSql+=" AND co_tar=" + objTblModAux.getValueAt(i, INT_TBL_DAT_Cod_Tar) + ";";
                    strSQLUpd+=strSql;
                }
                System.out.println("regenerarUbicacionDb: " + strSQLUpd);
                stmUbi.executeUpdate(strSQLUpd);                
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
    
    
    
    
    private boolean getCodTar_ubi(Connection conUbi){
        boolean blnRes=true;
        Statement stmUbi;
        ResultSet rstUbi;
        String strSQLCodTar_Ubi="";
        try{
            if(conUbi!=null){
                stmUbi=conUbi.createStatement();
                strSQLCodTar_Ubi="";
                strSQLCodTar_Ubi+="SELECT co_dep, MAX(co_tar) AS co_tar, MAX(ne_ubi) AS ne_ubi";
                strSQLCodTar_Ubi+="  FROM tbm_tartra";
                strSQLCodTar_Ubi+="  WHERE co_dep=" + txtCodDep.getText() + " GROUP BY co_dep";
                System.out.println("getCodTar_ubi: " + strSQLCodTar_Ubi);
                rstUbi=stmUbi.executeQuery(strSQLCodTar_Ubi);
                if(rstUbi.next()){
                    intMaxCodTar=rstUbi.getInt("co_tar");
                    intMaxUbi=rstUbi.getInt("ne_ubi");
                    
                    System.out.println("1-intMaxCodTar: " + intMaxCodTar);
                    System.out.println("1-intMaxUbi: " + intMaxUbi);
                    
                }
                stmUbi.close();
                stmUbi=null;
                rstUbi.close();
                rstUbi=null;
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

    
         /// configurar el formulario 
       private boolean configurarFrmAux(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecCabAux=new Vector();  //Almacena las cabeceras
            vecCabAux.clear();
            vecCabAux.add(INT_TBL_DAT_LIN,"");
            vecCabAux.add(INT_TBL_DAT_Cod_Tar,"Cod.Tar.");
            vecCabAux.add(INT_TBL_DAT_Ubi_TAR,"Ubi.Tar.");
            vecCabAux.add(INT_TBL_DAT_Ubi_TAR,"Ubi.Tar.");
            vecCabAux.add(INT_TBL_DAT_TAR_PAD,"Tar.");
            
            objTblModAux=new ZafTblMod();
            objTblModAux.setHeader(vecCabAux);
            tblAux.setModel(objTblModAux);
            
            //Configurar JTable: Establecer el modelo de la tabla.
            vecDatAux =new Vector();    //Almacena los datos
           
            //Configurar JTable: Establecer el ancho de las columnas.
            TableColumnModel tcmAux1=tblAux.getColumnModel();


            tcmAux1.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(1);
            tcmAux1.getColumn(INT_TBL_DAT_Cod_Tar).setPreferredWidth(1);
            tcmAux1.getColumn(INT_TBL_DAT_Ubi_TAR).setPreferredWidth(1);
            tcmAux1.getColumn(INT_TBL_DAT_TIP_TAR).setPreferredWidth(1);
            tcmAux1.getColumn(INT_TBL_DAT_TAR_PAD).setPreferredWidth(1);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblAux.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabAux=new ZafTblFilCab(tblAux);
            tcmAux1.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCabAux);  
            
             
            objTblFilCabAux=null;
            objTblModAux.setModoOperacion(objTblModAux.INT_TBL_INS);
     
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);  
        }
        return blnRes;
    }
  
       
       private boolean cargarTablaAuxiliar(){
        boolean blnRes = false;      

            strSql= " select a.co_tar , a.ne_ubi , a.tx_tip, a.ne_pad ,a.ne_niv , "
                    + "a.fe_tar ,a.tx_descor,a.tx_deslar , a.co_trasol,b.tx_nom||' '||b.tx_ape as solicitante, a.ne_pri ,a.co_trares ,CASE WHEN a.ne_pri=1 THEN 'Alta'  WHEN a.ne_pri=2 THEN 'Media' WHEN a.ne_pri=3 THEN 'Baja'ELSE '' END  ,c.tx_nom||' '||c.tx_ape as responsable ,"
                    + " a.fe_inipla,a.fe_finpla, a.st_reg, a.nd_portrarea, a.fe_inirea,a.fe_ulttrarea from tbm_tartra  a "
                    + " left outer  JOIN TBM_TRA b on ( a.co_trasol=b.co_tra) "
                    + "left outer  join tbm_tra c on (a.co_trares=c.co_tra) "
                    + "where a.co_dep = "+txtCodDep.getText() + " order by a.ne_ubi";
            System.out.println("ZafRechum.cargarDetReg:3322 " + strSql);
        
        try{
            conn=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stm=conn.createStatement();
            rst=stm.executeQuery(strSql);

            vecDatAux.clear();           
            while(rst.next()){
                vecRegAux=new Vector();
                vecRegAux.add(INT_TBL_DAT_LIN,"");
                vecRegAux.add(INT_TBL_DAT_Cod_Tar,rst.getString("co_tar"));
                vecRegAux.add(INT_TBL_DAT_Ubi_TAR,rst.getString("ne_ubi"));
                vecRegAux.add(INT_TBL_DAT_TIP_TAR,rst.getString("tx_tip"));
                vecRegAux.add(INT_TBL_DAT_TAR_PAD,rst.getString("ne_pad")); 
                vecDatAux.add(vecRegAux);
                blnRes=true;
                                
            }
            
            objTblModAux.setData(vecDatAux);
            tblAux.setModel(objTblModAux);
              
           
           
           
            rst.close();
            stm.close();
            conn.close();
            rst=null;
            stm=null;
            conn=null; 
          
          
             
        }catch(SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);            
        }        
        return blnRes;
               
       }
    
    
     private boolean confTblAuxiliar(int fila){
           boolean blnRes=true;
           int intCodTarPrn=-1, intCodTarAux=-1, intCodTarIns=-1;
           int intFilSel=fila;
           int j=1;
           try{
               intCodTarIns=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_Cod_Tar)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_Cod_Tar).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_Cod_Tar).toString()));
               if(intFilSel!=-1){
                   if (intFilSel==0)
                   intCodTarPrn=Integer.parseInt(objTblMod.getValueAt((0), INT_TBL_DAT_Cod_Tar)==null?"0":(objTblMod.getValueAt((0), INT_TBL_DAT_Cod_Tar).toString().equals("")?"0":objTblMod.getValueAt((0), INT_TBL_DAT_Cod_Tar).toString()));
                   
                   else 
                   intCodTarPrn=Integer.parseInt(objTblMod.getValueAt((intFilSel-1), INT_TBL_DAT_Cod_Tar)==null?"0":(objTblMod.getValueAt((intFilSel-1), INT_TBL_DAT_Cod_Tar).toString().equals("")?"0":objTblMod.getValueAt((intFilSel-1), INT_TBL_DAT_Cod_Tar).toString()));
               
                   for(int i=0; i<objTblModAux.getRowCountTrue(); i++){
                   intCodTarAux=Integer.parseInt(objTblModAux.getValueAt(i, INT_TBL_DAT_Cod_Tar)==null?"0":(objTblModAux.getValueAt(i, INT_TBL_DAT_Cod_Tar).toString().equals("")?"0":objTblModAux.getValueAt(i, INT_TBL_DAT_Cod_Tar).toString()));
                   if(intCodTarPrn==intCodTarAux){
                       objTblModAux.insertRow((i+1));                       
                       objTblModAux.setValueAt(intCodTarIns, (i+1), INT_TBL_DAT_Cod_Tar);
                       break;
                   }
               }
               }
               
               for(int i=0; i<objTblModAux.getRowCountTrue(); i++){
                   objTblModAux.setValueAt(j, i, INT_TBL_DAT_Ubi_TAR);
                   j++;
               }
               
               
               
               
               
           }
           catch(Exception e){
              blnRes=false;
              objUti.mostrarMsgErr_F1(this, e);            
           }
           return blnRes;
       }
       
       
       
       private boolean setCodigoPadreTbl(int fila){
           boolean blnRes=true;
           int intFilSel=fila;
           int intNivFilSel=-1;
           int intNivFilDes=-1;
           int intCodTarFilDes=-1;
           System.out.println("intNivFilSel:aaaaaaaaaaaaaaaaaa ");
           try{
               intNivFilSel=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NIV_TAR)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NIV_TAR).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NIV_TAR).toString()));
               System.out.println("intNivFilSel: " + intNivFilSel);
               
               intNivFilSel=intNivFilSel-1;
               if (intNivFilSel!=-1)
               {    
               for(int i=intFilSel; i>=0; i--){
                   intNivFilDes=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_NIV_TAR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_NIV_TAR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_NIV_TAR).toString()));
                   System.out.println("intNivFilDes: " + intNivFilDes);
                   if(intNivFilSel==intNivFilDes){
                       intCodTarFilDes=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_Cod_Tar)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_Cod_Tar).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_Cod_Tar).toString()));
                       
                       objTblMod.setValueAt(intCodTarFilDes, intFilSel, INT_TBL_DAT_TAR_PAD);
                       
                       break;}
               }
               }
               else 
                   
               {
                  mostrarMsgInf("No tiene subtareas usted se encuentra en una tarea padre");
               }
               
           }
           catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);            
           }
           return blnRes;
       }
       
      
       
      private void movtarpad()
      {
                    
         int nivsegu=Integer.parseInt(objTblMod.getValueAt(1, INT_TBL_DAT_NIV_TAR)==null?"0":(objTblMod.getValueAt(1, INT_TBL_DAT_NIV_TAR).toString().equals("")?"0":objTblMod.getValueAt(1, INT_TBL_DAT_NIV_TAR).toString()));
         String titar=objTblMod.getValueAt(1, INT_TBL_DAT_TIP_TAR)==null?"0":(objTblMod.getValueAt(1, INT_TBL_DAT_TIP_TAR).toString().equals("")?"0":objTblMod.getValueAt(1, INT_TBL_DAT_TIP_TAR).toString());
         boolean tarpad=false;
         int taraux=0;
                  

         for (int j = 0; j< objTblMod.getRowCountTrue() ;j++)
                             
            {    
                
                 
         
           int intNivFilSel=-1;
           int intNivFilDes=-1;
           int intCodTarFilDes=-1;
           int intcodpadre=-1;
                    
           try{
               intNivFilSel=Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_NIV_TAR)==null?"0":(objTblMod.getValueAt(j, INT_TBL_DAT_NIV_TAR).toString().equals("")?"0":objTblMod.getValueAt(j, INT_TBL_DAT_NIV_TAR).toString()));
               intNivFilSel=intNivFilSel-1;

               
                if (intNivFilSel!=-1)
               {  
                   if (j==0)
                   {
                    objTblMod.setValueAt(1, 0, INT_TBL_DAT_NIV_TAR);
                    objTblMod.setValueAt(0, 0, INT_TBL_DAT_TAR_PAD);
                    objTblMod.setValueAt(objTblMod.getValueAt(0,INT_TBL_DAT_DES_COR).toString().trim(), 0, INT_TBL_DAT_DES_COR);
                    objTblMod.setValueAt("C", 0, INT_TBL_DAT_TIP_TAR);
                   }
                   else if (j==1)
                   {
                       int tarpa=Integer.parseInt(objTblMod.getValueAt(0, INT_TBL_DAT_Cod_Tar)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_Cod_Tar).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_Cod_Tar).toString()));
                       objTblMod.setValueAt(tarpa, j, INT_TBL_DAT_TAR_PAD);
                       objTblMod.setValueAt(nivsegu, j, INT_TBL_DAT_NIV_TAR);
                       if (nivsegu==2)
                       {
                        objTblMod.setValueAt("        "+objTblMod.getValueAt(j,INT_TBL_DAT_DES_COR).toString().trim(), j, INT_TBL_DAT_DES_COR);
                       }
                       objTblMod.setValueAt(titar, j, INT_TBL_DAT_TIP_TAR);
                      
                   }
                  
                   else{
                            int redei=j;
                            
                             if (intNivFilSel==0)
                                {
                                   intNivFilSel=1;
                                   redei--;
                                   taraux++;
                                   if(taraux>1)
                                   tarpad=true;
                                } 
                           for(int i=redei; i>=0; i--){
                            intNivFilDes=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_NIV_TAR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_NIV_TAR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_NIV_TAR).toString()));
                            System.out.println("intNivFilDes: " + intNivFilDes);
                            
                            if(intNivFilSel==intNivFilDes){
                                
                                intCodTarFilDes=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_Cod_Tar)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_Cod_Tar).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_Cod_Tar).toString()));
                                intcodpadre=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_TAR_PAD)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_TAR_PAD).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_TAR_PAD).toString()));
                                String tiptar = objTblMod.getValueAt(i, INT_TBL_DAT_TIP_TAR).toString();
                                objTblMod.setValueAt(intCodTarFilDes, j, INT_TBL_DAT_TAR_PAD);
                                
                                if (tarpad==true&& tiptar.equals("C"))
                                {
                                 objTblMod.setValueAt(intcodpadre, j, INT_TBL_DAT_TAR_PAD);
                                 tarpad=false;
                                }
                                break;}
                            }
                        }
               }
//               else 
//                   
//               {
//                  mostrarMsgInf("Se encuentra en una tarea padre que no puede ser movida ");
//               }
               
           }
           catch(Exception e){
           
            objUti.mostrarMsgErr_F1(this, e);            
           }
          
           
            }  
      }


}
