package RecursosHumanos.ZafRecHum38;

import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * Plantillas para el rol de pagos.
 * @author Roberto Flores
 * Guayaquil 18/07/2012
*/

public class ZafRecHum38 extends javax.swing.JInternalFrame {
  
  private java.sql.Connection CONN_GLO=null;
  private java.sql.Statement  STM_GLO=null;
  private java.sql.ResultSet  rstCab=null;
  
  private ZafTblCelRenLbl objTblCelRenLbl;
  private ZafTblCelEdiTxt objTblCelEdiTxt;                     //Editor: JTextField en celda.
  
  private Librerias.ZafParSis.ZafParSis objZafParSis;
  private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
  private ZafTblEdi objTblEdi;                                                        //Editor: Editor del JTable.
  private ZafTblFilCab objTblFilCab;
  
  private ZafTblCelRenBut objTblCelRenBut;                                           //Render: Presentar JButton en JTable.
  
  private ZafTblCelEdiButVco objTblCelEdiButVcoCta;                                   //Editor: JButton en celda.
  private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoCta;                                   //Editor: JTextField de consulta en celda.
  
  private ZafMouMotAda objMouMotAda;                                                 //ToolTipText en TableHeader.
  private ZafTblPopMnu objTblPopMnu;                                                 //PopupMenu: Establecer PeopuMenú en JTable.
  private java.util.Date datFecAux;
  
  private String strAux="";

  private Vector vecCab=new Vector();
  private Vector vecDat;

  private ZafUtil objUti;
  private mitoolbar objTooBar;
  
  private boolean blnHayCam=false;
 
  private static final int INT_TBL_DAT_LINEA=0;
  private static final int INT_TBL_DAT_COD_RUB=1;
  private static final int INT_TBL_DAT_NOM_RUB=2;
  private static final int INT_TBL_DAT_COD_CTA=3;
  private static final int INT_TBL_DAT_NUM_CTA=4;
  private static final int INT_TBL_DAT_BUT_CTA=5;
  private static final int INT_TBL_DAT_NOM_CTA=6;
  
  private Connection con, conCab;
  private Statement stm, stmCab;
  private ResultSet rst;//, rstCab;60
  
  private ZafVenCon vcoCta;                               //Ventana de consulta "Item".
  
  private Vector vecAux;
  
  boolean blnMsjFor= false;
  
  private int intCodPla = 0;
  
  private String strMe;
  
  //ESTA VARIABLE SE CREO PARA Q CONTENGA EL CODTIPDOC PARA EL NUEVO ESQUEMA DE CTAS POR USUARIO
  private int intCodTipDocGlb;
    
  private ZafVenCon objVenConCli; //*****************
    
  private String strVersion="v1.02";
  private String strCodPla, strDesCor, strDesLar;
   
   /** Creates new form revisionTecMer */
  public ZafRecHum38(Librerias.ZafParSis.ZafParSis obj) {
      try{
          this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
          initComponents();
          objUti = new ZafUtil();  
          
          objTooBar = new mitoolbar(this);
          this.getContentPane().add(objTooBar,"South");

          this.setTitle(objZafParSis.getNombreMenu()+" " + strVersion);
          lblTit.setText(objZafParSis.getNombreMenu()); 

      }catch(CloneNotSupportedException e) {objUti.mostrarMsgErr_F1(this, e);e.printStackTrace();}
  }
    
  public void setEditable(boolean editable) {
      if(editable==true) objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
      else objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
  }

  public void abrirCon(){
      try{
          CONN_GLO=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
      }catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
  }

