/*
 *    tbr_cabmovinv   System.out
 *        
 * Created on 6 de septiembre de 2004, 15:54         
 *     
 */               
                             
package Ventas.ZafVen24;
import Librerias.ZafUtil.ZafUtil;
import java.sql.*;
import java.sql.*;
import java.io.*;
import java.util.Vector;
 /*
 * @author Javier Ayapata 
 */
public class ZafVen24_03 extends javax.swing.JDialog {

 //   mitoolbar objeto;
    private Librerias.ZafAsiDia.ZafAsiDia objAsiDia; // Asiento de Diario
    private Librerias.ZafUtil.ZafCtaCtb_dat objCtaCta_Dat;  //Datos de Cuentas contables
    Librerias.ZafInventario.ZafInvItm objInvItm; 
 
    //  private Librerias.ZafSisCon.ZafSisCon objSisCon; //Sistema Consolidado    
    private LisCambioTbl objLisCamTblFac = new LisCambioTbl();
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;     
    private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt;  
    private String strBeforeValue,strAfterValue;
    javax.swing.JTextArea txtsql = new javax.swing.JTextArea();
    Librerias.ZafParSis.ZafParSis objZafParSis;
    ZafUtil objutil;
    javax.swing.JDialog jfrThis; 
    Connection CONN_GLO=null;  //Coneccion a la base donde se encuentra la cotizacion
    Statement stmDoc;   //Statement para la cotizacion 
    Statement stmDocDet;   //Statement para la cotizacion 
    ResultSet rstDocCab;//Resultset que tendra los datos de la cabecera
    ResultSet rstDocDet,rstDocDet2;//Resultset que tendra los datos del Detalle
    String sSQL, strFiltro;//EL filtro de la Consulta actual
    int intCodMnu; //obtiene el codigo del menu
    double dblPorIva ;  //Porcentaje de Iva para la empresa enviado en zafParSys
    double dblSubtotalDoc, dblTotalDoc, dblIvaDoc;
    int intNumDec= 2 ; //Numero de decimales a presentar
    boolean blnCambios = false; //Detecta ke se hizo cambios en el documento
    LisTextos objlisCambios = new LisTextos();     // Instancia de clase que detecta cambios
   // Librerias.ZafInventario.ZafInventario objZafInv;   //Clase que maneja los costos y existencias de los productos
   // Librerias.ZafUtil.ZafTipItm      objZafTipItm;      // Para saber si un producto es o no Servicio    
    
    int intEstDev1=0,intEstDev2=1,intEstDev3=1;
     Librerias.ZafUtil.UltDocPrint           objUltDocPrint2;  // Para trabajar con la informacion de tipo de documento    
    
    java.util.Vector vecTipDoc; //Vector que contiene el codigo de tipos de documentos
    java.util.Vector vecLoc; //Vector que contiene los locales de la empresa
    
    
    final int INT_TBL_LINEA    = 0 ; 
    final int INT_TBL_ALTITM   = 1 ;
    final int INT_TBL_DESITM   = 2 ;
    final int INT_TBL_UNIDAD   = 3 ;
    final int INT_TBL_CODBOD   = 4 ;            //Codigo de la bodega
    final int INT_TBL_CANMOV   = 5 ;
    final int INT_TBL_CANDIFDEV= 6 ;
    final int INT_TBL_CANDEV   = 7 ;  
    final int INT_TBL_PRECIO   = 8 ;       //Cantidad del movimiento (venta o compra)
    final int INT_TBL_PREDEV   = 9 ;  
    final int INT_TBL_PORDES   = 10 ;  
    final int INT_TBL_CANDES   = 11 ;  
    final int INT_TBL_IVAITM   = 12;  
    final int INT_TBL_TOTAL    = 13;  
    final int INT_TBL_CODITM   = 14;            //Codigo del item (codigo interno del sistema)
    final int INT_TBL_ALTITM2  = 15 ;
    final int INT_TBL_ITMSER   = 16 ;  
    final int INT_TBL_COSTO    = 17 ;  
    final int INT_TBL_DESORI   = 18 ;  
    final int INT_TBL_NOMPRV   = 19 ;  
    final int INT_TBL_CODLOC   = 20 ;  
    final int INT_TBL_CODTIP   = 21 ;  
    final int INT_TBL_CODDOC   = 22 ;  
    final int INT_TBL_CODREG   = 23 ;  
    final int INT_TBL_CODREGDEV= 24 ;  
 
    
    
    int intNunDocFac=0;
      
    String strAux;   
      public boolean blnAcepta = false;
     public boolean blnEst=false;
    
    ZafCliente_dat objCliente_dat; // Para Obtener la informacion del cliente
    ZafCiudad_dat  objCiudad_dat;  // Para Obtener la informacion de una ciudad
    UltDocPrint    objUltDocPrint;  // Para trabajar con la informacion de tipo de documento
    int intSecuencialFac=0;
      
     String STR_ESTREG="";  
      
    String GLO_strArreItm="";    
    int Glo_intCodSec=0;
    String strCodDocFac;
    
    String strCodDocDev;
    String strCodNumDev="";
    String stIvaVen="S"; 
    String strCodTipDocFac;
    int intCodLocFac;
    int intCodLocDev;
    int intGenCruAut=0;

    public StringBuffer stbDocDevEmp=new StringBuffer();

    //ZafBarraInferior objeto2; 
    /** Creates new form zafCotCom */
    public ZafVen24_03(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis obj, String strDocFac, String strCodTidDocFac, int intLocFac, int intLocDev, java.sql.Connection ConnR, int intGenCru) { 
         
         super(parent, modal);
         objZafParSis = obj;
         intGenCruAut=intGenCru;
         strCodDocFac=strDocFac;
         strCodTipDocFac=strCodTidDocFac;
         intCodLocFac=intLocFac;
         intCodLocDev=intLocDev;
         CONN_GLO = ConnR; 
        
        jfrThis = this;
        objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia(objZafParSis);        
        //objSisCon = new Librerias.ZafSisCon.ZafSisCon(objZafParSis);
        
        objInvItm=new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);
        
        objutil = new ZafUtil();
        this.setTitle(objZafParSis.getNombreMenu()+  " Ver 5.8"  );
        intCodMnu = 1918; //objZafParSis.getCodigoMenu();
        initComponents();

         
        objUltDocPrint2 = new Librerias.ZafUtil.UltDocPrint(objZafParSis);    
        
        panAsiento.add(objAsiDia,java.awt.BorderLayout.CENTER);
        
        txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 

        txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtFecDoc.setText("");
        panCotGenNor.add(txtFecDoc);
        txtFecDoc.setBounds(568, 8, 92, 20);

        
        //Nombres de los tabs
        tabCotCom.setTitleAt(0,"General");
        tabCotCom.setTitleAt(1,"Asiento de Diario");
       // objeto = new mitoolbar(this);
        
        dblPorIva = getIva();
        lblIva.setText("IVA " + dblPorIva + "%");
       // this.getContentPane().add(objeto,"South");
        pack();
       
