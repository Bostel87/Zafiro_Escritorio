/*
 * ZafCom12.java
 *
 * Created on 20 de agosto de 2005, 11:38 PM  
 */
package Contabilidad.ZafCon47;
import Librerias.ZafParSis.ZafParSis;  
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;  //**************************    
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import java.util.StringTokenizer;
   
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author  Javier Ayapata
 */
public class ZafCon47 extends javax.swing.JInternalFrame 
{
    mitoolbar objTooBar;
    
    //Constantes: Columnas del JTable:
    final int INT_TBL_LIN=0;            //Línea
    final int INT_TBL_BUT=1;            //boton de busqueda.
    final int INT_TBL_ESP=2;            //numero de cuenta
    final int INT_TBL_NUM=3;            //numero de cuenta
    final int INT_TBL_NCT=4;            //numero de cuenta
    final int INT_TBL_DES=5;            //Descripcion
    final int INT_TBL_SME=6;            //Saldo mensuales.
    final int INT_TBL_SNU=7;            //Saldo Anuales.
    final int INT_TBL_NIV=8;            //Saldo Anuales.     
    final int INT_TBL_VAL=9;            //Saldo Anuales. 
    final int INT_TBL_PAD=10;
    final int INT_TBL_TIP=11;
    final int INT_TBL_EST=12;
    final int INT_TBL_LINEA=13;
    final int INT_TBL_EMP=14;
    
      
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                //Editor: Editor del JTable.
 //   private ZafThreadGUI objThrGUI;
    private ZafTblCelRenChk objTblCelRenChk;    //Render: Presentar JCheckBox en JTable.  
 //   private ZafMouMotAda objMouMotAda;          //ToolTipText en TableHeader.
    private ZafTblPopMnuFluPer ZafTblPopMn;  
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private ArrayList arlDat, arlRegMod;
    private boolean blnCon;                     //true: Continua la ejecución del hilo.
    private String strCodAlt, strNomItm;                //Contenido del campo al obtener el foco.
    javax.swing.JInternalFrame jfrThis; //Hace referencia a this
    ZafVenCon objVenConTipdoc; //***************** 
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;        //Render: Presentar JButton en JTable.
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;   //Editor: JButton en celda.
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;        //Editor: JButton en celda.    
    String strFlt;//EL filtro de la Consulta actual
    ResultSet rstCab;
    boolean blnCam=false;
    
    String VERSION = " v0.4";       
    Contabilidad.ZafCon47.ZafCon47_01 obj1;
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */      
     public ZafCon47(ZafParSis obj) 
    {
         objParSis=obj;
         
         initComponents();  
         jfrThis = this;
            
         objTooBar = new mitoolbar(this);
         
         this.getContentPane().add(objTooBar,"South");
         
       
         
        
    }
     
        
    
     public void Configura_ventana_consulta(){
        ConfigurarTabla();
        
         obj1 = new  Contabilidad.ZafCon47.ZafCon47_01( javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
         
         
    }
    
     
     
     
     
     
   private boolean ConfigurarTabla(){
       boolean blnRes=false;
       try{
            //Configurar JTable: Establecer el modelo.
            Vector vecDat=new Vector();    //Almacena los datos
            Vector vecCab=new Vector();    //Almacena las cabeceras  
            vecCab.clear();
            
            vecCab.add(INT_TBL_LIN,"");            
            vecCab.add(INT_TBL_BUT," ");
            vecCab.add(INT_TBL_ESP," "); 
            vecCab.add(INT_TBL_NUM,"COd.Cta");
            vecCab.add(INT_TBL_NCT,"Núm.Cuenta");
            vecCab.add(INT_TBL_DES,"Descripcion");            
            vecCab.add(INT_TBL_SME,"Sal.Mens.");
            vecCab.add(INT_TBL_SNU,"Sal.Anu.");            
            vecCab.add(INT_TBL_NIV," NIVEL ");
            vecCab.add(INT_TBL_VAL, "COD ");
            vecCab.add(INT_TBL_PAD," PAD ");
            vecCab.add(INT_TBL_TIP, " ");
            vecCab.add(INT_TBL_EST, "ESTADO ");    
            vecCab.add(INT_TBL_LINEA,"lINEA");
            vecCab.add(INT_TBL_EMP, "EMPGRP");    
            
            
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);

            tblDat.setModel(objTblMod);   
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(false);
          //  tblDat.setCellSelectionEnabled(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                  
                     
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LIN);
                  
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(20);         
            tcmAux.getColumn(INT_TBL_BUT).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NCT).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DES).setPreferredWidth(380);
            tcmAux.getColumn(INT_TBL_SME).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_SNU).setPreferredWidth(70);
             
            tblDat.getTableHeader().setReorderingAllowed(false);        
            
            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_ESP).setWidth(0);
            tcmAux.getColumn(INT_TBL_ESP).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ESP).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ESP).setPreferredWidth(0);
            
            tcmAux.getColumn(INT_TBL_NUM).setWidth(0);
            tcmAux.getColumn(INT_TBL_NUM).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_NUM).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_NUM).setPreferredWidth(0);  
            
            tcmAux.getColumn(INT_TBL_TIP).setWidth(0);
            tcmAux.getColumn(INT_TBL_TIP).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_TIP).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_TIP).setPreferredWidth(0);
              
            tcmAux.getColumn(INT_TBL_NIV).setWidth(0);
            tcmAux.getColumn(INT_TBL_NIV).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_NIV).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_NIV).setPreferredWidth(0);
              
            tcmAux.getColumn(INT_TBL_VAL).setWidth(0);
            tcmAux.getColumn(INT_TBL_VAL).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_VAL).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_VAL).setPreferredWidth(0); 
              
            tcmAux.getColumn(INT_TBL_PAD).setWidth(0);
            tcmAux.getColumn(INT_TBL_PAD).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_PAD).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_PAD).setPreferredWidth(0);
            
            tcmAux.getColumn(INT_TBL_EST).setWidth(0);
            tcmAux.getColumn(INT_TBL_EST).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_EST).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_EST).setPreferredWidth(0);
               
            tcmAux.getColumn(INT_TBL_LINEA).setWidth(0);
            tcmAux.getColumn(INT_TBL_LINEA).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_LINEA).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(0);
            