  public void CerrarCon(){
      try{
          CONN_GLO.close();
          CONN_GLO=null;
      }catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
  }
  
//  private boolean configurarTblDat(){
//        
//        boolean blnRes=true;
//        try{
//        
//            //Configurar JTable: Establecer el modelo.
//            vecDat=new Vector();    //Almacena los datos
//            vecCab=new Vector();  //Almacena las cabeceras
//            vecCab.clear();
//            vecCab.add(INT_TBL_DAT_LINEA,"");
//            vecCab.add(INT_TBL_DAT_COD_RUB,"Cód. Rub.");
//            vecCab.add(INT_TBL_DAT_NOM_RUB,"Rubro");
//            vecCab.add(INT_TBL_DAT_COD_CTA,"Cód. Cta.");
//            vecCab.add(INT_TBL_DAT_NUM_CTA,"Núm. Cta.");
//            vecCab.add(INT_TBL_DAT_BUT_CTA,"");
//            vecCab.add(INT_TBL_DAT_NOM_CTA,"Nombre de la cuenta");
//            
//            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
//            objTblMod.setHeader(vecCab);
//            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
//            //objTblMod.setColumnDataType(INT_TBL_DAT_COD_RUB, objTblMod.INT_COL_DBL, new Integer(0), null);
//            //Configurar ZafTblMod: Establecer las columnas obligatorias.
//            java.util.ArrayList arlAux=new java.util.ArrayList();
//            arlAux.add("" + INT_TBL_DAT_COD_CTA);
//            arlAux.add("" + INT_TBL_DAT_COD_RUB);
//            objTblMod.setColumnasObligatorias(arlAux);
//            arlAux=null;
//            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
//            objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());
//            //Configurar JTable: Establecer el modelo de la tabla.
//            tblDat.setModel(objTblMod);
//            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
//            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
//            tblDat.getTableHeader().setReorderingAllowed(false);
//            //Configurar JTable: Establecer el menú de contexto.
//            objTblPopMnu=new ZafTblPopMnu(tblDat);
//            //Tamaño de las celdas
//            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
//            tcmAux.getColumn(INT_TBL_DAT_LINEA).setPreferredWidth(30);
//            tcmAux.getColumn(INT_TBL_DAT_COD_RUB).setPreferredWidth(50);
//            tcmAux.getColumn(INT_TBL_DAT_NOM_RUB).setPreferredWidth(230);
//            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setPreferredWidth(80);
//            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setPreferredWidth(100);
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setPreferredWidth(20);
//            tcmAux.getColumn(INT_TBL_DAT_NOM_CTA).setPreferredWidth(280);
//
//            //Configurar JTable: Ocultar columnas del sistema.
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_RUB, tblDat);
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CTA, tblDat);
//            
//            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
//            objMouMotAda=new ZafMouMotAda();
//            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
//
//            //Configurar JTable: Establecer columnas editables.
//            Vector vecAux=new Vector();
//            vecAux.add("" + INT_TBL_DAT_NUM_CTA);
//            vecAux.add("" + INT_TBL_DAT_BUT_CTA);
//            objTblMod.setColumnasEditables(vecAux);
//            vecAux=null;
//            
//            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
////            //Configurar JTable: Establecer la fila de cabecera.
////            objTblFilCab=new ZafTblFilCab(tblDat);
////            tcmAux.getColumn(INT_TBL_DAT_LINEA).setCellRenderer(objTblFilCab);
//            
//            
//            //Configurar JTable: Renderizar celdas.
//            objTblCelRenLbl=new ZafTblCelRenLbl();
//            tcmAux.getColumn(INT_TBL_DAT_NOM_RUB).setCellRenderer(objTblCelRenLbl);
//            objTblCelRenLbl=null;
//            
//            int intColVen[]=new int[3];
//            intColVen[0]=1;
//            intColVen[1]=2;
//            intColVen[2]=3;
//            int intColTbl[]=new int[3];
//            intColTbl[0]=INT_TBL_DAT_COD_CTA;
//            intColTbl[1]=INT_TBL_DAT_NUM_CTA;
//            intColTbl[2]=INT_TBL_DAT_NOM_CTA;
//            
//            objTblCelEdiTxtVcoCta=new ZafTblCelEdiTxtVco(tblDat, vcoCta, intColVen, intColTbl);
//            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setCellEditor(objTblCelEdiTxtVcoCta);
//            objTblCelEdiTxtVcoCta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//                String strEstCncDia="";//estado de conciliacion bancaria de la cuenta
//
//                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    strEstCncDia=tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_EST_CON)==null?"":tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_EST_CON).toString();
////                    if(strEstCncDia.equals("S")){
////                        mostrarMsgInf("<HTML>La cuenta ya fue conciliada.<BR>Desconcilie la cuenta en el documento a modificar y vuelva a intentarlo.</HTML>");
//////                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_EDI_CEL);
////                        objTblCelEdiTxtVcoCta.setCancelarEdicion(true);
////                    }
////                    else if(strEstCncDia.equals("B")){
////                        mostrarMsgInf("<HTML>No se puede cambiar el valor de la cuenta<BR>Este valor proviene de la transferencia ingresada.</HTML>");
////                        objTblCelEdiTxtVcoCta.setCancelarEdicion(true);
////                    }
////                    else{
////                        //Permitir de manera predeterminada la operaciï¿½n.
////                        blnCanOpe=false;
////                        //Generar evento "beforeEditarCelda()".
////                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_EDI_CEL);
////                        //Permitir/Cancelar la ediciï¿½n de acuerdo a "cancelarOperacion".
////                        if (blnCanOpe)
////                        {
////                            objTblCelEdiTxtVcoCta.setCancelarEdicion(true);
////                        }
////                    }
//                }
//                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    
////                    System.out.println();
////                    
////                    if(strEstCncDia.equals("B")){
////
////                    }
////                    else{
////                        //Generar evento "afterEditarCelda()".
////                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_EDI_CEL);
////                    }
//
//                }
//                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    
//                    
//                    System.out.println();
//                    
////                    intBanNumCta++;
//
//                    //Generar evento "beforeConsultarCuentas()".
////                    fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_CON_CTA);
//                    vcoCta.setCampoBusqueda(1);
//                    vcoCta.setCriterio1(7);
//                    if(objZafParSis.getCodigoUsuario()!=1)
//                    {
//                        strMe="";
//                        strMe+=" AND a2.co_tipdoc=" + intCodTipDocGlb + "";
//                        strMe+=" AND a2.co_usr=" + objZafParSis.getCodigoUsuario() + "";
//                        vcoCta.setCondicionesSQL(strMe);
//                    }
//                    setPuntosCta();
//
//                }
//
//                public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    
////                    System.out.println();
////                    
////                    if (objTblCelEdiTxtVcoCta.isConsultaAceptada())
////                    {
////                        bytDiaGen=2;
////                        //Generar evento "afterConsultarCuentas()".
////                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_CON_CTA);
////                    }
//                }
//            });
//            
//            /**************************************************************************************/
//            /**************************************************************************************/
//            /**************************************************************************************/
//            
//            objTblCelRenBut=new ZafTblCelRenBut();
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setCellRenderer(objTblCelRenBut);
//            
//            
//            
//            objTblCelEdiButVcoCta=new ZafTblCelEdiButVco(tblDat, vcoCta, intColVen, intColTbl);
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setCellEditor(objTblCelEdiButVcoCta);
//             objTblCelEdiButVcoCta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//                 public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                     
//                 }
//                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                     
//                 }
//                  public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                      
//                  }
//                  public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                      
//                  }
//             });
//            intColVen=null;
//            intColTbl=null;
//            
//            
////            objTblCelRenLbl=null;
//            //objTblCelEdiTxt=null;
//            
//            //Configurar JTable: Modo de operación del JTable.
//            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
//            tcmAux=null;
//        }
//        catch(Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//            e.printStackTrace();
//        }
//        return blnRes;
//    }
  
  private boolean configurarTblDat(){
        
      boolean blnRes = true;
      
      try{
          
          vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LINEA,"");
            vecCab.add(INT_TBL_DAT_COD_RUB,"Cód. Rub.");
            vecCab.add(INT_TBL_DAT_NOM_RUB,"Rubro");
            vecCab.add(INT_TBL_DAT_COD_CTA,"Cód. Cta.");
            vecCab.add(INT_TBL_DAT_NUM_CTA,"Núm. Cta.");
            vecCab.add(INT_TBL_DAT_BUT_CTA,"");
            vecCab.add(INT_TBL_DAT_NOM_CTA,"Nombre de la cuenta");

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

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LINEA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_RUB).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_RUB).setPreferredWidth(230);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CTA).setPreferredWidth(280);
            
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_RUB, tblDat);
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CTA, tblDat);
            

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_NUM_CTA);
            vecAux.add("" + INT_TBL_DAT_BUT_CTA);
            //vecAux.add("" + INT_TBL_DAT_BUT_ITM);
            //objTblMod.setColumnasEditables(vecAux);
            objTblMod.addColumnasEditables(vecAux);
            vecAux=null;

            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setCellRenderer(objTblCelRenLbl);

            
            int intColVen[]=new int[3];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            int intColTbl[]=new int[3];
            intColTbl[0]=INT_TBL_DAT_COD_CTA;
            intColTbl[1]=INT_TBL_DAT_NUM_CTA;
            intColTbl[2]=INT_TBL_DAT_NOM_CTA;

            objTblCelEdiTxtVcoCta=new ZafTblCelEdiTxtVco(tblDat, vcoCta, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setCellEditor(objTblCelEdiTxtVcoCta);
            objTblCelEdiTxtVcoCta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strEstCncDia="";//estado de conciliacion bancaria de la cuenta

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    strEstCncDia=tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_EST_CON)==null?"":tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_EST_CON).toString();
//                    if(strEstCncDia.equals("S")){
//                        mostrarMsgInf("<HTML>La cuenta ya fue conciliada.<BR>Desconcilie la cuenta en el documento a modificar y vuelva a intentarlo.</HTML>");
////                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_EDI_CEL);
//                        objTblCelEdiTxtVcoCta.setCancelarEdicion(true);
//                    }
//                    else if(strEstCncDia.equals("B")){
//                        mostrarMsgInf("<HTML>No se puede cambiar el valor de la cuenta<BR>Este valor proviene de la transferencia ingresada.</HTML>");
//                        objTblCelEdiTxtVcoCta.setCancelarEdicion(true);
//                    }
//                    else{
//                        //Permitir de manera predeterminada la operaciï¿½n.
//                        blnCanOpe=false;
//                        //Generar evento "beforeEditarCelda()".
//                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_EDI_CEL);
//                        //Permitir/Cancelar la ediciï¿½n de acuerdo a "cancelarOperacion".
//                        if (blnCanOpe)
//                        {
//                            objTblCelEdiTxtVcoCta.setCancelarEdicion(true);
//                        }
//                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
//                    System.out.println();
//                    
//                    if(strEstCncDia.equals("B")){
//
//                    }
//                    else{
//                        //Generar evento "afterEditarCelda()".
//                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_EDI_CEL);
//                    }

                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    
                    System.out.println();
                    