        vecTipDoc =  new  java.util.Vector() ; //Vector que contiene el codigo de tipos de documentos
        String sqlPrede = "Select distinct doc.co_tipdoc,doc.tx_deslar from tbm_cabtipdoc as doc, tbr_tipdocprg as menu " +
                                        " where   menu.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                                                " menu.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                                                " menu.co_mnu = 14                                      and " +  
                                                " menu.co_tipdoc = doc.co_tipdoc ";        
        objutil.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), 
                                objZafParSis.getClaveBaseDatos(), sqlPrede, cboTipDoc, vecTipDoc);      
        
        
        
        vecLoc =  new  java.util.Vector() ; //Vector que contiene el codigo de tipos de documentos
        String sql = "select co_loc , tx_nom from tbm_loc where co_emp="+objZafParSis.getCodigoEmpresa()+" AND st_reg='P'";
        objutil.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), 
                                objZafParSis.getClaveBaseDatos(), sql, local, vecLoc);      
        
        
        


        
        
        
        /* Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
           NO se altere el formulario y se vea bonito  */
        spnObs1.setPreferredSize(new java.awt.Dimension(350,30));
        
        
        /* Metodo para agregar o eliminar lineas con enter y con escape
         */

        objutil.verDecimalesEnTabla(tblFacDet,7, intNumDec);
        objutil.verDecimalesEnTabla(tblFacDet,8, 2);
        objutil.verDecimalesEnTabla(tblFacDet,10, intNumDec);
        
            
         
            
         
          this.setSize(700, 450);
         
          
           
          
       //  objutil.desactivarCom(this);

         
         butacep.setEnabled(true);
         butCan.setEnabled(true);
         
         //Iniciando el objeto para manejar el inventario
        // objZafInv = new Librerias.ZafInventario.ZafInventario(this, objZafParSis);
         addListenerCambio();
         addLisCamTbls();
         /* 
          * Iniciando clase de informacion de cliente y ciudad
          */
         objCliente_dat = new ZafCliente_dat(objZafParSis);
         objCiudad_dat = new ZafCiudad_dat(objZafParSis);
         objUltDocPrint = new UltDocPrint(objZafParSis);
        // objZafTipItm     = new Librerias.ZafUtil.ZafTipItm(objZafParSis);         
    }

    
    
    
    
    

   private boolean Configurar_tabla() {
        boolean blnRes=false;
        try{
           
            //Configurar JTable: Establecer el modelo.
            Vector vecDat=new Vector();    //Almacena los datos
            Vector vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            
            vecCab.add(INT_TBL_LINEA,"");            
            vecCab.add(INT_TBL_ALTITM,"Cod.Item");
            vecCab.add(INT_TBL_DESITM,"Descripcion");            
            vecCab.add(INT_TBL_UNIDAD,"Unidad");
            vecCab.add(INT_TBL_CODBOD,"Bodega");            
            vecCab.add(INT_TBL_CANMOV,"Cantidad.");
            vecCab.add(INT_TBL_CANDIFDEV,"Cantidad.");
            vecCab.add(INT_TBL_CANDEV,"Devolucion.");
            vecCab.add(INT_TBL_PRECIO,"Precio.");
            vecCab.add(INT_TBL_PREDEV,"Can.Pre.");
            vecCab.add(INT_TBL_PORDES,"%Desc.");
            vecCab.add(INT_TBL_CANDES,"Can.Desc.");
            vecCab.add(INT_TBL_IVAITM,"IVA");
            vecCab.add(INT_TBL_TOTAL,"Total"); 
            vecCab.add(INT_TBL_CODITM,"");
            vecCab.add(INT_TBL_ALTITM2,"");
            vecCab.add(INT_TBL_ITMSER,"");
            vecCab.add(INT_TBL_COSTO,"");            
            vecCab.add(INT_TBL_DESORI,"");   
           
            vecCab.add(INT_TBL_NOMPRV,"Proveedor.");   
            vecCab.add(INT_TBL_CODLOC,"codloc"); 
            vecCab.add(INT_TBL_CODTIP,"tipdoc"); 
            vecCab.add(INT_TBL_CODDOC,"coddoc"); 
            vecCab.add(INT_TBL_CODREG,"codreg"); 
            vecCab.add(INT_TBL_CODREGDEV, "codregdev");
          
  
    
            
            
            
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);

            tblFacDet.setModel(objTblMod);   
            //Configurar JTable: Establecer tipo de selección.
            tblFacDet.setRowSelectionAllowed(false);
            tblFacDet.setCellSelectionEnabled(true);
            tblFacDet.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
             
            //Configurar JTable: Establecer la fila de cabecera.
            new Librerias.ZafColNumerada.ZafColNumerada(tblFacDet,INT_TBL_LINEA);
              
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_CANDEV, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_COSTO, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PORDES, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANDES, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PREDEV, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_TOTAL, objTblMod.INT_COL_DBL, new Integer(0), null);
           
            
            
            
            
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_CANDES);
            arlAux.add("" + INT_TBL_CANDEV);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());
                  

            //Configurar JTable: Establecer el menú de contexto.
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblFacDet.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblFacDet.getColumnModel();
            //Configurar JTable: Establecer el ancho de las columnas.
            
            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(10);         
            tcmAux.getColumn(INT_TBL_ALTITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DESITM).setPreferredWidth(160);
            tcmAux.getColumn(INT_TBL_UNIDAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(30);         
          
            tcmAux.getColumn(INT_TBL_IVAITM).setPreferredWidth(25);   
            tcmAux.getColumn(INT_TBL_CANMOV).setPreferredWidth(50);         
            tcmAux.getColumn(INT_TBL_CANDEV).setPreferredWidth(50);        
            
            tcmAux.getColumn(INT_TBL_PRECIO).setPreferredWidth(50);      
            
            tcmAux.getColumn(INT_TBL_COSTO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PORDES).setPreferredWidth(50);         
            tcmAux.getColumn(INT_TBL_CANDES).setPreferredWidth(50);         
            tcmAux.getColumn(INT_TBL_TOTAL).setPreferredWidth(80);         
              
            tcmAux.getColumn(INT_TBL_NOMPRV).setPreferredWidth(150);         
            

            ocultaColumna(tcmAux, INT_TBL_ALTITM2);      
            ocultaColumna(tcmAux, INT_TBL_CANDES);      
            ocultaColumna(tcmAux, INT_TBL_PREDEV);      
            ocultaColumna(tcmAux, INT_TBL_CANDIFDEV);      
            ocultaColumna(tcmAux, INT_TBL_CODITM);           
            ocultaColumna(tcmAux, INT_TBL_ITMSER);           
            ocultaColumna(tcmAux, INT_TBL_COSTO);           
            ocultaColumna(tcmAux, INT_TBL_DESORI);
           // ocultaColumna(tcmAux, INT_TBL_NOMPRV);
            
            ocultaColumna(tcmAux, INT_TBL_CODLOC);
            ocultaColumna(tcmAux, INT_TBL_CODTIP);
            ocultaColumna(tcmAux ,INT_TBL_CODDOC);
            ocultaColumna(tcmAux ,INT_TBL_CODREG);
            ocultaColumna(tcmAux ,INT_TBL_CODREGDEV);
           
            
            tblFacDet.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);                                        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.            
            tblFacDet.getTableHeader().setReorderingAllowed(false);            
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_CANDEV);
            vecAux.add("" + INT_TBL_CANDES); 
            vecAux.add("" + INT_TBL_PREDEV);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblFacDet);
            //Configurar JTable: Renderizar celdas.
             

            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl=null;
                       
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico("######",true,true);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_CANDEV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PORDES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANDES).setCellRenderer(objTblCelRenLbl); 
            tcmAux.getColumn(INT_TBL_PREDEV).setCellRenderer(objTblCelRenLbl); 
            tcmAux.getColumn(INT_TBL_COSTO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_TOTAL).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
           
           
         
            
          
            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblFacDet);
            tcmAux.getColumn(INT_TBL_CANDES).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_CANDEV).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_PREDEV).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    strBeforeValue = "0.00";
                     if (tblFacDet.getValueAt(tblFacDet.getSelectedRow(),INT_TBL_CODITM)!=null){  
                        if (tblFacDet.getValueAt(tblFacDet.getSelectedRow(),tblFacDet.getSelectedColumn())!=null)
                           if (!tblFacDet.getValueAt(tblFacDet.getSelectedRow(),tblFacDet.getSelectedColumn()).equals(""))
                             strBeforeValue = tblFacDet.getValueAt(tblFacDet.getSelectedRow(),tblFacDet.getSelectedColumn()).toString();
                           else
                                strBeforeValue = "0.00";
                     }
                }                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    
                    if (tblFacDet.getValueAt(tblFacDet.getSelectedRow(),INT_TBL_CODITM)!=null){ 
                        double dlbCanMov = Double.parseDouble(tblFacDet.getValueAt(tblFacDet.getSelectedRow(),INT_TBL_CANMOV).toString());
                      
                        double dlbCanPre = Double.parseDouble(tblFacDet.getValueAt(tblFacDet.getSelectedRow(),INT_TBL_PRECIO).toString());
                      
                          
                        if (tblFacDet.getValueAt(tblFacDet.getSelectedRow(),INT_TBL_CODITM)!=null)
                            strAfterValue = tblFacDet.getValueAt(tblFacDet.getSelectedRow(),tblFacDet.getSelectedColumn()).toString();
                          else
                              strAfterValue ="";
               
                    
                    if (tblFacDet.getSelectedColumn()==INT_TBL_CANDES){
                        if(strAfterValue.equals("")) strAfterValue="0.00"; 
                        if ( Double.parseDouble(strAfterValue) >100 || Double.parseDouble(strAfterValue) <0 ){
                            MensajeInf("Error! Porcentaje de Descuento ");
                            tblFacDet.setValueAt(strBeforeValue, tblFacDet.getSelectedRow(), INT_TBL_CANDES);
                        }
                    }
                     
                    if (tblFacDet.getSelectedColumn()==INT_TBL_CANDEV){
                        if(strAfterValue.equals("")) strAfterValue="0.00"; 
                        if ( Double.parseDouble(strAfterValue) > dlbCanMov ){
                            MensajeInf("Error! La cantidad a devolver no puede ser mayor a "+dlbCanMov );
                            tblFacDet.setValueAt(strBeforeValue, tblFacDet.getSelectedRow(), INT_TBL_CANDEV);
                        }
                    }
                     
                    
                    if (tblFacDet.getSelectedColumn()==INT_TBL_PREDEV){
                      if(strAfterValue.equals("")) strAfterValue="0.00"; 
                        if ( Double.parseDouble(strAfterValue) > dlbCanPre ){
                            MensajeInf("Error! La cantidad a devolver no puede ser mayor a "+dlbCanPre );
                            tblFacDet.setValueAt(strBeforeValue, tblFacDet.getSelectedRow(), INT_TBL_PREDEV);
                        }
                    }
                         
                        
                   if(!(strBeforeValue.equals(strAfterValue)))  
                   {    
                       if(item.isSelected())
                          calculaSubtotal();
                         if(desc.isSelected())
                             calculaDescuento();
                            if(val.isSelected())
                               calculaSubValores();
                       
                   }
                    }else { 
                           tblFacDet.setValueAt("", tblFacDet.getSelectedRow(), INT_TBL_CANDEV);
                           tblFacDet.setValueAt("", tblFacDet.getSelectedRow(), INT_TBL_CANDES);
                           tblFacDet.setValueAt("", tblFacDet.getSelectedRow(), INT_TBL_PREDEV); 
                }      }
            });

           
            //Libero los objetos auxiliares.
            tcmAux=null;
           
            setPredeterminado();
            setEditable(true); 
              
            
            blnRes=true;    
        }catch(Exception e) {
            blnRes=false;    
            objutil.mostrarMsgErr_F1(this,e);
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
   
   
   
   
      private void setPredeterminado(){
                try{
                    
                    
                    java.sql.Statement stmPrede=CONN_GLO.createStatement();
                    java.sql.ResultSet rstPrede;
            
                    
                    String sqlPrede = "Select distinct doc.co_tipdoc,doc.tx_deslar from tbm_cabtipdoc as doc, tbr_tipdocprg as menu " +
                                        " where   menu.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                                                " menu.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                                                " menu.co_mnu = " + intCodMnu                       + " and " +  
                                                " menu.co_tipdoc = doc.co_tipdoc and menu.st_reg = 'S'";
                    
                    rstPrede=stmPrede.executeQuery(sqlPrede);
                    
                    if(rstPrede.next()){
                        txtTipDocCod.setText(((rstPrede.getString("co_tipdoc")==null)?"":rstPrede.getString("co_tipdoc")));
                        txtTipDocNom.setText(((rstPrede.getString("tx_deslar")==null)?"":rstPrede.getString("tx_deslar")));
                        objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objZafParSis,  Integer.parseInt( (txtTipDocCod.getText().equals(""))?"0":txtTipDocCod.getText() ));            
      
                    }
                    
                    
                    sqlPrede = "Select distinct doc.co_tipdoc from tbm_cabtipdoc as doc, tbr_tipdocprg as menu " +
                                        " where   menu.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                                                " menu.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                                                " menu.co_mnu = 14                                      and " +  
                                                " menu.co_tipdoc = doc.co_tipdoc and menu.st_reg = 'S'";
                    rstPrede=stmPrede.executeQuery(sqlPrede);
                    
                    if(rstPrede.next()){
                       cboTipDoc.setSelectedIndex(vecTipDoc.indexOf("" + rstPrede.getInt("co_tipdoc")));
                    }
                        
                    
                     local.setSelectedIndex(0);
                    
                    stmPrede.close();
                    rstPrede.close();
                }
               catch(SQLException Evt)
               {
                      objutil.mostrarMsgErr_F1(jfrThis, Evt);

                }

                catch(Exception Evt)
                {
                      objutil.mostrarMsgErr_F1(jfrThis, Evt);
                }                       
                
            }
   
   
   
   
   
   
   
   
   private void ocultaColumna(javax.swing.table.TableColumnModel tcmAux, int Columna){
        tcmAux.getColumn(Columna).setWidth(0);
        tcmAux.getColumn(Columna).setMaxWidth(0);
        tcmAux.getColumn(Columna).setMinWidth(0);
        tcmAux.getColumn(Columna).setPreferredWidth(0);
   }
   
   
    
      private void MensajeInf(String strMensaje){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit;
            strTit="Mensaje del sistema Zafiro";
            obj.showMessageDialog(jfrThis,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
      
    
public void ExitForm(){
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 )
        {   
          
            dispose();
            
        }       
}
    

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Grupo1 = new javax.swing.ButtonGroup();
        tabCotCom = new javax.swing.JTabbedPane();
        panCotGen = new javax.swing.JPanel();
        panCotGenNor = new javax.swing.JPanel();
        txtCliDir = new javax.swing.JTextField();
        lblCli = new javax.swing.JLabel();
        lblDir = new javax.swing.JLabel();
        txtCliCod = new javax.swing.JTextField();
        txtCliNom = new javax.swing.JTextField();
        txtAte = new javax.swing.JTextField();
        lblAte = new javax.swing.JLabel();
        txtDoc = new javax.swing.JTextField();
        lblDoc = new javax.swing.JLabel();
        txtDev = new javax.swing.JTextField();
        lblDev = new javax.swing.JLabel();
        txtVenNom = new javax.swing.JTextField();
        txtVenCod = new javax.swing.JTextField();
        lblVen = new javax.swing.JLabel();
        lblFecDoc = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTipDocCod = new javax.swing.JTextField();
        txtTipDocNom = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblFromFac = new javax.swing.JLabel();
        txtFromFac = new javax.swing.JTextField();
        txtFecFac = new javax.swing.JTextField();
        lblFecFac = new javax.swing.JLabel();
        cboTipDoc = new javax.swing.JComboBox();
        item = new javax.swing.JRadioButton();
        desc = new javax.swing.JRadioButton();
        val = new javax.swing.JRadioButton();
        local = new javax.swing.JComboBox();
        spnCon = new javax.swing.JScrollPane();
        tblFacDet = new javax.swing.JTable();
        panCotGenSur = new javax.swing.JPanel();
        panCotGenSurCen = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblObs2 = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panCotGenSurEst = new javax.swing.JPanel();
        lblSubTot = new javax.swing.JLabel();
        txtSub = new javax.swing.JTextField();
        lblIva = new javax.swing.JLabel();
        txtIva = new javax.swing.JTextField();
        lblTot = new javax.swing.JLabel();
        txtTot = new javax.swing.JTextField();
        panAsiento = new javax.swing.JPanel();
        panCotNor = new javax.swing.JPanel();
        lblFacNumDes = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        butCan = new javax.swing.JButton();
        butacep = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        tabCotCom.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabCotCom.setName("General"); // NOI18N

        panCotGen.setLayout(new java.awt.BorderLayout());

        panCotGenNor.setPreferredSize(new java.awt.Dimension(800, 135));
        panCotGenNor.setLayout(null);

        txtCliDir.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtCliDir.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNor.add(txtCliDir);
        txtCliDir.setBounds(104, 108, 328, 20);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCli.setText("Cliente:");
        panCotGenNor.add(lblCli);
        lblCli.setBounds(8, 86, 72, 15);

        lblDir.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDir.setText("Dirección:");
        panCotGenNor.add(lblDir);
        lblDir.setBounds(8, 106, 60, 15);

        txtCliCod.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCliCod.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtCliCod.setMinimumSize(new java.awt.Dimension(0, 0));
        txtCliCod.setPreferredSize(new java.awt.Dimension(25, 20));
        panCotGenNor.add(txtCliCod);
        txtCliCod.setBounds(104, 88, 35, 20);

        txtCliNom.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCliNom.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtCliNom.setPreferredSize(new java.awt.Dimension(100, 20));
        panCotGenNor.add(txtCliNom);
        txtCliNom.setBounds(140, 88, 292, 20);
        panCotGenNor.add(txtAte);
        txtAte.setBounds(532, 68, 146, 20);

        lblAte.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblAte.setText("Atención:");
        lblAte.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNor.add(lblAte);
        lblAte.setBounds(468, 72, 60, 15);

        txtDoc.setBackground(objZafParSis.getColorCamposSistema()
        );
        objZafParSis.getColorCamposObligatorios();
        txtDoc.setMaximumSize(null);
        txtDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocActionPerformed(evt);
            }
        });
        panCotGenNor.add(txtDoc);
        txtDoc.setBounds(104, 48, 92, 20);

        lblDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDoc.setText("No. Documento:");
        panCotGenNor.add(lblDoc);
        lblDoc.setBounds(8, 48, 110, 15);

        txtDev.setBackground(objZafParSis.getColorCamposObligatorios()
        );
        txtDev.setMaximumSize(null);
        txtDev.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNor.add(txtDev);
        txtDev.setBounds(104, 68, 92, 20);

        lblDev.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDev.setText("No. Devolucion:");
        panCotGenNor.add(lblDev);
        lblDev.setBounds(8, 66, 110, 15);

        txtVenNom.setBackground(objZafParSis.getColorCamposObligatorios());
        txtVenNom.setPreferredSize(new java.awt.Dimension(100, 20));
        panCotGenNor.add(txtVenNom);
        txtVenNom.setBounds(568, 48, 110, 20);

        txtVenCod.setBackground(objZafParSis.getColorCamposObligatorios());
        txtVenCod.setMinimumSize(new java.awt.Dimension(0, 0));
        txtVenCod.setPreferredSize(new java.awt.Dimension(25, 20));
        panCotGenNor.add(txtVenCod);
        txtVenCod.setBounds(532, 48, 35, 20);

        lblVen.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblVen.setText("Vendedor:");
        lblVen.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNor.add(lblVen);
        lblVen.setBounds(468, 52, 70, 15);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecDoc.setText("Fecha documento:");
        lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panCotGenNor.add(lblFecDoc);
        lblFecDoc.setBounds(464, 8, 100, 15);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel2.setText("Tipo Documento:");
        panCotGenNor.add(jLabel2);
        jLabel2.setBounds(8, 8, 100, 15);

        txtTipDocCod.setBackground(objZafParSis.getColorCamposObligatorios());
        txtTipDocCod.setMinimumSize(new java.awt.Dimension(0, 0));
        txtTipDocCod.setPreferredSize(new java.awt.Dimension(25, 20));
        txtTipDocCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipDocCodActionPerformed(evt);
            }
        });
        txtTipDocCod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTipDocCodFocusLost(evt);
            }
        });
        panCotGenNor.add(txtTipDocCod);
        txtTipDocCod.setBounds(104, 8, 35, 20);

        txtTipDocNom.setBackground(objZafParSis.getColorCamposObligatorios());
        txtTipDocNom.setPreferredSize(new java.awt.Dimension(100, 20));
        txtTipDocNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipDocNomActionPerformed(evt);
            }
        });
        panCotGenNor.add(txtTipDocNom);
        txtTipDocNom.setBounds(140, 8, 188, 20);

        butTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        butTipDoc.setText("...");
        butTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCotGenNor.add(butTipDoc);
        butTipDoc.setBounds(328, 8, 20, 20);

        lblFromFac.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFromFac.setText("No Factura");
        panCotGenNor.add(lblFromFac);
        lblFromFac.setBounds(8, 30, 92, 15);

        txtFromFac.setMaximumSize(null);
        txtFromFac.setPreferredSize(new java.awt.Dimension(70, 20));
        txtFromFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFromFacActionPerformed(evt);
            }
        });
        panCotGenNor.add(txtFromFac);
        txtFromFac.setBounds(104, 28, 92, 20);

        txtFecFac.setMaximumSize(null);
        txtFecFac.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNor.add(txtFecFac);
        txtFecFac.setBounds(568, 28, 110, 20);

        lblFecFac.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecFac.setText("Fecha de Factura:");
        panCotGenNor.add(lblFecFac);
        lblFecFac.setBounds(468, 32, 100, 15);

        cboTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        cboTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipDocActionPerformed(evt);
            }
        });
        panCotGenNor.add(cboTipDoc);
        cboTipDoc.setBounds(196, 28, 120, 20);

        Grupo1.add(item);
        item.setFont(new java.awt.Font("SansSerif", 0, 11));
        item.setSelected(true);
        item.setText("Por Item");
        item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemActionPerformed(evt);
            }
        });
        panCotGenNor.add(item);
        item.setBounds(448, 96, 63, 23);

        Grupo1.add(desc);
        desc.setFont(new java.awt.Font("SansSerif", 0, 11));
        desc.setText("Descuento");
        desc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descActionPerformed(evt);
            }
        });
        panCotGenNor.add(desc);
        desc.setBounds(516, 96, 80, 23);

        Grupo1.add(val);
        val.setFont(new java.awt.Font("SansSerif", 0, 11));
        val.setText("Valores");
        val.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valActionPerformed(evt);
            }
        });
        panCotGenNor.add(val);
        val.setBounds(604, 96, 84, 23);

        local.setFont(new java.awt.Font("SansSerif", 0, 11));
        panCotGenNor.add(local);
        local.setBounds(320, 28, 120, 20);

        panCotGen.add(panCotGenNor, java.awt.BorderLayout.NORTH);

        spnCon.setPreferredSize(new java.awt.Dimension(453, 418));

        tblFacDet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "Codigo", "Descripción", "Unidad", "Bodega", "Cantidad", "Devolucion", "Precio", "%Desc", "Iva", "Total", "co_itm", "codigo2", "Item_ser", "costo", "DescOri"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false, true, false, false, false, false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblFacDet.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblFacDet.setCellSelectionEnabled(true);
        spnCon.setViewportView(tblFacDet);

        panCotGen.add(spnCon, java.awt.BorderLayout.CENTER);

        panCotGenSur.setLayout(new java.awt.BorderLayout());

        panCotGenSurCen.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.GridLayout(2, 1));

        jPanel4.setLayout(new java.awt.BorderLayout());

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs2.setText("Observación 1:");
        jPanel4.add(lblObs2, java.awt.BorderLayout.WEST);

        spnObs1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spnObs1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        txaObs1.setLineWrap(true);
        txaObs1.setWrapStyleWord(true);
        spnObs1.setViewportView(txaObs1);

        jPanel4.add(spnObs1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel4);

        jPanel3.setPreferredSize(new java.awt.Dimension(250, 25));
        jPanel3.setLayout(new java.awt.BorderLayout());

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs1.setText("Observación 2:");
        jPanel3.add(lblObs1, java.awt.BorderLayout.WEST);

        spnObs2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spnObs2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        txaObs2.setLineWrap(true);
        txaObs2.setWrapStyleWord(true);
        spnObs2.setViewportView(txaObs2);

        jPanel3.add(spnObs2, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel3);

        panCotGenSurCen.add(jPanel5, java.awt.BorderLayout.CENTER);

        panCotGenSur.add(panCotGenSurCen, java.awt.BorderLayout.CENTER);

        panCotGenSurEst.setLayout(new java.awt.GridLayout(3, 2));

        lblSubTot.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblSubTot.setText("SubTotal:");
        lblSubTot.setPreferredSize(new java.awt.Dimension(90, 14));
        panCotGenSurEst.add(lblSubTot);

        txtSub.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCotGenSurEst.add(txtSub);

        lblIva.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblIva.setText("IVA 12%:");
        lblIva.setPreferredSize(new java.awt.Dimension(60, 14));
        panCotGenSurEst.add(lblIva);

        txtIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCotGenSurEst.add(txtIva);

        lblTot.setFont(new java.awt.Font("SansSerif", 1, 12));
        lblTot.setText("Total:");
        lblTot.setPreferredSize(new java.awt.Dimension(60, 14));
        panCotGenSurEst.add(lblTot);

        txtTot.setFont(new java.awt.Font("Arial", 1, 12));
        txtTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCotGenSurEst.add(txtTot);

        panCotGenSur.add(panCotGenSurEst, java.awt.BorderLayout.EAST);

        panCotGen.add(panCotGenSur, java.awt.BorderLayout.SOUTH);

        tabCotCom.addTab("tab1", panCotGen);

        panAsiento.setLayout(new java.awt.BorderLayout());
        tabCotCom.addTab("tab4", panAsiento);

        getContentPane().add(tabCotCom, java.awt.BorderLayout.CENTER);

        lblFacNumDes.setText("Devolución");
        lblFacNumDes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblFacNumDes.setOpaque(true);
        panCotNor.add(lblFacNumDes);

        getContentPane().add(panCotNor, java.awt.BorderLayout.NORTH);

        jPanel1.setMinimumSize(new java.awt.Dimension(40, 10));
        jPanel1.setPreferredSize(new java.awt.Dimension(60, 45));
        jPanel1.setLayout(new java.awt.BorderLayout());

        butCan.setText("Cancelar.");
        butCan.setActionCommand("Cerrar");
        butCan.setMaximumSize(new java.awt.Dimension(100, 25));
        butCan.setPreferredSize(new java.awt.Dimension(100, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        jPanel2.add(butCan);

        butacep.setText("Aceptar");
        butacep.setPreferredSize(new java.awt.Dimension(90, 25));
        butacep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butacepActionPerformed(evt);
            }
        });
        jPanel2.add(butacep);

        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-554)/2, (screenSize.height-439)/2, 554, 439);
    }// </editor-fold>//GEN-END:initComponents

    private void butacepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butacepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_butacepActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        Configurar_tabla();   
        
        consultarFac();
        
    }//GEN-LAST:event_formWindowOpened

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
        
          cerrarVentana(); 
        
    }//GEN-LAST:event_butCanActionPerformed

    
    
     private void cerrarVentana(){
        String strMsg = "¿Está Seguro que desea cancelar el proceso de Facturación.?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 )
        {   
              blnAcepta = true;
              blnEst=false; 
              
           cerrarObj(); 
           System.gc(); 
           dispose(); 
        }    
    }
    
     
      public boolean acepta(){
        return blnEst;
     }
    
       
          public String txtCodDoc(){
                return strCodDocDev;
            } 
    
       
           public String txtNumDoc(){
                return strCodNumDev;
            } 
     
      private void cerrarVentana2(){
          
           
           cerrarObj(); 
           System.gc(); 
           dispose(); 
     }
    
     
     
      public void cerrarObj(){
        try{
         objutil=null;
         objCiudad_dat=null;
         objInvItm=null;
         objZafParSis=null;
         txtFecDoc=null;
        
         objUltDocPrint=null;
        }
         catch (Exception e)
         {
          objutil.mostrarMsgErr_F1(this, e);
         }
        
        
       
     }
    
     
     
    private void valActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valActionPerformed
        // TODO add your handling code here:
        if(intEstDev3==1){
          ocultaCol(INT_TBL_CANDEV) ; 
          ocultaCol(INT_TBL_CANDES) ; 
          MostrarCol(INT_TBL_PREDEV) ;
          
           ocultaCol(INT_TBL_CANMOV) ;
           MostrarCol(INT_TBL_CANDIFDEV) ; 
          
          LimpiarDatos();
          intEstDev1=1;
          intEstDev2=1;
          intEstDev3=0;
       } 
    }//GEN-LAST:event_valActionPerformed

    private void itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemActionPerformed
        // TODO add your handling code here:
        if(intEstDev1==1){
         ocultaCol(INT_TBL_CANDES) ;
         ocultaCol(INT_TBL_PREDEV) ;    
         MostrarCol(INT_TBL_CANDEV) ; 
         
         
         ocultaCol(INT_TBL_CANDIFDEV) ;
         MostrarCol(INT_TBL_CANMOV) ; 
         
         LimpiarDatos();
         intEstDev1=0;
         intEstDev2=1;
         intEstDev3=1;
         
        }  
    }//GEN-LAST:event_itemActionPerformed

    private void descActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descActionPerformed
        // TODO add your handling code here:
       if(intEstDev2==1){
         ocultaCol(INT_TBL_CANDEV) ;
         ocultaCol(INT_TBL_PREDEV) ;     
         MostrarCol(INT_TBL_CANDES) ; 
         
         ocultaCol(INT_TBL_CANMOV) ;
         MostrarCol(INT_TBL_CANDIFDEV) ; 
         
         LimpiarDatos();
         intEstDev1=1;
         intEstDev2=0;
         intEstDev3=1;
       }   
    }//GEN-LAST:event_descActionPerformed

    
    private void MostrarCol(int intCol){ 
              tblFacDet.getColumnModel().getColumn(intCol).setWidth(65);
              tblFacDet.getColumnModel().getColumn(intCol).setMaxWidth(65);
              tblFacDet.getColumnModel().getColumn(intCol).setMinWidth(65);
              tblFacDet.getColumnModel().getColumn(intCol).setPreferredWidth(65);
              tblFacDet.getColumnModel().getColumn(intCol).setResizable(false); 
     }
    
     private void ocultaCol(int intCol){
              tblFacDet.getColumnModel().getColumn(intCol).setWidth(0);
              tblFacDet.getColumnModel().getColumn(intCol).setMaxWidth(0);
              tblFacDet.getColumnModel().getColumn(intCol).setMinWidth(0);
              tblFacDet.getColumnModel().getColumn(intCol).setPreferredWidth(0);
              tblFacDet.getColumnModel().getColumn(intCol).setResizable(false); 
     }
     
    
     private void LimpiarDatos(){
           for(int i=0; i<tblFacDet.getRowCount();i++){
               tblFacDet.setValueAt("", i, INT_TBL_CANDEV);
               tblFacDet.setValueAt("", i, INT_TBL_CANDES);    
               tblFacDet.setValueAt("", i, INT_TBL_PREDEV);    
               tblFacDet.setValueAt("", i, INT_TBL_TOTAL);     
           }
         txtSub.setText("0.00");
         txtIva.setText("0.00");
         txtTot.setText("0.00");
         objAsiDia.inicializar(); 
     }
    
     
    private void cboTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTipDocActionPerformed

    private void txtDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDocActionPerformed

    private void txtTipDocCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipDocCodActionPerformed
        // TODO add your handling code here:
       Librerias.ZafConsulta.ZafConsulta  objFndTipDoc = 
        new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(jfrThis),
           "Codigo,Alias,Descripcion","doc.co_tipdoc,doc.tx_descor,doc.tx_deslar",
             "Select distinct doc.co_tipdoc,doc.tx_descor,doc.tx_deslar from tbm_cabtipdoc as doc, tbr_tipdocprg as menu " +
                " where   menu.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                        " menu.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                        " menu.co_mnu = " + intCodMnu                       + " and " +  
                        " menu.co_tipdoc = doc.co_tipdoc"   ,"", 
         objZafParSis.getStringConexion(), 
         objZafParSis.getUsuarioBaseDatos(), 
         objZafParSis.getClaveBaseDatos()
         );        

        objFndTipDoc.setTitle("Listado Tipos de Documentos");
        
         if(txtTipDocCod.getText().equals("")){
            objFndTipDoc.show();
         }else{
             if(objutil.isNumero(txtTipDocCod.getText())){
                if(!objFndTipDoc.buscar("doc.co_tipdoc = " + txtTipDocCod.getText()))
                    objFndTipDoc.show();
             }
             else{
                txtTipDocCod.setText("");
                objFndTipDoc.setSelectedCamBus(1);
                objFndTipDoc.show();
             }
         }
        if(!objFndTipDoc.GetCamSel(1).equals("")){
            txtTipDocCod.setText(objFndTipDoc.GetCamSel(1));
            txtTipDocNom.setText(objFndTipDoc.GetCamSel(3));
           objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objZafParSis,  Integer.parseInt( (txtTipDocCod.getText().equals(""))?"0":txtTipDocCod.getText() ));            
      
          //  cargaNum_Doc_Preimpreso();
        }
    }//GEN-LAST:event_txtTipDocCodActionPerformed

    private void txtTipDocCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTipDocCodFocusLost
        // Se busca el codigo y si no existe se borra lo escrito

       Librerias.ZafConsulta.ZafConsulta  objFndTipDoc = 
        new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(jfrThis),
           "Codigo,Alias,Descripcion","tbm_cabtipdoc.co_tipdoc,tbm_cabtipdoc.tx_descor,tbm_cabtipdoc.tx_deslar",
             "Select distinct doc.co_tipdoc,doc.tx_descor,doc.tx_deslar from tbm_cabtipdoc as doc, tbr_tipdocprg as menu " +
                " where   menu.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                        " menu.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                        " menu.co_mnu = " + intCodMnu                       + " and " +  
                        " menu.co_tipdoc = doc.co_tipdoc"   ,txtTipDocCod.getText(), 
         objZafParSis.getStringConexion(), 
         objZafParSis.getUsuarioBaseDatos(), 
         objZafParSis.getClaveBaseDatos()
         );        
         objFndTipDoc.setTitle("Listado Tipos de Documentos");

         if(!txtTipDocCod.getText().equals(""))
            if(objutil.isNumero(txtTipDocCod.getText())) {
                 if(objFndTipDoc.buscar("doc.co_tipdoc = " + txtTipDocCod.getText())){
                    txtTipDocCod.setText(objFndTipDoc.GetCamSel(1));
                    txtTipDocNom.setText(objFndTipDoc.GetCamSel(3));
                   //  cargaNum_Doc_Preimpreso();
                    }
                 else{
                    txtTipDocCod.setText("");
                    txtTipDocNom.setText("");
                 }
            }else{
                    txtTipDocCod.setText("");
                    txtTipDocNom.setText("");
            }
    }//GEN-LAST:event_txtTipDocCodFocusLost

    private void txtTipDocNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipDocNomActionPerformed
        // BUscando los que contengan lo que se digito en DEscripcion de Tipo de cliente
       Librerias.ZafConsulta.ZafConsulta  objFndTipDoc = 
        new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(jfrThis),
           "Codigo,Alias,Descripcion","tbm_cabtipdoc.co_tipdoc,tbm_cabtipdoc.tx_descor,tbm_cabtipdoc.tx_deslar",
             "Select distinct doc.co_tipdoc,doc.tx_descor,doc.tx_deslar from tbm_cabtipdoc as doc, tbr_tipdocprg as menu " +
                " where   menu.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                        " menu.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                        " menu.co_mnu = " + intCodMnu                       + " and " +  
                        " menu.co_tipdoc = doc.co_tipdoc"   ,txtTipDocNom.getText(), 
         objZafParSis.getStringConexion(), 
         objZafParSis.getUsuarioBaseDatos(), 
         objZafParSis.getClaveBaseDatos()
         );        
        
         objFndTipDoc.setSelectedCamBus(2);

         if(txtTipDocNom.getText().equals(""))
            objFndTipDoc.show();
         else
             if(!objFndTipDoc.buscar("tx_deslar = '" + txtTipDocNom.getText() + "'"))
                objFndTipDoc.show();

        if(!objFndTipDoc.GetCamSel(1).equals("")){
            txtTipDocCod.setText(objFndTipDoc.GetCamSel(1));
            txtTipDocNom.setText(objFndTipDoc.GetCamSel(3));
              objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objZafParSis,  Integer.parseInt( (txtTipDocCod.getText().equals(""))?"0":txtTipDocCod.getText() ));            
      
        }
    }//GEN-LAST:event_txtTipDocNomActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
       Librerias.ZafConsulta.ZafConsulta  objFndTipDoc = 
        new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(jfrThis),
           "Codigo,Alias,Descripcion","tbm_cabtipdoc.co_tipdoc,tbm_cabtipdoc.tx_descor,tbm_cabtipdoc.tx_deslar",
             "Select distinct doc.co_tipdoc,doc.tx_descor,doc.tx_deslar from tbm_cabtipdoc as doc, tbr_tipdocprg as menu " +
                " where   menu.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                        " menu.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                        " menu.co_mnu = " + intCodMnu                       + " and " +  
                        " menu.co_tipdoc = doc.co_tipdoc"   ,"", 
         objZafParSis.getStringConexion(), 
         objZafParSis.getUsuarioBaseDatos(), 
         objZafParSis.getClaveBaseDatos()
         );        
         
       
        objFndTipDoc.setTitle("Listado Tipos de Documentos");
        objFndTipDoc.show();

        if(!objFndTipDoc.GetCamSel(1).equals("")){
            txtTipDocCod.setText(objFndTipDoc.GetCamSel(1));
            txtTipDocNom.setText(objFndTipDoc.GetCamSel(3));
           objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objZafParSis,  Integer.parseInt( (txtTipDocCod.getText().equals(""))?"0":txtTipDocCod.getText() ));            
      
        }        
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtFromFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFromFacActionPerformed
        // TODO add your handling code here:
       
            
    }//GEN-LAST:event_txtFromFacActionPerformed
    
    
    
    private void consultarFac(){
        Statement  stmCot;
        ResultSet rstCotCab;
        try{
          if (CONN_GLO!=null){
               stmCot=CONN_GLO.createStatement();

                sSQL= "SELECT Cab.co_cli, Cab.tx_nomcli as nomcli, Cab.tx_dircli as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                " Cab.co_com as co_ven, Cab.tx_nomven as nomven, Cab.co_doc, Cab.fe_doc,  Cab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                " Cab.co_forPag, Cab.tx_obs1, Cab.tx_obs2, Cab.nd_sub, Cab.nd_porIva, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                " Cab.st_reg , Cab.ne_numdoc, cli.st_ivaven " + // Campo para saber si esta anulado o no
                " FROM tbm_cabMovInv as Cab" + // Tablas enlas cuales se trabajara y sus respectivos alias
                " inner join tbm_cli as cli on (cli.co_emp=Cab.co_emp and cli.co_cli=Cab.co_cli) "+
                " Where Cab.co_emp = "+objZafParSis.getCodigoEmpresa() + 
                " and Cab.co_loc ="+intCodLocFac+" and Cab.co_doc = "+strCodDocFac+ 
                " and Cab.co_tipdoc = "+strCodTipDocFac+" and Cab.st_reg not in ('I','E')";
                rstCotCab=stmCot.executeQuery(sSQL);
                if(rstCotCab.next()){
                 
                 if(cargaFactura(rstCotCab)){
                  if(insertar()){
                     strCodDocDev=txtDoc.getText();
                     blnEst=true;
                     cerrarVentana2(); 
                   }else 
                     blnEst=false;
                 }
                 
                }else{
                       javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                       String strTit, strMsg;
                       strTit="Mensaje del sistema Zafiro";
                       strMsg="El documento esta anulado, pendiente de autorización o no existe." ;
                       javax.swing.JOptionPane.showMessageDialog(javax.swing.JOptionPane.getFrameForComponent(this),strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);                            
                       clnTextos();
                }
                rstCotCab.close();
                rstCotCab=null;
                stmCot.close();
                stmCot=null;
                
       }}catch(SQLException Evt) {  objutil.mostrarMsgErr_F1(jfrThis, Evt); }
         catch(Exception Evt) {  objutil.mostrarMsgErr_F1(jfrThis, Evt); }
  }
    
    
    
    
    
    
        private void MensajeValidaCampo(String strNomCampo){
                    javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
                    obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
    
    
    private boolean validaCampos(){
               if(txtTipDocCod.getText().equals("") ){
                   tabCotCom.setSelectedIndex(0);                  
                   MensajeValidaCampo("Tipo de Documento");
                   txtTipDocCod.requestFocus();
                   return false;
               }
               if(!txtFecDoc.isFecha()){
                   tabCotCom.setSelectedIndex(0);                  
                   MensajeValidaCampo("Fecha de CotizaciÃ³n");
                   txtFecDoc.requestFocus();
                   return false;
               }

               /*
                * VAlidando los campos ke deben ser numericos
                */
               if(!txtFromFac.getText().equals(""))
                   if(!objutil.isNumero(txtFromFac.getText())){
                       tabCotCom.setSelectedIndex(0);                  
                       MensajeValidaCampo("Cotizacion");
                       txtFromFac.requestFocus();
                       return false;
                }
               
               if(!txtCliCod.getText().equals(""))
                   objCliente_dat.setCliente(Integer.parseInt( txtCliCod.getText()  ));
               else
                   objCliente_dat = new ZafCliente_dat(objZafParSis);               

               //******************************************************************************
               //******************************************************************************
                for(int intRowVal=0; intRowVal<tblFacDet.getRowCount(); intRowVal++){
                  
                    if(tblFacDet.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                    if(tblFacDet.getValueAt(intRowVal,INT_TBL_CANMOV) != null ){
                        if(Double.parseDouble(tblFacDet.getValueAt(intRowVal,INT_TBL_CANMOV).toString()) > 0.00  ){
                   
                     double dblCantFac = Double.parseDouble(tblFacDet.getValueAt(intRowVal,5).toString());
                     double dblCant   =  Double.parseDouble(tblFacDet.getValueAt(intRowVal,INT_TBL_CANMOV).toString());     
                        
                      if(dblCant>dblCantFac){
                    javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="El valor debe estar entre 0 y "+dblCantFac ;
                    javax.swing.JOptionPane.showMessageDialog(javax.swing.JOptionPane.getFrameForComponent(this),strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    tblFacDet.repaint();    
                    tblFacDet.requestFocus();
                    tblFacDet.editCellAt(intRowVal, INT_TBL_CANMOV);
                    return false; 
                      }
                     
                     double dblPordes = Double.parseDouble((tblFacDet.getValueAt(intRowVal, INT_TBL_PORDES)==null)?"0":tblFacDet.getValueAt(intRowVal, INT_TBL_PORDES).toString());
                     
                    if(dblPordes >= 100.00 ){
                        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                        String strTit, strMsg;
                        
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="El campo de porcentaje debe de estar entre 0  y 100  Corrija y vuelva a intentarlo. ";
                        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        tblFacDet.repaint();    
                        tblFacDet.requestFocus();
                        tblFacDet.editCellAt(intRowVal, INT_TBL_PORDES);
                        return false; 
                    }
                       
                   }}}
                 }
               //******************************************************************************
               //******************************************************************************
               
               if(!objAsiDia.isDiarioCuadrado()){
                    javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                        String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="EL diario no esta cuadrado, no se puede grabar";
                        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                   return false;
               }
               
               
         return true; 
          }
            
    
    
    
    
         
            public boolean validarCanDev(){
                    try{
                                for(int i=0; i<tblFacDet.getRowCount();i++){
                                        if(tblFacDet.getValueAt(i,INT_TBL_CODITM) != null ){
                                             if(tblFacDet.getValueAt(i, INT_TBL_CANDEV ) != null){
                                                  if(!tblFacDet.getValueAt(i, INT_TBL_CANDEV ).equals("")){
                                                double dblCanMov = Double.parseDouble(tblFacDet.getValueAt(i, INT_TBL_CANDEV).toString());
                                                 if( dblCanMov > 0.00 ){
                                                    
                                                 int intCodItm =  Integer.parseInt((tblFacDet.getValueAt(i, INT_TBL_CODITM).toString().equals(""))?"0":tblFacDet.getValueAt(i, INT_TBL_CODITM).toString()); 
                                                  
                                                     
                                          String  sql = "select sum(b.nd_can) as total from tbr_cabmovinv as a"+ 
                                                      " inner join tbm_cabmovinv as b1 on(a.co_doc=b1.co_doc and a.co_tipdoc=b1.co_tipdoc and b1.st_tipdev not in('P','V') )" +
                                                      " inner join tbm_detmovinv as b on(b1.co_doc=b.co_doc and b1.co_tipdoc=b.co_tipdoc)"+
                                                      " where a.co_docrel="+intNunDocFac+" and a.co_emp="+objZafParSis.getCodigoEmpresa()+" and "+
                                                      " a.co_tipdocrel="+vecTipDoc.elementAt(cboTipDoc.getSelectedIndex())+" and co_itm="+intCodItm+" and a.st_reg='A'";
                                                     stmDocDet=CONN_GLO.createStatement();
                                                     rstDocDet2 = stmDocDet.executeQuery(sql);
                                                     double intNumDev=0; 
                                                     if(rstDocDet2.next()){
                                                         intNumDev = rstDocDet2.getDouble(1); 
                                                     }
                                                     intNumDev=intNumDev+dblCanMov;
                                                     double dblCanOri = Double.parseDouble(tblFacDet.getValueAt(i, 5).toString());
                                                    
                                                     // System.out.println(">>> "+ tblFacDet.getValueAt(i, 1) + " >> " + dblCanOri + " >> " +intNumDev);     
                                                        
                                                      if(intNumDev > dblCanOri ) {
                                                             javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
                                                             String strTit, strMsg;
                                                             strTit="Mensaje del sistema Zafiro";
                                                             strMsg="Este Item  "+ tblFacDet.getValueAt(i, 1) + "  Ya tiene devoluciones y la cantidad a devolver es mayor";
                                                             oppMsg.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
                                                             return false;
                                                      }
                                                 }
                                   }}}
                               }   
                         
                       
                       }
                       catch(SQLException Evt)
                       {    
                            objutil.mostrarMsgErr_F1(jfrThis, Evt);
                            return false;
                        }  
                    return true;
            }
            

            
    
public boolean insertar(){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intCodDocDev=0;
  try{
   if(CONN_GLO!=null){
     stmLoc=CONN_GLO.createStatement();          
     txtsql.setText("");
     
      strSql= "SELECT CASE WHEN (Max(co_doc)+1) is null THEN  1 ELSE (Max(co_doc)+1) END as co_doc  FROM tbm_cabMovInv where " +
      " co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+intCodLocDev+" and co_tipDoc="+txtTipDocCod.getText();
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next())
        intCodDocDev=rstLoc.getInt("co_doc");
      rstLoc.close();
      rstLoc=null;
      
      txtDev.setText(((objUltDocPrint.getUltDoc(Integer.parseInt(txtTipDocCod.getText()), CONN_GLO, intCodLocDev))+1) +"");
      
     if(validaCampos()){
      if(validarCanDev()){ 
       if(insertarCab(intCodDocDev)){    
        if(insertarDet(intCodDocDev)){    
         if(insertarPag(intCodDocDev)){       
          if(objAsiDia.insertarDiario_OC(CONN_GLO,objZafParSis.getCodigoEmpresa(),intCodLocDev, Integer.parseInt(txtTipDocCod.getText()) , String.valueOf(intCodDocDev), txtDev.getText(), objutil.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), 1918 ) ){
                                            
           txtDoc.setText(""+intCodDocDev);
           blnRes=true;
     }}}}}}
      objInvItm.limpiarObjeto();
      stmLoc.close();
      stmLoc=null;
      
    }}catch(SQLException Evt){ blnRes=false; objutil.mostrarMsgErr_F1(jfrThis, Evt); }
      catch(Exception Evt){ blnRes=false; objutil.mostrarMsgErr_F1(jfrThis, Evt); }
    return blnRes;
}
                           
                     
                   
                   
                   