//            tcmAux.getColumn(INT_TBL_EMP).setWidth(0);
//            tcmAux.getColumn(INT_TBL_EMP).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_EMP).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_EMP).setPreferredWidth(0);
             
            
            
             //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_BUT);
            vecAux.add("" + INT_TBL_SME);
            vecAux.add("" + INT_TBL_SNU);
            
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
          
            //Configurar JTable: Editor de la tabla.  
            objTblEdi=new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            
             
             //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_SME).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_SNU).setCellRenderer(objTblCelRenChk);
            
            objTblCelRenChk=null;
            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_SME).setCellEditor(objTblCelEdiChk);
            //tcmAux.getColumn(INT_TBL_SNU).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    int intFil = tblDat.getSelectedRow(); 
                 
                    if(tblDat.getValueAt( intFil, INT_TBL_SNU)!=null){
                            if(tblDat.getValueAt( intFil,  INT_TBL_SNU).toString().equalsIgnoreCase("true")) {
                                tblDat.setValueAt( new Boolean(false), intFil,  INT_TBL_SNU);
                                tblDat.setValueAt( new Boolean(true), intFil,  INT_TBL_SME);  
                                blnCam=true;
                            }else{
                                 tblDat.setValueAt( new Boolean(true), intFil,  INT_TBL_SME);
                            }
                    }else  tblDat.setValueAt( new Boolean(true), intFil,  INT_TBL_SME);
                    
                }        
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblDat.getSelectedRow();  
                    
                    if(tblDat.getValueAt( intFil, INT_TBL_SNU)!=null){
                            if(tblDat.getValueAt( intFil,  INT_TBL_SNU).toString().equalsIgnoreCase("true")) {
                                tblDat.setValueAt( new Boolean(false), intFil,  INT_TBL_SNU);
                                tblDat.setValueAt( new Boolean(true), intFil,  INT_TBL_SME);
                                 blnCam=true;
                            }else{
                                 tblDat.setValueAt( new Boolean(true), intFil,  INT_TBL_SME);
                            }
                    } else tblDat.setValueAt( new Boolean(true), intFil,  INT_TBL_SME);
                }
            });

                    
              
                     
            objTblCelRenChk=null;
            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_SNU).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                     int intFil = tblDat.getSelectedRow(); 
                    
                     if(tblDat.getValueAt( intFil, INT_TBL_SME)!=null){
                            if(tblDat.getValueAt( intFil,  INT_TBL_SME).toString().equalsIgnoreCase("true")) {
                                tblDat.setValueAt( new Boolean(false), intFil,  INT_TBL_SME);
                                tblDat.setValueAt( new Boolean(true), intFil,  INT_TBL_SNU);
                                blnCam=true;
                                 
                            }else{
                                 tblDat.setValueAt( new Boolean(true), intFil,  INT_TBL_SNU);
                     }}else  tblDat.setValueAt( new Boolean(true), intFil,  INT_TBL_SNU);
                       
                }          
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblDat.getSelectedRow();  
                    
                    
                    if(tblDat.getValueAt( intFil, INT_TBL_SME)!=null){
                            if(tblDat.getValueAt( intFil,  INT_TBL_SME).toString().equalsIgnoreCase("true")) {
                                tblDat.setValueAt( new Boolean(false), intFil,  INT_TBL_SME);
                                tblDat.setValueAt( new Boolean(true), intFil,  INT_TBL_SNU);
                                blnCam=true;
                            }else{
                                 tblDat.setValueAt( new Boolean(true), intFil,  INT_TBL_SNU);
                    }}else  tblDat.setValueAt( new Boolean(true), intFil,  INT_TBL_SNU);
                    
                }
            });
            
            
            objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUT).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            ButFndItm ObjFndItm = new ButFndItm(tblDat, INT_TBL_BUT);   //*****
            
               
            tcmAux=null;
            setEditable(false);
            
            
            
            
            
              //Configurar JTable: Centrar columnas.
            ZafTblPopMn = new ZafTblPopMnuFluPer(tblDat);
            ZafTblPopMn.setInsertarFilaVisible(false);
            ZafTblPopMn.setInsertarFilasVisible(false);
            
           
            
             //***********************************************************
            ZafTblPopMn.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                         
                    if (ZafTblPopMn.isClickInsertarFila())
                    {
                      
                    } 
                    else if (ZafTblPopMn.isClickEliminarFila())  
                    {

                    }
                }
                 
                
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    
                    
                     if (ZafTblPopMn.isClickModificarFila())
                    {
                        //Escriba aquí el código que se debe realizar luego de insertar la fila.
                       ModificaReg();
                    }
                     
                    
                     else  if (ZafTblPopMn.isClickInsertarFila())
                    {
                       //System.out.println("afterClick: isClickInsertarFila");
                    }
                    else if (ZafTblPopMn.isClickEliminarFila())
                    {
                        
                        EliminarReg();
                        
                      //  javax.swing.JOptionPane.showMessageDialog(null, "Las filas se eliminaron con éxito.");
                    }
                 
                }
            });
            
          //***********************************************************
            
            
            
        }catch(Exception e) {
            blnRes=false;    
            objUti.mostrarMsgErr_F1(this,e);
        }
       return blnRes;    
    }
   
   
   
   
   
   
   
    
      public void setEditable(boolean editable)
       {
            if (editable==true){
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

            }else{
                objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
            }       
       }

       
       
      
         
          
            
       
        private void EliminarReg(){
           Contabilidad.ZafCon47.ZafCon47_02 obj2 = new  Contabilidad.ZafCon47.ZafCon47_02( javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
           obj2.show();
           javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
           String  strTit="Mensaje del sistema Zafiro";
           String  strMsg="La factura se grabo correctamente"; 
           String  strMsg2="Esta Seguro que desea Eliminar la linea seleccionada \n caso contrario se eliminara la generario de de reportes financieros personalizados";
            int intUbi=1;
                              
                  if(obj2.acepta()){  
                         
                          //  ELIMINA  BLOQUE DE CUENTA      
                         
                         if(obj2.GetCamSel(1).equals("1")){
                           
                        //**************************************************************************************
                           int intCodRegHj=-1;
                           int intCodRegHj01=-1;
                           int intCodRegHj02=-1;
                           int intCodRegHj03=-1;
                           int intCodReg = Integer.parseInt(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_VAL).toString()); 
                          
                           
                         if(oppMsg.showConfirmDialog(jfrThis,strMsg2,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 )
                         {
                           
                           Vector vecData = new Vector();
                           for(int i=0; i<tblDat.getRowCount(); i++){
                                //java.util.Vector vecReg = new java.util.Vector();
                                int intval = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_VAL).toString());
                                int intPad = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_PAD).toString());
                                 
                                if(!(intCodReg==intval)){
                                    if(!(intCodReg==intPad)){
                                       if(!(intCodRegHj==intPad)){
                                         if(!(intCodRegHj01==intPad)){
                                           if(!(intCodRegHj02==intPad)){
                                            if(!(intCodRegHj03==intPad)){     
                                            java.util.Vector vecReg = new java.util.Vector();
                                            vecReg.add(INT_TBL_LIN, "");
                                            vecReg.add(INT_TBL_BUT, "");
                                            vecReg.add(INT_TBL_ESP, ((tblDat.getValueAt(i, INT_TBL_ESP)==null)?"":tblDat.getValueAt(i, INT_TBL_ESP).toString()) );
                                            vecReg.add(INT_TBL_NUM, ((tblDat.getValueAt(i, INT_TBL_NUM)==null)?"":tblDat.getValueAt(i, INT_TBL_NUM).toString()) );
                                            vecReg.add(INT_TBL_NCT, ((tblDat.getValueAt(i, INT_TBL_NCT)==null)?"":tblDat.getValueAt(i, INT_TBL_NCT).toString()) );
                                      
                                            vecReg.add(INT_TBL_DES, tblDat.getValueAt( i, INT_TBL_DES ).toString() );
                                            Boolean blnIva = new Boolean(false);                     
                                             String strest = ((tblDat.getValueAt(i, INT_TBL_SME).toString().equals("true"))?"S":"N");
                                            if(strest.equals("S")) blnIva = new Boolean(true);
                                             vecReg.add(INT_TBL_SME,  blnIva );
                                             blnIva = new Boolean(false);
                                            strest = ((tblDat.getValueAt(i, INT_TBL_SNU).toString().equals("true"))?"S":"N");
                                             if(strest.equals("S")) blnIva = new Boolean(true);
                                            vecReg.add(INT_TBL_SNU, blnIva );
                                            vecReg.add(INT_TBL_NIV, tblDat.getValueAt(i, INT_TBL_NIV).toString() );
                                            vecReg.add(INT_TBL_VAL, tblDat.getValueAt(i, INT_TBL_VAL).toString() );
                                            vecReg.add(INT_TBL_PAD, tblDat.getValueAt(i, INT_TBL_PAD).toString() );
                                            vecReg.add(INT_TBL_TIP, tblDat.getValueAt(i, INT_TBL_TIP).toString() );
                                            vecReg.add(INT_TBL_EST, tblDat.getValueAt(i, INT_TBL_EST).toString() );
                                            vecReg.add(INT_TBL_LINEA, ""+intUbi );
                                            vecReg.add(INT_TBL_EMP, ((tblDat.getValueAt(i, INT_TBL_EMP)==null)?"":tblDat.getValueAt(i, INT_TBL_EMP).toString()) );       
                                            vecData.add(vecReg);         
                                            intUbi++; 
                                            
                                            }  else {  System.out.println("CodReg. "+ tblDat.getValueAt(i, INT_TBL_VAL).toString() +" LINEA. "+(i+1) );  arlDat.add(""+intval);   }
                                         }else { intCodRegHj03 = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_VAL).toString());  System.out.println("CodReg. "+intCodRegHj03+" LINEA. "+(i+1));   arlDat.add(""+intval);    }  
                                         }else { intCodRegHj02 = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_VAL).toString());  System.out.println("CodReg. "+intCodRegHj02+" LINEA. "+(i+1));   arlDat.add(""+intval);   }  
                                         }else { intCodRegHj01 = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_VAL).toString()); System.out.println("CodReg. "+intCodRegHj01+" LINEA. "+(i+1));    arlDat.add(""+intval);  }  
                                         }else { intCodRegHj = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_VAL).toString());  System.out.println("CodReg. "+intCodRegHj+" LINEA. "+(i+1)  );     arlDat.add(""+intval);   }  
                                 } else { System.out.println("CodReg. "+ tblDat.getValueAt(i, INT_TBL_VAL).toString() +" LINEA. "+(i+1) );    arlDat.add(""+intval);   }
                           
                           }  
                              
                                      objTblMod.setData(vecData);
                                      tblDat .setModel(objTblMod);
                                      blnCam=true;
                          }
                         
                         }   
                  //********************************************************************************************************************   
                          
                            //  ELIMINA SOLO LA LINEA
                 //*********************************************************************************************************************   
                        if(obj2.GetCamSel(1).equals("2")){
                              Vector vecData = new Vector();
                              int x = tblDat.getSelectedRow();
                              int xx=0;
                              int intCodReg = Integer.parseInt(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_VAL).toString()); 
                              int intCodPad = Integer.parseInt(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PAD).toString()); 
                            
                              
                             
                            
                              
                              if(oppMsg.showConfirmDialog(jfrThis,strMsg2,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 )
                              {
                                  
                                  System.out.println("CodReg. "+ tblDat.getValueAt(x, INT_TBL_VAL).toString() + "  LINEA. "+ (x+1)  );
                                  
                                  arlDat.add(""+intCodReg); 
                                  
                                      System.out.println(" Elimando... ");                 
                                                      
                              
                              
                              
                              for(int i=0; i<tblDat.getRowCount(); i++){
                                  int intval = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_VAL).toString());
                                  int intPad = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_PAD).toString());
                                  int intNiv = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_NIV).toString());
                                   
                                 if(intPad==intCodReg){
                                       if(xx==0) tblDat.setValueAt( ""+intCodPad, i, INT_TBL_PAD );
                                       tblDat.setValueAt( ""+(intNiv-1), i, INT_TBL_NIV );
                                          
                                       xx=1;  
                                        intCodReg=intval;
                                      //intCodPad=intPad;
                                     
                                      
                                 }
                              }
                                     
                              
                             
                              for(int i=0; i<tblDat.getRowCount(); i++){
                                  int intval = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_VAL).toString());
                                  int intPad = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_PAD).toString());
                                
                                  if(i!=x){
                                      
                                            java.util.Vector vecReg = new java.util.Vector();
                                            vecReg.add(INT_TBL_LIN, "");
                                            vecReg.add(INT_TBL_BUT, "");
                                            vecReg.add(INT_TBL_ESP, ((tblDat.getValueAt(i, INT_TBL_ESP)==null)?"":tblDat.getValueAt(i, INT_TBL_ESP).toString()) );
                                            vecReg.add(INT_TBL_NUM, ((tblDat.getValueAt(i, INT_TBL_NUM)==null)?"":tblDat.getValueAt(i, INT_TBL_NUM).toString()) );
                                            vecReg.add(INT_TBL_NCT, ((tblDat.getValueAt(i, INT_TBL_NCT)==null)?"":tblDat.getValueAt(i, INT_TBL_NCT).toString()) );
                                      
                                            vecReg.add(INT_TBL_DES, tblDat.getValueAt( i, INT_TBL_DES ).toString() );
                                            Boolean blnIva = new Boolean(false);                     
                                             String strest = ((tblDat.getValueAt(i, INT_TBL_SME).toString().equals("true"))?"S":"N");
                                            if(strest.equals("S")) blnIva = new Boolean(true);
                                             vecReg.add(INT_TBL_SME,  blnIva );
                                             blnIva = new Boolean(false);
                                            strest = ((tblDat.getValueAt(i, INT_TBL_SNU).toString().equals("true"))?"S":"N");
                                             if(strest.equals("S")) blnIva = new Boolean(true);
                                            vecReg.add(INT_TBL_SNU, blnIva );
                                            vecReg.add(INT_TBL_NIV, tblDat.getValueAt(i, INT_TBL_NIV).toString() );
                                            vecReg.add(INT_TBL_VAL, tblDat.getValueAt(i, INT_TBL_VAL).toString() );
                                            vecReg.add(INT_TBL_PAD, tblDat.getValueAt(i, INT_TBL_PAD).toString() );
                                            vecReg.add(INT_TBL_TIP, tblDat.getValueAt(i, INT_TBL_TIP).toString() );
                                            vecReg.add(INT_TBL_EST, tblDat.getValueAt(i, INT_TBL_EST).toString() );
                                            vecReg.add(INT_TBL_LINEA, ""+intUbi );
                                            vecReg.add(INT_TBL_EMP, tblDat.getValueAt(i, INT_TBL_EMP).toString() );       
                                            vecData.add(vecReg);    
                                           intUbi++; 
                                      
                                  }
                                  
                               
                               }
                              
                              objTblMod.setData(vecData);
                              tblDat .setModel(objTblMod);
                               blnCam=true;
                       //************************************************************************************************************************ 
                                 
                             String stresp="    ";
                             String stresp2=""; 
                             vecData.clear(); 
                             for(int i=0; i<tblDat.getRowCount(); i++){ 
                                     java.util.Vector vecReg = new java.util.Vector();
                  
                                      int intval = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_VAL).toString());
                                     
                                       
                                      vecReg.add(INT_TBL_LIN, "");
                                      vecReg.add(INT_TBL_BUT, "");
                                      
                                      vecReg.add(INT_TBL_ESP, ((tblDat.getValueAt(i, INT_TBL_ESP)==null)?"":tblDat.getValueAt(i, INT_TBL_ESP).toString()) );
                                      vecReg.add(INT_TBL_NUM, ((tblDat.getValueAt(i, INT_TBL_NUM)==null)?"":tblDat.getValueAt(i, INT_TBL_NUM).toString()) );
                                      vecReg.add(INT_TBL_NCT, ((tblDat.getValueAt(i, INT_TBL_NCT)==null)?"":tblDat.getValueAt(i, INT_TBL_NCT).toString()) );
                                      
                                    
                                      
                                      int intNiv = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_NIV).toString());
                                      
                                      stresp2="";
                                      for(int z=0; z<intNiv; z++)
                                          stresp2+=stresp;
                                      
                                            
                                      vecReg.add(INT_TBL_DES,  stresp2+tblDat.getValueAt( i, INT_TBL_DES ).toString().trim() );
                                      
                                      
                                       Boolean blnAnu = new Boolean(false);   
                                       Boolean blnMes = new Boolean(false);   
                                               
                                       String strest = ((tblDat.getValueAt(i, INT_TBL_SME).toString().equals("true"))?"S":"N");
                                        if(strest.equals("S")) blnMes = new Boolean(true);
                                       
                                     
                                       strest = ((tblDat.getValueAt(i, INT_TBL_SNU).toString().equals("true"))?"S":"N");
                                       if(strest.equals("S")) blnAnu = new Boolean(true);
                                    
                                      vecReg.add(INT_TBL_SME,  blnMes );
                                      vecReg.add(INT_TBL_SNU, blnAnu );
                                      vecReg.add(INT_TBL_NIV, tblDat.getValueAt(i, INT_TBL_NIV).toString() );
                                  
                                      vecReg.add(INT_TBL_VAL, ""+intval );
                                      vecReg.add(INT_TBL_PAD, tblDat.getValueAt(i, INT_TBL_PAD).toString() );
                                      vecReg.add(INT_TBL_TIP , tblDat.getValueAt(i, INT_TBL_TIP).toString() );
                                      vecReg.add(INT_TBL_EST, tblDat.getValueAt(i, INT_TBL_EST).toString() );       
                                      vecReg.add(INT_TBL_LINEA, tblDat.getValueAt(i, INT_TBL_LINEA).toString() );       
                                      vecReg.add(INT_TBL_EMP, tblDat.getValueAt(i, INT_TBL_EMP).toString() );       
                                         
                                      
                                      
                                    vecData.add(vecReg);    
                              
                             }    
                              
                              objTblMod.setData(vecData);
                              tblDat .setModel(objTblMod); 
                               blnCam=true;
                              
                               
                       //************************************************************************************************************************ 
                              
                              
                             
                            
                        }  }

                     }
                       obj2.dispose();
                       obj2=null;  
       }
   
       
        
        
        
        
        
        
        
        
        
        
          
        
     private boolean ModificaReg(){
         boolean blnRes=false;
          //Contabilidad.ZafCon47.ZafCon47_01 obj1 = new  Contabilidad.ZafCon47.ZafCon47_01( javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
           obj1.show();
           
           
                     if(obj1.acepta()){  
                         
                         String stresp="    ";
                         String stresp2=""; 
                         int intTip=0;       
                         int intNiv=0;
                         int intCodReg=0;
                         
                           
                           
                          String strNumCta=obj1.GetCamSel(1);
                         // String strDesCta=obj1.GetCamSel(1);  
                          
                          //String strNumCta=obj1.GetCamSel(4);
                          //String strDesCta=obj1.GetCamSel(2).trim();
                          
                          String strNumCta1="",strDesCta1="";
                          for(int i=0; i<tblDat.getRowCount(); i++){
                              //strNumCta1= (tblDat.getValueAt(i, INT_TBL_NCT)==null)?"":tblDat.getValueAt(i, INT_TBL_NCT).toString().trim();
                              
                              strNumCta1= (tblDat.getValueAt(i, INT_TBL_NCT)==null)?"":tblDat.getValueAt(i, INT_TBL_NCT).toString().trim();   
                            //  strDesCta1= (tblDat.getValueAt(i, INT_TBL_DES)==null)?"":tblDat.getValueAt(i, INT_TBL_DES).toString().trim();   
                              
                              
                              if(!strNumCta1.equals("")){
                                 if(strNumCta1.equals(strNumCta)){
                              //  if(!( strNumCta1.equals("") && strDesCta1.equals("") ) ){
                               //  if(strNumCta1.equals(strNumCta) && strDesCta1.equals(strDesCta) ){
                                     
                                    System.out.println("CODIGO CUENTA REPETIDO.. "); 
                                    MensajeInf("Numero de cuenta ya existe ");
                                     obj1.hide();
                                   //  obj1=null;
                                    return false;
                                 }
                              }  
                          }
                         
                        /// vecReg.add(INT_TBL_NCT, ((tblDat.getValueAt(i, INT_TBL_NCT)==null)?"":tblDat.getValueAt(i, INT_TBL_NCT).toString()) );
                                        
                         
                          strNumCta1="";             
                            
                               int i = tblDat.getSelectedRow(); 
                                  System.out.println(" fila "+i); 
                               
                                   intNiv = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_NIV).toString());
                                   intCodReg = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_VAL).toString());
                                   
                                   strNumCta1 =(tblDat.getValueAt(i, INT_TBL_NCT)==null)?"":tblDat.getValueAt(i, INT_TBL_NCT).toString().trim();   
                                  // String strNumCta= obj1.GetCamSel(1);
                                   
                                   if(!strNumCta1.equals(strNumCta)) {
                                       arlRegMod.add(""+intCodReg);
                                   }
                                      
                                   intNiv++;
                                      for(int x=1; x<intNiv; x++)
                                          stresp2+=stresp;
                                      
                                  tblDat.setValueAt( obj1.GetCamSel(1), i, INT_TBL_NUM );
                                  tblDat.setValueAt( obj1.GetCamSel(4), i, INT_TBL_NCT );
                                  tblDat.setValueAt( stresp2+obj1.GetCamSel(2), i, INT_TBL_DES );
                                  tblDat.setValueAt( obj1.GetCamSel(3), i, INT_TBL_TIP );
                                 // tblDat.setValueAt( "M", i, INT_TBL_EST );
                                  tblDat.setValueAt( obj1.GetCamSel(5), i, INT_TBL_EMP );
                                  
                                  
                                  
                                  blnRes=true;
                                  blnCam=true;
                     } 
               obj1.hide();
               //obj1=null;
            
           return blnRes;   
       }
   
       
       
       
         
       
       private class ButFndItm extends Librerias.ZafTableColBut.ZafTableColBut{
        public ButFndItm(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx);
        }
        public void butCLick() {
             llamada();
            
            
      }}
       
       
       
        private void MensajeInf(String strMensaje){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit;
            strTit="Mensaje del sistema Zafiro";
            obj.showMessageDialog(jfrThis,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
       }
        
        
           
        
       private boolean llamada(){
           boolean blnRes=false;
           try{
         //  Contabilidad.ZafCon47.ZafCon47_01 obj1 = new  Contabilidad.ZafCon47.ZafCon47_01( javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
           obj1.show();
           
           
                   if(obj1.acepta()){       
                          
                          String strCta=obj1.GetCamSel(4);
                          String strDesCtaOb = obj1.GetCamSel(2).trim();
                          String strNumCta="",strDesCta="";
                          for(int i=0; i<tblDat.getRowCount(); i++){
                              strNumCta= (tblDat.getValueAt(i, INT_TBL_NCT)==null)?"":tblDat.getValueAt(i, INT_TBL_NCT).toString().trim();   
                              strDesCta= (tblDat.getValueAt(i, INT_TBL_DES)==null)?"":tblDat.getValueAt(i, INT_TBL_DES).toString().trim();   
                             
                              if(!( strNumCta.equals("") && strDesCta.equals("") ) ){
                                 if(strNumCta.equals(strCta) && strDesCta.equals(strDesCtaOb) ){
                                    System.out.println("CODIGO CUENTA REPETIDO.. "); 
                                    MensajeInf("Numero de cuenta ya existe ");
                                    return false;
                                 }
                              }
                          }
                         
                              
                            
                          
                           strNumCta = (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_NUM)==null)?"":tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_NUM).toString().trim();  
                           if(!strNumCta.equals("")){
                                 MensajeInf("No es posible agregar nivel una cuenta de Mayor o de Detalle. ");
                                 return false;
                           }
                            
                          
                         
                         
                         String stresp="    ";
                         String stresp2=""; 
                         int intTip=0;       
                         int intNiv=0;
                         int intUbi=1;
                         Vector vecData = new Vector();
                         
                         for(int i=0; i<tblDat.getRowCount(); i++){
                               
                                         
                               if(i == tblDat.getSelectedRow()){
                                   
                                   int intval2 = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_VAL).toString());
                                   
                                     
                                 //*******************************************************
                                      java.util.Vector vecReg = new java.util.Vector();
                                      vecReg.add(INT_TBL_LIN, "");
                                      vecReg.add(INT_TBL_BUT, "");
                                //System.out.println(" AQUI 1  "+ tblDat.getValueAt(i, INT_TBL_NUM) );
                                      vecReg.add(INT_TBL_ESP, ((tblDat.getValueAt(i, INT_TBL_ESP)==null)?"":tblDat.getValueAt(i, INT_TBL_ESP).toString()) );
                                      vecReg.add(INT_TBL_NUM, ((tblDat.getValueAt(i, INT_TBL_NUM)==null)?"":tblDat.getValueAt(i, INT_TBL_NUM).toString()) );
                                      vecReg.add(INT_TBL_NCT, ((tblDat.getValueAt(i, INT_TBL_NCT)==null)?"":tblDat.getValueAt(i, INT_TBL_NCT).toString()) );
                                      
                                    
                                      
                                      
                                //System.out.println(" AQUI 2  ");     
                                      vecReg.add(INT_TBL_DES, tblDat.getValueAt( i, INT_TBL_DES ).toString() );
                                      Boolean blnIva = new Boolean(false);                     
                                        String strest = ((tblDat.getValueAt(i, INT_TBL_SME).toString().equals("true"))?"S":"N");
                                        if(strest.equals("S")) blnIva = new Boolean(true);
                                      vecReg.add(INT_TBL_SME,  blnIva );
                                        blnIva = new Boolean(false);
                                        strest = ((tblDat.getValueAt(i, INT_TBL_SNU).toString().equals("true"))?"S":"N");
                                        if(strest.equals("S")) blnIva = new Boolean(true);
                                      vecReg.add(INT_TBL_SNU, blnIva );
                                      vecReg.add(INT_TBL_NIV, tblDat.getValueAt(i, INT_TBL_NIV).toString() );
                                      vecReg.add(INT_TBL_VAL, ""+intval2 );
                                      vecReg.add(INT_TBL_PAD, tblDat.getValueAt(i, INT_TBL_PAD).toString() );
                                      vecReg.add(INT_TBL_TIP , tblDat.getValueAt(i, INT_TBL_TIP).toString() );
                                      vecReg.add(INT_TBL_EST , tblDat.getValueAt(i, INT_TBL_EST).toString() );
                                      vecReg.add(INT_TBL_LINEA , ""+intUbi );
                                      vecReg.add(INT_TBL_EMP , ((tblDat.getValueAt(i, INT_TBL_EMP)==null)?"":tblDat.getValueAt(i, INT_TBL_EMP).toString())  );
                                      
                                      
                                           
                                     intUbi++;    
                                      vecData.add(vecReg);         
                                     
                                      intTip=1;  
                                           
                                       
                                     //***********************************************************
                                       
                                              int intval=-1;  //tblDat.getRowCount(); 
                                                   
                                                ArrayList arlCam=new ArrayList();
                                                Vector vc = new Vector();
                                                int intz=0;
                                                
                                               for(int z=0; z<tblDat.getRowCount(); z++){
                                                    int intCodReg = Integer.parseInt(tblDat.getValueAt(z, INT_TBL_VAL).toString());
                                                    if(intz==0) intval = Integer.parseInt(tblDat.getValueAt(z, INT_TBL_VAL).toString());
                                                    else{
                                                        if(intCodReg>intval) intval = Integer.parseInt(tblDat.getValueAt(z, INT_TBL_VAL).toString());
                                                            
                                                    }intz=1;
                                               }
                                               intval++;
                                              
                                               // System.out.println("CODIGO REGISTRO ==> "+ intval ); 
                                               
                                               
                                     //***********************************************************   
                                        
                                    ///  System.out.println("> "+ tblDat.getValueAt(i, INT_TBL_NIV) );   
                                         
                                      vecReg = new java.util.Vector();  
                                      intNiv = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_NIV).toString());
                                      intNiv++;
                                      for(int x=0; x<intNiv; x++)
                                          stresp2+=stresp;
                                      
                                      vecReg.add(INT_TBL_LIN, "");
                                      vecReg.add(INT_TBL_BUT, "");
                                      vecReg.add(INT_TBL_ESP, ((tblDat.getValueAt(i, INT_TBL_ESP)==null)?"":tblDat.getValueAt(i, INT_TBL_ESP).toString())+stresp );
                                      vecReg.add(INT_TBL_NUM, obj1.GetCamSel(1) );
                                      
                                      vecReg.add(INT_TBL_NCT, obj1.GetCamSel(4) );
                                      
                                      vecReg.add(INT_TBL_DES,   stresp2 + obj1.GetCamSel(2).trim() );
                                       Boolean blnIva2 = new Boolean(true);                     
                                      vecReg.add(INT_TBL_SME,  blnIva2 );
                                               blnIva2 = new Boolean(false);  
                                      vecReg.add(INT_TBL_SNU, blnIva2 );
                                         
                                      vecReg.add(INT_TBL_NIV, ""+ intNiv );
                                      vecReg.add(INT_TBL_VAL, ""+intval );
                                        
                                      
                                      vecReg.add(INT_TBL_PAD, ""+intval2 );
                                      vecReg.add(INT_TBL_TIP , obj1.GetCamSel(3) );
                                      vecReg.add(INT_TBL_EST , "I");
                                      vecReg.add(INT_TBL_LINEA , ""+intUbi );
                                       vecReg.add(INT_TBL_EMP , obj1.GetCamSel(5) );
                                      vecData.add(vecReg);  
                                      intUbi++;
                                 //*********************************************************
                                        
                               }else{  
                                   
                                     //*******************************************************
                                     
                                      java.util.Vector vecReg = new java.util.Vector();
                                     
                                      vecReg.add(INT_TBL_LIN, "");
                                      vecReg.add(INT_TBL_BUT, "");
                                      
                                      vecReg.add(INT_TBL_ESP, ((tblDat.getValueAt(i, INT_TBL_ESP)==null)?"":tblDat.getValueAt(i, INT_TBL_ESP).toString()) );
                                      vecReg.add(INT_TBL_NUM, ((tblDat.getValueAt(i, INT_TBL_NUM)==null)?"":tblDat.getValueAt(i, INT_TBL_NUM).toString()) );
                                      vecReg.add(INT_TBL_NCT, ((tblDat.getValueAt(i, INT_TBL_NCT)==null)?"":tblDat.getValueAt(i, INT_TBL_NCT).toString()) );
                                    
                                      vecReg.add(INT_TBL_DES, ((tblDat.getValueAt(i, INT_TBL_DES)==null)?"":tblDat.getValueAt(i, INT_TBL_DES).toString()) );
                                      
                                        Boolean blnIva = new Boolean(false);                     
                                        String strest = ((tblDat.getValueAt(i, INT_TBL_SME).toString().equals("true"))?"S":"N");
                                        if(strest.equals("S")) blnIva = new Boolean(true);
                                      vecReg.add(INT_TBL_SME,  blnIva );
                                        blnIva = new Boolean(false);
                                       
                                        strest = ((tblDat.getValueAt(i, INT_TBL_SNU).toString().equals("true"))?"S":"N");
                                        if(strest.equals("S")) blnIva = new Boolean(true);
                                      vecReg.add(INT_TBL_SNU, blnIva );
                                      
                                      vecReg.add(INT_TBL_NIV, tblDat.getValueAt(i, INT_TBL_NIV).toString() );
                                      vecReg.add(INT_TBL_VAL, tblDat.getValueAt(i, INT_TBL_VAL).toString() );
                                      vecReg.add(INT_TBL_PAD, tblDat.getValueAt(i, INT_TBL_PAD).toString() );
                                      vecReg.add(INT_TBL_TIP , tblDat.getValueAt(i, INT_TBL_TIP).toString() );
                                      vecReg.add(INT_TBL_EST , tblDat.getValueAt(i, INT_TBL_EST).toString() );
                                      vecReg.add(INT_TBL_LINEA , ""+intUbi );
                                       vecReg.add(INT_TBL_EMP , ((tblDat.getValueAt(i, INT_TBL_EMP)==null)?"":tblDat.getValueAt(i, INT_TBL_EMP).toString())  );
                                      vecData.add(vecReg);   
                                      intUbi++;
                                    //*********************************************************
                               }
                             
                                   
                          }  
                          
                           
                           objTblMod.setData(vecData);
                           tblDat .setModel(objTblMod); 
                            blnCam=true;
                           
                     } 
                       obj1.hide();
                      // obj1=null;
                       blnRes=true;
                } catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }           
             return blnRes;      
       }
   
            
        
       private boolean llamada2(){
           boolean blnRes=false;
           try{
           Contabilidad.ZafCon47.ZafCon47_01 obj1 = new  Contabilidad.ZafCon47.ZafCon47_01( javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
           obj1.show();
           
           
                   if(obj1.acepta()){     
                          
                          String strCta=obj1.GetCamSel(1);
                          String strNumCta="";
                          for(int i=0; i<tblDat.getRowCount(); i++){
                              strNumCta= (tblDat.getValueAt(i, INT_TBL_NUM)==null)?"":tblDat.getValueAt(i, INT_TBL_NUM).toString().trim();   
                              if(!strNumCta.equals("")){
                                 if(strNumCta.equals(strCta)){
                                    System.out.println("CODIGO CUENTA REPETIDO.. "); 
                                    MensajeInf("Codigo de cuenta ya existe ");
                                    return false;
                                 }
                              }
                          }
                         
                            
                            
                          
                           strNumCta = (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_NUM)==null)?"":tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_NUM).toString().trim();  
                           if(!strNumCta.equals("")){
                                 MensajeInf("No es posible agregar nivel una cuenta de Mayor o de Detalle. ");
                                 return false;
                           }
                            
                          
                         
                         
                         String stresp="    ";
                         String stresp2=""; 
                         int intTip=0;       
                         int intNiv=0;
                         int intUbi=1;
                         Vector vecData = new Vector();
                         for(int i=0; i<tblDat.getRowCount(); i++){
                               
                                         
                               if(i == tblDat.getSelectedRow()){
                                   
                                   int intval2 = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_VAL).toString());
                                   
                                     
                                 //*******************************************************
                                      java.util.Vector vecReg = new java.util.Vector();
                                      vecReg.add(INT_TBL_LIN, "");
                                      vecReg.add(INT_TBL_BUT, "");
                                //System.out.println(" AQUI 1  "+ tblDat.getValueAt(i, INT_TBL_NUM) );
                                      vecReg.add(INT_TBL_ESP, ((tblDat.getValueAt(i, INT_TBL_ESP)==null)?"":tblDat.getValueAt(i, INT_TBL_ESP).toString()) );
                                      vecReg.add(INT_TBL_NUM, ((tblDat.getValueAt(i, INT_TBL_NUM)==null)?"":tblDat.getValueAt(i, INT_TBL_NUM).toString()) );
                                //System.out.println(" AQUI 2  ");     
                                      vecReg.add(INT_TBL_DES, tblDat.getValueAt( i, INT_TBL_DES ).toString() );
                                      Boolean blnIva = new Boolean(false);                     
                                        String strest = ((tblDat.getValueAt(i, INT_TBL_SME).toString().equals("true"))?"S":"N");
                                        if(strest.equals("S")) blnIva = new Boolean(true);
                                      vecReg.add(INT_TBL_SME,  blnIva );
                                        blnIva = new Boolean(false);
                                        strest = ((tblDat.getValueAt(i, INT_TBL_SNU).toString().equals("true"))?"S":"N");
                                        if(strest.equals("S")) blnIva = new Boolean(true);
                                      vecReg.add(INT_TBL_SNU, blnIva );
                                      vecReg.add(INT_TBL_NIV, tblDat.getValueAt(i, INT_TBL_NIV).toString() );
                                      vecReg.add(INT_TBL_VAL, ""+intval2 );
                                      vecReg.add(INT_TBL_PAD, tblDat.getValueAt(i, INT_TBL_PAD).toString() );
                                      vecReg.add(INT_TBL_TIP , tblDat.getValueAt(i, INT_TBL_TIP).toString() );
                                      vecReg.add(INT_TBL_EST , tblDat.getValueAt(i, INT_TBL_EST).toString() );
                                      vecReg.add(INT_TBL_LINEA , ""+intUbi );
                                     intUbi++;
                                      vecData.add(vecReg);         
                                     
                                      intTip=1;
                                           
                                         
                                     //***********************************************************
                                       
                                              int intval=-1;  //tblDat.getRowCount(); 
                                                   
                                                ArrayList arlCam=new ArrayList();
                                                Vector vc = new Vector();
                                                int intz=0;
                                                
                                               for(int z=0; z<tblDat.getRowCount(); z++){
                                                    int intCodReg = Integer.parseInt(tblDat.getValueAt(z, INT_TBL_VAL).toString());
                                                    if(intz==0) intval = Integer.parseInt(tblDat.getValueAt(z, INT_TBL_VAL).toString());
                                                    else{
                                                        if(intCodReg>intval) intval = Integer.parseInt(tblDat.getValueAt(z, INT_TBL_VAL).toString());
                                                            
                                                    }intz=1;
                                               }
                                               intval++;
                                              
                                               // System.out.println("CODIGO REGISTRO ==> "+ intval ); 
                                               
                                               
                                     //***********************************************************
                                        
                                    ///  System.out.println("> "+ tblDat.getValueAt(i, INT_TBL_NIV) );   
                                         
                                      vecReg = new java.util.Vector();  
                                      intNiv = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_NIV).toString());
                                      intNiv++;
                                      for(int x=0; x<intNiv; x++)
                                          stresp2+=stresp;
                                      
                                      vecReg.add(INT_TBL_LIN, "");
                                      vecReg.add(INT_TBL_BUT, "");
                                      vecReg.add(INT_TBL_ESP, ((tblDat.getValueAt(i, INT_TBL_ESP)==null)?"":tblDat.getValueAt(i, INT_TBL_ESP).toString())+stresp );
                                      vecReg.add(INT_TBL_NUM, obj1.GetCamSel(1) );
                                      vecReg.add(INT_TBL_DES,   stresp2 + obj1.GetCamSel(2).trim() );
                                       Boolean blnIva2 = new Boolean(true);                     
                                      vecReg.add(INT_TBL_SME,  blnIva2 );
                                               blnIva2 = new Boolean(false);  
                                      vecReg.add(INT_TBL_SNU, blnIva2 );
                                         
                                      vecReg.add(INT_TBL_NIV, ""+ intNiv );
                                      vecReg.add(INT_TBL_VAL, ""+intval );
                                        
                                      
                                      vecReg.add(INT_TBL_PAD, ""+intval2 );
                                      vecReg.add(INT_TBL_TIP , obj1.GetCamSel(3) );
                                      vecReg.add(INT_TBL_EST , "I");
                                      vecReg.add(INT_TBL_LINEA , ""+intUbi );
                                      vecData.add(vecReg);  
                                      intUbi++;
                                 //*********************************************************
                                        
                               }else{  
                                   
                                     //*******************************************************
                                     
                                      java.util.Vector vecReg = new java.util.Vector();
                                     
                                      vecReg.add(INT_TBL_LIN, "");
                                      vecReg.add(INT_TBL_BUT, "");
                                      
                                      vecReg.add(INT_TBL_ESP, ((tblDat.getValueAt(i, INT_TBL_ESP)==null)?"":tblDat.getValueAt(i, INT_TBL_ESP).toString()) );
                                      vecReg.add(INT_TBL_NUM, ((tblDat.getValueAt(i, INT_TBL_NUM)==null)?"":tblDat.getValueAt(i, INT_TBL_NUM).toString()) );
                                      vecReg.add(INT_TBL_DES, ((tblDat.getValueAt(i, INT_TBL_DES)==null)?"":tblDat.getValueAt(i, INT_TBL_DES).toString()) );
                                      
                                        Boolean blnIva = new Boolean(false);                     
                                        String strest = ((tblDat.getValueAt(i, INT_TBL_SME).toString().equals("true"))?"S":"N");
                                        if(strest.equals("S")) blnIva = new Boolean(true);
                                      vecReg.add(INT_TBL_SME,  blnIva );
                                        blnIva = new Boolean(false);
                                       
                                        strest = ((tblDat.getValueAt(i, INT_TBL_SNU).toString().equals("true"))?"S":"N");
                                        if(strest.equals("S")) blnIva = new Boolean(true);
                                      vecReg.add(INT_TBL_SNU, blnIva );
                                      
                                      vecReg.add(INT_TBL_NIV, tblDat.getValueAt(i, INT_TBL_NIV).toString() );
                                      vecReg.add(INT_TBL_VAL, tblDat.getValueAt(i, INT_TBL_VAL).toString() );
                                      vecReg.add(INT_TBL_PAD, tblDat.getValueAt(i, INT_TBL_PAD).toString() );
                                      vecReg.add(INT_TBL_TIP , tblDat.getValueAt(i, INT_TBL_TIP).toString() );
                                      vecReg.add(INT_TBL_EST , tblDat.getValueAt(i, INT_TBL_EST).toString() );
                                      vecReg.add(INT_TBL_LINEA , ""+intUbi );
                                      vecData.add(vecReg);   
                                      intUbi++;
                                    //*********************************************************
                               }
                             
                               
                          }
                          
                         
                           objTblMod.setData(vecData);
                           tblDat .setModel(objTblMod); 
                            blnCam=true;
                           
                     } 
                       obj1.dispose();
                       obj1=null;
                       blnRes=true;
                } catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }           
             return blnRes;      
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
        panFil = new javax.swing.JPanel();
        PanCab = new javax.swing.JPanel();
        PanOpt = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optDia = new javax.swing.JRadioButton();
        chkAgrCtaNo = new javax.swing.JCheckBox();
        chkAgrCtaEs = new javax.swing.JCheckBox();
        chkSalAntMes = new javax.swing.JCheckBox();
        lblItm = new javax.swing.JLabel();
        txtCod = new javax.swing.JTextField();
        lblItm1 = new javax.swing.JLabel();
        txtNom = new javax.swing.JTextField();
        PanDet = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        lblObs2 = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();

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
                formInternalFrameOpened(evt);
            }
        });

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("T\u00edtulo de la ventana");
        panFrm.add(lblTit);

        getContentPane().add(panFrm, java.awt.BorderLayout.NORTH);

        panFil.setLayout(new java.awt.BorderLayout());

        PanCab.setLayout(null);

        PanCab.setPreferredSize(new java.awt.Dimension(0, 180));
        PanOpt.setLayout(null);

        PanOpt.setBorder(new javax.swing.border.TitledBorder("Para obtener los saldos de las cuentas utilice:"));
        optTod.setSelected(true);
        optTod.setText("Todos los Diarios");
        bgrFil.add(optTod);
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });

        PanOpt.add(optTod);
        optTod.setBounds(20, 20, 380, 20);

        optDia.setText("Solo los diarios donde aparezcan una cuenta de efectivo");
        bgrFil.add(optDia);
        optDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optDiaActionPerformed(evt);
            }
        });

        PanOpt.add(optDia);
        optDia.setBounds(20, 40, 472, 20);

        chkAgrCtaNo.setText("Agregar cuentas \"No agrupadas\"");
        chkAgrCtaNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAgrCtaNoActionPerformed(evt);
            }
        });

        PanOpt.add(chkAgrCtaNo);
        chkAgrCtaNo.setBounds(55, 60, 300, 20);

        chkAgrCtaEs.setText("Agregar cuentas \"Especiales\" ");
        chkAgrCtaEs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAgrCtaEsActionPerformed(evt);
            }
        });

        PanOpt.add(chkAgrCtaEs);
        chkAgrCtaEs.setBounds(55, 80, 288, 20);

        chkSalAntMes.setText("Mostrar los saldos anteriores de cada mes.");
        chkSalAntMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSalAntMesActionPerformed(evt);
            }
        });

        PanOpt.add(chkSalAntMes);
        chkSalAntMes.setBounds(16, 100, 432, 20);

        PanCab.add(PanOpt);
        PanOpt.setBounds(12, 52, 624, 124);

        lblItm.setText("C\u00f3digo : ");
        lblItm.setToolTipText("Beneficiario");
        PanCab.add(lblItm);
        lblItm.setBounds(16, 8, 56, 20);

        txtCod.setBackground(objParSis.getColorCamposSistema());
        PanCab.add(txtCod);
        txtCod.setBounds(76, 8, 112, 20);

        lblItm1.setText("Nombre :");
        lblItm1.setToolTipText("Beneficiario");
        PanCab.add(lblItm1);
        lblItm1.setBounds(16, 28, 60, 20);

        txtNom.setBackground( objParSis.getColorCamposObligatorios() );
        txtNom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomKeyPressed(evt);
            }
        });

        PanCab.add(txtNom);
        txtNom.setBounds(76, 28, 452, 20);

        panFil.add(PanCab, java.awt.BorderLayout.NORTH);

        PanDet.setLayout(new java.awt.BorderLayout());

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

        PanDet.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panFil.add(PanDet, java.awt.BorderLayout.CENTER);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(78, 35));
        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs2.setText("Observaci\u00f3n 1:");
        jPanel4.add(lblObs2, java.awt.BorderLayout.WEST);

        txaObs1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txaObs1KeyPressed(evt);
            }
        });

        spnObs1.setViewportView(txaObs1);

        jPanel4.add(spnObs1, java.awt.BorderLayout.CENTER);

        panFil.add(jPanel4, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panFil);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void txaObs1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txaObs1KeyPressed
        // TODO add your handling code here:
        blnCam=true;    
    }//GEN-LAST:event_txaObs1KeyPressed

    private void chkSalAntMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSalAntMesActionPerformed
        // TODO add your handling code here:
        blnCam=true;    
          
    }//GEN-LAST:event_chkSalAntMesActionPerformed

    private void optDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optDiaActionPerformed
        // TODO add your handling code here:
        blnCam=true;    
    }//GEN-LAST:event_optDiaActionPerformed

    private void txtNomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomKeyPressed
        // TODO add your handling code here:
        
         blnCam=true;
         
    }//GEN-LAST:event_txtNomKeyPressed

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:
         chkAgrCtaNo.setSelected(false);  
         chkAgrCtaEs.setSelected(false);
        blnCam=true;    
    }//GEN-LAST:event_optTodActionPerformed

    private void chkAgrCtaEsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAgrCtaEsActionPerformed
        // TODO add your handling code here:
         if(!optDia.isSelected()){
            chkAgrCtaNo.setSelected(false);  
            chkAgrCtaEs.setSelected(false);
            blnCam=true;    
        }
    }//GEN-LAST:event_chkAgrCtaEsActionPerformed

    private void chkAgrCtaNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAgrCtaNoActionPerformed
        // TODO add your handling code here:
        if(!optDia.isSelected()){
            chkAgrCtaNo.setSelected(false);
            chkAgrCtaEs.setSelected(false);
            blnCam=true;    
        }
    }//GEN-LAST:event_chkAgrCtaNoActionPerformed

       
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
         if (!configurarFrm())
            exitForm();
    }//GEN-LAST:event_formInternalFrameOpened

      
    
  
   
    
    
    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
          
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanCab;
    private javax.swing.JPanel PanDet;
    private javax.swing.JPanel PanOpt;
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JCheckBox chkAgrCtaEs;
    private javax.swing.JCheckBox chkAgrCtaNo;
    private javax.swing.JCheckBox chkSalAntMes;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblItm1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optDia;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtNom;
    // End of variables declaration//GEN-END:variables
  
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + VERSION );
            lblTit.setText(strAux);
            arlDat=new ArrayList();
            arlRegMod=new ArrayList();
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

  
   
    
      
    
    
    
    public class mitoolbar extends ZafToolBar{
        public mitoolbar(javax.swing.JInternalFrame jfrThis){
            super(jfrThis, objParSis);
        }
        
        
       public boolean anular()
        {
             strAux=objTooBar.getEstadoRegistro(); 
            if (strAux.equals("Eliminado"))
            {
                MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                MensajeInf("El documento está ANULADO.\nNo es posible volver anular un documento anulado.");
                return false;
            }
           
             
              if(anularReg()){
                    objTooBar.setEstadoRegistro("Anulado");
                    return true;
              }
              else  return false;
        }

       
       
       
       
        
       
      private boolean anularReg(){
            boolean blnres=false;
            Statement stm;
            String sql="";
             
            try{
               
                java.sql.Connection conTipEmp=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if (conTipEmp!=null){
                    conTipEmp.setAutoCommit(false);
                
                    stm=conTipEmp.createStatement();
               
                       sql = "UPDATE  tbm_cabestfinper SET  st_reg='I'  WHERE  " +
                       " co_emp="+objParSis.getCodigoEmpresa()+" and  co_estfin="+txtCod.getText();
                       stm.executeUpdate(sql);
                       conTipEmp.commit();
                      stm.close();
                      stm=null;
                      conTipEmp.close();
                      conTipEmp=null;
                   blnres=true;
                   
                }
            }catch(SQLException Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); }
             catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }    
            
            return blnres;
        }
        
       
      
      
      
             
      private boolean eliminarReg(){
            boolean blnres=false;
            Statement stm;
            String sql="";
           
             
            try{
               
                java.sql.Connection conTipEmp=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if (conTipEmp!=null){
                    conTipEmp.setAutoCommit(false);
                
                    stm=conTipEmp.createStatement();
               
                       sql = "UPDATE  tbm_cabestfinper SET  st_reg='E'  WHERE  " +
                       " co_emp="+objParSis.getCodigoEmpresa()+" and  co_estfin="+txtCod.getText();
                       stm.executeUpdate(sql);
                       conTipEmp.commit();
                      stm.close();
                      stm=null;
                      conTipEmp.close();
                      conTipEmp=null;
                   blnres=true;
                   
                }
            }catch(SQLException Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); }
             catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }    
            
            return blnres;
        }
        
       
      
      
      
       
        public void clickAceptar()
        {
            
        }

        public void clickAnterior() 
        {
          try
            {
                if (!rstCab.isFirst())
                {
                    if (blnCam)
                    {
                        //if (isRegPro())
                       // {
                            rstCab.previous();
                            cargarReg();
                      //  }
                    }
                    else
                    {
                        rstCab.previous();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            
        }

        public void clickAnular()
        {
           
        }  
   

        public void clickConsultar() 
        {
           
        }

        public void clickEliminar()
        {
           
        }

        public void clickFin() 
        {
           try
            {
                if (!rstCab.isLast())
                {
                    if (blnCam)
                    {
                      //  if (isRegPro())
                      //  {
                            rstCab.last();
                            cargarReg();
                       // }
                    }
                    else
                    {
                        rstCab.last();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
   
        public void clickInicio()
        {
              try
            {
                if (!rstCab.isFirst())
                {
                    if (blnCam)
                    {
                       // if (isRegPro())
                      //  {
                            rstCab.first();
                            cargarReg();
                       //}
                    }
                    else
                    {
                        rstCab.first();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
          
        }

        
        
        public void clickInsertar()
        {
             limpiarPant();
            noEditable(false);     
            setEditable(true);
               
             //*******************************
            
              String stresp="   "; 
            
              Vector vecData = new Vector();
                 java.util.Vector vecReg = new java.util.Vector();
                  vecReg.add(INT_TBL_LIN, "");
                  vecReg.add(INT_TBL_BUT, "");
                  vecReg.add(INT_TBL_ESP, stresp);
                  vecReg.add(INT_TBL_NUM, "");
                  vecReg.add(INT_TBL_NCT, "");
                  vecReg.add(INT_TBL_DES, "GRUPO_1");
                    Boolean blnIva = new Boolean(true);                     
                  vecReg.add(INT_TBL_SME, blnIva);
                            blnIva = new Boolean(false); 
                  vecReg.add(INT_TBL_SNU, blnIva); 
                  vecReg.add(INT_TBL_NIV, "1" );
                  vecReg.add(INT_TBL_VAL, "1");
                  vecReg.add(INT_TBL_PAD, "0");
                  vecReg.add(INT_TBL_TIP , "P" );
                  vecReg.add(INT_TBL_EST , "I" );
                  vecReg.add(INT_TBL_LINEA , "1" );
                  vecReg.add(INT_TBL_EMP, "");
                  
                  vecData.add(vecReg);         
              objTblMod.setData(vecData);
              tblDat .setModel(objTblMod);           
             //*******************************
              
        }
  
           
        
        public void  noEditable(boolean blnEditable){
            java.awt.Color colBack = txtCod.getBackground();
            txtCod.setEditable(blnEditable);
            txtCod.setBackground(colBack);
            
      }
  
        
        
        public void clickSiguiente()
        {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnCam)
                    {
                            rstCab.next();
                            cargarReg();
                    }
                    else
                    {
                        rstCab.next();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
 
        public boolean eliminar()
        {
            strAux=objTooBar.getEstadoRegistro();
             if (strAux.equals("Eliminado"))
            {
                MensajeInf("El documento está ELIMINADO.\n.");
                return false;
            }
         
            
            if(eliminarReg()){  
                    objTooBar.setEstadoRegistro("Eliminado");
                    return true;
              }
              else  return false;
        }

        public boolean insertar()
        {
            
            if(validarCampos()){
              if(insertarReg())
                 return true;
              else  return false;
            }else {
            
              return false;
            }
        }

        
        
        
        private boolean validarCampos(){
            if(txtNom.getText().trim().equals("")){
                MensajeInf("Ingrese  Nombre.");
                txtNom.requestFocus();
                return false;        
            }
       
           
      return true;  
    }
    
      
        
        
       private void MensajeInf(String strMensaje){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit;
            strTit="Mensaje del sistema Zafiro";
            obj.showMessageDialog(jfrThis,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
       }
    
    
          
          
        
        private boolean insertarReg(){
            boolean blnRes=false;
            int x=0;
            String strval="";
            
            
            Connection conTipEmp;
            Statement stmTipEmp;
            ResultSet rstEmp;
            String sSql;
        
           try{
             conTipEmp=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if (conTipEmp!=null){
                    conTipEmp.setAutoCommit(false);
                    
                     if(insertarReg(conTipEmp)){
                         conTipEmp.commit();
                         blnRes=true;
                     }
                    
                     
                 }
          
                conTipEmp.close();
                conTipEmp=null;
                
            }catch(SQLException Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); }
             catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }    
            
            
            return blnRes;
        }
        
        
        
          
        
        private boolean insertarReg(java.sql.Connection conn){
            boolean blnres=false;
            Statement stm, stmReg;
            ResultSet rst;
            String sql="";
            int intCodfin=1;
            String strSalcta="";
            String stragrctasinagr="N";
            String stragrctaesp="N";
            String strmossalantmes="N";
             
            try{
                if (conn!=null){
                   stm=conn.createStatement();
                   stmReg=conn.createStatement();
                   
                   sql="SELECT case when (max(co_estfin)+1)is null  then 1 else (max(co_estfin)+1) end as co_estfin  FROM tbm_cabestfinper where co_emp="+objParSis.getCodigoEmpresa();
                   rst=stm.executeQuery(sql);
                   if(rst.next()) intCodfin=rst.getInt(1);
                   
                   if(optTod.isSelected())
                      strSalcta="T";
                   if(optDia.isSelected())
                      strSalcta="E";
                   
                   
                   if(chkAgrCtaNo.isSelected())
                      stragrctasinagr="S";
                      
                   if(chkAgrCtaEs.isSelected())
                      stragrctaesp="S";
                   
                   if(chkSalAntMes.isSelected())
                     strmossalantmes="S";
                     
                   
                   sql = "INSERT INTO tbm_cabestfinper( " +
                   "      co_emp, co_estfin, tx_nom, tx_salctauti, st_agrctasinagr, st_agrctaesp,  " +
                   "      st_mossalantmes, tx_obs1, st_reg,  co_usring,  co_usrmod, fe_ing, fe_ultmod ) " +
                   "  VALUES ("+objParSis.getCodigoEmpresa()+","+intCodfin+",'"+txtNom.getText()+"','"+strSalcta+"','"+stragrctasinagr+"','"+stragrctaesp+"','"+strmossalantmes+"' " +
                   ",'"+txaObs1.getText()+"','A',"+objParSis.getCodigoUsuario()+","+objParSis.getCodigoUsuario()+", CURRENT_TIMESTAMP, CURRENT_TIMESTAMP )";
                   
                   stm.executeUpdate(sql);
            
                    
                    
                  String strSalMA="NULL"; 
                  for(int i=0; i<tblDat.getRowCount(); i++){
                          
                      
                       if(tblDat.getValueAt( i, INT_TBL_SNU)!=null){
                            if(tblDat.getValueAt( i,  INT_TBL_SNU).toString().equalsIgnoreCase("true")) {
                                strSalMA="A";
                            }
                      }
                      
                       if(tblDat.getValueAt( i, INT_TBL_SME)!=null){
                            if(tblDat.getValueAt( i,  INT_TBL_SME).toString().equalsIgnoreCase("true")) {
                                strSalMA="M";
                            }
                      }
                         
                        
                            
                        
                          sql = " INSERT INTO tbm_detestfinper(co_emp, co_estfin, co_reg, ne_ubi, tx_tip, ne_pad, ne_niv, co_cta, tx_nom, tx_salctauti, co_empcfggrp) " +
                           "  VALUES ("+objParSis.getCodigoEmpresa()+","+intCodfin+","+tblDat.getValueAt(i, INT_TBL_VAL).toString()+","+(i+1)+",'"+tblDat.getValueAt(i, INT_TBL_TIP).toString()+"' " +  
                           ","+tblDat.getValueAt(i, INT_TBL_PAD).toString()+", "+tblDat.getValueAt(i, INT_TBL_NIV).toString()+" ,"+(((tblDat.getValueAt(i, INT_TBL_NUM)==null || tblDat.getValueAt(i, INT_TBL_NUM).toString().equals("")))?null:tblDat.getValueAt(i, INT_TBL_NUM).toString()) +" " +
                           "  ,'"+tblDat.getValueAt(i, INT_TBL_DES).toString().trim()+"','"+strSalMA+"', "+((tblDat.getValueAt(i, INT_TBL_EMP)==null ||  tblDat.getValueAt(i, INT_TBL_EMP).toString().equals("") )?null:tblDat.getValueAt(i, INT_TBL_EMP).toString())+")"; 
                         // System.out.println(">>  "+ sql );  
                           stm.executeUpdate(sql);
                           
                           if(objParSis.getCodigoEmpresaGrupo()==objParSis.getCodigoEmpresa()){
                              sql="select co_emp, co_cta, tx_deslar from  tbm_placta where  " +
                            " tx_codcta='"+ ((tblDat.getValueAt(i, INT_TBL_NCT)==null || tblDat.getValueAt(i, INT_TBL_NCT).toString().equals(""))?"":tblDat.getValueAt(i, INT_TBL_NCT).toString().trim())+"' " +
                            " and  upper(tx_deslar) LIKE '%"+tblDat.getValueAt(i, INT_TBL_DES).toString().trim()+"%'";
                            rst=stm.executeQuery(sql);    
                            int intSecc=1;
                            while(rst.next()){
                                 sql="INSERT INTO tbm_infflugrp(co_emp, co_estfin, co_reg, co_sec, co_emprea, co_cta, tx_codcta, tx_nom )" +
                                 " VALUES ("+objParSis.getCodigoEmpresa()+","+intCodfin+","+tblDat.getValueAt(i, INT_TBL_VAL).toString()+","+intSecc+","+rst.getString("co_emp")+","+rst.getString("co_cta")+",'"+
                                 ((tblDat.getValueAt(i, INT_TBL_NCT)==null || tblDat.getValueAt(i, INT_TBL_NCT).toString().equals(""))?"":tblDat.getValueAt(i, INT_TBL_NCT).toString().trim()) 
                                 +"','"+rst.getString("tx_deslar")+"' )";  
                                 System.out.println(" > "+ sql); 
                                 stmReg.executeUpdate(sql);   
                                 intSecc++;   
                            }
                           }  
                           
                           
                  }        
                  
                   for(int i=0; i<tblDat.getRowCount(); i++){
                       tblDat.setValueAt( "M", i, INT_TBL_EST );   
                   }
                   txtCod.setText(""+intCodfin);    
                   blnres=true;
                   
                }
            }catch(SQLException Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); }
             catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }    
            
            return blnres;
        }
        
        
        
        
        
        /**Procedimiento que limpia todas las cajas de texto que existen en el frame*/            
    public void limpiarPant()
   { 
          txtCod.setText("");
          txtNom.setText("");
          txaObs1.setText("");
          //Detalle        
           objTblMod.removeAllRows();     
          
   }
        
        
        
        public boolean modificar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                MensajeInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                MensajeInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
           
           
            if( blnCam ){
            
            if(validarCampos()){
              if(ModificarReg())
                 return true;
              else  return false;
            }else {
            
                  
                
              return false;
            }
          }else {
                MensajeInf("No se ha realizado ningùn cambio que se pueda guardar.");
                return false;  
           }  
            
        }
            
        
        
        
        
        private boolean ModificarReg(){
            boolean blnRes=false;
            int x=0;
            String strval="";
            
            
            Connection conTipEmp;
            Statement stmTipEmp;
            ResultSet rstEmp;
            String sSql;
        
           try{
             conTipEmp=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if (conTipEmp!=null){
                    conTipEmp.setAutoCommit(false);
                    
                     if(modifcaReg(conTipEmp)){
                         conTipEmp.commit();
                         blnRes=true;
                         blnCam=false;
                     }
                    
                    
                 }
          
                conTipEmp.close();
                conTipEmp=null;
                
            }catch(SQLException Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); }
             catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }    
            
            return blnRes;
        }
        
        
         
        
            
          
             
        private boolean modifcaReg(java.sql.Connection conn){
            boolean blnres=false;
            Statement stm, stmReg;
            ResultSet rst;
            String sql="";
            int intCodfin=1;
            String strSalcta="";
            String stragrctasinagr="N";
            String stragrctaesp="N";
            String strmossalantmes="N";
             
            try{
                if (conn!=null){
                   stm=conn.createStatement();
                   stmReg=conn.createStatement();
                 
                   
                   if(optTod.isSelected())
                      strSalcta="T";
                   if(optDia.isSelected())
                      strSalcta="E";
                   
                     
                   if(chkAgrCtaNo.isSelected())
                      stragrctasinagr="S";
                      
                   if(chkAgrCtaEs.isSelected())
                      stragrctaesp="S";
                   
                   if(chkSalAntMes.isSelected())
                     strmossalantmes="S";
                   
                          
                           
                   
                    for (int i=0; i<arlRegMod.size();i++)
                    {
                        
                         //*******************************************************************************
                            if(objParSis.getCodigoEmpresaGrupo()==objParSis.getCodigoEmpresa()){        
                                sql = " DELETE FROM tbm_infflugrp WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_estfin="+txtCod.getText()+" AND co_reg="+arlRegMod.get(i).toString()+" ";
                                // System.out.println(" > "+ sql); 
                                stmReg.executeUpdate(sql);   
                            }   
                         //*******************************************************************************
                         
                        
                        System.out.println("Datos Modificaco : "+ arlRegMod.get(i).toString() );
                        sql = " DELETE FROM tbm_detcfgestfinper WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_estfin="+txtCod.getText()+" AND co_reg="+arlRegMod.get(i).toString()+" ";
                        stm.executeUpdate(sql);
                    }
                    arlRegMod.clear();  
                       
                   
                                               
                     
                    for (int i=0; i<arlDat.size();i++)
                    {
                           System.out.println("Datos Eliminados : "+ arlDat.get(i).toString() );
                         
                           //*******************************************************************************
                            if(objParSis.getCodigoEmpresaGrupo()==objParSis.getCodigoEmpresa()){        
                                sql = " DELETE FROM tbm_infflugrp WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_estfin="+txtCod.getText()+" AND co_reg="+arlDat.get(i).toString()+" ";
                                // System.out.println(" > "+ sql); 
                                stmReg.executeUpdate(sql);   
                            }   
                          //*******************************************************************************
                         
                           
                            sql = " DELETE FROM tbm_detcfgestfinper WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_estfin="+txtCod.getText()+" AND co_reg="+arlDat.get(i).toString()+" ";
                            stm.executeUpdate(sql);
                            
                            sql="DELETE FROM tbm_detestfinper WHERE co_emp="+objParSis.getCodigoEmpresa()+" and  co_estfin="+txtCod.getText()+" AND co_reg="+arlDat.get(i).toString()+" ";
                            stm.executeUpdate(sql);
                    }
                    arlDat.clear();
                    
                    
                        
                   sql = "UPDATE  tbm_cabestfinper SET fe_ultmod=CURRENT_TIMESTAMP, tx_nom='"+txtNom.getText()+"',tx_salctauti='"+strSalcta+"', st_agrctasinagr='"+stragrctasinagr+"', " +
                   " st_agrctaesp='"+stragrctaesp+"',st_mossalantmes='"+strmossalantmes+"',tx_obs1='"+txaObs1.getText()+"',co_usrmod="+objParSis.getCodigoUsuario()+"  WHERE  " +
                   " co_emp="+objParSis.getCodigoEmpresa()+" and  co_estfin="+txtCod.getText();
                   // System.out.println("( < )"+ sql);
                   stm.executeUpdate(sql);
                          
                  // sql="DELETE FROM tbm_detestfinper WHERE co_emp="+objParSis.getCodigoEmpresa()+" and  co_estfin="+txtCod.getText();
                 //  stm.executeUpdate(sql);
                        
                      
                  String strSalMA="NULL"; 
                  for(int i=0; i<tblDat.getRowCount(); i++){
                                     
                      
                      
                      
                       if(tblDat.getValueAt( i, INT_TBL_SNU)!=null){
                            if(tblDat.getValueAt( i,  INT_TBL_SNU).toString().equalsIgnoreCase("true")) {
                                strSalMA="A";
                            }
                      }
                           
                       
                       if(tblDat.getValueAt( i, INT_TBL_SME)!=null){
                            if(tblDat.getValueAt( i,  INT_TBL_SME).toString().equalsIgnoreCase("true")) {
                                strSalMA="M";
                            }
                       }
                           
                          
                           
                                 
                       if(tblDat.getValueAt( i,  INT_TBL_EST).toString().equalsIgnoreCase("I")) {
                            sql = " INSERT INTO tbm_detestfinper(co_emp, co_estfin, co_reg, ne_ubi, tx_tip, ne_pad, ne_niv, co_cta, tx_nom, tx_salctauti, co_empcfggrp) " +
                            "  VALUES ("+objParSis.getCodigoEmpresa()+","+txtCod.getText()+","+tblDat.getValueAt(i, INT_TBL_VAL).toString()+","+(i+1)+",'"+tblDat.getValueAt(i, INT_TBL_TIP).toString()+"' " +  
                            ","+tblDat.getValueAt(i, INT_TBL_PAD).toString()+", "+tblDat.getValueAt(i, INT_TBL_NIV).toString()+" ,"+(((tblDat.getValueAt(i, INT_TBL_NUM)==null || tblDat.getValueAt(i, INT_TBL_NUM).toString().equals("")))?null:tblDat.getValueAt(i, INT_TBL_NUM).toString()) +" " +
                            "  ,'"+tblDat.getValueAt(i, INT_TBL_DES).toString().trim()+"','"+strSalMA+"', "+ ((tblDat.getValueAt(i, INT_TBL_EMP)==null ||  tblDat.getValueAt(i, INT_TBL_EMP).toString().equals("") )?null:tblDat.getValueAt(i, INT_TBL_EMP).toString())+")";
                            System.out.println("( < )"+ sql); 
                            stm.executeUpdate(sql);
                           
                           if(objParSis.getCodigoEmpresaGrupo()==objParSis.getCodigoEmpresa()){ 
                            sql="select co_emp, co_cta, tx_deslar from  tbm_placta where  " +
                            " tx_codcta='"+ ((tblDat.getValueAt(i, INT_TBL_NCT)==null || tblDat.getValueAt(i, INT_TBL_NCT).toString().equals(""))?"":tblDat.getValueAt(i, INT_TBL_NCT).toString().trim())+"' " +
                            " and  upper(tx_deslar) LIKE '%"+tblDat.getValueAt(i, INT_TBL_DES).toString().trim()+"%'";
                            rst=stm.executeQuery(sql);
                            int intSecc=1;
                            while(rst.next()){
                                 sql="INSERT INTO tbm_infflugrp(co_emp, co_estfin, co_reg, co_sec, co_emprea, co_cta, tx_codcta, tx_nom )" +
                                " VALUES ("+objParSis.getCodigoEmpresa()+","+txtCod.getText()+","+tblDat.getValueAt(i, INT_TBL_VAL).toString()+","+intSecc+","+rst.getString("co_emp")+","+rst.getString("co_cta")+",'"+
                               ((tblDat.getValueAt(i, INT_TBL_NCT)==null || tblDat.getValueAt(i, INT_TBL_NCT).toString().equals(""))?"":tblDat.getValueAt(i, INT_TBL_NCT).toString().trim()) 
                                +"','"+rst.getString("tx_deslar")+"' )";  
                               // System.out.println(" > "+ sql);    
                                stmReg.executeUpdate(sql);   
                                intSecc++;
                            }}
                       }     
                              
                       
                       if(tblDat.getValueAt( i,  INT_TBL_EST).toString().equalsIgnoreCase("M")) {    
                            sql = " UPDATE tbm_detestfinper  SET  ne_ubi="+tblDat.getValueAt(i, INT_TBL_LINEA).toString()+", tx_tip='"+tblDat.getValueAt(i, INT_TBL_TIP).toString()+"'," +
                            "  ne_pad="+tblDat.getValueAt(i, INT_TBL_PAD).toString()+" , ne_niv="+tblDat.getValueAt(i, INT_TBL_NIV).toString()+", " +
                            "  co_cta="+(((tblDat.getValueAt(i, INT_TBL_NUM)==null || tblDat.getValueAt(i, INT_TBL_NUM).toString().equals("")))?null:tblDat.getValueAt(i, INT_TBL_NUM).toString()) +", " +
                            "  tx_nom='"+tblDat.getValueAt(i, INT_TBL_DES).toString().trim()+"', tx_salctauti='"+strSalMA+"' " +
                            " , co_empcfggrp = "+((tblDat.getValueAt(i, INT_TBL_EMP)==null ||  tblDat.getValueAt(i, INT_TBL_EMP).toString().equals("") )?null:tblDat.getValueAt(i, INT_TBL_EMP).toString())+" "+ 
                            "  WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_estfin="+txtCod.getText()+" AND co_reg="+tblDat.getValueAt(i, INT_TBL_VAL).toString()+" ";
                            //System.out.println("( < )"+ sql); 
                            stm.executeUpdate(sql);
                                
                            
                            
                           if(objParSis.getCodigoEmpresaGrupo()==objParSis.getCodigoEmpresa()){
                               
                               //*******************************************************************************
                                sql = " DELETE FROM tbm_infflugrp WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_estfin="+txtCod.getText()+" AND co_reg="+tblDat.getValueAt(i, INT_TBL_VAL).toString()+" ";
                                // System.out.println(" > "+ sql); 
                                stmReg.executeUpdate(sql);   
                             //*******************************************************************************
                         
                                    
                                
                            sql="select co_emp, co_cta, tx_deslar from  tbm_placta where  " +
                            " tx_codcta='"+ ((tblDat.getValueAt(i, INT_TBL_NCT)==null || tblDat.getValueAt(i, INT_TBL_NCT).toString().equals(""))?"":tblDat.getValueAt(i, INT_TBL_NCT).toString().trim())+"' " +
                            " and  upper(tx_deslar) LIKE '%"+tblDat.getValueAt(i, INT_TBL_DES).toString().trim()+"%'";
                            rst=stm.executeQuery(sql);    
                            int intSecc=1;    
                            while(rst.next()){  
                                sql="INSERT INTO tbm_infflugrp(co_emp, co_estfin, co_reg, co_sec, co_emprea, co_cta, tx_codcta, tx_nom )" +
                                " VALUES ("+objParSis.getCodigoEmpresa()+","+txtCod.getText()+","+tblDat.getValueAt(i, INT_TBL_VAL).toString()+","+intSecc+","+rst.getString("co_emp")+","+rst.getString("co_cta")+",'"+
                               ((tblDat.getValueAt(i, INT_TBL_NCT)==null || tblDat.getValueAt(i, INT_TBL_NCT).toString().equals(""))?"":tblDat.getValueAt(i, INT_TBL_NCT).toString().trim()) 
                                +"','"+rst.getString("tx_deslar")+"' )";  
                              //  System.out.println(" > "+ sql); 
                                stmReg.executeUpdate(sql);   
                                intSecc++;
                            }
                           }        
                              
                       }    
                       
                         tblDat.setValueAt( "M", i, INT_TBL_EST );    
                       
                  }                   
                  
                   blnres=true;
                   
                }
            }catch(SQLException Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); }
             catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }    
            
            return blnres;
        }
        
        
        
        
        
        
           
        
        public boolean cancelar()
        {
            boolean blnRes=true;
            limpiarPant();
            return blnRes;
        }       
        
        
        
        
        
        public boolean aceptar() {
            return true;
        }
        
        public boolean afterAceptar() {
            return true;
        }
        
        public boolean afterAnular() {
            return true;
        }
        
        public boolean afterCancelar() {
            return true;
        }
         
        public boolean afterConsultar() {
            return true;
        }
        
        public boolean afterEliminar() {
            return true;
        }
        
        public boolean afterImprimir() {
            return true;
        }
        
        public boolean afterInsertar() {
            this.setEstado('w');
            return true;
        }
        
        public boolean afterModificar() {
            return true;
        }
        
        public boolean afterVistaPreliminar() {
            return true;
        }
              

        public boolean consultar() {
                /*
                 * Esto Hace en caso de que el modo de operacion sea Consulta
                 */
            return _consultar(FilSql());
        }
            
        
            
        
         private String FilSql() {               
                String sqlFiltro = "";

                //Agregando filtro por codigo de empresa
                 strAux = txtCod.getText() ;
                if(!txtCod.getText().equals(""))
                    sqlFiltro = sqlFiltro +  "AND  co_estfin = " + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "";
  
               //Agregando filtro por nombre
                 strAux = txtNom.getText();
                 if(!txtNom.getText().equals(""))
                    sqlFiltro = sqlFiltro + " AND LOWER( tx_nom) LIKE '" + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";
                      
               
                return sqlFiltro ;  
       }         
         
        
             
           
           
     private boolean _consultar(String strFiltro){
       strFlt = strFiltro;  
       Connection conCab;
       Statement stmCab;
       
        try{
                conCab= DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos()); 		
                if (conCab!=null){
                    stmCab=conCab.createStatement();
                     
                    String strSql = "SELECT * FROM  tbm_Cabestfinper ";
                    strSql = strSql + " WHERE co_emp = " + objParSis.getCodigoEmpresa() +" AND st_reg not in('E') ";
                    strSql = strSql + strFlt + " ORDER BY co_estfin";

                    rstCab=stmCab.executeQuery(strSql);
                    if(rstCab.next()){
                        rstCab.last();
                        objTooBar.setMenSis(rstCab.getRow() + " Registros encontrados");
                        cargarReg();
                    }
                    else{
                        objTooBar.setMenSis("Se encontraron 0 registros...");
                        MensajeInf("No se ha encontrado ningùn registro que cumpla el criterio de bùsqueda especìficado.");
                        
                        rstCab = null;
                        stmCab.close();
                        conCab.close();
                        stmCab = null;
                        conCab = null;
                        limpiarPant();
                        return false;
                    }
                }
              }
           catch(SQLException Evt)  {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;   }
            catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(jfrThis, Evt);    return false;   }                       
       return true;     
     }
      
       
          
          
         
       
     
     
    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            if (!cargarCabReg())
            {
                MensajeInf("Error al cargar registro");
                blnCam=false;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }        
    
       
    
    
      
      
    private boolean cargarCabReg()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {   // cboTipPer
            java.sql.Connection con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                java.sql.Statement stm=con.createStatement();
                String strSQL = " SELECT * FROM tbm_Cabestfinper " +
                                " Where  co_emp=" + rstCab.getString("co_emp") +
                                " AND co_estfin="+rstCab.getString("co_estfin")+" AND st_reg not in('E')  ";
           
                java.sql.ResultSet rst=stm.executeQuery(strSQL);
                java.sql.ResultSet rstAux;
                if (rst.next())  
                {
                        
                   // STR_REGREP=rst.getString("st_regrep");
                    txtCod.setText(((rst.getString("co_estfin")==null)?"":rst.getString("co_estfin")));
                    txtNom.setText(((rst.getString("tx_nom")==null)?"":rst.getString("tx_nom")));
                    txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                    
                      
                    if(rst.getString("tx_salctauti").equals("T")){
                        optTod.setSelected(true);
                        optDia.setSelected(false);
                    }else{
                        optDia.setSelected(true);
                        optTod.setSelected(false);
                    }
                    
                     chkAgrCtaNo.setSelected(false);
                     if(rst.getString("st_agrctasinagr").equals("S"))
                        chkAgrCtaNo.setSelected(true);
                        
                     chkAgrCtaEs.setSelected(false);
                     if(rst.getString("st_agrctaesp").equals("S"))
                        chkAgrCtaEs.setSelected(true); 
                    
                        chkSalAntMes.setSelected(false);
                     if(rst.getString("st_mossalantmes").equals("S"))
                        chkSalAntMes.setSelected(true); 
                          
                          
                        
                         
                ///***************************************************************************************
                     //String sql="select * from tbm_detestfinper where co_emp="+rstCab.getString("co_emp")+" and co_estfin="+rstCab.getString("co_estfin")+" ORDER BY  ne_ubi ";
                     String sql="";
                    if(objParSis.getCodigoEmpresaGrupo()==objParSis.getCodigoEmpresa()){
                       sql="SELECT  a.co_emp, a.co_estfin, a.co_reg, a.ne_ubi, a.tx_tip, a.ne_pad, a.ne_niv, a.co_cta, a.tx_nom, a.tx_salctauti,b.tx_codcta " +
                     " ,a.co_empCfgGrp  FROM tbm_detestfinper AS a " +
                     " left join tbm_placta as b on(b.co_emp=a.co_empCfgGrp and b.co_cta=a.co_cta) " +
                     " WHERE a.co_emp="+rstCab.getString("co_emp")+"  and a.co_estfin="+rstCab.getString("co_estfin")+"  ORDER BY  a.ne_ubi ";
                    }else{
                      sql="SELECT  a.co_emp, a.co_estfin, a.co_reg, a.ne_ubi, a.tx_tip, a.ne_pad, a.ne_niv, a.co_cta, a.tx_nom, a.tx_salctauti,b.tx_codcta " +
                     " ,a.co_empCfgGrp  FROM tbm_detestfinper AS a " +
                     " left join tbm_placta as b on(b.co_emp=a.co_emp and b.co_cta=a.co_cta) " +
                     " WHERE a.co_emp="+rstCab.getString("co_emp")+"  and a.co_estfin="+rstCab.getString("co_estfin")+"  ORDER BY  a.ne_ubi ";
                    }
                     
                            
                     
                     rstAux = stm.executeQuery(sql);
                     Vector vecData = new Vector(); 
                     int intNiv=0;
                     String stresp2="",stresp="    ";
                     while(rstAux.next()){
                         java.util.Vector vecReg = new java.util.Vector();
                  
                                      vecReg.add(INT_TBL_LIN, "");
                                      vecReg.add(INT_TBL_BUT, "");
                                      vecReg.add(INT_TBL_ESP, "" );
                                      vecReg.add(INT_TBL_NUM, rstAux.getString("co_cta") );
                                      
                                      vecReg.add(INT_TBL_NCT, rstAux.getString("tx_codcta") );
                                      
                                      intNiv = Integer.parseInt(rstAux.getString("ne_niv"));
                                      intNiv++;
                                      stresp2="";
                                      for(int x=1; x<intNiv; x++)
                                          stresp2+=stresp;
                                      
                                         
                                      vecReg.add(INT_TBL_DES, stresp2+rstAux.getString("tx_nom") );
                                         
                                      
                                       Boolean blnAnu = new Boolean(false);   
                                       Boolean blnMes = new Boolean(false);   
                                      
                                        if(rstAux.getString("tx_salctauti").equals("A"))
                                             blnAnu = new Boolean(true);
                                        if(rstAux.getString("tx_salctauti").equals("M"))
                                             blnMes = new Boolean(true);
                                         
                                      vecReg.add(INT_TBL_SME,  blnMes );
                                      vecReg.add(INT_TBL_SNU, blnAnu );
                                     
                                      vecReg.add(INT_TBL_NIV, rstAux.getString("ne_niv") ); 
                                      vecReg.add(INT_TBL_VAL,  rstAux.getString("co_reg") );
                                      vecReg.add(INT_TBL_PAD,  rstAux.getString("ne_pad") );
                                      vecReg.add(INT_TBL_TIP , rstAux.getString("tx_tip") );
                                      vecReg.add(INT_TBL_EST , "M");    
                                      vecReg.add(INT_TBL_LINEA , rstAux.getString("ne_ubi") );
                                      vecReg.add(INT_TBL_EMP , rstAux.getString("co_empCfgGrp") );
                                      
                                      
                                      
                           vecData.add(vecReg);  
                       }
                      objTblMod.setData(vecData);
                      tblDat.setModel(objTblMod);     
                  ///***************************************************************************************
                
                
                
                   //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                 
                    
            }else
            {
               objTooBar.setEstadoRegistro("Eliminado");
               limpiarPant();
            }    
               
                    
                 rst.close();
                 stm.close();
                 con.close();
                 rst=null;
                 stm=null;
                 con=null;
                
                //Mostrar la posición relativa del registro.
                intPosRel=rstCab.getRow();
                rstCab.last();
                objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
                rstCab.absolute(intPosRel);
                 blnCam=false;
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
          
     
         
         
         
                                                
        public void clickModificar(){
            noEditable(false);
            setEditable(true);
        }
      
         public boolean vistaPreliminar(){
                       
               return true;
         }
        
        
       public boolean imprimir(){
               return true;
       }
        
           
        public void clickImprimir(){
            }
        public void clickVisPreliminar(){
            }
  
        public void clickCancelar(){
        }
   
        public boolean beforeAceptar() {
            return true;
        }        
        public boolean beforeAnular() {
            return true;
        }        
        public boolean beforeCancelar() {
            return true;
        }        
        public boolean beforeConsultar() {
            return true;
        }        
        public boolean beforeEliminar() {
            return true;
        }        
        public boolean beforeImprimir() {
            return true;
        }        
        public boolean beforeInsertar() {
            return true;
        }        
        public boolean beforeModificar() {
            return true;
        }        
        public boolean beforeVistaPreliminar() {
            return true;
        }
        
   }   
    
      
    

    
}