//                    intBanNumCta++;

                    //Generar evento "beforeConsultarCuentas()".
//                    fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_CON_CTA);
                    vcoCta.setCampoBusqueda(1);
                    vcoCta.setCriterio1(7);
                    if(objZafParSis.getCodigoUsuario()!=1)
                    {
                        strMe="";
                        strMe+=" AND a2.co_tipdoc=" + intCodTipDocGlb + "";
                        strMe+=" AND a2.co_usr=" + objZafParSis.getCodigoUsuario() + "";
                        vcoCta.setCondicionesSQL(strMe);
                    }
                    setPuntosCta();

                }

                public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
//                    System.out.println();
//                    
//                    if (objTblCelEdiTxtVcoCta.isConsultaAceptada())
//                    {
//                        bytDiaGen=2;
//                        //Generar evento "afterConsultarCuentas()".
//                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_CON_CTA);
//                    }
                }
            });
            
                        
            /**************************************************************************************/
            /**************************************************************************************/
            /**************************************************************************************/
            
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
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
          
      }
      catch(Exception e){
          blnRes=false;
          objUti.mostrarMsgErr_F1(this, e);
          e.printStackTrace();
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
  
   public void Configura_ventana_consulta(){

       configurarVenConPla();
       configurarVenConCta();
       configurarTblDat();
        
   }
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        buttonGroup1 = new javax.swing.ButtonGroup();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        PanTabGen = new javax.swing.JPanel();
        lblCodCom = new javax.swing.JLabel();
        txtCodPla = new javax.swing.JTextField();
        butPla = new javax.swing.JButton();
        lblNomHor = new javax.swing.JLabel();
        lblMinGra = new javax.swing.JLabel();
        txtDesCor = new javax.swing.JTextField();
        txtDesLar = new javax.swing.JTextField();
        panDetTraDep = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            //protected javax.swing.table.JTableHeader createDefaultTableHeader()
            //{
                //return new ZafTblHeaGrp(columnModel);
                //}
        };
        jPanel4 = new javax.swing.JPanel();
        lblObs2 = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txtObs1 = new javax.swing.JTextArea();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

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
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        panCen.setLayout(new java.awt.BorderLayout());

        tabGen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        tabGen.setPreferredSize(new java.awt.Dimension(115, 100));

        panTabGen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panTabGen.setPreferredSize(new java.awt.Dimension(100, 90));
        panTabGen.setLayout(new java.awt.BorderLayout());

        PanTabGen.setPreferredSize(new java.awt.Dimension(100, 70));
        PanTabGen.setLayout(null);

        lblCodCom.setText("Código:");
        PanTabGen.add(lblCodCom);
        lblCodCom.setBounds(4, 10, 100, 20);

        txtCodPla.setBackground(objZafParSis.getColorCamposSistema());
        txtCodPla.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        //txtCodPla.getDocument().addDocumentListener(zafRecHum06Bean.getDocLis());
        txtCodPla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPlaActionPerformed(evt);
            }
        });
        txtCodPla.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPlaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPlaFocusLost(evt);
            }
        });
        PanTabGen.add(txtCodPla);
        txtCodPla.setBounds(150, 10, 100, 20);

        butPla.setText("...");
        butPla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPlaActionPerformed(evt);
            }
        });
        PanTabGen.add(butPla);
        butPla.setBounds(250, 10, 20, 20);

        lblNomHor.setText("Descripción corta:");
        PanTabGen.add(lblNomHor);
        lblNomHor.setBounds(4, 30, 140, 20);

        lblMinGra.setText("Descripción larga:");
        lblMinGra.setToolTipText("Minutos de Gracia");
        PanTabGen.add(lblMinGra);
        lblMinGra.setBounds(4, 50, 130, 20);

        txtDesCor.setBackground(objZafParSis.getColorCamposObligatorios());
        //txtDesLar.getDocument().addDocumentListener(zafRecHum06Bean.getDocLis());
        txtDesCor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorActionPerformed(evt);
            }
        });
        PanTabGen.add(txtDesCor);
        txtDesCor.setBounds(150, 30, 100, 20);

        txtDesLar.setBackground(objZafParSis.getColorCamposObligatorios());
        //txtDesLar.getDocument().addDocumentListener(zafRecHum06Bean.getDocLis());
        txtDesLar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarActionPerformed(evt);
            }
        });
        PanTabGen.add(txtDesLar);
        txtDesLar.setBounds(150, 50, 440, 20);

        panTabGen.add(PanTabGen, java.awt.BorderLayout.PAGE_START);

        panDetTraDep.setLayout(new java.awt.BorderLayout());

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
        scrTbl.setViewportView(tblDat);

        panDetTraDep.add(scrTbl, java.awt.BorderLayout.CENTER);

        panTabGen.add(panDetTraDep, java.awt.BorderLayout.CENTER);

        jPanel4.setPreferredSize(new java.awt.Dimension(99, 40));
        jPanel4.setLayout(new java.awt.BorderLayout());

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblObs2.setText("Observación 1:");
        jPanel4.add(lblObs2, java.awt.BorderLayout.WEST);

        spnObs1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        txtObs1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtObs1.setLineWrap(true);
        spnObs1.setViewportView(txtObs1);

        jPanel4.add(spnObs1, java.awt.BorderLayout.CENTER);

        panTabGen.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        tabGen.addTab("General", panTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panTit.setPreferredSize(new java.awt.Dimension(100, 30));

        lblTit.setText("titulo"); // NOI18N
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.PAGE_START);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
	Configura_ventana_consulta();
}//GEN-LAST:event_formInternalFrameOpened