public boolean insertarCab(int intCodDocDev){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strFecSis="";
  String strFecha="";
  String strSql="";
  String strTipDev="C";
  int intSecGrp = 0;
  int intSecEmp = 0;
  try{
   if(CONN_GLO!=null){
       stmLoc=CONN_GLO.createStatement();    
         
        objCiudad_dat.setCiudad(objCliente_dat.getCodciudad());
        dblPorIva = objutil.redondeo(getIva(),2);
        strCodNumDev=txtDev.getText();
                                                         
        int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
        strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                                        
        //******************* OBTENER EL MAX DE SEC GRUPO Y EMPRESA ****//////
//        intSecEmp = objUltDocPrint2.getNumeroOrdenEmpresa(CONN_GLO);
//        intSecGrp = objUltDocPrint2.getNumeroOrdenGrupo(CONN_GLO);
        Glo_intCodSec=intSecGrp;
        //*******************************************************************///

       strFecSis = objutil.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
                                       
       strSql="INSERT INTO  tbm_cabMovInv(co_emp, co_loc, co_doc, co_tipDoc, " + //CAMPOS PrimayKey
       " ne_secemp, fe_doc, ne_secgrp,co_cli, co_com, tx_ate, " +//<==Campos que aparecen en la parte superior del 1er Tab
       " tx_nomCli, tx_dirCli, tx_ruc, tx_telCli, tx_ciuCli, " + //<-- Campos de info. del cliente
       " tx_nomven, ne_numDoc, ne_numCot, " +//<==Campos que aparecen en la parte superior del 1er Tab
       " tx_obs1, tx_obs2, nd_sub,nd_tot,nd_valiva, nd_porIva, " +//<==Campos que aparecen en la parte inferior del 1er Tab
       " fe_ing, co_usrIng, fe_ultMod, co_usrMod ,st_reg ,st_tipdev , st_regrep, st_coninv )" +
       " VALUES ("+objZafParSis.getCodigoEmpresa()+", "+intCodLocDev+", "+intCodDocDev+ ", " + 
        txtTipDocCod.getText()+ ", "+intSecEmp + " ,'" +
        strFecha  + "', "+intSecGrp+", "+txtCliCod.getText()+",  "+
        ((txtVenCod.getText().equals(""))?"null":txtVenCod.getText()) + ",  '" +
        txtAte.getText()    + "', '" +txtCliNom.getText() + "', '" + 
        txtCliDir.getText() + "', '"+objCliente_dat.getIdentificacion() + "', '" + 
        objCliente_dat.getTelefono()+ "', '"+objCiudad_dat.getNombrelargo()+ "', '" + 
        txtVenNom.getText()+ "', "+txtDev.getText()+", "+
        ((txtFromFac.getText().equals(""))?"null":txtFromFac.getText()) + ",  '" +
        txaObs1.getText()   + "', '" +txaObs2.getText()   + "',  " + 
        Double.parseDouble(txtSub.getText())   + ",   " +Double.parseDouble(txtTot.getText())   + ",   " +
        Double.parseDouble(txtIva.getText())   + ",   " +objutil.redondeo(dblPorIva , 2)  + " , " +
        " '"+ strFecSis +"',"+objZafParSis.getCodigoUsuario() + " , " +
        " '"+ strFecSis +"' ,"+objZafParSis.getCodigoUsuario() +
        ",'A','"+strTipDev+"', 'I', null )";
        stmLoc.executeUpdate(strSql);

        stbDocDevEmp.append("SELECT "+objZafParSis.getCodigoEmpresa()+" AS coemp, "+intCodLocDev+" AS coloc, "+txtTipDocCod.getText()+" AS cotipdoc, "+intCodDocDev+" AS codoc ");
        
       //****************************************************************************/// 
         strSql ="INSERT INTO  tbr_cabMovInv(co_emp,co_loc,co_tipdoc,co_doc,st_reg,co_locrel,co_tipdocrel,co_docrel , st_regrep, co_emprel)"+
         " values("+objZafParSis.getCodigoEmpresa()+","+intCodLocDev+","+
         txtTipDocCod.getText()+","+intCodDocDev+",'A',"+intCodLocFac+","+
         vecTipDoc.elementAt(cboTipDoc.getSelectedIndex())+","+intNunDocFac+", 'I' , "+objZafParSis.getCodigoEmpresa()+" )";
         stmLoc.executeUpdate(strSql);
       //****************************************************************************///
                                        
      stmLoc.close();
      stmLoc=null;
      blnRes=true;
      
    }}catch(SQLException Evt){ blnRes=false; objutil.mostrarMsgErr_F1(jfrThis, Evt); }
      catch(Exception Evt){ blnRes=false; objutil.mostrarMsgErr_F1(jfrThis, Evt); }
    return blnRes;
  }                                    
         
     