private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
    // TODO add your handling code here:
     String strMsg = "¿Está Seguro que desea cerrar este programa?";
     javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
     String strTit;
     strTit="Mensaje del sistema Zafiro";
     if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {

         if(rstCab!=null) 
             rstCab=null;
         if(STM_GLO!=null)
             STM_GLO=null;

         System.gc();
         dispose();
     }
}//GEN-LAST:event_formInternalFrameClosing
               
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseMoved

    private void txtCodPlaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPlaActionPerformed
        // TODO add your handling code here:
        txtCodPla.transferFocus();
    }//GEN-LAST:event_txtCodPlaActionPerformed

    private void butPlaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPlaActionPerformed

        
        objVenConCli.setTitle("Listado de Plantillas");
        String  strSQL = "select co_pla, tx_descor, tx_deslar from tbm_cabPlaRolPag where co_emp = " + objZafParSis.getCodigoEmpresa() + " ";

        objVenConCli.setSentenciaSQL(strSQL);
        objVenConCli.setCampoBusqueda(1);
        objVenConCli.show();
        if (objVenConCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
            txtCodPla.setText(objVenConCli.getValueAt(1));
            objTooBar.consultar();
        }
    }//GEN-LAST:event_butPlaActionPerformed

    private void txtDesCorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesCorActionPerformed

    private void tblDatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblDatFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDatFocusGained

    private void txtDesLarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesLarActionPerformed

    private void txtCodPlaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPlaFocusGained
        // TODO add your handling code here:
        strCodPla = txtCodPla.getText();
        txtCodPla.selectAll();
    }//GEN-LAST:event_txtCodPlaFocusGained

    private void txtCodPlaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPlaFocusLost
        // TODO add your handling code here:
        if (!txtCodPla.getText().equalsIgnoreCase(strCodPla)) {
            if (txtCodPla.getText().equals("")) {
                txtCodPla.setText("");
                txtDesCor.setText("");
                txtDesLar.setText("");
            } else {
                BuscarPla("a1.co_pla", txtCodPla.getText(), 0);
            }
        } else {
            txtCodPla.setText(strCodPla);
        }
    }//GEN-LAST:event_txtCodPlaFocusLost

    public void BuscarPla(String campo,String strBusqueda,int tipo){
        
        objVenConCli.setTitle("Listado de Plantillas");
        if(objVenConCli.buscar(campo, strBusqueda )) {
            txtCodPla.setText(objVenConCli.getValueAt(1));
            txtDesCor.setText(objVenConCli.getValueAt(2));
            txtDesLar.setText(objVenConCli.getValueAt(3));
        }else{
            objVenConCli.setCampoBusqueda(tipo);
            objVenConCli.cargarDatos();
            objVenConCli.show();
            if (objVenConCli.getSelectedButton()==objVenConCli.INT_BUT_ACE) {
                txtCodPla.setText(objVenConCli.getValueAt(1));
            txtDesCor.setText(objVenConCli.getValueAt(2));
            txtDesLar.setText(objVenConCli.getValueAt(3));
        }else{
                txtCodPla.setText(strCodPla);
                txtDesCor.setText(strDesCor);
                txtDesLar.setText(strDesLar);
  }}}
    
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

private void bloquea(javax.swing.JTextField txtFiel,  boolean blnEst){
    java.awt.Color colBack = txtFiel.getBackground();
    txtFiel.setEditable(blnEst);
    txtFiel.setBackground(colBack);
}
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanTabGen;
    public javax.swing.JButton butPla;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblCodCom;
    private javax.swing.JLabel lblMinGra;
    private javax.swing.JLabel lblNomHor;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panDetTraDep;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTit;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    public javax.swing.JTextField txtCodPla;
    public javax.swing.JTextField txtDesCor;
    public javax.swing.JTextField txtDesLar;
    private javax.swing.JTextArea txtObs1;
    // End of variables declaration//GEN-END:variables

    
  private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }   
    

  public class mitoolbar extends ZafToolBar{
    public mitoolbar(javax.swing.JInternalFrame jfrThis){
        super(jfrThis, objZafParSis);
    }
   

public boolean anular() {
 boolean blnRes=false;
java.sql.Connection  conn;
try{
 conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
 if(conn!=null){
   conn.setAutoCommit(false);

    strAux=objTooBar.getEstadoRegistro();
    if (strAux.equals("Eliminado")) {
        MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
        blnRes=true;
    }
    if (strAux.equals("Anulado")) {
        MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
        blnRes=true;
    }
   if(!blnRes){

        if(anularReg(conn)){

            conn.commit();
            blnRes=true;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;  
        }else conn.rollback();
   }else blnRes=false;

  conn.close();
  conn=null;
}}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}


private boolean anularReg(java.sql.Connection conn){
boolean blnRes=false;
java.sql.Statement stmLoc;
String strSql="";

try{
 if(conn!=null){
    stmLoc=conn.createStatement();

    strSql = "UPDATE tbm_cabPlaRolPag SET st_reg='I', fe_ultMod=" + objZafParSis.getFuncionFechaHoraBaseDatos() + ", co_usrMod=" + objZafParSis.getCodigoUsuario() 
             + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_pla = " + txtCodPla.getText();
    stmLoc.executeUpdate(strSql);
    
   stmLoc.close();
   stmLoc=null;

 blnRes=true;
}}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
return blnRes;
}

    public void clickAceptar() {
        setEstadoBotonMakeFac();
    }

    public void clickAnterior() 
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    rstCab.previous();
                    cargarReg();
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

    public void clickSiguiente()
    {
        try
        {
            if (!rstCab.isLast())
            {
                rstCab.next();
                cargarReg();
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
                rstCab.first();
                cargarReg();
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

    public void clickFin()
    {
        try
        {
            if (!rstCab.isLast())
            {
                rstCab.last();
                cargarReg();
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

    public void clickAnular() {

    }


    public void clickConsultar() {
        clnTextos();
        noEditable(false);
        txtCodPla.requestFocus();
      //        bloquea(txtNumCuo, false);

    }

    public void clickEliminar() {
//          noEditable(false);
    }

    public void clickInsertar() {
        
        try{
            clnTextos();
            //noEditable(false);
            txtDesCor.requestFocus();


            datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                     java.util.Date dateObj =datFecAux;
                     java.util.Calendar calObj = java.util.Calendar.getInstance();
                     calObj.setTime(dateObj);

                     
                     cargarRubExi();

                     if(rstCab!=null) {
                         rstCab.close();
                         rstCab=null;
                     }
        }
        catch (Exception e) { e.printStackTrace(); objUti.mostrarMsgErr_F1(this, e); }
    }

    public boolean cargarRubExi(){
        
        boolean blnRes=false;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null; 
        String strSQL="";
        
        try{
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
            
                stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="select a2.co_rub,a2.tx_nom from tbm_rubrolpagemp a1";
                strSQL+=" inner join tbm_rubrolpag a2 on (a1.co_rub=a2.co_rub)";
                strSQL+=" where a1.co_emp = " + objZafParSis.getCodigoEmpresa();
                strSQL+=" and a2.st_reg like 'A'";
                strSQL+=" order by a2.co_rub asc";
                System.out.println("sql ne_per : "+strSQL);
                rst=stm.executeQuery(strSQL);
                
                while(rst.next()){
                    Vector vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LINEA,"");
                    vecReg.add(INT_TBL_DAT_COD_RUB,rst.getString("co_rub"));
                    vecReg.add(INT_TBL_DAT_NOM_RUB,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_DAT_COD_CTA,"");
                    vecReg.add(INT_TBL_DAT_NUM_CTA,"");
                    vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                    vecReg.add(INT_TBL_DAT_NOM_CTA,"");
                    vecDat.add(vecReg);
                }
                
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();

            }
        }catch (java.sql.SQLException Evt) {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, Evt);
    } catch (Exception Evt) {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, Evt);
    }finally {
        try{rst.close();}catch(Throwable ignore){}
        try{stm.close();}catch(Throwable ignore){}
        try{con.close();}catch(Throwable ignore){}
    }
    return blnRes;
    }
        
    public void setEstadoBotonMakeFac(){
        switch(getEstado()) {
            case 'l'://Estado 0 => Listo
                break;
            case 'x'://Estado click modificar
                break;
            case 'c'://Estado Consultar
                break;
            case 'y':
                break;
            case 'z':
                break;
            default:
                break;
        }
    }

public boolean eliminar() {
boolean blnRes=false;
java.sql.Connection  conn;
try{
 conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
 if(conn!=null){
   conn.setAutoCommit(false);

    strAux=objTooBar.getEstadoRegistro();
    if (strAux.equals("Eliminado")) {
        MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
        blnRes=true;
    }

   if(!blnRes){ 
    if(eliminarReg(conn)){
        conn.commit();
        blnRes=true;
        objTooBar.setEstadoRegistro("Eliminado");
        blnHayCam=false;
    }else conn.rollback();
   }else blnRes=false;

  conn.close();
  conn=null;
}}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
return blnRes;
}

private boolean eliminarReg(java.sql.Connection conn){
boolean blnRes=false;
java.sql.Statement stmLoc = null;
String strSql=""; 
try{
 if(conn!=null){
    stmLoc=conn.createStatement();

    strSql = "DELETE FROM tbm_detPlaRolPag  WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_pla = " + txtCodPla.getText();
    stmLoc.executeUpdate(strSql);
    
    strSql = "DELETE FROM tbm_cabPlaRolPag  WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_pla = " + txtCodPla.getText();
    stmLoc.executeUpdate(strSql);
    
}
    
   stmLoc.close();
   stmLoc=null;
   blnRes=true;
}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
return blnRes;
}

    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean validaCampos()
    {// TODO add your handling code here:

        //Validar el "Tipo de documento".
        if (txtDesCor.getText().equals(""))
        {
            //tabFrm.setSelectedIndex(0);
            tabGen.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Descripción corta</FONT> es obligatorio.<BR>Escriba o seleccione una descripción corta y vuelva a intentarlo.</HTML>");
            txtDesCor.requestFocus();
            return false;
        }
        if (txtDesLar.getText().equals(""))
        {
            //tabFrm.setSelectedIndex(0);
            tabGen.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Descripción larga</FONT> es obligatorio.<BR>Escriba o seleccione una descripción larga y vuelva a intentarlo.</HTML>");
            txtDesLar.requestFocus();
            return false;
        }

        return true;
    }

    public boolean insertar(){
        boolean blnRes=false;

        try{
            if(validaCampos()){
                if(insertarReg())
                    blnRes=true;
            }

        }catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;
    }

public boolean insertarReg(){
    boolean blnRes=true;
    java.sql.Connection con = null;
    java.sql.Statement stmLoc = null;
    java.sql.ResultSet rstLoc = null; 
    int intCodDoc=0;

    try{
        con =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        if(con!=null){ 
            con.setAutoCommit(false);
            stmLoc=con.createStatement();
        
            if(insertarCabPla(con)) {

                if(insertarDetPla(con)){
                    con.commit();
                }else{
                    con.rollback();
                    blnRes=false;
                }
                    
            } else{
                con.rollback();
                blnRes=false;
            }
  
            con.close();
            con=null;
        }
     }catch (java.sql.SQLException Evt) {
        Evt.printStackTrace();
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, Evt);
    } catch (Exception Evt) {
        Evt.printStackTrace();
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, Evt);
    }finally {
        try{rstLoc.close();}catch(Throwable ignore){}
        try{stmLoc.close();}catch(Throwable ignore){}
        try{con.close();}catch(Throwable ignore){}
    }
    return blnRes;
}

public boolean insertarDetPla(java.sql.Connection con){
    boolean blnRes=true;
    String strSql="";
    int intCoReg=1;
    java.sql.Statement stmLoc = null;
    java.sql.ResultSet rstLoc = null;
    try{
        stmLoc = con.createStatement();
            
        
//        String strSQL="SELECT case when (Max(co_reg)+1) is null then 1 else Max(co_reg)+1 end as co_reg  FROM tbm_detPlaRolPag WHERE " +
//                   " co_emp="+objZafParSis.getCodigoEmpresa() + " and co_pla = " + intCodPla;
//        rstLoc = stmLoc.executeQuery(strSQL);
//        if(rstLoc.next())
//            intCoReg = rstLoc.getInt("co_reg");
        
        for(int intFil=0;intFil<tblDat.getRowCount();intFil++){
            
            
            Object objCoRub=objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_RUB);
            String strCoRub="NULL";
            if(objCoRub!=null){
                strCoRub=objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_RUB).toString();
            }
            
            Object objCoCta=objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_CTA);
            String strCoCta="NULL";
            if(objCoCta!=null){
                strCoCta=objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_CTA).toString());
            }
            
            strSql="INSERT INTO tbm_detplarolpag(co_emp, co_pla, co_reg, co_rub, co_cta)";
            strSql+=" VALUES (";
            strSql+=objZafParSis.getCodigoEmpresa() + " , ";
            strSql+=intCodPla + " , ";
            strSql+=intCoReg + " , ";
            strSql+=strCoRub + " , ";
            strSql+=strCoCta + ")";
            
            System.out.println("insert det_pla: " + strSql);
            stmLoc.executeUpdate(strSql);
            //txtCodPla.setText(String.valueOf(intCodPla));
            intCoReg++;
        }
    }catch(java.sql.SQLException Evt){ 
        blnRes=false;  
        objUti.mostrarMsgErr_F1(this, Evt); 
    }catch(Exception Evt){ 
        blnRes=false;  
        objUti.mostrarMsgErr_F1(this, Evt); 
    }finally {
        try{stmLoc.close();}catch(Throwable ignore){}
        try{rstLoc.close();}catch(Throwable ignore){}
        }
   return blnRes;  
}