public boolean insertarDet(int intCodDocDev){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intTipCli=3;
  double dlbcostouni=0.00;
  double bldcanmov=0.00; 
  double bldcostot=0.00;
  double dblCanDev=0; 
  int intControl=0;
  double dblcanmov=0;
  double dlbCan=0.00;
  int  intNumLinReg=0;   
  int intReaActStk=0;
  int intCodItmAct=0;
  int intCodBod=0;
  try{
   if(CONN_GLO!=null){
       stmLoc=CONN_GLO.createStatement();  
                                       
       intTipCli= objUltDocPrint2.ValidarCodigoCliente(txtCliCod.getText(),CONN_GLO);
       GLO_strArreItm="";
      objInvItm.limpiarObjeto();
      objInvItm.inicializaObjeto();
   
      for(int i=0; i<tblFacDet.getRowCount();i++){
       if(tblFacDet.getValueAt(i,INT_TBL_CODITM) != null ){
        if(tblFacDet.getValueAt(i, INT_TBL_CANDEV ) != null){
         if(!tblFacDet.getValueAt(i, INT_TBL_CANDEV ).equals("")){
          if( Double.parseDouble(tblFacDet.getValueAt(i, INT_TBL_CANDEV).toString()) > 0.00 ){
             dlbcostouni = Double.parseDouble( (tblFacDet.getValueAt(i, INT_TBL_COSTO )==null)?"0":tblFacDet.getValueAt(i, INT_TBL_COSTO ).toString());
             bldcanmov = Double.parseDouble( (tblFacDet.getValueAt(i, INT_TBL_CANDEV )==null)?"0":tblFacDet.getValueAt(i, INT_TBL_CANDEV ).toString());
             bldcostot = (dlbcostouni * bldcanmov);
             dblcanmov = objutil.redondeo(Double.parseDouble( (tblFacDet.getValueAt(i, INT_TBL_CANDEV)==null)?"0":tblFacDet.getValueAt(i, INT_TBL_CANDEV).toString()),6);

             strSql="SELECT sum(abs(b.nd_can)) as total FROM tbr_cabmovinv AS a"+ 
             " inner join tbm_cabmovinv as b1 on(a.co_doc=b1.co_doc and a.co_tipdoc=b1.co_tipdoc and b1.st_tipdev not in('P','V') ) " +
             " inner join tbm_detmovinv as b on(b1.co_doc=b.co_doc and b1.co_tipdoc=b.co_tipdoc)"+
             " where a.co_docrel="+intNunDocFac+" and a.co_emp="+objZafParSis.getCodigoEmpresa()+" and "+
             " a.co_tipdocrel="+vecTipDoc.elementAt(cboTipDoc.getSelectedIndex())+" and co_itm="+tblFacDet.getValueAt(i, INT_TBL_CODITM)+" and a.st_reg='A'";
             rstLoc=stmLoc.executeQuery(strSql);
             if(rstLoc.next())
                 dblCanDev = rstLoc.getDouble("total"); 
             rstLoc.close();
             rstLoc=null;
             
             dblCanDev=dblCanDev+bldcanmov;
             if(intControl!=0)    
                GLO_strArreItm=GLO_strArreItm+",";

             GLO_strArreItm=GLO_strArreItm + tblFacDet.getValueAt(i, INT_TBL_CODITM).toString();
             intControl++;
             intNumLinReg++;
                                              
             strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, " + //CAMPOS PrimayKey
             " tx_unimed ,co_itm, co_itmact,  tx_codalt,tx_codalt2, tx_nomItm, " +//<==Campos que aparecen en la parte superior del 1er Tab
             " co_bod, nd_can, nd_canorg, nd_preUni, nd_cosUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
             " ,st_reg,nd_tot,nd_cosunigrp,nd_costot ,nd_costotgrp , nd_candev  , st_regrep)" +
             " VALUES("+objZafParSis.getCodigoEmpresa() + ", " +intCodLocDev +", " +txtTipDocCod.getText()+ ", " + 
             intCodDocDev+", "+intNumLinReg+ ",'"+tblFacDet.getValueAt(i, INT_TBL_UNIDAD) + "'," +
             tblFacDet.getValueAt(i, INT_TBL_CODITM) + "," +tblFacDet.getValueAt(i, INT_TBL_CODITM) + ",'" +
             tblFacDet.getValueAt(i, INT_TBL_ALTITM) + "','" +tblFacDet.getValueAt(i, INT_TBL_ALTITM2) + "','" +
             tblFacDet.getValueAt(i, INT_TBL_DESITM) + "'," +tblFacDet.getValueAt(i, INT_TBL_CODBOD) + ", " + 
             dblcanmov + ", " +objutil.redondeo(Double.parseDouble( (tblFacDet.getValueAt(i, INT_TBL_CANMOV)==null)?"0":tblFacDet.getValueAt(i, INT_TBL_CANMOV).toString()),6) + ", " +
             objutil.redondeo(Double.parseDouble( (tblFacDet.getValueAt(i, INT_TBL_PRECIO)==null)?"0":tblFacDet.getValueAt(i, INT_TBL_PRECIO).toString()),6) + ", " +
             objInvItm.getCostoItm(CONN_GLO, objZafParSis.getCodigoEmpresa(), Integer.parseInt(""+tblFacDet.getValueAt(i, INT_TBL_CODITM)))+ ", " +
             objutil.redondeo(Double.parseDouble( (tblFacDet.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblFacDet.getValueAt(i, INT_TBL_PORDES).toString()),2) + ", '" +
             ((tblFacDet.getValueAt(i, INT_TBL_IVAITM).toString().equals("true"))?"S":"N") + "'  " +
             ",'P',"+ objutil.redondear(Double.parseDouble( (tblFacDet.getValueAt(i,INT_TBL_TOTAL)==null)?"0":tblFacDet.getValueAt(i,INT_TBL_TOTAL).toString()),2) + ","+
             dlbcostouni+","+bldcostot+","+bldcostot+" , "+ dblCanDev +" , 'I' )"; 
             txtsql.append( strSql + " ; \n");
             
             intCodItmAct=Integer.parseInt(tblFacDet.getValueAt(i,INT_TBL_CODITM).toString());
             intCodBod=Integer.parseInt(tblFacDet.getValueAt(i,INT_TBL_CODBOD).toString());
             dlbCan =Double.parseDouble( (tblFacDet.getValueAt(i, INT_TBL_CANDEV)==null)?"0":tblFacDet.getValueAt(i, INT_TBL_CANDEV).toString());
             //-------->Realiza el aumento o disminuciï¿½n en el inventario y el recosteo
             if(tblFacDet.getValueAt(i, INT_TBL_ITMSER).toString().equalsIgnoreCase("N")){
               objInvItm.generaQueryStock(objZafParSis.getCodigoEmpresa(), intCodItmAct, intCodBod, dlbCan, 1, "", "", "", 1);      
               intReaActStk=1;
             }
                                                
         }}}}}
         
       if(intReaActStk==1){
        if(!objInvItm.ejecutaActStock(CONN_GLO, intTipCli))
          return false;
        if(!objInvItm.ejecutaVerificacionStock(CONN_GLO, intTipCli))
          return false; 
      }
      objInvItm.limpiarObjeto();
  
       if(intNumLinReg!=0)
         stmLoc.executeUpdate(txtsql.getText());
                                        
       stmLoc.close();
       stmLoc=null;
      blnRes=true;
       
    }}catch(SQLException Evt){ blnRes=false; objutil.mostrarMsgErr_F1(jfrThis, Evt); }
      catch(Exception Evt){ blnRes=false; objutil.mostrarMsgErr_F1(jfrThis, Evt); }
    return blnRes;
  }     
       
                                       
                                       

public boolean insertarPag(int intCodDocDev){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  String strFecha="";
  String strFecSis="";
  double dblBaseDePagos=0;
  int VarC = 0;
  String VarN = "N";
  String VarA = "A";
  try{
   if(CONN_GLO!=null){
       stmLoc=CONN_GLO.createStatement();  
                                    
       dblBaseDePagos = Double.parseDouble(txtTot.getText());; 
              
       int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
       strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
       
       strFecSis = objutil.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
       
       strSql="INSERT INTO tbm_pagmovinv(co_emp,co_loc,co_tipdoc,co_doc,co_reg,ne_diacre,fe_ven,mo_pag,ne_diagra,nd_abo,st_sop,st_entsop,st_pos,st_reg , st_regrep ) " +
       "values("+ objZafParSis.getCodigoEmpresa()+","+intCodLocDev+","+txtTipDocCod.getText()+","+intCodDocDev+" "+
       ", 1,"+VarC+",'"+ strFecha + "', "+dblBaseDePagos+","+VarC+","+VarC+",'"+VarN+"','"+VarN+"' "+
       ",'"+VarN+"','"+VarA+"','I')"; 
       stmLoc.executeUpdate(strSql);
                                        
       //**************************************************************************************
        if(intGenCruAut==1){ 
               int intTipDocCru=90;
               int intCodDocCru=0;
               int intSecc=1;
               java.sql.Statement stm = CONN_GLO.createStatement();
               java.sql.Statement stmins = CONN_GLO.createStatement();
               intCodDocCru = objUltDocPrint2.getCodDocTbmCabPag(CONN_GLO, intTipDocCru);
               strSql="INSERT INTO TBM_CABPAG(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc1, co_cli, tx_nomcli, nd_mondoc, st_reg, fe_ing , co_usring, st_regrep, st_imp) "+
               " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+intTipDocCru+", "+intCodDocCru+" " +
               ", '"+strFecha+"', "+intCodDocCru+", "+txtCliCod.getText()+", '"+txtCliNom.getText()+"',"+dblBaseDePagos+",'A','"+ strFecSis +"',"+objZafParSis.getCodigoUsuario()+",'I','P')";
               stmins.executeUpdate(strSql);

                strSql ="insert into tbm_detpag(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag, co_docpag,  " +
                " co_regpag, nd_abo, co_tipdoccon) VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", " +
                " "+intTipDocCru+", "+intCodDocCru+","+intSecc+", "+intCodLocDev+","+txtTipDocCod.getText()+","+intCodDocDev+" "+
                " ,1, "+dblBaseDePagos*-1+","+txtTipDocCod.getText()+")";
                stmins.executeUpdate(strSql);

               strSql ="select * from tbm_pagmovinv where co_Emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+intCodLocFac+" and co_tipdoc="+vecTipDoc.elementAt(cboTipDoc.getSelectedIndex())+" and co_doc="+intNunDocFac+" AND st_reg IN ('A','C') ";
               java.sql.ResultSet rst = stm.executeQuery(strSql);
               while(rst.next()){
                   intSecc++;
                   strSql ="insert into tbm_detpag(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag, co_docpag,  " +
                   " co_regpag, nd_abo, co_tipdoccon) VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", " +
                   " "+intTipDocCru+", "+intCodDocCru+","+intSecc+", "+rst.getString("co_loc")+","+rst.getString("co_tipdoc")+","+rst.getString("co_doc")+" "+
                   " ,"+rst.getString("co_reg")+",abs("+rst.getDouble("mo_pag")+"),"+rst.getString("co_tipdoc")+")";
                   stmins.executeUpdate(strSql);

                   strSql ="UPDATE tbm_pagmovinv SET nd_abo=abs(mo_pag) where " +
                   "  co_emp="+rst.getString("co_emp")+" and co_loc="+rst.getString("co_loc")+" and  co_tipdoc="+rst.getString("co_tipdoc")+" and co_doc="+rst.getString("co_doc")+" and co_reg="+rst.getString("co_reg");
                   stmins.executeUpdate(strSql);
                }

                  strSql ="UPDATE tbm_pagmovinv SET nd_abo=abs(mo_pag)*-1 where " +
                   "  co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+intCodLocDev+" and  co_tipdoc="+txtTipDocCod.getText()+" and co_doc="+intCodDocDev+" and co_reg=1";
                   stmins.executeUpdate(strSql);

                   stmins.close();
                   stmins=null;
                   stm.close();
                   stm=null;
                   rst.close();
                   rst=null;
         }       
       //*****************************************************************************************
                                         
       stmLoc.close();
       stmLoc=null;
      blnRes=true;
       
    }}catch(SQLException Evt){ blnRes=false; objutil.mostrarMsgErr_F1(jfrThis, Evt); }
      catch(Exception Evt){ blnRes=false; objutil.mostrarMsgErr_F1(jfrThis, Evt); }
    return blnRes;
  }    
       


       
   
    
      public String getUnidad(String int_co_itm){
           /*
            * Obteniendo la unidad del producto
            */
        
      
           String strDesUni = "";
           try{
                    if (CONN_GLO!=null){
                        java.sql.Statement stmUni = CONN_GLO.createStatement();

                        String sSQL= "SELECT var.tx_descor " +
                                        " from tbm_inv as inv " +
                                        "	inner join tbm_var as var on (inv.co_uni = var.co_reg) "+ 
                                        " where inv.co_emp = " + objZafParSis.getCodigoEmpresa() +  " and " +
                                        "       var.co_grp = 5  and inv.co_itm = " + int_co_itm ; 

                        java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                        if(rstUni.next())
                            strDesUni = (rstUni.getString("tx_descor")==null)?"":rstUni.getString("tx_descor");

                        rstUni.close();
                        stmUni.close();
                        rstUni = null;
                        stmUni = null;
                    }
           }
           catch(java.sql.SQLException Evt)
           {
                    System.out.println(Evt);
                    return strDesUni+"";
            }
            catch(Exception Evt)
            {
                    System.out.println(Evt);
                    return strDesUni+"";
            }                  

           return strDesUni;
    }
    
    
    
    /**
     * Factura ya tiene devolución
     */

    

private boolean  cargaFactura(ResultSet rstCotCab){
 boolean blnRes=false;
 try{
    if(cargaFacCab(rstCotCab)){
     if(cargaFacDet(rstCotCab)){
      blnRes=true;   
    }}
 }catch(Exception Evt) { blnRes=false;   objutil.mostrarMsgErr_F1(jfrThis, Evt);  }                       
 return blnRes;
}



private boolean  cargaFacCab(ResultSet rstCotCab){
 boolean blnRes=false;
 try{
    removeLisCamTbls();
    if(CONN_GLO!=null){
        
     if(rstCotCab != null){
      intNunDocFac = rstCotCab.getInt("co_doc");
      txtCliCod.setText(((rstCotCab.getString("co_cli")==null)?"":rstCotCab.getString("co_cli")));
      txtCliNom.setText(((rstCotCab.getString("nomcli")==null)?"":rstCotCab.getString("nomcli")));
      txtCliDir.setText(((rstCotCab.getString("dircli")==null)?"":rstCotCab.getString("dircli")));
      txtFromFac.setText(((rstCotCab.getString("ne_numdoc")==null)?"":rstCotCab.getString("ne_numdoc")));
                        
     if(rstCotCab.getDate("fe_doc")==null){
        txtFecDoc.setText("");  
     }else{
        java.util.Date dateObj = rstCotCab.getDate("fe_doc");
        java.util.Calendar calObj = java.util.Calendar.getInstance();
        calObj.setTime(dateObj);
        txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH), calObj.get(java.util.Calendar.MONTH)+1, calObj.get(java.util.Calendar.YEAR));
     }
      
     txtFecFac.setText(txtFecDoc.getText());
     txtFecDoc.setHoy();
     txtVenCod.setText(((rstCotCab.getString("co_ven")==null)?"":rstCotCab.getString("co_ven")));
     txtVenNom.setText(((rstCotCab.getString("nomven")==null)?"":rstCotCab.getString("nomven")));
     txtAte.setText(((rstCotCab.getString("tx_ate")==null)?"":rstCotCab.getString("tx_ate")));

     //Pie de pagina
     txaObs1.setText(((rstCotCab.getString("tx_obs1")==null)?"":rstCotCab.getString("tx_obs1")));
     txaObs2.setText(((rstCotCab.getString("tx_obs2")==null)?"":rstCotCab.getString("tx_obs2")));

     dblPorIva = objutil.redondeo(rstCotCab.getDouble("nd_porIva"),2);

     stIvaVen=rstCotCab.getString("st_ivaven");

     double dbl_Sub    = rstCotCab.getDouble("nd_sub"), 
     dbl_Iva    = ((dbl_Sub* dblPorIva)==0)?0:((dbl_Sub* dblPorIva)/100);

     dblSubtotalDoc = 0;
     dblIvaDoc = 0;
     dblTotalDoc = 0;

     txtSub.setText( "" + objutil.redondeo(dblSubtotalDoc,intNumDec) );
     txtIva.setText(""+ objutil.redondeo(dblIvaDoc,intNumDec));
     txtTot.setText("" + objutil.redondeo(dblTotalDoc,intNumDec));
     lblIva.setText("IVA " + dblPorIva + "%");
 }
 blnRes=true;
}}catch(SQLException Evt){  blnRes=false; objutil.mostrarMsgErr_F1(jfrThis, Evt); }
  catch(Exception Evt) { blnRes=false;   objutil.mostrarMsgErr_F1(jfrThis, Evt);  }                       
 return blnRes;
}
   