public boolean insertarCabPla(java.sql.Connection con){
    boolean blnRes=false;
    String strSQL="";
    java.sql.Statement stmLoc = null;
    java.sql.ResultSet rstLoc = null;
    try{
        
        stmLoc = con.createStatement();
      
        strSQL="SELECT case when (Max(co_pla)+1) is null then 1 else Max(co_pla)+1 end as co_pla  FROM tbm_cabPlaRolPag WHERE " +
                   " co_emp="+objZafParSis.getCodigoEmpresa();
        rstLoc = stmLoc.executeQuery(strSQL);
        if(rstLoc.next())
            intCodPla = rstLoc.getInt("co_pla");
        
//        if (!objTblMod.isAllRowsComplete())
//        {
//            mostrarMsgInf("<HTML>El detalle del documento contiene filas que están incompletas.<BR>Verifique el contenido de dichas filas y vuelva a intentarlo.</HTML>");
//            tblDat.setRowSelectionInterval(0, 0);
//            tblDat.changeSelection(0, INT_TBL_DAT_COD_RUB, true, true);
//            tblDat.requestFocus();
//            return false;
//        }
        
        String strSt_Reg = "A"; //registo activo

        strSQL="INSERT INTO tbm_cabplarolpag(co_emp, co_pla, tx_descor, tx_deslar, tx_obs1, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod)";
        strSQL+=" VALUES (";
        strSQL+=objZafParSis.getCodigoEmpresa() +" , ";
        strSQL+=intCodPla +" , ";
        strSQL+=objUti.codificar(txtDesCor.getText()) +" , ";
        strSQL+=objUti.codificar(txtDesLar.getText()) +" , ";
        strSQL+=objUti.codificar(txtObs1.getText()) +" , ";
        strSQL+=objUti.codificar(strSt_Reg) +" , ";
        strSQL+="CURRENT_TIMESTAMP" +" , ";
        strSQL+="NULL" +" , ";
        strSQL+=objZafParSis.getCodigoUsuario() +" , ";
        strSQL+="NULL)";
                
        System.out.println("insert cab_pla: " + strSQL);
        stmLoc.executeUpdate(strSQL);
        
        blnRes=true;

    }catch(java.sql.SQLException Evt){ 
        blnRes=false;  
        objUti.mostrarMsgErr_F1(this, Evt); 
    }catch(Exception Evt){ 
        blnRes=false;  
        objUti.mostrarMsgErr_F1(this, Evt); 
    }finally {
        try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
        try{rstLoc.close();rstLoc=null;}catch(Throwable ignore){}
        }
   return blnRes;         
 }
    public boolean modificar(){
        boolean blnRes=false;
        java.sql.Connection conn = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null; 
        
        try {

            strAux=objTooBar.getEstadoRegistro();
        
            if(strAux.equals("Eliminado")) {
                MensajeInf("El documento está ELIMINADO.\nNo es posible modifcar un documento eliminado.");
                return false;
            }
            if(strAux.equals("Anulado")) {
                MensajeInf("El documento ya está ANULADO.\nNo es posible modifcar un documento anulado.");
                return false;
            }
            
            if (validaCampos()) {
                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) {
                    conn.setAutoCommit(false);
                    stmLoc=conn.createStatement();
                    
                    if(modificarCab(conn)) {
                    
                        if(modificarDet(conn)){
                            conn.commit();
                            blnRes=true;
                        }else{
                            conn.rollback();
                        }

                } else{
                        conn.rollback();
                    }
                    
                }
            }
        }catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }finally {
            try{rstLoc.close();}catch(Throwable ignore){}
            try{stmLoc.close();}catch(Throwable ignore){}
            try{conn.close();}catch(Throwable ignore){}
        }
         
         return blnRes;
    }

 public boolean modificarCab(java.sql.Connection conn){
     boolean blnRes=true;
     java.sql.Statement stmLoc = null;
     java.sql.ResultSet rstLoc = null;
     String strSQL="";
     try{
         
         stmLoc = conn.createStatement();

         strSQL="UPDATE tbm_cabplarolpag SET";
         strSQL+=" tx_descor="+objUti.codificar(txtDesCor.getText()) + ", ";
         strSQL+=" tx_deslar="+objUti.codificar(txtDesLar.getText()) + ", ";
         strSQL+=" tx_obs1=" +  objUti.codificar(txtObs1.getText()) + ", ";
         strSQL+=" fe_ultmod= current_timestamp" + ", ";
         strSQL+=" co_usrmod="+objZafParSis.getCodigoUsuario();
         strSQL+=" WHERE co_emp = " + objZafParSis.getCodigoEmpresa();
         strSQL+=" AND co_pla = " + txtCodPla.getText();


         System.out.println("mod cab_pla: " + strSQL);
         stmLoc.executeUpdate(strSQL);

    }catch(java.sql.SQLException Evt){ 
        blnRes=false;  
        objUti.mostrarMsgErr_F1(this, Evt); 
    }catch(Exception Evt){ 
        blnRes=false;  
        objUti.mostrarMsgErr_F1(this, Evt); 
    }finally {
        try{stmLoc.close();}catch(Throwable ignore){}
        try{rstLoc.close();}catch(Throwable ignore){}
        }
   return blnRes;         
 }
 
 public boolean modificarDet(java.sql.Connection conn){
     
     boolean blnRes=true;
     java.sql.Statement stmLoc = null;
     java.sql.Statement stmLocAux = null;
     java.sql.ResultSet rstLoc = null;
     String strSQL="";
     int intCoReg=1;
     
     try{
        if (conn!=null){
             
            stmLoc=conn.createStatement();
            stmLocAux=conn.createStatement();
            
            strSQL = "delete from tbm_detPlaRolPag where co_emp="+ objZafParSis.getCodigoEmpresa() +
                     " and co_pla = " + txtCodPla.getText();
            stmLoc.executeUpdate(strSQL);
            
            for(int intFil=0;intFil<tblDat.getRowCount();intFil++){
            
            
                Object objCoRub=objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_RUB);
                String strCoRub="NULL";
                if(objCoRub!=null){
                    strCoRub=objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_RUB).toString();
                }

                Object objCoCta=objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_CTA);
                String strCoCta="NULL";
                if(objCoCta!=null){
                    strCoCta=objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_CTA).toString());
                }

                strSQL="INSERT INTO tbm_detplarolpag(co_emp, co_pla, co_reg, co_rub, co_cta)";
                strSQL+=" VALUES (";
                strSQL+=objZafParSis.getCodigoEmpresa() + " , ";
                //strSQL+=intCodPla + " , ";****
                strSQL+=txtCodPla.getText() + " , ";
                strSQL+=intCoReg + " , ";
                strSQL+=strCoRub + " , ";
                strSQL+=strCoCta + ")";

                System.out.println("insert det_pla: " + strSQL);
                stmLoc.executeUpdate(strSQL);
                
                if(tblDat.getValueAt(intFil, INT_TBL_DAT_LINEA).toString().equals("M")){
                    
                    String strCoRubMod = objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_RUB).toString();
                    Object obj = objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_CTA);
                    String strCoCtaMod = null;
                    if(obj==null){
                        strCoCtaMod=null;
                    }else{
                        strCoCtaMod = objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_CTA).toString();
                    }
                    
                    
                    strSQL="";
                    strSQL+="select * from tbm_traemp where co_pla = "  + txtCodPla.getText();
                    strSQL+=" and co_emp = " + objZafParSis.getCodigoEmpresa();
                    strSQL+=" and co_pla = " + txtCodPla.getText();
                    rstLoc=stmLoc.executeQuery(strSQL);
                    
                    
                    
                    while(rstLoc.next()){
                        
                        strSQL="";
                        strSQL+="update tbm_suetra set co_cta = " + objUti.codificar(strCoCtaMod);
                        strSQL+=" where co_emp = " + objZafParSis.getCodigoEmpresa();
                        strSQL+=" and co_rub = " + strCoRubMod;
                        strSQL+=" and co_tra = " + rstLoc.getString("co_tra");
                        System.out.println("act tbm_suetra: " + strSQL);
                        stmLocAux.executeUpdate(strSQL);
                        
                    }
                    
                    
                    
//                    strSQL+="update tbm_suetra set co_cta = " + strCoCtaMod;
//                    strSQL+=" where co_emp = " + objZafParSis.getCodigoEmpresa();
//                    strSQL+=" and co_rub = " + strCoRubMod;
//                    
//                    System.out.println("act tbm_suetra: " + strSQL);
//                    stmLoc.executeUpdate(strSQL);
                    
                }
                
                intCoReg++;
            }           
        }
     }catch(java.sql.SQLException Evt){ 
         blnRes=false;  
         objUti.mostrarMsgErr_F1(this, Evt); 
     }
     catch(Exception Evt){ 
         blnRes=false;  
         objUti.mostrarMsgErr_F1(this, Evt); 
     }finally {
         try{rstLoc.close();}catch(Throwable ignore){}
         try{stmLoc.close();}catch(Throwable ignore){}
         try{stmLocAux.close();}catch(Throwable ignore){}
     }
     return blnRes;         
 }

 private void noEditable(boolean blnEst){
      txtObs1.setEditable(false);
      

   }

    public void  clnTextos(){

        txtCodPla.setText("");
        txtDesCor.setText("");
        txtDesLar.setText("");

        txtObs1.setText("");

        objTblMod.removeAllRows();
    }

    public boolean cancelar() {
        boolean blnRes=true;

        try {
            if (blnHayCam || objTblMod.isDataModelChanged()) {
                if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m') {
                    if (!isRegPro())
                        return false;
                }
            }
            if (rstCab!=null) {
                rstCab.close();
                if (STM_GLO!=null){
                    STM_GLO.close();
                    STM_GLO=null;
                }
                rstCab=null;

            }
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        clnTextos();
        blnHayCam=false;

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

       this.setEstado('w');

        return true;
    }

    public boolean afterVistaPreliminar() {
        return true;
    }

/**
 * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
 * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
 * seleccionando una de las opciones que se presentan.
 */
private int mostrarMsgCon(String strMsg) {
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
}

/**
 * Esta función se encarga de agregar el listener "DocumentListener" a los objTooBars
 * de tipo texto para poder determinar si su contenido a cambiado o no.
 */