private boolean  cargaFacDet(ResultSet rstCotCab){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstCotDet;
 int intCodDoc = 0;
 String strSql=""; 
 try{
    if(CONN_GLO!=null){
     stmLoc=CONN_GLO.createStatement();
     if(rstCotCab != null){
       intCodDoc   = rstCotCab.getInt("co_doc");
     
     //Detalle        
     strSql="SELECT a.co_itm, a.tx_codalt, a.tx_codalt2, a.tx_nomitm, a.tx_unimed, a.co_bod, abs(a.nd_can) as nd_can, a.nd_canorg, a.nd_cosuni," +
     " a.nd_preuni, a.nd_pordes, a.st_ivacom,b.st_ser,a.nd_cosunigrp " +
     //" ,x.tx_nomcli, x.co_regrel, x.co_tipdocrel, x.co_docrel ,x.co_locrel" +
     " FROM tbm_detMovInv AS a " +
     "" +
//     " LEFT OUTER JOIN (" +
//     " SELECT tbrd.co_reg, tbrd.co_regrel, tbrd.co_tipdocrel, tbrd.co_docrel, tbrd.co_locrel, oc.tx_nomcli  FROM  tbr_detmovinv AS tbrd " +
//     " INNER JOIN tbm_cabmovinv AS oc ON(oc.co_emp=tbrd.co_emp AND oc.co_loc=tbrd.co_locrel AND oc.co_tipdoc=tbrd.co_tipdocrel " +
//     " AND oc.co_doc=tbrd.co_docrel )" +
//     " WHERE tbrd.co_emp="+objZafParSis.getCodigoEmpresa()+" AND tbrd.co_loc="+intCodLocFac+" AND" +
//     " tbrd.co_tipdoc="+strCodTipDocFac+" AND tbrd.co_doc="+intCodDoc+" " +
//     " ) as x ON (x.co_reg=a.co_reg) " +
     "" +
     " INNER JOIN tbm_inv AS b on(a.co_emp=b.co_emp and a.co_itm=b.co_itm)  WHERE " +
     " a.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
     " a.co_loc="+intCodLocFac+" and " +
     " a.co_doc = " + intCodDoc                       + " and " +
     " a.co_tipdoc =  " +strCodTipDocFac+ " order by a.co_reg ";


     rstCotDet = stmLoc.executeQuery(strSql);
     Vector vecData = new Vector();
    for(int i=0 ; rstCotDet.next() ; i++){
      java.util.Vector vecReg = new java.util.Vector();
      blnRes=true;                     
      vecReg.add(INT_TBL_LINEA, "");
      vecReg.add(INT_TBL_ALTITM, rstCotDet.getString("tx_codalt") );
      vecReg.add(INT_TBL_DESITM, rstCotDet.getString("tx_nomitm"));
      vecReg.add(INT_TBL_UNIDAD, rstCotDet.getString("tx_unimed"));
      vecReg.add(INT_TBL_CODBOD, new Integer(rstCotDet.getInt("co_bod")));
      vecReg.add(INT_TBL_CANMOV , new Double(rstCotDet.getDouble("nd_can")));
      vecReg.add(INT_TBL_CANDIFDEV, "");
      vecReg.add(INT_TBL_CANDEV, "");
      vecReg.add(INT_TBL_PRECIO , new Double(rstCotDet.getDouble("nd_preuni")));
      vecReg.add(INT_TBL_PREDEV, "");
      vecReg.add(INT_TBL_PORDES, new Double(rstCotDet.getDouble("nd_pordes")));
      vecReg.add(INT_TBL_CANDES, "");
      String strIva = rstCotDet.getString("st_ivacom");
      Boolean blnIva = new Boolean(strIva.equals("S"));                         
      vecReg.add(INT_TBL_IVAITM , blnIva);
      vecReg.add(INT_TBL_TOTAL, "");
      vecReg.add(INT_TBL_CODITM , new Integer(rstCotDet.getInt("co_itm")));
      vecReg.add(INT_TBL_ALTITM2, rstCotDet.getString("tx_codalt2"));
      vecReg.add(INT_TBL_ITMSER , rstCotDet.getString("st_ser"));
      vecReg.add(INT_TBL_COSTO, new Double( rstCotDet.getDouble("nd_cosunigrp") ));
      vecReg.add(INT_TBL_DESORI, new Double(rstCotDet.getDouble("nd_pordes")));
      vecReg.add(INT_TBL_NOMPRV , null ); //  rstCotDet.getString("tx_nomcli"));
      vecReg.add(INT_TBL_CODLOC , null ); //  rstCotDet.getString("co_locrel"));
      vecReg.add(INT_TBL_CODTIP , null ); // rstCotDet.getString("co_tipdocrel"));
      vecReg.add(INT_TBL_CODDOC , null ); // rstCotDet.getString("co_docrel"));
      vecReg.add(INT_TBL_CODREG , null ); // rstCotDet.getString("co_regrel"));
      vecReg.add(INT_TBL_CODREGDEV , "");
    vecData.add(vecReg);    
  }
  objTblMod.setData(vecData);
  tblFacDet.setModel(objTblMod);   
  ObtenerDiferenciadeDevolucion();
 
  calculaSubtotal_OC();
}
 stmLoc.close();
 stmLoc=null;
}}catch(SQLException Evt){  blnRes=false; objutil.mostrarMsgErr_F1(jfrThis, Evt); }
  catch(Exception Evt) { blnRes=false;   objutil.mostrarMsgErr_F1(jfrThis, Evt);  }                       
 return blnRes;
}


    
    