private boolean isRegPro() {
    boolean blnRes=true;
    String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
    strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
    switch (mostrarMsgCon(strAux)) {
        case 0: //YES_OPTION
            switch (objTooBar.getEstado()) {
                case 'n': //Insertar
                    blnRes=objTooBar.insertar();
                    break;
                case 'm': //Modificar
//                        blnRes=objTooBar.modificar();
                    break;
            }
            break;
        case 1: //NO_OPTION
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
            blnRes=true;
            break;
        case 2: //CANCEL_OPTION
            blnRes=false;
            break;
    }
    return blnRes;
}

    public boolean consultar() {
            /*
             * Esto Hace en caso de que el modo de operacion sea Consulta
             */
       //return _consultar(FilSql());
        consultarReg();
        return true;
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg()
    {
        boolean blnRes=true;
        String strSQL="";
        try
        {
            conCab=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que sólo se muestre los documentos asignados al programa.
                if (txtCodPla.getText().equals(""))
                {

                    strSQL+="select * FROM tbm_cabPlaRolPag AS a";
                    strSQL+=" where a.co_emp = " + objZafParSis.getCodigoEmpresa();
                    //strSQL+=" AND a.st_reg<>'E'";
                }else{
                    strSQL+="select * FROM tbm_cabPlaRolPag AS a";
                    strSQL+=" where a.co_emp = " + objZafParSis.getCodigoEmpresa();
                    strSQL+=" AND a.co_pla=" + txtCodPla.getText();
                    //strSQL+=" AND a.st_reg<>'E'";
                }

                strAux=txtDesCor.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a.tx_descor = " + objUti.codificar(txtDesCor.getText());
                strAux=txtDesLar.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a.tx_deslar = " + objUti.codificar(txtDesLar.getText());
                
                strSQL+=" ORDER BY a.co_pla";
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    clnTextos();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
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
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            if (cargarCabReg())
            {
                if (cargarDetReg())
                {
                    blnRes=true;
                }
            }
//            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg()
    {
        int intPosRel;
        boolean blnRes=true;
        String strSQL="";

        try
        {
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT * FROM tbm_cabplarolpag";
                strSQL+=" where co_emp = " + rstCab.getString("co_emp");
                strSQL+=" and co_pla = " + rstCab.getString("co_pla");
                
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strAux=rst.getString("co_pla");
                    txtCodPla.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_descor");
                    txtDesCor.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_deslar");
                    txtDesLar.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_obs1");
                    txtObs1.setText((strAux==null)?"":strAux);

                    
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                    
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    clnTextos();
                    blnRes=false;
                }
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
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {

        String strSQL;
        boolean blnRes=true;

        try
        {
//            objTooBar.setMenSis("Obteniendo datos...");
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="select a3.tx_nom,a1.co_rub,a2.co_cta,tx_deslar,tx_codcta from tbm_detplarolpag a1";
                strSQL+=" left outer join tbm_placta a2 on (a2.co_emp=a1.co_emp and a2.co_cta = a1.co_cta)";
                strSQL+=" inner join tbm_rubrolpag a3 on (a3.co_rub=a1.co_rub and a3.st_reg like 'A')";
                strSQL+=" where a1.co_emp = " + rstCab.getString("co_emp");
                strSQL+=" and a1.co_pla = " + rstCab.getString("co_pla");
                strSQL+=" order by a1.co_rub asc";
                System.out.println("sql det_pla: " +strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                
                //Obtener los registros.
//                objTooBar.setMenSis("Cargando datos...");
                while (rst.next())
                {
                    Vector vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LINEA,"");
                    vecReg.add(INT_TBL_DAT_COD_RUB,rst.getString("co_rub"));
                    vecReg.add(INT_TBL_DAT_NOM_RUB,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("co_cta"));
                    vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_codcta"));
                    vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                    vecReg.add(INT_TBL_DAT_NOM_CTA,rst.getString("tx_deslar"));
                    vecDat.add(vecReg);
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
//                objTooBar.setMenSis("Listo");
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

private void mostrarEstado(String strStReg){
            setEstado('w');//l-c-x-y-z-n-m-e-a-w
            if(strStReg.equals("I")){
                setEstadoRegistro("Anulado");
                setEnabledModificar(false);
                setEnabledEliminar(false);
                setEnabledAnular(false);
            }else{
                if(strStReg.equals("E")){
                    setEstadoRegistro("Eliminado");
                    setEnabledModificar(false);
                    setEnabledEliminar(false);
                    setEnabledAnular(false);
                }else{
                    setEstadoRegistro("");
                }
            }
        }

public void clickModificar(){
  setEditable(true);

  java.awt.Color colBack;
//  colBack = txtCodDoc.getBackground();
//  txtCodDoc.setEditable(false);
//  txtDif.setBackground(colBack);
//  txtDif.setEditable(false);
 this.setEnabledConsultar(false);

 objTblMod.setDataModelChanged(false);
 blnHayCam=false;
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

    public void cierraConnections(){

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

  private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {
      public void mouseMoved(java.awt.event.MouseEvent evt) {
          int intCol=tblDat.columnAtPoint(evt.getPoint());
          String strMsg="";
          switch (intCol) {
              case INT_TBL_DAT_LINEA:
                  strMsg="";
                  break;
              case INT_TBL_DAT_COD_RUB:
                  strMsg="Código del rubro";
                  break;
              case INT_TBL_DAT_NOM_RUB:
                  strMsg="Nombre del rubro";
                  break;
              case INT_TBL_DAT_COD_CTA:
                  strMsg="Código de cuenta";
                  break;
              case INT_TBL_DAT_NUM_CTA:
                  strMsg="Número de la cuenta";
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
            if(objZafParSis.getCodigoUsuario()==1)
            {
                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" FROM tbm_plaCta AS a1";
                strSQL+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL+=" AND a1.tx_tipCta='D'";
                strSQL+=" ORDER BY a1.tx_codCta";
            }
            else
            {
//                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
//                strSQL+=" FROM tbm_placta as a1";
//                strSQL+=" INNER JOIN tbr_ctatipdocusr as a2 ON (a1.co_emp=a2.co_emp and a1.co_cta=a2.co_cta)";
//                strSQL+=" WHERE a2.co_emp=" + objZafParSis.getCodigoEmpresa();
//                strSQL+=" AND a2.co_loc=" + objZafParSis.getCodigoLocal();
//                strSQL+=" AND a1.tx_tipCta='D'";
//                strSQL+=" ORDER BY a1.tx_codCta";
                    strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" FROM tbm_plaCta AS a1";
                strSQL+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL+=" AND a1.tx_tipCta='D'";
                strSQL+=" ORDER BY a1.tx_codCta";
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
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar las "Plantillas".
     */
    private boolean configurarVenConPla()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_pla");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción Corta");
            arlAli.add("Descripción Larga");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("80");
            arlAncCol.add("400");
            //Armar la sentencia SQL.
            String strSQL="";
            
            strSQL+="select co_pla, tx_desCor, tx_desLar where co_emp = " + objZafParSis.getCodigoEmpresa();
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=1;
            objVenConCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Plantillas", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            objVenConCli.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            objVenConCli.setCampoBusqueda(0);
            objVenConCli.setCriterio1(1);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
}