public void calculaSubtotal_OC(){
 double dblCan=0,dblDes=0,dblCosto=0,dblTotal=0;
 for (int i=0;i<tblFacDet.getRowCount();i++){ 
 dblCan=0; 
 dblDes=0;
 dblCosto=0;
   if (tblFacDet.getValueAt(i, INT_TBL_CANDEV)==null) tblFacDet.setValueAt("0",i, INT_TBL_CANMOV);
   if (tblFacDet.getValueAt(i, INT_TBL_PRECIO)==null) tblFacDet.setValueAt("0",i, INT_TBL_PRECIO);
   if (tblFacDet.getValueAt(i, INT_TBL_PORDES)==null) tblFacDet.setValueAt("0",i, INT_TBL_PORDES);
   dblCan = Double.parseDouble(tblFacDet.getValueAt(i, INT_TBL_CANDEV).toString());
   dblCosto = Double.parseDouble(tblFacDet.getValueAt(i, INT_TBL_PRECIO).toString());
   if (dblCan>0) dblDes = ((dblCan * dblCosto )*Double.parseDouble(tblFacDet.getValueAt(i, INT_TBL_PORDES).toString())/100);
   else dblDes = 0;
 
    dblTotal  = (dblCan * dblCosto)- dblDes;
    dblTotal =  objutil.redondear(dblTotal,3);  
    dblTotal =  objutil.redondear(dblTotal,2);  
    tblFacDet.setValueAt(new Double(dblTotal), i , INT_TBL_TOTAL);    
}
 calculaTotal();
 generaAsiento();
}

              
       /* esta funcion servira para obtener las devoluciones
        *que se aya hecho a esa factura 
        */
    
              public void BuscarDevAntaFac(int intCodDoc, java.sql.Connection con){
                  int intEst=0; 
                  String Coddoc="";
                  try{
                                          String sql="select co_doc from tbr_cabmovinv  where co_emp="+objZafParSis.getCodigoEmpresa()+"  and  " +
                                          "   co_locrel="+intCodLocFac+"  and  co_docrel="+intCodDoc+" and " +
                                          "   co_tipdocrel="+strCodTipDocFac+"  and  co_tipdoc="+txtTipDocCod.getText()+"  and  st_reg='A'";
                                         //System.out.println(">>> "+ sql );
                                                     stmDocDet=con.createStatement();
                                                     rstDocDet2 = stmDocDet.executeQuery(sql);
                                                     double intNumDev=0; 
                                                     while(rstDocDet2.next()){
                                                         intEst=1;
                                                         Coddoc += rstDocDet2.getString(1)+" , "; 
                                                     }
                                                      
                                                     
                                                     
                                                      
//                         
//                            "       Cab.co_emp = " + objZafParSis.getCodigoEmpresa() + 
//                        "       and Cab.co_loc ="+intCodLocFac+  
//                        "       and Cab.co_doc = "+strCodDocFac+ 
//                        "       and Cab.co_tipdoc = "+strCodTipDocFac+ 
//                        "       and Cab.st_reg not in ('I','P')";

                                                     
                                                       
                                                      if(intEst==1) {
                                                             javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
                                                             String strTit, strMsg;
                                                             strTit="Mensaje del sistema Zafiro";
                                                             strMsg="Esta Factura Ya tiene devoluciones con los soguiente Numero de Documentos ."+Coddoc;
                                                             oppMsg.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
                                                     }
                           con.close();
                           con=null; 
                          }
                          catch(SQLException Evt){ objutil.mostrarMsgErr_F1(jfrThis, Evt);  }
                   }  
               
            
    
    
    
     public void calculaSubtotal(){
        double dblCanFac =  objutil.redondear(Double.parseDouble(((tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_CANMOV)==null)?"0":(tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_CANMOV).toString()))),2),
                   dblCanDev =  objutil.redondear(Double.parseDouble(((tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_CANDEV)==null)?"0":(tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_CANDEV).toString()))),2),
                   dblCan    =  dblCanFac - dblCanDev , 
                   dblPre    =  Double.parseDouble(((tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_PRECIO)==null)?"0":(tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_PRECIO).toString()))),
                   dblPorDes =  objutil.redondeo(Double.parseDouble(((tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_PORDES)==null)?"0":(tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_PORDES).toString()))),2),
                   dblValDes =   ((dblCanDev * dblPre)==0)?0:((dblCanDev * dblPre) * dblPorDes / 100) ,
                  // dblTotal  = objutil.redondeo((dblCanDev * dblPre)- dblValDes ,6 ) ;
                   dblTotal  = (dblCanDev * dblPre)- dblValDes;
                    
                   dblTotal =  objutil.redondear(dblTotal,3);  
                   dblTotal =  objutil.redondear(dblTotal,2);  
                   
                   
            tblFacDet.setValueAt(new Double(dblTotal),tblFacDet.getSelectedRow(), INT_TBL_TOTAL);    
            calculaTotal();
            generaAsiento();     
    }    
    
     
   
     
     
       public void calculaSubValores(){
        double dblCanFac =  objutil.redondear(Double.parseDouble(((tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_CANDIFDEV)==null)?"0":(tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_CANDIFDEV).toString()))),2);
            double dblCanDev = dblCanFac , // objutil.redondear(Double.parseDouble(((tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_CANDEV)==null)?"0":(tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_CANDEV).toString()))),2),
                   dblCan    =  dblCanFac - dblCanDev , 
                   dblPre    =  Double.parseDouble(((tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_PREDEV)==null)?"0":(tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_PREDEV).toString()))),
                   dblPorDes =  objutil.redondeo(Double.parseDouble(((tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_PORDES)==null)?"0":(tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_PORDES).toString()))),2),
                   dblValDes =   ((dblCanDev * dblPre)==0)?0:((dblCanDev * dblPre) * dblPorDes / 100) ,
                  // dblTotal  = objutil.redondeo((dblCanDev * dblPre)- dblValDes ,6 ) ;
                   dblTotal  = (dblCanDev * dblPre)- dblValDes;
                    
                   dblTotal =  objutil.redondear(dblTotal,3);  
                   dblTotal =  objutil.redondear(dblTotal,2);  
                   
                   
            tblFacDet.setValueAt(new Double(dblTotal),tblFacDet.getSelectedRow(), INT_TBL_TOTAL);    
            calculaTotal();
            generaAsiento();     
    }    
    
   
     
     
     
      public void calculaDescuento(){
              double dblCan =  objutil.redondear(Double.parseDouble(((tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_CANDIFDEV)==null)?"0":(tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_CANDIFDEV).toString()))),2);
              double dblPre    =  Double.parseDouble(((tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_PRECIO)==null)?"0":(tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_PRECIO).toString())));
              double dblPorDes =  objutil.redondeo(Double.parseDouble(((tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_CANDES)==null)?"0":(tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_CANDES).toString()))),2);
              
              double dblValDes =   (dblCan * dblPre);  //)==0)?0:((dblCanDev * dblPre) * dblPorDes / 100); 
              double dblTotal  = (dblValDes * (dblPorDes/100));
                    
                   dblTotal =  objutil.redondear(dblTotal,3);  
                   dblTotal =  objutil.redondear(dblTotal,2);  
                   
            tblFacDet.setValueAt(new Double(dblTotal),tblFacDet.getSelectedRow(), INT_TBL_TOTAL);    
            calculaTotal();
            generaAsientoPorDescuento();   
    }    
    

    
    class EditorCol_Double extends javax.swing.DefaultCellEditor {
        
        final javax.swing.JTextField txtDouble;
        private boolean daEnter= false;
        
        EditorCol_Double() {
            super(new javax.swing.JCheckBox());
            txtDouble = new javax.swing.JTextField();
            
            /**
             * Listener para que cuando el usuario de click en la celda, se seleccione 
             * todo el contenido de la misma.
             */
            txtDouble.addFocusListener(new java.awt.event.FocusAdapter() {
                 public void focusGained(java.awt.event.FocusEvent evt) {
                    txtDouble.selectAll();
                  }
                public void focusLost(java.awt.event.FocusEvent evt) {
                      if(!daEnter){
                            cancelCellEditing();
//                            tblFacDet.removeRowSelectionInterval(tblFacDet.getSelectedRow(),tblFacDet.getSelectedRow());
                       }
                }
             });
            
            this.editorComponent = txtDouble;
            
            txtDouble.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    fireEditingStopped();
               }
            });
        }
        
        protected void fireEditingStopped() {
            double dblCantFac = Double.parseDouble(tblFacDet.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_CANMOV ).toString()), 
                   dblCantDev = Double.parseDouble(txtDouble.getText());
            if(dblCantFac < dblCantDev )
                txtDouble.setText("0");
            
             super.fireEditingStopped();

       
            
        }
        
        
        
        public Object getCellEditorValue() {
            Double dbl = new Double(objutil.redondeo(Double.parseDouble((txtDouble.getText().trim().equals(""))?"0":txtDouble.getText()),6));
            return dbl;
        }
        public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column) {
            
            if(value == null)
                ((javax.swing.JTextField) editorComponent).setText("0");
            else
                ((javax.swing.JTextField) editorComponent).setText(value.toString());
            ((javax.swing.JTextField) editorComponent).setFont( new java.awt.Font("SansSerif", 0, 11));
            ((javax.swing.JTextField) editorComponent).selectAll();

            return editorComponent;
        }
        
    }
  

    
    /*
     * Clase de tipo documenet listener para detectar los cambios en 
     * los textcomponent
     */
    class LisTextos implements javax.swing.event.DocumentListener {
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            blnCambios = true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            blnCambios = true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            blnCambios = true;
        }
    }        
    
    
    class LisCambioTbl implements javax.swing.event.TableModelListener{
        public void tableChanged(javax.swing.event.TableModelEvent e){
            //Cambiaron los datos en jtable
            //calculaTotal();
           ///****************** generaAsiento();
            blnCambios = true;
        }
    }
    
    /*
     * Obtiene el Iva que se debe cobrar en la empresa actual
     */
    public double getIva(){
        double dblIvaActual = 0;
        try{
          if (CONN_GLO!=null){
              stmDoc=CONN_GLO.createStatement();
              sSQL= "SELECT nd_ivaVen as porIva from tbm_emp " +
                   "where co_emp = " + objZafParSis.getCodigoEmpresa();  //<== Verificando que seleccione la empresa actual

              ResultSet rstIva = stmDoc.executeQuery(sSQL);
              if(rstIva.next()){
                 dblIvaActual = objutil.redondeo(rstIva.getDouble("porIva"),2);
              }
            }
          }
           catch(SQLException Evt)
           {
                    objutil.mostrarMsgErr_F1(jfrThis, Evt);
            }
            catch(Exception Evt)
            {
                    objutil.mostrarMsgErr_F1(jfrThis, Evt);
            }                  
           
           return dblIvaActual;
    }
    
    
    
    
    

    public void noEditable2(boolean blnEditable){
        java.awt.Color colBack = txtDoc.getBackground();
 
            colBack = txtTipDocCod.getBackground();
            txtTipDocCod.setEditable(blnEditable);
            txtTipDocCod.setBackground(colBack);        

            colBack = txtTipDocNom.getBackground();
            txtTipDocNom.setEditable(blnEditable);
            txtTipDocNom.setBackground(colBack);        

            colBack = txtDev.getBackground();
            txtDev.setEditable(blnEditable);
            txtDev.setBackground(colBack);        

            colBack = txtFecDoc.getBackground();
            txtFecDoc.setEnabled(blnEditable);
            txtFecDoc.setBackground(colBack);    
            
            colBack = txtFromFac.getBackground();
            txtFromFac.setEditable(!blnEditable);
            txtFromFac.setBackground(colBack);    

            cboTipDoc.setEnabled(!blnEditable);
            local.setEnabled(!blnEditable);
    }
    
    public void  noEditable(boolean blnEditable){
        java.awt.Color colBack = txtDoc.getBackground();

            txtDoc.setEditable(blnEditable);
            txtDoc.setBackground(colBack);

            colBack = txtCliCod.getBackground();
            txtCliCod.setEditable(blnEditable);
            txtCliCod.setBackground(colBack);        

            colBack = txtCliCod.getBackground();
            txtCliCod.setEditable(blnEditable);
            txtCliCod.setBackground(colBack);        

            colBack = txtCliNom.getBackground();
            txtCliNom.setEditable(blnEditable);
            txtCliNom.setBackground(colBack);        

            colBack = txtCliDir.getBackground();
            txtCliDir.setEditable(blnEditable);
            txtCliDir.setBackground(colBack);        
            
            colBack = txtAte.getBackground();
            txtAte.setEditable(blnEditable);
            txtAte.setBackground(colBack);        
            
            colBack = txtVenCod.getBackground();
            txtVenCod.setEditable(blnEditable);
            txtVenCod.setBackground(colBack);        

            colBack = txtVenNom.getBackground();
            txtVenNom.setEditable(blnEditable);
            txtVenNom.setBackground(colBack);        

            colBack = txtFecFac.getBackground();
            txtFecFac.setEditable(blnEditable);
            txtFecFac.setBackground(colBack);
            
    //      txtCliDir.setEnabled(blnEditable);
    //      txtCliDir.setBackground(colBack);

            colBack = txtSub.getBackground();
            txtSub.setEditable(blnEditable);
            txtSub.setBackground(colBack);
            
            txtIva.setEditable(blnEditable);
            txtIva.setBackground(colBack);
            
            txtTot.setEditable(blnEditable);
            txtTot.setBackground(colBack);
    }
  
    public void  clnTextos(){
        //Cabecera

            txtDoc.setText("");
            txtFromFac.setText("");
            txtDev.setText("");
            txtFecFac.setText("");

            txtCliCod.setText("");
            txtCliNom.setText("");
            txtCliDir.setText("");

            txtFecDoc.setText("");
            txtVenCod.setText("");
            txtVenNom.setText("");
            txtAte.setText("");

        
         //Detalle        
            objTblMod.removeAllRows();     
            
      
        //Pie de pagina
            txaObs1.setText("");
            txaObs2.setText("");
            txtSub.setText("0");
            txtIva.setText("0");
            //txtDes.setText("0");
            txtTot.setText("0");
            lblFacNumDes.setText("Devolución");

          
         //Tab de Asiento Contable
         //Tab de Asiento Contable
            objAsiDia.inicializar();
            objAsiDia.setEditable(false);
    }
   
     public void  calculaTotal(){
        double dblSub = 0, dblIva = 0, dblDes = 0, dblTmp=0, dblSub2=0;
        
        for( int i=0;i<tblFacDet.getRowCount();i++ ){
           if (tblFacDet.getValueAt(i,INT_TBL_CODITM)!=null){
           if (tblFacDet.getValueAt(i,INT_TBL_CANDEV)!=null){
               
            dblSub2 = ((tblFacDet.getValueAt(i, INT_TBL_TOTAL)==null||tblFacDet.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblFacDet.getValueAt(i, INT_TBL_TOTAL).toString()));  
            dblSub = dblSub + objutil.redondear(dblSub2,intNumDec);
            
            if(tblFacDet.getValueAt(i, INT_TBL_IVAITM)==null)
                dblIva = dblIva + 0;
            else{
                dblTmp = ((tblFacDet.getValueAt(i, INT_TBL_IVAITM).toString().equals("true"))? ((tblFacDet.getValueAt(i, INT_TBL_TOTAL)==null||tblFacDet.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblFacDet.getValueAt(i, INT_TBL_TOTAL).toString())) : 0 );
                dblIva = dblIva + (((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100) ;
            }
           }}
        }
        
        
         /////////////////////////////////////////////////////////////
        dblSubtotalDoc = dblSub;
        dblIvaDoc = objutil.redondear(dblIva,intNumDec);
        dblTotalDoc = dblSubtotalDoc + dblIvaDoc;
        dblTotalDoc = objutil.redondear(dblTotalDoc ,intNumDec);
        dblSubtotalDoc = objutil.redondear(dblSubtotalDoc ,intNumDec);
     
       
            txtSub.setText( "" + dblSubtotalDoc );
            txtIva.setText( "" + dblIvaDoc );
            txtTot.setText( ""+ dblTotalDoc);
        
        /////////////////////////////////////////////////////////////
    }
     
   
     
     
     
    public void  calculaTotalDesc(){
        double dblSub = 0, dblIva = 0, dblDes = 0, dblTmp=0, dblSub2=0;
        
        for( int i=0;i<tblFacDet.getRowCount();i++ ){
           if (tblFacDet.getValueAt(i,INT_TBL_CODITM)!=null){
           if (tblFacDet.getValueAt(i,INT_TBL_CANDES)!=null){
               
            dblSub2 = ((tblFacDet.getValueAt(i, INT_TBL_TOTAL)==null||tblFacDet.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblFacDet.getValueAt(i, INT_TBL_TOTAL).toString()));  
            dblSub = dblSub + objutil.redondear(dblSub2,intNumDec);
            
            if(tblFacDet.getValueAt(i, INT_TBL_IVAITM)==null)
                dblIva = dblIva + 0;
            else{
                dblTmp = ((tblFacDet.getValueAt(i, INT_TBL_IVAITM).toString().equals("true"))? ((tblFacDet.getValueAt(i, INT_TBL_TOTAL)==null||tblFacDet.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblFacDet.getValueAt(i, INT_TBL_TOTAL).toString())) : 0 );
                dblIva = dblIva + (((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100) ;
            }
           }}
        }
        
        
         /////////////////////////////////////////////////////////////
        dblSubtotalDoc = dblSub;
        dblIvaDoc = objutil.redondear(dblIva,intNumDec);
        dblTotalDoc = dblSubtotalDoc + dblIvaDoc;
        dblTotalDoc = objutil.redondear(dblTotalDoc ,intNumDec);
        dblSubtotalDoc = objutil.redondear(dblSubtotalDoc ,intNumDec);
     
        txtSub.setText( "" + dblSubtotalDoc );
        txtIva.setText( "" + dblIvaDoc );
        txtTot.setText( ""+ dblTotalDoc);
        /////////////////////////////////////////////////////////////
      
    }
     
       
      
    
     public void  calculaTotalPreDev(){
        double dblSub = 0, dblIva = 0, dblDes = 0, dblTmp=0, dblSub2=0;
        
        for( int i=0;i<tblFacDet.getRowCount();i++ ){
           if (tblFacDet.getValueAt(i,INT_TBL_CODITM)!=null){
           if (tblFacDet.getValueAt(i,INT_TBL_PREDEV)!=null){
               
            dblSub2 = ((tblFacDet.getValueAt(i, INT_TBL_TOTAL)==null||tblFacDet.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblFacDet.getValueAt(i, INT_TBL_TOTAL).toString()));  
            dblSub = dblSub + objutil.redondear(dblSub2,intNumDec);
            
            if(tblFacDet.getValueAt(i, INT_TBL_IVAITM)==null)
                dblIva = dblIva + 0;
            else{
                dblTmp = ((tblFacDet.getValueAt(i, INT_TBL_IVAITM).toString().equals("true"))? ((tblFacDet.getValueAt(i, INT_TBL_TOTAL)==null||tblFacDet.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblFacDet.getValueAt(i, INT_TBL_TOTAL).toString())) : 0 );
                dblIva = dblIva + (((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100) ;
            }
           }}
        }
        
        
         /////////////////////////////////////////////////////////////
        dblSubtotalDoc = dblSub;
        dblIvaDoc = objutil.redondear(dblIva,intNumDec);
        dblTotalDoc = dblSubtotalDoc + dblIvaDoc;
        dblTotalDoc = objutil.redondear(dblTotalDoc ,intNumDec);
        dblSubtotalDoc = objutil.redondear(dblSubtotalDoc ,intNumDec);
     
        txtSub.setText( "" + dblSubtotalDoc );
        txtIva.setText( "" + dblIvaDoc );
        txtTot.setText( ""+ dblTotalDoc);
        /////////////////////////////////////////////////////////////
    }
    
   
    
     
     
      public void generaAsiento(){
        
           double dblIvaCot=0,dblSubtotalCot=0,dblTotalCot=0;
           dblTotalCot = Double.parseDouble(txtTot.getText());
           dblSubtotalCot = Double.parseDouble(txtSub.getText());
           dblIvaCot = Double.parseDouble(txtIva.getText());
        
           objAsiDia.inicializar();
           java.util.Vector vecAsiento = new java.util.Vector();         
     
           double dblValTotDes =  getTotalDescuento();
        
          if((dblValTotDes+dblSubtotalCot)>0){ // if(dblTotalCot>0){
             java.util.Vector vecRowAsientoDeb = vecFila2(objCtaCta_Dat.getCtaDeb(),objCtaCta_Dat.getCtaCodDeb(),objCtaCta_Dat.getCtaNomDeb()); 
             vecRowAsientoDeb.setElementAt(new Double(objutil.redondear(dblValTotDes+dblSubtotalCot,2)), 5);
             vecAsiento.add(vecRowAsientoDeb);
          }
        
        /* Agregando la cuenta de descuento  */
     
        if( dblIvaCot > 0){ // if( dblValTotDes > 0){
            java.util.Vector vecRowAsientoDeb = vecFila2(objCtaCta_Dat.getCtaIvaVentas(),objCtaCta_Dat.getCtaCodIvaVen(),objCtaCta_Dat.getCtaNomIvaVen()); 
            vecRowAsientoDeb.setElementAt(new Double(dblIvaCot), 5);
            vecAsiento.add(vecRowAsientoDeb);
        }
        
        /*
         * Agregando la cuenta de Ventas
         */
        if( dblTotalDoc > 0){ //if( (dblValTotDes+dblSubtotalCot) > 0){
            java.util.Vector vecRowAsientoHab = vecFila2(objCtaCta_Dat.getCtaHab(),objCtaCta_Dat.getCtaCodHab(),objCtaCta_Dat.getCtaNomHab()); 
            vecRowAsientoHab.setElementAt(new Double(objutil.redondear(dblTotalCot,2)), 6);
            vecAsiento.add(vecRowAsientoHab);
        }
        /*
         * Agregando la cuenta Iva en Ventas
         */
        if( dblValTotDes > 0){ //if( dblIvaCot > 0){
            java.util.Vector vecRowAsientoHab = vecFila2(objCtaCta_Dat.getCtaDescVentas(),objCtaCta_Dat.getCtaCodDes(),objCtaCta_Dat.getCtaNomDes()); 
            vecRowAsientoHab.setElementAt(new Double(objutil.redondear(dblValTotDes,2)), 6);
            vecAsiento.add(vecRowAsientoHab);
       }        
        objAsiDia.setDetalleDiario(vecAsiento);        
    }
     
     
      
      
      
      
      
       public void generaAsientoPorDescuento(){
        
           double dblIvaCot=0,dblSubtotalCot=0,dblTotalCot=0;
           dblTotalCot    = Double.parseDouble(txtTot.getText());
           dblSubtotalCot = Double.parseDouble(txtSub.getText());
           dblIvaCot      = Double.parseDouble(txtIva.getText());
        
           objAsiDia.inicializar();
           java.util.Vector vecAsiento = new java.util.Vector();         
     
         ///  double dblValTotDes =  getTotalDescuento();
        
           if(dblSubtotalCot > 0){   //// if((dblValTotDes+dblSubtotalCot)>0){ 
             java.util.Vector vecRowAsientoDeb = vecFila2(objCtaCta_Dat.getCtaDeb(),objCtaCta_Dat.getCtaCodDeb(),objCtaCta_Dat.getCtaNomDeb()); 
             vecRowAsientoDeb.setElementAt(new Double(objutil.redondear(dblSubtotalCot,2)), 5);
             vecAsiento.add(vecRowAsientoDeb);
          }
        
        /* Agregando la cuenta de descuento  */
     
        if( dblIvaCot > 0){ 
            java.util.Vector vecRowAsientoDeb = vecFila2(objCtaCta_Dat.getCtaIvaVentas(),objCtaCta_Dat.getCtaCodIvaVen(),objCtaCta_Dat.getCtaNomIvaVen()); 
            vecRowAsientoDeb.setElementAt(new Double(dblIvaCot), 5);
            vecAsiento.add(vecRowAsientoDeb);
        }
        
        /*
         * Agregando la cuenta de Ventas
         */
        if( dblTotalDoc > 0){
            java.util.Vector vecRowAsientoHab = vecFila2(objCtaCta_Dat.getCtaHab(),objCtaCta_Dat.getCtaCodHab(),objCtaCta_Dat.getCtaNomHab()); 
            vecRowAsientoHab.setElementAt(new Double(objutil.redondear(dblTotalCot,2)), 6);
            vecAsiento.add(vecRowAsientoHab);
        }
        /*
         * Agregando la cuenta Iva en Ventas
         */
      /*  if( dblValTotDes > 0){ 
            java.util.Vector vecRowAsientoHab = vecFila2(objCtaCta_Dat.getCtaDescVentas(),objCtaCta_Dat.getCtaCodDes(),objCtaCta_Dat.getCtaNomDes()); 
            vecRowAsientoHab.setElementAt(new Double(objutil.redondear(dblValTotDes,2)), 6);
            vecAsiento.add(vecRowAsientoHab);
       }        */
        objAsiDia.setDetalleDiario(vecAsiento);        
    }
     
      
      
      //*********************************************************************************************
      
      private java.util.Vector vecFila2(int intCodCtaCtb,String codcta,String Nomcta){
            java.util.Vector vecRowAsiento = new java.util.Vector(); 
            /**Linea*/
            vecRowAsiento.add(null);

            /** Codigo de cuenta */
            vecRowAsiento.add(intCodCtaCtb+"");

            /** Codigo de ALterno de cuenta */
            vecRowAsiento.add(codcta);  //objCtaCta_Dat.getCodAltCta(intCodCtaCtb));

            /** Boton buscar cuenta */
            vecRowAsiento.add(null);

            /** NOmbre de cuenta */
            vecRowAsiento.add(Nomcta);  //objCtaCta_Dat.getNomCta(intCodCtaCtb));

            /** Valor que va en el debe */
            vecRowAsiento.add("0");

            /** Valor que va en el Haber  */
            vecRowAsiento.add("0");

            /** Referencia */
            vecRowAsiento.add(null);


             vecRowAsiento.add(null);

            return vecRowAsiento;

    }    
     
      
     
     ///////********************************************///////////*******************/////
   
    
    
   
      
      
      
          public double getTotalDescuento(){
            double dblTotalDescuento=0;
            for(int filaActual=0; filaActual< tblFacDet.getRowCount(); filaActual++){
               if(tblFacDet.getValueAt(filaActual, INT_TBL_CODITM)!=null){
                   
                     if(tblFacDet.getValueAt(filaActual, INT_TBL_CANDEV ) != null){
                       if(!tblFacDet.getValueAt(filaActual, INT_TBL_CANDEV ).equals("")){
                           if( Double.parseDouble(tblFacDet.getValueAt(filaActual, INT_TBL_CANDEV).toString()) > 0.00 ){
                   
                   double dblCan    =  objutil.redondeo(Double.parseDouble(((tblFacDet.getValueAt(filaActual, INT_TBL_CANDEV)==null)?"0":(tblFacDet.getValueAt(filaActual, INT_TBL_CANDEV).toString()))),6), 
                          dblPre    =  objutil.redondeo(Double.parseDouble(((tblFacDet.getValueAt(filaActual, INT_TBL_PRECIO)==null)?"0":(tblFacDet.getValueAt(filaActual, INT_TBL_PRECIO).toString()))),6),
                          dblPorDes =  objutil.redondeo(Double.parseDouble(((tblFacDet.getValueAt(filaActual, INT_TBL_PORDES )==null)?"0":(tblFacDet.getValueAt(filaActual, INT_TBL_PORDES).toString()))),2),
                          dblValDes =  objutil.redondeo(((dblCan * dblPre)==0)?0:(objutil.redondeo((dblCan * dblPre)* dblPorDes, 6)  / 100),6);
                          dblTotalDescuento =  dblTotalDescuento + dblValDes;      
                  
                  
               }}}}}
            
            dblTotalDescuento =  objutil.redondear(objutil.redondear(dblTotalDescuento,4),2);  
            
            return dblTotalDescuento;
        }
     
       
      
      
      
      
    public void  refrescaDatos(){
        try{//odbc,usuario,password        
            removeLisCamTbls();
            int intNumCot = 0;
                if(rstDocCab != null){
                    intNumCot = rstDocCab.getInt("co_doc");

                    java.sql.Statement stmDatosDoc = CONN_GLO.createStatement();

                     /*
                      * Agregando el Sql de Consulta para el Documento
                      */
                    String strDatosDocSQL = "SELECT DocCab.co_cli, DocCab.tx_nomCli as nomcli, DocCab.tx_dirCli as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                          " DocCab.co_com as co_ven, DocCab.tx_nomVen as nomven, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                          " DocCab.co_doc, DocCab.fe_doc, DocCab.co_tipDoc, DocCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                          " DocCab.ne_numCot, DocCab.ne_numDoc,  DocCab.tx_numPed,  DocCab.ne_numGui, " + // Numero de cotizacion, factura de imprenta, guia y pedido
                          " DocCab.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                          " DocCab.tx_obs1, DocCab.tx_obs2, DocCab.nd_sub, DocCab.nd_porIva, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                          " DocCab.st_reg, " + // Campo para saber si esta anulado o no. Codigo de Bodega
                          " DocCab.tx_ptoPar, DocCab.tx_tra, DocCab.co_motTra, DocCab.tx_desMotTra , DocCab.ne_secgrp "   + //Campos del Tab OTROS 
                          " ,DocCab.st_tipdev , DocCab.st_regrep FROM tbm_cabMovInv as DocCab, tbm_cabtipdoc as doc  " + // Tabla enla cual se trabajara y sus respectivo alia
                          " Where  DocCab.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                          "        and DocCab.co_loc = " + objZafParSis.getCodigoLocal() +
                          "        and DocCab.co_doc = " + intNumCot +// Consultando en el local en el ke se esta trabajando
                          "   and DocCab.st_reg not in ('E')     and DocCab.co_tipdoc = " + rstDocCab.getInt("co_tipdoc") + // Consultando en el local en el ke se esta trabajando
                          "        and doc.co_emp = DocCab.co_emp and doc.co_loc = DocCab.co_loc ";// Consultando en el local en el ke se esta trabajando
  
                    
                    java.sql.ResultSet rstDatosDocCab=stmDatosDoc.executeQuery(strDatosDocSQL);
                                      
                    if(rstDatosDocCab.next()){                    
                        txtDoc.setText(""+intNumCot);

                        txtTipDocCod.setText(((rstDatosDocCab.getString("co_tipdoc")==null)?"":rstDatosDocCab.getString("co_tipdoc")));
                        int intNumTipDoc[] = getNumTipDoc(rstDatosDocCab.getInt("ne_numcot"));
                        if(intNumTipDoc != null){
                                                   
                       objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objZafParSis,  Integer.parseInt( (txtTipDocCod.getText().equals(""))?"0":txtTipDocCod.getText() ));            
              
                       txtFromFac.setText(""+intNumTipDoc[0]);
                         
                        
                          cboTipDoc.setSelectedIndex(vecTipDoc.indexOf(""+intNumTipDoc[1]));
                       }
                        txtDev.setText(""+rstDatosDocCab.getInt("ne_numDoc"));
                        txtCliCod.setText(((rstDatosDocCab.getString("co_cli")==null)?"":rstDatosDocCab.getString("co_cli")));
                        txtCliNom.setText(((rstDatosDocCab.getString("nomcli")==null)?"":rstDatosDocCab.getString("nomcli")));
                        txtCliDir.setText(((rstDatosDocCab.getString("dircli")==null)?"":rstDatosDocCab.getString("dircli")));

                        STR_ESTREG=rstDatosDocCab.getString("st_regrep");
                        
                        java.util.Date dateObj = rstDatosDocCab.getDate("fe_doc");
                        if(dateObj==null){
                          txtFecDoc.setText("");  
                        }else{
                            java.util.Calendar calObj = java.util.Calendar.getInstance();
                            calObj.setTime(dateObj);
                            txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                              calObj.get(java.util.Calendar.MONTH)+1     ,
                                              calObj.get(java.util.Calendar.YEAR)        );
                        }

                        txtVenCod.setText(((rstDatosDocCab.getString("co_ven")==null)?"":rstDatosDocCab.getString("co_ven")));
                        txtVenNom.setText(((rstDatosDocCab.getString("nomven")==null)?"":rstDatosDocCab.getString("nomven")));
                        txtAte.setText(((rstDatosDocCab.getString("tx_ate")==null)?"":rstDatosDocCab.getString("tx_ate")));

                        Glo_intCodSec = rstDatosDocCab.getInt("ne_secgrp");
                        
                        ////////////////////////////////////////////////////////////////////////////////
                        
                         strAux=rstDatosDocCab.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                        // objeto.setEstadoRegistro(strAux);
                          /*
                     * VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO
                     */
                    if(strAux.equals("I")){
                        objutil.desactivarCom(jfrThis);
                    }else{
//                        if (objeto.getEstado() == 'm' ){
//                           // objutil.activarCom(jfrThis);
//                            noEditable(false);
//                        }
                    }
                         
                         
                         
                         if(rstDatosDocCab.getString("st_tipdev").equalsIgnoreCase("C")) item.setSelected(true);
                         if(rstDatosDocCab.getString("st_tipdev").equalsIgnoreCase("P")) desc.setSelected(true);
                         if(rstDatosDocCab.getString("st_tipdev").equalsIgnoreCase("V")) val.setSelected(true);
                         
                         
                    //////////////////////////////////////////////////////
                            
                        
                   
                            
                         sSQL  = "Select a.tx_codalt,a.co_itm,a.tx_nomitm,a.co_bod,a.co_itm,a.nd_canorg,a.nd_can,a.nd_preuni,a.nd_pordes,a.st_ivaCom, " +
                                " a.tx_codalt2,a.nd_cosunigrp,b.st_ser, a.tx_unimed, a.nd_candev , a.co_reg " +
                               // ",x.tx_nomcli, x.co_regrel, x.co_tipdocrel, x.co_docrel ,x.co_locrel  " +
                                " FROM tbm_detMovInv as a " +
                                " INNER join tbm_inv as b on(a.co_emp=b.co_emp and a.co_itm=b.co_itm) " +
//                                "" +
//                                " LEFT OUTER JOIN ( " +
//                                " SELECT tbrd.co_reg, tbrd.co_regrel, tbrd.co_tipdocrel, tbrd.co_docrel, tbrd.co_locrel, oc.tx_nomcli  FROM  tbr_detmovinv AS tbrd " +
//                                " INNER JOIN tbm_cabmovinv AS oc ON(oc.co_emp=tbrd.co_emp AND oc.co_loc=tbrd.co_locrel AND oc.co_tipdoc=tbrd.co_tipdocrel " +
//                                " AND oc.co_doc=tbrd.co_docrel )" +
//                                " WHERE tbrd.co_emp="+objZafParSis.getCodigoEmpresa()+" AND tbrd.co_loc="+objZafParSis.getCodigoLocal()+" AND tbrd.co_tipdoc="+ txtTipDocCod.getText()+" AND tbrd.co_doc="+intNumCot+" " +
//                                " ) as x ON (x.co_reg=a.co_reg)" +
                                "" +
                                " WHERE " +
                                " a.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                                " a.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                                " a.co_doc = " + intNumCot                       + " and " +
                                " a.co_tipDoc = " + txtTipDocCod.getText() + " order by a.co_reg";
                        
                     //   System.out.println("OKKKLLLL "+ sSQL);  //***************
                        
                       
                        
                        stmDocDet=CONN_GLO.createStatement();

                        rstDocDet = stmDocDet.executeQuery(sSQL);
                         double dblCan    =  0, dblCanDev = 0 , dblCanOrg = 0,  dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0;

  
                         
                        
                          dblPorIva = objutil.redondeo(rstDatosDocCab.getDouble("nd_porIva"),2);
                         double dbl_Iva=0;
                         double dbl_Sub    = 0; 
                                    
                          Vector vecData = new Vector();
                         
                        for(int i=0 ; rstDocDet.next() ; i++){
                              java.util.Vector vecReg = new java.util.Vector();
                            
                             vecReg.add(INT_TBL_LINEA, "");
                             vecReg.add(INT_TBL_ALTITM, rstDocDet.getString("tx_codalt") );
                             vecReg.add(INT_TBL_DESITM, rstDocDet.getString("tx_nomitm"));
                             vecReg.add(INT_TBL_UNIDAD, rstDocDet.getString("tx_unimed"));
                             vecReg.add(INT_TBL_CODBOD, new Integer(rstDocDet.getInt("co_bod")));
                                 
                             vecReg.add(INT_TBL_CANMOV , new Double(rstDocDet.getDouble("nd_canorg")));
                             
                             vecReg.add(INT_TBL_CANDIFDEV, new Double(rstDocDet.getDouble("nd_candev"))  );      
                           
                             
                             if(item.isSelected())
                                 vecReg.add(INT_TBL_CANDEV, new Double(rstDocDet.getDouble("nd_can")));
                              else
                                   vecReg.add(INT_TBL_CANDEV, "" );
                              
                             
                               
                             vecReg.add(INT_TBL_PRECIO , new Double(rstDocDet.getDouble("nd_preuni")));
                            
                             
                             if(val.isSelected())
                                  vecReg.add(INT_TBL_PREDEV, new Double(rstDocDet.getDouble("nd_can")));
                                 else
                                   vecReg.add(INT_TBL_PREDEV, "");
                             
                             
                             
                             
                             vecReg.add(INT_TBL_PORDES, new Double(rstDocDet.getDouble("nd_pordes")));
                             
                              if(desc.isSelected())
                                  vecReg.add(INT_TBL_CANDES, new Double(rstDocDet.getDouble("nd_can")));
                                 else
                                   vecReg.add(INT_TBL_CANDES, "");
                             
                             
                             
                             
                             
                             
                              String strIva = rstDocDet.getString("st_ivacom");
                              Boolean blnIva = new Boolean(strIva.equals("S"));                         
                              vecReg.add(INT_TBL_IVAITM , blnIva);
                            
                               dblPre    = rstDocDet.getDouble("nd_preuni");
                               dblCan = rstDocDet.getDouble("nd_can");
                               dblPorDes = rstDocDet.getDouble("nd_pordes");
                               dblCanOrg = rstDocDet.getDouble("nd_candev"); //rstDocDet.getDouble("nd_canorg");
                               
                             if(item.isSelected()){   
                                 dblValDes =   ((dblCan * dblPre * dblPorDes)==0)?0:((dblCan * dblPre) * dblPorDes / 100) ;
                                 dblTotal  = (dblCan * dblPre)- dblValDes ;
                             }
                            if(desc.isSelected()){
                                dblValDes =   (dblCanOrg * dblPre );  ///* dblPorDes)==0)?0:((dblCan * dblPre) * dblPorDes / 100) ;
                                dblTotal  = dblValDes * (dblCan / 100) ;
                            }
                               
                             if(val.isSelected()){   
                                 dblValDes =   ((dblCanOrg * dblCan * dblPorDes)==0)?0:((dblCanOrg * dblCan) * dblPorDes / 100) ;
                                 dblTotal  = (dblCanOrg * dblCan)- dblValDes ;
                             }
                               
                               
                            dblTotal =  objutil.redondear(dblTotal,3);
                            dblTotal =  objutil.redondear(dblTotal,2);
                              
                              vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
                             
                              vecReg.add(INT_TBL_CODITM , new Integer(rstDocDet.getInt("co_itm")));
                              vecReg.add(INT_TBL_ALTITM2, rstDocDet.getString("tx_codalt2"));
                             
                              vecReg.add(INT_TBL_ITMSER , rstDocDet.getString("st_ser"));
                              vecReg.add(INT_TBL_COSTO, new Double( rstDocDet.getDouble("nd_cosunigrp") ));
                              vecReg.add(INT_TBL_DESORI, new Double(rstDocDet.getDouble("nd_pordes")));
                          
                                
                               
                             vecReg.add(INT_TBL_NOMPRV , null ); //  rstDocDet.getString("tx_nomcli"));
                             vecReg.add(INT_TBL_CODLOC , null ); //rstDocDet.getString("co_locrel"));
                             vecReg.add(INT_TBL_CODTIP , null ); // rstDocDet.getString("co_tipdocrel"));
                             vecReg.add(INT_TBL_CODDOC , null ); //rstDocDet.getString("co_docrel"));
                             vecReg.add(INT_TBL_CODREG , null ); // rstDocDet.getString("co_regrel"));
                             vecReg.add(INT_TBL_CODREGDEV ,rstDocDet.getString("co_reg") ); 
                              
                              
                              vecData.add(vecReg);    
                            
                            
                         }
                          /**/
                          objTblMod.setData(vecData);
                          tblFacDet.setModel(objTblMod);  
                      
                           //ObtenerDiferenciadeDevolucion();
                           
                         if(item.isSelected()){
                           ocultaCol(INT_TBL_CANDES) ;    
                           ocultaCol(INT_TBL_PREDEV);
                           MostrarCol(INT_TBL_CANDEV) ; 
                           
                            ocultaCol(INT_TBL_CANDIFDEV) ;
                            MostrarCol(INT_TBL_CANMOV) ; 
                           
                         } if(desc.isSelected())    {
                           ocultaCol(INT_TBL_CANDEV) ;
                           ocultaCol(INT_TBL_PREDEV);
                           MostrarCol(INT_TBL_CANDES) ; 
                            ocultaCol(INT_TBL_CANMOV) ;
                            MostrarCol(INT_TBL_CANDIFDEV) ; 
         
                           
                         } if(val.isSelected())    {
                           ocultaCol(INT_TBL_CANDEV);
                           ocultaCol(INT_TBL_CANDES);
                           MostrarCol(INT_TBL_PREDEV); 
                            ocultaCol(INT_TBL_CANMOV) ;
                            MostrarCol(INT_TBL_CANDIFDEV) ; 
         
                         }
                          
                         
                       
                             
                             
                    //Si es ke no tiene ninguna fila se le pone una en blanco
                        if(tblFacDet.getRowCount()==0)
                            ((javax.swing.table.DefaultTableModel)tblFacDet.getModel()).addRow(new java.util.Vector());    

                    //Pie de pagina
                        txaObs1.setText(((rstDatosDocCab.getString("tx_obs1")==null)?"":rstDatosDocCab.getString("tx_obs1")));
                        txaObs2.setText(((rstDatosDocCab.getString("tx_obs2")==null)?"":rstDatosDocCab.getString("tx_obs2")));

                        //**dblPorIva = objutil.redondeo(rstDatosDocCab.getDouble("nd_porIva"),2);


                                 dbl_Sub    = rstDatosDocCab.getDouble("nd_sub"); 
                              // **dbl_Iva    = ((dbl_Sub* dblPorIva)==0)?0:((dbl_Sub* dblPorIva)/100);

                        dblSubtotalDoc = dbl_Sub;
                        dblIvaDoc = dbl_Iva;
                        dblTotalDoc = dblSubtotalDoc + dblIvaDoc ;

                         if(item.isSelected()) calculaTotal();
                          if(desc.isSelected()) calculaTotalDesc();  
                           if(val.isSelected()) calculaTotalPreDev();
                        
                       
                        lblFacNumDes.setText("Devolucion No. " + txtDoc.getText() +  " (" + txtCliNom.getText() + ") ");
                        lblIva.setText("IVA " + dblPorIva + "%");

                    /*
                     * CARGANDO DATOS DEL TAB ASIENTO DE DIARIO
                     */
                        
                           
                           
                           
                        objAsiDia.consultarDiario(objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), Integer.parseInt(txtTipDocCod.getText()), intNumCot);

                         
                           
                        //refrescaAsiento(intNumCot);


                    /*
                     * VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO
                     */
                        String strStatus = rstDatosDocCab.getString("st_reg");
                        if(strStatus.equals("I")){
                            lblFacNumDes.setText( lblFacNumDes.getText()+ "#ANULADO#");
                            objutil.desactivarCom(jfrThis);
                        }else{
//                            if (objeto.getEstado() == 'm' ){
//                               // objutil.activarCom(jfrThis);
//                                noEditable(false);
//                            }
                        }
                        rstDatosDocCab.close();
                        stmDatosDoc.close();
                    }else{
                        clnTextos();
                      //  objeto.setEstadoRegistro("Eliminado");
                        /**
                         * Significa que fue eliminado
                         */
                    }

                }
                    
            blnCambios = false; // Seteando que no se ha hecho cambios
            
          addLisCamTbls();      
          
          
            noEditable(false);                
                                        java.awt.Color colBack = txtDoc.getBackground();
                                        txtDoc.setEditable(false);
                                        txtDoc.setBackground(colBack);
                                         colBack = txtFromFac.getBackground();
                                         txtFromFac.setEditable(false);
                                         txtFromFac.setBackground(colBack);
            
                                        colBack = txtTipDocCod.getBackground();
                                        txtTipDocCod.setEditable(false);
                                        txtTipDocCod.setBackground(colBack);        

                                        colBack = txtTipDocNom.getBackground();
                                        txtTipDocNom.setEditable(false);
                                        txtTipDocNom.setBackground(colBack);
                                        butTipDoc.setEnabled(false);
          

        }//fin Try
       catch(SQLException Evt)
       {      addLisCamTbls();
              objutil.mostrarMsgErr_F1(jfrThis, Evt);
              
        }

        catch(Exception Evt)
        {     addLisCamTbls();
              objutil.mostrarMsgErr_F1(jfrThis, Evt);
        }                       
      
    }
    
    
    
     
    
        public boolean ObtenerDiferenciadeDevolucion(){
                    try{
                                for(int i=0; i<tblFacDet.getRowCount();i++){
                                        if(tblFacDet.getValueAt(i,INT_TBL_CODITM) != null ){
                                            // if(tblFacDet.getValueAt(i, INT_TBL_CANDEV ) != null){
                                               //   if(!tblFacDet.getValueAt(i, INT_TBL_CANDEV ).equals("")){
                                                double dblCanMov = Double.parseDouble(tblFacDet.getValueAt(i, INT_TBL_CANMOV).toString());
                                             //    if( dblCanMov > 0.00 ){
                                                    
                                                 int intCodItm =  Integer.parseInt((tblFacDet.getValueAt(i, INT_TBL_CODITM).toString().equals(""))?"0":tblFacDet.getValueAt(i, INT_TBL_CODITM).toString()); 
                                                  
                                                     
                                          String  sql = "select sum(b.nd_can) as total from tbr_cabmovinv as a"+ 
                                                      " inner join tbm_cabmovinv as b1 on(a.co_doc=b1.co_doc and a.co_tipdoc=b1.co_tipdoc and b1.st_tipdev not in('P','V') )" +
                                                      " inner join tbm_detmovinv as b on(b1.co_doc=b.co_doc and b1.co_tipdoc=b.co_tipdoc)"+
                                                      " where a.co_docrel="+strCodDocFac+" and a.co_emp="+objZafParSis.getCodigoEmpresa()+" and "+
                                                      " a.co_tipdocrel="+strCodTipDocFac+" and co_itm="+intCodItm+" and a.st_reg='A'";
                                                     stmDocDet=CONN_GLO.createStatement();
                                                     rstDocDet2 = stmDocDet.executeQuery(sql);
                                                     double intNumDev=0; 
                                                     if(rstDocDet2.next()){
                                                         intNumDev = rstDocDet2.getDouble(1); 
                                                     }
                                                     intNumDev=dblCanMov-intNumDev;
                                                   
                                                     tblFacDet.setValueAt( String.valueOf(intNumDev) , i , INT_TBL_CANDIFDEV );
                                                     
                                                     tblFacDet.setValueAt( String.valueOf(intNumDev) , i , INT_TBL_CANDEV );
                                                     
                                                     
                                                     
                                                  
                                                 }
                                   //}  //}  //}
                               }   
                         
                       
                       }
                       catch(SQLException Evt)
                       {    
                            objutil.mostrarMsgErr_F1(jfrThis, Evt);
                            return false;
                        }  
                    return true;
            }
    
    
    /*
     * Agrega el listener para detectar que hubo algun cambio en la caja de texto
     */
    private void addListenerCambio(){
        objlisCambios = new LisTextos();
        //Cabecera
        txtDoc.setText("");
            //txtFecDoc.getDocument().addDocumentListener(objlisCambios);
    }   
    
    private void removeLisCamTbls(){
        tblFacDet.getModel().removeTableModelListener(objLisCamTblFac);
    }
    
    private void addLisCamTbls(){
        tblFacDet.getModel().addTableModelListener(objLisCamTblFac);
    }
    
   
   

    /*
     * Retorna el numero secuencial asociados a ese numero y tipo de documento.
     */
    public int[] getNumTipDoc(int int_OrdDoc){
        int intSec[] = new int[2];
        
        try{
            ResultSet rstFromFac;
            Statement stmFromFac = CONN_GLO.createStatement();
            String SqlSecuencial = "Select ne_numdoc, co_tipdoc from tbm_cabmovinv " +
                                             "where ne_numdoc  = " + int_OrdDoc ;
                                   
            rstFromFac = stmFromFac.executeQuery(SqlSecuencial);
            
            if(rstFromFac.next()){
                intSec[0] = rstFromFac.getInt("ne_numdoc");
                intSec[1] = rstFromFac.getInt("co_tipdoc");
            }
            
            rstFromFac.close();
            stmFromFac.close();
        }
        catch(SQLException Evt)
         {
              objutil.mostrarMsgErr_F1(jfrThis, Evt);
            }
        catch(Exception Evt)
        {
              objutil.mostrarMsgErr_F1(jfrThis, Evt);
            }                       
        
        return intSec;
    }
      

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup Grupo1;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton butacep;
    private javax.swing.JComboBox cboTipDoc;
    private javax.swing.JRadioButton desc;
    private javax.swing.JRadioButton item;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblAte;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblDev;
    private javax.swing.JLabel lblDir;
    private javax.swing.JLabel lblDoc;
    private javax.swing.JLabel lblFacNumDes;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblFecFac;
    private javax.swing.JLabel lblFromFac;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblSubTot;
    private javax.swing.JLabel lblTot;
    private javax.swing.JLabel lblVen;
    private javax.swing.JComboBox local;
    private javax.swing.JPanel panAsiento;
    private javax.swing.JPanel panCotGen;
    private javax.swing.JPanel panCotGenNor;
    private javax.swing.JPanel panCotGenSur;
    private javax.swing.JPanel panCotGenSurCen;
    private javax.swing.JPanel panCotGenSurEst;
    private javax.swing.JPanel panCotNor;
    private javax.swing.JScrollPane spnCon;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabCotCom;
    private javax.swing.JTable tblFacDet;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtAte;
    private javax.swing.JTextField txtCliCod;
    private javax.swing.JTextField txtCliDir;
    private javax.swing.JTextField txtCliNom;
    private javax.swing.JTextField txtDev;
    private javax.swing.JTextField txtDoc;
    private javax.swing.JTextField txtFecFac;
    private javax.swing.JTextField txtFromFac;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtTipDocCod;
    private javax.swing.JTextField txtTipDocNom;
    private javax.swing.JTextField txtTot;
    private javax.swing.JTextField txtVenCod;
    private javax.swing.JTextField txtVenNom;
    private javax.swing.JRadioButton val;
    // End of variables declaration//GEN-END:variables
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
}
